<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>調撥申請作業</title>
<script
	src="<s:url value="/resources/plugins/tinymce/tinymce.min.js" />"></script>
<script
	src="<s:url value="/resources/plugins/tinymce/jquery.tinymce.min.js" />"></script>
<script type="text/javascript">

	////Run Select2 on element
	function Select2Priority() {
		$("#addEdit_PRIORITY").select2();
	}
	function Select2Type() {
		$("#addEdit_TYPES").select2();
	}

	$(document).ready(function() {
	LoadSelect2Script("<s:url value="/resources/plugins/select2/select2.min.js" />", selectDefault);
						$('#addneedDate').datepicker({
							dateFormat : "yy/mm/dd",
							changeYear : true,
							changeMonth : true,
							showButtonPanel : true,
							showTime : false
						});
						
						$("#dtlgrid").jqGrid({
											datatype : "local",
											pager : '#dtlpager',
											autowidth:'true',
											colNames : [  '料號', '品名', '物料狀態', '申請調撥數','目前庫存數','Booking數','檢核結果','','','' ],
											colModel : [ 
												{name : 'mat_no',index : 'mat_no',width : 40 , align : 'center',sortable : false},
											    {name : 'matName',index : 'matName',width : 40 , align : 'center',sortable : false},
											    {name : 'matStatusName',index : 'matStatusName',width : 40 , align : 'center',sortable : false},  
											    {name : 'app_qty',defval:0,index : 'app_qty',width : 40, align : 'center',sortable : false,editable:true,edittype:"text",editoptions:{size:"2",maxlength:"10"},
											    editrules:{required:true},formatter: nullForZero},
											    {name : 'stockQty',index : 'stockQty',width : 40 , align : 'center',sortable : false,formatter: nullForZero}, 
											    {name : 'bookingQty',index : 'bookingQty',width : 40 , align : 'center',sortable : false,formatter: nullForZero} ,
											    {name : 'checkResult',index : 'checkResult',width : 40 , align : 'center',sortable : false},
											    {name : 'mat_status',index : 'mat_status',width : 40 , align : 'center',sortable : false,hidden:true},
											    {name : 'checkStatus',index : 'checkStatus',width : 40 , align : 'center',sortable : false,hidden:true},
											    {name : 'ctrlType',index : 'ctrlType',width : 40 , align : 'center',sortable : false,hidden:true}
											   ],
											multiselect : true,
											caption : "申請明細",
											rownumbers : true,
											gridComplete : function() {
												var ids = jQuery("#dtlgrid").jqGrid('getDataIDs');
												for (var i = 0; i < ids.length; i++) 
												{
												    var rowId = ids[i];
												    var rowData = jQuery('#dtlgrid').jqGrid ('getRowData', rowId);
													if(rowData.checkStatus=="0"){
														$("#applyBtn").prop("disabled","disabled");
														$("#saveBtn").prop("disabled","disabled");
														break;
													}else{
														$("#applyBtn").prop("disabled",false);
														$("#saveBtn").prop("disabled",false);
													}
												}
											},
											error : function(data){
												alert("資料有誤或查無資料");
										}
									});//end grid
								$(window).resize(function() {
									$(window).unbind("onresize");
									$("#dtlgrid,.grid").setGridWidth($("#jQgrid").width() - 10);
									$(window).bind("onresize", this);
							}).trigger('resize');
						});	
						//申請
						$("#applyBtn").on('click',function(e){
							var saveMode = $("#saveMode").val();
							var outWareHouse = $("#addwareHouseOutSelect").find("option:selected").prop("value");
							var inWareHouse = $("#addwareHouseInSelect").find("option:selected").prop("value");
							var deptNeed = $("#adddeptNeedSelect").find("option:selected").prop("value");
							var needDate = $("#addneedDate").val();
							var deptApply = $("#adddeptApplySelect").find("option:selected").prop("value");
							var personnel = $("#addpersonnelSelect").find("option:selected").prop("value");
							var memo = $("#memo").prop("value");
							var genTrNo = "";
							var addDtlArray =[];//儲存陣列
							var addMasArray = [];
							if(inWareHouse=="--請選擇--" || inWareHouse==""){
								alert("請選擇調入倉庫");
								return false;
							}
							if(deptNeed=="--請選擇--" || deptNeed==""){
								alert("請選擇需求部門");
								return false;
							}
							if(needDate=="" || needDate==""){
								alert("請輸入需求日期");
								return false;
							}
							if(deptApply=="--請選擇--" || deptApply==""){
								alert("請選擇申請部門");
								return false;
							}
							if(saveMode=="add" && (personnel=="--請選擇--" || personnel=="")){
								alert("請選擇申請人，以便產生調撥單號");
								return false;
							}	
							if(inWareHouse==outWareHouse){
								alert("調出調入倉庫不可相同");
								return false;
							}
							//$("#applyBtn").prop("disabled","disabled");
							var data = {
								trNo : $("#tr_no").val(),
								personnel : personnel
							};
							if(saveMode=="add"){
							$.ajax({			
										url : CONTEXT_URL+"/inv/inv007/genTrNo/",		
										data:data,
										type : "POST",
										dataType : 'json',
										async: false,
										success : function(data) {
											if(data.success){
												genTrNo=data.msg
											}
										}
									});	
							}else{
								genTrNo=$("#tr_no").val();
							}
							var crTime=getCrTime();
							//申請明細檔資料
							var selr = jQuery('#dtlgrid').jqGrid('getGridParam','selarrrow');
							if(!selr.length==0) {
								for(var i=0;i<selr.length;i++){
										var myrow = jQuery('#dtlgrid').jqGrid('getRowData',selr[i]);
										var checkResult=myrow.checkResult;
										if(checkResult=="庫存已有其他申請單Booking"){
											alert("庫存已有其他申請單Booking");
											break;
										}	
									}
								for(var i=0;i<selr.length;i++){
									var myrow = jQuery('#dtlgrid').jqGrid('getRowData',selr[i]);
									var mat_no=myrow.mat_no;
									var mat_status=myrow.mat_status;
									var matName=myrow.matName;
									var appQty=myrow.app_qty;
									var ctrlType=myrow.ctrlType;
									var TbInvTrDtlDTO = {
										"tr_no" : genTrNo,
										"mat_no" : mat_no,
										"mat_status" : mat_status,
										"app_qty" : appQty,
										"ctrlType" : ctrlType
										};
									addDtlArray.push(TbInvTrDtlDTO);
									}
									var TbInvTr = {
										"tr_no" : genTrNo,
										"tr_out_wh_id" : outWareHouse,
										"tr_in_wh_id" : inWareHouse,
										"need_dept_id" : deptNeed,
										"need_date" : needDate,
										"app_dept_id" : deptApply,
										"app_user" : personnel,
										"status" : '2',
										"memo" :	memo
									};
									addMasArray.push(TbInvTr);
									//送出
									var data ={
										masData : JSON.stringify(addMasArray),
										dtlArray : JSON.stringify(addDtlArray),
										saveMode : saveMode,
										trNo : genTrNo,
										clickType : "apply"
									}
									$.ajax({			
										url : CONTEXT_URL+"/inv/inv007/applyData/",		
										data:data,
										type : "POST",
										dataType : 'json',
										success : function(data) {
											if(data.success){
												alert("申請成功，調撥單號為："+data.msg);
												parent.$.fancybox.close();	
											}else{
												alert("錯誤："+data.msg);
											}
										},
										error : function(data){
												alert(data.msg);
										}
									});	
								}else{
									alert("申請前，請先勾選明細資料！");
									return false;
								}	
						});
							
						//刪除明細資料
						$("#delDtlBtn").on('click',function(e){
							var trNo = $("#tr_no").val();
						var selr = jQuery('#dtlgrid').jqGrid('getGridParam','selarrrow');
							if(!selr.length==0) {
								if($("#saveMode").val() == "editAdd"){
									for(var i=0;i<selr.length;i++){
										var myrow = jQuery('#dtlgrid').jqGrid('getRowData',selr[i]);	
										var matNo=myrow.mat_no;	
										var matStatus=myrow.mat_status;
										//修改模式 才要刪除資料庫資料
											var data = {
													trNo : trNo,
													matNo : matNo,
													matStatus : matStatus
												};
												$.ajax({			
													url : CONTEXT_URL+"/inv/inv007/delDtlData/",		
													data:data,
													type : "POST",
													dataType : 'json',
													success : function(data) {
														if(data.success){
															alert("刪除成功!!");
															$("#dtlgrid").trigger("reloadGrid");
														}
													},
													error : function(data){
															alert("刪除失敗!!");
													}
												});
											}	
										}else{
											for (var i =  selr.length-1; i>=0; i --)  {
								                    $('#dtlgrid').jqGrid("delRowData", selr[0]);
								                }
								                var ids = jQuery("#dtlgrid").jqGrid('getDataIDs');
												if(ids.length==0){
													$("#applyBtn").prop("disabled","disabled");
													$("#saveBtn").prop("disabled","disabled");
												} 
										}
									}else{
										alert("刪除前，請先勾選明細資料！");
										return false;
									}
						});
						//儲存按鈕 檢核完成後寫入資料至ＤＢ
						$("#saveBtn").on('click',function(e){
							var saveMode = $("#saveMode").val();
							var outWareHouse = $("#addwareHouseOutSelect").find("option:selected").prop("value");
							var inWareHouse = $("#addwareHouseInSelect").find("option:selected").prop("value");
							var deptNeed = $("#adddeptNeedSelect").find("option:selected").prop("value");
							var needDate = $("#addneedDate").val();
							var deptApply = $("#adddeptApplySelect").find("option:selected").prop("value");
							var personnel = $("#addpersonnelSelect").find("option:selected").prop("value");
							var memo = $("#memo").prop("value");
							var addDtlArray =[];//儲存陣列
							var addMasArray = [];
							if(inWareHouse=="--請選擇--" || inWareHouse==""){
								alert("請選擇調入倉庫");
								return false;
							}
							if(deptNeed=="--請選擇--" || deptNeed==""){
								alert("請選擇需求單位");
								return false;
							}
							if(needDate==""){
								alert("請輸入需求日期");
								return false;
							}
							if(deptApply=="--請選擇--" || deptApply==""){
								alert("請選擇申請單位");
								return false;
							}
							if(saveMode=="add" && personnel=="" || personnel=="--請選擇--"){
								alert("請選擇申請人，以便產生調撥單號");
								return false;
							}	
							if(inWareHouse==outWareHouse){
								alert("調出調入倉庫不可相同");
								return false;
							}
							//$("#saveBtn").prop("disabled","disabled");
							var memo = $("#memo").val();
							var data = {
								trNo : $("#tr_no").val(),
								personnel : personnel
							};
							var genTrNo = "";
							if(saveMode=="add"){
							$.ajax({			
										url : CONTEXT_URL+"/inv/inv007/genTrNo/",		
										data:data,
										type : "POST",
										dataType : 'json',
										async: false,
										success : function(data) {
											if(data.success){
												genTrNo=data.msg
											}
										}
									});	
							}else{
								genTrNo=$("#tr_no").val();
							}
							var crTime=getCrTime();
							//儲存明細檔資料				
							var selr = jQuery('#dtlgrid').jqGrid('getGridParam','selarrrow');
							if(!selr.length==0) {
								for(var i=0;i<selr.length;i++){
									var myrow = jQuery('#dtlgrid').jqGrid('getRowData',selr[i]);
									var checkResult=myrow.checkResult;
									if(checkResult=="庫存已有其他申請單Booking"){
										alert("庫存已有其他申請單Booking");
										break;
									}	
								}
								for(var i=0;i<selr.length;i++){
									var myrow = jQuery('#dtlgrid').jqGrid('getRowData',selr[i]);
									var mat_no=myrow.mat_no;
									var mat_status=myrow.mat_status;
									var matName=myrow.matName;
									var appQty=myrow.app_qty;
									var ctrlType=myrow.ctrlType;
									var TbInvTrDtlDTO = {
										"tr_no" : genTrNo,
										"mat_no" : mat_no,
										"mat_status" : mat_status,
										"app_qty" : appQty,
										"ctrlType" : ctrlType
										};
									addDtlArray.push(TbInvTrDtlDTO);
									}
									var TbInvTr = {
										"tr_no" : genTrNo,
										"tr_out_wh_id" : outWareHouse,
										"tr_in_wh_id" : inWareHouse,
										"need_dept_id" : deptNeed,
										"need_date" : needDate,
										"app_dept_id" : deptApply,
										"app_user" : personnel,
										"status" : '1',
										"memo" :	memo
									};
									addMasArray.push(TbInvTr);
									//送出
									var data ={
										masData : JSON.stringify(addMasArray),
										dtlArray : JSON.stringify(addDtlArray),
										saveMode : saveMode,
										trNo : genTrNo,
										clickType : 'save'
									}
									$.ajax({			
										url : CONTEXT_URL+"/inv/inv007/applyData/",		
										data:data,
										type : "POST",
										dataType : 'json',
										success : function(data) {
											if(data.success){
												alert("儲存成功，調撥單號為："+data.msg);
												parent.$.fancybox.close();	
											}
										},
										error : function(data){
												alert(data.msg);
										}
									});	
								}else{
									alert("儲存前，請先勾選明細資料！");
									return false;
								}	
						});
						//單筆申請資料檢核並且寫入GRID			
						$("#plusBtn").on('click',function(e){
								var  status="1";//預設為草稿
								var  catgCode1 = $("#catg1Select").find("option:selected").prop("value");
								var  catgCode2 = $("#catg2Select").find("option:selected").prop("value");
								var  catgCode3 = $("#catg3Select").find("option:selected").prop("value");
								var  matNoSelect = $("#matNoSelect").prop("val");
								var  matNameSelect = $("#matNoSelect").val();
								var  matStatus = $("#matStatus").val();
								var  matStatusName = $("#matStatus").find("option:selected").text();
								var  appQty = $("#appQty").val();
								var  invQty = $("#invQty").val();
								var  gridId=$("#dtlgrid").jqGrid('getDataIDs');
								var  wareHouseOutSelect = $("#addwareHouseOutSelect").find("option:selected").prop("value");
								$.log("invQty:"+invQty+" appQty:"+appQty);
								if(appQty=="" || appQty=='' || appQty=='0'){
									alert("請輸入調撥數量並至少大於一筆");
									return false;
								}
								if(parseInt(invQty)<parseInt(appQty)){
									alert("調撥數量大於庫存數請重新輸入");
									return false;
								}
								
								if(!isAllNumber(appQty)){			
									alert("申請調撥數需為正數，不得為負數或其他文字");
									return false;
								}
								if(wareHouseOutSelect == "" || wareHouseOutSelect == ''){
									alert("請選擇調出倉庫");
									return false;
								}
								if(catgCode1 == "" || catgCode1 == ''){
									alert("請選擇物料類別");
									return false;
								}
								if(catgCode2 == "" || catgCode2 == ''){
									alert("請選擇中分類");
									return false;
								}
								if(catgCode3 == "" || catgCode3 == ''){
									alert("請選擇中分類");
									return false;
								}
								if(matNoSelect == "" || matNoSelect == ''){
									alert("請選擇料號");
									return false;
								}
								if(matStatus == "" || matStatus == ''){
									alert("請選擇物料狀態");
									return false;
								}
								
								var ids = jQuery("#dtlgrid").jqGrid('getDataIDs');
								for (var i = 0; i < ids.length; i++) 
								{
								    var rowId = ids[i];
								    var rowData = jQuery('#dtlgrid').jqGrid ('getRowData', rowId);
									if(rowData.mat_no==matNoSelect && rowData.mat_status==matStatus){
										alert("料號:"+rowData.mat_no+",物料狀態:"+rowData.matStatusName+" 已經存在,若要變更數量請刪除明細後再新增");
										return false;
										break;
									}
								}
								
								var data ={
									matNo : matNoSelect,
									matStatus : matStatus,
									wareHouseOut : wareHouseOutSelect
								};
								$.ajax({			
									url : CONTEXT_URL+"/inv/inv007/addRowCheck/",		
									data:data,
									type : "POST",
									dataType : 'json',
									success : function(data) {
											if(data.rows.length > 0){
											catg1BackDef();
											catg2BackDef();
											catg3BackDef();
											matBackDef();
											matStatusBackDef();
											$("#invQty").val("");
												for(i=0; i<data.rows.length; i++){	
												var stockQty = "";//庫存數
												var bookingQty = "";//Booking數
												var checkResult = "";//檢核結果中文
												var checkStatus = "";//檢核結果 0:不通過 1:通過
												
													if(matStatus=="1"){
															stockQty=data.rows[i].std_qty;
															bookingQty=data.rows[i].booking_qty;
														}else if(matStatus=="2"){
															stockQty=data.rows[i].wrd_qty;
															bookingQty="0";
														}else{
															stockQty=data.rows[i].wsp_qty;
															bookingQty="0";
													}
													if(appQty>stockQty){
															checkStatus=0;
															checkResult="調撥數大於庫存數，無法申請";	
														}else{
															checkStatus=1;
															checkResult="通過";
													}
													if(matStatus=="1" && appQty>(stockQty-bookingQty) && stockQty>=appQty){
															checkStatus=1;
															checkResult="庫存已有其他申請單Booking";
													}
													if(stockQty=="" || stockQty==''){
															checkStatus=0;
															checkResult="查無庫存";
													}
													$("#dtlgrid").jqGrid("addRowData", gridId+1 ,{'mat_no':matNoSelect, 'matName': matNameSelect
													,'mat_status':matStatus,'matStatusName':matStatusName,'app_qty':appQty,
													'stockQty':stockQty,'checkResult':checkResult,'bookingQty':bookingQty,'checkStatus':checkStatus,'ctrlType':data.rows[i].ctrlType
													}, "first" ); 
												}		
												var ids = jQuery("#dtlgrid").jqGrid('getDataIDs');
												for (var i = 0; i < ids.length; i++){
												    var rowId = ids[i];
												    var rowData = jQuery('#dtlgrid').jqGrid ('getRowData', rowId);
													if(rowData.checkStatus=="0"){
														$("#applyBtn").prop("disabled","disabled");
														$("#saveBtn").prop("disabled","disabled");
														break;
													}else{
														$("#applyBtn").prop("disabled",false);
														$("#saveBtn").prop("disabled",false);
													}
												}			 
											}else{//data.rows.length==0
												var checkResult = "查無庫存";//檢核結果中文
												var checkStatus = "0";//檢核結果 0:不通過 1:通過
												$("#applyBtn").prop("disabled","disabled");
												$("#saveBtn").prop("disabled","disabled");
													$("#dtlgrid").jqGrid("addRowData", gridId+1 ,{'mat_no':matNoSelect, 'matName': matNameSelect
													,'mat_status':matStatus,'matStatusName':matStatusName,'app_qty':appQty,
													'stockQty':'','checkResult':checkResult,'bookingQty':'','checkStatus':checkStatus,'ctrlType':''
													}, "first" )
											}
										}
								});	
							});
			$("#catg1Select").change(function(){
				var  catgCode1 = $("#catg1Select").find("option:selected").prop("value");
				var  catgCode2 = $("#catg2Select").find("option:selected").prop("value");
				catg2BackDef();
				catg3BackDef();
				matBackDef();
				var data = {
					catgCode1 : catgCode1,
					catgCode2 : catgCode2,
				};
				if (catgCode1 != "" || catgCode1 != '' ){
						$.ajax({			
							url : CONTEXT_URL+"/ajax/inv/public/categoryCode2/",		
							data:data,
							type : "POST",
							dataType : 'json',
							success : function(data) {
								if(data.success){
									if(data.rows.length > 0){
									$("#catg2Select option").remove();
									$("#catg2Select").append($("<option></option>").attr("value", "").text("--請選擇--"));
										for(i=0; i<data.rows.length; i++){		
											$("#catg2Select").append("<option value="+data.rows[i].catg2_code+">"+data.rows[i].catg2_name+"</option>");
										}
										$("#catg2Select").prop("disabled",false);							 
									}
								}
							}
						});
						$.ajax({			
							url : CONTEXT_URL+"/ajax/inv/public/categoryCode3/",		
							data:data,
							type : "POST",
							dataType : 'json',
							success : function(data) {
								if(data.success){
									if(data.rows.length > 0){
									$("#catg3Select option").remove();
									$("#catg3Select").append($("<option></option>").attr("value", "").text("--請選擇--"));
										for(i=0; i<data.rows.length; i++){		
											$("#catg3Select").append("<option value="+data.rows[i].catg3_code+">"+data.rows[i].catg3_name+"</option>");
										}						 
									}
								}
							}
						});	
					}else{
						$("#catg2Select").prop("disabled","disabled").prop("value","").prop("text","--請選擇--");
						$("#catg3Select").prop("disabled","disabled").prop("value","").prop("text","--請選擇--");
						//$("#matNoSelect").prop("disabled","disabled").prop("value","").prop("text","--請選擇--");
						$("#matNoSelect").prop("value","").prop("val","");												
					}
				});
				
				$("#catg2Select").change(function(){
				var  catgCode1 = $("#catg1Select").find("option:selected").prop("value");
				var  catgCode2 = $("#catg2Select").find("option:selected").prop("value");
				var  catgCode3 = $("#catg3Select").find("option:selected").prop("value");
				catg3BackDef();
				matBackDef();
				var data = {
					catgCode1 : catgCode1,
					catgCode2 : catgCode2,
					catgCode3 : catgCode3
				};
				var data2 = {
					catgCode1 : catgCode1
				};
				if(catgCode2 != "" || catgCode2 != ''){
						$.ajax({			
							url : CONTEXT_URL+"/ajax/inv/public/categoryCode3/",		
							data:data,
							type : "POST",
							dataType : 'json',
							success : function(data) {
								if(data.success){
									if(data.rows.length > 0){
									$("#catg3Select option").remove();
									$("#catg3Select").append($("<option></option>").attr("value", "").text("--請選擇--"));
										for(i=0; i<data.rows.length; i++){		
											$("#catg3Select").append("<option value="+data.rows[i].catg3_code+">"+data.rows[i].catg3_name+"</option>");
										}
										$("#catg3Select").prop("disabled",false);							 
									}
								}
							}
						});				
						$.ajax({			
							url : CONTEXT_URL+"/ajax/inv/public/materialByCateCode/",		
							data:data,
							type : "POST",
							dataType : 'json',
							success : function(data) {
								if(data.success){
									if(data.rows.length > 0){
									// $("#matNoSelect option").remove();
									//$("#matNoSelect").append($("<option></option>").attr("value", "").text("--請選擇--"));
										for(i=0; i<data.rows.length; i++){	
											//$("#matNoSelect").append("<option value="+data.rows[i].mat_no+">"+data.rows[i].mat_name+"</option>");
											//$("#matNoSelect").prop("val",data.rows[i].mat_no).prop("value",data.rows[i].mat_name);
										}
										//$("#matNoSelect").prop("disabled",false);							 
									}
								}
							}
						});	
						}else{
						$("#catg3Select").prop("disabled","disabled").prop("value","").prop("text","--請選擇--");
						//$("#matNoSelect").prop("disabled","disabled").prop("value","").prop("text","--請選擇--");
						$("#matNoSelect").prop("val","").prop("value","");
						catg3BackDef();
						matBackDef();
					}		
				});
				$("#catg3Select").change(function(){
				var  catgCode1 = $("#catg1Select").find("option:selected").prop("value");
				var  catgCode2 = $("#catg2Select").find("option:selected").prop("value");
				var  catgCode3 = $("#catg3Select").find("option:selected").prop("value");
				matBackDef();
				matStatusBackDef();
				var data = {
					catgCode1 : catgCode1,
					catgCode2 : catgCode2,
					catgCode3 : catgCode3
				};

				if((catgCode2 != "" || catgCode2 != '') && (catgCode3 != "" || catgCode3 != '')){
						$.ajax({			
							url : CONTEXT_URL+"/ajax/inv/public/materialByCateCode/",		
							data:data,
							type : "POST",
							dataType : 'json',
							success : function(data) {
								if(data.success){
									if(data.rows.length > 0){
									//$("#matNoSelect option").remove();
									//$("#matNoSelect").append($("<option></option>").attr("value", "").text("--請選擇--"));
										for(i=0; i<data.rows.length; i++){
											$("#matNoSelect").prop("val",data.rows[i].mat_no).prop("value",data.rows[i].mat_name);		
											//$("#matNoSelect").append("<option value="+data.rows[i].mat_no+">"+data.rows[i].mat_name+"</option>");
										}
										//$("#matNoSelect").prop("disabled",false);							 
									}
								}
							}
						});
					}else{
						//$("#matNoSelect").prop("disabled","disabled").prop("value","").prop("text","--請選擇--");
						$("#matNoSelect").prop("val","").prop("value","");
						matBackDef();
						matStatusBackDef();
					}		
				});		
				//申請部門連動申請人
				$("#adddeptApplySelect").change(onDeptSelectChange);
				//變更調出倉 若已經有新增則清除頁面資料
				$("#addwareHouseOutSelect").change(function(){
					$("#invQty").val("");
					//$("#plusBtn").prop("disabled","disabled");
						var  gridId=$("#dtlgrid").jqGrid('getDataIDs');
						//已有資料
						if (gridId[0] != undefined){
							if(confirm("申請明細已有資料，是否仍要變調出倉"))
							{
							    reset();
							}
						}
				});
				//物料狀態聯動帶出庫存數
				$("#matStatus").change(onMatStatusChange);
				//區域聯動調出入倉，需求/申請單位
				$("#adddomainSelect").change(onaddDomainSelectChange);
