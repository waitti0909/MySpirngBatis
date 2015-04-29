<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<title>工項維護作業</title>
		<script src="<s:url value="/resources/plugins/bootstrapvalidator/bootstrapValidator.min.js" />"></script>
	</head>
	<body>
		<div class="box-header">
			<div class="box-name">
				<i class="fa fa-edit"></i>
				<span>工項維護</span>
			</div>
			<div class="box-icons">
				<p class="expand-link"></p>
			</div>
			<div class="no-move"></div>
		</div>
		<div class="row">
			<div class="col-xs-12 col-sm-12">
				<div class="ui-draggable ui-droppable">
					<div class="box-content">
						<form id="addEdit" class="form-horizontal" method="post">
							<input type="hidden" id="btnType_M" value="${btn_type}" />
							<input type="hidden" id="isPoUsed" value="${isPoUsed}" />
							<input type="hidden" id="item_ID" name="item_ID" value="${element.ITEM_ID}" />
							<input type="hidden" id="quantity" name="quantity" value="${element.QUANTITY}" />
							<input type="hidden" id="md_USER" name="md_USER" value="${element.MD_USER}" />
							<input type="hidden" id="md_TIME" name="md_TIME" value="<fmt:formatDate value="${element.MD_TIME}" pattern="yyyy-MM-dd"/>" />
							<input type="hidden" id="cr_USER" name="cr_USER" value="${element.CR_USER}" />
							<input type="hidden" id="cr_TIME" name="cr_TIME" value="<fmt:formatDate value="${element.CR_TIME}" pattern="yyyy-MM-dd"/>" />
							<input type="hidden" id="sort" name="sort" value="${element.SORT}" />
							<div class="col-sm-4" id="btnDiv">
								<button type="submit" id="saveBtn" class="btn btn-primary btn-label-left editabled">
									<span><i class="fa fa-save"></i></span>
									<s:message code="button.save" text="儲存" />
								</button>
							</div>
							<table style="width: 100%">
							<tr>
								<td width="10%" nowrap="nowrap"><label
									class="col-sm-12 control-label"><span class="s">*
									</span>主項 :</label></td>
								<td>
									<div class="col-sm-4"
										style="width: 50%; height: auto; float: left; display: inline">
										<select id="mainItemSelectM" name="main_ITEM" class="populate placeholder require">
											<option value="">---請選擇---</option>
											<c:forEach items="${sessionScope.mainItemList}" var="mainItem">
												<option value="${mainItem.CAT_ID}" <c:if test="${element.MAIN_ITEM == mainItem.CAT_ID}">selected="selected"</c:if>>${mainItem.CAT_NAME}</option>
											</c:forEach>
										</select>
									</div>
									<div
										style="width: 10%; height: auto; float: left; display: inline">
										<button class="btn btn-primary btn-label-left"
											style="margin-right: 10px" type="button"
											id="addMainItemBtn"><span><i class="fa fa-plus"></i></span>增加項目</button>
									</div>
								</td>
								<td width="20%" nowrap="nowrap">
									<div style="height: auto; float: left; display: inline">
										<input id="enabled" name="enabled" class="editabled" type="checkbox" <c:if test="${element.ENABLED eq 'Y'}">checked</c:if>>&nbsp;&nbsp;<b>啟用</b>
									</div>
								</td>
							</tr>
							<tr>
								<td style="padding-top: 15px;"><label style="padding-left: 5px;"
									class="col-sm-12 control-label"><span class="s">*
									</span>中項 :</label></td>
								<td style="padding-top: 15px;">
									<div class="col-sm-4"
										style="width: 80%; height: auto; float: left; display: inline">
										<select id="subItemSelectM" name="sub_ITEM" class="populate placeholder require">
											<option value="">---請選擇---</option>
											<c:forEach items="${subItemList}" var="subItem">
												<option value="${subItem.CAT_ID}" <c:if test="${element.SUB_ITEM == subItem.CAT_ID}">selected="selected"</c:if>>${subItem.CAT_NAME}</option>
											</c:forEach>											
										</select>
									</div>
									<div
										style="width: 10%; height: auto; float: left; display: inline">
										<button class="btn btn-primary btn-label-left"
											style="margin-right: 10px" type="button"
											id="addSubItemBtn"><span><i class="fa fa-plus"></i></span>增加項目</button>
									</div>
								</td>
							</tr>
							<tr>
								<td style="padding-top: 15px;"><label style="padding-left: 5px;"
									class="col-sm-12 control-label"><span class="s">*
									</span>項目名稱 :</label></td>
								<td style="padding-top: 15px;" colspan="5" nowrap="nowrap">
									<div class="col-sm-10" height: auto; float: left; display: inline">
										<input class="form-control require editabled poUsed" id="item_NAME"
											name="item_NAME" value="${element.ITEM_NAME}" 
											placeholder="必填" />
									</div>
								</td>
							</tr>
							<tr>
								<td style="padding-top: 15px;"><label style="padding-left: 5px;"
									class="col-sm-12 control-label"><span class="s">*
									</span>項次 :</label></td>
								<td style="padding-top: 15px;" colspan="5" nowrap="nowrap">
									<div class="col-sm-2" height: auto; float: left; display: inline">
										<input class="form-control itemRegxM" id="item_NO"
											name="item_NO" value="${element.ITEM_NO}" 
											placeholder="必填，例:1.9.3"/>
									</div>
								</td>
							</tr>
							<tr>
								<td style="padding-top: 15px;"><label style="padding-left: 5px;"
									class="col-sm-12 control-label"><span class="s">*
									</span>單位工時 :</label></td>
								<td style="padding-top: 15px;" colspan="5" nowrap="nowrap">
									<div class="col-sm-2" height: auto; float: left; display: inline">
										<input class="form-control editabled numericM1 poUsed" id="unit_HOUR"
											name="unit_HOUR"
											value="<fmt:formatNumber value="${element.UNIT_HOUR}" pattern="###.##" />" 
											placeholder="整數一位、小數二位數，例:1.23"/>
									</div>
								</td>
							</tr>
							<tr>
								<td style="padding-top: 15px;"><label style="padding-left: 5px;"
									class="col-sm-12 control-label"><span class="s">*
									</span>單位 :</label></td>
								<td style="padding-top: 15px;" colspan="5" nowrap="nowrap">
									<div class="col-sm-2" height: auto; float: left; display: inline">
										<input class="form-control editabled require poUsed" id="unit"
											name="unit" value="${element.UNIT}"
											placeholder="必填" maxlength="10"/>
									</div>
								</td>
							</tr>
							<tr>
								<td style="padding-top: 15px;"><label style="padding-left: 5px;"
									class="col-sm-12 control-label"><span class="s">*
									</span>參考單價 :</label></td>
								<td style="padding-top: 15px;" colspan="5" nowrap="nowrap">
									<div class="col-sm-2" height: auto; float: left; display: inline">
										<input class="form-control editabled numericM" id="price"
											name="price"
											value="<fmt:formatNumber value="${element.PRICE}" pattern="###.##" />"
											placeholder="數字，必填，小數二位數" />
									</div>
								</td>
							</tr>
							<tr>
								<td style="padding-top: 15px;"><label style="padding-left: 5px;"
									class="col-sm-12 control-label">管理費 :</label></td>
								<td style="padding-top: 15px;" colspan="5" nowrap="nowrap">
									<div class="col-sm-2" height: auto; float: left; display: inline">
										<input class="form-control editabled integerO" id="mgr_FEE"
											name="mgr_FEE" value="${element.MGR_FEE}" 
											placeholder="整數" />
									</div>
									<label style="padding-left: 0px;" class="control-label">%</label>							
								</td>
							</tr>
							<tr>
								<td valign='top' width="10%" nowrap="nowrap"
									style="padding-top: 15px;"><label
									class="col-sm-12 control-label">備註 :</label></td>
								<td colspan="5" nowrap="nowrap">
								    <div style="margin-top: 15px" class="col-sm-10" height: auto; float: left; display: inline">									
										<textarea class="form-control editabled" id="memo" name="memo" rows="6" maxlength="400">${element.MEMO}</textarea>
									</div>
								</td>
							</tr>
						</table>
						</form>
					</div>
				</div>
			</div>
		</div>
		<!-- 主/中項新增 -->
		<div id="showAddItemFrame"></div>
	</body>
