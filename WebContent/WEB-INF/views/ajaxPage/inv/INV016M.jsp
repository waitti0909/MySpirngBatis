<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<title>倉庫維護作業</title>
		<script src="<s:url value="/resources/plugins/bootstrapvalidator/bootstrapValidator.min.js" />"></script>
	</head>
	<body>
		<div class="box-header">
			<div class="box-name">
				<i class="fa fa-edit"></i>
				<span>倉庫維護</span>
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
							<input type="hidden" id="zip_M" name="zip" value="${element.zip}" />
							<input type="hidden" id="city_M" name="city" value="${element.city}" />
							<input type="hidden" id="area_M" name="area" value="${element.area}" />
							<input type="hidden" id="village_M" name="village" value="${element.village}" />
							<input type="hidden" id="road_M" name="road" value="${element.road}" />
							<input type="hidden" id="adject_M" name="adject" value="${element.adject}" />
							<input type="hidden" id="lane_M" name="lane" value="${element.lane}" />
							<input type="hidden" id="alley_M" name="alley" value="${element.alley}" />
							<input type="hidden" id="subAlley_M" name="sub_alley" value="${element.sub_alley}" />
							<input type="hidden" id="doorNo_M" name="door_no" value="${element.door_no}" />
							<input type="hidden" id="doorNoEx_M" name="door_no_ex" value="${element.door_no_ex}" />
							<input type="hidden" id="floor_M" name="floor" value="${element.floor}" />
							<input type="hidden" id="floorEx_M" name="floor_ex" value="${element.floor_ex}" />
							<input type="hidden" id="room_M" name="room" value="${element.room}" />
							<input type="hidden" id="addOther_M" name="add_other" value="${element.add_other}" />
							<div class="col-sm-4" id="btnDiv">
								<button type="submit" id="saveBtn" class="btn btn-primary btn-label-left">
									<span><i class="fa fa-save"></i></span>
									<s:message code="button.save" text="儲存" />
								</button>
								<button type="button" id="cancelBtn" class="btn btn-default btn-label-left">
									<span><i class="fa fa-undo txt-danger"></i></span>
									<s:message code="button.reset" text="重置" />
								</button>
							</div>
							<div class="form-group">
								<div class="col-md-11">
									<div class="row">
										<label class="col-sm-2 control-label"><span class="s">*
										</span>代碼：</label>
										<div class="col-sm-2">
											<input type="text" id="whId_M" name="wh_id" value="${element.wh_id}"
												class="form-control require" />
										</div>
										<label class="col-sm-2 control-label"><span class="s">*
										</span>名稱：</label>
										<div class="col-sm-2">
											<input type="text" id="whName_M" name="wh_name" value="${element.wh_name}"
												class="form-control require" />
										</div>
									</div>
									<div class="row">
										<label class="col-sm-2 control-label"><span class="s">*
										</span>倉庫類別：</label>
										<div class="col-sm-2">
											<select id="whTypeSelect_M" name="wh_type" class="populate placeholder require">
												<option value="">-- 請選擇 --</option>
												<c:forEach items="${sessionScope.inv016_whTypeList}" var="var">
													<option value="${var.value}" <c:if test="${element.wh_type == var.value}">selected="selected"</c:if>>${var.label}</option>
												</c:forEach>
											</select>
										</div>
										<label class="col-sm-2 control-label"><span class="s">*
										</span>倉庫屬性：</label>
										<div class="col-sm-2">
											<select id="whAttributeSelect_M" name="wh_attribute" class="populate placeholder require">
												<option value="">-- 請選擇 --</option>
												<c:forEach items="${sessionScope.inv016_whAttributeList}" var="var">
													<option value="${var.value}" <c:if test="${element.wh_attribute == var.value}">selected="selected"</c:if>>${var.label}</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="row">
										<label class="col-sm-2 control-label"><span class="s">*
										</span>區域：</label>
										<div class="col-sm-2">
											<select id="domainSelect_M" name="domain" class="populate placeholder require">
												<option value="">-- 請選擇 --</option>
												<c:forEach items="${sessionScope.inv016_orgDomainList}" var="var">
													<option value="${var.ID}" <c:if test="${element.domain == var.ID}">selected="selected"</c:if>>${var.NAME}</option>
												</c:forEach>
											</select>
										</div>
										<label class="col-sm-2 control-label"><span class="s">*
										</span>管理單位：</label>
										<div class="col-sm-2">
											<select id="deptSelect_M" name="dept_id" class="populate placeholder require">
												<option value="">-- 請選擇 --</option>
											</select>
										</div>
									</div>
									<div class="row">
										<label class="col-sm-2 control-label">
										廠商：</label>
										<div class="col-sm-2">
											<select id="coVatNoSelect_M" name="co_vat_no" class="populate placeholder require">
												<option value="">-- 請選擇 --</option>
												<c:forEach items="${sessionScope.inv016_companyList}" var="var">
													<option value="${var.CO_VAT_NO}" <c:if test="${element.co_vat_no == var.CO_VAT_NO}">selected="selected"</c:if>>${var.CO_NAME}</option>
												</c:forEach>
											</select>
										</div>
										<label class="col-sm-2 control-label"><span class="s">*
										</span>保管人：</label>
										<div class="col-sm-2">
											<select id="assignedSelect_M" name="assigned_name" class="populate placeholder require">
												<option value="">-- 請選擇 --</option>
											</select>
										</div>
									</div>
									<div class="row">
										<label class="col-sm-2 control-label"><span class="s">*
										</span>倉庫聯絡人1：</label>
										<div class="col-sm-2">
											<input type="text" id="contact1_M" name="contact1" value="${element.contact1}"
												class="form-control require" />
										</div>
										<label class="col-sm-2 control-label">
										傳真：</label>
										<div class="col-sm-2">
											<input type="text" id="faxNbr_M" name="fax_nbr" value="${element.fax_nbr}"
												class="form-control" />
										</div>
									</div>
									<div class="row">
										<label class="col-sm-2 control-label"><span class="s">*
										</span>聯絡人1電話1：</label>
										<div class="col-sm-2">
											<input type="text" id="contact1Nbr1_M" name="contact1_nbr1" value="${element.contact1_nbr1}"
												class="form-control require" />
										</div>
										<label class="col-sm-2 control-label">
										聯絡人1電話2：</label>
										<div class="col-sm-2">
											<input type="text" id="contact1Nbr2_M" name="contact1_nbr2" value="${element.contact1_nbr2}"
												class="form-control" />
										</div>
									</div>
									<div class="row">
										<label class="col-sm-2 control-label"><span class="s">*
										</span>倉庫聯絡人2：</label>
										<div class="col-sm-2">
											<input type="text" id="contact2_M" name="contact2" value="${element.contact2}"
												class="form-control require" />
										</div>
										<label class="col-sm-2 control-label"><span class="s">*
										</span>聯絡人2電話：</label>
										<div class="col-sm-2">
											<input type="text" id="contact2Nbr_M" name="contact2_nbr" value="${element.contact2_nbr}"
												class="form-control require" />
										</div>
									</div>
									<div class="row">
										<label class="col-sm-2 control-label"><span class="s">*
										</span>聯絡地址：</label>
										<div class="col-sm-10">
											<div class="input-group">
												<input type="text" id="contactAddress_M"
													name="contact_address" value="${element.contact_address}"
													class="form-control require" size="80" readonly="readonly"
													placeholder="Click button to manage address" />
												<span class="input-group-btn">
													<button id="addrbtn" class="btn btn-primary" type="button" onclick="openAddressProcess();">
														<i class="fa fa-home"></i>
													</button> 
												</span>
											</div>
										</div>
									</div>
									<div class="row">
										<label class="col-sm-2 control-label"><span class="s">*
										</span>啟用狀況：</label>
										<div class="col-sm-2">
											<select id="isEffectiveSelect_M" name="is_effective_str" class="populate placeholder require">
												<option value="">-- 請選擇 --</option>
												<c:forEach items="${sessionScope.inv016_isEffectiveList}" var="var">
													<option value="${var.value}" <c:if test="${element.is_effective == (var.value == '0' ? false : true)}">selected="selected"</c:if>>${var.label}</option>
												</c:forEach>
											</select>
										</div>
										<label class="col-sm-2 control-label">
										失效日期：</label>
										<div class="col-sm-2">
											<input type="text" id="failureDate_M" name="str_failure_date"
												class="form-control require" value="<fmt:formatDate value="${element.failure_date}" pattern="yyyy/MM/dd" />" 
												readonly="readonly" placeholder="失效日期" />
										</div>
									</div>
									<div class="row">
										<label class="col-sm-2 control-label">
										失效原因：</label>
										<div class="col-sm-2">
											<select id="failureResnSelect_M" name="failure_resn" class="populate placeholder require">
												<option value="">-- 請選擇 --</option>
												<c:forEach items="${sessionScope.inv016_failureResnList}" var="var">
													<option value="${var.value}" <c:if test="${element.failure_resn == var.value}">selected="selected"</c:if>>${var.label}</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="row">
										<label class="col-sm-2 control-label"></label>
										<div class="col-sm-10">
											<textarea class="form-control require" id="memo_M" name="memo"
												readonly="readonly" rows="3">${element.memo}</textarea>
										</div>
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<div id="showAddressFrame"></div>
	</body>
