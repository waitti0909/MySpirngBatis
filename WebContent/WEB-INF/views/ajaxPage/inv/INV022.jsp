<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>


<!DOCTYPE html>
<html lang="en">
	<head>
		<title>物料查詢作業</title>

		<script type="text/javascript">
			var executed = false;
			$(document).ready(function() {				
				LoadSelect2Script('<s:url value="/resources/plugins/select2/select2.min.js" />', selectDefault);
			});//end grid					

			//類別
			$("#catg1Select").change(function(){
				//中分類
				$("#catg2Select").html("");
				$("<option value=''>-- 請選擇 --</option>").appendTo("#catg2Select");
				$("#catg2Select").select2();
				
				//小分類
				$("#catg3Select").html("");
				$("<option value=''>-- 請選擇 --</option>").appendTo("#catg3Select");
				$("#catg3Select").select2();
				
				//料號
				$("#matNoSelect").html("");
				$("<option value=''>-- 請選擇 --</option>").appendTo("#matNoSelect");
				$("#matNoSelect").select2();
				
				var catgCode1 = $("#catg1Select").find("option:selected").prop("value");
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
									$("#catg2Select  option").remove();
									$("#catg2Select").append($("<option></option>").attr("value", "").text("--請選擇--"));
									for(i=0; i<data.rows.length; i++){
										$("#catg2Select").append("<option value="+data.rows[i].catg2_code+">"+data.rows[i].catg2_name+"</option>");
										$("#catg2Select").prop("disabled",false);
									}							 
								}
							}
						}
					});
				}				
			});
		
			//中分類
			$("#catg2Select").change(function(){
				//小分類
				$("#catg3Select").html("");
				$("<option value=''>-- 請選擇 --</option>").appendTo("#catg3Select");
				$("#catg3Select").select2();
				
				//料號
				$("#matNoSelect").html("");
				$("<option value=''>-- 請選擇 --</option>").appendTo("#matNoSelect");
				$("#matNoSelect").select2();
				
				var catgCode1 = $("#catg1Select").find("option:selected").prop("value");
				var catgCode2 = $("#catg2Select").find("option:selected").prop("value");
				var data = {
					catgCode1 : catgCode1,
					catgCode2 : catgCode2
				};

				//defaultSelect(1);
				if(catgCode1 != "" || catgCode2 != ''){
					$.ajax({			
						url : CONTEXT_URL+"/ajax/inv/public/categoryCode3/",		
						data:data,
						type : "POST",
						dataType : 'json',
						success : function(data) {
							if(data.success){
								if(data.rows.length > 0){
									$("#catg3Select  option").remove();
									$("#catg3Select").append($("<option></option>").attr("value", "").text("--請選擇--"));
									for(i=0; i<data.rows.length; i++){		
										$("#catg3Select").append("<option value="+data.rows[i].catg3_code+">"+data.rows[i].catg3_name+"</option>");
										$("#catg3Select").prop("disabled",false);
									}							 
								}
							}
						}
					});
				}else{
					$("#matNoSelect").prop("disabled","disabled").prop("value","").prop("text","--請選擇--");
				}		
			});
			
			//小分類
			$("#catg3Select").change(function(){
				//料號
				$("#matNoSelect").html("");
				$("<option value=''>-- 請選擇 --</option>").appendTo("#matNoSelect");
				$("#matNoSelect").select2();
				
				var catgCode1 = $("#catg1Select").find("option:selected").prop("value");
				var catgCode2 = $("#catg2Select").find("option:selected").prop("value");
				var catgCode3 = $("#catg3Select").find("option:selected").prop("value");
				var data = {
					catgCode1 : catgCode1,
					catgCode2 : catgCode2,
					catgCode3 : catgCode3
				};
				if((catgCode2 != "" || catgCode2 != '') && (catgCode3 != "" || catgCode3 != '')){
					$.ajax({			
						url : CONTEXT_URL+"/ajax/inv/public/materialByCateCode/",		
						data:data,
						type : "POST",
						dataType : 'json',
						success : function(data) {
							if(data.success){
								if(data.rows.length > 0){
									for(i=0; i<data.rows.length; i++){		
										$("#matNoSelect").append("<option value="+data.rows[i].mat_no+">"+data.rows[i].mat_name+"</option>");
										$("#matNoSelect").prop("disabled",false);
									}							 
								}
							}
						}
					});
				}else{
					$("#matNoSelect").prop("disabled","disabled").prop("value","").prop("text","--請選擇--");
				}		
			});
			
			//query
			$('#btn_search').click(function() {
				var catgCode1 = $("#catg1Select").find("option:selected").prop("value");
				var catgCode2 = $("#catg2Select").find("option:selected").prop("value");
				var catgCode3 = $("#catg3Select").find("option:selected").prop("value");
				var matNo = $("#matNoSelect").find("option:selected").prop("value");
				var data = {
						catgCode1 : catgCode1,
						catgCode2 : catgCode2,
						catgCode3 : catgCode3,
						matNo : matNo					
					};
				//if(catgCode1 == '' && catgCode1 == ""){
				//	alert("請選擇類別。");
				//}else{
					$("#grid").setGridParam({datatype:"json", postData:data, mtype: 'POST',page:1}).jqGrid().trigger("reloadGrid");
				//}
			});
			
			//result
			$("#grid").jqGrid({
				datatype : "local",
				pager : '#pager',
				url : CONTEXT_URL + "/inv/inv022/Search/",
				colNames : [ '類別代碼', '類別', '中分類代碼', '中分類','小分類代碼','小分類','料號','品名','單位','控管方式','認列資產' ],
				colModel : [ 
				    {name : 'catg1_code',index : 'catg1_code',width : 70 , align : 'center',sortable : false},
				    {name : 'catg1_name',index : 'catg1_name',width : 90 , align : 'center',sortable : false}, 
				    {name : 'catg2_code',index : 'catg2_code',width : 80 , align : 'center',sortable : false},
				    {name : 'catg2_name',index : 'catg2_name',width : 110 , align : 'center',sortable : false}, 
				    {name : 'catg3_code',index : 'catg3_code',width : 80 , align : 'center',sortable : false}, 
				    {name : 'catg3_name',index : 'catg3_name',width : 110 , align : 'center',sortable : false},
				    {name : 'mat_no',index : 'mat_no',width : 70 , align : 'center',sortable : false},
				    {name : 'mat_name',index : 'mat_name',width : 80 , align : 'center',sortable : false},
				    {name : 'unit',index : 'unit',width : 60 , align : 'center',sortable : false},
				    {name : 'ctrl_type_dscr',index : 'ctrl_type_dscr',width : 80 , align : 'center',sortable : false},
				    {name : 'is_asset_dscr',index : 'is_asset_dscr',width : 60 , align : 'center',sortable : false}				    
				    ],
				multiselect : false,
				caption : "物料查詢結果",
				rownumbers : true,
				onSelectRow : function() {
					// do something when select a row
				},
				gridComplete : function() {
					if(jQuery("#grid").jqGrid('getGridParam', 'records') == -1){
						alert('查詢資料超過1000,請改按匯出EXCEL');
					}
				}
			});//end grid	
			
			$(window).resize(function() {
				$(window).unbind("onresize");
				$("#grid,.grid").setGridWidth($("#jQgrid").width() - 10);
				$(window).bind("onresize", this);
			}).trigger('resize');
			
			//excel
			$('#btn_to_excel').click(function() {
				var catgCode1 = $("#catg1Select").find("option:selected").prop("value");
				var catgCode2 = $("#catg2Select").find("option:selected").prop("value");
				var catgCode3 = $("#catg3Select").find("option:selected").prop("value");
				var matNo = $("#matNoSelect").find("option:selected").prop("value");
				
				//if(catgCode1 == '' && catgCode1 == ""){
				//	alert("請選擇類別。");
				//}else{
					window.location.href = CONTEXT_URL + "/inv/inv022/getExcel?catgCode1="+catgCode1+"&catgCode2="+catgCode2+"&catgCode3="+catgCode3+"&matNo="+matNo;
				//}
			});
			
			//重置按鈕
			$("#btn_reset").click(function() {
				$("#catg1Select").select2("val", "");
				
				$("#catg2Select").html("");
				$("<option value=''>-- 請選擇 --</option>").appendTo("#catg2Select");
				$("#catg2Select").select2();
				
				$("#catg3Select").html("");
				$("<option value=''>-- 請選擇 --</option>").appendTo("#catg3Select");
				$("#catg3Select").select2();
				
				$("#matNoSelect").html("");
				$("<option value=''>-- 請選擇 --</option>").appendTo("#matNoSelect");
				$("#matNoSelect").select2("val", "");

				$("#grid").jqGrid().clearGridData();
			});	
			
			// 開啟下拉式選單搜尋文字功能
			function selectDefault() {
				$("#searchFrom").find("select").select2();
			}
		</script>
	</head>
	<body>
		<div class="row">
			<div id="breadcrumb" class="col-md-12">
				<ol class="breadcrumb">
					<%-- Button import --%>
					<c:import url="/auth/role/menu/func/${currentMenuId}" />
				</ol>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-12 col-sm-12">
				<div class="box ui-draggable ui-droppable">
					<div class="box-header">
						<div class="box-name">
							<i class="fa fa-search"></i>
							<span>物料查詢作業</span>
						</div>
						<div class="box-icons">
							<a class="collapse-link"> <i class="fa fa-chevron-up"></i> </a>
							<a class="expand-link"> <i class="fa fa-expand"></i> </a>
						</div>
						<div class="no-move"></div>
					</div>
				</div>
				<div class="box-content">
					<form id="searchFrom" class="form-horizontal bootstrap-validator-form" role="form" novalidate="novalidate">
						<div class="form-group">
							<label class="col-sm-2 control-label">類別 :</label>
							<div class="col-sm-3">
								<select id="catg1Select" name="catg1Select">
									<option value="" selected="true">--請選擇--</option>
									<c:forEach items="${catg1Select}" var="catg1">
										<option value="${catg1.catg1_code}">${catg1.catg1_name}</option>
									</c:forEach>
								</select>
							</div>
							<label class="col-sm-2 control-label">中分類 :</label>
							<div class="col-sm-3">
								<select id="catg2Select" name="catg2Select">
									<option value="" selected="true">--請選擇--</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">小分類 :</label>
							<div class="col-sm-3">
								<select id="catg3Select" name="catg3Select">
									<option value="" selected="true">--請選擇--</option>

								</select>
							</div>
							<label class="col-sm-2 control-label">料號/品名 :</label>
							<div class="col-sm-3">
								<select id="matNoSelect" name="matNoSelect">
									<option value="" selected="true">--請選擇--</option>
								</select>
							</div>
						</div>
					</form>
				</div>
			</div>
			<div id="ajaxSearchResult" class="col-xs-12">
				<!-- 固定id for window.resize start-->
				<div id="jQgrid">
					<table id="grid"></table>
					<div id="pager"></div>
				</div>
				<!-- 固定id for window.resize end-->
			</div>
		</div>
	</body>
</html>