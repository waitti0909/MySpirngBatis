package com.foya.noms.web.controller.st;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.hornetq.utils.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.foya.dao.mybatis.model.TbAuthPersonnelExample;
import com.foya.dao.mybatis.model.TbInvMaterialOpt;
import com.foya.dao.mybatis.model.TbInvWarehouse;
import com.foya.dao.mybatis.model.TbOrgDept;
import com.foya.dao.mybatis.model.TbSiteOutsourcing;
import com.foya.dao.mybatis.model.TbSiteOutsourcingExample;
import com.foya.dao.mybatis.model.TbSiteOutsourcingExample.Criteria;
import com.foya.dao.mybatis.model.TbSiteWork;
import com.foya.dao.mybatis.model.TbSysLookup;
import com.foya.noms.dto.auth.UserDTO;
import com.foya.noms.dto.inv.TbInvMaterialOptDTO;
import com.foya.noms.dto.st.MeterialRtnDTO;
import com.foya.noms.dto.st.SiteMtDetailImportDTO;
import com.foya.noms.enums.MaterialOptType;
import com.foya.noms.enums.OutSourceStatus;
import com.foya.noms.enums.WorkType;
import com.foya.noms.service.auth.PersonnelService;
import com.foya.noms.service.st.MeterialOptDService;
import com.foya.noms.service.st.MeterialOptService;
import com.foya.noms.service.st.OutsourcingService;
import com.foya.noms.service.st.ST004Service;
import com.foya.noms.service.st.ST005Service;
import com.foya.noms.service.system.UniqueSeqService;
import com.foya.noms.util.BaseJasonObject;
import com.foya.noms.util.LabelValueModel;
import com.foya.noms.web.controller.BaseController;

@Controller
public class MeterialOptController extends BaseController {
	
	@Autowired
	private MeterialOptService meterialOptService;
	
	@Autowired
	private OutsourcingService outsourcingService;
	
	@Autowired
	private UniqueSeqService uniqueSeqService;
	
	@Autowired
	private ST004Service st004Service;
	
	@Autowired
	private ST005Service sT005Service;
	
	@Autowired
	private PersonnelService personnelService;
	
	@Autowired
	private MeterialOptDService meterialOptDService;
	
