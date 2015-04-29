package com.foya.noms.service.profile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foya.dao.mybatis.model.TbAuthMenu;
import com.foya.dao.mybatis.model.TbAuthPersonnel;
import com.foya.dao.mybatis.model.TbOrgAgent;
import com.foya.dao.mybatis.model.TbWorkflowCfg;
import com.foya.exception.NomsException;
import com.foya.noms.dto.auth.UserDTO;
import com.foya.noms.dto.workflow.WorkflowTodoDTO;
import com.foya.noms.service.BaseService;
import com.foya.noms.service.auth.PersonnelService;
import com.foya.noms.service.org.AgentService;
import com.foya.noms.service.workflow.WorkflowCfgService;
import com.foya.noms.workflow.WorkflowActionMethod;
import com.foya.workflow.exception.WorkflowException;
import com.foya.workflow.model.TodoInfo;
import com.foya.workflow.rest.WorkflowRestFacade;
import com.foya.workflow.util.MultiThreadHandler;

@Service
public class ProfileService extends BaseService {
	private static final String TASK_NAME_COUNTERSIGN = "\"countersign;\"";
	private static final String TASK_NAME_NOTIFY = "\"notify;\"";
	private static final String TASK_NAME_COUNTERSIGN_TW = "加簽";
	private static final String TASK_NAME_NOTIFY_TW = "會簽";

	@Autowired
	AgentService agentService;

	@Autowired
	PersonnelService personnelService;

	@Autowired
	WorkflowCfgService workflowCfgService;

	public List<WorkflowTodoDTO> getOwnTodoList(UserDTO executor) throws WorkflowException {
		WorkflowRestFacade engine = WorkflowActionMethod.getEngine(executor);
		List<WorkflowTodoDTO> todoDtos = convertTodoDTOs(engine.findTodoList());
		return todoDtos;
	}

	public List<WorkflowTodoDTO> getAgentTodoList(UserDTO executor) throws WorkflowException {
		List<TbOrgAgent> users = agentService.findActiveUsersByAgentUserNo(executor.getUsername());
		Set<String> userNos = new HashSet<>();
		for (TbOrgAgent user : users) {
			String userNo = user.getPSN_NO();
			userNos.add(userNo);
		}
		List<TbAuthPersonnel> persons = personnelService.findPsnByNos(userNos);
		List<WorkflowRestFacade> workflowRestAPIs = createRemoteEngines(persons);
		List<TodoInfo> todoInfos = getTodoListForAgent(workflowRestAPIs);
		List<WorkflowTodoDTO> todoDtos = convertTodoDTOs(todoInfos);
		return todoDtos;
	}

	private List<WorkflowRestFacade> createRemoteEngines(List<TbAuthPersonnel> persons)
			throws WorkflowException {
		List<WorkflowRestFacade> engines = new ArrayList<>(persons.size());
		for (TbAuthPersonnel person : persons) {
			if (StringUtils.isBlank(person.getPSN_NO())) {
				continue;
			}
			UserDTO executor = new UserDTO();
			executor.setUsername(person.getPSN_NO());
			executor.setPassword(person.getPSN_PWD());
			engines.add(WorkflowActionMethod.getEngine(executor));
		}
		return engines;
	}

