<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>假扣押作業-新增</title>
<script
	src="<s:url value="/resources/plugins/tinymce/tinymce.min.js" />"></script>
<script
	src="<s:url value="/resources/plugins/tinymce/jquery.tinymce.min.js" />"></script>
	<script	src="<s:url value="/resources/js/validate.js" />"></script>
<script type="text/javascript">
	$(document).ready(function() {
			$("#addGrid").jqGrid({
				datatype : "local",
				autowidth:'true',
				colNames : [ '付款對象', '名稱', '付款方式', '銀行','分行','帳號','扣押總額','付款比例','付款期間起','付款期間迄','支票寄送地址','','','' ],
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
			   		{name : 'check_ADDR',index : 'check_ADDR' , align : 'center',sortable : false},	
			   		{name : 'payMethod',index : 'payMethod' , align : 'center',sortable : false,hidden:true}, 
				    {name : 'bankCode',index : 'bankCode', align : 'center',sortable : false,hidden:true},
				    {name : 'bankBranch',index : 'bankBranch' , align : 'center',sortable : false,hidden:true}],
				multiselect : true,
				caption : "付款對象明細表",
				rownumbers : true,
				onSelectRow : function() {},
				gridComplete : function() {},
				ondblClickRow: function(rowId) {}
			});								
				$(window).resize(function() {
							$(window).unbind("onresize");
							$("#addGrid,.addGrid").setGridWidth($("#jQgrid").width());
							$(window).bind("onresize", this);
					}).trigger('resize');
					
				addMountButEvent();//啟動按鈕事件
			});
			prepareDay();
			selectAddDefault();//下拉選單格式
			
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
		$('#addAttachStartDate').datepicker({
			dateFormat : "yy/mm/dd",
			changeYear : true,
			changeMonth : true,
			showButtonPanel : true,
			showTime : false
		});
		//扣押日結束日曆
		$('#addAttachEndDate').datepicker({
			dateFormat : "yy/mm/dd",
			changeYear : true,
			changeMonth : true,
			showButtonPanel : true,
			showTime : false
		});
		//申請結束日曆
		$('#addAppDate').datepicker({
			dateFormat : "yy/mm/dd",
			changeYear : true,
			changeMonth : true,
			showButtonPanel : true,
			showTime : false
		});
		//扣押日起始日曆						
		$('#paymentStartDate').datepicker({
			dateFormat : "yy/mm/dd",
			changeYear : true,
			changeMonth : true,
			showButtonPanel : true,
			showTime : false
		});
		//扣押日結束日曆
		$('#paymentEndDate').datepicker({
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
		$("#payMethodSelect").select2();
		$("#bankCodeSelect").select2();
		$("#bankBranchCodeSelect").select2();
		$("#payCheck").select2();
	}
	//確認輸入欄位
	function addCheck() {
		var addDomain = $("#addDomain").find("option:selected").val();
		var addDocNo = $("#addDocNo").val();
		var addAttachStartDate = $("#addAttachStartDate").val();
		var addAttachEndDate = $("#addAttachEndDate").val();
		var attachUser = $("#attachUser").val();
		var attachAmt = $("#attachAmt").val();
		var maxAmt = $("#maxAmt").val();
		var appUser = $("#appUser").val();
		var addAppDate = $("#addAppDate").val();
		var personId = $("#personId").val();
		if ((addDomain == null || addDomain == "")) {
			alert('維運區不得為空值');
			return false;
		}
		if ((addDocNo == null || addDocNo == "")) {
			alert('文案編號不得為空值');
			return false;
		}
		if ((addAttachStartDate == null || addAttachStartDate == "")) {
			alert('扣押期間開始日不得為空值');
			return false;
		}
		if ((addAttachEndDate == null || addAttachEndDate == "")) {
			alert('扣押期間結束日不得為空值');
			return false;
		}
		if ((attachUser == null || attachUser == "")) {
			alert('扣押對象證號不得為空值');
			return false;
		}
		if (!isAllNumber(attachAmt)) {
			alert("扣押總額需為正數，不得為負數或其他文字");
			return false;
		}
		if (attachAmt.indexOf("0") > -1 && attachAmt.length==1) {
			alert("扣押總額不得為0");
			return false;
		}
		if (!isAllNumber(maxAmt)) {
			alert("每次請款扣抵金額上限需為正數，不得為負數或其他文字");
			return false;
		}
		if (maxAmt.indexOf("0") > -1 && maxAmt.length==1) {
			alert("每次請款扣抵金額上限不得為0");
			return false;
		}/* 
		if ((addAppDate == null || addAppDate == "")) {
			alert('申請日不得為空值');
			return false;
		} */
		
		if ($("#payCheck").find("option:selected").val() == "") {
			alert('請選擇指定付款');
			return false;
		}
		var selr = jQuery('#addGrid').jqGrid('getGridParam', 'selarrrow');
		if (selr.length == 0 && $("#payCheck").find("option:selected").val() == "Y") {
			alert('請輸入指定付款對象或勾選付款對象明細表內容');
			return false;
		}
		return dateCheck(addAttachStartDate, addAttachEndDate, "扣押期間");
	}
	
	function paymentCheck() {//當指定付款為Y
		var addPayUser = $("#addPayUser").val();
		var addPayUserName = $("#addPayUserName").val();
		var payMethod = $("#payMethodSelect").find("option:selected").val();
		var payMethodText = $("#payMethodSelect").find("option:selected").text();
		var bankCode = $("#bankCodeSelect").find("option:selected").val();
		var bankBranchCode = $("#bankBranchCodeSelect").find("option:selected")
				.val();
		var acctNbr = $("#acctNbr").val();
		var taxAmt = $("#taxAmt").val();
		var paymentProportion = $("#paymentProportion").val();
		var paymentStartDate = $("#paymentStartDate").val();
		var paymentEndDate = $("#paymentEndDate").val();
		var checkAddr = $("#checkAddr").val();

		if (isEmpty(addPayUser)) {
			alert("付款對象不得為空值");
			return false;
		}
		if(CheckCompanyNo(addPayUser) !=true && isTaiwanIDNO(addPayUser)!=true){
			alert("付款對象格式不符合公司統編格式及身分證字號格式");
			return false;
		}
		var ids = jQuery("#addGrid").jqGrid('getDataIDs');
			for (var i = 0; i < ids.length; i++) {
			var rowId = ids[i];
			var rowData = jQuery('#addGrid').jqGrid('getRowData', rowId);
				if(rowData.payment_USER_ID == addPayUser){
					alert("付款對象不得重複");
					return false;
				}
			}
		if (isEmpty(addPayUserName)) {
			alert("付款對象名稱不得為空值");
			return false;
		}
		if (isEmpty(payMethod)) {
			alert("付款方式不得為空值");
			return false;
		}
		if (isEmpty(bankCode) && payMethodText!="支票") {
			alert("銀行不得為空值");
			return false;
		}
		if (isEmpty(bankBranchCode) && payMethodText!="支票") {
			alert("分行不得為空值");
			return false;
		}
		if (isEmpty(checkAddr) && payMethodText=="支票") {
			alert("支票寄送地址不得為空值");
			return false;
		}
		if (isEmpty(acctNbr) && payMethodText!="支票") {
			alert("帳號不得為空值");
			return false;
		}
		if (isEmpty(taxAmt)) {
			alert("扣押總額不得為空值");
			return false;
		}
		if (!isAllNumber(taxAmt)) {
			alert("扣押總額需為正數，不得為負數或其他文字");
			return false;
		}
		if (taxAmt.indexOf("0") > -1 && taxAmt.length==1) {
			alert("扣押總額不得為0");
			return false;
		}
		if (isEmpty(paymentProportion)) {
			alert("付款比例不得為空值");
			return false;
		}
		if (paymentProportion > 100) {
			alert("付款比例不得大於100");
			return false;
		}
		if (paymentProportion.indexOf("0") > -1  && paymentProportion.length==1) {
			alert("付款比例不得為0");
			return false;
		}
		if (!isAllNumber(paymentProportion)) {
			alert("付款比例需為正數，不得為負數或其他文字");
			return false;
		}
		if ((paymentStartDate == null || paymentStartDate == "")) {
			alert('付款期間開始日不得為空值');
			return false;
		}
		if ((paymentEndDate == null || paymentEndDate == "")) {
			alert('付款期間結束日不得為空值');
			return false;
		}
		return dateCheck(paymentStartDate, paymentEndDate, "付款期間");
	}
	//重置		
	function reset() {
		$("#addDomain").select2("val", "");
		$("#addDocNo").prop("value", "");
		$("#addAttachStartDate").prop("value", "");
		$("#addAttachEndDate").prop("value", "");
		$("#addAppDate").prop("value", "");
		$("#paymentStartDate").prop("value", "");
		$("#paymentEndDate").prop("value", "");
		$("#attachUser").prop("value", "");
		$("#attachAmt").prop("value", "");
		$("#maxAmt").prop("value", "");
		$("#madeAmt").prop("value", "");
		$("#memo").prop("value", "");
		$("#appUser").prop("value", "");
		$("#payMethodSelect").select2("val", "");
		$("#bankCodeSelect").select2("val", "");
		$("#bankBranchCodeSelect").select2("val", "");
		$("#payCheck").select2("val", "");
		$("#acctNbr").prop("value", "");
		$("#taxAmt").prop("value", "");
		$("#paymentProportion").prop("value", "");
		$("#addPayUser").prop("value", "");
		$("#addPayUserName").prop("value", "");
		$("#personId").prop("value", "");
		$("#addGrid").jqGrid().clearGridData();
	}
	//新增付款人後的重置
	function resetPayment() {
		$("#payMethodSelect").select2("val", "");
		$("#bankCodeSelect").select2("val", "");
		$("#bankBranchCodeSelect").select2("val", "");
		$("#acctNbr").prop("value", "");
		$("#taxAmt").prop("value", "");
		$("#paymentProportion").prop("value", "");
		$("#paymentStartDate").prop("value", "");
		$("#paymentEndDate").prop("value", "");
		$("#addPayUser").prop("value", "");
		$("#addPayUserName").prop("value", "");
	}
	$("#payCheck").change(onPayCheckChange);
	// 指定付款控制
	function onPayCheckChange() {
		if ($("#payCheck").val() == "" || $("#payCheck").val() == "N") {
			$("#delBtn").prop("disabled", "disabled");
			$("#plusBtn").prop("disabled", "disabled");
			$("#addGrid").jqGrid().clearGridData();
		}
		if ($("#payCheck").val() == "Y") {
			$("#delBtn").prop("disabled", false);
			$("#plusBtn").prop("disabled", false);
		}
	}

	function paymentProportionCheck() {
		var ids = jQuery("#addGrid").jqGrid('getDataIDs');
		var payPortion = "";
		for (var i = 0; i < ids.length; i++) {
			var rowId = ids[i];
			var rowData = jQuery('#addGrid').jqGrid('getRowData', rowId);
			payPortion += rowData.payment_PROPORTION;
			if (payPortion > 100) {
				alert("付款比例加總超過100,請重新操作!");
				return false;
				break;
			}
		}
		return true;
	}

	function getCrTime() {
		var crTime = "";
		$.ajax({
			url : CONTEXT_URL + "/ajax/inv/public/getToday/",
			data : crTime,
			type : "POST",
			dataType : 'json',
			async : false,
			success : function(data) {
				if (data.success) {
					crTime = data.msg
				}
			}
		});
		return crTime;
	}
	function save() {
		var ids = jQuery("#addGrid").jqGrid('getDataIDs');
		var addPaymentAttachArray =[];
		var addPaymentAttachUserArray =[];
		for (var i = 0; i < ids.length; i++) {
			var rowId = ids[i];
			var rowData = jQuery('#addGrid').jqGrid('getRowData', rowId);
			var TbPayProvisionalAttachmentUser = {
				docNo : $("#addDocNo").val(),
				addPayUser : rowData.payment_USER_ID,
				addPayUserName : rowData.payment_USER_NAME,
				acctNbr : rowData.account_NBR,
				taxAmt : rowData.attach_TAX_INCLUSIVE_AMT,
				paymentProportion : rowData.payment_PROPORTION,
				paymentStartDate : rowData.payment_BEGIN_DATE,
				paymentEndDate : rowData.payment_END_DATE,
				payMethod : rowData.payMethod,
				bankCode : rowData.bankCode,
				bankBranch : rowData.bankBranch,
				attachAmt : $("#attachAmt").val(),
				checkAddr : rowData.check_ADDR
			};
			addPaymentAttachUserArray.push(TbPayProvisionalAttachmentUser);
		}
		var TbPayProvisionalAttachment = {
			addDomain : $("#addDomain").find("option:selected").val(),
			addDocNo : $("#addDocNo").val(),
			addAttachStartDate : $("#addAttachStartDate").val(),
			addAttachEndDate : $("#addAttachEndDate").val(),
			attachUser : $("#attachUser").val(),
			attachUserName : $("#attachUserName").val(),
			appUser : $("#appUser").val(),
			appDate : $("#addAppDate").val(),
			memo : $("#memo").val(),
			personId : $("#personId").val(),
			attachAmt : $("#attachAmt").val(),
			maxAmt : $("#maxAmt").val(),
			payCheck : $("#payCheck").find("option:selected").val()
		};
		addPaymentAttachArray.push(TbPayProvisionalAttachment);
		var data = {
			payAttach : JSON.stringify(addPaymentAttachArray),
			payAttachUser : JSON.stringify(addPaymentAttachUserArray),
			addDocNo : $("#addDocNo").val()
		}
		$.ajax({
			url : CONTEXT_URL + "/pay/pay014/saveToTable/",
			data : data,
			type : "POST",
			dataType : 'json',
			success : function(data) {
				if (data.success) {
					alert("文案編號： " + data.msg+" 新增成功！");
					isDetail=true;
					parent.$.fancybox.close();
				} else {
					alert("錯誤：" + data.msg);
				}
			}
		});
			return true;
	}

	function saveMas(crTime) {
		var attachAmt = $("#attachAmt").val();
		var maxAmt = $("#maxAmt").val();
		var data = {
			addDomain : $("#addDomain").find("option:selected").val(),
			addDocNo : $("#addDocNo").val(),
			addAttachStartDate : $("#addAttachStartDate").val(),
			addAttachEndDate : $("#addAttachEndDate").val(),
			attachUser : $("#attachUser").val(),
			attachUserName : $("#attachUserName").val(),
			appUser : $("#appUser").val(),
			appDate : $("#addAppDate").val(),
			memo : $("#memo").val(),
			crTime : crTime,
			personId : $("#personId").val(),
			attachAmt : $("#attachAmt").val(),
			maxAmt : $("#maxAmt").val(),
			payCheck : $("#payCheck").find("option:selected").val()
		};
		$.ajax({
			url : CONTEXT_URL + "/pay/pay014/saveMasterData/",
			data : data,
			type : "POST",
			dataType : 'json',
			success : function(data) {
				alert("文案編號:" + data.msg + ",新增成功！");
				isDetail=true;
				parent.$.fancybox.close();//關閉視窗
			},
			error : function(data) {
				alert(data.msg);
				return false;
			}
		});
		return true;
	}
	function addRow() {
		var addPayUser = $("#addPayUser").val();
		var addPayUserName = $("#addPayUserName").val();
		var payMethod = $("#payMethodSelect").find("option:selected").val();
		var payMethodName = $("#payMethodSelect").find("option:selected")
				.text();
		if(payMethodName == "支票"){
			var bankCode = "";
			var bankCodeDesc = "";
			var bankBranchCode = "";
			var bankBranchCodeDesc = "";
			var checkAddr = $("#checkAddr").val();
		}else{
			var bankCode = $("#bankCodeSelect").find("option:selected").val();
			var bankCodeDesc = $("#bankCodeSelect").find("option:selected").text();
			var bankBranchCode = $("#bankBranchCodeSelect").find("option:selected")
					.val();
			var bankBranchCodeDesc = $("#bankBranchCodeSelect").find(
					"option:selected").text();
			var checkAddr = "";
		}
		var acctNbr = $("#acctNbr").val();
		var taxAmt = $("#taxAmt").val();
		var paymentProportion = $("#paymentProportion").val();
		var paymentStartDate = $("#paymentStartDate").val();
		var paymentEndDate = $("#paymentEndDate").val();
		var gridId = $("#addGrid").jqGrid('getDataIDs');
		$("#addGrid").jqGrid("addRowData", gridId + 1, {
			'payment_USER_ID' : addPayUser,
			'payment_USER_NAME' : addPayUserName,
			'payMethodName' : payMethodName,
			'bankCodeDesc' : bankCodeDesc,
			'bankBranchDesc' : bankBranchCodeDesc,
			'account_NBR' : acctNbr,
			'attach_TAX_INCLUSIVE_AMT' : taxAmt,
			'payment_PROPORTION' : paymentProportion,
			'payment_BEGIN_DATE' : paymentStartDate,
			'payment_END_DATE' : paymentEndDate,
			'payMethod' : payMethod,
			'bankCode' : bankCode,
			'bankBranch' : bankBranchCode,
			'check_ADDR' : checkAddr
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
	}
	//掛載按鈕Event
	function addMountButEvent() {
		//新增付款人
		$('#plusBtn').click(function() {
			if (paymentCheck()) {
				addRow();
				resetPayment();//新增完成後刪除新增之選項內容
			}
		});
		//刪除付款人
		$('#delBtn').click(function() {
			delGrid();
		});
		//儲存
		$('#saveBtn').click(function() {
			var crTime = getCrTime();
			if (addCheck()) {
				if ($("#payCheck").find("option:selected").val() == "Y") {
					if (paymentProportionCheck()) {
						save();//寫入
					}
				} else {
					saveMas(crTime);//寫入主檔
				}
			}
		});
		//取消
		$('#resetBtn').click(function() {
			reset();
		});
	}
	//付款方式異動
	$("#payMethodSelect").change(payMethodChange);
	function payMethodChange(){
	var payMethodText = $("#payMethodSelect").find("option:selected").text();
		if(payMethodText=="支票"){
				$("#bankCodeSelect").prop("disabled", true);
				$("#bankBranchCodeSelect").prop("disabled", true);
				$("#bankCodeSelect").select2("val", "");
				$("#bankBranchCodeSelect").select2("val", "");
				$("#checkAddr").attr("disabled",false);
				$("#acctNbr").prop("disabled", true);
				$("#acctNbr").val("");
			}else if(payMethodText=="匯款"){
				$("#checkAddr").val("");
				$("#checkAddr").attr("disabled",true);
				$("#bankCodeSelect").prop("disabled", false);
				$("#bankBranchCodeSelect").prop("disabled", false);
				$("#acctNbr").prop("disabled", false);
			}
	}
	//銀行異動分行
	$("#bankCodeSelect").change(onbankCodeChange);
	function onbankCodeChange(){
		if($("#bankCodeSelect").val() ==""){
			    $("#bankBranchCodeSelect").html("");
				$("<option value=''>--請選擇--</option>").appendTo(
						"#bankBranchCodeSelect");
				$("#bankBranchCodeSelect").select2();
				return false;
			}
			data ={bankCode : $("#bankCodeSelect").val()};
			$.ajax({
				url : CONTEXT_URL + "/ajax/pay/public/getBranch",
				data : data,
				type : "POST",
				dataType : 'json',
				async:false,
				success : function(data) {
					if (data.success) {
						if (data.rows.length > 0) {
							for (i = 0; i < data.rows.length; i++) {
									$("#bankBranchCodeSelect").append(
											"<option value="+data.rows[i].sub_UNIT_CODE+">"
													+ data.rows[i].sub_NAME
													+ "</option>");								
								}
						}
					}
				}
			});
	}
	$("#attachUser").on('blur',function() {
			if($("#attachUser").val() ==""){
				$("#attachUserName").val("");
				return false;
			}
			data ={attachUserId : $("#attachUser").val()};
			$.ajax({
				url : CONTEXT_URL + "/ajax/pay/public/checkLessor",
				data : data,
				type : "POST",
				dataType : 'json',
				async:false,
				success : function(data) {
					if (data.success) {
						if (data.rows.length > 0) {
							$("#attachUserName").val(data.rows[0].lessor_NAME);
						}
					}else{
							$("#attachUser").val("");
							$("#attachUserName").val("");
							alert("扣押對象證號並不存在！");
						}
				}
			});
		})
</script>
</head>
<body>
<div class="row">
		<div id="breadcrumb" class="col-md-12">
			<ol class="breadcrumb">
				<div class="col-sm-12" id="btnDiv">
					<button class="btn btn-primary btn-label-left" type="button"
						id="saveBtn">
						<span><i class="fa fa-save"></i></span> 儲存
					</button>
					<button class="btn btn-primary btn-label-left" type="button"
						id="resetBtn" onclick="reset();">取消</button>
				</div>
			</ol>
		</div>
	</div>
	<div class="box-header">
		<div class="box-name">
			<i class="fa fa-edit"></i> <span>假扣押作業-新增</span>
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
							<label class="col-sm-2 control-label"><span class="s">* </span>維運區：</label>
							<div class="col-sm-2">
								<select id="addDomain" name="addDomain">
									<option value="" selected>--請選擇--</option>
									<c:forEach items="${domainSelect}" var="addDomain">
										<option value="${addDomain.ID}">${addDomain.NAME}</option>
									</c:forEach>
								</select>
							</div>
							<label class="col-sm-2 control-label"><span class="s">* </span>文案編號：</label>
							<div class="col-sm-2">
								<input id="addDocNo" maxlength="50" type="text"
									class="form-control" name="addDocNo" 
									value="">
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="row">
							<label class="col-sm-2"><span class="s">* </span>扣押期間起：</label>
							<div class="col-sm-2">
								<input id="addAttachStartDate" maxlength="20" type="text"
									 name="addAttachStartDate" readonly="readonly" class="form-control" 
									value="">
									
							</div>
							<div class="col-sm-2">至</div>
							<div class="col-sm-2">
								<input id="addAttachEndDate" maxlength="20" type="text"
									 name="addAttachEndDate" readonly="readonly" class="form-control" 
									value="">
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="row">
							<label class="col-sm-2 control-label"><span class="s">* </span>扣押對象證號：</label>
							<div class="col-sm-2">
								<input id="attachUser" maxlength="20" type="text"
									class="form-control" name="attachUser" 
									value="">
							</div>
							<label class="col-sm-2 control-label"><span class="s">*</span>扣押對象名稱：</label>
							<div class="col-sm-2">
								<input id="attachUserName" type="text"  disabled
									name="attachUserName" class="form-control"
									value="">
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="row">
							<label class="col-sm-2 control-label"><span class="s">* </span>每月最多扣款上限：</label>
							<div class="col-sm-2">
								<input id="maxAmt" maxlength="20" type="text"
									class="form-control" name="maxAmt" 
									>
							</div>
							<label class="col-sm-2 control-label"><span class="s">* </span>扣押總額：</label>
							<div class="col-sm-2">
								<input id="attachAmt" type="text" 
									name="attachAmt" class="form-control"
									value="">
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="row">
							<label class="col-sm-2 control-label">指定付款：</label>
							<div class="col-sm-2">
								<select id="payCheck" name="payCheck">
									<option value="" selected>--請選擇--</option>
									<option value="N" >否</option>
									<option value="Y" >是</option>
								</select>
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="row">
							<div class="col-md">
								<label class="col-sm-2 control-label">說明：</label>
								<div class="col-sm-9">
									<textarea class="form-control" rows="4" id=memo name="memo"
										></textarea>
								</div>
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="row">
							<label class="col-sm-2 control-label">申請人：</label>
							<div class="col-sm-2">
								<input id="appUserName"  type="text"
									class="form-control" name="appUser" disabled
									value="${appUserName}">
									<input id="appUser"  type="hidden"
									class="form-control" name="appUser" 
									value="${appUser}">
							</div>
							<label class="col-sm-2 control-label">申請日期：</label>
							<div class="col-sm-2">
								<input id="addAppDate"  type="text" disabled
									class="form-control" name="addAppDate" 
									value="">
							</div>
						</div>
					</div>					
						<div class="form-group">
							<div class="row">
								<div class="col-md-12">
								<label class="col-sm-2 control-label"></label>
											<button class="btn btn-primary btn-label-left" type="button"
												id="plusBtn" disabled="disabled">新增付款人</button>
											<button class="btn btn-primary btn-label-left" type="button"
												id="delBtn" disabled="disabled">刪除付款人</button>
									<div id="ajaxSearchResult" class="col-xs-12" style="width: 1100px;">
									<div id="jQgrid" align="center">
										<table id="addGrid"></table>
									</div>
								</div>
								</div>
							</div>
							<hr>	
							<div class="form-group">
								<div class="row">							
									<label class="col-sm-2"><span class="s">* </span>付款對象：</label>
											<div class="col-sm-2">
											<input id="addPayUser"  type="text"
													class="form-control" name="addPayUser" value="">
											</div>
											<label class="col-sm-1 control-label"><span class="s">* </span>名稱：</label>
											<div class="col-sm-2">
												<input id="addPayUserName"  type="text"
													class="form-control" name="addPayUserName" value="">
											</div>
										<label class="col-sm-1 control-label"><span class="s">* </span>付款方式：</label>
											<div class="col-sm-2">
												<select id="payMethodSelect" name="payMethodSelect">
													<option value="" selected>--請選擇--</option>
													<c:forEach items="${payMethodSelect}" var="payMethod">
														<option value="${payMethod.LOOKUP_CODE}">${payMethod.LOOKUP_CODE_DESC}</option>
													</c:forEach>
												</select>
											</div>
										</div>
									</div>
										<div class="clearfix">&nbsp;</div>
							<div class="form-group">
								<div class="row">
									<label class="col-sm-2 control-label"><span class="s">* </span>銀行：</label>
									<div class="col-sm-2">
											<select id="bankCodeSelect" name="bankCodeSelect"  class="populate placeholder">
												<option value="" selected>--請選擇--</option>
												<c:forEach items="${bankCodeSelect}" var="bankCode">
													<option value="${bankCode.UNIT_CODE}">${bankCode.UNIT_NAME}</option>
												</c:forEach>
											</select>
											</div>
											<label class="col-sm-1 control-label"><span class="s">* </span>分行：</label>
										<div class="col-sm-2">
												<select id="bankBranchCodeSelect" name="bankBranchCodeSelect"  class="populate placeholder">
												<option value="" selected>--請選擇--</option>
												</select>
										</div>
										<label class="col-sm-1 control-label"><span class="s">* </span>帳號：</label>
										<div class="col-sm-2">
												<input id="acctNbr" maxlength="14" type="text"
													class="form-control" name="acctNbr" value="">
										</div>
									</div>
								</div>
								<div class="clearfix">&nbsp;</div>
								<div class="form-group">
									<div class="row">
									<label class="col-sm-2 control-label"><span class="s">* </span>扣押總額：</label>
										<div class="col-sm-2">
												<input id="taxAmt" maxlength="10" type="text"
													class="form-control" name="taxAmt" value="">
									</div><label class="col-sm-1 control-label"><span class="s">* </span>付款比例：</label>
										<div class="col-sm-2">
												<input id="paymentProportion" maxlength="3" type="text"
													class="form-control" name="paymentProportion" value="">
											</div>		
										<label class="col-sm-1 control-label"><span class="s">* </span>付款期間 :</label>
										<div class="col-sm-2">
												<input id="paymentStartDate" type="text" value=""
													name="paymentStartDate" readonly="readonly" class="form-control" > 至
													<input id="paymentEndDate"
													type="text" value="" name="paymentEndDate" readonly="readonly" class="form-control" >
											</div>
										</div>
									</div>		
									<div class="form-group">
										<div class="row">
											<div class="col-md">
												<label class="col-sm-2 control-label">支票寄送地址：</label>
												<div class="col-sm-9">
													<textarea class="form-control" rows="2" id=checkAddr name="checkAddr" disabled
														></textarea>
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