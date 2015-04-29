<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<title>工項查詢作業</title>
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
							<span>工項維護 工項查詢</span>
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
									<label class="col-sm-2 control-label">主項 :</label>
									<div class="col-sm-3">
										<select id="mainItemSelect" class="populate placeholder" name="mainItemSelect">
											<option value="" selected>---請選擇---</option>
											<c:forEach items="${sessionScope.mainItemList}" var="mainItem">
												<option value="${mainItem.CAT_ID}">${mainItem.CAT_NAME}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">中項 :</label>
									<div class="col-sm-3">
										<select id="subItemSelect" class="populate placeholder" name="subItemSelect">
											<option value="" selected>---請選擇---</option>
										</select>
									</div>
								</div>
							</form>
						</div>
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
	<script type="text/javascript">
		var executed = false;
		$(document).ready(function() {				
			LoadSelect2Script('<s:url value="/resources/plugins/select2/select2.min.js" />', selectDefault);
		});//end grid					

		
		//主項
		$("#mainItemSelect").change(function(){
			executed = false;
			//中項
			$("#subItemSelect").html("");
			$("<option value=''>-- 請選擇 --</option>").appendTo("#subItemSelect");
			$("#subItemSelect").select2();
			var parentCatId = $("#mainItemSelect").find("option:selected").prop("value");
			
			var data = {
				parentCatId : parentCatId
			};
			if (parentCatId != "" || parentCatId != ''){
				$.ajax({			
					url : CONTEXT_URL+"/basic/cm003/subItem/",		
					data: data,
					type : "POST",
					dataType : 'json',
					success : function(data) {
						if(data.success){
							if(data.rows.length > 0){
								$("#subItemSelect  option").remove();
								$("#subItemSelect").append($("<option></option>").attr("value", "").text("--請選擇--"));
								for(i=0; i < data.rows.length; i++){
									$("#subItemSelect").append("<option value=" + data.rows[i].cat_ID + ">" + data.rows[i].cat_NAME + "</option>");
									$("#subItemSelect").prop("disabled",false);
								}							 
							}
						}
					}
				});
			}				
		});
		//主項
		$("#subItemSelect").change(function(){
			executed = false;
		});
		//query
		$('#btn_search').click(function() {
			var data = {
					mainItemCatId : $.trim($("#mainItemSelect").val()),
					subItemCatId : $.trim($("#subItemSelect").val()),
				};
			$("#grid").setGridParam({datatype:"json", postData:data, mtype: 'POST',page:1}).jqGrid().trigger("reloadGrid");
			executed = true;
		});
		
		// 修改Button
		$("#btn_edit").click(update);
		
		// 新增Button
		$("#btn_add").click(function() {
			editAddPage("add", null, null);
		});
		
		//result
		$("#grid").jqGrid({
					datatype: "local",
					pager: '#pager',
					url : CONTEXT_URL + "/basic/cm003/searchByCat/",
					colNames:['編號','主項','主項編號','中項','項次','小項','工時','單位','單價','啟用'],
					colModel:[
						{name:"item_ID", index:"item_ID", sortable:false, hidden:true},
						{name:'mainItemName',index:'mainItemName',width:150, sortable: false},
						{name:"main_ITEM", index:"main_ITEM", sortable:false, hidden:true},
						{name:'subItemName',index:'subItemName',width:180, sortable: false},
						{name:'item_NO',index:'item_NO',width:40, sortable: false},
						{name:'item_NAME',index:'item_NAME', width:180,sortable: false},
						{name:'unit_HOUR',index:'unit_HOUR', width:40,sortable: false},
						{name:'unit',index:'unit', width:40,sortable: false},
						{name:'price',index:'price', width:80,sortable: false},
						{name:'enabled',index:'enabled', width:30,sortable: false}
					],	
					viewrecords: true,
					caption:"工項清單",
					rownumbers:true,
					multiselect: false,
					onSelectRow: function(id){ 
						//點選欄位後觸發的event
					},
					gridComplete : function() {
						if(jQuery("#grid").jqGrid('getGridParam', 'records') == -1){
							alert('查詢資料超過1000,請改按匯出EXCEL');
						}
					},
					ondblClickRow: function(rowId) { //double clicking a jqgrid row
						var rowData = jQuery(this).getRowData(rowId);
						var whId = null;
			            $.each(rowData, function (i, item) {
			            	if (i == "item_ID") {
			            		item_id = item;
			            	}
			            	if (i == "main_ITEM") {
			            		main_item = item;
			            	}
			            });
			            editAddPage("view", item_id, main_item);
					}
				});//end grid	
		
				// 開啟新增/修改頁面
				function editAddPage(btnType, item_id, main_item) {
					$.fancybox.open({
						href : CONTEXT_URL + "/basic/cm003/addEdit",
						type : "ajax",
				        width : $(window).width(),
				        height : $(window).height(),
						autoSize : false,
						padding : [0, 0, 0, 0],
						scrolling : "yes",
						helpers : {
							overlay : {
								closeClick : false
							},
							title : {
								type : "float"
							}
						},
						title : "表單",
						openEffect : "elastic",
						closeEffect : "fade",
						ajax : {
								data : {
									btn_type : btnType,
									item_id : item_id,
									main_item : main_item
								},
								type : "POST",
								error : function(jqXHR, testStatus, errorThrown) {
									alert(jqXHR);
								},
								async : false
						},
						
						beforeClose : function() {
							if($("#btntype").val() == "edit"){
								if (!confirm("提醒：請確認資料是否存檔，關閉視窗？")) {
									return false;
								}
							}
						},
						afterShow : function() {
							$("#addEdit :input:enabled:visible:first").focus();
						},
						afterClose : function() {
							// fancybox關閉後要做的事(callback function)
							$("#btn_search").trigger("click");
							$("#grid").trigger("reloadGrid");
						}
					});
				}
				
				
		$(window).resize(function() {
			$(window).unbind("onresize");
			$("#grid,.grid").setGridWidth($("#jQgrid").width() - 10);
			$(window).bind("onresize", this);
		}).trigger('resize');
		
		//excel
		$('#btn_to_excel').click(function() {
			if (!executed) {
				alert('請先執行查詢後再匯出EXCEL');
				return;
			}
			var mainItemCatId = $("#mainItemSelect").find("option:selected").prop("value");
			var subItemCatId = $("#subItemSelect").find("option:selected").prop("value");
			
			window.location.href = CONTEXT_URL + "/basic/cm003/getExcel?mainItemCatId=" + mainItemCatId + "&subItemCatId=" + subItemCatId;
		});
		
		//重置按鈕
		$("#btn_reset").click(function() {
			executed = false;
			$("#mainItemSelect").select2("val", "");
			
			$("#subItemSelect").html("");
			$("<option value=''>-- 請選擇 --</option>").appendTo("#subItemSelect");
			$("#subItemSelect").select2();
			
			$("#grid").jqGrid().clearGridData();
		});	
		
		// 執行修改
		function update() {
			// 取得被選取列
			var ids = $("#grid").jqGrid("getGridParam", "selrow");
			// 是否選取
			if (ids == undefined) {
				alert("請選擇一筆資料編輯");
				return false;
			} else {
				var data = $("#grid").jqGrid("getRowData", ids);
				editAddPage("edit", data.item_ID, data.main_ITEM);
			}
		}
		
		// 開啟下拉式選單搜尋文字功能
		function selectDefault() {
			$("#searchForm").find("select").select2();
		}
	</script>
</html>
