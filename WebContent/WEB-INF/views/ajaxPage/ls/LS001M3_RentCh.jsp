<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>

<!-- 站點資訊 START -->
<form id="editRentForm">
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
				</div>
			</td>
			<td nowrap="nowrap"><label class="col-sm-12 control-label">建立人員
					:</label></td>
			<td COLSPAN=2>
				<div class="col-sm-12">
					<c:out value="${loginUser.deptName}"/>&nbsp;/&nbsp;<c:out value="${loginUser.chName}"/>
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

<!-- 類別、項目 START -->
<div class="form-group" style="margin-top: 10px">
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
		<select id="itemSEL" class="populate placeholder" name="itemSEL" >
			<c:forEach items="${itemSEL}" var="item">
				<option value="${item.key}">${item.value}</option>
			</c:forEach>
		</select>
	</div>

	<div id="printTypeDiv">
		<label class="col-sm-1 control-label">套表格式 :</label>
		<div class="col-sm-3">
			<select id="tbStyleSEL" class="populate placeholder" name="tbStyleSEL">
			</select>
		</div>
	</div>
</div>
<!-- 類別、項目 END -->

<hr>
	<div style="padding: 10px 10px 0px 10px">
		<input type="hidden" id="editLocId" name="editLocId">
		<input type="hidden" id="editAppSeq" name="editAppSeq">
		<input type="hidden" id="editLsNo" name="editLsNo">
		<input type="hidden" id="editAppMode" name="editAppMode">
		<input type="hidden" id="editMainLsVer" name="editMainLsVer">
		<input type="hidden" id="editLocData" name="editLocData">
		<input type="hidden" id="stopMonth" name="stopMonth">
		<table style="width: 100%">
			<tr>
				<td nowrap="nowrap" width="6%"><label
					class="col-sm-12 control-label">租金 :</label></td>
				<td width="20%">
					<p style="padding-top: 12px; padding-left: 10px;" id="finalRent"></p>
				</td>
				<td nowrap="nowrap" width="12%"><label
					class="col-sm-12 control-label">原租金類別 :</label></td>
				<td width="25%">
					<p style="padding-top: 12px; padding-left: 10px;" id="rentChType">管理費</p>
				</td>
				<td nowrap="nowrap" width="12%"><label
					class="col-sm-12 control-label">變更起始日 :</label></td>
				<td width="25%">
					<div class="col-sm-12">
						<input id="changeStrDay" name="changeStrDay" type="text" class="form-control rentChPicker"
							readonly="readonly" placeholder="點選輸入框">
					</div>
				</td>
			</tr>
			<tr id="managementRentDiv">
				<td nowrap="nowrap" width="6%"><label
					class="col-sm-8 control-label">變更租金類別 :</label></td>
				<td COLSPAN=5>
					<div class="col-sm-2">
						<select id="lsRentTypeChgSelect" class="populate placeholder" name="lsRentTypeChgSelect">
						</select>
					</div>
				</td>
			</tr>
		</table>
	</div>
	
	<div id="dropRiseDiv" style="padding: 10px 10px 0px 10px">
		<fieldset class="ftSolidLine">
			<legend class="legend">租金調降/租金調漲</legend>
	
			<table style="width: 100%;margin-bottom: 15px">
				<tr>
					<td nowrap="nowrap" width="6%"><label
						class="col-sm-12 control-label">變更後租金 :</label></td>
					<td width="94%">
						<div style="width: 15%; height: auto; float: left; display: inline">
							<input id="changeRent" name="changeRent" type="text" class="form-control">
						</div>
						<div style="width: 80%; height: auto; float: left; display: inline;padding-left: 2px;padding-top: 5px">
							元</div>
					</td>
				</tr>
			</table>
	
			<table
				class="table table-striped table-bordered table-hover table-heading no-border-bottom"
				style="width: 100%;">
				<thead>
					<tr style="height: 25px">
						<th style="width: 10%;">費用項目</th>
						<th style="width: 10%;">出租人</th>
						<th style="width: 10%;">付款對象</th>
						<th style="width: 10%;">付款方式</th>
						<th style="width: 10%;">銀行</th>
						<th style="width: 10%;">分行</th>
						<th style="width: 15%;">帳號</th>
						<th style="width: 10%;">原金額</th>
						<th style="width: 15%;">變更金額</th>
					</tr>
				</thead>
				<tbody id="paymentTbody">
				</tbody>
			</table>
		</fieldset>
	</div>
	
	<div id="rentCeaseDiv" style="padding: 10px 10px 0px 10px">
		<fieldset class="ftSolidLine">
			<legend class="legend">租金停付</legend>
			<table style="width: 100%">
				<tr>
					<td nowrap="nowrap" width="6%"><label
						class="col-sm-12 control-label">停付原因 :</label></td>
					<td width="20%">
						<div class="col-sm-12">
							<select id="lsStopResnSelect" class="populate placeholder" name="lsStopResnSelect">
							</select>
						</div>
					</td>
					<td nowrap="nowrap" width="30%"></td>
					<td width="44%"></td>
				</tr>
	
				<tr>
					<td nowrap="nowrap" style="padding-top: 10px"><label
						class="col-sm-12 control-label">停付期間 :</label></td>
					<td style="width: 35%">
						<div style="padding-top: 12px; padding-left: 10px" >
							<div class="col-sm-12">
								<div
									style="width: 45%; height: auto; float: left; display: inline; margin-top: 6px">
									<input id="stopBDate" name="stopBDate" type="text" title="合約起日"
										class="form-control rentChPicker" readonly="readonly" placeholder="點選輸入框" onchange="stopBDateFunction()">
								</div>
								<div
									style="width: 10%; height: auto; float: left; display: inline;; text-align: center; margin-top: 8px">
									至</div>
		
								<div
									style="width: 45%; height: auto; float: left; display: inline; margin-top: 6px">
									<input id="stopEDate" name="stopEDate" type="text" title="合約訖日" 
										class="form-control rentChPicker" readonly="readonly" placeholder="點選輸入框" onchange="stopEDateFunction()">
								</div>
								
							</div>
						</div>
					</td>
					<td >
						<div  style="padding-top: 12px; padding-left: 10px" >
						  <div
									style="width: 20%; height: auto; float: left; display: inline;; text-align: center; margin-top: 8px">
									<label id="totalMonth">共計　月</label></div>
						 <!--  <label id="totalMonth"></label> -->
						</div>
					</td>
				</tr>
			</table>
		</fieldset>
	</div>
