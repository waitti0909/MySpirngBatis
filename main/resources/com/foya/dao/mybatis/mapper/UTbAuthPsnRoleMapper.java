package com.foya.dao.mybatis.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.foya.dao.mybatis.model.TbAuthPsnRole;

public interface UTbAuthPsnRoleMapper {
	/**
	 * 查詢所有使用者的角色
	 * @return
	 */
	List<TbAuthPsnRole> selectAllPsnRole();
	
	/**
	 * 根據角色ID查詢使用者
	 * @return
	 */
	List<TbAuthPsnRole> selectPsnByRole(@Param("roleId") BigDecimal roleId);

}