</html>
<script type="text/javascript">

	// 開啟下拉式選單搜尋文字功能
	function selectDefault() {
		$("#addEdit").find("select").select2();
	}

	// 動態設定指定欄位是否啟用檢核
	function enableValidateFlag(id, flag) {
		$("#addEdit").bootstrapValidator("enableFieldValidators", $("#" + id).attr("name"), flag);
	}

	// 重新檢核指定欄位
	function revalidateField(id) {
		$("#addEdit").bootstrapValidator("updateStatus", $("#" + id).prop("name"), "NOT_VALIDATED")
		.bootstrapValidator("validateField", $("#" + id).prop("name"));
	}

	// 檢視畫面設定
	function showSet(btnType) {
		if (btnType == "view") {
			$("#addEdit :input").prop("disabled", true);
			$("#whTypeSelect_M").change(); // 異動倉庫類別
			$("#deptSelect_M").val("<c:out value="${element.dept_id}" />").change().select2();
			$("#assignedSelect_M").val("<c:out value="${element.assigned_name}" />").select2();
		} else if (btnType == "edit") {
			$("#whTypeSelect_M").change(); // 異動倉庫類別
			$("#isEffectiveSelect_M").change(); // 異動啟用狀況
			$("#failureResnSelect_M").change(); // 異動失效原因
			if ($("#whTypeSelect_M").val() == "V" && $("#whAttributeSelect_M").val() == "T") {
				$("#coVatNoSelect_M").prop("readonly", true).select2();
			}
			$("#deptSelect_M").val("<c:out value="${element.dept_id}" />").change().select2();
			$("#assignedSelect_M").val("<c:out value="${element.assigned_name}" />").select2();
			$("#cancelBtn").prop("disabled", true);
			$("#whId_M").prop("readonly", true);
			$("#whTypeSelect_M").prop("readonly", true);
			$("#whAttributeSelect_M").prop("readonly", true);
			$("#coVatNoSelect_M").prop("readonly", true);
			$("#domainSelect_M").prop("readonly", true);
		} else if (btnType == "add") {
			enableValidateFlag("failureDate_M", false); // 失效日期
			$("#failureDate_M").val("").prop("disabled", true);
			$("#failureResnSelect_M").val("").select2().prop("disabled", true).change(); // 失效原因
		}
	}

	// 異動倉庫類別
	function onWhTypeSelectChange() {
		if ($("#whTypeSelect_M").val() == "" || $("#whTypeSelect_M").val() == "V") {
			// 廠商倉
			$("#coVatNoSelect_M").prop("readonly", false);
			enableValidateFlag("coVatNoSelect_M", true);
		} else {
			$("#coVatNoSelect_M").prop("readonly", true).val("");
			enableValidateFlag("coVatNoSelect_M", false);
		}
		$("#coVatNoSelect_M").select2();
		onWhAttributeSelectChange();
	}

	// 異動倉庫屬性
	function onWhAttributeSelectChange() {
		if ($("#whTypeSelect_M").val() == "V") {
			// 廠商倉
			if ($("#whAttributeSelect_M").val() == "T") {
				$("#whId_M").prop("readonly", true);
				$("#domainSelect_M").val("HQ").prop("readonly", true);
				onCoVatNoSelectChange();
			} else {
				$("#whId_M").prop("readonly", false);
				$("#domainSelect_M").prop("readonly", false);
			}
		} else {
			$("#whId_M").prop("readonly", false);
			$("#domainSelect_M").prop("readonly", false);
		}
		onDomainSelectChange();
		$("#domainSelect_M").select2();
	}

	// 異動區域
	function onDomainSelectChange() {
		$("#deptSelect_M").html("");
		$("<option value=''>-- 請選擇 --</option>").appendTo("#deptSelect_M");
		$("#deptSelect_M").val("");
		$("#deptSelect_M").select2();
		$("#addEdit").bootstrapValidator("resetField", $("#deptSelect_M").attr("name"), true);
		
		onDeptSelectChange();

		if($("#domainSelect_M").val() == "") {
			return false;
		}
		$.ajax({
			url : CONTEXT_URL + "/inv/inv016/search/cityArea",
			type : "POST",
			data : {
				cityArea : $("#domainSelect_M").val()
			},
			async : false,
			success : function(map) {
				for (var key in map) {
					$("<option value='" + key + "'>" + map[key] + "</option>").appendTo("#deptSelect_M");
				}
			}
		});
	}

	// 異動管理單位
	function onDeptSelectChange() {
		$("#assignedSelect_M").html("");
		$("<option value=''>-- 請選擇 --</option>").appendTo("#assignedSelect_M");
		$("#assignedSelect_M").val("");
		$("#assignedSelect_M").select2();
		$("#addEdit").bootstrapValidator("resetField", $("#assignedSelect_M").attr("name"), true);

		if($("#deptSelect_M").val() == "") {
			return false;
		}
		$.ajax({
			url : CONTEXT_URL + "/inv/inv016/search/personnel",
			type : "POST",
			data : {
				deptId : $("#deptSelect_M").val()
			},
			async : false,
			success : function(map) {
				for (var key in map) {
					$("<option value='" + key + "'>" + map[key] + "</option>").appendTo("#assignedSelect_M");
				}
			}
		});
	}

	// 異動廠商
	function onCoVatNoSelectChange() {
		if ($("#whAttributeSelect_M").val() == "T" && $("#coVatNoSelect_M").val() != "") {
			$.ajax({
				url : CONTEXT_URL + "/inv/inv016/search/coErpNo",
				type : "POST",
				data : {
					coVatNo : $("#coVatNoSelect_M").val()
				},
				async : false,
				success : function(coErpNo) {
					$("#whId_M").val(coErpNo);
					revalidateField("whId_M");
				}
			});
		}
	}

	// 異動啟用狀況
	function onIsEffectiveSelectChange() {
		if ($("#isEffectiveSelect_M").val() == "0") {
			// 失效
			$("#failureDate_M").prop("disabled", false);
			$("#failureResnSelect_M").prop("disabled", false);
			enableValidateFlag("failureDate_M", true);
			enableValidateFlag("failureResnSelect_M", true);
		} else {
			// 生效
			enableValidateFlag("failureDate_M", false);
			enableValidateFlag("failureResnSelect_M", false);
			$("#failureDate_M").val("").prop("disabled", true);
			$("#failureResnSelect_M").val("").select2().prop("disabled", true);
		}
		onFailureResnSelectChange();
	}

	// 異動失效原因
	function onFailureResnSelectChange() {
		if ($("#failureResnSelect_M").val() == "OTHERS") {
			$("#memo_M").prop("readonly", false);
			enableValidateFlag("memo_M", true);
		} else {
			enableValidateFlag("memo_M", false);
			$("#memo_M").val("");
			$("#memo_M").prop("readonly", true);
		}
	}

	// 顯示地址處理頁
	function openAddressProcess() {
		var addr = {
			"zip" : $("#zip_M").val(),
			"city" : $("#city_M").val(),
			"area" : $("#area_M").val(),
			"village" : $("#village_M").val(),
			"road" : $("#road_M").val(),
			"adjacent" : $("#adject_M").val(),
			"lane" : $("#lane_M").val(),
			"alley" : $("#alley_M").val(),
			"subAlley" : $("#subAlley_M").val(),
			"doorNo" : $("#doorNo_M").val(),
			"doorNoEx" : $("#doorNoEx_M").val(),
			"floor" : $("#floor_M").val(),
			"floorEx" : $("#floorEx_M").val(),
			"room" : $("#room_M").val(),
			"remark" : $("#addOther_M").val(),
			"disabledFields" : "",
			"callBackFun" : "receviceAddressData"
		};
		openAddressDialogFrame("showAddressFrame", addr); // This is implement in common.js
	}

	// 地址callBack後動作
	function receviceAddressData(addressObject) {
		$.ajax({
			url : CONTEXT_URL + "/common/address/combineAddress",
			dataType : "json",
			data : addressObject,
			contentType : "application/json",
			type : "POST",
			async : false,
			success : function(data) {
				$("#contactAddress_M").val(data.row.fullAddress);
				$("#addEdit").bootstrapValidator("revalidateField", "contact_address");
			}
		});
		addressObject = JSON.parse(addressObject);
		$("#zip_M").val(addressObject.zip);
		$("#city_M").val(addressObject.city);
		$("#area_M").val(addressObject.area);
		$("#village_M").val(addressObject.village);
		$("#road_M").val(addressObject.road);
		$("#adject_M").val(addressObject.adjacent);
		$("#lane_M").val(addressObject.lane);
		$("#alley_M").val(addressObject.alley);
		$("#subAlley_M").val(addressObject.subAlley);
		$("#doorNo_M").val(addressObject.doorNo);
		$("#doorNoEx_M").val(addressObject.doorNoEx);
		$("#floor_M").val(addressObject.floor);
		$("#floorEx_M").val(addressObject.floorEx);
		$("#room_M").val(addressObject.room);
		$("#addOther_M").val(addressObject.remark);
	}

	// 存檔動作
	function MSubmitData() {
		$.ajax({
			url : CONTEXT_URL + "/inv/inv016/save",
			type : "POST",
			data : JSON.stringify(form2js($("#addEdit").attr("id"), ".",
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

	// Load script of Select2 and run this
	$(document).ready(function() {
		LoadSelect2Script("<s:url value="/resources/plugins/select2/select2.min.js" />", selectDefault);

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

		// 失效日期日曆
		$("#failureDate_M").datepicker({
			dateFormat : "yy/mm/dd",
			changeYear : true,
			changeMonth : true,
			showButtonPanel : true,
			onSelect: function(dateText, inst) {
				enableValidateFlag("failureDate_M", false);
			}
		});

		// 異動倉庫類別
		$("#whTypeSelect_M").change(onWhTypeSelectChange);
		// 異動倉庫屬性
		$("#whAttributeSelect_M").change(onWhAttributeSelectChange);
		// 異動區域
		$("#domainSelect_M").change(onDomainSelectChange);
		// 異動管理單位
		$("#deptSelect_M").change(onDeptSelectChange);
		// 異動廠商
		$("#coVatNoSelect_M").change(onCoVatNoSelectChange);
		// 異動啟用狀況
		$("#isEffectiveSelect_M").change(onIsEffectiveSelectChange);
		// 異動失效原因
		$("#failureResnSelect_M").change(onFailureResnSelectChange);

		// 重置按鈕
		$("#cancelBtn").click(function() {
			$("#addEdit").trigger("reset");
			showSet($("#btnType_M").val());
			selectDefault();
		});

		// 根據按鈕型態 呈現 編輯 /檢視
		showSet($("#btnType_M").val());
	});
</script>