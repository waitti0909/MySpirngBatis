<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<!-- 站點資訊 START -->
<div id="topSiteDIV">
	<table style="width: 100%; margin-top: 10px">
		<tr>
			<td width="8%"></td>
			<td width="25%"></td>
			<td width="8%"></td>
			<td width="25%"></td>
			<td width="8%"></td>
			<td width="26%"></td>
		</tr>

		<tr>
			<td nowrap="nowrap"><label class="col-sm-12 control-label">
					<span class="s">*</span>區域 :
			</label></td>
			<td COLSPAN=2>
				<div class="col-sm-12">
					<div style="width: 60%; height: auto; float: left; display: inline">
						<select id="selectDomain" class="populate placeholder" name="">
						</select>
					</div>
					<div style="width: 40%; height: auto; float: left; display: inline">
					</div>
				</div>
			</td>
			<td nowrap="nowrap"><label class="col-sm-12 control-label">建立人員
					:</label></td>
			<td COLSPAN=2>
				<div class="col-sm-12">
					<c:out value="${loginUser.deptName}" />
					&nbsp;/&nbsp;
					<c:out value="${loginUser.chName}" />
				</div>
			</td>
		</tr>

		<tr>
			<td valign="top" nowrap="nowrap" style="padding-top: 6px"><label
				class="col-sm-12 control-label">站點 :</label></td>
			<td COLSPAN=5 style="padding-top: 12px">
				<div class="col-sm-12">
					<table id="siteTB" class="scrollTable"
						style="table-layout: fixed; word-break: break-all; word-wrap: break-word; width: 100%">
						<thead style="background-color: #f0f0f0;">
							<tr style="height: 25px">
								<th class="theadth" style="width: 20%;">站點編號</th>
								<th class="theadth" style="width: 20%;">站點名稱</th>
								<th class="theadth" style="width: 50%;">地址</th>
								<th class="theadth" style="width: 10%;">訊號圖</th>
							</tr>
						</thead>
						<tbody id="siteTbody">
						</tbody>
					</table>
				</div>
			</td>
		</tr>
	</table>
</div>
<!-- 站點資訊 END -->

<form class="form-horizontal" id="lessorChForm">

<!-- 類別、項目 START -->
<div class="form-group" style="margin-top: 15px; margin-bottom: 15px">
	<label class="col-sm-1 control-label">類別 :</label>
	<div class="col-sm-3">
		<select id="selType" class="populate placeholder" name="selType"
			onchange="getItem(this.value);">
			<c:forEach items="${lsAddTypeEnum}" var="item">
				<option value="${item.value}">${item.label}</option>
			</c:forEach>
		</select>
	</div>
	<label class="col-sm-1 control-label">項目 :</label>
	<div class="col-sm-3">
		<select id="itemSEL" class="populate placeholder" name="itemSEL"
			onchange="getTbStyle(this.value);">
			<c:forEach items="${itemSEL}" var="item">
				<option value="${item.key}">${item.value}</option>
			</c:forEach>
		</select>
	</div>

	<div id="printTypeDiv">
		<label class="col-sm-1 control-label">套表格式 :</label>
		<div class="col-sm-3">
			<select id="tbStyleSEL" class="populate placeholder"
				name="tbStyleSEL">
			</select>
		</div>
	</div>
</div>
<!-- 類別、項目 END -->

<hr>
<table style="width: 100%; margin-top: 10px">
	<tr>
		<td width="10%"></td>
		<td width="25%"></td>
		<td width="8%"></td>
		<td width="57%"></td>
	</tr>

	<tr>
		<td nowrap="nowrap"><label class="col-sm-12 control-label"><span
							class="s">*</span>變更起始日
				:</label></td>
		<td>
			<div class="col-sm-12">
				<input id="rent_CHG_DATE" name="rent_CHG_DATE" type="text"
					class="form-control lessorPicker required" readonly="readonly"
					placeholder="點選輸入框" onchange="checkDateFunction()">
			</div>
			<input class="form-control" type="hidden" id="LessorNames_site" />
			<input class="form-control" type="hidden" id="Lessor_allPaymentMode" />
			<input class="form-control" type="hidden" id="LessorRelationType" />
			<input class="form-control" type="hidden" id="locationId" />
			<input class="form-control" type="hidden" name="modifyLessorId" id="modifyLessorId" />
		</td>
	</tr>
</table>


<!-- 原出租人  START -->
<div id="lessorPayerDiv" style="display: none; margin-top: 5px">
	<table style="width: 100%;">
		<tr>
			<td valign="top" nowrap="nowrap"><label
				class="col-sm-1 control-label">原出租人 :</label></td>
		</tr>
		<tr>
			<td style="padding-top: 6px">
				<div class="col-sm-12">
					<table id="lessorOrgTB"
						class="table table-bordered table-hover table-heading no-border-bottom"
			style="table-layout: fixed; word-break: break-all;">
						<thead>
							<tr style="height: 25px">
								<th style="width: 10%;">出租人證號</th>
								<th style="width: 10%;">名稱</th>
								<th style="width: 10%;">押金</th>
							</tr>
						</thead>
						<tbody id="lessorOrgInfo">
						</tbody>
					</table>
				</div>
			</td>
		</tr>
	</table>
</div>
<!-- 原出租人  END -->

<!-- 繼受出租人/變更出租人   START -->
<div id="lessorChDiv" style="display: none; margin-top: 5px">
	<fieldset class="ftSolidLine">
		<legend class="legend">繼受出租人/變更出租人</legend>

		<table style="width: 100%">
			<tr>
				<td nowrap="nowrap" style="width: 14%"><label
					class="col-sm-12 control-label">變更出租人原因 :</label></td>
				<td style="width: 20%">
					<div class="col-sm-12">
						<select id="chg_RESN" class="populate placeholder" name="chg_RESN">
							<option value="">--請選擇--</option>
						</select>
					</div>
				</td>

				<td nowrap="nowrap" style="width: 10%"><label
					class="col-sm-12 control-label">其他原因 :</label></td>
				<td style="width: 56%">
					<div class="col-sm-12">
						<input id="chg_DESC" name="chg_DESC" class="form-control" type="text" maxlength="250" />
					</div>
				</td>
			</tr>

			<tr>
				<td nowrap="nowrap" style="padding-top: 6px"><label
					class="col-sm-12 control-label">押金移轉金額 :</label></td>
				<td style="padding-top: 6px">
					<div class="col-sm-12">
						<input id="pldg_IN" name="pldg_IN" class="form-control"  readonly="readonly" 
							type="text" />
					</div>
				</td>

				<td nowrap="nowrap" style="padding-top: 6px">
<!-- 				<label -->
<!-- 					class="col-sm-12 control-label">押金移轉日期 :</label> -->
				</td>
				<td style="padding-top: 6px">
<!-- 					<div class="col-sm-5"> -->
<!-- 						<input id="pldg_IN_DATE" name="pldg_IN_DATE" type="text" -->
<!-- 							class="form-control lessorPicker" readonly="readonly"  -->
<!-- 							placeholder="點選輸入框"> -->
<!-- 					</div> -->
				</td>
				<input id="pldg_IN_LESSOR" name="pldg_IN_LESSOR" class="form-control" type="hidden" />
			</tr>
		</table>
	</fieldset>
</div>
<!-- 繼受出租人/變更出租人   END -->


