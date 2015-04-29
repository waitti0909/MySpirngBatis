<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>匯入領料單</title>
<style>.labelStyle {
	 margin-right: -80px;
	}
</style>
<script type="text/javascript">
$(document).ready(function() {
	$('input[type=file]').on('change', prepareUpload);
	$("#uploadBtn").on('click', uploadFiles);
});

var files, fileNames; // user to check duplicate file

//Grab the files and set them to our variable
function prepareUpload() {
	files = event.target.files;
}

//檔案上傳
function uploadFiles() {
	event.stopPropagation(); // Stop stuff happening
	event.preventDefault(); // Totally stop stuff happening 
	var duplicateFile = false;
	var filename = $("#file").val();
	var selname = filename.substr(filename.indexOf(".") + 1,filename.length);
	if ($("#file").val() == '') {
		alert('請選擇檔案');
		return false;
	} else if (selname != "xls" && selname != "xlsx") {
		alert("檔案類型須為 xls,xlsx");

	} else {
		duplicateFile = true;
	}

	if (duplicateFile) {
		// Create a formdata object and add the files
		var data = new FormData();
		$.each(files, function(key, value) {
			data.append(key, value);
		});

		$.ajax({
			url : CONTEXT_URL + "/st/invMeterial/uploadExcel",
			type : 'post',
			data : data,
			cache : false,
			dataType : 'json',
			processData : false,
			contentType : false, 
			success : function(data, textStatus, jqXHR) {
				if(data.success){
					var callBackFun ="${callBackFun}";
					if (typeof window[callBackFun] === "function") {
						window[callBackFun](JSON.stringify(data));
					}
					$("#${targetFrameId}").dialog('close');
				}else{
					alert('匯入失敗!'+data.msg);
				}
			}
		});
	}
}
</script>
</head>
<body>	
<form id="readRForm" enctype="multipart/form-data">
	<table>
		<tr>
	    	<td>
	        	<input id="file" type="file" name="file" />
	    	</td>
	    	<td>
	    		<p>
					<button type="button" id="uploadBtn">匯入領料單</button>
				</p>
	    	</td>
		</tr>
	</table>
</form>	
<input id="callBackFun" name="callBackFun" type="hidden" value="${callBackFun}"></input>
<input id="targetFrameId" name="targetFrameId" type="hidden" value="${targetFrameId}"></input>
<div id="bootstrapped-fine-uploader"></div>
</body>
</html>