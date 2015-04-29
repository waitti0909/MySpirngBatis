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
import com.foya.dao.mybatis.model.TbComSiteFeq;
import com.foya.dao.mybatis.model.TbComSiteFeqExample;
import com.foya.dao.mybatis.model.TbOrgDept;
import com.foya.dao.mybatis.model.TbSiteLocation;
import com.foya.dao.mybatis.model.TbSiteWork;
import com.foya.dao.mybatis.model.TbSysOrderType;
import com.foya.dao.mybatis.model.TbSysOrderTypeExample;
import com.foya.dao.mybatis.model.TbSysWorkOrderType;
import com.foya.dao.mybatis.model.TbSysWorkOrderTypeExample;
import com.foya.exception.CreateFailException;
import com.foya.exception.NomsException;
import com.foya.exception.UpdateFailException;
import com.foya.noms.dto.common.TownDomainTeamDTO;
import com.foya.noms.dto.st.SiteBuildApplyDTO;
import com.foya.noms.dto.st.SiteDTO;
import com.foya.noms.dto.st.SiteWorkDTO;
import com.foya.noms.dto.st.SiteWorkOrderDTO;
import com.foya.noms.enums.Level;
import com.foya.noms.enums.SiteStatus;
import com.foya.noms.enums.WorkStatus;
import com.foya.noms.enums.WorkType;
import com.foya.noms.service.auth.PersonnelService;
import com.foya.noms.service.st.ST001Service;
import com.foya.noms.service.st.ST002SP1Service;
import com.foya.noms.service.st.ST004Service;
import com.foya.noms.service.st.ST009Service;
import com.foya.noms.service.st.SiteLinesService;
import com.foya.noms.service.system.OrderTypeService;
import com.foya.noms.util.BaseJasonObject;
import com.foya.noms.util.JqGridData;
import com.foya.noms.util.LabelValueModel;
import com.foya.noms.web.controller.BaseController;
import com.foya.workflow.exception.WorkflowException;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Controller
@RequestMapping(value = "/st/st009")
public class ST009Controller extends BaseController {
	
	@Autowired
	private ST004Service st004Service;
	@Autowired
	private ST009Service sT009Service;
	@Autowired
	private PersonnelService personnelService;
	@Autowired
	private ST002SP1Service sT002SP1Service;
	@Autowired
	private OrderTypeService orderTypeService;
	@Autowired
	private ST001Service sT001Service;
	@Autowired
	private SiteLinesService siteLinesService;

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
		//基站頻段
		List<TbComSiteFeq> comSiteFeqList = sT009Service.getComSiteFeqAll(null);
		model.put("comSiteFeq", comSiteFeqList);
		
