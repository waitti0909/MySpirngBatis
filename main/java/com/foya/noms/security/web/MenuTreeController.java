package com.foya.noms.security.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foya.noms.security.model.Menu;
import com.foya.noms.security.model.Tree;
import com.foya.noms.security.service.MenuTreeService;
import com.foya.noms.web.controller.BaseController;

@Controller
public class MenuTreeController extends BaseController{

	// private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MenuTreeService menuService;

	/**
	 * menu頁面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/menu")
	public String menu(HttpServletRequest request) {
		if (request.getAttribute("trees") == null) {
			Authentication auth = SecurityContextHolder.getContext()
					.getAuthentication();
			String account = auth.getName();
			Map<String, Tree> trees = menuService.getTrees(account);
			request.setAttribute("trees", trees);
		}
		return "menuL";
	}

	@RequestMapping(value = "/ajaxChildMenu")
	public @ResponseBody List<Menu> ajaxChildMenu(@RequestParam("parentMenuId") Integer parentMenuId,HttpServletRequest request) {
		
		Integer psnId = getLoginUser().getUserId();
		List<Menu> menuList =  menuService.getChildMenuByParentIdRoldId(parentMenuId, psnId);
		return menuList;
	}

}
