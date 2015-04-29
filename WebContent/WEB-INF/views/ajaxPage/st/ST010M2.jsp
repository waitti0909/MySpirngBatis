<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">
<head>
<title>接工</title>
<meta charset="utf-8">

<style type="text/css">
.contentStyle {
	padding-top: 6px;
	padding-left: 15px;
}
</style>
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						osSiteOrderType();
						$("#lineInfoSiteId").val($("#siteBuildSiteId").val());
						$("#tabs").tabs();

						$("#tabs-1 :input ").prop("disabled", true);
						//接工
						$("#pickWorkBut").prop("disabled", true);
						//退工
						$("#retiredWorkBut").prop("disabled", true);
						//完工
						$("#completionWorkBut").prop("disabled", true);
						//重新工單
						$("#newStaetWorkBut").prop("disabled", true);
						//附件下載
						$("#downloadAttch").prop("disabled", true);
						//儲存
						$("#saveBtnTabs1").prop("disabled", true);
						$("#siteBuildApplyBtnsTr").hide();
						$("#fileDownloadsBtn").hide();
						
						//委外申請
						//==============================================================================
						$("#outSourceApply").click(function () {
							outSourceTableFun('0');
							osVenSelect();
						}); //tabs2
						
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
							
// 							$("#showPoIdTab2").text($("#poNoTab2").find("option:selected").text());
// 							$("#showVenTab2").text($("#coVatNoTab2").find("option:selected").text());
// 							$("#showYearTab2").text($("#poYearTab2").val());
							$("#checkBoxItemIdPoItem").val($("#checkBoxItemIdTab2").val());
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
										"aplDept" :  $("#repTeamTab2").val(),    //抓派工單位
										"processType" : $("#os_process_apply").val()
									},
									async : false,
									success : function(data) {
										//alert(JSON.stringify(data));
// 										saveBtnTabs2("1");
										alert(data.msg);
										$("#addTab2").prop("disabled",false);
										if (data.success){
											$("#saveTab2").prop("disabled",false);
											$("#poItemTab2").prop("disabled",false);
											$("#applicationBtn").prop("disabled",true);
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
									"osId" :  $("#osIdTab2").val(),
									"status" : $("#orderStatusTab2").val()
								},
								async : false,
								success : function(data) {
									//alert(JSON.stringify(data));
									alert(data.msg);
// 									$("#outSourceApply").click();
									if (data.success) {
										outSourceTableFun($("#osIdTab2").val());	// reload outsource list data table
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
						
						//委外申請-委外派工
						$("#outsourcingButton").click(function(){
							var osId =  $("#osIdTab2").val();
							if(!osId){
								alert("請選一筆資料！");
								return false;
							}
							if(!confirm("確定要進行委外派工？")){
								return false;
							}
							var sendMail = confirm("是否發e-mail通知廠商？");
							var continueSend = true;
							var mail='';
							var ccmail='';
							//alert($("#osIdTab2").val());
							if(sendMail){
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
											continueSend = confirm('連絡人無e-mail資料!\r\n是否要繼續派工？');
										} else{
											mail = data.row.con_EMAIL;
										}
										ccmail = data.row.con_EMAIL2;
										
									},
									
									error:function(data){
									}
								});
							}// if(sendMail) end
							
							
							if(sendMail && continueSend){
								$.ajax({
									url : CONTEXT_URL + "/st/outSourcing/out/sendMail",
									type : 'POST',
									dataType : "json",
									data:{"mail" :mail,'ccmail':ccmail,"osId" :  osId},
									async : false,
									success : function(data) {
										alert(data.msg);
										table.fnDraw();
										$('#outSourceApply').trigger('click');
										outSourceTableFun($("#osIdTab2").val());
									}//success end
								});// ajax end
								
							}//if(sendMail && continueSend) end
							
						});//委外申請-委外派工 end
						//委外申請-驗收
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
							itemMainQueryAfterFun();	// 最後重新計算總額
							
							//驗收數量
							$("#toItemMainTable :input[name='apNumber']").each(function(){
								if(!qtyCheck(this.value, "驗收數量")){
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
						
						//完工(送審核)
						$("#completionWorkBut").click(function() {
							if(confirm("確定要完工進行主管審核？")){
								$.ajax({
									url : CONTEXT_URL + "/st/st010/verifyOrder",
									dataType : 'json',
									async : false,
									data : {
										"workId" :  $("#workId").val(),
										"orderId" :  $("#orderId").val()
									},
									success : function(data){
										alert(data.msg);
										if(data.success){
											$("#disableAllBtn").val(true);
											$("#retiredWorkBut").hide();
											$("#completionWorkBut").hide();
											$('#btn_search').click();
											$.fancybox.close();
										}
									}	
								}); 
							}
						});
						//=================委外申請===================================================
						//物料操作
						$("#tabs3").on("click",function(){
							$("#orderIdMer").val($("#orderId").val());
						    $('#reqDate').datepicker();
						    $('#remDate').datepicker();
						    meterialItem($("#orderId").val());
						    initMrq ()
//                         	//取得操作類別清單
							$.ajax({
								url : CONTEXT_URL + "/st/invMeterial/init",
								
								type : "POST",
								dataType : 'json',
								data:{
									"orderId" :  $("#orderId").val()
								},
								async : false,
								success : function(data) {
									if(data.success){
										//取得操作類別清單
										var optTypeList=data.row['optTypeList'];
										if(optTypeList.length > 0){
											$("#optType  option").remove();
											$("#optType").append("<option value=''>--請選擇--</option>");
											for(var i=0; i<optTypeList.length; i++){		
												$("#optType").append("<option value="+optTypeList[i].value+">"+optTypeList[i].label+"</option>");
											}		
										}
										//取得派工單號
										var outSourcingList=data.row['outSourcingList'];
										if(outSourcingList.length > 0){
											$("#osId  option").remove();
											$("#osId").append("<option value=''>--請選擇--</option>");
											for(var i=0; i<outSourcingList.length; i++){		
												$("#osId").append("<option value="+outSourcingList[i].os_ID+">"+outSourcingList[i].os_ID+"</option>");
											}		
										}
										
									}
								}
							});
						    
						    $("#osId").change(function(){
							    $.ajax({
									url : CONTEXT_URL + "/st/invMeterial/searchInvWareHouse",
									
									type : "POST",
									dataType : 'json',
									data:{
										"orderId" : $("#orderId").val(),
										"osId" : $(this).val()
									},
									async : false,
									success : function(data) {
										$("#whIdMer  option").remove();
										$("#whIdMer").append("<option value=''>--請選擇--</option>");
										for(var i = 0 ; i < data.rows.length ; i++){
											var warehouse = data.rows[i];
											$("#whIdMer").append("<option value="+warehouse.wh_id+">"+warehouse.wh_name+"</option>");
										}
									}
								});
						    });
						    
							$('#meterialTable tbody').find("tr:first").trigger('click');
							$("#osId").change();
						});//tab3 onclick end
						
						
						
						//btnMrqtrExXls
						
						//當倉庫別改變時
						$("#whIdMer").change(function() {
							if($("#whIdMer").val()!="")
							{
								$("#btnMrqtrExXls").prop("disabled",false);
								   if($("#optType").val()=='RTN')
								   {
									    var optId=$("#optId").val();
									    if(optId=="")
									    {
											var data = {
		 											orderId :$("#orderId").val(), //工單編號
		 											whId : $("#whIdMer").val() //倉庫編號
												};
												$("#rtntrItem").setGridParam({
													datatype : "json",
													postData : data,
													mtype : 'POST',
													page : 1
												}).jqGrid().trigger("reloadGrid");
									    }
									    else
									    {
									    	rtntrItemByQuery($("#orderId").val(),$("#whIdMer").val());
									    }
								   }
							} else {
								$("#btnMrqtrExXls").prop("disabled",true);
							}
						 });

						
						$("#optType").change(function() {
							  if($("#optType").val()=='MRQ')
							  {
								initMrq();
							  }
							  if($("#optType").val()=='RTN')
							  {
								initRTN();
							  }	
							  if($("#optType").val()=='INS')
							  {
								initINS();
							    var optId=$("#optId").val();
							    if(optId=="")
							    {
									var data = {
 											orderId :$("#orderId").val() //工單編號
										};
										$("#remInsItem").setGridParam({
											datatype : "json",
											postData : data,
											mtype : 'POST',
											page : 1
										}).jqGrid().trigger("reloadGrid");
							    }
							    else
							    {
							    	$("#remInsItem").jqGrid('clearGridData');
							    }
							  }
							  if($("#optType").val()=='REM')
							  {
								  initREM();
							    var optId=$("#optId").val();
							    if(optId=="")
							    {
									var data = {
 											orderId :$("#orderId").val() //工單編號
										};
										$("#remItem").setGridParam({
											datatype : "json",
											postData : data,
											mtype : 'POST',
											page : 1
										}).jqGrid().trigger("reloadGrid");
							    }
							    else
							    {
							    	$("#remItem").jqGrid('clearGridData');
							    }
							  }	
						});
						
						//物料操作---新增button
						$("#btnMrqtr").click(function() {
							$("#optType").prop("disabled", false);
							$("#osId").prop("disabled", false);
							$("#whIdMer").prop("disabled", false);
							$("#showOptDesc").prop("disabled", false);
							$("#btnMrqtrExXls").prop("disabled", false);
							$("#btnMrqtrIpmXls").prop("disabled", false);
							$("#btnMrqtrApply").prop("disabled", false);
							$("#btnRemtr").prop("disabled", false);
							$("#showStatusMer").text("草稿");
							$("#statusMer").val("草稿");
							$("#showOptId").text("");
							$("#optId").val("");							
							$("#showAppDept").text("");
							$("#appDept").val("");					
							$("#showAppUser").text("");
							$("#appUser").val("");															
							$("#showAplTime").text("");
							$("#aplTime").val("");									
							$("#reqDate").val("");
							$("#remDate").val("");
							$("#osId").val("").change();
							$("#showOptDesc").prop("value", "");
							$("#whIdMer").val("").change();
							$("#optType").val("MRQ").change();
							
							var rownum=$("#meterialItem").dataTable().fnGetData();
							if(rownum.length!=0)
							{
								var table = $('#meterialItem').DataTable();
								table.fnClearTable();
							}
							$("#rtntrItem").jqGrid('clearGridData');
							$("#remInsItem").jqGrid('clearGridData');
							$("#remItem").jqGrid('clearGridData');
						});
						
						
						
						//物料操作---申請button
						$("#btnMrqtrApply").click(function() {
							  if($("#optType").val()=='MRQ')
							  {
								mtApply();
							  }
							  if($("#optType").val()=='RTN')
							  {
								mtReturn();
							  }	
						});
						
						//物料操作---拆裝button
						$("#btnRemtr").click(function() {
							  if($("#optType").val()=='INS')
							  {
								mtSetup();
							  }
							  if($("#optType").val()=='REM')
							  {
								
								mtUnload();
							  }	
						});
						
						//匯出Excel
						$("#btnMrqtrExXls").on('click',function(e){				
							window.location.href = CONTEXT_URL + "/st/invMeterial/getMeterialApplyExcel?orderId="+$("#orderId").val()+"&whId="+$("#whIdMer").val();
						});
						
						// 匯入EXCEL
						$("#btnMrqtrIpmXls").on('click',function(e) {
							openUpladDialogFrame("upladExcelPage", "receviceExcelData"); // call common.js
						});
						
						//專線申請-tab
						$("#tabs7").on("click",function(){
							$("#applyOrderId").text($("#orderId").val());
							$("#applyOrderIdHidden").val($("#orderId").val());
							
							//初始select change event
							initSelectEvent('');
							 $('#SiteLineApplyTable').dataTable({
											"bDestroy" : true,
											"iDisplayLength" : -1,
											"aaSorting" : [],
											"bSort" : true,
											"sDom" : "rS",
											"sScrollY" : '70',
							 		        "sScrollX" : "100%",
							 		        "bProcessing" : false,
							 		        "sAjaxDataProp" : "rows",
							 		        "sAjaxSource" : CONTEXT_URL + "/st/line/lineApply/searchLineApply?orderId="+$("#orderId").val(),
							 		        "aoColumnDefs" : [ 
							 		            {"bSortable": false, "aTargets": [0], "mData": "app_ID","sWidth":"150px"},
							 		            {"bSortable": false,  "aTargets": [1], "mData": "app_TYPE_NAME","sWidth":"150px"},
							 		            {"bSortable": false,  "aTargets": [2], "mData": "line_ID","sWidth":"150px"},
							 		            {"bSortable": false,  "aTargets": [3], "mData": "a_LOC","sWidth":"150px"},
							 		            {"bSortable": false,  "aTargets": [4], "mData": "b_LOC","sWidth":"150px"},
							 		            {"bSortable": false,  "aTargets": [5], "mData":"app_TIME","sWidth":"150px"},
							 		            {"bSortable": false,  "aTargets": [6], "mData": "app_STATUS_NAME","sWidth":"80px"},
							 		            {"bSortable": false,  "aTargets": [7], "mData": "line_STATUS_NAME","sWidth":"80px"}
							 		           ],
											"oLanguage" : {
												"sProcessing" : "處理中...",
												"sZeroRecords" : "沒有匹配結果",
												"sLoadingRecords" : "讀取中..."
											},
											"fnInitComplete" : function() {
												this.fnAdjustColumnSizing();
												$(this).find("tbody tr:first").trigger('click');
											}
										}); //end init lineApply datatable
							
							$("#SiteLineApplyTable > tbody").off('click', 'tr').on( 'click','tr', function () {
								 $("#aCostShare").prop("checked",false);
								var index = $(this).closest("tr").index();
							    var data = $('#SiteLineApplyTable').dataTable().fnGetData(index);
								if(data == null){ //無任何申請資料的時候
									$("#tabs-7 button").prop("disabled",true);
									$('#lineApplyInsertBtn').prop("disabled",false);
									return false;
								}
								
								//專線申請-存檔
								if($("#odrStatus").val()=='OR05'){ //已接工
									$("#lineApplySaveBtn").prop("disabled" ,false);//打開儲檔功能
								} else {
									$("#lineApplySaveBtn").prop("disabled" ,true);
								}
								//專線申請-存檔
								if($.inArray(data.app_STATUS , ['LA01','LA03','LA05']) > -1){ //草稿、已駁回、申請處理 
									$("#lineApplySaveBtn").prop("disabled", false);
								} else {
									$("#lineApplySaveBtn").prop("disabled", true);
								}
								
								
								if($.inArray(data.app_STATUS , ['LA01','LA03','LA04','LA05']) > -1){ //草稿、已駁回、核網設定、申請處理 
									$("#lineApplyCancelBtn").prop("disabled", false);
								} else {
									$("#lineApplyCancelBtn").prop("disabled", true);
								}
								
								//專線申請-申請
								if($.inArray(data.app_STATUS , ['LA01','LA03']) > -1){ //草稿、已駁回
									$("#lineApplyBtn").prop("disabled", false);
								} else {
									$("#lineApplyBtn").prop("disabled", true);
								}
								
								//專線申請-取消申請
								if($.inArray(data.app_STATUS , ['LA01','LA03','LA04','LA05']) > -1){ ///草稿、已駁回、申請處理、竣工 
									$("#lineApplyCancelBtn").prop("disabled", false);
								} else {
									$("#lineApplyCancelBtn").prop("disabled", true);
								}
								
								//專線申請-竣工
								if($.inArray(data.app_STATUS , ['LA05']) > -1){ //申請處理 
									$("#lineApplyCompleteBtn").prop("disabled", false);
								} else {
									$("#lineApplyCompleteBtn").prop("disabled", true);
								}
								
								initApplyData(data);
								
								applyDetail('a',data.a_LOC,data.a_EQP);
								applyDetail('b',data.b_LOC,data.b_EQP);
								//專線申請簽核
								applySign(data.app_ID);
							   
						    });	// end $("#SiteLineApplyTable > tbody")		
										
						  //初始專線編輯頁的下拉選單
							initApplySelect();		
							if ('${view}' == 'view') {
								allView();
							}
						}); // end $("#tabs7") onclick 專線申請
						
						
						//專線申請-匯出excel
						$("#applyExportExlBtn").on("click",function(){
							var $ifrm = $("<iframe style='display:none' />");
			                $ifrm.attr("src", CONTEXT_URL + "/st/line/lineApply/exportLineApplyExcel?appId="+$("#appId").text());
			                $ifrm.appendTo("body");
			                $ifrm.load(function () {
			                   $("body").append(
			                        "<div>Failed to download <i>'" + CONTEXT_URL + "/st/line/lineApply/exportLineApplyExcel?appId="+$("#appId").text() + "'</i>!");
			                });
						});
						
						//專線申請-新增
						$('#lineApplyInsertBtn').on("click",function(){
							$("#tabs-7 input").val('');
							$("#tabs-7 textarea").val('');
							$("#tabs-7 p").text('');
							$("#bCostShare").prop("checked",false);
							$("#applyType option:first").prop("selected",true).change();
							$("#shareCom option:first").prop("selected",true).change();
							$("#linePurpose option:first").prop("selected",true).change();
							$("#lineType option:first").prop("selected",true).change();
							
							$("#LineNeworkTable > tbody >tr").remove();
							$("#costShareTable > tbody > tr").remove();
							$("#lineApplySign > table > tbody > tr").remove();
							$(".new-disabled").prop("disabled",true);
							
							$("#applyOrderId").text($("#orderId").val());
					        $("#applyOrderIdHidden").val($("#orderId").val());
					        $("#aEqp option").remove();
					        $("#bEqp option").remove();
					        
							$.ajax({
								url : CONTEXT_URL + "/st/line/lineApply/insert",
								type : 'POST',
								dataType : "json",
								data:{
									"orderId" :  $("#orderId").val()
								},
								async : false,
								success : function(data) {
									//工務類別
									$('#applyWorkTypeName').text($('#siteBuildWorkType option:selected').text());
									$('#applyWorkType').val($('#siteBuildWorkType option:selected').val());
									
									$('#appStatusName').text(data.row['appStatusName']); //申請狀態
									$('#appStatus').val(data.row['appStatus']); //申請狀態
									 
									$('#lineStatus').text(data.row['lineStatusName']); //專線狀態
									$('#lineStatusValue').val(data.row['lineStatus']); //專線狀態
									
									$('#appId').text(data.row['lineApplyId']); //操作單號
									$('#appIdValue').val(data.row['lineApplyId']);
									
									$('#applyDeptName').text(data.row['deptName']); //申請單位名稱
									$('#applyDeptId').val(data.row['deptId']); //申請單位ID
									
									$('#applyChName').text(data.row['chName']); //申請人
									$('#applyUserName').val(data.row['userName']); //申請人ID
									
									$('#applyTime').text(data.row['applyTime']); //申請時間
									
									//設備類型
									$('#applyEqpTypeName').text( $('#siteBuildEqpType option:selected').text());
									$('#applyEqpType').val( $('#siteBuildEqpType option:selected').val());
									
								}
							});		
							$("#lineApplySaveBtn").prop("disabled",false);
							$("#aSiteBtn").prop("disabled",false);
							$("#bSiteBtn").prop("disabled",false);
							$("#oLineBtn").prop("disabled",false);
						});//專線申請-新增
						
						//專線申請--存檔
						$("#lineApplySaveBtn").on("click",function(){
							updateOrInsert("save");
							
						});//專線申請--存檔
						
						//專線申請-申請
						$("#lineApplyBtn").click(function(){
							updateOrInsert("apply");
						});// end  $("#lineApplyBtn")
						
						//專線申請-竣工
						$("#lineApplyCompleteBtn").click(function(){
							updateLineApply("complete");
						});// end $("#lineApplyCompleteBtn")
						
						//專線申請-取消申請
						$("#lineApplyCancelBtn").click(function(){
							updateLineApply("cancelApply");
						});// end  $("#lineApplyCancelBtn")
						
						//當工程類別選項換時， 連動委外廠商
						$("#osType").change(function() {
							var osType = $("#osType").val()
							if(osType == ""){
								$("#osVen  option").remove();
								$("#osVen").append("<option value=''>---請選擇---</option>");
								return false;
							}
							$.ajax({
								url : CONTEXT_URL+ "/st/st004/search/searchOsVen",
								type : "POST",
								dataType : 'json',
								data : {
										"osType" : $("#osType").val(),
										},
								async : false,
								success : function(data) {
//					 			alert(JSON.stringify(data));
								if(data.success){
								   $("#osVen  option").remove();
								   $("#osVen").append("<option value=''>---請選擇---</option>");
								if (data.rows.length > 0) {
									for (var i = 0; i < data.rows.length; i++) {
										$("#osVen").append("<option value="+data.rows[i].co_VAT_NO+">"+ data.rows[i].co_NAME+ "</option>");
									}
								  }
							    }
							   }
							});
						 });
						
						
						$("#singleOrderApplyEvent").val("${singleOrderApplyEvent}");
						initData();

						$("#saveBtnTabs1").click(function() {
							if ($("#endDesc").val().length > 200) {
								alert("處理說明超過限制200字！");
								return false;
							} else {
								$.ajax({
									url : CONTEXT_URL + "/st/st005/save",
									type : 'POST',
									dataType : "json",
									data:{
										"orderId" :  $("#orderId").val(),
										"endDesc" :  $("#endDesc").val(),
										"orderDesc" :  $("#orderDesc").val()
									},
									async : false,
									success : function(data) {
										alert(data.msg);
									}
								});
							}
						});
						
						//站點資訊
						$("#locationInfo").click(function () {
							
							$.ajax({
								url : CONTEXT_URL + "/st/locTemp/init",
								type : 'POST',
								dataType : "json",
								data:{
									"orderId" :  $("#orderId").val(),
									"workId" :  $("#workId").val()
								},
								async : false,
								success : function(data) {
									
									//alert(JSON.stringify(data));
									$("#ST001BtnTr").hide();
									//$("#qweqwe").val(JSON.stringify(data));
									
									updateInit(data);
								}
							});							
						});
						
						$("#city", "#locationInfoFrom").change(function(){
							cleanTagValue("maint_AREA", "#locationInfoFrom");
							cleanTagValue("maint_DEPT", "#locationInfoFrom");
							cleanTagValue("maint_TEAM", "#locationInfoFrom");
							cleanTagValue("maint_USER", "#locationInfoFrom");
						});//city end
				
						
						$("#siteLocArea", "#locationInfoFrom").change(function(){
							cleanTagValue("maint_AREA");
							cleanTagValue("maint_DEPT");
							cleanTagValue("maint_TEAM");
							cleanTagValue("maint_USER");
							if($("#siteLocArea").val() != ""){
								getMaintainArea($("#city", "#locationInfoFrom").val(),$("#siteLocArea", "#locationInfoFrom").val(),"maint_AREA");
							}
						});//siteLocArea end
						
						$("#maint_AREA", "#locationInfoFrom").change(function(){
							cleanTagValue("maint_DEPT");
							cleanTagValue("maint_TEAM");
							cleanTagValue("maint_USER");
							if($("#maint_AREA", "#locationInfoFrom").val() != ""){
								getMaintainDept($("#maint_AREA", "#locationInfoFrom").val(),"maint_DEPT");
							}
						});//maint_AREA end
						
						$("#maint_DEPT", "#locationInfoFrom").change(function(){
							cleanTagValue("maint_TEAM");
							cleanTagValue("maint_USER");
							if($("#maint_DEPT", "#locationInfoFrom").val() != ""){
								getMaintainTeam($("#maint_DEPT", "#locationInfoFrom").val(),"maint_TEAM");
							}
						});//maint_DEPT end
						
						$("#maint_TEAM", "#locationInfoFrom").change(function(){
							cleanTagValue("maint_USER");
							if($("#maint_TEAM", "#locationInfoFrom").val() != ""){
								getMaintainUser($("#maint_TEAM", "#locationInfoFrom").val(),"maint_USER");
							}
						});//maint_TEAM end
						
						
						
						
						//站點資訊-存檔
                       $("#saveTab4").click(function () {
                    	   if ($("#loc_TYPE").val() == ""){
                    		   alert("請輸入站點類別");
                    		   return;
                    	   }
                    	   if ($("#loc_NAME").val() == ""){
                    		   alert("請輸入站點名稱");
                    		   return;
                    	   }//locationInfoFrom city
                    	   if ($("#city" , "#locationInfoFrom").val() == "" || $("#siteLocArea", "#locationInfoFrom").val() == "" ){
                    		   alert("請輸入區域");
                    		   return; 
                    	   }
                    	   
                    	   if(!validateNumber($("#lon").val(),3,6)){
   							  alert("經度格式錯誤，整數最多三位、小數最多為六位");
   							  return;
	   					   }
	   					   if($("#lon").val() < 115 || $("#lon").val() > 124){
	   						  alert("經度需介於115~124之間");
	   						  return;
	   					   }
	   					   
	   					   if(!validateNumber($("#lat").val(),3,6)){
	   						  alert("緯度格式錯誤，整數最多三位、小數最多為六位");
	   						  return;
	   					   }
	   					   if($("#lat").val() < 18 || $("#lat").val() > 28){
	   						  alert("緯度需介於：18~28之間");
	   						  return;
	   					   }
	   					   
		   				   if ($("#maint_AREA" , "#locationInfoFrom").val() == ""){
	                 		   alert("請輸入歸屬區域");
	                 		   return; 
	                 	   }
		   				   
		   				   if ($("#maint_DEPT" , "#locationInfoFrom").val() == ""){
	                 		   alert("請輸入歸屬單位");
	                 		   return; 
	                 	   }
		   				   
		   				   if ($("#maint_TEAM" , "#locationInfoFrom").val() == ""){
	                 		   alert("請輸入歸屬單位");
	                 		   return; 
	                 	   }
		   				   
		   				   if ($("#maint_USER" , "#locationInfoFrom").val() == ""){
	                 		   alert("請輸入負責人");
	                 		   return; 
	                 	   }
		   				   
	   					   if(!validateNumber( $("#building_HEIGHT").val(),3,1)){
							  alert("建築高度格式錯誤，整數最多三位、小數最多一位");
							  return;
						   }
	   					   if($("#footage").val() != "" && $("#footage").val() != null){
							  if(!validateNumber( $("#footage").val(),3,0)){
								 alert("坪數格式錯誤，整數最多三位");
								 return;
							  }
						   }
	   					   if($("#loc_DESC").val().length >100){
							  alert("補充說明超過100個字");
							  return;
						   }
                    	   
							
							$.ajax({
								url : CONTEXT_URL + "/st/locTemp/save",
								type : 'POST',
								dataType : "json",
								data:{
									"workId"     :  $("#workId").val(), //站點編號	
									"locationId"     :  $("#location_ID").val(), //站點編號	
									"locType"        :  $("#loc_TYPE").val(), //站點類別
									"shareType"      :  $("#share_TYPE").val(), //共構/共站
									"locName"        :  $("#loc_NAME").val(), //站點名稱
									"baseType"       :  $("#base_TYPE").val(), //機房型態
									"indoorOption"   :  $("#indoor_OPTION").val(), //室內場所分類
									"city"           :  $("#city", "#locationInfoFrom").val(), //區域
									"siteLocArea"    :  $("#siteLocArea", "#locationInfoFrom").val(), //區域
									"lon"            :  $("#lon").val(), //經度
									"lat"            :  $("#lat").val(), //緯度
									"addr"           :  $("#addr", "#locationInfoFrom").val(), //地址
									"maintArea"      :  $("#maint_AREA").val(), //歸屬區域
									"maintDept"      :  $("#maint_DEPT").val(), //規屬單位
									"maintTeam"      :  $("#maint_TEAM").val(), //負責單位
									"maintUser"      :  $("#maint_USER").val(), //負責人
									"nsLeavel"       :  $("#ns_LEVEL").val(), //NS_LEVEL 
									"buildingHeight" :  $("#building_HEIGHT").val(), //建築高度
									"shareCom"       :  $("#sharComTab4").val(), //共站業者
									"spaceRoom"      :  $("#space_ROOM").val(), //空間
									"spaceRoof"      :  $("#space_ROOF").val(), //空間
									"spaceTop"       :  $("#space_TOP").val(), //空間
									"spacePlatform"  :  $("#space_PLATFORM").val(), //空間
									"spaceLand"      :  $("#space_LAND").val(), //空間
									"spaceIndoor"    :  $("#space_INDOOR").val(), //空間
									"spaceOutdoor"   :  $("#space_OUTDOOR").val(), //空間
									"footage"        :  $("#footage").val(), //坪數
									"empRelation"    :  $("#emp_RELATION").val(), //與使用者關係
									"isFreeenter"    :  $("#is_FREEENTER").val(), //其他資訊
									"isKey"          :  $("#is_KEY").val(), //其他資訊
									"isIndoor"       :  $("#is_INDOOR").val(), //其他資訊
									"needMiscLic"    :  $("#need_MISC_LIC").val(), //其他資訊
									"hasMiscLic"     :  $("#has_MISC_LIC").val(), //其他資訊
									"locDesc"        :  $("#loc_DESC").val() //補充說明
									
								
								},
								async : false,
								success : function(data) {
									alert(data.msg);
									
									
									
								}
							});							
						});
                       //站點資訊-上傳
                       $("#fileProcessBtnTab4").click(function(){
           				openFileDialogFrame("showFileUploadPageTab4", $("#siteBuildWorkType").val() ,$("#workId").val(), "NOTYPE");
           			   });
                       $("#updateAddrButton", "#locationInfoFrom").click(function(){
       					if($("#city", "#locationInfoFrom").val()=="" || $("#siteLocArea", "#locationInfoFrom").val()==""){
       						alert("請先選擇區域");
       						return false;
       					}
       					var siteLocAddr = {
       						"zip" : $("#zip", "#locationInfoFrom").val(),
       						"city" : $("#city", "#locationInfoFrom").val(),
       						"area" : $("#siteLocArea", "#locationInfoFrom").val(),
       						"village" : $("#village", "#locationInfoFrom").val(),
       						"road" : $("#road", "#locationInfoFrom").val(),
       						"adjacent" : $("#adjacent", "#locationInfoFrom").val(),
       						"lane" : $("#lane", "#locationInfoFrom").val(),
       						"alley" : $("#alley", "#locationInfoFrom").val(),
       						"subAlley" : $("#subAlley", "#locationInfoFrom").val(),
       						"doorNo" : $("#doorNo", "#locationInfoFrom").val(),
       						"doorNoEx" : $("#doorNoEx", "#locationInfoFrom").val(),
       						"floor" : $("#floor", "#locationInfoFrom").val(),
       						"floorEx" : $("#floorEx", "#locationInfoFrom").val(),
       						"room" : $("#room", "#locationInfoFrom").val(),
       						"remark" : $("#addOther", "#locationInfoFrom").val(),
       						"disabledFields" : $("#disabledFields", "#locationInfoFrom").val(),
       						"callBackFun" : $("#siteLocationCallBackFun", "#locationInfoFrom").val()
       					};
       					openAddressDialogFrame("showSiteLocAddrPageTab4", siteLocAddr);
       				});//updateAddrButton end
						 
						
						$('#date').datepicker({
							dateFormat : "yy/mm/dd",
							changeYear : true,
							changeMonth : true,
							showButtonPanel : true
						});
						$('#needtDate').datepicker({
							dateFormat : "yy/mm/dd",
							changeYear : true,
							changeMonth : true,
							showButtonPanel : true
						});
						$('#constrKoDate').datepicker({
							dateFormat : "yy/mm/dd",
							changeYear : true,
							changeMonth : true,
							showButtonPanel : true
						});
						$('#constrDoneDate').datepicker({
							dateFormat : "yy/mm/dd",
							changeYear : true,
							changeMonth : true,
							showButtonPanel : true
						});
						$('#smrReadyDate').datepicker({
							dateFormat : "yy/mm/dd",
							changeYear : true,
							changeMonth : true,
							showButtonPanel : true
						});
						$('#upsReadyDate').datepicker({
							dateFormat : "yy/mm/dd",
							changeYear : true,
							changeMonth : true,
							showButtonPanel : true
						});
						$('#acReadyDate').datepicker({
							dateFormat : "yy/mm/dd",
							changeYear : true,
							changeMonth : true,
							showButtonPanel : true
						});
						$('#dynamoReadyDate').datepicker({
							dateFormat : "yy/mm/dd",
							changeYear : true,
							changeMonth : true,
							showButtonPanel : true
						});
						$('#admReadyDate').datepicker({
							dateFormat : "yy/mm/dd",
							changeYear : true,
							changeMonth : true,
							showButtonPanel : true
						});
						$('#powerReadyDate').datepicker({
							dateFormat : "yy/mm/dd",
							changeYear : true,
							changeMonth : true,
							showButtonPanel : true
						});
						$('#transReadyDate').datepicker({
							dateFormat : "yy/mm/dd",
							changeYear : true,
							changeMonth : true,
							showButtonPanel : true
						});
						$('#xxx10').datepicker({
							dateFormat : "yy/mm/dd",
							changeYear : true,
							changeMonth : true,
							showButtonPanel : true
						});
						$('#xxx11').datepicker({
							dateFormat : "yy/mm/dd",
							changeYear : true,
							changeMonth : true,
							showButtonPanel : true
						});
						$('#xxx12').datepicker({
							dateFormat : "yy/mm/dd",
							changeYear : true,
							changeMonth : true,
							showButtonPanel : true
						});

						$("#retiredWorkBut").click(function() {
							window.close();
						});
						
						//已接工
						$("#pickWorkBut").click(function() {
							if(!confirm("確定要接工？")){
								return false;
							}
							$.ajax({
								url : CONTEXT_URL + "/st/st005/changePickWork",
								type : 'POST',
								dataType : "json",
								data:{
									"orderId" :  $("#orderId").val()									
								},
								async : false,
								success : function(data) {
									if (data.success) {
										$("#pickWorkBut").prop("disabled", true);
										//退工
										$("#retiredWorkBut").prop("disabled", false);
										//完工
										$("#completionWorkBut").prop("disabled", false);
										//重新工單
										$("#newStaetWorkBut").prop("disabled", false);
										//附件下載
										$("#downloadAttch").prop("disabled", false);
										//儲存
										$("#saveBtnTabs1").prop("disabled", false);
										
										$("#endDesc").prop("disabled", false);
										
// 										$("#orderDesc").prop("disabled", false);
										
										$("#showPickWorkName").text($("#showUserName").val());
										$("#showOdrStatus").text("已接工");
										$("#saveTab2").prop("disabled", false);
										$.fancybox.close();
										$('#btn_edit').click();
									}									
									alert(data.msg);
								}
							});
						});
// 						$('#newStaetWorkBut').click(
// 								function() {
// 									window.open('./ST005M2Sub.html', '重啟工單',
// 											config = 'height=768,width=1024')
// 											.focus();
// 								});

						$("#downloadAttch").click(function() {
							openFileDownFrame("showFileDownloadFrame", $("#siteBuildWorkType").val(), $("#workId").val(), "NOTYPE");
							
						});
						$("#fileProcessBtn1").click(
								function() {
									window.open('../common/Upload.html',
											'附件上傳',
											config = 'height=768,width=1024')
											.focus();
								});
						$("#engineRoomFileProcessBtn").click(
								function() {
							openFileDialogFrame("showEngineRoomFileUploadFrame", $("#siteBuildWorkType").val(), $("#workId").val(),"NOTYPE");
						});
						$("#fileProcessBtn3").click(
								function() {
									window.open('../common/Upload.html',
											'附件上傳',
											config = 'height=768,width=1024')
											.focus();
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

						//基站資訊 新增
						$("#addGroup")
								.click(
										function() {
											//先取得目前的row數
											var num = document
													.getElementById("xxxTable").rows.length;
											//建立新的tr 因為是從0開始算 所以目前的row數剛好為目前要增加的第幾個tr
											var Tr = document.getElementById(
													"xxxTable").insertRow(num);

											//建立新的td 而Tr.cells.length就是這個tr目前的td數 而這個就是要填入td中的innerHTML  這裡也可以用不同的變數來辨別不同的td 

											//內容3
											var Tr = document.getElementById(
													"xxxTable").insertRow(num);

											Td = Tr.insertCell(Tr.cells.length);
											Td.colSpan = 2;
											Td.innerHTML = '<select name="eee1[]" class="form-control"><option value=""></option><option value="">1 1/4"</option><option value="">1 5/8"</option><option value="">1 /2"</option><option value="">7 /8"</option></select>'

											Td = Tr.insertCell(Tr.cells.length);
											Td.colSpan = 2;
											Td.innerHTML = '<input type="text" name="eee2[]">'

											Td = Tr.insertCell(Tr.cells.length);
											Td.colSpan = 2;
											Td.innerHTML = '<input type="text" name="eee3[]">'

											Td = Tr.insertCell(Tr.cells.length);
											Td.colSpan = 3;
											Td.innerHTML = '<input type="text" name="eee4[]">'

											//內容2
											var Tr = document.getElementById(
													"xxxTable").insertRow(num);

											Td = Tr.insertCell(Tr.cells.length);
											Td.innerHTML = '<input type="text" name="www1[]">'

											Td = Tr.insertCell(Tr.cells.length);
											Td.innerHTML = '<input type="text" name="www2[]">'

											Td = Tr.insertCell(Tr.cells.length);

											Td.innerHTML = '<input type="text" name="www3[]">'

											Td = Tr.insertCell(Tr.cells.length);
											Td.colSpan = 3;
											Td.innerHTML = '<input type="text" name="www4[]">'

											Td = Tr.insertCell(Tr.cells.length);
											Td.innerHTML = '<input type="text" name="www5[]">'

											Td = Tr.insertCell(Tr.cells.length);
											Td.innerHTML = '<input type="text" name="www6[]">'

											//內容1
											var Tr = document.getElementById(
													"xxxTable").insertRow(num);
											Td = Tr.insertCell(Tr.cells.length);
											Td.align = "middle";
											Td.rowSpan = 5;
											Td.innerHTML = '<input type="checkbox" name="qqq1[]" checked />&nbsp;<input size="2"type="text">';

											Td = Tr.insertCell(Tr.cells.length);
											Td.innerHTML = '<select name="qqq2[]" class="form-control"><option value=""></option><option value="">NodeB</option><option value="">RRU</option><option value="">Splitter</option></select>';

											Td = Tr.insertCell(Tr.cells.length);
											Td.innerHTML = '<input name="qqq3[]" type="text" size="12">';

											Td = Tr.insertCell(Tr.cells.length);
											Td.innerHTML = ' <select name="qqq4[]"	class="form-control"><option value="">UMWD-04517-XD</option></select>';

											Td = Tr.insertCell(Tr.cells.length);
											Td.innerHTML = '<input name="qqq5[]" type="text" size="12">';

											Td = Tr.insertCell(Tr.cells.length);
											Td.innerHTML = '<input name="qqq6[]" type="text" size="12">';

											Td = Tr.insertCell(Tr.cells.length);
											Td.innerHTML = '<input name="qqq7[]" type="text" size="12">';

											Td = Tr.insertCell(Tr.cells.length);
											Td.innerHTML = '<input name="qqq8[]" type="text" size="12">';

											Td = Tr.insertCell(Tr.cells.length);
											Td.innerHTML = '<select name="qqq9[]" class="form-control"><option value=""></option><option value="">Tower</option><option value="">Pipe</option><option value="">Wall Mount</option></select>';

											//表頭3  Td.align="middle"; 
											var Tr = document.getElementById(
													"xxxTable").insertRow(
													num + 2);

											Td = Tr.insertCell(Tr.cells.length);
											Td.align = "middle";
											Td.colSpan = 2;
											Td.innerHTML = 'Feeder cable type';

											Td = Tr.insertCell(Tr.cells.length);
											Td.align = "middle";
											Td.colSpan = 2;
											Td.innerHTML = 'Feeder cable 長度';

											Td = Tr.insertCell(Tr.cells.length);
											Td.align = "middle";
											Td.colSpan = 2;
											Td.innerHTML = 'Jumper cable 長度';

											Td = Tr.insertCell(Tr.cells.length);
											Td.align = "middle";
											Td.colSpan = 3;
											Td.innerHTML = '覆蓋區域';

											//表頭2  Td.align="middle"; 
											//Td.width="200px";
											//Td.height="50px";
											var Tr = document.getElementById(
													"xxxTable").insertRow(
													num + 1);

											Td = Tr.insertCell(Tr.cells.length);
											Td.align = "middle";
											Td.innerHTML = '樓高';

											Td = Tr.insertCell(Tr.cells.length);
											Td.align = "middle";
											Td.innerHTML = '鐵塔';

											Td = Tr.insertCell(Tr.cells.length);
											Td.align = "middle";
											Td.innerHTML = '屋突';

											Td = Tr.insertCell(Tr.cells.length);
											Td.align = "middle";
											Td.colSpan = 3;
											Td.innerHTML = '地址';

											Td = Tr.insertCell(Tr.cells.length);
											Td.align = "middle";
											Td.innerHTML = '經度';

											Td = Tr.insertCell(Tr.cells.length);
											Td.align = "middle";
											Td.innerHTML = '緯度';

											//表頭1  Td.align="middle";
											var Tr = document.getElementById(
													"xxxTable").insertRow(num);
											Td = Tr.insertCell(Tr.cells.length);
											Td.align = "middle";
											Td.innerHTML = 'Cell';

											Td = Tr.insertCell(Tr.cells.length);
											Td.align = "middle";
											Td.innerHTML = '訊號源';

											Td = Tr.insertCell(Tr.cells.length);
											Td.align = "middle";
											Td.innerHTML = 'P-Code';

											Td = Tr.insertCell(Tr.cells.length);
											Td.align = "middle";
											Td.innerHTML = '天線型號';

											Td = Tr.insertCell(Tr.cells.length);
											Td.align = "middle";
											Td.innerHTML = '安裝高度';

											Td = Tr.insertCell(Tr.cells.length);
											Td.align = "middle";
											Td.innerHTML = '方向角';

											Td = Tr.insertCell(Tr.cells.length);
											Td.align = "middle";
											Td.innerHTML = 'E-TILT';

											Td = Tr.insertCell(Tr.cells.length);
											Td.align = "middle";
											Td.innerHTML = 'M-TILT';

											Td = Tr.insertCell(Tr.cells.length);
											Td.align = "middle";
											Td.innerHTML = '安裝方式';

											//這樣就好囉 記得td數目要一樣 不然會亂掉~
										});

						//基站資訊 刪除
						$("#deleteGroup")
								.click(
										function() {
											//先取得目前的row數
											var num = document
													.getElementById("xxxTable").rows.length;
											//防止把標題跟原本的第一個刪除XD
											if (num > 7) {
												//刪除最後二個
												document.getElementById(
														"xxxTable").deleteRow(
														-1);
												document.getElementById(
														"xxxTable").deleteRow(
														-1);
												document.getElementById(
														"xxxTable").deleteRow(
														-1);
												document.getElementById(
														"xxxTable").deleteRow(
														-1);
												document.getElementById(
														"xxxTable").deleteRow(
														-1);
												document.getElementById(
														"xxxTable").deleteRow(
														-1);
												document.getElementById(
														"xxxTable").deleteRow(
														-1);

											}
										});

						changeOptType("MRQTR");
						
						
						
						//機房資訊 init
						if($("#singleOrderApplyEvent").val() == "edit"){
							    <fmt:formatDate value="${siteWorkSiteTemp.CONSTR_KO_DATE}" pattern="yyyy/MM/dd" var="constrKoDate"/>
								$("#constrKoDate").val("${constrKoDate}");
								<fmt:formatDate value="${siteWorkSiteTemp.CONSTR_DONE_DATE}" pattern="yyyy/MM/dd" var="constrDoneDate"/>
								$("#constrDoneDate").val("${constrDoneDate}");
								<fmt:formatDate value="${siteWorkSiteTemp.SMR_READY_DATE}" pattern="yyyy/MM/dd" var="smrReadyDate"/>
								$("#smrReadyDate").val("${smrReadyDate}");
								<fmt:formatDate value="${siteWorkSiteTemp.UPS_READY_DATE}" pattern="yyyy/MM/dd" var="upsReadyDate"/>
								$("#upsReadyDate").val("${upsReadyDate}");
								<fmt:formatDate value="${siteWorkSiteTemp.AC_READY_DATE}" pattern="yyyy/MM/dd" var="acReadyDate"/>
								$("#acReadyDate").val("${acReadyDate}");
								<fmt:formatDate value="${siteWorkSiteTemp.DYNAMO_READY_DATE}" pattern="yyyy/MM/dd" var="dynamoReadyDate"/>
								$("#dynamoReadyDate").val("${dynamoReadyDate}");
								<fmt:formatDate value="${siteWorkSiteTemp.ADM_READY_DATE}" pattern="yyyy/MM/dd" var="admReadyDate"/>
								$("#admReadyDate").val("${admReadyDate}");
								<fmt:formatDate value="${siteWorkSiteTemp.POWER_READY_DATE}" pattern="yyyy/MM/dd" var="powerReadyDate"/>
								$("#powerReadyDate").val("${powerReadyDate}");
								<fmt:formatDate value="${siteWorkSiteTemp.TRANS_READY_DATE}" pattern="yyyy/MM/dd" var="transReadyDate"/>
								$("#transReadyDate").val("${transReadyDate}");
								$("#lineInfoSiteId").val("${siteWorkSiteTemp.SITE_ID}");
						}
						//機房資訊 儲存
						$("#saveEngineRoomBtn").click(function(){
							if(confirm("確定要存檔?")){
								$.ajax({
									url : CONTEXT_URL + "/st/siteLine/tabs/saveEngineRoom",
									type : 'POST',
									async : false,
									data : {
										CONSTR_KO_DATE : $("#constrKoDate").val(),
										CONSTR_DONE_DATE : $("#constrDoneDate").val(),
										SMR_READY_DATE : $("#smrReadyDate").val(),
										UPS_READY_DATE : $("#upsReadyDate").val(),
										AC_READY_DATE : $("#acReadyDate").val(),
										DYNAMO_READY_DATE : $("#dynamoReadyDate").val(),
										ADM_READY_DATE : $("#admReadyDate").val(),
										POWER_READY_DATE : $("#powerReadyDate").val(),
										TRANS_READY_DATE : $("#transReadyDate").val(),
										SITE_ID : $("#siteBuildSiteId").val(),
										WORK_ID :$("#workId").val()
									},
									success : function(data){
										var result = data['result'];
										if(result == true){
											alert('<s:message code="msg.update.success"/>');
										}else{
											alert(result);	
										}
									}
								});
							}
						});
						//基站資訊==================================================================================================================
						$("#siteData").click(function(){
							//alert($("#workId").val());
						//經度 
						$('#siteLon').val($("#siteBuildLon").val());
						//緯度 
						$('#siteLat').val($("#siteBuildLat").val());
						//基站名稱
						$('#siteTempName').val($("#siteBuildSiteName").val());
						//涵蓋類別
						$('#includeRange').val($("#coverageType").val()).change();
						
							$.ajax({
								url : CONTEXT_URL + "/st/siteLine/siteTemp",
								type : 'POST',
								data:{
									"workId" :  $("#workId").val()			
								},
								async : false,
								success : function(data) {
									//alert(JSON.stringify(data.rows[0].omcu_OBJ));
									//基站編號
							 		$("#btsSite").text("${siteWork.BTS_SITE_ID}");
									$("#btsSiteId").val("${siteWork.BTS_SITE_ID}");
									//基站頻段
							 		$("#feq").text($("#feqId").find("option:selected").text());
									$("#idFeq").val($("#feqId").val());
									//基站名稱
									$("#siteTempName").val(data.rows[0].site_NAME);
									//站台識別碼
									$("#searchSiteId").val(data.rows[0].site_ID);
									//基站狀態
									osSiteStatusId();
									//設備型態
									$("#siteHuntEqpTemp").val(data.rows[0].eqp_TYPE_ID).change();
									//設備機型
									$("#siteHuntEqpModelTemp").val(data.rows[0].eqp_MODEL_ID).change();
									//Feederless
									$("#feederlessTeam").val(data.rows[0].feederless).change();
									//Master Site
									$("#masterSiteTemp").val(data.rows[0].master_SITE);
									//Configuration
									$("#configuration").val(data.rows[0].bts_CONFIG);
									//速率(上)
									$("#speedUpload").val(data.rows[0].speed_UPLOAD);
									//速率(下) 
									$("#speedDownload").val(data.rows[0].speed_DOWNLOAD);
									//Donor BTS
									$("#donorBts").val(data.rows[0].donor_SITE);
									//涵蓋類別
									$("#includeRange").val(data.rows[0].coverage_TYPE).change();
									//室內天線涵蓋樓層
									$("#coverageInFloor").val(data.rows[0].coverage_IN_FLOOR);
									//功率
									$("#power").val(data.rows[0].power);
									//Cluster 
									$("#cluster").val(data.rows[0].cluster);
									//RNC-BTS 
									$("#rncBts").val(data.rows[0].rnc_BTS);
									//Cell Status 
									$("#cellStatus").val(data.rows[0].cell_STATUS);
									//OMCu Object
									$("#omcuObjectLine").val(data.rows[0].omcu_OBJ).change();
									//NOIS repeater狀態
									$("#repeater").val(data.rows[0].nios_RPT_STATUS).change();
									//經度 
									$("#siteLon").val(data.rows[0].loa);
									//緯度
									$("#siteLat").val(data.rows[0].lat);
									//有室內涵蓋
									if (data.rows[0].coverage_INDOOR == 'Y') {
										$("#coverageIndoor").prop("checked",true);
										$("#coverageIndoor").val("Y");
									}else{
										$("#coverageIndoor").prop("checked",false);
										$("#coverageIndoor").val("N");							
									}
									//有美化施工
									if (data.rows[0].hidden == 'Y') {
										$("#hidden").prop("checked",true);
										$("#hidden").val("Y");
									}else{
										$("#hidden").prop("checked",false);
										$("#hidden").val("N");							
									}
									//網管On Air
									if (data.rows[0].nois_ON_AIR == 'Y') {
										$("#noisOnAir").prop("checked",true);
										$("#noisOnAir").val("Y");
									}else{
										$("#noisOnAir").prop("checked",false);
										$("#noisOnAir").val("N");							
									}	
									//有接L2_SWITCH
									if (data.rows[0].l2_SWITCH == 'Y') {
										$("#l2Switch").prop("checked",true);
										$("#l2Switch").val("Y");
									}else{
										$("#l2Switch").prop("checked",false);
										$("#l2Switch").val("N");							
									}
									//onAirDate
									$("#onAirDate").val(data.rows[0].onair_DATE);
									//停用日期 
									$("#dumpDate").val(data.rows[0].dump_DATE);
								}
							});
							//天線組態
							$.ajax({
								url : CONTEXT_URL + "/st/siteLine/siteWorkAntCfgTemp",
								type : 'POST',
								data : {
									"workId" :  $("#workId").val(),
									"siteId" :  $("#searchSiteId").val()	
								},
								async : false,
								success : function(data) {
									//alert("JSON -> "+JSON.stringify(data));
									//天線組態
									//選點資料時，預防資料重覆，先刪除區塊
									$("#testTableDIV").remove();
									$("#tableAnt").append("<div id='testTableDIV'></div>");

									for(var i=0; i<data.rows.length; i++) {
										if (data.rows[i].work_ID != null) {
											var tableNum = addSrTable();
											//cell
				 							$("#cell-"+tableNum).val(data.rows[i].cell_ID).change();
				 							//console.log(unUse);
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
											//緯度
											$("#lat-"+tableNum).val(data.rows[i].lat);
											//Feeder cable type	
											$("#feederType-"+tableNum).val(data.rows[i].f_CABLE_TYPE).change();
											//Feeder cable 長度	
											$("#feederL-"+tableNum).val(data.rows[i].f_CABLE_LEN);
											//Jumper cable 長度	
											$("#jumper-"+tableNum).val(data.rows[i].j_CABLE_LEN);
											//覆蓋區域
											$("#coverageArea-"+tableNum).val(data.rows[i].cover_REG);
											$("#ant_zip-"+tableNum).val(data.rows[i].zip);
											$("#ant_city-"+tableNum).val($("#city").val());
											$("#ant_area-"+tableNum).val($("#area").val());
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
									}		
								}
							});
						});
						
						
						//--存檔
						$("#saveSiteTempBoutton").click(function() {
							//alert($("#searchSiteId").val());
							if ($('#siteTempName').val() == '') {
								alert('請輸入基站名稱!');
								return false;
							}
							if ($('#siteHuntEqpTemp').val() == '') {
								alert('請輸入設備型態!');
								return false;
							}
							if ($('#omcuObjectLine').val() == '') {
								alert('請輸入OMCu Object!');
								return false;
							}
							
							//天線組態的判斷
							//alert(tableNum);
							if($("#lon-"+tableNum).val() !=null && $("#lon-"+tableNum).val() !="") {		
								if(!validateNumber($("#lon-"+tableNum).val(),3,6)){
									  alert("天線組態 :經度格式錯誤，整數最多三位、小數最多為六位");
									  return false;
								}
							}
							
							if($("#lon-"+tableNum).val() < 115 || $("#lon-"+tableNum).val() > 124){
								  alert("天線組態 :經度需介於115~124之間");
								  return false;
							}
							
							if($("#lat-"+tableNum).val() != null && $("#lat-"+tableNum).val() != "") {				   
								if(!validateNumber($("#lat-"+tableNum).val(),3,6)){
									  alert("天線組態 :緯度格式錯誤，整數最多三位、小數最多為六位");
									  return false;
								}
							}
							
							if($("#lat-"+tableNum).val() < 18 || $("#lat-"+tableNum).val() > 28){
								alert("天線組態 :緯度需介於：18~28之間");
								return false;
							}
							
							//天線組態
							var exportArray = [];
							
							$('#testTableDIV table').each(function(){
								var exportObj = {} ;
								var ttable = $(this).closest('table');
								
								$(ttable).find(':input').each(function(){ 				
									exportObj[String($(this).attr("name"))] = String($(this).val());
								});
								//console.log(exportObj);
								exportArray.push(exportObj);
							});
							//alert($('#omcuObjectLine').val());
							$.ajax({
								mimeType : 'text/html; charset=utf-8', // ! Need set mimeType only when run from local file
								url : CONTEXT_URL + "/st/siteLine/saveWorkSiteTemp",
								type : 'POST',
								//contentType: "application/json",
								//data : $("#searchRecordTab1").serialize(), //+ "&srId=" + data['sr_ID'] + "&srSeq=" + data['sr_SEQ'],
			 					data : {
									'workId' : $("#workId").val(),
									"siteId" :  $("#searchSiteId").val(),
									//========================================TB_SITE_SR_SITE_TEMP
									'siteTempName' : $("#siteTempName").val(), //基站名稱
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
									'selectOmcuObject' : $('#omcuObjectLine').val(),//OmcuObject
									//========================================TB_SITE_SR_SITE_TEMP End
									
									//========================================TB_SITE_SR_ANT_CFG_TEMP
									'exportList' : JSON.stringify(exportArray)
									//========================================TB_SITE_SR_SITE_TEMP End
									
			 					},
								dataType : "json",
								async : false,
								success : function(data) {
									alert('更新儲存成功!')
								}
							});	
						});
						
						$("#uploadSiteTempBoutton").click(function() {
							openFileDialogFrame("showFileUploadFrame",  $("#siteBuildWorkType").val(), $("#workId").val(), "NOTYPE");
						});
						//基站資訊==END================================================================================================================
						//重啟工單
						$("#newStaetWorkBut").click(function(){
							var targetFrameId = "showReopenWorkOrderPage";
							$.ajax({
								mimeType : 'text/html; charset=utf-8',
								type : 'POST',
								url : CONTEXT_URL + "/st/st005/reopenOrderPage?targetFrameId="+targetFrameId,
								dataType : "html",
								async : false,
								success : function(data) {
									initialDialogFrame(targetFrameId,"重啟工單",data);
								}
							});
						});
						
						//退工
						$("#retiredWorkBut").click(function(){
							if($.trim($("#endDesc").val()) == ""){
								alert("退工時處理說明必須填寫！");
								return false;
							} else if ($.trim($("#endDesc").val()).length > 200) {
								alert("處理說明超過限制200字！");
								return false;
							}
							if(confirm("確認退工後作業工單將無法再進行處理，是否確定？")){
								$.ajax({
									url : CONTEXT_URL + '/st/st005/update/dropOrder',
									type : 'POST',
									async : false,
									dataType : "json",
									data : {
										workId : $("#workId").val()
									},
									success : function(data){
										var errorMessages = data.errors;
										if(data.success){
											alert('<s:message code="msg.dropOrder.success"/>');
											$('#btn_search').click();
											$.fancybox.close();
										}else if(errorMessages.length != 0){
											var msg = data.msg + "\n";
											for(var errorMessage in errorMessages){
												msg += errorMessages[errorMessage] + "\n"
											}
											alert(msg);
										}else{
											alert(data.msg);
										}
									}
								});
							}
						});
						
						//================================================================
						//非廠商
						if (loginUsrVendor != "true"){
							//alert($("#odrStatus").val());
						    if ($("#odrStatus").val() == 'OR04'){
						    	$("#pickWorkBut").prop("disabled", false);
						    }
						} else {	// 廠商
							$("button.vendorWork-disable").prop("disabled", true);
							$("#pickWorkBut").prop("disabled", true);
							$("#retiredWorkBut").prop("disabled", true);
							$("#completionWorkBut").prop("disabled", true);
							$("#newStaetWorkBut").prop("disabled", true);
						}
						tabOrderPageFun();
						//建站完工簽核 檢視
						if("${view}" == 'view' ){
							allView();
						}
				});//document ready end
					//站點資訊 
					function changeValue(checkboxName){
						if($("#"+checkboxName).prop("checked")){
							$("#"+checkboxName).val("Y");
						}else{
							$("#"+checkboxName).val("N");
						}
					}
					
					function cleanTagValue(tagName){
						$('#'+tagName).html("");
						$('<option value="">---請選擇---</option>').appendTo('#'+tagName);
						//$('#'+tagName).select2("val","");
						$('#'+tagName).val("").change();
					}
					
					function sharecomOnclick(obj){
						var sharComvar = "";
						if($("#"+obj.id).prop("checked") == true){
							sharComvar += $("#sharComTab4").val()+obj.id+","
						}else{
							if (sharComvar.indexOf(obj.id + ",") == -1) {
								sharComvar =$("#sharComTab4").val().replace(obj.id+ "," , "");
							}
						}
						$("#sharComTab4").val(sharComvar);
					}
					
					//物料領料申請
					function mtApply() {
							//需求日期
							var reqDate = $("#reqDate").val();

							var whIdMer= $("#whIdMer").val();
						
							var errorMessages = [];
							//檢核需求日期
							if(reqDate == "" ){
								errorMessages[errorMessages.length]="需求日期必須選擇";
							}
							//檢核作業說明字數
							if($("#showOptDesc").val().length >100){
								errorMessages [errorMessages.length]="領料說明超過100個字";
							}
							if(whIdMer=="")
							{
								errorMessages [errorMessages.length]="請選擇領料倉庫";
							}
						
							if(errorMessages.length!=0){
								var msg ="";
								for(var em in errorMessages){
									msg += errorMessages[em]+"\n";
								}
								alert(msg);
								return false;
							}
						
							var itemDatas = "";
					        var rows = $("#meterialItem").dataTable().fnGetData();
					        if(rows.length==0)
					        {
								alert("無領料明細，無法申請");
								return false;
					        }
				        
				        
					        for(var i=0; i<rows.length; i++) {
							
								var data =rows[i];
								var itemData =  "itemNo:"+data['itemNo']+",itemCatMain:"+data['itemCatMain']+",qty:"+data['qty']+",actQty:"+data['actQty']+",ctrlType:"+data['ctrlType'];
								if (itemDatas == "") {
									itemDatas = itemData;
								} else {
									itemDatas = itemDatas + "@" + itemData;
								}
					        };
							$.ajax({
								url : CONTEXT_URL + "/st/invMeterial/mtApply",
								type : 'POST',
								dataType : "html",
								data : {
									"optId" : $("#optId").val(),
									"optType": $("#optType").val(), //操作類別 
									"orderId": $("#orderIdMer").val(), // 工單號碼
									"workType": $("#workTypeMer").val(), //工務類別 
									"status": $("#statusMer").val(), //處理狀態
									"appDept": $("#appDept").val(), //申請單位 
									"appUser": $("#appUser").val(), //申請人
									"osId": $("#osId").val(), //派工單號
									"whId":$("#whIdMer").val(),//領料倉庫
									"reqDate":$("#reqDate").val(),//需求日期 
									"showOptDesc":$("#showOptDesc").val(),//領料說明
									"itemDatas":itemDatas//領料明細
								},
								async : false,
								success : function(data) {
				                	alert("存檔成功");
				                	meterialTableInit();
				                	$('#meterialTable tbody').find("tr:first").trigger('click');
				                	initPage(data);
								}
							});
						
					}
					
					//物料領料申請
					function mtReturn() {
							var whIdMer= $("#whIdMer").val();
						
							var errorMessages = [];

							//檢核作業說明字數
							if($("#showOptDesc").val().length >100){
								errorMessages [errorMessages.length]="領料說明超過100個字"+"\n";
							}
							if(whIdMer=="")
							{
								errorMessages [errorMessages.length]="請選擇領料倉庫"+"\n";
							}
						
							//取得jqgrid
							var ids      = $('#rtntrItem').getDataIDs();
							var tmpData  = ""; //迴圈處理列暫存
						
							var rowdata = new Array();
							for ( var i = 0; i < ids.length; i++)
							{
								tmpData = jQuery('#rtntrItem').jqGrid('getRowData', ids[i]);
								//檢核數字
								var assetQty=tmpData.assetQty-0;
								var qty=$("#qty" + (i+1)).val()-0;
								if(qty!='')
								{
									if(qty>assetQty){
										errorMessages [errorMessages.length]="料號:"+tmpData.matNo+"退料數量不得大於總數量"+"\n";
									}
								}
								
								//檢核必填欄位
								var checkItem=tmpData.checkItem;
								if(checkItem=="1")
								{
									var matStatus=$("#matStatus" + (i+1)).val();
									if(matStatus=="-1")
									{
										errorMessages [errorMessages.length]="料號:"+tmpData.matNo+"退料種類必須選填"+"\n";
									}
									var trnReason=$("#trnReason" + (i+1)).val();
									if(trnReason=="")
									{
										errorMessages [errorMessages.length]="料號:"+tmpData.matNo+"退料原因必須選填"+"\n";
									}
								}
								
								tmpData.qty=qty
								tmpData.matStatus=$("#matStatus" + (i+1)).val()
								tmpData.trnReason=$("#trnReason" + (i+1)).val();
								rowdata.push(tmpData);
							}
							
							if(errorMessages.length!=0){
								var msg ="";
								for(var em in errorMessages){
									msg += errorMessages[em]+"\n";
								}
								alert(msg);
								return false;
							}		
							
							var itemDatas=array2json(rowdata);
							$.ajax({
								url : CONTEXT_URL + "/st/invMeterial/mtReturn",
								type : 'POST',
								dataType : "html",
								data : {
									"optType": $("#optType").val(), //操作類別 
									"orderId": $("#orderIdMer").val(), // 工單號碼
									"workType": $("#workTypeMer").val(), //工務類別 
									"status": $("#statusMer").val(), //處理狀態
									"appDept": $("#appDept").val(), //申請單位 
									"appUser": $("#appUser").val(), //申請人
									"osId": $("#osId").val(), //派工單號
									"whId":$("#whIdMer").val(),//領料倉庫
									"showOptDesc":$("#showOptDesc").val(),//領料說明
									"itemDatas":itemDatas//退料明細
								},
								async : false,
								success : function(data) {
				                	alert("存檔成功");
				                	meterialTableInit();
				                	initPage(data);
								}
							});
						
					}
					
					//物料安裝
					function mtSetup() {
						
							//拆裝日期
							var remDate = $("#remDate").val();
							
							var errorMessages = [];
							//檢核拆裝日期
							if(remDate == "" ){
								errorMessages[errorMessages.length]="拆裝日期必須選擇";
							}
							
							//檢核作業說明字數
							if($("#showOptDesc").val().length >100){
								errorMessages [errorMessages.length]="領料說明超過100個字"+"\n";
							}
						
							//取得jqgrid
							var ids      = $('#remInsItem').getDataIDs();
							var tmpData  = ""; //迴圈處理列暫存
						
							var rowdata = new Array();
							for ( var i = 0; i < ids.length; i++)
							{
								tmpData = jQuery('#remInsItem').jqGrid('getRowData', ids[i]);
								//檢核數字
								var assetQty=tmpData.assetQty-0;
								var qty=$("#qty" + (i+1)).val()-0;
								if(qty!='')
								{
									if(qty>assetQty){
										errorMessages [errorMessages.length]="料號:"+tmpData.matNo+"安裝數量不得大於總數量"+"\n";
									}
								}						
								tmpData.qty=qty
								rowdata.push(tmpData);
							}
							
							if(errorMessages.length!=0){
								var msg ="";
								for(var em in errorMessages){
									msg += errorMessages[em]+"\n";
								}
								alert(msg);
								return false;
							}		
							
							var itemDatas=array2json(rowdata);
							$.ajax({
								url : CONTEXT_URL + "/st/invMeterial/mtSetup",
								type : 'POST',
								dataType : "html",
								data : {
									"optType": $("#optType").val(), //操作類別 
									"orderId": $("#orderIdMer").val(), // 工單號碼
									"workType": $("#workTypeMer").val(), //工務類別 
									"status": $("#statusMer").val(), //處理狀態
									"appDept": $("#appDept").val(), //申請單位 
									"appUser": $("#appUser").val(), //申請人
									"osId": $("#osId").val(), //派工單號
									"reqDate":$("#remDate").val(),//拆裝日期 
									"showOptDesc":$("#showOptDesc").val(),//領料說明
									"itemDatas":itemDatas//退料明細
								},
								async : false,
								success : function(data) {
				                	alert("存檔成功");
				                	meterialTableInit();
				                	initPage(data);
								}
							});
						
					}
					//物料拆除
					function mtUnload() {
						
							//拆裝日期
							var remDate = $("#remDate").val();
							
							var errorMessages = [];
							//檢核拆裝日期
							if(remDate == "" ){
								errorMessages[errorMessages.length]="拆裝日期必須選擇";
							}
							
							//檢核作業說明字數
							if($("#showOptDesc").val().length >100){
								errorMessages [errorMessages.length]="領料說明超過100個字"+"\n";
							}
						
							//取得jqgrid
							var ids      = $('#remItem').getDataIDs();
							var tmpData  = ""; //迴圈處理列暫存
						
							var rowdata = new Array();
							for ( var i = 0; i < ids.length; i++)
							{
								tmpData = jQuery('#remItem').jqGrid('getRowData', ids[i]);
								//檢核數字
								var assetQty=tmpData.assetQty-0;
								var qty=$("#qty" + (i+1)).val()-0;
								
								if(qty!='')
								{
									if(qty>assetQty){
										errorMessages [errorMessages.length]="料號:"+tmpData.matNo+"拆除數量不得大於總數量"+"\n";
									}
								}						
								tmpData.qty=qty
								rowdata.push(tmpData);
							}
							
							if(errorMessages.length!=0){
								var msg ="";
								for(var em in errorMessages){
									msg += errorMessages[em]+"\n";
								}
								alert(msg);
								return false;
							}		
							
							var itemDatas=array2json(rowdata);
							$.ajax({
								url : CONTEXT_URL + "/st/invMeterial/mtUnload",
								type : 'POST',
								dataType : "html",
								data : {
									"optType": $("#optType").val(), //操作類別 
									"orderId": $("#orderIdMer").val(), // 工單號碼
									"workType": $("#workTypeMer").val(), //工務類別 
									"status": $("#statusMer").val(), //處理狀態
									"appDept": $("#appDept").val(), //申請單位 
									"appUser": $("#appUser").val(), //申請人
									"osId": $("#osId").val(), //派工單號
									"reqDate":$("#remDate").val(),//拆裝日期 
									"showOptDesc":$("#showOptDesc").val(),//領料說明
									"itemDatas":itemDatas//退料明細
								},
								async : false,
								success : function(data) {
				                	alert("存檔成功");
				                	meterialTableInit();
				                	initPage(data);
								}
							});
						
					}
					
					/**
					 * 將GRID轉JSON字串
					 * 
					 */
					function array2json(arr) {

						var parts = [];
						var is_list = (Object.prototype.toString.apply(arr) === '[object Array]');

						for ( var key in arr) {
							var value = arr[key];

							if (typeof value == "object") {

								// Custom handling for arrays
								if (is_list) {

									parts.push(array2json(value));
								} else {
									parts[key] = array2json(value);
								}
							}

							else {
								var str = "";

								// alert("is_list="+is_list);

								try {
									if (!is_list) {
										// 塞KEY
										str = '"' + key + '":';

										// 塞VALUE
										// alert(""+key + " = '" + value + "'");
										if ($(value).is('input')) {
											if ($(value).attr("type") == "text") {

												str += '"' + $(value).attr('value') + '"';
											}
										} else {
											if ($(value).is('select')) {

												str += '"' + $(value).val() + '"';
											}

											else {

												str += '"' + value + '"';
											}

										}

									}
								} catch (Excetpion) {
									str += '""';
									// alert("str >>"+str+"<<");
									// alert(Excetpion.message + ">>"+str+"<<");
								}

								// alert("str >>"+str+"<<");
								parts.push(str);
							}
						}
						var json = parts.join(",");

						if (is_list) {
							return '[' + json + ']';// Return numerical JSON
						}
						return '{' + json + '}';// Return associative JSON
					}


					/**
					* 物料Excel上傳
					* @param callBackFunName
					*/
					function openUpladDialogFrame(targetFrameId,callBackFunName) {
						$.ajax({
							mimeType : 'text/html; charset=utf-8',
							type : 'POST',
							url : CONTEXT_URL + "/st/invMeterial/uploadMeterialApplyExcelInit",
							data : {
								"callBackFun" : callBackFunName,
								"targetFrameId" : targetFrameId
							},
							dataType : "html",
							async : false,
							success : function(data) {
								initialDialogFrame(targetFrameId, "匯入領料單", data);
							}
						});
					}
					
// 					//匯入Excel回傳事件	
					function receviceExcelData(data) {
							var itemDatas = "";
							var obj = $.parseJSON(data);
							for(var i=0; i<obj.rows.length; i++) {
								var itemData = "itemNo:"+obj.rows[i].itemNo+",itemCatMain:"+obj.rows[i].itemCatMain+",qty:"+obj.rows[i].qty+",actQty:"+obj.rows[i].actQty+",ctrlType:"+obj.rows[i].ctrlType;
								if (itemDatas == "") {
									itemDatas = itemData;
								} else {
									itemDatas = itemDatas + "@" + itemData;
								}
							};
	 						var table = $('#meterialItem').DataTable();
	 						table.fnClearTable();
							table = $('#meterialItem').dataTable(
									  {
										"bDestroy" : true,
										"iDisplayLength" : -1,
										"aaSorting" : [ [ 0, "desc" ] ],
										"sDom" : "rS",
										"bAutoWidth":false,
										"sScrollY" : '70',
										"bSort" : false,
										// 		        "sScrollX" : "100%",
										"bProcessing" : false,
										"sAjaxDataProp" : "rows",
										"sAjaxSource" : CONTEXT_URL+ '/st/invMeterial/itemExcel?itemData='+ itemDatas,
												"aoColumnDefs" : [ 
													{"aTargets" : [ 0 ],"mData" : 
													function(data){ 
//							 						alert(JSON.stringify(data))
													return data.itemNo;
													}, "sWidth":"5%", "bSortable":false },
													
													{"aTargets" : [ 1 ],"mData" : 
														function(data){ 
														return data.itemCatMain;
							    					}, "sWidth":"5%", "bSortable":false },
							    					
							    					{"aTargets" : [2 ],"mData" : 
														function(data){ 
														return data.qty;
							    					}, "sWidth":"5%", "bSortable":false },
							    					
							    					{"aTargets" : [3 ],"mData" : 
														function(data){ 
														return data.actQty;
							    					}, "sWidth":"5%", "bSortable":false },
							    					{"aTargets" : [4 ],"mData" : 
														function(data){ 
														return data.ctrlType;
							    					},  "bVisible": false },
												],
												"oLanguage" : {
													"sProcessing" : "處理中...",
													"sZeroRecords" : "沒有匹配結果",
													"sLoadingRecords" : "讀取中..."
												},
												"fnInitComplete" : function() {
													this.fnAdjustColumnSizing();
												},
											});
					}
					
					//狀態
					function siteWorkOrderStatus(status){
						if(status == "OR01"){
							return "草稿";
						} else if (status == "OR02"){
							return "申請審核中";
						} else if (status == "OR03"){
							return "待派工";
						} else if (status == "OR04"){
							return "已派工";
						} else if (status == "OR05"){
							return "已接工";
						} else if (status == "OR06"){
							return "已委外";
						} else if (status == "OR07"){
							return "已退工";
						} else if (status == "OR08"){
							return "完工審核中";
						} else if (status == "OR09"){
							return "已完工";
						}
						
					}
					//判斷 是否要顯示頁簽
					function tabOrderPageFun(){
						var currentStatus = $("#odrStatus").val();
						if (currentStatus == 'OR04') {
							hideTabs(2);
							hideTabs(3);
							hideTabs(4);
							hideTabs(5);
							hideTabs(6);
							hideTabs(7);
						} else if (currentStatus != 'OR01') {
							$.ajax({
								url : CONTEXT_URL + "/st/st005/tabOrderPage",
								dataType : 'json',
								data : {
									"orderId" :  $("#orderId").val()
								},
								async : false,
								success : function(data){
									//alert(data.msg);
									//alert(JSON.stringify(data));
									//alert(data.rows.length);
									var pageId = "", cantEdit = "", pageIdStr = "";
									for(var i =0; i< data.rows.length; i++){
										pageId = data.rows[i].page_ID;
										cantEdit = data.rows[i].is_EDIT == 'N'; 
										//alert(data.rows[i].page_ID);
										if(pageId == '委外申請' && cantEdit) setReadOnly(2);
										if(pageId == '物料操作' && cantEdit) setReadOnly(3);
										if(pageId == '站點資訊' && cantEdit) setReadOnly(4);
										if(pageId == '基站資訊' && cantEdit) setReadOnly(5);
										if(pageId == '機房資訊' && cantEdit) setReadOnly(6);
										if(pageId == '專線申請' && cantEdit) setReadOnly(7);
									
										pageIdStr += pageId + ",";
									}
									//alert(pageId);
									if(pageIdStr.indexOf("委外申請,") < 0) hideTabs(2);
									if(pageIdStr.indexOf("物料操作,") < 0) hideTabs(3);
									if(pageIdStr.indexOf("站點資訊,") < 0) hideTabs(4);
									if(pageIdStr.indexOf("基站資訊,") < 0) hideTabs(5);
									if(pageIdStr.indexOf("機房資訊,") < 0) hideTabs(6);
									if(pageIdStr.indexOf("專線申請,") < 0) hideTabs(7);		
									
									// TODO
									$("button.file-btn-controll").prop("disabled", false);
								}	
							});
						}
					}
					
					function hideTabs(tabNo) {
						$("#tabs-" + tabNo).hide();
	                	$("#tabs" + tabNo).hide();
					}
					
					function setReadOnly(tabNo) {
						$("#tabs-"+tabNo+" :input ").prop("disabled", true);
					}
					
					function showTabs(tabNo) {
						$("#tabs-" + tabNo).show();
	                	$("#tabs" + tabNo).show();
					}
					
					function changeOptType(optType) {
						$(".MRQTR").hide();
						$(".INSTR").hide();
						$(".RTNTR").hide();
						$(".REMTR").hide();
						$("." + optType).show();
					}
					//取得設備機型
/* 					function getEqpModel(eqpTypeId,tagName){
						$.ajax({
							url : CONTEXT_URL + "/st/st004/search/eqpModel",
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
					} */

				//站點資訊中的 select
				function selectAppendLoc (id,dataList){
					$("#"+id +" option").remove();
					$("#"+id).append("<option value =''>---請選擇---</option>");
					for(var i=0 ; i < dataList.length ; i++){
						$("#"+id).append("<option value ='" +dataList[i].value+"'>"+dataList[i].label+"</option>");
					}
					$("#"+id).change();
				}
				
				//站點資訊的欄位
			function updateInit(data){
				buildCityOptions("locationInfoFrom #city","locationInfoFrom #siteLocArea");
				//站點編號	
				//alert(JSON.stringify(data.row));
				$("#locationIdText").text(data.row.siteLocation.location_ID);
				$("#location_ID").val(data.row.siteLocation.location_ID);
				//站點類別
				selectAppendLoc('loc_TYPE' ,data.row['allSiteTypes']); 
				$("#loc_TYPE").val(data.row.siteLocation.loc_TYPE).change().prop("disabled", true);
				
				//共構/共站
				$("#share_TYPE").val("0");
				$("#share_TYPE").prop("disabled", true);
				//站點名稱
				$("#loc_NAME").val(data.row.siteLocation.loc_NAME);
				//機房型態
				selectAppendLoc('base_TYPE' ,data.row['allRoomTypes']); 
				$("#base_TYPE").val(data.row.siteLocation.base_TYPE).change();
				
				//室內場所分類
				selectAppendLoc('indoor_OPTION' ,data.row['allInBuildingTypes']);
				$("#indoor_OPTION").val(data.row.siteLocation.indoor_OPTION);
				//區域
				$("#city", "#locationInfoFrom").val(data.row.siteLocation.city).change();
				$("#siteLocArea", "#locationInfoFrom").val(data.row.siteLocation.area).change();
				
				
				$("#lon").val(data.row.siteLocation.lon);
				//緯度
				$("#lat").val(data.row.siteLocation.lat);
				
				//地址
				$("#addr", "#locationInfoFrom").val(data.row.siteLocation.addr);
				//歸屬區域 maint_AREA
				getMaintainArea(data.row.siteLocation.city,
						        data.row.siteLocation.area,
						        "maint_AREA");
				$("#maint_AREA").val(data.row.siteLocation.maint_AREA);
						        
				//歸屬單位 maint_DEPT
				getMaintainDept(data.row.siteLocation.maint_AREA,"maint_DEPT");
				$("#maint_DEPT").val(data.row.siteLocation.maint_DEPT).change();
				
				// 負責單位 maint_TEAM
				$("#maint_TEAM").val(data.row.siteLocation.maint_TEAM).change();
				//負責人 maint_USER
				$("#maint_USER").val(data.row.siteLocation.maint_PSN).change();
				//NS_LEVEL 
				selectAppendLoc('ns_LEVEL' ,data.row['allNSLevels']); 
				$("#ns_LEVEL").val(data.row.siteLocation.ns_LEVEL);
				//建築高度
				$("#building_HEIGHT").val(data.row.siteLocation.building_HEIGHT);
				//共站業者 allShareCom								
				var str ="";
				var strKey="";
				$("#shareComDiv").html('');
				for(var i =0; i < data.row.allShareCom.length; i++){
					var divId = "shareComDiv"   + data.row.allShareCom[i].label ;
					var labelId ="shareComLabel"+ data.row.allShareCom[i].label ;
					$("#shareComDiv").append("<div id='" + divId +"' class='checkbox-inline'> </div>");
					
					$("#" + divId).append("<label id='" +  labelId + "'> </label>");
					if(data.row.shareComTemp.indexOf(data.row.allShareCom[i].label) > -1) {
						str = "<input disabled type='checkbox' name='sharcomCheckbox'onclick='sharecomOnclick(this)' checked  id='" + data.row.allShareCom[i].label +"' value='" + data.row.allShareCom[i].label +"'>" + data.row.allShareCom[i].value +"&nbsp;</input>";
						strKey += data.row.allShareCom[i].label + ",";
					} else {
						str = "<input  disabled type='checkbox' name='sharcomCheckbox' onclick='sharecomOnclick(this)' id='" + data.row.allShareCom[i].label +"' value='" + data.row.allShareCom[i].label +"'>" + data.row.allShareCom[i].value +"&nbsp;</input>";
						
					}
					
					$("#" + labelId).append(str);
					$("#" + labelId).append("<i class='fa fa-square-o'></i>");
				}
				
				$("#sharComTab4").val(strKey);
				
				
				//空間
				if(data.row.siteLocation.space_ROOM=="Y"){
					$("#space_ROOM").prop("checked",true);
					$("#space_ROOM").val("Y");
				}
				if(data.row.siteLocation.space_ROOF=="Y"){
					$("#space_ROOF").prop("checked",true);
					$("#space_ROOF").val("Y");
				}
				if(data.row.siteLocation.space_TOP=="Y"){
					$("#space_TOP").prop("checked",true);
					$("#space_TOP").val("Y");
				}
				if(data.row.siteLocation.space_PLATFORM=="Y"){
					$("#space_PLATFORM").prop("checked",true);
					$("#space_PLATFORM").val("Y");
				}
				if(data.row.siteLocation.space_LAND=="Y"){
					$("#space_LAND").prop("checked",true);
					$("#space_LAND").val("Y");
				}
				if(data.row.siteLocation.space_INDOOR=="Y"){
					$("#space_INDOOR").prop("checked",true);
					$("#space_INDOOR").val("Y");
				}
				if(data.row.siteLocation.space_OUTDOOR=="Y"){
					$("#space_OUTDOOR").prop("checked",true);
					$("#space_OUTDOOR").val("Y");
				}
				
				//坪數
				$("#footage").val(data.row.siteLocation.footage);
				//與使用者關係
				$("#emp_RELATION").val(data.row.siteLocation.emp_RELATION);
				//其他資訊
				if(data.row.siteLocation.is_FREEENTER=="Y"){
					$("#is_FREEENTER").prop("checked",true);
					$("#is_FREEENTER").val("Y");
					
				}
				if(data.row.siteLocation.is_KEY=="Y"){
					$("#is_KEY").prop("checked",true);
					$("#is_KEY").val("Y");
				}
				if(data.row.siteLocation.is_INDOOR=="Y"){
					$("#is_INDOOR").prop("checked",true);
					$("#is_INDOOR").val("Y");
					
				}
				if(data.row.siteLocation.need_MISC_LIC=="Y"){
					$("#need_MISC_LIC").prop("checked",true);
					$("#need_MISC_LIC").val("Y");
				}
				if(data.row.siteLocation.has_MISC_LIC=="Y"){
					$("#has_MISC_LIC").prop("checked",true);
					$("#has_MISC_LIC").val("Y");
				}
				
				//補充說明
				$("#loc_DESC").val(data.row.siteLocation.loc_DESC);
				
				$("#zip", "#locationInfoFrom").val(data.row.siteLocation.zip);
				$("#village", "#locationInfoFrom").val(data.row.siteLocation.village);
				$("#adjacent", "#locationInfoFrom").val(data.row.siteLocation.adjacent);
				$("#road", "#locationInfoFrom").val(data.row.siteLocation.road);
				$("#lane", "#locationInfoFrom").val(data.row.siteLocation.lane);
				$("#alley", "#locationInfoFrom").val(data.row.siteLocation.alley);
				$("#sub_ALLEY", "#locationInfoFrom").val(data.row.siteLocation.sub_ALLEY);
				$("#door_NO", "#locationInfoFrom").val(data.row.siteLocation.door_NO);
				$("#floor_EX", "#locationInfoFrom").val(data.row.siteLocation.floor_EX);
				$("#floor", "#locationInfoFrom").val(data.row.siteLocation.floor);
				$("#floor_EX", "#locationInfoFrom").val(data.row.siteLocation.floor_EX);
				$("#room", "#locationInfoFrom").val(data.row.siteLocation.room);
				$("#add_OTHER", "#locationInfoFrom").val(data.row.siteLocation.add_OTHER);
				
	
				
				
			}
			
			//站點資訊-	地區
			function getMaintainArea(city,area,tagName){
				
				$.ajax({
					url : CONTEXT_URL + "/st/locTemp/maintainArea",
					type : 'POST',
					data : {
						cityId :city,
						townId :area
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
			
			function getMaintainDept(maintArea,tagName){
				$.ajax({
					url : CONTEXT_URL + "/st/locTemp/maintainDept",
					type : 'POST',
					data : {
						domain :maintArea
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
			//委外申請***********************************************************************************************
			//委外申請-委外廠商
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
									if (("${siteWork.OS_VEN}" != "" && "${siteWork.OS_VEN}" == data.rows[i].co_VAT_NO) || "${siteWork.OS_VEN}" == "") {
										$("#coVatNoTab2").append("<option value="+data.rows[i].co_VAT_NO+">"+data.rows[i].co_NAME+"</option>");
									}									
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
			
			function saveBtnTabs2(str) {
				//委外申請---存檔button
				if ($("#coVatNoTab2").val() == "") {
					alert("廠商名稱未選擇");
					return;
				}
				if ($("#poNoTab2").val() == "") {
					alert("PO單號未選擇");
					return;
				}
				if ($("#showOsDescTab2").val().length > 200) {
					alert('委外說明超過最大限制200字');
					return;
				}
		     
				$.ajax({
					url : CONTEXT_URL + "/st/outSourcing/out/save",
					type : 'POST',
					dataType : "json",
					data : {
						orderId : $("#orderId").val(), //工單編號  
						coVatNo : $("#coVatNoTab2").val(), // 廠商統編  
						poId : $("#poNoTab2").val(), //POID 
						eqpType : $("#eqpTypeTab2").val(), //設備型態  
						eqpModel : $("#siteBuildEqpModel").val(), //機型  
						osDesc : $("#showOsDescTab2").val(), //委外說明 
						amount : $("#priceTab2").val(), //總金額  
						osId : $("#osIdTab2").val(), //派工單號
						itemId : $("#checkBoxItemIdTab2").val(), //工項設定的ITEM_ID
						poNo : $("#poNoTab2").find("option:selected").text()
					},
					async : false,
					success : function(data) {
						//alert(JSON.stringify(data));
						//str 判斷msg的訊息 變數 0
		                if(data.success && str == "0"){
		                	alert("存檔成功");
		                }
						
		                $("#osIdTab2").val(data.msg);	// 將OS_ID塞入
						outSourceTableFun(data.msg);
						toItemMainTableFun(data.msg);
					}
				});
			}
			
			function disabledBtn(status, loginUsrVendor){
		        //alert(status + ":" +loginUsrVendor);
		        if ('${view}' == 'view') {
		        	allView();
		        	return;
		        }
				$("button.default-disabled").prop("disabled",true);
				$("#addTab2").prop("disabled",false);
				
				// 先鎖定可編輯欄位，再依可編輯狀態打開
				$("#coVatNoTab2").prop("disabled",true); //廠商名稱
				$("#poNoTab2").prop("disabled",true); //po單號
				$("#showOsDescTab2").prop("disabled", true);  // 委外說明
				
				if(status == 'OS01' || status == 'OS03'){ // 草稿 駁回
					$("#saveTab2").prop("disabled",false); //存檔		
					$("#applicationBtn").prop("disabled",false); //申請
					$("#applicationCancelBtn").prop("disabled",false); //取消申請
					$("#poItemTab2").prop("disabled",false); //工項設定
					
					$("#coVatNoTab2").prop("disabled", false); //廠商名稱
					$("#poNoTab2").prop("disabled", false); //po單號
					$("#showOsDescTab2").prop("disabled", false);  // 委外說明
				} else if (status == 'OS02'){ //審核中
					$("#coVatNoTab2").prop("disabled",true);	//廠商名稱
					$("#poNoTab2").prop("disabled",true);	//po單號
					$("#showOsDescTab2").prop("disabled", true);  // 委外說明
				} else if (status == 'OS04'){ //待派工
// 					$("#saveTab2").prop("disabled",false); //存檔
				    $("#applicationCancelBtn").prop("disabled",false); //取消申請
					$("#outsourcingButton").prop("disabled",false); //委外派工
				} else if (status == 'OS05'){ //已派工 
					if ($("#checkDabClick").val() != "view"){
						$("#applicationCancelBtn").prop("disabled",false); //取消申請
						$("#outsourcingButton").prop("disabled",false); //委外派工
						$("#applyWorkButton").prop("disabled",false); //驗收
					}
				} else if (status == 'OS06'){ //驗收送審
				
				} else if (status == 'OS07'){ //已驗收
				
				} else if (status == 'OS08'){ //已取消
 					$("#addTab2").prop("disabled",false);
					$("#poItemTab2").prop("disabled",true);
				}
				
				//登入者若是廠商(委外申請)
				if (loginUsrVendor == "true"){
					$("button.vendorWork-disable").prop("disabled", true);
// 					$("#tabs-1 :input ").prop("disabled", true);
// 					$("#tabs-2 :input ").prop("disabled", true);
// 					$("#tabs-3 :input ").prop("disabled", true);
// 					$("#tabs-4 :input ").prop("disabled", true);
// 					$("#tabs-5 :input ").prop("disabled", true);
// 					$("#tabs-6 :input ").prop("disabled", true);
// 					$("#tabs-7 :input ").prop("disabled", true);
				}
			}
			//委外申請 END**********************************************************************************
			
			function getMaintainTeam(maintDept,tagName){
				$.ajax({
					url : CONTEXT_URL + "/st/locTemp/maintainTeam",
					type : 'POST',
					data : {
						"deptId" :maintDept
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
			
			function getMaintainUser(maintTeam,tagName){
				$.ajax({
					url : CONTEXT_URL + "/st/locTemp/maintainUser",
					type : 'POST',
					data : {
						"deptId" :maintTeam
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
			
			function siteLocationCallBackFun(addressObject){
				var obj = JSON.parse(addressObject);
				$("#zip", "#locationInfoFrom").val(obj.zip);
				$("#city", "#locationInfoFrom").val(obj.city);
				$("#siteLocArea", "#locationInfoFrom").val(obj.area);
				$("#village", "#locationInfoFrom").val(obj.village);
				$("#road", "#locationInfoFrom").val(obj.road);
				$("#adjacent", "#locationInfoFrom").val(obj.adjacent);
				$("#lane", "#locationInfoFrom").val(obj.lane);
				$("#alley", "#locationInfoFrom").val(obj.alley);
				$("#subAlley", "#locationInfoFrom").val(obj.subAlley);
				$("#doorNo", "#locationInfoFrom").val(obj.doorNo);
				$("#doorNoEx", "#locationInfoFrom").val(obj.doorNoEx);
				$("#floor", "#locationInfoFrom").val(obj.floor);
				$("#floorEx", "#locationInfoFrom").val(obj.floorEx);
				$("#room", "#locationInfoFrom").val(obj.room);
				$("#addOther", "#locationInfoFrom").val(obj.remark);
				$.ajax({	
					url: CONTEXT_URL + "/common/address/combineAddress",
					dataType: "json",
					data : addressObject, 
					contentType : 'application/json',
					type: "POST",
					async: false,
					success : function(data) {
						$("#addr", "#locationInfoFrom").val(data.row.fullAddress);
					}
				});
			}
			
			function unselect(){
				if($("#share_TYPE").val()==0){
					$("input[name=SHARE_COM]").attr("checked",false);
				}
			}
					
			function allView() {
				$("#pickWorkBut").prop("disabled", true);
				$("#retiredWorkBut").prop("disabled", true);
				$("#completionWorkBut").prop("disabled", true);
				$("#newStaetWorkBut").prop("disabled", true);
				$("#tabs-1 :input ").prop("disabled", true);
				$("#tabs-2 :input ").prop("disabled", true);
				$("#tabs-3 :input ").prop("disabled", true);
				$("#tabs-4 :input ").prop("disabled", true);
				$("#tabs-5 :input ").prop("disabled", true);
				$("#tabs-6 :input ").prop("disabled", true);
				$("#tabs-7 :input ").prop("disabled", true);	
				$("#downloadAttch").prop("disabled", false);	// 附件鈕
			}
			
			function initOrderType() {			
				//變更 工單類別 塞值 到 物件裡

				$("#siteOrderType").change(function() {
					if ($("#siteOrderType").val() != "") {	
						if($("#siteHuntSiteId").val() != null && $("#siteHuntSiteId").val() != "") {				
							//alert("123");
							osResponsibleUnit();
						} else {
							alert('請先選擇 基站編號!!');
							$('#siteOrderType').val("").change();
						}
					} else {
						$('#siteOrderType').val("").change();
						$("#showResponsibleUnit").text("");
						$("#siteResponsibleUnit").val("");
					}
				});
			}
			
			function initData() {
				//編輯時
				if ($("#singleOrderApplyEvent").val() == "edit") {
					$("#siteBuildSiteId").val("${siteWork.SITE_ID}");
					//工單編號欄位
					$("#showWorkId").text("${siteWork.WORK_ID}");
					$("#workId").val("${siteWork.WORK_ID}");
					//工務類別
					$("#siteBuildWorkType").val("${siteWork.WORK_TYPE}").change();
					//重要等級
					$("#priority").val("${siteWork.PRIORITY}").change();
					//申請人原
					$("#showApplyUser").text("${applyUserName}");
					$("#applyUser").val("${siteWork.APL_USER}");
					//站點編號
					$("#showsiteBuildLocationId").text("${location.LOCATION_ID}");
					$("#siteBuildLocationId").val("${location.LOCATION_ID}");
					//站點名稱
					$("#showSiteName").text("${location.LOC_NAME}");
					$("#siteName").val("${location.LOC_NAME}");
					//多頻段站點 
					if ("${isMultibandLocation}" > 1) {
						$("#multibandLocation").prop("checked", true);
					}
					//處理狀態
					$("#showOdrStatus").text("${showOdrStatus}");
					$("#odrStatus").val("${odrStatus}");
					
					//建站目地
					$("#purpose").prop("disabled", false);
					$("#purpose").val("${siteWork.PURPOSE}").change();
					$("#purpose").prop("disabled", true);
					//處理狀態
					$("#showWorkStatus").text("${workStatusName}");
					//工程類別
					$("#osType").val("${siteWork.OS_TYPE}").change();
					//委外廠商
					$("#osVen").val("${siteWork.OS_VEN}").change();
					//地址相關欄位		
					$("#showAddr").text("${siteWork.ADDR}");
					$("#ADDR").val("${siteWork.ADDR}");
					$("#zip").val("${siteWork.ZIP}");
					$("#city").val("${siteWork.CITY}");
					$("#area").val("${siteWork.AREA}");
					$("#village").val("${siteWork.VILLAGE}");
					$("#road").val("${siteWork.ROAD}");
					$("#adjacent").val("${siteWork.ADJACENT}");
					$("#lane").val("${siteWork.LANE}");
					$("#alley").val("${siteWork.ALLEY}");
					$("#sub_ALLEY").val("${siteWork.SUB_ALLEY}");
					$("#door_NO").val("${siteWork.DOOR_NO}");
					$("#door_NO_EX").val("${siteWork.DOOR_NO_EX}");
					$("#floor").val("${siteWork.FLOOR}");
					$("#floor_EX").val("${siteWork.FLOOR_EX}");
					$("#room").val("${siteWork.ROOM}");
					$("#add_OTHER").val("${siteWork.ADD_OTHER}");
					//經度
					$("#showLon").text("${siteWork.SR_LON}");
					$("#lon").val("${siteWork.SR_LON}");
					//緯度
					$("#showLat").text("${siteWork.SR_LAT}");
					$("lat").val("${siteWork.SR_LAT}");
					//基站編號
					$("#siteHuntSiteId").val("${siteWork.BTS_SITE_ID}");
					//基站頻段	
						$("#siteFeqId").val("${siteWork.FEQ_ID}");
						$("#showFeqId").text("${feqName}");
						

					//工單類別
					$("#siteOrderType").val("${siteOrderType}");
					//============================================================
					$("#odTypeId").val("${siteOrderType}");
					$("#eqpType").val("${siteWork.EQP_TYPE_ID}");
					//alert("${siteOrderType}");
					//alert("${siteWork.EQP_TYPE_ID}");
					//============================================================
					initOrderType();
					$("#siteOrderType").trigger("change");
					

					//基站名稱
					$("#siteBuildSiteName").val("${siteWork.SITE_NAME}");
					//設備型態
					$("#siteBuildEqpType").prop("readonly", false);		
					$("#siteBuildEqpType").val("${siteWork.EQP_TYPE_ID}");
					$("#siteHuntEqpType").val("${siteWork.EQP_TYPE_ID}");
					$("#siteBuildEqpType").prop("readonly", true);
					//getEqpModel($("#siteBuildEqpType").val(),"siteBuildEqpModel");
					//設備機型
					$("#siteBuildEqpModel").prop("readonly", false);		
					$("#siteBuildEqpModel").val("${siteWork.EQP_MODEL_ID}").change();
					$("#siteBuildEqpModel").prop("readonly", true);
					//涵蓋類別
					$("#coverageType").val("${siteWork.COVERAGE_TYPE}").change();
					//Configuration
					$("#btsConfig").val("${siteWork.BTS_CONFIG}");
					//Donor BTS 
					$("#donorSiteText").val("${donorBtsSiteId}");
					$("#donorSite").val("${siteWork.DONOR_SITE}");
					//Feederless
					$("#feederless").val("${siteWork.FEEDERLESS}").change();
					//Master Site
					$("#masterSiteText").val("${masterBtsSiteId}");
					$("#masterSite").val("${siteWork.MASTER_SITE}");
					//預計完工日
					$("#prediceEndDate").val("${prediceEndDate}");
					//作業類別
					$("#showWorkType").text("${workTypeName}");
					//完工日期
					$("#endTime").val("${siteWork.END_TIME}");
	                //工單號碼
	                $("#showOrderId").text("${orderId}");
	                $("#orderId").val("${orderId}");
	                //派工目的
	                $("#showOrderType").text("${sysOrderTypeList[0].OD_TYPE_NAME}");
	                $("#orderType").val("${siteWorkOrderList[0].ORDER_TYPE}");
	                //接工人員
	                $("#showPickWorkName").text("${pickUser}");
	                $("#pckWorkName").val("${userName}");
	                //處理狀態 
	                $("#showOdrStatus").text(siteWorkOrderStatus("${siteWorkOrderList[0].ODR_STATUS}"));
	                $("#odrStatus").val("${odrStatus}");

	                disabledBtn($("#odrStatus").val(), $("#loginUsrVendor").val());

	                if (($("#odrStatus").val() == 'OR05' || $("#odrStatus").val() == 'OR06')  
	                		&& $("#loginUsrVendor").val() != "true"){
	    				$("#newStaetWorkBut").prop("disabled", false);
	    				$("#retiredWorkBut").prop("disabled", false);
	    				$("#completionWorkBut").prop("disabled", false);
	    				$("#endDesc").prop("disabled", false);
//	                 				$("#orderDesc").prop("disabled", false);
	    				$("#downloadAttch").prop("disabled", false);
	    				$("#saveBtnTabs1").prop("disabled", false);
	    				$("#saveTab2").prop("disabled", false);
	    				$("#applicationBtn").prop("disabled", false);
	    				$("#applicationCancelBtn").prop("disabled", false);
	    				$("#poItemTab2").prop("disabled",false); //工項設定
	    			}
				} else {
					//作業附件按鈕
					$("#fileProcessBtn").prop("disabled", true);
				} //編輯時end
			}
			
			//負責單位
			function osResponsibleUnit(){		
				$.ajax({
					url : CONTEXT_URL + "/st/st009/responsibleUnit",
					type : 'POST',
					data : {
						odTypeId :$("#odTypeId").val(),
						eqpType :$("#eqpType").val(),
						city : $("#city").val(),
						area : $("#area").val()
					},
					async : false,
					success : function(data){
						//console.log(data);
						//alert(JSON.stringify(data));
						if(data.success){	
							$("#showResponsibleUnit").text(data.row.dept_NAME);
							$("#siteResponsibleUnit").val(data.row.dept_ID);
						}
					}
				});			
			}
</script>
</head>
<body>
	<div class="row">
		<div class="col-xs-12 col-sm-12">
			<div class=" ui-draggable ui-droppable">
				<div class="box-header">
					<div class="box-name">
						<i class="fa fa-search"></i> <span>單張工單作業處理 </span>
					</div>
					<div class="box-icons">
						<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
						</a> <a class="expand-link"> <i class="fa fa-expand"></i>
						</a>
					</div>
					<div class="no-move"></div>
				</div>
				<div class="box-content">
					<form class="form-horizontal" id="addLocatiuonForm" action="">
						<span id="siteBuildProcessBtnsTr">
						<button class="btn btn-primary btn-label-left"
							style="margin-right: 10px" type="button" id="pickWorkBut">
							<span><i class="fa fa-hand-o-right"></i></span>接工</button>
						<button class="btn btn-primary btn-label-left"
							style="margin-right: 10px" type="button" id="retiredWorkBut">
							<span><i class="fa fa-reply-all"></i></span>退工</button>
						<button class="btn btn-primary btn-label-left"
							style="margin-right: 10px" type="button" id="completionWorkBut">
							<span><i class="fa fa-legal"></i></span>完工</button>
						<button class="btn btn-primary btn-label-left"
							style="margin-right: 10px" type="button" id="newStaetWorkBut">
							<span><i class="fa fa-repeat"></i></span>重啟工單</button>
						</span>
						<div id="tabs">
							<ul>
								<li><div id="tabs1"><a href="#tabs-1">作業內容</a></div></li>
								<li><div id="tabs2"><a href="#tabs-2" id ="outSourceApply">委外申請</a></div></li>
								<li><div id="tabs3"><a href="#tabs-3">物料操作</a></div></li>
								<li><div id="tabs4"><a href="#tabs-4" id = "locationInfo">站點資訊</a></div></li>
								<li><div id="tabs5"><a href="#tabs-5" id="siteData">基站資訊</a></div></li>
								<li><div id="tabs6"><a href="#tabs-6">機房資訊</a></div></li>
								<li><div id="tabs7"><a href="#tabs-7">專線申請</a></div></li>
							</ul>
							<div id="tabs-1">
								<table style="width: 100%">
									<tr>
										<td nowrap="nowrap" align='center' colspan="2">
											<button class="btn btn-primary btn-label-left"
												style="margin-right: 20px" type="button" id="saveBtnTabs1"><span><i class="fa fa-save"></i></span>存檔
											</button>
											<button class="btn btn-primary btn-label-left file-btn-controll" type="button"
												id="downloadAttch"><span><i class="fa fa-download"></i></span>附件下載</button>
										</td>
									</tr>
									<tr>
										<td width="10%" nowrap="nowrap"><label
											class="col-sm-10 control-label">工單號碼 :</label></td>
										<td width="15%">
											<p style="padding-top: 15px; padding-left: 15px;" id="showOrderId"></p>
											<input id="orderId" name="orderId" type="hidden"></input>
										</td>
										<td width="10%" nowrap="nowrap"><label
											class="col-sm-10 control-label">派工目的 :</label></td>
										<td width="15%">
											<p style="padding-top: 15px; padding-left: 15px;" id="showOrderType"></p>
											<input id="orderType" name="orderType" type="hidden"></input>
										</td>
										<td width="10%"><label class="col-sm-10 control-label">接工人員
												:</label></td>
										<td width="15%">
											<p style="padding-top: 15px; padding-left: 15px;" id="showPickWorkName"></p>
											<input id="pickWorkName" name="pckWorkName" type="hidden"></input>
										</td>
										<td width="10%" nowrap="nowrap"><label
											class="col-sm-10 control-label">處理狀態 :</label></td>
										<td width="15%">
											<p style="padding-top: 15px; padding-left: 15px;" id="showOdrStatus"></p>
											<input id="odrStatus" name="odrStatus" type="hidden"></input>
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
										<td colspan="2" nowrap="nowrap">
											<div style="margin-top: 5px">
												<textarea id="endDesc" name="endDesc"
													style="resize: none; width: 94%; border-left-width: 1px; margin-left: 15px; margin-top: 4px;"
													rows="3">${fn:replace(siteWorkOrderList[0].END_DESC,"\\n",'<br>') }</textarea>
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
													<c:param name="processType" value="SiteBuildSingleWorkCompletionApply"></c:param>
													<c:param name="applyNo" value="${siteWorkOrderList[0].ORDER_ID}"></c:param>
												</c:import>
											</div>
										</td>
									</tr>
								</table>
								<hr>
								<table style="width: 100%">
										<jsp:include page="/WEB-INF/views/ajaxPage/st/SingleOrderApply.jsp"></jsp:include>
								</table>
							</div>
							<div id="tabs-2">
								<table id="outSourceTable"
									class="table table-bordered  table-hover table-heading table-datatable"
									width="96%">
									<thead style="background-color: #f0f0f0;">
										<tr>
											<th class="theadth" style="width: 300px;">派工單號</th>
											<th class="theadth" style="width: 150px;">廠商</th>
											<th class="theadth" style="width: 150px;">PO單號</th>
											<th class="theadth" style="width: 150px;">申請日期</th>
											<th class="theadth" style="width: 150px;">派工狀態</th>
											<th class="theadth" style="width: 150px;">已驗完</th>
										</tr>
									</thead>
								</table>
								<h4 class="page-header"></h4>
								<button
									class="btn btn-primary btn-label-left retiredWork-disabled vendorWork-disable"
									style="margin-right: 10px" type="button" id="addTab2"><span><i class="fa fa-plus"></i></span>新增</button>
								<button
									class="btn btn-primary btn-label-left retiredWork-disabled default-disabled endorWork-disable vendorWork-disable"
									style="margin-right: 10px" type="button" id="saveTab2"
									onclick="saveBtnTabs2('0')"><span><i class="fa fa-save"></i></span>存檔</button>
								<button
									class="btn btn-primary btn-label-left retiredWork-disabled default-disabled endorWork-disable vendorWork-disable"
									style="margin-right: 10px" type="button" id="poItemTab2"><span><i class="fa fa-cog"></i></span>工項設定</button>
								<button
									class="btn btn-primary btn-label-left retiredWork-disabled default-disabled  endorWork-disable vendorWork-disable"
									style="margin-right: 10px" type="button" id="applicationBtn"><span><i class="fa fa-legal"></i></span>申請</button>
								<button
									class="btn btn-primary btn-label-left retiredWork-disabled default-disabled endorWork-disable vendorWork-disable"
									style="margin-right: 10px" type="button"
									id="applicationCancelBtn"><span><i class="fa fa-times"></i></span>取消申請</button>
								<button
									class="btn btn-primary btn-label-left retiredWork-disabled default-disabled endorWork-disable vendorWork-disable"
									style="margin-right: 10px" type="button" id="outsourcingButton"><span><i class="fa fa-briefcase"></i></span>委外派工</button>
								<button
									class="btn btn-primary btn-label-left retiredWork-disabled default-disabled endorWork-disable vendorWork-disable"
									style="margin-right: 10px" type="button" id="applyWorkButton"><span><i class="fa fa-legal"></i></span>驗收</button>
								<table style="width: 100%">
									<jsp:include page="/WEB-INF\views\ajaxPage\st\OutSource.jsp" />
								</table>
							</div>

							<div id="tabs-3">
								<div>
									<table id="meterialTable" class="table table-bordered  table-hover table-heading table-datatable" width="96%"
										style="table-layout: fixed; word-break: break-all; word-wrap: break-word;">
										<thead style="background-color: #f0f0f0;">
											<tr>
												<th class="theadth" style="width: 250px;">操作類別</th>
												<th class="theadth" style="width: 150px;">單號</th>
												<th class="theadth" style="width: 150px;">申請日期</th>
					 							<th class="theadth" style="width: 150px;">領/退料倉庫</th>
												<th class="theadth" style="width: 150px;">處理狀態</th>
											</tr>
										</thead>
									</table>
								</div>
								<h4 class="page-header"></h4>
								<button class="btn btn-primary btn-label-left" style="margin-right: 10px" type="button" id="btnMrqtr"><span><i class="fa fa-plus"></i></span>新增</button>
								<button class="btn btn-primary btn-label-left MRQTR"	style="margin-right: 10px" type="button" id="btnMrqtrExXls" ><span><i class="fa fa-file-text-o"></i></span>下載空白領料單(Excel)</button>
								<button class="btn btn-primary btn-label-left MRQTR"	style="margin-right: 10px" type="button" id="btnMrqtrIpmXls"><span><i class="fa fa-cloud-upload"></i></span>匯入領料單(Excel)</button>
								<button class="btn btn-primary btn-label-left MRQTR RTNTR" style="margin-right: 10px" type="button" id="btnMrqtrApply"><span><i class="fa fa-legal"></i></span>申請</button>
								<button class="btn btn-primary btn-label-left INSTR REMTR" style="margin-right: 10px" type="button" id="btnRemtr"><span><i class="fa fa-wrench"></i></span>拆/裝</button>
                                <!-- include MeterialOptM.jsp -->
                                 <jsp:include page="/WEB-INF\views\ajaxPage\st\MeterialOptM.jsp" />
							</div>
<!--tabs-3-->
							<div id="tabs-4">

								<button class="btn btn-primary btn-label-left file-btn-controll"
									style="margin-right: 10px" type="button" id="fileProcessBtnTab4"><span><i class="fa fa-upload"></i></span>附件上傳</button>
								<button class="btn btn-primary btn-label-left"
									style="margin-right: 10px" type="button" id="saveTab4"><span><i class="fa fa-save"></i></span>存檔</button>

								<jsp:include page="/WEB-INF/views/ajaxPage/st/LocationInfo.jsp"></jsp:include>
							</div>
							<!-- 							tabs-4 End -->
							<div id="tabs-5">
	<!-- 							<h5 class="page-header"></h5> -->
								<button class="btn btn-primary btn-label-left file-btn-controll"
									style="margin-right: 10px" type="button" id="uploadSiteTempBoutton"><span><i class="fa fa-upload"></i></span>附件上傳</button>
								<button class="btn btn-primary btn-label-left"
									style="margin-right: 10px" type="button" id="saveSiteTempBoutton"><span><i class="fa fa-save"></i></span>存檔</button>


 								<table style="width: 100%">
										<jsp:include page="/WEB-INF\views\ajaxPage\st\SiteLines.jsp" />
								</table>	


								<!-- 							Toggle effect End -->
							</div>
							<!-- 							tabs-5 End -->

							<div id="tabs-6">


								<button class="btn btn-primary btn-label-left file-btn-controll"
									style="margin-right: 10px" type="button" id="engineRoomFileProcessBtn"><span><i class="fa fa-upload"></i></span>附件上傳</button>
								<button class="btn btn-primary btn-label-left" id="saveEngineRoomBtn"
									style="margin-right: 10px" type="button"><span><i class="fa fa-save"></i></span>存檔</button>


								<table style="width: 100%; height: 199px;">


									<tr>
										<td>
											<div class="clearfix">&nbsp;</div>
										</td>
									</tr>

									<tr>
										<td><label class="col-sm-12 control-label">機房施工KO
												:</label></td>
										<td>
											<div class="col-sm-10">
												<input id="constrKoDate" name="CONSTR_KO_DATE" type="text"
													class="form-control" readonly="readonly"
													placeholder="點選輸入框">
											</div>
										</td>
										<td><label class="col-sm-12 control-label">機房施工完成
												:</label></td>
										<td>
											<div class="col-sm-10">
												<input id="constrDoneDate" name="CONSTR_DONE_DATE" type="text"
													class="form-control" readonly="readonly"
													placeholder="點選輸入框">
											</div>
										</td>
										<td><label class="col-sm-12 control-label">SMR/電池
												:</label></td>
										<td>
											<div class="col-sm-10">
												<input id="smrReadyDate" name="SMR_READY_DATE" type="text"
													class="form-control" readonly="readonly"
													placeholder="點選輸入框">
											</div>
										</td>

									</tr>

									<tr>
										<td>
											<div class="clearfix">&nbsp;</div>
										</td>
									</tr>

									<tr>
										<td nowrap="nowrap"><label class="col-sm-12 control-label">UPS
												READY :</label></td>
										<td>
											<div class="col-sm-10">
												<input id="upsReadyDate" name="UPS_READY_DATE" type="text"
													class="form-control" readonly="readonly"
													placeholder="點選輸入框">
											</div>
										</td>
										<td><label class="col-sm-12 control-label">AC
												READY :</label></td>
										<td>
											<div class="col-sm-10">
												<input id="acReadyDate" name="AC_READY_DATE" type="text"
													class="form-control" readonly="readonly"
													placeholder="點選輸入框">
											</div>
										</td>
										<td><label class="col-sm-12 control-label">發電機
												READY :</label></td>
										<td>
											<div class="col-sm-10">
												<input id="dynamoReadyDate" name="DYNAMO_READY_DATE" type="text"
													class="form-control" readonly="readonly"
													placeholder="點選輸入框">
											</div>
										</td>

									</tr>

									<tr>
										<td>
											<div class="clearfix">&nbsp;</div>
										</td>
									</tr>

									<tr>
										<td nowrap="nowrap"><label class="col-sm-12 control-label">ADM
												READY :</label></td>
										<td>
											<div class="col-sm-10">
												<input id="admReadyDate" name="ADM_READY_DATE" type="text"
													class="form-control" readonly="readonly"
													placeholder="點選輸入框">
											</div>
										</td>
										<td nowrap="nowrap"><label class="col-sm-12 control-label">電力設備
												READY :</label></td>
										<td>
											<div class="col-sm-10">
												<input id="powerReadyDate" name="POWER_READY_DATE" type="text"
													class="form-control" readonly="readonly"
													placeholder="點選輸入框">
											</div>
										</td>
										<td nowrap="nowrap"><label class="col-sm-12 control-label">傳輸設備
												READY :</label></td>
										<td>
											<div class="col-sm-10">
												<input id="transReadyDate" name="TRANS_READY_DATE" type="text"
													class="form-control" readonly="readonly"
													placeholder="點選輸入框">
											</div>
										</td>

									</tr>
									<tr>
										<td>
											<div class="clearfix">&nbsp;</div>
										</td>
									</tr>
									<tr>
										<td><label class="col-sm-12 control-label">專線資訊 :</label>
										</td>

									</tr>
									<tr>
										<td colspan="6">
											<jsp:include page="/WEB-INF/views/ajaxPage/st/line/LineInfo.jsp"></jsp:include>
										</td>
									</td>
									</tr>
									<tr>
										<td>
											<div class="clearfix">&nbsp;</div>
										</td>
									</tr>
									<tr>
										<td>
											<div class="clearfix">&nbsp;</div>
										</td>
									</tr>
									<tr>
										<td>
											<div class="clearfix">&nbsp;</div>
										</td>
									</tr>
									<tr>
										<td>
											<div class="clearfix">&nbsp;</div>
										</td>
									</tr>
									<tr>
										<td>
											<div class="clearfix">&nbsp;</div>
										</td>
									</tr>
									<tr>
										<td>
											<div class="clearfix">&nbsp;</div>
										</td>
									</tr>
									<tr>
										<td>
											<div class="clearfix">&nbsp;</div>
										</td>
									</tr>
									<tr>
										<td>
											<div class="clearfix">&nbsp;</div>
										</td>
									</tr>
									<tr>
										<td>
											<div class="clearfix">&nbsp;</div>
										</td>
									</tr>



								</table>
							</div>
							<!-- 							tabs-6 End -->
							<div id="tabs-7">

								<div>
									<table id="SiteLineApplyTable" class="table table-bordered  table-hover table-heading table-datatable"
										style="table-layout: fixed; word-break: break-all; word-wrap: break-word;">
										<thead style="background-color: #f0f0f0;">
											<tr>
												<th class="theadth" style="width: 150px;">申請單號</th>
												<th class="theadth" style="width: 150px;">申請類別</th>
												<th class="theadth" style="width: 150px;">專線號碼</th>
												<th class="theadth" style="width: 150px;">甲端</th>
												<th class="theadth" style="width: 150px;">乙端</th>
												<th class="theadth" style="width: 150px;">申請日期</th>
												<th class="theadth" style="width: 150px;">申請狀態</th>
												<th class="theadth" style="width: 150px;">專線狀態</th>
											</tr>
										</thead>
									</table>
								</div>

								<br></br>
								<button class="btn btn-primary btn-label-left "
									style="margin-right: 10px" type="button" id="lineApplyInsertBtn"><span><i class="fa fa-plus"></i></span>新增</button>
								<button class="btn btn-primary btn-label-left"
									style="margin-right: 10px" type="button" id="lineApplySaveBtn"><span><i class="fa fa-save"></i></span>存檔</button>
								<button class="btn btn-primary btn-label-left new-disabled"
									style="margin-right: 10px" type="button" id="lineApplyBtn" ><span><i class="fa fa-legal"></i></span>申請</button>
								<button class="btn btn-primary btn-label-left new-disabled"
									style="margin-right: 10px" type="button" id="applyExportExlBtn" ><span><i class="fa fa-cloud-download"></i></span>匯出Excel</button>
								<button class="btn btn-primary btn-label-left new-disabled"
									style="margin-right: 10px" type="button" id="lineApplyCancelBtn"><span><i class="fa fa-times"></i></span>取消申請</button>
								<button class="btn btn-primary btn-label-left new-disabled"
									style="margin-right: 10px" type="button" id="lineApplyCompleteBtn" ><span><i class="fa fa-flag-checkered"></i></span>竣工</button>

								<jsp:include page="./line/LineApplyM.jsp"></jsp:include>
							</div>
							<!--tabs-7 End -->
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
    <!-- 修改欄位 的變數 -->  
    <input id="singleOrderApplyEvent"  type="hidden" name="singleOrderApplyEvent" value="${singleOrderApplyEvent}"></input>
    <!-- LOGIN -->
	<input id="userName" value="${userName}" type="hidden"></input>
	<input id="showUserName" value="${showUserName}" type="hidden"></input>
	<input id="sharComTab4" type="hidden"></input>
	
	<!-- 廠商權限 -->
	<input id="loginUsrVendor" type="hidden" name="loginUsrVendor" value="${loginUsrVendor }"></input>
	<!-- 上傳 -->
	<div id="showFileUploadPageTab4"></div>
	<!-- 下載 -->
	<div id="showFileDownloadFrame"></div>
	<!-- 地址 -->
	<div id="showSiteLocAddrPageTab4"></div>
	<!-- 工項設定 -->			
	<div id="showPoItemPag" ></div>
	<!-- 判斷是否點擊 -->
	<input id ="checkDabClick" type="hidden" value="${checkDabClick}"> 
	
	<!-- 匯入Excel -->
	<div id="upladExcelPage"></div>
	<!-- 探勘 -附件上傳 -->
	<div id="showFileUploadFrame"></div>
	<div id="showEngineRoomFileUploadFrame"></div>
	<!-- 重啟工單 -->
	<div id="showReopenWorkOrderPage"></div>
    <!--探勘 -->
	<input id="siteHuntEqpType"  type="hidden" ></input>
	
	<!-- 建站委外申請簽核、驗收類型 -->
	<input id="os_process_apply" name="os_process_apply" type="hidden" value="SiteBuildOutsourcingApply" /> 
	<input id="os_process_accept" name="os_process_accept" type="hidden" value="SiteBuildOutsourcingAccept" />
	
</body>
</html>