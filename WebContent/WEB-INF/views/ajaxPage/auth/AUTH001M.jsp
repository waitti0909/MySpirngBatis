<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
<title>使用者帳號維護</title>


<script type="text/javascript">
$(document).ready(function() {
	if("${personnel.DISMISSION}" != null && "${personnel.DISMISSION}" != ""){
		$("#dismission_M").val("${personnel.DISMISSION}");
	}
	$('#expireDATE_M').datepicker({
		dateFormat : "yy/mm/dd",
		changeYear : true,
		changeMonth : true,
		showButtonPanel : true
	});
	
	$('#on_JOB').datepicker({
		dateFormat : "yy/mm/dd",
		changeYear : true,
		changeMonth : true,
		showButtonPanel : true
	});
	
	$('#deptID_M').val('${personnel.DEPT_ID}').change(); //部門
			
	showSet('${btnType}');
	
	//判斷是否修改表單
	$("#personnelMForm :input").change(function() {
		$("#personnelMForm").data("changed", true);
	});
		
	$("#btn_submit_edit").click(function() {
			//驗證終止日是否填值
// 			if ($('#expireDATE_M').val() == '') {
// 				$("#expireDATE_M").removeAttr('readonly');
// 				$("#expireDATE_M").keydown(function(e) {
// 					e.preventDefault();
// 				});
// 			}

			if ($('#personnelMForm')[0].checkValidity()) {
				// If the form is invalid, submit it. The form won't actually submit;
				// this will just cause the browser to display the native HTML5 error messages.
				event.preventDefault();
				var onJob = $("#on_JOB").val();
				var expireDate = $("#expireDATE_M").val();
				if(onJob == ""){
					alert("請選擇生效日");
					return false;
				}else{
					onJob = new Date(onJob).getTime();
					if(expireDate != ""){
						expireDate = new Date(expireDate).getTime();
						if(expireDate < onJob){
							alert("終止日請大於生效日");
							return false;
						}
					}
					
				}
				submitData();
			}
		});

	});
	
	function showSet(btn){
		if (btn == "edit") {
			document.getElementById('psnNO_M').disabled = true;
			document.getElementById('engNM_M').disabled = true;
		}else if(btn == "view"){
			$("#personnelMForm :input").prop("disabled", true);
		}
	}

	function submitData() {
		if ('${btnType}' == "add") {
			if (confirm("是否確定新增")) {
				toSubmit();
			}
		} else { //修改
			if (confirm("是否確定修改")) {
				//提交前判断
				if (!($("#personnelMForm").data("changed"))) {
					alert("無修改任何值!");
				} else {
					toSubmit();
				}
			}
		}
	}

	function toSubmit() {	
		var myform = $('#personnelMForm');
		var disabled = myform.find(':input:disabled').removeAttr('disabled');
		var serialized = myform.serialize();
		disabled.attr('disabled','disabled');
		
		$.ajax({
			url : CONTEXT_URL + "/auth/personnel/Save",
			type : 'POST',
			data : serialized + "&btnType=" + '${btnType}',
			async : false,
			dataType : 'json',
			success : function(data) {
				if (data.success) {
					alert(data.msg);
					$.fancybox.close();
					$('#btn_search').trigger('click');
				} else {
					alert(data.msg);
				}
			}
		});
	}
	
	function setDefaultSelectOption(selectObjId) {
		$('#'+selectObjId).empty().append("<option selected='selected' value=''>--- 請選擇 ---</option>");
	}
	
	function getPsnPos(dept) {
		setDefaultSelectOption('jobTIL_M');
		$.ajax({	
			url: CONTEXT_URL + "/auth/getDeptPos",
			dataType: "json",
			data : {
				"deptID" : dept
			},
			type: "POST",
			async: false,
			success : function(data) {
		 		var rows = data.rows;	
				for (var r in rows) {
					$('#jobTIL_M').append("<option value='"+rows[r].posId+"'>"+rows[r].posName+"</option>");
				}	
				$('#jobTIL_M').val('${personnel.JOB_TTL}'); //職稱
			}
		});
	}
</script>

</head>
<body>
	<div class="box-header">
		<div class="box-name">
			<i class="fa fa-edit"></i> <span>使用者帳號維護</span>
		</div>
		<div class="box-icons">
			<p class="expand-link"></p>
		</div>
		<div class="no-move"></div>
	</div>

