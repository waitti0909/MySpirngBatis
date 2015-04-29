<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<title>SR ID查詢</title>
		<style>
		.labelStyle {
		   margin-right: -80px;
		}
		</style>
		<script type="text/javascript">
			$(document).ready(function() {
				$("#srSearchBtn").click(function(){
					var lon = $("#lon").val();
					var lat = $("#lat").val();
					var errorMsg = validateLatAndLon(lon,lat,"search");
					if(errorMsg != ""){
						alert(errorMsg);
						return false;
					}
					var data= {
							srId : $("#st002sp2_srId").val(),
							lon : lon,
							lat : lat,
							coverRange : $("#srCoverRange").val(),
							city : '',
							area : '',
							feqId :　$("#srFeqId").val(),
							isST002SP2Page :　'Y',
						};
					$("#srGrid").setGridParam({datatype:"json", postData:data, mtype: 'POST',page:1}).jqGrid().trigger("reloadGrid");
				});//search button end
				
				$("#srOkBtn").click(function(){
					var id =$("#srGrid").jqGrid('getGridParam','selrow');
					if (id!="" && id!=null) {
						var row = $("#srGrid").jqGrid('getRowData', id);
						var searchRing={
						    "sr_ID" : row.sr_ID,
					        "sr_LON": row.sr_LON,
						    "sr_LAT": row.sr_LAT,
						    "sr_COVER_RANGE": row.sr_COVER_RANGE,
						    "sr_CITY":row.sr_CITY,
						    "sr_AREA":row.sr_AREA,
						    "feq_ID":row.feq_ID,
					  	};
						var callBackFun = "${searchRing.callBackFun}";
						if (typeof window[callBackFun] === "function") {
							window[callBackFun](JSON.stringify(searchRing));
						}
						$("#${targetFrameId}").dialog('close');
						
					}else {
			        	alert("先選取一列資料列");
			        }
				});//ok button end
				
				$("#srCancelBtn").click(function(){
					$("#${targetFrameId}").dialog('close');
				});//reset button end
				$("#srResetBtn").click(function(){
					$("#searchSrIdForm").trigger('reset');
					$("#srGrid").jqGrid().clearGridData();
				});//reset button end
				$("#srGrid").jqGrid({
					datatype: "local",
					pager: '#srPager',
					url : CONTEXT_URL+"/st/sr/ST002SP2/search",
				   	colNames:['SR_ID','基站頻段','經度','緯度','涵蓋範圍','站台','city','area','siteFeq'],
				   	colModel:[
						{name:'sr_ID',index:'sr_ID', width :150, sortable: false},
						{name:'feqName',index:'feqName', width :105, sortable: false,formatter : feqNameFormat},
						{name:'sr_LON',index:'sr_LON', width :150, sortable: false,formatter : lonFormat},
						{name:'sr_LAT',index:'sr_LAT', width :150,sortable: false,formatter : latFormat},
						{name:'sr_COVER_RANGE',index:'sr_COVER_RANGE', width :180, sortable: false},
				   		{name:'btsSiteId',index:'btsSiteId', width :180,sortable: false},
				   		{name:'sr_CITY',index:'sr_CITY', hidden:true,sortable: false},
				   		{name:'sr_AREA',index:'sr_AREA', hidden:true,sortable: false},
				   		{name:'feq_ID',index:'feq_ID', hidden:true,sortable: false},
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
					}
				});//end grid
				$(window).resize(function(){
				    $(window).unbind("onresize");
				    $(".grid").setGridWidth($("#srJqgrid").width());
				    $(window).bind("onresize", this);
				}).trigger('resize');
				filterText("lon");
				filterText("lat");
			});//document.ready end
			function filterText(tagName){
				$("#"+tagName).keyup(function(){
					if(!(/^[1-9]\d*\.?\d*$/gi.test($(this).val()))){
						$(this).val("");
					}
				});
				$("#"+tagName).keydown(function(){
					if(!(/^[1-9]\d*\.?\d*$/gi.test($(this).val()))){
						$(this).val("");
					}
				});
			}
			function feqNameFormat( cellvalue, options, cell){
		        if(cellvalue==""||cellvalue==null){
		            return "共用";
		        }else{
		            return cellvalue;
		        }
	        }
			function lonFormat( cellvalue, options, cell){
				var lon = cellvalue.toFixed(6)
				return lon;
	        }
			function latFormat( cellvalue, options, cell){
				var lat = cellvalue.toFixed(6)
				return lat;
	        }
		</script>
	</head>
	<body>		
