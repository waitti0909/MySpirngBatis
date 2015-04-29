package com.foya.noms.web.controller.auth;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foya.dao.mybatis.model.TbAuthMenu;
import com.foya.dao.mybatis.model.TbAuthMenuExample;
import com.foya.dao.mybatis.model.TbSysLookupExample;
import com.foya.exception.NomsException;
import com.foya.noms.dto.auth.MenuDTO;
import com.foya.noms.dto.auth.MenuFunDTO;
import com.foya.noms.service.auth.MenuService;
import com.foya.noms.util.BaseJasonObject;
import com.foya.noms.util.JqGridData;
import com.foya.noms.web.controller.BaseController;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

/**
 * @author arda
 * 
 */

@Controller
@RequestMapping(value = "/auth")
public class MenuController extends BaseController{

	@Autowired
	private MenuService menuService;
	
	/**
	 * 初始頁面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/systemMenu/")
	public String systemMenuInit(HttpServletRequest request,
			Map<String, Object> model) {
		List<TbAuthMenu> menus =menuService.selectByExample(new TbAuthMenuExample());
		model.put("allMenus",menus);
		return "ajaxPage/auth/menu";
	}
	/**重新產生新的下拉式選單
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/systemMenu/selectOption")
	@ResponseBody
	public Map<BigDecimal,String> selectOption(HttpServletRequest request) {
		List<TbAuthMenu> menus =menuService.selectByExample(new TbAuthMenuExample());
		Map<BigDecimal,String> menuObj = new HashMap<BigDecimal,String>();
		for(TbAuthMenu menu : menus){
			menuObj.put(menu.getMENU_ID(), menu.getMENU_NAME());
		}
		return menuObj;
	}
	/**
	 * 新增頁面
	 */
	@RequestMapping(value = "/systemMenu/add")
	public String systemMenuAdd(HttpServletRequest request,
			Map<String, Object> model) {
		model.put("lookupButtons", menuService.selectAllLookupButtons(new TbSysLookupExample()));
		
		model.put("allMenus", menuService.selectIsFolderMenu());
		return "ajaxPage/auth/menuA";
	}
//	@RequestMapping(value = "/systemMenu/add")
//	public String testSystemMenuAdd(HttpServletRequest request,
//			Map<String, Object> model) {
//		model.put("lookupButtons", menuService.selectByExample(new TbSysLookupExample()));
//		model.put("allMenus", menuService.selectByExample(new TbAuthMenuExample()));
//		return "ajaxPage/auth/testSystemMenu_add";
//	}

	/**
	 * 將資料寫入後導向成功頁面
	 */
	@RequestMapping(value = "/systemMenu/addSuccess")
	public String systemMenuAddSuccess(TbAuthMenu menu,@RequestParam("lookupButtons")String[] lookupButtons
			,HttpServletRequest request,Map<String, Object> model) {
		if ("".equals(menu.getPARENT_ID()) ) {
			menu.setPARENT_ID(null);
		}
		if ("".equals(menu.getPGM_PATH()) ) {
			menu.setPGM_PATH(null);
		}
		menuService.createMenu(menu, lookupButtons, getLoginUser().getUsername());
		return "ajaxPage/auth/menu";
	}
//	@RequestMapping(value = "/systemMenu/addSuccess")
//	public String testSystemMenuAddSuccess(TbAuthMenu menu,@RequestParam("lookupButtons")String[] lookupButtons,HttpServletRequest request) {
//		if ("null".equals(menu.getPARENT_ID()) ) {
//			menu.setPARENT_ID(null);
//		}
//		if ("".equals(menu.getPGM_PATH()) ) {
//			menu.setPGM_PATH(null);
//		}
////		menuService.createMenu(menu, lookupButtons, String.valueOf(getLoginUser().getUserId()));
//		return "ajaxPage/auth/systemMenu_add";
//	}
	/**
	 * 查詢頁面
	 */
	@RequestMapping(value = "/systemMenu/select_list")
	public @ResponseBody JqGridData<MenuDTO> selectMenuList(@RequestParam("selectID") String id) {
		List<MenuDTO> menuList;
		try{
			menuList = menuService.selectDTOByPrimaryKeyGrid(BigDecimal.valueOf(Integer.parseInt(id)));
		}catch(NumberFormatException ex){	
			menuList = menuService.selectDTOByPrimaryKeyGrid(new BigDecimal(-1));
		}
		PageList<MenuDTO> page= (PageList<MenuDTO>) menuList;		
		return new JqGridData<>(page.getPaginator().getTotalCount(), menuList);
	}
	/**
	 * 修改頁面
	 */
	@RequestMapping(value = "/systemMenu/updateForm")
	public String systemMenuUpdateForm(@RequestParam("showType") String showType,@RequestParam("menuID") String id, HttpServletRequest request,
			Map<String, Object> model) {
		List<MenuFunDTO> menuFuns = menuService.selectMenuFunByMenuID(BigDecimal.valueOf(Integer.valueOf(id)));
		String funCode ="";
		for(MenuFunDTO menuFun : menuFuns){
			funCode += menuFun.getFUN_CODE() +",";
		}
		model.put("showType", showType);
		model.put("funCode", funCode);
		model.put("lookupButtons", menuService.selectAllLookupButtons(new TbSysLookupExample()));
		model.put("allMenus", menuService.selectIsFolderMenu());
		model.put("menu", menuService.selectByPrimaryKey(BigDecimal.valueOf(Integer.parseInt(id))));
		return "ajaxPage/auth/menuE";
	}
	
	
	/**
	 * 將資料修改後導向成功頁面
	 */
	@RequestMapping(value = "/systemMenu/updateSuccess")
	@ResponseBody
	public BaseJasonObject<String> systemMenuUpdateSuccess(TbAuthMenu menu,@RequestParam("lookupButtons")String[] lookupButtons,
									HttpServletRequest request) {
		BaseJasonObject<String> jsonObj = new BaseJasonObject<>();
		try{
			jsonObj.setSuccess(menuService.updateMenu(menu, lookupButtons,getLoginUser().getUsername()));
		}catch(NomsException ex){
			jsonObj.setSuccess(Boolean.FALSE);
			jsonObj.setMsg(ex.getMessage());
			log.error(ExceptionUtils.getFullStackTrace(ex));
		}
		return jsonObj;
	}
	/**
	 * 刪除頁面
	 */
	@RequestMapping(value = "/systemMenu/delete")
	@ResponseBody
	public BaseJasonObject<String> systemMenuDelete(@RequestParam("menuID")String menuId, HttpServletRequest request,
			Map<String, Object> model) {
		BaseJasonObject<String> jsonObj = new BaseJasonObject<>();
		String[] menuIds = menuId.split(",");
		try{
			jsonObj.setSuccess(menuService.deleteMenu(menuIds));
		}catch(Exception ex){
			jsonObj.setSuccess(Boolean.FALSE);
			jsonObj.setMsg(ex.getMessage());
			log.error(ExceptionUtils.getFullStackTrace(ex));
		}
		return jsonObj;
	}
	
}
