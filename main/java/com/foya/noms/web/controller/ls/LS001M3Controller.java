package com.foya.noms.web.controller.ls;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.hornetq.utils.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foya.noms.enums.LsEnumCommon.AppStatusEnum;
import com.foya.noms.enums.LsEnumCommon.LsTypeEnum;
import com.foya.noms.enums.LsPaymentItem;
import com.foya.dao.mybatis.model.TbLsApp;
import com.foya.dao.mybatis.model.TbLsAppForm;
import com.foya.dao.mybatis.model.TbLsLocation;
import com.foya.dao.mybatis.model.TbLsMain;
import com.foya.dao.mybatis.model.TbLsMainAdded;
import com.foya.dao.mybatis.model.TbOrgDomain;
import com.foya.dao.mybatis.model.TbSysLookup;
import com.foya.noms.dto.ls.LeaseLocationDTO;
import com.foya.noms.dto.ls.LessorChDTO;
import com.foya.noms.dto.ls.TbLsLocElecLocPaymentDTO;
import com.foya.noms.dto.ls.TbLsLocPaymentDTO;
import com.foya.noms.dto.ls.TbLsLocationAddedDTO;
import com.foya.noms.dto.ls.TbLsRewardAddedDTO;
import com.foya.noms.dto.ls.TbLsRewardDTO;
import com.foya.noms.dto.pay.TbLsSiteDTO;
import com.foya.noms.dto.st.SiteDTO;
import com.foya.noms.enums.SiteStatus;
import com.foya.noms.service.ls.LS001M3Service;
import com.foya.noms.service.ls.LsCommonService;
import com.foya.noms.service.system.LookupService;
import com.foya.noms.util.BaseJasonObject;
import com.foya.noms.web.controller.BaseController;

@Controller
public class LS001M3Controller extends BaseController {

	@Autowired
	private LS001M3Service lS001M3ervice;
	@Autowired
	private LsCommonService lsCommonService;

	@Autowired
	private LookupService lookupService;

	
	
	/**
	 * 查詢合約站台資訊
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/ls/LS001M3/searchTbLsSiteByLocId")
	@ResponseBody
	public BaseJasonObject<Map<String, Object>> searchTbLsSiteByLocId(HttpServletRequest request, Map<String, Object> modelMap) {
		Map<String, Object> map = new HashMap<String, Object>();

		List<TbLsSiteDTO> dtoList = lS001M3ervice.getLsSiteByLocId(request.getParameter("lsNo"), request.getParameter("locId"));
		List<String> siteId = new ArrayList<String>();
		for (TbLsSiteDTO lsSiteDTO : dtoList)
		{
			siteId.add(lsSiteDTO.getSITE_ID());
		}

		List<SiteDTO> siteMainList = lS001M3ervice.getSiteMainByLocationId(request.getParameter("locId"), SiteStatus.getAvailableSiteStatus(), siteId);
		map.put("dtoList", dtoList);
		map.put("siteMainList", siteMainList);
		// data.row["showDeptName"]
		return new BaseJasonObject<Map<String, Object>>(map, AJAX_SUCCESS);
	}

	/**
	 * 取得站點資訊 By合約
	 * 
	 * @param lsNo
	 *            合約編號
	 * @param domain
	 *            區域
	 * @return
	 */
	@RequestMapping(value = "/ls/LS001M3/searchTbLsLocation")
	@ResponseBody
	public BaseJasonObject<LeaseLocationDTO> searchLsLocation(String lsNo, String domain) {
		List<LeaseLocationDTO> dtoList = lS001M3ervice.getTbLsLocation(lsNo, domain);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		for (LeaseLocationDTO leaseLocationDTO : dtoList)
		{
			if (leaseLocationDTO.getPAY_BEGIN_DATE() != null && !leaseLocationDTO.getPAY_BEGIN_DATE().equals(""))
			{
				leaseLocationDTO.setLsPayBeginDate(sdf.format(leaseLocationDTO.getPAY_BEGIN_DATE()));
			}
			else
			{
				leaseLocationDTO.setLsPayBeginDate("");
			}

			if (leaseLocationDTO.getLS_E_DATE() != null && !leaseLocationDTO.getLS_E_DATE().equals(""))
			{
				leaseLocationDTO.setLsEndDate(sdf.format(leaseLocationDTO.getLS_E_DATE()));
			}
			else
			{
				leaseLocationDTO.setLsEndDate("");
			}
			if (leaseLocationDTO.getEFF_DATE() != null && !leaseLocationDTO.getEFF_DATE().equals(""))
			{
				leaseLocationDTO.setLsEffDate(sdf.format(leaseLocationDTO.getEFF_DATE()));
			}
			else
			{
				leaseLocationDTO.setLsEffDate("");
			}
		}
		return new BaseJasonObject<LeaseLocationDTO>(dtoList, "", "");

	}

