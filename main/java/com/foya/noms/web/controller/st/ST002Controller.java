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

import com.foya.dao.mybatis.model.TbAuthPersonnelExample;
import com.foya.dao.mybatis.model.TbComEqpModel;
import com.foya.dao.mybatis.model.TbComEqpModelExample;
import com.foya.dao.mybatis.model.TbComSiteFeq;
import com.foya.dao.mybatis.model.TbComSiteFeqExample;
import com.foya.dao.mybatis.model.TbOrgDomain;
import com.foya.dao.mybatis.model.TbSiteLocation;
import com.foya.dao.mybatis.model.TbSiteMain;
import com.foya.dao.mybatis.model.TbSiteMainExample;
import com.foya.dao.mybatis.model.TbSiteSearchRing;
import com.foya.dao.mybatis.model.TbSiteWork;
import com.foya.dao.mybatis.model.TbSiteWorkExample;
import com.foya.dao.mybatis.model.TbSysLookup;
import com.foya.exception.CreateFailException;
import com.foya.exception.NomsException;
import com.foya.exception.UpdateFailException;
import com.foya.noms.dao.st.LocationDao;
import com.foya.noms.dto.st.SiteDTO;
import com.foya.noms.dto.st.SiteHuntApplyDTO;
import com.foya.noms.dto.st.SiteIdPoolDTO;
import com.foya.noms.dto.st.SiteWorkDTO;
import com.foya.noms.dto.st.SiteWorkOrderDTO;
import com.foya.noms.enums.BuildAim;
import com.foya.noms.enums.Feederless;
import com.foya.noms.enums.IncludeRange;
import com.foya.noms.enums.Level;
import com.foya.noms.enums.OrderStatus;
import com.foya.noms.enums.SiteStatus;
import com.foya.noms.enums.WorkStatus;
import com.foya.noms.enums.WorkType;
import com.foya.noms.service.auth.PersonnelService;
import com.foya.noms.service.org.DomainService;
import com.foya.noms.service.st.ST001Service;
import com.foya.noms.service.st.ST002SP1Service;
import com.foya.noms.service.st.ST002Service;
import com.foya.noms.service.st.SearchRingService;
import com.foya.noms.service.system.LookupService;
import com.foya.noms.service.system.UniqueSeqService;
import com.foya.noms.util.BaseJasonObject;
import com.foya.noms.util.JqGridData;
import com.foya.noms.util.LabelValueModel;
import com.foya.noms.web.controller.BaseController;
import com.foya.workflow.exception.WorkflowException;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Controller
@RequestMapping(value = "/st/st002")
public class ST002Controller extends BaseController {

