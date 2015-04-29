<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<title>可用基站編號</title>
		<style>
		.labelStyle {
		   margin-right: -80px;
		}
		</style>
		<script type="text/javascript">
			$(document).ready(function() {
// 				var errorMessage = []
// 				if($("#unuseBtsSiteIdEqpType").val()==""){
					
// 				}
				$("#useStatus").val("Y");
				$("#unuseBtsSiteIdSearchBtn").click(function(){
					var errorMesssages = [];
					if($("#unuseBtsSiteIdEqpType").val() == ""){
						errorMesssages[errorMesssages.length] = "必須選擇設備型態\n";
					}
					if($("#unuseBtsSiteIdSiteFeq").val() == ""){
						errorMesssages[errorMesssages.length] = "必須選擇基站頻段\n";
					}
					if($("#unuseBtsSiteIdCoverageType").val() == ""){
						errorMesssages[errorMesssages.length] = "必須選擇涵蓋類別\n";
					}
					if($("#unuseBtsSiteIdDomain").val() == ""){
						errorMesssages[errorMesssages.length] = "必須選擇區域\n";
					}
					if(errorMesssages.length != 0){
						var msg = "";
						for(var errorMsg in errorMesssages){
							msg += errorMesssages[errorMsg];
						}
						alert(msg);
						return false;
					}
					var data= {
							eqpType : $("#unuseBtsSiteIdEqpType").val(),
							feqType : $("#unuseBtsSiteIdSiteFeq").val(),
							coverageType : $("#unuseBtsSiteIdCoverageType").val(),
							domain : $("#unuseBtsSiteIdDomain").val(),
							siteIdBegin : $("#siteIdBegin").val(),
							siteEnd : $("#siteEnd").val(),
							useStatus : $("#useStatus").val(),
						};
					$("#unuseBtsSiteIdGrid").setGridParam({datatype:"json", postData:data, mtype: 'POST',page:1}).jqGrid().trigger("reloadGrid");
				});//search button end
				
				$("#unuseBtsSiteIdOkBtn").click(function(){
					
					var id =$("#unuseBtsSiteIdGrid").jqGrid('getGridParam','selrow');
					if (id!="" && id!=null) {
						var row = $("#unuseBtsSiteIdGrid").jqGrid('getRowData', id);
						if(row.siteId=="可用"){
							var unuseBtsSiteId={
							    "bts_SITE_ID" : row.bts_SITE_ID,
						  	};
							var callBackFun = "${siteIdPool.callBackFun}";
							if (typeof window[callBackFun] === "function") {
								window[callBackFun](JSON.stringify(unuseBtsSiteId));
							}
							$("#${targetFrameId}").dialog('close');
						}else{
							alert("無法選擇使用中的基站編號");
						}
					}else {
			        	alert("先選取一列資料列");
			        }
				});//ok button end
				
				$("#unuseBtsSiteIdCancelBtn").click(function(){
					$("#${targetFrameId}").dialog('close');
				});//reset button end
				$("#unuseBtsSiteIdResetBtn").click(function(){
					$("#searchUnuseBtsSiteIdForm").trigger('reset');
					$("#useStatus").val("Y").change();
					$("#unuseBtsSiteIdGrid").jqGrid().clearGridData();
				});//reset button end
				$("#unuseBtsSiteIdGrid").jqGrid({
					datatype: "local",
					pager: '#unuseBtsSiteIdPager',
					url : CONTEXT_URL+"/st/st002/search/unuseBTSSiteId/search",
				   	colNames:['基站編號','使用狀態'],
				   	colModel:[
						{name:'bts_SITE_ID',index:'bts_SITE_ID', width :450, sortable: false},
						{name:'siteId',index:'siteId', width :450, sortable: false ,formatter :useStatusFormat},
					],	
				   	viewrecords: true,
				   	caption:"可用基站編號清單",
					rownumbers:true,
					multiselect: false,
					sortname : "bts_SITE_ID",
					sortorder : "asc",
				   	onSelectRow: function(id){ 
				   		
				    },
				    gridComplete: function(){
						var ids = jQuery("#unuseBtsSiteIdGrid").jqGrid('getDataIDs');
						for(var i=0;i < ids.length;i++){
							var cl = ids[i];  // cl:jqgrid的行數，非資料真正的HEADER_ID
							$("#unuseBtsSiteIdGrid").jqGrid('getRowData', cl);
						}	
					}
				});//end grid
				$(window).resize(function(){
				    $(window).unbind("onresize");
				    $(".grid").setGridWidth($("#unuseBtsSiteIdJqgrid").width());
				    $(window).bind("onresize", this);
				}).trigger('resize');
			});//document.ready end
			function useStatusFormat( cellvalue, options, cell){
		        if(cellvalue == "" || cellvalue == null){
		            return "可用";
		        }else{
		            return "使用中";
		        }
	        }
		</script>
	</head>
	<body>		