	/**
	 * 取得站點資訊 By合約，申請單流水號，狀態
	 * 
	 * @param lsNo
	 *            合約編號
	 * @param appSeq
	 *            申請單流水號
	 * @param addState
	 *            狀態
	 * @return
	 */
	@RequestMapping(value = "/ls/LS001M3/searchTbLsLocationAdded")
	@ResponseBody
	public BaseJasonObject<TbLsLocationAddedDTO> searchTbLsLocationAddedByAppSeq(String lsNo, String appSeq, String addState) {
		List<TbLsLocationAddedDTO> dtoList = lS001M3ervice.searchLsLocationAddedByAppSeq(lsNo, appSeq, addState);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		for (TbLsLocationAddedDTO lsLocationAddedDTO : dtoList)
		{
			if (lsLocationAddedDTO.getPAY_BEGIN_DATE() != null && !lsLocationAddedDTO.getPAY_BEGIN_DATE().equals(""))
			{
				lsLocationAddedDTO.setLsPayBeginDate(sdf.format(lsLocationAddedDTO.getPAY_BEGIN_DATE()));
			}
			else
			{
				lsLocationAddedDTO.setLsPayBeginDate("");
			}

			if (lsLocationAddedDTO.getLS_E_DATE() != null && !lsLocationAddedDTO.getLS_E_DATE().equals(""))
			{
				lsLocationAddedDTO.setLsEndDate(sdf.format(lsLocationAddedDTO.getLS_E_DATE()));
			}
			else
			{
				lsLocationAddedDTO.setLsEndDate("");
			}
			if (lsLocationAddedDTO.getEFF_DATE() != null && !lsLocationAddedDTO.getEFF_DATE().equals(""))
			{
				lsLocationAddedDTO.setLsEffDate(sdf.format(lsLocationAddedDTO.getEFF_DATE()));
			}
			else
			{
				lsLocationAddedDTO.setLsEffDate("");
			}
		}
		return new BaseJasonObject<TbLsLocationAddedDTO>(dtoList, "", "");

	}

	/**
	 * 
	 * @param lsNo
	 *            合約編號
	 * @param domain
	 *            區域
	 * @return
	 */
	@RequestMapping(value = "/ls/LS001M3_ElecCh/searchTbLsLocation")
	@ResponseBody
	public BaseJasonObject<LeaseLocationDTO> searchTbLsLocation(String lsNo, String domain) {
		return new BaseJasonObject<LeaseLocationDTO>(lS001M3ervice.getTbLsLocation(lsNo, domain), "", "");

	}

	/**
	 * 撈取 區域 資料
	 * 
	 * @param lsNo
	 *            合約編號
	 * @param lsVer
	 *            版次
	 * @return
	 */
	@RequestMapping(value = "/ls/LS001M3_ElecCh/searchDomain")
	@ResponseBody
	public BaseJasonObject<TbOrgDomain> searchDomain(String lsNo, String lsVer) {
		return new BaseJasonObject<TbOrgDomain>(lsCommonService.getDomainValue(lsNo, lsVer), "", "");

	}

	/**
	 * 撈取 TbLsLocElec 與 TbLsLocPaymen 資料
	 * 
	 * @param lsNo
	 *            合約編號
	 * @param locId
	 *            站點編號
	 * @param appSql
	 *            申請流水號
	 * @param appMode
	 *            申請狀態
	 * @param item
	 *            項目
	 * @return
	 */
	@RequestMapping(value = "/ls/LS001M3_ElecCh/searchTbLsLocElecTbLsLocPaymen")
	@ResponseBody
	public BaseJasonObject<TbLsLocElecLocPaymentDTO> searchTbLsLocElecTbLsLocPaymen(String lsNo, String locId, String appMode,String appSql, String item) {
		return new BaseJasonObject<TbLsLocElecLocPaymentDTO>(lS001M3ervice.searchTbLsLocElecTbLsLocPaymen(lsNo, locId, appMode,appSql, item), "");
	}

	/**
	 * 查詢
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/ls/LS001M3/searchLsReward")
	@ResponseBody
	public BaseJasonObject<TbLsRewardDTO> searchLsReward(HttpServletRequest request, Map<String, Object> modelMap) {
		List<TbLsRewardDTO> lsRewardList = lS001M3ervice.getLsRewardByLsNoMaxVer(request.getParameter("lsNo"), "2");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		
		for(int i=0;i<lsRewardList.size();i++)
		{
			if (lsRewardList.get(i).getGET_DATE() != null && !lsRewardList.get(i).getGET_DATE().equals(""))
			{
				lsRewardList.get(i).setGetDate(sdf.format(lsRewardList.get(i).getGET_DATE()));
			}
			else
			{
				lsRewardList.get(i).setGetDate("");
			}
		}
		return new BaseJasonObject<>(lsRewardList, AJAX_SUCCESS, AJAX_EMPTY);
	}

	/**
	 * 套表格式搜尋
	 * 
	 * @param AddItem
	 *            項目值
	 * @return
	 */
	@RequestMapping(value = "/ls/LS001M3_ElecCh/searchLsAppFORM")
	@ResponseBody
	public BaseJasonObject<TbLsAppForm> searchLsAppFORM(String AddItem) {
		return new BaseJasonObject<TbLsAppForm>(lS001M3ervice.searchLsAppFORM(AddItem), "", "");
	}

