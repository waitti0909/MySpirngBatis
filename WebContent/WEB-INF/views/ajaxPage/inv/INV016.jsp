<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<title>倉庫維護作業</title>
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
							<span>倉庫維護作業</span>
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
								倉庫 :
							</label>
							<div class="col-sm-3">
								<select id="whIdSelect" name="whId" class="populate placeholder">
									<option value="">-- 請選擇 --</option>
									<c:forEach items="${sessionScope.inv016_warehouseList}" var="var">
										<option value="${var.wh_id}">${var.wh_name}</option>
									</c:forEach>
								</select>
							</div>
							<label class="col-sm-2 control-label">
								倉庫屬性 :
							</label>
							<div class="col-sm-2">
								<select id="whAttributeSelect" name="whAttribute" class="populate placeholder">
									<option value="">-- 請選擇 --</option>
									<c:forEach items="${sessionScope.inv016_whAttributeList}" var="var">
										<option value="${var.value}">${var.label}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">
								倉庫類別 :
							</label>
							<div class="col-sm-3">
								<select id="whTypeSelect" name="whType" class="populate placeholder">
									<option value="">-- 請選擇 --</option>
									<c:forEach items="${sessionScope.inv016_whTypeList}" var="var">
										<option value="${var.value}">${var.label}</option>
									</c:forEach>
								</select>
							</div>
							<label class="col-sm-2 control-label">
								啟用狀況 :
							</label>
							<div class="col-sm-2">
								<select id="isEffectiveSelect" name="isEffective" class="populate placeholder">
									<option value="">-- 請選擇 --</option>
									<c:forEach items="${sessionScope.inv016_isEffectiveList}" var="var">
										<option value="${var.value}">${var.label}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">
								區域 :
							</label>
							<div class="col-sm-2">
								<select id="domainSelect" name="domain" class="populate placeholder">
									<option value="">-- 請選擇 --</option>
									<c:forEach items="${sessionScope.inv016_orgDomainList}" var="var">
										<option value="${var.ID}">${var.NAME}</option>
									</c:forEach>
								</select>
							</div>
							<label class="col-sm-3 control-label">
								管理單位 :
							</label>
							<div class="col-sm-3">
								<select id="deptSelect" name="deptId" class="populate placeholder">
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

	// 異動區域
	function onDomainSelectChange() {
		$("#deptSelect").html("");
		$("<option value=''>-- 請選擇 --</option>").appendTo("#deptSelect");
		$("#deptSelect").select2();
		
		if($("#domainSelect").val() == "") {
			return false;
		}
		$.ajax({
			url : CONTEXT_URL + "/inv/inv016/search/cityArea",
			type : "POST",
			data : {
				cityArea : $("#domainSelect").val()
			},
			async : false,
			success : function(map) {
				for (var key in map) {
					$("<option value='" + key + "'>" + map[key] + "</option>").appendTo("#deptSelect");
				}
			}
		});
	}

	// 執行查詢
	function query() {
		var data = {
			wh_id : $.trim($("#whIdSelect").val()), // 倉庫
			wh_attribute : $.trim($("#whAttributeSelect").val()), // 倉庫屬性
			wh_type : $.trim($("#whTypeSelect").val()), // 倉庫類別
			is_effective : $.trim($("#isEffectiveSelect").val()), // 使用狀況
			domain : $.trim($("#domainSelect").val()), // 區域
			dept_id : $.trim($("#deptSelect").val()) // 管理單位
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
			editAddPage("edit", data.wh_id);
		}
	}

	// 設定查詢結果顯示內容
	function setResults() {
		$("#grid").jqGrid({
			datatype : "local",
			pager : "#pager",
			url : CONTEXT_URL + "/inv/inv016/query",
		   	colNames:["倉庫代碼", "倉庫名稱", "倉庫類別代碼", "倉庫類別", "倉庫屬性代碼", "倉庫屬性", "區域代碼", "區域", "管理單位", "保管人", "廠商", "啟用狀況"],
		   	colModel:[
		   		{name:"wh_id", index:"wh_id", sortable:false},
		   		{name:"wh_name", index:"wh_name", sortable:false},
		   		{name:"wh_type", index:"wh_type", sortable:false, hidden:true},
		   		{name:"wh_type_name", index:"wh_type_name", sortable:false},
		   		{name:"wh_attribute", index:"wh_attribute", sortable:false, hidden:true},
		   		{name:"wh_attribute_name", index:"wh_attribute_name", sortable:false},
		   		{name:"domain", index:"domain", sortable:false, hidden:true},
		   		{name:"domain_name", index:"domain_name", sortable:false},
		   		{name:"dept_name", index:"dept_name", sortable:false},
		   		{name:"assigned_dscr", index:"assigned_dscr", sortable:false},
		   		{name:"co_name", index:"co_name", sortable:false},
		   		{name:"is_effective_name", index:"is_effective_name", sortable:false}
			],	
		   	viewrecords : true,
		   	caption : "倉庫清單",
			rownumbers : true,
			multiselect : false,
		   	onSelectRow : function(id) {
		    },
		    gridComplete : function() {
			},
			ondblClickRow : function(rowId) {
				var rowData = jQuery(this).getRowData(rowId);
				var whId = null;
	            $.each(rowData, function (i, item) {
	            	if (i == "wh_id") {
	            		whId = item;
	            	}
	            });
	            editAddPage("view", whId);
	        }
		});
	}
	
	// 開啟倉庫新增/修改頁面
	function editAddPage(btnType, whId) {
		$.fancybox.open({
			href : CONTEXT_URL + "/inv/inv016/addEdit",
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
						wh_id : whId
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
			}
		});
	}

	// Load script of Select2 and run this
	$(document).ready(function() {
		LoadSelect2Script("<s:url value="/resources/plugins/select2/select2.min.js" />", selectDefault);

		// 查詢Button
		$("#btn_search").click(query);
		setResults(); // 設定查詢結果顯示內容

		// 新增Button
		$("#btn_add").click(function() {
			editAddPage("add", null);
		});

		// 修改Button
		$("#btn_edit").click(update);

		// 重置Button
		$("#btn_reset").click(function(){
			$("#searchFrom").trigger("reset");
			selectDefault();
			$("#grid").jqGrid("clearGridData");
		});

		// 異動區域
		$("#domainSelect").change(onDomainSelectChange);

		// 查詢結果Table寬度控制
		$(window).resize(function() {
			$(window).unbind("onresize");
			$("#grid,.grid").setGridWidth($("#jQgrid").width() - 10);
			$(window).bind("onresize", this);
		}).trigger("resize");
	});
</script>