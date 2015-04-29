<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>建站作業處理</title>
<script type="text/javascript">
$(document).ready(function() {
	//鎖定廠商
	if ($('#isVendors').val() == "true"){
		$('#vendors').val($('#covatNo').val())
		$("#vendors").prop("disabled", true);
		$('#ticketType').val("OR06");
		$("#ticketType").prop("disabled", true);
	}
// 	else if($('#isManager').val() == "true") {
// 		$('#ticketType').val("OR03");
// 	}else {
// 		$('#ticketType').val("OR04");
// 	}
	//取得行政區選項
	LoadSelect2Script('<s:url value="/resources/plugins/select2/select2.js" />', Select2Test);
// 	buildCityOptions("locCity","locTown");
	
	//派工
	$('#btn_st_assign').click(function() {
		var id =$("#grid").jqGrid('getGridParam','selrow');
		if (id!="" && id!=null) {
			var row = $("#grid").jqGrid('getRowData', id);
			//alert(row.odr_STATUS);
			if(row.odr_STATUS != "OR03"){
				alert("此工單狀態無法派工");
				return false;
			} else if (!preOrdersAllFinished(row.work_ID, row.order_ID)) {	// add by Charlie 20150110 增加判斷前序號工單是否皆已完成
				alert("尚有前置工單未完工，無法進行派工作業");
				return false;
			}
			assignPage(row.work_ID ,"edit",row.rep_DEPT,row.deptName,row.order_ID);		
		}else {
        	alert("先選取一列資料列");
        }	 		
	});//派工 end
	
	//修改
	$('#btn_edit').click(function() {
		var id =$("#grid").jqGrid('getGridParam','selrow');
		if (id!="" && id!=null) {
			var row = $("#grid").jqGrid('getRowData', id);
            if(row.odr_STATUS != "OR04" && row.odr_STATUS != "OR05" && row.odr_STATUS != "OR06"){  
				alert("此工單狀態無法修改");
				return false;
			}
			
			updateEide(row.work_ID, 
            		      row.order_ID,
            		      row.pick_USER,
            		      row.end_DESC,
            		      "edit",
            		      row.orderTypeName,
            		      row.odr_STATUS,
            		      row.siteId);
			//接工單位
			$("#siteBuildRepDept").val(row.rep_DEPT);
		}else {
        	alert("先選取一列資料列");
        }			 			 	
	});//修改 end

	$('#btn_search').click(function() {
 		var data= {
			workId : $("#st005L_workId").val(),//作業編號
			ticketId : $("#ticketId").val(),//工單編號
			baseStationId : $("#baseStationId").val(),//基站編號
			btsSiteName : $("#btsSiteName").val(),//基站名稱
			locCity : $.trim($("#locCity").val()),//行政區
			locTown : $.trim($("#locTown").val()),//行政區
			workersPickType : $("#workersPickType").val(),//接工單位
			ticketType : $("#ticketType").val(),//工單狀態
			vendors : $("#vendors").val(),//委外廠商
			worksType : $("#worksType").val(),//工務類別
			rangeBa:$.trim($("#rangeBa").val()),
			rangeBb:$.trim($("#rangeBb").val()),
			orderTypeId:$("#orderTypeId").val()
 		};
 		//alert(JSON.stringify(data));
		$("#grid").setGridParam({datatype:"json", postData:data, mtype: 'POST',page:1}).jqGrid().trigger("reloadGrid");
	});//searchButton end

	$("#grid").jqGrid({
		datatype: "local",
		pager: '#pager',
		url : CONTEXT_URL+"/st/st005/search",
		colNames:['作業編號','工務類別','派工目的','基站編號','基站名稱','工單號碼','接工單位','負責人','狀態','rep_DEPT','pick_USER','end_DESC','odr_STATUS','siteId'],
		colModel:[				   
    		{name:'work_ID',index:'work_ID',width:90, sortable: false},
			{name:'workTypeName',index:'workTypeName',width:80, sortable: false},
			{name:'orderTypeName',index:'orderTypeName', width:80,sortable: false},
			{name:'btsSiteId',index:'bts_SITE_ID',width:100, sortable: false},
			{name:'siteName',index:'site_NAME', width:100,sortable: false},
			{name:'order_ID',index:'order_ID', width:100,sortable: false},
			{name:'deptName',index:'deptName', width:100,sortable: false},
			{name:'chnName',index:'chnName', width:100,sortable: false},
			{name:'orderStatusName',index:'orderStatusName', width:80,sortable: false},
			{name:'rep_DEPT',index:'rep_DEPT', hidden:true,sortable: false},	
			{name:'pick_USER',index:'pick_USER', hidden:true,sortable: false},
			{name:'end_DESC',index:'end_DESC', hidden:true,sortable: false},
			{name:'odr_STATUS',index:'odr_STATUS', hidden:true,sortable: false},
			{name:'siteId',index:'siteId', hidden:true,sortable: false},
		],	
		viewrecords: true,
		caption:"建站作業工單清單",
		rownumbers:true,
		multiselect: false,
		onSelectRow: function(id){ },
		gridComplete: function(){
			var ids = jQuery("#grid").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				var cl = ids[i];  // cl:jqgrid的行數，非資料真正的HEADER_ID
				$("#grid").jqGrid('getRowData', cl);
			}	
		},
		ondblClickRow: function(rowId) { //double clicking a jqgrid row
			var rowData = jQuery(this).getRowData(rowId);
			//alert(JSON.stringify(rowData));
			updateEide(rowData.work_ID, 
       				   rowData.order_ID, 
       				   rowData.pick_USER, 
       				   rowData.end_DESC, 
       				   "view",
       				   rowData.orderTypeName,
       				   rowData.odr_STATUS,
       				   rowData.siteId);
	     }
	});//end grid
				
	//重置按鈕
	$('#btn_reset').click(function(){	
 		//alert($('#isVendors').val());
		//$("#searchSiteLocForm").trigger('reset');
		$("#locCity").val("").change();
		$("#locTown").select2({ allowClear: true }).change();
		$("#workersPickType").select2({ allowClear: true }).change();
		$("#worksType").val("").change();
		$('#st005L_workId').val("");
		$('#ticketId').val("");
		$('#baseStationId').val("");
		$('#rangeBa').val("");
		$('#rangeBb').val("");
		$('#workersPickType').val("");
		$('#btsSiteName').val("");
		$("#orderTypeId").val("").change();
		if ($('#isVendors').val() != "true") {
			//alert($('#isVendors').val() + "123");
			$("#ticketType").val("").change();
			$("#vendors").val("").change();
		} 
	$("#grid").jqGrid().clearGridData();
	});			
    
    //申請日期-起
	$('#rangeBa').datepicker({
		dateFormat : "yy/mm/dd",
		changeYear : true,
		changeMonth : true,
		showButtonPanel : true
	});
	
    //申請日期-迄
	$('#rangeBb').datepicker({
		dateFormat : "yy/mm/dd",
		changeYear : true,
		changeMonth : true,
		showButtonPanel : true
	});
	
	$(window).resize(function(){
		$(window).unbind("onresize");
		$("#grid,.grid").setGridWidth($("#jQgrid").width() -10);
		$(window).bind("onresize", this);
	}).trigger('resize');
});//document.ready end