	/**
	 * 儲存草稿
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ls/LS001M3_ElecCh/saveLsApp")
	@ResponseBody
	public BaseJasonObject<TbLsAppForm> saveLsApp(HttpServletRequest request) {
		String editAppMode = request.getParameter("editAppMode");//申請狀態
		String editAppSql = request.getParameter("editAppSql");// 申請流水號
		String editElecLocId = request.getParameter("editElecLocId");// 站點編號
		String editLsNo = request.getParameter("editLsNo");// 合約編號
		String editSelType = request.getParameter("editSelType");// 類別
		String editItemSEL = request.getParameter("editItemSEL");// 項目
		String editTbStyleSEL = request.getParameter("editTbStyleSEL");// 套表格式
		String[] newElecEndDegrees = request.getParameterValues("newElecEndDegree");// 新期末度數
		String[] editNewElecs = request.getParameterValues("editNewElec");// 新用電
		String[] editOldElecs = request.getParameterValues("editOldElec");// 舊用電
		String[] editFlags = request.getParameterValues("editFlag");// 修改記錄
		
		String[] editOldVers = request.getParameterValues("editOldVer");// 版次
		String[] oldElecEndDegrees = request.getParameterValues("oldElecEndDegree");// 舊期末度數

		String[] editOldPayAddeds = request.getParameterValues("editOldPayAdded");// 舊付款資訊
		String[] editNewPayAddeds = request.getParameterValues("editNewPayAdded");// 新付款資訊
		String[] editPayAlocs = request.getParameterValues("editPayAloc");// 分攤比率
		String[] editPayAmts = request.getParameterValues("editPayAmt");// 金額
		try {
			if (StringUtils.equals(editItemSEL, "電表歸零")) {
				lS001M3ervice.saveElecCh(editLsNo, editSelType, editItemSEL, editTbStyleSEL, editElecLocId, editOldVers, editOldElecs, editNewElecs, oldElecEndDegrees, newElecEndDegrees);
			} else {
				if (StringUtils.equals("NEW", editAppMode)) {
					lS001M3ervice.insertLsApp(editLsNo, editSelType, editItemSEL, editTbStyleSEL, editElecLocId, editOldVers, editOldElecs, editNewElecs, oldElecEndDegrees, newElecEndDegrees, editOldPayAddeds, editNewPayAddeds, editPayAlocs, editPayAmts,editFlags);
				} else {
					lS001M3ervice.updateLsApp(editAppSql, editLsNo, editTbStyleSEL, editElecLocId, editOldVers, editOldElecs, editNewElecs, oldElecEndDegrees, newElecEndDegrees, editOldPayAddeds, editNewPayAddeds, editPayAlocs, editPayAmts,editFlags);
				}
			}
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			return new BaseJasonObject<>(false, StringUtils.equals(editAppMode, "NEW") ? "新增失敗":"修改失敗");
		}
		return new BaseJasonObject<>(true, StringUtils.equals(editAppMode, "NEW") ? "新增成功":"修改成功");
	}

	/**
	 * 增補協議-租金起算日異動-存檔
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ls/LS001M3/saveRentDate")
	@ResponseBody
	public BaseJasonObject<TbLsApp> saveRentDate(HttpServletRequest request) {
		String msg = "";
		try {
			String lsNo = request.getParameter("lsNoRent");
			String lsVer = request.getParameter("lsVerRent");
			String appSeq = request.getParameter("appSeqRent");
			String appMode = request.getParameter("appModeRent");
			String selType = request.getParameter("selTypeRent");
			String itemSEL = request.getParameter("itemSELRent");

			if (appMode.equals("NEW")) // 當新增時
			{
				// 取獨申請檔
				TbLsApp tbLsapp = this.getTbLsApp(lsNo, lsVer, selType, itemSEL);

				// 取得明細檔資料
				Map<String, String[]> map = request.getParameterMap();
				String[] locationId = map.get("location_ID");

				// 寫入申請檔和相關資料
				String lsAppSeq = lS001M3ervice.addRentDate(lsNo, tbLsapp, map, locationId);
				msg = getMessageDetail("msg.add.success");

			} else // 當草稿編輯時
			{
				// 取得明細檔資料
				Map<String, String[]> map = request.getParameterMap();
				String[] locationId = map.get("location_ID");

				// /更新申請檔和寫入相關資料
				String lsAppSeq = lS001M3ervice.updateRentDate(appSeq, lsNo, map, locationId);
				msg = getMessageDetail("msg.update.success");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(ExceptionUtils.getFullStackTrace(e));
			return new BaseJasonObject<>(false, e.getMessage());
		}

		return new BaseJasonObject<>(true, msg);
	}

	/**
	 * 增補協議-資料異動-合約手機回饋-存檔
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ls/LS001M3/savePhoneReward")
	@ResponseBody
	public BaseJasonObject<TbLsApp> savePhoneReward(HttpServletRequest request) {
		String msg = "";
		try {
			String lsNo = request.getParameter("lsNoReward");
			String lsVer = request.getParameter("lsVerReward");
			String appSeq = request.getParameter("appSeqReward");
			String appMode = request.getParameter("appModeReward");
			String selType = request.getParameter("selTypeReward");
			String itemSEL = request.getParameter("itemSELReward");

			if (appMode.equals("NEW")) // 當新增時
			{
				// 取獨申請檔
				TbLsApp tbLsapp = this.getTbLsApp(lsNo, lsVer, selType, itemSEL);

				// 取得明細檔資料
				Map<String, String[]> map = request.getParameterMap();
				String[] orewardId = map.get("oreward_ID");
				String[] rewardId = map.get("REWARD_ID");

				// 寫入申請檔和相關資料
				String lsAcppSeq = lS001M3ervice.addPhoneReward(lsNo, tbLsapp, map, orewardId, rewardId);
				msg = getMessageDetail("msg.add.success");

			} else // 當草稿編輯時
			{
				// 取得明細檔資料
				Map<String, String[]> map = request.getParameterMap();
				String[] ver = map.get("ols_VER");
				String[] rewardId = map.get("REWARD_ID");
				int lsver = 0;
				if (ver.length > 0)
				{
					lsver = Integer.valueOf(ver[0]);
				}
				// 更新申請檔和寫入相關資料
				String lsAppSeq = lS001M3ervice.updatePhoneReward(lsNo, appSeq, lsver, map, rewardId);
				msg = getMessageDetail("msg.update.success");

			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(ExceptionUtils.getFullStackTrace(e));
			return new BaseJasonObject<>(false, e.getMessage());
		}
		return new BaseJasonObject<>(true, msg);
	}

	/**
	 * 取得手機回饋暫存檔 By合約，申請單流水號，狀態
	 * 
	 * @param lsNo
	 * @param appSeq
	 * @param addState
	 * @return
	 */
	@RequestMapping(value = "/ls/LS001M3/searchLsRewardAddedBySeq")
	@ResponseBody
	public BaseJasonObject<TbLsRewardAddedDTO> searchLsRewardAddedBySeq(String lsNo, String appSeq, String addState) {
		List<TbLsRewardAddedDTO> dtoList = lS001M3ervice.searchLsRewardAddedByAppSeq(lsNo, appSeq, addState);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		
		for(int i=0;i<dtoList.size();i++)
		{
			if (dtoList.get(i).getGET_DATE() != null && !dtoList.get(i).getGET_DATE().equals(""))
			{
				dtoList.get(i).setGetDate(sdf.format(dtoList.get(i).getGET_DATE()));
			}
			else{
				dtoList.get(i).setGetDate("");
			}
		}
		
		return new BaseJasonObject<TbLsRewardAddedDTO>(dtoList, "", "");

	}

