<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>物料操作</title>
<script type="text/javascript">
var materialMainBind = false;

$(document).ready(function() {
	
		if('${sign}' == "sign"){
			  //操作類別
			  $("#optType").append("<option>${optTypeName}</option>");
			  //工單編號
			  $("#showOrderIdMer").text("${meterialOpt.ORDER_ID}");
			  //工務類別 o
			  $("#showWorkTypeMer").text("${showWorkType}");
			  //處理狀態 
			  $("#showStatusMer").text("${showStatus}");
			  //操作單號
			  $("#showOptId").text("${meterialOpt.OPT_ID}");
			  $("#optId").val("${meterialOpt.OPT_ID}");
			  //申請單位 
			  $("#showAppDept").text("${showDeptName}");
			  //申請人 
			  $("#showAppUser").text("${showAppUserName}");
			  //基站編號 
			  $("#showBtsSiteId").text('${btsSiteId}');
			  //設備型態
			  $("#showEqpTypeId").text('${eqpTypeName}');
			  //申請時間
			  $("#showAplTime").text('${showAplTime}');
			  //派工單號
			  $("#osId option").remove();
			  
			  if($('#showOrderIdMer').text()  == '${meterialOpt.OS_ID}'){
				  $("#osId").append("<option value='${meterialOpt.OS_ID}' >自行施作</option>");
			  } else {
				  $("#osId").append("<option value='${meterialOpt.OS_ID}' >${meterialOpt.OS_ID}</option>");
			  }
			  
			  //領料倉庫
			  $("#whIdMer option").remove();
			  $("#whIdMer").append("<option value='' >${whName}</option>");
			  //需求日期
			  <fmt:formatDate value="${meterialOpt.REQ_DATE}" pattern="yyyy/MM/dd" var="reqDate"/>
			  $("#reqDate").val("${reqDate}").prop("disabled", true);
			  //領料說明
			  $("#showOptDesc").prop("disabled", false);
			  $("#showOptDesc").val("${meterialOpt.OPT_DESC}");
			  //明細資料
			 
			  initMrq();
			  meterialItem("${meterialOpt.OPT_ID}");
			  
			  $("#optType").prop("disabled", true);
			  $("#osId").prop("disabled", true);
			  $("#whIdMer").prop("disabled", true);
			  $("#showOptDesc").prop("disabled", true);
			  $("#btnMrqtrExXls").prop("disabled", true);
			  $("#btnMrqtrIpmXls").prop("disabled", true);
			  $("#btnMrqtrApply").prop("disabled", true);
			  $("#btnRemtr").prop("disabled", true);
		}
	});//document ready end	
	
	//綁定物料操作清單TR事件
	function bindMaterialOptTableTrEvt() {
		materialMainBind = true;
		$('#meterialTable tbody').off('click', 'tr').on( 'click','tr', function () {
			btnMrqtrClick = false;
			$('#meterialItem tbody').html('');
			$("#rtntrItem").jqGrid('clearGridData');
		    $("#remInsItem").jqGrid('clearGridData');
		    $("#remItem").jqGrid('clearGridData');
		    
			var data = $('#meterialTable').dataTable().fnGetData($(this).closest("tr").index());
			// set tr highlight by Charlie
			$("#meterialTable tbody tr").removeClass('row_selected_highlight');
			$(this).addClass('row_selected_highlight');
			if (data != null) {
				$.ajax({
					url : CONTEXT_URL + "/st/invMeterial/meterialOptById",
					type : 'POST',
					data : {
						optId :data['opt_ID']
					},
					async : false,
					success : function(data) {
	// 				  alert(JSON.stringify(data));
					  var meterialOpt = data.row['meterialOpt'];
					  //操作類別
					  $("#optType").val(meterialOpt.opt_TYPE);
					  //工單編號
					  $("#showOrderIdMer").text(meterialOpt.order_ID);
					  $("#orderIdMer").val(meterialOpt.order_ID);
					  //工務類別 o
					  $("#showWorkTypeMer").text(data.row["showWorkType"]);
					  $("#workTypeMer").val(meterialOpt.work_TYPE);
					  //處理狀態 
					  $("#showStatusMer").text(data.row["showStatus"]);
					  $("#statusMer").val(meterialOpt.status);
					  //操作單號
					  $("#showOptId").text(meterialOpt.opt_ID);
					  $("#optId").val(meterialOpt.opt_ID);
					  $("#applyNo").val(meterialOpt.opt_ID);
					  
					  //申請單位 
					  $("#showAppDept").text(data.row["showDeptName"]);
					  $("#appDept").val(meterialOpt.app_DEPT);
					  //申請人 
					  $("#showAppUser").text(data.row["showAppUserName"]);
					  $("#appUser").val(meterialOpt.app_USER);
					  //基站編號 
					  $("#showBtsSiteId").text(data.row['btsSiteId']);
					  $("#btsSiteId").val(data.row['btsSiteId']);
					  //設備型態
					  $("#showEqpTypeId").text(data.row['eqpTypeName']);
					  $("#eqpTypeId").val(data.row['eqpTypeId']);
					  //申請時間
					  $("#showAplTime").text(data.row['showAplTime']);
					  $("#aplTime").val(meterialOpt.app_TIME);
					  //派工單號
					  //不用重設派工單號-Kevin
					  //$("#osId").val(meterialOpt.os_ID).change();
					  //領料倉庫
					  $("#whIdMer").val(meterialOpt.wh_ID);
					  //需求日期
					  $("#reqDate").val(data.row['showReqDate']);
					  $("#remDate").val(data.row['showReqDate']);
					  //領料說明
					  $("#showOptDesc").prop("disabled", false);
					  $("#showOptDesc").prop("value",meterialOpt.opt_DESC);
					  //明細資料
					  if($("#optType").val()=='MRQ') //領料
					  {
						initMrq();
						meterialItem(meterialOpt.opt_ID);
					  }
					  if($("#optType").val()=='RTN') //退料
					  {
						initRTN();
						loadRtnTrItemGrid();
						$("#rtntrItem").hideCol("assetQty")
						.setColProp('qty',{editable:false})
						.setColProp('trnReason',{editable:false})
						.setColProp('matStatus',{editable:false})
						.setColProp('checkItem',{ editable:false,
												  formatoptions: {disabled :true}
						                         }
						           );
						
						$('#rtntrItem').jqGrid('setGridWidth',1000,true);
						rtntrItem(meterialOpt.opt_ID);
					  }
					  
					  if($("#optType").val()=='INS') //安裝
					  {
						initINS();
						loadRemInsItemGrid();
						$("#remInsItem").hideCol("assetQty")
						.setColProp('qty',{editable:false})
						.setColProp('checkItem',{editable:false})
						.setColProp('checkItem',{ editable:false,
																  formatoptions: {disabled :true}
                          										}
						                           );
						$('#remInsItem').jqGrid('setGridWidth',710,true);
						remInsItem(meterialOpt.opt_ID);
					  }
					  if($("#optType").val()=='REM') //拆下
					  {
						initREM();
						loadRemItemGrid();
						$("#remItem").hideCol("assetQty")
						.setColProp('qty',{editable:false})
						.setColProp('checkItem',{   editable:false,
							                                     formatoptions: {disabled :true}
								                                }
                                                    );
						$('#remItem').jqGrid('setGridWidth',710,true);
						remItem(meterialOpt.opt_ID);
					  }
					  $("#optType").prop("disabled", true);
					 // $("#osId").prop("disabled", true);
					  $("#whIdMer").prop("disabled", true);
					  $("#reqDate").prop("disabled", true);
					  $("#showOptDesc").prop("disabled", true);
					  $("#btnMrqtrExXls").prop("disabled", true);
					  $("#btnMrqtrIpmXls").prop("disabled", true);
					  
					  if(meterialOpt.status == 'MR03') {
						  $("#btnMrqtrApply").prop("disabled",false); //駁回 時
						  $("#btnMrqtrIpmXls").prop("disabled",false);
					  }else {
						  $("#btnMrqtrApply").prop("disabled", true);
						  $("#btnMrqtrIpmXls").prop("disabled",true);
					  }
					  
					  $("#btnRemtr").prop("disabled", true);
					}
				});
			} 
			
		});
	}
	
	//領料起始狀態
	function initMrq(){
		$("#btnMrqtrExXls").show();
		$("#btnMrqtrIpmXls").show();
		$("#mrqtr").show();
		$("#reqDateLabel").show();
		$("#reqDateText").show();
		$("#whIdName").show();
		$("#whIdMer").show();
		$("#mrqrtn").show();
		$("#btnMrqtrApply").show();
		$("#btnRemtr").hide();
		$("#rtntr").hide();
		$("#instr").hide();
		$("#remtr").hide();
		$("#remDateLabel").hide();
		$("#remDate").hide();
		$('#OptDescLabel').text("領料說明:");
		$('#whIdName').text("領料倉庫:");
		// 領料申請簽核
		$.ajax({
			url : CONTEXT_URL + "/commom/signHistory",
			type : 'POST',
			datatype:'html',
			data : {
				processType : 'SiteBuildMaterialApply',
				applyNo :   $("#optId").val()           
			},
			async : false,
			success : function(data) {
				$("#mtApplySignLog").empty();
				$('#mtApplySignLog').append(data);
			}
		});
	}
	//退料起始狀態
	function initRTN (){
		$("#rtntr").show();
		$("#whIdName").show();
		$("#whIdMer").show();
		$("#mrqrtn").hide();
		$("#btnMrqtrApply").show();
		$("#btnMrqtrExXls").hide();
		$("#btnMrqtrIpmXls").hide();
		$("#btnRemtr").hide();
		$("#reqDateLabel").hide();
		$("#reqDateText").hide();
		$("#remDateLabel").hide();
		$("#remDate").hide();
		$("#mrqtr").hide();
		$("#instr").hide();
		$("#remtr").hide();
		$('#OptDescLabel').text("退料說明:");
		$('#whIdName').text("退料倉庫:");
	}
	//安裝起始狀態
	function initINS (){
		$("#instr").show();
		$("#btnRemtr").show();
		$("#remDateLabel").show();
		$("#remDate").show();
		$("#mrqrtn").hide();
		$("#btnMrqtrApply").hide();
		$("#btnMrqtrExXls").hide();
		$("#btnMrqtrIpmXls").hide();
		$("#whIdName").hide();
		$("#whIdMer").hide();
		$("#reqDateLabel").hide();
		$("#reqDateText").hide();
		$("#mrqtr").hide();
		$("#rtntr").hide();
		$("#remtr").hide();
		$('#OptDescLabel').text("拆裝說明:");
	}
	//拆除起始狀態
	function initREM (){
		$("#remtr").show();
		$("#btnRemtr").show();
		$("#remDateLabel").show();
		$("#remDate").show();
		$("#mrqrtn").hide();
		$("#btnMrqtrApply").hide();
		$("#btnMrqtrExXls").hide();
		$("#btnMrqtrIpmXls").hide();
		$("#whIdName").hide();
		$("#whIdMer").hide();
		$("#reqDateLabel").hide();
		$("#reqDateText").hide();
		$("#mrqtr").hide();
		$("#rtntr").hide();
		$("#instr").hide();
		$('#OptDescLabel').text("拆裝說明:");
	}
	//領料明細
	function meterialItem(optId){
		  //第二個table Str
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
			"sAjaxSource" : CONTEXT_URL+ '/st/invMeterial/itemMainQuery?optId='+ optId,
					"aoColumnDefs" : [ 
						{"aTargets" : [ 0 ],"mData" : 
						function(data){ 
// 						alert(JSON.stringify(data))
						return data.itemNo+"<input type='hidden' name='itemNo' value='"+data.itemNo+"' >";
						}, "sWidth":"5%", "bSortable":false },
						
						{"aTargets" : [ 1 ],"mData" : 
							function(data){ 
							return data.itemDetail+"<input type='hidden' name='matName' value='"+data.itemDetail+"' >";
    					}, "sWidth":"10%", "bSortable":false },
    					
    					{"aTargets" : [2 ],"mData" : 
							function(data){ 
    						if("S" == data.ctrlType){
    							return data.qtySum+"<input type='hidden' name='qty' value='"+data.qtySum+"' >";
    						}else{
								return data.qty+"<input type='hidden' name='qty' value='"+data.qty+"' >";
    						}
    					}, "sWidth":"5%", "bSortable":false },
    					
    					{"aTargets" : [3 ],"mData" : 
							function(data){ 
							return data.actQty+"<input type='hidden' name='actQty' value='"+data.actQty+"' ><input type='hidden' name='ctrlType' value='"+data.ctrlType+"' >";
    					}, "sWidth":"5%", "bSortable":false },    
    					{"aTargets" : [4 ],"mData" : 
							function(data){ 
							return data.actNo;
    					}, "sWidth":"10%", "bSortable":false },
    					{"aTargets" : [5 ],"mData" : 
							function(data){ 
							return data.actName;
    					}, "sWidth":"10%", "bSortable":false },
    					{"aTargets" : [6 ],"mData" : 
							function(data){ 
							return data.vsn;
    					}, "sWidth":"15%", "bSortable":false },
    					{"aTargets" : [7 ],"mData" : 
							function(data){ 
							return data.tag;
    					}, "sWidth":"15%", "bSortable":false },
    					{"aTargets" : [8 ],"mData" : 
							function(data){ 
							return data.ctrlType;
    					}, "bVisible": false },
					],
					"oLanguage" : {
						"sProcessing" : "處理中...",
						"sZeroRecords" : "沒有匹配結果",
						"sLoadingRecords" : "讀取中..."
					},
// 					"fnInitComplete" : function() {
// 						this.fnAdjustColumnSizing();
// 					},
				});
		  
	}//領料明細  End
		
	//查詢申請單之退料明細
	function rtntrItem(optId) {
		
		$.ajax({
			url : CONTEXT_URL + "/st/invMeterial/rtntrItemByOptId",
			type : 'POST',
			data : {
				"optId": optId, //申請單號
			},
			async : false,
			success : function(data) {
				$("#rtntrItem").jqGrid('clearGridData');
				
			 for(var i=0;i<data.rows.length;i++) {
					jQuery("#rtntrItem").addRowData(i+1,data.rows[i]);
				} 
			}
		});
	}
	
	function loadRtnTrItemGrid() {
		//退料--新增
		$("#rtntrItem").jqGrid(
		{
		   datatype : "local",
		   url : CONTEXT_URL+ '/st/invMeterial/rtntrItemByRtn',
		   colNames : [ '選擇','料號', '品名','廠商序號', '貼標號碼', '在途數量',
						'退料數量', '退料種類', '退料原因','srlNo','assetNo' ],
			colModel : [ {
				name : 'checkItem',
				index : 'checkItem',
				width : 50,
				editable :true,
				edittype : 'checkbox',
				editoptions: {value:"1:0"},
				formatter: "checkbox",
				formatoptions: {
					defaultValue: "1",
					disabled :false
				},
				sortable : false
			}, {
				name : 'matNo',
				index : 'matNo',
				width : 110,
				sortable : false
			}, {
				name : 'matName',
				index : 'matName',
				width : 110,
				sortable : false
			}, {
				name : 'venSn',
				index : 'venSn',
				width : 110,
				sortable : false
			}, {
				name : 'tagNo',
				index : 'tagNo',
				width : 110,
				sortable : false
			}, {
				name : 'assetQty',
				index : 'assetQty',
				width : 80,
				sortable : false
			}, {
				name : 'qty',
				index : 'qty',
				editable : true,
				edittype : 'text',
				width : 80,
				editoptions: {
						  dataEvents:[
				             {
				            	type:"keypress",
				            	fn:function(e){
				            		return keyPressEventForInteger(e.keyCode,this);
							      }
							}, 
							 {
				            	type:"blur",
				            	fn:function(){
				            		onBlurCheckNumber(this);
							      }
							}
				          ] , 
				         dataInit:function(elem){
								$(elem).addClass('numberMask').addClass('form-control');
							} 
			}, 
			 width : 110,
             sortable : false
			}, {
				name : 'matStatus',
				index : 'matStatus',	
				editable : true,
				edittype:'select',
				formatter: 'select',
				editoptions: {
							value: matStatus,
							dataInit:function(elem){
								$(elem).addClass('form-control');
							}
						},
				width : 110,
				sortable : false
			}, {
				name : 'trnReason',
				index : 'trnReason',
				editable : true,
				edittype : 'select',
				formatter: "select",
				editoptions: {
						value: invRnResn,
						dataInit:function(elem){
								$(elem).addClass('form-control');
							}
						},
				width : 110,
				sortable : false
			}, {
				name : 'srlNo',
				index : 'srlNo',
				hidden : true,
				sortable : false
			}, {
				name : 'assetNo',
				index : 'assetNo',
				hidden : true,
				sortable : false
			} ],
			rownumbers : false,
			rowNum:-1,
			//width:980,
			shrinkToFit:false,
			scrollrows:false,
			onSelectRow: function(row){
				$('#rtntrItem').editRow(row, true);
				setTimeout(
						function(){
								$('#rtntrItem #'+row+'_qty').focus();
						}
				,50);
			},
			gridComplete : function() {
				$(".numberMask").mask('#0',{reverse: true});								 
			},
		});//end grid
	}
	

					//查詢申請單之安裝明細
					function remInsItem(optId) {
					 	$.ajax({
							url : CONTEXT_URL + "/st/invMeterial/remInsItemByOptId",
							type : 'POST',
							data : {
								"optId": optId, //申請單號
							},
							async : false,
							success : function(data) {
							$("#remInsItem").jqGrid('clearGridData');
								for(var i=0;i<data.rows.length;i++) {
									jQuery("#remInsItem").addRowData(i+1,data.rows[i]);
								} 
							 }
						}); 
					}
					
	function loadRemInsItemGrid(){	
					//安裝--新增
					$("#remInsItem").jqGrid(
					{
					   datatype : "local",
					   url : CONTEXT_URL+ '/st/invMeterial/remInsItemQuery',
					   colNames : [ '選擇',
									'料號', '品名',
									'廠商序號', '貼標號碼', '在途數量',
									'安裝數量','srlNo','assetNo','faNo' ],
										colModel : [ {
											name : 'checkItem',
											index : 'checkItem',
											width : 50,
											editable :true,
											edittype : 'checkbox',
											editoptions: {value:"1:0"},
											formatter: "checkbox",
											formatoptions: {
												defaultValue: "1",
												disabled :false
											},
											sortable : false
										}, {
											name : 'matNo',
											index : 'matNo',
											width : 110,
											sortable : false
										}, {
											name : 'matName',
											index : 'matName',
											width : 110,
											sortable : false
										}, {
											name : 'venSn',
											index : 'venSn',
											width : 110,
											sortable : false
										}, {
											name : 'tagNo',
											index : 'tagNo',
											width : 110,
											sortable : false
										}, {
											name : 'assetQty',
											index : 'assetQty',
											width : 110,
											sortable : false
										}, {
											name : 'qty',
											index : 'qty',
											editable : true,
											editoptions: {
												dataEvents:[
												             {
												            	type:"keypress",
												            	fn:function(e){
												            		return keyPressEventForInteger(e.keyCode,this);
															      }
															}, 
															 {
												            	type:"blur",
												            	fn:function(){
												            		onBlurCheckNumber(this);
															      }
															}
												          ] , 
										         dataInit:function(elem){
													$(elem).addClass('numberMask').addClass('form-control');
												} 
											},
											width : 110,
											sortable : false
										},{
											name : 'srlNo',
											index : 'srlNo',
											hidden : true,
											sortable : false
										}, {
											name : 'assetNo',
											index : 'assetNo',
											hidden : true,
											sortable : false
										} , {
											name : 'faNo',
											index : 'faNo',
											hidden : true,
											sortable : false
										} ],
										rownumbers : false,
										rowNum:-1,
										//width:750,
										shrinkToFit:false,
										gridComplete : function() {
											$(".numberMask").mask('#0',{reverse: true});
											
										},
										onSelectRow: function(row){
											$('#remInsItem').editRow(row, true);
											setTimeout(
													function(){
															$('#remInsItem #'+row+'_qty').focus();
													}
											,50);
										}
										
									});//end grid
	}
	
	//查詢申請單之拆下明細
	function remItem(optId) {
		
		$.ajax({
			url : CONTEXT_URL + "/st/invMeterial/remItemByOptId",
			type : 'POST',
			data : {
				"optId": optId, //申請單號
			},
			async : false,
			success : function(data) {
				$("#remItem").jqGrid('clearGridData');
				for(var i=0;i<data.rows.length;i++) {
					jQuery("#remItem").addRowData(i+1,data.rows[i]);
				}
			}
		});
	}
					
 function loadRemItemGrid(){					
					//拆下--新增
					$("#remItem").jqGrid(
					{
					   datatype : "local",
					   url : CONTEXT_URL+ '/st/invMeterial/remItemQuery',
					   colNames : [ '選擇',
									'料號', '品名',
									'廠商序號', '貼標號碼', '站上數量',
									'拆除數量','srlNo','assetNo','faNo' ],
						colModel : [ {
							name : 'checkItem',
							index : 'checkItem',
							width : 50,
							editable : true,
							edittype : 'checkbox',
							editoptions: {value:"1:0"},
							formatter: "checkbox",
							formatoptions: {
								defaultValue: "1",
								disabled :false
							},
							sortable : false
						}, {
							name : 'matNo',
							index : 'matNo',
							width : 110,
							sortable : false
						}, {
							name : 'matName',
							index : 'matName',
							width : 110,
							sortable : false
						}, {
							name : 'venSn',
							index : 'venSn',
							width : 110,
							sortable : false
						}, {
							name : 'tagNo',
							index : 'tagNo',
							width : 110,
							sortable : false
						}, {
							name : 'assetQty',
							index : 'assetQty',
							width : 110,
							sortable : false
						}, {
							name : 'qty',
							index : 'qty',
							editable : true,
							editoptions: {
								dataEvents:[
								             {
								            	type:"keypress",
								            	fn:function(e){
								            		return keyPressEventForInteger(e.keyCode,this);
											      }
											}, 
											 {
								            	type:"blur",
								            	fn:function(){
								            		onBlurCheckNumber(this);
											      }
											}
								          ],
						         dataInit:function(elem){
						        	 $(elem).addClass('numberMask').addClass('form-control');
									} 	          
						},
							width : 110,
							sortable : false
						},{
							name : 'srlNo',
							index : 'srlNo',
							hidden : true,
							sortable : false
						}, {
							name : 'assetNo',
							index : 'assetNo',
							hidden : true,
							sortable : false
						} , {
							name : 'faNo',
							index : 'faNo',
							hidden : true,
							sortable : false
						} ],
						rownumbers : false,
						rowNum:-1,
						//width:710,
						shrinkToFit:false,
						onSelectRow: function(row){
							$('#remItem').editRow(row, true);
							setTimeout(
									function(){
											$('#remItem #'+row+'_qty').focus();
									}
							,50);
						},
						gridComplete : function() {
							$(".numberMask").mask('#0',{reverse: true});
						}
					});//end grid
 }
 
	function meterialTableInit(optId,osId) {
		$("#meterialItem tbody tr").remove();
		$("#rtntrItem").jqGrid('clearGridData');
	    $("#remInsItem").jqGrid('clearGridData');
	    $("#remItem").jqGrid('clearGridData');
	//物料操作
	table = $('#meterialTable').dataTable(
			{
				"bDestroy" : true,
				"iDisplayLength" : -1,
				"sDom" : "rS",
					// 	 			"bAutoWidth":true,
				"sScrollY" : '70',
				"bSort" : false,
					// 		        "sScrollX" : "100%",
				"bProcessing" : false,
				"sAjaxDataProp" : "rows",
				"sAjaxSource" : CONTEXT_URL+ '/st/invMeterial/meterialOpt?orderId='+osId,
							"aoColumnDefs" : [ 
							{"aTargets" : [ 0 ],"mData" : "opt_ID"}, 
							{"aTargets" : [ 1 ],"mData" : "opt_TYPE_NAME"}, 
							{"aTargets" : [ 2 ],"mData" : function(data){ 
								 var date = new Date(data.app_TIME); 
								 var dateWrapper = moment(date);
								 return dateWrapper.format('YYYY/MM/DD');
	    					}}, 
							{"aTargets" : [ 3 ],"mData" : "wh_NAME"}, 
							{"aTargets" : [ 4 ],"mData" : "status_NAME"},
							],
					"oLanguage" : {
						"sProcessing" : "處理中...",
						"sZeroRecords" : "沒有匹配結果",
						"sLoadingRecords" : "讀取中..."
					},
					"fnInitComplete" : function() {
						this.fnAdjustColumnSizing();
						if (!materialMainBind) bindMaterialOptTableTrEvt();
						
						if(!btnMrqtrClick){  //NOMS-973 廠商-土木工單物料操作畫面控制異常
						
							if (optId == '0') {
								$('#meterialTable tbody').find("tr:first").trigger('click');
							}
							$('#meterialTable tr:gt(0)').each(function(){
						    	//$.log($('td:first', $(this)).html());
								if ($('td:first', $(this)).html() == optId) {
									$(this).trigger('click');	// 將原本資料預設進行點選動作
								}
							});
						}
					},
				});
	}
									
	//捕捉鍵盤對應的數量鍵(只允許正整數)								
	function keyPressEventForInteger(keyCode,elem){
   	  	if(keyCode>=48 && keyCode<=57){ //數字鍵盤
   		        return true;
   		    }else{
   		    	$(elem).focus();
   		        return false;
   		    }
	}
	
	//離開input後檢核
	function onBlurCheckNumber (elem){
		if($.trim($(elem).val()) == ''){
			$(elem).val('0');
		} else if((!gIsDigit($(elem).val()))  || $(elem).val() <0 ){
    		   $(elem).focus();
    		   alert("請輸入正整數的數字！");
    	   }
      }
	
