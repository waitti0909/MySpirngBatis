package com.foya.noms.workflow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import org.apache.commons.lang.StringUtils;
import org.jfree.util.Log;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.foya.noms.dto.auth.UserDTO;
import com.foya.noms.dto.common.WorkflowVersionDTO;
import com.foya.noms.dto.workflow.WorkflowWebDTO;
import com.foya.noms.enums.WorkflowEmailType;
import com.foya.noms.resources.AppConstants;
import com.foya.noms.service.common.EmailTemplateService;
import com.foya.noms.service.org.OrgWorkflowService;
import com.foya.noms.service.workflow.WorkflowVersionService;
import com.foya.noms.util.DateUtils;
import com.foya.noms.workflow.executor.ProcessTypeExecutor;
import com.foya.workflow.exception.WorkflowException;
import com.foya.workflow.model.BaseProcessVariables;
import com.foya.workflow.rest.RestEngineFactory;
import com.foya.workflow.rest.WorkflowRestFacade;
import com.foya.workflow.util.MultiThreadHandler;

public class WorkflowWebActionTest {
	private static List<String> emailTypes = null;
	static {
		emailTypes = new ArrayList<String>();
		for (WorkflowEmailType type : WorkflowEmailType.values()) {
			emailTypes.add(type.name());
		}
	}

	@Test
	public void test() {
		// nothing
	}

	public static void abortProcessInstance(WorkflowVersionService workflowVersionService,
			String processType, String applyNo) {
		try {
			WorkflowVersionDTO version = WorkflowActionMethod.getWorkflowVersion(
					workflowVersionService, processType);
			WorkflowRestFacade engine = null;
			if (AppConstants.WORKFLOW_REST_HOST.contains("https://".toLowerCase())) {
				engine = RestEngineFactory.createRemoteEngineSSL(AppConstants.WORKFLOW_REST_HOST,
						version.getDeploymentId(), 60, "admin", "admin");
			} else {
				engine = RestEngineFactory.createRemoteEngine(AppConstants.WORKFLOW_REST_HOST,
						version.getDeploymentId(), 60, "admin", "admin");
			}
			List<Long> pids = engine.findActiveProcessInstanceIdsByProcessTypeAndApplyNo(
					processType, applyNo);
			if (!pids.isEmpty()) {
				engine.abortProcessInstances(pids);
			}
		} catch (Exception e) {
			// nothing
		}
	}

	public static boolean isProcessCompleted(WorkflowVersionService workflowVersionService,
			UserDTO executor, String processType, String applyNo) {
		try {
			WorkflowVersionDTO version = WorkflowActionMethod.getWorkflowVersion(
					workflowVersionService, processType);
			WorkflowRestFacade engine = null;
			if (AppConstants.WORKFLOW_REST_HOST.contains("https://".toLowerCase())) {
				engine = RestEngineFactory.createRemoteEngineSSL(AppConstants.WORKFLOW_REST_HOST,
						version.getDeploymentId(), 60, "admin", "admin");
			} else {
				engine = RestEngineFactory.createRemoteEngine(AppConstants.WORKFLOW_REST_HOST,
						version.getDeploymentId(), 60, "admin", "admin");
			}
			List<Long> pids = engine.findActiveProcessInstanceIdsByProcessTypeAndApplyNo(
					processType, applyNo);
			if (pids.isEmpty()) {
				return true;
			} else {
				engine.abortProcessInstances(pids);
			}
		} catch (WorkflowException e) {
			Assert.fail(e.getLocalizedMessage());
		}
		return false;
	}

