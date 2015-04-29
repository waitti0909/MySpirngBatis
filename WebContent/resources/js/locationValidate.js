function locValidate(){
	var errorMessages = [];
	var lon = $("#lon").val();
	if(!validateNumber(lon,3,6)){
		errorMessages [errorMessages.length]="經度格式錯誤，整數最多三位、小數最多為六位";
	}
	var lat = $("#lat").val();
	if(!validateNumber(lat,3,6)){
		errorMessages [errorMessages.length]="緯度格式錯誤，整數最多三位、小數最多為六位";
	}
	var msg =  validateLatAndLon(lon,lat,"");
	if(msg != "" && msg != null){
		errorMessages [errorMessages.length]=msg;
	}
	if($("#addr").val() == ""){
		errorMessages[errorMessages.length]="請輸入地址";
	}
	if(!validateNumber( $("#building_HEIGHT").val(),3,1)){
		errorMessages [errorMessages.length]="建築高度格式錯誤，整數最多三位、小數最多一位";
	}
	if($("#footage").val() != "" && $("#footage").val() != null){
		if(!validateNumber( $("#footage").val(),3,0)){
			errorMessages [errorMessages.length]="坪數格式錯誤，整數最多三位";
		}
	}
	if($("#loc_DESC").val().length >100){
		errorMessages [errorMessages.length]="補充說明超過100個字";
	}
	return errorMessages;
}

function siteAntValidate(tableNum) {
	if($("#cell-"+tableNum).val() == "") {
		  alert("天線組態 : 請選擇 Cell");
		  return false;				
	}
	
	if($("#lon-"+tableNum).val() !=null && $("#lon-"+tableNum).val() !="") {		
		if(!validateNumber($("#lon-"+tableNum).val(),3,6)){
			  alert("天線組態 :經度格式錯誤，整數最多三位、小數最多為六位");
			  return false;
		}
	}
	
	if($("#lon-"+tableNum).val() < 115 || $("#lon-"+tableNum).val() > 124){
		  alert("天線組態 :經度需介於115~124之間");
		  return false;
	}
	
	if($("#lat-"+tableNum).val() != null && $("#lat-"+tableNum).val() != "") {				   
		if(!validateNumber($("#lat-"+tableNum).val(),2,6)){
			  alert("天線組態 :緯度格式錯誤，整數最多二位、小數最多為六位");
			  return false;
		}
	}
	
	if($("#lat-"+tableNum).val() < 18 || $("#lat-"+tableNum).val() > 28){
		alert("天線組態 :緯度需介於：18~28之間");
		return false;
	}	
	
	if($("#installH-"+tableNum).val() != null && $("#installH-"+tableNum).val() != "") {				   
		if(!validateNumber($("#installH-"+tableNum).val(),5,2)){
			  alert("天線組態 :安裝高度，整數最多五位、小數最多為二位");
			  return false;
		}
	}
	
	if($("#directionAngle-"+tableNum).val() != null && $("#directionAngle-"+tableNum).val() != "") {				   
		if(!validateNumber($("#directionAngle-"+tableNum).val(),5,2)){
			  alert("天線組態 :方向角，整數最多五位、小數最多為二位");
			  return false;
		}
	}
	
	if($("#eTilt-"+tableNum).val() != null && $("#eTilt-"+tableNum).val() != "") {				   
		if(!validateNumber($("#eTilt-"+tableNum).val(),5,2)){
			  alert("天線組態 :E-TILT，整數最多五位、小數最多為二位");
			  return false;
		}
	}
	
	if($("#mTilt-"+tableNum).val() != null && $("#mTilt-"+tableNum).val() != "") {				   
		if(!validateNumber($("#mTilt-"+tableNum).val(),5,2)){
			  alert("天線組態 :M-TILT，整數最多五位、小數最多為二位");
			  return false;
		}
	}
	
	if($("#floor-"+tableNum).val() != null && $("#floor-"+tableNum).val() != "") {				   
		if(!validateNumber($("#floor-"+tableNum).val(),12,2)){
			  alert("天線組態 :樓高，整數最多十二位、小數最多為二位");
			  return false;
		}
	}
	
	if($("#tower-"+tableNum).val() != null && $("#tower-"+tableNum).val() != "") {				   
		if(!validateNumber($("#tower-"+tableNum).val(),12,2)){
			  alert("天線組態 :鐵塔，整數最多十二位、小數最多為二位");
			  return false;
		}
	}
	
	if($("#roof-"+tableNum).val() != null && $("#roof-"+tableNum).val() != "") {				   
		if(!validateNumber($("#roof-"+tableNum).val(),12,2)){
			  alert("天線組態 :屋突，整數最多十二位、小數最多為二位");
			  return false;
		}
	}
	return true;
}