<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>


<!DOCTYPE html>
<html lang="en">
	<head>
		<title>資產分佈查詢作業</title>
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
							<span>資產分佈查詢作業</span>
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
								料號：
							</label>
							<div class="col-sm-3">
								<input type="text" id="matNoMaterial" class="form-control" placeholder="關鍵字查詢" />
							</div>
							<div class="col-sm-1" style="width: 1%;">
								<i id="matNoSearch" class="fa fa-search" style="cursor: pointer;"></i>
							</div>
							<label class="col-sm-2 control-label">
								貼標號碼：
							</label>
							<div class="col-sm-3">
								<input type="text" id="tag_no" class="form-control" placeholder="關鍵字查詢" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label"><span class="s">*</span>
								資產位置：
							</label>
							<div class="col-sm-3">
								<div class="checkbox-inline">
									<label>
										<input type="checkbox" name="asset_location" value="S" />站點<i class="fa fa-square-o"></i>
									</label>
								</div>
								<div class="checkbox-inline">
									<label>
										<input type="checkbox" name="asset_location" value="W" />庫存<i class="fa fa-square-o"></i>
									</label>
								</div>
								<div class="checkbox-inline">
									<label>
										<input type="checkbox" name="asset_location" value="O" />在途<i class="fa fa-square-o"></i>
									</label>
								</div>
							</div>
							<div class="col-sm-1" style="width: 1%;"></div>
							<label class="col-sm-2 control-label">
								倉庫：
							</label>
							<div class="col-sm-3">
								<select id="whIdSelect" class="populate placeholder">
									<option value="">-- 請選擇 --</option>
									<c:forEach items="${sessionScope.inv021_warehouseList}" var="var">
										<option value="${var.wh_id}">${var.wh_name}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">
								區域：
							</label>
							<div class="col-sm-3">
								<select id="domainSelect" class="populate placeholder">
									<option value="">-- 請選擇 --</option>
									<c:forEach items="${sessionScope.inv021_orgDomainList}" var="var">
										<option value="${var.ID}">${var.NAME}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-sm-1" style="width: 1%;"></div>
							<label class="col-sm-2 control-label"><span class="s">*</span>
								物料狀態：
							</label>
							<div class="col-sm-4">
								<div class="checkbox-inline">
									<label>
										<input type="checkbox" name="mat_status" value="1" />可用品<i class="fa fa-square-o"></i>
									</label>
								</div>
								<div class="checkbox-inline">
									<label>
										<input type="checkbox" name="mat_status" value="2" />待送修<i class="fa fa-square-o"></i>
									</label>
								</div>
								<div class="checkbox-inline">
									<label>
										<input type="checkbox" name="mat_status" value="3" />待報廢<i class="fa fa-square-o"></i>
									</label>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">
								工程維護單位：
							</label>
							<div class="col-sm-3">
								<select id="deptSelect" class="populate placeholder">
									<option value="">-- 請選擇 --</option>
								</select>
							</div>
							<div class="col-sm-1" style="width: 1%;"></div>
							<label class="col-sm-2 control-label">
								基站編號：
							</label>
							<div class="col-sm-3">
								<input id="btsSiteId" type="text" class="form-control" placeholder="請輸入完整的基站編號" />
							</div>
							<div class="col-sm-1" style="width: 1%;">
								<i id="siteIdSearch" class="fa fa-search" style="cursor: pointer;"></i>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">
								在途超過天數：
							</label>
							<div class="col-sm-2" style="width: 10%;">
								<input type="number" id="overDay" value="0" size="2" min="0" max="20" class="form-control" />
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
		<div id="selectMaterialPage"></div>
		<div id="selectSitePage"></div>
	</body>
