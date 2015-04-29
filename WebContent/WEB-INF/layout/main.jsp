<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html lang="en" >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-control" content="no-cache">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
 <meta http-equiv="Cache-Control" content="no-cache" />
	<meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="Expires" content="0" />
<link rel="shortcut icon"
	href="<s:url value="/resources/img/favicon.ico" />">
<title><decorator:title default="NOMS" /></title>
<jsp:include page="/WEB-INF/layout/jsLayout.jsp" />

<decorator:head />

<script type="text/javascript">
$(function() {
	$().UItoTop();
	window.onpopstate = function(event) {
		
		 var stateObj = { logResult: "LoginSuccess" };
		 history.pushState(stateObj, 'NOMS', '<c:url value="/index/" />'); 
		  
	};
	 var stateObj = { logResult: "LoginSuccess" };
	 history.pushState(stateObj, 'NOMS', '<c:url value="/index/" />'); 
});
</script>
<style type="text/css">
#main2 {
  margin-top: 65px;
  min-height: 668px;
  overflow: hidden;
}

#footer {
 	box-sizing: border-box;
    /* 設定footer絕對位置在底部 */
    position: static;
    bottom: 0;
    /* 展開footer寬度 */
    width: 100%;
}
</style>
</head>

<body id="loadingBody">
	<!--Start Header-->
	<div id="modalbox">
		<div class="devoops-modal">
			<div class="devoops-modal-header">
				<div class="modal-header-name">
					<span>Basic table</span>
				</div>
				<div class="box-icons">
					<a class="close-link"> <i class="fa fa-times"></i>
					</a>
				</div>
			</div>
			<div class="devoops-modal-inner"></div>
			<div class="devoops-modal-bottom"></div>
		</div>
	</div>
	<header class="navbar">
		<div class="container-fluid expanded-panel">
			<div class="row">
				<div id="logo" class="col-xs-12 col-sm-3" >
					<a href="<s:url value="/index/" />">&nbsp;</a>
				</div>
				<div id="top-panel" class="col-xs-12 col-sm-8">
					<div class="row">
<!-- 						<div class="col-xs-8 col-sm-4"> -->
<!-- 							<a href="#" class="show-sidebar"> <i class="fa fa-bars"></i> -->
<!-- 							</a> -->
<!-- 							<div id="search"> -->
<!-- 								<input type="text" placeholder="search" /> <i -->
<!-- 									class="fa fa-search"></i> -->
<!-- 							</div> -->
<!-- 						</div> -->
						<div class="col-xs-4 col-sm-9 top-panel-right">
							<jsp:include page="/WEB-INF/views/loginProfileInfo.jsp" />
						</div>
					</div>
				</div>
			</div>
		</div>
	</header>
	<!--End Header-->
	<!--Start Container-->	
	<div id="main2" class="container-fluid">
		<div class="row">

			<div id="sidebar-left" class="col-xs-2 col-sm-2" style="width:15%">
				<!-- menu -->
				<c:import url="/menu" />
			</div>

			<!--Start Content-->
			<div id="content" class="col-xs-12 col-sm-10" style="width:85%">
				<div class="preloader">
					<img src="../resources/img/devoops_getdata.gif"
						class="devoops-getdata" alt="preloader" />
				</div>
				<div id="ajax-content">
					<decorator:body />
				</div>
			</div>
			<!--End Content-->
		</div>
	</div>
	
	<!--End Container-->
	
	<!-- Start Footer -->
	<footer id="footer">
		<jsp:include page="/WEB-INF/views/footer.jsp" />
	</footer>
	<!-- End Footer -->
</body>
</html>