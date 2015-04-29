package com.foya.noms.web.controller.profile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foya.dao.mybatis.model.TbAuthMenu;
import com.foya.noms.dto.profile.TodoOrdersDTO;
import com.foya.noms.dto.workflow.WorkflowTodoDTO;
import com.foya.noms.service.profile.ProfileService;
import com.foya.noms.service.profile.TodoOrdersService;
import com.foya.noms.service.workflow.WorkflowActionService;
import com.foya.noms.util.BaseJasonObject;
import com.foya.noms.web.controller.BaseController;
import com.foya.noms.web.controller.inv.INV008Controller;
import com.foya.noms.web.controller.inv.INV009Controller;
import com.foya.noms.web.controller.inv.INV012Controller;
import com.foya.noms.web.controller.inv.INV015Controller;

@Controller
public class ProfileController extends BaseController {

	@Autowired
	private ProfileService profileService;
	@Autowired
	private WorkflowActionService workflowActionService;
	@Autowired
	private INV008Controller inv008Controller;
	@Autowired
	private INV009Controller inv009Controller;
	@Autowired
	private INV012Controller inv012Controller;
	@Autowired
	private INV015Controller inv015Controller;
	@Autowired
	private TodoOrdersService todoOrdersService;

	/**
	 * 導向簽核待辦頁面
	 */
	@RequestMapping(value = "/profile/todoList")
	public String initTodoList() {
		return "/ajaxPage/profile/todoList";
	}

	/**
	 * 導向簽核待辦頁面(自己的待辦工作)
	 */
	@RequestMapping(value = "/profile/todoList/own")
	public String initOwnTodoListPage() {
		return "/ajaxPage/profile/todoList_01";
	}

	/**
	 * 取得自己的待辦工作清單
	 * 
	 * @return BaseJasonObject
	 * @throws Exception
	 */
	@RequestMapping(value = "/profile/todoList/own/data")
	public @ResponseBody
	BaseJasonObject<WorkflowTodoDTO> getOwnTodoListData() throws Exception {
		List<WorkflowTodoDTO> todoDtos = null;
		BaseJasonObject<WorkflowTodoDTO> baseJasonObject = new BaseJasonObject<WorkflowTodoDTO>();
		try {
			todoDtos = profileService.getOwnTodoList(getLoginUser());
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			baseJasonObject.setSuccess(false);
			baseJasonObject.setRows(new ArrayList<WorkflowTodoDTO>());
			return baseJasonObject;
		}
		baseJasonObject.setSuccess(true);
		baseJasonObject.setRows(todoDtos);
		return baseJasonObject;
	}

	/**
	 * 導向簽核待辦頁面(代理的待辦工作)
	 */
	@RequestMapping(value = "/profile/todoList/agent")
	public String initAgentTodoListPage() {
		return "/ajaxPage/profile/todoList_02";
	}

	/**
	 * 取得代理的待辦工作清單
	 * 
	 * @return BaseJasonObject
	 */
	@RequestMapping(value = "/profile/todoList/agent/data")
	public @ResponseBody
	BaseJasonObject<WorkflowTodoDTO> getAgentTodoDataPage() {
		List<WorkflowTodoDTO> todoDtos = null;
		BaseJasonObject<WorkflowTodoDTO> baseJasonObject = new BaseJasonObject<WorkflowTodoDTO>();
		try {
			todoDtos = profileService.getAgentTodoList(getLoginUser());
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			baseJasonObject.setSuccess(false);
			baseJasonObject.setRows(new ArrayList<WorkflowTodoDTO>());
			return baseJasonObject;
		}
		baseJasonObject.setSuccess(true);
		baseJasonObject.setRows(todoDtos);
		return baseJasonObject;
	}

	@RequestMapping(value = "/profile/todoOrders")
	public String initTodoOrdersPage() {
		return "/ajaxPage/profile/todoOrders";
	}

	/**
	 * 取得工單待辦事項列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/profile/todoOrders/data")
	@ResponseBody
	public BaseJasonObject<TodoOrdersDTO> getTodoOrders(Map<String, Object> map, HttpServletRequest request) {

		List<TbAuthMenu> limitsOfAuthorityList = profileService.getLimitsOfAuthority(getLoginUser().getUserId());

		List<TodoOrdersDTO> todoDtos = new ArrayList<>();
		// 取得該登入人員需待處理之工單列表
		
		for(TbAuthMenu menu : limitsOfAuthorityList){
			if(menu.getPGM_PATH().contains("/inv/inv008/init")){
				todoDtos.addAll(inv008Controller.getTodoOrders());
			}
			if(menu.getPGM_PATH().contains("/inv/inv009/init")){
				todoDtos.addAll(inv009Controller.getTodoOrders());
			}
			if(menu.getPGM_PATH().contains("/inv/inv012/init")){
				todoDtos.addAll(inv012Controller.getTodoOrders());
			}
			if(menu.getPGM_PATH().contains("/inv/inv015/init")){
				todoDtos.addAll(inv015Controller.getTodoOrders());
			}
		}
		todoDtos.addAll(todoOrdersService.getTodoOrders(getLoginUser()));
		return new BaseJasonObject<>(todoDtos, AJAX_SUCCESS, AJAX_EMPTY);
	}

}
