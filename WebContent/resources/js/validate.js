
function checkDateRange(from, to) {
    if (Date.parse(from.value) <= Date.parse(to.value)) {
        return true;
    } else {
        return false;
    }
}


function checkDateStrRange(from, to) {

    if (Date.parse(from) <= Date.parse(to)) {
        return true;
    } else {
        return false;
    }
}

function getObj(form,name){
    var obj = form.elements[name];
    if(obj != null){
        return obj;
    }
    return null;
}

function checkEmpty(obj,name){
    if(isEmpty(obj.value)){
        //alert(name+"欄位不得為空");
        //obj.select();
        return false;
    }
    return true;
}


function checkIDs(obj,name){
    var str = obj.value;
    if(str == ""){
        return true;
    }

    if(!checkID(obj.value)){
        //obj.select();
        return false;
    }
    return true;
}

function checkSummary(form,array,condition){
    var itemName = array[1][0];
    var total = 0 ;

    //若全為空則不檢核
    for(var i=0 ; i<array[0].length;i++){
        if(getObj(form,array[0][i]).value != '')break
    }
    if( i == array[0].length ) return true

    for(var i=0 ; i<array[0].length;i++)
        total += eval((getObj(form,array[0][i]).value == '') ? 0 : getObj(form,array[0][i]).value);
		
    if(eval(condition) != total){
        //alert(itemName+'總合不等於'+eval(condition));
        //getObj(form,array[0][0]).select();
        return false;
    }
    return true;
}
	



function checkMAX(obj,name ,condition){
    if(!isNumerical(obj.value)){
        alert(name+"欄位格式錯誤");
        obj.select();
        return false;
    }
    if( eval(obj.value) > eval(condition) ){
        alert(name+"欄位不可大於"+condition);
        obj.select();
        return false;
    }

    return true;
}

function checkDatas(form,array,type,condition){
    var errorMsg = "";

    if(type == "EMPTY"){
        for(var i=0 ; i < array[0].length ; i++ ){
            if(array[0][i].indexOf('[0]')>0){
                for(var j=0;j<999;j++){
                    try{
                        var obj , index = j;
                        if( form.elements.namedItem(array[0][i]).length == undefined)
                        {
                            obj = form.elements.namedItem(array[0][i]);
                            j=999;
                        }
                        else
                        {
                            obj = form.elements.namedItem(array[0][i])[j];
                        }
                        if( ! checkEmpty( obj , array[1][i] )){
                            errorMsg +=  "「" + array[1][i] + (index+1) + "」" + "資料尚未輸入" + "\n";
                        }
                    }catch(ex){
                        break;
                    }
                }
            }
            else{
                if( ! checkEmpty(  getObj(form,array[0][i]) , array[1][i] ) ){
                    errorMsg +=  "「" + array[1][i] + "」" + "資料尚未輸入" + "\n";
                }
            }
        }
        return errorMsg;
    }
		
    if(type == "ID"){
        for(var i=0 ; i < array[0].length ; i++ ){
            if( ! checkIDs(  getObj(form,array[0][i]) , array[1][i] ) ){
                errorMsg +=  "「" + array[1][i] + "」" + "資料格式錯誤" + "\n";
            }
        }
        return errorMsg;
    }

    if(type == "SUMMARY"){
        if( ! checkSummary(  form ,array ,condition  ) ){
            errorMsg +=  "「" + array[1][i] + "」" + "總合不等於" + eval(condition) + "\n";
        }
        return errorMsg;
    }

			
    if(type == "DATE"){
        for(var i=0 ; i < array[0].length ; i++ ){
            if( ! checkDate(  getObj(form,array[0][i]) , array[1][i] ) ){
                errorMsg +=  "「" + array[1][i] + "」" + "日期資料格式錯誤" + "\n";
            }
        }
        return errorMsg;
    }



    if(type == "MAIL"){
        for(var i=0 ; i < array[0].length ; i++ ){
            if( ! checkMail(  getObj(form,array[0][i]) , array[1][i] ) ){
                errorMsg +=  "「" + array[1][i] + "」" + "e-mail資料格式錯誤" + "\n";
            }
        }
        return errorMsg;
    }



    if(type == "MAX"){
        for(var i=0 ; i < array[0].length ; i++ ){
            if(getObj(form,array[0][i]).value == '') return true;
            if( ! checkMAX(  getObj(form,array[0][i]) , array[1][i] , condition ) ){
                errorMsg +=  "「" + array[1][i] + "」" + "欄位不可大於" + eval(condition) + "\n";
					
            }
        }
        return errorMsg;
    }
		
    if(type == "MIN"){
        for(var i=0 ; i < array[0].length ; i++ ){
            if( ! checkMIN(  getObj(form,array[0][i]) , array[1][i] , condition ) ){
                errorMsg +=  "「" + array[1][i] + "」" + "欄位不可小於" + eval(condition) + "\n";
            }
        }
        return errorMsg;
    }
}

