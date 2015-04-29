package com.foya.noms.web.controller.workflow;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foya.noms.dto.workflow.WorkflowTodoDTO;
import com.foya.noms.dto.workflow.WorkflowWebDTO;
import com.foya.noms.service.workflow.WorkflowActionService;
import com.foya.noms.util.BaseJasonObject;
import com.foya.noms.web.controller.BaseController;
import com.foya.workflow.exception.WorkflowException;

@Controller
public class WorkflowController extends BaseController {

	@Autowired
	private WorkflowActionService workflowActionService;

	/**
	 * 執行 workflow 動作
	 * 
	 * @param request HttpServletRequest
	 * @param action 執行動作
	 * @param workflowWebDtos 從前端傳遞的資料 list
	 * @return message
	 */
	@RequestMapping(value = "/workflow/execute/{action}")
	public @ResponseBody BaseJasonObject<String> doWorkflow(HttpServletRequest request,
			@PathVariable String action, @RequestBody List<WorkflowWebDTO> workflowWebDtos) {
		try {
			workflowActionService.execute(action, workflowWebDtos);
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			return new BaseJasonObject<String>(false, e.getLocalizedMessage());
		}
		return new BaseJasonObject<String>(true, AJAX_SUCCESS);
	}

	/**
	 * 導向簽核待辦頁面(內部使用)
	 */
	@RequestMapping(value = "/workflow/query")
	public String initAgentTodoListPage(Map<String, Object> model) {
		model.put("processMap", workflowActionService.findProcessTypeName());
		return "/flowTasks";
	}

	/**
	 * 取得自己的待辦工作清單(內部使用)
	 * 
	 * @return BaseJasonObject
	 * @throws WorkflowException
	 */
	@RequestMapping(value = "/workflow/query/data")
	public @ResponseBody BaseJasonObject<WorkflowTodoDTO> query() throws WorkflowException {
		HttpServletRequest request = getRequest();
		String processType = request.getParameter("processType");
		String applyNo = request.getParameter("applyNo");
		return new BaseJasonObject<>(workflowActionService.query(processType, applyNo),
				AJAX_SUCCESS, AJAX_EMPTY);
	}

}
