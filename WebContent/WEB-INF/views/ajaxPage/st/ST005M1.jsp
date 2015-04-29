<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html lang="en">
	<head>
		<title>派工</title>
		<meta charset="utf-8">
		
		<style type="text/css">
		.contentStyle{padding-top:6px; padding-left :15px;}
		</style>
		<script type="text/javascript">
		$(document).ready(function() {
			LoadSelect2Script('<s:url value="/resources/plugins/select2/select2.min.js" />',mountSelect2);
			
			$("#siteHuntApplyForm :input ").prop("disabled", true);
			$("#maintainTeam").prop("disabled", false);
			$("#maintainUser").prop("disabled", false);
			$("#workOrderSaveBtn").prop("disabled", false);
			$("#siteBuildApplyBtnsTr").hide();
			
			if (!preOrdersAllFinished('${workId}', '${orderId}')) {	// add by Charlie 20150110 增加判斷前序號工單是否皆已完成
				$("#preOrderNotFinishedMsg").html("(尚有前置工單未完工，無法進行派工作業)");
				$("#workOrderSaveBtn").prop("disabled", true);
			}
			
			//當工程類別選項換時， 連動委外廠商
			$("#osType").change(function() {
				var osType = $("#osType").val()
				if(osType == ""){
					$("#osVen  option").remove();
					$("#osVen").append("<option value=''>---請選擇---</option>");
					return false;
				}
				$.ajax({
					url : CONTEXT_URL+ "/st/st004/search/searchOsVen",
					type : "POST",
					dataType : 'json',
					data : {
							"osType" : $("#osType").val(),
							},
					async : false,
					success : function(data) {
//		 			alert(JSON.stringify(data));
					if(data.success){
					   $("#osVen  option").remove();
					   $("#osVen").append("<option value=''>---請選擇---</option>");
					if (data.rows.length > 0) {
						for (var i = 0; i < data.rows.length; i++) {
							$("#osVen").append("<option value="+data.rows[i].co_VAT_NO+">"+ data.rows[i].co_NAME+ "</option>");
						}
					  }
				    }
				   }
				});
			 });
			
			$("#siteBuildApplyEvent").val("${siteBuildApplyEvent}");
			//編輯時
			if ($("#siteBuildApplyEvent").val() == "view") {
                
	            $("#siteBuildSiteId").val("${siteWork.SITE_ID}");
				//工單編號欄位
				$("#showWorkId").text("${siteWork.WORK_ID}");
				$("#workId").val("${siteWork.WORK_ID}");
				//工務類別
				$("#siteBuildWorkType").val("${siteWork.WORK_TYPE}").change();
				//重要等級
				$("#priority").val("${siteWork.PRIORITY}").change();
				//申請人原
				$("#showApplyUser").text("${applyUserName}");
				$("#applyUser").val("${siteWork.APL_USER}");
				//站點編號
				$("#showsiteBuildLocationId").text("${location.LOCATION_ID}");
				$("#siteBuildLocationId").val("${location.LOCATION_ID}");
				//站點名稱
				$("#showLocName").text("${location.LOC_NAME}");
				$("#locName").val("${location.LOC_NAME}");
				//多頻段站點 
				if ("${isMultibandLocation}" > 1) {
					$("#multibandLocation").prop("checked", true);
				}
				//建站目地
				$("#purpose").prop("disabled", false);
				$("#purpose").val("${siteWork.PURPOSE}").change();
				$("#purpose").prop("disabled", true);
				//處理狀態
				$("#showWorkStatus").text("${workStatusName}");
				//工程類別
				$("#osType").val("${siteWork.OS_TYPE}").change();
				//委外廠商
				$("#osVen").val("${siteWork.OS_VEN}").change();
				//地址相關欄位
				$("#addr").val("${siteWork.ADDR}");
				$("#zip").val("${siteWork.ZIP}");
				$("#city").val("${siteWork.CITY}");
				$("#area").val("${siteWork.AREA}");
				$("#village").val("${siteWork.VILLAGE}");
				$("#road").val("${siteWork.ROAD}");
				$("#adjacent").val("${siteWork.ADJACENT}");
				$("#lane").val("${siteWork.LANE}");
				$("#alley").val("${siteWork.ALLEY}");
				$("#sub_ALLEY").val("${siteWork.SUB_ALLEY}");
				$("#door_NO").val("${siteWork.DOOR_NO}");
				$("#door_NO_EX").val("${siteWork.DOOR_NO_EX}");
				$("#floor").val("${siteWork.FLOOR}");
				$("#floor_EX").val("${siteWork.FLOOR_EX}");
				$("#room").val("${siteWork.ROOM}");
				$("#add_OTHER").val("${siteWork.ADD_OTHER}");
				//經度
				$("#siteBuildLon").val("${siteWork.SR_LON}");
				//緯度
				$("#siteBuildLat").val("${siteWork.SR_LAT}");
				//基站編號
				$("#siteBuildBtsSiteId").val("${siteWork.BTS_SITE_ID}");
				//基站頻段
				if ("${siteWork.FEQ_ID}".split(",")[0] != "null"
						&& "${siteWork.FEQ_ID}".split(",")[1] != "null") {
					$("#feqId").prop("readonly", false);	
					$("#feqId").val("${siteWork.FEQ_ID}").change();
					$("#feqId").prop("readonly", true);
				}
				//基站名稱
				$("#siteBuildSiteName").val("${siteWork.SITE_NAME}");
				//設備型態
				$("#siteBuildEqpType").prop("readonly", false);		
				$("#siteBuildEqpType").val("${siteWork.EQP_TYPE_ID}");
				$("#siteBuildEqpType").prop("readonly", true);
				getEqpModel($("#siteBuildEqpType").val(),"siteBuildEqpModel");
				//設備機型
				$("#siteBuildEqpModel").prop("readonly", false);		
				$("#siteBuildEqpModel").val("${siteWork.EQP_MODEL_ID}").change();
				$("#siteBuildEqpModel").prop("readonly", true);
				//涵蓋類別
				$("#coverageType").val("${siteWork.COVERAGE_TYPE}").change();
				//Configuration
				$("#btsConfig").val("${siteWork.BTS_CONFIG}");
				//Donor BTS 
				$("#donorSiteText").val("${donorBtsSiteId}");
				$("#donorSite").val("${siteWork.DONOR_SITE}");
				//Feederless
				$("#feederless").val("${siteWork.FEEDERLESS}").change();
				//Master Site
				$("#masterSiteText").val("${masterBtsSiteId}");
				$("#masterSite").val("${siteWork.MASTER_SITE}");
				//預計完工日
				$("#prediceEndDate").val("${prediceEndDate}");
				//作業類別
				$("#showWorkType").text("${workTypeName}");
				//完工日期
				$("#endTime").val("${siteWork.END_TIME}");
				//工單號碼
                $("#showOrderId").text("${siteWorkOrderList[0].ORDER_ID}");
                $("#orderId").val("${siteWorkOrderList[0].ORDER_ID}");
                //派工目的
                $("#showOrderType").text("${sysOrderTypeList[0].OD_TYPE_NAME}");
                $("#orderType").val("${siteWorkOrderList[0].ORDER_TYPE}");
                
                $("#cplNo").val("${siteWork.cplNo}");
    			$("#permitNo").val("${siteWork.permitNo}");
    			$("#licenseNo").val("${siteWork.licenseNo}");
               // $("#maintainTeam").val("${siteWorkOrderList[0].REP_TEAM}");
                
                //if ("${siteWorkOrderList[0].REP_TEAM}" !=  ""){
                //	selectMaintainTeam("edit");
                //}
               
                
                
               
			}//編輯時end
			
			$("#workOrderSaveBtn").click(function(){
				if ($('#maintainUser').val() == '') {
					alert('請選擇負責人')
					return
				}
				if(!confirm("確定要派工？")){
					return false;
				}
				$.ajax({
					url : CONTEXT_URL + "/st/st005/assignOrder",
					type : 'POST',
					dataType : "json",
					data:{
						"orderId" :  $("#orderId").val(),
						"maintainTeam" :  $("#maintainTeam").val(),
						"maintainUser" :  $("#maintainUser").val()
					},
					async : false,
					success : function(data) {
						alert(data.msg);
						$('#btn_search').click();
						$.fancybox.close();
					}
				});
			});
			
			//掛載負責人
			//$("#maintainTeam").change(function(){
				
			//	selectMaintainTeam(null);
			//});
			
			

			

		});//document ready end
		//取得設備機型
		function getEqpModel(eqpTypeId,tagName){
			$.ajax({
				url : CONTEXT_URL + "/st/st004/search/eqpModel",
				type : 'POST',
				data : {
					eqpTypeId :eqpTypeId,
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
		}
		
		//下拉選單
		function mountSelect2() {			
			$("#maintainTeam").select2().on('change',function(e){
				selectMaintainTeam(e.val);	
			});
			$("#maintainUser").select2();
		}
		//掛載負責人
		function selectMaintainTeam(e){
			var data ={dept :e};
			$.ajax({			
				url : CONTEXT_URL+"/st/st005/personnelDept",		
				data:data,
				type : "POST",
				dataType : 'json',
				success : function(data) {
					$("#maintainUser  option").remove();
					$("#maintainUser").append("<option value=''>--請選擇--</option>");
					if(data.success){
						if(data.rows.length > 0){
							for(var i=0; i<data.rows.length; i++){	
								$("#maintainUser").append("<option value="+data.rows[i].psn_NO+">"+data.rows[i].chn_NM+"</option>");
							}
							$("#maintainUser").select2("val","");
						}
					}
				}
			});
		}
		
		//add by Charlie 20150110 
		function preOrdersAllFinished(workId, orderId) {
			// 檢查此工單之前置工單是否皆已經完工
			var finished = true;
			$.ajax({
				url : CONTEXT_URL + "/st/st003/checkPreOrdersNotFinished",
				type : 'POST',
				dataType : "text",
				async : false,
				data:{
					"workId" :  workId,
					"orderId" : orderId
				},
				success : function(data) {						
					if(new Number(data) > 0) {
						finished = false;
					}
				}
			});	
			return finished;
		}
		</script>
	</head>
	<body>
		<div class="row">
			<div class="col-xs-12 col-sm-12">
				<div class=" ui-draggable ui-droppable">
					<div class="box-header">
						<div class="box-name">
							<i class="fa fa-search"></i> <span>建站作業處理</span>
						</div>
						<div class="box-icons">
							<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
							</a> <a class="expand-link"> <i class="fa fa-expand"></i>
							</a>
						</div>
						<div class="no-move"></div>
					</div>
					<div class="box-content">
						<form class="form-horizontal" id="siteHuntApplyForm" action="" >
							<table style="width:80%">
								<tr>
									<td><div class="clearfix">&nbsp;</div></td>
								</tr>							
								<tr>
									<td nowrap="nowrap"  align='left'>&nbsp;&nbsp;&nbsp;&nbsp;
										<button class="btn btn-primary btn-label-left" style="margin-right:10px" type="button" id="workOrderSaveBtn" >
											<span><i class="fa fa-wrench"></i></span>派工
										</button>
										<span id="preOrderNotFinishedMsg" class="s"></span>
									</td>
									
								</tr>
							</table><hr>						
							<table style="width:100%">
								<tr>
									<td nowrap="nowrap"><label class="col-sm-12 control-label">工單號碼 :</label></td>
									<td>
										<p style="padding-top:15px; padding-left:15px;" id="showOrderId"></p>
										<input id="orderId" name="orderId" type="hidden"></input>
									</td>
									<td nowrap="nowrap"><label class="col-sm-12 control-label">派工目的 :</label></td>
									<td>
										<p style="padding-top:15px; padding-left:15px;" id="showOrderType"></p>
										<input id="orderType" name="orderType" type="hidden"></input>
									</td>									
								</tr>
								<tr>
									<td>
										<div class="clearfix">&nbsp;</div>
									</td>
								</tr>								
								<tr>
									<td width="10%" nowrap="nowrap"><label class="col-sm-12 control-label">接工單位 :</label></td>
									<td width="20%">
										<div class="col-sm-12">
											<select id="repDept" name="" class="form-control" disabled="disabled">
												<!-- <option value="">---請選擇---</option> -->
													<option value="${repDept}">${deptName}</option>
											</select>
										</div>
									</td>
									
									<td width="15%" nowrap="nowrap"><label class="col-sm-12 control-label" >負責單位 :</label></td>
									<td width="20%">
										<div class="col-sm-12">
											<select id="maintainTeam" name="maintainTeam">
												<option value="">---請選擇---</option> 
												<c:forEach items="${responsibleUnit}" var="repDept">
												   <option value="${repDept.DEPT_ID}">${repDept.DEPT_ID}-${repDept.DEPT_NAME}</option>
												</c:forEach>											
											</select>
										</div>
									</td>
									<td width="10%"><label class="col-sm-12 control-label" >
										<span class="s">* </span>負責人 :</label></td>
									<td width="20%">
										<div class="col-sm-10"> 
											<select id="maintainUser" name="maintainUser" required="required">
												<option value="">---請選擇---</option>
												<c:forEach items="${psnNoList}" var="psnNo">
													<option value="${psnNo.PSN_NO}">${psnNo.CHN_NM}</option>
												</c:forEach>
																					
											</select>
										</div>
									</td>
								</tr>
							</table><hr>
							
							<table style="width: 100%">
										<jsp:include page="/WEB-INF\views\ajaxPage\st\SiteBuildApply.jsp" />
							</table>
						</form>
					</div>
				</div>
			</div>
		</div>
		 <!-- 修改欄位 的變數 -->  
         <input id="siteBuildApplyEvent"  type="hidden"></input>
	</body>
</html>