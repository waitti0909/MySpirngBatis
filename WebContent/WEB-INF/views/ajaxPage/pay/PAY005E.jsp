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
<title>雜項請款作業-修改</title>
<script
	src="<s:url value="/resources/plugins/tinymce/tinymce.min.js" />"></script>
<script
	src="<s:url value="/resources/plugins/tinymce/jquery.tinymce.min.js" />"></script>
	<script	src="<s:url value="/resources/js/validate.js" />"></script>
<script type="text/javascript">
	$(document).ready(function() {	
		LoadSelect2Script("<s:url value="/resources/plugins/select2/select2.min.js" />", selectAddDefault);		
				addMountButEvent();//啟動按鈕事件
				mountGrid();//掛載表格
				selectAddDefault();//下拉選單格式
			});
			
		//掛載表格
		function mountGrid(){
			$("#addGrid").jqGrid({
				datatype : "local",
				autowidth:'true',
				url : CONTEXT_URL + "/pay/pay005/searchPayment",
				colNames : [ '雜項請款項目','憑證類別', '憑證號碼', '憑證日期', '統一編號','未稅金額','營業稅','所得稅','健保補充費','應付總額','付款方式',
				'付款對象', '付款名稱', '銀行', '分行','匯款帳號','支票兌現日','戶籍地址','備註','','','','','','',''],
				colModel : [ 
					{name : 'dtlItemDesc',index : 'dtlItemDesc', align : 'center',sortable : false},
				    {name : 'voucherDesc',index : 'voucherDesc' , align : 'center',sortable : false},
				    {name : 'voucher_NBR',index : 'voucher_NBR' , align : 'center',sortable : false}, 
				    {name : 'voucher_DATE',index : 'voucher_DATE', align : 'center',sortable : false, formatter: function(cellvalue, options, rowObject){
				   			if (cellvalue != null && cellvalue != "") {
					   			var stDate = new Date(cellvalue);
								return stDate.getFullYear() + "/" + (stDate.getMonth() + 1) + "/" + stDate.getDate() ;
				   			}else {
				   				return "";
				   			}
			   			}},
				    {name : 'vat_NUMBER',index : 'vat_NUMBER' , align : 'center',sortable : false}, 
				   /*  {name : 'tax_EXCLUSIVE_TOTAL_AMT',index : 'tax_EXCLUSIVE_TOTAL_AMT' , align : 'center',sortable : false},
				    {name : 'total_BUSINESS_TAX',index : 'total_BUSINESS_TAX', align : 'center',sortable : false},
				    {name : 'total_INCOME_TAX',index : 'total_INCOME_TAX',sortable : false},
				    {name : 'total_NHI_AMT',index : 'total_NHI_AMT',sortable : false},
				    {name : 'totalAmt',index : 'totalAmt',sortable : false}, */
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
				    {name : 'voucher_TYPE',index : 'voucher_TYPE' , align : 'center',sortable : false,hidden:true},
				    {name : 'checkShare',index : 'checkShare' , align : 'center',sortable : false,hidden:true},
				    {name : 'payment_METHOD',index : 'payment_METHOD' , align : 'center',sortable : false,hidden:true},
				    {name : 'bank_CODE',index : 'bank_CODE' , align : 'center',sortable : false,hidden:true},
				    {name : 'bank_BRANCH_CODE',index : 'bank_BRANCH_CODE' , align : 'center',sortable : false,hidden:true},
				    {name : 'voucherSeqNbr',index : 'voucherSeqNbr' , align : 'center',sortable : false,hidden:true},
				    {name : 'payment_REQ_NO',index : 'payment_REQ_NO' , align : 'center',sortable : false,hidden:true}
				],
				caption : "雜項清單",
				multiselect : true,
				rownumbers : true,
				onSelectRow : function(rowId) {},
				gridComplete : function() {}
			});
				$(window).resize(function() {
				$(window).unbind("onresize");
				$("#addGrid,.addGrid").setGridWidth($("#jQgrid").width() - 10);
				$(window).bind("onresize", this);
			}).trigger('resize');
			var data = {
				cashReqNo : $("#editCashReqNo").val()
			};
			$("#addGrid").setGridParam({datatype : "json",postData : data,page:1}).trigger("reloadGrid");	
		}		
	function nullForZero(cellvalue, options, rowObject) {
		var returnValue = cellvalue;
		if (cellvalue == "" || cellvalue == "undefined" || cellvalue == null) {
			returnValue = "0";
		}
		return returnValue;
	}
	// 開啟下拉式選單搜尋文字功能
	function selectAddDefault() {
		$("#adddomainSelect").select2();
	}
	
	function delGrid() {
		var selr = jQuery('#addGrid').jqGrid('getGridParam', 'selarrrow');
		if (!selr.length == 0) {
			if (confirm("是否確定要刪除")) {
			//刪除暫存
			for (var i = 0; i < selr.length; i++) {
				share = jQuery('#addGrid').jqGrid('getRowData', selr[i]);
				var delKey = {
						"paymentReqNo" : share.payment_REQ_NO,
						"vouSeqNo" : share.voucherSeqNbr,
						"cashReqNo" :  $("#editCashReqNo").val()
					};
				delList.push(delKey);
			}
				for (var i = selr.length - 1; i >= 0; i--) {
					$('#addGrid').jqGrid("delRowData", selr[0]);
				}
			}
		} else {
			alert("未勾選要刪除的資料列,請重新操作!");
		}
	}
	function saveCheck(){
		var selr = jQuery('#addGrid').jqGrid('getGridParam', 'selarrrow');
			for (var i = 0; i < selr.length; i++) {
				share = jQuery('#addGrid').jqGrid('getRowData', selr[i]);
				if(share.checkShare == ""){
					alert("雜項明細編號："+selr[i]+"請做基站分攤!");
					return false;
				}
			}	
		return true;
	}
	//修改
	function editAll(status) {
		var selr = jQuery('#addGrid').jqGrid('getGridParam', 'selarrrow');
		var appUser = $("#appUserSelect").find("option:selected").val();
		var Today = new Date($('#addAppDate').val());
		var appDate=Today.getFullYear() + "-" + (Today.getMonth()+1) + "-"
					+ (Today.getDate());	
		if (!selr.length == 0) {
			var ids = jQuery("#addGrid").jqGrid('getDataIDs');
			for (var i = 0; i < ids.length; i++) {
				rowId = ids[i];
				var rowNum=i+1;
				var rowData = jQuery('#addGrid').jqGrid('getRowData', rowId);
				if (rowData.checkShare == 'N') {
					alert("第"+rowNum+"筆資料尚未作基站分攤");
					return false;
				}
			}
			if(status=='B'){
				//TB_PAY_CASH_REQUIREMENT
				var data = {
						cashReqNo : $("#editCashReqNo").val(),
						status : status
					};
				$.ajax({
					url : CONTEXT_URL + "/pay/pay005/updateToTable/",
					data : data,
					type : "POST",
					dataType : 'json',
					async : false,
					success : function(data) {
						if (data.success) {
							alert("修改成功,請款單號 : " + cashReqNo);
							isDetail=true;
							parent.$.fancybox.close();//關閉視窗
						} else {
							alert("修改錯誤 訊息: " + data.msg);
							isDetail=true;
							parent.$.fancybox.close();//關閉視窗
						}
					},
					error : function(data) {
						alert(data.msg);
					}
				});
			}
		} else {
			alert("未勾選要儲存的資料列,請重新操作!");
		}
		return true;
	}
	function shareBtn() {
		var selr = jQuery('#addGrid').jqGrid('getGridParam', 'selarrrow');
		var share = "";
		var idTemp = "";
		if (selr.length == 0) {
			alert("請選擇一筆資料做基站分攤");
			return false;
		}
		if (selr.length > 1) {
			alert("一次僅能修改一筆資料");
			return;
		}
		for (var i = 0; i < selr.length; i++) {
			share = jQuery('#addGrid').jqGrid('getRowData', selr[i]);
			if (share.tax_EXCLUSIVE_TOTAL_AMT == "") {
				alert("明細項次：" + selr[i] + " 請輸入未稅金額！");
				return;
			}
		}
		//資料送至SP1
		openPageDialogFrame("showSharePag", "/pay/pay005/share", "基站分攤", "",
				share.totalAmt, share.payment_REQ_NO, "Y");
	}
	//掛載按鈕Event
	function addMountButEvent() {
		//刪除雜項
		$('#delBtn').click(function() {
			delGrid();
		});
		//分攤
		$('#shareBtn').click(function() {
			shareBtn();
		});
		//儲存
		$('#saveBtn').click(function() {
			if (saveCheck()) {
				editAll("A");
			}
		});
		//送審
		$('#applyBtn').click(function() {
			if (saveCheck()) {
				editAll("B");
			}
		});
	}

	//接收SP1回傳值並寫入Temp
	function showMsData(psnData, key) {
		var obj = JSON.parse(psnData);
		var ids = jQuery("#addGrid").jqGrid('getDataIDs');
		//更新明細資料暫存狀態
		for (var iTemp = 0; iTemp < ids.length; iTemp++) {
			var rowId = ids[iTemp];
			var rowData = jQuery('#addGrid').jqGrid('getRowData', rowId);
			var tempKey = rowData.voucher_TYPE + rowData.voucher_NBR
					+ rowData.vat_NUMBER;
			if (tempKey == key && obj.spObject.length == 0) {//如果將基站分攤資料清空 修改其基站狀態
				$("#addGrid").jqGrid("setRowData", rowId, {
					'checkShare' : "N"
				});
			}
		}
	}
