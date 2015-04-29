<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<html lang="en">
<head>
<title>基站查詢</title>
<script type="text/javascript">
				$(document).ready(function() {
					LoadSelect2Script('<s:url value="/resources/plugins/select2/select2.min.js" />', Select2Test);
					WinMove();
					$("#startDate").datepicker();
					$("#endDate").datepicker();
					
					$("#btn_add").click(function(){
						$.fancybox.open({
							href : CONTEXT_URL + "/otr/otr001/add",
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
							title : "編輯",
							openEffect : 'elastic',
							closeEffect : 'fade',
							ajax : {
								type : "POST",
								success : function(data) {

								},
								error : function(e) {
								},
								async : false
							}
						});
					});
					
					$('#btn_search').click(function() {
						var data= {
									 deptId : $("#repDept option:selected").val(),  //接工單位
									 orderId : $("#orderIdOut").val(),  //轉出工單編號
									 orderIdIn : $("#orderIdIn").val(), //目的工單編號
									 btsSiteIdOut : $("#btsSiteIdOut").val(), //轉出基站編號 
									 btsSiteIdIn : $("#btsSiteIdIn").val(), //目的基站編號
									 siteNameOut : $("#siteNameOut").val(), //轉出基站名稱  
									 siteNameIn : $("#siteNameIn").val(), //目的基站名稱
									 crTimeStart : $("#startDate").val(),  //轉出日期(起)
									 crTimeEnd : $("#endDate").val() //轉出日期(迄)
						        };

						$("#grid").setGridParam({datatype:"json", postData:data, mtype: 'POST',page:1}).jqGrid().trigger("reloadGrid");
						
					});//addButton end
					
					$("#btn_reset").click(function(){
						 $("#grid").jqGrid().clearGridData();
						 $("#repDept").select2("val",null);  //接工單位
						 $("#orderIdOut").val('');  //轉出工單編號
						 $("#orderIdIn").val(''); //目的工單編號
						 $("#btsSiteIdOut").val(''); //轉出基站編號 
						 $("#btsSiteIdIn").val(''); //目的基站編號
						 $("#siteNameOut").val(); //轉出基站名稱  
						 $("#siteNameIn").val(''); //目的基站名稱
						 $("#startDate").val('');  //轉出日期(起)
						 $("#endDate").val(''); //轉出日期(迄)
						
					});//reset button end
					
					$("#grid").jqGrid({
						datatype: "local",
						pager: '#pager',
						url : CONTEXT_URL+"/otr/otr001/search",
						colNames:['工單轉出號碼','目的工單號碼','基站編號','基站名稱','接工單位','轉出日期','optId'],
						colModel:[
							{name:'order_ID',index:'order_ID',width:100, sortable: false},
							{name:'order_ID_IN',index:'order_ID_IN',width:100, sortable: false},
							{name:'site_ID',index:'site_ID',width:100, sortable: false},
							{name:'site_NAME',index:'site_NAME',width:100, sortable: false},
							{name:'dept_NAME',index:'dept_NAME', width:100,sortable: false},
							{name:'cr_TIME',index:'cr_TIME',formatter:"date",formatoptions: {srcformat:'Y/m/d',newformat:'Y/m/d'}, width:100,sortable: false},
							{name:'opt_ID',index:'opt_ID',width:100,sortable: false,hidden:true}
						],	
						viewrecords: true,
						caption:"工單物料轉出清單",
						rownumbers:true,
						multiselect: false,
						onSelectRow: function(id){ 
							
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
				            $.each(rowData, function(i,item) {
				            	 if(i =='opt_ID'){
				            		$.fancybox.open({
							 			href : CONTEXT_URL+"/otr/otr001/add",
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
							 			title : "檢視",
							 			openEffect : 'elastic',
							 			closeEffect : 'fade',
							 			ajax : {
							 	            type: 'POST',
							 	            data:{
							 	            	"optId" : item,
							 	            }
							 	        },
							 	        afterClose : function() {
							 			}
									});
				            	} 
				            }); 
						}
					});//end grid
					
					$(window).resize(function(){
						$(window).unbind("onresize");
						$("#grid,.grid").setGridWidth($("#jQgrid").width() -10);
						$(window).bind("onresize", this);
					}).trigger('resize');					
				});
				function Select2Test() {
					$("select").select2();
				}
			</script>
