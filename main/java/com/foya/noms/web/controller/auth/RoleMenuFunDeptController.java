package com.foya.noms.web.controller.auth;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foya.dao.mybatis.model.TbAuthRoleMenuFunDept;
import com.foya.dao.mybatis.model.TbOrgDept;
import com.foya.dao.mybatis.model.TbOrgDeptExample;
import com.foya.noms.service.org.ORG002Service;
import com.foya.noms.service.org.RoleMenuFunDeptService;
import com.foya.noms.util.BaseJasonObject;
import com.foya.noms.web.controller.BaseController;

@Controller
@RequestMapping(value = "/auth")
public class RoleMenuFunDeptController extends BaseController {

	@Autowired
	private ORG002Service org002Service;
	
	@Autowired
	private RoleMenuFunDeptService roleMenuFunDeptService;
	
	/**
	 * 取得目前所有部門清單
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/roleMenuFunDept/getAllDept")
	@ResponseBody
	public BaseJasonObject<TbOrgDept> getAllDept(HttpServletRequest request) {
		List<TbOrgDept> rows = org002Service.getByCondition(new TbOrgDeptExample());
		return new BaseJasonObject<>(rows, AJAX_SUCCESS, AJAX_EMPTY);
	}
	
	/**
	 * 取得已使用的部門限制清單 
	 * @param roleId
	 * @param menuId
	 * @return
	 */
	@RequestMapping(value = "/roleMenuFunDept/getUsedDepts")
	public @ResponseBody BaseJasonObject<TbAuthRoleMenuFunDept> getUsedDepts(@RequestParam(value="roleId") String roleId, @RequestParam(value="menuId") String menuId) {
		
		List<TbAuthRoleMenuFunDept> rows = roleMenuFunDeptService.getUsedDeptByRoleMenu(roleId, menuId);
		return new BaseJasonObject<>(rows, AJAX_SUCCESS, AJAX_EMPTY);
	}
}
