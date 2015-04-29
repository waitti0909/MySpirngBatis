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
						<button class="btn btn-primary btn-label" type="button"
							id="addSiteBtn" style="margin-left: 15px">新增站台</button>
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
<div id= "downSiteDIV" class="form-group" style="margin-top: 10px">
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
		<select id="itemSEL" class="populate placeholder" name="" >
			<c:forEach items="${itemSEL}" var="item">
				<option value="${item.key}">${item.value}</option>
			</c:forEach>
		</select>
	</div>

	<div id="printTypeDiv">
		<label class="col-sm-1 control-label">套表格式 :</label>
		<div class="col-sm-3">
			<select id="tbStyleSEL" class="populate placeholder" name="">
			</select>
		</div>
	</div>
</div>
<!-- 類別、項目 END -->


<hr>
<div id="" style="padding: 10px 10px 0px 10px">

		<div id="tabs">
			<ul>
				<li><a href="#1M3tabs-1">租金</a></li>
				<li><a href="#1M3tabs-2">用電</a></li>
			</ul>
	
			<div id="1M3tabs-1">
				<jsp:include page="/WEB-INF/views/ajaxPage/ls/LS001M3_Rent.jsp"></jsp:include>
			</div>
	
			<div id="1M3tabs-2">
				<jsp:include page="/WEB-INF/views/ajaxPage/ls/LS001M3_Elec.jsp"></jsp:include>
			</div>
		</div>
</div>
<form>
	<!--                    暫存用值                     STR        -->
	<input type="hidden" id="editLocId" name="editLocId">
	<input type="hidden" id="editAppSeq" name="editAppSeq">
	<input type="hidden" id="editLsNo" name="editLsNo">
	<input type="hidden" id="editAppMode" name="editAppMode">
	<input id="LessorNames_site"  type="hidden" >
	<input id="LessorRelationType"  type="hidden">
	<input id="Lessor_allPaymentMode"  type="hidden">
	<!--                    暫存用值                     END        -->
	<!--                    租金暫存                     STR        -->
	<input id="rentStrDate"  type="hidden">
	<input id="rentEndDate"  type="hidden">
	<input id="rentRent"  type="hidden">
	<input id="rentPayment"  type="hidden">
	<input id="rentSite"  type="hidden">
	<!--                    租金暫存                     END        -->
	<!--                    用電暫存                     STR        -->
	<input id="elecData"  type="hidden">
	<input id="elecPayment"  type="hidden">
	<input id="elecSite"  type="hidden">
	<!--                    用電暫存                     END        -->
	
</form>

