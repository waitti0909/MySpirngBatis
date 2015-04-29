<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">

<head>
	
	<script type="text/javascript">
	
		$(document).ready(function() {
			
			//import Select2
			LoadSelect2Script('<s:url value="/resources/plugins/select2/select2.min.js" />',mountSelect2);
			
			//掛載時間
			mountDate();
			
			//掛載按鈕事件
			mountBtnEvent();
			
			//掛載表格
			mountGrid();
		});
		
		//掛載表格
		function mountGrid(){
			$("#detailGridBy015L").jqGrid({
				datatype : "local",
				pager : '#detailGridBy015Lpager',
				autowidth:'true',
				url : CONTEXT_URL + "/inv/inv015L/searchCallInData",
				colNames : [ '收料單號', '收料倉庫', '收料人員', '收料日期'],
				colModel : [ {name : 'txn_no',index : 'txn_no',align : 'center',sortable : false}, 
				             {name : 'wh_name',index : 'wh_name',align : 'center',sortable : false}, 
				             {name : 'appUserName',index : 'appUserName',align : 'center',sortable : false}, 
				             {name : 'crTime',index : 'crTime',align : 'center',sortable : false}       
				             ],
				caption : "收料清單",
				rownumbers : true,
				onSelectRow : function(id) {
					var row = jQuery(this).getRowData(id);
					var json = {'txnNo':row.txn_no};
					$("#historyGridBy015L").setGridParam({datatype:"json",postData:json, mtype: 'POST',page:1}).jqGrid().trigger("reloadGrid");
				}
			});
			
			$("#historyGridBy015L").jqGrid({
				datatype : "local",
				pager : '#historyGridBy015Lpager',
				autowidth:'true',
				url : CONTEXT_URL + "/inv/inv015L/searchCallInHistoryDetail",
				colNames : [ '退料單號', '料號','品名','廠商序號','貼標號碼','退料狀態','退料原因','預計收料數', '實際收料狀態', '收料原因', '收料數'],
				colModel : [ {name : 'ref_act_no',index : 'ref_act_no',align : 'center',sortable : false}, 
				             {name : 'mat_no',index : 'mat_no',align : 'center',sortable : false}, 
				             {name : 'matName',index : 'matName',align : 'center',sortable : false}, 
				             {name : 'invSrl.ven_sn',index : 'ven_sn',align : 'center',sortable : false}, 
				             {name : 'invSrl.tag_no',index : 'tag_no',align : 'center',sortable : false}, 
				             {name : 'returnMatStatus',index : 'returnMatStatus',align : 'center',sortable : false} ,
				             {name : 'returnMatReason',index : 'returnMatReason',align : 'center',sortable : false},
				             {name : 'invRtD.qty',index : 'qty',align : 'center',sortable : false}, 
				             {name : 'callInMatStatus',index : 'callInMatStatus',align : 'center',sortable : false} ,
				             {name : 'callInMatReason',index : 'callInMatReason',align : 'center',sortable : false}, 
				             {name : 'qty',index : 'qty',align : 'center',sortable : false}   
				             ],
				caption : "收料明細",
				rownumbers : true
			});
	
			$(window).resize(function() {
				$(window).unbind("onresize");
				$("#detailGridBy015L,.grid").setGridWidth($("#jQgrid").width() - 10);
				$("#historyGridBy015L,.grid").setGridWidth($("#jQgrid").width() - 10);
				$(window).bind("onresize", this);
			}).trigger('resize');
		}
		
		//掛載按鈕事件
		function mountBtnEvent(){
			//查詢按鈕
			$('#callInSearchButton').click(function(){
				var wareHouse = $("#callInDepot").find("option:selected").val();
				var txnNo=$("#callInNumber").val();
				/* if($('#callInUser').val() == ""){
					
					alert('請選擇收料人員');
					return ;
				} */
				if(txnNo == ""){
				if( wareHouse == "" || wareHouse == null){
				alert("請選擇收料倉庫");
				return false;
				}
				dateCheck($("#STR_demandDate").val(), $("#END_demandDate").val(),"收料日期起始日不可大於結束");
				var data ={
							'txnNo' : "",
							'whId' : wareHouse,
							'strDate' : $("#STR_demandDate").val(),
							'endDate' : $("#END_demandDate").val(),
						};
				$("#detailGridBy015L").setGridParam({datatype : "json",postData : data,page:1}).trigger("reloadGrid");
			}else{
				 var data ={
							'txnNo' : txnNo,
							'whId' : "",
							'strDate' : "",
							'endDate' : "",
						};
				$("#detailGridBy015L").setGridParam({datatype : "json",postData : data,page:1}).trigger("reloadGrid");
			}
				/* if(checkDateStrRange($("#STR_demandDate").val(), $("#END_demandDate").val())) {
					
					var data ={
							'txnNo' : $('#callInNumber').val(),
							'whId' : $('#callInDepot').val(),
							'createUser' : $('#callInUser').val(),
							'strDate' : $("#STR_demandDate").val(),
							'endDate' : $("#END_demandDate").val(),
						};
					
					$("#detailGridBy015L").setGridParam({datatype : "json",postData : data,page:1}).trigger("reloadGrid");
				}else{
					
					alert('起始日不可大於結束');
				} */
			});
			
			$('#print').click(function(){
				
				var rowId = $('#detailGridBy015L').jqGrid('getGridParam', 'selrow');
				
				if(rowId == null){
					alert("請選擇一筆資料。");
					return ;
				}
				
				var cellValue = $('#detailGridBy015L').jqGrid('getCell', rowId, 'txn_no');
				
				$(this).attr("href", CONTEXT_URL+"/inv/inv015L/printReceipt?txnNo="+cellValue);
			});
			
		}
		
		//掛載 Select2
		function mountSelect2(){
			$('#callInDepot').select2();
			$('#callInDept').select2().on('change',function(e){
				mountApplicant(e.val,"callInUser");
			});
			$('#callInUser').select2();
		}
		
		//掛載申請人
		function mountApplicant(e,id){
			var data ={dept : e };
			$.ajax({			
				url : CONTEXT_URL+"/ajax/inv/public/personnelDept/",		
				data:data,
				type : "POST",
				dataType : 'json',
				success : function(data) {
					if(data.success){
						if(data.rows.length > 0){
							$("#"+id+" option").remove();
							$("#"+id).append("<option value=''>-- 請選擇 --</option>");
							for(var i=0; i<data.rows.length; i++){		
								$("#"+id).append("<option value="+data.rows[i].psn_NO+">"+data.rows[i].chn_NM+"</option>");
							}
							$("#"+id).select2("val","");
						}
					}
				}
			});
		}
		
		//掛載時間
		function mountDate(){
			
			//起始日日曆						
			$('#STR_demandDate').datepicker({
				dateFormat : "yy/mm/dd",
				changeYear : true,
				changeMonth : true,
				showButtonPanel : true
			});
			//結束日日曆
			$('#END_demandDate').datepicker({
				dateFormat : "yy/mm/dd",
				changeYear : true,
				changeMonth : true,
				showButtonPanel : true,
				showTime : false
			});
			
			var today = new Date();
			var str = today.getFullYear() + '/' +(today.getMonth()+1)+ '/' + today.getDate() ;
			$('#END_demandDate').val(str);
			
			today.setDate(today.getDate()-7);
			str = today.getFullYear() + '/' +(today.getMonth()+1)+ '/' + today.getDate() ;
			$('#STR_demandDate').val(str);
		}
	</script>
