<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>領料單簽收單</title>
<script
	src="<s:url value="/resources/plugins/tinymce/tinymce.min.js" />"></script>
<script
	src="<s:url value="/resources/plugins/tinymce/jquery.tinymce.min.js" />"></script>
<script type="text/javascript">
	$(document).ready(function() {});
</script>
</head>
<body>
	<div class="row">
		<div class="col-xs-12 col-sm-12">
			<div class=" ui-draggable ui-droppable">
				<div class="box-header">
					<div class="box-name">
						<i class="fa fa-edit"></i> <span>領料單簽收單</span>
					</div>
					<div class="box-icons">
						<p class="expand-link"></p>
					</div>
					<div class="no-move"></div>
				</div>
				<div class="box-content">
					<form class="form-horizontal" id="bBoardAddEdit" />
					<table style="width: 100%" align="center">
						<div class="form-group">
							<div class="row">
								<tr>
									<td><label class="col-sm-12 control-label">發料單號：</label></td>
									<td><div class="col-sm-6">${masterDscr.txnNo}</div></td>
									<td><label class="col-sm-12 control-label">列印人員：</label></td>
									<td><div class="col-sm-6">${masterDscr.printUser}</div></td>
								</tr>
						<tr>
							<td><label class="col-sm-12 control-label">發料倉庫：</label></td>
							<td>
								<div class="col-sm-6">${masterDscr.whName}</div>
							</td>
							<td><label class="col-sm-12 control-label">列印時間：</label></td>
							<td><div class="col-sm-6">${masterDscr.printTime}</div></td>
						</tr>
						</div>
						</div>
					</table>
					<hr>
					<table style="width: 80%" align="center" frame="border" cellpadding="5" cellspacing="5" frame="border" rules="all">
						<tr>
							<td>項次</td>
							<td>領料單號</td>
							<td>領料部門</td>
							<td>申請人</td>
							<td>站台</td>
							<td>工單編號</td>
							<td>料號</td>
							<td>品名</td>
							<td>廠商序號</td>
							<td>貼標號碼</td>
							<td>領料數</td>
						</tr>
						<c:forEach items="${detail}" var="detail" varStatus="status">
							<tr>
								<td>${status.count}</td>
								<td>${detail.ref_act_no}</td>
								<td>${detail.dept_name}</td>
								<td>${detail.appUserName}</td>
								<td>${detail.site_name}</td>
								<td>${detail.order_no}</td>
								<td>${detail.mat_no}</td>
								<td>${detail.matName}</td>
								<td>${detail.venSn}</td>
								<td>${detail.tagNo}</td>
								<td>${detail.qty}</td>
							</tr>
						</c:forEach>	
					</table>
					<hr>
					
					<table style="width: 70%;height: 100px" align="center" cellpadding="5" cellspacing="5" frame="border" rules="all">
						<tr>
							<td style="height: 15px" align="center">發料倉管簽章</td><td style="height: 15px" align="center">領料人簽章</td>
						</tr>
						<tr>
							<td></td>
							<td></td>
						</tr>
					</table>
				</div>
			</div>
			</form>
		</div>
	</div>
</body>
</html>