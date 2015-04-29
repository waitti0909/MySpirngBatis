<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<
<!DOCTYPE html>
<html lang="en">
<head>
<title>角色維護</title>
<script type="text/javascript">
	// Run Select2 on element
	function Select3Test() {
		$("#RoleSelect").select2();
	}
	$(document).ready(function() {
						// Load script of Select2 and run this	
						LoadSelect2Script(
								'<s:url value="/resources/plugins/select2/select2.min.js" />',
								Select3Test);
						//LoadBootstrapValidatorScript('<s:url value="/resources/plugins/bootstrapvalidator/bootstrapValidator.min.js" />',roleFormValidator);
				        
						//檢視話面設定
						roleMastShowSet($('#showType').val());
						
						var original_roledesc=$("#roledesc").val(); //原始角色說明值
				        var isChange = 0;
				        //左右選單是否更改過
				        $('#allUserSelect').bind('change', function() {
				        	isChange = 1;
				        });
						
				        //儲存按鈕
						$("#userRoleSaveBtn").click(function() {
							//roleFormValidator();
											//取得已選取的值
											if($('select#allUserSelect').val()==null){
												var userId ='0';
											}else{
												userId = $('select#allUserSelect').val();
											}																				
											//當DB中未有已選取的使用者 以及 頁面未設定任何使用者  以及 角色說明未修改
											if ($("#UserSelected").val()==null && userId == '0' && (original_roledesc==$("#roledesc").val())) {
												alert("未更改任何資料");
											}
											//當左右選單未更改時 以及 角色說明未更改
											else if(isChange==0 && (original_roledesc==$("#roledesc").val())){
												alert("未更改任何資料");
											}else {
												if (confirm("確定儲存?")) {
													//未更改角色說明，DB不需要更改
													if(original_roledesc==$("#roledesc").val()){
														roleUserSave('noedit',userId);
													}else{
														roleUserSave($("#roledesc").val(),userId);
													}												
												} else {
													//parent.$.fancybox.close();
												}
											}
										});
						
				        //重置按鈕
						$("#userRoleCancelBtn").click(function() {
							$('#allUserSelect').multiSelect('deselect_all');
							loadUserSelected();
						});
						
						
						//使用者清單標頭
						$('#allUserSelect').multiSelect({
							keepOrder : true, // 保持項目排序
							selectableFooter : "<div class='custom-header'>未選取之使用者</div>",
							selectionFooter : "<div class='custom-header'>已選取之使用者</div>",											
							selectableHeader: "<input type='text' class='search-input' autocomplete='off' placeholder='try \"12\"'>",
							selectionHeader: "<input type='text' class='search-input' autocomplete='off' placeholder='try \"4\"'>",
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
						//全選按鈕
						$('#selectAll').click(function() {
							$('#allUserSelect').multiSelect('select_all');
							return false;
						});
						
						//全取消按鈕
						$('#deSelectAll').click(function() {
							$('#allUserSelect').multiSelect('deselect_all');
							return false;
						});
						
                        //預先載入已選取之user
						loadUserSelected();
                        
						
					});

//**************FUNCTION**************

	//預先載入已選取之user
	function loadUserSelected() {
		$("#UserSelected > option").each(function() {
			// 預先載入選擇
			$("#allUserSelect").find("option[value='"+this.value+"']").prop("selected", "selected");
			$("#allUserSelect").multiSelect('refresh');			
		});
	}
	
	//儲存執行
	function roleUserSave(editroledesc,userId){
		$.ajax({
			url : '<s:url value="/auth/userRoleSave/" />',
			data : {
				roleID : $("#roleid").val(),
				roleDESC : editroledesc,
				userIDArr : userId.toString()
			},
			type : "POST",
			success : function(data) {
				//針對下拉選單做修改
				$.each(data, function(i, item) {
					var option_text = item.roleName + "-" + item.roleDesc;
					$("#s2_RoleSelect > option").each(function() {
						if (this.value == item.roleId) {
							$("#s2_RoleSelect").select2("data", {
								id : item.roleId,
								text : option_text
							});
							this.text = option_text;
						}
					});
				});
			},
			complete : function() {
				parent.$.fancybox.close();
				$("#btn_search").trigger('click');
				$("#grid").trigger("reloadGrid");
			},
			error : function(
					jqXHR,
					testStatus,
					errorThrown) {
				alert(jqXHR);
				alert(testStatus);
				alert(errorThrown);
			}
		});
	}

	//檢視畫面設定
	function roleMastShowSet(btnType){
		if(btnType=="view"){
			$("#userRoleFrom :input").prop("disabled", true);
		}		
	}
</script>
<style>
.ms-container {
	width: 90%;
}

.ms-container:after {
	content: ".";
	display: block;
	height: 0;
	line-height: 0;
	font-size: 0;
	clear: both;
	min-height: 0;
	visibility: hidden;
}

.ms-container .ms-selectable,.ms-container .ms-selection {
	background: #fff;
	color: #555555;
	float: left;
	width: 30%;
}

.ms-container .ms-selection {
	float: right;
}

.ms-container .ms-list {
	-webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
	-moz-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
	box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
	-webkit-transition: border linear 0.2s, box-shadow linear 0.2s;
	-moz-transition: border linear 0.2s, box-shadow linear 0.2s;
	-ms-transition: border linear 0.2s, box-shadow linear 0.2s;
	-o-transition: border linear 0.2s, box-shadow linear 0.2s;
	transition: border linear 0.2s, box-shadow linear 0.2s;
	border: 1px solid #ccc;
	-webkit-border-radius: 3px;
	-moz-border-radius: 3px;
	border-radius: 3px;
	position: relative;
	height: 200px;
	padding: 0;
	overflow-y: auto;
}

.ms-container .ms-list.ms-focus {
	border-color: rgba(82, 168, 236, 0.8);
	-webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075), 0 0 8px
		rgba(82, 168, 236, 0.6);
	-moz-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075), 0 0 8px
		rgba(82, 168, 236, 0.6);
	box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075), 0 0 8px
		rgba(82, 168, 236, 0.6);
	outline: 0;
	outline: thin dotted \9;
}

