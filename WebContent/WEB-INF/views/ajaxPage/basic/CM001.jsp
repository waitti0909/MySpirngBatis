<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>廠商維護 廠商查詢</title>
<script type="text/javascript">
	$(document).ready(function() {
		
				//查詢按鈕
				$("#btn_search").click(function() {

							var isCkEng = $.trim(document.getElementById("search_typeEng").checked);
							var isCkEqu = $.trim(document.getElementById("search_typeEqu").checked);
							var isCkWh = $.trim(document.getElementById("search_typeWh").checked);
// 							if (isCkEng == "false" && isCkEqu == "false" && isCkWh == "false") {
// 								alert("廠商類別至少勾選一項");
// 								return false;
// 							} else {
								if (isCkEng == "true") {
									typeEng = "Y";
								} else {
									typeEng = "N";
								}
								if (isCkEqu == "true") {
									typeEqu = "Y";
								} else {
									typeEqu = "N";
								}
								if (isCkWh == "true") {
									typeWh = "Y";
								} else {
									typeWh = "N";
								}
// 							}

							var data = {
								search_CoUbnNo : $.trim($("#search_CoUbnNo").val()), //統一編號
								search_CoName : $.trim($("#search_CoName").val()), //廠商名稱
								search_typeEng : typeEng, //工程
								search_typeEqu : typeEqu, //設備
								search_typeWh : typeWh  //倉庫
							};
							
							$.log('data data=' + data);
							$("#grid").setGridParam({datatype : "json",postData : data, page:1}).trigger("reloadGrid");
						});

				//==================================================================

				$("#grid").jqGrid({
					datatype : "local",
					pager : '#pager',
					url : CONTEXT_URL + "/searchCompany/",
					colNames : [ '廠商編號','統一編號', '廠商名稱', '聯絡人', '聯絡人手機', 'E-Mail' ],
					colModel : [ {name : 'co_VAT_NO',index : 'co_VAT_NO',align : 'center',sortable : false,hidden : true}, 
					             {name : 'co_UBN_NO',index : 'co_UBN_NO',width : 60,align : 'center',sortable : false}, 
					             {name : 'co_NAME',index : 'co_NAME',sortable : false}, 
					             {name : 'contact',index : 'contact',width : 60,align : 'center',sortable : false}, 
					             {name : 'con_MOBILE',index : 'con_MOBILE',width : 60,align : 'center',sortable : false}, 
					             {name : 'con_EMAIL',index : 'con_EMAIL',width : 120,align : 'center',sortable : false} 
					             ],
					multiselect : false,
					caption : "廠商清單",
					rownumbers : true,
					onSelectRow : function() {
						// do something when select a row
					},
					gridComplete : function() {
						// do something after grid loaded
					},
					ondblClickRow: function(rowId) { //double clicking a jqgrid row
			            var rowData = jQuery(this).getRowData(rowId);
			            $.each(rowData, function(i,item) {
			            	if(i=="co_VAT_NO"){
			            		editAddCompany("view",item);
			            	}
			            });
			        }
				});//end grid

				$(window).resize(function() {
					$(window).unbind("onresize");
					$("#grid,.grid").setGridWidth($("#jQgrid").width() - 10);
					$(window).bind("onresize", this);
				}).trigger('resize');
				//==================================================================
					
									
				 //新增
			    $('#btn_add').click(function() {					    	
			    	editAddCompany("add");					    	
			    });
				
			    //修改
				$("#btn_edit").click(function() {
					//取得被選取列
					var ids = $("#grid").jqGrid('getGridParam','selrow');
					//是否選取
					if (ids==null) {
						alert("請選擇一筆資料編輯");
						return false;
					}else{
						var company = $("#grid").jqGrid('getRowData',ids);
						editAddCompany("edit",company.co_VAT_NO);
					}			
				});
			    
			    //重置
			    $("#btn_reset").click(function() {					    	
			    	$("#searchFrom").trigger('reset');		
			    	$("#grid").jqGrid().clearGridData();
			    });

			});

//==========FUNCTION==================================================================
	
	function editAddCompany(btnType,coVatNo){
		$.fancybox.open({
			href : CONTEXT_URL + "/cM001_AddEdit/",
			type : 'ajax',
	        width : $(window).width(),
	        height : $(window).height(),
			autoSize : false,
			padding : [ 0, 0, 0, 0 ],
			scrolling : 'yes',
			helpers : {
				overlay : {
					closeClick : false
				},
				title : {
					type : 'float'
				}
			},
			title : "表單",
			openEffect : 'elastic',
			closeEffect : 'fade',
			ajax : {
					data : {
						btnType : btnType,
						coVatNo : coVatNo
					},
					type : "POST",
					error : function(jqXHR, testStatus, errorThrown) {
						alert(jqXHR);
					},
					async : false
			},
			beforeClose : function() {
					
			},
			afterShow : function() {
				$('#companyAddEdit :input:enabled:visible:first').focus();
			},
			afterClose : function() {
				// fancybox關閉後要做的事(callback function)
				if(btnType=="add" || btnType=="edit"){
					$("#btn_search").trigger("click");
					$("#grid").trigger("reloadGrid");
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
						<i class="fa fa-search"></i> <span>廠商維護 廠商查詢</span>
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
						id="searchFrom" novalidate="novalidate">
						<div class="form-group">
							<label class="col-sm-2 control-label">統一編號</label>
							<div class="col-sm-3">
								<input id="search_CoUbnNo" type="text" name="search_CoUbnNo">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">廠商名稱</label>
							<div class="col-sm-3">
								<input id="search_CoName" type="text" name="search_CoName" placeholder="關鍵字查詢">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">廠商類別</label>
							<div class="col-sm-3">
								<div class="checkbox-inline">
									<label> <input id="search_typeEng" type="checkbox"
										checked> 工程 <i class="fa fa-square-o"></i>
									</label>
								</div>
								<div class="checkbox-inline">
									<label> <input id="search_typeEqu" type="checkbox"
										checked> 設備 <i class="fa fa-square-o"></i>
									</label>
								</div>
								<div class="checkbox-inline">
									<label> <input id="search_typeWh" type="checkbox"
										checked> 倉庫 <i class="fa fa-square-o"></i>
									</label>
								</div>
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
</html>