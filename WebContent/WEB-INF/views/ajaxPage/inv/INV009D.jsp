<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
<head>
<script type="text/javascript">
	
	var invTrActDataList;

	function getGridWidth(){
		return (parseInt($(window).width()) - 100);
	}
	
	$(document).ready(function() {
		
		$('#inv009DResult').css('width',getGridWidth());
		
		//Tabel 設定
		tableDetail();
		
		$("#inv009DDtlGrid").setGridParam({datatype:"json",postData:{'trNo':'${invTrDto.tr_no}'}, mtype: 'POST',page:1}).jqGrid().trigger("reloadGrid");

	});

	//Tabel設定
	function tableDetail() {

		$('#inv009DDtlGrid').jqGrid({
			datatype: "local",
			pager: '#inv009DDtlPager',
			url : CONTEXT_URL+"/inv/inv009M/getInvTrExportDtlData",
			colNames : [ '料號', '品名','廠商序號','貼標號碼','物料狀態', '申請數', '已調入數'],
			colModel : [ {name : 'mat_NO',index : 'mat_no',align : 'center',sortable : false},
			             {name : 'matName',index : 'mat_name',align : 'center',sortable : false},
			             {name : 'venSn',index : 'venSn',align : 'center',sortable : false},
						 {name : 'tagNo',index : 'tagNo',align : 'center',sortable : false},
			             {name : 'matStatusStr',index : 'matStatusStr',align : 'center',sortable : false},
			             {name : 'tr_QTY',index : 'trQty',align : 'center',sortable : false},
			             {name : 'dtlTrQty',index : 'trQty',align : 'center',sortable : false},
			            ],
			viewrecords : true,
			caption : "申請明細",
			onSelectRow : function(id) {
				var invTrActData = invTrActDataList[(id-1)];
				
				var data = {
					'trNo':invTrActData.tr_NO,
					'matNo':invTrActData.mat_NO,
					'dtlNo':invTrActData.tr_DTL_NO,
					'trIn':'1',
					'outActNo':invTrActData.tr_ACT_NO
				};
				
				$("#inv009DHistoryGrid").setGridParam({datatype:"json",postData : data}).trigger("reloadGrid");
			},
			gridComplete : function() {},
			ondblClickRow : function(rowId) {},
			gridComplete:function(){},
			beforeProcessing: function (data, status, xhr) {
				invTrActDataList = data.rows;
            }
		});
	
		$('#inv009DHistoryGrid').jqGrid({
			datatype : "local",
			pager: '#inv009DHistoryPage',
			url : CONTEXT_URL+"/inv/inv008M/exportHistorySearch",
			colNames : [ '調入日期', '廠商序號','貼標號碼','調入數量','調入人員','是否返回調出倉' ],
			colModel : [ {name : 'tr_IN_DATE',index : 'tr_out_date',align : 'center',sortable : false,formatter: "date", formatoptions:{newformat: "Y-m-d"}},
			             {name : 'venSn',index : 'venSn',align : 'center',sortable : false}, 
			             {name : 'tagNo',index : 'tagNo',align : 'center',sortable : false}, 
			             {name : 'tr_QTY',index : 'tr_qty',align : 'center',sortable : false},
			             {name : 'tr_IN_USER',index : 'tr_out_user',align : 'center',sortable : false},
			             {name : 'if_RETURN_TR_OUT',index : 'tr_out_user',align : 'center',sortable : false,
			            	 formatter:function(cellvalue, options, rowObject){
			            		if(cellvalue == '0'){
			            			return '否';
			            		}else{
			            			return '是';
			            		}	 
					         }
			             } 
			            ],
			viewrecords : true,
			caption : "調入歷程",
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
			$("#inv009DDtlGrid,.grid").setGridWidth($("#inv009DjQgrid").width() - 10);
			$("#inv009DHistoryGrid,.grid").setGridWidth($("#inv009DjQgrid").width() - 10);
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

		<div id="inv009DResult" class="col-xs-12" style="padding-left:50px;">
			<div id="inv009DjQgrid">
				<table id="inv009DDtlGrid"></table>
				<div id="inv009DDtlPager"></div>
			</div>
		</div>

		<div class="clearfix">&nbsp;</div>
		<div class="clearfix">&nbsp;</div>

		<div id="inv009DResult" class="col-xs-12" style="padding-left:50px;">
			<div id="inv009DjQgrid">
				<table id="inv009DHistoryGrid"></table>
				<div id="inv009DHistoryPage"></div>
			</div>
		</div>
	</div>
</body>
</html>