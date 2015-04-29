<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<!DOCTYPE html>
<html lang="en">
	<head>
		<title>廠商查詢</title>
		<script type="text/javascript">			
			$(document).ready(function() {
				
				$("#sendComOueryBtn").click(function(){
					var id =$("#comQueryGrid").jqGrid('getGridParam','selrow');
					if (id!="" && id!=null) {
						var row = $("#comQueryGrid").jqGrid('getRowData', id);
						var com={
							    "co_VAT_NO" : row.co_VAT_NO,
						        "co_ERP_NO": row.co_ERP_NO,
							    "co_UBN_NO": row.co_UBN_NO,
							    "co_NAME": row.co_NAME,
						  	};	
						var callBackFun = '${company.callBackFun}';
						if (typeof window[callBackFun] === "function") {
							window[callBackFun](JSON.stringify(com));
						}
						$("#${targetFrameId}").dialog('close');
					}else {
			        	alert("先選取一列資料列");
			        }
				});//saveButton end
				
				$("#cancelComOueryBtn").click(function(){
					$("#${targetFrameId}").dialog('close');
				});//reset button end
				
				$("#resetComOueryBtn").click(function(){
					$("#comQueryForm").trigger('reset');
					$("#comQueryGrid").jqGrid().clearGridData();
				});//reset button end
				
				$("#searchComOueryBtn").click(function(){
// 					var data = $("#comQueryForm").serialize();
					var data = {
						ubnNo : $("#comQueryUbnNo").val(),
						erpNo : $("#comQueryErpNo").val(),
						comName : $("#comQueryComName").val(),
					};
					$("#comQueryGrid").setGridParam({datatype:"json", postData:data, mtype: 'POST',page:1}).jqGrid().trigger("reloadGrid");

				});//searchButton end
				$("#comQueryGrid").jqGrid({
					datatype: "local",
					pager: '#comQueryPager',
					url : CONTEXT_URL+"/common/companyQuery/search",
				   	colNames:['廠商編號','ERP編號','統一編號','廠商名稱'],
				   	colModel:[
						{name:'co_VAT_NO',index:'co_VAT_NO',width:220, sortable: false},
						{name:'co_ERP_NO',index:'co_ERP_NO',width:225, sortable: false},
						{name:'co_UBN_NO',index:'co_UBN_NO',width:225, sortable: false},
						{name:'co_NAME',index:'co_NAME',width:225, sortable: false},
						
					],	
				   	viewrecords: true,
				   	caption:"廠商清單",
					rownumbers:true,
					multiselect: false,
				   	onSelectRow: function(id){ 
				   		
				    },
				    gridComplete: function(){
						var ids = jQuery("#comQueryGrid").jqGrid('getDataIDs');
						for(var i=0;i < ids.length;i++){
							var cl = ids[i];  // cl:jqgrid的行數，非資料真正的HEADER_ID
							$("#comQueryGrid").jqGrid('getRowData', cl);
						}	
					}
				});//end grid
				
				$(window).resize(function(){
				    $(window).unbind("onresize");
				    $(".grid").setGridWidth($("#comQueryJqgrid").width());
				    $(window).bind("onreCsize", this);
				}).trigger('resize');
				
				
				if($('#is_mergeId').val()=="Y"){
					document.getElementById('isTempC').disabled = true;					
				}
				
				
			});//document.ready end
			
		</script>
	</head>
	<body>
<!-- 		<div class="clearfix">&nbsp;</div> -->
<!-- 		<div style="margin-left : 15px"> -->
		<button class="btn btn-primary btn-label" type="button" id="searchComOueryBtn">
			<s:message code="button.search" text="查詢" />
		</button>
		<button class="btn btn-primary btn-label" type="button" id="sendComOueryBtn">
			<s:message code="button.ok" text="確定" />
		</button>
		<button class="btn btn-primary btn-label" type="button"
			id="cancelComOueryBtn">
			<s:message code="button.cancel" text="取消" />
		</button>
		<button class="btn btn-primary btn-label" type="button"
			id="resetComOueryBtn">
			<s:message code="button.reset" text="重置" />
		</button>
<!-- 		</div> -->
		<div class="row">
			<div id="" class="col-xs-12 col-sm-12">
				<div class="box ui-draggable ui-droppable">
					
					<div class="box-content">
						<form role="form" class="form-horizontal bootstrap-validator-form" novalidate="novalidate" id="comQueryForm">
							<div class="form-group">
							<!--  系統功能  -->
								<table style="width:100%">
									<tr>
										<td width="10%" nowrap="nowrap"><label  class="col-sm-10 control-label" style="padding-left : 0px;">廠商統編 : </label></td>
										<td width="20%">
											<div class="col-sm-8">
												<input id="comQueryUbnNo" name="ubnNo" type="text" class="form-control" />
											</div>
										</td>
										<td width="10%"><label  class="col-sm-10 control-label" style="padding-left : 0px;">ERP編號 : </label></td>
										<td width="20%">
											<div class="col-sm-8">
												<input id="comQueryErpNo" type="text" name="erpNo" class="form-control" />
											</div>
										</td>
									</tr>
									<tr>
										<td>
											<div class="clearfix">&nbsp;</div>
										</td>
									</tr>
									<tr >
										<td><label class="col-sm-10 control-label">廠商名稱 :</label></td>
										<td>	
											<div class="col-sm-8">
												<input id="comQueryComName" name="comName" type="text" class="form-control" placeholder="關鍵字查詢"/>
											</div>
										</td>
										
									</tr>
									<tr>
										<td>
											<div class="clearfix">&nbsp;</div>
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
				<div id="comQueryJqgrid">
					<table id="comQueryGrid"></table>
					<div id="comQueryPager"></div>
				</div>
<!-- 	 	固定id for window.resize end -->
			</div>
		</div>
	</body>
</html>