<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<style>
.qqGrid{
	height: 350px;
}
</style>

<div class="col-xs-12 col-sm-12" id="fileupload" style="padding: 0px 0px 0px 0px">
	<div class="box ui-draggable ui-droppable">
		<div class="box-content" >
			<div class="form-group">
				<table width="100%">
					<tr>
						<td><label>&nbsp;&nbsp;檔案相關：${typePathName}</label></td>
						<td><label>文件單號：${fileDoc}</label></td>
					</tr>
				</table>
			</div>
			<div class="form-group">
		    	<label class="col-sm-1_5 control-label"><span class="s">* </span>檔案分類：</label>
		    	<select name="fileTypeSelect" id="fileTypeSelect" onchange="resetForm();reloadFileList(this.value);">
		    		<option value="">全部</option>
					<c:forEach items="${fileTypeMap}" var="data">
		    			<option value="${data.key}">${data.value}</option>
		    		</c:forEach>
				</select>
				<font color="red" size="1">＊允許上傳的檔案類型：pdf, doc, docx, xls, xlsx, csv, zip, rar, rrr, 7z, gif, png, tif, tiff, jpg, txt</font>
			</div>
			<!-- The file upload form used as target for the file upload widget -->
	    	<div id="fine-uploader"></div>
<!-- 		    <div id="fileListDiv"></div> -->
			<!-- 固定id for window.resize start-->
			<div id="jQgrids" class="qqGrid">
				<table id="fileGrid"></table>
				<div id="filem_pager"></div>
			</div>
			<!-- 固定id for window.resize end-->
		</div>
	</div>
</div>


<script type="text/javascript">
var files, fileNames;	// user to check duplicate file

//Grab the files and set them to our variable
function prepareUpload(event) {
	files = event.target.files;
}

function uploadFiles(event) {
	
	event.stopPropagation(); // Stop stuff happening
    event.preventDefault(); // Totally stop stuff happening 
	var duplicateFile = false;
	
	if ($("#file1").val() == '') {
		alert('請選擇檔案');
		return false;
	} else {
		for	(var index = 0; index < fileNames.length; index++) {
		    if ($("#file1").val().indexOf(fileNames[index]) > -1 && confirm('檔案：' + fileNames[index] + '已經存在，是否覆蓋檔案？')) {
		    	duplicateFile = true;
		    }
		}
	}
	
	if (duplicateFile) {
		// Create a formdata object and add the files
		var data = new FormData();
		$.each(files, function(key, value) {
			data.append(key, value);
		});    
		data.append("typePath", '${typePath}');
		data.append("fileDoc", '${fileDoc}');
		data.append("fileType", $("#fileTypeSelect").val());
		
	    $.ajax({
	        url: CONTEXT_URL + "/common/file/upload?files",
	        type: 'post',
	        data: data,
	        cache: false,
	        dataType: 'json',
	        processData: false, // Don't process the files
	        contentType: false, // Set content type to false as jQuery will tell the server its a query string request
	        success: function(data, textStatus, jqXHR) {
	        	reloadFileList();
	        }
	    });
	}    
}

