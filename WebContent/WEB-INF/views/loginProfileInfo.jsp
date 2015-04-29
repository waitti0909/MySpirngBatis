<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
<script type="text/javascript">
	$(function() {
		
		$("#logoutLink").click(function(){
			location.href="<c:url value="/logout/" />";
		});
		$("#todoLink").click(function(){
			LoadAjaxContent(CONTEXT_URL + '/profile/todoList');
		});
		$("#todoLink2").click(function(){
			LoadAjaxContent(CONTEXT_URL + '/profile/todoOrders');
		});
	});
	
	
</script>
</head>
<body>

	<ul class="nav navbar-nav pull-right panel-menu">
<!-- 		<li class="hidden-xs"><a href="index.html" class="modal-link"> -->
<%-- 				<i class="fa fa-bell"></i> <span class="badge">1</span> --%>
<!-- 		</a></li> -->

 		<li class="hidden-xs"><a id="todoLink" href="javascript:void(0);" class="ajax-link"> <i
						class="fa fa-legal"></i> <span class="hidden-sm text">待簽事項</span>
				</a></li>
		<li class="hidden-xs"><a id="todoLink2" href="javascript:void(0);" class="ajax-link"> <i
						class="fa fa-list"></i> <span class="hidden-sm text">待辦事項</span>
				</a></li>  
		
		<li class="hidden-xs"><a id="logoutLink" href="javascript:void(0);" > 
		<i class="fa fa-sign-out"></i> <span class="">Logout</span>
		</a></li>
		<li class="dropdown">
			<a href="#" class="dropdown-toggle account" data-toggle="dropdown">
				<div class="avatar">
					<img src="../resources/img/avatar.png" class="img-rounded"
						alt="avatar" />
				</div> <i class="fa pull-right"></i>
				<div class="user-mini pull-right">
					<span class="welcome">歡迎, <sec:authentication property="principal.chName"></sec:authentication></span><span><sec:authentication property="principal.deptName"></sec:authentication></span> 
				</div>
			</a>
<!-- 			<ul class="dropdown-menu"> -->
<%-- 				<li><a href="#"> <i class="fa fa-user"></i> <span --%>
<%-- 						class="hidden-sm text">Profile</span> --%>
<!-- 				</a></li> -->
<!-- 				<li><a href="ajax/page_messages.html" class="ajax-link"> <i -->
<%-- 						class="fa fa-envelope"></i> <span class="hidden-sm text">Messages</span> --%>
<!-- 				</a></li> -->
<!-- 				<li><a href="ajax/gallery_simple.html" class="ajax-link"> <i -->
<%-- 						class="fa fa-picture-o"></i> <span class="hidden-sm text">Albums</span> --%>
<!-- 				</a></li> -->
<!-- 				<li><a href="ajax/calendar.html" class="ajax-link"> <i -->
<%-- 						class="fa fa-tasks"></i> <span class="hidden-sm text">待辦</span> --%>
<!-- 				</a></li> -->
<%-- 				<li><a href="#"> <i class="fa fa-cog"></i> <span --%>
<%-- 						class="hidden-sm text">Settings</span> --%>
<!-- 				</a></li> -->
				
<!-- 			</ul> -->
		</li>
	</ul>

</body>
</html>