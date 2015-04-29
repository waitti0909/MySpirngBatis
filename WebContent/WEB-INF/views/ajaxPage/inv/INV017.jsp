<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<title>序號更正作業</title>
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
							<span>序號更正作業</span>
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
								料號 :
							</label>
							<div class="col-sm-3">
								<input type="text" id="matNoMaterial" name="mat_no" class="form-control" placeholder="關鍵字查詢" />
							</div>
							<div class="col-sm-1">
								<i id="matNoSearch" class="fa fa-search" style="cursor: pointer;"></i>
							</div>
							<label class="col-sm-2 control-label">
								廠商序號 :
							</label>
							<div class="col-sm-3">
								<input id="venSn" type="text" name="ven_sn" class="form-control" placeholder="關鍵字查詢" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">
								基站編號 :
							</label>
							<div class="col-sm-3">
								<input id="btsSiteId" type="text" class="form-control"  placeholder="請輸入完整的基站編號" />
							</div>
							<div class="col-sm-1">
								<i id="siteIdSearch" class="fa fa-search" style="cursor: pointer;"></i>
							</div>
							<label class="col-sm-2 control-label">
								倉庫 :
							</label>
							<div class="col-sm-3">
								<select id="whIdSelect" name="wh_id" class="populate placeholder require">
									<option value="">-- 請選擇 --</option>
									<c:forEach items="${sessionScope.inv017_warehouseList}" var="var">
										<option value="${var.wh_id}">${var.wh_name}</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</form>
				</div>
			</div>
			<div id="ajaxSearchResult" class="col-xs-12">
				<!-- 固定id for window.resize start-->
				<div id="jQgrid_inv017">
					<table id="grid_inv017"></table>
					<div id="pager_inv017"></div>
				</div>
				<!-- 固定id for window.resize end-->
			</div>
		</div>
		<div id="selectMaterialPage"></div>
		<div id="selectSitePage"></div>
	</body>
</html>
<script type="text/javascript">

	function selectDefault() {
		$("#searchFrom").find("select").select2();
	}

	// 異動基站編號
	function onSiteIdChange() {
		if ($("#btsSiteId").val() == "") {
			$("#whIdSelect").prop("disabled", false);
		} else {
			$("#whIdSelect").val("").prop("disabled", true).select2();
		}
	}

	// 異動倉庫
	function onWhIdSelectChange() {
		if ($("#whIdSelect").val() == "") {
			$("#btsSiteId").prop("disabled", false);
		} else {
			$("#btsSiteId").val("").prop("disabled", true);
		}
	}

	// 執行查詢
	function query() {
		var matNo = $.trim($("#matNoMaterial").val());
		var venSn = $.trim($("#venSn").val());
		var btsSiteId = $.trim($("#btsSiteId").val());
		var whId = $.trim($("#whIdSelect").val());
		var count = 0;
		if (matNo != "") {
			count++;
		}
		if (venSn != "") {
			count++;
		}
		if (btsSiteId != "" || whId != "") {
			count++;
		}
		if (count < 2) {
			alert("請至少輸入兩項查詢條件");
		} else {
			var data = {
				mat_no : matNo, // 料號
				ven_sn :venSn, // 廠商序號
				bts_site_id : btsSiteId, // 基站編號
				wh_id : whId // 倉庫
			};
			$("#grid_inv017").setGridParam({datatype : "json",	postData : data, page:1}).trigger("reloadGrid");
		}
	}

	// 執行修改
	function update() {
		// 取得被選取列
		var ids = $("#grid_inv017").jqGrid("getGridParam", "selrow");
		// 是否選取
		if (ids == undefined) {
			alert("請選擇一筆資料編輯");
			return false;
		} else {
			var data = $("#grid_inv017").jqGrid("getRowData", ids);
			editAddPage(data.srl_no);
		}
	}

	// 設定查詢結果顯示內容
	function setResults() {
		$("#grid_inv017").jqGrid({
			datatype : "local",
			pager : "#pager_inv017",
			url : CONTEXT_URL + "/inv/inv017/query",
		   	colNames:["資料序號", "廠商序號", "放置地點", "料號", "品名"],
		   	colModel:[
				{name:"srl_no", index:"srl_no", sortable:false, hidden:true},
		   		{name:"ven_sn", index:"ven_sn", sortable:false},
		   		{name:"s_site_name", index:"s_site_name", sortable:false},
		   		{name:"mat_no", index:"mat_no", sortable:false},
		   		{name:"mat_name", index:"mat_name", sortable:false}
			],	
		   	viewrecords : true,
		   	caption : "序號清單",
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

	// 開啟倉庫新增/修改頁面
	function editAddPage(srlNo) {
		$.fancybox.open({
			href : CONTEXT_URL + "/inv/inv017/addEdit",
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
						srl_no : srlNo
					},
					type : "POST",
					error : function(jqXHR, testStatus, errorThrown) {
						alert(jqXHR);
					},
					async : false
			},
			beforeClose : function() {
			},
			afterShow : function() {
				$("#addEdit :input:enabled:visible:first").focus();
			},
			afterClose : function() {
				// fancybox關閉後要做的事(callback function)
				$("#btn_search").trigger("click");
				$("#grid_inv017").trigger("reloadGrid");
			}
		});
	}

	// 基站編號LOV Callback
	function receviceSiteData(data) {
		var json = JSON.parse(data);
		var btsSiteId = json.siteObjs[0].bts_SITE_ID;
		$("#btsSiteId").val(btsSiteId);
		onSiteIdChange();
	}

	// Load script of Select2 and run this	
	$(document).ready(function() {
		LoadSelect2Script("<s:url value="/resources/plugins/select2/select2.min.js" />", selectDefault);

		// 料號LOV
		$("#matNoSearch").click(function () {
			$("#matNoMaterial").val("");
			openMaterialFrame("selectMaterialPage"); // call common.js
		});
		// 基站編號LOV
		$("#siteIdSearch").click(function () {
			$("#btsSiteId").val("");
			openSiteDialogFrame("selectSitePage", "receviceSiteData"); // call common.js
		});

		// 異動基站編號
		$("#btsSiteId").change(onSiteIdChange);
		// 異動倉庫
		$("#whIdSelect").change(onWhIdSelectChange);
		
		// 查詢Button
		$("#btn_search").click(query);
		setResults(); // 設定查詢結果顯示內容

		// 修改Button
		$("#btn_edit").click(update);

		// 重置Button
		$("#btn_reset").click(function(){
			$("#searchFrom").trigger("reset").find("select").select2();
			$("#btsSiteId").prop("disabled", false);
			$("#whIdSelect").prop("disabled", false);
			selectDefault();
			$("#grid_inv017").jqGrid("clearGridData");
		});
	
		// 查詢結果Table寬度控制
		$(window).resize(function() {
			$(window).unbind("onresize");
			$("#grid_inv017,.grid").setGridWidth($("#jQgrid_inv017").width() - 10);
			$(window).bind("onresize", this);
		}).trigger("resize");
	});
</script>