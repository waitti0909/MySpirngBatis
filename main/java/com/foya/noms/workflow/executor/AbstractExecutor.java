package com.foya.noms.workflow.executor;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.foya.dao.mybatis.model.TbSysEmailTemplate;
import com.foya.dao.mybatis.model.TbSysLookup;
import com.foya.exception.NomsException;
import com.foya.noms.dto.auth.UserDTO;
import com.foya.noms.dto.org.DeptDTO;
import com.foya.noms.dto.org.DeptPosDTO;
import com.foya.noms.enums.WorkflowEmailType;
import com.foya.noms.service.common.EmailTemplateService;
import com.foya.noms.service.org.OrgWorkflowService;
import com.foya.noms.util.ParserUtil;
import com.foya.workflow.model.BaseProcessVariables;

public abstract class AbstractExecutor {
	protected OrgWorkflowService orgWorkflowService;
	protected EmailTemplateService emailTemplateService;
	protected UserDTO executor;
	protected Map<String, Object> extraData;

	protected String logisticsDeptId;
	protected String northern1RegionNetworkMgrMaint1stSectorDeptId;
	protected String northern1RegionNetworkMgrMaint2ndSectorDeptId;
	protected String northern2RegionNetworkMgrMaint1stSectorDeptId;
	protected String northern2RegionNetworkMgrMaint2ndSectorDeptId;
	protected String centralNetworkMgrMaint1stSectorDeptId;
	protected String centralNetworkMgrMaint2ndSectorDeptId;
	protected String southernNetworkMgrMaint1stSectorDeptId;
	protected String southernNetworkMgrMaint2ndSectorDeptId;

	protected String deptMgrPosId;
	protected String divisionMgrPosId;
	protected String viceGeneralMgrPosId;
	protected String generalMgrPosId;
	protected String wirelessOptEngineerPosId;
	protected String rfOptEngineerPosId;
	protected String siteBuildLeaseLineChargePosId;
	protected String leaseAccountingChargePosId;
	protected String leaseLogisticsChargePosId;
	protected String leaseProjectChargePosId;
	protected String payRentChargePosId;
	protected String payElectricBillChargePosId;
	protected String payOthersChargePosId;
	protected String payVoidedCheckChargePosId;

	public static final String SKIP_TASK_FLAG = "skipTaskFlag";

	protected enum orgType {
		ORG_SPECIFIC_DEPT_ID, ORG_SPECIFIC_POS_ID
	}

	protected enum deptIdCodeFromLookup {
		LOGISTICS_DEPT_ID, // 後勤管理部門代號
		NORTHERN_ONE_REGION_NETWORK_MGR_MAINT_1ST_SECTOR, // 北一區網維一部
		NORTHERN_ONE_REGION_NETWORK_MGR_MAINT_2ND_SECTOR, // 北一區網維二部
		NORTHERN_TWO_REGION_NETWORK_MGR_MAINT_1ST_SECTOR, // 北二區網維一部
		NORTHERN_TWO_REGION_NETWORK_MGR_MAINT_2ND_SECTOR, // 北二區網維二部
		CENTRAL_NETWORK_MGR_MAINT_1ST_SECTOR, // 中區網維一部
		CENTRAL_NETWORK_MGR_MAINT_2ND_SECTOR, // 中區網維二部
		SOUTHERN_NETWORK_MGR_MAINT_1ST_SECTOR, // 南區網維一部
		SOUTHERN_NETWORK_MGR_MAINT_2ND_SECTOR// 南區網維二部
	}

	protected enum posIdCodeFromLookup {
		WF_WL_ENGINEER, // 無線優化部工程師職稱代號(簽核用)
		WF_RF_ENGINEER, // 射頻優化部工程師職稱代號(簽核用)
		WF_DEPT_MANAGER, // 部主管職稱代號(簽核用)
		WF_DIV_MANAGER, // 處主管職稱代號(簽核用)
		WF_VICE_GENERAL_MANAGER, // 副總經理職稱代號(簽核用)
		WF_LEASE_LINE_CHARGE, // 專線經辦職稱代號(建站-簽核用)
		WF_LEASE_ACCOUNTING_CHARGE, // 會計經辦職稱代號(租約-簽核用)
		WF_LEASE_LOGISTICS_CHARGE, // 工務處後勤管理經辦職稱代號(租約-簽核用)
		WF_LEASE_PROJECT_CHARGE, // 各區專案部經辦職稱代號(租約-簽核用)
		WF_PAY_RENT_CHARGE, // 租金經辦職稱代號(請款-簽核用)
		WF_PAY_ELECTRIC_BILL_CHARGE, // 電費經辦職稱代號(請款-簽核用)
		WF_PAY_OTHERS_CHARGE, // 雜項經辦職稱代號(請款-簽核用)
		WF_PAY_VOIDED_CHECK_CHARGE // 支票作廢經辦職稱代號(請款-簽核用)
	}

