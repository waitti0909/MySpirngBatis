<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>

<button class="btn btn-primary btn-label" type="button" id="elecAddSave">存檔</button>

<table style="width: 100%;">
</table>

<div id="" style="padding: 10px 10px 0px 10px">
	<label class="control-label" style="margin-bottom: 10px">原用電協議</label>
	<table class="table table-bordered table-hover table-heading no-border-bottom"
				style="width: 100%;" id="location_elec_table">
		<thead>
			<tr style="height: 25px">
				<th style="width: 15%;">電錶號碼</th>
				<th style="width: 15%;">用電方式</th>
				<th style="width: 15%;">計費方式</th>
				<th style="width: 25%;">用電期間</th>
				<th style="width: 30%;">用電地址</th>
			</tr>
		</thead>
		<tbody id="esiteTbody">
		</tbody>
	</table>
</div>

<hr>

<div style="margin-top: 20px" >
		<button class="btn btn-primary btn-label" type="button" id="newLocElecButton">
			新增電錶</button>
		<button class="btn btn-primary btn-label" type="button" id="saveLocElecButton" onclick="saveLocElecTemp();">
			儲存電錶</button>
		<button class="btn btn-primary btn-label" type="button" id="delLocElecButton" >
			刪除電錶</button>
<br>
		<table style="width: 100%;margin-bottom: 15px" id="elec_editingArea_table">
			<tr>
				<td nowrap="nowrap" style="width: 6%"><label
					class="col-sm-12 control-label">用電方式 :</label></td>
				<td style="">
					<div class="col-sm-12">
						<div
							style="width: 30%; height: auto; float: left; display: inline">
							<select id="edit_elec_MODE" class="populate placeholder" name="elec_MODE">
							<c:forEach items="${elecModeMap}" var="data">
								<option value="${data.key}">${data.value}</option>
							</c:forEach>
							</select>
						</div>
						<div
							style="width: 15%; height: auto; float: left; display: inline">
							&nbsp;</div>
						<div
							style="width: 50%; height: auto; float: left; display: inline; margin-top: 6px">
							<input id="edit_sign_AGREEMENT" type="checkbox" name="sign_AGREEMENT" value="Y"  >&nbsp;&nbsp;簽訂借電協議書
						</div>
					</div>
				</td>
			</tr>

			<tr id="edit_CHRG_MODE_D_tr" style="display:none;">
				<td nowrap="nowrap" style="width: 6%">&nbsp;</td>
				<td style="padding-top: 8px" colspan="2">
					<div class="col-sm-12">
						<div
							style="width: 27%; height: auto; float: left; display: inline; margin-top: 6px">
							級距計費 : 以每度為台電公告之非時間電價</div>
						<div style="width: 15%; height: auto; float: left; display: inline">
							<input id="edit_chrg_MODE_D" value="0" name="chrg_MODE" type="number" class="form-control" min="0" max="10" readonly="readonly" />
						</div>
						<div
							style="width: 55%; height: auto; float: left; display: inline; margin-left: 5px; margin-top: 6px">
							度以上級距費率。(請點選下方級距)</div>
					</div>
					<br>
					<div style="padding-top: 10px;">
						<table class="table table-striped table-bordered table-hover table-heading no-border-bottom" style="width: 100%; margin-top: 5px">
							<thead>
								<tr >
									<th style="width: 20%;">開始級距</th>
									<th style="width: 20%;">結束級距</th>
									<th style="width: 20%;">非夏日電價</th>
									<th style="width: 20%;">夏日電價</th>
								</tr>
							</thead>
							<tbody id="tbodySite_Elec_Range_Setting">
							</tbody>
						</table>
					
					</div>
				</td>
			</tr>

			<tr id="edit_CHRG_MODE_C_tr" style="display:none;">
				<td nowrap="nowrap" style="width: 6%">&nbsp;</td>
				<td style=" padding-top: 4px" >
					<div class="col-sm-12">
						<div
							style="width: 20%; height: auto; float: left; display: inline; margin-top: 6px">
							以度計費 : 以每度為新台幣</div>
						<div style="width: 5%; height: auto; float: left; display: inline">
							<input id="edit_chrg_MODE_C" name="chrg_MODE" value="0" class="form-control" min="0" max="10" onblur="checkIntOnBlur(this);" />
						</div>
						<div
							style="width: 60%; height: auto; float: left; display: inline; margin-left: 5px; margin-top: 6px">
							元計算。</div>
					</div>
				</td>
			</tr>

			<tr id="edit_CHRG_MODE_E_tr" style="display:none;">
				<td nowrap="nowrap" style="width: 6%">&nbsp;</td>
				<td style=" padding-top: 4px" colspan="2">
					<div class="col-sm-12">
						<div
							style="width: 32%; height: auto; float: left; display: inline; margin-top: 6px">
							固定金額 : 估算每月用電，按月補助新台幣</div>
						<div
							style="width: 10%; height: auto; float: left; display: inline">
							<input id="edit_chrg_MODE_E" value="0" name="chrg_MODE" class="form-control" type="number" min="0" max="10" onblur="checkIntOnBlur(this);" />
						</div>
						<div
							style="width: 50%; height: auto; float: left; display: inline; margin-left: 5px; margin-top: 6px">
							元。</div>
					</div>
				</td>
			</tr>

			
			
			<tr>
				<td nowrap="nowrap" style="width: 6%; padding-top: 6px"><label
					class="col-sm-12 control-label">用電起訖 :</label></td>
				<td style=" padding-top: 4px">
					<div class="col-sm-12">
						<div
							style="width: 20%; height: auto; float: left; display: inline; margin-top: 6px">
							<input id="edit_elec_BEGIN_DATE" name="elec_BEGIN_DATE" type="text"
								class="form-control" readonly="readonly" placeholder="點選輸入框">
						</div>
						<div
							style="width: 5%; height: auto; float: left; display: inline; margin-top: 12px; text-align: center;">
							至</div>
						<div
							style="width: 75%; height: auto; float: left; display: inline; margin-top: 6px">
							<div class="col-sm-4" style="padding-left: 0px">
								<input id="edit_elec_END_DATE" name="elec_END_DATE" type="text"
									class="form-control" readonly="readonly" placeholder="點選輸入框">
							</div>
						</div>
					</div>
				</td>
			</tr>

			<tr>
				<td nowrap="nowrap" style="width: 6%; padding-top: 6px"><label
					class="col-sm-12 control-label">電錶號碼 :</label></td>
				<td style="padding-top: 4px">
					<div class="col-sm-12">
						<div
							style="width: 20%; height: auto; float: left; display: inline; margin-top: 6px">
							<input id="edit_energy_METER" name="energy_METER" class="form-control" type="text">
						</div>

						<div
							style="width: 30%; height: auto; float: left; display: inline; margin-top: 5px">
							<label class="col-sm-12 control-label">期初度數 :</label>
						</div>

						<div
							style="width: 8%; height: auto; float: left; display: inline; margin-top: 6px; margin-left: 8px">
							<input id="edit_begin_DEGREE" onblur="checkIntOnBlur(this);" name="begin_DEGREE" class="form-control" type="number">
						</div>
						<div
							style="width: 37%; height: auto; float: left; display: inline; margin-left: 6px; margin-top: 10px">
							度</div>

					</div>
				</td>
			</tr>
			
			
			<tr>
				<td nowrap="nowrap" style="width: 6%; padding-top: 6px"><label
					class="col-sm-12 control-label">用電戶名 :</label></td>
				<td style="padding-top: 4px">
					<div class="col-sm-12">
						<div
							style="width: 20%; height: auto; float: left; display: inline; margin-top: 6px">
							<input id="edit_elec_CUST_NAME" name="elec_CUST_NAME" class="form-control" type="text">
						</div>

						<div
							style="width: 30%; height: auto; float: left; display: inline; margin-top: 5px">
							<label class="col-sm-12 control-label">押金 :</label>
						</div>

						<div
							style="width: 8%; height: auto; float: left; display: inline; margin-top: 6px; margin-left: 8px">
							<input id="edit_elec_PLEDGE" name="elec_PLEDGE" class="form-control" type="number" onblur="checkIntOnBlur(this);">
						</div>
						

					</div>
				</td>
			</tr>
			<tr>
				<td nowrap="nowrap" style="width: 6%; padding-top: 6px"><label
					class="col-sm-12 control-label">移入電錶號碼 :</label></td>
				<td style="padding-top: 4px">
					<div class="col-sm-12">
						<div
							style="width: 20%; height: auto; float: left; display: inline; margin-top: 6px">
							<input id="edit_org_ENERGY_METER" name="org_ENERGY_METER" class="form-control" type="text">
						</div>

						<div
							style="width: 30%; height: auto; float: left; display: inline; margin-top: 5px">
							<label class="col-sm-12 control-label">移入押金 :</label>
						</div>

						<div
							style="width: 8%; height: auto; float: left; display: inline; margin-top: 6px; margin-left: 8px">
							<input id="edit_pldg_IN" name="pldg_IN" class="form-control" onblur="checkIntOnBlur(this);" type="number">
						</div>
					

					</div>
				</td>
			</tr>
			
			
			<tr>
				<td nowrap="nowrap" style="width: 6%"><label
					class="col-sm-12 control-label">用電地址 :</label></td>
				<td style="padding-top: 4px">
					<div class="col-sm-12" style="padding-top: 6px">
						<div
							style="width: 60%; height: auto; float: left; display: inline">
							<input class="form-control" id="edit_elec_ADDR" name="elec_ADDR" readonly="readonly"
								placeholder="預設租約地址" />
							<input type="hidden" id="edit_elec_ADDR_STD" name="elec_ADDR_STD">	
						</div>
						<div
							style="width: 35%; height: auto; float: left; display: inline; margin-left: 5px">
							<button class="btn btn-primary btn-label" type="button" id=""
								onclick="updateElecAddrButton();">輸入地址</button>
						</div>
					</div>
				</td>
			</tr>

			<tr>
				<td nowrap="nowrap" style="width: 6%"><label
					class="col-sm-12 control-label">滯納金條件 :</label></td>
				<td style="padding-top: 4px">
					<div class="col-sm-12">
						<div
							style="width: 4%; height: auto; float: left; display: inline; margin-top: 6px">
							每月</div>
						<div
							style="width: 5%; height: auto; float: left; display: inline;">
							<input id="edit_penalty_DAY" name="penalty_DAY" class="form-control"
								style="padding: 2px 0px 0px 10px" type="number" min="1" max="31" onblur="checkIntOnBlur(this);" />
						</div>
						<div
							style="width: 88%; height: auto; float: left; display: inline; margin-top: 6px; margin-left: 5px">
							號開始產生滯納金</div>
					</div>
				</td>
			</tr>

			<tr>
				<td nowrap="nowrap" style="padding-top: 5px"><label class="col-sm-12 control-label">滯納金 :</label></td>
				<td colspan="3">
					<div class="col-sm-12">
						<div style="width: 6%; height: auto; float: left; display: inline; margin-top: 10px">
							<input id="elec_penalty_DESC_TEXT" class="form-control" style="padding: 2px 0px 0px 10px" type="text" title="滯納金 ">
							<input type="hidden" name="penalty_DESC" id="elec_penalty_DESC">
						</div>
						<div style=" height: auto; float: left; display: inline; margin-top: 10px">
							&nbsp;&nbsp;<input id="elec_penalty_DESC_RADIO_1" name="elec_penalty_DESC_RADIO" class="penalty_DESC_RADIO resetCheckBox" type="checkbox" value="%">&nbsp;&nbsp;%
							&nbsp;&nbsp;<input id="elec_penalty_DESC_RADIO_2" name="elec_penalty_DESC_RADIO" class="penalty_DESC_RADIO resetCheckBox" type="checkbox" value="‰">&nbsp;&nbsp;‰
							&nbsp;&nbsp;<input id="elec_penalty_DESC_RADIO_3" name="elec_penalty_DESC_RADIO" class="penalty_DESC_RADIO resetCheckBox" type="checkbox" value="$">&nbsp;&nbsp;固定金額
						</div>
					</div>
				</td>
			</tr>

		</table>
	
