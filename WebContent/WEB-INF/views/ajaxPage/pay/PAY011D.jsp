<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>電費分攤明細表-明細</title>
<script
	src="<s:url value="/resources/plugins/tinymce/tinymce.min.js" />"></script>
<script
	src="<s:url value="/resources/plugins/tinymce/jquery.tinymce.min.js" />"></script>
<script src="<s:url value="/resources/js/validate.js" />"></script>
<script type="text/javascript">
	$(document).ready(function() {

	});
</script>
</head>

<body>
	<div class="row"></div>
	<div class="box-header">
		<div class="box-name">
			<i class="fa fa-edit"></i> <span>用電清單-明細</span>
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
				   <form class="form-horizontal" id="bBoardAddEdit">
					<div class="form-group">
						<div class="row">
							<div class="col-md-12">
								<table style="width: 100%">
									<tr>
										<td><label class="col-sm-12 control-label">使用類別：</label></td>
										<td>
										     <div class="col-sm-6">
												<input id="useTypeDscr" type="text" disabled
													class="form-control" name="useTypeDscr"
													value="${useTypeDscr}">
											  </div>
										</td>
										<td><label class="col-sm-12 control-label">使用年月：</label></td>
										<td>
										     <div class="col-sm-6">
												<input id="yearMonth" type="text" disabled
													class="form-control" name="yearMonth"
													value="${yearMonth}">
											  </div>
										</td>
									</tr>
									<tr>
										<td><div class="clearfix">&nbsp;</div></td>
									</tr>
									<tr>
										<td><label class="col-sm-12 control-label">請款單號：</label></td>
										<td>
										     <div class="col-sm-6">
												<input id="cashReqNo" type="text" disabled
													class="form-control" name="cashReqNo"
													value="${cashReqNo}">
											  </div>
										</td>
										<td><label class="col-sm-12 control-label">租約編號：</label></td>
										<td>
										     <div class="col-sm-6">
												<input id="contractNo" type="text" disabled
													class="form-control" name="contractNo"
													value="${contractNo}">
											  </div>
										</td>
									</tr>
									<tr>
										<td><div class="clearfix">&nbsp;</div></td>
									</tr>
									<tr>
										<td><label class="col-sm-12 control-label">電錶號碼：</label></td>
										<td>
										     <div class="col-sm-6">
												<input id="electricityMeterNbr" type="text" disabled
													class="form-control" name="electricityMeterNbr"
													value="${electricityMeterNbr}">
											  </div>
										</td>
										<td><label class="col-sm-12 control-label">租約(名稱)：</label></td>
										<td>
										     <div class="col-sm-6">
												<input id="contractName" type="text" disabled
													class="form-control" name="contractName"
													value="${contractName}">
											  </div>
										</td>
									</tr>
									<tr>
										<td><div class="clearfix">&nbsp;</div></td>
									</tr>
									<tr>
										<td><label class="col-sm-12 control-label">用電方式：</label></td>
										<td>
										     <div class="col-sm-6">
												<input id="electricityDscr" type="text" disabled
													class="form-control" name="electricityDscr"
													value="${electricityDscr}">
											  </div>
										</td>
										<td><label class="col-sm-12 control-label">基站編號：</label></td>
										<td>
										     <div class="col-sm-6">
												<input id="siteName" type="text" disabled
													class="form-control" name="siteName"
													value="${siteName}">
											  </div>
										</td>
									</tr>
									<tr>
										<td><div class="clearfix">&nbsp;</div></td>
									</tr>
									<tr>
										<td><label class="col-sm-12 control-label">請款期間起始：</label></td>
										<td>
										     <div class="col-sm-6">
												<input id="paymentReqBeginDate" type="text" disabled
													class="form-control" name="paymentReqBeginDate"
													value="<fmt:formatDate  value="${paymentReqBeginDate}"  pattern="yyyy/MM/dd"  />">
											  </div>
										</td>
										<td><label class="col-sm-12 control-label">請款期間結束：</label></td>
										<td>
										     <div class="col-sm-6">
												<input id="paymentReqEndDate" type="text" disabled
													class="form-control" name="paymentReqEndDate"
													value="<fmt:formatDate  value="${paymentReqEndDate}"  pattern="yyyy/MM/dd"  />">
											  </div>
										</td>
									</tr>
									<tr>
										<td><div class="clearfix">&nbsp;</div></td>
									</tr>
									<tr>
										<td><label class="col-sm-12 control-label">本次用電數：</label></td>
										<td>
										     <div class="col-sm-6">
												<input id="totalUsedDegree" type="text" disabled
													class="form-control" name="totalUsedDegree"
													value="${totalUsedDegree}">
											  </div>
										</td>
										<td><label class="col-sm-12 control-label">本期使用度數：</label></td>
										<td>
										     <div class="col-sm-6">
												<input id="usedDegree" type="text" disabled
													class="form-control" name="usedDegree"
													value="${usedDegree}">
											  </div>
										</td>
									</tr>
									<tr>
										<td><div class="clearfix">&nbsp;</div></td>
									</tr>
									<tr>
										<td><label class="col-sm-12 control-label">請款金額：</label></td>
										<td>
										     <div class="col-sm-6">
												<input id="paymentReqAmt" type="text" disabled
													class="form-control" name="paymentReqAmt"
													value="${paymentReqAmt}">
											  </div>
										</td>
										<td><label class="col-sm-12 control-label">異常用電說明：</label></td>
										<td>
										     <div class="col-sm-6">
												<input id="memo" type="text" disabled
													class="form-control" name="memo"
													value="${memo}">
											  </div>
										</td>
									</tr>
									<tr>
										<td><div class="clearfix">&nbsp;</div></td>
									</tr>
									<tr>
										<td><label class="col-sm-12 control-label">是否自動扣款：</label></td>
										<td>
										     <div class="col-sm-6">
												<input id="ifAutoDeductDscr" type="text" disabled
													class="form-control" name="ifAutoDeductDscr"
													value="${ifAutoDeductDscr}">
											  </div>
										</td>
										<td><label class="col-sm-12 control-label">無站台對應：</label></td>
										<td>
										     <div class="col-sm-6">
												<input id="ifNoSiteMappingDscr" type="text" disabled
													class="form-control" name="ifNoSiteMappingDscr"
													value="${ifNoSiteMappingDscr}">
											  </div>
										</td>
									</tr>
									<tr>
										<td><div class="clearfix">&nbsp;</div></td>
									</tr>
								</table>
							</div>
						</div>
						<hr>
					</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>