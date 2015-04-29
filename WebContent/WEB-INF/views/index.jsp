<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>NOMS System</title>	
<style type="text/css">
.titleTD {
	text-align: center;
}

.textTD {
	padding: 0.2em;
}
</style>
</head>
<body>
<div class="row">
	<div id="breadcrumb" class="col-md-12">
		<ol class="breadcrumb">
			<li><a href="<s:url value="/index/" />">首頁</a></li>
			<li><a href="#">電子公佈欄</a></li>
		</ol>
	</div>
</div>

<div class="col-xs-16">
	<%-- Button import --%>
	<c:import url="/bulletboard/" />
</div>
		

<script type="text/javascript">
	$(document).ready(function() {
		
	});

	//顯示公告欄資訊
	function showContents(b_ID) {
		$.fancybox.open({
			href : CONTEXT_URL + "/sysBulletinboard/contents/",
			type : 'ajax',
			width : $(window).width(),
			height : $(window).height(),
			autoSize : false,
			padding : [ 0, 0, 0, 0 ],
			scrolling : 'yes',
			helpers : {
				overlay : {
					closeClick : false
				},
				title : {
					type : 'float'
				}
			},
			title : "公告欄資訊",
			openEffect : 'elastic',
			closeEffect : 'fade',
			ajax : {
				data : {
					b_ID : b_ID
				},
				type : "POST",
				async : false
			}
		});
	}
	
</script>
</body>
</html>