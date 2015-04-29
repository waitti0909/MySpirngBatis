<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>角色執行選單設定</title>
</head>
<body>
	<div class="row">
		<div id="breadcrumb" class="col-md-12">
			<ol class="breadcrumb">
<!-- 				<li><a href="#">Auth</a></li> -->
<!-- 				<li><a href="#">角色功能</a></li> -->
				<c:import url="/auth/role/menu/func/${currentMenuId}" />
			</ol>
		</div>
	</div>

	<div class="row">
		<div class="col-xs-12 col-sm-12">
			<div class="box ui-draggable ui-droppable">
				<div class="box-header">
					<div class="box-name">
						<i class="fa fa-search"></i> <span>角色執行選單查詢</span>
					</div>
					<div class="box-icons">
						<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
						</a> <a class="expand-link"> <i class="fa fa-expand"></i>
						</a>
					</div>
					<div class="no-move"></div>
				</div>

				<div class="box-content">
					<form class="form-horizontal" role="form" id="searchForm">
						<div class="form-group">
							<label class="col-sm-2 control-label"><input type="radio"
								name="searchBy" id="searchByRole" checked value="byRole"> 系統角色</label>
							<div class="col-sm-3">
								<select class="populate placeholder" name="roleId" id="s2_RoleSelect">
									<option value="">-- 請選擇 --</option>
									<c:forEach items="${allRoles}" var="role">
										<option value="${role.id}">${role.desc}</option>
									</c:forEach>
								</select>
							</div>

							<label class="col-sm-2 control-label"><input type="radio"
								id="searchByMenu" name="searchBy" value="byMenu"> 系統功能</label>
							<div class="col-sm-3">
								<select class="populate placeholder" name="menuId" id="s2_MenuSelect" disabled="disabled">
									<option value="">-- 請選擇 --</option>
 									<c:forEach items="${allMenus}" var="menu">
 										<option value="${menu.id}">${menu.name}</option>
									</c:forEach> 
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
				<div id="pager"></div><p>
				
				<div id="detailList" style="display:none">
