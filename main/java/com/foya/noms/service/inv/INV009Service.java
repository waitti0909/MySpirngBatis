package com.foya.noms.service.inv;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hornetq.utils.json.JSONArray;
import org.hornetq.utils.json.JSONException;
import org.hornetq.utils.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foya.dao.mybatis.model.TbInvInv;
import com.foya.dao.mybatis.model.TbInvSrl;
import com.foya.dao.mybatis.model.TbInvTr;
import com.foya.dao.mybatis.model.TbInvTrAct;
import com.foya.dao.mybatis.model.TbInvTxn;
import com.foya.exception.NomsException;
import com.foya.noms.dao.inv.INV008Dao;
import com.foya.noms.dao.inv.INV009Dao;
import com.foya.noms.dto.inv.TbInvTrActCompleteDTO;
import com.foya.noms.enums.InvEnumCommon;
import com.foya.noms.service.BaseService;
import com.foya.noms.util.BaseJasonObject;

@Service
public class INV009Service extends BaseService {

	@Autowired
	private INV009Dao inv009Dao;

	@Autowired
	private INV008Dao inv008Dao;

	/**
	 * 查詢調撥資料
	 * 
	 * @param trNo
	 *            調撥申請單號
	 * 
	 * @return List<TbInvTrAct>
	 */
	public List<TbInvTrActCompleteDTO> getInvTrExportDtlData(String trNo) {

		return inv009Dao.getInvTrExportDtlData(trNo);
	}

	/**
	 * 查詢調撥數量
	 * 
	 * @param trNo
	 *            調撥申請單號
	 * 
	 * @return List<TbInvTrAct>
	 */
	public List<TbInvTrActCompleteDTO> getApplyTotal(String trNo) {

		return inv009Dao.getApplyTotal(trNo);
	}

