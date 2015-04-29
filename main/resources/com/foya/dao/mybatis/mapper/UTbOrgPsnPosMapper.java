package com.foya.dao.mybatis.mapper;

import java.util.List;

import com.foya.noms.dto.org.PsnPosDTO;


public interface UTbOrgPsnPosMapper {
	/**
	 * 用deptPosId查詢Personnel
	 * @param deptPosId
	 * @return
	 */
	public List<PsnPosDTO> getPsnPosDTOByDeptPosId(String deptPosId);
}
