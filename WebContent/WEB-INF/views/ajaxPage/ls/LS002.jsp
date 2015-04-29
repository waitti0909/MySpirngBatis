<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title></title>
<script type="text/javascript">

$(document).ready(function() {
	LoadSelect2Script('<s:url value="/resources/plugins/select2/select2.min.js" />', selectDefault);
	
	
	// 開啟下拉式選單搜尋文字功能
	function selectDefault() {
		$("#searchForm").find("select").select2();
	}
	
	//查詢
	$('#btn_search').click(function() {
		if($.trim($('#qLeaseNo').val())==''){
			alert("請填寫合約編號!");
		}else{
			$("#grid").jqGrid('clearGridData');
			var data = {
				lsNo : $.trim($("#qLeaseNo").val()),//合約型態
				type : $("#qType").val()
			}
			$("#grid").setGridParam({datatype:"json", postData:data, mtype: 'POST',page:1}).jqGrid().trigger("reloadGrid");
		}
	});
	
	$("#grid").jqGrid({
		datatype: "local",
		pager: '#pager',
		url : CONTEXT_URL + "/ls/LS002/searchByCond",
		colNames:['版本','申請流水號','合約編號','合約型態','申請狀態代碼','申請狀態','合約狀態','增補協議','項目','洽談人員','建立人員','建立日期'],
		colModel:[
			{name:'ls_VER',index:'ls_VER',width:90, sortable: false, hidden : true},
			{name:'app_SEQ',index:'app_SEQ',width:90, sortable: false, hidden : true},
			{name:'ls_NO',index:'ls_NO',width:90, sortable: false},
			{name:'ls_TYPE_NAME',index:'ls_TYPE_NAME',width:60, sortable: false},
			{name:'app_STATUS',index:'app_STATUS',width:60, sortable: false, hidden : true},
			{name:'app_STATUS_NAME',index:'app_STATUS_NAME',width:60, sortable: false},
			{name:'ls_STATUS_NAME',index:'ls_STATUS_NAME', width:60,sortable: false},
			{name:'add_TYPE_NAME',index:'add_TYPE_NAME', width:80,sortable: false},
			{name:'add_ITEM',index:'add_ITEM', width:100,sortable: false},
			{name:'deal_USER_NAME',index:'deal_USER_NAME', width:60,sortable: false},
			{name:'app_USER_NAME',index:'app_USER_NAME', width:60,sortable: false},
			{name:'cr_TIME',index:'cr_TIME', width:60,sortable: false, formatter: "date", formatoptions:{newformat: "Y/m/d"}}
		],	
		viewrecords: true,
		caption:"合約清單",
		rownumbers:true,
		multiselect: false,
		onSelectRow: function(id,status){ 							
		},
		gridComplete: function(){
			var ids = jQuery("#grid").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				var cl = ids[i];  // cl:jqgrid的行數，非資料真正的HEADER_ID
				$("#grid").jqGrid('getRowData', cl);
			}	
		},
		ondblClickRow: function(rowId) { //double clicking a jqgrid row
			var rowData = jQuery(this).getRowData(rowId);
			var whId = null;
            $.each(rowData, function (i, item) {
            	if (i == "ls_NO") {
            		lsNo = item;
            	}
            	if(i == "ls_VER"){
            		lsVer = item;
            	}
            	if(i == "app_SEQ"){
            		appSeq = item;
            	}
            });
            toLs002M($("#qType").val(),appSeq,lsNo,lsVer,true);
		}
	});
	
	//查詢表單大小設定
	$(window).resize(function() {
		$(window).unbind("onresize");
		$("#grid,.grid").setGridWidth($("#jQgrid").width() - 10);
		$(window).bind("onresize", this);
	}).trigger('resize');
	
	
	//重置按鈕
	$("#btn_reset").click(function() {
		$("#searchForm").trigger('reset');
		$("#grid").jqGrid().clearGridData();
	});	
	
	// 修改Button
	$("#btn_edit").click(function() {
		var ids = $("#grid").jqGrid("getGridParam", "selrow");
		// 是否選取
		if (ids == undefined) {
			alert("請選擇一筆資料編輯");
			return false;
		} else {
			var data = $("#grid").jqGrid("getRowData", ids);
			//狀態為草稿 or 駁回才能修改 0:草稿，1:送審中，2:核可，9:駁回
			if($("#qType").val() == 1){
				if (data.app_STATUS == "1" && data.ls_TYPE_NAME == "增補協議" && data.add_ITEM == "租金起算日") {
					toLs002M($("#qType").val(),data.app_SEQ,data.ls_NO,data.ls_VER,false);
				} else {
					alert('此單無法作租金起算日修改');
				}
			}else{
				toLs002M($("#qType").val(),data.app_SEQ,data.ls_NO,data.ls_VER,false);
			}
		}
	});
	
	
	//開啟ls002M頁
	function toLs002M(qType,appSeq,lsNo,lsVer,flag){
		
		var data = {
				qType : qType,
				appSeq : appSeq,
				lsNo : lsNo,
				lsVer: lsVer
			};
		$.ajax({			
			url : CONTEXT_URL + "/ls/LS002/confirmLs002M",		
			data: data,
			type : "POST",
			dataType : 'json',
			success : function(data) {
				if(!data.success){	
					alert(data.msg);
				}else{
					$.fancybox.open({
			 			href : CONTEXT_URL+"/ls/LS002M",
			 			type : 'ajax',
			 			width : $(window).width(),
			 			height : $(window).width(),
			 			autoSize : false,
			 			padding : [0,0,0,0],
			 			scrolling : 'yes',
			 			helpers:{
			 				overlay:{closeClick:false},
			 				title : {type : 'float'}
			 			},
			 			title : "修改",
			 			openEffect : 'elastic',
			 			closeEffect : 'fade',
			 			ajax : {
							data : {
								qType : qType,
								appSeq : appSeq,
								lsNo : lsNo,
								lsVer: lsVer,
								flag : flag
							},
							type : "POST",
							async : false
					    },
			 	        afterClose : function() {
			 			}
			 		});	
				}
			}
		});
		
		
		
		
		
	}
	
});

