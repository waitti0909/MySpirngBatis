<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>專線申請/異動 查詢</title>
<script type="text/javascript">
$(document).ready(function() {
	
	LoadSelect2Script('<s:url value="/resources/plugins/select2/select2.min.js" />', Select2Test);
	WinMove();
	//修改
	$('#btn_edit').click(function() {
		var id =$("#grid").jqGrid('getGridParam','selrow');
		if (id!="" && id!=null) {
			var row = $("#grid").jqGrid('getRowData', id);	
			
			if($.inArray(row.line_PURPOSE , ['B','M','P']) > -1  ){ //工務類型
				if($.inArray(row.app_STATUS , ['LA04','LA05','LA06']) == -1){
					alert("此工務類專線目前狀態無法進行編輯！");
					return false;
				} 
			} 
			
			updatelineApply(row.app_ID,row.line_PURPOSE,"edit");
		}else {
        	alert("先選取一列資料列");
        }			 			 	
	});//修改 end

	$('#btn_search').click(function() {
 		var data= {
 					workId : $("#workId").val(),//工單編號
 					applicationId : $("#applicationId").val(),//申請單號
 					baseStationId : $("#baseStationId").val(),//基站編號
 					btsSiteName : $("#btsSiteName").val(),//基站名稱
 					lineId : $.trim($("#lineId").val()),//專線號碼
 					applyDept : $("#applyDept").val(),//申請單位
 					appleType : $("#appleType").val(),//申請類別
 					appleState : $("#appleState").val(),//申請狀態
 					worksType : $("#worksType").val(),//業者別
 					lineType : $("#lineType").val(),//專線狀態
 		};
 		//alert(JSON.stringify(data));
		$("#grid").setGridParam({datatype:"json", postData:data, mtype: 'POST',page:1}).jqGrid().trigger("reloadGrid");
	});//searchButton end

	$("#grid").jqGrid({
		datatype: "local",
		pager: '#pager',
		url : CONTEXT_URL+"/st/line/lineApply/search",
		colNames:['申請單號','類別','專線號碼','業者','電路用途代碼','電路用途','速率','甲端','乙端','開通日期','退租日期','租期迄日','申請狀態','專線狀態',''],
		colModel:[				   
					{name:'app_ID',index:'app_ID',width:80, sortable: false},
					{name:'app_TYPE_NAME',index:'app_TYPE_NAME',width:50, sortable: false},
					{name:'line_ID',index:'line_ID',width:80, sortable: false},
					{name:'vendor',index:'vendor', width:60,sortable: false},
					{name:'line_PURPOSE',index:'line_PURPOSE', hidden : true,width:60,sortable: false},
					{name:'line_PURPOSE_NAME',index:'line_PURPOSE_NAME', width:60,sortable: false},
					{name:'line_SPEED',index:'line_SPEED', width:60,sortable: false},
					{name:'a_LOC',index:'a_LOC', width:120,sortable: false},
					{name:'b_LOC',index:'b_LOC', width:120,sortable: false},
					{name:'start_DATE',index:'start_DATE', formatter:"date",formatoptions: {srcformat:'Y/m/d',newformat:'Y/m/d'},width:60,sortable: false},
					{name:'end_DATE',index:'end_DATE',formatter:"date",formatoptions: {srcformat:'Y/m/d',newformat:'Y/m/d'}, width:60,sortable: false},
					{name:'rent_END_DATE',index:'rent_END_DATE',formatter:"date",formatoptions: {srcformat:'Y/m/d',newformat:'Y/m/d'}, width:60,sortable: false},
					{name:'app_STATUS_NAME',index:'app_STATUS_NAME', width:50,sortable: false},
					{name:'line_STATUS_NAME',index:'line_STATUS_NAME', width:50,sortable: false},
					{name:'app_STATUS',index:'app_STATUS', width:50,sortable: false,hidden:true}
				],	
				viewrecords: true,
				caption:"專線清單",
				rownumbers:true,
				multiselect: false,
				onSelectRow: function(id){ },
				gridComplete: function(){
					
				},
				ondblClickRow: function(rowId) { //double clicking a jqgrid row
			        var rowData = jQuery(this).getRowData(rowId);
					//alert(JSON.stringify(rowData));
			         $.each(rowData, function(i,item) {
					    if (i == "app_ID") {
// 					    	alert(rowData.app_ID);
								updatelineApply(rowData.app_ID,rowData.line_PURPOSE,"view");
						 }
			          });
			            
			     }
	});//end grid
				
	//重置按鈕
	$('#btn_reset').click(function(){	
		$("#searchLineApplyForm").trigger('reset');
		$("#applyDept").select2({ allowClear: true });
		$("#appleType").select2({ allowClear: true });
		$("#appleState").select2({ allowClear: true });
		$("#worksType").select2({ allowClear: true });
		$("#lineType").select2({ allowClear: true });
		$("#grid").jqGrid().clearGridData();
	});			
    
	//新增
	$('#btn_line_add').click(function() {
		$.fancybox.open({
			href : CONTEXT_URL + "/st/line/lineApply/insertNonLine",
			type : 'ajax',
			width : $(window).width(),
			height : $(window).width(),
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
			title : "新增",
			openEffect : 'elastic',
			closeEffect : 'fade',
			ajax : {
				type : 'POST'
			},
			afterClose : function() {
			}
		});
	});//addButton end
	
	$(window).resize(function(){
		$(window).unbind("onresize");
		$("#grid,.grid").setGridWidth($("#jQgrid").width() -10);
		$(window).bind("onresize", this);
	}).trigger('resize');
});//document.ready end

