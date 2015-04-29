<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>支票作廢申請作業-新增</title>
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
				prepareDay();//預設日期
			});
	//掛載表格
		function mountGrid(){
			$("#addGrid").jqGrid({
				datatype : "local",
				autowidth:'true',
				colNames : [ '票據號碼','付款對象', '名稱', '金額', '支票兌現日','作廢原因','說明','','','',''],
				colModel : [ 
					{name : 'check_NBR',index : 'check_NBR', align : 'center',sortable : false},
				    {name : 'payment_user_id',index : 'payment_user_id' , align : 'center',sortable : false,formatter: nullForZero},
				    {name : 'payment_user_name',index : 'payment_user_name' , align : 'center',sortable : false}, 
				    {name : 'totalAmt',index : 'totalAmt', align : 'center',sortable : false,formatter: nullForZero},
				    {name : 'check_cash_date',index : 'check_cash_date' , align : 'center',sortable : false}, 
				    {name : 'check_disregard_reason',index : 'check_disregard_reason' , align : 'center',sortable : false},
				    {name : 'disregard_MEMO',index : 'disregard_MEMO', align : 'center',sortable : false},
				    {name : 'reasonCode',index : 'reasonCode',sortable : false,hidden:true},
				    {name : 'payment_seq_nbr',index : 'payment_seq_nbr',sortable : false,hidden:true},
				    {name : 'payment_REQ_NO',index : 'payment_REQ_NO',sortable : false,hidden:true},
				    {name : 'payment_REQ_ITEM_NO',index : 'payment_REQ_ITEM_NO',sortable : false,hidden:true}],
				caption : "支票清單",
				multiselect : true,
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
				caption : "付款明細",
				rownumbers : true
			});
			$(window).resize(function() {
				$(window).unbind("onresize");
				$("#addGrid,.addGrid").setGridWidth($("#jQgrid").width() - 10);
				$("#dtlGrid,.dtlGrid").setGridWidth($("#jQgrid").width() - 10);
				$(window).bind("onresize", this);
			}).trigger('resize');	
		}		
	function nullForZero(cellvalue, options, rowObject) {
		var returnValue = cellvalue;
		if (cellvalue == "" || cellvalue == "undefined" || cellvalue == null) {
			returnValue = "0";
		}
		return returnValue;
	}
	function prepareDay() {
			var Today = new Date();
			$('#addAppDate').prop(
					"value",
					Today.getFullYear() + "/" + (Today.getMonth() + 1) + "/"
							+ (Today.getDate()));
			//扣押日起始日曆						
			$('#addAppDate').datepicker({
				dateFormat : "yy/mm/dd",
				changeYear : true,
				changeMonth : true,
				showButtonPanel : true,
				showTime : false
			});
		}
	// 開啟下拉式選單搜尋文字功能
	function selectAddDefault() {
		$("#addDomain").select2();
		$("#checkReason").select2();
		$("#payment").select2();
	}
	//確認輸入欄位
	function addcheck() {
		var addDomain = $("#addDomain").find("option:selected").val();		
		if ((addDomain == null || addDomain == "")) {
			alert('維運區不得為空值');
			return false;
		}
		return true;
	}
	//檢核支票資訊
	function paymentcheck() {
		var payment	 = $("#payment").find("option:selected").val();
		var checkReason = $("#checkReason").find("option:selected").val();
		if (isEmpty(payment)) {
			alert("請選擇票據號碼");
			return false;
		}
		if (isEmpty(checkReason)) {
			alert("票據號碼: "+payment+" 請選擇作廢原因");
			return false;
		}
		return true;
	}
	//重置		
	function reset() {
		$("#addDomain").select2("val", "");
		$("#payment").select2("val", "");
		$("#checkReason").select2();
		//支票清單
		$("#paymentUserId").prop("value", "");
		$("#paymentUserName").prop("value", "");
		$("#paymentAmt").prop("value","");
		$("#checkCashDate").prop("value", "");
		$("#memo").prop("value","");
		$("#paymentSeq").prop("value","");		
		$("#paymentReqNo").prop("value","");
		$("#paymentItemNo").prop("value","");	
		$("#addGrid").jqGrid().clearGridData();
		$("#dtlGrid").jqGrid().clearGridData();
	}
	//新增支票後的重置
	function resetPayment() {
		$("#payment").select2("val", "");
		$("#paymentUserId").prop("value", "");
		$("#paymentUserName").prop("value", "");
		$("#paymentAmt").prop("value","");
		$("#checkCashDate").prop("value", "");
		$("#memo").prop("value","");
		$("#checkReason").select2("val", "");
		$("#paymentSeq").prop("value","");		
		$("#paymentReqNo").prop("value","");
		$("#paymentItemNo").prop("value","");	
		$("#dtlGrid").jqGrid().clearGridData();
	}
	$("#payment").change(onPayChange);
	// 票據號碼異動控制
	function onPayChange() {
		var data = {
			checkNbr : $("#payment").find("option:selected").val()
		}
		$.ajax({
			url : CONTEXT_URL + "/pay/pay010/getPaymentData",
			data : data,
			type : "POST",
			dataType : 'json',
			async : false,
			success : function(data) {
				if (data.success) {
					$("#paymentUserId").prop("value",data.rows[0].payment_USER_ID);
					$("#paymentUserName").prop("value",data.rows[0].payment_USER_NAME);
					$("#paymentAmt").prop("value",
							data.rows[0].tax_EXCLUSIVE_TOTAL_AMT
									+ data.rows[0].total_BUSINESS_TAX
									- data.rows[0].total_INCOME_TAX
									- data.rows[0].total_NHI_AMT);
					var Today = new Date(data.rows[0].check_CASH_DATE);
					$('#checkCashDate').prop("value",
					Today.getFullYear() + "/" + (Today.getMonth() + 1) + "/"
							+ (Today.getDate()));		
					$("#paymentSeq").prop("value",data.rows[0].seq_NBR);		
					$("#paymentReqNo").prop("value",data.rows[0].payment_REQ_NO);
					$("#paymentItemNo").prop("value",data.rows[0].payment_REQ_ITEM_NO);		
				} else {
					alert("查無此票據號碼 "
							+ $("#payment").find("option:selected").val()
							+ " 資訊")
					$("#paymentUserId").prop("value","");
					$("#paymentUserName").prop("value","");
					$("#paymentAmt").prop("value","");
					$('#checkCashDate').prop("value","");		
					$("#paymentSeq").prop("value","");		
					$("#paymentReqNo").prop("value","");
					$("#paymentItemNo").prop("value","");			
				}
			}
		});
	}

	function getPayCheckDisregardNo() {
		var disregardNo = "";
		var crTime = "";
		$.ajax({
			url : CONTEXT_URL + "/pay/pay010/getPayCheckDisregardNo",
			data : crTime,
			type : "POST",
			dataType : 'json',
			async : false,
			success : function(data) {
				if (data.success) {
					disregardNo = data.msg
				}
			}
		});
		return disregardNo;
	}
	
	function saveMas(disregardNo) {
		var selr = jQuery('#addGrid').jqGrid('getGridParam', 'selarrrow');
		var addArray =[];
		if (!selr.length == 0) {
			var Today = new Date($('#addAppDate').val());
				var appDate=Today.getFullYear() + "-" + (Today.getMonth()+1) + "-"
							+ (Today.getDate());
			for(var i=0;i<selr.length;i++){
				var myrow = jQuery('#addGrid').jqGrid('getRowData',selr[i]);
				var TbPayCheckDisregard = {
					"disregard_NO" : disregardNo,
					"check_NBR" : myrow.check_NBR,
					"payment_SEQ_NBR" : myrow.payment_seq_nbr,
					"payment_REQ_NO" : myrow.payment_REQ_NO,
					"payment_REQ_ITEM_NO" : myrow.payment_REQ_ITEM_NO,
					"app_DATE" : appDate,
					"app_USER" : $('#appUser').val(),
					"status" : "A",
					"check_DISREGARD_REASON" : myrow.reasonCode,
					"check_DISREGARD_REASON_DESC" : myrow.disregard_MEMO,
					"cr_USER" : $('#appUser').val(),
					"md_USER" : $('#appUser').val(),
					"check_AMT" : myrow.totalAmt,
					"cash_REQ_NO" : myrow.payment_REQ_NO,
					"domain" : $("#addDomain").find("option:selected").val()
				};
				addArray.push(TbPayCheckDisregard);
			}
			$.ajax({
				url : CONTEXT_URL + "/pay/pay010/saveDisData/",
				data : JSON.stringify(addArray),
				contentType : "application/json",
				type : "POST",
				dataType : 'json',
				success : function(data) {
					alert("新增成功,作廢單號 : " + disregardNo);
					isDetail=true;
					parent.$.fancybox.close();//關閉視窗
				}
			});
		} else {
			alert("未勾選要作廢的資料列,請重新操作!");
		}
		return true;
	}
	function getPayment(rowData) {
			var data = {
				reqNo : rowData.payment_REQ_NO,
				reqItemNo : rowData.payment_REQ_ITEM_NO,
				seqNo : rowData.payment_seq_nbr
			};
			$("#dtlGrid").setGridParam({datatype:"json", postData:data,page:1}).trigger("reloadGrid");
		}
	function addRow() {
		var checkNbr = $("#payment").find("option:selected").val();
		var paymentUserId = $("#paymentUserId").val();
		var paymentUserName = $("#paymentUserName").val();
		var paymentAmt = $("#paymentAmt").val();
		var checkCashDate = $("#checkCashDate").val();
		var checkReason = $("#checkReason").find("option:selected").val();
		var checkReasonText = $("#checkReason").find("option:selected").text();
		var memo = $("#memo").val();
		var paymentSeq = $("#paymentSeq").val();
		var paymentReqNo = $("#paymentReqNo").val();
		var paymentItemNo = $("#paymentItemNo").val();
		var gridId = $("#addGrid").jqGrid('getDataIDs');
		$("#addGrid").jqGrid("addRowData", gridId + 1, {
			'check_NBR' : checkNbr,
			'payment_user_id' : paymentUserId,
			'payment_user_name' : paymentUserName,
			'totalAmt' : paymentAmt,
			'check_cash_date' : checkCashDate,
			'check_disregard_reason' : checkReasonText,
			'disregard_MEMO' : memo,
			'reasonCode' : checkReason,
			'payment_seq_nbr' : paymentSeq,
			'payment_REQ_NO' : paymentReqNo,
			'payment_REQ_ITEM_NO' : paymentItemNo
		}, "first");
	}

	function delGrid() {
		var selr = jQuery('#addGrid').jqGrid('getGridParam', 'selarrrow');
		if (!selr.length == 0) {
			if (confirm("是否確定要刪除")) {
				for (var i = selr.length - 1; i >= 0; i--) {
					$('#addGrid').jqGrid("delRowData", selr[0]);
				}
			}
		} else {
			alert("未勾選要刪除的資料列,請重新操作!");
		}
		$("#dtlGrid").jqGrid().clearGridData();
	}
	//掛載按鈕Event
	function addMountButEvent() {
		//新增付款人
		$('#plusBtn').click(function() {
			if (paymentcheck()) {
				addRow();
				resetPayment();//新增完成後刪除新增之選項內容
			}
		});
		//刪除付款人
		$('#delBtn').click(function() {
			delGrid();
		});
		//作廢
		$('#saveBtn').click(function() {
			if (addcheck()) {
				var disregardNo = getPayCheckDisregardNo();
				saveMas(disregardNo);//寫入主檔
			}
		});
		//取消
		$('#resetBtn').click(function() {
			reset();
		});
	}
