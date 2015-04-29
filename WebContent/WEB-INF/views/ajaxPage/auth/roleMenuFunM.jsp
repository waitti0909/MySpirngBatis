<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
<head>
<style>
.selectItemDiv{
	overflow: auto; 
	height: 400px;
	float: left;
}

.butyBanner{
	height:450px;
}
</style>

<title>角色權限設定</title>

</head>
<body>

	<div class="box-header">
		<div class="box-name">
			<i class="fa fa-search"></i> <span>角色權限${suffix}</span>
		</div>
		<div class="box-icons">			 
			<p class="expand-link"></p>
		</div>
		<div class="no-move"></div>
	</div>

	<div class="row">
		<div class="col-xs-12 col-sm-12">
			<div class=" ui-draggable ui-droppable">
				<div class="box-content">
					<form class="form-horizontal" role="form" id="addForm" method="get">
						<input type="hidden" name="checkedMenuIds" id="checkedMenuIds" />
						<div class="form-group">
							<label class="col-sm-2 control-label"> 角色名稱：</label>
							<div class="col-sm-3">
								<select class="populate placeholder" name="roleSelected" id="roleSelected" onchange="makeMenuJsTree(this.value)">
									<option value="">-- 請選擇 --</option>
									<c:forEach items="${allRoles}" var="role">
										<option value="${role.id}">${role.desc}</option>
									</c:forEach>
								</select>
							</div>
							<button class="btn btn-primary btn-label-left" type="button" id="addSaveBtn" disabled="disabled">
								<span><i class="fa fa-save"></i></span> <s:message code="button.save" text="Save" />
							</button>
						</div>
						<div id="menuList" class="" style="width:35%;float: left;">
							<div class="col-xs-12 col-sm-12" style="padding-right:5px;">
								<fieldset class="butyBanner" >
									<legend class="butyLegend">程式清單</legend>
									<div id="menuListDiv" class="col-sm-12 selectItemDiv"></div>
								</fieldset>
<!-- 								<h4 class="page-header">程式清單</h4> -->
<!-- 								<div id="menuListDiv" class="col-sm-12"></div> -->
							</div>
						</div>
						<div id="funList" class="" style="width:20%;float: left;">
							<div class="col-xs-12 col-sm-12" style="padding-right:5px;">
								<fieldset class="butyBanner" >
									<legend class="butyLegend"><input type="checkbox" id="selectAll">全選&nbsp;&nbsp;功能清單</legend>
									<div id="funListDiv" class="col-sm-12 selectItemDiv"></div>
								</fieldset>
<!-- 								<h4 class="page-header">功能清單</h4> -->
<!-- 								<div id="funListDiv" class="col-sm-12"></div> -->
							</div>
						</div>
						<div id="deptList" class="" style="width:45%;float: left;">
							<div class="col-xs-12 col-sm-12">
								<fieldset class="butyBanner" >
									<legend class="butyLegend">部門資料限制</legend>
									<div id="deptListDiv" class="col-sm-12 selectItemDiv">
									<select name="selectedDepts" id='custom-headers' multiple='multiple' ></select>
								</div>
								</fieldset>
