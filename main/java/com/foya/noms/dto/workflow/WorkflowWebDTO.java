package com.foya.noms.dto.workflow;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class WorkflowWebDTO {
	private long taskId;
	private String applyNo;
	private String processType;
	private String taskOwnerId;
	private String taskOwnerGroupId;
	private String taskName;
	private String applyDescription;
	private String applicantDeptId;
	private String applicantDeptPosId;
	private String applicantId;
	private String applicantName;
	private String comment;
	private String jsonData;
	private String[] notifys;
	private String[] countersigns;
	private Date processStartTime;
	private Date taskStartTime;
	private Map<String, Object> extraData;

	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	public String getProcessType() {
		return processType;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskOwnerId() {
		return taskOwnerId;
	}

	public void setTaskOwnerId(String taskOwnerId) {
		this.taskOwnerId = taskOwnerId;
	}

	public String getTaskOwnerGroupId() {
		return taskOwnerGroupId;
	}

	public void setTaskOwnerGroupId(String taskOwnerGroupId) {
		this.taskOwnerGroupId = taskOwnerGroupId;
	}

	public String getApplyDescription() {
		return applyDescription;
	}

	public void setApplyDescription(String applyDescription) {
		this.applyDescription = applyDescription;
	}

	public String getApplicantDeptId() {
		return applicantDeptId;
	}

	public void setApplicantDeptId(String applicantDeptId) {
		this.applicantDeptId = applicantDeptId;
	}

	public String getApplicantDeptPosId() {
		return applicantDeptPosId;
	}

	public void setApplicantDeptPosId(String applicantDeptPosId) {
		this.applicantDeptPosId = applicantDeptPosId;
	}

	public String getApplicantId() {
		return applicantId;
	}

	public void setApplicantId(String applicantId) {
		this.applicantId = applicantId;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	public String[] getNotifys() {
		return notifys;
	}

	public void setNotifys(String[] notifys) {
		this.notifys = notifys;
	}

	public String[] getCountersigns() {
		return countersigns;
	}

	public void setCountersigns(String[] countersigns) {
		this.countersigns = countersigns;
	}

	public Date getProcessStartTime() {
		return processStartTime;
	}

	public void setProcessStartTime(Date processStartTime) {
		this.processStartTime = processStartTime;
	}

	public Date getTaskStartTime() {
		return taskStartTime;
	}

	public void setTaskStartTime(Date taskStartTime) {
		this.taskStartTime = taskStartTime;
	}

	public Map<String, Object> getExtraData() {
		if (extraData == null) {
			extraData = new HashMap<>();
		}
		return extraData;
	}

	public void setExtraData(Map<String, Object> extraData) {
		this.extraData = extraData;
	}
}
