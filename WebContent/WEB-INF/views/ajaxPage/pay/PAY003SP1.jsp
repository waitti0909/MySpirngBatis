<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<!DOCTYPE html>
<html lang="en">
	<head>
		<title>請款作業 </title>
		<script src="<s:url value="/resources/plugins/bootstrapvalidator/bootstrapValidator.min.js" />"></script>
	</head>
	<body>
		<button type="button" id="saveBtn_SP1" class="btn btn-primary btn-label-left">
			<span><i class="fa fa-save"></i></span>
			<s:message code="button.save" text="儲存" />
		</button>
		<button type="button" id="cancelBtn_SP1" class="btn btn-primary btn-label-left">
			<span><i class="fa fa-times"></i></span>
			<s:message code="button.cancel" text="取消" />
		</button>
		<div class="row">
			<div class="col-xs-12 col-sm-12">
				<div class="ui-draggable ui-droppable">
					<div class="box-content">
						<div class="form-group">
							<div class="col-md-12">
								<div class="row">
									<label class="col-sm-2 control-label" style="width : 11%;">
										租約編號：
									</label>
									<div class="col-sm-2">
										<input id="apCashReqNo" type="hidden" name=apCashReqNo value="${sPcashReqNo}">
										<input type="text" id="apAmt_SP1" value="${sPcontractNo}" class="form-control" disabled="disabled" />
									</div>
									<label class="col-sm-2 control-label" style="width : 11%;">
										租約名稱：
									</label>
									<div class="col-sm-2">
										<input type="text" id="taxExclusiveAmt_SP1" value="${sPlsName}" class="form-control" disabled="disabled" />
									</div>
									<label class="col-sm-1 control-label" style="width : 14%;">
										本次請款金額：
									</label>
									<div class="col-sm-2">
										<input type="text" id="businessTax_SP1" value="<fmt:formatNumber value="${sPsumAmt}" pattern="#"/>" class="form-control" disabled="disabled" />
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<br />
		<button type="button" id="addBtn_SP1" class="btn btn-primary btn-label-left">
			<span><i class="fa fa-plus"></i></span>
			<s:message text="增加憑證" />
		</button>
		<!-- <button type="button" id="editBtn_SP1" class="btn btn-primary btn-label-left">
			<span><i class="fa fa-edit"></i></span>
			<s:message text="修改憑證" />
		</button>-->
		<button type="button" id="delBtn_SP1" class="btn btn-primary btn-label-left">
			<span><i class="fa fa-minus"></i></span>
			<s:message text="刪除憑證" />
		</button>
		<div id="ajaxSearchResult_SP1" class="col-xs-12">
			<!-- 固定id for window.resize start-->
			<div id="jQgrid_SP1">
				<table id="grid_SP1"></table>
				<div id="pager_SP1"></div>
			</div>
			<!-- 固定id for window.resize end-->
		</div>
	</body>
