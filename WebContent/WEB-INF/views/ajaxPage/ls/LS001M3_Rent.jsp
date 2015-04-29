<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>

<button class="btn btn-primary btn-label" type="button" id="rentAddSave">存檔</button>

<table style="width: 100%;">
	<tr>
		<td nowrap="nowrap" width="6%"><label
			class="col-sm-12 control-label">承租日 :</label></td>
		<td width="94%">
			<div class="col-sm-12">
				<div
					style="width: 20%; height: auto; float: left; display: inline; margin-top: 8px">
					<input id="tenantDateS" name="tenantDateS" type="text" class="form-control"
						readonly="readonly" placeholder="點選輸入框">
				</div>
				<div
					style="width: 5%; height: auto; float: left; display: inline;; text-align: center; margin-top: 8px">
					至</div>

				<div
					style="width: 20%; height: auto; float: left; display: inline; margin-top: 8px">
					<input id="tenantDateE" name="tenantDateE" type="text" class="form-control"
						readonly="readonly" value="承租迄日同租約迄日" >
				</div>
			</div>
		</td>
	</tr>

	<tr>
		<td nowrap="nowrap" style="padding-top: 8px"><label class="col-sm-12 control-label">租金
				:</label></td>
		<td style="padding-top: 8px">
			<div class="col-sm-12">
				<div
					style="width: 20%; height: auto; float: left; display: inline">
					<input id="addRent" name="addRent" type="text" class="form-control" onblur="checkIntOnBlur(this);">
				</div>
				<div
					style="width: 75%; height: auto; float: left; display: inline;margin-top: 5px; margin-left: 2px">
					元</div>
			</div>
		</td>
	</tr>
</table>


<div style="margin-top: 5px">
	<fieldset class="ftSolidLine">
		<legend class="legend">付款資訊</legend>
		<div
			style="width: 90%; height: auto; float: left; display: inline; margin-top: 5px">
			<button class="btn btn-primary btn-label" type="button" id="tableAdd"
				onclick="addPaymentPSN('rentPayTbody','rent_');">新增</button>
			<button class="btn btn-primary btn-label" type="button" id="tableDel"
				onclick="delRow('rentPayTB');">刪除</button>
		</div>

		<table id="rentPayTB"
			class="table table-bordered table-hover table-heading no-border-bottom"
			style="width: 100%;">
			<thead>
				<tr>
					<th style="width: 10%;">費用項目</th>
					<th style="width: 10%;">出租人</th>
					<th style="width: 7%;">付款對象證號</th>
					<th style="width: 8%;">名稱</th>
					<th style="width: 10%;">與出租人關係</th>
					<th style="width: 6%;">付款方式</th>
					<th style="width: 10%;">銀行</th>
					<th style="width: 10%;">分行</th>
					<th style="width: 11%;">帳號</th>
					<th style="width: 8%;">分攤比</th>
					<th style="width: 10%;">金額</th>
				</tr>
			</thead>
			<tbody id="rentPayTbody">

			</tbody>
		</table>
	</fieldset>
</div>


<div style="margin-top: 5px">
	<fieldset class="ftSolidLine">
		<legend class="legend">站台資訊</legend>
		<table class="table table-striped table-bordered table-hover table-heading no-border-bottom"
			style="width: 100%; margin-top: 5px">
			<thead>
				<tr>
					<th style="width: 25%;">站台編號</th>
					<th style="width: 25%;">終止日</th>
					<th style="width: 25%;">分攤項目</th>
					<th style="width: 25%;">分攤比率</th>
				</tr>
			</thead>
			<tbody id="siteInfoTbody">
			</tbody>
		</table>
	</fieldset>
</div>