</head>
<body>
				<div class="preloader">
					<img src="../resources/img/devoops_getdata.gif"
						class="devoops-getdata" alt="preloader" />
				</div>
				<!--內容在這裡-->
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
									<i class="fa fa-search"></i> <span>工單物料轉出查詢</span>
								</div>
								<div class="box-icons">
									<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
									</a> <a class="expand-link"> <i class="fa fa-expand"></i>
									</a>
								</div>
								<div class="no-move"></div>
							</div>
							<div class="box-content">
								<form role="form" class="form-horizontal bootstrap-validator-form" novalidate="novalidate" id="searchSiteLocForm" action='<s:url value="/auth/systemMenu/"/>' >
									<div class="form-group">
										<!--  系統功能  -->
										<table style="width:100%">
											<tr>
												<td><label class="col-sm-10"></label></td>
												<td><label class="col-sm-12 control-label">接工單位:</label></td>
												<td>
													<div class="col-sm-6">
														<select id="repDept"  class="populate placeholder" >
															<option value=''>---請選擇---</option>
															<c:forEach items="${deptList }" var="dept" >
																<option value="${dept.REP_DEPT }"><c:out value='${dept.deptName}'/></option>
															</c:forEach>
														</select>
													</div>
												</td>
												<td></td>
												<td></td>
											</tr>
											<tr>
											<td><div class="clearfix">&nbsp;</div></td>
											</tr>
											<tr>
												<td></td>
												<td><label class="col-sm-12 control-label">轉出工單編號 :</label></td>
												<td>
													<div class="col-sm-6">
														<input id="orderIdOut"  type="text" class="form-control">
													</div>
												</td>
												<td><label class="col-sm-12 control-label">目的工單編號 :</label></td>
												<td>
													<div class="col-sm-6">
														<input id="orderIdIn"  type="text" class="form-control">
													</div>
												</td>
											</tr>
											<tr>
												<td><div class="clearfix">&nbsp;</div></td>
											</tr>
											<tr>
												<td></td>
												<td><label class="col-sm-12 control-label">轉出基站編號 : </label></td>
												<td>
													<div class="col-sm-6">
														<input id="btsSiteIdOut"  type="text" class="form-control">
													</div>
												</td>
												<td><label class="col-sm-12 control-label">目的基站編號:</label></td>
												<td>
													<div class="col-sm-6">
														<input id="btsSiteIdIn"   type="text" class="form-control">
													</div>
												</td>
											</tr>
											<tr>
												<td><div class="clearfix">&nbsp;</div></td>
											</tr>
											<tr>
												<td></td>
												<td><label class="col-sm-12 control-label">轉出基站名稱 : </label></td>
												<td>
													<div class="col-sm-6">
														<input id="siteNameOut" type="text" class="form-control">
													</div>
												</td>
												<td><label class="col-sm-12 control-label">目的基站名稱:</label></td>
												<td>
													<div class="col-sm-6">
														<input id="siteNameIn"  type="text" class="form-control">
													</div>
												</td>
											</tr>
											<tr>
												<td><div class="clearfix">&nbsp;</div></td>
											</tr>
											<tr>
												<td></td>
												<td><label class="col-sm-12 control-label">轉出日期(起):</label></td>
												<td>
													<div class="col-sm-8">
														<input id="startDate"  type="text" class="form-control" readonly="readonly" placeholder="點選輸入框">
													</div>
												</td>
												<td><label class="col-sm-12 control-label">轉出日期(迄):</label></td>
												<td>
													<div class="col-sm-8">
														<input id="endDate"  type="text" class="form-control" readonly="readonly" placeholder="點選輸入框">
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
				<!-- end -->
				</div>
			<!-- 查詢結果 -->
		<div id="ajaxSearchResult"  class="col-xs-12">
		<!--固定id for window.resize start -->
				<div id="jQgrid">
					<table id="grid"></table>
					<div id="pager"></div>
				</div>
		<!--固定id for window.resize end -->
		</div>
</body>
</html>