<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>


<!DOCTYPE html>
<html lang="en">
	<head>
		<title>BOM維護作業</title>
		<script src="<s:url value="/resources/plugins/bootstrapvalidator/bootstrapValidator.min.js" />"></script>
		<style type="text/css">
			.jqmWindow {
				z-index: 10000;
			}
		</style>
	</head>
	<body>
		<div class="box-header">
			<div class="box-name">
				<i class="fa fa-edit"></i>
				<span>BOM修改</span>
			</div>
			<div class="box-icons">
				<p class="expand-link"></p>
			</div>
			<div class="no-move"></div>
		</div>
		<div class="row">
			<div class="col-xs-12 col-sm-12">
				<div class="ui-draggable ui-droppable">
					<div class="box-content form-horizontal">
						<div class="form-group">
							<div class="row">
								<label class="col-sm-2 control-label">
								設備型態：</label>
								<div class="col-sm-2">
									<input type="text" id="eqpModel_E" value="${element.eqp_model_name}"
										class="form-control require" readonly="readonly" />
								</div>
								<label class="col-sm-2 control-label">
								設備機型：</label>
								<div class="col-sm-2">
									<input type="text" id="eqpType_E" value="${element.eqp_type_name}"
										class="form-control require" readonly="readonly" />
								</div>
							</div>
						</div>
					</div>
				</div>
				<div id="ajaxSearchResult_E" class="col-xs-8">
					<!-- 固定id for window.resize start-->
					<div id="jQgrid_E">
						<table id="grid_E"></table>
						<div id="pager_E"></div>
					</div>
					<!-- 固定id for window.resize end-->
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-10 col-sm-10">
				<div class="ui-draggable ui-droppable">
					<div class="box-content">
						<form id="addEdit" class="form-horizontal" method="post">
							<input type="hidden" id="btnType_E" />
							<div id="test" class="form-group">
								<div class="row">
									<label class="col-sm-2 control-label">
									大分類：</label>
									<div class="col-sm-2">
										<select id="catg1Select_E" name="catg1_code" class="populate placeholder require">
											<option value="">-- 請選擇 --</option>
											<c:forEach items="${sessionScope.inv027_Category1List}" var="var">
												<option value="${var.catg1_code}">${var.catg1_name}</option>
											</c:forEach>
										</select>
									</div>
									<label class="col-sm-2 control-label">
									中分類：</label>
									<div class="col-sm-2">
										<select id="catg2Select_E" name="catg2_code" class="populate placeholder require">
											<option value="">-- 請選擇 --</option>
										</select>
									</div>
									<label class="col-sm-2 control-label">
									數量：</label>
									<div class="col-sm-2">
										<input type="number" id="qty_E" name="qty" min="1"
											class="form-control require"
											data-bv-integer-message="需輸入數字!"
											data-bv-greaterthan-message="數值需大於%s" />
									</div>
								</div>
								<div class="row">
									<label class="col-sm-2 control-label">
									小分類：</label>
									<div class="col-sm-2">
										<select id="catg3Select_E" name="catg3_code" class="populate placeholder require">
											<option value="">-- 請選擇 --</option>
										</select>
									</div>
									<label class="col-sm-2 control-label">
									料號：</label>
									<div class="col-sm-2">
										<select id="matNoSelect_E" name="mat_no" class="populate placeholder require">
											<option value="">-- 請選擇 --</option>
										</select>
									</div>
									<label class="col-sm-2 control-label">
									失效日期：</label>
									<div class="col-sm-2">
										<input type="text" id="failureDate_E" name="failure_date"
											class="form-control" readonly="readonly" placeholder="失效日期" />
									</div>
								</div>
								<div class="row">
									<label class="col-sm-2 control-label"></label>
									<div class="col-sm-2"></div>
									<div class="col-sm-4">
										<button type="submit" id="saveBtn" class="btn btn-primary btn-label-left">
											<span><i class="fa fa-save"></i></span>
											<s:message code="button.save" text="儲存" />
										</button>
									</div>
									<div class="col-sm-2"></div>
									<div class="col-sm-4">
										<button type="button" id="cancelBtn" class="btn btn-default btn-label-left">
											<span><i class="fa fa-undo txt-danger"></i></span>
											<s:message code="button.reset" text="重置" />
										</button>
									</div>
								</div>
							</div>
						</form>
					</div>
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
		$("#addEdit").hide();
		$("#saveBtn").prop("disabled", true);
	}

	// 異動大分類
	function onCatg1SelectChange() {
		$("#catg2Select_E").html("");
		$("<option value=''>-- 請選擇 --</option>").appendTo("#catg2Select_E");
		onCatg2SelectChange();

		if($("#catg1Select_E").val() == "") {
			return false;
		}
		$.ajax({
			url : CONTEXT_URL + "/inv/inv027/search/category2",
			type : "POST",
			data : {
				catg1_code : $.trim($("#catg1Select_E").val())
			},
			async : false,
			success : function(map) {
				for (var key in map) {
					$("<option value='" + key + "'>" + map[key] + "</option>").appendTo("#catg2Select_E");
				}
			}
		});
	}

	// 異動中分類
	function onCatg2SelectChange() {
		$("#catg3Select_E").html("");
		$("<option value=''>-- 請選擇 --</option>").appendTo("#catg3Select_E");
		onCatg3SelectChange();

		if($("#catg2Select_E").val() == "") {
			return false;
		}
		$.ajax({
			url : CONTEXT_URL + "/inv/inv027/search/category3",
			type : "POST",
			data : {
				catg1_code : $.trim($("#catg1Select_E").val()),
				catg2_code : $.trim($("#catg2Select_E").val())
			},
			async : false,
			success : function(map) {
				for (var key in map) {
					$("<option value='" + key + "'>" + map[key] + "</option>").appendTo("#catg3Select_E");
				}
			}
		});
	}

	// 異動小分類
	function onCatg3SelectChange() {
		$("#matNoSelect_E").html("");
		$("<option value=''>-- 請選擇 --</option>").appendTo("#matNoSelect_E");

		if($("#catg3Select_E").val() == "") {
			return false;
		}
		$.ajax({
			url : CONTEXT_URL + "/inv/inv027/search/material",
			type : "POST",
			data : {
				catg1_code : $.trim($("#catg1Select_E").val()),
				catg2_code : $.trim($("#catg2Select_E").val()),
				catg3_code : $.trim($("#catg3Select_E").val())
			},
			async : false,
			success : function(map) {
				for (var key in map) {
					$("<option value='" + key + "'>" + map[key] + "</option>").appendTo("#matNoSelect_E");
				}
			}
		});
	}

	// 設定主檔明細結果顯示內容
	var lastSelectId;
	function setDetailResults() {
		$("#grid_E").jqGrid({
			datatype : "local",
			pager : "#pager_E",
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
		   		{name:"qty", index:"qty", sortable:false, editable:true},
		   		{name:"effective_date", index:"effective_date", sortable:false, formatter: "date", formatoptions:{newformat: "Y/m/d"}},
		   		{name:"failure_date", index:"failure_date", sortable:false, formatter: "date", formatoptions:{newformat: "Y/m/d"}}
			],	
		   	viewrecords : true,
		   	caption : "設備清單明細",
		   	height : 220,
			rownumbers : true,
			multiselect : false,
		   	onSelectRow : function(id) {
		   		if(id && id != lastSelectId){
		   			$("#addEdit").hide();
		   			lastSelectId = id;
		   		}
		    },
		    gridComplete : function() {
			},
			ondblClickRow : function(rowId) {
			}
		});
		// 設定新增/修改Button
		jQuery("#grid_E").jqGrid("navGrid","#pager_E",{search:false,edit:false,add:false,del:false});
		jQuery("#grid_E").jqGrid("navButtonAdd", "#pager_E", {
			caption:"新增",
			onClickButton: function() {
				$("#catg1Select_E").prop("disabled", false).val("").select2();
				$("#catg2Select_E").prop("disabled", false).val("").select2();
				$("#catg3Select_E").prop("disabled", false).val("").select2();
				$("#matNoSelect_E").prop("disabled", false).val("").select2();
				$("#qty_E").prop("disabled", false).val("");
				$("#failureDate_E").val("").prop("disabled", true);
				onCatg1SelectChange();
				$("#btnType_E").val("add");
				$("#addEdit").show();
			}
		});
		jQuery("#grid_E").jqGrid("navButtonAdd", "#pager_E", {
			caption:"修改",
			onClickButton: function() {
				var id = $(this).jqGrid("getGridParam", "selrow");
				if (id) {
					var row = $(this).jqGrid("getRowData", id);
					$("#catg1Select_E").val(row.catg1_code).select2();
					onCatg1SelectChange();
					$("<option value='" + row.catg3_code + "'>" + row.catg3_name + "</option>").appendTo("#catg3Select_E");
					$("<option value='" + row.mat_no + "'>" + row.mat_name + "</option>").appendTo("#matNoSelect_E");
					$("#catg2Select_E").val(row.catg2_code).select2();
					$("#catg3Select_E").val(row.catg3_code).select2();
					$("#matNoSelect_E").val(row.mat_no).select2();
					$("#qty_E").val(row.qty);
					$("#failureDate_E").val($.trim(row.failure_date));
					$("#catg1Select_E").prop("disabled", true);
					$("#catg2Select_E").prop("disabled", true);
					$("#catg3Select_E").prop("disabled", true);
					$("#matNoSelect_E").prop("disabled", true);
					if ($.trim(row.failure_date) != "") {
						$("#qty_E").prop("disabled", true);
						$("#failureDate_E").prop("disabled", true);
					} else {
						$("#qty_E").prop("disabled", false);
						$("#failureDate_E").prop("disabled", false);
					}
					$("#btnType_E").val("edit");
					$("#addEdit").show();
				} else {
					$("#btnType_E").val("");
					alert("請選擇一筆資料編輯");
				}
			}
		});
	}

	// 存檔動作
	function MSubmitData() {
		var re = /^[0-9]+$/;
		if (!re.test($.trim($("#qty_E").val()))){
			alert("數量需輸入數字");
			return false;
		}
		if ($("#btnType_E").val() == "edit" && $.trim($("#failureDate_E").val()) != "") {
			if (!confirm("異動失效日期，是否確定存檔？")) {
				return false;
			}
		}
		$.ajax({
			url : CONTEXT_URL + "/inv/inv027/update",
			type : "POST",
			data : {
				btn_type : $.trim($("#btnType_E").val()),
				eqp_model_id : $.trim("<c:out value="${element.eqp_model_id}" />"),
				eqp_type_id : $.trim("<c:out value="${element.eqp_type_id}" />"),
				mat_no : $.trim($("#matNoSelect_E").val()),
				qty : $.trim($("#qty_E").val()),
				failure_date : $.trim($("#failureDate_E").val())
			},
			dataType : "json",
			async : false,
			success : function(data) {
				$("#addEdit").hide();
				alert(data.msg.replace("error", ""));
				if (data.msg.indexOf("error") >= 0) {
					return false;
				}
				$("#grid_E").trigger("reloadGrid");
			}
		});
	}

	//Load script of Select2 and run this
	$(document).ready(function() {
		LoadSelect2Script("<s:url value="/resources/plugins/select2/select2.min.js" />", selectDefault);

		setDetailResults();
		var data = {
			eqp_model_id : "<c:out value="${element.eqp_model_id}" />", // 設備型態 
			eqp_type_id : "<c:out value="${element.eqp_type_id}" />" // 設備機型
		};
		$("#grid_E").setGridParam({datatype : "json", postData : data, page:1}).trigger("reloadGrid");

		showSet();

		// 異動大分類
		$("#catg1Select_E").change(onCatg1SelectChange);
		// 異動中分類
		$("#catg2Select_E").change(onCatg2SelectChange);
		// 異動小分類
		$("#catg3Select_E").change(onCatg3SelectChange);
		
		// 失效日期日曆
		$("#failureDate_E").datepicker({
			dateFormat : "yy/mm/dd",
			changeYear : true,
			changeMonth : true,
			showButtonPanel : true,
		});

		// 欄位檢核設定
		var fields = {};
		$("#addEdit").find(".require").each(function () {
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
			MSubmitData();
		});

		// 重置Button
		$("#cancelBtn").click(function(){
			$("#addEdit").trigger("reset");
			selectDefault();
			showSet();
			$("#grid_E").jqGrid("clearGridData");
		});

		// 查詢結果Table寬度控制
		$(window).resize(function() {
			$(window).unbind("onresize");
			$("#grid_E,.grid").setGridWidth($("#jQgrid").width() - 10);
			$(window).bind("onresize", this);
		}).trigger("resize");
	});
</script>