$(document).ready(function() {	
	
	//////////////////////// 元件式上傳
	if ('${fileType}' != '') {		
		$("#fileTypeSelect").val('${fileType}').change();
	} else {
		$("#fileTypeSelect").change();
	}
	
	var manualuploader = $('#fine-uploader').fineUploader({
		element: $('#fine-uploader'),
		request: {
			endpoint: CONTEXT_URL + "/common/file/upload"
		},
		maxConnections : 1,	// used to synchronized the connection limit once a file.
		multiple : true,	// if required mutiple upload just set it to "true".
		template: "qq-template",
		validation: {
        	allowedExtensions: ['pdf','doc', 'docx', 'xls', 'xlsx', 'ppt', 'pptx', 'csv', 'zip', 'rar', '7z', 'gif', 'png', 'tif', 'tiff', 'jpg', 'txt']
//		    	sizeLimit: 1024 * 1024 * 2 // 50 kB = 50 * 1024 bytes
	    },
	    messages: {
	        typeError: '不合法的檔案類型： {file}.'
	    },
      	showMessage: function(message) {
	        // Using Bootstrap's classes
	        resetForm();
	        $('.qq-upload-list').append('<div class="bg-danger">' + message + '</div>');
      	},
      	formatFileName: function(filename) {
            return filename;
        },
// 		autoUpload: false,
		editFilename: {
        	enabled: false
      	},
      	callbacks: {
            onComplete: function(id, fileName, responseJSON) {
                if (!responseJSON.success) {
                	resetForm();
                	$('.qq-upload-list').append('<div class="bg-danger">' + responseJSON.msg + '</div>');
                }
            }
        }
    }).on("allComplete", function(event, succeeded, failed) {
       	reloadFileList($("#fileTypeSelect").val());
       	if (failed.length == 0) {
	       	resetForm();
       	}
    }).on('submit', function(event, id, name) { 
    	
    	if ($("#fileTypeSelect").val() == '') {
    		alert('請選擇一種檔案分類！');
    		return false;
    	}
    	
    	var forbidFile = /\.(exe|bat)$/i;  //不允許的副檔名
    	if (forbidFile.test(name)) { 
    		alert("非合法的檔案類型，請重新選擇");
    		return false;
    	}
    	
    	var noReplaceFile = false;
    	for	(var index = 0; index < fileNames.length; index++) {
		    if (name == fileNames[index]) {
		    	if (!confirm('檔案：' + fileNames[index] + '已經存在，是否覆蓋檔案？')) {
			    	noReplaceFile = true;
		    	}
		    }
		}
    	if (!noReplaceFile) {
	        $(this).fineUploader('setParams', { 
	        	typePath : '${typePath}',
				fileDoc : '${fileDoc}',
				fileType: $("#fileTypeSelect").val() 
	        });
    	} else {
    		return false;
    	}
    }).on('error', function (event, id, name, reason, xhr) {
//         alert(qq.format("Error on file number {} - {}.  Reason: {}", id, name, errorReason));
        alert("Error on file：" + name + ", 上傳失敗原因：" + reason);
    });
	////////////////////////元件式上傳
	
	$("#fileGrid").jqGrid({
		url : CONTEXT_URL + "/common/file/getList",
		datatype : "json",
		pager : '#filem_pager',
		postData  : {
			typePath : '${typePath}',
			fileDoc : '${fileDoc}',
			fileType : $("#fileTypeSelect").val()
		},
		//width : $('#fine-uploader').attr('width'),
		colNames : [ '功能', '檔案分類', '檔案名稱', '上傳時間', '上傳人員', 'fileId' ],
		colModel : [ 
		    {name : 'act',index : 'act', align : 'center', sortable : false}, 
		    {name : 'file_TYPE_NAME',index : 'file_TYPE_NAME', sortable : true}, 
		    {name : 'file_NAME',index : 'file_NAME', width:300, sortable : true}, 
		    {name : 'cr_TIME_STR',index : 'cr_TIME_STR', sortable : true},
		    {name : 'cr_USER_NAME',index : 'cr_USER_NAME', sortable : true},
		    {name : 'file_DTL_SEQ_ID',index : 'file_DTL_SEQ_ID',sortable : false, hidden : true}
		],
		multiselect : false,
		caption : "已上傳檔案清單",
		rownumbers : true,
		width : 860,
		sortname : "file_TYPE_NAME",
		gridComplete : function() {
			// do something after grid loaded
			var ids = $("#fileGrid").jqGrid('getDataIDs');
			fileNames = [];
			for(var i=0;i < ids.length;i++){
				var cl = ids[i];
				var row = $("#fileGrid").jqGrid('getRowData', cl);
				var fileId = row.file_DTL_SEQ_ID;
				var fileName = row.file_NAME;
// 				be = "<a href="+CONTEXT_URL+"/common/file/download?fileId="+fileId+"><img src='../resources/img/download_file.png' class='downloadPic' /></a>";
				be = "<img src='../resources/img/download_file.png' style='cursor:pointer' class='downloadPic' onclick=downloadFile('"+fileId+"') />";
				de = "<img src='../resources/img/delete_file.png' style='cursor:pointer' class='deletePic' onclick=deleteFile('"+fileId+"') />"; 
				$("#fileGrid").jqGrid('setRowData',ids[i],{act:be+" "+de});		
				fileNames.push(fileName);
			}			
			reloadTips();
		}
	});//end grid
	
	
	$(window).resize(function(){
	    $(window).unbind("onresize");
	    $("#fileGrid,.grid").setGridWidth($("#jQgrids").width() -5);
	    $(window).bind("onresize", this);
	}).trigger('resize');
});

