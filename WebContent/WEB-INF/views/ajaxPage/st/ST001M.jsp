<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<title>站點維護</title>
		<script type="text/javascript">
			$(document).ready(function() {
				LoadSelect2Script(
					'<s:url value="/resources/plugins/select2/select2.min.js" />',Select2Test);
				WinMove();
				
				buildCityOptions("city","siteLocArea");
				$("#addSiteBtn").hide();
				if($("#siteLocEvent").val() == "siteLocUpdate"){
					updateInit();
					$("#addSiteBtn").show();
					$("#city").prop("disabled",true);
					$("#siteLocArea").prop("disabled",true);
				}
				//多頻段站點
				if("${isMultibandLocation}">1){
					$("#multibandLocation").attr("checked",true);
				}
				//多頻段站點 end
				$("#share_TYPE").change(function(){
					if($("#share_TYPE").val()=="0"){
						$("input[name=SHARE_COM]").attr("checked",false);
					}
				});
				$("#multibandLocation").prop("disabled",true);
				var city = $("#city").val();
				$("#city").change(function(){
					cleanerAddr();
					cleanTagValue("maint_AREA");
					cleanTagValue("maint_DEPT");
					cleanTagValue("maint_TEAM");
					cleanTagValue("maint_USER");
				});//city end
				
				$("#siteLocArea").change(function(){
					cleanerAddr();
					cleanTagValue("maint_AREA");
					cleanTagValue("maint_DEPT");
					cleanTagValue("maint_TEAM");
					cleanTagValue("maint_USER");
					if($("#siteLocArea").val() != ""){
						getMaintainArea($("#city").val(),$("#siteLocArea").val(),"maint_AREA");
					}
				});//siteLocArea end
				
				$("#maint_AREA").change(function(){
					cleanTagValue("maint_DEPT");
					cleanTagValue("maint_TEAM");
					cleanTagValue("maint_USER");
					if($("#maint_AREA").val() != ""){
						getMaintainDept($("#maint_AREA").val(),"maint_DEPT");
					}
				});//maint_AREA end
				
				$("#maint_DEPT").change(function(){
					cleanTagValue("maint_TEAM");
					cleanTagValue("maint_USER");
					if($("#maint_DEPT").val() != ""){
						getMaintainTeam($("#maint_DEPT").val(),"maint_TEAM");
					}
				});//maint_DEPT end
				
				$("#maint_TEAM").change(function(){
					cleanTagValue("maint_USER");
					if($("#maint_TEAM").val() != ""){
						getMaintainUser($("#maint_TEAM").val(),"maint_USER");
					}
				});//maint_TEAM end
				
				$("#updateAddrButton").click(function(){
					if($("#city").val()=="" || $("#siteLocArea").val()==""){
						alert("請先選擇區域");
						return false;
					}
					var siteLocAddr = {
						"zip" : $("#zip").val(),
						"city" : $("#city").val(),
						"area" : $("#siteLocArea").val(),
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
					openAddressDialogFrame("showSiteLocAddrPage", siteLocAddr);
				});//updateAddrButton end
				
				$("#addSiteBtn").click(function(){
					var locType = $("#loc_TYPE").val();
					if(locType==""){
						alert("請先選擇站點類別");
						return false;
					}
					var feqIdArray = [];
					$("input[name=siteFeqId]").each(function(){
						feqIdArray[feqIdArray.length] = $(this).val();
					});
					
					var targetFrameId = "showAddSitePage";
					$.ajax({
						mimeType : 'text/html; charset=utf-8',
						url : CONTEXT_URL + "/st/st001/addSitePage",
						type : 'POST',
						dataType : "html",
						data:{
							"targetFrameId" : targetFrameId,
							"siteType" : locType,
							"feqId" : feqIdArray.toString(),
						},
						async : false,
						success : function(data) {
							initialDialogFrame(targetFrameId, "建立基站",data);
						}
					});
				});//addSiteBtn end
				
				$("#locationSaveBtn").click(function(){
					
					if ($('#addLocatiuonForm')[0].checkValidity()) {
						event.preventDefault();
						var errorMessages = locValidate();
						if(errorMessages.length != 0){
							var msg ="";
							for(var em in errorMessages){
								msg += errorMessages[em]+"\n";
							}
							alert(msg);
							return false;
						}
						
						if(confirm("是否要儲存?")){
							var shareComArray = new Array();
							var attr = "";
							if($("#siteLocEvent").val() == "siteLocUpdate"){
								attr = "&CITY="+$("#city").val()+"&AREA="+$("#siteLocArea").val()+"&LOC_TYPE="+$("#loc_TYPE").val();
							}
							if($("#share_TYPE").prop("disabled")){
								$("#share_TYPE").prop("disabled",false);
							}
							$('input:checkbox:checked[name="SHARE_COM"]').each(function(i) {shareComArray[i]= this.value ; });
							$.ajax({
								url : "<s:url value='/st/st001/save'/>",
								type : 'POST',
								async : false,
								data : $("#addLocatiuonForm").serialize()+"&shareComArray="+shareComArray+"&siteLocEvent="+$("#siteLocEvent").val()+attr,
								success : function(data){
									var result = data['result'];
									var siteLocEvent = data['siteLocEvent'];
									if(result == true){
										if(siteLocEvent == "siteLocInsert"){
											alert('<s:message code="msg.add.success"/>');
										}else{
											alert('<s:message code="msg.update.success"/>');
										}
										var city = $('#city').val();
										var area = $("#siteLocArea").val();
										var locName = $("#loc_NAME").val();
										var locType = $("#loc_TYPE").val();
										var location = data['location'];
										$("#locationId").val(location.location_ID);
										$("#locName").val(locName);
										$('#locType').select2("val",locType);
										$('#locCity').select2("val",city).change();
										$('#locTown').select2("val",area);
										var data= {
												locationId : location.location_ID,
												btsSiteId : "",
												locName : locName,
												locType : locType,
												locCity : city,
												locTown : area,
												siteStatus : "",
												siteFeq : "",
										};
					 					$("#grid").setGridParam({page:1,datatype:"json", postData:data, mtype: 'POST'}).jqGrid().trigger("reloadGrid");
					 					$.fancybox.close();
									}else{
										if($("#share_TYPE").prop("disabled")){
											$("#share_TYPE").prop("disabled",false);
										}
										alert(result);
									}
								}					
			 	       		});//ajax
						}//confirm
					}//checkValidity
				});//locationSaveBtn end
			});//document.ready end
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
				if(maintArea != null && maintArea != ""){
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
			}
			
			
			function getMaintainTeam(maintDept,tagName){
				if(maintDept != null && maintDept != ""){
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
			}
			
			function getMaintainUser(maintTeam,tagName){
				if(maintTeam != null && maintTeam != ""){
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
			}
			
			
			
			function changeValue(checkboxName){
				if($("#"+checkboxName).prop("checked")){
					$("#"+checkboxName).val("Y");
				}else{
					$("#"+checkboxName).val("N");
				}
			}
			
			function siteLocationCallBackFun(addressObject){
				var obj = JSON.parse(addressObject);
				$("#zip").val(obj.zip);
				$("#city").val(obj.city);
				$("#siteLocArea").val(obj.area);
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
					url: CONTEXT_URL + "/common/address/combineAddress",
					dataType: "json",
					data : addressObject, 
					contentType : 'application/json',
					type: "POST",
					async: false,
					success : function(data) {
						$("#addr").val(data.row.fullAddress);
					}
				});
			}
			function updateInit(){
				var shareComArray = "${shareCom}".split(",");
				for(var shareCom in shareComArray){
					$("#"+shareComArray[shareCom]).prop("checked",true);
				}
				$("#locationIdText").text("${siteLocation.LOCATION_ID}");
				$("#location_ID").val("${siteLocation.LOCATION_ID}");
				$("#loc_TYPE").val("${siteLocation.LOC_TYPE}").prop("disabled", true);	// modify by Charlie 20150107 編輯時站點類別不再異動
				var locType = $("#loc_TYPE").val();
				if(locType == "M" || locType == "H" || locType == "C"){
					$("#share_TYPE").prop("disabled",true);
				}
				$("#loc_NAME").val("${siteLocation.LOC_NAME}");
				$("#share_TYPE").val("${siteLocation.SHARE_TYPE}");
				$("#base_TYPE").val("${siteLocation.BASE_TYPE}");
				$("#indoor_OPTION").val("${siteLocation.INDOOR_OPTION}");
				$("#city").val("${siteLocation.CITY}").change();
				$("#siteLocArea").val("${siteLocation.AREA}");
				getMaintainArea($("#city").val(),$("#siteLocArea").val(),"maint_AREA");
				$("#lon").val("${siteLocation.LON}");
				$("#lat").val("${siteLocation.LAT}");
				$("#addr").val("${siteLocation.ADDR}");
				$("#maint_AREA").val("${siteLocation.MAINT_AREA}").change();
				
				getMaintainDept($("#maint_AREA").val(),"maint_DEPT");
				$("#maint_DEPT").val("${siteLocation.MAINT_DEPT}").change();
				getMaintainTeam($("#maint_DEPT").val(),"maint_TEAM");
				$("#maint_TEAM").val("${siteLocation.MAINT_TEAM}").change();
				getMaintainUser($("#maint_TEAM").val(),"maint_USER");
				$("#maint_USER").val("${siteLocation.MAINT_USER}").change();
				$("#ns_LEVEL").val("${siteLocation.NS_LEVEL}");
				$("#building_HEIGHT").val("${siteLocation.BUILDING_HEIGHT}");
				if("${siteLocation.IS_FREEENTER}"=="Y"){
					$("#is_FREEENTER").prop("checked",true);
					changeValue("is_FREEENTER");
				}
				if("${siteLocation.IS_KEY}"=="Y"){
					$("#is_KEY").prop("checked",true);
					changeValue("is_KEY");
				}
				if("${siteLocation.IS_INDOOR}"=="Y"){
					$("#is_INDOOR").prop("checked",true);
					changeValue("is_INDOOR");
				}
				if("${siteLocation.NEED_MISC_LIC}"=="Y"){
					$("#need_MISC_LIC").prop("checked",true);
					changeValue("need_MISC_LIC");
				}
				if("${siteLocation.HAS_MISC_LIC}"=="Y"){
					$("#has_MISC_LIC").prop("checked",true);
					changeValue("has_MISC_LIC");
				}
				//
				if("${siteLocation.SPACE_ROOM}"=="Y"){
					$("#space_ROOM").prop("checked",true);
					changeValue("space_ROOM");
				}
				if("${siteLocation.SPACE_ROOF}"=="Y"){
					$("#space_ROOF").prop("checked",true);
					changeValue("space_ROOF");
				}
				if("${siteLocation.SPACE_TOP}"=="Y"){
					$("#space_TOP").prop("checked",true);
					changeValue("space_TOP");
				}
				if("${siteLocation.SPACE_PLATFORM}"=="Y"){
					$("#space_PLATFORM").prop("checked",true);
					changeValue("space_PLATFORM");
				}
				if("${siteLocation.SPACE_LAND}"=="Y"){
					$("#space_LAND").prop("checked",true);
					changeValue("space_LAND");
				}
				if("${siteLocation.SPACE_INDOOR}"=="Y"){
					$("#space_INDOOR").prop("checked",true);
					changeValue("space_INDOOR");
				}
				if("${siteLocation.SPACE_OUTDOOR}"=="Y"){
					$("#space_OUTDOOR").prop("checked",true);
					changeValue("space_OUTDOOR");
				}
				$("#footage").val("${siteLocation.FOOTAGE}");
				$("#emp_RELATION").val("${siteLocation.EMP_RELATION}");
				<c:set var="siteLocationDesc" value="${siteLocation.LOC_DESC}" scope="request"></c:set>
				$("#zip").val("${siteLocation.ZIP}");
				$("#village").val("${siteLocation.VILLAGE}");
				$("#road").val("${siteLocation.ROAD}");
				$("#adjacent").val("${siteLocation.ADJACENT}");
				$("#lane").val("${siteLocation.LANE}");
				$("#alley").val("${siteLocation.ALLEY}");
				$("#subAlley").val("${siteLocation.SUB_ALLEY}");
				$("#doorNo").val("${siteLocation.DOOR_NO}");
				$("#doorNoEx").val("${siteLocation.DOOR_NO_EX}");
				$("#floor").val("${siteLocation.FLOOR}");
				$("#floorEx").val("${siteLocation.FLOOR_EX}");
				$("#room").val("${siteLocation.ROOM}");
				$("#addOther").val("${siteLocation.ADD_OTHER}");
				if("${isOnAir}" == "Y"){
					$("#loc_TYPE").prop("disabled",true);
					$("#lon").prop("readOnly",true);
					$("#lat").prop("readOnly",true);
					$("#updateAddrButton").prop("disabled",true);
				}
			}
			function unselect(){
				if($("#share_TYPE").val()==0){
					$("input[name=SHARE_COM]").attr("checked",false);
				}
			}
			function cleanTagValue(tagName){
				$('#'+tagName).html("");
				$('<option value="">---請選擇---</option>').appendTo('#'+tagName);
				$('#'+tagName).select2("val","");
			}
			function filterText(tagName){
				$("#"+tagName).keyup(function(){
					 $(this).val($(this).val().replace(/[^\d.]/g,""));
					 $(this).val($(this).val().replace(/^\./g,""));
				});
				$("#"+tagName).keydown(function(){
					 $(this).val($(this).val().replace(/[^\d.]/g,""));
					 $(this).val($(this).val().replace(/^\./g,""));
				});
			}
			function cleanerAddr(){
				$("#zip").val("");
				$("#village").val("");
				$("#adjacent").val("");
				$("#road").val("");
				$("#lane").val("");
				$("#alley").val("");
				$("#subAlley").val("");
				$("#doorNo").val("");
				$("#doorNoEx").val("");
				$("#floor").val("");
				$("#floorEx").val("");
				$("#room").val("");
				$("#addOther").val("");
				$("#addr").val("");
			}
		</script>
	</head>
	<body>
		<div class="box-header">
			<div class="box-name">
				<span>站點維護</span>
			</div>
			<div class="box-icons">			 
				<p class="expand-link"></p>
			</div>
			<div class="no-move"></div>
		</div>
		<div class="row">
			<div class="col-xs-12 col-sm-12">
				<div class=" ui-draggable ui-droppable">
					<div class="box-content" id="aaa">
						<jsp:include page="/WEB-INF/views/ajaxPage/st/LocationInfo.jsp"></jsp:include>
						<c:if test="${siteLocEvent eq 'siteLocUpdate'}">
							<jsp:include page="/WEB-INF/views/ajaxPage/st/SiteInfo.jsp"></jsp:include>
						</c:if>
						<div id="showAddSitePage"> </div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>