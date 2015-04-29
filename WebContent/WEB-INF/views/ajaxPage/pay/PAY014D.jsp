<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>假扣押作業-明細</title>
<script
	src="<s:url value="/resources/plugins/tinymce/tinymce.min.js" />"></script>
<script
	src="<s:url value="/resources/plugins/tinymce/jquery.tinymce.min.js" />"></script>
<script type="text/javascript">


	$(document).ready(function() {
			$("#dGrid").jqGrid({
				datatype : "local",
				pager : '#dtlPager',
				autowidth:'true',
				url : CONTEXT_URL + "/pay/pay014/searchUser/",
				colNames : [ '付款對象', '名稱', '付款方式', '銀行','分行','帳號','扣押總額','付款比例','付款期間起','付款期間迄','支票寄送地址' ],
				colModel : [ 
					{name : 'payment_USER_ID',index : 'payment_USER_ID' , align : 'center',sortable : false},
				    {name : 'payment_USER_NAME',index : 'payment_USER_NAME' , align : 'center',sortable : false},
				    {name : 'payMethodName',index : 'payMethodName' , align : 'center',sortable : false}, 
				    {name : 'bankCodeDesc',index : 'bankCodeDesc', align : 'center',sortable : false},
				    {name : 'bankBranchDesc',index : 'bankBranchDesc' , align : 'center',sortable : false}, 
				    {name : 'account_NBR',index : 'account_NBR' , align : 'center',sortable : false},
				    {name : 'attach_TAX_INCLUSIVE_AMT',index : 'attach_TAX_INCLUSIVE_AMT', align : 'center',sortable : false},
	   			 	{name : 'payment_PROPORTION',index : 'payment_PROPORTION' , align : 'center',sortable : false}, 
	   			 	{name : 'payment_BEGIN_DATE',index : 'payment_BEGIN_DATE' , align : 'center',sortable : false,
				    	formatter: function(cellvalue, options, rowObject){
				   			if (cellvalue != null && cellvalue != "") {
					   			var stDate = new Date(cellvalue);
								return stDate.getFullYear() + "/" + (stDate.getMonth() + 1) + "/" + stDate.getDate();
				   			}else {
				   				return "";
				   			}
			   			}}, 
	   			 	{name : 'payment_END_DATE',index : 'payment_END_DATE',sortable : false, align : 'center',
				    	formatter: function(cellvalue, options, rowObject){
				   			if (cellvalue != null && cellvalue != "") {
					   			var stDate = new Date(cellvalue);
								return stDate.getFullYear() + "/" + (stDate.getMonth() + 1) + "/" + stDate.getDate();
				   			}else {
				   				return "";
				   			}
			   			}},
			   			{name : 'check_ADDR',index : 'check_ADDR' , align : 'center',sortable : false}],
				caption : "付款對象明細表",
				rownumbers : true,
				onSelectRow : function() {},
				gridComplete : function() {},
				ondblClickRow: function(rowId) {}
			});								
				$(window).resize(function() {
							$(window).unbind("onresize");
							$("#dGrid,.dGrid").setGridWidth($("#jQgrid").width());
							$(window).bind("onresize", this);
					}).trigger('resize');
					
					var data = {
						docNo : $("#dtlDocNo").val()
					};
				$("#dGrid").setGridParam({datatype : "json",postData : data}).trigger("reloadGrid");
				$("#addDomain").select2();
			});
			function nullForZero (cellvalue, options, rowObject)
			{
			   var returnValue=cellvalue;
			   if(cellvalue == "" || cellvalue == "undefined" || cellvalue == null){
			   			returnValue="0";
			   }
			   return returnValue;
			}
			