	public enum workflowWebAction {
		APPLY {
			@Override
			public void execute(OrgWorkflowService orgWorkflowService,
					WorkflowVersionService workflowVersionService,
					final EmailTemplateService emailTemplateService, UserDTO executor,
					List<WorkflowWebDTO> webDtos) throws WorkflowException {

				apply(orgWorkflowService, workflowVersionService, emailTemplateService, executor,
						webDtos);
			}
		},
		APPROVAL {
			@Override
			public void execute(OrgWorkflowService orgWorkflowService,
					WorkflowVersionService workflowVersionService,
					final EmailTemplateService emailTemplateService, UserDTO executor,
					List<WorkflowWebDTO> webDtos) throws WorkflowException {

				verify(orgWorkflowService, workflowVersionService, executor, webDtos,
						RESULT_APPROVE);
			}
		},
		REJECT {
			@Override
			public void execute(OrgWorkflowService orgWorkflowService,
					WorkflowVersionService workflowVersionService,
					final EmailTemplateService emailTemplateService, UserDTO executor,
					List<WorkflowWebDTO> webDtos) throws WorkflowException {

				verify(orgWorkflowService, workflowVersionService, executor, webDtos, RESULT_REJECT);
			}
		},
		COUNTERSIGN {
			@Override
			public void execute(OrgWorkflowService orgWorkflowService,
					WorkflowVersionService workflowVersionService,
					final EmailTemplateService emailTemplateService, UserDTO executor,
					List<WorkflowWebDTO> webDtos) throws WorkflowException {

				validateArgument(executor, webDtos);

				WorkflowWebDTO webDto = webDtos.get(0);
				WorkflowVersionDTO version = getWorkflowVersion(workflowVersionService,
						webDto.getProcessType());
				WorkflowRestFacade engine = getEngine(version.getDeploymentId(), executor);

				long taskId = webDto.getTaskId();
				String comment = webDto.getComment();
				engine.countersign(taskId, executor.getUsername(), webDto.getCountersigns(),
						RESULT_COUNTERSIGN, comment);
			}
		},
		NOTIFY {
			@Override
			public void execute(OrgWorkflowService orgWorkflowService,
					WorkflowVersionService workflowVersionService,
					final EmailTemplateService emailTemplateService, UserDTO executor,
					List<WorkflowWebDTO> webDtos) throws WorkflowException {

				validateArgument(executor, webDtos);

				WorkflowWebDTO webDto = webDtos.get(0);
				WorkflowVersionDTO version = getWorkflowVersion(workflowVersionService,
						webDto.getProcessType());
				WorkflowRestFacade engine = getEngine(version.getDeploymentId(), executor);

				long taskId = webDto.getTaskId();
				String comment = webDto.getComment();
				engine.notify(taskId, executor.getUsername(), webDto.getNotifys(), RESULT_NOTIFY,
						comment);
			}
		},
		ABORT_PROCESS {
			@Override
			public void execute(OrgWorkflowService orgWorkflowService,
					WorkflowVersionService workflowVersionService,
					final EmailTemplateService emailTemplateService, UserDTO executor,
					List<WorkflowWebDTO> webDtos) throws WorkflowException {

				validateArgument(executor, webDtos);

				WorkflowWebDTO webDto = webDtos.get(0);
				WorkflowVersionDTO version = getWorkflowVersion(workflowVersionService,
						webDto.getProcessType());
				WorkflowRestFacade engine = getEngine(version.getDeploymentId(), executor);

				String applyNo = webDto.getApplyNo();
				String processType = webDto.getProcessType();
				engine.abortRootProcessInstanceByProcessTypeAndApplyNo(processType, applyNo);

			}
		},
		BATCH_APPLY {
			@Override
			public void execute(final OrgWorkflowService orgWorkflowService,
					final WorkflowVersionService workflowVersionService,
					final EmailTemplateService emailTemplateService, final UserDTO executor,
					final List<WorkflowWebDTO> webDtos) throws WorkflowException {

				validateArgument(executor, webDtos);

				Map<String, WorkflowVersionDTO> processTypeVersionMap = getProcessTypeWorkflowVersionMap(
						workflowVersionService, webDtos);
				List<Callable<WorkflowWebDTO>> workers = new ArrayList<>(webDtos.size());
				for (final WorkflowWebDTO webDto : webDtos) {
					final String processType = webDto.getProcessType();
					final WorkflowVersionDTO version = processTypeVersionMap.get(processType);
					Callable<WorkflowWebDTO> worker = new Callable<WorkflowWebDTO>() {
						@Override
						public WorkflowWebDTO call() throws Exception {
							if (version != null) {
								BaseProcessVariables variables = ProcessTypeExecutor.valueOf(
										processType).getVariablesObject(orgWorkflowService,
										emailTemplateService, executor, webDto.getExtraData());
								variables.setApplyNo(webDto.getApplyNo());
								variables.setApplyDescription(new String[]{webDto.getApplyDescription()});
								variables.setApplicantId(executor.getUsername());
								variables.setApplyDate(DateUtils.today());
								variables.setProcessType(processType);
								variables.setRestFailHandler(AppConstants.WORKFLOW_FAIL_HANDLER);
								variables.setFailMailSubject(new String[] { "Workflow mail fail" });
								variables
										.setFailMailContent(new String[] { "Mail fail task executor : " });

								WorkflowRestFacade engine = getEngine(version.getDeploymentId(),
										executor);
								engine.startProcess(version.getProcessId(), variables.toVariables());
								return webDto;
							} else {
								log.warn("ProcessType :" + processType
										+ " is not found from workflow version.");
							}
							return webDto;
						}
					};
					workers.add(worker);
				}
				try {
					MultiThreadHandler.executeMultiThread(3, workers);
				} catch (Exception e) {
					throw new WorkflowException(e);
				}
			}
		},
		BATCH_APPROVAL {
			@Override
			public void execute(OrgWorkflowService orgWorkflowService,
					WorkflowVersionService workflowVersionService,
					final EmailTemplateService emailTemplateService, UserDTO executor,
					List<WorkflowWebDTO> webDtos) throws WorkflowException {

				batchVerify(orgWorkflowService, workflowVersionService, executor, webDtos,
						RESULT_APPROVE);
			}
		},
		BATCH_REJECT {
			@Override
			public void execute(OrgWorkflowService orgWorkflowService,
					WorkflowVersionService workflowVersionService,
					final EmailTemplateService emailTemplateService, UserDTO executor,
					List<WorkflowWebDTO> webDtos) throws WorkflowException {

				batchVerify(orgWorkflowService, workflowVersionService, executor, webDtos,
						RESULT_REJECT);
			}
		},
		// TODO test not yet
		BATCH_ABORT_PROCESS {
			@Override
			public void execute(final OrgWorkflowService orgWorkflowService,
					final WorkflowVersionService workflowVersionService,
					final EmailTemplateService emailTemplateService, final UserDTO executor,
					final List<WorkflowWebDTO> webDtos) throws WorkflowException {

				WorkflowActionMethod.validateArgument(executor, webDtos);
				final Map<String, WorkflowVersionDTO> processTypeVersionMap = getProcessTypeWorkflowVersionMap(
						workflowVersionService, webDtos);
				List<Callable<WorkflowWebDTO>> workers = new ArrayList<>(webDtos.size());
				for (final WorkflowWebDTO dto : webDtos) {
					Callable<WorkflowWebDTO> worker = new Callable<WorkflowWebDTO>() {
						@Override
						public WorkflowWebDTO call() throws Exception {
							String applyNo = dto.getApplyNo();
							String processType = dto.getProcessType();
							WorkflowVersionDTO version = processTypeVersionMap.get(processType);
							WorkflowRestFacade engine = WorkflowActionMethod.getEngine(
									version.getDeploymentId(), executor);
							engine.abortRootProcessInstanceByProcessTypeAndApplyNo(processType,
									applyNo);
							return dto;
						}
					};
					workers.add(worker);
				}
				try {
					MultiThreadHandler.executeMultiThread(3, workers);
				} catch (Exception e) {
					throw new WorkflowException(e);
				}
			}
		},
		AGENT_APPROVAL {
			@Override
			public void execute(OrgWorkflowService orgWorkflowService,
					WorkflowVersionService workflowVersionService,
					final EmailTemplateService emailTemplateService, UserDTO executor,
					List<WorkflowWebDTO> webDtos) throws WorkflowException {

				agentVerify(orgWorkflowService, workflowVersionService, executor, webDtos,
						RESULT_APPROVE);
			}
		},
		AGENT_REJECT {
			@Override
			public void execute(OrgWorkflowService orgWorkflowService,
					WorkflowVersionService workflowVersionService,
					final EmailTemplateService emailTemplateService, UserDTO executor,
					List<WorkflowWebDTO> webDtos) throws WorkflowException {

				agentVerify(orgWorkflowService, workflowVersionService, executor, webDtos,
						RESULT_REJECT);
			}
		},
		AGENT_BATCH_APPROVAL {
			@Override
			public void execute(OrgWorkflowService orgWorkflowService,
					WorkflowVersionService workflowVersionService,
					final EmailTemplateService emailTemplateService, UserDTO executor,
					List<WorkflowWebDTO> webDtos) throws WorkflowException {

				agentBatchVerify(orgWorkflowService, workflowVersionService, executor, webDtos,
						RESULT_APPROVE);
			}
		},
		AGENT_BATCH_REJECT {
			@Override
			public void execute(OrgWorkflowService orgWorkflowService,
					WorkflowVersionService workflowVersionService,
					final EmailTemplateService emailTemplateService, UserDTO executor,
					List<WorkflowWebDTO> webDtos) throws WorkflowException {

				agentBatchVerify(orgWorkflowService, workflowVersionService, executor, webDtos,
						RESULT_REJECT);
			}
		},
		AGENT_COUNTERSIGN {
			@Override
			public void execute(OrgWorkflowService orgWorkflowService,
					WorkflowVersionService workflowVersionService,
					final EmailTemplateService emailTemplateService, UserDTO executor,
					List<WorkflowWebDTO> webDtos) throws WorkflowException {

				validateArgument(executor, webDtos);
				WorkflowWebDTO webDto = webDtos.get(0);
				WorkflowVersionDTO version = getWorkflowVersion(workflowVersionService,
						webDto.getProcessType());

				long taskId = webDto.getTaskId();
				UserDTO taskOwner = orgWorkflowService.getUserDTO(webDto.getTaskOwnerId());
				delegate(version, taskId, taskOwner, executor);

				WorkflowRestFacade executorEngine = getEngine(version.getDeploymentId(), executor);
				String comment = webDto.getComment();
				executorEngine.countersign(taskId, executor.getUsername(),
						webDto.getCountersigns(), RESULT_APPROVE, comment);
			}
		},
		AGENT_NOTIFY {
			@Override
			public void execute(OrgWorkflowService orgWorkflowService,
					WorkflowVersionService workflowVersionService,
					final EmailTemplateService emailTemplateService, UserDTO executor,
					List<WorkflowWebDTO> webDtos) throws WorkflowException {

				validateArgument(executor, webDtos);
				WorkflowWebDTO webDto = webDtos.get(0);
				WorkflowVersionDTO version = getWorkflowVersion(workflowVersionService,
						webDto.getProcessType());

				long taskId = webDto.getTaskId();
				UserDTO taskOwner = orgWorkflowService.getUserDTO(webDto.getTaskOwnerId());
				delegate(version, taskId, taskOwner, executor);

				WorkflowRestFacade executorEngine = getEngine(version.getDeploymentId(), executor);
				String comment = webDto.getComment();
				executorEngine.notify(taskId, executor.getUsername(), webDto.getNotifys(),
						RESULT_APPROVE, comment);
			}
		};

