<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>


<!DOCTYPE html>
<html lang="en">
	<head>
		<title>BOM維護作業</title>
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
							<span>BOM維護作業</span>
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
								設備型態 :
							</label>
							<div class="col-sm-3">
								<select id="eqpModelSelect" name="eqpModelId" class="populate placeholder">
									<option value="">-- 請選擇 --</option>
									<c:forEach items="${sessionScope.inv027_EqpModelList}" var="var">
										<option value="${var.EQP_MODEL_ID}">${var.EQP_MODEL}</option>
									</c:forEach>
								</select>
							</div>
							<label class="col-sm-2 control-label">
								設備機型 :
							</label>
							<div class="col-sm-3">
								<select id="eqpTypeSelect" name="eqpTypeId" class="populate placeholder">
									<option value="">-- 請選擇 --</option>
								</select>
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

	// 異動設備型態
	function onEqpModelChange() {
		$("#eqpTypeSelect").html("");
		$("<option value=''>-- 請選擇 --</option>").appendTo("#eqpTypeSelect");
		$("#eqpTypeSelect").select2();
		
		if($("#eqpModelSelect").val() == "") {
			return false;
		}
		$.ajax({
			url : CONTEXT_URL + "/inv/inv027/search/eqpType",
			type : "POST",
			data : {
				eqpModelId : $("#eqpModelSelect").val()
			},
			async : false,
			success : function(map) {
				for (var key in map) {
					$("<option value='" + key + "'>" + map[key] + "</option>").appendTo("#eqpTypeSelect");
				}
			}
		});
	}

	// 執行查詢
	function query() {
		var data = {
			eqp_model_id : $.trim($("#eqpModelSelect").val()), // 設備型態 
			eqp_type_id : $.trim($("#eqpTypeSelect").val()) // 設備機型
		};
		$("#grid").setGridParam({datatype : "json",	postData : data, page:1}).trigger("reloadGrid");
	}

	// 執行修改
	function update() {
		// 取得被選取列
		var ids = $("#grid").jqGrid("getGridParam", "selrow");
		// 是否選取
		if (ids == undefined) {
			alert("請選擇一筆資料編輯");
			return false;
		} else {
			var data = $("#grid").jqGrid("getRowData", ids);
			editAddPage("edit", data.eqp_model_id, data.eqp_model_name, data.eqp_type_id, data.eqp_type_name);
		}
	}

	// 設定主檔查詢結果顯示內容
	function setMasterResults() {
		$("#grid").jqGrid({
			datatype : "local",
			pager : "#pager",
			url : CONTEXT_URL + "/inv/inv027/query",
		   	colNames:["設備型態", "設備型態名稱", "設備機型", "設備機型名稱"],
		   	colModel:[
		   		{name:"eqp_model_id", index:"eqp_model_id", sortable:false},
		   		{name:"eqp_model_name", index:"eqp_model_name", sortable:false},
		   		{name:"eqp_type_id", index:"eqp_type_id", sortable:false},
		   		{name:"eqp_type_name", index:"eqp_type_name", sortable:false}
			],
		   	viewrecords : true,
		   	caption : "設備清單主檔",
			rownumbers : true,
			multiselect : false,
		   	onSelectRow : function(id) {
		   		var ids = $("#grid").jqGrid("getGridParam", "selrow");
		   		var row = $("#grid").jqGrid("getRowData", ids);
		   		var data = {
	   				eqp_model_id : row.eqp_model_id, // 設備型態 
	   				eqp_type_id : row.eqp_type_id // 設備機型
	   			};
	   			$("#grid2").setGridParam({datatype : "json", postData : data, page:1}).trigger("reloadGrid");
		    },
		    gridComplete : function() {
		    	$("#grid2").jqGrid("clearGridData", true);
			},
			ondblClickRow : function(rowId) {
	        }
		});
	}

	// 設定主檔明細結果顯示內容
	function setDetailResults() {
		$("#grid2").jqGrid({
			datatype : "local",
			pager : "#pager2",
			url : CONTEXT_URL + "/inv/inv027/queryDetail",
		   	colNames:["大分類代碼", "大分類", "中分類代碼", "中分類", "小分類代碼", "小分類", "料號", "品名", "數量", "生效日期", "失效日期"],
		   	colModel:[
		   		{name:"catg1_code", index:"catg1_code", sortable:false},
		   		{name:"catg1_name", index:"catg1_name", sortable:false},
		   		{name:"catg2_code", index:"catg2_code", sortable:false},
		   		{name:"catg2_name", index:"catg2_name", sortable:false},
		   		{name:"catg3_code", index:"catg3_code", sortable:false},
		   		{name:"catg3_name", index:"catg3_name", sortable:false},
		   		{name:"mat_no", index:"mat_no", sortable:false},
		   		{name:"mat_name", index:"mat_name", sortable:false},
		   		{name:"qty", index:"qty", sortable:false},
		   		{name:"effective_date", index:"effective_date", sortable:false, formatter: "date", formatoptions:{newformat: "Y/m/d"}},
		   		{name:"failure_date", index:"failure_date", sortable:false, formatter: "date", formatoptions:{newformat: "Y/m/d"}}
			],	
		   	viewrecords : true,
		   	caption : "設備清單明細",
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

	// 開啟BOM新增/修改頁面
	function editAddPage(btnType, eqpModelId, eqpModelName, eqpTypeId, eqpTypeName) {
		$.fancybox.open({
			href : CONTEXT_URL + "/inv/inv027/addEdit",
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
						eqp_model_id : eqpModelId,
						eqp_model_name: eqpModelName,
						eqp_type_id : eqpTypeId,
						eqp_type_name : eqpTypeName
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
				$("#grid").trigger("reloadGrid");
				$("#grid2").trigger("reloadGrid");
			}
		});
	}

	// 日期格式化，只取年月日
	function dayFormat(cellvalue){
		if (cellvalue == null || cellvalue == "") {
			return "";
		} else {
			return cellvalue.substr(0,cellvalue.indexOf(" "));
		} 
    }

	// Load script of Select2 and run this	
	$(document).ready(function() {
		LoadSelect2Script("<s:url value="/resources/plugins/select2/select2.min.js" />", selectDefault);

		// 查詢Button
		$("#btn_search").click(query);
		setMasterResults(); // 設定主檔查詢結果顯示內容
		setDetailResults(); // 設定明細查詢結果顯示內容

		// 新增Button
		$("#btn_add").click(function() {
			editAddPage("add", null, null, null, null);
		});

		// 修改Button
		$("#btn_edit").click(update);

		// 重置Button
		$("#btn_reset").click(function(){
			$("#searchFrom").trigger("reset");
			selectDefault();
			$("#grid").jqGrid("clearGridData");
			$("#grid2").jqGrid("clearGridData");
		});

		// 異動設備型態
		$("#eqpModelSelect").change(onEqpModelChange);

		// 查詢結果Table寬度控制
		$(window).resize(function() {
			$(window).unbind("onresize");
			$("#grid,.grid").setGridWidth($("#jQgrid").width() - 10);
			$("#grid2,.grid").setGridWidth($("#jQgrid").width() - 10);
			$(window).bind("onresize", this);
		}).trigger("resize");
	});
</script>