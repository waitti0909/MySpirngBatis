package com.foya.noms.workflow.pay;

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

public class PayLeaseLineFlowTest extends GenericTest {
	@Autowired
	WorkflowVersionService workflowVersionService;

	@Autowired
	OrgWorkflowService orgWorkflowService;

	@Autowired
	ProfileService profileService;

	@Autowired
	private EmailTemplateService emailTemplateService;

	final String processType = "PayLeaseLine";
	final String applyNo = "201503-004JunitTest";
	List<WorkflowWebDTO> applyWorkflowWebDtos;
	UserDTO applyUser = new UserDTO();
	UserDTO[] taskUser = new UserDTO[3];
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
		applyUser.setUsername("P07088190");
		applyUser.setChName("P07088190_Chname");
		applyUser.setDeptId("450300");
		applyUser.setDeptName("450300_Chname");
		applyUser.setPassword("1");

		// 申請單位-部主管
		taskUser[0] = new UserDTO();
		taskUser[0].setUsername("P14075225");
		taskUser[0].setChName("P14075225_Chname");
		taskUser[0].setDeptId("450200");
		taskUser[0].setDeptName("450200_Chname");
		taskUser[0].setPassword("1");

		// 申請單位-處長
		taskUser[1] = new UserDTO();
		taskUser[1].setUsername("P02115070");
		taskUser[1].setChName("P02115070_Chname");
		taskUser[1].setDeptId("450000");
		taskUser[1].setDeptName("450000_Chname");
		taskUser[1].setPassword("1");

		// 網路服務事業部-副總
		taskUser[2] = new UserDTO();
		taskUser[2].setUsername("PTS000068");
		taskUser[2].setChName("PTS000068_Chname");
		taskUser[2].setDeptId("400000");
		taskUser[2].setDeptName("400000_Chname");
		taskUser[2].setPassword("1");

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
		applyWebDto.setApplyDescription("租約簡易流程 junit test");
		applyWebDto.setExtraData(extraData);
		applyWorkflowWebDtos.add(applyWebDto);

		WorkflowWebActionTest.abortProcessInstance(workflowVersionService, processType, applyNo);
	}

	@Ignore
	@Test
	public void testPayLeaseLineFlowApproval() {
		try {
			testApply();
			Thread.sleep(1500);
			testTask1Approval();
			Thread.sleep(1500);
			testTask2Approval();
			Thread.sleep(1500);
			testTask3Approval();
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
	public void testPayLeaseLineFlowReject1() {
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
	public void testPayLeaseLineFlowReject2() {
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
	public void testPayLeaseLineFlowReject3() {
		try {
			testApply();
			Thread.sleep(1500);
			testTask1Approval();
			Thread.sleep(1500);
			testTask2Approval();
			Thread.sleep(1500);
			testTask3Reject();
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
	public void testPayLeaseLineFlowCountersign() {
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

			testTask3Countersign();
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
	public void testPayLeaseLineFlowCountersignAtTask1Countersign1Reject() {
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
	public void testPayLeaseLineFlowCountersignAtTask1Countersign2Reject() {
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
	public void testPayLeaseLineFlowCountersignAtTask2Countersign1Reject() {
		try {
			testApply();
			Thread.sleep(1500);
			testTask1Approval();
			Thread.sleep(1500);

			testTask2Countersign();
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
	public void testPayLeaseLineFlowCountersignAtTask2Countersign2Reject() {
		try {
			testApply();
			Thread.sleep(1500);
			testTask1Approval();

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
	public void testPayLeaseLineFlowCountersignAtTask3Countersign1Reject() {
		try {
			testApply();
			Thread.sleep(1500);
			testTask1Approval();
			Thread.sleep(1500);
			testTask2Approval();
			Thread.sleep(1500);

			testTask3Countersign();
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
	public void testPayLeaseLineFlowCountersignAtTask3Countersign2Reject() {
		try {
			testApply();
			Thread.sleep(1500);
			testTask1Approval();
			Thread.sleep(1500);
			testTask2Approval();
			Thread.sleep(1500);

			testTask3Countersign();
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
	public void testPayLeaseLineFlowNotify() {
		try {
			testApply();
			Thread.sleep(1500);

			testTask1Notify();
			Thread.sleep(1500);
			testNotify1Approval();
			Thread.sleep(1500);
			testNotify2Approval();
			Thread.sleep(1500);

			testTask2Notify();
			Thread.sleep(1500);
			testNotify1Approval();
			Thread.sleep(1500);
			testNotify2Approval();
			Thread.sleep(1500);

			testTask3Notify();
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
	public void testPayLeaseLineFlowAbortProcess() {
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
			verify(taskUser[0], "APPROVAL");
		} catch (WorkflowException e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	public void testTask1Reject() {
		try {
			verify(taskUser[0], "REJECT");
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
			verify(taskUser[1], "APPROVAL");
		} catch (WorkflowException e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	public void testTask2Reject() {
		try {
			verify(taskUser[1], "REJECT");
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

	public void testTask3Approval() {
		try {
			verify(taskUser[2], "APPROVAL");
		} catch (WorkflowException e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	public void testTask3Reject() {
		try {
			verify(taskUser[2], "REJECT");
		} catch (WorkflowException e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	public void testTask3Countersign() {
		try {
			verify(taskUser[2], "COUNTERSIGN");
		} catch (WorkflowException e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	public void testTask3Notify() {
		try {
			verify(taskUser[2], "NOTIFY");
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

	public List<WorkflowWebDTO> getTaskWebDto(UserDTO executor) throws WorkflowException {
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
