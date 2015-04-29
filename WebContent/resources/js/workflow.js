/**
 * workflow.js for apply, countersign, notify, approve, reject features
 */
// Apply
function workflowApply(processType, applyNo, applyDescription) {
	var arrayMap = [ {
		'applyNo' : applyNo,
		'processType' : processType,
		'applyDescription' : applyDescription
	} ];
	return workflowAction(arrayMap, 'APPLY');
}

function workflowApply(processType, applyNo, applyDescription, extraData) {
	var arrayMap = [ {
		'applyNo' : applyNo,
		'processType' : processType,
		'applyDescription' : applyDescription,
		'extraData' : extraData
	} ];
	return workflowAction(arrayMap, 'APPLY');
}
// Approval
function workflowApproval(taskId, processType, applyNo, taskOwnerGroupId,
		comment, taskStartTime, jsonData) {
	var arrayMap = [ {
		'taskId' : taskId,
		'applyNo' : applyNo,
		'processType' : processType,
		'taskOwnerGroupId' : taskOwnerGroupId,
		'comment' : comment,
		'taskStartTime' : new Date(taskStartTime),
		'jsonData' : jsonData
	} ];
	return workflowAction(arrayMap, 'APPROVAL');
}

// Agent approval
function workflowAgentApproval(taskId, processType, applyNo, taskOwnerId,
		taskOwnerGroupId, comment, taskStartTime, jsonData) {
	var arrayMap = [ {
		'taskId' : taskId,
		'applyNo' : applyNo,
		'processType' : processType,
		'taskOwnerGroupId' : taskOwnerGroupId,
		'comment' : comment,
		'taskStartTime' : new Date(taskStartTime),
		'taskOwnerId' : taskOwnerId,
		'jsonData' : jsonData
	} ];
	return workflowAction(arrayMap, 'AGENT_APPROVAL');
}

// Reject
function workflowReject(taskId, processType, applyNo, taskOwnerGroupId,
		comment, taskStartTime, jsonData) {
	var arrayMap = [ {
		'taskId' : taskId,
		'applyNo' : applyNo,
		'processType' : processType,
		'taskOwnerGroupId' : taskOwnerGroupId,
		'comment' : comment,
		'taskStartTime' : new Date(taskStartTime),
		'jsonData' : jsonData
	} ];
	return workflowAction(arrayMap, 'REJECT');
}

// Agent reject
function workflowAgentReject(taskId, processType, applyNo, taskOwnerId,
		taskOwnerGroupId, comment, taskStartTime, jsonData) {
	var arrayMap = [ {
		'taskId' : taskId,
		'applyNo' : applyNo,
		'processType' : processType,
		'comment' : comment,
		'taskStartTime' : new Date(taskStartTime),
		'taskOwnerId' : taskOwnerId,
		'taskOwnerGroupId' : taskOwnerGroupId,
		'jsonData' : jsonData
	} ];
	return workflowAction(arrayMap, 'AGENT_REJECT');
}

// Countersign
function workflowCountersign(taskId, processType, applyNo, countersigns,
		taskOwnerGroupId, comment, taskStartTime, jsonData) {
	var arrayMap = [ {
		'taskId' : taskId,
		'applyNo' : applyNo,
		'processType' : processType,
		'countersigns' : countersigns,
		'taskOwnerGroupId' : taskOwnerGroupId,
		'comment' : comment,
		'taskStartTime' : new Date(taskStartTime),
		'jsonData' : jsonData
	} ];
	return workflowAction(arrayMap, 'COUNTERSIGN');
}

// Agent countersign
function workflowAgentCountersign(taskId, processType, applyNo, countersigns,
		taskOwnerId, taskOwnerGroupId, comment, taskStartTime, jsonData) {
	var arrayMap = [ {
		'taskId' : taskId,
		'applyNo' : applyNo,
		'processType' : processType,
		'countersigns' : countersigns,
		'taskOwnerId' : taskOwnerId,
		'taskOwnerGroupId' : taskOwnerGroupId,
		'comment' : comment,
		'taskStartTime' : new Date(taskStartTime),
		'jsonData' : jsonData
	} ];
	return workflowAction(arrayMap, 'AGENT_COUNTERSIGN');
}

