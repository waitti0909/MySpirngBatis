package com.foya.noms.service.common;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.noms.GenericTest;
import com.foya.noms.dto.common.WorkflowVersionDTO;
import com.foya.noms.service.workflow.WorkflowVersionService;

public class WorkflowVersionServiceTest extends GenericTest {

	@Autowired
	WorkflowVersionService service;

	@Test
	public void findWorkFlowVersion() {
		List<WorkflowVersionDTO> workflowVersionDTOs = service.findWorkflowVersion();
		Assert.assertNotNull(workflowVersionDTOs);
		Assert.assertTrue(workflowVersionDTOs.size() > 0);
	}

	@Test
	public void findWorkFlowVersionByProcessTypes() {
		List<String> processTypes = new ArrayList<>();
		processTypes.add("SearchSiteCompletionSH");
		processTypes.add("SearchSiteApplySH");
		processTypes.add("派工單");
		processTypes.add("TestUse");

		List<WorkflowVersionDTO> workflowVersionDTOs = service
				.findWorkflowVersionByProcessTypes(processTypes);
		Assert.assertNotNull(workflowVersionDTOs);
		Assert.assertTrue(workflowVersionDTOs.size() > 0);
	}

	@Test
	public void findAvailableWorkFlowVersion() {
		List<WorkflowVersionDTO> workflowVersionDTOs = service.findActiveWorkflowVersion();
		Assert.assertNotNull(workflowVersionDTOs);
		Assert.assertTrue(workflowVersionDTOs.size() > 0);
	}

	@Test
	public void findWorkFlowVersionByProcessType() {
		List<WorkflowVersionDTO> workflowVersionDTOs = service
				.findWorkflowVersionByProcessType("SearchSiteApplySH");
		Assert.assertNotNull(workflowVersionDTOs);
		Assert.assertTrue(workflowVersionDTOs.size() > 0);
	}

	@Test
	public void findAvailableWorkFlowVersionByProcessType() {
		List<WorkflowVersionDTO> workflowVersionDTOs = service
				.findActiveWorkflowVersionByProcessType("SearchSiteApplySH");
		Assert.assertNotNull(workflowVersionDTOs);
		Assert.assertTrue(workflowVersionDTOs.size() > 0);
	}
}
