<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>憑證維護</title>
<script type="text/javascript">
	var theGrid2Data = [];
	var theGrid3Data = [];
	var theGrid1DataId = -1; //grid1Id, 新資料使用 負數
	var theGrid2DataId = -1; //grid2Id
	//var theGrid3DataId = 0; //grid3Id =>item_NO
	var theDGrid1SelectedRowId = 0;
	var theDGrid2SelectedRowId = 0;
	var theActionType = "";
	var theDeleteGrid1Id =[];
	var theDeleteGrid2Id =[];
	//for jqgrid edit	
	var voucherTypeSelect = ":--請選擇--";
	<c:if test="${type eq 'A'}">
	<c:forEach items="${voucherTypeSelect}" var="domain">
	voucherTypeSelect+=";${domain.LOOKUP_CODE}:${domain.LOOKUP_CODE_DESC}";
	</c:forEach>
	</c:if>
	
	var prTypeSelect = ":--請選擇--";
	<c:forEach items="${prTypeSelect}" var="domain">
	prTypeSelect+=";${domain.LOOKUP_CODE}:${domain.LOOKUP_CODE_DESC}";
	</c:forEach>
	
	var locationIdSelect = ":--請選擇--";
	<c:forEach items="${locationIdSelect}" var="domain">
	locationIdSelect+=";${domain.LOCATION_ID}:${domain.LOCATION_ID}";
	</c:forEach>
	
	$.datepicker.setDefaults({
		dateFormat : "yy/mm/dd",
		changeYear : true,
		changeMonth : true,
		showButtonPanel : true,
		showTime : false});
	$(document).ready(function() {
		LoadSelect2Script("<s:url value="/resources/plugins/select2/select2.min.js" />", selectDefault1);		
		mountGrid();
		<c:if test="${type eq 'D'}">
		//隱藏所有 Button
		$("#detailContent button").hide();
		</c:if>
		<c:if test="${type eq 'A'}">
		$("#detailContent #addDtlBtn").click(btAdd);	
		$("#detailContent #cancelDtlBtn").click(btCancel);
		$("#detailContent #saveDtlBtn").click(btSave);
		</c:if>
		<c:if test="${type eq 'E' || type eq 'A'}">
		$("#detailContent #saveBtn").click(btSaveAll);
		$("#detailContent #delDtlBtn").click(btDelete);
		$("#detailContent #addDtlBtn2").click(btAdd2);
		$("#detailContent #delDtlBtn2").click(btDelete2);
		$("#detailContent #saveDtlBtn2").click(btSave2);
		$("#detailContent #cancelDtlBtn2").click(btCancel2);
		$("#detailContent #saveDtlBtn3").click(btAdd3);
		</c:if>
			
		getDGrid1Data();
		btShowHide();
	});
	//掛載表格
	function mountGrid(){
		$("#dGrid1").jqGrid({
			datatype : "local",
			url : CONTEXT_URL + "/pay/pay009/searchDetail1",
			autowidth:'true',
			colNames:['憑證處理單號','憑證類別','憑證號碼','憑證日期','統一編號','未稅金額','稅額','總額','','',''],
			colModel:[
				{name:'voucher_NO',index:'voucher_NO',width:80, sortable: false, hidden:true},
				{name:'voucher_TYPE_DESC',index:'voucher_TYPE_DESC',width:60, sortable: false, editable:true,edittype:"select",
					editoptions:{
						value : voucherTypeSelect,
						dataInit:function(colId){$(colId).select2();},
						class : "require" ,
						dataEvents: [
				                      {  type: 'change',
				                         fn: function(e) {
				                        	 $('#dGrid1 input[name=voucher_TYPE]').val($(this).val());
				                         }
				                      }
				                   ]
				}},
				{name:'voucher_NBR',index:'voucher_NBR',width:70, sortable: false, editable:true,editoptions:{class : "require"}},
				{name:'voucher_DATE',index:'voucher_DATE', width:80,sortable: false, formatter: "date", formatoptions:{newformat: "Y/m/d"},
					editable:true,
		   			editoptions:{
		   				readonly:true,
		   				class : "form-control require" ,
		   				placeholder : "點選輸入框",
		   				dataInit:function(colId){$(colId).datepicker();}
		   			}},				
				{name:'vat_NUMBER',index:'vat_NUMBER', width:60,sortable: false, editable:true,editoptions:{class : "require"}},
				{name:'tax_EXCLUSIVE_AMT',index:'tax_EXCLUSIVE_AMT', width:60,sortable: false, align : 'right', 
					editable:true,
		   			editoptions:{
						class : "number require",
		   				dataEvents: [
				                      {  type: 'change',
				                         fn: function(e) {	
				                     		taxCalculate($(this), $("#dGrid1 input[name=business_TAX]"), $("#dGrid1 input[name=totalAmt]"));
				                         }
				                      }
				                   ]
		   			}},		
				{name:'business_TAX',index:'business_TAX', width:70,sortable: false, align : 'right', editable:true,editoptions:{readonly:true}},
				{name:'totalAmt',index:'totalAmt', width:80,sortable: false, align : 'right', editable:true,editoptions:{readonly:true}},			
				{name:'gridId',index:'gridId', hidden:true, editable:true},
				{name:'seq_NBR',index:'seq_NBR', hidden:true},
				{name:'voucher_TYPE',index:'voucher_TYPE', width:0,hidden:true, editable:true}
			],	
			caption : "憑證輸入",
			rownumbers : true,
			beforeSelectRow: function (rowid, e) {
				if (theActionType!="" && rowid!=theDGrid1SelectedRowId) {
					alert("尚在修改中,請先儲存或取消!!");
					return false;
				};
				return true;
			},
			onSelectRow : function(rowId) {				
				var rowData = jQuery(this).getRowData(rowId);

				theDGrid1SelectedRowId = rowId;	
				var data = {mstSeqNbr : rowData.seq_NBR};
				settingGridData(theGrid2Data, rowData.gridId, "dGrid2", data);
				$("#dGrid3").jqGrid().clearGridData();				
				$("#addBillListDiv").show();
			},
			gridComplete : function() {
				theDGrid1Records = $("#dGrid1").jqGrid('getGridParam', 'records');
				<c:if test="${not (type eq 'A')}">
				//set gridId = seq_NBR
				var gridRow;
				var ids = $("#dGrid1").jqGrid('getDataIDs');
				for(var i=0;i < ids.length;i++){
					gridRow = $( "#dGrid1" ).jqGrid('getRowData',ids[i]);
					jQuery("#dGrid1").jqGrid('setRowData', ids[i], {gridId : gridRow.seq_NBR});
				};
				</c:if>
			}
		});	
		$("#dGrid2").jqGrid({
			datatype : "local",
			autowidth:'true',
			url : CONTEXT_URL + "/pay/pay009/searchDetail2",
			colNames:['請款類別','站點編號','請款金額','未稅金額','營業稅','已沖金額','沖銷金額','沖銷日期','','','','',''],
			colModel:[
				{name:'prTypeDesc',index:'prTypeDesc',width:60, sortable: false, editable:true ,edittype:"select",
					editoptions:{
					value : prTypeSelect,
					class : "require" ,
					dataInit:function(colId){$(colId).select2();},
					dataEvents: [
			                      {  type: 'change',
			                         fn: function(e) {
			                        	 $('#dGrid2 input[name=pr_TYPE]').val($(this).val());
			                        	 billTaxAmtChange($(this));
			                         }
			                      }
			                   ]
				}},
				{name:'location_ID',index:'location_ID',width:80, sortable: false, editable:true ,edittype:"select",
					editoptions:{
						value : locationIdSelect,
						class : "require" ,
						dataInit:function(colId){$(colId).select2();},
						dataEvents: [
				                      {  type: 'change',
				                    	  fn: function(e) {
					                        	 billTaxAmtChange($(this));
					                         }
				                      }
				                   ]
					}},
				{name:'sumPrAmt',index:'sumPrAmt', width:80,sortable: false, align : 'right'},
				{name:'sumTaxExclusiveAmt',index:'sumTaxExclusiveAmt', width:60,sortable: false, align : 'right'},
				{name:'sumBusinessTax',index:'sumBusinessTax', width:60,sortable: false, align : 'right'},
				{name:'sumCreditAmt',index:'sumCreditAmt', width:80,sortable: false, align : 'right'},
				{name:'reversalAmt',index:'reversalAmt', width:80,sortable: false, align : 'right'},
				{name:'credit_DATE',index:'credit_DATE',width:90, sortable: false, formatter: "date", formatoptions:{newformat: "Y/m/d"},
					editable:true,
		   			editoptions:{
		   				readonly:true,
		   				class : "form-control require" ,
		   				placeholder : "點選輸入框",
		   				dataInit:function(colId){$(colId).datepicker();}
		   			}},				
				{name:'payment_REQ_NO',index:'payment_REQ_NO', hidden:true},
				{name:'pr_TYPE',index:'pr_TYPE',  hidden:true, editable:true},
				{name:'isValid',index:'isValid', hidden:true},
				{name:'parentId',index:'parentId', hidden:true},
				{name:'gridId',index:'gridId', hidden:true}
			],	
			caption : "沖銷對象",
			rownumbers : true,
			beforeSelectRow: function (rowid, e) {
				if (theActionType!="" && rowid!=theDGrid2SelectedRowId) {
					alert("尚在修改中,請先儲存或取消!!");
					return false;
				};
				return true;
			},
			onSelectRow : function(rowId) {	
				if (theActionType!="")return false;
				var rowData = jQuery(this).getRowData(rowId);
				var rowData1 = $("#dGrid1").getRowData(theDGrid1SelectedRowId);
				var data = {
						mstSeqNbr : rowData1.seq_NBR,
						locationId : rowData.location_ID,
						prType : rowData.pr_TYPE
						};
				//settingGridData(theGrid3Data, rowData.gridId, "dGrid3", data);
				
				$("#dGrid3").setGridParam({datatype : "json",postData : data, rowNum:100000}).trigger("reloadGrid");			
				theDGrid2SelectedRowId = rowId;								
			},
			gridComplete : function() {				
				var rowData = $("#dGrid1").getRowData(theDGrid1SelectedRowId);
				var ids = $("#dGrid2").jqGrid('getDataIDs');
				var gridRow;
				for(var i=0;i < ids.length;i++){
					gridRow = $( "#dGrid2" ).jqGrid('getRowData',ids[i]);
					if(gridRow["gridId"] == undefined || gridRow["gridId"] == ""){
						gridRow["gridId"] = theGrid2DataId--;
					};
					jQuery("#dGrid2").jqGrid('setRowData', ids[i], 
						{paymentReqBeginDate : rowData.pyment_REQ_BEGIN_DATE, paymentReqEndDate : rowData.payment_REQ_END_DATE,
						isValid:true, gridId: gridRow["gridId"]});
					gridRow = $( "#dGrid2" ).jqGrid('getRowData',ids[i]);
					gridRow["parentId"]=rowData.gridId;
					addEditTmpGridData(theGrid2Data, gridRow, "gridId");
				};	
			}
		});	
		
		$("#dGrid3").jqGrid({
			datatype : "local",
			autowidth:'true',
			url : CONTEXT_URL + "/pay/pay009/searchDetail3/",
			colNames:['請款單號','合約編號','請款對象','名稱','本次請款期間','請款金額','未稅金額','營業稅','已沖金額','沖銷金額','','',''],
			colModel:[
				{name:'cashReqNo',index:'cashReqNo',width:90, sortable: false},
				{name:'contractNo',index:'contractNo',width:80, sortable: false},
				{name:'paymentReqUserId',index:'paymentReqUserId',width:80, sortable: false},
				{name:'paymentUserName',index:'paymentUserName', width:80,sortable: false},
				{name:'paymentReqDate',index:'paymentReqDate', width:120,sortable: false},
				{name:'sumPrAmt',index:'sumPrAmt', width:60,sortable: false, align : 'right'},
				{name:'tax_EXCLUSIVE_AMT',index:'tax_EXCLUSIVE_AMT', width:60,sortable: false, align : 'right'},
				{name:'business_TAX',index:'business_TAX', width:60,sortable: false, align : 'right'},
				{name:'sumCreditAmt',index:'sumCreditAmt', width:60,sortable: false, align : 'right'},
				{name:'reversalAmt',index:'reversalAmt', width:60,sortable: false, align : 'right', editable:true,editoptions:{class : "number require"}},		
				{name:'payment_REQ_NO',index:'payment_REQ_NO', hidden:true},
				{name:'parentId',index:'parentId', hidden:true},
				{name:'item_NO',index:'item_NO', hidden:true}
			],	
			caption : "請款單",
			rownumbers : true,
			<c:if test="${not (type eq 'D')}">
			multiselect: true,
			onSelectAll : function(rowids, status) {
				var ids = $("#dGrid3").jqGrid('getDataIDs');
				for(var i=0;i < ids.length;i++){
					if(status){
						$('#dGrid3').editRow(ids[i]);
					}else{
						$('#dGrid3').restoreRow(ids[i]);
					};
				};
			},
			onSelectRow : function(rowId) {
				var isChecked = ($(this).find('#'+rowId+' input[type=checkbox]:checked').length==1);
				if(isChecked){
					$('#dGrid3').editRow(rowId);
				}else{
					$('#dGrid3').restoreRow(rowId);
					jQuery("#dGrid3").jqGrid('setRowData', rowId, 
							{reversalAmt : ""});
				};	
				//$("#dGrid3 input[name=reversalAmt]").change(dGrid3ReversalAmtChange);
				$("#saveDtlBtn3").show();
			},
			</c:if>
			gridComplete : function() {
				//檢查是否已勾選過修改金額
				var ids = $("#dGrid3").jqGrid('getDataIDs');
				var rowData = $("#dGrid2").getRowData(theDGrid2SelectedRowId);
				for(var i=0;i < ids.length;i++){
					gridRow = $( "#dGrid3" ).jqGrid('getRowData',ids[i]);
					for(var x = 0; x<theGrid3Data.length ;x++){						
						if(rowData.gridId == theGrid3Data[x].parentId && theGrid3Data[x].isValid
								&& theGrid3Data[x].item_NO == gridRow.item_NO && theGrid3Data[x].payment_REQ_NO == gridRow.payment_REQ_NO){
							jQuery("#dGrid3").jqGrid('setRowData', ids[i], 
									{reversalAmt : theGrid3Data[x].reversalAmt});
							$("#dGrid3").jqGrid('setSelection', ids[i], true);
						};
					};
				};
				if(theActionType != ""){
					var prType = $('#dGrid2 select[name=prTypeDesc]');
					var locationId = $('#dGrid2 select[name=location_ID]');		
					if(prType.val()=="" || locationId.val()=="") return false;
					if(ids.length == 0 && theDGrid2SelectedRowId > 0){
						alert("無任何請款單資料,建議重新選擇!!\n\n以避免無法新增!!");
					};
				};

				if($("#dGrid3").jqGrid('getGridParam', 'selarrrow').length>0){
					$("#saveDtlBtn3").show();
				}else{
					$("#saveDtlBtn3").hide();
				};
			}
		});	
		<c:if test="${type eq 'D'}">
		$("#dGrid1").jqGrid('showCol',"voucher_NO");
		$("#dGrid2").jqGrid('hideCol',"reversalAmt");
		$("#dGrid3").jqGrid('hideCol',"reversalAmt");
		</c:if>
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
		//$("#detailContent #voucherType").select2();
		$("#detailContent #prType").select2();
		$("#detailContent #locationId").select2();		
	};
	function getDGrid1Data(){
		var data = {
				voucherNo : '${voucherNo}'
		};
		$("#dGrid1").setGridParam({datatype : "json",postData : data, rowNum:100000}).trigger("reloadGrid");
	};
	//=======button 功能===============		
	//真正儲存至後端
	function btSaveAll(){	
		if (theActionType!="" ) {
			alert("尚在修改中,請先儲存或取消!!");
			return false;
		};
		var voucherList =[];
		var voucherRecord ={};
		var voucherCredit ={};
		var creditList;		
		var myId = $("#dGrid1").jqGrid('getDataIDs');
		for (var i = 0 ; i<myId.length ; i++) {
			var rowData = $("#dGrid1").getRowData(myId[i]);
			creditList = [];
			for(var x=0 ; x<theGrid2Data.length ; x++){				
				if(theGrid2Data[x].parentId == rowData.gridId && theGrid2Data[x].isValid){					
					for(var y=0 ; y<theGrid3Data.length ; y++){
						if(theGrid3Data[y].parentId == theGrid2Data[x].gridId ){													
							voucherCredit = {
								'payment_REQ_NO' : 	theGrid3Data[y].payment_REQ_NO,
								'payment_REQ_ITEM_NO':theGrid3Data[y].item_NO,
								'cash_REQ_AP_NO' : 	theGrid3Data[y].cashReqNo,
								'contract_PO_NO' : 	theGrid3Data[y].contractNo,
								'credit_AMT' : 		theGrid3Data[y].reversalAmt,
								'credit_DATE' : 	theGrid2Data[x].credit_DATE.replace(/\//g,"-"),
								'pr_TYPE' : 		theGrid2Data[x].pr_TYPE,
								'location_ID' : 	theGrid2Data[x].location_ID,
								'payment_REQ_USER_ID' : theGrid3Data[y].paymentReqUserId
							};
							creditList.push(voucherCredit);
						};
					};
				};
			};
			if(creditList.length>0){
				voucherRecord = {
					'seq_NBR' 		: 	rowData.seq_NBR,
					'voucher_TYPE' : 	rowData.voucher_TYPE,
					'voucher_NBR' : 	rowData.voucher_NBR,
					'voucher_DATE' : 	rowData.voucher_DATE.replace(/\//g,"-"),
					'vat_NUMBER' : 		rowData.vat_NUMBER,
					'tax_EXCLUSIVE_AMT':rowData.tax_EXCLUSIVE_AMT,
					'business_TAX' : 	rowData.business_TAX,
					'tbPayVoucherCreditList': creditList
				};
				voucherList.push(voucherRecord);
			};
		};
		<c:if test="${not(type eq 'A')}">
		//加上刪除者-detail
		if(theDeleteGrid2Id.length>0){
			voucherRecord = {
					'seq_NBR' : 	-99,
					'editType': "DEL",
					'tbPayVoucherCreditList': theDeleteGrid2Id
				};
			voucherList.push(voucherRecord);
		};
		//加上刪除者-master
		for(var i=0 ; i<theDeleteGrid1Id.length ; i++){
			voucherRecord = {
					'seq_NBR' : theDeleteGrid1Id[i],
					'editType': "DEL"
				};
			voucherList.push(voucherRecord);
		};
		</c:if>
		if(voucherList.length == 0){
			alert("尚未做任何修改!!");
			return false;
		};
		//alert(JSON.stringify(voucherList));
		//return false;
		$.ajax({
			<c:if test="${type eq 'A'}">	
				url : CONTEXT_URL + "/pay/pay009/add",
			</c:if>
			<c:if test="${type eq 'E'}">	
				url : CONTEXT_URL + "/pay/pay009/edit",
			</c:if>
				data : JSON.stringify(voucherList),
				contentType : "application/json",
				type : "POST",
				dataType : 'json',
				success : function(data) {
					alert(data.msg);
					isDetail = true;
					parent.$.fancybox.close();	
				},
				error : function(data) {
					alert(data.msg);
				}
		});			
	};

	//增加 憑證輸入
	function btAdd(){
		//var gridId = $("#dGrid1").jqGrid('getDataIDs');
		theDGrid1SelectedRowId = theGrid1DataId--;
		theActionType = "add";
		$("#dGrid1").jqGrid('addRow',{
			rowID : theDGrid1SelectedRowId,
		    initdata : {},
		    position :"first",
		});
		btShowHide("addVoucherDiv");
		$("#dGrid2").jqGrid().clearGridData();
		$("#dGrid3").jqGrid().clearGridData();
	};

	//取消修改
	function btCancel(){
		var myId = $("#dGrid1").jqGrid('getGridParam', 'selrow');// 取得勾選資料id	
		$('#dGrid1').restoreRow(myId);	
		theActionType = "";
		btShowHide();
	};
	//儲存 憑證
	function btSave(){
		if(!PayValidate("#dGrid1"))return false;
		var myId = $("#dGrid1").jqGrid('getGridParam', 'selrow');// 取得勾選資料id	
		$("#dGrid1 input[name=gridId]").val(theGrid1DataId--);
		$("#dGrid1").saveRow(myId, false, 'clientArray');
		theActionType = "";
		btShowHide();
		$("#addBillListDiv").show();
	};
	//增加 沖銷對象
	function btAdd2(){
		//var gridId = $("#dGrid2").jqGrid('getDataIDs');
		theDGrid2SelectedRowId = theGrid2DataId--;
		theActionType = "add";
		$("#dGrid2").jqGrid('addRow',{
			rowID : theDGrid2SelectedRowId,
		    initdata : {},
		    position :"first",
		});
		btShowHide("addBillListDiv");
	};
	//取消修改
	function btCancel2(){
		var myId = $("#dGrid2").jqGrid('getGridParam', 'selrow');// 取得勾選資料id	
		$('#dGrid2').restoreRow(myId);	
		theActionType = "";
		btShowHide();
	};
	//儲存 沖銷對象
	function btSave2(){
		if(!PayValidate("#dGrid2"))return false;
		var myId = $("#dGrid2").jqGrid('getGridParam', 'selrow');// 取得勾選資料id		
		var rowData2 = $("#dGrid2").getRowData(myId);
		
		//增加 請款單
		if(!btAdd3())return false;
		
		var rowData1 = $("#dGrid1").getRowData(theDGrid1SelectedRowId);
		$("#dGrid2").saveRow(myId, false, 'clientArray');
		rowData2 = $("#dGrid2").getRowData(myId);
		rowData2["parentId"] = rowData1.gridId;
		rowData2["isValid"] = true;
		//rowData2["reversalAmt"] = sumReversalAmt;
		addEditTmpGridData(theGrid2Data, rowData2, "gridId");

		theActionType = "";
		btShowHide();
	};
	//刪除憑證
	function btDelete(){
		if(!confirm("是否確定要刪除?")){
			return false;
		};
		var myId = $("#dGrid1").jqGrid('getGridParam', 'selrow');// 取得勾選資料id
		if (myId == null) {
			alert("未選取要刪除的資料列,請重新操作!");
			return false;			
		};
		var row = $("#dGrid1").jqGrid('getRowData', myId);
		theDeleteGrid1Id.push(row.seq_NBR);
		$('#dGrid1').jqGrid('delRowData',myId);		
		$("#dGrid2").jqGrid().clearGridData();
		$("#dGrid3").jqGrid().clearGridData();
		$("#addBillListDiv").hide();
	};
	//刪除沖銷對象
	function btDelete2(){
		if(!confirm("是否確定要刪除?")){
			return false;
		};
		var myId = $("#dGrid2").jqGrid('getGridParam', 'selrow');// 取得勾選資料id
		if (myId == null) {
			alert("未選取要刪除的資料列,請重新操作!");
			return false;
		};
		var row = $("#dGrid2").jqGrid('getRowData', myId);		
		$('#dGrid2').jqGrid('delRowData',myId);
		delTempData(theGrid2Data, row.gridId);
		
		var rowData1 = $("#dGrid1").getRowData(theDGrid1SelectedRowId);
		theDeleteGrid2Id.push({mst_SEQ_NBR:rowData1.seq_NBR, pr_TYPE:row.pr_TYPE, location_ID:row.location_ID});
		
		$("#dGrid3").jqGrid().clearGridData();
	};
	//增加 請款單
	function btAdd3(){
		var grid3Cnt = $("#dGrid3 input[type=checkbox]:checked").length;
		if(grid3Cnt==0){
			alert("至少需輸入一筆請款單 沖銷金額!!");
			return false;
		};
		
		var rowData2 = $("#dGrid2").getRowData(theDGrid2SelectedRowId);		
		var isValid = true;
		var isChecked;
		var reversalAmt;
		//先刪除grid3 data
		delTempData(theGrid3Data, null, rowData2.gridId);
		//先檢查Grid3有無資料 //增加 請款單
		$("#dGrid3 tr:not(:first)").each(function(idx, obj) {			
			isChecked = ($('input[type=checkbox]:checked', this).length==1);

			if(!isChecked) return;
			reversalAmt = $("input[name=reversalAmt]",this);			
			if($.trim(reversalAmt.val())!="") {
				if(!gIsDigit(reversalAmt.val())){
					alert("僅能輸入數值,請重新操作!");
					reversalAmt.focus();
					isValid = false;
					return false;
				};	
				var gridRow = $("#dGrid3").getRowData($(this).attr("id"));
				var myRecord = {
						'reversalAmt' 	:reversalAmt.val(),//新增資料
						'cashReqNo' 	:gridRow.cashReqNo,//新增資料
						'contractNo' 	:gridRow.contractNo,//新增資料
						'business_TAX' 	:gridRow.business_TAX,//新增資料
						'paymentReqUserId' 	:gridRow.paymentReqUserId,//新增資料
						'item_NO' 		:gridRow.item_NO, //關連可由Grid2Id連結,需item_No+payment_REQ_NO
						'payment_REQ_NO':gridRow.payment_REQ_NO,//關連可由Grid2Id連結,需item_No+payment_REQ_NO
						'isValid' : true,
						'parentId' : rowData2.gridId
					};
				addEditTmpGridData(theGrid3Data, myRecord, "item_NO", "payment_REQ_NO");
				//sumReversalAmt += parseInt(reversalAmt.val());
				grid3Cnt++;
			}else{
				alert("請輸入金額!");
				reversalAmt.focus();
				isValid = false;
				return false;
			};
		});
		if(!isValid) return false;
		dGrid3ReversalAmtChange();
		alert("已加總『沖銷金額』");
		return true;		
	};
	//刪除暫存資料
	function delTempData(tempData, id, parentId){
		for(var i=0 ; i<tempData.length ; i++){
			if(id!=null && tempData[i].gridId == id){
				tempData[i].isValid = false;
			}else if(parentId != undefined && tempData[i].parentId == parentId){
				tempData[i].isValid = false;
			};
		};
	};
	//Grid3 金額加總至 Grid2
	function dGrid3ReversalAmtChange(){
		var amount=0;
		var isValid = true;		
		$("#dGrid3 input[name=reversalAmt]").each(function(idx, obj) {			
			if($.trim($(this).val())!=""){
				if(!gIsDigit($(this).val()) || parseInt($(this).val())<=0){
					alert('欄位 必需 為大於 0的 正整數');
					$(this).focus();
					isValid = false;
					return false;
				}else{
					amount += parseInt($(this).val());
				};
			}; 
		});
		if(!isValid)return false;
		$("#dGrid2").jqGrid('setRowData', theDGrid2SelectedRowId, {reversalAmt : amount});
	};

	//=======共用  功能===============
	//button 功能呈現或隱藏
	function btShowHide(actArea){
		if(actArea == undefined){
			$("#detailContent button").show();
			$("#detailContent button.group2").hide();
			$("#saveDtlBtn3").hide();
			return false;
		};
		$("#detailContent button[id!=saveBtn]").hide();
		if(saveBtn == "edit"){
			$("#"+actArea +" button.group1").show();
		}else{
			$("#"+actArea +" button.group2").show();
		};
		$("#saveDtlBtn3").hide();
		$("#dGrid3").jqGrid().clearGridData();		
	};
	//依 GridData 設定 jqGrid Data
	function settingGridData(gridData, parentId, gridName, ajaxData){
		var rownum = 0;
		$("#"+gridName).jqGrid().clearGridData();
		for(var i = 0 ; i<gridData.length ; i++){
			if(gridData[i].parentId == parentId && gridData[i].isValid){
				rownum ++;
				$("#"+gridName).jqGrid("addRowData", rownum, gridData[i]);
			};
		};
		//如果 gridData內無資料, 則重新查詢
		if(rownum == 0 ){
			$("#"+gridName).setGridParam({datatype : "json",postData : ajaxData, rowNum:100000}).trigger("reloadGrid");	
		};		
	};
	
	//與 Grid2Data, Grid3Data 比對,以做新增或修改
	function addEditTmpGridData(tmpGridData, newData, indexId, indexId2){
		for(var x=0 ; x<tmpGridData.length ; x++){
			if(newData[indexId] == tmpGridData[x][indexId]
					&& newData.parentId == tmpGridData[x].parentId 
					&& (indexId2 == undefined || (indexId2 != undefined && newData[indexId2] == tmpGridData[x][indexId2]))) {
				tmpGridData[x] = newData;
				return true;
			};
		};
		newData["isValid"] = true;
		tmpGridData.push(newData);
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
	//計算稅率/金額
	function taxCalculate(obj, taxObj, amtObj){
		if(obj.val()=="")return false;
		if(obj.val()!="" && (!isAllNumber(obj.val()) || parseInt(obj.val())<=0)){
			alert('欄位 必需 為大於 0的 正整數');
			obj.focus();
			return false;
		};
		var myTax = amountPlusTax(obj.val());
		taxObj.val(myTax);
		if(amtObj != undefined){
			amtObj.val(parseInt(myTax)+parseInt(obj.val()));		
		};	
	};
	//請款類別+基站編號 變動者,會更動金額
	function billTaxAmtChange(obj){				
		var prType = $('#dGrid2 select[name=prTypeDesc]');
		var locationId = $('#dGrid2 select[name=location_ID]');		
		if(prType.val()=="" || locationId.val()=="") return false;
		
		//檢查是否有重覆:
		var ids = $("#dGrid2").jqGrid('getDataIDs');
		var gridRow;
		for(var i=0;i < ids.length;i++){
			if(theDGrid2SelectedRowId == ids[i])continue;
			gridRow = $( "#dGrid2" ).jqGrid('getRowData',ids[i]);
			if(prType.val() == gridRow.pr_TYPE && locationId.val() == gridRow.location_ID){
				alert("已有重覆請款別及基站編號, 請重新選擇!!");
				obj.select2("val","");
				return false;
			};
		};

		processAjax("/pay/pay009/getDtlTaxAmt", 
				{prType : prType.val(), locationId : locationId.val()},
				null, false, 
				function(data){
					if(data.sumPrAmt == null){
						alert("無任何請款資料!!請重新選擇!!");
						obj.select2("val","");
						return false;
					};
					//取得請款單資料
					jQuery("#dGrid2").jqGrid('setRowData', theDGrid2SelectedRowId, 
							{sumPrAmt : data.sumPrAmt, sumTaxExclusiveAmt : data.sumTaxExclusiveAmt, 
						sumBusinessTax : data.sumBusinessTax, payment_REQ_NO:data.payment_REQ_NO});
					var ajaxdata = {prType : prType.val(), locationId : locationId.val()};
					$("#dGrid3").setGridParam({datatype : "json",postData : ajaxdata, rowNum:100000}).trigger("reloadGrid");
				});	
	};
	//計算金額 payFnGetTax
	function amountPlusTax(money){
		var myTax = 0;
		processAjax("/ajax/pay/public/payFnGetTax", 
				{type : "3",amount : money, includeTax : "N"},
				null, false, 
				function(data){
					myTax = (data.msg);
				});	
		return myTax;
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
		
		$(myParent+" .number").each(function() {
			if($(this).val()!="" && (!gIsDigit($(this).val()) || parseInt($(this).val())<=0)){
				alert('欄位 必需 為大於 0的 正整數');
				$(this).focus();
				isValid = false;
				return false;
			};
		});
		if(!isValid) return false;
		return true;
	};
	function nullForZero (cellvalue, options, rowObject)
	{
	   var returnValue=cellvalue;
	   if(cellvalue == "" || cellvalue == "undefined" || cellvalue == null){
	   			returnValue="0";
	   }
	   return returnValue;
	};	
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
						<c:if test="${type eq 'E' || type eq 'A'}">
							<div class="col-sm-12" id="btnDiv">								
								<button class="btn btn-primary btn-label-left" type="button"
									id="saveBtn">
									<span><i class="fa fa-save"></i></span> 儲存
								</button>
							</div>
						</c:if>
						<!-- 內容 -->
<!--憑證輸入  -->						
<div class="row" id="addVoucherDiv">
	<div class="col-xs-12">
		<div class="box">
			<div class="box-header">
				<div class="box-name">
					<i class="fa fa-table"></i>
					<span>憑證輸入</span>
					<c:if test="${type eq 'A'}">
						<button class="btn btn-primary btn-label-left group1" type="button"
							id="addDtlBtn">
							<span><i class="fa fa-plus"></i></span> 增加憑證</button>						
						<button class="btn btn-primary btn-label-left group2" type="button" style="display:none"
							id="cancelDtlBtn">
							<span><i class="fa fa-edit"></i></span> 取消修改</button>
						<button class="btn btn-primary btn-label-left group2" type="button" style="display:none"
							id="saveDtlBtn">
							<span><i class="fa fa-edit"></i></span> 儲存憑證</button>
					</c:if>
					<c:if test="${type eq 'E' || type eq 'A'}">
						<button class="btn btn-primary btn-label-left group1" type="button"
							id="delDtlBtn">
							<span><i class="fa fa-minus"></i></span>刪除憑證</button>
					</c:if>
				</div>
			</div>
			<div class="box-content no-padding">
				<table id="voucherTB" class="table table-bordered table-hover table-heading no-border-bottom" style="table-layout: fixed; word-break: break-all;" >
					<tfoot>
					<tr>
					<td>
					<!-- 憑證輸入 -->
				<div id="ajaxSearchResult">
					<div id="jQgrid1">
						<table id="dGrid1"></table>
					</div>
				</div>
					</td>
					</tr>
					</tfoot>
				</table>
			</div>
		</div>
	</div>
</div>

<!--沖銷對象  -->	
<div class="row" id="addBillListDiv" style="display:none">
	<div class="col-xs-12">
		
		<div class="box">
			<div class="box-header">
				<div class="box-name">
					<i class="fa fa-table"></i>
					<span>沖銷對象</span>
					<c:if test="${type eq 'E' || type eq 'A'}">
						<button class="btn btn-primary btn-label-left group1" type="button"
							id="addDtlBtn2">
							<span><i class="fa fa-plus"></i></span> 增加沖銷對象</button>					
						<button class="btn btn-primary btn-label-left group2" type="button" style="display:none"
							id="cancelDtlBtn2">
							<span><i class="fa fa-edit"></i></span> 取消修改</button>
						<button class="btn btn-primary btn-label-left group2" type="button" style="display:none"
							id="saveDtlBtn2">
							<span><i class="fa fa-edit"></i></span> 儲存沖銷對象</button>
						<button class="btn btn-primary btn-label-left group1" type="button"
							id="delDtlBtn2">
							<span><i class="fa fa-minus"></i></span>刪除沖銷對象</button>
					</c:if>
				</div>
			</div>
			<div class="box-content no-padding col-xs-12">
				<table id="billListTB" class="table table-bordered table-hover table-heading no-border-bottom" style="table-layout: fixed; word-break: break-all;" >
					<tfoot>
					<tr>
					<td colspan="6">
					<!-- 憑證輸入 -->
				<div id="ajaxSearchResult">
					<div id="jQgrid2">
						<table id="dGrid2"></table>
					</div>
				</div>
				<div class="clearfix">&nbsp;</div>
				<c:if test="${type eq 'E' || type eq 'A'}">
				<button class="btn btn-primary btn-label-left" type="button" style="display:none"
						id="saveDtlBtn3">
						<span><i class="fa fa-edit"></i></span> 儲存沖銷金額</button>
				</c:if>
				<!-- 請款單 -->
				<div id="ajaxSearchResult">
					<div id="jQgrid3">
						<table id="dGrid3"></table>
					</div>
				</div>		
					</td>
					</tr>
					</tfoot>
				</table>
			</div>
		</div>
	</div>
</div>

						
						
						</div>
					</div>
				</div>	
			</div>
		</div>
	</div>

</body>
</html>