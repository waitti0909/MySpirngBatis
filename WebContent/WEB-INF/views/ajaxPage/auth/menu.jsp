<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<title>系統清單選單設定</title>
		<script type="text/javascript">
		
			function Select2Test() {
				$("#s2_RoleSelect").select2();
				$("#s2_MenuSelect").select2();
				$("#selectParentId").select2();
			}
			$(document).ready(function() {
				// Load script of Select2 and run this	
				LoadSelect2Script(
					'<s:url value="/resources/plugins/select2/select2.min.js" />',Select2Test);
				WinMove();
				
				$("#btn_reset").click(function(){
					$("#searchForm").trigger('reset');
					Select2Test();
					var data={selectID : 0};
					$("#grid").setGridParam({datatype:"json",postData:data, mtype: 'POST',page:1}).jqGrid().trigger("reloadGrid");
				});//resetButton end
				$('#btn_add').click(function() {
					$.fancybox.open({
			 			href : CONTEXT_URL+"/auth/systemMenu/add",
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
// 			 	        	$('#addForm :input:enabled:visible:first').focus();
			 			}
				 	   
			 		});		 		
				});//addButton end
	
		
				$("#btn_search").click(function(){
					var data= {
							selectID: $.trim($('#selectParentId').val())//serialize()
						};
						$("#grid").setGridParam({page:1,datatype:"json", postData:data, mtype: 'POST',page:1}).jqGrid().trigger("reloadGrid");
				});//searchButton end
				
				$("#grid").jqGrid({
					datatype: "local",
					pager: '#pager',
					url : CONTEXT_URL+"/auth/systemMenu/select_list",
				   	colNames:['menuID','清單名稱','清單說明','繼承編號','清單排序','程式路徑','資料夾','建立員工名稱','建立時間','修改員工名稱','修改時間'],
				   	colModel:[
// 						{name:'act', index:'act', width:100, align:'center', sortable:false, search:false},
						{name:'menu_ID',index:'menu_ID',hidden:true, sortable: false},
				   		{name:'menu_NAME',index:'menu_NAME',width:100, sortable: false},
				   		{name:'menu_DESC',index:'menu_DESC',width:100, sortable: false },
				   		{name:'parent_NAME',index:'parent_NAME',width:100, sortable: false,formatter : parentNameFormat},
				   		{name:'menu_SORT',index:'menu_SORT',width:100, sortable: false},
				   		{name:'pgm_PATH',index:'pgm_PATH',width:100, sortable: false},
				   		{name:'is_FODR',index:'is_FODR',width:100, sortable: false ,formatter :isFordFormat},
				   		{name:'cr_USER',index:'cr_USER',width:100, sortable: false},
				   		{name:'cr_TIME',index:'cr_TIME',width:108, sortable: false},
				   		{name:'md_USER',index:'md_USER', width:100,sortable: false},
				   		{name:'md_TIME',index:'md_TIME', width:108,sortable: false},
				   		
					],	
				   	viewrecords: true,
				   	caption:"系統功能清單",
					rownumbers:true,
					multiselect: false,
				   	onSelectRow: function(id){ 
				   		
				    },
				    gridComplete: function(){
						var ids = jQuery("#grid").jqGrid('getDataIDs');
						for(var i=0;i < ids.length;i++){
							var cl = ids[i];  // cl:jqgrid的行數，非資料真正的HEADER_ID
							var row = $("#grid").jqGrid('getRowData', cl);
// 							var id = row.menu_ID;
// 		 					be = "<input type='button' value='編輯' name='edit_'"+id+" id='edit_'"+id+" onclick='updateMenu("+id+")' />";
// 			  				jQuery("#grid").jqGrid('setRowData', ids[i], {act:be});
						}	
					},
					ondblClickRow: function(rowId) { //double clicking a jqgrid row
			            var rowData = jQuery(this).getRowData(rowId);
			            $.each(rowData, function(i,item) {
			            	if(i=="menu_ID"){
			            		updateMenu("view",item);
			            	}
			            });
			        }
				});//end grid
				
				$("#btn_delete").click(function(){
					
					//取得被選取列
					var ids = $("#grid").jqGrid('getGridParam','selrow');
					//是否選取
					if (ids == null) {
						alert("請選擇一筆資料刪除");
						return false;
					}else{
						var row = $("#grid").jqGrid('getRowData',ids);
						deleteMenu(row.menu_ID);
					}
					
				});//deleteButton end
				
				$("#btn_edit").click(function(){					
					//取得被選取列
					var ids = $("#grid").jqGrid('getGridParam','selrow');
					//是否選取
					if (ids == null) {
						alert("請選擇一筆資料編輯");
						return false;
					}else{
						var row = $("#grid").jqGrid('getRowData',ids);
						updateMenu("edit",row.menu_ID);
					}		

				});//btn_edit end
				$(window).resize(function(){
				    $(window).unbind("onresize");
				    $("#grid,.grid").setGridWidth($("#jQgrid").width() -10);
				    $(window).bind("onresize", this);
				}).trigger('resize');
				
			});//document.ready end
	
			function updateMenu(showType,id){
				$.fancybox.open({
		 			href : CONTEXT_URL+"/auth/systemMenu/updateForm",
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
	 	            		"showType" : showType,
	 	 					"menuID" : id
	 	 				},
		 	        },
		 			afterClose : function() {
		 				var menuID= $.trim($('#selectParentId').val());
		 				$.ajax({
		 					url : '<s:url value="/auth/systemMenu/selectOption"/>',
		 					async : false,
		 					success : function(datas) {
		 						$('#selectParentId').html('');
		 						$('<option id="notSelect" value="">--Select a Menu --</option>').appendTo('#selectParentId');
		 						for(var data in datas){
		 							$('<option id="'+data+'" value="'+data+'">'+datas[data]+'</option>').appendTo('#selectParentId');
		 						}
		 						$('#selectParentId').select2("val",$('#'+menuID).val());
		 						var data= {selectID: menuID};
		 						$("#grid").setGridParam({page:1,datatype:"json", postData:data, mtype: 'POST'}).jqGrid().trigger("reloadGrid");
			 					
		 					}
	 					});
		 				var data= {selectID: menuID};
 						$("#grid").setGridParam({page:1,datatype:"json", postData:data, mtype: 'POST'}).jqGrid().trigger("reloadGrid");
	 					
// 						$("#searchForm").trigger('reset');
						
		 			}
		 		});	
			}//update function end
			function parentNameFormat( cellvalue, options, cell){
		        if(cellvalue==""||cellvalue==null){
		            return "無";
		        }else{
		            return cellvalue;
		        }
	        }
			function isFordFormat( cellvalue, options, cell){
		        if(cellvalue=="1"){
		            return "是";
		        }else{
		            return "否";
		        }
	        }
			
			function deleteMenu(menuID){
	        	 $.ajax({
						url : '<s:url value="/auth/systemMenu/delete"/>',
						async : false,
						data : {
							"menuID" : menuID
						},
						success : function(data) {
							if(data.success){
								alert('<s:message code="msg.delete.success"/>');
//								$.ajax({
//				 					url : '<s:url value="/auth/systemMenu/selectOption"/>',
//				 					async : false,
//				 					success : function(datas) {
//				 						$('#selectParentId').html('');
//				 						$('<option id="notSelect" value="">--Select a Menu --</option>').appendTo('#selectParentId');
//				 						for(var data in datas){
//				 							$('<option id="'+data+'" value="'+data+'">'+datas[data]+'</option>').appendTo('#selectParentId');
//				 						}
//				 					}
//			 					});
//								$("#searchForm").trigger('reset');
//								Select2Test();
//								var data= {selectID: ""};
//		 						$("#grid").setGridParam({page:1,datatype:"json",postData:data, mtype: 'POST',page:1}).jqGrid().trigger("reloadGrid");
								$("#selectParentId").select2("val", "");		
								$("#grid").jqGrid().clearGridData();
		 						
							}else{
								alert('<s:message code="msg.delete.fail"/>：' + data.msg);
							}
							
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
							<i class="fa fa-search"></i> <span>系統清單查詢</span>
						</div>
						<div class="box-icons">
							<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
							</a> <a class="expand-link"> <i class="fa fa-expand"></i>
							</a>
						</div>
						<div class="no-move"></div>
					</div>
					<div class="box-content">
<!-- 							<h4 class="page-header">查詢條件</h4> -->
						<form role="form" class="form-horizontal bootstrap-validator-form" novalidate="novalidate" id="searchForm" action='<s:url value="/auth/systemMenu/"/>' >
							<div class="form-group">
								<!--  系統功能  -->
								<label class="col-sm-2 control-label">系統功能</label>
								<div class="col-sm-3">
								<div id="test"></div>
									<select class="populate placeholder" name="country" id="selectParentId">
										<option id="notSelect" value="">--Select a Menu --</option>
										<c:forEach items="${allMenus}" var="menu">
											<option id="${menu.MENU_ID}" value="${menu.MENU_ID}">${menu.MENU_NAME}</option>
										</c:forEach>
									</select>
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