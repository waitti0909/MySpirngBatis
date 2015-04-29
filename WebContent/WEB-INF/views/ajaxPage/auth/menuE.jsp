<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<title>系統清單選單設定</title>
		<style type="text/css">
			font{
				color :red;
			}
		</style>
		<script type="text/javascript">
			$().ready(function(){
				showSet('${showType}');
				selectCheck();
				$("#parent_ID").select2().val("${menu.PARENT_ID}").change();
				$("#is_FODR").select2().val("${menu.IS_FODR}").change();
				$("#cleanerButton").click(function(){
					selectCheck();
					$("#menu_NAME").val("${menu.MENU_NAME}");
					$("#menu_DESC").val("${menu.MENU_DESC}");
					$("#pgm_PATH").val("${menu.PGM_PATH}");
					$("#parent_ID").val("${menu.PARENT_ID}").change();
					$("#is_FODR").val("${menu.IS_FODR}").change();
					$("#menu_SORT").val("${menu.MENU_SORT}"); 
					$('#nameIsEmpty').remove();
 					$('#nameDataError').remove();
 					$('#descIsEmpty').remove();
 					$('#urlIsEmpty').remove();
 					$('#urlDataError').remove();
 					$('#sortIsEmpty').remove();
 					$('#sortDataError').remove();
 					$('#updateListParentIdDataError').remove();
 					$('#isFolderDataError').remove();
 					
 					
				});
				$("#addSaveBtn").click(function(){
					var errorMessages = new Array();
					var is_FODR = $("#is_FODR").val();
					var parent_ID = $("#parent_ID").val();
					var pgm_PATH = $("#pgm_PATH").val();
					var menu_SORT = $("#menu_SORT").val();
					var menu_NAME = $("#menu_NAME").val();
					if(menu_NAME=="" ){
						$('#nameDataError').remove();
						if ($('#nameIsEmpty').val() == null) {
							$("<font id='nameIsEmpty'>請輸入清單名稱</font>").appendTo("#nameMessage");
		 				}
						errorMessages[errorMessages.length] = 'nameIsEmpty';
	// 				}else if(menu_NAME.match("[^a-zA-Z_0-9]")){
	// 					$('#nameIsEmpty').remove();
	// 					if ($('#nameDataError').val() == null) {
	// 						$("<font id='nameDataError' color='red'>開頭不可為特殊字元</font>").appendTo("#nameMessage");
	// 	 				}
	// 					errorMessages[errorMessages.length] = 'urlDataError';
					}else{
	 					$('#nameIsEmpty').remove();
	 					$('#nameDataError').remove();
		 			}
					if($("#menu_DESC").val()==""){
						if ($('#descIsEmpty').val() == null) {
		 					$("<font id='descIsEmpty'>請輸入清單說明</font>").appendTo("#descMessage");
		 				}
						errorMessages[errorMessages.length] = 'descIsEmpty';
					}else{
	 					$('#descIsEmpty').remove();
		 			}
					
					if(is_FODR==0){
						if(pgm_PATH==""){
							$('#urlDataError').remove();
							$('#urlDataNotEmpty').remove();
							if ($('#urlIsEmpty').val() == null) {
								$("<font id='urlIsEmpty' >請輸入程式路徑</font>").appendTo("#urlMessage");
			 				}
							errorMessages[errorMessages.length] = 'urlIsEmpty';
						}else if(pgm_PATH.match("[^a-zA-Z0-9_/.]")){
							$('#urlIsEmpty').remove();
							$('#urlDataNotEmpty').remove();
							if ($('#urlDataError').val() == null) {
								$("<font id='urlDataError'>不可有特殊字元</font>").appendTo("#urlMessage");
			 				}
							errorMessages[errorMessages.length] = 'urlDataError';
						}else{
		 					$('#urlIsEmpty').remove();
		 					$('#urlDataError').remove();
		 					$('#urlDataNotEmpty').remove();
			 			}
					}else if(pgm_PATH!=""){
						$('#urlIsEmpty').remove();
	 					$('#urlDataError').remove();
						if ($('#urlDataNotEmpty').val() == null) {
							$("<font id='urlDataNotEmpty'>無繼承不可填路徑</font>").appendTo("#urlMessage");
						}
						errorMessages[errorMessages.length] = 'urlDataNotEmpty';
					}else{
						$('#urlIsEmpty').remove();
	 					$('#urlDataError').remove();
	 					$('#urlDataNotEmpty').remove();
					}
					
					if(menu_SORT==""){
						$('#sortDataError').remove();
						if ($('#sortIsEmpty').val() == null) {
							$("<font id='sortIsEmpty'>請輸入清單排序</font>").appendTo("#sortMessage");
		 				}
						errorMessages[errorMessages.length] = 'sortIsEmpty';
					}else if(menu_SORT.match("[^0-9]")){
						$('#sortIsEmpty').remove();
						if ($('#sortDataError').val() == null) {
							$("<font id='sortDataError'>請輸入整數</font>").appendTo("#sortMessage");
		 				}
						errorMessages[errorMessages.length] = 'sortDataError';
					}else{
	 					$('#sortIsEmpty').remove();
	 					$('#sortDataError').remove();
		 			}
					
					if(parent_ID=="" && is_FODR==0){
						if ($('#updateListParentIdDataError').val() == null) {
							$("<font id='updateListParentIdDataError'>無繼承時必須是資料夾</font>").appendTo("#updateListParentIdMessage");
		 				}
						errorMessages[errorMessages.length] = 'updateListParentIdDataError';
					}else{
	 					$('#updateListParentIdDataError').remove();
		 			}
					
					if(parent_ID=="" && is_FODR==0){
						if ($('#isFolderDataError').val() == null) {
							$("<font id='isFolderDataError'>無繼承時必須是資料夾</font>").appendTo("#isFolderMessage");
		 				}
						errorMessages[errorMessages.length] = 'isFolderDataError';
					}else{
						$('#isFolderDataError').remove();
		 			}
					if(errorMessages.length!=0){
						return false;
					}
					
					var lookupButtons = new Array();
					$('input:checkbox:checked[name="lookupButton"]').each(function(i) {lookupButtons[i]= this.value ; });
					$.ajax({
						url : "<s:url value='/auth/systemMenu/updateSuccess'/>",
						type : 'POST',
						async: false,
						data : $("#updateForm").serialize()+"&lookupButtons="+lookupButtons+"&menu_ID="+$("#menu_ID").val(),
						success : function(data){
							if(data.success){
								alert('<s:message code="msg.update.success"/>');
								$.fancybox.close();
							}else{
								alert('<s:message code="msg.update.fail"/>：' + data.msg);
							}							
						}					
	 	       		});
				});
			});
			function checkRole(){
				if($("#is_FODR").val()==1){
					alert("資料夾不需設定按鈕");
					return false;
				}else{
					return true;	
				}
			}
			function selectCheck(){
				var funCode = "${funCode}";
				var funCodes = funCode.split(",");
				for(var funCodeResult in funCodes){
					if(funCodes[funCodeResult]!=""){
						var button = funCodes[funCodeResult];
						$("input:checkbox[value="+button+"]").attr("checked", true);
					}
				}
			}
			function unselAll(){
				if($("#is_FODR").val()==1){
 					$("input[name=lookupButton]").attr("checked",false);
				}
			}
			function showSet(showType){
				if(showType=='view'){
					$("#updateForm :input").prop("disabled", true);
				}
			}
		</script>
	</head>
	<body>
		<div id="breadcrumb" class="col-md-12"></div>
		<div class="col-xs-12 col-sm-12" >
			<div class="box ui-draggable ui-droppable">
				<div class="box-content" >
					<h4 class="page-header" id="updateConditionText">修改條件</h4>
					<form class="form-horizontal" role="form" id="updateForm">
						<div class="form-group" >
							<!--  系統功能  -->