//修改
function updateEide(workId, orderId, pickUser, endDesc, status,
		orderTypeName, orderStatus, siteId){
	$.fancybox.open({
			href : CONTEXT_URL+"/st/st005/updatePage",
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
	            type: 'POST',
	            async : false,
	            data:{
	            	"orderId" : orderId,
	            	"status" : status,
  		      		"siteId" : siteId
	            }
	        }
		});
}	

//派工
function assignPage (workId ,status ,rep_DEPT ,deptName, orderId) {
	$.fancybox.open({
		href : CONTEXT_URL+"/st/st005/assignPage",
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
			title : "派工",
			openEffect : 'elastic',
			closeEffect : 'fade',
			ajax : {
	            type: 'POST',
	            async: false,
	            data:{
	            	"workId" : workId,
	            	"status" : status,
	            	"rep_DEPT" : rep_DEPT,
	            	"deptName" : deptName,
	            	"orderId" : orderId
	            }
	        },
	        afterClose : function() {
	       		$('#btn_search').click();
	        }
	});
}

function Select2Test() {
	buildCityOptions("locCity","locTown");
	$("select").select2();
}

//add by Charlie 20150110 
function preOrdersAllFinished(workId, orderId) {
	// 檢查此工單之前置工單是否皆已經完工
	var finished = true;
	$.ajax({
		url : CONTEXT_URL + "/st/st003/checkPreOrdersNotFinished",
		type : 'POST',
		dataType : "text",
		async : false,
		data:{
			"workId" :  workId,
			"orderId" : orderId
		},
		success : function(data) {						
			if(new Number(data) > 0) {
					finished = false;
			}
		}
	});	
	return finished;
}
</script>
</head>
<body>
    <!-- 頁面功能按鈕 -->
	<div class="row">
		<div id="breadcrumb" class="col-md-12">
			<ol class="breadcrumb">
				<c:import url="/auth/role/menu/func/${currentMenuId}" />
			</ol>
		</div>
	</div>
	<!--頁面主體-->
	<div class="row">
		<!--搜尋條件 -->
		<div class="col-xs-12 col-sm-12">
			<div class="box ui-draggable ui-droppable">
			    <!--標題 -->
				<div class="box-header">
					<div class="box-name">
						<i class="fa fa-search"></i> <span>建站作業處理 工單查詢</span>
					</div>
					<div class="box-icons">
						<a class="collapse-link"> <i class="fa fa-chevron-up"></i></a>
						<a class="expand-link"> <i class="fa fa-expand"></i></a>
					</div>
					<div class="no-move"></div>
				</div>
				<!--Search Body -->
				<div class="box-content">
					<form role="form" class="form-horizontal bootstrap-validator-form" novalidate="novalidate" id="searchSiteLocForm" action='<s:url value="/auth/systemMenu/"/>' >
						<div class="form-group"><!--  系統功能  -->
							<table style="width:100%">
								<tr>
									<td>
										<label class="col-sm-10"></label>
									</td>
									
									<td>
										<label class="col-sm-12 control-label">作業編號 :</label>
									</td>
									<td width="35%">
										<div class="col-sm-6">
											<input id="st005L_workId" name="workId" type="text" class="form-control">
										</div>
									</td>
									
									<td>
										<label class="col-sm-12 control-label">工單編號 :</label>
									</td>
									<td>
										<div class="col-sm-6">
											<input id="ticketId" name="ticketId" type="text" class="form-control">
