package com.foya.noms.service.workflow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.kie.api.task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foya.noms.dto.auth.UserDTO;
import com.foya.noms.dto.common.WorkflowVersionDTO;
import com.foya.noms.dto.org.DeptPosDTO;
import com.foya.noms.dto.workflow.WorkflowTodoDTO;
import com.foya.noms.dto.workflow.WorkflowWebDTO;
import com.foya.noms.service.BaseService;
import com.foya.noms.service.common.EmailTemplateService;
import com.foya.noms.service.org.OrgWorkflowService;
import com.foya.noms.service.system.SignLogService;
import com.foya.noms.util.DateUtils;
import com.foya.noms.workflow.WorkflowAction;
import com.foya.noms.workflow.WorkflowActionMethod;
import com.foya.workflow.exception.WorkflowException;
import com.foya.workflow.rest.WorkflowRestFacade;

@Service
public class WorkflowActionService extends BaseService {

	@Autowired
	private OrgWorkflowService orgWorkflowService;
	@Autowired
	private WorkflowVersionService workflowVersionService;
	@Autowired
	private EmailTemplateService emailTemplateService;
	@Autowired
	private SignLogService signLogService;

	/**
	 * 申請流程.
	 *
	 * @param processType 流程類型
	 * @param applyNo 申請單號
	 * @param applyDescription 申請事由
	 * @throws WorkflowException the workflow exception
	 */
	public void apply(String processType, String applyNo, String applyDescription)
			throws WorkflowException {
		apply(processType, applyNo, applyDescription, null);
	}

	/**
	 * 申請流程.
	 *
	 * @param processType 流程類型
	 * @param applyNo 申請單號
	 * @param applyDescription 申請事由
	 * @param extraData 額外資料
	 * @throws WorkflowException the workflow exception
	 */

	public void apply(String processType, String applyNo, String applyDescription,
			Map<String, Object> extraData) throws WorkflowException {
		WorkflowWebDTO webDto = new WorkflowWebDTO();
		webDto.setProcessType(processType);
		webDto.setApplyNo(applyNo);
		webDto.setApplyDescription(applyDescription);
		webDto.setExtraData(extraData);
		List<WorkflowWebDTO> webDtos = new ArrayList<WorkflowWebDTO>(1);
		webDtos.add(webDto);
		WorkflowAction.APPLY.execute(orgWorkflowService, workflowVersionService,
				emailTemplateService, signLogService, getLoginUser(), webDtos);
	}

	/**
	 * Find process type and name.
	 *
	 * @return the map
	 */
	public Map<String, String> findProcessTypeName() {
		List<WorkflowVersionDTO> versions = workflowVersionService.findActiveWorkflowVersion();
		Map<String, String> map = new HashMap<>(versions.size());
		for (WorkflowVersionDTO version : versions) {
			map.put(version.getProcessType(), version.getProcessName());
		}

		return sortByValue(map);
	}

