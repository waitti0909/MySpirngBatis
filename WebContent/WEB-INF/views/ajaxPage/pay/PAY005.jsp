<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<title>雜項請款作業</title>
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
							<i class="fa fa-search"></i>
							<span>雜項請款作業</span>
						</div>
						<div class="box-icons">
							<a class="collapse-link"> <i class="fa fa-chevron-up"></i> </a>
							<a class="expand-link"> <i class="fa fa-expand"></i> </a>
						</div>
						<div class="no-move"></div>
					</div>
				</div>
				<div class="box-content">
					<form id="searchFrom" class="form-horizontal bootstrap-validator-form" role="form" novalidate="novalidate">
						<div class="form-group">
							<label class="col-sm-2 control-label">
								<span class="s">*</span>維運區 :
							</label>
							<div class="col-sm-3">
								<select id="domainSelect" class="populate placeholder">
									<option value="">-- 請選擇 --</option>
									<c:forEach items="${domainList}" var="var">
										<option value="${var.ID}">${var.NAME}</option>
									</c:forEach>
								</select>
							</div>
							<label class="col-sm-2 control-label">
								請款單號：
							</label>
							<div class="col-sm-3">
								<input type="text" id="cashReqNo" class="form-control" placeholder="關鍵字查詢" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">
								<span class="s">*</span>申請起日：
							</label>
							<div class="col-sm-3">
								<input type="text" id="crStartDate" value="" readonly="readonly" class="form-control" placeholder="申請起日" />
							</div>
							<label class="col-sm-2 control-label">
								<span class="s">*</span>申請迄日：
							</label>
							<div class="col-sm-3">
								<input type="text" id="crEndDate" value="" readonly="readonly" class="form-control" placeholder="申請迄日" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">
								<span class="s">*</span> 申請狀態：
							</label>
							<div class="col-sm-6">
								<c:forEach items="${statusList}" var="var">
									<div class="checkbox-inline">
										<label>
											<input type="checkbox" name="status" value="${var.LOOKUP_CODE}" checked="checked" />${var.LOOKUP_CODE_DESC}<i class="fa fa-square-o"></i>
										</label>
									</div>
								</c:forEach>
							</div>
						</div>
					</form>
				</div>
			</div>
			<div id="ajaxSearchResult" class="col-xs-12">
				<!-- 固定id for window.resize start-->
				<div id="jQgrid">
					<table id="grid"></table>
					<div id="pager"></div>
				</div>
				<!-- 固定id for window.resize end-->
			</div>

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
	</div>
	</body>
</html>

