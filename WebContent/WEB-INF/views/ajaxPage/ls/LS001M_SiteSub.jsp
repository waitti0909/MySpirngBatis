<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>

<div id="siteTabs" style="margin-top: 20px">
	<ul>
		<c:if test="${lsMain.hasRentCat()}"><li id="siteTabs-1_li" onclick=""><a href="#siteTabs-1">租金</a></li></c:if>
		<c:if test="${lsMain.hasElecCat()}"><li id="siteTabs-2_li" onclick="ajaxElecData();"><a href="#siteTabs-2">用電</a></li></c:if>
		<c:if test="${lsMain.hasExchangeCat()}"><li id="siteTabs-3_li" onclick="ajaxExchData();"><a href="#siteTabs-3">資源互換</a></li></c:if>
	</ul>
	<c:if test="${lsMain.hasRentCat()}">
	<div id="siteTabs-1">
		<jsp:include
			page="/WEB-INF/views/ajaxPage/ls/LS001M_SiteRent.jsp"></jsp:include>
	</div>
	</c:if>
	<c:if test="${lsMain.hasElecCat()}">
	<div id="siteTabs-2">
		<jsp:include
			page="/WEB-INF/views/ajaxPage/ls/LS001M_SiteElec.jsp"></jsp:include>
	</div>
	</c:if>
	<c:if test="${lsMain.hasExchangeCat()}">
	<div id="siteTabs-3">
		<jsp:include
			page="/WEB-INF/views/ajaxPage/ls/LS001M_SiteExch.jsp"></jsp:include>
	</div>
	</c:if>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		console.log("1="+${lsMain.hasRentCat()}+",2="+${lsMain.hasElecCat()}+",3="+${lsMain.hasExchangeCat()});
		$("#siteTabs").tabs();
	});

	
</script>