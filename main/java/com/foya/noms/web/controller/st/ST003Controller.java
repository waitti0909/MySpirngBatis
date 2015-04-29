package com.foya.noms.web.controller.st;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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
import com.foya.dao.mybatis.model.TbOrgDeptPos;
import com.foya.dao.mybatis.model.TbOrgPsnPos;
import com.foya.dao.mybatis.model.TbSiteLocation;
import com.foya.dao.mybatis.model.TbSiteMain;
import com.foya.dao.mybatis.model.TbSiteSearchRing;
import com.foya.dao.mybatis.model.TbSiteWork;
import com.foya.dao.mybatis.model.TbSiteWorkOrder;
import com.foya.dao.mybatis.model.TbSysOrderPage;
import com.foya.dao.mybatis.model.TbSysOrderPageExample;
import com.foya.dao.mybatis.model.TbSysOrderType;
import com.foya.exception.NomsException;
import com.foya.exception.UpdateFailException;
import com.foya.noms.dao.common.TownDomainTeamDao;
import com.foya.noms.dao.st.SearchRingDao;
import com.foya.noms.dto.basic.CompanyDTO;
import com.foya.noms.dto.org.DeptDTO;
import com.foya.noms.dto.st.SiteDTO;
import com.foya.noms.dto.st.SiteHuntApplyDTO;
import com.foya.noms.dto.st.SiteWorkDTO;
import com.foya.noms.dto.st.SiteWorkOrderDTO;
import com.foya.noms.enums.BuildAim;
import com.foya.noms.enums.Feederless;
import com.foya.noms.enums.IncludeRange;
import com.foya.noms.enums.Level;
import com.foya.noms.enums.OrderStatus;
import com.foya.noms.enums.WorkStatus;
import com.foya.noms.enums.WorkType;
import com.foya.noms.service.auth.PersonnelService;
import com.foya.noms.service.org.ORG002Service;
import com.foya.noms.service.st.ST001Service;
import com.foya.noms.service.st.ST002SP1Service;
import com.foya.noms.service.st.ST002Service;
import com.foya.noms.service.st.ST003SP1Service;
import com.foya.noms.service.st.ST003Service;
import com.foya.noms.service.system.OrderTypeService;
import com.foya.noms.util.BaseJasonObject;
import com.foya.noms.util.DateUtils;
import com.foya.noms.util.JqGridData;
import com.foya.noms.util.LabelValueModel;
import com.foya.noms.web.controller.BaseController;
import com.foya.workflow.exception.WorkflowException;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Controller
@RequestMapping(value = "/st/st003")
public class ST003Controller extends BaseController {

	@Autowired
	private ST003Service sT003Service;
	@Autowired
	private ST003SP1Service sT003SP1Service;
	@Autowired
	private ST002Service sT002Service;
	@Autowired
	private ST001Service sT001Service;
	@Autowired
	private ST002SP1Service sT002SP1Service;
	@Autowired
	private PersonnelService personnelService;
	@Autowired
	private ORG002Service org002Service;
	@Autowired
	private OrderTypeService orderTypeService;
	@Autowired
	private SearchRingDao searchRingDao;
	@Autowired
	private TownDomainTeamDao townDomainTeamDao;
	/**
	 * 查詢頁
	 * 
	 * @return
	 */
	@RequestMapping(value = "init")
	public String initST003Page(HttpServletRequest request, Map<String, Object> model) {
		List<String> list = new ArrayList<String>();
		Set<String> tempDeptList = getLoginUser().getAccessDeptListByMenuID(Integer.valueOf(request.getParameter("menuId")));
		if (tempDeptList != null) {
			log.debug("tempDeptList.size()----->" + tempDeptList.size());
			list.addAll(tempDeptList);
		}
		for (String str : tempDeptList) {
			log.debug("STR >>>> " + str);
		}
		model.put("allOrderStatus", OrderStatus.getLabelValueListOnProcess(true)); // modify
																					// by
																					// Charlie
																					// 2014/12/27

		List<LabelValueModel> deptList = sT003Service.getDeptAll(list);

		model.put("allRepDept", deptList);

		// 查詢條件 委外廠商 下拉選單
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

		return "/ajaxPage/st/ST003L";
	}