		protected Logger log = LoggerFactory.getLogger(this.getClass());
		public static final String RESULT_APPROVE = "Y";
		public static final String RESULT_REJECT = "N";
		public static final String RESULT_COUNTERSIGN = "C";
		public static final String RESULT_NOTIFY = "O";

		public abstract void execute(final OrgWorkflowService orgWorkflowService,
				final WorkflowVersionService workflowVersionService,
				final EmailTemplateService emailTemplateService, final UserDTO executor,
				final List<WorkflowWebDTO> webDtos) throws WorkflowException;

		private static WorkflowRestFacade getEngine(String deploymentId, final UserDTO executor)
				throws WorkflowException {
			if (AppConstants.WORKFLOW_REST_HOST.contains("https://".toLowerCase())) {
				return RestEngineFactory.createRemoteEngineSSL(AppConstants.WORKFLOW_REST_HOST,
						deploymentId, 60, executor.getUsername(), executor.getPassword());
			} else {
				return RestEngineFactory.createRemoteEngine(AppConstants.WORKFLOW_REST_HOST,
						deploymentId, 60, executor.getUsername(), executor.getPassword());
			}
		}

		private static void apply(final OrgWorkflowService orgWorkflowService,
				final WorkflowVersionService workflowVersionService,
				final EmailTemplateService emailTemplateService, final UserDTO executor,
				final List<WorkflowWebDTO> webDtos) throws WorkflowException {

			validateWorkflowWebDTOs(webDtos);
			WorkflowWebDTO webDto = webDtos.get(0);
			apply(orgWorkflowService, workflowVersionService, emailTemplateService, executor,
					webDto.getProcessType(), webDto.getApplyNo(), webDto.getApplyDescription(),
					webDto.getExtraData());
		}

