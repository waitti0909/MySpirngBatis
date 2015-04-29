<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%-- 功能按紐統一由此自動生成 --%>
<%-- 請由各自頁面利用 <c:import url="/auth/role/menu/func/${currentMenuId}" /> --%>
<!-- <div class="form-group"> -->
	<c:forEach items="${btnList}" var="funBtn" varStatus="status">
<%-- 		<c:set var="divClass" value="col-sm-1" />	 --%>
		<c:if test="${status.index eq 0 }"><c:set var="divClass" value="col-sm-offset-0 col-sm-1" /></c:if>
		<div class="" style="float: left; padding-right:5px">
			<button class="btn btn-label-left ${funBtn.btnClass }" type="button" id="${funBtn.funCode}">
				<span><i class="${funBtn.btnIconClass }"></i></span>
				<s:message code="${funBtn.btnI18n}" text="${funBtn.btnName }" />
			</button>
		</div>
	</c:forEach>
	
	<%-- 重置(不做權限控管) --%>
	<div class="" style="float: left; padding-right:5px">
		<button class="btn btn-label-left btn-default" type="button" id="btn_reset">
			<span><i class="fa fa-undo txt-danger"></i></span>
			<s:message code="button.reset" text="重置" />
		</button>
	</div>	
<!-- </div> -->