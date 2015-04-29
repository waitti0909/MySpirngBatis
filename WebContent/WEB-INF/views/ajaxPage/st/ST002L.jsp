<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<title>尋點作業</title>
		
		<script type="text/javascript">
			$(document).ready(function() {
				buildCityOptions("siteWorkCity","siteWorkArea");
				LoadSelect2Script(
					'<s:url value="/resources/plugins/select2/select2.min.js" />',Select2Test);
				WinMove();
				$("#btn_reset").click(function(){
					$("#searchSiteForm").trigger('reset');
					$("#grid").jqGrid().clearGridData();
					$('#workType').select2({ allowClear: true });
					$('#applyDept').select2({ allowClear: true });
					$('#siteWorkCity').select2({ allowClear: true }).change();
					$('#siteWorkArea').select2({ allowClear: true });
					$('#siteFeq').select2({ allowClear: true });
					$('#workStatus').select2({ allowClear: true });
				});//reset button end
				$('#btn_add').click(function() {
					$.fancybox.open({
			 			href : CONTEXT_URL+"/st/st002/insertPage",
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
						if(row.work_STATUS != "W01" && row.work_STATUS != "W02" && row.work_STATUS != "W04"){
							alert("此處理狀態無法修改");
							return false;
						}
// 						if(row.work_STATUS == "W06"){
// 							alert("已退工無法修改");
// 							return false;
// 						}
						updateSiteWork(row.work_ID ,"edit");		
					}else {
			        	alert("先選取一列資料列");
			        }		
				});//updateButton end
				$('#btn_search').click(function() {
					var data= {
						workType :$.trim($("#workType").val()),
						srId : $.trim($("#siteWorkSrId").val()),
						siteWorkCity : $.trim($("#siteWorkCity").val()),
						siteWorkArea : $.trim($("#siteWorkArea").val()),
						btsSiteId : $.trim($("#siteWorkBtsSiteId").val()),
						siteName : $.trim($("#siteWorkSiteName").val()),
						locationId : $.trim($("#locationId").val()),
						siteFeq : $.trim($("#siteFeq").val()),
						workStatus : $.trim($("#workStatus").val()),
						applyDept : $.trim($("#applyDept").val()),
						applyDateStart:$.trim($("#applyDateStart").val()),
						applyDateEnd:$.trim($("#applyDateEnd").val())
					};
					$("#grid").setGridParam({datatype:"json", postData:data, mtype: 'POST',page:1}).jqGrid().trigger("reloadGrid");
				});//searchButton end
				$("#grid").jqGrid({
					datatype: "local",
					pager: '#pager',
					url : CONTEXT_URL+"/st/st002/search",
				   	colNames:['作業編號','work_STATUS','Search Ring ID','工務類別','基站編號','基站名稱','基站頻段','基站狀態','處理狀態','站點編號','地址'],
				   	colModel:[
						{name:'work_ID',index:'work_ID',width:120, sortable: false},
						{name:'work_STATUS',index:'work_STATUS',hidden:true,sortable: false},
						{name:'sr_ID',index:'sr_ID',width:130, sortable: false},
				   		{name:'work_TYPE',index:'work_TYPE',width:80, sortable: false},
				   		{name:'bts_SITE_ID',index:'bts_SITE_ID',width:80, sortable: false},
				   		{name:'site_NAME',index:'site_NAME', width:100,sortable: false},
				   		{name:'feqName',index:'feqName', width:80,sortable: false},
				   		{name:'siteStatus',index:'siteStatus', width:110,sortable: false},
				   		{name:'workStatusName',index:'workStatusName', width:80,sortable: false},
				   		{name:'location_ID',index:'location_ID', width:120,sortable: false},
				   		{name:'addr',index:'addr', width:180,sortable: false},
				   	],	
				   	viewrecords: true,
				   	caption:"尋點作業清單",
					rownumbers:true,
					multiselect: false,
				   	onSelectRow: function(id){ 
				   		
				    },
				    gridComplete: function(){
						
					},
					ondblClickRow: function(rowId) { //double clicking a jqgrid row
			            var rowData = jQuery(this).getRowData(rowId);
			            $.each(rowData, function(i,item) {
			            	if(i=="work_ID"){
			            		updateSiteWork(item ,"view");
			            	}
			            });
			            
			        }
				});//end grid
				
				$(window).resize(function(){
				    $(window).unbind("onresize");
				    $("#grid,.grid").setGridWidth($("#jQgrid").width() -10);
				    $(window).bind("onresize", this);
				}).trigger('resize');
				
				$('#applyDateStart').datepicker();
				
				$('#applyDateEnd').datepicker({
					dateFormat : "yy/mm/dd",
					changeYear : true,
					changeMonth : true,
					showButtonPanel : true
				});
			});//document.ready end
			
			function Select2Test() {
				$("#workType").select2();
				$("#applyDept").select2();
				$("#siteWorkCity").select2();
				$("#siteWorkArea").select2();
				$("#siteFeq").select2();
				$("#workStatus").select2();
			}
			
			function updateSiteWork(workId ,status){
				$.fancybox.open({
		 			href : CONTEXT_URL+"/st/st002/updatePage",
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
		 	            	"workId" : workId,
		 	            	"status" : status
		 	            }
		 	        },
		 	        afterClose : function() {
		 			}
		 		});
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
							<i class="fa fa-search"></i> <span>尋點作業申請 查詢</span>
						</div>
						<div class="box-icons">
							<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
							</a> <a class="expand-link"> <i class="fa fa-expand"></i>
							</a>
						</div>
						<div class="no-move"></div>
					</div>
					<div class="box-content">
						<form role="form" class="form-horizontal bootstrap-validator-form" novalidate="novalidate" id="searchSiteForm">
							<div class="form-group">
								<!--  系統功能  -->
								<table style="width:90%">
									<tr>
										<td width="20%"><label class="col-sm-12 control-label">工務類別 :</label></td>
										<td width="30%">
											<div class="col-sm-8">
												<select id="workType" class="populate placeholder" name="country" >
													<option value="">---請選擇---</option>
													<c:forEach items="${allWorkType}" var="workType">
														<option value="${workType.value}">${workType.label}</option>
													</c:forEach>
												</select>
											</div>
										</td>
										<td width="10%"><label class="col-sm-12 control-label">申請單位 :</label></td>
										<td width="40%">
											<div class="col-sm-7">
												<select id="applyDept" class="populate placeholder" name="country" >
													<option value="">---請選擇---</option>
													<c:forEach items="${siteWorkList}" var="siteWork">
														<option value="${siteWork.APP_DEPT}">${siteWork.deptName}</option>
													</c:forEach>
												</select>
											</div>
										</td>
									</tr>
									<tr>
									<td><div class="clearfix">&nbsp;</div></td>
									</tr>
									<tr>
										<td><label class="col-sm-12 control-label">Search Ring ID :</label></td>
										<td>
											<div class="col-sm-8">
												<input id="siteWorkSrId" type="text" class="form-control" />
											</div>
										</td>
										<td><label class="col-sm-12 control-label">行政區 : </label></td>
										<td nowrap="nowrap">
											<div class="col-sm-6">
												<select id="siteWorkCity" class="populate placeholder" name="country" >
												</select>
											</div>
											<div class="col-sm-6">
												<select id="siteWorkArea" class="populate placeholder" name="country" >
												</select>
											</div>
										</td>
									</tr>
									<tr>
										<td><div class="clearfix">&nbsp;</div></td>
									</tr>
									<tr>
										<td><label class="col-sm-12 control-label">基站編號 : </label></td>
										<td>
											<div class="col-sm-8">
												<input id="siteWorkBtsSiteId" type="text" class="form-control" />
											</div>
										</td>
										<td><label class="col-sm-12 control-label">基站名稱 : </label></td>
										<td>
											<div class="col-sm-7">
												<input id="siteWorkSiteName" type="text" class="form-control" placeholder="關鍵字查詢" />
											</div>
										</td>
									</tr>
									<tr>
										<td><div class="clearfix">&nbsp;</div></td>
									</tr>
									<tr>
										<td><label class="col-sm-12 control-label">站點編號 : </label></td>
										<td>
											<div class="col-sm-8">
												<input id="locationId" type="text" class="form-control" />
											</div>
										</td>
										<td><label class="col-sm-12 control-label">基站頻段 : </label></td>
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
									<tr>
										<td><div class="clearfix">&nbsp;</div></td>
									</tr>
									<tr>
										<td><label class="col-sm-12 control-label">申請日期(起) : </label></td>
										<td>
											<div class="col-sm-8">
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
									</tr>
									<tr>
										<td><div class="clearfix">&nbsp;</div></td>
									</tr>
									<tr>
										<td><label class="col-sm-12 control-label">處理狀態 : </label></td>
										<td>
											<div class="col-sm-8">
												<select id="workStatus" class="populate placeholder" name="country" >
													<option value="">---請選擇---</option>
													<c:forEach items="${allWorkStatus }" var="workStatus">
														<option value="${workStatus.value}">${workStatus.label}</option>
													</c:forEach>
												</select>
											</div>
										</td>
										<td></td>
										<td></td>
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