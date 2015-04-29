<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<script type="text/javascript">
		$(document).ready(function() {
			//掛載表格
			mountGrid();
		});
		
		//掛載表格
		function mountGrid(){
			$("#gridByView").jqGrid({
				datatype : "local",
				pager : '#gridByViewpager',
				autowidth:'true',
				url : CONTEXT_URL + "/inv/inv015D/searchReturnDetail",
				colNames : [ '料號', '品名', '貼標號碼','廠商序號', '物料狀態', '預計收料數', '已收料數','','','','' ],
				colModel : [ {name : 'mat_NO',index : 'mat_NO',align : 'center',sortable : false}, 
				             {name : 'matName',index : 'matName',align : 'center',sortable : false}, 
				             {name : 'tagNo',index : 'tagNo',align : 'center',sortable : false}, 
				             {name : 'venSn',index : 'venSn',align : 'center',sortable : false}, 
				             {name : 'matStatusDscr',index : 'matStatusDscr',align : 'center',sortable : false},
				             {name : 'qty',index : 'qty',align : 'center',sortable : false,formatter: nullForZero} ,
				             {name : 'rtQty',index : 'rtQty',align : 'center',sortable : false,formatter: nullForZero},
				             {name : 'mat_status',index : 'mat_status',align : 'center',sortable : false,hidden:true} ,
				             {name : 'wh_ID',index : 'wh_ID',align : 'center',sortable : false,hidden:true} ,
				             {name : 'optId',index : 'optId',align : 'center',sortable : false,hidden:true}  ,
				             {name : 'dtl_SEQ',index : 'dtl_SEQ',align : 'center',sortable : false,hidden:true}      
				             ],
				caption : "退料明細",
				rownumbers : true,
				onSelectRow : function(id) {
				var rowData = jQuery(this).getRowData(id);
				var data = {
						optId : rowData.optId,
						matNo : rowData.mat_NO,
						dtlSeq : rowData.dtl_SEQ
					};
					$("#historyGridByView").setGridParam({datatype:"json", postData:data, mtype: 'POST',page:1}).jqGrid().trigger("reloadGrid");
				},
				gridComplete : function() {
					// do something after grid loaded
				}
			});
			
			$("#historyGridByView").jqGrid({
				datatype : "local",
				pager : '#historyGridByViewpager',
				autowidth:'true',
				url : CONTEXT_URL + "/inv/inv015/searchDtlHistory",
				colNames : [ '收料單號', '收料日期', '收料人員','收料數'],
				colModel : [ {name : 'txn_no',index : 'txn_no',align : 'center',sortable : false}, 
				             {name : 'cr_time',index : 'cr_time',align : 'center',sortable : false,formatter: function(cellvalue, options, rowObject){
					   			if (cellvalue != null && cellvalue != "") {
						   			var stDate = new Date(cellvalue);
									return stDate.getFullYear() + "/" + (stDate.getMonth() + 1) + "/" + stDate.getDate()+" " + addZero(stDate.getHours()) +":"+addZero(stDate.getMinutes());
					   			}else {
					   				return "";
					   			}}}, 
				             {name : 'crUser',index : 'crUser',align : 'center',sortable : false}, 
				             {name : 'qty',index : 'qty',align : 'center',sortable : false,formatter: nullForZero}
				             ],
				caption : "收料歷程",
				rownumbers : true,
				onSelectRow : function() {
					// do something when select a row
				},
				gridComplete : function() {
					// do something after grid loaded
				}
			});
			
			$(window).resize(function() {
				$(window).unbind("onresize");
				$("#gridByView,.grid").setGridWidth($("#jQgrid").width() - 10);
				$("#historyGridByView,.grid").setGridWidth($("#jQgrid").width() - 10);
				$(window).bind("onresize", this);
			}).trigger('resize');
		}	
		function nullForZero (cellvalue, options, rowObject)
			{
			   var returnValue=cellvalue;
			   if(cellvalue == "" || cellvalue == "undefined" || cellvalue == null){
			   			returnValue="0";
			   }
			   return returnValue;
			}
		function addZero(i) {
			if (i < 10) {
				i = "0" + i;
			}
			return i;
		}
	</script>
