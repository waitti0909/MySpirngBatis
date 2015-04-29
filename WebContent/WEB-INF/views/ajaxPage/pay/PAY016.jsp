<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<title>工程驗收傳票作業</title>
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
							<span>工程驗收傳票作業</span>
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
									<c:forEach items="${sessionScope.pay016_orgDomainList}" var="var">
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
									<c:forEach items="${sessionScope.pay016_companyList}" var="var">
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
								申請起日：
							</label>
							<div class="col-sm-3">
								<input type="text" id="crTimeS" value="<fmt:formatDate value="${cr_time_s}" pattern="yyyy/MM/dd" />" readonly="readonly" class="form-control" placeholder="申請起日" />
							</div>
							<label class="col-sm-2 control-label">
								申請迄日：
							</label>
							<div class="col-sm-3">
								<input type="text" id="crTimeE" value="<fmt:formatDate value="${cr_time_e}" pattern="yyyy/MM/dd" />" readonly="readonly" class="form-control" placeholder="申請迄日" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">
								<span class="s">*</span> 申請狀態：
							</label>
							<div class="col-sm-4">
								<c:forEach items="${sessionScope.pay016_statusList}" var="var">
									<c:if test="${var.LOOKUP_CODE != 'A'}">
										<div class="checkbox-inline">
											<label>
												<input type="checkbox" name="status" value="${var.LOOKUP_CODE}" checked="checked" />${var.LOOKUP_CODE_DESC}<i class="fa fa-square-o"></i>
											</label>
										</div>
									</c:if>
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
		</div>
	</body>
