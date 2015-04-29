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

public class SearchSiteExecutors extends AbstractExecutor {
	private DeptPosDTO applicantDeptPosDTO = null;
	private String wirelessDeptId = null;
	private String rfDeptId = null;
	private SearchSiteVariables variables;

	public SearchSiteExecutors(final OrgWorkflowService orgWorkflowService,
			final EmailTemplateService emailTemplateService, final UserDTO executor,
			Map<String, Object> extraData) {
		super(orgWorkflowService, emailTemplateService, executor, extraData);
		variables = new SearchSiteVariables();
		variables.setVariables(extraData);
		variables.setApplicantDeptMgr(SKIP_TASK_FLAG);
		variables.setApplicantDivisionMgr(SKIP_TASK_FLAG);
		variables.setRfOptEngineer(SKIP_TASK_FLAG);
		variables.setRfOptDeptMgr(SKIP_TASK_FLAG);
		variables.setRfOptDivisionMgr(SKIP_TASK_FLAG);
		variables.setWirelessOptEngineer(SKIP_TASK_FLAG);
		variables.setWirelessOptDeptMgr(SKIP_TASK_FLAG);
		variables.setWirelessOptDivisionMgr(SKIP_TASK_FLAG);
		variables.setSysEngineer(SKIP_TASK_FLAG);
		variables.setSysDivisionMgr(SKIP_TASK_FLAG);

	}

	@Override
	public BaseProcessVariables getVariables() {
		return variables;
	}

	public SearchSiteExecutors addMailVariables(Collection<WorkflowEmailType> mailTypes) {
		Map<String, Object> parserMap = new HashMap<>();
		parserMap.putAll(extraData);
		parserMap.putAll(variables.toVariables());
		setMailVariableFromTemplates(mailTypes, variables, parserMap);
		return this;
	}