</script>
</head>
<body>
	<p><p>
	<table style="width: 100%">
		<tr>
			<td width="10%" nowrap="nowrap">
				<label	class="col-sm-12 control-label">操作類別 :</label></td>
			<td width="23%">
				<select id="optType" name="optType"	class="form-control">
				</select>
			</td>
		</tr>
		<tr>
			<td width="10%" nowrap="nowrap">
				<label	class="col-sm-12 control-label">工單號碼 :</label>
			</td>
			<td width="23%">
				<p style="padding-top: 15px; padding-left: 5px;" id="showOrderIdMer"></p>
				<input id="orderIdMer" name="orderIdMer" type="hidden"></input>
			</td>
			<td width="10%" nowrap="nowrap">
				<label	class="col-sm-12 control-label">工務類別 :</label>
			</td>
			<td width="23%">
				<p style="padding-top: 15px; padding-left: 5px;" id="showWorkTypeMer"></p>
				<input id="workTypeMer" name="workTypeMer" type="hidden"></input>
			</td>
			<td width="10%">
				<label class="col-sm-12 control-label">處理狀態:</label>
			</td>
			<td width="24%">
				<p style="padding-top: 15px; padding-left: 5px;" id="showStatusMer"></p>
				<input id="statusMer" name="statusMer" type="hidden"></input>
			</td>
		</tr>
		<tr>
			<td width="10%" nowrap="nowrap">
				<label class="col-sm-12 control-label">操作單號 :</label>
			</td>
			<td width="23%">
				<p style="padding-top: 15px; padding-left: 5px;" id="showOptId"></p>
				<input id="optId" name="optId" type="hidden"></input>
			</td>
			<td width="10%" nowrap="nowrap">
				<label	class="col-sm-12 control-label">申請單位 :</label>
			</td>
			<td width="23%">
				<p style="padding-top: 15px; padding-left: 5px;" id="showAppDept"></p>
				<input id="appDept" name="appDept" type="hidden"></input>
			</td>
			<td width="10%">
				<label class="col-sm-12 control-label">申請人:</label>
			</td>
			<td width="24%">
				<p style="padding-top: 15px; padding-left: 5px;" id="showAppUser"></p>
				<input id="appUser" name="appUser" type="hidden"></input>
			</td>
		</tr>
		<tr>
			<td width="10%" nowrap="nowrap">
				<label	class="col-sm-12 control-label">基站編號 :</label>
			</td>
			<td width="23%">
				<p style="padding-top: 15px; padding-left: 5px;" id="showBtsSiteId"></p>
				<input id="btsSiteId" name="btsSiteId" type="hidden"></input>
			</td>
			<td width="10%" nowrap="nowrap">
				<label	class="col-sm-12 control-label">設備型態 :</label>
			</td>
			<td width="23%">
				<p style="padding-top: 15px; padding-left: 5px;" id="showEqpTypeId"></p>
				<input id="eqpTypeId" name="eqpTypeId" type="hidden"></input>
			</td>
			<td width="10%">
				<label class="col-sm-12 control-label">申請時間:</label>
			</td>
			<td width="24%">
				<p style="padding-top: 15px; padding-left: 5px;" id="showAplTime"></p> 
				<input id="aplTime" name="aplTime" type="hidden"></input>
			</td>
		</tr>
		<tr class="MRQTR">
			<td width="10%" nowrap="nowrap">
				<label class="col-sm-12 control-label">派工單號 :</label>
			</td>
			<td width="23%">
				<select id="osId" name="osId" class="form-control">
					<option value="" >---請選擇--- </option>
				</select>
			</td>
			<td width="10%">
				<label class="col-sm-12 control-label" id="whIdName"></label>
				<label class="col-sm-12 control-label" id="remDateLabel">拆裝日期 :</label>
			</td>
			<td width="24%" id="whIdItem">
				<select id="whIdMer" name="whIdMer" class="form-control">
					<option value="" >----請選擇---- </option>
				</select>
				<div class="col-sm-10">
					<input id="remDate" name="remDate" type="text"	class="form-control" readonly="readonly" placeholder="點選輸入框">
				</div>
			</td>
			
			<td id="reqDateLabel">
				<label class="col-sm-12 control-label" id="reqDateLabelText">需求日期 :</label>
			</td>
			<td id="reqDateText">
				<div class="col-sm-10">
					<input id="reqDate" name="reqDate" type="text"	class="form-control" readonly="readonly" placeholder="點選輸入框">
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<div class="clearfix">&nbsp;</div>
			</td>
		</tr>
		<tr class="MRQTR" id="mrqtr">
			<td valign='top' width="10%" nowrap="nowrap" style="padding-top: 15px;">
				<label class="col-sm-12 control-label">領料明細 :</label>
			</td>
			<td colspan="5">
				<div>
					<table id="meterialItem"  class="table table-bordered table-striped table-hover table-heading table-datatable dataTables_wrapper" border="1"	style="table-layout: fixed; word-break: break-all; word-wrap: break-word;">
						<thead style="background-color: #f0f0f0;">
							<tr>
								<th class="" style="width: 110px;">料號</th>
								<th class="" style="width: 110px;">品名</th>
								<th class="" style="width: 110px;">申請數量</th>
								<th class="" style="width: 110px;">發料數量</th>
								<th class="" style="width: 110px;">實發料號</th>
								<th class="" style="width: 110px;">實發品名</th>
								<th class="" style="width: 110px;">廠商序號</th>
								<th class="" style="width: 110px;">貼標號碼</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
			</td>
		</tr>
		<tr id="rtntr">
			<td valign='top' width="10%" nowrap="nowrap" style="padding-top: 15px;">
				<label class="col-sm-12 control-label">退料明細 :</label>
			</td>
			<td colspan="5">
				<div>
					<table id="rtntrItem"  border="1"	style="table-layout: fixed; word-break: break-all; word-wrap: break-word;">
					</table>
				</div>
			</td>
		</tr>
		
		<tr id="instr">
			<td valign='top' width="10%" nowrap="nowrap" style="padding-top: 15px;">
				<label class="col-sm-12 control-label">拆裝明細 :</label>
			</td>
			<td colspan="5" nowrap="nowrap">
				<div>
					<table id="remInsItem"  border="1"	style="table-layout: fixed; word-break: break-all; word-wrap: break-word;">
						
					</table>
				</div>
			</td>
		</tr>
		<tr id="remtr">
			<td valign='top' width="10%" nowrap="nowrap" style="padding-top: 15px;">
				<label class="col-sm-12 control-label">拆裝明細 :</label>
			</td>
			<td colspan="5" nowrap="nowrap">
				<div>
					<table id="remItem" border="1"	style="table-layout: fixed; word-break: break-all; word-wrap: break-word;">
					</table>
				</div>
			</td>
		</tr>
		
		<tr class="MRQTR">
			<td valign='top' width="10%" nowrap="nowrap" style="padding-top: 15px;">
			  <label class="col-sm-12 control-label"  id="OptDescLabel"></label>
			</td>
			<td colspan="5" nowrap="nowrap">　
				<div style="margin-top: 15px">
					<textarea class="form-control" id="showOptDesc" name="showOptDesc" rows="3"></textarea>
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<div class="clearfix">&nbsp;</div>
			</td>
		</tr>
