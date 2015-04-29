package com.foya.noms.web.controller.st;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foya.dao.mybatis.model.TbAuthPersonnel;
import com.foya.dao.mybatis.model.TbAuthPersonnelExample;
import com.foya.dao.mybatis.model.TbComSiteFeq;
import com.foya.dao.mybatis.model.TbComSiteFeqExample;
import com.foya.dao.mybatis.model.TbOrgDept;
import com.foya.dao.mybatis.model.TbOrgDeptExample;
import com.foya.dao.mybatis.model.TbSiteLocation;
import com.foya.dao.mybatis.model.TbSiteWork;
import com.foya.dao.mybatis.model.TbSiteWorkOrder;
import com.foya.dao.mybatis.model.TbSiteWorkOrderExample;
import com.foya.dao.mybatis.model.TbSiteWorkSiteTempKey;
import com.foya.dao.mybatis.model.TbSysOrderType;
import com.foya.dao.mybatis.model.TbSysOrderTypeExample;
import com.foya.dao.mybatis.model.TbSysWorkOrderType;
import com.foya.dao.mybatis.model.TbSysWorkOrderTypeExample;
import com.foya.exception.CreateFailException;
import com.foya.exception.NomsException;
import com.foya.exception.UpdateFailException;
import com.foya.noms.dto.basic.CompanyDTO;
import com.foya.noms.dto.common.TownDomainTeamDTO;
import com.foya.noms.dto.org.DeptDTO;
import com.foya.noms.dto.st.SiteBuildApplyDTO;
import com.foya.noms.dto.st.SiteDTO;
import com.foya.noms.dto.st.SiteWorkDTO;
import com.foya.noms.dto.st.SiteWorkOrderDTO;
import com.foya.noms.enums.BuildAim;
import com.foya.noms.enums.IncludeRange;
import com.foya.noms.enums.Level;
import com.foya.noms.enums.OrderStatus;
import com.foya.noms.enums.PurchaseOrderType;
import com.foya.noms.enums.SiteStatus;
import com.foya.noms.enums.WorkStatus;
import com.foya.noms.enums.WorkType;
import com.foya.noms.service.auth.PersonnelService;
import com.foya.noms.service.org.ORG002Service;
import com.foya.noms.service.st.ST001Service;
import com.foya.noms.service.st.ST002SP1Service;
import com.foya.noms.service.st.ST003Service;
import com.foya.noms.service.st.ST004Service;
import com.foya.noms.service.st.ST005Service;
import com.foya.noms.service.st.ST009Service;
import com.foya.noms.service.st.ST010Service;
import com.foya.noms.service.system.OrderTypeService;
import com.foya.noms.util.BaseJasonObject;
import com.foya.noms.util.JqGridData;
import com.foya.noms.util.LabelValueModel;
import com.foya.noms.web.controller.BaseController;
import com.foya.workflow.exception.WorkflowException;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Controller
@RequestMapping(value = "/st/st010")
public class ST010Controller extends BaseController {
	
	@Autowired
	private ST003Service sT003Service;
	@Autowired
	private ST004Service st004Service;
	@Autowired
	private ST009Service sT009Service;
	@Autowired
	private ST005Service sT005Service;
	@Autowired
	private PersonnelService personnelService;
	@Autowired
	private ST002SP1Service sT002SP1Service;
	@Autowired
	private OrderTypeService orderTypeService;
	@Autowired
	private ST001Service sT001Service;
	@Autowired
	private ORG002Service org002Service;
	@Autowired
	private ST010Service sT010Service;

	/**
	 * 查詢頁
	 * @return
	 */
	@RequestMapping(value="init")
	public String initST003Page(HttpServletRequest request,
			Map<String, Object> model) {
		List<String> list = new ArrayList<String>();
		Set<String> tempDeptList = getLoginUser()
		.getAccessDeptListByMenuID(Integer.valueOf(request.getParameter("menuId")));
		if(tempDeptList!=null){
			list.addAll(tempDeptList);
		}
		//接工單位
		List<LabelValueModel> deptList = sT009Service.getAppDeptAll(list);
		model.put("allRepDept", deptList);
		model.put("allOrderStatus", OrderStatus.getLabelValueListOnProcess(true));
		
		//查詢條件 委外廠商 下拉選單 		
		model.put("logInCovatNo", getLoginUser().isVendor());
		if (getLoginUser().isVendor() == true) {
			String coVatNo = getLoginUser().getCoVatNo();
			List<CompanyDTO> companyList = sT003Service.getCompany(coVatNo);
			model.put("allCompany", companyList);
			model.put("covatNo", companyList.get(0).getCO_VAT_NO());
		} else {
			List<CompanyDTO> companyList = sT003Service.getCompanyAll();
			model.put("allCompany", companyList);
		}
		return "/ajaxPage/st/ST010L";
	}
	
