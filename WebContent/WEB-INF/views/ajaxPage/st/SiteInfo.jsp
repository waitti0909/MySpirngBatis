<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
<title>站點維護</title>
	<style type="text/css">
		.contentStyle{padding-top:6px; padding-left :15px;}
	</style>
	<script type="text/javascript">
		
		$(document).ready(function (){
			$( "#tabs" ).tabs();
			$("#siteInfoForm :input").prop("disabled",true);
			if("${eventType}" != "view"){
				$("#siteInfoForm :button").prop("disabled",false);
			}
		});
		function deleteSite(siteId){
			if(confirm("確定要刪除站台？")){
				$.ajax({
					type : 'POST',
					url : CONTEXT_URL + "/st/st001/deleteSite",
					data :{
						"siteId" : siteId,
					},
					async : false,
					success : function(data) {
						alert(data.msg);
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
					}
				});
			}
		}
	</script>
</head>
<body>
	<div class="row">
		<div class="col-xs-12 col-sm-12">
			<div class=" ui-draggable ui-droppable">
				<div class="box-content">
					<form id="siteInfoForm" class="form-horizontal" method="post">
						<div id="tabs">
							<ul id="tabsTitle">
								<c:forEach  items="${siteList}" var ="site">
									<c:if test="${site.BTS_SITE_ID ne null}">
										<li><a href="#${site.BTS_SITE_ID}">${site.BTS_SITE_ID}</a></li>
									</c:if>
									<c:if test="${site.BTS_SITE_ID eq null}">
										<li><a href="#${site.FEQ_ID}">申請中${site.feqName}</a></li>
									</c:if>
								</c:forEach>
							</ul>
							<c:forEach  items="${siteList}" var ="site">
							<div id="${site.BTS_SITE_ID}">
								<table style="width:100%" id="siteInfoTable">
									<tr>
										<td>
											<label class="col-sm-12 control-label" >
												<button  class="btn btn-primary btn-label-left" type="button" value="${site.SITE_ID}" onclick="deleteSite('${site.SITE_ID}');">
													<span><i class="fa fa-times"></i></span>
													<s:message  text="刪除基站" />
												</button>
											</label>
										</td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td width="12%"><label class="col-sm-12 control-label" >基站編號 :</label></td>
										<td width="20%" class="contentStyle">${site.BTS_SITE_ID}</td>
										
										<td width="20%" nowrap="nowrap"><label class="col-sm-12 control-label">基站狀態 :</label></td>
										<td width="15%" class="contentStyle">${site.SITE_STATUS}</td>
										
										<td width="15%"><label class="col-sm-12 control-label">涵蓋區域 :</label></td>
										<td width="20%" class="contentStyle">${site.locRoad}</td>
									</tr>
									<tr>
										<td>
											<div class="clearfix">&nbsp;</div>
										</td>
									</tr>
									<tr>
										<td><label class="col-sm-12 control-label">設備型態 :</label></td>
										<td class="contentStyle">${site.eqpName}</td>
										
										<td><label class="col-sm-12 control-label">設備機型 :</label></td>
										<td class="contentStyle">${site.eqpModel}</td>
										
										<td><label class="col-sm-12 control-label">簽約日期 :</label></td>
										<td class="contentStyle">
											<fmt:formatDate value="${site.signDate}" pattern="yyyy/MM/dd" var="signDate"/>	
											${signDate}
										</td>
									</tr>
									<tr>
										<td>
											<div class="clearfix">&nbsp;</div>
										</td>
									</tr>
									<tr>
										<td><label class="col-sm-12 control-label">涵蓋類別 :</label></td>
										<td class="contentStyle">${site.COVERAGE_TYPE}</td>
										
										<td><label class="col-sm-12 control-label">室內天線涵蓋樓層 :</label></td>
										<td class="contentStyle">${site.COVERAGE_IN_FLOOR}</td>
										
										<td><label class="col-sm-12 control-label">Integrated 日期 :</label></td>
										<td class="contentStyle">
											<fmt:formatDate value="${site.INTEGRATED_DATE}" pattern="yyyy/MM/dd" var="integratedDate"/>
											${integratedDate}
										</td>
									</tr>
									<tr>
										<td>
											<div class="clearfix">&nbsp;</div>
										</td>
									</tr>
									<tr>
										<td></td>
										<td></td>
										
										<td></td>
										<td></td>
										
										<td><label class="col-sm-12 control-label">On Air :</label></td>
										<td class="contentStyle">
											<fmt:formatDate value="${site.ONAIR_DATE}" pattern="yyyy/MM/dd" var="onairDate"/>
											${onairDate}
										</td>
									</tr>
									<tr>
										<td>
											<div class="clearfix">&nbsp;</div>
										</td>
									</tr>
									<tr>
										<td nowrap="nowrap"><label class="col-sm-12 control-label">Configuration :</label></td>
										<c:choose>
											<c:when test="${site.eqpName eq 'Node B' or site.eqpName eq 'eNodeB'}">
												<td class="contentStyle">${site.BTS_CONFIG}</td>
											</c:when>
											<c:otherwise>
												<td></td>
											</c:otherwise>
										</c:choose>
										<td><label class="col-sm-12 control-label">Donor BTS :</label></td>
										<c:choose>
											<c:when test="${site.eqpName eq 'Repeater' or site.eqpName eq 'Booster'}">
												<td class="contentStyle">${site.DONOR_SITE}</td>
											</c:when>
											<c:otherwise>
												<td></td>
											</c:otherwise>
										</c:choose>
										
										<td><label class="col-sm-12 control-label">停用日期 :</label></td>
										<td class="contentStyle">
											<fmt:formatDate value="${site.SUSPEND_DATE}" pattern="yyyy/MM/dd" var="suspendDate"/>
											${suspendDate}
										</td>
									</tr>
									<tr>
										<td>
											<div class="clearfix">&nbsp;</div>
										</td>
									</tr>
									<tr>
										<td><label class="col-sm-12 control-label">Feederless :</label></td>
										<c:choose>
											<c:when test="${site.eqpName eq 'Node B' or site.eqpName eq 'eNodeB'}">
												<td class="contentStyle">${site.FEEDERLESS}</td>
											</c:when>
											<c:otherwise>
												<td></td>
											</c:otherwise>
										</c:choose>
										
										<td><label class="col-sm-12 control-label">Master Site :</label></td>
										<c:choose>
											<c:when test="${site.eqpName eq 'Node B' or site.eqpName eq 'eNodeB'}">
												<td class="contentStyle">${site.MASTER_SITE}</td>
											</c:when>
											<c:otherwise>
												<td></td>
											</c:otherwise>
										</c:choose>
										
										<td><label class="col-sm-12 control-label">預計恢復日期 :</label></td>
										<td class="contentStyle">
											<fmt:formatDate value="${site.RESTORE_DATE}" pattern="yyyy/MM/dd" var="restoreDate"/>
											${restoreDate}
										</td>
									</tr>
									<tr>
										<td>
											<div class="clearfix">&nbsp;</div>
										</td>
									</tr>
									<tr >
										<td colspan="4" nowrap="nowrap" style="padding-left : 60px;">
											<div class="row form-group">
												<div class="col-sm-10">
													<div class="checkbox-inline">
														<label>
															<c:choose>
																<c:when test="${site.COVERAGE_INDOOR eq 'Y'}">
																	<input name="COVERAGE_INDOOR" type="checkbox" checked> 有室內涵蓋
																	<i class="fa fa-square-o"></i>
																</c:when>
																<c:otherwise>
																	<input name="COVERAGE_INDOOR" type="checkbox"> 有室內涵蓋
																	<i class="fa fa-square-o"></i>
																</c:otherwise>
															</c:choose>
														</label>
													</div>
													<div class="checkbox-inline">
														<label>
															<c:choose>
																<c:when test="${site.HIDDEN eq 'Y'}">
																	<input name="HIDDEN" type="checkbox" checked> 有美化施工
																	<i class="fa fa-square-o"></i>
																</c:when>
																<c:otherwise>
																	<input name="HIDDEN" type="checkbox"> 有美化施工
																	<i class="fa fa-square-o"></i>
																</c:otherwise>
															</c:choose>
														</label>
													</div>
													<div class="checkbox-inline">
														<label>
															<c:choose>
																<c:when test="${site.NOIS_ON_AIR eq 'Y'}">
																	<input name="NOIS_ON_AIR" type="checkbox" checked> 網管On Air
																	<i class="fa fa-square-o"></i>
																</c:when>
																<c:otherwise>
																	<input name="NOIS_ON_AIR" type="checkbox"> 網管On Air
																	<i class="fa fa-square-o"></i>
																</c:otherwise>
															</c:choose>
														</label>
													</div>
													<div class="checkbox-inline">
														<label>
															<c:choose>
																<c:when test="${site.l2_SWITCH eq 'Y'}">
																	<input name="L2_SWITCH" type="checkbox" checked> 有接L2_SWITCH
																	<i class="fa fa-square-o"></i>
																</c:when>
																<c:otherwise>
																	<input name="L2_SWITCH" type="checkbox"> 有接L2_SWITCH
																	<i class="fa fa-square-o"></i>
																</c:otherwise>
															</c:choose>
														</label>
													</div>
												</div>
											</div>
										</td>
									</tr>
									<tr>
										<td>
											<input name="siteFeqId" type="hidden" value="${site.FEQ_ID}" />
											<div class="clearfix">&nbsp;</div>
										</td>
									</tr>
								</table>
							</div>
							</c:forEach>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>