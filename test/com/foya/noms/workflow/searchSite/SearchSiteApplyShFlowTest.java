package com.foya.noms.workflow.searchSite;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.kie.services.client.api.command.exception.RemoteCommunicationException;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.noms.GenericTest;
import com.foya.noms.dto.auth.UserDTO;
import com.foya.noms.dto.workflow.WorkflowTodoDTO;
import com.foya.noms.dto.workflow.WorkflowWebDTO;
import com.foya.noms.resources.AppConstants;
import com.foya.noms.service.common.EmailTemplateService;
import com.foya.noms.service.org.OrgWorkflowService;
import com.foya.noms.service.profile.ProfileService;
import com.foya.noms.service.workflow.WorkflowVersionService;
import com.foya.noms.workflow.WorkflowWebActionTest;
import com.foya.workflow.exception.WorkflowException;

public class SearchSiteApplyShFlowTest extends GenericTest {
	@Autowired
	WorkflowVersionService workflowVersionService;

	@Autowired
	OrgWorkflowService orgWorkflowService;

	@Autowired
	ProfileService profileService;

	@Autowired
	private EmailTemplateService emailTemplateService;

	final String processType = "SearchSiteApplySH";
	final String applyNo = "201411-002JunitTest";
	List<WorkflowWebDTO> applyWorkflowWebDtos;
	UserDTO applyUser = new UserDTO();
	UserDTO[] taskUser = new UserDTO[2];
	UserDTO[] notify = new UserDTO[2];
	UserDTO[] countersign = new UserDTO[2];

	@Ignore
	@Before
	public void makeTestData() {
		try {
			Properties systemConfig = new Properties();
			systemConfig.load(Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("system.properties"));
			AppConstants.NOMS_HOST = String.valueOf(systemConfig.getProperty("nomsHost"));
			AppConstants.WORKFLOW_REST_HOST = String.valueOf(systemConfig
					.getProperty("workflow.restHost"));
		} catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}
		applyUser.setUsername("P14075175");
		applyUser.setChName("P14075175_Chname");
		applyUser.setDeptId("480100");
		applyUser.setDeptName("480100_Chname");
		applyUser.setPassword("1");

		taskUser[0] = new UserDTO();
		taskUser[0].setUsername("P02072336");
		taskUser[0].setChName("P02072336_Chname");
		taskUser[0].setDeptId("480100");
		taskUser[0].setDeptName("480100_Chname");
		taskUser[0].setPassword("1");

		taskUser[1] = new UserDTO();
		taskUser[1].setUsername("P02072336");
		taskUser[1].setChName("P02072336_Chname");
		taskUser[1].setDeptId("480100");
		taskUser[1].setDeptName("480100_Chname");
		taskUser[1].setPassword("1");

		countersign[0] = new UserDTO();
		countersign[0].setUsername("user");
		countersign[0].setChName("user");
		countersign[0].setPassword("user");

		countersign[1] = new UserDTO();
		countersign[1].setUsername("man");
		countersign[1].setChName("man");
		countersign[1].setPassword("man");

		notify[0] = new UserDTO();
		notify[0].setUsername("user");
		notify[0].setChName("user");
		notify[0].setPassword("user");

		notify[1] = new UserDTO();
		notify[1].setUsername("man");
		notify[1].setChName("man");
		notify[1].setPassword("man");

		applyWorkflowWebDtos = new ArrayList<>();
		Map<String, Object> extraData = new HashMap<>();
		extraData.put("workType",
				"junitTestTypetttttttttttttttttttttttttttttttttttttttttttttttttttt"
						+ "tttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt"
						+ "長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試"
						+ "長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試"
						+ "長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試"
						+ "長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試"
						+ "長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試"
						+ "長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試"
						+ "長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試"
						+ "長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試"
						+ "長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試"
						+ "長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試"
						+ "長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試"
						+ "長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試"
						+ "長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試長度測試");
		WorkflowWebDTO applyWebDto = new WorkflowWebDTO();
		applyWebDto.setProcessType(processType);
		applyWebDto.setApplyNo(applyNo);
		applyWebDto.setApplyDescription("尋點申請Site Hunt事由  junit test");
		applyWebDto.setExtraData(extraData);
		applyWorkflowWebDtos.add(applyWebDto);

