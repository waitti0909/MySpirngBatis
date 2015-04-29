<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<div class="col-xs-12 col-sm-12">
	<div class="box ui-draggable ui-droppable">
		<div class="box-content" >
			<div class="form-group">
				<table width="100%">
					<tr>
						<td><label>檔案相關：${typePathName}</label></td>
						<td><label>文件單號：${fileDoc}</label></td>
					</tr>
				</table>				
			</div>
		    <div id="fileListDiv"></div>
			<!-- 固定id for window.resize start-->
			<div id="jQgrid">
				<table id="fileGrid"></table>
				<div id="download_pager"></div>
			</div>
			<!-- 固定id for window.resize end-->
		</div>
	</div>
</div>

<script type="text/javascript">
function getFileList() {
// 	$.ajax({
// 		mimeType : 'text/html; charset=utf-8', // ! Need set mimeType only when run from local file
// 		type : 'POST',
// 		url : CONTEXT_URL + "/common/file/getList",
// 		dataType : "json",
// 		async : false,
// 		data : {
// 			"typePath" : '${typePath}',
// 			"fileDoc" : '${fileDoc}',
// 			"fileType" : '${fileType}'
// 		},
// 		success : function(data) {
// 			var rows = data.rows;
// 			fileNames = [];
			
// 			var comboHtml = '<table class="table table-heading table-hover"><thead>' + 
// 			'<tr><th>&nbsp;</th><th width="10%">檔案分類</th><th width="60%">檔名</th><th width="15%">上傳日期</th><th width="10%">上傳人員</th></tr></thead><tbody>';
// 			// loop
// 			for (var r in rows) {
// 				comboHtml += '<tr><td class="addMargin" nowrap><a href="'+CONTEXT_URL+'/common/file/download?fileId='+rows[r].file_DTL_SEQ_ID+'"><img src="../resources/img/download_file.png" class="downloadPic" /></a></td>';				
// 				comboHtml += '<td class="addMargin">'+rows[r].file_TYPE_NAME+'</td><td class="addMargin">'+rows[r].file_NAME+'</td><td class="addMargin">'+rows[r].cr_TIME_STR+'</td><td class="addMargin">'+rows[r].cr_USER+'</td></tr>';
// 				fileNames.push(rows[r].file_NAME);
// 			}
// 			comboHtml += '</tbody></table>';
			
// 			$("#fileListDiv").html(comboHtml);
// 			reloadTips();
// 		}
// 	});	

	$("#fileGrid").jqGrid({
		url : CONTEXT_URL + "/common/file/getList",
		datatype : "json",
		pager : '#download_pager',
		postData  : {
			typePath : '${typePath}',
			fileDoc : '${fileDoc}',
			fileType : '${fileType}'
		},
		colNames : [ '功能', '檔案分類', '檔案名稱', '上傳時間', '上傳人員', 'fileId' ],
		colModel : [ 
		    {name : 'act',index : 'act', width : 80, align : 'center', sortable : false}, 
		    {name : 'file_TYPE_NAME',index : 'file_TYPE_NAME', width:100, sortable : true}, 
		    {name : 'file_NAME',index : 'file_NAME', width:450, sortable : true}, 
		    {name : 'cr_TIME_STR',index : 'cr_TIME_STR', width:100, sortable : true},
		    {name : 'cr_USER_NAME',index : 'cr_USER_NAME', width:100, sortable : true},
		    {name : 'file_DTL_SEQ_ID',index : 'file_DTL_SEQ_ID',sortable : false, hidden : true}
		],
		multiselect : false,
		caption : "下載檔案清單",
		rownumbers : true,
		sortname : "file_TYPE_NAME",
		gridComplete : function() {
			// do something after grid loaded
			var ids = $("#fileGrid").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				var cl = ids[i];
				var row = $("#fileGrid").jqGrid('getRowData', cl);
				var fileId = row.file_DTL_SEQ_ID;
				var fileName = row.file_NAME;
// 				be = "<a href="+CONTEXT_URL+"/common/file/download?fileId="+fileId+"><img src='../resources/img/download_file.png' class='downloadPic' /></a>";
				be = "<img src='../resources/img/download_file.png' style='cursor:pointer' class='downloadPic' onclick=downloadFile('"+fileId+"') />";
				$("#fileGrid").jqGrid('setRowData',ids[i],{act:be});		
			}			
			reloadTips();
		}
	});//end grid
}

function reloadTips() {
	$('.downloadPic').poshytip({
		content: '點擊下載',
		className: 'tip-darkgray',
		bgImageFrameSize: 9,
		timeOnScreen: 3000
	});	
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
</script>