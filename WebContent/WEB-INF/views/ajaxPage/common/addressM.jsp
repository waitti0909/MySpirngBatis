<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<!DOCTYPE html>
<form class="form-horizontal form-inline" role="form" name="addressForm" id="addressForm">
	<button class="btn btn-label-left btn-primary" type="submit" id="btn_submit_edit">
		<span><i class="fa fa-check"></i></span>
		<s:message code="button.ok" />
	</button>
	<button class="btn btn-primary btn-label-left" type="button" id="btn_zip">
		<span><i class="fa fa-home"></i></span>郵遞區號查詢
	</button>
	<button class="btn btn-label-left btn-default" type="button" id="btn_reset_edit">
		<span><i class="fa fa-undo txt-danger"></i></span>
		<s:message code="button.reset" />
	</button>
<p>
<div class="box-content">
<div class="col-sm-1">
	</div>
	地址輸入：
	<input type="text" id="zip_edit" name="zip1" value="${address.zip1}" size="3" readonly="readonly" />-<input type="text" id="zip2_edit" maxlength="2" name="zip2" value="${address.zip2}" size="2" pattern="[0-9][0-9]" title="請輸入郵遞區號2碼" />&nbsp;&nbsp;
	<input type="hidden" name="zip" value="${address.zip}" id="zipValue">
	<select id="city_edit" name="city" class="form-control" style="width:130px;" onchange="getAreas(this.value);" required="required">
		<option value="">--- 請選擇 ---</option>
		<c:forEach items="${cityMap}" var="data">
			<option value="${data.key}">${data.value}</option>
		</c:forEach>
	</select>&nbsp;&nbsp;
	<select id="area_edit" name="area" class="form-control" style="width:130px;" onchange="getRoads(this.value);" required="required">
		<option value="">--- 請選擇 ---</option>
	</select>&nbsp;&nbsp;
	<input type="text" id="village_edit" name="village" value="${address.village}" size="12" maxlength="10" /> (村里)&nbsp;&nbsp;
	<input type="text" id="adjacent_edit" name="adjacent" value="${address.adjacent}" size="8" maxlength="6" /> 鄰&nbsp;&nbsp;
	<p>
	<div class="col-sm-2">
	</div>
    <select id="road_edit" name="road" class="form-control" style="width:150px;" onchange="cleanOthInfo(this.value);">
		<option value="">--- 請選擇 ---</option>
	</select>&nbsp;&nbsp;
	<input type="text" id="lane_edit" name="lane" value="${address.lane }" size="8" maxlength="6"/> 巷&nbsp;&nbsp;
	<input type="text" id="alley_edit" name="alley" value="${address.alley}" size="8" maxlength="6"  /> 弄&nbsp;&nbsp;
	<input type="text" id="subAlley_edit" name="subAlley" value="${address.subAlley }" size="8" maxlength="6" /> 衖&nbsp;&nbsp;
	<input type="text" id="doorNo_edit" name="doorNo" value="${address.doorNo }" size="8" maxlength="6" onchange="checkDoorNoEmpty(this.value);" /> 號之
	<input type="text" id="doorNoEx_edit" name="doorNoEx" value="${address.doorNoEx }" size="8" maxlength="6" />
	<p>
	<div class="col-sm-2">
	</div>
	<input type="text" id="floor_edit" name="floor" value="${address.floor }" size="8" maxlength="6" onchange="checkFloorEmpty(this.value);" /> 樓之
	<input type="text" id="floorEx_edit" name="floorEx" value="${address.floorEx }" size="8" maxlength="6" />&nbsp;&nbsp;
	<input type="text" id="room_edit" name="room" value="${address.room }" size="8" maxlength="6" /> 室
	<p>
	<div class="col-sm-1">
	</div>
	其他資訊：<input type="text" size="85" maxlength="50" id="remark_edit" name="remark" value="${address.remark }" />		
	<input type="hidden" name="disabledFields" id="disabledFields_edit" value="${address.disabledFields}" />
	<input type="hidden" id="callBackFun_edit" name="callBackFun" value="${address.callBackFun }" />
	</div>
