<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>NOMS</title>
<meta name="description" content="description">
<meta name="author" content="Charlie Woo">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="<s:url value="/resources/plugins/bootstrap/bootstrap.css" />" rel="stylesheet">
<link href="http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" rel="stylesheet">
<link href="<s:url value="/resources/css/style.css" />" rel="stylesheet">
<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
</head>

<body>
	<div class="container-fluid">
		<div class="row">
			<div id="page-500" class="col-xs-12 text-center">
				<img src="<s:url value="/resources/img/not_found.png" />" alt="" /> 
				<h1>We are Sorry !</h1>
				<h3>Oops, 404 Not Found</h3>
				<a href="<s:url value="/index/" />"
					class="btn btn-default btn-label-left txt-danger">
					<span><i class="fa fa-home"></i></span> 回首頁</a>&nbsp;&nbsp;
<%-- 					<a href="<s:url value="/index/" />" class="btn btn-default btn-label-left txt-danger"> --%>
<%-- 					<span><i class="fa fa-user-md"></i></span> 回報錯誤</a> --%>
			</div>
		</div>
	</div>
</body>
</html>