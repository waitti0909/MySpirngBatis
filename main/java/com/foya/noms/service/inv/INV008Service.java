package com.foya.noms.service.inv;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hornetq.utils.json.JSONArray;
import org.hornetq.utils.json.JSONException;
import org.hornetq.utils.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foya.dao.mybatis.model.TbAuthMenu;
import com.foya.dao.mybatis.model.TbAuthMenuExample;
import com.foya.dao.mybatis.model.TbInvOnhand;
import com.foya.dao.mybatis.model.TbInvSrl;
import com.foya.dao.mybatis.model.TbInvTr;
import com.foya.dao.mybatis.model.TbInvTrAct;
import com.foya.dao.mybatis.model.TbInvTxn;
import com.foya.exception.NomsException;
import com.foya.noms.dao.inv.INV008Dao;
import com.foya.noms.dto.inv.TbInvTrActCompleteDTO;
import com.foya.noms.dto.inv.TbInvTrCompleteDTO;
import com.foya.noms.dto.inv.TbInvTrDtlCompleteDTO;
import com.foya.noms.enums.InvEnumCommon;
import com.foya.noms.enums.InvEnumCommon.Allocationstatus;
import com.foya.noms.service.BaseService;
import com.foya.noms.util.BaseJasonObject;

@Service
public class INV008Service extends BaseService {

	@Autowired
	private INV008Dao inv008Dao;

	/**
	 * 取得調撥主檔By日期
	 * 
	 * @param userNo
	 *            登入使用者
	 * @param strDemandDate
	 *            需求日期 (開始)
	 * @param endDemandDate
	 *            需求日期 (結束)
	 * @param applyNumber
	 *            調撥單號
	 * @param outDepot
	 *            調出倉庫
	 * @param inDepot
	 *            調入倉庫
	 * @param demandDept
	 *            需求單位
	 * @param applyDept
	 *            申請單位
	 * @param applicant
	 *            申請人
	 * @param list
	 *            調撥進度
	 * @param domain
	 *            domain
	 * @param depotIdList
	 *            倉庫ID_List
	 * @param depot
	 *            調出倉或者調入倉
	 * @return List<TbInvTr>
	 */
	public List<TbInvTrCompleteDTO> getInvTrDataPageByDate(String applyDateStr, String applyDateEnd, String outWhId,
			ArrayList<String> wareHouseIdList, String inWhId, String trNo, String needDept, String applyDept, String applicant, Object[] list,
			String wareHouseType) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("applyDateStr", applyDateStr);
		map.put("applyDateEnd", applyDateEnd);
		map.put("outWhId", outWhId);
		map.put("wareHouseIdList", wareHouseIdList);
		map.put("inWhId", inWhId);
		map.put("trNo", trNo);
		map.put("needDept", needDept);
		map.put("applyDept", applyDept);
		map.put("applicant", applicant);
		if (wareHouseType != null) {
			map.put("wareHouseType", wareHouseType.toLowerCase());
		} else {
			map.put("wareHouseType", null);
		}

		list = list == null ? new Object[0] : list;

		for (Allocationstatus status : Allocationstatus.values()) {
			if (status.getCode() > 0) {
				for (Object value : list) {
					String code = value != null ? String.valueOf(value) : "";
					if (code != null && code.trim() != "" && status.getCode() == Integer.parseInt(code)) {
						map.put(status.getName(), true);
						break;
					} else {
						map.put(status.getName(), false);
					}
				}
			}

		}