	@Autowired
	private ST002SP1Service sT002SP1Service;
	@Autowired
	private ST002Service sT002Service;
	@Autowired
	private ST001Service sT001Service;
	@Autowired
	private UniqueSeqService uniqueSeqService;
	@Autowired
	private PersonnelService personnelService;
	@Autowired
	private SearchRingService searchRingService;
	@Autowired
	private DomainService domainService;
	@Autowired
	private LocationDao locationDao;
	@Autowired
	private LookupService lookupService;
	/**
	 * 初始頁面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/init")
	public String initST002Page(HttpServletRequest request, Map<String, Object> model) {

		Set<String> set = getLoginUser().getAccessDeptListByMenuID(
				Integer.valueOf(request.getParameter("menuId")));
		List<String> list = new ArrayList<String>();
		if (set != null) {
//			list = new ArrayList<String>(set);
			list.addAll(set);
		}
		
		model.put("allWorkType", WorkType.getLabelValueList("ST002"));
		model.put("allWorkStatus", WorkStatus.getLabelValueList());
		model.put("allSiteFeq", sT002SP1Service.getSiteFeqs());
		List<SiteWorkDTO> siteWorkList = sT002Service.getWorkListByDeptId(list);
		model.put("siteWorkList", siteWorkList);
		model.put("allWorkType", WorkType.getLabelValueList("ST002"));

		return "ajaxPage/st/ST002L";
	}

	/**
	 * 查詢
	 * 
	 * @return
	 */
	@RequestMapping(value = "/search")
	@ResponseBody
	public JqGridData<SiteWorkDTO> search(HttpServletRequest request,@RequestParam("workType") String workType,
			@RequestParam("srId") String srId, @RequestParam("siteWorkCity") String siteWorkCity,
			@RequestParam("siteWorkArea") String siteWorkArea,
			@RequestParam("btsSiteId") String btsSiteId, @RequestParam("siteName") String siteName,
			@RequestParam("locationId") String locationId, @RequestParam("siteFeq") String siteFeq,
			@RequestParam("workStatus") String workStatus,
			@RequestParam("applyDept") String applyDept,
			@RequestParam("applyDateStart") String applyDateStart,
			@RequestParam("applyDateEnd") String applyDateEnd) {
		if (StringUtils.isNotEmpty(applyDateEnd)) {
			applyDateEnd += " 23:59:59";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("workType", workType);
		map.put("srId", srId);
		map.put("siteWorkCity", siteWorkCity);
		map.put("siteWorkArea", siteWorkArea);
		map.put("btsSiteId", btsSiteId);
		map.put("siteName", siteName);
		map.put("locationId", locationId);
		map.put("siteFeq", siteFeq);
		map.put("workStatus", workStatus);
		if(StringUtils.isNotEmpty(applyDept)){
			map.put("applyDept", applyDept);
		}else{
			Set<String> set = getLoginUser().getAccessDeptListByMenuID(this.getCurrentMenuId());
			List<String> list = new ArrayList<String>(set);
			List<String> applyDeptList = new ArrayList<String>();
			List<SiteWorkDTO> siteWorkList = sT002Service.getWorkListByDeptId(list);
			for(SiteWorkDTO siteWork : siteWorkList){
				applyDeptList.add(siteWork.getAPP_DEPT());
			}
			if(siteWorkList.size() == 0){
				applyDeptList = new ArrayList<String>(set);
			}
			map.put("applyDeptList", applyDeptList);
		}
		map.put("applyDateStart", applyDateStart);
		map.put("applyDateEnd", applyDateEnd);

		List<SiteWorkDTO> siteWorkList = sT002Service.getWorkList(map);
		for (SiteWorkDTO siteWork : siteWorkList) {
			siteWork.setWorkStatusName(WorkStatus.detectLabel(siteWork.getWORK_STATUS()));
			siteWork.setSiteStatus(SiteStatus.detectLabel(siteWork.getSiteStatus()));
			siteWork.setWORK_TYPE(WorkType.detectLabel(siteWork.getWORK_TYPE()));
		}
		PageList<SiteWorkDTO> page = (PageList<SiteWorkDTO>) siteWorkList;
		return new JqGridData<>(page.getPaginator().getTotalCount(), siteWorkList);
	}

	/**
	 * 查詢設備機型
	 * 
	 * @param eqpTypeId
	 * @return
	 */
	@RequestMapping(value = "/search/eqpModel")
	@ResponseBody
	public Map<String, String> getEqpModel(@RequestParam("eqpTypeId") String eqpTypeId) {
		Map<String, String> map = new HashMap<String, String>();
		if(StringUtils.isNotEmpty(eqpTypeId)){
			TbComEqpModelExample example = new TbComEqpModelExample();
			example.createCriteria().andEQP_TYPE_IDEqualTo(eqpTypeId);
			List<TbComEqpModel> eqpModelList = sT002Service.getEqpModelList(example);
			for (TbComEqpModel eqpModel : eqpModelList) {
				map.put(eqpModel.getEQP_MODEL_ID(), eqpModel.getEQP_MODEL());
			}
		}
		return map;
	}

	/**
	 * 查詢location
	 * 
	 * @param locationId
	 * @return
	 */
	@RequestMapping(value = "/search/location")
	@ResponseBody
	public Map<String, Object> getLocation(@RequestParam("locationId") String locationId) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<SiteDTO> siteList = sT002SP1Service.getSiteByLocationId(locationId);
		List<String> siteFeqList = new ArrayList<String>();
		for (SiteDTO site : siteList) {
			siteFeqList.add(site.getFEQ_ID());
		}
		TbComSiteFeqExample example = new TbComSiteFeqExample();
		List<TbComSiteFeq> sourceSiteFeqList = sT002SP1Service.getSiteFeqsByExample(example);
		example = new TbComSiteFeqExample();
		example.createCriteria().andFEQ_IDIn(siteFeqList);
		List<TbComSiteFeq> targetSiteFeqList = sT002SP1Service.getSiteFeqsByExample(example);
		sourceSiteFeqList.removeAll(targetSiteFeqList);
		map.put("siteLoc", sT001Service.getSiteLocationByLocationId(locationId));
		map.put("isMultibandLocation", siteList.size());
		map.put("siteFeqMap", this.getSiteFeqModel(sourceSiteFeqList));
		return map;
	}

