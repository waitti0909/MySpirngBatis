package com.foya.noms.workflow.executor;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.foya.noms.dto.auth.UserDTO;
import com.foya.noms.dto.org.DeptPosDTO;
import com.foya.noms.enums.WorkflowEmailType;
import com.foya.noms.service.common.EmailTemplateService;
import com.foya.noms.service.org.OrgWorkflowService;
import com.foya.workflow.exception.WorkflowException;
import com.foya.workflow.model.BaseProcessVariables;

public class SiteBuildExecutors extends AbstractExecutor {
	private DeptPosDTO logisticsChargeDeptPosDTO = null;
	private SiteBuildVariables variables;

	public SiteBuildExecutors(final OrgWorkflowService orgService,
			final EmailTemplateService emailTemplateService, final UserDTO executor,
			final Map<String, Object> extraData) {
		super(orgService, emailTemplateService, executor, extraData);
		variables = new SiteBuildVariables();
		variables.setVariables(extraData);
		variables.setApplicantDeptMgr(SKIP_TASK_FLAG);
		variables.setApplicantDivisionMgr(SKIP_TASK_FLAG);

	}

	@Override
	public BaseProcessVariables getVariables() {
		return variables;
	}

	public SiteBuildExecutors addMailVariables(Collection<WorkflowEmailType> mailTypes) {
		Map<String, Object> parserMap = new HashMap<>();
		parserMap.putAll(extraData);
		parserMap.putAll(variables.toVariables());
		setMailVariableFromTemplates(mailTypes, variables, parserMap);
		return this;
	}

	public SiteBuildExecutors addApplicantDeptMgr() throws WorkflowException {
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

	public SiteBuildExecutors addApplicantDivisionMgr() throws WorkflowException {
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

	public SiteBuildExecutors addLeaseLineCharge() throws WorkflowException {
		List<DeptPosDTO> deptPosDtos = findSpecificWorker(siteBuildLeaseLineChargePosId);
		DeptPosDTO chargeDeptPosDto = null;
		if (!deptPosDtos.isEmpty()) {
			chargeDeptPosDto = deptPosDtos.get(0);
		}
		if (chargeDeptPosDto != null) {
			setLogisticsChargeDeptPosDTO(chargeDeptPosDto);
			variables.setLogisticsChargeTaskName(chargeDeptPosDto.getPosName());
			variables.setLogisticsCharge(chargeDeptPosDto.getDeptPosId());
		}

		String worker = variables.getLogisticsCharge();
		if (StringUtils.isBlank(worker) || SKIP_TASK_FLAG.equals(worker)) {
			throw new WorkflowException("查無專線經辦簽核職稱");
			// Logistics lease charge is blank.
		}
		return this;
	}

	public SiteBuildExecutors addLeaseLineChargeDeptMgr() throws WorkflowException {
		DeptPosDTO logisticsChargeDeptPosDto = getLogisticsChargeDeptPosDTO();
		if (logisticsChargeDeptPosDto == null) {
			throw new WorkflowException("查無專線經辦之部門主管簽核職稱");
		}
		DeptPosDTO deptPosDto = findDeptMgr(logisticsChargeDeptPosDto.getDeptId());
		if (deptPosDto != null) {
			variables.setLogisticsDeptMgrTaskName(deptPosDto.getPosName());
			variables.setLogisticsDeptMgr(deptPosDto.getDeptPosId());
		}

		String worker = variables.getLogisticsDeptMgr();
		if (StringUtils.isBlank(worker) || SKIP_TASK_FLAG.equals(worker)) {
			throw new WorkflowException("查無專線經辦之部門主管簽核職稱");
			// Logistics department manager is blank.
		}
		return this;
	}

	public SiteBuildExecutors addLeaseLineChargeDivisionMgr() throws WorkflowException {
		DeptPosDTO logisticsChargeDeptPosDto = getLogisticsChargeDeptPosDTO();
		if (logisticsChargeDeptPosDto == null) {
			throw new WorkflowException("查無專線經辦之處長簽核職稱");
		}
		DeptPosDTO deptPosDto = findDivisionMgr(logisticsChargeDeptPosDto.getDeptId());
		if (deptPosDto != null) {
			variables.setLogisticsDivisionMgrTaskName(deptPosDto.getPosName());
			variables.setLogisticsDivisionMgr(deptPosDto.getDeptPosId());
		}

		String worker = variables.getLogisticsDivisionMgr();
		if (StringUtils.isBlank(worker) || SKIP_TASK_FLAG.equals(worker)) {
			throw new WorkflowException("查無專線經辦之處長簽核職稱");
			// Logistics division manager is blank.
		}
		return this;
	}

	private void setLogisticsChargeDeptPosDTO(DeptPosDTO logisticsChargeDeptPosDTO) {
		this.logisticsChargeDeptPosDTO = logisticsChargeDeptPosDTO;
	}

	private DeptPosDTO getLogisticsChargeDeptPosDTO() {
		return logisticsChargeDeptPosDTO;
	}
}