		private static void apply(final OrgWorkflowService orgWorkflowService,
				final WorkflowVersionService workflowVersionService,
				final EmailTemplateService emailTemplateService, final UserDTO executor,
				String processType, String applyNo, String applyDescription,
				final Map<String, Object> extraData) throws WorkflowException {

			validateExecutor(executor);
			if (StringUtils.isBlank(processType)) {
				throw new WorkflowException("processType is blank.");
			}
			if (StringUtils.isBlank(applyNo)) {
				throw new WorkflowException("applyNo is blank.");
			}
			WorkflowVersionDTO version = getWorkflowVersion(workflowVersionService, processType);
			Map<String, Object> data = new HashMap<>();

			// for email variables
			data.put("appId", applyNo);
			data.put("processName", version.getProcessName());
			data.put("appUser", executor.getChName());
			data.put("appDept", executor.getDeptName());
			// for workflow variables
			data.put("applyNo", applyNo);
			data.put("applyDate", DateUtils.today());
			data.put("applyDescription", new String[] { applyDescription });
			data.put("applicantId", executor.getUsername());
			data.put("processType", processType);
			data.put("failMailTo", "ericwang@foyatech.com");
			data.put("failMailCc", "ericwang@foyatech.com");
			data.put("failMailSubject", new String[]{"fail mail subject"});
			data.put("failMailContent", new String[]{"fail mail content"});
			data.put("restFailHandler", AppConstants.WORKFLOW_FAIL_HANDLER);
			data.put("restFailHandlerTaskName", AppConstants.WORKFLOW_FAIL_HANDLER_TASK_NAME);
			final String restUrl = AppConstants.NOMS_HOST + "/noms/flowActionRest?type="
					+ processType + "&docNo=" + applyNo + "&action=";
			data.put("restApprovalUrl", restUrl + AppConstants.WORKFLOW_REST_APPROVAL);
			data.put("restRejectUrl", restUrl + AppConstants.WORKFLOW_REST_REJECT);

			if (extraData != null) {
				data.putAll(extraData);
			}
			BaseProcessVariables vars = ProcessTypeExecutor.valueOf(processType)
					.getVariablesObject(orgWorkflowService, emailTemplateService, executor, data);

			WorkflowRestFacade engine = getEngine(version.getDeploymentId(), executor);
			
			engine.startProcess(version.getProcessId(), vars.toVariables());
		}

