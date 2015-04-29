<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>

<!-- 站點資訊 START -->
<form id="editElecForm">
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
					<div class="col-sm-12" style="margin-top: 6px">
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
			<select id="selType" class="populate placeholder" name="editSelType"
				onchange="getItem(this.value);">
				<c:forEach items="${lsAddTypeEnum}" var="item">
				<option value="${item.value}">${item.label}</option>
				</c:forEach>
			</select>
		</div>
		<label class="col-sm-1 control-label">項目 :</label>
		<div class="col-sm-3">
			<select id="itemSEL" class="populate placeholder" name="editItemSEL" >
				<c:forEach items="${itemSEL}" var="item">
					<option value="${item.key}">${item.value}</option>
				</c:forEach>
			</select>
		</div>
	
		<div id="printTypeDiv">
			<label class="col-sm-1 control-label">套表格式 :</label>
			<div class="col-sm-3">
				<select id="tbStyleSEL" class="populate placeholder" name="editTbStyleSEL">
				</select>
			</div>
		</div>
	</div>
	<!-- 類別、項目 END -->
	
	<hr>
	<div style="padding: 10px 10px 0px 10px" id="electricityDiv">
		<label class="control-label" style="margin-bottom: 10px">原用電</label>
		<input type="hidden" id="editElecLocId" name="editElecLocId">
		<input type="hidden" id="editAppSql" name="editAppSql">
		<input type="hidden" id="editLsNo" name="editLsNo">
		<input type="hidden" id="editAppMode" name="editAppMode">
		<input type="hidden" id="oldAmmeterNo" >
		<table
			class="table table-striped table-bordered table-hover table-heading no-border-bottom"
			style="width: 100%;">
			<thead>
				<tr style="height: 25px">
					<th style="width: 20%;">用電方式</th>
					<th style="width: 15%;">電錶號碼</th>
					<th style="width: 10%;">計費方式</th>
					<th style="width: 20%;">用電期間</th>
					<th style="width: 35%;">用電地址</th>
				</tr>
			</thead>
			<tbody id="electricityTbody">
			</tbody>
		</table>
	</div>
	
	<div style="padding: 10px 10px 0px 10px" id="editElectricityDiv">
		<label class="control-label" style="margin-bottom: 10px">變更用電</label>
		<table
			class="table table-striped table-bordered table-hover table-heading no-border-bottom"
			style="width: 100%;">
			<thead>
				<tr style="height: 25px">
					<th style="width: 15%;">用電方式</th>
					<th style="width: 10%;">電錶號碼</th>
					<th style="width: 15%;">計費方式</th>
					<th style="width: 15%;">期初度數</th>
					<th style="width: 20%;">變更起始日</th>
					<th style="width: 10%;">終止日</th>
					<th style="width: 15%;">原用電期末度數</th>
					
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>
						<select id="editElecMode" class="populate placeholder" >
						</select>
					</td>
					<td><input id="editAmmeterNo" disabled="disabled"></td>
					<td><input id="editBillingMode" type="text" size="2"
						style="margin: 2px 5px 2px 0px" />
					<td><input id="editInitDegree"></td>
					<td style="padding: 3px 5px 3px 5px"><input id="editChangeStrDate"
						class="form-control elecChPicker" type="text" style="width: 100%;"
						readonly="readonly" placeholder="點選輸入框"></td>
					<td><p id="editEndDate"></p></td>
					<td><input class="form-control" type="text" style="width: 100%;" id="editElecEndDegree"><input type="hidden" id="editRow"><input type="hidden" id="editElecAddr"></td>
				</tr>
			</tbody>
		</table>
	</div>
	
	<div style="padding: 10px 10px 0px 10px" id="paymentDateDiv">
		<fieldset class="ftSolidLine">
			<legend class="legend">付款資訊</legend>
			<table
				class="table table-striped table-bordered table-hover table-heading no-border-bottom"
				style="width: 100%;">
				<thead>
					<tr style="height: 25px">
						<th style="width: 10%;">站台編號</th>
						<th style="width: 10%;">出租人</th>
						<th style="width: 10%;">付款對象證號</th>
						<th style="width: 10%;">名稱</th>
						<th style="width: 9%;">與出租人關係</th>
						<th style="width: 7%;">付款方式</th>
						<th style="width: 7%;">銀行</th>
						<th style="width: 7%;">分行</th>
						<th style="width: 10%;">帳號</th>
						<th style="width: 10%;">分攤比率</th>
						<th style="width: 10%;">金額</th>
					</tr>
				</thead>
				<tbody id="paymentDateTbody">
				</tbody>
			</table>
		</fieldset>
	</div>