function checkMAX(obj,name ,condition){
    if(!isNumerical(obj.value)){
        //alert(name+"欄位格式錯誤");
        //obj.select();
        return false;
    }
    if( eval(obj.value) > eval(condition) ){
        //alert(name+"欄位不可大於"+condition);
        //obj.select();
        return false;
    }

    return true;
}

function checkMIN(obj,name ,condition){
    if(!isNumerical(obj.value)){
        //alert(name+"欄位格式錯誤");
        //obj.select();
        return false;
    }
    if( eval(obj.value) < eval(condition) ){
        //alert(name+"欄位不可小於"+condition);
        //obj.select();
        return false;
    }
    return true;
}

function checkDate(obj,name){
    var str = obj.value;
		
    if(str == ""){
        return true;
    }

    if(str.length != 10){
        //alert( name + "格式錯誤" );
        //obj.select();
        return false;
    }

    if( str.indexOf("/") != 4){
        //alert( name + "格式錯誤" );
        //obj.select();
        return false;
    }
    if( str.lastIndexOf("/") != 7){
        //alert( name + "格式錯誤" );
        //obj.select();
        return false;
    }
				
    if(! isValidDate(str.substr(0,4),
    str.substr(5,2),
    str.substr(8,2)) )
    {
        //alert( name + "格式錯誤" );
        //obj.select();
        return false;
    }
    return true;
}
	
function checkMail(obj,name){
    var str = obj.value;
    if(str == ""){
        return true;
    }

    if(!isValidateEmail(obj.value)){
        //obj.select();
        //alert(name+"格式錯誤");
        return false;
    }
    return true;
}

function trim(s) {
    while (s.substring(0,1) == ' ') {
        s = s.substring(1,s.length);
    }
    while (s.substring(s.length-1,s.length) == ' ') {
        s = s.substring(0,s.length-1);
    }
    return s;
}

function isAllNumber(value) {
    var bValid = true;
    if (!isEmpty(value)) {
        var keywords = "0123456789";
        for (var i=0; i<value.length; i++) {
            if (keywords.indexOf(value.charAt(i)) == -1) {
                bValid = false;
                break;
            }
        }
    } else {
        bValid = false;
    }
    return bValid;
}
	
function isPhone(value) {
    var bValid = true;
    if (!isEmpty(value)) {
        var keywords = "0123456789-()";
        for (var i=0; i<value.length; i++) {
            if (keywords.indexOf(value.charAt(i)) == -1) {
                bValid = false;
                break;
            }
        }
    } else {
        bValid = false;
    }
    return bValid;
}
	
function isEmpty(value) {
    var regform = / /g;
    var flag = false;
    if (value.replace(regform, "").length == 0) {
        flag = true;
    }
    return flag;
}

function InputOnlyPositiveInt(event) {  
    if ( (event.keyCode<48 || event.keyCode>57))
        event.returnValue=false
} 
	