function reloadFileList(fileType) {	
	var data = {
		"typePath" : '${typePath}',
		"fileDoc" : '${fileDoc}',
		"fileType" : fileType
	};
	$("#fileGrid").setGridParam({datatype : "json",	postData : data	}).trigger("reloadGrid");
}

function downloadFile(dtlSeqId) {
	$.ajax({
		mimeType : 'text/html; charset=utf-8', // ! Need set mimeType only when run from local file
		type : 'POST',
		url : CONTEXT_URL + "/common/file/checkExist",
		dataType : "json",
		async : false,
		data : {
			"fileId" : dtlSeqId
		},
		success : function(data) {
			if (data.success) {
				window.location.href = CONTEXT_URL + "/common/file/download?fileId=" + dtlSeqId;
			} else {
				alert(data.msg);
			}
		}
	});
}

function deleteFile(dtlSeqId) {
	if (confirm('確定刪除檔案？')) {
		$.ajax({
			mimeType : 'text/html; charset=utf-8', // ! Need set mimeType only when run from local file
			type : 'POST',
			url : CONTEXT_URL + "/common/file/delete",
			dataType : "json",
			async : false,
			data : {
				"fileId" : dtlSeqId
			},
			success : function(data) {
				if (data.success) {
					alert('刪除成功');
					reloadFileList();
				} else {
					alert(data.msg);
				}
			}
		});
	}		
}

function reloadTips() {
	$('.downloadPic').poshytip({
		content: '點擊下載',
		className: 'tip-darkgray',
		bgImageFrameSize: 9,
		timeOnScreen: 3000
	});
	
	$('.deletePic').poshytip({
		content: '刪除檔案',
		className: 'tip-darkgray',
		bgImageFrameSize: 9,
		timeOnScreen: 3000
	});
}

function resetForm() {
	$(".qq-upload-list").html('');
}
</script>

<script type="text/template" id="qq-template">
	<div class="qq-uploader-selector qq-uploader">
        <div class="qq-upload-drop-area-selector qq-upload-drop-area" qq-hide-dropzone>
            <span>Drop files here to upload</span>
        </div>
        <div class="qq-upload-button-selector qq-upload-button btn btn-danger">
            <div id="uploadBtn">檔案上傳</div>
        </div>
        <span class="qq-drop-processing-selector qq-drop-processing">
            <span>Processing dropped files...</span>
            <span class="qq-drop-processing-spinner-selector qq-drop-processing-spinner"></span>
        </span>
        <ul class="qq-upload-list-selector qq-upload-list">
            <li>
                <div class="qq-progress-bar-container-selector">
                    <div class="qq-progress-bar-selector qq-progress-bar"></div>
                </div>
                <span class="qq-upload-spinner-selector qq-upload-spinner"></span>
                <img class="qq-thumbnail-selector" qq-max-size="100" qq-server-scale>
                <span class="qq-edit-filename-icon-selector qq-edit-filename-icon"></span>
                <span class="qq-upload-file-selector qq-upload-file"></span>
                <input class="qq-edit-filename-selector qq-edit-filename" tabindex="0" type="text">
                <span class="qq-upload-size-selector qq-upload-size"></span>
                <a class="qq-upload-cancel-selector btn-small btn-warning" href="#">Cancel</a>
                <a class="qq-upload-retry-selector btn-small btn-info" href="#">Retry</a>
                <a class="qq-upload-delete-selector btn-small btn-warning" href="#">Delete</a>
                <a class="qq-upload-pause-selector btn-small btn-info" href="#">Pause</a>
                <a class="qq-upload-continue-selector btn-small btn-info" href="#">Continue</a>
                <span class="qq-upload-status-text-selector qq-upload-status-text"></span>
                <a class="view-btn btn-small btn-info hide" target="_blank">View</a>
            </li>
        </ul>
    </div> 
</script>