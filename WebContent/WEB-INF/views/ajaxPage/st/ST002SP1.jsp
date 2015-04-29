<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<title>基站查詢</title>
		<style>
		.labelStyle {
		   margin-right: -80px;
		}
		</style>
		<script type="text/javascript">
			$(document).ready(function() {
				buildCityOptions("st002sp1_siteCity","st002sp1_siteTown",'filterDomainId');
				var siteType = "${siteType}";
				if(siteType == "site" || siteType == "engineRoom"){
					$("#st002sp1_siteType").val(siteType).prop("disabled" ,true);
				}
				$("#siteSearchBtn").click(function(){
					var data= {
							btsSiteId : $("#st002sp1_btsSiteId").val(),
							siteName : $("#st002sp1_siteName").val(),
							feqName : $("#st002sp1_feqName").val(),
							eqpName : $("#st002sp1_eqpName").val(),
							siteCity : $("#st002sp1_siteCity").val(),
							siteTown : $("#st002sp1_siteTown").val(),
							siteStatus : $("#st002sp1_siteStatus").val(),
							siteType : $("#st002sp1_siteType").val(),
							locationId : $("#filterLocationId").val()
						};
					$("#siteGrid").setGridParam({datatype:"json", postData:data, mtype: 'POST',page:1}).jqGrid().trigger("reloadGrid");
				});//search button end
				
				$("#siteOkBtn").click(function(){
					var ids =$("#siteGrid").jqGrid('getGridParam','selarrrow');
					var siteObjList = {};
					var siteObjs = [];
					siteObjList.siteObjs = siteObjs;
					if (ids!="") {
						
						if('${selectMode}'=='single' && ids.length>1){
							alert("只能選擇一筆");
							return ;
						}

						for(var id in ids){
			        	  var row = $("#siteGrid").jqGrid('getRowData', ids[id]);
						  var site={
							  "site_ID" : row.site_ID,
							  "bts_SITE_ID": row.bts_SITE_ID,
							  "location_ID": row.location_ID,
							  "locName": row.locName,
							  "feqName": row.feqName,
							  "feq_ID": row.feq_ID,
							  "eqpName": row.eqpName,
							  "locZip": row.locZip,
							  "site_STATUS" : row.site_STATUS,
							  "eqp_TYPE_ID" : row.eqp_TYPE_ID,
							  "maintArea":row.maintArea,
							  "locAddr":row.locAddr,
							  "lon": row.lon,
							  "lat": row.lat,
							  "locCity": row.locCity,
							  "locArea": row.locArea,
							  "locVillage": row.locVillage,
							  "locAdjacent": row.locAdjacent,
							  "locRoad": row.locRoad,
							  "locLane": row.locLane,
							  "locAlley": row.locAlley,
							  "locSub_Alley": row.locSub_Alley,
							  "locDoor_No": row.locDoor_No,
							  "locDoor_No_Ex": row.locDoor_No_Ex,
							  "locFloor" : row.locFloor,
							  "locFloor_Ex": row.locFloor_Ex,
							  "locRoom": row.locRoom,
							  "locAdd_Other": row.locAdd_Other,
							  "coverage_In_Floor": row.coverage_In_Floor,
							  "cluster": row.cluster,
						  };
						  siteObjList.siteObjs.push(site);
						  
						}//for end
						var callBackFun = "${site.callBackFun}";
						if (typeof window[callBackFun] === "function") {
							window[callBackFun](JSON.stringify(siteObjList));
						}
// 						var frameId = $("#frameId").val();
// 						$("#" + frameId).dialog('close');
						if("${isClosed}" != "close"){
							$("#${targetFrameId}").dialog('close');
						}
						
						
					}else {
			        	alert("先選取一列資料列");
			        }
				});//ok button end
				$("#siteCancelBtn").click(function(){
					$("#${targetFrameId}").dialog('close');
				});//reset button end
				$("#siteResetBtn").click(function(){
// 					$("#st002sp1_searchSiteForm").trigger('reset');
					$("#st002sp1_btsSiteId").val("");
					$("#st002sp1_siteName").val("");
					$("#st002sp1_feqName").val("");
					$("#st002sp1_eqpName").val("");
					$("#st002sp1_siteCity").val("").change();
					$("#st002sp1_siteTown").val("");
					$("#st002sp1_siteStatus").val("");
					if(!$("#st002sp1_siteType").prop("disabled")){
						$("#st002sp1_siteType").val("");
					}
					$("#siteGrid").jqGrid().clearGridData();
				});//reset button end
				$("#siteGrid").jqGrid({
					datatype: "local",
					pager: '#sitePager',
					url : CONTEXT_URL+"/common/st002SP1/search",
				   	colNames:['siteId','基站編號','基站名稱','基站頻段','feq_ID','設備型態','基站狀態','站點編號','站點名稱','地址','基站狀態(代碼)','Zip','eqp_TYPE_ID','maintArea','lon','lat','locCity','locArea','locVillage','locAdjacent','locRoad','locLane','locAlley','locSub_Alley','locDoor_No','locDoor_No_Ex','locFloor','locFloor_Ex','locRoom','locAdd_Other','coverage_In_Floor','cluster'],
				   	colModel:[
						{name:'site_ID',index:'site_ID',hidden:true, width :100, sortable: false},
						{name:'bts_SITE_ID',index:'bts_SITE_ID', width :100, sortable: false},
						{name:'site_NAME',index:'site_NAME', width :100, sortable: false},
						{name:'feqName',index:'feqName', width :100,sortable: false},
						{name:'feq_ID',index:'feq_ID',hidden:true, 0 :100,sortable: false},
						{name:'eqpName',index:'eqpName', width :100, sortable: false},
				   		{name:'siteStatusName',index:'siteStatusName', width :100,sortable: false},
				   		{name:'location_ID',index:'location_ID', width :100,sortable: false},
				   		{name:'locName',index:'locName', width :100,sortable: false},
				   		{name:'locAddr',index:'locAddr', width :170,sortable: false},
				   		{name:'site_STATUS',index:'site_STATUS', width :0,sortable: false, hidden:true},
				   		{name:'locZip',index:'locZip', width :0,sortable: false, hidden:true},
				   		{name:'eqp_TYPE_ID',index:'eqp_TYPE_ID',hidden:true, width :100, sortable: false},
				   		{name:'maintArea',index:'maintArea',hidden:true, 0 :100, sortable: false},
				   		{name:'lon',index:'lon',hidden:true, 0 :100, sortable: false},
				   		{name:'lat',index:'lat',hidden:true, 0 :100, sortable: false},
				   		{name:'locCity',index:'locCity',hidden:true, 0 :100, sortable: false},
				   		{name:'locArea',index:'locArea',hidden:true, 0 :100, sortable: false},
 				   		{name:'locVillage',index:'locVillage',hidden:true, 0 :100, sortable: false},
				   		{name:'locAdjacent',index:'locAdjacent',hidden:true, 0 :100, sortable: false},
				   		{name:'locRoad',index:'locRoad',hidden:true, 0 :100, sortable: false},
				   		{name:'locLane',index:'locLane',hidden:true, 0 :100, sortable: false},
				   		{name:'locAlley',index:'locAlley',hidden:true, 0 :100, sortable: false},
				   		{name:'locSub_Alley',index:'locSub_Alley',hidden:true, 0 :100, sortable: false},
				   		{name:'locDoor_No',index:'locDoor_No',hidden:true, 0 :100, sortable: false},
				   		{name:'locDoor_No_Ex',index:'locDoor_No_Ex',hidden:true, 0 :100, sortable: false},
				   		{name:'locFloor',index:'locFloor',hidden:true, 0 :100, sortable: false},
				   		{name:'locFloor_Ex',index:'locFloor_Ex',hidden:true, 0 :100, sortable: false},
				   		{name:'locRoom',index:'locRoom',hidden:true, 0 :100, sortable: false},
				   		{name:'locAdd_Other',index:'locAdd_Other',hidden:true, 0 :100, sortable: false},
				   		{name:'coverage_In_Floor',index:'coverage_In_Floor',hidden:true, 0 :100, sortable: false},
				   		{name:'cluster',index:'cluster',hidden:true, 0 :100, sortable: false}
				   	],	
				   	viewrecords: true,
				   	caption:"基站清單",
					rownumbers:true,
					multiselect: true,
// 					sortname : "bts_SITE_ID",
// 					sortorder : "asc",
				   	onSelectRow: function(id){ 
				   		
				    },
				    gridComplete: function(){
						var ids = jQuery("#siteGrid").jqGrid('getDataIDs');
						for(var i=0;i < ids.length;i++){
							var cl = ids[i];  // cl:jqgrid的行數，非資料真正的HEADER_ID
							$("#siteGrid").jqGrid('getRowData', cl);
						}	
					},
					ondblClickRow: function(rowId) { //double clicking a jqgrid row
			            var rowData = jQuery(this).getRowData(rowId);
			            $.each(rowData, function(i,item) {
			            	if(i=="pos_ID"){
			            		updatePosition("view",item);
			            	}
			            });
			        }
				});//end grid
				$(window).resize(function(){
				    $(window).unbind("onresize");
				    $(".grid").setGridWidth($("#siteJqgrid").width());
				    $(window).bind("onresize", this);
				}).trigger('resize');
			});//document.ready end
		</script>
	</head>
	<body>		