	/**
	 * 查詢
	 * 
	 * @return
	 */
	@RequestMapping(value = "/search")
	@ResponseBody
	public JqGridData<SiteWorkOrderDTO> search(HttpServletRequest request,
			@RequestParam("workId") String workId,
			//@RequestParam("orderType") String orderType,
			@RequestParam("btsSiteId") String btsSiteId,
			@RequestParam("siteName") String siteName,
			@RequestParam("orderId") String orderId,
			@RequestParam("repDept") String repDept,
			@RequestParam("siteWorkCity") String siteWorkCity,
			@RequestParam("siteWorkArea") String siteWorkArea,
			@RequestParam("coVatNo") String coVatNo,		
			@RequestParam("orderStatus") String orderStatus,
			@RequestParam("siteOrderType") String siteOrderType) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("workId", workId);
		//map.put("orderType", orderType);
		map.put("btsSiteId", btsSiteId);
		map.put("siteName", siteName);
		map.put("orderId", orderId);
		map.put("repDept", repDept);
		map.put("siteWorkCity", siteWorkCity);
		map.put("siteWorkArea", siteWorkArea);
		map.put("coVatNo", coVatNo);
		map.put("siteOrderType", siteOrderType);
	
		List<String> list = new ArrayList<String>();
		log.debug("getCurrentMenuId() ::: " + getCurrentMenuId());
		Set<String> tempDeptList = getLoginUser().getAccessDeptListByMenuID(getCurrentMenuId());
		if(tempDeptList!=null){
			list.addAll(tempDeptList);
		}
		List<LabelValueModel> deptTempList = sT003Service.getDeptAll(list);
		
		if(repDept == null || repDept == "") {
			List<String> deptTemp = new ArrayList<String>();
			for(LabelValueModel temp :deptTempList) {
				if(StringUtils.isNotEmpty(temp.getValue())){
					deptTemp.add(temp.getValue());
				}
			}
			if(deptTemp != null || deptTemp.equals("")) {
				map.put("repDeptList", deptTemp);
			}
		}
		
		if (StringUtils.equalsIgnoreCase("todo", orderStatus)) {
			List<String> todoStatus = OrderStatus.getNotFinishedStatus();
			map.put("todoStatus", todoStatus);
		} else {
			map.put("orderStatus", orderStatus);// 工單狀態
		}
		
		List<TbOrgDept> deptList = org002Service.getByCondition(new TbOrgDeptExample());// sT003Service.getDeptAll(list);
		List<TbAuthPersonnel> authPersonnelList = personnelService.getAllPsn();// sT003Service.findPsnName(repUser);
		List<TbSysOrderType> orderTypes = orderTypeService.getAllOrderTypes();
		List<SiteWorkOrderDTO> siteWorkOrderList = sT010Service.getWorkLOrderist(map);
		if (!siteWorkOrderList.isEmpty()) {		
			map.put("orderId", siteWorkOrderList.get(0).getORDER_ID());
		}
		