</html>
<script type="text/javascript">
	
	

	// 執行查詢
	function query() {
		<c:if test="${not(sPcashReqNo eq null)}">
		var data = {
			ap_no : $.trim("<c:out value="${sPcashReqNo}" />") // 驗收單號
		};
		$("#grid_SP1").setGridParam({datatype : "json", postData : data, page : 1}).trigger("reloadGrid");
		</c:if>
		<c:if test="${(sPcashReqNo eq null)}">
		if(creditList_Sp.length > 0 ){
			$.each(creditList_Sp, function(index, rosterEntry) {
				if(creditList_Sp[index]['sp_STATUS'] == "0" && creditList_Sp[index]['po_NO'] == "<c:out value="${sPcontractNo}" />"){
				jQuery("#grid_SP1").jqGrid('addRowData', index, rosterEntry);
				}
			});
		}
		</c:if>
	}
	

	// 設定查詢結果顯示內容
	function setResults() {
		$("#grid_SP1").jqGrid({
			datatype : "local",
			pager : "#pager_SP1",
			url : CONTEXT_URL + "/pay/pay003/sp1/queryPaymentRequestVoucher",
		   	colNames:["","工程驗收主檔單號", "工程驗收主檔PO", "憑證流水號", "憑證類別", "憑證類別", "憑証號碼", "憑證日期", "統一編號", "未稅金額", "稅額", "總額"],
		   	colModel:[
				{name:"sp_status".cf(), index:"sp_status".cf(), sortable:false, hidden:true,  editable:true},
				{name:"ap_no".cf(), index:"ap_no".cf(), sortable:false, hidden:true, editable:true},
				{name:"po_no".cf(), index:"po_no".cf(), sortable:false,  hidden:true,editable:true},
				{name:"seq_nbr".cf(), index:"seq_nbr".cf(), sortable:false, hidden:true, editable:true},
				{name:"voucher_type".cf(), index:"voucher_type".cf(), sortable:false, hidden:true, editable:true, editrules:{required:true}},
		   		{name:"voucher_type_desc".cf(), index:"voucher_type_desc".cf(), sortable:false, 
		   			editable:true,
		   			edittype:"select",
		   			editrules:{required:true},
		   			editoptions:{
		   				dataUrl : CONTEXT_URL + "/pay/pay003/search/voucherType",
		   				buildSelect: function (data) {
		   					var map = $.parseJSON(data);
		   					var s = "<select>";
		   					s += "<option value=''>-- 請選擇 --</option>";
		   					for (var key in map) {
		   						s += "<option value='" + key + "'>" + map[key] + "</option>";
		   					}
		   					s += "</select>";
		   					return s;
		   				}
		   			}
		   		},
		   		{name:"voucher_nbr".cf(), index:"voucher_nbr".cf(), sortable:false, editable:true, editrules:{required:true}},
		   		{name:"voucher_date".cf(), index:"voucher_date".cf(), sortable:false, formatter: "date", formatoptions:{newformat: "Y/m/d"},
		   			editable:true,
		   			editrules:{required:true, date:true},
		   			editoptions:{
		   				readonly:true,
		   				style:"cursor: not-allowed; background-color: #eee; opacity: 1;",
		   				dataInit:function(colId){$(colId).datepicker();}
		   			}
		   		},
		   		{name:"vat_number".cf(), index:"vat_number".cf(), sortable:false, editable:true, editrules:{required:true}},
		   		{name:"tax_exclusive_amt".cf(), index:"tax_exclusive_amt".cf(), sortable:false,
		   			editable:true,
		   			editrules:{required:true, integer:true, minValue:0},
		   			editoptions:{
		   				dataEvents:[{
			   				type: "change",
			   				fn: function(e) {
			   					var rowID = this.id.replace("tax_exclusive_amt".cf(), "");
			   					var taxExclusiveAmt = parseFloat(parseFloat(this.value).toFixed(0));
			   					if (!$.isNumeric(taxExclusiveAmt)) {
			   						taxExclusiveAmt = 0;
			   					}
			   					var businessTax = parseFloat((taxExclusiveAmt * 0.05).toFixed(0));
			   					$("#" + rowID + "business_tax".cf()).val(businessTax);
			   					$("#" + rowID + "ap_amt".cf()).val(taxExclusiveAmt + businessTax);
			   				}
			   			}]
		   			}
		   		},
		   		{name:"business_tax".cf(), index:"business_tax".cf(), sortable:false,
		   			editable:true,
		   			editrules:{required:true, integer:true, minValue:0},
			   		editoptions:{
			   			readonly:true,
			   			style:"cursor: not-allowed; background-color: #eee; opacity: 1;"
		   			}
		   		},
		   		{name:"ap_amt".cf(), index:"ap_amt".cf(), sortable:false,
		   			editable:true,
		   			editrules:{required:true, integer:true, minValue:0},
		   			editoptions:{
		   				readonly:true,
		   				style:"cursor: not-allowed; background-color: #eee; opacity: 1;"
		   			}
		   		}
			],	
		   	viewrecords : true,
		   	caption : "憑證清單",
			rownumbers : true,
			multiselect : true,
		   	onSelectRow : function(id) {
		    },
		    gridComplete : function() {
		    	var $this = $(this);
		    	var ids = $this.jqGrid("getDataIDs");
		    	if (ids.length >= 1000) {
		    		alert("若查詢結果筆數超過1000筆，將無法取得正確的憑證總額加總，來比對是否符合本次請款金額");
		    	} else {
		    		for (var id in ids) {
			    		$this.jqGrid("editRow", ids[id], true);
		    		}
		    	}
			},
			ondblClickRow : function(rowId) {
	        }
		});
	}

	// 儲存憑證
	function save() {
		if (validate()) {
			var id = "";
			var voucher_type = "";
			var voucher_nbr = "";
			var voucher_date = "";
			var vat_number = "";
			var tax_exclusive_amt = "";
			var spstatus = "";

			var ids = $("#grid_SP1").jqGrid("getDataIDs");
			for (var i = 0; i < ids.length; i++) {
				var row = $("#grid_SP1").jqGrid("getRowData", ids[i]);
				if($.trim(row["sp_status".cf()]) == '0'){
				voucherCredit_Sp = {
						'po_NO' : $.trim("<c:out value="${sPcontractNo}" />"), // 驗收PO
						'sp_STATUS' : $.trim(row["sp_status".cf()]),
						'voucher_TYPE' : $.trim(row["voucher_type".cf()]), // 憑證類別
						'voucher_TYPE_DESC' : $.trim(row["voucher_type".cf()]),
						'voucher_NBR' : $.trim(row["voucher_nbr".cf()]), // 憑証號碼
						'voucher_DATE' : $.trim(row["voucher_date".cf()]), // 憑證日期
						'vat_NUMBER' : $.trim(row["vat_number".cf()]), // 統一編號
						'tax_EXCLUSIVE_AMT' : $.trim(row["tax_exclusive_amt".cf()]),
						'business_TAX' : $.trim(row["business_tax".cf()]),
						'ap_AMT' : $.trim(row["ap_amt".cf()])// 未稅金額	
				}
				creditList_Sp.push(voucherCredit_Sp);
				}
				if (id != "") { id += ","; }
				if (spstatus != "") { spstatus += ","; }
				if (voucher_type != "") { voucher_type += ","; }
				if (voucher_nbr != "") { voucher_nbr += ","; }
				if (voucher_date != "") { voucher_date += ","; }
				if (vat_number != "") { vat_number += ","; }
				if (tax_exclusive_amt != "") { tax_exclusive_amt += ","; }
				id += $.trim(ids[i]);
				spstatus += $.trim(row["sp_status".cf()]);
				voucher_type += $.trim(row["voucher_type".cf()]);
				voucher_nbr += $.trim(row["voucher_nbr".cf()]);
				voucher_date += $.trim(row["voucher_date".cf()]);
				vat_number += $.trim(row["vat_number".cf()]);
				tax_exclusive_amt += $.trim(row["tax_exclusive_amt".cf()]);
			}
			<c:if test="${(sPcashReqNo eq null)}">
			voucherCredit = {
					'mst_po_no' : $.trim("<c:out value="${sPcontractNo}" />"), // 驗收PO
					'spstatus' : spstatus,
					'_id' : id, // jgGrid資料ID
					'voucher_TYPE' : voucher_type, // 憑證類別
					'voucher_NBR' : voucher_nbr, // 憑証號碼
					'voucherdate' : voucher_date, // 憑證日期
					'vat_NUMBER' : vat_number, // 統一編號
					'taxexclusiveamt' : tax_exclusive_amt // 未稅金額
				};
			creditList.push(voucherCredit);
			alert(creditList.length);
			$("#cancelBtn_SP1" ).prop("disabled",true);
			</c:if>
			<c:if test="${not(sPcashReqNo eq null)}">
			$.ajax({
				url : CONTEXT_URL + "/pay/pay003/sp1/creditAddEdit",
				type : "POST",
				data : {
					mst_ap_no : $.trim("<c:out value="${sPcashReqNo}" />"), // 驗收單號
					mst_po_no : $.trim("<c:out value="${sPcontractNo}" />"), // 驗收PO
					id : id, // jgGrid資料ID
					voucher_type : voucher_type, // 憑證類別
					voucher_nbr : voucher_nbr, // 憑証號碼
					voucher_date : voucher_date, // 憑證日期
					vat_number : vat_number, // 統一編號
					tax_exclusive_amt : tax_exclusive_amt // 未稅金額
				},
				dataType : "json",
				async : false,
				success : function(data) {
					$("#grid_SP1").trigger("reloadGrid");
					alert(data.msg);
				}
			});
			</c:if>
		}
		
	}

	// 取消新增、修改
	function cancel() {
		$("#grid_SP1").trigger("reloadGrid");
	}

	// 增加憑證
	var editingRowID = 1; // 新增資料的ROWID
	function enableAdd() {
		<c:if test="${not(sPcashReqNo eq null)}">
		$("#grid_SP1").jqGrid("addRow", {
			rowID : "new_row_" + (editingRowID++),
			initdata : {
				sp_STATUS : 0,
				tax_EXCLUSIVE_AMT : 0,
				business_TAX : 0,
				ap_AMT : 0
			}
		});
		</c:if>
		<c:if test="${(sPcashReqNo eq null)}">
		$("#grid_SP1").jqGrid("addRow", {
			rowID : "new_row_" + (addRowID++),
			initdata : {
				sp_STATUS : 0,
				tax_EXCLUSIVE_AMT : 0,
				business_TAX : 0,
				ap_AMT : 0
			}
		});
		
		</c:if>
	}

	// 刪除憑證
	function remove() {
		var id = $("#grid_SP1").jqGrid("getGridParam", "selrow");
		$("#grid_SP1").jqGrid("delRowData", id);
		
		if(creditList_Sp.length > 0 && creditList_Sp[id]['sp_STATUS'] == '0' && creditList_Sp[id] != undefined){
			creditList_Sp[id]['sp_STATUS'] = "1";
		}
	}
	// 資料檢核
	function validate() {
		// 使用jQgrid欄位檢核
		var validateFlag = true;
		var totApAmt = 0;
		var ids = $("#grid_SP1").jqGrid("getDataIDs");
		//alert(ids);
		for (var i = 0; i < ids.length; i++) {
			// 儲存憑證類別代碼
			$("#" + ids[i] + "_" + "voucher_type".cf()).val($("#" + ids[i] + "_" + "voucher_type_desc".cf()).val());

			$("#grid_SP1").jqGrid("saveRow", ids[i], false, "clientArray");
			// 判斷jQgrid檢核是否成功
			if ($("#" + ids[i] + "_" + "voucher_type_desc".cf()).prop("id") != undefined) {
   				validateFlag = false;
   				break;
   			}
			// 檢核特殊字元
			var row = $("#grid_SP1").jqGrid("getRowData", ids[i]);
   			var voucherNbr = $.trim(row["voucher_nbr".cf()]); // 憑證號碼
   			var vatNumber = $.trim(row["vat_number".cf()]); // 統一編號
   			if (voucherNbr.indexOf(",") >= 0) {
   				$.jgrid.info_dialog("錯誤", "憑證號碼: 不可包含字元[ , ]", $.jgrid.edit.bClose);
   				validateFlag = false;
   				break;
   			}
   			if (vatNumber.indexOf(",") >= 0) {
   				$.jgrid.info_dialog("錯誤", "統一編號: 不可包含字元[ , ]", $.jgrid.edit.bClose);
   				validateFlag = false;
   				break;
   			}
			// 總額加總
			totApAmt += parseFloat($.trim(row["ap_amt".cf()]));
		}
 		var msg = null;
		if (validateFlag && totApAmt != parseFloat("<c:out value="${sPsumAmt}" />")) {
			validateFlag = false;
			msg = "憑證總額加總 " + totApAmt + " 必須等於本次請款金額" + parseFloat("<c:out value="${sPsumAmt}" />") + "，請重新操作";
		}
		if (validateFlag == false) { // 若檢核有誤，則將欄位恢復至編輯狀態
			for (var i = 0; i < ids.length; i++) {
   				$("#grid_SP1").jqGrid("editRow", ids[i], true);
   			}
			if (msg != null) {
				alert(msg);
			}
   		}
		return validateFlag;
	}
	// Load script of Select2 and run this
	$(document).ready(function() {
		LoadSelect2Script("<s:url value="/resources/plugins/select2/select2.min.js" />", selectDefault);

		setResults();

		// 儲存按鈕
		$("#saveBtn_SP1").click(save);
		// 取消按鈕
		$("#cancelBtn_SP1").click(cancel);
		// 增加憑證按鈕
		$("#addBtn_SP1").click(enableAdd);
		// 刪除憑證按鈕
		$("#delBtn_SP1").click(remove);

		// 查詢結果Table寬度控制
		$(window).resize(function() {
			$(window).unbind("onresize");
			$("#grid_SP1,.grid").setGridWidth($("#jQgrid_SP1").width() - 10);
			$(window).bind("onresize", this);
		}).trigger("resize");
		$.jgrid.jqModal = $.extend($.jgrid.jqModal || {}, {
		    zIndex: 10302
		});
		
		
		query();
		
		
	});
</script>