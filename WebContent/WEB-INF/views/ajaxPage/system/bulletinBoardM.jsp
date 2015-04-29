<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>公佈欄新增</title>
<script
	src="<s:url value="/resources/plugins/tinymce/tinymce.min.js" />"></script>
<script
	src="<s:url value="/resources/plugins/tinymce/jquery.tinymce.min.js" />"></script>
<script type="text/javascript">

	////Run Select2 on element
	function Select2Priority() {
		$("#addEdit_PRIORITY").select2();
	}
	function Select2Type() {
		$("#addEdit_TYPES").select2();
	}

	$(document)
			.ready(
					function() {
						TinyMCEStart('#addEdit_CONTENTS', null);				
						// Load script of Select2 and run this	
						LoadSelect2Script(
								'<s:url value="/resources/plugins/select2/select2.min.js" />',
								Select2Priority);
						LoadSelect2Script(
								'<s:url value="/resources/plugins/select2/select2.min.js" />',
								Select2Type);
						LoadBootstrapValidatorScript(
								'<s:url value="/resources/plugins/bootstrapvalidator/bootstrapValidator.min.js" />',
								bBoardaddFormValidator);

						bulletinboardShowSet('${showType}');
						
						//新增與修改的儲存按鈕
						$('#saveBtn').click(
								function() {
									bBoardaddFormValidator();
									var content = tinyMCE.get('addEdit_CONTENTS').getContent();
															
										if ($("#addEdit_SUBJECT").val() != ''&& $("#addEdit_STARTDATE").val() != ''&& content != '') {
											//新增
											if ($("#addEdit_ID").val() == '') {
												if (confirm("是否確定新增")) {
													bBoardAddEditSave(content);
												} else {
													//parent.$.fancybox.close();
												}
											} else {
												//修改
												if (confirm("是否確定修改")) {
													bBoardAddEditSave(content,$("#addEdit_ID").val());
												} else {
													//parent.$.fancybox.close();
												}
											}
										}

								

								});

						//重置按鈕
						$('#cancelbtn').click(function() {
						//$("#bBoardAddEdit").trigger('reset');
							$('#bBoardAddEdit').data('bootstrapValidator').resetForm(true);
						});

						$('#addEdit_STARTDATE').datepicker({
							dateFormat : "yy/mm/dd",
							changeYear : true,
							changeMonth : true,
							showButtonPanel : true,
							onClose:function( dateText,inst){ $('#bBoardAddEdit').bootstrapValidator('revalidateField',
							'addEdit_STARTDATE');
                            }
						});

						$('#addEdit_ENDDATE').datepicker({
							dateFormat : "yy/mm/dd",
							changeYear : true,
							changeMonth : true,
							showButtonPanel : true,
							showTime : false,
							onClose:function( dateText,inst){ $('#bBoardAddEdit').bootstrapValidator('revalidateField',
							'addEdit_ENDDATE');
                            }
						});
						
						if ($("#addEdit_ID").val() != '') {
							$("#uploadAttachDiv").show();
						}

					});

	//**************FUNCTION**************

	//新增與修改儲存
	function bBoardAddEditSave(content, bulletinId) {
		$.ajax({
			url : '<s:url value="/sysBulletinboard/AddEdit/Save/" />',
			data : {
				addEdit_ID : bulletinId,
				addEdit_SUBJECT : $("#addEdit_SUBJECT").val(),
				addEdit_STARTDATE : $("#addEdit_STARTDATE").val(),
				addEdit_ENDDATE : $("#addEdit_ENDDATE").val(),
				addEdit_TYPES : $("#addEdit_TYPES").val(),
				addEdit_PRIORITY : $("#addEdit_PRIORITY").val(),
				addEdit_CONTENTS : content
			},
			type : "POST",
			dataType : "text",
			success : function(data) {
				if (bulletinId === undefined) {					
					if (confirm('儲存成功，是否要上傳附件？')) {
						$("#addEdit_ID").val(data);
						$("#uploadAttachDiv").show();
						return false;
					}
				} else {
					alert("儲存成功");
				}
				parent.$.fancybox.close();
 				$("#btn_search").trigger("click");
 				$("#grid").trigger("reloadGrid");
			}
		});
	}

	//欄位輸入初步檢核(是否為空)
	function bBoardaddFormValidator() {
		$('#bBoardAddEdit').bootstrapValidator({
			message : 'This value is not valid',
			submitButtons : 'button[id="saveBtn"]',

			fields : {
				addEdit_SUBJECT : {
					validators : {
						notEmpty : {
							message : '主旨不可為空!'
						}
					}
				},
				addEdit_STARTDATE : {
					group: '.col-md-6',
					validators : {
						notEmpty : {
							message : '開始時間不可為空'
						},
						 callback: {
		                        message: "起始日必須小於等於結束日，請重新選擇",
		                        callback: function (value, validator) {
		                        	if($('[name="addEdit_STARTDATE"]').val()!=null){
			                            if(new Date(value) > new Date($('[name="addEdit_ENDDATE"]').val()) ) {						                         
			                            	return false;
			                            } else {
			                                return true;
			                            }
		                        	}

		                        }
		                    }
					}
				},
				addEdit_ENDDATE : {
					group: '.col-md-6',
					validators : {
						 callback: {
		                        message: "結束日必須大於等於起始日，請重新選擇",
		                        callback: function (value, validator) {
		                            if(new Date(value) < new Date($('[name="addEdit_STARTDATE"]').val()) ) {						                         
		                            	return false;
		                            } else {
		                                return true;
		                            }
		                        }
		                    }
						}
					
				},
				addEdit_CONTENTS : {
					validators : {
						notEmpty : {
							message : '公告內容不可為空'
						}
					}
				}
			}
		});
	}
	
	//顯示檔案處理頁
	function openFileProcessPage() {	
		openFileDialogFrame("showFileUploadFrame", "BULLETIN", $("#addEdit_ID").val(), $("#fileType").val());	// This is implement in common.js
	}
	
	function bulletinboardShowSet(show){
		if(show=='view'){
			$("#bBoardAddEdit :input").prop("disabled", true);
			btnhtml="<button class='btn btn-success btn-label-left' type='button' id='downFile' onfocus='losesfocus()' onclick='goToDownloadFile(${editBulletin.bulletinID})'><span><i class='fa fa-upload'></i></span> 附件下載</button>";			
			$('#bulletinFileDiv').append(btnhtml);				
		}else{
			$("#bBoardAddEdit :input").prop("disabled", false);
		}
		
	}
	
	//檔案下載
	function goToDownloadFile(fileDoc) {
		openFileDownFrame("showFileDownloadFrame", "BULLETIN", fileDoc, "");
	}
	
	function losesfocus(){
		$('#downFile').blur();
	}
