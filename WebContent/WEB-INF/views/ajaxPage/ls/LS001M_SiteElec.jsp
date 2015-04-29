<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>

<div>
	
	<button class="btn btn-primary btn-label" type="button" id="saveLocElecDBButton" >
		存檔</button>
	<button class="btn btn-primary btn-label" type="button" id="">
		列印借電協議書</button>
</div>
<form id="siteElec_form">
<input id="location_Ls_No_elec" name="LS_NO" type="hidden" value="${lsNo}">
<input id="location_Ls_Ver_elec" name="LS_VER" type="hidden" value="${lsVer}"> 
<input  id="edit_location_id_elec" name="LOCATION_ID" type="hidden" value="" >  
<input  id="leaseEndDate_Elec" name="LS_E_DATE" type="hidden" value=""> 
<input  id="leaseStartDate_Elec" name="LS_S_DATE" type="hidden" value=""> 
<input  id="leaseDefaultAddress_Elec" type="hidden" value=""> 
<input  id="leaseDefaultAddress_STD_Elec" type="hidden" value=""> 



<div style="margin-top: 10px">
	<p style="font-weight: bold;">用電方式</p>

	<table id="location_elec_table"
		class="table table-bordered table-hover table-heading no-border-bottom"
		style="width: 100%; margin-top: 5px">
		<thead>
			<tr>
				<th style="width: 13%;">用電方式</th>
				<th style="width: 13%;">電錶號碼</th>
				<th style="width: 13%;">計費方式</th>
				<th style="width: 21%;">用電期間</th>
				<th style="width: 40%;">用電地址</th>
			</tr>
		</thead>
		<tbody id="location_elec_tBody">
		</tbody>
	</table>
</div>
<br>

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
							<input id="edit_penalty_DAY" name="penalty_DAY" class="form-control" style="padding: 2px 0px 0px 10px" type="text" maxlength="2"  onblur="checkIntOnBlurEmpty(this);"/>
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
							<input id="elec_penalty_DESC_TEXT" class="form-control" style="padding: 2px 0px 0px 10px" type="text" title="滯納金" onblur="checkIntOnBlurEmpty(this);">
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
					<th style="width: 8%;">銀行</th>
					<th style="width: 8%;">分行</th>
					<th style="width: 11%;">帳號</th>
					<th style="width: 6%;">分攤比</th>
					<th style="width: 8%;">金額</th>
				<!-- 	<th style="width: 8%;">營業稅</th> -->
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
					<th style="width: 20%;">站台編號</th>
					<!-- <th style="width: 20%;">租金起算日</th> -->
					<th style="width: 20%;">終止日</th>
					<th style="width: 20%;">分攤項目</th>
					<th style="width: 20%;">分攤比率</th>
				</tr>
			</thead>
			<tbody id="tbodySite_elec" />
		</table>

	</fieldset>
</div>
<!-- 用電站台資訊   END -->
</form>

<script type="text/javascript">
	$(document).ready(function() {
		 $("#siteElec_form").bind("reset",function(){
				$("#tbodySite_elec").html('');
				$("#elecTbody").html('');
		   });
		   
		//用電起訖
/* 		$( "#edit_elec_BEGIN_DATE" ).datepicker({
		    onClose: function( selectedDate ) {
		        $( "#edit_elec_END_DATE" ).datepicker( "option", "minDate", selectedDate );
		    }
		});
		$( "#edit_elec_END_DATE" ).datepicker({
		    onClose: function( selectedDate ) {
		        $( "#edit_elec_BEGIN_DATE" ).datepicker( "option", "maxDate", selectedDate );
		    }
		}); */

		addClassSEL("elecTB");

		$("#newLocElecButton").click(function() {
		
			clear_form_elements("elec_editingArea_table");
			$('#edit_CHRG_MODE_C_tr').hide();
			$('#edit_CHRG_MODE_D_tr').hide();
			$('#edit_CHRG_MODE_E_tr').hide();
			
			$('#edit_elec_ADDR').val($('#leaseDefaultAddress_Elec').val());
			$('#edit_elec_ADDR_STD').val($('#leaseDefaultAddress_STD_Elec').val());
		
			$('#edit_elec_MODE').val($('#edit_elec_MODE option:first-child').val());
			$('#edit_elec_MODE').select2("val",$('#edit_elec_MODE option:first-child').val()); 
		
			$("#location_elec_tBody tr").removeClass('selected');
			
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

		$("#saveLocElecDBButton").click(function() {
			/* if ($("#elecTbody tr").length==0) {
				alert("請輸入付款資訊");
				return false;
			}else  */if($("#location_elec_tBody tr").length==0){
				alert("請輸入電錶資訊");
				return false;
			}else if($("#location_elec_tBody tr").hasClass("selected")){

				alert("請先儲存電錶資訊");
				return false;

			}else { 

				var checkFlag = checkBankNecessary("elecTbody", "payment_Mode", "UNIT_CODE", "SUB_UNIT_CODE","ACCOUNT_NO");
				if(!checkFlag){
					return checkFlag;
				}

				
				
				 $.ajax({
					url : CONTEXT_URL + "/ls001M/saveLeaseLocationElec",
					type : 'POST',
					dataType : "json",
					data : $("#siteElec_form").serialize(),
					async : false,
					success : function(data) {
						alert(data.msg);
						getLsDomainMoneyList();
					}
				}); 
			}
		
		});
		
		
		$("input[name='elec_penalty_DESC_RADIO']:checkbox").click(function() {
			if ($(this).is(":checked")) {
				var group = "#siteElec_form input:checkbox[name='" + $(this).prop("name") + "']";
				$(group).prop("checked", false);
				$(this).prop("checked", true);
			} else {
				$(this).prop("checked", false);
			}
		});


		
		
	});
	

	function updateElecAddrButton(){
		callBackAddrField = "edit_elec_ADDR";
		callBackAddrStdField = "edit_elec_ADDR_STD";
		openLessorAddressProcess("receviceAddressData");
	}
	function showElecData(theField){

		$("#newLocElecButton").trigger("click");
		
		$("#location_elec_tBody tr").removeClass('selected');
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
			if($('#edit_elec_BEGIN_DATE').val()!=''){
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
			}
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
		$("#location_elec_tBody tr").each(function() {
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
		
		
		row1+='<td>'+$("#edit_elec_MODE").find("option:selected").text()+'</td>';
		row1+='<td><span name="energy_METER_span">'+$("#edit_energy_METER").val()+'</span></td>';
		
		row1+='<td>'+((typeof(tempValue)!='undefined')?tempValue:"&nbsp;")+'</td>';
		row1+='<td>'+$("#edit_elec_BEGIN_DATE").val()+'~'+$("#edit_elec_END_DATE").val()+'</td>';
		row1+='<td>'+$("#edit_elec_ADDR").val()+'<input type="hidden" name="elecJsonValue" value=\''+jsonString+'\'></td>';
		
		if(selectedRow==null || typeof(selectedRow)=='undefined'){
			row1+='</tr>';
			$("#location_elec_tBody").append(row1);
		}else{
		//	selectedRow.html('');
			selectedRow.html(row1);
		}
		
		$("#newLocElecButton").trigger("click");

		
	}
	
</script>
