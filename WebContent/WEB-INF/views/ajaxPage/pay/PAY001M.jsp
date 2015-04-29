<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>租/押金請款</title>
<script type="text/javascript">

	var theContractNo;
	var theDelPaymentReqNo = [];
	var theDGrid1Records = 0;
	var theDGrid1SelectedRowId = 0;
	var theDGrid2SelectedRowId = 0;
	var theDGrid1Url = "searchDetail1";
	var theKEY = "";
	<c:if test="${type eq 'A'}">
	var theGrid3Data = [];
	var theGrid2Data = {};
		theDGrid1Url = "searchDetail1ByProcess";
	</c:if>
	$(document).ready(function() {
		LoadSelect2Script("<s:url value="/resources/plugins/select2/select2.min.js" />", selectDefault1);		
		mountGrid();
		//各功能:	
		<c:if test="${type eq 'D'}">
			getDGrid1Data();	
			setDisabled();	
		</c:if>			
		<c:if test="${type eq 'A'}">
			$("#detailContent #appUser").prop("readonly",true).prop("disabled",true);
			$("#detailContent #appDate").prop("readonly",true).prop("disabled",true);
			$("#detailContent #yearMonth").removeAttr("readonly").removeAttr("disabled");
			$("#detailContent #domainSelect").change(selDomainChange);
			$("#processBtn").click(btProcess);
			$("#moneyBtn").click(btMoney);
		</c:if>
		<c:if test="${type eq 'E'}">
			getDGrid1Data();	
			setDisabled();	
			$("#delDtlBtn").click(btDelete);
			$("#saveBtn").click(btSave);
			$("#approvedBtn").click(btApproved);
		</c:if>
	});
	//掛載表格
	function mountGrid(){
		$("#dGrid1").jqGrid({
			datatype : "local",
			autowidth:'true',
			url : CONTEXT_URL + "/pay/pay001/"+theDGrid1Url,
			colNames:['狀態','租約編號','名稱','押金','租金','付款月數','起帳日','請款期間起始','請款期間結束','本次請款',''],
			colModel:[
				{name:'statusDesc',index:'statusDesc',width:60, sortable: false},
				{name:'contract_NO',index:'contract_NO',width:120, sortable: false},
				{name:'lsName',index:'lsName', width:150,sortable: false},
				{name:'pldgAmt',index:'pldgAmt', width:80,sortable: false, align : 'right'},
				{name:'rentAmt',index:'rentAmt', width:80,sortable: false, align : 'right'},
				{name:'payment_MONTHS',index:'payment_MONTHS', width:60,sortable: false},
				{name:'lsSdate',index:'lsSdate', width:80,sortable: false, formatter : jqGridDataFormat},
				{name:'payment_REQ_BEGIN_DATE',index:'payment_REQ_BEGIN_DATE', width:80,sortable: false, formatter : jqGridDataFormat},
				{name:'payment_REQ_END_DATE',index:'payment_REQ_END_DATE', width:80,sortable: false, formatter : jqGridDataFormat},
				{name:'sumAmt',index:'sumAmt', width:80,sortable: false, align : 'right'},
				{name:'payment_REQ_NO',index:'payment_REQ_NO', sortable: false,hidden:true}
			],	
			caption : "請款清單",
			rownumbers : true,
			<c:if test="${type eq 'E' || type eq 'A'}"> multiselect: true,</c:if>
			<c:if test="${type eq 'A'}">
			onSelectAll : function(rowids, status) {
				if(status){
					var rowData ;
					var voList=[];
					for(var i=0;i < rowids.length;i++){
						rowData = $( "#dGrid1" ).jqGrid('getRowData',rowids[i]);
						var TbPayPaymentRequestDTO = {
								contract_NO : rowData.contract_NO,
								domain : $("#detailContent #domainSelect").val(),
								payment_REQ_BEGIN_DATE : rowData.payment_REQ_BEGIN_DATE.replace(/\//g,"-"),
								payment_REQ_END_DATE : rowData.payment_REQ_END_DATE.replace(/\//g,"-"),
								yearMonth : $("#detailContent #yearMonth").val(),
								processType : $("#detailContent #processType").val(),
								appDate : "<fmt:formatDate  value="${master.APP_DATE}"  pattern="yyyy-MM-dd"  />",
								paymentPeriod : $("#detailContent #paymentPeriod").val(),
								_id : rowids[i],
								_key : theKEY
							};
						voList.push(TbPayPaymentRequestDTO);
					};
					$.ajax({
							url : CONTEXT_URL + "/pay/pay001/searchDetail2AllByProcess",
							data : JSON.stringify(voList),
							contentType : "application/json",
							type : "POST",
							dataType : 'json',
							success : function(data) {
								//需保留,以利之後請款新增用
								theGrid2Data = data.maps.grid2All;
								theGrid3Data = data.maps.grid3;
								theKEY = data.maps.key;
							},
							error : function(data) {
								alert(data.msg);
							}
					});			
				};
			},
			</c:if>
			onSelectRow : function(rowId) {
				<c:if test="${type eq 'E' || type eq 'A'}">
				var isChecked = ($(this).find('#'+rowId+' input[type=checkbox]:checked').length==1);
				if(!isChecked){
					$("#dGrid2").jqGrid().clearGridData();
					$("#dGrid3").jqGrid().clearGridData();
					return false;
				};
				</c:if>
				theDGrid1SelectedRowId = rowId;
				var rowData = jQuery(this).getRowData(rowId);
				var data = {
						contractNo : rowData.contract_NO,
						domain : $("#detailContent #domainSelect").val(),
						paymentReqBeginDate : rowData.payment_REQ_BEGIN_DATE,
						paymentReqEndDate : rowData.payment_REQ_END_DATE,
						paymentMonth : $("#detailContent #yearMonth").val(),
						processType : $("#detailContent #processType").val(),
						paymentReqNo : rowData.payment_REQ_NO,
						appDate : "<fmt:formatDate  value="${master.APP_DATE}"  pattern="yyyy/MM/dd"  />",
						paymentPeriod : $("#detailContent #paymentPeriod").val(),
						_key : theKEY
					};
				<c:if test="${not(type eq 'A')}">
				theContractNo = rowData.contract_NO;
				$("#dGrid2").setGridParam({datatype : "json",postData : data, rowNum:100000}).trigger("reloadGrid");
				$("#dGrid3").jqGrid().clearGridData();
				</c:if>
				<c:if test="${type eq 'A'}">
				//需保留,以利之後請款新增用
				getGrid2Data(data);
				</c:if>
			},
			gridComplete : function() {
				theDGrid1Records = $("#dGrid1").jqGrid('getGridParam', 'records');
				<c:if test="${type eq 'A'}">
					var ids = $("#dGrid1").jqGrid('getDataIDs');
					var sum = 0;
					var gridRow;
					var endDate;
					for(var i=0;i < ids.length;i++){
						gridRow = $( "#dGrid1" ).jqGrid('getRowData',ids[i]);
						paymentMonth = gridRow.payment_MONTHS;
						//請款期間起始+付款月數-1天
						endDate = "";
						if(gridRow.lsSdate!=""){
							endDate = addDate(gridRow.lsSdate, paymentMonth, -1);
						};
						//租金*對應  dGrid1  那筆的付款月數
						sum = parseInt(gridRow.rentAmt) * parseInt(paymentMonth); 
						jQuery("#dGrid1").jqGrid('setRowData', ids[i], 
								{payment_REQ_BEGIN_DATE : gridRow.lsSdate, payment_REQ_END_DATE : endDate, sumAmt : sum});
					};
				</c:if>
			}
		});	
		$("#dGrid2").jqGrid({
			datatype : "local",
			autowidth:'true',
			url : CONTEXT_URL + "/pay/pay001/searchDetail2",
			colNames:['站點編號','起帳日','終止日','租金','預付餘額','類別','請款期間起始','請款期間結束','本次請款','','',''],
			colModel:[
				{name:'location_ID',index:'location_ID',width:110, sortable: false},
				{name:'payBeginDate',index:'payBeginDate',width:80, sortable: false, formatter : jqGridDataFormat},
				{name:'lsEDate',index:'lsEDate',width:80, sortable: false, formatter : jqGridDataFormat},
				{name:'rentAmt',index:'rentAmt', width:80,sortable: false, align : 'right'},
				{name:'sumPrepaymentBalance',index:'sumPrepaymentBalance', width:80,sortable: false, align : 'right'},
				{name:'itemDesc',index:'itemDesc', width:80,sortable: false},
				{name:'payment_REQ_BEGIN_DATE',index:'payment_REQ_BEGIN_DATE', width:80,sortable: false, formatter : jqGridDataFormat},
				{name:'payment_REQ_END_DATE',index:'payment_REQ_END_DATE', width:80,sortable: false, formatter : jqGridDataFormat},
				{name:'sumAmt',index:'sumAmt', width:80,sortable: false, align : 'right'},
				{name:'payment_REQUEST_ITEM',index:'payment_REQUEST_ITEM', sortable: false,hidden:true},
				{name:'_id',index:'_id',hidden:true},
				{name:'item_NO',index:'item_NO', sortable: false,hidden:true}
			],	
			caption : "租約基站清單",
			rownumbers : true,
			rowNum : 100000,
			onSelectRow : function(rowId) {
				<c:if test="${not(type eq 'A')}">
				var rowData = jQuery(this).getRowData(rowId);
				var rowData1 = $("#dGrid1").getRowData(theDGrid1SelectedRowId);
				theDGrid2SelectedRowId = rowId;
				var data = {
						locationId : rowData.location_ID,
						contractNo : theContractNo,
						paymentReqNo : rowData1.payment_REQ_NO,
						appDate : "<fmt:formatDate  value="${master.APP_DATE}"  pattern="yyyy/MM/dd"  />",
						paymentMonth : rowData1.payment_MONTHS,
						itemNo : rowData.item_NO,
						processType : $("#detailContent #processType").val()
					};
				$("#dGrid3").setGridParam({datatype : "json",postData : data, rowNum:100000}).trigger("reloadGrid");
				</c:if>
				<c:if test="${type eq 'A'}">
				getGrid3Data();
				</c:if>
			},
			gridComplete : function() {
				var ids = $("#dGrid2").jqGrid('getDataIDs');
				var gridRow;
				for(var i=0;i < ids.length;i++){
					gridRow = $( "#dGrid2" ).jqGrid('getRowData',ids[i]);
					if(gridRow.payment_REQUEST_ITEM=="REP03"){
						jQuery("#dGrid2").jqGrid('setRowData', ids[i], {payment_REQ_BEGIN_DATE : "", payment_REQ_END_DATE :""});
					}else{
						jQuery("#dGrid2").jqGrid('setRowData', ids[i], {sumPrepaymentBalance : ""});
					};	
				};
			}
		});	
		$("#dGrid3").jqGrid({
			datatype : "local",
			autowidth:'true',
			url : CONTEXT_URL + "/pay/pay001/searchDetail3",
			colNames:['出租人','付款對象','名稱','付款方式','租金','本次請款','未稅金額','營業稅','所得稅','健保補充費','實付金額'],
			colModel:[
				{name:'lessorName',index:'lessorName',width:110, sortable: false},
				{name:'payment_USER_ID',index:'payment_USER_ID',width:110, sortable: false},
				{name:'payment_USER_NAME',index:'payment_USER_NAME',width:120, sortable: false},
				{name:'paymethodName',index:'paymethodName', width:60,sortable: false},
				{name:'rentAmt',index:'rentAmt', width:80,sortable: false, align : 'right'},
				{name:'totalAmt',index:'totalAmt', width:80,sortable: false, align : 'right'},
				{name:'tax_EXCLUSIVE_TOTAL_AMT',index:'tax_EXCLUSIVE_TOTAL_AMT', width:80,sortable: false, align : 'right'},
				{name:'total_BUSINESS_TAX',index:'total_BUSINESS_TAX', width:80,sortable: false, align : 'right'},
				{name:'total_INCOME_TAX',index:'total_INCOME_TAX', width:80,sortable: false, align : 'right'},
				{name:'total_NHI_AMT',index:'total_NHI_AMT', width:80,sortable: false, align : 'right'},
				{name:'sumAllAmt',index:'sumAllAmt', width:80,sortable: false}
			],	
			caption : "站點請款紀錄",
			rowNum : 100000,
			rownumbers : true
		});	
		$(window).resize(function() {
					$(window).unbind("onresize");
					$("#dGrid1,.dGrid1").setGridWidth($("#jQgrid").width());
					$("#dGrid2,.dGrid2").setGridWidth($("#jQgrid").width());
					$("#dGrid3,.dGrid3").setGridWidth($("#jQgrid").width());
					$(window).bind("onresize", this);
		}).trigger('resize');		
	};	
	// 開啟下拉式選單搜尋文字功能
	function selectDefault1() {
		$("#detailContent #domainSelect").select2();
		$("#detailContent #processType").select2();
		$("#detailContent #appUser").select2();
	};
	//預設 Disabled
	function setDisabled(){
		$("input,select","#editFrom").prop("readonly",true).prop("disabled",true);
	};
	function getDGrid1Data(){
		//取得  處理類別 直接傳入(狀態: Master的處理類別)
		processTypeDesc = ($("#processType option:selected","#detailContent").text());
		var data = {
			cashReqNo : '${master.CASH_REQ_NO}',
			domain : $("#detailContent #domainSelect").val(),
			paymentPeriod : $("#detailContent #paymentPeriod").val(),
			processType : $("#detailContent #processType").val(),
			processTypeDesc : processTypeDesc,
			yearMonth : $("#detailContent #yearMonth").val(),
			appDate : "<fmt:formatDate  value="${master.APP_DATE}"  pattern="yyyy/MM/dd"  />"
		};
		$("#dGrid1").setGridParam({datatype : "json",postData : data, size:100000}).trigger("reloadGrid");
	};
	//=======button 功能===============
	//刪除明細
	function btDelete(){
		if(!confirm("是否確定要刪除?")){
			return false;
		};
		var myId = $("#dGrid1").jqGrid('getGridParam', 'selarrrow');// 取得勾選資料id
		if (myId.length > 0) {
			for (var i = myId.length - 1; i >= 0; i--) {
				var row = $("#dGrid1").jqGrid('getRowData', myId[i]);
				$('#dGrid1').jqGrid('delRowData',myId[i]);
				theDelPaymentReqNo.push(row.payment_REQ_NO);
				$("#dGrid2").jqGrid().clearGridData();
				$("#dGrid3").jqGrid().clearGridData();
			};
		} else {
			alert("未勾選要刪除的資料列,請重新操作!");
			return false;
		};
	};
	//儲存
	function btSave(){
		if(theDelPaymentReqNo.length ==0 ){
			alert("未做任何處理動作,請重新操作!");
			return false;
		};
		if($("#dGrid1").jqGrid('getGridParam', 'records') == 0){
			if(!confirm("提醒:請款清單已無資料,該請款單將被刪除,是否確定?")){				
				return false;	
			};				
		};
		
		processAjax("/pay/pay001/update", 
				{
					paymentReqNo : theDelPaymentReqNo.toString(),
					cashReqNo : '${master.CASH_REQ_NO}'
				},
				"請款單號 ${master.CASH_REQ_NO} 變更儲存成功!",true);			
	};
	//送審
	function btApproved(){
		if(theDelPaymentReqNo.length == theDGrid1Records ){
			alert("您已先行執行刪除所有資料!");
			return false;
		};
		if($("#dGrid1").jqGrid('getGridParam', 'records') == 0){
			alert("請款清單已無資料,無法送審,請重新操作");
			return false;	
		};
		
		processAjax("/pay/pay001/approved", 
				{
					paymentReqNo : theDelPaymentReqNo.toString(),
					cashReqNo : '${master.CASH_REQ_NO}'
				},
				"請款單號 ${master.CASH_REQ_NO} 送審中!",true);		
	};
	//資料處理
	function btProcess(){
		if(!PayValidate("#detailContent")){
			return false;
		};
		//檢查 YYYYMM
		var isValid = true;
		if($("#yearMonth").val()!=""){
			if(!gIsDigit($("#yearMonth").val())) isValid = false;
			if(isValid && $("#yearMonth").val().length!=6 ) isValid = false;
			if(isValid && 
					!($("#yearMonth").val().substring(4,6)>="01" && $("#yearMonth").val().substring(4,6)<="12"))
				isValid = false;
			if(!isValid){
				alert('請款年月  請符合格式 (YYYYMM)');
				$("#yearMonth").focus();
				return false;					
			};
		};
		$("#dGrid1").jqGrid().clearGridData();
		$("#dGrid2").jqGrid().clearGridData();
		$("#dGrid3").jqGrid().clearGridData();
		
		theGrid3Data = [];
		theGrid2Data = {};
		//檢查是否有重覆資料
		processAjax("/pay/pay001/checkDataExists", 
					{
						domain : $("#detailContent #domainSelect").val(),
						processType : $("#detailContent #processType").val(),
						yearMonth : $("#detailContent #yearMonth").val()
					},
					null,
					false,
					function(data) {
						if(data.msg == ""){
							getDGrid1Data();
						}else{
							alert(data.msg);
							return false;
						}
					});	
	};
	//請款
	function btMoney(){
		if(!PayValidate("#detailContent")){
			return false;
		};
		
		var myId = $("#dGrid1").jqGrid('getGridParam', 'selarrrow');// 取得勾選資料id
		var processType = $("#detailContent #processType").val();
		var tbPayPaymentRequestDTOList;
		var tbPayPaymentRequestDtlList;
		var tbPayPaymentList;
		var rowData1 ;
		var grid2id = 0;
		var paymentReqUserId ="";//grid3皆同,需取給grid2
		if (myId.length > 0) {
			/*
			tbPayPaymentRequestDTOList = [];
			for (var i = 0 ; i<myId.length ; i++) {
				rowData1 = $("#dGrid1").jqGrid('getRowData', myId[i]);
				tbPayPaymentRequestDtlList =[];
				//Grid2 =>Dtl
				grid2id = 0;
				$.each(theGrid2Data[myId[i]] , function(index2, rowData2) {
					if(rowData2.location_ID == "" || rowData2.location_ID == null)return;//同一筆...
					
					grid2id ++;
					//Grid3 => Payment
					tbPayPaymentList = [];
					$.each(theGrid3Data, function(index3, rowData3) {
						if(rowData3._id == rowData2._id && rowData3.contractNo == rowData1.contract_NO){
							var tbPayPayment = {
								'payment_REQ_ITEM_NO' :	grid2id,
								'location_ID' : 		rowData2.location_ID,
								'payment_REQ_USER_ID' : rowData3.payment_REQ_USER_ID,
								'payment_USER_ID' : 	rowData3.payment_USER_ID,
								'payment_USER_NAME' : 	rowData3.payment_USER_NAME,
								'payment_METHOD' : 		rowData3.payment_METHOD,
								'bank_CODE' : 			rowData3.bank_CODE,
								'bank_BRANCH_CODE' : 	rowData3.bank_BRANCH_CODE,
								'account_NBR' : 		rowData3.account_NBR,
								'tax_EXCLUSIVE_TOTAL_AMT' : rowData3.tax_EXCLUSIVE_TOTAL_AMT,
								'total_BUSINESS_TAX' : 	rowData3.total_BUSINESS_TAX,
								'total_INCOME_TAX' : 	rowData3.total_INCOME_TAX,
								'total_NHI_AMT' : 		rowData3.total_NHI_AMT,
								'status' : 'N'
							};
							paymentReqUserId = rowData3.payment_REQ_USER_ID;
							tbPayPaymentList.push(tbPayPayment);
						};
					});
					var tbPayPaymentRequestDtl = {
						'item_NO' :	grid2id,
						'payment_REQUEST_ITEM' :rowData2.payment_REQUEST_ITEM,
						'location_ID' : 		rowData2.location_ID,
						'tax_EXCLUSIVE_AMT' : 	rowData2.tax_EXCLUSIVE_AMT,
						'business_TAX' : 		rowData2.business_TAX,
						'payment_REQ_BEGIN_DATE' :rowData2.payment_REQ_BEGIN_DATE.replace(/\//g,"-"),
						'payment_REQ_END_DATE' :rowData2.payment_REQ_END_DATE.replace(/\//g,"-"),
						'payment_REQ_USER_ID' : paymentReqUserId,
						'tbPayPaymentList' : 	tbPayPaymentList
					};
					//當處理方式=例行REN03/補請REN04 時
					if(processType=="REN03" || processType=="REN04"){
						tbPayPaymentRequestDtl["charge_UNIT"] = "MON";
						tbPayPaymentRequestDtl["unit_PRICE"] = rowData2.rentAmt;
					};
					tbPayPaymentRequestDtlList.push(tbPayPaymentRequestDtl);
				});
				var tbPayPaymentRequestDTO = {
					'payment_REQ_BEGIN_DATE' : 	rowData1.payment_REQ_BEGIN_DATE.replace(/\//g,"-"),
					'payment_REQ_END_DATE' : 	rowData1.payment_REQ_END_DATE.replace(/\//g,"-"),
					'contract_NO' : 			rowData1.contract_NO,
					'payment_MONTHS' : 			rowData1.payment_MONTHS,
					'tbPayPaymentRequestDtlDTOList' : tbPayPaymentRequestDtlList
				};
				tbPayPaymentRequestDTOList.push(tbPayPaymentRequestDTO);
			};*/
		} else {
			alert("未勾選要請款的資料列,請重新操作!");
			return false;
		};
		/*
		var tbPayCashRequirementDTO = {
				domain : 		$("#detailContent #domainSelect").val(),
				process_TYPE : 	$("#detailContent #processType").val(),
				app_USER : 		$("#detailContent #appUser").val(),
				app_DATE : 		$("#detailContent #appDate").val().replace(/\//g,"-"),
				year_MONTH : 	$("#detailContent #yearMonth").val(),
				payment_PERIOD :$("#detailContent #paymentPeriod").val(),
				status : 		"A",
				tbPayPaymentRequestDTOList : tbPayPaymentRequestDTOList
			};
		$.ajax({
			url : CONTEXT_URL + "/pay/pay001/money",
			data : JSON.stringify(tbPayCashRequirementDTO),
			contentType : "application/json",
			type : "POST",
			dataType : 'json',
			success : function(data) {
				if(data.success){
					alert(data.msg);
					isDetail = true;
					parent.$.fancybox.close();				
				}else{
					alert(data.msg);
				};
			},
			error : function(data) {
				alert(data.msg);
			}
		});	*/	
		
		var data = {
				key : theKEY,
				processType : $("#detailContent #processType").val(),
				paymentPeriod : $("#detailContent #paymentPeriod").val(),
				paymentMonth : $("#detailContent #yearMonth").val(),
				appDate : "<fmt:formatDate  value="${master.APP_DATE}"  pattern="yyyy/MM/dd"  />"
			};
		processAjax("/pay/pay001/money", 
				data,
				null,
				false,
				function(data) {
					if(data.success){
						alert(data.msg);
						isDetail = true;
						parent.$.fancybox.close();				
					}else{
						alert(data.msg);
					};
				});		
	};
	//取得Grid2 & grid3 資料
	function getGrid2Data(ajaxData){
		$("#dGrid2").jqGrid().clearGridData();
		$("#dGrid3").jqGrid().clearGridData();
		if(theGrid2Data[theDGrid1SelectedRowId] != undefined){
			$.each(theGrid2Data[theDGrid1SelectedRowId] , function(index, rosterEntry) {
				jQuery("#dGrid2").jqGrid('addRowData', index, rosterEntry);
			});
		}else{
			processAjax("/pay/pay001/searchDetail2ByProcess", 
					ajaxData,
					null,
					false,
					function(data) {
						if(theGrid3Data.length == 0){
							theGrid3Data = data.maps.grid3;
						}else{	
							var cnt = theGrid3Data.length;
							$.each(data.maps.grid3, function(index, rosterEntry) {
								theGrid3Data.splice(cnt+index,0,rosterEntry);
							});
						};	
						theGrid2Data[theDGrid1SelectedRowId] = data.maps.grid2;
						theKEY = data.maps.key;
						$.each(data.maps.grid2, function(index, rosterEntry) {
							jQuery("#dGrid2").jqGrid('addRowData', index, rosterEntry);
						});
					});		
		};
		
	};
	function getGrid3Data(){
		$("#dGrid3").jqGrid().clearGridData();
		var myId = $("#dGrid2").jqGrid('getGridParam', 'selrow');// 取得勾選資料id
		var rowData1 = $("#dGrid1").getRowData(theDGrid1SelectedRowId);
		var rowData2 = $("#dGrid2").getRowData(myId);
		
		$.each(theGrid3Data, function(index, rosterEntry) {
			if(rosterEntry._id == rowData2._id && rosterEntry.contractNo == rowData1.contract_NO)
			jQuery("#dGrid3").jqGrid('addRowData', index, rosterEntry);
		});
	};
	//=======共用  功能===============
	//日期加減 (date-longtime, mm月, dd天數)
	function addDate(date, mm, dd){
		var endDate = new Date();
		var tmpDate;
		tmpDate = new Date(date);
		endDate.setFullYear(tmpDate.getFullYear(), tmpDate.getMonth()+parseInt(mm), tmpDate.getDate()+parseInt(dd));
		return endDate.getFullYear() + "/" + addZero(endDate.getMonth() + 1) + "/" + addZero(endDate.getDate()) ;
	};
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
	//維運區連動 申請人
	function selDomainChange(){	
		$("#detailContent #appUser").select2("val", "");
		processAjax("/ajax/pay/public/appUserDomain", 
				{domain : $("#detailContent #domainSelect").val()},
				null,
				false,
				function(data) {
					var selObj = $("#detailContent #appUser");
					selObj.html('<option value="" selected>--請選擇--</option>');
					$.each(data.rows, function(index, rosterEntry) {
						selObj.append('<option value="'+rosterEntry.psn_NO+'">'+rosterEntry.chn_NM+'</option>');
					});
					selObj.removeAttr("readonly").removeAttr("disabled");
				});		
	};
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
					if(closeFancybox){
						isDetail = true;
						parent.$.fancybox.close();				
					};
				};
			},
			error:function(xhr, textStatus, errorThrown) { 
				isDetail = true;
			}
		});
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
		var myPeriod = $(myParent+" #paymentPeriod");
		if(myPeriod.val()!="" && !gIsDigit(myPeriod.val())){
			alert('付款週期 必需為數值');
			myPeriod.focus();
			return false;
		};
		return true;
	};
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
	<div id="detailContent" class="col-xs-12 col-sm-10" style="width: 100%">		
		<div id="ajax-content">
			<div class="row">
				<div class="col-xs-12 col-sm-12">
					<div class="box ui-draggable ui-droppable">
						<div class="box-header">
							<i class="fa fa-edit"></i> <span>租/押金請款</span>
							<div class="no-move"></div>
						</div>
						<div class="box-content">	
						<!-- 功能 Button -->
							<div id="breadcrumb" class="col-md-12">
								<ol class="breadcrumb">
									<div class="col-sm-12" id="btnDiv">
									<c:if test="${type eq 'E'}"> 
										<button class="btn btn-primary btn-label-left" type="button"
											id="delDtlBtn">刪除明細</button>
										<button class="btn btn-primary btn-label-left" type="button"
											id="saveBtn">
											<span><i class="fa fa-save"></i></span> 儲存
										</button>
										<button class="btn btn-primary btn-label-left" type="button"
											id="approvedBtn">
											送審
										</button>
									</c:if>
									<c:if test="${type eq 'A'}"> 
										<button class="btn btn-primary btn-label-left" type="button"
											id="processBtn">資料處理</button>										
										<button class="btn btn-primary btn-label-left" type="button"
											id="moneyBtn">
											請款
										</button>
									</c:if>
									</div>
								</ol>
							</div>
						<!-- 內容 -->
						<c:if test="${not (type eq 'A')}">
							<div id="button" style="text-align:center;">
								<font color='#CC0000'>&nbsp;&nbsp;&nbsp;&nbsp;請款單號 :${master.CASH_REQ_NO}</font>
							</div>
							<br>
						</c:if>
							<div class="form-group">
							<form id="editFrom" class="form-horizontal bootstrap-validator-form" role="form" novalidate="novalidate">
							<table style="width:100%">
								<tr>
									<td><label class="col-sm-12 control-label"><span class="s">* </span>維運區 :</label></td>
									<td>
										<div class="col-sm-6">
												<select id="domainSelect" name="domainSelect"  class="populate placeholder require">
													<option value="">--請選擇--</option>
													<c:forEach items="${domainSelect}" var="domain">
														<option value="${domain.ID}"<c:if test="${domain.ID eq master.DOMAIN}"> selected</c:if>>
														${domain.NAME}
														</option>
													</c:forEach>
												</select>
										</div>
									</td>
									<td><label class="col-sm-12 control-label"><span class="s">* </span>處理類別 :</label></td>
									<td>
										<div class="col-sm-6">
											<select id="processType" name="processType"  class="populate placeholder require">
												<option value="">--請選擇--</option>
												<c:forEach items="${processTypeSelect}" var="domain">
													<option value="${domain.LOOKUP_CODE}"<c:if test="${domain.LOOKUP_CODE eq master.PROCESS_TYPE}"> selected</c:if>>
													${domain.LOOKUP_CODE_DESC}
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
									<td><label class="col-sm-12 control-label"><span class="s">* </span>付款週期 : </label></td>
									<td>
										<div class="col-sm-6">
										<input id="paymentPeriod" type="text" value="${master.PAYMENT_PERIOD}" class="form-control require" name="paymentPeriod">
										</div>
									</td>
									<td><label class="col-sm-12 control-label"><span class="s">* </span>申請人 : </label></td>
									<td>
										<div class="col-sm-6">
											<select id="appUser" name="appUser"  class="populate placeholder require">
											<c:if test="${not(type eq 'A')}">
												<option value="${personnelSelect.PSN_NO}">
												${personnelSelect.CHN_NM}
												</option>
											</c:if>
											<c:if test="${type eq 'A'}">
												<option value="">--請選擇--</option>
												<c:forEach items="${personnelSelect}" var="domain">
													<option value="${domain.PSN_NO}"<c:if test="${domain.PSN_NO eq master.APP_USER}"> selected</c:if>>
													${domain.CHN_NM}
													</option>
												</c:forEach>
											</c:if>
											</select>
										</div>
									</td>
								</tr>
								<tr>
									<td><div class="clearfix">&nbsp;</div></td>
								</tr>
								<tr>
									<td><label class="col-sm-12 control-label"><span class="s">* </span>申請日期 : </label></td>
									<td>
										<div class="col-sm-6">
										<input id="appDate" type="text" value="<fmt:formatDate  value="${master.APP_DATE}"  pattern="yyyy/MM/dd"  />" name="appDate" class="form-control require" readonly="readonly">
										</div>
									</td>
									<td><label class="col-sm-12 control-label"><span class="s">* </span>請款年月: </label></td>
									<td>
										<div class="col-sm-6">
										<input id="yearMonth" type="text" value="${master.YEAR_MONTH}" name="yearMonth"  class="form-control require" readonly="readonly" placeholder="YYYYMM">
										</div>
									</td>
								</tr>
							</table>
						</form>
						</div>
						</div>
					</div>
				</div>

				<!-- 請款清單 -->
				<div id="ajaxSearchResult" class="col-xs-12">
					<div id="jQgrid1" align="center">
						<table id="dGrid1"></table>
					</div>
				</div>
				<br/>
				<div class="clearfix">&nbsp;</div>
				<!-- 租約基站清單 -->
				<div id="ajaxSearchResult" class="col-xs-12">
					<div id="jQgrid2" align="center">
						<table id="dGrid2"></table>
					</div>
				</div>
				<br/>
				<div class="clearfix">&nbsp;</div>
				<!-- 基站請款紀錄 -->
				<div id="ajaxSearchResult" class="col-xs-12">
					<div id="jQgrid3" align="center">
						<table id="dGrid3"></table>
					</div>
				</div>				
			</div>
		</div>
	</div>

</body>
</html>