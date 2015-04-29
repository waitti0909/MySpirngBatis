<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>Insert title here</title>
<style type="text/css">
hr {
	display: block;
	margin-top: 0.5em;
	margin-bottom: 0.5em;
	margin-left: auto;
	margin-right: auto;
	border-style: inset;
	border-width: 1px;
}

.ftSolidLine {
	display: block;
	border: 1px solid #000000;
	margin-left: 2px;
	margin-right: 2px;
	padding-top: 0.35em;
	padding-bottom: 0.625em;
	padding-left: 0.75em;
	padding-right: 0.75em;
}

.legend {
	width: inherit;
	font-size: 14px;
	padding: 3px 5px;
	border-bottom: 0px dashed #B6B6B6;
	margin-bottom: 2px;
}

.selected {
  background-color: #b0bed9;
}
</style>

<script src="../resources/plugins/scrolltable/jquery.scrolltable.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
				
			
				
				$('#trmnDate').datepicker({
					dateFormat : "yy/mm/dd",
					changeYear : true,
					changeMonth : true,
					showButtonPanel : true
				});
				
				$('#trmnDate').change(function() {
					if(Date.parse($(this).val()).valueOf() > Date.parse('${E_Date}').valueOf()){
						alert('解約日不可超過承租迄日');
						$(this).val('');
					}
				}); 
				
				var phoneNoJson = JSON.parse('${phoneNoList}');
				for(var i = 0;i<phoneNoJson.length;i++){
					var phoneNoString = '<tr style="height: 25px">';
					phoneNoString += '<td>'+phoneNoJson[i]['REWARD_ID1']+'</td>';
					phoneNoString += '<td>'+phoneNoJson[i]['CUST_NAME']+'</td>';
					phoneNoString += '<td>'+phoneNoJson[i]['PHONE_NBR']+'</td>';
					phoneNoString += '<td>'+phoneNoJson[i]['PRCING']+'</td>';
					phoneNoString += '<td>'+phoneNoJson[i]['REWARD_RESN']+'</td>';
					phoneNoString += '<td>'+phoneNoJson[i]['REWARD_DESC']+'</td>';
					phoneNoString += '</tr>';
					$('#phoneNoList').append(phoneNoString);
				}
				var phoneJson = JSON.parse('${phoneList}');
				for(var i = 0;i<phoneJson.length;i++){
					var phoneString = '<tr style="height: 25px">';
					phoneString += '<td>'+phoneJson[i]['REWARD_ID2']+'</td>';
					phoneString += '<td>'+phoneJson[i]['REWARD_QTY']+'</td>';
					phoneString += '<td>'+phoneJson[i]['ESTIMATE_AMT']+'</td>';
					phoneString += '<td>'+phoneJson[i]['REWARD_RESN']+'</td>';
					phoneString += '<td>'+phoneJson[i]['REWARD_DESC']+'</td>';
					$('#phoneList').append(phoneString);
				}
				
				$("#btn_upload").click(function() {
					window.open('../common/Upload.html', '附件上傳', config = 'height=768,width=1024').focus();
				});

			
				
				$('#forecastTB').scrolltable({
					stripe : true,
					oddClass : 'odd',
					maxHeight : 80
				});
				
				$('#forecastTB table:last').addClass('table-hover');
				
				$('#save_draft').click(function() {
					
					var count = 0 ;
					if($.trim($('#trmnDate').val()) == ''){
						alert('解約日必填');
						return false;
					}
					if($.trim($('#recovDay').val()) == ''){
						alert('於租賃契約終止日後為必填，請填入正整數');
						return false;
					}
					
					$("#elecList input[name=trmnDegree]").each(function() {
						if($(this).val() == '' || isNaN($(this).val())){
							count++;
						}
					});
					if(count > 0){
						alert('用電方式 的 抄錶度數欄位有值未填寫，請填入數字');
						return false;
					}
					
					var data = $("#draftForm").serialize();
					if('${type}' == 'edit'){
						$.ajax({			
							url : CONTEXT_URL + "/ls/LS001T/updateTerminateLease",		
							data: data,
							type : "POST",
							dataType : 'json',
							success : function(data) {
								alert(data.msg);
								if(data.success){					
									parent.$.fancybox.close();
								}
							}
						}); 
					}else{
					  	$.ajax({			
							url : CONTEXT_URL + "/ls/LS001T/terminateLease",		
							data: data,
							type : "POST",
							dataType : 'json',
							success : function(data) {
								alert(data.msg);
								if(data.success){					
									parent.$.fancybox.close();
								}
							}
						});  
					}
				});
				
				var elecJson = JSON.parse('${elec}');
				if('${type}' == 'edit'){
					var editElecJson = JSON.parse('${editElec}');
					$('#trmnDate').val('${editTrmnDate}');
					$('#trmnTyepeEnum').val('${editTrmnTyepe}');
					$('#trmnResnEnum').val('${editTrmnResn}');
					$('#recovDay').val('${editRecovDay}');
					$('#trmnList').val('${editTrmnFormId}');
					$('#trmnDesc').val('${editTrmnDesc}');
					
					for(var i = 0;i<elecJson.length;i++){
						var elecString = '<tr>';
						elecString += '<td>'+elecJson[i]['ENERGY_METER']+'</td>';
						elecString += '<td>'+elecJson[i]['ELEC_MODE']+'</td>';
						elecString += '<td>'+elecJson[i]['CHRG_MODE']+'</td>';
						elecString += '<td>'+elecJson[i]['ELEC_BEGIN_DATE']+'</td>';
						elecString += '<td style="padding: 3px 5px 3px 5px"><input name="energyMeter" type="hidden" value="'+elecJson[i]['ENERGY_METER']+'"><input name="locationId" type="hidden" value="'+elecJson[i]['LOCATION_ID']+'">'
						for(var j = 0;j<editElecJson.length;j++){
							$('#elecupdList').val(editElecJson[j]['FORM_ID']);
							if(elecJson[i]['ENERGY_METER'] == editElecJson[j]['ENERGY_METER'] && elecJson[i]['LOCATION_ID'] == editElecJson[j]['LOCATION_ID']){
								elecString += '<input id="" name="trmnDegree" class="form-control" type="text" style="width: 100%;" value="'+editElecJson[i]['TRMN_DEGREE']+'">';	
							}
						}
						elecString += '</td>';
						elecString += '</tr>';
						$('#elecList').append(elecString);
					}
				}else{
					for(var i = 0;i<elecJson.length;i++){
						var elecString = '<tr>';
						elecString += '<td>'+elecJson[i]['ENERGY_METER']+'</td>';
						elecString += '<td>'+elecJson[i]['ELEC_MODE']+'</td>';
						elecString += '<td>'+elecJson[i]['CHRG_MODE']+'</td>';
						elecString += '<td>'+elecJson[i]['ELEC_BEGIN_DATE']+'</td>';
						elecString += '<td style="padding: 3px 5px 3px 5px"><input name="energyMeter" type="hidden" value="'+elecJson[i]['ENERGY_METER']+'"><input name="locationId" type="hidden" value="'+elecJson[i]['LOCATION_ID']+'"><input id="" name="trmnDegree" class="form-control" type="text" style="width: 100%;"></td>';
						elecString += '</tr>';
						$('#elecList').append(elecString);
					}
				}
				
				$("select").select2();


				// 申請Button
				$("#btn_apply").click(function(){
					$.ajax({			
						url : CONTEXT_URL + "/ls/LS001M3/ls001M3ApplyNotValidator",		
						data:{
							"appSeq" : $("input[name='appSeq']").val(),
							"lsNo"   :  $("input[name='lsNo']").val(),
							"type"   : "T"
						},
						type : "POST",
						dataType : 'json',
						success : function(data) {
							alert(data.msg);
						}
					});
				});
	});

	

	
