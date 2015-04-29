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
import com.foya.dao.mybatis.model.TbComEqpModel;
import com.foya.dao.mybatis.model.TbComSiteFeq;
import com.foya.dao.mybatis.model.TbComSiteFeqExample;
import com.foya.dao.mybatis.model.TbSiteLocation;
import com.foya.dao.mybatis.model.TbSiteMain;
import com.foya.dao.mybatis.model.TbSiteMainExample;
import com.foya.dao.mybatis.model.TbSiteWork;
import com.foya.exception.CreateFailException;
import com.foya.exception.NomsException;
import com.foya.exception.UpdateFailException;
import com.foya.noms.dto.basic.CompanyDTO;
import com.foya.noms.dto.st.SiteBuildApplyDTO;
import com.foya.noms.dto.st.SiteDTO;
import com.foya.noms.dto.st.SiteWorkDTO;
import com.foya.noms.dto.st.SiteWorkOrderDTO;
import com.foya.noms.enums.BuildAim;
import com.foya.noms.enums.Feederless;
import com.foya.noms.enums.IncludeRange;
import com.foya.noms.enums.Level;
import com.foya.noms.enums.OrderStatus;
import com.foya.noms.enums.PurchaseOrderType;
import com.foya.noms.enums.SiteStatus;
import com.foya.noms.enums.WorkStatus;
import com.foya.noms.enums.WorkType;
import com.foya.noms.service.auth.PersonnelService;
import com.foya.noms.service.st.ST001Service;
import com.foya.noms.service.st.ST002SP1Service;
import com.foya.noms.service.st.ST002Service;
import com.foya.noms.service.st.ST004Service;
import com.foya.noms.service.st.SiteService;
import com.foya.noms.util.BaseJasonObject;
import com.foya.noms.util.JqGridData;
import com.foya.noms.web.controller.BaseController;
import com.foya.workflow.exception.WorkflowException;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Controller
@RequestMapping(value = "/st/st004")
public class ST004Controller extends BaseController {

	@Autowired
	private ST004Service st004Service;

	@Autowired
	private ST002SP1Service sT002SP1Service;

	@Autowired
	private ST002Service sT002Service;

	@Autowired
	private ST001Service sT001Service;

	@Autowired
	private PersonnelService personnelService;

