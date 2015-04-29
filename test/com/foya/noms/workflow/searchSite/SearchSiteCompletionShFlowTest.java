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

public class SearchSiteCompletionShFlowTest extends GenericTest {
	@Autowired
	WorkflowVersionService workflowVersionService;

	@Autowired
	OrgWorkflowService orgWorkflowService;

	@Autowired
	ProfileService profileService;

	@Autowired
	private EmailTemplateService emailTemplateService;

	final String processType = "SearchSiteCompletionSH";
	final String applyNo = "201411-006JunitTest";
	List<WorkflowWebDTO> applyWorkflowWebDtos;
	UserDTO applyUser = new UserDTO();
	UserDTO[] taskUser = new UserDTO[7];
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
		applyUser.setUsername("P03095073");
		applyUser.setChName("P03095073_Chname");
		applyUser.setDeptId("450200");
		applyUser.setDeptName("450200_Chname");
		applyUser.setPassword("1");

		taskUser[0] = new UserDTO();
		taskUser[0].setUsername("P05015620");
		taskUser[0].setChName("P05015620_Chname");
		taskUser[0].setDeptId("450200");
		taskUser[0].setDeptName("450200_Chname");
		taskUser[0].setPassword("1");

		taskUser[1] = new UserDTO();
		taskUser[1].setUsername("P14075188");
		taskUser[1].setChName("P14075188_Chname");
		taskUser[1].setDeptId("450400");
		taskUser[1].setDeptName("450400_Chname");
		taskUser[1].setPassword("1");

		taskUser[2] = new UserDTO();
		taskUser[2].setUsername("P14075225");
		taskUser[2].setChName("P14075225_Chname");
		taskUser[2].setDeptId("450400");
		taskUser[2].setDeptName("450400_Chname");
		taskUser[2].setPassword("1");

		taskUser[3] = new UserDTO();
		taskUser[3].setUsername("P02115070");
		taskUser[3].setChName("P02115070_Chname");
		taskUser[3].setDeptId("450000");
		taskUser[3].setDeptName("450000_Chname");
		taskUser[3].setPassword("1");

		taskUser[4] = new UserDTO();
		taskUser[4].setUsername("P02062326");
		taskUser[4].setChName("P02062326_Chname");
		taskUser[4].setDeptId("410300");
		taskUser[4].setDeptName("410300_Chname");
		taskUser[4].setPassword("1");

		taskUser[5] = new UserDTO();
		taskUser[5].setUsername("P02122403");
		taskUser[5].setChName("P02122403_Chname");
		taskUser[5].setDeptId("410300");
		taskUser[5].setDeptName("410300_Chname");
		taskUser[5].setPassword("1");

		taskUser[6] = new UserDTO();
		taskUser[6].setUsername("P02122403");
		taskUser[6].setChName("P02122403_Chname");
		taskUser[6].setDeptId("410000");
		taskUser[6].setDeptName("410000_Chname");
		taskUser[6].setPassword("1");

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
		applyWebDto.setApplyDescription("尋點完工Site Hunt事由  junit test");
		applyWebDto.setExtraData(extraData);
		applyWorkflowWebDtos.add(applyWebDto);