	public SearchSiteExecutors addApplicantDeptMgr() throws WorkflowException {
		DeptPosDTO deptPosDto = findApplicantDeptMgr();
		if (deptPosDto != null) {
			setApplicantDeptPosDTO(deptPosDto);
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

	public SearchSiteExecutors addApplicantDivisionMgr() throws WorkflowException {
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

	public SearchSiteExecutors addWirelessOptEngineer() throws WorkflowException {
		setWirelessDeptId("");
		List<DeptPosDTO> deptPosDtos = findSpecificWorker(wirelessOptEngineerPosId);
		for (DeptPosDTO deptPosDto : deptPosDtos) {
			String domain = deptPosDto.getDomain();
			if (domain != null && domain.length() > 0) {
				domain = deptPosDto.getDomain().substring(0, 1);
			}
			String applicantDomain = getApplicantDeptPosDTO().getDomain();
			if (domain != null && applicantDomain != null && applicantDomain.startsWith(domain)) {
				setWirelessDeptId(deptPosDto.getDeptId());
				variables.setWirelessOptEngineerTaskName(deptPosDto.getPosName());
				variables.setWirelessOptEngineer(deptPosDto.getDeptPosId());
				break;
			}
		}

		String worker = variables.getWirelessOptEngineer();
		if (StringUtils.isBlank(worker) || SKIP_TASK_FLAG.equals(worker)) {
			throw new WorkflowException("查無無線優化部之工程師簽核職稱");
			// Wireless optimization engineer is blank.
		}
		return this;
	}

	public SearchSiteExecutors addWirelessOptDeptMgr() throws WorkflowException {
		DeptPosDTO deptPosDto = findDeptSpecificWorker(getWirelessDeptId(), deptMgrPosId);
		if (deptPosDto != null) {
			variables.setWirelessOptDeptMgrTaskName(deptPosDto.getPosName());
			variables.setWirelessOptDeptMgr(deptPosDto.getDeptPosId());
		}

		String worker = variables.getWirelessOptDeptMgr();
		if (StringUtils.isBlank(worker) || SKIP_TASK_FLAG.equals(worker)) {
			throw new WorkflowException("查無無線優化部之部門主管簽核職稱");
			// Wireless optimization department manager is blank.
		}
		return this;
	}

	public SearchSiteExecutors addWirelessOptDivisionMgr() throws WorkflowException {
		DeptPosDTO deptPosDto = findDivisionMgr(getWirelessDeptId());
		if (deptPosDto != null) {
			variables.setWirelessOptDivisionMgrTaskName(deptPosDto.getPosName());
			variables.setWirelessOptDivisionMgr(deptPosDto.getDeptPosId());
		}

		String worker = variables.getWirelessOptDivisionMgr();
		if (StringUtils.isBlank(worker) || SKIP_TASK_FLAG.equals(worker)) {
			throw new WorkflowException("查無無線優化部之處長簽核職稱");
			// Wireless optimization division manager is blank.
		}
		return this;
	}

	public SearchSiteExecutors addRfOptEngineer() throws WorkflowException {
		setRfDeptId("");
		List<DeptPosDTO> deptPosDtos = findSpecificWorker(rfOptEngineerPosId);
		for (DeptPosDTO deptPosDto : deptPosDtos) {
			if (deptPosDto != null) {
				setRfDeptId(deptPosDto.getDeptId());
				variables.setRfOptEngineerTaskName(deptPosDto.getPosName());
				variables.setRfOptEngineer(deptPosDto.getDeptPosId());
				break;
			}
		}

		String worker = variables.getRfOptEngineer();
		if (StringUtils.isBlank(worker) || SKIP_TASK_FLAG.equals(worker)) {
			throw new WorkflowException("查無射頻優化部之工程師簽核職稱");
			// RF optimization engineer is blank.
		}
		return this;
	}

	public SearchSiteExecutors addRfOptDeptMgr() throws WorkflowException {
		DeptPosDTO deptPosDto = findDeptMgr(getRfDeptId());
		if (deptPosDto != null) {
			variables.setRfOptDeptMgrTaskName(deptPosDto.getPosName());
			variables.setRfOptDeptMgr(deptPosDto.getDeptPosId());
		}

		String worker = variables.getRfOptDeptMgr();
		if (StringUtils.isBlank(worker) || SKIP_TASK_FLAG.equals(worker)) {
			throw new WorkflowException("查無射頻優化部之部門主管簽核職稱");
			// RF optimization department manager is blank.
		}
		return this;
	}

	public SearchSiteExecutors addRfOptDivisionMgr() throws WorkflowException {
		DeptPosDTO deptPosDto = findDivisionMgr(getRfDeptId());
		if (deptPosDto != null) {
			variables.setRfOptDivisionMgrTaskName(deptPosDto.getPosName());
			variables.setRfOptDivisionMgr(deptPosDto.getDeptPosId());
		}

		String worker = variables.getRfOptDivisionMgr();
		if (StringUtils.isBlank(worker) || SKIP_TASK_FLAG.equals(worker)) {
			throw new WorkflowException("查無射頻優化部之處長簽核職稱");
			// RF optimization division manager is blank.
		}
		return this;
	}

	private void setApplicantDeptPosDTO(DeptPosDTO applicantDeptPosDTO) {
		this.applicantDeptPosDTO = applicantDeptPosDTO;
	}

	private DeptPosDTO getApplicantDeptPosDTO() {
		if (applicantDeptPosDTO == null) {
			List<DeptPosDTO> deptPosDtoList = orgWorkflowService.getDeptPosDtoByDeptPosId(executor
					.getPrimaryDeptPosId());
			if (deptPosDtoList != null && !deptPosDtoList.isEmpty()) {
				setApplicantDeptPosDTO(deptPosDtoList.get(0));
			}
		}
		return applicantDeptPosDTO;
	}

	private void setWirelessDeptId(String wirelessDeptId) {
		this.wirelessDeptId = wirelessDeptId;
	}

	private String getWirelessDeptId() {
		if (wirelessDeptId == null) {
			List<DeptPosDTO> deptPosDtos = findSpecificWorker(wirelessOptEngineerPosId);
			if (deptPosDtos.isEmpty()) {
				setWirelessDeptId("");
				return "";
			}
			for (DeptPosDTO deptPosDto : deptPosDtos) {
				String domain = deptPosDto.getDomain();
				if (domain != null && domain.equals(getApplicantDeptPosDTO().getDomain())) {
					setWirelessDeptId(deptPosDto.getDeptId());
					return wirelessDeptId;
				}
			}
		}
		return wirelessDeptId;
	}

	private void setRfDeptId(String rfDeptId) {
		this.rfDeptId = rfDeptId;
	}

	private String getRfDeptId() {
		if (rfDeptId == null) {
			List<DeptPosDTO> deptPosDtos = findSpecificWorker(rfOptEngineerPosId);
			if (deptPosDtos.isEmpty()) {
				setRfDeptId("");
				return "";
			}
			for (DeptPosDTO deptPosDto : deptPosDtos) {
				if (deptPosDto != null) {
					setRfDeptId(deptPosDto.getDeptId());
					return rfDeptId;
				}
			}
		}
		return rfDeptId;
	}

}
