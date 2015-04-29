package com.foya.dao.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.foya.noms.dto.org.DeptPosDTO;

public interface UTbOrgDeptPosMapper {

	List<DeptPosDTO> getDeptPos();

	List<DeptPosDTO> getDeptPosByDeptPosId(String deptPosId);

	List<DeptPosDTO> getDeptPosByPosId(String posId);

	List<DeptPosDTO> getDeptPosByDeptId(String deptId);

	List<DeptPosDTO> getDeptPosByDeptIdAndPosId(@Param("deptId") String deptId,
			@Param("posId") String posId);

	List<DeptPosDTO> getDeptAllUnderDeptPosByDeptId(String deptId);
	
	/**
	 * 用deptId查詢該部門下所有員工
	 * @param deptId
	 * @return
	 */
	List<DeptPosDTO> selectMaintainUserByDeptId(String deptId);
}