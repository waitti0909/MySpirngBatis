<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="decorator"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<title>單張工單作業申請</title>
		
			<script type="text/javascript">
				$(document).ready(function() {
					//設備型態
					osSiteHuntEqp();
					
					buildCityOptions("siteWorkCity","siteWorkArea");
					LoadSelect2Script('<s:url value="/resources/plugins/select2/select2.min.js" />', Select2Test);
					WinMove();
					
					//查詢
					$('#btn_search').click(function() {
	 					var data= {
	 						appDept : $("#appDept").val(),
							siteWorkCity : $.trim($("#siteWorkCity").val()),
							siteWorkArea : $.trim($("#siteWorkArea").val()),
							siteHuntEqpTemp : $("#siteHuntEqpTemp").val(),
	 						btsSiteId : $("#btsSiteId").val(),
	 						siteName : $("#siteName").val(),
	 						workStatus : $("#workStatus").val(), 
	 						comSiteFeq : $("#comSiteFeq").val(),
	 					};
	 					//alert(JSON.stringify(data));
	 					$("#grid").setGridParam({datatype:"json", postData:data, mtype: 'POST',page:1}).jqGrid().trigger("reloadGrid");
					});//searchButton end					
					
					$("#grid").jqGrid({
						datatype: "local",
						pager: '#pager',
						url : CONTEXT_URL+"/st/st009/search",
						colNames:['基站編號','基站名稱','基站頻段','基站狀態','處理狀態','站點編號','地址','work_ID','work_STATUS'],
						colModel:[
                            {name:'bts_SITE_ID',index:'bts_SITE_ID',sortable: false},
							{name:'site_NAME',index:'site_NAME',sortable: false},
							{name:'srFeqId',index:'srFeqId',sortable: false},
							{name:'siteStatusName',index:'siteStatusName',sortable: false},
							{name:'workStatusName',index:'workStatusName',sortable: false},
							{name:'location_ID',index:'location_ID',sortable: false},
							{name:'addr',index:'addr', width:200 ,sortable: false},
							{name:'work_ID',index:'work_ID',hidden:true,sortable: false},
							{name:'work_STATUS',index:'work_STATUS',hidden:true,sortable: false},
						],
						viewrecords: true,
						caption:"單張工單清單",
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
							//alert(JSON.stringify(rowData));
				            updateSiteWork(rowData.work_ID,"view");		
							
						}
					});//end grid
					
					$(window).resize(function(){
						$(window).unbind("onresize");
						$("#grid,.grid").setGridWidth($("#jQgrid").width() -10);
						$(window).bind("onresize", this);
					}).trigger('resize');		
					
					
					//新增
					$('#btn_add').click(function() {		
						$.fancybox.open({
				 			href : CONTEXT_URL+"/st/st009/insertPage",
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
				 			title : "新增",
				 			openEffect : 'elastic',
				 			closeEffect : 'fade',
				 			ajax : {
				 	            type: 'POST'	 	            
				 	        },
				 	        afterClose : function() {
				 			}
				 		});		
					});
					
					//修改
					$('#btn_edit').click(function() {
						var id =$("#grid").jqGrid('getGridParam','selrow');
						if (id!="" && id!=null) {
							var row = $("#grid").jqGrid('getRowData', id);
							//alert(row.work_STATUS);
							//return false;
							if(row.work_STATUS != "W01" && row.work_STATUS != "W04"){
								alert("此處理狀態無法修改");
								return false;
							}
							updateSiteWork(row.work_ID ,"edit");		
						}else {
				        	alert("先選取一列資料列");
				        }		
					});
					
					//重置按鈕
					$('#btn_reset').click(function(){	
						$("#searchSiteLocForm").trigger('reset');
						
/* 						$("#workIds").val("");
						$("#orderId").val("");
						$("#btsSiteId").val("");
						$("#siteName").val("");
						$("#applyDateStart").val("");
						$("#applyDateEnd").val("");
						$("#repDept").val("");
						
						$("#siteWorkCity").val("").change();
						$("#siteWorkArea").select2({ allowClear: true });
						$("#repDept").select2({ allowClear: true });
						if($('#logInCovatNo').val() != "true") {						
							$("#orderStatus").val("").change();
							$("#coVatNo").val("").change();
						} */
						
						$("#grid").jqGrid().clearGridData();
						//first = 'true';
						
						//$('#btn_search').click();
					});							
				});
				
				
				function Select2Test() {
					$("select").select2();
				}
				
				//探勘紀錄 --設備型態
				function osSiteHuntEqp(){
					$.ajax({
						url : CONTEXT_URL + "/st/st009/siteHuntEqp",			
						type : "POST",
						dataType : 'json',
						async : false,
						success : function(data) {
							//if(data.success){
								if(data.success){
									if(data.rows.length > 0){
										$("#siteHuntEqpTemp  option").remove();
										$("#siteHuntEqpTemp").append("<option value=''>--請選擇--</option>");
										
										for(var i=0; i<data.rows.length; i++){		
											$("#siteHuntEqpTemp").append("<option value="+data.rows[i].value+">"+data.rows[i].label+"</option>");
										}
										
									}
								}
							//}
						}
					});
				}
				
				function updateSiteWork(workId ,status){
					$.fancybox.open({
			 			href : CONTEXT_URL+"/st/st009/updatePage",
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
			 	            	"workId" : workId,
			 	            	"status" : status
			 	            }
			 	        },
			 	        afterClose : function() {
			 			}
			 		});	
			}	
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
							<i class="fa fa-search"></i> <span>單張工單作業申請 查詢</span>
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
										<td><label class="col-sm-12 control-label">申請單位 :</label></td>
										<td>
											<div class="col-sm-8">
												<select  id="appDept" class="populate placeholder" name="appDept" >
													<option value="">---請選擇---</option>
													<c:forEach items="${appDept}" var="appDept">
														<option value="${appDept.value}">${appDept.label}</option>
													</c:forEach>													
												</select>
											</div>
										</td>
									</tr>
									<tr>
									<td><div class="clearfix">&nbsp;</div></td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td><label class="col-sm-12 control-label">行政區 : </label></td>
										<td>
											<div class="col-sm-6">
												<select id="siteWorkCity" class="populate placeholder" name="siteWorkCity" >
												</select>
											</div>
											<div class="col-sm-6">
												<select id="siteWorkArea" class="populate placeholder" name="siteWorkArea" >
												</select>
											</div>
										</td>
										<td><label class="col-sm-12 control-label">設備型態 :</label></td>
										<td>
											<div class="col-sm-8">
												<select  id="siteHuntEqpTemp" name="siteHuntEqpTemp" required="required">
													<option value="">---請選擇---</option>								
												</select>
											</div>
										</td>
									</tr>
									<tr>
										<td><div class="clearfix">&nbsp;</div></td>
									</tr>
									<tr>
										<td></td>
										<td><label class="col-sm-12 control-label">基站編號 :</label></td>
										<td>
											<div class="col-sm-6">
												<input id="btsSiteId" type="text" name="btsSiteId" class="form-control">
											</div>
										</td>
										<td><label class="col-sm-12 control-label">基站名稱 :</label></td>
										<td>
											<div class="col-sm-6">
												<input id="siteName" type="text" name="siteName" class="form-control">
											</div>
										</td>
									</tr>
									<tr>
									<td><div class="clearfix">&nbsp;</div></td>
									</tr>																		
									<tr>
										<td></td>
										<td><label class="col-sm-12 control-label">處理狀態 :</label></td>
										<td>
											<div class="col-sm-8">
												<select  id="workStatus" class="populate placeholder" name="workStatus" >
													<option value="">---請選擇---</option>
													<c:forEach items="${allWorkStatus}" var="workStatus">
														<option value="${workStatus.value}">${workStatus.label}</option>
													</c:forEach>
												</select>
											</div>
										</td>
										<td><label class="col-sm-12 control-label">基站頻段 :</label></td>
										<td>
											<div class="col-sm-8">
												<select  id="comSiteFeq" class="populate placeholder" name="comSiteFeq" >
													<option value="">---請選擇---</option>
													<c:forEach items="${comSiteFeq}" var="siteFeq">
														<option value="${siteFeq.FEQ_ID}">${siteFeq.FEQ_NAME}</option>
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
			<div id="ajaxSearchResult"  class="col-xs-12">
<!-- 		固定id for window.resize start -->
				<div id="jQgrid">
					<table id="grid"></table>
					<div id="pager"></div>
				</div>
<!-- 	 	固定id for window.resize end -->
			</div>
		</div>
		<input id="logInCovatNo" type="hidden" value="${logInCovatNo}">
		<input id="covatNo" type="hidden" value="${covatNo}">	
		<input id="workType" type="hidden" />
	</body>
</html>