<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>廠商維護</title>
<script
	src="<s:url value="/resources/plugins/scrolltable/jquery.scrolltable.js" />"></script>
<style type="text/css">

.titleTDVo {
	text-align: center;
}

.textTDVo {
	padding: 0.2em;
}

.selected {
  background-color: #b0bed9;
}
</style>
	
<script type="text/javascript">
	// //Run Select2 on element
	function mselectType() {
		$("#co_SITE_TYPE").select2();
		$("#ctr_RELATION").select2();
		$("#insurance").select2();
	}

	$(document).ready(function() {
						// Load script of Select2 and run this	
						LoadSelect2Script(
								'<s:url value="/resources/plugins/select2/select2.min.js" />',
								mselectType);
						//根據按鈕型態 呈現 編輯 /檢視
						showSet('${btnType}');

						//附件上傳按鈕is_disabled
						if ('${btnType}' == 'add') {
							document.getElementById('upFile').disabled = true;
							document.getElementById('addAccount').disabled = true;
							document.getElementById('editAccount').disabled = true;
						} else {
							document.getElementById('co_UBN_NO').readOnly = true;
						}
						
						if('${mCompany.INSURANCE}'=="N"){
							document.getElementById('ins_AMOUNT').readOnly = true;
						}

						//判斷是否修改表單
						$("#companyAddEdit :input").change(function() {
							$("#companyAddEdit").data("changed", true);
						});
						var orgAddr = $("#addr").val();
												
						//存檔鈕
						$("#saveBtn").click(function() {
							//是否需要驗證廠商類別
							chkEngEuqWh();			
							//驗證地址是否必填
							if($('#addr').val()==''){
								$("#addr").removeAttr('readonly');	
								 $("#addr").keydown(function(e){
								        e.preventDefault();
								    });
							}
														
							if ($('#companyAddEdit')[0].checkValidity()) {
							  // If the form is invalid, submit it. The form won't actually submit;
							  // this will just cause the browser to display the native HTML5 error messages.
								event.preventDefault();
								tosubmitData();
							}
						});
					
						addClassSEL("accountTB");						
											
					});

	//顯示地址處理頁
	function openAddressProcess() {
		var addr = {
			"zip" : $("#zip").val(),
			"city" : $("#city").val(),
			"area" : $("#area").val(),
			"village" : $("#village").val(),
			"road" : $("#road").val(),
			"adjacent" : $("#adjacent").val(),
			"lane" : $("#lane").val(),
			"alley" : $("#alley").val(),
			"subAlley" : $("#sub_ALLEY").val(),
			"doorNo" : $("#door_NO").val(),
			"doorNoEx" : $("#door_NO_EX").val(),
			"floor" : $("#floor").val(),
			"floorEx" : $("#floor_EX").val(),
			"room" : $("#room").val(),
			"remark" : $("#add_OTHER").val(),
			"disabledFields" : $("#disabledFields").val(),
			"callBackFun" : $("#callBackFun").val()
		};
		openAddressDialogFrame("showAddressFrame", addr); // This is implement in common.js
	}

	//地址callBack後動作
	function receviceAddressData(addressObject) {
		$.ajax({
			url : CONTEXT_URL + "/common/address/combineAddress",
			dataType : "json",
			data : addressObject,
			contentType : 'application/json',
			type : "POST",
			async : false,
			success : function(data) {
				$("#addr").val(data.row.fullAddress);
			}
		});
		addressObject = JSON.parse(addressObject);
		$("#zip").val(addressObject.zip);
		$("#city").val(addressObject.city);
		$("#area").val(addressObject.area);
		$("#village").val(addressObject.village);
		$("#road").val(addressObject.road);
		$("#adjacent").val(addressObject.adjacent);
		$("#lane").val(addressObject.lane);
		$("#alley").val(addressObject.alley);
		$("#sub_ALLEY").val(addressObject.subAlley);
		$("#door_NO").val(addressObject.doorNo);
		$("#door_NO_EX").val(addressObject.doorNoEx);
		$("#floor").val(addressObject.floor);
		$("#floor_EX").val(addressObject.floorEx);
		$("#room").val(addressObject.room);
		$("#add_OTHER").val(addressObject.remark);

	}

	//顯示檔案處理頁
	function openFileProcess() {
		openFileDialogFrame("showFileUploadFrame", "COMPANY",
				"${mCompany.CO_VAT_NO}", "CONTRACT");
	}
	
	//存檔檢查
	var s = 0;
	function tosubmitData() {	
		$("#comTypeDiv input:checked").each(function() {
			if ($(this).val() == 'Equ') {
				if ($('#insurance').val() == 'N') {					
					s = 1;
				}
			}
		});	
		if (!CheckCompanyNo($('#co_UBN_NO').val())) {
			s=2;
		}
		
	    if (s==1) {
			alert("廠商類別為設備時，投保金額為必填!");
			s=0;
			return false;
		} else if (s==2) {
			alert("統一編號不合法或格式不對!");
			s=0;
			return false;
		} else {
			if ('${btnType}' == "add") {
				if (confirm("是否確定新增")) {
					MSubmitData();
				}
			} else { //修改
				if (confirm("是否確定修改")) {
					//提交前判断
					if (!($("#companyAddEdit").data("changed"))
							&& orgAddr == $("#addr").val()) {
						alert("無修改任何值!");
					} else {
						MSubmitData();
					}
				}
			}
		}
	}

	//存檔動作
	function MSubmitData() {
				$.ajax({
					url : CONTEXT_URL + "/cM001M_Save/",
					type : 'POST',
					data : JSON.stringify(form2js($("#companyAddEdit").attr("id"), '.',true, null)),
					contentType : 'application/json',
					dataType : "json",
					async : false,
					success : function(data) {
						alert(data.msg);
						if(data.success){					
							parent.$.fancybox.close();
						}				
					}
				});
	}

	//檢視畫面設定
	function showSet(btnType) {
		if (btnType == "view") {
			$("#companyAddEdit :input").prop("disabled", true);
			btnhtml = "<button class='btn btn-success btn-label-left' type='button' id='downFile' onfocus='losesfocus()' onclick='goToDownloadFile(${mCompany.CO_VAT_NO})'><span><i class='fa fa-upload'></i></span> 附件下載</button>";
			$('#btnDiv').append(btnhtml);
			$("#comAccountForm :input").prop("disabled", true);
		}
	}

	//檔案下載
	function goToDownloadFile(fileDoc) {
		openFileDownFrame("showFileDownloadFrame", "COMPANY", fileDoc, "");
	}
	function losesfocus() {
		$('#downFile').blur();
	}

	function disableAmount(insurance) {
		if (insurance == "N") {
			$("#ins_AMOUNT").val(0);
			document.getElementById('ins_AMOUNT').readOnly = true;
		} else {
			document.getElementById('ins_AMOUNT').readOnly = false;
		}
	}

	//判斷廠商類別是否需要驗證
	function chkEngEuqWh() {
		var chkArray = [];
		$("#comTypeDiv input:checked").each(function() {
			chkArray.push($(this).val());
		});
		if (chkArray.length == 0) {
			$("#engEuqWh").prop('required', true);
		} else {
			$("#engEuqWh").prop('required', false);
		}
	}

	//檢核統編
	function CheckCompanyNo(idvalue) {
		var tmp = new String("12121241");
		var sum = 0;
		re = /^\d{8}$/;
		if (!re.test(idvalue)) {
			return false;
		}
		for (i = 0; i < 8; i++) {
			s1 = parseInt(idvalue.substr(i, 1));
			s2 = parseInt(tmp.substr(i, 1));
			sum += cal(s1 * s2);
		}
		if (!valid(sum)) {
			if (idvalue.substr(6, 1) == "7")
				return (valid(sum + 1));
		}
		return (valid(sum));
	}

	function valid(n) {
		return (n % 10 == 0) ? true : false;
	}

	function cal(n) {
		var sum = 0;
		while (n != 0) {
			sum += (n % 10);
			n = (n - n % 10) / 10; // 取整數
		}
		return sum;
	}

	// ================================================================

	function addClassSEL(tbid) {
		$('#' + tbid).on('click', 'tr', function() {
			$('#' + tbid).find('tr').each(function() {
				$(this).removeClass('selected');
			});
			$(this).addClass('selected');
		});
	}

	//顯示帳號編輯頁_新增
	function openAccountProcessAdd() {
		var account = {
			"e_MAIL" : $('#con_EMAIL').val(),
			"co_VAT_NO" : '${mCompany.CO_VAT_NO}',
			"callBackFun" : $("#callBackFunAccount").val()
		};
		openAccountDialogFrame("showAccountFrame", account, 'add');
	}

	//顯示帳號編輯頁_修改
	function openAccountProcessEdit() {
		var account = {};
		var isSELAccount = false;
		var table = document.getElementById('accountTB');
		var rows = document.getElementById('accountTB').getElementsByTagName(
				'tbody')[0].getElementsByTagName('tr');
		for (var i = 0; i < rows.length; i++) {
			var trSel = table.rows[i + 1].getAttribute("class");
			if (trSel != null) {
				var isSel = trSel.indexOf("selected");
				if (isSel != -1) {
					var x = document.getElementById('accountTB').rows[i + 1].cells;
					account = {
						"eng_NM" : x[0].innerHTML,
						"chn_NM" : x[1].innerHTML,
						"psn_PWD" : x[5].innerHTML,
						"dept_ID" : x[4].innerHTML,
						"e_MAIL" : x[3].innerHTML,
						"psn_ID" : x[6].innerHTML,
						"co_VAT_NO" : '${mCompany.CO_VAT_NO}',
						"callBackFun" : $("#callBackFunAccount").val()
					};
					isSELAccount = true;
				}
			}
		}
		if (isSELAccount) {
			openAccountDialogFrame("showAccountFrame", account, 'edit');
		} else {
			alert("請選一筆資料修改!");
		}
	}

	function openAccountDialogFrame(targetFrameId, accountJsonText, btn) {
		$
				.ajax({
					url : CONTEXT_URL + "/cm001/Account/initLoad",
					type : 'POST',
					contentType : "application/json",
					data : JSON.stringify(accountJsonText),
					dataType : "html",
					async : false,
					success : function(data) {
						var hiddenFrameId = "<input type='hidden' id='frameId' name='frameId' value="
									+ targetFrameId + " />";
						var btnType = "<input type='hidden' id='btnTypeId' name='btnTypeId' value="
											+ btn + " />";
						initialDialogFrame(targetFrameId, "帳號維護", hiddenFrameId
								+ btnType + data);
					}
				});
	}

	//帳號編輯callBack後動作
	function toReTB() {
		$('#accountTbody').html("");

		$.ajax({
			url : CONTEXT_URL + "/getCompanyAccountList",
			dataType : "json",
			data : {
				coVatNo : '${mCompany.CO_VAT_NO}'
			},
			type : "POST",
			async : false,
			success : function(data) {

				$.each(data.rows, function(i, item) {
					var row = '';
					var engNm = '';
					var chnNm = '';
					var deptname = '';
					var email = '';
					var deptid = '';
					var psnPwd = '';
					var psnid = '';

					$.each(item, function(ia, itema) {
						if (ia == "eng_NM" || ia == "chn_NM"
								|| ia == "deptName" || ia == "e_MAIL"
								|| ia == "dept_ID" || ia == "psn_PWD"
								|| ia == "psn_ID") {
							if (ia == "eng_NM") {
								engNm = itema;
							} else if (ia == "chn_NM") {
								chnNm = itema;
							} else if (ia == "deptName") {
								deptname = itema;
							} else if (ia == "e_MAIL") {
								email = itema;
							} else if (ia == "dept_ID") {
								deptid = itema;
							} else if (ia == "psn_PWD") {
								psnPwd = itema;
							} else if (ia == "psn_ID") {
								psnid = itema;
							}
						}
					});

					row = '<tr><td class="textTDVo">' + engNm + '</td>';
					row += '<td class="textTDVo">' + chnNm + '</td>';
					row += '<td class="textTDVo">' + deptname + '</td>';
					row += '<td class="textTDVo">' + email + '</td>';

					row += '<td class="textTDVo" style="display:none;">'
							+ deptid + '</td>';
					row += '<td class="textTDVo" style="display:none;">'
							+ psnPwd + '</td>';
					row += '<td class="textTDVo" style="display:none;">'
							+ psnid + '</td><tr>';
					$('#accountTbody').append(row);
				});

			}
		});
	}