<!-- 								<h4 class="page-header">部門資料限制</h4> -->
<!-- 								<div id="deptListDiv" class="col-sm-12"> -->
<%-- 									<select name="selectedDepts" id='custom-headers' multiple='multiple'></select> --%>
<!-- 								</div> -->
							</div>
						</div>	
						
					</form>
				</div>
			</div>
		</div>
	</div>
	<div id="showFileUploadFrame"></div>
	
	<script type="text/javascript">
		var currentMenu = '${menuId}' != '' ? '${menuId}' : 0;
		var firstLoad = true;
		
		function Select2() {
			$("#roleSelected").select2();
		}
		
		$(document).ready(function() {
			LoadSelect2Script('<s:url value="/resources/plugins/select2/select2.min.js" />', Select2);

			var roleId = '${roleId}' != null ? '${roleId}' : "";
			if (roleId != '') {
				$("#roleSelected").val(roleId).change().prop("disabled", true);
			}
			
			/** 存檔 **/
			$('#addSaveBtn').on('click', function(e) {
				// check all required
				if ($("#roleSelected").val() == '') {
					alert('請先選擇角色');
					return false;
				}
				
				saveRoleMenu();
			});	
			
			//全選
			$("#selectAll").on("click", function(){
				if($("#selectAll").prop("checked")){
					$('#funListDiv input[type="checkbox"]').each(function(){
						$(this).prop("checked", true);
					});
				} else {
					$('#funListDiv input[type="checkbox"]').each(function(){
						$(this).prop("checked", false);
					});
				}
			});
			
		});
		
		
		/** 選取角色後動態取程式清單 **/
		function makeMenuJsTree(roleId){
			$('#menuListDiv').jstree("destroy");
			if(roleId == '') {
				$("#addSaveBtn").prop("disabled", true);
				return false;
			} else {
				$("#addSaveBtn").prop("disabled", false);
			}
			$.ajax({
				mimeType : 'text/html; charset=utf-8', // ! Need set mimeType only when run from local file
				type : 'POST',
				url : CONTEXT_URL + "/auth/roleMenu/getTreeByRole",
				dataType : "json",
				async : false,
				data : {
					"roleId" : roleId
				},
				success : function(data) {
					$('#menuListDiv').jstree({ 
						'core' : {
							'multiple' : false,
							'data' : data.rows
						},
						"plugins" : [ "checkbox", "types"],
						"checkbox" : {
							"tie_selection" : false,
							"whole_node" : false,
					    	"keep_selected_style" : true
					    }
					}).bind("loaded.jstree", function (event, data) {	// 節點載入完成後觸發
						var selectedElms = $('#menuListDiv').jstree("get_selected", true);
						$.each(selectedElms, function(i,item) {						
						    $('#menuListDiv').jstree("check_node", "#" + this.id);
						});
						$("#menuListDiv" + " li[id=" + currentMenu + "] a").click();	// 預先選擇(編輯)		
				    }).bind("select_node.jstree", function (e, data) {	// 點選程式節點觸發	
				    	if (firstLoad) {	// 是否為預設載入節點
				    		getAllDepts(currentMenu);
				    		firstLoad = false;							
				    	} else {
							$.each($('#menuListDiv').jstree("get_checked", true), function() {
							    if (currentMenu == this.id) {
							    	saveRoleMenuFun();	// 先儲存功能選單和部門限制結果
							    }
							});				    		
				    	}
						currentMenu = data.instance.get_node(data.selected[0]).id;	// 切換目前選擇MENU位置
						getFunctionBtns(currentMenu);	// 載入MENU設定的Function
						getUsedDeptList(currentMenu);	// 載入MENU設定的DEPT
						
					}).bind("check_node.jstree", function (e, data) {	// 勾選程式節點觸發
						setCheckMenuIds();
					
						
					}).bind("uncheck_node.jstree", function (e, data) { // 取消勾選程式節點觸發
						setCheckMenuIds();
					});
				}
			});	
			roleMenuFunshowSet();
		}
		
		function setCheckMenuIds() {
			var r = [];
			var selectedElms = $('#menuListDiv').jstree("get_checked", true);
			$.each(selectedElms, function() {
			    r.push(this.id);
			});
			$("#checkedMenuIds").val(r.join(','));	// 串接已勾選的所有MENU_ID
		}
		
		/** 選取程式後動態取功能清單 **/
		function getFunctionBtns(menuId) {
			document.getElementById('selectAll').checked=false;
			$("#funListDiv").html('');
			$.ajax({
				mimeType : 'text/html; charset=utf-8', // ! Need set mimeType only when run from local file
				type : 'POST',
				url : CONTEXT_URL + "/auth/menuFun/getFunctions",
				dataType : "json",
				async : false,
				data : {
					"menuId" : menuId
				},
				success : function(data) {
					if(data.msg != 'isFolder') {
						var rows = data.rows;
						var htmlDiv = "";
						for (var row in rows) {
							htmlDiv += "<div class='checkbox'><label>"
							 + "<input name='checkedFunctions' type='checkbox' value='"+rows[row].menu_FUN_ID+"'>"+rows[row].fun_CODE+" <i class='fa fa-square-o'></i>"
							 + "</label></div>";
						}
						if (htmlDiv.length == 0) {
							alert('該程式尚未設定功能清單');
						} else {
							$("#funListDiv").html(htmlDiv);
							// 預設勾選BY ROLE AND MENU
							getCheckedFunctions(menuId);
						}
					}
				}
			});
		}
		
		/** 選取程式後動態取回已設定之功能清單 **/
		function getCheckedFunctions(menuId) {
			$.ajax({
				mimeType : 'text/html; charset=utf-8', // ! Need set mimeType only when run from local file
				type : 'POST',
				url : CONTEXT_URL + "/auth/roleMenuFun/getCheckedFunctions",
				dataType : "json",
				async : false,
				data : {
					"roleId" : $("#roleSelected").val(),
					"menuId" : menuId
				},
				success : function(data) {
					if (data.msg != 'empty') {
						var rows = data.rows;
						for (var row in rows) {
							var menuFunId = rows[row].menuFunId;
							$("input:checkbox[value="+menuFunId+"]").prop("checked", true);
						}
					}
				}
			});
		}
		
		function getAllDepts(menuId) {
			$.ajax({
				mimeType : 'text/html; charset=utf-8', // ! Need set mimeType only when run from local file
				type : 'GET',
				url : CONTEXT_URL + "/auth/roleMenuFunDept/getAllDept",
				dataType : "json",
				async : false,
				success : function(data) {
					$('#custom-headers').empty();
					if (data.rows != null) {
						var rows = data.rows;
						for (var r in rows) {
							$('#custom-headers').append("<option value='"+rows[r].dept_ID+"'>"+rows[r].dept_NAME+"</option>");
						}
						refreshDeptList();
					}
				}
			});
		}
		
		function getUsedDeptList(menuId) {
			$.ajax({
				mimeType : 'text/html; charset=utf-8', // ! Need set mimeType only when run from local file
				type : 'POST',
				url : CONTEXT_URL + "/auth/roleMenuFunDept/getUsedDepts",
				data : {
					"roleId" : $("#roleSelected").val(),
					"menuId" : menuId
				},
				dataType : "json",
				async : false,
				success : function(data) {
					if (data.rows != null) {
						$('#custom-headers').multiSelect('deselect_all');
						var rows = data.rows;
						for (var r in rows) {
							$("#custom-headers").find("option[value='"+rows[r].dept_ID+"']").prop("selected", "selected");
						}
						$("#custom-headers").multiSelect('refresh');
					}
				}
			});
		}
		
		function refreshDeptList() {
			$('#custom-headers').multiSelect({
			  selectableHeader: "<input type='text' class='search-input' autocomplete='off' placeholder='Search'>",
			  selectionHeader: "<input type='text' class='search-input' autocomplete='off' placeholder='Search'>",
			  selectableFooter: "<div class='custom-header'>未選擇</div>",
			  selectionFooter: "<div class='custom-header'>已選擇</div>",
			  keepOrder : true, // 保持項目排序
			  afterInit: function(ms){
			    var that = this,
			        $selectableSearch = that.$selectableUl.prev(),
			        $selectionSearch = that.$selectionUl.prev(),
			        selectableSearchString = '#'+that.$container.attr('id')+' .ms-elem-selectable:not(.ms-selected)',
			        selectionSearchString = '#'+that.$container.attr('id')+' .ms-elem-selection.ms-selected';

			    that.qs1 = $selectableSearch.quicksearch(selectableSearchString)
			    .on('keydown', function(e){
			      if (e.which === 40){
			        that.$selectableUl.focus();
			        return false;
			      }
			    });

			    that.qs2 = $selectionSearch.quicksearch(selectionSearchString)
			    .on('keydown', function(e){
			      if (e.which == 40){
			        that.$selectionUl.focus();
			        return false;
			      }
			    });
			  },
			  afterSelect: function(){
			    this.qs1.cache();
			    this.qs2.cache();
			  },
			  afterDeselect: function(){
			    this.qs1.cache();
			    this.qs2.cache();
			  }
			});
		}
		
		function saveRoleMenu() {
			$("#roleSelected").prop("disabled", false);
			// save role_menu
			$.ajax({
				mimeType : 'text/html; charset=utf-8', // ! Need set mimeType only when run from local file
				type : 'POST',
				url : CONTEXT_URL + "/auth/roleMenu/save/",
				data : $("#addForm").serialize(),
				dataType : "html",
				async : false,
				success : function(data) {
					//parent.$.fancybox.close();
					saveRoleMenuFun();
					alert("存檔成功");
				}
			});
			$("#roleSelected").prop("disabled", true);
		}
		
		function saveRoleMenuFun() {
			$("#roleSelected").prop("disabled", false);
			// save role_menu_function
			$.ajax({
				mimeType : 'text/html; charset=utf-8', // ! Need set mimeType only when run from local file
				type : 'POST',
				url : CONTEXT_URL + "/auth/roleMenuFun/save/",
				data : $("#addForm").serialize() + "&currentMenu=" + currentMenu,
				dataType : "html",
				async : false,
				success : function(data) {
					// Do nothing..
				}
			});
			$("#roleSelected").prop("disabled", true);
		}
		
		function roleMenuFunshowSet(){
			if('${showType}'=='view'){
				//$("#addForm :button").prop("disabled", true);
				$("#addSaveBtn").prop("disabled", true);
			}
		}
	</script>

</body>
</html>