	/**
	 * 執行調入動作
	 * 
	 * @param trDtlDtoList
	 *            List<TbInvTrActCompleteDTO>
	 * @param userNo
	 *            userNo
	 * @param action
	 *            1:調入，2:結案
	 * @return boolean
	 * @throws JSONException
	 */
	@Transactional(rollbackFor = NomsException.class)
	public BaseJasonObject<JSONObject> executionImportWork(String action, JSONObject invTrDto, JSONArray invTrActDtoList) throws NomsException,
			JSONException {

		Date nowTime = new Date();

		// 返回件紀錄
		ArrayList<JSONObject> returnMatNoList = new ArrayList<JSONObject>();

		int sum = 0;

		int befortExportSum = 0;

		int presentExportSum = 0;

		int importSum = 0;

		// 紀錄所有的申請數是否已全調出
		Map<String, Integer> applyNumberMap = new HashMap<String, Integer>();

		// 取得申請數量
		List<TbInvTrActCompleteDTO> applyTotalList = getApplyTotal(invTrDto.getString("tr_no"));

		for (TbInvTrActCompleteDTO dto : applyTotalList) {
			// 加總此單號總共可調入多少數量
			sum += dto.getAppQty();
			applyNumberMap.put(dto.getMAT_NO(), dto.getAppQty());
		}

		l: for (int i = 0; i < invTrActDtoList.length(); i++) {

			JSONObject invTrAct = invTrActDtoList.getJSONObject(i);

			// 加總此單號已經調出多少數量
			befortExportSum += invTrAct.getInt("tr_QTY");

			applyNumberMap.put(invTrAct.getString("mat_NO"), applyNumberMap.get(invTrAct.get("mat_NO")) - invTrAct.getInt("tr_QTY"));

			int trQty = invTrAct.getString("dtlTrQty").equals("null") ? 0 : invTrAct.getInt("dtlTrQty");

			// 加總此單號已經調入數輛
			importSum += trQty;

			if (invTrAct.getInt("tr_QTY") == trQty) {

				continue l;
			}

			// 紀錄返回件
			if (action.equals("3") && ((invTrAct.getInt("tr_QTY") - trQty) > invTrAct.getInt("exportNumber"))) {
				returnMatNoList.add(invTrAct);
			}

			// 有調入才做動作
			if (invTrAct.getInt("exportNumber") > 0) {

				// 加總此單號本次調入數量
				presentExportSum += invTrAct.getInt("exportNumber");

				// 更新TB_INV_INV
				switch (InvEnumCommon.ProductStatus.toSource(invTrAct.getInt("matStatus"))) {
				case NORMAL:
					// 可用品
					boolean value1 = inv009Dao.updateStockByCallIn(1, invTrAct.getString("mat_NO"), invTrDto.getString("tr_in_wh_id"),
							invTrAct.getInt("exportNumber"));

					if (!value1) {
						TbInvInv invInv = getTbInvInv(invTrAct, invTrDto);
						invInv.setStd_qty(invTrAct.getInt("exportNumber"));
						invInv.setCr_time(nowTime);
						invInv.setMd_time(nowTime);
						inv009Dao.insertInvInv(invInv);
					}

					break;
				case PENDING_INVALID:
					// 待報廢
					boolean value3 = inv009Dao.updateStockByCallIn(3, invTrAct.getString("mat_NO"), invTrDto.getString("tr_in_wh_id"),
							invTrAct.getInt("exportNumber"));

					if (!value3) {
						TbInvInv invInv = getTbInvInv(invTrAct, invTrDto);
						invInv.setWsp_qty(invTrAct.getInt("exportNumber"));
						invInv.setCr_time(nowTime);
						invInv.setMd_time(nowTime);
						inv009Dao.insertInvInv(invInv);
					}
					break;
				case PENDING_REPAIR:
					// 待送修
					boolean value2 = inv009Dao.updateStockByCallIn(2, invTrAct.getString("mat_NO"), invTrDto.getString("tr_in_wh_id"),
							invTrAct.getInt("exportNumber"));

					if (!value2) {
						TbInvInv invInv = getTbInvInv(invTrAct, invTrDto);
						invInv.setWrd_qty(invTrAct.getInt("exportNumber"));
						invInv.setCr_time(nowTime);
						invInv.setMd_time(nowTime);
						inv009Dao.insertInvInv(invInv);
					}
					break;
				default:
					break;
				}

				// 新增 TB_INV_TXN
				TbInvTxn tbInvTxn = getIbInvTxn(invTrAct, invTrDto);
				tbInvTxn.setTxn_type((byte) InvEnumCommon.DepotBusinessStatus.IN.getCode());
				tbInvTxn.setCr_time(nowTime);
				inv009Dao.insertInvTxn(tbInvTxn);

				// 新增 TB_INV_TR_ACT(調撥紀錄檔)
				TbInvTrAct tbInvTrAct = getTbInvTrAct(invTrAct, invTrDto);
				tbInvTrAct.setTR_IN_DATE(nowTime);
				tbInvTrAct.setCR_TIME(nowTime);
				tbInvTrAct.setMD_TIME(nowTime);
				inv009Dao.insertInvTrAct(tbInvTrAct);

				// 更新 TB_INV_ONHAND
				inv009Dao.updateOnhandQty(invTrAct.getInt("exportNumber"), nowTime, getLoginUser().getUsername(), invTrDto.getString("tr_no"),
						invTrAct.getString("mat_NO"), invTrAct.getLong("tr_DTL_NO"));

				// 若為序號件則更新序號檔
				if (!invTrAct.getString("srl_NO").equals("null")) {
					TbInvSrl tbInvSrl = new TbInvSrl();
					tbInvSrl.setSrl_no(invTrAct.getLong("srl_NO"));
					tbInvSrl.setWh_id(invTrDto.getString("tr_in_wh_id"));
					tbInvSrl.setSite_id(null);
					tbInvSrl.setMd_time(nowTime);
					tbInvSrl.setMd_user(getLoginUser().getUsername());

					// 更新 TB_INV_SRL
					inv009Dao.updateTbInvSrl(tbInvSrl);
				}
			}
		}
		
		// 代表尚未輸入任何數量
		if (!action.equals("3") && presentExportSum == 0) {
			return new BaseJasonObject<JSONObject>(false, "請輸入調入量。");
		}

		// 更新 TB_INV_TR
		/**
		 * 申請單<BR>
		 * 判斷此單號全申請數是否等於調出數?是(結案):否(是否強制戒案?是(強制結案):否(原狀態)
		 */
		int status = sum == (importSum + presentExportSum) ? 7 : action.equals("3") ? 9 : (byte) invTrDto
				.getInt("status");

		TbInvTr invTr = new TbInvTr();
		invTr.setTr_no(invTrDto.getString("tr_no"));
		invTr.setStatus((byte) status);
		invTr.setIf_manual_close(action.equals("3") ? true : false);
		invTr.setMd_user(getLoginUser().getUsername());
		invTr.setMd_time(nowTime);
		inv009Dao.updateTbInvTrByKey(invTr);

		// 處裡缺少件
		if (returnMatNoList.size() > 0) {
			lackItem(returnMatNoList, invTrDto, nowTime);
		}

		if (action.equals("3")) {
			for (String key : applyNumberMap.keySet()) {
				if (applyNumberMap.get(key) > 0) {
					// 結案狀態，但卻有數量尚未調出，則扣回Booking
					// 更新Booking
					inv008Dao.updateBookingQty(invTrDto.getString("tr_no"), "2", key, invTrDto.getString("tr_out_wh_id"), applyNumberMap.get(key));
				}
			}
		}

		return new BaseJasonObject<JSONObject>(true, "調入成功。");
	}

