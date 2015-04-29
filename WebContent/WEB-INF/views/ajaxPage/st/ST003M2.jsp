
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
<title>接工</title>
<meta charset="utf-8">
<!-- ==================== CSS plugins =================== -->


<style type="text/css">
.contentStyle {
	padding-top: 6px;
	padding-left: 15px; 
}
td.searchRecordDisplayNone {display: none;}
</style>
<script type="text/javascript">
	var tabs01 = false;
	var tabs02 = false;
	var tabs03 = false;
	var osMap = new Hashtable();
	$(document).ready(function() {
		//是否為簽核頁面
		if("${isApplyPage}" == "Y"){
			$("#workflowJsonData").val('${jsonData}');
		}
		
		var data =[];
		$("#tabs").tabs();
		$("#tabs-1 :input ").prop("disabled", true);
// 		$("#selectSrIdBtn").prop("disabled",false);
		$("#retiredWorkBut").prop("disabled", true);
		$("#pickWorkBut").prop("disabled", true);
		$("#completionWorkBut").prop("disabled", true);
		$("#endDesc").prop("disabled", true);
// 		$("#downloadAttch").prop("disabled", true);
		$("#saveBtnTabs1").prop("disabled", true);
		$("#siteHuntApplyBtnsTr").hide();
		//簽核頁面NSR只顯示作業內容
		if("${applyType}" == "NewSiteRequest"){
			$("#tabs2").hide();
			$("#tabs-2").hide();
			$("#tabs3").hide();
			$("#tabs-3").hide();
		}
		//
		$('#date').datepicker({
			dateFormat : "yy/mm/dd",
			changeYear : true,
			changeMonth : true,
			showButtonPanel : true
		});
		
		//退工
		$("#retiredWorkBut").click(function() {
			if($.trim($("#endDesc").val()) == ""){
				alert("退工時處理說明必須填寫！");
				return false;
			} else if ($.trim($("#endDesc").val()).length > 200) {
				alert("處理說明超過200字限制！");
				return false;
			} else if (confirm('確認退工後作業工單將無法再進行處理，是否確定？')) {
				$.ajax({
					url : CONTEXT_URL + "/st/st003/dropOrder",
					dataType : 'json',
					async : false,
					data : {
						"orderId" :  $("#orderId").val(),
						"endDesc" :  $("#endDesc").val()
					},
					success : function(data){
						alert(data.msg);
						if(data.success){
							$("#disableAllBtn").val(true);
							$("button.retiredWork-disabled").prop("disabled", true);
							$('#btn_search').click();
							$.fancybox.close();
						} 
					}	
				});
			}	
		});
		
		
		$("#pickWorkBut").click(function() {
			if(!confirm("確定要接工？")){
				return false;
			}
			$.ajax({
				url : CONTEXT_URL + "/st/st003/changePickWork",
				type : 'POST',
				dataType : "json",
				data:{
					"orderId" :  $("#orderId").val(),
					"pickWorkName" :  $("#userName").val()
					
				},
				async : false,
				success : function(data) {
					
					$("#pickWorkBut").prop("disabled", true);
					$("#retiredWorkBut").prop("disabled", false);
					$("#completionWorkBut").prop("disabled", false);
					$("#endDesc").prop("disabled", false);
					$("#downloadAttch").prop("disabled", false);
					$("#saveBtnTabs1").prop("disabled", false);
					$("#saveTab2").prop("disabled",false); //存檔		
					$("#applicationBtn").prop("disabled",false); //申請
					$("#showPickWorkName").text($("#showUserName").val());
					$("#pickWorkName").val($("#userName").val());
					
					$("#showOdrStatus").text("已接工");
					
// 					if ($("#siteHuntWorkType").val() == 'SH') {
						$("#tabs2").show();
						$("#tabs-2").show();
						$("#tabs3").show();
						$("#tabs-3").show();
						$("#outSourceApply").click();
						$("#searchRecord").click();
// 					}
					$("#workContant").click();
					tabOrderPageFun();
					alert(data.msg);
					if ('${fromTODO}' == 'N') {
						updateEide($("#workId").val(), $("#orderId").val(), "", "", "edit", "", $("#odrStatus").val())
					}
				}
			});
			
		});
		$("#saveBtnTabs1").click(function() {
			if($("#notAlert").val() != "Y"){
				var errorMessage = "";
				if ($("#siteHuntSrId").val() == "" || $("#siteHuntSrId").val() == null) {
					errorMessage="請選擇 srId ！\n";
				}
				
				if ($("#endDesc").val().length > 200) {
					errorMessage="處理說明超過限制200字！\n";
				}
				var message = doValidate();
				if(message != ""){
					errorMessage=message+"\n";
				}
				if(errorMessage != ""){
					alert(errorMessage);
					return false;
				}
			}
			$.ajax({
				url : CONTEXT_URL + "/st/st003/save",
				type : 'POST',
				dataType : "json",
				data:{
					"orderId" :  $("#orderId").val(),
					"endDesc" :  $("#endDesc").val(),
					"srId" : $("#siteHuntSrId").val(),
					"lon" : $("#siteHuntLon").val(),
					"lat" : $("#siteHuntLat").val(),
					"city" : $("#siteHuntCity").val(),
					"area" : $("#siteHuntArea").val(),
					"coverRange" : $("#coverRange").val()
				},
				async : false,
				success : function(data) {
					if($("#notAlert").val() != "Y"){
						alert(data.msg);
					}
				}
			});
			
		});
		
		
		//add by Miles Change 2014.12.18
		$("#outsourcingButton").click(function(){
			var osId =  $("#osIdTab2").val();
			if(!osId){
				alert("請選一筆資料！");
				return false;
			}
			var osStatus = $("#orderStatusTab2").val();
			var continueSend = false;
			if(osStatus != 'OS05' && confirm("確定要進行委外派工？")){
				$.ajax({
					url : CONTEXT_URL + "/st/outSourcing/out/getTbComCompany",
					type : 'POST',
					dataType : "json",
					data:{
						"osId" :  $("#osIdTab2").val()
					},
					async : false,
					success : function(data) {
						if(!data.row.con_EMAIL){
							continueSend = confirm('廠商連絡人無e-mail資料!\r\n是否要繼續？');
						} else{
							continueSend = true;
						}
					}
				});
			}
			
			if(continueSend || osStatus == 'OS05'){
				$.ajax({
					url : CONTEXT_URL + "/st/outSourcing/out/sendMail",
					type : 'POST',
					dataType : "json",
					data:{"osId" :  osId},
					async : false,
					success : function(data) {
						alert(data.msg);
						table.fnDraw();
						$('#outSourceApply').trigger('click');
						outSourceTableFun($("#osIdTab2").val());
					}
				});								
			}
			
		});//委外申請-委外派工 end
		
		//委外申請-驗收
		// add by Miles 2014.12.24
		$("#applyWorkButton").click(function(){
			
			if ($("#orderIdTab2").val() == "") {
				alert("無任何派工單 無法驗收");
				return;
			}
			if(!confirm("送出驗收後將無法再修改資料，是否繼續？")){
				return false;
			}
			var apNumber =[];
			var itemId = [];
			var apAmount=[];			
			var isNumber = true;
// 			itemMainQueryAfterFun();
			
			//驗收數量
			$("#toItemMainTable :input[name='apNumber']").each(function(){
				if(!qtyCheck(this.value,"驗收數量")){
					isNumber = false;
					return false;
				}
				apNumber.push($(this).val());
			});
			
			if(!isNumber){
				return false;
			}
			
			$("#toItemMainTable :input[name='itemId']").each(function(){
				itemId.push($(this).val());
			});
			
			//驗收金額
			$("#toItemMainTable :input[name='apAmount']").each(function(){
				apAmount.push($(this).val());
			});
			
			// AJAX至後端驗收
			$.ajax({
				url : CONTEXT_URL + "/st/outSourcing/out/checkOsOrder",
				type : 'POST',
				dataType : "json",
				data: { 
					"osId" :  $("#osIdTab2").val(),
				    "poId" : $("#poId").val(),
				    "apNumber" : apNumber.toString(),
				    "itemId" : itemId.toString(),
				    "apAmount" : apAmount.toString(),
				    "processType" : $("#os_process_accept").val()
				},
				async : false,
				success : function(data) {
					alert(data.msg);
					table.fnDraw();
					$('#outSourceApply').trigger('click');
					outSourceTableFun($("#osIdTab2").val());
				}
			});   
		});//委外申請-驗收 end
		
		$("#downloadAttch").click(function() {
			openFileDownFrame("showFileDownloadFrame", $("#siteHuntWorkType").val(), $("#workId").val(), "NOTYPE");
			
		});
		
		//委外申請
		$("#outSourceApply").click(function () {
			if(tabs02) return;
			tabs02 = true;
			outSourceTableFun('0');
			osVenSelect();
			$("#orderIdTab2").val($("#orderId").val());
		});
		
		table = $('#workLocTable').dataTable({
			"bDestroy" : true,
			"iDisplayLength" : -1,
			"aaSorting" : [ [ 0, "desc" ] ],
			"sDom" : "rS",
			"sScrollY" : '70',
            "oLanguage" : {
				"sProcessing" : "處理中...",
				"sZeroRecords" : "沒有匹配結果",
				"sLoadingRecords" : "讀取中..."
			},
			"fnInitComplete" : function() {
				this.fnAdjustColumnSizing();
			}
		});

		table = $('#signLogTable').dataTable({
			"bDestroy" : true,
			"iDisplayLength" : -1,
			"aaSorting" : [ [ 0, "desc" ] ],
			"sDom" : "rS",
			// 	 			"bAutoWidth":true,
			"sScrollY" : '70',
			// 		        "sScrollX" : "100%",
			// 		        "bProcessing" : false,
			// 		        "sAjaxDataProp" : "rows",
			// 		        "sAjaxSource" : CONTEXT_URL+'/org/dept/deptPos/deptPosTable?deptId='+deptId,
			// 		        "aoColumnDefs" : [ {
			// 		           	"aTargets" : [0], bSortable : false, "mData" : "posId",
			// 		           	"mRender": function ( data, type, full ) {
			// 		             	return '<input type="checkbox"  onclick="listenerDeptPosCheckBox();" '+disable+' name="deptPos" value="'+data+'">';
			// 		            }},
			// 		            { "aTargets": [1], "mData": "posId"},
			// 		            { "aTargets": [2], "mData": "posName"},
			// 		            { "aTargets": [3], "mData": "posType"},
			// 		            { "aTargets": [3], "mData": "posType"},
			// 		           ],
			"oLanguage" : {
				"sProcessing" : "處理中...",
				"sZeroRecords" : "沒有匹配結果",
				"sLoadingRecords" : "讀取中..."
			},
			"fnInitComplete" : function() {
				this.fnAdjustColumnSizing();
			}
		});
		
		$("#siteHuntApplyEvent").val("${siteHuntApplyEvent}");
		buildCityOptions("siteHuntCity","siteHuntArea");
		
		if($("#siteHuntApplyEvent").val() == "update"){
			
			$("#showWorkId").text("${siteWork.WORK_ID}");
			$("#workId").val("${siteWork.WORK_ID}");
			$("#showWorkType").text("${workTypeName}");
			$("#workType").val("${workTypeName}");
			$("#siteHuntWorkType").val("${siteWork.WORK_TYPE}").change();
			$("#siteHuntWorkTypeInput").val($('#siteHuntWorkType :selected').text());
			$("#showWorkStatus").text("${workStatusName}");
			$("#siteHuntWorkStatus").val("${siteWork.WORK_STATUS}");
			$("#priority").val("${siteWork.PRIORITY}").change();
			//$("#showApplyUser").val("${siteWork.APL_USER}");2015.01.09被換掉
			//$("#applyUser").val("${siteWork.APL_USER}");
			$("#showApplyUser").text("${applyUserName}");
			$("#applyUser").val("${applyUserName}");
			
			$("#showsiteHuntLocationId").text("${location.LOCATION_ID}");
			$("#siteHuntLocationId").val("${location.LOCATION_ID}");
			$("#showLocName").text("${location.LOC_NAME}");
			$("#locName").val("${location.LOC_NAME}");
			$("#loginUsrVendor").val("${loginUsrVendor}");
			$("#userName").val("${userName}");
			$("#showUserName").val("${userNameCH}");
			
			//作業內容 狀態
			
			$("#showOdrStatus").text("${orderTypeName}");
			$("#odrStatus").val("${orderStatus}");
			if ($("#checkDabClick").val() != "view"){
				disabledBtn($("#odrStatus").val(), $("#loginUsrVendor").val());
			}
			
			if (($("#odrStatus").val() == 'OR05' || $("#odrStatus").val() == 'OR06')  && $("#loginUsrVendor").val() != "true"){
				
				$("#endDesc").prop("disabled", false);
				$("#retiredWorkBut").prop("disabled", false);
				$("#completionWorkBut").prop("disabled", false);
				$("#endDesc").prop("disabled", false);
				$("#downloadAttch").prop("disabled", false);
				$("#saveBtnTabs1").prop("disabled", false);
				$("#saveTab2").prop("disabled", false);
				$("#applicationBtn").prop("disabled", false);
				$("#applicationCancelBtn").prop("disabled", false);
				$("#poItemTab2").prop("disabled",false); 
				
			}
			if($("#odrStatus").val() == 'OR01' 
					|| $("#odrStatus").val() == 'OR02'
					|| $("#odrStatus").val() == 'OR03'
					|| $("#odrStatus").val() == 'OR04'){
				$("#tabs2").hide();
				$("#tabs-2").hide();
				$("#tabs3").hide();
				$("#tabs-3").hide();
			}
			$("#showOrderId").text("${orderId}");
			$("#orderId").val("${orderId}");
			$("#showPickWorkName").text("${showPickUser}");
			$("#pickWorkName").val("${pickUser}");
			
			if("${isMultibandLocation}" >1){
				$("#multibandLocation").prop("checked",true);
			}
			$("#showSiteHuntSrId").text("${siteWork.SR_ID}");
			$("#siteHuntSrId").val("${siteWork.SR_ID}");
			
			if($("#siteHuntSrId").val() != ""){
				$("#siteHuntCity").prop("disabled",true);
				$("#siteHuntArea").prop("disabled",true);
				$("#siteHuntLon").prop("readonly",true);
				$("#siteHuntLat").prop("readonly",true);
				$("#coverRange").prop("readonly",true);
			}
			
			if("${siteWork.FEQ_ID}".split(",")[0] != "null" && "${siteWork.FEQ_ID}".split(",")[1] != "null"){
				$("#feqId").val("${siteWork.FEQ_ID}").change();
			}
			
			$("#purpose").val("${siteWork.PURPOSE}").change();
			$("#siteHuntCity").val("${siteWork.CITY}").change();
			$("#siteHuntArea").val("${siteWork.AREA}").change();
			$("#siteHuntLon").val("${siteWork.SR_LON}");
			$("#siteHuntLat").val("${siteWork.SR_LAT}");
			$("#coverRange").val("${siteWork.SR_COVER_RANG}").change();
			$("#siteHuntBtsSiteId").val("${siteWork.BTS_SITE_ID}");
			$("#siteHuntSiteId").val("${siteWork.SITE_ID}");
			$("#searchSiteId").val("${siteWork.SITE_ID}")
			$("#siteHuntSiteName").val("${siteWork.SITE_NAME}");
			$("#siteHuntEqpType").val("${siteWork.EQP_TYPE_ID}");
			
			getEqpModel("${siteWork.EQP_TYPE_ID}","siteHuntEqpModel");
			
// 			var eqpType = $("#siteHuntEqpType").val();
// 			var feqType = $("#feqId").val().split(",")[1];
// 			if(eqpType ==3 || eqpType==4){
// 				$("#btsConfig").prop("readonly",true).val("");
// 				$("#feederless").prop("disabled",true);
// 				$("#coverRange").prop("disabled",true).val("");
// 				$("#siteHuntLon").prop("disabled",true).val("");
// 				$("#siteHuntLat").prop("disabled",true).val("");
// 				//$("#selectSrIdBtn").prop("disabled",true);
// 			}else if(eqpType == 1 || eqpType == 2){
// 				$("#btsConfig").prop("readonly",false);
// 				$("#donorSiteText").val("");
// 				$("#donorSite").val("");
// 				$("#masterSiteText").val("");
// 				$("#masterSite").val("");
// 				$("#feederless").val("").change();
// 				$("#feederless").prop("disabled",true);
// 			}else{
// 				$("#donorSiteText").val("");
// 				$("#donorSite").val("");
// 				$("#btsConfig").prop("readonly",true).val("");
// 				$("#feederless").prop("disabled",false);
// 			}
// 			if($("#feqId").val().split(",")[1] == "L" && 
// 					$("#siteHuntEqpType").val() == 2){
// 				$("#siteHuntBtsSiteId").prop("readonly",true);
// 				$("#unuseBtsSiteIdBtn").prop("disabled",true);
// 			}else{
// 				$("#siteHuntBtsSiteId").prop("readonly",false);
// 				$("#unuseBtsSiteIdBtn").prop("disabled",false);
// 			}
			//
			$("#siteHuntEqpModel").val("${siteWork.EQP_MODEL_ID}").change();
			$("#coverageType").val("${siteWork.COVERAGE_TYPE}");
			$("#btsConfig").val("${siteWork.BTS_CONFIG}");
			$("#donorSiteText").val("${donorBtsSiteId}");
			$("#donorSite").val("${siteWork.DONOR_SITE}");
			$("#masterSiteText").val("${masterBtsSiteId}");
			$("#masterSite").val("${siteWork.MASTER_SITE}");
			$("#feederless").val("${siteWork.FEEDERLESS}").change();
			$("#prediceEndDate").val("${prediceEndDate}");
			<fmt:formatDate value="${siteWork.APL_TIME}" pattern="yyyy/MM/dd HH:mm" var="apptime"/>
			$("#applyTime").val("${apptime}");
			$("#endTime").val("${siteWork.END_TIME}");
		} else {
			$("#showSiteHuntSrId").text("${siteWork.SR_ID}");
			$("#siteHuntSrId").val("${siteWork.SR_ID}");
		}
		
		
		//委外申請---新增button
		$("#addTab2").click(function() {
			$("button.default-disabled").prop("disabled",true);
			
			$("#saveTab2").prop("disabled", false);
			$("#coVatNoTab2").prop("disabled", false);
			$("#poNoTab2").prop("disabled", false);
			$("#poItemTab2").prop("disabled", false);
			$("#applicationBtn").prop("disabled", false);
			
			$("#showOsIdTab2").text("");
			$("#osIdTab2").val("");
			
			$("#showOrderStatusTab2").text("");
			$("#orderStatusTab2").val("");
			
			$("#showAplTimeTab2").text("");
			$("#aplTimeTab2").val("");
			
			$("#showOsTimeTab2").text("");
			$("#osTimeTab2").val("");
			
			$("#showApDateTab2").text("");
			$("#apDateTab2").val("");
			
			$("#showOsDescTab2").val("").prop("disabled", false);
			
			$("#showOsIdTab2").text("");
			$("#osIdTab2").val("");
			
			$("#showOsIdTab2").text("");
			$("#osIdTab2").val("");
			
			if ($("#orderIdTab2").val() != "") {
				var table2 = $('#toItemMainTable').DataTable().fnClearTable();
			}
			
			$("#showAplUserTab2").text("");
			$("#aplUserTab2").val("");
			
			$("#showPriceTab2").text("");
			$("#priceTab2").val("");
			
			$("#coVatNoTab2").val("");
			$("#poNoTab2").val("");
			$("#poNoTab2  option").remove();
			$("#poNoTab2").append("<option value=''>--請選擇--</option>");
			$("#checkBoxItemIdTab2").val("");
			
			// clean history log
			$("#applySignLog").empty();
			$("#acceptSignLog").empty();
		});
		
		
		
		
		//委外申請---工項設定
		$("#poItemTab2").click(function() {
			if ($("#coVatNoTab2").val() == ""){
				alert("廠商名稱未選擇");
				return;
			}
			if ($("#poNoTab2").val() == ""){
				alert("PO單號未選擇");
				return;
			}
			openOpItemDialogFrame("showPoItemPag", $("#poNoTab2").val());
			itemMainSelect();
			$("#checkBoxItemIdPoItem").val($("#checkBoxItemIdTab2").val());
			
			setOsNumberMap();
		});
		
		//委外申請---申請
		$("#applicationBtn").click(function(){
			if ($("#orderIdTab2").val() == "") {
				alert("無任何派工單 無法申請");
				return;
			}
			
			if ($("#checkBoxItemIdTab2").val() == ""){
				alert("無工項明細 無法申請");
				return;
			}
			var application = confirm("送出申請後將無法再修改資料，是否繼續？");
			if(application){
				saveBtnTabs2("1");	// update or save first
				$.ajax({
					url : CONTEXT_URL + "/st/outSourcing/out/applicationCheck",
					type : 'POST',
					dataType : "json",
					data:{
						"osId" :  $("#osIdTab2").val(),
	                    "aplDept" :  $("#repTeamTab2").val(),
	                    "processType" : $("#os_process_apply").val()
					},
					async : false,
					success : function(data) {
						//alert(JSON.stringify(data));
// 						saveBtnTabs2("1");
						alert(data.msg);
						$("#addTab2").prop("disabled",false);
						if (data.success){
							$("#applicationBtn").prop("disabled",true);
							$("#saveTab2").prop("disabled",false);
							$("#poItemTab2").prop("disabled",false);
							$("#applicationCancelBtn").prop("disabled",false);
							$("#outsourcingButton").prop("disabled",false);
							$("#applyWorkButton").prop("disabled",false);
							outSourceTableFun($("#osIdTab2").val());	// reload outsource list data table
						}						
					}
				});
			}
		});
		
		//委外申請--取消申請  applicationCancelBtn
		$("#applicationCancelBtn").click(function(){
			if ($("#orderIdTab2").val() == "") {
				alert("無任何派工單 無法取消申請");
				return;
			}
			if(!confirm("取消申請後將無法再修改，是否繼續？")){
				return false;
			}
			$.ajax({
				url : CONTEXT_URL + "/st/outSourcing/out/applicationCancel",
				type : 'POST',
				dataType : "json",
				data:{
					"osId" :  $("#osIdTab2").val()
				},
				async : false,
				success : function(data) {
					//alert(JSON.stringify(data));
					alert(data.msg);
					//$("#outSourceApply").click();
					if (data.success) {
						outSourceTableFun($("#osIdTab2").val());
						$("#saveTab2").prop("disabled",true);
						$("#poItemTab2").prop("disabled",true);
						$("#applicationBtn").prop("disabled",true);
						$("#applicationCancelBtn").prop("disabled",true);
						$("#outsourcingButton").prop("disabled",true);
						$("#applyWorkButton").prop("disabled",true);
					}
				}
			});
		});
	
		//探勘記錄Tab===========================================================================================================
		$("#searchDownloadAttch").click(function(){
			openFileDownFrame("showFileDownloadFrame", "SEARCH", $("#srId").val()+"S"+$('#srSeq').val(), "");
		});
		
		$("#searchRecord").click(function(){	// 探勘記錄Tab
			//alert($("#searchSiteId").val()+ " = 123456");
			if(tabs03) return;
			tabs03 = true;
			$("#hideTr").hide();
			$("#hideTrOne").hide();
			$("#hideTrTwo").hide();
			$("#searchDownloadAttch").hide();
			if("${view}" == "view"){
				$("#searchDownloadAttch").show().prop("disabled", false);
			}
			
			getSiteSearchTable('0'); // 讀取探勘記錄清單 drag function by Charlie 2015/03/23
			
			//基站編號
	 		$("#btsSite").text($("#siteHuntBtsSiteId").val());
			$("#btsSiteId").val($("#siteHuntBtsSiteId").val());
	 		$("#btsSiteTemp").text($("#siteHuntBtsSiteId").val());
			$("#btsSiteTempId").val($("#siteHuntBtsSiteId").val());
			//基站頻段
	 		$("#feq").text($("#feqId").find("option:selected").text());
			$("#idFeq").val($("#feqId").val());	
			$("#includeRange").val($("#coverageType").val()).change();
		});
		
		//
		$("#uploadBoutton").click(function() {
			openFileDialogFrame("showFileUploadFrame", "SEARCH", $("#srId").val()+"S"+$('#srSeq').val(), "");
		});
		
		//複製為新記錄
		$("#copyNewBoutton").click(function() {
			$('#seq').text("");
			$('#srSeq').val("");
		});
		
		//重置  
		$("#newDataBoutton").click(function() {
			//alert('123');
			$('#seq').text("");
			$('#srSeq').val("");
			$('#srDate').val("");
			$('#srPsn').val("");
			$('#srEval').val("").change();
			$('#address').val("");
			$('#siteLon').val($("#siteHuntLon").val());
			$('#siteLat').val($("#siteHuntLat").val());
			$('#baseType').val("").change();
			$('#height').val("");
			$('#floor').val("");
			$('#inBuilding').val("").change();	
			$('#isFreeEnter').prop("checked",false);
			$('#isKey').prop("checked",false);
			$('#isInDoor').prop("checked",false);
			$('#needMiscLic').prop("checked",false);
			$('#hasMiscLic').prop("checked",false);
			$('#lineReachable').prop("checked",false);
			$('#expectRent').val("");
			$('#shareType').val("0").change();
			$('#antStand').val("").change();
			$('#antSHight').val("");
			$('#antCHight').val("");
			
			$('#lookupCHT').prop("checked",false);
			$('#lookupFET').prop("checked",false);
			$('#lookupTWM').prop("checked",false);
			$('#lookupAPT').prop("checked",false);
			$('#spaceRoom').prop("checked",false);
			$('#spaceRoof').prop("checked",false);
			$('#spaceTop').prop("checked",false);
			$('#spacePlatform').prop("checked",false);
			$('#spaceLand').prop("checked",false);
			$('#spaceIndoor').prop("checked",false);
			$('#spaceOutdoor').prop("checked",false);
			$('#footAge').val("");
			$('#empRelation').val("");
			$('#locDesc').val("");
			$('#siteTempName').val($("#siteHuntSiteName").val());
			$('#siteHuntEqpTemp').val($("#siteHuntEqpType").val()).change();
			$("#siteHuntEqpTemp").prop("disabled", true);
			$('#siteHuntEqpModelTemp').val($("#siteHuntEqpModel").val()).change();
			$('#feederlessTeam').val($("#feederless").val()).change();
			$("#feederlessTeam").prop("disabled", true);
			$('#masterSiteTemp').val($("#masterSiteText").val());
			$('#configuration').val($("#btsConfig").val());
// 			$('#speedUpload').val("");
// 			$('#speedDownload').val("");
			$('#donorBts').val($("#donorSiteText").val());
			$('#includeRange').val("").change();
			$('#coverageInFloor').val("");
			$('#power').val("");
			$('#cluster').val("");
			$('#rncBts').val("");
			$('#testTableDIV table').remove();
			$("#uploadBoutton").prop("disabled", true);
			// add by Charlie 清除天線組態已使用的元素
			unUse.length = 0;
			//console.log(unUse);
		});
		
		
		//探勘記錄--存檔
		$("#saveBoutton").click(function() {
			
			if ($('#srDate').val() == '') {
				alert('請點選 探勘日期!');
				return false;
			} 
			if ($('#srPsn').val() == '') {
					alert('請輸入 探勘人員!');
					return false;
				} 
			if ($('#srEval').val() == '') {
					alert('請選擇 探勘評估!');
					return false;
			} 	
			if ($('#address').val() == '') {
					alert('請輸入 探勘地址!');
					return false;
			}
			if ($('#siteLon').val() == '') {
				alert('請輸入 經度!');
				return false;
			}
			if ($('#siteLat').val() == '') {
				alert('請輸入 緯度!');
				return false;
			}
			//天線架設高度
			if ($('#antSHight').val() < 0) {
				alert('天線架設高度: 不可輸入負數!');
				return false;
			}
			//天線中心高度
			if ($('#antCHight').val() < 0) {
				alert('天線中心高度 : 不可輸入負數!');
				return false;
			}
			

			if(!validateNumber( $("#siteLon").val(),3,6)){
				  alert("經度格式錯誤，整數最多三位、小數最多為六位");
				  return;
			}
			
			if($("#siteLon").val() < 115 || $("#siteLon").val() > 124){
				  alert("經度需介於115~124之間");
				  return;
			}
			   
			if(!validateNumber($("#siteLat").val(),2,6)){
				  alert("緯度格式錯誤，整數最多二位、小數最多為六位");
				  return;
			}
			
			if($("#siteLat").val() < 18 || $("#siteLat").val() > 28){
				alert("緯度需介於：18~28之間");
				return;
			}
			   
			if ($('#siteTempName').val() == '') {
				alert('請輸入基站名稱!');
				return false;
			}
			if ($('#siteHuntEqpTemp').val() == '') {
				alert('請輸入設備型態!');
				return false;
			}
			
			if($("#height").val() !=null && $("#height").val() !="") {				
				if(!validateNumber($("#height").val(),3,1)){
					alert('建物高度格式錯誤，整數最多三位、小數最多一位');
					return false;
				}
			}
			
			if($("#footAge").val() != "" && $("#footAge").val() != null){
				if(!validateNumber( $("#footAge").val(),3,0)){
					alert('坪數格式錯誤，整數最多三位');
					return false;
				}
			}
			//天線組態的判斷
			//alert($("#cell-"+tableNum).val());
			for(var num=1;num<=tableNum;num++) {
				var antCheckOk = siteAntValidate(num);
				if (!antCheckOk) {
					return false;
				}
			}
			
			//空間 check 判斷 塞val值
			$("#spaceList :input[name*=space]").each(function(){
				$(this).val($(this).prop("checked")?"Y":"N");
				//console.log($(this).val());
			});	
			
			//其他資訊 check 判斷 塞val值
			$("#isTemp :input[name*=is]").each(function(){
				$(this).val($(this).prop("checked")?"Y":"N");
				//console.log($(this).val());
			});	
			
			//其他資訊 check 判斷 塞val值
			$("#isTeam :input[name*=is]").each(function(){
				$(this).val($(this).prop("checked")?"Y":"N");
				//console.log($(this).val());
			});	
			
			//共站業者 將資料 組回 陣列 放入 物件
			var lookupList = [];
			$("#checkBoxList :input[name*='lookup']:checked").each(function(){
				lookupList.push($(this).val());
			});
			$("#lookup").val(lookupList);
			

			//天線組態
			var exportArray = [];
			
			$('#testTableDIV table').each(function(){
				var exportObj = {} ;
				var ttable = $(this).closest('table');
				
				$(ttable).find(':input').each(function(){ 				
					exportObj[String($(this).attr("name"))] = String($(this).val());
				});
				exportArray.push(exportObj);
				console.log(exportObj);
			});
			
			
			// 產生序號			
			if ($('#srSeq').val() == "") {
				//alert($("#siteHuntSrId").val());
				if($("#siteHuntSrId").val() == null) {
					alert("作業內容 沒有 SRID，請選擇 ！");
					return false;				
				}
				$("#srId").val($("#siteHuntSrId").val());
				uniqueSeg();
				//alert("srSeq 1="+$("#srSeq").val());
				$.ajax({
					url : CONTEXT_URL + "/st/searchRecord/saveNewSiteSearch",
					type : 'POST',
					data : {
						'workId' : $("#workId").val(),
						'srId' : $("#siteHuntSrId").val(),
						'srSeq': $("#srSeq").val(),
						'siteTempName' : $("#siteTempName").val(), //基站名稱
						'searchSiteId': $("#searchSiteId").val(), //站台識別碼
						//========================================Tb_site_Search  
						'srPsn' : $("#srPsn").val(), //探勘人員
						'srDate' : $("#srDate").val(), //探勘日期					
						'srEval' : $("#srEval").val(), //探勘評估
						'address' : $("#address").val(), //探勘地址
						//地址細項
						'qq_zip' : $("#qq_zip").val(),//郵遞區號
						'qq_city' : $("#qq_city").val(), //地址-縣市
						'qq_area' : $("#qq_area").val(), //地址-鄉鎮區
						'qq_village' : $("#qq_village").val(), //地址-村里
						'qq_adjacent' : $("#qq_adjacent").val(), //地址-鄰
						'qq_road' : $("#qq_road").val(), //地址-路(街)
						'qq_lane' : $("#qq_lane").val(), //地址-巷
						'qq_alley' : $("#qq_alley").val(), //地址-弄
						'qq_subAlley' : $("#qq_subAlley").val(), //地址-衖
						'qq_doorNo' : $("#qq_doorNo").val(), //地址-門號
						'qq_doorNoEx' : $("#qq_doorNoEx").val(), //地址-門號(之)
						'qq_floor' : $("#qq_floor").val(), //地址-樓層
						'qq_floorEx' : $("#qq_floorEx").val(), //地址-樓層(之)
						'qq_room' : $("#qq_room").val(), //地址-室
						'qq_remark' : $("#qq_remark").val(), //地址-其他
						
						'siteLon' : $("#siteLon").val(), //經度
						'siteLat' : $("#siteLat").val(), //緯度
						'baseType' : $("#baseType").val(), //機房型態 
						'height' : $("#height").val(), //建物高度
						'floor' : $("#floor").val(),  //建物樓層
						'inBuilding' : $("#inBuilding").val(), //室內場所分類
						//其他資訊
						'isFreeEnter' : $("#isFreeEnter").val(), //是否可自由進入
						'isKey' : $("#isKey").val(), //是否有鑰匙
						'isInDoor' : $("#isInDoor").val(), //是否為室內
						'needMiscLic' : $("#needMiscLic").val(), //需要雜項執照
						'hasMiscLic' : $("#hasMiscLic").val(), //已有雜項執照
						'lineReachable' : $("#lineReachable").val(), //專線到達 
						'expectRent' : $("#expectRent").val(), //預估租金
						'shareType' : $("#shareType").val(), //共構/共站
						'antStand' : $("#antStand").val(), //天線支架
						'antSHight' : $("#antSHight").val(), //天線架設高度
						'antCHight' : $("#antCHight").val(), //天線中心高度

						//空間
						'spaceRoom' : $("#spaceRoom").val(), //房屋
						'spaceRoof' : $("#spaceRoof").val(), //樓頂
						'spaceTop' : $("#spaceTop").val(), //屋凸
						'spacePlatform' : $("#spacePlatform").val(), //平地
						'spaceLand' : $("#spaceLand").val(), //土地
						'spaceIndoor' : $("#spaceIndoor").val(), //室內
						'spaceOutdoor' : $("#spaceOutdoor").val(), //室外
						'footAge' : $("#footAge").val(), //坪數
						'empRelation' : $("#empRelation").val(), //員工關係
						'locDesc' : $("#locDesc").val(), //探勘註解
						//========================================Tb_site_Search  End
						//========================================TB_SITE_SR_SITE_TEMP
						'siteHuntEqpTemp' : $("#siteHuntEqpTemp").val(), //設備型態
						'siteHuntEqpModelTemp' : $("#siteHuntEqpModelTemp").val(), //設備機型
						'feederlessTeam' : $("#feederlessTeam").val(), //Feederless
						'masterSiteTemp' : $("#masterSiteTemp").val(), //Master Site
						'configuration' : $("#configuration").val(), //Configuration
// 						'speedUpload' : $("#speedUpload").val(), //速率(上) 
// 						'speedDownload' : $("#speedDownload").val(), //速率(下)
						'donorBts' : $("#donorBts").val(), //Donor BTS
						'includeRange' : $("#includeRange").val(), //涵蓋類別
						'coverageInFloor' : $("#coverageInFloor").val(), //室內天線涵蓋樓層
						'power' : $("#power").val(), //功率
						'cluster' : $("#cluster").val(), //Cluster
						'rncBts' : $("#rncBts").val(), //RNC-BTS
						'coverageIndoor' : $("#coverageIndoor").val(),//有室內涵蓋
						'hidden' : $("#hidden").val(),//有美化施工
						'noisOnAir' : $("#noisOnAir").val(),//網管On Air
						'l2Switch' : $("#l2Switch").val(),//有接L2_SWITCH

						//========================================TB_SITE_SR_SITE_TEMP End		
						//共站業者
						'lookup' : $("#lookup").val(), 
						//========================================TB_SITE_SR_ANT_CFG_TEMP
						'exportList' : JSON.stringify(exportArray)
						//========================================TB_SITE_SR_SITE_TEMP End
					},
					async : false,
					success : function(data) {
						alert('存檔完成！');
						//$('#searchRecord').click();
						getSiteSearchTable($("#srSeq").val());
					}
				});	
			} else {
				//console.log($("#searchRecordTab1").serialize());
				$.ajax({
					mimeType : 'text/html; charset=utf-8', // ! Need set mimeType only when run from local file
					url : CONTEXT_URL + "/st/searchRecord/saveSiteSearch",
					type : 'POST',
					//contentType: "application/json",
					//data : $("#searchRecordTab1").serialize(), //+ "&srId=" + data['sr_ID'] + "&srSeq=" + data['sr_SEQ'],
 					data : {
						'srId' :$("#siteHuntSrId").val(),
						'srSeq' :$("#srSeq").val(),
						'workId' : $("#workId").val(),
						'searchSiteId': $("#searchSiteId").val(),
						//========================================Tb_site_Search  
						'srPsn' : $("#srPsn").val(), //探勘人員
						'srDate' : $("#srDate").val(), //探勘日期					
						'srEval' : $("#srEval").val(), //探勘評估
						'address' : $("#address").val(), //探勘地址
						//地址細項
						'qq_zip' : $("#qq_zip").val(),//郵遞區號
						'qq_city' : $("#qq_city").val(), //地址-縣市
						'qq_area' : $("#qq_area").val(), //地址-鄉鎮區
						'qq_village' : $("#qq_village").val(), //地址-村里
						'qq_adjacent' : $("#qq_adjacent").val(), //地址-鄰
						'qq_road' : $("#qq_road").val(), //地址-路(街)
						'qq_lane' : $("#qq_lane").val(), //地址-巷
						'qq_alley' : $("#qq_alley").val(), //地址-弄
						'qq_subAlley' : $("#qq_subAlley").val(), //地址-衖
						'qq_doorNo' : $("#qq_doorNo").val(), //地址-門號
						'qq_doorNoEx' : $("#qq_doorNoEx").val(), //地址-門號(之)
						'qq_floor' : $("#qq_floor").val(), //地址-樓層
						'qq_floorEx' : $("#qq_floorEx").val(), //地址-樓層(之)
						'qq_room' : $("#qq_room").val(), //地址-室
						'qq_remark' : $("#qq_remark").val(), //地址-其他
						
						'siteLon' : $("#siteLon").val(), //經度
						'siteLat' : $("#siteLat").val(), //緯度
						'baseType' : $("#baseType").val(), //機房型態 
						'height' : $("#height").val(), //建物高度
						'floor' : $("#floor").val(),  //建物樓層
						'inBuilding' : $("#inBuilding").val(), //室內場所分類
						//其他資訊
						'isFreeEnter' : $("#isFreeEnter").val(), //是否可自由進入
						'isKey' : $("#isKey").val(), //是否有鑰匙
						'isInDoor' : $("#isInDoor").val(), //是否為室內
						'needMiscLic' : $("#needMiscLic").val(), //需要雜項執照
						'hasMiscLic' : $("#hasMiscLic").val(), //已有雜項執照
						'lineReachable' : $("#lineReachable").val(), //專線到達 
						'expectRent' : $("#expectRent").val(), //預估租金
						'shareType' : $("#shareType").val(), //共構/共站
						'antStand' : $("#antStand").val(), //天線支架
						'antSHight' : $("#antSHight").val(), //天線架設高度
						'antCHight' : $("#antCHight").val(), //天線中心高度
						
						//共站業者
						'lookup' : $("#lookup").val(), 
						
						//空間
						'spaceRoom' : $("#spaceRoom").val(), //房屋
						'spaceRoof' : $("#spaceRoof").val(), //樓頂
						'spaceTop' : $("#spaceTop").val(), //屋凸
						'spacePlatform' : $("#spacePlatform").val(), //平地
						'spaceLand' : $("#spaceLand").val(), //土地
						'spaceIndoor' : $("#spaceIndoor").val(), //室內
						'spaceOutdoor' : $("#spaceOutdoor").val(), //室外
						'footAge' : $("#footAge").val(), //坪數
						'empRelation' : $("#empRelation").val(), //員工關係
						'locDesc' : $("#locDesc").val(), //探勘註解
						//========================================Tb_site_Search  End
						//========================================TB_SITE_SR_SITE_TEMP
/* 						'siteTempName' : $("#siteTempName").val(), //基站名稱
						'siteHuntEqpTemp' : $("#siteHuntEqpTemp").val(), //設備型態
						'siteHuntEqpModelTemp' : $("#siteHuntEqpModelTemp").val(), //設備機型
						'feederlessTeam' : $("#feederlessTeam").val(), //Feederless
						'masterSiteTemp' : $("#masterSiteTemp").val(), //Master Site
						'configuration' : $("#configuration").val(), //Configuration
						'speedUpload' : $("#speedUpload").val(), //速率(上) 
						'speedDownload' : $("#speedDownload").val(), //速率(下)
						'donorBts' : $("#donorBts").val(), //Donor BTS
						'includeRange' : $("#includeRange").val(), //涵蓋類別
						'coverageInFloor' : $("#coverageInFloor").val(), //室內天線涵蓋樓層
						'power' : $("#power").val(), //功率
						'cluster' : $("#cluster").val(), //Cluster
						'rncBts' : $("#rncBts").val(), //RNC-BTS
						'coverageIndoor' : $("#coverageIndoor").val(),//有室內涵蓋
						'hidden' : $("#hidden").val(),//有美化施工
						'noisOnAir' : $("#noisOnAir").val(),//網管On Air
						'l2Switch' : $("#l2Switch").val(),//有接L2_SWITCH
						'feqId' : $("#feqId").val(),
						'btsSiteTempId' : $("#btsSiteTempId").val(), */
						'SITE_ID': $("#searchSiteId").val(),
						'SR_ID' : $("#srId").val(),
						'SITE_NAME' : $("#siteTempName").val(), //基站名稱
						'EQP_TYPE_ID' : $("#siteHuntEqpTemp").val(), //設備型態
						'EQP_MODEL_ID' : $("#siteHuntEqpModelTemp").val(), //設備機型
						'FEEDERLESS' : $("#feederlessTeam").val(), //Feederless
						'MASTER_SITE' : $("#masterSiteTemp").val(), //Master Site
						'BTS_CONFIG' : $("#configuration").val(), //Configuration
// 						'SPEED_UPLOAD' : $("#speedUpload").val(), //速率(上) 
// 						'SPEED_DOWNLOAD' : $("#speedDownload").val(), //速率(下)
						'donorBts' : $("#donorBts").val(), //Donor BTS
						'COVERAGE_TYPE' : $("#includeRange").val(), //涵蓋類別
						'COVERAGE_IN_FLOOR' : $("#coverageInFloor").val(), //室內天線涵蓋樓層
						'POWER' : $("#power").val(), //功率
						'CLUSTER' : $("#cluster").val(), //Cluster
						'RNC_BTS' : $("#rncBts").val(), //RNC-BTS
						'COVERAGE_INDOOR' : $("#coverageIndoor").val(),//有室內涵蓋
						'HIDDEN' : $("#hidden").val(),//有美化施工
						'NOIS_ON_AIR' : $("#noisOnAir").val(),//網管On Air
						'l2_SWITCH' : $("#l2Switch").val(),//有接L2_SWITCH
						'FEQ_ID' : $("#feqId").val(),
						'BTS_SITE_ID' : $("#btsSiteTempId").val(),
						//========================================TB_SITE_SR_SITE_TEMP End
						
						//========================================TB_SITE_SR_ANT_CFG_TEMP
						'exportList' : JSON.stringify(exportArray)
						//========================================TB_SITE_SR_SITE_TEMP End
						
 					},
					dataType : "json",
					async : false,
					success : function(data) {
						alert('更新儲存成功!');
						getSiteSearchTable($("#srSeq").val());
					}
				});				
			}
		});		
		//=============================================================================================================
		//完工(送審核)
		$("#completionWorkBut").click(function() {
			$("#notAlert").val("Y");
			var errorMessage = "";
			if ($("#siteHuntSrId").val() == "" || $("#siteHuntSrId").val() == null) {
				errorMessage="請選擇 srId ！\n";
			}
			
			if ($("#endDesc").val().length > 200) {
				errorMessage="處理說明超過限制200字！\n";
			}
			var message = doValidate();
			if(message != ""){
				errorMessage=message+"\n";
			}
			if(errorMessage != ""){
				alert(errorMessage);
				$("#notAlert").val("N");
				return false;
			}
			$("#saveBtnTabs1").click();
			$("#notAlert").val("N");
			$(this).prop("disabled", true);		// 先鎖定按鈕以防止dbClick
			if (confirm('尚未存檔的資料即將放棄，確定要完工進行主管審核？')) {
				$.ajax({
					url : CONTEXT_URL + "/st/st003/verifyOrder",
					dataType : 'json',
					data : {
						"workId" :  $("#workId").val(),
						"orderId" :  $("#orderId").val()
					},
					async : false,
					success : function(data){
						alert(data.msg);
						$("#completionWorkBut").prop("disabled", false);	
						if(data.success){
							$('#btn_search').click();
							$.fancybox.close();
						}
					}	
				}); 
			} else {
				$("#completionWorkBut").prop("disabled", false);
			}	
			//window.close();
		});
		
		// 動態取得頁籤顯示
		tabOrderPageFun();
		//非廠商
		if ($("#loginUsrVendor").val() != "true"){
			
		    if ($("#odrStatus").val() == 'OR04'){
		    	$("#pickWorkBut").prop("disabled", false);
		    }
		} else {	// 廠商
			$("button.vendorWork-disable").prop("disabled", true);
			$("#pickWorkBut").prop("disabled", true);
			$("#retiredWorkBut").prop("disabled", true);
			$("#completionWorkBut").prop("disabled", true);
			
			$("#tabs1").hide();
			$("#tabs-1").hide();
			$(".hideWhenSign").hide();
			setTimeout(function(){ $("#outSourceApply").click(); }, 1500);
		}
		
		if($("#siteHuntWorkType").val() == "NSR"){			
			$("#selectSrIdBtn").prop("disabled", false);
		}
		
		if("${view}" == 'view'){
			$("button.retiredWork-disabled").prop("disabled", true);
			$(".hideWhenSign").hide();
			$("#tabs-1 :input ").prop("disabled", true);
			$("#tabs-2 :input ").prop("disabled", true);
			$("#tabs-3 :input ").prop("disabled", true);
			$("#toggleBtn").prop("disabled", false);
			$("#downloadAttch").prop("disabled", false);
		}
	});//document ready end
	
	// 探勘記錄主清單TABLE
	function getSiteSearchTable(srSeq) {
		// 探勘記錄LIST
		table2 = $('#siteSearchTable').dataTable({
			"bDestroy" : true,
			"iDisplayLength" : -1,
			"aaSorting" : [ [ 0, "desc" ] ],
			"sDom" : "rS",
			"sScrollY" : '70',
			"bSort" : false,
			"bProcessing" : false,
			"sAjaxDataProp" : "rows",
			"sAjaxSource" : CONTEXT_URL+ '/st/searchRecord/searchRecordList?workId='+$("#workId").val(),
						"aoColumnDefs" : [ 
					{"aTargets" : [ 0 ],"mData" : function(data){ 
					//alert(JSON.stringify(data));
						var result = '${jsonData}';
						if(result != null && result != ""){
							var obj = JSON.parse(result);
							if(obj.isModify == "Y"){
								return "<input  type='checkbox' onclick='checkedSearchRecord(\""+data.sr_ID+"\",\""+data.sr_SEQ+"\")' id='searchBox"+data.sr_SEQ+"' name='searchBox' value='"+data.sr_ID+"'>";
							}else{
								if(obj.srSeq == data.sr_SEQ){
									return "<input disabled checked name='searchBox' type='checkbox' >";
								}else{
									return "<input disabled name='searchBox' type='checkbox' >";
								}
							}
						}
						return "<input disabled type='checkbox'>";
					}, 
					"sWidth":"3%", "bSortable":false },	
					{"aTargets" : [ 1 ], "mData" : "work_ID", "sWidth":"10%", "bSortable":false }, 
					{"aTargets" : [2 ],"mData" : 
						function(data){ 
							return $("#siteHuntBtsSiteId").val();
	  					}, "sWidth":"8%", "bSortable":false 
	  				},
					{"aTargets" : [ 3 ], "mData" : "sr_SEQ", "sWidth":"5%", "bSortable":false }, 
					{"aTargets" : [ 4 ], "mData" : "addr", "sWidth":"35%", "bSortable":false }, 
					{"aTargets" : [ 5 ], "mData" : "sr_DATE", "sWidth":"8%", "bSortable":false }, 
					{"aTargets" : [ 6 ], "mData" : "sr_PSN", "sWidth":"8%", "bSortable":false },
					{"aTargets" : [ 7 ], "mData" : "evalName", "sWidth":"8%", "bSortable":false },
					{"aTargets" : [ 8 ], "mData" : "sr_ID", "sWidth":"5%", "bSortable":false, "sClass": "searchRecordDisplayNone"}
					], 
			"oLanguage" : {
				"sProcessing" : "處理中...",
				"sZeroRecords" : "沒有匹配結果",
				"sLoadingRecords" : "讀取中..."
			},
			"fnInitComplete" : function() {
				this.fnAdjustColumnSizing();
				bindSiteSearchTableTrEvent(); // 綁定探勘記錄TR事件 drag function by Charlie 2015/03/23
				if (srSeq == '0') {
					// 自動選擇第一筆資料
					$('#siteSearchTable tbody').find("tr:first").trigger('click');
				}
				$('#siteSearchTable tr:gt(0)').each(function(){
			    	trData = $('#siteSearchTable').dataTable().fnGetData($(this).closest("tr").index());
					if (trData != null && trData['sr_SEQ'] == srSeq) {
						$(this).trigger('click');	// 將原本資料預設進行點選動作
					}
				});
				
			}
		});
	}	
	
	function bindSiteSearchTableTrEvent() {
		//點選該筆資料 帶到下方欄位 成 編輯模式		
		$('#siteSearchTable tbody').off('click', 'tr').on( 'click','tr', function () {
			unUse = [];
			data = $('#siteSearchTable').dataTable().fnGetData($(this).closest("tr").index());
// 			$("#uploadBoutton").prop("disabled", false);
			
			// set tr highlight by Charlie
			$("#siteSearchTable tbody tr").removeClass('row_selected_highlight');
			$(this).addClass('row_selected_highlight');
			
			if (data == null) {
				return;
			}
			
			$("#srId").val(data['sr_ID']);
			$.ajax({
				url : CONTEXT_URL + "/st/searchRecord/searchTable",
				type : 'POST',
				data : {
					srId :data['sr_ID'],
					srSeq :data['sr_SEQ']
				},
				async : false,
				success : function(data) {
				  //alert(JSON.stringify(data));
				  //探勘序號
 				  $("#seq").text(data.rows[0].sr_SEQ);
				  $("#srSeq").val(data.rows[0].sr_SEQ);
				  //站台識別碼
				  $("#searchSiteId").val(data.rows[0].site_ID);
					//基站狀態
					osSiteStatusId();
				  //探勘日期
				  $("#srDate").val(data.rows[0].sr_DATE);
				  //探勘人員
				  $("#srPsn").val(data.rows[0].sr_PSN);
				  //探勘評估
				  $("#srEval").val(data.rows[0].sr_EVAL).change();
				  //探勘地址
				  $("#address").val(data.rows[0].addr);
				  $("#qq_zip").val(data.rows[0].zip);
				  $("#qq_city").val(data.rows[0].city);
				  $("#qq_area").val(data.rows[0].area);
				  $("#qq_village").val(data.rows[0].village);
				  $("#qq_road").val(data.rows[0].road);
				  $("#qq_adjacent").val(data.rows[0].adjacent);
				  $("#qq_lane").val(data.rows[0].lane);
				  $("#qq_alley").val(data.rows[0].alley);
				  $("#qq_subAlley").val(data.rows[0].sub_ALLEY);
				  $("#qq_doorNo").val(data.rows[0].door_NO);
				  $("#qq_doorNoEx").val(data.rows[0].door_NO_EX);
				  $("#qq_floor").val(data.rows[0].floor);
				  
				  $("#qq_floorEx").val(data.rows[0].floor_EX);
				  $("#qq_room").val(data.rows[0].room);
				  $("#qq_remark").val(data.rows[0].add_OTHER);
				  //經度
				  $("#siteLon").val(data.rows[0].lon);
					var lon = $("#siteLon").val();
					$("#siteLon").val(showAsFloat(lon));
				  //緯度
				  $("#siteLat").val(data.rows[0].lat);
				 	 var lat = $("#siteLat").val();
					$("#siteLat").val(showAsFloat(lat));
				  //機房型態
				  $("#baseType").val(data.rows[0].base_TYPE).change();
				  //建物高度
				  $("#height").val(data.rows[0].building_HEIGHT);
				  //建物樓層
				  $("#floor").val(data.rows[0].building_FLOOR);
				  //室內場所分類
				  $("#inBuilding").val(data.rows[0].indoor_OPTION).change();
				  //其他資訊
					if(data.rows[0].is_FREEENTER == 'Y'){
						$("#isFreeEnter").prop("checked",true);
						$("#isFreeEnter").val("Y");
					} else {
						$("#isFreeEnter").prop("checked",false);
						$("#isFreeEnter").val("N");
					}
					if(data.rows[0].is_KEY == 'Y'){
						$("#isKey").prop("checked",true);
						$("#isKey").val("Y");
					} else {
						$("#isKey").prop("checked",false);
						$("#isKey").val("N");
					}
					if(data.rows[0].is_INDOOR == 'Y'){
						$("#isInDoor").prop("checked",true);
						$("#isInDoor").val("Y");
					} else {
						$("#isInDoor").prop("checked",false);
						$("#isInDoor").val("N");
					}
					if(data.rows[0].need_MISC_LIC == 'Y'){
						$("#needMiscLic").prop("checked",true);
						$("#needMiscLic").val("Y");
					} else {
						$("#needMiscLic").prop("checked",false);
						$("#needMiscLic").val("N");
					}
					if(data.rows[0].has_MISC_LIC == 'Y'){
						$("#hasMiscLic").prop("checked",true);
						$("#hasMiscLic").val("Y");
					} else {
						$("#hasMiscLic").prop("checked",false);
						$("#hasMiscLic").val("N");
					}
					if(data.rows[0].line_REACHABLE == 'Y'){
						$("#lineReachable").prop("checked",true);
						$("#lineReachable").val("Y");
					} else {
						$("#lineReachable").prop("checked",false);
						$("#lineReachable").val("N");
					}
					//預估租金
					$("#expectRent").val(data.rows[0].expect_RENT);
					//共構/共站 
					$("#shareType").val(data.rows[0].share_TYPE).change();
// 					if(data.rows[0].share_TYPE == "1" || data.rows[0].share_TYPE == "2") {
// 						osLookup(data.rows[0].share_TYPE);
// 					} 
					//天線支架
					$("#antStand").val(data.rows[0].ant_STAND).change();
					//天線架設高度 
					$("#antSHight").val(data.rows[0].ant_S_HIGHT);
					//天線中心高度  
					$("#antCHight").val(data.rows[0].ant_C_HIGHT);
					//空間
					if (data.rows[0].space_ROOM == 'Y') {
						$("#spaceRoom").prop("checked",true);
						$("#spaceRoom").val("Y");
					}else{
						$("#spaceRoom").prop("checked",false);
						$("#spaceRoom").val("N");							
					}
					if (data.rows[0].space_ROOF == 'Y') {
						$("#spaceRoof").prop("checked",true);
						$("#spaceRoof").val("Y");
					}else{
						$("#spaceRoof").prop("checked",false);
						$("#spaceRoof").val("N");							
					}
					if (data.rows[0].space_TOP == 'Y') {
						$("#spaceTop").prop("checked",true);
						$("#spaceTop").val("Y");
					}else{
						$("#spaceTop").prop("checked",false);
						$("#spaceTop").val("N");							
					}
					if (data.rows[0].space_PLATFORM == 'Y') {
						$("#spacePlatform").prop("checked",true);
						$("#spacePlatform").val("Y");
					}else{
						$("#spacePlatform").prop("checked",false);
						$("#spacePlatform").val("N");							
					}
					if (data.rows[0].space_LAND == 'Y') {
						$("#spaceLand").prop("checked",true);
						$("#spaceLand").val("Y");
					}else{
						$("#spaceLand").prop("checked",false);
						$("#spaceLand").val("N");							
					}
					if (data.rows[0].space_INDOOR == 'Y') {
						$("#spaceIndoor").prop("checked",true);
						$("#spaceIndoor").val("Y");
					}else{
						$("#spaceIndoor").prop("checked",false);
						$("#spaceIndoor").val("N");							
					}
					if (data.rows[0].space_OUTDOOR == 'Y') {
						$("#spaceOutdoor").prop("checked",true);
						$("#spaceOutdoor").val("Y");
					}else{
						$("#spaceOutdoor").prop("checked",false);
						$("#spaceOutdoor").val("N");							
					}
					//坪數
					$("#footAge").val(data.rows[0].footage);
					//員工關係 
					$("#empRelation").val(data.rows[0].emp_RELATION);
					//探勘註解
					$("#locDesc").val(data.rows[0].loc_DESC);
					//基站名稱
			 		$("#siteTempName").val(data.rows[0].loc_NAME);
					//設備型態
					$("#siteHuntEqpTemp").val(data.rows[0].tbSiteSrSiteTemp.eqp_TYPE_ID).change();
					//設備機型
					$("#siteHuntEqpModelTemp").val(data.rows[0].tbSiteSrSiteTemp.eqp_MODEL_ID).change();
					//Feederless
					$("#feederlessTeam").val(data.rows[0].tbSiteSrSiteTemp.feederless).change();
					//Master Site
					$("#masterSiteTemp").val(data.rows[0].tbSiteSrSiteTemp.master_SITE);
					//Configuration
					$("#configuration").val(data.rows[0].tbSiteSrSiteTemp.bts_CONFIG);
// 					//速率(上)
// 					$("#speedUpload").val(data.rows[0].tbSiteSrSiteTemp.speed_UPLOAD);
// 					//速率(下) 
// 					$("#speedDownload").val(data.rows[0].tbSiteSrSiteTemp.speed_DOWNLOAD);
					//Donor BTS
					$("#donorBts").val(data.rows[0].tbSiteSrSiteTemp.donor_SITE);
					//涵蓋類別
					$("#includeRange").val(data.rows[0].tbSiteSrSiteTemp.coverage_TYPE).change();
					//室內天線涵蓋樓層
					$("#coverageInFloor").val(data.rows[0].tbSiteSrSiteTemp.coverage_IN_FLOOR);
					//功率
					$("#power").val(data.rows[0].tbSiteSrSiteTemp.power);
					//Cluster 
					$("#cluster").val(data.rows[0].tbSiteSrSiteTemp.cluster);
					//RNC-BTS 
					$("#rncBts").val(data.rows[0].tbSiteSrSiteTemp.rnc_BTS);
					//有室內涵蓋
					if (data.rows[0].tbSiteSrSiteTemp.coverage_INDOOR == 'Y') {
						$("#coverageIndoor").prop("checked",true);
						$("#coverageIndoor").val("Y");
					}else{
						$("#coverageIndoor").prop("checked",false);
						$("#coverageIndoor").val("N");							
					}
					//有美化施工
					if (data.rows[0].tbSiteSrSiteTemp.hidden == 'Y') {
						$("#hidden").prop("checked",true);
						$("#hidden").val("Y");
					}else{
						$("#hidden").prop("checked",false);
						$("#hidden").val("N");							
					}
					//網管On Air
					if (data.rows[0].tbSiteSrSiteTemp.nois_ON_AIR == 'Y') {
						$("#noisOnAir").prop("checked",true);
						$("#noisOnAir").val("Y");
					}else{
						$("#noisOnAir").prop("checked",false);
						$("#noisOnAir").val("N");							
					}	
					//有接L2_SWITCH
					if (data.rows[0].tbSiteSrSiteTemp.l2_SWITCH == 'Y') {
						$("#l2Switch").prop("checked",true);
						$("#l2Switch").val("Y");
					}else{
						$("#l2Switch").prop("checked",false);
						$("#l2Switch").val("N");							
					}						
				}
			});
			
			getShareComsChecks(data['sr_ID'], data['sr_SEQ']);	// 讀取共站業者 drag function by Charlie 20150323
			
			getSearchAntCfgRecords(data['sr_ID'], data['sr_SEQ']);	// 讀取天線組態	drag function by Charlie 20150323
			
			//已經完工 不能使用 附件上傳
			if ($("#odrStatus").val() == "OR09" || "${view}" == "view") {					
				$("#uploadBoutton").prop("disabled", true);
			} else {
				$("#uploadBoutton").prop("disabled", false);
			}
		});
	}
	
	//共站業者
	function getShareComsChecks(srId, srSeq) {
		$.ajax({
			url : CONTEXT_URL + "/st/searchRecord/shareSearch",
			type : 'POST',
			data : {
				"srId" : srId,
				"srSeq" : srSeq
			},					
			async : false,
			success : function(data) {
				//共站業者
				for (var i=0; i<data.rows.length; i++) {
					var shareCom = data.rows[i].share_COM;
					if (shareCom == "CHT"){
						$("#lookup"+shareCom).prop("checked",true);
					}
					if (shareCom == "FET"){
						$("#lookup"+shareCom).prop("checked",true);
					}	
					if (shareCom == "TWM"){
						$("#lookup"+shareCom).prop("checked",true);
					}	
					if (shareCom == "APT"){
						$("#lookup"+shareCom).prop("checked",true);
					}								
				}						
			}
		});
	}
	
	//天線組態
	function getSearchAntCfgRecords(srId, srSeq) {
		$.ajax({
			url : CONTEXT_URL + "/st/searchRecord/siteSrAntCfgTemp",
			type : 'POST',
			data : {
				'srId' : srId,
				'srSeq' : srSeq
			},
			async : false,
			success : function(data) {
				//alert("JSON -> "+JSON.stringify(data));
				//天線組態
				//選點資料時，預防資料重覆，先刪除區塊
				$("#testTableDIV").remove();
				$("#tableAnt").append("<div id='testTableDIV'></div>");

				for(var i=0; i<data.rows.length; i++) {
					if (data.rows[i].sr_ID != null) {
						var tableNum = addSrTable();
						//cell
							$("#cell-"+tableNum).val(data.rows[i].cell_ID).change();
						//訊號源
							$("#signalSRC-"+tableNum).val(data.rows[i].seg_SOURCE).change();
						//P-Code
							$("#pCode-"+tableNum).val(data.rows[i].p_CODE);
						//天線型號
							$("#antenna-"+tableNum).val(data.rows[i].ant_MODEL).change();
						//安裝高度
						$("#installH-"+tableNum).val(data.rows[i].ant_HIGH);
						//方向角
						$("#directionAngle-"+tableNum).val(data.rows[i].ant_ORIENT);
						//E-TILT
						$("#eTilt-"+tableNum).val(data.rows[i].e_TILT);
						//M-TILT
						$("#mTilt-"+tableNum).val(data.rows[i].m_TILT);
						//安裝方式
						$("#installStyle-"+tableNum).val(data.rows[i].setup_STYLE).change();
						//樓高
						$("#floor-"+tableNum).val(data.rows[i].floor_LEVEL);
						//鐵塔	
						$("#tower-"+tableNum).val(data.rows[i].tower_HEIGHT);
						//屋突	
						$("#roof-"+tableNum).val(data.rows[i].penthouse_HEIGHT);
						//地址	
						$("#arrd-"+tableNum).val(data.rows[i].addr);
						//經度	
						$("#lon-"+tableNum).val(data.rows[i].lon);
					 	 var lon = $("#lon-"+tableNum).val();
							$("#lon-"+tableNum).val(showAsFloat(lon));
						//緯度
						$("#lat-"+tableNum).val(data.rows[i].lat);
					 	 var lat = $("#lat-"+tableNum).val();
							$("#lat-"+tableNum).val(showAsFloat(lat));

						//Feeder cable type	
						$("#feederType-"+tableNum).val(data.rows[i].f_CABLE_TYPE).change();
						//Feeder cable 長度	
						$("#feederL-"+tableNum).val(data.rows[i].f_CABLE_LEN);
						//Jumper cable 長度
						//alert(data.rows[i].j_CABLE_LEN);
						$("#jumper-"+tableNum).val(data.rows[i].j_CABLE_LEN);
						//覆蓋區域
						$("#coverageArea-"+tableNum).val(data.rows[i].cover_REG);
						$("#ant_zip-"+tableNum).val(data.rows[i].zip);
						$("#ant_city-"+tableNum).val(data.rows[i].city);
						$("#ant_area-"+tableNum).val(data.rows[i].area);
						$("#ant_village-"+tableNum).val(data.rows[i].village);
						$("#ant_road-"+tableNum).val(data.rows[i].road);
						$("#ant_adjacent-"+tableNum).val(data.rows[i].adjacent);
						$("#ant_lane-"+tableNum).val(data.rows[i].lane);
						$("#ant_alley-"+tableNum).val(data.rows[i].alley);
						$("#ant_subAlley-"+tableNum).val(data.rows[i].sub_ALLEY);
						$("#ant_doorNo-"+tableNum).val(data.rows[i].door_NO);
						$("#ant_doorNoEx-"+tableNum).val(data.rows[i].door_NO_EX);
						$("#ant_floor-"+tableNum).val(data.rows[i].floor);
						$("#ant_floorEx-"+tableNum).val(data.rows[i].floor_EX);
						$("#ant_room-"+tableNum).val(data.rows[i].room);
						$("#ant_remark-"+tableNum).val(data.rows[i].add_OTHER);
					}
					
					if("${view}" == 'view' ){
						$("#view-"+tableNum+" table tbody tr :input ").prop("disabled", true);		
					   //console.log($("div input").hasClass("testclass"));
						$("#toggleBtn").prop("disabled", false);
					}
				}		
			}
		});	
	}
	
	function getEqpModel(eqpTypeId,tagName){
		$.ajax({
			url : CONTEXT_URL + "/st/st002/search/eqpModel",
			type : 'POST',
			data : {
				eqpTypeId :eqpTypeId,
			},
			async : false,
			success : function(datas){
				$('#'+tagName).html("");
				$('<option value="">---請選擇---</option>').appendTo('#'+tagName);
				for(var data in datas){
					$('<option value="'+data+'">'+datas[data]+'</option>').appendTo('#'+tagName);
				}
			}					
       	});
	}
	
	//委外申請----委外廠商
	function osVenSelect(){
		$.ajax({
			url : CONTEXT_URL + "/st/outSourcing/out/osVenSearchTable",
			
			type : "POST",
			dataType : 'json',
			data:{
				"workId" :  $("#workId").val()
				
			},
			async : false,
			success : function(data) {
				//alert(JSON.stringify(data));
				if(data.success){
					if(data.rows.length > 0){
						$("#coVatNoTab2  option").remove();
						$("#coVatNoTab2").append("<option value=''>--請選擇--</option>");
						for(var i=0; i<data.rows.length; i++){		
							$("#coVatNoTab2").append("<option value="+data.rows[i].co_VAT_NO+">"+data.rows[i].co_NAME+"</option>");
						}		
					}
					
				}
			}
		});
	}
	
	//委外申請----委外廠商--工項設定--大項
	function itemMainSelect(){
		$.ajax({
			url : CONTEXT_URL + "/st/outSourcing/out/itemMainSearch",
			type : "POST",
			dataType : 'json',
			data:{
				
			},
			async : false,
			success : function(data) {
				//alert(JSON.stringify(data));
				if(data.success){
					if(data.rows.length > 0){
						$("#mainItemTab2  option").remove();
						$("#mainItemTab2").append("<option value=''>--請選擇--</option>");
						//大項
						for(var i=0; i<data.rows.length; i++){		
							$("#mainItemTab2").append("<option value="+data.rows[i].cat_ID+">"+data.rows[i].cat_NAME+"</option>");
						}
					}
				}
			}
		});
	}
	
	function setOsNumberMap() {
		//alert($("input[name=itemIdTab2]").length);
		$("input[name=itemIdTab2]").each(function(i){
// 			console.log("Before:" + $(this).val() + "@@@" + $("input[name=osNumber]")[i].value);
			osMap.put($(this).val(), $("input[name=osNumber]")[i].value);
		});
	}
	
	function setBackOsItemOsNumber() {
		$("input[name=itemIdTab2]").each(function(i){
			if (osMap.containsKey($(this).val())) {
				var id  = $("input[name=osNumber]")[i].id;			
				$("#" + id).val(osMap.get($(this).val())).blur();
			}
// 			console.log("After:" + $(this).val() + "@@@" + $("input[name=osNumber]")[i].value);
		});
		if(!osMap.isEmpty()) osMap.clear();
	}
	
	//小數點 補0
	function showAsFloat(n) {
        return parseFloat(n).toFixed(6);
    }
	
	function tabOrderPageFun(){
		$.ajax({
			url : CONTEXT_URL + "/st/st003/tabOrderPage",
			dataType : 'json',
			data : {
					"orderId" :  $("#orderId").val()
				},
			success : function(data){
				//alert(data.msg);
				//alert(JSON.stringify(data));
				//alert(data.rows.length);
				var pageId="", cantEdit = "", pageIdStr = "";
				for(var i =0; i< data.rows.length; i++){
					pageId = data.rows[i].page_ID;
					cantEdit = data.rows[i].is_EDIT == 'N';
					if(pageId == '委外申請' && cantEdit) setReadOnly(2);
					if(pageId == '探勘紀錄' && cantEdit) setReadOnly(3);
					pageIdStr += pageId + ",";
				}
				
				if(pageIdStr.indexOf("委外申請,") < 0) {
					$("#tabs2").hide();
					$("#tabs-2").hide();
				}

                if(pageIdStr.indexOf("探勘紀錄,") < 0) {
                	$("#tabs-3").hide();
                	$("#tabs3").hide();
				}
			}	
		});
	}
	
	function setReadOnly(tabNo) {
		$("#tabs-"+tabNo+" :input ").prop("disabled", true);
	}

	function disabledBtn(status, loginUsrVendor){
        //alert(status + ":" +loginUsrVendor);
		$("button.default-disabled").prop("disabled",true);
		$("#addTab2").prop("disabled",false);
		
		// 先鎖定可編輯欄位，再依可編輯狀態打開
		$("#coVatNoTab2").prop("disabled",true); //廠商名稱
		$("#poNoTab2").prop("disabled",true); //po單號
		$("#showOsDescTab2").prop("disabled", true);  // 委外說明
		
		if(status == 'OS01' || status == 'OS03'){ // 草稿 駁回
			$("#saveTab2").prop("disabled",false); //存檔
			$("#poItemTab2").prop("disabled",false); //工項設定
			$("#applicationBtn").prop("disabled",false); //申請
			$("#applicationCancelBtn").prop("disabled",false); //取消申請
			
			$("#coVatNoTab2").prop("disabled", false); //廠商名稱
			$("#poNoTab2").prop("disabled", false); //po單號
			$("#showOsDescTab2").prop("disabled", false);  // 委外說明
		} else if (status == 'OS02'){ //審核中
			$("#coVatNoTab2").prop("disabled",true); //廠商名稱
			$("#poNoTab2").prop("disabled",true); //po單號
			$("#showOsDescTab2").prop("disabled", true);  // 委外說明
		} else if (status == 'OS04'){ //待派工
// 			$("#saveTab2").prop("disabled",false); //存檔
		    $("#applicationCancelBtn").prop("disabled",false); //取消申請
			$("#outsourcingButton").prop("disabled",false); //委外派工
		} else if (status == 'OS05'){ //已派工 
			if ($("#checkDabClick").val() != "view"){
				$("#applicationCancelBtn").prop("disabled",false); //取消申請
				$("#outsourcingButton").prop("disabled",false).html("<span><i class='fa fa-envelope-o'></i></span>委外通知"); //委外通知
				$("#applyWorkButton").prop("disabled",false); //驗收
			}		    
			
		} else if (status == 'OS06'){ //驗收送審
			
		} else if (status == 'OS07'){ //已驗收
			
		} else if (status == 'OS08'){ //已取消
           // $("#addTab2").prop("disabled",false);
			$("#poItemTab2").prop("disabled",true);
		}
		
		//登入者若是廠商(委外申請)
		if (loginUsrVendor == "true"){
			$("button.vendorWork-disable").prop("disabled", true);
// 			$("#tabs-1 :input ").prop("disabled", true);
// 			$("#tabs-2 :input ").prop("disabled", true);
// 			$("#tabs-3 :input ").prop("disabled", true);
		}
	}
	
	function saveBtnTabs2(str) {
		//委外申請---存檔button
		if ($("#coVatNoTab2").val() == "") {
			alert("廠商名稱未選擇");
			return;
		}
		if ($("#poNoTab2").val() == "") {
			alert("PO單位未選擇");
			return;
		}
		if ($("#showOsDescTab2").val().length > 200) {
			alert('委外說明超過最大限制200字');
			return;
		}
		
		var osNumber =[];
		var isNumber = true;
		itemMainQueryAfterFun();	// 最後重新計算總額
		
		//派工數量
		$("#toItemMainTable :input[name='osNumber']").each(function(){
			if(!qtyCheck(this.value, "派工數量")){
				isNumber = false;
				return false;
			}
			osNumber.push($(this).val());
		});
		
		$.ajax({
			url : CONTEXT_URL + "/st/outSourcing/out/save",
			type : 'POST',
			dataType : "json",
			data : {
				orderId : $("#orderId").val(), //工單編號  
				coVatNo : $("#coVatNoTab2").val(), // 廠商統編  
				poId : $("#poNoTab2").val(), //PO_ID 
				eqpType : "${siteWork.EQP_TYPE_ID}", //設備型態  
				eqpModel : "${siteWork.EQP_MODEL_ID}", //機型  
				osDesc : $("#showOsDescTab2").val(), //委外說明 
				amount : $("#priceTab2").val(), //總金額  
				osId : $("#osIdTab2").val(), //派工單號
				itemId : $("#checkBoxItemIdTab2").val(), //工項設定的ITEM_ID
				osNumber : osNumber.toString(),	// 派工數量
				poNo : $("#poNoTab2").find("option:selected").text()
			},
			async : false,
			success : function(data) {
				//alert(JSON.stringify(data));
				//str 判斷msg的訊息 變數 0
                if(data.success && str == '0'){
                	alert("存檔成功");
                }
				
                $("#osIdTab2").val(data.msg);	// 將OS_ID塞入
				outSourceTableFun(data.msg);
				toItemMainTableFun(data.msg);
			}
		});
	}
	//簽核探看紀錄選取
	function checkedSearchRecord(srId,srSeq){
		if($("#searchBox"+srSeq).prop("checked")){
			$("input[name='searchBox']").prop("checked",false);
			$("#searchBox"+srSeq).prop("checked",true);
			$("#checkedSearchRecordData").val('{"srId":"'+srId+'","srSeq":"'+srSeq+'"}');
			$("#workflowJsonData").val($("#checkedSearchRecordData").val());
		}
	}
	//簽核驗證
	function validateWFVerify(resultValue){
		var result = false;
		//駁回時需要參數
		if(resultValue == "REJECT"){
			$("#workflowJsonData").val('{"srId":"","srSeq":""}');
			return true;
		}else if("${applyType}" == "NewSiteRequest"){
			return true;
		}
		$("input[name='searchBox']").each(function(){
			if($(this).prop("checked")){
				result = true;
			}
		});
		if(result != true){
			alert("請檢視探勘紀錄或是勾選一筆探勘紀錄");
		}
		return result;
	}
	
	$("#selectSrIdBtn").click(function (){
		var targetFrameId = "showSrIdPage";
		$.ajax({
			url : CONTEXT_URL + "/st/sr/ST002SP2/init",
			type : 'POST',
			dataType : "html",
			data:{
				"callBackFun" :  $("#srIdCallBackFun").val(),
				"targetFrameId" : targetFrameId,
			},
			async : false,
			success : function(data) {
				initialDialogFrame(targetFrameId, "SR ID查詢",data);
			}
		});
	});
	
	function srIdCallBackFun(srObject){
		var obj = JSON.parse(srObject);
		$("#siteHuntSrId").val(obj.sr_ID);
		$("#showSiteHuntSrId").text(obj.sr_ID);
		$("#siteHuntLon").val(obj.sr_LON).prop("readonly",true);
		$("#siteHuntLat").val(obj.sr_LAT).prop("readonly",true);
		$("#coverRange").val(obj.sr_COVER_RANGE).prop("readonly",true);
		$("#siteHuntCity").val(obj.sr_CITY).change().prop("disabled",true);
		$("#siteHuntArea").val(obj.sr_AREA).change().prop("disabled",true);
	}
	function doValidate(){
		var message = "";
		$.ajax({
			url : CONTEXT_URL + "/st/st003/doValidate",
			type : 'POST',
			dataType : "json",
			data:{
				"srId" : $("#siteHuntSrId").val(),
				"feqId" : $("#feqId").val(),
				"eqpTypeId" : $("#siteHuntEqpType").val(),
				"btsSiteId" : $("#siteHuntBtsSiteId").val(),
				"city" : $("#siteHuntCity").val(),
				"area" : $("#siteHuntArea").val(),
				"coverageType" : $("#coverageType").val(),
				"workType" : $("#siteHuntWorkType").val()
			},
			async : false,
			success : function(data) {
				if(data.row != ""){
					message = data.row;
				}
			}
		});
		return message;
	}