	/**
	 * 資料異動站台編號異動儲存
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ls/LS001M3/saveSiteIdCh")
	@ResponseBody
	public BaseJasonObject<TbLsApp> saveSiteIdCh(HttpServletRequest request) {
		try {
			String lsNo = request.getParameter("lsNositeIdCh");
			String lsVer = request.getParameter("lsVersiteIdCh");
			String selType = request.getParameter("selTypesiteIdCh");
			String itemSEL = request.getParameter("itemSELsiteIdCh");

			// 取獨申請檔
			TbLsApp tbLsapp = this.getTbLsApp(lsNo, lsVer, selType, itemSEL);

			// 取得明細檔資料
			Map<String, String[]> map = request.getParameterMap();
			String[] siteId = map.get("site_ID");
			String[] locId = map.get("location_ID");
			String[] siteVer = map.get("ls_VER");

			// 寫入申請檔和寫入相關資料

			String lsAppSeq = lS001M3ervice.addSiteIdCh(lsNo, siteVer, locId, tbLsapp, map, siteId);

		} catch (Exception e) {
			e.printStackTrace();
			log.error(ExceptionUtils.getFullStackTrace(e));
			return new BaseJasonObject<>(false, e.getMessage());
		}

		return new BaseJasonObject<>(true, getMessageDetail("msg.add.success"));
	}

	// 組成新增的合約申請檔
	private TbLsApp getTbLsApp(String lsNo, String lsVer, String selType, String itemSEL)
	{
		Date date = new Date();
		TbLsApp tbLsApp = new TbLsApp();
		tbLsApp.setLS_NO(lsNo);
		tbLsApp.setLS_VER(lsVer);
		tbLsApp.setLS_TYPE(LsTypeEnum.ExtraLease.getLsType());
		tbLsApp.setAPP_STATUS(AppStatusEnum.appStatus0.getAppStatus());
		tbLsApp.setADD_TYPE(selType);
		tbLsApp.setADD_ITEM(itemSEL);
		tbLsApp.setAPP_DEPT_ID(this.getLoginUser().getDeptId());
		tbLsApp.setAPP_USER(this.getLoginUser().getUsername());
		tbLsApp.setCR_USER(this.getLoginUser().getUsername());
		tbLsApp.setCR_TIME(date);
		tbLsApp.setMD_USER(this.getLoginUser().getUsername());
		tbLsApp.setMD_TIME(date);

		return tbLsApp;
	}

	/**
	 * 撈取下拉值
	 * 
	 * @param type
	 *            Lookup Type
	 * @return
	 */
	@RequestMapping(value = "/ls/LS001M3/searchTbSysLookup")
	@ResponseBody
	public BaseJasonObject<TbSysLookup> searchTbSysLookup(String type,String item) {
		if(StringUtils.isNotEmpty(item)){
			List<String> codes= new ArrayList<String>();
			if(StringUtils.equals("更改計費方式－一般", item)){
				codes.add("C");
				codes.add("E");
				return new BaseJasonObject<TbSysLookup>(lS001M3ervice.searchTbSysLookup(type,codes), "", "");
			}else if(StringUtils.equals("更改計費方式－實報實銷", item)){
				codes.add("F");
				codes.add("G");
				return new BaseJasonObject<TbSysLookup>(lS001M3ervice.searchTbSysLookup(type,codes), "", "");
			}else{
				return new BaseJasonObject<TbSysLookup>(lS001M3ervice.searchTbSysLookup(type,null), "", "");
			}
		}else{
			return new BaseJasonObject<TbSysLookup>(lS001M3ervice.searchTbSysLookup(type,null), "", "");
		}
	}

