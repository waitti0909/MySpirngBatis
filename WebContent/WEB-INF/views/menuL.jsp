<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="en">
<head>

<style type="text/css">
/* ul .HoverClass a{ */
/*   color:#228DFF; */
/* } */
/* ul .HoverClass a:hover{ */
/*   -webkit-animation: neon2 1.5s ease-in-out infinite alternate; */
/*   -moz-animation: neon2 1.5s ease-in-out infinite alternate; */
/*   animation: neon2 1.5s ease-in-out infinite alternate; */
/* } */
</style>
<script type="text/javascript">
	$(function() {
		
	});
	function LoadAjaxMenu(theField,parentId){
		$('.preloader').show();
		$.ajax({
			type: "POST",
			url: '<s:url value="/ajaxChildMenu" />',
			data: "parentMenuId="+parentId,
			success: function(data) {
				
				$('.preloader').hide();
				//$.log(data);
				var htmlString = [];
			
				if(!$(theField).next("ul").hasClass("dropdown-menu")){
					htmlString.push('<ul class="dropdown-menu">');
					$.each( data, function( i, item ) {
						if(item.isFolder!=1){
							
							if(item.url != '' && item.url!='/'  && item.url.length>2){
								htmlString.push('<li class="HoverClass"><a id="li_'+item.id+'" class="ajax-link" '+' href="<%= request.getContextPath()%>'+item.url+'?menuId='+item.id+'" >'+item.name+'</a></li>');
							}else{
								htmlString.push('<li class="HoverClass"><a id="li_'+item.id+'" class="ajax-link"  >'+item.name+'</a></li>');
								
							}
						}else{
							htmlString.push('<li class="dropdown HoverClass">');
							htmlString.push('<a href="#" id="li_'+item.id+'" class="dropdown-toggle" onclick="LoadAjaxMenu(this,'+item.id+');">');
							htmlString.push('<i class="fa fa-plus-square"></i> ');
							htmlString.push('<span class="hidden-xs">'+item.name+'</span>');
							htmlString.push('</a></li>');
						}
					});
					htmlString.push('</ul>');
					$(theField).after(htmlString.join(""));
				}
			},
			dataType: "json",
			async: false
		});
	}
		
</script>
</head>
<body>

	
			<ul class="nav main-menu">
				
				<c:forEach var="entry" items="${trees}">
				<c:set var="mas" value="${entry.value.master}"/>
				<li class="dropdown HoverClass">
					<a href="#" class="dropdown-toggle" onclick="LoadAjaxMenu(this,${mas.id});">
						<i class="fa fa-list"></i>
						 <span class="hidden-xs">${mas.name}</span>
					</a>
				</li>
				</c:forEach>
				
			</ul>
	&nbsp;
	<DIV id="objDiv" class="pull-right" style="display:none; Z-INDEX: 1; position:absolute;">
		<img src="<s:url value="/resources/img/success.png" />" />
	</DIV>
</body>
</html>