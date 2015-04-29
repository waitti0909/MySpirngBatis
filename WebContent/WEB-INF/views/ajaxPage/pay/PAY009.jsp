<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>憑證處理</title>
	<script type="text/javascript">	
		var isQuery = false;
		var isDetail = false;
		$(document).ready(function() {
			$('#btn_search').click(buttonSearch);
			$('#btn_add').click(buttonAdd);
			$('#btn_edit').click(buttonEdit);
			$('#btn_detail').click(buttonDetail);
			$('#btn_reset').click(buttonReset);
			$('#lessorId').change(lessorIdChange);
			LoadSelect2Script("<s:url value="/resources/plugins/select2/select2.min.js" />", selectDefault1);
			mountGrid();
		});	
		
		//掛載表格
		function mountGrid(){
			$("#grid").jqGrid({
				datatype : "local",
				pager : '#pager',
				autowidth:'true',
				url : CONTEXT_URL + "/pay/pay009/search/",
				colNames:['憑證處理單號','筆數','未稅金額','營業稅','總額','沖銷金額'],
				colModel : [ 
					{name:'voucher_NO',index:'voucher_NO',sortable: false},
					{name:'voucherCnt',index:'voucherCnt',sortable: false},
					{name:'sumTaxExclusiveAmt',index:'sumTaxExclusiveAmt',sortable: false, align : 'right'},
					{name:'sumBusinessTax',index:'sumBusinessTax', sortable: false, align : 'right'},
					{name:'totalAmt',index:'totalAmt', sortable: false, align : 'right'},
				    {name : 'sumCreditMadeAmt',index : 'sumCreditMadeAmt',sortable : false, align : 'right'}],
				caption : "憑證清單",
				rownumbers : true,
				onSelectRow : function(rowId) {//點一下帶出清單
					var rowData = jQuery(this).getRowData(rowId);
					var data = {
							voucherNo : rowData.voucher_NO
						};
					$("#gridDetail").setGridParam({datatype : "json",postData : data, page:1, rowNum:100000}).trigger("reloadGrid");
				}
			});
			$("#gridDetail").jqGrid({
				datatype : "local",
				autowidth:'true',
				url : CONTEXT_URL + "/pay/pay009/searchDetail",
				colNames:['憑證類別','憑證編號','憑證日期','統一編號'],
				colModel:[
					{name:'voucher_TYPE_DESC',index:'voucher_TYPE_DESC',width:80, sortable: false},
					{name:'voucher_NBR',index:'voucher_NBR',width:80, sortable: false},
					{name:'voucher_DATE',index:'voucher_DATE',width:120, sortable: false,formatter:'date', formatoptions:{newformat: "Y/m/d"}},
					{name:'vat_NUMBER',index:'vat_NUMBER', width:60,sortable: false}
				],	
				caption : "憑證明細表",
				rownumbers : true,
				gridComplete : function() {
								
				}
			});	
			$(window).resize(function() {
				$(window).unbind("onresize");
				$("#grid,.grid").setGridWidth($("#jQgrid").width() - 10);
				$("#gridDetail,.grid").setGridWidth($("#jQgrid").width() - 10);
				$(window).bind("onresize", this);
			}).trigger('resize');	
		};
		function check() {
			var isValid = true;
			
			if ($("#domainSelect").val() == "") {
				alert('維運區不得為空值');
				$("#domainSelect").focus();
				return false;
			};
			//檢查 YYYYMM
			if($("#yearMonth").val()!=""){
				if(!gIsDigit($("#yearMonth").val())) isValid = false;
				if(isValid && $("#yearMonth").val().length!=6 ) isValid = false;
				if(isValid && 
						!($("#yearMonth").val().substring(4,6)>="01" && $("#yearMonth").val().substring(4,6)<="12"))
					isValid = false;
				if(!isValid){
					alert('欄位  請符合格式 (YYYYMM)');
					$("#yearMonth").focus();
					return false;					
				};
			};
			return true;
		};		
	//=======click/change/blur 功能===============
		// 開啟下拉式選單搜尋文字功能
		function selectDefault1() {
			$("#domainSelect").select2();
		}
		//查詢
		function buttonSearch(){
			if(!check()){
				return false;
			};
			isQuery = true;
			var  data = {
					domainSelect : $("#domainSelect").val(),
					lessorId : $("#lessorId").val(),
					locationId : $("#locationId").val(),
					yearMonth : $("#yearMonth").val(),
					voucherNbr : $("#voucherNbr").val()
				};
			$("#grid").setGridParam({datatype:"json", postData:data, page:1, size:10}).trigger("reloadGrid");
			$("#gridDetail").jqGrid().clearGridData();
		};
		//新增
		function buttonAdd(){
			openPage("addPage", {});	
		};
		//修改
		function buttonEdit(){
			var id = $('#grid').jqGrid('getGridParam', 'selrow');
			if (id == null) {
				alert("必須點選一筆資料進行修改,請重新操作!");
				return;
			};
			var rowData = $('#grid').getRowData(id);
			var ajax = {
					data : {voucherNo : rowData.voucher_NO},
					type : "POST",
					error : function(jqXHR, testStatus, errorThrown) {
						alert(jqXHR);
					},
					async : false
			};
			openPage("editPage", ajax);		
		};
		function buttonReset(){
			$("#searchFrom").trigger('reset');
			$("#lessorName").html("");
			$("#grid").jqGrid().clearGridData();
			$("#gridDetail").jqGrid().clearGridData();
		};
		function buttonDetail(){
			var id = $('#grid').jqGrid('getGridParam', 'selrow');
			if (id == null) {
				alert("必須點選一筆資料,請重新操作!");
				return;
			};
			var rowData = $('#grid').getRowData(id);
	            var ajax = {
						data : {voucherNo : rowData.voucher_NO},
						type : "POST",
						error : function(jqXHR, testStatus, errorThrown) {
							alert(jqXHR);
						},
						async : false
				};
	            isDetail = true;
				openPage("searchDtl", ajax);	
		};
		function lessorIdChange(){
			if($("#lessorId").val() ==""){
				$("#lessorName").html("");
				return false;
			}
			processAjax("/ajax/pay/public/checkLessor", 
					{attachUserId : $("#lessorId").val()},
					false, false, 
					function(data){
						if (data.success) {
							if (data.rows.length > 0) {
								$("#lessorName").html(data.rows[0].lessor_NAME);
							}
						}else{
								$("#lessorId").val("");
								$("#lessorName").html("");
								alert("請輸入完整付款對象證號再進行查詢!！");
						};
					});		
			
		};
		
		//========共用=============
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
						if(msg != null && msg!=false)alert(msg);
						if(closeFancybox)parent.$.fancybox.close();				
					};
				},
				error:function(xhr, textStatus, errorThrown) { 
					isDetail = true;
				}
			});
		};
		function openPage(page, ajax){
			$.fancybox.open({
				href : CONTEXT_URL + "/pay/pay009/"+page,
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
				title : "憑證維護",
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
					isDetail = false;
					if(isQuery)buttonSearch();
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
						<i class="fa fa-search"></i> <span>憑證處理</span>
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
												<option value="">--請選擇--</option>
													<c:forEach items="${domainSelect}" var="domain">
														<option value="${domain.ID}">
														${domain.NAME}
														</option>
													</c:forEach>
											</select>
										</div>
									</td>
									<td></td>
									<td>
										
									</td>
								</tr>
								<tr>
									<td><div class="clearfix">&nbsp;</div></td>
								</tr>
								<tr>
									<td><label class="col-sm-12 control-label">付款對象 : </label></td>
									<td>
										<div class="col-sm-6">
										<input id="lessorId" type="text" value="" class="form-control" name="lessorId">										
										</div>
									</td>
									<td><label class="col-sm-12 control-label">付款對象名稱 : </label></td>
									<td><label class="control-label" id="lessorName"></label></td>
								</tr>
								<tr>
									<td><div class="clearfix">&nbsp;</div></td>
								</tr>
								<tr>
									<td><label class="col-sm-12 control-label">站點編號 : </label></td>
									<td>
										<div class="col-sm-6">
										<input id="locationId" type="text" value="" class="form-control" name="locationId">										
										</div>										
									</td>
									<td><label class="col-sm-12 control-label">請款年月 : </label></td>
									<td>
										<div class="col-sm-6">
										<input id="yearMonth" type="text" value="" class="form-control" placeholder="YYYYMM" name="yearMonth">
										</div>
									</td>
								</tr>
								<tr>
									<td><div class="clearfix">&nbsp;</div></td>
								</tr>
								<tr>
									<td><label class="col-sm-12 control-label">憑證號碼: </label></td>
									<td>
										<div class="col-sm-6">
										<input id="voucherNbr" type="text" value="" class="form-control" name="voucherNbr">
										</div>
									</td>
									<td></td>
									<td>
										
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
		<br/>
		<div class="clearfix">&nbsp;</div>
		<div id="ajaxSearchResult" class="col-xs-12">
			<div id="jQgridDetail" align="center">
				<table id="gridDetail"></table>
			</div>
		</div>
		<div id="selectSitePage"></div>
</body>
</html>