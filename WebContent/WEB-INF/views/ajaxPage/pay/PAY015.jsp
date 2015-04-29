<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<title>工程驗收複驗查詢</title>
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
							<span>工程驗收複驗查詢</span>
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
								維運區 :
							</label>
							<div class="col-sm-3">
								<select id="domainSelect" class="populate placeholder">
									<option value="">-- 請選擇 --</option>
									<c:forEach items="${sessionScope.pay015_orgDomainList}" var="var">
										<option value="${var.ID}">${var.NAME}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">
								業者 :
							</label>
							<div class="col-sm-3">
								<select id="coVatNoSelect" class="populate placeholder">
									<option value="">-- 請選擇 --</option>
									<c:forEach items="${sessionScope.pay015_companyList}" var="var">
										<option value="${var.CO_VAT_NO}" >${var.CO_NAME}</option>
									</c:forEach>
								</select>
							</div>
							<label class="col-sm-2 control-label">
								PO：
							</label>
							<div class="col-sm-3">
								<input type="text" id="poNo" class="form-control" placeholder="關鍵字查詢" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">
								驗收起日：
							</label>
							<div class="col-sm-3">
								<input type="text" id="apDateS" value="<fmt:formatDate value="${ap_date_s}" pattern="yyyy/MM/dd" />" readonly="readonly" class="form-control" placeholder="驗收起日" />
							</div>
							<label class="col-sm-2 control-label">
								驗收迄日：
							</label>
							<div class="col-sm-3">
								<input type="text" id="apDateE" value="<fmt:formatDate value="${ap_date_e}" pattern="yyyy/MM/dd" />" readonly="readonly" class="form-control" placeholder="驗收迄日" />
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
					<br />
					<table id="grid2"></table>
					<div id="pager2"></div>
				</div>
				<!-- 固定id for window.resize end-->
			</div>
		</div>
	</body>