<!-- 		<div class="clearfix">&nbsp;</div> -->
<!-- 		<div style="margin-left : 15px"> -->
		<button class="btn btn-primary btn-label-left" type="button" id="srSearchBtn">
			<span><i class="fa fa-search"></i></span>
			<s:message code="button.search" text="查詢" />
		</button>
		<button class="btn btn-primary btn-label-left" type="button" id="srOkBtn">
			<span><i class="fa fa-check"></i></span>
			<s:message code="button.ok" text="確定" />
		</button>
		<button class="btn btn-primary btn-label-left" type="button"
			id="srCancelBtn">
			<span><i class="fa fa-times"></i></span>
			<s:message code="button.cancel" text="取消" />
		</button>
		<button class="btn btn-default btn-label-left" type="button"
			id="srResetBtn">
			<span><i class="fa fa-undo txt-danger"></i></span>
			<s:message code="button.reset" text="重置" />
		</button>
<!-- 		</div> -->
		<div class="row">
			<div id="" class="col-xs-12 col-sm-12">
				<div class="box ui-draggable ui-droppable">
					
					<div class="box-content">
						<form role="form" class="form-horizontal bootstrap-validator-form" novalidate="novalidate" id="searchSrIdForm">
							<div class="form-group">
							<!--  系統功能  -->
								<table style="width:100%">
									<tr>
										<td width="15%" nowrap="nowrap"><label  class="col-sm-10 control-label" style="padding-left : 0px;">SR_ID : </label></td>
										<td width="20%">
											<div class="col-sm-7">
												<input id="st002sp2_srId" type="text" class="form-control" />
											</div>
										</td>
										<td width="10%"><label  class="col-sm-10 control-label" style="padding-left : 0px;">基站頻段 : </label></td>
										<td width="20%">
											<div class="col-sm-7">
												<select id="srFeqId" name="feqId" class="form-control">
													<option value="">---請選擇---</option>
													<c:forEach items="${allSiteFeq}" var="siteFeq">
														<option value="${siteFeq.value}">${siteFeq.label}</option>
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
									<tr >
										<td><label class="col-sm-10 control-label">經度 :</label></td>
										<td nowrap="nowrap">	
											<div class="col-sm-7">
												<input id="lon"  type="text" class="form-control"/>
											</div>
											<p style="margin-top : 5px;">(+-0.001)</p>
										</td>
										<td><label class="col-sm-10 control-label">緯度 :</label></td>
										<td nowrap="nowrap">
											<div class="col-sm-7">
												<input id="lat"  type="text" class="form-control" />
											</div>
											<p style="margin-top : 5px;">(+-0.001)</p>
										</td>
									</tr>
									<tr>
										<td>
											<div class="clearfix">&nbsp;</div>
										</td>
									</tr>
									<tr>
										<td><label class="col-sm-10 control-label">涵蓋範圍關鍵字 :</label></td>
										<td colspan="3">
											<div class="col-sm-10" >
												<input id="srCoverRange"  type="text" class="form-control" placeholder="關鍵字查詢"/>
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
				<div id="srJqgrid">
					<table id="srGrid"></table>
					<div id="srPager"></div>
				</div>
<!-- 	 	固定id for window.resize end -->
			</div>
		</div>
	</body>
</html>