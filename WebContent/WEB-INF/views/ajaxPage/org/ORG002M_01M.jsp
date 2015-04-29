<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<div id="org002M_01Btn">
<%-- 部門樹狀列表設定頁面 --%>
<button class="btn btn-primary btn-app-xs" type="button" id="Add_01M">
	<span><i class="fa fa-plus"></i></span>
</button>
<button class="btn btn-primary btn-app-xs" type="button"
	id="Edit_01M">
	<span><i class="fa fa-edit"></i></span>
</button>
<button class="btn btn-primary btn-app-xs" type="button"
	id="Delete_01M">
	<span><i class="fa fa-times txt-danger"></i></span>
</button>
</div>

<div id="deptTreeDiv" class="selectItemDiv">
</div>

<div id="showDeptMDiv" style="display:none">
<form class='form-horizontal' name='s' id='s'></form>

<form class='form-horizontal' name='org01MForm' id='org01MForm' action="" method="post">

	<button class="btn btn-primary btn-label-left" type="submit"
		id="saveBtn_01M">
		<span><i class="fa fa-save"></i></span>
		<s:message code="button.save" text="儲存" />
	</button>	
	<p>
	<div class='form-group'>
		<div class='row'>
			<div class='col-md-12'>
				<label class='col-sm-4 control-label'><span class='s'>*
				</span>部門代號：</label>
				<div class='col-sm-6'>
					<input id='dept_ID_01M' type='text' name='dept_ID_01M' class='form-control'
						value='' required="required">
				</div>
			</div>
		</div>
	</div>
	
		<div class="form-group">
		<div class="row">
			<div class="col-md-12">
				<label class="col-sm-4 control-label"><span class="s">*
				</span>部門名稱：</label>
				<div class="col-sm-6">
					<input id="dept_Name_01M" type="text" name="dept_Name_01M" class="form-control"
						value="" required="required">
				</div>
			</div>
		</div>
	</div>
	
			<div class="form-group">
		<div class="row">
			<div class="col-md-12">
				<label class="col-sm-4 control-label"><span class="s">*
				</span>部門層級：</label>
				<div class="col-sm-6">
				<select id="dept_Level_01M" name="dept_Level_01M" class="form-control">					
				</select>				
				</div>
			</div>
		</div>
	</div>
	
		<div class="form-group">
		<div class="row">
			<div class="col-md-12">
			<label class="col-sm-4 control-label">從屬部門：</label>
				<div class="col-sm-6">				
				<select id="orgDept_01M" name="orgDept_01M" class="form-control">					
				</select>				
				</div>
			</div>
		</div>
	</div>
	
	
<!-- 	<div class="form-group"> -->
<!-- 		<div class="row"> -->
<!-- 			<div class="col-md-12"> -->
<!-- 			<label class="col-sm-4 control-label">部門主管：</label> -->
<!-- 				<div class="col-sm-6"> -->
<%-- 				<select id="orgPos_01M" name="orgPos_01M" class="form-control"> --%>
				
<%-- 				</select> --%>
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 	</div> -->
	
		<div class="form-group">
		<div class="row">
			<div class="col-md-12">
			<label class="col-sm-4 control-label">區域：</label>
				<div class="col-sm-6">
				<select id="orgDom_01M" name="orgDom_01M" class="form-control">
				
				</select>
				</div>
			</div>
		</div>
	</div>
	
			<div class="form-group">
		<div class="row">
			<div class="col-md-12">
			<label class="col-sm-4 control-label">類型：</label>
				<div class="col-sm-6">
				<select id="orgType_01M" name="orgType_01M" class="form-control">
				</select>
				</div>
			</div>
		</div>
	</div>
	
			<div class="form-group">
		<div class="row">
			<div class="col-md-12">
				<label class="col-sm-4 control-label">HR部門代號：</label>
				<div class="col-sm-6">
					<input id="hr_dept_ID_01M" type="text" name="hr_dept_ID_01M" class="form-control"
						value="">
				</div>
			</div>
		</div>
	</div>
</form>
</div>


