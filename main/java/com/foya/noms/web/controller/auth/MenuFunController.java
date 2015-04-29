package com.foya.noms.web.controller.auth;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foya.noms.dto.auth.MenuFunDTO;
import com.foya.noms.service.auth.MenuFunService;
import com.foya.noms.service.auth.MenuService;
import com.foya.noms.util.BaseJasonObject;
import com.foya.noms.web.controller.BaseController;

@Controller
public class MenuFunController extends BaseController {

	@Autowired
	private MenuFunService menuFunService;
	
	@Autowired
	private MenuService menuService;
	
	@RequestMapping(value = "/auth/menuFun/getFunctions")
	@ResponseBody
	public BaseJasonObject<MenuFunDTO> getMenuFunByMenuId(@RequestParam(value="menuId") BigDecimal menuId) {
		String msg = "";
		List<MenuFunDTO> rows = new ArrayList<MenuFunDTO>();
		if(StringUtils.equals("1", menuService.selectByPrimaryKey(menuId).getIS_FODR())) {
			msg = "isFolder";
		} else {
			rows = menuFunService.selectByMenuID(menuId);
		}
		
		BaseJasonObject<MenuFunDTO> jsonObj = new BaseJasonObject<>(rows, AJAX_SUCCESS, AJAX_EMPTY);
		jsonObj.setMsg(msg);
		return jsonObj;
	}
}