</div>

<hr>


<!-- 用電付款資訊   START -->
<div style="margin-top: 5px">
	<fieldset class="ftSolidLine">
		<legend class="legend">付款資訊</legend>
		<div>
			<button class="btn btn-primary btn-label" type="button" id=""
				onclick="addPaymentRow('elecTbody',true);">新增</button>
			<button class="btn btn-primary btn-label" type="button" id=""
				onclick="delRow('elecTB');">刪除</button>
		</div>

		<table id="elecTB"
			class="table table-bordered table-hover table-heading no-border-bottom"
			style="width: 100%; margin-top: 5px">
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
			<tbody id="elecTbody">
			</tbody>
		</table>

	</fieldset>
</div>
<!-- 用電付款資訊   END -->

<!-- 用電站台資訊   START -->
<div style="margin-top: 5px">
	<fieldset class="ftSolidLine">
		<legend class="legend">站台資訊</legend>

		<table
			class="table table-striped table-bordered table-hover table-heading no-border-bottom"
			style="width: 100%; margin-top: 5px">
			<thead>
				<tr>
					<th style="width: 25%;">站台編號</th>
					<th style="width: 25%;">終止日</th>
					<th style="width: 25%;">分攤項目</th>
					<th style="width: 25%;">分攤比率</th>
				</tr>
			</thead>
			<tbody id="tbodySite_elec" />
		</table>

	</fieldset>