	/**
	 * 物料處理初始資料(簽核用)
	 * 
	 * @param optId   操作單號
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/st/invMeterial/meterialOptViewById")
	@ResponseBody
	public String meterialOptInitView(@RequestParam("optId") String optId, @RequestParam("status") String status, Map<String, Object> map) {
		log.debug("optId Id = " + optId);
		
		TbInvMaterialOpt model = meterialOptService.getByPk(optId);
		//取得操作類別清單
		List<LabelValueModel> optTypeList = MaterialOptType.getLabelValueList();	
		map.put("optTypeList", optTypeList);
		//派工單清單
		List<String> osEffective = new ArrayList<>();
		osEffective.add(OutSourceStatus.OS05.name());
		osEffective.add(OutSourceStatus.OS06.name());
		osEffective.add(OutSourceStatus.OS07.name());
		TbSiteOutsourcingExample example = new TbSiteOutsourcingExample();
		Criteria criteria = example.createCriteria();
		criteria.andORDER_IDEqualTo(model.getORDER_ID()).andSTATUSIn(osEffective);	// 有效委外單
		if (getLoginUser().isVendor()) {
			criteria.andCO_VAT_NOEqualTo(getLoginUser().getCoVatNo());	// 若為廠商登入 add by Charlie 20150316
		}
		List<TbSiteOutsourcing> outSourcingList=outsourcingService.getSiteOutSoureByExample(example);
		map.put("outSourcingList", outSourcingList);
		//取得領料倉庫清單
		List<TbInvWarehouse> warehouseList=meterialOptService.getInvWareHouse(model.getORDER_ID(), model.getOS_ID());
		map.put("warehouseList", warehouseList);
		
		return "ajaxPage/st/MeterialOptM";
	}
	
	/**
	 * 物料處理初始資料
	 * 
	 * @param orderId   工單編號
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/st/invMeterial/init")
	@ResponseBody
	public BaseJasonObject<Map<String, Object>> meterialOptInit(@RequestParam("orderId") String orderId) {
		Map<String, Object> model=new HashMap<String, Object>();
		log.debug("workOrder Id = " + orderId);
		//取得操作類別清單
		List<LabelValueModel> optTypeList=MaterialOptType.getLabelValueList();	
		model.put("optTypeList", optTypeList);
		//派工單清單
		List<String> osEffective = new ArrayList<>();
		osEffective.add(OutSourceStatus.OS05.name());
		osEffective.add(OutSourceStatus.OS06.name());
		osEffective.add(OutSourceStatus.OS07.name());
		TbSiteOutsourcingExample example = new TbSiteOutsourcingExample();
		Criteria criteria = example.createCriteria();
		criteria.andORDER_IDEqualTo(orderId).andSTATUSIn(osEffective);	// 有效委外單
		if (getLoginUser().isVendor()) {
			criteria.andCO_VAT_NOEqualTo(getLoginUser().getCoVatNo());	// 若為廠商登入 add by Charlie 20150316
		}
		List<TbSiteOutsourcing> outSourcingList = outsourcingService.getSiteOutSoureByExample(example);
		model.put("outSourcingList", outSourcingList);
		
		
		StringBuffer invRnResn = new StringBuffer(); //退料原因
		for(TbSysLookup sysLookup :meterialOptService.findMeterStatus("INV_RN_RESN")){
			if(invRnResn.length() == 0){
				invRnResn.append(sysLookup.getCODE() + ":" + sysLookup.getNAME());
			} else {
				invRnResn.append(";"+sysLookup.getCODE() + ":" + sysLookup.getNAME());
			}
		}
		
		StringBuffer matStatus = new StringBuffer(); //退料種類
		for(TbSysLookup sysLookup :meterialOptService.findMeterStatus("MAT_STATUS")){
			if(matStatus.length() == 0){
				matStatus.append(sysLookup.getCODE() + ":" + sysLookup.getNAME());
			} else {
				matStatus.append(";"+sysLookup.getCODE() + ":" + sysLookup.getNAME());
			}
		}
		
		model.put("invRnResn", invRnResn.toString()); //退料原因
		model.put("matStatus", matStatus.toString()); //退料種類
		return new BaseJasonObject<>(model, AJAX_SUCCESS);
	}
	
	/**
	 * 取得倉庫資料
	 * @param orderId
	 * @return
	 */
	@RequestMapping(value = "/st/invMeterial/searchInvWareHouse")
	@ResponseBody
	public BaseJasonObject<TbInvWarehouse> searchInvWareHouse(String orderId,String osId){
		List<TbInvWarehouse> warehouseList=meterialOptService.getInvWareHouse(orderId,osId);
		if(warehouseList == null){
			warehouseList = new ArrayList<TbInvWarehouse>();
		}
		return new BaseJasonObject<TbInvWarehouse>(warehouseList, AJAX_SUCCESS, AJAX_EMPTY);
	}
	
	
	/**
	 * 查詢工單下所有物料操作單主檔資料
	 * 
	 * @param orderId   工單編號
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/st/invMeterial/meterialOpt")
	@ResponseBody
	public BaseJasonObject<TbInvMaterialOptDTO> getMeterialOpt(
			@RequestParam("orderId") String orderId, Map<String, Object> model) {
		log.debug("workOrder Id = " + orderId);
		return new BaseJasonObject<>(meterialOptService.selectMaterialOptByOsId(orderId), AJAX_SUCCESS, AJAX_EMPTY);
	}	
	
	/**
	 * 查詢物料操作單主檔資料
	 * 
	 * @param optId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/st/invMeterial/meterialOptById")
	@ResponseBody
	public BaseJasonObject<Map<String, Object>> getMeterialOptTableById(
			@RequestParam("optId") String optId, Map<String, Object> model) {
		log.debug("optId = " + optId);
		TbInvMaterialOpt meterialOpt = meterialOptService.getByPk(optId);
		String appTime="";
		String btsSiteId="";
		String eqpTypeId="";
		String eqpTypeName="";
		String showStatus="";
		String showWorkType="";
		String showDeptName="";
		String showReqDate="";
		String siteId="";
		String showAppUserName="";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy/MM/dd");
		
		if(meterialOpt != null)
		{
			appTime=sdf.format(meterialOpt.getAPP_TIME());
			
			TbSiteWork siteWork=meterialOptService.getbSiteWork(meterialOpt.getORDER_ID());
			btsSiteId=siteWork.getBTS_SITE_ID();
			eqpTypeId=siteWork.getEQP_TYPE_ID();
			siteId=siteWork.getSITE_ID();
			List<LabelValueModel> eqpTypes = st004Service.getEqpTypes();
			for (LabelValueModel eqpType : eqpTypes) {
 				if(eqpTypeId.equals(eqpType.getValue()))
				{
					eqpTypeName=eqpType.getLabel();
					break;
				}
				
			}
			String optType = meterialOpt.getOPT_TYPE();
			if(StringUtils.equals(MaterialOptType.MRQ.name(), optType))
			{
				List <TbSysLookup> mrStatus=meterialOptService.findMeterStatus("INV_MR_STATUS");
				for (TbSysLookup lookup : mrStatus) {
					if(lookup.getCODE().equals(meterialOpt.getSTATUS()))
					{
						showStatus=lookup.getNAME();
						break;
					}
				}
			}else if(StringUtils.equals(MaterialOptType.RTN.name(), optType))
			{
				List <TbSysLookup> rtStatus=meterialOptService.findMeterStatus("INV_RT_STATUS");
				for (TbSysLookup lookup : rtStatus) {
					if(lookup.getCODE().equals(meterialOpt.getSTATUS()))
					{
						showStatus=lookup.getNAME();
						break;
					}
				}
			}
			

			List<LabelValueModel> workTypeList=WorkType.getLabelValueList("ST004");
			for (LabelValueModel workType : workTypeList) {
				if(workType.getValue().equals(meterialOpt.getWORK_TYPE()))
				{
					showWorkType=workType.getLabel();
					break;
				}
				
			}
			
			if(meterialOpt.getREQ_DATE()!=null)
			{
				showReqDate=sdformat.format(meterialOpt.getREQ_DATE());
			}
			
			if(meterialOpt.getAPP_USER() != null) {
				UserDTO user = personnelService.getUserDto(meterialOpt.getAPP_USER());
				showAppUserName = user.getChName();
			}
			
			TbAuthPersonnelExample example = new TbAuthPersonnelExample();
			example.createCriteria().andPSN_NOEqualTo(getLoginUser().getUsername());
			TbOrgDept tbOrgDept=meterialOptService.findOrgDeptById(personnelService.getPersonnelsByExample(example).get(0).getDEPT_ID());
			showDeptName=tbOrgDept.getDEPT_NAME();
		}
		model.put("meterialOpt", meterialOpt);
		model.put("showAplTime", appTime);
		model.put("btsSiteId", btsSiteId);
		model.put("eqpTypeId", eqpTypeId);
		model.put("eqpTypeName", eqpTypeName);
		model.put("showStatus", showStatus);
		model.put("showWorkType", showWorkType);
		model.put("showDeptName", showDeptName);
		model.put("showReqDate", showReqDate);
		model.put("showAppUserName", showAppUserName);
		model.put("siteId", siteId);
		
		return new BaseJasonObject<>(model, AJAX_SUCCESS);
	}
	
	/**
	 * 領料申請簽核資料
	 * @param optId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/st/invMaterial/sign/materialOptById")
	public String materialOpt(@RequestParam("optId") String optId,
			Map<String, Object> model) {
		log.debug("optId = " + optId);
		TbInvMaterialOpt meterialOpt = meterialOptService.getByPk(optId);
		
		if(meterialOpt != null && StringUtils.isNotEmpty(meterialOpt.getOPT_DESC())){
			meterialOpt.setOPT_DESC(meterialOpt.getOPT_DESC().replaceAll("(\n|\r\n)", "\\\\n"));
		}
		
		String appTime="";
		String btsSiteId="";
		String eqpTypeId="";
		String eqpTypeName="";
		String showStatus="";
		String showWorkType="";
		String showReqDate="";
		String siteId="";
		String showAppUserName="";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy/MM/dd");
		
		appTime=sdf.format(meterialOpt.getAPP_TIME());
		
		TbSiteWork siteWork=meterialOptService.getbSiteWork(meterialOpt.getORDER_ID());
		btsSiteId=siteWork.getBTS_SITE_ID();
		eqpTypeId=siteWork.getEQP_TYPE_ID();
		siteId=siteWork.getSITE_ID();
		List<LabelValueModel> eqpTypes = st004Service.getEqpTypes();
		for (LabelValueModel eqpType : eqpTypes) {
			if(eqpTypeId.equals(eqpType.getValue())){
				eqpTypeName=eqpType.getLabel();
			}
			
		}
		
		if(MaterialOptType.MRQ.name().equals(meterialOpt.getOPT_TYPE())){
			List <TbSysLookup> mrStatus=meterialOptService.findMeterStatus("INV_MR_STATUS");
			for (TbSysLookup lookup : mrStatus) {
				if(lookup.getCODE().equals(meterialOpt.getSTATUS())){
					showStatus=lookup.getNAME();
				}
			}
		}else if(MaterialOptType.RTN.name().equals(meterialOpt.getOPT_TYPE())){
			List <TbSysLookup> rtStatus=meterialOptService.findMeterStatus("INV_RT_STATUS");
			for (TbSysLookup lookup : rtStatus) {
				if(lookup.getCODE().equals(meterialOpt.getSTATUS())){
					showStatus=lookup.getNAME();
				}
			}
		}
		

		List<LabelValueModel> workTypeList=WorkType.getLabelValueList("ST004");
		for (LabelValueModel workType : workTypeList) {
			if(workType.getValue().equals(meterialOpt.getWORK_TYPE())){
				showWorkType=workType.getLabel();
			}
			
		}
		
		if(meterialOpt.getREQ_DATE()!=null){
			showReqDate=sdformat.format(meterialOpt.getREQ_DATE());
		}
		
		if(meterialOpt.getAPP_USER() != null) {
			UserDTO user = personnelService.getUserDto(meterialOpt.getAPP_USER());
			showAppUserName = user.getChName();
		}
		
		TbAuthPersonnelExample example = new TbAuthPersonnelExample();
		example.createCriteria().andPSN_NOEqualTo(getLoginUser().getUsername());
		TbOrgDept tbOrgDept=meterialOptService.findOrgDeptById(personnelService.getPersonnelsByExample(example).get(0).getDEPT_ID());
	
		TbInvWarehouse tbInvWarehouse = meterialOptService.selectWarehouseByPrimaryKey(meterialOpt.getWH_ID());
	
		model.put("meterialOpt", meterialOpt);
		model.put("showAplTime", appTime);
		model.put("btsSiteId", btsSiteId);
		model.put("eqpTypeId", eqpTypeId);
		model.put("eqpTypeName", eqpTypeName);
		model.put("showStatus", showStatus);
		model.put("showWorkType", showWorkType);
		model.put("showDeptName", tbOrgDept.getDEPT_NAME());
		model.put("showReqDate", showReqDate);
		model.put("siteId", siteId);
		model.put("sign","sign");
		model.put("whName", tbInvWarehouse.getWh_name());
		model.put("optTypeName", MaterialOptType.detectLabel(meterialOpt.getOPT_TYPE()));
		model.put("showAppUserName", showAppUserName);
		
		return "/ajaxPage/st/MeterialOptM";
	}
	
	/**
	 * 查詢物料操作單明細資料
	 * 
	 * @param orderId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/st/invMeterial/itemMainQuery")
	@ResponseBody
	public BaseJasonObject<SiteMtDetailImportDTO> getItemMainQuery(
			@RequestParam("optId") String optId, Map<String, Object> model) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("optId", optId);

		List<SiteMtDetailImportDTO> list = meterialOptService.getItemMainQuery(map);

		return new BaseJasonObject<>(list, AJAX_SUCCESS, AJAX_EMPTY);
	}
	
	/**
	 * 查詢物料操作單-退料在途查詢
	 * 
	 * @param orderId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/st/invMeterial/rtntrItemByRtn")
	@ResponseBody
	public BaseJasonObject<MeterialRtnDTO> getRtntrItemByRtn(
			@RequestParam("orderId") String orderId,@RequestParam("whId") String whId, Map<String, Object> model) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("osId", orderId);
		
		List<MeterialRtnDTO> list = meterialOptService.getRemInsItemQuery(map);
		return new BaseJasonObject<>(list, AJAX_SUCCESS, AJAX_EMPTY);
	}
	
	/**
	 * 查詢物料操作單-安裝在途查詢
	 * 
	 * @param orderId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/st/invMeterial/remInsItemQuery")
	@ResponseBody
	public BaseJasonObject<MeterialRtnDTO> remInsItemQuery(
			@RequestParam("orderId") String orderId, Map<String, Object> model) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("osId", orderId);
		List<MeterialRtnDTO> list = meterialOptService.getRemInsItemQuery(map);
		return new BaseJasonObject<>(list, AJAX_SUCCESS, AJAX_EMPTY);
	}
	
	/**
	 * 查詢物料操作單-拆下站上數量查詢
	 * 
	 * @param orderId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/st/invMeterial/remItemQuery")
	@ResponseBody
	public BaseJasonObject<MeterialRtnDTO> remItemQuery(
			@RequestParam("orderId") String orderId, String siteId,Map<String, Object> model) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("osId", orderId);
		map.put("siteId", siteId);
		List<MeterialRtnDTO> list = meterialOptService.getRemItemQuery(map);
		return new BaseJasonObject<>(list, AJAX_SUCCESS, AJAX_EMPTY);
	}
	
	/**
	 * 查詢物料操作單明細資料
	 * 
	 * @param optId
	 * @param model
	 * @return7
	 */
	@RequestMapping(value = "/st/invMeterial/rtntrItemByOptId")
	@ResponseBody
	public BaseJasonObject<MeterialRtnDTO> getRtntrItemByOptId(
			@RequestParam("optId") String optId,Map<String, Object> model) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("optId", optId);
		List<MeterialRtnDTO> list = meterialOptService.getRtntrItemByOptId(map);

