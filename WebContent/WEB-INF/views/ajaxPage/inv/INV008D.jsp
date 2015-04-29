<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
<head>
<script type="text/javascript">
	
	var invTrDtlDataList;

	function getGridWidth(){
		return (parseInt($(window).width()) - 100);
	}
	
	$(document).ready(function() {
		
		$('#inv008DResult').css('width',getGridWidth());
		
		//Tabel 設定
		tableDetail();
		
		$("#inv008DDtlGrid").setGridParam({datatype:"json",postData:{'trNo':'${invTrDto.tr_no}','whId':'${invTrDto.tr_out_wh_id}'}, mtype: 'POST',page:1}).jqGrid().trigger("reloadGrid");

	});

	//Tabel設定
	function tableDetail() {

		$('#inv008DDtlGrid').jqGrid({
			datatype: "local",
			pager: '#inv008DDtlPager',
			url : CONTEXT_URL+"/inv/inv008/applyDetailSearch",
			colNames : [ '料號', '品名','物料狀態', '申請調撥數', '已調出數'],
			colModel : [ {name : 'mat_no',index : 'mat_no',align : 'center',sortable : false},
			             {name : 'matName',index : 'mat_name',align : 'center',sortable : false},
			             {name : 'matStatus',index : 'matStatus',align : 'center',sortable : false},
			             {name : 'app_qty',index : 'applyNumber',align : 'center',sortable : false},
			             {name : 'trQty',index : 'tr_qty',align : 'center',sortable : false}
			            ],
			viewrecords : true,
			caption : "申請明細",
			onSelectRow : function(id) {
				var id = $("#inv008DDtlGrid").jqGrid('getGridParam', 'selrow');
				
				var invTrDtlData = invTrDtlDataList[(id-1)];
				
				var data = {
					'trNo':invTrDtlData.tr_no,
					'matNo':invTrDtlData.mat_no,
					'dtlNo':invTrDtlData.tr_dtl_no,
					'trIn':'0',
					'outActNo':'0'
				};
				
				$("#inv008DHistoryGrid").setGridParam({datatype:"json",postData : data}).trigger("reloadGrid");
			},
			gridComplete : function() {},
			ondblClickRow : function(rowId) {},
			gridComplete:function(){},
			beforeProcessing: function (data, status, xhr) {
				invTrDtlDataList = data.rows;
            }
		});
	
		$('#inv008DHistoryGrid').jqGrid({
			datatype : "local",
			pager: '#inv008DHistoryPage',
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
			$("#inv008DDtlGrid,.grid").setGridWidth($("#inv008DjQgrid").width() - 10);
			$("#inv008DHistoryGrid,.grid").setGridWidth($("#inv008DjQgrid").width() - 10);
			$(window).bind("onresize", this);
		}).trigger('resize');
		
	}
</script>
</head>
<body>
	<!-- Table Header -->
	<div class="box-header">
		<div class="box-name">
			<i class="fa fa-search"></i>調出作業 明細查詢
		</div>
		<div class="no-move"></div>
	</div>

	<div class="row">
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

		<div id="inv008DResult" class="col-xs-12" style="padding-left:50px;">
			<div id="inv008DjQgrid">
				<table id="inv008DDtlGrid"></table>
				<div id="inv008DDtlPager"></div>
			</div>
		</div>

		<div class="clearfix">&nbsp;</div>
		<div class="clearfix">&nbsp;</div>

		<div id="inv008DResult" class="col-xs-12" style="padding-left:50px;">
			<div id="inv008DjQgrid">
				<table id="inv008DHistoryGrid"></table>
				<div id="inv008DHistoryPage"></div>
			</div>
		</div>
	</div>
</body>
</html>