<div class="row">
	<div class="col-xs-12 col-sm-12">
		<div class=" ui-draggable ui-droppable">
			<div class="box-content">
				<form class="form-horizontal" name="personnelMForm" id="personnelMForm" style="margin-bottom: 5%">
					<div class="col-sm-12" id="btnDiv">
						<button class="btn btn-primary btn-label-left" type="submit"
							id="btn_submit_edit">
							<span><i class="fa fa-save"></i></span>
							<s:message code="button.save" text="儲存" />
						</button>
					</div>
					<div class="clearfix">&nbsp;</div>

					<!-- =================================================================================== -->
					<div class='form-group'>
						<div class='row'>
							<div class='col-md-12'>
								<label class='col-sm-3 control-label'><span class='s'>*
								</span>員工編號：</label>
								<div class='col-sm-6'>
									<input id='psnNO_M' type='text' name='PSN_NO'
										class='form-control' value='${personnel.PSN_NO}' required="required">
									<input id='psnID_M' type="hidden" name='PSN_ID' value='${personnel.PSN_ID}' >
								</div>
							</div>
						</div>
					</div>
					<!-- =================================================================================== -->
					<div class='form-group'>
						<div class='row'>
							<div class='col-md-12'>
								<label class='col-sm-3 control-label'><span class='s'>*
								</span>AD帳號：</label>
								<div class='col-sm-6'>
									<input id='engNM_M' type='text' name='ENG_NM'
										class='form-control' value='${personnel.ENG_NM}' required="required">
								</div>
							</div>
						</div>
					</div>
					<!-- =================================================================================== -->
					<div class='form-group'>
						<div class='row'>
							<div class='col-md-12'>
								<label class='col-sm-3 control-label'><span class='s'>*
								</span>中文姓名：</label>
								<div class='col-sm-6'>
									<input id='chn_NM' type='text' name='CHN_NM'
										class='form-control' value='${personnel.CHN_NM}' required="required">
								</div>
							</div>
						</div>
					</div>
					<!-- =================================================================================== -->
					<div class='form-group'>
						<div class='row'>
							<div class='col-md-12'>
								<label class='col-sm-3 control-label'><span class='s'>*
								</span>離職否：</label>
								<div class='col-sm-6'>
									<select id="dismission_M" name="DISMISSION" class="form-control"
										required="required">
										<option value="N">否</option>
										<option value="Y">是</option>
									</select>
								</div>
							</div>
						</div>
					</div>
					<!-- =================================================================================== -->
					<div class='form-group'>
						<div class='row'>
							<div class='col-md-12'>
								<label class='col-sm-3 control-label'><span class='s'>*
								</span>生效日：</label>
								<div class='col-sm-6'>
									<fmt:formatDate value="${personnel.ON_JOB}" pattern="yyyy/MM/dd" var="onJobDate"/>
									<input id="on_JOB" type="text" name="ON_JOB"
									    class='form-control' value='${onJobDate}' placeholder="帳號生效日" readonly>
								</div>
							</div>
						</div>
					</div>
					<!-- =================================================================================== -->
					<div class='form-group'>
						<div class='row'>
							<div class='col-md-12'>
								<label class='col-sm-3 control-label'>終止日：</label>
								<div class='col-sm-6'>
									<input id="expireDATE_M" type="text" name="EXPIRE_DATE"
									    class='form-control' value='${psnExpireDATE}' placeholder="帳號終止日" readonly>
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
									<input id='eMAIL_M' type="email" name='E_MAIL'
										class='form-control' value='${personnel.e_MAIL}' required="required">
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
									<select id="deptID_M" name="DEPT_ID" class="form-control"
										required="required" onchange="getPsnPos(this.value);">
										<option value="">--- 請選擇 ---</option>
										<c:forEach items="${orgDept}" var="data">
											<option value="${data.key}">${data.value}</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>
					</div>	
					<!-- =================================================================================== -->
					<div class='form-group'>
						<div class='row'>
							<div class='col-md-12'>
								<label class='col-sm-3 control-label'><span class='s'>*
								</span>職稱：</label>
								<div class='col-sm-6'>
									<select id="jobTIL_M" name="JOB_TTL" class="form-control"
										required="required">
										<option value="">--- 請選擇 ---</option>
									</select>
								</div>
							</div>
						</div>
					</div>
					<!-- =================================================================================== -->
					<div class='form-group'>
						<div class='row'>
							<div class='col-md-12'>
								<label class='col-sm-3 control-label'>電話：</label>
								<div class='col-sm-6'>
									<input id='cellular' type="text" name='CELLULAR'
										class='form-control' value='${personnel.CELLULAR}' maxlength="50">
								</div>
							</div>
						</div>
					</div>					
				</form>
			</div>
		</div>
	</div>
</div>
</body>
</html>

