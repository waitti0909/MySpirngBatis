<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>用電度數輸入-修改</title>
<script src="<s:url value="/resources/plugins/tinymce/tinymce.min.js" />"></script>
<script src="<s:url value="/resources/plugins/tinymce/jquery.tinymce.min.js" />"></script>
<script	src="<s:url value="/resources/js/validate.js" />"></script>
<script type="text/javascript">
	// 初始載入
	$(document).ready(function() {
		
		
		var electricityType = $("#electricityType").val();
		var paymentReqAmt = $("#paymentReqAmtEdit").val();
		// 判斷欄位是否可修改，依用電方式區分
		if (electricityType == "ELE06"){// 預付: 只有請款金額可以修改
			$("#contractNoEdit").prop("disabled", "disabled");
			$("#electricityMeterNbrEdit").prop("disabled", "disabled");
			$("#contractNameEdit").prop("disabled", "disabled");
			$("#electricityDscrEdit").prop("disabled", "disabled");
			$("#lsSDateEdit").prop("disabled", "disabled");
			$("#paymentReqBeginDateEdit").prop("disabled", "disabled");
			$("#paymentReqEndDateEdit").prop("disabled", "disabled");
			$("#usedDegreeDayEdit").prop("disabled", "disabled");
			$("#lastUsedDegreeEdit").prop("disabled", "disabled");
			$("#totalUsedDegreeEdit").prop("disabled", "disabled");
			$("#usedDegreeEdit").prop("disabled", "disabled");
			$("#ratioEdit").prop("disabled", "disabled");
			$("#memoEdit").prop("disabled", "disabled");
		} else if(electricityType == "ELE02"){// 用電方式為借電_抄表時,請款金額=本次用電度數*租約每度金額(此時請款金額不可修改)
			$("#contractNoEdit").prop("disabled", "disabled");
			$("#electricityMeterNbrEdit").prop("disabled", "disabled");
			$("#contractNameEdit").prop("disabled", "disabled");
			$("#electricityDscrEdit").prop("disabled", "disabled");
			$("#lsSDateEdit").prop("disabled", "disabled");
			$("#usedDegreeDayEdit").prop("disabled", "disabled");
			$("#lastUsedDegreeEdit").prop("disabled", "disabled");
			$("#ratioEdit").prop("disabled", "disabled");
			$("#paymentReqAmtEdit").prop("disabled", "disabled");
		} else {// 請電/借電_其他: 請款日起迄、本次度數、本次用電數、請款金額、說明可以修改
			$("#contractNoEdit").prop("disabled", "disabled");
			$("#electricityMeterNbrEdit").prop("disabled", "disabled");
			$("#contractNameEdit").prop("disabled", "disabled");
			$("#electricityDscrEdit").prop("disabled", "disabled");
			$("#lsSDateEdit").prop("disabled", "disabled");
			$("#usedDegreeDayEdit").prop("disabled", "disabled");
			$("#lastUsedDegreeEdit").prop("disabled", "disabled");
			$("#ratioEdit").prop("disabled", "disabled");
		}
		// 用電方式為請電,請款金額不可修改
		if (electricityType == "ELE04"){
			$("#paymentReqAmtEdit").prop("disabled", "disabled");
			$("#totalUsedDegreeEdit").prop("disabled", "disabled");
			$("#usedDegreeEdit").prop("disabled", "disabled");
		}
		editMountButEvent();
		// 本次請款起迄日曆
		$('#lsSDateEdit').datepicker();
		$('#paymentReqBeginDateEdit').datepicker();
		$('#paymentReqEndDateEdit').datepicker();
	});
	
	// 掛載表格Event
	function editMountButEvent() {
		// 儲存
		$('#saveEditBtn').click(function() {
			if (editCheck()) {
				isEdit = true;// 控制頁面的關閉視窗提醒訊息
				updateData();
			}
		});

		// 取消
		$('#resetBtn').click(function() {
			$("#bBoardEdit").trigger('reset');
		});
	}
	$("#paymentReqBeginDateEdit").change(onPaymentReqBeginDateEditChange);
	$("#paymentReqEndDateEdit").change(onPaymentReqEndDateEditChange);
	$("#usedDegreeEdit").change(onUsedDegreeEditChange);
	$("#totalUsedDegreeEdit").change(onTotalUsedDegreeEditChange);
	//================FUNCTION======================
	// 當日期有改變時清空本次用電度數及請款金額,避免請款金額計算錯誤
	function 	onPaymentReqBeginDateEditChange(){
		var electricityType = $("#electricityType").val();
		if (electricityType == "ELE02"){
			$("#usedDegreeEdit").val("");
			$("#paymentReqAmtEdit").val("");
		}
	}
	// 當日期有改變時清空本次用電度數及請款金額,避免請款金額計算錯誤
	function 	onPaymentReqEndDateEditChange(){
		var electricityType = $("#electricityType").val();
		if (electricityType == "ELE02"){
			$("#usedDegreeEdit").val("");
			$("#paymentReqAmtEdit").val("");
		}
	}	
	
	// 本次用電度數修改控制
	function onUsedDegreeEditChange(){
		var electricityType = $("#electricityType").val();
		var usedDegree = $("#usedDegreeEdit").val();
		var countAmt = 0;
		if(usedDegree !="" && !isAllNumber(usedDegree)){
			alert("本次用電度數只能輸入數字");
			$("#usedDegreeEdit").val("");
			$("#paymentReqAmtEdit").val("");
			return false;
		}
		if (electricityType == "ELE02"){
			if (usedDegree == ""){
				alert("本次用電度數不可為空值! ");
				$("#paymentReqAmtEdit").val("");
			} else {
			     // disable請款金額欄位並計算請款金額
			     countAmt = getPaymentReqAmt();
			     $("#paymentReqAmtEdit").val(countAmt);
			}
		}
	}
	
	// 本次度數修改控制
	function onTotalUsedDegreeEditChange(){
		var electricityType = $("#electricityType").val();
		var totalUsedDegree = $("#totalUsedDegreeEdit").val();
		var countAmt = 0;
		if(totalUsedDegree !="" && !isAllNumber(totalUsedDegree)){
			alert("本次度數只能輸入數字");
			$("#totalUsedDegreeEdit").val("");
			$("#paymentReqAmtEdit").val("");
			return false;
		}
		if (electricityType == "ELE02"){
			if (totalUsedDegree == ""){
				alert("本次用電度數不可為空值! ");
				$("#paymentReqAmtEdit").val("");
			} else {
			     // disable請款金額欄位並計算請款金額
			     countAmt = getPaymentReqAmt();
			     $("#paymentReqAmtEdit").val(countAmt);
			}
		}
	}
	
	// 取得計算後請款金額
	function getPaymentReqAmt() {
		var data = {
				contractNoVal : $("#contractNoEdit").val(),
				electricityMeterNbrVal : $("#electricityMeterNbrEdit").val(),
				paymentReqBeginDateVal : $("#paymentReqBeginDateEdit").val(),
				paymentReqEndDateVal : $("#paymentReqEndDateEdit").val(),
				usedDegreeVal : $("#usedDegreeEdit").val(),
				totalUsedDegreeVal: $("#totalUsedDegreeEdit").val()
		};
		var countAmt = "";
		$.ajax({
			url : CONTEXT_URL + "/pay/pay002/getPaymentReqAmt/",
			data : data,
			type : "POST",
			dataType : 'json',
			async: false,
			success : function(data) {
				if (data.success) {
					countAmt = data.msg
				}
			}
		});
		return countAmt;
	}
	
    // 檢查修改欄位
    function editCheck(){
    	var paymentReqBeginDate= $("#paymentReqBeginDateEdit").val();
    	var paymentReqEndDate = $("#paymentReqEndDateEdit").val();
    	var useElectricityRatio = $("#useElectricityRatio").val();
		var usedDegreeDay = $("#usedDegreeDayEdit").val();
		var totalUsedDegree = $("#totalUsedDegreeEdit").val();
		var usedDegree = $("#usedDegreeEdit").val();
		var paymentReqAmt = $("#paymentReqAmtEdit").val();
		var memo = $("#memoEdit").val();
		var electricityType = $("#electricityType").val();
		// 輸入欄位檢查
		if (electricityType != "ELE06"){
			// 必須輸入欄位檢查
			if(paymentReqBeginDate ==""){
				alert("本次請款起始日不可為空值!");
				return false;
			}
			if(paymentReqEndDate ==""){
				alert("本次請款結束日不可為空值!");
				return false;
			}
			if(totalUsedDegree ==""){
				alert("本次度數不可為空值!");
				return false;
			}
			if(usedDegree ==""){
				alert("本次用電度數不可為空值!");
				return false;
			}
			// 取得用電補充說明是否需輸入的判斷依據(本次用電度數-平均用電數/日)/平均用電數/日
			var countAvgVal = (usedDegree-usedDegreeDay)/usedDegreeDay;
			// 若(本次用電度數-平均用電數/日)/平均用電數/日>設定用電比,則用電補充說明為必要欄位
			if(countAvgVal > useElectricityRatio && memo ==""){
				alert("用電補充說明一定要輸入!");
				return false;
			}
			// 輸入數字檢查
			if(totalUsedDegree !="" && !isAllNumber(totalUsedDegree)){
				alert("本次度數只能輸入數字");
				return false;
			}
			if(usedDegree !="" && !isAllNumber(usedDegree)){
				alert("本次用電度數只能輸入數字");
				return false;
			}
		}
		if (electricityType != "ELE04"){
			if(paymentReqAmt ==""){
				alert("請款/預付金額不可為空值!");
				return false;
			}
		}
		if(paymentReqAmt !="" && isNaN(paymentReqAmt)){
			alert("請款/預付金額只能輸入數字");
			return false;
		}
		return true;
	}
	
	// 更新資料
	function updateData(){
		var data = {
				seqNbrEdit : $("#seqNbrEdit").val(),
				totalUsedDegreeEdit : $("#totalUsedDegreeEdit").val(),
				usedDegreeEdit : $("#usedDegreeEdit").val(),
				paymentReqAmtEdit : $("#paymentReqAmtEdit").val(),
				memoEdit : $("#memoEdit").val(),
				paymentReqBeginDateEdit : $("#paymentReqBeginDateEdit").val(),
				paymentReqEndDateEdit : $("#paymentReqEndDateEdit").val(),
				ifAutoDeductSelectEdit : $("#ifAutoDeductSelectEdit").find("option:selected").val(),
				ifNoSiteMappingSelectEdit : $("#ifNoSiteMappingSelectEdit").find("option:selected").val()
		};
		$.ajax({
				url : CONTEXT_URL + "/pay/pay002/updateData/",
				data : data,
				type : "POST",
				dataType : 'json',
				success : function(data) {
					alert("租約編號"+ $("#contractNoEdit").val() +
						    ",電表號碼"+ $("#electricityMeterNbrEdit").val() + "變更儲存成功!");
				    parent.$.fancybox.close();// 關閉視窗
			    },
			    error : function(data) {
				alert(data.msg);
			    }
		});
	}