function InputOnlyPositiveFloat(event) {  
    if (event.keyCode!=46 && (event.keyCode<48 || event.keyCode>57))
        event.returnValue=false
} 


/**
 * 檢查是否為正常之統編
 */
function chknumCompanyNo(NO){  
    var cx = new Array;
    cx[0] = 1;
    cx[1] = 2;
    cx[2] = 1;
    cx[3] = 2;
    cx[4] = 1;
    cx[5] = 2;
    cx[6] = 4;
    cx[7] = 1;

    var SUM = 0;
    if (NO.length != 8) {
        //alert("統編錯誤，要有 8 個數字");
        return;
    }
    var cnum = NO.split("");
    for (i=0; i<=7; i++) {
        if (NO.charCodeAt() < 48 || NO.charCodeAt() > 57)
        {
            //alert("統編錯誤，要有 8 個 0-9 數字組合");
            return;
        }
        SUM += cc(cnum[i] * cx[i]);
    }

    if (SUM % 10 == 0) return true;
    else if (cnum[6] == 7 && (SUM + 1) % 10 == 0) return true;
    else {
        //alert("統 編 ： "+NO+" 錯 誤 !");
        return false;
    }
}

function isTaiwanIDNO(id) {
    letter ="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    i=0;
    result=0;

    id=id.toUpperCase(); // 轉成大寫
    id=id.replace(" /g", ""); // 把空白換掉
    if(id.length != 10) // 是不是10個字?
    {
        if ( id.length == 8 )	return chknumCompanyNo(id);
        //alert("統編或身分證長度有誤。");
        return false;
    }
    if(letter.indexOf(id.substring(0,1)) == -1)
    {
        //alert("第一個字母必須是英文:o。");
        return false;
    }
    else {
        i = letter.indexOf(id.substring(0,1)) + 10;
    }

    result=0;

    if(isNaN(id.substring(1,10)))
    {
        //alert("後面九碼必須是數字。");
        return false;
    }

    if(id.substring(1,2) != "1" && id.substring(1,2) != "2")
    {
        //alert("性別錯誤。");
        return false;
    }

    idalpha = id.substring(0,1);
    idalpha_value= 0;

    if( idalpha == "A" )  idalpha_value = 1;
    else if( idalpha == "B" )  idalpha_value = 10;
    else if( idalpha == "C" )  idalpha_value = 19;
    else if( idalpha == "D" )  idalpha_value = 28;
    else if( idalpha == "E" )  idalpha_value = 37;
    else if( idalpha == "F" )  idalpha_value = 46;
    else if( idalpha == "G" )  idalpha_value = 55;
    else if( idalpha == "H" )  idalpha_value = 64;
    else if( idalpha == "I" )  idalpha_value = 39;
    else if( idalpha == "J" )  idalpha_value = 73;
    else if( idalpha == "K" )  idalpha_value = 82;
    else if( idalpha == "L" )  idalpha_value = 2;
    else if( idalpha == "M" )  idalpha_value = 11;
    else if( idalpha == "N" )  idalpha_value = 20;
    else if( idalpha == "O" )  idalpha_value = 48;
    else if( idalpha == "P" )  idalpha_value = 29;
    else if( idalpha == "Q" )  idalpha_value = 38;
    else if( idalpha == "R" )  idalpha_value = 47;
    else if( idalpha == "S" )  idalpha_value = 56;
    else if( idalpha == "T" )  idalpha_value = 65;
    else if( idalpha == "U" )  idalpha_value = 74;
    else if( idalpha == "V" )  idalpha_value = 83;
    else if( idalpha == "W" )  idalpha_value = 21;
    else if( idalpha == "X" )  idalpha_value = 3;
    else if( idalpha == "Y" )  idalpha_value = 12;
    else if( idalpha == "Z" )  idalpha_value = 30;


    for(i=1; i<9; i++)
    {
        result += (parseInt(id.substring(i, i+1)) * (9-i));
    }

    result = result+idalpha_value;

    if( ((10 - (result % 10))%10) != id.substring(9,10))
    {
        //alert("錯誤的身分證字號！");
        return false;
    }

    return true;
}

