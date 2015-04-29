package com.foya.noms.web.controller.ls;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.hornetq.utils.json.JSONArray;
import org.hornetq.utils.json.JSONException;
import org.hornetq.utils.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foya.dao.mybatis.model.TbAuthPersonnel;
import com.foya.dao.mybatis.model.TbComEqpModel;
import com.foya.dao.mybatis.model.TbComEqpType;
import com.foya.dao.mybatis.model.TbLsApp;
import com.foya.dao.mybatis.model.TbLsAvgRent;
import com.foya.dao.mybatis.model.TbLsElecRange;
import com.foya.dao.mybatis.model.TbLsLessor;
import com.foya.dao.mybatis.model.TbLsLocElec;
import com.foya.dao.mybatis.model.TbLsMain;
import com.foya.dao.mybatis.model.TbLsReward;
import com.foya.dao.mybatis.model.TbLsTrmnElec;
import com.foya.dao.mybatis.model.TbOrgDept;
import com.foya.dao.mybatis.model.TbOrgDomain;
import com.foya.dao.mybatis.model.TbSysLookup;
import com.foya.exception.DataNotFoundException;
import com.foya.exception.NomsException;
import com.foya.noms.dto.ls.LeaseDTO;
import com.foya.noms.dto.ls.LeaseDomainMoneyDTO;
import com.foya.noms.dto.ls.LeaseLocElecDTO;
import com.foya.noms.dto.ls.LeaseLocationDTO;
import com.foya.noms.dto.ls.LeaseMainDTO;
import com.foya.noms.dto.ls.LeaseTerminalDTO;
import com.foya.noms.dto.ls.SiteAlocDetailDTO;
import com.foya.noms.dto.ls.TbLsLocPaymentDTO;
import com.foya.noms.dto.ls.TbLsResExchDTO;
import com.foya.noms.dto.pay.TbLsLocElecDTO;
import com.foya.noms.dto.pay.TbLsSiteDTO;
import com.foya.noms.enums.LsAddType;
import com.foya.noms.enums.LsEnumCommon;
import com.foya.noms.enums.TerminationEnum;
import com.foya.noms.enums.LsEnumCommon.LsTypeEnum;
import com.foya.noms.service.auth.PersonnelService;
import com.foya.noms.service.ls.LS001M3Service;
import com.foya.noms.service.ls.LS001Service;
import com.foya.noms.service.ls.LS002Service;
import com.foya.noms.service.ls.LsCommonService;
import com.foya.noms.service.ls.SiteAlocService;
import com.foya.noms.service.org.DeptService;
import com.foya.noms.service.org.DomainService;
import com.foya.noms.service.org.ORG002Service;
import com.foya.noms.service.system.LookupService;
import com.foya.noms.service.system.UniqueSeqService;
import com.foya.noms.util.BaseJasonObject;
import com.foya.noms.util.JqGridData;
import com.foya.noms.web.controller.BaseController;
import com.github.miemiedev.mybatis.paginator.domain.PageList;


@Controller
public class LS001Controller extends BaseController {

	@Autowired
	private LS001Service lS001Service;
	@Autowired
	private DomainService domainService;
	@Autowired
	private DeptService deptService;
	@Autowired
	private PersonnelService personnelService;
	@Autowired
	private LS002Service lS002Service;
	@Autowired
	private UniqueSeqService uniqueSeqService;
	@Autowired
	private ORG002Service oRG002Service;
	
	@Autowired
	private LS001M3Service lS001M3ervice;
	
	@Autowired
	private LookupService lookupService;
	
	@Autowired
	private SiteAlocService siteAlocService;
	
	@Autowired
	private LsCommonService lsCommonService;

	/**
	 * 合約維護作業初始頁
	 * 
	 * @return
	 */
	@RequestMapping(value = "/ls/LS001/init")
	public String lS001Init(HttpServletRequest request, Map<String, Object> model) {
		model.put("domainList", domainService.getAccessDomainByUser(getLoginUser().getDomain()));

		return "ajaxPage/ls/LS001";
	}

