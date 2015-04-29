package com.foya.dao.mybatis.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.foya.noms.dto.common.WorkflowVersionDTO;

public interface UTbWorkflowVersionMapper {
	public List<WorkflowVersionDTO> findWorkflowVersionDto();

	public List<WorkflowVersionDTO> findWorkflowVersionDtoByProcessTypes(
			@Param("processTypes") List<String> processTypes);

	public List<WorkflowVersionDTO> findActiveWorkflowVersionDtoByProcessTypesAndTime(
			@Param("processTypes") List<String> processTypes, @Param("startTime") Date startTime,
			@Param("endTime") Date endTime);
}