		private static void verify(OrgWorkflowService orgWorkflowService,
				WorkflowVersionService workflowVersionService, UserDTO executor,
				List<WorkflowWebDTO> webDtos, String result) throws WorkflowException {

			verify(orgWorkflowService, workflowVersionService, executor, webDtos, result, false);
		}

		private static void agentVerify(OrgWorkflowService orgWorkflowService,
				WorkflowVersionService workflowVersionService, UserDTO executor,
				List<WorkflowWebDTO> webDtos, String result) throws WorkflowException {

			verify(orgWorkflowService, workflowVersionService, executor, webDtos, result, true);
		}

		private static void verify(OrgWorkflowService orgWorkflowService,
				WorkflowVersionService workflowVersionService, UserDTO executor,
				List<WorkflowWebDTO> webDtos, String result, boolean isAgent)
				throws WorkflowException {

			validateArgument(executor, webDtos);

			WorkflowWebDTO webDto = webDtos.get(0);
			List<WorkflowVersionDTO> versions = getWorkflowVersions(workflowVersionService,
					webDto.getProcessType());
			WorkflowVersionDTO version = versions.get(0);

			long taskId = webDto.getTaskId();
			UserDTO taskOwner = null;
			if (isAgent) {
				taskOwner = orgWorkflowService.getUserDTO(webDto.getTaskOwnerId());
				delegate(version, taskId, taskOwner, executor);
			}
			WorkflowRestFacade executorEngine = getEngine(version.getDeploymentId(), executor);
			String comment = webDto.getComment();
			try {
				Map<String, Object> extraData = new HashMap<>(2);
				extraData.put("jsonData", new String[] { webDto.getJsonData() });
				extraData.put("verifyUserName", executor.getChName());

				executorEngine.checkIn(taskId, executor.getUsername(), result, comment, extraData);
			} catch (Exception e) {
				if (isAgent) {
					delegateRollback(version, taskId, taskOwner, executor);
				}
				throw new WorkflowException(e);
			}
		}

