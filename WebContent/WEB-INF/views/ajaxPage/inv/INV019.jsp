<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>


<!DOCTYPE html>
<html lang="en">
	<head>
		<title>庫存查詢</title>

		<script type="text/javascript">
			var executed = false;
			$(document).ready(function() {
				LoadSelect2Script("<s:url value="/resources/plugins/select2/select2.min.js" />", selectDefault);
			});//end grid	
			
			// 開啟下拉式選單搜尋文字功能
			function selectDefault() {
				$("#searchFrom").find("select").select2();
			}
		
			//區域選單連動管理單位
			$("#domainSelect").change(function(){
				$("#deptSelect").html("");
				$("<option value=''>-- 請選擇 --</option>").appendTo("#deptSelect");
				$("#deptSelect").select2();
				
				var domain = $("#domainSelect").find("option:selected").prop("value");				
				var data = {
					domain : domain
				};
				if (domain != "" || domain != ''){
					$.ajax({			
						url : CONTEXT_URL+"/ajax/inv/public/deptByDomain/",		
						data: data,
						type : "POST",
						dataType : 'json',
						success : function(data) {
							if(data.success){
								if(data.rows.length > 0){
									$("#deptSelect  option").remove();
									$("#deptSelect").append($("<option></option>").attr("value", "").text("--請選擇--"));
									for(i=0; i<data.rows.length; i++){
										$("#deptSelect").append("<option value="+data.rows[i].dept_ID+">"+data.rows[i].dept_NAME+"</option>");
										$("#deptSelect").prop("disabled",false);
									}							 
								}
							}
						}
					});
				}else{
					$("#deptSelect").html("");
					$("<option value=''>-- 請選擇 --</option>").appendTo("#deptSelect");
					$("#deptSelect").select2();
				}				
			});
			
			//query
			$('#btn_search').click(function() {
				var item1 = ($("#tagNoFlag").prop('checked') == true)? "Y": "";
				var item2 = ($("#remainFlag").prop('checked') == true)? "Y": "";
				var data = {
						matNo : $.trim($("#matNoMaterial").val()),
						wh_id : $.trim($("#wareHouseSelect").val()),
						domain : $.trim($("#domainSelect").val()), // 區域domainSelect
						dept_id : $.trim($("#deptSelect").val()),
						tagNoFlag : item1,
						remainFlag : item2				
					};
				$("#grid").setGridParam({datatype : "json",	postData : data,page:1}).trigger("reloadGrid");	
			});
			
			//result
			$("#grid").jqGrid({
				datatype : "local",
				pager : '#pager',
				url : CONTEXT_URL + "/inv/inv019/Search/",
				colNames : ['倉庫','大分類代碼','大分類','中分類代碼','中分類','小分類代碼','小分類','料號','品名','廠商序號','貼標號碼','可用品','待送修','待報廢','庫存/總量' ],
				colModel : [ 
				    {name : 'wh_name',index : 'wh_id',width : 70 , align : 'center',sortable : false},
				    {name : 'catg1_code',index : 'catg1_code',width : 80 , align : 'center',sortable : false},
				    {name : 'catg1_name',index : 'catg1_name',width : 90 , align : 'center',sortable : false},
				    {name : 'catg2_code',index : 'catg2_code',width : 80 , align : 'center',sortable : false},
				    {name : 'catg2_name',index : 'catg2_name',width : 90 , align : 'center',sortable : false},
				    {name : 'catg3_code',index : 'catg2_code',width : 80 , align : 'center',sortable : false},
				    {name : 'catg3_name',index : 'catg3_name',width : 90 , align : 'center',sortable : false},	
				    {name : 'mat_no',index : 'mat_no',width : 70 , align : 'center',sortable : false},
				    {name : 'mat_name',index : 'mat_name',width : 80 , align : 'center',sortable : false},
				    {name : 'ven_sn',index : 'ven_sn',width : 90 , align : 'center',sortable : false}, 
				    {name : 'tag_no',index : 'tag_no',width : 90 , align : 'center',sortable : false},
				    {name : 'std_qty',index : 'std_qty',width : 55 , align : 'center',sortable : false},
				    {name : 'wrd_qty',index : 'wrd_qty',width : 55 , align : 'center',sortable : false},
				    {name : 'wsp_qty',index : 'wsp_qty',width : 55 , align : 'center',sortable : false},
				    {name : 'qty',index : 'qty',width : 70 , align : 'center',sortable : false}
				    ],
				multiselect : false,
				caption : "庫存明細查詢",
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
		
			//excel
			$('#btn_to_excel').click(function() {			
				var matNo = $.trim($("#matNoMaterial").val());
				var wh_id = $.trim($("#wareHouseSelect").val());
				var domain = $.trim($("#domainSelect").val());
				var dept_id = $.trim($("#deptSelect").val());
				var item1 = ($("#tagNoFlag").prop('checked') == true)? "Y": "";
				var item2 = ($("#remainFlag").prop('checked') == true)? "Y": "";
				window.location.href = CONTEXT_URL + "/inv/inv019/getExcel?matNo="+matNo+"&wh_id="+wh_id+"&domain="+domain+"&dept_id="+dept_id+"&tagNoFlag="+item1+"&remainFlag"+item2;
			});
			
			//料號選擇
			$("#materialSearch").click(function () {
				openMaterialFrame("selectMaterialPage"); // call common.js
			});
			
			$(window).resize(function() {
				$(window).unbind("onresize");
				$("#grid,.grid").setGridWidth($("#jQgrid").width() - 10);
				$(window).bind("onresize", this);
			}).trigger('resize');
			
			//重置按鈕
			$("#btn_reset").click(function() {
				$("#searchFrom").trigger('reset');	
				$("#matNoMaterial").prop("value","");
				$("#wareHouseSelect").select2("val", "");
				$("#domainSelect").select2("val", "");
				$("#deptSelect").html("");
				$("<option value=''>-- 請選擇 --</option>").appendTo("#deptSelect");
				$("#deptSelect").select2();
				$("#tagNoFlag").prop("checked", false);
				$("#remainFlag").prop("checked", false);
				$("#grid").jqGrid().clearGridData();
			});			
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
						<div class="box-name"><span>庫存查詢</span></div>
						<div class="box-icons">
							<a class="collapse-link"> <i class="fa fa-chevron-up"></i> </a>
							<a class="expand-link"> <i class="fa fa-expand"></i> </a>
						</div>
						<div class="no-move"></div>
					</div>
				</div>
				<div id="selectMaterialPage"></div>
				<div class="box-content">
					<form id="searchFrom" class="form-horizontal bootstrap-validator-form" role="form" novalidate="novalidate">
						<div class="form-group">
							<label class="col-sm-2 control-label">料號 :</label>
							<div class="col-sm-3">
								<input id="matNoMaterial" type="text" name="matNoMaterial" class="form-control fa fa-search" placeholder="關鍵字查詢" />
							</div>
							<div class="col-sm-1"><i  id="materialSearch" class="fa fa-search" style="cursor: pointer;"></i></div>
							<label class="col-sm-2 control-label">倉庫 :</label>
							<div class="col-sm-3">
								<select id="wareHouseSelect" name="wareHouseSelect">
									<option value="" selected="true">--請選擇--</option>
									<c:forEach items="${wareHouseSelect}" var="wareHouse">
										<option value="${wareHouse.wh_id}">${wareHouse.wh_name}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">區域 :</label>
							<div class="col-sm-3">
								<select id="domainSelect" name="domainSelect">
									<option value="">-- 請選擇 --</option>
									<c:forEach items="${domainSelect}" var="domain">
										<option value="${domain.ID}">${domain.NAME}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-sm-1"></div>
							<label class="col-sm-2 control-label">管理單位 :</label>
							<div class="col-sm-3">
								<select id="deptSelect" name="deptSelect">
									<option value="">-- 請選擇 --</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">貼標號碼 :</label>
							<div class="col-sm-3">
								<div class="checkbox-inline">
									<label><input id="tagNoFlag" type="checkbox" value="Y" /><i class="fa fa-square-o"></i></label>
								</div>
							</div>
							<div class="col-sm-1"></div>
							<label class="col-sm-2 control-label">零庫存 :</label>
							<div class="col-sm-3">
								<div class="checkbox-inline">
									<label><input id="remainFlag" type="checkbox" value="Y" /><i class="fa fa-square-o"></i></label>
								</div>								
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