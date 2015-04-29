<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
<title>單張工單作業申請</title>
<script type="text/javascript">
	$(document).ready(function() {
		
			

			//$("#siteOrderType").prop("readonly",true);
			$("#siteHuntSiteId").prop("readonly",true);
			
// 			LoadSelect2Script('<s:url value="/resources/plugins/select2/select2.min.js" />', Select2Test);
			WinMove();
			
			var showBtn = "${showBtn}";
			if(showBtn != ""){
				$("tr").attr("hidden",false);
			}
			$('#prediceEndDate').datepicker({
				dateFormat : "yy/mm/dd",
				changeYear : true,
				changeMonth : true,
				showButtonPanel : true,
				showTime : false,
				minDate : 0
			});
			table = $('#workOrderTable').dataTable(
					{
						"bDestroy" : true,
						"iDisplayLength" : -1,
						"aaSorting" : [ [ 0, "desc" ] ],
						"sDom" : "rS",
						// 	 			"bAutoWidth":true,
						"sScrollY" : '70',
						"bSort" : false,
						// 		        "sScrollX" : "100%",
						"bProcessing" : false,
						"sAjaxDataProp" : "rows",
						"sAjaxSource" : CONTEXT_URL
								+ '/st/st002/search/workOrder?workId='
								+ $("#workId").val(),
						"aoColumnDefs" : [ {
							"aTargets" : [ 0 ],
							"mData" : "order_ID"
						}, {
							"aTargets" : [ 1 ],
							"mData" : "orderTypeName"
						}, {
							"aTargets" : [ 2 ],
							"mData" : "deptName"
						}, {
							"aTargets" : [ 3 ],
							"mData" : "odr_SEQ"
						}, {
							"aTargets" : [ 4 ],
							"mData" : "odr_STATUS"
						}, ],
						"oLanguage" : {
							"sProcessing" : "處理中...",
							"sZeroRecords" : "沒有匹配結果",
							"sLoadingRecords" : "讀取中..."
						},
						"fnInitComplete" : function() {
							this.fnAdjustColumnSizing();
						}
					});
		});//document ready end
		
		//基站查詢
		function searchSiteBtn() {
			console.log($("#siteCallBackFun").val());
			openSiteDialogFrame("selectSitePage", $("#siteCallBackFun").val(), $(
					"#filter_DomainId").val(), "multi");
			//  多筆請傳入 multi	
			//	openSiteDialogFrame("selectSitePage", $("#siteCallBackFun").val(),$("#filter_DomainId").val());

		}
		
		function receviceSiteData(siteObjects) {
			var obj = JSON.parse(siteObjects);
			//alert(JSON.stringify(siteObjects));
			
			if(obj.siteObjs[0].site_STATUS != 'S06' && obj.siteObjs[0].site_STATUS != 'S08' 
				&& obj.siteObjs[0].site_STATUS != 'S02' && obj.siteObjs[0].site_STATUS != 'S04') {
				alert("請選擇 On Air、停用、待尋點、待建設");
				return false;
			}
			
			$("#locationId").val(obj.siteObjs[0].location_ID);
			$("#siteBuildSiteId").val(obj.siteObjs[0].site_ID);
			//基站編號
			$("#siteHuntSiteId").val(obj.siteObjs[0].bts_SITE_ID);
			//基站名稱
			$("#showSiteName").text(obj.siteObjs[0].locName);
			$("#siteNameData").val(obj.siteObjs[0].locName);
			//經度 & 緯度
			$("#showLon").text(obj.siteObjs[0].lon);
			$("#singleLon").val(obj.siteObjs[0].lon);
			$("#showLat").text(obj.siteObjs[0].lat);
			$("#singleLat").val(obj.siteObjs[0].lat);
			//基站頻段
			$("#showFeqId").text(obj.siteObjs[0].feqName);
			$("#siteFeqId").val(obj.siteObjs[0].feq_ID);
			//地址
			$("#showAddr").text(obj.siteObjs[0].locAddr);
			$("#siteAddr").val(obj.siteObjs[0].locAddr);
			$("#eqpType").val(obj.siteObjs[0].eqp_TYPE_ID);
			
			$("#zip").val(obj.siteObjs[0].locZip);
			$("#city").val(obj.siteObjs[0].locCity);
			$("#area").val(obj.siteObjs[0].locArea);
			$("#village").val(obj.siteObjs[0].locVillage);
			$("#adjacent").val(obj.siteObjs[0].locAdjacent);
			$("#road").val(obj.siteObjs[0].locRoad);
			$("#lane").val(obj.siteObjs[0].locLane);
			$("#alley").val(obj.siteObjs[0].locAlley);
			$("#subAlley").val(obj.siteObjs[0].locSub_Alley);
			$("#doorNo").val(obj.siteObjs[0].locDoor_No);
			$("#doorNoEx").val(obj.siteObjs[0].locDoor_No_Ex);
			$("#floor").val(obj.siteObjs[0].locFloor);
			$("#floorEx").val(obj.siteObjs[0].locFloor_Ex);
			$("#room").val(obj.siteObjs[0].locRoom);
			$("#addOther").val(obj.siteObjs[0].locAdd_Other);
			//區域 (負責單位使用參數)
			$("#locCity").val(obj.siteObjs[0].locCity);
			$("#locArea").val(obj.siteObjs[0].locArea);
			$("#eqpType").val(obj.siteObjs[0].eqp_TYPE_ID);
			//設備型態
			$("#siteBuildEqpType").val("${siteWork.EQP_TYPE_ID}");
			$("#siteCallbackFunEqpType").val("${siteWork.EQP_TYPE_ID}");
			//設備機型
			
			//基站狀態
			$("#startSiteStatus").val(obj.siteObjs[0].site_STATUS);
			osSiteStatus();
			if(obj.siteObjs[0].location_ID != null && obj.siteObjs[0].location_ID != "") {				
				$.ajax({
					url : CONTEXT_URL + "/st/st002/search/location",
					type : 'POST',
					data : {
						locationId :obj.siteObjs[0].location_ID,
					},
					async : false,
					success : function(record){
						var isMultibandLocation = record['isMultibandLocation'];
						if(isMultibandLocation >1){
							$("#multibandLocation").attr("checked",true);
						}	
					}
				});
			}

			//選擇完基站編號 判斷 工單類別是否有值，有的話 連動 查詢 負責單位
/* 			if ($('#siteOrderType').val() != "") {
				osResponsibleUnit();
			} */
			osSiteOrderType();
		}
		
		//工單類別
		function osSiteOrderType(){
			$.ajax({
				url : CONTEXT_URL + "/st/st009/siteOrderType",			
				type : "POST",
				dataType : 'json',
				data : {
					eqpTypeId :$('#eqpType').val(),
					siteStatus : $('#startSiteStatus').val(),
				},
				async : false,
				success : function(data) {
					if(data.success){
						if(data.rows.length > 0){
							$("#siteOrderType  option").remove();
							$("#siteOrderType").append("<option value=''>--請選擇--</option>");
							//alert(JSON.stringify(data.rows));
							for(var i=0; i<data.rows.length; i++){		
								$("#siteOrderType").append("<option value="+data.rows[i].od_TYPE_ID+">"+data.rows[i].od_TYPE_NAME+"</option>");
							}			
						}
					}
				}
			});
		}
		
