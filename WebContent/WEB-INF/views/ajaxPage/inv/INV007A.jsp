<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>調撥批次申請作業</title>
<script
	src="<s:url value="/resources/plugins/tinymce/tinymce.min.js" />"></script>
<script
	src="<s:url value="/resources/plugins/tinymce/jquery.tinymce.min.js" />"></script>
	<script	src="<s:url value="/resources/js/validate.js" />"></script>
<script type="text/javascript">
	$(document).ready(function() {
		LoadSelect2Script("<s:url value="/resources/plugins/select2/select2.min.js" />", selectDefault);
	});	
						$('#addneedDate').datepicker({
							dateFormat : "yy/mm/dd",
							changeYear : true,
							changeMonth : true,
							showButtonPanel : true
						});
						
						$("#dtlgrid").jqGrid({
											datatype : "local",
											url : CONTEXT_URL + "/inv/inv007/readBatchApply",
											pager : '#dtlpager',
											autowidth:'true',
											colNames : [  '料號', '品名', '物料狀態', '申請調撥數','目前庫存數','Booking數','檢核結果','','','' ],
											colModel : [ 
												{name : 'mat_no',index : 'mat_no',width : 40 , align : 'center',sortable : false},
											    {name : 'matName',index : 'matName',width : 40 , align : 'center',sortable : false},
											    {name : 'mat_status_name',index : 'mat_status_name',width : 40 , align : 'center',sortable : false},  
											    {name : 'app_qty',index : 'app_qty',width : 40, align : 'center',sortable : false,editable:true,edittype:"text",editoptions:{size:"2",maxlength:"10"},
											    editrules:{required:true},formatter: nullForZero},
											    {name : 'stockQty',index : 'stockQty',width : 40 , align : 'center',sortable : false,formatter: nullForZero}, 
											    {name : 'bookingQty',index : 'bookingQty',width : 40 , align : 'center',sortable : false,formatter: nullForZero} ,
											    {name : 'checkResult',index : 'checkResult',width : 40 , align : 'center',sortable : false,formatter: nullForZero},
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
														$("#applyBtn").prop("disabled",true);
														$("#saveBtn").prop("disabled",true);
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
						//下載物料明細
						 $("#downloadBtn").on('click',function(e){				
							var addwareHouseOut = $("#addwareHouseOutSelect").val();
							if(addwareHouseOut == "" || addwareHouseOut == ''){
								alert("請選擇調出倉庫");
								return false;
							};
							window.location.href = CONTEXT_URL + "/inv/inv007/genBatchApplyExcel?wareHouseOut="+addwareHouseOut;
						}); 
						//儲存按鈕 檢核完成後寫入資料至ＤＢ
						$("#saveBtn").on('click',function(e){
							var saveMode = "add";
							var outWareHouse = $("#addwareHouseOutSelect").find("option:selected").prop("value");
							var inWareHouse = $("#addwareHouseInSelect").find("option:selected").prop("value");
							var deptNeed = $("#adddeptNeedSelect").find("option:selected").prop("value");
							var needDate = $("#addneedDate").val();
							var deptApply = $("#adddeptApplySelect").find("option:selected").prop("value");
							var personnel = $("#addpersonnelSelect").find("option:selected").prop("value");
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
							if(personnel == "" || personnel == ''){
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
							var genTrNo="";
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
							var crTime=getCrTime();
							//儲存明細檔資料				
							var selr = jQuery('#dtlgrid').jqGrid('getGridParam','selarrrow');
							if(selr.length) {
								for(var i=0;i<selr.length;i++){
									var myrow = jQuery('#dtlgrid').jqGrid('getRowData',selr[i]);
									var checkResult=myrow.checkResult;
									if(checkResult=="庫存已有其他申請單Booking"){
										alert("庫存已有其他申請單Booking");
										break;
									}	
									if(myrow.app_qty=="0"){
										alert("料號:"+myrow.mat_no+",物料狀態:"+myrow.mat_status_name+" 申請調撥數為0,無法新增,請取消勾選或是重新匯入!!");
										return false;
									}
								}
								for(var i=0;i<selr.length;i++){
									var myrow = jQuery('#dtlgrid').jqGrid('getRowData',selr[i]);
									var mat_no=myrow.mat_no;
									var mat_status=myrow.mat_status;
									var matName=myrow.matName;
									var appQty=myrow.app_qty;
									var checkResult=myrow.checkResult;
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
						//刪除
						//刪除明細資料
						$("#delDtlBtn").on('click',function(e){
						var selr = jQuery('#dtlgrid').jqGrid('getGridParam','selarrrow');
							if(!selr.length==0) {
								for (var i =  selr.length-1; i>=0; i --)  {
					                    $('#dtlgrid').jqGrid("delRowData", selr[0]);
					                }
					                var ids = jQuery("#dtlgrid").jqGrid('getDataIDs');
									if(ids.length==0){
										$("#applyBtn").prop("disabled","disabled");
										$("#saveBtn").prop("disabled","disabled");
									} 
								}else{
									alert("刪除前，請先勾選明細資料！");
									return false;
								}
						});
						
						//申請
						$("#applyBtn").on('click',function(e){
							var saveMode = "add";
							var outWareHouse = $("#addwareHouseOutSelect").find("option:selected").prop("value");
							var inWareHouse = $("#addwareHouseInSelect").find("option:selected").prop("value");
							var deptNeed = $("#adddeptNeedSelect").find("option:selected").prop("value");
							var needDate = $("#addneedDate").val();
							var deptApply = $("#adddeptApplySelect").find("option:selected").prop("value");
							var personnel = $("#addpersonnelSelect").find("option:selected").prop("value");
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
							if(needDate==""){
								alert("請輸入需求日期");
								return false;
							}
							if(deptApply=="--請選擇--" || deptApply==""){
								alert("請選擇申請部門");
								return false;
							}
							if(personnel == "" || personnel == '' || personnel=="--請選擇--"){
								alert("請選擇申請人，以便產生調撥單號");
								return false;
							}	
							if(inWareHouse==outWareHouse){
								alert("調出調入倉庫不可相同");
								return false;
							}
							//$("#applyBtn").prop("disabled","disabled");
							var memo = $("#memo").val();
							var data = {
								trNo : $("#tr_no").val(),
								personnel : personnel
							};
							var genTrNo ="";
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
								var crTime=getCrTime();
							//申請明細檔資料
							var selr = jQuery('#dtlgrid').jqGrid('getGridParam','selarrrow');
							if(selr.length) {
							for(var i=0;i<selr.length;i++){
									var myrow = jQuery('#dtlgrid').jqGrid('getRowData',selr[i]);
									var checkResult=myrow.checkResult;
									if(checkResult=="庫存已有其他申請單Booking"){
										alert("庫存已有其他申請單Booking");
										break;
									}	
									if(myrow.app_qty=="0"){
										alert("料號:"+myrow.mat_no+",物料狀態:"+myrow.mat_status_name+" 申請調撥數為0,無法新增,請取消勾選或是重新匯入!!");
										return false;
									}
								}
								for(var i=0;i<selr.length;i++)
								{
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
								
				$(window).resize(function() {
							$(window).unbind("onresize");
							$("#dtlgrid,.grid").setGridWidth($("#jQgrid").width() - 10);
							$(window).bind("onresize", this);
					}).trigger('resize');
			
				//申請部門連動申請人
				$("#adddeptApplySelect").change(onDeptSelectChange);	
					
					$("#addwareHouseOutSelect").change(function(){
							var  gridId=$("#dtlgrid").jqGrid('getDataIDs');
							if (gridId[0] != undefined){
								if(confirm("申請明細已有資料，是否仍要變調出倉"))
								{
									$("#dtlgrid").jqGrid().clearGridData();
									$("#applyBtn").prop("disabled","disabled");
									$("#saveBtn").prop("disabled","disabled");
								}else{}
							}
					});
									//區域聯動調出入倉，需求/申請單位
				$("#adddomainSelect").change(onaddDomainSelectChange);
			//區域聯動調出入倉，需求/申請單位
			function onaddDomainSelectChange(){
				resetByAddDomain();
				if ($("#adddomainSelect").find("option:selected").val() == "") {
					return false;
				}
				var data = {
					id : $("#adddomainSelect").find("option:selected").prop(
							"value")
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
								if("總倉" != data.rows[i].wh_name){						
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
					domain : $("#adddomainSelect").find("option:selected").prop(
							"value")
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
			function resetByAddDomain(){
				$("#addwareHouseOutSelect").html("");
				$("<option value=''>--請選擇--</option>").appendTo(
						"#addwareHouseOutSelect");
				$("#addwareHouseOutSelect").select2();
				$("#addwareHouseInSelect").html("");
				$("<option value=''>--請選擇--</option>").appendTo(
						"#addwareHouseInSelect");
				$("#addwareHouseInSelect").select2();
				$("#adddeptNeedSelect").html("");
				$("<option value=''>--請選擇--</option>").appendTo(
						"#adddeptNeedSelect");
				$("#adddeptNeedSelect").select2();
				$("#adddeptApplySelect").html("");
				$("<option value=''>--請選擇--</option>").appendTo(
						"#adddeptApplySelect");
				$("#adddeptApplySelect").select2();
			}
//=============================================
						function getCrTime(){
						var crTime = "";
							$.ajax({			
										url : CONTEXT_URL+"/ajax/inv/public/getToday/",		
										data:crTime,
										type : "POST",
										dataType : 'json',
										async: false,
										success : function(data) {
											if(data.success){
												crTime=data.msg
											}
										}
									});
							return crTime;			
						}
						function nullForZero (cellvalue, options, rowObject)
						{
						   var returnValue=cellvalue;
						   if(cellvalue == "" || cellvalue == "undefined" || cellvalue == null){
						   			returnValue="0";
						   }
						   return returnValue;
						}
						//上傳功能
						$("#uploadBtn").click(function() {
							$("#dtlgrid").jqGrid().clearGridData();	
							if ($.trim($("#file_A").val()) == "") {
									$("#file_A").click();
								}
						});
						// 匯入報表Button
						$("#file_A").change(function () {
							if ($.trim($("#file_A").val()) != "") {
								prepareUpload();
								uploadFiles();
								$("#file_A").val("");
							}
						});
						var files, fileNames; // user to check duplicate file

						//Grab the files and set them to our variable
						function prepareUpload() {
							files = event.target.files;
						}
					
						//檔案上傳
						function uploadFiles() {
							var addwareHouseOut = $("#addwareHouseOutSelect").val();
							if(addwareHouseOut == "" || addwareHouseOut == ''){
								alert("請選擇調出倉庫");
								return false;
							};
							event.stopPropagation(); // Stop stuff happening
							event.preventDefault(); // Totally stop stuff happening 
							var duplicateFile = false;
							var filename = $("#file_A").val();
							
							var selname = filename.substr(filename.indexOf(".") + 1,filename.length);
							if ($("#file_A").val() == '') {
								alert('請選擇檔案');
								return false;
							} else if (selname != "xls" && selname != "xlsx") {
								alert("檔案類型須為 xls,xlsx");
					
							} else {
								duplicateFile = true;
							}
							
							if (duplicateFile) {
								// Create a formdata object and add the files
								var data = new FormData();
								$.each(files, function(key, value) {
									data.append(key, value);
								});
								data.append('wareHouseOut', addwareHouseOut);
								
								$.ajax({
									url : CONTEXT_URL + "/inv/inv007/readBatchApply",
									type : 'post',
									data : data,
									cache : false,
									dataType : 'json',
									processData : false, // Don't process the files
									contentType : false, // Set content type to false as jQuery will tell the server its a query string request
									success : function(data) {
									alert(data.msg);
										var size=data.rows.length;
										if(data.rows.length > 0){
											for(i=0; i<data.rows.length; i++){	
												if(i==size-1){
													$("#dtlgrid").jqGrid({'total':data.rows[i].total,'page':data.rows[i].page,'record':data.rows[i].record});
												}else{	
													$("#dtlgrid").jqGrid("addRowData", i+1 ,{'mat_no':data.rows[i].mat_no, 'matName': data.rows[i].matName
													,'mat_status':data.rows[i].mat_status,'mat_status_name':data.rows[i].matStatusName,'app_qty':data.rows[i].app_qty,
													'stockQty':data.rows[i].stockQty,'checkResult':data.rows[i].checkResult,'bookingQty':data.rows[i].bookingQty,
													'checkStatus':data.rows[i].checkStatus,'ctrlType':data.rows[i].ctrlType
													}, "last" ); 
												}
											}
											//只要有一筆資料調撥筆數大於庫存 按鈕就鎖定並跳出
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
										}else{
											$("#applyBtn").prop("disabled","disabled");
											$("#saveBtn").prop("disabled","disabled");
											alert("上傳之批次新增EXCEL，皆查無庫存");
										}
										
									}
								});
							}
						}
						// 開啟下拉式選單搜尋文字功能
				function selectDefault() {
					$("#bBoardAddEdit").find("select").select2();
					$("#addwareHouseOutSelect").select2();
					$("#addwareHouseInSelect").select2();
					$("#adddeptNeedSelect").select2();
					$("#adddeptApplySelect").select2();
					$("#addpersonnelSelect").select2();
					$("#adddomainSelect").select2();
				}
				// 申請部門連動申請人
			function onDeptSelectChange() {
				$("#addpersonnelSelect").html("");
				$("<option value=''>--請選擇--</option>").appendTo(
						"#addpersonnelSelect");
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
									$("#addpersonnelSelect").append(
											"<option value="+data.rows[i].psn_NO+">"
													+ data.rows[i].chn_NM
													+ "</option>");
								}
							}
						}
					}
				});
			}
</script>
</head>
<body>
	<div class="row">
		<div id="breadcrumb" class="col-md-12">
			<ol class="breadcrumb">
				<div class="col-sm-12" id="btnDiv">
				<input type="hidden" id="saveFlag_A" value="N" />
				<input id="file_A" type="file" name="file" class="require" style="display: none;" />
					<button class="btn btn-primary btn-label-left" type="button"
						id="saveBtn" disabled="disabled">
						<span><i class="fa fa-save"></i></span> 儲存
					</button>
					<button class="btn btn-primary btn-label-left" type="button"
						id="applyBtn" disabled="disabled">申請</button>
					<button class="btn btn-primary btn-label-left" type="button"
						id="delDtlBtn">刪除明細</button>
					<button class="btn btn-primary btn-label-left" type="button"
						id="downloadBtn">下載物料明細</button>
					<button type="button" id="uploadBtn"
						class="btn btn-primary btn-label-left">
						<span><i class="fa fa-file"></i></span>
						<s:message text="匯入調撥明細" />
					</button>
				</div>
			</ol>
		</div>
	</div>
	<div class="box-header">
		<div class="box-name">
			<i class="fa fa-edit"></i> <span>調撥申請作業-批次新增</span>
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
					<form class="form-horizontal" id="bBoardAddEdit" enctype="multipart/form-data"/>
						<div class="form-group">
							<table style="width: 100%">
								<div class="row">
									<tr>
										<td><label class="col-sm-12 control-label"><span class="s">* </span>區域：</label></td>
									<td><div class="col-sm-6">
											<select id="adddomainSelect" name="adddomainSelect">
											<option value="" selected>--請選擇--</option>
												<c:forEach items="${domainSelect}" var="domain">
													<option value="${domain.ID}">${domain.NAME}</option>
												</c:forEach>
											</select>
										</div></td>
									</tr>		
									<tr>
										<td><label class="col-sm-12 control-label">調撥單號：</label></td>
										<td><div class="col-sm-6">
												<input id="tr_no" maxlength="20" type="text"
													class="form-control" name="tr_no" disabled="disabled"
													value="">
											</div></td>
										<td><label class="col-sm-12 control-label">調撥進度：草稿</label></td>
										<td><div class="col-sm-6"></div></td>
									</tr>

									<tr>
										<td><label class="col-sm-12 control-label"><span class="s">* </span>調出倉庫：</label></td>
										<td><div class="col-sm-6">
												<select id="addwareHouseOutSelect"
													name="addwareHouseOutSelect">
													<option value="" selected="true">--請選擇--</option>
												</select>
											</div></td>
										<td><label class="col-sm-12 control-label"><span class="s">* </span>調入倉庫：</label></td>
										<td>
											<div class="col-sm-6">
												<select id="addwareHouseInSelect"
													name="addwareHouseInSelect">
													<option value="" selected="true">--請選擇--</option>
												</select>
											</div>
										</td>
									</tr>
									<!-- <tr>
										<td><label class="col-sm-12 control-label">下載物料明細：</label></td>
										<td><div class="col-sm-6">
												<button class="btn btn-primary btn-label-left" type="button"
													id="downloadBtn">下載物料明細</button>
											</div></td>
										<td><label class="col-sm-12 control-label">上傳調撥明細：</label></td>
										<td><div class="col-sm-6"></div>
											<input id="file" type="file" name="file" /> <button type="button" id="uploadBtn">匯入報表</button>
										</td>
									</tr> -->
									<tr>
										<td><label class="col-sm-12 control-label"><span class="s">* </span>需求單位：</label></td>
										<td>
											<div class="col-sm-6">
												<select id="adddeptNeedSelect" name="adddeptNeedSelect">
													<option value="" selected="true">--請選擇--</option>
												</select>
											</div>
										</td>
										<td><label class="col-sm-12 control-label"><span class="s">* </span>需求日期：</label></td>
										<td><div class="col-sm-6">
												<input id="addneedDate" type="text" value="" name="addneedDate"
													readonly="readonly">
											</div></td>
									</tr>
									<tr>
										<td><label class="col-sm-12 control-label"><span class="s">* </span>申請單位：</label></td>
										<td>
											<div class="col-sm-6">
												<select id="adddeptApplySelect" name="adddeptApplySelect">
													<option value="" selected="true">--請選擇--</option>
												</select>
											</div>
										</td>
										<td><label class="col-sm-12 control-label">申請人：</label></td>
										<td>
											<div class="col-sm-6">
												<select id="addpersonnelSelect" name="addpersonnelSelect">
													<option value="" selected="true">--請選擇--</option>
													<c:forEach items="${personnelSelect}" var="personnel">
														<c:choose>
													   <c:when test="${invTrDscr.app_user eq personnel.PSN_NO}">  
								                          	<option value="${personnel.PSN_NO}" selected="selected">${personnel.CHN_NM}</option>
													   </c:when>
													   <c:otherwise>  
															<option value="${personnel.PSN_NO}">${personnel.CHN_NM}</option>
													   </c:otherwise>
													</c:choose>	
													</c:forEach>
												</select>
											</div>
										</td>
									</tr>
									<tr>
										<td><label class="col-sm-12 control-label">備註：</label></td>
										<td>
											<div class="col-sm-16">
												<textarea class="form-control" rows="2" id=memo name="memo"
													value=""></textarea>
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