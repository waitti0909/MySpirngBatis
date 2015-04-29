package com.foya.noms.security.web;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.foya.noms.dto.auth.RoleMenuFunDTO;
import com.foya.noms.service.auth.RoleMenuFunService;
import com.foya.noms.web.controller.BaseController;

@Controller
@RequestMapping(value = "/auth")
public class FunctionButtonController extends BaseController {
	
	@Autowired
	private RoleMenuFunService roleMenuFunService;
	
	@RequestMapping(value = "/role/menu/func/{currentMenuId}")
	public String getFunctions(HttpServletRequest request, @PathVariable Integer currentMenuId) {
		
		List<RoleMenuFunDTO> list = roleMenuFunService.getFuncitonByPsnAndMenuId(getLoginUser().getUserId(), currentMenuId);
		request.setAttribute("btnList", list);
		return "buttons";
	}
}