<script type="text/javascript">
	var isDetail = false;
	// 開啟下拉式選單搜尋文字功能
	function selectDefault() {
		$("#searchFrom").find("select").select2();
		prepareDay();
	}

	// 查詢
	function query() {
		var status = $.trim($("input:checkbox:checked[name='status']").map(function() { return $(this).val(); }).get());
		var domain = $("#domainSelect").find("option:selected").val();
		if ((domain == null || domain == "")) {
			alert('維運區不得為空值');
			return false;
		}
		if (status == "") {
			alert("申請狀態至少需勾選一項");
			return false;
		}
		var data = {
			domain : $("#domainSelect").find("option:selected").val(), // 維運區
			cashReqNo : $("#cashReqNo").val(), // PO
			crStartDate : $("#crStartDate").val(), // 申請起日
			crEndDate : $("#crEndDate").val(), // 申請迄日
			status : status // 申請狀態
		};
		$("#grid").setGridParam({datatype : "json",	postData : data, page : 1}).trigger("reloadGrid");
		$("#signHistoryDiv").hide();
	}

	// 修改
	function update() {
		var ids = $("#grid").jqGrid("getGridParam", "selrow");
		if (ids == undefined) {
			alert("請選擇一筆資料編輯");
			return false;
		}
		var data = $("#grid").jqGrid("getRowData", ids);
		if(data["status"] != 'A'){
			alert("申請狀態為「待審核」的資料才可修改,請重新操作!");
			return false;
		}		
		editPage(data["cash_REQ_NO"],data["app_USER"],data["app_DATE"],data["year_MONTH"],data["appUserCnm"],data["domain"]);
	}

	// 設定查詢結果顯示內容
	function setResults() {
		$("#grid").jqGrid({
			datatype : "local",
			pager : "#pager",
			autowidth:'true',
			url : CONTEXT_URL + "/pay/pay005/search",
		   	colNames:["請款單號", "筆數", "憑證筆數", "申請狀態", "請款金額", "營業稅", "所得稅", "健保補充費", "應付金額"
		   	, "申請人", "申請日期", "審核人", "審核日期", '','','',''],
		   	colModel:[
		   		{name:"cash_REQ_NO", index:"cash_REQ_NO", sortable:false},
		   		{name:"reqCnt", index:"reqCnt", sortable:false},
		   		{name:"voucherCnt", index:"voucherCnt", sortable:false, hidden:true},
		   		{name:"statusDscr", index:"statusDscr", sortable:false},
		   		{name:"totalTaxEx", index:"totalTaxEx", sortable:false},
		   		{name:"totalBusinessEx", index:"totalBusinessEx", sortable:false},
		   		{name:"totalIncomeEx", index:"totalIncomeEx", sortable:false},
		   		{name:"totalNhiEx", index:"totalNhiEx", sortable:false},
		   		{name:"totalAmt", index:"totalAmt", sortable:false},
		   		{name:"appUserCnm", index:"appUserCnm", sortable:false},
		   		{name:"app_DATE", index:"app_DATE", sortable:false, formatter: function(cellvalue, options, rowObject){
								   			if (cellvalue != null && cellvalue != "") {
									   			var stDate = new Date(cellvalue);
												return stDate.getFullYear() + "/" + (stDate.getMonth() + 1) + "/" + stDate.getDate() 
												+ " " + addZero(stDate.getHours()) +":"+addZero(stDate.getMinutes());
								   			}else {
								   				return "";
								   			}}},
		   		{name:"accountUserCnm", index:"accountUserCnm", sortable:false},
		   		{name:"account_APPROVAL_DATE", index:"account_APPROVAL_DATE", sortable:false ,formatter: function(cellvalue, options, rowObject){
								   			if (cellvalue != null && cellvalue != "") {
									   			var stDate = new Date(cellvalue);
												return stDate.getFullYear() + "/" + (stDate.getMonth() + 1) + "/" + stDate.getDate() 
												+ " " + addZero(stDate.getHours()) +":"+addZero(stDate.getMinutes());
								   			}else {
								   				return "";
								   			}}},
		   		/* {name:"", index:"", sortable:false}, */
		   		{name:"year_MONTH", index:"year_MONTH", sortable:false,hidden:true},
		   		{name:"domain", index:"domain", sortable:false,hidden:true},
		   		{name:"app_USER", index:"app_USER", sortable:false,hidden:true},
		   		{name:"status", index:"status", sortable:false,hidden:true}
			],	
		   	caption : "雜項請款清單",
			rownumbers : true,
			multiselect : false,
		   	onSelectRow : function(id) {
		   		var rowData = jQuery(this).getRowData(id);
				getDetail(rowData);
		    },
		    gridComplete : function() {
			},
			ondblClickRow : function(rowId) {
	            /* var rowData = jQuery(this).getRowData(rowId);
				gridDtl(rowData.cash_REQ_NO);	 */	
	            
	        }
		});
	}

	function gridDtl(cashReqNo) {
		$.fancybox.open({
			href : CONTEXT_URL + "/pay/pay005/dtlPage",
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
			title : "明細資料",
			openEffect : 'elastic',
			closeEffect : 'fade',
			ajax : {
				data : {
					cashReqNo : cashReqNo
				},
				type : "POST",
				error : function(jqXHR, testStatus, errorThrown) {
					alert(jqXHR);
				}
			},
			afterShow : function() {
			},
			afterClose : function() {
			}
		});
	}
	function addZero(i) {
		if (i < 10) {
			i = "0" + i;
		}
		return i;
	}
	// 開啟新增頁面
	function editAddPage() {
		$.fancybox.open({
			href : CONTEXT_URL + "/pay/pay005/addPage",
			type : "ajax",
			width : $(window).width(),
			height : $(window).height(),
			autoSize : false,
			padding : [ 0, 0, 0, 0 ],
			scrolling : "yes",
			helpers : {
				overlay : {
					closeClick : false
				},
				title : {
					type : "float"
				}
			},
			title : "新增雜項請款",
			openEffect : "elastic",
			closeEffect : "fade",
			beforeClose : function() {
				if(!isDetail){
					if (!confirm("提醒：請確認資料是否存檔，關閉視窗？")) {
						return false;
					}
				}
			}
		});
	}
	// 開啟修改頁面
	function editPage(cashNo, appUser, appDate, yearMonth, appUserName,domain) {
		$.fancybox.open({
			href : CONTEXT_URL + "/pay/pay005/editPage",
			type : "ajax",
			width : $(window).width(),
			height : $(window).height(),
			autoSize : false,
			padding : [ 0, 0, 0, 0 ],
			scrolling : "yes",
			helpers : {
				overlay : {
					closeClick : false
				},
				title : {
					type : "float"
				}
			},
			ajax : {
				data : {
					cashNo : cashNo,
					appUser : appUser,
					appDate : appDate,
					yearMonth : yearMonth,
					appUserName : appUserName,
					domain : domain
				},
				type : "POST",
				error : function(jqXHR, testStatus, errorThrown) {
					alert(jqXHR);
				},
				async : false
			},
			title : "修改雜項請款",
			openEffect : "elastic",
			closeEffect : "fade",
			beforeClose : function() {
				if(!isDetail){
					if (!confirm("提醒：請確認資料是否存檔，關閉視窗？")) {
						return false;
					}
				}
			}
		});
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
	function prepareDay() {
		var Today = new Date();
		var TodayLess = (new Date());
		TodayLess.setDate(TodayLess.getDate() - 7);
		$('#crStartDate').prop(
				"value",
				TodayLess.getFullYear() + "/" + (TodayLess.getMonth() + 1)
						+ "/" + (TodayLess.getDate()));
		$('#crEndDate').prop(
				"value",
				Today.getFullYear() + "/" + (Today.getMonth() + 1) + "/"
						+ (Today.getDate()));

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
	// Load script of Select2 and run this	
	$(document)
			.ready(
					function() {
						LoadSelect2Script(
								"<s:url value="/resources/plugins/select2/select2.min.js" />",
								selectDefault);
						setResults();
						// 查詢Button
						$("#btn_search").click(query);
						 // 設定查詢結果顯示內容

						// 修改Button
						$("#btn_edit").click(update);

						// 新增Button
						$("#btn_add").click(function() {
							editAddPage();
						});

						// 重置Button
						$("#btn_reset").click(function() {
							$("#searchFrom").trigger("reset");
							selectDefault();
							$("#signHistoryDiv").hide();
						});
						//顯示明細			
						$('#btn_detail').click(function() {
							var id = $('#grid').jqGrid('getGridParam', 'selrow');
							if (id == null) {
								alert("請選擇一筆請款資料");
								return;
							}
							var payment = $("#grid").jqGrid('getRowData', id);
							gridDtl(payment.cash_REQ_NO);
						});
						// 查詢結果Table寬度控制
						$(window).resize(function() {
							$(window).unbind("onresize");
							$("#grid,.grid").setGridWidth(
							$("#jQgrid").width() - 10);
							$(window).bind("onresize", this);
						}).trigger("resize");
					});
</script>