//==================================================
//功能名稱:Function gIsDigit
//功能用途:檢查是否為數字
//參    數: strVal
//
//傳 回 值:
//   True
//   False
//==================================================
function gIsDigit(strVal){
    if (strVal=="")
        return false;
    for(var i=0; i<strVal.length; i++) {
        if (strVal.charCodeAt(i)<48 || strVal.charCodeAt(i)>57 || strVal.charCodeAt(i)==45)
            if (i!=0)
                return false;
        else if (i==0 && strVal.charCodeAt(0)!=45)
            return false;
    }
    return true;
} 

function cc(n){

    if (n > 9) {
        var s = n + "";
        n1 = s.substring(0,1) * 1;
        n2 = s.substring(1,2) * 1;
        n = n1 + n2;
    }
    return n;

}
 
function checkEmail(emailStr) {
    var regform = / /g;
    if (emailStr.length == 0) {
        return false;
    }
    var emailPat=/^(.+)@(.+)$/;
    var specialChars="\\(\\)<>@,;:\\\\\\\"\\.\\[\\]";
    var validChars="\[^\\s" + specialChars + "\]";
    var quotedUser="(\"[^\"]*\")";
    var ipDomainPat=/^(\d{1,3})[.](\d{1,3})[.](\d{1,3})[.](\d{1,3})$/;
    var atom=validChars + '+';
    var word="(" + atom + "|" + quotedUser + ")";
    var userPat=new RegExp("^" + word + "(\\." + word + ")*$");
    var domainPat=new RegExp("^" + atom + "(\\." + atom + ")*$");
    var matchArray=emailStr.match(emailPat);
    if (matchArray == null) {
        return false;
    }
    var user=matchArray[1];
    var domain=matchArray[2];
    if (user.match(userPat) == null) {
        return false;
    }
    var IPArray = domain.match(ipDomainPat);
    if (IPArray != null) {
        for (var i = 1; i <= 4; i++) {
            if (IPArray[i] > 255) {
                return false;
            }
        }
        return true;
    }
    var domainArray=domain.match(domainPat);
    if (domainArray == null) {
        return false;
    }
    var atomPat=new RegExp(atom,"g");
    var domArr=domain.match(atomPat);
    var len=domArr.length;
    if ((domArr[domArr.length-1].length < 2) ||
        (domArr[domArr.length-1].length > 3)) {
        return false;
    }
    if (len < 2) {
        return false;
    }
    return true;
}

function noenter() {
    return !(window.event && window.event.keyCode == 13);
}

function InputOnlyEngAndNum(event) {  
    if ( !((event.keyCode>=48 && event.keyCode<=57)  || (event.keyCode>=65 && event.keyCode<=90) ||  (event.keyCode>=97 && event.keyCode<=122) ) )
        event.returnValue=false
} 

function addSelectedIndex (srcObj, destObj) {	
    var src = eval(srcObj);
    var dest = eval(destObj);
    if (src.options == null || dest.options == null) {
        return false;
    }
    if (src.selectedIndex==-1 || src.selectedIndex == 0)
        return false;
    if (src.length > 0) {
        if (dest.length <= 0) dest.length = 0;
        dest.length += 1;
        dest.options[dest.length - 1].text = src.options[src.selectedIndex].text;
        dest.options[dest.length - 1].value = src.options[src.selectedIndex].value;
        for (var i = src.selectedIndex; i< (src.length-1); i++) {
            src.options[i].text = src.options[i+1].text;
            src.options[i].value = src.options[i+1].value;
        }
        src.length = src.length -1;
        //src.selectedIndex = 0;
    }
}