// ////////////////
// Notify
function workflowNotify(taskId, processType, applyNo, notifys,
		taskOwnerGroupId, comment, taskStartTime, jsonData) {
	var arrayMap = [ {
		'taskId' : taskId,
		'applyNo' : applyNo,
		'processType' : processType,
		'notifys' : notifys,
		'taskOwnerGroupId' : taskOwnerGroupId,
		'comment' : comment,
		'taskStartTime' : new Date(taskStartTime),
		'jsonData' : jsonData
	} ];
	return workflowAction(arrayMap, 'NOTIFY');
}

// Agent notify
function workflowAgentNotifys(taskId, processType, applyNo, notifys,
		taskOwnerGroupId, taskOwnerId, comment, taskStartTime, jsonData) {
	var arrayMap = [ {
		'taskId' : taskId,
		'applyNo' : applyNo,
		'processType' : processType,
		'notifys' : notifys,
		'taskOwnerId' : taskOwnerId,
		'taskOwnerGroupId' : taskOwnerGroupId,
		'comment' : comment,
		'taskStartTime' : new Date(taskStartTime),
		'jsonData' : jsonData
	} ];
	return workflowAction(arrayMap, 'AGENT_NOTIFY');
}
// //////////////////

// Abort process
function workflowAbortProcess(processType, applyNo, comment, jsonData) {
	var arrayMap = [ {
		'applyNo' : applyNo,
		'processType' : processType,
		'comment' : comment,
		'jsonData' : jsonData
	} ];
	return workflowAction(arrayMap, 'ABORT_PROCESS');
}

// Batch
function workflowBatchApprovalAction(arrayMap) {
	return workflowBatchAction(arrayMap, 'BATCH_APPROVAL');
}

function workflowBatchAgentApprovalAction(arrayMap) {
	return workflowBatchAction(arrayMap, 'AGENT_BATCH_APPROVAL');
}

function workflowBatchRejectAction(arrayMap) {
	return workflowBatchAction(arrayMap, 'BATCH_REJECT');
}

function workflowBatchAgentRejectAction(arrayMap) {
	return workflowBatchAction(arrayMap, 'AGENT_BATCH_REJECT');
}

function workflowBatchAbortProcess(arrayMap) {
	return workflowBatchAction(arrayMap, 'BATCH_ABORT_PROCESS');
}

function workflowAction(arrayMap, action) {
	var result = "";
	$.ajax({
		type : 'POST',
		url : CONTEXT_URL + '/workflow/execute/' + action,
		contentType : "application/json",
		data : JSON.stringify(arrayMap),
		success : function(data) {
			if (data.success) {
				alert('已完成');
				result = 'success';
			} else {
				alert(data.msg);
				result = 'fail';
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			result = 'fail';
			alert(errorThrown);
		},
		async : false
	});
	return result;
}

function workflowBatchAction(arrayMap, action) {
	var result = "";
	$.ajax({
		type : 'POST',
		url : CONTEXT_URL + '/workflow/execute/' + action,
		contentType : "application/json",
		data : JSON.stringify(arrayMap),
		beforeSend : function(jqXHR, settings) {
			$.blockUI({
				message : '處理中'
			});
		},
		success : function(data) {
			if (data.success) {
				alert('執行批簽完畢');
				result = 'success';
			} else {
				alert(data.msg);
				result = 'fail';
			}
			if ($('#todoTabs').size() > 0) {
				var activeIndex = $('#todoTabs').tabs("option", "active");
				$('#todoTabs').tabs('load', activeIndex);
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert(errorThrown);
		},
		complete : function(jqXHR, textStatus) {
			$.unblockUI();
		},
		async : true
	});
	return result;
}
