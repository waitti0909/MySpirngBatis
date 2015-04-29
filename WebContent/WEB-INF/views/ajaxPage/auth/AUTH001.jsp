<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<title>使用者維護 查詢</title>
		
		<script type="text/javascript">
			$(document).ready(function() {
				
				$('#btn_add').click(function() {
					editAddPSN("add");
				});
				
				$("#btn_reset").click(function(){
					$("#searchPSNFrom").trigger('reset');
					$("#grid").jqGrid().clearGridData();
				});//reset button end
				
				$("#btn_search").click(function(){
					var data = (form2js($("#searchPSNFrom").attr("id"), '.',false, null));					
					$("#grid").setGridParam({datatype:"json", postData:data, mtype: 'POST',page:1}).jqGrid().trigger("reloadGrid");					
				});//search button end
				
				$("#grid").jqGrid({
					datatype: "local",
					pager: '#pager',
					url : CONTEXT_URL+"/autn/personnel/search",
				   	colNames:['PSNId','員工編號','帳號','Email','部門名稱','終止日','離職否'],
				   	colModel:[
						{name:'psn_ID',index:'psn_ID',hidden:true, sortable: false},
						{name:'psn_NO',index:'psn_NO', sortable: false},
						{name:'eng_NM',index:'eng_NM', sortable: false},
						{name:'e_MAIL',index:'e_MAIL', width:180, sortable: false},
						{name:'deptName',index:'deptName', sortable: false},
						{name:'expire_DATE',index:'expire_DATE', sortable: false},
						{name:'dismission',index:'dismission', width:60,sortable: false}
					],	
				   	viewrecords: true,
				   	caption:"使用者帳號清單",
					rownumbers:true,
					multiselect: false,
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
			            	if(i=="psn_ID"){
			            		editAddPSN("view",item);
			            	}
			            });			            
			        }
				});//end grid
				
				$(window).resize(function(){
				    $(window).unbind("onresize");
				    $("#grid,.grid").setGridWidth($("#jQgrid").width() -10);
				    $(window).bind("onresize", this);
				}).trigger('resize');
				
				$('#btn_edit').click(function(){
					//取得被選取列
					var ids = $("#grid").jqGrid('getGridParam','selrow');
					//是否選取
					if (ids == null) {
						alert("請選擇一筆資料編輯");
						return false;
					}else{
						var psn = $("#grid").jqGrid('getRowData',ids);
						editAddPSN("edit",psn.psn_ID);
					}	
				});
				
			});//document.ready end
			
			
			function editAddPSN(btnType,psn_ID){
				$.fancybox.open({
		 			href : CONTEXT_URL+"/auth/personnel/editAddPage",
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
		 			title : "新增/修改",
		 			openEffect : 'elastic',
		 			closeEffect : 'fade',
		 			ajax : {
		 				data : {
		 					psn_ID : psn_ID,
		 					btnType : btnType
						},
		 	            type: 'POST'	 	            
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
							<i class="fa fa-search"></i> <span>使用者維護 查詢</span>
						</div>
						<div class="box-icons">
							<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
							</a> <a class="expand-link"> <i class="fa fa-expand"></i>
							</a>
						</div>
						<div class="no-move"></div>
					</div>
					
									<div class="box-content">
					<form class="form-horizontal bootstrap-validator-form"
						id="searchPSNFrom" novalidate="novalidate">
						<div class="form-group">
							<label class="col-sm-2 control-label">員工編號 :</label>
							<div class="col-sm-4">
								<input id="psnNO001" type="text" class="form-control" name="psn_NO"
									placeholder="員工編號">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">員工帳號 :</label>
							<div class="col-sm-4">
								<input id="engNM001" type="text" class="form-control"
									name="eng_NM" placeholder="關鍵字查詢">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">Email :</label>
							<div class="col-sm-6">
								<input id="email001" type="text" class="form-control" name="e_MAIL"
									placeholder="Email">
							</div>
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