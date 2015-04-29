<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>

<table style="width: 100%">
	<tr>
		<td valign="top" nowrap="nowrap" style="width: 6%"><label
			class="col-sm-12 control-label">簽核意見 :</label></td>
		<td style="width: 94%">
			<div class="col-sm-12">
				<c:import url="/commom/signHistory">
					<c:param name="processType" value="${processType}"></c:param>
					<c:param name="applyNo" value="${applyNo}"></c:param>
				</c:import>
			</div>
		</td>
	</tr>
</table>

<script type="text/javascript">
	$(document).ready(function() {	
		var div_data=document.getElementById('approvalList').getElementsByTagName('div');
        div_data[0].id="approvalListDiv";
        $("#approvalListDiv").css("max-height", "400px");

		$('#approvalList table:last').addClass('table-hover');
	});
</script>