	/**
	 * 撈取站點付款明細資料
	 * @param lsNo
	 * 			合約編號
	 * @param locId
	 * 			站點編號
	 * @param paymentItem
	 * 			支付項目
	 * @param appMode
	 * 			新單 或 修改
	 * @param appSeq
	 * 			申請單號
	 * @param mainLsVer
	 * 			合約主檔版次
	 * @return
	 */
	@RequestMapping(value = "/ls/LS001M3_RentCh/searchTbLsLocPaymen")
	@ResponseBody
	public BaseJasonObject<TbLsLocElecLocPaymentDTO> searchTbLsLocPaymen(String lsNo, String locId, String paymentItem, String appMode, String appSeq, String mainLsVer) {
		TbLsLocElecLocPaymentDTO dto = new TbLsLocElecLocPaymentDTO();
		String[] paymentItems =new String[1];
		paymentItems[0] = paymentItem;
		if (StringUtils.equals("NEW", appMode)) {
			dto.setTbLsLocPaymentDTOList(lS001M3ervice.searchTbLsLocPaymen(lsNo, locId, paymentItems));
		} else {
			dto = lS001M3ervice.searchRentChDate(lsNo, locId, appSeq, mainLsVer);
			if(dto.getNewTbLsLocPaymentAddedList().size() == 0 && dto.getOldTbLsLocPaymentAddedList().size() == 0){
				dto.setTbLsLocPaymentDTOList(lS001M3ervice.searchTbLsLocPaymen(lsNo, locId, paymentItems));
			}
		}
		return new BaseJasonObject<TbLsLocElecLocPaymentDTO>(dto, "");
	}
	
	/**
	 * 撈取 TbLsMainAdded 資料
	 * @param lsNo
	 * 			合約編號
	 * @param appSeq
	 * 			申請單號
	 * @param mainLsVer
	 * 			合約主檔版次
	 * @return
	 */
	@RequestMapping(value = "/ls/LS001M3_RentCh/searchTbLsMain")
	@ResponseBody
	public BaseJasonObject<TbLsMainAdded> searchTbLsMain(String lsNo, String appSeq, String mainLsVer){
		return new BaseJasonObject<TbLsMainAdded>(lS001M3ervice.searchTbLsMainAdded(lsNo, appSeq, mainLsVer), "");
	}


	/**
	 * 查詢出租人及付款對象資訊
	 * 
	 * @param lsNo
	 *            合約編號
	 * @param appSeq
	 *            申請流水號
	 * @param appMode
	 *            狀態
	 * @return
	 */
	@RequestMapping(value = "/ls/LS001M3/LessorCh/searchLsLessorCh")
	@ResponseBody
	public BaseJasonObject<LessorChDTO> searchLsLessorCh(String lsNo, String itemSEL,String appSeq, String appMode) throws Exception{

		LessorChDTO lessorCh = new LessorChDTO();
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd");
		if (appMode.equals("NEW"))
		{
			if(!"變更印鑑".equals(itemSEL))
			{
				//取得合約主檔資訊
				List<TbLsMain> lsMainList = lsCommonService.selectLsMainBylsNoVerMax(lsNo);
				if(lsMainList.get(0).getRENT_CHG_DATE()!=null && !"".equals(lsMainList.get(0).getRENT_CHG_DATE()))
				{
					lessorCh.setRent_CHG_DATE(sdFormat.format(lsMainList.get(0).getRENT_CHG_DATE()));
				}
			}
			//出租人關係選項
			lessorCh.setLessors(lS001M3ervice.getLsLessorByNoVer(lsNo));
			lessorCh.setModifyLessorId("");
			List<TbSysLookup> TbSysLookupList =lookupService.getByType("LS_LEASE_RELATION");
			
			String tempLsLeaseRelation = "";
			for (TbSysLookup tbSysLookup : TbSysLookupList) {
				if (tempLsLeaseRelation.length() > 0) {
					tempLsLeaseRelation += ";";
				}
				tempLsLeaseRelation += tbSysLookup.getCODE() + ","
						+ tbSysLookup.getNAME();
			}
			lessorCh.setLsLeaseRelation(tempLsLeaseRelation);

			
			// 取得出租人類型
			lessorCh.setLessorTypeList(lookupService.getByType("LS_LESSOR"));

			// 取得匯款選項
			lessorCh.setPaymentModeList(lookupService.getByType("LS_PAYMENT_MODE"));
			
			//查詢出租人關係
			lessorCh.setVoucherTypeList(lS001M3ervice.searchPayLookupByType("VOUCHER_TYPE"));
			
			//查詢與所有權人關係
			lessorCh.setOwnerRelationList(lookupService.getByType("LS_LEASE_RELATION"));

		} else {
			
			if(!"印鑑變更".equals(itemSEL))
			{
				//取得合約暫存檔資訊
				List<TbLsMainAdded>  lsMainAddedList=lS001M3ervice.searchTbLsMainAdded(lsNo, appSeq);
				if(lsMainAddedList.size()>0)
				{
					if(lsMainAddedList.get(0).getRENT_CHG_DATE()!=null && !"".equals(lsMainAddedList.get(0).getRENT_CHG_DATE()))
					{
						lessorCh.setRent_CHG_DATE(sdFormat.format(lsMainAddedList.get(0).getRENT_CHG_DATE()));
					}
				}
			}
			
			// 取得出租人資訊
			lessorCh.setLessors(lS001M3ervice.searchLsLessorAddByAppSeq(appSeq,lsNo,"BEFORE"));
			List<LessorChDTO> lessor;
			try {
				lessor = lS001M3ervice.searchLsLessorAddByAppSeqState(appSeq, null, lsNo, "BEFORE");
			} catch (Exception ex) {
				log.error(ex.getMessage(), ex);
				throw ex;
			}
			lessorCh.setModifyLessorId(lessor.get(0).getORG_LESSOR_ID());
			
			List<TbSysLookup> TbSysLookupList =lookupService.getByType("LS_LEASE_RELATION");
			
			String tempLsLeaseRelation = "";
			for (TbSysLookup tbSysLookup : TbSysLookupList) {
				if (tempLsLeaseRelation.length() > 0) {
					tempLsLeaseRelation += ";";
				}
				tempLsLeaseRelation += tbSysLookup.getCODE() + ","
						+ tbSysLookup.getNAME();
			}
			lessorCh.setLsLeaseRelation(tempLsLeaseRelation);
			
			//出租人關係選項　
			lessorCh.setLessorTypeList(lookupService.getByType("LS_LESSOR"));

			// 取得匯款選項
			lessorCh.setPaymentModeList(lookupService.getByType("LS_PAYMENT_MODE"));
			
			//查詢出租人關係
			lessorCh.setVoucherTypeList(lS001M3ervice.searchPayLookupByType("VOUCHER_TYPE"));
			
			//查詢與所有權人關係
			lessorCh.setOwnerRelationList(lookupService.getByType("LS_LEASE_RELATION"));
			
		}

		return new BaseJasonObject<LessorChDTO>(lessorCh, AJAX_SUCCESS);
	}

