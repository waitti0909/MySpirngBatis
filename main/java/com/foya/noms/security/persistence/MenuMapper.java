package com.foya.noms.security.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.foya.noms.security.model.Menu;

/**
 * The Interface MenuMapper.
 */
public interface MenuMapper{

	/**
	 * 根據使用者id 取得最上層menu.
	 *
	 * @param psnId the psn id
	 * @return the root menu by psn id
	 */
	List<Menu> getRootMenuByPsnId(Integer psnId);
	
	/**
	 * 根據角色id 取得最上層menu.
	 *
	 * @param roleId the role id
	 * @return the root menu by role id
	 */
	List<Menu> getRootMenuByRoleId(Integer... roleId);
	
	/**
	 * 根據使用者id 取得menu底下的清單.
	 *
	 * @param psnId the psn id
	 * @param parentMenuId the parent menu id
	 * @return the child menu by parent id rold id
	 */
	List<Menu> getChildMenuByParentIdRoldId(@Param("psnId")Integer psnId,@Param("parentMenuId")Integer parentMenuId);
	
	/**
	 * 取得全部menu.
	 *
	 * @return the all menu
	 */
	List<Menu> getAllMenu();

	
	/**
	 * 取得非Folder屬性 的所有 menu.
	 *
	 * @return the non folder menu
	 */
	List<Menu> getNonFolderMenu();
	
	
	/**
	 * Select Tree menu tree by role
	 *
	 * @return the list
	 */
	List<Menu> selectAllMenuTree();
	
	/**
	 * Select Tree menu tree by role
	 *
	 * @return the list
	 */
	List<Menu> selectMenuTreeByRole(@Param("roleId") Integer roleId);
	
	/**
	 * Select all Tree menu tree by role
	 *
	 * @param psnId the psn id
	 * @return the list
	 */
	List<Menu> selectAllMenuTreeByRole(@Param("roleId") Integer roleId);
}