</script>
</head>
<body>
	<div class="box-header">
		<div class="box-name">
			<i class="fa fa-edit"></i> <span>廠商維護</span>
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
					<form class="form-horizontal" id="companyAddEdit" action=""
						method="post">
						<div class="col-sm-12" id="btnDiv">
							<button class="btn btn-primary btn-label-left" type="submit"
								id="saveBtn">
								<span><i class="fa fa-save"></i></span>
								<s:message code="button.save" text="儲存" />
							</button>

							<button class="btn btn-success btn-label-left" type="button"
								id="upFile" onclick="openFileProcess()">
								<span><i class="fa fa-upload"></i></span> 附件上傳
							</button>
						</div>
						<div class="clearfix">&nbsp;</div>


						<input id="btntype" name="btntype" type='hidden'
							value="${btnType}" />
						<!-- =================================================================================== -->
						<table style="width: 100%">
							<tr>
								<td nowrap="nowrap" width="15%" align="right" ><label
									class="col-sm-12 control-label"><span
										class="s">* </span>廠商編號：</label></td>
								<td width="35%"><div class="col-sm-10" >
										${mCompany.CO_VAT_NO} <input id="co_VAT_NO" type="hidden"
											name="co_VAT_NO" value="${mCompany.CO_VAT_NO}">
									</div></td>
								<td nowrap="nowrap" width="8%"></td>
								<td width="42%"></td>
							</tr>
						<!-- =================================================================================== -->
							<tr>
								<td nowrap="nowrap" align="right" style="padding-top: 10px"><label class="col-sm-12 control-label"><span
										class="s">* </span>統一編號：</label></td>
								<td style="padding-top: 10px"><div class="col-sm-10">
										<input id="co_UBN_NO" type="text" name="co_UBN_NO"
											class="form-control" value="${mCompany.CO_UBN_NO}"
											required="required" maxlength="20">
									</div></td>
								<td nowrap="nowrap" style="padding-top: 10px"><label class="col-sm-12 control-label">ERP廠商編號：</label></td>
								<td style="padding-top: 5px">
									<div class="col-sm-12">
										<div
											style="width: 60%; height: auto; float: left; display: inline">
											<input id="erp_ID" type="text" name="erp_ID"
												class="form-control" value="${mCompany.ERP_ID}" maxlength="20">
										</div>

										<div
											style="width: 35%; height: auto; float: left; display: inline; padding-left: 10%">

											<div class="checkbox">
												<c:if test="${mCompany.ACTIVATE == 'Y'}">
													<label> <input id="activate" name="activate"
														type="checkbox" checked value="Y"> 啟用<i
														class="fa fa-square-o small"></i>
													</label>
												</c:if>
												<c:if
													test="${mCompany.ACTIVATE == 'N' || mCompany.ACTIVATE ==null}">
													<label> <input id="activate" name="activate"
														type="checkbox" value="Y"> 啟用<i
														class="fa fa-square-o small"></i>
													</label>
												</c:if>
											</div>
										</div>
									</div>
								</td>
							</tr>
						<!-- =================================================================================== -->
							<tr>
								<td nowrap="nowrap" align="right" style="padding-top: 10px"><label
									class="col-sm-12 control-label"><span class="s">*
									</span>廠商名稱：</label></td>
								<td COLSPAN=3 style="padding-top: 10px">
									<div class="col-sm-10">
										<input id="co_NAME" type="text" name="co_NAME"
											class="form-control" value="${mCompany.CO_NAME}"
											required="required" maxlength="100">
									</div>
								</td>
							</tr>
						<!-- =================================================================================== -->
							<tr>
								<td nowrap="nowrap" align="right" style="padding-top: 10px"><label
									class="col-sm-12 control-label"><span class="s">*
									</span>地址：</label></td>
								<td COLSPAN=3 style="padding-top: 10px">
									<div class="col-sm-10">
										<div class="input-group">
											<input size="80" type="text" id="addr" name="addr"
												class="form-control"
												placeholder="Click button to manage address" readonly
												value="${mCompany.ADDR}" required="required"
												autocomplete="off"> <span class="input-group-btn">
												<button id="addrbtn" class="btn btn-primary" type="button"
													onclick="openAddressProcess(receviceAddressData)">
													<i class="fa fa-home"></i>
												</button>
											</span>
										</div>
									</div>
								</td>
							</tr>
						<!-- =================================================================================== -->
							<tr>
								<td nowrap="nowrap" align="right" style="padding-top: 10px"><label
									class="col-sm-12 control-label"><span class="s">*
									</span>廠商類別：</label></td>
								<td COLSPAN=3 style="padding-top: 10px">
									<div class="col-sm-6" id="comTypeDiv">
										<div class="checkbox-inline">
											<c:choose>
												<c:when test="${mCompany.TYPE_ENG == 'Y'}">
													<label> <input id="engEuqWh" type="checkbox"
														name="engEuqWh[]" value="Eng" checked title="請至少勾選一類別">
														工程 <i class="fa fa-square-o"></i>
													</label>
												</c:when>
												<c:otherwise>
													<label> <input id="engEuqWh" type="checkbox"
														name="engEuqWh[]" value="Eng" title="請至少勾選一類別"> 工程
														<i class="fa fa-square-o"></i>
													</label>
												</c:otherwise>
											</c:choose>
										</div>

										<div class="checkbox-inline">
											<c:choose>
												<c:when test="${mCompany.TYPE_EQU == 'Y'}">
													<label> <input id="engEuqWh" type="checkbox"
														name="engEuqWh[]" value="Equ" checked> 設備 <i
														class="fa fa-square-o"></i>
													</label>
												</c:when>
												<c:otherwise>
													<label> <input id="engEuqWh" type="checkbox"
														name="engEuqWh[]" value="Equ"> 設備 <i
														class="fa fa-square-o"></i>
													</label>
												</c:otherwise>
											</c:choose>
										</div>

										<div class="checkbox-inline">
											<c:choose>
												<c:when test="${mCompany.TYPE_WH == 'Y'}">
													<label> <input id="engEuqWh" type="checkbox"
														name="engEuqWh[]" value="Wh" checked> 倉庫 <i
														class="fa fa-square-o"></i>
													</label>
												</c:when>
												<c:otherwise>
													<label> <input id="engEuqWh" type="checkbox"
														name="engEuqWh[]" value="Wh"> 倉庫 <i
														class="fa fa-square-o"></i>
													</label>
												</c:otherwise>
											</c:choose>
										</div>

									</div>
								</td>
							</tr>
						<!-- =================================================================================== -->
							<tr>
								<td nowrap="nowrap" align="right" style="padding-top: 10px"><label
									class="col-sm-12 control-label"><span class="s">*
									</span>電話：</label></td>
								<td style="padding-top: 10px">
									<div class="col-sm-10">
										<input id="co_TEL" type="text" name="co_TEL"
											class="form-control" value="${mCompany.CO_TEL}" required
											pattern="(\+?\d[- .]*){7,13}" title="請輸入電話號碼" maxlength="20" />
									</div>
								</td>
								<td nowrap="nowrap" style="padding-top: 10px"><label
									class="col-sm-12 control-label">傳真：</label></td>
								<td style="padding-top: 10px">
									<div class="col-sm-8">
										<input id="co_FAX" type="text" name="co_FAX"
											class="form-control" value="${mCompany.CO_FAX}" maxlength="20">
									</div>
								</td>
							</tr>
							<!-- =================================================================================== -->
							<tr>
								<td nowrap="nowrap" align="right" style="padding-top: 10px"><label class="col-sm-12 control-label"><span class="s">*
									</span>聯絡人：</label></td>
								<td style="padding-top: 10px">
								<div class="col-sm-10">
										<input id="contact" type="text" name="contact"
											class="form-control" value="${mCompany.CONTACT}" required="required" maxlength="20">
									</div>
								</td>
								<td nowrap="nowrap" style="padding-top: 10px"><label class="col-sm-12 control-label"><span class="s">*
									</span>職稱：</label></td>
								<td style="padding-top: 10px">
								<div class="col-sm-8">
										<input id="con_TITLE" type="text" name="con_TITLE"
											class="form-control" value="${mCompany.CON_TITLE}" required="required" maxlength="50">
									</div>
								</td>
							</tr>
						<!-- =================================================================================== -->
							<tr>
								<td nowrap="nowrap" align="right" style="padding-top: 10px"><label
									class="col-sm-12 control-label"><span class="s">*
									</span>手機號碼：</label></td>
								<td style="padding-top: 10px">
									<div class="col-sm-10">
										<input id="con_MOBILE" type="text" name="con_MOBILE"
											class="form-control" value="${mCompany.CON_MOBILE}" required
											pattern="(\+?\d[- .]*){10}" title="請輸入手機號碼" maxlength="20">
									</div>
								</td>
								<td nowrap="nowrap" style="padding-top: 10px"><label
									class="col-sm-12 control-label"><span class="s">*
									</span>E-Mail：</label></td>
								<td style="padding-top: 10px">
									<div class="col-sm-10">
										<input id="con_EMAIL" type="email" name="con_EMAIL"
											class="form-control" value="${mCompany.CON_EMAIL}"
											required="required" maxlength="50">
									</div>
								</td>
							</tr>
						<!-- =================================================================================== -->
							<tr>
								<td nowrap="nowrap" align="right" style="padding-top: 10px"><label
									class="col-sm-12 control-label"><span class="s">*
									</span>聯絡人2：</label></td>
								<td style="padding-top: 10px">
									<div class="col-sm-10">
										<input id="contact2" type="text" name="contact2"
											class="form-control" value="${mCompany.CONTACT2}"
											required="required" maxlength="20">
									</div>
								</td>
								<td nowrap="nowrap" style="padding-top: 10px"><label
									class="col-sm-12 control-label"><span class="s">*
									</span>職稱2：</label></td>
								<td style="padding-top: 10px">
									<div class="col-sm-8">
										<input id="con_TITLE2" type="text" name="con_TITLE2"
											class="form-control" value="${mCompany.CON_TITLE2}"
											required="required" maxlength="50">
									</div>
								</td>
							</tr>
						<!-- =================================================================================== -->
							<tr>
								<td nowrap="nowrap" align="right" style="padding-top: 10px"><label
									class="col-sm-12 control-label"><span class="s">*
									</span>手機號碼2：</label></td>
								<td style="padding-top: 10px">
									<div class="col-sm-10">
										<input id="con_MOBILE2" type="text" name="con_MOBILE2"
											class="form-control" value="${mCompany.CON_MOBILE2}" required
											pattern="(\+?\d[- .]*){10}" title="請輸入手機號碼" maxlength="20">
									</div>
								</td>
								<td nowrap="nowrap" style="padding-top: 10px"><label
									class="col-sm-12 control-label"><span class="s">*
									</span>E-Mail2：</label></td>
								<td style="padding-top: 10px">
									<div class="col-sm-10">
										<input id="con_EMAIL2" type="email" name="con_EMAIL2"
											class="form-control" value="${mCompany.CON_EMAIL2}"
											required="required" maxlength="50">
									</div>
								</td>
							</tr>
						<!-- =================================================================================== -->
							<tr>
								<td nowrap="nowrap" align="right" style="padding-top: 10px"><label
									class="col-sm-12 control-label">是否投保：</label></td>
								<td style="padding-top: 10px">
									<div class="col-sm-10">
										<select class="populate placeholder" name="insurance"
											id="insurance" onchange="disableAmount(this.value);">
											<c:if
												test="${mCompany.INSURANCE == 'Y' || mCompany.INSURANCE == null}">
												<option value="Y" selected="selected">是</option>
												<option value="N">否</option>
											</c:if>
											<c:if test="${mCompany.INSURANCE == 'N'}">
												<option value="Y">是</option>
												<option value="N" selected="selected">否</option>
											</c:if>
										</select>
									</div>
								</td>
								<td nowrap="nowrap" style="padding-top: 10px"><label
									class="col-sm-12 control-label">投保金額：</label></td>
								<td style="padding-top: 10px">
									<div class="col-sm-8">
										<input id="ins_AMOUNT" type="number" name="ins_AMOUNT" min="1"
											class="form-control" value="${mCompany.INS_AMOUNT}"
											required="required" >
									</div>
								</td>
							</tr>
						<!-- =================================================================================== -->
							<tr>
								<td valign="top" nowrap="nowrap" align="right" style="padding-top: 10px"><label class="col-sm-12 control-label">備註：</label></td>
								<td COLSPAN=3 style="padding-top: 10px">
								<div class="col-sm-11">
										<textarea class="form-control" id="memo" name="memo" rows="3" maxlength="400">${mCompany.MEMO}</textarea>
									</div>
								</td>
							</tr>

						</table>


						<input type="hidden" name="zip" id="zip" value="${mCompany.ZIP}" />
						<input type="hidden" name="city" id="city"
							value="${mCompany.CITY}" /> <input type="hidden" name="area"
							id="area" value="${mCompany.AREA}" /> <input type="hidden"
							name="village" id="village" value="${mCompany.VILLAGE}" /> <input
							type="hidden" name="road" id="road" value="${mCompany.ROAD}" />
						<input type="hidden" name="adjacent" id="adjacent"
							value="${mCompany.ADJACENT}" /> <input type="hidden" name="lane"
							id="lane" value="${mCompany.LANE}" /> <input type="hidden"
							name="alley" id="alley" value="${mCompany.ALLEY}" /> <input
							type="hidden" name="sub_ALLEY" id="sub_ALLEY"
							value="${mCompany.SUB_ALLEY}" /> <input type="hidden"
							name="door_NO" id="door_NO" value="${mCompany.DOOR_NO}" /> <input
							type="hidden" name="door_NO_EX" id="door_NO_EX"
							value="${mCompany.DOOR_NO_EX}" /> <input type="hidden"
							name="floor" id="floor" value="${mCompany.FLOOR}" /> <input
							type="hidden" name="floor_EX" id="floor_EX"
							value="${mCompany.FLOOR_EX}" /> <input type="hidden" name="room"
							id="room" value="${mCompany.ROOM}" /> <input type="hidden"
							name="add_OTHER" id="add_OTHER" value="${mCompany.ADD_OTHER}" />
						<input type="hidden" name="disabledFields" id="disabledFields"
							value="" /> <input type="hidden" name="callBackFun"
							id="callBackFun" value="receviceAddressData" />
					</form>

					<form class="form-horizontal" id="comAccountForm">
						<div id="accountDIV" class="form-group"
							style="margin-bottom: 10px">
							
							<table style="width: 100%">
								<tr>
									<td nowrap="nowrap" width="15%" align="right" style="padding-top: 10px"><label class="col-sm-12 control-label">系統帳號：</label></td>
									<td width="85%" style="padding-top: 10px"><div class="col-md-10">
										<button class="btn btn-success btn-label-left" type="button"
											id="addAccount" onclick="openAccountProcessAdd(toReTB)">新增帳號</button>
										<button class="btn btn-success btn-label-left" type="button"
											id="editAccount" onclick="openAccountProcessEdit(toReTB)">修改帳號</button>
									</div></td>
								</tr>

								<tr>
									<td nowrap="nowrap" width="15%" align="right"
										style="padding-top: 10px"></td>
									<td style="padding-top: 10px">

										<div class="col-sm-11">
											<table id="accountTB" border=1 class="table-heading"
												style="table-layout: fixed; word-break: break-all; word-wrap: break-word; width: 100%;">
												<thead class="tb_thead">
													<tr style="height: 25px">
														<th class="titleTDVo" style="width: 20%">帳號</th>
														<th class="titleTDVo" style="width: 25%">名稱</th>
														<th class="titleTDVo" style="width: 25%">部門</th>
														<th class="titleTDVo" style="width: 30%">E-Mail</th>

														<th class="titleTDVo" style="display: none;">部門id</th>
														<th class="titleTDVo" style="display: none;">pwd</th>
														<th class="titleTDVo" style="display: none;">psnId</th>
													</tr>
												</thead>
												<tbody id="accountTbody">
													<c:forEach items="${comPsnList}" var="comUser">
														<tr>
															<td class="textTDVo">${comUser.ENG_NM}</td>
															<td class="textTDVo">${comUser.CHN_NM}</td>
															<td class="textTDVo">${comUser.deptName}</td>
															<td class="textTDVo">${comUser.e_MAIL}</td>
															<td class="textTDVo" style="display: none;">${comUser.DEPT_ID}</td>
															<td class="textTDVo" style="display: none;">${comUser.PSN_PWD}</td>
															<td class="textTDVo" style="display: none;">${comUser.PSN_ID}</td>
														<tr>
													</c:forEach>

												</tbody>
											</table>
										</div>
									</td>
								</tr>
							</table>
														
						</div>
					</form>

				</div>
			</div>
		</div>
	</div>
	
	<div id="showFileUploadFrame"></div>

	<div id="showAddressFrame"></div>
	<div id="showFileDownloadFrame"></div>
	
	<div id="showAccountFrame"></div>
	
	<input type="hidden" name="callBackFunAccount" id="callBackFunAccount" value="toReTB" />
	
	
	
</body>
</html>