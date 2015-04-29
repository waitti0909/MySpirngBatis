<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>探勘紀錄</title>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title>Insert title here</title>
<script type="text/javascript">
	$(document).ready(function() {
		LoadSelect2Script('<s:url value="/resources/plugins/select2/select2.min.js" />',Select2);

		osSearchRecord();
		osInBuilding();
		osAntStand();
// 		osLookup();
		$("#checkBoxList  div").remove();
		$("#shareCom  label").remove();
		//經度 siteBuildLon
		$('#siteLon').val($("#siteHuntLon").val());
		//緯度 
		$('#siteLat').val($("#siteHuntLat").val());
		
		$("#shareType").on('change',function(data){
			$("#checkBoxList  div").remove();
			$("#shareCom  label").remove();
			if ($(this).val() == "1" || $(this).val() == '2') {
				osLookup();	
			}
		});
	});//document.ready end
	
	//探勘紀錄 -- 自動產生序號
	function uniqueSeg(){
		//alert($("#srId").val());
		$.ajax({
			url : CONTEXT_URL + "/st/searchRecord/uniqueSeq",			
			type : "POST",
			dataType : 'json',
			data:{
				"srId" :  $("#siteHuntSrId").val()			
			},
			async : false,
			success : function(data) {
				if(data.success){
					//alert(JSON.stringify(data));
					$("#seq").text(data.rows[0]);
					$("#srSeq").val(data.rows[0]);
				}
			}
		});
	}	
	
	//探勘日期
	$('#srDate').datepicker({
		dateFormat : "yy/mm/dd",
		changeYear : true,
		changeMonth : true,
		showButtonPanel : true,
		showTime : false,
		//minDate : 0
	});
	
	
	
	//探勘紀錄 --機房型態
	function osSearchRecord(){
		$.ajax({
			url : CONTEXT_URL + "/st/searchRecord/baseType",			
			type : "POST",
			dataType : 'json',
			data:{
				"workId" :  $("#workId").val()			
			},
			async : false,
			success : function(data) {
				if(data.success){
					if(data.success){
						if(data.rows.length > 0){
							$("#baseType  option").remove();
							$("#baseType").append("<option value=''>--請選擇--</option>");
							for(var i=0; i<data.rows.length; i++){		
								$("#baseType").append("<option value="+data.rows[i].value+">"+data.rows[i].label+"</option>");
							}
							
						}
					}
				}
			}
		});
	}

	//探勘紀錄 --室內場所分類
	function osInBuilding(){
		$.ajax({
			url : CONTEXT_URL + "/st/searchRecord/inBuilding",			
			type : "POST",
			dataType : 'json',
			data:{
				"workId" :  $("#workId").val()			
			},
			async : false,
			success : function(data) {
				if(data.success){
					if(data.success){
						if(data.rows.length > 0){
							$("#inBuilding  option").remove();
							$("#inBuilding").append("<option value=''>--請選擇--</option>");
							for(var i=0; i<data.rows.length; i++){		
								$("#inBuilding").append("<option value="+data.rows[i].value+">"+data.rows[i].label+"</option>");
							}
							
						}
					}
				}
			}
		});
	}
	
	//探勘紀錄 --天線支架
	function osAntStand(){
		$.ajax({
			url : CONTEXT_URL + "/st/searchRecord/antStand",			
			type : "POST",
			dataType : 'json',
			data:{
				"workId" :  $("#workId").val()			
			},
			async : false,
			success : function(data) {
				if(data.success){
					if(data.success){
						if(data.rows.length > 0){
							$("#antStand  option").remove();
							$("#antStand").append("<option value=''>--請選擇--</option>");
							for(var i=0; i<data.rows.length; i++){		
								$("#antStand").append("<option value="+data.rows[i].value+">"+data.rows[i].label+"</option>");
							}
							
						}
					}
				}
			}
		});
	}
	
 	function osLookup(e){
		$.ajax({
			url : CONTEXT_URL + "/st/searchRecord/lookup",			
			type : "POST",
			dataType : 'json',
			data:{
				"workId" :  $("#workId").val()			
			},
			async : false,
			success : function(data) {
 				//alert(JSON.stringify(data));
				if(data.success){
					if(data.rows.length > 0){
						$("#shareCom").append("<label class='col-sm-12 control-label'>共站業者 :</label></td>");
						var lockInputStr = "${view}" != 'view' ? "" : "disabled";
						for(var i=0; i<data.rows.length; i++){		
// 							$("#checkBoxList").append("<div style='height: auto; float: left; display: inline'><input id='lookup"+data.rows[i].code+"' name='lookup"+data.rows[i].code+"' type='checkbox' value='"+data.rows[i].code+"'>&nbsp;&nbsp;"+data.rows[i].name+"&nbsp;&nbsp;</input></div>");
							$("#checkBoxList").append("<div class='checkbox-inline'><label><input id='lookup"+data.rows[i].code+"' name='lookup"+data.rows[i].code+"' type='checkbox' value='"+data.rows[i].code+"' "+lockInputStr+">&nbsp;"+data.rows[i].name+"</input><i class='fa fa-square-o'></i></label></div>");
						}	
					}
				}
			}
		});
	}
	
	//--共構/共站  連動共站業者勾選欄位(選否的話 隱藏起來)
	function Select2() {	
		
	}
	
	//顯示地址處理頁
	function openAddressProcess() {
		
		var addr = {
			"zip" : $("#qq_zip").val(),
			"city" : $("#siteHuntCity").val(),
			"area" : $("#siteHuntArea").val(),
			"village" : $("#qq_village").val(),
			"road" : $("#qq_road").val(),
			"adjacent" : $("#qq_adjacent").val(),
			"lane" : $("#qq_lane").val(),
			"alley" : $("#qq_alley").val(),
			"subAlley" : $("#qq_subAlley").val(),
			"doorNo" : $("#qq_doorNo").val(),
			"doorNoEx" : $("#qq_doorNoEx").val(),
			"floor" : $("#qq_floor").val(),
			"floorEx" : $("#qq_floorEx").val(),
			"room" : $("#qq_room").val(),
			"remark" : $("#qq_remark").val(),
			"disabledFields" : $("#qq_disabledFields").val(),
			"callBackFun" : $("#qq_callBackFun").val()
		};
		openAddressDialogFrame("showAddressFrame", addr); // This is implement in common.js
	}	
	
	function receviceAddressData(addressObject) {
		// var obj = JSON.parse(addressObject);
		// do what you want to do
		$.ajax({
			url : CONTEXT_URL + "/common/address/combineAddress",
			dataType : "json",
			data : addressObject,
			contentType : 'application/json',
			type : "POST",
			async : false,
			success : function(data) {
				//alert(JSON.stringify(data));
				$("#address").val(data.row.fullAddress);
				
				$("#qq_zip").val(data.row.zip);
				$("#qq_city").val(data.row.city);
				$("#qq_area").val(data.row.area);
				$("#qq_village").val(data.row.village);
				$("#qq_adjacent").val(data.row.adjacent);
				$("#qq_road").val(data.row.road);
				$("#qq_lane").val(data.row.lane);
				$("#qq_alley").val(data.row.alley);
				$("#qq_subAlley").val(data.row.subAlley);
				$("#qq_doorNo").val(data.row.doorNo);
				$("#qq_doorNoEx").val(data.row.doorNoEx);
				$("#qq_floor").val(data.row.floor);
				$("#qq_floorEx").val(data.row.floorEx);
				$("#qq_room").val(data.row.room);
				$("#qq_remark").val(data.row.remark);
			}
		});
	}		
	
	//限制輸入數字
	function ValidateFloat(e, pnumber){
    if (!/^\d+[.]?\d*$/.test(pnumber)){
        $(e).val(/^\d+[.]?\d*/.exec($(e).val()));
		}
    	return false;
	}
	
	
