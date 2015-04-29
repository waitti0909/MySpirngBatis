<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>


<!DOCTYPE html>
<html lang="en">
<head>
<title>支票作廢申請作業</title>
	<script type="text/javascript">	
		var isDetail = false;		
		$(document).ready(function() {
			LoadSelect2Script("<s:url value="/resources/plugins/select2/select2.min.js" />", selectDefault1);
			prepareDay();//扣款期間 預設日期
			mountGrid();
		});	
		mountButEvent();
		//掛載表格
		function mountGrid(){
			$("#grid").jqGrid({
				datatype : "local",
				pager : '#pager',
				autowidth:'true',
				url : CONTEXT_URL + "/pay/pay010/search/",
				colNames : [ '作廢清單','筆數', '申請狀態', '應付金額', '申請人','申請日期','審核人','審核日期','','','' ],
				colModel : [ 
					{name : 'disregard_NO',index : 'disregard_NO', align : 'center',sortable : false},
				    {name : 'disregardCount',index : 'disregardCount' , align : 'center',sortable : false,formatter: nullForZero},
				    {name : 'statusDscr',index : 'statusDscr' , align : 'center',sortable : false}, 
				    {name : 'check_AMT',index : 'check_AMT', align : 'center',sortable : false,formatter: nullForZero},
				    {name : 'appUserName',index : 'appUserName' , align : 'center',sortable : false}, 
				    {name : 'app_DATE',index : 'app_DATE' , align : 'center',sortable : false,formatter: function(cellvalue, options, rowObject){
			   			if (cellvalue != null && cellvalue != "") {
				   			var stDate = new Date(cellvalue);
							return stDate.getFullYear() + "/" + (stDate.getMonth() + 1) + "/" + stDate.getDate() ;
			   			}else {
			   				return "";
			   			}}},
				    {name : 'approveUserName',index : 'approvalUserName',align : 'center',sortable : false},
				    {name : 'approveDate',index : 'approveDate',sortable : false,align : 'center',formatter: function(cellvalue, options, rowObject){
			   			if (cellvalue != null && cellvalue != "") {
				   			var stDate = new Date(cellvalue);
							return stDate.getFullYear() + "/" + (stDate.getMonth() + 1) + "/" + stDate.getDate() ;
			   			}else {
			   				return "";
			   			}}},
			   		{name : 'domain',index : 'domain',sortable : false,hidden:true},
			   		{name : 'status',index : 'status',sortable : false,hidden:true},
			   		{name : 'app_USER',index : 'app_USER',sortable : false,hidden:true}],
				caption : "支票作廢清單",
				rownumbers : true,
				onSelectRow : function(id) {
			   		var rowData = jQuery(this).getRowData(id);
					getDetail(rowData);
		    	},
				ondblClickRow: function(rowId) { //double clicking a jqgrid row
		        },
				gridComplete : function() {}
			});
			
			$(window).resize(function() {
				$(window).unbind("onresize");
				$("#grid,.grid").setGridWidth($("#jQgrid").width() - 10);
				$(window).bind("onresize", this);
			}).trigger('resize');	
		}
		function getDetail(rowData) {
			$.ajax({
				url:CONTEXT_URL + "/commom/signHistory",
				type : 'POST',
				dataType : "html",
				data:{
					"processType" :  rowData.process_TYPE,
					"applyNo" : rowData.cash_REQ_NO
				},
				async : false,
				success : function(data) {
					$("#signHistoryDiv").show();
					$("#signHistory").html(data);
				}
			});
		};
		function selectData() {
			var status = JQClick() ;
			
			var data = {
				domain : $("#domainSelect").find("option:selected").val(),
				disregardNo : $("#disregardNo").val(),
				appStartDate : $("#appStartDate").val(),
				appEndDate : $("#appEndDate").val(),
				crStartDate : $("#crStartDate").val(),
				crEndDate : $("#crEndDate").val(),
				status : status
			};
			$("#grid").setGridParam({datatype:"json", postData:data,page:1}).trigger("reloadGrid");
			$("#signHistoryDiv").hide();
		}
		function check() {
			var domain = $("#domainSelect").find("option:selected").val();
			var disregardNo = $("#disregardNo").val();
			var appStartDate = $("#appStartDate").val();
			var appEndDate = $("#appEndDate").val();
			var crStartDate = $("#crStartDate").val();
			var crEndDate = $("#crEndDate").val();
			 if ((domain == null || domain == "")) {
				alert('維運區不得為空值');
				return false;
			}
			
			/* if ((disregardNo == null || disregardNo == "")) {
				alert('作廢單號不得為空值');
				return false;
			} */
			if ((appStartDate == null || appStartDate == "")) {
				alert('申請開始日不得為空值');
				return false;
			}
			if ((appEndDate == null || appEndDate == "")) {
				alert('申請結束日不得為空值');
				return false;
			}
			dateCheck(appStartDate, appEndDate, "申請日");
			if ((crStartDate == null || crStartDate == "")) {
				alert('請款開始日不得為空值');
				return false;
			}
			if ((crEndDate == null || crEndDate == "")) {
				alert('請款結束日不得為空值');
				return false;
			}
			dateCheck(crStartDate, crEndDate, "請款日"); 
			if(!JQCheck()){
				return false;
			}
			return true;
		}		
		
		function JQClick() {
			var str = "";
			$("input[name=statusCheck]:checked").each(function(i) {
				str = str + $(this).val() + ",";
			})
			//將字串最後一個逗號去除
			if (str.length > 0)
				str = str.substr(0, str.length - 1);
			return str;				
		}
		
		function JQCheck() {
			var str = "";
			$("input[name=statusCheck]:checked").each(function(i) {
				str = str + $(this).val() + ",";
			})			
			if (str.length == 0){
				alert("請勾選申請狀態");
				return false;	
			}
			return true;				
		}
		//勾選所有狀態
		function checkFalse() {
			$("input[name=statusCheck]:checked").each(function(i) {
				$(this).prop("checked",true);
			})
		}
		//掛載表格Event
		function mountButEvent() {
			//查詢
			$('#btn_search').click(function() {
				if (check()) {
					selectData();
				}
			});
			//顯示明細			
			$('#btn_detail').click(function() {
				var id = $('#grid').jqGrid('getGridParam', 'selrow');
				if (id == null) {
					alert("請選擇一筆支票作廢資料");
					return;
				}
				var payment = $("#grid").jqGrid('getRowData', id);
				getDtl(payment);
			});
			//新增
			$('#btn_add').click(function() {
				openAddPage();
			});

			//修改			
			$('#btn_edit').click(function() {
				var id = $('#grid').jqGrid('getGridParam', 'selrow');
				var payment = $("#grid").jqGrid('getRowData', id);
				if (id == null) {
					alert("請選擇一筆支票作廢資料");
					return;
				}
				if(payment.status != "A"){
					alert("非待審中資料不得修改");
					return;
				}
				openEditPage(payment);
			});

			//重置按鈕
			$('#btn_reset').click(function() {
				$("#searchFrom").trigger('reset');
				$("#grid").jqGrid().clearGridData();
				$("#disregardNo").prop("value", "");
				$("#domainSelect").select2("val", "");
				prepareDay();
				checkFalse();
				$("#signHistoryDiv").hide();
			});
		}

		//=========Function=============		
		function nullForZero(cellvalue, options, rowObject) {
			var returnValue = cellvalue;
			if (cellvalue == "" || cellvalue == "undefined"
					|| cellvalue == null) {
				returnValue = "0";
			}
			return returnValue;
		}
		// 開啟下拉式選單搜尋文字功能
		function selectDefault1() {
			$("#domainSelect").select2();
		}
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
			$('#appStartDate').prop(
					"value",
					TodayLess.getFullYear() + "/" + (TodayLess.getMonth() + 1)
							+ "/" + (TodayLess.getDate()));
			$('#appEndDate').prop(
					"value",
					Today.getFullYear() + "/" + (Today.getMonth() + 1) + "/"
							+ (Today.getDate()));
			$('#crStartDate').prop(
					"value",
					TodayLess.getFullYear() + "/" + (TodayLess.getMonth() + 1)
							+ "/" + (TodayLess.getDate()));
			$('#crEndDate').prop(
					"value",
					Today.getFullYear() + "/" + (Today.getMonth() + 1) + "/"
							+ (Today.getDate()));
			//扣押日起始日曆						
			$('#appStartDate').datepicker({
				dateFormat : "yy/mm/dd",
				changeYear : true,
				changeMonth : true,
				showButtonPanel : true,
				showTime : false
			});
			//扣押日結束日曆
			$('#appEndDate').datepicker({
				dateFormat : "yy/mm/dd",
				changeYear : true,
				changeMonth : true,
				showButtonPanel : true,
				showTime : false
			});
			//扣押日起始日曆						
			$('#crStartDate').datepicker({
				dateFormat : "yy/mm/dd",
				changeYear : true,
				changeMonth : true,
				showButtonPanel : true,
				showTime : false
			});
			//扣押日結束日曆
			$('#crEndDate').datepicker({
				dateFormat : "yy/mm/dd",
				changeYear : true,
				changeMonth : true,
				showButtonPanel : true,
				showTime : false
			});
		}

		function getDtl(rowData) {
			$.fancybox.open({
				href : CONTEXT_URL + "/pay/pay010/searchDtl",
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
						disregardNo : rowData.disregard_NO,
						appUserName : rowData.appUserName,
						appDate : rowData.app_DATE,
						domain : rowData.domain
					},
					type : "POST",
					error : function(jqXHR, testStatus, errorThrown) {
						alert(jqXHR);
					},
					async : false
				},
				title : "支票作廢明細",
				openEffect : 'elastic',
				closeEffect : 'fade',
				afterShow : function() {
				},
				afterClose : function() {
				}
			});
		}
		function openAddPage() {
			$.fancybox.open({
				href : CONTEXT_URL + "/pay/pay010/addPage",
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
				title : "支票作廢功能",
				openEffect : 'elastic',
				closeEffect : 'fade',
				afterClose : function() {
					selectData();
				},
				beforeClose : function() {
					if(!isDetail){
						if (!confirm("提醒：請確認資料是否存檔，關閉視窗？")) {
							selectData();
							return false;
						}
					}
				}
			});
		}
		function openEditPage(rowData) {
			$.fancybox.open({
				href : CONTEXT_URL + "/pay/pay010/editPage",
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
						disregardNo : rowData.disregard_NO,
						appUserName : rowData.appUserName,
						appDate : rowData.app_DATE,
						domain : rowData.domain,
						appUser : rowData.app_USER
					},
					type : "POST",
					error : function(jqXHR, testStatus, errorThrown) {
						alert(jqXHR);
					},
					async : false
				},
				title : "支票作廢功能",
				openEffect : 'elastic',
				closeEffect : 'fade',
				afterClose : function() {
					selectData();
				},
				beforeClose : function() {
					if(!isDetail){
						if (!confirm("提醒：請確認資料是否存檔，關閉視窗？")) {
							selectData();
							return false;
						}
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
						<i class="fa fa-search"></i> <span>支票作廢申請作業功能 查詢條件</span>
					</div>
					<div class="box-icons">
						<a class="collapse-link"> <i class="fa fa-chevron-up"></i></a> 
						<a class="expand-link"> <i class="fa fa-expand"></i></a>
					</div>
					<div class="no-move"></div>
				</div>
				
				<div class="box-content">
					<form id="searchFrom" class="form-horizontal bootstrap-validator-form" role="form" novalidate="novalidate">
						<table style="width:100%">
						<div class="form-group">
								<tr>
									<td><label class="col-sm-12"><span class="s">* </span>維運區 :</label></td>
									<td>
										<div class="col-sm-6">
												<select id="domainSelect" name="domainSelect" >
												<option value="" selected>--請選擇--</option>
												<c:forEach items="${domainSelect}" var="domain">
													<option value="${domain.ID}">${domain.NAME}</option>
												</c:forEach>
											</select>
										</div>
									</td>
									<td><label class="col-sm-12">作廢單號 :</label></td>
									<td>
										<div class="col-sm-12">
											<input id="disregardNo" type="text" value="" class="form-control" name="disregardNo" >
										</div>
									</td>
								</tr>
								<tr>
									<td><div class="clearfix">&nbsp;</div></td>
								</tr>
								<tr>
									<td><label class="col-sm-12"><span class="s">* </span>申請起日:</label></td>
									<td>
										<div class="col-sm-6">
											<input id="appStartDate" type="text" value=""  class="form-control"
												name="appStartDate" readonly="readonly">
										</div>
									</td>
									<td><label class="col-sm-12"><span class="s">* </span>申請迄日:</label></td>
									<td>
										<div class="col-sm-12">
											<input id="appEndDate"  class="form-control"
												type="text" value="" name="appEndDate" readonly="readonly">
										</div>
									</td>
								</tr>
								<tr>
									<td><div class="clearfix">&nbsp;</div></td>
								</tr>
								<tr>
									<td><label class="col-sm-12"><span class="s">* </span>請款起日:</label></td>
									<td>
										<div class="col-sm-6">
											<input id="crStartDate" type="text" value=""  class="form-control"
												name="crStartDate" readonly="readonly">
										</div>
									</td>
									<td><label class="col-sm-12"><span class="s">* </span>請款迄日:</label></td>
									<td>
										<div class="col-sm-12">
											<input id="crEndDate"  class="form-control"
												type="text" value="" name="crEndDate" readonly="readonly">
										</div>
									</td>
								</tr>
								<tr>
									<td><div class="clearfix">&nbsp;</div></td>
								</tr>
								<tr>
									<td><label class="col-sm-12"> <span class="s">* </span> 申請狀態 : </label></td>
									<td>
										<div class="col-sm-12">
												
												<c:forEach items="${cashReqStatus}" var="cashReq">
												<div class="checkbox-inline">
													<label> 
													<input type="checkbox" value="${cashReq.LOOKUP_CODE}" id="statusCheck" name="statusCheck" checked>
													${cashReq.LOOKUP_CODE_DESC} <i class="fa fa-square-o"></i>
													</label>
												</div>											
											</c:forEach>
										</div>
									</td>
								</tr>
						</div>
						</table>
					</form>
				</div>
			</div>
		</div>
		
		<div id="ajaxSearchResult" class="col-xs-12">
			<div id="jQgrid" align="center">
				<table id="grid"></table>
				<div id="pager"></div>
			</div>
		</div>
		<tr>
			<td><div class="clearfix">&nbsp;</div></td>
		</tr>
		<table style="width:100%">
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
		</table>
		</body>
</html>