	/**
	 * 查詢 Site
	 * 
	 * @param siteId
	 * @return
	 */
	@RequestMapping(value = "/search/site")
	@ResponseBody
	public TbSiteMain getSite(@RequestParam("siteId") String siteId) {
		TbSiteMainExample example = new TbSiteMainExample();
		example.createCriteria().andSITE_IDEqualTo(siteId);
		List<TbSiteMain> siteMainList = sT002SP1Service.getWorkSiteListByExample(example);
		return siteMainList.get(0);
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
	public BaseJasonObject<SiteWorkOrderDTO> getWorkOrder(@RequestParam("workId") String workId,
			Map<String, Object> model) {
		List<SiteWorkOrderDTO> siteWorkOrderList = sT002Service.getSiteWorkOrderByWorkId(workId);
		for (SiteWorkOrderDTO siteWorkOrder : siteWorkOrderList) {
			siteWorkOrder.setODR_STATUS(OrderStatus.detectLabel(siteWorkOrder.getODR_STATUS()));
		}
		model.put("siteWorkOrder", siteWorkOrderList);
		return new BaseJasonObject<>(siteWorkOrderList, AJAX_SUCCESS, AJAX_EMPTY);
	}

	/**
	 * 查詢Search Ring
	 * 
	 * @param srId
	 * @return
	 */
	@RequestMapping(value = "/search/searchRing")
	@ResponseBody
	public TbSiteSearchRing getSearchRing(@RequestParam("srId") String srId) {
		TbSiteSearchRing searchRing = searchRingService.getSearchRingByPrimaryKey(srId);
		return searchRing;
	}

	/**
	 * 查詢Site Id Pool
	 * 
	 * @param btsSiteId
	 * @return
	 */
	@RequestMapping(value = "/search/unuseBTSSiteId")
	public String getUnuseBTSSiteId(HttpServletRequest request, Map<String, Object> model,
			SiteIdPoolDTO siteIdPool) {
		List<TbOrgDomain> domainList = domainService.getStandardDomainList();
		String targetFrameId = "";
		if (request.getParameter("targetFrameId") != null
				&& request.getParameter("targetFrameId").length() > 0) {
			targetFrameId = request.getParameter("targetFrameId");
		}
		model.put("targetFrameId", targetFrameId);
		model.put("allEqpType", sT002SP1Service.getEqpTypes());
		model.put("allSiteFeq", this.getSiteFeqModel(sT002SP1Service
				.getSiteFeqsByExample(new TbComSiteFeqExample())));
		model.put("domainList", domainList);
		model.put("siteIdPool", siteIdPool);
		model.put("allCoverageType", IncludeRange.getLabelValueList());
		return "ajaxPage/st/ST002SP3";
	}

	/**
	 * 查詢
	 * 
	 * @param srId
	 * @param lon
	 * @param lat
	 * @param coverRange
	 * @return
	 */
	@RequestMapping(value = "/search/unuseBTSSiteId/search")
	@ResponseBody
	public JqGridData<SiteIdPoolDTO> search(HttpServletRequest request,@RequestParam("eqpType") String eqpType,
			@RequestParam("feqType") String feqType,
			@RequestParam("coverageType") String coverageType,
			@RequestParam("domain") String domain, @RequestParam("siteIdBegin") String siteIdBegin,
			@RequestParam("siteEnd") String siteEnd, @RequestParam("useStatus") String useStatus) {
		Map<String, String> model = new HashMap<String, String>();
		if (!feqType.isEmpty()) {
			feqType = feqType.split(",")[1];
		}
		if ("Y".equals(useStatus)) {
			model.put("isUseStatus", "Y");
		} else if ("N".equals(useStatus)) {
			model.put("notUseStatus", "N");
		}
		model.put("eqpType", eqpType);
		model.put("feqType", feqType);
		model.put("domain", domain);
		model.put("siteIdBegin", siteIdBegin);
		model.put("siteEnd", siteEnd);
		model.put("coverageType", coverageType);
		model.put("sort", request.getParameter("sort"));
		model.put("order", request.getParameter("order"));
		List<SiteIdPoolDTO> siteIdPoolList = sT002Service.getUnuseBTSSiteId(model);

		PageList<SiteIdPoolDTO> page = (PageList<SiteIdPoolDTO>) siteIdPoolList;
		return new JqGridData<>(page.getPaginator().getTotalCount(), siteIdPoolList);
	}

	/**
	 * 新增siteWork頁面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/insertPage")
	public String insertPage(HttpServletRequest request, Map<String, Object> model) {
		model.put("siteHuntApplyEvent", "insert");
		model.put("allBuildAim", BuildAim.getLabelValueList());
		model.put("allSiteFeq", this.getSiteFeqModel(sT002SP1Service
				.getSiteFeqsByExample(new TbComSiteFeqExample())));
		model.put("allEqpType", sT002SP1Service.getEqpTypes());
		model.put("allIncludeRange", IncludeRange.getLabelValueList());
		model.put("allFeederless", Feederless.getLabelValueList());
		model.put("allWorkType", WorkType.getLabelValueList("ST002"));
		model.put("allLevel", Level.getLabelValueList());
		model.put("showBtn", true);
		model.put("loginDeptId", getLoginUser().getDeptId());
		return "ajaxPage/st/ST002M";
	}

	/**
	 * 儲存
	 * 
	 * @param siteHuntApply
	 * @param city
	 * @param area
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Map<String, Object> save(SiteHuntApplyDTO siteHuntApply,
			@RequestParam("city") String city, @RequestParam("area") String area,
			@RequestParam("siteHuntApplyEvent") String siteHuntApplyEvent,
			@RequestParam("orderIdArray") String[] orderIdArray) {
		Map<String, Object> map = new HashMap<String, Object>();
		siteHuntApply.setCity(city);
		siteHuntApply.setArea(area);
		if ("insert".equals(siteHuntApplyEvent)) {
			String errorMessage = sT002Service.doValidate(siteHuntApply);
			if (errorMessage.isEmpty()) {
				try {
					TbSiteWork siteWork = sT002Service.insert(siteHuntApply,getLoginUser().getDeptId());
					map.put("result", true);
					map.put("siteWork", siteWork);
				} catch (CreateFailException ce) {
					log.error(ExceptionUtils.getFullStackTrace(ce));
					map.put("result", this.getMessageDetail(ce.getErrCode()));
				} catch (Exception ex) {
					log.error(ExceptionUtils.getFullStackTrace(ex));
					map.put("result", ex.getMessage());
				}
			} else {
				map.put("result", errorMessage);
			}

		} else {
			String errorMessage = sT002Service.doValidate(siteHuntApply);
			if (errorMessage.isEmpty()) {
				try {
					TbSiteWork siteWork = sT002Service.update(siteHuntApply, orderIdArray,
							getLoginUser().getUsername());
					map.put("result", true);
					map.put("siteWork", siteWork);
				} catch (UpdateFailException ex) {
					log.error(ExceptionUtils.getFullStackTrace(ex));
					map.put("result", this.getMessageDetail(ex.getErrCode()));
				} catch (Exception ex) {
					log.error(ExceptionUtils.getFullStackTrace(ex));
					map.put("result", ex.getMessage());
				}
			} else {
				map.put("result", errorMessage);
			}
		}
		return map;
	}

	/**
	 * 修改頁面
	 * 
	 * @param workId
	 * @return
	 */
	@RequestMapping(value = "/updatePage")
	public String getSiteHuntApplyByWorkId(Map<String, Object> model,
			@RequestParam("workId") String workId, @RequestParam("status") String status) {
		String processType = "";
		SiteWorkDTO siteWork = sT002Service.getSiteWorkByWorkId(workId);
		siteWork.setFEQ_ID(siteWork.getFEQ_ID() + "," + siteWork.getFeqType());
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd");
		String dateFormat = sdFormat.format(siteWork.getPREDICE_END_DATE());
		model.put("prediceEndDate", dateFormat);
		if (WorkType.NSR.name().equals(siteWork.getWORK_TYPE())) {
			processType = "SearchSiteApplyNSR";
		} else {
			processType = "SearchSiteApplySH";
		}
		TbSiteWorkExample workExample = new TbSiteWorkExample();
		workExample.createCriteria().andSITE_IDEqualTo(siteWork.getSITE_ID())
			.andWORK_TYPEEqualTo(WorkType.NSR.name()).andWORK_STATUSEqualTo(WorkStatus.W07.name());
		List<TbSiteWork> siteWorkList = sT002Service.getSiteWorkByConditions(workExample);
		if(siteWorkList.size() > 0){
			model.put("isSHByNSRFinish", "Y");
		}
		model.put("showBtn", true);
		model.put("processType", processType);
		model.put("status", status);
		model.put("allBuildAim", BuildAim.getLabelValueList());
		model.put("allSiteFeq", this.getSiteFeqModel(sT002SP1Service
				.getSiteFeqsByExample(new TbComSiteFeqExample())));
		model.put("allEqpType", sT002SP1Service.getEqpTypes());
		model.put("allIncludeRange", IncludeRange.getLabelValueList());
		model.put("allFeederless", Feederless.getLabelValueList());
		model.put("allWorkType", WorkType.getLabelValueList("ST002"));
		model.put("allLevel", Level.getLabelValueList());
		model.put("siteWork", siteWork);
		model.put("siteHuntApplyEvent", "update");
		model.put("workTypeName", WorkType.detectLabel(siteWork.getWORK_TYPE()));
		model.put("workStatusName", WorkStatus.detectLabel(siteWork.getWORK_STATUS()));
		if (!StringUtils.isEmpty(siteWork.getLOCATION_ID())) {
			TbSiteLocation location = sT001Service.getSiteLocationByLocationId(siteWork
					.getLOCATION_ID());
			log.debug("location : "+location.toString());
			model.put("location", location);
			List<SiteDTO> siteList = sT002SP1Service.getSiteByLocationId(siteWork.getLOCATION_ID());
			model.put("isMultibandLocation", siteList.size());
		}
		if (!StringUtils.isEmpty(siteWork.getAPL_USER())) {
			TbAuthPersonnelExample example = new TbAuthPersonnelExample();
			example.createCriteria().andPSN_NOEqualTo(siteWork.getAPL_USER());
			model.put("applyUserName", personnelService.getPersonnelsByExample(example).get(0)
					.getCHN_NM());
		}
		if (!StringUtils.isEmpty(siteWork.getDONOR_SITE())) {
			model.put("donorBtsSiteId",
					sT002SP1Service.getWorkSiteBySiteId(siteWork.getDONOR_SITE()).getBTS_SITE_ID());
		}
		if (!StringUtils.isEmpty(siteWork.getMASTER_SITE())) {
			model.put("masterBtsSiteId",
					sT002SP1Service.getWorkSiteBySiteId(siteWork.getMASTER_SITE()).getBTS_SITE_ID());
		}
		return "ajaxPage/st/ST002M";
	}

	/**
	 * 申請
	 * 
	 * @param workId
	 * @return
	 */
	@RequestMapping(value = "/updateApply")
	@ResponseBody
	public Map<String, Object> updateApplyInfo(SiteHuntApplyDTO siteHuntApply,
			@RequestParam("siteHuntApplyEvent") String siteHuntApplyEvent,
			@RequestParam("orderIdArray") String[] orderIdArray) {
		Map<String, Object> map = new HashMap<String, Object>();
//		if (StringUtils.isEmpty(siteHuntApply.getBtsSiteId())) {
//			if ("L".equals(siteHuntApply.getFeqId().split(",")[1])
//					&& "2".equals(siteHuntApply.getEqpTypeId())) {
//				TbComTownExample example = new TbComTownExample();
//				example.createCriteria().andCITY_IDEqualTo(siteHuntApply.getCity())
//						.andTOWN_IDEqualTo(siteHuntApply.getArea());
//				List<TbComTown> town = sT002Service.getTownByCondition(example);
//				siteHuntApply.setBtsSiteId(uniqueSeqService.getNextNodeB4G(town.get(0)
//						.getZIP_CODE()));
//			}
//		}
		log.debug("siteHuntApply : "+siteHuntApply.toString());
		String errorMessage = sT002Service.doValidate(siteHuntApply);
		if (errorMessage.isEmpty()) {
			try {
				TbSiteWork siteWork = sT002Service.updateApplyInfo(getLoginUser().getUsername(),
						siteHuntApply, getLoginUser().getDeptId(), siteHuntApplyEvent, orderIdArray);
				map.put("siteWork", siteWork);
				map.put("result", true);
			} catch (CreateFailException | UpdateFailException ex) {
				log.error(ExceptionUtils.getFullStackTrace(ex));
				map.put("result", ex.getMessage());
			} catch (NomsException | NullPointerException | WorkflowException ex) {
				log.error(ExceptionUtils.getFullStackTrace(ex));
				map.put("result", ex.getMessage());
			} catch (Exception e) {
				log.error(ExceptionUtils.getFullStackTrace(e));
				map.put("result", "申請失敗");
			}
		} else {
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
			TbSiteWork siteWork = sT002Service.cancelApplyInfo(workId, this.getLoginUser()
					.getUsername());
			map.put("siteWork", siteWork);
			map.put("result", true);
		} catch (NomsException e) {
			map.put("result", e.getMessage());
		}
		return map;
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
	 * 檢查是否為系統設計部(射頻優化部)
	 * 
	 * @param loginDeptId
	 * @return
	 */
	@RequestMapping(value = "/checkDeptId")
	@ResponseBody
	public boolean checkDeptId(@RequestParam("loginDeptId") String loginDeptId) {
		boolean result = false;
		TbSysLookup lookup = lookupService.getByTypeAndCode("ORG_SPECIFIC_DEPT_ID", "SEARCH_RING_DEPT_ID");
		if (lookup.getVALUE1() != null) {
			String[] deptArray = lookup.getVALUE1().split(",");
			for (String dept : deptArray) {
				if (dept.equals(loginDeptId)) {
					result = true;
					break;
				}
			}
		}
		return result;
	}
}