	@Autowired
	private SiteService siteService;
	/**
	 * 初始頁面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/init")
	public String st004Init(HttpServletRequest request,
			Map<String, Object> model) {
		Set<String> set = getLoginUser().getAccessDeptListByMenuID(
				Integer.valueOf(request.getParameter("menuId")));
		List<String> list = new ArrayList<String>();
		if (set != null) {
			list = new ArrayList<String>(set);
		}

		// 工務類別
		model.put("allWorkType", WorkType.getLabelValueList("ST004"));

		// 申請單位
		List<SiteWorkDTO> siteWorkList = st004Service.getWorkListByDeptId(list);
		model.put("siteWorkList", siteWorkList);

		// 處理狀態
		model.put("allWorkStatus", WorkStatus.getLabelValueList());

		// 基站頻段
		model.put("allSiteFeq", st004Service.getSiteFeqs());

		// 設備型態
		model.put("allEqpTypes", st004Service.getEqpTypes());

		return "ajaxPage/st/ST004L";
	}

	/**
	 * 查詢
	 * 
	 * @return
	 */
	@RequestMapping(value = "/search")
	@ResponseBody
	public JqGridData<SiteWorkDTO> search(
			@RequestParam("workType") String workType,
			@RequestParam("applyDept") String applyDept,
			@RequestParam("siteWorkCity") String siteWorkCity,
			@RequestParam("siteWorkArea") String siteWorkArea,
			@RequestParam("eqpTypeId") String eqpTypeId,
			@RequestParam("btsSiteId") String btsSiteId,
			@RequestParam("siteName") String siteName,
			@RequestParam("workStatus") String workStatus,
			@RequestParam("siteFeq") String siteFeq,
			@RequestParam("rangeBa") String rangeBa,
			@RequestParam("rangeBb") String rangeBb) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("workType", workType);
		map.put("siteWorkCity", siteWorkCity);
		map.put("siteWorkArea", siteWorkArea);
		map.put("eqpTypeId", eqpTypeId);
		map.put("btsSiteId", btsSiteId);
		map.put("siteName", siteName);
		map.put("workStatus", workStatus);
		map.put("siteFeq", siteFeq);
		map.put("applyDateStart", rangeBa);
		map.put("applyDateEnd", rangeBb);
		map.put("type", "T");
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
		List<SiteWorkDTO> siteWorkList = st004Service.getWorkList(map);
		for (SiteWorkDTO siteWork : siteWorkList) {
			siteWork.setWorkStatusName(WorkStatus.detectLabel(siteWork.getWORK_STATUS()));
			siteWork.setSiteStatus(SiteStatus.detectLabel(siteWork.getSiteStatus()));
			siteWork.setWORK_TYPE(WorkType.detectLabel(siteWork.getWORK_TYPE()));
		}
		PageList<SiteWorkDTO> page = (PageList<SiteWorkDTO>) siteWorkList;
		return new JqGridData<>(page.getPaginator().getTotalCount(),
				siteWorkList);
	}

	/**
	 * 新增建站作業申請頁面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/insertPage")
	public String insertPage(HttpServletRequest request,
			Map<String, Object> model) {
		// 工作類型
		model.put("siteBuildApplyEvent", "new");
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
		model.put("showBtn", true);
		model.put("workTypeName", "建站作業");

		return "ajaxPage/st/ST004M";
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
			@RequestParam("status") String status) {
		// 建站作業資料
		SiteWorkDTO siteWork = st004Service.getSiteWorkByWorkId(workId);

		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd");
		String dateFormat = sdFormat.format(siteWork.getPREDICE_END_DATE());
		model.put("prediceEndDate", dateFormat);

		model.put("showBtn", true);
		model.put("processType", "SiteBuildApply");
		model.put("status", status);
		// 建站工單資料
		String orderIdStr = "", orderNameStr = "";
		List<SiteWorkOrderDTO> workOrderList= st004Service.getSiteWorkOrderByWorkId(workId);
		if(workOrderList.size()>0){
			for(SiteWorkOrderDTO workOrder : workOrderList){
				if("Y".equals(workOrder.getIS_ACTIVE())){
					orderIdStr+=workOrder.getORDER_ID()+",";
					orderNameStr+=workOrder.getOrderTypeName()+"<br>";
				}
			}
			if(orderIdStr.length() != 0){
				orderIdStr = orderIdStr.substring(0, orderIdStr.length()-1);
			}
			
		}
		model.put("orderIdStr", orderIdStr);
		model.put("orderNameStr", orderNameStr);
		
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
		model.put("siteBuildApplyEvent", "edit");

		// 作業類別
		model.put("workTypeName", "建站作業");
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
			List<TbAuthPersonnel> psnList = personnelService.getPersonnelsByExample(example);
			if(psnList.size()>0){
				model.put("applyUserName",psnList.get(0).getCHN_NM());
			}
			
		}
		model.put("donorBtsSiteId", siteWork.getDONOR_SITE());

		model.put("masterBtsSiteId", siteWork.getMASTER_SITE());

		return "ajaxPage/st/ST004M";
	}

	/**
	 * 儲存
	 * 
	 * @param SiteBuildApply
	 * @param siteBuildApplyEvent
	 * @param orderId
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Map<String, Object> save(SiteBuildApplyDTO siteBuildApply,
			@RequestParam("siteBuildApplyEvent") String siteBuildApplyEvent,
			@RequestParam("orderId") String orderId) {
		Map<String, Object> map = new HashMap<String, Object>();
		String errorMessage = st004Service.doValidate(siteBuildApply);
		if(StringUtils.isEmpty(errorMessage)){
			if ("new".equals(siteBuildApplyEvent)) {
				try {
					TbSiteWork siteWork = st004Service.insert(siteBuildApply);
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
					st004Service.update(siteBuildApply, orderIdArray,
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
		}else{
			map.put("result", errorMessage);
		}
		return map;
	}

	/**
	 * 申請
	 * 
	 * @param workId
	 * @return
	 */
	@RequestMapping(value = "/updateApply")
	@ResponseBody
	public Map<String, Object> updateApplyInfo(SiteBuildApplyDTO siteBuildApply,@RequestParam("orderId") String orderId) {
		Map<String, Object> map = new HashMap<String, Object>();
//		String errMsg = null;
		
		String[] orderIdArray = null;
		if (!orderId.equals("")) {
			orderIdArray = orderId.split(",");
			log.debug("orderIdArray : "+orderIdArray.toString());
		}
		
		String errorMessage = st004Service.doValidate(siteBuildApply);
		if(StringUtils.isEmpty(errorMessage)){
			try {
				TbSiteWork siteWork = st004Service.updateApplyInfo(getLoginUser()
						.getUsername(), siteBuildApply, getLoginUser().getDeptId(),orderIdArray);
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
			TbSiteWork siteWork = st004Service.cancelApplyInfo(workId, this
					.getLoginUser().getUsername());
			map.put("siteWork", siteWork);
			map.put("result", true);
		} catch (NomsException e) {
			map.put("result", e.getMessage());
		}
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
		List<TbSiteMain> siteMainList = sT002SP1Service
				.getWorkSiteListByExample(example);
		return siteMainList.get(0);
	}

	/**
	 * 查詢location
	 * 
	 * @param locationId
	 * @return
	 */
	@RequestMapping(value = "/search/location")
	@ResponseBody
	public Map<String, Object> getLocation(
			@RequestParam("locationId") String locationId) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<SiteDTO> siteList = sT002SP1Service
				.getSiteByLocationId(locationId);
		List<String> siteFeqList = new ArrayList<String>();
		for (SiteDTO site : siteList) {
			siteFeqList.add(site.getFEQ_ID());
		}
		TbComSiteFeqExample example = new TbComSiteFeqExample();
		List<TbComSiteFeq> sourceSiteFeqList = sT002SP1Service
				.getSiteFeqsByExample(example);
		example = new TbComSiteFeqExample();
		example.createCriteria().andFEQ_IDIn(siteFeqList);
		List<TbComSiteFeq> targetSiteFeqList = sT002SP1Service
				.getSiteFeqsByExample(example);
		sourceSiteFeqList.removeAll(targetSiteFeqList);
		map.put("siteLoc", sT001Service.getSiteLocationByLocationId(locationId));
		map.put("isMultibandLocation", siteList.size());
		return map;
	}

	/**
	 * 查詢委外廠商
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/search/searchOsVen")
	@ResponseBody
	public BaseJasonObject<CompanyDTO> getSearchOsVen(
			@RequestParam("osType") String opType) {
//		Map<String, Object> map = new HashMap<String, Object>();
		List<CompanyDTO> OsVenList = st004Service.getOsVenList(opType);
		return new BaseJasonObject<>(OsVenList, AJAX_SUCCESS, AJAX_EMPTY);
	}

	/**
	 * 查詢設備機型
	 * 
	 * @param eqpTypeId
	 * @return
	 */
	@RequestMapping(value = "/search/eqpModel")
	@ResponseBody
	public Map<String, String> getEqpModel(
			@RequestParam("eqpTypeId") String eqpTypeId) {
		Map<String, String> map = new HashMap<String, String>();
		if(StringUtils.isNotEmpty(eqpTypeId)){
			List<TbComEqpModel> eqpModelList = siteService.getEqpModelListByEqpTypeId(eqpTypeId);
			for (TbComEqpModel eqpModel : eqpModelList) {
				map.put(eqpModel.getEQP_MODEL_ID(), eqpModel.getEQP_MODEL());
			}
		}
		return map;
	}

	/**
	 * 查詢workOrder資料
	 * 
	 * @param workId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/search/workOrder")
	@ResponseBody
	public BaseJasonObject<SiteWorkOrderDTO> getWorkOrder(
			@RequestParam("workId") String workId, Map<String, Object> model) {
		List<SiteWorkOrderDTO> siteWorkOrderList = st004Service
				.getSiteWorkOrderByWorkId(workId);
		for (SiteWorkOrderDTO siteWorkOrder : siteWorkOrderList) {
			siteWorkOrder.setODR_STATUS(OrderStatus.detectLabel(siteWorkOrder
					.getODR_STATUS()));
		}
		
		model.put("siteWorkOrder", siteWorkOrderList);

		return new BaseJasonObject<>(siteWorkOrderList, AJAX_SUCCESS,
				AJAX_EMPTY);
	}

}