	/**
	 * 處裡缺少料件
	 * 
	 * @param lackTrDtlDtoList
	 *            List<TbInvTrDtlCompleteDTO>
	 * @param invTr
	 *            TbInvTr
	 * @param userNo
	 *            userNo
	 * @throws JSONException
	 * 
	 */
	private void lackItem(ArrayList<JSONObject> returnMatNoList, JSONObject invTrDto, Date nowTime) throws JSONException {

		for (JSONObject invTrAct : returnMatNoList) {

			int trQty = invTrAct.getString("dtlTrQty").equals("null") ? 0 : invTrAct.getInt("dtlTrQty");

			// 返回數
			int returnNumber = invTrAct.getInt("tr_QTY") - trQty - invTrAct.getInt("exportNumber");

			// 新增 IB_INV_TXN
			TbInvTxn tbInvTxn = getIbInvTxn(invTrAct, invTrDto);
			tbInvTxn.setTxn_type((byte) InvEnumCommon.DepotBusinessStatus.RETURN_EXPORT_DEPOT.getCode());
			tbInvTxn.setWh_id(invTrDto.getString("tr_out_wh_id"));
			tbInvTxn.setQty(returnNumber);
			inv009Dao.insertInvTxn(tbInvTxn);

			// 新增TB_INV_TR_ACT(調撥紀錄檔)
			TbInvTrAct tbInvTrAct = getTbInvTrAct(invTrAct, invTrDto);
			tbInvTrAct.setTR_QTY(returnNumber);
			tbInvTrAct.setTR_IN_WH_ID(invTrDto.getString("tr_out_wh_id"));
			tbInvTrAct.setIF_RETURN_TR_OUT(true);
			inv009Dao.insertInvTrAct(tbInvTrAct);

			// 更新 TB_INV_ONHAND
			inv009Dao.updateOnhandQty(returnNumber, nowTime, getLoginUser().getUsername(), invTrDto.getString("tr_no"), invTrAct.getString("mat_NO"),
					invTrAct.getLong("tr_DTL_NO"));

			// 若為序號件則更新序號檔
			if (!invTrAct.getString("srl_NO").equals("null")) {
				TbInvSrl tbInvSrl = new TbInvSrl();
				tbInvSrl.setSrl_no(invTrAct.getLong("srl_NO"));
				tbInvSrl.setSite_id(null);
				tbInvSrl.setMd_time(nowTime);
				tbInvSrl.setMd_user(getLoginUser().getUsername());
				tbInvSrl.setWh_id(invTrDto.getString("tr_out_wh_id"));

				// 更新 TB_INV_SRL
				inv009Dao.updateTbInvSrl(tbInvSrl);
			}

			// 更新TB_INV_INV
			switch (InvEnumCommon.ProductStatus.toSource(invTrAct.getInt("matStatus"))) {
			case NORMAL:
				// 可用品
				inv009Dao.updateStockByCallIn(1, invTrAct.getString("mat_NO"), invTrDto.getString("tr_out_wh_id"), returnNumber);
				break;
			case PENDING_INVALID:
				// 待報廢
				inv009Dao.updateStockByCallIn(3, invTrAct.getString("mat_NO"), invTrDto.getString("tr_out_wh_id"), returnNumber);
				break;
			case PENDING_REPAIR:
				// 待送修
				inv009Dao.updateStockByCallIn(2, invTrAct.getString("mat_NO"), invTrDto.getString("tr_out_wh_id"), returnNumber);
				break;
			default:
				break;
			}
		}

	}