<!-- 繼受出租人/變更負責人/變更出租人/出租人更名   START -->
<div id="lessorChNDiv" style="display: none; margin-top: 5px">
	<fieldset class="ftSolidLine">
		<legend class="legend">繼受出租人&變更出租人&變更負責人(以下為空值)/出租人更名(以下帶原出租人)</legend>
		<button class="btn btn-primary btn-label" type="button" id="addLessor">
			新增</button>
		<button class="btn btn-primary btn-label" type="button" id="delLessor"
			onclick="delRow('ls001MTB1');">刪除</button>

		<table id="ls001MTB1"
			class="table table-bordered table-hover table-heading no-border-bottom"
			style="table-layout: fixed; word-break: break-all;">
			<thead>
				<tr>
					<th>出租人證號</th>
					<th>姓名</th>
				</tr>
			</thead>
			<tbody id="lessorEditInfo">
			</tbody>
		</table>
		<div id="lessorEditDiv" style="margin-top: 5px">
			<button class="btn btn-primary btn-label" type="button"
				id="btnLessorSave">存檔</button>
			<table style="width: 100%">
				<tr>
					<td width="12%"></td>
					<td width="23%"></td>
					<td width="10%"></td>
					<td width="23%"></td>
					<td width="10%"></td>
					<td width="22%"></td>
				</tr>

				<tr>
					<td nowrap="nowrap"><label class="col-sm-12 control-label"><span
							class="s">*</span>出租人類型 :</label></td>
					<td COLSPAN=5>
						<div class="col-sm-3">
							<select id="lessor_TYPE" class="populate placeholder resetSelect"
								name="lessor_TYPE">
								<option value="">--請選擇--</option>
							</select>
						</div>
					</td>
				</tr>
				<tr>
					<td nowrap="nowrap"><label class="col-sm-12 control-label"><span
							class="s">*</span>所有權人證號
							:</label></td>
					<td style="padding-top: 6px">
						<div class="col-sm-12">
							<input class="form-control reset" id="owner_ID" name="owner_ID"
								maxlength="20" />
						</div>
					</td>
					<td nowrap="nowrap"><label class="col-sm-12 control-label"><span
							class="s">*</span>所有權人姓名
							:</label></td>
					<td>
						<div class="col-sm-12">
							<input class="form-control  reset" id="owner_NAME"
								name="owner_NAME" maxlength="25" />
						</div>
					</td>
					<td nowrap="nowrap"><label class="col-sm-12 control-label"><span
							class="s">*</span>電話
							:</label></td>
					<td>
						<div class="col-sm-12">
							<input class="form-control reset" id="owner_NBR" name="owner_NBR"
								maxlength="25" />
						</div>
					</td>
				</tr>

				<tr>
					<td nowrap="nowrap"><label class="col-sm-12 control-label"><span
							class="s">*</span>所有權人地址
							:</label></td>
					<td COLSPAN=5>
						<div class="col-sm-12" style="padding-top: 6px">
							<div
								style="width: 60%; height: auto; float: left; display: inline">
								<input type="text" class="form-control reset" id="owner_ADDR"
									name="owner_ADDR" readonly="readonly" /> <input
									class="form-control reset" type="hidden" id="owner_ADDR_STD"
									name="owner_ADDR_STD" />
							</div>
							<div
								style="width: 10%; height: auto; float: left; display: inline; margin-left: 5px">
								<button class="btn-input btn btn-primary btn-label" type="button"
									id="updateOwnerAddrButton">輸入地址</button>
							</div>
						</div>
					</td>
				</tr>

				<tr>
					<td nowrap="nowrap"><label class="col-sm-12 control-label"><span
							class="s">*</span>出租人證號
							:</label></td>
					<td style="padding-top: 6px">
						<div class="col-sm-12">
							<input class="form-control reset" id="lessor_ID" name="lessor_ID"
								maxlength="20" />
						</div>
					</td>
					<td nowrap="nowrap"><label class="col-sm-12 control-label"><span
							class="s">*</span>出租人姓名
							:</label></td>
					<td>
						<div class="col-sm-12">
							<input class="form-control require reset" id="lessor_NAME"
								name="lessor_NAME" maxlength="25" />
						</div>
					</td>
					<td COLSPAN=2></td>
				</tr>

				<tr>
					<td nowrap="nowrap" style="padding-top: 6px"><label
						class="col-sm-12 control-label">憑證開立方式 :</label></td>
					<td>
						<div class="col-sm-12">
							<select id="voucher_TYPE"
								class="populate placeholder resetSelect" name="voucher_TYPE">
								<option value="">--請選擇--</option>
							</select>
						</div>
					</td>
					<td nowrap="nowrap" style="padding-top: 6px"><label
						class="col-sm-12 control-label">房屋稅籍編號 :</label></td>
					<td>
						<div class="col-sm-12">
							<input class="form-control reset" id="house_TAX_NO"
								name="house_TAX_NO" maxlength="25" />
						</div>
					</td>
					<td COLSPAN=2></td>
				</tr>

				<tr>
					<td nowrap="nowrap"></td>
					<td nowrap="nowrap" style="padding-left: 8px; padding-top: 5px"><input
						id="income_TAX" name="income_TAX" type="checkbox"
						class="resetCheckBox">&nbsp;&nbsp;代扣所得稅(屋主同意)</td>
					<td nowrap="nowrap"><label class="col-sm-12 control-label"><span
							class="s">*</span>付款方式 :</label></td>
					<td COLSPAN=3 style="padding-top: 5px">
						<div class="col-sm-12">
							<div
								style="width: 38%; height: auto; float: left; display: inline">
								<select id="payment_MODE"
									class="populate placeholder resetSelect" name="payment_MODE">
									<option value="">--請選擇--</option>
								</select>
							</div>
							<div
								style="width: 40%; height: auto; float: left; display: inline; margin-left: 10px; padding-top: 4px">
								<input id="per_MONTH" name="per_MONTH" type="checkbox"
									class="resetCheckBox">&nbsp;&nbsp;是否按月開票
							</div>
						</div> <input type="hidden" name="business_TAX" id="business_TAX"
						class="reset" maxlength="10" size="5">
					</td>
					<td></td>
				</tr>

				<tr>
					<td nowrap="nowrap"><label class="col-sm-12 control-label">與所有權人關係
							:</label></td>
					<td>
						<div class="col-sm-12">
							<select id="owner_RELATION"
								class="populate placeholder resetSelect" name="owner_RELATION">
								<option value="">---請選擇---</option>
							</select>
						</div>
					</td>
					<td nowrap="nowrap"><label class="col-sm-12 control-label"><span
							class="s">*</span>戶籍地址 :</label></td>
					<td COLSPAN=3>
						<div class="col-sm-12" style="padding-top: 6px">
							<div
								style="width: 85%; height: auto; float: left; display: inline">
								<input class="form-control reset" id="residence_ADD"
									name="residence_ADD" readonly="readonly" /> <input
									class="form-control reset" type="hidden"
									id="residence_ADD_STD" name="residence_ADD_STD" />
							</div>
							<div
								style="width: 10%; height: auto; float: left; display: inline; margin-left: 5px">
								<button class="btn-input btn btn-primary btn-label" type="button"
									id="updateResidenceAddrButton">輸入地址</button>
							</div>
						</div>
					</td>
				</tr>

				<tr>
					<td nowrap="nowrap"><label class="col-sm-12 control-label">聯絡人姓名
							:</label></td>
					<td>
						<div class="col-sm-12">
							<input class="form-control reset" id="ctact_NAME"
								name="ctact_NAME" maxlength="25" />
						</div>
					</td>
					<td nowrap="nowrap"><label class="col-sm-12 control-label">電話
							:</label></td>
					<td>
						<div class="col-sm-12">
							<input class="form-control reset" id="ctact_NBR" name="ctact_NBR"
								maxlength="25" />
						</div>
					</td>
					<td COLSPAN=2></td>
				</tr>

				<tr>
					<td nowrap="nowrap"><label class="col-sm-12 control-label">聯絡地址
							:</label></td>
					<td COLSPAN=5>
						<div class="col-sm-12" style="padding-top: 6px">
							<div
								style="width: 60%; height: auto; float: left; display: inline">
								<input class="form-control reset" id="ctact_ADDR"
									name="ctact_ADDR" readonly="readonly" /> <input
									class="form-control reset" type="hidden" id="ctact_ADDR_STD"
									name="ctact_ADDR_STD" />
							</div>
							<div
								style="width: 10%; height: auto; float: left; display: inline; margin-left: 5px">
								<button class="btn-input btn btn-primary btn-label" type="button"
									id="updateCtactAddrButton">輸入地址</button>
							</div>
						</div>
					</td>
				</tr>

				<tr>
					<td valign='top' nowrap="nowrap"><label
						class="col-sm-12 control-label">扣繳憑單寄送地址 :</label></td>
					<td COLSPAN=5>
						<div class="col-sm-12" style="padding-top: 4px">
							<input id="" name="tax_ADDR_SET" type="checkbox" value="0"
								class="resetCheckBox">&nbsp;&nbsp;同出租人聯絡地址&nbsp;&nbsp; <input
								id="" name="tax_ADDR_SET" type="checkbox" value="1"
								class="resetCheckBox">&nbsp;&nbsp;同租賃標的物地址&nbsp;&nbsp;<br>

							<div
								style="width: 10%; height: auto; float: left; display: inline; margin-top: 5px">
								<input id="" name="tax_ADDR_SET" type="checkbox" value="2"
									class="resetCheckBox">&nbsp;&nbsp;另列如右
							</div>
							<div
								style="width: 70%; height: auto; float: left; display: inline; margin-top: 5px">
								<input class="form-control reset" id="doc_ADDR" name="doc_ADDR"
									readonly="readonly" /> <input class="form-control reset"
									type="hidden" id="doc_ADDR_STD" name="doc_ADDR_STD" />
							</div>
							<div
								style="width: 10%; height: auto; float: left; display: inline; margin-left: 5px; margin-top: 5px">
								<button class="btn-input btn btn-primary btn-label" type="button"
									id="updateDocAddrButton">輸入地址</button>
							</div>
						</div>
					</td>
				</tr>	
				<input class="form-control reset" type="hidden" id="lessorEditState" />
			</table>
		</div>
	</fieldset>
