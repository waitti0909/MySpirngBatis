<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
<style>
.div-a {
	float: left;
	width: 40%
}

.div-b {
	float: left;
	width: 60%
}
</style>
<title>簽核作業</title>
</head>
<body>
	<div class="box-header">
		<div class="box-name">
			<i class="fa fa-legal"></i> <span>簽核作業</span>
		</div>
		<div class="box-icons">
			<p class="expand-link"></p>
		</div>
		<div class="no-move"></div>
	</div>

	<div class="row">
		<div class="col-xs-12 col-sm-12">
			<div class="box-content">

				<div class="form-horizontal" id="approvalForm" >
					<div id="approvalBtn">
						<button class="btn btn-label-left btn-primary" type="button" id="approvalAgree">
							<s:message code="" text="核可" />
						</button>
						<button class="btn btn-label-left btn-danger" type="button" style="display:none;" id="approvalReject">
							<s:message code="" text="駁回" />
						</button>
						<button class="btn btn-label-left btn-primary" type="button" onclick="openCounterSign('COUNTERSIGN');" style="display:none;"
							id="addedApproval">
							<s:message code="" text="加簽" />
						</button>
						<button class="btn btn-label-left btn-primary" type="button" onclick="openCounterSign('NOTIFY');" style="display:none;"
							id="parallelApproval">
							<s:message code="" text="會簽" />
						</button>
					</div>

					<div class="form-group" style="border: 1px solid #c0c0c0; background-color: #F0F8FF; margin: 0 auto;">
						<div style="margin: 5px 5px 5px 5px;">
							<div class="div-a">
								<div class="form-group">
									<label class="col-sm-4 control-label">簽核人員:</label>
									<div class="col-sm-3 control-label" style="text-align: left;">${approvalPSN}</div>
								</div>
								<br>
								<div class="form-group">
									<label class="col-sm-4 control-label">簽核類別 : </label>
									<div class="col-sm-6 control-label" style="text-align: left;">${processName}(${applyNo})</div>
								</div>
							</div>

							<div class="div-b">
								<div class="form-group">
									<label class="col-sm-2 control-label">簽核意見 : </label>
									<div class="col-sm-10">
										<textarea class="form-control" id="approvalComment" name="approvalComment" rows="3"></textarea>
									</div>
								</div>
							</div>
						</div>
					</div>
					
					<%-- 簽核明細頁面，制訂於TB_WORKFLOW_CFG.DETAIL_PAGE_URL內 --%>
					<div class="form-group" style="border: 1px solid #c0c0c0; height: 540px; margin: 0 auto; overflow: auto;">
						<c:if test="${detailPageURL!=''}">
							<c:import url="${detailPageURL}"/>
						</c:if>
					</div>
					<div id="showCountSignPage"></div>
				</div>

			</div>
		</div>
	</div>
	<input type="hidden" value = "{}" id="workflowJsonData">
<script type="text/javascript">
$(document).ready(function() {
	if('${userTaskType}'=="NORMAL"){
		$('#approvalReject').show();
		$('#addedApproval').show();
		$('#parallelApproval').show();
	}else if('${userTaskType}'=="COUNTERSIGN"){  //加簽
		$('#approvalReject').show();
	}
		
	//駁回按鈕
	$("#approvalReject").click(function() {
		
		if($("#approvalComment").val()==""){
			alert("請填寫簽核意見");
		} else if ($("#approvalComment").val().length > 200) {
			// check sign log length add by Charlie 20150304
			alert("簽核意見超過限制200字！");
			return false;
		} else{
			if(!(validateDetailPageRule("REJECT")==true)){
				return false;
			}
			if('${isAgent}'=="Y"){
				var result = workflowAgentReject('${taskId}', '${processType}', '${applyNo}', '${taskOwnerId}', '${taskOwnerGroupId}', $('#approvalComment').val(), '${taskStartTime}', $('#workflowJsonData').val());
				doResultAction(result, 1);
			}else{
				var result = workflowReject('${taskId}', '${processType}', '${applyNo}', '${taskOwnerGroupId}', $('#approvalComment').val(), '${taskStartTime}', $('#workflowJsonData').val());
				doResultAction(result, 0);
			}	
		}		
	});

	//核可按鈕
	$("#approvalAgree").click(function() {
		if(!validateDetailPageRule()){
			return false;
		}
		if($("#approvalComment").val()==""){
			$("#approvalComment").val("同意(單筆)");
		} else if ($("#approvalComment").val().length > 200) {
			// check sign log length add by Charlie 20150304
			alert("簽核意見超過限制200字！");
			return false;
		}
		if('${isAgent}'=="Y"){
			var result = workflowAgentApproval('${taskId}', '${processType}', '${applyNo}', '${taskOwnerId}', '${taskOwnerGroupId}',$('#approvalComment').val(), '${taskStartTime}', $('#workflowJsonData').val());
			doResultAction(result, 1);
		}else{
			var result = workflowApproval('${taskId}', '${processType}', '${applyNo}', '${taskOwnerGroupId}', $('#approvalComment').val(), '${taskStartTime}', $('#workflowJsonData').val());
			doResultAction(result, 0);
		}	
	});
});

function doResultAction(result, reloadTabIndex){
	if(result=="success"){
		$.fancybox.close();
		$('#todoTabs').tabs('load',reloadTabIndex);
	}
}

//開啟 加會簽頁面
function openCounterSign(approveType){
	if(!(validateDetailPageRule()==true)){
		return false;
	}
	openCounterSignDialogFrame("showCountSignPage", '${isAgent}', '${taskId}', '${processType}', '${applyNo}', '${taskOwnerId}', '${taskOwnerGroupId}', $('#approvalComment').val(), '${taskStartTime}', approveType, $('#workflowJsonData').val());
}
function validateDetailPageRule(resultValue){
	var callBackFun = "validateWFVerify";
	if (typeof window[callBackFun] === "function") {
		return window[callBackFun](resultValue);
	}else{
		return true;
	}
}
</script>

</body>
</html>