package com.foya.noms.workflow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kie.api.runtime.process.ProcessInstance;

import com.foya.workflow.exception.WorkflowException;
import com.foya.workflow.model.TodoInfo;
import com.foya.workflow.rest.RestEngineFactory;
import com.foya.workflow.rest.WorkflowRestFacade;

/**
 * Task status : Created, Ready, Reserved, InProgress, Suspended, Completed, Failed, Error, Exited,
 * Obsolete;
 * 
 * @author Eric
 * 
 */
public class CheckinTest {
	private String user = Constant.TEST_ACCOUNT;
	private String password = Constant.TEST_PASSWORD;

	@Before
	public void testStartProcess() {
		try {
			WorkflowRestFacade engine = RestEngineFactory.createRemoteEngine(
					Constant.WORKFLOW_REST_HOST, Constant.DEPLOYMENT_ID, user, password);
			Map<String, Object> flowMap = new HashMap<String, Object>();
			flowMap.put("emp", "user");
			flowMap.put("group", "PM");
			ProcessInstance processInstance = engine.startProcess(Constant.PROCESS_ID, flowMap);
			Assert.assertNotNull(processInstance);
		} catch (WorkflowException e) {
			Assert.fail(e.getLocalizedMessage());
		}

	}

	@Test
	public void testCheckout() {
		try {
			Thread.sleep(2000);
			WorkflowRestFacade engine = RestEngineFactory.createRemoteEngine(
					Constant.WORKFLOW_REST_HOST, Constant.DEPLOYMENT_ID, user, password);
			Assert.assertNotNull(engine);
			List<TodoInfo> todos = engine.findTodoList();
			Assert.assertNotNull(todos);
			for (TodoInfo info : todos) {
				Assert.assertNotNull(engine.checkOut(info.getTaskId(), user));
			}
		} catch (WorkflowException | InterruptedException e) {
			Assert.fail(e.getLocalizedMessage());
		}

	}

	@After 
	public void testCheckin() {
		try {
			WorkflowRestFacade engine = RestEngineFactory.createRemoteEngine(
					Constant.WORKFLOW_REST_HOST, Constant.DEPLOYMENT_ID, user, password);
			Map<String, Object> flowMap = new HashMap<String, Object>();
			flowMap.put("emp", "user");
			flowMap.put("group", "PM");
			List<TodoInfo> todos = engine.findTodoList();
			Assert.assertNotNull(todos);
			for (TodoInfo info : todos) {
				Assert.assertTrue(engine.checkIn(info.getTaskId(), user, flowMap));
			}
		} catch (WorkflowException e) {
			Assert.fail(e.getLocalizedMessage());
		}

	}
}
