<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>


<!DOCTYPE html>
<html lang="en">
<head>
<title>電費分攤明細表</title>
	<script type="text/javascript">
	    // 初始載入
		$(document).ready(function() {
			mountButEvent();
			// 預設下拉選單格式
			LoadSelect2Script("<s:url value="/resources/plugins/select2/select2.min.js" />", selectDefault);
			prepareDay();// 請款期間 預設日期
			mountGrid();// 載入表格
		});
	    
		// 掛載表格
		function mountGrid(){
			$("#grid").jqGrid({
				datatype : "local",
				pager : '#pager',
				autowidth:'true',
				url : CONTEXT_URL + "/pay/pay011/search/",
				colNames : [ '請款單號','租約編號', '電錶號碼', '(租約)名稱', '用電方式','請款期間起始','請款期間結束','本次用電數','請款金額','','','','','','','','',''],
				colModel : [
					{name : 'cashReqNo',index : 'cashReqNo', align : 'center',sortable : false},
				    {name : 'contract_NO',index : 'contract_NO', align : 'center',sortable : false},
				    {name : 'electricity_METER_NBR',index : 'electricity_METER_NBR' , align : 'center',sortable : false},
				    {name : 'contractName',index : 'contractName' , align : 'center',sortable : false},// 秀中文
				    {name : 'electricityDscr',index : 'electricityDscr' , align : 'center',sortable : false}, // 秀中文
				    {name : 'payment_REQ_BEGIN_DATE',index : 'payment_REQ_BEGIN_DATE' , align : 'center',sortable : false,
				    	formatter: function(cellvalue, options, rowObject){
				   			if (cellvalue != null && cellvalue != "") {
					   			var stDate = new Date(cellvalue);
								return stDate.getFullYear() + "/" + addZero((stDate.getMonth() + 1)) + "/" + addZero(stDate.getDate()) ;
				   			}else {
				   				return "";
				   			}
			   			}},
				    {name : 'payment_REQ_END_DATE',index : 'payment_REQ_END_DATE' , align : 'center',sortable : false,
				    	formatter: function(cellvalue, options, rowObject){
				   			if (cellvalue != null && cellvalue != "") {
					   			var stDate = new Date(cellvalue);
								return stDate.getFullYear() + "/" + addZero((stDate.getMonth() + 1)) + "/" + addZero(stDate.getDate());
				   			}else {
				   				return "";
				   			}
			   			}},
				    {name : 'total_USED_DEGREE',index : 'total_USED_DEGREE' , align : 'center',sortable : false},
				    {name : 'payment_REQ_AMT',index : 'payment_REQ_AMT' , align : 'center',sortable : false},
				    {name : 'appDate',index : 'appDate',sortable : false,hidden:true},
				    // 隱藏欄位for 用電明細視窗顯示資料用
				    {name : 'useTypeDscr',index : 'useTypeDscr',sortable : false,hidden:true},
				    {name : 'year_MONTH',index : 'year_MONTH',sortable : false,hidden:true},
				    {name : 'siteName',index : 'siteName',sortable : false,hidden:true},
				    {name : 'used_DEGREE',index : 'used_DEGREE',sortable : false,hidden:true},
				    {name : 'memo',index : 'memo',sortable : false,hidden:true},
				    {name : 'ifAutoDeductDscr',index : 'ifAutoDeductDscr',sortable : false,hidden:true},
				    {name : 'ifNoSiteMappingDscr',index : 'ifNoSiteMappingDscr',sortable : false,hidden:true},
				    {name : 'payment_REQ_NO',index : 'payment_REQ_NO',sortable : false,hidden:true}],
				caption : "用電清單",
				multiselect : true,
				rownumbers : true,
				onSelectRow : function(rowId) {// 點一下帶出清單
					var rowData = jQuery(this).getRowData(rowId);
					getAmtData(rowData);// 取得固定金額資料
				},
				gridComplete : function() {},
				ondblClickRow: function(rowId) { // double clicking a jqgrid row
		            //var rowData = jQuery(this).getRowData(rowId);
		           // getDtl(rowData);
		        }
			});
			
			$("#dtlgrid").jqGrid({
				datatype : "local",
				pager : '#dtlpager',
				autowidth:'true',
				url : CONTEXT_URL + "/pay/pay011/searchAmtData/",
				colNames : [ '站點編號','起帳日', '終止日', '預付餘額' ,'類別','請款期間起始','請款期間結束','本次請款'],
				colModel : [
					{name : 'location_ID',index : 'location_ID', align : 'center',sortable : false},
					{name : 'pay_BEGIN_DATE',index : 'pay_BEGIN_DATE' , align : 'center',sortable : false,
				    	formatter: function(cellvalue, options, rowObject){
				   			if (cellvalue != null && cellvalue != "") {
					   			var stDate = new Date(cellvalue);
								return stDate.getFullYear() + "/" + addZero((stDate.getMonth() + 1)) + "/" + addZero(stDate.getDate()) ;
				   			}else {
				   				return "";
				   			}
			   		}},
					{name : 'ls_E_DATE',index : 'ls_E_DATE' , align : 'center',sortable : false,
				    	formatter: function(cellvalue, options, rowObject){
				   			if (cellvalue != null && cellvalue != "") {
					   			var stDate = new Date(cellvalue);
								return stDate.getFullYear() + "/" + addZero((stDate.getMonth() + 1)) + "/" + addZero(stDate.getDate()) ;
				   			}else {
				   				return "";
				   			}
			   		}},
			   		{name : 's_PREPAYMENT_BALANCE',index : 's_PREPAYMENT_BALANCE', align : 'center',sortable : false},
					{name : 'payment_REQUEST_ITEM_DESC',index : 'payment_REQUEST_ITEM_DESC', align : 'center',sortable : false},
					{name : 'payment_REQ_BEGIN_DATE',index : 'payment_REQ_BEGIN_DATE' , align : 'center',sortable : false,
				    	formatter: function(cellvalue, options, rowObject){
				   			if (cellvalue != null && cellvalue != "") {
					   			var stDate = new Date(cellvalue);
								return stDate.getFullYear() + "/" + addZero((stDate.getMonth() + 1)) + "/" + addZero(stDate.getDate()) ;
				   			}else {
				   				return "";
				   			}
			   		}},
				    {name : 'payment_REQ_END_DATE',index : 'payment_REQ_END_DATE' , align : 'center',sortable : false,
				    	formatter: function(cellvalue, options, rowObject){
				   			if (cellvalue != null && cellvalue != "") {
					   			var stDate = new Date(cellvalue);
								return stDate.getFullYear() + "/" + addZero((stDate.getMonth() + 1)) + "/" + addZero(stDate.getDate());
				   			}else {
				   				return "";
				   			}
			   		}},
					{name : 'payment_REQUEST_AMT',index : 'payment_REQUEST_AMT', align : 'center',sortable : false}],
				caption : "固定金額",
				rownumbers : true,
				onSelectRow : function() {},
				gridComplete : function() {},
				ondblClickRow: function(rowId) {}
			});
			$(window).resize(function() {
				$(window).unbind("onresize");
				$("#grid,.grid").setGridWidth($("#jQgrid").width() - 10);
				$("#dtlgrid,.dtlgrid").setGridWidth($("#jQgrid").width() - 10);
				$(window).bind("onresize", this);
			}).trigger('resize');
		}
		
		// 載入條件
		function selectData() {
			var data = {
				domain : $("#domainSelect").find("option:selected").val(),
				electricityType : $("#electricityTypeSelect").find("option:selected").val(),
				btsSiteId : $("#btsSiteId").val(),
				electricityMeterNbr : $("#electricityMeterNbr").val(),
				paymentReqStartDate : $("#paymentReqStartDate").val(),
				paymentReqEndDate : $("#paymentReqEndDate").val()
			};
			$("#grid").setGridParam({datatype:"json", postData:data}).trigger("reloadGrid");
			$("#dtlgrid").jqGrid().clearGridData();
		}

		// 輸入條件檢核
		function check() {
			var domain = $("#domainSelect").find("option:selected").val();
			var electricityType = $("#electricityTypeSelect").find("option:selected").val();
			var paymentReqStartDate = $("#paymentReqStartDate").val();
			var paymentReqEndDate = $("#paymentReqEndDate").val();
			if ((domain == null || domain == "")) {
				alert('維運區不得為空值');
				return false;
			}
			if ((electricityType == null || electricityType == "")) {
				alert('用電方式不得為空值');
				return false;
			}
			// 日期檢核
			dateCheck(paymentReqStartDate, paymentReqEndDate, "請款期間");
			return true;
		}
		
		// 掛載表格Event
		function mountButEvent() {
			// 查詢
			$('#btn_search').click(function() {
				if(check()){
					selectData();
				}
			});

			// 列印
			$('#btn_print').click(function() {
				var selr = jQuery('#grid').jqGrid('getGridParam','selarrrow');
				// 判斷是否勾選
				if(selr.length==0){
					alert("需先選擇一筆用電清單資料才可以進行列印動作,請重新操作!");
					return false;
				}
				if(selr.length > 1){
					alert("只能勾選一筆資料");
					return false;
				}
				if (!selr.length == 0) {
					for(var i=0;i<selr.length;i++){
						 var myrow = jQuery('#grid').jqGrid('getRowData',selr[i]);
						 var electricityMeterNbr = myrow.electricity_METER_NBR;
						 var contractNo = myrow.contract_NO;
						 var paymentReqNo = myrow.payment_REQ_NO;
						 var appDate = myrow.appDate;
						 // 顯示用電清單列印視窗
						 openPrintView(electricityMeterNbr,contractNo,paymentReqNo,appDate);
					}
				}
			});
			
			// 顯示明細
			$('#btn_detail').click(function() {
				var selr = jQuery('#grid').jqGrid('getGridParam','selarrrow');
				// 判斷是否勾選
				if(selr.length==0){
					alert("需先選擇一筆用電清單資料才可以顯示明細資料,請重新操作!");
					return false;
				}
				if(selr.length > 1){
					alert("只能勾選一筆資料");
					return false;
				}
				if (!selr.length == 0) {
					for(var i=0;i<selr.length;i++){
						 var rowData = jQuery('#grid').jqGrid('getRowData',selr[i]);
						 // 顯示用電清單明細視窗
						 getDtl(rowData);
					}
				}
			});
			
			// 重置按鈕
			$('#btn_reset').click(
					function() {
						$("#searchFrom").trigger('reset');
						$("#grid").jqGrid().clearGridData();
						$("#dtlgrid").jqGrid().clearGridData();
						$("#domainSelect").select2("val", "");
						$("#electricityTypeSelect").select2("val", "");
						$("#btsSiteId").prop("value", "");
						$("#electricityMeterNbr").prop("value", "");
						prepareDay();
			       });
		}
		
		//================FUNCTION======================	
		//  開啟下拉式選單搜尋文字功能
		function selectDefault() {
			$("#searchFrom").find("select").select2();
		}
		
		// 站台編號LOV
		$("#siteIdSearch").click(function () {
			openSiteDialogFrame("selectSitePage", "receviceSiteData"); // call common.js
		});

		//站台查詢使用	
		function receviceSiteData(data) {
			var json = JSON.parse(data);
			var btsSiteId = json.siteObjs[0].bts_SITE_ID;
			$("#btsSiteId").val(btsSiteId);
		}
		
		//時間左補零滿兩位
		function addZero(i) {
			if (i < 10) {
				i = "0" + i;
			}
			return i;
		}
		
	    // 預設日期設定
		function prepareDay() {
			var Today = new Date();
			var TodayLess = (new Date());
			TodayLess.setDate(TodayLess.getDate() - 7);
			$('#paymentReqStartDate').prop(
					"value",
					TodayLess.getFullYear() + "/" + addZero((TodayLess.getMonth() + 1))
							+ "/" +addZero((TodayLess.getDate())));
			$('#paymentReqEndDate').prop(
					"value",
					Today.getFullYear() + "/" + addZero((Today.getMonth() + 1)) + "/"
							+ addZero((Today.getDate())));
			// 請款期間起始日曆
			$('#paymentReqStartDate').datepicker();
			// 請款期間結束日曆
			$('#paymentReqEndDate').datepicker();
		}
	    
		// 取得固定金額資料
		function getAmtData(rowData) {
			var data = {
					contractNo : rowData.contract_NO,
					electricityMeterNbr : rowData.electricity_METER_NBR,
					appDate : rowData.appDate,
					paymentReqNo : rowData.payment_REQ_NO
			};
			$("#dtlgrid").setGridParam({
				datatype : "json",
				postData : data,
				mtype : 'POST',
				page : 1
			}).jqGrid().trigger("reloadGrid");
		}
		
		// 顯示用電清單明細視窗
		function getDtl(rowData) {
			$.fancybox.open({
				href : CONTEXT_URL + "/pay/pay011/searchDtl",
				type : 'ajax',
				width : $(window).width(),
				height : $(window).height(),
				autoSize : false,
				padding : [ 0, 0, 0, 0 ],
				scrolling : 'yes',
				helpers : {
					overlay : {
						closeClick : false
					},
					title : {
						type : 'float'
					}
				},
				ajax : {
					data : {
						useTypeDscr : rowData.useTypeDscr,
						yearMonth : rowData.year_MONTH,
						cashReqNo : rowData.cashReqNo,
						contractNo : rowData.contract_NO,
						electricityMeterNbr : rowData.electricity_METER_NBR,
						contractName : rowData.contractName,
						electricityDscr : rowData.electricityDscr,
						siteName : rowData.siteName,
						paymentReqBeginDate : rowData.payment_REQ_BEGIN_DATE,
						paymentReqEndDate : rowData.payment_REQ_END_DATE,
						totalUsedDegree : rowData.total_USED_DEGREE,
						usedDegree : rowData.used_DEGREE,
						paymentReqAmt : rowData.payment_REQ_AMT,
						memo : rowData.memo,
						ifAutoDeductDscr : rowData.ifAutoDeductDscr,
						ifNoSiteMappingDscr : rowData.ifNoSiteMappingDscr
					},
					type : "POST",
					error : function(jqXHR, testStatus, errorThrown) {
						alert(jqXHR);
					},
					async : false
				},
				title : "用電清單明細",
				openEffect : 'elastic',
				closeEffect : 'fade',
				afterShow : function() {
				},
				afterClose : function() {
				}
			});
		}
		
		// 顯示用電清單列印視窗
		function openPrintView(electricityMeterNbr,contractNo,paymentReqNo,appDate) {
			$.fancybox.open({
				href : CONTEXT_URL + "/pay/pay011/openPrintView",
				type : 'ajax',
				width : $(window).width(),
				height : $(window).height()-300,
				autoSize : false,
				padding : [ 0, 0, 0, 0 ],
				scrolling : 'yes',
				helpers : {
					overlay : {
						closeClick : false
					},
					title : {
						type : 'float'
					}
				},
				ajax : {
					data : {
						 electricityMeterNbr : electricityMeterNbr,
						 contractNo : contractNo,
						 paymentReqNo : paymentReqNo,
						 appDate : appDate
					},
					type : "POST",
					error : function(jqXHR, testStatus, errorThrown) {
						alert(jqXHR);
					},
					async : false
				},
				title : "用電清單列印",
				openEffect : 'elastic',
				closeEffect : 'fade',
				afterShow : function() {
				},
				afterClose : function() {
				}
			});
		}
	</script>