	/**
	 * 取得 InvTxn
	 * 
	 * @param invTrDtlDtl
	 *            TbInvTrDtlCompleteDTO
	 * @param invTr
	 *            TbInvTr
	 * @param userNo
	 *            登入者
	 * @param depot
	 *            [out:紀錄調出倉ID,In:紀錄調入倉ID]
	 * @param lostEvent
	 *            是否為遺失件
	 * 
	 * @return TbInvTxn
	 * @throws JSONException
	 */
	private TbInvTxn getIbInvTxn(JSONObject invTrAct, JSONObject invTrDto) throws JSONException {

		String srlNo = invTrAct.getString("srl_NO");

		TbInvTxn invTxn = new TbInvTxn();
		invTxn.setTxn_type((byte) -1);
		invTxn.setTxn_no(invTrDto.getString("tr_no"));
		invTxn.setFa_no(invTrAct.getString("faNo"));
		invTxn.setSrl_no(srlNo.equals("null") ? null : invTrAct.getLong("srl_NO"));
		invTxn.setWh_id(invTrDto.getString("tr_in_wh_id"));
		invTxn.setMat_no(invTrAct.getString("mat_NO"));
		invTxn.setMat_status((byte) invTrAct.getInt("matStatus"));
		invTxn.setQty(invTrAct.getInt("exportNumber"));
		invTxn.setTr_dtl_no(invTrAct.getLong("tr_DTL_NO"));
		invTxn.setCr_user(getLoginUser().getUsername());
		invTxn.setCr_time(new Date());

		return invTxn;
	}

	/**
	 * 取得 TbInvTrAct
	 * 
	 * @param invTrDtlDto
	 *            TbInvTrDtlCompleteDTO
	 * @param invTr
	 *            TbInvTr
	 * @param userNo
	 *            登入者
	 * @param lsotEvent
	 *            是否為遺失件
	 * 
	 * @return TbInvTrAct
	 * @throws JSONException
	 */
	private TbInvTrAct getTbInvTrAct(JSONObject invTrAct, JSONObject invTrDto) throws JSONException {

		String srlNo = invTrAct.getString("srl_NO");

		TbInvTrAct trAct = new TbInvTrAct();
		trAct.setOUT_ACT_NO(invTrAct.getLong("tr_ACT_NO"));
		trAct.setTR_NO(invTrDto.getString("tr_no"));
		trAct.setMAT_NO(invTrAct.getString("mat_NO"));
		trAct.setSRL_NO(srlNo.equals("null") ? null : invTrAct.getLong("srl_NO"));
		trAct.setTR_DTL_NO(invTrAct.getLong("tr_DTL_NO"));
		trAct.setTR_QTY(invTrAct.getInt("exportNumber"));
		trAct.setIF_RETURN_TR_OUT(false);
		trAct.setTR_IN_WH_ID(invTrDto.getString("tr_in_wh_id"));
		trAct.setTR_IN_USER(getLoginUser().getUsername());
		trAct.setTR_IN_DATE(new Date());
		trAct.setIF_TR_IN(true);
		trAct.setCR_TIME(new Date());
		trAct.setCR_USER(getLoginUser().getUsername());
		trAct.setMD_TIME(new Date());
		trAct.setMD_USER(getLoginUser().getUsername());

		return trAct;
	}

	private TbInvInv getTbInvInv(JSONObject invTrAct, JSONObject invTrDto) throws JSONException {

		TbInvInv invInv = new TbInvInv();
		invInv.setWh_id(invTrDto.getString("tr_in_wh_id"));
		invInv.setMat_no(invTrAct.getString("mat_NO"));
		invInv.setInv_qty(0);
		invInv.setStd_qty(0);
		invInv.setWrd_qty(0);
		invInv.setWsp_qty(0);
		invInv.setRd_qty(0);
		invInv.setRj_qty(0);
		invInv.setBooking_qty(0);
		invInv.setCr_user(getLoginUser().getUsername());
		invInv.setCr_time(new Date());
		invInv.setMd_user(getLoginUser().getUsername());
		invInv.setMd_time(new Date());

		return invInv;
	}
}