</script>
</head>
<body>
	<div class="box-header">
		<div class="box-name">
			<i class="fa fa-edit"></i> <span>假扣押作業-明細</span>
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
							<label class="col-sm-2 control-label">維運區：</label>
							<div class="col-sm-2">
								 <select id="addDomain" name="addDomain" disabled>
									<option value="" selected>--請選擇--</option>
									<c:forEach items="${domainSelect}" var="addDomain">
										<c:choose>
											<c:when test="${master.DOMAIN eq addDomain.ID}">
												<option value="${addDomain.ID}" selected>${addDomain.NAME}</option>
											</c:when>
											<c:otherwise>
												<option value="${addDomain.ID}">${addDomain.NAME}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
							</div>
							<label class="col-sm-2 control-label">文案編號：</label>
							<div class="col-sm-2">
								<input id="dtlDocNo" maxlength="20" type="text"
									class="form-control" name="dtlDocNo" disabled="disabled"
									value="${master.DOCUMENT_NO}">
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="row">
							<label class="col-sm-2 control-label">扣押期間：</label>
							<div class="col-sm-2">
								<input id="attachStartDate" maxlength="20" type="text"
									class="form-control" name="attachStartDate" disabled="disabled"
									value="<fmt:formatDate  value="${master.ATTACH_BEGIN_DATE}"  pattern="yyyy/MM/dd"  />">
							</div>
							<div class="col-sm-2">至</div>
							<div class="col-sm-2">
								<input id="attachEndDate" maxlength="20" type="text"
									class="form-control" name="attachEndDate" disabled="disabled"
									value="<fmt:formatDate  value="${master.ATTACH_END_DATE}"  pattern="yyyy/MM/dd"  />">
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="row">
							<label class="col-sm-2 control-label">扣押對象：</label>
							<div class="col-sm-2">
								<input id="attachUser" maxlength="20" type="text"
									class="form-control" name="attachUser" disabled="disabled"
									value="${master.ATTACH_USER_NAME}">
							</div>
							<label class="col-sm-2 control-label">扣押總額：</label>
							<div class="col-sm-2">
								<input id="attachAmt" type="text" disabled="disabled"
									name="attachAmt" class="form-control"
									value="<fmt:formatNumber value="${master.ATTACH_TAX_INCLUSIVE_TOTAL_AMT}" pattern="#" type="number"/>">
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="row">
							<label class="col-sm-2 control-label">每月最多扣款上限：</label>
							<div class="col-sm-2">
								<input id="maxAmt" maxlength="20" type="text"
									class="form-control" name="maxAmt" disabled="disabled"
									value="<fmt:formatNumber value="${master.CREDIT_MAX_AMT_PER_PR}" pattern="#" type="number"/>">
							</div>
							<label class="col-sm-2 control-label">指定付款：</label>
							<div class="col-sm-2">
								<c:choose>
									<c:when test="${master.IF_HAVE_PAYMENT_USER eq Y}">
											<input id="payCheck" maxlength="20" type="text"
								class="form-control" name="payCheck" disabled="disabled"
								value="是">
									</c:when>
									<c:otherwise>
											<input id="payCheck" maxlength="20" type="text"
								class="form-control" name="payCheck" disabled="disabled"
								value="是">
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="row">
							<div class="col-md">
								<label class="col-sm-2 control-label">說明：</label>
								<div class="col-sm-9">
									<textarea class="form-control" rows="4" id=memo name="memo" disabled
										>${master.MEMO}</textarea>
								</div>
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="row">
							<label class="col-sm-2 control-label">申請人：</label>
							<div class="col-sm-2">
								<input id="appUser" maxlength="20" type="text"
									class="form-control" name="appUser" disabled="disabled"
									value="${appUserName}">
							</div>
							<label class="col-sm-2 control-label">申請日期：</label>
							<div class="col-sm-2">
								<input id="appDate" maxlength="20" type="text"
									class="form-control" name="appDate" disabled="disabled"
									value="<fmt:formatDate  value="${master.APP_DATE}"  pattern="yyyy/MM/dd HH:mm"  />">
							</div>
						</div>
					</div>					
						<div class="form-group">
							<div class="row">
								<div class="col-md">
									<label class="col-sm-2 control-label">假扣押明細：</label>
									<div id="ajaxSearchResult" class="col-sm-12 "
										style="width: 1100px;">
										<div id="jQgrid" align="center">
											<table id="dGrid"></table>
											<div id="dtlPager"></div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>