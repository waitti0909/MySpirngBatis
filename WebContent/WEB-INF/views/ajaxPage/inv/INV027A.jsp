<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>


<!DOCTYPE html>
<html lang="en">
	<head>
		<title>BOM維護作業</title>
		<script src="<s:url value="/resources/plugins/bootstrapvalidator/bootstrapValidator.min.js" />"></script>
	</head>
	<body>
		<div class="box-header">
			<div class="box-name">
				<i class="fa fa-edit"></i>
				<span>BOM新增</span>
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
						<form id="addEdit" class="form-horizontal" method="post" enctype="multipart/form-data">
							<input type="hidden" id="saveFlag_A" value="N" />
							<input id="file_A" type="file" name="file" class="require" style="display: none;" accept=".xls, .xlsx" />
							<div class="row">
								<div class="col-sm-4" id="btnDiv">
									<button type="submit" id="saveBtn" class="btn btn-primary btn-label-left">
										<span><i class="fa fa-save"></i></span>
										<s:message code="button.save" text="儲存" />
									</button>
									<button type="button" id="uploadBtn" class="btn btn-primary btn-label-left">
										<span><i class="fa fa-file"></i></span>
										<s:message text="匯入BOM明細" />
									</button>
									<button type="button" id="cancelBtn" class="btn btn-default btn-label-left">
										<span><i class="fa fa-undo txt-danger"></i></span>
										<s:message code="button.reset" text="重置" />
									</button>
								</div>
							</div>
							<div class="form-group">
								<div class="row">
									<label class="col-sm-2 control-label"><span class="s">*
									</span>設備型態：</label>
									<div class="col-sm-2">
										<select id="eqpModelSelect_A" name="eqp_model_id" class="populate placeholder require">
											<option value="">-- 請選擇 --</option>
											<c:forEach items="${sessionScope.inv027_EqpModelList}" var="var">
												<option value="${var.EQP_MODEL_ID}">${var.EQP_MODEL}</option>
											</c:forEach>
										</select>
									</div>
									<label class="col-sm-2 control-label"><span class="s">*
									</span>設備機型：</label>
									<div class="col-sm-2">
										<select id="eqpTypeSelect_A" name="eqp_type_id" class="populate placeholder require">
											<option value="">-- 請選擇 --</option>
										</select>
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
			<div id="ajaxSearchResult_A" class="col-xs-8">
				<!-- 固定id for window.resize start-->
				<div id="jQgrid_A">
					<table id="grid_A"></table>
					<div id="pager_A"></div>
				</div>
				<!-- 固定id for window.resize end-->
			</div>
		</div>
	</body>
