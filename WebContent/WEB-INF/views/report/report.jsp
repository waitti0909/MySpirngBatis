<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>report</title>
</head>
<body>

	<ol>
		<li><a href='<s:url value="/report/pdf" />'>pdf</a></li>
		<li><a href='javascript:void(0);' onclick="window.open('<s:url value="/report/pdf" />')">open new window pdf</a></li>
		<li><a href='<s:url value="/report/xls" />'>xls</a></li>
		<li><a href='<s:url value="/report/csv" />'>csv</a></li>
		<li><a href='<s:url value="/report/html" />'>html</a></li>
	</ol>

</body>
</html>