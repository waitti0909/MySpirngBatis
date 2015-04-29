<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<!DOCTYPE html>
<html lang="en">
	<head>
		<title>工程驗收作業 </title>
		<script src="<s:url value="/resources/plugins/bootstrapvalidator/bootstrapValidator.min.js" />"></script>
	</head>
	<body>
		<div class="box-header">
			<div class="box-name">
				<i class="fa fa-edit"></i>
				<span>工程驗收作業 </span>
			</div>
			<div class="box-icons">
				<p class="expand-link"></p>
			</div>
			<div class="no-move"></div>
		</div>
		<div class="row">
			<div class="col-xs-12 col-sm-12">
				<div class="ui-draggable ui-droppable">
					<div class="box-content">
						<form id="addEdit" class="form-horizontal" method="post">
							<div id="btnDiv" class="col-sm-8">
								<button type="button" id="print" class="btn btn-primary btn-label-left">
									<span><i class="fa fa-print"></i></span>
									<s:message text="列印" />
								</button>
								<button type="button" id="paymentRequest" class="btn btn-primary btn-label-left" style="display: none;">
									<span><i class="fa fa-book"></i></span>
									<s:message text="送請款" />
								</button>
								<button type="button" id="certificate" class="btn btn-primary btn-label-left" style="display: none;">
									<span><i class="fa fa-plus"></i></span>
									<s:message text="輸入憑證" />
								</button>
							</div>
							<div class="col-sm-11" style="text-align: center; height: 30px;">
								<span class="s">
									本次驗收單號：<c:out value="${element.AP_NO}" />
								</span>
							</div>
							<div class="form-group">
								<div class="col-md-11">
									<div class="row">
										<label class="col-sm-2 control-label">
											維運區：
										</label>
										<div class="col-sm-3">
											<select class="populate placeholder">
												<option value="">-- 請選擇 --</option>
												<c:forEach items="${sessionScope.pay006_orgDomainList}" var="var">
													<option value="${var.ID}" <c:if test="${element.DOMAIN == var.ID}">selected="selected"</c:if>>${var.NAME}</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="row">
										<label class="col-sm-2 control-label">
											業者：
										</label>
										<div class="col-sm-3">
											<select id="coVatNo_M" class="populate placeholder">
												<option value="">-- 請選擇 --</option>
												<c:forEach items="${sessionScope.pay006_companyList}" var="var">
													<option value="${var.CO_VAT_NO}" <c:if test="${element.CO_VAT_NO == var.CO_VAT_NO}">selected="selected"</c:if>>${var.CO_NAME}</option>
												</c:forEach>
											</select>
										</div>
										<label class="col-sm-2 control-label">
											PO：
										</label>
										<div class="col-sm-3">
											<input type="text" value="${element.PO_NO}" class="form-control" />
										</div>
									</div>
									<div class="row">
										<label class="col-sm-2 control-label">
											申請人：
										</label>
										<div class="col-sm-3">
											<input type="text" value="${element.APP_USER_NAME}" class="form-control" />
										</div>
										<label class="col-sm-2 control-label">
											申請日期：
										</label>
										<div class="col-sm-3">
											<input type="text" value="<fmt:formatDate value="${element.APP_DATE}" pattern="yyyy/MM/dd" />" class="form-control" />
										</div>
									</div>
									<div class="row">
										<label class="col-sm-2 control-label">
											請款金額：
										</label>
										<div class="col-sm-2">
											<input type="text" id="apAmt_M" value="<fmt:formatNumber value="${element.AP_AMT}" pattern="#"/>" class="form-control" />
										</div>
										<label class="col-sm-2 control-label" style="width : 9%;">
											未稅金額：
										</label>
										<div class="col-sm-2">
											<input type="text" id="taxExclusiveAmt_M" value="<fmt:formatNumber value="${element.TAX_EXCLUSIVE_AMT}" pattern="#"/>" class="form-control" />
										</div>
										<label class="col-sm-1 control-label">
											營業稅：
										</label>
										<div class="col-sm-2">
											<input type="text" id="businessTax_M" value="<fmt:formatNumber value="${element.BUSINESS_TAX}" pattern="#"/>" class="form-control" />
										</div>
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
				<div id="ajaxSearchResult_D" class="col-xs-12">
					<!-- 固定id for window.resize start-->
					<div id="jQgrid_D">
						<table id="grid_D1"></table>
						<div id="pager_D1"></div>
						<br />
						<table id="grid_D2"></table>
						<div id="pager_D2"></div>
					</div>
					<!-- 固定id for window.resize end-->
				</div>
			</div>
		</div>
		<div id="certificatePage"></div>
	</body>