</html>
<script type="text/javascript">

	// 開啟下拉式選單搜尋文字功能
	function selectDefault() {
		$("#searchFrom").find("select").select2();
	}

	// 設定查詢結果顯示內容
	function setResults() {
		$("#grid").jqGrid({
			datatype : "local",
			pager : "#pager",
			url : CONTEXT_URL + "/pay/pay016/query",
		   	colNames:["驗收單號", "PO", "申請狀態代碼", "申請狀態", "請款金額", "憑證", "傳票號碼", "傳票日期", "申請人", "申請日期"],
		   	colModel:[
		   		{name:"ap_no".cf(), index:"ap_no".cf(), sortable:false, width: 200},
		   		{name:"po_no".cf(), index:"po_no".cf(), sortable:false},
		   		{name:"status".cf(), index:"status".cf(), sortable:false, hidden:true},
		   		{name:"status_dscr".cf(), index:"status_dscr".cf(), sortable:false},
		   		{name:"ap_amt".cf(), index:"ap_amt".cf(), sortable:false},
		   		{name:"certificate_flag".cf(), index:"certificate_flag".cf(), sortable:false},
		   		{name:"subpoena_nbr".cf(), index:"subpoena_nbr".cf(), sortable:false, editable:true, editrules:{required:true}},
		   		{name:"subpoena_date".cf(), index:"subpoena_date".cf(), sortable:false, formatter: "date", formatoptions:{newformat: "Y/m/d"},
		   			editable:true,
		   			editrules:{required:true, date:true},
		   			editoptions:{
		   				readonly:true,
		   				style:"cursor: not-allowed; background-color: #eee; opacity: 1;",
		   				dataInit:function(colId){$(colId).datepicker();}
		   			}
		   		},
		   		{name:"app_user_name".cf(), index:"app_user_name".cf(), sortable:false},
		   		{name:"app_date".cf(), index:"app_date".cf(), sortable:false, formatter: "date", formatoptions:{newformat: "Y/m/d"}}
			],	
		   	viewrecords : true,
		   	caption : "工程驗收清單",
			rownumbers : true,
			multiselect : true,
		   	onSelectRow : function(id) {
		   		var row = $("#grid").jqGrid("getRowData", id);
		   		var status = $.trim(row["status".cf()]);
		   		if (status == "P") {
		   			$("#jqg_grid_" + id).prop("checked", false);
		   		}
		    },
		    gridComplete : function() {
		    	var $this = $(this);
		    	var ids = $this.jqGrid("getDataIDs");
		    	for (var id in ids) {
		    		var rowIndex = parseFloat(ids[id]);
		    		var row = $("#grid").jqGrid("getRowData", rowIndex);
		    		var status = $.trim(row["status".cf()]);
		    		if (status == "P") {
		    			$("#jqg_grid_" + rowIndex).prop("disabled", true);
		    		} else {
		    			$this.jqGrid("editRow", ids[id], true);
		    		}
		    	}
			},
			ondblClickRow : function(rowId) {
	        }
		});
	}

	// 查詢
	function query() {
		var status = $.trim($("input:checkbox:checked[name='status']").map(function() { return $(this).val(); }).get());
		if (status == "") {
			alert("申請狀態至少需勾選一項");
			return false;
		}
		var data = {
			domain : $.trim($("#domainSelect").val()), // 維運區
			co_vat_no : $.trim($("#coVatNoSelect").val()), // 業者
			po_no : $.trim($("#poNo").val()), // PO
			cr_time_s : $.trim($("#crTimeS").val()), // 申請起日
			cr_time_e : $.trim($("#crTimeE").val()), // 申請迄日
			status : status // 申請狀態
		};
		$("#grid").setGridParam({datatype:"json", postData:data, page:1}).trigger("reloadGrid");
	}

	// 存檔
	function save() {
		var ids = $("#grid").jqGrid("getGridParam", "selarrrow");
   		if (ids.length == 0) {
   			alert("請至少勾選一筆需存檔的資料列");
			return;
		}
   		// 使用jQgrid欄位檢核
   		var validateFlag = true;
   		for (var id in ids) {
   			var rowIndex = parseFloat(ids[id]);
   			// 檢核所勾選的資料是否可編輯
   			if ($("#" + rowIndex + "_" + "subpoena_nbr".cf()).prop("id") == undefined) {
   				alert("請勾選可編輯的資料");
   				validateFlag = false;
   				break;
   			}
   			// 啟用jQgrid檢核
   			$("#grid").jqGrid("saveRow", rowIndex, false, "clientArray");
   			// 判斷jQgrid檢核是否成功
   			if ($("#" + rowIndex + "_" + "subpoena_nbr".cf()).prop("id") != undefined) {
   				validateFlag = false;
   				break;
   			}
   			// 檢核特殊字元
   			var row = $("#grid").jqGrid("getRowData", rowIndex);
   			var subpoenaNbr = $.trim(row["subpoena_nbr".cf()]);
   			if (subpoenaNbr.indexOf(",") >= 0) {
   				$.jgrid.info_dialog("錯誤", "傳票號碼: 不可包含字元[ , ]", $.jgrid.edit.bClose);
   				validateFlag = false;
   				break;
   			}
   		}
   		if (validateFlag == false) { // 若檢核有誤，則將欄位恢復至編輯狀態
   			for (var id in ids) {
   				var rowIndex = parseFloat(ids[id]);
   				var row = $("#grid").jqGrid("getRowData", rowIndex);
   				var status = $.trim(row["status".cf()]);
   				if (status != "P") {
   					$("#grid").jqGrid("editRow", rowIndex, true);
   				}
   			}
   		} else { // 檢核成功後處理相關邏輯
   			var apNos = ""; // 驗收單號
   			var subpoenaNbrs = ""; // 傳票號碼
   			var subpoenaDates = ""; // 傳票日期
   			for (var id in ids) {
   				var rowIndex = parseFloat(ids[id]);
   				var row = $("#grid").jqGrid("getRowData", rowIndex);
   
   				if (apNos != "") { apNos += ","; }
   				if (subpoenaNbrs != "") { subpoenaNbrs += ","; }
   				if (subpoenaDates != "") { subpoenaDates += ","; }

   				apNos += $.trim(row["ap_no".cf()]);
   				subpoenaNbrs += $.trim(row["subpoena_nbr".cf()]);
   				subpoenaDates += $.trim(row["subpoena_date".cf()]);
   			}

   			$.ajax({
   				url : CONTEXT_URL + "/pay/pay016/save",
   				type : "POST",
   				data : {
   					ap_nos : apNos, // 驗收單號
   					subpoena_nbrs : subpoenaNbrs, // 傳票號碼
   					subpoena_dates : subpoenaDates // 傳票日期
   				},
   				dataType : "json",
   				async : false,
   				success : function(data) {
   					$("#grid").trigger("reloadGrid");
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

		// 申請日期日曆
		$("#crTimeS").datepicker();
		$("#crTimeE").datepicker();

		// 查詢Button
		$("#btn_search").click(query);
		setResults(); // 設定查詢結果顯示內容

		// 存檔Button
		$("#btn_save").click(save);

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