		private static void batchVerify(OrgWorkflowService orgWorkflowService,
				WorkflowVersionService workflowVersionService, UserDTO executor,
				List<WorkflowWebDTO> webDtos, String result) throws WorkflowException {

			validateArgument(executor, webDtos);

			WorkflowVersionDTO version = getWorkflowVersion(workflowVersionService, webDtos.get(0)
					.getProcessType());
			WorkflowRestFacade engine = getEngine(version.getDeploymentId(), executor);

			List<Long> taskIds = new ArrayList<>(webDtos.size());
			for (WorkflowWebDTO webDto : webDtos) {
				taskIds.add(webDto.getTaskId());
			}
			WorkflowWebDTO webDto = webDtos.get(0);
			Map<String, Object> extraData = new HashMap<>(2);
			extraData.put("jsonData", new String[] { webDto.getJsonData() });
			extraData.put("verifyUserName", executor.getChName());
			Map<Long, Boolean> resultMap = engine.batchCheckIn(taskIds, executor.getUsername(),
					result, webDto.getComment(), extraData);

			Map<Long, WorkflowWebDTO> successMap = getTaskIdWorkflowWebDtoMapOfSuccessCheckIn(
					webDtos, resultMap);

			if (successMap.size() == webDtos.size()) {
				return;
			}
			Map<Long, WorkflowWebDTO> failMap = getTaskIdWorkflowWebDtoMapOfFailCheckIn(webDtos,
					resultMap);

			StringBuffer failMsg = new StringBuffer();
			for (Map.Entry<Long, WorkflowWebDTO> entry : failMap.entrySet()) {
				long taskId = entry.getKey();
				failMsg.append("taskId = ").append(taskId).append(" check in fail").append("\n");
			}
			if (failMsg.length() > 0) {
				String msg = failMsg.toString();
				Log.error(msg);
				throw new WorkflowException(msg);
			}

		}