</html>
<script type="text/javascript">

	// 開啟下拉式選單搜尋文字功能
	function selectDefault() {
		$("#searchFrom").find("select").select2();
	}

	// 異動區域
	function onDomainSelectChange() {
		$("#deptSelect").html("");
		$("<option value=''>-- 請選擇 --</option>").appendTo("#deptSelect");
		$("#deptSelect").select2();
		
		if($("#domainSelect").val() == "") {
			return false;
		}
		$.ajax({
			url : CONTEXT_URL + "/inv/inv021/search/orgDept",
			type : "POST",
			data : {
				domain : $("#domainSelect").val()
			},
			async : false,
			success : function(map) {
				for (var key in map) {
					$("<option value='" + key + "'>" + map[key] + "</option>").appendTo("#deptSelect");
				}
			}
		});
	}

	// 查詢條件檢核
	var map = [];
	function validate() {
		map = [];
		var assetLocation = $.trim($("input:checkbox:checked[name='asset_location']").map(function() { return $(this).val(); }).get());
		var matStatus = $.trim($("input:checkbox:checked[name='mat_status']").map(function() { return $(this).val(); }).get());
		var overDay = $.trim($("#overDay").val());
		
		if (assetLocation == "") {
			alert("資產位置至少需勾選一項");
			return false;
		}
		if (matStatus == "") {
			alert("物料狀態至少需勾選一項");
			return false;
		}
		if (overDay == "") {
			$("#overDay").val("0");
			overDay = "0";
		}
		var re = /^[0-9]+$/;
		if (!re.test(overDay)){
			alert("在途超過天數需輸入數字");
			return false;
		}
		if (parseFloat(overDay) < 0 || parseFloat(overDay) > 20) {
			alert("在途超過天數超過限制範圍(1 ~ 20)");
			return false;
		}

		map["assetLocation"] = assetLocation;
		map["matStatus"] = matStatus;

		return true;
	}

	// 取得查詢結果筆數
	function getDataCount(maxRowNum) {
		var count = 0;
		$.ajax({
			url : CONTEXT_URL + "/inv/inv021/getDataCount",
			type : "POST",
			data : {
				max_row_num : maxRowNum,
				mat_no : $.trim($("#matNoMaterial").val()), // 料號
				tag_no : $.trim($("#tag_no").val()), // 貼標號碼
				asset_location : map["assetLocation"], // 資產位置
				wh_id : $.trim($("#whIdSelect").val()), // 倉庫
				domain : $.trim($("#domainSelect").val()), // 區域
				mat_status : map["matStatus"], // 物料狀態
				dept_id : $.trim($("#deptSelect").val()), // 工程維護單位
				bts_site_id : $.trim($("#btsSiteId").val()), // 基站編號
				over_day : $.trim($("#overDay").val()) // 在途超過天數
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

	// 查詢
	function query() {
		if (validate()) {
			var count = getDataCount("1001");
			if (count > 1000) {
				alert("資料超過1000筆，請改按匯出EXCEL取得資料");
				return false;
			}
			var data = {
				mat_no : $.trim($("#matNoMaterial").val()), // 料號
				tag_no : $.trim($("#tag_no").val()), // 貼標號碼
				asset_location : map["assetLocation"], // 資產位置
				wh_id : $.trim($("#whIdSelect").val()), // 倉庫
				domain : $.trim($("#domainSelect").val()), // 區域
				mat_status : map["matStatus"], // 物料狀態
				dept_id : $.trim($("#deptSelect").val()), // 工程維護單位
				bts_site_id : $.trim($("#btsSiteId").val()), // 基站編號
				over_day : $.trim($("#overDay").val()) // 在途超過天數
			};
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
			window.location.href = CONTEXT_URL + "/inv/inv021/genExcel" +
				"?mat_no=" + $.trim($("#matNoMaterial").val()) + // 料號
				"&tag_no=" + $.trim($("#tag_no").val()) + // 貼標號碼
				"&asset_location=" + map["assetLocation"] + // 資產位置
				"&wh_id=" + $.trim($("#whIdSelect").val()) + // 倉庫
				"&domain=" + $.trim($("#domainSelect").val()) + // 區域
				"&mat_status=" + map["matStatus"] + // 物料狀態
				"&dept_id=" + $.trim($("#deptSelect").val()) + // 工程維護單位
				"&bts_site_id=" + $.trim($("#btsSiteId").val()) + // 基站編號
				"&over_day=" + $.trim($("#overDay").val()) ; // 在途超過天數
		}
	}

	// 設定查詢結果顯示內容
	function setResults() {
		$("#grid").jqGrid({
			datatype : "local",
			pager : "#pager",
			url : CONTEXT_URL + "/inv/inv021/query",
		   	colNames:["大分類代碼", "大分類", "中分類代碼", "中分類", "小分類代碼", "小分類", "料號", "品名", "廠商序號", "貼標號碼", "批號", "數量", "放置地點", "放置日期", "物料狀態", "區域", "工程維護單位"],
		   	colModel:[
		   		{name:"catg1_code", index:"catg1_code", sortable:false},
		   		{name:"catg1_name", index:"catg1_name", sortable:false},
		   		{name:"catg2_code", index:"catg2_code", sortable:false},
		   		{name:"catg2_name", index:"catg2_name", sortable:false},
		   		{name:"catg3_code", index:"catg3_code", sortable:false},
		   		{name:"catg3_name", index:"catg3_name", sortable:false},
		   		{name:"mat_no", index:"mat_no", sortable:false},
		   		{name:"mat_name", index:"mat_name", sortable:false},
		   		{name:"ven_sn", index:"ven_sn", sortable:false},
		   		{name:"tag_no", index:"tag_no", sortable:false},
		   		{name:"fa_no", index:"fa_no", sortable:false},
		   		{name:"qty", index:"qty", sortable:false},
		   		{name:"wh_name", index:"wh_name", sortable:false},
		   		{name:"cr_time", index:"cr_time", sortable:false, formatter: "date", formatoptions:{newformat: "Y/m/d H:i"}},
		   		{name:"mat_status_dscr", index:"mat_status_dscr", sortable:false},
		   		{name:"domain_name", index:"domain_name", sortable:false},
		   		{name:"dept_name", index:"dept_name", sortable:false}
			],	
		   	viewrecords : true,
		   	caption : "資產分佈清單",
			rownumbers : true,
			multiselect : false,
			shrinkToFit : false,
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
		LoadSelect2Script("<s:url value="/resources/plugins/select2/select2.min.js" />", selectDefault);

		// 料號LOV
		$("#matNoSearch").click(function () {
			openMaterialFrame("selectMaterialPage"); // call common.js
		});
		// 基站編號LOV
		$("#siteIdSearch").click(function () {
			$("#btsSiteId").val("");
			openSiteDialogFrame("selectSitePage", "receviceSiteData"); // call common.js
		});
		// 異動區域
		$("#domainSelect").change(onDomainSelectChange);

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