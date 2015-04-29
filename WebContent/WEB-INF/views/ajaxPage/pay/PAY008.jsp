<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>

<!DOCTYPE html>
<html lang="en">
<head>
<style type="text/css">
	tr.highlight td {padding-top: 10px;}
</style>
<script type="text/javascript">
	
	var row2;
	
	$(document).ready(function() {
		//import Select2
		LoadSelect2Script('<s:url value="/resources/plugins/select2/select2.min.js" />',select2);
		
		mountGrid();
		
		mountButEvent();
		
	});
	
	function formatNumber (num) {
	    return num.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1,");
	}
	
	//掛載表格
	function mountGrid(){
		$("#grid").jqGrid({
			datatype : "local",
			//pager : '#pager',
			autowidth:'true',
			height:'240px',
			scroll:'true',
			url : CONTEXT_URL + "/pay/pay008/getStopPayData",
			colNames : [ '','','','請款單號', '狀態', '租約編號', '名稱', '押金', '租金', '付款月數', '起帳日', '請款期間起', '請款期間訖', '本次請款', '止付原因', '說明' ],
			colModel : [ {name : 'itemNo',index : 'payReqNo',align : 'center',sortable : false, hidden:true}, 
			             {name : 'payBillsAppDate',index : 'payReqNo',align : 'center',sortable : false, hidden:true}, 
			             {name : 'payment_REQ_NO',index : 'payReqNo',align : 'center',sortable : false, hidden:true}, 
			             {name : 'cash_REQ_NO',index : 'cashReqNo',align : 'center',sortable : false,width:250},
			             {name : 'processTypeName',index : 'processType',align : 'center',sortable : false},
			             {name : 'contract_NO',index : 'contractNo',align : 'center',sortable : false}, 
			             {name : 'lsMainName',index : 'lsMainName',align : 'center',sortable : false}, 
			             {name : 'locationPldgAmtSum',index : 'pldgAmt',align : 'center',width:'120px', sortable : false, formatter:'integer', formatoptions:{thousandsSeparator: ','}}, 
			             {name : 'locationRentAmtSum',index : 'rentAmt',align : 'center',width:'120px',sortable : false, formatter:'integer', formatoptions:{thousandsSeparator: ','}} ,
			             {name : 'payment_MONTHS',index : 'payMonths',align : 'center',width:'100px',sortable : false},
			             {name : 'lsMainDateFormat',index : 'lsDate',align : 'center',sortable : false},       
			             {name : 'begindDateFormat',index : 'beginDate',align : 'center',sortable : false},       
			             {name : 'endDateFormat',index : 'endDate',align : 'center',sortable : false},       
			             {name : 'payBillsTotal',index : 'total',align : 'center',width:'120px', sortable : false},       
			             {name : 'stopPayType', index: 'stpoPayType', editable:true, edittype:'select',editoptions:{value:'${reasonList}'}},       
			             {name : 'explain',index : 'textBox',align : 'center',sortable : false, editable:true, edittype:'text'},       
			            ],
			caption : "止付清單",
			multiselect : true,
			rownumbers : true,
			rowNum : -1,
			loadonce: true,
			onSelectRow : function(id,status) {
				
				if(status){
					//啟動欄位編輯
					jQuery('#grid').jqGrid('editRow',id);
				}else{
					//取消欄位編輯
					jQuery('#grid').jqGrid('restoreRow',id);
				}
				
				var array = [];
				
				var selectRow = jQuery(this).jqGrid('getGridParam','selarrrow');
				
				for(var i =0 ; i < selectRow.length ; i++){
					var rowData = jQuery(this).jqGrid('getRowData',selectRow[i]);
					
					var data ={
							'payment_REQ_NO':rowData['payment_REQ_NO'],
							'contract_NO':rowData['contract_NO'],
							'paymentMonths':rowData['payment_MONTHS'],
							'appDate':rowData['payBillsAppDate']
					};
					
					array.push(data);
				}
				
				//掛載租約站點清單
				mountDataMenu(array,true);
			},
			onSelectAll: function(aRowids, status) {
				
				var array = [];
				
				for(var i =0 ; i < aRowids.length ; i++){

					if(status){
						jQuery('#grid').jqGrid('editRow',aRowids[i]);
					}else{
						jQuery('#grid').jqGrid('restoreRow',aRowids[i]);
					}
				
					var rowData = jQuery(this).jqGrid('getRowData',aRowids[i]);
					
					var data ={
							'payment_REQ_NO':rowData['payment_REQ_NO'],
							'contract_NO':rowData['contract_NO'],
							'paymentMonths':rowData['payment_MONTHS'],
							'appDate':rowData['payBillsAppDate']
					};
					
					array.push(data);
				}
				
				//掛載租約站點清單
				mountDataMenu(array,status);
			},
			gridComplete : function() {
				
			},
			ondblClickRow: function(rowId) { 
				alert(rowId);
	        }
		});
		
		$(window).resize(function() {
			$(window).unbind("onresize");
			$("#grid,.grid").setGridWidth($("#jQgrid").width() - 10);
			$(window).bind("onresize", this);
		}).trigger('resize');
	}
	
	//掛載按鈕
	function mountButEvent(){
		
		//重置
		$('#btn_reset').click(function(){
			$('#btn_search').click();
			jQuery("#dataMenu tbody tr").remove();
			jQuery("#recordMenu tbody tr").remove();
		});
		
		//查詢按鈕
		$('#btn_search').click(function(){
			var stopPayType = $("#stopPayType option:selected").val();
			var domain = $("#maintainArea option:selected").val();
			var cashReqNo = $("#payNumber option:selected").val();

			
			if(stopPayType == ""){
				alert("請選擇止付類型 ");
				return ;
			}
			if(domain == ""){
				alert("請選擇維運區");
				return ;
			}
			if(cashReqNo == "" || cashReqNo == null){
				alert("查無對應請款單號，無法查詢");
				return ;
			}
			
			var data ={'cashReqNo':cashReqNo ,'domain':domain};

			$("#grid").setGridParam({datatype : "json",postData : data}).trigger("reloadGrid");
		});
		
		//儲存按鈕
		$('#btn_save').click(function(){			
			var selectRow = jQuery('#grid').jqGrid('getGridParam','selarrrow');
			if(selectRow.length <=0 ){
				alert("請勾選資料");
			}else{
				var domain = $("#maintainArea option:selected").val();
				var array = [];
				for(var i =0 ; i < selectRow.length ; i++){
					var rowData = jQuery('#grid').jqGrid('getRowData',selectRow[i]);
					var id = rowData['payPaymentRequest.payment_REQ_NO'];
					var stopPayType = $('#'+selectRow[i]+"_stopPayType").val();
					var explain = $('#'+selectRow[i]+"_explain").val();
					var cashReqNo = rowData['cash_REQ_NO'];//請款單號
					var contractNo = rowData['contract_NO'];//租約編號
					if(stopPayType == '' || stopPayType == null){
						alert("請款單號: "+id+" 請選擇止付原因! ");
						return;
					}
					$.log("cashReqNo : "+cashReqNo+" contractNo: "+contractNo);
					var data ={
							'id':id,
							'stopPayType':stopPayType,
							'explain':explain,
							'cashReqNo' : cashReqNo,
							'contractNo' : contractNo,
							'domain' : domain
					};
					array.push(data);
				}
				//更新請款資料
				updatePaymentRequest(array);
			}
		});
		
	}
	
	//Select2 UI 
	function select2() {
		
		$('#stopPayType').select2().on('change',function(e){
			mountPayNumber(e.val,$('#maintainArea').val());
		});
		
		$('#maintainArea').select2().on('change',function(e){
			mountPayNumber($('#stopPayType').val(),e.val);
		});
		
		$('#payNumber').select2();
	}
	
	//連動請款單號
	function mountPayNumber(payType,domain){
		//若止付類型獲或維運區 不完整 就不帶請款單號
		if(payType=="" || domain==""){
			return;
		}
		var data ={'payType':payType ,'domain':domain};
		
		$.ajax({			
			url : CONTEXT_URL+"/pay/pay008/getPayNumber",		
			data:data,
			type : "POST",
			dataType : 'json',
			success : function(data) {
				$("#payNumber  option").remove();
				if(data.success){
					if(data.rows.length > 0){
						for(var i=0; i<data.rows.length; i++){
							$("#payNumber").append("<option value="+data.rows[i].cash_REQ_NO+">"+data.rows[i].cash_REQ_NO+"</option>");
							if(i == 0){
								$("#payNumber").select2("val",data.rows[i].cash_REQ_NO);
							}
						}
					}
				}else{
					$("#payNumber").select2("val","");
				}
			}
		
		});
	}
	
	//掛載租約站點清單
	function mountDataMenu(array,status){
		
		jQuery("#dataMenu tbody tr").remove();
		jQuery("#recordMenu tbody tr").remove();
		
		if(status){
			
			for(var x = 0 ; x < array.length ;x++){
				
				var data ={	'contractNO':array[x].contract_NO,
						   	'paymentReqNo' : array[x].payment_REQ_NO,
						   	'paymentMonths':array[x].paymentMonths,
						   	'appDate':array[x].appDate
				};
				
				$.ajax({			
					url : CONTEXT_URL+"/pay/pay008/getLsLocationData",		
					data:data,
					async: false,
					type : "POST",
					dataType : 'json',
					success : function(data) {
						if(data.success){
							if(data.rows.length > 0){
								
								for(var i=0; i<data.rows.length; i++){

 									//本次請款 (租金 + 付款月數)
									var paymentRequest = formatNumber(parseInt(data.rows[i].tax_EXCLUSIVE_AMT + data.rows[i].business_TAX));
									
 									//預付餘額
									var prepaid = data.rows[i].prepaidBalance == null ? 0 : data.rows[i].prepaidBalance;
									
 									//類別名稱
 									var itemName = data.rows[i].itemName;
 									
		 							var rowContent = "<tr onClick = mountRecordMenu('"+array[x].contract_NO+"','"+data.rows[i].location_ID+"','"+array[x].payment_REQ_NO+"','"+array[x].appDate+"','"+data.rows[i].item_NO+"');>";
// 									var rowContent = "<tr>";
									
									rowContent += "<td>" + (x+1) + "</td>";
									rowContent += "<td>" + data.rows[i].location_ID + "</td>";
									rowContent += "<td>" + data.rows[i].payBeginDate + "</td>";
									rowContent += "<td>" + data.rows[i].lsEndDate + "</td>";

									if(itemName == "扣抵"){
										rowContent += "<td></td>";
										rowContent += "<td> "+ formatNumber(prepaid) +"</td>";
										rowContent += "<td> "+ itemName +" </td>";
										rowContent += "<td>  </td>";
										rowContent += "<td>  </td>";
										rowContent += "<td> "+ paymentRequest +" </td>";
										rowContent += "</tr>";
									}else{
										rowContent += "<td>" + formatNumber(data.rows[i].rentAmt) + "</td>";
										rowContent += "<td></td>";
										rowContent += "<td> "+ itemName +" </td>";
										rowContent += "<td> "+ data.rows[i].paymentReqBeginDate+" </td>";
										rowContent += "<td> "+ data.rows[i].paymentReqEndDate+" </td>";
										rowContent += "<td> "+ paymentRequest +" </td>";
										rowContent += "</tr>";
									}
									
									jQuery("#dataMenu tbody").append(rowContent);
								}
							}
						}
					}
				});
			}
		}
	}
	
	//掛載基站請款紀錄
	function mountRecordMenu(contractNo,locationId,paymentReqNo,appDate,itemNo){

		jQuery("#recordMenu tbody tr").remove();
		
		var data ={	'contractNo':contractNo,
					'locationId':locationId,			
					'paymentReqNo':paymentReqNo,
					'appDate':appDate,
					'itemNo':itemNo
		};			
		
		$.ajax({			
			url : CONTEXT_URL+"/pay/pay008/getPayPaymentData",		
			data:data,
			type : "POST",
			dataType : 'json',
			success : function(data) {
				if(data.success){
					if(data.rows.length > 0){
						for(var i=0; i<data.rows.length; i++){
							
							var rowContent = "<tr>";
							rowContent += "<td>" + data.rows[i].locPaymentUserId + " ("+data.rows[i].locPaymentUserName+")</td>";
							rowContent += "<td>" + data.rows[i].payment_USER_ID + "</td>";
							rowContent += "<td>" + data.rows[i].payment_USER_NAME + "</td>";
							rowContent += "<td>" + data.rows[i].payMethod + "</td>";
							rowContent += "<td>" + formatNumber(data.rows[i].rentFormat)+"</td>";
							rowContent += "<td>" + formatNumber(data.rows[i].amountPayableFormat) + "</td>";
							rowContent += "<td>" + formatNumber(data.rows[i].noneIncludedTaxFormat) + "</td>";
							rowContent += "<td>" + formatNumber(data.rows[i].taxOnUurnoverFormat) + "</td>";
							rowContent += "<td>" + formatNumber(data.rows[i].incomeTaxFormat) + "</td>";
							rowContent += "<td>" + formatNumber(data.rows[i].nhiAmtFormat) + "</td>";
							rowContent += "<td>" + formatNumber(data.rows[i].amountPaidFormat) + "</td>";
							rowContent += "</tr>";
							
							jQuery("#recordMenu tbody").append(rowContent);
							
						}
					}
				}
			}
		});
	}
	
	//更新請款資料
	function updatePaymentRequest(array){
		var data = {'rowDataList':JSON.stringify(array)};
		$.ajax({			
			url : CONTEXT_URL+"/pay/pay008/updatePaymentRequest",		
			data:data,
			type : "POST",
			dataType : 'json',
			success : function(data) {
				if(data){
					alert("止付變更儲存成功!");
					$('#btn_search').click();
					jQuery("#dataMenu tbody tr").remove();
					jQuery("#recordMenu tbody tr").remove();
				}else{
					alert("止付變更儲存失敗!");
				}
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
						<i class="fa fa-search"></i> <span>止付維護 止付查詢</span>
					</div>
					<div class="no-move"></div>
				</div>

				<div class="box-content">
					<form id="searchFrom" class="form-horizontal bootstrap-validator-form" role="form" novalidate="novalidate">
						<div class="form-group">
							<table style="width: 100%">
								<tr>
									<td class="col-sm-2 control-label"><span class="s">* </span><label>止付類型 :</label></td>
									<td class="col-sm-3">
										<div>
											<select id="stopPayType">
											<option value="" selected>--請選擇--</option>
												<c:forEach items="${payLookUpList}" var="lookUp">
													<option value="${lookUp.LOOKUP_CODE}">${lookUp.LOOKUP_CODE_DESC}</option>
												</c:forEach>
											</select>
										</div>
									</td>
									<td class="col-sm-1"></td>
									<td class="col-sm-2 control-label"/>
									<td class="col-sm-3"/>
									<td class="col-sm-1"/>
								</tr>
								
								<tr class="highlight">
									<td class="col-sm-2 control-label"><span class="s">* </span><label>維運區 :</label></td>
									<td class="col-sm-3">
										<div>
											<select id="maintainArea">
											<option value="" selected>--請選擇--</option>
												<c:forEach items="${standardDomainList}" var="domain">
													<option value="${domain.ID}">${domain.NAME}</option>
												</c:forEach>
											</select>
										</div>
									</td>
									<td class="col-sm-1"></td>
									<td class="col-sm-2 control-label"><span class="s">* </span><label>請款單號 :</label></td>
									<td class="col-sm-3">
										<div>
											<select id="payNumber"></select>
										</div>
									</td>
									<td class="col-sm-1"/>
								</tr>
								
							</table>
						</div>
					</form>
				</div>
				
			</div>
		</div>
		
		<div id="ajaxSearchResult" class="col-xs-12">
			<div id="jQgrid">
				<table id="grid"></table>
				<div id="pager"></div>
			</div>
		</div>
		
		<div class="clearfix">&nbsp;</div>
		
		<div class="col-xs-12">
			<div class="box-header">
				<div class="box-name">
					<i class="fa fa-table"></i>租約站點清單
				</div>
				<div class="no-move"></div>
			</div>
			<table id="dataMenu" class="table table-bordered  table-hover table-heading table-datatable"	style="width:100%">
				<thead>
					<tr>
						<th>項次</th>
					    <th>站點編號</th>
						<th>起帳日</th>
						<th>終止日</th>
						<th>租金</th>
						<th>預付餘額</th>
						<th>類別</th>
						<th>請款期間起始</th>
						<th>請款期間結束</th>
						<th>本次請款</th>
					</tr>
				</thead>
				<tbody></tbody>
			</table>
		</div>
		
		<div class="clearfix">&nbsp;</div>
		
		<div class="col-xs-12">
			<div class="box-header">
				<div class="box-name">
					<i class="fa fa-table"></i>基站請款紀錄
				</div>
				<div class="no-move"></div>
			</div>
			<table id="recordMenu" class="table table-bordered  table-hover table-heading table-datatable"	style="width:100%">
				<thead>
					<tr>
						<th>出租人</th>
						<th>付款對象</th>
						<th>名稱</th>
						<th>付款方式</th>
						<th>租金</th>
						<th>本次請款</th>
						<th>未稅金額</th>
						<th>營業稅</th>
						<th>所得稅</th>
						<th>健保所得稅</th>
						<th>實付金額</th>
					</tr>
				</thead>
				<tbody></tbody>
			</table>
		</div>
	</div>
</body>
</html>