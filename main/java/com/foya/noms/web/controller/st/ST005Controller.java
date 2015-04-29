package com.foya.noms.web.controller.st;

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
import com.foya.dao.mybatis.model.TbOrgDept;
import com.foya.dao.mybatis.model.TbOrgDeptExample;
import com.foya.dao.mybatis.model.TbSiteLocation;
import com.foya.dao.mybatis.model.TbSiteWorkOrder;
import com.foya.dao.mybatis.model.TbSiteWorkOrderExample;
import com.foya.dao.mybatis.model.TbSiteWorkSiteTempKey;
import com.foya.dao.mybatis.model.TbSysOrderType;
import com.foya.dao.mybatis.model.TbSysOrderTypeExample;
import com.foya.noms.dto.basic.CompanyDTO;
import com.foya.noms.dto.org.DeptDTO;
import com.foya.noms.dto.st.SiteDTO;
import com.foya.noms.dto.st.SiteWorkDTO;
import com.foya.noms.dto.st.SiteWorkOrderDTO;
import com.foya.noms.enums.BuildAim;
import com.foya.noms.enums.Feederless;
import com.foya.noms.enums.IncludeRange;
import com.foya.noms.enums.Level;
import com.foya.noms.enums.OrderStatus;
import com.foya.noms.enums.PurchaseOrderType;
import com.foya.noms.enums.WorkStatus;
import com.foya.noms.enums.WorkType;
import com.foya.noms.service.auth.PersonnelService;
import com.foya.noms.service.org.ORG002Service;
import com.foya.noms.service.st.ST001Service;
import com.foya.noms.service.st.ST002SP1Service;
import com.foya.noms.service.st.ST003Service;
import com.foya.noms.service.st.ST004Service;
import com.foya.noms.service.st.ST005Service;
import com.foya.noms.service.system.OrderTypeService;
import com.foya.noms.util.JqGridData;
import com.foya.noms.util.LabelValueModel;
import com.foya.noms.web.controller.st.base.BaseOrderProcessController;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Controller
@RequestMapping(value = "/st/st005")
public class ST005Controller extends BaseOrderProcessController {

	@Autowired
	private ST005Service sT005Service;
	@Autowired
	private ST004Service st004Service;
	@Autowired
	private ST002SP1Service sT002SP1Service;
	@Autowired
	private ST001Service sT001Service;
	@Autowired
	private PersonnelService personnelService;
	@Autowired
	private ORG002Service org002Service;
	@Autowired
	private ST003Service sT003Service;
	@Autowired
	private OrderTypeService orderTypeService;

	/**
	 * 初始要面
	 * 
	 * @return
	 */
	@RequestMapping(value = "init")
	public String initST003Page(HttpServletRequest request, Map<String, Object> model) {
		List<String> list = new ArrayList<String>();

		// 取得施工單位選項
		Set<String> tempDeptList = getLoginUser().getAccessDeptListByMenuID(Integer.valueOf(request.getParameter("menuId")));

		if (tempDeptList != null) {
			list.addAll(tempDeptList);
		}
		// 接工單位
		List<LabelValueModel> deptList = sT005Service.getDeptAll(list);
		model.put("allRepDept", deptList);

		// 工單狀態
		model.put("allOrderStatus", OrderStatus.getLabelValueListOnProcess(true));

		// 取得登入者是否為廠商
		model.put("isVendors", getLoginUser().isVendor());
		model.put("isManager", getLoginUser().isManager());

		if (getLoginUser().isVendor() == true) {
			String coVatNo = getLoginUser().getCoVatNo();
			List<CompanyDTO> companyList = sT005Service.getCompany(coVatNo);
			model.put("allCompany", companyList);
			model.put("covatNo", companyList.get(0).getCO_VAT_NO());
		} else {
			List<CompanyDTO> companyList = sT005Service.getCompanyAll();
			model.put("allCompany", companyList);
		}

		// 工務類別
		model.put("allWorkType", WorkType.getLabelValueList("ST004"));

		// 派工目的
		model.put("allOrderType", sT005Service.getWorkOrderTypesForBuild());

		return "/ajaxPage/st/ST005L";
	}