<!-- 		<div class="clearfix">&nbsp;</div> -->
<!-- 		<div style="margin-left : 15px"> -->
		<button class="btn btn-primary btn-label-left" type="button" id="unuseBtsSiteIdSearchBtn">
			<span><i class="fa fa-search"></i></span>
			<s:message code="button.search" text="查詢" />
		</button>
		<button class="btn btn-primary btn-label-left" type="button" id="unuseBtsSiteIdOkBtn">
			<span><i class="fa fa-check"></i></span>
			<s:message code="button.ok" text="確定" />
		</button>
		<button class="btn btn-primary btn-label-left" type="button"
			id="unuseBtsSiteIdCancelBtn">
			<span><i class="fa fa-times"></i></span>
			<s:message code="button.cancel" text="取消" />
		</button>
		<button class="btn btn-default btn-label-left" type="button"
			id="unuseBtsSiteIdResetBtn">
			<span><i class="fa fa-undo txt-danger"></i></span>
			<s:message code="button.reset" text="重置" />
		</button>
<!-- 		</div> -->
		<div class="row">
			<div id="" class="col-xs-12 col-sm-12">
				<div class="box ui-draggable ui-droppable">
					
					<div class="box-content">
						<form role="form" class="form-horizontal bootstrap-validator-form" novalidate="novalidate" id="searchUnuseBtsSiteIdForm">
							<div class="form-group">
							<!--  系統功能  -->
								<table style="width:100%">
									<tr>
										<td width="15%" nowrap="nowrap"><label  class="col-sm-10 control-label" style="padding-left : 0px;"><span class="s">* </span>設備型態 : </label></td>
										<td width="20%">
											<div class="col-sm-8">
												<select id="unuseBtsSiteIdEqpType" class="form-control">
													<option value="">--- 請選擇 ---</option>
													<c:forEach items="${allEqpType}" var="eqpType">
														<option value="${eqpType.value}">${eqpType.label}</option>
													</c:forEach>
												</select>
											</div>
										</td>
										<td width="10%"><label  class="col-sm-10 control-label" style="padding-left : 0px;"><span class="s">* </span>基站頻段 : </label></td>
										<td width="20%">
											<div class="col-sm-8">
												<select id="unuseBtsSiteIdSiteFeq" class="form-control">
													<option value="">--- 請選擇 ---</option>
													<c:forEach items="${allSiteFeq}" var="siteFeq">
														<option value="${siteFeq.key},${siteFeq.value.value}">${siteFeq.value.label}</option>
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
										
										<td><label class="col-sm-10 control-label"><span class="s">* </span>區域 :</label></td>
										<td nowrap="nowrap">
											<div class="col-sm-8">
												<select id="unuseBtsSiteIdDomain" class="form-control">
													<option value="">--- 請選擇 ---</option>
													<c:forEach items="${domainList}" var="domain">
														<option value="${domain.ID}">${domain.NAME}</option>
													</c:forEach>
												</select>
											</div>
										</td>
										<td><label class="col-sm-10 control-label"><span class="s">* </span>涵蓋類別 :</label></td>
										<td nowrap="nowrap">	
											<div class="col-sm-8">
												<select id="unuseBtsSiteIdCoverageType" class="form-control">
													<option value="">--- 請選擇 ---</option>
													<c:forEach items="${allCoverageType}" var="coverageType">
														<option value="${coverageType.value}">${coverageType.label}</option>
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
										<td><label class="col-sm-10 control-label">使用狀態 :</label></td>
										<td>
											<div class="col-sm-8">
												<select id="useStatus" class="form-control">
													<option value="">--- 請選擇 ---</option>
													<option value="N">使用中</option>
													<option value="Y">可用</option>
												</select>
											</div>
										</td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td>
											<div class="clearfix">&nbsp;</div>
										</td>
									</tr>
									<tr>
										<td><label class="col-sm-10 control-label">基站編號起 :</label></td>
										<td>
											<div class="col-sm-8">
												<input id="siteIdBegin"  type="text" class="form-control" />
											</div>
										</td>
										<td><label class="col-sm-10 control-label">基站編號迄 :</label></td>
										<td>
											<div class="col-sm-8">
												<input id="siteEnd"  type="text" class="form-control" />
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
				<div id="unuseBtsSiteIdJqgrid">
					<table id="unuseBtsSiteIdGrid"></table>
					<div id="unuseBtsSiteIdPager"></div>
				</div>
<!-- 	 	固定id for window.resize end -->
			</div>
		</div>
	</body>
</html>