	private <K, V extends Comparable<? super V>> Map<K, V> sortByValue(final Map<K, V> map) {
		List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
			@Override
			public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});

		Map<K, V> result = new LinkedHashMap<>(map.size());
		for (Map.Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}

	/**
	 * Query.
	 *
	 * @param processType
	 * @param applyNo
	 * @return the list
	 * @throws WorkflowException the workflow exception
	 */
	public List<WorkflowTodoDTO> query(String processType, String applyNo) throws WorkflowException {
		UserDTO executor = getLoginUser();
		WorkflowActionMethod.validateExecutor(executor);
		WorkflowVersionDTO version = WorkflowActionMethod.getWorkflowVersion(
				workflowVersionService, processType);
		WorkflowRestFacade engine = WorkflowActionMethod.getEngine(version.getDeploymentId(),
				executor);
		List<Long> pids = null;
		if (StringUtils.isNotBlank(processType) && StringUtils.isNotBlank(applyNo)) {
			pids = engine.findActiveProcessInstanceIdsByProcessTypeAndApplyNo(processType, applyNo);
		} else if (StringUtils.isNotBlank(processType) && StringUtils.isBlank(applyNo)) {
			pids = engine.findActiveProcessInstanceIdsByProcessType(processType);
		} else if (StringUtils.isBlank(processType) && StringUtils.isNotBlank(applyNo)) {
			pids = engine.findActiveProcessInstanceIdsByApplyNo(applyNo);
		} else {
			pids = new ArrayList<>();
			List<WorkflowVersionDTO> versionDtos = workflowVersionService
					.findActiveWorkflowVersion();
			for (WorkflowVersionDTO versionDto : versionDtos) {
				pids.addAll(engine.findActiveProcessInstanceIdsByProcessType(versionDto
						.getProcessType()));
			}
		}

		return getWorkflowTodoDTOs(engine, version, pids);
	}

	private List<WorkflowTodoDTO> getWorkflowTodoDTOs(WorkflowRestFacade engine,
			WorkflowVersionDTO version, List<Long> pids) {
		List<WorkflowTodoDTO> todoList = new ArrayList<>();
		Map<String, Set<UserDTO>> deptPosUserCache = new HashMap<>();
		Map<String, UserDTO> userCache = new HashMap<>();
		Map<String, String> deptIdNameCache = new HashMap<>();
		TaskService taskservice = engine.getTaskService();
		for (Long pid : pids) {
			List<Long> taskIds = taskservice.getTasksByProcessInstanceId(pid);
			// not active user task
			if (taskIds == null) {
				continue;
			}
			for (Long taskId : taskIds) {
				Map<String, Object> taskMap = engine.getTaskVariables(taskId);
				WorkflowTodoDTO todo = new WorkflowTodoDTO();
				todo.setTaskId(taskId);
				todo.setTaskStartTime(taskservice.getTaskById(taskId).getTaskData()
						.getActivationTime());
				todo.setTaskName(String.valueOf(taskMap.get("NodeName")));
				todo.setApplicantId(String.valueOf(taskMap.get("applicantId")));
				todo.setApplyNo(String.valueOf(taskMap.get("applyNo")));
				todo.setProcessType(String.valueOf(taskMap.get("processType")));
				String deptPosId = String.valueOf(taskMap.get("GroupId"));
				todo.setTaskOwnerGroupId(deptPosId);
				todo.setTaskOwnerDeptName(getDeptNameByDeptPosId(deptIdNameCache, deptPosId));

				Set<UserDTO> userDtos = getUsersByDeptPosId(deptPosUserCache, deptPosId);

				if (userDtos.isEmpty()) {
					String actorId = String.valueOf(taskMap.get("ActorId"));
					if (StringUtils.isNotBlank(actorId)) {
						UserDTO userDto = getUserByUserNo(userCache, actorId);
						todo.setTaskOwnerId(userDto.getEnName());
						todo.setTaskOwnerName(userDto.getChName());
					}
				} else {
					StringBuffer sbEnName = new StringBuffer();
					StringBuffer sbChnName = new StringBuffer();
					for (UserDTO userDto : userDtos) {
						sbEnName.append(userDto.getEnName()).append(",");
						sbChnName.append(userDto.getChName()).append(",");
					}
					if (sbEnName.length() > 0) {
						sbEnName.deleteCharAt(sbEnName.length() - 1);
					}
					if (sbChnName.length() > 0) {
						sbChnName.deleteCharAt(sbChnName.length() - 1);
					}
					todo.setTaskOwnerId(sbEnName.toString());
					todo.setTaskOwnerName(sbChnName.toString());
				}
				todo.setApplyDate(DateUtils.parseDateObject(taskMap.get("applyDate")));
				todo.setUserTaskType(String.valueOf(taskMap.get("userTaskType")));
				try {
					String[] applyDescriptionArr = (String[]) taskMap.get("applyDescription");
					todo.setApplyDescription(applyDescriptionArr[0]);
				} catch (Exception e) {
					todo.setApplyDescription("");
				}
				todo.setProcessName(version.getProcessName());
				todoList.add(todo);
			}
		}
		return todoList;
	}

	private String getDeptNameByDeptPosId(Map<String, String> cacheMap, String deptPosId) {
		String deptName = cacheMap.get(deptPosId);
		if (deptName == null) {
			List<DeptPosDTO> deptPosDtos = orgWorkflowService.getDeptPosDtoByDeptPosId(deptPosId);
			if (deptPosDtos != null && !deptPosDtos.isEmpty()) {
				DeptPosDTO deptPosDto = deptPosDtos.get(0);
				deptName = deptPosDto.getDeptName();
				cacheMap.put(deptPosId, deptName);
			}
		}
		return deptName;
	}

	private Set<UserDTO> getUsersByDeptPosId(Map<String, Set<UserDTO>> cacheMap, String deptPosId) {
		Set<UserDTO> userDtos = cacheMap.get(deptPosId);
		if (userDtos == null) {
			userDtos = new HashSet<>();
			List<UserDTO> userDtosFromDb = orgWorkflowService.getUserByDeptPosId(deptPosId);
			for (UserDTO userDto : userDtosFromDb) {
				userDtos.add(userDto);
			}
			cacheMap.put(deptPosId, userDtos);
		}
		return userDtos;
	}

	private UserDTO getUserByUserNo(Map<String, UserDTO> cacheMap, String userNo) {
		UserDTO userDto = cacheMap.get(userNo);
		if (userDto == null) {
			userDto = orgWorkflowService.getUserDTO(userNo);
			if (userDto == null) {
				userDto = new UserDTO();
				cacheMap.put(userNo, userDto);
			}
			cacheMap.put(userNo, userDto);
		}
		return userDto;
	}

	/**
	 * 執行流程動作.
	 *
	 * @param action 執行動作
	 * @param webDtos 從前端傳遞的資料 list
	 * @throws WorkflowException the workflow exception
	 */
	public void execute(String action, List<WorkflowWebDTO> webDtos) throws WorkflowException {
		WorkflowAction.valueOf(action).execute(orgWorkflowService, workflowVersionService,
				emailTemplateService, signLogService, getLoginUser(), webDtos);
	}

}
