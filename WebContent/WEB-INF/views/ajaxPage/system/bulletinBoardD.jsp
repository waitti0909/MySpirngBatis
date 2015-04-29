<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>公佈欄詳細資料</title>
</head>
<body>
<div class="box-header">
		<div class="box-name">
			<i class="fa fa-search"></i> <span>公告詳細資訊</span>
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
						<form class="form-horizontal">
							<div class="form-group">
								<label class="col-sm-2 control-label">主旨 :</label>
								<div class="col-sm-3 control-label" style="text-align: left;">${bulletinboardDetail.subject}</div>
							</div>
							<h4 class="page-header"></h4>

							<div class="form-group">
								<label class="col-sm-2 control-label">公佈單位:</label>
								<div class="col-sm-3 control-label" style="text-align: left;">${bulletinboardDetail.deptName}</div>

								<label class="col-sm-2 control-label">公佈人員 : </label>
							<div class="col-sm-3 control-label" style="text-align: left;">
								<c:choose>
									<c:when test="${bulletinboardDetail.chnNM !=null}">
										<c:out value="${bulletinboardDetail.chnNM}" />
									</c:when>
									<c:otherwise>
										<c:out value="${bulletinboardDetail.engNM}" />
									</c:otherwise>
								</c:choose>
							</div>
						</div>

							<div class="form-group">
								<label class="col-sm-2 control-label">公佈時間 : </label>
								<div class="col-sm-3 control-label" style="text-align: left;">
									<fmt:formatDate value="${bulletinboardDetail.startDate}"
										pattern="yyyy/MM/dd" />
								</div>

								<label class="col-sm-2 control-label">結束時間 : </label>
								<div class="col-sm-3 control-label" style="text-align: left;">
									<fmt:formatDate value="${bulletinboardDetail.endDate}"
										pattern="yyyy/MM/dd" />
								</div>
							</div>

							<h4 class="page-header"></h4>
							<div class="form-group">
								<label class="col-sm-2 control-label">公佈內容 : </label>
								<div class="col-sm-8" style="width:70%; BACKGROUND-COLOR: #f5f5f5;display:block;word-break: break-all;word-wrap: break-word;">
								${bulletinboardDetail.contents}
								</div>
							</div>

						</form>
					
				</div>
			</div>
		</div>
	</div>
</body>
</html>