		private static void agentBatchVerify(final OrgWorkflowService orgWorkflowService,
				final WorkflowVersionService workflowVersionService, final UserDTO executor,
				final List<WorkflowWebDTO> webDtos, String result) throws WorkflowException {

			validateArgument(executor, webDtos);

			WorkflowVersionDTO version = getWorkflowVersion(workflowVersionService, webDtos.get(0)
					.getProcessType());
			WorkflowRestFacade executorEngine = getEngine(version.getDeploymentId(), executor);

			Map<String, UserDTO> cacheUserDtoMap = new HashMap<>();
			List<Long> taskIds = new ArrayList<>(webDtos.size());
			for (WorkflowWebDTO webDto : webDtos) {
				long taskId = webDto.getTaskId();
				taskIds.add(taskId);
				UserDTO taskOwner = null;
				String taskOwnerId = webDto.getTaskOwnerId();
				taskOwner = cacheUserDtoMap.get(taskOwnerId);
				if (taskOwner == null) {
					taskOwner = orgWorkflowService.getUserDTO(taskOwnerId);
					cacheUserDtoMap.put(taskOwnerId, taskOwner);
				}
				delegate(version, taskId, taskOwner, executor);
			}
			WorkflowWebDTO webDto = webDtos.get(0);
			Map<String, Object> extraData = new HashMap<>(2);
			extraData.put("jsonData", new String[] { webDto.getJsonData() });
			extraData.put("verifyUserName", executor.getChName());
			Map<Long, Boolean> resultMap = executorEngine.batchCheckIn(taskIds,
					executor.getUsername(), result, webDto.getComment(), extraData);

			Map<Long, WorkflowWebDTO> successMap = getTaskIdWorkflowWebDtoMapOfSuccessCheckIn(
					webDtos, resultMap);

			if (successMap.size() == webDtos.size()) {
				return;
			} else {
				doAgentVerifyFail(orgWorkflowService, webDtos, successMap, resultMap,
						cacheUserDtoMap, executor, version);
			}
		}

		private static void doAgentVerifyFail(final OrgWorkflowService orgWorkflowService,
				final List<WorkflowWebDTO> webDtos, final Map<Long, WorkflowWebDTO> successMap,
				Map<Long, Boolean> resultMap, final Map<String, UserDTO> cacheUserDtoMap,
				final UserDTO executor, final WorkflowVersionDTO version) throws WorkflowException {

			Map<Long, WorkflowWebDTO> failMap = getTaskIdWorkflowWebDtoMapOfFailCheckIn(webDtos,
					resultMap);
			List<Callable<WorkflowWebDTO>> workers = new ArrayList<>(failMap.size());
			for (final Map.Entry<Long, WorkflowWebDTO> entry : failMap.entrySet()) {
				final long taskId = entry.getKey();
				Callable<WorkflowWebDTO> worker = new Callable<WorkflowWebDTO>() {
					@Override
					public WorkflowWebDTO call() throws Exception {
						WorkflowWebDTO webDto = entry.getValue();
						String taskOwnerId = webDto.getTaskOwnerId();
						UserDTO taskOwner = cacheUserDtoMap.get(taskOwnerId);
						delegateRollback(version, taskId, taskOwner, executor);
						throw new Exception(new StringBuffer("taskId = ").append(taskId)
								.append(" check in fail").append("\n").toString());
					}
				};
				workers.add(worker);
			}
			try {
				MultiThreadHandler.executeMultiThread(3, workers);
			} catch (Exception e) {
				throw new WorkflowException();
			}
		}

		private static void delegateRollback(WorkflowVersionDTO version, long taskId,
				UserDTO ownerUser, UserDTO targetUser) throws WorkflowException {
			delegate(version, taskId, targetUser, ownerUser);
		}

		private static void delegate(WorkflowVersionDTO version, long taskId, UserDTO ownerUser,
				UserDTO targetUser) throws WorkflowException {
			WorkflowRestFacade ownEngine = getEngine(version.getDeploymentId(), ownerUser);
			ownEngine.delegate(taskId, ownerUser.getUsername(), targetUser.getUsername());
		}

