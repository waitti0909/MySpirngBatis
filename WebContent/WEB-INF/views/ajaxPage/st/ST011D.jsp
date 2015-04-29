<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html lang="en">
<head>
<title>基站基本資料</title>
	<script type="text/javascript">
	$(document).ready(function() {
		$( "#tabs" ).tabs();
		var omcuObj = "${siteDTO.OMCU_OBJ }";
		if(omcuObj != ""){
			if(omcuObj == 1){
				$("#omcuObj").text("未建立");
			}else{
				$("#omcuObj").text("已建立");
			}
		}
		var noisRepeater = "${siteDTO.NIOS_RPT_STATUS }";
		if(noisRepeater != ""){
			if(noisRepeater == 1){
				$("#noisRepeater").text("納入網管系統");
			}else{
				$("#noisRepeater").text("未納入網管系統");
			}
		}
		checkedOption("coverageIndoor" , "");
		checkedOption("hidden" , "${siteDTO.HIDDEN}");
		checkedOption("noisOnAir" , "${siteDTO.NOIS_ON_AIR}");
		checkedOption("l2Switch" , "${siteDTO.l2_SWITCH}");
// 		$("#updateAddrButton").click(function(){
// 			window.open('../common/address.html', '正規地址輸入', config='height=600,width=800').focus();
// 		});
		
// 		$("#locationSaveBtn").click(function(){
// 			window.close();
// 		});
		$("#tabsForm :input").prop("disabled", true);
	});//document ready end
	
	function unselect(){
		if($("#share_TYPE").val()==0){
			$("input[name=SHARE_COM]").attr("checked",false);
		}
	}
	
	//顯示基站檔案下載頁 add by Charlie 20150128
	function openSiteFileProcess() {
		openSiteFileDownFrame("showSiteFileDownloadFrame", "${siteDTO.SITE_ID}", "${siteDTO.BTS_SITE_ID}"); // This is implement in common.js
	}
	function checkedOption(tagId , tagValue){
		if(tagValue == "Y"){
			$('#'+tagId).prop("checked",true);
		}
	}
	</script>
</head>
<body>
<div class="box-header">
	<div class="box-name">
		<span>基站基本資料</span>
	</div>
	<div class="box-icons">			 
		<p class="expand-link"></p>
	</div>
	<div class="no-move"></div>
