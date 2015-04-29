<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>


<!DOCTYPE html>
<html lang="en">
	<head>
		<title>申請Booking查詢</title>
	</head>
	<body>
		<c:if test="${cancelSystemBut == true}">
			<form id="searchFrom" class="form-horizontal bootstrap-validator-form" role="form" novalidate="novalidate">
				<input type="hidden" id="matNoMaterial" value="<c:out value="${mat_no}" />" />
				<input type="hidden" id="whId" value="<c:out value="${wh_id}" />" />
				<input type="hidden" id="domain" value="<c:out value="${domain}" />" />
			</form>
		</c:if>
		<c:if test="${cancelSystemBut == false}">
			<div class="row">
				<div id="breadcrumb" class="col-md-12">
					<ol class="breadcrumb">
						<%-- Button import (如給別頁面使用 c:import 會導致頁面導向錯誤，須避開) --%>
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
							<span>申請Booking查詢</span>
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
								區域 :
							</label>
							<div class="col-sm-2">
								<select id="domain" class="populate placeholder">
									<option value="">-- 請選擇 --</option>
									<c:forEach items="${sessionScope.inv024_orgDomainList}" var="var">
										<option value="${var.ID}">${var.NAME}</option>
									</c:forEach>
								</select>
							</div>
							<label class="col-sm-2 control-label">
								倉庫 :
							</label>
							<div class="col-sm-3">
								<select id="whId" class="populate placeholder">
									<option value="">-- 請選擇 --</option>
									<c:forEach items="${sessionScope.inv024_warehouseList}" var="var">
										<option value="${var.wh_id}">${var.wh_name}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">
								料號：
							</label>
							<div class="col-sm-3">
								<input type="text" id="matNoMaterial" class="form-control" placeholder="關鍵字查詢" />
							</div>
							<div class="col-sm-1" style="width: 1%;">
								<i id="matNoSearch" class="fa fa-search" style="cursor: pointer;"></i>
							</div>
						</div>
					</form>
				</div>
			</div>
		</c:if>

		<div class="row">
			<div id="ajaxSearchResult" class="col-xs-12">
				<!-- 固定id for window.resize start-->
				<div id="jQgrid">
					<table id="inv024_grid"></table>
					<div id="inv024_pager"></div>
					<br />
					<table id="inv024_grid2"></table>
					<div id="inv024_pager2"></div>
				</div>
				<!-- 固定id for window.resize end-->
			</div>
		</div>
		<div id="selectMaterialPage"></div>
	</body>
</html>
<script type="text/javascript">

	// 開啟下拉式選單搜尋文字功能
	function selectDefault() {
		$("#searchFrom").find("select").select2();
	}

	// 執行查詢
	function query() {
		var data = {
			mat_no : $.trim($("#matNoMaterial").val()), // 料號
			wh_id : $.trim($("#whId").val()), // 倉庫
			domain : $.trim($("#domain").val()), // 區域
			cancelSystemBut : "<c:out value="${element.cancelSystemBut}" />"
		};
		$("#inv024_grid2").jqGrid("clearGridData");
		$("#inv024_grid").setGridParam({datatype : "json",	postData : data, page:1}).trigger("reloadGrid");
	}

	// 設定查詢結果顯示內容
	function setMasterResults() {
		$("#inv024_grid").jqGrid({
			datatype : "local",
			pager : "#inv024_pager",
			url : CONTEXT_URL + "/inv/inv024/query",
		   	colNames:["倉庫代碼", "倉庫", "料號", "品名", "Booking數"],
		   	colModel:[
				{name:"wh_id", index:"wh_id", sortable:false, hidden:true},
		   		{name:"wh_name", index:"wh_name", sortable:false},
		   		{name:"mat_no", index:"mat_no", sortable:false},
		   		{name:"mat_name", index:"mat_name", sortable:false},
		   		{name:"booking_qty", index:"booking_qty", sortable:false}
			],	
		   	viewrecords : true,
		   	caption : "Booking主檔清單",
			rownumbers : true,
			multiselect : false,
		   	onSelectRow : function(id) {
		   		var ids = $("#inv024_grid").jqGrid("getGridParam", "selrow");
		   		var row = $("#inv024_grid").jqGrid("getRowData", ids);
		   		var data = {
		   			mat_no : row.mat_no, // 料號
		   			wh_id : row.wh_id // 倉庫代碼
	   			};
	   			$("#inv024_grid2").setGridParam({datatype : "json", postData : data, page:1}).trigger("reloadGrid");
		    },
		    gridComplete : function() {
		    	var ids = $("#inv024_grid").jqGrid("getDataIDs");
		    	$("#inv024_grid").jqGrid("setSelection", ids[0] ,true);
			},
			ondblClickRow : function(rowId) {
	        }
		});
	}

	// 設定查詢結果顯示內容
	function setDetailResults() {
		$("#inv024_grid2").jqGrid({
			datatype : "local",
			pager : "#inv024_pager2",
			url : CONTEXT_URL + "/inv/inv024/queryDetail",
		   	colNames:["Booking單號", "目前狀態", "Booking日期", "單位", "人員", "數量"],
		   	colModel:[
		   		{name:"act_no", index:"act_no", sortable:false},
		   		{name:"status_dscr", index:"status_dscr", sortable:false},
		   		{name:"cr_time", index:"cr_time", sortable:false},
		   		{name:"dept_name", index:"dept_name", sortable:false},
		   		{name:"cr_user_name", index:"cr_user_name", sortable:false},
		   		{name:"booking_qty", index:"booking_qty", sortable:false}
			],
		   	viewrecords : true,
		   	caption : "Booking明細清單",
			rownumbers : true,
			multiselect : false,
		   	onSelectRow : function(id) {
		    },
		    gridComplete : function() {
			},
			ondblClickRow : function(rowId) {
	        }
		});
	}

	// Load script of Select2 and run this	
	$(document).ready(function() {
		LoadSelect2Script("<s:url value="/resources/plugins/select2/select2.min.js" />", selectDefault);

		// 料號LOV
		$("#matNoSearch").click(function () {
			$("#matNoMaterial").val("");
			openMaterialFrame("selectMaterialPage"); // call common.js
		});

		// 查詢Button
		$("#btn_search").click(query);
		setMasterResults(); // 設定主檔查詢結果顯示內容
		setDetailResults(); // 設定明細查詢結果顯示內容

		// 重置Button
		$("#btn_reset").click(function(){
			$("#searchFrom").trigger("reset");
			selectDefault();
			$("#inv024_grid").jqGrid("clearGridData");
			$("#inv024_grid2").jqGrid("clearGridData");
		});

		// 查詢結果Table寬度控制
		$(window).resize(function() {
			$(window).unbind("onresize");
			$("#inv024_grid,.grid").setGridWidth($("#jQgrid").width() - 10);
			$("#inv024_grid2,.grid").setGridWidth($("#jQgrid").width() - 10);
			$(window).bind("onresize", this);
		}).trigger("resize");

		if ("<c:out value="${cancelSystemBut}" />" == "true") {
			query();
		}
	});
</script>