		for (SiteWorkOrderDTO siteWorkOrder : siteWorkOrderList) {
			//工單狀態 代碼 --> 中文
			for (LabelValueModel orderStatusValue : OrderStatus.getLabelValueList()) {
				if (orderStatusValue.getValue().equals(siteWorkOrder.getODR_STATUS())) {
					siteWorkOrder.setOrderStatusName(orderStatusValue.getLabel());
					break;
				}
			}
			
			//公務類別 代碼 --> 中文
			for (LabelValueModel workTypeValue : WorkType.getLabelValueList()) {
				if (workTypeValue.getValue().equals(siteWorkOrder.getWORK_TYPE())) {
					siteWorkOrder.setWorkTypeName(workTypeValue.getLabel());
					break;
				}
			}
			
			//工單狀態 代碼 --> 中文
			for (LabelValueModel orderStatusValue : OrderStatus.getLabelValueList()) {
				if (orderStatusValue.getValue().equals(siteWorkOrder.getODR_STATUS())) {
					siteWorkOrder.setOrderStatusName(orderStatusValue.getLabel());
					break;
				}
			}
	
			//接工單位資料 代碼 --> 中文
			for (TbOrgDept repDeptAll : deptList) {
				if (StringUtils.equals(siteWorkOrder.getREP_DEPT(), repDeptAll.getDEPT_ID())) {
					siteWorkOrder.setDeptName(repDeptAll.getDEPT_NAME());
					break;
				}
			}			
			
			//負責人 代碼 -->中文
			String repUser = siteWorkOrder.getREP_USER();
			for (TbAuthPersonnel chnNM : authPersonnelList) {
				if (StringUtils.equals(repUser, chnNM.getPSN_NO())) {
					siteWorkOrder.setChnName(chnNM.getCHN_NM());
					break;
				}
			}
			
			// 查詢結果-派工目的
			for (TbSysOrderType orderType : orderTypes) {
				if (StringUtils.equals(siteWorkOrder.getORDER_TYPE(), orderType.getOD_TYPE_ID())) {
					siteWorkOrder.setOrderTypeName(orderType.getOD_TYPE_NAME());
					break;
				}
			}
		}
		PageList<SiteWorkOrderDTO> page = (PageList<SiteWorkOrderDTO>) siteWorkOrderList;
		return new JqGridData<>(page.getPaginator().getTotalCount(),
				siteWorkOrderList);
	}
	
	/**
	 * 設備型態 下拉選單
	 * 
	 * @return
	 */
	@RequestMapping(value = "/siteHuntEqp")
	@ResponseBody
	public BaseJasonObject<LabelValueModel> initSiteHuntEqpPage(HttpServletRequest request, Map<String, Object> model) {
		List<LabelValueModel> labelModelList = sT002SP1Service.getEqpTypes();
		return new BaseJasonObject<>(labelModelList, AJAX_SUCCESS, AJAX_EMPTY);
	}
	
	/**
	 * 工單類別
	 */
	@RequestMapping(value = "/siteOrderType")
	@ResponseBody
	public BaseJasonObject<TbSysOrderType> initSysOrderType(HttpServletRequest request, Map<String, Object> model) {
		TbSysWorkOrderTypeExample exampleType = new TbSysWorkOrderTypeExample();
		List<String> tempList = new ArrayList<String>();
		tempList.add("NSR");
		tempList.add("SH");
		exampleType.createCriteria().andWK_TYPE_IDNotIn(tempList);
		List<TbSysWorkOrderType> list = sT009Service.getSysWorkType(exampleType);
		
		List<String> temp = new ArrayList<String>();
		for (TbSysWorkOrderType listTemp : list) {
			temp.add(listTemp.getOD_TYPE_ID());
		}
		TbSysOrderTypeExample example = new TbSysOrderTypeExample(); 
		example.createCriteria().andOD_TYPE_IDIn(temp);
		List<TbSysOrderType> labelModelList = sT009Service.getSysOrderTypes(example);
		return new BaseJasonObject<>(labelModelList, AJAX_SUCCESS, AJAX_EMPTY);
	}	
	
	/**
	 * 新增 單張工單 頁面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/insertPage")
	public String insertPage(HttpServletRequest request, Map<String, Object> model) {
/*		model.put("siteHuntApplyEvent", "insert");
		model.put("allBuildAim", BuildAim.getLabelValueList());
		model.put("allEqpType", sT002SP1Service.getEqpTypes());
		model.put("allIncludeRange", IncludeRange.getLabelValueList());
		model.put("showBtn", true);*/
		model.put("singleOrderApplyEvent", "insert");
		model.put("allSiteStatus", SiteStatus.getLabelValueList("SB"));
		model.put("allSiteFeq", this.getSiteFeqModel(sT002SP1Service
				.getSiteFeqsByExample(new TbComSiteFeqExample())));
		model.put("allLevel", Level.getLabelValueList());
		model.put("showWorkType", WorkType.getLabelValueList("ST009").get(0).getValue());
		return "ajaxPage/st/ST009M";
	}
	
	private Map<String, LabelValueModel> getSiteFeqModel(List<TbComSiteFeq> siteFeqList) {
		Map<String, LabelValueModel> map = new HashMap<String, LabelValueModel>();
		for (TbComSiteFeq siteFeq : siteFeqList) {
			LabelValueModel labelValue = LabelValueModel.getLabelValueModel(siteFeq.getFEQ_NAME(),
					siteFeq.getFEQ_TYPE());
			map.put(siteFeq.getFEQ_ID(), labelValue);
		}
		return map;
	}
	
	/**
	 * 負責單位
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/responsibleUnit")
	@ResponseBody
	public BaseJasonObject<TbOrgDept> initResponsibleUnit(HttpServletRequest request, Map<String, Object> model,
			@RequestParam("odTypeId") String odTypeId, @RequestParam("eqpType") String eqpType,
			@RequestParam("city") String city, @RequestParam("area") String area) throws Exception {
		//利用city, area 查詢 domainTeam
		TownDomainTeamDTO domainTeam = sT009Service.domainTeam(city, area);
		//查詢 deptKey
		String domain = domainTeam.getDOMAIN();
		String deptKey = orderTypeService.getOrdersDeptKey(odTypeId, domain, eqpType);		
		//查詢負責單位
		TbOrgDept responsibleUnit = orderTypeService.getOrderTypeForWorkArea(deptKey, domainTeam);
		
		return new BaseJasonObject<>(responsibleUnit, AJAX_SUCCESS);
	}
	
	/**
	 * 儲存
	 * 
	 * @return
	 * @throws Exception 
	 * @throws CreateFailException 
	 * @throws NomsException 
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Map<String, Object> save(SiteBuildApplyDTO singleOrderApply,
			@RequestParam("singleOrderApplyEvent") String singleOrderApplyEvent,
			@RequestParam("orderId") String orderId) throws NomsException, CreateFailException, Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		//String errorMessage = st004Service.doValidate(singleOrderApply);
		log.debug("singleOrderApply : " + singleOrderApply.getBtsSiteId());
//			if(StringUtils.isEmpty(errorMessage)){
				if ("insert".equals(singleOrderApplyEvent)) {
					try {
						TbSiteWork siteWork = sT009Service.insert(singleOrderApply, getLoginUser().getDeptId());
						map.put("result", true);
						map.put("siteWork", siteWork);	
					}catch (CreateFailException ex) {
						map.put("result", this.getMessageDetail(ex.getErrCode()));
						log.error(ExceptionUtils.getFullStackTrace(ex));
					}  catch (Exception ex) {
						log.error("ex.getMessage() : " + ex.getMessage());
						log.error(ExceptionUtils.getFullStackTrace(ex));
						map.put("result", ex.getMessage());
					}
				} else {
					try {
						String[] orderIdArray = null;
						if (!orderId.equals("")) {
							orderIdArray = orderId.split(",");
						}
						sT009Service.update(singleOrderApply, orderIdArray,
								getLoginUser().getUsername(),null);
						map.put("result", true);
				} catch (UpdateFailException ex) {
					map.put("result", this.getMessageDetail(ex.getErrCode()));
					log.error(ExceptionUtils.getFullStackTrace(ex));
				} catch (Exception ex) {
					map.put("result", ex.getMessage());
					log.error(ExceptionUtils.getFullStackTrace(ex));
				}
			}
/*		} else {
			map.put("result", errorMessage);
		}*/
		return map;
	}
	
	/**
	 * 修改頁面
	 * 
	 * @param workId
	 * @return
	 */
	@RequestMapping(value = "/updatePage")
	public String getSiteBuildApplyByWorkId(Map<String, Object> model,
			@RequestParam("workId") String workId,
			@RequestParam("status") String status,
			@RequestParam("orderId") String orderId) {
		
		TbSiteWorkOrderExample order = new TbSiteWorkOrderExample();
		order.createCriteria().andORDER_IDEqualTo(orderId);
		List<TbSiteWorkOrder> orderDate = sT005Service.getSiteWorkOrderByOrderId(order);
		workId = orderDate.get(0).getWORK_ID();
		// 建站作業資料
		SiteWorkDTO siteWork = st004Service.getSiteWorkByWorkId(workId);
		//if(StringUtils.isNotBlank(workId)) {			
		TbSiteWorkOrder siteWorkOrder = sT003Service.selectByPrimaryKey(orderId);
		
		//接工人員
		TbAuthPersonnelExample pickUserExaple = new TbAuthPersonnelExample();
		String pickUser = "";
		if(siteWorkOrder.getPICK_USER() != null && !"".equals(siteWorkOrder.getPICK_USER())) {
			pickUserExaple.createCriteria().andPSN_NOEqualTo(siteWorkOrder.getPICK_USER());
			List<TbAuthPersonnel> authPersonnelList = personnelService.getPersonnelsByExample(pickUserExaple);
			if (authPersonnelList.size() > 0){
				pickUser = authPersonnelList.get(0).getCHN_NM();
			}
			
		}
		model.put("pickUser", pickUser);
		//}
		
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd");
		String dateFormat = sdFormat.format(siteWork.getPREDICE_END_DATE());
		model.put("prediceEndDate", dateFormat);
		model.put("workId", workId);
		model.put("showBtn", true);
		model.put("processType", "SiteBuildApply");
		model.put("status", status);
		model.put("showWorkType", WorkType.getLabelValueList("ST009").get(0).getValue());
		model.put("allSiteStatus", SiteStatus.getLabelValueList("SB"));
		model.put("locationId", siteWork.getLOCATION_ID());
		model.put("siteBuildSiteId", siteWork.getSITE_ID());
		model.put("initPage", siteWork.getWORK_TYPE());
		// 建站工單資料
		//String orderIdStr = "";
		List<SiteWorkOrderDTO> workOrderList= st004Service.getSiteWorkOrderByWorkId(workId);
		// 派工目的 轉中文
		if (workOrderList.size() > 0) {
			if (workOrderList.get(0).getORDER_TYPE() != null) {
				String orderType = workOrderList.get(0).getORDER_TYPE();
				TbSysOrderTypeExample orderTypeExample = new TbSysOrderTypeExample();
				orderTypeExample.createCriteria().andOD_TYPE_IDEqualTo(orderType);
				List<TbSysOrderType> sysOrderTypeList = sT005Service
						.getSysOrderTypeByOrderType(orderTypeExample);
				model.put("sysOrderTypeList", sysOrderTypeList);
			}

		}
		
		// 處理狀態
		model.put("workStatusName", WorkStatus.detectLabel(siteWork.getWORK_STATUS()));
		model.put("siteOrderType", workOrderList.get(0).getORDER_TYPE());
		model.put("showResponsibleUnit", "");
		model.put("siteResponsibleUnit", workOrderList.get(0).getREP_DEPT());
		model.put("orderId", workOrderList.get(0).getORDER_ID());
		model.put("odrStatus", workOrderList.get(0).getODR_STATUS());
		model.put("showOdrStatus", OrderStatus.detectLabel(workOrderList.get(0).getODR_STATUS()));
		// 工務類別
		model.put("allWorkType", WorkType.getLabelValueList("ST009"));
		// 重要等級
		model.put("allLevel", Level.getLabelValueList());
		// 基站頻段
		model.put("allSiteFeq", st004Service.getSiteFeqs());
		List<LabelValueModel> dataFeq = st004Service.getSiteFeqs();
		for (LabelValueModel temp : dataFeq) {
			if (temp.getValue().equals(siteWork.getFEQ_ID())) {
				model.put("feqName", temp.getLabel());
				break;
			}
		}
		model.put("siteWork", siteWork);
		model.put("singleOrderApplyEvent", "edit");

		// 作業類別
		model.put("workTypeName", "單張工單");
		// 處理狀態
		model.put("workStatusName",
				WorkStatus.detectLabel(siteWork.getWORK_STATUS()));

		model.put("userName", getLoginUser().getUsername());
		// 多頻段站點
		if (StringUtils.isNotEmpty(siteWork.getLOCATION_ID())) {
			TbSiteLocation location = sT001Service
					.getSiteLocationByLocationId(siteWork.getLOCATION_ID());
			model.put("location", location);
			List<SiteDTO> siteList = sT002SP1Service
					.getSiteByLocationId(siteWork.getLOCATION_ID());
			model.put("isMultibandLocation", siteList.size());
		}
		// 申請人員
		if (StringUtils.isNotEmpty(siteWork.getAPL_USER())) {
			TbAuthPersonnelExample example = new TbAuthPersonnelExample();
			example.createCriteria().andPSN_NOEqualTo(siteWork.getAPL_USER());
			model.put("applyUserName",personnelService.getPersonnelsByExample(example).get(0).getCHN_NM());
		}
		model.put("donorBtsSiteId", siteWork.getDONOR_SITE());

		model.put("masterBtsSiteId", siteWork.getMASTER_SITE());
		
		//工單類別
		model.put("siteOrderType", workOrderList.get(0).getORDER_TYPE());
		//負責單位
		model.put("#responsibleUnit", workOrderList.get(0).getREP_TEAM());

		TbSiteWorkOrderExample example = new TbSiteWorkOrderExample();
		example.createCriteria().andORDER_IDEqualTo(orderId);
		List<TbSiteWorkOrder> siteWorkOrderList = sT005Service.getSiteWorkOrderByOrderId(example);
		model.put("siteWorkOrderList", siteWorkOrderList);
		model.put("processType", "SiteBuildSingleWorkCompletionApply");	
		
		// 建站目的
		model.put("allBuildAim", BuildAim.getLabelValueList());
		// 工程類別
		model.put("poTypeList", PurchaseOrderType.getLabelValueList());
		// 設備型態
		model.put("allEqpTypes", st004Service.getEqpTypes());
		// 涵蓋範圍
		model.put("allIncludeRange", IncludeRange.getLabelValueList());
		SimpleDateFormat sdFormatEnd = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		if (siteWork.getEND_DATE() != null) {
			String endDate = sdFormatEnd.format(siteWork.getEND_DATE());				
			model.put("siteWorkEndTime", endDate);
		} else if (siteWork.getEND_TIME() != null) {
			String endDate = sdFormatEnd.format(siteWork.getEND_TIME());				
			model.put("siteWorkEndTime", endDate);				
		}
		
		// 作業類別
		model.put("workTypeName", WorkType.detectLabel(siteWork.getWORK_TYPE()));
		// 處理狀態
		model.put("workStatusName", WorkStatus.detectLabel(siteWork.getWORK_STATUS()));
		//工單類別
		model.put("orderType", siteWorkOrder.getORDER_TYPE());
		model.put("siteOrderType", siteWorkOrder.getORDER_TYPE());
		// 機房資訊
		TbSiteWorkSiteTempKey key = new TbSiteWorkSiteTempKey();
		key.setSITE_ID(siteWork.getSITE_ID());
		key.setWORK_ID(siteWorkOrder.getWORK_ID());
		model.put("siteWorkSiteTemp", sT005Service.getSiteTempByPrimaryKey(key));

		// 多頻段站點
		if (!StringUtils.isEmpty(siteWork.getLOCATION_ID())) {
			TbSiteLocation location = sT001Service.getSiteLocationByLocationId(siteWork
					.getLOCATION_ID());
			model.put("location", location);
			List<SiteDTO> siteList = sT002SP1Service.getSiteByLocationId(siteWork.getLOCATION_ID());
			model.put("isMultibandLocation", siteList.size());
		}
		// 申請人員
		if (!StringUtils.isEmpty(siteWork.getAPL_USER())) {
			TbAuthPersonnelExample example1 = new TbAuthPersonnelExample();
			example1.createCriteria().andPSN_NOEqualTo(siteWork.getAPL_USER());
			List<TbAuthPersonnel> applys = personnelService.getPersonnelsByExample(example1);
			String applyName = "";
			if (!applys.isEmpty()) {
				applyName = applys.get(0).getCHN_NM();
			}
			model.put("applyUserName", applyName);
		}
		model.put("donorBtsSiteId", siteWork.getDONOR_SITE());

		model.put("masterBtsSiteId", siteWork.getMASTER_SITE());
		
		TbAuthPersonnelExample userNameCH = new TbAuthPersonnelExample();
		userNameCH.createCriteria().andPSN_NOEqualTo(getLoginUser().getUsername());
		model.put("showUserName",personnelService.getPersonnelsByExample(userNameCH).get(0).getCHN_NM());
		
		model.put("userName", getLoginUser().getUsername());
		// log.info("廠商權限 = " + getLoginUser().isVendor());
		model.put("loginUsrVendor", getLoginUser().isVendor());

		// 派工目的 轉中文
		if (siteWorkOrderList.size() > 0) {
			if (siteWorkOrderList.get(0).getORDER_TYPE() != null) {
				String orderType = siteWorkOrderList.get(0).getORDER_TYPE();
				TbSysOrderTypeExample orderTypeExample = new TbSysOrderTypeExample();
				orderTypeExample.createCriteria().andOD_TYPE_IDEqualTo(orderType);
				List<TbSysOrderType> sysOrderTypeList = sT005Service
						.getSysOrderTypeByOrderType(orderTypeExample);
				model.put("sysOrderTypeList", sysOrderTypeList);
			}
		}
		model.put("view", status);
		
		return "ajaxPage/st/ST005M2";
	}
	
	/**
	 * 申請
	 * 
	 * @param workId
	 * @return
	 */
	@RequestMapping(value = "/updateApply")
	@ResponseBody
	public Map<String, Object> updateApplyInfo(SiteBuildApplyDTO siteBuildApply,
			@RequestParam("singleOrderApplyEvent") String singleOrderApplyEvent,
			@RequestParam("orderId") String orderId) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		String[] orderIdArray = null;
		if (!orderId.equals("")) {
			orderIdArray = orderId.split(",");
			log.debug("orderIdArray : "+orderIdArray.toString());
		}
		
		String errorMessage = st004Service.doValidate(siteBuildApply);
		if(StringUtils.isEmpty(errorMessage)){
			try {
				TbSiteWork siteWork = sT009Service.updateApplyInfo(getLoginUser()
						.getUsername(), siteBuildApply, singleOrderApplyEvent, getLoginUser().getDeptId(),orderIdArray);
				map.put("siteWork", siteWork);
				map.put("result", true);
			} catch (NomsException | WorkflowException e) {
				map.put("result", e.getMessage());
				log.error(ExceptionUtils.getFullStackTrace(e));
			} catch(UpdateFailException e){
				map.put("result", this.getMessageDetail(e.getErrCode()));
				log.error(ExceptionUtils.getFullStackTrace(e));
			} catch(Exception e){
				map.put("result", "申請失敗");
				log.error(ExceptionUtils.getFullStackTrace(e));
			}			
		}else{
			map.put("result", errorMessage);
		}
		return map;		
	}
	
	/**
	 * 取消申請
	 * 
	 * @param workId
	 * @return
	 */
	@RequestMapping(value = "/cancelApply")
	@ResponseBody
	public Map<String, Object> cancelApply(@RequestParam("workId") String workId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			TbSiteWork siteWork = sT009Service.cancelApplyInfo(workId, this
					.getLoginUser().getUsername());
			map.put("siteWork", siteWork);
			map.put("result", true);
		} catch (NomsException e) {
			map.put("result", e.getMessage());
		}
		return map;
	}
	
	/**
	 * 派工頁
	 */
	@RequestMapping(value = "assignPage")
	public String assignPage(Map<String, Object> model, @RequestParam("workId") String workId,
			@RequestParam("status") String status, @RequestParam("rep_DEPT") String rep_DEPT,
			@RequestParam("orderId") String orderId, HttpServletRequest request) {

		// 建站作業資料
		SiteWorkDTO siteWork = st004Service.getSiteWorkByWorkId(workId);

		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd");
		String dateFormat = sdFormat.format(siteWork.getPREDICE_END_DATE());
		model.put("prediceEndDate", dateFormat);
		model.put("workId", workId);
		model.put("showBtn", true);
		model.put("processType", "SiteBuildApply");
		model.put("status", status);
		model.put("showWorkType", WorkType.getLabelValueList("ST009").get(0).getValue());
		model.put("allSiteStatus", SiteStatus.getLabelValueList("SB"));
		model.put("locationId", siteWork.getLOCATION_ID());
		model.put("siteBuildSiteId", siteWork.getSITE_ID());
		model.put("workDesc", siteWork.getWORK_DESC());
		
		// 建站工單資料
		String orderIdStr = "";
		List<SiteWorkOrderDTO> workOrderList= st004Service.getSiteWorkOrderByWorkId(workId);
		if(workOrderList.size()>0){
			for(SiteWorkOrderDTO workOrder : workOrderList){
				if("Y".equals(workOrder.getIS_ACTIVE())){
					orderIdStr+=workOrder.getORDER_ID()+",";
				}
			}
			orderIdStr = orderIdStr.substring(0, orderIdStr.length()-1);
		}
		model.put("orderIdStr", orderIdStr);
		model.put("siteOrderType", workOrderList.get(0).getORDER_TYPE());
		model.put("showResponsibleUnit", "");
		model.put("siteResponsibleUnit", workOrderList.get(0).getREP_DEPT());

		
		// 工務類別
		model.put("allWorkType", WorkType.getLabelValueList("ST009"));
		// 重要等級
		model.put("allLevel", Level.getLabelValueList());
		// 基站頻段
		model.put("allSiteFeq", st004Service.getSiteFeqs());

		model.put("siteWork", siteWork);
		
		List<LabelValueModel> dataFeq = st004Service.getSiteFeqs();
		for (LabelValueModel temp : dataFeq) {
			if (temp.getValue().equals(siteWork.getFEQ_ID())) {
				model.put("feqName", temp.getLabel());
				break;
			}
		}
		
		
		model.put("singleOrderApplyEvent", "edit");

		// 作業類別
		model.put("workTypeName", "單張工單");
		// 處理狀態
		model.put("workStatusName",
				WorkStatus.detectLabel(siteWork.getWORK_STATUS()));

		// 多頻段站點
		if (StringUtils.isNotEmpty(siteWork.getLOCATION_ID())) {
			TbSiteLocation location = sT001Service
					.getSiteLocationByLocationId(siteWork.getLOCATION_ID());
			model.put("location", location);
			List<SiteDTO> siteList = sT002SP1Service
					.getSiteByLocationId(siteWork.getLOCATION_ID());
			model.put("isMultibandLocation", siteList.size());
		}
		// 申請人員
		if (StringUtils.isNotEmpty(siteWork.getAPL_USER())) {
			TbAuthPersonnelExample example = new TbAuthPersonnelExample();
			example.createCriteria().andPSN_NOEqualTo(siteWork.getAPL_USER());
			model.put("applyUserName",personnelService.getPersonnelsByExample(example).get(0).getCHN_NM());
		}
		model.put("donorBtsSiteId", siteWork.getDONOR_SITE());

		model.put("masterBtsSiteId", siteWork.getMASTER_SITE());
		
		String isApplyPage = request.getParameter("isApplyPage");
		if("Y".equals(isApplyPage)){
			model.put("isApplyPage", isApplyPage);
		}
		

		// 用接工單位DeptID 查詢出HIERARCHY 為 負責單位下拉選單 like 條件
		DeptDTO DeptType = sT003Service.getDeptIdByHierarchy(rep_DEPT);
		String hierarchy = DeptType.getHIERARCHY();
		List<TbOrgDept> responsibleUnitList = sT003Service.findResponsibleUnitList(hierarchy);
		log.info("rep_DEPT = " + rep_DEPT);
		if (!responsibleUnitList.isEmpty()) {
			model.put("responsibleUnit", responsibleUnitList);
		} else {
			String deptId = rep_DEPT;
			List<TbAuthPersonnel> authPersonnelList = sT003Service.findDeptIdByPsnName(deptId);
			model.put("psnNoList", authPersonnelList);

		}
		
		TbSiteWorkOrderExample example = new TbSiteWorkOrderExample();
		example.createCriteria().andORDER_IDEqualTo(orderId);
		List<TbSiteWorkOrder> siteWorkOrderList = sT005Service.getSiteWorkOrderByOrderId(example);
		model.put("siteWorkOrderList", siteWorkOrderList);
		model.put("orderId", siteWorkOrderList.get(0).getORDER_ID());
		model.put("orderType", siteWorkOrderList.get(0).getORDER_TYPE());
		// 派工目的 轉中文
		if (siteWorkOrderList.size() > 0) {
			if (siteWorkOrderList.get(0).getORDER_TYPE() != null) {
				String orderType = siteWorkOrderList.get(0).getORDER_TYPE();
				TbSysOrderTypeExample orderTypeExample = new TbSysOrderTypeExample();
				orderTypeExample.createCriteria().andOD_TYPE_IDEqualTo(orderType);
				List<TbSysOrderType> sysOrderTypeList = sT005Service
						.getSysOrderTypeByOrderType(orderTypeExample);
				model.put("sysOrderTypeList", sysOrderTypeList);
			}

		}
		
		model.put("repDept", rep_DEPT);
        TbOrgDept orgDept =  sT003Service.selectOrgDept(rep_DEPT);
		model.put("deptName", orgDept.getDEPT_NAME());
		
		return "/ajaxPage/st/ST010M1";
	}
	
	/**
	 * 接工
	 * 
	 * @param workId
	 * @param model
	 * @return
	 */
