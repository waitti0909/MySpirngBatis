package com.foya.noms.workflow.executor;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.foya.dao.mybatis.model.TbOrgDept;
import com.foya.noms.dto.auth.UserDTO;
import com.foya.noms.dto.org.DeptPosDTO;
import com.foya.noms.enums.WorkflowEmailType;
import com.foya.noms.service.common.EmailTemplateService;
import com.foya.noms.service.org.OrgWorkflowService;
import com.foya.workflow.exception.WorkflowException;
import com.foya.workflow.model.BaseProcessVariables;

public class LeaseExecutors extends AbstractExecutor {
	private TbOrgDept applicantTbOrgDept = null;
	private DeptPosDTO projectChargeDeptPosDTO = null;
	private DeptPosDTO logisticsChargeDeptPosDTO = null;
	private DeptPosDTO accountingChargeDeptPosDTO = null;
	private LeaseVariables variables;

	public LeaseExecutors(final OrgWorkflowService orgWorkflowService,
			final EmailTemplateService emailTemplateService, final UserDTO executor,
			final Map<String, Object> extraData) {

		super(orgWorkflowService, emailTemplateService, executor, extraData);
		variables = new LeaseVariables();
		variables.setVariables(extraData);
		variables.setProjectCharge(SKIP_TASK_FLAG);
		variables.setProjectDeptMgr(SKIP_TASK_FLAG);
		variables.setLogisticsCharge(SKIP_TASK_FLAG);
		variables.setLogisticsDeptMgr(SKIP_TASK_FLAG);
		variables.setAccountingCharge(SKIP_TASK_FLAG);
		variables.setAccountingDeptMgr(SKIP_TASK_FLAG);
	}

	@Override
	public BaseProcessVariables getVariables() {
		return variables;
	}

	public LeaseExecutors addMailVariables(Collection<WorkflowEmailType> mailTypes) {
		Map<String, Object> parserMap = new HashMap<>();
		parserMap.putAll(extraData);
		parserMap.putAll(variables.toVariables());
		setMailVariableFromTemplates(mailTypes, variables, parserMap);
		return this;
	}

	public LeaseExecutors addProjectCharge() throws WorkflowException {
		DeptPosDTO deptPosDto = getProjectChargeDeptPosDTO();
		if (deptPosDto != null) {
			variables.setProjectChargeTaskName(deptPosDto.getPosName());
			variables.setProjectCharge(deptPosDto.getDeptPosId());
		}

		String worker = variables.getProjectCharge();
		if (StringUtils.isBlank(worker) || SKIP_TASK_FLAG.equals(worker)) {
			throw new WorkflowException("查無專案之經辦簽核職稱");
			// Project charge is blank.
		}
		return this;
	}

	public LeaseExecutors addProjectDeptMgr() throws WorkflowException {
		DeptPosDTO projectChargeDeptPosDto = getProjectChargeDeptPosDTO();
		if (projectChargeDeptPosDto == null) {
			throw new WorkflowException("查無專案之經辦簽核職稱");
		}
		DeptPosDTO deptPosDto = findDeptMgr(projectChargeDeptPosDto.getDeptId());
		if (deptPosDto != null) {
			variables.setProjectDeptMgrTaskName(deptPosDto.getPosName());
			variables.setProjectDeptMgr(deptPosDto.getDeptPosId());
		}

		String worker = variables.getProjectDeptMgr();
		if (StringUtils.isBlank(worker) || SKIP_TASK_FLAG.equals(worker)) {
			throw new WorkflowException("查無專案之經辦部門主管簽核職稱");
			// Project department manager is blank.
		}
		return this;
	}

	public LeaseExecutors addLogisticsCharge() throws WorkflowException {
		DeptPosDTO deptPosDto = getLogisticsChargeDeptPosDTO();
		if (deptPosDto != null) {
			variables.setLogisticsChargeTaskName(deptPosDto.getPosName());
			variables.setLogisticsCharge(deptPosDto.getDeptPosId());
		}

		String worker = variables.getLogisticsCharge();
		if (StringUtils.isBlank(worker) || SKIP_TASK_FLAG.equals(worker)) {
			throw new WorkflowException("查無後勤管理部門之經辦簽核職稱");
			// Logistics charge is blank.
		}
		return this;
	}

	public LeaseExecutors addLogisticsDeptMgr() throws WorkflowException {
		DeptPosDTO logisticsChargeDeptPosDto = getLogisticsChargeDeptPosDTO();
		if (logisticsChargeDeptPosDto == null) {
			throw new WorkflowException("查無後勤管理部門之部門主管簽核職稱");
		}
		DeptPosDTO deptPosDto = findDeptMgr(logisticsChargeDeptPosDto.getDeptId());
		if (deptPosDto != null) {
			variables.setLogisticsDeptMgrTaskName(deptPosDto.getPosName());
			variables.setLogisticsDeptMgr(deptPosDto.getDeptPosId());
		}

		String worker = variables.getLogisticsDeptMgr();
		if (StringUtils.isBlank(worker) || SKIP_TASK_FLAG.equals(worker)) {
			throw new WorkflowException("查無後勤管理部門之部門主管簽核職稱");
			// Logistics department manager is blank.
		}
		return this;
	}