//修改
	function updatelineApply(appId,linePurpose, status) {
		if(linePurpose=='D'||linePurpose=='S'||linePurpose=='V'||linePurpose=='O')
		{
	 		$.fancybox.open({
				href : CONTEXT_URL + "/st/line/lineApply/updateNonLine",
				type : 'ajax',
				width : $(window).width(),
				height : $(window).width(),
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
				title : "修改",
				openEffect : 'elastic',
				closeEffect : 'fade',
				ajax : {
					type : 'POST',
					data : {
						"appId" : appId,
						"status" : status
					}
			},
				afterClose : function() {
				}
			});
		}
		else {
			$.fancybox.open({
	 			href : CONTEXT_URL + "/st/line/lineApply/initLineApplyM",
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
	 	            data:{
	 	            	"appId" : appId,
	 	            	"viewType" :status
	 	            }
	 	        },
	 	       afterClose : function() {
	 	        }
	 		});			
		}

	}

function Select2Test() {
	$("select").select2();
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
						<i class="fa fa-search"></i> <span>專線申請/異動 查詢</span>
					</div>
					<div class="box-icons">
						<a class="collapse-link"> <i class="fa fa-chevron-up"></i></a>
						<a class="expand-link"> <i class="fa fa-expand"></i></a>
					</div>
					<div class="no-move"></div>
				</div>
				<!--Search Body -->
				<div class="box-content">
					<form role="form" class="form-horizontal bootstrap-validator-form" novalidate="novalidate" id="searchLineApplyForm" action='<s:url value="/auth/systemMenu/"/>' >
						<div class="form-group"><!--  系統功能  -->
							<table style="width:100%">
								<tr>
									<td>
										<label class="col-sm-10"></label>
									</td>
									
									<td>
										<label class="col-sm-12 control-label">工單編號 :</label>
									</td>
									<td width="35%">
										<div class="col-sm-6">
											<input id="workId" name="workId" type="text" class="form-control">
										</div>
									</td>
									
									<td>
										<label class="col-sm-12 control-label">申請單號 :</label>
									</td>
									<td>
										<div class="col-sm-6">
											<input id="applicationId" name="ticketId" type="text" class="form-control">
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
										</div>
									</td>
									
									<td>
										<label class="col-sm-12 control-label">基站名稱 :</label>
									</td>
									<td>
										<div class="col-sm-6">
											<input id=btsSiteName type="text" name="btsSiteName" class="form-control">
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
										<label class="col-sm-12 control-label">專線號碼 :</label>
									</td>
									<td>
										<div class="col-sm-6">
											<input id=lineId type="text" name="lineId" class="form-control">
										</div>
									</td>
									
									<td>
										<label class="col-sm-12 control-label">申請單位 :</label>
									</td>
									<td>
										<div class="col-sm-8">
<%-- 											<select  id="repDept" class="populate placeholder" name="repDept" > --%>
											<select id="applyDept" class="populate placeholder"
												name="applyDept">
												<option value="">---請選擇---</option>
												<c:forEach items="${applyDeptList}" var="applyDeptList">
													<option value="${applyDeptList.APP_DEPT}">${applyDeptList.deptName}</option>
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
										<label class="col-sm-12 control-label">申請類別 :</label>
									</td>
									<td>
										<div class="col-sm-8">
<%-- 											<select  id="orderStatus" class="populate placeholder" name="orderStatus" > --%>
											<select  id="appleType" class="populate placeholder" name="appleType" >
												<option value="">---請選擇---</option>
												<c:forEach items="${appleTypeList}" var="appleType">
													<option value="${appleType.value}">${appleType.label}</option>
												</c:forEach>
											</select>
										</div>
									</td>
									<td>
										<label class="col-sm-12 control-label">申請狀態 :</label>
									</td>
									
									<td>
										<div class="col-sm-8">
<%-- 											<select  id="coVatNo" class="populate placeholder" name="coVatNo" > --%>
										<select id="appleState" class="populate placeholder"
												name="appleState">
												<option value="">---請選擇---</option>
												<c:forEach items="${appleStateList}" var="appleStateList">
													<option value="${appleStateList.value}">${appleStateList.label}</option>
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
										<label class="col-sm-12 control-label">業者別 :</label>
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
										<label class="col-sm-12 control-label">專線狀態:</label>
									</td>
									<td>
										<div class="col-sm-8">
											<select  id="lineType" class="populate placeholder" name="worksType" >
												<option value="">---請選擇---</option>
												<c:forEach items="${lineTypeList}" var="lineType">
													<option value="${lineType.value}">${lineType.label}</option>
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
</body>
</html>