	private List<TodoInfo> getTodoListForAgent(List<WorkflowRestFacade> workflowRestAPIs) {
		final Set<TodoInfo> todoInfos = new HashSet<>();
		List<Callable<Boolean>> workers = new ArrayList<>(workflowRestAPIs.size());
		for (final WorkflowRestFacade rest : workflowRestAPIs) {
			Callable<Boolean> worker = null;
			worker = new Callable<Boolean>() {
				@Override
				public Boolean call() throws Exception {
					todoInfos.addAll(rest.findTodoList());
					return true;
				}
			};
			workers.add(worker);
		}
		try {
			MultiThreadHandler.executeMultiThread(3, workers);
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
		return new ArrayList<>(todoInfos);
	}

	private List<WorkflowTodoDTO> convertTodoDTOs(Collection<TodoInfo> todoInfoList) {
		if (todoInfoList == null) {
			throw new NomsException("todoInfoList is null");
		}
		List<WorkflowTodoDTO> todoDtoList = new ArrayList<>(todoInfoList.size());
		Map<String, String> numberNameMap = new HashMap<>();
		Map<String, String> typeNameMap = new HashMap<>();
		Map<String, String> typeAllowBatchNameMap = new HashMap<>();
		setMapFromTodoInfos(todoInfoList, numberNameMap, typeNameMap, typeAllowBatchNameMap);
		for (TodoInfo info : todoInfoList) {
			WorkflowTodoDTO dto = new WorkflowTodoDTO(info);
			setApplicatNameToTodoDto(numberNameMap, dto);
			setProcessNameToTodoDto(typeNameMap, dto);
			setRenameTaskName(dto);
			setAllowBatchToTodoDto(typeAllowBatchNameMap, dto);
			todoDtoList.add(dto);
		}

		return todoDtoList;
	}

	private void setApplicatNameToTodoDto(Map<String, String> numberNameMap,
			WorkflowTodoDTO workflowTodoDto) {
		String applicantId = workflowTodoDto.getApplicantId();
		workflowTodoDto.setApplicantName(numberNameMap.get(applicantId));
	}

	private void setProcessNameToTodoDto(Map<String, String> typeNameMap,
			WorkflowTodoDTO workflowTodoDto) {
		String processType = workflowTodoDto.getProcessType();
		workflowTodoDto.setProcessName(typeNameMap.get(processType));
	}

	private void setAllowBatchToTodoDto(Map<String, String> typeAllowBatchNameMap,
			WorkflowTodoDTO workflowTodoDto) {
		String processType = workflowTodoDto.getProcessType();
		workflowTodoDto.setAllowBatch(typeAllowBatchNameMap.get(processType));
	}

	private void setRenameTaskName(WorkflowTodoDTO workflowTodoDto) {

		String taskName = workflowTodoDto.getTaskName();
		if (StringUtils.isNotBlank(taskName)) {
			workflowTodoDto.setTaskName(taskName.replaceAll(TASK_NAME_COUNTERSIGN,
					TASK_NAME_COUNTERSIGN_TW).replaceAll(TASK_NAME_NOTIFY, TASK_NAME_NOTIFY_TW));
		}
	}

	private void setMapFromTodoInfos(Collection<TodoInfo> todoInfoList,
			Map<String, String> numberNameMap, Map<String, String> typeNameMap,
			Map<String, String> typeAllowBatchNameMap) {
		Set<String> processTypes = new HashSet<>();
		Set<String> applicantIds = new HashSet<>();
		for (TodoInfo info : todoInfoList) {
			applicantIds.add(info.getApplicantId());
			processTypes.add(info.getProcessType());
		}
		List<TbAuthPersonnel> personnels = personnelService.findPsnByNos(applicantIds);
		for (TbAuthPersonnel personnel : personnels) {
			numberNameMap.put(personnel.getPSN_NO(), personnel.getCHN_NM());
		}
		List<TbWorkflowCfg> workflowCfgs = workflowCfgService
				.findWorkflowCfgByProcessTypes(processTypes);
		for (TbWorkflowCfg workflowCfg : workflowCfgs) {
			typeNameMap.put(workflowCfg.getPROCESS_TYPE(), workflowCfg.getPROCESS_NAME());
			typeAllowBatchNameMap.put(workflowCfg.getPROCESS_TYPE(), workflowCfg.getALLOW_BATCH());
		}
	}

	/**
	 * 查詢登入者所擁有的權限
	 * 
	 * @param psnId 登入者ID
	 * @return List<TbAuthMenu>
	 */
	public List<TbAuthMenu> getLimitsOfAuthority(int psnId) {

		return personnelService.getLimitsOfAuthority(psnId);
	}

}
