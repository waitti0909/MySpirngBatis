<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<title>尋點作業申請</title>
		<script type="text/javascript">
		var selectBtsSiteIdEvent = false;
		$(document).ready(function() {
			buildCityOptions("siteHuntCity","siteHuntArea");
			$("#siteHuntApplyEvent").val("${siteHuntApplyEvent}");
			initForm();
			$("#selectSiteBtn").click(function(){
				openSiteDialogFrame("showSitePage", $("#siteCallBackFun").val(),undefined,undefined,"close","site");
			});
			
			$("#donorSiteBtn").click(function(){
				var eqpType = $("#siteHuntEqpType").val();
				if(eqpType==3 || eqpType==4){
					openSiteDialogFrame("showSitePage", $("#donorSiteCallBackFun").val(),undefined,undefined,"close","site");
				}else{
					alert("此設備型態無法選擇Donor BTS");
				}
			});
			
			$("#masterSiteBtn").click(function(){
				if($("#feederless").val() == "R"){
					openSiteDialogFrame("showSitePage", $("#masterSiteCallBackFun").val(),undefined,undefined,"close","site");	
				}else{
					alert("Feederless為Remote才可以選擇Master Site");
				}
			});
			
			$("#siteHuntWorkType").change(function(){
				var workType = $("#siteHuntWorkType").val();
				$("#siteHuntBtsSiteId").prop("required",true);
				$("#btsSiteIdPrimary").prop("hidden",false);
				if(workType == "NSR"){
					$("#siteHuntBtsSiteId").prop("required",false);
					$("#btsSiteIdPrimary").prop("hidden",true);
					//如果不是系設而且是NSR的狀態下不選擇SRID就必須選擇區域 經緯度 涵蓋區域，
					$.ajax({
						url : CONTEXT_URL + "/st/st002/checkDeptId",
						type : 'POST',
						data : {
							loginDeptId : "${loginDeptId}"
						},
						async : false,
						success : function(data) {
							if(!data){
								$("#isSystemDesignDept").val("N");
								$("#siteHuntSrId").val("");
								$("#showSiteHuntSrId").text("");
								$("#siteHuntCity").prop("disabled",false).val("");
								$("#siteHuntArea").prop("disabled",false).val("");
								$("#siteHuntLon").prop("readonly",false).val("");
								$("#siteHuntLat").prop("readonly",false).val("");
								$("#coverRange").prop("readonly",false).val("");
								$("#lonPrimary").prop("hidden",true);
								$("#latPrimary").prop("hidden",true);
								$("#siteHuntLon").prop("required",false);
								$("#siteHuntLat").prop("required",false);
							}else{
								$("#isSystemDesignDept").val("Y");
							}
						}
					});
				}else{
					$("#siteHuntCity").prop("disabled",true);
					$("#siteHuntArea").prop("disabled",true);
					$("#siteHuntLon").prop("readonly",true);
					$("#siteHuntLat").prop("readonly",true);
					$("#coverRange").prop("readonly",true);
					$("#lonPrimary").prop("hidden",false);
					$("#latPrimary").prop("hidden",false);
					$("#siteHuntLon").prop("required",true);
					$("#siteHuntLat").prop("required",true);
				}
			});
			
			$("#siteHuntEqpType").change(function(){
// 				initForm();
				getEqpModel($("#siteHuntEqpType").val(),"siteHuntEqpModel");
				var eqpType = $("#siteHuntEqpType").val();
				var feqType = $("#feqId").val();
				if(feqType != "" && feqType != null){
					feqType = $("#feqId").val().split(",")[1];
// 					var feederless = $("#feederless").val();
					if(eqpType == 1 && feqType =="L"){
						alert("設備型態為Node B只能使用3G的基站頻段");
						$("#siteHuntEqpType").val("").change();
						return false;
					}else if(eqpType == 2 && feqType =="U"){
						alert("設備型態為eNode B只能使用4G的基站頻段");
						$("#siteHuntEqpType").val("").change();
						return false;
					}
					if(eqpType ==3 || eqpType==4){
						$("#btsConfig").prop("readonly",true).val("");
					}else if(eqpType == 1 || eqpType == 2){
						$("#btsConfig").prop("readonly",false);
						$("#donorSiteText").val("");
						$("#donorSite").val("");
					}else if(eqpType == ""){
						$("#masterSiteText").val("");
						$("#masterSite").val("");
						$("#donorSiteText").val("");
						$("#donorSite").val("");
						$("#btsConfig").prop("readonly",false).val("");
					}
				}
				
// 				disableBtsSiteIdText();
			});
			
			$("#feederless").change(function(){
				var feederless = $("#feederless").val();
				$("#coverageTypePrimary").prop("hidden",true);
				$("#coverageType").prop("required",false);
				if(feederless !='R'){
					$("#masterSiteText").val("");
					$("#masterSite").val("");
				}else{
					$("#coverageTypePrimary").prop("hidden",false);
					$("#coverageType").prop("required",true);
				}
			});
			
			$("#feqId").change(function(){
				var eqpType = $("#siteHuntEqpType").val();
				var feqType = $("#feqId").val();
				if(feqType != "" && feqType != null){
					feqType = $("#feqId").val().split(",")[1];
					if(eqpType == 1 && feqType =="L"){
						alert("設備型態為Node B只能使用3G的基站頻段");
						$("#feqId").val("").change();
						return false;
					}else if(eqpType == 2 && feqType =="U"){
						alert("設備型態為eNode B只能使用4G的基站頻段");
						$("#feqId").val("").change();
						return false;
					}
				}
				
// 				disableBtsSiteIdText();
			});
			if($("#siteHuntApplyEvent").val() == "insert"){
				$("#cancelApplyBtn").prop("disabled",true);
				$("#showWorkType").text("尋點作業申請");
			}
			if($("#siteHuntApplyEvent").val() == "update"){
				$("#showWorkId").text("${siteWork.WORK_ID}");
				$("#workId").val("${siteWork.WORK_ID}");
				$("#showWorkType").text("${workTypeName}");
				$("#siteHuntWorkType").val("${siteWork.WORK_TYPE}").change();
				$("#siteHuntWorkType").prop("disabled",true);
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
				$("#siteHuntLon").val("${siteWork.SR_LON}");
				$("#siteHuntLat").val("${siteWork.SR_LAT}");
				$("#coverRange").val("${siteWork.SR_COVER_RANG}").change();
				$("#siteHuntSiteId").val("${siteWork.SITE_ID}");
				$("#siteHuntSiteName").val("${siteWork.SITE_NAME}");
				$("#siteHuntEqpType").val("${siteWork.EQP_TYPE_ID}").change();
				getEqpModel("${siteWork.EQP_TYPE_ID}","siteHuntEqpModel");
				//
				var eqpType = $("#siteHuntEqpType").val();
				var feqType = $("#feqId").val().split(",")[1];
// 				if(eqpType ==3 || eqpType==4){
// 					$("#btsConfig").prop("readonly",true);//.val("");
// 					$("#feederless").prop("disabled",false);
// 					$("#coverRange").prop("disabled",true);//.val("");
// 					$("#siteHuntLon").prop("disabled",true);//.val("");
// 					$("#siteHuntLat").prop("disabled",true);//.val("");
// 					$("#selectSrIdBtn").prop("disabled",true);
// 				}else 
				if(eqpType == 1 || eqpType == 2){
					$("#btsConfig").prop("readonly",false);
// 					$("#donorSiteText").val("");
// 					$("#donorSite").val("");
// 					$("#masterSiteText").val("");
// 					$("#masterSite").val("");
				}else{
// 					$("#donorSiteText").val("");
// 					$("#donorSite").val("");
					$("#btsConfig").prop("readonly",true);//.val("");
				}
// 				if($("#feqId").val().split(",")[1] == "L" && 
// 						$("#siteHuntEqpType").val() == 2){
// 					$("#siteHuntBtsSiteId").prop("readonly",true);
// 					$("#unuseBtsSiteIdBtn").prop("disabled",true);
// 				}else{
// 				$("#siteHuntBtsSiteId").prop("readonly",false);
// 				$("#unuseBtsSiteIdBtn").prop("disabled",false);
// 				}
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
				$("#siteHuntBtsSiteId").val("${siteWork.BTS_SITE_ID}");
				$("#siteHuntCity").val("${siteWork.CITY}").change();
				$("#siteHuntArea").val("${siteWork.AREA}").change();
				//if($("#siteHuntWorkStatus").val() == "W02" || "${isSHByNSRFinish}" == "Y"){//如果是待辦disabled srId,eqptype,feqId
					//$("#feqId").prop("disabled",true);
					//$("#siteHuntEqpType").prop("disabled",true);
					//$("#selectSrIdBtn").prop("disabled",true);
				//}
				isSiteHuntByNSRFinish(true);
			}else{
				$("#fileProcessBtn").prop("disabled", true);
			}
			$("#fileProcessBtn").click(function(){
				openFileDialogFrame("showFileUploadPage", $("#siteHuntWorkType").val(),$("#workId").val(), "NOTYPE");
			});
			$("#fileDownloadsBtn").click(function(){
				openFileDownFrame("showFileDownloadPage", $("#siteHuntWorkType").val(),$("#workId").val(), "NOTYPE");
			});
			$("#unuseBtsSiteIdBtn").click(function(){
				selectUnuseBTSSiteId();
			});
			
			
			$("#siteWorkSaveBtn").click(function(){
				var msg = save();
				if(msg == "success"){
					if($("#siteHuntApplyEvent").val() == "insert"){
						alert('<s:message code="msg.add.success"/>');
					}else{
						alert('<s:message code="msg.update.success"/>');
					}
					var data= {
							workType : $.trim($("#siteHuntWorkType").val()),
							srId : $.trim($("#siteHuntSrId").val()),
							siteWorkCity : $.trim($("#siteHuntCity").val()),
							siteWorkArea : $.trim($("#siteHuntArea").val()),
							btsSiteId : $.trim($("#siteHuntBtsSiteId").val()),
							siteName : $.trim($("#siteHuntSiteName").val()),
							locationId : $.trim($("#siteHuntLocationId").val()),
							siteFeq : $.trim($("#feqId").val().split(",")[0]),
							workStatus : $.trim($("#siteHuntWorkStatus").val()),
							applyDept : "",
							applyDateStart:"",
							applyDateEnd:""
					};	
					$("#workType").val($("#siteHuntWorkType").val()).change();
					$("#siteWorkCity").val($("#siteHuntCity").val()).change();
					$("#siteWorkArea").val($("#siteHuntArea").val()).change();
					$("#siteWorkSiteName").val($("#siteHuntSiteName").val());
					$("#locationId").val($("#siteHuntLocationId").val());
					$("#siteFeq").val($("#feqId").val().split(",")[0]).change();
					$("#workStatus").val($("#siteHuntWorkStatus").val()).change();
					$("#siteWorkSrId").val($("#siteHuntSrId").val());
					$("#siteWorkBtsSiteId").val($("#siteHuntBtsSiteId").val());
					$("#grid").setGridParam({page:1 ,datatype:"json", postData:data, mtype: 'POST'}).jqGrid().trigger("reloadGrid");
					$.fancybox.close();
				}else if(msg !=""){
					alert(msg);
					if ($('#addSiteWorkForm')[0].checkValidity()) {
						event.preventDefault();
					}
				}
				
			});//save butoon end
			
			$("#siteHuntApplyBtn").click(function(){
				if ($('#addSiteWorkForm')[0].checkValidity()) {
					event.preventDefault();
					if(!confirm("送出申請後將無法再修改資料，是否繼續？")){
						return false;
					}
					var msg = dovalidate();
					if(msg != ""){
						alert( msg);
						return false;
					}
					var orderIdArray = [];
					if($("#siteHuntApplyEvent").val() == "update"){
						$('#workOrderTable tbody').each(function () {
							var data = $('#workOrderTable').dataTable().fnGetData($(this).index()-1);
							orderIdArray [orderIdArray.length]= data['order_ID'];
						});
					}
					var attr ="&siteHuntApplyEvent="+$("#siteHuntApplyEvent").val()+"&orderIdArray="+orderIdArray;
					if($("#siteHuntCity").prop("disabled") && $("#siteHuntArea").prop("disabled")){
						attr += "&city="+$("#siteHuntCity").val()+"&area="+$("#siteHuntArea").val();
					}
					if($("#siteHuntWorkType").prop("disabled")){
						attr+="&workType="+$("#siteHuntWorkType").val();
					}
// 					if(($("#siteHuntWorkStatus").val() == "W02" && $("#siteHuntWorkType").val() == "SH")|| "${isSHByNSRFinish}" == "Y"){
// 						$("#feqId").prop("disabled",false);
// 						$("#siteHuntEqpType").prop("disabled",false);
// 					}
					isSiteHuntByNSRFinish(false);
					$.ajax({
						url : CONTEXT_URL + "/st/st002/updateApply",
						type : 'POST',
						async : false,
						data : $("#addSiteWorkForm").serialize()+attr,
						success : function(datas){
							var result = datas['result'];
							if(result == true){
								var siteWork = datas['siteWork'];
								alert('<s:message code="msg.apply.success"/>');
								$("#workStatus").val(siteWork.work_STATUS).change();
								$("#applyDept").val(siteWork.app_DEPT).change();
								
								var data= {
										workType : $.trim($("#siteHuntWorkType").val()),
										srId : $.trim($("#siteHuntSrId").val()),
										siteWorkCity : $.trim($("#siteHuntCity").val()),
										siteWorkArea : $.trim($("#siteHuntArea").val()),
										btsSiteId : $.trim(siteWork.bts_SITE_ID),
										siteName : $.trim($("#siteHuntSiteName").val()),
										locationId : $.trim($("#siteHuntLocationId").val()),
										siteFeq : $.trim($("#feqId").val().split(",")[0]),
										workStatus : $.trim(siteWork.work_STATUS),
										applyDept : $.trim(siteWork.app_DEPT),
										applyDateStart:"",
										applyDateEnd:""
								};
								$("#workType").val($("#siteHuntWorkType").val()).change();
								$("#siteWorkCity").val($("#siteHuntCity").val()).change();
								$("#siteWorkArea").val($("#siteHuntArea").val()).change();
								$("#siteWorkSiteName").val($("#siteHuntSiteName").val());
								$("#locationId").val($("#siteHuntLocationId").val());
								$("#siteFeq").val($("#feqId").val().split(",")[0]).change();
								$("#workStatus").val(siteWork.work_STATUS).change();
								$("#siteWorkSrId").val($("#siteHuntSrId").val());
								$("#siteWorkBtsSiteId").val(siteWork.bts_SITE_ID);
								$("#grid").setGridParam({page:1 ,datatype:"json", postData:data, mtype: 'POST'}).jqGrid().trigger("reloadGrid");
								$.fancybox.close();
							}else{
								alert(result);
// 								if(($("#siteHuntWorkStatus").val() == "W02" && $("#siteHuntWorkType").val() == "SH")|| "${isSHByNSRFinish}" == "Y"){
// 									$("#feqId").prop("disabled",true);
// 									$("#siteHuntEqpType").prop("disabled",true);
// 								}
								isSiteHuntByNSRFinish(true);
								event.preventDefault();
							}
						}					
			       	});
				}
			});//siteHuntApplyBtn end
			
			
			$("#selectSrIdBtn").click(function (){
				var targetFrameId = "showSrIdPage";
				$.ajax({
					url : CONTEXT_URL + "/st/sr/ST002SP2/init",
					type : 'POST',
					dataType : "html",
					data:{
						"callBackFun" :  $("#srIdCallBackFun").val(),
						"targetFrameId" : targetFrameId,
					},
					async : false,
					success : function(data) {
						initialDialogFrame(targetFrameId, "SR ID查詢",data);
					}
				});
			});
			
			$("#cancelApplyBtn").click(function (){
				if(confirm("取消申請後將無法再修改資料，是否繼續？")){
					var workStatus = $("#siteHuntWorkStatus").val();
					if(workStatus != "W01" && workStatus != "W02" && workStatus != "W04"){
						alert("此處理狀態無法取消申請");
						return false;
					}
					$.ajax({
						url : CONTEXT_URL + "/st/st002/cancelApply",
						type : 'POST',
						data:{
							workId :  $("#workId").val(),
						},
						async : false,
						success : function(record) {
							var result = record['result'];
							if(result == true){
								var siteWork = record['siteWork'];
								alert('<s:message code="msg.cancelApply.success"/>');
								var data= {
										workType : $("#siteHuntWorkType").val(),
										srId : $("#siteHuntSrId").val(),
										siteWorkCity : $("#siteHuntCity").val(),
										siteWorkArea : $("#siteHuntArea").val(),
										btsSiteId : $("#siteHuntBtsSiteId").val(),
										siteName : $("#siteHuntSiteName").val(),
										locationId : $("#siteHuntLocationId").val(),
										siteFeq : $("#feqId").val().split(",")[0],
										workStatus : siteWork.work_STATUS,
										applyDept : siteWork.APP_DEPT,
								};
								$("#workType").val($("#siteHuntWorkType").val()).change();
								$("#siteWorkCity").val($("#siteHuntCity").val()).change();
								$("#siteWorkArea").val($("#siteHuntArea").val()).change();
								$("#siteWorkSiteName").val($("#siteHuntSiteName").val());
								$("#locationId").val($("#siteHuntLocationId").val());
								$("#siteFeq").val($("#feqId").val().split(",")[0]).change();
								$("#workStatus").val(siteWork.work_STATUS).change();
								$("#siteWorkSrId").val($("#siteHuntSrId").val());
								$("#siteWorkBtsSiteId").val($("#siteHuntBtsSiteId").val());
								$("#grid").setGridParam({page:1,datatype:"json", postData:data, mtype: 'POST'}).jqGrid().trigger("reloadGrid");
								$.fancybox.close();
							}else {
								alert(result);
							}
							
						}
					});
				}
			});//cancelApplyBtn end
			
			if("${status}" == "view"){
				$("#addSiteWorkForm :input").prop("disabled", true);
				$("#fileDownloadsBtn").show().prop("disabled", false);
			}
			
		});//document ready end 
		
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
		function siteCallBackFun(siteObjects){
			var obj = JSON.parse(siteObjects);
			if(obj.siteObjs[0].location_ID == null || obj.siteObjs[0].location_ID == ""){
				alert("無法選擇無站點的基站");
				return false;
			}
			var eqpTypeId = obj.siteObjs[0].eqp_TYPE_ID;
			if(eqpTypeId == "M" || eqpTypeId == "H" || eqpTypeId == "C"){
				alert("無法選擇機房");
				return false;
			}
			$("#showSitePage").dialog('close');
			$.ajax({
				url : CONTEXT_URL + "/st/st002/search/location",
				type : 'POST',
				data : {
					locationId :obj.siteObjs[0].location_ID,
				},
				async : false,
				success : function(record){
					var siteLoc = record['siteLoc'];
					var isMultibandLocation = record['isMultibandLocation'];
					var siteFeqMap = record['siteFeqMap'];
					if(isMultibandLocation >1){
						$("#multibandLocation").attr("checked",true);
					}
					$('#feqId').empty();
					$("<option value=''>---請選擇---</option>").appendTo("#feqId");
					for(var siteFeq in siteFeqMap){
						$("<option value='"+siteFeq+","+siteFeqMap[siteFeq].value+"'>"+siteFeqMap[siteFeq].label+"</option>").appendTo("#feqId");
					}
					$("#showsiteHuntLocationId").text(siteLoc.location_ID);
					$("#siteHuntLocationId").val(siteLoc.location_ID);
					$("#showLocName").text(siteLoc.loc_NAME);
					$("#locName").val(siteLoc.loc_NAME);
// 					$("#showSiteHuntSrId").text(siteLoc.sr_ID);
// 					$("#siteHuntSrId").val(siteLoc.sr_ID);
// 					if($("#siteHuntSrId").val() != ""){
// 						$("#siteHuntCity").val(siteLoc.city).change();
// 						$("#siteHuntArea").val(siteLoc.area).change();
// 						$("#siteHuntCity").prop("disabled",true);
// 						$("#siteHuntArea").prop("disabled",true);
// 						$.ajax({
// 							url : CONTEXT_URL + "/st/st002/search/searchRing",
// 							type : 'POST',
// 							async : false,
// 							data : {
// 								srId :$("#siteHuntSrId").val(),
// 							},
// 							async : false,
// 							success : function(data){
// 								$("#siteHuntLon").val(data.sr_LON);
// 								$("#siteHuntLat").val(data.sr_LAT);
// 								$("#coverRange").val(data.sr_COVER_RANGE);
// 								$("#siteHuntLon").prop("readonly",true);
// 								$("#siteHuntLat").prop("readonly",true);
// 								$("#coverRange").prop("readonly",true);
// 							}
// 						});
// 					}
					$("#siteHuntSiteName").val(siteLoc.loc_NAME);
				}					
	       	});
		}
		function donorSiteCallBackFun(siteObjects){
			var obj = JSON.parse(siteObjects);
			checkSiteIsOnAir("donorSite","Donor BTS必須選擇ON AIR的基站",obj);
		}
		function masterSiteCallBackFun(siteObjects){
			var obj = JSON.parse(siteObjects);
			checkSiteIsOnAir("masterSite","Master Site必須選擇ON AIR的基站",obj);
		}
		function checkSiteIsOnAir(tagName ,errorMessage ,obj){
			var eqpTypeId = obj.siteObjs[0].eqp_TYPE_ID;
			var status = obj.siteObjs[0].site_STATUS;
			if (status != "S06") {
				alert(errorMessage);
			}else{
				$("#"+tagName+"Text").val(obj.siteObjs[0].bts_SITE_ID);
				$("#"+tagName).val(obj.siteObjs[0].site_ID);
				$("#showSitePage").dialog('close');
			}
		}
		function srIdCallBackFun(srObject){
			var obj = JSON.parse(srObject);
			$("#siteHuntSrId").val(obj.sr_ID);
			$("#showSiteHuntSrId").text(obj.sr_ID);
			$("#siteHuntLon").val(obj.sr_LON).prop("readonly",true);
			$("#siteHuntLat").val(obj.sr_LAT).prop("readonly",true);
			$("#coverRange").val(obj.sr_COVER_RANGE).prop("readonly",true);
			$("#siteHuntCity").val(obj.sr_CITY).change().prop("disabled",true);
			$("#siteHuntArea").val(obj.sr_AREA).change().prop("disabled",true);
		}
		
		function save(){
			var resultMessage = "";
			if ($('#addSiteWorkForm')[0].checkValidity()) {
				event.preventDefault();
				var msg = dovalidate();
				if(msg != ""){
					return msg;
				}
				
				if($("#siteHuntApplyEvent").val() == "insert"){
					if(!confirm("儲存後工務類別無法修改，確定要儲存?")){
						return false;
					}
				}
				var attr ="";
				if($("#siteHuntCity").prop("disabled") && $("#siteHuntArea").prop("disabled")){
					attr += "&city="+$("#siteHuntCity").val()+"&area="+$("#siteHuntArea").val();
				}
				var orderIdArray = [];
				if($("#siteHuntApplyEvent").val() == "update"){
					attr+="&workType="+$("#siteHuntWorkType").val();
					$('#workOrderTable tbody').each(function () {
						var data = $('#workOrderTable').dataTable().fnGetData($(this).index()-1);
						orderIdArray [orderIdArray.length]= data['order_ID'];
					});
				}
// 				if(($("#siteHuntWorkStatus").val() == "W02" && $("#siteHuntWorkType").val() == "SH")|| "${isSHByNSRFinish}" == "Y"){
// 					$("#feqId").prop("disabled",false);
// 					$("#siteHuntEqpType").prop("disabled",false);
// 				}
				isSiteHuntByNSRFinish(false);
				$.ajax({
					url : CONTEXT_URL + "/st/st002/save",
					type : 'POST',
					async : false,
					data : $("#addSiteWorkForm").serialize()+"&siteHuntApplyEvent="+$("#siteHuntApplyEvent").val()+attr+"&orderIdArray="+orderIdArray,
					success : function(data){
						var result = data['result'];
						if(result == true){
							var siteWork = data['siteWork'];
							if($("#siteHuntApplyEvent").val() == "insert"){
								$("#workId").val(siteWork.work_ID);
								$("#siteHuntSrId").val(siteWork.sr_ID);
							}
							$("#siteHuntBtsSiteId").val(siteWork.bts_SITE_ID);
							resultMessage="success";
						}else {
							resultMessage = result;
// 							if(($("#siteHuntWorkStatus").val() == "W02" && $("#siteHuntWorkType").val() == "SH")|| "${isSHByNSRFinish}" == "Y"){
// 								$("#feqId").prop("disabled",true);
// 								$("#siteHuntEqpType").prop("disabled",true);
// 							}
							isSiteHuntByNSRFinish(true);
						}
					}					
 	       		});
			}
			return resultMessage;
		}//save end
		
		function disableBtsSiteIdText(){
			var siteFeq = $("#feqId").val();
			var siteFeqArray = siteFeq.split(",");
			if(siteFeqArray[1] == "L" && $("#siteHuntEqpType").val() == 2){
				$("#siteHuntBtsSiteId").prop("readonly",true).val("");
				$("#unuseBtsSiteIdBtn").prop("disabled",true);
			}else{
				$("#siteHuntBtsSiteId").prop("readonly",false);
				$("#unuseBtsSiteIdBtn").prop("disabled",false);
			}

		}
		function selectUnuseBTSSiteId(){
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
		}
		function unuseBtsSiteIdCallBackFun(object){
			
			var obj = JSON.parse(object);
			var errorMsg = "";
			if($("#siteHuntWorkStatus").val() == "W02"){
				if($("#unuseBtsSiteIdSiteFeq").val() != $("#feqId").val()){
					errorMsg += "可用基站編號查詢的基站頻段與申請的不符\n";
				}
				if($("#siteHuntEqpType").val() != $("#unuseBtsSiteIdEqpType").val()){
					errorMsg += "可用基站編號查詢的設備型態與申請的不符\n";
				}
				if(errorMsg != ""){
					alert(errorMsg);
					return;
				}
			}
			$("#feqId").val("").change();
			$("#siteHuntEqpType").val("").change();
			$("#siteHuntBtsSiteId").val(obj.bts_SITE_ID);
			$("#feqId").val($("#unuseBtsSiteIdSiteFeq").val()).change();
			$("#siteHuntEqpType").val($("#unuseBtsSiteIdEqpType").val()).change();
			$("#coverageType").val($("#unuseBtsSiteIdCoverageType").val()).change();
			if($("#siteHuntEqpType").val() == "5"){
				$("#feederless").val("R").change();	
			}
			
		}
		function initForm(){
// 			$("#addSiteWorkForm :input ").prop("disabled", false);
			var workStatus = $("#siteHuntWorkStatus").val();
			if(workStatus == ""){
				$("#siteHuntWorkStatus").val("W01");
				$("#showWorkStatus").text("草稿");
			}
			$("#siteHuntCity").prop("disabled",true);
			$("#siteHuntArea").prop("disabled",true);
			$("#multibandLocation").prop("disabled",true);
			$("#donorSiteText").prop("readonly",true);
			$("#masterSiteText").prop("readonly",true);
			$("#siteHuntLon").prop("readonly",true);
			$("#siteHuntLat").prop("readonly",true);
			$("#coverRange").prop("readonly",true);
			$("#fileDownloadsBtn").hide();
		}
		function getSiteFeq(feqType,tagName){
			$.ajax({
				url : CONTEXT_URL + "/st/st002/search/siteFeq",
				type : 'POST',
				data : {
					"feqType" :feqType,
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
		function dovalidate(status){
			var prediceEndDate = $("#prediceEndDate").val();
			var eqpType = $("#siteHuntEqpType").val();
			var feederless = $("#feederless").val();
			var errorMessages = [];
			var msg ="";
			if(prediceEndDate == "" ){
				errorMessages[errorMessages.length]="預定完成日必須選擇";
			}
			if($("#workDesc").val().length >100){
				errorMessages [errorMessages.length]="作業說明超過100個字";
			}
			if($("#siteHuntSrId").val() == ""){
				if($("#siteHuntWorkType").val() == "NSR"){
					//如果登入者不是系設,工務類別是NSR 可以不用SRID
					if($("#isSystemDesignDept").val() == "Y"){//Y 表示你是系設
						errorMessages[errorMessages.length]="必須選擇SR ID";
					}else{//如果登入者不是系設,工務類別是NSR,區域 ,涵蓋區域 ,經緯度必須輸入及檢查
						var siteHuntLon = $("#siteHuntLon").val();
						if(!validateNumber(siteHuntLon,3,6)){
							errorMessages [errorMessages.length]="經度格式錯誤，整數最多三位、小數最多為六位";
						}
						var siteHuntLat= $("#siteHuntLat").val();
						if(!validateNumber(siteHuntLat,3,6)){
							errorMessages [errorMessages.length]="緯度格式錯誤，整數最多三位、小數最多為六位";
						}
						var siteHuntMsg =  validateLatAndLon(siteHuntLon,siteHuntLat,"search");
						if(siteHuntMsg != "" && siteHuntMsg != null){
							errorMessages [errorMessages.length]=siteHuntMsg;
						}
					}
				}else{
					errorMessages[errorMessages.length]="必須選擇SR ID";
				}
			}
			if(eqpType == 1 || eqpType == 2){
				if(feederless == "R"){
					errorMessages[errorMessages.length]= "此設備型態Feederless無法選擇Remote"
				}
			}else if(eqpType == 3 || eqpType == 4){
				if(feederless != "N"){
					errorMessages[errorMessages.length]= "此設備型態Feederless必須是Normal"
				}
			}else{
				if(feederless != "R"){
					errorMessages[errorMessages.length]= "此設備型態Feederless必須是Remote"
				}
			}
			if(feederless == "R" && $("#masterSiteText").val()==""){
				errorMessages[errorMessages.length]= "Feederless為Remote必須選擇Master Site"
			}
			if(errorMessages.length!=0){
				for(var em in errorMessages){
					msg += errorMessages[em]+"\n";
				}
			}
			return msg;
		}
		function isSiteHuntByNSRFinish(booleanValue){
			if(($("#siteHuntWorkStatus").val() == "W02" && $("#siteHuntWorkType").val() == "SH")|| "${isSHByNSRFinish}" == "Y"){
				$("#feqId").prop("disabled",booleanValue);
				$("#siteHuntEqpType").prop("disabled",booleanValue);
			}
		}
		</script>
	</head>
	<body>
		<div class="row">
			<div class="col-xs-12 col-sm-12">
				<div class=" ui-draggable ui-droppable">
					<div class="box-header">
						<div class="box-name">
							<i class="fa fa-search"></i> <span>尋點作業申請</span>
						</div>
						<div class="no-move"></div>
					</div>
					<div class="box-content">
						<div class="box-content">
							<form class="form-horizontal" id="addSiteWorkForm" action="" >
								<jsp:include page="/WEB-INF/views/ajaxPage/st/SiteHuntApply.jsp"></jsp:include>
								<input id="siteCallBackFun" value="siteCallBackFun" type="hidden"></input>
								<input id="donorSiteCallBackFun" value="donorSiteCallBackFun" type="hidden"></input>
								<input id="masterSiteCallBackFun" value="masterSiteCallBackFun" type="hidden"></input>
								<input id="siteHuntApplyEvent"  type="hidden"></input>
								<input id="srIdCallBackFun" value="srIdCallBackFun" type="hidden"></input>
								<input id="unuseBtsSiteIdCallBackFun" value="unuseBtsSiteIdCallBackFun" type="hidden"></input>
								<input id="isSystemDesignDept"  type="hidden"></input>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="showSitePage"></div>
		<div id="showFileUploadPage"></div>
		<div id="showSrIdPage"></div>
		<div id="showUnuseBtsSiteIdPage"></div>
		<div id="showFileDownloadPage"></div>
	</body>
</html>