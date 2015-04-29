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
		<form class="form-horizontal bootstrap-validator-form" id="mainItemForm" novalidate="novalidate">
			<input type="hidden" id="cat_TYPE" name="cat_TYPE" value="${cat.CAT_TYPE}" />
			<input type="hidden" id="parent_CAT" name="parent_CAT" value="${cat.PARENT_CAT}" />
			<div style="padding-top: 15px;">
				&nbsp;&nbsp;
				<button id="mainItemSaveBtn" class="btn btn-primary btn-label" style="margin-right: 10px" type="submit">存檔</button>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label"><span class="s">*</span>主項編號 :</label>
				<div class="col-sm-2">
				<input id="cat_NO" name="cat_NO" class="form-control integerM"
					type="text" placeholder="整數，必填">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label"><span class="s">*</span>主項名稱 :</label>
				<div class="col-sm-6">
				<input id="cat_NAME" class="form-control require" type="text" maxlength="100"
					name="cat_NAME" placeholder="必填">
			</div>
			</div>
		</form>
	</body>
</html>
<script type="text/javascript">
	// Load script of Select2 and run this
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
		$(".integerM").each(function () {
			var name = $(this).attr("name");
			var group = "." + $(this).parent().attr("class");
			fields[name] = {
				group : group,
				validators : {
					notEmpty: {
                        message: '不得為空'
                    },
					integer: {
                        message: '必須為整數'
                    }
				}
			};
		});
		$("#mainItemForm").bootstrapValidator({
			fields : fields
		}).on("success.form.bv", function(e) {
			e.preventDefault();
			save();
		});
		
	});
	
	// 存檔動作
	function save() {
		$.ajax({
			url : CONTEXT_URL + "/basic/cm003/saveNewItemCategory/",
			type : "POST",
			data : JSON.stringify(form2js($("#mainItemForm").attr("id"), ".",
					true, null)),
			contentType : "application/json",
			dataType : "json",
			async : false,
			success : function(data) {
				alert(data.msg.replace("error", ""));
				
				if (data.msg.indexOf("error") >= 0) {
					return false;
				}
				callbackFun = 'reloadMainItemSelect';
				if (typeof window[callbackFun] === "function") {
					window[callbackFun](data.row);
				}
				
				var frameId = $("#frameId").val();
				$("#" + frameId).dialog('close');
			}
		});
	}
</script>