	/**
	 * 查詢
	 * 
	 * @return
	 */
	@RequestMapping(value = "/search")
	@ResponseBody
	public JqGridData<SiteWorkOrderDTO> search(HttpServletRequest request, @RequestParam("workId") String workId,
			@RequestParam("ticketId") String ticketId, @RequestParam("baseStationId") String baseStationId,
			@RequestParam("btsSiteName") String btsSiteName, @RequestParam("locCity") String locCity, @RequestParam("locTown") String locTown,
			@RequestParam("workersPickType") String workersPickType, @RequestParam("ticketType") String ticketType,
			@RequestParam("vendors") String vendors, @RequestParam("worksType") String worksType, @RequestParam("rangeBa") String rangeBa,
			@RequestParam("rangeBb") String rangeBb, @RequestParam("orderTypeId") String orderTypeId) {

		// 查詢條件
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("workId", workId);// 作業編號
		map.put("orderId", ticketId);// 工單編號
		map.put("btsSiteId", baseStationId);// 基站編號
		map.put("siteName", btsSiteName);// 基站名稱
		map.put("siteWorkCity", locCity);// 行政區
		map.put("siteWorkArea", locTown);// 行政區
		map.put("repDept", workersPickType);// 接工單位
		map.put("coVatNo", vendors);// 委外廠商
		map.put("workType", worksType);// 工務類別
		map.put("applyDateStart", rangeBa);// 申請日期(起)
		map.put("applyDateEnd", rangeBb);// 申請日期(迄)
		map.put("orderType", orderTypeId);// 派工目的
		map.put("type", "T");

		List<String> list = new ArrayList<String>();
		Set<String> tempDeptList = getLoginUser().getAccessDeptListByMenuID(getCurrentMenuId());
		if (tempDeptList != null) {
			list.addAll(tempDeptList);
		}
		List<LabelValueModel> deptTempList = sT003Service.getDeptAll(list);

		if (workersPickType == null || workersPickType == "") {
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
		
		if (StringUtils.equalsIgnoreCase("todo", ticketType)) {
			List<String> todoStatus = OrderStatus.getNotFinishedStatus();
			map.put("todoStatus", todoStatus);
		} else {
			map.put("orderStatus", ticketType);// 工單狀態
		}
		
		if (getLoginUser().isVendor()) {
			map.put("isVenderQuery", true);
		}

		List<TbOrgDept> deptList = org002Service.getByCondition(new TbOrgDeptExample());// sT003Service.getDeptAll(list);
		List<TbAuthPersonnel> authPersonnelList = personnelService.getAllPsn();
		List<TbSysOrderType> orderTypes = orderTypeService.getAllOrderTypes();

		List<SiteWorkOrderDTO> siteWorkOrderList = sT005Service.getWorkLOrderist(map);

		for (SiteWorkOrderDTO siteWorkOrder : siteWorkOrderList) {

			// 查詢結果-工單狀態
			for (LabelValueModel orderStatusValue : OrderStatus.getLabelValueList()) {
				if (orderStatusValue.getValue().equals(siteWorkOrder.getODR_STATUS())) {
					siteWorkOrder.setOrderStatusName(orderStatusValue.getLabel());
					break;
				}
			}

			// 查詢結果-工務類別
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

			// 查詢結果-接工單位
			for (TbOrgDept repDeptAll : deptList) {
				if (StringUtils.equals(siteWorkOrder.getREP_DEPT(), repDeptAll.getDEPT_ID())) {
					siteWorkOrder.setDeptName(repDeptAll.getDEPT_NAME());
					break;
				}
			}

			// 查詢結果-負責人
			String repUser = siteWorkOrder.getREP_USER();
			for (TbAuthPersonnel person : authPersonnelList) {
				if (StringUtils.equals(repUser, person.getPSN_NO())) {
					siteWorkOrder.setChnName(person.getCHN_NM());
					break;
				}
			}
		}

		PageList<SiteWorkOrderDTO> page = (PageList<SiteWorkOrderDTO>) siteWorkOrderList;
		return new JqGridData<>(page.getPaginator().getTotalCount(), siteWorkOrderList);
	}

	/**
	 * 編輯處理頁
	 */
	@RequestMapping(value = "updatePage")
	public String processPage(Map<String, Object> model, @RequestParam("status") String status, @RequestParam("orderId") String orderId,
			@RequestParam("siteId") String siteId) {

		TbSiteWorkOrder siteWorkOrder = sT003Service.selectByPrimaryKey(orderId);
		// 工單資料
		SiteWorkDTO siteWork = st004Service.getSiteWorkByWorkId(siteWorkOrder.getWORK_ID());

		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd");
		String dateFormat = sdFormat.format(siteWork.getPREDICE_END_DATE());
		model.put("prediceEndDate", dateFormat);

		model.put("showBtn", true);
		if (siteWork.getWORK_TYPE().equals(WorkType.SGL.name())) {
			model.put("processType", "SiteBuildSingleWorkCompletionApply");
		} else {
			model.put("processType", "SiteBuildApply");
		}

		model.put("status", status);
		model.put("initPage", siteWork.getWORK_TYPE());
		// 工務類別
		model.put("allWorkType", WorkType.getLabelValueList("ST004"));
		// 重要等級
		model.put("allLevel", Level.getLabelValueList());
		// 建站目的
		model.put("allBuildAim", BuildAim.getLabelValueList());
		// 工程類別
		model.put("poTypeList", PurchaseOrderType.getLabelValueList());
		// 基站頻段
		model.put("allSiteFeq", st004Service.getSiteFeqs());

		List<LabelValueModel> dataFeq = st004Service.getSiteFeqs();
		for (LabelValueModel temp : dataFeq) {
			if (temp.getValue().equals(siteWork.getFEQ_ID())) {
				model.put("feqName", temp.getLabel());
				break;
			}
		}
		// 設備型態
		model.put("allEqpTypes", st004Service.getEqpTypes());
		// 涵蓋範圍
		model.put("allIncludeRange", IncludeRange.getLabelValueList());
		// Feederless
		model.put("allFeederless", Feederless.getLabelValueList());

		model.put("siteWork", siteWork);
		model.put("siteBuildApplyEvent", "view");
		model.put("singleOrderApplyEvent", "edit");
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
		// 工單類別
		model.put("orderType", siteWorkOrder.getORDER_TYPE());
		model.put("siteOrderType", siteWorkOrder.getORDER_TYPE());
		model.put("orderSeq", siteWorkOrder.getODR_SEQ());
		// 機房資訊
		TbSiteWorkSiteTempKey key = new TbSiteWorkSiteTempKey();
		key.setSITE_ID(siteId);
		key.setWORK_ID(siteWorkOrder.getWORK_ID());
		model.put("siteWorkSiteTemp", sT005Service.getSiteTempByPrimaryKey(key));

		// 多頻段站點
		if (!StringUtils.isEmpty(siteWork.getLOCATION_ID())) {
			TbSiteLocation location = sT001Service.getSiteLocationByLocationId(siteWork.getLOCATION_ID());
			model.put("location", location);
			List<SiteDTO> siteList = sT002SP1Service.getSiteByLocationId(siteWork.getLOCATION_ID());
			model.put("isMultibandLocation", siteList.size());
		}
		// 申請人員
		if (!StringUtils.isEmpty(siteWork.getAPL_USER())) {
			TbAuthPersonnelExample example = new TbAuthPersonnelExample();
			example.createCriteria().andPSN_NOEqualTo(siteWork.getAPL_USER());
			List<TbAuthPersonnel> applys = personnelService.getPersonnelsByExample(example);
			String applyName = "";
			if (!applys.isEmpty()) {
				applyName = applys.get(0).getCHN_NM();
			}
			model.put("applyUserName", applyName);
		}
		model.put("donorBtsSiteId", siteWork.getDONOR_SITE());

		model.put("masterBtsSiteId", siteWork.getMASTER_SITE());

		TbSiteWorkOrderExample example = new TbSiteWorkOrderExample();
		example.createCriteria().andORDER_IDEqualTo(orderId);
		List<TbSiteWorkOrder> siteWorkOrderList = sT005Service.getSiteWorkOrderByOrderId(example);

		model.put("siteWorkOrderList", siteWorkOrderList);

		TbAuthPersonnelExample pickUserExaple = new TbAuthPersonnelExample();
		String pickUser = "";
		if (siteWorkOrder.getPICK_USER() != null && !"".equals(siteWorkOrder.getPICK_USER())) {
			pickUserExaple.createCriteria().andPSN_NOEqualTo(siteWorkOrder.getPICK_USER());
			List<TbAuthPersonnel> authPersonnelList = personnelService.getPersonnelsByExample(pickUserExaple);
			if (authPersonnelList.size() > 0) {
				pickUser = authPersonnelList.get(0).getCHN_NM();
			}

		}
		model.put("pickUser", pickUser);
		model.put("showUserName", getLoginUser().getChName());
		model.put("userName", getLoginUser().getUsername());
		// log.info("廠商權限 = " + getLoginUser().isVendor());
		model.put("loginUsrVendor", getLoginUser().isVendor());

		// 派工目的 轉中文
		if (siteWorkOrderList.size() > 0) {
			if (siteWorkOrderList.get(0).getORDER_TYPE() != null) {
				String orderType = siteWorkOrderList.get(0).getORDER_TYPE();
				TbSysOrderTypeExample orderTypeExample = new TbSysOrderTypeExample();
				orderTypeExample.createCriteria().andOD_TYPE_IDEqualTo(orderType);
				List<TbSysOrderType> sysOrderTypeList = sT005Service.getSysOrderTypeByOrderType(orderTypeExample);
				model.put("sysOrderTypeList", sysOrderTypeList);
			}
		}

		// 若為物料處理工單類型，多夾一張原土木工單號碼，方便查到物料操作 add by Charlie 20150325
		model.put("mtHandleOrder", sT005Service.getOriginalMaterialOrderId(siteWorkOrder));

		model.put("view", status);
		return "/ajaxPage/st/ST005M2";
	}

//	/**
//	 * 接工
//	 * 
//	 * @param workId
//	 * @param model
//	 * @return
//	 */
//	@RequestMapping(value = "/changePickWork")
//	@ResponseBody
//	public BaseJasonObject<Object> changePickWork(@RequestParam("orderId") String orderId, Map<String, Object> model) {
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

//	/**
//	 * 編輯-處理狀態
//	 * 
//	 * @param orderId
//	 * @param endDesc
//	 * @param orderDesc
//	 * @return
//	 * @author Charlie Woo
//	 */
//	@Deprecated
//	@RequestMapping(value = "/save")
//	@ResponseBody
//	public BaseJasonObject<Object> save(@RequestParam("orderId") String orderId, @RequestParam("endDesc") String endDesc,
//			@RequestParam("orderDesc") String orderDesc) {
//
//		TbSiteWorkOrder siteWorkOrder = sT005Service.getOrderByKey(orderId);
//		siteWorkOrder.setORDER_ID(orderId);
//		siteWorkOrder.setORDER_DESC(orderDesc);
//		siteWorkOrder.setEND_DESC(endDesc);
//		siteWorkOrder.setMD_TIME(new Date());
//		siteWorkOrder.setMD_USER(getLoginUser().getUsername());
//		sT005Service.updateSiteWorkOrder(siteWorkOrder);// .updateSiteWorkOrderSelective(siteWorkOrder);
//
//		return new BaseJasonObject<Object>(true, "存檔成功！");
//	}

	// /**
	// * 查詢編輯頁處理頁籤-for各別工單
	// * @param orderId
	// * @param model
	// * @return
	// * @author Charlie Woo
	// */
	// @Deprecated
	// @RequestMapping(value = "tabOrderPage")
	// @ResponseBody
	// public BaseJasonObject<TbSysOrderPage>
	// tabOrderPage(@RequestParam("orderId") String orderId,
	// Map<String, Object> model) {
	// log.debug("getOutSourceTable = " + orderId);
	//
	// TbSiteWorkOrder siteWorkOrder = sT005Service.getOrderByKey(orderId);
	// log.info("siteWorkOrder = " + siteWorkOrder.getORDER_TYPE());
	//
	// TbSysOrderPageExample pageExample = new TbSysOrderPageExample();
	// pageExample.createCriteria().andOD_TYPE_IDEqualTo(siteWorkOrder.getORDER_TYPE());
	// List<TbSysOrderPage> sysOrderPage =
	// sT005Service.selectByExample(pageExample);
	//
	// log.info("sysOrderPage = " + sysOrderPage.size());
	// return new BaseJasonObject<>(sysOrderPage, AJAX_SUCCESS, AJAX_EMPTY);
	// }

	/**
	 * 派工頁
	 */
	@RequestMapping(value = "assignPage")
	public String assignPage(Map<String, Object> model, @RequestParam("workId") String workId, @RequestParam("status") String status,
			@RequestParam("rep_DEPT") String rep_DEPT, @RequestParam("orderId") String orderId) {
		// 工單資料
		SiteWorkDTO siteWork = st004Service.getSiteWorkByWorkId(workId);
		model.put("workId", workId);
		model.put("orderId", orderId);

		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd");
		String dateFormat = sdFormat.format(siteWork.getPREDICE_END_DATE());
		model.put("prediceEndDate", dateFormat);

		model.put("showBtn", true);
		model.put("processType", "SiteBuildApply");
		
		model.put("status", status);

		// 工務類別
		model.put("allWorkType", WorkType.getLabelValueList("ST004"));
		// 重要等級
		model.put("allLevel", Level.getLabelValueList());
		// 建站目的
		model.put("allBuildAim", BuildAim.getLabelValueList());
		// 工程類別
		model.put("poTypeList", PurchaseOrderType.getLabelValueList());
		// 基站頻段
		model.put("allSiteFeq", st004Service.getSiteFeqs());
		// 設備型態
		model.put("allEqpTypes", st004Service.getEqpTypes());
		// 涵蓋範圍
		model.put("allIncludeRange", IncludeRange.getLabelValueList());
		// Feederless
		model.put("allFeederless", Feederless.getLabelValueList());

		model.put("siteWork", siteWork);
		model.put("siteBuildApplyEvent", "view");

		// 作業類別
		model.put("workTypeName", "建站作業");
		// 處理狀態
		model.put("workStatusName", WorkStatus.detectLabel(siteWork.getWORK_STATUS()));

		// 多頻段站點
		if (!StringUtils.isEmpty(siteWork.getLOCATION_ID())) {
			TbSiteLocation location = sT001Service.getSiteLocationByLocationId(siteWork.getLOCATION_ID());
			model.put("location", location);
			List<SiteDTO> siteList = sT002SP1Service.getSiteByLocationId(siteWork.getLOCATION_ID());
			model.put("isMultibandLocation", siteList.size());
		}
		// 申請人員
		if (!StringUtils.isEmpty(siteWork.getAPL_USER())) {
			TbAuthPersonnelExample example = new TbAuthPersonnelExample();
			example.createCriteria().andPSN_NOEqualTo(siteWork.getAPL_USER());
			model.put("applyUserName", personnelService.getPersonnelsByExample(example).get(0).getCHN_NM());
		}
		model.put("donorBtsSiteId", siteWork.getDONOR_SITE());

		model.put("masterBtsSiteId", siteWork.getMASTER_SITE());

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

		// 派工目的 轉中文
		if (siteWorkOrderList.size() > 0) {
			if (siteWorkOrderList.get(0).getORDER_TYPE() != null) {
				String orderType = siteWorkOrderList.get(0).getORDER_TYPE();
				TbSysOrderTypeExample orderTypeExample = new TbSysOrderTypeExample();
				orderTypeExample.createCriteria().andOD_TYPE_IDEqualTo(orderType);
				List<TbSysOrderType> sysOrderTypeList = sT005Service.getSysOrderTypeByOrderType(orderTypeExample);
				model.put("sysOrderTypeList", sysOrderTypeList);
			}

		}

		model.put("repDept", rep_DEPT);
		TbOrgDept orgDept = sT003Service.selectOrgDept(rep_DEPT);
		model.put("deptName", orgDept.getDEPT_NAME());

		return "/ajaxPage/st/ST005M1";
	}

	// /**
	// * 派工 - 負責人 下拉選單
	// *
	// * @param reques
	// * @param dept
	// * @return
	// * @throws Exception
	// */
	// @Deprecated
	// @RequestMapping(value = "/personnelDept")
	// @ResponseBody
	// public BaseJasonObject<TbAuthPersonnel>
	// initPersonnelDeptSelect(HttpServletRequest reques,
	// String dept) throws Exception {
	// // 負責人 下拉選單
	// List<TbOrgDeptPos> repUserList = sT003Service.findRepUserList(dept);
	// List<TbAuthPersonnel> personnel = new ArrayList<TbAuthPersonnel>();
	//
	// if(StringUtils.isNotBlank(dept)) {
	// for (TbOrgDeptPos temp : repUserList) {
	// String deptPosId = temp.getDEPT_POS_ID();
	// List<TbOrgPsnPos> psnNoList = sT003Service.findPsnNo(deptPosId);
	//
	// for (TbOrgPsnPos tempName : psnNoList) {
	// String psnNo = tempName.getPSN_NO();
	// List<TbAuthPersonnel> authPersonnelList =
	// sT003Service.findPsnName(psnNo);
	// personnel.addAll(authPersonnelList);
	// //return new BaseJasonObject<TbAuthPersonnel>(authPersonnelList,
	// AJAX_SUCCESS,AJAX_EMPTY);
	// }
	// }
	// return new BaseJasonObject<TbAuthPersonnel>(personnel, AJAX_SUCCESS,
	// AJAX_EMPTY);
	// } else {
	// // 如果沒有職稱，直接使用 接工單位 DeptId 尋找 負責人 預設
	// String psnNo = dept;
	// List<TbAuthPersonnel> authPersonnelList =sT003Service.findPsnName(psnNo);
	// //List<TbAuthPersonnel> authPersonnelList = new
	// ArrayList<TbAuthPersonnel>();
	// return new BaseJasonObject<TbAuthPersonnel>(authPersonnelList,
	// AJAX_SUCCESS, AJAX_EMPTY);
	// }
	// }

	// /**
	// * 派工
	// *
	// * @param workId
	// * @param model
	// * @return
	// */
	//
	// @Deprecated
	// @RequestMapping(value = "/saveBtn")
	// @ResponseBody
	// public BaseJasonObject<Object> saveBtn(@RequestParam("orderId") String
	// orderId,
	// @RequestParam("maintainTeam") String maintainTeam,
	// @RequestParam("maintainUser") String maintainUser, Map<String, Object>
	// model) {
	//
	// TbSiteWorkOrder siteWorkOrder = new TbSiteWorkOrder();
	// siteWorkOrder.setORDER_ID(orderId);
	// siteWorkOrder.setODR_STATUS("OR04");
	// siteWorkOrder.setREP_TEAM(maintainTeam);
	// siteWorkOrder.setREP_USER(maintainUser);
	// siteWorkOrder.setMD_TIME(new Date());
	// siteWorkOrder.setMD_USER(getLoginUser().getUsername());
	// sT005Service.updateSiteWorkOrderSelective(siteWorkOrder);
	// siteEmailService.sendOrderPickupMail(orderId);
	// return new BaseJasonObject<Object>(true, "派工完成！");
	// }

	// /**
	// * 完工(送審核)
	// *
	// * @param orderId
	// * @author Charlie Woo
	// * @throws Exception
	// */
	// @Deprecated
	// @RequestMapping("verifyOrder")
	// @ResponseBody
	// public BaseJasonObject<Object> verifyOrder(String workId, String orderId)
	// throws Exception{
	//
	// try{
	// String msg = sT005Service.doValidate(workId, orderId);
	//
	// if(msg != null){
	// return new BaseJasonObject<>(false, msg);
	// }
	// sT005Service.verifyOrder(orderId, workId, getLoginUser().getUsername());
	// } catch(WorkflowException wfe){
	// log.error(wfe.getMessage(), wfe);
	// return new BaseJasonObject<>(true, "工單編號：" + workId + "完工送審失敗！" +
	// wfe.getMessage());
	// }
	//
	// return new BaseJasonObject<>(true, "工單編號：" + workId + "完工送審成功！");
	// }

	// /**
	// * 退工
	// *
	// * @param orderId
	// * @author Charlie Woo
	// */
	// @Deprecated
	// @RequestMapping(value = "/dropOrder")
	// @ResponseBody
	// public BaseJasonObject<Object> dropOrder(String workId) {
	// BaseJasonObject<Object> jsonObj = new BaseJasonObject<>();
	// try{
	// List<String> errorMessageList = sT005Service.dropOrder(workId);
	// if(errorMessageList.isEmpty()){
	// jsonObj.setSuccess(true);
	// jsonObj.setMsg("退工完成！");
	// }else{
	// jsonObj.setSuccess(false);
	// jsonObj.setMsg("退工失敗！");
	// jsonObj.setErrors(errorMessageList);
	// }
	// }catch(UpdateFailException e){
	// log.error(ExceptionUtils.getFullStackTrace(e));
	// jsonObj.setSuccess(false);
	// jsonObj.setMsg("退工失敗！");
	// }catch(NomsException e){
	// log.error(ExceptionUtils.getFullStackTrace(e));
	// jsonObj.setSuccess(false);
	// jsonObj.setMsg("退工失敗！");
	// }
	// return jsonObj;
	// }

	// /**
	// * 重啟工單頁面
	// *
	// * @param request
	// * @param model
	// * @return
	// */
	// @Deprecated
	// @RequestMapping(value = "/reopenOrderPage")
	// public String showReopenOrderPage(HttpServletRequest request, Map<String,
	// Object> model) {
	// model.put("targetFrameId", request.getParameter("targetFrameId"));
	// return "/ajaxPage/st/ReopenOrder";
	// }

	// /**
	// * 重啟工單
	// *
	// * @param orderId
	// * @author Charlie Woo
	// */
	// @Deprecated
	// @RequestMapping(value = "/update/reopenOrder")
	// @ResponseBody
	// public Map<String, Object> reopenOrder(@RequestParam("orderId") String
	// orderId,
	// @RequestParam("reopenOrderDesc") String reopenOrderDesc,
	// @RequestParam("workType") String workType) {
	// Map<String, Object> map = new HashMap<String, Object>();
	// try{
	// sT005Service.reopenOrder(orderId, reopenOrderDesc, workType);
	// map.put("result", true);
	// }catch(UpdateFailException e){
	// map.put("result", this.getMessageDetail(e.getErrCode()));
	// log.error(ExceptionUtils.getFullStackTrace(e));
	// }
	// return map;
	// }

//	/**
//	 * 查詢workOrder資料
//	 * 
//	 * @param workId
//	 * @param model
//	 * @return
//	 */
//	@Deprecated
//	@RequestMapping(value = "/search/workOrder")
//	@ResponseBody
//	public BaseJasonObject<SiteWorkOrderDTO> getWorkOrderByWorkId(@RequestParam("workId") String workId, Map<String, Object> model) {
//		List<SiteWorkOrderDTO> siteWorkOrderList = sT005Service.getSiteWorkOrderByWorkIdAndIsActive(workId, "Y");
//		for (SiteWorkOrderDTO siteWorkOrder : siteWorkOrderList) {
//			siteWorkOrder.setOrderStatusName(OrderStatus.detectLabel(siteWorkOrder.getODR_STATUS()));
//		}
//		model.put("siteWorkOrder", siteWorkOrderList);
//		return new BaseJasonObject<>(siteWorkOrderList, AJAX_SUCCESS, AJAX_EMPTY);
//	}

//	/**
//	 * 查詢workOrder資料
//	 * 
//	 * @param workId
//	 * @param model
//	 * @return
//	 */
//	@Deprecated
//	@RequestMapping(value = "/search/workOrderObj")
//	@ResponseBody
//	public TbSiteWorkOrder getWorkOrderByOrderId(@RequestParam("orderId") String orderId, Map<String, Object> model) {
//		TbSiteWorkOrder workOrder = sT005Service.getOrderByKey(orderId);
//		return workOrder;
//	}
}
