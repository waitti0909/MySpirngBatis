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
		<title>單張工單作業處理</title>
		
			<script type="text/javascript">
				$(document).ready(function() {
					buildCityOptions("siteWorkCity","siteWorkArea");
					osSiteOrderType();
					
					if ($('#logInCovatNo').val() == "true"){
						$('#coVatNo').val($('#covatNo').val())
						$("#coVatNo").prop("disabled", true);
						$('#orderStatus').val("OR06");
						$("#orderStatus").prop("disabled", true);
					} else if($('#isManager').val() == "true") {
						$('#orderStatus').val("OR03");
					} 
/* 					else {
						$('#orderStatus').val("OR04");
					} */
					
					LoadSelect2Script('<s:url value="/resources/plugins/select2/select2.min.js" />', Select2Test);
					WinMove();
					
					//查詢
					$('#btn_search').click(function() {
	 					var data= {
	 						workId : $("#workIds").val(),
	 						workType : $("#workType").val(),
	 						btsSiteId : $("#btsSiteId").val(),
	 						siteName : $("#siteName").val(),
	 						orderId : $("#orderId").val(),
	 						repDept : $("#repDept").val(),
	 						//repUser : $("#repUser").val(),
	 						orderStatus : $("#orderStatus").val(), 
							siteWorkCity : $.trim($("#siteWorkCity").val()),
							siteWorkArea : $.trim($("#siteWorkArea").val()),
							//applyDateStart:$.trim($("#applyDateStart").val()),
							//applyDateEnd:$.trim($("#applyDateEnd").val()),
							coVatNo : $("#coVatNo").val(),
							siteOrderType : $("#siteOrderTypeSelect").val(),
	 					};
	 					//alert(JSON.stringify(data));
	 					$("#grid").setGridParam({datatype:"json", postData:data, mtype: 'POST',page:1}).jqGrid().trigger("reloadGrid");
					});//searchButton end					
					
					$("#grid").jqGrid({
						datatype: "local",
						pager: '#pager',
						url : CONTEXT_URL+"/st/st010/search",
					   	colNames:['作業編號','工單類別','基站編號','基站名稱','基站頻段','工單號碼','接工單位','負責人','狀態','rep_DEPT','pick_USER','end_DESC','odr_STATUS','siteId'],
					   	colModel:[				   
							{name:'work_ID',index:'work_ID',width:90, sortable: false},
							{name:'orderTypeName',index:'orderTypeName', width:100,sortable: false},
							{name:'btsSiteId',index:'bts_SITE_ID',width:100, sortable: false},
							{name:'siteName',index:'site_NAME', width:100,sortable: false},
							{name:'feqName',index:'feqName', width:80,sortable: false},
							{name:'order_ID',index:'order_ID', width:150,sortable: false},
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
					   	caption:"單張工單作業處理清單",
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
				            updateSiteWork(rowData.work_ID, 
			         				   rowData.order_ID, 
			         				   rowData.pick_USER, 
			         				   rowData.end_DESC, 
			         				   "view",
			         				   rowData.orderTypeName,
			         				   rowData.odr_STATUS,
			         				   rowData.siteId);		            
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
				            if(row.odr_STATUS != "OR04" && row.odr_STATUS != "OR05" && row.odr_STATUS != "OR06"){  
								alert("此工單狀態無法修改");
								return false;
							}
							
				            updateSiteWork(row.work_ID, 
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
										
				});
				
				//派工
				$('#btn_st_assign').click(function() {
					var id =$("#grid").jqGrid('getGridParam','selrow');
					if (id!="" && id!=null) {
						var row = $("#grid").jqGrid('getRowData', id);
						//alert(row);
						if(row.odr_STATUS != "OR03"){
							alert("此工單狀態無法派工");
							return false;
						} 
/* 						else if (!preOrdersAllFinished(row.work_ID, row.order_ID)) {	// add by Charlie 20150110 增加判斷前序號工單是否皆已完成
							alert("尚有前置工單未完工，無法進行派工作業");
							return false;
						} */
						assignPage(row.work_ID ,"edit",row.rep_DEPT,row.deptName,row.order_ID);		
					}else {
			        	alert("先選取一列資料列");
			        }	 		
				});//派工 end
				
				//派工
				function assignPage (workId ,status ,rep_DEPT ,deptName, orderId) {
					$.fancybox.open({
						href : CONTEXT_URL+"/st/st010/assignPage",
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
					$("select").select2();
				}
				
				//修改
				function updateSiteWork(workId, orderId, pickUser, endDesc, status,
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
					            	"workId" : workId,
					            	"orderId" : orderId,
					            	"pickUser" : pickUser,
					            	"endDesc" : endDesc,
					            	"status" : status,
					            	"orderTypeName" : orderTypeName,
					            	"orderStatus" : orderStatus ,
				  		      		"siteId" : siteId
					            }
					        }
						});
				}	
				
				
			//工單類別
			function osSiteOrderType(){
				$.ajax({
					url : CONTEXT_URL + "/st/st009/initOrderType",			
					type : "POST",
					dataType : 'json',
					async : false,
					success : function(data) {
						if(data.success){
							if(data.rows.length > 0){
								$("#siteOrderTypeSelect  option").remove();
								$("#siteOrderTypeSelect").append("<option value=''>--請選擇--</option>");
								//alert(JSON.stringify(data.rows));
								for(var i=0; i<data.rows.length; i++){		
									$("#siteOrderTypeSelect").append("<option value="+data.rows[i].od_TYPE_ID+">"+data.rows[i].od_TYPE_NAME+"</option>");
								}			
							}
						}
					}
				});
			}	

			//重置按鈕
			$('#btn_reset').click(function(){	
				//$("#searchSiteLocForm").trigger('reset');
				
				$("#workIds").val("");
				$("#orderId").val("");
				$("#btsSiteId").val("");
				$("#siteName").val("");
				$("#applyDateStart").val("");
				$("#applyDateEnd").val("");
				$("#repDept").val("");
				
				$("#siteOrderTypeSelect").val("").change();
				$("#siteWorkCity").val("").change();
				$("#siteWorkArea").select2({ allowClear: true });
				$("#repDept").select2({ allowClear: true });
				if($('#logInCovatNo').val() != "true") {						
					$("#orderStatus").val("").change();
					$("#coVatNo").val("").change();
				}
				$("#siteOrderType").val("").change();
				
				$("#grid").jqGrid().clearGridData();
				//first = 'true';
				
				//$('#btn_search').click();
			});	
			
			// add by Charlie 20150110 
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
							<i class="fa fa-search"></i> <span>單張工單作業處理 查詢</span>
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
										<td><label class="col-sm-12 control-label">作業編號 :</label></td>
										<td width="35%">
											<div class="col-sm-6">
												<input id="workIds" name="workId" type="text" class="form-control">
											</div>
										</td>
										<td><label class="col-sm-12 control-label">工單編號 :</label></td>
										<td>
											<div class="col-sm-6">
												<input id="orderId" name="orderId" type="text" class="form-control">
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
<!-- 									<tr>
										<td></td>
										<td><label class="col-sm-12 control-label">申請日期(起) : </label></td>
										<td>
											<div class="col-sm-6">
											<input id="applyDateStart" name="applyDateStart" type="text"
									class="form-control" readonly="readonly" placeholder="點選輸入框">
											</div>
										</td>
										<td nowrap="nowrap"><label class="col-sm-12 control-label">申請日期(迄) : </label></td>
										<td>
											<div class="col-sm-6">
													<input id="applyDateEnd" name="applyDateEnd" type="text"
									class="form-control" readonly="readonly" placeholder="點選輸入框">
											</div>
										</td>
									</tr>	 -->								
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
										<td><label class="col-sm-12 control-label">接工單位 :</label></td>
										<td>
											<div class="col-sm-8">
												<select  id="repDept" class="populate placeholder" name="repDept" >
													<option value="">---請選擇---</option>
													<c:forEach items="${allRepDept }" var="repDept">
														<option value="${repDept.value}">${repDept.label}</option>
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
										<td><label class="col-sm-12 control-label">工單狀態 :</label></td>
										<td>
											<div class="col-sm-8">
												<select  id="orderStatus" class="populate placeholder" name="orderStatus" >
													<option value="">---請選擇---</option>
													<option value="todo">未結案</option>
													<c:forEach items="${allOrderStatus}" var="orderStatus">
														<option value="${orderStatus.value}">${orderStatus.label}</option>
													</c:forEach>
												</select>
											</div>
										</td>
										<td><label class="col-sm-12 control-label">委外廠商 :</label></td>
										<td>
											<div class="col-sm-8">
												<select  id="coVatNo" class="populate placeholder" name="coVatNo" >
													<option value="">---請選擇---</option>
													<c:forEach items="${allCompany}" var="company">
														<option value="${company.CO_VAT_NO}">${company.CO_NAME}</option>
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
										<td nowrap="nowrap"><label class="col-sm-12 control-label">工單類別 :</label></td>
										<td>		
											<div class="col-sm-8">										
											<select  id="siteOrderTypeSelect" name="siteOrderType" required="required">
												<option value="">---請選擇---</option>								
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