</div>
<div class="row">
	<div class="col-xs-12 col-sm-12">
		<div class=" ui-draggable ui-droppable">
			<div class="box-content">
				<form class="form-horizontal" id="addLocatiuonForm" action="" >
					
					<table style="width:100%">
						<tr>
							<td width="12%">
							<div class="col-sm-offset-3 col-sm-2">
								<button class="btn btn-primary btn-label-left" type="button" id="uploadFileBtn" onclick="openSiteFileProcess()">
									<span><i class="fa fa-download"></i></span>附件下載
								</button>
							</div>
							</td>
							<td width="20%"></td>
							<td width="15%"></td>
							<td width="15%"></td>
							<td width="15%"></td>
							<td width="15%"></td>
						</tr>
						<tr>
							<td nowrap="nowrap"><label class="col-sm-12 control-label">基站編號 :</label></td>
							<td>
								<p style="padding-top:17px; padding-left:15px;">${siteDTO.BTS_SITE_ID}</p>
							</td>
							
							<td nowrap="nowrap"><label class="col-sm-12 control-label" >基站名稱 :</label></td>
							<td>
							 	<p style="padding-top:17px; padding-left:15px;">${siteDTO.SITE_NAME}</p>
							</td>
							<td><label class="col-sm-12 control-label" >基站狀態 :</label></td>
							<td>
								<p style="padding-top:17px; padding-left:15px;">${siteDTO.siteStatusName}</p>
							</td>
						</tr>
						<tr >
							<td><label style="padding-left:5px;" class="col-sm-12 control-label"><span class="s">* </span>基站頻段 :</label></td>
							<td>	
								<p style="padding-top:17px; padding-left:15px;">${siteDTO.feqName}</p>
							</td>
							<td><label class="col-sm-12 control-label">設備型態 :</label></td>
							<td>
								<p style="padding-top:17px; padding-left:15px;">${siteDTO.eqpName}</p>
							</td>
							<td><label style="padding-left:5px;" class="col-sm-12 control-label">設備機型 :</label></td>
							<td>
								<p style="padding-top:17px; padding-left:15px;">${siteDTO.eqpModel}</p>
							</td>
						</tr>
						<tr>
							<td><label class="col-sm-12 control-label">Feederless :</label></td>
							<td nowrap="nowrap">
								<p style="padding-top:17px; padding-left:15px;">${siteDTO.feederlessName }</p>
							</td>
							<td><label class="col-sm-12 control-label">Master Site :</label></td>
							<td>
								<p style="padding-top:17px; padding-left:15px;">${siteDTO.MASTER_SITE }</p>
							</td>
							<td><label class="col-sm-12 control-label">Configuration :</label></td>
							<td>
								<p style="padding-top:17px; padding-left:15px;">${siteDTO.BTS_CONFIG }</p>
							</td>
						</tr>
						<tr>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td><label class="col-sm-12 control-label" >Donor BTS :</label></td>
							<td>
								<p style="padding-top:17px; padding-left:15px;">${siteDTO.DONOR_SITE }</p>
							</td>
						</tr>
						<tr>
							<td><label class="col-sm-12 control-label">涵蓋類別 :</label></td>
							<td>
								<p style="padding-top:17px; padding-left:15px;">${siteDTO.coverageTypeName }</p>
							</td>
							<td><label style="padding-left:5px;" class="col-sm-12 control-label">室內天線涵蓋樓層 :</label></td>
							<td>
								<p style="padding-top:17px; padding-left:15px;">${siteDTO.COVERAGE_IN_FLOOR }</p>
							</td>
							<td><label class="col-sm-12 control-label">功率 :</label></td>
							<td nowrap="nowrap">
								<p style="padding-top:17px; padding-left:15px;">${siteDTO.POWER }mW</p>
							</td>
						</tr>
						
						<tr>
							<td><label class="col-sm-12 control-label">Cluster :</label></td>
							<td>
								<p style="padding-top:17px; padding-left:15px;">${siteDTO.CLUSTER }</p>
							</td>
							<td><label class="col-sm-12 control-label">RNC-BTS :</label></td>
							<td>
								<p style="padding-top:17px; padding-left:15px;">${siteDTO.RNC_BTS }</p>
							</td>
						</tr>
						<tr>
							<td><label class="col-sm-12 control-label">Cell Status :</label></td>
							<td>
								<p style="padding-top:17px; padding-left:15px;">${siteDTO.CELL_STATUS }</p>
							</td>
							<td><label class="col-sm-12 control-label">OMCu Object :</label></td>
							<td>
								<p style="padding-top:17px; padding-left:15px;" id="omcuObj"></p>
							</td>
							<td><label class="col-sm-12 control-label">NOIS repeater狀態 :</label></td>
							<td>
								<p style="padding-top:17px; padding-left:15px;" id="noisRepeater"></p>
							</td>
						</tr>
						<tr>
							<td><label class="col-sm-12 control-label">經度 :</label></td>
							<td>
								<p style="padding-top:17px; padding-left:15px;">${siteDTO.LON }</p>
							</td>
							<td><label class="col-sm-12 control-label">緯度 :</label></td>
							<td>
								<p style="padding-top:17px; padding-left:15px;">${siteDTO.LAT }</p>
							</td>
						</tr>
						<tr >
							<td colspan="4" nowrap="nowrap" style="padding-left : 60px;">
								<div class="row form-group">
									<div class="col-sm-10">
										<div class="checkbox-inline">
											<label>
												<input type="checkbox" id="coverageIndoor" disabled > 有室內涵蓋
												<i class="fa fa-square-o"></i>
											</label>
										</div>
										<div class="checkbox-inline">
											<label>
												<input type="checkbox" id="hidden" disabled> 有美化施工
												<i class="fa fa-square-o"></i>
											</label>
										</div>
										<div class="checkbox-inline">
											<label>
												<input type="checkbox" id="noisOnAir" disabled> 網管On Air
												<i class="fa fa-square-o"></i>
											</label>
										</div>
										<div class="checkbox-inline">
											<label>
												<input type="checkbox" id="l2Switch" disabled> 有接L2_SWITCH
												<i class="fa fa-square-o"></i>
											</label>
										</div>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td><label class="col-sm-12 control-label">簽約日期 :</label></td>
							<td>
								<p style="padding-top:17px; padding-left:15px;"><fmt:formatDate value="${ siteDTO.signDate }" pattern="yyyy/MM/dd" /></p>
							</td>
							<td><label class="col-sm-12 control-label">Integrated日期 :</label></td>
							<td>
								<p style="padding-top:17px; padding-left:15px;"><fmt:formatDate value="${ siteDTO.INTEGRATED_DATE }" pattern="yyyy/MM/dd" /></p>
							</td>
						</tr>
						<tr>
							<td><label class="col-sm-12 control-label">On Air日期 :</label></td>
							<td>
								<p style="padding-top:17px; padding-left:15px;"><fmt:formatDate value="${siteDTO.ONAIR_DATE }"  pattern="yyyy/MM/dd"/></p>
							</td>
							<td><label class="col-sm-12 control-label">停用日期 :</label></td>
							<td>
								<p style="padding-top:17px; padding-left:15px;"><fmt:formatDate value="${siteDTO.SUSPEND_DATE }"  pattern="yyyy/MM/dd"/></p>
							</td>
							<td hidden="hidden"><label class="col-sm-12 control-label">預計恢復日期 :</label></td>
							<td hidden="hidden">
								<p style="padding-top:17px; padding-left:15px;"><fmt:formatDate value="${siteDTO.RESTORE_DATE }"  pattern="yyyy/MM/dd"/> </p>
							</td>
						</tr>
					</table>
					
				</form>
			</div>
		</div>
	</div>
