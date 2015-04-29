package com.foya.noms.dto.workflow;

import com.foya.workflow.model.TodoInfo;

public class WorkflowTodoDTO extends TodoInfo {
	private String applicantName;
	private String processName;
	private String allowBatch;
	private String taskOwnerDeptName;
	private String taskOwnerName;

	public WorkflowTodoDTO() {

	}

	public WorkflowTodoDTO(TodoInfo todoInfo) {
		this.setApplicantId(todoInfo.getApplicantId());
		this.setApplyDescription(todoInfo.getApplyDescription());
		this.setApplyNo(todoInfo.getApplyNo());
		this.setProcessId(todoInfo.getProcessId());
		this.setProcessInstanceId(todoInfo.getProcessInstanceId());
		this.setApplyDate(todoInfo.getApplyDate());
		this.setProcessType(todoInfo.getProcessType());
		this.setStatus(todoInfo.getStatus());
		this.setTaskId(todoInfo.getTaskId());
		this.setTaskName(todoInfo.getTaskName());
		this.setTaskOwnerId(todoInfo.getTaskOwnerId());
		this.setTaskOwnerGroupId(todoInfo.getTaskOwnerGroupId());
		this.setTaskStartTime(todoInfo.getTaskStartTime());
		this.setUserTaskType(todoInfo.getUserTaskType());
		this.setJsonData(todoInfo.getJsonData());
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getAllowBatch() {
		return allowBatch;
	}

	public void setAllowBatch(String allowBatch) {
		this.allowBatch = allowBatch;
	}

	public String getTaskOwnerDeptName() {
		return taskOwnerDeptName;
	}

	public void setTaskOwnerDeptName(String taskOwnerDeptName) {
		this.taskOwnerDeptName = taskOwnerDeptName;
	}

	public String getTaskOwnerName() {
		return taskOwnerName;
	}

	public void setTaskOwnerName(String taskOwnerName) {
		this.taskOwnerName = taskOwnerName;
	}

}