//=============================================
			//撈庫存數
			function onMatStatusChange(){
				if ($("#matStatus").find("option:selected").val() == "") {
					return false;
				}
				var data = {
					matStatus : $("#matStatus").find("option:selected").prop(
							"value"),
					whId : $("#addwareHouseOutSelect").find("option:selected").prop("value"),
					//matNo : $("#matNoSelect").find("option:selected").val()
					matNo : $("#matNoSelect").prop("val")		
				};
				$.ajax({
					url : CONTEXT_URL + "/inv/inv007/getInvQty/",
					data : data,
					type : "POST",
					dataType : 'json',
					success : function(data) {
						if (data.success) {
							$("#invQty").val(data.msg);
							/* if($("#appQty").val()<data.msg){
								$("#plusBtn").prop("disabled",false);
							}else{
								$("#plusBtn").prop("disabled","disabled");
							} */
						}else{
							$("#invQty").val("");
							//$("#plusBtn").prop("disabled","disabled");
						}
					}
				});
			}
			
	/* $("#appQty").on('blur', function() {
		if ($("#appQty").val() < $("#invQty").val()) {
			$("#plusBtn").prop("disabled", false);
		} else {
			$("#plusBtn").prop("disabled", "disabled");
		}
	}) */
	
	//區域聯動調出入倉，需求/申請單位
	function onaddDomainSelectChange() {
		resetByAddDomain();
		if ($("#adddomainSelect").find("option:selected").val() == "") {
			return false;
		}
		var data = {
			id : $("#adddomainSelect").find("option:selected").prop("value")
		};
		$.ajax({
			url : CONTEXT_URL + "/ajax/inv/public/getWareHouseByDomain/",
			data : data,
			type : "POST",
			dataType : 'json',
			success : function(data) {
				if (data.success) {
					if (data.rows.length > 0) {
						for (i = 0; i < data.rows.length; i++) {
							$("#addwareHouseOutSelect").append(
									"<option value="+data.rows[i].wh_id+">"
											+ data.rows[i].wh_name
											+ "</option>");
							if ("總倉" != data.rows[i].wh_name) {
								$("#addwareHouseInSelect").append(
										"<option value="+data.rows[i].wh_id+">"
												+ data.rows[i].wh_name
												+ "</option>");
							}
						}
					}
				}
			}
		});
		var data2 = {
			domain : $("#adddomainSelect").find("option:selected")
					.prop("value")
		};
		$.ajax({
			url : CONTEXT_URL + "/ajax/inv/public/deptByDomain/",
			data : data2,
			type : "POST",
			dataType : 'json',
			success : function(data) {
				if (data.success) {
					if (data.rows.length > 0) {
						for (i = 0; i < data.rows.length; i++) {
							$("#adddeptNeedSelect").append(
									"<option value="+data.rows[i].dept_ID+">"
											+ data.rows[i].dept_NAME
											+ "</option>");
							$("#adddeptApplySelect").append(
									"<option value="+data.rows[i].dept_ID+">"
											+ data.rows[i].dept_NAME
											+ "</option>");
						}
					}
				}
			}
		});
	}
	function resetByAddDomain() {
		$("#addwareHouseOutSelect").html("");
		$("<option value=''>--請選擇--</option>").appendTo(
				"#addwareHouseOutSelect");
		$("#addwareHouseOutSelect").select2();
		$("#addwareHouseInSelect").html("");
		$("<option value=''>--請選擇--</option>")
				.appendTo("#addwareHouseInSelect");
		$("#addwareHouseInSelect").select2();
		$("#adddeptNeedSelect").html("");
		$("<option value=''>--請選擇--</option>").appendTo("#adddeptNeedSelect");
		$("#adddeptNeedSelect").select2();
		$("#adddeptApplySelect").html("");
		$("<option value=''>--請選擇--</option>").appendTo("#adddeptApplySelect");
		$("#adddeptApplySelect").select2();
	}
	function getCrTime() {
		var crTime = "";
		$.ajax({
			url : CONTEXT_URL + "/ajax/inv/public/getToday/",
			data : crTime,
			type : "POST",
			dataType : 'json',
			async : false,
			success : function(data) {
				if (data.success) {
					crTime = data.msg
				}
			}
		});
		return crTime;
	}
	function catg1BackDef() {
		$("#catg1Select").select2("val","");
	}
	function catg2BackDef() {
		$("#catg2Select").html("");
		$("<option value=''>--請選擇--</option>").appendTo("#catg2Select");
		$("#catg2Select").select2();
	}
	function catg3BackDef() {
		$("#catg3Select").html("");
		$("<option value=''>--請選擇--</option>").appendTo("#catg3Select");
		$("#catg3Select").select2();
	}
	function matBackDef() {
		/* $("#matNoSelect").html("");
		$("<option value=''>--請選擇--</option>").appendTo("#matNoSelect");
		$("#matNoSelect").select2(); */
		$("#matNoSelect").prop("val","").prop("value","");
	}
	function matStatusBackDef() {
		$("#matStatus").select2("val", "");
	}
	// 申請部門連動申請人
	function onDeptSelectChange() {
		$("#addpersonnelSelect").html("");
		$("<option value=''>--請選擇--</option>").appendTo("#addpersonnelSelect");
		$("#addpersonnelSelect").select2();
		if ($("#adddeptApplySelect").val() == "") {
			return false;
		}
		var data = {
			dept : $("#adddeptApplySelect").find("option:selected").prop(
					"value")
		};
		$.ajax({
			url : CONTEXT_URL + "/ajax/inv/public/personnelDept/",
			data : data,
			type : "POST",
			dataType : 'json',
			success : function(data) {
				if (data.success) {
					if (data.rows.length > 0) {
						for (i = 0; i < data.rows.length; i++) {
							$("#addpersonnelSelect")
									.append(
											"<option value="+data.rows[i].psn_NO+">"
													+ data.rows[i].chn_NM
													+ "</option>");
						}
					}
				}
			}
		});
	}
	//重置
	function reset() {
		$("#addEdit").trigger('reset');
		$("#addneedDate").prop("value", "");
		$("#appQty").prop("value", "1");
		$("#memo").prop("value", "");
		$("#catg1Select").select2("val", "");
		$("#catg2Select").select2("val", "");
		$("#catg3Select").select2("val", "");
		//$("#matNoSelect").select2("val", "");
		$("#matNoSelect").prop("val","").prop("value","");
		$("#matStatus").select2("val", "");
		$("#addwareHouseOutSelect").select2("val", "");
		$("#addwareHouseInSelect").select2("val", "");
		$("#adddeptNeedSelect").select2("val", "");
		$("#adddeptApplySelect").select2("val", "");
		$("#addpersonnelSelect").select2("val", "");
		$("#catg2Select").prop("disabled", "disabled").prop("value", "").prop(
				"text", "--請選擇--");
		$("#catg3Select").prop("disabled", "disabled").prop("value", "").prop(
				"text", "--請選擇--");
		//$("#matNoSelect").prop("disabled", "disabled").prop("value", "").prop("text", "--請選擇--");
		$("#addpersonnelSelect").html("");
		$("<option value=''>--請選擇--</option>").appendTo("#addpersonnelSelect");
		$("#addpersonnelSelect").select2();
		$("#invQty").val("");
		$("#dtlgrid").jqGrid().clearGridData();
	}
	//格式化grid數字
	function nullForZero(cellvalue, options, rowObject) {
		var returnValue = cellvalue;
		if (cellvalue == "" || cellvalue == "undefined" || cellvalue == null) {
			returnValue = "0";
		}
		return returnValue;
	}

	// 開啟下拉式選單搜尋文字功能
	function selectDefault() {
		$("#addEdit").find("select").select2();
		$("#addwareHouseOutSelect").select2();
		$("#addwareHouseInSelect").select2();
		$("#adddeptNeedSelect").select2();
		$("#adddeptApplySelect").select2();
		$("#addpersonnelSelect").select2();
		$("#catg1Select").select2();
		$("#catg2Select").select2();
		$("#catg3Select").select2();
		//$("#matNoSelect").select2();
		$("#matStatus").select2();
		$("#adddomainSelect").select2();
	}