</script>
</head>
<body>
	<div class="box-header">
		<div class="box-name">
			<i class="fa fa-edit"></i> <span>公告欄新增/修改</span>
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
				<div class="form-group">
							<div class="col-sm-1">
								<div style="text-align: center">
									<button class="btn btn-primary btn-label-left" type="button"
										id="saveBtn">
										<span><i class="fa fa-save"></i></span>
										<s:message code="button.save" text="儲存" />
									</button>
								</div>
							</div>
							<c:if test="${editBulletin.bulletinID == null}">
								<div class="col-sm-1">
									<button type="reset" class="btn btn-default btn-label-left"
										id="cancelbtn">
										<span><i class="fa fa-undo txt-danger"></i></span> 重置
									</button>
								</div>
							</c:if>
						</div>
					<div class="clearfix">&nbsp;</div>
					<form class="form-horizontal" id="bBoardAddEdit"
						action="<s:url value="/sysBulletinboard/AddEdit/Save/" />"
						method="post">						
						<input id="btntype" type='hidden' value="${showType}" /> 
						<input id="addEdit_ID" name="addEdit_ID" type='hidden' value="${editBulletin.bulletinID}" />
						<div class="form-group">
							<label class="col-sm-2 control-label"><span class="s">*
							</span>主旨：</label>
							<div class="col-sm-8">
								<input id="addEdit_SUBJECT" maxlength="500" type="text"
									class="form-control" name="addEdit_SUBJECT"
									value="${editBulletin.subject}">
							</div>
						</div>
						<div class="form-group">
							<div class="row">
								<div class="col-md-6">
									<label class="col-sm-4 control-label"><span class="s">*
									</span>起始日：</label>
									<div class="col-sm-4">
										<input id="addEdit_STARTDATE" type="text"
											name="addEdit_STARTDATE" class="form-control"
											placeholder="公告開始時間"
											value="<fmt:formatDate  value="${editBulletin.startDate}"  pattern="yyyy/MM/dd"  />">
									</div>
								</div>
								<div class="col-md-6">
									<label class="col-sm-4 control-label">結束日：</label>
									<div class="col-sm-4">
										<input id="addEdit_ENDDATE" type="text"
											name="addEdit_ENDDATE" class="form-control"
											placeholder="公告結束時間"
											value="<fmt:formatDate  value="${editBulletin.endDate}"  pattern="yyyy/MM/dd"  />">
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="row">
								<div class="col-md-6">
									<label class="col-sm-4 control-label">類型：</label>
									<div class="col-sm-4">
										<select class="populate placeholder" name="addEdit_TYPES"
											id="addEdit_TYPES">
											<c:forEach items="${bulletinType}" var="bType">
												<c:choose>
													<c:when test="${bType.CODE==editBulletin.types}">
														<option value="${bType.CODE}" selected="selected">${bType.VALUE1}</option>
													</c:when>
													<c:otherwise>
														<option value="${bType.CODE}">${bType.VALUE1}</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="col-md-6">
									<label class="col-sm-4 control-label">重要性：</label>
									<div class="col-sm-4">
										<select class="populate placeholder" name="addEdit_PRIORITY"
											id="addEdit_PRIORITY">
											<c:forEach var="i" begin="0" end="9">
												<c:choose>
													<c:when test="${editBulletin.priority==i}">
														<option value="${i}" selected="selected">${i}</option>
													</c:when>
													<c:otherwise>
														<option value="${i}">${i}</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</select>
									</div>
								</div>
							</div>
						</div>
						
						<div class="form-group" style="display:none" id="uploadAttachDiv">
							<label class="col-sm-2 control-label">附件：</label>
							<div id="bulletinFileDiv" class="col-sm-5">
								<button class="btn btn-success btn-label-left" type="button" id="fileProcessBtn" onclick="openFileProcessPage()">
									<span><i class="fa fa-upload"></i></span> 附件上傳
								</button>
								<input id="fileType" type='hidden' value="${fileType}" /> 
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-2 control-label"><span class="s">*
							</span>內容：</label>
							<c:choose>
							 <c:when test="${showType=='view'}">
							 <div class="col-sm-9" style="min-height:200px;border:1px solid #cccccc; BACKGROUND-COLOR: #f5f5f5">
							 ${editBulletin.contents}
							 </div>
							 </c:when>
							 <c:otherwise>
							 <div class="col-sm-9">
							 <textarea class="form-control" rows="8" id="addEdit_CONTENTS"
									name="addEdit_CONTENTS" maxlength="4000" >${editBulletin.contents}</textarea>
									</div>
							 </c:otherwise>
							</c:choose>						
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div id="showFileUploadFrame"></div>
	<div id="showFileDownloadFrame"></div>
</body>
</html>