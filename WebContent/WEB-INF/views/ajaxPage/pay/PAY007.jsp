<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>


<!DOCTYPE html>
<html lang="en">
<head>
<title>會計審核作業</title>
	<script type="text/javascript">
	    // 初始載入
		$(document).ready(function() {
			mountButEvent();
			// 預設下拉選單格式
			LoadSelect2Script("<s:url value="/resources/plugins/select2/select2.min.js" />", selectDefault);
			prepareDay();// 申請日期起迄 預設日期
			mountGrid();// 載入表格
		});
	    
		// 掛載表格
		function mountGrid(){
			$("#grid").jqGrid({
				datatype : "local",
				pager : '#pager',
				autowidth:'true',
				shrinkToFit: true,
				url : CONTEXT_URL + "/pay/pay007/search/",
				colNames : [ '請款單號','筆數', '憑證筆數', '請款金額', '營業稅','所得稅','健保補充費用','應付金額','申請人','申請日期','(會計)審核人員','(會計)審核日期','滯納金','駁回原因','說明','','','','',''],
				colModel : [
					{name : 'cash_REQ_NO',index : 'cash_REQ_NO', align : 'center',sortable : false,width:350},
				    {name : 'pr_CNT',index : 'pr_CNT', align : 'center',sortable : false,width:120},
				    {name : 'voucher_CNT',index : 'voucher_CNT' , align : 'center',sortable : false,width:160},
				    {name : 's_TAX_EXCLUSIVE_TOTAL_AMT',index : 's_TAX_EXCLUSIVE_TOTAL_AMT' , align : 'center',sortable : false,width:160},
				    {name : 's_TOTAL_BUSINESS_TAX',index : 's_TOTAL_BUSINESS_TAX' , align : 'center',sortable : false},
				    {name : 's_TOTAL_INCOME_TAX',index : 's_TOTAL_INCOME_TAX' , align : 'center',sortable : false},
				    {name : 's_TOTAL_NHI_AMT',index : 's_TOTAL_NHI_AMT' , align : 'center' ,width:220 ,sortable : false,width:200},
				    {name : 'amount_DUE',index : 'amount_DUE' , align : 'center',sortable : false,width:160},
				    {name : 'app_USER_DSCR',index : 'app_USER_DSCR' , align : 'center',width:210,sortable : false},// 秀中文
				    {name : 'app_DATE',index : 'app_DATE' , align : 'center',width:210,sortable : false,
				    	formatter: function(cellvalue, options, rowObject){
				   			if (cellvalue != null && cellvalue != "") {
					   			var stDate = new Date(cellvalue);
								return stDate.getFullYear() + "/" + addZero((stDate.getMonth() + 1)) + "/" + addZero(stDate.getDate()) ;
				   			}else {
				   				return "";
				   			}
			   		}},
				    {name : 'account_APPROVAL_USER_DSCR',index : 'account_APPROVAL_USER_DSCR' , align : 'center' ,width:240 ,sortable : false,width:270},// 秀中文
				    {name : 'account_APPROVAL_DATE',index : 'account_APPROVAL_DATE' , align : 'center' ,width:240 ,sortable : false,width:270,
				    	formatter: function(cellvalue, options, rowObject){
				   			if (cellvalue != null && cellvalue != "") {
					   			var stDate = new Date(cellvalue);
								return stDate.getFullYear() + "/" + addZero((stDate.getMonth() + 1)) + "/" + addZero(stDate.getDate()) ;
				   			}else {
				   				return "";
				   			}
			   		}},
				    {name : 's_FORFEIT_AMT',index : 's_FORFEIT_AMT' , align : 'center',sortable : false},// 滯納金
		            {name : 'rejectReasonSelect',index : 'rejectReasonSelect',align : 'center',width:280,sortable : false, editable: true,edittype:"select",width:300} ,// 駁回原因下拉選單
				    {name : 'reject_MEMO',index : 'reject_MEMO',align : 'center',width:250,sortable : false, editable: true,edittype:"text",editoptions:{size:"13",maxlength:"13"}},// 說明
				    {name : 'account_APPROVAL_USER',index : 'account_APPROVAL_USER',align : 'center',sortable : false,hidden:true},
				    {name : 'reject_REASON',index : 'reject_REASON',align : 'center',sortable : false,hidden:true},
				    {name : 'process_TYPE',index : 'process_TYPE',align : 'center',sortable : false,hidden:true},
				    {name : 'app_USER',index : 'app_USER',align : 'center',sortable : false,hidden:true},
				    {name : 'payment_PERIOD',index : 'payment_PERIOD',align : 'center',sortable : false,hidden:true}],
				caption : "請款單號清單",
				multiselect : true,
				rownumbers : true,
				onSelectRow : function(rowId) {
				},
				gridComplete : function() {
					var ids = jQuery("#grid").jqGrid('getDataIDs');
						for (var i = 0; i < ids.length; i++) {
						    var rowId = ids[i];
						    var rowData = jQuery('#grid').jqGrid ('getRowData', rowId);
							var rejectReason = rowData.reject_REASON;
							var accountApprovalUser = rowData.account_APPROVAL_USER;
							
							// 當ACCOUNT_APPROVAL_USER&A.REJECT_REASON有值，值直接show在畫面上，不讓user對該欄位做更改動作(取代disable效果)
							if((rejectReason != "")&&(accountApprovalUser != "")){
								var reSetRowData = $("#jqGrid_id").jqGrid('getRowData', rowId); 
								$.extend(reSetRowData, {"rejectReasonSelect":getRejectReason('getDesc',rejectReason)});// 取得駁回原因中文說明
								$('#grid').jqGrid('setRowData', rowId, reSetRowData);// 將取得的中文說明reSet回該欄位
								
							} else {
								$('#grid').jqGrid('setColProp', 'rejectReasonSelect', { editoptions: { value: getRejectReason('getSelectOne',rejectReason)}});
								jQuery('#grid').jqGrid('editRow',rowId,true);
							}
						}
						// 處理直接show選擇reject_REASON值
						for (var i = 0; i < ids.length; i++) {
						    var rowId = ids[i];
						    var rowData = jQuery('#grid').jqGrid ('getRowData', rowId);
							var rejectReason = rowData.reject_REASON;
							var accountApprovalUser = rowData.account_APPROVAL_USER;
							var rejectReasonDoSelect;
							if((rejectReason != "")&&(accountApprovalUser == "")){
								rejectReasonDoSelect = "#"+rowId+"_rejectReasonSelect";
								$(rejectReasonDoSelect).val(rejectReason);// 直接將該筆資料顯示於下拉選單
							}
						}
					 
					
						
				},
				ondblClickRow: function(rowId) { // double clicking a jqgrid row
		            var rowData = jQuery(this).getRowData(rowId);
		    		var cashReqNo = rowData.cash_REQ_NO;// 請款單號
		    		var processType = rowData.process_TYPE;
		    		var processTypeVal = '';
		    		if (processType != null && processType != ""){
		    			processTypeVal = processType.substr(0,1);
		    			var ajax = '';
		    			if (processTypeVal == "E"){
		    				 ajax = {
	    							data : {cashReqNo : cashReqNo,
				    						   domain : '',
				    						   processType : processType,
				    						   appUserName : '',
				    						   appDate   : '',
				    						   yearMonth : $("#yearMonth").val()},
	    						type : "POST",
	    						error : function(jqXHR, testStatus, errorThrown) {
	    							alert(jqXHR);
	    						},
	    						async : false
	    					};
		    			} else {
		    				 ajax = {
	    							data : {cashReqNo : cashReqNo},
	    						type : "POST",
	    						error : function(jqXHR, testStatus, errorThrown) {
	    							alert(jqXHR);
	    						},
	    						async : false
	    					};
		    			}
		    			if (processTypeVal == "R") {
		    				openPage("/pay/pay001/searchDtl", ajax , "租/押金請款");
		    			} else if (processTypeVal == "E") {
		    				openPage("/pay/pay003/searchDtl", ajax , "電費請款明細");
		    			} else if (processTypeVal == "L") {
		    				openPage("/pay/pay004/searchDtl", ajax , "專線請款清單");// Call PAY004D.jsp尚未完成,待修改
		    			} else {//processTypeVal == "M"
		    				openPage("/pay/pay005/dtlPage", ajax , "明細資料");
		    			}
		    		}
		        }
			});
			$(window).resize(function() {
				$(window).unbind("onresize");
				$("#grid,.grid").setGridWidth($("#jQgrid").width()+10);
				$(window).bind("onresize", this);
			}).trigger('resize');
		}
		
		
		// 載入查詢條件
		function selectData() {
			var data = {
				domain : $("#domainSelect").find("option:selected").val(),
				processType : $("#processTypeSelect").find("option:selected").val(),
				appUser : $("#appUserSelect").find("option:selected").val(),
				appStartDate : $("#appStartDate").val(),
				appEndDate : $("#appEndDate").val(),
				cashReqNo : $("#cashReqNo").val(),
				yearMonth : $("#yearMonth").val()
			};
			$("#grid").setGridParam({datatype:"json", postData:data}).trigger("reloadGrid");
		}
		
		// 輸入條件檢核
		function check() {
			var domain = $("#domainSelect").find("option:selected").val();
		//	var appUser = $("#appUserSelect").find("option:selected").val();
			var processType = $("#processTypeSelect").find("option:selected").val();
			var appStartDate = $("#appStartDate").val();
			var appEndDate = $("#appEndDate").val();
			var yearMonth = $("#yearMonth").val();
			if ((domain == null || domain == "")) {
				alert('維運區不得為空值');
				return false;
			}
		/* 	if ((appUser == null || appUser == "")) {
				alert('申請人不得為空值');
				return false;
			} */
			if ((processType == null || processType == "")) {
				alert('處理類別不得為空值');
				return false;
			}
			// 日期檢核:結束日不能小於開始日
			dateCheck(appStartDate, appEndDate, "申請日期");
			// 檢查請款年月格式
			flagCk = YMCheck(yearMonth);
			if (!flagCk){
				alert('請款年月格式錯誤');
				return false;
			}
			return true;
		}

		// 掛載表格Event
		function mountButEvent() {
			// 查詢
			$('#btn_search').click(function() {
				if(check()){
					selectData();
				}
			});

			// 駁回鈕
			$('#btn_reject').click(function() {
				if(selectCheck('reject')){
					updateData('E','駁回變更儲存');
				}
			});
			
			// 通過鈕
			$('#btn_pass').click(function() {
				if(selectCheck('pass')){
					updateData('C','通過變更儲存');
				}
			});
			
			// 請款匯出鈕
			$('#btn_pr_export').click(function() {
				if(selectCheck('export')){
					updateData('F','請款匯出');
				}
			});
			
			// 重置按鈕
			$('#btn_reset').click(
					function() {
						$("#searchFrom").trigger('reset');
						$("#grid").jqGrid().clearGridData();
						$("#domainSelect").select2("val", "");
						$("#processTypeSelect").select2("val", "");
						$("#appUserSelect").select2("val", "");
						$("#siteId").prop("value", "");
						prepareDay();
			});
		}
		
		//  維運區連動申請人
		$("#domainSelect").change(onDomainSelectChange);
		//================FUNCTION======================
		// 依據不同process_type值導頁至不同頁面
		function openPage(pageUrl, ajax, title){
			$.fancybox.open({
				href : CONTEXT_URL + pageUrl,
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
				title : title,
				openEffect : 'elastic',
				closeEffect : 'fade',
				afterClose : function() {
					selectData();// 重新查詢
				}
			});	
		}	

		// 更新資料
		function updateData(status,statusDesc){
			var selr = jQuery('#grid').jqGrid('getGridParam', 'selarrrow');
			var updateArray =[];
			for (var i = 0; i < selr.length; i++) {
				var rowData = jQuery('#grid').jqGrid('getRowData',selr[i]);
				var cashReqNo = rowData.cash_REQ_NO;// 請款單號
				var rejectReason = rowData.reject_REASON;// 駁回原因代碼
				//var processType = rowData.process_TYPE;// 駁回原因代碼
				var accountApprovalUser = rowData.account_APPROVAL_USER;// (會計)審核人員
				var rejectReasonSelect = "#"+selr[i]+"_rejectReasonSelect";// 駁回原因下拉選單
				var rejectMemo = "#"+selr[i]+"_reject_MEMO";// 說明textbox
				var rejectReasonChinese = $("#"+selr[i]+"_rejectReasonSelect :selected").text();
				if ((rejectReason != "")&&(accountApprovalUser != "")){
					// 當account_approval_user&reject_reason有值,則取得原來該筆資料的駁回原因代碼及說明
					rejectReasonSelect=rejectReason;
					rejectMemo=rowData.reject_MEMO;
				} else {
					// 取得user選擇的駁回原因代碼及輸入說明
					rejectReasonSelect=$(rejectReasonSelect).find("option:selected").val();
					rejectMemo=$(rejectMemo).val();
				}
				
				var appUser = rowData.app_USER;
				var appDate = rowData.app_DATE;
				var payMentPeriod= rowData.payment_PERIOD;
				var TbPayCashRequirement = '';
				// 更新駁回、通過資料
				if (status == 'E' || status == 'C'){
					 TbPayCashRequirement = {
							"cash_REQ_NO" : cashReqNo,
							"status" : status,
							"reject_REASON" : rejectReasonSelect + "_" +rejectReasonChinese,			
							"reject_MEMO" : rejectMemo,
							"process_TYPE" : $("#processTypeSelect").find("option:selected").val(),
							"year_MONTH" : $("#yearMonth").val(),
							"app_USER" : appUser,
							"app_DATE" : appDate.replace(/\//g,"-"),
							"payment_PERIOD" : payMentPeriod
					};

				}
				// 更新請款匯出資料
				else if (status == 'F'){
					 TbPayCashRequirement = {
							"cash_REQ_NO" : cashReqNo,
							"process_TYPE" : $("#processTypeSelect").find("option:selected").val(),
							"status" : status
					};
				}
				updateArray.push(TbPayCashRequirement);
			}
			$.ajax({
					url : CONTEXT_URL + "/pay/pay007/updateData/",
					data : JSON.stringify(updateArray),
					contentType : "application/json",
					type : "POST",
					dataType : 'json',
					success : function(data) {
						alert(statusDesc+"成功!");
						selectData();// 重新執行查詢
					},
					error : function(data) {
						alert(data.msg);
					}
			});	
	   }
			
		// 勾選資料檢核
		function selectCheck(btnCheck) {
			var selr = jQuery('#grid').jqGrid('getGridParam','selarrrow');
			// 判斷是否勾選
			if(selr.length==0){
				alert("請至少勾選一筆資料,請重新操作!");
				return false;
			}
			if (!selr.length == 0) {
				for (var i = 0; i < selr.length; i++) {
				    var rowData = jQuery('#grid').jqGrid ('getRowData', selr[i]);
					var rejectReason = rowData.reject_REASON;
					var cashReqNo = rowData.cash_REQ_NO;// 請款單號
					var accountApprovalUser = rowData.account_APPROVAL_USER;// (會計)審核人員
					var accountApprovalDate = rowData.account_APPROVAL_DATE;// (會計)審核日期
					var rejectReasonSelect = "#"+selr[i]+"_rejectReasonSelect";// 駁回原因下拉選單
					var rejectMemo = "#"+selr[i]+"_reject_MEMO";// 說明textbox
					if ((rejectReason != "")&&(accountApprovalUser != "")){
						// 當account_approval_user&reject_reason有值,則取得原來該筆資料的駁回原因代碼及說明
						rejectReasonSelect=rejectReason;
						rejectMemo=rowData.reject_MEMO;
					} else {
						// 取得user選擇的駁回原因代碼及輸入說明
						rejectReasonSelect=$(rejectReasonSelect).find("option:selected").val();
						rejectMemo=$(rejectMemo).val();
					}
					if (btnCheck == 'reject'){
						if (rejectReasonSelect == ""){
							alert("請款單號"+cashReqNo+",駁回原因必須選擇,請重新操作!");
							return false;
						}
						if (rejectReasonSelect == '00'){
							alert("請款單號"+cashReqNo+",駁回原因不可選'會計審核通過',請重新操作!");
							return false;
						}
					}
					if (btnCheck == 'pass'){
						if (rejectReasonSelect == "" || rejectReasonSelect!= '00'){
							alert("請款單號"+cashReqNo+",駁回原因需選'會計審核通過',請重新操作!");
							return false;
						}
					}
					if (btnCheck == 'export'){
						if (accountApprovalUser == "" || accountApprovalDate == "" || rejectReasonSelect!= '00'){
							alert("必須選擇會計審核通過的資料,請重新操作!");
							return false;
						}
					}
				}
			}
			return true;
		}

		// 取得grid內reject_reason 選單內容OR取得中文說明
		function getRejectReason(getStatus,rejectReason) {
			var returnValue="";
			var data = {
				  rejectReason : rejectReason
			};
			$.ajax({
				url : CONTEXT_URL + "/pay/pay007/lookupByType",
				data : data,
				type : "POST",
				dataType : 'json',
				async : false,
				// 此抓法為selectOne直接show取得的lookup_CODE_DESC,且有"請選擇"及其他選項可選擇
				success : function(data) {
					if (data.success) {
							for (var i = 0; i < data.rows.length; i++) {
								if (getStatus == 'getSelect' || getStatus == 'getSelectOne'){
									if (i == 0) {
										returnValue += "" + ":" + "--請選擇--" + ";";
									}
									if (i < data.rows.length - 1) {
										returnValue += data.rows[i].lookup_CODE + ":"
												+ data.rows[i].lookup_CODE_DESC + ";";
									} else {
										returnValue += data.rows[i].lookup_CODE + ":"
												+ data.rows[i].lookup_CODE_DESC;
									}
								} else {// getDesc
									if (data.rows[i].lookup_CODE == rejectReason){
										returnValue = data.rows[i].lookup_CODE_DESC;
									}
								}
							}
					}
				}
			});
			return returnValue;
		}
		
		// 維運區連動申請人
		function onDomainSelectChange() {
			$("#appUserSelect").html("");
			$("<option value=''>--請選擇--</option>").appendTo("#appUserSelect");
			$("#appUserSelect").select2();
			// 若維運區選擇空值則不取申請人資料
			if ($("#domainSelect").val() == "") {
				return false;
			}
			var data = {
				domain : $("#domainSelect").find("option:selected").prop("value")
			};
			$.ajax({
				url : CONTEXT_URL + "/ajax/pay/public/appUserDomain/",
				data : data,
				type : "POST",
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						if (data.rows.length > 0) {
							for (var i = 0; i < data.rows.length; i++) {
								$("#appUserSelect").append(
										"<option value="+data.rows[i].psn_NO+">"
												+ data.rows[i].chn_NM
												+ "</option>");
							}
						}
					}
				}
			});
		}
		
		// 開啟下拉式選單搜尋文字功能
		function selectDefault() {
			$("#searchFrom").find("select").select2();
		}
		
		// 時間左補零滿兩位
		function addZero(i) {
			if (i < 10) {
				i = "0" + i;
			}
			return i;
		}
		
	    // 預設日期設定
		function prepareDay() {
			var Today = new Date();
			var TodayLess = (new Date());
			TodayLess.setDate(TodayLess.getDate() - 7);
			$('#appStartDate').prop(
					"value",
					TodayLess.getFullYear() + "/" + addZero((TodayLess.getMonth() + 1))
							+ "/" +addZero((TodayLess.getDate())));
			$('#appEndDate').prop(
					"value",
					Today.getFullYear() + "/" + addZero((Today.getMonth() + 1)) + "/"
							+ addZero((Today.getDate())));
			// 請款年月:預設帶本月份
			$('#yearMonth').prop("value",
					Today.getFullYear() + addZero((Today.getMonth() + 1)));
			// 申請日期起始日曆
			$('#appStartDate').datepicker();
			// 申請日期結束日曆
			$('#appEndDate').datepicker();
		}
	    
		// 檢查年月是否正確
		function YMCheck(ym){
		    var tmp;
		    var num = "0123456789";
		    var nab = ym.length-1; 
			var flag = true;
            var yyyy = parseFloat(ym.substr(0,4));
            var mm = parseFloat(ym.substr(4,2));
			if(ym.length < 6 ){
				 flag =  false;
			}else{
				// 檢查是否為數字
				for (var i=0;i<=nab;i++){
			        tmp=ym.substr(i,1);
			        if (num.indexOf(tmp) == -1) {
			        	flag =  false;
			        }
			    }
			}
            // 檢查年月
			if(yyyy <= 0 || yyyy > 9999){
				 flag =  false;
			}
			if(mm <= 0 ||mm > 12){
				 flag =  false;
			}
			return flag;
		}
	</script>
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
						<i class="fa fa-search"></i> <span>會計審核作業</span>
					</div>
					<div class="box-icons">
						<a class="collapse-link"> <i class="fa fa-chevron-up"></i></a>
						<a class="expand-link"> <i class="fa fa-expand"></i></a>
					</div>
					<div class="no-move"></div>
				</div>

				<div class="box-content">
					<form id="searchFrom" class="form-horizontal bootstrap-validator-form" role="form" novalidate="novalidate">
						<div class="form-group">
							<table style="width: 100%">
								<tr>
									<td><label class="col-sm-12 control-label"><span class="s">* </span>維運區 :</label></td>
									<td>
										<div class="col-sm-6">
												<select id="domainSelect" name="domainSelect"  class="populate placeholder">
												<option value="" selected>--請選擇--</option>
												<c:forEach items="${domainSelect}" var="domain">
													<option value="${domain.ID}">${domain.NAME}</option>
												</c:forEach>
											</select>
										</div>
									</td>
									<td><label class="col-sm-12 control-label"><span class="s">* </span>處理類別 :</label></td>
									<td>
										<div class="col-sm-6">
												<select id="processTypeSelect" name="processTypeSelect"  class="populate placeholder">
												<option value="" selected>--請選擇--</option>
												<c:forEach items="${processTypeSelect}" var="process">
													<option value="${process.LOOKUP_CODE}">${process.LOOKUP_CODE_DESC}</option>
												</c:forEach>
											</select>
										</div>
									</td>
								</tr>
							    <tr>
									<td><div class="clearfix">&nbsp;</div></td>
								</tr>
								<tr>
									<td><label class="col-sm-12 control-label">申請人 :</label></td>
									<td>
										<div class="col-sm-6">
											<select id="appUserSelect" name="appUserSelect" class="populate placeholder">
												<option value="" selected>--請選擇--</option>
											</select>
										</div>
									</td>
									<td><label class="col-sm-12 control-label">請款單號 :</label></td>
									<td>
										<div class="col-sm-6">
										<input id="cashReqNo" type="text" value="" 
										   class="form-control" name="cashReqNo">
										</div>
									</td>
								</tr>
								<tr>
									<td><div class="clearfix">&nbsp;</div></td>
								</tr>
								<tr>
									<td><label class="col-sm-12 control-label"> <span class="s">* </span>申請起日 :</label></td>
									<td>
										<div class="col-sm-6">
											<input id="appStartDate" type="text" value="" 
											   class="form-control" name="appStartDate" maxlength="10" readonly="readonly">
										</div>
									</td>
									<td><label class="col-sm-12 control-label"> <span class="s">* </span>申請迄日 :</label></td>
									<td>
										<div class="col-sm-6">
											<input id="appEndDate" type="text" value="" 
											   class="form-control" name="appEndDate" maxlength="10" readonly="readonly">
										</div>
									</td>
								</tr>
							    <tr>
									<td><div class="clearfix">&nbsp;</div></td>
								</tr>
								<tr>
									<td><label class="col-sm-12 control-label">請款年月 :</label></td>
									<td>
										<div class="col-sm-6">
											<input id="yearMonth" type="text" value=""
											   class="form-control" name="yearMonth" maxlength="6">
										</div> (格式:YYYYMM)
									</td>
								</tr>
							</table>
						</div>
					</form>
				</div>
			</div>
		</div>

		<div id="ajaxSearchResult" class="col-xs-12">
			<!-- 固定id for window.resize start-->
			<div id="jQgrid" align="center">
				<table id="grid"></table>
				<div id="pager"></div>
			</div>
			<!-- 固定id for window.resize end-->
		</div>
	</div>
</body>
</html>