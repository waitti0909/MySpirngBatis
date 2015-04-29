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
								<button type="submit" id="queryBtn_A" class="btn btn-primary btn-label-left">
									<span><i class="fa fa-search"></i></span>
									<s:message text="資料處理" />
								</button>
								<button type="button" id="saveBtn_A" class="btn btn-primary btn-label-left">
									<span><i class="fa fa-save"></i></span>
									<s:message text="驗收" />
								</button>
								<button type="button" id="cancelBtn_A" class="btn btn-default btn-label-left">
									<span><i class="fa fa-undo txt-danger"></i></span>
									<s:message code="button.reset" text="重置" />
								</button>
							</div>
							<div class="form-group">
								<div class="col-md-11">
									<div class="row">
										<label class="col-sm-2 control-label">
											<span class="s">*</span> 維運區：
										</label>
										<div class="col-sm-3">
											<select id="domainSelect_A" name="domain" class="populate placeholder require">
												<option value="">-- 請選擇 --</option>
												<c:forEach items="${sessionScope.pay006_orgDomainList}" var="var">
													<option value="${var.ID}">${var.NAME}</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="row">
										<label class="col-sm-2 control-label">
											<span class="s">*</span> 業者：
										</label>
										<div class="col-sm-3">
											<select id="coVatNoSelect_A" name="co_vat_no" class="populate placeholder require">
												<option value="">-- 請選擇 --</option>
												<c:forEach items="${sessionScope.pay006_companyList}" var="var">
													<option value="${var.CO_VAT_NO}">${var.CO_NAME}</option>
												</c:forEach>
											</select>
										</div>
										<label class="col-sm-2 control-label">
											<span class="s">*</span> PO：
										</label>
										<div class="col-sm-3">
											<input type="text" id="poNo_A" name="po_no" class="form-control require" />
										</div>
									</div>
									<div class="row">
										<label class="col-sm-2 control-label">
											<span class="s">*</span> 申請人：
										</label>
										<div class="col-sm-3">
											<select id="aplUser_A" name="apl_user" class="populate placeholder require">
												<option value="">-- 請選擇 --</option>
											</select>
											<!-- <input type="text" id="aplUser_A" name="apl_user" class="form-control require" /> -->
										</div>
										<label class="col-sm-2 control-label">
											<span class="s">*</span> 申請日期：
										</label>
										<div class="col-sm-3">
											<input type="text" id="aplTime_A" name="apl_time" value="<fmt:formatDate value="${element.APL_TIME}" pattern="yyyy/MM/dd" />" class="form-control require" readonly="readonly" placeholder="申請日期" />
										</div>
									</div>
									<div class="row">
										<label class="col-sm-2 control-label">
											請款金額：
										</label>
										<div class="col-sm-2">
											<input type="text" id="apAmt_A" value="0" class="form-control" readonly="readonly" />
										</div>
										<label class="col-sm-2 control-label" style="width : 9%;">
											未稅金額：
										</label>
										<div class="col-sm-2">
											<input type="text" id="taxExclusiveAmt_A" value="0" class="form-control" readonly="readonly" />
										</div>
										<label class="col-sm-1 control-label">
											營業稅：
										</label>
										<div class="col-sm-2">
											<input type="text" id="businessTax_A" value="0" class="form-control" readonly="readonly" />
										</div>
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
				<div id="ajaxSearchResult_A" class="col-xs-12">
					<!-- 固定id for window.resize start-->
					<div id="jQgrid_A">
						<table id="grid_A1"></table>
						<div id="pager_A1"></div>
						<br />
						<table id="grid_A2"></table>
						<div id="pager_A2"></div>
					</div>
					<!-- 固定id for window.resize end-->
				</div>
			</div>
		</div>
	</body>