// 		function Select2Test() {
			//$("select").select2();
// 		}
</script>
</head>
<body>
	<div style="padding-right: 15px;">
	<table style="width: 100%;margin-bottom: 2%">
		<tr>
			<td nowrap="nowrap" width="10%"><label
				class="col-sm-12 control-label">作業編號 :</label></td>
			<td width="25%">
				<p style="padding-top: 15px; padding-left: 15px;" id="showWorkId"></p>
				<input id="workId" name="workId" type="hidden" ></input>
			</td>
			<td width="10%" nowrap="nowrap"></td>
			<td width="23%"></td>
			<td width="10%">
				<label class="col-sm-12 control-label">處理狀態 :</label>
			</td>
			<td width="22%">
				<p id="showWorkStatus" style="padding-top: 15px; padding-left: 15px;"></p> 
				<input id="siteHuntWorkStatus" name="workStatus" type="hidden"></input>
			</td>
		</tr>
		<tr>
			<td>
				<div class="clearfix">&nbsp;</div>
			</td>
		</tr>		
		<tr>
			<td><label style="padding-left: 5px;"
				class="col-sm-12 control-label"><span class="s">* </span>工務類別:</label></td>
			<td width="22%">
				<p id="showWorkType"
					style="padding-top: 15px; padding-left: 15px;"></p> 
				<input id="siteWorkType" name="workType" type="hidden"></input>
			</td>
			<td><label class="col-sm-12 control-label">重要等級 :</label></td>
			<td>
				<div class="col-sm-10">
					<select id="priority" name="priority" class="form-control">
						<c:forEach items="${allLevel}" var="level">
							<option value="${level.value}">${level.label}</option>
						</c:forEach>
					</select>
				</div>
			</td>
			<td><label style="padding-left: 5px;"
				class="col-sm-12 control-label">申請人員 :</label></td>
			<td>
				<p style="padding-top: 15px; padding-left: 15px;" id="showApplyUser"></p>
				<input id="applyUser" name="applyUser" type="hidden"></input>
			</td>
		</tr>
		<tr>
			<td>
				<div class="clearfix">&nbsp;</div>
			</td>
		</tr>
		<tr>
			<td>
				<label class="col-sm-12 control-label"><span class="s" id="btsSiteIdPrimary">* </span>基站編號 :</label>
			</td>
			<td nowrap="nowrap">
				<div style="padding-top: 10px;">
					<div
						style="width: 80%; height: auto; float: left; display: inline">
						<input id="siteHuntSiteId" name="btsSiteId" class="form-control" value="" placeholder="點擊選擇按鈕"/>
					</div>
					<div
						style="width: 10%; height: auto; float: left; display: inline">
						&nbsp;&nbsp;
						<button class="btn btn-primary btn-label"
							style="margin-right: 10px" type="button"
							onclick="searchSiteBtn()">選擇</button>
					</div>
					<div id="selectSitePage"></div>
					<form id="siteTestForm">
						<input type="hidden" name="siteCallBackFun" id="siteCallBackFun" value="receviceSiteData" />
						<input type="hidden" name="filter_DomainId" id="filter_DomainId" value="" />
						<!-- 單選不用傳 -->
						<input type="hidden" name="selectMode" id="selectMode" value="multi" />
					</form>					
				</div>
			</td>
			<td nowrap="nowrap"><label class="col-sm-12 control-label">基站名稱:</label></td>			
			<td>
				<p id="showSiteName" style="padding-top: 15px; padding-left: 15px;"></p> 
				<input type="hidden" id="siteNameData" name="siteName" class="form-control" value="" />
			</td>
			
			<td nowrap="nowrap"><label class="col-sm-12 control-label"><span
					class="s">*</span>基站頻段 :</label></td>
			<td>
				<p id="showFeqId" style="padding-top: 15px; padding-left: 15px;"></p>
				<input type="hidden" id="siteFeqId" name="feqId" class="form-control" value=""/>
			</td>
		</tr>
		<tr>
			<td>
				<div class="clearfix">&nbsp;</div>
			</td>
		</tr>
		<tr>
			<td nowrap="nowrap"><label class="col-sm-12 control-label"><span
					class="s">*</span>地址 :</label></td>
			<td>
				<p id="showAddr" style="padding-top: 15px; padding-left: 15px;"></p>
				<input type="hidden" id="siteAddr" name="ADDR" class="form-control" value=""/>
				<input type="hidden" name="ZIP" id="zip"/>
				<input type="hidden" name="CITY" id="city"/>
				<input type="hidden" name="AREA" id="area"/>
				<input type="hidden" name="VILLAGE" id="village"/>
				<input type="hidden" name="ADJACENT" id="adjacent"/>
				<input type="hidden" name="ROAD" id="road"/>
				<input type="hidden" name="LANE" id="lane"/>
				<input type="hidden" name="ALLEY" id="alley"/>
				<input type="hidden" name="SUB_ALLEY" id="subAlley"/>
				<input type="hidden" name="DOOR_NO" id="doorNo"/>
				<input type="hidden" name="DOOR_NO_EX" id="doorNoEx"/>
				<input type="hidden" name="FLOOR" id="floor"/>
				<input type="hidden" name="FLOOR_EX" id="floorEx"/>
				<input type="hidden" name="ROOM" id="room"/>
				<input type="hidden" name="ADD_OTHER" id="addOther"/>
				<input type="hidden" name="disabledFields" id="disabledFields" value="" />
				<input id="siteLocationCallBackFun" type="hidden" value="siteLocationCallBackFun" />
			</td>
		</tr>
		<tr>
			<td>
				<div class="clearfix">&nbsp;</div>
			</td>
		</tr>
		<tr>
			<td nowrap="nowrap"><label class="col-sm-12 control-label">
				<span class="s">*</span>經度 :</label></td>			
			<td>
				<p id="showLon" style="padding-top: 15px; padding-left: 15px;"></p>
				<input type="hidden" id="singleLon" name="lon" class="form-control" value=""/>
			</td>
			<td nowrap="nowrap"><label class="col-sm-12 control-label">
				<span class="s">*</span>緯度 :</label></td>	
			<td>
				<p id="showLat" style="padding-top: 15px; padding-left: 15px;"></p>
				<input type="hidden" id="singleLat" name="lat" class="form-control" value=""/>
			</td>
			<td COLSPAN=2><input id="multibandLocation"
				type="checkbox" style="margin-top: 10px;margin-left: 30%" >&nbsp;&nbsp;多頻段站點</td>
		</tr>
		<tr>
			<td>
				<div class="clearfix">&nbsp;</div>
			</td>
		</tr>
		<tr>
			<td nowrap="nowrap"><label class="col-sm-12 control-label"><span class="s" id="btsSiteIdPrimary">* </span>工單類別 :</label></td>
			<td>												
				<select  id="siteOrderType" name="siteOrderType" class="form-control">
					<!-- <option value="">---請選擇---</option> -->							
				</select>
			</td>
			<td nowrap="nowrap"><label class="col-sm-12 control-label"
				style="margin-top: 10px">負責單位 :</label></td>
			<td>
				<p style="margin-top: 25px; padding-left: 5px;" id="showResponsibleUnit"></p>
				<input id="siteResponsibleUnit" name="siteResponsibleUnit" type="hidden" value=""></input>
			</td>
			<td nowrap="nowrap">
				<label class="col-sm-12 control-label" style="margin-top: 10px">完工基站狀態 :</label></td>
			<td>	
				<div class="col-sm-10">
					<select id="allSiteStatus" name="allSiteStatus" class="form-control">
