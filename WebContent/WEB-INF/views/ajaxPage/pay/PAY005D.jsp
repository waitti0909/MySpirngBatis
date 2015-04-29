<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="decorator"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>雜項請款作業-明細</title>
<script
	src="<s:url value="/resources/plugins/tinymce/tinymce.min.js" />"></script>
<script
	src="<s:url value="/resources/plugins/tinymce/jquery.tinymce.min.js" />"></script>
<script type="text/javascript">
	$(document).ready(function() {	
		mountDisGrid()
		$("#dtlYM").val($('#dtlYM').val().substring(0,4) + "/" + $('#dtlYM').val().substring(4,6));
	});	
	function nullForZero(cellvalue, options, rowObject) {
		var returnValue = cellvalue;
		if (cellvalue == "" || cellvalue == "undefined" || cellvalue == null) {
			returnValue = "0";
		}
		return returnValue;
	}
	function mountDisGrid(){
	$("#disGrid").jqGrid({
				datatype : "local",
				autowidth:'true',
				pager : '#dtlPager',
				url : CONTEXT_URL + "/pay/pay005/searchPayment",
				colNames : [ '雜項請款項目','憑證類別', '憑證號碼', '憑證日期', '統一編號','未稅金額','營業稅','所得稅','健保補充費',
				'應付總額','付款方式','付款對象', '付款名稱', '銀行', '分行','匯款帳號','支票兌現日','戶籍地址','備註',''],
				colModel : [ 
					{name : 'dtlItemDesc',index : 'dtlItemDesc', align : 'center',sortable : false},
				    {name : 'voucherDesc',index : 'voucherDesc' , align : 'center',sortable : false},
				    {name : 'voucher_NBR',index : 'voucher_NBR' , align : 'center',sortable : false}, 
				    {name : 'voucher_DATE',index : 'voucher_DATE', align : 'center',sortable : false, formatter: "date",formatter: function(cellvalue, options, rowObject){
			   			if (cellvalue != null && cellvalue != "") {
				   			var stDate = new Date(cellvalue);
							return stDate.getFullYear() + "/" + (stDate.getMonth() + 1) + "/" + stDate.getDate();
			   			}else {
			   				return "";
			   			}}},
				    {name : 'vat_NUMBER',index : 'vat_NUMBER' , align : 'center',sortable : false}, 
				    {name : 'sumTaxExclusiveTotalAmt',index : 'sumTaxExclusiveTotalAmt' , align : 'center',sortable : false},
				    {name : 'sumTotalBusinessTax',index : 'sumTotalBusinessTax', align : 'center',sortable : false},
				    {name : 'sumTotalIncomeTax',index : 'sumTotalIncomeTax',sortable : false},
				    {name : 'sumTotalNHIAmt',index : 'sumTotalNHIAmt',sortable : false},
				    {name : 'sumAmt',index : 'sumAmt',sortable : false},
				    {name : 'paymentDesc',index : 'paymentDesc',sortable : false},
				    {name : 'payment_USER_ID',index : 'payment_USER_ID', align : 'center',sortable : false},
				    {name : 'payment_USER_NAME',index : 'payment_USER_NAME' , align : 'center',sortable : false}, 
				    {name : 'bankCodeDesc',index : 'bankCodeDesc' , align : 'center',sortable : false},
				    {name : 'branchCodeDesc',index : 'branchCodeDesc', align : 'center',sortable : false},
				    {name : 'account_NBR',index : 'account_NBR',sortable : false},
				    {name : 'check_CASH_DATE',index : 'check_CASH_DATE',sortable : false, formatter: "date",formatter: function(cellvalue, options, rowObject){
			   			if (cellvalue != null && cellvalue != "") {
				   			var stDate = new Date(cellvalue);
							return stDate.getFullYear() + "/" + (stDate.getMonth() + 1) + "/" + stDate.getDate();
			   			}else {
			   				return "";
			   			}}},
				    {name : 'resident_ADDRESS',index : 'resident_ADDRESS',sortable : false},
				    {name : 'memo',index : 'memo',sortable : false},
				    {name : 'payment_REQ_NO',index : 'payment_REQ_NO' , align : 'center',sortable : false,hidden:true}
				],
				caption : "雜項明細",
				rownumbers : true
			});
				$(window).resize(function() {
				$(window).unbind("onresize");
				$("#disGrid,.disGrid").setGridWidth($("#jQgrid").width() - 10);
				$(window).bind("onresize", this);
			}).trigger('resize');
			var data = {
						cashReqNo : $("#dtlCashReqNo").val()
					};
			$("#disGrid").setGridParam({datatype : "json",postData : data,page:1}).trigger("reloadGrid");
	}
	//分攤
		 $('#shareDisBtn').click(function() {	
			shareBtn();
		}); 
	
		function shareBtn(){
		var selr = $("#disGrid").jqGrid("getGridParam", "selrow");
		var share  = "";
		var idTemp = "";
			if (selr == undefined) {
				alert("請選擇一筆資料顯示基站分攤");
				return false;
			}
			var data = $("#disGrid").jqGrid("getRowData", selr);
			//資料送至SP1
			openPageDialogFrame("showSharePag", "/pay/pay005/share",
					"基站分攤", "",data["totalAmt"],data["payment_REQ_NO"],"D");
	}
