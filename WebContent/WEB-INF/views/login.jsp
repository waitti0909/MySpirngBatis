<!-- <note_column_mapping>
j_username:姓名
j_password：密碼
</note_column_mapping>
<note_column_validate>
j_username:chkNotBlank
j_password:chkNotBlank
</note_column_validate>-->
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page import="com.foya.noms.resources.AppConstants" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Cache-Control" content="no-cache" />
	<meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="Expires" content="0" />
    <link rel="shortcut icon" href="<s:url value="/resources/img/favicon.ico" />">
    
    <title>NOMS</title>

    <!-- Bootstrap core CSS -->
	
	<link href="<s:url value="/resources/plugins/bootstrap/bootstrap.min.css" />" rel="stylesheet">
	
	<link href="<s:url value="/resources/plugins/fontawesome/css/font-awesome.css" />" rel="stylesheet">
	
	<link href='<s:url value="/resources/css/righteous.css" />' rel='stylesheet' type='text/css'>
	
	<link href="<s:url value="/resources/css/style.css" />" rel="stylesheet">
	
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script type="text/javascript" src="<s:url value="/resources/js/html5shiv.js" />"></script>
      <script type="text/javascript" src="<s:url value="/resources/js/respond.min.js" />"></script>
    <![endif]-->
    
    
<script src="<s:url value="/resources/plugins/jquery/jquery-2.1.0.min.js" />"></script>
<script src="<s:url value="/resources/plugins/jquery-ui/jquery-ui.min.js" />"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="<s:url value="/resources/plugins/bootstrap/bootstrap.min.js" />"></script>
<script src="<s:url value="/resources/js/main.js" />"></script>
<c:set var="env" value="<%=AppConstants.ENVIRONMENT %>" />

<script type="text/javascript">
	var env = 
    $(document).ready(function() {
    	

    	LoadBootstrapValidatorScript('<s:url value="/resources/plugins/bootstrapvalidator/bootstrapValidator.min.js" />',loginFormValidator);
    	// Add drag-n-drop feature to boxes
    	WinMove();
    });
    function loginFormValidator(){
    	$('#loginForm').bootstrapValidator({
    		message: 'This value is not valid',
    		fields: {
    			j_username: {
    				message: 'The username is not valid',
    				validators: {
    					notEmpty: {
    						message: 'The username is required and can\'t be empty'
    					},
    					regexp: {
    						regexp: /^[a-zA-Z0-9_\.]+$/,
    						message: 'The username can only consist of alphabetical, number, dot and underscore'
    					}
    				}
    			},
    			j_password: {
    				validators: {
    					notEmpty: {
    						message: 'The password is required and can\'t be empty'
    					}
    				}
    			}
    		}
    	});
    }
    
    // 正式環境自動勾選
    function changeUserType(userType) {
    	if ('${env}' == 'TSTAR_PROD') {
    		if (userType == 'E') {
        		$("#useAD").prop("checked", "checked");
        	} else if (userType == 'V') {
        		$("#useAD").prop("checked", "");
        	} else {
        		$("#useAD").prop("checked", "");
        		alert("無效的登入身分");
        	}
    	}    	
    }
    </script>
</head>

<body>
	<div class="container-fluid">
	<div class="row">
	<div class="col-md-12" id="breadcrumb">
		<ol class="breadcrumb">
			<li><a href="javascript:void(0);">NOMS Login Page</a></li>
		</ol>
		

	<div id="page-login" class="row">
	
	
	
			<div class="container">
				<div class="text-center danger">
					<c:if test="${not empty param.error}">
						<p class="bg-danger"> <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>.
						<s:message code="msg.login.failure"  text="Login Fail" />
						</p>
					</c:if>
				</div>
			</div>
			
		<div class="col-xs-12 col-md-4 col-md-offset-4 col-sm-6 col-sm-offset-3">
			<div class="text-right">
			&nbsp;
			</div>
			<div class="box">
				<div class="box-content">
				<form id="loginForm" action="<s:url value="/loginProcess/" />" method="post">
					<div class="text-center">
						<h3 class="page-header">NOMS Login Page</h3>
					</div>
					<div class="form-group">
						<div class="col-sm-6">
							<label class="control-label">登入身份</label>
							<select name="userType" onchange="changeUserType(this.value);"><option value="E">員工</option><option value="V">廠商</option></select>
						</div>
						<div class="col-sm-6">
						<c:if test="${env eq TSTAR_PROD }">
							<label class="control-label" style="display:none;">USE AD</label> 
							<input type="checkbox" name="useAD" id="useAD" value="Y" style="display:none;">
						</c:if>	
						<c:if test="${env ne TSTAR_PROD }">
							<label class="control-label">USE AD</label> 
							<input type="checkbox" name="useAD" id="useAD" value="Y">
						</c:if>
						</div>
					</div>
					<div class="form-group">
						
					</div>
					<div class="form-group">
						<label class="control-label">Username</label>
						<input type="text" placeholder="username" name="j_username" id="j_username" class="form-control" name="username"/>
					</div>
					<div class="form-group">
						<label class="control-label">Password</label>
						<input type="password" placeholder="password" name="j_password" id="j_password" class="form-control" name="password"/>
					</div>
					<div class="text-center">
						<button type="submit" class="btn btn-primary btn-label-left"><span><i class="fa fa-sign-in"></i></span><s:message code="button.login"  text="Sign in" /></button>
						
					</div>
					
					
				
					</form>
				</div>
			</div>
		</div>
	</div>
	
	</div>
</div>
</div>
	

</body>
</html>