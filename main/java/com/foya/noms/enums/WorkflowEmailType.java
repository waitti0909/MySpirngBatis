package com.foya.noms.enums;

import java.util.Map;

import com.foya.workflow.model.BaseProcessVariables;

public enum WorkflowEmailType {
	WORKFLOW_APPROVAL_WORK_LEASE_LINE_APPLY {
		@Override
		public void setMailSubjectAndContent(BaseProcessVariables vars, String subject,
				String content) {
			vars.setFinalApprovalMailSubject(new String[] { subject });
			vars.setFinalApprovalMailContent(new String[] { content });
		}

		@Override
		public void setMailSubjectAndContent(Map<String, Object> vars, String subject,
				String content) {
			vars.put("finalApprovalMailSubject", new String[] { subject });
			vars.put("finalApprovalMailContent", new String[] { content });
		}
	},
	WORKFLOW_APPROVAL_MATERIAL_AND_RENT_APPLY {
		@Override
		public void setMailSubjectAndContent(BaseProcessVariables vars, String subject,
				String content) {
			vars.setFinalApprovalMailSubject(new String[] { subject });
			vars.setFinalApprovalMailContent(new String[] { content });
		}

		@Override
		public void setMailSubjectAndContent(Map<String, Object> vars, String subject,
				String content) {
			vars.put("finalApprovalMailSubject", new String[] { subject });
			vars.put("finalApprovalMailContent", new String[] { content });
		}
	},
	WORKFLOW_APPROVAL_WORK_APPLY {
		@Override
		public void setMailSubjectAndContent(BaseProcessVariables vars, String subject,
				String content) {
			vars.setFinalApprovalMailSubject(new String[] { subject });
			vars.setFinalApprovalMailContent(new String[] { content });
		}

		@Override
		public void setMailSubjectAndContent(Map<String, Object> vars, String subject,
				String content) {
			vars.put("finalApprovalMailSubject", new String[] { subject });
			vars.put("finalApprovalMailContent", new String[] { content });
		}
	},
	WORKFLOW_APPROVAL_WORK_COMPLETION_APPLY {
		@Override
		public void setMailSubjectAndContent(BaseProcessVariables vars, String subject,
				String content) {
			vars.setFinalApprovalMailSubject(new String[] { subject });
			vars.setFinalApprovalMailContent(new String[] { content });
		}

		@Override
		public void setMailSubjectAndContent(Map<String, Object> vars, String subject,
				String content) {
			vars.put("finalApprovalMailSubject", new String[] { subject });
			vars.put("finalApprovalMailContent", new String[] { content });
		}
	},
	WORKFLOW_APPROVAL_WORK_MATERIAL_APPLY {
		@Override
		public void setMailSubjectAndContent(BaseProcessVariables vars, String subject,
				String content) {
			vars.setFinalApprovalMailSubject(new String[] { subject });
			vars.setFinalApprovalMailContent(new String[] { content });
		}

		@Override
		public void setMailSubjectAndContent(Map<String, Object> vars, String subject,
				String content) {
			vars.put("finalApprovalMailSubject", new String[] { subject });
			vars.put("finalApprovalMailContent", new String[] { content });
		}
	},
	WORKFLOW_APPROVAL_WORK_OUTSOURCING_ACCEPT {
		@Override
		public void setMailSubjectAndContent(BaseProcessVariables vars, String subject,
				String content) {
			vars.setFinalApprovalMailSubject(new String[] { subject });
			vars.setFinalApprovalMailContent(new String[] { content });
		}

		@Override
		public void setMailSubjectAndContent(Map<String, Object> vars, String subject,
				String content) {
			vars.put("finalApprovalMailSubject", new String[] { subject });
			vars.put("finalApprovalMailContent", new String[] { content });
		}
	},
	WORKFLOW_APPROVAL_WORK_OUTSOURCING_APPLY {
		@Override
		public void setMailSubjectAndContent(BaseProcessVariables vars, String subject,
				String content) {
			vars.setFinalApprovalMailSubject(new String[] { subject });
			vars.setFinalApprovalMailContent(new String[] { content });
		}

		@Override
		public void setMailSubjectAndContent(Map<String, Object> vars, String subject,
				String content) {
			vars.put("finalApprovalMailSubject", new String[] { subject });
			vars.put("finalApprovalMailContent", new String[] { content });
		}
	},
	WORKFLOW_EVER_VERIFY_REJECT_WORK_LEASE_LINE_APPLY {
		@Override
		public void setMailSubjectAndContent(BaseProcessVariables vars, String subject,
				String content) {
			vars.setEverVerifyRejectMailSubject(new String[] { subject });
			vars.setEverVerifyRejectMailContent(new String[] { content });
		}

		@Override
		public void setMailSubjectAndContent(Map<String, Object> vars, String subject,
				String content) {
			vars.put("everVerifyRejectMailSubject", new String[] { subject });
			vars.put("everVerifyRejectMailContent", new String[] { content });
		}
	},
	WORKFLOW_EVER_VERIFY_REJECT_MATERIAL_AND_RENT_APPLY {
		@Override
		public void setMailSubjectAndContent(BaseProcessVariables vars, String subject,
				String content) {
			vars.setEverVerifyRejectMailSubject(new String[] { subject });
			vars.setEverVerifyRejectMailContent(new String[] { content });
		}

		@Override
		public void setMailSubjectAndContent(Map<String, Object> vars, String subject,
				String content) {
			vars.put("everVerifyRejectMailSubject", new String[] { subject });
			vars.put("everVerifyRejectMailContent", new String[] { content });
		}
	},

