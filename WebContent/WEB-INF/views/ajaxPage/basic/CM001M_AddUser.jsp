<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<!DOCTYPE html>

<div class="row">
	<div class="col-xs-12 col-sm-12">
		<div class=" ui-draggable ui-droppable">
			<div class="box-content">
				<form class="form-horizontal" role="form" name="companyAccountForm" id="companyAccountForm">
					<div class="col-sm-12" id="btnDiv">
						<button class="btn btn-primary btn-label-left" type="submit"
							id="btn_submit_edit">
							<span><i class="fa fa-save"></i></span>
							<s:message code="button.save" text="儲存" />
						</button>
					</div>
					<div class="clearfix">&nbsp;</div>

                    <input id='au_CovatNo' type="hidden" name='CO_VAT_NO' value='${companyAccount.CO_VAT_NO}' >
					<!-- =================================================================================== -->
					<div class='form-group'>
						<div class='row'>
							<div class='col-md-12'>
								<label class='col-sm-3 control-label'><span class='s'>*
								</span>帳號：</label>
								<div class='col-sm-6'>
									<input id='au_EngNm' type='text' name='ENG_NM'
										class='form-control' value='${companyAccount.ENG_NM}' required="required" maxlength="50">
									<input id='au_PsnId' type="hidden" name='PSN_ID' value='${companyAccount.PSN_ID}' >
								</div>
							</div>
						</div>
					</div>
					<!-- =================================================================================== -->
					<div class='form-group'>
						<div class='row'>
							<div class='col-md-12'>
								<label class='col-sm-3 control-label'><span class='s'>*
								</span>名稱：</label>
								<div class='col-sm-6'>
									<input id='au_ChnNm' type='text' name='CHN_NM'
										class='form-control' value='${companyAccount.CHN_NM}' required="required" maxlength="50">
								</div>
							</div>
						</div>
					</div>
					<!-- =================================================================================== -->
					<div class='form-group'>
						<div class='row'>
							<div class='col-md-12'>
								<label class='col-sm-3 control-label'><span class='s'>*
								</span>密碼：</label>
								<div class='col-sm-6'>
									<input id='au_PsnPwd' type="password" name='PSN_PWD'
										class='form-control' value='${companyAccount.PSN_PWD}' required="required" maxlength="50">
								</div>
							</div>
						</div>
					</div>
					<!-- =================================================================================== -->
					<div class='form-group'>
						<div class='row'>
							<div class='col-md-12'>
								<label class='col-sm-3 control-label'><span class='s'>*
								</span>密碼確認：</label>
								<div class='col-sm-6'>
									<input id='au_PsnPed2' type="password" name='psn_PWD2'
										class='form-control' value='${companyAccount.PSN_PWD}' required="required" maxlength="50">
								</div>
							</div>
						</div>
					</div>
					<!-- =================================================================================== -->
					<div class='form-group'>
						<div class='row'>
							<div class='col-md-12'>
								<label class='col-sm-3 control-label'><span class='s'>*
								</span>E-Mail：</label>
								<div class='col-sm-6'>
									<input id='au_Email' type="email" name='E_MAIL'
										class='form-control' value='${companyAccount.e_MAIL}' required="required" maxlength="60">
								</div>
							</div>
						</div>
					</div>
					<!-- =================================================================================== -->
					<div class='form-group'>
						<div class='row'>
							<div class='col-md-12'>
								<label class='col-sm-3 control-label'><span class='s'>*
								</span>部門：</label>
								<div class='col-sm-6'>
									<select id="au_DeptId" name="DEPT_ID" class="form-control"
										required="required">
										<option value="">--- 請選擇 ---</option>
										<c:forEach items="${orgDept}" var="data">
											<option value="${data.key}">${data.value}</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>
					</div>					
				</form>
			</div>
		</div>
	</div>
</div>



<script type="text/javascript">
$(document).ready(function() {
	
	if ($('#btnTypeId').val() == "edit") {
		document.getElementById('au_EngNm').disabled = true;
	}
	
	$('#au_DeptId').val('${companyAccount.DEPT_ID}');
	
	//判斷是否修改表單
	$("#companyAccountForm :input").change(function() {
		$("#companyAccountForm").data("changed", true);
	});
	
	$("#btn_submit_edit").click(function() {
		if ($('#companyAccountForm')[0].checkValidity()) {
		  // If the form is invalid, submit it. The form won't actually submit;
		  // this will just cause the browser to display the native HTML5 error messages.
			event.preventDefault();
			submitData();
		}
	});
});


	function submitData() {
		if ($('#au_PsnPwd').val() != $('#au_PsnPed2').val()) {
			alert("密碼不一致，請重新輸入");
		} else {

			if ($('#btnTypeId').val() == "add") {
				if (confirm("是否確定新增")) {
					toSubmit();
				}
			} else { //修改
				
				if (!($("#companyAccountForm").data("changed"))) {
					alert("無修改任何值!");
				} else {
					if (confirm("是否確定修改")) {
					toSubmit();
					}
				}
			}
		}
	}

	function toSubmit() {
		$.ajax({
			url : CONTEXT_URL + "/cM001M_AddUser/Save/",
			type : 'POST',
			data : $("#companyAccountForm").serialize() + "&btnType="
					+ $('#btnTypeId').val(),
			//contentType : 'application/json',
			dataType : "json",
			async : false,
			success : function(data) {
				alert(data.msg);
				if (data.success) {

					var formJson = JSON
							.stringify(form2js($("#companyAccountForm").attr(
									"id"), '.', true, null));
					var formJsonAddName = '';
					for (var i = 0; i < formJson.length; i++) {
						formJsonAddName = formJsonAddName + formJson[i];
						if (i == (formJson.length - 2)) {
							formJsonAddName = formJsonAddName + ',"au_DeptId":"'
									+ $("#au_DeptId option:selected").text()
									+ '"';
						}
					}
					var callbackFun = '${companyAccount.callBackFun}';

					if (typeof window[callbackFun] === "function") {
						window[callbackFun](formJsonAddName);
					}
					var frameId = $("#frameId").val();
					$("#" + frameId).dialog('close');
				}
			}
		});
	}
</script>