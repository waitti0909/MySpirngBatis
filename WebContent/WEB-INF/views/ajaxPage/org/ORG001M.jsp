<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<title>職務名稱維護設定</title>
		<script src="<s:url value="/resources/plugins/bootstrapvalidator/bootstrapValidator.min.js" />"></script>
		<script type="text/javascript">
			$().ready(function(){
				showSet('${showType}');
				$("#pos_TYPE").select2();
				$("#pos_TYPE").val("${position.POS_TYPE}").change();
				var posId = "${position.POS_ID}";
				var event =$("#event").val();
				if(posId == ""){
					$("#addForm").attr("action", "<s:url  value='/org/ORG001/addSave' />");
					event = "add";
				}else{
					$("#addForm").attr("action", "<s:url  value='/org/ORG001/updateSave' />");
					event="update";
				}
				$('#addForm').bootstrapValidator({
		    		fields: {
		    			POS_ID: {
		    				validators: {
		    					notEmpty: {
		    						message: '職務代號不可為空!'
		    					},
		    					regexp: {
		    						regexp: /^[a-zA-Z0-9_]+$/,
		    						message: "職稱代號只能是數字,英文或是底線"
		    					}
		    				}
		    			},POS_NAME: {
		    				validators: {
		    					notEmpty: {
		    						message: '職務名稱不可為空!'
		    					}
		    				}
		    			}
		    		}
		    	}).on('success.form.bv', function(e) {
					
		            var $form = $(e.target);
		            e.preventDefault();
		            if($("#pos_ID").val().split("__").length >1){
	            		alert("職稱代號底線不可連續");
	            		return false;
	            	}
		            
		            $.post($form.attr('action'), $form.serialize(), function(result) {
		            	
		            	if(result==true){
		            		if(event == "add"){
		            			alert('<s:message code="msg.add.success"/>');
		            		}else{
		            			alert('<s:message code="msg.update.success"/>');
		            		}
		            		var code=$("#pos_ID").val();
							var name=$("#pos_NAME").val();
							$("#positionCode").val(code);
							$("#positionName").val(name);
							var data= {positionCode : code,positionName : name};
		 					$("#grid").setGridParam({page:1,datatype:"json", postData:data, mtype: 'POST'}).jqGrid().trigger("reloadGrid");
		            		parent.$.fancybox.close();
		            	}else{
		            		if(event == "add"){
		            			alert('<s:message code="msg.add.dataExists"/>');
		            		}else{
		            			alert('<s:message code="msg.update.fail"/>');
		            		}
		            	}			
		            	e.preventDefault();
		     				$("#grid").trigger("reloadGrid");
		            });
		        });
			});//ready end
			function showSet(showSet){
				if(showSet=="view"){
					$("#addForm :input").prop("disabled", true);
				}
			}
		</script>
	</head>
	<body>
		<div class="box-header">
			<div class="box-name">
				<i class="fa fa-plus"></i> <span>職務名稱修改</span>
			</div>
			<div class="box-icons">			 
				<p class="expand-link"></p>
			</div>
			<div class="no-move"></div>
		</div>
		<div class="row">
			<div class="col-xs-8 col-sm-8">
				<div class=" ui-draggable ui-droppable">
					<div class="box-content">
						<form class="form-horizontal" id="addForm"  method="post">
							<div class="col-sm-3">
								<button style="margin-left: 25px" class="btn btn-primary btn-label-left" type="submit" id="saveBtn">
				                <span><i class="fa fa-save"></i></span> <s:message code="button.save" text="儲存" />
			                    </button>
		                    </div>
		                    <div class="clearfix">&nbsp;</div>
							<div class="form-group" >
								<label class="col-sm-3 control-label"><span class="s">* </span>職務名稱代號 :</label>
								<div class="col-sm-4">
									<c:if test="${position.POS_ID !=null }">
									<input id="pos_ID" type="text" class="form-control"
									name="POS_ID" value="${position.POS_ID}" readonly="readonly"  placeholder="職務名稱代號" maxlength="30">
									</c:if>
									<c:if test="${position.POS_ID ==null }">
									<input id="pos_ID" type="text" class="form-control"
									name="POS_ID" value="${position.POS_ID}" placeholder="職務名稱代號" maxlength="30">
									</c:if>
								</div>
							</div>
							<div class="form-group" >
								<label class="col-sm-3 control-label"><span class="s">* </span>職務名稱描述 :</label>
								<div class="col-sm-4">
									<input id="pos_NAME" type="text" class="form-control"
										name="POS_NAME" value="${position.POS_NAME}" placeholder="職務名稱描述" maxlength="128">
								</div>
							
								<div class="clearfix">&nbsp;</div>
								<label class="col-sm-3 control-label"><span class="s">* </span>職務類別 :</label>
								<div class="col-sm-4">
									<select class="populate placeholder" name="POS_TYPE" id="pos_TYPE">
										<option value="N">一般</option>
										<option value="W">簽核</option>
									</select>
								</div>
							</div>
							<input id="event" type="hidden">				
						</form>
					</div>
				</div>
			</div>
		</div>
	
	</body>
</html>