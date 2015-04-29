package com.foya.noms.workflow;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kie.api.runtime.process.ProcessInstance;

import com.foya.workflow.exception.WorkflowException;
import com.foya.workflow.rest.RestEngineFactory;
import com.foya.workflow.rest.WorkflowRestFacade;

public class ProcessTest {
	private String user = Constant.TEST_ACCOUNT;
	private String password = Constant.TEST_PASSWORD;
	private Long processInstanceId = null;

	@Before
	public void testStartProcess() {
		try {
			WorkflowRestFacade engine = RestEngineFactory.createRemoteEngine(
					Constant.WORKFLOW_REST_HOST, Constant.DEPLOYMENT_ID, user, password);
			Map<String, Object> flowMap = new HashMap<String, Object>();
			flowMap.put("emp", "man");
			flowMap.put("group", "PM");
			ProcessInstance processInstance = engine.startProcess(Constant.PROCESS_ID, flowMap);
			Assert.assertNotNull(processInstance);
			processInstanceId = processInstance.getId();
		} catch (WorkflowException e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test
	public void testAbortProcess() {
		try {
			Thread.sleep(1000);
			WorkflowRestFacade engine = RestEngineFactory.createRemoteEngine(
					Constant.WORKFLOW_REST_HOST, Constant.DEPLOYMENT_ID, user, password);
			Assert.assertTrue(engine.abortProcessInstance(processInstanceId));
		} catch (WorkflowException | InterruptedException e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}
}