</script>
</head>
<body id="_elements">
	<div class="row">
		<div class="col-xs-12 col-sm-12">
			<div class=" ui-draggable ui-droppable">
				<div class="box-header">
					<div class="box-name">
						<i class="fa fa-search"></i> <span>尋點作業處理</span>
					</div>
<!-- 					<div class="box-icons"> -->
<!-- 						<a class="collapse-link"> <i class="fa fa-chevron-up"></i> -->
<!-- 						</a> <a class="expand-link"> <i class="fa fa-expand"></i> -->
<!-- 						</a> -->
<!-- 					</div> -->
					<div class="no-move"></div>
				</div>
				<div class="box-content">
					<form class="form-horizontal" id="addLocatiuonForm" action="">
						<button class="btn btn-primary btn-label-left retiredWork-disabled hideWhenSign"
							style="margin-right: 10px" type="button" id="pickWorkBut">
							<span><i class="fa fa-hand-o-right"></i></span>接工</button>
						<button class="btn btn-primary btn-label-left retiredWork-disabled hideWhenSign"
							style="margin-right: 10px" type="button" id="retiredWorkBut">
							<span><i class="fa fa-reply-all"></i></span>退工</button>
						<button class="btn btn-primary btn-label-left retiredWork-disabled hideWhenSign"
							style="margin-right: 10px" type="button" id="completionWorkBut">
							<span><i class="fa fa-legal"></i></span>完工</button>
						<div id="tabs">
							<ul>   
								  <li><div id="tabs1"><a href="#tabs-1" id ="workContant">作業內容</a></div></li>
								  <li><div id="tabs2"><a href="#tabs-2" id ="outSourceApply">委外申請</a></div></li>
								  <li><div id="tabs3"><a href="#tabs-3" id ="searchRecord">探勘記錄</a></div></li>
							</ul>
							<div id="tabs-1">
								<table style="width: 100%">
									<tr>
										<td nowrap="nowrap" align='center' colspan="2">
											<button class="btn btn-primary btn-label-left retiredWork-disabled"
												style="margin-right: 20px" type="button" id="saveBtnTabs1"><span><i class="fa fa-save"></i></span>存檔
											</button>
											<button class="btn btn-primary btn-label-left retiredWork-disabled" type="button" id="downloadAttch">
												<span><i class="fa fa-download"></i></span>附件下載</button>
										</td>
									</tr>
									<tr>
										<td width="15%" nowrap="nowrap"><label
											class="col-sm-12 control-label">工單號碼 :</label></td>
										<td width="20%">
											<p style="padding-top: 15px; padding-left: 15px;" id="showOrderId"></p>
											<input id="orderId" name="orderId" type="hidden"></input>
										</td>
										<td width="15%" nowrap="nowrap"><label
											class="col-sm-12 control-label">處理狀態 :</label></td>
										<td width="20%">
											<p style="padding-top: 15px; padding-left: 15px;" id="showOdrStatus"></p>
											<input id="odrStatus" name="odrStatus" type="hidden"></input>
										</td>
										<td width="10%"><label class="col-sm-12 control-label">接工人員
												:</label></td>
										<td width="20%">
											<p style="padding-top: 15px; padding-left: 15px;" id="showPickWorkName"></p>
											<input id="pickWorkName" name="pickWorkName" type="hidden"></input>
										</td>
									</tr>
									<tr>
										<td>
											<div class="clearfix">&nbsp;</div>
										</td>
									</tr>
									<tr>
										<td><label class="col-sm-12 control-label"
											style="margin-bottom: 60px">處理說明 :</label></td>
										<td colspan="5" nowrap="nowrap">
											<div style="margin-top: 5px">
												<textarea id="endDesc" name="endDesc"
													style="resize: none; width: 94%; border-left-width: 1px; margin-left: 15px; margin-top: 4px;"
													rows="3">${fn:replace(endDesc,"\\n",'<br>') }
													</textarea>
											</div>
										</td>
									</tr>
									<tr>
										<td><label class="col-sm-12 control-label"
											style="margin-bottom: 10px">完工簽核意見 :</label></td>
										<td colspan="5" >
											<div class="form-group"
												style="margin-left: 15px; width: 94%; margin-bottom: 10px;">
												<c:import url="/commom/signHistory">
													<c:param name="processType" value="${finalProcessType}"></c:param>
													<c:param name="applyNo" value="${orderId}"></c:param>
												</c:import>
											</div>
										</td>
									</tr>
									</table><hr>
									<table style="width: 100%">
										<jsp:include page="/WEB-INF/views/ajaxPage/st/SiteHuntApply.jsp" />
									</table>
							</div>
							
							<div id="tabs-2">
							    <table id="outSourceTable" class="table table-bordered table-hover table-heading table-datatable" width="96%">
									<thead style="background-color: #f0f0f0;">
										<tr>
											<th class="theadth" style="width: 300px;">派工單號</th>
											<th class="theadth" style="width: 150px;">廠商</th>
											<th class="theadth" style="width: 150px;">PO單號</th>
											<th class="theadth" style="width: 150px;">申請日期</th>
											<th class="theadth" style="width: 150px;">派工狀態</th>
											<th class="theadth" style="width: 150px;">已完驗</th>
										</tr>
									</thead>
							
								</table> 
								<h4 class="page-header"></h4>
								<button class="btn btn-primary btn-label-left retiredWork-disabled vendorWork-disable" style="margin-right: 10px"
									type="button" id="addTab2"><span><i class="fa fa-plus"></i></span>新增</button>
								<button class="btn btn-primary btn-label-left retiredWork-disabled default-disabled endorWork-disable vendorWork-disable" style="margin-right: 10px"
									type="button" id="saveTab2" onclick="saveBtnTabs2('0')"><span><i class="fa fa-save"></i></span>存檔</button>
								<button class="btn btn-primary btn-label-left retiredWork-disabled default-disabled endorWork-disable vendorWork-disable" style="margin-right: 10px"
									type="button" id="poItemTab2"><span><i class="fa fa-cog"></i></span>工項設定</button>
								<button class="btn btn-primary btn-label-left retiredWork-disabled default-disabled  endorWork-disable vendorWork-disable" style="margin-right: 10px"
									type="button" id="applicationBtn"><span><i class="fa fa-legal"></i></span>申請</button>
								<button class="btn btn-primary btn-label-left retiredWork-disabled default-disabled endorWork-disable vendorWork-disable" style="margin-right: 10px"
									type="button" id="applicationCancelBtn"><span><i class="fa fa-times"></i></span>取消申請</button>
								<button class="btn btn-primary btn-label-left retiredWork-disabled default-disabled endorWork-disable vendorWork-disable" style="margin-right: 10px"
									type="button" id="outsourcingButton" ><span><i class="fa fa-envelope-o"></i></span>委外派工</button>
								<button class="btn btn-primary btn-label-left retiredWork-disabled default-disabled endorWork-disable vendorWork-disable" style="margin-right: 10px"
									type="button" id="applyWorkButton" ><span><i class="fa fa-legal"></i></span>驗收</button>
															<table style="width: 100%">
																	<jsp:include page="/WEB-INF\views\ajaxPage\st\OutSource.jsp" />
																</table>
				
							</div>


							<!-- 探勘尋點 -->
							<div id="tabs-3">
								<div>
									<table id="siteSearchTable" class="table table-bordered table-hover table-heading table-datatable" width="96%">
											<thead style="background-color: #f0f0f0;">
											<tr>
												<th class="theadth" style="width: 24px">核選</th>
												<th class="theadth" style="width: 70px">作業編號</th>
												<th class="theadth" style="width: 70px">基站編號</th>
												<th class="theadth" style="width: 20px">探勘序號</th>
												<th class="theadth" style="width: 442px">探勘地址</th>
												<th class="theadth" style="width: 70px">探勘日期</th>
												<th class="theadth" style="width: 70px">探勘人員</th>
												<th class="theadth" style="width: 50px">探勘評估</th>
												<th hidden="hidden">SR_ID</th>
											</tr>
										</thead>
									</table>
								</div>

								<h4 class="page-header"></h4>
								<button class="btn btn-primary btn-label-left retiredWork-disabled"
									style="margin-right: 10px" type="button" id="saveBoutton"><span><i class="fa fa-save"></i></span>存檔</button>
								<button class="btn btn-primary btn-label-left retiredWork-disabled"
									style="margin-right: 10px" type="button" id="uploadBoutton"><span><i class="fa fa-upload"></i></span>附件上傳</button>
								<button class="btn btn-primary btn-label-left retiredWork-disabled"
									style="margin-right: 10px" type="button" id="copyNewBoutton"><span><i class="fa fa-copy"></i></span>複製為新紀錄</button>
								<button class="btn btn-default btn-label-left retiredWork-disabled"
									style="margin-right: 10px" type="button" id="newDataBoutton"><span><i class="fa fa-undo txt-danger"></i></span>重置</button>
								<button class="btn btn-primary btn-label-left retiredWork-disabled" type="button" id="searchDownloadAttch">
									<span><i class="fa fa-download"></i></span>附件下載</button>									

								<table style="width: 100%">
										<jsp:include page="/WEB-INF/views/ajaxPage/st/SearchRecord.jsp" />
								</table>
	
								<!--基站資訊Start -->
								<div>
									<label class="col-sm-1 control-label">基站資訊 :</label>
									<button class="btn btn-primary btn-app-xs" type="button"
										id="toggleBtn" onclick="runEffect()">
										<span><i class="fa fa-chevron-up"></i></span>
									</button>
									<hr>
									<style>
										hr {
											display: block;
											margin-top: 0.5em;
											margin-bottom: 0.5em;
											margin-left: auto;
											margin-right: auto;
											border-style: inset;
											border-width: 1px;
										}
									</style>
								</div>		
								
 								<table style="width: 100%">
										<jsp:include page="/WEB-INF/views/ajaxPage/st/SiteLines.jsp" />
								</table>	
															
								
							</div>  <!-- tabs-3 End -->
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div id="showSiteLocAddrPage"></div>
	<input id="siteHuntApplyEvent"  type="hidden"></input>
	<!-- 廠商權限 -->
	<input id="loginUsrVendor" type="hidden" name="loginUsrVendor" value="${loginUsrVendor }"></input>
	<!-- LOGIN -->
	<input id="userName" type="hidden"></input>
	<input id="showUserName" type="hidden"></input>
	
	<!-- 工務類別 -->
	<input id="workType" type="hidden"></input>
	<!-- 基站編號 -->
	<input id="siteHuntWorkTypeInput" type="hidden"></input>
	<div id="showFileDownloadFrame"></div>
	<!-- 工項設定 -->			
	<div id="showPoItemPag" >
	<input id="locationId" type="hidden"/>
		
	<input id="disableAllBtn" type="hidden" value="false" >    
	</div>
	<!-- 探勘 -附件上傳 -->
	<div id="showFileUploadFrame"></div>
	<!-- 判斷是否點擊 -->
	<input id ="checkDabClick" type="hidden" value="${checkDabClick}"> 
	
	<input id="tempSeq" type="hidden" value="false" > 
	
	<!-- 簽核探勘紀錄勾選 -->
	<input id="checkedSearchRecordData" type="hidden">
	
	<!-- 尋點委外申請簽核、驗收類型 -->
	<input id="os_process_apply" name="os_process_apply" type="hidden" value="SearchSiteOutsourcingApply" /> 
	<input id="os_process_accept" name="os_process_accept" type="hidden" value="SearchSiteOutsourcingAccept" />
	<input id="srIdCallBackFun" value="srIdCallBackFun" type="hidden"></input>
	<div id="showSrIdPage"></div>
	<!-- 是否要alert -->
	<input id="notAlert" type="hidden" />
</body>
</html>