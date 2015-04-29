<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html manifest='<c:url value="/resources/CACHE.MANIFEST" />'>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登錄驗證成功後，跑到這邊，需要跳轉</title>
<script>
var Backlen=history.length;   
history.go(-Backlen); 
var stateObj = { logResult: "LoginSuccess" };
history.pushState(stateObj, 'NOMS', '<c:url value="/index/" />'); 

</script>
</head>
<body>
<c:redirect url="/index/"></c:redirect>
</body>
</html>