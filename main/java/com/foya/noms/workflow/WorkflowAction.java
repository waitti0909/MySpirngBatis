package com.foya.noms.workflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.foya.noms.dto.auth.UserDTO;
import com.foya.noms.dto.common.WorkflowVersionDTO;
import com.foya.noms.dto.workflow.WorkflowWebDTO;
import com.foya.noms.service.common.EmailTemplateService;
import com.foya.noms.service.org.OrgWorkflowService;
import com.foya.noms.service.system.SignLogService;
import com.foya.noms.service.workflow.WorkflowVersionService;
import com.foya.workflow.exception.WorkflowException;
import com.foya.workflow.rest.WorkflowRestFacade;
import com.foya.workflow.util.MultiThreadHandler;

public enum WorkflowAction {
	APPLY {
		@Override
		public void execute(final OrgWorkflowService orgWorkflowService,
				final WorkflowVersionService workflowVersionService,
				final EmailTemplateService emailTemplateService,
				final SignLogService signLogService, final UserDTO executor,
				List<WorkflowWebDTO> webDtos) throws WorkflowException {

			WorkflowActionMethod.apply(orgWorkflowService, workflowVersionService,
					emailTemplateService, signLogService, executor, webDtos);
		}

	},
	APPROVAL {
		@Override
		public void execute(final OrgWorkflowService orgWorkflowService,
				final WorkflowVersionService workflowVersionService,
				final EmailTemplateService emailTemplateService,
				final SignLogService signLogService, final UserDTO executor,
				final List<WorkflowWebDTO> webDtos) throws WorkflowException {

			WorkflowActionMethod.verify(orgWorkflowService, workflowVersionService, signLogService,
					executor, webDtos, RESULT_APPROVE);
		}
	},
	REJECT {
		@Override
		public void execute(final OrgWorkflowService orgWorkflowService,
				final WorkflowVersionService workflowVersionService,
				final EmailTemplateService emailTemplateService,
				final SignLogService signLogService, final UserDTO executor,
				final List<WorkflowWebDTO> webDtos) throws WorkflowException {

			WorkflowActionMethod.verify(orgWorkflowService, workflowVersionService, signLogService,
					executor, webDtos, RESULT_REJECT);
		}
	},
	COUNTERSIGN {
		@Override
		public void execute(final OrgWorkflowService orgWorkflowService,
				final WorkflowVersionService workflowVersionService,
				final EmailTemplateService emailTemplateService,
				final SignLogService signLogService, final UserDTO executor,
				final List<WorkflowWebDTO> webDtos) throws WorkflowException {

			WorkflowActionMethod.validateArgument(executor, webDtos);

			WorkflowWebDTO webDto = webDtos.get(0);
			WorkflowVersionDTO version = WorkflowActionMethod.getWorkflowVersion(
					workflowVersionService, webDto.getProcessType());
			WorkflowRestFacade engine = WorkflowActionMethod.getEngine(version.getDeploymentId(),
					executor);

			long taskId = webDto.getTaskId();
			String comment = webDto.getComment();
			Map<String, Object> extraData = new HashMap<>(2);
			extraData.put("jsonData", new String[] { webDto.getJsonData() });
			extraData.put("verifyUserName", executor.getChName());

			engine.countersign(taskId, executor.getUsername(), webDto.getCountersigns(),
					RESULT_COUNTERSIGN, comment, extraData);
			signLogService.insertSignLog(webDto, RESULT_APPROVE);
		}
	},
	NOTIFY {
		@Override
		public void execute(final OrgWorkflowService orgWorkflowService,
				final WorkflowVersionService workflowVersionService,
				final EmailTemplateService emailTemplateService,
				final SignLogService signLogService, final UserDTO executor,
				final List<WorkflowWebDTO> webDtos) throws WorkflowException {

			WorkflowActionMethod.validateArgument(executor, webDtos);

			WorkflowWebDTO webDto = webDtos.get(0);
			WorkflowVersionDTO version = WorkflowActionMethod.getWorkflowVersion(
					workflowVersionService, webDto.getProcessType());
			WorkflowRestFacade engine = WorkflowActionMethod.getEngine(version.getDeploymentId(),
					executor);

			long taskId = webDto.getTaskId();
			String comment = webDto.getComment();

			Map<String, Object> extraData = new HashMap<>(2);
			extraData.put("jsonData", new String[] { webDto.getJsonData() });
			extraData.put("verifyUserName", executor.getChName());

			engine.notify(taskId, executor.getUsername(), webDto.getNotifys(), RESULT_NOTIFY,
					comment, extraData);
			signLogService.insertSignLog(webDto, RESULT_APPROVE);
		}
	},
	ABORT_PROCESS {
		@Override
		public void execute(final OrgWorkflowService orgWorkflowService,
				final WorkflowVersionService workflowVersionService,
				final EmailTemplateService emailTemplateService,
				final SignLogService signLogService, final UserDTO executor,
				final List<WorkflowWebDTO> webDtos) throws WorkflowException {

			WorkflowActionMethod.validateArgument(executor, webDtos);

			WorkflowWebDTO webDto = webDtos.get(0);
			WorkflowVersionDTO version = WorkflowActionMethod.getWorkflowVersion(
					workflowVersionService, webDto.getProcessType());
			WorkflowRestFacade engine = WorkflowActionMethod.getEngine(version.getDeploymentId(),
					executor);

			String applyNo = webDto.getApplyNo();
			String processType = webDto.getProcessType();
			engine.abortRootProcessInstanceByProcessTypeAndApplyNo(processType, applyNo);

		}
	},
	BATCH_APPLY {
		@Override
		public void execute(final OrgWorkflowService orgWorkflowService,
				final WorkflowVersionService workflowVersionService,
				final EmailTemplateService emailTemplateService,
				final SignLogService signLogService, final UserDTO executor,
				final List<WorkflowWebDTO> webDtos) throws WorkflowException {

			WorkflowActionMethod.validateArgument(executor, webDtos);

			Map<String, WorkflowVersionDTO> processTypeVersionMap = WorkflowActionMethod
					.getProcessTypeWorkflowVersionMap(workflowVersionService, webDtos);

			List<Callable<WorkflowWebDTO>> workers = new ArrayList<>(webDtos.size());
			for (final WorkflowWebDTO webDto : webDtos) {
				final String processType = webDto.getProcessType();
				final WorkflowVersionDTO version = processTypeVersionMap.get(processType);
				Callable<WorkflowWebDTO> worker = new Callable<WorkflowWebDTO>() {
					@Override
					public WorkflowWebDTO call() throws Exception {
						if (version != null) {
							WorkflowActionMethod.apply(orgWorkflowService, workflowVersionService,
									emailTemplateService, signLogService, executor, processType,
									webDto.getApplyNo(), webDto.getApplyDescription(),
									webDto.getExtraData());
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
		public void execute(final OrgWorkflowService orgWorkflowService,
				final WorkflowVersionService workflowVersionService,
				final EmailTemplateService emailTemplateService,
				final SignLogService signLogService, final UserDTO executor,
				final List<WorkflowWebDTO> webDtos) throws WorkflowException {

			WorkflowActionMethod.batchVerify(orgWorkflowService, workflowVersionService,
					signLogService, executor, webDtos, RESULT_APPROVE);
		}
	},
	BATCH_REJECT {
		@Override
		public void execute(final OrgWorkflowService orgWorkflowService,
				final WorkflowVersionService workflowVersionService,
				final EmailTemplateService emailTemplateService,
				final SignLogService signLogService, final UserDTO executor,
				final List<WorkflowWebDTO> webDtos) throws WorkflowException {

			WorkflowActionMethod.batchVerify(orgWorkflowService, workflowVersionService,
					signLogService, executor, webDtos, RESULT_REJECT);
		}
	},
	// TODO test not yet
	BATCH_ABORT_PROCESS {
		@Override
		public void execute(final OrgWorkflowService orgWorkflowService,
				final WorkflowVersionService workflowVersionService,
				final EmailTemplateService emailTemplateService,
				final SignLogService signLogService, final UserDTO executor,
				final List<WorkflowWebDTO> webDtos) throws WorkflowException {

			WorkflowActionMethod.validateArgument(executor, webDtos);

			final Map<String, WorkflowVersionDTO> processTypeVersionMap = WorkflowActionMethod
					.getProcessTypeWorkflowVersionMap(workflowVersionService, webDtos);

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
						engine.abortRootProcessInstanceByProcessTypeAndApplyNo(processType, applyNo);
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
		public void execute(final OrgWorkflowService orgWorkflowService,
				final WorkflowVersionService workflowVersionService,
				final EmailTemplateService emailTemplateService,
				final SignLogService signLogService, final UserDTO executor,
				final List<WorkflowWebDTO> webDtos) throws WorkflowException {

			WorkflowActionMethod.agentVerify(orgWorkflowService, workflowVersionService,
					signLogService, executor, webDtos, RESULT_APPROVE);
		}
	},
	AGENT_REJECT {
		@Override
		public void execute(final OrgWorkflowService orgWorkflowService,
				final WorkflowVersionService workflowVersionService,
				final EmailTemplateService emailTemplateService,
				final SignLogService signLogService, final UserDTO executor,
				final List<WorkflowWebDTO> webDtos) throws WorkflowException {

			WorkflowActionMethod.agentVerify(orgWorkflowService, workflowVersionService,
					signLogService, executor, webDtos, RESULT_REJECT);
		}
	},
	AGENT_BATCH_APPROVAL {
		@Override
		public void execute(final OrgWorkflowService orgWorkflowService,
				final WorkflowVersionService workflowVersionService,
				final EmailTemplateService emailTemplateService,
				final SignLogService signLogService, final UserDTO executor,
				final List<WorkflowWebDTO> webDtos) throws WorkflowException {

			WorkflowActionMethod.agentBatchVerify(orgWorkflowService, workflowVersionService,
					signLogService, executor, webDtos, RESULT_APPROVE);
		}
	},
	AGENT_BATCH_REJECT {
		@Override
		public void execute(final OrgWorkflowService orgWorkflowService,
				final WorkflowVersionService workflowVersionService,
				final EmailTemplateService emailTemplateService,
				final SignLogService signLogService, final UserDTO executor,
				final List<WorkflowWebDTO> webDtos) throws WorkflowException {

			WorkflowActionMethod.agentBatchVerify(orgWorkflowService, workflowVersionService,
					signLogService, executor, webDtos, RESULT_REJECT);
		}
	},
	AGENT_COUNTERSIGN {
		@Override
		public void execute(final OrgWorkflowService orgWorkflowService,
				final WorkflowVersionService workflowVersionService,
				final EmailTemplateService emailTemplateService,
				final SignLogService signLogService, final UserDTO executor,
				final List<WorkflowWebDTO> webDtos) throws WorkflowException {

			WorkflowActionMethod.validateArgument(executor, webDtos);
			WorkflowWebDTO webDto = webDtos.get(0);
			WorkflowVersionDTO version = WorkflowActionMethod.getWorkflowVersion(
					workflowVersionService, webDto.getProcessType());

			long taskId = webDto.getTaskId();
			UserDTO taskOwner = orgWorkflowService.getUserDTO(webDto.getTaskOwnerId());
			WorkflowActionMethod.delegate(version, taskId, taskOwner, executor);

			WorkflowRestFacade executorEngine = WorkflowActionMethod.getEngine(
					version.getDeploymentId(), executor);
			String comment = webDto.getComment();

			Map<String, Object> extraData = new HashMap<>(2);
			extraData.put("jsonData", new String[] { webDto.getJsonData() });
			extraData.put("verifyUserName", executor.getChName());

			executorEngine.countersign(taskId, executor.getUsername(), webDto.getCountersigns(),
					RESULT_APPROVE, comment, extraData);

			signLogService.insertSignLog(webDto, RESULT_APPROVE);
		}
	},
	AGENT_NOTIFY {
		@Override
		public void execute(final OrgWorkflowService orgWorkflowService,
				final WorkflowVersionService workflowVersionService,
				final EmailTemplateService emailTemplateService,
				final SignLogService signLogService, final UserDTO executor,
				final List<WorkflowWebDTO> webDtos) throws WorkflowException {

			WorkflowActionMethod.validateArgument(executor, webDtos);
			WorkflowWebDTO webDto = webDtos.get(0);
			WorkflowVersionDTO version = WorkflowActionMethod.getWorkflowVersion(
					workflowVersionService, webDto.getProcessType());

			long taskId = webDto.getTaskId();
			UserDTO taskOwner = orgWorkflowService.getUserDTO(webDto.getTaskOwnerId());
			WorkflowActionMethod.delegate(version, taskId, taskOwner, executor);

			WorkflowRestFacade executorEngine = WorkflowActionMethod.getEngine(
					version.getDeploymentId(), executor);
			String comment = webDto.getComment();

			Map<String, Object> extraData = new HashMap<>(2);
			extraData.put("jsonData", new String[] { webDto.getJsonData() });
			extraData.put("verifyUserName", executor.getChName());

			executorEngine.notify(taskId, executor.getUsername(), webDto.getNotifys(),
					RESULT_APPROVE, comment, extraData);

			signLogService.insertSignLog(webDto, RESULT_APPROVE);
		}
	};

	private static Logger log = LoggerFactory.getLogger(WorkflowAction.class);
	public final static String RESULT_APPROVE = "Y";
	public final static String RESULT_REJECT = "N";
	public final static String RESULT_COUNTERSIGN = "C";
	public final static String RESULT_NOTIFY = "O";

	/**
	 * 執行 workflow 特定動作
	 * 
	 * @param orgWorkflowService workflow 用組織 service
	 * @param workflowVersionService workflow 版本 service
	 * @param emailTemplateService email 範本 service
	 * @param signLogService 簽核紀錄 service
	 * @param executor 執行者
	 * @param webDtos 從前端傳遞的資料 list
	 * @throws WorkflowException
	 */
	public abstract void execute(final OrgWorkflowService orgWorkflowService,
			final WorkflowVersionService workflowVersionService,
			final EmailTemplateService emailTemplateService, final SignLogService signLogService,
			final UserDTO executor, final List<WorkflowWebDTO> webDtos) throws WorkflowException;
}