<%-- 						<c:forEach items="${allSiteStatus}" var="siteStatus">
							<option value="${siteStatus.value}">${siteStatus.label}</option>
						</c:forEach> --%>
						<option value="S06">On Air</option>
						<option value="S08">停用</option>
						<option value="S11">拆站</option>
					</select>
					<input id="siteStatusValue" name="siteStatusValue" type="hidden"/>	
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<div class="clearfix">&nbsp;</div>
			</td>
		</tr>
		<tr>
			<td width="10%" nowrap="nowrap"><label
				class="col-sm-12 control-label">申請日期:</label></td>
			<td width="23%">
				<fmt:formatDate value="${siteWork.APL_TIME}" pattern="yyyy/MM/dd HH:mm" var="apptime"/>
				<p style="padding-top: 15px; padding-left: 15px;" id="showApplyTime">
					<c:out value="${apptime}"/>
				</p>			 
				<c:if test="${siteWork.APL_TIME ne null}">
					<input id="applyTime" name="apllyTime" type="hidden"></input>
				</c:if>
			</td>
			<td nowrap="nowrap"><label class="col-sm-12 control-label"><span
					class="s">*</span>預計完工日 :</label></td>
			<td>
				<div class="col-sm-10">
					<input id="prediceEndDate" name="prediceEndDate" type="text"
						class="form-control" placeholder="點選輸入框" readonly="readonly"></input>
				</div>
			</td>
			
			<td><label class="col-sm-12 control-label">完工日期 :</label></td>
			<td>
				<p style="padding-top: 15px; padding-left: 15px;"  id="showEndTime"></p> 
				<c:if test="${siteWork.END_TIME ne null}">
					<input id="endTime" name="endTime" type="hidden"></input>
				</c:if>
			</td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td>
				<div class="clearfix">&nbsp;</div>
			</td>
		</tr>
		<tr>
			<td><label class="col-sm-12 control-label"
				style="margin-bottom: 45px">作業說明 :</label></td>
			<td colspan="5" nowrap="nowrap">
				<div style="margin-top: 5px">
					<textarea id="workDesc" name="workDesc" style="resize: none; width:94%; border-left-width:1px; 
					margin-left: 15px; margin-top: 4px;" rows="3" maxlength="100">${siteWork.WORK_DESC}</textarea>
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<div class="clearfix">&nbsp;</div>
			</td>
		</tr>
		<tr>
			<td><label class="col-sm-12 control-label"
				style="margin-bottom: 80px">簽核意見 :</label></td>
			<td colspan="5" >
				<div class="form-group"
					style="margin-left: 15px; width: 94%; margin-bottom: 65px;">
					<c:import url="/commom/signHistory">
						<c:param name="processType" value="SiteBuildSingleWorkApply"></c:param>
						<c:param name="applyNo" value="${siteWork.WORK_ID}"></c:param>
					</c:import>
				</div>
			</td>
		</tr>
	</table>
</div>
<input id="locCity" name="locCity" type="hidden"></input>
<input id="locArea" name="locArea" type="hidden"></input>
<input id="odTypeId" name="odTypeId" type="hidden"></input>
<input id="eqpType" name="eqpTypeId" type="hidden"></input>
<input id="siteBuildSiteId" name="siteId" type="hidden" />
<input id="locationId" name="locationId" type="hidden" />
<input id="checkBoxItemId" type="hidden"></input>
<input id="startSiteStatus" name="startSiteStatus" type="hidden"></input>
<input id="osType" type="hidden" >
<input id="siteBuildEqpModel" type="hidden" >
<input id="siteBuildWorkType" type="hidden" >

</body>
</html>