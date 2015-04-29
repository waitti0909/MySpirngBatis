<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>建站作業申請</title>
<script type="text/javascript">
$(document).ready(function() {
	$("#showWorkType").text("${workTypeName}");
	$("#siteBuildApplyEvent").val("${siteBuildApplyEvent}");
	$("#fileDownloadsBtn").hide();
	//作業說明
    <c:set var="st004M_workDesc" value="${siteWork.WORK_DESC}" scope="request"></c:set>
	
	//檔案上傳事件
	$("#fileProcessBtn").click(function() {
		openFileDialogFrame("showFileUploadPage",$("#siteBuildWorkType").val(),$("#workId").val(),"NOTYPE");
	});
	$("#fileDownloadsBtn").click(function() {
		openFileDownFrame("showFileUploadPage",$("#siteBuildWorkType").val(),$("#workId").val(),"NOTYPE");
	});
	//更新地址事件
	$("#updateAddrButton").click(function() {
		var siteLocAddr = {
		"zip" : $("#zip").val(),
		"city" : $("#city").val(),
		"area" : $("#area").val(),
		"village" : $("#village").val(),
		"road" : $("#road").val(),
		"adjacent" : $("#adjacent").val(),
		"lane" : $("#lane").val(),
		"alley" : $("#alley").val(),
		"subAlley" : $("#subAlley").val(),
		"doorNo" : $("#doorNo").val(),
		"doorNoEx" : $("#doorNoEx").val(),
     	"floor" : $("#floor").val(),
		"floorEx" : $("#floorEx").val(),
		"room" : $("#room").val(),
		"remark" : $("#addOther").val(),
		"disabledFields" : $("#disabledFields").val(),
		"callBackFun" : $("#siteLocationCallBackFun").val()
		};
		 openAddressDialogFrame("showSiteLocAddrPage",siteLocAddr);
	});//更新地址事件  end

	//Donor BTS選擇事件
	$("#donorSiteBtn").click(function() {
		openSiteDialogFrame("showSitePage", $("#donorSiteCallBackFun").val(),undefined,undefined,"close","site");
	});

	//Maste Site選擇事件
	$("#masterSiteBtn").click(function() {
		if($("#feederless").val()=='R'){
			openSiteDialogFrame("showSitePage", $("#masterSiteCallBackFun").val(),undefined,undefined,"close","site");	
		}else{
			alert("Feederless為Remote才可以選擇Master Site");
		}
	});

	//基站編號選擇事件
	$("#unuseBtsSiteIdBtn").click(function() {
		if($("#siteBuildWorkType").val() != ""){
			openSiteDialogFrame("showSitePage", $("#siteCallBackFun").val(),undefined,undefined,"close");		
		}else{
			alert("請先選擇工務類別");
		}
		
	});

	//當工程類別選項換時， 連動委外廠商
	$("#osType").change(function() {
		var osType = $("#osType").val();
		// 將委外廠商下拉設為原始狀態
		$("#osVen").prop("required", false);
		$("#osRequireMark").removeClass("s").html("");
		
		if(osType == ""){
			$("#osVen  option").remove();
			$("#osVen").append("<option value=''>---請選擇---</option>");
			return false;
		} else if (osType == "T") {	// 若工程類別為統包時廠商必選
			$("#osVen").prop("required", "required");
			$("#osRequireMark").addClass("s").html("*");
		}
		$.ajax({
			url : CONTEXT_URL+ "/st/st004/search/searchOsVen",
			type : "POST",
			dataType : 'json',
			data : {
				"osType" : osType,
			},
			async : false,
			success : function(data) {
// 			alert(JSON.stringify(data));
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

	//設備型態連動事件
	$("#siteBuildEqpType").change(function() {
		//取的設備機型清單
		getEqpModel($("#siteBuildEqpType").val(),"siteBuildEqpModel");
		
		
		var eqpType = $("#siteBuildEqpType").val();

		if (eqpType == 3 || eqpType == 4) {
		$("#btsConfig").prop("readonly", true).val("");
		$("#donorSiteBtn").prop("disabled", false);
		} else if (eqpType == 1 || eqpType == 2) {
		$("#btsConfig").prop("readonly", false);
		$("#donorSiteBtn").prop("disabled", true);
		}
	});

	//feederless連動事件
	$("#feederless").change(function() {
		var feederless = $("#feederless").val();
		if (feederless != 'R') {
			$("#masterSiteText").prop("required", false);
		}
	});

	if ($("#siteBuildApplyEvent").val() == "new") {
		$("#cancelApplyBtn").prop("disabled", true);
	}

	//存檔按鈕事件
	$("#siteWorkSaveBtn").click(function(){
		if ($('#addSiteWorkForm')[0].checkValidity()) {
			event.preventDefault();
			if(!confirm("確定要儲存?")){
				return false;
			}
			var orderId="";
			if($("#siteBuildApplyEvent").val() == "edit"){
				orderId=$("#checkBoxItemId").val();
			}
			var errorMessages = doValidate(orderId);
			var msg ="";
			if(errorMessages.length!=0){
				for(var em in errorMessages){
					msg += errorMessages[em]+"\n";
				}
			}
			if(msg != ""){
				alert(msg);
				return false;
			}
			saveEnableTag();
			var attr="";
			if($("#siteBuildEqpType").val() == null){
				attr = "&eqpTypeId="+$("#siteCallbackFunEqpType").val();
			}
			$.ajax({
				url : CONTEXT_URL + "/st/st004/save",
				type : 'POST',
				async : false,
				data : $("#addSiteWorkForm").serialize()+"&siteBuildApplyEvent="+$("#siteBuildApplyEvent").val()+"&orderId="+orderId+attr,
				success : function(data){
					$("#feqId").prop("disabled", true);
					$("#siteBuildEqpType").prop("disabled", true);	
					$("#feederless").prop("disabled", true);
					$("#coverageType").prop("disabled", true);
					var result = data['result'];
					if(result == true){
						if($("#siteBuildApplyEvent").val() == "new"){
							var siteWork = data['siteWork'];
							$("#workId").val(siteWork.work_ID);
							$("#siteBuildBtsSiteId").val(siteWork.bts_SITE_ID);
							alert('<s:message code="msg.add.success"/>' + '，請勾選欲施作的作業工單後送出申請');
							updateSiteWork($("#workId").val(), "edit");
						}else{
							alert('<s:message code="msg.update.success"/>');
							var data= {
								    workType : $.trim($("#siteBuildWorkType").val()), //工務類別
									applyDept : "", //申請單位
									siteWorkCity : $.trim($("#city").val()), //行政區 
									siteWorkArea :"", //行政區 
									eqpTypeId : $.trim($("#siteBuildEqpType").val()), //設備型態
									btsSiteId : $.trim($("#siteBuildBtsSiteId").val()), //基站編號 
									siteName : $.trim($("#siteBuildSiteName").val()), //基站名稱
									workStatus :$("#siteBuildWorkStatus").val(), //處理狀態
									siteFeq : $.trim($("#siteFeq").val()),
									rangeBa:"",
									rangeBb:""
							};	
							$('#workType').val($("#siteBuildWorkType").val()).change();
							$('#siteWorkCity').val($("#city").val()).change();
							$('#siteWorkArea').val($("#area").val()).change();
							$('#siteFeq').val($("#feqId").val()).change();
							$('#workStatus').val($("#siteBuildWorkStatus").val()).change();
							$("#siteWorkBtsSiteId").val($("#siteBuildBtsSiteId").val());
							$("#siteWorkSiteName").val($("#siteBuildSiteName").val());
							$('#eqpTypeId').val($("#siteBuildEqpType").val()).change();
							$("#grid").setGridParam({page:1 ,datatype:"json", postData:data, mtype: 'POST'}).jqGrid().trigger("reloadGrid");
							$.fancybox.close();
						}
					}else {
						alert(result);
					}
				}					
	       	});
		}
	});//存檔按鈕事件  end


	//申請按鈕事件
	$("#siteBuildApplyBtn").click(function(){
		
		if ($('#addSiteWorkForm')[0].checkValidity()) {
			event.preventDefault();
			
			var orderId="";
			if($("#siteBuildApplyEvent").val() == "edit"){
				orderId = $("#checkBoxItemId").val();
			}
			var errorMessages = doValidate(orderId);
			var msg ="";
			if(errorMessages.length!=0){
				for(var em in errorMessages){
					msg += errorMessages[em]+"\n";
				}
			}
			if(msg != ""){
				alert(msg);
				return false;
			}
			
			swal({   
				title: "Confirm",   
				text: "勾選申請的工單為：<br><FONT COLOR = '#D76A70'>" + $("#checkBoxItemName").val() + "</font><br>送出申請後將無法再修改資料，是否繼續？",   
// 				type: "input",   
				showCancelButton: true,   
// 				confirmButtonColor: "green",   
				confirmButtonText: "確定", 
				cancelButtonText: "取消",
				html: true,
				closeOnConfirm: false 
				}, 
				function(isConfirm){   
					if (isConfirm) {
						saveEnableTag();
						var attr="";
						if($("#siteBuildEqpType").val() == null){
							attr = "&eqpTypeId="+$("#siteCallbackFunEqpType").val();
						}
						 $.ajax({
							 url : CONTEXT_URL + "/st/st004/updateApply",
							 type : 'POST',
							 async : false,
							 data: $("#addSiteWorkForm").serialize()+"&orderId="+orderId+attr,
							 success : function(datas){
							 saveDisableTag();
							 var result = datas['result'];
							 if(result == true){
							   var siteWork = datas['siteWork'];
							   swal("Success", "申請完成！", "success"); 
// 							   alert('<s:message code="msg.apply.success"/>');
							   $("#workStatus").val(siteWork.work_STATUS).change();
							   $("#applyDept").val(siteWork.app_DEPT).change();
							   var data= {
									    workType : $.trim($("#siteBuildWorkType").val()), //工務類別
										applyDept : "", //申請單位
										siteWorkCity : $.trim($("#city").val()), //行政區 
										siteWorkArea : "", //行政區 
										eqpTypeId : $.trim($("#siteBuildEqpType").val()), //設備型態 
										btsSiteId : $.trim($("#siteBuildBtsSiteId").val()), //基站編號 
										siteName : $.trim($("#siteBuildSiteName").val()), //基站名稱
										workStatus :siteWork.work_STATUS, //處理狀態
										siteFeq : $.trim($("#feqId").val()),
										rangeBa:"",
										rangeBb:""
									     };
								$('#workType').val($("#siteBuildWorkType").val()).change();
								$('#siteWorkCity').val($("#city").val()).change();
						// 		$('#siteWorkArea').val($("#area").val()).change();;
								$('#siteFeq').val($("#feqId").val()).change();
								$('#workStatus').val(siteWork.work_STATUS).change();
								$("#siteWorkBtsSiteId").val($("#siteBuildBtsSiteId").val());
								$("#siteWorkSiteName").val($("#siteBuildSiteName").val());
								$("#grid").setGridParam({page:1 ,datatype:"json", postData:data, mtype: 'POST'}).jqGrid().trigger("reloadGrid");
								$.fancybox.close();
							}else{
// 								alert(result);
								swal("Failed", result, "error"); 
							}
						   }					
						});
					}
				}
			);	// sweet alert end
		}
  });//申請按鈕事件  end

  //取消申請事件
		$("#cancelApplyBtn").click(function (){
			if(confirm("取消申請後將無法再修改資料，是否繼續？")){
				var workStatus = $("#siteBuildWorkStatus").val();
				if(workStatus != "W01" && workStatus != "W04"){
					alert("此處理狀態無法取消申請");
					return false;
				}
		$.ajax({
			url : CONTEXT_URL + "/st/st004/cancelApply",
			type : 'POST',
			data:{
					workId :  $("#workId").val()
			},
			async : false,
			success : function(record) {
			var result = record['result'];
			if(result == true){
			var siteWork = record['siteWork'];
			alert('<s:message code="msg.cancelApply.success"/>');
			var data= {
				    workType : $.trim($("#siteBuildWorkType").val()), //工務類別
					applyDept :siteWork.app_DEPT, //申請單位
					siteWorkCity : $.trim($("#city").val()), //行政區 
					siteWorkArea : "", //行政區 
					eqpTypeId : $.trim($("#siteBuildEqpType").val()), //設備型態 
					btsSiteId : $.trim($("#siteBuildBtsSiteId").val()), //基站編號 
					siteName : $.trim($("#siteBuildSiteName").val()), //基站名稱
					workStatus :siteWork.work_STATUS, //處理狀態
					siteFeq : $.trim($("#siteFeq").val()),
					rangeBa:"",
					rangeBb:""
				
					};
			$('#workType').val($("#siteBuildWorkType").val()).change();
			$('#siteWorkCity').val($("#city").val()).change();
// 			$('#siteWorkArea').val($("#area").val()).change();
			$('#siteFeq').val($("#feqId").val()).change();
			$('#workStatus').val(siteWork.work_STATUS).change();
			$("#siteWorkBtsSiteId").val($("#siteBuildBtsSiteId").val());
			$("#siteWorkSiteName").val($("#siteBuildSiteName").val());
			$("#grid").setGridParam({page:1,datatype:"json", postData:data, mtype: 'POST'}).jqGrid().trigger("reloadGrid");
			$.fancybox.close();
		}else {
				alert(result);
		       }

		}
	});
	}
	});//取消申請事件 end
	
	//欄位初始狀態
	initState();
// 	itemBoxClick();
	
	if("${status}" == "view"){
		$("#siteBuildApplyEvent").val("${status}");
		$("#addSiteWorkForm :input").prop("disabled", true);
		$("#siteBuildApplyBtnsTr").hide();
		$("#fileDownloadsBtn").show().prop("disabled",false);
// 		$("#addSiteWorkForm :select").prop("disabled", true);
// 		$("#addSiteWorkForm :check").prop("disabled", true);
	}
});//document ready end 

					
   //地址回傳事件
   function siteLocationCallBackFun(addressObject) {
			var obj = JSON.parse(addressObject);
			$("#zip").val(obj.zip);
			$("#city").val(obj.city);
			$("#area").val(obj.area);
			$("#village").val(obj.village);
			$("#road").val(obj.road);
			$("#adjacent").val(obj.adjacent);
			$("#lane").val(obj.lane);
			$("#alley").val(obj.alley);
			$("#subAlley").val(obj.subAlley);
			$("#doorNo").val(obj.doorNo);
			$("#doorNoEx").val(obj.doorNoEx);
			$("#floor").val(obj.floor);
			$("#floorEx").val(obj.floorEx);
			$("#room").val(obj.room);
			$("#addOther").val(obj.remark);
			$.ajax({
					url : CONTEXT_URL+ "/common/address/combineAddress",
						  dataType : "json",
						  data : addressObject,
						  contentType : 'application/json',
						  type : "POST",
						  async : false,
						  success : function(data) {
							    $("#addr").val(data.row.fullAddress);
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

	//站點查詢回應事件
	function siteCallBackFun(siteObjects) {
		
		var obj = JSON.parse(siteObjects);
		if (obj.siteObjs[0].location_ID == null	|| obj.siteObjs[0].location_ID == "") {
			alert("無法選擇無站點的基站");
			return false;
		} else if (obj.siteObjs[0].site_STATUS != "S04") {
			alert('請選擇待建設狀態之站台');
			return false;
		}
		var workType=$("#siteBuildWorkType").val();
		var eqpType=obj.siteObjs[0].eqp_TYPE_ID;
		var result = workTypeRuleValidate(workType,eqpType);
		if(result != ""){
			alert(result);
			return false;
		}
		$("#siteCallbackFunEqpType").val(eqpType);
		//查詢站點資料
		$.ajax({
			url : CONTEXT_URL + "/st/st004/search/location",
			type : 'POST',
			data : {
				locationId : obj.siteObjs[0].location_ID,
			},
			async : false,
			success : function(record) {
				$("#showSitePage").dialog('close');
				var siteLoc = record['siteLoc'];
				var isMultibandLocation = record['isMultibandLocation'];
				//站點編號
				$("#showsiteBuildLocationId").text(siteLoc.location_ID);
				$("#siteBuildLocationId").val(siteLoc.location_ID);
				//站點名稱
				$("#showLocName").text(siteLoc.loc_NAME);
				$("#locName").val(siteLoc.loc_NAME);
				
				//多頻段站點
				if (isMultibandLocation > 1) {
					$("#multibandLocation").attr("checked", true);
				}
				
			    //地址欄位
			    $("#addr").val(siteLoc.addr);
				$("#city").val(siteLoc.city);
				$("#area").val(siteLoc.area);
				$("#zip").val(siteLoc.zip);
				$("#village").val(siteLoc.village);
				$("#road").val(siteLoc.road);
				$("#adjacent").val(siteLoc.adjacent);
				$("#lane").val(siteLoc.lane);
				$("#alley").val(siteLoc.alley);
				$("#subAlley").val(siteLoc.sub_ALLEY);
				$("#doorNo").val(siteLoc.door_NO);
				$("#doorNoEx").val(siteLoc.door_NO_EX);
				$("#floor").val(siteLoc.floor);
				$("#floorEx").val(siteLoc.floor_EX);
				$("#room").val(siteLoc.room);
				$("#addOther").val(siteLoc.add_OTHER);
				
				//經度
				$("#siteBuildLon").val(siteLoc.lon);
				//緯度
				$("#siteBuildLat").val(siteLoc.lat);
				
				//基站編號
				$("#siteBuildBtsSiteId").val(obj.siteObjs[0].bts_SITE_ID);
				//基站名稱
				$("#siteBuildSiteName").val(siteLoc.loc_NAME);
				
				//查詢基站主檔
				$.ajax({
					url : CONTEXT_URL + "/st/st004/search/site",
					type : 'POST',
					data : {
						siteId : obj.siteObjs[0].site_ID,
					},
					async : false,
					success : function(record) {
						//site_id
						$("#siteBuildSiteId").val(record.site_ID);
						//建站目地
						var purpose = record.purpose;
						if(purpose != "" && purpose != null){
							$("#purpose").val(purpose).change();
						}
						//基站頻率
						$("#feqId").prop("disabled", false);
						$("#feqId").val(record.feq_ID).change();
						$("#feqId").prop("disabled", true);
						//設備型態
						var eqpType= record.eqp_TYPE_ID;
						$("#siteBuildEqpType").prop("disabled", false);
						$("#siteBuildEqpType").val(eqpType).change();
						$("#siteBuildEqpType").prop("disabled", true);
						//設備
						$("#siteBuildEqpModel").val(record.eqp_MODEL_ID).change();	
						//涵蓋類別
					    $("#coverageType").val(record.coverage_TYPE).change().prop("disabled", true);
						//Configuration
						$("#btsConfig").val(record.bts_CONFIG);
 						//Donor BTS 
						$("#donorSiteText").val(record.donor_SITE);
						$("#donorSite").val(record.donor_SITE);
						//Feederless
						$("#feederless").val(record.feederless).change().prop("disabled", true);
						//Master Site
						$("#masterSiteText").val(record.master_SITE);
						$("#masterSite").val(record.master_SITE);
					}
				});
			}
		});
	}

	//欄位初始狀態
	function initState() {
		$("#multibandLocation").prop("disabled", true);
		$("#siteBuildBtsSiteId").prop("readonly", true);
		$("#siteBuildSiteName").prop("readonly", true);
		$("#feqId").prop("disabled", true);
		$("#siteBuildEqpType").prop("disabled", true);		
		$("#masterSiteText").prop("readonly", true);
		$("#donorSiteText").prop("readonly", true);
		$("#addr").prop("readonly", true);
		$("#siteBuildLon").prop("readonly", true);
		$("#siteBuildLat").prop("readonly", true);
		$("#updateAddrButton").prop("disabled", true);
		var workStatus = $("#showWorkStatus").text();
		if (workStatus == "") {
			$("#siteBuildWorkStatus").val("W01");
			$("#showWorkStatus").text("草稿");
		}

		//編輯時
		if ($("#siteBuildApplyEvent").val() == "edit") {
			
            $("#siteBuildSiteId").val("${siteWork.SITE_ID}");
			//工單編號欄位
			$("#showWorkId").text("${siteWork.WORK_ID}");
			$("#workId").val("${siteWork.WORK_ID}");
			//工務類別
			$("#siteBuildWorkType").val("${siteWork.WORK_TYPE}").change().prop("disabled", true);
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

			$("#purpose").val("${siteWork.PURPOSE}").change();
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
			$("#subAlley").val("${siteWork.SUB_ALLEY}");
			$("#doorNo").val("${siteWork.DOOR_NO}");
			$("#doorNoEx").val("${siteWork.DOOR_NO_EX}");
			$("#floor").val("${siteWork.FLOOR}");
			$("#floorEx").val("${siteWork.FLOOR_EX}");
			$("#room").val("${siteWork.ROOM}");
			$("#addOther").val("${siteWork.ADD_OTHER}");
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
			$("#siteBuildEqpType").val("${siteWork.EQP_TYPE_ID}").change();
			$("#siteBuildEqpType").prop("readonly", true);
			getEqpModel($("#siteBuildEqpType").val(),"siteBuildEqpModel");
			//設備機型
			$("#siteBuildEqpModel").val("${siteWork.EQP_MODEL_ID}").change();
			//涵蓋類別
			$("#coverageType").val("${siteWork.COVERAGE_TYPE}").change().prop("disabled", true);
			//Configuration
			$("#btsConfig").val("${siteWork.BTS_CONFIG}");
			//Donor BTS 
			$("#donorSiteText").val("${donorBtsSiteId}");
			$("#donorSite").val("${siteWork.DONOR_SITE}");
			//Feederless
			$("#feederless").val("${siteWork.FEEDERLESS}").change().prop("disabled", true);
			//Master Site
			$("#masterSiteText").val("${masterBtsSiteId}");
			$("#masterSite").val("${siteWork.MASTER_SITE}");
			//預計完工日
			$("#prediceEndDate").val("${prediceEndDate}");
			//完工日期
			$("#endTime").val("${siteWork.END_TIME}");
			//工單
			$("#checkBoxItemId").val("${orderIdStr}");
			//eqpType
			$("#siteCallbackFunEqpType").val("${siteWork.EQP_TYPE_ID}");
			$("#cplNo").val("${siteWork.cplNo}");
			$("#permitNo").val("${siteWork.permitNo}");
			$("#licenseNo").val("${siteWork.licenseNo}");
		} else {
			//作業附件按鈕
			$("#fileProcessBtn").prop("disabled", true);
			//申請按鈕
			$("#siteBuildApplyBtn").prop("disabled", true);
		}
	}
	
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
	function saveEnableTag(){
		$("#feqId").prop("disabled", false);
		$("#siteBuildEqpType").prop("disabled", false);	
		$("#siteBuildWorkType").prop("disabled", false);
		$("#feederless").prop("disabled", false);
		$("#coverageType").prop("disabled", false);
	}
	function saveDisableTag(){
		$("#feqId").prop("disabled", true);
		$("#siteBuildEqpType").prop("disabled", true);	
		$("#siteBuildWorkType").prop("disabled", true);
		$("#feederless").prop("disabled", true);
		$("#coverageType").prop("disabled", true);
	}
	//工單選取
	function itemBoxClick() {
		var allData="", checkedOrders = "";
		$('input:checkbox:checked[name="itemBox"]').each(function(i) {
			allData+=this.value+",";
			checkedOrders += $("#itemName" + this.value).val() + "<br>";
		});
		allData = allData.substring(0, allData.length-1);
		$("#checkBoxItemId").val(allData);
		$("#checkBoxItemName").val(checkedOrders);
	}
	
	function workTypeRuleValidate(workType,eqpType){
		var errorMsg="";
		var workTypeName = $("#siteBuildWorkType").find(":selected").text();
		if(workType == "SBH"){
			if(eqpType!="H"){
				errorMsg="工務類別為"+workTypeName+"無法選擇此設備類型的基站";
			}
		}else if(workType == "SBN"){
			if(eqpType!="C"){
				errorMsg="工務類別為"+workTypeName+"無法選擇此設備類型的基站";
			}
		}else{
			if(eqpType=="H" || eqpType == "C"){
				errorMsg="工務類別為"+workTypeName+"無法選擇此設備類型的基站";
			}
		}
		return errorMsg;
	}
	
	function doValidate(orderId){
		//預計完工日 
		var prediceEndDate = $("#prediceEndDate").val();
		var errorMessages = [];
		//檢核預計完工日 
		if(prediceEndDate == "" ){
			errorMessages[errorMessages.length]="預計完工日必須選擇";
		}
		//檢核作業說明字數
		if($("#workDesc").val().length >100){
			errorMessages [errorMessages.length]="作業說明超過100個字";
		}
		//工務類別與基站設備型態檢核
		var workType=$("#siteBuildWorkType").val();
		var eqpType=$("#siteCallbackFunEqpType").val();
		var result = workTypeRuleValidate(workType,eqpType);
		if(result != ""){
			errorMessages [errorMessages.length]=result;
		}
		var lon = $("#siteBuildLon").val();
		if(lon != ""){
			if(!validateNumber(lon,3,6)){
				errorMessages [errorMessages.length]="經度格式錯誤，整數最多三位、小數最多為六位";
			}
		}else{
			errorMessages [errorMessages.length]="經度不可為空白";
		}
		var lat = $("#siteBuildLat").val();
		if(lat != ""){
			if(!validateNumber(lat,3,6)){
				errorMessages [errorMessages.length]="緯度格式錯誤，整數最多三位、小數最多為六位";
			}
		}else{
			errorMessages [errorMessages.length]="緯度不可為空白";
		}
		if(lat != "" && lon != ""){
			var msg =  validateLatAndLon(lon,lat,"");
		}
		
		
		if($("#feederless").val() == "R" && $("#masterSiteText").val()==""){
			errorMessages[errorMessages.length]= "Feederless為Remote必須選擇Master Site"
		}
		//檢核地址 
		if($("#addr").val() == "" ){
			errorMessages[errorMessages.length]="地址必須填寫";
		}
		//檢核工單是否勾選
		if($("#siteBuildApplyEvent").val() == "edit"){
			if(orderId == ""){
				errorMessages[errorMessages.length]="至少要勾選一筆工單\n";
			}
		}
		
		if(msg != "" && msg != null){
			errorMessages [errorMessages.length]=msg;
		}
		return  errorMessages;
	}
	
</script>
</head>
<body>
	<div class="row">
		<div class="col-xs-12 col-sm-12">
			<div class=" ui-draggable ui-droppable">
				<div class="box-header">
					<div class="box-name">
						<i class="fa fa-search"></i> <span>建站作業申請</span>
					</div>
					<div class="no-move"></div>
				</div>
				<div class="box-content">
					<div class="box-content">
						<form class="form-horizontal" id="addSiteWorkForm" action="">
							<jsp:include page="/WEB-INF/views/ajaxPage/st/SiteBuildApply.jsp"></jsp:include>
							<input id="siteCallBackFun" value="siteCallBackFun" type="hidden"></input>
							<input id="donorSiteCallBackFun" value="donorSiteCallBackFun" type="hidden"></input>
							<input id="masterSiteCallBackFun"	value="masterSiteCallBackFun" type="hidden"></input>
							<input id="siteBuildApplyEvent"  type="hidden"></input>
							<input id="siteCallbackFunEqpType"  type="hidden"></input>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="showSitePage"></div>
	<div id="showFileUploadPage"></div>
	<div id="showSiteLocAddrPage"></div>
</body>
</html>