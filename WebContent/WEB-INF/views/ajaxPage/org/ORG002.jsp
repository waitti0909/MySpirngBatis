<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<title>部門人員資料設定</title>
		<script type="text/javascript">
		
			function Select2Test() {
				$("#s2_RoleSelect").select2();
				$("#s2_MenuSelect").select2();
			}
			$(document).ready(function() {
				// Load script of Select2 and run this	
				LoadSelect2Script(
					'<s:url value="/resources/plugins/select2/select2.min.js" />',Select2Test);
				WinMove();
				
				$("#btn_reset").click(function(){
					$("#searchForm").trigger('reset');
					Select2Test();
					var data={deptId : 0,deptName : 0};
					$("#grid").setGridParam({page:1,datatype:"json",postData:data, mtype: 'POST',page:1}).jqGrid().trigger("reloadGrid");
				});//resetButton end
				$("#btn_search").click(function(){
					var data= {
							deptId: $.trim($('#deptId').val()),
							deptName: $.trim($('#deptName').val())
						};
						$("#grid").setGridParam({page:1,datatype:"json", postData:data, mtype: 'POST',page:1}).jqGrid().trigger("reloadGrid");
				});//searchButton end
// 				$("#btn_add").click(function(){
// 					// TODO
// 					$.fancybox.open({
// 						href : CONTEXT_URL + "/org/dept/modify",
// 						type : 'ajax',
// 						width : $(window).width(),
// 				        height : $(window).height(),
// 						autoSize : false,
// 						padding : [ 0, 0, 0, 0 ],
// 						scrolling : 'no',
// 						helpers : {
// 							overlay : { closeClick : false },
// 							title : { type : 'float' }
// 						},
// 						title : "Setting",
// 						openEffect : 'elastic', // fancy 開啟與關閉動畫
// 						closeEffect : 'fade',
// 						ajax : {
// 							// 搭配ajax傳送參數，也可直接加在URL後面
// 							type : "post",
// 							data : {
								
// 							}
// 						},
// 						beforeClose : function() {
							
// 						},
// 						afterClose : function() {
// 							// fancybox關閉後要做的事(callback function)
// 							$("#grid").trigger("reloadGrid");
// 						}
// 					});
// 				});
				
				$("#btn_edit").click(function(){
					// TODO
					//取得被選取列
					var ids = $("#grid").jqGrid('getGridParam','selarrrow');
					var ckdeptId = "21";
					//是否選取
					if(ids.length == 1){
						ckdeptId = $("#grid").jqGrid('getRowData', ids).dept_ID;
					}else if (ids.length == 0) {
						
					}else{
						alert("請選擇一筆資料編輯");
						return false;
					}				
					showORG002M(ckdeptId,"edit");
				});
				
				$("#grid").jqGrid({
					datatype: "local",
					pager: '#pager',
					url : CONTEXT_URL+"/org/dept/search",
				   	colNames:['部門代號','部門名稱','分區','類型'],
				   	colModel:[
						{name:'dept_ID',index:'dept_ID',width:100, sortable: false},
				   		{name:'dept_NAME',index:'dept_NAME',width:100, sortable: false},
				   		{name:'domain_NAME',index:'domain_NAME', width:100,sortable: false},
				   		{name:'type',index:'type', width:108,sortable: false},
				   	],	
				   	viewrecords: true,
				   	caption:"部門清單",
					rownumbers:true,
					multiselect: true,
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
			            	if(i=="dept_ID"){
			            		showORG002M(item,"view");
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
			
			function showORG002M(ckdeptId,showType){
				$.fancybox.open({
					href : CONTEXT_URL + "/org/dept/modify",
					type : 'ajax',
					width : $(window).width(),
			        height : $(window).height(),
					autoSize : false,
					padding : [ 0, 0, 0, 0 ],
					scrolling : 'no',
					helpers : {
						overlay : { closeClick : false },
						title : { type : 'float' }
					},
					title : "Setting",
					openEffect : 'elastic', // fancy 開啟與關閉動畫
					closeEffect : 'fade',
					ajax : {
						// 搭配ajax傳送參數，也可直接加在URL後面
						type : "post",
						data : {
							"showType" : showType,
							"deptId" : ckdeptId
						}
					},
					beforeClose : function() {
						
					},
					afterClose : function() {
						// fancybox關閉後要做的事(callback function)
						
						$("#grid").trigger("reloadGrid");
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
							<i class="fa fa-search"></i> <span>部門查詢</span>
						</div>
						<div class="box-icons">
							<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
							</a> <a class="expand-link"> <i class="fa fa-expand"></i>
							</a>
						</div>
						<div class="no-move"></div>
					</div>
					<div class="box-content">
						<form role="form" class="form-horizontal bootstrap-validator-form" novalidate="novalidate" id="searchForm">
							<div class="form-group">
								<!--  系統功能  -->
								<label class="col-sm-2 control-label">部門代號 :</label>
								<div class="col-sm-3">
									<input id="deptId" type="text" 
											name="deptId">
								</div>
								<label class="col-sm-2 control-label">部門名稱 :</label>
								<div class="col-sm-3">
									<input id="deptName" type="text" 
											name="deptName" placeholder="關鍵字查詢">
								</div>
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