<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<title> 電費請款維護 電費請款查詢</title>
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
							<span> 電費請款維護 電費請款查詢</span>
						</div>
						<div class="box-icons">
							<a class="collapse-link"> <i class="fa fa-chevron-up"></i> </a>
							<a class="expand-link"> <i class="fa fa-expand"></i> </a>
						</div>
						<div class="no-move"></div>
					</div>
				</div>
				<div id="detailContent" class="box-content">
					<form id="searchFrom" class="form-horizontal bootstrap-validator-form" role="form" novalidate="novalidate">
						<div class="form-group">
							<label class="col-sm-2 control-label">
								<span class="s">* </span>維運區 :
							</label>
							<div class="col-sm-3">
								<select id="domainSelect" class="populate placeholder">
									<option value="">-- 請選擇 --</option>
									<c:forEach items="${sessionScope.pay003_orgDomainList}" var="var">
										<option value="${var.ID}">${var.NAME}</option>
									</c:forEach>
								</select>
							</div>
							<label class="col-sm-2 control-label">
								<span class="s">* </span>處理類別 :
							</label>
							<div class="col-sm-3">
								<select id="processSelect" class="populate placeholder">
									<option value="">-- 請選擇 --</option>
									<c:forEach items="${sessionScope.pay003_processList}" var="var">
										<option value="${var.LOOKUP_CODE}" >${var.LOOKUP_CODE_DESC}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							
							<label class="col-sm-2 control-label">
								付款週期：
							</label>
							<div class="col-sm-3">
								<input type="text" id="paymentPeriod" class="form-control" placeholder="關鍵字查詢" />
							</div>
							<label class="col-sm-2 control-label">
								請款單號：
							</label>
							<div class="col-sm-3">
								<input type="text" id="cashReqNo" class="form-control" placeholder="關鍵字查詢" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">
								<span class="s">* </span>申請起日：
							</label>
							<div class="col-sm-3">
								<input type="text" id="crTimeS" value="<fmt:formatDate value="${cr_time_s}" pattern="yyyy/MM/dd" />" readonly="readonly" class="form-control" placeholder="申請起日" />
							</div>
							<label class="col-sm-2 control-label">
								<span class="s">* </span>申請迄日：
							</label>
							<div class="col-sm-3">
								<input type="text" id="crTimeE" value="<fmt:formatDate value="${cr_time_e}" pattern="yyyy/MM/dd" />" readonly="readonly" class="form-control" placeholder="申請迄日" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">
								<span class="s">*</span> 申請狀態：
							</label>
							<div class="col-sm-4">
								<c:forEach items="${sessionScope.pay003_statusList}" var="var">
									<div class="checkbox-inline">
										<label>
											<input type="checkbox" name="status" value="${var.LOOKUP_CODE}" checked="checked" />${var.LOOKUP_CODE_DESC}<i class="fa fa-square-o"></i>
										</label>
									</div>
								</c:forEach>
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
			<br/>
		<div class="clearfix">&nbsp;</div>		
		<div class="col-xs-12" id="signHistoryDiv" style="display:none">
		<div class="box">
			<div class="box-header">
				<div class="box-name">
					<i class="fa fa-table"></i>
					<span>簽核紀錄</span>
				</div>				
			</div>
			<div class="box-content no-padding" id="signHistory">
			</div>
		</div>
	</div>
					
					
			
		</div>
	</body>
</html>

