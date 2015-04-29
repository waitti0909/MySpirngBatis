<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<!DOCTYPE html>
<html lang="en">
	<head>
		<title>PO單查詢</title>
		<script type="text/javascript">			
			$(document).ready(function() {
				
				$("#choosePoBtn").click(function(){
					var id =$("#poGrid").jqGrid('getGridParam','selrow');
					if (id!="" && id!=null) {
						var row = $("#poGrid").jqGrid('getRowData', id);
						var po={
						    "po_ID" : row.po_ID,
					        "po_YEAR": row.po_YEAR,
						    "po_NO": row.po_NO,
						    "po_NAME": row.po_NAME,
						    "amount":row.amount,
						    "po_DOMAIN":row.po_DOMAIN,
					  	};
						
						var callBackFun = '${comPo.callBackFun}';
						
						if($('#is_mergeId').val()=="Y"){
							
							$.ajax({
								url : CONTEXT_URL + "/cm/po/countPONo",
								type : 'POST',
								dataType : "html",
								data : {
									mergeTargetPoNo : row.po_NO
								},
								async : false,
								success : function(data) {
									if(data>1){
										alert("此正式PO單已合併過,不可再次合併!");
									}else{
										if (confirm("是否確定合併?")) {	
											if (typeof window[callBackFun] === "function") {
												window[callBackFun](JSON.stringify(po));
											}
											$("#${targetFrameId}").dialog('close');
										}
									}									
								}
							});		
							
							
							
						}else{
							if (typeof window[callBackFun] === "function") {
								window[callBackFun](JSON.stringify(po));
							}
							$("#${targetFrameId}").dialog('close');
						}												
					}else {
			        	alert("先選取一列資料列");
			        }
				});//saveButton end
				
				$("#cancelPoBtn").click(function(){
					$("#${targetFrameId}").dialog('close');
				});//reset button end
				
				$("#resetPoBtn").click(function(){
					$("#commSearchPoForm").trigger('reset');
					$("#poGrid").jqGrid().clearGridData();
				});//reset button end
				
				$("#searchPoBtn").click(function(){
					if($('#isTempC').prop("checked")){
						$('#isTempC').val("Y");
					}else{
						$('#isTempC').val("N");
					}
					var data= {
							poNo: $.trim($('#poNoC').val()),
							year: $.trim($('#poYearC').val()),
							poName: $.trim($('#poNameC').val()),
							co_vat_No: $.trim($('#coNameC').val()),
							isTemp: $.trim($('#isTempC').val()),
					};
					$("#poGrid").setGridParam({datatype:"json", postData:data, mtype: 'POST',page:1}).jqGrid().trigger("reloadGrid");
				});//searchButton end
				$("#poGrid").jqGrid({
					datatype: "local",
					pager: '#poPager',
					url : CONTEXT_URL+"/cm/po/common/search",
				   	colNames:['poId','poDomain','年度','PO單號','PO單名稱','廠商名稱','總額'],
				   	colModel:[
						{name:'po_ID',index:'po_ID',hidden:true, sortable: false},
						{name:'po_DOMAIN',index:'po_DOMAIN',hidden:true, sortable: false},
						{name:'po_YEAR',index:'po_YEAR',width:180, sortable: false},
						{name:'po_NO',index:'po_NO',width:180, sortable: false},
						{name:'po_NAME',index:'po_NAME', width:180,sortable: false},
						{name:'coName',index:'coName', width:180,sortable: false},
						{name:'amount',index:'amount', width:180,sortable: false}
					],	
				   	viewrecords: true,
				   	caption:"PO單清單",
					rownumbers:true,
					multiselect: false,
				   	onSelectRow: function(id){ 
				   		
				    },
				    gridComplete: function(){
						var ids = jQuery("#poGrid").jqGrid('getDataIDs');
						for(var i=0;i < ids.length;i++){
							var cl = ids[i];  // cl:jqgrid的行數，非資料真正的HEADER_ID
							$("#poGrid").jqGrid('getRowData', cl);
						}	
					}
				});//end grid
				
				$(window).resize(function(){
				    $(window).unbind("onresize");
				    $(".grid").setGridWidth($("#poJQgrid").width());
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
		<button class="btn btn-primary btn-label" type="button" id="searchPoBtn">
			<s:message code="button.search" text="查詢" />
		</button>
		<button class="btn btn-primary btn-label" type="button" id="choosePoBtn">
			<s:message code="button.ok" text="確定" />
		</button>
		<button class="btn btn-primary btn-label" type="button"
			id="cancelPoBtn">
			<s:message code="button.cancel" text="取消" />
		</button>
		<button class="btn btn-primary btn-label" type="button"
			id="resetPoBtn">
			<s:message code="button.reset" text="重置" />
		</button>
<!-- 		</div> -->
		<div class="row">
			<div id="" class="col-xs-12 col-sm-12">
				<div class="box ui-draggable ui-droppable">
					
					<div class="box-content">
						<form role="form" class="form-horizontal bootstrap-validator-form" novalidate="novalidate" id="commSearchPoForm">
							<div class="form-group">
							<!--  系統功能  -->
								<table style="width:100%">
									<tr>
										<td width="10%" nowrap="nowrap"><label  class="col-sm-10 control-label" style="padding-left : 0px;">PO單號 : </label></td>
										<td width="20%">
											<div class="col-sm-6">
												<input id="poNoC" name="poNo" type="text" class="form-control" />
											</div>
										</td>
										<td width="10%"><label  class="col-sm-10 control-label" style="padding-left : 0px;">年度 : </label></td>
										<td width="20%">
											<div class="col-sm-6">
												<input id="poYearC" type="text" name="year" class="form-control" />
											</div>
										</td>
									</tr>
									<tr>
										<td>
											<div class="clearfix">&nbsp;</div>
										</td>
									</tr>
									<tr >
										<td><label class="col-sm-10 control-label">PO單名稱 :</label></td>
										<td>	
											<div class="col-sm-12">
												<input id="poNameC"  type="text" name="poName" class="form-control" placeholder="關鍵字查詢"/>
											</div>
										</td>
										<td><label class="col-sm-10 control-label">廠商 :</label></td>
										<td>
											<div class="col-sm-6">
												<select id="coNameC" name="co_vat_No" class="form-control">
													<option value="">---請選擇---</option>
													<c:forEach items="${companyList }" var="company">
														<option value="${company.CO_VAT_NO}">${company.CO_NAME}</option>
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
										<td>
											<div class="row form-group">
												<div class="checkbox-inline" style="margin-left: 70px">
													<label>
														<input type="checkbox" id="isTempC" name="isTemp" value="Y" /> 臨時PO單 <i class="fa fa-square-o"></i>
													</label>
												</div>
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
				<div id="poJqgrid">
					<table id="poGrid"></table>
					<div id="poPager"></div>
				</div>
<!-- 	 	固定id for window.resize end -->
			</div>
		</div>
	</body>
</html>