<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>

<!-- 回饋門號 START -->
<div style="margin-top: 5px">
	<fieldset class="ftSolidLine">
		<legend class="legend">回饋門號</legend>
		<button class="btn btn-primary btn-label" type="button" id=""
			onclick="addPhoneNumRow('phoneNumFBTbody');">新增</button>
		<button class="btn btn-primary btn-label" type="button" id=""
			onclick="toDelReward('phoneNumFBTB');">刪除</button>
		<table id="phoneNumFBTB"
			class="table table-bordered table-hover table-heading no-border-bottom"
			style="width: 100%; margin-top: 5px">
			<thead>
				<tr style="height: 30px">
					<th width=14%>申辦證號</th>
					<th width=14%>姓名</th>
					<th width=14%>門號</th>
					<th width=17%>資費方案</th>
					<th width=17%>回饋原因</th>
					<th width=24%>回饋說明</th>
				</tr>
			</thead>
			<tbody id="phoneNumFBTbody">
				<c:forEach items="${lease.rewardList}" var="pnL">
					<c:if test="${pnL.REWARD_TYPE eq '1'}">
						<tr>
							<td><input class="form-control" name="REWARD_ID" class="REWARD_ID" maxlength="10" type="text" style="width: 100%;" value="${pnL.REWARD_ID}"></td>
							<td><input class="form-control" name="CUST_NAME"  maxlength="50" type="text" style="width: 100%;" value="${pnL.CUST_NAME}"></td>
							<td><input class="form-control" name="PHONE_NBR" maxlength="20" type="text" style="width: 100%;" value="${pnL.PHONE_NBR}"></td>
							<td><input class="form-control" name="PRCING" maxlength="100" type="text" style="width: 100%;" value="${pnL.PRCING}"></td>
							<td><select id="" class="" name="REWARD_RESN">
									<c:forEach items="${rewardResn}" var="data">
										<c:choose>
											<c:when test="${data.key eq pnL.REWARD_RESN}">
												<option value="${data.key}" selected="selected">${data.value}</option>
											</c:when>
											<c:otherwise>
												<option value="${data.key}">${data.value}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach></select></td>
							<td><input class="form-control" name="REWARD_DESC" maxlength="500" type="text" style="width: 100%;" value="${pnL.REWARD_DESC}"></td>
						</tr>
					</c:if>
				</c:forEach>
			</tbody>
		</table>
	</fieldset>
</div>
<!-- 回饋門號  END -->

<!-- 回饋手機 START -->
<div style="margin-top: 5px">
	<fieldset class="ftSolidLine">
		<legend class="legend">回饋手機</legend>
		<button class="btn btn-primary btn-label" type="button" id=""
			onclick="addMobPhoneRow('mobPhoneFBTbody');">新增</button>
		<button class="btn btn-primary btn-label" type="button" id=""
			onclick="toDelReward('mobPhoneFBTB');">刪除</button>

		<table id="mobPhoneFBTB"
			class="table table-bordered table-hover table-heading no-border-bottom"
			style="width: 100%; margin-top: 5px">
			<thead>
				<tr style="height: 30px">
					<th width=16%>手機型號</th>
					<th width=16%>數量</th>
					<th width=16%>預估金額</th>
					<th width=16%>回饋原因</th>
					<th width=36%>回饋說明</th>
				</tr>
			</thead>
			<tbody id="mobPhoneFBTbody">
				<c:forEach items="${lease.rewardList}" var="mpL">
					<c:if test="${mpL.REWARD_TYPE eq '2'}">
						<tr>
							<td><input class="form-control" name="REWARD_ID" class="REWARD_ID" maxlength="10" type="text" style="width: 100%;" value="${mpL.REWARD_ID}"></td>
							<td><input class="form-control" name="REWARD_QTY" type="text" style="width: 100%;" value="${mpL.REWARD_QTY}"></td>
							<td><input class="form-control" name="ESTIMATE_AMT" type="text" style="width: 100%;" value="${mpL.ESTIMATE_AMT}"></td>
							<td><select id="" class="" name="REWARD_RESN">
									<c:forEach items="${rewardResn}" var="data">
										<c:choose>
											<c:when test="${data.key eq mpL.REWARD_RESN}">
												<option value="${data.key}" selected="selected">${data.value}</option>
											</c:when>
											<c:otherwise>
												<option value="${data.key}">${data.value}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach></select></td>
							<td><input class="form-control" name="REWARD_DESC"  maxlength="500" type="text" style="width: 100%;" value="${mpL.REWARD_DESC}"></td>
						</tr>
					</c:if>
				</c:forEach>
			</tbody>
		</table>
	</fieldset>
