package com.foya.dao.mybatis.mapper;

import java.util.List;
import java.util.Map;

import com.foya.dao.mybatis.model.TbAuthRoleMenuFunDept;
import com.foya.noms.dto.auth.RoleMenuFunDepDTO;


public interface UTbAuthRoleMenuFunDeptMapper {

	List<TbAuthRoleMenuFunDept> selectUsedDeptByRoleMenu(Map<String, String> map);
	
	
	List<RoleMenuFunDepDTO> getAccessDeptByUser(String psnId);
}