</html>
<script type="text/javascript">

	// 開啟下拉式選單搜尋文字功能
	function selectDefault() {
		$("#addEdit").find("select").select2();
	}

	// 準備上傳檔案設定
	var files;
	function prepareUpload() {
		files = event.target.files;
	}

	// 檔案上傳
	function uploadFiles() {
		event.stopPropagation(); // Stop stuff happening
		event.preventDefault(); // Totally stop stuff happening 
		var duplicateFile = false;
		var filename = $.trim($("#file_A").val());
		var selname = filename.substr(filename.indexOf(".") + 1,filename.length);
		if (filename == "") {
			alert("請選擇檔案");
			return false;
		} else if (selname != "xls" && selname != "xlsx") {
			alert("檔案類型須為 xls,xlsx");
			$("#file_A").val("");
			return false;
		} else {
			duplicateFile = true;
		}

		if (duplicateFile) {
			var data = new FormData();
			$.each(files, function(key, value) {
				data.append(key, value);
			});
			$.ajax({
				url : CONTEXT_URL + "/inv/inv027/uploadExcel",
				type : "post",
				data : data,
				cache : false,
				dataType : "json",
				processData : false, // Don't process the files
				contentType : false, // Set content type to false as jQuery will tell the server its a query string request
				success : function(data, textStatus, jqXHR) {
					if(data.success){
						$("#saveFlag_A").val("Y");
						$("#eqpTypeSelect_A").change();
					} else {
						$("#saveFlag_A").val("N");
					}
					var data = {flag : "Y"};
					$("#grid_A").setGridParam({datatype : "json", postData : data, page:1}).trigger("reloadGrid");
				}
			});
		}
	}

	// 異動設備型態
	function onEqpModelChange() {
		$("#eqpTypeSelect_A").html("");
		$("<option value=''>-- 請選擇 --</option>").appendTo("#eqpTypeSelect_A");
		$("#eqpTypeSelect_A").select2();
		$("#eqpTypeSelect_A").change();
		
		if($("#eqpModelSelect_A").val() == "") {
			return false;
		}
		$.ajax({
			url : CONTEXT_URL + "/inv/inv027/search/eqpType",
			type : "POST",
			data : {
				eqpModelId : $("#eqpModelSelect_A").val()
			},
			async : false,
			success : function(map) {
				for (var key in map) {
					$("<option value='" + key + "'>" + map[key] + "</option>").appendTo("#eqpTypeSelect_A");
				}
			}
		});
	}

	// 設定BOM明細匯入結果
	function setResults() {
		$("#grid_A").jqGrid({
			datatype : "local",
			pager : "#pager_A",
			url : CONTEXT_URL + "/inv/inv027/queryExcel",
		   	colNames:["大分類代碼", "大分類", "中分類代碼", "中分類", "小分類代碼", "小分類", "料號", "品名", "數量", "檢核結果代碼", "檢核結果"],
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
				{name:"check_status", index:"check_status", sortable:false, hidden:true},
				{name:"check_status_name", index:"check_status_name", sortable:false}
			],	
		   	viewrecords : true,
		   	caption : "BOM匯入明細",
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

	// 存檔動作
	function MSubmitData() {
		$.ajax({
			url : CONTEXT_URL + "/inv/inv027/insert",
			type : "POST",
			data : JSON.stringify(form2js($("#addEdit").attr("id"), ".",
					true, null)),
			contentType : "application/json",
			dataType : "json",
			async : false,
			success : function(data) {
				alert(data.msg.replace("error", ""));
				if (data.msg.indexOf("error") >= 0) {
					$("#grid_A").trigger("reloadGrid");
					return false;
				}
				parent.$.fancybox.close();
			}
		});
	}

	//Load script of Select2 and run this
	$(document).ready(function() {
		LoadSelect2Script("<s:url value="/resources/plugins/select2/select2.min.js" />", selectDefault);

		// 欄位檢核設定
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
			if ($("#saveFlag_A").val() == "Y") {
				$("#file_A").removeAttr("name");
				MSubmitData();
			} else {
				var records = $("#grid_A").jqGrid("getGridParam", "records");
				if (parseFloat(records) == 0) {
					alert("需匯入BOM明細");
				} else {
					alert("匯入資料有誤，無法進行存檔");
				}
			}
		});

		// 異動設備型態
		$("#eqpModelSelect_A").change(onEqpModelChange);

		// 匯入BOM明細Button
		$("#uploadBtn").click(function() {
			if ($.trim($("#file_A").val()) == "") {
				$("#file_A").click();
			}
		});

		// 匯入報表Button
		$("#file_A").change(function () {
			if ($.trim($("#file_A").val()) != "") {
				prepareUpload();
				uploadFiles();
				$("#file_A").val("");
			}
		});
		setResults(); // 報表匯入結果顯示

		// 重置Button
		$("#cancelBtn").click(function() {
			$("#addEdit").trigger("reset");
			selectDefault();
			$("#grid_A").jqGrid("clearGridData");
		});

		// 查詢結果Table寬度控制
		$(window).resize(function() {
			$(window).unbind("onresize");
			$("#grid_A,.grid").setGridWidth($("#jQgrid").width() - 10);
			$(window).bind("onresize", this);
		}).trigger("resize");
	});
</script>