</div>
<!-- 回饋手機   END -->


<script type="text/javascript">
	$(document).ready(function() {
		LoadSelect2Script(
				'<s:url value="/resources/plugins/select2/select2.min.js" />',rewardSelect2);		
		
		addClassSEL("phoneNumFBTB");
		addClassSEL("mobPhoneFBTB");
		
		var lsType = '${lease.LS_TYPE}'
			if (btnType == "view" || lsType == "1") {
			$("#mainTabs-3 :input").prop("disabled", true);
		}
	});
	
	function rewardSelect2() {
		$("select").select2();
	}
	
	//新增門號
	function addPhoneNumRow(tbodyID){
		var row='<tr><td><input class="form-control" name="REWARD_ID" class="REWARD_ID" maxlength="10" type="text" style="width: 100%;"></td>';
		row +='<td><input class="form-control" name="CUST_NAME" maxlength="50" type="text" style="width: 100%;"></td>';
		row +='<td><input class="form-control" name="PHONE_NBR" maxlength="20" type="text" style="width: 100%;"></td>';
		row +='<td><input class="form-control" name="PRCING" maxlength="100" type="text" style="width: 100%;"></td>';
		row +='<td><select id="" class="" name="REWARD_RESN">';
		row +='<c:forEach items="${rewardResn}" var="data"><option value="${data.key}">${data.value}</option></c:forEach>';
		row+='</select></td>';
		row +='<td><input class="form-control" name="REWARD_DESC" maxlength="500" type="text" style="width: 100%;"></td></tr>';
		$("#"+tbodyID).append(row);
		LoadSelect2Script(
				'<s:url value="/resources/plugins/select2/select2.min.js" />',rewardSelect2);	
	}
		
	//新增手機
	function addMobPhoneRow(tbodyID){
		var row='<tr><td><input class="form-control" name="REWARD_ID" class="REWARD_ID" maxlength="10" type="text" style="width: 100%;"></td>';
		row +='<td><input class="form-control" name="REWARD_QTY" type="text" value="1" style="width: 100%;"></td>';
		row +='<td><input class="form-control" name="ESTIMATE_AMT" type="text" style="width: 100%;"></td>';
		row +='<td><select id="" class="" name="REWARD_RESN">';
		row +='<c:forEach items="${rewardResn}" var="data"><option value="${data.key}">${data.value}</option></c:forEach>';
		row+='</select></td>';
		row +='<td><input class="form-control" name="REWARD_DESC" maxlength="500" type="text" style="width: 100%;"></td></tr>';
		$("#"+tbodyID).append(row);
		LoadSelect2Script(
				'<s:url value="/resources/plugins/select2/select2.min.js" />',rewardSelect2);
	}
	
	//刪除回饋資訊
	function toDelReward(tbId){
		var selRows = $("#" + tbId + " tr.selected").length;
		if (selRows == 0) {
			alert("請選擇要刪除的資料列");
			return;
		} 
		if (confirm("確認刪除資料?")) {
			delRow(tbId);
		}		
	}
	
	//檢查回饋門號
	function checkPhoneNumTbody() {
		var rowCount = $('#phoneNumFBTbody tr').length;
		var valid = true;
		if (rowCount > 0) {
			$("#phoneNumFBTbody tr input[name='REWARD_ID']").each(function(){
				if($.trim($(this).val()) == "") {
					$("#mainTabs a[href='#mainTabs-3']").trigger('click');
					alert('回饋門號: 申辦證號不得為空!');
					valid = false;
					$(this).focus();
					return false;
				}
			});
			
			if (!valid) {
				return valid;
			}
			
			$("#phoneNumFBTbody tr input[name='CUST_NAME']").each(function(){
				if($.trim($(this).val()) == "") {
					$("#mainTabs a[href='#mainTabs-3']").trigger('click');
					alert('回饋門號: 姓名不得為空!');
					valid = false;
					$(this).focus();
					return false;
				}
			});
			
			if (!valid) {
				return valid;
			}
			
			$("#phoneNumFBTbody tr input[name='PHONE_NBR']").each(function(){
				if($.trim($(this).val()) == "") {
					$("#mainTabs a[href='#mainTabs-3']").trigger('click');
					alert('回饋門號: 門號不得為空!');
					valid = false;
					$(this).focus();
					return false;
				}
			});
			
			if (!valid) {
				return valid;
			}
			
			$("#phoneNumFBTbody tr input[name='REWARD_DESC']").each(function(){
				var rewardResn = $(this).closest("tr").find("select").find("option:selected").prop("value");
				if($.trim($(this).val()) == "" && rewardResn == "O") {
					$("#mainTabs a[href='#mainTabs-3']").trigger('click');
					alert('回饋門號: 回饋說明不得為空!');
					valid = false;
					$(this).focus();
					return false;
				}
			});
			
			if (!valid) {
				return valid;
			}
			
		}
		
		return valid;
	}
	
	//檢查回饋手機
	function checkMobileTbody() {
		var rowCount = $('#mobPhoneFBTbody tr').length;
		var valid = true;
		if (rowCount > 0) {
			$("#mobPhoneFBTbody tr input[name='REWARD_ID']").each(function(){
				if($.trim($(this).val()) == "") {
					$("#mainTabs a[href='#mainTabs-3']").trigger('click');
					alert('回饋手機: 手機型號不得為空!');
					valid = false;
					$(this).focus();
					return false;
				}
			});
			
			if (!valid) {
				return valid;
			}
			
			$("#mobPhoneFBTbody tr input[name='REWARD_QTY']").each(function(){
				if($.trim($(this).val()) == "") {
					$("#mainTabs a[href='#mainTabs-3']").trigger('click');
					alert('回饋手機: 數量不得為空!');
					valid = false;
					$(this).focus();
					return false;
				} else {
					if(!gIsDigit($.trim($(this).val()))) {
						$("#mainTabs a[href='#mainTabs-3']").trigger('click');
						alert('回饋手機: 數量格式錯誤!');
						valid = false;
						$(this).focus();
						return false;
					}
				}
			});
			if (!valid) {
				return valid;
			}
			
			$("#mobPhoneFBTbody tr input[name='ESTIMATE_AMT']").each(function(){
				if($.trim($(this).val()) == "") {
					$("#mainTabs a[href='#mainTabs-3']").trigger('click');
					alert('回饋手機: 預估金額不得為空!');
					valid = false;
					$(this).focus();
					return false;
				} else {
					if(!gIsDigit($.trim($(this).val()))) {
						$("#mainTabs a[href='#mainTabs-3']").trigger('click');
						alert('回饋手機: 預估金額格式錯誤!');
						valid = false;
						$(this).focus();
						return false;
					}
				}
			});
			if (!valid) {
				return valid;
			}
			

			$("#mobPhoneFBTbody tr input[name='REWARD_DESC']").each(function(){
				var rewardResn = $(this).closest("tr").find("select").find("option:selected").prop("value");
				if($.trim($(this).val()) == "" && rewardResn == "O") {
					$("#mainTabs a[href='#mainTabs-3']").trigger('click');
					alert('回饋手機: 回饋說明不得為空!');
					valid = false;
					$(this).focus();
					return false;
				}
			});
			
			if (!valid) {
				return valid;
			}
		}
		return valid;
	}
	
</script>