</script>
</head>
<body>
	<div class="row">
		<div id="breadcrumb" class="col-md-12">
			<ol class="breadcrumb">
				<!-- button -->
				<c:import url="/auth/role/menu/func/${currentMenuId}" />
				<!-- button end-->
			</ol>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-12 col-sm-12">
			<div class="box ui-draggable ui-droppable">
				<div class="box-header">
					<div class="box-name">
						<i class="fa fa-search"></i> <span>合約請款維護 合約請款查詢</span>
					</div>
					<div class="box-icons">
						<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
						</a> <a class="expand-link"> <i class="fa fa-expand"></i>
						</a>
					</div>
					<div class="no-move"></div>
				</div>
				<div class="box-content">
						<form role="form"
							class="form-horizontal bootstrap-validator-form"
							novalidate="novalidate" id="searchForm">
							<table style="width: 100%">
								<tr>
									<td nowrap="nowrap" width="10%"><label
										class="col-sm-12 control-label"><span class="s">*</span>類型 :</label></td>
									<td width="40%">
										<div class="col-sm-8">
											<select id="qType" class="populate placeholder" name="qType">
												<option value="1">租金起算日</option>
												<option value="2">房屋稅籍編號</option>
											</select>
										</div>
									</td>

									<td nowrap="nowrap" width="10%"><label
										class="col-sm-12 control-label"><span class="s">*</span>合約編號 :</label></td>
									<td width="40%">
										<div class="col-sm-10">
											<div
												style="width: 70%; height: auto; float: left; display: inline">
												<input id="qLeaseNo" name="qLeaseNo" type="text" class="form-control">
											</div>
										<div
												style="width: 25%; height: auto; float: left; display: inline; margin-top: 5px">
												(關鍵字)</div>
										</div>
									</td>
								</tr>
							</table>
						</form>
					</div>
			</div>
		</div>
		<div id="ajaxSearchResult"  class="col-xs-12">
<!-- 		固定id for window.resize start -->
			<div id="jQgrid">
				<table id="grid"></table>
				<div id="pager"></div>
			</div>
<!-- 	 	固定id for window.resize end -->
		</div>
	</div>
</body>
</html>