	/**
	 * 新約/續約/換約/一解一簽
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws JSONException 
	 */
	@RequestMapping(value = "/ls/LS001M")
	public String loadLeaseByNo(HttpServletRequest request, Map<String, Object> modelMap) throws Exception {
		String btnType =(String) modelMap.get("btnType");
		String lsNo = (String) modelMap.get("lsNo");
		String lsVer = (String) modelMap.get("lsVer");
		String appSeq = (String) modelMap.get("appSeq");
		String newWindow = (String) modelMap.get("newWindow");
		String lsType = (String) modelMap.get("lsType");
		
		if(btnType==null || btnType.length()==0){
			btnType = request.getParameter("btnType");
		}
		if(lsNo==null || lsNo.length()==0){
			lsNo = request.getParameter("lsNo");
		}
		if(lsVer==null || lsVer.length()==0){
			lsVer = request.getParameter("lsVer");
		}
		if(appSeq==null || appSeq.length()==0){
			appSeq = request.getParameter("appSeq");
		}
		if(newWindow==null || newWindow.length()==0){
			newWindow = request.getParameter("newWindow");
		}
		if(lsType==null || lsType.length()==0){
			lsType = request.getParameter("lsType");
		}
		//簽核作業 
		try {
			if(StringUtils.isNotBlank(appSeq)  && (StringUtils.isBlank(lsNo) || StringUtils.isBlank(lsVer))){
				TbLsApp AppObj = new TbLsApp();
				AppObj = lS001Service.selectLsAppByAppSeq(appSeq);
				if(AppObj != null){
					lsVer = AppObj.getLS_VER();
					lsNo = AppObj.getLS_NO();
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new Exception("簽核作業錯誤顯示");
		}
		
		modelMap.put("lsNo", lsNo);
		modelMap.put("lsVer", lsVer);
		modelMap.put("appSeq", appSeq);
		modelMap.put("processType" , LsEnumCommon.LsTypeEnum.detectType(lsType));
		modelMap.put("newWindow", "true".equals(newWindow));
		
		try {
			lS001Service.initLs001M(btnType, lsNo, lsVer, modelMap);	//取得進入Ls001M頁面所需資訊
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw ex;
		}
		
		//付款方式
		modelMap.put("paymentMode",lookupService.getByType("LS_PAYMENT_MODE"));// 付款方式;
		//資源互換:互換業者
		modelMap.put("carriers", lS001Service.getCarriers());
		
		return "ajaxPage/ls/LS001M";
	}

	
	/**
	 * 儲存草稿
	 * 
	 * @param lsMain
	 * @param lessorJson
	 * @return
	 * @throws JSONException
	 */
	@RequestMapping(value = "/ls001M/saveDraft")
	@ResponseBody
	public BaseJasonObject<LeaseMainDTO> saveUpdateLeaseMain(LeaseMainDTO lsMain, String lessorJson, 
			String rewardJsonArr, String btnType) {

		try {
			lS001Service.saveUpdateLeaseMain(btnType, lsMain, lessorJson, rewardJsonArr);
			return new BaseJasonObject<LeaseMainDTO>(lsMain, "儲存草稿成功");
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return new BaseJasonObject<LeaseMainDTO>(false, "儲存草稿失敗");
		}
		//return new BaseJasonObject<String>("合約編號: " + lsMain.getLS_NO() + " 儲存草稿成功");
		
	}

	/**
	 * 取得合約紙本保管地點
	 * 
	 * @param deptId
	 * @return
	 */
	@RequestMapping(value = "/ls001M/contract/getKeepPlace")
	@ResponseBody
	public BaseJasonObject<TbOrgDomain> getKeepPlace(@RequestParam(value = "deptId") String deptId) {

		return new BaseJasonObject<>(lS001Service.getKeepPlace(deptId), AJAX_SUCCESS);
	}

	/**
	 * 查詢使用者透過 部門代號
	 * 
	 */
	@RequestMapping(value = "/ls001M/contract/getDeptPSN")
	@ResponseBody
	public BaseJasonObject<TbAuthPersonnel> getDeptPSN(@RequestParam(value = "deptId") String deptId) {
		return new BaseJasonObject<>(personnelService.selectByDeptIdLike(deptId), AJAX_SUCCESS, AJAX_EMPTY);
	}

	/**
	 * 查詢合約清單
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ls/LS001/searchByCond")
	@ResponseBody
	public JqGridData<LeaseDTO> searchByCond(HttpServletRequest request) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lsNo", StringUtils.isBlank(request.getParameter("lsNo")) ? "" : "%" + request.getParameter("lsNo") + "%"); // 合約編號
		//map.put("btsSiteId", StringUtils.isBlank(request.getParameter("btsSiteId")) ? "" : request.getParameter("btsSiteId")); // 站台編號
		map.put("locId", StringUtils.isBlank(request.getParameter("locId")) ? "" : request.getParameter("locId")); // 站點編號
		map.put("opDomain", StringUtils.isBlank(request.getParameter("opDomain")) ? "" : "%" + request.getParameter("opDomain") + "%"); // 區域
		map.put("expiredMonths", request.getParameter("expiredMonths")); // 距到期日
		map.put("dealUser", request.getParameter("dealUser")); // 工程維護單位
		map.put("appUser", request.getParameter("appUser")); // 基站編號
		map.put("lsType", StringUtils.split(request.getParameter("lsType"), ",")); // 合約型態
		map.put("appStatus", StringUtils.split(request.getParameter("appStatus"), ",")); // 申請狀態
		map.put("lsStatus", StringUtils.split(request.getParameter("lsStatus"), ","));// 合約型態

		List<LeaseDTO> list = lS001Service.searchByCond(map);
		PageList<LeaseDTO> page = (PageList<LeaseDTO>) list;
		
		if (list.size() >= 1000) {
			return new JqGridData<LeaseDTO>(-1, null);
		} else {
			return new JqGridData<LeaseDTO>(page.getPaginator().getTotalCount(), list);
		}
	}

	/**
	 * 由區域取得部門
	 */
	@RequestMapping(value = "/ls/LS001/getDeptByDomain")
	@ResponseBody
	public BaseJasonObject getDeptByDomain(HttpServletRequest request, String opDomain) {

		List<String> domainList = new ArrayList<String>();
		List<TbOrgDomain> list = domainService.getDomainTreeByStandardDomain(opDomain);
		for (TbOrgDomain domain : list) {
			domainList.add(domain.getID());
		}

		return new BaseJasonObject(deptService.getDeptByDomain(domainList), AJAX_SUCCESS, AJAX_EMPTY);
	}

	/**
	 * 由部門取得人員
	 */
	@RequestMapping(value = "/ls/LS001/getUsertByDeptId")
	@ResponseBody
	public BaseJasonObject getUsertByDeptId(HttpServletRequest request, String deptId) {

		List<TbAuthPersonnel> personnelList = personnelService.selectByDeptIdLike(deptId);

		return new BaseJasonObject<TbAuthPersonnel>(personnelList, AJAX_SUCCESS, AJAX_EMPTY);
	}

	/**
	 * 合約作廢
	 */
	@RequestMapping(value = "/ls/LS001/cancelLeaseApp")
	@ResponseBody
	public BaseJasonObject<String> cancelLeaseApp(HttpServletRequest request, String appSeq, String lsNo) {

		try {
			lS001Service.cancelLeaseApp(appSeq);
			return new BaseJasonObject<String>("作廢成功");
		} catch (Exception ex) {
			return new BaseJasonObject<String>("合約編號: " + lsNo + ", 作廢失敗");
		}
	}

	/**
	 * 增修條文
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ls/LS001M/clause")
	public String clausePage(HttpServletRequest request, Map<String, Object> model) {
		log.info("btnType: " + request.getParameter("btnType"));
		log.info("claId: " + request.getParameter("claId"));
		
		model.put("clauseList", lS001Service.getLsClause());
		model.put("btnType", request.getParameter("btnType"));
		model.put("claId", request.getParameter("claId"));
		
		return "/ajaxPage/ls/LS001M_Clause";
	}
	
	/**
	 * 增補協議
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/ls/LS001M3")
	public String ls001M3(HttpServletRequest request, Map<String, Object> modelMap) {
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd");
		
		if(request.getParameter("lsNo")!=null)
		{
			//取得合約主檔
			List<TbLsMain> lsMainList = lsCommonService.selectLsMainBylsNoVerMax(request.getParameter("lsNo"));
			String lessorUser="";
			modelMap.put("tbLsMain", lsMainList.get(0));
			modelMap.put("lsDate", sdFormat.format(lsMainList.get(0).getLS_S_DATE())+"~"+sdFormat.format(lsMainList.get(0).getLS_E_DATE()));
			modelMap.put("appSeq", request.getParameter("appSeq"));
			modelMap.put("appStatus", request.getParameter("appStatus"));
			
			//查詢合約保管地點
			TbOrgDept tbOrgDept = deptService.getDeptByPrimaryKey(lsMainList.get(0).getKEEP_DEPT());
			modelMap.put("keepDept", tbOrgDept.getDEPT_NAME()+"/"+lsMainList.get(0).getKEEP_PLACE());
			
			//判斷增補協議編輯狀態
			//NEW:新增;VIEW:唯讀;EDIT:草稿
			if(request.getParameter("appSeq")!=null && !request.getParameter("appSeq").equals("")&& 
					request.getParameter("lsType")!=null && !request.getParameter("lsType").equals("") && request.getParameter("lsType").equals("5"))
			{
				if(request.getParameter("appStatus").equals("0"))
				{
					modelMap.put("appMode", "EDIT");
					modelMap.put("tbLsApp", lS001M3ervice.searchTbLsApp(request.getParameter("appSeq")));
				}else if(request.getParameter("appStatus").equals("1")){
					modelMap.put("appMode", "VIEW");
					modelMap.put("tbLsApp", lS001M3ervice.searchTbLsApp(request.getParameter("appSeq")));
				}
				else{
					modelMap.put("appMode", "NEW");
				}
			}
			else
			{
				modelMap.put("appMode", "NEW");
			}
			
			//查詢出租人
			List<TbLsLessor> lessorList = lS001M3ervice.getLsLessorByNoVer(request.getParameter("lsNo"));
			for(TbLsLessor tbLsLessor:lessorList)
			{
				lessorUser+=tbLsLessor.getLESSOR_NAME()+",";
			}
			modelMap.put("lessorUser",lessorUser);
		}else
		{
			//取得申請檔資訊
			TbLsApp  tbLsApp =lS001M3ervice.searchTbLsApp(request.getParameter("appSeq"));
			
			//取得合約主檔
			List<TbLsMain> lsMainList = lsCommonService.selectLsMainBylsNoVerMax(tbLsApp.getLS_NO());
			String lessorUser="";
			modelMap.put("tbLsMain", lsMainList.get(0));
			modelMap.put("lsDate", sdFormat.format(lsMainList.get(0).getLS_S_DATE())+"~"+sdFormat.format(lsMainList.get(0).getLS_E_DATE()));
			modelMap.put("appSeq", request.getParameter("appSeq"));
			modelMap.put("appStatus", tbLsApp.getAPP_STATUS());
			modelMap.put("appMode", "VIEW");
			modelMap.put("tbLsApp", tbLsApp);
			
			
			//查詢合約保管地點
			TbOrgDept tbOrgDept = deptService.getDeptByPrimaryKey(lsMainList.get(0).getKEEP_DEPT());
			modelMap.put("keepDept", tbOrgDept.getDEPT_NAME()+"/"+lsMainList.get(0).getKEEP_PLACE());
			
			//查詢出租人
			List<TbLsLessor> lessorList = lS001M3ervice.getLsLessorByNoVer(tbLsApp.getLS_NO());
			for(TbLsLessor tbLsLessor:lessorList)
			{
				lessorUser+=tbLsLessor.getLESSOR_NAME()+",";
			}
			modelMap.put("lessorUser",lessorUser);
		}

		return "ajaxPage/ls/LS001M3";
	}

	@RequestMapping(value = "/ls/LS001M3/chPage")
	public String ls001M3ChPage(HttpServletRequest request, Map<String, Object> modelMap) {
		String page = "";
		modelMap.put("lsAddTypeEnum", LsAddType.getLabelValueList());
		if ("0".equals(request.getParameter("typeID"))) { // 借電變更
			Map<String, String> mapb = new LinkedHashMap<>();
			mapb.put("btn_1M3Save_Draft", "儲存草稿");
			mapb.put("btn_1M3Print", "套表列印");
			modelMap.put("button", mapb);
			Map<String, String> map = new LinkedHashMap<>();
			map.put("更改計費方式－一般", "更改計費方式－一般");
			map.put("更改計費方式－實報實銷", "更改計費方式－實報實銷");
			map.put("電表歸零", "電表歸零");
			modelMap.put("itemSEL", map);

			page = "ajaxPage/ls/LS001M3_ElecCh";
		} else if ("1".equals(request.getParameter("typeID"))) { // 租金變更
			Map<String, String> mapb = new LinkedHashMap<>();
			mapb.put("btn_1M3Save_Draft", "儲存草稿");
			mapb.put("btn_1M3Print", "套表列印");
			modelMap.put("button", mapb);
			Map<String, String> map = new LinkedHashMap<>();
			map.put("租金停付", "租金停付");
			map.put("租金調降", "租金調降");
			map.put("租金調漲", "租金調漲");
			map.put("管理費變更租金", "管理費變更租金");
			modelMap.put("itemSEL", map);

			page = "ajaxPage/ls/LS001M3_RentCh";
		} else if ("2".equals(request.getParameter("typeID"))) { // 加裝設備
			Map<String, String> mapb = new LinkedHashMap<>();
			mapb.put("btn_1M3Save_Draft", "儲存草稿");
			mapb.put("btn_1M3Print", "套表列印");
			mapb.put("btn_1M3PrintRent", "列印租金起付確認書");
			modelMap.put("button", mapb);
			Map<String, String> map = new LinkedHashMap<>();
			map.put("不調租金", "不調租金");
			map.put("調漲租金", "調漲租金");
			map.put("調漲租金及電費", "調漲租金及電費");
			map.put("不調租金調電費", "不調租金調電費");
			modelMap.put("itemSEL", map);
			//付款方式
			modelMap.put("paymentMode",lookupService.getByType("LS_PAYMENT_MODE"));// 付款方式;
			modelMap.put("elecModeMap", lS001Service.getLookupByType("LS_ELEC_MODE"));// 用電方式
			page = "ajaxPage/ls/LS001M3_AddDevice";
		} else if ("3".equals(request.getParameter("typeID"))) { // 出租人及付款對象變更
			Map<String, String> mapb = new LinkedHashMap<>();
			mapb.put("btn_1M3Save_Draft", "儲存草稿");
			mapb.put("btn_1M3Print", "套表列印");
			modelMap.put("button", mapb);
			Map<String, String> map = new LinkedHashMap<>();
			map.put("繼受出租人", "繼受出租人");
			map.put("變更負責人", "變更負責人");
			map.put("變更出租人", "變更出租人");
			map.put("出租人更名", "出租人更名");
			map.put("變更印鑑", "變更印鑑");
			map.put("租金抵扣所得稅", "租金抵扣所得稅");
			map.put("變更電匯帳戶", "變更電匯帳戶");
			modelMap.put("itemSEL", map);

			page = "ajaxPage/ls/LS001M3_LessorCh";
		} else if ("4".equals(request.getParameter("typeID"))) { // 資料異動
			Map<String, String> mapb = new LinkedHashMap<>();
			mapb.put("btn_1M3Save", "存檔");
			mapb.put("btn_1M3PrintRent", "列印租金起付確認書");
			modelMap.put("button", mapb);
			Map<String, String> map = new LinkedHashMap<>();
			map.put("租金起算日", "租金起算日");
			map.put("手機回饋", "手機回饋");
			map.put("站台編號異動", "站台編號異動");
			modelMap.put("itemSEL", map);

			page = "ajaxPage/ls/LS001M3_DataCh";
		}
		modelMap.put("selType", request.getParameter("typeID"));
		return page;
	}

	/**
	 * 查詢解約UI需要用的值。
	 * 
	 * @param lsNo
	 *            合約編號
	 * @param lsVer
	 *            版次
	 * @param type
	 *            新增 or 修改
	 * @param appSeq
	 *            申請流水號
	 * @param modelMap
	 *            返回UI所承接的map
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/ls/LS001T")
	public String lS001T(String lsNo, String lsVer, String type, String appSeq, Map<String, Object> modelMap) throws IOException {
		try {
			//簽核作業 
			if(lsNo==null || lsNo.length()==0 || lsVer==null || lsVer.length()==0){
				TbLsApp AppObj = new TbLsApp();
				AppObj = lS001Service.selectLsAppByAppSeq(appSeq);			
				lsVer = AppObj.getLS_VER();
				lsNo = AppObj.getLS_NO();
			}

			lS002Service.searchByHouseLs002M(false, lsNo, modelMap);
			lS002Service.searchByCommonLs002M(lsNo, lsVer, modelMap);
			List<TbLsLocElec> tbLsLocElecList = lS001Service.searchLsLocElecToTermination(lsNo, lsVer);
			List<TbLsReward> phoneNoList = lS001Service.getRewardInfo(lsNo, lsVer, "1");
			List<TbLsReward> phoneList = lS001Service.getRewardInfo(lsNo, lsVer, "2");
			modelMap.put("lsVer", lsVer);
			modelMap.put("type", type);
			modelMap.put("appSeq", appSeq);
			modelMap.put("elecupdList", lS001Service.selectAppFormElecupd());
			modelMap.put("trmnList", lS001Service.selectAppFormTrmn());
			modelMap.put("trmnResnEnum", TerminationEnum.LsTerminationEnum.getLabelValueList());
			modelMap.put("trmnTyepeEnum", TerminationEnum.LsTerminationTypeEnum.getLabelValueList());
			JSONArray arr = new JSONArray();
			JSONObject tmp;
			// 回饋門號 與 回饋手機
			for (TbLsReward list : phoneNoList) {
				tmp = new JSONObject();
				tmp.put("REWARD_ID1", list.getREWARD_ID());
				tmp.put("CUST_NAME", list.getCUST_NAME());
				tmp.put("PHONE_NBR", list.getPHONE_NBR());
				tmp.put("PRCING", list.getPRCING());
				tmp.put("REWARD_RESN", list.getREWARD_RESN());
				tmp.put("REWARD_DESC", list.getREWARD_DESC());
				arr.put(tmp);
			}
			modelMap.put("phoneNoList", arr.toString());
			arr = new JSONArray();
			for(TbLsReward list : phoneList){
				tmp = new JSONObject();
				tmp.put("REWARD_ID2", list.getREWARD_ID());
				tmp.put("REWARD_QTY", list.getREWARD_QTY());
				tmp.put("ESTIMATE_AMT", list.getESTIMATE_AMT());
				tmp.put("REWARD_RESN", list.getREWARD_RESN());
				tmp.put("REWARD_DESC", list.getREWARD_DESC());
				arr.put(tmp);
			}
			modelMap.put("phoneList", arr.toString());
			// 用電方式
			arr = new JSONArray();
			for (TbLsLocElec list : tbLsLocElecList) {
				tmp = new JSONObject();
				tmp.put("ENERGY_METER", list.getENERGY_METER());
				tmp.put("LOCATION_ID", list.getLOCATION_ID());
				tmp.put("ELEC_MODE", list.getELEC_MODE());
				tmp.put("CHRG_MODE", (StringUtils.isEmpty(list.getCHRG_MODE()) ? "": list.getCHRG_MODE()));
				tmp.put("ELEC_BEGIN_DATE", list.getELEC_BEGIN_DATE() == null ? "" : new SimpleDateFormat("yyyy/MM/dd").format(list.getELEC_BEGIN_DATE()));
				tmp.put("ELEC_END_DATE", list.getELEC_END_DATE() == null ? "" : new SimpleDateFormat("yyyy/MM/dd").format(list.getELEC_END_DATE()));
				arr.put(tmp);
			}
			modelMap.put("elec", arr.toString());
			if ("edit".equals(type)) {
				lS001Service.searchLsTrmn(lsNo, lsVer, modelMap);
				List<TbLsTrmnElec> tbLsTrmnElecList = lS001Service.searchLsTrmnElec(lsNo, lsVer);
				arr = new JSONArray();
				for (TbLsTrmnElec list : tbLsTrmnElecList) {
					tmp = new JSONObject();
					tmp.put("LOCATION_ID", list.getLOCATION_ID());
					tmp.put("ENERGY_METER", list.getENERGY_METER());
					tmp.put("TRMN_DEGREE", list.getTRMN_DEGREE());
					tmp.put("FORM_ID", list.getFORM_ID());
					arr.put(tmp);
				}
				modelMap.put("editElec", arr.toString());

				TbLsApp tbLsApp = lS001Service.searchLsApp(lsNo, lsVer).get(0);
//				modelMap.put("deptName", oRG002Service.getByPk(tbLsApp.getAPP_DEPT_ID()).getDEPT_NAME());// 建立部門
//				modelMap.put("userName", tbLsApp.getAPP_USER());// 建立人員
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "ajaxPage/ls/LS001T";
	}

	/**
	 * 解約 儲存草稿-新增
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ls/LS001T/terminateLease")
	@ResponseBody
	public BaseJasonObject<LeaseDTO> terminateLease(HttpServletRequest request) {
		try {
			LeaseTerminalDTO dto = setLeaseTerminalDTO(request, true);
			lS001Service.terminateLease(dto);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(ExceptionUtils.getFullStackTrace(e));
			return new BaseJasonObject<>(false, e.getMessage());
		}
		return new BaseJasonObject<>(true, getMessageDetail("msg.add.success"));
	}

	/**
	 * 查詢這合約編號是否有解約的資料
	 * 
	 * @param lsNo
	 *            合約編號
	 * @return
	 */
	@RequestMapping(value = "/ls/LS001T/vettingTermination")
	@ResponseBody
	public BaseJasonObject<LeaseDTO> vettingTermination(String lsNo) {
		List<TbLsApp> list = lS001Service.searchLsApp(lsNo, null);
		if (list.size() > 0) {
			return new BaseJasonObject<>(true, "此合約編號已有解約過了");
		}
		return new BaseJasonObject<>(false, "");
	}

	/**
	 * 解約 儲存草稿-更新
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ls/LS001T/updateTerminateLease")
	@ResponseBody
	public BaseJasonObject<LeaseDTO> updateTerminateLease(HttpServletRequest request) {
		try {
			LeaseTerminalDTO dto = setLeaseTerminalDTO(request, false);
			lS001Service.updateTerminateLease(dto, request.getParameter("appSeq"));
		} catch (Exception e) {
			e.printStackTrace();
			log.error(ExceptionUtils.getFullStackTrace(e));
			return new BaseJasonObject<>(false, e.getMessage());
		}
		return new BaseJasonObject<>(true, getMessageDetail("msg.update.success"));
	}

	/**
	 * 將UI的值塞入物件中
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param flag
	 *            true:新增 false:修改
	 * @return
	 * @throws Exception
	 */
	private LeaseTerminalDTO setLeaseTerminalDTO(HttpServletRequest request, boolean flag) throws Exception {
		String userName = getLoginUser().getUsername();
		Date date = new Date();
		String lsNo = request.getParameter("lsNo");
		String lsVer = request.getParameter("lsVer");
		Date trmnDate = new SimpleDateFormat("yyyy/MM/dd").parse(request.getParameter("trmnDate"));
		String elecupdList = request.getParameter("elecupdList");

		LeaseTerminalDTO dto = new LeaseTerminalDTO();
		dto.setLS_NO(lsNo);
		dto.setLS_VER(lsVer);
		dto.setTRMN_DATE(trmnDate);
		dto.setTRMN_TYEPE(request.getParameter("trmnTyepeEnum"));
		dto.setTRMN_RESN(request.getParameter("trmnResnEnum"));
		dto.setRECOV_DAY(new BigDecimal(request.getParameter("recovDay")));//
		dto.setFORM_ID(request.getParameter("trmnList"));
		dto.setTRMN_DESC(StringUtils.isEmpty(request.getParameter("trmnDesc")) ? "" : request.getParameter("trmnDesc"));
		if (flag) {
			dto.setCR_USER(userName);
			dto.setCR_TIME(date);
		} else {
			dto.setMD_TIME(date);
			dto.setMD_USER(userName);
		}

		if (request.getParameterValues("trmnDegree") != null) {
			List<TbLsTrmnElec> list = new ArrayList<TbLsTrmnElec>();
			String[] trmnDegree = request.getParameterValues("trmnDegree");
			String[] locationId = request.getParameterValues("locationId");
			String[] energyMeter = request.getParameterValues("energyMeter");
			for (int i = 0; i < trmnDegree.length; i++) {
				TbLsTrmnElec tbLsTrmnElec = new TbLsTrmnElec();
				tbLsTrmnElec.setLS_NO(lsNo);
				tbLsTrmnElec.setLS_VER(lsVer);
				tbLsTrmnElec.setLOCATION_ID(locationId[i]);
				tbLsTrmnElec.setENERGY_METER(energyMeter[i]);
				tbLsTrmnElec.setTRMN_DATE(trmnDate);
				tbLsTrmnElec.setFORM_ID(elecupdList);
				tbLsTrmnElec.setTRMN_DEGREE(new BigDecimal(trmnDegree[i]));
				if (flag) {
					tbLsTrmnElec.setCR_USER(userName);
					tbLsTrmnElec.setCR_TIME(date);
				} else {
					tbLsTrmnElec.setMD_TIME(date);
					tbLsTrmnElec.setMD_USER(userName);
				}
				list.add(tbLsTrmnElec);
			}
			dto.setTrmnElecList(list);
		}
		return dto;
	}
	/**
	 * 站點畫面init
	 * @param lsNo
	 * 			合約編號
	 * @param lsVer
	 * 			版次
	 * @return
	 */
	@RequestMapping(value = "/ls/LS001M_Site/initLeaseLocation")
	public String initLeaseLocation(String lsNo, String lsVer, Map<String, Object> modelMap,String appSeq) {
	
		
		TbLsApp app =  lS001Service.selectLsAppByAppSeq(appSeq);
//		String lsType = LsEnumCommon.LsTypeEnum.detectType(app.getLS_TYPE());
		List<TbOrgDomain> allowDomain = new ArrayList<>();
		if(!app.getLS_TYPE().equals(LsTypeEnum.ContinueLease.getLsType())){
			allowDomain= lS001Service.getLeaseDomainListByUser
				(lsNo, lsVer, getLoginUser().getDomain(),modelMap);	
		}else{
			allowDomain= lS001Service.getLeaseAddedDomainListByUser
					(appSeq, getLoginUser().getDomain(), modelMap);
		}
		
		modelMap.put("lsType", app.getLS_TYPE());
		modelMap.put("appSeq", appSeq);
		modelMap.put("isContinue", app.getLS_TYPE().equals(LsTypeEnum.ContinueLease.getLsType()));
		if(allowDomain.size()>0){
			modelMap.put("lsNo", lsNo);
			modelMap.put("lsVer", lsVer);
//			modelMap.put("siteDomain",allowDomain);
			modelMap.put("elecModeMap", lS001Service.getLookupByType("LS_ELEC_MODE"));// 用電方式
			
			
			
			return "ajaxPage/ls/LS001M_Site";
		}else{
			return "accessDenied";
			
		}
	}
	
	/**
	 * 站點畫面取的站點清單
	 * @param lsNo
	 * 			合約編號
	 * @param lsVer
	 * 			版次
	 * @return
	 */
	@RequestMapping(value = "/ls/LS001M_Site/getLeaseLocationList")
	@ResponseBody
	public BaseJasonObject<LeaseDTO> getLeaseLocationList(String lsNo, String lsVer,String domain,String appSeq,String lsType) {
		JSONArray arr = new JSONArray();
		try {
			List<LeaseLocationDTO> dtoList =null;
			if(!lsType.equals(LsEnumCommon.LsTypeEnum.ContinueLease.getLsType())){
				dtoList = lS001Service.getLeaseLocation(lsNo, lsVer,domain);
			}else{
				if(StringUtils.isEmpty(appSeq)){
					throw new NomsException("App Seq 不可為空");
				}
				dtoList = lS001Service.getLeaseAddedLocation(appSeq, domain);
			}
			for (LeaseLocationDTO list : dtoList) {
				setLocationJson(list, arr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new BaseJasonObject<>(true, arr.toString());
	}
	/**
	 * 新增站點
	 * @param lsNo
	 * 			合約編號
	 * @param lsVer
	 * 			版次
	 * @param locId
	 * 			站點編號
	 * @param domain
	 * 			區域
	 * @param zipCode
	 * 			郵區號
	 * @param locAddr
	 * 			地址
	 * @param locName
	 * 			站點名稱
	 * @return
	 */
	@RequestMapping(value = "/ls/LS001M_Site/saveNewLeaseLocation")
	@ResponseBody
	public BaseJasonObject<LeaseDTO> saveNewLeaseLocation(String lsNo, String lsVer, String locId, String domain, String zipCode, String locAddr, String locName) {
		JSONArray arr = new JSONArray();
		try {
			LeaseLocationDTO dto = lS001Service.saveNewLeaseLocation(lsNo, lsVer, locId, domain, zipCode);
			dto.setADDR(locAddr);
			dto.setLOC_NAME(locName);
			setLocationJson(dto, arr);
		} catch (Exception e) {
			e.printStackTrace();
			return new BaseJasonObject<>(false, e.getMessage());
		}
		return new BaseJasonObject<>(true, arr.toString());
	}
	/**
	 * 組站點 table 資料
	 * @param list
	 * 			LeaseLocationDTO
	 * @param arr
	 * 			JSONArray
	 * @throws Exception
	 */
	private void setLocationJson(LeaseLocationDTO list, JSONArray arr) throws Exception {
		JSONObject tmp = new JSONObject();
		tmp.put("LOCATION_ID", list.getLOCATION_ID());
		tmp.put("LocName", list.getLOC_NAME());
		tmp.put("Addr", list.getADDR());
		tmp.put("LS_VER", list.getLS_VER());
		tmp.put("LS_NO", list.getLS_NO());
		tmp.put("ZIP", list.getZIP_CODE());
		tmp.put("HavingEffectiveLease", (list.isHavingEffectiveLease() == true ? "有" : "無"));
		arr.put(tmp);
	}
	/**
	 * 站點刪除
	 * @param lsNo
	 * 			合約編號
	 * @param lsVer
	 * 			版次
	 * @param locId
	 * 			站點編號
	 * @return
	 */
	@RequestMapping(value = "/ls/LS001M_Site/deleteLeaseSite")
	@ResponseBody
	public BaseJasonObject<LeaseDTO> deleteLeaseSite(String lsNo, String lsVer, String locId) {
		lS001Service.deleteLeaseSite(lsNo, lsVer, locId);
		return new BaseJasonObject<>(true, getMessageDetail("msg.delete.success"));
	}

	/**
	 * 點選站點，撈取資料
	 * 
	 * @param lsNo
	 * 			合約編號
	 * @param lsVer
	 * 			版次
	 * @param locId
	 * 			站點編號
	 * @param addFlag
	 * 			增補協議-加裝設備用
	 * @return
	 */
	@RequestMapping(value = "/ls/LS001M_Site/getLocationSitePay4Rent")
	@ResponseBody
	public BaseJasonObject<LeaseLocationDTO> getLocationSitePay4Rent(String lsNo, String lsVer, String locId,String lsType,String appSeq,String addFlag){
		BaseJasonObject<LeaseLocationDTO> baseJasonObject;
		try {
			
			if(!LsEnumCommon.LsTypeEnum.ContinueLease.getLsType().equals(lsType)){
				baseJasonObject = new BaseJasonObject<LeaseLocationDTO>(lS001Service.getLocationSitePay4Rent(lsNo, lsVer, locId,addFlag), getMessageDetail("msg.query.success"));
			}else{
				baseJasonObject = new BaseJasonObject<LeaseLocationDTO>(lS001Service.getLsLocationSiteAddedPay4Rent(appSeq, locId), getMessageDetail("msg.query.success"));
			}
			
			baseJasonObject.setSuccess(true);
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			baseJasonObject = new BaseJasonObject<>(false, e.getMessage());
		}
		
		return baseJasonObject;
	}
	/**
	 * 點選站點，撈取資料
	 * 
	 * @param lsNo
	 * 			合約編號
	 * @param lsVer
	 * 			版次
	 * @param locId
	 * 			站點編號
	 * @return
	 */
	@RequestMapping(value = "/ls/LS001M_Site/getLocationSiteAddedPay4Rent")
	@ResponseBody
	public BaseJasonObject<LeaseLocationDTO> getLocationSiteAddedPay4Rent(String appSeq, String locId){
		BaseJasonObject<LeaseLocationDTO> baseJasonObject;
		try {
			baseJasonObject = new BaseJasonObject<LeaseLocationDTO>(lS001Service.getLsLocationSiteAddedPay4Rent(appSeq, locId), getMessageDetail("msg.query.success"));
			baseJasonObject.setSuccess(true);
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			baseJasonObject = new BaseJasonObject<>(false, e.getMessage());
		}
		
		return baseJasonObject;
	}
	
	/**
	 * 點選站點，撈取資料
	 * 
	 * @param lsNo
	 * 			合約編號
	 * @param lsVer
	 * 			版次
	 * @param locId
	 * 			站點編號
	 * @param addFlag
	 * 			增補協議-加裝設備用
	 * @return
	 */
	@RequestMapping(value = "/ls/LS001M_Site/getLocationSitePay4Elec")
	@ResponseBody
	public BaseJasonObject<LeaseLocElecDTO> getLocationSitePay4Elec(String lsNo, String lsVer, String locId,String lsType,String appSeq,String addFlag){
		BaseJasonObject<LeaseLocElecDTO> baseJasonObject;
		try {
			if(!LsEnumCommon.LsTypeEnum.ContinueLease.getLsType().equals(lsType)){
				baseJasonObject = new BaseJasonObject<LeaseLocElecDTO>(lS001Service.getLocationSitePay4Elec(lsNo, lsVer, locId,addFlag), getMessageDetail("msg.query.success"));
			}else{
				baseJasonObject = new BaseJasonObject<LeaseLocElecDTO>(lS001Service.getLocationSiteAddedPay4Elec(appSeq, locId), getMessageDetail("msg.query.success"));
			}
			
			baseJasonObject.setSuccess(true);
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			baseJasonObject = new BaseJasonObject<>(false, e.getMessage());
		}
		
		return baseJasonObject;
	}
	
	
	
	/**
	 * 撈取 承租建物 資料
	 * 
	 * @return
	 */
	@RequestMapping(value = "/ls/LS001M_Site/getLsLeaseBldgtype")
	@ResponseBody
	public BaseJasonObject<TbSysLookup> getLsLeaseBldgtype(){
		BaseJasonObject<TbSysLookup> baseJasonObject = new BaseJasonObject<TbSysLookup>(true, getMessageDetail("msg.delete.success"));
		baseJasonObject.setRows(lS001Service.getLsLeaseBldgtype());
		return baseJasonObject;
	}
	/**
	 * 撈取 設備類型
	 * @return
	 */
	@RequestMapping(value = "/ls/LS001M_Site/getTbComEqpType")
	@ResponseBody
	public BaseJasonObject<TbComEqpType> getTbComEqpType(){
		return new BaseJasonObject<TbComEqpType>(lS001Service.getTbComEqpType(),"","");
	}
	/**
	 * 撈取 機型代碼
	 * 
	 * @param eqpTypeId
	 * 			設備類型ID
	 * @return
	 */
	@RequestMapping(value = "/ls/LS001M_Site/getTbComEqpModel")
	@ResponseBody
	public BaseJasonObject<TbComEqpModel> getTbComEqpModel(String eqpTypeId){
		return new BaseJasonObject<TbComEqpModel>(lS001Service.getTbComEqpModel(eqpTypeId),"","");
	}
	/**
	 * 撈取均價資料
	 * 
	 * @param eqpTypeId
	 * 				設備類型ID
	 * @param eqpModelId
	 * 				機型代碼ID
	 * @param zipCode
	 * 				郵遞區號
	 * @return
	 */
	@RequestMapping(value = "/ls/LS001M_Site/getTbLsAvgRent")
	@ResponseBody
	public BaseJasonObject<TbLsAvgRent> getTbLsAvgRent(String eqpTypeId,String eqpModelId,String zipCode){
		return new BaseJasonObject<TbLsAvgRent>(lS001Service.getTbLsAvgRent(eqpTypeId, eqpModelId, zipCode),"","");
		
	}

	/**
	 * 取得簽核記錄 Tab
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/ls/getLeaseSignHistory")
	public String getLeaseSignHistory(@RequestParam(required=false,value = "processType") String processType, @RequestParam(value = "applyNo", required = false) String applyNo, Map<String, Object> modelMap) {
		if(StringUtils.isBlank(processType)){
			processType="SimpleLease";
		}
		modelMap.put("processType", processType);
		modelMap.put("applyNo", applyNo);
		return "ajaxPage/ls/LS001M_Signlog";
	}
	
	
	/**
	 * 儲存站台租金資料
	 * 
	 * @param lsMain
	 * @param lessorJson
	 * @return
	 * @throws JSONException
	 */
	@RequestMapping(value = "/ls001M/saveLeaseLocationRent")
	@ResponseBody
	public BaseJasonObject<String> saveLeaseLocationRent(LeaseLocationDTO leaseLocationDTO,HttpServletRequest request) {
		
		try {
			
			List<TbLsSiteDTO> tbLsSiteDTOList = new ArrayList<>();
			List<TbLsLocPaymentDTO> tbLsLocPaymentDTOList = new ArrayList<>();
			Date md_time = new Date();
			
			String mdUser = getLoginUser().getUsername();
			String[] RECIPIENT_ID =request.getParameterValues("RECIPIENT_ID");
			String[] RECIPIENT_NAME =request.getParameterValues("RECIPIENT_NAME");
			String[] PAYMENT_ITEM =request.getParameterValues("PAYMENT_ITEM");
			String[] LESSOR_ID =request.getParameterValues("LESSOR_ID");
			String[] LESSOR_NAME =request.getParameterValues("LESSOR_NAME");
			String[] LESSOR_RELATION =request.getParameterValues("LESSOR_RELATION");
			String[] payment_Mode =request.getParameterValues("payment_Mode");
			String[] UNIT_CODE =request.getParameterValues("UNIT_CODE");
			String[] SUB_UNIT_CODE =request.getParameterValues("SUB_UNIT_CODE");
			String[] ACCOUNT_NO =request.getParameterValues("ACCOUNT_NO");
			String[] PAY_ALOC =request.getParameterValues("PAY_ALOC");
			String[] PAY_AMT =request.getParameterValues("PAY_AMT");
			String[] BUSINESS_TAX =request.getParameterValues("BUSINESS_TAX");
			leaseLocationDTO.setCR_USER(mdUser);
			leaseLocationDTO.setMD_USER(mdUser);
			leaseLocationDTO.setCR_TIME(md_time);
			leaseLocationDTO.setMD_TIME(md_time);
			
			if(RECIPIENT_ID!=null){
				TbLsLocPaymentDTO dto = null;
				for(int i=0;i<RECIPIENT_ID.length;i++){
					if(StringUtils.isNotEmpty(RECIPIENT_NAME[i]) && StringUtils.isNotEmpty(RECIPIENT_ID[i]) ){
						dto =new TbLsLocPaymentDTO();
						dto.setRECIPIENT_ID(RECIPIENT_ID[i]);
						dto.setRECIPIENT_NAME(RECIPIENT_NAME[i]);
						dto.setBUSINESS_TAX(new BigDecimal(BUSINESS_TAX[i]));
						dto.setEFF_DATE(leaseLocationDTO.getEFF_DATE());
						dto.setEND_DATE(leaseLocationDTO.getEND_DATE());
						dto.setLS_NO(leaseLocationDTO.getLS_NO());
						dto.setLS_VER(leaseLocationDTO.getLS_VER());
						dto.setLESSOR_ID(LESSOR_ID[i]);
						dto.setLESSOR_NAME(LESSOR_NAME[i]);
						dto.setLESSOR_RELATION(LESSOR_RELATION[i]);
						dto.setLOCATION_ID(leaseLocationDTO.getLOCATION_ID());
						dto.setPAY_ALOC(new BigDecimal(PAY_ALOC[i]));
						dto.setPAY_AMT(new BigDecimal(PAY_AMT[i]));
						dto.setPAYMENT_ITEM(PAYMENT_ITEM[i]);
						dto.setPAYMENT_MODE(payment_Mode[i]);
						dto.setUNIT_CODE(UNIT_CODE[i]);
						dto.setSUB_UNIT_CODE(SUB_UNIT_CODE[i]);
						dto.setACCOUNT_NO(ACCOUNT_NO[i]);
						dto.setCR_USER(mdUser);
						dto.setMD_USER(mdUser);
						dto.setCR_TIME(md_time);
						dto.setMD_TIME(md_time);
						tbLsLocPaymentDTOList.add(dto);
					}
					
				}	
			}
			
			
			String[] BTS_SITE_ID =request.getParameterValues("BTS_SITE_ID");
			String[] SITE_ID =request.getParameterValues("SITE_ID");
			String[] EXPN_TYPE =request.getParameterValues("EXPN_TYPE");
			String[] ALOC_ID =request.getParameterValues("ALOC_ID");
			
			if(ALOC_ID!=null){
				TbLsSiteDTO dto = null;
				for(int i=0;i<ALOC_ID.length;i++){
					dto=new TbLsSiteDTO();
					dto.setALOC_ID(ALOC_ID[i]);
					dto.setBTS_SITE_ID(BTS_SITE_ID[i]);
					dto.setSITE_ID(SITE_ID[i]);
					dto.setEXPN_TYPE(EXPN_TYPE[i]);
					dto.setLOCATION_ID(leaseLocationDTO.getLOCATION_ID());
					dto.setLS_NO(leaseLocationDTO.getLS_NO());
					dto.setLS_VER(leaseLocationDTO.getLS_VER());
					dto.setPAY_BEGIN_DATE(leaseLocationDTO.getPAY_BEGIN_DATE());
					dto.setEFF_DATE(leaseLocationDTO.getEFF_DATE());
					dto.setEND_DATE(leaseLocationDTO.getEND_DATE());
					dto.setLS_E_DATE(leaseLocationDTO.getLS_E_DATE());
					dto.setCR_USER(mdUser);
					dto.setMD_USER(mdUser);
					dto.setCR_TIME(md_time);
					dto.setMD_TIME(md_time);
					
					tbLsSiteDTOList.add(dto);
				}
			}
			
			leaseLocationDTO.setTbLsLocPaymentList(tbLsLocPaymentDTOList);
			leaseLocationDTO.setTbLsSiteDTOList(tbLsSiteDTOList);
			lS001Service.saveUpdateLeaseLocation4Rent(leaseLocationDTO);
			return new BaseJasonObject<>(getMessageDetail("msg.update.success"));
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			return new BaseJasonObject<>(getMessageDetail("msg.update.fail")+":"+e.getMessage());
		}
		
	}
	/**
	 * 儲存站台租金資料
	 * 
	 * @param lsMain
	 * @param lessorJson
	 * @return
	 * @throws JSONException
	 */
	@RequestMapping(value = "/ls001M/saveLeaseLocationRentAdded")
	@ResponseBody
	public BaseJasonObject<String> saveLeaseLocationRentAdded(LeaseLocationDTO leaseLocationDTO,HttpServletRequest request) {
		
		try {
			
			List<TbLsSiteDTO> tbLsSiteDTOList = new ArrayList<>();
			List<TbLsLocPaymentDTO> tbLsLocPaymentDTOList = new ArrayList<>();
			Date md_time = new Date();
			
			String mdUser = getLoginUser().getUsername();
			String[] RECIPIENT_ID =request.getParameterValues("RECIPIENT_ID");
			String[] RECIPIENT_NAME =request.getParameterValues("RECIPIENT_NAME");
			String[] PAYMENT_ITEM =request.getParameterValues("PAYMENT_ITEM");
			String[] LESSOR_ID =request.getParameterValues("LESSOR_ID");
			String[] LESSOR_NAME =request.getParameterValues("LESSOR_NAME");
			String[] LESSOR_RELATION =request.getParameterValues("LESSOR_RELATION");
			String[] payment_Mode =request.getParameterValues("payment_Mode");
			String[] UNIT_CODE =request.getParameterValues("UNIT_CODE");
			String[] SUB_UNIT_CODE =request.getParameterValues("SUB_UNIT_CODE");
			String[] ACCOUNT_NO =request.getParameterValues("ACCOUNT_NO");
			String[] PAY_ALOC =request.getParameterValues("PAY_ALOC");
			String[] PAY_AMT =request.getParameterValues("PAY_AMT");
			String[] BUSINESS_TAX =request.getParameterValues("BUSINESS_TAX");
			leaseLocationDTO.setCR_USER(mdUser);
			leaseLocationDTO.setMD_USER(mdUser);
			leaseLocationDTO.setCR_TIME(md_time);
			leaseLocationDTO.setMD_TIME(md_time);
//			
//			if(RECIPIENT_ID!=null){
//				TbLsLocPaymentDTO dto = null;
//				for(int i=0;i<RECIPIENT_ID.length;i++){
//					if(StringUtils.isNotEmpty(RECIPIENT_NAME[i]) && StringUtils.isNotEmpty(RECIPIENT_ID[i]) ){
//						dto =new TbLsLocPaymentDTO();
//						dto.setRECIPIENT_ID(RECIPIENT_ID[i]);
//						dto.setRECIPIENT_NAME(RECIPIENT_NAME[i]);
//						dto.setBUSINESS_TAX(new BigDecimal(BUSINESS_TAX[i]));
//						dto.setEFF_DATE(leaseLocationDTO.getEFF_DATE());
//						dto.setEND_DATE(leaseLocationDTO.getEND_DATE());
//						dto.setLS_NO(leaseLocationDTO.getLS_NO());
//						dto.setLS_VER(leaseLocationDTO.getLS_VER());
//						dto.setLESSOR_ID(LESSOR_ID[i]);
//						dto.setLESSOR_NAME(LESSOR_NAME[i]);
//						dto.setLESSOR_RELATION(LESSOR_RELATION[i]);
//						dto.setLOCATION_ID(leaseLocationDTO.getLOCATION_ID());
//						dto.setPAY_ALOC(new BigDecimal(PAY_ALOC[i]));
//						dto.setPAY_AMT(new BigDecimal(PAY_AMT[i]));
//						dto.setPAYMENT_ITEM(PAYMENT_ITEM[i]);
//						dto.setPAYMENT_MODE(payment_Mode[i]);
//						dto.setUNIT_CODE(UNIT_CODE[i]);
//						dto.setSUB_UNIT_CODE(SUB_UNIT_CODE[i]);
//						dto.setACCOUNT_NO(ACCOUNT_NO[i]);
//						dto.setCR_USER(mdUser);
//						dto.setMD_USER(mdUser);
//						dto.setCR_TIME(md_time);
//						dto.setMD_TIME(md_time);
//						tbLsLocPaymentDTOList.add(dto);
//					}
//					
//				}	
//			}
//			
//			
//			String[] BTS_SITE_ID =request.getParameterValues("BTS_SITE_ID");
//			String[] SITE_ID =request.getParameterValues("SITE_ID");
//			String[] EXPN_TYPE =request.getParameterValues("EXPN_TYPE");
//			String[] ALOC_ID =request.getParameterValues("ALOC_ID");
//			
//			if(ALOC_ID!=null){
//				TbLsSiteDTO dto = null;
//				for(int i=0;i<ALOC_ID.length;i++){
//					dto=new TbLsSiteDTO();
//					dto.setALOC_ID(ALOC_ID[i]);
//					dto.setBTS_SITE_ID(BTS_SITE_ID[i]);
//					dto.setSITE_ID(SITE_ID[i]);
//					dto.setEXPN_TYPE(EXPN_TYPE[i]);
//					dto.setLOCATION_ID(leaseLocationDTO.getLOCATION_ID());
//					dto.setLS_NO(leaseLocationDTO.getLS_NO());
//					dto.setLS_VER(leaseLocationDTO.getLS_VER());
//					dto.setPAY_BEGIN_DATE(leaseLocationDTO.getPAY_BEGIN_DATE());
//					dto.setEFF_DATE(leaseLocationDTO.getEFF_DATE());
//					dto.setEND_DATE(leaseLocationDTO.getEND_DATE());
//					dto.setLS_E_DATE(leaseLocationDTO.getLS_E_DATE());
//					dto.setCR_USER(mdUser);
//					dto.setMD_USER(mdUser);
//					dto.setCR_TIME(md_time);
//					dto.setMD_TIME(md_time);
//					
//					tbLsSiteDTOList.add(dto);
//				}
//			}
//			
//			leaseLocationDTO.setTbLsLocPaymentList(tbLsLocPaymentDTOList);
//			leaseLocationDTO.setTbLsSiteDTOList(tbLsSiteDTOList);
			lS001Service.saveUpdateLeaseLocation4RentAdded(leaseLocationDTO);
			return new BaseJasonObject<>(getMessageDetail("msg.update.success"));
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			return new BaseJasonObject<>(getMessageDetail("msg.update.fail")+":"+e.getMessage());
		}
		
	}
	
	
	/**
	 * 儲存站台租金資料
	 * 
	 * @param lsMain
	 * @param lessorJson
	 * @return
	 * @throws JSONException
	 */
	@RequestMapping(value = "/ls001M/saveLeaseLocationElec")
	@ResponseBody
	public BaseJasonObject<String> saveLeaseLocationElec(LeaseLocElecDTO leaseLocElecDTO,HttpServletRequest request) {
		
		try {
			if(leaseLocElecDTO==null){
				return new BaseJasonObject<>(getMessageDetail("msg.update.fail")+":租約資訊不完整");
			}
			List<TbLsLocElecDTO> lsLocElecList = new ArrayList<>();
			Date md_time = new Date();
			
			String mdUser = getLoginUser().getUsername();
			String[] elecJsonValue =request.getParameterValues("elecJsonValue");
			
			if(elecJsonValue!=null && elecJsonValue.length>0){
				ObjectMapper mapper = new ObjectMapper();
				mapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
					
				for(String s: elecJsonValue){
					TbLsLocElecDTO e =mapper.readValue(s, TbLsLocElecDTO.class);
					e.setLS_NO(leaseLocElecDTO.getLS_NO());
					e.setLS_VER(leaseLocElecDTO.getLS_VER());
					e.setLOCATION_ID(leaseLocElecDTO.getLOCATION_ID());
					if(!"Y".equals(e.getSIGN_AGREEMENT())){
						e.setSIGN_AGREEMENT("N");
					}
					e.setCR_USER(mdUser);
					e.setMD_USER(mdUser);
					e.setCR_TIME(md_time);
					e.setMD_TIME(md_time);
					lsLocElecList.add(e);
				}
			}
			leaseLocElecDTO.setLsLocElecList(lsLocElecList);
			
			List<TbLsSiteDTO> tbLsSiteDTOList = new ArrayList<>();
			List<TbLsLocPaymentDTO> tbLsLocPaymentDTOList = new ArrayList<>();
			
			String[] RECIPIENT_ID =request.getParameterValues("RECIPIENT_ID");
			String[] RECIPIENT_NAME =request.getParameterValues("RECIPIENT_NAME");
			String[] PAYMENT_ITEM =request.getParameterValues("PAYMENT_ITEM");
			String[] LESSOR_ID =request.getParameterValues("LESSOR_ID");
			String[] LESSOR_NAME =request.getParameterValues("LESSOR_NAME");
			String[] LESSOR_RELATION =request.getParameterValues("LESSOR_RELATION");
			String[] payment_Mode =request.getParameterValues("payment_Mode");
			String[] UNIT_CODE =request.getParameterValues("UNIT_CODE");
			String[] SUB_UNIT_CODE =request.getParameterValues("SUB_UNIT_CODE");
			String[] ACCOUNT_NO =request.getParameterValues("ACCOUNT_NO");
			String[] PAY_ALOC =request.getParameterValues("PAY_ALOC");
			String[] PAY_AMT =request.getParameterValues("PAY_AMT");
			String[] BUSINESS_TAX =request.getParameterValues("BUSINESS_TAX");

			
			
			if(RECIPIENT_ID!=null){
				TbLsLocPaymentDTO dto = null;
				for(int i=0;i<RECIPIENT_ID.length;i++){
					if(StringUtils.isNotEmpty(RECIPIENT_NAME[i]) && StringUtils.isNotEmpty(RECIPIENT_ID[i]) ){
						dto =new TbLsLocPaymentDTO();
						dto.setRECIPIENT_ID(RECIPIENT_ID[i]);
						dto.setRECIPIENT_NAME(RECIPIENT_NAME[i]);
						dto.setBUSINESS_TAX(new BigDecimal(BUSINESS_TAX[i]));
						dto.setLESSOR_ID(LESSOR_ID[i]);
						dto.setLESSOR_NAME(LESSOR_NAME[i]);
						dto.setLESSOR_RELATION(LESSOR_RELATION[i]);
						dto.setPAY_ALOC(new BigDecimal(PAY_ALOC[i]));
						dto.setPAY_AMT(new BigDecimal(PAY_AMT[i]));
						dto.setPAYMENT_ITEM(PAYMENT_ITEM[i]);
						dto.setPAYMENT_MODE(payment_Mode[i]);
						dto.setUNIT_CODE(UNIT_CODE[i]);
						dto.setSUB_UNIT_CODE(SUB_UNIT_CODE[i]);
						dto.setACCOUNT_NO(ACCOUNT_NO[i]);
						dto.setCR_USER(mdUser);
						dto.setMD_USER(mdUser);
						dto.setCR_TIME(md_time);
						dto.setMD_TIME(md_time);
						tbLsLocPaymentDTOList.add(dto);
					}
					
				}	
			}
			
			
			String[] BTS_SITE_ID =request.getParameterValues("BTS_SITE_ID");
			String[] SITE_ID =request.getParameterValues("SITE_ID");
			String[] EXPN_TYPE =request.getParameterValues("EXPN_TYPE");
			String[] ALOC_ID =request.getParameterValues("ALOC_ID");
			
			if(ALOC_ID!=null){
				TbLsSiteDTO dto = null;
				for(int i=0;i<ALOC_ID.length;i++){
					dto=new TbLsSiteDTO();
					dto.setALOC_ID(ALOC_ID[i]);
					dto.setBTS_SITE_ID(BTS_SITE_ID[i]);
					dto.setSITE_ID(SITE_ID[i]);
					dto.setEXPN_TYPE(EXPN_TYPE[i]);
					dto.setCR_USER(mdUser);
					dto.setMD_USER(mdUser);
					dto.setCR_TIME(md_time);
					dto.setMD_TIME(md_time);
					tbLsSiteDTOList.add(dto);
				}
			}
			
			leaseLocElecDTO.setTbLsLocPaymentList(tbLsLocPaymentDTOList);
			leaseLocElecDTO.setTbLsSiteDTOList(tbLsSiteDTOList);
			lS001Service.saveUpdateLeaseLocation4Elec(leaseLocElecDTO);
			return new BaseJasonObject<>(getMessageDetail("msg.update.success"));
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			return new BaseJasonObject<>(getMessageDetail("msg.update.fail")+":"+e.getMessage());
		}
		
	}
	
	/**
	 * 撈取 租約電費級距 設定
	 * 
	 * @return
	 */
	@RequestMapping(value = "/ls/LS001M_Site/getElecRangeData")
	@ResponseBody
	public BaseJasonObject<TbLsElecRange> getElecRangeData(String eqpTypeId){
		return new BaseJasonObject<TbLsElecRange>(lS001Service.getLeaseElecRange(),AJAX_SUCCESS, AJAX_EMPTY);
	}
	
	
	//*************START 資源互換*****************
	
	/**
	 * 資源互換畫面init
	 * @param lsNo
	 * 			合約編號
	 * @param lsVer
	 * 			版次
	 * @return
	 */
	@RequestMapping(value = "/ls/LS001M_Site/initLeaseResExch")
	public String initLeaseResExch(String lsNo, String lsVer, Map<String, Object> modelMap) {
		List<TbOrgDomain> allowDomain= lS001Service.getLeaseDomainListByUser
				(lsNo, lsVer, getLoginUser().getDomain(),modelMap);
		
		if(allowDomain.size()>0){
			modelMap.put("lsNo", lsNo);
			modelMap.put("lsVer", lsVer);
			
			return "ajaxPage/ls/LS001M_Site";
		}else{
			return "accessDenied";
			
		}
	}
	
	/**
	 * 新增資源互換站點
	 * @param lsNo
	 * 			合約編號
	 * @param lsVer
	 * 			版次
	 * @param locId
	 * 			站點編號
	 * @param domain
	 * 			區域
	 * @param zipCode
	 * 			郵區號
	 * @param locAddr
	 * 			地址
	 * @param locName
	 * 			站點名稱
	 * @return
	 */
	@RequestMapping(value = "/ls/LS001M_Site/queryResExchSite")
	@ResponseBody
	public BaseJasonObject<LeaseDTO> queryResExchSite(String lsNo, String lsVer, String locId, String domain, String zipCode, String locAddr, String locName,String appSeq,String lsType) {
		JSONArray arr = new JSONArray();
		try {
			LeaseLocationDTO dto = lS001Service.queryResExchSite(lsNo, lsVer, locId, domain, zipCode,lsType,appSeq);
			dto.setADDR(locAddr);
			dto.setLOC_NAME(locName);
			setLocationJson(dto, arr);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new BaseJasonObject<>(false, e.getMessage());
		}
		return new BaseJasonObject<>(true, arr.toString());
	}
	
	@RequestMapping(value = "/ls/LS001M_Site/getExchangeSiteAlocByLocationId")
	@ResponseBody
	public BaseJasonObject<String> getExchangeSiteAlocByLocationId(String locId, String mainLocId, String lsNo, String lsVer,String lsType,String appSeq) {
		List<SiteAlocDetailDTO> list = new ArrayList<SiteAlocDetailDTO>();
		try {
			list = siteAlocService.findExchSiteAlocExitByCond(lsNo, lsVer, mainLocId, locId,lsType,appSeq);
		
			if (list == null || list.size() ==0) {
				list = siteAlocService.getExchangeSiteAlocByLocationId(locId, mainLocId, lsNo, lsVer,appSeq,lsType);
			}
			
			return new BaseJasonObject<>(true, new ObjectMapper().writeValueAsString(list));
		} catch (DataNotFoundException dnfe) {
			log.error(dnfe.getMessage(), dnfe);
			return new BaseJasonObject<>(false, dnfe.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new BaseJasonObject<>(false, "系統發生錯誤");
		}
	}
	
	/**
	 * 儲存草稿
	 * 
	 * @param lsMain
	 * @param lessorJson
	 * @return
	 * @throws JSONException
	 */
	@RequestMapping(value = "/ls/LS001M_Site/saveUpdateResExch")
	@ResponseBody
	public BaseJasonObject<TbLsResExchDTO> saveUpdateResExch(HttpServletRequest request) {

		try {
			lS001Service.saveUpdateResExch(request.getParameter("resExchJson"),
										   request.getParameter("lsNo"),
										   request.getParameter("lsVer"),
										   request.getParameter("mainLocId"));
			
			return new BaseJasonObject<TbLsResExchDTO>(true, "儲存成功");
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return new BaseJasonObject<TbLsResExchDTO>(false, "儲存失敗");
		}
		
	}
	
	/**
	 * 點選站點，撈取資源互換資料
	 * 
	 * @param lsNo
	 * 			合約編號
	 * @param lsVer
	 * 			版次
	 * @param locId
	 * 			站點編號
	 * @return
	 */
	@RequestMapping(value = "/ls/LS001M_Site/getLocationSitePay4Exch")
	@ResponseBody
	public BaseJasonObject<Object> getLocationSitePay4Exch(String lsNo, String lsVer, String locId,String lsType,String appSeq){
		
		try {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			List<TbLsResExchDTO> list = null;
			LeaseMainDTO dto =null;
			
			if(!LsEnumCommon.LsTypeEnum.ContinueLease.getLsType().equals(lsType)){
				list = lS001Service.getLocationSitePay4Exch(lsNo, lsVer, locId);
				dto = lS001Service.getLsMainByNo(lsNo, lsVer);
			}else{
				list = lS001Service.getLocationSiteAddedPay4Exch(appSeq, locId);
				dto = lS001Service.getLsMainAddedByAppSeq(appSeq);
			}
			dataMap.put("exchData", new ObjectMapper().writeValueAsString(list));
			dataMap.put("carriers", dto.getEXCH_CARRIERS());
			dataMap.put("exchItem", dto.getEXCH_ITEM());
//			return new BaseJasonObject<>(true, new ObjectMapper().writeValueAsString(list));
			return new BaseJasonObject<>(dataMap, AJAX_SUCCESS, AJAX_EMPTY);
		} catch (DataNotFoundException dnfe) {
			log.error(dnfe.getMessage(), dnfe);
			return new BaseJasonObject<>(false, dnfe.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new BaseJasonObject<>(false, "系統發生錯誤");
		}
	}
	//*************END 資源互換*****************

	@RequestMapping(value = "/ls/LS001M/getLsDomainMoneyList")
	@ResponseBody
	public BaseJasonObject<String> getLsDomainMoneyList(String lsNo, String lsVer) {
		
		List<LeaseDomainMoneyDTO> list = new ArrayList<LeaseDomainMoneyDTO>();
		try {
			list = lS001Service.getLsDomainMoneyList(lsNo, lsVer);
			return new BaseJasonObject<>(true, new ObjectMapper().writeValueAsString(list));
		} catch (DataNotFoundException dnfe) {
			log.error(dnfe.getMessage(), dnfe);
			return new BaseJasonObject<>(false, dnfe.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new BaseJasonObject<>(false, "系統發生錯誤");
		}
	}
}
