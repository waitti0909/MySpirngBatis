<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<html lang="en">
<head>
<title>專線查詢</title>
<script type="text/javascript">
$(document).ready(function() {
	 $("#lineGrid").jqGrid({
		datatype: "local",
		pager: '#linePager',
		url : CONTEXT_URL+"/st/line/lineApply/searchApplyLineData",
		colNames:['','專線號碼','甲端名稱','甲端站台','乙端名稱','乙端站台','','','','','','','狀態'],
		colModel:[
			{
				name:'app_ID',index:'app_ID',width:100, hidden:true
			},
			{name:'line_ID',index:'line_ID', sortable: false},
			{name:'a_SITE_NAME',index:'a_SITE_NAME', sortable: false},
			{name:'a_BTS_SITE_ID',index:'a_BTS_SITE_ID', sortable: false},
			{name:'b_SITE_NAME',index:'b_SITE_NAME',sortable: false},
			{name:'b_BTS_SITE_ID',index:'b_BTS_SITE_ID',sortable: false},
			{
				name:'a_LOC',index:'a_LOC',width:100, hidden:true
			},
			{
				name:'vendor',index:'vendor',width:100, hidden:true
			},
			{
				name:'line_PURPOSE',index:'line_PURPOSE',width:100, hidden:true
			},
			{
				name:'line_TYPE',index:'line_TYPE',width:100, hidden:true
			},
			{
				name:'line_SPEED',index:'line_SPEED',width:100, hidden:true
			},
			{
				name:'use_TYPE',index:'use_TYPE',width:100, hidden:true
			},
			{name:'line_STATUS_NAME',index:'line_STATUS_NAME',sortable: false}
		],	
		viewrecords: true,
		caption:"基站清單",
		rownumbers:true,
		multiselect: false,
		gridComplete: function(){
			var ids = jQuery("#lineGrid").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				var cl = ids[i];  // cl:jqgrid的行數，非資料真正的HEADER_ID
				$("#lineGrid").jqGrid('getRowData', cl);
			}	
		}
	});//end grid
	
	$(window).resize(function(){
		$(window).unbind("onresize");
		$("#lineGrid,.grid").setGridWidth($("#linejQgrid").width() -10);
		$(window).bind("onresize", this);
	}).trigger('resize');	
	
	
	$('#lineDataSearchBtn').click(function() {
		var data= {
				"lineId" : $("#queryLineId").val(),// 專線編號
				"lineStatus" : $.trim($("#queryLineStatus").val()),//專線狀態
				"aBtsSiteId" : $("#aBtsSiteId").val(),//甲端基站代碼
				"bBtsSiteId" : $("#bBtsSiteId").val(),//乙端基站代碼
				"applyDept" : $.trim($("#queryApplyDept").val())//申請單位
			};
		$("#lineGrid").setGridParam({datatype:"json", postData:data, mtype: 'POST',page:1}).jqGrid().trigger("reloadGrid");
		
	});//addButton end
	
	$("#lineDataSearchOkBtn").click(function(){
		var id =$("#lineGrid").jqGrid('getGridParam','selrow');
		if (id!="") {
			var row = $("#lineGrid").jqGrid('getRowData', id);
			var callBackFun = "${callBackFun}";
			if (typeof window[callBackFun] === "function") {
				window[callBackFun](row);
			}
			$("#${targetFrameId}").dialog('close');
			
		}else {
        	alert("先選取一列資料列");
        }
	});
	
	
	$("#lineResetSearchBtn").click(function(){
		$("#lineGrid").jqGrid().clearGridData();
		$("#lineQueryTable input").val('');
		$("#lineQueryTable select").val('');
	});//reset button end
	
});
</script>
</head>
<body>
	<div id="ajax-content">
	<!--內容在這裡-->
	<button class="btn btn-primary btn-label" type="button" id="lineDataSearchBtn">
		<s:message code="button.search" text="查詢" />
	</button>
	<button class="btn btn-primary btn-label" type="button" id="lineDataSearchOkBtn">
		<s:message code="button.ok" text="確定" />
	</button>
	<button class="btn btn-primary btn-label" type="button" id="lineCancelSearchBtn"
		id="">
		<s:message code="button.cancel" text="取消" />
	</button>
	<button class="btn btn-primary btn-label" type="button" id="lineResetSearchBtn">
		<s:message code="button.reset" text="重置"  />
	</button>
	<div  class="row">
		<div class="col-xs-12 col-sm-12">
			<div class="box ui-draggable ui-droppable">
				<div class="box-header">
					<div class="box-name">
						<i class="fa fa-search"></i> <span>專線查詢</span>
					</div>
					<div class="box-icons">
						<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
						</a> <a class="expand-link"> <i class="fa fa-expand"></i>
						</a>
					</div>
					<div class="no-move"></div>
				</div>
				<div class="box-content">
					<form role="form" class="form-horizontal bootstrap-validator-form" novalidate="novalidate" id="searchLineForm"  >
						<div class="form-group">
							<!--  系統功能  -->
							<table id="lineQueryTable" style="width:100%">
								<tr>
									<td><label class="col-sm-10"></label></td>
									<td><label class="col-sm-12 control-label">專線編號 :</label></td>
									<td>
										<div class="col-sm-6">
											<input  type="text" id="queryLineId" class="form-control">
										</div>
									</td>
									<td><label class="col-sm-12 control-label">專線狀態 :</label></td>
									<td>
										<div class="col-sm-6">
											<select id="queryLineStatus" class="form-control"  >
												<option value="">---請選擇---</option>
												<c:forEach items="${lineStatusList }" var="lineStatus">
													<option value="${lineStatus.value}">${lineStatus.label}</option>
												</c:forEach>
											</select>
										</div>
									</td>
								</tr>
								<tr>
								<td><div class="clearfix">&nbsp;</div></td>
								</tr>
								<tr>
									<td></td>
									<td><label class="col-sm-12 control-label">甲端基站代碼 :</label></td>
									<td>
										<div class="col-sm-6">
											<input  type="text" id="aBtsSiteId" class="form-control">
										</div>
									</td>
									<td><label class="col-sm-12 control-label">乙端基站代碼  :</label></td>
									<td>
										<div class="col-sm-6">
											<input  type="text" id="bBtsSiteId" class="form-control">
										</div>
									</td>
								</tr>
								<tr>
									<td><div class="clearfix">&nbsp;</div></td>
								</tr>
								<tr>
									<td></td>
									<td><label class="col-sm-12 control-label">申請單位 : </label></td>
									<td>
										<div class="col-sm-6">
											<select id="queryApplyDept" class="form-control"  >
												<option value="">---請選擇---</option>
												<c:forEach items="${allRepDept }" var="repDept">
													<option value="${repDept.value}">${repDept.label}</option>
												</c:forEach>
											</select>
										</div>
									</td>
									<td></td>
									<td>
									</td>
								</tr>
							</table>
							<!--  系統功能 end -->
						</div>
					</form>
				</div>
			</div>
		</div>
		
	</div>
<!-- 查詢結果 -->
	<div id="ajaxSearchResult"  class="col-xs-12">
<!-- 		固定id for window.resize start -->
			<div id="linejQgrid">
				<table id="lineGrid" style="" ></table>
				<div id="linePager"></div>
			</div>
<!-- 	 	固定id for window.resize end -->
		</div>
	<!-- end -->
	</div>
</body>
</html>