<!-- 					<table id="gridD"></table> -->
<!-- 					<div id="pagerD"></div> -->
					<input type="hidden" name="headerId_s" id="headerId_s" value="" />
					<input type="hidden" name="menuId_s" id="menuId_s" value="" />
				</div>
			</div>
			<!-- 固定id for window.resize end-->

		</div>
	</div>
	
	<script type="text/javascript">
		// Run Select2 on element
		function Select2Test() {
			$("#s2_RoleSelect").select2();
			$("#s2_MenuSelect").select2();
		}

		$(document)
				.ready(
						function() {
							// Load script of Select2 and run this	
							LoadSelect2Script(
									'<s:url value="/resources/plugins/select2/select2.min.js" />',
									Select2Test);
							LoadBootstrapValidatorScript(
									'<s:url value="/resources/plugins/bootstrapvalidator/bootstrapValidator.min.js" />',
									searchFormValidator);

							//WinMove();

							$("input[name=searchBy]:radio").click(
									function() {

										if ($(this).val() == 'byRole') {
											$("#s2_MenuSelect").select2("val",
													"");
											$("#s2_RoleSelect").prop(
													"disabled", false);
											$("#s2_MenuSelect").prop(
													"disabled", true);
										} else {
											$("#s2_RoleSelect").select2("val",
													"");
											$("#s2_RoleSelect").prop(
													"disabled", true);
											$("#s2_MenuSelect").prop(
													"disabled", false);

										}

									});
							//==================================================================
							$("#grid").jqGrid({
								datatype : "local",
								pager : '#pager',
								url : CONTEXT_URL + "/auth/roleMenu/searchResult/",		// search url
								colNames : [/*'功能',*/ '角色名稱', '程式名稱', 'roleId', 'menuId' ],
								colModel : [
// 								   {name : 'action', index : 'action', sortable : false, width : 20, align : 'center'}, 
								   {name : 'roleDesc', index : 'roleDesc', sortable : false}, 
								   {name : 'menuName', index : 'menuName', sortable : false}, 
								   {name : 'roleId', index : 'roleId', sortable : false, hidden : true}, 
								   {name : 'menuId', index : 'menuId', sortable : false, hidden : true}
								],
								caption : "角色程式列表",
								multiselect : true,		// 多選checkbox
								onSelectRow : function(ids) {
									var roleId = $("#grid").jqGrid('getRowData', ids).roleId;
									var menuId = $("#grid").jqGrid('getRowData', ids).menuId;
									var roleDesc = $("#grid").jqGrid('getRowData', ids).roleDesc;
									var menuName = $("#grid").jqGrid('getRowData', ids).menuName;
// 									$("#gridD").jqGrid('setGridParam',{datatype : "json", postData:{roleId : roleId, menuId : menuId}, page:1}).jqGrid("setCaption", roleDesc + " - " + menuName);
// 									$("#gridD").trigger('reloadGrid');
// 									$("#detailList").show();
								},							               	
								gridComplete : function() {
									// do something after grid loaded
									var ids = $("#grid").jqGrid('getDataIDs');
									for(var i=0;i < ids.length;i++){
										var cl = ids[i];  // cl:jqgrid的行數，非資料真正的HEADER_ID
										var roleId = $("#grid").jqGrid('getRowData', cl).roleId;
										var menuId = $("#grid").jqGrid('getRowData', cl).menuId;
										be = "<input type='button' value='設定' name='edit_'"+roleId+" id='edit_'"+roleId+" onclick='goSetFunctions("+roleId+", "+menuId+")'/>";
// 										$("#grid").jqGrid('setRowData', ids[i], {action:be});
									}
								},
								ondblClickRow: function(rowId) { //double clicking a jqgrid row
// 						            var rowData = jQuery(this).getRowData(rowId);
// 								    var roleIdClick=null;
// 								    var menuIdClick=null;
// 						            $.each(rowData, function(i,item) {
// 						            	if(i=="roleId"){
// 						            		roleIdClick = item;
// 						            	}
// 						            	if(i=="menuId"){
// 						            		menuIdClick = item;
// 						            	}
// 						            });
// 						            showEditorView(roleIdClick,menuIdClick,"view");
						        }
							});//end grid
							
// 							$("#gridD").jqGrid({
// 								datatype : "local",
// 								pager : '#pagerD',
// 								url : CONTEXT_URL + "/auth/roleMenuFun/searchFunDept/",		// search url
// 								colNames : [ '功能名稱', '部門別', 'deptId', 'menuId' ],
// 								colModel : [
// 								   {name : 'funName', index : 'funName', sortable : false}, 
// 								   {name : 'depName', index : 'depName', sortable : false}, 
// 								   {name : 'funCode', index : 'funCode', sortable : false, hidden : true}, 
// 								   {name : 'depId', index : 'depId', sortable : false, hidden : true}
// 								],
// 								gridComplete : function() {
// 									// do something after grid loaded
// 								}
// 							});//end grid

							$(window).resize(function() {
								$(window).unbind("onresize");
								$("#grid,.grid").setGridWidth($("#jQgrid").width() - 10);
// 								$("#gridD,.grid").setGridWidth($("#jQgrid").width() - 10);
								$(window).bind("onresize", this);
							}).trigger('resize');
							//==================================================================
								
							$("#btn_search").click(function() {												
								$("#detailList").hide();
// 								$("#gridD").jqGrid('clearGridData');
								
								var data = {
									searchBy : $.trim($('input[name=searchBy]:checked').val()),
									roleId : $.trim($("#s2_RoleSelect").val()),
									menuId : $.trim($("#s2_MenuSelect").val())
								};
								$("#grid").setGridParam({datatype : "json",	postData : data, page:1	}).trigger("reloadGrid");	
							});
							
							$("#btn_add").click(function() {
								var roleId = 0;
								if ($("#searchByRole").prop("checked") && $("#s2_RoleSelect").val() != '') {
// 									alert("請選擇角色");
// 									return false;
									roleId = $("#s2_RoleSelect").val();
								}

// 								if ($("#searchByMenu").prop("checked") && $("#s2_MenuSelect").val() == '') {
// 									alert("Please select Menu");
// 									return false;
// 								}

								$.fancybox.open({
									href : CONTEXT_URL + "/auth/roleMenuFun/add/init",//CONTEXT_URL + "/auth/roleMenu/modify/init",
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
									title : "新增",
									openEffect : 'elastic', // fancy 開啟與關閉動畫
									closeEffect : 'fade',
									ajax : {
										// 搭配ajax傳送參數，也可直接加在URL後面
										type : "post",
										data : {
											"roleId" : roleId
										}
									},
									beforeClose : function() {
										if (!confirm('提醒：請確認資料是否存檔，關閉視窗？')) {
											return false;
										}
									},
									afterClose : function() {
										// fancybox關閉後要做的事(callback function)
										$("#grid").trigger("reloadGrid");
									},
									afterShow:function(){
										$('#addForm :input:enabled:visible:first').focus();
									}
								});

							});

							$("#btn_edit").click(function() {
								//取得被選取列
								var ids = $("#grid").jqGrid('getGridParam','selarrrow');
								var roleId = "", menuId = "";
								//是否選取
								if (ids.length != 1) {
									alert("請選擇一筆資料編輯");
									return false;
								} else {
									roleId = $("#grid").jqGrid('getRowData', ids).roleId;
									menuId = $("#grid").jqGrid('getRowData', ids).menuId;
								}
								showEditorView(roleId,menuId,"edit");
							});
							
							$("#btn_delete").click(function() {
								//取得被選取列
								var ids = $("#grid").jqGrid('getGridParam','selarrrow');
								//是否選取
								if (ids.length == 0) {
									alert("請勾選欲刪除項目");
									return false;
								}
								var roleId = [];
								var menuId = [];
								if (confirm("確定刪除？")) {										
									for (var i=0; i<ids.length; i++) {
										var cl = ids[i]; // cl:jqgrid的行數，非資料真正的HEADER_ID
										var row = $("#grid").jqGrid('getRowData', cl);	// 該筆資料model
										roleId.push(row.roleId);
										menuId.push(row.menuId);										
									}
									
									$.ajax({
										url: '<s:url value="/auth/roleMenu/delete" />',
										data: {
											"roleId": roleId.toString(),
											"menuId": menuId.toString()
										},
										type : "GET",
										datatype : "html",
										success: function(data) {
											alert("刪除成功");
											$("#grid").trigger("reloadGrid");
										}
									});
									
								}
							});
							
							$("#btn_reset").click(function() {
								$("#searchForm").trigger('reset');
								Select2Test();
							});

						}

				);

		function searchFormValidator() {
			$('#searchForm').bootstrapValidator({
				message : 'This value is not valid',
				fields : {
					s2_RoleSelect : {
						message : 'The username is not valid',
						validators : {
							message : 'Role is required and can\'t be empty',
							callback : function(value, validator) {
								alert(value);
								$("input[name=searchBy]:radio").each(function() {
									if ($(this)
											.val() == 'byRole'
											&& value == '') {
										return false;
									}
								});
								return true;
							}
						}
					}
				}
			});
		}

		function goSetFunctions(roleId, menuId) {
			$.fancybox.open({
				href : CONTEXT_URL + "/auth/roleMenuFun/edit/init",
				type : 'ajax',
				width : $(window).width(),
		        height : $(window).height(),
				autoSize : false,
				padding : [ 0, 0, 0, 0 ],
				helpers : {
					overlay : {closeClick : false},
					title : {type : 'float'}
				},
				title : "設定",
				openEffect : 'elastic', // fancy 開啟與關閉動畫
				closeEffect : 'fade',
				ajax : {
					// 搭配ajax傳送參數，也可直接加在URL後面
					type : "post",
					data : {
						"roleId" : roleId,
						"menuId" : menuId
					}
				},
				afterClose : function() {
					// fancybox關閉後要做的事(callback function)
// 					$("#gridD").trigger("reloadGrid");
				},
				afterShow:function(){
					//$('#addForm :input:enabled:visible:first').focus();
				}
				
			});
		}
		function showEditorView(roleId,menuId,btnType){
			$.fancybox.open({
				href : CONTEXT_URL + "/auth/roleMenuFun/edit/init",//CONTEXT_URL + "/auth/roleMenu/modify/init",
				type : 'ajax',
				width : $(window).width(),
		        height : $(window).height(),
				autoSize : false,
				padding : [ 0, 0, 0, 0 ],
				scrolling : 'no',
				helpers : {
					overlay : {closeClick : false},
					title : {type : 'float'}
				},
				title : "編輯",
				openEffect : 'elastic', // fancy 開啟與關閉動畫
				closeEffect : 'fade',
				ajax : {
					// 搭配ajax傳送參數，也可直接加在URL後面
					type : "post",
					data : {
						"btnType" : btnType,
						"roleId" : roleId,//$("#s2_RoleSelect").val(),
						"menuId" : menuId
					}
				},
				beforeClose : function() {
					if (!confirm('提醒：請確認資料是否存檔，關閉視窗？')) {
						return false;
					}
				},
				afterClose : function() {
					// fancybox關閉後要做的事(callback function)
					$("#grid").trigger("reloadGrid");
//						$("#gridD").trigger("reloadGrid");
				},
				afterShow:function(){
					$('#addForm :input:enabled:visible:first').focus();
				}
				
			});
		}
	</script>

</body>
</html>