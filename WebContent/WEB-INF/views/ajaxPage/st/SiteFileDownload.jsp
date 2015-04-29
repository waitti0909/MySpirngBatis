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
						<td><label>基站編號：${btsSiteId}</label></td>
						<td><label>工務類別：</label>
							<select name="workIdSelect" id="workIdSelect" onchange="reloadFileList();">
								<option value="">全部</option>
								<c:forEach items="${works}" var="data">
									<option value="${data.key}">${data.value}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
				</table>				
			</div>
			<input type="hidden" name="siteId" id="siteId" value="${siteId}" />
		    <div id="fileListDiv"></div>
			<!-- 固定id for window.resize start-->
			<div id="jQgrid">
				<table id="siteFileGrid"></table>
				<div id="site_download_pager"></div>
			</div>
			<!-- 固定id for window.resize end-->
		</div>
	</div>
</div>

<script type="text/javascript">
function getFileList() {
	$("#siteFileGrid").jqGrid({
		url : CONTEXT_URL + "/st/file/getList",
		datatype : "json",
		pager : '#site_download_pager',
		postData  : {
			siteId : $("#siteId").val(),
			docNo : $("#workIdSelect").val()
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
		caption : "基站附件清單",
		rownumbers : true,
		sortname : "file_TYPE_NAME",
		gridComplete : function() {
			// do something after grid loaded
			var ids = $("#siteFileGrid").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				var cl = ids[i];
				var row = $("#fileGrid").jqGrid('getRowData', cl);
				var fileId = row.file_DTL_SEQ_ID;
				var fileName = row.file_NAME;
// 				be = "<a href="+CONTEXT_URL+"/common/file/download?fileId="+fileId+"><img src='../resources/img/download_file.png' class='downloadPic' /></a>";
				be = "<img src='../resources/img/download_file.png' style='cursor:pointer' class='downloadPic' onclick=downloadFile('"+fileId+"') />";
				$("#siteFileGrid").jqGrid('setRowData',ids[i],{act:be});		
			}			
			reloadTips();
		}
	});//end grid
}

function reloadFileList() {	
	var data = {
		"siteId" : $("#siteId").val(),
		"docNo"  : $("#workIdSelect").val()
	};
	$("#siteFileGrid").setGridParam({datatype : "json",	postData : data	}).trigger("reloadGrid");
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