	protected AbstractExecutor(final OrgWorkflowService orgWorkflowService,
			final EmailTemplateService emailTemplateService, final UserDTO executor,
			Map<String, Object> extraData) {
		this.orgWorkflowService = orgWorkflowService;
		this.emailTemplateService = emailTemplateService;
		this.executor = executor;
		this.extraData = extraData;
		initialize();
	}

	private void initialize() {
		List<TbSysLookup> lookups = getSysLookups();
		for (TbSysLookup lookup : lookups) {
			String type = lookup.getCODE();
			String value = lookup.getVALUE1();
			if (StringUtils.equalsIgnoreCase(type, deptIdCodeFromLookup.LOGISTICS_DEPT_ID.name())) {
				logisticsDeptId = value;
			} else if (StringUtils.equalsIgnoreCase(type,
					deptIdCodeFromLookup.NORTHERN_ONE_REGION_NETWORK_MGR_MAINT_1ST_SECTOR.name())) {
				northern1RegionNetworkMgrMaint1stSectorDeptId = value;
			} else if (StringUtils.equalsIgnoreCase(type,
					deptIdCodeFromLookup.NORTHERN_ONE_REGION_NETWORK_MGR_MAINT_2ND_SECTOR.name())) {
				northern1RegionNetworkMgrMaint2ndSectorDeptId = value;
			} else if (StringUtils.equalsIgnoreCase(type,
					deptIdCodeFromLookup.NORTHERN_TWO_REGION_NETWORK_MGR_MAINT_1ST_SECTOR.name())) {
				northern2RegionNetworkMgrMaint1stSectorDeptId = value;
			} else if (StringUtils.equalsIgnoreCase(type,
					deptIdCodeFromLookup.NORTHERN_TWO_REGION_NETWORK_MGR_MAINT_2ND_SECTOR.name())) {
				northern2RegionNetworkMgrMaint2ndSectorDeptId = value;
			} else if (StringUtils.equalsIgnoreCase(type,
					deptIdCodeFromLookup.CENTRAL_NETWORK_MGR_MAINT_1ST_SECTOR.name())) {
				centralNetworkMgrMaint1stSectorDeptId = value;
			} else if (StringUtils.equalsIgnoreCase(type,
					deptIdCodeFromLookup.CENTRAL_NETWORK_MGR_MAINT_2ND_SECTOR.name())) {
				centralNetworkMgrMaint2ndSectorDeptId = value;
			} else if (StringUtils.equalsIgnoreCase(type,
					deptIdCodeFromLookup.SOUTHERN_NETWORK_MGR_MAINT_1ST_SECTOR.name())) {
				southernNetworkMgrMaint1stSectorDeptId = value;
			} else if (StringUtils.equalsIgnoreCase(type,
					deptIdCodeFromLookup.SOUTHERN_NETWORK_MGR_MAINT_2ND_SECTOR.name())) {
				southernNetworkMgrMaint2ndSectorDeptId = value;
			} else if (StringUtils.equalsIgnoreCase(type,
					posIdCodeFromLookup.WF_DEPT_MANAGER.name())) {
				deptMgrPosId = value;
			} else if (StringUtils
					.equalsIgnoreCase(type, posIdCodeFromLookup.WF_DIV_MANAGER.name())) {
				divisionMgrPosId = value;
			} else if (StringUtils.equalsIgnoreCase(type,
					posIdCodeFromLookup.WF_VICE_GENERAL_MANAGER.name())) {
				viceGeneralMgrPosId = value;
			} else if (StringUtils
					.equalsIgnoreCase(type, posIdCodeFromLookup.WF_WL_ENGINEER.name())) {
				wirelessOptEngineerPosId = value;
			} else if (StringUtils
					.equalsIgnoreCase(type, posIdCodeFromLookup.WF_RF_ENGINEER.name())) {
				rfOptEngineerPosId = value;
			} else if (StringUtils.equalsIgnoreCase(type,
					posIdCodeFromLookup.WF_LEASE_LINE_CHARGE.name())) {
				siteBuildLeaseLineChargePosId = value;
			} else if (StringUtils.equalsIgnoreCase(type,
					posIdCodeFromLookup.WF_LEASE_ACCOUNTING_CHARGE.name())) {
				leaseAccountingChargePosId = value;
			} else if (StringUtils.equalsIgnoreCase(type,
					posIdCodeFromLookup.WF_LEASE_LOGISTICS_CHARGE.name())) {
				leaseLogisticsChargePosId = value;
			} else if (StringUtils.equalsIgnoreCase(type,
					posIdCodeFromLookup.WF_LEASE_PROJECT_CHARGE.name())) {
				leaseProjectChargePosId = value;
			} else if (StringUtils.equalsIgnoreCase(type,
					posIdCodeFromLookup.WF_PAY_RENT_CHARGE.name())) {
				payRentChargePosId = value;
			} else if (StringUtils.equalsIgnoreCase(type,
					posIdCodeFromLookup.WF_PAY_ELECTRIC_BILL_CHARGE.name())) {
				payElectricBillChargePosId = value;
			} else if (StringUtils.equalsIgnoreCase(type,
					posIdCodeFromLookup.WF_PAY_OTHERS_CHARGE.name())) {
				payOthersChargePosId = value;
			} else if (StringUtils.equalsIgnoreCase(type,
					posIdCodeFromLookup.WF_PAY_VOIDED_CHECK_CHARGE.name())) {
				payVoidedCheckChargePosId = value;
			}
		}
	}

