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
import org.apache.commons.lang.exception.ExceptionUtils;
import org.kie.services.client.api.command.exception.RemoteCommunicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.foya.noms.dto.auth.UserDTO;
import com.foya.noms.dto.common.WorkflowVersionDTO;
import com.foya.noms.dto.workflow.WorkflowWebDTO;
import com.foya.noms.resources.AppConstants;
import com.foya.noms.service.common.EmailTemplateService;
import com.foya.noms.service.org.OrgWorkflowService;
import com.foya.noms.service.system.SignLogService;
import com.foya.noms.service.workflow.WorkflowVersionService;
import com.foya.noms.util.DateUtils;
import com.foya.noms.workflow.executor.ProcessTypeExecutor;
import com.foya.workflow.exception.WorkflowException;
import com.foya.workflow.model.BaseProcessVariables;
import com.foya.workflow.rest.RestEngineFactory;
import com.foya.workflow.rest.WorkflowRestFacade;
import com.foya.workflow.util.MultiThreadHandler;

public class WorkflowActionMethod {
	private static Logger log = LoggerFactory.getLogger(WorkflowActionMethod.class);

	private WorkflowActionMethod() {
	}

	/**
	 * Gets the engine.
	 *
	 * @param executor the executor
	 * @return the engine
	 * @throws WorkflowException the workflow exception
	 */
	public static WorkflowRestFacade getEngine(final UserDTO executor) throws WorkflowException {
		return getEngine(null, executor);
	}

	/**
	 * Gets the engine.
	 *
	 * @param deploymentId the deployment id
	 * @param executor the executor
	 * @return the engine
	 * @throws WorkflowException the workflow exception
	 */
	public static WorkflowRestFacade getEngine(String deploymentId, final UserDTO executor)
			throws WorkflowException {
		if (AppConstants.WORKFLOW_REST_HOST.contains("https://")) {
			return RestEngineFactory.createRemoteEngineSSL(AppConstants.WORKFLOW_REST_HOST,
					deploymentId, 60, executor.getUsername(), executor.getPassword());
		} else {
			return RestEngineFactory.createRemoteEngine(AppConstants.WORKFLOW_REST_HOST,
					deploymentId, 60, executor.getUsername(), executor.getPassword());
		}
	}

	/**
	 * Apply.
	 *
	 * @param orgWorkflowService the org workflow service
	 * @param workflowVersionService the workflow version service
	 * @param emailTemplateService the email template service
	 * @param signLogService the sign log service
	 * @param executor the executor
	 * @param webDtos the web dtos
	 * @throws WorkflowException the workflow exception
	 */
	public static void apply(final OrgWorkflowService orgWorkflowService,
			final WorkflowVersionService workflowVersionService,
			final EmailTemplateService emailTemplateService, final SignLogService signLogService,
			final UserDTO executor, final List<WorkflowWebDTO> webDtos) throws WorkflowException {

		validateWorkflowWebDTOs(webDtos);
		WorkflowWebDTO webDto = webDtos.get(0);
		apply(orgWorkflowService, workflowVersionService, emailTemplateService, signLogService,
				executor, webDto.getProcessType(), webDto.getApplyNo(),
				webDto.getApplyDescription(), webDto.getExtraData());
	}

