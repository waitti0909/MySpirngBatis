<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>新約/續約/換約/一解一簽</title>
<style type="text/css">
hr {
	display: block;
	margin-top: 0.5em;
	margin-bottom: 0.5em;
	margin-left: auto;
	margin-right: auto;
	border-style: inset;
	border-width: 1px;
}

.ftSolidLine {
	display: block;
	border: 1px solid #000000;
	margin-left: 2px;
	margin-right: 2px;
	padding-top: 0.35em;
	padding-bottom: 0.625em;
	padding-left: 0.75em;
	padding-right: 0.75em;
}

.legend {
	width: inherit;
	font-size: 14px;
	padding: 3px 5px;
	border-bottom: 0px dashed #B6B6B6;
	margin-bottom: 2px;
}

.selected {
  background-color: #b0bed9;
}
</style>
<script	src="<s:url value="/resources/js/lsCommon.js" />"></script>

	<c:if test="${newWindow=='true'}">
		<jsp:include page="/WEB-INF/layout/jsLayout.jsp" />
	</c:if>

<script type="text/javascript">
	var btnType;
	$(document).ready(function() {
		
		btnType = $("#btnType_M").val();
		$("#tabs").tabs();
		if (btnType == "view") {
			$('#boxTitle').append('<span>合約內容</span>');
		} else if (btnType == "new") {
			$('#boxTitle').append('<span>新約</span>');
		} else if (btnType == "edit") {
			$('#boxTitle').append('<span>編輯合約</span>');
		} else if (btnType == "change") {
			$('#boxTitle').append('<span>換約</span>');
		}
		
		// 根據按鈕型態 呈現 編輯 /檢視
		showSet($("#btnType_M").val());



		if('edit'!=btnType){
			$("#btn_apply").prop("disabled",true);
			$("#btn_upload").prop("disabled",true);
		}else{
			// 申請Button
			$("#btn_apply").click(function(){
				$.ajax({			
					url : CONTEXT_URL + "/ls/LS001M3/ls001M3ApplyValidator",		
					data:{
						"appSeq" : "${appSeq}",
						"lsNo"   : "${lsNo}",
						"type"   : "M"
					},
					type : "POST",
					dataType : 'json',
					success : function(data) {
						alert(data.msg);
					}
				});
			});

			$("#btn_upload").click(function() {
				//(沒編號(存草稿)不給傳附件)
			
				if ($.trim('${appSeq}') == "") {
					alert('無合約編號無法上傳附件');
				}else{
					
					//附件上傳事件
					openFileDialogFrame("showFileUploadPage","LEASE",'${appSeq}',"LEASE");
				}
			});
			
		}
		
	
	
	});
	
	// 檢視畫面設定
	function showSet(btnType) {
		if (btnType == "view") {
			$("#addEditForm :input").prop("disabled", true);
		} else if (btnType == "edit") {
			//$("#addEditForm :input").prop("disabled", true);
			//新約站台相關及簽合紀錄TAB隱藏
			//$("#li-ls001Tabs-2").hide();
			//$("#li-ls001Tabs-3").hide();
		} else if (btnType == "add") {
			//something todo
		} else if (btnType == "change" || btnType == "ReSign" ||btnType == "continue") {
			$("#li-ls001Tabs-2").hide();
			$("#li-ls001Tabs-3").hide();
		}
	}
	
	//點選選取列
	function addClassSEL(tbid) {
		$('#' + tbid).on('click', 'tr', function() {
			$('#' + tbid).find('tr').each(function() {
				$(this).removeClass('selected');
			});
			$(this).addClass('selected');
		});
	}
	
	//刪除選取列
	function delRow(tableID) {
		var table = document.getElementById(tableID);
		var rows = document.getElementById(tableID).getElementsByTagName(
				'tbody')[0].getElementsByTagName('tr');
		for (var i = 0; i < rows.length; i++) {
			var trSel = table.rows[i + 1].getAttribute("class");
			if (trSel != null) {
				var isSel = trSel.indexOf("selected");
				if (isSel != -1) {
					table.rows[i + 1].remove();
				}
			}
		}
	}

	
	
	
	function loadSiteTabAjaxContent(){
		var postData={
				   lsNo : "${lsNo}",
				   lsVer:"${lsVer}",
				   btnType:"${btnType}",
				   appSeq:"${appSeq}"};
		if (btnType == "view") {
			LoadAjaxPage('<s:url value="/ls/LS001M_Site/initLeaseLocation" />', postData, 'ls001Tabs-2');
		} else if(confirm("離開前請先儲存租約設定,確定要離開嗎?")){
			LoadAjaxPage('<s:url value="/ls/LS001M_Site/initLeaseLocation" />', postData, 'ls001Tabs-2');
		}
	}
	function loadSignTabAjaxContent(){

		var postData={applyNo : "${appSeq}",
				   lsNo : "${lsNo}",
				   lsVer:"${lsVer}",
				   processType : "${processType}"};
		LoadAjaxPage('<s:url value="/ls/getLeaseSignHistory" />', postData, 'ls001Tabs-3');
	}

	function getLessorPaymentModeText(pCode){
		<c:forEach items="${paymentMode}" var="data">
		if('${data.CODE}'==pCode){	 
			return "${data.NAME}"; 
		}
		</c:forEach>
		return "";
	}

	function setLessorPaymentMode(htmlId,htmlName,selectedValue,disabled){
		var disFlag = "disabled";
		if(typeof(disabled)=='undefined' || !disabled || ''==disabled){
			disFlag="";
		}
		var resultString = "<select "+disFlag+" id='"+htmlId+"' name='"+htmlName+"' >";
		<c:forEach items="${paymentMode}" var="data">
			resultString+='<option value="${data.CODE}" ';
			if('${data.CODE}'==selectedValue){	 
				resultString+='selected ';
			}
			resultString+='>${data.NAME}</option>';
		</c:forEach>
		return resultString+"</select>";
	}


	function openSourceLease(lsNo){

		var postData = "?newWindow=true&btnType=view&lsNo="+lsNo;
		
		var url = CONTEXT_URL + "/ls/LS001M"+encodeURI(postData);

		window.open(url, '來源合約', config = 'height=768,width=1024').focus();


	}


	