	protected List<TbSysLookup> getSysLookups() {
		Set<String> types = new HashSet<>(orgType.values().length);
		for (orgType type : orgType.values()) {
			types.add(type.name());
		}
		return orgWorkflowService.getSysLookupByType(types);
	}

	protected DeptPosDTO findDeptSpecificWorker(String workerDeptId, String posId) {
		return orgWorkflowService.getDeptPosDtoByDeptIdAndPosId(workerDeptId, posId);
	}

	protected DeptPosDTO findApplicantDeptMgr() {
		return findDeptMgr(executor.getDeptId());
	}

	protected DeptPosDTO findDeptMgr(String deptId) {
		return findAboveUpperUnitMgr(deptId, deptMgrPosId);
	}

	protected DeptPosDTO findApplicantDivisionMgr() {
		return findDivisionMgr(executor.getDeptId());
	}

	protected DeptPosDTO findDivisionMgr(String deptId) {
		return findAboveUpperUnitMgr(deptId, divisionMgrPosId);
	}

	protected DeptPosDTO findApplicantViceGeneral() {
		return findAboveUpperUnitMgr(executor.getDeptId(), viceGeneralMgrPosId);
	}

	protected DeptPosDTO findViceGeneral(String deptId) {
		return findAboveUpperUnitMgr(deptId, viceGeneralMgrPosId);
	}

	protected DeptPosDTO findGreatThanUpperUnitMgr(String workerDeptId, String posId) {
		return findUpperUnitMgr(workerDeptId, posId, false);
	}

	protected DeptPosDTO findAboveUpperUnitMgr(String workerDeptId, String posId) {
		return findUpperUnitMgr(workerDeptId, posId, true);
	}

	private DeptPosDTO findUpperUnitMgr(String workerDeptId, String posId, boolean isIncludeSameDept) {
		// sort by dept level
		List<DeptDTO> deptList = orgWorkflowService.searchParentByChild(workerDeptId);
		for (DeptDTO deptDto : deptList) {
			String deptId = deptDto.getDEPT_ID();
			if (StringUtils.equals(workerDeptId, deptId) && !isIncludeSameDept) {
				continue;
			}
			DeptPosDTO deptPosDto = orgWorkflowService.getDeptPosDtoByDeptIdAndPosId(deptId, posId);
			if (deptPosDto != null) {
				return deptPosDto;
			}
		}
		return null;
	}

