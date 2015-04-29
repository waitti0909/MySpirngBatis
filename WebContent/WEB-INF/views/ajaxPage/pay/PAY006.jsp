<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<title>工程驗收作業</title>
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
							<span>工程驗收作業</span>
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
									<c:forEach items="${sessionScope.pay006_orgDomainList}" var="var">
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
									<c:forEach items="${sessionScope.pay006_companyList}" var="var">
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
								<c:forEach items="${sessionScope.pay006_statusList}" var="var">
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
					<table id="grid1"></table>
					<div id="pager1"></div>
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
		$("#grid1").setGridParam({datatype : "json",	postData : data, page : 1}).trigger("reloadGrid");
	}

	// 修改
	function update() {
		// 取得被選取列
		var ids = $("#grid1").jqGrid("getGridParam", "selrow");
		// 是否選取
		if (ids == undefined) {
			alert("請選擇一筆資料編輯");
			return false;
		}
		var data = $("#grid1").jqGrid("getRowData", ids);

		if (data["status".cf()] != "A") {
			alert("申請狀態為[" + data["status_dscr".cf()] + "]的資料不可修改，請重新操作");
			return false;
		}
		editAddPage("edit", data["ap_no".cf()]);
	}

	// 設定查詢結果顯示內容
	function setResults() {
		$("#grid1").jqGrid({
			datatype : "local",
			pager : "#pager1",
			url : CONTEXT_URL + "/pay/pay006/query",
		   	colNames:["驗收單號", "PO", "申請狀態代碼", "申請狀態", "請款金額", "憑證", "傳票號碼", "傳票日期", "申請人", "申請日期"],
		   	colModel:[
		   		{name:"ap_no".cf(), index:"ap_no".cf(), sortable:false, width: 200},
		   		{name:"po_no".cf(), index:"po_no".cf(), sortable:false},
		   		{name:"status".cf(), index:"status".cf(), sortable:false, hidden:true},
		   		{name:"status_dscr".cf(), index:"status_dscr".cf(), sortable:false},
		   		{name:"ap_amt".cf(), index:"ap_amt".cf(), sortable:false},
		   		{name:"certificate_flag".cf(), index:"certificate_flag".cf(), sortable:false},
		   		{name:"subpoena_nbr".cf(), index:"subpoena_nbr".cf(), sortable:false},
		   		{name:"subpoena_date".cf(), index:"subpoena_date".cf(), sortable:false, formatter: "date", formatoptions:{newformat: "Y/m/d"}},
		   		{name:"app_user_name".cf(), index:"app_user_name".cf(), sortable:false},
		   		{name:"app_date".cf(), index:"app_date".cf(), sortable:false, formatter: "date", formatoptions:{newformat: "Y/m/d"}}
			],	
		   	viewrecords : true,
		   	caption : "驗收清單",
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

	// 開啟新增/修改頁面
	function editAddPage(btnType, apNo) {
		$.fancybox.open({
			href : CONTEXT_URL + "/pay/pay006/addEdit",
			type : "ajax",
	        width : $(window).width(),
	        height : $(window).height(),
			autoSize : false,
			padding : [0, 0, 0, 0],
			scrolling : "yes",
			helpers : {
				overlay : {
					closeClick : false
				},
				title : {
					type : "float"
				}
			},
			title : "表單",
			openEffect : "elastic",
			closeEffect : "fade",
			ajax : {
					data : {
						btn_type : btnType,
						ap_no : apNo
					},
					type : "POST",
					error : function(jqXHR, testStatus, errorThrown) {
						alert(jqXHR);
					},
					async : false
			},
			beforeClose : function() {
				if($("#btntype").val() == "edit"){
					if (!confirm("提醒：請確認資料是否存檔，關閉視窗？")) {
						return false;
					}
				}
			},
			afterShow : function() {
				$("#addEdit :input:enabled:visible:first").focus();
			},
			afterClose : function() {
				// fancybox關閉後要做的事(callback function)
				$("#btn_search").trigger("click");
				$("#grid1").trigger("reloadGrid");
			}
		});
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

		// 修改Button
		$("#btn_edit").click(update);

		// 新增Button
		$("#btn_add").click(function() {
			editAddPage("add", null);
		});

		// 顯示明細Button
		$("#btn_detail").click(function() {
			// 取得被選取列
			var ids = $("#grid1").jqGrid("getGridParam", "selrow");
			if (ids == undefined) {
				return false;
			}
			var data = $("#grid1").jqGrid("getRowData", ids);
			editAddPage("view", data["ap_no".cf()]);
		});

		// 重置Button
		$("#btn_reset").click(function(){
			$("#searchFrom").trigger("reset");
			selectDefault();
			$("#grid1").jqGrid("clearGridData");
		});

		// 查詢結果Table寬度控制
		$(window).resize(function() {
			$(window).unbind("onresize");
			$("#grid1,.grid").setGridWidth($("#jQgrid").width() - 10);
			$(window).bind("onresize", this);
		}).trigger("resize");
	});
</script>