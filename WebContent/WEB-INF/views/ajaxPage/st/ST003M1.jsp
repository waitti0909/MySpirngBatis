<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
		<title>派工</title>
		<script type="text/javascript">
		$(document).ready(function() {
			LoadSelect2Script(
					'<s:url value="/resources/plugins/select2/select2.min.js" />',mountSelect2);
			buildCityOptions("siteHuntCity","siteHuntArea");
			$("#siteHuntApplyEvent").val("${siteHuntApplyEvent}");
			$("#siteHuntApplyForm :input ").prop("disabled", true);
			<!-- $("#workTeam").prop("disabled", false); -->
			$("#maintainTeam").prop("disabled", false);
			$("#maintainUser").prop("disabled", false);
			$("#workOrderSaveBtn").prop("disabled", false);
			$("#siteHuntApplyBtnsTr").hide();
			$('#date').datepicker({
				dateFormat : "yy/mm/dd",
				changeYear : true,
				changeMonth : true,
				showButtonPanel : true
			});
			
			if (!preOrdersAllFinished('${workId}', '${orderId}')) {	// add by Charlie 20150110 增加判斷前序號工單是否皆已完成
				$("#preOrderNotFinishedMsg").html("(尚有前置工單未完工，無法進行派工作業)");
				$("#workOrderSaveBtn").prop("disabled", true);
			}
			
			$("#workOrderSaveBtn").click(function(){
				if ($('#maintainUser').val() == '') {
					alert('請選擇負責人')
					return
				}
				if(!confirm("確定要派工？")){
					return false;
				}
				$.ajax({
					url : CONTEXT_URL + "/st/st003/assignOrder",
					type : 'POST',
					dataType : "json",
					data:{
						"orderId" :  '${orderId}',
						"maintainUser" : $("#maintainUser").val(),
						"maintainTeam" : $("#maintainTeam").val()
					},
					async : false,
					success : function(data) {						
							alert(data.msg);
							$('#btn_search').click();
		 					parent.$.fancybox.close();
						}
				});				
			});
			
			table = $('#workLocTable').dataTable( {
				"bDestroy": true,
				"iDisplayLength" : -1,
				"aaSorting" : [[ 0, "desc" ]],
				"sDom" : "rS",
// 	 			"bAutoWidth":true,
				"sScrollY" : '70',
// 		        "sScrollX" : "100%",
// 		        "bProcessing" : false,
// 		        "sAjaxDataProp" : "rows",
// 		        "sAjaxSource" : CONTEXT_URL+'/org/dept/deptPos/deptPosTable?deptId='+deptId,
// 		        "aoColumnDefs" : [ {
// 		           	"aTargets" : [0], bSortable : false, "mData" : "posId",
// 		           	"mRender": function ( data, type, full ) {
// 		             	return '<input type="checkbox"  onclick="listenerDeptPosCheckBox();" '+disable+' name="deptPos" value="'+data+'">';
// 		            }},
// 		            { "aTargets": [1], "mData": "posId"},
// 		            { "aTargets": [2], "mData": "posName"},
// 		            { "aTargets": [3], "mData": "posType"},
// 		            { "aTargets": [3], "mData": "posType"},
// 		           ],
		        "oLanguage":{
		        	"sProcessing": "處理中...",
		        	"sZeroRecords": "沒有匹配結果",
		        	"sLoadingRecords": "讀取中..."
		        },"fnInitComplete": function() {
		            this.fnAdjustColumnSizing();
		        }
			});
			
			table = $('#signLogTable').dataTable( {
				"bDestroy": true,
				"iDisplayLength" : -1,
				"aaSorting" : [[ 0, "desc" ]],
				"sDom" : "rS",
// 	 			"bAutoWidth":true,
				"sScrollY" : '70',
// 		        "sScrollX" : "100%",
// 		        "bProcessing" : false,
// 		        "sAjaxDataProp" : "rows",
// 		        "sAjaxSource" : CONTEXT_URL+'/org/dept/deptPos/deptPosTable?deptId='+deptId,
// 		        "aoColumnDefs" : [ {
// 		           	"aTargets" : [0], bSortable : false, "mData" : "posId",
// 		           	"mRender": function ( data, type, full ) {
// 		             	return '<input type="checkbox"  onclick="listenerDeptPosCheckBox();" '+disable+' name="deptPos" value="'+data+'">';
// 		            }},
// 		            { "aTargets": [1], "mData": "posId"},
// 		            { "aTargets": [2], "mData": "posName"},
// 		            { "aTargets": [3], "mData": "posType"},
// 		            { "aTargets": [3], "mData": "posType"},
// 		           ],
		        "oLanguage":{
		        	"sProcessing": "處理中...",
		        	"sZeroRecords": "沒有匹配結果",
		        	"sLoadingRecords": "讀取中..."
		        } ,"fnInitComplete": function() {
		            this.fnAdjustColumnSizing();
		        }
			});			
			
			
 			if($("#siteHuntApplyEvent").val() == "insert"){
				$("#cancelApplyBtn").prop("disabled",true);
			}
			
			if($("#siteHuntApplyEvent").val() == "update"){
				$("#showWorkId").text("${siteWork.WORK_ID}");
				$("#workId").val("${siteWork.WORK_ID}");
				$("#showWorkType").text("${workTypeName}");
				$("#siteHuntWorkType").val("${siteWork.WORK_TYPE}").change();
				$("#showWorkStatus").text("${workStatusName}");
				$("#siteHuntWorkStatus").val("${siteWork.WORK_STATUS}");
				$("#priority").val("${siteWork.PRIORITY}").change();
				$("#showApplyUser").text("${applyUserName}");
				$("#applyUser").val("${siteWork.APL_USER}");
				$("#showsiteHuntLocationId").text("${location.LOCATION_ID}");
				$("#siteHuntLocationId").val("${location.LOCATION_ID}");
				$("#showLocName").text("${location.LOC_NAME}");
				$("#locName").val("${location.LOC_NAME}");
				if("${isMultibandLocation}" >1){
					$("#multibandLocation").prop("checked",true);
				}
				$("#showSiteHuntSrId").text("${siteWork.SR_ID}");
				$("#siteHuntSrId").val("${siteWork.SR_ID}");
				if($("#siteHuntSrId").val() != ""){
					$("#siteHuntCity").prop("disabled",true);
					$("#siteHuntArea").prop("disabled",true);
					$("#siteHuntLon").prop("readonly",true);
					$("#siteHuntLat").prop("readonly",true);
					$("#coverRange").prop("readonly",true);
				}
				if("${siteWork.FEQ_ID}".split(",")[0] != "null" && "${siteWork.FEQ_ID}".split(",")[1] != "null"){
					$("#feqId").val("${siteWork.FEQ_ID}").change();
				}

				$("#purpose").val("${siteWork.PURPOSE}").change();
				$("#siteHuntCity").val("${siteWork.CITY}").change();
				$("#siteHuntArea").val("${siteWork.AREA}").change();
				$("#siteHuntLon").val("${siteWork.SR_LON}");
				$("#siteHuntLat").val("${siteWork.SR_LAT}");
				$("#coverRange").val("${siteWork.SR_COVER_RANG}").change();
				$("#siteHuntBtsSiteId").val("${siteWork.BTS_SITE_ID}");
				$("#siteHuntSiteId").val("${siteWork.SITE_ID}");
				$("#siteHuntSiteName").val("${siteWork.SITE_NAME}");
				$("#siteHuntEqpType").val("${siteWork.EQP_TYPE_ID}");

				getEqpModel("${siteWork.EQP_TYPE_ID}","siteHuntEqpModel");

// 				var eqpType = $("#siteHuntEqpType").val();
// 				var feqType = $("#feqId").val().split(",")[1];
// 				if(eqpType ==3 || eqpType==4){
// 					$("#btsConfig").prop("readonly",true).val("");
// 					$("#feederless").prop("disabled",true);
// 					$("#coverRange").prop("disabled",true).val("");
// 					$("#siteHuntLon").prop("disabled",true).val("");
// 					$("#siteHuntLat").prop("disabled",true).val("");
// 					$("#selectSrIdBtn").prop("disabled",true);
// 				}else if(eqpType == 1 || eqpType == 2){
// 					$("#btsConfig").prop("readonly",false);
// 					$("#donorSiteText").val("");
// 					$("#donorSite").val("");
// 					$("#masterSiteText").val("");
// 					$("#masterSite").val("");
// 					$("#feederless").val("").change();
// 					$("#feederless").prop("disabled",true);
// 				}else{
// 					$("#donorSiteText").val("");
// 					$("#donorSite").val("");
// 					$("#btsConfig").prop("readonly",true).val("");
// 					$("#feederless").prop("disabled",true);
// 				}
//  				if($("#feqId").val().split(",")[1] == "L" && 
// 						$("#siteHuntEqpType").val() == 2){
					
// 					$("#siteHuntBtsSiteId").prop("readonly",true);
// 					$("#unuseBtsSiteIdBtn").prop("disabled",true);
// 				}
/*  				else{
					
					$("#siteHuntBtsSiteId").prop("readonly",false);
					$("#unuseBtsSiteIdBtn").prop("disabled",false);
				} */
				//
				$("#siteHuntEqpModel").val("${siteWork.EQP_MODEL_ID}").change();
				$("#coverageType").val("${siteWork.COVERAGE_TYPE}");
				$("#btsConfig").val("${siteWork.BTS_CONFIG}");
				$("#donorSiteText").val("${donorBtsSiteId}");
				$("#donorSite").val("${siteWork.DONOR_SITE}");
				$("#masterSiteText").val("${masterBtsSiteId}");
				$("#masterSite").val("${siteWork.MASTER_SITE}");
				$("#feederless").val("${siteWork.FEEDERLESS}").change();
				$("#prediceEndDate").val("${prediceEndDate}");
			
				<c:set var="st002M_workDesc" value="${siteWork.WORK_DESC}" scope="request"></c:set>
				$("#applyTime").val("${siteWork.APL_TIME}");
				$("#endTime").val("${siteWork.END_TIME}");
			}	
	
			
			
		});//document ready end
			
		//下拉選單
		function mountSelect2() {			
			$("#maintainTeam").select2().on('change',function(e){
				mountApplicant(e.val);	
			});
			$("#maintainUser").select2();
		}
		
		//掛載負責人
		function mountApplicant(e){
			var data ={dept :e};
			$.ajax({			
				url : CONTEXT_URL+"/st/st003/personnelDept",		
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
		
		function getEqpModel(eqpTypeId,tagName){
		
			$.ajax({
				url : CONTEXT_URL + "/st/st002/search/eqpModel",
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
							<i class="fa fa-search"></i> <span>尋點作業處理</span>
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
							<table style="width:100%">
								<tr>
									<td nowrap="nowrap"  align='right'>
										<button class="btn btn-primary btn-label-left" style="margin-right:10px" type="button" id="workOrderSaveBtn" >
											<span><i class="fa fa-wrench"></i></span>派工
										</button>
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
								<jsp:include page="/WEB-INF/views/ajaxPage/st/SiteHuntApply.jsp"></jsp:include>
							</table>		
							<input type="hidden" id="siteLocEvent" value="${siteLocEvent}"></input>	
							<input id="siteHuntApplyEvent"  type="hidden"></input>
						</form>
					</div>	
				</div>
			</div>
		</div>
		<div id="showSiteLocAddrPage"></div>
	</body>
</html>