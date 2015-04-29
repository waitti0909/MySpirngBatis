<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<title>Search Ring ID</title>
		<script type="text/javascript">
		var currentSrKey = "";
		$(document).ready(function() {
			$("#searchRecordDownload").hide();	// modify by Charlie 20150306 羅舒筠 said.
			buildCityOptions("srCity","srArea");
			if("${status}" == "view"){
				$("#addSearchRIngForm :input").prop("disabled" ,true);
				$("#searchRecordDownload").prop("disabled" ,false);
				$("#searchRecordDownload").show();	// 附件下載鈕要開
			}
			$("#searchRingEvent").val("${searchRingEvent}");
			if($("#searchRingEvent").val() == "update"){
				$("#showSrId").text("${searchRing.SR_ID}");
				$("#srId").val("${searchRing.SR_ID}");
				$("#srFeqId").val("${searchRing.FEQ_ID}").change();
				$("#srCity").val("${searchRing.SR_CITY}").change().prop("disabled",true);
				$("#srArea").val("${searchRing.SR_AREA}").change().prop("disabled",true);
				$("#srLon").val("${searchRing.SR_LON}");
				$("#srLat").val("${searchRing.SR_LAT}");
				$("#srCoverRange").val("${searchRing.SR_COVER_RANGE}");
				var existSiteWork = "${existSiteWork}";
				var existLocation = "${existLocation}";
				if(existSiteWork > 0 || existLocation > 0){
					$("#srLon").prop("readonly",true);
					$("#srLat").prop("readonly",true);
					$("#srFeqId").prop("disabled",true);
					
				}
// 				$("#srCity").prop("disabled",true);
// 				$("#srArea").prop("disabled",true);
			}
			table = $('#siteSearchTable').dataTable({
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
						+ '/st/sr/searchRing/search/siteSearchTable?srId='+$("#srId").val(),
				"aoColumnDefs" : [ {
					"aTargets" : [ 0 ],
					"mData" : "work_ID"
				}, {
					"aTargets" : [ 1 ],
					"mData" : "btsSiteId"
				}, {
					"aTargets" : [ 2 ],
					"mData" : "sr_SEQ"
				}, {
					"aTargets" : [ 3 ],
					"mData" : "addr"
				}, {
					"aTargets" : [ 4 ],
					"mData" : "sr_DATE","mRender": function ( data, type, full ) {
						var srDate = new Date(data)
						return srDate.getFullYear()+"/"+(srDate.getMonth()+1)+"/"+srDate.getDay();
						
	            	}
				},  {
					"aTargets" : [ 5 ],
					"mData" : "sr_PSN"
				},  {
					"aTargets" : [ 6 ],
					"mData" : "location_ID"
				}, 
				],
				"oLanguage" : {
					"sProcessing" : "處理中...",
					"sZeroRecords" : "沒有匹配結果",
					"sLoadingRecords" : "讀取中..."
				},
				"fnInitComplete" : function() {
					this.fnAdjustColumnSizing();
				},
			});
			
			$('#siteSearchTable tbody').off('click', 'tr').on( 'click','tr', function () {
				var data = $('#siteSearchTable').dataTable().fnGetData($(this).closest("tr").index());
				if(data != null){
					$.ajax({
						url : CONTEXT_URL + "/st/sr/searchRing/search/siteSearch",
						type : 'POST',
						data : {
							srSeq :　data['sr_SEQ'],
							srId : $("#srId").val(), 
						},
						async : false,
						success : function(data) {
							$('input[type="checkbox"]').each(function() {
						         $(this).prop("checked", false);
						     });
							currentSrKey = $("#srId").val() + "S" + data['sr_SEQ'];
							$("#siteSearchRecord").prop("hidden",false);
							$("#showSrSeq").text(data.sr_SEQ);
							$("#srSeq").val(data.sr_SEQ);
							$("#showBtsSiteId").text(data.btsSiteId);
							if(data.search_RESULT == "Y"){
								$("#srResult").text("已核可");
							}else{
								$("#srResult").text("未核可");
							}
							$("#srDate").text(data.sr_DATE);
							$("#srPsn").text(data.chnName);
							//探勘評估
							$("#srAddr").text(data.addr);
							$("#siteSearchLon").text(data.lon);
							$("#siteSearchLan").text(data.lat);
							$("#srBaseType").text(data.base_TYPE);
							$("#srBuildingHeight").text(data.building_HEIGHT);
							$("#srBuildingFloor").text(data.building_FLOOR);
							$("#srIndoorOption").text(data.indoor_OPTION);
							if(data.is_FREEENTER == "Y"){
								$("#srIsFreeenter").prop("checked",true);	
							}
							if(data.is_KEY == "Y"){
								$("#srIsKey").prop("checked",true);	
							}
							if(data.is_INDOOR == "Y"){
								$("#srIsIndoor").prop("checked",true);	
							}
							if(data.need_MISC_LIC == "Y"){
								$("#srNeedMiscLic").prop("checked",true);	
							}
							if(data.has_MISC_LIC == "Y"){
								$("#srHasMiscLic").prop("checked",true);	
							}
							if(data.line_REACHABLE == "Y"){
								$("#srLineReachable").prop("checked",true);	
							}
							$("#srExpectRent").text(data.expect_RENT);
							
							if(data.share_TYPE == 0){
								$("#shareType").text("無");
							}else if(data.share_TYPE == 1){
								$("#shareType").text("共構");
							}else if(data.share_TYPE == 2){
								$("#shareType").text("共站");
							}
							
							var shareComs = data.shareCom.split(",");
							for(var shareCom in shareComs){
								$("#"+shareComs[shareCom]).prop("checked",true);	
							}
							if(data.ant_STAND != null){
								$("#srAntStand").text(data.ant_STAND);
							}
							if(data.ant_C_HIGHT != null){
								$("#srAnt_S_Height").text(data.ant_C_HIGHT+" (m)");
							}
							if(data.ant_S_HIGHT != null){
								$("#srAnt_C_Height").text(data.ant_S_HIGHT+" (m)");
							}
							$("#srLocDesc").val(data.loc_DESC);
							$("#siteSearchRecord :input").prop("disabled",true);
						}
					});
				}
			});
			
			$("#searchRingSaveBtn").click(function(){
				
				if ($('#addSearchRIngForm')[0].checkValidity()) {
					event.preventDefault();
					var errorMessages = [];
					var lonArray = $("#srLon").val().split(".");
					var lon = $("#srLon").val();
					if(!validateNumber(lon,3,6)){
						errorMessages [errorMessages.length]="經度格式錯誤，整數最多三位、小數最多為六位";
					}
					if(lon < 115 || lon > 124){
						errorMessages [errorMessages.length]="經度需介於115~124之間";
					}
					var lat = $("#srLat").val();
					if(!validateNumber(lat,3,6)){
						errorMessages [errorMessages.length]="緯度格式錯誤，整數最多三位、小數最多為六位";
					}
					if(lat < 18 || lat > 28){
						errorMessages [errorMessages.length]="緯度需介於：18~28之間";
					}
					if(errorMessages.length!=0){
						var msg ="";
						for(var em in errorMessages){
							msg += errorMessages[em]+"\n";
						}
						alert(msg);
						return false;
					}
					//驗證
					var message ="";
					$.ajax({
						url : CONTEXT_URL + "/st/sr/searchRing/doValidate",
						type : 'POST',
						data : $("#addSearchRIngForm").serialize(),
						async : false,
						success : function(data) {
							message = data.row;
						}
					});
					if(message!=""){
						if(!confirm(message+"，是否要儲存?")){
							return false;
						}
					}
					if(!confirm("確定要儲存?")){
						return false;
					}
					var attr="";
					if($("#srCity").prop("disabled") && $("#srArea").prop("disabled")){
						attr += "&SR_CITY="+$("#srCity").val()+"&SR_AREA="+$("#srArea").val();
					}
					//儲存
					$.ajax({
						url : CONTEXT_URL + "/st/sr/searchRing/save",
						type : 'POST',
						data : $("#addSearchRIngForm").serialize()+"&searchRingEvent="+$("#searchRingEvent").val(),
						async : false,
						success : function(data) {
							var result = data['result'];
							var srEvent = $("#searchRingEvent").val();
							if(result == true){
								var searchRing = data['searchRing'];
								if(srEvent == "insert"){
									$("#srId").val(searchRing.sr_ID);
									alert('<s:message code="msg.add.success"/>');
								}else{
									alert('<s:message code="msg.update.success"/>');
								}
								$.fancybox.close();
								var data= {
										srId : $("#srId").val(),
										lon : $("#srLon").val(),
										lat : $("#srLat").val(),
										coverRange : $("#srCoverRange").val(),
										city : $("#srCity").val(),
										area : $("#srArea").val(),
										feqId : $("#srFeqId").val(),
								};	
								$("#searchRingId").val($("#srId").val());
								$("#searchRingLon").val($("#srLon").val());
								$("#searchRingLat").val($("#srLat").val());
								$("#searchRingCoverRange").val($("#srCoverRange").val());
								$("#searchRingCity").val($("#srCity").val()).change();
								$("#searchRingArea").val($("#srArea").val()).change();
								$("#feqId").val($("#srFeqId").val()).change();
								$("#grid").setGridParam({page:1,datatype:"json", postData:data, mtype: 'POST'}).jqGrid().trigger("reloadGrid");
							}else{
								alert(result);
							}
						}
					});
				}
			});
		});//ready end
		
		function downloadSearchRecordDoc() {
			if (currentSrKey == "") {
				alert("請先點選一筆探勘資料");
			} else {
				openFileDownFrame("showSearchRecordDownPage", "SEARCH", currentSrKey, "");
			}
		}
		</script>
	</head>
	<body>
		<div id="showSearchRecordDownPage"></div>
		<div class="row">
			<div class="col-xs-12 col-sm-12">
				<div class=" ui-draggable ui-droppable">
					<div class="box-header">
						<div class="box-name">
							<i class="fa fa-search"></i> <span>Search Ring 維護</span>
						</div>
						<div class="no-move"></div>
					</div>
					<div class="box-content">
						<div class="box-content">
							<form class="form-horizontal" id="addSearchRIngForm" action="" >
								<table style="width: 100%">
									<tr>
										<td width="5%" >
											<button class="btn btn-primary btn-label-left" style="margin-left: 30px"
												type="submit" id="searchRingSaveBtn"><span><i class="fa fa-save"></i></span>存檔</button>&nbsp;
										</td>
										<td width="18%" nowrap="nowrap">
											<button class="btn btn-primary btn-label-left" style="margin-left: 30px; margin-bottom :20px;" onclick="downloadSearchRecordDoc();"  
												type="button" id="searchRecordDownload"><span><i class="fa fa-download"></i></span>附件下載</button>
										</td>
										<td width="10%"></td>
										<td width="34%"></td>
										<td width="10%"></td>
										<td width="18%"></td>
									</tr>
									<tr>
										<td nowrap="nowrap">
											<label class="col-sm-10 control-label">SR_ID :</label>
										</td>
										<td nowrap="nowrap">
											<p style="padding-top: 15px;margin-left:16px; " id="showSrId"></p>
											<input type="hidden" id="srId" name="SR_ID">
										</td>
										<td nowrap="nowrap"></td>
										<td nowrap="nowrap"></td>
										<td nowrap="nowrap"></td>
										<td nowrap="nowrap"></td>
									</tr>
									<tr>
										<td>
											<div class="clearfix">&nbsp;</div>
										</td>
									</tr>	
									<tr>
										<td nowrap="nowrap"><label class="col-sm-10 control-label"><span class="s">* </span>基站頻段 :</label></td>
										<td nowrap="nowrap">
											<div class="col-sm-12">
												<select id="srFeqId" name="FEQ_ID" class="form-control" required="required">
													<option value="">--- 請選擇 ---</option>
													<option value="all">共用</option>
													<c:forEach items="${allSiteFeq}" var="siteFeq">
														<option value="${siteFeq.value}">${siteFeq.label}</option>
													</c:forEach>
												</select>
											</div>
										</td>
										<td nowrap="nowrap"><label class="col-sm-10 control-label"><span class="s">* </span>行政區 :</label></td>
										<td nowrap="nowrap">
											<div class="col-sm-6">
												<select id="srCity" name="SR_CITY" class="form-control"
													required="required">
												</select>
											</div>
											<div class="col-sm-6" style="margin-left: -25px;">
												<select id="srArea" name="SR_AREA" class="form-control"
													required="required">
												</select>
											</div>
										</td>
										<td nowrap="nowrap"></td>
										<td nowrap="nowrap"></td>
									</tr>
									<tr>
										<td>
											<div class="clearfix">&nbsp;</div>
										</td>
									</tr>							
									<tr>
										<td><label class="col-sm-10 control-label"><span class="s">* </span>經度 :</label></td>
										<td>
											<div class="col-sm-12">
												<input id="srLon" name="SR_LON" type="text" class="form-control" required="required" placeholder="例:123.123456" maxlength="10"></input>
											</div>
										</td>
										<td><label class="col-sm-10 control-label"><span class="s">* </span>緯度 :</label></td>
										<td>
											<div class="col-sm-6">
												<input id="srLat" name="SR_LAT" type="text" class="form-control" required="required" placeholder="例:123.123456" maxlength="10"></input>
											</div>
										</td>
										<td nowrap><label class="col-sm-10 control-label"><span class="s">* </span>涵蓋區域 :</label></td>
										<td>
											<div class="col-sm-10">
												<input id="srCoverRange" name="SR_COVER_RANGE" type="text" class="form-control" required="required" maxlength="200"></input>
											</div>
										</td>
									</tr>
									<tr>
										<td>
											<div class="clearfix">&nbsp;</div>
										</td>
									</tr>
									<tr>
										<td>
											<div class="clearfix">&nbsp;</div>
										</td>
									</tr>
									<tr>
										<td colspan="6">
											<div style="margin-left: 16px;">
												<table id="siteSearchTable"
													class="table table-bordered  table-hover table-heading table-datatable"
													width="96%">
													<thead>
														<tr>
															<th>作業編號</th>
															<th>基站編號</th>
															<th nowrap="nowrap">探勘序號</th>
															<th>探勘地址</th>
															<th>探勘日期</th>
															<th>探勘人員</th>
															<th>站點編號</th>
														</tr>
													</thead>
													<tbody>
													</tbody>
												</table>
											</div>
										</td>
									</tr>
								</table>
								<table id ="siteSearchRecord" hidden="true">
									<tr>
										<td width="10%"><label class="col-sm-10 control-label">探勘序號 :</label></td>
										<td width="18%">
											<p style="padding-top: 15px;" id="showSrSeq"></p>
											<input id="srSeq" name="SR_SEQ" type="hidden" />
										</td>
										<td width="10%"><label class="col-sm-10 control-label">基站編號 :</label></td>
										<td width="34%">
											<p style="padding-top: 15px;" id="showBtsSiteId"></p>
										</td>
										<td width="10%"><label class="col-sm-10 control-label">探勘結果 :</label></td>
										<td width="18%">
											<p style="padding-top: 15px;" id="srResult"></p>
										</td>
									</tr>
									<tr>
										<td><label class="col-sm-10 control-label">探勘日期 :</label></td>
										<td>
											<p style="padding-top: 15px;" id="srDate"></p>
										</td>
										<td><label class="col-sm-10 control-label">探勘人員 :</label></td>
										<td>
											<p style="padding-top: 15px;" id="srPsn"></p>
										</td>
										<td><label class="col-sm-10 control-label">探勘評估 :</label></td>
										<td>
											<p style="padding-top: 15px;" id=""></p>
										</td>
									</tr>
									<tr>
										<td><label class="col-sm-10 control-label">探勘地址 :</label></td>
										<td colspan="5">
											<p style="padding-top: 15px;" id="srAddr"></p>
										</td>
									</tr>
									<tr>
										<td><label class="col-sm-10 control-label">經度 :</label></td>
										<td>
											<p style="padding-top: 15px;" id="siteSearchLon"></p>
										</td>
										<td><label class="col-sm-10 control-label">緯度 :</label></td>
										<td>
											<p style="padding-top: 15px;" id="siteSearchLan"></p>
										</td>
										<td><label class="col-sm-10 control-label">機房型態 :</label></td>
										<td>
											<p style="padding-top: 15px;" id="srBaseType"></p>
										</td>
									</tr>
									<tr>
										<td><label class="col-sm-10 control-label">建築高度 :</label></td>
										<td>
											<p style="padding-top: 15px;" id="srBuildingHeight"></p>
										</td>
										<td><label class="col-sm-10 control-label">建物樓層 :</label></td>
										<td>
											<p style="padding-top: 15px;" id="srBuildingFloor"></p>
										</td>
										<td nowrap="nowrap"><label class="col-sm-10 control-label" style="padding-left :0px;padding-right :0px">室內場所分類 :</label></td>
										<td>
											<p style="padding-top: 15px;" id="srIndoorOption"></p>
										</td>
									</tr>
									<tr>
										<td><label class="col-sm-10 control-label">其他資訊 :</label></td>
										<td colspan="6">
											<div class="row form-group">
												<div class="col-sm-10">
													<div class="checkbox-inline">
														<label>
															<input id="srIsFreeenter" name="otherMsg" type="checkbox">是否可自由進入
															<i class="fa fa-square-o"></i>
														</label>
													</div>
													
													<div class="checkbox-inline">
														<label>
															<input id="srIsKey" name="otherMsg" type="checkbox">是否有鑰匙
															<i class="fa fa-square-o"></i>
														</label>
													</div>
													<div class="checkbox-inline">
														<label>
															<input id="srIsIndoor" name="otherMsg" type="checkbox">是否為室內
															<i class="fa fa-square-o"></i>
														</label>
													</div>
													<div class="checkbox-inline">
														<label>
															<input id="srNeedMiscLic" name="otherMsg" type="checkbox">需要雜項執照
															<i class="fa fa-square-o"></i>
														</label>
													</div>
													<div class="checkbox-inline">
														<label>
															<input id="srHasMiscLic" name="otherMsg" type="checkbox">已有雜項執照
															<i class="fa fa-square-o"></i>
														</label>
													</div>
													<div class="checkbox-inline">
														<label>	
															<input id="srLineReachable" name="otherMsg" type="checkbox">專線到達
															<i class="fa fa-square-o"></i>
														</label>
													</div>
												</div>
											</div>
										</td>
									</tr>
									<tr>
										<td><label class="col-sm-10 control-label">預估租金 :</label></td>
										<td>
											<p style="padding-top: 15px;" id="srExpectRent"></p>
										</td>
										<td><label class="col-sm-10 control-label">共構/共站 :</label></td>
										<td>
											<p style="padding-top: 15px;" id="shareType"></p>
										</td>
									</tr>
									<tr>
										<td><label class="col-sm-10 control-label">天線支架 :</label></td>
										<td>
											<p style="padding-top: 15px;" id="srAntStand"></p>
										</td>
										<td nowrap="nowrap"><label class="col-sm-10 control-label" style="padding-left :0px;padding-right :0px">天線架設高度 :</label></td>
										<td nowrap="nowrap">
											<p style="padding-top: 15px;" id="srAnt_S_Height"></p>
										</td>
										<td nowrap="nowrap"><label class="col-sm-10 control-label" style="padding-left :0px;padding-right :0px">天線中心高度 :</label></td>
										<td nowrap="nowrap">
											<p style="padding-top: 15px;" id="srAnt_C_Height"></p>
										</td>
									</tr>
									<tr>
										<td nowrap="nowrap"><label class="col-sm-10 control-label" style="padding-left :0px;padding-right :0px">共構/共站業者 :</label></td>
										<td colspan="6">
											<div class="row form-group">
												<div class="col-sm-10">
													<div class="checkbox-inline">
														<label>
															<input id="CHT" name="shareCom" type="checkbox">CHT
															<i class="fa fa-square-o"></i>
														</label>
													</div>
													<div class="checkbox-inline">
														<label>
															<input id="FET" name="shareCom" type="checkbox">FET
															<i class="fa fa-square-o"></i>
														</label>
													</div>
													<div class="checkbox-inline">
														<label>
															<input id="TWM" name="shareCom" type="checkbox">TWM
															<i class="fa fa-square-o"></i>
														</label>
													</div>
													<div class="checkbox-inline">
														<label>
															<input id="APT" name="shareCom"  type="checkbox">APT
															<i class="fa fa-square-o"></i>
														</label>
													</div>
												</div>
											</div>
										</td>
									</tr>
									<tr>
									<td><label class="col-sm-10 control-label" style="margin-bottom: 55px;">探勘註解 :</label></td>
									<td colspan="5" nowrap="nowrap">
										<textarea style="resize: none;width:96%;" rows="3" id="srLocDesc"></textarea>
									</td>
								</tr>
								</table>
								<input id="searchRingEvent" type="hidden" />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