</script>
</head>
<body>
	<div class="row">
		<div id="breadcrumb" class="col-md-12">
			<ol class="breadcrumb">
				<div class="col-sm-12" id="btnDiv">
					<button class="btn btn-primary btn-label-left" type="button"
						id="saveBtn" disabled="disabled">
						<span><i class="fa fa-save"></i></span> 儲存
					</button>
					<button class="btn btn-primary btn-label-left" type="button"
						id="applyBtn" disabled="disabled">申請</button>
					<button class="btn btn-primary btn-label-left" type="button"
						id="delDtlBtn">刪除明細</button>
					<button class="btn btn-primary btn-label-left" type="button"
						id="resetBtn" onclick="reset();">重置</button>
				</div>
			</ol>
		</div>
	</div>
	<div class="box-header">
		<div class="box-name">
			<i class="fa fa-edit"></i> <span>調撥申請作業-<c:choose>
					<c:when test="${saveMode == 'add'}">  
				                          	新增
									   </c:when>
					<c:otherwise>  
											修改
									   </c:otherwise>
				</c:choose>
			</span>
		</div>
		<div class="box-icons">
			<p class="expand-link"></p>
		</div>
		<div class="no-move"></div>
	</div>

	<div class="row">
		<div class="col-xs-12 col-sm-12">
			<div class=" ui-draggable ui-droppable">
				<div class="box-content">
					<form class="form-horizontal" id="addEdit" />
					<input id="trNo" type='hidden' value="${invTrDscr.tr_no}" /> 
					<input id="saveMode" type='hidden' value="${saveMode}" />
					<div class="form-group">
						<table style="width: 100%">
							<div class="row">
								<tr>
									<td><label class="col-sm-12 control-label">區域：</label></td>
									<td><div class="col-sm-6">
											<select id="adddomainSelect" name="adddomainSelect">
											<option value="" selected>--請選擇--</option>
											<c:forEach items="${domainSelect}" var="domain">
													<c:choose>
														<c:when test="${deptCode eq domain.ID}">
															<option value="${domain.ID}" selected>${domain.NAME}</option>
														</c:when>
														<c:otherwise>
															<option value="${domain.ID}">${domain.NAME}</option>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</select></td>
										</div></td>
								</tr>
								<tr>
									<td><label class="col-sm-12 control-label">調撥單號：</label></td>
									<td><div class="col-sm-6">
											<input id="tr_no" maxlength="20" type="text"
												class="form-control" name="tr_no" disabled="disabled"
												value="${invTrDscr.tr_no}">
										</div></td>
									<td><label class="col-sm-12 control-label">調撥進度： <c:choose>
												<c:when test="${saveMode == 'add'}">  
				                          	草稿
									   </c:when>
												<c:otherwise>  
											${invTrDscr.statusDscr}
									   </c:otherwise>
											</c:choose>
									</label></td>
									<td><div class="col-sm-6"></div></td>
								</tr>
								<tr>
									<td><label class="col-sm-12 control-label"><span class="s">* </span>調出倉庫：</label></td>
									<td><div class="col-sm-6">
											<select id="addwareHouseOutSelect"
												name="addwareHouseOutSelect"   class="populate placeholder">
												<option value="" selected="true">--請選擇--</option>
												<c:forEach items="${wareHouseSelect}" var="wareHouse">
													<c:choose>
														<c:when
															test="${invTrDscr.tr_out_wh_id eq wareHouse.wh_id}">
															<option value="${wareHouse.wh_id}" selected="true">${wareHouse.wh_name}</option>
														</c:when>
														<c:otherwise>
															<option value="${wareHouse.wh_id}">${wareHouse.wh_name}</option>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</select>
										</div></td>
									<td><label class="col-sm-12 control-label"><span class="s">* </span>調入倉庫：</label></td>
									<td>
										<div class="col-sm-6">
											<select id="addwareHouseInSelect" name="addwareHouseInSelect">
												<option value="" selected="true">--請選擇--</option>
												<c:forEach items="${wareHouseSelect}" var="wareHouse">
													<c:choose>
														<c:when test="${invTrDscr.tr_in_wh_id eq wareHouse.wh_id}">
															<option value="${wareHouse.wh_id}" selected="true">${wareHouse.wh_name}</option>
														</c:when>
														<c:otherwise>
															<option value="${wareHouse.wh_id}">${wareHouse.wh_name}</option>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</select>
										</div>
									</td>
								</tr>
								<tr>
									<td><label class="col-sm-12 control-label"><span class="s">* </span>需求單位：</label></td>
									<td>
										<div class="col-sm-6">
											<select id="adddeptNeedSelect" name="adddeptNeedSelect">
												<option value="" selected="true">--請選擇--</option>
												<c:forEach items="${deptSelect}" var="dept">
													<c:choose>
														<c:when test="${invTrDscr.need_dept_id eq dept.DEPT_ID}">
															<option value="${dept.DEPT_ID}" selected="selected">${dept.DEPT_NAME}</option>
														</c:when>
														<c:otherwise>
															<option value="${dept.DEPT_ID}">${dept.DEPT_NAME}</option>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</select>
										</div>
									</td>
									<td><label class="col-sm-12 control-label"><span class="s">* </span>需求日期：</label></td>
									<td><div class="col-sm-6">
											<input id="addneedDate" type="text"
												value="<fmt:formatDate  value="${invTrDscr.need_date}"  pattern="yyyy/MM/dd"  />"
												name="addneedDate" readonly="readonly">
										</div></td>
								</tr>
								<tr>
									<td><label class="col-sm-12 control-label"><span class="s">* </span>申請單位：</label></td>
									<td>
										<div class="col-sm-6">
											<select id="adddeptApplySelect" name="adddeptApplySelect">
												<option value="" selected="true">--請選擇--</option>
												<c:forEach items="${deptSelect}" var="dept">
													<c:choose>
														<c:when test="${invTrDscr.app_dept_id eq dept.DEPT_ID}">
															<option value="${dept.DEPT_ID}" selected="selected">${dept.DEPT_NAME}</option>
														</c:when>
														<c:otherwise>
															<option value="${dept.DEPT_ID}">${dept.DEPT_NAME}</option>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</select>
										</div>
									</td>
									<td><label class="col-sm-12 control-label">申請人：</label></td>
									<td>
										<div class="col-sm-6">
											<select id="addpersonnelSelect" name="addpersonnelSelect">
												<option value="" selected="selected">--請選擇--</option>
												<c:choose>
													<c:when test="${saveMode == 'editAdd'}">
														<option selected="selected" value="${invTrDscr.app_user}">${invTrDscr.appUserDscr}</option>
													</c:when>
												</c:choose>
											</select>
										</div>
									</td>
								</tr>
								<tr>
									<td><label class="col-sm-12 control-label">備註：</label></td>
									<td>
										<div class="col-sm-16">
											<textarea class="form-control" rows="2" id=memo name="memo"
												value="">${invTrDscr.memo}</textarea>
										</div>
									</td>
								</tr>
								<table style="width: 100%">
									<tr>
										<td><label class="col-sm-12 control-label">申請明細：</label></td>
										<td><div id="ajaxSearchResult" class="col-sm-10">
												<!-- 固定id for window.resize start-->
												<div id="jQgrid" align="center">
													<table id="dtlgrid"></table>
													<!-- <div id="dtlpager"></div> -->
												</div>
												<!-- 固定id for window.resize end-->
											</div></td>
									</tr>
								</table>
								<hr>
								<table style="width: 100%">
									<tr>
										<td><label class="col-sm-12 control-label">物料類別：</label></td>
										<td>
											<div class="col-sm-12">
												<select id="catg1Select" name="catg1Select">
													<option value="" selected="true">--請選擇--</option>
													<c:forEach items="${catg1Select}" var="catg1">
														<option value="${catg1.catg1_code}">${catg1.catg1_name}</option>
													</c:forEach>
												</select>
											</div>
										</td>
										<td><label class="col-sm-12 control-label">中分類：</label></td>
										<td>
											<div class="col-sm-12">
												<select id="catg2Select" name="catg2Select"
													disabled="disabled">
													<option value="" selected="true">--請選擇--</option>
												</select>
											</div>
										</td>
										<td><label class="col-sm-12 control-label">小分類：</label></td>
										<td>
											<div class="col-sm-12">
												<select id="catg3Select" name="catg3Select"
													disabled="disabled">
													<option value="" selected="true">--請選擇--</option>
												</select>
											</div>
										</td>
										<td><label class="col-sm-12 control-label">品名：</label></td>
										<td><div class="col-sm-12">
												<%-- <select id="matNoSelect" name="matNoSelect"
													disabled="disabled">
													<option value="" selected="true">--請選擇--</option>
												</select> --%>
												<input id="matNoSelect" maxlength="10" type="text"
													class="form-control" name="matNoSelect" value="" val="" disabled="disabled">
											</div></td>
									</tr>
									<tr>
										<td><label class="col-sm-12 control-label">物料狀態：</label></td>
										<td><div class="col-sm-12">
												<select id="matStatus" name="matStatus">
													<option value="" selected="true">--請選擇--</option>
													<c:forEach items="${matStatus}" var="matStatus">
														<option value="${matStatus.CODE}">${matStatus.NAME}</option>
													</c:forEach>
												</select>
											</div></td>
										<td><label class="col-sm-12 control-label">庫存數：</label></td>
										<td><div class="col-sm-12">
												<input id="invQty" maxlength="10" type="text"
													class="form-control" name="invQty" value="" disabled="disabled">
											</div>	
										<td><label class="col-sm-12 control-label">申請調撥數：</label></td>
										<td><div class="col-sm-12">
												<input id="appQty" maxlength="10" type="text"
													class="form-control" name="appQty" value="1">
											</div>
										</td>
										<td><div class="col-sm-12">	
											<button class="btn btn-primary btn-label-left" type="button"
												id="plusBtn">+</button></div></td>
									</tr>
								</table>
							</div>
							</div>

							</div>
							</div>
							</div>
						</table>
					</div>
					</form>
				</div>
			</div>
</body>
</html>