<!-- 		<div class="clearfix">&nbsp;</div> -->
<!-- 		<div style="margin-left : 15px"> -->
		<button class="btn btn-primary btn-label-left" type="button" id="siteSearchBtn">
			<span><i class="fa fa-search"></i></span>
			<s:message code="button.search" text="查詢" />
		</button>
		<button class="btn btn-primary btn-label-left" type="button" id="siteOkBtn">
			<span><i class="fa fa-check"></i></span>
			<s:message code="button.ok" text="確定" />
		</button>
		<button class="btn btn-primary btn-label-left" type="button"
			id="siteCancelBtn">
			<span><i class="fa fa-times"></i></span>
			<s:message code="button.cancel" text="取消" />
		</button>
		<button class="btn btn-default btn-label-left" type="button"
			id="siteResetBtn">
			<span><i class="fa fa-undo txt-danger"></i></span>
			<s:message code="button.reset" text="重置" />
		</button>
<!-- 		</div> -->
		<div class="row">
			<div id="" class="col-xs-12 col-sm-12">
				<div class="box ui-draggable ui-droppable">
					<div class="box-header">
						<div class="box-name">
							<i class="fa fa-search"></i> <span>站點基站申請 查詢</span>
						</div>
						<div class="box-icons">
							<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
							</a> <a class="expand-link"> <i class="fa fa-expand"></i>
							</a>
						</div>
						<div class="no-move"></div>
					</div>
					<div class="box-content">
						<form role="form" class="form-horizontal bootstrap-validator-form" novalidate="novalidate" id="st002sp1_searchSiteForm">
							<div class="form-group">
							<!--  系統功能  -->
								<table style="width:100%">
									<tr>
										<td width="12%"><label  class="col-sm-10 control-label">基站編號 :</label></td>
										<td width="38%">
											<div class="col-sm-6">
												<input id="st002sp1_btsSiteId" type="text" class="form-control">
											</div>
										</td>
										<td width="12%"><label class="col-sm-10 control-label">基站名稱 :</label></td>
										<td width="38%">
											<div class="col-sm-6">
												<input id="st002sp1_siteName" type="text" class="form-control" placeholder="關鍵字查詢">
											</div>
										</td>
									</tr>
									<tr>
										<td>
											<div class="clearfix">&nbsp;</div>
										</td>
									</tr>
									<tr >
										<td><label class="col-sm-10 control-label">區域 :</label></td>
										<td>	
											<div class="col-sm-6">
												<select id="st002sp1_siteCity" class="form-control">
													
												</select>
											</div>
											<div class="col-sm-6">
												<select id="st002sp1_siteTown" class="form-control">
												
												</select>
											</div>
											<input type="hidden" id="filterDomainId" name="filterDomainId" value="${filterDomainId}" >
											<input type="hidden" id="filterLocationId" name="filterLocationId" value="${locationId}" >
										</td>
										<td><label class="col-sm-10 control-label">設備型態 :</label></td>
										<td>
											<div class="col-sm-6">
												<select id="st002sp1_eqpName" class="form-control">
													<option value="">--- 請選擇 ---</option>
													<c:forEach items="${allEqpTypes}" var="eqpType">
														<option value="${eqpType.value}">${eqpType.label}</option>
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
									<tr>
										<td><label class="col-sm-10 control-label">基站頻段 :</label></td>
										<td>
											<div class="col-sm-6">
												<select id="st002sp1_feqName" class="form-control">
													<option value="">--- 請選擇 ---</option>
													<c:forEach items="${allSiteFeqs}" var="siteFeq">
														<option value="${siteFeq.value}">${siteFeq.label}</option>
													</c:forEach>
												</select>
											</div>
										</td>
										<td><label class="col-sm-10 control-label">基站狀態 :</label></td>
										<td>
											<div class="col-sm-6">
												<select id="st002sp1_siteStatus" class="form-control">
													<option value="">--- 請選擇 ---</option>
													<c:forEach items="${siteStatusList}" var="siteStatus">
														<option value="${siteStatus.value}">${siteStatus.label}</option>
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
									<tr>
										<td><label class="col-sm-10 control-label">基站類型 :</label></td>
										<td>
											<div class="col-sm-6">
												<select id="st002sp1_siteType" class="form-control">
													<option value="">--- 請選擇 ---</option>
													<option value="site">基站</option>
													<option value="engineRoom">機房</option>
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
				<div id="siteJqgrid">
					<table id="siteGrid"></table>
					<div id="sitePager"></div>
				</div>
<!-- 	 	固定id for window.resize end -->
			</div>
		</div>
	</body>
</html>