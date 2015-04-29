<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>


<!DOCTYPE html>
<html lang="en">
<head>
<title>租/押金請款維護 租/押金請款查詢</title>
	<script type="text/javascript">	

	var isDetail = false;
		$.datepicker.setDefaults({
			dateFormat : "yy/mm/dd",
			changeYear : true,
			changeMonth : true,
			showButtonPanel : true,
			showTime : false});
		$(document).ready(function() {
			//扣押日起始日曆						
			$('#appDateStart').datepicker();
			//扣押日結束日曆
			$('#appDateEnd').datepicker();
			mountButEvent();
			LoadSelect2Script("<s:url value="/resources/plugins/select2/select2.min.js" />", selectDefault1);
			prepareDay();//扣款期間 預設日期
			mountGrid();
		});	
		
		//掛載表格
		function mountGrid(){
			$("#grid").jqGrid({
				datatype : "local",
				pager : '#pager0',
				url : CONTEXT_URL + "/pay/pay001/search/",
				colNames:['請款單號','筆數','憑證筆數','申請狀態','請款金額','營業稅','所得稅','健保補充費','應付金額','申請人','申請日期','審核人','審核日期','滯納金','','',''],
				colModel : [ 
					{name:'cash_REQ_NO',index:'cash_REQ_NO',width:120,sortable: false},
					{name:'reqCnt',index:'reqCnt', align : 'right',width:50,sortable: false},
					{name:'voucherCnt',index:'voucherCnt',width:50, sortable: false, align : 'right'},
					{name:'statusDscr',index:'statusDscr',width:60,sortable: false},
					{name:'sumTaxExclusiveTotalAmt',index:'sumTaxExclusiveTotalAmt', sortable: false,width:80, align : 'right'},
					{name:'sumTotalBusinessTax',index:'sumTotalBusinessTax', sortable: false,width:50, align : 'right'},
					{name:'sumTotalIncomeTax',index:'sumTotalIncomeTax', sortable: false,width:50, align : 'right'},
					{name:'sumTotalNhiAmt',index:'sumTotalNhiAmt', sortable: false,width:50, align : 'right'},
					{name:'amount_DUE',index:'amount_DUE', sortable: false,width:50, align : 'right'},
					{name:'appUserCnm',index:'appUserCnm', sortable: false,width:80},
					{name:'app_DATE',index:'app_DATE',width:75, align : 'center',sortable : false, formatter : jqGridDataFormat},
					{name:'accountApprovalUserCnm',index:'accountApprovalUserCnm',width:80, sortable: false},
					{name:'account_APPROVAL_DATE',index:'account_APPROVAL_DATE',width:75, align : 'center',sortable : false, formatter : jqGridDataFormat},
					{name:'sumPenalty',index:'sumPenalty', sortable: false,width:70, align : 'right'},
					{name:'status',index:'status', hidden:true},
					{name:'process_TYPE',index:'process_TYPE', hidden:true},
				    {name : 'attach_USER_ID',index : 'attach_USER_ID',sortable : false,hidden:true}],
				caption : "請款清單",
				rownumbers : true,
				onSelectRow : function(rowId) {//點一下帶出清單
					var rowData = jQuery(this).getRowData(rowId);
					getDetail(rowData);
				}
			});
			
			$(window).resize(function() {
				$(window).unbind("onresize");
				$("#grid,.grid").setGridWidth($("#jQgrid").width() - 10);
				$(window).bind("onresize", this);
			}).trigger('resize');	
		}
		function selectData() {
			var myStatus = [];
			$("input[type=checkbox][id=status]:checked").each(function(idx, object){
				myStatus.push($(this).val());
			});
			var data = {
					domainSelect : $("#domainSelect").val(),
					processType : $("#processType").val(),
					paymentPeriod : $("#paymentPeriod").val(),
					cashReqNo : $("#cashReqNo").val(),
					status : myStatus.toString(),
					appDateStart : $("#appDateStart").val(),
					appDateEnd : $("#appDateEnd").val()
				};
			$("#grid").setGridParam({datatype:"json", postData:data, page:1, size:10}).trigger("reloadGrid");
			$("#signHistoryDiv").hide();
		}
		function check() {
			if($("#paymentPeriod").val()!="" && !gIsDigit($("#paymentPeriod").val())){
				alert('付款週期 必需為數值');
				$("#paymentPeriod").focus();
				return false;
			};
			if($("#paymentPeriod").val().length>3){
				alert('付款週期 必需為小於三位數的數值');
				$("#paymentPeriod").focus();
				return false;
			};			
			if ($("#domainSelect").val() == "") {
				alert('維運區不得為空值');
				$("#domainSelect").focus();
				return false;
			};
			
			if ($("#processType").val() == "") {
				alert('處理類別不得為空值');
				$("#processType").focus();
				return false;
			};	
			if ($("#appDateStart").val() == "" ||$("#appDateEnd").val() == "" ) {
				alert('申請日期起迄不得為空值');
				if($("#appDateStart").val() == "")$("#appDateStart").focus();
				if($("#appDateEnd").val() == "")$("#appDateEnd").focus();
				return false;
			};	
			
			if(!dateCheck($("#appDateStart").val(), $("#appDateEnd").val(), "申請起迄")){
				return false;
			};
			if($("input[type=checkbox][id=status]:checked").length == 0){
				alert('至少勾選一種申請狀態');
				return false;
			};
			return true;
		}
		//掛載表格Event
		function mountButEvent() {
			//查詢
			$('#btn_search').click(function() {
				if(check()){
					selectData();
				}	
			});

			//新增
			$('#btn_add').click(function() {
				openPage("addPage", {});	
			});

			//修改			
			$('#btn_edit').click(function() {
				var id = $('#grid').jqGrid('getGridParam', 'selrow');
				if (id == null) {
					alert("必須點選一筆申請狀態為[待審核]的資料進行修改,請重新操作!");
					return;
				};
				var rowData = $("#grid").jqGrid('getRowData', id);				
				if(rowData.status != "A"){
					alert("申請狀態為[待審核]的資料才可修改,請重新操作!");
					return false;
				};
				var ajax = {
						data : {cashReqNo : rowData.cash_REQ_NO},
						type : "POST",
						error : function(jqXHR, testStatus, errorThrown) {
							alert(jqXHR);
						},
						async : false
				};
				openPage("editPage", ajax);		
			});
			//明細		
			$('#btn_detail').click(function() {
				var id = $('#grid').jqGrid('getGridParam', 'selrow');
				if (id == null) {
					alert("必須點選一筆資料,請重新操作!");
					return;
				};	
				var rowData = $("#grid").jqGrid('getRowData', id);			
	            var ajax = {
						data : {cashReqNo : rowData.cash_REQ_NO},
						type : "POST",
						error : function(jqXHR, testStatus, errorThrown) {
							alert(jqXHR);
						},
						async : false
				};
	            isDetail = true;
				openPage("searchDtl", ajax);	
			});
			//重置按鈕
			$('#btn_reset').click(
					function() {
						$("#searchFrom").trigger('reset');
						$("#grid").jqGrid().clearGridData();
						$("#docNo").prop("value", "");
						$("#domainSelect").select2("val", "");
						$("#processType").select2("val", "");
						prepareDay();
						$("#signHistoryDiv").hide();
					});

		}

		//=========Function=============
		// 開啟下拉式選單搜尋文字功能
		function selectDefault1() {
			$("#domainSelect").select2();
			$("#processType").select2();
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
		}
		function prepareDay() {
			var Today = new Date();
			var TodayLess = (new Date());
			TodayLess.setDate(TodayLess.getDate() - 7);
			$('#appDateStart').prop(
					"value",
					TodayLess.getFullYear() + "/" + (TodayLess.getMonth() + 1)
							+ "/" + (TodayLess.getDate()));
			$('#appDateEnd').prop(
					"value",
					Today.getFullYear() + "/" + (Today.getMonth() + 1) + "/"
							+ (Today.getDate()));
		}

		function getDetail(rowData) {
			$.ajax({
				url:CONTEXT_URL + "/commom/signHistory",
				type : 'POST',
				dataType : "html",
				data:{
					"processType" :  "PayRent",
					"applyNo" : rowData.cash_REQ_NO
				},
				async : false,
				success : function(data) {
					$("#signHistoryDiv").show();
					$("#signHistory").html(data);
				}
			});
		};
		function openPage(page, ajax){
			$.fancybox.open({
				href : CONTEXT_URL + "/pay/pay001/"+page,
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
				ajax : ajax,
				title : "租/押金請款",
				openEffect : 'elastic',
				closeEffect : 'fade',
				beforeClose : function() {
					if(!isDetail){
						if (!confirm("提醒：請確認資料是否存檔，關閉視窗？")) {
							return false;
						}
					}
				},
				afterClose : function() {
					selectData();
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

		<div class="col-xs-12 col-sm-12">
			<div class="box ui-draggable ui-droppable">
			
				<div class="box-header">
					<div class="box-name">
						<i class="fa fa-search"></i> <span>租/押金請款維護 租/押金請款查詢</span>
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
							<table style="width:100%">
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
									<td><label class="col-sm-12 control-label"> <span class="s">* </span>處理類別 :</label></td>
									<td>
										<div class="col-sm-6">
											<select id="processType" name="processType"  class="populate placeholder">
												<option value="" selected>--請選擇--</option>
												<c:forEach items="${processTypeSelect}" var="domain">
													<option value="${domain.LOOKUP_CODE}">${domain.LOOKUP_CODE_DESC}</option>
												</c:forEach>
											</select>
										</div>
									</td>
								</tr>
								<tr>
									<td><div class="clearfix">&nbsp;</div></td>
								</tr>
								<tr>
									<td><label class="col-sm-12 control-label">付款週期 : </label></td>
									<td>
										<div class="col-sm-6">
										<input id="paymentPeriod" type="text" value="" class="form-control" name="paymentPeriod">
										</div>
									</td>
									<td><label class="col-sm-12 control-label">請款單號 : </label></td>
									<td>
										<div class="col-sm-6">
										<input id="cashReqNo" type="text" value="" class="form-control" name="cashReqNo">
										</div>
									</td>
								</tr>
								<tr>
									<td><div class="clearfix">&nbsp;</div></td>
								</tr>
								<tr>
									<td><label class="col-sm-12 control-label"><span class="s">* </span>申請起日 : </label></td>
									<td>
										<div class="col-sm-6">
										<input id="appDateStart" type="text" value="" name="appDateStart" class="form-control" readonly="readonly" placeholder="點選輸入框">
										</div>
									</td>
									<td><label class="col-sm-12 control-label"><span class="s">* </span>申請迄日 : </label></td>
									<td>
										<div class="col-sm-6">
										<input id="appDateEnd" type="text" value="" name="appDateEnd"  class="form-control" readonly="readonly" placeholder="點選輸入框">
										</div>
									</td>
								</tr>
								<tr>
									<td><div class="clearfix">&nbsp;</div></td>
								</tr>
								<tr>
									<td><label class="col-sm-12 control-label"><span class="s">* </span>申請狀態 : </label></td>
									<td colspan="3">
										<div class="col-sm-12">
											<c:forEach items="${cashReqStatus}" var="domain">
												<div class="checkbox-inline">
													<label> 
													<input type="checkbox" value="${domain.LOOKUP_CODE}" id="status" name="status" checked>
													${domain.LOOKUP_CODE_DESC} <i class="fa fa-square-o"></i>
													</label>
												</div>											
											</c:forEach>
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
			<div id="jQgrid" align="center">
				<table id="grid"></table>
				<div id="pager0"></div>
			</div>
		</div>
		<br/>
		<div class="clearfix">&nbsp;</div>		
		<div class="col-xs-12" id="signHistoryDiv" style="display:none">
		<div class="box">
			<div class="box-header">
				<div class="box-name">
					<i class="fa fa-table"></i>
					<span>簽核紀錄</span>
				</div>				
			</div>
			<div class="box-content no-padding" id="signHistory">
			</div>
		</div>
	</div>
		
</body>
</html>