</div>
<div id="showAddressFrame"></div>

<script type="text/javascript">
	$(document).ready(function() {
		
		//用電起訖
		$( "#edit_elec_BEGIN_DATE" ).datepicker({
		    onClose: function( selectedDate ) {
		        $( "#edit_elec_END_DATE" ).datepicker( "option", "minDate", selectedDate );
		    }
		});
		$( "#edit_elec_END_DATE" ).datepicker({
		    onClose: function( selectedDate ) {
		        $( "#edit_elec_BEGIN_DATE" ).datepicker( "option", "maxDate", selectedDate );
		    }
		});
		
		//加設_租  站台編號
		$('#elec_siteInfoTB').scrolltable({
			stripe : true,
			oddClass : 'odd',
			maxHeight : 80
		});			
		$('#elec_siteInfoTB table:last').addClass('table-striped');

		addClassSEL("elecPayTB");
		
		$('#elecAddSave').click(function(){
			var elecPay='';
			 $('#elecTbody').find('tr').each(function(){
				 if(elecPay.length > 0){
					 elecPay += ';';	 
				 }
				 elecPay += $(this).find('select[name="PAYMENT_ITEM"]').val()+',';
				 elecPay += $(this).find('select[name="LESSOR_ID"]').val()+',';
				 elecPay += $(this).find('input[name="RECIPIENT_ID"]').val()+',';
				 elecPay += $(this).find('input[name="RECIPIENT_NAME"]').val()+',';
				 elecPay += $(this).find('select[name="LESSOR_RELATION"]').val()+',';
				 elecPay += $(this).find('input[name="payment_Mode_text"]').val()+',';
				 elecPay += $(this).find('select[name="UNIT_CODE"]').val()+',';
				 elecPay += $(this).find('select[name="SUB_UNIT_CODE"]').val()+',';
				 elecPay += $(this).find('input[name="ACCOUNT_NO"]').val()+',';
				 elecPay += $(this).find('input[name="PAY_ALOC"]').val()+',';
				 elecPay += $(this).find('input[name="PAY_AMT"]').val();
			});  
			$('#elecPayment').val(elecPay);
			var elecSite = '';
			var number = 1;
			$('#tbodySite_elec tr').find('td').each(function(){
				if((number != 1 ? number-1 : 1) % 4 == 0){
					elecSite += ';';
				}
				elecSite +=$(this).text();
				 if(number % 4 == 0){
				 }else{
					 elecSite += ',';
				 }
				 number++;
			});
			$('#elecSite').val(rentSite);
		});
		
		$("#newLocElecButton").click(function() {
			
			clear_form_elements("elec_editingArea_table");
			$('#edit_CHRG_MODE_C_tr').hide();
			$('#edit_CHRG_MODE_D_tr').hide();
			$('#edit_CHRG_MODE_E_tr').hide();
			
			$('#edit_elec_ADDR').val($('#leaseDefaultAddress_Elec').val());
			$('#edit_elec_ADDR_STD').val($('#leaseDefaultAddress_STD_Elec').val());
		
			$('#edit_elec_MODE').val($('#edit_elec_MODE option:first-child').val());
			$('#edit_elec_MODE').select2("val",$('#edit_elec_MODE option:first-child').val()); 
		
			$("#esiteTbody tr").removeClass('selected');
			
		});
		
		$("#delLocElecButton").click(function() {
			
			if(confirm("確定刪除此電錶?")){
				delRow('location_elec_table');
				$("#newLocElecButton").trigger("click");
			}
		});
		
		$("#edit_elec_MODE").change(function() {
			$("#edit_CHRG_MODE_C_tr").hide();
			$("#edit_CHRG_MODE_C").val('0');
			$("#edit_CHRG_MODE_D_tr").hide();
			$("#edit_CHRG_MODE_D").val('0');
			$("#edit_CHRG_MODE_E_tr").hide();
			$("#edit_CHRG_MODE_E").val('0');
			$("#edit_CHRG_MODE_"+$(this).val()+"_tr").show();
		});

	});	
	
	function ls001M3ElecSelect2() {
		$("select").select2();
	}
	
	//新增用電付款對象
	function addPaymentPSNPower(tbodyID,type){	
		
		var lessorNamesValue =  $('#LessorNames_site').val().split(',');
		var row1 = '<tr><td><select id="" class="" name="'+type+'PAYMENT_ITEM">';
			row1 +='<option value="E" selected>電費</option><option value="ED">電費押金</option>';
		
			row1 +='</select></td>';
		
			row1 += '<td><select id="'+type+'loc_pay_lessor" onchange="changeLessorPaymentMode(this);" name="'+type+'LESSOR_ID">';
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
		row1 +='</select><input type="hidden" name="'+type+'LESSOR_NAME" value="'+firstElement+'"  id="'+type+'loc_pay_lessor_name" ></td>';
		row1 += '<td><input class="form-control" name="'+type+'RECIPIENT_ID" type="text" style="width: 100%;" value=""></td>';
		row1 += '<td><input class="form-control" name="'+type+'RECIPIENT_NAME" type="text" style="width: 100%;" value=""></td>';
		row1 += '<td><select id="'+type+'locpayment_lessor_relation"  name="'+type+'LESSOR_RELATION">';
		var typeValue =  $('#LessorRelationType').val().split(';');
		for(var j = 0 ;j<typeValue.length;j++){
			var nameCode = typeValue[j].split(',')
			row1 += '<option value="'+nameCode[0]+'" >'+nameCode[1]+'</option>';
		}
		row1 += '</select></td>';
		var paymentAndTax = getLessorPaymentModeAndTax(firstElementId).split(":");

		row1 += "<td><input type='text' class='form-control' readOnly id='"+type+"payment_Mode_text_id' name='"+type+"payment_Mode_text' value='"+getLessorPaymentModeText(paymentAndTax[0])+"' >";
		
		row1 += '<input type="hidden" name="'+type+'payment_Mode"  value="'+paymentAndTax[0]+'"></td>';
		row1 += '<td><select  name="'+type+'UNIT_CODE" onchange="changeBanksSub(this);"  id="'+type+'edit_unit_CODE">';
		row1 += buildBanksOptions();
		row1 +='</select></td>';
		row1 += '<td><select name="'+type+'SUB_UNIT_CODE" id="'+type+'edit_sub_unit_code" ><option value="" >--- 請選擇 ---</option></select></td>';
		row1 += '<td><input class="form-control" name="'+type+'ACCOUNT_NO" type="text" style="width: 100%;" value=""></td>';
		row1 += '<td><input class="form-control" name="'+type+'PAY_ALOC" onblur="checkIntOnBlur(this);" max="3" type="number" style="width: 100%;padding: 2px 0px 0px 10px" value="0"></td>';
		row1 += '<td><input class="form-control" name="'+type+'PAY_AMT" onblur="checkIntOnBlur(this);" max="9" type="number" style="width: 100%;padding: 2px 0px 0px 10px" value="0"></td>';
		row1 += '<td><input class="form-control"  name="'+type+'BUSINESS_TAX" onblur="checkIntOnBlur(this);" value="'+paymentAndTax[1]+'"></td></tr>';
		
		$("#" + tbodyID).append(row1);
		$("select").select2();
		
	}
	
	function setElecData(){
		var data = {
				lsNo : $("#lsNo").text(),
				locId : $.trim($('#editLocId').val()),
				addFlag : 'flag'
			}; 
		$.ajax({			
			url : CONTEXT_URL + "/ls/LS001M_Site/getLocationSitePay4Elec",		
			data: data,
			type : "POST",
			dataType : 'json',
			success : function(data) {
				if(data.success){
					//alert(dateFormat(data.row.pay_BEGIN_DATE));editPldgAmt
					
					var elecList = data.row.lsLocElecList;
					$("#esiteTbody tr").remove();
					for(var i = 0 ;i<elecList.length;i++){
						var jsonString=  JSON.stringify(elecList[i]);
						var row1='';
						row1+='<tr  onclick="showElecData(this);">';
						row1+='<td>'+elecList[i].energy_METER+'</td>';
						row1+='<td>'+$("#edit_elec_MODE option[value='"+elecList[i].elec_MODE+"']").text()+'</td>';
						row1+='<td>'+(elecList[i].chrg_MODE!=null?elecList[i].chrg_MODE:"")+'</td>';
						row1+='<td>'+elecList[i].elec_BEGIN_DATE+'~'+elecList[i].elec_END_DATE+'</td>';
						row1+='<td>'+elecList[i].elec_ADDR+'<input type="hidden" name="elecJsonValue" value=\''+jsonString+'\'></td></tr>';
						$("#esiteTbody").append(row1);
					}
					
					var elecRangeSettingList = data.row.tbLsElecRanges;
					$("#tbodySite_Elec_Range_Setting").empty();
					for(var i = 0 ;i<elecRangeSettingList.length;i++){
						var row1='';
						row1+='<tr onclick=\'javascript:$("#edit_chrg_MODE_D").val("'+elecRangeSettingList[i].begin_RANGE+'");\'>';
						row1+='<td>'+elecRangeSettingList[i].begin_RANGE+'</td>';
						row1+='<td>'+elecRangeSettingList[i].end_RANGE+'</td>';
						row1+='<td>'+elecRangeSettingList[i].normal_PRICE+'</td>';
						row1+='<td>'+elecRangeSettingList[i].special_PRICE+'</td></tr>';
						$("#tbodySite_Elec_Range_Setting").append(row1);
					}	
					
					//組站台資訊資料
					$('#tbodySite_elec tr').remove();
					$("#tbodySite_elec").append(setTbLsSite(data.row.tbLsSiteDTOList));
					//組付款資訊資料a
					$('#elecTbody tr').remove();
					$("#elecTbody").append(buildPaymeData(data.row.tbLsLocPaymentList,true)); 
					$("select").select2();
				}else{
					alert(data.msg);
				}
			}
		});
	}
	
	function updateElecAddrButton(){
		callBackAddrField = "edit_elec_ADDR";
		callBackAddrStdField = "edit_elec_ADDR_STD";
		openLessorAddressProcess("receviceAddressData");
	}
	
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
				/* if (callBackAddrField == "residence_ADD") {
					var obj = JSON.parse(addressObject);
					var tax = getDefaultTax(obj.zip);
					$("#business_TAX").val(tax);
				} */
			}
		});
	}
	
	// 塞值至下方
	function showElecData(theField){

		$("#newLocElecButton").trigger("click");
		
		$("#esiteTbody tr").removeClass('selected');
		$(theField).addClass('selected');
		
		var stringJson = $(theField).find("input[name='elecJsonValue']").val();
		var jsonObj = JSON.parse(stringJson);
		for(var o in jsonObj){
			if(o=='elec_MODE'){
				$("#edit_elec_MODE").select2("val",jsonObj[o]);
				$("#edit_chrg_MODE_"+jsonObj[o]).val(jsonObj['chrg_MODE']);
				$("#edit_CHRG_MODE_"+jsonObj[o]+"_tr").show();
				
			}else if(o=='sign_AGREEMENT'){
				if(jsonObj[o]=='Y'){
					$("#edit_sign_AGREEMENT").prop("checked",true);
				}
			}else if(o=='penalty_DESC'){
				if(jsonObj[o]!=null){
					var t= jsonObj[o].split(":");
					$("#elec_penalty_DESC_TEXT").val(t[0]);
					if($("#elec_penalty_DESC_RADIO_1").val()==t[1]){
						$("#elec_penalty_DESC_RADIO_1").prop("checked",true);
					}else if($("#elec_penalty_DESC_RADIO_2").val()==t[1]){
						$("#elec_penalty_DESC_RADIO_2").prop("checked",true);
					}else if($("#elec_penalty_DESC_RADIO_3").val()==t[1]){
						$("#elec_penalty_DESC_RADIO_3").prop("checked",true);
					}
				}
			}else{
				$("#edit_"+o).val(jsonObj[o]);
			}
		}
	}
	
