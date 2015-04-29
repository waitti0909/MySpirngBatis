<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>支票申請作廢作業-明細</title>
<script
	src="<s:url value="/resources/plugins/tinymce/tinymce.min.js" />"></script>
<script
	src="<s:url value="/resources/plugins/tinymce/jquery.tinymce.min.js" />"></script>
	<script	src="<s:url value="/resources/js/validate.js" />"></script>
<script type="text/javascript">
	$(document).ready(function() {	
		LoadSelect2Script("<s:url value="/resources/plugins/select2/select2.min.js" />", selectDisDefault);		
				mountGrid();//掛載表格
				var data = {
						disregardNo : $("#disregardNoD").val()
					};
				$("#dGrid").setGridParam({datatype : "json",postData : data}).trigger("reloadGrid");
				$("#disDomain").select2();
			});
			function selectDisDefault() {
			$("#disDomain").select2();
		}
	//掛載表格
		function mountGrid(){
			$("#dGrid").jqGrid({
				datatype : "local",
				autowidth:'true',
				pager : '#dPager',
				url : CONTEXT_URL + "/pay/pay010/searchPayment/",
				colNames : [ '票據號碼','付款對象', '名稱', '金額', '支票兌現日','作廢原因','說明','','','',''],
				colModel : [ 
					{name : 'check_NBR',index : 'check_NBR', align : 'center',sortable : false},
				    {name : 'paymentUserId',index : 'paymentUserId' , align : 'center',sortable : false,formatter: nullForZero},
				    {name : 'paymentUserName',index : 'paymentUserName' , align : 'center',sortable : false}, 
				    {name : 'check_amt',index : 'totalAmt', align : 'center',sortable : false,formatter: nullForZero},
				    {name : 'checkCashDate',index : 'checkCashDate' , align : 'center',sortable : false,formatter: function(cellvalue, options, rowObject){
			   			if (cellvalue != null && cellvalue != "") {
				   			var stDate = new Date(cellvalue);
							return stDate.getFullYear() + "/" + (stDate.getMonth() + 1) + "/" + stDate.getDate() ;
			   			}else {
			   				return "";
			   			}}}, 
				    {name : 'check_DISREGARD_REASON',index : 'check_DISREGARD_REASON' , align : 'center',sortable : false},
				    {name : 'check_DISREGARD_REASON_DESC',index : 'check_DISREGARD_REASON_DESC', align : 'center',sortable : false},
				    {name : 'reasonCode',index : 'reasonCode',sortable : false,hidden:true},
				    {name : 'payment_SEQ_NBR',index : 'payment_SEQ_NBR',sortable : false,hidden:true},
				    {name : 'payment_REQ_NO',index : 'payment_REQ_NO',sortable : false,hidden:true},
				    {name : 'payment_REQ_ITEM_NO',index : 'payment_REQ_ITEM_NO',sortable : false,hidden:true}],
				caption : "支票清單",
				rownumbers : true,
				onSelectRow : function(rowId) {//點一下帶出清單
					var rowData = jQuery(this).getRowData(rowId);
					getPayment(rowData);
				},
				gridComplete : function() {}
			});
			$("#dtlGrid").jqGrid({
				datatype : "local",
				autowidth:'true',
				url : CONTEXT_URL + "/pay/pay010/searchPaymentDtl/",
				colNames : [ '請款單號','租約編號', '名稱', '站點編號', '出租人','出租名稱','付款對象','名稱','付款方式', '合約金額', '本次請款', '未稅金額'
				,'營業稅','所得稅','健保補充費','實付金額'],
				colModel : [ 
					{name : 'cashReqNo',index : 'cashReqNo', align : 'center',sortable : false},
				    {name : 'contractNo',index : 'contractNo' , align : 'center',sortable : false},
				    {name : 'lsName',index : 'lsName' , align : 'center',sortable : false}, 
				    {name : 'location_ID',index : 'location_ID', align : 'center',sortable : false},
				    {name : 'lessorId',index : 'lessorId' , align : 'center',sortable : false}, 
				    {name : 'lessorName',index : 'lessorName' , align : 'center',sortable : false},
				    {name : 'payment_USER_ID',index : 'payment_USER_ID', align : 'center',sortable : false},
				    {name : 'payment_USER_NAME',index : 'payment_USER_NAME', align : 'center',sortable : false},
				    {name : 'paymethodName',index : 'paymethodName', align : 'center',sortable : false},
				    {name : 'payAmt',index : 'payAmt' , align : 'center',sortable : false},
				    {name : 'sumExclusive',index : 'sumExclusive' , align : 'center',sortable : false}, 
				    {name : 'tax_EXCLUSIVE_TOTAL_AMT',index : 'tax_EXCLUSIVE_TOTAL_AMT', align : 'center',sortable : false},
				    {name : 'total_BUSINESS_TAX',index : 'total_BUSINESS_TAX' , align : 'center',sortable : false}, 
				    {name : 'total_INCOME_TAX',index : 'total_INCOME_TAX' , align : 'center',sortable : false},
				    {name : 'total_NHI_AMT',index : 'total_NHI_AMT', align : 'center',sortable : false},
				    {name : 'sumAllAmt',index : 'sumAllAmt', align : 'center',sortable : false}],
				caption : "支票明細",
				rownumbers : true
			});
			$(window).resize(function() {
				$(window).unbind("onresize");
				$("#dGrid,.dGrid").setGridWidth($("#jQgrid").width() - 10);
				$("#dtlGrid,.dtlGrid").setGridWidth($("#jQgrid").width() - 10);
				$(window).bind("onresize", this);
			}).trigger('resize');	
		}	
		function getPayment(rowData) {
			var data = {
				reqNo : rowData.payment_REQ_NO,
				reqItemNo : rowData.payment_REQ_ITEM_NO,
				seqNo : rowData.payment_SEQ_NBR
			};
			$("#dtlGrid").setGridParam({datatype:"json", postData:data,page:1}).trigger("reloadGrid");
		}	
	function nullForZero(cellvalue, options, rowObject) {
		var returnValue = cellvalue;
		if (cellvalue == "" || cellvalue == "undefined" || cellvalue == null) {
			returnValue = "0";
		}
		return returnValue;
	}
