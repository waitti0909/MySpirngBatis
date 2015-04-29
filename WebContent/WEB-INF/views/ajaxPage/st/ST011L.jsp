<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<html lang="en">
<head>
<title>基站查詢</title>
<script type="text/javascript">
				$(document).ready(function() {
					buildCityOptions("locCity","locTown");
					LoadSelect2Script('<s:url value="/resources/plugins/select2/select2.min.js" />', Select2Test);
					WinMove();
					
					//window.open(' 新視窗的網址 ', '新視窗的名稱', config='height=高度,width=寬度');
					$('#btn_search').click(function() {
						var data= {
								btsSiteId : $("#btsSiteId").val(),// 基站編號
								siteName : $("#siteName").val(),//基站名稱 
								feqId : $("#siteFeq").val(),//基站頻段
								eqpTypeId : $("#eqpType").val(),//設備型態
								city : $.trim($("#locCity").val()),//行政區
								area : $.trim($("#locTown").val()),//行政區
								siteStatus : $("#siteStatus").val(),//基站狀態
								feederless : $("#feederless").val()//feederless
			 		};

						$("#grid").setGridParam({datatype:"json", postData:data, mtype: 'POST',page:1}).jqGrid().trigger("reloadGrid");
						
					});//addButton end
					
					$("#btn_reset").click(function(){
						$("#grid").jqGrid().clearGridData();
						$("#btsSiteId").val('');// 基站編號
						$("#siteName").val('');//基站名稱 
						$('#siteFeq').select2("val",null);//基站頻段
						$('#eqpType').select2("val",null);//設備型態
						$('#locCity').select2("val",null).change();	//行政區				
						$('#siteStatus').select2("val",null);//基站狀態
						$('#feederless').select2("val",null);//feederless
						
					});//reset button end
					
					$('#btn_view').click(function() {
						window.open('./ST011M.html', '基站基本資料', config='height=768,width=1024').focus(); 		
					});//addButton end
					
					$("#grid").jqGrid({
						datatype: "local",
						pager: '#pager',
						url : CONTEXT_URL+"/st/st011/getSiteMainList",
						colNames:['','基站編號','基站名稱','基站頻段','基站狀態','站點編號','地址'],
						colModel:[
							{
								name:'site_ID',index:'site_ID',width:100, hidden:true
							},
							{name:'bts_SITE_ID',index:'bts_SITE_ID',width:100, sortable: false},
							{name:'site_NAME',index:'site_NAME',width:100, sortable: false},
							{name:'feqName',index:'feqName',width:100, sortable: false},
							{name:'siteStatusName',index:'siteStatusName', width:100,sortable: false},
							{name:'location_ID',index:'location_ID', width:100,sortable: false},
							{name:'locAddr',index:'locAddr', width:150,sortable: false},
						],	
						viewrecords: true,
						caption:"基站清單",
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
				            	if(i =='site_ID'){
				            		$.fancybox.open({
							 			href : CONTEXT_URL+"/st/st011/viewDetails",
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
							 	            	"siteId" : item,
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
				<div id="ajax-content">
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
									<i class="fa fa-search"></i> <span>基站查詢</span>
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
												<td><label class="col-sm-12 control-label">基站編號 :</label></td>
												<td>
													<div class="col-sm-6">
														<input id="btsSiteId" name="btsSiteId"  type="text" class="form-control">
													</div>
												</td>
												<td><label class="col-sm-12 control-label">基站名稱 :</label></td>
												<td>
													<div class="col-sm-6">
														<input id="siteName" name="siteName"  type="text" class="form-control">
													</div>
												</td>
											</tr>
											<tr>
											<td><div class="clearfix">&nbsp;</div></td>
											</tr>
											<tr>
												<td></td>
												<td><label class="col-sm-12 control-label">基站頻段 :</label></td>
												<td>
													<div class="col-sm-6">
														<select id="siteFeq" class="populate placeholder" name="siteFeq" >
															<option value="">---請選擇---</option>
															<c:forEach items="${allSiteFeq}" var="siteFeq">
																<option value="${siteFeq.value}">${siteFeq.label}</option>
															</c:forEach>
														</select>
													</div>
												</td>
												<td><label class="col-sm-12 control-label">設備型態 :</label></td>
												<td>
													<div class="col-sm-6">
														<select id="eqpType" class="populate placeholder" name="eqpType" >
															<option value="">---請選擇---</option>
															<c:forEach items="${allEqpTypes}" var="eqpType">
																<option value="${eqpType.value}">${eqpType.label}</option>
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
												<td><label class="col-sm-12 control-label">行政區 : </label></td>
												<td>
													<div class="col-sm-6">
														<select id="locCity" class="populate placeholder" name="locCity" ></select>
													</div>
													<div class="col-sm-6">
														<select id="locTown" class="populate placeholder" name="locTown" ></select>
													</div>
												</td>
												<td><label class="col-sm-12 control-label">基站狀態:</label></td>
												<td>
													<div class="col-sm-6">
														<select id="siteStatus" name="siteStatus" class="populate placeholder">
															<option value="">---請選擇---</option>
															
															<c:forEach items="${allSiteStatus}" var="siteStatus">
																<option value="${siteStatus.value}">${siteStatus.label}</option>
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
												<td><label class="col-sm-12 control-label">Feederless :</label></td>
												<td>
													<div class="col-sm-6">
														<select id="feederless" name="feederless" class="populate placeholder">
															<option value="">---請選擇---</option>
															
															<c:forEach items="${allFeederless}" var="feederless">
																<option value="${feederless.value}">${feederless.label}</option>
															</c:forEach>
														</select>
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