</script>
</head>
<body>
<div class="row">
		<div id="breadcrumb" class="col-md-12">
			<ol class="breadcrumb">
				<div class="col-sm-12" id="btnDiv">
					<button class="btn btn-primary btn-label-left" type="button"
						id="saveBtn">儲存</button>
					<button class="btn btn-primary btn-label-left" type="button"
						id="applyBtn">送審</button>
					<button class="btn btn-primary btn-label-left" type="button"
						id="delBtn">刪除雜項</button>
					<button class="btn btn-primary btn-label-left" type="button"
						id="shareBtn">基站分攤</button>
				</div>
			</ol>
		</div>
	</div>
	<div class="box-header">
		<div class="box-name">
			<i class="fa fa-edit"></i> <span>雜項請款作業-修改</span>
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
					<input type="hidden" id="editCashReqNo" name="editCashReqNo" value="${cashNo}" />
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
													disabled="disabled" value="${cashNo}">
											</div>
										</td>
									</tr>
									<tr>
										<td><label class="col-sm-12">維運區：</label></td>
										<td>
											<div class="col-sm-6">
												<select id="adddomainSelect" name="adddomainSelect" disabled>
											<option value="" selected>--請選擇--</option>
											<c:forEach items="${domainList}" var="domain">
													<c:choose>
														<c:when test="${domainId eq domain.ID}">
															<option value="${domain.ID}" selected>${domain.NAME}</option>
														</c:when>
														<c:otherwise>
															<option value="${domain.ID}">${domain.NAME}</option>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</select>
											</div>
										</td>
										<td><label class="col-sm-12">申請人：</label></td>
										<td><div class="col-sm-6">
											<input type="hidden" id="appUser" name="appUser" value="${appUser}" />
											<input id="appUserName" type="text" disabled
													class="form-control" name="appUserName" value="${appUserName}">
										</div></td>
									</tr>
									<tr>
										<td><div class="clearfix">&nbsp;</div></td>
									</tr>
									<tr>
										<td><label class="col-sm-12">申請日期：</label></td>
										<td>
											<div class="col-sm-6">
												<input id="addAppDate" type="text" disabled
													class="form-control" name="addAppDate" value="${appDate}">
											</div>
										</td>
										<td><label class="col-sm-12">請款年月：</label></td>
										<td>
											<div class="col-sm-6">
												<input id="addApplyYMSlash" type="text" disabled
													class="form-control" name="addApplyYMSlash" value="${yearMonthSlash}">
												<input type="hidden" id="addApplyYM" type="text" 
													class="form-control" name="addApplyYM" value="${yearMonth}">	
											</div>
										</td>
									</tr>
									<tr>
										<td><div class="clearfix">&nbsp;</div></td>
									</tr>
								</table>
								<label class="col-sm-1 control-label"></label>
								
								<div id="ajaxSearchResult" class="col-sm-9 control-label"
										style="width: 1000px;" >
									<div id="jQgrid" align="center" >
										<table id="addGrid"></table>
									</div>
								</div>
							</div>
						</div>
						<hr>
					</div>
					</form>
				</div>
				<div id="showSharePag" hidden="true"></div>		
				<div id="showSharePosPag" >
					<div id="selectSharePage"></div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>