//	@RequestMapping(value = "/changePickWork")
//	@ResponseBody
//	public BaseJasonObject<Object> changePickWork(@RequestParam("orderId") String orderId,
//			Map<String, Object> model) {
//
//		try {			
//			TbSiteWorkOrder siteWorkOrder = new TbSiteWorkOrder();
//			siteWorkOrder.setORDER_ID(orderId);
//			siteWorkOrder.setODR_STATUS(OrderStatus.OR05.name());
//			siteWorkOrder.setPICK_TIME(new Date());
//			siteWorkOrder.setPICK_USER(getLoginUser().getUsername());
//			siteWorkOrder.setMD_TIME(new Date());
//			siteWorkOrder.setMD_USER(getLoginUser().getUsername());
//			sT005Service.updateSiteWorkOrderSelective(siteWorkOrder);
//			
//			return new BaseJasonObject<>(true, "接工完成！");
//		} catch (Exception e) {
//			log.error(ExceptionUtils.getFullStackTrace(e));
//		}
//
//		return new BaseJasonObject<>(false, "接工失敗！");
//	}
	
	/**
	 * 查詢編輯頁處理頁籤-for各別工單
	 * @param orderId
	 * @param model
	 * @return
	 * @author Charlie Woo
	 */
//	@RequestMapping(value = "tabOrderPage")
//	@ResponseBody
//	public BaseJasonObject<TbSysOrderPage> tabOrderPage(@RequestParam("orderId") String orderId,
//			Map<String, Object> model) {
//		log.debug("getOutSourceTable = " + orderId);
//
//		TbSiteWorkOrder siteWorkOrder = sT005Service.getOrderByKey(orderId);
//		log.info("siteWorkOrder = " + siteWorkOrder.getORDER_TYPE());
//
//		TbSysOrderPageExample pageExample = new TbSysOrderPageExample();
//		pageExample.createCriteria().andOD_TYPE_IDEqualTo(siteWorkOrder.getORDER_TYPE());
//		List<TbSysOrderPage> sysOrderPage = sT005Service.selectByExample(pageExample);
//
//		log.info("sysOrderPage = " + sysOrderPage.size());
//		return new BaseJasonObject<>(sysOrderPage, AJAX_SUCCESS, AJAX_EMPTY);
//	}
	
	/**
	 * 退工
	 * 
	 * @param orderId
	 * @author Charlie Woo
	 */