	protected List<DeptPosDTO> findSpecificWorker(String posId) {
		return orgWorkflowService.getDeptPosDtoByPosId(posId);
	}

	protected void setMailVariableFromTemplate(WorkflowEmailType type,
			Map<String, Object> variables, Map<String, Object> parserData) {
		if (variables == null) {
			throw new IllegalArgumentException("Variables is null");
		}
		TbSysEmailTemplate emailTemplate = getTbSysEmailTemplate(type);

		type.setMailSubjectAndContent(variables,
				ParserUtil.getParseStringByMap(parserData, emailTemplate.getSUBJECT()),
				ParserUtil.getParseStringByMap(parserData, emailTemplate.getCONTENT()));
	}

	protected void setMailVariableFromTemplates(Collection<WorkflowEmailType> types,
			Map<String, Object> variables, Map<String, Object> parserData) {
		if (variables == null) {
			throw new IllegalArgumentException("Variables is null");
		}
		if (types == null || types.isEmpty()) {
			throw new IllegalArgumentException("WorkflowEmailType is empty");
		}
		List<TbSysEmailTemplate> emailTemplates = getTbSysEmailTemplates(types);
		if (emailTemplates == null) {
			throw new NomsException("EMail types is empty");
		}
		for (TbSysEmailTemplate emailTemplate : emailTemplates) {
			WorkflowEmailType.valueOf(emailTemplate.getEMAIL_TYPE()).setMailSubjectAndContent(
					variables,
					ParserUtil.getParseStringByMap(parserData, emailTemplate.getSUBJECT()),
					ParserUtil.getParseStringByMap(parserData, emailTemplate.getCONTENT()));
		}

	}

	protected void setMailVariableFromTemplate(WorkflowEmailType type,
			BaseProcessVariables variables, Map<String, Object> parserData) {
		if (variables == null) {
			throw new IllegalArgumentException("Variables BaseProcessVariables object is null");
		}
		if (type == null) {
			throw new IllegalArgumentException("WorkflowEmailType is null");
		}
		TbSysEmailTemplate emailTemplate = getTbSysEmailTemplate(type);
		if (emailTemplate == null) {
			throw new NomsException("EMail type[" + type + "] not found");
		}
		type.setMailSubjectAndContent(variables,
				ParserUtil.getParseStringByMap(parserData, emailTemplate.getSUBJECT()),
				ParserUtil.getParseStringByMap(parserData, emailTemplate.getCONTENT()));
	}

	protected void setMailVariableFromTemplates(Collection<WorkflowEmailType> types,
			BaseProcessVariables variables, Map<String, Object> parserData) {
		if (variables == null) {
			throw new IllegalArgumentException("Variables is null");
		}
		if (types == null || types.isEmpty()) {
			throw new IllegalArgumentException("List of WorkflowEmailType is empty");
		}
		List<TbSysEmailTemplate> emailTemplates = getTbSysEmailTemplates(types);
		if (emailTemplates == null) {
			throw new NomsException("EMail list of type is empty");
		}
		for (TbSysEmailTemplate emailTemplate : emailTemplates) {
			WorkflowEmailType.valueOf(emailTemplate.getEMAIL_TYPE()).setMailSubjectAndContent(
					variables,
					ParserUtil.getParseStringByMap(parserData, emailTemplate.getSUBJECT()),
					ParserUtil.getParseStringByMap(parserData, emailTemplate.getCONTENT()));
		}
	}

	private TbSysEmailTemplate getTbSysEmailTemplate(WorkflowEmailType type) {
		Set<WorkflowEmailType> types = new HashSet<WorkflowEmailType>(1);
		types.add(type);

		List<TbSysEmailTemplate> emailTemplates = getTbSysEmailTemplates(types);
		if (emailTemplates.isEmpty()) {
			return null;
		}
		return emailTemplates.get(0);
	}

	private List<TbSysEmailTemplate> getTbSysEmailTemplates(Collection<WorkflowEmailType> types) {
		Set<String> typeNames = new HashSet<>(types.size());
		for (WorkflowEmailType type : types) {
			typeNames.add(type.name());
		}
		return emailTemplateService.getEmailTemplates(typeNames);
	}

	public abstract BaseProcessVariables getVariables();

}