		private static Map<String, WorkflowVersionDTO> getProcessTypeWorkflowVersionMap(
				WorkflowVersionService workflowVersionService, Collection<WorkflowWebDTO> webDtos) {

			Set<String> processTypes = new HashSet<>();
			for (WorkflowWebDTO data : webDtos) {
				processTypes.add(data.getProcessType());
			}
			List<WorkflowVersionDTO> versions = workflowVersionService
					.findActiveWorkflowVersionByProcessTypes(processTypes);

			Map<String, WorkflowVersionDTO> processTypeVersionMap = new HashMap<>(
					processTypes.size());
			for (WorkflowWebDTO data : webDtos) {
				String processType = data.getProcessType();
				Iterator<WorkflowVersionDTO> versionIt = versions.iterator();
				while (versionIt.hasNext()) {
					WorkflowVersionDTO version = versionIt.next();
					if (StringUtils.equalsIgnoreCase(processType, version.getProcessType())) {
						processTypeVersionMap.put(processType, version);
						versionIt.remove();
						break;
					}
				}
			}
			return processTypeVersionMap;
		}

		private static WorkflowVersionDTO getWorkflowVersion(
				WorkflowVersionService workflowVersionService, String processType)
				throws WorkflowException {
			return getWorkflowVersions(workflowVersionService, processType).get(0);
		}

		private static List<WorkflowVersionDTO> getWorkflowVersions(
				WorkflowVersionService workflowVersionService, String processType)
				throws WorkflowException {
			List<WorkflowVersionDTO> version = null;
			if (StringUtils.isNotBlank(processType)) {
				version = workflowVersionService
						.findActiveWorkflowVersionByProcessType(processType);
			} else {
				/**
				 * 因為checkIn/abortProcess 時並不會把rest API engine的deployment id 做為執行依據 , 但因為取得rest API
				 * engine 需要 JBPM 上的實際存在的 deployment id, 所以需要取得其中一個deployment id即可.
				 */
				version = workflowVersionService.findActiveWorkflowVersion();
			}
			if (version == null || version.isEmpty()) {
				throw new WorkflowException("Not found active processType : " + processType);
			}
			return version;
		}

		private static Map<Long, WorkflowWebDTO> getTaskIdWorkflowWebDtoMapOfSuccessCheckIn(
				Collection<WorkflowWebDTO> webDtos, Map<Long, Boolean> resultMap) {
			return getTaskIdWorkflowWebDtoMapOfBooleanCheckIn(webDtos, resultMap, true);
		}

		private static Map<Long, WorkflowWebDTO> getTaskIdWorkflowWebDtoMapOfFailCheckIn(
				Collection<WorkflowWebDTO> webDtos, Map<Long, Boolean> resultMap) {
			return getTaskIdWorkflowWebDtoMapOfBooleanCheckIn(webDtos, resultMap, false);
		}

		private static Map<Long, WorkflowWebDTO> getTaskIdWorkflowWebDtoMapOfBooleanCheckIn(
				Collection<WorkflowWebDTO> webDtos, Map<Long, Boolean> resultMap, boolean bool) {
			Map<Long, WorkflowWebDTO> successMap = new HashMap<>();
			for (Map.Entry<Long, Boolean> entry : resultMap.entrySet()) {
				if (entry.getValue() == bool) {
					Long taskId = entry.getKey();
					for (WorkflowWebDTO webDto : webDtos) {
						if (webDto.getTaskId() == taskId) {
							successMap.put(taskId, webDto);
							break;
						}
					}
				}
			}
			return successMap;
		}

		public static void validateArgument(final UserDTO executor,
				final Collection<WorkflowWebDTO> webDtos) throws WorkflowException {
			validateExecutor(executor);
			validateWorkflowWebDTOs(webDtos);
		}

		public static void validateExecutor(final UserDTO executor) throws WorkflowException {
			if (executor == null) {
				throw new WorkflowException("Executor is null.");
			}
			if (StringUtils.isBlank(executor.getUsername())) {
				throw new WorkflowException("Executor name is blank.");
			}
		}

		public static void validateWorkflowWebDTOs(final Collection<WorkflowWebDTO> webDtos)
				throws WorkflowException {
			if (webDtos == null) {
				throw new WorkflowException("Action data is null.");
			}
			if (webDtos.isEmpty()) {
				throw new WorkflowException("Action data is empty.");
			}
		}
	}
}