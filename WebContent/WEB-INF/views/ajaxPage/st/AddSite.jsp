<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<!DOCTYPE html>
<html lang="en">
	<head>
		<title>建立基站</title>
		<script type="text/javascript">
			
			$(document).ready(function() {
				$("#saveAddSiteBtn").click(function(){
					if ($('#addSiteForm')[0].checkValidity()) {
						event.preventDefault();
						$.ajax({
							url : CONTEXT_URL + "/st/st001/addSite",
							type : 'POST',
							data : $("#addSiteForm").serialize()+
									"&city="+$("#city").val()+
									"&area="+$("#siteLocArea").val()+
									"&locationId="+$("#location_ID").val()+
									"&coverageType="+$("#addSite_coverageType").val(),
							async : false,
							success : function(data){
								var result = data['result'];
								if(result == true){
									alert('<s:message code="msg.add.success"/>');
									$("#${targetFrameId}").dialog('close');
									var city = $('#city').val();
									var area = $("#siteLocArea").val();
									var locName = $("#loc_NAME").val();
									var locType = $("#loc_TYPE").val();
									var location_ID = $("#location_ID").val();
									$("#locationId").val(location_ID);
									$("#locName").val(locName);
									$('#locType').select2("val",locType);
									$('#locCity').select2("val",city).change();
									$('#locTown').select2("val",area);
									var data= {
											locationId : location_ID,
											btsSiteId : "",
											locName : locName,
											locType : locType,
											locCity : city,
											locTown : area,
											siteStatus : "",
											siteFeq : "",
									};
				 					$("#grid").setGridParam({page:1,datatype:"json", postData:data, mtype: 'POST'}).jqGrid().trigger("reloadGrid");
									updateSiteLoc($("#location_ID").val(),"edit");
								}else{
									alert(result);
								}
							}
						});
					}
				});//saveButton end
				
				$("#resetAddSiteBtn").click(function(){
					$("#addSiteForm").trigger('reset');
					$("#addSiteForm :input").prop("disabled",false);
				});//reset button end
				
				$("#addSite_SiteFeq").change(function(){
					verifyNodeBLogic("addSite_SiteFeq");
				});
				
				$("#addSite_eqpType").change(function(){
					verifyNodeBLogic("addSite_eqpType");
					var eqpTypeId= $("#addSite_eqpType").val();
					var tagName= "addSite_eqpModel";
					$.ajax({
						url : CONTEXT_URL + "/st/st001/search/eqpModel",
						type : 'POST',
						data : {
							"eqpTypeId" :eqpTypeId
						},
						async : false,
						success : function(datas){
							$('#'+tagName).html("");
							$('<option value="">---請選擇---</option>').appendTo('#'+tagName);
								for(var data in datas){
									$('<option value="'+data+'">'+datas[data]+'</option>').appendTo('#'+tagName);
								}
						}					
			       	});
				});
				
				$("#addSite_UnuseBtsSiteIdBtn").click(function(){
					var targetFrameId = "showUnuseBtsSiteIdPage";
					$.ajax({
						url : CONTEXT_URL + "/st/st002/search/unuseBTSSiteId",
						type : 'POST',
						dataType : "html",
						data:{
							"callBackFun" : $("#unuseBtsSiteIdCallBackFun").val(),
							"targetFrameId" : targetFrameId,
						},
						async : false,
						success : function(data) {
							initialDialogFrame(targetFrameId, "可用基站編號",data);
						}
					});
				});
			});//document.ready end
			function unuseBtsSiteIdCallBackFun(object){
				var obj = JSON.parse(object);
				$("#addSite_BtsSiteId").val(obj.bts_SITE_ID);
				$("#addSite_eqpType").val($("#unuseBtsSiteIdEqpType").val()).change();
				$("#addSite_SiteFeq").val($("#unuseBtsSiteIdSiteFeq").val()).change();
				$("#addSite_coverageType").val($("#unuseBtsSiteIdCoverageType").val());
// 				if($("#addSite_eqpType").val() ==null){
// 					alert("此站點類別只能建立Repeater及Booster基站");
// 					$("#addSite_BtsSiteId").val("");
// 					$("#addSite_eqpType").val("");
// 					$("#addSite_SiteFeq").val("");
// 					return false;
// 				}else if($("#addSite_SiteFeq").val() ==null){
// 					alert("此頻段已經存在，無法建立");
// 					$("#addSite_BtsSiteId").val("");
// 					$("#addSite_eqpType").val("");
// 					$("#addSite_SiteFeq").val("");
// 					return false;
// 				}
			}
			
			function verifyNodeBLogic(tagId){
				if($("#addSite_SiteFeq").val() != null && $("#addSite_SiteFeq").val() !=""){
					var eqpTypeId = $("#addSite_eqpType").val();
					var feqType = $("#addSite_SiteFeq").val().split(",")[1];
					$("#addSite_BtsSiteId").prop("disabled",false);
					$("#addSite_UnuseBtsSiteIdBtn").prop("disabled",false);
					if(eqpTypeId == "1" && feqType == "L"){
						alert("Node B無法選擇4G頻段");
						$("#"+tagId).val("");
						return false;
					}else if(eqpTypeId == "2" && feqType == "U"){
						alert("eNodeB無法選擇3G頻段");
						$("#"+tagId).val("");
						return false;
					}
// 					else if(eqpTypeId == "2" && feqType == "L"){
// 						$("#addSite_BtsSiteId").val("");
// 						$("#addSite_BtsSiteId").prop("disabled",true);
// 						$("#addSite_UnuseBtsSiteIdBtn").prop("disabled",true);
// 					}
				}
			}
		</script>
	</head>
	<body>
		<div class="row">
			<div id="" class="col-xs-12 col-sm-12">
				<div class="box ui-draggable ui-droppable">
					<div class="box-content">
						<div class="form-group">
							<form class="form-horizontal" id="addSiteForm" action="">
							<!--  系統功能  -->
								<button class="btn btn-primary btn-label" type="submit" id="saveAddSiteBtn">
									<s:message code="button.ok" text="確定" />
								</button>
								<button class="btn btn-primary btn-label" type="button"
									id="resetAddSiteBtn">
									<s:message code="button.reset" text="重置" />
								</button>
		
								<table style="width:100%">
									<tr>
										<td width="10%" nowrap="nowrap"><label  class="col-sm-10 control-label" style="padding-left : 0px;"><span class="s" id="btsSiteIdPrimary">* </span>基站頻段 : </label></td>
										<td width="20%">
											<div class="col-sm-6">
												<select id="addSite_SiteFeq" name="FEQ_ID" class="form-control" required="required">
													<option value="">---請選擇---</option>
														<c:forEach items="${siteFeqList}" var="siteFeq">
															<option value="${siteFeq.key},${siteFeq.value.value}">${siteFeq.value.label}</option>
														</c:forEach>
												</select>
											</div>
										</td>
										<td width="10%"></td>
										<td width="20%"></td>
									</tr>
									<tr>
										<td>
											<div class="clearfix">&nbsp;</div>
										</td>
									</tr>
									<tr >
										<td><label class="col-sm-10 control-label"><span class="s">* </span>基站編號 :</label></td>
										<td>	
											<table>
												<tr>
													<td width="50%">
														<div class="col-sm-12">
															<input id="addSite_BtsSiteId" name="BTS_SITE_ID"  type="text" class="form-control" required="required" maxlength="20"/>
														</div>
													</td>
													<td width="50%">
														<input id="addSite_UnuseBtsSiteIdBtn" type="button" value="可用基站編號" style="padding-left :0px;padding-right :0px"/>
													</td>	
												</tr>
											</table>
										</td>
										<td><label class="col-sm-10 control-label">基站名稱 :</label></td>
										<td>
											<div class="col-sm-8">
												<input id="addSite_SiteName" name="SITE_NAME"  type="text" class="form-control" maxlength="100"/>
											</div>
										</td>
									</tr>
									<tr>
										<td>
											<div class="clearfix">&nbsp;</div>
										</td>
									</tr>
									<tr>
										<td>
											<label  class="col-sm-10 control-label" style="padding-left : 0px;"><span class="s">* </span>設備型態 : </label>
										</td>
										<td>
											<div class="col-sm-6">
												<select id="addSite_eqpType" name="EQP_TYPE_ID" class="form-control" required="required">
													<option value="">---請選擇---</option>
														<c:forEach items="${eqpTypeList}" var="eqpType">
															<option value="${eqpType.value}">${eqpType.label}</option>
														</c:forEach>
												</select>
											</div>
										</td>
										<td>
											<label  class="col-sm-10 control-label" style="padding-left : 0px;">設備機型 : </label>
											
										</td>
										<td>
											<div class="col-sm-6">
												<select id="addSite_eqpModel" name="EQP_MODEL_ID" class="form-control">
													<option value="">---請選擇---</option>
												</select>
											</div>
										</td>
									</tr>
								</table>
								<input id="addSite_coverageType" type="hidden" />
								<div id="showUnuseBtsSiteIdPage" ></div>
								<input type="hidden" id="unuseBtsSiteIdCallBackFun" name="unuseBtsSiteIdCallBackFun" value="unuseBtsSiteIdCallBackFun"/>
							</form>
							<!--  系統功能 end -->
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>