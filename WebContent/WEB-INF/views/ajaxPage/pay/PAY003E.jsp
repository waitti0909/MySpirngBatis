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
<title>支票申請作廢作業-明細</title>
<script
	src="<s:url value="/resources/plugins/tinymce/tinymce.min.js" />"></script>
<script
	src="<s:url value="/resources/plugins/tinymce/jquery.tinymce.min.js" />"></script>
	<script	src="<s:url value="/resources/js/validate.js" />"></script>
<script type="text/javascript">
	
	var _delArrey_ = [];
	$(document).ready(function() {	
		LoadSelect2Script("<s:url value="/resources/plugins/select2/select2.min.js" />", selectDisDefault);		
				mountGrid();//掛載表格
				var data = {
						cashReqNo : $("#disCashReqNo").val(),
						processType : $("#disProcess").val()
					};
				$("#dGrid").setGridParam({datatype : "json",postData : data}).trigger("reloadGrid");
				selectDisDefault();
				mountButEvent();
			});
			function selectDisDefault() {
			$("#disDomain").select2();
			$("#disProcess").select2();
		}
	//掛載表格
		function mountGrid(){
			$("#dGrid").jqGrid({
				datatype : "local",
				autowidth:'true',
				pager : '#dPager',
				url : CONTEXT_URL + "/pay/pay003/searchRequiRement/",
				colNames : [ '','租約編號','(租約)名稱', '處理類別', '押金', '金額/月','付款月數','起帳日','請款期間起始','請款期間結束','請款金額','未稅金額','營業稅','應付金額','憑證',''],
				colModel : [ 
					{name : 'certificateBtn',index : 'certificateBtn', align : 'center',sortable : false},
					{name : 'contract_NO',index : 'contract_NO', align : 'center',sortable : false},
				    {name : 'lsName',index : 'lsName' , align : 'center',sortable : false},
				    {name : 'processType',index : 'processType' , align : 'center',sortable : false},
				    {name : 'pldgAmt',index : 'pldgAmt' , align : 'center',sortable : false,formatter: nullForZero}, 
				    {name : 'rentAmt',index : 'rentAmt', align : 'center',sortable : false,formatter: nullForZero},
				    {name : 'payment_MONTHS',index : 'payment_MONTHS', align : 'center',sortable : false},
				    {name : 'pay_begin_date',index : 'pay_begin_date' , align : 'center',sortable : false,formatter: jqGridDataFormat}, 
			   		{name : 'payment_REQ_BEGIN_DATE',index : 'payment_REQ_BEGIN_DATE' , align : 'center',sortable : false,formatter: jqGridDataFormat}, 
				   	{name : 'payment_REQ_END_DATE',index : 'payment_REQ_END_DATE' , align : 'center',sortable : false,formatter: jqGridDataFormat}, 
			   		{name : 's_amt',index : 's_amt' , align : 'center',sortable : false,formatter: nullForZero},
				    {name : 's_tax_exclusive_amt',index : 's_tax_exclusive_amt' , align : 'center',sortable : false,formatter: nullForZero},
				    {name : 's_business_tax',index : 's_business_tax' , align : 'center',sortable : false,formatter: nullForZero},
				    {name : 's_amt',index : 's_amt', align : 'center',sortable : false,formatter: nullForZero},
				    {name : 'voucher_credit',index : 'voucher_credit', align : 'center',sortable : false},
				    {name : 'payment_REQ_NO',index : 'payment_REQ_NO', align : 'center',sortable : false, hidden:true}],
				caption : "電費請款清單",
				rownumbers : true,
				multiselect : true,
				onSelectRow : function(rowId) {//點一下帶出清單
					var rowData = jQuery(this).getRowData(rowId);
					getPayment(rowData);
					$("#ajaxSearchResult2").show();
					$("#ajaxSearchResult3").hide();
				},
				gridComplete : function() {
					var ids = $("#dGrid").jqGrid('getDataIDs');
					var certificateBtn= "";
					for(var i=0;i < ids.length;i++){
						var cl = ids[i];
						certificateBtn = '<input id="certificate" type="button" value="輸入憑證" onclick="certificateadd('+cl+')">';
						jQuery("#dGrid").jqGrid('setRowData', ids[i], {certificateBtn:certificateBtn});
					};
					
				}
			});
			
			$("#d2Grid").jqGrid({
				datatype : "local",
				autowidth:'true',
				url : CONTEXT_URL + "/pay/pay003/searchRequiRement2/",
				colNames : [ '','電錶號碼','用電方式', '金額/度', '起帳日', '終止日','請款期間起始','請款期間結束','平均用電數/日','','上期度數','本次度數','本期用電度數','超出/減少比率','用電補充說明','請款金額','對應站台資訊'],
				colModel : [ 
					{name : 'contract_NO',index : 'contract_NO', align : 'center',sortable : false, hidden:true},
					{name : 'electricity_METER_NBR',index : 'electricity_METER_NBR', align : 'center',sortable : false},
				    {name : 'electricityDscr',index : 'electricityDscr' , align : 'center',sortable : false},
				    {name : 'chrg_mode',index : 'chrg_mode' , align : 'center',sortable : false, hidden:true},
				    {name : 'elec_begin_date',index : 'elec_begin_date' , align : 'center',sortable : false,formatter: jqGridDataFormat}, 
				    {name : 'elec_end_date',index : 'elec_end_date', align : 'center',sortable : false,formatter: jqGridDataFormat}, 
			   		{name : 'payment_REQ_BEGIN_DATE',index : 'payment_REQ_BEGIN_DATE' , align : 'center',sortable : false,formatter: jqGridDataFormat}, 
				   	{name : 'payment_REQ_END_DATE',index : 'payment_REQ_END_DATE' , align : 'center',sortable : false,formatter: jqGridDataFormat}, 
					{name : 'used_degree_day',index : 'used_degree_day', align : 'center',sortable : false,formatter: "number", formatoptions: { defaulValue:"0",decimalPlaces:2}},
					{name : 'm_day',index : 'm_day', align : 'center',sortable : false,formatter: nullForZero, hidden:true},
					{name : 'last_used_degree',index : 'last_used_degree', align : 'center',sortable : false,formatter: nullForZero},
			   		{name : 'total_USED_DEGREE',index : 'total_USED_DEGREE' , align : 'center',sortable : false,formatter: nullForZero},
				    {name : 'used_DEGREE',index : 'used_DEGREE' , align : 'center',sortable : false,formatter: nullForZero},
				    {name : 'ratio',index : 'ratio', align : 'center',sortable : false,formatter: "number", formatoptions: { defaulValue:"0",decimalPlaces:2}},
					{name : 'memo',index : 'memo', align : 'center',sortable : false},
				    {name : 'payment_REQ_AMT',index : 'payment_REQ_AMT', align : 'center',sortable : false,formatter: nullForZero},
				    {name : 'if_NO_SITE_MAPPING',index : 'if_NO_SITE_MAPPING', align : 'center',sortable : false}],
				caption : "電錶資訊",
				rownumbers : true,
				onSelectRow : function(rowId) {//點一下帶出清單
					var rowData = jQuery(this).getRowData(rowId);
					getTable3(rowData);
					$("#ajaxSearchResult3").show();
				},
				gridComplete : function() {
					var ids = $("#d2Grid").jqGrid('getDataIDs');
					var ratio = 0 ;
					var used_degree_day = 0 ;
					var gridRow = null;
					for(var i=0;i < ids.length;i++){
						var cl = ids[i];
						gridRow = $( "#d2Grid" ).jqGrid('getRowData',cl);
						used_degree_day = gridRow.used_degree_day/gridRow.m_day;
						ratio = (gridRow.used_DEGREE - used_degree_day)/used_degree_day;
						jQuery("#d2Grid").jqGrid('setRowData', ids[i], {used_degree_day:used_degree_day,ratio:ratio});
					}
				}
			});
			
			$("#d3Grid").jqGrid({
				datatype : "local",
				autowidth:'true',
				url : CONTEXT_URL + "/pay/pay003/searchRequiRement3/",
				colNames : [ '站點編號', '起帳日', '終止日','預付餘額','類別','請款期間起始','請款期間結束','本次請款'],
				colModel : [ 
					{name : 'location_ID',index : 'location_ID', align : 'center',sortable : false},
				    {name : 'pay_BEGIN_DATE',index : 'pay_BEGIN_DATE' , align : 'center',sortable : false,formatter: jqGridDataFormat}, 
				    {name : 'ls_E_DATE',index : 'ls_E_DATE', align : 'center',sortable : false,formatter: jqGridDataFormat}, 
			   		{name : 's_PREPAYMENT_BALANCE',index : 's_PREPAYMENT_BALANCE', align : 'center',sortable : false,formatter: nullForZero}, 
			   		{name : 'exp_ITEM_DESC',index : 'exp_ITEM_DESC', align : 'center',sortable : false},
			   		{name : 'payment_REQ_BEGIN_DATE',index : 'payment_REQ_BEGIN_DATE' , align : 'center',sortable : false,formatter: jqGridDataFormat}, 
				   	{name : 'payment_REQ_END_DATE',index : 'payment_REQ_END_DATE' , align : 'center',sortable : false,formatter: jqGridDataFormat}, 
					{name : 's_exclusive_business_amt',index : 's_exclusive_business_amt', align : 'center',sortable : false,formatter: nullForZero}],
				caption : "固定金額",
				rownumbers : true,
				gridComplete : function() {}
			});
			
			$(window).resize(function() {
				$(window).unbind("onresize");
				$("#dGrid,.dGrid").setGridWidth($("#jQgrid").width() - 10);
				$("#d2Grid,.d2Grid").setGridWidth($("#jQgrid").width() - 10);
				$("#d3Grid,.d3Grid").setGridWidth($("#jQgrid").width() - 10);
				$(window).bind("onresize", this);
			}).trigger('resize');	
		}	
		function getPayment(rowData) {
			var data = {
				contractNo : rowData.contract_NO,
				yearMonth : $.trim($("#yearMonth").val()),
				paymentReqNo : rowData.payment_REQ_NO
			};
			$("#disReqBeginDate").val(rowData.payment_REQ_BEGIN_DATE);
			$("#d2Grid").setGridParam({datatype:"json", postData:data}).trigger("reloadGrid");
		}
		function getTable3(rowData) {
			var data = {
					contractNo : rowData.contract_NO,
					electricityMeterNbr : rowData.electricity_METER_NBR,
					paymentReqBeginDate : $("#disReqBeginDate").val()
			};
			$("#d3Grid").setGridParam({datatype:"json", postData:data}).trigger("reloadGrid");
		}
		//for jqGrid 的 data format
		function jqGridDataFormat(cellvalue, options, rowObject){
				if (cellvalue != null && cellvalue != "") {
	   			var stDate = new Date(cellvalue);
				return stDate.getFullYear() + "/" + addZero(stDate.getMonth() + 1) + "/" + addZero(stDate.getDate()) ;
				}else {
					return "";
				}
		};
		//時間左補零滿兩位
		function addZero(i) {
			if (i < 10) {
				i = "0" + i;
			}
			return i;
		};
	function certificateadd(cl){
		var gridRow = $( "#dGrid" ).jqGrid('getRowData',cl);
		$.ajax({
			mimeType : "text/html; charset=utf-8",
			url : CONTEXT_URL + "/pay/pay003/sp1/init",
			type : "POST",
			data : {
				sPcashReqNo : $("#disCashReqNo").val(),
				sPcontractNo : gridRow.contract_NO, // 驗收單號
				sPlsName : gridRow.lsName,
				sPsumAmt : gridRow.s_amt
			},
			dataType : "html",
			async : false,
			success : function(data) {
				initialDialogFrame("certificatePage", "輸入憑證", data);
			}
		});
		
	}
	function nullForZero(cellvalue, options, rowObject) {
		var returnValue = cellvalue;
		if (cellvalue == "" || cellvalue == "undefined" || cellvalue == null) {
			returnValue = "0";
		}
		return returnValue;
	}
	function updateData(status,statusDesc){
		$.ajax({
				url : CONTEXT_URL + "/pay/pay003/updateData",
				data : {
					cashReqNo : $("#disCashReqNo").val()
				},
				contentType : "application/json",
				dataType : "json",
				async : false,
				success : function(data) {
					alert(data.msg.replace("error", ""));
					if (data.msg.indexOf("error") >= 0) {
						return false;
					}
					parent.$.fancybox.close();//關閉視窗
				},
				error : function(data) {
					alert(data.msg);
				}
			});	
	}
	function delEdit() {
		var selr = jQuery('#dGrid').jqGrid('getGridParam', 'selarrrow');
		for (var i = 0; i < selr.length; i++) {
			var rowData = jQuery('#dGrid').jqGrid('getRowData',selr[i]);
			 var TbPayPaymentRequest = {
					"cash_REQ_NO" : $("#disCashReqNo").val(), 
					"payment_REQ_NO" : rowData.payment_REQ_NO
				};
			 	_delArrey_.push(TbPayPaymentRequest);
				$("#ajaxSearchResult2").hide();
				$("#ajaxSearchResult3").hide();
				$('#dGrid').jqGrid("delRowData", selr[i]);
		}
	}
	//掛載表格Event
	function mountButEvent() {
		//儲存
		$('#saveEditBtn').click(function() {
			_delcheck_ = true;
			if(_delArrey_.length == 0){
				 var TbPayPaymentRequest = {
							"cash_REQ_NO" : $("#disCashReqNo").val(), 
							"payment_REQ_NO" : ''
						};
					 	_delArrey_.push(TbPayPaymentRequest);
			}
			$.ajax({
				url : CONTEXT_URL + "/pay/pay003/deleteData",
				data : JSON.stringify(_delArrey_),
				contentType : "application/json",
				type : "POST",
				dataType : 'json',
				success : function(data) {
					alert(data.msg.replace("error", ""));
					if (data.msg.indexOf("error") >= 0) {
						return false;
					}
				},
				error : function(data) {
					alert(data.msg);
				}
			});	
			
		});

		//刪除
		$('#delEditBtn').click(function() {
			// 取得被選取列
			var ids = $("#dGrid").jqGrid("getGridParam", "selrow");
			// 是否選取
			if (ids == undefined) {
				alert("請選擇一筆資料");
				return false;
			}
			if(confirm("是否確定要刪除?")){
			_delcheck_ = false;
			delEdit();
			}
		});

		//送審			
		$('#approveEditBtn').click(function() {
			if(_delcheck_){
				updateData('B','送審中');	
			}else{
				alert("請先按『儲存』。");
			}
							
		});
	}
