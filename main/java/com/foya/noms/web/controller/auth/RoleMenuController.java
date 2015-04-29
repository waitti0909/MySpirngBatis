package com.foya.noms.web.controller.auth;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foya.dao.mybatis.model.TbAuthRoleMenu;
import com.foya.dao.mybatis.model.TbAuthRoleMenuKey;
import com.foya.noms.dto.JsTreeDTO;
import com.foya.noms.dto.auth.RoleMenuFunDepDTO;
import com.foya.noms.resources.AppConstants;
import com.foya.noms.security.service.MenuTreeService;
import com.foya.noms.service.auth.RoleMastService;
import com.foya.noms.service.auth.RoleMenuService;
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
public class RoleMenuController extends BaseController {

	@Autowired
	private MenuTreeService menuService;

	@Autowired
	private RoleMastService roleService;

	@Autowired
	private RoleMenuService roleMenuService;

	/**
	 * 初始頁面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/roleMenu/")
	public String roleMenuInit(HttpServletRequest request, Map<String, Object> model) {

		model.put("allRoles", roleService.getAll());
		model.put("allMenus", menuService.getNonFolderMenu());
		return "ajaxPage/auth/roleMenu";
	}

	/**
	 * 根據該目錄id 尋找角色所能存取的 Menu 清單
	 * 
	 * @param parentMenuId
	 *            目錄id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/roleMenu/searchResult")
	public @ResponseBody JqGridData<RoleMenuFunDepDTO> searchRoleMenuDTO(@RequestParam(value = "roleId", required = false) Integer roleId,
			@RequestParam(value = "menuId", required = false) Integer menuId, @RequestParam("searchBy") String searchBy,
			HttpServletRequest request) {

		List<RoleMenuFunDepDTO> rsList = null;

		if ("byRole".equals(searchBy)) {
			rsList = roleMenuService.selectRoleMenuFunDepDTOByRoleId(roleId);
		} else {
			rsList = roleMenuService.selectRoleMenuFunDepDTOByMenuId(menuId);
		}

		PageList<RoleMenuFunDepDTO> page = (PageList<RoleMenuFunDepDTO>) rsList;

		return new JqGridData<>(page.getPaginator().getTotalCount(), rsList);
	}
	
	/**
	 * 存檔
	 * @param searchBy
	 * @param roleId
	 * @param menuId
	 * @param roleList
	 * @param menuList
	 * @return
	 */
	@RequestMapping(value = "/roleMenu/save")
	public @ResponseBody String roleMenuAddSave(@RequestParam(value="roleSelected") BigDecimal roleId, @RequestParam(value="checkedMenuIds") String[] menuList) {

		try {
			List<TbAuthRoleMenu> roleMenuList = new ArrayList<>();
			TbAuthRoleMenu roleMenu;
			String currentUserName = getLoginUser().getUsername();
			Date now = new Date();

			for (String m : menuList) {
				if (!StringUtils.equals("#", m)) {
					log.debug(roleId + m);
					roleMenu = new TbAuthRoleMenu();
					roleMenu.setROLE_ID(roleId);
					roleMenu.setMENU_ID(new BigDecimal(m));
					roleMenu.setMD_USER(currentUserName);
					roleMenu.setMD_TIME(now);
					roleMenuList.add(roleMenu);
				}

			}
			if (!roleMenuList.isEmpty()) {			
				roleMenuService.saveRoleMenu(roleMenuList);
			}
			return AJAX_SUCCESS;
		} catch (RuntimeException e) {
			
			e.printStackTrace();
			return AJAX_FAILED;
		}
		
	}
	
	/**
	 * 刪除
	 * @param roleId
	 * @param menuId
	 * @retur
	 */
	@RequestMapping(value = "/roleMenu/delete")
	public @ResponseBody String delete(@RequestParam BigDecimal[] roleId, @RequestParam BigDecimal[] menuId) {

		log.debug("roleId.size=" + roleId.length);
		log.debug("menuId.size=" + menuId.length);
		List<TbAuthRoleMenuKey> keys = new ArrayList<>();

		for (int i = 0; i < menuId.length; i++) {
			TbAuthRoleMenuKey key = new TbAuthRoleMenuKey();
			key.setROLE_ID(roleId[i]);
			key.setMENU_ID(menuId[i]);
			keys.add(key);
		}

		roleMenuService.delete(keys);

		return AppConstants.DELETE + AppConstants.SUCCESS;
	}
	
	/**
	 * 依roleId找MenuTree
	 * @param roleId
	 * @return
	 */ 
	@RequestMapping(value = "/roleMenu/getTreeByRole")
	public @ResponseBody BaseJasonObject<JsTreeDTO> selectMenuTreeByRole(@RequestParam(value="roleId") Integer roleId) {
		List<JsTreeDTO> rows = null;
		if (roleId == null) {
			rows = menuService.getAllMenuTree();
		} else {
			rows = menuService.getAllMenuTreeByRole(roleId);
		}
		
		return new BaseJasonObject<>(rows, AJAX_SUCCESS, AJAX_EMPTY);
	}

}