</script>
</head>
<body>
<div class="row">
		<div id="breadcrumb" class="col-md-12">
			<ol class="breadcrumb">
				<div class="col-sm-12" id="btnDiv">
					<button class="btn btn-primary btn-label-left" type="button"
						id="saveBtn">
						<span><i class="fa fa-save"></i></span>作廢
					</button>
					<button class="btn btn-primary btn-label-left" type="button"
						id="resetBtn">取消</button>
				</div>
			</ol>
		</div>
	</div>
	<div class="box-header">
		<div class="box-name">
			<i class="fa fa-edit"></i> <span>支票作廢申請作業-新增</span>
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
										<td><label class="col-sm-12 control-label"><span
												class="s">* </span>維運區：</label></td>
										<td>
											<div class="col-sm-12">
												<select id="addDomain" name="addDomain">
													<option value="" selected>--請選擇--</option>
													<c:forEach items="${domainSelect}" var="addDomain">
														<option value="${addDomain.ID}">${addDomain.NAME}</option>
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
										<td><div class="col-sm-12">
												<input id="appUserName" type="text" disabled
													class="form-control" name="appUser" value="${appUserName}">
												<input id="appUser" type="hidden" class="form-control"
													name="appUser" value="${appUser}">
											</div></td>
										<td><label class="col-sm-12 control-label">申請日期：</label></td>
										<td>
											<div class="col-sm-12">
												<input id="addAppDate" type="text" disabled
													class="form-control" name="addAppDate" value="">
											</div>
										</td>
									</tr>
									<tr>
										<td><div class="clearfix">&nbsp;</div></td>
									</tr>
									<tr>
										<td><div class="clearfix">&nbsp;</div></td>
									</tr>			
									<tr>
										<td><label class="col-sm-12 control-label"><span class="s">* </span>票據號碼：</label></td>
										<td>
											<div class="col-sm-12">
												<select id="payment"
													name="payment" class="populate placeholder">
													<option value="" selected>--請選擇--</option>
													<c:forEach items="${paymentSelect}" var="payment">
														<option value="${payment.CHECK_NBR}">${payment.CHECK_NBR}</option>
													</c:forEach>
												</select>
											</div>
										</td>
										<td><label class="col-sm-12 control-label"><span class="s">* </span>付款對象：</label></td>
										<td>
											<div class="col-sm-12">
												<input id="paymentUserId" type="text" class="form-control" disabled
													name="paymentUserId" value="">
											</div>
										</td>
										<td><label class="col-sm-12 control-label"><span class="s">* </span>名稱：</label></td>
										<td>
											<div class="col-sm-12">
												<input id="paymentUserName" type="text" class="form-control" disabled
													name="paymentUserName" value="">
											</div>
										</td>
									</tr>
									<tr>
										<td><div class="clearfix">&nbsp;</div></td>
									</tr>
									<tr>	
										<td><label class="col-sm-12 control-label"><span class="s">* </span>金額：</label></td>
										<td><div class="col-sm-12">
											<input id="paymentAmt" type="text" class="form-control" disabled
													name="paymentAmt" value="">
											</div></td>
										<td><label class="col-sm-12 control-label"><span class="s">* </span>支票兌現日：</label></td>
										<td><div class="col-sm-12">
												<input id="checkCashDate" type="text" class="form-control" disabled
													name="checkCashDate" value="">
										</div></td>
										<td><label class="col-sm-12 control-label"><span class="s">* </span>作廢原因：</label></td>
										<td><div class="col-sm-12">
												<select id="checkReason"
													name="checkReason" class="populate placeholder">
													<option value="" selected>--請選擇--</option>
													<c:forEach items="${checkReason}" var="reason">
														<option value="${reason.LOOKUP_CODE}">${reason.LOOKUP_CODE_DESC}</option>
													</c:forEach>
												</select>
											</div></td>
									</tr>
									<tr>
										<td><div class="clearfix">&nbsp;</div></td>
									</tr>
									<tr>
										<td><label class="col-sm-12 control-label">說明：</label></td>
										<td><div class="col-sm-24">
													<textarea class="form-control" rows="2" id=memo name="memo"/>
												<!--支票隱藏欄位-->
												<input id="paymentSeq" maxlength="10" type="hidden"
													class="form-control" name="paymentSeq" value="">
												<input id="paymentReqNo" maxlength="10" type="hidden"
													class="form-control" name="paymentReqNo" value="">
												<input id="paymentItemNo" maxlength="10" type="hidden"
													class="form-control" name="paymentItemNo" value="">			
											</div></td>
									</tr>
									<tr>
										<td><div class="clearfix">&nbsp;</div></td>
									</tr>
								</table>
								<label class="col-sm-1 control-label"></label>
								<button class="btn btn-primary btn-label-left" type="button"
									id="plusBtn">新增支票</button>
								<button class="btn btn-primary btn-label-left" type="button"
									id="delBtn">刪除支票</button>
								<div id="ajaxSearchResult" class="col-xs-12">
									<div id="jQgrid" align="center">
										<table id="addGrid"></table>
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