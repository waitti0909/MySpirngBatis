<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>


<!DOCTYPE html>
<html lang="en">
	<head>
		<title></title>
		<script src="<s:url value="/resources/plugins/bootstrapvalidator/bootstrapValidator.min.js" />"></script>
	</head>
	<body>
<div style="margin-top: 5px">
	<fieldset class="ftSolidLine">
		<legend class="legend">出租人</legend>
		<button class="btn btn-primary btn-label" type="button" id="addBtn">新增</button>
		<button class="btn btn-primary btn-label" type="button" id="delBtn" onclick="delRow('lessorTB');clearAll();" >刪除</button>

		<table id="lessorTB"
			class="table table-bordered table-hover table-heading no-border-bottom"
			style="table-layout: fixed; word-break: break-all;">
			<thead>
				<tr>
					<th>出租人證號</th>
					<th>姓名</th>
				</tr>
			</thead>
			<tbody id="lessorListTbody">
			</tbody>
		</table>
		<div id="LessorDiv" style="margin-top: 5px">
			<button class="btn btn-primary btn-label" type="button" id="saveBtn">存檔</button>
			<table style="width: 100%">
				<tr>
					<td width="12%" nowrap="nowrap"><label
						class="col-sm-12 control-label"><span class="s">*</span>出租人類型
							:</label></td>
					<td COLSPAN=2>
						<div class="col-sm-8">
							<select id="lessor_TYPE" class="populate placeholder resetSelect" name="lessor_TYPE"">
								<option value="">--請選擇--</option>
								<c:forEach items="${lsLessorList}" var="lessorType" varStatus="loop">
									<option value="${lessorType.CODE}" title="${lessorType.VALUE2}">${lessorType.NAME}</option>
								</c:forEach>
							</select>
						</div>
					</td>
					<td COLSPAN=3><input id="printamr" name="printamr" class="resetCheckBox" type="checkbox">&nbsp;&nbsp;簽訂委託管理使用收益暨授權簽約書
					</td>
				</tr>

				<tr>
					<td nowrap="nowrap"><label class="col-sm-12 control-label"><span class="s">*</span>所有權人證號
							:</label></td>
					<td width="23%" style="padding-top: 5px">
						<div class="col-sm-12">
							<input class="form-control reset requiredLessor" id="owner_ID" name="owner_ID" title="所有權人證號" maxlength="12"/>
						</div>
					</td>
					<td nowrap="nowrap" width="10%"><label
						class="col-sm-12 control-label"><span class="s">*</span>所有權人姓名 :</label></td>
					<td width="23%" style="padding-top: 5px">
						<div class="col-sm-12">
							<input class="form-control reset requiredLessor" id="owner_NAME" name="owner_NAME" title="所有權人姓名" maxlength="50"/>
						</div>
					</td>
					<td nowrap="nowrap" width="10%"><label
						class="col-sm-12 control-label"><span class="s">*</span>電話:</label></td>
					<td width="22%" style="padding-top: 5px">
						<div class="col-sm-12">
							<input class="form-control reset requiredLessor" id="owner_NBR" name="owner_NBR" title="所有權人電話" maxlength="10"/>
						</div>
					</td>
				</tr>

				<tr>
					<td><label class="col-sm-12 control-label"><span class="s">*</span>所有權人地址 :</label></td>
					<td COLSPAN=5>
						<div class="col-sm-12" style="padding-top: 6px">
							<div
								style="width: 60%; height: auto; float: left; display: inline">
								<input class="form-control reset requiredLessor" id="owner_ADDR" name="owner_ADDR" readonly="readonly" title="所有權人地址 "/>
								<input class="form-control reset" type="hidden" id="owner_ADDR_STD" name="owner_ADDR_STD"/>
							</div>
							<div style="width: 35%; height: auto; float: left; display: inline; margin-left: 5px">
								<button class="btn btn-primary btn-label" type="button" id="ownerAddrBtn">更新地址</button>
							</div>
						</div>
					</td>
				</tr>

				<tr>
					<td nowrap="nowrap"><label class="col-sm-12 control-label"><span
							class="s">*</span>出租人證號 :</label></td>
					<td>
						<div class="col-sm-12">
							<input class="form-control reset requiredLessor" id="lessor_ID" name="lessor_ID" title="出租人證號 " onblur="" maxlength="12"/>
						</div>
					</td>
					<td nowrap="nowrap"><label class="col-sm-12 control-label"><span
							class="s">*</span>出租人姓名 :</label></td>
					<td>
						<div class="col-sm-12">
							<input class="form-control reset requiredLessor" id="lessor_NAME" name="lessor_NAME" title="出租人姓名" maxlength="50"/>
						</div>
					</td>
					<td COLSPAN=2>
						<input id="pldg_PT" name="pldg_PT" type="checkbox" class="resetCheckBox" >&nbsp;&nbsp;是否假扣押
					</td>
				</tr>

				<tr>
					<td nowrap="nowrap" style="padding-top: 6px"><label
						class="col-sm-12 control-label">憑證開立方式 :</label></td>
					<td>
						<div id="voucherTypeDiv" class="col-sm-12" style="padding-top: 12px"></div>
						<input type="hidden" id="voucher_TYPE" name="voucher_TYPE" class="form-control reset" title="憑證開立方式">
					</td>
					<td nowrap="nowrap" style="padding-top: 6px"><label
						class="col-sm-12 control-label">房屋稅籍編號 :</label></td>
					<td>
						<div class="col-sm-12" style="padding-top: 12px">
							<input class="form-control reset" id="house_TAX_NO" name="house_TAX_NO" title="房屋稅籍編號" maxlength="50"/>
						</div>
					</td>
					<td COLSPAN=2></td>
				</tr>
				<tr>
					<td></td>
					<td nowrap="nowrap" style="padding-left: 14px; padding-top: 5px">
					<input id="income_TAX" type="checkbox" class="resetCheckBox" name="income_TAX">&nbsp;&nbsp;代扣所得稅(屋主同意)</td>
					<td nowrap="nowrap"><label class="col-sm-12 control-label"><span
							class="s">*</span>付款方式 :</label></td>
					<td COLSPAN=5 style="padding-top: 5px">
						<div class="col-sm-12">
							<div
								style="width: 38%; height: auto; float: left; display: inline">
								<select id="payment_MODE" class="populate placeholder resetSelect" name="payment_MODE" title="付款方式">
									<option value="">--請選擇--</option>
									<option value="C">支票</option>
									<option value="W">匯款</option>
								</select>
							</div>
							<div style="width: 25%; height: auto; float: left; display: inline; margin-left: 10px; padding-top: 4px">
								<input id="per_MONTH" name="per_MONTH" type="checkbox" class="resetCheckBox" >&nbsp;&nbsp;是否按月開票
							</div>
							<div  style="width: 25%; height: auto; float: left; display: inline; margin-left: 10px; padding-top: 4px">
								營業稅:
								<input type="text" name="business_TAX" id="business_TAX" class="reset" maxlength="10" size="5">
							</div>
							<label style="padding-left: 0px;margin-left: 1px; padding-top: 8px" class="control-label">%</label>
						</div>
					</td>
				</tr>

				<tr>
					<td nowrap="nowrap"><label class="col-sm-12 control-label">與所有權人關係
							:</label></td>
					<td>
						<div class="col-sm-12">
							<!-- <input class="form-control reset" id="owner_RELATION" name="owner_RELATION" maxlength="50"/> -->
							<select id="owner_RELATION" class="populate placeholder resetSelect" name="owner_RELATION">
								<option value="">---請選擇---</option>
								<c:forEach items="${lsRelationMap}" var="data">
									<option value="${data.key}">${data.value}</option>
								</c:forEach>
							</select>
						</div>
					</td>
					<td nowrap="nowrap"><label class="col-sm-12 control-label"><span
							class="s">*</span>戶籍地址 :</label></td>
					<td COLSPAN=3>
						<div class="col-sm-12" style="padding-top: 6px">
							<div
								style="width: 85%; height: auto; float: left; display: inline">
								<input class="form-control reset requiredLessor" id="residence_ADD" name="residence_ADD" title="戶籍地址" readonly="readonly"/>
								<input class="form-control reset" type="hidden" id="residence_ADD_STD" name="residence_ADD_STD" />	
							</div>
							<div
								style="width: 10%; height: auto; float: left; display: inline; margin-left: 5px">
								<button class="btn btn-primary btn-label" type="button" id="residenceAddrBtn">更新地址</button>
							</div>
						</div>
					</td>
				</tr>

				<tr>
					<td nowrap="nowrap"><label class="col-sm-12 control-label">聯絡人姓名
							:</label></td>
					<td>
						<div class="col-sm-12">
							<input class="form-control reset" id="ctact_NAME" name="ctact_NAME" maxlength="50"/>
						</div>
					</td>
					<td nowrap="nowrap"><label class="col-sm-12 control-label">電話
							:</label></td>
					<td>
						<div class="col-sm-12">
							<input class="form-control reset" id="ctact_NBR" name="ctact_NBR" maxlength="10" title="聯絡人電話"/>
						</div>
					</td>
					<td COLSPAN=2></td>
				</tr>

				<tr>
					<td nowrap="nowrap"><label class="col-sm-12 control-label">聯絡地址
							:</label></td>
					<td COLSPAN=5>
						<div class="col-sm-12" style="padding-top: 6px">
							<div style="width: 60%; height: auto; float: left; display: inline">
								<input class="form-control reset" id="ctact_ADDR" name="ctact_ADDR" readonly="readonly" />
								<input class="form-control reset" type="hidden" id="ctact_ADDR_STD" name="ctact_ADDR_STD" />
							</div>
							<div style="width: 10%; height: auto; float: left; display: inline; margin-left: 5px">
								<button class="btn btn-primary btn-label" type="button" id="ctactAddrBtn">更新地址</button>
							</div>
						</div>
					</td>
				</tr>

				<tr>
					<td valign='top' nowrap="nowrap"><label
						class="col-sm-12 control-label">憑證寄送地址 :</label></td>
					<td COLSPAN=5>
						<div class="col-sm-12" style="padding-top: 4px">
							<input id="" name="tax_ADDR_SET" type="checkbox" value="0" class="resetCheckBox" >&nbsp;&nbsp;同出租人聯絡地址&nbsp;&nbsp;
							<input id="" name="tax_ADDR_SET" type="checkbox" value="1" class="resetCheckBox" >&nbsp;&nbsp;同租賃標的物地址&nbsp;&nbsp;<br>

							<div style="width: 12%; height: auto; float: left; display: inline; margin-top: 5px">
								<input id="" name="tax_ADDR_SET"  type="checkbox" value="2" class="resetCheckBox" >&nbsp;&nbsp;另列如右
							</div>
							<div style="width: 70%; height: auto; float: left; display: inline; margin-top: 5px">
								<input class="form-control reset" id="doc_ADDR" name="doc_ADDR" readonly="readonly" />
								<input class="form-control reset" type="hidden" id="doc_ADDR_STD" name="doc_ADDR_STD" />
							</div>
							<div style="width: 8%; height: auto; float: left; display: inline; margin-left: 5px; margin-top: 5px">
								<button class="btn btn-primary btn-label" type="button" id="taxAddrBtn">更新地址</button>
							</div>
						</div>
					</td>
				</tr>
				<%--
				<tr>
					<td nowrap="nowrap"><label class="col-sm-12 control-label" style="padding-left: 0px" >更換出租人租金起付日
							:</label></td>
					<td COLSPAN=5>
						<div class="col-sm-3" style="padding-top: 6px">
							<input id="chPayDate" name="chPayDate" type="text" class="form-control reset"
								readonly="readonly" placeholder="點選輸入框">
						</div>
					</td>
				</tr>
				 --%>
			</table>
		</div>
	</fieldset>
