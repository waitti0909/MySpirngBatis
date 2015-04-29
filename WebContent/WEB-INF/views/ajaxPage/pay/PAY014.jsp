<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>


<!DOCTYPE html>
<html lang="en">
<head>
<title>假扣押作業</title>
	<script type="text/javascript">		
		var isDetail = false;	
		$(document).ready(function() {
			mountButEvent();
			LoadSelect2Script("<s:url value="/resources/plugins/select2/select2.min.js" />", selectDefault1);
			prepareDay();//扣款期間 預設日期
			mountGrid();
		});			
		
		//掛載表格
		function mountGrid(){
			$("#grid").jqGrid({
				datatype : "local",
				pager : '#pager',
				autowidth:'true',
				url : CONTEXT_URL + "/pay/pay014/search/",
				colNames : [ '文案編號','扣押期間起', '扣押期間迄', '扣押對象', '扣押總額','說明','' ],
				colModel : [ 
					{name : 'document_NO',index : 'document_NO', align : 'center',sortable : false},
				    {name : 'attach_BEGIN_DATE',index : 'attach_BEGIN_DATE' , align : 'center',sortable : false,
				    	formatter: function(cellvalue, options, rowObject){
				   			if (cellvalue != null && cellvalue != "") {
					   			var stDate = new Date(cellvalue);
								return stDate.getFullYear() + "/" + (stDate.getMonth() + 1) + "/" + stDate.getDate() ;
				   			}else {
				   				return "";
				   			}
			   			}},
				    {name : 'attach_END_DATE',index : 'attach_END_DATE' , align : 'center',sortable : false,
				    	formatter: function(cellvalue, options, rowObject){
				   			if (cellvalue != null && cellvalue != "") {
					   			var stDate = new Date(cellvalue);
								return stDate.getFullYear() + "/" + (stDate.getMonth() + 1) + "/" + stDate.getDate();
				   			}else {
				   				return "";
				   			}
			   			}}, 
				    {name : 'attach_USER_NAME',index : 'attach_USER_NAME', align : 'center',sortable : false},
				    {name : 'attach_TAX_INCLUSIVE_TOTAL_AMT',index : 'attach_TAX_INCLUSIVE_TOTAL_AMT' , align : 'center',sortable : false}, 
				    {name : 'memo',index : 'memo' , align : 'center',sortable : false},
				    {name : 'attach_USER_ID',index : 'attach_USER_ID',sortable : false,hidden:true}],
				caption : "假扣押清單",
				rownumbers : true,
				onSelectRow : function(rowId) {//點一下帶出清單
					var rowData = jQuery(this).getRowData(rowId);
					getUser(rowData);
				},
				gridComplete : function() {},
				ondblClickRow: function(rowId) { //double clicking a jqgrid row
		            var rowData = jQuery(this).getRowData(rowId);
		            getDtl(rowData);
		        }
			});
			
			$("#dtlgrid").jqGrid({
				datatype : "local",
				pager : '#dtlpager',
				autowidth:'true',
				url : CONTEXT_URL + "/pay/pay014/searchUser/",
				colNames : [ '文案編號','付款對象', '名稱', '付款方式', '銀行','分行','帳號','扣押總額','付款比例','付款期間起','付款期間迄' ],
				colModel : [ 
					{name : 'document_NO',index : 'document_NO', align : 'center',sortable : false},
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
	   			 	{name : 'payment_END_DATE',index : 'payment_END_DATE', align : 'center',sortable : false,
				    	formatter: function(cellvalue, options, rowObject){
				   			if (cellvalue != null && cellvalue != "") {
					   			var stDate = new Date(cellvalue);
								return stDate.getFullYear() + "/" + (stDate.getMonth() + 1) + "/" + stDate.getDate();
				   			}else {
				   				return "";
				   			}
			   			}}],
				caption : "付款對象明細表",
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
		
		function selectData() {
			var data = {
				domain : $("#domainSelect").find("option:selected").val(),
				docNo : $("#docNo").val(),
				attachStartDate : $("#attachStartDate").val(),
				attachEndDate : $("#attachEndDate").val(),
				attachUserId : "" //暫時為空
			};
			$("#grid").setGridParam({datatype:"json", postData:data,page:1}).trigger("reloadGrid");
			$("#dtlgrid").jqGrid().clearGridData();
		}
		function check() {
			var domain = $("#domainSelect").find("option:selected").val();
			var docNo = $("#docNo").val();
			var attachStartDate = $("#attachStartDate").val();
			var attachEndDate = $("#attachEndDate").val();
			if ((domain == null || domain == "")) {
				alert('維運區不得為空值');
				return false;
			}
			/* if ((docNo == null || docNo == "")) {
				alert('文案編號不得為空值');
				return false;
			} */
			if ((attachStartDate == null || attachStartDate == "")) {
				alert('扣押期間開始日不得為空值');
				return false;
			}
			if ((attachEndDate == null || attachEndDate == "")) {
				alert('扣押期間結束日不得為空值');
				return false;
			}
			dateCheck(attachStartDate, attachEndDate, "扣押期間");
			
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
			//顯示明細			
			$('#btn_detail').click(function() {
				var id = $('#grid').jqGrid('getGridParam', 'selrow');
				if (id == null) {
					alert("請選擇一筆假扣押資料");
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
					alert("請選擇一筆假扣押資料");
					return;
				}	
				openEditPage(payment);			
			});

			//重置按鈕
			$('#btn_reset').click(
					function() {
						$("#searchFrom").trigger('reset');
						$("#grid").jqGrid().clearGridData();
						$("#dtlgrid").jqGrid().clearGridData();
						$("#docNo").prop("value", "");
						$("#domainSelect").select2("val", "");
						prepareDay();
					});

		}

		//=========Function=============
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
			$('#attachStartDate').prop(
					"value",
					TodayLess.getFullYear() + "/" + (TodayLess.getMonth() + 1)
							+ "/" + (TodayLess.getDate()));
			$('#attachEndDate').prop(
					"value",
					Today.getFullYear() + "/" + (Today.getMonth() + 1) + "/"
							+ (Today.getDate()));
			//扣押日起始日曆						
			$('#attachStartDate').datepicker({
				dateFormat : "yy/mm/dd",
				changeYear : true,
				changeMonth : true,
				showButtonPanel : true,
				showTime : false
			});
			//扣押日結束日曆
			$('#attachEndDate').datepicker({
				dateFormat : "yy/mm/dd",
				changeYear : true,
				changeMonth : true,
				showButtonPanel : true,
				showTime : false
			});
		}

		function getUser(rowData) {
			var data = {
				docNo : rowData.document_NO
			};
			$("#dtlgrid").setGridParam({
				datatype : "json",
				postData : data,
				mtype : 'POST',
				page : 1
			}).jqGrid().trigger("reloadGrid");
		}

		function getDtl(rowData) {
			$.fancybox.open({
				href : CONTEXT_URL + "/pay/pay014/searchDtl",
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
						docNo : rowData.document_NO
					},
					type : "POST",
					error : function(jqXHR, testStatus, errorThrown) {
						alert(jqXHR);
					},
					async : false
				},
				title : "假扣押明細",
				openEffect : 'elastic',
				closeEffect : 'fade',
				afterShow : function() {
				},
				afterClose : function() {
				}
			});
		}
		function openAddPage(){
			$.fancybox.open({
					href : CONTEXT_URL + "/pay/pay014/addPage",
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
					title : "假扣押功能",
					openEffect : 'elastic',
					closeEffect : 'fade',
					afterClose : function() {},
					beforeClose : function() {
						if(!isDetail){
							if (!confirm("提醒：請確認資料是否存檔，關閉視窗？")) {
								return false;
							}				
						}		
					}	
				});		
		}
		function openEditPage(rowData){
			$.fancybox.open({
					href : CONTEXT_URL + "/pay/pay014/editPage",
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
							data : {'docNo':rowData.document_NO},
							type : "POST",
							error : function(jqXHR, testStatus, errorThrown) {
								alert(jqXHR);
							},
							async : false
					},
					title : "假扣押功能",
					openEffect : 'elastic',
					closeEffect : 'fade',
					afterClose : function() {
						selectData();
					},
					beforeClose : function() {
						if(!isDetail){
							if (!confirm("提醒：請確認資料是否存檔，關閉視窗？")) {
								return false;
							}
						}
					}
				});		
		}
		
		$("#attachUserId").on('blur',function() {
			if($("#attachUserId").val() ==""){
				$("#attatchName").html("");
				return false;
			}
			data ={attachUserId : $("#attachUserId").val()};
			$.ajax({
				url : CONTEXT_URL + "/ajax/pay/public/checkLessor",
				data : data,
				type : "POST",
				dataType : 'json',
				async:false,
				success : function(data) {
					if (data.rows.length > 0) {
						$("#attatchName").html(data.rows[0].lessor_NAME);
					}else{
						$("#attachUserId").val("");
						$("#attatchName").html("");
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
						<i class="fa fa-search"></i> <span>假扣押作業功能 查詢條件</span>
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
									<td><label class="col-sm-12 control-label"> 文案編號 :</label></td>
									<td>
										<div class="col-sm-6">
											<input id="docNo" type="text" value="" class="form-control" name="docNo" >
										</div>
									</td>
								</tr>
								<tr>
									<td><div class="clearfix">&nbsp;</div></td>
								</tr>
								<tr>
									<td><label class="col-sm-12 control-label"> <span class="s">* </span>扣押期間 :</label></td>
									<td>
										<div class="col-sm-3">
											<input id="attachStartDate" type="text" value=""
												name="attachStartDate" readonly="readonly"
												class="form-control">
										</div>
										<div class="col-sm-1">至</div>
										<div class="col-sm-3">
											<input id="attachEndDate" type="text" value=""
												name="attachEndDate" readonly="readonly"
												class="form-control">
										</div>
									</td>
								</tr>
								<tr>
									<td><div class="clearfix">&nbsp;</div></td>
								</tr>
								<tr>
									<td><label class="col-sm-12 control-label">扣押對象 : </label></td>
									<td>
										<div class="col-sm-6">
										<input id="attachUserId" type="text" value="" class="form-control" name="attachUserId">
										
										</div>
									</td>
									<td>
										<div class="col-sm-12">
										<label class="col-sm-12 control-label" id="attatchName"></label>
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
				<div id="pager"></div>
			</div>
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
</body>
</html>