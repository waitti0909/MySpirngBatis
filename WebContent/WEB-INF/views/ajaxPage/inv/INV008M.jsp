<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
<head>
<style type="text/css">
	.ui-autocomplete.ui-front.ui-menu.ui-widget.ui-widget-content.ui-corner-all {
         z-index: 10000 !important;
     }
	.ui-autocomplete {
		max-height: 100px;
		overflow-y: auto;
		overflow-x: hidden;
	}
</style>
<script type="text/javascript">
	
	var invTrDtlDataList;
	
	function errorMsg(keyCode){
		alert("請勿輸入中文");
	}
	
	function checkNumber(obj,appQty,trQty,id){
		var number = $(obj).val();
		var max = parseInt(appQty);
		
		var invTrDtlData = invTrDtlDataList[(id-1)];
		
		if((parseInt(number) + parseInt(trQty)) > max){
			alert("調撥數不可大於申請數。");
			$(obj).val(0);
			invTrDtlData.exportNumber = 0;
			return;
		}
		
		invTrDtlData.exportNumber = number;
		
	}
	
	function getGridWidth(){
		return (parseInt($(window).width()) - 100);
	}
	
	$(document).ready(function() {

		$('#inv008MDtlResult').css('width',getGridWidth());
		
		mountGrid();
		
		$("#inv008MDtlGrid").setGridParam({datatype:"json",postData:{'trNo':'${invTrDto.tr_no}','whId':'${invTrDto.tr_out_wh_id}'}, mtype: 'POST',page:1}).jqGrid().trigger("reloadGrid");

		mountButEvent();

	});
	
	//掛載表格
	function mountGrid(){
		
		var selectID = null;
		
		$('#inv008MDtlGrid').jqGrid({
			datatype: "local",
			//pager: '#inv008MDtlPager',
			height:'120px',
			url : CONTEXT_URL+"/inv/inv008/applyDetailSearch",
			colNames : [ '料號', '品名','廠商序號','已選廠商序號', '物料狀態', '申請調撥數', '已調出數', '本次調出數', 'Booking 數'],
			colModel : [ {name : 'mat_no',index : 'mat_no',align : 'center',sortable : false},
			             {name : 'matName',index : 'mat_name',align : 'center',sortable : false},
			             {name : 'companyNo',index : '',align : 'center',sortable : false, editable:true, edittype: 'text',
			            	 editoptions:{
				                     dataInit: function (elem) {
				                         value: mountAutocomplete(elem);
				                     }
				   				}},
						{name : 'selectNo',index : '',align : 'center',sortable : false, editable:true, edittype: 'button',
							 editoptions:{
		                    	 value:'查看已選序號',
		                    	 dataEvents:[
 			            		 	{	
 			            		 		type: "click",
 						   				fn: showSelectVenSn
 						   			}
 						   		]
			   				}},
			             {name : 'matStatus',index : 'matStatus',align : 'center',sortable : false},
			             {name : 'app_qty',index : 'applyNumber',align : 'center',sortable : false},
			             {name : 'trQty',index : 'tr_qty',align : 'center',sortable : false},
			             {name : 'exportNumber',index : 'number',align : 'center',sortable : false,
		   					formatter:function(cellvalue, options, rowObject){
		   						//數量件
		   						if(rowObject.ctrlType.toUpperCase() == "V"){
		   							var appQty = rowObject.app_qty;
		   							var trQty = rowObject.trQty;
		   							var id = options.rowId;
		   							return "<input id='inv008MDtlGrid_"+id+"' type='text' value='0' onkeypress='return event.charCode >= 48 && event.charCode <= 57' onkeyDown='if(event.keyCode==229)errorMsg()' onkeyup='checkNumber(this,"+appQty+","+trQty+","+id+")'></label>";
		   						}else if(rowObject.ctrlType.toUpperCase() == "S"){
		   						//序號件	
			            	 		return "<label id='inv008MDtlGrid_"+options.rowId+"'>"+cellvalue+"</label>";
		   						}
			             	}},
			             {name : 'bookingQty',index : 'bookingNumber',align : 'center',sortable : false,
			             	formatter:function(cellvalue, options, rowObject){
			            	 	return "<a href='#' onclick='bookingLink("+rowObject["mat_no"]+");return false;'><label>"+cellvalue+"</label></a>";
			             	}
			             } 
			            ],
			viewrecords : true,
			caption : "申請明細",
			loadonce: true,
			rowNum:-1,
			onSelectRow : function(id) {
				
				var id = $("#inv008MDtlGrid").jqGrid('getGridParam', 'selrow');
				
				var invTrDtlData = invTrDtlDataList[(id-1)];
				
				if(id != selectID){
					//開啟欄位編輯
					jQuery('#inv008MDtlGrid').jqGrid('editRow',id);
					//取消欄位編輯
					jQuery('#inv008MDtlGrid').jqGrid('restoreRow',selectID);
				}
				
				selectID = id;

				if(invTrDtlData.ctrlType.toUpperCase() == "V"){
					jQuery('#inv008MDtlGrid').jqGrid('restoreRow',id);
				}

				var data = {
					'trNo':invTrDtlData.tr_no,
					'matNo':invTrDtlData.mat_no,
					'dtlNo':invTrDtlData.tr_dtl_no,
					'trIn':'0',
					'outActNo':'0'
				};
				
				$("#inv008MHistoryGrid").setGridParam({datatype:"json",postData : data}).trigger("reloadGrid");
			},
			gridComplete : function() {},
			ondblClickRow : function(rowId) {},
			gridComplete:function(){},
			beforeProcessing: function (data, status, xhr) {
				invTrDtlDataList = data.rows;
            }
		});
		
		$('#inv008MHistoryGrid').jqGrid({
			datatype : "local",
			pager: '#inv008MHistoryPager',
			url : CONTEXT_URL+"/inv/inv008M/exportHistorySearch",
			colNames : [ '調出日期', '廠商序號','貼標號碼','調出數量','調出人員' ],
			colModel : [ {name : 'tr_OUT_DATE',index : 'tr_out_date',align : 'center',sortable : false,formatter: "date", formatoptions:{newformat: "Y-m-d"}},
			             {name : 'venSn',index : 'venSn',align : 'center',sortable : false}, 
			             {name : 'tagNo',index : 'tagNo',align : 'center',sortable : false}, 
			             {name : 'tr_QTY',index : 'tr_qty',align : 'center',sortable : false},
			             {name : 'tr_OUT_USER',index : 'tr_out_user',align : 'center',sortable : false} 
			            ],
			viewrecords : true,
			caption : "調出歷程",
			rownumbers : true,
			onSelectRow : function(id) {
			},
			gridComplete : function() {
			},
			ondblClickRow : function(rowId) {
			}
		});
		
		$(window).resize(function() {
			$(window).unbind("onresize");
			$("#inv008MDtlGrid,.grid").setGridWidth($("#inv008MJQgrid").width() - 10);
			$("#inv008MHistoryGrid,.grid").setGridWidth($("#inv008MJQgrid").width() - 10);
			$(window).bind("onresize", this);
		}).trigger('resize');
	}
	
	//callBackFunction
	function changeRowData(data){
		
		var id = $("#inv008MDtlGrid").jqGrid('getGridParam', 'selrow');
		
		var invTrDtlData = invTrDtlDataList[(id-1)];

		invTrDtlData.selectVenSnStrList = data;
		
		invTrDtlData.exportNumber = invTrDtlData.selectVenSnStrList.length;
		
		$('#inv008MDtlGrid_'+id).html(invTrDtlData.selectVenSnStrList.length);
		
	}
	
	//掛載按鈕事件
	function mountButEvent(){
		$("#saveBtn").click(function(e){
			
			//清除序號檔，避免過多造成延遲導致錯誤
			for(var i = 0 ; i<invTrDtlDataList.length ; i++){
				invTrDtlDataList[i].venSnByStrList=[];
			}
			
			var invTrDtoWork = JSON.stringify(invTrDtoToJson());
			var TbInvTrDtlDto = JSON.stringify(invTrDtlDataList);
			
			$.ajax({			
				url : CONTEXT_URL+"/inv/inv008M/executionExportWork",		
				data: {
						'invTrDtoWork':invTrDtoWork,
						'TbInvTrDtlDto':TbInvTrDtlDto
					},
				type : "POST",
				dataType : 'json',
				async:false,
				success : function(data) {
					if(data.success){
						parent.$.fancybox.close();
					}
					alert(data.msg);
				}
			});
			
		});
	}
	
	//TB_INV_TR To JSON
	function invTrDtoToJson(){
		
		var serviceMap = {};
		serviceMap["tr_no"]='${invTrDto.tr_no}';
		serviceMap["tr_out_wh_id"]='${invTrDto.tr_out_wh_id}';
		
		return serviceMap;
	}
	
	//查看廠商序號
	function showSelectVenSn(e) {
		
		var id = $("#inv008MDtlGrid").jqGrid('getGridParam', 'selrow');
		
		var invTrDtlData = invTrDtlDataList[(id-1)];
		
		$.ajax({
			mimeType : 'text/html; charset=utf-8',
			type : 'POST',
			url : CONTEXT_URL + "/inv/inv008M/showVenSnListView",
			data :JSON.stringify(invTrDtlData),
			dataType : "html",
			contentType : "application/json",
			async : false,
			success : function(data) {
				initialDialogFrame("certificatePage", "已選廠商序號", data);
			}
		});
	}
	
	function bookingLink(matNo) {
		$.ajax({			
			url : CONTEXT_URL+"/inv/inv008M/bookingSearch",		
			data :{
				'matNo':matNo,
				'whId':'${invTrDto.tr_out_wh_id}'
			},
			type : "POST",
			dataType : 'json',
			success : function(data) {
				$.ajax({
					mimeType : 'text/html; charset=utf-8',
					type : 'POST',
					url : CONTEXT_URL + "/inv/inv024/init",
					data : {
						"paramMatNo" : data.row["paramMatNo"],
						"paramWhId" : data.row["paramWhId"],
						"cancelSystemBut" : "true"
					},
					dataType : "html",
					async : false,
					success : function(data) {
						initialDialogFrame("showDeptPosPag", "Booking查詢", data);
					}
				});
			}
		});	
	}
	
	//掛載下拉選單
	function mountAutocomplete(elem) {
		var id = $("#inv008MDtlGrid").jqGrid('getGridParam', 'selrow');
		
		var invTrDtlData = invTrDtlDataList[(id-1)];
		
		//非序號件離開此Function		
		if(invTrDtlData.ctrlType.toUpperCase() == "V"){
			return;
		}
		
// 		var venSnList = invTrDtlData.venSnList;
		
		var venSnStrList = invTrDtlData.venSnByStrList;
		
		//若序號件已有資料則不在進行掛載
		if(venSnStrList.length == 0){

			var data = {
					'matNo':invTrDtlData.mat_no,
					'matStatus':invTrDtlData.mat_status,
					'whId':'${invTrDto.tr_out_wh_id}'
			};
			
			//取的廠商序號
			$.ajax({			
				url : CONTEXT_URL+"/inv/inv008M/getTrSrlData",		
				data:data,
				type : "POST",
				dataType : 'json',
				async:false,
				success : function(data) {
					if(data.success){
						if(data.rows.length > 0){
							for(var i=0; i<data.rows.length; i++){
								if(data.rows[i].ven_sn != null){
// 									venSnList.push(data.rows[i]);
									venSnStrList.push($.trim(data.rows[i].ven_sn));
								}
							}
						}
					}
				}
			});
		}
		
		//元件掛載
		$(elem).autocomplete({
			source:venSnStrList,
			select:function(event,ui){
				var selectVenSnList = invTrDtlData.selectVenSnStrList;
				if($.inArray(ui.item.value,selectVenSnList) >= 0){
					alert(ui.item.value+" 此廠商序號不可重複。");					
				}else{
					if(parseInt(invTrDtlData.exportNumber)+parseInt(invTrDtlData.trQty) >= parseInt(invTrDtlData.app_qty)){
						alert("調出數不可大於申請數。");
						return;
					}
					
					selectVenSnList.push(ui.item.value);
					invTrDtlData.exportNumber++;
					$('#inv008MDtlGrid_'+id).html(invTrDtlData.exportNumber);
				}
			}
		},{
			//delay:1000,
			minLength:3,
			matchContains: true,
			autoFocus:true
		});
		
	}