</div>
<div id="showAddressFrame"></div>
</body>
<script type="text/javascript">
	var callBackAddrField;		//Full addr
	var callBackAddrStdField;	//Std addr
	var processType = "1";		//add:1,edit:2
	var btnType = $("#btnType_M").val();
	
	$(document).ready(function() {
		
		//$("#pldg_OUT_DATE").datepicker();
		$("#chPayDate").datepicker();
		
		addClassSEL("lessorTB");
		if (btnType != "new") {
			loadFormData();
		}
		var lsType = '${lease.LS_TYPE}'
		if (btnType == "view" || lsType == "1") {
			$("#mainTabs-2 :input").prop("disabled", true);
		}
	});
	
	function loadFormData() {
		var jsonStr = '${lease.jsonLessors}';
		var jsonArry = JSON.parse(jsonStr);
		for (var i= 0 ; i < jsonArry.length ; i++) {
			$("#lessorListTbody").append(buildLessorListTbody(jsonArry[i]));
		} 
	}
	

	function lessorIdOnblur() {
		var lessorId = $("#lessor_ID").val();
		if ($.trim(lessorId) != "" && checkEng(lessorId.substring(0, 1))) {
			$("#business_TAX").val("");
			$("#business_TAX").prop("disabled", true);
		} else {
			$("#business_TAX").prop("disabled", false);
			$("#business_TAX").val(5);
		}
	}

	//字串是否為英文
	function checkEng(str) {
		var regExp = /^[a-zA-Z]$/;
		if (regExp.test(str))
			return true;
		else
			return false;
	}

	//憑證寄送地址 : 多組 checkbox 單選
	$("input[name='tax_ADDR_SET']:checkbox").click(function() {
		if ($(this).is(":checked")) {
			var group = "input:checkbox[name='" + $(this).prop("name") + "']";
			$(group).prop("checked", false);
			$(this).prop("checked", true);
		} else {
			$(this).prop("checked", false);
		}
		taxAddrCheck();
	});

	//憑證寄送地址:另列如右checkbox
	function taxAddrCheck() {
		var taxAddrChecked = false;
		$("input[name='tax_ADDR_SET']:checkbox").each(function() {
			if (btnType != 'view' && $(this).prop('checked') && $(this).val() == 2) {
				taxAddrChecked = true;
			}
		});
		
		if (taxAddrChecked) {
			$("#taxAddrBtn").prop("disabled", false);
		} else {
			$("#taxAddrBtn").prop("disabled", true);
			$("#doc_ADDR").val("");
			$("#doc_ADDR_STD").val("");
		}
	}
			
	//存檔
	$("#saveBtn").click(
			function() {
				if (notValidData()) {
					return;
				}

				var formData = form2Json();
				var lessorId = $("#lessor_ID").val();
				var hasSameLessorId = false;
				//出租人證號相同則為修改案件
				$(".lessorIds").each(
						function() {
							if (lessorId == $(this).text()) {
								//在edit狀態下，存檔不增加列
								hasSameLessorId = true;
								if (processType == "2") {
									$(this).parent("tr").find(".formData").val(
											JSON.stringify(formData));
									$(this).parent("tr").find(".lessorNames").text(formData.lessor_NAME);
								}
								return;
							}
						});
				//新增狀態下，存檔增加1列
				if ((processType == "1")) {
					if (hasSameLessorId) {
						alert("出租人不可重複");
						return;
					} else {
						$("#lessorListTbody").append(
								buildLessorListTbody(formData));
					}
				}
				clearAll();
			});

	//出租人類型變動時變更憑證開立方式 
	$("#lessor_TYPE").change(function(){
		var voucherType = $(this).find("option:selected").attr("title");
		$("#voucherTypeDiv").text(voucherType);
		$("#voucher_TYPE").val(voucherType);
	});
	
	function notValidData() {
		var isNotValid = false;
		if ($("#lessor_TYPE").find("option:selected").prop("value") == "") {
			$("#mainTabs a[href='#mainTabs-2']").trigger('click');
			alert("請選擇出租人類型!");
			return true;
		}
		if ($("#payment_MODE").find("option:selected").prop("value") == "") {
			$("#mainTabs a[href='#mainTabs-2']").trigger('click');
			alert("請選擇付款方式!");
			return true;
		}

		//營業稅
		if ($.trim($("#business_TAX").val()) != "" && !isAllNumber($("#business_TAX").val())) {
			alert("營業稅必須為正整數");
			$("#business_TAX").focus();
			return true;
		}
		//所有權人電話驗證
		if (!isTel($("#owner_NBR").val())) {
			alert($("#owner_NBR").prop("title") + "格式有誤");
			return true;
		}
		
		//聯絡人電話驗證
		if ($.trim($("#ctact_NBR").val()) != "" && !isTel($("#ctact_NBR").val())) {
			alert($("#ctact_NBR").prop("title") + "格式有誤");
			return true;
		}
		
		$(".requiredLessor").each(function() {
			if ($.trim($(this).val()) == "") {
				$("#mainTabs a[href='#mainTabs-2']").trigger('click');
				alert($(this).prop("title") + "為必填欄位!");
				$(this).focus();
				isNotValid = true;
				return false;
			}
		});

		return isNotValid;

	}
	
	//電話驗證 待確認???
	function isTel(str){
		var reg=/^([0-9]|[\-])+$/g ;
		if(str.length<10 || str.length>18){
			return false;
		} else {
			return reg.exec(str);
		}
	} 

	//動態產生LessorListTbody
	function buildLessorListTbody(formData) {
		var row = "<tr>";
		row += "<td class='lessorIds'>";
		row += formData.lessor_ID;
		row += "</td>";
		row += "<td class='lessorNames'>";
		row += formData.lessor_NAME;
		row += "</td>";
		row += "<input type='hidden' class='formData' value='"
				+ JSON.stringify(formData) + "'/>";
		row += "</tr>";

		return row;
	}

	//點上方出租人區塊，進入修改狀態
	$("#lessorTB").delegate("#lessorListTbody tr", "click", function() {
		processType = "2";
		json2Form($(this).find(".formData").val());
		$("#lessor_ID").prop("readonly", true);
	});

	//出租人下方區塊轉成json格式存在hidden
	function form2Json() {
		var data = form2js($("#LessorDiv").attr("id"), ".", true, null);
		//單選checkbox
		data['income_TAX'] = $("#income_TAX").prop('checked') ? "Y" : "N";
		data['printamr'] = $("#printamr").prop('checked') ? "Y" : "N";
		data['per_MONTH'] = $("#per_MONTH").prop('checked') ? "Y" : "N";
		data['pldg_PT'] = $("#pldg_PT").prop('checked') ? "Y" : "N";
		
		//多選一checkbox
		$("input[name='tax_ADDR_SET']:checkbox").each(function() {
			if ($(this).prop('checked')) {
				data['tax_ADDR_SET'] = $(this).val();
			}
		});

		return data;
	}

	//將json轉成form
	function json2Form(formData) {
		$.each(JSON.parse(formData), function(k, v) {
			if (k == 'income_TAX' || k == 'printamr' || k == 'per_MONTH' || k == 'pldg_PT') {
				$("#" + k).prop("checked", v == "Y" ? true : false);
			} else if (k == 'tax_ADDR_SET') {
				$(".resetCheckBox").prop("checked", false);
				$("input[name='tax_ADDR_SET']:checkbox").each(
						function() {
							if ($(this).val() == v) {
								var group = "input:checkbox[name='"
										+ $(this).prop("name") + "']";
								$(group).prop("checked", false);
								$(this).prop("checked", true);
							}
						});
				taxAddrCheck();
			} else {
				if ($("#" + k).prop("readonly")) {
					$("#" + k).prop("readonly", false);
					$("#" + k).val(v);
					$("#" + k).prop("readonly", true);
				} else {
					$("#" + k).val(v);
				}
			}

		});
		//select下拉式選單
		$(".resetSelect").each(function() {
			$(this).trigger("change");
		});
	}

	$("#addBtn").click(function() {
		clearAll();
	});

	function clearAll() {
		//checkbox 重置
		$(".resetCheckBox").each(function() {
			$(this).prop("checked", false);
		});
		//select下拉式選單 重置
		$(".resetSelect").each(function() {
			$(this).val("");
			$(this).trigger("change");
		});
		//input 重置
		$(".reset").each(function() {
			$(this).val("");
		});
		$("#lessor_ID").prop("readonly", false);
		processType = "1";
	}

	//所有人地址
	$("#ownerAddrBtn").click(function() {
		callBackAddrField = "owner_ADDR";
		callBackAddrStdField = "owner_ADDR_STD";
		openLessorAddressProcess("receviceAddressData");
	});

	//戶籍地址
	$("#residenceAddrBtn").click(function() {
		callBackAddrField = "residence_ADD";
		callBackAddrStdField = "residence_ADD_STD";
		openLessorAddressProcess("receviceAddressData");
	});

	//聯絡地址
	$("#ctactAddrBtn").click(function() {
		callBackAddrField = "ctact_ADDR";
		callBackAddrStdField = "ctact_ADDR_STD";
		openLessorAddressProcess("receviceAddressData");
	});

	//扣繳憑單寄送地址
	$("#taxAddrBtn").click(function() {
		callBackAddrField = "doc_ADDR";
		callBackAddrStdField = "doc_ADDR_STD";
		openLessorAddressProcess("receviceAddressData");
	});

	//顯示地址處理頁
	function openLessorAddressProcess(callBackFunc) {
		//取得相對應hidden正規化地址
		var addrStd = $("#" + callBackAddrStdField).val();
		var addr = {
			"zip" : "",
			"city" : "",
			"area" : "",
			"village" : "",
			"road" : "",
			"adjacent" : "",
			"lane" : "",
			"alley" : "",
			"subAlley" : "",
			"doorNo" : "",
			"doorNoEx" : "",
			"floor" : "",
			"floorEx" : "",
			"room" : "",
			"remark" : "",
			"disabledFields" : "",
			"callBackFun" : callBackFunc
		};
		if (addrStd != "") {
			addr = JSON.parse(addrStd);
		}
		$("#callBackFun").val();
		openAddressDialogFrame("showAddressFrame", addr); // This is implement in common.js
	}

	function receviceAddressData(addressObject) {
		// var obj = JSON.parse(addressObject);
		//將正規化地址暫存至hidden欄位
		$("#" + callBackAddrStdField).val(addressObject);
		$.ajax({
			url : CONTEXT_URL + "/common/address/combineAddress",
			dataType : "json",
			data : addressObject,
			contentType : 'application/json',
			type : "POST",
			async : false,
			success : function(data) {
				$("#" + callBackAddrField).val(data.row.fullAddress);
				//依戶籍地取得預設營業稅
				if (callBackAddrField == "residence_ADD") {
					var obj = JSON.parse(addressObject);
					var tax = getDefaultTax(obj.zip);
					$("#business_TAX").val(tax);
				}
			}
		});
	}
	
</script>