<script type="text/javascript">
	$(document).ready(function() {
		var appMode =  $("#appMode").text();
		$('#editAppSeq').val($("#appSeq").text());
		$('#editLsNo').val($("#lsNo").text());
		$('#editAppMode').val($("#appMode").text());
		
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
		
		$('#selectDomain').change(function(){
			initData = {
					lsNo : $("#lsNo").text(),
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
						for(var i = 0 ;i<data.rows.length ; i++){
							siteValue += '<tr>';
							siteValue += '<td class="tbodytd">'+data.rows[i].location_ID+'</td>';
							siteValue += '<td class="tbodytd">'+data.rows[i].loc_NAME+'</td>';
							siteValue += '<td class="tbodytd">'+data.rows[i].addr+'</td>';
							siteValue += '<td class="tbodytd"><button class="btn-primary" type="button" id="" onclick="downloadPicFile('+data.rows[i].zip_CODE+')">檢視</button></td>';
							siteValue += '</tr>';
						}
						$('#siteTbody').append(siteValue);
					}
				}
			}); 
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
			if($('#editLocId').val() != ''){
				setRentData();
				setElecData();
			}
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
				
				$('#editLocId').val($.trim($(this).children().first().text()));
				$('#siteTbody').find('tr').each(function(){
					$(this).removeClass('selected');
				}); 
				$(this).addClass('selected');
				$('#tabs').show();
				setRentData();
				setElecData();
			}
		});
		
		
		
		$('#itemSEL').trigger('change');
		
		
		$("#tabs").tabs();
		//類別
		$('#selType').val('${selType}');
		selText=$("#selType").find("option:selected").text();
		//站點資訊
		siteTBFormat();
		
		//按鈕呈現
		$('#buttonDiv').html("");
		var str = '<c:forEach items="${button}" var="data"><button class="btn btn-primary btn-label" type="button" id="${data.key}" style="margin-right: 5px; display: none;" >${data.value}</button></c:forEach>';
		$('#buttonDiv').append(str);
		$('#btn_1M3Save_Draft').show();
		$('#btn_1M3Print').show();
		$('#btn_1M3PrintRent').show();
		
		//控制畫面是否開啟
		initState(appMode);
		
		//租金、用電tab切換控制上方紐
		$("a[href=#1M3tabs-1]").click(function(){
			$('#btn_1M3PrintRent').show(); //列印租金起付確認書					
		});
		
		$("a[href=#1M3tabs-2]").click(function(){
			$('#btn_1M3PrintRent').hide();
		});
		
		$('#tabs').hide();
		
		$('#addSiteBtn').click(function(){
			if($("#selectDomain").val()==''){
				alert("請先選擇區域");
				return false;
			}
			openSiteDialogFrame('selectSitePage', 'receviceSiteData', '');	
		});
	});
	
	
	function buildPaymeData(tbLsLocPaymentList,isElec){
        var row1="";
    	for(var i = 0 ;i<tbLsLocPaymentList.length;i++){
			 row1 += '<tr><td><select id="" class="" name="PAYMENT_ITEM">';
			
			if(isElec){
				if(tbLsLocPaymentList[i].payment_ITEM == 'E'){
				    row1 +='<option value="E" selected>電費</option><option value="ED">電費押金</option>';
				}else{
					row1 +='<option value="E" >電費</option><option value="ED" selected>電費押金</option>';
				}
    		}else{
    			if(tbLsLocPaymentList[i].payment_ITEM == 'R'){
    			    row1 +='<option value="R" selected>租金</option><option value="RD">租金押金</option>';
    			}else{
    				row1 +='<option value="R" >租金</option><option value="RD" selected>租金押金</option>';
    			}
        	}
			row1 +='</select></td>';
			var lessorNamesValue =  $('#LessorNames_site').val().split(',');
			row1 += '<td><select id="loc_pay_lessor" onchange="changeLessorPaymentMode(this);" name="LESSOR_ID">';
			for(var j = 0 ;j<lessorNamesValue.length;j++){
				var tmpLessor = lessorNamesValue[j].split(":");
				if(tmpLessor[0] == tbLsLocPaymentList[i].lessor_ID){
					row1 +='<option value="'+tmpLessor[0]+'" selected>'+tmpLessor[1]+'</option>';
				}else{
					row1 +='<option value="'+tmpLessor[0]+'">'+tmpLessor[1]+'</option>';
				}
				
			}
			row1 +='</select><input type="hidden" name="LESSOR_NAME"  id="loc_pay_lessor_name" value="'+tbLsLocPaymentList[i].lessor_NAME+'"></td>';
			row1 += '<td><input class="form-control" name="RECIPIENT_ID" type="text" style="width: 100%;" value="'+tbLsLocPaymentList[i].recipient_ID+'"></td>';
			row1 += '<td><input class="form-control" name="RECIPIENT_NAME" type="text" style="width: 100%;" value="'+tbLsLocPaymentList[i].recipient_NAME+'"></td>';
			row1 += '<td><select id="locpayment_lessor_relation"  name="LESSOR_RELATION">';
			var typeValue =  $('#LessorRelationType').val().split(';');
			for(var j = 0 ;j<typeValue.length;j++){
				var nameCode = typeValue[j].split(',')
				if(nameCode[0] == tbLsLocPaymentList[i].lessor_RELATION){
					row1 += '<option value="'+nameCode[0]+'" selected>'+nameCode[1]+'</option>';
				}else{
					row1 += '<option value="'+nameCode[0]+'">'+nameCode[1]+'</option>';
				}
			}
			row1 += '</select></td>';
			
			row1 += "<td><input type='text' class='form-control' readOnly id='payment_Mode_text_id' name='payment_Mode_text' value='"+getLessorPaymentModeText(tbLsLocPaymentList[i].payment_MODE)+"' >";
			
			row1 += '<input type="hidden" name="payment_Mode"  value="'+tbLsLocPaymentList[i].payment_MODE +'"></td>';
			row1 += '<td><select  name="UNIT_CODE"onchange="changeBanksSub(this);" >'+buildBanksOptions(tbLsLocPaymentList[i].unit_CODE)+'</select></td>';

			row1 += '<td><select name="SUB_UNIT_CODE" >"'+buildSubBanksOptions(tbLsLocPaymentList[i].unit_CODE, tbLsLocPaymentList[i].sub_UNIT_CODE) +'</select></td>';

			row1 += '<td><input class="form-control" name="ACCOUNT_NO"  type="text" style="width: 100%;" value="'+tbLsLocPaymentList[i].account_NO+'"></td>';
			row1 += '<td><input class="form-control" name="PAY_ALOC" onblur="checkIntOnBlur(this);" max="3" type="number" style="width: 100%;padding: 2px 0px 0px 10px" value="'+tbLsLocPaymentList[i].pay_ALOC+'"></td>';
			row1 += '<td><input class="form-control" name="PAY_AMT" onblur="checkIntOnBlur(this);" max="9" type="number" style="width: 100%;padding: 2px 0px 0px 10px" value="'+tbLsLocPaymentList[i].pay_AMT+'">';		
			row1 += '<input name="BUSINESS_TAX" type="hidden"  value="'+tbLsLocPaymentList[i].business_TAX+'"></td></tr>';
	
 	   }	
  	   return row1;
    }

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
		console.log("333="+$(theField).parent().find("input[name='LESSOR_NAME']").val());
		console.log("222="+$(theField).find("option:selected").text());
	}
	
	function setTbLsSite(tbLsSiteDTOList){
		var siteDateValue = '';
		for(var i = 0 ; i<tbLsSiteDTOList.length;i++){
 			siteDateValue += "<tr>";
 			siteDateValue += "<td>"+tbLsSiteDTOList[i].bts_SITE_ID+"<input type='hidden' name='BTS_SITE_ID' value='"+tbLsSiteDTOList[i].bts_SITE_ID+"'>";
			siteDateValue += "<input type='hidden' name='SITE_ID' value='"+tbLsSiteDTOList[i].site_ID+"'></td>";
			siteDateValue += "<td>"+dateFormat(tbLsSiteDTOList[i].ls_E_DATE)+"</td>";
			siteDateValue += "<td>"+(tbLsSiteDTOList[i].expn_TYPE == 'E'?"用電":"租金")+"<input type='hidden' name='EXPN_TYPE' value='"+tbLsSiteDTOList[i].expn_TYPE+"'></td>";
			siteDateValue += "<td>"+tbLsSiteDTOList[i].aloc_RATIO+"%<input type='hidden' name='ALOC_ID' value='"+tbLsSiteDTOList[i].aloc_ID+"'></td>";
			siteDateValue += "</tr>";
		}
		return siteDateValue;
	}
	
	function getLessorPaymentModeText(pCode){
		<c:forEach items="${paymentMode}" var="data">
		if('${data.CODE}'==pCode){	 
			return "${data.NAME}"; 
		}
		</c:forEach>
		return "";
	}
	
	function changeBanksSub(theField){
		var tempSelect = $(theField).parent().parent().find("select[name='SUB_UNIT_CODE']");
		tempSelect.empty();
		
		tempSelect.append(buildSubBanksOptions(theField.value,""));
		
		tempSelect.val("");
		tempSelect.select2({ allowClear: true });
	}
	
	
	function dateFormat(date){
		if(date != null && date != ''){
		var day = new Date(date);
		return day.getFullYear()+"/"+(day.getMonth()+1)+"/"+day.getDate();
		}else{
			return '';
		}
	}
	
	var countSite = 1;
	function receviceSiteData(siteObjects) {
		var obj = JSON.parse(siteObjects);
		var flag = false;
	
		if(obj.siteObjs[0].maintArea!='' && $('#selectDomain').val()==$.trim(obj.siteObjs[0].maintArea.charAt(0))){
			flag = true;
		}
		
		if(flag){
			$("#siteTbody tr").each(function() {
				 if($.trim($(this).children().first().text()) == obj.siteObjs[0].location_ID){
					flag = false;
				}; 
			});
			if(flag){
				 if(obj.siteObjs[0].location_ID!=null && obj.siteObjs[0].location_ID!=''){
					 var data = {
							lsNo : $("#lsNo").text(),
							lsVer : $("#lsVer").text(),
							locId : obj.siteObjs[0].location_ID,
							domain : obj.siteObjs[0].maintArea,
							zipCode : obj.siteObjs[0].locZip,
							locAddr : obj.siteObjs[0].locAddr,
							locName : obj.siteObjs[0].locName
						};
					$.ajax({			
						url : CONTEXT_URL + "/ls/LS001M_Site/saveNewLeaseLocation",		
						data: data,
						type : "POST",
						dataType : 'json',
						success : function(data) {
							if(data.success){	
								addSiteRow(data.msg);
							}
						}
					});   
				}else{
					alert("Location不得為空");
				}
				
			}else{
				alert("該站點已經存在");
			}
		}else{
			alert("該站點不在區域內");
		}
	}
	
	function addSiteRow(data){
		var row1 = '';
		var locationJson = JSON.parse(data);
			for(var i = 0;i<locationJson.length;i++){
				row1+='<tr id="tr'+countSite+'"><td class="tbodytd" style="width: 15%;">'+locationJson[i]['LOCATION_ID'];
				row1+='<input type="hidden" name="site_Row_lsVer" value="'+locationJson[i]['LS_VER']+'">';
				row1+='</td>';
				row1+='<td class="tbodytd" style="width: 15%;">'+locationJson[i]['LocName']+'</td>';
				row1+='<td class="tbodytd" style="width: 45%;">'+locationJson[i]['Addr']+'</td>';
				row1+='<td class="tbodytd" style="width: 15%;">'+locationJson[i]['HavingEffectiveLease']+'</td>';
				row1+='<td class="tbodytd" style="width: 10%;"><button class="btn-primary" type="button" id="" onclick="downloadPicFile('+locationJson[i]['ZIP']+')">檢視</button></td></tr>';
				countSite++;
			}
		$("#siteTbody").prepend(row1);									
	}
	
	//控制畫面是否開啟
	 function initState(appMode){
			if(appMode=='VIEW'){
				$("#topSiteDIV select").prop("disabled",true);
				$("#topSiteDIV input").prop("disabled",true);
				
				$("#downSiteDIV select").prop("disabled",true);
				$("#downSiteDIV input").prop("disabled",true);
				
				$('#btn_1M3Save_Draft').prop("disabled",true);
				$('#btn_1M3Print').prop("disabled",true);
				$('#btn_1M3PrintRent').prop("disabled",true);
				$("#btn_1M3Save_Draft").prop("disabled", true);
			}
		}
</script>