</script>
</head>

<body>
	<div class="row">
		<div id="breadcrumb" class="col-md-12">
			<ol class="breadcrumb">
				<div class="col-sm-12" id="btnDiv">
					<button class="btn btn-primary btn-label-left" type="button"
						id="saveEditBtn">
						<span><i class="fa fa-save"></i></span> 儲存
					</button>
					<button class="btn btn-primary btn-label-left" type="button"
						id="resetBtn">取消</button>
				</div>
			</ol>
		</div>
	</div>
	<div class="box-header">
		<div class="box-name">
			<i class="fa fa-edit"></i> <span>用電度數輸入-修改</span>
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
					<form class="form-horizontal" id="bBoardEdit">
						<div class="form-group">
							<div class="row">
								<div class="col-md-12">
									<label class="col-sm-12 control-label">
										<table border=2 style="table-layout: fixed; word-break: break-all; word-wrap: break-word; width: 99%;">
											<tr>
											    <td class="titleTD" style="width: 15%">租約編號</td>
												<td class="titleTD" style="width: 15%">電錶號碼</td>
												<td class="titleTD" style="width: 15%">租約名稱</td>
												<td class="titleTD" style="width: 13%">用電/預付方式</td>
												<td class="titleTD" style="width: 12%">起帳日</td>
												<td class="titleTD" style="width: 12%">本次請款起始日</td>
												<td class="titleTD" style="width: 12%">本次請款結束日</td>
											</tr>
											<tr>
												<td class="textTD">
													<!-- 隱藏欄位 start-->
													<input type="hidden" id="seqNbrEdit" name="seqNbrEdit"  value="${seqNbrEdit}"/>
													<input type="hidden" id="useElectricityRatio" name="useElectricityRatio"  value="${useElectricityRatio}"/>
													<input type="hidden" id="electricityType" name="electricityType"  value="${electricityType}"/>
													<!-- 隱藏欄位 end-->
													<input id="contractNoEdit" type="text" class="form-control" 
														name="contractNoEdit" value="${contractNoEdit}">
												</td>
												<td class="textTD">
													<input id="electricityMeterNbrEdit" type="text" class="form-control" 
														name="electricityMeterNbrEdit" value="${electricityMeterNbrEdit}">
												</td>
												<td class="textTD">
													<input id="contractNameEdit" type="text" class="form-control"
														name="contractNameEdit" value="${contractNameEdit}">
												</td>
												<td class="textTD">
													<input id="electricityDscrEdit" type="text" class="form-control" 
														name="electricityDscrEdit" value="${electricityDscrEdit}">
												</td>
												<td class="textTD">
													<input id="lsSDateEdit" type="text"  value="<fmt:formatDate value="${lsSDateEdit}" pattern="yyyy/MM/dd" />"
														class="form-control" name="lsSDateEdit" readonly="readonly">
												</td>
												<td class="textTD">
													<input id="paymentReqBeginDateEdit" type="text" value="<fmt:formatDate value="${paymentReqBeginDateEdit}" pattern="yyyy/MM/dd" />" 
														class="form-control" name="paymentReqBeginDateEdit"  readonly="readonly">
												</td>
												<td class="textTD">
													<input id="paymentReqEndDateEdit" type="text" value="<fmt:formatDate value="${paymentReqEndDateEdit}" pattern="yyyy/MM/dd" />" 
														 class="form-control" name="paymentReqEndDateEdit"  readonly="readonly">
												</td>
											</tr>
											<tr><td class="titleTD">平均用電數/日</td>
												<td class="titleTD">上期度數</td>
												<td class="titleTD">本次度數</td>
												<td class="titleTD">本次用電度數</td>
												<td class="titleTD">超出/減少比率</td>
												<td COLSPAN=2 class="titleTD">用電補充說明</td>
											</tr>
											<tr>
												<td class="textTD">
													<input id="usedDegreeDayEdit" type="text" class="form-control" 
														name="usedDegreeDayEdit" value="${usedDegreeDayEdit}">
												</td>
												<td class="textTD">
													<input id="lastUsedDegreeEdit" type="text" class="form-control" 
														name="lastUsedDegreeEdit" value="${lastUsedDegreeEdit}">
												</td>
												<td class="textTD">
													<input id="totalUsedDegreeEdit" type="text" class="form-control"
														name="totalUsedDegreeEdit" value="${totalUsedDegreeEdit}">
												</td>
												<td class="textTD">
													<input id="usedDegreeEdit" type="text" class="form-control" 
														name="usedDegreeEdit" value="${usedDegreeEdit}">
												</td>
												<td class="textTD">
													<input id="ratioEdit" type="text" class="form-control" 
														name="ratioEdit" value="${ratioEdit}">
												</td>
												<td COLSPAN=2 class="textTD">
													<input id="memoEdit" type="text" class="form-control" 
														name="memoEdit" value="${memoEdit}">
												</td>
											</tr>
											<tr>
											    <td class="titleTD">請款/預付金額</td>
												<td class="titleTD">自動扣款</td>
												<td COLSPAN=5 class="titleTD">無站台對應</td>
											</tr>
											<tr>
												<td class="textTD">
													<input id="paymentReqAmtEdit" type="text" class="form-control" 
														name="paymentReqAmtEdit" value="${paymentReqAmtEdit}">
												</td>
												<td class="textTD">
													<select id="ifAutoDeductSelectEdit" name="ifAutoDeductSelectEdit" class="form-control">
													   <option value="Y" <c:if test="${ifAutoDeductEdit eq 'Y'}">selected</c:if>>是</option>
													   <option value="N" <c:if test="${ifAutoDeductEdit eq 'N'}">selected</c:if>>否</option>
												    </select>
												</td>
												<td COLSPAN=5 class="textTD">
													<select id="ifNoSiteMappingSelectEdit" name="ifNoSiteMappingSelectEdit" class="form-control">
													   <option value="Y" <c:if test="${ifNoSiteMappingEdit eq 'Y'}">selected</c:if>>無站台對應</option>
													   <option value="N" <c:if test="${ifNoSiteMappingEdit eq 'N'}">selected</c:if>>有站台對應</option>
												    </select>	
												</td>
											</tr>
										</table>
									</label>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>