</div>
<!-- 繼受出租人/變更負責人/變更出租人/出租人更名   END -->


<!-- 繼受出租人/變更負責人/變更出租人/出租人更名/變更電匯帳戶   START -->
<div id="accountChDiv" style="display: none; margin-top: 5px">
	<fieldset class="ftSolidLine">
		<legend class="legend">繼受出租人/變更出租人/變更負責人/出租人更名/變更電匯帳戶</legend>

		<div
			style="width: 90%; height: auto; float: left; display: inline; margin-top: 5px">
			<button class="btn btn-primary btn-label" type="button"
				id="btnAddLessor" onclick="addLessorCh('lessorChTbody');">新增</button>
			<button class="btn btn-primary btn-label" type="button" id="btnDel"
				onclick="delRow('lessorChTB');">刪除</button>
		</div>

		<table id="lessorChTB"
			class="table table-bordered table-hover table-heading no-border-bottom"
			style="width: 100%;">
			<thead>
				<tr>
				    <th style="width: 8%;">站點編號</th>
					<th style="width: 8%;">費用項目</th>
					<th style="width: 8%;">出租人</th>
					<th style="width: 8%;">付款對象</th>
					<th style="width: 8%;">名稱</th>
					<th style="width: 8%;">與出租人關係</th>
					<th style="width: 8%;">付款方式</th>
					<th style="width: 8%;">銀行</th>
					<th style="width: 8%;">分行</th>
					<th style="width: 12%;">帳號</th>
					<th style="width: 8%;">分攤比</th>
					<th style="width: 8%;">金額</th>
<!-- 					<th style="width: 8%;">營業稅</th> -->
				</tr>
			</thead>
			<tbody id="lessorChTbody">
			</tbody>
		</table>
	</fieldset>
</div>
<!-- 繼受出租人/變更負責人/變更出租人/出租人更名/變更電匯帳戶   END -->


<!-- 租金抵扣所得稅  START -->
<div id="offsetTaxDiv" style="display: none; margin-top: 5px">
	<fieldset class="ftSolidLine">
		<legend class="legend">租金抵扣所得稅</legend>
		<table style="width: 100%">
			<tr>
				<td nowrap="nowrap" width="8%"><label
					class="col-sm-12 control-label">年度 :</label></td>
				<td width="24%">
					<div class="col-sm-12">
						<input id="tax_YEAR_B" name="tax_YEAR_B" type="text" size="3" onblur="chkInt(this)"
							style="margin: 0px 3px 0px 0px" />至 <input id="tax_YEAR_E" name="tax_YEAR_E"
							type="text" size="3" onblur="chkInt(this)" style="margin: 0px 3px 0px 3px" />年
					</div>
				</td>
				<td nowrap="nowrap" width="10%"><label
					class="col-sm-12 control-label">所得稅金額 :</label></td>
				<td width="24%">
					<div class="col-sm-8">
						<input class="form-control" id="tax_AMT" name="tax_AMT" onblur="checkIntOnBlur(this)" />
					</div>
				</td>
				<td nowrap="nowrap" width="10%"><label
					class="col-sm-12 control-label">扣還年月 :</label></td>
				<td width="24%">
					<div class="col-sm-12">
						<input id="deductYear" name="deductYear" type="text" size="3"
							style="margin: 0px 3px 0px 0px" maxlength="4" onblur="chkInt(this)" />年 <input id="deductMon" name="deductMon"
							type="text" size="3" maxlength="2" onblur="chkInt(this)" style="margin: 0px 3px 0px 3px" />月
					</div>
				</td>
			</tr>
		</table>
	</fieldset>
</div>
<!-- 租金抵扣所得稅  END -->

</form>
<div id="showAddressFrame"></div>

