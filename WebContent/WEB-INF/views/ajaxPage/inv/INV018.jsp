<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<title>庫存狀態異動作業</title>
		<script src="<s:url value="/resources/plugins/bootstrapvalidator/bootstrapValidator.min.js" />"></script>
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
							<span>庫存狀態異動作業</span>
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
								異動單號 :
							</label>
							<div class="col-sm-3">
								<input type="text" id="invTranNo" name="inv_tran_no" class="form-control" placeholder="關鍵字查詢" />
							</div>
							<label class="col-sm-2 control-label">
								狀態 :
							</label>
							<div class="col-sm-3">
								<select id="invTranAuditStatus" name="inv_tran_audit_status" class="populate placeholder">
									<option value="">-- 請選擇 --</option>
									<c:forEach items="${sessionScope.inv018_statusList}" var="var">
										<option value="${var.CODE}">${var.NAME}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label"><span class="s">*</span>
								倉庫 :
							</label>
							<div class="col-sm-3">
								<select id="whIdSelect" name="whId" class="populate placeholder require">
									<option value="">-- 請選擇 --</option>
									<c:forEach items="${sessionScope.inv018_warehouseList}" var="var">
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

	// 查詢
	function query() {
		if ($.trim($("#whIdSelect").val()) == "") {
			alert("需輸入倉庫");
			return false;
		}
		var data = {
			inv_tran_no : $.trim($("#invTranNo").val()), // 異動單號
			inv_tran_audit_status : $.trim($("#invTranAuditStatus").val()), // 狀態
			wh_id : $.trim($("#whIdSelect").val()) // 倉庫
		};
		$("#grid").setGridParam({datatype : "json",	postData : data, page:1}).trigger("reloadGrid");
	}

	// 修改
	function update() {
		// 取得被選取列
		var ids = $("#grid").jqGrid("getGridParam", "selarrrow");
		// 是否選取
		if (ids.length != 1) {
			alert("請選擇一筆資料編輯");
			return false;
		}
		var data = $("#grid").jqGrid("getRowData", ids);
		if (data.inv_tran_audit_status != "1" && data.inv_tran_audit_status != "4") {
			alert("只有進度為草稿或駁回才可修改");
			return false;
		}
		editAddPage("edit", data.inv_tran_no);
	}

	// 新增、修改
	function editAddPage(btnType, invTranNo) {
		var href = CONTEXT_URL;
		if (btnType == "view") {
			href += "/inv/inv018/view";
		} else {
			href += "/inv/inv018/addEdit";
		}
		
		$.fancybox.open({
			href : href,
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
						inv_tran_no : invTranNo
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
				if ($.trim($("#whIdSelect").val()) != "") {
					$("#btn_search").trigger("click");
					$("#grid").trigger("reloadGrid");
				}
			}
		});
	}

	// 執行狀態異動
	function statTran() {
		var ids = $("#grid").jqGrid("getGridParam", "selarrrow");
		if (ids.length < 1) {
			alert("請至少選擇一筆資料");
			return false;
		}
		var invTranNos = "";
		for(var id in ids) {
			var row = $("#grid").jqGrid("getRowData", ids[id]);
			if (row.inv_tran_audit_status != "3") {
				alert("只允許狀態為(核准)的資料才可執行");
				return false;
			}
			if (invTranNos != "") {
				invTranNos += ",";
			}
			invTranNos += $.trim(row.inv_tran_no);
		}
		$.ajax({
			url : CONTEXT_URL + "/inv/inv018/changeStatus",
			type : "POST",
			data : {
				inv_tran_nos : invTranNos
			},
			dataType : "json",
			async : false,
			success : function(data) {
				alert(data.msg.replace("error", ""));
				if (data.msg.indexOf("error") >= 0) {
					return false;
				}
				$("#grid").trigger("reloadGrid");
			}
		});
	}

	// 刪除
	function cancelApply() {
		var ids = $("#grid").jqGrid("getGridParam", "selarrrow");
		if (ids.length < 1) {
			alert("請至少選擇一筆資料");
			return false;
		}
		var invTranNos = "";
		for(var id in ids) {
			var row = $("#grid").jqGrid("getRowData", ids[id]);
			if (row.inv_tran_audit_status != "1" && row.inv_tran_audit_status != "4") {
				alert("只有進度為草稿或駁回才可刪除");
				return false;
			}
			if (invTranNos != "") {
				invTranNos += ",";
			}
			invTranNos += $.trim(row.inv_tran_no);
		}
		$.ajax({
			url : CONTEXT_URL + "/inv/inv018/cancelApply",
			type : "POST",
			data : {
				inv_tran_nos : invTranNos
			},
			dataType : "json",
			async : false,
			success : function(data) {
				alert(data.msg.replace("error", ""));
				if (data.msg.indexOf("error") >= 0) {
					return false;
				}
				$("#grid").trigger("reloadGrid");
			}
		});
	}

	// 設定查詢結果顯示內容
	function setMasterResults() {
		$("#grid").jqGrid({
			datatype : "local",
			pager : "#pager",
			url : CONTEXT_URL + "/inv/inv018/query",
		   	colNames:["異動單號", "狀態代碼", "狀態", "倉庫"],
		   	colModel:[
		   		{name:"inv_tran_no", index:"inv_tran_no", sortable:false},
		   		{name:"inv_tran_audit_status", index:"inv_tran_audit_status", sortable:false, hidden:true},
		   		{name:"inv_tran_audit_status_dscr", index:"inv_tran_audit_status_dscr", sortable:false},
		   		{name:"wh_name", index:"wh_name", sortable:false}
			],	
		   	viewrecords : true,
		   	caption : "庫存狀態主檔",
			rownumbers : true,
			multiselect : true,
		   	onSelectRow : function(id) {
		   		var ids = $("#grid").jqGrid("getGridParam", "selrow");
		   		var row = $("#grid").jqGrid("getRowData", ids);
		   		var data = {
		   			inv_tran_no : row.inv_tran_no // 異動單號
	   			};
	   			$("#grid2").setGridParam({datatype : "json", postData : data, page:1}).trigger("reloadGrid");
		    },
		    gridComplete : function() {
		    	$("#grid2").jqGrid("clearGridData", true);
			},
			ondblClickRow : function(rowId) {
				var rowData = jQuery(this).getRowData(rowId);
				var invTranNo = null;
	            $.each(rowData, function (i, item) {
	            	if (i == "inv_tran_no") {
	            		invTranNo = item;
	            	}
	            });
	            editAddPage("view", invTranNo);
	        }
		});
	}

	// 設定主檔明細結果顯示內容
	function setDetailResults() {
		$("#grid2").jqGrid({
			datatype : "local",
			pager : "#pager2",
			url : CONTEXT_URL + "/inv/inv018/queryDetail",
		   	colNames:["料號", "品名", "廠商序號", "貼標號碼", "異動前狀態", "異動後狀態", "數量", "異動原因", "異動日期", "異動人員"],
		   	colModel:[
		   		{name:"mat_no", index:"mat_no", sortable:false},
		   		{name:"mat_name", index:"mat_name", sortable:false},
		   		{name:"ven_sn", index:"ven_sn", sortable:false},
		   		{name:"tag_no", index:"tag_no", sortable:false},
		   		{name:"old_mat_status_dscr", index:"old_mat_status_dscr", sortable:false},
		   		{name:"new_mat_status_dscr", index:"new_mat_status_dscr", sortable:false},
		   		{name:"tran_qty", index:"tran_qty", sortable:false},
		   		{name:"tran_reason_dscr", index:"tran_reason_dscr", sortable:false},
		   		{name:"tran_date", index:"tran_date", sortable:false, formatter: "date", formatoptions:{newformat: "Y/m/d H:i"}},
		   		{name:"tran_user_name", index:"tran_user_name", sortable:false}
			],	
		   	viewrecords : true,
		   	caption : "庫存狀態明細",
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

	// Load script of Select2 and run this	
	$(document).ready(function() {
		LoadSelect2Script("<s:url value="/resources/plugins/select2/select2.min.js" />", selectDefault);

		// 欄位檢核設定
		var fields = {};
		$(".require").each(function () {
			var name = $(this).prop("name");
			var group = "." + $(this).parent().prop("class");
			fields[name] = {
				group : group,
				validators : {
					notEmpty : {
						message : "不可為空!"
					}
				}
			};
		});
		$("#searchFrom").bootstrapValidator({
			fields : fields
		}).on("success.form.bv", function(e) {
			e.preventDefault();
			query();
		});

		// 查詢Button
		$("#btn_search").click(function () {
			$("#searchFrom").submit();
		});
		setMasterResults(); // 設定主檔查詢結果顯示內容
		setDetailResults(); // 設定明細查詢結果顯示內容

		// 新增Button
		$("#btn_add").click(function() {
			editAddPage("add", null);
		});

		// 修改Button
		$("#btn_edit").click(update);

		// 執行狀態異動Button
		$("#btn_stat_tran").click(statTran);

		// 刪除Button
		$("#btn_delete").click(cancelApply);

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
			$("#grid2,.grid").setGridWidth($("#jQgrid").width() - 10);
			$(window).bind("onresize", this);
		}).trigger("resize");
	});
</script>