	public LeaseExecutors addAccountingCharge() throws WorkflowException {
		DeptPosDTO deptPosDto = getAccountingChargeDeptPosDTO();
		if (deptPosDto != null) {
			variables.setAccountingChargeTaskName(deptPosDto.getPosName());
			variables.setAccountingCharge(deptPosDto.getDeptPosId());
		}

		String worker = variables.getAccountingCharge();
		if (StringUtils.isBlank(worker) || SKIP_TASK_FLAG.equals(worker)) {
			throw new WorkflowException("查無會計部之經辦簽核職稱");
			// Accounting charge is blank.
		}
		return this;
	}

	public LeaseExecutors addAccountingDeptMgr() throws WorkflowException {
		DeptPosDTO accountingDeptPosDto = getAccountingChargeDeptPosDTO();
		if (accountingDeptPosDto == null) {
			throw new WorkflowException("查無會計部之部門主管簽核職稱");
		}

		DeptPosDTO deptPosDto = findDeptMgr(accountingDeptPosDto.getDeptId());
		if (deptPosDto != null) {
			variables.setAccountingDeptMgrTaskName(deptPosDto.getPosName());
			variables.setAccountingDeptMgr(deptPosDto.getDeptPosId());
		}

		String worker = variables.getAccountingDeptMgr();
		if (StringUtils.isBlank(worker) || SKIP_TASK_FLAG.equals(worker)) {
			throw new WorkflowException("查無會計部之部門主管簽核職稱");
			// Accounting department manager is blank.
		}
		return this;
	}

	private void setApplicantTbOrgDept(TbOrgDept applicantTbOrgDept) {
		this.applicantTbOrgDept = applicantTbOrgDept;
	}

	private TbOrgDept getApplicantTbOrgDept() {
		if (applicantTbOrgDept == null) {
			TbOrgDept orgDept = orgWorkflowService.getTbOrgDept(executor.getDeptId());
			if (orgDept != null) {
				setApplicantTbOrgDept(orgDept);
			}
		}
		return applicantTbOrgDept;
	}

	private void setProjectChargeDeptPosDTO(DeptPosDTO projectChargeDeptPosDTO) {
		this.projectChargeDeptPosDTO = projectChargeDeptPosDTO;
	}

	private DeptPosDTO getProjectChargeDeptPosDTO() {
		if (projectChargeDeptPosDTO == null) {
			List<DeptPosDTO> deptPosDtos = findSpecificWorker(leaseProjectChargePosId);
			for (DeptPosDTO deptPosDto : deptPosDtos) {
				String domain = deptPosDto.getDomain();
				if (domain != null && domain.length() > 0) {
					domain = deptPosDto.getDomain().substring(0, 1);
				}
				if (getApplicantTbOrgDept() == null) {
					break;
				}
				String applicantDomain = getApplicantTbOrgDept().getDOMAIN();
				if (domain != null && applicantDomain != null && applicantDomain.startsWith(domain)) {
					setProjectChargeDeptPosDTO(deptPosDto);
					break;
				}
			}
		}
		return projectChargeDeptPosDTO;
	}

	private void setLogisticsChargeDeptPosDTO(DeptPosDTO logisticsChargeDeptPosDTO) {
		this.logisticsChargeDeptPosDTO = logisticsChargeDeptPosDTO;
	}

	private DeptPosDTO getLogisticsChargeDeptPosDTO() {
		if (logisticsChargeDeptPosDTO == null) {
			List<DeptPosDTO> deptPosDtos = findSpecificWorker(leaseLogisticsChargePosId);
			if (deptPosDtos != null && !deptPosDtos.isEmpty()) {
				DeptPosDTO logisticsChargeDeptPosDto = deptPosDtos.get(0);
				setLogisticsChargeDeptPosDTO(logisticsChargeDeptPosDto);
			}
		}
		return logisticsChargeDeptPosDTO;
	}

	private void setAccountingChargeDeptPosDTO(DeptPosDTO accountingChargeDeptPosDTO) {
		this.accountingChargeDeptPosDTO = accountingChargeDeptPosDTO;
	}

	private DeptPosDTO getAccountingChargeDeptPosDTO() {
		if (accountingChargeDeptPosDTO == null) {
			List<DeptPosDTO> deptPosDtos = findSpecificWorker(leaseAccountingChargePosId);
			if (!deptPosDtos.isEmpty()) {
				DeptPosDTO accountingChargeDeptPosDto = deptPosDtos.get(0);
				setAccountingChargeDeptPosDTO(accountingChargeDeptPosDto);
			}
		}
		return accountingChargeDeptPosDTO;
	}

}
