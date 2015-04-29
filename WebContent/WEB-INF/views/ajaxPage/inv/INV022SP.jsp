<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<title>料號查詢</title>
		<style>
		.labelStyle {
		   margin-right: -80px;
		}
		</style>

		<script type="text/javascript">
			var executed = false;
			$(document).ready(function() {				

			});//end grid
			
			//類別
			$("#catgCode1Select").change(function(){
				//中分類
				$("#catgCode2Select").html("");
				$("<option value=''>-- 請選擇 --</option>").appendTo("#catgCode2Select");
				
				//小分類
				$("#catgCode3Select").html("");
				$("<option value=''>-- 請選擇 --</option>").appendTo("#catgCode3Select");
				
				var catgCode1 = $("#catgCode1Select").find("option:selected").prop("value");
				var data = {
					catgCode1 : catgCode1
				};
				 
				if (catgCode1 != "" || catgCode1 != ''){
					$.ajax({			
						url : CONTEXT_URL+"/ajax/inv/public/categoryCode2/",		
						data: data,
						type : "POST",
						dataType : 'json', 
						success : function(data) {
							if(data.success){
								if(data.rows.length > 0){
									$("#catgCode2Select  option").remove();
									$("#catgCode2Select").append($("<option></option>").prop("value", "").text("--請選擇--"));
									for(i=0; i<data.rows.length; i++){
										$("#catgCode2Select").append("<option value="+data.rows[i].catg2_code+">"+data.rows[i].catg2_name+"</option>");
										$("#catgCode2Select").prop("disabled",false);
									}							 
								}								
							}
						}
					});
				}
				$("#catg2Select").prop("disabled","disabled").prop("value","").prop("text","--請選擇--");
				$("#catg3Select").prop("disabled","disabled").prop("value","").prop("text","--請選擇--");
			});
		
			//中分類
			$("#catgCode2Select").change(function(){
				//小分類
				$("#catgCode3Select").html("");
				$("<option value=''>-- 請選擇 --</option>").appendTo("#catgCode3Select");
				
				var catgCode1 = $.trim($("#catgCode1Select").val());	//$("#catgCode1Select").find("option:selected").prop("value");
				var catgCode2 = $.trim($("#catgCode2Select").val());	//$("#catgCode2Select").find("option:selected").prop("value");
				var data = {
					catgCode1 : catgCode1,
					catgCode2 : catgCode2
				};

				if(catgCode1 != "" || catgCode2 != ''){
					$.ajax({			
						url : CONTEXT_URL+"/ajax/inv/public/categoryCode3/",		
						data:data,
						type : "POST",
						dataType : 'json',
						success : function(data) {
							if(data.success){
								if(data.rows.length > 0){
									for(i=0; i<data.rows.length; i++){		
										$("#catgCode3Select").append("<option value="+data.rows[i].catg3_code+">"+data.rows[i].catg3_name+"</option>");
										$("#catgCode3Select").prop("disabled",false);
									}							 
								}
							}
						}
					});
				}	
			});
			
			//query
			$('#materialSearchBtn').click(function() {
				var catgCode1 = $("#catgCode1Select").find("option:selected").prop("value");
				var catgCode2 = $("#catgCode2Select").find("option:selected").prop("value");
				var catgCode3 = $("#catgCode3Select").find("option:selected").prop("value");
				var data = {
						catgCode1 : catgCode1,
						catgCode2 : catgCode2,
						catgCode3 : catgCode3					
					};
				//if(catgCode1 == '' && catgCode1 == ""){
				//	alert("請選擇類別。");
				//}else{
					$("#gridMaterial").setGridParam({datatype:"json", postData:data, mtype: 'POST',page:1}).jqGrid().trigger("reloadGrid");
				//}
			});
			
			//result
			$("#gridMaterial").jqGrid({
				datatype : "local",
				pager : '#INV022SP_Pager',
				shrinkToFit: true,
				autowidth:true,
				url : CONTEXT_URL + "/inv/inv022SP/Search/",
				colNames : [ '類別','中分類','小分類','料號','品名','單位','控管方式','認列資產' ],
				colModel : [ 
				    {name : 'catg1_name',index : 'catg1_name', align : 'center',sortable : false},
				    {name : 'catg2_name',index : 'catg2_name',align : 'center',sortable : false},
				    {name : 'catg3_name',index : 'catg3_name', align : 'center',sortable : false},
				    {name : 'mat_no',index : 'mat_no', align : 'center',sortable : false},
				    {name : 'mat_name',index : 'mat_name', align : 'center',sortable : false},
				    {name : 'unit',index : 'unit',align : 'center',sortable : false},
				    {name : 'ctrl_type_dscr',index : 'ctrl_type_dscr', align : 'center',sortable : false},
				    {name : 'is_asset_dscr',index : 'is_asset_dscr', align : 'center',sortable : false}	
				    ],
				multiselect : false,
				caption : "物料查詢結果",
				rownumbers : true,
				onSelectRow : function() {
					// do something when select a row
				},
				gridComplete: function(){
					var ids = jQuery("#gridMaterial").jqGrid('getDataIDs');
					for(var i=0;i < ids.length;i++){
						var cl = ids[i];  // cl:jqgrid的行數，非資料真正的HEADER_ID
						$("#gridMaterial").jqGrid('getRowData', cl);
					}	
				},
				ondblClickRow: function(rowId) { //double clicking a jqgrid row
		            var rowData = jQuery(this).getRowData(rowId);
		            $.each(rowData, function(i,item) {

		            });
		        }
			});//end grid	
			
			$(window).resize(function() {
				$(window).unbind("onresize");
				$("#gridMaterial").setGridWidth($("#jQgrid").width() - 500);
				$(window).bind("onresize", this);
			}).trigger('resize');
			
			//確定
			$("#materialOkBtn").click(function(){
				var ids =$("#gridMaterial").jqGrid('getGridParam','selrow');
				if (ids != "" && ids != null) {					
					var row = $("#gridMaterial").jqGrid('getRowData', ids);
					$("#matNoMaterial").val(row.mat_no).change();
					$("#matNameMaterial").val(row.mat_name);					
					$("#${targetFrameId}").dialog('close');
				}else {
		        	alert("先選取一列資料列");
		        }
			});//ok button end
			
			//重置按鈕
			$('#materialResetBtn').click(function() {
				$("#catgCode1Select").prop("value","").prop("text","--請選擇--");
				$("#catgCode2Select").html("");
				$("<option value=''>-- 請選擇 --</option>").appendTo("#catgCode2Select");
				$("#catgCode3Select").html("");
				$("<option value=''>-- 請選擇 --</option>").appendTo("#catgCode3Select");
				$("#gridMaterial").jqGrid().clearGridData();
			});	
		</script>
	</head>
	<body>
		<button class="btn btn-primary btn-label" type="button" id="materialSearchBtn">
			<s:message code="button.search" text="查詢" />
		</button>
		<button class="btn btn-primary btn-label" type="button" id="materialOkBtn">
			<s:message code="button.ok" text="確定" />
		</button>
		<button class="btn btn-primary btn-label" type="button" id="materialResetBtn">
			<s:message code="button.reset" text="重置" />
		</button>
		
		<div class="row">
			<div class="col-xs-12 col-sm-12">
				<div class="box ui-draggable ui-droppable">
					<div class="box-header">
						<div class="box-name">
							<i class="fa fa-search"></i>
							<span>料號查詢</span>
						</div>
						<div class="box-icons">
							<a class="collapse-link"> <i class="fa fa-chevron-up"></i> </a>
							<a class="expand-link"> <i class="fa fa-expand"></i> </a>
						</div>
						<div class="no-move"></div>
					</div>
				</div>
				<div class="box-content">
				<form role="form" class="form-horizontal bootstrap-validator-form" novalidate="novalidate" id="searchMaterialForm">
					<div class="form-group">
						<table style="width:150%">
							<tr>
								<td><label class="col-sm-10 control-label">類別 :</label></td>
								<td>	
									<div class="col-sm-6">
										<select id="catgCode1Select" class="form-control">
											<option value="">--- 請選擇 ---</option>
											<c:forEach items="${catgCode1Select}" var="catg1">
												<option value="${catg1.catg1_code}">${catg1.catg1_name}</option>
											</c:forEach>													
										</select>
									</div>
								</td>
								<td><label class="col-sm-10 control-label">中分類 :</label></td>
								<td>
									<div class="col-sm-6">
										<select id="catgCode2Select" class="form-control">
											<option value="">--- 請選擇 ---</option>
											<c:forEach items="${catgCode2Select}" var="catg2">
												<option value="${catg2.catg2_code}">${catg2.catg2_name}</option>
											</c:forEach>
										</select>
									</div>
								</td>
							</tr>
							<tr>
								<td><div class="clearfix">&nbsp;</div></td>
							</tr>
							<tr>
								<td><label class="col-sm-10 control-label">小分類 :</label></td>
								<td>
									<div class="col-sm-6">
										<select id="catgCode3Select" class="form-control">
											<option value="">--- 請選擇 ---</option>
											<c:forEach items="${catgCode3Select}" var="catg3">
												<option value="${catg3.catg3_code}">${catg3.catg3_name}</option>
											</c:forEach>
										</select>
									</div>
								</td>
							</tr>
						</table>
					</div>
				</form>
			</div>
			</div>
			<div class="form-group">
				<div class="row">
					<div class="col-md">
						<div id="ajaxSearchResult" class="col-sm-12 "
							style="width: 1200px;">
							<!-- 固定id for window.resize start-->
							<div id="jQgrid" align="center">
								<table id="gridMaterial"></table>
								<div id="INV022SP_Pager"></div>
							</div>
						</div>
					</div>
				</div>
		</div>
	</body>
</html>