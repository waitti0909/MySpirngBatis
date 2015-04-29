
/*
*
*@param selectCityId: city 的 select id
*       ex:<select id="siteCity" class="form-control"></select>
*
*@param townSelectId: town 的 select id
*       ex:<select id="siteTown" class="form-control"></select>
*/
function buildCityOptions(selectCityId,townSelectId,zipCode) {
	
	var filterDomainValue = "";
	
	if($("#filterDomainId").length > 0 && $("#filterDomainId").val()!='' ){
		filterDomainValue = $("#filterDomainId").val();
	}
	
	var fixedOption = '<option value="" >--- 請選擇 ---</option>';
	var allCities = localStorage.getItem("cities");
	var cityOptions = [];
	cityOptions.push(fixedOption);
	if(allCities!=null){
		var localData = JSON.parse(allCities);
	
		  for(var i in localData){
			  if(filterDomainValue!=''){
				 
				  if(filterDomainValue.indexOf(localData[i].domain)>-1){
					  cityOptions.push('<option value="'+localData[i].city_ID+'" >'+localData[i].city_NAME+'</option>');
				  }
			  }else{
				  cityOptions.push('<option value="'+localData[i].city_ID+'" >'+localData[i].city_NAME+'</option>');
			  }
		  }
		$("#"+selectCityId).append(cityOptions.join(""));
		$("#"+townSelectId).append(fixedOption);
		
		
		$("#"+selectCityId).change(function(){
			var selectedCity = $(this).val();
			
			if(selectedCity=='') {$("#"+townSelectId).html('');$("#"+townSelectId).append(fixedOption); $("#" + townSelectId).select2("val", "");return;}
			var filterDomainValue='';
			if($("#filterDomainId").length > 0 && $("#filterDomainId").val()!='' ){
				filterDomainValue = $("#filterDomainId").val();
			}
			
			var townOptions = [];
			townOptions.push(fixedOption);
			var allTowns ;
			if(filterDomainValue!=''){
				allTowns = localStorage.getItem("cityTownsDomainTeam");
			}else{
				allTowns = localStorage.getItem("towns");
			}
			
			
			if(allTowns!=null){
			  var hisFound = false;
			  var localTowns = JSON.parse(allTowns);	
			  for(var i in localTowns){
				  if(hisFound && selectedCity!=localTowns[i].city_ID){
					  break;
				  }
				  if(selectedCity==localTowns[i].city_ID){
					
					  if(filterDomainValue!=''){
						  if(localTowns[i].domain.indexOf(filterDomainValue)>-1){
							  townOptions.push('<option value="'+localTowns[i].town_ID+'" >'+localTowns[i].townName+'</option>');
							
					  	  }
					  }else{
						  townOptions.push('<option value="'+localTowns[i].town_ID+'" >'+localTowns[i].town_NAME+'</option>');
						 
					  }
					  hisFound=true;
				  }
				 
			  }
			  
			  $("#"+townSelectId).html('');
			  
			  $("#"+townSelectId).append(townOptions.join(""));	
		
			  	try {
					$("#" + townSelectId).select2("val", "");
				} catch (e) {
					console.log(e);
				}
			
			}
		});
	}
	
}
function getZipCode(townValue){
	var allTowns = localStorage.getItem("towns");
	var localTowns = JSON.parse(allTowns);	
	  for(var i in localTowns){
		  if(townValue==localTowns[i].town_ID){		
			 return localTowns[i].zip_CODE;
		  }
		 
	  }
	return "";
}