</form>
<script type="text/javascript">
$(document).ready(function() {
	initSelector();
	
	$("#btn_reset_edit").click(function() {
		$("#addressForm").trigger('reset');
		initSelector();
	});
	
	$("#btn_submit_edit").click(function() {
		if ($('#addressForm')[0].checkValidity()) {
		  // If the form is invalid, submit it. The form won't actually submit;
		  // this will just cause the browser to display the native HTML5 error messages.
			event.preventDefault();
			submitData();
		}
	});
	
	$("#btn_zip").click(function(){
		window.open('http://www.post.gov.tw/post/internet/Postal/index.jsp?ID=208','郵遞區號查詢',config = 'height=768,width=1024').focus();	
	});
	
});

function submitData() {
	if($('#road_edit').val()=="" && $('#remark_edit').val()==""){
		alert("請輸入道路或其他資訊");
	}else{
// 		halfToFull("zip_edit",$('#zip_edit').val());
// 		halfToFull("adjacent_edit",$('#adjacent_edit').val());
// 		halfToFull("lane_edit",$('#lane_edit').val());
// 		halfToFull("alley_edit",$('#alley_edit').val());
// 		halfToFull("subAlley_edit",$('#subAlley_edit').val());
// 		halfToFull("doorNo_edit",$('#doorNo_edit').val());
// 		halfToFull("doorNoEx_edit",$('#doorNoEx_edit').val());
// 		halfToFull("floor_edit",$('#floor_edit').val());
// 		halfToFull("floorEx_edit",$('#floorEx_edit').val());
// 		halfToFull("room_edit",$('#room_edit').val());

		if($('#road_edit').val()!="" && $("#zip2_edit").val()==''){
			alert("請輸入郵遞區號2碼");
			return false;
		}
		if($("#zip2_edit").val()=='00'){
			alert("郵遞區號2碼不可為00");
			return false;
		}
		zerotoNull("adjacent_edit",$('#adjacent_edit').val());
		zerotoNull("lane_edit",$('#lane_edit').val());
		zerotoNull("alley_edit",$('#alley_edit').val());
		zerotoNull("subAlley_edit",$('#subAlley_edit').val());
		zerotoNull("doorNo_edit",$('#doorNo_edit').val());
		zerotoNull("doorNoEx_edit",$('#doorNoEx_edit').val());
		zerotoNull("floor_edit",$('#floor_edit').val());
		zerotoNull("floorEx_edit",$('#floorEx_edit').val());

		$("#zipValue").val($("#zip_edit").val()+($("#zip2_edit").val().length==1?"0"+$("#zip2_edit").val():$("#zip2_edit").val()));
		
		var disabledFields = '${address.disabledFields}'.split(",");
		$.each(disabledFields, function() {
			$("#"+this+"_edit").prop("disabled", false);
		});	
		var callbackFun = '${address.callBackFun}';
		
		var formJson = JSON.stringify(form2js($("#addressForm").attr("id"), '.', true, null));
		var formJsonAddName='';
		for(var i=0;i<formJson.length;i++){
			formJsonAddName = formJsonAddName+formJson[i];
			if(i==(formJson.length-2)){
				formJsonAddName=formJsonAddName+',"cityName":"'+$("#city_edit option:selected" ).text()+'","areaName":"'+$("#area_edit option:selected" ).text()+'"';
			}
		}
		
		if (typeof window[callbackFun] === "function") {
			window[callbackFun](formJsonAddName);
		}
		var frameId = $("#frameId").val();
		$("#" + frameId).dialog('close');
	}
}

function initSelector() {
	var zip2Value = $("#zip2_edit").val();
	
	if ('${address.city}' != '') {
		$("#city_edit").val('${address.city}').change();
	}
	if ('${address.area}' != '') {
		$("#area_edit").val('${address.area}').change();
	}
	if ('${address.road}' != '') {
		
		$("#road_edit").val('${address.road}').change();
		 $("#zip2_edit").val(zip2Value);
	}
	if ('${address.doorNo}' != '') {
		checkDoorNoEmpty('${address.doorNo}');
	}
	if ('${address.floor}' != '') {
		checkFloorEmpty('${address.floor}');
	}

	var disabledFields = '${address.disabledFields}'.split(",");
	$.each(disabledFields, function() {
		$("#"+this+"_edit").prop("disabled", true);
	});	
}

function setDefaultSelectOption(selectObjId) {
	$('#'+selectObjId).empty().append("<option selected='selected' value=''>--- 請選擇 ---</option>");
}