	/**
	 * 查詢
	 * 
	 * @return
	 */
	@RequestMapping(value = "/search")
	@ResponseBody
	public JqGridData<SiteWorkOrderDTO> search(
			HttpServletRequest request,
			@RequestParam("workId") String workId,
			// @RequestParam("orderType") String orderType,
			@RequestParam("btsSiteId") String btsSiteId, @RequestParam("siteName") String siteName, @RequestParam("orderId") String orderId,
			@RequestParam("repDept") String repDept, @RequestParam("siteWorkCity") String siteWorkCity,
			@RequestParam("siteWorkArea") String siteWorkArea, @RequestParam("coVatNo") String coVatNo,
			@RequestParam("orderStatus") String orderStatus, @RequestParam("applyDateStart") String applyDateStart,
			@RequestParam("applyDateEnd") String applyDateEnd) {
		if (StringUtils.isNotEmpty(applyDateEnd)) {
			// log.debug("applyDateEnd : "+applyDateEnd);
			// log.debug("applyDateEnd : "+DateUtils.paserDate(applyDateEnd+" 00:00:00",
			// AppConstants.DATE_TIME_PATTERN));
			applyDateEnd += " 23:59:59";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("workId", workId);
		// map.put("orderType", orderType);
		map.put("btsSiteId", btsSiteId);
		map.put("siteName", siteName);
		map.put("orderId", orderId);
		map.put("repDept", repDept);
		map.put("siteWorkCity", siteWorkCity);
		map.put("siteWorkArea", siteWorkArea);
		map.put("coVatNo", coVatNo);
		map.put("applyDateStart", applyDateStart);
		map.put("applyDateEnd", applyDateEnd);

		List<String> list = new ArrayList<String>();
		log.debug("getCurrentMenuId() ::: " + getCurrentMenuId());
		Set<String> tempDeptList = getLoginUser().getAccessDeptListByMenuID(getCurrentMenuId());
		if (tempDeptList != null) {
			list.addAll(tempDeptList);
		}
		List<LabelValueModel> deptTempList = sT003Service.getDeptAll(list);

		if (repDept == null || repDept == "") {
			List<String> deptTemp = new ArrayList<String>();
			for (LabelValueModel temp : deptTempList) {
				if (StringUtils.isNotEmpty(temp.getValue())) {
					deptTemp.add(temp.getValue());
				}
			}
			if (deptTemp != null || deptTemp.equals("")) {
				map.put("repDeptList", deptTemp);
			}
		}
		
		if (StringUtils.equalsIgnoreCase("todo", orderStatus)) {
			List<String> todoStatus = OrderStatus.getNotFinishedStatus();
			map.put("todoStatus", todoStatus);
		} else {
			map.put("orderStatus", orderStatus); // 工單狀態
		}

		List<TbOrgDept> deptList = org002Service.getByCondition(new TbOrgDeptExample());// sT003Service.getDeptAll(list);
		List<TbAuthPersonnel> authPersonnelList = personnelService.getAllPsn();// sT003Service.findPsnName(repUser);
		List<TbSysOrderType> orderTypes = orderTypeService.getAllOrderTypes();

		List<SiteWorkOrderDTO> siteWorkOrderList = sT003Service.getWorkLOrderist(map);
		for (SiteWorkOrderDTO siteWorkOrder : siteWorkOrderList) {
			// 工單狀態 代碼 --> 中文
			for (LabelValueModel orderStatusValue : OrderStatus.getLabelValueList()) {
				if (orderStatusValue.getValue().equals(siteWorkOrder.getODR_STATUS())) {
					siteWorkOrder.setOrderStatusName(orderStatusValue.getLabel());
					break;
				}
			}

			// 公務類別 代碼 --> 中文
			for (LabelValueModel workTypeValue : WorkType.getLabelValueList()) {
				if (workTypeValue.getValue().equals(siteWorkOrder.getWORK_TYPE())) {
					siteWorkOrder.setWorkTypeName(workTypeValue.getLabel());
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

			// 接工單位資料 代碼 --> 中文
			for (TbOrgDept repDeptAll : deptList) {
				if (StringUtils.equals(siteWorkOrder.getREP_DEPT(), repDeptAll.getDEPT_ID())) {
					siteWorkOrder.setDeptName(repDeptAll.getDEPT_NAME());
					break;
				}
			}

			// 負責人 代碼 -->中文
			String repUser = siteWorkOrder.getREP_USER();
			for (TbAuthPersonnel chnNM : authPersonnelList) {
				if (StringUtils.equals(repUser, chnNM.getPSN_NO())) {
					siteWorkOrder.setChnName(chnNM.getCHN_NM());
					break;
				}
			}
		}
		PageList<SiteWorkOrderDTO> page = (PageList<SiteWorkOrderDTO>) siteWorkOrderList;
		return new JqGridData<>(page.getPaginator().getTotalCount(), siteWorkOrderList);
	}

	/**
	 * 派工頁
	 */
	@RequestMapping(value = "assignPage")
	public String assignPage(Map<String, Object> model, @RequestParam("orderId") String orderId, @RequestParam("workId") String workId,
			@RequestParam("status") String status, @RequestParam("rep_DEPT") String rep_DEPT) {
		String processType = "";
		SiteWorkDTO siteWork = sT002Service.getSiteWorkByWorkId(workId);
		// System.err.println((siteWork.getFEQ_ID()==null) + "," +
		// (siteWork.getFeqType()==null)); Do not use System.out
		log.debug((siteWork.getFEQ_ID() == null) + "," + (siteWork.getFeqType() == null));
		siteWork.setFEQ_ID(siteWork.getFEQ_ID() + "," + siteWork.getFeqType());
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd");
		String dateFormat = sdFormat.format(siteWork.getPREDICE_END_DATE());
		model.put("prediceEndDate", dateFormat);
		// List<TbComSiteFeq> siteFeqList = sT002SP1Service
		// .getSiteFeqsByExample(new TbComSiteFeqExample());
		// Map<String, LabelValueModel> map = new HashMap<String,
		// LabelValueModel>();
		// for (TbComSiteFeq siteFeq : siteFeqList) {
		// LabelValueModel labelValue = LabelValueModel.getLabelValueModel(
		// siteFeq.getFEQ_NAME(), siteFeq.getFEQ_TYPE());
		// map.put(siteFeq.getFEQ_ID(), labelValue);
		// }
		if (siteWork.getWORK_TYPE().equals("NSR")) {
			processType = "SearchSiteApplyNSR";
		} else {
			processType = "SearchSiteApplySH";
		}
		if (siteWork.getAPL_TIME() != null) {
			siteWork.setAPL_TIME(DateUtils.parse(siteWork.getAPL_TIME().toString(), "yyyy/MM/dd"));
		}
		model.put("showBtn", true);
		model.put("processType", processType);
		model.put("status", status);
		model.put("allBuildAim", BuildAim.getLabelValueList());
		model.put("allSiteFeq", this.getSiteFeqModel(sT002SP1Service.getSiteFeqsByExample(new TbComSiteFeqExample())));
		model.put("allEqpType", sT002SP1Service.getEqpTypes());
		model.put("allIncludeRange", IncludeRange.getLabelValueList());
		model.put("allFeederless", Feederless.getLabelValueList());
		model.put("allWorkType", WorkType.getLabelValueList("ST002"));
		model.put("allLevel", Level.getLabelValueList());
		model.put("siteWork", siteWork);
		model.put("workId", workId);
		model.put("orderId", orderId);
		model.put("siteHuntApplyEvent", "update");
		model.put("workTypeName", WorkType.detectLabel(siteWork.getWORK_TYPE()));
		model.put("workStatusName", WorkStatus.detectLabel(siteWork.getWORK_STATUS()));
		if (!StringUtils.isEmpty(siteWork.getLOCATION_ID())) {
			TbSiteLocation location = sT001Service.getSiteLocationByLocationId(siteWork.getLOCATION_ID());
			model.put("location", location);
			List<SiteDTO> siteList = sT002SP1Service.getSiteByLocationId(siteWork.getLOCATION_ID());
			model.put("isMultibandLocation", siteList.size());
		}
		if (!StringUtils.isEmpty(siteWork.getAPL_USER())) {
			String aplUser = "";
			TbAuthPersonnelExample example = new TbAuthPersonnelExample();
			example.createCriteria().andPSN_NOEqualTo(siteWork.getAPL_USER());
			List<TbAuthPersonnel> persons = personnelService.getPersonnelsByExample(example);
			if (!persons.isEmpty()) {
				aplUser = persons.get(0).getCHN_NM();
			}
			model.put("applyUserName", aplUser);
		}
		if (!StringUtils.isEmpty(siteWork.getDONOR_SITE())) {
			model.put("donorBtsSiteId", sT002SP1Service.getWorkSiteBySiteId(siteWork.getDONOR_SITE()).getBTS_SITE_ID());
		}
		if (!StringUtils.isEmpty(siteWork.getMASTER_SITE())) {
			model.put("masterBtsSiteId", sT002SP1Service.getWorkSiteBySiteId(siteWork.getMASTER_SITE()).getBTS_SITE_ID());
		}
		model.put("repDept", rep_DEPT);
		TbOrgDept orgDept = sT003Service.selectOrgDept(rep_DEPT);

		model.put("deptName", orgDept.getDEPT_NAME());

		// 用接工單位DeptID 查詢出HIERARCHY 為 負責單位下拉選單 like 條件
		DeptDTO DeptType = sT003Service.getDeptIdByHierarchy(rep_DEPT);
		String hierarchy = DeptType.getHIERARCHY();
		List<TbOrgDept> responsibleUnitList = sT003Service.findResponsibleUnitList(hierarchy);
		if (!responsibleUnitList.isEmpty()) {
			model.put("responsibleUnit", responsibleUnitList);
		} else {
			String deptId = rep_DEPT;
			List<TbAuthPersonnel> authPersonnelList = sT003Service.findDeptIdByPsnName(deptId);
			model.put("psnNoList", authPersonnelList);

		}
		return "/ajaxPage/st/ST003M1";
	}

	/**
	 * 派工 - 負責人 下拉選單
	 * 
	 * @param reques
	 * @param dept
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/personnelDept")
	@ResponseBody
	public BaseJasonObject<TbAuthPersonnel> initPersonnelDeptSelect(HttpServletRequest reques, String dept) throws Exception {
		// 負責人 下拉選單
		List<TbOrgDeptPos> repUserList = sT003Service.findRepUserList(dept);
		List<TbAuthPersonnel> personnel = new ArrayList<TbAuthPersonnel>();
		if (StringUtils.isNotBlank(dept)) {
			for (TbOrgDeptPos temp : repUserList) {
				String deptPosId = temp.getDEPT_POS_ID();
				List<TbOrgPsnPos> psnNoList = sT003Service.findPsnNo(deptPosId);

				for (TbOrgPsnPos tempName : psnNoList) {
					String psnNo = tempName.getPSN_NO();
					List<TbAuthPersonnel> authPersonnelList = sT003Service.findPsnName(psnNo);
					personnel.addAll(authPersonnelList);
				}
			}
			return new BaseJasonObject<TbAuthPersonnel>(personnel, AJAX_SUCCESS, AJAX_EMPTY);
		} else {
			// 如果沒有職稱，直接使用 接工單位 DeptId 尋找 負責人 預設
			String psnNo = dept;
			List<TbAuthPersonnel> authPersonnelList = sT003Service.findPsnName(psnNo);
			// List<TbAuthPersonnel> authPersonnelList = new
			// ArrayList<TbAuthPersonnel>();
			return new BaseJasonObject<TbAuthPersonnel>(authPersonnelList, AJAX_SUCCESS, AJAX_EMPTY);
		}
	}

	/**
	 * 派工
	 */
	@RequestMapping(value = "assignOrder")
	@ResponseBody
	public BaseJasonObject<String> assignOrder(@RequestParam("orderId") String orderId, @RequestParam("maintainTeam") String maintainTeam,
			@RequestParam("maintainUser") String maintainUser, Map<String, Object> model) {

		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orderId", orderId);
			map.put("maintainTeam", maintainTeam);
			map.put("maintainUser", maintainUser);
			sT003Service.assignOrder(map);
		} catch (NomsException nomsException) {
			log.error(nomsException.getMessage());
			return new BaseJasonObject<>(false, "派工失敗：" + nomsException.getMessage());
		}
		return new BaseJasonObject<>(true, "派工完成！");
	}

	/**
	 * 派工前檢查尚未完成前置工單數
	 * 
	 * @param workId
	 * @param orderId
	 * @return
	 */
	@RequestMapping(value = "/checkPreOrdersNotFinished")
	@ResponseBody
	public Integer checkPreOrdersNotFinished(@RequestParam("workId") String workId, @RequestParam("orderId") String orderId) {
		return sT003Service.getNotFinishedOrders(workId, orderId);
	}

	/**
	 * 編輯處理頁
	 * 
	 * @param model
	 * @param request
	 * @param orderId
	 * @param status
	 * @return
	 * @author Charlie Woo
	 */
	@RequestMapping(value = "updatePage")
	public String processPage(Map<String, Object> model, HttpServletRequest request, @RequestParam("orderId") String orderId,
			@RequestParam("status") String status) {

		TbSiteWorkOrder siteWorkOrder = sT003Service.selectByPrimaryKey(orderId);

		SiteWorkDTO siteWork = sT003Service.getSiteWorkByWorkId(siteWorkOrder.getWORK_ID());
		siteWork.setFEQ_ID(siteWork.getFEQ_ID() + "," + siteWork.getFeqType());

		String processType = "", finalProcessType = "";
		if (siteWork.getWORK_TYPE().equals(WorkType.NSR.name())) {
			processType = "SearchSiteApplyNSR";
			finalProcessType = "SearchSiteCompletionNSR";
		} else {
			processType = "SearchSiteApplySH";
			finalProcessType = "SearchSiteCompletionSH";
		}
		// if(siteWork.getAPL_TIME() != null){
		// siteWork.setAPL_TIME(DateUtils.parse(siteWork.getAPL_TIME().toString(),
		// "yyyy/MM/dd"));
		// }
		model.put("orderId", orderId);

		TbAuthPersonnelExample pickUserExaple = new TbAuthPersonnelExample();
		String pickUser = "";
		if (siteWorkOrder.getPICK_USER() != null && !"".equals(siteWorkOrder.getPICK_USER())) {
			pickUserExaple.createCriteria().andPSN_NOEqualTo(siteWorkOrder.getPICK_USER());
			pickUser = personnelService.getPersonnelsByExample(pickUserExaple).get(0).getCHN_NM();
		}
		model.put("view", status);
		model.put("showPickUser", pickUser);
		model.put("pickUser", siteWorkOrder.getPICK_USER());

		model.put("endDesc", siteWorkOrder.getEND_DESC());
		model.put("siteHuntApplyEvent", "update");
		model.put("siteWork", siteWork);
		model.put("showBtn", true);
		model.put("processType", processType);
		model.put("finalProcessType", finalProcessType);
		model.put("status", "edit");
		model.put("allBuildAim", BuildAim.getLabelValueList());
		model.put("allSiteFeq", this.getSiteFeqModel(sT002SP1Service.getSiteFeqsByExample(new TbComSiteFeqExample())));
		model.put("allEqpType", sT002SP1Service.getEqpTypes());
		model.put("orderTypeName", siteWork.getWORK_STATUS());
		model.put("applyType", request.getParameter("applyType"));
		// 涵蓋類別
		model.put("allIncludeRange", IncludeRange.getLabelValueList());

		model.put("allFeederless", Feederless.getLabelValueList());
		// 工務類別
		model.put("allWorkType", WorkType.getLabelValueList("ST002"));
		// 重要等級
		model.put("allLevel", Level.getLabelValueList());

		model.put("workTypeName", WorkType.detectLabel(siteWork.getWORK_TYPE()));
		model.put("workStatusName", WorkStatus.detectLabel(siteWork.getWORK_STATUS()));
		if (!StringUtils.isEmpty(siteWork.getLOCATION_ID())) {
			TbSiteLocation location = sT001Service.getSiteLocationByLocationId(siteWork.getLOCATION_ID());
			model.put("location", location);
			List<SiteDTO> siteList = sT002SP1Service.getSiteByLocationId(siteWork.getLOCATION_ID());
			model.put("isMultibandLocation", siteList.size());
		}
		if (!StringUtils.isEmpty(siteWork.getAPL_USER())) {
			TbAuthPersonnelExample example = new TbAuthPersonnelExample();
			example.createCriteria().andPSN_NOEqualTo(siteWork.getAPL_USER());
			model.put("applyUserName", personnelService.getPersonnelsByExample(example).get(0).getCHN_NM());
			/*
			 * model.put("applyUserName", personnelService
			 * .findPersonnelByPsnId(BigDecimal.valueOf(Integer
			 * .valueOf(siteWork.getAPL_USER()))));
			 */
		}
		if (!StringUtils.isEmpty(siteWork.getDONOR_SITE())) {
			TbSiteMain siteMain = sT002SP1Service.getWorkSiteBySiteId(siteWork.getDONOR_SITE());
			if (siteMain != null) {
				model.put("donorBtsSiteId", siteMain.getBTS_SITE_ID());
			}

		}

		if (!StringUtils.isEmpty(siteWork.getMASTER_SITE())) {
			TbSiteMain siteMain = sT002SP1Service.getWorkSiteBySiteId(siteWork.getMASTER_SITE());
			if (siteMain != null) {
				model.put("masterBtsSiteId", siteMain.getBTS_SITE_ID());
			}

		}
		log.debug("廠商權限ST003 = " + getLoginUser().isVendor());
		model.put("loginUsrVendor", getLoginUser().isVendor());
		// model.put("loginUsrVendor", "false");
		model.put("userName", getLoginUser().getUsername());
		model.put("userNameCH", getLoginUser().getChName());

		// 作業內容 狀態
		model.put("orderTypeName", OrderStatus.detectLabel(siteWorkOrder.getODR_STATUS()));
		model.put("orderStatus", siteWorkOrder.getODR_STATUS());

		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd");
		String dateFormat = sdFormat.format(siteWork.getPREDICE_END_DATE());
		model.put("prediceEndDate", dateFormat);

		String orderStatus = siteWorkOrder.getODR_STATUS();
		status = StringUtils.equals(orderStatus, OrderStatus.OR07.name()) || StringUtils.equals(orderStatus, OrderStatus.OR08.name()) ? "view"
				: status;
		// status 判斷 是否點擊
		model.put("checkDabClick", status);

		String isApplyPage = request.getParameter("isApplyPage");
		if ("Y".equals(isApplyPage)) {
			model.put("isApplyPage", isApplyPage);
		}

		model.put("view", status);
		model.put("fromTODO", StringUtils.defaultIfEmpty(request.getParameter("fromTODO"), "N"));
		return "/ajaxPage/st/ST003M2";
	}

	/**
	 * 查詢編輯頁處理頁籤-for各別工單
	 * 
	 * @param orderId
	 * @param model
	 * @return
	 * @author Charlie Woo
	 */
	@RequestMapping(value = "/tabOrderPage")
	@ResponseBody
	public BaseJasonObject<TbSysOrderPage> tabOrderPage(@RequestParam("orderId") String orderId, Map<String, Object> model) {
		log.debug("getOutSourceTable = " + orderId);

		TbSiteWorkOrder siteWorkOrder = sT003Service.selectByPrimaryKey(orderId);
		log.debug("siteWorkOrder = " + siteWorkOrder.getORDER_TYPE());

		TbSysOrderPageExample Pageexample = new TbSysOrderPageExample();
		Pageexample.createCriteria().andOD_TYPE_IDEqualTo(siteWorkOrder.getORDER_TYPE());
		List<TbSysOrderPage> sysOrderPage = sT003Service.selectByExample(Pageexample);

		log.info("sysOrderPage = " + sysOrderPage.size());
		return new BaseJasonObject<>(sysOrderPage, AJAX_SUCCESS, AJAX_EMPTY);
	}

	/**
	 * 查詢workOrder
	 * 
	 * @param workId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/search/workOrder")
	@ResponseBody
	public BaseJasonObject<SiteWorkOrderDTO> getWorkOrder(@RequestParam("workId") String workId, Map<String, Object> model) {
		List<SiteWorkOrderDTO> siteWorkOrderList = sT002Service.getSiteWorkOrderByWorkId(workId);
		for (SiteWorkOrderDTO siteWorkOrder : siteWorkOrderList) {
			siteWorkOrder.setODR_STATUS(OrderStatus.detectLabel(siteWorkOrder.getODR_STATUS()));
		}

		model.put("siteWorkOrder", siteWorkOrderList);
		return new BaseJasonObject<>(siteWorkOrderList, AJAX_SUCCESS, AJAX_EMPTY);
	}

	/**
	 * 接工
	 * 
	 * @param workId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/changePickWork")
	@ResponseBody
	public BaseJasonObject<Object> changePickWork(@RequestParam("orderId") String orderId, @RequestParam("pickWorkName") String pickWorkName,
			Map<String, Object> model) {

		Map<String, String> map = new HashMap<String, String>();
		map.put("orderId", orderId);
		map.put("pickWorkName", pickWorkName);
		sT003Service.updatePackWork(map);

		return new BaseJasonObject<>(true, "接工成功！");
	}

	/**
	 * 新增
	 * 
	 * @param workId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public BaseJasonObject<Object> save(@RequestParam("orderId") String orderId, @RequestParam("endDesc") String endDesc,
			@RequestParam("srId") String srId,@RequestParam("lon") String lon,
			@RequestParam("lat") String lat,@RequestParam("city") String city,
			@RequestParam("area") String area,@RequestParam("coverRange") String coverRange) {
		TbSiteWork siteWork = new TbSiteWork();
		siteWork.setSR_ID(srId);
		siteWork.setSR_LON(BigDecimal.valueOf(Double.parseDouble(lon)));
		siteWork.setSR_LAT(BigDecimal.valueOf(Double.parseDouble(lat)));
		siteWork.setCITY(city);
		siteWork.setAREA(area);
		siteWork.setSR_COVER_RANG(coverRange);
		sT003Service.updateSaveTab1(orderId, endDesc, siteWork, getLoginUser());

		return new BaseJasonObject<>(true, "儲存完成！");
	}

	private Map<String, LabelValueModel> getSiteFeqModel(List<TbComSiteFeq> siteFeqList) {
		Map<String, LabelValueModel> map = new HashMap<String, LabelValueModel>();
		for (TbComSiteFeq siteFeq : siteFeqList) {
			LabelValueModel labelValue = LabelValueModel.getLabelValueModel(siteFeq.getFEQ_NAME(), siteFeq.getFEQ_TYPE());
			map.put(siteFeq.getFEQ_ID(), labelValue);
		}
		return map;
	}

	/**
	 * 退工
	 * 
	 * @return
	 */
	@RequestMapping("dropOrder")
	@ResponseBody
	public BaseJasonObject<Object> dropOrder(String orderId, String endDesc) {
		try {

			sT003Service.dropOrder(orderId, endDesc, getLoginUser().getUsername());
		} catch (NomsException ne) {
			log.error(ne.getMessage(), ne);
			return new BaseJasonObject<>(false, ne.getMessage());
		} catch (UpdateFailException ue) {
			log.error(ue.getMessage(), ue);
			return new BaseJasonObject<>(false, "工單編號：" + orderId + "退工失敗！");
		}
		return new BaseJasonObject<>(true, "工單編號：" + orderId + "退工成功！");
	}

	/**
	 * 完工送審
	 * 
	 * @param workId
	 * @param orderId
	 * @return
	 */
	@RequestMapping("verifyOrder")
	@ResponseBody
	public BaseJasonObject<Object> verifyOrder(String workId, String orderId) {

		try {
			String msg = sT003Service.doValidate(workId, orderId);
			
			if (msg != null) {
				return new BaseJasonObject<>(false, msg);
			}
			sT003Service.verifyOrder(orderId, workId, getLoginUser().getUsername());
		} catch (WorkflowException wfe) {
			log.error(wfe.getMessage(), wfe);
			return new BaseJasonObject<>(false, "工單編號：" + workId + "完工送審失敗！" + wfe.getMessage());
		}

		return new BaseJasonObject<>(true, "工單編號：" + workId + "完工送審成功！");
	}
	
	@RequestMapping(value = "/doValidate")
	@ResponseBody
	public BaseJasonObject<String> doValidateSearchRing(SiteHuntApplyDTO siteHuntApply){
		String errorMessages = "";
		String feqType = siteHuntApply.getFeqId().split(",")[1];
		String feqId = siteHuntApply.getFeqId().split(",")[0];
		String eqpTypeId = siteHuntApply.getEqpTypeId();
		String btsSiteId = StringUtils.trimToNull(siteHuntApply.getBtsSiteId());
		String domain = townDomainTeamDao.getTownDomainTeamByCityIdTownId(siteHuntApply.getCity(), siteHuntApply.getArea()).getDOMAIN();
		domain = domain.substring(0, 1);
		String coverageType = siteHuntApply.getCoverageType();
		String srId = siteHuntApply.getSrId();
		TbSiteSearchRing searchRing = searchRingDao.findSearchRingByPrimaryKey(srId);
		if (!feqId.equals(searchRing.getFEQ_ID()) && !"all".equals(searchRing.getFEQ_ID())) {
			errorMessages = "Search Ring的基站頻段與尋點的基站頻段不符\n";
		}
		// 驗證btsSiteId
		if (btsSiteId != null || !WorkType.NSR.name().equals(siteHuntApply.getWorkType())) {
			errorMessages += sT002Service.doValidateBTSSiteId(eqpTypeId, feqType, domain, btsSiteId, coverageType);
		}
		return new BaseJasonObject<String>(errorMessages, AJAX_SUCCESS);
	}

}