</form>

<script type="text/javascript">
	$(document).ready(function() {	
	
		var appMode =  $("#appMode").text();
		$('#editAppSeq').val($("#appSeq").text());
		$('#editLsNo').val($("#lsNo").text());
		$('#editAppMode').val($("#appMode").text());
		$('#editMainLsVer').val($("#lsVer").text());
		$('#editElecLocId').val($("#appSeq").text());
		$('#rentChType').text(($("#rentType").text() == 0 ? "租金":"管理費"));
		
		
		var initData = {
				lsNo : $("#lsNo").text(),
				lsVer : $("#lsVer").text()
			};
		$.ajax({			
			url : CONTEXT_URL + "/ls/LS001M3_ElecCh/searchDomain",		
			data: initData,
			type : "POST",
			dataType : 'json',
			success : function(data) {
				if(data.success){	
					for(var i = 0 ;i<data.rows.length ; i++){
						if(i == 0 ){
							$('#selectDomain').append('<option value="'+data.rows[i].id+'" selected>'+data.rows[i].name+'</option>');
						}else{
							$('#selectDomain').append('<option value="'+data.rows[i].id+'">'+data.rows[i].name+'</option>');
						}
					}
					$('#selectDomain').trigger('change');
				}
			}
		}); 
		
		//TB_SYS_LOOKUP Str
		var chgData = {
					type : 'LS_RENT_TYPE_CHG'
				};
	 		
			$.ajax({			
				url : CONTEXT_URL + "/ls/LS001M3/searchTbSysLookup",		
				data: chgData,
				type : "POST",
				dataType : 'json',
				success : function(data) {
					if(data.success){	
						for(var i = 0 ;i<data.rows.length ; i++){
							if(i == 0 ){
								$('#lsRentTypeChgSelect').append('<option value="'+data.rows[i].code+'" selected>'+data.rows[i].name+'</option>');
							}else{
								$('#lsRentTypeChgSelect').append('<option value="'+data.rows[i].code+'">'+data.rows[i].name+'</option>');
							}
						}
						$('#lsRentTypeChgSelect').trigger('change');
					}
				}
			});
			
			var resnData = {
					type : 'LS_STOP_RESN'
				};
	 		
			$.ajax({			
				url : CONTEXT_URL + "/ls/LS001M3/searchTbSysLookup",		
				data: resnData,
				type : "POST",
				dataType : 'json',
				success : function(data) {
					if(data.success){	
						for(var i = 0 ;i<data.rows.length ; i++){
							if(i == 0 ){
								$('#lsStopResnSelect').append('<option value="'+data.rows[i].code+'" selected>'+data.rows[i].name+'</option>');
							}else{
								$('#lsStopResnSelect').append('<option value="'+data.rows[i].code+'">'+data.rows[i].name+'</option>');
							}
						}
						$('#lsStopResnSelect').trigger('change');
					}
				}
			});
		//TB_SYS_LOOKUP End
		
		$('#siteTbody').on( 'click', 'tr', function () {
			var flag = false;
			$('#siteTbody').find('tr').each(function(){
				if($(this).hasClass('selected')){
					flag = true;	
				}
			}); 
			if(flag){
				if(!confirm("是否不儲存此修改資料？")){
					return false;
				}
			}
			
			$('#editLocId').val($.trim($(this).children().first().text()));
			$('#editLocData').val($(this).find('input[name="locData"]').val());
			var splits = $('#editLocData').val().split(",");
			$('#siteTbody').find('tr').each(function(){
				$(this).removeClass('selected');
			}); 
			$(this).addClass('selected');
			CleanerUI();
			 var data = {
					lsNo : $("#lsNo").text(),
					locId : $.trim($(this).children().first().text()),
					appMode : $('#editAppMode').val(),
					appSeq : $('#editAppSeq').val(),
					mainLsVer : $('#editMainLsVer').val(),
					paymentItem : "R"
				};
			$.ajax({			
				url : CONTEXT_URL + "/ls/LS001M3_RentCh/searchTbLsLocPaymen",		
				data: data,
				type : "POST",
				dataType : 'json',
				success : function(data) {
					$('#finalRent').text(splits[5]);
					$('#paymentTbody tr').remove();
					if($('#editAppMode').val() == 'NEW'){
						paymentDate(data.row.tbLsLocPaymentDTOList,data.row.tbLsLocPaymentDTOList,true);
					}else{
						if(data.row.oldTbLsLocPaymentAddedList != '' && data.row.oldTbLsLocPaymentAddedList != null){
							paymentDate(data.row.oldTbLsLocPaymentAddedList,data.row.newTbLsLocPaymentAddedList,false);
						}else{
							paymentDate(data.row.tbLsLocPaymentDTOList,data.row.tbLsLocPaymentDTOList,true);
						}
						if(data.row.tbLsLocationAdded != null){
							$('#changeRent').val(data.row.tbLsLocationAdded.rent_AMT);
							$('#changeStrDay').val(dateFormat(data.row.tbLsLocationAdded.chg_DATE));
						}
						if(data.row.tbLsMainAdded != null){
							$('#changeStrDay').val(dateFormat(data.row.tbLsMainAdded.rent_CHG_DATE));
							$('#lsRentTypeChgSelect').val(data.row.tbLsMainAdded.rent_TYPE_CHG);
							$('#lsStopResnSelect').val(data.row.tbLsMainAdded.stop_RESN);
							$('#stopBDate').val(dateFormat(data.row.tbLsMainAdded.stop_B_DATE));
							$('#stopEDate').val(dateFormat(data.row.tbLsMainAdded.stop_E_DATE));
							$('#totalMonth').text('共'+data.row.tbLsMainAdded.stop_MONTH+'個月');
							$('#stopMonth').val(data.row.tbLsMainAdded.stop_MONTH);
						}
						$('#lsRentTypeChgSelect').trigger('change');
						$('#lsStopResnSelect').trigger('change');
					}
					
				}
			});	  
			
		});
		
		$('#changeRent').change(function(){
			if($('#finalRent').text() == ''){
				alert("請點選一筆站點資訊！");
				$(this).val('');
				return false;
			}
			if(checkIntOnBlur(this)){
				if($('#itemSEL').val() == "租金調降"){
					if(!(parseInt($(this).val()) < parseInt($('#finalRent').text()))){
						alert("變更後租金應比原租金低");
						$(this).val('');
					}
				}else if($('#itemSEL').val() == "租金調漲"){
					if(!(parseInt($(this).val()) > parseInt($('#finalRent').text()))){
						alert("變更後租金應比原租金高");
						$(this).val('');
					}
				}
			}
		});
		
		$('#changeStrDay').change(function(){
			if(dateInterval($(this).val())){
			}else{
				$(this).val('');
				alert("變更起始日必需在合約起迄之間！");
			}
		});
		
		$('#selectDomain').change(function(){
			data = {
					lsNo : $("#lsNo").text(),
					domain : $(this).val()
				};
	 		
			$.ajax({			
				url : CONTEXT_URL + "/ls/LS001M3_ElecCh/searchTbLsLocation",		
				data: data,
				type : "POST",
				dataType : 'json',
				success : function(data) {
					$('#siteTbody tr').remove();
					if(data.success){	
						var siteValue = '';
						for(var i = 0 ;i<data.rows.length ; i++){
							siteValue += '<tr>';
							siteValue += '<td class="tbodytd">'+data.rows[i].location_ID+'</td>';
							siteValue += '<td class="tbodytd">'+data.rows[i].loc_NAME+'</td>';
							siteValue += '<td class="tbodytd">'+data.rows[i].addr+'</td>';
							siteValue += '<td class="tbodytd"><input type="hidden"  name="locData" value="'+data.rows[i].ls_VER+','+data.rows[i].zip_CODE+','+data.rows[i].domain+','+dateFormat(data.rows[i].ls_E_DATE)+','+dateFormat(data.rows[i].eff_DATE)+','+data.rows[i].rent_AMT+'"><button class="btn-primary" type="button" id="" onclick="downloadPicFile('+data.rows[i].zip_CODE+')">檢視</button></td>';
							siteValue += '</tr>';
						}
						$('#siteTbody').append(siteValue);
					}
				}
			});
			if($('#editAppMode').val() != "NEW"){
				dataMain = {
						lsNo : $("#editLsNo").val(),
						mainLsVer : $("#editMainLsVer").val(),
						appSeq : $("#editAppSeq").val()
					};
		 		
				$.ajax({			
					url : CONTEXT_URL + "/ls/LS001M3_RentCh/searchTbLsMain",		
					data: dataMain,
					type : "POST",
					dataType : 'json',
					success : function(data) {
						if(data.success){	
							$('#changeStrDay').val(dateFormat(data.row.rent_CHG_DATE));
							$('#lsRentTypeChgSelect').val(data.row.rent_TYPE_CHG);
							$('#lsStopResnSelect').val(data.row.stop_RESN);
							$('#stopBDate').val(dateFormat(data.row.stop_B_DATE));
							$('#stopEDate').val(dateFormat(data.row.stop_E_DATE));
							$('#totalMonth').text('共'+data.row.stop_MONTH+'個月');
							$('#stopMonth').val(data.row.stop_MONTH);
							$('#lsRentTypeChgSelect').trigger('change');
							$('#lsStopResnSelect').trigger('change');
						}
					}
				});
			}
		});
		
		
		$('#itemSEL').change(function(){
			var data = {
					AddItem : $(this).val()
				};
			$.ajax({			
				url : CONTEXT_URL + "/ls/LS001M3_ElecCh/searchLsAppFORM",		
				data: data,
				type : "POST",
				dataType : 'json',
				success : function(data) {
					$('#tbStyleSEL option').remove();
					$('#tbStyleSEL').val('');
					for(var i = 0 ; i<data.rows.length;i++){
						if(i == 0){
							$('#tbStyleSEL').append('<option value="'+data.rows[i].form_ID+'" selected>'+data.rows[i].form_NAME+'</option>');
						}else{
							$('#tbStyleSEL').append('<option value="'+data.rows[i].form_ID+'">'+data.rows[i].form_NAME+'</option>');	
						}
					}
					$('#tbStyleSEL').trigger('change');
				}
			});
			
			if($(this).val() == "租金停付"){
				$('#rentCeaseDiv').show();
				$('#managementRentDiv').hide();
				$('#dropRiseDiv').hide();
			 }else if($(this).val() == "管理費變更租金"){
				 $('#rentCeaseDiv').hide();
					$('#managementRentDiv').show();
					$('#dropRiseDiv').hide();
			 }else{
				 $('#rentCeaseDiv').hide();
					$('#managementRentDiv').hide();
					$('#dropRiseDiv').show();
			 }
		});
		
		//類別
		$('#selType').val('${selType}');
		selText=$("#selType").find("option:selected").text();
		//站點資訊
		siteTBFormat();
		
		//變更起始日
		$('.rentChPicker').datepicker();
		
		//按鈕呈現
		$('#buttonDiv').html("");
		var str = '<c:forEach items="${button}" var="data"><button class="btn btn-primary btn-label" type="button" id="${data.key}" style="margin-right: 5px" >${data.value}</button></c:forEach>';
		$('#buttonDiv').append(str);
		
		$('#itemSEL').trigger('change');
		
		//儲存草稿  Str
		$('#btn_1M3Save_Draft').click(function(){
			 if($('#changeStrDay').val() == '' || $('#changeStrDay').val() == null){
				 alert('變更起始日請填入資料');
				 return false;
			 }
			 if($('#itemSEL').val() == "租金停付"){
				 var r = /^\d+$/;
				 if($('#stopBDate').val() != '' && $('#stopEDate').val() != '' && r.test($('#stopMonth').val())){
					 saveDraft();
				 }else{
					 alert('停付期間需以整個月為單位');
				 }  
			 }else if($('#itemSEL').val() == "租金調降" || $('#itemSEL').val() == "租金調漲"){
					$('#siteTbody').find('tr').each(function(){
						if($(this).hasClass('selected')){
							flag = false;	
						}
					}); 
					if(flag){
						alert("請點選一筆站點資訊！");
						return false;
					}
				 var flag = true;
				 var cont = 0;
				 $('#paymentTbody tr').find('input[name="newPayAmt"]').each(function(){
					 cont = cont +(new Number($(this).val()));
				 });
			 	 if(cont == $('#changeRent').val()){
			 		saveDraft();
				 }else{
					 alert("變更後租金 與 變更租金總合不相等！");
				 } 
			 }else if($('#itemSEL').val() == "管理費變更租金"){
				if($('#lsRentTypeChgSelect').val() != ''){
					saveDraft();
				}else{
					alert('變更起始日 與 變更租金類別 請填入資料');
				}				 
			 }else{
			 }
		});
		//儲存草稿  End	
		
		initState(appMode);
		
		//控制畫面是否開啟
		initStates(appMode);
	});
	
	

	
	function paymentDate(oldData,newData,flag){
		var paymentDateValue = '';
		for(var i = 0 ; i<oldData.length;i++){
			paymentDateValue += '<tr style="height: 25px">';
			paymentDateValue += '<td>租金</td>';
			paymentDateValue += '<td>'+(oldData[i].lessor_NAME != null ? oldData[i].lessor_NAME:"")+'</td>';
			paymentDateValue += '<td>'+(oldData[i].recipient_NAME != null ? oldData[i].recipient_NAME:"")+'</td>';
			paymentDateValue += '<td>'+(oldData[i].paymentModeName != null ? oldData[i].paymentModeName:"")+'</td>';
			paymentDateValue += '<td>'+(oldData[i].unit_NAME != null ? oldData[i].unit_NAME:"")+'</td>';
			paymentDateValue += '<td>'+(oldData[i].nick_NAME != null ? oldData[i].nick_NAME:"")+'</td>';
			paymentDateValue += '<td>'+(oldData[i].account_NO != null ? oldData[i].account_NO:"")+'</td>';
			paymentDateValue += '<td>'+(oldData[i].pay_AMT != null ? oldData[i].pay_AMT:"")+'</td>';
			if(flag){
				paymentDateValue += '<td style="padding: 3px 5px 3px 5px"><input id="newPayAmt" name="newPayAmt" type="text" class="form-control" style="width: 100%;" onblur="checkIntOnBlur(this);" value="" />';
			}else{
				paymentDateValue += '<td style="padding: 3px 5px 3px 5px"><input id="newPayAmt" name="newPayAmt" type="text" class="form-control" style="width: 100%;" onblur="checkIntOnBlur(this);" value="'+newData[i].pay_AMT+'" />';
			}
			paymentDateValue += '<input type="hidden" name="oldPayData" value="'+oldData[i].location_ID+','+oldData[i].lessor_NAME+','+oldData[i].recipient_NAME+','+oldData[i].payment_MODE+','+oldData[i].unit_CODE+','+oldData[i].sub_UNIT_CODE+','+oldData[i].account_NO+','+oldData[i].ls_VER+','+oldData[i].pay_AMT+'">';
			paymentDateValue += '</td></tr>';
		}
		$('#paymentTbody').append(paymentDateValue);
	}
	
	
	function saveDraft(){
		$("#selType").prop("disabled", false);
		$("#itemSEL").prop("disabled", false);
		var data = $("#editRentForm").serializeArray();
 		$.ajax({			
			url : CONTEXT_URL + "/ls/LS001M3_RentCh/saveRentCh",		
			data: data,
			type : "POST",
			dataType : 'json',
			success : function(data) {
				alert(data.msg);
				if(data.success){
					parent.$.fancybox.close();
				}
				if($('#editAppMode').val() != 'NEW'){
					$("#selType").prop("disabled", true);
					$("#selType").prop("disabled", true);
				}
			}
		}); 
	}
	
	function stopBDateFunction(){
		console.log('B');
		var flagB = false;
		if($('#stopBDate').val() != ''){
			flagB = dateInterval($('#stopBDate').val());
		}
		if(flagB){
			getMonthTotal();
		}else{
			$('#stopBDate').val('');
			alert("停付期間起 需在合約起迄之間");
		}
	}
	
	function stopEDateFunction(){
		console.log('E');
		var flagE = false;
		if($('#stopEDate').val() != ''){
			flagE = dateInterval($('#stopEDate').val());
		}
		if(flagE){
			getMonthTotal();
		}else{
			$('#stopEDate').val('');
			alert("停付期間迄 需在合約起迄之間");
		}
	}
	
	function getMonthTotal(){
			if($('#stopBDate').val() != '' && $('#stopEDate').val() != ''){
				$('#stopMonth').val(compareDate($('#stopBDate').val(),$('#stopEDate').val()));
				$('#totalMonth').text('共計 '+$('#stopMonth').val()+' 月');
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
	
	//年月總計
	function compareDate(conDateS,conDateE){
		var SDate = new Date(conDateS);
		var EDate = new Date(conDateE);
		
		var SDays = new Date(SDate.getFullYear(),SDate.getMonth() + 1,0).getDate();	//起日當月總天數
		var EDays = new Date(EDate.getFullYear(),EDate.getMonth() + 1,0).getDate();	//迄日當月總天數
		
		//IF　起訖日同月 總月數 = (迄日 - 起日 + 1) / 當月總天數
		if (SDate.getFullYear() == EDate.getFullYear() && SDate.getMonth() == EDate.getMonth()) {
			var diffDays = EDate.getDate() - SDate.getDate() + 1;
			var months = diffDays / SDays;
			
			return formatFloat(months, 1);
		} else {
			//ELSE 總月數 =
			// 	   (起日當月最後一天日期 - 起日  + 1) / 起日當月總天數 +
			var SMonth = (SDays - SDate.getDate() + 1) / SDays;
			//     (迄年月 - 起年月 -1) + 
			var diffMonth = (EDate.getFullYear() - SDate.getFullYear()) * 12 + 
							(EDate.getMonth() - SDate.getMonth()) - 1;
			//	   (迄日 - 迄日當月第一天日期  + 1) / 迄日當月總天數 
			var EMonth = EDate.getDate() / EDays;
			//年月小記 = 總月數 / 12 (四捨五入到小數1位)
			var totalMonths = SMonth + diffMonth + EMonth;
			return formatFloat(totalMonths, 1);
		}

    }
	
	//四捨五入 pos:進位的小數位
	function formatFloat(num, pos)
	{
	  var size = Math.pow(10, pos);
	  return Math.round(num * size) / size;
	}
	
	function dateFormat(date){
		if(date != null && date != ''){
		var day = new Date(date);
		return day.getFullYear()+"/"+(day.getMonth()+1)+"/"+day.getDate();
		}else{
			return '';
		}
	}
	
	
	function CleanerUI(){
		$('#stopEDate').val('');
		$('#stopBDate').val('');
		$('#totalMonth').text(''); 
		$('#changeRent').val('');
	}
		//控制畫面是否開啟
	 function initStates(appMode){
			if(appMode=='VIEW'){
				$("#editRentForm select").prop("disabled",true);
				$("#editRentForm input").prop("disabled",true);
				$('#btn_1M3Save_Draft').prop("disabled",true);
				$('#btn_1M3Print').prop("disabled",true);
				$('#btn_1M3PrintRent').prop("disabled",true);
				$("#btn_1M3Save_Draft").prop("disabled", true);
			}
		}
</script>