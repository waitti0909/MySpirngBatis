package com.foya.dao.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.foya.noms.dto.auth.RoleDTO;
import com.foya.noms.dto.auth.RoleMastDTO;

public interface UTbAuthRoleMastMapper {
	
	/**
	 * ByID查詢角色
	 * @return
	 */
	List<RoleMastDTO> searchRole(@Param("roleId") Integer roleId);
	
	/**
	 * ByID查詢角色
	 * @return
	 */
	List<RoleMastDTO> searchRoleWithPsn(@Param("roleId") Integer roleId);
	
	/**
	 * 根據用戶id獲取角色集合
	 * @param userId
	 * @return
	 */
	List<RoleDTO> selectByUserId(Integer userId);
	
	/**
	 * 查詢所有角色
	 * @return
	 */
	List<RoleDTO> selectAll();
	
}