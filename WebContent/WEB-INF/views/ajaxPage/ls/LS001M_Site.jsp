<%@page import="com.foya.noms.enums.LsEnumCommon"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<input id="site_LsNo" name="lsNo" type="hidden" value="${lsNo}">
<input id="site_LsVer" name="site_LsVer" type="hidden" value="${lsVer}"> 
<input id="site_LsType" name="site_LsType" type="hidden" value="${lsType}"> 
<input id="site_AppSeq" name="site_AppSeq" type="hidden" value="${appSeq}"> 

<input id="LessorNames_site" name="" type="hidden" value="">
<input id="Lessor_allPaymentMode" name="" type="hidden" value="">
<input id="LessorRelationType" name="" type="hidden" value="" >
			
			
<table style="width: 100%; margin-top: 10px">
	<tr>
		<td style="width: 8%"><label class="col-sm-12 control-label"><span
				class="s">*</span>區域 :</label></td>
		<td style="width: 46%">
			<div class="col-sm-12" style="padding-right: 0px">
				<div style="width: 45%; height: auto; float: left; display: inline">
					<select id="siteDomainList" class="populate placeholder" name="">
							<option value="" selected >請選擇</option>
						<c:forEach items="${siteDomain}" var="item">
							<option value="${item.ID}"  >${item.NAME}</option>
						</c:forEach>
					</select>
				</div>
				<div
					style="width: 45%; height: auto; float: left; display: inline; margin-left: 10px">

					<button class="btn btn-primary btn-label" type="button"
						id="addSite">新增站點</button>
					<button class="btn btn-primary btn-label" type="button"
						id="delSite">刪除站點</button>
				</div>
			</div>
		</td>
		<td nowrap="nowrap" style="width: 6%"><label
			class="col-sm-12 control-label">建立人員 :</label></td>
		<td style="width: 40%">
			<div class="col-sm-10">
			<input type="hidden" id="app_USER_DEPT" name="APP_USER_DEPT" readonly="readonly" value="${loginUser.deptId}">
			<input type="hidden" id="app_USER" name="APP_USER" class="form-control" value="${loginUser.userId}">
			<c:out value="${loginUser.deptName}"/>&nbsp;<c:out value="${loginUser.chName}"/>
				<%-- <div style="width: 60%; height: auto; float: left; display: inline">
					<select id="" class="populate placeholder" name="">
						<option value="">--請選擇--</option>
						<option value="" selected>北一區專案部</option>
					</select>
				</div>
				<div
					style="width: 40%; height: auto; float: left; display: inline; padding-left: 5px">
					<select id="" class="populate placeholder" name="">
						<option value="">--請選擇--</option>
						<option value="" selected>張小明</option>
					</select>
				</div> --%>
			</div>
		</td>
	</tr>


	<tr>
		<td nowrap="nowrap" valign='top' style="padding-top: 10px"><label
			class="col-sm-12 control-label">站點 :</label></td>
		<td colspan="3" style="padding: 18px 10px 0px 10px;">
			<div>

				<table id="siteTB" class="scrollTable"
					style="table-layout: fixed; word-break: break-all; word-wrap: break-word; width: 100%">
					<thead style="background-color: #f0f0f0;">
						<tr>
							<th class="theadth" style="width: 15%;">站點編號</th>
							<th class="theadth" style="width: 15%;">站點名稱</th>
							<th class="theadth" style="width: 45%;">地址</th>
							<th class="theadth" style="width: 15%;">有生效約</th>
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

<jsp:include
			page="/WEB-INF/views/ajaxPage/ls/LS001M_SiteSub.jsp">
	<jsp:param name="lsNo" value="${lsNo}"/>
    <jsp:param name="lsVer" value="${lsVer}"/>	
    
</jsp:include>


<script src="../resources/plugins/scrolltable/jquery.scrolltable.js"></script>

