<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>分攤_資源互換/共電</title>
	<script type="text/javascript">
	    // 初始載入
		$(document).ready(function() {
			mountButEvent();
			prepareDay();// 預設日期設定
			mountGrid();// 載入表格
		});
	    
		// 掛載表格
		function mountGrid(){
			$("#grid").jqGrid({
				datatype : "local",
				pager : '#pager',
				autowidth:'true',
				url : CONTEXT_URL + "/pay/pay013/search/",
				colNames : ['站台編號', '類別', '分攤基準(租金/電費)', '請款金額','分攤站台編號','分攤比率(%)','金額','','','','','' ],
				colModel : [
				    {name : 'siteId', index : 'siteId', sortable : false},
				    {name : 'locTypeDscr', index : 'locTypeDscr', sortable : false},
				    {name : 'alocItemDscr', index : 'alocItemDscr', sortable : false},
				    {name : 'taxExclusiveAmt', index : 'taxExclusiveAmt', sortable : false},
				    {name : 'exchSiteId', index : 'exchSiteId', sortable : false},
				    {name : 'alocRatio', index : 'alocRatio', sortable : false},
				    {name : 'exchAmt', index : 'exchAmt', sortable : false},
				    {name : 'paymentReqNo', index : 'paymentReqNo', sortable : false,hidden:true},
				    {name : 'itemNo', index : 'itemNo', sortable : false,hidden:true},
				    {name : 'seqNbr', index : 'seqNbr', sortable : false,hidden:true},
				    {name : 'locationId', index : 'locationId', sortable : false,hidden:true},
				    {name : 'alocItem', index : 'alocItem', sortable : false,hidden:true}],
				caption : "分攤清單",
				rownumbers : true,
				onSelectRow : function() {},
				gridComplete : function() {},
				ondblClickRow: function() {}
			});
			// 設定表格寬度
			$(window).resize(function() {
				$(window).unbind("onresize");
				$("#grid,.grid").setGridWidth($("#jQgrid").width());
				$(window).bind("onresize", this);
			}).trigger('resize');
		}
		
		// 載入條件
		function selectData() {
			var data = {
				yearMonth : $("#yearMonth").val(),
				appUser : $("#appUser").val(),
				appDate : $("#appDate").val()
			};
			$("#grid").setGridParam({datatype:"json", postData:data}).trigger("reloadGrid");
		}
		
		// 輸入條件檢核
		function check() {
			var yearMonth = $("#yearMonth").val();
			var appUserName = $("#appUserName").val();
			var appDate = $("#appDate").val();
			var flagCk;
			if ((yearMonth == null || yearMonth == "")) {
				alert('分攤年月不得為空值');
				return false;
			}
			if ((appUserName == null || appUserName == "")) {
				alert('申請人不得為空值');
				return false;
			}
			if ((appDate == null || appDate == "")) {
				alert('申請日期不得為空值');
				return false;
			}
			// 檢查年月格式
			flagCk = YMCheck(yearMonth);
			if (!flagCk){
				alert('分攤年月格式錯誤');
				return false;
			}
			return true;
		}

		// 掛載表格Event
		function mountButEvent() {
			// 分攤處理
			$("#btn_repartition").click(function() {
				if (check()) {
					resetGridTitle();// 重置GridTitle
					selectData();
				}
			});

			// 存檔
			$('#btn_save').click(function() {
				// 檢查是否已執行分攤處理
				var ids = jQuery("#grid").jqGrid('getDataIDs');
				if (ids.length == 0){
					alert('尚未執行分攤處理,請重新操作!');
					return false;
				}
				var dataConut = getCount();
				var rptNo;
				// 若筆數>0,代表所指定分攤年月已有產生過分攤資訊資料
				if (dataConut > 0){
					var yearMonth = $("#yearMonth").val();
					if (confirm("分攤年月:"+ yearMonth +"已有資料,是否要重新產生?")) {
						// 刪除現有分攤資料
						delRepartition(yearMonth);
						// 產生分攤單號
						rptNo=getRptNo();
						// 寫入申請分攤資料
						inserRepartition(rptNo);
						// show本次分攤單號
	                    resetGridTitle(rptNo);
				     } else {
	                    // 重置頁面
					    $("#searchFrom").trigger('reset');
						$("#grid").jqGrid().clearGridData();
						prepareDay();
					}
				}else if (dataConut == 0){
					// 產生分攤單號
					rptNo=getRptNo();
					// 寫入分攤資料
					inserRepartition(rptNo);
					// show本次分攤單號
					resetGridTitle(rptNo);
				}
			});

			// 重置按鈕
			$('#btn_reset').click(function() {
				resetGridTitle();// 重置GridTitle
				$("#searchFrom").trigger('reset');
				$("#grid").jqGrid().clearGridData();
				prepareDay();
			});
		}

		//================FUNCTION======================
		// GridTitle顯示控制
		function resetGridTitle(rptNo){
			var $grid = $('#grid');
			if (rptNo != "" && rptNo != undefined){
				$grid.jqGrid('setCaption', "分攤清單"+"<font color='#CC0000'>&nbsp;&nbsp;&nbsp;&nbsp;本次分攤單號 :"+rptNo+"</font>");
			}else{
				$grid.jqGrid('setCaption', "分攤清單");
			}
		}
		
	    // 取得已攤分資料筆數 
		function getCount() {
			var ids = jQuery("#grid").jqGrid('getDataIDs');
			var count = "";
			// 有資料才執行
			if (ids.length != 0){
				var data = {
					yearMonth : $("#yearMonth").val()
				};
				
				$.ajax({
					url : CONTEXT_URL + "/pay/pay013/getCount/",
					data : data,
					type : "POST",
					dataType : 'json',
					async: false,
					success : function(data) {
						if (data.success) {
							count = data.msg;
						}
					}
				});
			}
			return count;
	    }
		
		// 刪除TB_PAY_REPARTITION現有分攤資料
		function delRepartition(yearMonth){
			var data = {
					yearMonth : yearMonth
				};
			$.ajax({
					url : CONTEXT_URL + "/pay/pay013/delData/",
					data : data,
					type : "POST",
					dataType : 'json',
					success : function(data) {
					},
					error : function(data) {
						alert(data.msg);
					}
			});
		}
		
		// 產生分攤單號
		function getRptNo() {
			var rptNo = "";
			var appDate = $("#appDate").val().substr(0,4)+$("#appDate").val().substr(5,2)+$("#appDate").val().substr(8,2);// 取得申請日期YYYYMMDD
			var data = {appDate : appDate};
			$.ajax({
				url : CONTEXT_URL + "/pay/pay013/getRptNo/",
				data : data,
				type : "POST",
				dataType : 'json',
				async : false,
				success : function(data) {
					if (data.success) {
						rptNo = data.msg;
					}
				}
			});
			return rptNo;
		}
		
		// 寫入分攤資料
		function inserRepartition(rptNo){
			var sucFlag = false;
			var ids = jQuery("#grid").jqGrid('getDataIDs');
			var repartitionNo = "";
			for (var i = 0; i < ids.length; i++) {
				var rowId = ids[i];
				var rowData = jQuery('#grid').jqGrid('getRowData', rowId);
				var expType = "";
				if (rowData.alocItem == "R"){
					expType = "RER";
				} else if(rowData.alocItem == "E"){
					expType = "ELR";
				} else {
					expType = "";
				}
				var data = {
					repartitionNo :rptNo,
					paymentReqNo :rowData.paymentReqNo,
					paymentReqItemNo : rowData.itemNo,
					paymentSeqNbr : rowData.seqNbr,
					locationId : rowData.locationId,
					yearMonth : $("#yearMonth").val(),
					repartitionAmt : rowData.exchAmt,
					siteId : rowData.siteId,
					expType : expType,
					appUser : $("#appUser").val(),
					appDate : $("#appDate").val()
				};
				
				$.ajax({
						url : CONTEXT_URL + "/pay/pay013/saveData/",
						data : data,
						type : "POST",
						dataType : 'json',
						async : false,// 同步
						success : function(data) {
							if(data.success){
								repartitionNo = data.msg;
								sucFlag = true;
							}
						},
						error : function(data) {
							alert(data.msg);
						}
				});
			}
			// 處理完成顯示成功訊息
			if (sucFlag){
				alert($("#yearMonth").val()+"的分攤作業(分攤單號:"+repartitionNo+")處理成功!");
			}
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
			// 分攤年月:預設帶本月份
			$('#yearMonth').prop(
					"value",
					Today.getFullYear()
							+ (addZero(Today.getMonth() + 1)));
			// 申請日期:預設帶今天
			$('#appDate').prop(
					"value",
					Today.getFullYear() + "/"
							+ (addZero(Today.getMonth() + 1)) + "/"
							+ (addZero(Today.getDate())));
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
						<i class="fa fa-search"></i> <span>分攤_資源互換/共電</span>
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
									<td><label class="col-sm-12 control-label"><span class="s">* </span>分攤年月 :</label></td>
									<td>
										<div class="col-sm-6">
											<input id="yearMonth" type="text" value=""
												class="form-control" name="yearMonth" maxlength="6">
										</div> (格式:YYYYMM)
									</td>
								</tr>
								<tr>
									<td><label class="col-sm-12 control-label"><span class="s">* </span>申請人 :</label></td>
									<td>
										<div class="col-sm-6">
											<input id="appUserName" type="text" class="form-control" 
											   name="appUserName" disabled value="${appUserName}"> 
											<input id="appUser" type="hidden" class="form-control"
												name="appUser" value="${appUser}">
										</div>
									</td>
									<td><label class="col-sm-12 control-label"><span class="s">* </span>申請日期 :</label></td>
									<td>
										<div class="col-sm-6">
											<input id="appDate" type="text" value="" class="form-control"
												name="appDate" readonly="readonly">
										</div>
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