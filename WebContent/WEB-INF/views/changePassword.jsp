<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<title>Change password page</title>
<style type="text/css">
.label {
	color: black;
}
</style>
<script type="text/javascript">
	jQuery(document).ready(function() {
		var validator = jQuery("#changePasswordForm").validate({
			rules : {
				oldPassword : "required",
				newPassword : {
					required : true,
					minlength : 5
				},
				confirmNewPassword : {
					required : true,
					minlength : 5,
					equalTo : "#newPassword"
				}
			},
			messages : {
				oldPassword : "請輸入原始密碼",
				newPassword : {
					required : "請輸入新密碼",
					minlength : jQuery.format("密碼最小長度為{0}")
				},
				confirmNewPassword : {
					required : "請再次輸入新密碼",
					minlength : jQuery.format("密碼最小長度為{0}位"),
					equalTo : "兩次密碼輸入不一致"
				}
			}
		});
	});
</script>
</head>
<body>
	<div class="row well">
		<div class="label col-md-12 text-center">Change Password</div><br/><br/>
		<div class="col-md-4 col-md-offset-4">
			<sf:form id="changePasswordForm" modelAttribute="changePasswordValidationBean" method="post" cssClass="form">
				<fieldset>
					<p>
						<label for="oldPassword" class="label">Old password:</label> <br />
						<sf:password path="oldPassword" />
						<sf:errors id="oldPassword" path="oldPassword" cssClass="error" />
					</p>

					<p>
						<label for="newPassword" class="label">New password:</label> <br />
						<sf:password path="newPassword" />
						<sf:errors id="newPassword" path="newPassword" cssClass="error" />
					</p>

					<p>
						<label for="confirmNewPassword" class="label">Confirm new password:</label> <br />
						<sf:password path="confirmNewPassword" />
						<sf:errors id="confirmNewPassword" path="confirmNewPassword"
							cssClass="error" />
					</p>

					<p>
						<input type="submit" value="Change Password" class="btn button btn-primary" />
					</p>
				</fieldset>
			</sf:form>


		</div>
	</div>












</body>
</html>
