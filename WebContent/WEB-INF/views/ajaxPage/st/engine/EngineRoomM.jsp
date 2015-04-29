<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<title>機房資訊維護</title>
		
		<script type="text/javascript">
		$(document).ready(function() {
			buildCityOptions("engineRoomCity","engineRoomArea");
			$("#engineRoomStatus_select").hide();
			$("#engineRoomType").change(function(){
				engineRoomStatusRule();
			});
			
			if("${eventType}" == "view"){
				$("#addEngineRoomForm :input ").prop("disabled", true);
			}
			$("#engineRoomArea").change(function(){
				cleanTagValue("maintArea");
				cleanTagValue("maintDept");
				cleanTagValue("maintTeam");
				cleanTagValue("maintUser");
				if($("#engineRoomArea").val() != ""){
					getMaintainArea($("#engineRoomCity").val(),$("#engineRoomArea").val(),"maintArea");
				}
			});
			$("#maintArea").change(function(){
				cleanTagValue("maintDept");
				cleanTagValue("maintTeam");
				cleanTagValue("maintUser");
				if($("#maintArea").val() != ""){
					getMaintainDept($("#maintArea").val(),"maintDept");
				}
			});
			$("#maintDept").change(function(){
				cleanTagValue("maintTeam");
				cleanTagValue("maintUser");
				if($("#maintDept").val() != ""){
					getMaintainTeam($("#maintDept").val(),"maintTeam");
				}
			});
			$("#maintTeam").change(function(){
				cleanTagValue("maintUser");
				if($("#maintTeam").val() != ""){
					getMaintainUser($("#maintTeam").val(),"maintUser");
				}
			});
			$("#engineRoomArea").change(function(){
				$("#engineRoomAddr").val("");
			});
			$("#fileProcessBtn").click(function(){
				openFileDialogFrame("showFileUploadPage", "SITE", $("#engineRoomSiteId").val(), "NOTYPE");	// modify by Charlie 以站台識別碼為KEY
			});
			if($("#engineRoomEvent").val() == "engineRoomUpdate"){
				$("#showLocationId").text("${location.LOCATION_ID}");
				$("#engineRoomLocationId").val("${location.LOCATION_ID}");
				$("#showBtsSiteId").text("${site.BTS_SITE_ID}");
				$("#engineRoomBtsSiteId").val("${site.BTS_SITE_ID}");
				$("#engineRoomSiteId").val("${site.SITE_ID}");
				$("#engineRoomType").val("${location.LOC_TYPE}").change();
				engineRoomStatusRule();
				if($("#engineRoomType").val() == "M"){
					$("#engineRoomStatus_select").val("${site.SITE_STATUS}").change();
				}else{
					$("#showEngineRoomStatus").text("${siteStatusName}");
				}
				$("#engineRoomStatus").val("${site.SITE_STATUS}");
// 				if($("#engineRoomStatus").val() == "S06"){
// 					$("#engineRoomLon").prop("readonly",true);
// 					$("#engineRoomLat").prop("readonly",true);
// 					$("#updateAddrButton").prop("disabled",true);
// 				}
				$("#engineRoomName").val("${location.LOC_NAME}");
				$("#engineRoomBaseType").val("${location.BASE_TYPE}").change();
				$("#indoorOption").val("${location.INDOOR_OPTION}").change();
				$("#engineRoomCity").val("${location.CITY}").change();
				$("#engineRoomArea").val("${location.AREA}").change();
				$("#engineRoomLon").val("${location.LON}");
				$("#engineRoomLat").val("${location.LAT}");
				$("#engineRoomAddr").val("${location.ADDR}");
				$("#engineRoomZip").val("${location.ZIP}");
				$("#engineRoomVillage").val("${location.VILLAGE}");
				$("#engineRoomRoad").val("${location.ROAD}");
				$("#engineRoomAdjacent").val("${location.ADJACENT}");
				$("#engineRoomLane").val("${location.LANE}");
				$("#engineRoomAlley").val("${location.ALLEY}");
				$("#engineRoomDoorNo").val("${location.DOOR_NO}");
				$("#engineRoomDoorNoEx").val("${location.DOOR_NO_EX}");
				$("#engineRoomFloor").val("${location.FLOOR}");
				$("#engineRoomFloorEx").val("${location.FLOOR_EX}");
				$("#engineRoomRoom").val("${location.ROOM}");
				$("#engineRoomAddOther").val("${location.ADD_OTHER}");
				$("#maintArea").val("${location.MAINT_AREA}").change();
				$("#maintDept").val("${location.MAINT_DEPT}").change();
				$("#maintTeam").val("${location.MAINT_TEAM}").change();
				$("#maintUser").val("${location.MAINT_USER}").change();
				$("#nsLevel").val("${location.NS_LEVEL}");
				$("#buildingHeight").val("${location.BUILDING_HEIGHT}");
				$("#footage").val("${location.FOOTAGE}");
				$("#empRelation").val("${location.EMP_RELATION}");
				<c:set var="engineRoomDesc" value="${location.LOC_DESC}" scope="request"></c:set>
				$("#owner").val("${location.OWNER}");
				$("#cntPsn").val("${location.CNT_PSN}");
				$("#cntTel").val("${location.CNT_TEL}");
				<fmt:formatDate value="${site.CONSTR_KO_DATE}" pattern="yyyy/MM/dd" var="constrKoDate"/>
				$("#constrKoDate").val("${constrKoDate}");
				<fmt:formatDate value="${site.CONSTR_DONE_DATE}" pattern="yyyy/MM/dd" var="constrDoneDate"/>
				$("#constrDoneDate").val("${constrDoneDate}");
				<fmt:formatDate value="${site.SMR_READY_DATE}" pattern="yyyy/MM/dd" var="smrReadyDate"/>
				$("#smrReadyDate").val("${smrReadyDate}");
				<fmt:formatDate value="${site.UPS_READY_DATE}" pattern="yyyy/MM/dd" var="upsReadyDate"/>
				$("#upsReadyDate").val("${upsReadyDate}");
				<fmt:formatDate value="${site.AC_READY_DATE}" pattern="yyyy/MM/dd" var="acReadyDate"/>
				$("#acReadyDate").val("${acReadyDate}");
				<fmt:formatDate value="${site.DYNAMO_READY_DATE}" pattern="yyyy/MM/dd" var="dynamoReadyDate"/>
				$("#dynamoReadyDate").val("${dynamoReadyDate}");
				<fmt:formatDate value="${site.ADM_READY_DATE}" pattern="yyyy/MM/dd" var="admReadyDate"/>
				$("#admReadyDate").val("${admReadyDate}");
				<fmt:formatDate value="${site.POWER_READY_DATE}" pattern="yyyy/MM/dd" var="powerReadyDate"/>
				$("#powerReadyDate").val("${powerReadyDate}");
				<fmt:formatDate value="${site.TRANS_READY_DATE}" pattern="yyyy/MM/dd" var="transReadyDate"/>
				$("#transReadyDate").val("${transReadyDate}");
				isChecked("spaceRoom","${location.SPACE_ROOM}");
				isChecked("spaceRoof","${location.SPACE_ROOF}");
				isChecked("spaceTop","${location.SPACE_TOP}");
				isChecked("spacePlatform","${location.SPACE_PLATFORM}");
				isChecked("spaceInoor","${location.SPACE_INDOOR}");
				isChecked("spaceOutdoor","${location.SPACE_OUTDOOR}");
				isChecked("spaceLand","${location.SPACE_LAND}");
				isChecked("isFreeenter","${location.IS_FREEENTER}");
				isChecked("isKey","${location.IS_KEY}");
				isChecked("isIndoor","${location.IS_INDOOR}");
				isChecked("needMiscLic","${location.NEED_MISC_LIC}");
				isChecked("hasMiscLic","${location.HAS_MISC_LIC}");
				
				if($("#engineRoomStatus").val() == "S06" || $("#engineRoomStatus").val() == "S08"){
					$("#updateAddrButton").prop("disabled",true);
					$("#engineRoomLon").prop("readonly",true);
					$("#engineRoomLat").prop("readonly",true);
				}
// 				if($("#engineRoomStatus").val() != "S04"){
				$("#engineRoomType").prop("disabled",true);
// 				}
			}
			$("#lineInfoSiteId").val($("#engineRoomSiteId").val());
			$("#engineRoomSaveBtn").click(function(){
				if ($('#addEngineRoomForm')[0].checkValidity()) {
					
					event.preventDefault();
					var errorMessages = [];
					
					if(!validateNumber( $("#buildingHeight").val(),3,1)){
						errorMessages [errorMessages.length]="建築高度格式錯誤，整數最多三位、小數最多一位";
					}
					var lon = $("#engineRoomLon").val();
					if(lon != "" && lon != null){
						if(!validateNumber(lon,3,6)){
							errorMessages [errorMessages.length]="經度格式錯誤，整數最多三位、小數最多為六位";
						}
						if(lon < 115 || lon > 124){
							errorMessages [errorMessages.length]="經度需介於115~124之間";
						}
					}
					var lat = $("#engineRoomLat").val();
					if(lat != "" && lat != null){
						if(!validateNumber(lat,3,6)){
							errorMessages [errorMessages.length]="緯度格式錯誤，整數最多三位、小數最多為六位";
						}
						if(lat < 18 || lat > 28){
							errorMessages [errorMessages.length]="緯度需介於：18~28之間";
						}
					}
					if($("#footage").val() != "" && $("#footage").val() != null){
						if(!validateNumber( $("#footage").val(),3,0)){
							errorMessages [errorMessages.length]="坪數格式錯誤，整數最多三位";
						}
					}
					if($("#engineRoomAddr").val() == ""){
						errorMessages [errorMessages.length]="必須要輸入地址";
					}
					if($("#engineRoomDesc").val().length >100){
						errorMessages [errorMessages.length]="補充說明超過100個字";
					}
					if(errorMessages.length != 0){
						var msg ="";
						for(var em in errorMessages){
							msg += errorMessages[em]+"\n";
						}
						alert(msg);
						return false;
					}
					var alertMessage = "儲存後無法修改機房類別，是否要儲存?";
					if($("#engineRoomType").val()=="M"){
// 						alertMessage = "機房類別為傳輸中繼或是同業機房則無法再修改機房類別，是否要儲存?";
						$("#engineRoomStatus").val($("#engineRoomStatus_select").val());
					}
					if($("#engineRoomEvent").val() == "engineRoomUpdate"){
						alertMessage = "是否要儲存?";
					}
					if(confirm(alertMessage)){
						var attr = "";
						if($("#engineRoomType").prop("disabled")){
							attr = "&LOC_TYPE="+$("#engineRoomType").val();
						}
						$.ajax({
							url : "<s:url value='/st/engineRoom/save'/>",
							type : 'POST',
							async : false,
							data : $("#addEngineRoomForm").serialize()+"&engineRoomEvent="+$("#engineRoomEvent").val()+attr,
							success : function(data){
								var result = data['result'];
								var engineRoomEvent=$("#engineRoomEvent").val();
								if(result == true){
									if(engineRoomEvent == 'engineRoomInsert'){
										alert('<s:message code="msg.add.success"/>');
										var engineRoom = data['engineRoom'];
										$("#engineRoomBtsSiteId").val(engineRoom.bts_SITE_ID);
										$("#engineRoomStatus").val(engineRoom.site_STATUS);
									}else{
										alert('<s:message code="msg.update.success"/>');
									}
									var btsSiteId = $("#engineRoomBtsSiteId").val();
									var siteStatus = $("#engineRoomStatus").val();
									var roomType = $("#engineRoomType").val();
									var city = $("#engineRoomCity").val();
									var area = $("#engineRoomArea").val();
									var name = $("#engineRoomName").val();
									$("#engineRoom_BtsSiteId").val(btsSiteId);
									$("#engineRoom_Status").val(siteStatus).change();
									$("#engineRoom_Name").val(name);
									$("#engineRoom_Type").val(roomType).change();
									$("#engineRoom_City").val(city).change();
									$("#engineRoom_Area").val(area).change();
									var data={
											btsSiteId : btsSiteId,
											engineRoomStatus : siteStatus,
											engineRoomName : name,
											engineRoomType : roomType,
											engineRoomCity : city,
											engineRoomArea : area,
										}
									$("#grid").setGridParam({page:1,datatype:"json", postData:data, mtype: 'POST'}).jqGrid().trigger("reloadGrid");
				 					$.fancybox.close();
								}else{
									alert(result);
								}	
							}				
		 	       		});//ajax
					}
				}
			});
			$("#updateAddrButton").click(function(){
				if($("#engineRoomCity").val()=="" || $("#engineRoomArea").val()==""){
					alert("請先選擇區域");
					return false;
				}
				var siteLocAddr = {
					"zip" : $("#engineRoomZip").val(),
					"city" : $("#engineRoomCity").val(),
					"area" : $("#engineRoomArea").val(),
					"village" : $("#engineRoomVillage").val(),
					"road" : $("#engineRoomRoad").val(),
					"adjacent" : $("#engineRoomAdjacent").val(),
					"lane" : $("#engineRoomLane").val(),
					"alley" : $("#engineRoomAlley").val(),
					"subAlley" : $("#engineRoomSubAlley").val(),
					"doorNo" : $("#engineRoomDoorNo").val(),
					"doorNoEx" : $("#engineRoomDoorNoEx").val(),
					"floor" : $("#engineRoomFloor").val(),
					"floorEx" : $("#engineRoomFloorEx").val(),
					"room" : $("#engineRoomRoom").val(),
					"remark" : $("#engineRoomAddOther").val(),
					"disabledFields" : $("#disabledFields").val(),
					"callBackFun" : $("#engineRoomCallBackFun").val()
				};
				openAddressDialogFrame("showSiteLocAddrPage", siteLocAddr);
			});//updateAddrButton end
		});//document ready end
		function getMaintainArea(city,area,tagName){
			$.ajax({
				url : CONTEXT_URL + "/st/st001/search/maintainArea",
				type : 'POST',
				data : {
					cityId :city,
					townId :area
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
		
		function getMaintainDept(maintArea,tagName){
			$.ajax({
				url : CONTEXT_URL + "/st/st001/search/maintainDept",
				type : 'POST',
				data : {
					domain :maintArea
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
		
		
		function getMaintainTeam(maintDept,tagName){
			$.ajax({
				url : CONTEXT_URL + "/st/st001/search/maintainTeam",
				type : 'POST',
				data : {
					"deptId" :maintDept
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
		
		function getMaintainUser(maintTeam,tagName){
			$.ajax({
				url : CONTEXT_URL + "/st/st001/search/maintainUser",
				type : 'POST',
				data : {
					"deptId" :maintTeam
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
		
		function cleanTagValue(tagName){
			$('#'+tagName).html("");
			$('<option value="">---請選擇---</option>').appendTo('#'+tagName);
			$('#'+tagName).select2("val","");
		}
		
		function changeValue(checkboxName){
			if($("#"+checkboxName).prop("checked")){
				$("#"+checkboxName).val("Y");
			}else{
				$("#"+checkboxName).val("N");
			}
		}
		
		function engineRoomCallBackFun(addressObject){
			var obj = JSON.parse(addressObject);
			$("#engineRoomZip").val(obj.zip);
// 			$("#city").val(obj.city);
// 			$("#area").val(obj.area);
			$("#engineRoomVillage").val(obj.village);
			$("#engineRoomRoad").val(obj.road);
			$("#engineRoomAdjacent").val(obj.adjacent);
			$("#engineRoomLane").val(obj.lane);
			$("#engineRoomAlley").val(obj.alley);
			$("#engineRoomSubAlley").val(obj.subAlley);
			$("#engineRoomDoorNo").val(obj.doorNo);
			$("#engineRoomDoorNoEx").val(obj.doorNoEx);
			$("#engineRoomFloor").val(obj.floor);
			$("#engineRoomFloorEx").val(obj.floorEx);
			$("#engineRoomRoom").val(obj.room);
			$("#engineRoomAddOther").val(obj.remark);
			$.ajax({	
				url: CONTEXT_URL + "/common/address/combineAddress",
				dataType: "json",
				data : addressObject, 
				contentType : 'application/json',
				type: "POST",
				async: false,
				success : function(data) {
					$("#engineRoomAddr").val(data.row.fullAddress);
				}
			});
		}
		function isChecked(tagName,value){
			if(value == "Y"){
				$("#"+tagName).prop("checked",true);
				$("#"+tagName).val(value);
			}else{
				$("#"+tagName).val(value);
			}
		}
		function engineRoomStatusRule(){
			if($("#engineRoomType").val()=="M"){
				$("#showEngineRoomStatus").hide().val("");
				$("#engineRoomStatus_select").show().prop("required",true);
				$("#engineRoomStatusPrimary").show();
			}else{
				$("#showEngineRoomStatus").show();
				$("#engineRoomStatus_select").hide().prop("required",false).val("");
				$("#engineRoomStatusPrimary").hide();
			}
		}
		</script>
	</head>
	<body>
		<div class="row">
			<div class="col-xs-12 col-sm-12">
				<div class=" ui-draggable ui-droppable">
					<div class="box-content">
						<form class="form-horizontal" id="addEngineRoomForm">
							
							<table style="width:100%">
								<tr>
									<td width="10%" nowrap="nowrap">
										<label class="col-sm-12 control-label">
											<button class="btn btn-primary btn-label-left" type="submit" id="engineRoomSaveBtn" >
												<span><i class="fa fa-save"></i></span>
												<s:message code="button.save" text="Save" />
											</button>
											
										</label>
									</td>
									<td width="35%">
										<label class="col-sm-2 control-label">
											<button  class="btn btn-primary btn-label-left" type="button" id="fileProcessBtn" >
												<span><i class="fa fa-upload"></i></span>
												<s:message  text="附件上傳" />
											</button>
										</label>
									</td>
									<td width="7%" nowrap="nowrap"></td>
									<td width="20%" nowrap="nowrap"></td>
									<td width="8%"></td>
									<td width="20%"></td>
								</tr>
								<tr>
									<td nowrap="nowrap"><label class="col-sm-12 control-label">站點編號 :</label></td>
									<td>
										<p style="padding-top:20px; padding-left:15px;" id="showLocationId"></p>
										<input type="hidden" id="engineRoomLocationId" name="LOCATION_ID" maxlength="20"/>
									</td>
								</tr>
								<tr>
									<td>
										<div class="clearfix">&nbsp;</div>
									</td>
								</tr>
								<tr>
									<td><label class="col-sm-12 control-label">站台編號 :</label></td>
									<td>	
										<div class="col-sm-7" style="margin-left :16px">
<!-- 											<input id="engineRoomBtsSiteId" name="bts_SITE_ID"  type="text" class="form-control" required="required"> -->
											<p style="padding-top:20px;" id="showBtsSiteId"></p>
											<input type="hidden" id="engineRoomBtsSiteId" name="btsSiteId" />
											<input type="hidden" id="engineRoomSiteId" name="siteId" />
										</div>
									</td>
									<td><label class="col-sm-12 control-label"><span class="s">* </span>機房類別 :</label></td>
									<td>
										<div class="col-sm-14" style="margin-left :16px">
											<select id="engineRoomType" name="LOC_TYPE" class="form-control" required="required">
												<option value="">---請選擇---</option>
												<c:forEach items="${allSiteTypes}" var="siteType">
													<option value="${siteType.value}">${siteType.label}</option>
												</c:forEach>
											</select>
										</div>
									</td>
									<td nowrap="nowrap"><label style="padding-left:5px;" class="col-sm-12 control-label"><span class="s" id="engineRoomStatusPrimary" hidden="hidden">* </span>狀態 :</label></td>
									<td>
										<div class="col-sm-12">
											<p style="padding-top:15px;" id="showEngineRoomStatus"></p>
											<input type="hidden" id="engineRoomStatus" name="siteStatus" />
											<select id="engineRoomStatus_select" class="form-control" >
												<option value="">---請選擇---</option>
												<c:forEach items="${allSiteStatus}" var="siteStatus">
													<option value="${siteStatus.value}">${siteStatus.label}</option>
												</c:forEach>
											</select>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="clearfix">&nbsp;</div>
									</td>
								</tr>
								<tr>
									<td><label style="padding-left:5px;" class="col-sm-12 control-label"><span class="s">* </span>機房名稱 :</label></td>
									<td>	
										<div class="col-sm-14" style="margin-left :16px">
											<input id="engineRoomName" name="LOC_NAME"  type="text" class="form-control" required="required" maxlength="100">
										</div>
									</td>
									<td><label class="col-sm-12 control-label">機房型態 :</label></td>
									<td>
										<div class="col-sm-12">
											<select id="engineRoomBaseType" name="BASE_TYPE" class="form-control" name="siteSelect" >
												<option value="">---請選擇---</option>
												<c:forEach items="${allRoomTypes}" var="roomType">
													<option value="${roomType.value}">${roomType.label}</option>
												</c:forEach>
											</select>
										</div>
									</td>
									<td nowrap="nowrap"><label style="padding-left:5px;" class="col-sm-12 control-label">室內場所分類 :</label></td>
									<td>
										<div class="col-sm-12">
											<select id="indoorOption" name="INDOOR_OPTION" class="form-control" name="siteSelect">
												<option value="">---請選擇---</option>
												<c:forEach items="${allInBuildingTypes}" var="inBuildingType">
													<option value="${inBuildingType.value}">${inBuildingType.label}</option>
												</c:forEach>
											</select>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="clearfix">&nbsp;</div>
									</td>
								</tr>
								<tr>
									<td><label class="col-sm-12 control-label"><span class="s">* </span>區域 :</label></td>
									<td nowrap="nowrap">
										<div class="col-sm-6" style="margin-left :0px;padding-right :0px;">
											<select id="engineRoomCity" name="CITY" class="form-control" required="required">
											</select>
										</div>
										<div class="col-sm-6" style="padding-left :5px;padding-right :0px;">
											<select id="engineRoomArea" name="AREA" class="form-control" required="required">
											</select>
										</div>
									</td>
									<td><label class="col-sm-12 control-label"><span class="s">* </span>經度 :</label></td>
									<td>
										<div class="col-sm-11" >
											<input id="engineRoomLon" name="LON" type="text" class="form-control" placeholder="例:123.123456" required="required" maxlength="10">
										</div>
									</td>
									<td><label class="col-sm-12 control-label"><span class="s">* </span>緯度 :</label></td>
									<td>
										<div class="col-sm-11">
											<input id="engineRoomLat" name="LAT"  type="text" class="form-control" placeholder="例:123.123456" required="required" maxlength="10">
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="clearfix">&nbsp;</div>
									</td>
								</tr>
								<tr>
									<td><label class="col-sm-12 control-label"><span class="s">* </span>地址 :</label></td>
									<td colspan="5" nowrap="nowrap">
										<div class="col-sm-11">
											<input id="engineRoomAddr" name="ADDR" type="text" class="form-control" placeholder="請點選更新地址按鈕" readonly="readonly" required="required">
										</div>
										<input style="padding-left:0px;" id="updateAddrButton" type="button" value="更新地址" />
										<input type="hidden" name="ZIP" id="engineRoomZip"/>
										<input type="hidden" name="VILLAGE" id="engineRoomVillage"/>
										<input type="hidden" name="ADJACENT" id="engineRoomAdjacent"/>
										<input type="hidden" name="ROAD" id="engineRoomRoad"/>
										<input type="hidden" name="LANE" id="engineRoomLane"/>
										<input type="hidden" name="ALLEY" id="engineRoomAlley"/>
										<input type="hidden" name="SUB_ALLEY" id="engineRoomSubAlley"/>
										<input type="hidden" name="DOOR_NO" id="engineRoomDoorNo"/>
										<input type="hidden" name="DOOR_NO_EX" id="engineRoomDoorNoEx"/>
										<input type="hidden" name="FLOOR" id="engineRoomFloor"/>
										<input type="hidden" name="FLOOR_EX" id="engineRoomFloorEx"/>
										<input type="hidden" name="ROOM" id="engineRoomRoom"/>
										<input type="hidden" name="ADD_OTHER" id="engineRoomAddOther"/>
										<input type="hidden" name="disabledFields" id="disabledFields" value="city,area" />
										<input id="engineRoomCallBackFun" type="hidden" value="engineRoomCallBackFun" />
									</td>
								</tr>
								<tr>
									<td>
										<div class="clearfix">&nbsp;</div>
									</td>
								</tr>
								<tr>
									<td><label style="padding-left:5px;" class="col-sm-12 control-label"><span class="s">* </span>歸屬區域 :</label></td>
									<td>
										<div class="col-sm-8">
											<select id="maintArea" name="MAINT_AREA" class="form-control" name="siteSelect" required="required">
												<option value="">---請選擇---</option>
											</select>
										</div>
									</td>
									<td nowrap="nowrap"><label class="col-sm-12 control-label"><span class="s">* </span>歸屬單位 :</label></td>
									<td>
										<div class="col-sm-14" style="margin-left :16px">
											<select id="maintDept" name="MAINT_DEPT" class="form-control" name="siteSelect" required="required">
												<option value="">---請選擇---</option>
											</select>
										</div>
									</td>
									<td><label class="col-sm-12 control-label" ><span class="s">* </span>負責單位 :</label></td>
									<td>
										<div class="col-sm-13" style="padding-right: 0px;margin-left:16px">
											<select id="maintTeam" name="MAINT_TEAM" class="form-control" name="siteSelect" required="required">
												<option value="">---請選擇---</option>
											</select>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="clearfix">&nbsp;</div>
									</td>
								</tr>
								<tr>
									<td><label class="col-sm-12 control-label"><span class="s">* </span>負責人 :</label></td>
									<td>
										<div class="col-sm-8">
											<select id="maintUser" name="MAINT_USER" class="form-control" name="siteSelect" required="required">
												<option value="">---請選擇---</option>
											</select>
										</div>
									</td>
									<td nowrap="nowrap"><label style="padding-left:5px;" class="col-sm-12 control-label">NS_LEVEL :</label></td>
									<td>
										<div class="col-sm-12">
											<select id="nsLevel" name="NS_LEVEL" class="form-control" name="siteSelect" >
												<option value="">---請選擇---</option>
												<c:forEach items="${allNSLevels}" var="nSLevel">
													<option value="${nSLevel.value}">${nSLevel.label}</option>
												</c:forEach>
											</select>
										</div>
									</td>
									<td><label class="col-sm-12 control-label"><span class="s">* </span>建築高度 :</label></td>
									<td nowrap="nowrap">
										<div class="col-sm-10" style="padding-right:5px" >
											<input id="buildingHeight" name="BUILDING_HEIGHT" type="text" class="form-control" required="required" placeholder="例:123.1" maxlength="5">
										</div>
										<label style="padding-left:0px" class="col-sm-1 control-label">(m)</label>
									</td>
								</tr>
								<tr>
									<td>
										<div class="clearfix">&nbsp;</div>
									</td>
								</tr>
								<tr>
									<td><label class="col-sm-12 control-label">空間 :</label></td>
									<td colspan="3" nowrap="nowrap">
										<div class="row form-group" style="padding-left : 15px;">
											<div class="col-sm-10">
												<div class="checkbox-inline">
													<label>
														<input id="spaceRoom" name="SPACE_ROOM" onclick="changeValue('spaceRoom');" value="N" type="checkbox"> 房屋
														<i class="fa fa-square-o"></i>
													</label>
												</div>
												<div class="checkbox-inline">
													<label>
														<input id="spaceRoof" name="SPACE_ROOF" onclick="changeValue('spaceRoof');" value="N" type="checkbox"> 樓頂
														<i class="fa fa-square-o"></i>
													</label>
												</div>
												<div class="checkbox-inline">
													<label>
														<input id="spaceTop" name="SPACE_TOP" onclick="changeValue('spaceTop');" value="N"  type="checkbox"> 屋凸
														<i class="fa fa-square-o"></i>
													</label>
												</div>
												<div class="checkbox-inline">
													<label>
														<input id="spacePlatform" name="SPACE_PLATFORM" onclick="changeValue('spacePlatform');" value="N" type="checkbox" > 平台
														<i class="fa fa-square-o"></i>
													</label>
												</div>
												<div class="checkbox-inline">
													<label>
														<input id="spaceLand" name="SPACE_LAND" onclick="changeValue('spaceLand');" value="N" type="checkbox"> 土地
														<i class="fa fa-square-o"></i>
													</label>
												</div>
												<div class="checkbox-inline">
													<label>
														<input id="spaceInoor" name="SPACE_INDOOR" onclick="changeValue('spaceInoor');" value="N" type="checkbox"> 室內
														<i class="fa fa-square-o"></i>
													</label>
												</div>
												<div class="checkbox-inline">
													<label>
														<input id="spaceOutdoor" name="SPACE_OUTDOOR" onclick="changeValue('spaceOutdoor');" value="N" type="checkbox"> 室外
														<i class="fa fa-square-o"></i>
													</label>
												</div>
											</div>
										</div>
									</td>
									<td><label class="col-sm-12 control-label">坪數 :</label></td>
									<td>
										<div class="col-sm-11" style="padding-right:5px" >
											<input id="footage" name="FOOTAGE" type="text" placeholder="只取到小數點第二位" class="form-control" maxlength="19">
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="clearfix">&nbsp;</div>
									</td>
								</tr>
								<tr>
									<td nowrap="nowrap"><label class="col-sm-12 control-label">與使用者關係 :</label></td>
									<td>
										<div class="col-sm-8" style="padding-right:5px" >
											<input id="empRelation" name="EMP_RELATION" type="text" class="form-control" maxlength="50">
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="clearfix">&nbsp;</div>
									</td>
								</tr>
								<tr>
									<td><label class="col-sm-12 control-label">其他資訊 :</label></td>
									<td colspan="5" nowrap="nowrap">
										<div class="row form-group" style="padding-left : 15px;">
											<div class="col-sm-10">
												<div class="checkbox-inline">
													<label>
														<input id="isFreeenter" name="IS_FREEENTER" onclick="changeValue('isFreeenter');" value="N" type="checkbox"> 是否可自由進入
														<i class="fa fa-square-o"></i>
													</label>
												</div>
												<div class="checkbox-inline">
													<label>
														<input id="isKey" name="IS_KEY" onclick="changeValue('isKey');" value="N"  type="checkbox"> 是否有鑰匙
														<i class="fa fa-square-o"></i>
													</label>
												</div>
												<div class="checkbox-inline">
													<label>
														<input id="isIndoor" name="IS_INDOOR" onclick="changeValue('isIndoor');" value="N" type="checkbox" > 是否為室內
														<i class="fa fa-square-o"></i>
													</label>
												</div>
												<div class="checkbox-inline">
													<label>
														<input id="needMiscLic" name="NEED_MISC_LIC" onclick="changeValue('needMiscLic');" value="N" type="checkbox"> 需要雜項執照
														<i class="fa fa-square-o"></i>
													</label>
												</div>
												<div class="checkbox-inline">
													<label>
														<input id="hasMiscLic" name="HAS_MISC_LIC" onclick="changeValue('hasMiscLic');" value="N" type="checkbox"> 已有雜項執照
														<i class="fa fa-square-o"></i>
													</label>
												</div>
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="clearfix">&nbsp;</div>
									</td>
								</tr>
								<tr >
									<td><label class="col-sm-12 control-label">補充說明 :</label></td>
									<td colspan="5" nowrap="nowrap">
										<textarea id="engineRoomDesc" name="LOC_DESC" style="resize: none;width:730px;border-left-width:1px;margin-left:15px;margin-top:4px;" rows="3"><c:if test="${engineRoomDesc != null}">${engineRoomDesc}</c:if></textarea>
									</td>
								</tr>
								<tr>
									<td>
										<div class="clearfix">&nbsp;</div>
									</td>
								</tr>
								<tr>
									<td><label class="col-sm-12 control-label">所有權 :</label></td>
									<td>
										<div class="col-sm-4" style="padding-right:5px" >
											<select id="owner" name="OWNER" class="form-control" name="siteSelect" >
												<option value="N">否</option>
												<option value="Y">是</option>
											</select>
										</div>
									</td>
									<td><label class="col-sm-12 control-label">聯絡人 :</label></td>
									<td>
										<div class="col-sm-10" style="padding-right:5px" >
											<input id="cntPsn" name="CNT_PSN" type="text" class="form-control" maxlength="20">
										</div>
									</td>
									<td><label class="col-sm-12 control-label">聯絡電話 :</label></td>
									<td>
										<div class="col-sm-12" style="padding-right:5px" >
											<input id="cntTel" name="CNT_TEL" type="text" class="form-control" maxlength="20">
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="clearfix">&nbsp;</div>
									</td>
								</tr>
								<tr>
									<td nowrap="nowrap"><label class="col-sm-12 control-label">機房施工KO :</label></td>
									<td nowrap="nowrap">
										<div class="col-sm-6">
											<input id="constrKoDate" name="constrKoDate" type="text"
												class="form-control" readonly="readonly" ></input>
										</div>
									</td>
									<td nowrap="nowrap"><label class="col-sm-12 control-label">機房施工完成 :</label></td>
									<td nowrap="nowrap">
										<div class="col-sm-10">
											<input id="constrDoneDate" name="constrDoneDate" type="text"
												class="form-control" readonly="readonly"></input>
										</div>
									</td>
									<td nowrap="nowrap"><label class="col-sm-12 control-label">SMR/電池 :</label></td>
									<td nowrap="nowrap">
										<div class="col-sm-10">
											<input id="smrReadyDate" name="smrReadyDate" type="text"
												class="form-control" readonly="readonly"></input>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="clearfix">&nbsp;</div>
									</td>
								</tr>
								<tr>
									<td nowrap="nowrap"><label class="col-sm-12 control-label">UPS READY :</label></td>
									<td nowrap="nowrap">
										<div class="col-sm-6">
											<input id="upsReadyDate" name="upsReadyDate" type="text"
												class="form-control" readonly="readonly"></input>
										</div>
									</td>
									<td nowrap="nowrap"><label class="col-sm-12 control-label">AC READY :</label></td>
									<td nowrap="nowrap">
										<div class="col-sm-10">
											<input id="acReadyDate" name="acReadyDate" type="text"
												class="form-control" readonly="readonly"></input>
										</div>
									</td>
									<td nowrap="nowrap"><label class="col-sm-12 control-label">發電機 READY :</label></td>
									<td nowrap="nowrap">
										<div class="col-sm-10">
											<input id="dynamoReadyDate" name="dynamoReadyDate" type="text"
												class="form-control" readonly="readonly"></input>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="clearfix">&nbsp;</div>
									</td>
								</tr>
								<tr>
									<td nowrap="nowrap"><label class="col-sm-12 control-label">ADM READY :</label></td>
									<td nowrap="nowrap">
										<div class="col-sm-6">
											<input id="admReadyDate" name="admReadyDate" type="text"
												class="form-control" readonly="readonly"></input>
										</div>
									</td>
									<td nowrap="nowrap"><label class="col-sm-12 control-label">電力設備 READY :</label></td>
									<td nowrap="nowrap">
										<div class="col-sm-10">
											<input id="powerReadyDate" name="powerReadyDate" type="text"
												class="form-control" readonly="readonly"></input>
										</div>
									</td>
									<td nowrap="nowrap"><label class="col-sm-12 control-label">傳輸設備 READY :</label></td>
									<td nowrap="nowrap">
										<div class="col-sm-10">
											<input id="transReadyDate" name="transReadyDate" type="text"
												class="form-control" readonly="readonly"></input>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="clearfix">&nbsp;</div>
									</td>
								</tr>
								<tr>
									<td><label class="col-sm-12 control-label">專線資訊 :</label></td>
								</tr>
								<tr>
									<td colspan="6">
										<jsp:include page="/WEB-INF/views/ajaxPage/st/line/LineInfo.jsp"></jsp:include>
									</td>
								</tr>
							</table>
							<input type="hidden" id="engineRoomEvent" value="${engineRoomEvent}"></input>
							<input id ="engineRoomShareType" name="SHARE_TYPE" value="0" type="hidden" />	
						</form>
					</div>
				</div>
			</div>
		</div>
		<div id="showSiteLocAddrPage"></div>
		<div id="showFileUploadPage"></div>
	</body>
</html>