function getAreas(city) {
	setDefaultSelectOption('area_edit');
	setDefaultSelectOption('road_edit');
	cleanOthInfo();
	
	if (city == '') return;
	$.ajax({	
		url: CONTEXT_URL + "/common/address/getAreas",
		dataType: "json",
		data : {
			"city" : $("#city_edit").val()
		},
		type: "POST",
		async: false,
		success : function(data) {
	 		var rows = data.rows;	
			for (var r in rows) {
				$('#area_edit').append("<option value='"+rows[r].town_ID+"'>"+rows[r].town_NAME+"</option>");
			}	
			$("#zip_edit").val('');
			$("#zip2_edit").val('');
			$("#adjacent_edit").val("");
		}
	});
}


function getRoads(area) {	
	setDefaultSelectOption('road_edit');
	$( "#road_edit" ).trigger( "onchange" );
	
	$.ajax({	
		url: CONTEXT_URL + "/common/address/getRoads",
		dataType: "json",
		data : {
			"city" : $("#city_edit").val(),
			"area" : $("#area_edit").val()
		},
		type: "POST",
		async: false,
		success : function(data) {			
	 		var rows = data.rows;	
			for (var r in rows) {
				//$("#zip_edit").val(rows[r].zip);
				$('#road_edit').append("<option value='"+rows[r].road+"'>"+rows[r].road+"</option>");
			}			
			getZip();
			$("#adjacent_edit").val("");
		}
	});
}

function getZip(){
	$.ajax({	
		url: CONTEXT_URL + "/common/address/getZip",
		dataType: "json",
		data : {
			"city" : $("#city_edit").val(),
			"area" : $("#area_edit").val()
		},
		type: "POST",
		async: false,
		success : function(data) {				 			
			$("#zip_edit").val(data);
			$("#zip2_edit").val('');
		}
	});
}

function checkDoorNoEmpty(value) {
	if (value == '') {
		$("#doorNoEx_edit").val('').prop("readonly", true);
	} else {
		$("#doorNoEx_edit").prop("readonly", false);
	}
}

function checkFloorEmpty(value) {
	if (value == '') {
		$("#floorEx_edit").val('').prop("readonly", true);
	} else {
		$("#floorEx_edit").prop("readonly", false);
	}
}

	function cleanOthInfo(value) {	
		$('#lane_edit').val("");
		$('#alley_edit').val("");
		$('#subAlley_edit').val("");
		$('#doorNo_edit').val("");
		$('#doorNoEx_edit').val("");
		$('#floor_edit').val("");
		$('#floorEx_edit').val("");
		$('#room_edit').val("");
		if (value == "") {
			$("#lane_edit").prop("disabled", true);
			$("#alley_edit").prop("disabled", true);
			$("#subAlley_edit").prop("disabled", true);
			$("#doorNo_edit").prop("disabled", true);
			$("#doorNoEx_edit").prop("disabled", true);
			$("#floor_edit").prop("disabled", true);
			$("#floorEx_edit").prop("disabled", true);
			$("#room_edit").prop("disabled", true);
		} else {
			if(value=='${address.road}'){
				$("#adjacent_edit").val('${address.adjacent}');
				$("#lane_edit").val('${address.lane}');
				$("#alley_edit").val('${address.alley}');
				$("#subAlley_edit").val('${address.subAlley}');
				$("#doorNo_edit").val('${address.doorNo}');
				$("#doorNoEx_edit").val('${address.doorNoEx}');
				$("#floor_edit").val('${address.floor}');
				$("#floorEx_edit").val('${address.floorEx}');
				$("#room_edit").val('${address.room}');
			}
						
			$("#lane_edit").prop("disabled", false);
			$("#alley_edit").prop("disabled", false);
			$("#subAlley_edit").prop("disabled", false);
			$("#doorNo_edit").prop("disabled", false);
			$("#doorNoEx_edit").prop("disabled", false);
			$("#floor_edit").prop("disabled", false);
			$("#floorEx_edit").prop("disabled", false);
			$("#room_edit").prop("disabled", false);
		}
	}
	
	function halfToFull(chid,changeText){
	    var temp = "";
	    for (var i = 0; i < changeText.toString().length; i++) {
	        var charCode = changeText.toString().charCodeAt(i);
	        if (charCode <= 126 && charCode >= 33) {
	            charCode += 65248;
	        } else if (charCode == 32) { // 半形空白轉全形
	            charCode = 12288;
	        }
	        temp = temp + String.fromCharCode(charCode);
	    }
	   $('#'+chid).val(temp);
	}
	
	function zerotoNull(inputId,inputval){
		if(inputval=="0"){
			$('#'+inputId).val('');
		}
	}
</script>