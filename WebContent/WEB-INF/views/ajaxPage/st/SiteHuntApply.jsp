<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
<title>尋點作業申請</title>
<script type="text/javascript">
	$(document).ready(function() {
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
						},
						"fnRowCallback": function( nRow, aData, iDisplayIndex ) {
							if ($("#orderId").val() == aData['order_ID']) {
								nRow.className = "row_selected_highlight";
								return nRow;
							}
						}
					});
		});//document ready end
			
</script>
</head>
<body>
	<table style="width: 100%">
		<tr id="siteHuntApplyBtnsTr">
			<td nowrap="nowrap" colspan="3">
				<button class="btn btn-primary btn-label-left" style="margin-left: 30px"
					type="submit" id="siteWorkSaveBtn"><span><i class="fa fa-save"></i></span>存檔</button>
				<button class="btn btn-primary btn-label-left" style="margin-left: 10px"
					type="submit" id="siteHuntApplyBtn"><span><i class="fa fa-legal"></i></span>申請</button>
				<button class="btn btn-primary btn-label-left" style="margin-left: 10px" id="cancelApplyBtn"
					 type="button"><span><i class="fa fa-times"></i></span>取消申請</button>
				<button class="btn btn-primary btn-label-left" id="fileProcessBtn"
					style="margin-left: 10px" type="button"><span><i class="fa fa-upload"></i></span>作業附件</button>
				<button class="btn btn-primary btn-label-left" id="fileDownloadsBtn"
					style="margin-left: 10px" type="button"><span><i class="fa fa-download"></i></span>附件下載</button>
			</td>
		</tr>
		<tr>
			<td nowrap="nowrap" width="10%"><label
				class="col-sm-12 control-label">作業編號 :</label></td>
			<td width="25%">
				<p style="padding-top: 15px; padding-left: 15px;" id="showWorkId"></p>
				<input id="workId" name="workId" type="hidden"></input>
				<input id="osType" name="osType" type="hidden" value="P" /><%-- osType尋點工程類別預設為P --%>
			</td>

			<td nowrap="nowrap" width="10%">
				<label class="col-sm-12 control-label">作業類別 :</label></td>
			<td width="23%">
				<p style="padding-top: 15px; padding-left: 15px;" id="showWorkType"></p>
			</td>
			<td width="10%">
				<label class="col-sm-12 control-label">處理狀態 :</label>
			</td>
			<td width="22%">
				<p id="showWorkStatus"
					style="padding-top: 15px; padding-left: 15px;"></p> 
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
				class="col-sm-12 control-label"><span class="s">* </span>工務類別
					:</label></td>
			<td>
				<div class="col-sm-10">
					<select id="siteHuntWorkType" name="workType" class="form-control"
						required="required">
						<option value="">---請選擇---</option>
						<c:forEach items="${allWorkType}" var="workType">
							<option value="${workType.value}">${workType.label}</option>
						</c:forEach>
					</select>
				</div>
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
			<td><label class="col-sm-12 control-label"><span
					class="s">* </span>基站頻段 :</label></td>
			<td>
				<div class="col-sm-9">
					<select id="feqId" name="feqId" class="form-control"
						required="required">
						<option value="">---請選擇---</option>
						<c:forEach items="${allSiteFeq}" var="siteFeq">
							<option value="${siteFeq.key},${siteFeq.value.value}">${siteFeq.value.label}</option>
						</c:forEach>
					</select>
				</div>
			</td>
			<td>
				<label class="col-sm-12 control-label"><span class="s" id="btsSiteIdPrimary">* </span>基站編號 :</label>
			</td>
			<td>
				<table>
					<tr>
						<td width="70%">
							<div class="col-sm-12">
								<input id="siteHuntBtsSiteId" name="btsSiteId" type="text"
									class="form-control" required="required" maxlength="20"></input>
							</div> 
							<input id="siteHuntSiteId" name="siteId" type="hidden"></input>
						</td>
						<td width="50%">
							<input id="unuseBtsSiteIdBtn" type="button" value="可用基站編號" style="padding-left :0px;padding-right :0px"/>
						</td>	
					</tr>
				</table>
			</td>
			<td><label class="col-sm-12 control-label">基站名稱 :</label></td>
			<td>
				<div class="col-sm-12">
					<input id="siteHuntSiteName" name="siteName" type="text"
						class="form-control" maxlength="100"></input>
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<div class="clearfix">&nbsp;</div>
			</td>
		</tr>
		<tr>
			<td><label class="col-sm-12 control-label"><span class="s">* </span>設備型態 :</label></td>
			<td>
				<div class="col-sm-9">
					<select id="siteHuntEqpType" name="eqpTypeId" class="form-control" required="required">
						<option value="">---請選擇---</option>
						<c:forEach items="${allEqpType}" var="eqpType">
							<option value="${eqpType.value}">${eqpType.label}</option>
						</c:forEach>
					</select>
				</div>
			</td>
			<td><label class="col-sm-12 control-label">設備機型 :</label></td>
			<td>
				<div class="col-sm-10">
					<select id="siteHuntEqpModel" name="eqpModelId"
						class="form-control">
						<option value="">---請選擇---</option>
					</select>
				</div>
			</td>
			<td>
				<label style="padding-left: 5px;" class="col-sm-12 control-label"><span class="s">* </span>建站目的 :</label>
			</td>
			<td>
				<div class="col-sm-11">
					<select id="purpose" name="purpose" class="form-control"
						required="required">
						<option value="">---請選擇---</option>
						<c:forEach items="${allBuildAim}" var="buildAim">
							<option value="${buildAim.value}">${buildAim.label}</option>
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
			<td nowrap="nowrap"><label style="padding-left: 5px;"
				class="col-sm-12 control-label">Configuration :</label></td>
			<td>
				<div class="col-sm-9">
					<input id="btsConfig" name="btsConfig" type="text"
						class="form-control" maxlength="15"></input>
				</div>
			</td>
			<td nowrap="nowrap"><label class="col-sm-12 control-label">Donor
					BTS :</label></td>
			<td nowrap="nowrap">
				<div class="col-sm-10">
					<input id="donorSiteText" name="donorSiteText" type="text"
						class="form-control" placeholder="點擊選擇按鈕"></input>
				</div> 
				<input id="donorSite" name="donorSite" type="hidden" /> 
				<input id="donorSiteBtn" name="donorSiteBtn" type="button" value="選擇" style="margin-left: -5px" />
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
			<td><label class="col-sm-12 control-label">Feederless :</label></td>
			<td>
				<div class="col-sm-9">
					<select id="feederless" name="feederless" class="form-control">
						<c:forEach items="${allFeederless}" var="feederless">
							<option value="${feederless.value }">${feederless.label }</option>
						</c:forEach>
					</select>
				</div>
			</td>
			<td nowrap="nowrap"><label class="col-sm-12 control-label">Master
					Site :</label></td>
			<td nowrap="nowrap">
				<div class="col-sm-10">
					<input id="masterSiteText" name="masterSiteText" type="text"
						class="form-control" placeholder="點擊選擇按鈕"></input>
				</div> 
				<input id="masterSite" name="masterSite" type="hidden" /> 
				<input id="masterSiteBtn" name="masterSiteBtn" type="button" value="選擇"
				style="margin-left: -5px" />
			</td>
			<td><label class="col-sm-12 control-label"><span
					class="s">* </span>預計完工日 :</label></td>
			<td>
				<div class="col-sm-10">
					<input id="prediceEndDate" name="prediceEndDate" type="text"
						class="form-control" placeholder="點選輸入框" readonly="readonly"></input>
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<div class="clearfix">&nbsp;</div>
			</td>
		</tr>
		<tr>
			<td><label style="padding-left: 5px;"
				class="col-sm-12 control-label">站點編號 :</label></td>
			<td>
				<table>
					<tr>
						<td width="65%">
							<p id="showsiteHuntLocationId"
								style="padding-top: 15px; padding-left: 15px;"></p> 
								<input id="siteHuntLocationId" name="locationId" type="hidden" />
						</td>
						<td width="35%">
							<input id="selectSiteBtn" type="button" value="選擇站點" />
						</td>
					</tr>
				</table>
			</td>
			<td><label class="col-sm-12 control-label">站點名稱 :</label></td>
			<td>
				<p id="showLocName" style="padding-top: 15px; padding-left: 15px;"></p>
				<input id="locName" name="locName" type="hidden" />
			</td>
			<td colspan="1" align='right' nowrap="nowrap">
				<div class="row form-group">
					<div class="col-sm-10" style="margin-left: 50px">
						<div class="checkbox-inline">
							<label>
								<input type="checkbox" id="multibandLocation" /> 多頻段站點 <i class="fa fa-square-o"></i>
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
		<tr>
			<td><label class="col-sm-12 control-label"><span
					class="s">* </span>區域 :</label></td>
			<td nowrap="nowrap" colspan="2">
				<div class="col-sm-6">
					<select id="siteHuntCity" name="city" class="form-control"
						required="required">
					</select>
				</div>
				<div class="col-sm-6" style="margin-left: -25px;">
					<select id="siteHuntArea" name="area" class="form-control"
						required="required">
					</select>
				</div>
			</td>
			<td></td>
			<td><label class="col-sm-12 control-label"><span class="s" id="coverageTypePrimary" hidden="hidden">* </span>涵蓋類別 :</label></td>
			<td>
				<div class="col-sm-12">
					<select id="coverageType" name="coverageType" class="form-control">
						<option value="">---請選擇---</option>
						<c:forEach items="${allIncludeRange }" var="includeRange">
							<option value="${includeRange.value}">${includeRange.label }</option>
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
			<td><label style="padding-left: 5px;"
				class="col-sm-12 control-label">SR ID :</label></td>
			<td>
				<table>
					<tr>
						<td width="61%">
							<p id="showSiteHuntSrId" style="padding-top: 15px; padding-left: 15px;"></p> 
							<input id="siteHuntSrId" name="srId" type="hidden" />
						</td>
						<td width="39%">
							<input id="selectSrIdBtn" type="button" value="選擇SR ID" />
						</td>
					</tr>
				</table>
			</td>
			<td><label class="col-sm-12 control-label"><span class="s" id="lonPrimary">* </span>經度 :</label></td>
			<td>
				<div class="col-sm-10">
					<input id="siteHuntLon" name="lon" type="text" class="form-control"
						placeholder="例:123.123456" maxlength="10" required="required">
				</div>
			</td>
			<td><label class="col-sm-12 control-label"><span class="s" id="latPrimary">* </span>緯度 :</label></td>
			<td>
				<div class="col-sm-11">
					<input id="siteHuntLat" name="lat" type="text" class="form-control"
						placeholder="例:123.123456" maxlength="10" required="required"></input>
				</div>
			</td>
		</tr>

		<tr>
			<td>
				<div class="clearfix">&nbsp;</div>
			</td>
		</tr>
		<tr>
			<td><label class="col-sm-12 control-label"><span
					class="s">* </span>涵蓋區域 :</label></td>
			<td colspan="5">
				<div class="col-sm-12">
					<input id="coverRange" name="coverRange" type="text"
						class="form-control" required="required" maxlength="200"></input>
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<div class="clearfix">&nbsp;</div>
			</td>
		</tr>
		
		
		
		<tr>
			<td><label class="col-sm-12 control-label">申請日期 :</label></td>
			<td>
				<fmt:formatDate value="${siteWork.APL_TIME}" pattern="yyyy/MM/dd HH:mm" var="apptime"/>
				<p style="padding-top: 15px; padding-left: 15px;" id="showApplyTime">
					<c:out value="${apptime}"/>
				</p>
				 
				<c:if test="${siteWork.APL_TIME ne null}">
					<input id="applyTime" name="apllyTime" type="hidden"></input>
				</c:if>
			</td>
			<td><label class="col-sm-12 control-label">完工日期 :</label></td>
			<td>
				<fmt:formatDate value="${siteWork.END_TIME}" pattern="yyyy/MM/dd HH:mm" var="endTime"/>
				<p id="showEndTime" style="padding-top: 15px; padding-left: 15px;">
					<c:out value="${endTime}"/>
				</p> 
				<c:if test="${siteHunt.endDate ne null}">
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
					margin-left: 15px; margin-top: 4px;" rows="3">${siteWork.WORK_DESC}</textarea>
				</div>
			</td>
		</tr>
		<tr>
			<td><label class="col-sm-12 control-label"
				style="margin-bottom: 45px">作業工單 :</label></td>
			<td colspan="5" nowrap="nowrap">
				<div style="margin-left: 16px; margin-top: 15px">
					<table id="workOrderTable"
						class="table table-bordered  table-hover table-heading table-datatable"
						width="96%">
						<thead>
							<tr>
								<th>工單號碼</th>
								<th>派工目的</th>
								<th>負責單位</th>
								<th>作業順序</th>
								<th>狀態</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
			</td>
		</tr>
		<tr>
			<td><label class="col-sm-12 control-label" style="margin-bottom: 80px">簽核意見 :</label></td>
			<td colspan="5" >
				<div class="form-group"
					style="margin-left: 15px; width: 94%; margin-bottom: 65px;">
					<c:import url="/commom/signHistory">
						<c:param name="processType" value="${processType}"></c:param>
						<c:param name="applyNo" value="${siteWork.WORK_ID}"></c:param>
					</c:import>
				</div>
			</td>
		</tr>
	</table>
</body>
</html>