<!-- 											<input id="orderId" name="orderId" type="text" class="form-control"> -->
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="clearfix">&nbsp;</div>
									</td>
								</tr>
								<tr>
									<td></td>
									<td>
										<label class="col-sm-12 control-label">基站編號 :</label>
									</td>
									<td>
										<div class="col-sm-6">
											<input id="baseStationId" type="text" name="baseStationId" class="form-control">
<!-- 											<input id="btsSiteId" type="text" name="btsSiteId" class="form-control"> -->
										</div>
									</td>
									
									<td>
										<label class="col-sm-12 control-label">基站名稱 :</label>
									</td>
									<td>
										<div class="col-sm-6">
											<input id=btsSiteName type="text" name="btsSiteName" class="form-control">
<!-- 											<input id="siteName" type="text" name="siteName" class="form-control"> -->
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="clearfix">&nbsp;</div>
									</td>
								</tr>
								<tr>
									<td></td>
									<td>
										<label class="col-sm-12 control-label">申請日期(起) : </label>
									</td>
									<td>
										<div class="col-sm-6">
											<input id="rangeBa" name="rangeBa" type="text" class="form-control" readonly="readonly" placeholder="點選輸入框">
<!-- 											<input id="applyDateStart" name="applyDateStart" type="text" class="form-control" readonly="readonly" placeholder="點選輸入框"> -->
										</div>
									</td>
									
									<td nowrap="nowrap">
										<label class="col-sm-12 control-label">申請日期(迄) : </label>
									</td>
									<td>
										<div class="col-sm-6">
											<input id="rangeBb" name="rangeBb" type="text"	class="form-control" readonly="readonly" placeholder="點選輸入框">
