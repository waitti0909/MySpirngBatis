<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<title>站點維護</title>
		
		<script type="text/javascript">
			$(document).ready(function() {
				buildCityOptions("locCity","locTown");
				LoadSelect2Script(
					'<s:url value="/resources/plugins/select2/select2.min.js" />',Select2Test);
				WinMove();
				$("#btn_reset").click(function(){
					$("#searchSiteLocForm").trigger('reset');
					$("#grid").jqGrid().clearGridData();
					$('#locCity').select2({ allowClear: true }).change();
					$('#locTown').select2({ allowClear: true });
					$('#locType').select2({ allowClear: true });
					$('#siteStatus').select2({ allowClear: true });
					$('#siteFeq').select2({ allowClear: true });
				});//reset button end
				$("#btn_search").click(function(){
					var data= {
							locationId : $("#locationId").val(),
							btsSiteId : $("#btsSiteId").val(),
							locName : $("#locName").val(),
							locType : $("#locType").val(),
							locCity : $("#locCity").val(),
							locTown : $("#locTown").val(),
							siteStatus : $("#siteStatus").val(),
							siteFeq : $("#siteFeq").val(),
					};
					$("#grid").setGridParam({datatype:"json", postData:data, mtype: 'POST',page:1}).jqGrid().trigger("reloadGrid");
				});//search button end
				$('#btn_add').click(function() {
					$.fancybox.open({
			 			href : CONTEXT_URL+"/st/st001/insertPage",
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
				});//addButton end
				
				$('#btn_edit').click(function() {
					var id =$("#grid").jqGrid('getGridParam','selrow');
					if (id!="" && id!=null) {
						var row = $("#grid").jqGrid('getRowData', id);
						updateSiteLoc(row.location_ID,"edit");
					}else {
// 			        	swal("", "先選取一列資料列", "warning");
			        	alert("先選取一列資料列");
			        }	 		
				});//updateButton end
				
				$("#grid").jqGrid({
					datatype: "local",
					pager: '#pager',
					url : CONTEXT_URL+"/st/st001/search",
				   	colNames:['站點編號','站點名稱','基站編號','行政區','地址','站點類別','負責人'],
				   	colModel:[
						{name:'location_ID',index:'location_ID',width:100, sortable: false},
				   		{name:'loc_NAME',index:'loc_NAME',width:100, sortable: false},
				   		{name:'btsSiteId',index:'btsSiteId',width:100, sortable: false},
				   		{name:'townName',index:'townName', width:50,sortable: false},
				   		{name:'addr',index:'addr', width:200,sortable: false},
				   		{name:'locTypeName',index:'locTypeName', width:108,sortable: false},
				   		{name:'maintChnNm',index:'maintChnNm', width:60,sortable: false}
				   	],	
				   	viewrecords: true,
				   	caption:"站點維護清單",
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
			            	if(i=="location_ID"){
			            		updateSiteLoc(item,"view");	
			            	}
			            });
			            
			        }
				});//end grid
				
				$(window).resize(function(){
				    $(window).unbind("onresize");
				    $("#grid,.grid").setGridWidth($("#jQgrid").width() -10);
				    $(window).bind("onresize", this);
				}).trigger('resize');
			});//document.ready end
			
			function updateSiteLoc(location_ID,eventType){
				$.fancybox.open({
		 			href : CONTEXT_URL+"/st/st001/updatePage",
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
		 	            	"eventType" : eventType,
		 	            	"locationId" : location_ID,
		 	            }
		 	        },
		 	        afterClose : function() {
		 			}
				});
			}
			
			function Select2Test() {
				$("#locType").select2();
				$("#locCity").select2();
				$("#locTown").select2();
				$("#siteStatus").select2();
				$("#siteFeq").select2();
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
							<i class="fa fa-search"></i> <span>站點維護 站點查詢</span>
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
										<td><label class="col-sm-12 control-label">站點編號 :</label></td>
										<td width="35%">
											<div class="col-sm-6">
												<input id="locationId" type="text" style="padding-right : 0px;" class="form-control">
											</div>
										</td>
										<td><label class="col-sm-12 control-label">基站編號 :</label></td>
										<td>
											<div class="col-sm-6">
												<input id="btsSiteId"  type="text" class="form-control">
											</div>
										</td>
									</tr>
									<tr>
									<td><div class="clearfix">&nbsp;</div></td>
									</tr>
									<tr>
										<td></td>
										<td><label class="col-sm-12 control-label">站點名稱 :</label></td>
										<td>
											<div class="col-sm-12">
												<input id="locName" type="text" class="form-control" placeholder="關鍵字查詢">
											</div>
										</td>
										<td><label class="col-sm-12 control-label">站點類別 :</label></td>
										<td>
											<div class="col-sm-6">
												<select id="locType" class="populate placeholder" name="country" >
													<option value="">---請選擇---</option>
													<c:forEach items="${allSiteTypes}" var="siteType">
														<option value="${siteType.value}">${siteType.label}</option>
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
												<select id="locCity" class="populate placeholder" name="country" >
												</select>
											</div>
											<div class="col-sm-6">
												<select id="locTown" class="populate placeholder" name="country" >
												</select>
											</div>
										</td>
									</tr>
									<tr>
										<td><div class="clearfix">&nbsp;</div></td>
									</tr>
									<tr>
										<td></td>
										<td><label class="col-sm-12 control-label">基站狀態 :</label></td>
										<td>
											<div class="col-sm-6">
												<select id="siteStatus" class="populate placeholder" name="country" >
													<option value="">---請選擇---</option>
													<c:forEach items="${allSiteStatus}" var="siteStatus">
														<option value="${siteStatus.value}">${siteStatus.label}</option>
													</c:forEach>
												</select>
											</div>
										</td>
										<td><label class="col-sm-12 control-label">基站頻段 :</label></td>
										<td>
											<div class="col-sm-6">
												<select id="siteFeq" class="populate placeholder" name="country" >
													<option value="">---請選擇---</option>
													<c:forEach items="${allSiteFeq}" var="siteFeq">
														<option value="${siteFeq.value}">${siteFeq.label}</option>
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
	</body>
</html>