</script>
</head>
<body>
<div class="row">
	</div>
	<div class="box-header">
		<div class="box-name">
			<i class="fa fa-edit"></i> <span>支票作業-明細</span>
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
					<form class="form-horizontal" id="bBoardAddEdit" />
					<div class="form-group">
						<div class="row">
							<div class="col-md-12">
									<table style="width: 100%">
									<tr>
										<td><label class="col-sm-12 control-label">作廢單號：</label></td>
										<td><div class="col-sm-6">
												<input id="disregardNoD" type="text" disabled
													class="form-control" name="disregardNoD" value="${disregardNo}">
											</div></td>
										<td><label class="col-sm-12 control-label">維運區：</label></td>
										<td>
											<div class="col-sm-6">
												<select id="disDomain" name="disDomain" disabled>
													<option value="" selected disabled>--請選擇--</option>
													<c:forEach items="${domainSelect}" var="addDomain">
														<c:choose>
															<c:when test="${domain eq addDomain.ID}">
																<option value="${addDomain.ID}" selected>${addDomain.NAME}</option>
															</c:when>
															<c:otherwise>
																<option value="${addDomain.ID}">${addDomain.NAME}</option>
															</c:otherwise>
														</c:choose>
													</c:forEach>
												</select>
											</div>
										</td>
									</tr>
									<tr>
										<td><div class="clearfix">&nbsp;</div></td>
									</tr>
									<tr>
										<td><label class="col-sm-12 control-label">申請人：</label></td>
										<td><div class="col-sm-6">
												<input id="appUserName" type="text" disabled
													class="form-control" name="appUserName" value="${appUserName}">
											</div></td>
										<td><label class="col-sm-12 control-label">申請日期：</label></td>
										<td>
											<div class="col-sm-6">
												<input id="appDate" type="text" disabled
													class="form-control" name="appDate" class="form-control"
													value="<fmt:formatDate  value="${appDate}"  pattern="yyyy/MM/dd"  />">
											</div>
										</td>
									</tr>
								</table>	
								<tr>
									<td><div class="clearfix">&nbsp;</div></td>
								</tr>
								<div id="ajaxSearchResult" class="col-xs-12">
									<div id="jQgrid" align="center">
										<table id="dGrid"></table>
										<div id="dPager"></div>
									</div>
								</div>
								<tr>
									<td><div class="clearfix">&nbsp;</div></td>
								</tr>
								<div id="ajaxSearchResult" class="col-xs-12">
									<div id="jQgrid" align="center">
										<table id="dtlGrid"></table>
									</div>
								</div>
							</div>
						</div>
						<hr>
					</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>