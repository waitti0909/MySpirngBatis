<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script src="<s:url value="/resources/plugins/scrolltable/jquery.scrolltable.js" />"></script>
<!DOCTYPE html>
<div>
	<table id="approvalList" class="scrollTable" style="table-layout: fixed; word-break: break-all; word-wrap: break-word;" width="100%">
		<thead class="tb_thead">
			<tr>
				<th class="tb_thead_th" style="width: 15%">日期</th>
				<th class="tb_thead_th" style="width: 15%">步驟名稱</th>
				<th class="tb_thead_th" style="width: 10%">職稱</th>
				<th class="tb_thead_th" style="width: 15%">人員</th>
				<th class="tb_thead_th" style="width: 10%">決議</th>
				<th style="width: 35%">意見</th>
			</tr>
		</thead>
		<tbody id="signHistoryTbody">
			<c:forEach items="${currentTodos}" var="cTodo">
				<tr class="highlightYellow">
					<td class="tb_td" style="width: 15%"></td>
					<td class="tb_td" style="width: 15%">${cTodo.taskOwnerDeptName}</td>
					<td class="tb_td" style="width: 10%">${cTodo.taskName}</td>
					<td class="tb_td" style="width: 15%">${cTodo.taskOwnerName}</td>
					<td class="tb_td" style="width: 10%">待決議</td>
					<td class="tb_td" style="width: 35%"></td>
				</tr>
			</c:forEach>
			<c:forEach items="${signHistory}" var="data">
				<tr>
					<td class="tb_td" style="width: 15%">
						<fmt:formatDate value="${data.SIGNDATE}" pattern="yyyy/MM/dd HH:mm:ss" />
					</td>
					<td class="tb_td" style="width: 15%">${data.SIGN_DEPT_NAME}</td>
					<td class="tb_td" style="width: 10%">${data.SIGN_POS_NAME}</td>
					<td class="tb_td" style="width: 15%">${data.SIGN_USER_NAME}</td>
					<td class="tb_td" style="width: 10%">${data.RESOLUTION}</td>
					<td class="tb_td" style="width: 35%">${data.COMMENT}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

<script type="text/javascript">
	$(function() {
		$("#approvalList").scrolltable({
			stripe : true,
			oddClass : 'odd',
			maxHeight : 100
		});
	});

</script>

<style type="text/css">

table.scrollTable {
	width: 100%;
	border: 2px solid #ddd;
	border-color: #000000;
}

.tb_thead {
	background-color: #f0f0f0;
}

.tb_thead_th {
	text-align: left;
	padding: 0.1em 0.3em;
}

.tb_td {
 	border-top: 1px solid #eee; 
 	border-right: 1px solid #eee; 
	padding: 0.1em 0.3em;
}

tbody tr.odd td {
	background-color: #f9f9f9;
}
tr.highlightYellow td {
	background-color:yellow !important;
}
</style>