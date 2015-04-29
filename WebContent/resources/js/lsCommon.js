
/*
 * 取得預設營業稅
 * */
function getDefaultTax(zipCode){
		var islandZipCode = ['929','951','952','290','209','210','211'
				,'212','817','819','890','891','892','893','894','896','880','881'
				,'882','883','884','885'];
		if(zipCode==null || zipCode==''){
			return 5;
		}
		for(var i=0;i<islandZipCode.length;i++){
			if(zipCode.indexOf(islandZipCode[i])>-1){
				return 0;
			}
		}

		return 5;	
}


function loadCollectUnit(){
	var allCollectUnitSub = localStorage.getItem("allCollectUnitSub");
	if(allCollectUnitSub==null){
		$.ajax({
			type : "get",
			url : CONTEXT_URL + "/ls/common/allCollectUnit",  
			contentType : 'application/json',
			dataType : 'json',  
			cache: false,
			async: false,
			success : function(jsonResponse) {
				var dataToStore = JSON.stringify(jsonResponse);
				localStorage.removeItem("CollectUnit");
				localStorage.setItem('CollectUnit', dataToStore);				
			},  
			error: function(jqXHR, textStatus, errorThrown){
				
			} 
		});
	}
	
	var allCollectUnitSub = localStorage.getItem("allCollectUnitSub");
	if(allCollectUnitSub==null){
		$.ajax({
			type : "get",
			url : CONTEXT_URL + "/ls/common/allCollectUnitSub",  
			contentType : 'application/json',
			dataType : 'json',  
			cache: false,
			async: false,
			success : function(jsonResponse) {
				var dataToStore = JSON.stringify(jsonResponse);
				localStorage.removeItem("CollectUnitSub");
				localStorage.setItem('CollectUnitSub', dataToStore);				
			},  
			error: function(jqXHR, textStatus, errorThrown){
				
			} 
		});
	}
}

function buildBanksOptions(selectselectedBankCode) {
	var fixedOption = '<option value="" >--- 請選擇 ---</option>';
	var allCollectUnit = localStorage.getItem("CollectUnit");
	var CollectUnitOptions = [];
	CollectUnitOptions.push(fixedOption);
	if(allCollectUnit!=null){
		var localData = JSON.parse(allCollectUnit);
	
		  for(var i in localData){
			if(localData[i].unit_CODE==selectselectedBankCode){
				CollectUnitOptions.push('<option value="'+localData[i].unit_CODE+'" selected >'+localData[i].unit_NAME+'</option>');
			 }else{
				CollectUnitOptions.push('<option value="'+localData[i].unit_CODE+'" >'+localData[i].unit_NAME+'</option>');
				 
			 }
		  }
	}
	return CollectUnitOptions.join('');
}



function buildSubBanksOptions(selectselectedBankCode,selectselectedSubBankCode) {
	var fixedOption = '<option value="" >--- 請選擇 ---</option>';
	var CollectUnitOptions = [];
	CollectUnitOptions.push(fixedOption);
	if(selectselectedBankCode==''){
		return CollectUnitOptions.join('');
	}
	var allCollectUnitSub = localStorage.getItem("CollectUnitSub");
	
	if(allCollectUnitSub!=null){
		var localData = JSON.parse(allCollectUnitSub);
		
		  for(var i in localData){
			  
			 if(localData[i].unit_CODE==selectselectedBankCode){
				 
				 if(localData[i].sub_UNIT_CODE==selectselectedSubBankCode){
					CollectUnitOptions.push('<option value="'+localData[i].sub_UNIT_CODE+'" selected >'+localData[i].sub_NAME+'</option>');
				 }else{
					CollectUnitOptions.push('<option value="'+localData[i].sub_UNIT_CODE+'" >'+localData[i].sub_NAME+'</option>'); 
				 }
			 }
		  }
	}
	return CollectUnitOptions.join('');
}


function checkBankNecessary(paymentAreaId,paymentModeName,bankName,subBankName,accountNumberName){
	var flag = true;
	$("#"+paymentAreaId).find("[name='"+paymentModeName+"']").each(function( index ) {
		  if($(this).val()=='W'){
			  var bankCode = $(this).parent().parent().find("select[name='"+bankName+"']").val();
			  var subBankCode = $(this).parent().parent().find("select[name='"+subBankName+"']").val();
			  var accountNumberValue = $(this).parent().parent().find("input[name='"+accountNumberName+"']").val();
			  if(accountNumberValue==''){
				  flag=false;
				  return flag;
			  }else if(bankCode==''){
				  flag=false;
				  return flag;
			  }else if(subBankCode==''){
				  if(bankCode!='700'){
					  flag=false;
					  return flag; 
				  }
			  }
			
		  }
	});
	if(!flag){
		alert("付款方式為匯款銀行帳號資訊不可為空");
	}
	return flag;
}
function checkRepeatPayment(tBodyId){
	
	var tempArray =[];
	var checkFlag = true;
	$('#'+tBodyId).find('tr').each(function(){
		
		var pay_item=$(this).find("select[name='PAYMENT_ITEM']").val();
		var lessor_id=$(this).find("select[name='LESSOR_ID']").val();
		var RECIPIENT_ID=$(this).find("input[name='RECIPIENT_ID']").val();
		var RECIPIENT_NAME=$(this).find("input[name='RECIPIENT_NAME']").val();
		
		
		if(pay_item==''||lessor_id==''|| RECIPIENT_ID==''||RECIPIENT_NAME==''){
			alert("付款資訊不可為空");
			$(this).find("input[name='RECIPIENT_NAME']").focus();
			checkFlag=false;
			return false;
		}
		
		var temKey = pay_item+"^"+lessor_id+"^"+RECIPIENT_ID;
		
		console.log("temKey="+temKey);
		console.log("temKey[]="+typeof(tempArray[temKey]));
		if(typeof(tempArray[temKey])=='undefined'){
			tempArray[temKey]=temKey;
		}else{
			alert("付款資訊不可重複");
			checkFlag=false;
			return false;
		}
	});
	return checkFlag;
}

function downloadPicFile(zip) {
	$.ajax({
		mimeType : 'text/html; charset=utf-8', // ! Need set mimeType only when run from local file
		type : 'POST',
		url : CONTEXT_URL + "/common/PIC001/downloadSignalMap",
		dataType : "json",
		async : false,
		data : {
			"zip" : zip
		},
		success : function(data) {
			if (data.success) {
				window.location.href = CONTEXT_URL + "/common/file/download?fileId=" + data.row;
			} else {
				alert(data.msg);
			}
		}
	});
}