package com.foya.noms.dao.workflow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.UTbWorkflowVersionMapper;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.common.WorkflowVersionDTO;

@Repository
public class WorkflowVersionDao extends BaseDao {

	@Autowired
	private UTbWorkflowVersionMapper mapper;

	public List<WorkflowVersionDTO> findWorkflowVersionDto() {
		return mapper.findWorkflowVersionDto();
	}

	public List<WorkflowVersionDTO> findWorkflowVersionDtoByProcessTypes(
			Collection<String> processTypes) {
		return mapper.findWorkflowVersionDtoByProcessTypes(new ArrayList<String>(processTypes));
	}

	public List<WorkflowVersionDTO> findActiveWorkflowVersionDtoByProcessTypesAndTime(
			Collection<String> processTypes, Date startTime, Date endTime) {
		List<String> processTypeList = null;
		if (processTypes != null && !processTypes.isEmpty()) {
			processTypeList = new ArrayList<String>(processTypes);
		}
		return mapper.findActiveWorkflowVersionDtoByProcessTypesAndTime(processTypeList, startTime,
				endTime);
	}
}
