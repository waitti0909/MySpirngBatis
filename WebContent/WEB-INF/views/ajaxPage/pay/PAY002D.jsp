<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>用電/預付明細</title>
<script src="<s:url value="/resources/plugins/tinymce/tinymce.min.js" />"></script>
<script src="<s:url value="/resources/plugins/tinymce/jquery.tinymce.min.js" />"></script>
<script	src="<s:url value="/resources/js/validate.js" />"></script>
<script type="text/javascript">
	// 初始載入
	$(document).ready(function() {
	});
	//================FUNCTION======================

</script>
</head>

<body>
	<div class="row">
		<div id="breadcrumb" class="col-md-12">
			<ol class="breadcrumb">
			</ol>
		</div>
	</div>
	<div class="box-header">
		<div class="box-name">
			<i class="fa fa-edit"></i> <span>用電/預付明細</span>
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
													<input id="contractNoDtl" type="text" class="form-control" 
														name="contractNoDtl" value="${contractNoDtl}" disabled>
												</td>
												<td class="textTD">
													<input id="electricityMeterNbrDtl" type="text" class="form-control" 
														name="electricityMeterNbrDtl" value="${electricityMeterNbrDtl}" disabled>
												</td>
												<td class="textTD">
													<input id="contractNameDtl" type="text" class="form-control"
														name="contractNameDtl" value="${contractNameDtl}" disabled>
												</td>
												<td class="textTD">
													<input id="electricityDscrDtl" type="text" class="form-control" 
														name="electricityDscrDtl" value="${electricityDscrDtl}" disabled>
												</td>
												<td class="textTD">
													<input id="lsSDateDtl" type="text"  value="<fmt:formatDate value="${lsSDateDtl}" pattern="yyyy/MM/dd" />"
														class="form-control" name="lsSDateDtl" readonly="readonly" disabled>
												</td>
												<td class="textTD">
													<input id="paymentReqBeginDateDtl" type="text" value="<fmt:formatDate value="${paymentReqBeginDateDtl}" pattern="yyyy/MM/dd" />" 
														class="form-control" name="paymentReqBeginDateDtl"  readonly="readonly" disabled>
												</td>
												<td class="textTD">
													<input id="paymentReqEndDateDtl" type="text" value="<fmt:formatDate value="${paymentReqEndDateDtl}" pattern="yyyy/MM/dd" />" 
														 class="form-control" name="paymentReqEndDateDtl"  readonly="readonly" disabled>
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
													<input id="usedDegreeDayDtl" type="text" class="form-control" 
														name="usedDegreeDayDtl" value="${usedDegreeDayDtl}" disabled>
												</td>
												<td class="textTD">
													<input id="lastUsedDegreeDtl" type="text" class="form-control" 
														name="lastUsedDegreeDtl" value="${lastUsedDegreeDtl}" disabled>
												</td>
												<td class="textTD">
													<input id="totalUsedDegreeDtl" type="text" class="form-control"
														name="totalUsedDegreeDtl" value="${totalUsedDegreeDtl}" disabled>
												</td>
												<td class="textTD">
													<input id="usedDegreeDtl" type="text" class="form-control" 
														name="usedDegreeDtl" value="${usedDegreeDtl}" disabled>
												</td>
												<td class="textTD">
													<input id="ratioDtl" type="text" class="form-control" 
														name="ratioDtl" value="${ratioDtl}" disabled>
												</td>
												<td COLSPAN=2 class="textTD">
													<input id="memoDtl" type="text" class="form-control" 
														name="memoDtl" value="${memoDtl}" disabled>
												</td>
											</tr>
											<tr>
											    <td class="titleTD">請款/預付金額</td>
												<td class="titleTD">自動扣款</td>
												<td COLSPAN=5 class="titleTD">無站台對應</td>
											</tr>
											<tr>
												<td class="textTD">
													<input id="paymentReqAmtDtl" type="text" class="form-control" 
														name="paymentReqAmtDtl" value="${paymentReqAmtDtl}" disabled>
												</td>
												<td class="textTD">
													<select id="ifAutoDeductSelectDtl" name="ifAutoDeductSelectDtl" class="form-control" disabled>
													   <option value="Y" <c:if test="${ifAutoDeductDtl eq 'Y'}">selected</c:if>>是</option>
													   <option value="N" <c:if test="${ifAutoDeductDtl eq 'N'}">selected</c:if>>否</option>
												    </select>
												</td>
												<td COLSPAN=5 class="textTD">
													<select id="ifNoSiteMappingSelectDtl" name="ifNoSiteMappingSelectDtl" class="form-control" disabled>
													   <option value="Y" <c:if test="${ifNoSiteMappingDtl eq 'Y'}">selected</c:if>>無站台對應</option>
													   <option value="N" <c:if test="${ifNoSiteMappingDtl eq 'N'}">selected</c:if>>有站台對應</option>
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