	/**
	 * 查詢系統參數設定(TB_SYS_LOOKUP)
	 * 
	 * @param type
	 *            系統參數設定類型
	 * @return
	 */
	@RequestMapping(value = "/ls/LS001M3/searchSysLookupByType")
	@ResponseBody
	public BaseJasonObject<TbSysLookup> searchSysLookupByType(String type) {
		return new BaseJasonObject<TbSysLookup>(lookupService.getByType(type), "", "");

	}

	/**
	 * 租金存儲草稿
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ls/LS001M3_RentCh/saveRentCh")
	@ResponseBody
	public BaseJasonObject<TbLsAppForm> saveRentCh(HttpServletRequest request) {// editLocVer
		String editAppSeq = request.getParameter("editAppSeq");// 申請流水號
		String editAppMode = request.getParameter("editAppMode");// 申請狀態
		String editLsNo = request.getParameter("editLsNo");// 合約編號
		String editLocId = request.getParameter("editLocId");// 站點編號
		String editLocData = request.getParameter("editLocData");// 站點資料
		String editMainLsVer = request.getParameter("editMainLsVer");// Main版次

		String selType = request.getParameter("selType");// 類別
		String itemSEL = request.getParameter("itemSEL");// 項目
		String tbStyleSEL = request.getParameter("tbStyleSEL");// 套表格式

		String changeStrDay = request.getParameter("changeStrDay");// 變更起始日
		String lsRentTypeChgSelect = request.getParameter("lsRentTypeChgSelect");// 變更租金類別

		String changeRent = request.getParameter("changeRent");// 變更後租金

		String[] oldPayData = request.getParameterValues("oldPayData");// 舊資料
		String[] newPayAmt = request.getParameterValues("newPayAmt");// 變更金額

		String lsStopResnSelect = request.getParameter("lsStopResnSelect");// 停付原因
		String stopBDate = request.getParameter("stopBDate");// 停付起
		String stopEDate = request.getParameter("stopEDate");// 停付迄
		String totalMonth = request.getParameter("stopMonth");// 共幾月
		try {
			if (StringUtils.equals(editAppMode, "NEW")) {
				lS001M3ervice.saveRentCh(editLsNo, editMainLsVer, selType, itemSEL, tbStyleSEL, changeRent, oldPayData, newPayAmt, changeStrDay, lsRentTypeChgSelect, lsStopResnSelect, stopBDate, stopEDate, totalMonth, editLocId, editLocData);
			} else {
				lS001M3ervice.updateLsAppRentCh(editAppSeq, editLsNo, editMainLsVer, itemSEL, tbStyleSEL, changeRent, oldPayData, newPayAmt, changeStrDay, lsRentTypeChgSelect, lsStopResnSelect, stopBDate, stopEDate, totalMonth, editLocId, editLocData);
			}
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			return new BaseJasonObject<>(false, StringUtils.equals(editAppMode, "NEW") ? "新增失敗":"修改失敗");
		}
		return new BaseJasonObject<>(true, StringUtils.equals(editAppMode, "NEW") ? "新增成功":"修改成功");
	}
	
	/**
	 * 查詢出租人編輯資訊
	 * 
	 * @param itemSEL  項目
	 * @param lsNo  合約編號
	 * @param lessorVer  出租人資料版號
	 * @param lessorId 出租人編號
	 * @param appSeq  申請檔流水號
	 * @param appMode  申請單狀態
	 * @return
	 */
	@RequestMapping(value = "/ls/LS001M3/LessorCh/searchLsLessorChEdit")
	@ResponseBody
	public BaseJasonObject<Map<String, Object>> searchLsLessorChEdit(String itemSEL, String lsNo,String lessorVer,String lessorId,String appSeq,String appMode) throws Exception{
		List<LessorChDTO> lessor = new ArrayList<LessorChDTO>();
		Map<String, Object> map = new HashMap<String, Object>();
		
		String tempLessorName = "";
		String tempLessorPaymentMode = "";
		
		//
		try {
			
		//取得出租人資訊	
		if("繼受出租人".equals(itemSEL)||"變更出租人".equals(itemSEL)||"變更負責人".equals(itemSEL))
		{
			int count=lS001M3ervice.lessorCountByLsNoIdAppSeq(appSeq, lsNo,lessorId, "AFTER",itemSEL);
			
			if(count>0){
					lessor=lS001M3ervice.searchLsLessorAddByAppSeqState(appSeq, lessorId, lsNo, "AFTER");
					
					for (LessorChDTO tbLsLessor : lessor) {
					
						if (tempLessorName.length() > 0) {
							tempLessorName += ",";
							tempLessorPaymentMode += ",";
						}
						tempLessorPaymentMode += tbLsLessor.getLESSOR_ID() + ":"
								+ tbLsLessor.getPAYMENT_MODE()+":"+tbLsLessor.getBUSINESS_TAX();
						tempLessorName += tbLsLessor.getLESSOR_ID() + ":"
								+ tbLsLessor.getLESSOR_NAME();
					}
			}
			else
			{
				List<LessorChDTO> list=lS001M3ervice.serarchLsLessorBylsNoLessorId(lsNo, lessorVer, lessorId);
				for (LessorChDTO tbLsLessor : list) {
					
					if (tempLessorName.length() > 0) {
						tempLessorName += ",";
						tempLessorPaymentMode += ",";
					}
					tempLessorPaymentMode += tbLsLessor.getLESSOR_ID() + ":"
							+ tbLsLessor.getPAYMENT_MODE()+":"+tbLsLessor.getBUSINESS_TAX();
					tempLessorName += tbLsLessor.getLESSOR_ID() + ":"
							+ tbLsLessor.getLESSOR_NAME();
				}
			}
			
		}else if("出租人更名".equals(itemSEL)||"變更印鑑".equals(itemSEL))
		{
			if("NEW".equals(appMode))
			{
				lessor=lS001M3ervice.serarchLsLessorBylsNoLessorId(lsNo, lessorVer, lessorId);
				
				for (LessorChDTO tbLsLessor : lessor) {
					
					if (tempLessorName.length() > 0) {
						tempLessorName += ",";
						tempLessorPaymentMode += ",";
					}
					tempLessorPaymentMode += tbLsLessor.getLESSOR_ID() + ":"
							+ tbLsLessor.getPAYMENT_MODE()+":"+tbLsLessor.getBUSINESS_TAX();
					tempLessorName += tbLsLessor.getLESSOR_ID() + ":"
							+ tbLsLessor.getLESSOR_NAME();
				}
			}else{
				int count=lS001M3ervice.lessorCountByLsNoIdAppSeq(appSeq, lsNo,lessorId, "AFTER",itemSEL);
				
				if(count>0){
					lessor=lS001M3ervice.searchLsLessorAddByAppSeqState(appSeq, lessorId, lsNo, "AFTER");
					for (LessorChDTO tbLsLessor : lessor) {
						
						if (tempLessorName.length() > 0) {
							tempLessorName += ",";
							tempLessorPaymentMode += ",";
						}
						tempLessorPaymentMode += tbLsLessor.getLESSOR_ID() + ":"
								+ tbLsLessor.getPAYMENT_MODE()+":"+tbLsLessor.getBUSINESS_TAX();
						tempLessorName += tbLsLessor.getLESSOR_ID() + ":"
								+ tbLsLessor.getLESSOR_NAME();
					}
				}
				else{
					lessor=lS001M3ervice.serarchLsLessorBylsNoLessorId(lsNo, lessorVer, lessorId);
					for (LessorChDTO tbLsLessor : lessor) {
						
						if (tempLessorName.length() > 0) {
							tempLessorName += ",";
							tempLessorPaymentMode += ",";
						}
						tempLessorPaymentMode += tbLsLessor.getLESSOR_ID() + ":"
								+ tbLsLessor.getPAYMENT_MODE()+":"+tbLsLessor.getBUSINESS_TAX();
						tempLessorName += tbLsLessor.getLESSOR_ID() + ":"
								+ tbLsLessor.getLESSOR_NAME();
					}
				}
			}
		}
		
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw ex;
		}
		
		map.put("lessor", lessor);
		map.put("lessorName", tempLessorName);
		map.put("lessorPaymentMode", tempLessorPaymentMode);
		return new BaseJasonObject<Map<String, Object>>(map,AJAX_SUCCESS);
	}
	
	
	/**
	 * 查詢出租人付款資訊
	 * @param itemSEL
	 * @param lsNo
	 * @param lessorId
	 * @param appSeq
	 * @param appMode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/ls/LS001M3/LessorCh/searchLsPaymentByLessorId")
	@ResponseBody
	public BaseJasonObject<Map<String, Object>> searchLsPaymentByLessorId(String itemSEL, String lsNo,String lessorId,String appSeq,String appMode) throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<TbLsLocPaymentDTO> list=new ArrayList<TbLsLocPaymentDTO>();
		
		if(!"變更印鑑".equals(itemSEL)&&!"租金抵扣所得稅".equals(itemSEL))
		{
			if("NEW".equals(appMode))
			{
				list=lS001M3ervice.getLsLocPaymentByLsNoVerLocIdLessorId(lsNo, lessorId,LsPaymentItem.getLsPaymentItem());
			}else{
				int count=lS001M3ervice.paymentCountByLsNoAppSeq(appSeq, lsNo,lessorId, "AFTER");
				
				if(count>0){
					list=lS001M3ervice.getLsLocPaymentByLsNoVerLocIdLessorId(appSeq,lsNo,lessorId,LsPaymentItem.getLsPaymentItem(), "AFTER");		
				}
				else{
					list=lS001M3ervice.getLsLocPaymentByLsNoVerLocIdLessorId(lsNo, lessorId,LsPaymentItem.getLsPaymentItem());
				}
			}
		}
		
		map.put("lessor", list);
		return new BaseJasonObject<Map<String, Object>>(map,AJAX_SUCCESS);
	}
	
	/**
	 * 增補協定_出租人相關異動-儲存草稿
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ls/LS001M3/LessorCh/saveDraft")
	@ResponseBody
	public BaseJasonObject<String> saveDraft(HttpServletRequest request,String lessorJson,String lsNo,String lsVer,String appSeq,String appMode) {
		String msg = "";
		try {
			String selType = request.getParameter("selType");
			String itemSEL = request.getParameter("itemSEL");
			if (appMode.equals("NEW")) // 當新增時
			{
				// 取獨申請檔
				TbLsApp tbLsapp = this.getTbLsApp(lsNo, lsVer, selType, itemSEL);

				// 取得頁面資料
				Map<String, String[]> map = request.getParameterMap();
				
				// 寫入申請檔和相關資料
				String lsAcppSeq = lS001M3ervice.addLessorCh(lsNo,lsVer,itemSEL,tbLsapp,map,lessorJson,request);
				msg = getMessageDetail("msg.add.success");

			} else // 當草稿編輯時
			{
				// 取得頁面資料
				Map<String, String[]> map = request.getParameterMap();

				// 更新申請檔和寫入相關資料
				String lsAppSeq =lS001M3ervice.updateLessorCh(lsNo,lsVer,itemSEL,appSeq,map,lessorJson,request);
				
				msg = getMessageDetail("msg.update.success");
			}
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			return new BaseJasonObject<>(false, e.getMessage());
		}
		
		return new BaseJasonObject<>(true, msg);
	}
	
	
	/**
	 * 儲存時檢核付款人資訊
	 * @param request
	 * @param lsNo
	 * @return
	 */
	@RequestMapping(value = "/ls/LS001M3/LessorCh/locPaymentValidator")
	@ResponseBody
	public BaseJasonObject<String> locPaymentValidator(HttpServletRequest request,String lsNo) {
		String msg = "";
		msg=lS001M3ervice.locPaymentValidator(request,lsNo);
		if(!msg.equals(""))
		{
			return new BaseJasonObject<>(false, msg);
		}
		return new BaseJasonObject<>(true, "");
	}
	