		WorkflowWebActionTest.abortProcessInstance(workflowVersionService, processType, applyNo);
	}

	@Ignore
	@Test
	public void testSearchSiteCompletionShFlowApproval() {
		try {
			testApply();
			Thread.sleep(1500);
			testTask1Approval();
			Thread.sleep(1500);
			testTask2Approval();
			Thread.sleep(1500);
			testTask3Approval();
			Thread.sleep(1500);
			testTask4Approval();
			Thread.sleep(1500);
			testTask5Approval();
			Thread.sleep(1500);
			testTask6Approval();
			Thread.sleep(1500);
			testTask7Approval();
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
	public void testSearchSiteCompletionShFlowReject1() {
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
	public void testSearchSiteCompletionShFlowReject2() {
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
	public void testSearchSiteCompletionShFlowReject3() {
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
	public void testSearchSiteCompletionShFlowReject4() {
		try {
			testApply();
			Thread.sleep(1500);
			testTask1Approval();
			Thread.sleep(1500);
			testTask2Approval();
			Thread.sleep(1500);
			testTask3Approval();
			Thread.sleep(1500);
			testTask4Reject();
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
	public void testSearchSiteCompletionShFlowReject5() {
		try {
			testApply();
			Thread.sleep(1500);
			testTask1Approval();
			Thread.sleep(1500);
			testTask2Approval();
			Thread.sleep(1500);
			testTask3Approval();
			Thread.sleep(1500);
			testTask4Approval();
			Thread.sleep(1500);
			testTask5Reject();
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
	public void testSearchSiteCompletionShFlowReject6() {
		try {
			testApply();
			Thread.sleep(1500);
			testTask1Approval();
			Thread.sleep(1500);
			testTask2Approval();
			Thread.sleep(1500);
			testTask3Approval();
			Thread.sleep(1500);
			testTask4Approval();
			Thread.sleep(1500);
			testTask5Approval();
			Thread.sleep(1500);
			testTask6Reject();
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
	public void testSearchSiteCompletionShFlowReject7() {
		try {
			testApply();
			Thread.sleep(1500);
			testTask1Approval();
			Thread.sleep(1500);
			testTask2Approval();
			Thread.sleep(1500);
			testTask3Approval();
			Thread.sleep(1500);
			testTask4Approval();
			Thread.sleep(1500);
			testTask5Approval();
			Thread.sleep(1500);
			testTask6Approval();
			Thread.sleep(1500);
			testTask7Reject();
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
	public void testSearchSiteCompletionShFlowCountersign() {
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

			testTask4Countersign();
			Thread.sleep(1500);
			testCountersign1Approval();
			Thread.sleep(1500);
			testCountersign2Approval();
			Thread.sleep(1500);

			testTask5Countersign();
			Thread.sleep(1500);
			testCountersign1Approval();
			Thread.sleep(1500);
			testCountersign2Approval();
			Thread.sleep(1500);

			testTask6Countersign();
			Thread.sleep(1500);
			testCountersign1Approval();
			Thread.sleep(1500);
			testCountersign2Approval();
			Thread.sleep(1500);

			testTask7Countersign();
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
	public void testSearchSiteCompletionShFlowCountersignAtTask1Countersign1Reject() {
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
	public void testSearchSiteCompletionShFlowCountersignAtTask1Countersign2Reject() {
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
	public void testSearchSiteCompletionShFlowCountersignAtTask2Countersign1Reject() {
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
	public void testSearchSiteCompletionShFlowCountersignAtTask2Countersign2Reject() {
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
	public void testSearchSiteCompletionShFlowCountersignAtTask3Countersign1Reject() {
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
	public void testSearchSiteCompletionShFlowCountersignAtTask3Countersign2Reject() {
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
	public void testSearchSiteCompletionShFlowCountersignAtTask4Countersign1Reject() {
		try {
			testApply();
			Thread.sleep(1500);
			testTask1Approval();
			Thread.sleep(1500);
			testTask2Approval();
			Thread.sleep(1500);
			testTask3Approval();
			Thread.sleep(1500);

			testTask4Countersign();
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
	public void testSearchSiteCompletionShFlowCountersignAtTask4Countersign2Reject() {
		try {
			testApply();
			Thread.sleep(1500);
			testTask1Approval();
			Thread.sleep(1500);
			testTask2Approval();
			Thread.sleep(1500);
			testTask3Approval();
			Thread.sleep(1500);

			testTask4Countersign();
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
	public void testSearchSiteCompletionShFlowCountersignAtTask5Countersign1Reject() {
		try {
			testApply();
			Thread.sleep(1500);
			testTask1Approval();
			Thread.sleep(1500);
			testTask2Approval();
			Thread.sleep(1500);
			testTask3Approval();
			Thread.sleep(1500);
			testTask4Approval();
			Thread.sleep(1500);

			testTask5Countersign();
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
	public void testSearchSiteCompletionShFlowCountersignAtTask5Countersign2Reject() {
		try {
			testApply();
			Thread.sleep(1500);
			testTask1Approval();
			Thread.sleep(1500);
			testTask2Approval();
			Thread.sleep(1500);
			testTask3Approval();
			Thread.sleep(1500);
			testTask4Approval();
			Thread.sleep(1500);

			testTask5Countersign();
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
	public void testSearchSiteCompletionShFlowCountersignAtTask6Countersign1Reject() {
		try {
			testApply();
			Thread.sleep(1500);
			testTask1Approval();
			Thread.sleep(1500);
			testTask2Approval();
			Thread.sleep(1500);
			testTask3Approval();
			Thread.sleep(1500);
			testTask4Approval();
			Thread.sleep(1500);
			testTask5Approval();
			Thread.sleep(1500);

			testTask6Countersign();
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
	public void testSearchSiteCompletionShFlowCountersignAtTask6Countersign2Reject() {
		try {
			testApply();
			Thread.sleep(1500);
			testTask1Approval();
			Thread.sleep(1500);
			testTask2Approval();
			Thread.sleep(1500);
			testTask3Approval();
			Thread.sleep(1500);
			testTask4Approval();
			Thread.sleep(1500);
			testTask5Approval();
			Thread.sleep(1500);

			testTask6Countersign();
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
	public void testSearchSiteCompletionShFlowCountersignAtTask7Countersign1Reject() {
		try {
			testApply();
			Thread.sleep(1500);
			testTask1Approval();
			Thread.sleep(1500);
			testTask2Approval();
			Thread.sleep(1500);
			testTask3Approval();
			Thread.sleep(1500);
			testTask4Approval();
			Thread.sleep(1500);
			testTask5Approval();
			Thread.sleep(1500);
			testTask6Approval();
			Thread.sleep(1500);
			
			testTask7Countersign();
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
	public void testSearchSiteCompletionShFlowCountersignAtTask7Countersign2Reject() {
		try {
			testApply();
			Thread.sleep(1500);
			testTask1Approval();
			Thread.sleep(1500);
			testTask2Approval();
			Thread.sleep(1500);
			testTask3Approval();
			Thread.sleep(1500);
			testTask4Approval();
			Thread.sleep(1500);
			testTask5Approval();
			Thread.sleep(1500);
			testTask6Approval();
			Thread.sleep(1500);

			testTask7Countersign();
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
	public void testSearchSiteCompletionShFlowNotify() {
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

			testTask4Notify();
			Thread.sleep(1500);
			testNotify1Approval();
			Thread.sleep(1500);
			testNotify2Approval();
			Thread.sleep(1500);

			testTask5Notify();
			Thread.sleep(1500);
			testNotify1Approval();
			Thread.sleep(1500);
			testNotify2Approval();
			Thread.sleep(1500);

			testTask6Notify();
			Thread.sleep(1500);
			testNotify1Approval();
			Thread.sleep(1500);
			testNotify2Approval();
			Thread.sleep(1500);

			testTask7Notify();
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
	public void testSearchSiteCompletionShFlowAbortProcess() {
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

	public void testTask4Approval() {
		try {
			verify(taskUser[3], "APPROVAL");
		} catch (WorkflowException e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	public void testTask4Reject() {
		try {
			verify(taskUser[3], "REJECT");
		} catch (WorkflowException e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	public void testTask4Countersign() {
		try {
			verify(taskUser[3], "COUNTERSIGN");
		} catch (WorkflowException e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	public void testTask4Notify() {
		try {
			verify(taskUser[3], "NOTIFY");
		} catch (WorkflowException e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	public void testTask5Approval() {
		try {
			verify(taskUser[4], "APPROVAL");
		} catch (WorkflowException e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	public void testTask5Reject() {
		try {
			verify(taskUser[4], "REJECT");
		} catch (WorkflowException e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	public void testTask5Countersign() {
		try {
			verify(taskUser[4], "COUNTERSIGN");
		} catch (WorkflowException e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	public void testTask5Notify() {
		try {
			verify(taskUser[4], "NOTIFY");
		} catch (WorkflowException e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	public void testTask6Approval() {
		try {
			verify(taskUser[5], "APPROVAL");
		} catch (WorkflowException e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	public void testTask6Reject() {
		try {
			verify(taskUser[5], "REJECT");
		} catch (WorkflowException e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	public void testTask6Countersign() {
		try {
			verify(taskUser[5], "COUNTERSIGN");
		} catch (WorkflowException e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	public void testTask6Notify() {
		try {
			verify(taskUser[5], "NOTIFY");
		} catch (WorkflowException e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	public void testTask7Approval() {
		try {
			verify(taskUser[6], "APPROVAL");
		} catch (WorkflowException e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	public void testTask7Reject() {
		try {
			verify(taskUser[6], "REJECT");
		} catch (WorkflowException e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	public void testTask7Countersign() {
		try {
			verify(taskUser[6], "COUNTERSIGN");
		} catch (WorkflowException e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	public void testTask7Notify() {
		try {
			verify(taskUser[6], "NOTIFY");
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