<!-- 											<input id="applyDateEnd" name="applyDateEnd" type="text"	class="form-control" readonly="readonly" placeholder="點選輸入框"> -->
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="clearfix">&nbsp;</div>
									</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td>
										<label class="col-sm-12 control-label">行政區 : </label>
									</td>
									<td>
										<div class="col-sm-6">
											<select id="locCity" class="populate placeholder" name="locCity" >
											</select>
										</div>
										<div class="col-sm-6">
											<select id="locTown" class="populate placeholder" name="locTown" >
											</select>
										</div>
									</td>									
									<td>
										<label class="col-sm-12 control-label">接工單位 :</label>
									</td>
									<td>
										<div class="col-sm-8">
<%-- 											<select  id="repDept" class="populate placeholder" name="repDept" > --%>
											<select  id="workersPickType" class="populate placeholder" name="workersPickType" >
												<option value="">---請選擇---</option>
												<c:forEach items="${allRepDept }" var="repDept">
													<option value="${repDept.value}">${repDept.label}</option>
												</c:forEach>													
											</select>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="clearfix">&nbsp;</div>
									</td>
								</tr>
								<tr>
									<td></td>
									<td>
										<label class="col-sm-12 control-label">工單狀態 :</label>
									</td>
									<td>
										<div class="col-sm-8">
<%-- 											<select  id="orderStatus" class="populate placeholder" name="orderStatus" > --%>
											<select  id="ticketType" class="populate placeholder" name="ticketType" >
												<option value="">---請選擇---</option>
												<option value="todo">未結案</option>
												<c:forEach items="${allOrderStatus}" var="orderStatus">
													<option value="${orderStatus.value}">${orderStatus.label}</option>
												</c:forEach>
											</select>
										</div>
									</td>
									<td>
										<label class="col-sm-12 control-label">委外廠商 :</label>
									</td>
									
									<td>
										<div class="col-sm-8">
<%-- 											<select  id="coVatNo" class="populate placeholder" name="coVatNo" > --%>
											<select  id="vendors" class="populate placeholder" name="vendors" >
												<option value="">---請選擇---</option>
												<c:forEach items="${allCompany}" var="company">
													<option value="${company.CO_VAT_NO}">${company.CO_NAME}</option>
												</c:forEach>
											</select>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="clearfix">&nbsp;</div>
									</td>
								</tr>
								<tr>
									<td></td>
									<td>
										<label class="col-sm-12 control-label">工務類別 :</label>
									</td>
									<td>
										<div class="col-sm-8">
											<select  id="worksType" class="populate placeholder" name="worksType" >
												<option value="">---請選擇---</option>
												<c:forEach items="${allWorkType}" var="workType">
													<option value="${workType.value}">${workType.label}</option>
												</c:forEach>
											</select>
										</div>
									</td>
									<td>
										<label class="col-sm-12 control-label">派工目的 :</label>
									</td>
									<td>
										<div class="col-sm-8">
											<select id="orderTypeId" class="populate placeholder" name="orderTypeId" >
												<option value="">---請選擇---</option>
												<c:forEach items="${allOrderType}" var="orderType">
													<option value="${orderType.value}">${orderType.label}</option>
												</c:forEach>
											</select>
										</div>
									</td>
								</tr>
							</table>
						</div><!--  系統功能 end -->
					</form>
				</div><!-- Search Body結束 -->
			</div>
		</div>
		<!-- 搜尋條件 End -->
		<!-- 查詢結果 -->
		<div id="ajaxSearchResult"  class="col-xs-12">
		<!--固定id for window.resize start -->
				<div id="jQgrid">
					<table id="grid"></table>
					<div id="pager"></div>
				</div>
		<!--固定id for window.resize end -->
		</div>
	</div>
	<!-- 頁面主體 End-->
	<input id="isVendors" type="hidden" value="${isVendors}">
	<input id="isManager" type="hidden" value="${isManager}">
	<input id="vendors" type="hidden" value="${vendors}">
	<input id="covatNo" type="hidden" value="${covatNo}">
		
<!-- 	<input id="workType" type="hidden" /> -->
	<!-- 接工單位 -->
	<input id="siteBuildRepDept"  type="hidden" ></input>
</body>
</html>