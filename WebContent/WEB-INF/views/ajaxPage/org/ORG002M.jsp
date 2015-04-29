<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
<head>
<style>
.selectItemDiv{
	overflow: auto; 
	height: 440px;
/* 	float: left; */
}

</style>

<title>部門職稱人員設定</title>

</head>
<body>

	<div class="box-header">
		<div class="box-name">
			<i class="fa fa-group"></i> <span>部門職稱人員設定</span>
		</div>
		<div class="box-icons">			 
			<p class="expand-link"></p>
		</div>
		<div class="no-move"></div>
	</div>

	<div class="row">
		<div class="col-xs-12 col-sm-12">
			<div class=" ui-draggable ui-droppable">
				<div class="box-content">
					<form class="form-horizontal" role="form" id="addForm" method="get">
						<div id="menuList" style="width:24%;float: left;">
							<div class="col-xs-12 col-sm-12" style="padding-right:5px;">
								<fieldset class="butyBanner" >
									<legend class="butyLegend">部門列表</legend>
									<jsp:include page="/WEB-INF/views/ajaxPage/org/ORG002M_01M.jsp"></jsp:include>
								</fieldset>
							</div>
						</div>
						<div id="funList" style="width:38%;float: left;">
							<div class="col-xs-12 col-sm-12" style="padding-right:5px;">
								<fieldset class="butyBanner" >
									<legend class="butyLegend">部門職稱</legend>
									<jsp:include page="/WEB-INF/views/ajaxPage/org/ORG002M_02M.jsp"></jsp:include>
								</fieldset>
							</div>
						</div>
						<div id="deptList" style="width:38%;float: left;">
							<div class="col-xs-12 col-sm-12">
								<fieldset class="butyBanner" >
									<legend class="butyLegend">所屬員工</legend>
									<jsp:include page="/WEB-INF/views/ajaxPage/org/ORG002M_03M.jsp"></jsp:include>
								</fieldset>
							</div>
						</div>							
					</form>
				</div>
			</div>
		</div>
	</div>
	
	<script type="text/javascript">		
	//var editDeptId = '${deptId}' != '' ? '${deptId}' : 0;
		$(document).ready(function() {
			if('${showType}'=="view"){
				var nodes01M = document.getElementById("org002M_01Btn").getElementsByTagName('*');
				disabledBtn(nodes01M);
				var nodes02M = document.getElementById("deptPosButtons").getElementsByTagName('*');
				disabledBtn(nodes02M);
				var nodes03M = document.getElementById("psnPosButtons").getElementsByTagName('*');
				disabledBtn(nodes03M);
			}
			getDeptTree('${ckdeptId}');			
		});		
		
		function disabledBtn(nodes){
			for(var i = 0; i < nodes.length; i++){
				nodes[i].disabled = true;
			}
		}
	</script>

</body>
</html>