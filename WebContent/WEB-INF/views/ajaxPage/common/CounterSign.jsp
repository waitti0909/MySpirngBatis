<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
<head>

<style>
.ui-state-highlight { height: 1.5em; line-height: 1.2em; }
.memberSel {
    width: 200px;
}
.nav_list {
 padding:20px;
 font-size: 1em;
}
.nav_list li {
  list-style-type:none;
}
.nav_list li+li {
  margin-top:3px
}
.nav_list li span{
  border: 1px solid gray;
  padding: 1px;
  background-color:#D8D8D8;
  display: inline-block;
  width: 200px; 
  margin-left:5px;
  margin-top:5px
}

</style>
<title>加/會簽</title>
</head>
<body>
	<div class="row">
		<div class="col-xs-12 col-sm-12">
			<div class=" ui-draggable ui-droppable">
				<div class="box-content">
					<form class="form-horizontal" id="counterSignForm" method="get">
						<div id="approvalBtn" style="padding-left: 2em">
							<button class="btn btn-label-left btn-primary" type="button"
								id="counterSignAgree">
								<s:message code="" text="確定" />
							</button>
							<button class="btn btn-label-left btn-primary" type="button"
								id="counterSignCancel">
								<s:message code="" text="取消" />
							</button>	
						</div>

						<div class="form-group">
							<label class="col-sm-1 control-label">意見 : </label>
							<div class="col-sm-10">
								<textarea class="form-control" id="commentCS"
									name="commentCS" rows="3" maxlength="200">${comment}</textarea>
							</div>
						</div>
						<br>

						<div class="form-group">
							<div class="row">
								<div class="col-md-5">
									<label class="col-sm-3 control-label">部門：</label>
									<div class="col-sm-8">
										<select class="form-control" name="counterSignDept"
											id="counterSignDept" onchange="getDeptMember(this.value);">
											<option value="">-- 請選擇 --</option>
											<c:forEach items="${deptMap}" var="data">
												<option value="${data.key}">${data.value}</option>
											</c:forEach>
										</select> 
									</div>
								</div>

								<div class="col-md-5">
									<label class="col-sm-3 control-label">員工：</label>
									<div class="col-sm-6">
										<select id="memberSel" class="memberSel" name="interest" size="8">
										</select>
									</div>
								</div>

								<div class="col-md-2">
									<button class="btn btn-label-left btn-primary" type="button"
										id="counterSignAdd" onclick="addMember()">
										<s:message code="" text="加入" />
									</button>
								</div>

							</div>
						</div>

						<br>
						<div class="form-group">
							<div id="signMemberDiv"
								style="overflow-y: scroll; border: 1px solid #000000; background-color: #F0F8FF;; width: 600px; height: 240px; margin: 0 auto;">
								<ul id="signMember" class="nav_list">
								</ul>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		$(document).ready(function() {
			$("#signMember").sortable({
				placeholder : "ui-state-highlight"
			});
			$("#signMember").disableSelection();

			//確定按鈕
			$("#counterSignAgree").click(function() {
				if ($('#commentCS').val() == "") {
					alert("請填寫簽核意見");
				} else {
					var memberArray=[];
					if ($("#signMember li").length != 0) {
						$('#signMember li').each(function(index) {
							//memberArray.push($(this).text().trim());
							memberArray.push($(this).attr('id'));
						});
						toCounterSign('${isAgent}', memberArray);
					}else{
						alert("請至少選擇一位加會簽人員!");
					}
				}
			});
		});
		//取得部門下人員
		function getDeptMember(dept) {
			$('#memberSel').empty();
			$.ajax({
				url : CONTEXT_URL + "/commom/counterSign/getDeptMember",
				dataType : "json",
				data : {
					"dept" : $("#counterSignDept").val()
				},
				type : "POST",
				async : false,
				success : function(data) {
					var rows = data.rows;
					for ( var r in rows) {
						$('#memberSel').append(
								"<option value='"+rows[r].psn_NO+"'>"
										+ rows[r].chn_NM + "</option>");
					}
				}
			});
		}
		
		//加入 加/會簽成員
		function addMember() {
			var member = '';
			var memberId = '';
			$("#memberSel > option").each(function() {
				if (this.value == $('#memberSel').val()) {
					member = this.text;
					memberId = this.value;
				}
			});
			if (checkMember(member) == 1) {
				alert("此成員已存在!");
			} else {
				var str = '<li id="'+memberId+'"><span><button type="button" onclick="deleteMember()"><i class="fa fa-times txt-danger"></i></button>&nbsp;&nbsp;&nbsp;&nbsp;'
						+ member + '</span></li>';
				$('#signMember').append(str);
			}
		}

		//移除 加/會簽成員
		function deleteMember() {
			var ulList = document.getElementById("signMember");
			var aList = ulList.getElementsByTagName("li");
			for (var i = 0; i < aList.length; i++) {
				aList[i].onclick = function() {
					$(this).remove();
				};
			}
		}

		//檢查選取成員是否重複
		function checkMember(member) {
			var isDup = 0;
			if ($("#signMember li").length != 0) {
				$('#signMember li').each(function(index) {
					if (member == $(this).text().trim()) {
						isDup = 1;
					}
				});
			}
			return isDup;
		}

		//加會簽送出
		function toCounterSign(isAgent,memberArray) {
			var result = '';
			if (isAgent == "N") {
				if ('${approveType}' == "COUNTERSIGN") { //加簽
					result = workflowCountersign('${taskId}', '${processType}',
							'${applyNo}', memberArray, '${taskOwnerGroupId}', $('#commentCS').val(),
							'${taskStartTime}','${jsonData}');
				} else if ('${approveType}' == "NOTIFY") { //會簽
					result = workflowNotify('${taskId}', '${processType}',
							'${applyNo}', memberArray, '${taskOwnerGroupId}', $('#commentCS').val(),
							'${taskStartTime}','${jsonData}');
				}
				doResultAction(result, 0);
			} else {
				if ('${approveType}' == "COUNTERSIGN") { //代理加簽
					result = workflowAgentCountersign('${taskId}',
							'${processType}', '${applyNo}', memberArray,
							'${taskOwnerId}', '${taskOwnerGroupId}', $('#commentCS').val(),
							'${taskStartTime}','${jsonData}');
				} else if ('${approveType}' == "NOTIFY") { //代理會簽
					result = workflowAgentNotifys('${taskId}',
							'${processType}', '${applyNo}', memberArray,
							'${taskOwnerId}','${taskOwnerGroupId}', $('#commentCS').val(),
							'${taskStartTime}','${jsonData}');
				}
				doResultAction(result, 1);
			}
			var frameId = $("#frameId").val();
			$("#" + frameId).dialog('close');
		}

		function doResultActionCN(result, reloadTabIndex) {
			if (result == "success") {
				$.fancybox.close();
				$('#todoTabs').tabs('load', reloadTabIndex);
			}
		}
	</script>
</body>
</html>