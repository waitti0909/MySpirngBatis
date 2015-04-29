package com.foya.noms.web.controller.auth;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foya.noms.dto.auth.RoleMenuFunDepDTO;
import com.foya.noms.resources.AppConstants;
import com.foya.noms.service.auth.RoleMastService;
import com.foya.noms.service.auth.RoleMenuFunService;
import com.foya.noms.util.BaseJasonObject;
import com.foya.noms.util.JqGridData;
import com.foya.noms.web.controller.BaseController;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Controller
@RequestMapping(value = "/auth")
public class RoleMenuFunController extends BaseController {
	
	@Autowired
	private RoleMastService roleService;
	
	@Autowired
	private RoleMenuFunService roleMenuFunService;
	
	/**
	 * 依角色ID和程式ID找功能和部門
	 * 
	 * @param roleId
	 * @param menuId
	 * @return
	 */
	@RequestMapping(value = "/roleMenuFun/searchFunDept")
	public @ResponseBody JqGridData<RoleMenuFunDepDTO> searchFunDeptDTO(@RequestParam(value = "roleId") Integer roleId,
			@RequestParam(value = "menuId") Integer menuId) {

		List<RoleMenuFunDepDTO> rsList = roleMenuFunService.selectFuncDepDTOByRoleAndMenuForGrid(roleId, menuId);
		PageList<RoleMenuFunDepDTO> page = (PageList<RoleMenuFunDepDTO>) rsList;

		return new JqGridData<>(page.getPaginator().getTotalCount(), rsList);
	}
	
	/**
	 * 新增頁
	 * @param roleId
	 * @param menuId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/roleMenuFun/add/init")
	public String initAddPage(Map<String, Object> model) {
//		model.put("role", roleService.getByRoleId(roleId));
//		model.put("menuId", menuId);
		model.put("allRoles", roleService.getAll());
		model.put("suffix", AppConstants.INSERT);
		return "ajaxPage/auth/roleMenuFunM";
	}
	
	/**
	 * 編輯頁
	 * @param roleId
	 * @param menuId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/roleMenuFun/edit/init")
	public String initEditPage(@RequestParam(value = "btnType") String btnType, @RequestParam(value = "roleId") Integer roleId, @RequestParam(value = "menuId") Integer menuId, Map<String, Object> model) {
		model.put("showType", btnType);
		model.put("roleId", roleId);
		model.put("menuId", menuId);
		model.put("allRoles", roleService.getAll());
		model.put("suffix", AppConstants.EDIT);
		return "ajaxPage/auth/roleMenuFunM";
	}
	
	/**
	 * 存檔
	 * @param roleId
	 * @param menuId
	 * @param menuFunIds
	 * @return
	 */
	@RequestMapping(value = "/roleMenuFun/save/")
	@ResponseBody
	public String roleMenuFunSave(@RequestParam(value="roleSelected") BigDecimal roleId, 
			@RequestParam(value="currentMenu") BigDecimal menuId, 
			@RequestParam(value="checkedFunctions", required = false) BigDecimal[] menuFunIds, 
			@RequestParam(value="selectedDepts", required = false) String[] deptIds) {
		
		try {
			roleMenuFunService.saveRoleMenuFun(roleId, menuId, menuFunIds, deptIds, getLoginUser().getUsername());
			return AJAX_SUCCESS;
		} catch (Exception e) {
		
			e.printStackTrace();
			return AJAX_FAILED;
		}
	}
	
	/**
	 * AJAX FOR FIND FUNCTIONS BY ROLE_ID AND MENU_ID
	 * @param roleId
	 * @param menuId
	 * @return
	 */
	@RequestMapping(value = "/roleMenuFun/getCheckedFunctions")
	@ResponseBody
	public BaseJasonObject<RoleMenuFunDepDTO> getCheckedFunctions(@RequestParam(value = "roleId") Integer roleId, @RequestParam(value = "menuId") Integer menuId) {
		List<RoleMenuFunDepDTO> rows = roleMenuFunService.getFuncDepByRoleAndMenu(roleId, menuId);
		return new BaseJasonObject<>(rows, AJAX_SUCCESS, AJAX_EMPTY);
	}
}