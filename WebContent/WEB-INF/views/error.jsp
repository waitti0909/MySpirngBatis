<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>Server Error Page</title>
</head>
<body>
	<h2>Error occurs!!!</h2>
	<h2>${errCode}</h2>
	<h2>${errMsg}</h2>
	<p>
		Back to <a href="<c:url value="/index/" />">home</a> page
	</p>
	${stack}
</body>
</html>