</html>
<script type="text/javascript">

	// 開啟下拉式選單搜尋文字功能
	function selectDefault() {
		$("#addEdit").find("select").select2();
	}

	// 檢視畫面設定
	function showSet() {
		$("#saveBtn_A").prop("disabled", true);
	}

	// 重新檢核指定欄位
	function revalidateField(id) {
		$("#addEdit").bootstrapValidator("updateStatus", $("#" + id).prop("name"), "NOT_VALIDATED")
		.bootstrapValidator("validateField", $("#" + id).prop("name"));
	}

	// 設定查詢結果顯示內容
	function setMasterResults(initFlag) {
		$("#grid_A1").jqGrid({
			datatype : "local",
			pager : "#pager_A1",
			url : CONTEXT_URL + "/pay/pay006/querySiteOutsourcing",
		   	colNames:["", "工單", "名稱", "派工單號", "站台代碼", "站台", "驗收日", "總額", "未稅金額", "營業額"],
		   	colModel:[
				{name:"po_id".cf(), index:"po_id".cf(), sortable:false, hidden:true},
				{name:"order_id".cf(), index:"order_id".cf(), sortable:false},
				{name:"order_desc".cf(), index:"order_desc".cf(), sortable:false},
				{name:"os_id".cf(), index:"os_id".cf(), sortable:false},
				{name:"site_id".cf(), index:"site_id".cf(), sortable:false, hidden:true},
				{name:"site_name".cf(), index:"site_name".cf(), sortable:false},
				{name:"ap_date".cf(), index:"ap_date".cf(), sortable:false, formatter: "date", formatoptions:{newformat: "Y/m/d"}},
				{name:"ap_amt".cf(), index:"ap_amt".cf(), sortable:false},
				{name:"tax_exclusive_amt".cf(), index:"tax_exclusive_amt".cf(), sortable:false},
				{name:"business_tax".cf(), index:"business_tax".cf(), sortable:false}
			],
		   	viewrecords : true,
		   	caption : "工程驗收清單",
			rownumbers : true,
			multiselect : true,
		   	onSelectRow : function(id) {
		   		var ids = $("#grid_A1").jqGrid("getGridParam", "selarrrow");
		   		if (ids.length == 0) {
		   			$("#grid_A2").jqGrid("clearGridData");
					return;
				}
		   		var osIds = "";
		   		var poIds = "";
		   		var orderIds = "";
				for(var id in ids) {
					var row = $("#grid_A1").jqGrid("getRowData", ids[id]);
					if (osIds != "") {
						osIds += ",";
					}
					if (poIds != "") {
						poIds += ",";
					}
					if (orderIds != "") {
						orderIds += ",";
					}
					osIds += $.trim(row["os_id".cf()]);
					poIds += $.trim(row["po_id".cf()]);
					orderIds += $.trim(row["order_id".cf()]);
				}
		   		var data = {
	   				os_ids : osIds,
	   				po_ids : poIds,
	   				order_ids : orderIds
	   			};
	   			$("#grid_A2").setGridParam({datatype : "json", postData : data, page : 1}).trigger("reloadGrid");
		    },
		    onSelectAll : function(rowids, status) {
		    	if (status) {
		    		var ids = $("#grid_A1").jqGrid("getGridParam", "selarrrow");
			   		if (ids.length == 0) {
			   			$("#grid_A2").jqGrid("clearGridData");
						return;
					}
			   		var osIds = "";
			   		var poIds = "";
			   		var orderIds = "";
					for(var id in ids) {
						var row = $("#grid_A1").jqGrid("getRowData", ids[id]);
						if (osIds != "") {
							osIds += ",";
						}
						if (poIds != "") {
							poIds += ",";
						}
						if (orderIds != "") {
							orderIds += ",";
						}
						osIds += $.trim(row["os_id".cf()]);
						poIds += $.trim(row["po_id".cf()]);
						orderIds += $.trim(row["order_id".cf()]);
					}
			   		var data = {
		   				os_ids : osIds,
		   				po_ids : poIds,
		   				order_ids : orderIds
		   			};
		   			$("#grid_A2").setGridParam({datatype : "json", postData : data, page : 1}).trigger("reloadGrid");
		    	} else {
		    		$("#grid_A2").jqGrid("clearGridData");
		    	}
		    },
		    gridComplete : function() {
		    	var $this = $(this);
		    	var ids = $this.jqGrid("getDataIDs");
		    	if (queryFlag && ids.length == 0) {
		    		queryFlag = false;
		    		alert("查無資料");
		    	}
			},
			ondblClickRow : function(rowId) {
	        }
		});
	}

	// 設定查詢結果顯示內容
	function setDetailResults() {
		$("#grid_A2").jqGrid({
			datatype : "local",
			pager : "#pager_A2",
			url : CONTEXT_URL + "/pay/pay006/querySiteOsItem",
		   	colNames:["", "", "", "工項", "名稱", "單價", "申請數量", "驗收數量", "實際驗收數量", "請款金額", "未稅金額", "營業稅"],
		   	colModel:[
				{name:"os_id".cf(), index:"os_id".cf(), sortable:false, hidden:true},
				{name:"po_id".cf(), index:"po_id".cf(), sortable:false, hidden:true},
				{name:"mst_order_id".cf(), index:"mst_order_id".cf(), sortable:false, hidden:true},
				{name:"item_id".cf(), index:"item_id".cf(), sortable:false},
				{name:"item_name".cf(), index:"item_name".cf(), sortable:false},
				{name:"price".cf(), index:"price".cf(), sortable:false},
				{name:"number".cf(), index:"number".cf(), sortable:false},
				{name:"ap_number".cf(), index:"ap_number".cf(), sortable:false},
				{name:"real_ap_number".cf(), index:"real_ap_number".cf(), sortable:false, editable:true, editrules:{required:true, integer:true, minValue:0}},
				{name:"ap_amt".cf(), index:"ap_amt".cf(), sortable:false},
				{name:"tax_exclusive_amt".cf(), index:"tax_exclusive_amt".cf(), sortable:false},
				{name:"business_tax".cf(), index:"business_tax".cf(), sortable:false}
			],
		   	viewrecords : true,
		   	caption : "工單資訊",
			rownumbers : true,
			rowNum : 1000,
			multiselect : false,
		   	onSelectRow : function(id) {
		    },
		    gridComplete : function() {
		    	var $this = $(this);
		    	var ids = $this.jqGrid("getDataIDs");
		        for (var i = 0; i < ids.length; i++) {
		            $this.jqGrid("editRow", ids[i], true);
		        }
			},
			ondblClickRow : function(rowId) {
	        }
		});
	}

	// 取得申請人
	function getAuthPersonnelByDomain() {
		$("#aplUser_A").html("");
		$("<option value=''>-- 請選擇 --</option>").appendTo("#aplUser_A");
		$("#aplUser_A").select2();
		if ($.trim($("#domainSelect_A").val()) == "") {
			return false;
		}
		$.ajax({
			url : CONTEXT_URL + "/pay/pay006/search/authPersonnelByDomain",
			type : "POST",
			data : {
				domain : $.trim($("#domainSelect_A").val()) // 維運區
			},
			dataType : "json",
			async : false,
			success : function(data) {
				for (var i = 0; i < data.length; i++) {
					$("<option value='" + data[i].psn_NO + "'>" + data[i].chn_NM + "</option>").appendTo("#aplUser_A");
				}
			}
		});
	}

	// 查詢
	var queryFlag = false;
	function query() {
		$("#addEdit :input").prop("disabled", true);
		$("#saveBtn_A").prop("disabled", false);
		$("#cancelBtn_A").prop("disabled", false);

		var data = {
			domain : $.trim($("#domainSelect_A").val()), // 維運區
			co_vat_no : $.trim($("#coVatNoSelect_A").val()), // 業者
			po_no : $.trim($("#poNo_A").val()), // PO
			apl_user : $.trim($("#aplUser_A").val()), // 申請人
			apl_time : $.trim($("#aplTime_A").val()) // 申請日期
		};
		queryFlag = true;
		$("#grid_A1").setGridParam({datatype : "json",	postData : data, page : 1}).trigger("reloadGrid");
		getSumAmount(data);
	}

	// 取得站點工程相關金額加總
	function getSumAmount(data) {
		$.ajax({
			url : CONTEXT_URL + "/pay/pay006/search/siteOutsourcingSumAmount",
			type : "POST",
			data : data,
			dataType : "json",
			async : false,
			success : function(data) {
				$("#apAmt_A").val(data["ap_amt".cf()]);
				$("#taxExclusiveAmt_A").val(data["tax_exclusive_amt".cf()]);
				$("#businessTax_A").val(data["business_tax".cf()]);
			}
		});
	}

	// 送出驗收
	function save() {
		var ids = $("#grid_A1").jqGrid("getGridParam", "selarrrow");
   		if (ids.length == 0) {
			return;
		}
   		$("#saveBtn_A").prop("disabled", true);

   		// 處理Master資料
   		var siteIds = ""; // 站點代碼
		var orderIds = ""; // 工單代碼
		var osIds = ""; // 派工單號
		for(var id in ids) {
			var row = $("#grid_A1").jqGrid("getRowData", ids[id]);
			if (orderIds != "") { orderIds += ","; }
			if (siteIds != "") { siteIds += ","; }
			if (osIds != "") { osIds += ","; }
			orderIds += $.trim(row["order_id".cf()]);
			siteIds += $.trim(row["site_id".cf()]);
			osIds += $.trim(row["os_id".cf()]);
		}

		// 處理Detail資料
		var records = $("#grid_A2").jqGrid("getGridParam", "records");
		var mstOrderIds = ""; // Master的工單代碼
		var itemIds = ""; // 工項
		var prices = ""; // 單價
		var numbers = ""; // 申請數量
		var apNumbers = ""; // 驗收數量
		var realApNumbers = ""; // 實際驗收數量
		for (var i = 0; i < parseFloat(records); i++) {
			$("#grid_A2").jqGrid("saveRow", i + 1, false, "clientArray");
			var row = $("#grid_A2").jqGrid("getRowData", i + 1);
			
			if (mstOrderIds != "") { mstOrderIds += ","; }
			if (itemIds != "") { itemIds += ","; }
			if (prices != "") { prices += ","; }
			if (numbers != "") { numbers += ","; }
			if (apNumbers != "") { apNumbers += ","; }
			if (realApNumbers != "") { realApNumbers += ","; }
			mstOrderIds += $.trim(row["mst_order_id".cf()]);
			itemIds += $.trim(row["item_id".cf()]);
			prices += $.trim(row["price".cf()]);
			numbers += $.trim(row["number".cf()]);
			apNumbers += $.trim(row["ap_number".cf()]);
			realApNumbers += $.trim(row["real_ap_number".cf()]);
		}

		$.ajax({
			url : CONTEXT_URL + "/pay/pay006/insertSiteOutsourcing",
			type : "POST",
			data : {
				domain : $.trim($("#domainSelect_A").val()), // 維運區
				co_vat_no : $.trim($("#coVatNoSelect_A").val()), // 業者
				po_no : $.trim($("#poNo_A").val()), // PO
				apl_user : $.trim($("#aplUser_A").val()), // 申請人
				apl_time : $.trim($("#aplTime_A").val()), // 申請日期
				order_ids : orderIds, // 工單
				site_ids : siteIds, // 站台代碼
				os_ids : osIds, // 派工單號
				mst_order_ids : mstOrderIds, // Master的工單代碼
				item_ids : itemIds, // 工項
				prices : prices, // 單價
				numbers : numbers, // 申請數量
				ap_numbers : apNumbers, // 驗收數量
				real_ap_numbers : realApNumbers // 實際驗收數量
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

	//Load script of Select2 and run this
	$(document).ready(function() {
		LoadSelect2Script("<s:url value="/resources/plugins/select2/select2.min.js" />", selectDefault);

		// 異動維運區
		$("#domainSelect_A").change(getAuthPersonnelByDomain);

		showSet();
		//$("#aplTime_A").datepicker(); // 申請日期日曆(測試用)
		setMasterResults();
		setDetailResults();

		// 資料處理按鈕(欄位檢核設定)
		var fields = {};
		$(".require").each(function () {
			var name = $(this).attr("name");
			var group = "." + $(this).parent().attr("class");
			fields[name] = {
				group : group,
				validators : {
					notEmpty : {
						message : "不可為空!"
					}
				}
			};
		});
		$("#addEdit").bootstrapValidator({
			fields : fields
		}).on("success.form.bv", function(e) {
			e.preventDefault();
			query();
		});
		// 驗收按鈕
		$("#saveBtn_A").click(save);
		// 重置按鈕
		$("#cancelBtn_A").click(function() {
			$("#queryBtn_A").prop("disabled", false);
			$("#saveBtn_A").prop("disabled", true);
			$("#domainSelect_A").prop("disabled", false);
			$("#coVatNoSelect_A").prop("disabled", false);
			$("#poNo_A").prop("disabled", false);
			$("#aplUser_A").prop("disabled", false);
			$("#aplTime_A").prop("disabled", false);
			$("#apAmt_A").prop("disabled", false).val("0");
			$("#taxExclusiveAmt_A").prop("disabled", false).val("0");
			$("#businessTax_A").prop("disabled", false).val("0");
			$("#grid_A1").jqGrid("clearGridData");
			$("#grid_A2").jqGrid("clearGridData");
			selectDefault();
			$("#addEdit .require").each(function() {
				var id = $(this).prop("id");
				if (id.indexOf("s2id_") == -1) {
					revalidateField(id);
				}
			});
		});

		// 查詢結果Table寬度控制
		$(window).resize(function() {
			$(window).unbind("onresize");
			$("#grid_A1,.grid").setGridWidth($("#jQgrid").width() + 90);
			$("#grid_A2,.grid").setGridWidth($("#jQgrid").width() + 90);
			$(window).bind("onresize", this);
		}).trigger("resize");
	});
</script>