<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

	<c:url var="exportPSNEmpty" value="/poi/exportPSNEmpty" />
	<c:url var="exportPSNExcel" value="/poi/exportPSNExcel" />

	<button type ="button" onclick="javascript:location.href='${exportPSNEmpty}'" >匯出空報表</button>
	<button type ="button" onclick="javascript:location.href='${exportPSNExcel}'" >匯出報表</button>
	
	
	<form id="readRForm" enctype="multipart/form-data">
		<label for="file">Filet : </label> <input id="file" type="file"
			name="file" />
		<p>
			<button type="button" id="uploadBtn">匯入報表</button>
		</p>
	</form>

	<div id="bootstrapped-fine-uploader"></div>


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
				url : CONTEXT_URL + "/poi/readPerson",
				type : 'post',
				data : data,
				cache : false,
				dataType : 'json',
				processData : false, // Don't process the files
				contentType : false, // Set content type to false as jQuery will tell the server its a query string request
				success : function(data, textStatus, jqXHR) {
					if(data.success){
						alert('匯入完成!');
					}else{
						alert('匯入失敗!'+data.msg);
					}
				}
			});
		}
	}
</script>