</script>
</head>
<body>
	<!-- Table Header -->
	<div class="box-header">
		<div class="box-name">
			<i class="fa fa-search"></i>調出作業 執行調出
		</div>
		<div class="no-move"></div>
	</div>
	
	<div class="row">
		<div id="breadcrumb" class="col-md-12">
			<ol class="breadcrumb">
				<div class="col-sm-12" id="btnDiv">
					<button class="btn btn-primary btn-label-left" type="button"
						id="saveBtn" >
						<span>
							<i class="fa fa-save"></i>
						</span> 
						執行調出
					</button>
				</div>
			</ol>
		</div>
	</div>
	
	<div class="row" id="sdf">
		<div class="box-content">
			<form role="form" class="form-horizontal bootstrap-validator-form"
				novalidate="novalidate" id="searchForm">
				<div class="form-group">
					<table style="width: 95%">
						<tr>
							<td class="col-sm-2 control-label"><label>調撥單號 :</label></td>
							<td class="col-sm-3"><label>${invTrDto.tr_no}</label></td>
							<td class="col-sm-1"></td>
							<td class="col-sm-2 control-label"><label>調撥進度：</label></td>
							<td class="col-sm-3"><label>${invTrDto.statusValue}</label></td>
							<td class="col-sm-1"></td>
						</tr>
						<tr>
							<td><div class="clearfix">&nbsp;</div></td>
						</tr>
						<tr>
							<td class="col-sm-2 control-label"><label>調出倉庫 :</label></td>
							<td class="col-sm-3"><label>${invTrDto.outWhName}</label></td>
							<td class="col-sm-1"></td>
							<td class="col-sm-2 control-label"><label>調入倉庫：</label></td>
							<td class="col-sm-3"><label>${invTrDto.inWhName}</label></td>
							<td class="col-sm-1"></td>
						</tr>
						<tr>
							<td><div class="clearfix">&nbsp;</div></td>
						</tr>
						<tr>
							<td class="col-sm-2 control-label"><label>需求單位 :</label></td>
							<td class="col-sm-3"><label>${invTrDto.needDeptName}</label></td>
							<td class="col-sm-1"></td>
							<td class="col-sm-2 control-label"><label>需求日期：</label></td>
							<td class="col-sm-3"><label>${invTrDto.needDateToString}</label></td>
							<td class="col-sm-1"></td>
						</tr>
						<tr>
							<td><div class="clearfix">&nbsp;</div></td>
						</tr>
						<tr>
							<td class="col-sm-2 control-label"><label>申請人單位 :</label></td>
							<td class="col-sm-3"><label>${invTrDto.applyDeptName}</label></td>
							<td class="col-sm-1"></td>
							<td class="col-sm-2 control-label"><label>申請人：</label></td>
							<td class="col-sm-3"><label>${invTrDto.appUserName}</label></td>
							<td class="col-sm-1"></td>
						</tr>
						<tr>
							<td><div class="clearfix">&nbsp;</div></td>
						</tr>
						<tr>
							<td class="col-sm-2 control-label"><label>備註 :</label></td>
							<td class="col-sm-3" colspan="4"><textarea id="memo"
									name="LOC_DESC" style="resize: none; width: 100%" rows="3" disabled>${invTrDto.memo}</textarea>
							</td>
							<td class="col-sm-1"></td>
							<td class="col-sm-2"></td>
							<td class="col-sm-3"></td>
							<td class="col-sm-1"></td>
						</tr>
						<tr>
							<td><div class="clearfix">&nbsp;</div></td>
						</tr>
					</table>
				</div>
			</form>
		</div>

		<div id="inv008MDtlResult" class="col-xs-12" style="padding-left:50px;">
			<div id="inv008MJQgrid">
				<table id="inv008MDtlGrid"></table>
				<div id="inv008MDtlPager"></div>
			</div>
		</div>

		<div class="clearfix">&nbsp;</div>
		<div class="clearfix">&nbsp;</div>
		
		<div id="inv008MDtlResult" class="col-xs-12" style="padding-left:50px;">
			<div id="inv008MJQgrid">
				<table id="inv008MHistoryGrid"></table>
				<div id="inv008MHistoryPager"></div>
			</div>
		</div>
	</div>
	
	<div id="showDeptPosPag" >
	</div>
	
	<div id="certificatePage"></div>
</body>
</html>