</script>
</head>
<body>
	<div class="row">
		<div class="col-xs-12 col-sm-12">
			<div class=" ui-draggable ui-droppable">
				<div class="box-header">
					<div class="box-name">
						解除合約
					</div>
					<div class="box-icons">
						<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
						</a> <a class="expand-link"> <i class="fa fa-expand"></i>
						</a>
					</div>
					<div class="no-move"></div>
				</div>
				<div class="box-content">
					<form class="form-horizontal" id="draftForm" action="">
					<input name="lsNo" type="hidden" value="${lsNo}">
					<input name="lsVer" type="hidden" value="${lsVer}">
					<input name="appSeq" type="hidden" value="${appSeq}">
						<button class="btn btn-primary btn-label"  type="button" id="btn_apply">
							申請</button>
						<button class="btn btn-primary btn-label" type="button"
							id="btn_upload">附件上傳</button>
						<hr>
						<table style="width: 100%">
							<tr>
								<td nowrap="nowrap" width="6%"><label
									class="col-sm-12 control-label">合約編號 :</label></td>
								<td width="20%">
									<p style="padding-top: 12px; padding-left: 10px;" id="" >${lsNo}</p>
								</td>
								<td nowrap="nowrap" width="10%"><label
									class="col-sm-12 control-label">合約名稱 :</label></td>
								<td width="20%">
									<p style="padding-top: 12px; padding-left: 10px;" id="">${lsName}</p>
								</td>
								<td nowrap="nowrap" width="10%"><label
									class="col-sm-12 control-label">合約起迄 :</label></td>
								<td width="34%">
									<p style="padding-top: 12px; padding-left: 10px;" id="">${S_Date}~${E_Date}</p>
								</td>
							</tr>

							<tr>
								<td nowrap="nowrap"><label class="col-sm-12 control-label">出租人
										:</label></td>
								<td COLSPAN="3">
									<p style="padding-top: 15px; padding-left: 10px;" id="">${lessorName}</p>
								</td>
								<td nowrap="nowrap"><label class="col-sm-12 control-label">合約紙本保管單位/地點 :</label></td>
								<td>
									<p style="padding-top: 15px; padding-left: 10px;" id="">${deptPlace}</p>
								</td>
							</tr>
						</table>
						<hr>

						<div>
							<div>
								<button class="btn btn-primary btn-label" type="button" id="save_draft">
									儲存草稿</button>
								<button class="btn btn-primary btn-label" type="button" id="">
									列印解約協議書</button>
								<button class="btn btn-primary btn-label" type="button" id="">
									列印借電終止協議書</button>
							</div>

							<table style="width: 100%;margin-top:10px">								
								<tr>
									<td nowrap="nowrap" width="8%"><label class="col-sm-12 control-label">解約日
											:</label></td>
									<td width="42%">
										<div class="col-sm-12">
											<div style="width: 40%; height: auto; float: left; display: inline">
												<input id="trmnDate" name="trmnDate" type="text" class="form-control" readonly="readonly" placeholder="點選輸入框">
											</div>
											<div style="width: 60%; height: auto; float: left; display: inline; padding-left: 10px;padding-top: 6px">
												<font color='#CC0000'>(解約日不可超過承租迄日)</font>
											</div>
										</div>
									</td>
									<td nowrap="nowrap" width="15%"><label class="col-sm-12 control-label">建立人員
											:</label></td>
									<td width="35%">
										<div class="col-sm-12">
											<p style="padding-top: 12px; padding-left: 10px;" id=""><c:out value="${loginUser.deptName}"/>&nbsp;/&nbsp;<c:out value="${loginUser.chName}"/></p>
										</div>
									</td>
								</tr>

								<tr>
									<td nowrap="nowrap" style="padding-top: 6px"><label
										class="col-sm-12 control-label">解約類型 :</label></td>
									<td COLSPAN=5 style="padding-top: 6px">
										<div class="col-sm-12">
											<div
												style="width: 20%; height: auto; float: left; display: inline">
												<select id="trmnTyepeEnum" class="populate placeholder" name="trmnTyepeEnum">
													<c:forEach items="${trmnTyepeEnum}" var="item">
														<c:choose>
															<c:when test="${item.value == 1}">
																<option value="${item.value}" selected >${item.label}</option>
															</c:when>
															<c:otherwise>
																<option value="${item.value}" >${item.label}</option>
	   														</c:otherwise>
														</c:choose>
													</c:forEach>
												</select>
											</div>
											<div
												style="width: 19%; height: auto; float: left; display: inline; padding-top: 5px">
												<label class="col-sm-12 control-label">解約原因 :</label>
											</div>
											<div
												style="width: 20%; height: auto; float: left; display: inline; padding-left: 15px">
												<select id="trmnResnEnum" class="populate placeholder" name="trmnResnEnum">
												<c:forEach items="${trmnResnEnum}" var="item">
													<c:choose>
														<c:when test="${item.value == 1}">
															<option value="${item.value}" selected >${item.label}</option>
														</c:when>
														<c:otherwise>
															<option value="${item.value}" >${item.label}</option>
   														</c:otherwise>
													</c:choose>
												</c:forEach>
												
												</select>
											</div>
											<div
												style="width: 16%; height: auto; float: left; display: inline; padding-left: 20px; padding-top: 10px">
												於租賃契約終止日後</div>
											<div
												style="width: 5%; height: auto; float: left; display: inline; padding-top: 5px">
												<input id="recovDay" name="recovDay" class="form-control" 
													style="padding: 2px 0px 0px 10px" type="number" min="0" />
											</div>
											<div
												style="width: 20%; height: auto; float: left; display: inline; padding-top: 10px; padding-left: 5px">
												天內將租賃標的物回復原狀</div>
										</div>
									</td>
								</tr>
								
								<tr>
									<td nowrap="nowrap" style="padding-top: 6px"><label
										class="col-sm-12 control-label">套表格式 :</label></td>
									<td COLSPAN=5 style="padding-top: 6px">
										<div class="col-sm-6">
											<select id="trmnList" class="populate placeholder" name="trmnList">
												<c:forEach items="${trmnList}" var="item">
													<option value="${item.FORM_ID}">${item.FORM_NAME}</option>
												</c:forEach>
											</select>
										</div>
									</td>
								</tr>
								
								<tr>
									<td valign='top' nowrap="nowrap" style="padding-top: 6px"><label
										class="col-sm-12 control-label">說明 :</label></td>
									<td COLSPAN=5 style="padding-top: 6px">
										<div class="col-sm-10">
											<textarea class="form-control" id="trmnDesc" name="trmnDesc" rows="4"></textarea>
										</div>
									</td>
								</tr>
								
								<tr>
									<td valign='top' nowrap="nowrap" style="padding-top: 6px"><label
										class="col-sm-12 control-label">回饋門號 :</label></td>
									<td COLSPAN=5 style="padding-top: 10px">
										<div class="col-sm-12" >
											<table id="" border=2 class="table-striped"
												style="table-layout: fixed; word-break: break-all; word-wrap: break-word; width: 100%;">
												<thead style="background-color: #f0f0f0;">
													<tr style="height: 25px">
														<th class="titleTDVo" style="width: 13%;">申辦證號</th>
														<th class="titleTDVo" style="width: 12%;">姓名</th>
														<th class="titleTDVo" style="width: 15%;">門號</th>
														<th class="titleTDVo" style="width: 15%;">資費方案</th>
														<th class="titleTDVo" style="width: 20%;">回饋原因</th>
														<th class="titleTDVo" style="width: 25%;">說明</th>
													</tr>
												</thead>
												<tbody id="phoneNoList">
												</tbody>
											</table>
										</div>
									</td>
								</tr>
								
								<tr>
									<td valign='top' nowrap="nowrap" style="padding-top: 6px"><label
										class="col-sm-12 control-label">回饋手機 :</label></td>
									<td COLSPAN=5 style="padding-top: 10px">
										<div class="col-sm-12" >
											<table id="" border=2 class="table-striped"
												style="table-layout: fixed; word-break: break-all; word-wrap: break-word; width: 100%">
												<thead style="background-color: #f0f0f0;">
													<tr style="height: 25px">
														<th class="titleTDVo" style="width: 15%;">手機型號</th>
														<th class="titleTDVo" style="width: 15%;">數量</th>
														<th class="titleTDVo" style="width: 15%;">預估金額</th>
														<th class="titleTDVo" style="width: 25%;">回饋原因</th>
														<th class="titleTDVo" style="width: 30%;">回饋原因說明</th>
													</tr>
												</thead>
												<tbody id="phoneList">
												</tbody>
											</table>
										</div>
									</td>
								</tr>

								<tr>
									<td COLSPAN=6 nowrap="nowrap" style="padding-top: 6px">
										<div style="width: 8%">
											<label class="col-sm-12 control-label">用電方式 :</label>
										</div>

										<div class="col-sm-12" style="margin-top: 5px">
											<table id="" border=2 class="table-hover"
												style="table-layout: fixed; word-break: break-all; word-wrap: break-word; width: 100%">
												<thead style="background-color: #f0f0f0;">
													<tr style="height: 25px">
														<th class="titleTDVo" style="width: 15%;">電錶號碼</th>
														<th class="titleTDVo" style="width: 20%;">用電方式</th>
														<th class="titleTDVo" style="width: 15%;">計費方式</th>
														<th class="titleTDVo" style="width: 21%;">起算日</th>
														<th class="titleTDVo" style="width: 26%;">抄錶度數</th>
													</tr>
												</thead>
												<tbody id="elecList">
												</tbody>
											</table>
										</div>
									</td>
								</tr>
								
								<tr>
									<td nowrap="nowrap" style="padding-top: 6px"><label
										class="col-sm-12 control-label">套表格式 :</label></td>
									<td COLSPAN=5 style="padding-top: 6px">
										<div class="col-sm-3">
											<select id="elecupdList" class="populate placeholder" name="elecupdList">
												<c:forEach items="${elecupdList}" var="item">
													<option value="${item.FORM_ID}">${item.FORM_NAME}</option>
												</c:forEach>
											</select>
										</div>
									</td>
								</tr>
							</table>

							<div style="padding: 20px 10px 0px 10px">
								<button class="btn btn-primary btn-label" type="button" id="">
									預估清算</button>

								<table id="forecastTB" class="scrollTable"
									style="table-layout: fixed; word-break: break-all; word-wrap: break-word; width: 100%">
									<thead style="background-color: #f0f0f0;">
										<tr style="height: 25px">
											<th class="theadth" style="width: 12%;">出租人</th>
											<th class="theadth" style="width: 12%;">租金</th>
											<th class="theadth" style="width: 12%;">押金</th>
											<th class="theadth" style="width: 12%;">電費</th>
											<th class="theadth" style="width: 12%;">押金</th>
											<th class="theadth" style="width: 12%;">預繳</th>
											<th class="theadth" style="width: 15%;">結算結果</th>
											<th class="theadth" style="width: 13%;">金額</th>
										</tr>
									</thead>
									<tbody>
										<tr style="height:25px">
											<td>出租人</td>
											<td>租金</td>
											<td>押金</td>
											<td>電費</td>
											<td>押金</td>
											<td>預繳</td>
											<td>應收/應付</td>
											<td>900</td>