</head>

<body>
	
	<div class="box-header">
		<div class="box-name">
			<i class="fa fa-search"></i>退料收料作業功能 收料單查詢條件
		</div>
		<div class="no-move"></div>
	</div>
	<div class="row">
		<div id="breadcrumb" class="col-md-12">
			<ol class="breadcrumb">
				<div class="col-sm-12" id="btnDiv">
					<button class="btn btn-primary btn-label-left" type="button"
						id="callInSearchButton" >
						<span>
							<i class="fa fa-save"></i>
						</span> 
						查詢
					</button>
					<a id='print'>
						<button class="btn btn-primary btn-label-left" type="button"
							id="printReceiptButton" >
							<span>
								<i class="fa fa-save"></i>
							</span> 
							列印簽收單
						</button>
					</a>
				</div>
			</ol>
		</div>
	</div>
	
	<div class="row">
		<div class="box-content">
			<form role="form" class="form-horizontal bootstrap-validator-form"
				novalidate="novalidate" id="searchForm">
				<div class="form-group">
					<table style="width: 95%">
						<tr>
							<td class="col-sm-2 control-label">
								<label>收料單號 :</label>
							</td>
							<td class="col-sm-3">
								<input id="callInNumber" type="text" class="form-control">
							</td>
							<td class="col-sm-1"/>
							<td class="col-sm-2 control-label">
								<label>收料倉庫：</label>
							</td>
							<td class="col-sm-3">
								<select id="callInDepot">
									<option value="">-- 請選擇 --</option>
									<c:forEach items="${callInDepot}" var="depot">
										<option value="${depot.wh_id}">${depot.wh_name}</option>
									</c:forEach>
								</select>
							</td>
							<td  class="col-sm-1"/>
						</tr>
						<tr>
							<td><div class="clearfix">&nbsp;</div></td>
						</tr>
						<%-- <tr>
							<td class="col-sm-2 control-label">
								<label>收料單位 :</label>
							</td>
							<td class="col-sm-3">
								<select id="callInDept">
									<option value="">-- 請選擇 --</option>
									<c:forEach items="${department}" var="dept">
										<option value="${dept.DEPT_ID}">${dept.DEPT_NAME}</option>
									</c:forEach>
								</select>
							</td>
							<td class="col-sm-1"/>
							<td class="col-sm-2 control-label">
								<span class="s">* </span>
								<label>收料人員：</label>
							</td>
							<td class="col-sm-3">
								<select id="callInUser">
									<option value="">-- 請選擇 --</option>
								</select>
							</td>
							<td class="col-sm-1"/>
						</tr> --%>
						<tr>
							<td><div class="clearfix">&nbsp;</div></td>
						</tr>
						<tr>
							<td class="col-sm-2 control-label">
								<span class="s">* </span>
								<label>收料日期 :</label>
							</td>
							<td class="col-sm-3" colspan="2">
								<div>
									<input id="STR_demandDate" style="width:115px;" type="text" placeholder="需求起始日" readonly="readonly">
									 至 
									<input id="END_demandDate" style="width:115px;" type="text" placeholder="需求結束日" readonly="readonly">
								</div>
							</td>
							<td class="col-sm-1"/>
							<td class="col-sm-2"/>
							<td class="col-sm-3"/>
							<td class="col-sm-1"/>
						</tr>
					</table>
				</div>
			</form>
		</div>

		<div id="ajaxSearchResult" class="col-xs-12" style="padding-left:50px;">
			<div id="jQgrid">
				<table id="detailGridBy015L"></table>
				<div id="detailGridBy015Lpager"></div>
			</div>
		</div>

		<div class="clearfix">&nbsp;</div>

		<div id="ajaxSearchResult" class="col-xs-12" style="padding-left:50px;">
			<div id="jQgrid">
				<table id="historyGridBy015L"></table>
				<div id="historyGridBy015Lpager"></div>
			</div>
		</div>
	</div>

</body>
</html>