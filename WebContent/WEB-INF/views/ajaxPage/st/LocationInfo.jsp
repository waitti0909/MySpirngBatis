<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<title>站點維護</title>
		<script type="text/javascript">
		$(document).ready(function() {
			
			if("${eventType}" == "view"){
				$("#addLocatiuonForm :input ").prop("disabled", true);
			}
		});//document ready end
		
		
		</script>
	</head>
	
	<body>
	<span id="locationInfoFrom">
		<div class="row">
			<div class="col-xs-12 col-sm-12">
				<div class=" ui-draggable ui-droppable">
					<div class="box-content">
						<form class="form-horizontal" id="addLocatiuonForm" action="" >
							
							<table style="width:100%">
								<tr id="ST001BtnTr">
									<td width="12%" nowrap="nowrap">
										<label class="col-sm-12 control-label">
											<button class="btn btn-primary btn-label-left" type="submit" id="locationSaveBtn" >
												<span><i class="fa fa-save"></i></span>
												<s:message code="button.save" text="Save" />
											</button>
											
										</label>
									</td>
									<td width="30%">
										<label class="col-sm-2 control-label">
											<button  class="btn btn-primary btn-label-left" type="button" id="addSiteBtn" >
												<span><i class="fa fa-rss"></i></span>
												<s:message  text="建立基站" />
											</button>
										</label>
									</td>
									<td width="9%"></td>
									<td width="20%"></td>
									<td width="9%"></td>
									<td width="20%"></td>
								</tr>
								<tr>
									<td nowrap="nowrap"><label class="col-sm-12 control-label">站點編號 :</label></td>
									<td>
										<p style="padding-top:20px; padding-left:15px;" id="locationIdText"></p>
										<input id="location_ID" name="LOCATION_ID" type="hidden"></input>
									</td>
									
									<td nowrap="nowrap"><label class="col-sm-12 control-label" ><span class="s">* </span>站點類別 :</label></td>
									<td>
									 	<div class="col-sm-12">
											<select id="loc_TYPE" name="LOC_TYPE" class="form-control" required="required">
												<option value="">---請選擇---</option>
												<c:forEach items="${allSiteTypes}" var="siteType">
													<option value="${siteType.value}">${siteType.label}</option>
												</c:forEach>
											</select>
										</div>
									</td>
									<td><label class="col-sm-12 control-label" >共構/共站 :</label></td>
									<td>
										<div class="col-sm-10">
											<select id="share_TYPE" name="SHARE_TYPE" class="form-control">
												<option value="0">無</option>
												<option value="1">共構</option>
												<option value="2">共站</option>
											</select>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="clearfix">&nbsp;</div>
									</td>
								</tr>
								<tr >
									<td><label style="padding-left:5px;" class="col-sm-12 control-label"><span class="s">* </span>站點名稱 :</label></td>
									<td>	
										<div class="col-sm-14" style="margin-left :16px">
											<input id="loc_NAME" name="LOC_NAME"  type="text" class="form-control" required="required" maxlength="100">
										</div>
									</td>
									<td><label class="col-sm-12 control-label">機房型態 :</label></td>
									<td>
										<div class="col-sm-14" style="margin-left :16px">
											<select id="base_TYPE" name="BASE_TYPE" class="form-control" name="siteSelect" >
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
											<select id="indoor_OPTION" name="INDOOR_OPTION" class="form-control" name="siteSelect">
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
											<select id="city" name="CITY" class="form-control" required="required">
											</select>
										</div>
										<div class="col-sm-6" style="padding-left :5px;padding-right :0px;">
											<select id="siteLocArea" name="AREA" class="form-control" required="required">
											</select>
										</div>
									</td>
									<td><label class="col-sm-12 control-label"><span class="s">* </span>經度 :</label></td>
									<td>
										<div class="col-sm-12" >
											<input id="lon" name="LON" type="text" class="form-control" required="required" placeholder="例:123.123456" maxlength="10">
										</div>
									</td>
									<td><label class="col-sm-12 control-label"><span class="s">* </span>緯度 :</label></td>
									<td>
										<div class="col-sm-12">
											<input id="lat" name="LAT"  type="text" class="form-control" required="required" placeholder="例:123.123456" maxlength="10">
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="clearfix">&nbsp;</div>
									</td>
								</tr>
								<tr >
									<td><label class="col-sm-12 control-label"><span class="s">* </span>地址 :</label></td>
									<td colspan="5" nowrap="nowrap">
										<div class="col-sm-11">
											<input id="addr" name="ADDR" type="text" class="form-control" placeholder="請點選更新地址按鈕" readonly="readonly" >
										</div>
										<input style="padding-left:0px;" id="updateAddrButton" type="button" value="更新地址" />
										<input type="hidden" name="ZIP" id="zip"/>
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
										<input type="hidden" name="disabledFields" id="disabledFields" value="city,area" />
										<input id="siteLocationCallBackFun" type="hidden" value="siteLocationCallBackFun" />
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
											<select id="maint_AREA" name="MAINT_AREA" class="form-control" name="siteSelect" required="required">
												<option value="">---請選擇---</option>
											</select>
										</div>
									</td>
									<td><label class="col-sm-12 control-label"><span class="s">* </span>歸屬單位 :</label></td>
									<td>
										<div class="col-sm-14" style="margin-left :16px">
											<select id="maint_DEPT" name="MAINT_DEPT" class="form-control" name="siteSelect" required="required">
												<option value="">---請選擇---</option>
											</select>
										</div>
									</td>
									<td><label class="col-sm-12 control-label" ><span class="s">* </span>負責單位 :</label></td>
									<td>
										<div class="col-sm-13" style="padding-right: 0px;margin-left:16px">
											<select id="maint_TEAM" name="MAINT_TEAM" class="form-control" name="siteSelect" required="required">
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
											<select id="maint_USER" name="MAINT_USER" class="form-control" name="siteSelect" required="required">
												<option value="">---請選擇---</option>
											</select>
										</div>
									</td>
									<td><label style="padding-left:5px;" class="col-sm-12 control-label">NS_LEVEL :</label></td>
									<td>
										<div class="col-sm-12">
											<select id="ns_LEVEL" name="NS_LEVEL" class="form-control" name="siteSelect" >
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
											<input id="building_HEIGHT" name="BUILDING_HEIGHT" type="text" class="form-control" required="required" placeholder="例:123.1" maxlength="5">
										</div>
										<label style="padding-left:0px" class="col-sm-1 control-label">(m)</label>
									</td>
								</tr>
								<tr>
									<td>
										<div class="clearfix">&nbsp;</div>
									</td>
								</tr>
								<tr >
									<td><label class="col-sm-12 control-label">共站業者 :</label></td>
									<td colspan="3" nowrap="nowrap">
										<div class="row form-group" style="padding-left : 15px;">
											<div class="col-sm-10" id='shareComDiv'>			   
												<c:forEach items="${allShareCom}" var="shareCom">
													<div class="checkbox-inline">
														<label>
															<input id="${shareCom.value}" name="SHARE_COM" value="${shareCom.value}" type="checkbox" onclick="unselect();">${shareCom.label}
															<i class="fa fa-square-o"></i>
														</label>
													</div>
												</c:forEach>
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
									<td><label class="col-sm-12 control-label">空間 :</label></td>
									<td colspan="3" nowrap="nowrap">
										<div class="row form-group" style="padding-left : 15px;">
											<div class="col-sm-10">
												<div class="checkbox-inline">
													<label>
														<input id="space_ROOM" name="SPACE_ROOM" onclick="changeValue('space_ROOM');" value="N" type="checkbox"> 房屋
														<i class="fa fa-square-o"></i>
													</label>
												</div>
												<div class="checkbox-inline">
													<label>
														<input id="space_ROOF" name="SPACE_ROOF" onclick="changeValue('space_ROOF');" value="N" type="checkbox"> 樓頂
														<i class="fa fa-square-o"></i>
													</label>
												</div>
												<div class="checkbox-inline">
													<label>
														<input id="space_TOP" name="SPACE_TOP" onclick="changeValue('space_TOP');" value="N"  type="checkbox"> 屋凸
														<i class="fa fa-square-o"></i>
													</label>
												</div>
												<div class="checkbox-inline">
													<label>
														<input id="space_PLATFORM" name="SPACE_PLATFORM" onclick="changeValue('space_PLATFORM');" value="N" type="checkbox" > 平台
														<i class="fa fa-square-o"></i>
													</label>
												</div>
												<div class="checkbox-inline">
													<label>
														<input id="space_LAND" name="SPACE_LAND" onclick="changeValue('space_LAND');" value="N" type="checkbox"> 土地
														<i class="fa fa-square-o"></i>
													</label>
												</div>
												<div class="checkbox-inline">
													<label>
														<input id="space_INDOOR" name="SPACE_INDOOR" onclick="changeValue('space_INDOOR');" value="N" type="checkbox"> 室內
														<i class="fa fa-square-o"></i>
													</label>
												</div>
												<div class="checkbox-inline">
													<label>
														<input id="space_OUTDOOR" name="SPACE_OUTDOOR" onclick="changeValue('space_OUTDOOR');" value="N" type="checkbox"> 室外
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
									<td><label class="col-sm-12 control-label">與員工關係 :</label></td>
									<td>
										<div class="col-sm-8" style="padding-right:5px" >
											<input id="emp_RELATION" name="EMP_RELATION" type="text" class="form-control" maxlength="50">
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
													<label><input type="checkbox" id="multibandLocation"> 多頻段站點
														<i class="fa fa-square-o"></i>
													</label>
												</div>
												<div class="checkbox-inline">
													<label>
														<input id="is_FREEENTER" name="IS_FREEENTER" onclick="changeValue('is_FREEENTER');" value="N" type="checkbox"> 是否可自由進入
														<i class="fa fa-square-o"></i>
													</label>
												</div>
												<div class="checkbox-inline">
													<label>
														<input id="is_KEY" name="IS_KEY" onclick="changeValue('is_KEY');" value="N"  type="checkbox"> 是否有鑰匙
														<i class="fa fa-square-o"></i>
													</label>
												</div>
												<div class="checkbox-inline">
													<label>
														<input id="is_INDOOR" name="IS_INDOOR" onclick="changeValue('is_INDOOR');" value="N" type="checkbox" > 是否為室內
														<i class="fa fa-square-o"></i>
													</label>
												</div>
												<div class="checkbox-inline">
													<label>
														<input id="need_MISC_LIC" name="NEED_MISC_LIC" onclick="changeValue('need_MISC_LIC');" value="N" type="checkbox"> 需要雜項執照
														<i class="fa fa-square-o"></i>
													</label>
												</div>
												<div class="checkbox-inline">
													<label>
														<input id="has_MISC_LIC" name="HAS_MISC_LIC" onclick="changeValue('has_MISC_LIC');" value="N" type="checkbox"> 已有雜項執照
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
										<textarea id="loc_DESC" name="LOC_DESC" style="resize: none;width:730px;border-left-width:1px;margin-left:15px;margin-top:4px;" rows="3"><c:if test="${siteLocationDesc != null}">${siteLocationDesc}</c:if></textarea>
									</td>
								</tr>
							</table>
							<input type="hidden" id="siteLocEvent" value="${siteLocEvent}"></input>	
						</form>
					</div>
				</div>
			</div>
		</div>
		<div id="showSiteLocAddrPage"></div>
		</span>
	</body>
	
</html>