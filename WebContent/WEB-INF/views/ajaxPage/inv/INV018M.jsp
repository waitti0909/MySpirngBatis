<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<!DOCTYPE html>
<html lang="en">
	<head>
		<title>庫存狀態異動作業</title>
		<script src="<s:url value="/resources/plugins/bootstrapvalidator/bootstrapValidator.min.js" />"></script>
	</head>
	<body>
		<div class="box-header">
			<div class="box-name">
				<i class="fa fa-edit"></i>
				<span>庫存狀態異動作業 - 申請</span>
			</div>
			<div class="box-icons">
				<p class="expand-link"></p>
			</div>
			<div class="no-move"></div>
		</div>
		<div class="row">
			<div class="col-xs-12 col-sm-12">
				<div class="ui-draggable ui-droppable">
					<div class="box-content">
						<form id="addEdit" class="form-horizontal" method="post">
							<input type="hidden" id="btnType_M" name="btn_type" value="${btn_type}" />
							<input type="hidden" id="srlNo_M" name="srl_no" />
							<div class="row">
								<div class="col-sm-5" id="btnDiv">
									<button type="submit" id="saveBtn" class="btn btn-primary btn-label-left">
										<span><i class="fa fa-save"></i></span>
										<s:message code="button.save" text="儲存" />
									</button>
									<button type="submit" id="applyBtn" class="btn btn-primary btn-label-left">
										<span><i class="fa fa-save"></i></span>
										<s:message code="button.workflow.apply" text="申請" />
									</button>
									<button type="button" id="cancelBtn" class="btn btn-default btn-label-left">
										<span><i class="fa fa-undo txt-danger"></i></span>
										<s:message code="button.reset" text="重置" />
									</button>
								</div>
							</div>
							<div class="form-group">
								<div class="row">
									<label class="col-sm-2 control-label">
									異動單號：</label>
									<div class="col-sm-2">
										<input type="text" id="whId_M" name="inv_tran_no" value="${element.inv_tran_no}"
											class="form-control" readonly="readonly" />
									</div>
									<label class="col-sm-2 control-label"><span class="s">*
									</span>狀態：</label>
									<div class="col-sm-2">
										<select id="invTranAuditStatus_M" name="inv_tran_audit_status" class="populate placeholder require">
											<c:forEach items="${sessionScope.inv018_statusList}" var="var">
												<option value="${var.CODE}" <c:if test="${element.inv_tran_audit_status == var.CODE}">selected="selected"</c:if>>${var.NAME}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="row">
									<label class="col-sm-2 control-label"><span class="s">*
									</span>倉庫：</label>
									<div class="col-sm-2">
										<select id="whIdSelect_M" name="wh_id" class="populate placeholder require">
											<option value="">-- 請選擇 --</option>
											<c:forEach items="${sessionScope.inv018_warehouseList}" var="var">
												<option value="${var.wh_id}" <c:if test="${element.wh_id == var.wh_id}">selected="selected"</c:if>>${var.wh_name}</option>
											</c:forEach>
										</select>
									</div>
									<label class="col-sm-2 control-label"><span class="s">*
									</span>料號：</label>
									<div class="col-sm-2">
										<input type="text" id="matNoMaterial" name="mat_no" value="${element.mat_no}"
											class="form-control require" readonly="readonly" />
									</div>
									<div class="col-sm-1" style="width: 1%;">
										<i id="matNoSearch" class="fa fa-search" style="cursor: pointer;"></i>
									</div>
								</div>
								<div class="row">
									<label class="col-sm-2 control-label"><span class="s">*
									</span>異動前狀態：</label>
									<div class="col-sm-2">
										<select id="oldMatStatus_M" name="old_mat_status" class="populate placeholder require">
											<option value="">-- 請選擇 --</option>
											<c:forEach items="${sessionScope.inv018_matStatusList}" var="var">
												<option value="${var.CODE}" <c:if test="${element.old_mat_status == var.CODE}">selected="selected"</c:if>>${var.NAME}</option>
											</c:forEach>
										</select>
									</div>
									<label class="col-sm-2 control-label"><span class="s">*
									</span>異動後狀態：</label>
									<div class="col-sm-2">
										<select id="newMatStatus_M" name="new_mat_status" class="populate placeholder require">
											<option value="">-- 請選擇 --</option>
											<c:forEach items="${sessionScope.inv018_matStatusList}" var="var">
												<option value="${var.CODE}" <c:if test="${element.new_mat_status == var.CODE}">selected="selected"</c:if>>${var.NAME}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="row">
									<label class="col-sm-2 control-label">
									貼標號碼：</label>
									<div class="col-sm-2">
										<select id="tagNo_M" name="tag_no" class="populate placeholder">
											<option value="">-- 請選擇 --</option>
										</select>
									</div>
									<label class="col-sm-2 control-label">
									廠商序號：</label>
									<div class="col-sm-2">
										<input type="text" id="venSn_M" name="ven_sn" value="${element.ven_sn}"
											class="form-control" readonly="readonly" />
									</div>
								</div>
								<div class="row">
									<label class="col-sm-2 control-label"><span class="s">*
									</span>異動數量：</label>
									<div class="col-sm-2">
									<input type="number" id="tranQty_M" name="tran_qty"
										value="${element.tran_qty}" size="2"
										class="form-control require" min="1"
										data-bv-integer-message="需輸入數字!"
										data-bv-greaterthan-message="數值需大於%s" />
								</div>
								</div>
								<div class="row">
									<label class="col-sm-2 control-label"><span class="s">*
									</span>異動原因：</label>
									<div class="col-sm-2">
										<select id="tranReason_M" name="tran_reason" class="populate placeholder require">
											<option value="">-- 請選擇 --</option>
											<c:forEach items="${sessionScope.inv018_tranResnList}" var="var">
												<option value="${var.CODE}" <c:if test="${element.tran_reason == var.CODE}">selected="selected"</c:if>>${var.NAME}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="row">
									<label class="col-sm-2 control-label">
									備註：</label>
									<div class="col-sm-9">
										<textarea class="form-control" id="memo_M" name="memo"
											rows="3">${element.memo}</textarea>
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<div id="selectMaterialPage"></div>
	</body>
