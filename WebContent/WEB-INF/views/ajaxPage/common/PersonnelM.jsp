<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<!DOCTYPE html>
<html lang="en">
	<head>
		<title>使用者選擇設定</title>
		<script type="text/javascript">
			
			$(document).ready(function() {
				$("#savePsnPosBtn").click(function(){
					var ids =$("#psnPosGrid").jqGrid('getGridParam','selarrrow');
					var psnObjectList = {};
					var employees = [];
					psnObjectList.employees = employees;
					if (ids!="") {
						for(var id in ids){
			        	  var row = $("#psnPosGrid").jqGrid('getRowData', ids[id]);
						  var person={
							  "no": row.psn_NO,
							  "name": row.chn_NM,
							  "email": row.e_MAIL,
							  "deptPosId" : row.primary_DEPT_POS_ID
						  };
						  psnObjectList.employees.push(person);
						  
						}//for end
						var callBackFun = "${person.callBackFun}";
						if (typeof window[callBackFun] === "function") {
							window[callBackFun](JSON.stringify(psnObjectList));
						}
						var frameId = $("#frameId").val();
						$("#" + frameId).dialog('close');
					}else {
			        	alert("先選取一列資料列");
			        }
				});
				
				$("#searchBtn").click(function(){
					var data= {
							psnNo: $.trim($('#psnNo').val()),
							chnName: $.trim($('#chn_NM').val()),
							email: $.trim($('#email').val())
						};
						$("#psnPosGrid").setGridParam({datatype:"json", postData:data, mtype: 'POST',page:1}).jqGrid().trigger("reloadGrid");
				});//searchButton end
				
				$("#psnPosGrid").jqGrid({
					datatype: "local",
					pager: '#psnPosPager',
					url : CONTEXT_URL+"/common/personnel/search",
				   	colNames:['員工編號','員工名稱','EMail','primary_DEPT_POS_ID'],
				   	colModel:[
						{name:'psn_NO',index:'psn_NO',width:100, sortable: false},
				   		{name:'chn_NM',index:'chn_NM',width:100, sortable: false},
				   		{name:'e_MAIL',index:'e_MAIL', width:100,sortable: false},
				   		{name:'primary_DEPT_POS_ID',hidden:true,index:'primary_DEPT_POS_ID', width:100,sortable: false},
				   	],	
				   	viewrecords: true,
				   	caption:"員工清單",
					rownumbers:true,
					multiselect: true,
				   	onSelectRow: function(id){ 
				   		
				    }

				});//end grid
				$(window).resize(function(){
				    $(window).unbind("onresize");
				    $("#psnPosGrid,.psnPosGrid").setGridWidth(550);
				    $(window).bind("onreCsize", this);
				}).trigger('resize');
			});//document.ready end
			
		</script>
	</head>
	<body>
		
		<div class="row">
			<div class="col-xs-8 col-sm-8">
				<div class=" ui-draggable ui-droppable">
					<div class="box-content">
						<form class="form-horizontal" id="addForm">
							<div style="text-align:left">
			                    <button class="btn btn-primary btn-label-left" type="button" id="searchBtn">
				                <span><i class="fa fa-search"></i></span> <s:message code="button.search" text="查詢" />
			                    </button>
			                    <button class="btn btn-primary btn-label-left" type="button" id="savePsnPosBtn">
				                <span><i class="fa fa-check"></i></span> <s:message code="button.select" text="選取" />
			                    </button>
		                    </div>
		                    
							<div class="form-group" >
								<label class="col-sm-3 control-label-left">員工編號 :</label>
								<div class="col-sm-6">
									<input id="psnNo" type="text" class="form-control"
									name="psnNo"  placeholder="員工編號">
								</div>
							</div>
							<div class="form-group" >
								<label class="col-sm-3 control-label-left">員工名稱 :</label>
								<div class="col-sm-6">
									<input id="chn_NM" type="text" class="form-control"
										name="chn_NM" placeholder="關鍵字查詢">
								</div>
							</div>
							
							<div class="form-group" >
								<label class="col-sm-3 control-label-left">Email :</label>
								<div class="col-sm-6">
									<input id="email" type="text" class="form-control"
										name="email" placeholder="Email">
								</div>
							</div>					
						</form>
					</div>
				</div>
			</div>
			<div id="ajaxSearchResult"  class="col-xs-12">
	<!-- 		固定id for window.resize start -->
				<div id="psnPosJQgrid">
					<table id="psnPosGrid"></table>
					<div id="psnPosPager"></div>
				</div>
	<!-- 	 	固定id for window.resize end -->
			</div>
		</div>
	</body>
</html>