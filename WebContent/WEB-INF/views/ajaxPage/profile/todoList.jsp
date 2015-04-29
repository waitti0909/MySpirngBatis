<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<style type="text/css">
	th.todoListDisplayNone, td.todoListDisplayNone {display: none;}
</style>
<script type="text/javascript">
$(function() {
	$('#todoTabs').tabs({
    	activate : function(event, ui){
    		$('#todoTabs').find('input[type="checkbox"]').each(function(){
    			$(this).prop('checked', false);
    		});
    		var activeIndex = $('#todoTabs').tabs("option","active");
    		if(activeIndex == 1){
    			$('#approvalBatch').hide();
    			$('#rejectBatch').hide();
    			$('#agentApprovalBatch').show();
    			$('#agentRejectBatch').show();
    		} else {
    			$('#approvalBatch').show();
    			$('#rejectBatch').show();
    			$('#agentApprovalBatch').hide();
    			$('#agentRejectBatch').hide();
    		}
    	}, 
    	beforeLoad : function( event, ui ) {
	    	ui.ajaxSettings.async = false;
	    	ui.ajaxSettings.type = "POST";
	        ui.jqXHR.error(function() {
	       		ui.panel.html("Couldn't load this tab.");
	       	});
		}
    });
    
    //batch button
    $('#approvalBatch').click(function() {
    	if(!checkMultiSelect()){
    		alert('請先選擇一筆');
    		return;
    	}
    	if(!$.trim($('#batchComment').val()).length){
			$('#batchComment').val('同意');
		}
    	var arrayMap = getSelectWorkflowTodoArray("own");
		workflowBatchApprovalAction(arrayMap);
	});
    
    $('#rejectBatch').click(function() {
    	if(!checkMultiSelect()){
    		alert('請先選擇一筆');
    		return;
    	}
    	if(checkHasNotify()){
    		alert('會簽不允許駁回，請先勾選掉會簽工作');
    		return;
    	}
    	if(!$.trim($('#batchComment').val()).length){
			$('#batchComment').val('不同意');
		}
    	var arrayMap = getSelectWorkflowTodoArray("own");
    	workflowBatchRejectAction(arrayMap);
	});
    
    //agent batch button
    $('#agentApprovalBatch').click(function() {
    	if(!checkMultiSelect()){
    		alert('請先選擇一筆');
    		return;
    	}
    	if(!$.trim($('#batchComment').val()).length){
			$('#batchComment').val('同意');
		}
    	var arrayMap = getSelectWorkflowTodoArray("agent");		
		workflowBatchAgentApprovalAction(arrayMap);
	});
    
    $('#agentRejectBatch').click(function() {
    	if(!checkMultiSelect()){
    		alert('請先選擇一筆');
    		return;
    	}
    	if(checkHasNotify()){
    		alert('會簽不允許駁回，請先勾選掉會簽工作');
    		return;
    	}
    	if(!$.trim($('#batchComment').val()).length){
			$('#batchComment').val('不同意');
		}
    	var arrayMap = getSelectWorkflowTodoArray("agent");
    	workflowBatchAgentRejectAction(arrayMap);
	});
});