</html>
<script type="text/javascript">

	// 開啟下拉式選單搜尋文字功能
	function selectDefault() {
		$("#addEdit").find("select").select2();
	}

	// 檢視畫面設定
	function showSet() {
		var btnType = "<c:out value="${btn_type}" />";
		$("#addEdit :input").prop("disabled", true);
		if (btnType == "edit") {
			$("#paymentRequest").css("display", "inline-block");
			$("#certificate").css("display", "inline-block");
			$("#print").prop("disabled", false);
			$("#paymentRequest").prop("disabled", false);
			$("#certificate").prop("disabled", false);

			$("#grid_D1")
			.jqGrid("navGrid", "#pager_D1", {refresh:false, search:false, edit:false, add:false, del:false})
			.jqGrid("navButtonAdd", "#pager_D1", {caption:"刪除", buttonicon:"ui-icon-trash", onClickButton: deleteMaster});
			$("#grid_D2")
			.jqGrid("navGrid", "#pager_D2", {refresh:false, search:false, edit:false, add:false, del:false})
			.jqGrid("inlineNav", "#pager_D2", {edit:true, add:false, del:false, edittext:"編輯", savetext:"儲存", canceltext:"取消"});
		} else {
			$("#print").prop("disabled", false);
		}
	}

	// 執行查詢
	function query() {
		var data = {
			ap_no : $.trim("<c:out value="${element.AP_NO}" />"), // 驗收單號
			po_no : $.trim("<c:out value="${element.PO_NO}" />") // PO
		};
		$("#grid_D1").setGridParam({datatype : "json", postData : data, page : 1}).trigger("reloadGrid");
	}

	// 列印匯出EXCEL
	function excel() {
		window.location.href = CONTEXT_URL + "/pay/pay006/genExcel" +
			"?ap_no=" + $.trim("<c:out value="${element.AP_NO}" />") + // 驗收單號
			"&po_no=" + $.trim("<c:out value="${element.PO_NO}" />") + // PO
			"&co_vat_no=" + $.trim("<c:out value="${element.CO_VAT_NO}" />"); // 業者代碼
	}

	// 輸入憑證
	function showCertificatePage() {
		$.ajax({
			mimeType : "text/html; charset=utf-8",
			url : CONTEXT_URL + "/pay/pay006/sp1/init",
			type : "POST",
			data : {
				ap_no : $.trim("<c:out value="${element.AP_NO}" />") // 驗收單號
			},
			dataType : "html",
			async : false,
			success : function(data) {
				initialDialogFrame("certificatePage", "輸入憑證", data);
			}
		});
	}

	// 送請款
	function paymentRequest() {
		$.ajax({
			url : CONTEXT_URL + "/pay/pay006/paymentRequest",
			type : "POST",
			data : {
				ap_no : $.trim("<c:out value="${element.AP_NO}" />"), // 驗收單號
				po_no : $.trim("<c:out value="${element.PO_NO}" />"), // PO
				co_vat_no : $.trim("<c:out value="${element.CO_VAT_NO}" />") // 業者代碼
			},
			dataType : "json",
			async : false,
			success : function(data) {
				alert(data.msg);
				if (data.success) {
					parent.$.fancybox.close();
				}
			}
		});
	}

	// 刪除工程驗收資料
	function deleteMaster() {
		// 取得被選取列
		var ids = $("#grid_D1").jqGrid("getGridParam", "selarrrow");
		if (ids.length == 0) {
			return false;
		}
		var orderNos = "";
		var osIds = "";
		for(var id in ids) {
			var row = $("#grid_D1").jqGrid("getRowData", ids[id]);
			if (orderNos != "") {
				orderNos += ",";
			}
			if (osIds != "") {
				osIds += ",";
			}
			orderNos += $.trim(row["order_no".cf()]);
			osIds += $.trim(row["os_id".cf()]);
		}
		if (confirm("是否確定刪除？")) {
			$.ajax({
				url : CONTEXT_URL + "/pay/pay006/deleteAcceptanceOrder",
				type : "POST",
				data : {
					mst_ap_no : $.trim("<c:out value="${element.AP_NO}" />"), // 驗收單號
					po_no : $.trim("<c:out value="${element.PO_NO}" />"), // PO
					order_nos : orderNos,
					os_ids : osIds
				},
				dataType : "json",
				async : false,
				success : function(data) {
					if (data.success) {
						// 若驗收主檔資料已刪除，則關閉此頁面
						if (data.maps != null && data.maps.noDataFlag) {
							alert(data.msg);
							parent.$.fancybox.close();
							return true;
						}
						getPayOutsourceAcceptance();
						$("#grid_D1").trigger("reloadGrid");
						var data = {ap_no : "0"};
						$("#grid_D2").setGridParam({datatype : "json", postData : data, page : 1}).trigger("reloadGrid");
					}
					alert(data.msg);
				}
			});
		}
	}

	// 取得驗收主檔資料
	function getPayOutsourceAcceptance() {
		$.ajax({
			url : CONTEXT_URL + "/pay/pay006/search/payOutsourceAcceptance",
			type : "POST",
			data : {
				ap_no : $.trim("<c:out value="${element.AP_NO}" />") // 驗收單號
			},
			dataType : "json",
			async : false,
			success : function(data) {
				$("#apAmt_M").val(data["ap_amt".cf()]);
				$("#taxExclusiveAmt_M").val(data["tax_exclusive_amt".cf()]);
				$("#businessTax_M").val(data["business_tax".cf()]);
			}
		});
	}

	// 設定查詢結果顯示內容
	function setMasterResults() {
		var multiselectFlag = "<c:out value="${btn_type}" />" == "edit" ? true : false;
		$("#grid_D1").jqGrid({
			datatype : "local",
			pager : "#pager_D1",
			url : CONTEXT_URL + "/pay/pay006/queryAcceptanceOrder",
		   	colNames:["工單", "名稱", "派工單號", "站台", "驗收日", "總額", "未稅金額", "營業額"],
		   	colModel:[
				{name:"order_no".cf(), index:"order_no".cf(), sortable:false},
				{name:"order_name".cf(), index:"order_name".cf(), sortable:false},
				{name:"os_id".cf(), index:"os_id".cf(), sortable:false},
				{name:"site_name".cf(), index:"site_name".cf(), sortable:false},
				{name:"ap_date".cf(), index:"ap_date".cf(), sortable:false, formatter: "date", formatoptions:{newformat: "Y/m/d"}},
				{name:"ap_amt".cf(), index:"ap_amt".cf(), sortable:false},
				{name:"tax_exclusive_amt".cf(), index:"tax_exclusive_amt".cf(), sortable:false},
				{name:"business_tax".cf(), index:"business_tax".cf(), sortable:false}
			],
		   	viewrecords : true,
		   	caption : "工程驗收清單",
			rownumbers : true,
			multiselect : multiselectFlag,
		   	onSelectRow : function(id) {
		   		var ids = $("#grid_D1").jqGrid("getGridParam", "selrow");
		   		var row = $("#grid_D1").jqGrid("getRowData", ids);
		   		var data = {
		   			ap_no : $.trim("<c:out value="${element.AP_NO}" />"), // 驗收單號
		   			po_no : $.trim("<c:out value="${element.PO_NO}" />"), // PO
		   			order_no : row["order_no".cf()], // 異動單號
		   			os_id : row["os_id".cf()] // 派工單號
	   			};
	   			$("#grid_D2").setGridParam({datatype : "json", postData : data, page : 1}).trigger("reloadGrid");
		    },
		    gridComplete : function() {
			},
			ondblClickRow : function(rowId) {
	        }
		});
	}

	// 設定查詢結果顯示內容
	function setDetailResults() {
		$("#grid_D2").jqGrid({
			datatype : "local",
			pager : "#pager_D2",
			url : CONTEXT_URL + "/pay/pay006/queryOutsourceAcceptanceDtl",
			editurl : CONTEXT_URL + "/pay/pay006/updateRealApQty",
		   	colNames:["主檔驗收單號(編輯)", "主檔PO(編輯)", "工單單號(編輯)", "工項(編輯)", "單價(編輯)", "派工單號(編輯)", "工項", "名稱", "單價", "申請數量", "驗收數量", "實際驗收數量", "請款金額", "未稅金額", "營業稅"],
		   	colModel:[
				{name:"mst_ap_no".cf(), index:"mst_ap_no".cf(), sortable:false, hidden:true, editable:true, editrules:{required:true}},
				{name:"po_no".cf(), index:"mst_ap_no".cf(), sortable:false, hidden:true, editable:true, editrules:{required:true}},
				{name:"order_no".cf(), index:"order_no".cf(), sortable:false, hidden:true, editable:true, editrules:{required:true}},
				{name:"order_item".cf(), index:"order_item".cf(), sortable:false, hidden:true, editable:true, editrules:{required:true}},
				{name:"unit_price".cf(), index:"unit_price".cf(), sortable:false, hidden:true, editable:true, editrules:{required:true}},
				{name:"os_id".cf(), index:"os_id".cf(), sortable:false, hidden:true, editable:true, editrules:{required:true}},
				{name:"order_item".cf(), index:"order_item".cf(), sortable:false},
				{name:"order_item_desc".cf(), index:"order_item_desc".cf(), sortable:false},
				{name:"unit_price".cf(), index:"unit_price".cf(), sortable:false},
				{name:"app_qty".cf(), index:"app_qty".cf(), sortable:false},
				{name:"ap_qty".cf(), index:"ap_qty".cf(), sortable:false},
				{name:"real_ap_qty".cf(), index:"real_ap_qty".cf(), sortable:false, editable:true, editrules:{required:true, integer:true, minValue:0}},
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
		})
		.bind("jqGridInlineSuccessSaveRow", function (event, jqXHR, rowid, options) {
			var data = jqXHR.responseJSON;
			if (data.success) {
				getPayOutsourceAcceptance();
				$("#grid_D1").trigger("reloadGrid");
				$("#grid_D2").trigger("reloadGrid");
			} else {
				alert(data.msg);
				//$.jgrid.info_dialog("錯誤", data.msg, $.jgrid.edit.bClose);
			}
			//return [data.success, jqXHR.responseJSON];
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

		setMasterResults();
		setDetailResults();
		showSet();

		// 列印款按鈕
		$("#print").click(excel);
		// 送請款按鈕
		$("#paymentRequest").click(paymentRequest);
		// 輸入憑證按鈕
		$("#certificate").click(showCertificatePage);

		// 查詢結果Table寬度控制
		$(window).resize(function() {
			$(window).unbind("onresize");
			$("#grid_D1,.grid").setGridWidth($("#jQgrid").width() + 90);
			$("#grid_D2,.grid").setGridWidth($("#jQgrid").width() + 90);
			$(window).bind("onresize", this);
		}).trigger("resize");
		// 修正jqGrid訊息視窗z-index
		$.jgrid.jqModal = $.extend($.jgrid.jqModal || {}, {
		    zIndex: 8012
		});

		query();
	});
</script>