</html>
<script type="text/javascript">
	// Load script of Select2 and run this
	$(document).ready(function() {
		LoadSelect2Script("<s:url value="/resources/plugins/select2/select2.min.js" />", selectDefault);

		// 欄位檢核設定
		var fields = {};
		$(".require").each(function () {
			var name = $(this).attr("name");
			var group = "." + $(this).parent().attr("class");
			fields[name] = {
				group : group,
				validators : {
					notEmpty : {
						message : "不可為空!"
					}
				}
			};
		});
		$(".itemRegxM").each(function () {
			var name = $(this).attr("name");
			var group = "." + $(this).parent().attr("class");
			fields[name] = {
				group : group,
				validators : {
					notEmpty: {
                        message: '不得為空'
                    },
                    regexp: {
                        regexp: /^[1-9][0-9]*(.[0-9]{1,2}){2,4}$/i,
                        message: '必須符合項次格式'
                    },
                    stringLength: {
                        max: 10,
                        message: '長度最大為10'
                    }
				}
			};
		});
		$(".numericM1").each(function () {
			var name = $(this).attr("name");
			var group = "." + $(this).parent().attr("class");
			fields[name] = {
				group : group,
				validators : {
					notEmpty: {
                        message: '不得為空'
                    },
					numeric: {
                        message: '必須為數值'
                    },
                    regexp: {
                        regexp: /^[0-9](.[0-9]{1,2})?$/i,
                        message: '必須為整數壹位、小數兩位數'
                    }
				}
			};
		});		
		$(".numericM").each(function () {
			var name = $(this).attr("name");
			var group = "." + $(this).parent().attr("class");
			fields[name] = {
				group : group,
				validators : {
					notEmpty: {
                        message: '不得為空'
                    },
					numeric: {
                        message: '必須為數值'
                    },
                    regexp: {
                        regexp: /^[0-9]+(.[0-9]{1,2})?$/i,
                        message: '必須為小數兩位數'
                    },
                    stringLength: {
                        max: 18,
                        message: '長度不符合格式，最大長度18'
                    }
				}
			};
		});
		$(".integerM").each(function () {
			var name = $(this).attr("name");
			var group = "." + $(this).parent().attr("class");
			fields[name] = {
				group : group,
				validators : {
					notEmpty: {
                        message: '不得為空'
                    },
					integer: {
                        message: '必須為整數'
                    }
				}
			};
		});
		$(".integerO").each(function () {
			var name = $(this).attr("name");
			var group = "." + $(this).parent().attr("class");
			fields[name] = {
				group : group,
				validators : {
					integer: {
                        message: '必須為整數'
                    }
				}
			};
		});
		$("#addEdit").bootstrapValidator({
			fields : fields
		}).on("success.form.bv", function(e) {
			e.preventDefault();
			//提交前判断
			if (!($("#addEdit").data("changed"))){
				alert("無修改任何值!");
				return ;
			} else {
				MSubmitData();
			}
		});
		
		// 根據按鈕型態 呈現 編輯 /檢視
		showSet($("#btnType_M").val());
		
	});
	
	// 開啟下拉式選單搜尋文字功能
	function selectDefault() {
		$("#addEdit").find("select").select2();
	}

	// 檢視畫面設定
	function showSet(btnType) {
		if (btnType == "view") {
			$("#addEdit :input").prop("disabled", true);
		} else if (btnType == "edit") {
			$("#addEdit :input").prop("disabled", true);
			$(".editabled").prop("disabled", false);
			$(".poUsed").prop("disabled", $("#isPoUsed").val() == "true");
		} else if (btnType == "add") {
			//something todo
		}
	}

	//主項
	$("#mainItemSelectM").change(function(){
		//中項
		$("#subItemSelectM").html("");
		$("<option value=''>-- 請選擇 --</option>").appendTo("#subItemSelectM");
		$("#subItemSelectM").select2();
		var parentCatId = $("#mainItemSelectM").find("option:selected").prop("value");
		
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
							$("#subItemSelectM  option").remove();
							$("#subItemSelectM").append($("<option></option>").attr("value", "").text("--請選擇--"));
							for(i=0; i < data.rows.length; i++){
								$("#subItemSelectM").append("<option value=" + data.rows[i].cat_ID + ">" + data.rows[i].cat_NAME + "</option>");
								$("#subItemSelectM").prop("disabled",false);
							}							 
						}
					}
				}
			});
		}				
	});
	
	// 主項新增Button
	$("#addMainItemBtn").click(function() {
		openAddItemDialogFrame("showAddItemFrame", null, "mainItemAdd");
	});
	
	// 中項新增Button
	$("#addSubItemBtn").click(function() {
		
		parentCatId = $("#mainItemSelectM").val()
		
		if (parentCatId != "" || parentCatId != ''){
			openAddItemDialogFrame("showAddItemFrame", parentCatId, "subItemAdd");
		} else {
			alert("請選擇主項!");
			return false;
		}
	});
	
	//判斷是否修改表單
	$("#addEdit :input").change(function() {
		$("#addEdit").data("changed", true);
	});
	
	function reloadMainItemSelect(catObject){
		$.ajax({			
			url : CONTEXT_URL + "/basic/cm003/searchMainItem",		
			data: {},
			type : "POST",
			dataType : 'json',
			success : function(data) {
				if(data.success){
					if(data.rows.length > 0){
						$("#mainItemSelectM").html("");
						$("<option value=''>-- 請選擇 --</option>").appendTo("#mainItemSelectM");
						$("#mainItemSelectM").select2();
						
						//清空中項
						$("#subItemSelectM").html("");
						$("<option value=''>-- 請選擇 --</option>").appendTo("#subItemSelectM");
						$("#subItemSelectM").select2();
						
						for(i=0; i < data.rows.length; i++){
							if (catObject.cat_ID == data.rows[i].cat_ID) {
								$("#mainItemSelectM").append("<option selected='selected' value=" + data.rows[i].cat_ID + ">" + data.rows[i].cat_NAME + "</option>");
							} else {
								$("#mainItemSelectM").append("<option value=" + data.rows[i].cat_ID + ">" + data.rows[i].cat_NAME + "</option>");
							}
								
						}		
						$("#mainItemSelectM").trigger("change"); 
					}
				}
			}
		});
	}
	
	function reloadSubItemSelect(catObject){
		var data = {
				parentCatId : catObject.parent_CAT
			};
		$.ajax({			
			url : CONTEXT_URL + "/basic/cm003/subItem/",		
			data: data,
			type : "POST",
			dataType : 'json',
			success : function(data) {
				if(data.success){
					if(data.rows.length > 0){
						$("#subItemSelectM  option").remove();
						$("#subItemSelectM").append($("<option></option>").attr("value", "").text("--請選擇--"));
						for(i=0; i < data.rows.length; i++){
							if (catObject.cat_ID == data.rows[i].cat_ID) {
								$("#subItemSelectM").append("<option selected='selected' value=" + data.rows[i].cat_ID + ">" + data.rows[i].cat_NAME + "</option>");
							} else {
								$("#subItemSelectM").append("<option value=" + data.rows[i].cat_ID + ">" + data.rows[i].cat_NAME + "</option>");
							}
							$("#subItemSelectM").prop("disabled",false);
						}							 
					}
					$("#subItemSelectM").trigger("change"); 
				}
			}
		});
	}
	
	// 存檔動作
	function MSubmitData() {
		$("#addEdit :input").prop("disabled", false);
		var btnType = $("#btnType_M").val();
		$.ajax({
			url : CONTEXT_URL + "/basic/cm003/save/",
			type : "POST",
			data : JSON.stringify(form2js($("#addEdit").attr("id"), ".",
					true, null)),
			contentType : "application/json",
			dataType : "json",
			async : false,
			success : function(data) {
				alert(data.msg.replace("error", ""));
				if (data.msg.indexOf("error") >= 0) {
					showSet(btnType);
					return false;
				}
				parent.$.fancybox.close();
			}
		});
	}
</script>