<!-- 											<td style="padding: 3px 5px 3px 5px"><input id="" name="" class="form-control" type="text" style="width: 100%;" value="900"></td> -->
										</tr>
										<tr style="height:25px">
											<td>出租人2</td>
											<td>租金2</td>
											<td>押金2</td>
											<td>電費2</td>
											<td>押金2</td>
											<td>預繳2</td>
											<td>應收/應付2</td>
											<td>500</td>
<!-- 											<td style="padding: 3px 5px 3px 5px"><input id="" name="" class="form-control" type="text" style="width: 100%;" value="500"></td> -->
										</tr>
									</tbody>
								</table>
							</div>

							<div style="margin-top: 10px">
								<fieldset class="ftSolidLine">
									<legend class="legend">至解約日清算明細</legend>
									<table id="" border=2 class="table-striped"
												style="table-layout: fixed; word-break: break-all; word-wrap: break-word; width: 100%">
												<thead style="background-color: #f0f0f0;">
													<tr style="height: 25px">
														<th class="titleTDVo" style="width: 17%;">項目</th>
														<th class="titleTDVo" style="width: 10%;">請款起</th>
														<th class="titleTDVo" style="width: 10%;">請款迄</th>
														<th class="titleTDVo" style="width: 10%;">金額<br>(A)</th>
														<th class="titleTDVo" style="width: 10%;">代扣所得稅<br>(B)</th>
														<th class="titleTDVo" style="width: 10%;">代扣補充健保費<br>(C)</th>
														<th class="titleTDVo" style="width: 10%;">營業稅<br>(D)</th>
														<th class="titleTDVo" style="width: 10%;">押金<br>(E)</th>
														<th class="titleTDVo" style="width: 13%;">支付淨額(=A-B-C+E)<br>或(=A+D+E)</th>
													</tr>
												</thead>
												<tbody>
													<tr style="height: 25px">
														<td>押金退回</td>
														<td>起</td>
														<td>迄</td>
														<td>A</td>
														<td>B</td>
														<td>C</td>
														<td>D</td>
														<td>E</td>
														<td>xxxx</td>
													</tr>
													<tr style="height: 25px">
														<td>預付</td>
														<td>&nbsp;</td>
														<td>&nbsp;</td>
														<td>&nbsp;</td>
														<td>&nbsp;</td>
														<td>&nbsp;</td>
														<td>&nbsp;</td>
														<td>&nbsp;</td>
														<td>&nbsp;</td>
													</tr>
													<tr style="height: 25px">
														<td>租金補退</td>
														<td>&nbsp;</td>
														<td>&nbsp;</td>
														<td>&nbsp;</td>
														<td>&nbsp;</td>
														<td>&nbsp;</td>
														<td>&nbsp;</td>
														<td>&nbsp;</td>
														<td>&nbsp;</td>
													</tr>
													<tr style="height: 25px">
														<td>應收回(支付)金額</td>
														<td>&nbsp;</td>
														<td>&nbsp;</td>
														<td>&nbsp;</td>
														<td>&nbsp;</td>
														<td>&nbsp;</td>
														<td>&nbsp;</td>
														<td>&nbsp;</td>
														<td>&nbsp;</td>
													</tr>
													<tr style="height: 25px">
														<td>&nbsp;</td>
														<td>&nbsp;</td>
														<td>&nbsp;</td>
														<td>&nbsp;</td>
														<td>&nbsp;</td>
														<td>&nbsp;</td>
														<td>&nbsp;</td>
														<td>&nbsp;</td>
														<td>&nbsp;</td>
													</tr>
													<tr style="height: 25px">
														<td>應收回(支付)金額</td>
														<td>&nbsp;</td>
														<td>&nbsp;</td>
														<td>&nbsp;</td>
														<td>&nbsp;</td>
														<td>&nbsp;</td>
														<td>&nbsp;</td>
														<td>&nbsp;</td>
														<td>&nbsp;</td>
													</tr>
													<tr style="height: 25px">
														<td>減:未兌現支票</td>
														<td>&nbsp;</td>
														<td>&nbsp;</td>
														<td>&nbsp;</td>
														<td>&nbsp;</td>
														<td>&nbsp;</td>
														<td>&nbsp;</td>
														<td>&nbsp;</td>
														<td>&nbsp;</td>
													</tr>
													<tr style="height: 25px">
														<td>應收回(支付)金額</td>
														<td>&nbsp;</td>
														<td>&nbsp;</td>
														<td>&nbsp;</td>
														<td>&nbsp;</td>
														<td>&nbsp;</td>
														<td>&nbsp;</td>
														<td>&nbsp;</td>
														<td>&nbsp;</td>
													</tr>
												</tbody>
											</table>
									
								</fieldset>
							</div>


						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>