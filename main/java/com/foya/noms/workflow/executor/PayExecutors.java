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

public class PayExecutors extends AbstractExecutor {

	private TbOrgDept applicantTbOrgDept = null;
	private DeptPosDTO logisticsChargeDeptPosDTO = null;
	private PayVariables variables;
	private String networkMgrMaint1stDeptId = null;
	private String networkMgrMaint2ndDeptId = null;

	public PayExecutors(final OrgWorkflowService orgWorkflowService,
			final EmailTemplateService emailTemplateService, final UserDTO executor,
			final Map<String, Object> extraData) {

		super(orgWorkflowService, emailTemplateService, executor, extraData);
		variables = new PayVariables();
		variables.setVariables(extraData);
		variables.setApplicantDeptMgr(SKIP_TASK_FLAG);
		variables.setApplicantDivisionMgr(SKIP_TASK_FLAG);
		variables.setLogisticsCharge(SKIP_TASK_FLAG);
		variables.setLogisticsDeptMgr(SKIP_TASK_FLAG);
		variables.setLogisticsDivisionMgr(SKIP_TASK_FLAG);
		variables.setViceGeneralMgr(SKIP_TASK_FLAG);
	}

	@Override
	public BaseProcessVariables getVariables() {
		return variables;
	}

	public PayExecutors addMailVariables(Collection<WorkflowEmailType> mailTypes) {
		Map<String, Object> parserMap = new HashMap<>();
		parserMap.putAll(extraData);
		parserMap.putAll(variables.toVariables());
		setMailVariableFromTemplates(mailTypes, variables, parserMap);
		return this;
	}

