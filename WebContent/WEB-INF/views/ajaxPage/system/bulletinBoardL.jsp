<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<div class="form-group" style="margin-top:15px">
	<div class="box-header">
		<div class="box-name">
			<i class="fa fa-list-alt"></i> <span>最新公告</span>
		</div>
	</div>
	<div class="box-content no-padding">
		<table class="table table-heading table-hover">
			<thead>
				<tr>
					<th>公告類別</th>
					<th>主旨</th>
					<th>公佈日期</th>
					<th>公佈單位</th>
					<th>公佈人員</th>
					<th>附件</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${bulletinboardList}" var="bblist" varStatus="status">
					<tr>
						<td class="addMargin" onclick="showContents(${bblist.bulletinID})"><c:out value="${bblist.typesName}" /></td>
						<td class="addMargin" onclick="showContents(${bblist.bulletinID})"><c:out value="${bblist.subject}" /></td>
						<td class="addMargin" onclick="showContents(${bblist.bulletinID})"><fmt:formatDate value="${bblist.startDate}" pattern="yyyy/MM/dd" /></td>
						<td class="addMargin" onclick="showContents(${bblist.bulletinID})"><c:out value="${bblist.deptName}" /></td>
						<td class="addMargin" onclick="showContents(${bblist.bulletinID})">
							<c:choose>
								<c:when test="${bblist.chnNM !=null}">
									<c:out value="${bblist.chnNM}" />
								</c:when>
								<c:otherwise>
									<c:out value="${bblist.engNM}" />
								</c:otherwise>
							</c:choose>
						</td>
						<td class="addMargin"><img src="../resources/img/download_box.png" onclick="goToDownload('${bblist.bulletinID}')" style="cursor:pointer" /></td>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div id="showFileDownloadFrame"></div>
</div>

<script type="text/javascript">
function goToDownload(fileDoc) {
	$.ajax({
		mimeType : 'text/html; charset=utf-8', // ! Need set mimeType only when run from local file
		type : 'GET',
		url : CONTEXT_URL + "/common/file/initDownload",
		data : {
			"typePath" : "BULLETIN",
			"fileDoc" : fileDoc,
			"fileType" : "NOTYPE"
		},
		dataType : "html",
		async : false,
		success : function(data) {
			$("#showFileDownloadFrame").html(data);
			$("#showFileDownloadFrame").dialog({
				width : 950,
				height : 600,
				modal: true,
				title: "檔案下載",
				resizable: false,
				position: { my: "center", at: "center"},
				close: function() {
			  		$("#showFileDownloadFrame").html('');
			    },
			    open: function (event, ui) {
			    	getFileList();
			    	$('.ui-dialog').css('z-index',10300);
			    	$('.ui-widget-overlay').css('z-index',10200);
			   	}
			});
		}
	});
}
</script>