<script type="text/javascript">

	$(document).ready(function() {
		
		$("#addSite").prop("disabled", (btnType == "view" || '${lsType}' == "1"));
		
		$("#delSite").prop("disabled", (btnType == "view" || '${lsType}' == "1"));

		$("#siteDomainList").change(function(){	
			$('#siteTabs').hide();	
		   if($(this).val()==''){ 
			   $("#siteTbody").html('');
				//$('#siteRent_form').trigger('reset');
			/* 	$('#siteTabs').hide(); */
			    return false;	
		   }
		   var initData = {
				lsNo : $("#site_LsNo").val(),
				lsVer : $("#site_LsVer").val(),
				lsType : $("#site_LsType").val(),
				appSeq : $("#site_AppSeq").val(),
				domain: $("#siteDomainList").val()
			};
		var ajaxURL = 	CONTEXT_URL + "/ls/LS001M_Site/getLeaseLocationList";
	/* 	if('${isContinue}'=='true'){
			 ajaxURL = 	CONTEXT_URL + "/ls/LS001M_Site/getLeaseAddedLocationList";
		}	 */
		$.ajax({			
			url :ajaxURL,		
			data: initData,
			type : "POST",
			dataType : 'json',
			success : function(data) {
				if(data.success){	
					$("#siteTbody").html('');		
					addSiteRow(data.msg);
				}
			}
		}); 
		});

		
		$('#siteTB').scrolltable({
			stripe : true,
			oddClass : 'odd',
			maxHeight : 100
		});
		
		
		$('#addSite').click(function(){
			if($("#siteDomainList").val()==''){
				alert("請先選擇區域");
				return false;
			}
			openSiteDialogFrame('selectSitePage', 'receviceSiteData', '');	
		});
		
		$('.st-body-table tbody').on( 'click', 'tr', function () {
			$('.st-body-table').find('tr').each(function(){
				$(this).removeClass('selected');
			});
				$(this).addClass('selected');

				if(${lsMain.hasRentCat()}){
					$("#siteTabs-1_li").show();	
				}
				if(${lsMain.hasElecCat()} ){
					$("#siteTabs-2_li").show();	
				}
				if(${lsMain.hasExchangeCat()} ){
					$("#siteTabs-3_li").show();	
				}
			
				
				var firstCateId = $("#siteTabs li").first().prop("id");
				console.log("firstCateId="+firstCateId);
			
				if(${lsMain.hasRentCat()} && firstCateId=='siteTabs-1_li'){
					$('#siteRent_form').trigger('reset');
					ajaxRentData();
				}else if(${lsMain.hasElecCat()} && firstCateId=='siteTabs-2_li'){
					$('#siteElec_form').trigger('reset');
					ajaxElecData();

				}else if(${lsMain.hasExchangeCat()} &&firstCateId=='siteTabs-3_li'){
					//可允許無基站分攤比設
					$('#siteExch_form').trigger('reset');
					ajaxExchData();
				}else{

					alert("請設定合約有無租金電費或是屬於資源交換");
					return false;
				}
				$("#siteTabs").tabs({active:0});
				
		});

		
		$('#delSite').click(function(){		
			$("#siteTbody tr").each(function() {
				if($(this).hasClass('selected')){
					if(confirm("確定刪除該站點資料嗎？")){
						 var trRow = $(this);
						 var data = {
									lsNo : $("#site_LsNo").val(),
									lsVer : $("#site_LsVer").val(),
									locId : $.trim($(this).children().first().text())
								};
						 $.ajax({			
							url : CONTEXT_URL + "/ls/LS001M_Site/deleteLeaseSite",		
							data: data,
							type : "POST",
							dataType : 'json',
							success : function(data) {
								if(data.success){	
						 			trRow.remove();
									alert(data.msg);
									$("#siteTabs").hide();
								}
							}
						});  
					}
				}
			});
		});
		
	});	
	var countSite = 1;
	function receviceSiteData(siteObjects) {
		var obj = JSON.parse(siteObjects);
		var flag = false;
	
		if(obj.siteObjs[0].maintArea!='' && $('#siteDomainList').val()==$.trim(obj.siteObjs[0].maintArea.charAt(0))){
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
							lsNo : $("#site_LsNo").val(),
							lsVer : $("#site_LsVer").val(),
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
	
	function dateFormat(date){
		if(date != null && date != ''){
		var day = new Date(date);
		return day.getFullYear()+"/"+(day.getMonth()+1)+"/"+day.getDate();
		}else{
			return '';
		}
	}

	
	function ajaxRentData(){
		if($("#tbodySite_rent tr").length>0){
			return false;
		}
		var loc_Id=$("#siteTbody tr").closest(".selected").first().children().first().text();
		
	 	var data = {
				lsNo : $("#site_LsNo").val(),
				lsVer: $("#site_LsVer").val(),
				lsType: $("#site_LsType").val(),
				appSeq : $("#site_AppSeq").val(),
				locId : loc_Id
			}; 
		$.ajax({			
			url : CONTEXT_URL + "/ls/LS001M_Site/getLocationSitePay4Rent",		
			data: data,
			type : "POST",
			dataType : 'json',
			success : function(data) {
				$('#siteRent_form').trigger('reset');		
				if(data.success){
					//alert(dateFormat(data.row.pay_BEGIN_DATE));editPldgAmt
					setRentData(data.row);
					$('#siteTabs').show();
				}else{
					//$('#siteRent_form').trigger('reset');
					alert(data.msg);
					$('#siteTabs').hide();
				}
			}
		}); 
	}
	function ajaxElecData(){
	
		var loc_Id=$("#siteTbody tr").closest(".selected").first().children().first().text();
		
	 	var data = {
				lsNo : $("#site_LsNo").val(),
				lsVer : $("#site_LsVer").val(),
				appSeq : $("#site_AppSeq").val(),
				lsType : $("#site_LsType").val(),
				locId : loc_Id
			}; 
		$.ajax({			
			url : CONTEXT_URL + "/ls/LS001M_Site/getLocationSitePay4Elec",		
			data: data,
			type : "POST",
			dataType : 'json',
			success : function(data) {
				$('#siteElec_form').trigger('reset');
				if(data.success){
					//alert(dateFormat(data.row.pay_BEGIN_DATE));editPldgAmt
					setElecData(data.row);
					$('#siteTabs').show();
				}else{
					alert(data.msg);
					$('#siteTabs').hide();
				}
			}
		}); 

	}
	
	
	function setRentData(data){
		
		$('#siteRent_form').trigger('reset');
		
		$('#priceZipCode').val(data.zip_CODE);
		$('#location_id_Rent').val(data.location_ID);
		$('#location_Ls_No').val(data.ls_NO);
		$('#location_Ls_Ver').val(data.ls_VER);
		$('#leaseEndDate_Rent').val(dateFormat(data.ls_E_DATE));//ls_e_date		
		
		$('#AREA_AVG').val(data.area_AVG);
		$('#LessorNames_site').val(data.lessorNames);//出租人
		$('#Lessor_allPaymentMode').val(data.allLessorPaymentMode);//出租人付款方式		
		$('#LessorRelationType').val(data.lsLeaseRelation);//與出租人關係
		$('#siteRentDateS').val(dateFormat(data.eff_DATE));//承租起
		$('#siteRentDateE').val(dateFormat(data.end_DATE));//承租迄
		
		$('#editPayBeginDate').val(dateFormat(data.pay_BEGIN_DATE));//租金起算日
		$('#editRentAmt').val(data.rent_AMT);//租金
	
		$('#LOCATION_NEXT_INCR').val(dateFormat(data.next_INCR));//下次調整
		
		if(data.elec_PLUS == 'Y'){
			$('#editElecPlus').prop("checked", true);//電費
		}
		if(data.bldg_TYPE!=null){
			var bldgType = data.bldg_TYPE.split(",");
			for(var i = 0 ;i<bldgType.length;i++){
				$('#editBldgType'+bldgType[i]).prop("checked", true);
			}
		}
		$('#editPldgAmt').val(data.pldg_AMT);//押金
		$('#editPldg_OUT').val(data.pldg_IN);//移入押金金額
		$('#editIncrMonth').val(data.incr_MONTH);//調幅月
		$('#editIncrRange').val(data.incr_RANGE);//調幅金額
		if(data.incr_MODEL=='%'){
			$('#LOCATION_INCR_MODEL_percent').attr("checked",true);//調幅方試
		}else if(data.incr_MODEL=='元'){
			$('#LOCATION_INCR_MODEL_dallar').attr("checked",true);//調幅方試
		}else{
			$('#LOCATION_INCR_MODEL_percent').attr("checked",false);//調幅方試
			$('#LOCATION_INCR_MODEL_dallar').attr("checked",false);//調幅方試
		}
		$('#editIncrBeginM').val(data.incr_BEGIN_M);//調幅起始月
		$('#editIncrBeginD').val(data.incr_BEGIN_D);//調幅起始日
		$('#editBldgSm').val(data.bldg_SM);//坪數
		if(data.est_COST==null){
			//$('#editEstCost').text(data.est_COST);//預估成本
			data.est_COST="0";
		}
		/* else{
			$('#editEstCost').text("0");//預估成本
		} */
		$('#editEstCost').text(data.est_COST);//預估成本
		$('#EST_COST_HIDDEN').val(data.est_COST);
		
		if(data.rent_AMT!=null){
			$('#editEstRentAmt').text(data.rent_AMT);//預估租金
		}else{
			$('#editEstRentAmt').text("0");//預估租金
		}
		if(data.est_ELEC!=null){
			$('#editEstElec').val(data.est_ELEC);//預估電費
		}
		if(data.est_DT!=null){
			$('#editEstDt').val(data.est_DT);//預估傳輸
		}
		if(data.est_HOA!=null){
			$('#editEstHoa').val(data.est_HOA);//預估大樓管理費
		}
		if(data.est_OTHERS!=null){
			$('#editEstOthers').val(data.est_OTHERS);//預估其他
		}
		if(data.est_INCOME!=null){
			$('#editEstIncome').val(data.est_INCOME);//預估話務量
		}
		if(data.est_ERL!=null){
			$('#editEstErl').val(data.est_ERL);//Erl/月
		}
		if(data.org_RENT!=null){
			$('#editOrgRent').text(data.org_RENT+"元");//續/換約原租金
		}
		if(data.est_ERL!=null){
			$('#editCpRise').text(data.cp_RISE+"%");//新租金與舊約漲幅
		}
		if(data.reduce_CNT!=null){
			$('#editReduceCnt').val(data.reduce_CNT);//歷史減租次數
		}
		if(data.reduce_AMT!=null){
			$('#editReduceAmt').val(data.reduce_AMT);//歷史總減租金額／調幅
		}
		if(data.main_DOMAIN!=null){
			$('#editMainDomain').val(data.main_DOMAIN);//涵蓋重點區域
		}
		if(data.improve!=null){
			$('#editImprove').val(data.improve);//改善方案
		}
		//下次調整日期
		
		
		//組站台資訊資料
		
		$("#tbodySite_rent").append(buildSiteData(data.tbLsSiteDTOList));
		
		//組付款資訊資料
		$("#rentTbody").append(buildPaymeData(data.tbLsLocPaymentList,false));
		setepAREA_AVG();
		rentSelect2Test();
		$("#siteRent_form :input").prop("disabled", (btnType == "view"));
		if ('${lsType}' == "1") {
			$("#siteRent_form :input").prop("disabled", true);
			$(".contiueOnly").prop("disabled", false);
		}
		$("#saveLocationRent").prop("disabled", (btnType == "view"));

		if(data.elec_PLUS == 'Y'){
			$("#siteTabs-2_li").hide();	
		}
		
	}


    function buildSiteData(tbLsSiteDTOList){
    	var siteVal ='';
		for(var i = 0 ;i<tbLsSiteDTOList.length;i++){
			siteVal += "<tr>";
			siteVal += "<td>"+tbLsSiteDTOList[i].bts_SITE_ID+"<input type='hidden' name='BTS_SITE_ID' value='"+tbLsSiteDTOList[i].bts_SITE_ID+"'>";
			//siteVal += "<td>"+dateFormat(tbLsSiteDTOList[i].pay_BEGIN_DATE)+"<input type='hidden' name='SITE_ID' value='"+tbLsSiteDTOList[i].site_ID+"'></td>";
			siteVal += "<input type='hidden' name='SITE_ID' value='"+tbLsSiteDTOList[i].site_ID+"'></td>";
			siteVal += "<td>"+dateFormat(tbLsSiteDTOList[i].ls_E_DATE)+"</td>";
			siteVal += "<td>"+(tbLsSiteDTOList[i].expn_TYPE == 'E'?"用電":"租金")+"<input type='hidden' name='EXPN_TYPE' value='"+tbLsSiteDTOList[i].expn_TYPE+"'></td>";
			siteVal += "<td>"+tbLsSiteDTOList[i].aloc_RATIO+"%<input type='hidden' name='ALOC_ID' value='"+tbLsSiteDTOList[i].aloc_ID+"'></td>";
			siteVal += "</tr>";
		}
		return siteVal;
    }

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
	
	function setElecData(data){
		$('#siteElec_form').trigger('reset');		
		$('#newLocElecButton').trigger('click');		
		
		$('#edit_location_id_elec').val(data.location_ID);
		
		$('#location_Ls_No_elec').val(data.ls_NO);
		$('#location_Ls_Ver_elec').val(data.ls_VER);
		$('#leaseEndDate_Elec').val(dateFormat(data.ls_E_DATE));//ls_e_date		
		$('#leaseStartDate_Elec').val(dateFormat(data.ls_S_DATE));//ls_e_date		

		$('#edit_elec_END_DATE').val(dateFormat(data.ls_E_DATE));//ls_e_date		
		$('#edit_elec_BEGIN_DATE').val(dateFormat(data.ls_S_DATE));//ls_e_date		

		
		$('#leaseDefaultAddress_Elec').val((data.ls_ADD));//ls_e_date		
		$('#leaseDefaultAddress_STD_Elec').val((data.ls_ADD_STD));//ls_e_date		
		
		$('#edit_elec_ADDR').val($('#leaseDefaultAddress_Elec').val());
		$('#edit_elec_ADDR_STD').val($('#leaseDefaultAddress_STD_Elec').val());
		
		$('#LessorNames_site').val(data.lessorNames);//出租人
		$('#Lessor_allPaymentMode').val(data.allLessorPaymentMode);//出租人付款方式		

		$("#location_elec_tBody").html("");
		var elecList = data.lsLocElecList;
		for(var i = 0 ;i<elecList.length;i++){
			var jsonString=  JSON.stringify(elecList[i]);
			console.log("jsonString="+jsonString);
			var row1='';
			row1+='<tr onclick="showElecData(this);">';
			row1+='<td>'+$("#edit_elec_MODE option[value='"+elecList[i].elec_MODE+"']").text()+'</td>';
			row1+='<td>'+elecList[i].energy_METER+'</td>';
			row1+='<td>'+(elecList[i].chrg_MODE!=null?elecList[i].chrg_MODE:"")+'</td>';
			row1+='<td>'+elecList[i].elec_BEGIN_DATE+'~'+elecList[i].elec_END_DATE+'</td>';
			row1+='<td>'+elecList[i].elec_ADDR+'<input type="hidden" name="elecJsonValue" value=\''+jsonString+'\'></td></tr>';
			$("#location_elec_tBody").append(row1);
		}
		
		
		//組站台資訊資料
		$("#tbodySite_elec").append(buildSiteData(data.tbLsSiteDTOList));
		
		//組付款資訊資料
		$("#elecTbody").append(buildPaymeData(data.tbLsLocPaymentList,true));
		$('#edit_location_id_elec').val(data.location_ID);

		var elecRangeSettingList = data.tbLsElecRanges;
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
		rentSelect2Test();
		$("#siteElec_form :input").prop("disabled", (btnType == "view"));
		
		if ('${lsType}' == "1") {
			$("#siteElec_form :input").prop("disabled", true);
		}
		$("#saveLocElecDBButton").prop("disabled", (btnType == "view"));
 	   	
	}

   
		function rentSelect2Test() {
			$("select").select2();
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
	


		//新增租金付款資訊
		function addPaymentRow(tbodyID,isElec) {
			
			if( $('#LessorNames_site').val()=='' || $('#LessorNames_site').val()==','){
				alert("請先設定出租人資料");
				return false;
			}
			var lessorNamesValue =  $('#LessorNames_site').val().split(',');
			var row1 = '<tr><td><select id="" class="" name="PAYMENT_ITEM">';
			if(isElec){
				row1 +='<option value="E" selected>電費</option><option value="ED">電費押金</option>';
			}else{
				row1 +='<option value="R" selected>租金</option><option value="RD">租金押金</option>';
			}
			
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
			
			rentSelect2Test();
		
		}

		function changeBanksSub(theField){
			var tempSelect = $(theField).parent().parent().find("select[name='SUB_UNIT_CODE']");
			tempSelect.empty();
			
			tempSelect.append(buildSubBanksOptions(theField.value,""));
			
			tempSelect.val("");
			tempSelect.select2({ allowClear: true });
			//alert(tempSelect.prop("name"));
		}
		
		//*********資源互換***********
		function ajaxExchData(){
			var loc_Id = $("#siteTbody tr").closest(".selected").first().children().first().text();
			
		 	var data = {
					lsNo : $("#site_LsNo").val(),
					lsVer : $("#site_LsVer").val(),
					lsVer : $("#site_LsVer").val(),
					lsType : $("#site_LsType").val(),
					appSeq : $("#site_AppSeq").val(),
					locId : loc_Id
				}; 
			$.ajax({			
				url : CONTEXT_URL + "/ls/LS001M_Site/getLocationSitePay4Exch",		
				data: data,
				type : "POST",
				dataType : 'json',
				success : function(data) {
					$('#siteExch_form').trigger('reset');
					if(data.success){
						$("#carrier").html(data.maps.carriers);
						//funciton of LS001M_SiteExch.jsp
						exchItemLoad(data.maps.exchItem);
						var jsonArray = JSON.parse(data.maps.exchData);
						if (jsonArray.length > 0) {
							setExchData(jsonArray);
						}
						$('#siteTabs').show();
					}else{
						alert(data.msg);
						$('#siteTabs').hide();
					}
				}
			}); 

		}
		
		function setExchData(jsonArray) {
			var obj = jsonArray[0];
			$.each(obj, function(k, v) {
				if (k == 'exch_DESC'){
					$("#" + k).val(v);
				}
			});
			//funciton of LS001M_SiteExch.jsp
			loadExchSiteRow(jsonArray);
		}
		
		function getLsDomainMoneyList() {
			$.ajax({			
				url : CONTEXT_URL + "/ls/LS001M/getLsDomainMoneyList",		
				data: {"lsNo" : $("#site_LsNo").val(),
					   "lsVer" : $("#site_LsVer").val() 
				},
				type : "POST",
				dataType : 'json',
				success : function(data) {
					if(data.success){
						domainMoneyLoad(data.msg);
						cRentCheck();
						cElecCheck();
					}
				}
			}); 
		}
</script>

<style type="text/css">
table.scrollTable {
	width: 100%;
	border: 2px solid #ddd;
	border-color: #000000;
}

.theadth {
	text-align: left;
	padding: 0.3em 0.2em;
}

.tbodytd {
	border-top: 1px solid #eee;
	border-bottom: 1px solid #eee;
	border-right: 1px solid #eee;
	padding: 0.1em 0.3em;
	height: 25px
}

</style>