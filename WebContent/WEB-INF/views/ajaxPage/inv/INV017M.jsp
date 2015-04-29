<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<title>序號更正作業</title>
		<script src="<s:url value="/resources/plugins/bootstrapvalidator/bootstrapValidator.min.js" />"></script>
	</head>
	<body>
		<div class="box-header">
			<div class="box-name">
				<i class="fa fa-edit"></i>
				<span>廠商序號更正</span>
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
							<div class="col-sm-4" id="btnDiv">
								<button type="submit" id="saveBtn" class="btn btn-primary btn-label-left">
									<span><i class="fa fa-save"></i></span>
									<s:message code="button.save" text="儲存" />
								</button>
							</div>
							<div class="form-group">
								<div class="col-md-11">
									<div class="row">
										<label class="col-sm-2 control-label">
										放置地點：</label>
										<div class="col-sm-2">
											<input type="text" value="${element.s_site_name}"
												class="form-control" readonly="readonly" />
										</div>
										<label class="col-sm-2 control-label">
										料號：</label>
										<div class="col-sm-2">
											<input type="text" value="${element.mat_no}"
												class="form-control" readonly="readonly" />
										</div>
										<label class="col-sm-2 control-label">
										品名：</label>
										<div class="col-sm-2">
											<input type="text" value="${element.mat_name}"
												class="form-control" readonly="readonly" />
										</div>
									</div>
									<div class="row">
										<label class="col-sm-2 control-label">
										舊序號：</label>
										<div class="col-sm-2">
											<input type="text" value="${element.ven_sn}"
												class="form-control" readonly="readonly" />
										</div>
									</div>
									<div class="row">
										<label class="col-sm-2 control-label">
										新序號：</label>
										<div class="col-sm-2">
											<input type="text" id="venSn_M" name="ven_sn"
												class="form-control require" />
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
<script type="text/javascript">

	// 存檔動作
	function MSubmitData() {
		$.ajax({
			url : CONTEXT_URL + "/inv/inv017/update",
			type : "POST",
			data : {
				srl_no : "<c:out value="${element.srl_no}" />",
				mat_no : "<c:out value="${element.mat_no}" />",
				old_ven_sn : "<c:out value="${element.ven_sn}" />",
				ven_sn : $("#venSn_M").val()
			},
			dataType : "json",
			async : false,
			success : function(data) {
				alert(data.msg);
				if (data.success) {
					parent.$.fancybox.close();
				}
			}
		});
	}

	//Load script of Select2 and run this
	$(document).ready(function() {
		// 欄位檢核設定
		var fields = {};
		$(".require").each(function () {
			var name = $(this).attr("name");
			var group = "." + $(this).parent().attr("class");
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
	});
</script>