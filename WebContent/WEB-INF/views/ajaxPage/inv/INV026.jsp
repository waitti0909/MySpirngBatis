<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<!DOCTYPE html>
<html lang="en">
	<head>
		<title>資材歷程查詢作業</title>
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
							<span>資材歷程查詢作業</span>
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
								基站編號：
							</label>
							<div class="col-sm-2">
								<input id="btsSiteId" type="text" class="form-control" placeholder="請輸入完整的基站編號" />
							</div>
							<div class="col-sm-1">
								<i id="siteIdSearch" class="fa fa-search" style="cursor: pointer;"></i>
							</div>
							<label class="col-sm-2 control-label"><span class="s">*
							</span>異動日期：</label>
							<div class="col-sm-2">
								<input type="text" id="mdTimeS" name="md_time_s" value="<fmt:formatDate value="${md_time_s}" pattern="yyyy/MM/dd" />" readonly="readonly"
									class="form-control" placeholder="異動日期起值" />
							</div>
							<div class="col-sm-2">
								<input type="text" id="mdTimeE" name="md_time_e" value="<fmt:formatDate value="${md_time_e}" pattern="yyyy/MM/dd" />" readonly="readonly"
									class="form-control" placeholder="異動日期迄值" />
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
		</div>
		<div id="selectSitePage"></div>
	</body>
</html>
<script type="text/javascript">

	// 檢核查詢條件
	function validate() {
		var mdTimeS = $.trim($("#mdTimeS").val());
		var mdTimeE = $.trim($("#mdTimeE").val());
		if (mdTimeS == "" || mdTimeE == "") {
			alert("需輸入異動日期起迄");
			return false;
		}
		if(Date.parse(mdTimeS) > Date.parse(mdTimeE)) {
			alert("需輸入異動日期起值不可大於迄值");
			return false;
		}
		return true;
	}

	// 取得查詢結果筆數
	function getDataCount(maxRowNum) {
		var count = 0;
		$.ajax({
			url : CONTEXT_URL + "/inv/inv026/getDataCount",
			type : "POST",
			data : {
				max_row_num : maxRowNum,
				bts_site_id : $.trim($("#btsSiteId").val()), // 基站編號
				md_time_s : $.trim($("#mdTimeS").val()), // 異動日期(起)
				md_time_e : $.trim($("#mdTimeE").val()) // 異動日期(迄)
			},
			dataType : "json",
			async : false,
			success : function(data) {
				if (data.success) {
					if (data.maps != null) {
						count = parseFloat(data.maps.count);
					} 
				} else {
					alert(data.msg);
				}
			}
		});
		return count;
	}

	// 執行查詢
	function query() {
		if (validate()) {
			var count = getDataCount("1001");
			if (count > 1000) {
				alert("資料超過1000筆，請改按匯出EXCEL取得資料");
				return false;
			}
			var data = {};
			$("#grid").setGridParam({datatype : "json",	postData : data, page:1}).trigger("reloadGrid");
		}
	}

	// 匯出EXCEL
	function excel() {
		if (validate()) {
			var count = getDataCount("35001");
			if (count > 35000) {
				alert("資料超過35000筆，請增加查詢條件或縮小查詢範圍");
				return false;
			}
			window.location.href = CONTEXT_URL + "/inv/inv026/genExcel";
		}
	}

	// 設定查詢結果顯示內容
	function setResults() {
		$("#grid").jqGrid({
			datatype : "local",
			pager : "#pager",
			url : CONTEXT_URL + "/inv/inv026/query",
		   	colNames:["異動原因", "單號", "單位", "料號", "品名", "廠商序號", "貼標號碼", "數量", "日期", "人員", "站台"],
		   	colModel:[
		   		{name:"txn_type_name", index:"txn_type_name", sortable:false},
		   		{name:"txn_no", index:"txn_no", sortable:false},
		   		{name:"dept_name", index:"dept_name", sortable:false},
		   		{name:"mat_no", index:"mat_no", sortable:false},
		   		{name:"mat_name", index:"mat_name", sortable:false},
		   		{name:"ven_sn", index:"ven_sn", sortable:false},
		   		{name:"tag_no", index:"tag_no", sortable:false},
		   		{name:"qty", index:"qty", sortable:false},
		   		{name:"cr_time", index:"cr_time", sortable:false, formatter: "date", formatoptions:{srcformat: "Y/m/d H:i:s", newformat: "Y/m/d H:i"}},
		   		{name:"cr_user_name", index:"cr_user_name", sortable:false},
		   		{name:"bts_site_id", index:"bts_site_id", sortable:false}
			],	
		   	viewrecords : true,
		   	caption : "資材歷程",
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

	// 基站編號LOV Callback
	function receviceSiteData(data) {
		var json = JSON.parse(data);
		var btsSiteId = json.siteObjs[0].bts_SITE_ID;
		$("#btsSiteId").val(btsSiteId);
	}

	// Load script of Select2 and run this	
	$(document).ready(function() {
		// 異動日期日曆
		$("#mdTimeS").datepicker({
			dateFormat : "yy/mm/dd",
			changeYear : true,
			changeMonth : true,
			showButtonPanel : true
		});
		$("#mdTimeE").datepicker({
			dateFormat : "yy/mm/dd",
			changeYear : true,
			changeMonth : true,
			showButtonPanel : true
		});

		// 基站編號LOV
		$("#siteIdSearch").click(function () {
			$("#btsSiteId").val("");
			openSiteDialogFrame("selectSitePage", "receviceSiteData"); // call common.js
		});

		// 查詢Button
		$("#btn_search").click(query);
		setResults();
		// 匯出EXCEL
		$("#btn_to_excel").click(excel);
		// 重置Button
		$("#btn_reset").click(function(){
			$("#searchFrom").trigger("reset");
			selectDefault();
			$("#grid").jqGrid("clearGridData");
		});

		// 查詢結果Table寬度控制
		$(window).resize(function() {
			$(window).unbind("onresize");
			$("#grid,.grid").setGridWidth($("#jQgrid").width() - 10);
			$(window).bind("onresize", this);
		}).trigger("resize");
	});
</script>