	public PayExecutors addApplicantDeptMgr() throws WorkflowException {
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

	public PayExecutors addApplicantDivisionMgr() throws WorkflowException {
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

	public PayExecutors addApplicantViceGeneral() throws WorkflowException {
		DeptPosDTO deptPosDto = findApplicantViceGeneral();
		if (deptPosDto != null) {
			variables.setViceGeneralMgrTaskName(deptPosDto.getPosName());
			variables.setViceGeneralMgr(deptPosDto.getDeptPosId());
		}

		String worker = variables.getViceGeneralMgr();
		if (StringUtils.isBlank(worker) || SKIP_TASK_FLAG.equals(worker)) {
			throw new WorkflowException("查無申請單位之副總簽核職稱");
			// Internet services vice general is blank.
		}
		return this;
	}

	public PayExecutors addNetworkMgrMaint1stSectorDeptMgr() throws WorkflowException {
		DeptPosDTO deptPosDto = findDeptMgr(getNetworkMgrMaint1stDeptId());
		if (deptPosDto != null) {
			variables.setNetworkMaint1stDeptMgrTaskName(deptPosDto.getPosName());
			variables.setNetworkMaint1stDeptMgr(deptPosDto.getDeptPosId());
		}

		String worker = variables.getNetworkMaint1stDeptMgr();
		if (StringUtils.isBlank(worker) || SKIP_TASK_FLAG.equals(worker)) {
			throw new WorkflowException("查無網維一部之部門主管簽核職稱");
			// Network manage and maintain first department manager is blank.
		}
		return this;
	}

	public PayExecutors addNetworkMgrMaint2ndSectorDeptMgr() throws WorkflowException {
		String secondDeptId = getNetworkMgrMaint2ndDeptId();
		DeptPosDTO deptPosDto = findDeptMgr(secondDeptId);
		if (deptPosDto != null) {
			variables.setNetworkMaint2ndDeptMgrTaskName(deptPosDto.getPosName());
			variables.setNetworkMaint2ndDeptMgr(deptPosDto.getDeptPosId());
		}

		String worker = variables.getNetworkMaint2ndDeptMgr();
		if (StringUtils.isBlank(worker) || SKIP_TASK_FLAG.equals(worker)) {
			throw new WorkflowException("查無網維二部之部門主管簽核職稱");
			// Network manage and maintain second department manager is blank.
		}
		return this;
	}

	public PayExecutors addLogisticsRentCharge() throws WorkflowException {
		List<DeptPosDTO> deptPosDtos = findSpecificWorker(payRentChargePosId);
		DeptPosDTO chargeDeptPosDto = null;
		for (DeptPosDTO deptPosDto : deptPosDtos) {
			// 優先找後勤管理部底下的租金經辦
			if (StringUtils.equals(deptPosDto.getDeptId(), logisticsDeptId)) {
				chargeDeptPosDto = deptPosDto;
				break;
			}
			chargeDeptPosDto = deptPosDto;
		}
		if (chargeDeptPosDto != null) {
			setLogisticsChargeDeptPosDTO(chargeDeptPosDto);
			variables.setLogisticsChargeTaskName(chargeDeptPosDto.getPosName());
			variables.setLogisticsCharge(chargeDeptPosDto.getDeptPosId());
		}

		String worker = variables.getLogisticsCharge();
		if (StringUtils.isBlank(worker) || SKIP_TASK_FLAG.equals(worker)) {
			throw new WorkflowException("查無租金經辦簽核職稱");
			// Logistics rent charge is blank.
		}
		return this;
	}

	public PayExecutors addLogisticsElectricBillCharge() throws WorkflowException {
		List<DeptPosDTO> deptPosDtos = findSpecificWorker(payElectricBillChargePosId);
		DeptPosDTO chargeDeptPosDto = null;
		for (DeptPosDTO deptPosDto : deptPosDtos) {
			// 優先找後勤管理部底下的電費經辦
			if (StringUtils.equals(deptPosDto.getDeptId(), logisticsDeptId)) {
				chargeDeptPosDto = deptPosDto;
				break;
			}
			chargeDeptPosDto = deptPosDto;
		}
		if (chargeDeptPosDto != null) {
			setLogisticsChargeDeptPosDTO(chargeDeptPosDto);
			variables.setLogisticsChargeTaskName(chargeDeptPosDto.getPosName());
			variables.setLogisticsCharge(chargeDeptPosDto.getDeptPosId());
		}

		String worker = variables.getLogisticsCharge();
		if (StringUtils.isBlank(worker) || SKIP_TASK_FLAG.equals(worker)) {
			throw new WorkflowException("查無電費經辦簽核職稱");
			// Logistics electric bill charge is blank.
		}
		return this;
	}

	public PayExecutors addLogisticsOthersCharge() throws WorkflowException {
		List<DeptPosDTO> deptPosDtos = findSpecificWorker(payOthersChargePosId);
		DeptPosDTO chargeDeptPosDto = null;
		for (DeptPosDTO deptPosDto : deptPosDtos) {
			// 優先找後勤管理部底下的雜項經辦
			if (StringUtils.equals(deptPosDto.getDeptId(), logisticsDeptId)) {
				chargeDeptPosDto = deptPosDto;
				break;
			}
			chargeDeptPosDto = deptPosDto;
		}
		if (chargeDeptPosDto != null) {
			setLogisticsChargeDeptPosDTO(chargeDeptPosDto);
			variables.setLogisticsChargeTaskName(chargeDeptPosDto.getPosName());
			variables.setLogisticsCharge(chargeDeptPosDto.getDeptPosId());
		}

		String worker = variables.getLogisticsCharge();
		if (StringUtils.isBlank(worker) || SKIP_TASK_FLAG.equals(worker)) {
			throw new WorkflowException("查無雜項經辦簽核職稱");
			// Logistics others charge is blank.
		}
		return this;
	}

	public PayExecutors addLogisticsVoidedCheckCharge() throws WorkflowException {
		List<DeptPosDTO> deptPosDtos = findSpecificWorker(payVoidedCheckChargePosId);
		DeptPosDTO chargeDeptPosDto = null;
		for (DeptPosDTO deptPosDto : deptPosDtos) {
			// 優先找後勤管理部底下的雜項經辦
			if (StringUtils.equals(deptPosDto.getDeptId(), logisticsDeptId)) {
				chargeDeptPosDto = deptPosDto;
				break;
			}
			chargeDeptPosDto = deptPosDto;
		}
		if (chargeDeptPosDto != null) {
			setLogisticsChargeDeptPosDTO(chargeDeptPosDto);
			variables.setLogisticsChargeTaskName(chargeDeptPosDto.getPosName());
			variables.setLogisticsCharge(chargeDeptPosDto.getDeptPosId());
		}

		String worker = variables.getLogisticsCharge();
		if (StringUtils.isBlank(worker) || SKIP_TASK_FLAG.equals(worker)) {
			throw new WorkflowException("查無支票作廢經辦簽核職稱");
			// Logistics voided check charge is blank.
		}
		return this;
	}

	public PayExecutors addLogisticsDeptMgr() throws WorkflowException {
		DeptPosDTO logisticsChargeDeptPosDto = getLogisticsChargeDeptPosDTO();
		if (logisticsChargeDeptPosDto == null) {
			throw new WorkflowException("查無經辦之部門主管簽核職稱");
		}
		DeptPosDTO deptPosDto = findDeptMgr(logisticsChargeDeptPosDto.getDeptId());
		if (deptPosDto != null) {
			variables.setLogisticsDeptMgrTaskName(deptPosDto.getPosName());
			variables.setLogisticsDeptMgr(deptPosDto.getDeptPosId());
		}

		String worker = variables.getLogisticsDeptMgr();
		if (StringUtils.isBlank(worker) || SKIP_TASK_FLAG.equals(worker)) {
			throw new WorkflowException("查無經辦之部門主管簽核職稱");
			// Logistics department manager is blank.
		}
		return this;
	}

	public PayExecutors addLogisticsDivisionMgr() throws WorkflowException {
		DeptPosDTO logisticsChargeDeptPosDto = getLogisticsChargeDeptPosDTO();
		if (logisticsChargeDeptPosDto == null) {
			throw new WorkflowException("查無經辦之處長簽核職稱");
		}
		DeptPosDTO deptPosDto = findDivisionMgr(logisticsChargeDeptPosDto.getDeptId());
		if (deptPosDto != null) {
			variables.setLogisticsDivisionMgrTaskName(deptPosDto.getPosName());
			variables.setLogisticsDivisionMgr(deptPosDto.getDeptPosId());
		}

		String worker = variables.getLogisticsDivisionMgr();
		if (StringUtils.isBlank(worker) || SKIP_TASK_FLAG.equals(worker)) {
			throw new WorkflowException("查無經辦之處長簽核職稱");
			// Logistics division manager is blank.
		}
		return this;
	}

	public PayExecutors addLogisticsViceGeneral() throws WorkflowException {
		DeptPosDTO deptPosDto = findViceGeneral(logisticsDeptId);
		if (deptPosDto != null) {
			variables.setViceGeneralMgrTaskName(deptPosDto.getPosName());
			variables.setViceGeneralMgr(deptPosDto.getDeptPosId());
		}

		String worker = variables.getViceGeneralMgr();
		if (StringUtils.isBlank(worker) || SKIP_TASK_FLAG.equals(worker)) {
			throw new WorkflowException("查無網路服務事業部之副總簽核職稱");
			// Internet services vice general is blank.
		}
		return this;
	}

	private void setNetworkMgrMaint1stDeptId(String networkMgrMaint1stDeptId) {
		this.networkMgrMaint1stDeptId = networkMgrMaint1stDeptId;
	}

	// Domain
	// 北一 N
	// 北二 M
	// 中 C
	// 南 S
	private String getNetworkMgrMaint1stDeptId() {
		if (networkMgrMaint1stDeptId == null) {
			if (getApplicantTbOrgDept() == null) {
				setNetworkMgrMaint1stDeptId("");
				return "";
			}
			String deptId = "";
			String applicantDomain = getApplicantDomain();
			if ("N".equalsIgnoreCase(applicantDomain)) {
				deptId = northern1RegionNetworkMgrMaint1stSectorDeptId;
			} else if ("M".equalsIgnoreCase(applicantDomain)) {
				deptId = northern2RegionNetworkMgrMaint1stSectorDeptId;
			} else if ("C".equalsIgnoreCase(applicantDomain)) {
				deptId = centralNetworkMgrMaint1stSectorDeptId;
			} else if ("S".equalsIgnoreCase(applicantDomain)) {
				deptId = southernNetworkMgrMaint1stSectorDeptId;
			} else {
				deptId = "";
			}
			setNetworkMgrMaint1stDeptId(deptId);
		}
		return networkMgrMaint1stDeptId;
	}

	private void setNetworkMgrMaint2ndDeptId(String networkMgrMaint2ndDeptId) {
		this.networkMgrMaint2ndDeptId = networkMgrMaint2ndDeptId;
	}

	private String getNetworkMgrMaint2ndDeptId() {
		if (networkMgrMaint2ndDeptId == null) {
			if (getApplicantTbOrgDept() == null) {
				setNetworkMgrMaint2ndDeptId("");
				return "";
			}
			String deptId = "";
			String applicantDomain = getApplicantDomain();
			if ("N".equalsIgnoreCase(applicantDomain)) {
				deptId = northern1RegionNetworkMgrMaint2ndSectorDeptId;
			} else if ("M".equalsIgnoreCase(applicantDomain)) {
				deptId = northern2RegionNetworkMgrMaint2ndSectorDeptId;
			} else if ("C".equalsIgnoreCase(applicantDomain)) {
				deptId = centralNetworkMgrMaint2ndSectorDeptId;
			} else if ("S".equalsIgnoreCase(applicantDomain)) {
				deptId = southernNetworkMgrMaint2ndSectorDeptId;
			} else {
				deptId = "";
			}
			setNetworkMgrMaint2ndDeptId(deptId);
		}
		return networkMgrMaint2ndDeptId;
	}

	private String getApplicantDomain() {
		if (getApplicantTbOrgDept() == null) {
			setNetworkMgrMaint1stDeptId("");
			setNetworkMgrMaint2ndDeptId("");
		}
		String applicantDomain = getApplicantTbOrgDept().getDOMAIN();
		if (applicantDomain != null && applicantDomain.length() > 0) {
			return applicantDomain.substring(0, 1);
		}
		return "";
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

	private void setLogisticsChargeDeptPosDTO(DeptPosDTO logisticsChargeDeptPosDTO) {
		this.logisticsChargeDeptPosDTO = logisticsChargeDeptPosDTO;
	}

	private DeptPosDTO getLogisticsChargeDeptPosDTO() {
		return logisticsChargeDeptPosDTO;
	}
}
