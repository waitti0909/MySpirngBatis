package com.foya.noms.workflow.executor;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.foya.noms.dto.auth.UserDTO;
import com.foya.noms.enums.WorkflowEmailType;
import com.foya.noms.service.common.EmailTemplateService;
import com.foya.noms.service.org.OrgWorkflowService;
import com.foya.workflow.exception.WorkflowException;
import com.foya.workflow.model.BaseProcessVariables;

public enum ProcessTypeExecutor {
	// 尋點流程
	SearchSiteApplyNSR {
		public BaseProcessVariables getVariablesObject(final OrgWorkflowService orgService,
				final EmailTemplateService emailService, final UserDTO executor,
				Map<String, Object> extraData) throws WorkflowException {

			Set<WorkflowEmailType> mailTypes = new HashSet<>(5);
			mailTypes.add(WorkflowEmailType.WORKFLOW_APPROVAL_WORK_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_EVER_VERIFY_REJECT_WORK_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_REJECT_WORK_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_TODO_WORK_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_FAIL_IT_HANDLER);

			SearchSiteExecutors executors = new SearchSiteExecutors(orgService, emailService,
					executor, extraData).addApplicantDeptMgr().addApplicantDivisionMgr()
					.addMailVariables(mailTypes);
			return executors.getVariables();
		}
	},
	SearchSiteApplySH {
		public BaseProcessVariables getVariablesObject(final OrgWorkflowService orgService,
				final EmailTemplateService emailService, final UserDTO executor,
				Map<String, Object> extraData) throws WorkflowException {

			Set<WorkflowEmailType> mailTypes = new HashSet<>(5);
			mailTypes.add(WorkflowEmailType.WORKFLOW_APPROVAL_WORK_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_EVER_VERIFY_REJECT_WORK_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_REJECT_WORK_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_TODO_WORK_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_FAIL_IT_HANDLER);

			SearchSiteExecutors executors = new SearchSiteExecutors(orgService, emailService,
					executor, extraData).addApplicantDeptMgr().addApplicantDivisionMgr()
					.addMailVariables(mailTypes);
			return executors.getVariables();
		}
	},
	SearchSiteOutsourcingApply {
		public BaseProcessVariables getVariablesObject(final OrgWorkflowService orgService,
				final EmailTemplateService emailService, final UserDTO executor,
				Map<String, Object> extraData) throws WorkflowException {

			Set<WorkflowEmailType> mailTypes = new HashSet<>(5);
			mailTypes.add(WorkflowEmailType.WORKFLOW_APPROVAL_WORK_OUTSOURCING_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_EVER_VERIFY_REJECT_WORK_OUTSOURCING_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_REJECT_WORK_OUTSOURCING_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_TODO_WORK_OUTSOURCING_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_FAIL_IT_HANDLER);

			SearchSiteExecutors executors = new SearchSiteExecutors(orgService, emailService,
					executor, extraData).addApplicantDeptMgr().addApplicantDivisionMgr()
					.addMailVariables(mailTypes);
			return executors.getVariables();
		}
	},
	SearchSiteOutsourcingAccept {
		public BaseProcessVariables getVariablesObject(final OrgWorkflowService orgService,
				final EmailTemplateService emailService, final UserDTO executor,
				Map<String, Object> extraData) throws WorkflowException {

			Set<WorkflowEmailType> mailTypes = new HashSet<>(5);
			mailTypes.add(WorkflowEmailType.WORKFLOW_APPROVAL_WORK_OUTSOURCING_ACCEPT);
			mailTypes.add(WorkflowEmailType.WORKFLOW_EVER_VERIFY_REJECT_WORK_OUTSOURCING_ACCEPT);
			mailTypes.add(WorkflowEmailType.WORKFLOW_REJECT_WORK_OUTSOURCING_ACCEPT);
			mailTypes.add(WorkflowEmailType.WORKFLOW_TODO_WORK_OUTSOURCING_ACCEPT);
			mailTypes.add(WorkflowEmailType.WORKFLOW_FAIL_IT_HANDLER);

			SearchSiteExecutors executors = new SearchSiteExecutors(orgService, emailService,
					executor, extraData).addApplicantDeptMgr().addMailVariables(mailTypes);
			return executors.getVariables();
		}
	},
	SearchSiteCompletionNSR {
		public BaseProcessVariables getVariablesObject(final OrgWorkflowService orgService,
				final EmailTemplateService emailService, final UserDTO executor,
				Map<String, Object> extraData) throws WorkflowException {

			Set<WorkflowEmailType> mailTypes = new HashSet<>(5);
			mailTypes.add(WorkflowEmailType.WORKFLOW_APPROVAL_WORK_COMPLETION_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_EVER_VERIFY_REJECT_WORK_COMPLETION_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_REJECT_WORK_COMPLETION_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_TODO_WORK_COMPLETION_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_FAIL_IT_HANDLER);

			SearchSiteExecutors executors = new SearchSiteExecutors(orgService, emailService,
					executor, extraData).addApplicantDeptMgr().addApplicantDivisionMgr()
					.addMailVariables(mailTypes);
			return executors.getVariables();
		}
	},
	SearchSiteCompletionSH {
		public BaseProcessVariables getVariablesObject(final OrgWorkflowService orgService,
				final EmailTemplateService emailService, final UserDTO executor,
				Map<String, Object> extraData) throws WorkflowException {

			Set<WorkflowEmailType> mailTypes = new HashSet<>(5);
			mailTypes.add(WorkflowEmailType.WORKFLOW_APPROVAL_WORK_COMPLETION_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_EVER_VERIFY_REJECT_WORK_COMPLETION_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_REJECT_WORK_COMPLETION_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_TODO_WORK_COMPLETION_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_FAIL_IT_HANDLER);

			SearchSiteExecutors executors = new SearchSiteExecutors(orgService, emailService,
					executor, extraData).addApplicantDeptMgr().addWirelessOptEngineer()
					.addWirelessOptDeptMgr().addWirelessOptDivisionMgr().addRfOptEngineer()
					.addRfOptDeptMgr().addRfOptDivisionMgr().addMailVariables(mailTypes);
			return executors.getVariables();

		}
	},
	// 資材流程
	TransferApplyForLogistics {
		@Override
		public BaseProcessVariables getVariablesObject(final OrgWorkflowService orgService,
				final EmailTemplateService emailService, final UserDTO executor,
				Map<String, Object> extraData) throws WorkflowException {

			Set<WorkflowEmailType> mailTypes = new HashSet<>(5);
			mailTypes.add(WorkflowEmailType.WORKFLOW_APPROVAL_MATERIAL_AND_RENT_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_EVER_VERIFY_REJECT_MATERIAL_AND_RENT_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_REJECT_MATERIAL_AND_RENT_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_TODO_MATERIAL_AND_RENT_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_FAIL_IT_HANDLER);

			MaterialExecutors executors = new MaterialExecutors(orgService, emailService, executor,
					extraData).addApplicantDeptMgr().addApplicantDivisionMgr()
					.addMailVariables(mailTypes);

			return executors.getVariables();
		}

	},
	TransferApply {
		@Override
		public BaseProcessVariables getVariablesObject(final OrgWorkflowService orgService,
				final EmailTemplateService emailService, final UserDTO executor,
				Map<String, Object> extraData) throws WorkflowException {

			Set<WorkflowEmailType> mailTypes = new HashSet<>(5);
			mailTypes.add(WorkflowEmailType.WORKFLOW_APPROVAL_MATERIAL_AND_RENT_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_EVER_VERIFY_REJECT_MATERIAL_AND_RENT_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_REJECT_MATERIAL_AND_RENT_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_TODO_MATERIAL_AND_RENT_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_FAIL_IT_HANDLER);

			MaterialExecutors executors = new MaterialExecutors(orgService, emailService, executor,
					extraData).addApplicantDeptMgr().addApplicantDivisionMgr()
					.addLogisticsDeptMgr().addLogisticsDivisionMgr().addMailVariables(mailTypes);
			return executors.getVariables();
		}

	},
	InventoryStatusChange {
		@Override
		public BaseProcessVariables getVariablesObject(final OrgWorkflowService orgService,
				final EmailTemplateService emailService, final UserDTO executor,
				Map<String, Object> extraData) throws WorkflowException {

			Set<WorkflowEmailType> mailTypes = new HashSet<>(5);
			mailTypes.add(WorkflowEmailType.WORKFLOW_APPROVAL_MATERIAL_AND_RENT_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_EVER_VERIFY_REJECT_MATERIAL_AND_RENT_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_REJECT_MATERIAL_AND_RENT_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_TODO_MATERIAL_AND_RENT_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_FAIL_IT_HANDLER);

			MaterialExecutors executors = new MaterialExecutors(orgService, emailService, executor,
					extraData).addApplicantDeptMgr().addApplicantDivisionMgr()
					.addMailVariables(mailTypes);
			return executors.getVariables();
		}
	},
	// 建站流程
	SiteBuildApply {
		@Override
		public BaseProcessVariables getVariablesObject(final OrgWorkflowService orgService,
				final EmailTemplateService emailService, final UserDTO executor,
				Map<String, Object> extraData) throws WorkflowException {

			Set<WorkflowEmailType> mailTypes = new HashSet<>(5);
			mailTypes.add(WorkflowEmailType.WORKFLOW_APPROVAL_WORK_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_EVER_VERIFY_REJECT_WORK_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_REJECT_WORK_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_TODO_WORK_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_FAIL_IT_HANDLER);

			SiteBuildExecutors executors = new SiteBuildExecutors(orgService, emailService,
					executor, extraData).addApplicantDeptMgr().addApplicantDivisionMgr()
					.addMailVariables(mailTypes);
			return executors.getVariables();
		}

	},
	SiteBuildOutsourcingApply {
		@Override
		public BaseProcessVariables getVariablesObject(final OrgWorkflowService orgService,
				final EmailTemplateService emailService, final UserDTO executor,
				Map<String, Object> extraData) throws WorkflowException {

			Set<WorkflowEmailType> mailTypes = new HashSet<>(5);
			mailTypes.add(WorkflowEmailType.WORKFLOW_APPROVAL_WORK_OUTSOURCING_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_EVER_VERIFY_REJECT_WORK_OUTSOURCING_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_REJECT_WORK_OUTSOURCING_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_TODO_WORK_OUTSOURCING_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_FAIL_IT_HANDLER);

			SiteBuildExecutors executors = new SiteBuildExecutors(orgService, emailService,
					executor, extraData).addApplicantDeptMgr().addMailVariables(mailTypes);
			return executors.getVariables();
		}
	},
	SiteBuildOutsourcingAccept {
		@Override
		public BaseProcessVariables getVariablesObject(final OrgWorkflowService orgService,
				final EmailTemplateService emailService, final UserDTO executor,
				Map<String, Object> extraData) throws WorkflowException {

			Set<WorkflowEmailType> mailTypes = new HashSet<>(5);
			mailTypes.add(WorkflowEmailType.WORKFLOW_APPROVAL_WORK_OUTSOURCING_ACCEPT);
			mailTypes.add(WorkflowEmailType.WORKFLOW_EVER_VERIFY_REJECT_WORK_OUTSOURCING_ACCEPT);
			mailTypes.add(WorkflowEmailType.WORKFLOW_REJECT_WORK_OUTSOURCING_ACCEPT);
			mailTypes.add(WorkflowEmailType.WORKFLOW_TODO_WORK_OUTSOURCING_ACCEPT);
			mailTypes.add(WorkflowEmailType.WORKFLOW_FAIL_IT_HANDLER);

			SiteBuildExecutors executors = new SiteBuildExecutors(orgService, emailService,
					executor, extraData).addApplicantDeptMgr().addMailVariables(mailTypes);
			return executors.getVariables();
		}
	},
	SiteBuildLeaseLineApply {
		@Override
		public BaseProcessVariables getVariablesObject(final OrgWorkflowService orgService,
				final EmailTemplateService emailService, final UserDTO executor,
				Map<String, Object> extraData) throws WorkflowException {

			Set<WorkflowEmailType> mailTypes = new HashSet<>(5);
			mailTypes.add(WorkflowEmailType.WORKFLOW_APPROVAL_WORK_LEASE_LINE_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_EVER_VERIFY_REJECT_WORK_LEASE_LINE_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_REJECT_WORK_LEASE_LINE_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_TODO_WORK_LEASE_LINE_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_FAIL_IT_HANDLER);

			SiteBuildExecutors executors = new SiteBuildExecutors(orgService, emailService,
					executor, extraData).addLeaseLineCharge().addMailVariables(mailTypes);
			return executors.getVariables();
		}
	},
	SiteBuildMaterialApply {
		@Override
		public BaseProcessVariables getVariablesObject(final OrgWorkflowService orgService,
				final EmailTemplateService emailService, final UserDTO executor,
				Map<String, Object> extraData) throws WorkflowException {

			Set<WorkflowEmailType> mailTypes = new HashSet<>(5);
			mailTypes.add(WorkflowEmailType.WORKFLOW_APPROVAL_WORK_MATERIAL_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_EVER_VERIFY_REJECT_WORK_MATERIAL_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_REJECT_WORK_MATERIAL_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_TODO_WORK_MATERIAL_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_FAIL_IT_HANDLER);

			SiteBuildExecutors executors = new SiteBuildExecutors(orgService, emailService,
					executor, extraData).addApplicantDeptMgr().addMailVariables(mailTypes);
			return executors.getVariables();
		}
	},
	SiteBuildCompletionApply {
		@Override
		public BaseProcessVariables getVariablesObject(final OrgWorkflowService orgService,
				final EmailTemplateService emailService, final UserDTO executor,
				Map<String, Object> extraData) throws WorkflowException {

			Set<WorkflowEmailType> mailTypes = new HashSet<>(5);
			mailTypes.add(WorkflowEmailType.WORKFLOW_APPROVAL_WORK_COMPLETION_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_EVER_VERIFY_REJECT_WORK_COMPLETION_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_REJECT_WORK_COMPLETION_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_TODO_WORK_COMPLETION_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_FAIL_IT_HANDLER);

			SiteBuildExecutors executors = new SiteBuildExecutors(orgService, emailService,
					executor, extraData).addApplicantDeptMgr().addMailVariables(mailTypes);
			return executors.getVariables();
		}
	},
	SiteBuildSingleWorkApply {
		@Override
		public BaseProcessVariables getVariablesObject(final OrgWorkflowService orgService,
				final EmailTemplateService emailService, final UserDTO executor,
				Map<String, Object> extraData) throws WorkflowException {

			Set<WorkflowEmailType> mailTypes = new HashSet<>(5);
			mailTypes.add(WorkflowEmailType.WORKFLOW_APPROVAL_WORK_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_EVER_VERIFY_REJECT_WORK_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_REJECT_WORK_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_TODO_WORK_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_FAIL_IT_HANDLER);

			SiteBuildExecutors executors = new SiteBuildExecutors(orgService, emailService,
					executor, extraData).addApplicantDeptMgr().addApplicantDivisionMgr()
					.addMailVariables(mailTypes);
			return executors.getVariables();
		}

	},
	SiteBuildSingleWorkCompletionApply {
		@Override
		public BaseProcessVariables getVariablesObject(final OrgWorkflowService orgService,
				final EmailTemplateService emailService, final UserDTO executor,
				Map<String, Object> extraData) throws WorkflowException {

			Set<WorkflowEmailType> mailTypes = new HashSet<>(5);
			mailTypes.add(WorkflowEmailType.WORKFLOW_APPROVAL_WORK_COMPLETION_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_EVER_VERIFY_REJECT_WORK_COMPLETION_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_REJECT_WORK_COMPLETION_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_TODO_WORK_COMPLETION_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_FAIL_IT_HANDLER);

			SiteBuildExecutors executors = new SiteBuildExecutors(orgService, emailService,
					executor, extraData).addApplicantDeptMgr().addMailVariables(mailTypes);
			return executors.getVariables();
		}
	},
	// 租約流程
	NewLease {
		@Override
		public BaseProcessVariables getVariablesObject(final OrgWorkflowService orgService,
				final EmailTemplateService emailService, final UserDTO executor,
				Map<String, Object> extraData) throws WorkflowException {

			Set<WorkflowEmailType> mailTypes = new HashSet<>(5);
			mailTypes.add(WorkflowEmailType.WORKFLOW_APPROVAL_MATERIAL_AND_RENT_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_EVER_VERIFY_REJECT_MATERIAL_AND_RENT_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_REJECT_MATERIAL_AND_RENT_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_TODO_MATERIAL_AND_RENT_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_FAIL_IT_HANDLER);

			LeaseExecutors executors = new LeaseExecutors(orgService, emailService, executor,
					extraData).addProjectCharge().addProjectDeptMgr().addLogisticsCharge()
					.addLogisticsDeptMgr().addAccountingCharge().addAccountingDeptMgr()
					.addMailVariables(mailTypes);
			return executors.getVariables();
		}
	},
	ContinueLease {
		@Override
		public BaseProcessVariables getVariablesObject(final OrgWorkflowService orgService,
				final EmailTemplateService emailService, final UserDTO executor,
				Map<String, Object> extraData) throws WorkflowException {

			Set<WorkflowEmailType> mailTypes = new HashSet<>(5);
			mailTypes.add(WorkflowEmailType.WORKFLOW_APPROVAL_MATERIAL_AND_RENT_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_EVER_VERIFY_REJECT_MATERIAL_AND_RENT_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_REJECT_MATERIAL_AND_RENT_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_TODO_MATERIAL_AND_RENT_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_FAIL_IT_HANDLER);

			LeaseExecutors executors = new LeaseExecutors(orgService, emailService, executor,
					extraData).addProjectCharge().addProjectDeptMgr().addLogisticsCharge()
					.addLogisticsDeptMgr().addAccountingCharge().addAccountingDeptMgr()
					.addMailVariables(mailTypes);
			return executors.getVariables();
		}
	},
	ChangeLease {
		@Override
		public BaseProcessVariables getVariablesObject(final OrgWorkflowService orgService,
				final EmailTemplateService emailService, final UserDTO executor,
				Map<String, Object> extraData) throws WorkflowException {

			Set<WorkflowEmailType> mailTypes = new HashSet<>(5);
			mailTypes.add(WorkflowEmailType.WORKFLOW_APPROVAL_MATERIAL_AND_RENT_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_EVER_VERIFY_REJECT_MATERIAL_AND_RENT_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_REJECT_MATERIAL_AND_RENT_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_TODO_MATERIAL_AND_RENT_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_FAIL_IT_HANDLER);

			LeaseExecutors executors = new LeaseExecutors(orgService, emailService, executor,
					extraData).addProjectCharge().addProjectDeptMgr().addLogisticsCharge()
					.addLogisticsDeptMgr().addAccountingCharge().addAccountingDeptMgr()
					.addMailVariables(mailTypes);
			return executors.getVariables();
		}
	},
	ReSignLease {
		@Override
		public BaseProcessVariables getVariablesObject(final OrgWorkflowService orgService,
				final EmailTemplateService emailService, final UserDTO executor,
				Map<String, Object> extraData) throws WorkflowException {

			Set<WorkflowEmailType> mailTypes = new HashSet<>(5);
			mailTypes.add(WorkflowEmailType.WORKFLOW_APPROVAL_MATERIAL_AND_RENT_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_EVER_VERIFY_REJECT_MATERIAL_AND_RENT_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_REJECT_MATERIAL_AND_RENT_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_TODO_MATERIAL_AND_RENT_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_FAIL_IT_HANDLER);

			LeaseExecutors executors = new LeaseExecutors(orgService, emailService, executor,
					extraData).addProjectCharge().addProjectDeptMgr().addLogisticsCharge()
					.addLogisticsDeptMgr().addAccountingCharge().addAccountingDeptMgr()
					.addMailVariables(mailTypes);
			return executors.getVariables();
		}
	},
	CancelLease {
		@Override
		public BaseProcessVariables getVariablesObject(final OrgWorkflowService orgService,
				final EmailTemplateService emailService, final UserDTO executor,
				Map<String, Object> extraData) throws WorkflowException {

			Set<WorkflowEmailType> mailTypes = new HashSet<>(5);
			mailTypes.add(WorkflowEmailType.WORKFLOW_APPROVAL_MATERIAL_AND_RENT_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_EVER_VERIFY_REJECT_MATERIAL_AND_RENT_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_REJECT_MATERIAL_AND_RENT_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_TODO_MATERIAL_AND_RENT_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_FAIL_IT_HANDLER);

			LeaseExecutors executors = new LeaseExecutors(orgService, emailService, executor,
					extraData).addProjectCharge().addProjectDeptMgr().addLogisticsCharge()
					.addLogisticsDeptMgr().addAccountingCharge().addAccountingDeptMgr()
					.addMailVariables(mailTypes);
			return executors.getVariables();
		}
	},
	ExtraLease {
		@Override
		public BaseProcessVariables getVariablesObject(final OrgWorkflowService orgService,
				final EmailTemplateService emailService, final UserDTO executor,
				Map<String, Object> extraData) throws WorkflowException {

			Set<WorkflowEmailType> mailTypes = new HashSet<>(5);
			mailTypes.add(WorkflowEmailType.WORKFLOW_APPROVAL_MATERIAL_AND_RENT_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_EVER_VERIFY_REJECT_MATERIAL_AND_RENT_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_REJECT_MATERIAL_AND_RENT_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_TODO_MATERIAL_AND_RENT_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_FAIL_IT_HANDLER);

			LeaseExecutors executors = new LeaseExecutors(orgService, emailService, executor,
					extraData).addProjectCharge().addProjectDeptMgr().addLogisticsCharge()
					.addLogisticsDeptMgr().addAccountingCharge().addAccountingDeptMgr()
					.addMailVariables(mailTypes);
			return executors.getVariables();
		}
	},
	// 請款流程
	PayRent {
		@Override
		public BaseProcessVariables getVariablesObject(final OrgWorkflowService orgService,
				final EmailTemplateService emailService, final UserDTO executor,
				Map<String, Object> extraData) throws WorkflowException {

			Set<WorkflowEmailType> mailTypes = new HashSet<>(5);
			mailTypes.add(WorkflowEmailType.WORKFLOW_APPROVAL_MATERIAL_AND_RENT_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_EVER_VERIFY_REJECT_MATERIAL_AND_RENT_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_REJECT_MATERIAL_AND_RENT_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_TODO_MATERIAL_AND_RENT_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_FAIL_IT_HANDLER);

			PayExecutors executors = new PayExecutors(orgService, emailService, executor, extraData)
					.addNetworkMgrMaint1stSectorDeptMgr().addNetworkMgrMaint2ndSectorDeptMgr()
					.addApplicantDivisionMgr().addLogisticsRentCharge().addLogisticsDeptMgr()
					.addLogisticsDivisionMgr().addLogisticsViceGeneral()
					.addMailVariables(mailTypes);
			return executors.getVariables();
		}
	},
	PayElectricBill {
		@Override
		public BaseProcessVariables getVariablesObject(final OrgWorkflowService orgService,
				final EmailTemplateService emailService, final UserDTO executor,
				Map<String, Object> extraData) throws WorkflowException {

			Set<WorkflowEmailType> mailTypes = new HashSet<>(5);
			mailTypes.add(WorkflowEmailType.WORKFLOW_APPROVAL_MATERIAL_AND_RENT_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_EVER_VERIFY_REJECT_MATERIAL_AND_RENT_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_REJECT_MATERIAL_AND_RENT_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_TODO_MATERIAL_AND_RENT_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_FAIL_IT_HANDLER);

			PayExecutors executors = new PayExecutors(orgService, emailService, executor, extraData)
					.addNetworkMgrMaint1stSectorDeptMgr().addNetworkMgrMaint2ndSectorDeptMgr()
					.addApplicantDivisionMgr().addLogisticsElectricBillCharge()
					.addLogisticsDeptMgr().addLogisticsDivisionMgr().addLogisticsViceGeneral()
					.addMailVariables(mailTypes);
			return executors.getVariables();
		}
	},
	PayOthers {
		@Override
		public BaseProcessVariables getVariablesObject(OrgWorkflowService orgService,
				EmailTemplateService emailService, UserDTO executor, Map<String, Object> extraData)
				throws WorkflowException {
			Set<WorkflowEmailType> mailTypes = new HashSet<>(5);
			mailTypes.add(WorkflowEmailType.WORKFLOW_APPROVAL_MATERIAL_AND_RENT_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_EVER_VERIFY_REJECT_MATERIAL_AND_RENT_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_REJECT_MATERIAL_AND_RENT_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_TODO_MATERIAL_AND_RENT_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_FAIL_IT_HANDLER);

			PayExecutors executors = new PayExecutors(orgService, emailService, executor, extraData)
					.addApplicantDeptMgr().addApplicantDivisionMgr().addLogisticsOthersCharge()
					.addLogisticsDeptMgr().addLogisticsDivisionMgr().addLogisticsViceGeneral()
					.addMailVariables(mailTypes);
			return executors.getVariables();
		}

	},
	PayLeaseLine {
		@Override
		public BaseProcessVariables getVariablesObject(OrgWorkflowService orgService,
				EmailTemplateService emailService, UserDTO executor, Map<String, Object> extraData)
				throws WorkflowException {
			Set<WorkflowEmailType> mailTypes = new HashSet<>(5);
			mailTypes.add(WorkflowEmailType.WORKFLOW_APPROVAL_MATERIAL_AND_RENT_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_EVER_VERIFY_REJECT_MATERIAL_AND_RENT_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_REJECT_MATERIAL_AND_RENT_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_TODO_MATERIAL_AND_RENT_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_FAIL_IT_HANDLER);

			PayExecutors executors = new PayExecutors(orgService, emailService, executor, extraData)
					.addApplicantDeptMgr().addApplicantDivisionMgr().addApplicantViceGeneral()
					.addMailVariables(mailTypes);
			return executors.getVariables();
		}
	},
	PayVoidedCheck {
		@Override
		public BaseProcessVariables getVariablesObject(OrgWorkflowService orgService,
				EmailTemplateService emailService, UserDTO executor, Map<String, Object> extraData)
				throws WorkflowException {
			Set<WorkflowEmailType> mailTypes = new HashSet<>(5);
			mailTypes.add(WorkflowEmailType.WORKFLOW_APPROVAL_MATERIAL_AND_RENT_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_EVER_VERIFY_REJECT_MATERIAL_AND_RENT_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_REJECT_MATERIAL_AND_RENT_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_TODO_MATERIAL_AND_RENT_APPLY);
			mailTypes.add(WorkflowEmailType.WORKFLOW_FAIL_IT_HANDLER);

			PayExecutors executors = new PayExecutors(orgService, emailService, executor, extraData)
					.addApplicantDeptMgr().addApplicantDivisionMgr()
					.addLogisticsVoidedCheckCharge().addLogisticsDeptMgr()
					.addLogisticsDivisionMgr().addLogisticsViceGeneral()
					.addMailVariables(mailTypes);
			return executors.getVariables();
		}
	};

	public abstract BaseProcessVariables getVariablesObject(final OrgWorkflowService orgService,
			final EmailTemplateService emailService, final UserDTO executor,
			Map<String, Object> extraData) throws WorkflowException;
}
