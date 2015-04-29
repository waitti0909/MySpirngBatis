<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<title>Search Ring ID</title>
		
		<script type="text/javascript">
			$(document).ready(function() {
				buildCityOptions("searchRingCity","searchRingArea");
				LoadSelect2Script(
					'<s:url value="/resources/plugins/select2/select2.min.js" />',Select2Test);
				WinMove();
				$("#btn_reset").click(function(){
					$("#searchRingForm").trigger('reset');
					$("#feqId").select2({ allowClear: true });
					$("#searchRingCity").select2({ allowClear: true }).change();
					$("#searchRingArea").select2({ allowClear: true });
					$("#grid").jqGrid().clearGridData();
				});//reset button end
				$("#btn_search").click(function(){
					var srLon = $("#searchRingLon").val();
					var srLat = $("#searchRingLat").val();
					var msg = validateLatAndLon(srLon,srLat,"search");
					if(msg !=""){
						alert(msg);
						return false;
					}
					var data= {
							srId : $("#searchRingId").val(),
							lon : srLon,
							lat : srLat,
							coverRange : $("#searchRingCoverRange").val(),
							city : $("#searchRingCity").val(),
							area : $("#searchRingArea").val(),
							feqId : $("#feqId").val(),
						};
					$("#grid").setGridParam({datatype:"json", postData:data, mtype: 'POST',page:1}).jqGrid().trigger("reloadGrid");
				});//search button end
				
				
				$('#btn_add').click(function() {
					$.fancybox.open({
			 			href : CONTEXT_URL+"/st/sr/searchRing/insertPage",
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
						update(row.sr_ID,"edit");
					}else {
			        	alert("先選取一列資料列");
			        }	 		
				});//updateButton end
				
				$("#grid").jqGrid({
					datatype: "local",
					pager: '#pager',
					url : CONTEXT_URL+"/st/sr/ST002SP2/search",
				   	colNames:['SR_ID','基站頻段','經度','緯度','涵蓋範圍','站台'],
				   	colModel:[
						{name:'sr_ID',index:'sr_ID', width :170, sortable: false},
						{name:'feqName',index:'feqName', width :170, sortable: false,formatter : feqNameFormat},
						{name:'sr_LON',index:'sr_LON', width :170, sortable: false,formatter : lonFormat},
						{name:'sr_LAT',index:'sr_LAT', width :170,sortable: false,formatter : latFormat},
						{name:'sr_COVER_RANGE',index:'sr_COVER_RANGE', width :180, sortable: false},
				   		{name:'btsSiteId',index:'btsSiteId', width :180,sortable: false},
				   	],	
				   	viewrecords: true,
				   	caption:"Search Ring ID清單",
					rownumbers:true,
					multiselect: false,
				   	onSelectRow: function(id){ 
				   		
				    },
				    gridComplete: function(){
						var ids = jQuery("#srGrid").jqGrid('getDataIDs');
						for(var i=0;i < ids.length;i++){
							var cl = ids[i];  // cl:jqgrid的行數，非資料真正的HEADER_ID
							$("#srGrid").jqGrid('getRowData', cl);
						}	
					},
					ondblClickRow: function(rowId) { //double clicking a jqgrid row
			           var rowData = jQuery(this).getRowData(rowId);
			            $.each(rowData, function(i,item) {
			            	if(i=="sr_ID"){
			            		update(item,"view");	
			            	}
			            });
			            
			        }
				});//end grid
				
				$(window).resize(function(){
				    $(window).unbind("onresize");
				    $("#grid,.grid").setGridWidth($("#jQgrid").width() -10);
				    $(window).bind("onresize", this);
				}).trigger('resize');
				filterText("searchRingLon");
				filterText("searchRingLat");
			});//document.ready end
			function filterText(tagName){
				$("#"+tagName).keyup(function(){
					if(!(/^[1-9]\d*\.?\d*$/gi.test($(this).val()))){
						$(this).val("");
					}
// 					$(this).val($(this).val().replace(/[^\d\./{1,1/}]/gi,""));
// 					 $(this).val($(this).val().replace(/^\./g,""));
				});
				$("#"+tagName).keydown(function(){
					if(!(/^[1-9]\d*\.?\d*$/gi.test($(this).val()))){
						$(this).val("");
					}
// 					 $(this).val($(this).val().replace(/^\d+\.\d+$/gi,""));
// 					 $(this).val($(this).val().replace(/^\./g,""));
					 
				});
			}
			
			function update(srId,status){
				$.fancybox.open({
		 			href : CONTEXT_URL+"/st/sr/searchRing/updatePage",
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
	 	            	data : {
	 	 					"srId" : srId,
	 	 					"status" : status
	 	 				},
		 	        },
		 	        afterClose : function() {
		 			}
		 		});
			}
			
			function Select2Test() {
				$("#feqId").select2();
				$("#searchRingCity").select2();
				$("#searchRingArea").select2();
			}
			function lonFormat( cellvalue, options, cell){
				var lon = cellvalue.toFixed(6)
				return lon;
	        }
			function latFormat( cellvalue, options, cell){
				var lat = cellvalue.toFixed(6)
				return lat;
	        }
			function feqNameFormat( cellvalue, options, cell){
		        if(cellvalue==""||cellvalue==null){
		            return "共用";
		        }else{
		            return cellvalue;
		        }
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
							<i class="fa fa-search"></i> <span>Search Ring 維護 Search Ring 查詢</span>
						</div>
						<div class="box-icons">
							<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
							</a> <a class="expand-link"> <i class="fa fa-expand"></i>
							</a>
						</div>
						<div class="no-move"></div>
					</div>
					<div class="box-content">
						<form role="form" class="form-horizontal bootstrap-validator-form" novalidate="novalidate" id="searchRingForm" >
							<div class="form-group">
								<!--  系統功能  -->
								<table style="width:100%">
									<tr>
										<td><label class="col-sm-10"></label></td>
										<td><label class="col-sm-12 control-label">SR ID :</label></td>
										<td width="35%">
											<div class="col-sm-7">
												<input id="searchRingId" type="text" class="form-control">
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
										<td><label class="col-sm-12 control-label">基站頻段 :</label></td>
										<td>
											<div class="col-sm-7">
												<select id="feqId" class="populate placeholder">
													<option value="">---請選擇---</option>
													<option value="all">共用</option>
													<c:forEach items="${allSiteFeq}" var="siteFeq">
														<option value="${siteFeq.value}">${siteFeq.label}</option>
													</c:forEach>
												</select>

											</div>
<!-- 											<div class="col-sm-6"> -->
<%-- 												<select id="searchRingCity" class="populate placeholder"> --%>
<%-- 												</select> --%>
<!-- 											</div> -->
<!-- 											<div class="col-sm-6"> -->
<%-- 												<select id="searchRingArea" class="populate placeholder"> --%>
<%-- 												</select> --%>
<!-- 											</div> -->
										</td>
										<td><label class="col-sm-12 control-label">行政區 :</label></td>
										<td>
											<div class="col-sm-6">
												<select id="searchRingCity" class="populate placeholder">
												</select>
											</div>
											<div class="col-sm-6">
												<select id="searchRingArea" class="populate placeholder">
												</select>
											</div>
<!-- 											<div class="col-sm-6"> -->
<%-- 											<select id="feqId" class="populate placeholder"> --%>
<!-- 													<option value="">---請選擇---</option> -->
<%-- 													<c:forEach items="${allSiteFeq}" var="siteFeq"> --%>
<%-- 														<option value="${siteFeq.value}">${siteFeq.label}</option> --%>
<%-- 													</c:forEach> --%>
<%-- 												</select> --%>
<!-- 											</div> -->
										</td>
									</tr>
									<tr>
										<td><div class="clearfix">&nbsp;</div></td>
									</tr>
									<tr>
										<td></td>
										<td><label class="col-sm-12 control-label">經度 :</label></td>
										<td>
											<div class="col-sm-7">
												<input id="searchRingLon" type="text" class="form-control">
											</div>
											<p style="margin-top : 5px;">(+-0.001)</p>
										</td>
										<td><label class="col-sm-12 control-label">緯度 :</label></td>
										<td>
											<div class="col-sm-7">
												<input id="searchRingLat" type="text" class="form-control">
											</div>
											<p style="margin-top : 5px;">(+-0.001)</p>
										</td>
									</tr>
									<tr>
										<td><div class="clearfix">&nbsp;</div></td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td><label class="col-sm-12 control-label">涵蓋範圍關鍵字 : </label></td>
										<td colspan="3">
											<div class="col-sm-10">
												<input id="searchRingCoverRange" type="text" class="form-control" placeholder="關鍵字查詢">
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