</script>
</head>
<body>
<div class="row">

	</div>
	</div>
	<div class="box-header">
		<div class="box-name">
			<i class="fa fa-edit"></i> <span>電費請款</span>
		</div>
		<div class="box-icons">
			<p class="expand-link"></p>
		</div>
		<div class="no-move"></div>
	</div>
	<div class="box-content">
	<div id="breadcrumb" >
			<ol class="breadcrumb">
				<div class="col-sm-12" id="btnDiv">
					<button class="btn btn-primary btn-label-left" type="button"
						id="delEditBtn">刪除明細</button>
						<button class="btn btn-primary btn-label-left" type="button"
						id="saveEditBtn">
						<span><i class="fa fa-save"></i></span> 儲存
					</button>
					<button class="btn btn-primary btn-label-left" type="button"
						id="approveEditBtn">送審</button>
				</div>
			</ol>
		</div>
							<div id="button">
									<div id="button" style="text-align:center;">
									<input id="disCashReqNo" type="hidden" name=disCashReqNo value="${cashReqNo}">
									<input id="disReqBeginDate" type="hidden" name="disReqBeginDate" >
								<font color='#CC0000'>&nbsp;&nbsp;&nbsp;&nbsp;請款單號 : <c:out value="${cashReqNo}"/></font>
							</div>
							</div>
	<div class="row">
		<div class="col-xs-12 col-sm-12">
			<div class=" ui-draggable ui-droppable">
				<div class="box-content">
					<form class="form-horizontal" id="bBoardAddEdit" >
					<div class="form-group">
						<div class="row">
							<div class="col-md-12">
									<table style="width: 100%">
									<tr>
										<td><label class="col-sm-12 control-label">維運區：</label></td>
										<td>
											<div class="col-sm-6">
												<select id="disDomain" name="disDomain" disabled>
													<option value="" selected>--請選擇--</option>
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
										<td><label class="col-sm-12 control-label">處理類別：</label></td>
										<td>
											<div class="col-sm-6">
												<select id="disProcess" name="disProcess" disabled>
													<option value="" selected>--請選擇--</option>
													<c:forEach items="${processSelect}" var="addProcess">
														<c:choose>
															<c:when test="${processType eq addProcess.LOOKUP_CODE}">
																<option value="${addProcess.LOOKUP_CODE}" selected>${addProcess.LOOKUP_CODE_DESC}</option>
															</c:when>
															<c:otherwise>
																<option value="${addProcess.LOOKUP_CODE}">${addProcess.LOOKUP_CODE_DESC}</option>
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
												<input id="disappUser" type="hidden" class="form-control"
													name="disappUser" value="${appUser}">	
											</div></td>
										<td><label class="col-sm-12 control-label">申請日期：</label></td>
										<td>
											<div class="col-sm-6">
												<input id="appDate" type="text" disabled
													class="form-control" name="appDate" 
													value="<fmt:formatDate  value="${appDate}"  pattern="yyyy/MM/dd HH:mm"  />">
											</div>
										</td>
									</tr>
									<tr>
										<td><div class="clearfix">&nbsp;</div></td>
									</tr>
									<tr>
										<td><label class="col-sm-12 control-label">請款日期：</label></td>
										<td>
											<div class="col-sm-6">
											<input id="yearMonth" type="text" disabled
													class="form-control" name="yearMonth" value="${yearMonth}">
											</div>
										</td>
									</tr>
									<tr>
										<td><div class="clearfix">&nbsp;</div></td>
									</tr>
								</table>	
								<div id="ajaxSearchResult" class="col-xs-12">
									<div id="jQgrid" align="center">
										<table id="dGrid"></table>
										<div id="dPager"></div>
									</div>
								</div>
									<tr>
										<td><div class="clearfix">&nbsp;</div></td>
									</tr>
								<div id="ajaxSearchResult2" class="col-xs-12" style="display:none">
									<div id="jQgrid" align="center">
										<table id="d2Grid"></table>
									</div>
								</div>
									<tr>
										<td><div class="clearfix">&nbsp;</div></td>
									</tr>
								<div id="ajaxSearchResult3" class="col-xs-12" style="display:none">
									<div id="jQgrid" align="center">
										<table id="d3Grid"></table>
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
	<div id="certificatePage"></div>
	
</body>
</html>