</script>
</head>
<body>
<div class="box-header">
		<div class="box-name">
			<i class="fa fa-edit"></i> <span>雜項請款作業-明細</span>
		</div>
		<div class="box-icons">
			<p class="expand-link"></p>
		</div>
		<div class="no-move"></div>
	</div>
	<div class="col-xs-12 col-sm-12">
		<div class=" ui-draggable ui-droppable">
			<div class="box-content">
				<form class="form-horizontal">
					<div class="form-group">
						<div class="row">
							<div class="col-md-12">
								<table style="width: 100%">
									<tr>
										<td><label class="col-sm-12 control-label">請款單號：</label></td>
										<td>
											<div class="col-sm-8">
												<input id="dtlCashReqNo" maxlength="20" type="text"
													class="form-control" name="dtlCashReqNo"
													disabled="disabled" value="${master.CASH_REQ_NO}">
											</div>
										</td>
									</tr>
									<tr>
										<td><label class="col-sm-12 control-label">維運區：</label></td>
										<td>
											<div class="col-sm-6">
												<input id="dtlDomain" maxlength="20" type="text"
													class="form-control" name="dtlDomain" disabled="disabled"
													value="${domain.NAME}">
											</div>
										</td>
										<td><label class="col-sm-12 control-label">申請人：</label></td>
										<td><div class="col-sm-6">
												<input id="dtlAppUserName" maxlength="20" type="text"
													class="form-control" name="dtlAppUserName"
													disabled="disabled" value="${appUserName}">
											</div></td>
									</tr>
									<tr>
										<td><label class="col-sm-12 control-label">申請日期：</label></td>
										<td>
											<div class="col-sm-6">
												<input id="dtlAppDate" maxlength="20" type="text"
													class="form-control" name="dtlAppDate" disabled="disabled"
													value="<fmt:formatDate  value="${master.APP_DATE}"  pattern="yyyy/MM/dd"  />">
											</div>
										</td>
										<td><label class="col-sm-12 control-label">請款年月：</label></td>
										<td>
											<div class="col-sm-6">
												<input id="dtlYM" maxlength="20" type="text"
													class="form-control" name="dtlYM" disabled="disabled"
													value="${master.YEAR_MONTH}">
											</div>
										</td>
									</tr>

									<tr>
										<td><div class="clearfix">&nbsp;</div></td>
									</tr>
								</table>
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="row">
							<div class="col-md">
								<label class="col-sm-1 control-label">雜項請款明細：</label>
								<div id="ajaxSearchResult" class="col-sm-12 "
									style="width: 800px;">
									<div id="jQgrid" align="center">
										<table id="disGrid"></table>
										<div id="dtlPager"></div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div id="showSharePag" hidden="true"></div>		
					<div id="showSharePosPag" >
						<div id="selectSharePage"></div>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>