function removeSelectedIndex (srcObj, destObj) {
    var src = eval(srcObj);
    var dest = eval(destObj);
    if (src.options == null || dest.options == null) {
        return false;
    }
    if (dest.selectedIndex==-1 || dest.selectedIndex == 0)
        return false;
    if (dest.length > 0) {
        if (src.length <= 0) src.length = 0;
        src.length += 1;
        src.options[src.length - 1].text = dest.options[dest.selectedIndex].text;
        src.options[src.length - 1].value = dest.options[dest.selectedIndex].value;
        for (var i = dest.selectedIndex; i< (dest.length-1); i++) {
            dest.options[i].text = dest.options[i+1].text;
            dest.options[i].value = dest.options[i+1].value;
        }
        dest.length = dest.length - 1;
        //dest.selectedIndex = 0;
    }
}

//
function changeStatus(propertyName,eqValue,arg1,arg2,arg3,arg4) {  
    var obj1 = document.getElementsByName(arg1);
    var obj2 = document.getElementsByName(arg2);
    var obj3 = document.getElementsByName(arg3);
    var obj4 = document.getElementsByName(arg4);
    if(propertyName){
        if (propertyName.value==eqValue) {
            if(obj1){
                setDisabledAndClear(obj1);
            }
            if(obj2){
                setDisabledAndClear(obj2);
            }
            if(obj3){
                setDisabledAndClear(obj3);
            }
            if(obj4){
                setDisabledAndClear(obj4);
            }
        }
        else{
            if(obj1){
                setEnabled(obj1);
            }
            if(obj2){
                setEnabled(obj2);
            }
            if(obj3){
                setEnabled(obj3);
            }
            if(obj4){
                setEnabled(obj4);
            }


        }
		 
		
    }
    else{
        return false;
    }
	
	
} 

function setEnabled(objName) {  
    if(objName.length >0){
        for(var i=0;i<objName.length;i++){
            objName[i].readOnly = false;
        }
    }


}

function setDisabledAndClear(objName) {  
    if(objName.length >0){
        for(var i=0;i<objName.length;i++){
            objName[i].readOnly = true;
            objName[i].value = "";
            objName[i].checked = false;
					
        }
    }


}

function removeFormatString(TargetElement, FormatString) {
    if (TargetElement.value == FormatString) {
        TargetElement.value = "";
    }

    TargetElement.select();
}

//檢查電話號碼長度
function checkPhoneLength(areaCode,phone){

    if ( areaCode=="02" )
    {
        if (phone.length==8)
        {
            return true;
        }
        return false;
		
    }else if ( areaCode=="039" )
    {
        if (phone.length==6)
        {
            return true;
        }
        return false;

    }else if ( areaCode=="035" )
    {
        if (phone.length==6)
        {
            return true;
        }
        return false;

    }else if ( areaCode=="03" )
    {
        if (phone.length==7)
        {
            return true;
        }
        return false;

    }else if ( areaCode=="037" )
    {
        if (phone.length==6)
        {
            return true;
        }
        return false;

    }else if (  areaCode=="04"  )
    {
        if (phone.length==8 || phone.length==7)
        {
            return true;
        }
        return false;

	
    }else if ( areaCode=="049" )
    {
		
        if (phone.length==7)
        {
            return true;
        }
        return false;


    }else if ( areaCode=="05" )
    {

        if (phone.length==7)
        {
            return true;
        }
        return false;


    }else if ( areaCode=="06" )
    {
        if (phone.length==7)
        {
            return true;
        }
        return false;


    }else if ( areaCode=="07" )
    {
        if (phone.length==7)
        {
            return true;
        }
        return false;

    }else if ( areaCode=="08" )
    {
        if (phone.length==7)
        {
            return true;
        }
        return false;

    }else if ( areaCode=="089" )
    {
        if (phone.length==6)
        {
            return true;
        }
        return false;

    }else if ( areaCode=="082" )
    {
        if (phone.length==6)
        {
            return true;
        }
        return false;

    }else if ( areaCode=="0836" )
    {
        if (phone.length==5)
        {
            return true;
        }
        return false;
    }else{
        //區碼錯誤
        return false;
    }

}