<script type="text/javascript">
	var callBackAddrField; //Full addr
	var callBackAddrStdField; //Std addr
	$(document).ready(function() {

	var appMode =  $("#appMode").text();
		
	//類別
	$('#selType').val('${selType}');
	selText = $("#selType").find("option:selected").text();

	//變更起始日
	$('.lessorPicker').datepicker();

	//按鈕呈現
	$('#buttonDiv').html("");
	var str = '<c:forEach items="${button}" var="data"><button class="btn btn-primary btn-label" type="button" id="${data.key}" style="margin-right: 10px" >${data.value}</button></c:forEach>';
	$('#buttonDiv').append(str);

	//帶出區域資訊
	searchDomain();
	
	//根據區域欄位，帶出站點資訊
	$('#selectDomain').change(function() {
		$.ajax({
				url : CONTEXT_URL+ "/ls/LS001M3_ElecCh/searchTbLsLocation",
				data : {
						lsNo : $("#lsNo").text(),
						domain : $(this).val()},
						type : "POST",
						dataType : 'json',
						success : function(data) {
						$("#siteTbody tr").remove();
						if (data.success) {
						var siteValue = '';
						for (var i = 0; i < data.rows.length; i++) {
							siteValue += '<tr>';
							siteValue += '<td class="tbodytd">'+ data.rows[i].location_ID+ '</td>';
							siteValue += '<td class="tbodytd">'+ data.rows[i].loc_NAME+ '</td>';
							siteValue += '<td class="tbodytd">'+ data.rows[i].addr+ '</td>';
							siteValue += '<td class="tbodytd"><button class="btn-primary" type="button" id="" onclick="downloadPicFile('+data.rows[i].zip_CODE+')">檢視</button></td>';
							siteValue += '</tr>';
						}
						$('#siteTbody').append(siteValue);
					}
				}
			});
	});

	//根據項目欄位內容帶出套表下拉選項
	$('#itemSEL').change(function() {
	initPage($(this).val());//根據選項，初始畫畫面欄位

	$.ajax({
			url : CONTEXT_URL+ "/ls/LS001M3_ElecCh/searchLsAppFORM",
			data : {
					AddItem : $(this).val()},
			type : "POST",
			dataType : 'json',
			success : function(data) {
			$('#tbStyleSEL option').remove();
			$('#tbStyleSEL').val('');
			for (var i = 0; i < data.rows.length; i++) {
				if (i == 0) {
					$('#tbStyleSEL').append('<option value="'+data.rows[i].form_ID+'" selected>'+ data.rows[i].form_NAME+ '</option>');
				} else {
					$('#tbStyleSEL').append('<option value="'+data.rows[i].form_ID+'">'+ data.rows[i].form_NAME+ '</option>');
				}
			}
			$('#tbStyleSEL').trigger('change');
			//查詢變更出租人原因
			getChgResn();
		}
	});
  });

	//變更出租人原因事件
	$('#chg_RESN').change(function() {
		if($(this).val()=='2')
		{
			$("#chg_DESC").prop("readonly", false);
		}else
		{
			$('#chg_DESC').val("");
			$("#chg_DESC").prop("readonly", true);
		}
   });
	
		//項目
		$('#itemSEL').trigger("change");

		//初始化畫面表格CSS
		addClassSEL("lessorChTB");
		addClassSEL("siteTB");
		addClassSEL("lessorOrgTB");
		addClassSEL("ls001MTB1");

		//帶出出租人及付款對象資訊
		searchLsLessorCh();

		//點選原出租人事件
		$('#lessorOrgInfo').on('click','tr',function() {
			var itemSel=$('#itemSEL').val();
			if(itemSel=='變更印鑑')
			{
				$('#pldg_IN_LESSOR').val($.trim($(this).children().find('input[name="orglessor_ID"]').val()));
				if ($.trim($(this).children().find('input[name="orgseal_CHG_DATE"]').val()) != "null") {
					$('#rent_CHG_DATE').val($.trim($(this).children().find('input[name="orgseal_CHG_DATE"]').val()));
				}
				else
				{
					$('#rent_CHG_DATE').val("");
				}
			}
			else if(itemSel=='租金抵扣所得稅')
			{	
				$('#pldg_IN_LESSOR').val($.trim($(this).children().find('input[name="orglessor_ID"]').val()));
				if ($.trim($(this).children().find('input[name="orgtax_YEAR_B"]').val()) != "null") {
					$('#tax_YEAR_B').val($.trim($(this).children().find('input[name="orgtax_YEAR_B"]').val()));
				}
				else
				{
					$('#tax_YEAR_B').val("");
				}
				if ($.trim($(this).children().find('input[name="orgtax_YEAR_E"]').val()) != "null") {
					$('#tax_YEAR_E').val($.trim($(this).children().find('input[name="orgtax_YEAR_E"]').val()));
				}
				else
				{
					$('#tax_YEAR_E').val("");
				}
				if ($.trim($(this).children().find('input[name="orgtax_AMT"]').val()) != "null") {
					$('#tax_AMT').val($.trim($(this).children().find('input[name="orgtax_AMT"]').val()));
				}
				else
				{
					$('#tax_AMT').val("");
				}
				if ($.trim($(this).children().find('input[name="orgdeduct_DATE"]').val()) != "null") {
					var deductDate=$.trim($(this).children().find('input[name="orgdeduct_DATE"]').val());
					$('#deductYear').val(deductDate.substr(0,4));
					$('#deductMon').val(deductDate.substr(4,2));
				}
				else
				{
					$('#deductYear').val("");
					$('#deductMon').val("");
				}
			}
			else if(itemSel=='變更電匯帳戶')
			{
				$('#pldg_IN_LESSOR').val($.trim($(this).children().find('input[name="orglessor_ID"]').val()));
				searchLsPaymentByLessorId($('#itemSEL').val(),$("#lsNo").text(),$.trim($(this).children().find('input[name="orgls_VER"]').val()),$.trim($(this).children().find('input[name="orglessor_ID"]').val()), $("#appSeq").text(),$("#appMode").text());				
			}
			else
			{
				
				if ($.trim($(this).children().find('input[name="orgchg_RESN"]').val()) != "null") {
					$('#chg_RESN').val($.trim($(this).children().find('input[name="orgchg_RESN"]').val()));		
					$('#chg_RESN').trigger("change");
				}
				else
				{
					$('#chg_RESN').val("");		
					$('#chg_RESN').trigger("change");
				}
				
				if ($.trim($(this).children().find('input[name="orgchg_DESC"]').val()) != "null") {
					$('#chg_DESC').val($.trim($(this).children().find('input[name="orgchg_DESC"]').val()));
				}
				else
				{
					$('#chg_DESC').val("");
				}
				
				if ($.trim($(this).children().find('input[name="orgpldg_IN"]').val()) != "null") {		
					$('#pldg_IN').val($.trim($(this).children().find('input[name="orgpldg_IN"]').val()));
				}
				else
				{
					$('#pldg_IN').val("");
				}
				
				if ($.trim($(this).children().find('input[name="orgpldg_IN_DATE"]').val()) != "null"){
					$('#pldg_IN_DATE').val($.trim($(this).children().find('input[name="orgpldg_IN_DATE"]').val()));
				}
				else
				{
					$('#pldg_IN_DATE').val("");
				}
			
				$('#pldg_IN_LESSOR').val($.trim($(this).children().find('input[name="orglessor_ID"]').val()));

				//清空相關表格
				clearAll();
				console.log("orglessor_ID:"+$.trim($(this).children().find('input[name="orglessor_ID"]').val()));
				$('#modifyLessorId').val($.trim($(this).children().find('input[name="orglessor_ID"]').val()));
				searchLsLessorChEdit($('#itemSEL').val(),$("#lsNo").text(),$.trim($(this).children().find('input[name="orgls_VER"]').val()),$.trim($(this).children().find('input[name="orglessor_ID"]').val()), $("#appSeq").text(),$("#appMode").text());
// 				searchLsPaymentByLessorId($('#itemSEL').val(),$("#lsNo").text(),$.trim($(this).children().find('input[name="orgls_VER"]').val()),$.trim($(this).children().find('input[name="orglessor_ID"]').val()), $("#appSeq").text(),$("#appMode").text());
			}
		});////點選原出租人事件 end
		
		//點選編輯出租人區塊
		$('#lessorEditInfo').on('click', 'tr', function() {
			json2Form($(this).find(".formData").val());
			$('#lessorEditState').val("EDIT");			
		});
		
		//當站點時
		$('#siteTbody').on( 'click', 'tr', function () {
			$('#locationId').val($.trim($(this).children().first().text()));
		});
		
		//增補協定頁面共用Function
		initState(appMode);
		
		//控制畫面是否開啟
		initStates(appMode);
		
	});//document end
	
	//select2
	function ls001M3LessorChSelect2() {
		$("select").select2();
	}

	//根據項目選項，初始畫畫面欄位
	function initPage(itemID) {
		//清空相關表格
		clearAll();
		lessorEnable();
		$("#lessorEditInfo tr").remove();
		$("#lessorChTbody tr").remove();
		

		if (itemID == '繼受出租人' || itemID == '變更負責人' || itemID == '變更出租人') {
			$("#addLessor").prop("disabled", false);
			$("#delLessor").prop("disabled", false);
			$("#btnAddLessor").prop("disabled", false);
			$("#btnDel").prop("disabled", false);
		} else if (itemID == '出租人更名' || itemID == '變更印鑑') {
			$("#addLessor").prop("disabled", true);
			$("#delLessor").prop("disabled", true);
			$("#btnAddLessor").prop("disabled", false);
			$("#btnDel").prop("disabled", false);
			if(itemID == '出租人更名')
			{
				$("#btnAddLessor").prop("disabled", true);
				$("#btnDel").prop("disabled", true);
				lessorDisable();
				$("#lessor_NAME").prop("disabled", false);
			}
		}
		else if(itemID == '變更電匯帳戶')
		{
			$("#btnAddLessor").prop("disabled", true);
			$("#btnDel").prop("disabled", true);
		}
	}
	
	//頁面欄位重置
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
	}
	
	//帶出區域資訊
	function searchDomain() {
		$("#selectDomain  option").remove();
		$.ajax({
			url : CONTEXT_URL + "/ls/LS001M3_ElecCh/searchDomain",
			data : {
				lsNo : $("#lsNo").text(),
				lsVer : $("#lsVer").text()
			},
			type : "POST",
			dataType : 'json',
			success : function(data) {
				if (data.success) {
					for (var i = 0; i < data.rows.length; i++) {
						if (i == 0) {
							$('#selectDomain').append(
									'<option value="'+data.rows[i].id+'" selected>'
											+ data.rows[i].name + '</option>');
						} else {
							$('#selectDomain').append(
									'<option value="'+data.rows[i].id+'">'
											+ data.rows[i].name + '</option>');
						}
					}
					$('#selectDomain').trigger('change');
				}
			}
		});
	}
	
	//儲存草稿按鈕事件
	$("#btn_1M3Save_Draft").click(function() {
		if(saveDraftValid())
		{
			$("#selType").prop("disabled", false);
			$("#itemSEL").prop("disabled", false);
			
			paymentEnable();
			
			//取得頁面資訊
			var data = $("#lessorChForm").serialize();
			//取得出租人編輯頁面
			var formDataArray = $(".formData").toArray();
			var array = [];
			var lessorJson = "";
			for (var i = 0; i < formDataArray.length; i++) {
				var formData = formDataArray[i];
				array.push(formData.value);
			}
			if(array.length > 0){   
				lessorJson = array.join(";");
			}
			//主程序
			$.ajax({
				url : CONTEXT_URL + "/ls/LS001M3/LessorCh/saveDraft",
				type : 'POST',
				dataType : "json",
				data : data
						+ "&lessorJson=" + lessorJson
						+ "&lsNo="+$("#lsNo").text()
						+ "&lsVer="+$("#lsVer").text()
						+ "&appSeq="+$("#appSeq").text()
						+ "&appMode="+$("#appMode").text(),
				async : false,
				success : function(data) {
					if(data.success){
						alert(data.msg);
						parent.$.fancybox.close();
					}
					else{
						alert(data.msg);
					}
				}
			});
		}
	});
	
	function saveDraftValid() {
		console.log("modifyLessorId:"+$('#modifyLessorId').val());
		if($('#modifyLessorId').val()=='')
		{
			alert("需點選一筆原出租人資料才可儲存");
			return false;
		}
		if($('#rent_CHG_DATE').val()=='')
		{
			alert("變更起始日為必填欄位");
			return false;
		}
		
		//檢核付款人資訊-銀行帳戶
		var checkBankFlag = checkBankNecessary("lessorChTbody", "payment_Mode", "UNIT_CODE", "SUB_UNIT_CODE","ACCOUNT_NO");
		if(!checkBankFlag){
			return false;
		}
		if($('#itemSEL').val()=='繼受出租人'||$('#itemSEL').val()=='變更出租人')
		{
			//取得頁面資訊
			var data = $("#lessorChForm").serialize();
			var flag=false;
			//主程序
			$.ajax({
				url : CONTEXT_URL + "/ls/LS001M3/LessorCh/locPaymentValidator",
				type : 'POST',
				dataType : "json",
				async : false,
				data : data
						+ "&lsNo="+$("#lsNo").text(),
				async : false,
				success : function(data) {
					if(data.success){
						flag=true;
					}
					else
					{
						alert(data.msg);
					}
				}
			});
			if(!flag)
			{
				return flag;
			}
		}
		else if($('#itemSEL').val()=='租金抵扣所得稅')
		{
			if($('#tax_YEAR_B').val() =="")
			{
				alert("年度 為必填欄位");
				return false;
			}
			if($('#tax_YEAR_E').val() =="")
			{
				alert("年度 為必填欄位");
				return false;
			}
			if($('#tax_AMT').val() =="")
			{
				alert("所得稅金額 為必填欄位");
				return false;
			}
			if($('#deductYear').val() =="")
			{
				alert("扣還年月為必填欄位");
				return false;
			}
			if($('#deductMon').val() =="")
			{
				alert("扣還年月為必填欄位");
				return false;
			}
			
			var lsDate  = $('#lsDate').text().split("~");
			var endYear=new Date(lsDate[1]).getFullYear();
			var startYear=new Date(lsDate[0]).getFullYear();
			
			var nowDate=new Date(new Date().getFullYear(),new Date().getMonth()+1,0);
			nowDate.setMonth(nowDate.getMonth()+2);
			
			if(($('#tax_YEAR_B').val() < startYear || $('#tax_YEAR_B').val() > endYear) || ($('#tax_YEAR_E').val() < startYear || $('#tax_YEAR_E').val()> endYear))
			{
				alert("年度須介於合約起訖年之間");
				return false;
			}
			
			if($('#deductYear').val() < nowDate.getFullYear())
			{
				alert("扣還年月須為 當年當月+1 之後");
				return false;
			}else if($('#deductYear').val() ==nowDate.getFullYear() && $('#deductMon').val() <nowDate.getMonth())	
			{
				alert("扣還年月須為 當年當月+1 之後");
				return false;
			}
		}
		console.log("1");
		return true;
	}

	function chkInt(theField)
	{
		var temp=/^\d+(\.\d+)?$/;
		if(temp.test(theField.value)==false){
		 	alert("欄位內容需為數字，不得為其他文字");
		 	theField.value="";
		 	theField.focus();
			 return false;
		 }
	}
	
	//檢核變更起始日須在合約期間
	function checkDateFunction(){
		var flag = false;
		if($('#rent_CHG_DATE').val() != ''){
			flag = dateInterval($('#rent_CHG_DATE').val());
		}
		if(!flag){
			$('#rent_CHG_DATE').val('');
			alert("變更期間 需在合約起迄之間");
		}
	}
	
	function dateInterval(data){
		var splits  = $('#lsDate').text().split("~");
		if(Date.parse(splits[1]).valueOf() >= Date.parse(data).valueOf() && Date.parse(splits[0]).valueOf() <= Date.parse(data).valueOf()){
			return true;
		}else{
			return false;
		}
	}
