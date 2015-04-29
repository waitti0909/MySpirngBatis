<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<title>機房資訊維護</title>
		
		<script type="text/javascript">
			$(document).ready(function() {
				buildCityOptions("engineRoom_City","engineRoom_Area");
				LoadSelect2Script(
					'<s:url value="/resources/plugins/select2/select2.min.js" />',Select2Test);
				WinMove();
				$("#btn_reset").click(function(){
					$("#searchEngineRoomForm").trigger('reset');
					$("#grid").jqGrid().clearGridData();
					$('#engineRoom_City').select2({ allowClear: true }).change();
					$('#engineRoom_Area').select2({ allowClear: true });
					$('#engineRoom_Type').select2({ allowClear: true });
					$('#engineRoom_Status').select2({ allowClear: true });
				});//reset button end
				$("#btn_search").click(function(){
					var data={
						btsSiteId : $("#engineRoom_BtsSiteId").val(),
						engineRoomStatus : $("#engineRoom_Status").val(),
						engineRoomName : $("#engineRoom_Name").val(),
						engineRoomType : $("#engineRoom_Type").val(),
						engineRoomCity : $("#engineRoom_City").val(),
						engineRoomArea : $("#engineRoom_Area").val(),
					}
					$("#grid").setGridParam({datatype:"json", postData:data, mtype: 'POST',page:1}).jqGrid().trigger("reloadGrid");
				});//search button end
				$('#btn_add').click(function() {
					$.fancybox.open({
			 			href : CONTEXT_URL+"/st/engineRoom/insertPage",
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
						updateEngineRoom(row.location_ID,row.siteId,"edit");
					}else {
			        	alert("先選取一列資料列");
			        }	 		
				});//updateButton end
				
				$("#grid").jqGrid({
					datatype: "local",
					pager: '#pager',
					url : CONTEXT_URL+"/st/engineRoom/search",
				   	colNames:['站台編號','機房名稱','行政區','地址','機房類別','負責人','狀態','locationId','siteId'],
				   	colModel:[
						{name:'btsSiteId',index:'btsSiteId',width:100, sortable: false},
				   		{name:'loc_NAME',index:'loc_NAME',width:100, sortable: false},
				   		{name:'cityAreaName',index:'cityAreaName',width:100, sortable: false},
				   		{name:'addr',index:'addr', width:200,sortable: false},
				   		{name:'loc_TYPE',index:'loc_TYPE', width:100,sortable: false},
				   		{name:'maintChnNm',index:'maintChnNm', width:108,sortable: false},
				   		{name:'siteStatus',index:'siteStatus', width:60,sortable: false},
				   		{name:'location_ID',index:'location_ID', hidden:true,sortable: false},
				   		{name:'siteId',index:'siteId', hidden:true,sortable: false},
				   	],	
				   	viewrecords: true,
				   	caption:"機房資訊維護清單",
					rownumbers:true,
					multiselect: false,
				   	onSelectRow: function(id){ 
				   		
				    },
					ondblClickRow: function(rowId) { //double clicking a jqgrid row
			            var rowData = jQuery(this).getRowData(rowId);
			            var locationId = "";
			            var siteId = "";
			            $.each(rowData, function(i,item) {
							if(i=="siteId"){
								siteId = item;
							}		
			            	if(i=="location_ID"){
			            		locationId = item;
			            	}
			            });
			            updateEngineRoom(locationId,siteId,'view');
			        }
				});//end grid
				
				$(window).resize(function(){
				    $(window).unbind("onresize");
				    $("#grid,.grid").setGridWidth($("#jQgrid").width() -10);
				    $(window).bind("onresize", this);
				}).trigger('resize');
			});//document.ready end
			
			function updateEngineRoom(location_ID,siteId,eventType){
				$.fancybox.open({
		 			href : CONTEXT_URL+"/st/engineRoom/updatePage",
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
		 	            	"siteId" : siteId,
		 	            }
		 	        },
		 	        afterClose : function() {
		 			}
				});
			}
			
			function Select2Test() {
				$("select").select2();
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
							<i class="fa fa-search"></i> <span>機房維護 機房查詢</span>
						</div>
						<div class="box-icons">
							<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
							</a> <a class="expand-link"> <i class="fa fa-expand"></i>
							</a>
						</div>
						<div class="no-move"></div>
					</div>
					<div class="box-content">
						<form role="form" class="form-horizontal bootstrap-validator-form" novalidate="novalidate" id="searchEngineRoomForm">
							<div class="form-group">
								<!--  系統功能  -->
								<table style="width:100%">
									<tr>
										<td><label class="col-sm-12 control-label">站台編號 :</label></td>
										<td width="35%">
											<div class="col-sm-6">
												<input id="engineRoom_BtsSiteId" type="text" style="padding-right : 0px;" class="form-control">
											</div>
										</td>
										<td><label class="col-sm-12 control-label">狀態 :</label></td>
										<td width="35%">
											<div class="col-sm-6">
												<select id="engineRoom_Status" class="populate placeholder" name="country" >
													<option value="">---請選擇---</option>
													<option value="S04">待建設</option>
													<c:forEach items="${allSiteStatus}" var="SiteStatus">
														<option value="${SiteStatus.value}">${SiteStatus.label}</option>
													</c:forEach>
												</select>
											</div>
										</td>
									</tr>
									<tr>
										<td><div class="clearfix">&nbsp;</div></td>
									</tr>
									<tr>
										<td><label class="col-sm-12 control-label">機房名稱 :</label></td>
										<td>
											<div class="col-sm-12">
												<input id="engineRoom_Name" type="text" class="form-control" placeholder="關鍵字查詢">
											</div>
										</td>
										<td><label class="col-sm-12 control-label">機房類別 :</label></td>
										<td>
											<div class="col-sm-6">
												<select id="engineRoom_Type" class="populate placeholder" name="country" >
													<option value="">---請選擇---</option>
													<c:forEach items="${allSiteType}" var="siteType">
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
										<td><label class="col-sm-12 control-label">行政區 : </label></td>
										<td>
											<div class="col-sm-6">
												<select id="engineRoom_City" class="populate placeholder" name="country" >
												</select>
											</div>
											<div class="col-sm-6">
												<select id="engineRoom_Area" class="populate placeholder" name="country" >
												</select>
											</div>
										</td>
									</tr>
									<tr>
										<td><div class="clearfix">&nbsp;</div></td>
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