</head>

<body>
	<div class="row">
		<div id="breadcrumb" class="col-md-12">
			<ol class="breadcrumb">
				<%-- Button import --%>
				<c:import url="/auth/role/menu/func/${currentMenuId}" />
			</ol>
		</div>
	</div>

	<div class="row">
		<div class="col-xs-12 col-sm-12">
			<div class="box ui-draggable ui-droppable">

				<div class="box-header">
					<div class="box-name">
						<i class="fa fa-search"></i> <span>電費分攤明細表</span>
					</div>
					<div class="box-icons">
						<a class="collapse-link"> <i class="fa fa-chevron-up"></i></a>
						<a class="expand-link"> <i class="fa fa-expand"></i></a>
					</div>
					<div class="no-move"></div>
				</div>

				<div class="box-content">
					<form id="searchFrom" class="form-horizontal bootstrap-validator-form" role="form" novalidate="novalidate">
						<div class="form-group">
							<table style="width: 100%">
								<tr>
									<td><label class="col-sm-12 control-label"><span class="s">* </span>維運區 :</label></td>
									<td>
										<div class="col-sm-6">
												<select id="domainSelect" name="domainSelect"  class="populate placeholder">
												<option value="" selected>--請選擇--</option>
												<c:forEach items="${domainSelect}" var="domain">
													<option value="${domain.ID}">${domain.NAME}</option>
												</c:forEach>
											</select>
										</div>
									</td>
									<td><label class="col-sm-12 control-label"><span class="s">* </span>用電方式:</label></td>
									<td>
										<div class="col-sm-6">
												<select id="electricityTypeSelect" name="electricityTypeSelect"  class="populate placeholder">
												<option value="" selected>--請選擇--</option>
												<c:forEach items="${electricityTypeSelect}" var="electricity">
													<option value="${electricity.LOOKUP_CODE}">${electricity.LOOKUP_CODE_DESC}</option>
												</c:forEach>
											</select>
										</div>
									</td>
								</tr>
								<tr>
									<td><div class="clearfix">&nbsp;</div></td>
								</tr>
								<tr>
									<td><label class="col-sm-12 control-label">站台編號 :</label></td>
									<td>
										<div class="col-sm-6">
											<input id="btsSiteId" type="text" value=""  class="form-control" name="btsSiteId" placeholder="請輸入完整的基站編號">
											<div id="selectSitePage"></div>
											<form id="siteTestForm">
												<input type="hidden" name="siteCallBackFun" id="siteCallBackFun" value="receviceSiteData" />
												<input type="hidden" name="filter_DomainId" id="filter_DomainId" value="" />
												<!-- 單選不用傳 -->
												<input type="hidden" name="selectMode" id="selectMode" value="multi" />
											</form>
										</div>
										<i id="siteIdSearch" class="fa fa-search" ></i>
									</td>
									<td><label class="col-sm-12 control-label">電錶號碼 : </label></td>
									<td>
										<div class="col-sm-6">
										<input id="electricityMeterNbr" type="text" value="" class="form-control" name="electricityMeterNbr">
										</div>
									</td>
								</tr>
								<tr>
									<td><div class="clearfix">&nbsp;</div></td>
								</tr>
								<tr>
							        <td><label class="col-sm-12 control-label"> <span class="s">* </span>請款起日 :</label></td>
									<td>
										<div class="col-sm-6">
											<input id="paymentReqStartDate" type="text" value="" 
											   class="form-control" name="paymentReqStartDate"  readonly="readonly">
										</div>
									</td>
									<td><label class="col-sm-12 control-label"> <span class="s">* </span>請款迄日 :</label></td>
									<td>
										<div class="col-sm-6">
											<input id="paymentReqEndDate" type="text" value="" 
											   class="form-control" name="paymentReqEndDate"  readonly="readonly">
										</div>
									</td>
								</tr>
							</table>
						</div>
					</form>
				</div>
			</div>
		</div>

		<div id="ajaxSearchResult" class="col-xs-12">
			<!-- 固定id for window.resize start-->
			<div id="jQgrid" align="center">
				<table id="grid"></table>
				<div id="pager"></div>
			</div>
			<!-- 固定id for window.resize end-->
		</div>
		<tr>
			<td><div class="clearfix">&nbsp;</div></td>
		</tr>
		<div id="ajaxSearchResult" class="col-xs-12">
			<div id="jQgrid" align="center">
				<table id="dtlgrid"></table>
				<div id="dtlpager"></div>
			</div>
		</div>
	</div>
	
</body>
</html>