<!-- 							<div id="update_List"> -->
								<label class="col-sm-2 control-label" ><span class="s">* </span>清單名稱:</label>
								<div class="col-sm-2">
									<input id="menu_NAME" name="MENU_NAME" type="text" class="form-control" placeholder="清單名稱" value="${menu.MENU_NAME}"></input>
								</div>
								<label class="col-sm-2 control-label" id="nameMessage"></label>
								<label class="col-sm-2 control-label" ><span class="s">* </span>清單說明:</label>
								<div class="col-sm-2">
									<input id="menu_DESC" name="MENU_DESC" type="text" class="form-control" placeholder="清單說明" value="${menu.MENU_DESC}"></input>
								</div>
								<label class="col-sm-2 control-label" id="descMessage"></label>
								
								<div class="clearfix">&nbsp;</div>
								<div class="clearfix">&nbsp;</div>
								
								<label class="col-sm-2 control-label" ><span class="s">* </span>繼承編號:</label>
								<div class="col-sm-2">
									<select class="populate placeholder" name="PARENT_ID"
										id="parent_ID" >
										<option value="">無</option>
										<c:forEach items="${allMenus}" var="allMenu">
											<option value="${allMenu.MENU_ID}">${allMenu.MENU_NAME}</option>
										</c:forEach>
									</select>
								</div>
								<label class="col-sm-2 control-label" id="updateListParentIdMessage"></label>
								<label class="col-sm-2 control-label" ><span class="s">* </span>清單排序:</label>
								<div class="col-sm-2">
								<input id="menu_SORT" name="MENU_SORT" type="text" class="form-control" placeholder="清單排序" value="${menu.MENU_SORT}"></input>
								</div>
								<label class="col-sm-2 control-label" id="sortMessage"></label>
								
								<div class="clearfix">&nbsp;</div>
								<div class="clearfix">&nbsp;</div>
								
								<label class="col-sm-2 control-label" ><span class="s">* </span>程式路徑:</label>
								<div class="col-sm-2">
									<input id="pgm_PATH" name="PGM_PATH" type="text" class="form-control" placeholder="程式路徑" value="${menu.PGM_PATH}"></input>
								</div>
								<label class="col-sm-2 control-label" id="urlMessage"></label>
								<label class="col-sm-2 control-label" ><span class="s">* </span>資料夾:</label>
								<div class="col-sm-2">
									<select class="populate placeholder" name="IS_FODR"
											id="is_FODR" onclick="unselAll();"> 
										<option value="1">是</option>
										<option value="0">否</option>
									</select> 
								</div>
								
								<label class="col-sm-2 control-label" id="isFolderMessage"></label>
								<input type="hidden" id="menu_ID" name="MENU_ID"  value="${menu.MENU_ID}" />
			
<!-- 							</div> -->
							<!--  系統功能 end -->
						</div>
						<div class="row form-group">
							<div class="col-sm-12">
							<label class="col-sm-2 control-label" ></label>
								<c:forEach items="${lookupButtons}" var="lookupButton"> 
									<div class="checkbox-inline">
										<label>
											<input id="${lookupButton.CODE}" onclick="return checkRole();" name="lookupButton" type="checkbox" value="${lookupButton.CODE}">${lookupButton.VALUE1}
											<i class="fa fa-square-o"></i>
										</label>
									</div>
								</c:forEach>
							</div>
						</div>
						<div id="button">
							<div class="form-group">
								<div class="col-sm-offset-4 col-sm-2">
									<button class="btn btn-primary btn-label-left" type="button" id="addSaveBtn" >
										<span><i class="fa fa-save"></i></span>
										<s:message code="button.save" text="Save" />
									</button>
								</div>
								<div class="col-sm-2">
									<button class="btn btn-default btn-label-left" type="button" id="cleanerButton">
										<span><i class="fa fa-undo txt-danger"></i></span>
										<s:message code="button.reset" text="Reset" />
									</button>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</body>
</html>