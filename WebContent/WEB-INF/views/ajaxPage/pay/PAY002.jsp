<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>用電/預付輸入作業</title>
	<script type="text/javascript">
	     // 全域變數控制POP頁面的關閉視窗提醒訊息 
         var isEdit = false;
         var isAdd = false;
	    // 初始載入
		$(document).ready(function() {
			mountButEvent();
			// 預設下拉選單格式
			LoadSelect2Script("<s:url value="/resources/plugins/select2/select2.min.js" />", selectDefault);
			prepareDay();// 申請日期起迄 預設日期
			mountGrid();// 載入表格
		});

		// 掛載表格
		function mountGrid(){
			$("#grid").jqGrid({
				datatype : "local",
				pager : '#pager',
				autowidth:'true',
				url : CONTEXT_URL + "/pay/pay002/search/",
				colNames : [ '請款單號','租約編號', '(租約)名稱', '電錶號碼', '用電方式', '起帳日' ,'請款期間起始','請款期間結束','本次用電數','請款金額','自動扣款','','','','','','','','','',''],
				colModel : [
					{name : 'cashReqNo',index : 'cashReqNo', align : 'center',sortable : false},
				    {name : 'contract_NO',index : 'contract_NO', align : 'center',sortable : false},
				    {name : 'contractName',index : 'contractName' , align : 'center',sortable : false},// 秀中文
				    {name : 'electricity_METER_NBR',index : 'electricity_METER_NBR' , align : 'center',sortable : false},
				    {name : 'electricityDscr',index : 'electricityDscr' , align : 'center',sortable : false}, // 秀中文
				    {name : 'lsSDate',index : 'lsSDate' , align : 'center',sortable : false,
				    	formatter: function(cellvalue, options, rowObject){
				   			if (cellvalue != null && cellvalue != "") {
					   			var stDate = new Date(cellvalue);
								return stDate.getFullYear() + "/" + addZero((stDate.getMonth() + 1)) + "/" + addZero(stDate.getDate()) ;
				   			}else {
				   				return "";
				   			}
			   		}},// 起帳日
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
				    {name : 'used_DEGREE',index : 'used_DEGREE' , align : 'center',sortable : false},
				    {name : 'payment_REQ_AMT',index : 'payment_REQ_AMT' , align : 'center',sortable : false},
				    {name : 'ifAutoDeductDscr',index : 'ifAutoDeductDscr' , align : 'center',sortable : false},
				    {name : 'total_USED_DEGREE',index : 'total_USED_DEGREE',sortable : false,hidden:true},
				    {name : 'memo',index : 'memo',sortable : false,hidden:true},
				    {name : 'seq_NBR',index : 'seq_NBR',sortable : false,hidden:true},
				    {name : 'if_AUTO_DEDUCT',index : 'if_AUTO_DEDUCT',sortable : false,hidden:true},
				    {name : 'if_NO_SITE_MAPPING',index : 'if_NO_SITE_MAPPING',sortable : false,hidden:true},
				    {name : 'electricity_TYPE',index : 'electricity_TYPE',sortable : false,hidden:true},// 用電方式代碼for修改頁面判斷使用
					{name : 'used_degree_day',index : 'used_degree_day', align : 'center',sortable : false,formatter: "number", formatoptions: { defaulValue:"0",decimalPlaces:2},hidden:true},
					{name : 'm_day',index : 'm_day', align : 'center',sortable : false, hidden:true},
					{name : 'last_used_degree',index : 'last_used_degree', align : 'center',sortable : false,formatter: nullForZero,hidden:true},
					{name : 'ratio',index : 'ratio', align : 'center',sortable : false,formatter: "number", formatoptions: { defaulValue:"0",decimalPlaces:2},hidden:true}],
				caption : "用電/預付清單",
				multiselect : true,
				rownumbers : true,
				onSelectRow : function(rowId) {// 點一下帶出清單
					var rowData = jQuery(this).getRowData(rowId);
					getElectricityDtlData(rowData);// 取得用電明細表資料
				},
				gridComplete : function() {
					var ids = $("#grid").jqGrid('getDataIDs');
					var ratio = 0 ;
					var used_degree_day = 0 ;
					var gridRow = null;
					for(var i=0;i < ids.length;i++){
						var cl = ids[i];
						gridRow = $( "#grid" ).jqGrid('getRowData',cl);
						var mDay = gridRow.m_day;
						if (mDay == "" || mDay == "undefined" || mDay == null) {
							mDay = 1;
						}
						used_degree_day = gridRow.used_degree_day/mDay;
						ratio = (gridRow.used_DEGREE - used_degree_day)/used_degree_day;
						jQuery("#grid").jqGrid('setRowData', ids[i], {used_degree_day:used_degree_day,ratio:ratio});
					}
				},
				ondblClickRow: function() {
		        }
			});
			
			$("#dtlgrid").jqGrid({
				datatype : "local",
				pager : '#dtlpager',
				autowidth:'true',
				url : CONTEXT_URL + "/pay/pay002/searchElectricityDtlData/",
				colNames : [ '租約編號','電錶號碼','用電方式', '平均用電數/日','', '上期度數', '本次度數','本次用電度數','超出/減少比率','用電補充說明','對應站台說明'],
				colModel : [
					{name : 'contract_NO',index : 'contract_NO', align : 'center',sortable : false},
					{name : 'electricity_METER_NBR',index : 'electricity_METER_NBR', align : 'center',sortable : false},
					{name : 'electricityDscr',index : 'electricityDscr', align : 'center',sortable : false},// 秀中文
					{name : 'used_degree_day',index : 'used_degree_day', align : 'center',sortable : false,formatter: "number", formatoptions: { defaulValue:"0",decimalPlaces:2}},
					{name : 'm_day',index : 'm_day', align : 'center',sortable : false, hidden:true},
					{name : 'last_used_degree',index : 'last_used_degree', align : 'center',sortable : false,formatter: nullForZero},
					{name : 'total_USED_DEGREE',index : 'total_USED_DEGREE', align : 'center',sortable : false},
					{name : 'used_DEGREE',index : 'used_DEGREE', align : 'center',sortable : false},
					{name : 'ratio',index : 'ratio', align : 'center',sortable : false,formatter: "number", formatoptions: { defaulValue:"0",decimalPlaces:2}},
					{name : 'memo',index : 'memo', align : 'center',sortable : false},
					{name : 'siteMemoDSCR',index : 'siteMemoDSCR', align : 'center',sortable : false}],// 秀中文
				caption : "用電/預付明細表",
				rownumbers : true,
				onSelectRow : function() {},
				gridComplete : function() {
					var ids = $("#dtlgrid").jqGrid('getDataIDs');
					var ratio = 0 ;
					var used_degree_day = 0 ;
					var gridRow = null;
					for(var i=0;i < ids.length;i++){
						var cl = ids[i];
						gridRow = $( "#dtlgrid" ).jqGrid('getRowData',cl);
						var mDay = gridRow.m_day;
						if (mDay == "" || mDay == "undefined" || mDay == null) {
							mDay = 1;
						}
						used_degree_day = gridRow.used_degree_day/mDay;
						ratio = (gridRow.used_DEGREE - used_degree_day)/used_degree_day;
						jQuery("#dtlgrid").jqGrid('setRowData', ids[i], {used_degree_day:used_degree_day,ratio:ratio});
					}
				},
				ondblClickRow: function(rowId) {}
			});
			$(window).resize(function() {
				$(window).unbind("onresize");
				$("#grid,.grid").setGridWidth($("#jQgrid").width() - 10);
				$("#dtlgrid,.dtlgrid").setGridWidth($("#jQgrid").width() - 10);
				$(window).bind("onresize", this);
			}).trigger('resize');
		}
		
		// 載入查詢條件
		function selectData() {
			var data = {
				//domain : $("#domainSelect").find("option:selected").val(),
				contractNo : $("#contractNo").val(),
				electricityMeterNbr : $("#electricityMeterNbr").val(),
				dataType : $("#dataTypeSelect").find("option:selected").val(),
				appStartDate : $("#appStartDate").val(),
				appEndDate : $("#appEndDate").val(),
				appUser : $("#appUserSelect").find("option:selected").val(),
				electricityType : $("#electricityTypeSelect").find("option:selected").val()
			};
			
			$("#grid").setGridParam({datatype:"json", postData:data}).trigger("reloadGrid");
			$("#dtlgrid").jqGrid().clearGridData();
		}

		// 輸入條件檢核
		function check() {
			//var domain = $("#domainSelect").find("option:selected").val();
			var dataType = $("#dataTypeSelect").find("option:selected").val();
			var appStartDate = $("#appStartDate").val();
			var appEndDate = $("#appEndDate").val();
			//var appUser = $("#appUserSelect").find("option:selected").val();
			var electricityType = $("#electricityTypeSelect").find("option:selected").val();
			/* if ((domain == null || domain == "")) {
				alert('維運區不得為空值');
				return false;
			} */
			if ((dataType == null || dataType == "")) {
				alert('資料類型不得為空值');
				return false;
			}
			if ((appStartDate == null || appStartDate == "") || (appEndDate == null || appEndDate == "")) {
				alert('申請起迄日期不得為空值');
				return false;
			}
			/*if ((appUser == null || appUser == "")) {
				alert('申請人不得為空值');
				return false;
			}*/
			if ((dataType == "A") && (electricityType == null || electricityType == "")) {
				alert('用電方式不得為空值');
				return false;
			}
			// 申請日期檢核
			if(Date.parse(appEndDate).valueOf() <= Date.parse(appStartDate).valueOf()){
				alert("申請結束日不能小於開始日!");
				return false;
			}
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

			// 修改
			$('#btn_edit').click(function() {
				var selr = jQuery('#grid').jqGrid('getGridParam','selarrrow');
				// 判斷是否勾選
				if(selr.length==0 || selr.length >1){
					alert("請勾選一筆未產生請款單號的資料,請重新操作!");
					return false;
				}
				if (!selr.length == 0) {
					for (var i = 0; i < selr.length; i++) {
					    var rowData = jQuery('#grid').jqGrid ('getRowData', selr[i]);
						var cashReqNo = rowData.cashReqNo;// 請款單號
					    if ((cashReqNo != "")){
							alert("未產生請款單號的資料才可修改,請重新操作!");
							return false;
						} else {
							openEditPage(rowData);
						}
					}
				}
			});
			
			// 用電/預付輸入
			$('#btn_electricity_add').click(function() {
					openAddPage();
			});

			// 顯示用電/預付明細
			$('#btn_detail').click(function() {
				var selr = jQuery('#grid').jqGrid('getGridParam','selarrrow');
				// 判斷是否勾選
				if(selr.length==0){
					alert("需先選擇一筆用電/預付清單資料才可以顯示明細資料,請重新操作!");
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
						 openDtlPage(rowData);
					}
				}
			});
			
			// 重置按鈕
			$('#btn_reset').click(function() {
					$("#searchFrom").trigger('reset');
					$("#grid").jqGrid().clearGridData();
					$("#dtlgrid").jqGrid().clearGridData();
					$("#domainSelect").select2("val", "");
					$("#contractNo").prop("value", "");
					$("#electricityMeterNbr").prop("value", "");
					$("#dataTypeSelect").select2("val", "");
					$("#appUserSelect").select2("val", "");
					$("#electricityTypeSelect").select2("val", "");
					prepareDay();
			});
		}

	    //  維運區下拉選單連動申請人下拉選單資料
		$("#domainSelect").change(onDomainSelectChange);
	    //  資料類型下拉選單連動控制用電方式下拉選單顯示
		$("#dataTypeSelect").change(onDataTypeSelectChange);
		//================FUNCTION======================
		// 顯示用電/預付清單明細視窗
		function openDtlPage(rowData) {
			$.fancybox.open({
				href : CONTEXT_URL + "/pay/pay002/dtlPage",
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
						contractNo : rowData.contract_NO,
						electricityMeterNbr : rowData.electricity_METER_NBR,
						contractName : rowData.contractName,
						electricityDscr : rowData.electricityDscr,
						lsSDate : rowData.lsSDate,
						paymentReqBeginDate : rowData.payment_REQ_BEGIN_DATE,
						paymentReqEndDate : rowData.payment_REQ_END_DATE,
						totalUsedDegree : rowData.total_USED_DEGREE,
						usedDegree : rowData.used_DEGREE,
						memo : rowData.memo,
						paymentReqAmt : rowData.payment_REQ_AMT,
						ifAutoDeduct : rowData.if_AUTO_DEDUCT,
						ifNoSiteMapping : rowData.if_NO_SITE_MAPPING,
						electricityType : rowData.electricity_TYPE,
						usedDegreeDay : rowData.used_degree_day,
						lastUsedDegree : rowData.last_used_degree,
						ratio : rowData.ratio
					},
					type : "POST",
					error : function(jqXHR, testStatus, errorThrown) {
						alert(jqXHR);
					},
					async : false
				},
				title : "用電/預付明細",
				openEffect : 'elastic',
				closeEffect : 'fade',
				beforeClose : function() {},
				afterClose : function() {}
			});
		}
		
		// 導頁至用電輸入頁
		function openAddPage() {
			$.fancybox.open({
				href : CONTEXT_URL + "/pay/pay002/addPage",
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
					data : {},
					type : "POST",
					error : function(jqXHR, testStatus, errorThrown) {
						alert(jqXHR);
					},
					async : false
				},
				title : "用電/預付輸入作業",
				openEffect : 'elastic',
				closeEffect : 'fade',
				beforeClose : function() {
					if(!isAdd){
						// fancybox關閉前要做的事
						if (!confirm('提醒:請確認資料是否存檔,關閉視窗?')) {
							return false;
						}
					}
				},
				afterClose : function() {
					isAdd = false;
				}
			});
		}
			
		// 導頁至修改頁
		function openEditPage(rowData) {
			$.fancybox.open({
				href : CONTEXT_URL + "/pay/pay002/editPage",
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
						seqNbr : rowData.seq_NBR,
						contractNo : rowData.contract_NO,
						electricityMeterNbr : rowData.electricity_METER_NBR,
						contractName : rowData.contractName,
						electricityDscr : rowData.electricityDscr,
						lsSDate : rowData.lsSDate,
						paymentReqBeginDate : rowData.payment_REQ_BEGIN_DATE,
						paymentReqEndDate : rowData.payment_REQ_END_DATE,
						totalUsedDegree : rowData.total_USED_DEGREE,
						usedDegree : rowData.used_DEGREE,
						memo : rowData.memo,
						paymentReqAmt : rowData.payment_REQ_AMT,
						ifAutoDeduct : rowData.if_AUTO_DEDUCT,
						ifNoSiteMapping : rowData.if_NO_SITE_MAPPING,
						electricityType : rowData.electricity_TYPE,
						usedDegreeDay : rowData.used_degree_day,
						lastUsedDegree : rowData.last_used_degree,
						ratio : rowData.ratio
					},
					type : "POST",
					error : function(jqXHR, testStatus, errorThrown) {
						alert(jqXHR);
					},
					async : false
				},
				title : "用電/預付輸入作業",
				openEffect : 'elastic',
				closeEffect : 'fade',
				beforeClose : function() {
					if(!isEdit){
						// fancybox關閉前要做的事
						if (!confirm('提醒:請確認資料是否存檔,關閉視窗?')) {
							return false;
						}
					}
				},
				afterClose : function() {
					isEdit = false;
					selectData();// 重新查詢
				}
			});
		}

		//  開啟下拉式選單搜尋文字功能
		function selectDefault() {
			$("#searchFrom").find("select").select2();
		}

		//  維運區下拉選單連動申請人下拉選單資料
		function onDomainSelectChange() {
			$("#appUserSelect").html("");
			$("<option value=''>--請選擇--</option>").appendTo("#appUserSelect");
			$("#appUserSelect").select2();
			// 若維運區選擇空值則不取申請人資料
			if ($("#domainSelect").val() == "") {
				return false;
			}
			var data = {
				domain : $("#domainSelect").find("option:selected").prop("value")
			};
			$.ajax({
				url : CONTEXT_URL + "/ajax/pay/public/appUserDomain/",
				data : data,
				type : "POST",
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						if (data.rows.length > 0) {
							for (var i = 0; i < data.rows.length; i++) {
								$("#appUserSelect").append(
										"<option value="+data.rows[i].psn_NO+">"
												+ data.rows[i].chn_NM
												+ "</option>");
							}
						}
					}
				}
			});
		}
		
		// 資料類型下拉選單連動控制用電方式下拉選單顯示
		function onDataTypeSelectChange() {
			$("#electricityTypeSelect").html("");
			$("<option value=''>--請選擇--</option>").appendTo("#electricityTypeSelect");
			$("#electricityTypeSelect").select2();
			// 若資料類型選擇空值或預付金額則不顯示用電方式資料
			if ($("#dataTypeSelect").val() == "") {
				document.getElementById('electricityTypeSelect').disabled = false;
				return false;
			} else if ($("#dataTypeSelect").val() == "B"){
				document.getElementById('electricityTypeSelect').disabled = true;
				return false;
			} else {
				document.getElementById('electricityTypeSelect').disabled = false;
				$("#electricityTypeSelect").append(
						"<c:forEach items='${electricityTypeSelect}' var='electricity'>"+
						"<option value='${electricity.LOOKUP_CODE}'>${electricity.LOOKUP_CODE_DESC}</option>"+
					    "</c:forEach>");
			}
		}
		
		// 時間左補零滿兩位
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
			$('#appStartDate').prop(
					"value",
					TodayLess.getFullYear() + "/" + addZero((TodayLess.getMonth() + 1))
							+ "/" +addZero((TodayLess.getDate())));
			$('#appEndDate').prop(
					"value",
					Today.getFullYear() + "/" + addZero((Today.getMonth() + 1)) + "/"
							+ addZero((Today.getDate())));
			// 申請日期起始日曆
			$('#appStartDate').datepicker();
			// 申請日期結束日曆
			$('#appEndDate').datepicker();
		}
	    
		// 取得用電明細表資料
		function getElectricityDtlData(rowData) {
			var data = {// 請款單號、租約編號、電錶號碼
					cashReqNo : rowData.cashReqNo,
					contractNo : rowData.contract_NO,
					electricityMeterNbr : rowData.electricity_METER_NBR
			};
			$("#dtlgrid").setGridParam({
				datatype : "json",
				postData : data,
				mtype : 'POST',
				page : 1
			}).jqGrid().trigger("reloadGrid");
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
						<i class="fa fa-search"></i> <span>用電/預付輸入作業</span>
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
									<td><label class="col-sm-12 control-label">維運區 :</label></td>
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
									
									<td><label class="col-sm-12 control-label">申請人 :</label></td>
									<td>
										<div class="col-sm-6">
											<select id="appUserSelect" name="appUserSelect" class="populate placeholder">
												<option value="" selected>--請選擇--</option>
											</select>
										</div>
									</td>
								</tr>
								<tr>
									<td><div class="clearfix">&nbsp;</div></td>
								</tr>
								<tr>
									<td><label class="col-sm-12 control-label">電錶號碼 : </label></td>
									<td>
										<div class="col-sm-6">
										<input id="electricityMeterNbr" type="text" value="" class="form-control" name="electricityMeterNbr">
										</div>
									</td>
									
									<td><label class="col-sm-12 control-label">租約編號 : </label></td>
									<td>
										<div class="col-sm-6">
										<input id="contractNo" type="text" value="" class="form-control" name="contractNo">
										</div>
									</td>
									
									
								</tr>
								<tr>
									<td><div class="clearfix">&nbsp;</div></td>
								</tr>
								<tr>
									<td><label class="col-sm-12 control-label"><span class="s">* </span>申請起日 :</label></td>
									<td>
										<div class="col-sm-6">
											<input id="appStartDate" type="text" value="" 
											   class="form-control" name="appStartDate" maxlength="10" readonly="readonly">
										</div>
									</td>
									<td><label class="col-sm-12 control-label"><span class="s">* </span>申請迄日 :</label></td>
									<td>
										<div class="col-sm-6">
											<input id="appEndDate" type="text" value="" 
											   class="form-control" name="appEndDate" maxlength="10" readonly="readonly">
										</div>
									</td>
								</tr>
							    <tr>
									<td><div class="clearfix">&nbsp;</div></td>
								</tr>
								<tr>
									
									
									<td><label class="col-sm-12 control-label"><span class="s">* </span>資料類型 :</label></td>
									<td>
										<div class="col-sm-6">
											<select id="dataTypeSelect" name="dataTypeSelect"  class="populate placeholder">
												<option value="" selected>--請選擇--</option>
											    <option value="A">用電度數</option>
												<option value="B">預付金額</option>
											</select>
										</div>
									</td>
									
									<td><label class="col-sm-12 control-label">用電方式 :</label></td>
									<td>
										<div class="col-sm-6">
												<select id="electricityTypeSelect" name="electricityTypeSelect"  class="populate placeholder">
												<option value="" selected>--請選擇--</option>
											</select>
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