function checkFileImg(obj) {
	var re = /\.(jpg|JPG|jpeg|JPEG|gif|GIF|png|PNG)$/i;  //允許的圖片副檔名
	return re.test(obj.value);
}
/*日期結束日不得大於開始日*/
function dateCheck(sDate,eDate,dateName){		
	if(Date.parse(eDate).valueOf() < Date.parse(sDate).valueOf()){
		alert(dateName+" 結束日不能小於開始日");
		return false;
	}else{
		return true;
	}
}

//正數或非文字檢核
function qtyCheck(qty,qtyName){
 var temp=/^\d+(\.\d+)?$/;
if(temp.test(qty)==false){
 	alert(qtyName+"需為正數，不得為負數或其他文字");
	 return false;
 }
 return true;
}
//正數或非文字檢核
function checkIntOnBlur(theField){
 var temp=/^\d+(\.\d+)?$/;
 
if(temp.test(theField.value)==false){
 	alert("需為正數，不得為負數或其他文字");
 	theField.value=0;
 	theField.focus();
	 return false;
 }
 return true;
}

//正數或非文字檢核
function checkIntOnBlurEmpty(theField){

 if(theField.value!=''){	
	 var temp=/^\d+(\.\d+)?$/;
	 
	if(temp.test(theField.value)==false){
	 	alert("需為正數，不得為負數或其他文字");
	 	theField.value=0;
	 	theField.focus();
		 return false;
	 }
 }
 return true;
}


//驗證整數位數與小數位數是否符合
function validateNumber(number,positiveInteger,decimal){
	var result = false;
	if(/^[0-9]\d*\.{1,1}[0-9]\d*$/g.test(number)){
		var  numberArray = number.split(".");
		if(numberArray[0].length <= positiveInteger && numberArray[1].length <= decimal){
			
			result = true;
		}else if(typeof decimal === "undefined" || decimal == 0){
			if(numberArray[0].length <= positiveInteger){
				
				result = true;
			}
		}
	}else if(/^[0-9]*$/g.test(number)){
		if(number.length <= positiveInteger){
			result = true;
		}
	}
	return result;
}

//經度緯度驗證是否在台灣國土範圍
function validateLatAndLon(lon,lat,status){
	var msg ="";
	if(status == "search"){
		if(lon != null && lon!= ""){
			if(lon < 115 || lon > 124){
				msg+="經度需介於115~124之間\n";
			}
		}
		if(lat != null && lat != ""){
			if(lat < 18 || lat > 28){
				msg+="緯度需介於：18~28之間\n";
			}
		}
	}else{
		if(lon < 115 || lon > 124){
			msg+="經度需介於115~124之間\n";
		}
		if(lat < 18 || lat > 28){
			msg+="緯度需介於：18~28之間\n";
		}
	}
	return msg.trim();
}
//認真能用的公司統編檢和
function CheckCompanyNo(thisObj) {
	comNo = thisObj;
	var res = new Array(8);
	var key = "12121241";
	var isModeTwo = false; // 第七個數是否為七
	var result = 0;
	if (comNo.length != 8) {
		return false;
	}
	for (var i = 0; i < 8; i++)
	{
		var tmp = comNo.charAt(i) * key.charAt(i);
		res[i] = Math.floor(tmp / 10) + (tmp % 10); // 取出十位數和個位數相加
		if (i == 6 && comNo.charAt(i) == 7)
			isModeTwo = true;
	}
	for (var s = 0; s < 8; s++)
		result += res[s];
	if (isModeTwo) {
		if ((result % 10) != 0 && ((result + 1) % 10) != 0) {// 如果第七位數為7
			return false;
		}
	}
	else
	if ((result % 10) != 0) {
		return false;
	}
	return true;
}