</form>
<script type="text/javascript">
	$(document).ready(function() {
		
		var appMode =  $("#appMode").text();
		$('#editAppMode').val($("#appMode").text());
		$('#editAppSql').val($("#appSeq").text());
		$('#editLsNo').val($("#lsNo").text());
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
					if($('#editAppMode').val() != 'NEW'){
						var data = {
								appSeq : $('#editAppSql').val(),
								lsNo : $('#editLsNo').val()
							};
						$.ajax({			
							url : CONTEXT_URL + "/ls/LS001M3_ElecCh/initEditElecCh",		
							data: data,
							type : "POST",
							dataType : 'json',
							success : function(data) {
								$('#selectDomain').val(data.row.domain);
								$('#editElecLocId').val(data.row.location_ID);
								$('#selectDomain').trigger('change');
							}
						});
					}else{
					$('#selectDomain').trigger('change');
					}
				}
			}
		}); 
		
	$('#selectDomain').change(function(){
		 initData = {
					lsNo : $('#editLsNo').val(),
					domain : $(this).val()
				};
	 		
			$.ajax({			
				url : CONTEXT_URL + "/ls/LS001M3_ElecCh/searchTbLsLocation",		
				data: initData,
				type : "POST",
				dataType : 'json',
				success : function(data) {
					$('#siteTbody tr').remove();
					if(data.success){	
						var siteValue = '';
						if(data.rows != null){
							for(var i = 0 ;i<data.rows.length ; i++){
								siteValue += '<tr>';
								siteValue += '<td class="tbodytd">'+data.rows[i].location_ID+'</td>';
								siteValue += '<td class="tbodytd">'+data.rows[i].loc_NAME+'</td>';
								siteValue += '<td class="tbodytd">'+data.rows[i].addr+'</td>';
								siteValue += '<td class="tbodytd"><button class="btn-primary" type="button" id="" onclick="downloadPicFile('+data.rows[i].zip_CODE+')">檢視</button></td>';
								siteValue += '</tr>';
							}
						}
						$('#siteTbody').append(siteValue);
						if($('#editAppMode').val() != 'NEW'){
							$('#siteTbody tr').each(function(){
								if($(this).children().first().text() == $('#editElecLocId').val()){
									$(this).trigger('click');
								}
							});
						}
					}
				}
			}); 
	});
	
	$('#siteTbody').on( 'click', 'tr', function () {
		var flag = false;
		if($(this).hasClass('selected')){
		}else{
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
		var data = {
				lsNo : $("#lsNo").text(),
				locId : $.trim($(this).children().first().text()),
				appSql : $("#editAppSql").val(),
				appMode : $("#editAppMode").val(),
				item : $("#itemSEL").val()
			};
			$('#electricityDiv').show();
			$('#editElectricityDiv').hide();
			if($('#itemSEL').val() == '電表歸零'){
				$('#paymentDateDiv').hide();
			}else{
				$('#paymentDateDiv').show();
			}
			$('#editRow').val('');
			$('#editElecAddr').val('');
			$('#editElecLocId').val($.trim($(this).children().first().text()));
			$('#siteTbody').find('tr').each(function(){
				$(this).removeClass('selected');
			}); 
			$(this).addClass('selected');
			$.ajax({			
				url : CONTEXT_URL + "/ls/LS001M3_ElecCh/searchTbLsLocElecTbLsLocPaymen",		
				data: data,
				type : "POST",
				dataType : 'json',
				success : function(data) {
				$('#electricityTbody tr').remove();
					var electricityValue = '';
					if(data.row.oldTbLsLocElecAddedList != null){
						electricityOldNewDate(data.row.oldTbLsLocElecAddedList,data.row.newTbLsLocElecAddedList);
					}else{
						electricityOldNewDate(data.row.tbLsLocElecDTOList,data.row.tbLsLocElecDTOList);
					}
					if(data.row.oldTbLsLocPaymentAddedList!= null){
						paymentOldNewDate(data.row.oldTbLsLocPaymentAddedList,data.row.newTbLsLocPaymentAddedList);
					}else{
						paymentOldNewDate(data.row.tbLsLocPaymentDTOList,data.row.tbLsLocPaymentDTOList);
					}
					if($('#editAppMode').val() != "NEW"){
						$('#electricityTbody tr').each(function(){
							var newSplits  = $(this).find('input[name="editNewElec"]').val().split(",");
							if(newSplits[8] != ''){
								$(this)	.trigger('click');
							}
						});
					}
				}
			});	 
		}	
		
		
	});
	
	$('#electricityTbody').on( 'click', 'tr', function () {
			if($(this).hasClass('zona')){
			}else{
				if($('#editRow').val() != ''){
					if(!confirm("是否不儲存此修改資料？")){
						return false;
					}else{
						$('#electricityTbody').children().eq($('#editRow').val()).find('input[name="editFlag"]').val(false);
					}
				}
				$('#electricityTbody').find('tr').each(function(){
					$(this).removeClass('zona');
				}); 
				$(this).addClass('zona');
				console.log("OP1 Str");
				$('#editElectricityDiv').show();
				console.log("OP1 End");
				var newSplits  = $(this).find('input[name="editNewElec"]').val().split(",");
				var oldSplits  = $(this).find('input[name="editOldElec"]').val().split(",");
				 	$('#oldAmmeterNo').val(oldSplits[0]);
				 	if($('#itemSEL').val() == '電表歸零'){
				 		$('#editElecMode').val(newSplits[0]);
					}else{
						if(newSplits[8] != ''){
							$('#editElecMode').val(newSplits[0]);
						}else{
							$('#editElecMode').val('');
						}
						
					}
				 	if(newSplits[1] != 'null' && newSplits[1] != ''){
					$('#editAmmeterNo').val(newSplits[1]);
				 	}
					if(newSplits[2] != 'null' && newSplits[2] != ''){
						$('#editBillingMode').val(newSplits[2]);
					}else{
						$('#editBillingMode').val('');	
					}
					if(newSplits[3] != 'null' && newSplits[3] != ''){
						$('#editInitDegree').val(newSplits[3]);
					}else{
						$('#editInitDegree').val('');
					}
					if(newSplits[4] != 'null' && newSplits[4] != ''){
					$('#editChangeStrDate').val(newSplits[4]);
					}
					if(newSplits[5] != 'null' && newSplits[5] != ''){
					$('#editEndDate').text(newSplits[5]);
					}
					if(newSplits[6] != 'null' && newSplits[6] != ''){
					$('#editRow').val(newSplits[6]);
					}
					if(newSplits[7] != 'null' && newSplits[7] != ''){
					$('#editElecAddr').val(newSplits[7]);
					}
					if($(this).find('input[name="newElecEndDegree"]').val() != ''){
						$('#editElecEndDegree').val($(this).find('input[name="newElecEndDegree"]').val());
					}
					$('#editElecMode').trigger('change');
					disabledFlag();
			}
			
			
	});	
	
	$('#itemSEL').change(function(){
		var qdata = {
				lsNo : $("#lsNo").text(),
				locId : $("#editElecLocId").val(),
				appSql : $("#editAppSql").val(),
				appMode : $("#editAppMode").val(),
				item : $("#itemSEL").val()
			};
		
			$.ajax({			
				url : CONTEXT_URL + "/ls/LS001M3_ElecCh/searchTbLsLocElecTbLsLocPaymen",		
				data: qdata,
				type : "POST",
				dataType : 'json',
				success : function(data) {
				$('#electricityTbody tr').remove();
					var electricityValue = '';
					if(data.row.oldTbLsLocElecAddedList != null){
						electricityOldNewDate(data.row.oldTbLsLocElecAddedList,data.row.newTbLsLocElecAddedList);
					}else{
						electricityOldNewDate(data.row.tbLsLocElecDTOList,data.row.tbLsLocElecDTOList);
					}
					if(data.row.oldTbLsLocPaymentAddedList != null){
						paymentOldNewDate(data.row.oldTbLsLocPaymentAddedList,data.row.newTbLsLocPaymentAddedList);
					}else{
						paymentOldNewDate(data.row.tbLsLocPaymentDTOList,data.row.tbLsLocPaymentDTOList);
					}
				}
			});	 
		
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
				if($('#itemSEL').val() == '電表歸零'){
					$('#electricityTbody').find('tr').each(function(){
						$(this).removeClass('zona');
					});
					$('#editRow').val('');
					$('#tbStyleSEL').select2("val","");
					$('#paymentDateDiv').hide();
					$('#editElectricityDiv').hide();
					
				}else{
					$('#paymentDateDiv').show();
					$('#electricityDiv').show();
					$('#editElectricityDiv').hide();
					$('#electricityTbody').find('tr').each(function(){
						$(this).removeClass('zona');
					});
					$('#editRow').val('');
				}
				$('#tbStyleSEL').trigger('change');
			}
		});
		
		var resnData = {
				type : 'LS_ELEC_MODE',
				item : $('#itemSEL').val()
			};
 		
		$.ajax({			
			url : CONTEXT_URL + "/ls/LS001M3/searchTbSysLookup",		
			data: resnData,
			type : "POST",
			dataType : 'json',
			success : function(data) {
				$('#editElecMode option').remove();
				$('#editElecMode').append('<option value="" selected>請選擇…</option>');
					for(var i = 0 ;i<data.rows.length ; i++){
							$('#editElecMode').append('<option value="'+data.rows[i].code+'">'+data.rows[i].name+'</option>');
					}
					$('#editElecMode').trigger('change');
			}
		});
	});
	
	//==============用電變更 Str=================
	$('#editElecMode').change(function(){
		newElec();
		disabledFlag();
	});
	
	$('#editAmmeterNo').change(function(){
		newElec();
	});
	
	$('#editBillingMode').change(function(){
		checkIntOnBlur(this);
			newElec();
	});
	
	$('#editInitDegree').change(function(){
		checkIntOnBlur(this);
		newElec();
	});
	
	$('#editChangeStrDate').change(function(){
		var splits  = $('#lsDate').text().split("~");
		if(Date.parse(splits[1]).valueOf() > Date.parse($(this).val()).valueOf() && Date.parse(splits[0]).valueOf() < Date.parse($(this).val()).valueOf()){
			newElec();
		}else{
			$(this).val('');
			alert("變更起始日必需在合約起迄之間！");
		}
		
	});
	
	$('#editElecEndDegree').change(function(){
		checkIntOnBlur(this);
		newElec();
	});
	//==============用電變更 End=================
		
		
		//類別
		$('#selType').val('${selType}');
		selText=$("#selType").find("option:selected").text();
		//站點資訊
		siteTBFormat();
		
		//變更起始日
		$('.elecChPicker').datepicker();
		
		$('#buttonDiv').html("");
		var str = '<c:forEach items="${button}" var="data"><button class="btn btn-primary btn-label" type="button" id="${data.key}" style="margin-right: 5px" >${data.value}</button></c:forEach>';
		$('#buttonDiv').append(str);

		$('#itemSEL').trigger('change');
		console.log('原ED1 Str');
		$('#electricityDiv').hide();
		console.log('原ED1 End');
		console.log('ED1 Str');
		$('#editElectricityDiv').hide();
		console.log('ED1 End');
		$('#paymentDateDiv').hide();
		
		initState(appMode);
		
		//控制畫面是否唯讀
		initStates(appMode);
		
		//儲存草稿
		$('#btn_1M3Save_Draft').click(function(){
			var flag = true;
			$('#siteTbody').find('tr').each(function(){
				if($(this).hasClass('selected')){
					flag = false;	
				}
			}); 
			if(flag){
				alert("請點選一筆站點資訊！");
				return false;
			}
			flag = true;
			$('#electricityTbody').find('tr').each(function(){
				if($(this).hasClass('zona')){
					flag = false;
				}
			});
			if(flag){
				alert("請點選一筆用電資訊！");
				return false;
			}
			
			var flagAloc = false;
			var flagAmt = false;
			var msg ='';
			$('#electricityTbody tr').each(function(){
				if($(this).find('input[name="editFlag"]').val() == 'true'){
					$(this).find('input[name="editNewElec"]').val($(this).find('input[name="editFlagData"]').val());
					var newSplits  = $(this).find('input[name="editNewElec"]').val().split(",");
					var oldSplits  = $(this).find('input[name="editOldElec"]').val().split(",");
					var newElecEndDegree  = $(this).find('input[name="newElecEndDegree"]').val();
					var msgs = qequiredConfirm(newSplits,oldSplits,newElecEndDegree);
					if(msgs.length > 0){
						msg += 	msgs+"\n";
					}
				}else{
					$(this).find('input[name="editNewElec"]').val($(this).find('input[name="editOldElec"]').val());
					$(this).find('input[name="newElecEndDegree"]').val($(this).find('input[name="oldElecEndDegree"]').val());
				}
			});
			$('#paymentDateTbody tr input[name="editPayAloc"]').each(function(){
				if($(this).val() == ''){
					flagAloc = true;
				}
			});
			if(flagAloc){
				if(msg.length > 0){
					msg += '\n付款資訊-分攤比率 　';
				}else{
					msg += '付款資訊-分攤比率 　';
				}
			}
			$('#paymentDateTbody tr input[name="editPayAmt"]').each(function(){
				if($(this).val() == ''){
					flagAmt = true;
				}
			});
			if(flagAmt){
				if(msg.length > 0){
					msg += '\n付款資訊-金額 　';
				}else{
					msg += '付款資訊-金額 　';
				}
			}
			
			if(msg.length > 0){
				alert(msg+"\n請填值");
			}else{
				$("#selType").prop("disabled", false);
				$("#itemSEL").prop("disabled", false);
				var data = $("#editElecForm").serializeArray();
			 	$.ajax({			
					url : CONTEXT_URL + "/ls/LS001M3_ElecCh/saveLsApp",		
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
							$("#itemSEL").prop("disabled", true);
						}
							
						disabledFlag();
					}
				}); 
			}
		});
		
	});
	
	function dateFormat(date){
		if(date != null && date != ''){
		var day = new Date(date);
		return day.getFullYear()+"/"+(day.getMonth()+1)+"/"+day.getDate();
		}else{
			return '';
		}
	}
	
	function newElec(){
		$('#electricityTbody').children().eq($('#editRow').val()).find('input[name="editFlagData"]').val($('#editElecMode').val()+','+$('#editAmmeterNo').val()+','+$('#editBillingMode').val()+','+$('#editInitDegree').val()+','+$('#editChangeStrDate').val()+','+$('#editEndDate').text()+','+$('#editElecAddr').val());
		$('#electricityTbody').children().eq($('#editRow').val()).find('input[name="editFlag"]').val(true);
		$('#electricityTbody').children().eq($('#editRow').val()).find('input[name="newElecEndDegree"]').val($('#editElecEndDegree').val());
	}
	
	function electricityOldNewDate(oldData,newData){
		$('#electricityTbody tr').remove();
		var electricityValue = '';
		
		if(oldData != null){
			for(var i = 0 ; i<oldData.length;i++){
				electricityValue += '<tr>';
				electricityValue += '<td>'+(oldData[i].siteName != null ? oldData[i].siteName:"")+'</td>';
				electricityValue += '<td>'+(oldData[i].energy_METER != null ? oldData[i].energy_METER:"")+'</td>';
				electricityValue += '<td>'+(oldData[i].chrg_MODE != null ? oldData[i].chrg_MODE:"")+'</td>';
				electricityValue += '<td>'+dateFormat(oldData[i].elec_BEGIN_DATE)+'~'+dateFormat(oldData[i].elec_END_DATE)+'</td>';
				electricityValue += '<td>'+(oldData[i].elec_ADDR != null ? oldData[i].elec_ADDR:"");
				electricityValue += '<input type="hidden"  name="newElecEndDegree"  value="'+(newData[i].end_DEGREE != null ? newData[i].end_DEGREE :"")+'">';
				electricityValue += '<input type="hidden" name="editNewElec" value="'+newData[i].elec_MODE+','+newData[i].energy_METER+','+newData[i].chrg_MODE+','+newData[i].begin_DEGREE+','+dateFormat(newData[i].elec_BEGIN_DATE)+','+dateFormat(newData[i].elec_END_DATE)+','+i+','+newData[i].elec_ADDR+','+(newData[i].chg_DATE == null ? "":newData[i].chg_DATE)+'">';
				electricityValue += '<input type="hidden" name="editOldElec" value="'+oldData[i].elec_MODE+','+oldData[i].energy_METER+','+oldData[i].chrg_MODE+','+oldData[i].begin_DEGREE+','+dateFormat(oldData[i].elec_BEGIN_DATE)+','+dateFormat(oldData[i].elec_END_DATE)+ ','+ oldData[i].elec_ADDR+'">';
				electricityValue += '<input type="hidden" name="editOldVer" value="'+oldData[i].ls_VER+'">';
				electricityValue += '<input type="hidden" name="editFlag" value="false">';
				electricityValue += '<input type="hidden" name="editFlagData" value="">';
				electricityValue += '<input type="hidden" name="oldElecEndDegree" value="'+(oldData[i].end_DEGREE == null?"":oldData[i].end_DEGREE)+'">';
				electricityValue += '</td></tr>';
			}
		}
		
		$('#electricityTbody').append(electricityValue);
	}
	
	function paymentOldNewDate(oldData,newData){
		$('#paymentDateTbody tr').remove();
		var paymentDateValue = '';
		if(newData != null){
			for(var i = 0 ; i<newData.length;i++){
				paymentDateValue += '<tr>';
				paymentDateValue += '<td>'+(newData[i].location_ID != null ? newData[i].location_ID:"")+'</td>';
				paymentDateValue += '<td>'+(newData[i].lessor_NAME != null ? newData[i].lessor_NAME:"")+'</td>';
				paymentDateValue += '<td>'+(newData[i].recipient_ID != null ? newData[i].recipient_ID:"")+'</td>';
				paymentDateValue += '<td>'+(newData[i].recipient_NAME != null ? newData[i].recipient_NAME:"")+'</td>';
				paymentDateValue += '<td>'+(newData[i].lessor_RELATION != null ? newData[i].lessor_RELATION:"")+'</td>';
				paymentDateValue += '<td>'+(newData[i].paymentModeName != null ? newData[i].paymentModeName:"")+'</td>';
				paymentDateValue += '<td>'+(newData[i].unit_NAME != null ? newData[i].unit_NAME:"")+'</td>';
				paymentDateValue += '<td>'+(newData[i].nick_NAME != null ? newData[i].nick_NAME:"")+'</td>';
				paymentDateValue += '<td>'+(newData[i].account_NO != null ? newData[i].account_NO:"")+'</td>';
				paymentDateValue += '<td><input name="editPayAloc" type="text" style="margin: 2px 5px 2px 0px" size="2" onblur="checkIntOnBlur(this);" value="'+(newData[i].pay_ALOC != null ? newData[i].pay_ALOC : "")+'"/>%</td>';
				paymentDateValue += '<td style="padding: 3px 5px 3px 5px"><input name="editPayAmt" type="text" class="form-control" onblur="checkIntOnBlur(this);" style="width: 100%;" value="'+(newData[i].pay_AMT != null ? newData[i].pay_AMT:"")+'"/></td>';
				paymentDateValue += '<input type="hidden" name="editNewPayAdded" value="'+newData[i].location_ID+','+newData[i].lessor_NAME+','+newData[i].recipient_ID+','+newData[i].recipient_NAME+','+newData[i].lessor_RELATION+','+newData[i].payment_MODE+','+newData[i].unit_CODE+','+newData[i].sub_UNIT_CODE+','+newData[i].account_NO+','+newData[i].business_TAX+','+newData[i].ls_VER+','+newData[i].payment_ITEM+'">';
				paymentDateValue += '<input type="hidden" name="editOldPayAdded" value="'+oldData[i].location_ID+','+oldData[i].lessor_NAME+','+oldData[i].recipient_ID+','+oldData[i].recipient_NAME+','+oldData[i].lessor_RELATION+','+oldData[i].payment_MODE+','+oldData[i].unit_CODE+','+oldData[i].sub_UNIT_CODE+','+oldData[i].account_NO+','+oldData[i].business_TAX+','+oldData[i].ls_VER+','+newData[i].payment_ITEM+','+oldData[i].pay_ALOC+','+oldData[i].pay_AMT+'">';
				paymentDateValue += '</td>';
				paymentDateValue += '</tr>';
			}
		}
		$('#paymentDateTbody').append(paymentDateValue);
	}
	
	function disabledFlag(){
		$('#editAmmeterNo').prop("disabled", true);
		if($('#itemSEL').val() == '電表歸零'){
			$('#editElecMode').prop("disabled", true);
			$('#editBillingMode').prop("disabled", true);//計費方式
		}else{
			$('#editElecMode').prop("disabled", false);
			$('#editBillingMode').prop("disabled", false);//計費方式
		}
	}
	
	 function qequiredConfirm(newSplits,oldSplits,newElecEndDegree){
		var news  = newSplits;
		var olds  = oldSplits;
		var msg ='';
		if(news[4] == ''){
			if(msg.length == 0){
				msg += "電碼號碼:"+news[1]+"-";
			}
			msg += '變更起始日 　';
		} 
		if(news[0] == '' || news[0] == 'null'){
			if(msg.length == 0){
				msg += "電碼號碼:"+news[1]+"-";
			}
			msg += '用電方式  　';
		}
		if($('#itemSEL').val() == '電表歸零'){
			if(newElecEndDegree == ''){
				if(msg.length == 0){
					msg += "電碼號碼:"+news[1]+"-";
				}
				msg += '期末度數  　';
			}
			if(news[3] == ''){
				if(msg.length == 0){
					msg += "電碼號碼:"+news[1]+"-";
				}
				msg += '期初度數  　';
			}
		}else{
			if(olds[0] == 'C' && news[0] == 'C'){
				if(news[2] == ''){
					if(msg.length == 0){
						msg += "電碼號碼:"+news[1]+"-";
					}
					msg += '計費方式  　';
				}
			}else if(olds[0] == 'C' && (news[0] == 'G' || news[0] == 'F')){
				if(newElecEndDegree == ''){
					if(msg.length == 0){
						msg += "電碼號碼:"+news[1]+"-";
					}
					msg += '期末度數  　';
				}
			}else if(olds[0] == 'C' && news[0] == 'E'){
				if(newElecEndDegree == ''){
					if(msg.length == 0){
						msg += "電碼號碼:"+news[1]+"-";
					}
					msg += '期末度數  　';
				}
				if(news[2] == ''){
					if(msg.length == 0){
						msg += "電碼號碼:"+news[1]+"-";
					}
					msg += '計費方式  　';
				}
			}else if(olds[0] == 'E' && news[0] == 'E'){
				if(news[2] == ''){
					if(msg.length == 0){
						msg += "電碼號碼:"+news[1]+"-";
					}
					msg += '計費方式  　';
				}
			}else if(olds[0] == 'E' && (news[0] == 'G' || news[0] == 'F')){
			}else if(olds[0] == 'E' && news[0] == 'C'){
				if(news[2] == ''){
					if(msg.length == 0){
						msg += "電碼號碼:"+news[1]+"-";
					}
					msg += '計費方式  　';
				}
				if(news[3] == ''){
					if(msg.length == 0){
						msg += "電碼號碼:"+news[1]+"-";
					}
					msg += '期初度數  　';
				}
			}else if((olds[0] == 'G' || olds[0] == 'F') && news[0] == 'C'){
				if(news[2] == ''){
					if(msg.length == 0){
						msg += "電碼號碼:"+news[1]+"-";
					}
					msg += '計費方式  　';
				}
				if(news[3] == ''){
					if(msg.length == 0){
						msg += "電碼號碼:"+news[1]+"-";
					}
					msg += '期初度數  　';
				}
			}else if((olds[0] == 'G' || olds[0] == 'F') && news[0] == 'E'){
				if(news[2] == ''){
					if(msg.length == 0){
						msg += "電碼號碼:"+news[1]+"-";
					}
					msg += '計費方式  　';
				}
			}else if(olds[0] == 'A' && news[0] == 'C'){
				if(news[2] == ''){
					if(msg.length == 0){
						msg += "電碼號碼:"+news[1]+"-";
					}
					msg += '計費方式  　';
				}
				if(news[3] == ''){
					if(msg.length == 0){
						msg += "電碼號碼:"+news[1]+"-";
					}
					msg += '期初度數  　';
				}
			}else if(olds[0] == 'A' && news[0] == 'E'){
				if(news[2] == ''){
					if(msg.length == 0){
						msg += "電碼號碼:"+news[1]+"-";
					}
					msg += '計費方式  　';
				}
			}else if(olds[0] == 'B' && news[0] == 'C'){
				if(news[2] == ''){
					if(msg.length == 0){
						msg += "電碼號碼:"+news[1]+"-";
					}
					msg += '計費方式  　';
				}
				if(news[3] == ''){
					if(msg.length == 0){
						msg += "電碼號碼:"+news[1]+"-";
					}
					msg += '期初度數  　';
				}
			}else if(olds[0] == 'B' && news[0] == 'E'){
				if(news[2] == ''){
					if(msg.length == 0){
						msg += "電碼號碼:"+news[1]+"-";
					}
					msg += '計費方式  　';
				}
			}else if(olds[0] == 'D' && news[0] == 'C'){
				if(newElecEndDegree == ''){
					if(msg.length == 0){
						msg += "電碼號碼:"+news[1]+"-";
					}
					msg += '期末度數  　';
				}
				if(news[2] == ''){
					if(msg.length == 0){
						msg += "電碼號碼:"+news[1]+"-";
					}
					msg += '計費方式  　';
				}
				if(news[3] == ''){
					if(msg.length == 0){
						msg += "電碼號碼:"+news[1]+"-";
					}
					msg += '期初度數  　';
				}
			}else if(olds[0] == 'D' && news[0] == 'E'){
				if(newElecEndDegree == ''){
					if(msg.length == 0){
						msg += "電碼號碼:"+news[1]+"-";
					}
					msg += '期末度數  　';
				}
				if(news[2] == ''){
					if(msg.length == 0){
						msg += "電碼號碼:"+news[1]+"-";
					}
					msg += '計費方式  　';
				}
			}else if(olds[0] == 'D' && (news[0] == 'G' || news[0] == 'F')){
				if(newElecEndDegree == ''){
					if(msg.length == 0){
						msg += "電碼號碼:"+news[1]+"-";
					}
					msg += '期末度數  　';
				}
			}
		}
		return msg;
	} 
	  //控制畫面是否開啟
	 function initStates(appMode){
			if(appMode=='VIEW'){
				$("#editElecForm select").prop("disabled",true);
				$("#editElecForm input").prop("disabled",true);
				$('#btn_1M3Save_Draft').prop("disabled",true);
				$('#btn_1M3Print').prop("disabled",true);
				$('#btn_1M3PrintRent').prop("disabled",true);
				$("#btn_1M3Save_Draft").prop("disabled", true);
			}
		}
	 
</script>