	WORKFLOW_EVER_VERIFY_REJECT_WORK_APPLY {
		@Override
		public void setMailSubjectAndContent(BaseProcessVariables vars, String subject,
				String content) {
			vars.setEverVerifyRejectMailSubject(new String[] { subject });
			vars.setEverVerifyRejectMailContent(new String[] { content });
		}

		@Override
		public void setMailSubjectAndContent(Map<String, Object> vars, String subject,
				String content) {
			vars.put("everVerifyRejectMailSubject", new String[] { subject });
			vars.put("everVerifyRejectMailContent", new String[] { content });
		}
	},
	WORKFLOW_EVER_VERIFY_REJECT_WORK_COMPLETION_APPLY {
		@Override
		public void setMailSubjectAndContent(BaseProcessVariables vars, String subject,
				String content) {
			vars.setEverVerifyRejectMailSubject(new String[] { subject });
			vars.setEverVerifyRejectMailContent(new String[] { content });
		}

		@Override
		public void setMailSubjectAndContent(Map<String, Object> vars, String subject,
				String content) {
			vars.put("everVerifyRejectMailSubject", new String[] { subject });
			vars.put("everVerifyRejectMailContent", new String[] { content });
		}
	},
	WORKFLOW_EVER_VERIFY_REJECT_WORK_MATERIAL_APPLY {
		@Override
		public void setMailSubjectAndContent(BaseProcessVariables vars, String subject,
				String content) {
			vars.setEverVerifyRejectMailSubject(new String[] { subject });
			vars.setEverVerifyRejectMailContent(new String[] { content });
		}

		@Override
		public void setMailSubjectAndContent(Map<String, Object> vars, String subject,
				String content) {
			vars.put("everVerifyRejectMailSubject", new String[] { subject });
			vars.put("everVerifyRejectMailContent", new String[] { content });
		}
	},
	WORKFLOW_EVER_VERIFY_REJECT_WORK_OUTSOURCING_ACCEPT {
		@Override
		public void setMailSubjectAndContent(BaseProcessVariables vars, String subject,
				String content) {
			vars.setEverVerifyRejectMailSubject(new String[] { subject });
			vars.setEverVerifyRejectMailContent(new String[] { content });
		}

		@Override
		public void setMailSubjectAndContent(Map<String, Object> vars, String subject,
				String content) {
			vars.put("everVerifyRejectMailSubject", new String[] { subject });
			vars.put("everVerifyRejectMailContent", new String[] { content });
		}
	},
	WORKFLOW_EVER_VERIFY_REJECT_WORK_OUTSOURCING_APPLY {
		@Override
		public void setMailSubjectAndContent(BaseProcessVariables vars, String subject,
				String content) {
			vars.setEverVerifyRejectMailSubject(new String[] { subject });
			vars.setEverVerifyRejectMailContent(new String[] { content });
		}

		@Override
		public void setMailSubjectAndContent(Map<String, Object> vars, String subject,
				String content) {
			vars.put("everVerifyRejectMailSubject", new String[] { subject });
			vars.put("everVerifyRejectMailContent", new String[] { content });
		}
	},
	WORKFLOW_REJECT_WORK_LEASE_LINE_APPLY {
		@Override
		public void setMailSubjectAndContent(BaseProcessVariables vars, String subject,
				String content) {
			vars.setFinalRejectMailSubject(new String[] { subject });
			vars.setFinalRejectMailContent(new String[] { content });
		}

		@Override
		public void setMailSubjectAndContent(Map<String, Object> vars, String subject,
				String content) {
			vars.put("finalRejectMailSubject", new String[] { subject });
			vars.put("finalRejectMailContent", new String[] { content });
		}
	},
	WORKFLOW_REJECT_MATERIAL_AND_RENT_APPLY {
		@Override
		public void setMailSubjectAndContent(BaseProcessVariables vars, String subject,
				String content) {
			vars.setFinalRejectMailSubject(new String[] { subject });
			vars.setFinalRejectMailContent(new String[] { content });
		}

		@Override
		public void setMailSubjectAndContent(Map<String, Object> vars, String subject,
				String content) {
			vars.put("finalRejectMailSubject", new String[] { subject });
			vars.put("finalRejectMailContent", new String[] { content });
		}
	},
	WORKFLOW_REJECT_WORK_APPLY {
		@Override
		public void setMailSubjectAndContent(BaseProcessVariables vars, String subject,
				String content) {
			vars.setFinalRejectMailSubject(new String[] { subject });
			vars.setFinalRejectMailContent(new String[] { content });
		}

		@Override
		public void setMailSubjectAndContent(Map<String, Object> vars, String subject,
				String content) {
			vars.put("finalRejectMailSubject", new String[] { subject });
			vars.put("finalRejectMailContent", new String[] { content });
		}
	},
	WORKFLOW_REJECT_WORK_COMPLETION_APPLY {
		@Override
		public void setMailSubjectAndContent(BaseProcessVariables vars, String subject,
				String content) {
			vars.setFinalRejectMailSubject(new String[] { subject });
			vars.setFinalRejectMailContent(new String[] { content });
		}

		@Override
		public void setMailSubjectAndContent(Map<String, Object> vars, String subject,
				String content) {
			vars.put("finalRejectMailSubject", new String[] { subject });
			vars.put("finalRejectMailContent", new String[] { content });
		}
	},
	WORKFLOW_REJECT_WORK_MATERIAL_APPLY {
		@Override
		public void setMailSubjectAndContent(BaseProcessVariables vars, String subject,
				String content) {
			vars.setFinalRejectMailSubject(new String[] { subject });
			vars.setFinalRejectMailContent(new String[] { content });
		}

		@Override
		public void setMailSubjectAndContent(Map<String, Object> vars, String subject,
				String content) {
			vars.put("finalRejectMailSubject", new String[] { subject });
			vars.put("finalRejectMailContent", new String[] { content });
		}
	},
	WORKFLOW_REJECT_WORK_OUTSOURCING_ACCEPT {
		@Override
		public void setMailSubjectAndContent(BaseProcessVariables vars, String subject,
				String content) {
			vars.setFinalRejectMailSubject(new String[] { subject });
			vars.setFinalRejectMailContent(new String[] { content });
		}

		@Override
		public void setMailSubjectAndContent(Map<String, Object> vars, String subject,
				String content) {
			vars.put("finalRejectMailSubject", new String[] { subject });
			vars.put("finalRejectMailContent", new String[] { content });
		}
	},
	WORKFLOW_REJECT_WORK_OUTSOURCING_APPLY {
		@Override
		public void setMailSubjectAndContent(BaseProcessVariables vars, String subject,
				String content) {
			vars.setFinalRejectMailSubject(new String[] { subject });
			vars.setFinalRejectMailContent(new String[] { content });
		}

		@Override
		public void setMailSubjectAndContent(Map<String, Object> vars, String subject,
				String content) {
			vars.put("finalRejectMailSubject", new String[] { subject });
			vars.put("finalRejectMailContent", new String[] { content });
		}
	},
	WORKFLOW_TODO_MATERIAL_AND_RENT_APPLY {
		@Override
		public void setMailSubjectAndContent(BaseProcessVariables vars, String subject,
				String content) {
			vars.setMailSubject(new String[] { subject });
			vars.setMailContent(new String[] { content });
		}

		@Override
		public void setMailSubjectAndContent(Map<String, Object> vars, String subject,
				String content) {
			vars.put("mailSubject", new String[] { subject });
			vars.put("mailContent", new String[] { content });
		}
	},
	WORKFLOW_TODO_WORK_APPLY {
		@Override
		public void setMailSubjectAndContent(BaseProcessVariables vars, String subject,
				String content) {
			vars.setMailSubject(new String[] { subject });
			vars.setMailContent(new String[] { content });
		}

		@Override
		public void setMailSubjectAndContent(Map<String, Object> vars, String subject,
				String content) {
			vars.put("mailSubject", new String[] { subject });
			vars.put("mailContent", new String[] { content });
		}
	},
	WORKFLOW_TODO_WORK_COMPLETION_APPLY {
		@Override
		public void setMailSubjectAndContent(BaseProcessVariables vars, String subject,
				String content) {
			vars.setMailSubject(new String[] { subject });
			vars.setMailContent(new String[] { content });
		}

		@Override
		public void setMailSubjectAndContent(Map<String, Object> vars, String subject,
				String content) {
			vars.put("mailSubject", new String[] { subject });
			vars.put("mailContent", new String[] { content });
		}
	},
	WORKFLOW_TODO_WORK_LEASE_LINE_APPLY {
		@Override
		public void setMailSubjectAndContent(BaseProcessVariables vars, String subject,
				String content) {
			vars.setMailSubject(new String[] { subject });
			vars.setMailContent(new String[] { content });
		}

		@Override
		public void setMailSubjectAndContent(Map<String, Object> vars, String subject,
				String content) {
			vars.put("mailSubject", new String[] { subject });
			vars.put("mailContent", new String[] { content });
		}
	},
	WORKFLOW_TODO_WORK_MATERIAL_APPLY {
		@Override
		public void setMailSubjectAndContent(BaseProcessVariables vars, String subject,
				String content) {
			vars.setMailSubject(new String[] { subject });
			vars.setMailContent(new String[] { content });
		}

		@Override
		public void setMailSubjectAndContent(Map<String, Object> vars, String subject,
				String content) {
			vars.put("mailSubject", new String[] { subject });
			vars.put("mailContent", new String[] { content });
		}
	},
	WORKFLOW_TODO_WORK_OUTSOURCING_ACCEPT {
		@Override
		public void setMailSubjectAndContent(BaseProcessVariables vars, String subject,
				String content) {
			vars.setMailSubject(new String[] { subject });
			vars.setMailContent(new String[] { content });
		}

		@Override
		public void setMailSubjectAndContent(Map<String, Object> vars, String subject,
				String content) {
			vars.put("mailSubject", new String[] { subject });
			vars.put("mailContent", new String[] { content });
		}
	},
	WORKFLOW_TODO_WORK_OUTSOURCING_APPLY {
		@Override
		public void setMailSubjectAndContent(BaseProcessVariables vars, String subject,
				String content) {
			vars.setMailSubject(new String[] { subject });
			vars.setMailContent(new String[] { content });
		}

		@Override
		public void setMailSubjectAndContent(Map<String, Object> vars, String subject,
				String content) {
			vars.put("mailSubject", new String[] { subject });
			vars.put("mailContent", new String[] { content });
		}
	},
	WORKFLOW_FAIL_IT_HANDLER {
		@Override
		public void setMailSubjectAndContent(BaseProcessVariables vars, String subject,
				String content) {
			vars.setRestFailMailSubject(new String[] { subject });
			vars.setRestFailMailContent(new String[] { content });
		}

		@Override
		public void setMailSubjectAndContent(Map<String, Object> vars, String subject,
				String content) {
			vars.put("restFailMailSubject", new String[] { subject });
			vars.put("restFailMailContent", new String[] { content });
		}
	};
	public abstract void setMailSubjectAndContent(final Map<String, Object> vars, String subject,
			String content);

	public abstract void setMailSubjectAndContent(final BaseProcessVariables vars, String subject,
			String content);
}
