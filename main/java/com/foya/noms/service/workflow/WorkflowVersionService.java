package com.foya.noms.service.workflow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foya.noms.dao.workflow.WorkflowVersionDao;
import com.foya.noms.dto.common.WorkflowVersionDTO;
import com.foya.noms.service.BaseService;
import com.foya.noms.util.DateUtils;

/**
 * The Class WorkflowVersionService.
 */
@Service
public class WorkflowVersionService extends BaseService {

	/** The work flow version dao. */
	@Autowired
	private WorkflowVersionDao workflowVersionDao;

	/**
	 * Find work flow version.
	 * 
	 * @return the list
	 */
	public List<WorkflowVersionDTO> findWorkflowVersion() {
		return workflowVersionDao.findWorkflowVersionDto();
	}

	/**
	 * Find work flow version.
	 * 
	 * @param ProcessTypes the process type list
	 * @return the list
	 */
	public List<WorkflowVersionDTO> findWorkflowVersionByProcessTypes(
			Collection<String> processTypes) {
		if (processTypes == null || processTypes.isEmpty()) {
			return new ArrayList<WorkflowVersionDTO>();
		}
		return workflowVersionDao.findWorkflowVersionDtoByProcessTypes(processTypes);
	}

	/**
	 * Find active work flow version.
	 * 
	 * @return the list
	 */
	public List<WorkflowVersionDTO> findActiveWorkflowVersion() {
		Date today = DateUtils.today();
		return workflowVersionDao.findActiveWorkflowVersionDtoByProcessTypesAndTime(null, today,
				today);
	}

	/**
	 * Find work flow version by process type.
	 * 
	 * @param ProcessType the process type
	 * @return the list
	 */
	public List<WorkflowVersionDTO> findWorkflowVersionByProcessType(String processType) {
		List<String> processTypes = new ArrayList<String>();
		processTypes.add(processType);
		return findWorkflowVersionByProcessTypes(processTypes);
	}

	/**
	 * Find active work flow version by process type.
	 * 
	 * @param ProcessType the process type
	 * @return the list
	 */
	public List<WorkflowVersionDTO> findActiveWorkflowVersionByProcessType(String processType) {
		List<String> processTypes = new ArrayList<>(1);
		processTypes.add(processType);
		return findActiveWorkflowVersionByProcessTypes(processTypes);
	}

	/**
	 * Find active work flow version by process type.
	 * 
	 * @param ProcessTypes the process type list
	 * @return the list
	 */
	public List<WorkflowVersionDTO> findActiveWorkflowVersionByProcessTypes(
			Collection<String> processTypes) {
		Date today = DateUtils.today();
		if (processTypes == null || processTypes.isEmpty()) {
			return new ArrayList<WorkflowVersionDTO>(0);
		}
		return workflowVersionDao.findActiveWorkflowVersionDtoByProcessTypesAndTime(processTypes,
				today, today);
	}
}