</html>
<script type="text/javascript">

	// 開啟下拉式選單搜尋文字功能
	function selectDefault() {
		$("#searchFrom").find("select").select2();
	}

	// 設定查詢結果顯示內容
	function setMasterResults() {
		$("#grid").jqGrid({
			datatype : "local",
			pager : "#pager",
			url : CONTEXT_URL + "/pay/pay015/querySiteOutsourcing",
		   	colNames:["", "業者", "PO", "工單", "名稱", "派工單號", "站台", "驗收日", "總額", "未稅金額", "營業額"],
		   	colModel:[
				{name:"po_id".cf(), index:"po_id".cf(), sortable:false, hidden:true},
		   		{name:"co_name".cf(), index:"co_name".cf(), sortable:false},
		   		{name:"po_no".cf(), index:"po_no".cf(), sortable:false},
		   		{name:"order_id".cf(), index:"order_id".cf(), sortable:false, width:160},
		   		{name:"order_desc".cf(), index:"order_desc".cf(), sortable:false},
		   		{name:"os_id".cf(), index:"os_id".cf(), sortable:false},
		   		{name:"site_name".cf(), index:"site_name".cf(), sortable:false},
		   		{name:"ap_date".cf(), index:"ap_date".cf(), sortable:false, formatter: "date", formatoptions:{newformat: "Y/m/d"}},
		   		{name:"ap_amt".cf(), index:"ap_amt".cf(), sortable:false},
		   		{name:"tax_exclusive_amt".cf(), index:"tax_exclusive_amt".cf(), sortable:false},
		   		{name:"business_tax".cf(), index:"business_tax".cf(), sortable:false}
			],	
		   	viewrecords : true,
		   	caption : "工程驗收清單 - 複驗",
			rownumbers : true,
			multiselect : true,
		   	onSelectRow : function(id) {
		   		var ids = $("#grid").jqGrid("getGridParam", "selrow");
		   		if (ids == undefined) {
		   			$("#grid2").jqGrid("clearGridData");
		   			return;
		   		}
		   		var row = $("#grid").jqGrid("getRowData", ids);
		   		var data = {
	   				os_id : $.trim(row["os_id".cf()]),
	   				po_id : $.trim(row["po_id".cf()])
	   			};
	   			$("#grid2").setGridParam({datatype:"json", postData:data, page:1}).trigger("reloadGrid");
		    },
		    gridComplete : function() {
			},
			ondblClickRow : function(rowId) {
	        }
		});
	}

	// 設定查詢結果顯示內容
	function setDetailResults() {
		$("#grid2").jqGrid({
			datatype : "local",
			pager : "#pager2",
			url : CONTEXT_URL + "/pay/pay015/querySiteOsItem",
		   	colNames:["工項", "名稱", "單價", "申請數量", "驗收數量", "請款金額", "未稅金額", "營業稅"],
		   	colModel:[
				{name:"item_id".cf(), index:"item_id".cf(), sortable:false},
				{name:"item_name".cf(), index:"item_name".cf(), sortable:false},
				{name:"price".cf(), index:"price".cf(), sortable:false},
				{name:"number".cf(), index:"number".cf(), sortable:false},
				{name:"ap_number".cf(), index:"ap_number".cf(), sortable:false},
				{name:"ap_amt".cf(), index:"ap_amt".cf(), sortable:false},
				{name:"tax_exclusive_amt".cf(), index:"tax_exclusive_amt".cf(), sortable:false},
				{name:"business_tax".cf(), index:"business_tax".cf(), sortable:false}
			],
		   	viewrecords : true,
		   	caption : "工單資訊",
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

	// 查詢
	function query() {
		var data = {
			domain : $.trim($("#domainSelect").val()), // 維運區
			co_vat_no : $.trim($("#coVatNoSelect").val()), // 業者
			po_no : $.trim($("#poNo").val()), // PO
			ap_date_s : $.trim($("#apDateS").val()), // 驗收起日
			ap_date_e : $.trim($("#apDateE").val()) // 驗收迄日
		};
		$("#grid").setGridParam({datatype : "json",	postData : data, page : 1}).trigger("reloadGrid");
	}

	// 存檔
	function save() {
		// 取得被選取列
		var ids = $("#grid").jqGrid("getGridParam", "selarrrow");
		// 是否選取
		if (ids.length == 0) {
			alert("請選擇一筆要複驗的資料列");
			return false;
		}
		var osIds = "";
		for(var id in ids) {
			var row = $("#grid").jqGrid("getRowData", ids[id]);
			if (osIds != "") {
				osIds += ",";
			}
			osIds += $.trim(row["os_id".cf()]);
		}
		if (osIds != "") {
			$.ajax({
				url : CONTEXT_URL + "/pay/pay015/save",
				type : "POST",
				data : {
					os_ids : osIds
				},
				dataType : "json",
				async : false,
				success : function(data) {
					if (data.success) {
						$("#grid").trigger("reloadGrid");
						$("#grid2").jqGrid("clearGridData");
					}
					alert(data.msg);
				}
			});
		}
	}

	// 處理DB欄位名稱大小寫問題
	String.prototype.cf = function () {
		var keywordIndex = this.indexOf("_");
		if (keywordIndex >= 0) {
			return this.substr(0, keywordIndex).toLowerCase() + this.substr(keywordIndex).toUpperCase();
		} else {
			return this.toLowerCase();
		}
	};

	// Load script of Select2 and run this	
	$(document).ready(function() {
		LoadSelect2Script("<s:url value="/resources/plugins/select2/select2.min.js" />", selectDefault);

		// 驗收日期日曆
		$("#apDateS").datepicker();
		$("#apDateE").datepicker();

		// 查詢Button
		$("#btn_search").click(query);
		setMasterResults(); // 設定查詢結果顯示內容
		setDetailResults();

		// 儲存Button
		$("#btn_save").click(save);

		// 重置Button
		$("#btn_reset").click(function(){
			$("#searchFrom").trigger("reset");
			selectDefault();
			$("#grid").jqGrid("clearGridData");
			$("#grid2").jqGrid("clearGridData");
		});

		// 查詢結果Table寬度控制
		$(window).resize(function() {
			$(window).unbind("onresize");
			$("#grid,.grid").setGridWidth($("#jQgrid").width() - 10);
			$("#grid2,.grid").setGridWidth($("#jQgrid").width() - 10);
			$(window).bind("onresize", this);
		}).trigger("resize");
	});
</script>