		return new BaseJasonObject<>(list, AJAX_SUCCESS, AJAX_EMPTY);
	}
	
	/**
	 * 查詢物料安裝明細資料
	 * 
	 * @param optId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/st/invMeterial/remInsItemByOptId")
	@ResponseBody
	public BaseJasonObject<MeterialRtnDTO> getRemInsItemByOptId(
			String optId,Map<String, Object> model) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("optId", optId);
		
		List<MeterialRtnDTO> list = meterialOptService.getRemInsItemByOptId(map);

		return new BaseJasonObject<>(list, AJAX_SUCCESS, AJAX_EMPTY);
	}
	
	/**
	 * 查詢物料拆除明細資料
	 * 
	 * @param optId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/st/invMeterial/remItemByOptId")
	@ResponseBody
	public BaseJasonObject<MeterialRtnDTO> getRemItemByOptId(
			@RequestParam("optId") String optId,Map<String, Object> model) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("optId", optId);
		
		List<MeterialRtnDTO> list = meterialOptService.getRemItemByOptId(map);

		return new BaseJasonObject<>(list, AJAX_SUCCESS, AJAX_EMPTY);
	}
	
	/**
	 * 匯入物料操作單明細資料
	 * 
	 * @param orderId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/st/invMeterial/itemExcel")
	@ResponseBody
	public BaseJasonObject<SiteMtDetailImportDTO> getitemExcel(
			@RequestParam("itemData") String itemData, Map<String, Object> model) {

		List<SiteMtDetailImportDTO> list =meterialOptService.transform(itemData);

		return new BaseJasonObject<>(list, AJAX_SUCCESS, AJAX_EMPTY);
	}
	
	/**
	 * 物料操作-領料申請
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/st/invMeterial/mtApply")
	@ResponseBody
	public BaseJasonObject<TbInvMaterialOpt> mtApply(String optId,
			@RequestParam("optType") String optType,
			@RequestParam("orderId") String orderId,
			@RequestParam("osId") String osId,
			@RequestParam("whId") String whId,
			@RequestParam("reqDate") Date reqDate,
			@RequestParam("showOptDesc") String optDesc, String jsonArrayStr) {
		
		
		TbInvMaterialOpt tbInvMaterialOpt = null;
		try{
			//主檔
			tbInvMaterialOpt = meterialOptService.mtApply(optId, optType, orderId, osId, whId, reqDate,
							optDesc, "MR01", jsonArrayStr);
			
		} catch (Exception e){
			log.error(e.getMessage() , e);
			log.error("領料申請送審失敗！！");
			return new BaseJasonObject<>(tbInvMaterialOpt, AJAX_FAILED);
		}
		
		return new BaseJasonObject<>(tbInvMaterialOpt, AJAX_FAILED);
	}
	
	/**
	 * 物料操作-安裝
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/st/invMeterial/mtSetup")
	@ResponseBody
	public BaseJasonObject<TbInvMaterialOpt> mtSetup(
			@RequestParam("optType")  String optType,
			@RequestParam("orderId")  String orderId, 
			@RequestParam("osId")   String osId,
			@RequestParam("reqDate")     Date reqDate,
			@RequestParam("showOptDesc")     String optDesc,
			@RequestParam("jsonArrayString")     String jsonArrayString,
			Map<String, Object> model) {
		
		TbInvMaterialOpt tbInvMaterialOpt = null;
		
		try{
			
		   tbInvMaterialOpt = meterialOptService.mtSetup(optType, orderId, osId, reqDate, optDesc, jsonArrayString);
		} catch(Exception e){
			log.error(e.getMessage() , e);
			return new BaseJasonObject<TbInvMaterialOpt>(tbInvMaterialOpt, "安裝失敗！！");
		}
		 
		
		return  new BaseJasonObject<TbInvMaterialOpt>(tbInvMaterialOpt, "安裝成功！！");
	}
	
	/**
	 * 物料操作-拆除
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/st/invMeterial/mtUnload")
	@ResponseBody
	public BaseJasonObject<TbInvMaterialOpt> mtUnload(
			@RequestParam("optType")  String optType,
			@RequestParam("orderId")  String orderId, 
			@RequestParam("osId")   String osId,
			@RequestParam("reqDate")     Date reqDate,
			@RequestParam("showOptDesc")     String optDesc,
			@RequestParam("jsonArrayString")     String jsonArrayString,
			Map<String, Object> model) {
		TbInvMaterialOpt tbInvMaterialOpt = null;
		try{
			tbInvMaterialOpt =  meterialOptService.mtUnload(optType, orderId, osId, reqDate, optDesc,jsonArrayString);
		}catch(Exception e){
			log.error(e.getMessage() , e);
			return new BaseJasonObject<TbInvMaterialOpt>(tbInvMaterialOpt, "拆除失敗！！");
		}
		
		return new BaseJasonObject<TbInvMaterialOpt>(tbInvMaterialOpt, "拆除成功！！");
	}
	
	/**
	 * 物料操作-退料申請
	 * @param optType
	 * @param orderId
	 * @param osId
	 * @param whId
	 * @param showOptDesc
	 * @param jsonArrayString
	 * @return
	 */
	@RequestMapping(value = "/st/invMeterial/mtReturn")
	@ResponseBody
	public BaseJasonObject<TbInvMaterialOpt> mtReturn(
			@RequestParam("optType") String optType,
			@RequestParam("orderId") String orderId,
			@RequestParam("osId") String osId,
			@RequestParam("whId") String whId,
			@RequestParam("showOptDesc") String optDesc,
			@RequestParam("jsonArrayString") String jsonArrayString) {
		
		TbInvMaterialOpt tbInvMaterialOpt = null;
		try{
			tbInvMaterialOpt=meterialOptService.mtReturn(optType, orderId, osId, whId, optDesc, jsonArrayString);
		}catch(JSONException e){
			log.error(e.getMessage() ,e);
			return new BaseJasonObject<TbInvMaterialOpt>(tbInvMaterialOpt,"退料失敗！");
		}
		return new BaseJasonObject<>(tbInvMaterialOpt,"退料成功！");
	}
	
	/**
	 * 匯出excel
	 * @return
	 */
	@RequestMapping(value = "/st/invMeterial/getMeterialApplyExcel", method = RequestMethod.GET) 
	public void getExcel(HttpServletRequest request,HttpServletResponse response,
			Map<String, Object> model,@RequestParam("orderId") String orderId, String whId, String osId) throws Exception { 

		TbSiteWork siteWork=meterialOptService.getbSiteWork(orderId);
		
		String tempFilePath=request.getServletContext().getRealPath("/")+"resources/MtImportTemplate.xls";
		Map<String,String> data = new HashMap<String,String>();
		data.put("eqyModel",siteWork.getEQP_MODEL_ID());
		data.put("whId",whId);
		List<SiteMtDetailImportDTO> mDetailList = meterialOptService.selectMeterialApplyExcel(data);
		
		TbInvMaterialOptDTO tbInvMaterialOptDTO = meterialOptService.selectForExportMaterialExeclTitle(orderId);
		tbInvMaterialOptDTO.setOS_ID(osId); // 由畫面上派工單號下拉選單下來
		TbInvWarehouse warehouse = meterialOptService.selectWarehouseByPrimaryKey(whId);
		
		if(warehouse != null){
			tbInvMaterialOptDTO.setWH_ID(whId);
			tbInvMaterialOptDTO.setWH_NAME(warehouse.getWh_name());
		}
		
		//file name
		String fileName = "MtImportTemplate.xls";
		
		meterialOptService.exportExcel(response, "sheet", fileName,tempFilePath, mDetailList, tbInvMaterialOptDTO);
    }
	
	/**
	 * 上傳物料檔
	 */
	@RequestMapping(value="/st/invMeterial/uploadMeterialApplyExcelInit")
	public String processPage(HttpServletRequest request,Map<String, Object> model,@RequestParam("callBackFun") String callBackFun,
			@RequestParam("targetFrameId") String targetFrameId) {
		model.put("callBackFun", callBackFun);
		model.put("targetFrameId", targetFrameId);
		return "/ajaxPage/st/ImportMT";		
	}
	
	/**
	 * 匯入物料 excel報表
	 * 
	 * @param mutipartRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/st/invMeterial/uploadExcel", method = RequestMethod.POST)
	@ResponseBody
	public BaseJasonObject<SiteMtDetailImportDTO> handleUploadProcess(
			MultipartHttpServletRequest mutipartRequest) {
		List<SiteMtDetailImportDTO> list =null;
		try{
			
			// 讀取報表
			for (Iterator<String> itr = mutipartRequest.getFileNames(); itr.hasNext();) {
				MultipartFile file = mutipartRequest.getFile(itr.next());
				list = new MeterialOptDService().readReport(file.getInputStream());
			}
		}catch(NumberFormatException nfe){
			log.error(nfe.getMessage(), nfe);
			return new BaseJasonObject<>(list, AJAX_FAILED, nfe.getLocalizedMessage());
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			return new BaseJasonObject<>(list, AJAX_FAILED, "讀檔發生錯誤！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new BaseJasonObject<>(list, AJAX_FAILED, "發生未預知的錯誤！！");
		}

		return new BaseJasonObject<>(list, AJAX_SUCCESS, AJAX_EMPTY);
	}
	
}