<script type="text/javascript">
var _delcheck_ = false;
	// 開啟下拉式選單搜尋文字功能
	function selectDefault() {
		$("#searchFrom").find("select").select2();
	}

	// 查詢
	function query() {
		var status = $.trim($("input:checkbox:checked[name='status']").map(function() { return $(this).val(); }).get());
		if (status == "") {
			alert("申請狀態至少需勾選一項");
			return false;
		}
		if($("#domainSelect").val()=="" || $("#domainSelect").val()== null){
			alert('維運區 不得為空值,請重新操作!');
			$("#paymentPeriod").focus();
			return false;
		};
		if($("#processSelect").val()=="" || $("#processSelect").val()== null){
			alert('處裡類別 不得為空值,請重新操作!');
			$("#paymentPeriod").focus();
			return false;
		};
		if($("#crTimeS").val()=="" || $("#crTimeS").val()== null){
			alert('申請起日 不得為空值,請重新操作!');
			$("#paymentPeriod").focus();
			return false;
		};
		if($("#crTimeE").val()=="" || $("#crTimeE").val()== null){
			alert('申請迄日 不得為空值,請重新操作!');
			$("#paymentPeriod").focus();
			return false;
		};
		
		if($("#paymentPeriod").val()!="" && !gIsDigit($("#paymentPeriod").val())){
				alert('付款週期 必需為數值');
				$("#paymentPeriod").focus();
				return false;
			};
		if(!dateCheck($("#crTimeS").val(), $("#crTimeE").val(), "申請起迄")){
			return false;
		};
		var data = {
			domain : $.trim($("#domainSelect").val()), // 維運區
			processType : $.trim($("#processSelect").val()), // 業者
			paymentPeriod : $.trim($("#paymentPeriod").val()), // PO
			cashReqNo : $.trim($("#cashReqNo").val()), // PO
			appStartDate : $.trim($("#crTimeS").val()), // 申請起日
			appEndDate : $.trim($("#crTimeE").val()), // 申請迄日
			status : status // 申請狀態
		};
		$("#grid").setGridParam({datatype : "json",	postData : data, page : 1}).trigger("reloadGrid");
		$("#signHistoryDiv").hide();
	}

	// 修改
	function update() {
		// 取得被選取列
		var ids = $("#grid").jqGrid("getGridParam", "selrow");
		// 是否選取
		if (ids == undefined) {
			alert("請選擇一筆資料編輯");
			return false;
		}
		var rowData = $("#grid").jqGrid("getRowData", ids);
		if(rowData.status == "A" ){
			editPage(rowData);
		}else{
			alert('申請狀態必須為：待審核');
			return false;
		};
		
	}
	

	// 設定查詢結果顯示內容
	function setResults() {
		$("#grid").jqGrid({
			datatype : "local",
			pager : "#pager",
			url : CONTEXT_URL + "/pay/pay003/query",
		   	colNames:["請款單號", "筆數", "憑證筆數", "", "申請狀態", "請款金額", "營業稅", "所得稅", "健保補充費用", "應付金額","", "申請人", "申請日期", "(會計)審核人", "(會計)審核日期", "滯留金", "", "", ""],
		   	colModel:[
		   		{name:"cash_REQ_NO".cf(), index:"cash_REQ_NO".cf(), sortable:false},
		   		{name:"pr_cnt".cf(), index:"pr_cnt".cf(), sortable:false},
		   		{name:"VOUCHER_CNT".cf(), index:"VOUCHER_CNT".cf(), sortable:false},
		   		{name:"status".cf(), index:"status".cf(), sortable:false, hidden:true},
		   		{name:"statusDscr", index:"statusDscr", sortable:false},
		   		{name:"sumTaxExclusiveTotalAmt", index:"sumTaxExclusiveTotalAmt", sortable:false},
		   		{name:"s_total_business_tax".cf(), index:"s_total_business_tax".cf(), sortable:false},
		   		{name:"S_TOTAL_INCOME_TAX".cf(), index:"S_TOTAL_INCOME_TAX".cf(), sortable:false},
		   		{name:"s_total_NHI_amt".cf(), index:"s_total_NHI_amt".cf(), sortable:false},
		   		{name:"amount_due".cf(), index:"amount_due".cf(), sortable:false},
		   		{name:"app_USER".cf(), index:"app_USER".cf(), sortable:false, hidden:true},
		   		{name:"app_USER_DSCR".cf(), index:"app_USER_DSCR".cf(), sortable:false},
		   		{name:"app_DATE".cf(), index:"app_DATE".cf(), sortable:false, formatter: jqGridDataFormat},
		   		{name:"account_APPROVAL_USER_DSCR", index:"account_APPROVAL_USER_DSCR", sortable:false},
		   		{name:"account_APPROVAL_DATE", index:"account_APPROVAL_DATE", sortable:false, formatter: jqGridDataFormat},
		   		{name:"S_EXCLUSIVE_BUSINESS_TAX".cf(), index:"S_EXCLUSIVE_BUSINESS_TAX".cf(), sortable:false},
		   		{name:"domain".cf(), index:"domain".cf(), sortable:false, hidden:true},
		   		{name:"process_TYPE".cf(), index:"process_TYPE".cf(), sortable:false, hidden:true},
		   		{name:"year_MONTH".cf(), index:"year_MONTH".cf(), sortable:false, hidden:true}
			],	
		   	viewrecords : true,
		   	caption : "用電請款清單",
			rownumbers : true,
			multiselect : false,
		   	onSelectRow : function(id) {
		   		var rowData = jQuery(this).getRowData(id);
				getDetail(rowData);
		    },
		    gridComplete : function() {
			}
		});
	}
	//for jqGrid 的 data format
	function jqGridDataFormat(cellvalue, options, rowObject){
			if (cellvalue != null && cellvalue != "") {
   			var stDate = new Date(cellvalue);
			return stDate.getFullYear() + "/" + addZero(stDate.getMonth() + 1) + "/" + addZero(stDate.getDate()) ;
			}else {
				return "";
			}
	};
	//時間左補零滿兩位
	function addZero(i) {
		if (i < 10) {
			i = "0" + i;
		}
		return i;
	}

	function getDtl(rowData) {
		//alert("processType : "+rowData.process_TYPE);
		$.fancybox.open({
			href : CONTEXT_URL + "/pay/pay003/searchDtl",
			type : 'ajax',
 			width : $(window).width(),
 			height : $(window).height(),
 			autoSize : false,
 			padding : [ 0, 0, 0, 0 ],
			scrolling : 'yes',
			helpers : {
				overlay : {
					closeClick : false
				},
				title : {
					type : 'float'
				}
			},ajax : {
				data : {
					cashReqNo : rowData.cash_REQ_NO
				},
				type : "POST",
				error : function(jqXHR, testStatus, errorThrown) {
					alert(jqXHR);
				},
				async : false
			},
			openEffect : 'elastic',
			closeEffect : 'fade',
			beforeClose : function() {
			},
			afterClose : function() {
			}
		});
	}

	// 開啟新增/修改頁面
	function editPage(rowData) {
		$.fancybox.open({
			href : CONTEXT_URL + "/pay/pay003/edit",
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
			title : "電費請款修改",
			openEffect : "elastic",
			closeEffect : "fade",
			ajax : {
					data : {
						cashReqNo : rowData.cash_REQ_NO, //請款單號
						domain : rowData.domain, // 維運區
						processType : rowData.process_TYPE, // 處理類別
						appUser : rowData.app_USER,
						appUserName : rowData.app_USER_DSCR, //申請人
						appDate   : rowData.app_DATE, //申請日期
						yearMonth : rowData.year_MONTH //請款年月
					},
					type : "POST",
					error : function(jqXHR, testStatus, errorThrown) {
						alert(jqXHR);
					},
					async : false
			},
			beforeClose : function() {
				if(!_delcheck_){
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
	
	function openPage(page, ajax){
		$.fancybox.open({
			href : CONTEXT_URL + "/pay/pay003/"+page,
			type : 'ajax',
			width : $(window).width(),
			height : $(window).height(),
			autoSize : false,
			padding : [ 0, 0, 0, 0 ],
			scrolling : 'yes',
			helpers : {
				overlay : {
					closeClick : false
				},
				title : {
					type : 'float'
				}
			},
			ajax : ajax,
			title : "電費請款",
			openEffect : 'elastic',
			closeEffect : 'fade',
			afterClose : function() {

			}
		});	
	}
	function getDetail(rowData) {
		$.ajax({
			url:CONTEXT_URL + "/commom/signHistory",
			type : 'POST',
			dataType : "html",
			data:{
				"processType" :  "PayElectricBill",
				"applyNo" : rowData.cash_REQ_NO
			},
			async : false,
			success : function(data) {
				$("#signHistoryDiv").show();
				$("#signHistory").html(data);
			}
		});
	};
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

		// 申請日期日曆
		$("#crTimeS").datepicker();
		$("#crTimeE").datepicker();

		// 查詢Button
		$("#btn_search").click(query);
		setResults(); // 設定查詢結果顯示內容

		// 修改Button
		$("#btn_edit").click(update);

		// 新增Button
		$("#btn_add").click(function() {
			openPage("addPage", {});	
		});

		// 重置Button
		$("#btn_reset").click(function(){
			$("#searchFrom").trigger("reset");
			selectDefault();
			var data = {ap_NO : "0"};
			$("#grid").setGridParam({datatype:"json", postData:data, mtype:"POST", page:1}).jqGrid().trigger("reloadGrid");
			$("#signHistoryDiv").hide();
		});
		
		$('#btn_detail').click(function() {
			var id = $('#grid').jqGrid('getGridParam', 'selrow');
			if (id == null) {
				alert("必須點選一筆資料,請重新操作!");
				return;
			};	
			var rowData = $("#grid").jqGrid('getRowData', id);		
			getDtl(rowData);
		});

		// 查詢結果Table寬度控制
		$(window).resize(function() {
			$(window).unbind("onresize");
			$("#grid,.grid").setGridWidth($("#jQgrid").width() - 10);
			$(window).bind("onresize", this);
		}).trigger("resize");
	});
</script>