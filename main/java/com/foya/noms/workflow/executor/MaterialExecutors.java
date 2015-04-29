package com.foya.noms.workflow.executor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.foya.noms.dto.auth.UserDTO;
import com.foya.noms.dto.org.DeptPosDTO;
import com.foya.noms.enums.WorkflowEmailType;
import com.foya.noms.service.common.EmailTemplateService;
import com.foya.noms.service.org.OrgWorkflowService;
import com.foya.workflow.exception.WorkflowException;
import com.foya.workflow.model.BaseProcessVariables;

public class MaterialExecutors extends AbstractExecutor {
	private MaterialVariables variables;

	public MaterialExecutors(final OrgWorkflowService orgService,
			final EmailTemplateService emailTemplateService, final UserDTO executor,
			final Map<String, Object> extraData) {
		super(orgService, emailTemplateService, executor, extraData);
		variables = new MaterialVariables();
		variables.setVariables(extraData);
		variables.setApplicantDeptMgr(SKIP_TASK_FLAG);
		variables.setApplicantDivisionMgr(SKIP_TASK_FLAG);
		variables.setLogisticsDeptMgr(SKIP_TASK_FLAG);
		variables.setLogisticsDivisionMgr(SKIP_TASK_FLAG);
	}

	@Override
	public BaseProcessVariables getVariables() {
		return variables;
	}

	public MaterialExecutors addMailVariables(Collection<WorkflowEmailType> mailTypes) {
		Map<String, Object> parserMap = new HashMap<>();
		parserMap.putAll(extraData);
		parserMap.putAll(variables.toVariables());
		setMailVariableFromTemplates(mailTypes, variables, parserMap);
		return this;
	}

	public MaterialExecutors addApplicantDeptMgr() throws WorkflowException {
		DeptPosDTO deptPosDto = findApplicantDeptMgr();
		if (deptPosDto != null) {
			variables.setApplicantDeptMgrTaskName(deptPosDto.getPosName());
			variables.setApplicantDeptMgr(deptPosDto.getDeptPosId());
		}

		String worker = variables.getApplicantDeptMgr();
		if (StringUtils.isBlank(worker) || SKIP_TASK_FLAG.equals(worker)) {
			throw new WorkflowException("查無申請單位之部門主管簽核職稱");
			// Applicant department manager is blank.
		}
		return this;
	}

	public MaterialExecutors addApplicantDivisionMgr() throws WorkflowException {
		DeptPosDTO deptPosDto = findApplicantDivisionMgr();
		if (deptPosDto != null) {
			variables.setApplicantDivisionMgrTaskName(deptPosDto.getPosName());
			variables.setApplicantDivisionMgr(deptPosDto.getDeptPosId());
		}

		String worker = variables.getApplicantDivisionMgr();
		if (StringUtils.isBlank(worker) || SKIP_TASK_FLAG.equals(worker)) {
			throw new WorkflowException("查無申請單位之處長簽核職稱");
			// Applicant division manager is blank.
		}
		return this;
	}

	public MaterialExecutors addLogisticsDeptMgr() throws WorkflowException {
		DeptPosDTO deptPosDto = findDeptMgr(logisticsDeptId);
		if (deptPosDto != null) {
			variables.setLogisticsDeptMgrTaskName(deptPosDto.getPosName());
			variables.setLogisticsDeptMgr(deptPosDto.getDeptPosId());
		}

		String worker = variables.getApplicantDeptMgr();
		if (StringUtils.isBlank(worker) || SKIP_TASK_FLAG.equals(worker)) {
			throw new WorkflowException("查無後勤管理部之部主管簽核職稱");
			// Logistics department manager is blank.
		}
		return this;
	}

	public MaterialExecutors addLogisticsDivisionMgr() throws WorkflowException {
		DeptPosDTO deptPosDto = findDivisionMgr(logisticsDeptId);
		if (deptPosDto != null) {
			variables.setLogisticsDivisionMgrTaskName(deptPosDto.getPosName());
			variables.setLogisticsDivisionMgr(deptPosDto.getDeptPosId());
		}

		String worker = variables.getLogisticsDivisionMgr();
		if (StringUtils.isBlank(worker) || SKIP_TASK_FLAG.equals(worker)) {
			throw new WorkflowException("查無後勤管理部之處長簽核職稱");
			// Logistics division manager is blank.
		}
		return this;
	}

}