</script>
</head>

<body>
	<div class="row">
		<div class="col-xs-12 col-sm-12">
			<div class=" ui-draggable ui-droppable">
				<div class="box-header">
					<div id="boxTitle" class="box-name"></div>
					<div class="no-move"></div>
				</div>
				<div class="box-content">
					<form class="form-horizontal" id="addEditForm" action="">
						<input type="hidden" id="btnType_M" value="${btnType}" />
						<button class="btn btn-primary btn-label" type="button"
							id="btn_apply" >申請</button>
						<button class="btn btn-primary btn-label" type="button"
							id="btn_upload"  >附件上傳</button>
							
						<hr>

						<!-- TABS START-->
						<div id="tabs">
							<ul>
								<li><a href="#ls001Tabs-1">合約主檔</a></li>
								<c:if test="${lsNo!=''}">
									<li id="li-ls001Tabs-2"><a href="#ls001Tabs-2" onclick="loadSiteTabAjaxContent();">站台相關</a></li>
								
									<c:if test="${appSeq!=''}">
										<li id="li-ls001Tabs-3"><a href="#ls001Tabs-3" onclick="loadSignTabAjaxContent();">簽核紀錄</a></li>
									</c:if>
								</c:if>
							</ul>

							<div id="ls001Tabs-1">
								<jsp:include
									page="/WEB-INF/views/ajaxPage/ls/LS001M_Main.jsp"></jsp:include>
							</div>
							<div id="ls001Tabs-2">
								
							</div>
							<div id="ls001Tabs-3">
								
							</div>
						</div>
						<!-- TABS START-->
					</form>
				</div>
			</div>
		</div>
	</div>
	<div id="showFileUploadPage"></div>
	<div id="sourceLeaseDivId"></div>
	
</body>
</html>