</head>
<body>

	<div class="box-header">
		<div class="box-name">
			<i class="fa fa-edit"></i> <span>退料單明細</span>
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
			<form role="form" class="form-horizontal"
				novalidate="novalidate" id="searchForm">
				<div class="form-group">
						<%-- <tr>
							<td class="col-sm-2 control-label"><label>退料申請單號 :</label></td>
							<td class="col-sm-3"><label>${masterDscr.optId}</label></td>
							<td class="col-sm-1"></td>
							<td class="col-sm-2 control-label"><label>收料狀態：</label></td>
							<td class="col-sm-3"><label>${masterDscr.statusDscr}</label></td>
							<td class="col-sm-1"></td>
						</tr>
						<tr>
							<td><div class="clearfix">&nbsp;</div></td>
						</tr>
						<tr>
							<td class="col-sm-2 control-label"><label>申請單位 :</label></td>
							<td class="col-sm-3"><label>${masterDscr.deptIdDscr}</label></td>
							<td class="col-sm-1"></td>
							<td class="col-sm-2 control-label"><label>申請人：</label></td>
							<td class="col-sm-3"><label>${masterDscr.appUser}</label></td>
							<td class="col-sm-1"></td>
						</tr>
						<tr>
							<td><div class="clearfix">&nbsp;</div></td>
						</tr>
						<tr>
							<td class="col-sm-2 control-label"><label>站台 :</label></td>
							<td class="col-sm-3"><label>${masterDscr.siteIdDscr}</label></td>
							<td class="col-sm-1"/>
							<td class="col-sm-2 control-label"/>
							<td class="col-sm-3"/>
							<td class="col-sm-1"/>
						</tr>
						<tr>
							<td><div class="clearfix">&nbsp;</div></td>
						</tr> --%>
						<div class="form-group">
							<div class="row">
								<label class="col-sm-2 control-label">退料申請單號 :</label>
								<div class="col-sm-2">
									<input id="optId" maxlength="20" type="text"
										class="form-control" name="optId" disabled="disabled"
										value="${masterDscr.optId}">
								</div>
								<label class="col-sm-1 control-label">收料狀態：</label>
								<div class="col-sm-2">
									<input id="statusDscr" maxlength="20" type="text"
										class="form-control" name="statusDscr" disabled="disabled"
										value="${masterDscr.statusDscr}">
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="row">
								<label class="col-sm-2 control-label">申請單位：</label>
								<div class="col-sm-2">
									<input id="deptIdDscr" maxlength="20" type="text"
										class="form-control" name="deptIdDscr" disabled="disabled"
										value="${masterDscr.deptIdDscr}">
								</div>
								<label class="col-sm-1 control-label">申請人：</label>
								<div class="col-sm-2">
									<input id="appUser" maxlength="20" type="text"
										class="form-control" name="appUser" disabled="disabled"
										value="${masterDscr.appUser}">
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="row">
								<label class="col-sm-2 control-label">站台：</label>
								<div class="col-sm-2">
									<input id="siteIdDscr" maxlength="20" type="text"
										class="form-control" name="siteIdDscr" disabled="disabled"
										value="${masterDscr.siteIdDscr}">
								</div>
							</div>
						</div>
				</div>
			</form>
		</div>

		<div id="ajaxSearchResult" class="col-xs-12" >
			<div id="jQgrid">
				<table id="gridByView"></table>
				<div id="gridByViewpager"></div>
			</div>
		</div>
		
		<div class="clearfix">&nbsp;</div>

		<div id="ajaxSearchResult" class="col-xs-12" >
			<div id="jQgrid">
				<table id="historyGridByView"></table>
				<div id="historyGridByViewpager"></div>
			</div>
		</div>
		</div>
		</div>
	</div>
	
</body>
</html>