		model.put("appDept", deptList);
		model.put("allWorkStatus", WorkStatus.getLabelValueList());
		return "/ajaxPage/st/ST009L";
	}
	
	/**
	 * 查詢
	 * 
	 * @return
	 */
	@RequestMapping(value = "/search")
	@ResponseBody
	public JqGridData<SiteWorkDTO> search(HttpServletRequest request,
			@RequestParam("appDept") String appDept, @RequestParam("btsSiteId") String btsSiteId,
			@RequestParam("siteName") String siteName, @RequestParam("siteWorkCity") String siteWorkCity,
			@RequestParam("siteWorkArea") String siteWorkArea, @RequestParam("siteHuntEqpTemp") String siteHuntEqpTemp,
			@RequestParam("comSiteFeq") String comSiteFeq, @RequestParam("workStatus") String workStatus){
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appDept", appDept);
		map.put("btsSiteId", btsSiteId);
		map.put("siteName", siteName);
		map.put("siteWorkCity", siteWorkCity);
		map.put("siteWorkArea", siteWorkArea);
		map.put("comSiteFeq", comSiteFeq);
		map.put("workStatus", workStatus);
		map.put("siteHuntEqpTemp", siteHuntEqpTemp);
	
		List<TbComSiteFeq> feqList = sT009Service.getComSiteFeqAll(null);
		List<SiteWorkDTO> siteWorkList = sT009Service.getWorkSgnAll(map);
		
		for(SiteWorkDTO workList : siteWorkList) {
			//處理狀態 代碼 --> 中文
			for (LabelValueModel workTypeValue : WorkStatus.getLabelValueList()) {
				if (workTypeValue.getValue().equals(workList.getWORK_STATUS())) {
					workList.setWorkStatusName(workTypeValue.getLabel());
				}
			}
			//基站頻段
			for (TbComSiteFeq feqTemp : feqList) {
				if (StringUtils.equals(workList.getFEQ_ID(), feqTemp.getFEQ_ID())) {
					workList.setSrFeqId(feqTemp.getFEQ_NAME());
				}
			}
			//基站狀態
			for (LabelValueModel workTypeValue : SiteStatus.getLabelValueList()) {
				if (workTypeValue.getValue().equals(workList.getSiteStatus())) {
					workList.setSiteStatusName(workTypeValue.getLabel());
				}
			}
		}
		PageList<SiteWorkDTO> page = (PageList<SiteWorkDTO>) siteWorkList;
		return new JqGridData<>(page.getPaginator().getTotalCount(),
				siteWorkList);
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
	public BaseJasonObject<TbSysOrderType> sysOrderType(@RequestParam("eqpTypeId") String eqpTypeId, @RequestParam("siteStatus") String startSiteStatus, 
			HttpServletRequest request, Map<String, Object> model) {	
		
		TbSysWorkOrderTypeExample exampleType = new TbSysWorkOrderTypeExample();
		List<String> tempList = new ArrayList<String>();
		tempList.add("NSR");
		tempList.add("SH");
		exampleType.createCriteria().andWK_TYPE_IDNotIn(tempList).andOD_SEQNotEqualTo(99);
		List<TbSysWorkOrderType> list = sT009Service.getSysWorkType(exampleType);
		
		List<String> temp = new ArrayList<String>();
		for (TbSysWorkOrderType listTemp : list) {
			temp.add(listTemp.getOD_TYPE_ID());
		}

		TbSysOrderTypeExample example = new TbSysOrderTypeExample(); 
		// 設備型態 為 H 的 (基站編號)機房
		List<String> tempEqp = new ArrayList<String>();
		if (StringUtils.equals(eqpTypeId, "H")) {			
			tempEqp.add("EDN");
			tempEqp.add("LAN");
			tempEqp.add("SCH");
			example.createCriteria().andOD_TYPE_IDIn(tempEqp);
		} else if (StringUtils.equals(eqpTypeId, "C")){
			tempEqp.add("EDN");
			tempEqp.add("LAN");
			tempEqp.add("SCN");	
			example.createCriteria().andOD_TYPE_IDIn(tempEqp);
		} else if (StringUtils.equals(startSiteStatus, "S02") || StringUtils.equals(startSiteStatus, "S04")) {
			tempEqp.add("CL");	
			example.createCriteria().andOD_TYPE_IDIn(tempEqp);
		} else {
			tempEqp.add("EDN");
			tempEqp.add("LAN");
			tempEqp.add("SCN");
			tempEqp.add("SCH");
			example.createCriteria().andOD_TYPE_IDIn(temp).andOD_TYPE_IDNotIn(tempEqp);
		}

		List<TbSysOrderType> labelModelList = sT009Service.getSysOrderTypes(example);
		
		return new BaseJasonObject<>(labelModelList, AJAX_SUCCESS, AJAX_EMPTY);
	}	
	
	/**
	 * 工單類別
	 */
	@RequestMapping(value = "/initOrderType")
	@ResponseBody
	public BaseJasonObject<TbSysOrderType> initSysOrderType(HttpServletRequest request, Map<String, Object> model) {	
		
		TbSysWorkOrderTypeExample exampleType = new TbSysWorkOrderTypeExample();
		List<String> tempList = new ArrayList<String>();
		tempList.add("NSR");
		tempList.add("SH");
		exampleType.createCriteria().andWK_TYPE_IDNotIn(tempList).andOD_SEQNotEqualTo(99);
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
		model.put("singleOrderApplyEvent", "insert");
		//model.put("allSiteStatus", SiteStatus.getLabelValueList("SB"));
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
		TbOrgDept responsibleUnit = new TbOrgDept();
		try {
			if (domainTeam != null &&  !domainTeam.equals("")) {			
				//查詢 deptKey
				String domain = domainTeam.getDOMAIN();
				String deptKey = orderTypeService.getOrdersDeptKey(odTypeId, domain, eqpType);		
				//查詢負責單位
				responsibleUnit = orderTypeService.getOrderTypeForWorkArea(deptKey, domainTeam);
				return new BaseJasonObject<>(responsibleUnit, AJAX_SUCCESS);
			} 
		} catch (Exception ex) {
			log.error("ex.getMessage() : " + ex.getMessage());
			log.error(ExceptionUtils.getFullStackTrace(ex));
		}
		return new BaseJasonObject<>(responsibleUnit, AJAX_FAILED);
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
			HttpServletRequest request,
			@RequestParam("workId") String workId,
			@RequestParam("status") String status) {
		// 建站作業資料
		SiteWorkDTO siteWork = st004Service.getSiteWorkByWorkId(workId);

		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd");
		String dateFormat = sdFormat.format(siteWork.getPREDICE_END_DATE());
		model.put("prediceEndDate", dateFormat);
		model.put("workId", workId);
		model.put("showBtn", true);
		
		model.put("processType", "SiteBuildSingleWorkApply");
		
		model.put("status", status);
		model.put("showWorkType", WorkType.getLabelValueList("ST009").get(0).getValue());
		model.put("allSiteStatus", SiteStatus.getLabelValueList("SB"));
		model.put("locationId", siteWork.getLOCATION_ID());
		model.put("siteBuildSiteId", siteWork.getSITE_ID());
		
		// 建站工單資料
		String orderIdStr = "";
		List<SiteWorkOrderDTO> workOrderList= st004Service.getSiteWorkOrderByWorkId(workId);
		if(workOrderList.size()>0){
			for(SiteWorkOrderDTO workOrder : workOrderList){
				if("Y".equals(workOrder.getIS_ACTIVE())){
					orderIdStr+=workOrder.getORDER_ID()+",";
				}
			}
			if(StringUtils.isNotBlank(orderIdStr)) {
				orderIdStr = orderIdStr.substring(0, orderIdStr.length()-1);
			}
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
		List<LabelValueModel> feqName = st004Service.getSiteFeqs();
		for(LabelValueModel siteFeqName : feqName) {
			if(siteFeqName.getValue().equals(siteWork.getFEQ_ID())) {
				model.put("siteFeqName", siteFeqName.getLabel());		
				break;
			}
		}
		SimpleDateFormat sdFormatEnd = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		if (siteWork.getEND_TIME() != null ) {			
			String endTime = sdFormatEnd.format(siteWork.getEND_TIME());
			model.put("siteWorkEndTime", endTime);
		}
		model.put("siteWork", siteWork);
		
		model.put("singleOrderApplyEvent", "edit");

		// 作業類別
		model.put("workTypeName", "單張工單");
		// 處理狀態
		model.put("workStatusName",
				WorkStatus.detectLabel(siteWork.getWORK_STATUS()));
		model.put("siteHuntWorkStatus",siteWork.getWORK_STATUS());
		
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

		return "ajaxPage/st/ST009M";
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
		if(!StringUtils.equals(singleOrderApplyEvent, "insert")) {
			if (!orderId.equals("")) {
				orderIdArray = orderId.split(",");
				log.debug("orderIdArray : "+orderIdArray.toString());
			}
		}
		String errorMessage = sT009Service.doValidate(siteBuildApply);
		if(StringUtils.isEmpty(errorMessage)){
			try {
				TbSiteWork siteWork = sT009Service.updateApplyInfo(getLoginUser().getUsername(), 
						siteBuildApply, getLoginUser().getDeptId(), singleOrderApplyEvent, orderIdArray);
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
}
