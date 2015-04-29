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
<title>雜項請款作業-新增</title>
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
		//銀行聯動分行	
			$("#addBank").change(onBankSelectChange);
			function onBankSelectChange(){
				resetByBank();
				if ($("#addBank").find("option:selected").val() == "") {
					return false;
				}
				var data = {
					bankCode : $("#addBank").find("option:selected").prop(
							"value")
				};
				$.ajax({
					url : CONTEXT_URL + "/ajax/pay/public/getBranch/",
					data : data,
					type : "POST",
					dataType : 'json',
					success : function(data) {
						if (data.success) {
							if (data.rows.length > 0) {
								for (i = 0; i < data.rows.length; i++) {								
									$("#addBranch").append(
											"<option value="+data.rows[i].sub_UNIT_CODE+">"
													+ data.rows[i].sub_NAME
													+ "</option>");												
								}
							}
						}
					}
				});
			}	
			function resetByBank(){
				$("#addBranch").html("");
				$("<option value=''>--請選擇--</option>").appendTo(
						"#addBranch");
				$("#addBranch").select2();
			}
		//區域聯動申請人
			$("#addDomain").change(onDomainSelectChange);
			function onDomainSelectChange(){
				resetByDomain();
				if ($("#addDomain").find("option:selected").val() == "") {
					return false;
				}
				var data = {
					domain : $("#addDomain").find("option:selected").prop(
							"value")
				};
				$.ajax({
					url : CONTEXT_URL + "/ajax/pay/public/appUserDomain/",
					data : data,
					type : "POST",
					dataType : 'json',
					success : function(data) {
						if (data.success) {
							if (data.rows.length > 0) {
								for (i = 0; i < data.rows.length; i++) {								
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
			function resetByDomain(){
				$("#appUserSelect").html("");
				$("<option value=''>--請選擇--</option>").appendTo(
						"#appUserSelect");
				$("#appUserSelect").select2();
			}
		//掛載表格
		function mountGrid(){
			$("#addGrid").jqGrid({
				datatype : "local",
				autowidth:'true',
				colNames : [ '雜項請款項目','憑證類別', '憑證號碼', '憑證日期', '統一編號','未稅金額','營業稅','所得稅','健保補充費','應付總額','付款方式',
				'付款對象', '付款名稱', '銀行', '分行','匯款帳號','支票兌現日','戶籍地址','備註','','','','',''],
				colModel : [ 
					{name : 'payment_REQUEST_ITEM',index : 'payment_REQUEST_ITEM', align : 'center',sortable : false},
				    {name : 'voucher_TYPE_NAME',index : 'voucher_TYPE_NAME' , align : 'center',sortable : false},
				    {name : 'voucher_NBR',index : 'voucher_NBR' , align : 'center',sortable : false}, 
				    {name : 'voucher_DATE',index : 'voucher_DATE', align : 'center',sortable : false,formatter: function(cellvalue, options, rowObject){
				   			if (cellvalue != null && cellvalue != "") {
					   			var stDate = new Date(cellvalue);
								return stDate.getFullYear() + "/" + (stDate.getMonth() + 1) + "/" + stDate.getDate() ;
				   			}else {
				   				return "";
				   			}
			   			}},
				    {name : 'vat_NUMBER',index : 'vat_NUMBER' , align : 'center',sortable : false}, 
				    {name : 'tax_EXCLUSIVE_AMT',index : 'tax_EXCLUSIVE_AMT' , align : 'center',sortable : false},
				    {name : 'total_BUSINESS_TAX',index : 'total_BUSINESS_TAX', align : 'center',sortable : false},
				    {name : 'total_INCOME_TAX',index : 'total_INCOME_TAX',sortable : false},
				    {name : 'total_NHI_AMT',index : 'total_NHI_AMT',sortable : false},
				    {name : 'totalAmt',index : 'totalAmt',sortable : false},
				    {name : 'payment_METHOD_NAME',index : 'payment_METHOD_NAME',sortable : false},
				    {name : 'payment_USER_ID',index : 'payment_USER_ID', align : 'center',sortable : false},
				    {name : 'payment_USER_NAME',index : 'payment_USER_NAME' , align : 'center',sortable : false}, 
				    {name : 'bank_CODE_NAME',index : 'bank_CODE_NAME' , align : 'center',sortable : false},
				    {name : 'bank_BRANCH_CODE_NAME',index : 'bank_BRANCH_CODE_NAME', align : 'center',sortable : false},
				    {name : 'account_NBR',index : 'account_NBR',sortable : false},
				    {name : 'check_CASH_DATE',index : 'check_CASH_DATE',sortable : false},
				    {name : 'resident_ADDRESS',index : 'resident_ADDRESS',sortable : false},
				    {name : 'memo',index : 'memo',sortable : false},
				    {name : 'voucher_TYPE',index : 'voucher_TYPE' , align : 'center',sortable : false,hidden:true},
				    {name : 'checkShare',index : 'checkShare' , align : 'center',sortable : false,hidden:true},
				    {name : 'payment_METHOD',index : 'payment_METHOD' , align : 'center',sortable : false,hidden:true},
				    {name : 'bank_CODE',index : 'bank_CODE' , align : 'center',sortable : false,hidden:true},
				    {name : 'bank_BRANCH_CODE',index : 'bank_BRANCH_CODE' , align : 'center',sortable : false,hidden:true}
				],
				caption : "雜項清單",
				multiselect : true,
				rownumbers : true,
				onSelectRow : function(rowId) {},
				gridComplete : function() {}
			});
			
			$("#tempGrid").jqGrid({
				datatype : "local",
				autowidth:'true',
				colNames : ['偽key','基站編號','未稅金額','營業稅','分攤總額','',''],
				colModel : [ 
					{name : 'tempKey',index : 'tempKey', align : 'center',sortable : false},
					{name : 'site_ID',index : 'site_ID', align : 'center',sortable : false},
				    {name : 'repartition_AMT',index : 'repartition_AMT' , align : 'center',sortable : false},
				    {name : 'repartition_AMTP',index : 'repartition_AMTP' , align : 'center',sortable : false}, 
				    {name : 'spTotalAmt',index : 'spTotalAmt', align : 'center',sortable : false},
				    {name : 'location_ID',index : 'location_ID',width : 80 , align : 'center',sortable : false},
				    {name : 'bts_SITE_ID',index : 'bts_SITE_ID',width : 80 , align : 'center',sortable : false}
				],
				caption : "隱藏清單",
				multiselect : true,
				rownumbers : true,
				onSelectRow : function(rowId) {},
				gridComplete : function() {}
			});
				$(window).resize(function() {
				$(window).unbind("onresize");
				$("#addGrid,.addGrid").setGridWidth($("#jQgrid").width() - 10);
				$("#tempGrid,.tempGrid").setGridWidth($("#jQgrid").width() - 10);
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
			var month =(Today.getMonth() + 1);
			if (month < 10) {
				month = "0" + month;
			}
		
			$('#addAppDate').prop(
					"value",
					Today.getFullYear() + "/" + (Today.getMonth() + 1) + "/"
							+ (Today.getDate()));
			$('#addApplyYM').prop(
					"value",
					Today.getFullYear() + "" + month);	
			$('#addVoucherDate').prop(
					"value",
					Today.getFullYear() + "/" + (Today.getMonth() + 1) + "/"
							+ (Today.getDate()));	
			$('#addCheckCashDate').prop(
					"value",
					Today.getFullYear() + "/" + (Today.getMonth() + 1) + "/"
							+ (Today.getDate()));
			//憑證日期						
			$('#addVoucherDate').datepicker({
				dateFormat : "yy/mm/dd",
				changeYear : true,
				changeMonth : true,
				showButtonPanel : true,
				showTime : false
			});	
			$('#addCheckCashDate').datepicker({
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
		$("#appUserSelect").select2();
		$("#addVoucher").select2();
		$("#addPayment").select2();
		$("#addBranch").select2();
		$("#addBank").select2();
		$("#addPrItem").select2();
	}
	
	//新增支票後的重置
	function resetPayment() {
		$("#addVoucher").select2("val", "");
		$("#addPayment").select2("val", "");
		$("#addBranch").select2("val", "");
		$("#addBank").select2("val", "");
		$("#addPrItem").select2("val", "");
		prepareDay(); 
		$("#addVoucherNbr").prop("value", "");
		$("#addVatNbr").prop("value", "");
		$("#addTaxExclusiveAmt").prop("value","");
		$("#addBusinessTax").prop("value", "");
		$("#addIncomeTax").prop("value","");		
		$("#addNhiAmt").prop("value","");
		$("#addTotalAmt").prop("value","");
		$("#addUserId").prop("value", "");
		$("#addUserName").prop("value", "");
		$("#addAcctNbr").prop("value","");
		$("#addResidentAddress").prop("value", "");			
		$("#memo").prop("value","");
	}
	
	function addRow() {
		var prItem = $("#addPrItem").find("option:selected").val();//
		var prItemText = $("#addPrItem").find("option:selected").text();
		var voucher = $("#addVoucher").find("option:selected").val();//
		var voucherText = $("#addVoucher").find("option:selected").text();
		var voucherNbr = $("#addVoucherNbr").val();
		var voucherDate = $("#addVoucherDate").val();
		var vatNbr = $("#addVatNbr").val();
		var taxExclusiveAmt = $("#addTaxExclusiveAmt").val();
		var businessTax = $("#addBusinessTax").val();
		var incomeTax = $("#addIncomeTax").val();
		var nhiAmt = $("#addNhiAmt").val();
		var totalAmt = $("#addTotalAmt").val();
		var payment = $("#addPayment").find("option:selected").val();//
		var paymentText = $("#addPayment").find("option:selected").text();
		var userId = $("#addUserId").val();
		var userName = $("#addUserName").val();
		var bank = $("#addBank").find("option:selected").val();//
		var bankText = $("#addBank").find("option:selected").text();
		var branch = $("#addBranch").find("option:selected").val();//
		var branchText = $("#addBranch").find("option:selected").text();
		var acctNbr = $("#addAcctNbr").val();
		var checkCashDate = $("#addCheckCashDate").val();
		var residentAddress = $("#addResidentAddress").val();
		var memo = $("#memo").val();
		var gridId = $("#addGrid").jqGrid('getDataIDs');
		$("#addGrid").jqGrid("addRowData", gridId + 1, {
			'payment_REQUEST_ITEM' : prItemText,
			'voucher_TYPE' : voucher,
			'voucher_TYPE_NAME' : voucherText,
			'voucher_NBR' : voucherNbr,
			'voucher_DATE' : voucherDate,
			'vat_NUMBER' : vatNbr,
			'tax_EXCLUSIVE_AMT' : taxExclusiveAmt,
			'total_BUSINESS_TAX' : businessTax,
			'total_INCOME_TAX' : incomeTax,
			'total_NHI_AMT' : nhiAmt,
			'totalAmt' : totalAmt,
			'payment_METHOD_NAME' : paymentText,
			'payment_USER_ID' : userId,
			'payment_USER_NAME' : userName,
			'bank_CODE_NAME' : bankText,
			'bank_BRANCH_CODE_NAME' : branchText,
			'account_NBR' : acctNbr,
			'check_CASH_DATE' : checkCashDate,
			'resident_ADDRESS' : residentAddress,
			'memo' : memo,
			'checkShare' :"N",
			'payment_METHOD' : payment,
			'bank_CODE' : bank,
			'bank_BRANCH_CODE' : branch
		}, "first");
	}
	
	function delGrid() {
		var selr = jQuery('#addGrid').jqGrid('getGridParam', 'selarrrow');
		if (!selr.length == 0) {
			if (confirm("是否確定要刪除")) {
			var ids = jQuery("#tempGrid").jqGrid('getDataIDs');
			//刪除暫存
			for (var i = 0; i < selr.length; i++) {
				share = jQuery('#addGrid').jqGrid('getRowData', selr[i]);
				idTemp = share.voucher_TYPE+share.voucher_NBR+share.vat_NUMBER;
				for (var iTemp = 0; iTemp < ids.length; iTemp++) {//刪除雜項明細
						var rowId = ids[iTemp];
						var rowData = jQuery('#tempGrid').jqGrid('getRowData', rowId);
					if (rowData.tempKey == idTemp) {
						$('#tempGrid').jqGrid("delRowData", rowId);
					}
				}
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
		var addDomain = $("#addDomain").find("option:selected").val();
		var appUser = $("#appUserSelect").find("option:selected").val();				
		if ((addDomain == null || addDomain == "")) {
			alert('維運區不得為空值');
			return false;
		}
		if ((appUser == null || appUser == "")) {
			alert('申請人員不得為空值');
			return false;
		}	
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
	function paymentcheck(){
		var prItem = $("#addPrItem").find("option:selected").val();//
		var prItemText = $("#addPrItem").find("option:selected").text();
		var voucher = $("#addVoucher").find("option:selected").val();//
		var voucherText = $("#addVoucher").find("option:selected").text();
		var voucherVal1 = $("#addVoucher").find("option:selected").prop("val1");
		var voucherVal2 = $("#addVoucher").find("option:selected").prop("val2");//未使用 補充健保費設置
		var voucherNbr = $("#addVoucherNbr").val();
		var voucherDate = $("#addVoucherDate").val();
		var vatNbr = $("#addVatNbr").val();
		var taxExclusiveAmt = $("#addTaxExclusiveAmt").val();
		var payment = $("#addPayment").find("option:selected").val();//
		var paymentText = $("#addPayment").find("option:selected").text();
		var userId = $("#addUserId").val();
		var userName = $("#addUserName").val();
		var bank = $("#addBank").find("option:selected").val();//
		var bankText = $("#addBank").find("option:selected").text();
		var branch = $("#addBranch").find("option:selected").val();//
		var branchText = $("#addBranch").find("option:selected").text();
		var acctNbr = $("#addAcctNbr").val();
		var checkCashDate = $("#addCheckCashDate").val();
		var residentAddress = $("#addResidentAddress").val();
		
		if (isEmpty(prItem)) {
			alert("請選擇雜項請款項目");
			return false;
		}
		if (isEmpty(voucher)) {
			alert("請選擇憑證類別");
			return false;
		}
		if(voucherVal1=="Y"){//代扣所得稅
			//確認身分證號
			if (isEmpty(userId)) {
				alert("請輸入身分證號");
				return false;
			}
			if (isTaiwanIDNO(userId)) {
				alert("請檢核身分證號格式");
				return false;
			}
			if (isEmpty(residentAddress)) {
				alert("請輸入戶籍地址");
				return false;
			}	
		}else if(voucherVal1=="N"){
			if (isEmpty(userId)) {
				alert("請確認身分證號內容不得為空");
				return false;
			}
			if (isEmpty(userName)) {
				alert("請確認付款名稱內容不得為空");
				return false;
			}
		}
		if (isEmpty(voucherNbr)) {
			alert("請輸入憑證號碼");
			return false;
		}
		if (!isAllNumber(voucherNbr)) {
			alert("憑證號碼需為正數，不得為負數或其他文字");
			return false;
		} 
		if (isEmpty(vatNbr)) {
			alert("請輸入統一編號");
			return false;
		}
		if (!isAllNumber(vatNbr)) {
			alert("統一編號需為正數，不得為負數或其他文字");
			return false;
		} 
		if (!CheckCompanyNo(vatNbr)) {
			alert("統一編號格式不符");
			return false;
		}		
		if (isEmpty(taxExclusiveAmt)) {
			alert("請輸入未稅金額");
			return false;
		}
		if(!isAllNumber(taxExclusiveAmt)){			
				alert("未稅金額只能輸入數字");
				return false;
			}
		if (isEmpty(payment)) {
			alert("請選擇付款方式");
			return false;
		}	
		if (paymentText=="匯款") {//電匯
			if (isEmpty(bank)) {
				alert("請選擇銀行");
				return false;
			}
			if (isEmpty(branch)) {
				alert("請選擇分行");
				return false;
			}
			if (isEmpty(acctNbr)) {
				alert("請輸入匯款帳號");
				return false;
			}	
		}
		return true;	
	}
	function getPayCashReqNo(initWord) {
		var cashReqNo = "";
		data ={initWord : initWord};
		$.ajax({
			url : CONTEXT_URL + "/pay/pay005/getPayCashReqNo",
			data : data,
			type : "POST",
			dataType : 'json',
			async:false,
			success : function(data) {
				if (data.success) {
					cashReqNo = data.msg
				}
			}
		});
		return cashReqNo;
	}
	function getPayVoucherNo(initWord) {
		var payVoucherNo = "";
		data ={initWord : initWord};
		$.ajax({
			url : CONTEXT_URL + "/pay/pay005/getPayVoucherNo",
			data : data,
			type : "POST",
			dataType : 'json',
			async:false,
			success : function(data) {
				if (data.success) {
					payVoucherNo = data.msg
				}
			}
		});
		return payVoucherNo;
	}
	
	//合併單一流程！！
	function applyAll(cashReqNo,voucherNo) {
		var selr = jQuery('#addGrid').jqGrid('getGridParam', 'selarrrow');
		var appUser = $("#appUserSelect").find("option:selected").val();
		var Today = new Date($('#addAppDate').val());
		var appDate=Today.getFullYear() + "-" + (Today.getMonth()+1) + "-"
					+ (Today.getDate());										
		var addPaymentReqArray =[];
		var addPaymentReqVouArray =[];
		var addCashReqArray =[];
		var addPayment = [];
		if (!selr.length == 0) {
			for(var i=0;i<selr.length;i++){
				//TB_PAY_PAYMENT_REQUEST
				var myrow = jQuery('#addGrid').jqGrid('getRowData',selr[i]);
				var TbPayPaymentRequestDTO = {
					"cash_REQ_NO" : cashReqNo,
					"payment_REQ_BEGIN_DATE" : appDate,
					"payment_REQ_END_DATE" : appDate,
					"payment_REQ_NO" :"",
					"vatNbr" : myrow.vat_NUMBER,
					"voucherNbr" : myrow.voucher_NBR,
					"voucherType" : myrow.voucher_TYPE
				};
				addPaymentReqArray.push(TbPayPaymentRequestDTO);
				//TB_PAY_PAYMENT_REQUEST_VOUCHER
				var vouToday = new Date(myrow.voucher_DATE);			
				var voucherDate=vouToday.getFullYear() + "-" + (vouToday.getMonth()+1) + "-"+ (vouToday.getDate());
					var TbPayPaymentRequestVoucher = {
						"process_TYPE" : 'CRQ',
						"voucher_NO" : voucherNo,
						"voucher_TYPE" : myrow.voucher_TYPE,
						"voucher_NBR" : myrow.voucher_NBR,
						"voucher_DATE" : voucherDate,
						"vat_NUMBER" : myrow.vat_NUMBER,
						"tax_EXCLUSIVE_AMT" : myrow.tax_EXCLUSIVE_AMT,
						"business_TAX" : myrow.total_BUSINESS_TAX
					};
				addPaymentReqVouArray.push(TbPayPaymentRequestVoucher);
				var idTemp = myrow.voucher_TYPE+myrow.voucher_NBR+myrow.vat_NUMBER;//組合暫存key
				 var ids = jQuery("#tempGrid").jqGrid('getDataIDs');//用暫存序號取值
					for (var i2 = 0; i2 < ids.length; i2++) {
						var rowId = ids[i2];
						var rowData = jQuery('#tempGrid').jqGrid('getRowData', rowId);
						if (rowData.tempKey == idTemp) {
						//TB_PAY_PAYMENT + TB_PAY_PAYMENT_REQUEST_DTL + TB_PAY_VOUCHER_CREDIT + TB_PAY_REPARTITION
						var TbPayPaymentDTO = {
								'siteId' : rowData.site_ID,
								'repartition_AMT' : rowData.repartition_AMT,
								'repartition_AMTP' : rowData.repartition_AMTP,
								'spTotalAmt' : rowData.spTotalAmt,
								'tempKey' : rowData.tempKey,
								'locationId' : rowData.location_ID,
								'payment_REQ_USER_ID' : myrow.payment_USER_ID,
								'payment_USER_ID' : myrow.payment_USER_ID,
								'payment_USER_NAME' : myrow.payment_USER_NAME,
								'payment_METHOD' : myrow.payment_METHOD,
								'bank_CODE' : myrow.bank_CODE,
								'bank_BRANCH_CODE' : myrow.bank_BRANCH_CODE,
								'memo' : myrow.memo,
								'resident_ADDRESS' :myrow.resident_ADDRESS,
								'check_CASH_DATE' : myrow.check_CASH_DATE,
								'account_NBR' : myrow.account_NBR
							};
							addPayment.push(TbPayPaymentDTO);							
						}
					} 
				}
				//TB_PAY_CASH_REQUIREMENT
				var TbPayCashRequirement = {
					"cash_REQ_NO" : cashReqNo,
					"process_TYPE" : 'MIS',
					"app_USER" : appUser,
					"app_DATE" : appDate,
					"year_MONTH" : Today.getFullYear()+""+(Today.getMonth()+1),
					"status" : "A",
					"domain" : $("#addDomain").find("option:selected").val()
				};
				addCashReqArray.push(TbPayCashRequirement);	
				var data ={
					paymentVou : JSON.stringify(addPaymentReqVouArray),
					paymentReq : JSON.stringify(addPaymentReqArray),
					cashReq : JSON.stringify(addCashReqArray),
					paymentDTO : JSON.stringify(addPayment),
					cashReqNo : cashReqNo,
					yearMonth : Today.getFullYear()+""+(Today.getMonth()+1)
				}
				 $.ajax({
					url : CONTEXT_URL + "/pay/pay005/saveToTable/",
					data : data,
					type : "POST",
					dataType : 'json',
					async:false,
					success : function(data) {
						if(data.success){
							alert("新增成功,請款單號 : " + cashReqNo);
							isDetail=true;
							parent.$.fancybox.close();//關閉視窗
						}else{
							alert("新增錯誤 訊息: " + data.msg);
							isDetail=true;
							parent.$.fancybox.close();//關閉視窗
						}
					},
					error : function(data) {
						alert(data.msg);
					}
				});	 	
			} else {
				alert("未勾選要儲存的資料列,請重新操作!");
			}
		return true;
	}
	function shareBtn(){
		var selr = jQuery('#addGrid').jqGrid('getGridParam', 'selarrrow');
		var share  = "";
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
				if(share.tax_EXCLUSIVE_AMT == ""){
					alert("明細項次："+selr[i]+" 請輸入未稅金額！");
					return;
				}
				//組合畫面上的暫存key
				idTemp = share.voucher_TYPE+share.voucher_NBR+share.vat_NUMBER;
			}
			openPageDialogFrame("showSharePag", "/pay/pay005/share",
					"基站分攤", idTemp,share.totalAmt ,$("#callBackFun").val(),"A");
	}
	//掛載按鈕Event
	function addMountButEvent() {
		//新增雜項
		$('#plusBtn').click(function() {
			if (paymentcheck()) {
				addRow();
				resetPayment();//新增完成後刪除新增之選項內容
			}
		});
		//刪除雜項
		$('#delBtn').click(function() {
			delGrid();
		});
		//分攤
		 $('#shareBtn').click(function() {	
			shareBtn();
		}); 
		//請款
		$('#applyBtn').click(function() {
			if(saveCheck()){
				var cashReqNo=getPayCashReqNo("MIP");
				var voucherNo=getPayVoucherNo("CRQ");
				applyAll(cashReqNo,voucherNo); 
			}				
		});
	}

	//計算金額
	$("#addTaxExclusiveAmt").on('blur',function() {
				var spTax = $("#addTaxExclusiveAmt").val();
				if (!isAllNumber(spTax)) {
					alert("未稅金額只能輸入數字");
					$("#addBusinessTax").val("");
					$("#addIncomeTax").val("");
					$("#addNhiAmt").val("");
					$("#addTotalAmt").val("");
					return false;
				}
				var data={oriAmt : spTax};
				 $.ajax({
					url : CONTEXT_URL + "/pay/pay005/getAmt/",
					data : data,
					type : "POST",
					dataType : 'json',
					async:false,
					success : function(data) {
						if(data.success){
							$("#addBusinessTax").val(data.maps.businessTax);
							$("#addIncomeTax").val(data.maps.incomeTax);
							$("#addNhiAmt").val(data.maps.nhiAmt);
							$("#addTotalAmt").val(
									data.maps.businessTax + 
									data.maps.incomeTax + 
									data.maps.nhiAmt
											+ (spTax * 1));
						}else{
							alert("修改錯誤 訊息: " + data.msg);
						}
					}
				});
			});

	//接收SP1回傳值並寫入Temp
	function showMsData(psnData, key) {
		var obj = JSON.parse(psnData);
		var ids = jQuery("#addGrid").jqGrid('getDataIDs');
		for (var i = 0; i < obj.spObject.length; i++) {
			var gridId = $("#tempGrid").jqGrid('getDataIDs');
			$("#tempGrid").jqGrid("addRowData", gridId + 1, {
				'tempKey' : key,
				'site_ID' : obj.spObject[i].site_ID,
				'repartition_AMT' : obj.spObject[i].repartition_AMT,
				'repartition_AMTP' : obj.spObject[i].repartition_AMTP,
				'spTotalAmt' : obj.spObject[i].spTotalAmt,
				'location_ID' : obj.spObject[i].location_ID,
				'bts_SITE_ID' : obj.spObject[i].bts_SITE_ID
			});
		}
		//更新明細資料暫存狀態
		for (var iTemp = 0; iTemp < ids.length; iTemp++) {
			var rowId = ids[iTemp];
			var rowData = jQuery('#addGrid').jqGrid('getRowData', rowId);
			var tempKey = rowData.voucher_TYPE+rowData.voucher_NBR+rowData.vat_NUMBER;
			if (tempKey == key) {
				$("#addGrid").jqGrid("setRowData", rowId, {'checkShare' : "Y"});
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
						id="applyBtn">儲存</button>
				</div>
			</ol>
		</div>
	</div>
	<div class="box-header">
		<div class="box-name">
			<i class="fa fa-edit"></i> <span>雜項請款作業-新增</span>
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
					<input type="hidden" id="callBackFun" name="callBackFun"
						value="showMsData" />
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
										<td><label class="col-sm-12 control-label"><span
												class="s">* </span>申請人：</label></td>
										<td><div class="col-sm-12">
												<select id="appUserSelect" name="appUserSelect">
													<option value="" selected>--請選擇--</option>
												</select>
											</div></td>
									</tr>
									<tr>
										<td><div class="clearfix">&nbsp;</div></td>
									</tr>
									<tr>
										<td><label class="col-sm-12 control-label">申請日期：</label></td>
										<td>
											<div class="col-sm-12">
												<input id="addAppDate" type="text" disabled
													class="form-control" name="addAppDate" value="">
											</div>
										</td>
										<td><label class="col-sm-12 control-label">請款年月：</label></td>
										<td>
											<div class="col-sm-12">
												<input id="addApplyYM" type="text" disabled
													class="form-control" name="addApplyYM" value="">
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
										<td><label class="col-sm-12 control-label"><span
												class="s">* </span>雜項請款項目：</label></td>
										<td>
											<div class="col-sm-12">
												<select id="addPrItem" name="addPrItem"
													class="populate placeholder">
													<option value="" selected>--請選擇--</option>
													<c:forEach items="${prItemList}" var="var">
														<option value="${var.EXP_ITEM}">${var.EXP_ITEM_DESC}</option>
													</c:forEach>
												</select>
											</div>
										</td>
										<td><label class="col-sm-12 control-label"><span
												class="s">* </span>憑證類別：</label></td>
										<td>
											<div class="col-sm-12">
												<select id="addVoucher" name="addVoucher"
													class="populate placeholder">
													<option value="" selected>--請選擇--</option>
													<c:forEach items="${voucherList}" var="var">
														<option value="${var.LOOKUP_CODE}" val1="${var.TXT_VAL_1}"
															val2="${var.TXT_VAL_2}">${var.LOOKUP_CODE_DESC}</option>
													</c:forEach>
												</select>
											</div>
										</td>
										<td><label class="col-sm-12 control-label"><span
												class="s">* </span>憑證號碼：</label></td>
										<td>
											<div class="col-sm-12">
												<input id="addVoucherNbr" type="text" class="form-control"
													name="addVoucherNbr" value="">
											</div>
										</td>
									</tr>
									<tr>
										<td><div class="clearfix">&nbsp;</div></td>
									</tr>
									<tr>
										<td><label class="col-sm-12 control-label"><span
												class="s">* </span>憑證日期：</label></td>
										<td><div class="col-sm-12">
												<input type="text" id="addVoucherDate" value=""
													readonly="readonly" class="form-control" placeholder="憑證日期" />
											</div></td>
										<td><label class="col-sm-12 control-label"><span
												class="s">* </span>統一編號：</label></td>
										<td><div class="col-sm-12">
												<input id="addVatNbr" type="text" class="form-control"
													name="addVatNbr" value="">
											</div></td>
										<td><label class="col-sm-12 control-label"><span
												class="s">* </span>未稅金額：</label></td>
										<td><div class="col-sm-12">
												<input id="addTaxExclusiveAmt" type="text"
													class="form-control" name="addTaxExclusiveAmt" value="">
											</div></td>
									</tr>
									<tr>
										<td><div class="clearfix">&nbsp;</div></td>
									</tr>
									<tr>
										<td><label class="col-sm-12 control-label"><span
												class="s">* </span>營業稅：</label></td>
										<td><div class="col-sm-12">
												<input id="addBusinessTax" type="text" class="form-control"
													disabled name="addBusinessTax" value="">
											</div></td>
										<td><label class="col-sm-12 control-label"><span
												class="s">* </span>所得稅：</label></td>
										<td><div class="col-sm-12">
												<input id="addIncomeTax" type="text" class="form-control"
													disabled name="addIncomeTax" value="">
											</div></td>
										<td><label class="col-sm-12 control-label"><span
												class="s">* </span>健保補充費：</label></td>
										<td><div class="col-sm-12">
												<input id="addNhiAmt" type="text" class="form-control"
													disabled name="addNhiAmt" value="">
											</div></td>
									</tr>
									<tr>
										<td><div class="clearfix">&nbsp;</div></td>
									</tr>
									<tr>
										<td><label class="col-sm-12 control-label"><span
												class="s">* </span>應付總額：</label></td>
										<td><div class="col-sm-12">
												<input id="addTotalAmt" type="text" class="form-control"
													disabled name="addTotalAmt" value="">
											</div></td>
										<td><label class="col-sm-12 control-label"><span
												class="s">* </span>付款方式：</label></td>
										<td><div class="col-sm-12">
												<select id="addPayment" name="addPayment"
													class="populate placeholder">
													<option value="" selected>--請選擇--</option>
													<c:forEach items="${paymentList}" var="var">
														<option value="${var.LOOKUP_CODE}">${var.LOOKUP_CODE_DESC}</option>
													</c:forEach>
												</select>
											</div></td>
										<td><label class="col-sm-12 control-label"><span
												class="s">* </span>付款對象：</label></td>
										<td><div class="col-sm-12">
												<input id="addUserId" type="text" class="form-control"
													name="addUserId" value="">
											</div></td>
									</tr>
									<tr>
										<td><div class="clearfix">&nbsp;</div></td>
									</tr>
									<tr>
										<td><label class="col-sm-12 control-label"><span
												class="s">* </span>付款名稱：</label></td>
										<td><div class="col-sm-12">
												<input id="addUserName" type="text" class="form-control"
													name="addUserName" value="">
											</div></td>
										<td><label class="col-sm-12 control-label"><span
												class="s">* </span>銀行：</label></td>
										<td><div class="col-sm-12">
												<select id="addBank" name="addBank"
													class="populate placeholder">
													<option value="" selected>--請選擇--</option>
													<c:forEach items="${bankList}" var="var">
														<option value="${var.UNIT_CODE}">${var.UNIT_NAME}</option>
													</c:forEach>
												</select>
											</div></td>
										<td><label class="col-sm-12 control-label"><span
												class="s">* </span>分行：</label></td>
										<td><div class="col-sm-12">
												<select id="addBranch" name="addBranch"
													class="populate placeholder">
													<option value="" selected>--請選擇--</option>
													<c:forEach items="${branchList}" var="var">
														<option value="${var.SUB_UNIT_CODE}">${var.SUB_NAME}</option>
													</c:forEach>
												</select>
											</div></td>
									</tr>
									<tr>
										<td><div class="clearfix">&nbsp;</div></td>
									</tr>
									<tr>
										<td><label class="col-sm-12 control-label"><span
												class="s">* </span>匯款帳號：</label></td>
										<td><div class="col-sm-12">
												<input id="addAcctNbr" type="text" class="form-control"
													name="addAcctNbr" value="">
											</div></td>
										<td><label class="col-sm-12 control-label"><span
												class="s">* </span>支票兌現日：</label></td>
										<td><div class="col-sm-12">
												<input type="text" id="addCheckCashDate" value=""
													readonly="readonly" class="form-control"
													placeholder="支票兌現日" />
											</div></td>
										<td><label class="col-sm-12 control-label"><span
												class="s">* </span>戶籍地址：</label></td>
										<td><div class="col-sm-12">
												<input id="addResidentAddress" type="text"
													class="form-control" name="addResidentAddress" value="">
											</div></td>
									</tr>
									<tr>
										<td><div class="clearfix">&nbsp;</div></td>
									</tr>
									<tr>
										<td><label class="col-sm-12 control-label"><span
												class="s">* </span>備註：</label></td>
										<td><div class="col-sm-12">
												<textarea class="form-control" rows="2" id=memo name="memo"
													value=""></textarea>
											</div></td>
									</tr>
									<tr>
										<td><div class="clearfix">&nbsp;</div></td>
									</tr>
								</table>
								<button class="btn btn-primary btn-label-center" type="button"
									id="plusBtn">新增雜項</button>
								<button class="btn btn-primary btn-label-center" type="button"
									id="delBtn">刪除雜項</button>
								<button class="btn btn-primary btn-label-center" type="button"
									id="shareBtn">基站分攤</button>
									<div></div>
								<div id="ajaxSearchResult" class="col-sm-9 ">
									<div id="jQgrid" align="center">
										<table id="addGrid"></table>
									</div>
									<div id="jQgrid" align="center" hidden="true">
										<table id="tempGrid"></table>
									</div>
								</div>
							</div>
						</div>
						<hr>
					</div>
					</form>
				</div>
				<div id="showSharePag" hidden="true"></div>
				<div id="showSharePosPag">
					<div id="selectSharePage"></div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>