</html>
<script type="text/javascript">

	// 動態設定指定欄位是否啟用檢核
	function enableValidateFlag(id, flag) {
		$("#addEdit").bootstrapValidator("enableFieldValidators", $("#" + id).prop("name"), flag);
	}

	// 重新檢核指定欄位
	function revalidateField(id) {
		$("#addEdit").bootstrapValidator("updateStatus", $("#" + id).prop("name"), "NOT_VALIDATED")
		.bootstrapValidator("validateField", $("#" + id).prop("name"));
	}

	// 檢視畫面設定
	function showSet(btnType) {
		$("#invTranAuditStatus_M").prop("readonly", true).select2();
		onOldMatStatusChange(); // 異動異動前狀態
		onNewMatStatusChange(); // 異動異動後狀態
		if (btnType == "add") {
			//$("#applyBtn").prop("type", "button").prop("disabled", true);
		} else if (btnType == "edit") {
			onWhIdAndMatNoChange();
			$("#tagNo_M").val("<c:out value="${element.tag_no}" />").change();
		}
	}

	// 開啟下拉式選單搜尋文字功能
	function selectDefault() {
		$("#addEdit").find("select").select2();
	}

	// 異動倉庫、料號
	var venSnList = new Array();
	function onWhIdAndMatNoChange() {
		venSnList = new Array();
		$("#tagNo_M").html("");
		$("<option value=''>-- 請選擇 --</option>").appendTo("#tagNo_M");
		$("#tagNo_M").change().select2();
		$("#venSn_M").val("");
		revalidateField("matNoMaterial");

		if ($.trim($("#whIdSelect_M").val()) == "" || $.trim($("#matNoMaterial").val()) == "") {
			return false;
		}
		$.ajax({
			url : CONTEXT_URL + "/inv/inv018/search/tagNo",
			type : "POST",
			data : {
				wh_id : $.trim($("#whIdSelect_M").val()),
				mat_no : $.trim($("#matNoMaterial").val())
			},
			async : false,
			success : function(data) {
				venSnList = data;
				for (var i = 0; i < data.length; i++) {
					$("<option value='" + data[i].tag_no + "'>" + data[i].tag_no + "</option>").appendTo("#tagNo_M");
				}
				if ($("#tagNo_M option").size() > 1) {
					$("#tranQty_M").val("1").prop("readonly", true);
					revalidateField("tranQty_M");
				} else {
					$("#tranQty_M").prop("readonly", false);
				}
			}
		});
	}

	// 異動異動前狀態
	function onOldMatStatusChange() {
		$("#newMatStatus_M option").each(function() {
			$(this).prop("disabled", false);
		});
		if ($("#oldMatStatus_M").val() != "") {
			$("#newMatStatus_M option[value='" + $("#oldMatStatus_M").val() + "']").prop("disabled", true);
		}
	}

	// 異動異動後狀態
	function onNewMatStatusChange() {
		$("#oldMatStatus_M option").each(function() {
			$(this).prop("disabled", false);
		});
		if ($("#newMatStatus_M").val() != "") {
			$("#oldMatStatus_M option[value='" + $("#newMatStatus_M").val() + "']").prop("disabled", true);
		}
	}

	// 異動貼標號碼
	function onTagNoChange() {
		for (var i = 0; i < venSnList.length; i++) {
			if ($("#tagNo_M").val() == venSnList[i].tag_no) {
				$("#venSn_M").val(venSnList[i].tag_no);
				$("#srlNo_M").val(venSnList[i].srl_no);
				break;
			}
		}
	}

	// 儲存、申請
	var submitType = "save";
	function MSubmitData() {
		$.ajax({
			url : CONTEXT_URL + "/inv/inv018/" + submitType,
			type : "POST",
			data : JSON.stringify(form2js($("#addEdit").prop("id"), ".",
					true, null)),
			contentType : "application/json",
			dataType : "json",
			async : false,
			success : function(data) {
				alert(data.msg.replace("error", ""));
				if (data.msg.indexOf("error") >= 0) {
					return false;
				}
				parent.$.fancybox.close();
			}
		});
	}

	//Load script of Select2 and run this
	$(document).ready(function() {
		LoadSelect2Script("<s:url value="/resources/plugins/select2/select2.min.js" />", selectDefault);

		// 料號LOV
		$("#matNoSearch").click(function () {
			openMaterialFrame("selectMaterialPage"); // call common.js
		});

		// 異動倉庫
		$("#whIdSelect_M").change(onWhIdAndMatNoChange);
		// 異動料號
		$("#matNoMaterial").change(onWhIdAndMatNoChange);
		// 異動異動前狀態
		$("#oldMatStatus_M").change(onOldMatStatusChange);
		// 異動異動後狀態
		$("#newMatStatus_M").change(onNewMatStatusChange);
		// 異動貼標號碼
		$("#tagNo_M").change(onTagNoChange);

		// 欄位檢核設定
		var fields = {};
		$(".require").each(function () {
			var name = $(this).prop("name");
			var group = "." + $(this).parent().prop("class");
			fields[name] = {
				group : group,
				validators : {
					notEmpty : {
						message : "不可為空!"
					}
				}
			};
		});
		$("#addEdit").bootstrapValidator({
			fields : fields
		}).on("success.form.bv", function(e) {
			e.preventDefault();
			MSubmitData();
		});

		// 申請按鈕
		$("#applyBtn").click(function() {
			submitType = "apply";
		});
		// 重置按鈕
		$("#cancelBtn").click(function() {
			$("#addEdit").trigger("reset");
			showSet($("#btnType_M").val());
			selectDefault();
		});

		// 根據按鈕型態呈現新增/編輯
		showSet($("#btnType_M").val());
	});
</script>