</script>
</head>
<body>
	<form id="searchRecordTab1">
	<table style="width: 100%" >
			<tr>
				<td width="10%" nowrap="nowrap">
					<label class="col-sm-12 control-label">探勘序號 :</label>
				</td>
				<td width="23%">
					<p style="padding-top: 15px; padding-left: 5px;" id="seq"></p>
					<input id="srId" name="srId" type="hidden" />
					<input id="srSeq" name="srSeq" type="hidden" />
				</td>
				
				<td width="10%" nowrap="nowrap">
					<label class="col-sm-12 control-label">基站編號 :</label>
				</td>
				<td width="23%">
					<p style="padding-top: 15px; padding-left: 5px;" id="btsSiteTemp"></p>
					<input id="btsSiteTempId" name="btsSiteTempId" type="hidden"></input>
				</td>
				
				<td width="10%"><label class="col-sm-12 control-label">探勘結果:</label></td>
				<td width="24%">
					<p style="padding-top: 15px; padding-left: 5px;" id=""></p>
<!-- 					<input id="" name="" type="hidden"></input> -->
				</td>
			</tr>
			<tr>
				<td width="10%" nowrap="nowrap"><div class="clearfix">&nbsp;</div></td>
			</tr>		
			<tr>
				<td width="10%" nowrap="nowrap">
					<label class="col-sm-12 control-label"><span class="s">*</span>探勘日期:</label>
				</td>
				<td width="23%">
					<input id="srDate" name="srDate" type="text" class="form-control" readonly="readonly" placeholder="點選輸入框" ></input>
				</td>
				<td width="10%" nowrap="nowrap">
					<label class="col-sm-12 control-label"><span class="s">*</span>探勘人員:</label></td>
				<td width="23%">
					<input id="srPsn" name="srPsn" type="text" value="" class="form-control"></input>
				</td>
				<td width="10%"><label class="col-sm-12 control-label"><span class="s">*</span>探勘評估 :</label></td>
				<td width="24%">
					<select id="srEval" name="srEval" class="form-control">
							<option value="" selected>---請選擇---</option>
							<option value="1" >可用</option>
							<option value="0" >建議取消</option>
					</select>
				</td>
			</tr>
			<tr>
				<td valign='top' width="10%" nowrap="nowrap" style="padding-top: 15px;">
					<label class="col-sm-12 control-label"><span class="s">*</span>探勘地址:</label></td>
				<td colspan="5" nowrap="nowrap">
					<div style="margin-top: 15px">
	 					<div class="col-lg-5 input-group" style="width: 80%; height: auto; float: left; display: inline">
							<input type="text" id="address" name="address" class="form-control" placeholder="Click button to manage address" readonly="readonly" ></input>
							<span>
							<button class="btn btn-primary btn-label" style="margin-right: 10px"  id="addrButton"
								type="button" onclick="openAddressProcess(receviceAddressData)">變更地址</button>	
							 </span>	
						</div>			
					</div>
				</td>
			</tr>
			<tr>
				<td width="10%" nowrap="nowrap" style="padding-top: 15px;">
					<label class="col-sm-12 control-label"><span class="s">*</span>經度:</label></td>
				<td width="23%" style="padding-top: 15px;">
					<input id="siteLon" name="siteLon" type="text" class="form-control" maxlength="10"></input></td>
				<td width="10%" nowrap="nowrap" style="padding-top: 15px;">
					<label class="col-sm-12 control-label"><span class="s">*</span>緯度:</label></td>
				<td width="23%" style="padding-top: 15px;">
					<input id="siteLat" name="siteLat" type="text" class="form-control" maxlength="9"></input></td>
				<td width="10%" style="padding-top: 15px;">
					<label class="col-sm-12 control-label">機房型態 :</label>
				</td>
				<td width="24%" style="padding-top: 15px;">
					<select  id="baseType" class="populate placeholder form-control" name="baseType" >
						<option value="">---請選擇---</option>								
					</select>
				</td>
			</tr>
			<tr>
				<td width="10%" nowrap="nowrap" style="padding-top: 15px;">
					<label class="col-sm-12 control-label">建物高度 :</label></td>
				<td width="23%" style="padding-top: 15px;">
					<input id="height" name="height" type="text" class="form-control" style="width:95%;display:inline;" maxlength="10" onkeyup="return ValidateFloat(this,value)"></input>(m)</td>
					
				<td width="10%" nowrap="nowrap" style="padding-top: 15px;">
					<label class="col-sm-12 control-label">建物樓層 :</label></td>
				<td width="23%" style="padding-top: 15px;">
					<input id="floor" name="floor" type="text" class="form-control" maxlength="10" onkeyup="value=value.replace(/[^-_A-Za-z0-9]/g,'')"></input></td>
					
				<td width="10%" nowrap="nowrap" style="padding-top: 15px;">
					<label class="col-sm-12 control-label">室內場所分類 :</label></td>
				<td width="23%" style="padding-top: 15px;">
					<select id="inBuilding" name="inBuilding" class="form-control">
						<option value="">---請選擇---</option>
					</select>
				</td>
			</tr>
			
			<tr>
				<td width="10%" nowrap="nowrap" style="padding-top: 15px;">
					<label class="col-sm-12 control-label">其他資訊 :</label></td>
				<td colspan="5" nowrap="nowrap">
					<div id="isTemp" style="padding-top: 15px;">
						<div style="height: auto; float: left; display: inline" class="checkbox-inline"><label>
							<input id="isFreeEnter" name="isFreeEnter" type="checkbox" ></input> 是否可自由進入<i class="fa fa-square-o"></i></label> 
						</div>
						<div style="height: auto; float: left; display: inline" class="checkbox-inline"><label>
							<input id="isKey" name="isKey" type="checkbox" ></input> 是否有鑰匙<i class="fa fa-square-o"></i></label>
						</div>
						<div style="height: auto; float: left; display: inline" class="checkbox-inline"><label>
							<input id="isInDoor" name="isInDoor" type="checkbox" ></input> 是否為室內<i class="fa fa-square-o"></i></label>
						</div>
						<div style="height: auto; float: left; display: inline" class="checkbox-inline"><label>
							<input id="needMiscLic" name="isNeedMiscLic" type="checkbox" ></input> 需要雜項執照<i class="fa fa-square-o"></i></label>
						</div>
						<div style="height: auto; float: left; display: inline" class="checkbox-inline"><label>
							<input id="hasMiscLic" name="isHasMiscLic" type="checkbox" ></input> 已有雜項執照<i class="fa fa-square-o"></i></label>
						</div>
						<div style="height: auto; float: left; display: inline" class="checkbox-inline"><label>
							<input id="lineReachable" name="isLineReachable" type="checkbox" ></input> 專線到達<i class="fa fa-square-o"></i></label>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td width="10%" nowrap="nowrap" style="padding-top: 15px;">
					<label class="col-sm-12 control-label">預估租金 :</label></td>
				<td width="23%" style="padding-top: 15px;">
				<input id="expectRent" name="expectRent" class="form-control" maxlength="18" onkeyup="return ValidateFloat(this,value)"></input></td>
					
				<td width="10%" nowrap="nowrap" style="padding-top: 15px;">
					<label class="col-sm-12 control-label">共構/共站 :</label></td>
				<td width="23%" style="padding-top: 15px;">
					<select id="shareType" name="shareType" class="form-control">
						<option value="0">無</option>
						<option value="1">共站</option>
						<option value="2">共構</option>
					</select>
				</td>
			</tr>
			<tr>
				<td width="10%" nowrap="nowrap" style="padding-top: 15px;">
					<label	class="col-sm-12 control-label">天線支架 :</label></td>
				<td width="23%" style="padding-top: 15px;">
					<select id="antStand" name="antStand" class="form-control">
							<option value="">---請選擇---</option>
					</select>
				</td>
				<td width="10%" nowrap="nowrap" style="padding-top: 15px;"><label
					class="col-sm-12 control-label">天線架設高度 :</label></td>
				<td width="23%" style="padding-top: 15px;">
					<input id="antSHight" name="antSHight" type="text" class="form-control" style="width:91%;display:inline;" onkeyup="return ValidateFloat(this,value)" maxlength="8"></input>(m)</td>
				<td width="10%" nowrap="nowrap" style="padding-top: 15px;">
					<label class="col-sm-12 control-label">天線中心高度 :</label>
				</td>
				<td width="23%" style="padding-top: 15px;">
					<input id="antCHight" name="antCHight" type="text" class="form-control" style="width:91%;display:inline;" onkeyup="return ValidateFloat(this,value)" maxlength="8"></input>(m)</td>
			</tr>
			<tr>
				<td id="shareCom"><label class="col-sm-12 control-label"></label></td> <!-- 共站業者 :-->
				<td colspan="5" nowrap="nowrap">
					<div class="row form-group" style="padding-left : 15px;">
						<div class="col-sm-10" id="checkBoxList">			
							<input type="hidden" name="lookup" id="lookup" ></input>
						</div>
					</div>
				</td>
			</tr>
			<tr >
				<td><label class="col-sm-12 control-label">空間 :</label></td>
				<td colspan="1" nowrap="nowrap">
					<div class="row form-group" style="padding-left : 15px;">
						<div class="col-sm-10" id="spaceList">
							<div class="checkbox-inline">
								<label>
									<input type="checkbox" id="spaceRoom" name="spaceRoom" ></input> 房屋<i class="fa fa-square-o"></i>
								</label>
							</div>
							<div class="checkbox-inline">
								<label>
									<input id="spaceRoof" name="spaceRoof" value="" type="checkbox"></input> 樓頂<i class="fa fa-square-o"></i>
								</label>
							</div>
							<div class="checkbox-inline">
								<label>
									<input id="spaceTop" name="spaceTop" value=""  type="checkbox"></input> 屋凸<i class="fa fa-square-o"></i>
								</label>
							</div>
							<div class="checkbox-inline">
								<label>
									<input id="spacePlatform" name="spacePlatform" value="" type="checkbox" ></input> 平台<i class="fa fa-square-o"></i>
								</label>
							</div>
							<div class="checkbox-inline">
								<label>
									<input id="spaceLand" name="spaceLand" value="" type="checkbox"></input> 土地<i class="fa fa-square-o"></i>
								</label>
							</div>
							<div class="checkbox-inline">
								<label>
									<input id="spaceIndoor" name="spaceIndoor" value="" type="checkbox"></input> 室內<i class="fa fa-square-o"></i>
								</label>
							</div>
							<div class="checkbox-inline">
								<label>
									<input id="spaceOutdoor" name="spaceOutdoor" value="" type="checkbox"></input> 室外<i class="fa fa-square-o"></i>
								</label>
							</div>
						</div>
					</div>
				</td>
				<td><label class="col-sm-12 control-label">坪數 :</label></td>
				<td width="10%">
					<div style="margin-top: 15px" >
						<input id="footAge" name="footAge" type="text" class="form-control" 
							required="required" style="ime-mode:disabled" onkeyup="return ValidateFloat(this,value)" maxlength="18" ></input>
					</div>
				</td>
			</tr>
			<tr>
				<td><label class="col-sm-12 control-label">員工關係 :</label></td>
				<td width="10%">
					<div style="margin-top: 15px" >
						<input id="empRelation" name="empRelation" type="text" class="form-control" required="required" maxlength="50"></input>
					</div>
				</td>
			</tr>		
			<tr>
				<td valign='top' width="10%" nowrap="nowrap" style="padding-top: 15px;">
					<label class="col-sm-12 control-label">探勘註解 :</label>
				</td>
				<td colspan="5" nowrap="nowrap">
					<div style="margin-top: 15px">
						<textarea class="form-control" id="locDesc" name="locDesc" rows="3" maxlength="400"></textarea>
					</div>
				</td>
			</tr>
		</table>
	</form>
	<div class="clearfix">&nbsp;</div>
	<div id="showAddressFrame"></div>
	
	<form action="" id="inputAddressForm" name="" method="post">
		<span id="abccc">
			<input type="hidden" name="qq_zip" id="qq_zip" />
			<input type="hidden" name="qq_city" id="qq_city" />
			<input type="hidden" name="qq_area" id="qq_area"  />
			<input type="hidden" name="qq_village" id="qq_village" />
			<input type="hidden" name="qq_road" id="qq_road" />
			<input type="hidden" name="qq_adjacent" id="qq_adjacent"  />
			<input type="hidden" name="qq_lane" id="qq_lane"  />
			<input type="hidden" name="qq_alley" id="qq_alley"  />
			<input type="hidden" name="qq_subAlley" id="qq_subAlley"  />
			<input type="hidden" name="qq_doorNo" id="qq_doorNo"  />
			<input type="hidden" name="qq_doorNoEx" id="qq_doorNoEx" />
			<input type="hidden" name="qq_floor" id="qq_floor" />
			<input type="hidden" name="qq_floorEx" id="qq_floorEx" />
			<input type="hidden" name="qq_room" id="qq_room" />
			<input type="hidden" name="qq_remark" id="qq_remark" />
			<input type="hidden" name="qq_disabledFields" id="qq_disabledFields" value="zip,city,area"/>
			<input type="hidden" name="qq_callBackFun" id="qq_callBackFun" value="receviceAddressData" />
		</span>
		<input type="hidden" name="abcdedf" id="adajoi" value="adijr" />
	</form>	
<!-- 	<input id="srId" type="hidden" > -->
</body>
</html>