.ms-container ul {
	margin: 0;
	list-style-type: none;
	padding: 0;
}

.ms-container .ms-optgroup-container {
	width: 100%;
}

.ms-container .ms-optgroup-label {
	margin: 0;
	padding: 5px 0px 0px 5px;
	cursor: pointer;
	color: #999;
}

.ms-container .ms-selectable li.ms-elem-selectable,.ms-container .ms-selection li.ms-elem-selection
	{
	border-bottom: 1px #eee solid;
	padding: 2px 10px;
	color: #555;
	font-size: 14px;
}

.ms-container .ms-selectable li.ms-hover,.ms-container .ms-selection li.ms-hover
	{
	cursor: pointer;
	color: #fff;
	text-decoration: none;
	background-color: #08c;
}

.ms-container .ms-selectable li.disabled,.ms-container .ms-selection li.disabled
	{
	background-color: #eee;
	color: #aaa;
	cursor: text;
}

.custom-header {
	text-align: center;
	padding: 3px;
	background: #000;
	color: #fff;
}
</style>
</head>
<body>

	<!-- 彈出新增表單 -->
	<div class="box-header">
		<div class="box-name">
			<i class="fa fa-edit"></i> <span>角色包含之使用者編輯</span>
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
				<input id="showType" type="hidden" value="${showType}">
					<form class="form-horizontal bootstrap-validator-form" id="userRoleFrom" novalidate="novalidate">
								<c:forEach items="${role}" var="role">
								<input id="roleid" type="hidden" name="country" value="${role.roleId}" />
								<div class="form-group">
								<label class="col-sm-4 control-label">系統角色 : </label>							
								<div class="col-sm-3">
								<input id="rolename" type="text" class="form-control" readonly="readonly" name="rolename" value="${role.roleName}">
								</div>
								</div>
								<div class="form-group">
								<label class="col-sm-4 control-label">系統角色說明 : </label>
								<div class="col-sm-5">
								<input id="roledesc" type="text" class="form-control" name="roledesc" value="${role.roleDesc}" maxlength="250">
								</div>
								</div>
								</c:forEach>					
						<div class="clearfix">&nbsp;</div>
						<div class="form-group">
							<div class="col-sm-offset-3 col-sm-4">
								<button class="btn btn-primary btn-label-left" type="button"
									id="userRoleSaveBtn">
									<span><i class="fa fa-save"></i></span>
									<s:message code="button.save" text="儲存" />
								</button>
							</div>
							<div class="col-sm-2">
								<button class="btn btn-default btn-label-left" type="button"
									id="userRoleCancelBtn">
									<span><i class="fa fa-undo txt-danger"></i></span>
									<s:message code="button.reset" text="重置" />
								</button>
							</div>
						</div>
					

            <h4 class="page-header"></h4>

			<div class="col-sm-offset-4 col-sm-2">
				<button class="btn btn-primary btn-label-left" type="button"
					id="selectAll">
					<span><i class="fa fa-plus"></i></span>全選
				</button>
			</div>
			<div class="col-sm-2">
				<button class="btn btn-primary btn-label-left" type="button"
					id="deSelectAll">
					<span><i class="fa fa-times txt-danger"></i></span>取消全選
				</button>
			</div>
			<div class="clearfix">&nbsp;</div>

			<div class="col-sm-offset-1">
				<select multiple="multiple" id="allUserSelect" name="selectDemo" class="searchable">
					<c:forEach items="${allPsn}" var="user">
						<option value="${user.PSN_ID}">${user.ENG_NM}</option>  
                    </c:forEach>
				</select>
			</div>
			</form>
		</div>
	</div>
	</div>
	</div>
	<!-- 隱藏已選取的user -->
	<select name="userID" id="UserSelected" style=" display:none">
	<c:forEach items="${getUserByRoleId}" var="seuser">
	<option value="${seuser.PSN_ID}">${seuser.PSN_ID}</option>
    </c:forEach>
	</select>
	<!-- 隱藏已選取的user -->
</body>
</html>