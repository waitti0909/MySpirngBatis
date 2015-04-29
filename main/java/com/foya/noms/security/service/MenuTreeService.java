package com.foya.noms.security.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.noms.dao.auth.MenuDao;
import com.foya.noms.dao.auth.PersonnelDao;
import com.foya.noms.dto.JsTreeDTO;
import com.foya.noms.dto.JsTreeStateDTO;
import com.foya.noms.dto.auth.UserDTO;
import com.foya.noms.security.model.Menu;
import com.foya.noms.security.model.Tree;
import com.foya.noms.service.BaseService;

public class MenuTreeService extends BaseService {
	
	@Autowired
	private PersonnelDao personnelDao;
	
	@Autowired
	private MenuDao menuDao;	
	
	public Map<String, Tree> getTrees(String psnNo) {
		Map<String, Tree> trees = new TreeMap<String, Tree>();

		UserDTO user = personnelDao.getUserDtoByPsnNo(psnNo);
		List<Menu> menuList = menuDao.getRootMenuByPsnId(user.getUserId());
		
		for(Menu menu:menuList){
			Tree tree = new Tree(menu);
		
			trees.put(menu.getSort()+"_"+menu.getId(), tree);
		}
		
		return trees;
	}
	
	/**
	 * 根據使用者id獲取該角色可以訪問的第一層Menu
	 * @param roleId
	 * @return
	 */
	public List<Menu> getMenuByPsnId(Integer psnId) {
		return menuDao.getRootMenuByPsnId(psnId);
	}
	/**
	 * 根據角色id獲取該角色可以訪問的menu
	 * @param roleId
	 * @return
	 */
	public List<Menu> getMenuByRoleId(Integer... roleIds) {
		return menuDao.getRootMenuByRoleId(roleIds);
	}
	/**
	 * 根據使用者id獲取該menu底下清單menu
	 * @param psnId
	 * @param parentMenuId 
	 * @return
	 */
	public List<Menu> getChildMenuByParentIdRoldId(Integer parentMenuId, Integer psnId) {
		return menuDao.getChildMenuByParentIdRoldId(psnId,parentMenuId);
	}
	
	/**
	 * 取得全部menu
	 * */
	public List<Menu> getAllMenu(){
		return menuDao.getAllMenu();
	}
	
	/**
	 * 取得非Folder屬性 的所有 menu
	 * */
	public List<Menu> getNonFolderMenu(){
		return menuDao.getNonFolderMenu();
	}
	
	/**
	 * 取得所有Menu的階層樹(新增)
	 * @param roleId
	 * @return
	 */
	public List<JsTreeDTO> getAllMenuTree() {
		List<Menu> menus = menuDao.selectAllMenuTree();	
		return toBuildMenuTree(menus, null);
	}
	
	/**
	 * 取得所有Menu的階層樹(編輯)
	 * @param roleId
	 * @return
	 */
	public List<JsTreeDTO> getAllMenuTreeByRole(Integer roleId) {
		List<Menu> menus = menuDao.selectAllMenuTreeByRole(roleId);	
		return toBuildMenuTree(menus, null);
	}
	
	/**
	 * 取得角色已使用Menu的階層樹
	 * @param roleId
	 * @return
	 */
	public List<JsTreeDTO> getMenuTreeByRole(Integer roleId, Integer menuId) {
		List<Menu> menus = menuDao.selectMenuTreeByRole(roleId);	
		return toBuildMenuTree(menus, menuId);
	}
	
	/**
	 * 組合Menu的階層樹
	 * @param roleId
	 * @return
	 */
	private List<JsTreeDTO> toBuildMenuTree(List<Menu> menus, Integer defaultSelectedMenu) {
		List<JsTreeDTO> jsTreeDto = new ArrayList<JsTreeDTO>();
		
		if (CollectionUtils.isNotEmpty(menus)) {			
			for(Menu menu : menus) {
				JsTreeDTO dto = new JsTreeDTO();
				JsTreeStateDTO state = new JsTreeStateDTO();
				dto.setId(menu.getId());
				dto.setParent(StringUtils.isNotEmpty(menu.getParentId()) ? menu.getParentId() : "#");
				dto.setText(menu.getName());
				if (StringUtils.equals("0", menu.getIsFolder())) dto.setIcon("glyphicon glyphicon-leaf");
				
				if (defaultSelectedMenu != null) {
					state.setSelected(StringUtils.equals(defaultSelectedMenu.toString(), menu.getId()));
				} else {
					state.setSelected(StringUtils.equals("0", menu.getIsFolder()) && StringUtils.isNotEmpty(menu.getUsed()));
				}				
				dto.setState(state);
				jsTreeDto.add(dto);
			}
		}
		return jsTreeDto;
	}
	
	
}