<!-- 		<tr class="MRQTR" id='mrqrtn'> -->
<!-- 			<td> -->
<!-- 				<label class="col-sm-12 control-label" style="margin-bottom: 80px">簽核意見 :</label> -->
<!-- 			</td> -->
<!-- 			<td colspan="5" nowrap="nowrap"> -->
<!-- 				<div class="form-group" -->
<!-- 					style="margin-left: 15px; width: 94%; margin-bottom: 65px;"> -->
<%-- 					<c:import url="/commom/signHistory"> --%>
<%-- 						<c:param name="processType" value="${processType}"></c:param> --%>
<%-- 						<c:param name="applyNo"></c:param> --%>
<%-- 					</c:import> --%>
<!-- 				</div> -->
<!-- 			</td> -->
<!-- 		</tr> -->
		<tr class="MRQTR" id='mrqrtn'>
			<td><label class="col-sm-12 control-label" style="margin-bottom: 80px">簽核意見 :</label></td>
			<td colspan="5">
				<div class="form-group" id="mtApplySignLog"
					style="margin-left: 15px; width: 94%; margin-bottom: 65px;">
				</div>
			</td>
		</tr>
	</table>
<!-- 	<input id="checkBoxItemIdTab2" type="hidden"></input> -->
<!-- 	<input id="poYearTab2" type="hidden"></input> -->
<!-- 	<input id="poId" type="hidden" > -->
</body>
</html>