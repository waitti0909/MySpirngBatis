<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>NOMS</title>
<meta name="description" content="description">
<meta name="author" content="Charlie Woo">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="<s:url value="/resources/plugins/bootstrap/bootstrap.css" />" rel="stylesheet">
<link href="http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" rel="stylesheet">
<link href="<s:url value="/resources/css/style.css" />" rel="stylesheet">
<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
</head>

<body>
	<div class="container-fluid">
		<div class="row">
			<div id="" class="col-xs-12 text-center">
				<img src="<s:url value="/resources/img/symbol_construction.png" />" alt="" />
				<h1>We are Sorry !</h1>
				<h3>Oops, 500 Internal Server Error</h3>
					<button class="btn btn-default btn-label-left txt-danger" onclick="showErrorDiv()">
					<span><i class="fa fa-bug"></i></span> 錯誤資訊</button>&nbsp;&nbsp;
					<button class="btn btn-default btn-label-left txt-danger" id="mailBtn" onclick="reportError()">
					<span><i class="fa fa-envelope"></i></span> 回報錯誤</button><p>
				<div id="reportDiv" style="display:none">
					<textarea rows="3" cols="50" id="usercontent"></textarea><p>
					<button class="btn btn-default" id="sendBtn" onclick="reportError();">送出</button>
				</div>
				<div id="msgDiv" style="display:none; text-align: left;">
				<span><i class="fa fa-exclamation-triangle"></i></span> Error Message：<br> ${stack}</div>
			</div>
		</div>
	</div>
	<form action="" id="hiddenForm">
		<input type="hidden" name="requestUrl" id="requestUrl" value="${requestUrl}" />
		<input type="hidden" name="action" id="action" value="${action}" />
		<input type="hidden" name="stack" id="stack" value="${stack}" />
		<input type="hidden" name="errMsg" id="errMsg" value="${errMsg}" />
	</form>
</body>

<script type="text/javascript">
var showToken = false;
function showReportDiv() {
	$("#reportDiv").show();
}

function reportError() {
	$("#sendBtn").attr("disabled", true);
	$.ajax({
		url: '<s:url value="/exception/sendMail" />',
		data: {
			"requestUrl": $("#requestUrl").val(),
			"action": $("#action").val(),
			"stack" : $("#stack").val()
		},
		async : false,
		type : "POST",
		datatype : "text",
		success: function(data) {
			if ("success" == data) {
				alert("回報完成，請靜候相關人員處理！");
				parent.$.fancybox.close();
			}
		}
	});
	$("#sendBtn").attr("disabled", false);
}

function showErrorDiv() {
	if (!showToken) {
		$("#msgDiv").show("slow");
		showToken = true;
	} else {
		$("#msgDiv").hide("slow");
		showToken = false;
	}
	
}
</script>
</html>