<script type="text/javascript">
	$(document).ready(function() {
		
		//承租日	
		$("#tenantDateS").datepicker({
		    onClose: function( selectedDate ) {
		        $("#tenantDateE").datepicker( "option", "minDate", selectedDate );
		    }
		});
		$("#tenantDateE").datepicker({
		    onClose: function( selectedDate ) {
		        $( "#tenantDateS" ).datepicker( "option", "maxDate", selectedDate );
		    }
		});
		
		//加設_租  站台編號
		$('#rent_siteInfoTB').scrolltable({
			stripe : true,
			oddClass : 'odd',
			maxHeight : 80
		});			
		$('#rent_siteInfoTB table:last').addClass('table-striped');
		
		//生效日
		$('.rentPicker').datepicker();
		
		addClassSEL("rentPayTB");
		var splits  = $('#lsDate').text().split("~");
		$('#tenantDateE').val(splits[1]);
		
		$('#tenantDateS').change(function(){
			if($('#tenantDateE').val() != ''){
				if(Date.parse($('#tenantDateS').val()) > Date.parse($('#tenantDateE').val())){
					$('#tenantDateS').val('');
					alert("迄日應比起日大");
					return false;
				}
			}
				var splits  = $('#lsDate').text().split("~");
				if(Date.parse($('#tenantDateS').val()) < Date.parse(splits[0])){
					$('#tenantDateS').val('');
					alert("迄日不能比合約中止日大");
					return false;
				}
			
		});
		
		$('#tenantDateE').change(function(){
				var splits  = $('#lsDate').text().split("~");
			if($('#tenantDateS').val() != ''){
				if(Date.parse($('#tenantDateS').val()) > Date.parse($('#tenantDateE').val())){
					$('#tenantDateE').val(splits[1]);
					alert("迄日應比起日大");
					return false;
				}
			}
				if(Date.parse($('#tenantDateE').val()) > Date.parse(splits[1])){
					$('#tenantDateE').val(splits[1]);
					alert("迄日不能比合約中止日大");
					return false;
				}
		});
		
		$('#rentAddSave').click(function(){
			$('#rentStrDate').val($('#tenantDateS').val());
			$('#rentEndDate').val($('#tenantDateE').val());
			$('#rentRent').val($('#addRent').val());
			var rentPay='';
			 $('#rentPayTbody').find('tr').each(function(){
				 if(rentPay.length > 0){
					 rentPay += ';';	 
				 }
				 rentPay += $(this).find('select[name="PAYMENT_ITEM"]').val()+',';
				 rentPay += $(this).find('select[name="LESSOR_ID"]').val()+',';
				 rentPay += $(this).find('input[name="RECIPIENT_ID"]').val()+',';
				 rentPay += $(this).find('input[name="RECIPIENT_NAME"]').val()+',';
				 rentPay += $(this).find('select[name="LESSOR_RELATION"]').val()+',';
				 rentPay += $(this).find('input[name="payment_Mode_text"]').val()+',';
				 rentPay += $(this).find('select[name="UNIT_CODE"]').val()+',';
				 rentPay += $(this).find('select[name="SUB_UNIT_CODE"]').val()+',';
				 rentPay += $(this).find('input[name="ACCOUNT_NO"]').val()+',';
				 rentPay += $(this).find('input[name="PAY_ALOC"]').val()+',';
				 rentPay += $(this).find('input[name="PAY_AMT"]').val();
			});  
			$('#rentPayment').val(rentPay);
			var rentSite = '';
			var number = 1;
			$('#siteInfoTbody tr').find('td').each(function(){
				if((number != 1 ? number-1 : 1) % 4 == 0){
					rentSite += ';';
				}
				 rentSite +=$(this).text();
				 if(number % 4 == 0){
				 }else{
					 rentSite += ',';
				 }
				 number++;
			});
			$('#rentSite').val(rentSite);
		});
	});
	
	function ls001M3RentSelect2() {
		$("select").select2();
	}
	
	//新增付款對象
	//新增租金付款資訊
		function addPaymentPSN(tbodyID,type) {
	
			var lessorNamesValue =  $('#LessorNames_site').val().split(',');
			var row1 = '<tr><td><select id="" class="" name="PAYMENT_ITEM">';
				row1 +='<option value="R" selected>租金</option><option value="RD">租金押金</option>';
			
			row1 +='</select></td>';
			
			row1 += '<td><select id="loc_pay_lessor" onchange="changeLessorPaymentMode(this);" name="LESSOR_ID">';
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
			
			row1 += '<input type="hidden" name="payment_Mode"  value="'+paymentAndTax[0]+'"></td>';
			row1 += '<td><select  name="UNIT_CODE" onchange="changeBanksSub(this);"  id="edit_unit_CODE">';
			row1 += buildBanksOptions();
			row1 +='</select></td>';
			row1 += '<td><select name="SUB_UNIT_CODE" id="edit_sub_unit_code" ><option value="" >--- 請選擇 ---</option></select></td>';
			row1 += '<td><input class="form-control" name="ACCOUNT_NO" type="text" style="width: 100%;" value=""></td>';
			row1 += '<td><input class="form-control" name="PAY_ALOC" onblur="checkIntOnBlur(this);" max="3" type="number" style="width: 100%;padding: 2px 0px 0px 10px" value="0"></td>';
			row1 += '<td><input class="form-control" name="PAY_AMT" onblur="checkIntOnBlur(this);" max="9" type="number" style="width: 100%;padding: 2px 0px 0px 10px" value="0">';
			row1 += '<input type="hidden"  name="BUSINESS_TAX" value="'+paymentAndTax[1]+'"></td></tr>';
			
			$("#" + tbodyID).append(row1);
			$("select").select2();
		
		}
	
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
	
		function rentDisabled(){
			$('#tenantDateE').prop("disabled", true);
			if($('#itemSEL').val() == '不調租金'){
				$('#tenantDateS').prop("disabled", true);
				$('#addRent').prop("disabled", true);
				$('#rentPayTbody tr select').prop("disabled", true);
				$('#rentPayTbody tr input').prop("disabled", true);
				/* $('#siteInfoTbody tr select').prop("disabled", true);
				$('#siteInfoTbody tr input').prop("disabled", true); */
				$('#rentAddSave').prop("disabled", true);
				$('#tableAdd').prop("disabled", true);
				$('#tableDel').prop("disabled", true);
			}else{
				$('#tenantDateS').prop("disabled", false);
				$('#addRent').prop("disabled", false);
				$('#rentPayTbody tr select').prop("disabled", false);
				$('#rentPayTbody tr input').prop("disabled", false);
				/* $('#siteInfoTbody tr select').prop("disabled", false);
				$('#siteInfoTbody tr input').prop("disabled", false); */
				$('#rentAddSave').prop("disabled", false);
				$('#tableAdd').prop("disabled", false);
				$('#tableDel').prop("disabled", false);
			}
		}
		
		function setRentData(){
			var data = {
					lsNo : $("#lsNo").text(),
					locId : $.trim($('#editLocId').val()),
					addFlag : 'flag'
				}; 
			$.ajax({			
				url : CONTEXT_URL + "/ls/LS001M_Site/getLocationSitePay4Rent",		
				data: data,
				type : "POST",
				dataType : 'json',
				success : function(data) {
					$('#siteRent_form').trigger('reset');		
					if(data.success){
						$('#addRent').val(data.row.rent_AMT)
						$('#LessorRelationType').val(data.row.lsLeaseRelation);//與出租人關係
						$('#LessorNames_site').val(data.row.lessorNames);//出租人
						$('#Lessor_allPaymentMode').val(data.row.allLessorPaymentMode);//出租人付款方式		
						$('#siteInfoTbody tr').remove();
						$('#siteInfoTbody').append(setTbLsSite(data.row.tbLsSiteDTOList));
						$('#rentPayTbody tr').remove();
						$("#rentPayTbody").append(buildPaymeData(data.row.tbLsLocPaymentList,false));
						$("select").select2();
						rentDisabled();
					}
				}
			}); 
		}
	</script>
	