//*****************出租人編輯區況**********************************//

	//查詢原出租人及付款對象相關資訊
	function searchLsLessorCh() {
		$.ajax({
			url : CONTEXT_URL + "/ls/LS001M3/LessorCh/searchLsLessorCh",
			data : {
				lsNo : $("#lsNo").text(),
				appSeq : $("#appSeq").text(),
				appMode : $("#appMode").text(),
				itemSEL: $('#itemSEL').val()
			},
			type : "POST",
			dataType : 'json',
			success : function(data) {
				if (data.success) {
					addLessorOrgInfo(data.row['lessors']);
					addPaymentMode(data.row['paymentModeList']);
					addLessorType(data.row['lessorTypeList']);
					addVoucherType(data.row['voucherTypeList']);
					addownerRelation(data.row['ownerRelationList']);

					if($('#itemSEL').val() != '變更印鑑')
					{
						$('#rent_CHG_DATE').val(data.row['rent_CHG_DATE']);
					}
					$('#LessorRelationType').val(data.row['lsLeaseRelation']);
					
					if(data.row['modifyLessorId']=='')
					{
						$('#lessorOrgInfo').find("tr:first").trigger('click');
						$('#modifyLessorId').val($.trim($('#lessorOrgInfo').children().find('input[name="orglessor_ID"]').val()));
						console.log("xxx1"+$.trim($('#lessorOrgInfo').children().find('input[name="orglessor_ID"]').val()));
					}
					else
					{
						console.log("orglessor_ID1:"+data.row['modifyLessorId']);
						$('#lessorOrgInfo tr').each(function(){
							if($(this).find("input[name='orglessor_ID']").val()==data.row['modifyLessorId'])
							{
								$(this).trigger('click')
								console.log("xxx1"+$.trim($('#lessorOrgInfo').children().find('input[name="orglessor_ID"]').val()));
								$('#modifyLessorId').val($.trim($('#lessorOrgInfo').children().find('input[name="orglessor_ID"]').val()));
							}
							
						});
					}
				}
			}
		});
	}

	//原出租人Grid資訊
	function addLessorOrgInfo(data) {
		//清空Grid內容
		$("#lessorOrgInfo tr").remove();
		var lessorVaslue = '';
		for (var i = 0; i < data.length; i++) {
			lessorVaslue += '<tr>';
			lessorVaslue += '<td class="tbodytd" style="width: 10%;">'+ data[i].lessor_ID + '</td>';
			lessorVaslue += '<td class="tbodytd" style="width: 10%;">'+ data[i].lessor_NAME + '</td>';
			lessorVaslue += '<td class="tbodytd" style="width: 10%;">' + ""+ '</td>';
			lessorVaslue += '<td class="tbodytd" style="display: none"><input name="orglessor_ID" type="hiddlen" value="'+data[i].lessor_ID+'"></td>';
			lessorVaslue += '<td class="tbodytd" style="display: none"><input name="orglessor_NAME" type="hiddlen" value="'+data[i].lessor_NAME+'"></td>';
			lessorVaslue += '<td class="tbodytd" style="display: none"><input name="orgls_PLDG" type="hiddlen" value="'+""+'"></td>';
			lessorVaslue += '<td class="tbodytd" style="display: none"><input name="orgls_VER" type="hiddlen" value="'+data[i].ls_VER+'"></td>';
			lessorVaslue += '<td class="tbodytd" style="display: none"><input name="orgchg_RESN" type="hiddlen" value="'+data[i].chg_RESN+'"></td>';
			lessorVaslue += '<td class="tbodytd" style="display: none"><input name="orgchg_DESC" type="hiddlen" value="'+data[i].chg_DESC+'"></td>';
// 			lessorVaslue += '<td class="tbodytd" style="display: none"><input name="orgpldg_IN" type="hiddlen" valu e="'+data[i].pldg_IN+'"></td>';
			lessorVaslue += '<td class="tbodytd" style="display: none"><input name="orgpldg_IN_DATE" type="hiddlen" value="'+data[i].pldg_IN_DATE+'"></td>';
			lessorVaslue += '<td class="tbodytd" style="display: none"><input name="orgPAYMENT_MODE" type="hiddlen" value="'+data[i].payment_MODE+'"></td>';
			lessorVaslue += '<td class="tbodytd" style="display: none"><input name="orgBUSINESS_TAX" type="hiddlen" value="'+data[i].business_TAX+'"></td>';
			lessorVaslue += '<td class="tbodytd" style="display: none"><input name="orgpldg_IN_LESSOR" type="hiddlen" value="'+data[i].pldg_IN_LESSOR+'"></td>';
			lessorVaslue += '<td class="tbodytd" style="display: none"><input name="orgseal_CHG_DATE" type="hiddlen" value="'+data[i].seal_CHG_DATE+'"></td>';
			lessorVaslue += '<td class="tbodytd" style="display: none"><input name="orgtax_YEAR_B" type="hiddlen" value="'+data[i].tax_YEAR_B+'"></td>';
			lessorVaslue += '<td class="tbodytd" style="display: none"><input name="orgtax_YEAR_E" type="hiddlen" value="'+data[i].tax_YEAR_E+'"></td>';
			lessorVaslue += '<td class="tbodytd" style="display: none"><input name="orgtax_AMT" type="hiddlen" value="'+data[i].tax_AMT+'"></td>';
			lessorVaslue += '<td class="tbodytd" style="display: none"><input name="orgdeduct_DATE" type="hiddlen" value="'+data[i].deduct_DATE+'"></td>';
			lessorVaslue += '</tr>';
		}
		$('#lessorOrgInfo').append(lessorVaslue);
	}

	//出租人編輯相關資訊
	function searchLsLessorChEdit(itemSEL, lsNo, lessorVer, lessorId, appSeq,
			appMode) {
		//清空Grid內容
		$("#lessorEditInfo tr").remove();
		$.ajax({
			url : CONTEXT_URL + "/ls/LS001M3/LessorCh/searchLsLessorChEdit",
			data : {
				itemSEL : itemSEL,
				lsNo : lsNo,
				lessorVer : lessorVer,
				lessorId : lessorId,
				appSeq : appSeq,
				appMode : appMode
			},
			type : "POST",
			dataType : 'json',
			success : function(data) {
// 				if (data.success) {
					$('#LessorNames_site').val(data.row['lessorName']);//出租人
					$('#Lessor_allPaymentMode').val(data.row['lessorPaymentMode']);//出租人付款方式
					addLessorEditInfo(data.row['lessor']);
					
					searchLsPaymentByLessorId(itemSEL,lsNo,lessorVer,lessorId,appSeq,appMode);
// 				}
			}
		});
	}

	//出租人編輯區塊
	function addLessorEditInfo(data) {
		var lessorVaslue = '';
		for (var i = 0; i < data.length; i++) {
			lessorVaslue += '<tr>';
			lessorVaslue += "<td class='lessorIds'>";
			lessorVaslue += data[i].lessor_ID;
			lessorVaslue += "</td>";
			lessorVaslue += "<td class='lessorNames'>";
			lessorVaslue += data[i].lessor_NAME;
			lessorVaslue += "</td>";
			lessorVaslue += "<input type='hidden' class='formData' value='"
					+ JSON.stringify(JSON.parse(data[i].lessorToJson)[0])
					+ "'/>";
			lessorVaslue += "</tr>";
		}
		$('#lessorEditInfo').append(lessorVaslue);
		if(data.length>0)
		{
			$('#lessorEditInfo').find("tr:first").trigger('click');
		}
	}
	
	//出租人類型下拉選項
	function addPaymentMode(data) {
		$("#payment_MODE  option").remove();
		$('#payment_MODE').append('<option value="">--請選擇--</option>');
		for (var i = 0; i < data.length; i++) {
			$('#payment_MODE').append('<option value="'+data[i].code+'">' + data[i].name+ '</option>');
		}
	}

	//租金付款方式下拉選項
	function addLessorType(data) {
		$("#lessor_TYPE  option").remove();
		$('#lessor_TYPE').append('<option value="">--請選擇--</option>');
		for (var i = 0; i < data.length; i++) {
			$('#lessor_TYPE').append('<option value="'+data[i].code+'">' + data[i].name+ '</option>');
		}
	}

	//憑證開立方式下拉選項
	function addVoucherType(data) {
		$("#voucher_TYPE  option").remove();
		$('#voucher_TYPE').append('<option value="">--請選擇--</option>');
		for (var i = 0; i < data.length; i++) {
			$('#voucher_TYPE').append('<option value="'+data[i].lookup_CODE+'">'+ data[i].lookup_CODE_DESC + '</option>');
		}
	}

	//查詢與所有權人關係下拉選項 
	function addownerRelation(data) {
		$("#owner_RELATION  option").remove();
		$('#owner_RELATION').append('<option value="">--請選擇--</option>');
		for (var i = 0; i < data.length; i++) {
			$('#owner_RELATION').append('<option value="'+data[i].code+'">' + data[i].name+ '</option>');
		}
	}

	//查詢變更出租人原因
	function getChgResn() {
		var itemSEL = $("#itemSEL").find("option:selected").text();
		var type = '';

		if (itemSEL == '變更出租人') {
			type = 'LS_CHG_RESN_A';
		} else if (itemSEL == '繼受出租人') {
			type = 'LS_CHG_RESN_B';
		}

		if (type != '') {
			$.ajax({
				url : CONTEXT_URL + "/ls/LS001M3/searchSysLookupByType",
				data : {
					type : type
				},
				type : "POST",
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						$("#chg_RESN  option").remove();
						$('#chg_RESN').append(
								'<option value="">--請選擇--</option>');
						for (var i = 0; i < data.rows.length; i++) {
							$('#chg_RESN').append(
									'<option value="'+data.rows[i].code+'">'
											+ data.rows[i].name + '</option>');
						}
						$('#selectDomain').trigger('change');
					}
				}
			});
		}
	}

	//出租人新增事件
	$("#addLessor").click(function() {
		clearAll();
		$('#lessorEditState').val("NEW");
	});

	//出租人存檔事件
	$("#btnLessorSave").click(function() {
		if(lessorValid())
		{
 			lessorEnable();
			var formData = form2Json();
			var lessorId = $("#lessor_ID").val();
			var lessorName = $("#lessor_NAME").val();
			var state = $("#lessorEditState").val();

			var hasSameLessorId = false;

			//出租人證號相同則為修改案件
			$(".lessorIds").each(
					function() {
						if (lessorId == $(this).text()) {
							//在edit狀態下，存檔不增加列
							hasSameLessorId = true;
							if (state == "EDIT") {
								$(this).parent("tr").find(".formData").val(JSON.stringify(formData));
								$(this).parent("tr").find(".lessorNames").text(formData.lessor_NAME);
							}
							return;
						}
					});
			//新增狀態下，存檔增加1列
			if ((state == "NEW")) {
				if (hasSameLessorId) {
					alert("出租人不可重複");
					return;
				} else {
					var lessorVaslue = '<tr>';
					lessorVaslue += "<td class='lessorIds'>";
					lessorVaslue += lessorId;
					lessorVaslue += "</td>";
					lessorVaslue += "<td class='lessorNames'>";
					lessorVaslue += lessorName;
					lessorVaslue += "</td>";
					lessorVaslue += "<input type='hidden' class='formData' value='"
							+ JSON.stringify(formData) + "'/>";
					lessorVaslue += "</tr>";
					$('#lessorEditInfo').append(lessorVaslue);
				}
			}
			changePayment(lessorId,lessorName,lessorEditInfo,$('#itemSEL').val());
		}		
	});
	
	//出租人存檔資料驗證
	function lessorValid(){
		var flag=true;
		$(".require").each(function() {
			if ($.trim($(this).val()) == "") {
				alert("*為必填欄位!");
				flag=false;
				return flag;
			}
		});
		return flag;
	}
	
	//出租人新增或修改後，付款資訊連動事件
	function changePayment(lessorId,lessorName,lessorTDName,itemSel)
	{
		if(itemSel=='出租人更名')
		{
			lessorDisable();
			$("#lessor_NAME").prop("disabled", false);
			
			$('#lessorChTbody tr').each(function(){
				$(this).find("input[name='LESSOR_NAME']").val(lessorName);
				$(this).find("select[name='LESSOR_ID']").prop("disabled", false);
				$(this).find("select[name='LESSOR_ID'] option").remove();
				$(this).find("select[name='LESSOR_ID']").append(
						'<option value="'+lessorId+'" selected>'
								+ lessorName + '</option>');
				$(this).find("select[name='LESSOR_ID']").prop("disabled", true);
				
			});
		}
		else if(itemSel=='繼受出租人'||itemSel=='變更出租人'||itemSel=='變更負責人')
		{
			var lessorVaslue ='<option value="" selected>請選擇</option>';
			$(".lessorIds").each(function() {
				lessorVaslue+='<option value="'+$(this).text()+'">'+ $(this).parent("tr").find(".lessorNames").text() + '</option>';
			});
			console.log(lessorVaslue);
			$('#lessorChTbody tr').each(function(){
				$(this).find("select[name='LESSOR_ID'] option").remove();
				$(this).find("input[name='LESSOR_NAME']").val("");
				$(this).find("select[name='LESSOR_ID']").append(lessorVaslue);		
				$(this).find("input[name='ACCOUNT_NO']").val("");
				$(this).find("select[name='UNIT_CODE']").val("");
				$(this).find("select[name='UNIT_CODE']").trigger('change');
				
			});
		}
	}
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~出租人頁面(json2Form、Form2json)~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~`//   
	//將json轉成form
	function json2Form(formData) {
		$.each(JSON.parse(formData), function(k, v) {		
			if (k == 'income_TAX' || k == 'per_MONTH') {
				$("#" + k).prop("checked", v == "Y" ? true : false);
			} else if (k == 'tax_ADDR_SET') {
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

	//出租人form2Json
	function form2Json() {
		var data = form2js($("#lessorEditDiv").attr("id"), ".", true, null);
		//單選checkbox
		data['income_TAX'] = $("#income_TAX").prop('checked') ? "Y" : "N";
		data['per_MONTH'] = $("#per_MONTH").prop('checked') ? "Y" : "N";

		//多選一checkbox
		$("input[name='tax_ADDR_SET']:checkbox").each(function() {
			if ($(this).prop('checked')) {
				data['tax_ADDR_SET'] = $(this).val();
			}
		});

		return data;
	}
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~出租人地址事件~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~`//   
	//所有權人地址更新事件
	$("#updateOwnerAddrButton").click(function() {
		callBackAddrField = "owner_ADDR";
		callBackAddrStdField = "owner_ADDR_STD";
		openLessorAddressProcess("receviceAddressData");
	});

	//戶籍地址更新事件
	$("#updateResidenceAddrButton").click(function() {
		callBackAddrField = "residence_ADD";
		callBackAddrStdField = "residence_ADD_STD";
		openLessorAddressProcess("receviceAddressData");
	});

	//聯絡地址更新事件
	$("#updateCtactAddrButton").click(function() {
		callBackAddrField = "ctact_ADDR";
		callBackAddrStdField = "ctact_ADDR_STD";
		openLessorAddressProcess("receviceAddressData");
	});

	//扣繳憑單寄送地址更新事件
	$("#updateDocAddrButton").click(function() {
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

	//地址回傳事件
	function receviceAddressData(addressObject) {
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
		$("input[name='tax_ADDR_SET']:checkbox").each(
				function() {
					if ($(this).prop('checked')	&& $(this).val() == 2) {
						$("#updateDocAddrButton").prop("disabled", false);
					} else {
						$("#updateDocAddrButton").prop("disabled", true);
						$("#doc_ADDR").val("");
						$("#doc_ADDR_STD").val("");
					}
				});
	}

//******************************付款資資訊區塊**********************************//
	//出租人付款相關資訊
	function searchLsPaymentByLessorId(itemSEL, lsNo, lessorVer, lessorId, appSeq,appMode) {
		//清空Grid內容
		$("#lessorChTbody tr").remove();
		
		$.ajax({
			url : CONTEXT_URL + "/ls/LS001M3/LessorCh/searchLsPaymentByLessorId",
			data : {
				itemSEL : itemSEL,
				lsNo : lsNo,
				lessorVer : lessorVer,
				lessorId : lessorId,
				appSeq : appSeq,
				appMode : appMode
			},
			type : "POST",
			dataType : 'json',
			success : function(data) {
// 				if (data.success) {
					lsPaymentEditInfo(data.row['lessor']);
					if(itemSEL=='變更電匯帳戶'||itemSEL=='出租人更名')
					{
						paymentDisable();
					}
// 				}
			}
		});
	}
	
	//組出租人付款相關資訊區塊
	function lsPaymentEditInfo(data) {
		var row1 = '';
		for (var i = 0; i < data.length; i++) {
			
			row1 += '<tr><td><input type="text" readOnly style="width: 100px;" name="siteLocationId"  value="'+data[i].location_ID +'"></td>';
			row1 +='<td><select id="" class="" name="PAYMENT_ITEM">';
			if(data[i].payment_ITEM == 'E'){
			    row1 +='<option value="E" selected>電費</option><option value="ED">電費押金</option><option value="R" >租金</option><option value="RD">租金押金</option>';
			}else if (data[i].payment_ITEM == 'ED'){
				row1 +='<option value="E" >電費</option><option value="ED" selected>電費押金</option><option value="R" >租金</option><option value="RD">租金押金</option>';
			}else if(data[i].payment_ITEM == 'R'){
	    	    row1 +='<option value="E" >電費</option><option value="ED">電費押金</option><option value="R" selected>租金</option><option value="RD">租金押金</option>';
	    	}else{
	    		row1 +='<option value="E" >電費</option><option value="ED">電費押金</option><option value="R" >租金</option><option value="RD" selected>租金押金</option>';
	    	}

			row1 +='</select></td>';
			                           
			var lessorNamesValue =  $('#LessorNames_site').val().split(',');
			row1 += '<td><select id="loc_pay_lessor" onchange="changeLessorPaymentMode(this);" name="LESSOR_ID"><option value="" selected>請選擇</option>';
			for(var j = 0 ;j<lessorNamesValue.length;j++){
				var tmpLessor = lessorNamesValue[j].split(":");
				if(tmpLessor[0] == data[i].lessor_ID){
					row1 +='<option value="'+tmpLessor[0]+'" selected>'+tmpLessor[1]+'</option>';
				}else{
					row1 +='<option value="'+tmpLessor[0]+'">'+tmpLessor[1]+'</option>';
				}		
			}

			row1 +='</select><input type="hidden" name="LESSOR_NAME"  id="loc_pay_lessor_name" value="'+data[i].lessor_NAME+'"></td>';
			
			row1 += '<td><input class="form-control" name="RECIPIENT_ID" type="text" style="width: 100%;" value="'+data[i].recipient_ID+'"></td>';
			row1 += '<td><input class="form-control" name="RECIPIENT_NAME" type="text" style="width: 100%;" value="'+data[i].recipient_NAME+'"></td>';
			
			row1 += '<td><select id="locpayment_lessor_relation"  name="LESSOR_RELATION">';
			var typeValue =  $('#LessorRelationType').val().split(';');
			for(var j = 0 ;j<typeValue.length;j++){
				var nameCode = typeValue[j].split(',')
				if(nameCode[0] == data[i].lessor_RELATION){
					row1 += '<option value="'+nameCode[0]+'" selected>'+nameCode[1]+'</option>';
				}else{
					row1 += '<option value="'+nameCode[0]+'">'+nameCode[1]+'</option>';
				}
			}
			row1 += '</select></td>';
			
			row1 += "<td><input type='text' class='form-control' readOnly id='payment_Mode_text_id' name='payment_Mode_text' value='"+getLessorPaymentModeText(data[i].payment_MODE)+"' >";
			
			row1 += '<input type="hidden" name="payment_Mode"  value="'+data[i].payment_MODE +'"></td></td>';
			row1 += '<td><select  name="UNIT_CODE"onchange="changeBanksSub(this);" >'+buildBanksOptions(data[i].unit_CODE)+'</select></td>';
			row1 += '<td><select name="SUB_UNIT_CODE" >"'+buildSubBanksOptions(data[i].unit_CODE, data[i].sub_UNIT_CODE) +'</select></td>';
			row1 += '<td><input class="form-control" name="ACCOUNT_NO"  type="text" style="width: 100%;" value="'+data[i].account_NO+'"></td>';
			row1 += '<td><input class="form-control" name="PAY_ALOC" onblur="checkIntOnBlur(this);" max="3" type="number" style="width: 100%;padding: 2px 0px 0px 10px" value="'+data[i].pay_ALOC+'"></td>';
			row1 += '<td><input class="form-control" name="PAY_AMT" onblur="checkIntOnBlur(this);" max="9" type="number" style="width: 100%;padding: 2px 0px 0px 10px" value="'+data[i].pay_AMT+'">';
			row1 += '<input class="form-control" name="BUSINESS_TAX"  min="0" size="3" max="3" type="hidden" style="width: 100%;padding: 2px 0px 0px 10px" value="'+data[i].business_TAX+'"></div>';
			row1 += '</td></tr>';
			
		}
		console.log(row1);
		$('#lessorChTbody').append(row1);
	}
	
	function paymentDisable() {
		$('#lessorChTbody tr').each(function(){
			$(this).find("select[name='PAYMENT_ITEM']").prop("disabled", true);
			$(this).find("select[name='LESSOR_ID']").prop("disabled", true);
			$(this).find("input[name='RECIPIENT_ID']").prop("disabled", true);
			$(this).find("input[name='RECIPIENT_NAME']").prop("disabled", true);
			$(this).find("select[name='LESSOR_RELATION']").prop("disabled", true);
			$(this).find("input[name='PAY_ALOC']").prop("disabled", true);
			$(this).find("input[name='PAY_AMT']").prop("disabled", true);
			$(this).find("input[name='BUSINESS_TAX']").prop("disabled", true);
		});
	}
	
	function paymentEnable() {
		$('#lessorChTbody tr').each(function(){
			$(this).find("select[name='PAYMENT_ITEM']").prop("disabled", false);
			$(this).find("select[name='LESSOR_ID']").prop("disabled", false);
			$(this).find("input[name='RECIPIENT_ID']").prop("disabled", false);
			$(this).find("input[name='RECIPIENT_NAME']").prop("disabled", false);
			$(this).find("select[name='LESSOR_RELATION']").prop("disabled", false);
			$(this).find("input[name='PAY_ALOC']").prop("disabled", false);
			$(this).find("input[name='PAY_AMT']").prop("disabled", false);
			$(this).find("input[name='BUSINESS_TAX']").prop("disabled", false);
		});
	}
	
	function lessorDisable(){
		//checkbox
		$(".resetCheckBox").each(function() {
			$(this).prop("disabled", true);
		});
		//select下拉式選單
		$(".resetSelect").each(function() {
			$(this).prop("disabled", true);
		});
		//input
		$(".reset").each(function() {
			$(this).prop("disabled", true);
		});
		
		//地址 Button
		$(".btn-input").each(function() {
			$(this).prop("disabled", true);
		});
	}
	
	function lessorEnable(){
		//checkbox
		$(".resetCheckBox").each(function() {
			$(this).prop("disabled", false);
		});
		//select下拉式選單
		$(".resetSelect").each(function() {
			$(this).prop("disabled", false);
		});
		//input
		$(".reset").each(function() {
			$(this).prop("disabled", false);
		});
		//地址 Button
		$(".btn-input").each(function() {
			$(this).prop("disabled", false);
		});
		
	}
	
	//新增變更電匯帳戶
	function addLessorCh(tbodyID) {
		if( $('#LessorNames_site').val()=='' || $('#LessorNames_site').val()==','){
			alert("請先選擇原出租人資料");
			return false;
		}
		if($('#locationId').val()==''){
			alert("請先選擇站點資料");
			return false;
		}
		
		var lessorNamesValue =  $('#LessorNames_site').val().split(',');
		var row1 = '<tr><td><input type="text" readOnly style="width: 100px;" name="siteLocationId"  value="'+$('#locationId').val()+'"></td>';
		row1 +='<td><select id="" class="" name="PAYMENT_ITEM">';
		row1 +='<option value="E" selected>電費</option><option value="ED">電費押金</option><option value="R" selected>租金</option><option value="RD">租金押金</option>';
		
		row1 +='</select></td>';
		
		row1 += '<td><select id="loc_pay_lessor" onchange="changeLessorPaymentMode(this);" name="LESSOR_ID"><option value="" selected>請選擇</option>';
		var firstElement ="";
		var firstElementId ="";
		for(var j = 0 ;j<lessorNamesValue.length;j++){
			var tmpLessor = lessorNamesValue[j].split(":");
			if(j==0){
				firstElement=tmpLessor[1];
				firstElementId=tmpLessor[0];
				row1 +='<option value="'+tmpLessor[0]+'" selected >'+tmpLessor[1]+'</option>';
			}else{
				row1 +='<option value="'+tmpLessor[0]+'" >'+tmpLessor[1]+'</option>';
			}
			
		}
		
		row1 +='</select><input type="hidden" name="LESSOR_NAME" value="'+firstElement+'"  id="loc_pay_lessor_name" ></td>';
		row1 += '<td><input class="form-control" name="RECIPIENT_ID" type="text" style="width: 100%;" value=""></td>';
		row1 += '<td><input class="form-control" name="RECIPIENT_NAME" type="text" style="width: 100%;" value=""></td>';
		row1 += '<td><select id="locpayment_lessor_relation"  name="LESSOR_RELATION">';
		
		var typeValue =  $('#LessorRelationType').val().split(';');
		for(var j = 0 ;j<typeValue.length;j++){
			var nameCode = typeValue[j].split(',')
			row1 += '<option value="'+nameCode[0]+'" >'+nameCode[1]+'</option>';
		}
		row1 += '</select></td>';
		var paymentAndTax = getLessorPaymentModeAndTax(firstElementId).split(":");
		row1 += "<td><input type='text' class='form-control' readOnly id='payment_Mode_text_id' name='payment_Mode_text' value='"+getLessorPaymentModeText(paymentAndTax[0])+"' >";
		
		row1 += '<input type="hidden" name="payment_Mode"  value="'+paymentAndTax[0]+'"></td></td>';
		row1 += '<td><select  name="UNIT_CODE" onchange="changeBanksSub(this);"  id="edit_unit_CODE">';
		row1 += buildBanksOptions();
		row1 +='</select></td>';
		row1 += '<td><select name="SUB_UNIT_CODE" id="edit_sub_unit_code" ><option value="" >--- 請選擇 ---</option></select></td>';
		row1 += '<td><input class="form-control" name="ACCOUNT_NO" type="text" style="width: 100%;" value=""></td>';
		row1 += '<td><input class="form-control" name="PAY_ALOC" onblur="checkIntOnBlur(this);" max="3" type="number" style="width: 100%;padding: 2px 0px 0px 10px" value="0"></td>';
		row1 += '<td><input class="form-control" name="PAY_AMT" onblur="checkIntOnBlur(this);" max="9" type="number" style="width: 100%;padding: 2px 0px 0px 10px" value="0">';
		row1 += '<div style="width: 70%; height: auto; float: left; display: inline">';
		row1 += '<input class="form-control"  name="BUSINESS_TAX" min="0" type="hidden" style="width: 100%;padding: 2px 0px 0px 10px" value="'+paymentAndTax[1]+'" size="3" max="3"></div>';
		row1 += '</td></tr>';
		
		$("#" + tbodyID).append(row1);
		
		LoadSelect2Script(
			'<s:url value="/resources/plugins/select2/select2.min.js" />',
			ls001M3LessorChSelect2);
	}
	
	//銀行帳號連動事件
	function changeBanksSub(theField){
		var tempSelect = $(theField).parent().parent().find("select[name='SUB_UNIT_CODE']");
		tempSelect.empty();
		
		tempSelect.append(buildSubBanksOptions(theField.value,""));
		
		tempSelect.val("");
		tempSelect.select2({ allowClear: true });
	}
	
	//出租人連動事件
	function changeLessorPaymentMode(theField){
		var allLessorPaymentMode = $("#Lessor_allPaymentMode").val().split(",");
		console.log("allLessorPaymentMode="+allLessorPaymentMode);
		var paymentModeString ="";
		var businessTax ="";
		for(var i=0;i<allLessorPaymentMode.length;i++){
			var lessor = allLessorPaymentMode[i].split(":");
			console.log("lessor="+lessor[0]+lessor[1]);
			if(theField.value==lessor[0]){
				paymentModeString=lessor[1];
				businessTax=lessor[2];
				break;
			}
		}
		$(theField).parent().parent().find("input[name='payment_Mode_text']").val(getLessorPaymentModeText(paymentModeString));
	
		$(theField).parent().parent().find("input[name='payment_Mode']").val(paymentModeString);
		$(theField).parent().parent().find("input[name='BUSINESS_TAX']").val(businessTax);		
		
		$(theField).parent().find("input[name='LESSOR_NAME']").val($(theField).find("option:selected").text());		
	}
	
	//匯款方式
	function getLessorPaymentModeText(pCode){
		if(pCode=='C'){	 
			return "支票"; 
		}
		if(pCode=='W'){	 
			return "匯款"; 
		}
	}
	
	//取得營業稅
	function getLessorPaymentModeAndTax(lessorId){
		var allLessorPaymentMode = $("#Lessor_allPaymentMode").val().split(",");
		
		var paymentMode ="Q";
		var businessTax ="5";
		for(var i=0;i<allLessorPaymentMode.length;i++){
			var lessor = allLessorPaymentMode[i].split(":");
			
			if(lessorId==lessor[0]){
				paymentMode=lessor[1];
				businessTax=lessor[2];
				break;
			}
		}
	
		return paymentMode+":"+businessTax;
	}
	//控制畫面是否開啟
	function initStates(appMode){
		if(appMode=='VIEW'){
			$("#topSiteDIV select").prop("disabled",true);
			$("#topSiteDIV input").prop("disabled",true);

			$("#lessorChForm select").prop("disabled",true);
			$("#lessorChForm input").prop("disabled",true);
			
			$('#btn_1M3Save_Draft').prop("disabled",true);
			$('#btn_1M3Print').prop("disabled",true);
			$('#btn_1M3PrintRent').prop("disabled",true);
			$("#btn_1M3Save_Draft").prop("disabled", true);
		}
	}
 
</script>