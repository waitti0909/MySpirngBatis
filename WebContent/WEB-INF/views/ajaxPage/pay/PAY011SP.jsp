<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>電費分攤明細表-列印</title>
<script
	src="<s:url value="/resources/plugins/tinymce/tinymce.min.js" />"></script>
<script
	src="<s:url value="/resources/plugins/tinymce/jquery.tinymce.min.js" />"></script>
<script src="<s:url value="/resources/js/validate.js" />"></script>
<script type="text/javascript">
	$(document).ready(function() {
		LoadSelect2Script("<s:url value="/resources/plugins/select2/select2.min.js" />", selectDisDefault);	
	});
	
	function selectDisDefault() {
		$("#spSiteIdSelect").select2();
	}
	
	// 匯出excel
	$("#downloadBtn").on('click', function(e) {
		var spContractNo = $("#spContractNo").val();
		var spPaymentReqNo = $("#spPaymentReqNo").val();
		var spAppDate = $("#spAppDate").val();
		var spElectricityMeterNbr = $("#spElectricityMeterNbr").val();
		var spSiteId = $("#spSiteIdSelect").find("option:selected").val();
		var spSiteIdDscr = $("#spSiteIdSelect").find("option:selected").text();
		if (spSiteId == "" || spSiteId == '') {
			alert("需先選擇基站編號,請重新操作!");
			return false;
		};
		// 產EXCEL
		expExcel(spContractNo,spPaymentReqNo,spAppDate,spElectricityMeterNbr,spSiteId,spSiteIdDscr);
	});
	
	// 產EXCEL
	function expExcel(spContractNo,spPaymentReqNo,spAppDate,spElectricityMeterNbr,spSiteId,spSiteIdDscr) {
		data ={
				spContractNo : spContractNo,
				spPaymentReqNo : spPaymentReqNo,
				spAppDate : spAppDate,
				spElectricityMeterNbr : spElectricityMeterNbr,
				spSiteId : spSiteId
		};
		$.ajax({
			url : CONTEXT_URL + "/pay/pay011/getExcelData",
			data : data,
			type : "GET",
			dataType : 'json',
			async:false,
			success : function(data) {
				if(!data.success){
					alert(data.msg);// 查無資料
				} else {
                    // 執行產EXCEL檔
					window.location.href = CONTEXT_URL
					+ "/pay/pay011/genExcel?spElectricityMeterNbr="
					+ spElectricityMeterNbr + "&spSiteIdDscr=" + spSiteIdDscr;
				}
			},
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
						id="downloadBtn">列印</button>
				</div>
			</ol>
		</div>
	</div>
	<div class="box-header">
		<div class="box-name">
			<i class="fa fa-edit"></i> <span>用電清單-列印</span>
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
										<td><label class="col-sm-12 control-label">電錶號碼：</label></td>
										<td>
										      <input type="hidden" id="spContractNo" name="spContractNo"  value="${spContractNo}"/>
										      <input type="hidden" id="spPaymentReqNo" name="spPaymentReqNo"  value="${spPaymentReqNo}"/>
										      <input type="hidden" id="spAppDate" name="spAppDate"  value="${spAppDate}"/>
										     <div class="col-sm-3">
												<input id="spElectricityMeterNbr" type="text" disabled
													class="form-control" name="spElectricityMeterNbr"
													value="${spElectricityMeterNbr}">
											  </div>
										</td>
									</tr>
									<tr>
										<td><div class="clearfix">&nbsp;</div></td>
									</tr>
									<tr>
										<td><label class="col-sm-12 control-label">基站編號：</label></td>
										<td>
											<div class="col-sm-3">
												<select id="spSiteIdSelect" name="spSiteIdSelect">
													<option value="" selected>--請選擇--</option>
														<c:forEach items="${spSiteIdSelect}" var="spSiteId">
															<option value="${spSiteId.siteId}">${spSiteId.siteName}</option>
														</c:forEach>
												</select>
											</div>
										</td>
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