<script type="text/javascript">	
	$(document).ready(function() {
		//部門新增鈕
		$('#Add_01M').on('click', function(e) {
			showDeptInfo("add");		
		});
		//部門編輯鈕
		$('#Edit_01M').on('click', function(e) {
			if(deptIDNode==""){
				alert("請點選欲修改之部門");
			}else{
				showDeptInfo("edit");
			}
		});		
		//部門刪除紐
		$('#Delete_01M').on('click', function(e) {
			if(deptIDNode==""){
				alert("請點選欲刪除之部門");
			}else{
				deleteDept();
			}
		});
		
		//部門儲存鈕
		$("#saveBtn_01M").click(function() {
			if ($('#org01MForm')[0].checkValidity()) {
			  // If the form is invalid, submit it. The form won't actually submit;
			  // this will just cause the browser to display the native HTML5 error messages.
				event.preventDefault();
			  if($('#hr_dept_ID_01M').val()==''){
				  $('#hr_dept_ID_01M').val($('#dept_ID_01M').val());
			  }
			org002MSubmit();			  
			}
		});
	});
	
	//產生部門樹
	var deptIDNode = "";
	function getDeptTree(editDeptId) {
		editDeptId = editDeptId != '' ? editDeptId : 0;
		$('#deptTreeDiv').jstree("destroy");
		$.ajax({
			type : 'POST',
			url : CONTEXT_URL + "/org/dept/getDeptTree",
			dataType : "json",
			async : false,
			data : {
				"deptId" : editDeptId
			},
			success : function(data) {
				$('#deptTreeDiv').jstree({
					'core' : {
						'multiple' : false,
						'data' : data.rows
					}
				}).bind("loaded.jstree", function(event, data) { // 節點載入完成後觸發
					if(editDeptId!='0'){
						$("#deptTreeDiv" + " li[id="+editDeptId+"] a").click();	// 預先選擇(編輯)
						}
				}).bind("select_node.jstree", function(e, data) { // 點選節點觸發
					deptIDNode = data.instance.get_node(data.selected[0]).id; //記錄點選之部門節點	
					open02M(deptIDNode);
					});
			}
		});
	}
	
	//顯示部門資訊for 新增/編輯
	var btn01M=""; //按鈕屬性
	function showDeptInfo(btnType) {				
		$.ajax({
			url : '<s:url value="/org/dept/getDeptM" />',
			type : 'POST',
			data : {
				btnType : btnType,
				deptId : deptIDNode
			},
			dataType : "json",
			async : false,
			success : function(data) {
				btn01M=data.maps.btn01M;
				var p_ID="";
				var dept_Level="";
// 				var manager_ID="";
				var domain_ID="";
				$.each(data.maps.orgDeptInfo, function(i, item) {
					if(i=="dept_ID"){
						$('#dept_ID_01M').val(item);   //部門代號
					}
					if(i=="dept_NAME"){
						$('#dept_Name_01M').val(item);  //部門名稱
					}	
					if(i=="dept_LEVEL"){
						dept_Level=item;
						//$('#dept_Level_01M').val(item);  //部門層級
					}
					if(i=="parent_DEPT_ID"){
						p_ID=item;   //從屬部門
					}
// 					if(i=="manager"){
// 						manager_ID=item; //部門主管
// 					}
					if(i=="domain"){
						domain_ID=item; //區域
					}
					if(i=="hr_DEPT_ID"){
						$('#hr_dept_ID_01M').val(item);  //HR部門
					}
				});
				
				//重新產生下拉選單_部門層級
				var level1 = ''; 
				var levelOth = '';     
				$( '#dept_Level_01M')[0].options.length = 0;  //清空
				level1 += ( '<option value="">-- Select --</option>');
				$( '#dept_Level_01M').append(level1);                       				                     
				$.each(data.maps.deptLevel, function(i, item) {
					if(this.code==dept_Level){
						levelOth += ( '<option selected="selected" value="'+ this.code+ '">'+ this.value3+'</option>');
					}else{
						levelOth += ( '<option value="'+ this.code+ '">'+ this.value3+'</option>');
					}
				       
				 });             
				$( '#dept_Level_01M').append(levelOth);							
								
				//重新產生下拉選單_從屬部門
				var dept1 = ''; 
				var deptOth = '';     
				$( '#orgDept_01M')[0].options.length = 0;  //清空
				dept1 += ( '<option value="">-- Select --</option>');
				$( '#orgDept_01M').append(dept1);                       				                     
				$.each(data.maps.orgDept, function(i, item) {
					if(btn01M=="add"){
						if(this.dept_ID==deptIDNode){
						  deptOth += ( '<option selected="selected" value="'+ this.dept_ID+ '">'+ this.dept_NAME+'</option>');
						}
					}
					if(this.dept_ID==p_ID){
						deptOth += ( '<option selected="selected" value="'+ this.dept_ID+ '">'+ this.dept_NAME+'</option>');
					}else{
						deptOth += ( '<option value="'+ this.dept_ID+ '">'+ this.dept_NAME+'</option>');
					}
				       
				 });             
				$( '#orgDept_01M').append(deptOth);				
				
// 				//重新產生下拉選單_部門主管
// 				var pos1 = ''; 
// 				var posOth = '';     
// 				$( '#orgPos_01M')[0].options.length = 0;  //清空
// 				pos1 += ( '<option value="">-- Select --</option>');
// 				$( '#orgPos_01M').append(pos1);                       				                     
// 				$.each(data.maps.orgPosition, function(i, item) {			
// 					if(this.pos_ID==manager_ID){
// 						posOth += ( '<option selected="selected" value="'+ this.pos_ID+ '">'+ this.pos_NAME+'</option>');
// 					}else{
// 						posOth += ( '<option value="'+ this.pos_ID+ '">'+ this.pos_NAME+'</option>');
// 					}
				       
// 				 });             
// 				$( '#orgPos_01M').append(posOth);
								
				//重新產生下拉選單_區域
				var dom1 = 0; 
				var domOth = '';     
				$( '#orgDom_01M')[0].options.length = 0;  //清空			         				                     
				$.each(data.maps.orgDomain, function(i, item) {			
					if(this.id==domain_ID){
						domOth += ( '<option selected="selected" value="'+ this.id+ '">'+ this.name+'</option>');
						dom1=1;
					}
					else{
						if(dom1!=1){ //預設"全區"
							if(this.id=="HQ")
							domOth += ( '<option selected="selected" value="'+ this.id+ '">'+ this.name+'</option>');
						}
						domOth += ( '<option value="'+ this.id+ '">'+ this.name+'</option>');
					}
				       
				 });            
				$( '#orgDom_01M').append(domOth);
				
				//重新產生下拉選單_類型             			
				var posOth = '';     
				$('#orgType_01M')[0].options.length = 0;  //清空                    
				var idDW="";
				$.each(data.maps.orgDeptInfo, function(i, item) {	
					if(i=="type"){
					  idDW=item;					
					}				       
				 }); 
				if(idDW=="W"){
					posOth += ( '<option value="D">部門</option>');
					posOth += ( '<option selected="selected" value="W">工作組</option>');	
				}else{
					posOth += ( '<option selected="selected" value="D">部門</option>');
					posOth += ( '<option value="W">工作組</option>');	
				}
				$( '#orgType_01M').append(posOth);
				
				//=====顯示部門資訊Div=========
				$("#showDeptMDiv").dialog({
					width : $(window).width() - ($(window).width() * 0.5),
					height : $(window).height() - 50,
					modal : true,
					title : "部門資訊",
					position : {
						my : "center",
						at : "center"
					},
					close : function() {
						$("#org01MForm").data("changed",false);
						$("#org01MForm").trigger('reset');
					},
					open : function(event, ui) {
						$('.ui-dialog').css('z-index', 10300);
						$('.ui-widget-overlay').css('z-index', 10200);
					}
				});					
				orgMShowSet();
			}
		});		
	}
	
	//部門資訊呈現設定
	function orgMShowSet(){
		//部門代號是否readonly
		if(btn01M=='edit'){
			document.getElementById('dept_ID_01M').readOnly=true;			
		}
		if(btn01M=='add'){
			document.getElementById('dept_ID_01M').readOnly=false;			
		}			
		
		//判斷是否修改表單
		$("#org01MForm :input").change(function() {
			$("#org01MForm").data("changed",true);
		});	
	}
	
	
	//存檔動作
	var saveNode='';
	function org002MSubmit() {
		saveNode=$('#dept_ID_01M').val();
		if ( !($("#org01MForm").data("changed"))){
			alert("無修改任何值!");
		}else{
			$.ajax({
				url : CONTEXT_URL + "/org/dept/getDeptM/save",
				type : 'POST',
				data : {
					btn01M : btn01M,
					dept_ID : $('#dept_ID_01M').val(),
					dept_NAME : $('#dept_Name_01M').val(),
					dept_LEVEL : $('#dept_Level_01M').val(),
					org_DEPT : $('#orgDept_01M').val(), 
//	 				org_POS : $('#orgPos_01M').val(),
					org_DOM : $('#orgDom_01M').val(),
					org_TYPE : $('#orgType_01M').val(),
					hr_dept_ID : $('#hr_dept_ID_01M').val()
				},
				dataType : "json",
				async : false,
				success : function(data) {
					alert(data.msg);
					if(data.success){
						$("#showDeptMDiv").dialog('close');
						getDeptTree(saveNode);
						open02M(saveNode);
					}
				}
			});
		}
	}
	
	//部門刪除
	function deleteDept(){
		if (confirm("確定刪除?")) {
			$.ajax({
				url : CONTEXT_URL + "/org/dept/getDeptM/delete",
				type : 'POST',
				data : {
					deptId : deptIDNode
				},
				dataType : "json",
				async : false,
				success : function(data) {
					alert(data.msg);
					if(data.success){
						getDeptTree('');
						$("#deptPosButtons").hide();
						$("#ORG002M_02MPage").hide();
					}					
				}
			});
		}
	}
</script>