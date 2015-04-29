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
	var _delcheck_ = false;
	var _delArrey_ = [];
	var voucherCredit = {};
	var creditList = [];	
	var voucherCredit_Sp = {};
	var creditList_Sp = [];
	var theDGridSelectedRowId = 0;
	var theDGrid1SelectedRowId = 0;
	var theGrid2Data = {};
	var theGridAllData = [];
	var theGrid3Data = {};
	var addRowID = 1 ;
	$(document).ready(function() {	
		LoadSelect2Script("<s:url value="/resources/plugins/select2/select2.min.js" />", selectDisDefault);		
				mountGrid();//掛載表格
				$("#appDate").datepicker();
				$("#disDomain").change(selDomainChange);
				selectDisDefault();
				mountButEvent();
			});
			function selectDisDefault() {
				$("#disDomain").select2();
				$("#disProcess").select2();
				$("#disappUser").select2();
			}
	//掛載表格
		function mountGrid(){
			$("#dGrid").jqGrid({
				datatype : "local",
				autowidth:'true',
				pager : '#dPager',
				url : CONTEXT_URL + "/pay/pay003/searchDetail1ByProcess/",
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
				    {name : 'voucherType',index : 'voucherType', align : 'center',sortable : false},
				    {name : 'payment_REQ_NO',index : 'payment_REQ_NO', align : 'center',sortable : false, hidden:true}],
				caption : "電費請款清單",
				rownumbers : true,
				multiselect : true,
				onSelectRow : function(rowId) {//點一下帶出清單
					//alert(JSON.stringify(creditList));
					$("#ajaxSearchResult2").show();
					$("#ajaxSearchResult3").hide();
					theDGridSelectedRowId = rowId;
					var isChecked = ($(this).find('#'+rowId+' input[type=checkbox]:checked').length==1);
					if(!isChecked){
						theGrid2Data[theDGridSelectedRowId] = undefined;
					}else{
						var rowData = $( "#dGrid" ).jqGrid('getRowData',theDGridSelectedRowId);
						var data = {
								contractNo : rowData.contract_NO,
								domain : $("#detailContent #disDomain").val(),
								paymentReqBeginDate : rowData.payment_REQ_BEGIN_DATE,
								paymentReqEndDate : rowData.payment_REQ_END_DATE,
								paymentMonth : $("#detailContent #yearMonth").val(),
								processType : $("#detailContent #disProcess").val(),
								appDate : "<fmt:formatDate  value="${appDate}"  pattern="yyyy/MM/dd"  />"
							};
						
						getGrid2Data(data);
					}
					var _aIDs = $("#dGrid").jqGrid('getGridParam','selarrrow');
				      if (_aIDs.length > 0) {
				    	  theGridAllData = [];
				        for (var i=0; i < _aIDs.length; i++) {
				          var id = _aIDs[i];
				          if(theGrid2Data[id] != undefined){
				          var cnt = theGrid2Data[id].length;
							$.each(theGrid2Data[id], function(index, rosterEntry) {
								theGridAllData.splice(cnt+index,0,rosterEntry);
							});
				          }

				        }
				        $.each(theGridAllData, function(index, rosterEntry) {
							jQuery("#d2Grid").jqGrid('addRowData', index, rosterEntry);
						});
				      }else{
				    	  $("#ajaxSearchResult2").hide();
				      }	
				},
				gridComplete : function() {
					var ids = $("#dGrid").jqGrid('getDataIDs');
					var certificateBtn= "";
					for(var i=0;i < ids.length;i++){
						var cl = ids[i];
						certificateBtn = '<input id="certificate" type="button" value="輸入憑證" onclick="certificateadd('+cl+')">';
						jQuery("#dGrid").jqGrid('setRowData', ids[i], {certificateBtn:certificateBtn});
					};
					
				},onSelectAll : function(rowids, status) { //還須修正。
					if(status){
						var rowData ;
						var voList=[];
						for(var i=0;i < rowids.length;i++){
							theDGridSelectedRowId = rowids[i];
							rowData = $( "#dGrid" ).jqGrid('getRowData',rowids[i]);
							var data = {
									contractNo : rowData.contract_NO,
									domain : $("#detailContent #disDomain").val(),
									paymentReqBeginDate : rowData.payment_REQ_BEGIN_DATE,
									paymentReqEndDate : rowData.payment_REQ_END_DATE,
									paymentMonth : $("#detailContent #yearMonth").val(),
									processType : $("#detailContent #disProcess").val(),
									appDate : "<fmt:formatDate  value="${appDate}"  pattern="yyyy/MM/dd"  />"
								};
							getGrid2Data(data);
							var cnt = theGrid2Data[theDGridSelectedRowId].length;
							$.each(theGrid2Data[theDGridSelectedRowId], function(index, rosterEntry) {
								theGridAllData.splice(cnt+index,0,rosterEntry);
							});
						};
						$.each(theGridAllData, function(index, rosterEntry) {
							jQuery("#d2Grid").jqGrid('addRowData', index, rosterEntry);
						});		
					}else{
						$("#ajaxSearchResult2").hide();
						$("#d2Grid").jqGrid().clearGridData();
						
					};
					$("#ajaxSearchResult2").show();
					$("#ajaxSearchResult3").hide();
				}
				
			});
			
			$("#d2Grid").jqGrid({
				datatype : "local",
				autowidth:'true',
				url : CONTEXT_URL + "/pay/pay003/selectDtl4PAY003ATb2/",
				colNames : [ '','','','電錶號碼','','用電方式', '金額/度', '起帳日', '終止日','請款期間起始','請款期間結束','平均用電數/日','','上期度數','本次度數','本期用電度數','超出/減少比率','用電補充說明','請款金額','對應站台資訊','','',''],
				colModel : [ 
					{name : 'seq_NBR',index : 'seq_NBR', align : 'center',sortable : false, hidden:true},
					{name : 'imp_FILE_SEQ_NBR',index : 'imp_FILE_SEQ_NBR', align : 'center',sortable : false, hidden:true},
					{name : 'contract_NO',index : 'contract_NO', align : 'center',sortable : false, hidden:true},
					{name : 'electricity_METER_NBR',index : 'electricity_METER_NBR', align : 'center',sortable : false},
					{name : 'electricity_TYPE',index : 'electricity_TYPE', align : 'center',sortable : false, hidden:true},
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
				    {name : 'if_NO_SITE_MAPPING_DSCR',index : 'if_NO_SITE_MAPPING_DSCR', align : 'center',sortable : false},
				    {name : 'if_NO_SITE_MAPPING',index : 'if_NO_SITE_MAPPING', align : 'center',sortable : false,hidden:true},
				    {name : 'if_AUTO_DEDUCT',index : 'IF_AUTO_DEDUCT', align : 'center',sortable : false,hidden:true},
				    {name : '_id',index:'_id'}],
				caption : "電錶資訊",
				rownumbers : true,
				onSelectRow : function(rowId) {//點一下帶出清單
					
					$("#ajaxSearchResult3").show();
					$("#d3Grid").jqGrid().clearGridData();
					theDGrid1SelectedRowId = rowId;
					var rowData = jQuery(this).getRowData(rowId);
					var data = {
							contractNo : rowData.contract_NO,
							domain : $("#detailContent #disDomain").val(),
							paymentReqBeginDate : rowData.payment_REQ_BEGIN_DATE,
							paymentReqEndDate : rowData.payment_REQ_END_DATE,
							paymentMonth : $("#detailContent #yearMonth").val(),
							processType : $("#detailContent #disProcess").val(),
							appDate : "<fmt:formatDate  value="${appDate}"  pattern="yyyy/MM/dd"  />"
						};
					getGrid3Data(data);
					
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
						jQuery("#d2Grid").jqGrid('setRowData', ids[i], {used_degree_day:used_degree_day,ratio:ratio,_id:cl});						
					}
				}
			});
			
			$("#d3Grid").jqGrid({
				datatype : "local",
				autowidth:'true',
				url : CONTEXT_URL + "/pay/pay003/searchRequiRement3/",
				colNames : [ '站點編號', '起帳日', '終止日','','預付餘額','類別','請款期間起始','請款期間結束','本次請款','','','','',''],
				colModel : [ 
					{name : 'location_ID',index : 'location_ID', align : 'center',sortable : false},
				    {name : 'payBeginDate',index : 'payBeginDate' , align : 'center',sortable : false,formatter: jqGridDataFormat}, 
				    {name : 'lsEDate',index : 'lsEDate', align : 'center',sortable : false,formatter: jqGridDataFormat}, 
				    {name:'rentAmt',index:'rentAmt', width:60,sortable: false ,formatter: nullForZero ,hidden:true},
				    {name : 'sumPrepaymentBalance',index : 'sumPrepaymentBalance', align : 'center',sortable : false,formatter: nullForZero}, 
			   		{name : 'itemDesc',index : 'itemDesc', align : 'center',sortable : false},
			   		{name : 'payment_REQ_BEGIN_DATE',index : 'payment_REQ_BEGIN_DATE' , align : 'center',sortable : false,formatter: jqGridDataFormat}, 
				   	{name : 'payment_REQ_END_DATE',index : 'payment_REQ_END_DATE' , align : 'center',sortable : false,formatter: jqGridDataFormat}, 
		   			{name : 'sumAmt',index:'sumAmt', width:60,sortable: false,formatter: nullForZero},
		   			{name : 'tax_EXCLUSIVE_AMT',index:'tax_EXCLUSIVE_AMT', width:60,sortable: false,formatter: nullForZero},
		   			{name : 'business_TAX',index:'business_TAX', width:60,sortable: false,formatter: nullForZero},
		   			{name : 'sumincomeTax',index:'sumincomeTax', width:60,sortable: false,formatter: nullForZero},
		   			{name : 'sumNhiAmt',index:'sumNhiAmt', width:60,sortable: false,formatter: nullForZero},
				    {name : '_id',index:'_id'}],
				caption : "固定金額",
				rownumbers : true,
				gridComplete : function() {
					var ids = $("#d3Grid").jqGrid('getDataIDs');
					for(var i=0;i < ids.length;i++){
					jQuery("#d3Grid").jqGrid('setRowData', ids[i], {_id:theDGrid1SelectedRowId});
					}
				}
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
					cashReqNo : $("#disCashReqNo").val(),
					appUser : $("#disappUser").val()
				},
				type : "POST",
				dataType : 'html',
				success : function(data) {
					alert("請款單號 : " + $("#disCashReqNo").val()+" "+statusDesc+"!");
					parent.$.fancybox.close();//關閉視窗
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
		//資料處理
		$('#dataBtn').click(function() {
			if(!PayValidate("#detailContent")){
				return false;
			};
			$("#d2Grid").jqGrid().clearGridData();
			$("#d3Grid").jqGrid().clearGridData();
			var data = {
					domain : $.trim($("#disDomain").val()), // 維運區
					processType : $.trim($("#disProcess").val()), // 業者
					appUser : $.trim($("#disappUser").val()), // PO
					appDate : $.trim($("#appDate").val()), // PO
					yearMonth : $.trim($("#yearMonth").val()), // 申請起日
					paymentPeriod : $.trim($("#dispaymentPeriod").val()) // 申請迄日
				};
				$("#dGrid").setGridParam({datatype : "json",	postData : data, page : 1}).trigger("reloadGrid");
		});

		//請款			
		$('#moneyBtn').click(function() {
				if(!PayValidate("#detailContent")){
					return false;
				};

				var myId = $("#dGrid").jqGrid('getGridParam', 'selarrrow');// 取得勾選資料id
				var ids = $("#d2Grid").jqGrid('getDataIDs');//grid2 所有ID
				var processType = $("#disProcess").val();
				var tbPayPaymentRequestDTOList;
				var tbPayPaymentRequestDtlList;
				var tbPayElectricityDTOList;
				var tbPayPaymentList;
				var rowData1 ;
				var grid2id = 0;
				if (myId.length > 0) {
					tbPayPaymentRequestDTOList = [];
					for (var i = 0 ; i<myId.length ; i++) {
						rowData1 = $("#dGrid").jqGrid('getRowData', myId[i]);
 						tbPayPaymentRequestDtlList =[];
 						tbPayElectricityDTOList = [];
						tbPayPaymentList = [];
						//Grid2 =>Dtl
						grid2id = 0;
						for(var i=0;i < ids.length;i++){
							var cl = ids[i];
							rowData2 = $( "#d2Grid" ).jqGrid('getRowData',cl);
							theDGrid1SelectedRowId = cl;
							if(theGrid3Data[theDGrid1SelectedRowId] == undefined){
							var data = {
									contractNo : rowData2.contract_NO,
									domain : $("#detailContent #disDomain").val(),
									paymentReqBeginDate : rowData2.payment_REQ_BEGIN_DATE,
									paymentReqEndDate : rowData2.payment_REQ_END_DATE,
									paymentMonth : $("#detailContent #yearMonth").val(),
									processType : $("#detailContent #disProcess").val(),
									appDate : "<fmt:formatDate  value="${appDate}"  pattern="yyyy/MM/dd"  />"
								};
								getGrid3Data(data);	
							};
							
							if(rowData1.contract_NO == rowData2.contract_NO){
								$.each(theGrid3Data[cl] , function(index2, rowData3) {
									grid2id ++;
									var tbPayPayment = {
											'contractNo' :	rowData1.contract_NO,
											'location_ID' : 		rowData3.location_ID,
											'voucher_type' : 		rowData2.electricity_TYPE,
											'electricitySeqNbr' : 		rowData2.seq_NBR,
											'tax_EXCLUSIVE_TOTAL_AMT' : rowData3.tax_EXCLUSIVE_AMT,
											'total_BUSINESS_TAX' : 	rowData3.business_TAX,
											'total_INCOME_TAX' : 	rowData3.sumincomeTax,
											'total_NHI_AMT' : 		rowData3.sumNhiAmt,
											'status' : 'N'
										};
										tbPayPaymentList.push(tbPayPayment);
									var tbPayPaymentRequestDtl = {
										'contractNo' :	rowData1.contract_NO,	
										'item_NO' :	grid2id,
										'payment_REQUEST_ITEM' :rowData3.payment_REQUEST_ITEM,
										'location_ID' : 		rowData3.location_ID,
										'tax_EXCLUSIVE_AMT' : 	rowData3.tax_EXCLUSIVE_AMT,
										'business_TAX' : 		rowData3.business_TAX,
										'payment_REQ_BEGIN_DATE' :rowData3.payment_REQ_BEGIN_DATE.replace(/\//g,"-"),
										'payment_REQ_END_DATE' :rowData3.payment_REQ_END_DATE.replace(/\//g,"-"),
										'electricityType' :rowData2.electricity_TYPE
									};
									//當處理方式=例行ELE01/補請ELE02 ELE03 時
									if(rowData2.electricity_TYPE=="ELE01"){
										tbPayPaymentRequestDtl["charge_UNIT"] = "FIX";
										tbPayPaymentRequestDtl["unit_PRICE"] = rowData3.rentAmt;
									};
									if(rowData2.electricity_TYPE=="ELE02" || rowData2.electricity_TYPE=="ELE03"){
										tbPayPaymentRequestDtl["charge_UNIT"] = "DEG";
										tbPayPaymentRequestDtl["unit_PRICE"] = rowData3.rentAmt;
									};
									tbPayPaymentRequestDtlList.push(tbPayPaymentRequestDtl);
								});
								if(rowData2.electricity_TYPE=="ELE01" || rowData2.electricity_TYPE=="ELE05"){
									var tbPayElectricity = {
										'contract_NO' :	rowData1.contract_NO,
										'electricity_METER_NBR' :rowData2.electricity_METER_NBR,
										'electricity_TYPE' : 		rowData2.electricity_TYPE,
										'total_USED_DEGREE' : 	rowData2.total_USED_DEGREE,
										'used_DEGREE' : 		rowData2.used_DEGREE,
										'payment_REQ_AMT' : rowData2.payment_REQ_AMT,
										'memo' : rowData2.memo,
										'if_NO_SITE_MAPPING' : rowData2.if_NO_SITE_MAPPING,
										'payment_REQ_BEGIN_DATE' :rowData2.payment_REQ_BEGIN_DATE.replace(/\//g,"-"),
										'payment_REQ_END_DATE' :rowData2.payment_REQ_END_DATE.replace(/\//g,"-")
									};
								}else{
									var tbPayElectricity = {
											'seq_NBR' : rowData2.seq_NBR,
											'electricity_TYPE' : rowData2.electricity_TYPE,
											'imp_FILE_SEQ_NBR' : rowData2.imp_FILE_SEQ_NBR// ELE04 執行 UPDATE IMP FILE
										};

										
								};
								
								tbPayElectricityDTOList.push(tbPayElectricity);
							}
						}
						
						
					
						var tbPayPaymentRequestDTO = {
							'payment_REQ_BEGIN_DATE' : 	rowData1.payment_REQ_BEGIN_DATE.replace(/\//g,"-"),
							'payment_REQ_END_DATE' : 	rowData1.payment_REQ_END_DATE.replace(/\//g,"-"),
							'contract_NO' : 			rowData1.contract_NO,
							'payment_MONTHS' : 			rowData1.payment_MONTHS,
							'tbPayPaymentRequestDtlDTOList' : tbPayPaymentRequestDtlList,
							'tbPayElectricityDTOList' : tbPayElectricityDTOList,
							'tbPayPaymentList'     : tbPayPaymentList
						};
						tbPayPaymentRequestDTOList.push(tbPayPaymentRequestDTO);
					};
				} else {
					alert("未勾選要請款的資料列,請重新操作!");
					return false;
				};
				var tbPayCashRequirementDTO = {
						domain : $.trim($("#disDomain").val()), // 維運區
						process_TYPE : $.trim($("#disProcess").val()), // 業者
						app_USER : $.trim($("#disappUser").val()), // PO
						app_DATE : $.trim($("#appDate").val().replace(/\//g,"-")), // PO
						year_MONTH : $.trim($("#yearMonth").val()), // 申請起日
						payment_PERIOD : $.trim($("#dispaymentPeriod").val()), // 申請迄日
						status : 		"A",
						tbPayPaymentRequestDTOList : tbPayPaymentRequestDTOList,
						tbPayPaymentRequestVoucherDTOList :creditList
					};
				$.ajax({
					url : CONTEXT_URL + "/pay/pay003/money",
					data : JSON.stringify(tbPayCashRequirementDTO),
					contentType : "application/json",
					type : "POST",
					dataType : 'json',
					success : function(data) {
						if(data.success){
							alert(data.msg);
							parent.$.fancybox.close();				
						}else{
							alert(data.msg);
						};
					},
					error : function(data) {
						alert(data.msg);
					}
				});	
				
		
		});
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
	function PayValidate(myParent){		
		var isValid = true;
		$(myParent+" .require").each(function() {
			if($(this).prop("id").indexOf("s2id_") == -1 && $(this).val()=="") {
				alert("所有欄位必須輸入才能進行資料處理,請重新操作!");
				$(this).focus();
				isValid = false;
				return false;
			};
		});
		if(!isValid) return false;
		var myPeriod = $(myParent+" #dispaymentPeriod");
		if(myPeriod.val()!="" && !gIsDigit(myPeriod.val())){
			alert('付款週期 必需為數值');
			myPeriod.focus();
			return false;
		};
		return true;
	};
	//維運區連動 申請人
	function selDomainChange(){	
		$("#disappUser").select2("val", "");
		processAjax("/ajax/pay/public/appUserDomain", 
				{domain : $("#disDomain").val()},
				null,
				false,
				function(data) {
					var selObj = $("#disappUser");
					selObj.html('<option value="" selected>--請選擇--</option>');
					$.each(data.rows, function(index, rosterEntry) {
						selObj.append('<option value="'+rosterEntry.psn_NO+'">'+rosterEntry.chn_NM+'</option>');
					});
				});		
	}
	function processAjax(url, data, msg, closeFancybox, _callback){
		$.ajax({
			url : CONTEXT_URL + url,
			type : "POST",
			data : data,
			dataType : "json",
			async : false,
			success : function(data) {
				if(jQuery.isFunction(_callback)) {
					_callback.call(_callback, data);					
				};
				if(data.success){
					if(msg != null)alert(msg);
					if(closeFancybox)parent.$.fancybox.close();				
				};
			}
		});
	};
	//取得Grid2  資料
	function getGrid2Data(ajaxData){
		$("#d2Grid").jqGrid().clearGridData();
		if(theGrid2Data[theDGridSelectedRowId] == undefined){
// 			$.each(theGrid2Data[theDGridSelectedRowId] , function(index, rosterEntry) {
// 				jQuery("#d2Grid").jqGrid('addRowData', index, rosterEntry);
// 			});
// 		}else{
	
			processAjax("/pay/pay003/selectDtl4PAY003ATb2", 
					ajaxData,
					null,
					false,
					function(data) {
						theGrid2Data[theDGridSelectedRowId] = data.maps.grid;
// 						$.each(data.maps.grid, function(index, rosterEntry) {
// 							jQuery("#d2Grid").jqGrid('addRowData', index, rosterEntry);
// 						});
					});		
		};
		
	};
	
	
	//取得grid3 資料
	function getGrid3Data(ajaxData){
		$("#d3Grid").jqGrid().clearGridData();
		if(theGrid3Data[theDGrid1SelectedRowId] != undefined){
			$.each(theGrid3Data[theDGrid1SelectedRowId] , function(index, rosterEntry) {
				jQuery("#d3Grid").jqGrid('addRowData', index, rosterEntry);
			});
		}else{
			processAjax("/pay/pay003/searchDetail2ByProcess", 
					ajaxData,
					null,
					false,
					function(data) {
						theGrid3Data[theDGrid1SelectedRowId] = data.maps.grid2;
						$.each(data.maps.grid2, function(index, rosterEntry) {
							jQuery("#d3Grid").jqGrid('addRowData', index, rosterEntry);
						});
					});		
		};
		
	};

	
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
						id="dataBtn">資料處理</button>
					<button class="btn btn-primary btn-label-left" type="button"
						id="moneyBtn">請款</button>
				</div>
			</ol>
		</div>
							<div id="button">
									<div id="button" style="text-align:center;">
									<input id="disCashReqNo" type="hidden" name=disCashReqNo value="${cashReqNo}">
									<input id="disReqBeginDate" type="hidden" name="disReqBeginDate" >
								<!-- <font color='#CC0000'>&nbsp;&nbsp;&nbsp;&nbsp;請款單號 : <c:out value="${cashReqNo}"/></font>-->
							</div>
							</div>
	<div class="row">
		<div id="detailContent" class="col-xs-12 col-sm-12">
			<div class=" ui-draggable ui-droppable">
				<div class="box-content">
					<form class="form-horizontal" id="bBoardAddEdit" >
					<div class="form-group">
						<div class="row">
							<div class="col-md-12">
									<table style="width: 100%">
									<tr>
										<td><label class="col-sm-12 control-label"><span class="s">* </span>維運區：</label></td>
										<td>
											<div class="col-sm-6">
												<select id="disDomain" name="disDomain" class="populate placeholder require">
													<option value="" selected>--請選擇--</option>
													<c:forEach items="${domainSelect}" var="addDomain">
														<option value="${addDomain.ID}"<c:if test="${domain eq addDomain.ID}"> selected</c:if>>
															${addDomain.NAME}
														</option>
													</c:forEach>
												</select>
											</div>
										</td>
										<td><label class="col-sm-12 control-label"><span class="s">* </span>處理類別：</label></td>
										<td>
											<div class="col-sm-6">
												<select id="disProcess" name="disProcess" class="populate placeholder require">
													<option value="" selected>--請選擇--</option>
													<c:forEach items="${processSelect}" var="addProcess">
														<option value="${addProcess.LOOKUP_CODE}"<c:if test="${processType eq addProcess.LOOKUP_CODE}"> selected</c:if>>
														${addProcess.LOOKUP_CODE_DESC}
														</option>
													</c:forEach>
												</select>
											</div>
										</td>
									</tr>
									<tr>
										<td><div class="clearfix">&nbsp;</div></td>
									</tr>
									<tr>
										<td><label class="col-sm-12 control-label"><span class="s">* </span>申請人：</label></td>
									<td>
										<div class="col-sm-6">
											<select id="disappUser" name="disappUser"  class="populate placeholder require">
												<option value="">--請選擇--</option>
												<c:forEach items="${personnelSelect}" var="domain">
													<option value="${domain.PSN_NO}"<c:if test="${domain.PSN_NO eq master.APP_USER}"> selected</c:if>>
													${domain.CHN_NM}
													</option>
												</c:forEach>
											</select>
										</div>
									</td>
										<td><label class="col-sm-12 control-label"><span class="s">* </span>申請日期：</label></td>
										<td>
											<div class="col-sm-6">
												<input id="appDate" type="text" 
													class="form-control" name="appDate" 
													value="<fmt:formatDate  value="${appDate}"  pattern="yyyy/MM/dd"  />" readonly="readonly" class="form-control" placeholder="申請日期">
											</div>
										</td>
									</tr>
									<tr>
										<td><div class="clearfix">&nbsp;</div></td>
									</tr>
									<tr>
										<td><label class="col-sm-12 control-label"><span class="s">* </span>請款年月：</label></td>
										<td>
											<div class="col-sm-6">
											<input id="yearMonth" type="text" 
													class="form-control" name="yearMonth" value="${yearMonth}">
											</div>
										</td>
										<td><label class="col-sm-12 control-label"><span class="s">* </span>付款週期：</label></td>
										<td>
										<div class="col-sm-6">
										<input id="dispaymentPeriod" type="text" class="form-control require" name="dispaymentPeriod">
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