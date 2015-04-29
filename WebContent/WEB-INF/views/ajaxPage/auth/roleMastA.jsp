<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>Insert title here</title>
<script type="text/javascript">
	$(document).ready(function() {
// 		LoadBootstrapValidatorScript('<s:url value="/resources/plugins/bootstrapvalidator/bootstrapValidator.min.js" />',addFormValidator);
		
		//新增儲存按鈕
		$("#addSaveBtn").click(function() {
// 			    addFormValidator();
// 			    if($("#add_rolename").val()!=''){
// 				    var isSave = addCheck(); //判斷修改的角色名稱是否重複
// 					if(isSave=="true"){
// 						confirmChoice();
// 					}else{
// 						alert("角色名稱已被使用");
// 					}
// 			    }
			if ($('#addForm')[0].checkValidity()) {
				event.preventDefault();
			    var isSave = addCheck(); //判斷修改的角色名稱是否重複
				if(isSave=="true"){
					confirmChoice();
				}else{
					alert("角色名稱已被使用");
				}
			}
		});
		
	});

//**************FUNCTION**************

	//判斷新增角色名稱是否重複
	function addCheck(){
		var toedit = "true";
		$("#s2_RoleSelect > option").each(function() {
			var selname = this.text.substr(0,this.text.indexOf("-"));
			if(selname==$("#add_rolename").val()){						
				toedit="false";
				return false;
	      }else{
	    	  toedit="true";
	      }
		});
		return toedit;	
  }

	//判斷是否新增
	function confirmChoice() {
		if (confirm("是否確定新增")) {
			addSave();
		} else {
			parent.$.fancybox.close();
		}
	}

	//儲存新增
	function addSave() {
		$.ajax({
			url : '<s:url value="/auth/roleAddSave/" />',
			async : false,
			data : {
				roleName : $("#add_rolename").val(),
				roleDesc : $("#add_roledesc").val()
			},
			type : "POST",
			success : function(data) {
				//重新產生下拉選單
				var ohtml = '';	
				var tohtml = '';	
				$('#s2_RoleSelect')[0].options.length = 0;  //清空下拉選單
				tohtml += ('<option value="">-- 請選擇 --</option>');
				$('#s2_RoleSelect').append(tohtml);					
				
				$.each(data, function() {
					ohtml += ('<option value="'+this.id+'">'+this.name+"-"+this.desc+'</option>');
				});			
				$('#s2_RoleSelect').append(ohtml);
			},
			complete : function() {
				$("#s2_RoleSelect").select2("val",$('#s2_RoleSelect option:last').val()); 
				parent.$.fancybox.close();
				$("#btn_search").trigger('click');
			},
			error : function() {
				alert("新增失敗");
			}
		});
	}
	
	//欄位輸入初步檢核(是否為空)
	function addFormValidator(){
    	$('#addForm').bootstrapValidator({
    		message: 'This value is not valid',
    		submitButtons: 'button[type="button"]',
    				
    		fields: {
    			add_rolename: {
    				validators: {
    					notEmpty: {
    						message: '角色名稱不可為空!'
    					}
    				}
    			}
    		}
    	});
    }
	
</script>
</head>
<body>
	<!-- 彈出新增表單 -->
	<div class="box-header">
		<div class="box-name">
			<i class="fa fa-plus"></i> <span>角色新增</span>
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
				<form class="form-horizontal" id="addForm">
					<div style="text-align:left">
	                    <button class="btn btn-primary btn-label-left" type="submit" id="addSaveBtn">
		                <span><i class="fa fa-save"></i></span> <s:message code="button.save" text="儲存" />
	                    </button>
                    </div>	
					<div class="form-group" >
					<div class="clearfix">&nbsp;</div>
						<label class="col-sm-3 control-label"><span class="s">* </span>系統角色名稱 :</label>
							<div class="col-sm-4">
								<input id="add_rolename" type="text" class="form-control" required="required"
									name="add_rolename" placeholder="系統角色名稱" maxlength="50">
							</div>
					</div>
					<div class="form-group">
							<label class="col-sm-3 control-label">系統角色說明 :</label>
							<div class="col-sm-6">
								<input id="add_roledesc" type="text" class="form-control"
									name="add_roledesc" placeholder="系統角色說明" maxlength="250">
							</div>
					</div>
										
				</form>
		</div>
		</div>
		</div>
		</div>
</body>
</html>