//	@RequestMapping(value = "/update/dropOrder")
//	@ResponseBody
//	public BaseJasonObject<Object> dropOrder(String workId, String endDesc) {
//		BaseJasonObject<Object> jsonObj = new BaseJasonObject<>(); 
//		try{
//			List<String> errorMessageList = sT005Service.dropOrder(workId, endDesc);
//			if(errorMessageList.isEmpty()){
//				jsonObj.setSuccess(true);
//				jsonObj.setMsg("退工完成！");
//			}else{
//				jsonObj.setSuccess(false);
//				jsonObj.setMsg("退工失敗！");
//				jsonObj.setErrors(errorMessageList);
//			}
//		}catch(UpdateFailException e){
//			log.error(ExceptionUtils.getFullStackTrace(e));
//			jsonObj.setSuccess(false);
//			jsonObj.setMsg("退工失敗！");
//		}catch(NomsException e){
//			log.error(ExceptionUtils.getFullStackTrace(e));
//			jsonObj.setSuccess(false);
//			jsonObj.setMsg("退工失敗！");
//		}
//		return jsonObj;
//	}
	
	
	/**
	 * 完工(送審核)
	 * 
	 * @param orderId
	 * @author Charlie Woo
	 * @throws Exception 
	 */
	@RequestMapping("verifyOrder")
	@ResponseBody
	public BaseJasonObject<Object> verifyOrder(String workId, String orderId) throws Exception{
		
		String msg = sT005Service.doValidate(workId, orderId);
		
		if(msg != null){
			return new BaseJasonObject<>(false, msg);
		}
		
		try{
			sT010Service.verifyOrder(orderId, workId, getLoginUser().getUsername());
		} catch(WorkflowException wfe){
			log.error(wfe.getMessage(), wfe);
			return new BaseJasonObject<>(true, "工單編號：" + workId + "完工送審失敗！" + wfe.getMessage());
		}
		
		return new BaseJasonObject<>(true, "工單編號：" + workId + "完工送審成功！");
	}
}