function saveLocElecTemp(){
		
		if($('#edit_elec_MODE').val()==''){
			alert("請選擇用電方式");
			return false;
		}
		if($('#edit_elec_CUST_NAME').val()==''){
			alert("請輸入用電戶名");
			return false;
		}
		if($('#edit_elec_BEGIN_DATE').val()=='' || $('#edit_elec_END_DATE').val()==''){
			alert("請輸入用電起訖");
			return false;
		}else{
			var splits  = $('#lsDate').text().split("~");
			if(Date.parse($('#edit_elec_BEGIN_DATE').val()) > Date.parse($('#edit_elec_END_DATE').val())){
				alert("迄日應比起日大");
				return false;
			}
			if(Date.parse($('#edit_elec_BEGIN_DATE').val()) < Date.parse(splits[0])){
				alert("起日應比合約起日大");
				return false;
			}
			if(Date.parse($('#edit_elec_END_DATE').val()) > Date.parse(splits[1])){
				alert("迄日應比合約迄日小");
				return false;
			}
			/* if($('#edit_elec_BEGIN_DATE').val()!=''){
				if(!checkDateStrRange($('#leaseStartDate_Elec').val(),$('#edit_elec_BEGIN_DATE').val())){
					alert("用電起日不可早於合約起日");
					return false;
				}	
			}
			if($('#edit_elec_END_DATE').val()!=''){
				if(!checkDateStrRange($('#edit_elec_END_DATE').val(),$('#leaseEndDate_Elec').val())){
					alert("用電迄日不可晚於合約迄日");
					return false;
				}	
			} */
		}
		var edit_meter_val = $('#edit_energy_METER').val();
		if(edit_meter_val==''){
			alert("請輸入電錶號碼");
			return false;
		}
		if($('#edit_begin_DEGREE').val()==''){
			alert("請輸入期初度數");
			return false;
		}
		
		if($('#edit_elec_ADDR').val()==''){
			alert("請輸入用電地址");
			return false;
		}
		
		if($("#edit_penalty_DAY").val()!=''){
			if($("#elec_penalty_DESC_TEXT").val()==''){
				   alert("請輸入滯納金");
				   return false;
			}
		}	


		if($("#edit_CHRG_MODE_D_tr").is(':visible')){
			if($("#edit_chrg_MODE_D").val()==''||$("#edit_chrg_MODE_D").val()==0){
				alert("請選擇級距");
				   return false;
			}
		}
		if($("#edit_CHRG_MODE_C_tr").is(':visible')){
			if($("#edit_chrg_MODE_C").val()==''){
				alert("請輸入電費價格");
				   return false;
			}
		}
		if($("#edit_CHRG_MODE_E_tr").is(':visible')){
			if($("#edit_chrg_MODE_E").val()==0){
				alert("請輸入電費價格");
				   return false;
			}
		}
		
		if($("#elec_penalty_DESC_TEXT").val()!=''){
			if(!$("#elec_penalty_DESC_RADIO_1").prop("checked") && 
			   !$("#elec_penalty_DESC_RADIO_2").prop("checked") 
			   && !$("#elec_penalty_DESC_RADIO_3").prop("checked")){
				   alert("請選擇滯納金單位");
				   return false;
			}
		}	
		
		if($("#elec_penalty_DESC_RADIO_1").prop("checked") || 
				   $("#elec_penalty_DESC_RADIO_2").prop("checked") 
				   || $("#elec_penalty_DESC_RADIO_3").prop("checked")){
			if($("#elec_penalty_DESC_TEXT").val()==''){
					alert("請選擇滯納金金額");
					return false;
			}else{
				var aaa=$("input[name='elec_penalty_DESC_RADIO']:checkbox:checked").val();
			
				$("#elec_penalty_DESC").val($("#elec_penalty_DESC_TEXT").val()+":"+aaa);
			}
		}


		var selectedRow ;
		var repeatMeterFlag = false;
		$("#esiteTbody tr").each(function() {
			var tempValue= $(this).find("span[name='energy_METER_span']").text();
			if(tempValue==edit_meter_val){
				repeatMeterFlag=true;
			}
			if($(this).hasClass('selected')){
				selectedRow=$(this);
				repeatMeterFlag=false;
			}
		});
		if(repeatMeterFlag){
			alert("電錶號碼不可重複");
			return false;
		}
		console.log("selectedRow="+selectedRow);
		var row1='';
		if(selectedRow==null || typeof(selectedRow)=='undefined'){
				row1+='<tr onclick="showElecData(this);">';
		}
		var data = form2js("elec_editingArea_table", ".", true, null);
		var tempValue = $("#edit_chrg_MODE_"+$("#edit_elec_MODE").val()).val();
		data.chrg_MODE=tempValue;
		var jsonString=  JSON.stringify(data);
		
		
		row1+='<td><span name="energy_METER_span">'+$("#edit_energy_METER").val()+'</span></td>';
		row1+='<td>'+$("#edit_elec_MODE").find("option:selected").text()+'</td>';
		
		row1+='<td>'+((typeof(tempValue)!='undefined')?tempValue:"&nbsp;")+'</td>';
		row1+='<td>'+$("#edit_elec_BEGIN_DATE").val()+'~'+$("#edit_elec_END_DATE").val()+'</td>';
		row1+='<td>'+$("#edit_elec_ADDR").val()+'<input type="hidden" name="elecJsonValue" value=\''+jsonString+'\'></td>';
		
		if(selectedRow==null || typeof(selectedRow)=='undefined'){
			row1+='</tr>';
			$("#esiteTbody").append(row1);
		}else{
		//	selectedRow.html('');
			selectedRow.html(row1);
		}
		
		$("#newLocElecButton").trigger("click");

		
	}
</script>