	/**
	 * 申請有檢核
	 * @return
	 */
	@RequestMapping(value = "/ls/LS001M3/ls001M3ApplyValidator")
	@ResponseBody
	public BaseJasonObject<Object> ls001M3ApplyValidator(@RequestParam("appSeq") String appSeq,
						@RequestParam("lsNo") String lsNo , @RequestParam("type")String type) {
		String errMsg ="";
		try {
			errMsg =lS001M3ervice.validatorApply(appSeq,lsNo);
			if(StringUtils.isBlank(errMsg)){
			 errMsg = lS001M3ervice.ls001M3Apply(appSeq,lsNo,this.getLoginUser().getUsername(),type);
			}
			return new BaseJasonObject<Object>(StringUtils.isBlank(errMsg), StringUtils.isBlank(errMsg) ? "申請成功！" : errMsg);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new BaseJasonObject<Object>(false, "申請失敗：" + e.getMessage());
		}
	}
	
	/**
	 * 申請無檢核
	 * @return
	 */
	@RequestMapping(value = "/ls/LS001M3/ls001M3ApplyNotValidator")
	@ResponseBody
	public BaseJasonObject<Object> ls001M3ApplyNotValidator(@RequestParam("appSeq") String appSeq,
						@RequestParam("lsNo") String lsNo , @RequestParam("type")String type) {
		String errMsg ="";
		try {
			 errMsg = lS001M3ervice.ls001M3Apply(appSeq,lsNo,this.getLoginUser().getUsername(),type);
			return new BaseJasonObject<Object>(StringUtils.isBlank(errMsg), StringUtils.isBlank(errMsg) ? "申請成功！" : errMsg);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new BaseJasonObject<Object>(false, "申請失敗：" + e.getMessage());
		}
	}

	
	//=================Wei Str=====================
	@RequestMapping(value = "/ls/LS001M3_ElecCh/initEditElecCh")
	@ResponseBody
	public BaseJasonObject<TbLsLocation> initEditElecCh(String appSeq,String lsNo){
		return new BaseJasonObject<TbLsLocation>(lS001M3ervice.initEditElecCh(appSeq, lsNo), "");
	}
	
	//=================Wei End=====================

}