</div>
<div class="row">
	<div class="col-xs-12 col-sm-12">
		<div class=" ui-draggable ui-droppable">
			<div class="box-content">
				<form class="form-horizontal" method="post" id="tabsForm">
					<div id="tabs">
						<ul>
							<li><a href="#tabs-1">站點資訊</a></li>
							<li><a href="#tabs-2">天線組態</a></li>
						</ul>
						<div id="tabs-1">
							<table style="width:100%" >
								<tr>
									<td nowrap="nowrap" width="12%"><label class="col-sm-12 control-label">站點編號 :</label></td>
									<td width="26%">
										<p style="padding-top:17px; padding-left:15px;" id="locationId">${SiteLocationDTO.LOCATION_ID }</p>
									</td>
									
									<td nowrap="nowrap" width="15%"><label class="col-sm-12 control-label" ><span class="s">* </span>站點類別 :</label></td>
									<td width="15%">
										<p style="padding-top:17px; padding-left:15px;" id="locType">${SiteLocationDTO.locTypeName }</p>
									</td>
									<td width="15%"><label class="col-sm-12 control-label" >共構/共站 :</label></td>
									<td width="15%">
										<p style="padding-top:17px; padding-left:15px;" id="shareType">
											
											<c:if test="${SiteLocationDTO.SHARE_TYPE eq '1' }">
												<c:out value="共站"/>
											</c:if>
											<c:if test="${SiteLocationDTO.SHARE_TYPE eq '2' }">
												<c:out value="共構"/>
											</c:if>
											<c:if test="${SiteLocationDTO.SHARE_TYPE eq '0' or  empty SiteLocationDTO.SHARE_TYPE }">
												<c:out value="無"/>
											</c:if>
										</p>
									</td>
								</tr>
								<tr >
									<td><label style="padding-left:5px;" class="col-sm-12 control-label">站點名稱 :</label></td>
									<td>
										<p style="padding-top:17px; padding-left:15px;" id="locationName">${SiteLocationDTO.LOC_NAME }</p>
									</td>
									<td><label class="col-sm-12 control-label">機房型態 :</label></td>
									<td>
										<p style="padding-top:17px; padding-left:15px;" id=baseTypeName>${SiteLocationDTO.baseTypeName }</p>
									</td>
									<td><label style="padding-left:5px;" class="col-sm-12 control-label">室內場所分類 :</label></td>
									<td>
										<p style="padding-top:17px; padding-left:15px;" id="indoorOptionName">${SiteLocationDTO.indoorOptionName }</p>
									</td>
								</tr>
								<tr>
									<td><label class="col-sm-12 control-label"><span class="s">* </span>區域 :</label></td>
									<td>
										<p style="padding-top:17px; padding-left:15px;" id="cityAndArea">
									        ${SiteLocationDTO.ZIP }${SiteLocationDTO.cityName }${SiteLocationDTO.townName }
										</p>
									</td>
									<td><label class="col-sm-12 control-label"><span class="s">* </span>經度 :</label></td>
									<td>
										<p style="padding-top:17px; padding-left:15px;" id="siteLon">${SiteLocationDTO.LON }</p>
									</td>
									<td><label class="col-sm-12 control-label"><span class="s">* </span>緯度 :</label></td>
									<td>
										<p style="padding-top:17px; padding-left:15px;" id="siteLat">${SiteLocationDTO.LAT }</p>
									</td>
								</tr>
								<tr >
									<td><label class="col-sm-12 control-label">地址 :</label></td>
									<td colspan="5">
										<p style="padding-top:17px; padding-left:15px;" id="addr">${SiteLocationDTO.ADDR }</p>
									</td>
								</tr>
								<tr>
									<td><label style="padding-left:5px;" class="col-sm-12 control-label"><span class="s">* </span>歸屬區域 :</label></td>
									<td>
										<p style="padding-top:17px; padding-left:15px;" id="maintainArea">${SiteLocationDTO.maintAreaName }</p>
									</td>
									<td><label class="col-sm-12 control-label"><span class="s">* </span>歸屬單位 :</label></td>
									<td>
										<p style="padding-top:17px; padding-left:15px;" id="maintainDept">${SiteLocationDTO.maintDeptName}</p>
									</td>
									<td><label class="col-sm-12 control-label" ><span class="s">* </span>負責單位 :</label></td>
									<td>
										<p style="padding-top:17px; padding-left:15px;" id="maintainTeam">${SiteLocationDTO.maintTeamName}</p>
									</td>
								</tr>
								<tr>
									<td><label class="col-sm-12 control-label"><span class="s">* </span>負責人 :</label></td>
									<td>
										<p style="padding-top:17px; padding-left:15px;" id="maintainUser">${SiteLocationDTO.maintChnNm}</p>
									</td>
									<td><label style="padding-left:5px;" class="col-sm-12 control-label">NS_LEVEL :</label></td>
									<td>
										<p style="padding-top:17px; padding-left:15px;" id="maintainTeam">${SiteLocationDTO.NSLevelName }</p>
									</td>
									<td><label class="col-sm-12 control-label"><span class="s">* </span>建築高度 :</label></td>
									<td>
										<p style="padding-top:17px; padding-left:15px;" id="maintainTeam">${SiteLocationDTO.BUILDING_HEIGHT }</p>
									</td>
								</tr>
								<tr >
									<td><label class="col-sm-12 control-label">共站業者 :</label></td>
									<td colspan="3" nowrap="nowrap">
										<div class="row form-group" style="padding-left : 15px;">
											<div class="col-sm-10">
												<div class="checkbox-inline">
													<label>
														<input name="SHARE_COM"  
														   ${fn:trim(CHT) =='' ? '' : 'checked'} value="${CHT}" type="checkbox" onclick="unselect();" >CHT
														<i class="fa fa-square-o"></i>
													</label>
												</div>
												<div class="checkbox-inline">
													<label>
														<input name="SHARE_COM"
														   ${fn:trim(FET) =='' ? '' : 'checked'} value="${FET}" type="checkbox" onclick="unselect();">FET
														<i class="fa fa-square-o"></i>
													</label>
												</div>
												<div class="checkbox-inline">
													<label>
														<input name="SHARE_COM"
														   ${fn:trim(TWM) =='' ? '' : 'checked'} value="${TWM}"  type="checkbox" onclick="unselect();" >TWM
														<i class="fa fa-square-o"></i>
													</label>
												</div>
												<div class="checkbox-inline">
													<label>
														<input name="SHARE_COM"   
														  ${fn:trim(APT) =='' ? '' : 'checked'} value="${APT}"  type="checkbox" onclick="unselect();">APT
														<i class="fa fa-square-o"></i>
													</label>
												</div>
											</div>
										</div>
									</td>
								</tr>
								<tr >
									<td><label class="col-sm-12 control-label">空間 :</label></td>
									<td colspan="3" nowrap="nowrap">
										<div class="row form-group" style="padding-left : 15px;">
											<div class="col-sm-10">
												<div class="checkbox-inline">
													<label><input type="checkbox" value='${SiteLocationDTO.SPACE_ROOM }' ${SiteLocationDTO.SPACE_ROOM eq 'Y' ? 'checked':'' } > 房屋
														<i class="fa fa-square-o"></i>
													</label>
												</div>
												<div class="checkbox-inline">
													<label>
														<input id="is_FREEENTER" name="IS_FREEENTER" onclick="changeValue('is_FREEENTER');" value="${SiteLocationDTO.SPACE_ROOF }" type="checkbox" ${SiteLocationDTO.SPACE_ROOF eq 'Y' ? 'checked':'' }> 樓頂
														<i class="fa fa-square-o"></i>
													</label>
												</div>
												<div class="checkbox-inline">
													<label>
														<input id="is_KEY" name="IS_KEY" onclick="changeValue('is_KEY');"  type="checkbox" value='${SiteLocationDTO.SPACE_TOP }' ${SiteLocationDTO.SPACE_TOP eq 'Y' ? 'checked':'' }> 屋凸
														<i class="fa fa-square-o"></i>
													</label>
												</div>
												<div class="checkbox-inline">
													<label>
														<input id="is_INDOOR" name="IS_INDOOR" onclick="changeValue('is_INDOOR');"  type="checkbox" value='${SiteLocationDTO.SPACE_PLATFORM}' ${SiteLocationDTO.SPACE_PLATFORM eq 'Y' ? 'checked':'' } > 平台
														<i class="fa fa-square-o"></i>
													</label>
												</div>
												<div class="checkbox-inline">
													<label>
														<input id="need_MISC_LIC" name="NEED_MISC_LIC" onclick="changeValue('need_MISC_LIC');"  type="checkbox" value='${SiteLocationDTO.SPACE_LAND}' ${SiteLocationDTO.SPACE_LAND eq 'Y' ? 'checked':'' }> 土地
														<i class="fa fa-square-o"></i>
													</label>
												</div>
												<div class="checkbox-inline">
													<label>
														<input id="has_MISC_LIC" name="HAS_MISC_LIC" onclick="changeValue('has_MISC_LIC');" type="checkbox" value='${SiteLocationDTO.SPACE_INDOOR}' ${SiteLocationDTO.SPACE_INDOOR eq 'Y' ? 'checked':'' }> 室內
														<i class="fa fa-square-o"></i>
													</label>
												</div>
												<div class="checkbox-inline">
													<label>
														<input id="has_MISC_LIC" name="HAS_MISC_LIC" onclick="changeValue('has_MISC_LIC');"  type="checkbox" value='${SiteLocationDTO.SPACE_OUTDOOR}' ${SiteLocationDTO.SPACE_OUTDOOR eq 'Y' ? 'checked':'' }> 室外
														<i class="fa fa-square-o"></i>
													</label>
												</div>
											</div>
										</div>
									</td>
									<td><label class="col-sm-12 control-label">坪數 :</label></td>
									<td>
										<p style="padding-top:17px; padding-left:15px;" id="footage">${SiteLocationDTO.FOOTAGE}</p>
									</td>
								</tr>
								<tr >
								
									<td><label class="col-sm-12 control-label">員工關係 :</label></td>
									<td>
										<p style="padding-top:17px; padding-left:15px;" id="emp_RELATION">${SiteLocationDTO.EMP_RELATION }</p>
									</td>
								</tr>
								<tr >
									<td><label class="col-sm-12 control-label">其他資訊 :</label></td>
									<td colspan="5" nowrap="nowrap">
										<div class="row form-group" style="padding-left : 15px;">
											<div class="col-sm-10">
												<div class="checkbox-inline">
													<label><input type="checkbox" ${multiSite}> 多頻段站點
														<i class="fa fa-square-o"></i>
													</label>
												</div>
												<div class="checkbox-inline">
													<label>
														<input id="is_FREEENTER" name="IS_FREEENTER" onclick="changeValue('is_FREEENTER');"  type="checkbox"  value='${SiteLocationDTO.IS_FREEENTER}' ${SiteLocationDTO.IS_FREEENTER eq 'Y' ? 'checked':''}  > 是否可自由進入
														<i class="fa fa-square-o"></i>
													</label>
												</div>
												<div class="checkbox-inline">
													<label>
														<input id="is_KEY" name="IS_KEY" onclick="changeValue('is_KEY');"  type="checkbox" value='${SiteLocationDTO.IS_KEY}' ${SiteLocationDTO.IS_KEY eq 'Y' ? 'checked':''}> 是否有鑰匙
														<i class="fa fa-square-o"></i>
													</label>
												</div>
												<div class="checkbox-inline">
													<label>
														<input id="is_INDOOR" name="IS_INDOOR" onclick="changeValue('is_INDOOR');"  type="checkbox" value='${SiteLocationDTO.IS_INDOOR}' ${SiteLocationDTO.IS_INDOOR eq 'Y' ? 'checked':''} > 是否為室內
														<i class="fa fa-square-o"></i>
													</label>
												</div>
												<div class="checkbox-inline">
													<label>
														<input id="need_MISC_LIC" name="NEED_MISC_LIC" onclick="changeValue('need_MISC_LIC');"  type="checkbox"  value='${SiteLocationDTO.NEED_MISC_LIC}' ${SiteLocationDTO.NEED_MISC_LIC eq 'Y' ? 'checked':''}> 需要雜項執照
														<i class="fa fa-square-o"></i>
													</label>
												</div>
												<div class="checkbox-inline">
													<label>
														<input id="has_MISC_LIC" name="HAS_MISC_LIC" onclick="changeValue('has_MISC_LIC');"  type="checkbox" value='${SiteLocationDTO.HAS_MISC_LIC}' ${SiteLocationDTO.HAS_MISC_LIC eq 'Y' ? 'checked':''}> 已有雜項執照
														<i class="fa fa-square-o"></i>
													</label>
												</div>
											</div>
										</div>
									</td>
								</tr>
								<tr >
									<td><label class="col-sm-12 control-label">補充說明 :</label></td>
									<td colspan="5" nowrap="nowrap">
										<textarea id="loc_DESC" name="LOC_DESC" style="resize: none;width:730px;border-left-width:1px;margin-left:15px;margin-top:4px;" rows="3">
											${fn:replace(SiteLocationDTO.LOC_DESC,"\\n",'<br>') }
										</textarea>
									</td>
								</tr>
							</table>
						</div>
							<div id="tabs-2">
						<c:forEach items="${tbSiteAntCfgList }" var="tbSiteAntCfg"  >
								<table border=2style= "table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 100%">
									<tr>
										<td class="titleTD">&nbsp; Cell &nbsp;</td>
										<td class="titleTD">訊號源</td>
										<td class="titleTD">P-Code</td>
										<td class="titleTD">天線型號</td>
										<td class="titleTD">安裝高度</td>
										<td class="titleTD">方向角</td>
										<td class="titleTD">E-TILT</td>
										<td class="titleTD">M-TILT</td>
										<td class="titleTD">安裝方式</td>
	
									</tr>
	
	
									<tr>
										<td ROWSPAN=5 class="titleTD" style="width: 133px;"><input
											type="checkbox" checked />&nbsp;<input size="2" type="text"
											value="${tbSiteAntCfg.CELL_ID }"></input></td>
										<td class="textTD">
											<select class="form-control" >
													<option value=""></option>
											    <c:forEach  var="antSignal" items="${allAntSignal}" >
													<option value="${antSignal.value}" ${antSignal.value eq tbSiteAntCfg.SEG_SOURCE ? 'selected':'' }  >${antSignal.label}</option>
											    </c:forEach>
											</select>
										</td>
										<td class="textTD">
											<input type="text" style="width: 100%;" value="${tbSiteAntCfg.p_CODE }" >
										</td>
										<td class="textTD">
											<select class="form-control">
													<option value=""></option>
													<c:forEach  var="antenna" items="${antennaList}" >
														<option value="${antenna.ANTENNA_ID}"  ${antenna.ANTENNA_ID eq tbSiteAntCfg.ANT_MODEL ? 'selected':'' }  >${antenna.ANTENNA_MODEL}</option>
												    </c:forEach>
											</select>
										</td>
										<td class="textTD">
											<input type="text" style="width: 100%;" value="${tbSiteAntCfg.ANT_HIGH}"  >
										</td>
										<td class="textTD"><input type="text" value="${tbSiteAntCfg.ANT_ORIENT}"
											style="width: 100%;" ></td>
										<td class="textTD"><input type="text" value="${tbSiteAntCfg.e_TILT}"
											style="width: 100%;"></td>
										<td class="textTD"><input type="text" value="${tbSiteAntCfg.m_TILT}"
											style="width: 100%;"></td>
										<td class="textTD"><select class="form-control">
												<option value=""></option>
												<c:forEach  var="antInstallWay" items="${allAntInstallWay}" >
														<option value="${antInstallWay.value}"  ${antInstallWay.value eq tbSiteAntCfg.SETUP_STYLE ? 'selected':'' }  >${antInstallWay.label}</option>
												    </c:forEach>
										</select></td>
									</tr>
	
									<tr>
										<td class="titleTD">樓高</td>
										<td class="titleTD">鐵塔</td>
										<td class="titleTD">屋突</td>
										<td COLSPAN=3 class="titleTD">地址</td>
										<td class="titleTD">經度</td>
										<td class="titleTD">緯度</td>
									</tr>
	
									<tr>
										<td class="textTD"><input type="text" value="${tbSiteAntCfg.FLOOR_LEVEL}"
											style="width: 100%;"></td>
										<td class="textTD"><input type="text" value="${tbSiteAntCfg.TOWER_HEIGHT}"
											style="width: 100%;"></td>
										<td class="textTD"><input type="text" value="${tbSiteAntCfg.PENTHOUSE_HEIGHT}"
											style="width: 100%;"></td>
										<td COLSPAN=3 class="textTD"><input type="text" value="${tbSiteAntCfg.ADDR}"
											style="width: 100%;"></td>
										<td class="textTD"><input type="text"  value="${tbSiteAntCfg.LON}"
											style="width: 100%;"></td>
										<td class="textTD"><input type="text" value="${tbSiteAntCfg.LAT}"
											style="width: 100%;"></td>
									</tr>
	
									<tr>
										<td COLSPAN=2 class="titleTD">Feeder cable type</td>
										<td COLSPAN=2 class="titleTD">Feeder cable 長度</td>
										<td COLSPAN=2 class="titleTD">Jumper cable 長度</td>
										<td COLSPAN=3 class="titleTD">覆蓋區域</td>
									</tr>
	
									<tr>
										<td COLSPAN=2 class="textTD">
											<select class="form-control">
												<option value=""></option>
												<c:forEach  var="feederCableType" items="${allFeederCableType}" >
													<option value="${feederCableType.value}"  ${feederCableType.value eq tbSiteAntCfg.f_CABLE_TYPE ? 'selected':'' }  >${feederCableType.label}</option>
											    </c:forEach>
											</select>
										</td>
										<td COLSPAN=2 class="textTD"><input type="text" value="${tbSiteAntCfg.f_CABLE_LEN}"
											style="width: 100%;"></td>
										<td COLSPAN=2 class="textTD"><input type="text" value="${tbSiteAntCfg.j_CABLE_LEN}"
											style="width: 100%;"></td>
										<td COLSPAN=3 class="textTD"><input type="text" value="${tbSiteAntCfg.COVER_REG}" 
											style="width: 100%;"></td>
									</tr>
	
								</table>
						</c:forEach>
							</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
<div id="showSiteFileDownloadFrame"></div>
</body>
</html>