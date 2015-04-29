<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<title>職務名稱維護設定</title>
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
// 					Select2Test();
					var data={positionCode : 0,positionName : 0};
					$("#grid").setGridParam({datatype:"json",postData:data, mtype: 'POST',page:1}).jqGrid().trigger("reloadGrid");
				});//resetButton end
				$('#btn_add').click(function() {
					$.fancybox.open({
			 			href : CONTEXT_URL+"/org/ORG001/add",
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
	
				$("#btn_search").click(function(){
					var data= {
							positionCode: $.trim($('#positionCode').val()),
							positionName: $.trim($('#positionName').val())
						};
						$("#grid").setGridParam({datatype:"json", postData:data, mtype: 'POST',page:1}).jqGrid().trigger("reloadGrid");
				});//searchButton end
				
				$("#grid").jqGrid({
					datatype: "local",
					pager: '#pager',
					url : CONTEXT_URL+"/org/ORG001/search",
				   	colNames:['職務代號','職務名稱','職務類別','修改人','修改時間'],
				   	colModel:[
						{name:'pos_ID',index:'pos_ID',width:100, sortable: false},
				   		{name:'pos_NAME',index:'pos_NAME',width:100, sortable: false},
				   		{name:'pos_TYPE',index:'pos_TYPE', width:100,sortable: false,formatter : posTypeFormat},
				   		{name:'md_USER',index:'md_USER', width:100,sortable: false},
				   		{name:'md_TIME',index:'md_TIME', width:108,sortable: false},
				   	],	
				   	viewrecords: true,
				   	caption:"職務名稱清單",
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
			            	if(i=="pos_ID"){
			            		updatePosition("view",item);
			            	}
			            });
			        }
				});//end grid
				$("#btn_edit").click(function(){
					var id =$("#grid").jqGrid('getGridParam','selarrrow');
					if (id!="") {
						if(id.length<=1){
							var row = $("#grid").jqGrid('getRowData', id);
							updatePosition("edit",row.pos_ID);
						}else{
							alert("不可同時修改多筆資料");
						}
					}else {
			        	alert("先選取一列資料列");
			        }
				});//btn_edit end
				$(window).resize(function(){
				    $(window).unbind("onresize");
				    $("#grid,.grid").setGridWidth($("#jQgrid").width() -10);
				    $(window).bind("onresize", this);
				}).trigger('resize');
				
			});//document.ready end
			
			function updatePosition(showType,id){
				$.fancybox.open({
		 			href : CONTEXT_URL+"/org/ORG001/update",
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
	 	 					"positionID" : id
	 	 				},
		 	        },
		 			afterClose : function() {
		
		 			}
		 		});	
			}//update function end
			
			function posTypeFormat( cellvalue, options, cell){
		        if(cellvalue=="N"){
		            return "一般";
		        }else if(cellvalue=="W"){
		            return "簽核";
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
							<i class="fa fa-search"></i> <span>職務名稱查詢</span>
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
								<label class="col-sm-2 control-label">職務代號 :</label>
								<div class="col-sm-3">
									<input id="positionCode" type="text" 
											name="positionCode">
								</div>
								<label class="col-sm-2 control-label">職務名稱 :</label>
								<div class="col-sm-3">
									<input id="positionName" type="text" 
											name="positionName"  placeholder="關鍵字查詢">
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