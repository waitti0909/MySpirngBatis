package com.foya.noms.web.controller.common;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foya.dao.mybatis.model.TbOrgDept;
import com.foya.dao.mybatis.model.TbOrgDeptExample;
import com.foya.dao.mybatis.model.TbSysLookup;
import com.foya.dao.mybatis.model.TbWorkflowCfg;
import com.foya.noms.dto.auth.PersonnelDTO;
import com.foya.noms.dto.system.SignLogDTO;
import com.foya.noms.service.auth.PersonnelService;
import com.foya.noms.service.org.ORG002Service;
import com.foya.noms.service.system.LookupService;
import com.foya.noms.service.system.SignLogService;
import com.foya.noms.service.workflow.WorkflowActionService;
import com.foya.noms.service.workflow.WorkflowCfgService;
import com.foya.noms.util.BaseJasonObject;
import com.foya.noms.util.LabelValueModel;
import com.foya.noms.web.controller.BaseController;

@Controller
public class ApprovalController extends BaseController {

	@Autowired
	private SignLogService signLogService;

	@Autowired
	private ORG002Service org002Service;

	@Autowired
	private PersonnelService personnelService;

	@Autowired
	private WorkflowCfgService workflowCfgService;

	@Autowired
	private WorkflowActionService workflowActionService;

	@Autowired
	private LookupService lookupService;

	/**
	 * 開啟單筆簽核作業
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/workflow/openTask")
	public String openTask(HttpServletRequest request, Map<String, Object> modelMap) {
		String applyNo = request.getParameter("applyNo");
		String processType = request.getParameter("processType");
		String detailPageURL = "";
		if (StringUtils.isNotEmpty(processType)) {
			TbWorkflowCfg cfg = workflowCfgService.getWorkflowCfgByProcessType(processType);
			detailPageURL = cfg.getDETAIL_PAGE_URL() + applyNo;
		}

		modelMap.put("isAgent", request.getParameter("isAgent"));
		modelMap.put("taskId", request.getParameter("taskId"));
		modelMap.put("applyNo", applyNo);
		modelMap.put("processType", processType);
		modelMap.put("processName", request.getParameter("processName"));
		modelMap.put("taskName", request.getParameter("taskName"));
		modelMap.put("taskOwnerId", request.getParameter("taskOwnerId"));
		modelMap.put("taskOwnerGroupId", request.getParameter("taskOwnerGroupId"));
		modelMap.put("taskStartTime", request.getParameter("taskStartTime"));
		modelMap.put("userTaskType", request.getParameter("userTaskType"));
		modelMap.put("approvalPSN", getLoginUser().getChName());
		modelMap.put("detailPageURL", detailPageURL);
		modelMap.put("jsonData", request.getParameter("jsonData"));
		modelMap.put("rejectTypes", getRejectTypes(processType));
		return "ajaxPage/common/Approval";
	}

	private List<LabelValueModel> getRejectTypes(String processType) {
		List<LabelValueModel> result = new ArrayList<>();
		List<TbSysLookup> list = lookupService.getByType("WF_REJECT_TYPE");
		for (TbSysLookup lookup : list) {
			String value = lookup.getVALUE1();
			if (StringUtils.isNotBlank(value)) {
				String[] values = value.split(",");
				for (String val : values) {
					if (StringUtils.isNotBlank(val)) {
						val = val.trim();
					} else {
						continue;
					}
					if (StringUtils.equalsIgnoreCase(val, "ALL")
							|| StringUtils.equalsIgnoreCase(val, processType)) {
						result.add(new LabelValueModel(lookup.getNAME(), lookup.getCODE()));
						break;
					}
				}
			}
		}
		return result;
	}

	/**
	 * 簽核記錄
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/commom/signHistory")
	public String getSignHistory(HttpServletRequest request, Map<String, Object> modelMap) {
		String processType = request.getParameter("processType");
		String applyNo = request.getParameter("applyNo");
		List<SignLogDTO> rows = signLogService.getSignHistory(processType, applyNo);
		modelMap.put("signHistory", rows);
		if (StringUtils.isNotBlank(processType) && StringUtils.isNotBlank(applyNo)) {
			modelMap.put("currentTodos", workflowActionService.query(processType, applyNo));
		}
		return "ajaxPage/common/SignHistory";

	}

	/**
	 * 開啟加/會簽頁面
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/commom/counterSign")
	public String counterSign(HttpServletRequest request, Map<String, Object> modelMap,
			Map<String, String> model) {
		TbOrgDeptExample example = new TbOrgDeptExample();
		Map<String, String> map = new LinkedHashMap<>();
		List<TbOrgDept> rows = org002Service.selectAllOrgDept(example);
		for (TbOrgDept row : rows) {
			map.put(row.getDEPT_ID(), row.getDEPT_NAME());
		}
		modelMap.put("deptMap", map);

		model.put("isAgent", request.getParameter("isAgent"));
		model.put("taskId", request.getParameter("taskId"));
		model.put("processType", request.getParameter("processType"));
		model.put("applyNo", request.getParameter("applyNo"));
		model.put("taskOwnerId", request.getParameter("taskOwnerId"));
		model.put("taskOwnerGroupId", request.getParameter("taskOwnerGroupId"));
		model.put("comment", request.getParameter("approvalComment"));
		model.put("taskStartTime", request.getParameter("taskStartTime"));
		model.put("approveType", request.getParameter("approveType"));
		model.put("jsonData", request.getParameter("jsonData"));

		return "ajaxPage/common/CounterSign";
	}

	/**
	 * 取得部門下所有職稱之人員
	 * 
	 * @param dept
	 * @return
	 */
	@RequestMapping(value = "/commom/counterSign/getDeptMember")
	@ResponseBody
	public BaseJasonObject<PersonnelDTO> counterSignGetDeptMember(
			@RequestParam(value = "dept") String dept) {
		return new BaseJasonObject<>(personnelService.getDeptPersonnels(dept), AJAX_SUCCESS,
				AJAX_EMPTY);
	}

}