function getSelectWorkflowTodoArray(approvalType){
	var arrayMap = [];
	$('#todoTabs').find('input[type="checkbox"]').each(function(){
		if($(this).prop('checked') && $(this).prop('id').match('selectAll') == null){
//	 		var allowBatch = $(this).prop('value');
			var taskId = $(this).closest("tr").find('td:eq(1)').text();
			var applyNo = $(this).closest("tr").find('td:eq(2)').text();
			var processType = $(this).closest("tr").find('td:eq(3)').text();
// 			var processName = $(this).closest("tr").find('td:eq(4)').text();
			var taskName = $(this).closest("tr").find('td:eq(5)').text();
	 		var taskOwnerId = $(this).closest("tr").find('td:eq(6)').text();
	 		var taskOwnerGroupId = $(this).closest("tr").find('td:eq(7)').text();
//	 		var applyDescription = $(this).closest("tr").find('td:eq(8)').text();
//	 		var applicantId = $(this).closest("tr").find('td:eq(9)').text();
//	 		var applicantName = $(this).closest("tr").find('td:eq(10)').text();
//	 		var applyDate = $(this).closest("tr").find('td:eq(11)').text();
			var taskStartTime = $(this).closest("tr").find('td:eq(12)').text();
// 			var userTaskType = $(this).closest("tr").find('td:eq(13)').text();
			var jsonData = $(this).closest("tr").find('td:eq(14)').text();
			if(approvalType=="agent"){
				arrayMap.push({'taskId':taskId,
					'applyNo':applyNo,
					'processType':processType,
					'taskName':taskName,
					'taskOwnerId':taskOwnerId,
					'taskOwnerGroupId':taskOwnerGroupId,
					'comment':$('#batchComment').val(),
					'taskStartTime':new Date(taskStartTime),
					'jsonData' : jsonData
				});
			}else{
				arrayMap.push({'taskId':taskId,
					'applyNo':applyNo,
					'processType':processType,
					'taskName':taskName,
					'taskOwnerGroupId':taskOwnerGroupId,
					'comment':$('#batchComment').val(),
					'taskStartTime':new Date(taskStartTime),
					'jsonData' : jsonData
				});
			}
		}			
	});
	return arrayMap;
}

function checkMultiSelect(){
	var flag = false;
	$('#todoTabs').find('input[type="checkbox"]').each(function(){
		if($(this).prop('checked') && $(this).prop('id').match('selectAll') == null){
			flag = true;
			return;
		}
	});
	return flag;
}

function checkHasNotify(){
	var flag = false;
	$('#todoTabs').find('input[type="checkbox"]').each(function(){
		if($(this).prop('checked') && $(this).prop('id').match('selectAll') == null){
			var taskName = $(this).closest("tr").find('td:eq(12)').text();
			if(taskName.match(/NOTIFY/g) != null){
				flag = true;
				return;
			}
		}
	});
	return flag;
}
</script>
<head>
<title>Todo list</title>
<body>
	<div class="row">
		<div id="breadcrumb" class="col-md-12">
			<ol class="breadcrumb">
				<li><a href="<s:url value="/index/" />">首頁</a></li>
				<li><a href="javascript:void(0);">待簽核事項</a></li>
			</ol>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-12 col-sm-12">
			<div class="box ui-draggable ui-droppable">
				<div class="box-header">
					<div class="box-name">
						<i class="fa fa-search"></i> <span>待簽核</span>
					</div>
					<div class="box-icons">
						<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
						</a> <a class="expand-link"> <i class="fa fa-expand"></i>
						</a>
					</div>
					<div class="no-move"></div>
				</div>
				<div class="box-content  no-padding">
<!-- 					<button type="button" id="countersignApproval" class="btn btn-label-left btn-primary"> -->
<!-- 						加簽簽核(僅供測試) -->
<!-- 					</button> -->
<!-- 					<button type="button" id="notifyApproval" class="btn btn-label-left btn-primary"> -->
<!-- 						會簽簽核(僅供測試) -->
<!-- 					</button> -->
					<button type="button" id="approvalBatch" class="btn btn-label-left btn-primary">
						<s:message code="button.workflow.approval.batch" />
					</button>
					<button type="button" id="rejectBatch" class="btn btn-label-left btn-danger">
						<s:message code="button.workflow.reject.batch" />
					</button>
					<button type="button" id="agentApprovalBatch" class="btn btn-label-left btn-primary" style="display:none;">
						<s:message code="button.workflow.approval.batch" />
					</button>
					<button type="button" id="agentRejectBatch" class="btn btn-label-left btn-danger" style="display:none;">
						<s:message code="button.workflow.reject.batch" />
					</button>
					<input type="text" id="batchComment" class="form-control" style="height:32px;width:70%;display:inline;" placeholder="批次簽核意見">
					<div id="todoTabs" class="ui-tabs ui-widget ui-widget-content ui-corner-all">
						<ul class="ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all" role="tablist">
							<li><a href="<s:url value="/profile/todoList/own"/>"><s:message code="label.profile.todoList.own" /></a></li>
							<li><a href="<s:url value="/profile/todoList/agent"/>"><s:message code="label.profile.todoList.agent" /></a></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>