<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<html lang="en">
<head>
<title>工單查詢</title>
<script type="text/javascript">
				$(document).ready(function() {
					 $("#workGrid").jqGrid({
						datatype: "local",
						pager: '#workPager',
						url : CONTEXT_URL+"/otr/otr001/searchWorkOrder",
						colNames:['siteId','orderId','workType','eqpName','odrStatus','repDept','工單號碼','工務類別','基站編號','基站名稱','基站頻段','接工單位','負責人','狀態'],
						colModel:[
							{name:'siteId',index:'siteId',width:100, hidden:true},
							{name:'orderId',index:'orderId',width:100, hidden:true},
							{name:'work_TYPE',index:'work_TYPE',width:100, hidden:true},
							{name:'eqpName',index:'eqpName',width:100, hidden:true},
							{name:'odr_STATUS',index:'odr_STATUS',width:100, hidden:true},
							{name:'rep_DEPT',index:'rep_DEPT',width:100, hidden:true},
							{name:'work_ID',index:'work_ID', sortable: false},
							{name:'workTypeName',index:'workTypeName', sortable: false},
							{name:'bts_SITE_ID',index:'bts_SITE_ID', sortable: false},
							{name:'site_NAME',index:'site_NAME',sortable: false},
							{name:'feqName',index:'feq_ID',sortable: false},
							{name:'deptName',index:'deptName',sortable: false},
							{name:'repUserName',index:'repUserName',sortable: false},
							{name:'odStatusName',index:'odStatusName',sortable: false}
						],	
						viewrecords: true,
						caption:"基站清單",
						rownumbers:true,
						multiselect: false,
						gridComplete: function(){
							var ids = jQuery("#workGrid").jqGrid('getDataIDs');
							for(var i=0;i < ids.length;i++){
								var cl = ids[i];  // cl:jqgrid的行數，非資料真正的HEADER_ID
								$("#workGrid").jqGrid('getRowData', cl);
							}	
						}
					});//end grid
					
					$(window).resize(function(){
						$(window).unbind("onresize");
						$("#workGrid,.grid").setGridWidth($("#workjQgrid").width() -10);
						$(window).bind("onresize", this);
					}).trigger('resize');	
					
					
					$('#workDataSearchBtn').click(function() {
						var data= {
								"depId" : $("#queryWorkDepId option:selected").val(), //接工單位
								"orderStatus" : $.trim($("#queryorderStatus").val()),//工單狀態
								"btsSiteId" : $("#queryBtsSiteId").val(),//基站編號
								"siteName" : $("#queryBtsSiteName").val() //基站名稱
			 			};
						$("#workGrid").setGridParam({datatype:"json", postData:data, mtype: 'POST',page:1}).jqGrid().trigger("reloadGrid");
						
					});//addButton end
					
					$("#workDataSearchOkBtn").click(function(){
						var id =$("#workGrid").jqGrid('getGridParam','selrow');
						
						if (id != null && id!="") {
							var row = $("#workGrid").jqGrid('getRowData', id);
							var callBackFun = "${callBackFun}";
							if (typeof window[callBackFun] === "function") {
								window[callBackFun](row);
							}
							$("#${targetFrameId}").dialog('close');
							
						}else {
				        	alert("先選取一列資料列");
				        }
					});
					
					
					$("#workResetSearchBtn").click(function(){
						$("#workGrid").jqGrid().clearGridData();
						$("#workQueryTable input").val('');
						$("#workQueryTable select").val('');
					});//reset button end
					
									
				});
			</script>
</head>
<body>
				<div id="ajax-content">
				<!--內容在這裡-->
				<button class="btn btn-primary btn-label" type="button" id="workDataSearchBtn">
					<s:message code="button.search" text="查詢" />
				</button>
				<button class="btn btn-primary btn-label" type="button" id="workDataSearchOkBtn">
					<s:message code="button.ok" text="確定" />
				</button>
				<button class="btn btn-primary btn-label" type="button" id="workResetSearchBtn">
					<s:message code="button.reset" text="重置"  />
				</button>
				<div  class="row">
					<div class="col-xs-12 col-sm-12">
						<div class="box ui-draggable ui-droppable">
							<div class="box-header">
								<div class="box-name">
									<i class="fa fa-search"></i> <span>工單查詢</span>
								</div>
								<div class="box-icons">
									<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
									</a> <a class="expand-link"> <i class="fa fa-expand"></i>
									</a>
								</div>
								<div class="no-move"></div>
							</div>
							<div class="box-content">
								<form role="form" class="form-horizontal bootstrap-validator-form" novalidate="novalidate" id="searchworkForm"  >
									<div class="form-group">
										<table id="workQueryTable" style="width:100%">
											<tr>
												<td></td>
												<td><label class="col-sm-12 control-label">接工單位 : </label></td>
												<td>
													<div class="col-sm-6">
														<select class="form-control" id="queryWorkDepId"  >
															<option value="" >---請選擇---</option>
															<c:forEach items="${deptList }" var="repDept">
																<option value="${repDept.REP_DEPT }"><c:out value='${repDept.deptName}'/></option>
															</c:forEach>
														</select>
													</div>
												</td>
												<td><label class="col-sm-12 control-label">工單狀態 : </label></td>
												<td>
													<div class="col-sm-6">
														<select class="form-control" id="queryorderStatus"  >
															<option value="">---請選擇---</option>
															<c:forEach items="${orderStatusList }" var="orderStatus">
																<option value="${orderStatus.value }"><c:out value='${orderStatus.label}'/></option>
															</c:forEach>
														</select>
													</div>
												</td>
											</tr>
											<tr>
												<td><label class="col-sm-10"></label></td>
												<td><label class="col-sm-12 control-label">基站編號 :</label></td>
												<td>
													<div class="col-sm-6">
														<input  type="text" id="queryBtsSiteId" class="form-control">
													</div>
												</td>
												<td><label class="col-sm-12 control-label">基站名稱:</label></td>
												<td>
													<div class="col-sm-6">
														<input  type="text" id="queryBtsSiteName" class="form-control">
													</div>
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
						<div id="workjQgrid">
							<table id="workGrid" style="" ></table>
							<div id="workPager"></div>
						</div>
		<!-- 	 	固定id for window.resize end -->
					</div>
				<!-- end -->
				</div>
</body>
</html>