	/**
	 * Apply.
	 *
	 * @param orgWorkflowService the org workflow service
	 * @param workflowVersionService the workflow version service
	 * @param emailTemplateService the email template service
	 * @param signLogService the sign log service
	 * @param executor the executor
	 * @param processType the process type
	 * @param applyNo the apply no
	 * @param applyDescription the apply description
	 * @param extraData the extra data
	 * @throws WorkflowException the workflow exception
	 */
	public static void apply(final OrgWorkflowService orgWorkflowService,
			final WorkflowVersionService workflowVersionService,
			final EmailTemplateService emailTemplateService, final SignLogService signLogService,
			final UserDTO executor, String processType, String applyNo, String applyDescription,
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
		data.put("restFailHandler", AppConstants.WORKFLOW_FAIL_HANDLER);
		data.put("restFailHandlerTaskName", AppConstants.WORKFLOW_FAIL_HANDLER_TASK_NAME);
		final String restUrl = AppConstants.NOMS_HOST + "/noms/flowActionRest?type=" + processType
				+ "&docNo=" + applyNo + "&action=";
		data.put("restApprovalUrl", restUrl + AppConstants.WORKFLOW_REST_APPROVAL);
		data.put("restRejectUrl", restUrl + AppConstants.WORKFLOW_REST_REJECT);

		if (extraData != null) {
			data.putAll(extraData);
		}
		BaseProcessVariables vars = ProcessTypeExecutor.valueOf(processType).getVariablesObject(
				orgWorkflowService, emailTemplateService, executor, data);

		WorkflowRestFacade engine = getEngine(version.getDeploymentId(), executor);
		try {
			engine.startProcess(version.getProcessId(), vars.toVariables());
			log.debug("processType:" + vars.getProcessType() + ", applyNo:" + vars.getApplyNo()
					+ ", emailTodo:" + vars.getMailContent()[0] + ", emailEverReject:"
					+ vars.getEverVerifyRejectMailContent()[0]);
		} catch (RemoteCommunicationException rce) {
			// 因為有可能read timed out 錯誤訊息(response較過慢), 但jbpm其實已啟動好流程,
			// 所以再去檢查流程是否已經產生, 若有則不拋出例外.
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// nothing
			}
			List<Long> activePi = engine.findActiveProcessInstanceIdsByProcessTypeAndApplyNo(
					processType, applyNo);
			if (activePi.isEmpty()) {
				throw new WorkflowException(rce);
			}
		}
	}

	/**
	 * Verify.
	 *
	 * @param orgWorkflowService the org workflow service
	 * @param workflowVersionService the workflow version service
	 * @param signLogService the sign log service
	 * @param executor the executor
	 * @param webDtos the web dtos
	 * @param result the result
	 * @throws WorkflowException the workflow exception
	 */
	public static void verify(final OrgWorkflowService orgWorkflowService,
			final WorkflowVersionService workflowVersionService,
			final SignLogService signLogService, final UserDTO executor,
			final List<WorkflowWebDTO> webDtos, String result) throws WorkflowException {

		verify(orgWorkflowService, workflowVersionService, signLogService, executor, webDtos,
				result, false);
	}

	/**
	 * Agent verify.
	 *
	 * @param orgWorkflowService the org workflow service
	 * @param workflowVersionService the workflow version service
	 * @param signLogService the sign log service
	 * @param executor the executor
	 * @param webDtos the web dtos
	 * @param result the result
	 * @throws WorkflowException the workflow exception
	 */
	public static void agentVerify(final OrgWorkflowService orgWorkflowService,
			final WorkflowVersionService workflowVersionService,
			final SignLogService signLogService, final UserDTO executor,
			final List<WorkflowWebDTO> webDtos, String result) throws WorkflowException {

		verify(orgWorkflowService, workflowVersionService, signLogService, executor, webDtos,
				result, true);
	}

	/**
	 * Verify.
	 *
	 * @param orgWorkflowService the org workflow service
	 * @param workflowVersionService the workflow version service
	 * @param signLogService the sign log service
	 * @param executor the executor
	 * @param webDtos the web dtos
	 * @param result the result
	 * @param isAgent the is agent
	 * @throws WorkflowException the workflow exception
	 */
	private static void verify(final OrgWorkflowService orgWorkflowService,
			final WorkflowVersionService workflowVersionService,
			final SignLogService signLogService, final UserDTO executor,
			final List<WorkflowWebDTO> webDtos, String result, boolean isAgent)
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
			log.error(ExceptionUtils.getFullStackTrace(e));
			throw new WorkflowException(e.getLocalizedMessage());
		}
		signLogService.insertSignLog(webDto, result);
	}

	/**
	 * Batch verify.
	 *
	 * @param orgWorkflowService the org workflow service
	 * @param workflowVersionService the workflow version service
	 * @param signLogService the sign log service
	 * @param executor the executor
	 * @param webDtos the web dtos
	 * @param result the result
	 * @throws WorkflowException the workflow exception
	 */
	public static void batchVerify(final OrgWorkflowService orgWorkflowService,
			final WorkflowVersionService workflowVersionService,
			final SignLogService signLogService, final UserDTO executor,
			final List<WorkflowWebDTO> webDtos, String result) throws WorkflowException {

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
		Map<Long, Boolean> resultMap = engine.batchCheckIn(taskIds, executor.getUsername(), result,
				webDto.getComment(), extraData);

		Map<Long, WorkflowWebDTO> successMap = getTaskIdWorkflowWebDtoMapFromCheckInSuccess(
				webDtos, resultMap);

		signLogService.insertSignLogList(successMap.values(), result);

		if (successMap.size() == webDtos.size()) {
			return;
		}
		Map<Long, WorkflowWebDTO> failMap = getTaskIdWorkflowWebDtoMapFromCheckInFail(webDtos,
				resultMap);

		StringBuffer failMsg = new StringBuffer();
		for (Map.Entry<Long, WorkflowWebDTO> entry : failMap.entrySet()) {
			long taskId = entry.getKey();
			failMsg.append("taskId = ").append(taskId).append(" check in fail").append("\n");
		}
		if (failMsg.length() > 0) {
			String msg = failMsg.toString();
			log.error(msg);
			throw new WorkflowException(msg);
		}

	}

	/**
	 * Agent batch verify.
	 *
	 * @param orgWorkflowService the org workflow service
	 * @param workflowVersionService the workflow version service
	 * @param signLogService the sign log service
	 * @param executor the executor
	 * @param webDtos the web dtos
	 * @param result the result
	 * @throws WorkflowException the workflow exception
	 */
	public static void agentBatchVerify(final OrgWorkflowService orgWorkflowService,
			final WorkflowVersionService workflowVersionService,
			final SignLogService signLogService, final UserDTO executor,
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
		Map<Long, Boolean> resultMap = executorEngine.batchCheckIn(taskIds, executor.getUsername(),
				result, webDto.getComment(), extraData);

		Map<Long, WorkflowWebDTO> successMap = getTaskIdWorkflowWebDtoMapFromCheckInSuccess(
				webDtos, resultMap);

		signLogService.insertSignLogList(successMap.values(), result);

		if (successMap.size() == webDtos.size()) {
			return;
		} else {
			doAgentVerifyFail(orgWorkflowService, webDtos, successMap, resultMap, cacheUserDtoMap,
					executor, version);
		}
	}

	/**
	 * Do agent verify fail.
	 *
	 * @param orgWorkflowService the org workflow service
	 * @param webDtos the web dtos
	 * @param successMap the success map
	 * @param resultMap the result map
	 * @param cacheUserDtoMap the cache user dto map
	 * @param executor the executor
	 * @param version the version
	 * @throws WorkflowException the workflow exception
	 */
	private static void doAgentVerifyFail(final OrgWorkflowService orgWorkflowService,
			final List<WorkflowWebDTO> webDtos, final Map<Long, WorkflowWebDTO> successMap,
			final Map<Long, Boolean> resultMap, final Map<String, UserDTO> cacheUserDtoMap,
			final UserDTO executor, final WorkflowVersionDTO version) throws WorkflowException {

		Map<Long, WorkflowWebDTO> failMap = getTaskIdWorkflowWebDtoMapFromCheckInFail(webDtos,
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

	/**
	 * Delegate rollback.
	 *
	 * @param version the version
	 * @param taskId the task id
	 * @param ownerUser the owner user
	 * @param targetUser the target user
	 * @throws WorkflowException the workflow exception
	 */
	public static void delegateRollback(final WorkflowVersionDTO version, long taskId,
			final UserDTO ownerUser, final UserDTO targetUser) throws WorkflowException {
		delegate(version, taskId, targetUser, ownerUser);
	}

	/**
	 * Delegate.
	 *
	 * @param version the version
	 * @param taskId the task id
	 * @param ownerUser the owner user
	 * @param targetUser the target user
	 * @throws WorkflowException the workflow exception
	 */
	public static void delegate(final WorkflowVersionDTO version, long taskId,
			final UserDTO ownerUser, final UserDTO targetUser) throws WorkflowException {
		WorkflowRestFacade ownEngine = getEngine(version.getDeploymentId(), ownerUser);
		ownEngine.delegate(taskId, ownerUser.getUsername(), targetUser.getUsername());
	}

	/**
	 * Gets the process type workflow version map.
	 *
	 * @param workflowVersionService the workflow version service
	 * @param webDtos the web dtos
	 * @return the process type workflow version map
	 */
	public static Map<String, WorkflowVersionDTO> getProcessTypeWorkflowVersionMap(
			final WorkflowVersionService workflowVersionService,
			final Collection<WorkflowWebDTO> webDtos) {

		Set<String> processTypes = new HashSet<>();
		for (WorkflowWebDTO data : webDtos) {
			processTypes.add(data.getProcessType());
		}
		List<WorkflowVersionDTO> versions = workflowVersionService
				.findActiveWorkflowVersionByProcessTypes(processTypes);

		Map<String, WorkflowVersionDTO> processTypeVersionMap = new HashMap<>(processTypes.size());
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

	/**
	 * Gets the workflow version.
	 *
	 * @param workflowVersionService the workflow version service
	 * @param processType the process type
	 * @return the workflow version
	 * @throws WorkflowException the workflow exception
	 */
	public static WorkflowVersionDTO getWorkflowVersion(
			final WorkflowVersionService workflowVersionService, String processType)
			throws WorkflowException {
		return getWorkflowVersions(workflowVersionService, processType).get(0);
	}

	/**
	 * Gets the workflow versions.
	 *
	 * @param workflowVersionService the workflow version service
	 * @param processType the process type
	 * @return the workflow versions
	 * @throws WorkflowException the workflow exception
	 */
	public static List<WorkflowVersionDTO> getWorkflowVersions(
			final WorkflowVersionService workflowVersionService, String processType)
			throws WorkflowException {

		List<WorkflowVersionDTO> version = null;
		if (StringUtils.isNotBlank(processType)) {
			version = workflowVersionService.findActiveWorkflowVersionByProcessType(processType);
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

	/**
	 * Gets the task id workflow web dto map from check in success.
	 *
	 * @param webDtos the web dtos
	 * @param resultMap the result map
	 * @return the task id workflow web dto map from check in success
	 */
	public static Map<Long, WorkflowWebDTO> getTaskIdWorkflowWebDtoMapFromCheckInSuccess(
			final Collection<WorkflowWebDTO> webDtos, final Map<Long, Boolean> resultMap) {
		return getTaskIdWorkflowWebDtoMapFromCheckInResult(webDtos, resultMap, true);
	}

	/**
	 * Gets the task id workflow web dto map from check in fail.
	 *
	 * @param webDtos the web dtos
	 * @param resultMap the result map
	 * @return the task id workflow web dto map from check in fail
	 */
	public static Map<Long, WorkflowWebDTO> getTaskIdWorkflowWebDtoMapFromCheckInFail(
			final Collection<WorkflowWebDTO> webDtos, final Map<Long, Boolean> resultMap) {
		return getTaskIdWorkflowWebDtoMapFromCheckInResult(webDtos, resultMap, false);
	}

	/**
	 * Gets the task id workflow web dto map from check in result.
	 *
	 * @param webDtos the web dtos
	 * @param resultMap the result map
	 * @param resultType the result type
	 * @return the task id workflow web dto map from check in result
	 */
	private static Map<Long, WorkflowWebDTO> getTaskIdWorkflowWebDtoMapFromCheckInResult(
			final Collection<WorkflowWebDTO> webDtos, final Map<Long, Boolean> resultMap,
			boolean resultType) {
		Map<Long, WorkflowWebDTO> successMap = new HashMap<>();
		for (Map.Entry<Long, Boolean> entry : resultMap.entrySet()) {
			if (entry.getValue() == resultType) {
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

	/**
	 * Validate argument.
	 *
	 * @param executor the executor
	 * @param webDtos the web dtos
	 * @throws WorkflowException the workflow exception
	 */
	public static void validateArgument(final UserDTO executor,
			final Collection<WorkflowWebDTO> webDtos) throws WorkflowException {
		validateExecutor(executor);
		validateWorkflowWebDTOs(webDtos);
	}

	/**
	 * Validate executor.
	 *
	 * @param executor the executor
	 * @throws WorkflowException the workflow exception
	 */
	public static void validateExecutor(final UserDTO executor) throws WorkflowException {
		if (executor == null) {
			throw new WorkflowException("Executor is null.");
		}
		if (StringUtils.isBlank(executor.getUsername())) {
			throw new WorkflowException("Executor name is blank.");
		}
	}

	/**
	 * Validate list of WorkflowWebDTO.
	 *
	 * @param webDtos the web dtos
	 * @throws WorkflowException the workflow exception
	 */
	public static void validateWorkflowWebDTOs(final Collection<WorkflowWebDTO> webDtos)
			throws WorkflowException {
		if (webDtos == null) {
			throw new WorkflowException("Action web data is null.");
		}
		if (webDtos.isEmpty()) {
			throw new WorkflowException("Action web data is empty.");
		}
	}
}