		return inv008Dao.getInvTrDataDtoByDate(map);
	}

	public List<TbInvTrCompleteDTO> getInvTrDataPageToDo(String applyDateStr, String applyDateEnd, String outWhId, ArrayList<String> wareHouseIdList,
			String inWhId, String trNo, String needDept, String applyDept, String applicant, Object[] list, String wareHouseType) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("applyDateStr", applyDateStr);
		map.put("applyDateEnd", applyDateEnd);
		map.put("outWhId", outWhId);
		map.put("wareHouseIdList", wareHouseIdList);
		map.put("inWhId", inWhId);
		map.put("trNo", trNo);
		map.put("needDept", needDept);
		map.put("applyDept", applyDept);
		map.put("applicant", applicant);
		if (wareHouseType != null) {
			map.put("wareHouseType", wareHouseType.toLowerCase());
		} else {
			map.put("wareHouseType", null);
		}

		list = list == null ? new Object[0] : list;

		for (Allocationstatus status : Allocationstatus.values()) {
			if (status.getCode() > 0) {
				for (Object value : list) {
					String code = value != null ? String.valueOf(value) : "";
					if (code != null && code.trim() != "" && status.getCode() == Integer.parseInt(code)) {
						map.put(status.getName(), true);
						break;
					} else {
						map.put(status.getName(), false);
					}
				}
			}

		}

		return inv008Dao.getInvTrDataDtoForToDo(map);
	}

	// /**
	// * 取得調撥主檔By日期
	// *
	// * @param userNo
	// * 登入使用者
	// * @param strDemandDate
	// * 需求日期 (開始)
	// * @param endDemandDate
	// * 需求日期 (結束)
	// * @param applyNumber
	// * 調撥單號
	// * @param outDepot
	// * 調出倉庫
	// * @param inDepot
	// * 調入倉庫
	// * @param demandDept
	// * 需求單位
	// * @param applyDept
	// * 申請單位
	// * @param applicant
	// * 申請人
	// * @param list
	// * 調撥進度
	// * @param domain
	// * domain
	// * @param depotIdList
	// * 倉庫ID_List
	// * @param depot
	// * 調出倉或者調入倉
	// * @return List<TbInvTr>
	// */
	// public List<TbInvTrCompleteDTO> getInvTrDataDate(String userNo, Date
	// strDemandDate, Date endDemandDate, String applyNumber, String outDepot,
	// String inDepot, String demandDept, String applyDept, String applicant,
	// Object[] list, String domain, ArrayList<String> depotIdList,
	// String depot) {
	//
	// // String trNo = request.getParameter("trNo");
	// // String domain = request.getParameter("domain");
	// // String outWhId = request.getParameter("outWhId");
	// // String inWhId = request.getParameter("inWhId");
	// // String needDept = request.getParameter("needDept");
	// // String applyDept = request.getParameter("applyDept");
	// // String applyDateStr = request.getParameter("applyDateStr");
	// // String applyDateEnd = request.getParameter("applyDateEnd");
	// // String applicant = request.getParameter("applicant");
	// // String applyState = request.getParameter("applyState");
	//
	// HashMap<String, Object> map = new HashMap<String, Object>();
	// map.put("userNo", userNo);
	// map.put("strDemandDate", strDemandDate);
	// map.put("endDemandDate", endDemandDate);
	// map.put("applyNumber", applyNumber);
	// map.put("outDepot", outDepot);
	// map.put("inDepot", inDepot);
	// map.put("demandDept", demandDept);
	// map.put("applyDept", applyDept);
	// map.put("applicant", applicant);
	// map.put("domain", domain);
	// map.put("depotIdList", depotIdList);
	// map.put("depot", depot);
	//
	// list = list == null ? new Object[0] : list;
	//
	// for (Allocationstatus status : Allocationstatus.values()) {
	// if (status.getCode() > 0) {
	// for (Object value : list) {
	// String code = value != null ? String.valueOf(value) : "";
	// if (code != null && code.trim() != "" && status.getCode() ==
	// Integer.parseInt(code)) {
	// map.put(status.getName(), true);
	// break;
	// } else {
	// map.put(status.getName(), false);
	// }
	// }
	// }
	//
	// }
	//
	// return inv008Dao.getInvTrDataDtoByDate(map);
	// }

	/**
	 * 查詢調撥明細檔
	 * 
	 * @param trNo
	 *            調撥申請單號
	 * @param matNo
	 *            料號代碼
	 * @param matStatus
	 *            物料狀態
	 * @return List<TbInvTrDtlCompleteDTO>
	 */
	public List<TbInvTrDtlCompleteDTO> getInvTrDtlDTo(String trNo, String whId) {

		// HashMap<String, Object> map = new HashMap<String, Object>();
		// map.put("trNo", trNo);
		// map.put("matNo", matNo);
		// map.put("matStatus", matStatus);

		return inv008Dao.getInvTrDtlDTo(trNo, whId);
	}

	/**
	 * 查詢調撥數量
	 * 
	 * @param trNo
	 *            調撥申請單號
	 * @param matNo
	 *            料號代碼
	 * @param srlNo
	 *            序號控管ID
	 * @param trIn
	 *            是否調入(0:否，1:是)
	 * @param trDtlNo
	 *            trDtlNo
	 * @return List<TbInvTrAct>
	 */
	public List<TbInvTrActCompleteDTO> getInvTrActDtoData(String trNo, String matNo, int srlNo, String trIn, long trDtlNo) {

		return inv008Dao.getInvTrActDtoData(trNo, matNo, srlNo, trIn, trDtlNo);
	}

	// /**
	// * 更新調撥申請單
	// *
	// * @param invTr
	// * TbInvTr
	// * @return Int
	// */
	// @Transactional(rollbackFor = Exception.class)
	// public boolean updateTbInvTrByKey(TbInvTr invTr) {
	//
	// try {
	//
	// return inv008Dao.updateTbInvTrByKey(invTr);
	// } catch (Exception exception) {
	// return false;
	// }
	// }

	/**
	 * 
	 * @param actNo
	 *            調撥單號
	 * @param actType
	 *            異動單據類型
	 * @param matNo
	 *            料號
	 * @param whId
	 *            倉庫代碼
	 * @param bookingQty
	 *            Booking數量
	 * @return boolean
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean updateBookingQty(String actNo, String actType, String matNo, String whId, int bookingQty) {

		try {

			return inv008Dao.updateBookingQty(actNo, actType, matNo, whId, bookingQty);
		} catch (Exception exception) {
			return false;
		}
	}

	// /**
	// *
	// * @param stockType
	// * 物料狀態
	// * @param matNo
	// * 料號
	// * @param whId
	// * 倉庫代碼
	// * @param stdQty
	// * StdQty 數量
	// * @param wrdQty
	// * wrdQty 數量
	// * @param wspQty
	// * wspQty 數量
	// * @param bookingQty
	// * Booking 數量
	// * @return boolean
	// */
	// @Transactional(rollbackFor = Exception.class)
	// public boolean updateInvInvStock(int stockType, String matNo, String
	// whId, int stdQty, int wrdQty, int wspQty, int bookingQty) {
	//
	// try {
	//
	// return inv008Dao.updateStockByExport(stockType, matNo, whId, stdQty,
	// wrdQty, wspQty, bookingQty);
	// } catch (Exception exception) {
	// return false;
	// }
	// }

	/**
	 * 新增庫存主黨
	 * 
	 * @param invInv
	 *            invInv
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean insertInvTxn(TbInvTxn invTxn) {

		try {

			return inv008Dao.insertInvTxn(invTxn) == 0 ? false : true;
		} catch (Exception exception) {
			return false;
		}
	}

	/**
	 * 新增調撥紀錄檔
	 * 
	 * @param invTrAct
	 *            TbInvTrAct
	 * @return int
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean insertTrAct(TbInvTrAct invTrAct) {

		try {

			return inv008Dao.insertTrAct(invTrAct) == 0 ? false : true;
		} catch (Exception exception) {
			return false;
		}
	}

	/**
	 * 新增在途檔
	 * 
	 * @param invTrAct
	 *            TbInvTrAct
	 * @return int
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean insertOnHand(TbInvOnhand onHand) {

		try {

			return inv008Dao.insertOnHand(onHand) == 0 ? false : true;
		} catch (Exception exception) {
			return false;
		}
	}

	/**
	 * 取得調撥記錄檔
	 * 
	 * @param trNo
	 *            單據編號
	 * @param matNo
	 *            料號
	 * 
	 * @param srlNo
	 *            序號編號
	 * @param trIn
	 *            是否調入
	 * @param trDtlNo
	 *            trDtl序號
	 * @return List<TbInvTrAct>
	 * 
	 */
	public List<TbInvTrActCompleteDTO> getExportHistory(String trNo, String matNo, String trIn, int trDtlNo, int outActNo) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("trNo", trNo);
		map.put("matNo", matNo);
		map.put("trIn", trIn);
		map.put("trDtlNo", trDtlNo);
		map.put("outActNo", outActNo);

		List<TbInvTrActCompleteDTO> list = inv008Dao.getExportHistory(map);

		return list;
	}

	/**
	 * 執行調出修改
	 * 
	 * @param invTrDtlDtoList
	 *            List<TbInvTrDtlCompleteDTO>
	 * @param trDto
	 *            TbInvTrCompleteDTO
	 * @param memo
	 *            備註
	 * @param userNo
	 *            登入者
	 * 
	 * @return boolean
	 * @throws JSONException
	 */
	@Transactional(rollbackFor = { NomsException.class, JSONException.class })
	public BaseJasonObject<JSONObject> executionExportWork(JSONObject invTrDto, JSONArray invTrDtlDtoList) throws NomsException, JSONException {

		Date nowTime = new Date();

		int sum = 0;

		int befortExportSum = 0;

		int presentExportSum = 0;

		for (int i = 0; i < invTrDtlDtoList.length(); i++) {

			JSONObject invTrDtl = invTrDtlDtoList.getJSONObject(i);

			// 加總此單號總共可調出多少數量
			sum += invTrDtl.getInt("app_qty");

			// 加總此單號已經調出多少數量
			befortExportSum += invTrDtl.getInt("trQty");

			// 有調出才做動作
			if (invTrDtl.getInt("exportNumber") > 0) {

				// 加總此單號本次調出數量
				presentExportSum += invTrDtl.getInt("exportNumber");

				switch (InvEnumCommon.ProductStatus.toSource(invTrDtl.getInt("mat_status"))) {
				case NORMAL:
					// 可用品
					inv008Dao.updateBookingQty(invTrDtl.getString("tr_no"), "2", invTrDtl.getString("mat_no"), invTrDto.getString("tr_out_wh_id"),
							invTrDtl.getInt("exportNumber"));
					inv008Dao.updateStockByExport(1, invTrDtl.getString("mat_no"), invTrDto.getString("tr_out_wh_id"),
							invTrDtl.getInt("exportNumber"));
					break;
				case PENDING_INVALID:
					// 待報廢
					inv008Dao.updateStockByExport(3, invTrDtl.getString("mat_no"), invTrDto.getString("tr_out_wh_id"),
							invTrDtl.getInt("exportNumber"));
					break;
				case PENDING_REPAIR:
					// 待送修
					inv008Dao.updateStockByExport(2, invTrDtl.getString("mat_no"), invTrDto.getString("tr_out_wh_id"),
							invTrDtl.getInt("exportNumber"));
					break;
				default:
					break;
				}

				JSONArray selectVenSnList = invTrDtl.getJSONArray("selectVenSnStrList");

				// 有序號檔 (序號件)
				if (invTrDtl.getString("ctrlType").toUpperCase().equals("S")) {

					for (int x = 0; x < selectVenSnList.length(); x++) {

						// 取得序號檔
						TbInvSrl srlObj = getJsonSrlData(selectVenSnList.getString(x).trim());

						// 新增 TB_INV_TXN
						TbInvTxn tbInvTxn = getIbInvTxn(invTrDto, invTrDtl);
						tbInvTxn.setFa_no(srlObj.getFa_no());
						tbInvTxn.setSrl_no(srlObj.getSrl_no());
						tbInvTxn.setQty(-1);
						inv008Dao.insertInvTxn(tbInvTxn);

						// 新增 TB_INV_TR_ACT
						TbInvTrAct trAct = getTbInvTrAct(invTrDto, invTrDtl);
						trAct.setSRL_NO(srlObj.getSrl_no());
						trAct.setTR_QTY(1);
						trAct.setTR_OUT_DATE(nowTime);
						trAct.setCR_TIME(nowTime);
						inv008Dao.insertTrAct(trAct);

						// 新增 TB_INV_ONHAND
						TbInvOnhand invOnhand = getTbInvOnhand(invTrDto, invTrDtl);
						invOnhand.setFa_no(srlObj.getFa_no());
						invOnhand.setSrl_no(srlObj.getSrl_no());
						invOnhand.setQty(1);
						invOnhand.setOnhand_qty(1);
						inv008Dao.insertOnHand(invOnhand);

						// 更新INV_TR
						TbInvSrl invSrl = new TbInvSrl();
						invSrl.setWh_id("ON_HAND");
						invSrl.setMd_time(new Date());
						invSrl.setMd_user(getLoginUser().getUsername());
						inv008Dao.updateTbInvSrl(invSrl, srlObj.getSrl_no());
					}
				} else {
					// 數量件

					// 新增 TB_INV_TXN
					inv008Dao.insertInvTxn(getIbInvTxn(invTrDto, invTrDtl));

					// 新增 TB_INV_TR_ACT
					TbInvTrAct trAct = getTbInvTrAct(invTrDto, invTrDtl);
					trAct.setCR_TIME(nowTime);
					trAct.setTR_OUT_DATE(nowTime);
					inv008Dao.insertTrAct(trAct);

					// 新增 TB_INV_ONHAND
					inv008Dao.insertOnHand(getTbInvOnhand(invTrDto, invTrDtl));
				}

			}

		}

		// 代表尚未輸入任何數量
		if (presentExportSum == 0) {
			return new BaseJasonObject<JSONObject>(false, "請輸入調出量。");
		}

		// 更新INV_TR
		TbInvTr invTr = new TbInvTr();
		invTr.setStatus(sum == (befortExportSum + presentExportSum) ? (byte) 5 : (byte) 4);
		invTr.setMd_time(new Date());
		invTr.setMd_user(getLoginUser().getUsername());
		inv008Dao.updateTbInvTr(invTr, invTrDto.getString("tr_no"));

		return new BaseJasonObject<JSONObject>(true, "調出完成。");
	}

	/**
	 * 取得序號
	 * 
	 * @throws JSONException
	 */
	private TbInvSrl getJsonSrlData(String venSn) throws JSONException {
		
		
		List<TbInvSrl> srlList = inv008Dao.getInvTrSrl(venSn);

		System.out.println("venSn ? "+ venSn +" get is Size ? "+srlList.size());

		return srlList.size() > 0 ? srlList.get(0) : null;
		// for (int i = 0; i < venSnList.length(); i++) {
		//
		// JSONObject srlObj = venSnList.getJSONObject(i);
		//
		// if (srlObj.getString("ven_sn").equals(venSn)) {
		// return srlObj;
		// }
		// }

//		return null;
	}

	/**
	 * 取得 InvTxn
	 * 
	 * @param trDtlDto
	 *            TbInvTrDtlCompleteDTO
	 * @param trDto
	 *            TbInvTrCompleteDTO
	 * @param invSrl
	 *            TbInvSrl
	 * @param inputVal
	 *            調撥數量
	 * @param selectVal
	 *            貼標號碼
	 * @return TbInvTxn
	 * @throws JSONException
	 */
	private TbInvTxn getIbInvTxn(JSONObject invTrDto, JSONObject invTrDtl) throws JSONException {

		TbInvTxn invTxn = new TbInvTxn();
		invTxn.setTxn_type((byte) InvEnumCommon.DepotBusinessStatus.EXIT.getCode());
		invTxn.setTxn_no(invTrDto.getString("tr_no"));
		invTxn.setWh_id(invTrDto.getString("tr_out_wh_id"));
		invTxn.setMat_no(invTrDtl.getString("mat_no"));
		invTxn.setMat_status((byte) invTrDtl.getInt("mat_status"));
		invTxn.setQty((0 - invTrDtl.getInt("exportNumber")));
		invTxn.setTr_dtl_no(invTrDtl.getLong("tr_dtl_no"));
		invTxn.setCr_time(new Date());
		invTxn.setCr_user(getLoginUser().getUsername());

		return invTxn;
	}

	/**
	 * 取得 TbInvTrAct
	 * 
	 * @throws JSONException
	 */
	private TbInvTrAct getTbInvTrAct(JSONObject invTrDto, JSONObject invTrDtl) throws JSONException {

		TbInvTrAct trAct = new TbInvTrAct();
		trAct.setTR_NO(invTrDto.getString("tr_no"));
		trAct.setMAT_NO(invTrDtl.getString("mat_no"));
		trAct.setTR_QTY(invTrDtl.getInt("exportNumber"));
		trAct.setTR_OUT_WH_ID(invTrDto.getString("tr_out_wh_id"));
		trAct.setTR_OUT_USER(getLoginUser().getUsername());
		trAct.setTR_OUT_DATE(new Date());
		trAct.setIF_TR_IN(false);
		trAct.setTR_DTL_NO(invTrDtl.getLong("tr_dtl_no"));
		trAct.setCR_TIME(new Date());
		trAct.setCR_USER(getLoginUser().getUsername());
		trAct.setMD_TIME(new Date());
		trAct.setMD_USER(getLoginUser().getUsername());

		return trAct;
	}

	/**
	 * 取得 TbInvOnhand
	 * 
	 * @param trDtlDto
	 *            TbInvTrDtlCompleteDTO
	 * @param trDto
	 *            TbInvTrCompleteDTO
	 * @param invSrl
	 *            TbInvSrl
	 * @param inputVal
	 *            調撥數量
	 * @param selectVal
	 *            貼標號碼
	 * @return TbInvOnhand
	 * @throws JSONException
	 */
	private TbInvOnhand getTbInvOnhand(JSONObject invTrDto, JSONObject invTrDtl) throws JSONException {

		TbInvOnhand invOnhand = new TbInvOnhand();
		invOnhand.setTxn_type((byte) InvEnumCommon.OnHandStatus.EXIT.getCode());
		invOnhand.setTxn_no(invTrDto.getString("tr_no"));
		invOnhand.setWh_id(invTrDto.getString("tr_out_wh_id"));
		invOnhand.setMat_no(invTrDtl.getString("mat_no"));
		invOnhand.setMat_status((byte) invTrDtl.getInt("mat_status"));
		invOnhand.setQty(invTrDtl.getInt("exportNumber"));
		invOnhand.setOnhand_qty(invTrDtl.getInt("exportNumber"));
		invOnhand.setTr_dtl_no(invTrDtl.getLong("tr_dtl_no"));
		invOnhand.setCr_time(new Date());
		invOnhand.setCr_user(getLoginUser().getUsername());
		invOnhand.setMd_time(new Date());
		invOnhand.setMd_user(getLoginUser().getUsername());

		return invOnhand;
	}

	/**
	 * 搜尋貼標號碼(序號檔)
	 * 
	 * @param matNo
	 *            料號
	 * @param whId
	 *            調出倉ID
	 * @return List<TbInvSrl>
	 */
	public List<TbInvSrl> getTbInvSrlList(String matNo, String whId, byte matStatus) {

		return inv008Dao.getTbInvSrlList(matNo, whId, matStatus);
	}

	public List<TbAuthMenu> getMenuId(String menuCode){
		
		return inv008Dao.getMenuId(menuCode);
		
	}
}
