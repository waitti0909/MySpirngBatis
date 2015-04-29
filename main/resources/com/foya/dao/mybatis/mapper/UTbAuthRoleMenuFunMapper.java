package com.foya.dao.mybatis.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.foya.noms.dto.auth.RoleMenuFunDTO;
import com.foya.noms.dto.auth.RoleMenuFunDepDTO;

/**
 * The Interface UTbAuthRoleMenuFunMapper.
 */
public interface UTbAuthRoleMenuFunMapper {
	
	
	/**
	 * Find function by psn and menu id.
	 *
	 * @param condition the condition
	 * @return the list
	 */
	List<RoleMenuFunDTO> findFunctionByPsnAndMenuId(Map<String, Integer> condition);
	
	
	/**
	 * Find role menu fun by role id.
	 *
	 * @param roleId the role id
	 * @return the list
	 */
	List<RoleMenuFunDepDTO> findRoleMenuByRoleId(@Param("roleId") Integer roleId);

	
	/**
	 * Find role menu fun by menu id.
	 *
	 * @param menuId the menu id
	 * @return the list
	 */
	List<RoleMenuFunDepDTO> findRoleMenuByMenuId(@Param("menuId") Integer menuId);
	
	/**
	 * Find func dept by role and menu
	 *
	 * @param hashmap
	 * @return the list
	 */
	List<RoleMenuFunDepDTO> findFuncDeptByRoleAndMenu(HashMap<String, Integer> map);
	/**
	 * 用menuID找roleMenuFun對應menuFun的fun_code
	 * @param menuId
	 * @return
	 */
	List<RoleMenuFunDTO> selectRoleMenuFunByMenuId(Integer menuId);
}