package com.foya.dao.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.foya.noms.dto.auth.RoleMenuFunDepDTO;

/**
 * The Interface UTbAuthRoleMenuMapper.
 */
public interface UTbAuthRoleMenuMapper {
	
	 /**
 	 * Select role menu by role id.
 	 *
 	 * @param roleId the role id
 	 * @return the list
 	 */
 	List<RoleMenuFunDepDTO> selectRoleMenuByRoleId(@Param("roleId") Integer roleId);
	 
 	/**
 	 * Select role menu by menu id.
 	 *
 	 * @param menuId the menu id
 	 * @return the list
 	 */
 	List<RoleMenuFunDepDTO> selectRoleMenuByMenuId(@Param("menuId") Integer menuId);
 	
 	/**
	  * Search non owned menu by role id.
	  *
	  * @param roleId the role id
	  * @return the list
	  */
	 List<RoleMenuFunDepDTO> searchNonOwnedMenuByRoleId(@Param("roleId") Integer roleId);
 	
 	
	 /**
 	 * Search non owned role by menu id.
 	 *
 	 * @param menuId the menu id
 	 * @return the list
 	 */
 	List<RoleMenuFunDepDTO> searchNonOwnedRoleByMenuId(@Param("menuId") Integer menuId);
 	
 	/**
	  * Search owned menu by role id.
	  *
	  * @param roleId the role id
	  * @return the list
	  */
	 List<RoleMenuFunDepDTO> findSelectedMenuByRoleId(@Param("roleId") Integer roleId);
 	
}