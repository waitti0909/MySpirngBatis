<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<title>工項維護作業</title>
		<script src="<s:url value="/resources/plugins/bootstrapvalidator/bootstrapValidator.min.js" />"></script>
	</head>
	<body>
		<div class="box-header">
			<div class="box-name">
				<i class="fa fa-edit"></i>
				<span>工項維護</span>
			</div>
			<div class="box-icons">
				<p class="expand-link"></p>
			</div>
			<div class="no-move"></div>
		</div>
		<form class="form-horizontal bootstrap-validator-form" id="subItemFrom" novalidate="novalidate" >
			<input type="hidden" id="cat_TYPE" name="cat_TYPE" value="${cat.CAT_TYPE}" />
			<input type="hidden" id="parent_CAT" name="parent_CAT" value="${mainItem.CAT_ID}" />
			<input type="hidden" id="cat_NO" name="cat_NO"/>
			<div style="padding-top: 15px;">
				&nbsp;&nbsp;
				<button id="subItemSaveBtn" type="button" class="btn btn-primary btn-label" style="margin-right: 10px" type="button">存檔</button>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">主項 :</label>
				<div class="col-sm-6">
					<p style="padding-top: 5px; padding-left: 2px;" id="">${mainItem.CAT_NAME}</p>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">中項編號 :</label>
				<div class="col-sm-6">
					<p style="padding-top: 5px; padding-left: 2px;" id="">
						<c:out value="${mainItem.CAT_NO}"/>. 
						<input id="suffixNo" type="text" size="10" placeholder="整數，必填">
					</p>
				</div>	
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">中項名稱 :</label>
				<div class="col-sm-6">
				<input id="cat_NAME" class="form-control require" type="text"
					name="cat_NAME" placeholder="必填" maxlength="100">
			</div>
			</div>						
		</form>
	</body>
</html>
<script type="text/javascript">
$(function(){
});

$("#subItemSaveBtn").click(save);

// 存檔動作
function save() {
	suffixNo = $("#suffixNo").val();
	cat_NAME = $("#cat_NAME").val();
	//檢核中項編號為非0整數
	var reg = /^[1-9][0-9]*$/g;
	if(!reg.test(suffixNo)) {
		alert('中項編號不可為空且為整數');
		$("#suffixNo").focus();
		return false;
	}
	if (cat_NAME == "" || cat_NAME == ''){
		alert('中項名稱不可為空');
		$("#cat_NAME").focus();
		return false;
	}
	
	$("#cat_NO").val('${mainItem.CAT_NO}' + "." + suffixNo);
	$.ajax({
		url : CONTEXT_URL + "/basic/cm003/saveNewItemCategory/",
		type : "POST",
		data : JSON.stringify(form2js($("#subItemFrom").attr("id"), ".",
				true, null)),
		contentType : "application/json",
		dataType : "json",
		async : false,
		success : function(data) {
			alert(data.msg.replace("error", ""));
			if (data.msg.indexOf("error") >= 0) {
				return false;
			}
			
			callbackFun = 'reloadSubItemSelect';
			if (typeof window[callbackFun] === "function") {
				window[callbackFun](data.row);
			}
			
			var frameId = $("#frameId").val();
			$("#" + frameId).dialog('close');
		}
	});
}
</script>