		WorkflowWebActionTest.abortProcessInstance(workflowVersionService, processType, applyNo);
	}

	@Ignore
	@Test
	public void testSearchSiteApplyShFlowApproval() {
		try {
			testApply();
			Thread.sleep(1500);
			testTask1Approval();
			Thread.sleep(1500);
			testTask2Approval();
			Thread.sleep(1500);
			testItApproval();
			Thread.sleep(1500);
			Assert.assertTrue(WorkflowWebActionTest.isProcessCompleted(workflowVersionService,
					applyUser, processType, applyNo));
		} catch (InterruptedException e) {
			Assert.fail(e.getLocalizedMessage());
		} finally {
			WorkflowWebActionTest
					.abortProcessInstance(workflowVersionService, processType, applyNo);
		}
	}

	@Ignore
	@Test
	public void testSearchSiteApplyShFlowReject1() {
		try {
			testApply();
			Thread.sleep(1500);
			testTask1Reject();
			Thread.sleep(1500);
			testItApproval();
			Thread.sleep(1500);
			Assert.assertTrue(WorkflowWebActionTest.isProcessCompleted(workflowVersionService,
					applyUser, processType, applyNo));
		} catch (InterruptedException e) {
			Assert.fail(e.getLocalizedMessage());
		} finally {
			WorkflowWebActionTest
					.abortProcessInstance(workflowVersionService, processType, applyNo);
		}
	}

	@Ignore
	@Test
	public void testSearchSiteApplyShFlowReject2() {
		try {
			testApply();
			Thread.sleep(1500);
			testTask1Approval();
			Thread.sleep(1500);
			testTask2Reject();
			Thread.sleep(1500);
			testItApproval();
			Thread.sleep(1500);
			Assert.assertTrue(WorkflowWebActionTest.isProcessCompleted(workflowVersionService,
					applyUser, processType, applyNo));
		} catch (InterruptedException e) {
			Assert.fail(e.getLocalizedMessage());
		} finally {
			WorkflowWebActionTest
					.abortProcessInstance(workflowVersionService, processType, applyNo);
		}
	}

	@Ignore
	@Test
	public void testSearchSiteApplyShFlowCountersign() {
		try {
			testApply();
			Thread.sleep(1500);

			testTask1Countersign();
			Thread.sleep(1500);
			testCountersign1Approval();
			Thread.sleep(1500);
			testCountersign2Approval();
			Thread.sleep(1500);

			testTask2Countersign();
			Thread.sleep(1500);
			testCountersign1Approval();
			Thread.sleep(1500);
			testCountersign2Approval();
			Thread.sleep(1500);
			testItApproval();
			Thread.sleep(1500);
			Assert.assertTrue(WorkflowWebActionTest.isProcessCompleted(workflowVersionService,
					applyUser, processType, applyNo));
		} catch (InterruptedException e) {
			Assert.fail(e.getLocalizedMessage());
		} finally {
			WorkflowWebActionTest
					.abortProcessInstance(workflowVersionService, processType, applyNo);
		}
	}

	@Ignore
	@Test
	public void testSearchSiteApplyShFlowCountersignAtTask1Countersign1Reject() {
		try {
			testApply();
			Thread.sleep(1500);

			testTask1Countersign();
			Thread.sleep(1500);
			testCountersign1Reject();
			Thread.sleep(1500);
			testItApproval();
			Thread.sleep(1500);
			Assert.assertTrue(WorkflowWebActionTest.isProcessCompleted(workflowVersionService,
					applyUser, processType, applyNo));
		} catch (InterruptedException e) {
			Assert.fail(e.getLocalizedMessage());
		} finally {
			WorkflowWebActionTest
					.abortProcessInstance(workflowVersionService, processType, applyNo);
		}
	}

	@Ignore
	@Test
	public void testSearchSiteApplyShFlowCountersignAtTask1Countersign2Reject() {
		try {
			testApply();
			Thread.sleep(1500);

			testTask1Countersign();
			Thread.sleep(1500);
			testCountersign1Approval();
			Thread.sleep(1500);
			testCountersign2Reject();
			Thread.sleep(1500);
			testItApproval();
			Thread.sleep(1500);
			Assert.assertTrue(WorkflowWebActionTest.isProcessCompleted(workflowVersionService,
					applyUser, processType, applyNo));
		} catch (InterruptedException e) {
			Assert.fail(e.getLocalizedMessage());
		} finally {
			WorkflowWebActionTest
					.abortProcessInstance(workflowVersionService, processType, applyNo);
		}
	}

	@Ignore
	@Test
	public void testSearchSiteApplyShFlowCountersignAtTask2Countersign1Reject() {
		try {
			testApply();
			Thread.sleep(1500);
			testTask1Approval();
			Thread.sleep(1500);

			testTask2Countersign();
			testCountersign1Reject();
			Thread.sleep(1500);
			testItApproval();
			Thread.sleep(1500);
			Assert.assertTrue(WorkflowWebActionTest.isProcessCompleted(workflowVersionService,
					applyUser, processType, applyNo));
		} catch (InterruptedException e) {
			Assert.fail(e.getLocalizedMessage());
		} finally {
			WorkflowWebActionTest
					.abortProcessInstance(workflowVersionService, processType, applyNo);
		}
	}

	@Ignore
	@Test
	public void testSearchSiteApplyShFlowCountersignAtTask2Countersign2Reject() {
		try {
			testApply();
			Thread.sleep(1500);
			testTask1Approval();
			Thread.sleep(1500);
			testTask2Countersign();
			Thread.sleep(1500);
			testCountersign1Approval();
			Thread.sleep(1500);
			testCountersign2Reject();
			Thread.sleep(1500);
			testItApproval();
			Thread.sleep(1500);
			Assert.assertTrue(WorkflowWebActionTest.isProcessCompleted(workflowVersionService,
					applyUser, processType, applyNo));
		} catch (InterruptedException e) {
			Assert.fail(e.getLocalizedMessage());
		} finally {
			WorkflowWebActionTest
					.abortProcessInstance(workflowVersionService, processType, applyNo);
		}
	}

	@Ignore
	@Test
	public void testSearchSiteApplyShFlowNotify() {
		try {
			testApply();
			Thread.sleep(1500);

			testTask1Countersign();
			Thread.sleep(1500);
			testNotify1Approval();
			Thread.sleep(1500);
			testNotify2Approval();
			Thread.sleep(1500);

			testTask2Countersign();
			Thread.sleep(1500);
			testNotify1Approval();
			Thread.sleep(1500);
			testNotify2Approval();
			Thread.sleep(1500);
			testItApproval();
			Thread.sleep(1500);
			Assert.assertTrue(WorkflowWebActionTest.isProcessCompleted(workflowVersionService,
					applyUser, processType, applyNo));
		} catch (InterruptedException e) {
			Assert.fail(e.getLocalizedMessage());
		} finally {
			WorkflowWebActionTest
					.abortProcessInstance(workflowVersionService, processType, applyNo);
		}
	}

	@Ignore
	@Test
	public void testSearchSiteApplyShFlowAbortProcess() {
		try {
			testApply();
			Thread.sleep(1500);
			abortProcess();
			Thread.sleep(1500);
			Assert.assertTrue(WorkflowWebActionTest.isProcessCompleted(workflowVersionService,
					applyUser, processType, applyNo));
		} catch (InterruptedException e) {
			Assert.fail(e.getLocalizedMessage());
		} finally {
			WorkflowWebActionTest
					.abortProcessInstance(workflowVersionService, processType, applyNo);
		}
	}

	public void testApply() {
		try {
			WorkflowWebActionTest.workflowWebAction.APPLY.execute(orgWorkflowService,
					workflowVersionService, emailTemplateService, applyUser, applyWorkflowWebDtos);
		} catch (WorkflowException e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	public void abortProcess() {
		try {
			verify(taskUser[0], "ABORT_PROCESS");
		} catch (WorkflowException e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	public void testTask1Approval() {
		try {
			verify(taskUser[0], "BATCH_APPROVAL");
		} catch (WorkflowException e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	public void testTask1Reject() {
		try {
			verify(taskUser[0], "BATCH_REJECT");
		} catch (WorkflowException e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	public void testTask1Countersign() {
		try {
			verify(taskUser[0], "COUNTERSIGN");
		} catch (WorkflowException e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	public void testTask1Notify() {
		try {
			verify(taskUser[0], "NOTIFY");
		} catch (WorkflowException e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	public void testTask2Approval() {
		try {
			verify(taskUser[1], "BATCH_APPROVAL");
		} catch (WorkflowException e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	public void testTask2Reject() {
		try {
			verify(taskUser[1], "BATCH_REJECT");
		} catch (WorkflowException e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	public void testTask2Countersign() {
		try {
			verify(taskUser[1], "COUNTERSIGN");
		} catch (WorkflowException e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	public void testTask2Notify() {
		try {
			verify(taskUser[1], "NOTIFY");
		} catch (WorkflowException e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	public void testCountersign1Approval() {
		try {
			verify(countersign[0], "APPROVAL");
		} catch (WorkflowException e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	public void testCountersign1Reject() {
		try {
			verify(countersign[0], "REJECT");
		} catch (WorkflowException e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	public void testCountersign2Approval() {
		try {
			verify(countersign[1], "APPROVAL");
		} catch (WorkflowException e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	public void testCountersign2Reject() {
		try {
			verify(countersign[1], "REJECT");
		} catch (WorkflowException e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	public void testNotify1Approval() {
		try {
			verify(notify[0], "APPROVAL");
		} catch (WorkflowException e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	public void testNotify2Approval() {
		try {
			verify(notify[1], "APPROVAL");
		} catch (WorkflowException e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	public void testItApproval() {
		try {
			UserDTO it = new UserDTO();
			it.setUsername("admin");
			it.setPassword("admin");
			verify(it, "APPROVAL");
		} catch (Exception e) {
			// nothing
		}
	}

	public void verify(UserDTO executor, String result) throws WorkflowException {
		List<WorkflowWebDTO> workflowWebDtos = getTaskWebDto(executor);
		WorkflowWebActionTest.workflowWebAction.valueOf(result).execute(orgWorkflowService,
				workflowVersionService, emailTemplateService, executor, workflowWebDtos);
	}

	public List<WorkflowWebDTO> getTaskWebDto(UserDTO executor) throws WorkflowException,
			RemoteCommunicationException {
		List<WorkflowTodoDTO> todoDtos = profileService.getOwnTodoList(executor);
		List<WorkflowWebDTO> taskWebDtos = new ArrayList<>(todoDtos.size());
		for (WorkflowTodoDTO todoDto : todoDtos) {
			String tmpApplyNo = todoDto.getApplyNo();
			String tmpProcessType = todoDto.getProcessType();
			if (StringUtils.equals(applyNo, tmpApplyNo)
					&& StringUtils.equals(processType, tmpProcessType)) {
				WorkflowWebDTO taskWebDto = new WorkflowWebDTO();
				taskWebDto.setTaskId(todoDto.getTaskId());
				taskWebDto.setApplyNo(todoDto.getApplyNo());
				taskWebDto.setProcessType(todoDto.getProcessType());
				taskWebDto.setComment("Junit test approval");
				taskWebDto.setTaskStartTime(new Date());
				String[] countersigns = new String[2];
				countersigns[0] = countersign[0].getUsername();
				countersigns[1] = countersign[1].getUsername();
				taskWebDto.setCountersigns(countersigns);
				String[] notifys = new String[2];
				notifys[0] = notify[0].getUsername();
				notifys[1] = notify[1].getUsername();
				taskWebDto.setNotifys(notifys);
				taskWebDtos.add(taskWebDto);
			}
		}
		return taskWebDtos;
	}

}
