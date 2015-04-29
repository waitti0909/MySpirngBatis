<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>建站作業申請</title>
<script type="text/javascript">
	$(document).ready(
			function() {
				var showBtn = "${showBtn}";
				if (showBtn != "") {
					$("tr").attr("hidden", false);
				}
				$('#prediceEndDate').datepicker({
					dateFormat : "yy/mm/dd",
					changeYear : true,
					changeMonth : true,
					showButtonPanel : true,
					showTime : false,
					minDate : 0
				});
				//作業工單
				table = $('#workOrderTable').dataTable(
						{
							"bDestroy" : true,
							"iDisplayLength" : -1,
							"aaSorting" : [ [ 0, "desc" ] ],
							"sDom" : "rS",
							"sScrollY" : '70',
							"bSort" : false,
							"bProcessing" : false,
							"sAjaxDataProp" : "rows",
							"sAjaxSource" : CONTEXT_URL
									+ '/st/st004/search/workOrder?workId='
									+ $("#workId").val(),
							"aoColumnDefs" : [
                             {"aTargets" : [ 0 ],"mData" : 
                            function(data){ 
                            	var isActive = "";
                            	var disabledStr = "";
                            	if (data.is_ACTIVE == 'Y'){ 
                            		isActive = "checked";
                            	}
                             	if($("#siteBuildApplyEvent").val()=="view"){
                             		disabledStr = "disabled";
                             	}
                       	      	return "<input type='checkbox' id='itemBox"+data.order_ID+"' onclick='itemBoxClick();' name='itemBox' "+isActive+" "+disabledStr+" value='"+data.order_ID+"'><input type='hidden' id='itemName"+data.order_ID+"' name='itemName' value='"+data.orderTypeName+"' />";}, 
                       	      	"sWidth":"5%", "bSortable":false },                 
							{
								"aTargets" : [ 1 ],
								"mData" : "order_ID"
							}, {
								"aTargets" : [ 2 ],
								"mData" : "orderTypeName"
							}, {
								"aTargets" : [ 3 ],
								"mData" : "deptName"
							}, {
								"aTargets" : [ 4 ],
								"mData" : "odr_SEQ"
							}, {
								"aTargets" : [ 5 ],
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
			
//             function itemBoxClick(obj) {
// 			    	if ($(obj).is(':checked') ) {
// 				    	allData += $(obj).val() + ",";
				    	
// 				    } else {
// 					if (allData.indexOf($(obj).val() + ",") != -1) {
// 						allData= allData.replace($(obj).val() + "," , "");
// 					}
// 				}
// 			    alert(allData);
// 				$("#checkBoxItemId").val(allData);
// 			}
			
</script>
</head>
<body>
	<table style="width: 100%">
		<tr hidden="true" id="siteBuildApplyBtnsTr">
			<td nowrap="nowrap" align='right'>
				<button class="btn btn-primary btn-label-left" style="margin-right: 10px"
					type="submit" id="siteWorkSaveBtn"><span><i class="fa fa-save"></i></span>存檔</button>
			</td>
			<td nowrap="nowrap" colspan="2">
				<button class="btn btn-primary btn-label-left" style="margin-left: 10px"
					type="submit" id="siteBuildApplyBtn"><span><i class="fa fa-legal"></i></span>申請</button>
				<button class="btn btn-primary btn-label-left" id="cancelApplyBtn" style="margin-left: 10px"
					 type="button"><span><i class="fa fa-times"></i></span>取消申請</button>
				<button class="btn btn-primary btn-label-left" id="fileProcessBtn"
					style="margin-left: 10px" type="button"><span><i class="fa fa-upload"></i></span>作業附件</button>
				
			</td>
			<td>
			</td>
		</tr>
		<tr>
			<td nowrap="nowrap" align='right'>
				<button class="btn btn-primary btn-label-left" id="fileDownloadsBtn"
					style="margin-left: 30px" type="button"><span><i class="fa fa-download"></i></span>附件下載</button>	
					
			</td>
		</tr>
		<tr>
			<td width="10%" nowrap="nowrap">
			  <label class="col-sm-12 control-label">作業編號 :</label>
			</td>
			<td width="22%">
			  <p style="padding-top: 15px; padding-left: 15px;" id="showWorkId"></p>
			  <input id="workId" name="workId" type="hidden"></input>
			</td>
			
			<td width="10%" nowrap="nowrap">
			  <label class="col-sm-12 control-label">作業類別 :</label>
			</td>
			<td width="24%">
			  <p style="padding-top: 15px; padding-left: 15px;" id="showWorkType"></p>
			</td>
			
			<td width="10%">
			  <label class="col-sm-12 control-label">處理狀態:</label>
			</td>
			<td width="24%">
			  <p id="showWorkStatus" style="padding-top: 15px; padding-left: 15px;"></p>
			  <input id="siteBuildWorkStatus" name="workStatus" type="hidden"></input>
			</td>
		</tr>

		<tr>
			<td>
				<div class="clearfix">&nbsp;</div>
			</td>
		</tr>
		<tr>
			<td nowrap="nowrap">
			  <label class="col-sm-12 control-label"><span class="s">* </span>工務類別:</label>
			</td>
			<td>
				<div class="col-sm-10">
				  <select id="siteBuildWorkType" name="workType" required="required"
					class="form-control">
						<option value="">---請選擇---</option>
						<c:forEach items="${allWorkType}" var="workType">
							<option value="${workType.value}">${workType.label}</option>
						</c:forEach>
				  </select>
			  	</div>
			</td>
			
			<td nowrap="nowrap">
			  <label class="col-sm-12 control-label">重要等級:</label>
			</td>
			<td>
				<div class="col-sm-10">
				  <select id="priority" name="priority" class="form-control">
						<c:forEach items="${allLevel}" var="level">
							<option value="${level.value}">${level.label}</option>
						</c:forEach>
				  </select>
				 </div>
			</td>
			
			<td>
			  <label class="col-sm-12 control-label">申請人員 :</label>
			</td>
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
			<td nowrap="nowrap">
			  <label class="col-sm-12 control-label">站點編號:</label>
			</td>
			<td>
			  <p id="showsiteBuildLocationId" style="padding-top: 15px; padding-left: 15px;"></p>
			  <input id="siteBuildLocationId" name="locationId" type="hidden" />
			  <input id="siteBuildSiteId" name="siteId" type="hidden" />
			</td>
			
			<td nowrap="nowrap">
			  <label class="col-sm-12 control-label">站點名稱:</label>
			</td>
			<td>
			  <p id="showLocName" style="padding-top: 15px; padding-left: 15px;"></p>
			  <input id="locName" name="locName" type="hidden" />
			</td>
			
			<td colspan="1" align='right' nowrap="nowrap">
				<div  class="row form-group">
					<div class="col-sm-10" style="margin-left: 50px">
						<div class="checkbox-inline">
							<label> 
							      <input type="checkbox" id="multibandLocation" />多頻段站點 <i class="fa fa-square-o"></i>
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
			<td nowrap="nowrap">
			  <label class="col-sm-12 control-label"><span class="s">* </span> 建站目的 :</label>
			</td>
			<td>
				<div class="col-sm-10">
					<select id="purpose" name="purpose" class="form-control"
						required="required">
						<option value="">---請選擇---</option>
						<c:forEach items="${allBuildAim}" var="buildAim">
							<option value="${buildAim.value}">${buildAim.label}</option>
						</c:forEach>
					</select>
				</div>
			</td>
			
			<td nowrap="nowrap">
			  <label class="col-sm-12 control-label"><span class="s">* </span> 工程類別:</label>
			</td>
			<td>
				<div class="col-sm-10">
					  <select id="osType" name="osType" class="form-control" required="required">
							<option value="">---請選擇---</option>
							<c:forEach items="${poTypeList}" var="poType">
								<option value="${poType.value}">${poType.label}</option>
							</c:forEach>
				      </select>
				</div>
			</td>
			
			<td nowrap="nowrap">
			  <label class="col-sm-12 control-label"><span id="osRequireMark"></span> 委外廠商:</label>
			</td>
			<td>
				<div class="col-sm-10">
				  <select id="osVen" name="osVen" class="form-control">
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
			<td nowrap="nowrap">
			  <label class="col-sm-12 control-label"><span class="s">* </span>地址 :</label>
			</td>
			<td colspan="5" nowrap="nowrap">
				<div class="col-sm-11">
					<input id="addr" name="ADDR" type="text" class="form-control" placeholder="請點選更新地址按鈕" required="required">
				</div>
			    <input style="padding-left:0px;" id="updateAddrButton" type="button" value="更新地址" />
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
			<td nowrap="nowrap">
			  <label class="col-sm-12 control-label"><span class="s">* </span>經度 :</label>
			</td>
			<td>
				<div class="col-sm-10">
			  		<input id="siteBuildLon" name="lon" type="text" class="form-control" required="required" placeholder="例:123.123456" maxlength="10"></input>
			  	</div>
			</td>
			
			<td nowrap="nowrap">
			  <label class="col-sm-12 control-label"><span class="s">* </span>緯度 :</label>
			</td>
			<td>
				<div class="col-sm-10">
				  <input id="siteBuildLat" name="lat" type="text" class="form-control" required="required" placeholder="例:123.123456" maxlength="10"></input>
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<div class="clearfix">&nbsp;</div>
			</td>
		</tr>
		<tr>
			<td nowrap="nowrap">
			  <label class="col-sm-12 control-label"><span class="s">* </span>基站編號 :</label>
			</td>
			<td nowrap="nowrap">
				<div class="col-sm-8">
					<input id="siteBuildBtsSiteId" name="btsSiteId" type="text"	class="form-control"  placeholder="點擊選擇按鈕" required="required"></input>
				</div>
				<input id="unuseBtsSiteIdBtn" type="button"	value="選擇" />
			</td>
			
			<td nowrap="nowrap">
			  <label class="col-sm-12 control-label">基站頻段 :</label>
			</td>
			<td>
				<div class="col-sm-10">
				  <select id="feqId" name="feqId" class="form-control">
						<option value="">---請選擇---</option>
						<c:forEach items="${allSiteFeq}" var="siteFeq">
							<option value="${siteFeq.value}">${siteFeq.label}</option>
						</c:forEach>
				  </select>
				</div>
			</td>
			
			<td nowrap="nowrap">
			  <label class="col-sm-12 control-label"><span class="s">* </span>基站名稱 :</label>
			</td>
			<td>
				<div class="col-sm-12">
			  		<input id="siteBuildSiteName" name="siteName" type="text" class="form-control" required="required"></input>
			  	</div>
			</td>
		</tr>
		<tr>
			<td>
				<div class="clearfix">&nbsp;</div>
			</td>
		</tr>
		<tr>
			<td nowrap="nowrap">
			  <label class="col-sm-12 control-label">設備型態:</label>
			</td>
			<td>
				<div class="col-sm-10">
				  <select id="siteBuildEqpType" name="eqpTypeId"	class="form-control">
						<option value="">---請選擇---</option>
						<c:forEach items="${allEqpTypes}" var="eqpType">
							<option value="${eqpType.value}">${eqpType.label}</option>
						</c:forEach>
				  </select>
			  	</div>
			</td>
			
			<td nowrap="nowrap">
			  <label class="col-sm-12 control-label">設備機型:</label>
			</td>
			<td>
				<div class="col-sm-10">
				  <select id="siteBuildEqpModel" name="eqpModelId" class="form-control">
						<option value="">---請選擇---</option>
				  </select>
			  	</div>
			</td>
			
			<td nowrap="nowrap">
			  <label class="col-sm-12 control-label">涵蓋類別:</label>
			</td>
			<td>
				<div class="col-sm-11">
				  <select id="coverageType" name="coverageType"	class="form-control">
						<option value="">---請選擇---</option>
						<c:forEach items="${allIncludeRange }" var="includeRange">
							<option value="${includeRange.value}">${includeRange.label}</option>
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
			<td nowrap="nowrap">
			  <label class="col-sm-12 control-label">Configuration:</label>
			</td>
			<td>
				<div class="col-sm-10">
			  		<input id="btsConfig" name="btsConfig" type="text" class="form-control" maxlength="15"></input>
			  	</div>
			</td>
			
			<td nowrap="nowrap">
			  <label class="col-sm-12 control-label">Donor BTS :</label>
			</td>
			<td nowrap="nowrap">
				<div class="col-sm-10">
					<input id="donorSiteText" name="donorSiteText" type="text" class="form-control" placeholder="點擊選擇按鈕"></input>
				</div>
				<input id="donorSite" name="donorSite" type="hidden" />
				<input id="donorSiteBtn" name="donorSiteBtn" type="button"	value="選擇" />
			</td>
		</tr>
		<tr>
			<td>
				<div class="clearfix">&nbsp;</div>
			</td>
		</tr>
		<tr>
			<td nowrap="nowrap">
			  <label class="col-sm-12 control-label">Feederless:</label>
			</td>
			<td>
				<div class="col-sm-10">
				  <select id="feederless" name="feederless"	class="form-control">
						<c:forEach items="${allFeederless}" var="feederless">
							<option value="${feederless.value }">${feederless.label }</option>
						</c:forEach>
				  </select>
				</div>
			</td>
			
			<td nowrap="nowrap">
			  <label class="col-sm-12 control-label">Master	Site:</label>
			</td>
			<td nowrap="nowrap">
				<div class="col-sm-10">
					<input id="masterSiteText" name="masterSiteText" type="text" class="form-control" placeholder="點擊選擇按鈕"></input>
				</div>
				<input id="masterSite" name="masterSite" type="hidden" />
				<input id="masterSiteBtn" name="masterSiteBtn" type="button" value="選擇"/>
			</td>
			
			<td nowrap="nowrap">
			  <label class="col-sm-12 control-label"><span class="s">* </span>預計完工日 :</label>
			</td>
			<td>
				<div class="col-sm-10">
			  		<input id="prediceEndDate" name="prediceEndDate" type="text" class="form-control" placeholder="點選輸入框" readonly="readonly"></input>
			  	</div>
			</td>
		</tr>

		<tr>
			<td>
				<div class="clearfix">&nbsp;</div>
			</td>
		</tr>
		
		<tr>
			<td nowrap="nowrap">
			  <label class="col-sm-12 control-label">架許證號:</label>
			</td>
			<td>
				<div class="col-sm-10">
				  <input id="permitNo" name="permitNo"  class="form-control" maxlength="20"/>
				</div>
			</td>
			
			<td nowrap="nowrap">
			  <label class="col-sm-12 control-label">電台(雜項)證號:</label>
			</td>
			<td>
				<div class="col-sm-10">
			  		<input id="licenseNo" name="licenseNo"  class="form-control" maxlength="20"/>
			  	</div>
			</td>
			
			<td nowrap="nowrap">
			  <label class="col-sm-12 control-label">客訴單號:</label>
			</td>
			<td>
				<div class="col-sm-10">
			  		<input id="cplNo" name="cplNo" class="form-control" maxlength="20"/>
			  	</div>
			</td>
		</tr>
		<tr>
			<td>
				<div class="clearfix">&nbsp;</div>
			</td>
		</tr>
		<tr>
			<td width="10%" nowrap="nowrap">
			  <label class="col-sm-12 control-label">申請日期:</label>
			</td>
			<td width="23%">
			  <fmt:formatDate value="${siteWork.APL_TIME}" pattern="yyyy/MM/dd HH:mm" var="apptime" />
				<p style="padding-top: 15px; padding-left: 5px;" id="showApplyTime">
					<c:out value="${apptime}" />
				</p>
				<c:if test="${siteWork.APL_TIME ne null}">
					<input id="applyTime" name="apllyTime" type="hidden"></input>
				</c:if>
			</td>
				
			<td width="10%" nowrap="nowrap">
			  <label class="col-sm-12 control-label">完工日期 :</label>
			</td>
			<td width="23%">
				<fmt:formatDate value="${siteWork.END_TIME}" pattern="yyyy/MM/dd HH:mm" var="endTime" />
				<p style="padding-top: 15px; padding-left: 5px;" id="showEndTime">
					<c:out value="${endTime}" />
				</p>
				<c:if test="${siteWork.END_TIME ne null}">
					<input id="endTime" name="endTime" type="hidden" value=""></input>
				</c:if>
			</td>
		</tr>
		<tr>
			<td>
				<div class="clearfix">&nbsp;</div>
			</td>
		</tr>

		<tr>
			<td valign='top' width="10%" nowrap="nowrap" style="padding-top: 15px;">
			  <label class="col-sm-12 control-label">作業說明 :</label>
			 </td>
			<td colspan="5" nowrap="nowrap">
				<div style="margin-top: 15px">
					<textarea id="workDesc" name="workDesc" style="resize: none; width:94%; border-left-width:1px; 
					margin-left: 15px; margin-top: 4px;" rows="3">${siteWork.WORK_DESC}</textarea>
				</div>
			</td>
		</tr>
		<tr>
			<td>
			  <label class="col-sm-12 control-label" style="margin-bottom: 45px">作業工單 :</label>
			</td>
			<td colspan="5" nowrap="nowrap">
				<div style="margin-left: 16px; margin-top: 15px">
					<table id="workOrderTable"	class="table table-bordered  table-hover table-heading table-datatable"	width="96%">
						<thead>
							<tr>
							    <th>選擇</th>
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
			<td>
			  <label class="col-sm-12 control-label" style="margin-bottom: 80px">簽核意見 :</label>
			 </td>
			<td colspan="5">
				<div class="form-group" style="margin-left: 15px; width: 94%; margin-bottom: 65px;">
					<c:import url="/commom/signHistory">
						<c:param name="processType" value="${processType}"></c:param>
						<c:param name="applyNo" value="${siteWork.WORK_ID}"></c:param>
					</c:import>
				</div>
			</td>
		</tr>
	</table>
	<!-- 勾選工項設定的value -->
<input id="checkBoxItemId" type="hidden"></input>
<input id="checkBoxItemName" type="hidden" value="${orderNameStr}"></input>
</body>
</html>