<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<title>新增臨時PO單</title>
		<script type="text/javascript">
		$(document).ready(function() {		
// 			var thisYear = new Date().getFullYear();
// 			var nextYear = thisYear+1; 
// 			$("<option value="+thisYear+" >"+thisYear+"</option><option value="
// 					+nextYear+" >"+nextYear+"</option>").appendTo("#addFormPoYear");
			
			$('#amountTips').poshytip({
				className: 'tip-violet',
				bgImageFrameSize: 9
			});

			
			if($("#poEvent").val() == "insert"){
				$('#boxTitle').append('<i class="fa fa-search"></i> <span>新增臨時PO單</span>');
				document.getElementById('enabled').checked=true;
				$("#addFormIsTemp").val("Y");
				$("#addFormShowIsTemp").text("是");
				$("#exportBtn").prop("disabled",true);
				$("#mergerBtn").prop("disabled",true);
				
			}else{				
				//啟用
				if('${result.ENABLED}'=="Y"){
					 document.getElementById('enabled').checked=true;
				}
  				//$('#addFormPoYear').val('${result.PO_YEAR}'); //年度
  				$('#addFormPoYear').val('${result.PO_YEAR}');
				document.getElementById('addFormPoNo').disabled=true; //PO單號
				document.getElementById('addFormPoName').disabled=true; //PO名稱
 				$('#poDomain').val('${result.PO_DOMAIN}');  //區域
 				$('#poType').val('${result.PO_TYPE}'); //工程類別
 				$('#feeType').val('${result.FEE_TYPE}'); //支出類別
 				$('#taxIn').val('${result.TAX_IN}');  //是否含稅				
				$('#currency').val('${result.CURRENCY}');  //幣別
				
				if('${result.IS_TEMP}'=="Y"){
					$("#addFormShowIsTemp").text("是");
				}else{
					$("#addFormShowIsTemp").text("否");
					$("#addFormIsTemp").val("N");
					$("#mergerBtn").prop("disabled",true);
					document.getElementById('poDomain').disabled=true;
					document.getElementById('feeType').disabled=true;
					document.getElementById('amount').disabled=true;	
					document.getElementById('companyQryBtn').disabled=true;
				}
				if('${poEvent}' == "edit"){
					$('#boxTitle').append('<i class="fa fa-search"></i> <span>修改PO單</span>');	
				}else if('${poEvent}' == "view"){
					$('#boxTitle').append('<i class="fa fa-search"></i> <span>檢視PO單</span>');	
					$("#addPOForm :input").prop("disabled", true);
				}	
				
			}
						
			$('#startDate').datepicker({
				dateFormat : "yy/mm/dd",
				changeYear : true,
				changeMonth : true,
				showButtonPanel : true,
				showTime : false
			});
			
			$('#endDate').datepicker({
				dateFormat : "yy/mm/dd",
				changeYear : true,
				changeMonth : true,
				showButtonPanel : true,
				showTime : false
			});
			
			
			$("#savePoBtn").click(function(){
                var domainQuotaArray =[];
                var total =0;
				var table = document.getElementById("domainAmountTable");
				for (var i = 1, row; row = table.rows[i]; i++) {								    				
					var str=row.cells[1].innerHTML+"_"+$('#'+row.cells[1].innerHTML).val();
					total +=parseInt($('#'+row.cells[1].innerHTML).val());
					domainQuotaArray.push(str);					
				}


				if ($('#addPOForm')[0].checkValidity()) {
					event.preventDefault();
					var errorMessages = [];
					var startDate = $("#startDate").val();
					var startDateArray = startDate.split("/");
					var start = new Date(startDateArray[0],startDateArray[1],startDateArray[2],0,0,0,0);
					var endDate = $("#endDate").val();
					var endDateArray = endDate.split("/");
					var end = new Date(endDateArray[0],endDateArray[1],endDateArray[2],0,0,0,0);
					if($("#startDate").val() == ""){
						errorMessages[errorMessages.length] = "必須選擇開始日期";
					}
					if($("#endDate").val() == ""){
						errorMessages[errorMessages.length] = "必須選擇結束日期";
					}
					if($("#amount").val() > 2147483647){
						errorMessages[errorMessages.length] = "總額輸入數字過大";
					}
					if($("#alertRate").val() >100){
						errorMessages[errorMessages.length] = "告警百分比超過100%";
					}
					if($("#excRate").val().match("[^0-9.]")){
						errorMessages[errorMessages.length] = "匯率格式錯誤";
					}
	 				if(total > parseInt($("#amount").val())){
	 					errorMessages[errorMessages.length] = "區域配額已超過總額";
	 				} else if (total < parseInt($("#amount").val())) {
	 					errorMessages[errorMessages.length] = "區域配額總和不符合總額";
	 				}
					var excRateArray = $("#excRate").val().split(".");
					if(!$("#excRate").val().match("[0-9]{1,4}[.][0-9]{2,2}")){
						errorMessages [errorMessages.length]="匯率格式錯誤，必須是整數加上兩位小數";
					}else if(excRateArray[0].length >4 && excRateArray[1].length >2){
						errorMessages [errorMessages.length]="匯率格式錯誤，整數最多四位，小數必須為兩位";
					}else if(excRateArray[0].length >4 ){
						errorMessages [errorMessages.length]="匯率格式錯誤，整數最多四位";
					}else if(excRateArray[1].length >2){
						errorMessages [errorMessages.length]="匯率格式錯誤，小數必須為兩位";
					}
					var guarantee = $("#guarantee").val();
					if(guarantee != "" && guarantee != null){
						if((guarantee.length >4) || (!guarantee.match("[0-9]{4,4}"))){
							errorMessages[errorMessages.length] = "保固期請輸入西元年";
						}else if(guarantee < 1970){
							errorMessages[errorMessages.length] = "保固期必須大於1970年";
						}	
					}
					if($("#startDate").val() != "" && $("#endDate").val() != ""){
						if(start.getTime() > end.getTime()){
							errorMessages[errorMessages.length] = "結束日期不可早於開始日期";
						}
					}
					var  coVatNo = $("#coVatNo").val();
					if(coVatNo.length == ""){
						errorMessages[errorMessages.length] = "廠商不得為空";
					}
					if(errorMessages.length!=0){
						var msg ="";
						for(var em in errorMessages){
							msg += errorMessages[em]+"\n";
						}
						alert(msg);
						return false;
					}
					
					if('${poEvent}'=='insert'){						
						if($("#poDomain").val()!="HQ"){
							if(checkQuota()==1){
								alert("非該區域之配額需為0!");
							}else{
								toInsert(domainQuotaArray);
							}	
						}else{
							toInsert(domainQuotaArray);
						}												
					}else if('${poEvent}'=='edit'){	
						if($("#poDomain").val()!="HQ"){
							if(checkQuota()==1){
								alert("非該區域之配額需為0!");
							}else{
								toUpdate();
							}	
						}else{
							toUpdate();
						}											
					}					
				}
				
			});//save end	
			
			//合併po單
			$('#mergerBtn').click(function(){
				if('${result.IS_MERGE}'=="Y"){
					alert("此臨時PO單已合併過!");
				}else{
					var targetFrameId = "showPOQueryFrame";
					
					$.ajax({
						mimeType : 'text/html; charset=utf-8',
						url : CONTEXT_URL + "/cm/po/common/init",
						type : 'POST',
						dataType : "html",
						data : {
							"callBackFun" : $('#callBackFun').val(),
							"targetFrameId" : targetFrameId,
						},
						async : false,
						success : function(data) {
							var is_Merge = "<input type='hidden' id='is_mergeId' name='is_mergeId' value='Y' />";
							var is_temp = "<input type='hidden' id='is_tempId' name='is_tempId' value='N' />";
							initialDialogFrame(targetFrameId, "PO單查詢", is_Merge+is_temp+data);
						}
					});										
					
				}				
			});
			
			
			//匯出工項
			$('#exportBtn').click(function(){
				window.location.href = CONTEXT_URL + "/cm/po/exportExcel?poId=" + $("#poId").val();
			});
			
		});//document ready end
		
		//統一編號檢核
		function checkCompanyNo(idvalue) {
		   var tmp = new String("12121241");
		   var sum = 0;
		   for (var i=0; i< 8; i++) {
		     var s1 = parseInt(idvalue.substr(i,1));
		     var s2 = parseInt(tmp.substr(i,1));
		     sum += cal(s1*s2);
		   }
		   if (!valid(sum)) {
		      if (idvalue.substr(6,1)=="7") return(valid(sum+1));
		   }  
		   return(valid(sum));
		}

		function valid(n) {
		   return (n%10 == 0)?true:false;
		}

		function cal(n) {
		   var num=0;
		   while (n!=0) {
			   num += (n % 10);
		      n = (n - n%10) / 10;  // 取整數
		     }
		   return num;
		}
		
		//檢查Quota
		function checkQuota(){
			var isOK=0;
			var selDomain=$("#poDomain").val();			
			var table = document.getElementById("domainAmountTable");			
			for (var i = 1, row; row = table.rows[i]; i++) {	
				if(selDomain!=row.cells[1].innerHTML){
					if($('#'+row.cells[1].innerHTML).val()>0){
						isOK=1;
						break;
					}							    	
			    }
			}	
			return isOK;
		}
		
		function toInsert(domainQuotaArray){
				$.ajax({
				url : CONTEXT_URL + "/cm/po/save",
				type : 'POST',
				async : false,
				data : $("#addPOForm").serialize()+"&domainQuotaArray="+domainQuotaArray+"&poEvent="+$("#poEvent").val(),
				success : function(data){
					var result = data['result'];
					if(result == true){
						alert('<s:message code="msg.add.success"/>');
						var poNo = $('#addFormPoNo').val();
						var poName = $('#addFormPoName').val();
						var isTemp = $('#addFormIsTemp').val();
						var poYear = $("#addFormPoYear").val();
						var data= {
								poNo: $.trim(poNo),
								year: $.trim(poYear),
								poName: $.trim(),
								co_vat_No: "",
								isTemp: $.trim(),
						};
						$("#poNo").val(poNo);
						$("#poName").val(poName);
						$("#poYear").val(poYear);
						if(isTemp == "Y"){
							$('#isTemp').prop("checked",true);
						}else{
							$('#isTemp').prop("checked",false);
						}
						$("#grid").setGridParam({datatype:"json", postData:data, mtype: 'POST',page:1}).jqGrid().trigger("reloadGrid");
						$.fancybox.close();
					}else{
						alert(result);
					}
				
				}					
    		});
		}
		
		//修改儲存
		function toUpdate(){
			var lineIdMap = getLineIdMap();
			var data = [];
			var table = document.getElementById("domainAmountTable");
			for (var i = 1, row; row = table.rows[i]; i++) {								    				
				data.push(row.cells[1].innerHTML);
				data.push($('#'+row.cells[1].innerHTML).val());
			}	
			
			var myform = $('#addPOForm');
			var disabled = myform.find(':input:disabled').removeAttr('disabled');
			var serialized = myform.serialize();
			disabled.attr('disabled','disabled');

			$.ajax({
				url : CONTEXT_URL + "/cm/po/updateSave",
				type : 'POST',
				async : false,
				data : serialized+"&domainQuotaArray="+data + "&jLineIdMap=" + lineIdMap,
				success : function(data){
					var result = data['result'];
					if(result){
						alert('<s:message code="msg.update.success"/>');
						$.fancybox.close();
						$('#btn_search').trigger( "click" );
					}else{
						alert(result);
					}
				}					
	       	});
			
		}
		
		function toMerger(addressObject){
			addressObject = JSON.parse(addressObject);
			$.ajax({
				url : CONTEXT_URL + "/cm/po/toMerger",
				type : 'POST',
				data : {
					targetID : addressObject.po_ID,
					tempID : '${result.PO_ID}'
				},
				dataType : "json",
				async : false,
				success : function(data) {
					if(data.success){		
						alert("合併成功!");
						editAddPO(data.msg,"edit");	 					
					}else{
						alert(data.msg);
					}				
				}
			});

		}
		
		//取得Line ID 設定
		function getLineIdMap() {
			var lineIdMap = [];
			$(".lineIdMapCatId").each(function(i){
				var poLineId = $("#poLineId_" + (i+1)).val();
				if (poLineId != "") {
					lineIdMap.push({
						  catId : $(this).val(),
						  poLineId : poLineId,
					}); 
				}
			});
			return JSON.stringify(lineIdMap);
		}
		
		
		function companyQuery(){
			var callBackFunName = $("#companyQueryCallBackFun").val();
			var targetFrameId = "showCompanyQueryPage";
			openCompanyQueryFrame(targetFrameId, callBackFunName);
		}
		
		function showCompanyQueryData(object){
			var obj = JSON.parse(object);
			$("#coVatNo").val(obj.co_VAT_NO);
			$("#companyName").text(obj.co_NAME);
		}
		</script>
	</head>
	<body>
		<div class="row">
			<div class="col-xs-12 col-sm-12">
				<div class=" ui-draggable ui-droppable">
					<div class="box-header">
						<div id="boxTitle" class="box-name">
							
						</div>
						<div class="no-move"></div>
					</div>
					<div class="box-content">
						<div class="box-content">
							<form class="form-horizontal" id="addPOForm" action="" >
								<table style="width: 100%">
									<tr>
										<td align='right'>
											<button class="btn btn-primary btn-label-left" style="margin-right: 10px"
												type="submit" id="savePoBtn"><span><i class="fa fa-save"></i></span>存檔</button>
										</td>
										<td nowrap="nowrap">
											<button class="btn btn-primary btn-label-left" id="exportBtn"
												 type="button"><span><i class="fa fa-cloud-download"></i></span>匯出工項</button>
											<button class="btn btn-primary btn-label-left" id="mergerBtn"
												style="margin-left: 10px" type="button"><span><i class="fa fa-link"></i></span>合併正式PO單</button>
										</td>
									</tr>
									<tr>
										<td align='right' nowrap="nowrap">
											<div class="row form-group">
												<div class="col-sm-12">
													<div class="checkbox-inline">
														<label>
															<input type="checkbox" id="enabled" name="ENABLED" />啟用 <i class="fa fa-square-o"></i>
														</label>
													</div>
												</div>
											</div>
										</td>
									</tr>
									<tr>
										<td nowrap="nowrap">
											<label class="col-sm-12 control-label">年度：</label>
										</td>
										<td nowrap="nowrap">
											<div class="col-sm-6">
												<select id="addFormPoYear" name="PO_YEAR" class="form-control" required="required">
													<option value="">---請選擇---</option>
													<c:forEach items="${poYearL}" var="poYear">
														<option value="${poYear.key}">${poYear.value}</option>
													</c:forEach>
												</select>

											</div>
										</td>
										<td><label class="col-sm-12 control-label">臨時PO單：</label></td>
										<td>
											<div class="col-sm-6">
												<p style="padding-top: 15px;" id="addFormShowIsTemp"></p>
												<input id="addFormIsTemp" name="IS_TEMP" type="hidden" value="${result.IS_TEMP}" >
											</div>
										</td>
									</tr>
									<tr>
										<td>
											<div class="clearfix">&nbsp;</div>
										</td>
									</tr>
									<tr>
										<td width="15%"><label
											class="col-sm-12 control-label"><span class="s">* </span>PO單號：</label></td>
										<td width="35%">
											<div class="col-sm-10">
												<input id="addFormPoNo" name="PO_NO" class="form-control" type="text" required="required" value="${result.PO_NO}"></input>
											</div>
											<input id="poId" name="PO_ID" type="hidden" value="${result.PO_ID}" ></input>
										</td>
							
										<td width="15%">
											<label class="col-sm-12 control-label"><span class="s">* </span>PO名稱：</label></td>
										<td width="35%">
											<div class="col-sm-10">
												<input id="addFormPoName" name="PO_NAME" type="text" class="form-control" required="required" value="${result.PO_NAME}"></input>
											</div>
										</td>
									</tr>
									<tr>
										<td>
											<div class="clearfix">&nbsp;</div>
										</td>
									</tr>
									<tr>
										<td>
											<label class="col-sm-12 control-label">
												<span class="s">* </span>區域：
											</label>
										</td>
										<td>
											<div class="col-sm-6">
												<select id="poDomain" name="PO_DOMAIN" class="form-control" required="required">
													<option value="">---請選擇---</option>
													<c:forEach items="${domainList}" var="domain">
														<option value="${domain.ID}">${domain.NAME}</option>
													</c:forEach>
												</select>
											</div>
										</td>
										<td>
											<label class="col-sm-12 control-label">
												<span class="s">* </span>工程類別：
											</label>
										</td>
										<td>
											<div class="col-sm-6">
												<select id="poType" name="PO_TYPE" class="form-control" required="required">
													<option value="">---請選擇---</option>
													<c:forEach items="${poTypeList}" var="poType">
														<option value="${poType.value}">${poType.label}</option>
													</c:forEach>
												</select>
											</div>
										</td>
									</tr>
									
									<tr>
										<td>
											<div class="clearfix">&nbsp;</div>
										</td>
									</tr>
									<tr>
										<td><label class="col-sm-12 control-label"><span class="s">* </span>廠商：</label></td>
										<td>
											<div class="col-sm-10">
												<input id="coVatNo" name="CO_VAT_NO" style="padding-right : 0px;"  
												type="text" class="form-control" readonly="readonly" required="required" value="${result.CO_VAT_NO}"/>
											</div>
											<div style="width: 16%; height: auto; float: left; display: inline;padding-left:5px">
												<input type="button" id="companyQryBtn" class="btn btn-primary btn-label-left" onclick="companyQuery();" value="選擇"/>
												<input type="hidden" id="companyQueryCallBackFun" name="companyQueryCallBackFun" value="showCompanyQueryData" />
												<div id="showCompanyQueryPage"></div>
											</div>
										</td>
										<td>
											<label class="col-sm-12 control-label"><span class="s" id="btsSiteIdPrimary">* </span>支出類別：</label>
										</td>
										<td>
											<div class="col-sm-6">
												<select id="feeType" name="FEE_TYPE" class="form-control" required="required">
													<option value="">---請選擇---</option>
													<c:forEach items="${feeTypeList}" var="feeType">
														<option value="${feeType.value}">${feeType.label}</option>
													</c:forEach>
												</select>
											</div>
										</td>
									</tr>
									<tr>
										<td>
											<div class="clearfix">&nbsp;</div>
										</td>
									</tr>
									<tr>
										<td><label class="col-sm-12 control-label">廠商名稱：</label></td>
										<td>
											<div class="col-sm-10">
												<p style="padding-top: 15px;" id="companyName">${companyName}</p>
											</div>
										</td>
										<td><label class="col-sm-12 control-label"><span class="s">* </span>是否含稅：</label></td>
										<td>
											<div class="col-sm-6">
												<select id="taxIn" name="TAX_IN" class="form-control" required="required">
													<option value="Y">是</option>
													<option value="N">否</option>
												</select>
											</div>
										</td>
										
									</tr>
									<tr>
										<td>
											<div class="clearfix">&nbsp;</div>
										</td>
									</tr>
									<tr>
										<td>
											<label class="col-sm-12 control-label">
												<span class="s">* </span>開始日期：
											</label>
										</td>
										<td>
											<div class="col-sm-6">
												<input id="startDate" name="START_DATE" type="text" class="form-control"
													 placeholder="點選輸入框" readonly="readonly" value="<fmt:formatDate value="${result.START_DATE}" pattern="yyyy/MM/dd"/>"></input>
											</div>
										</td>
										<td>
											<label class="col-sm-12 control-label">
												<span class="s">* </span>結束日期：
											</label>
										</td>
										<td>
											<div class="col-sm-6">
												<input id="endDate" name="END_DATE" type="text" class="form-control"
														 placeholder="點選輸入框" readonly="readonly" value="<fmt:formatDate value="${result.END_DATE}" pattern="yyyy/MM/dd"/>" ></input>
											</div>
										</td>
									</tr>
									<tr>
										<td>
											<div class="clearfix">&nbsp;</div>
										</td>
									</tr>
									<tr>
										<td><label class="col-sm-12 control-label">票期(天)：</label></td>
										<td>
											<div class="col-sm-6">
												<input id="usance" name="USANCE" style="padding-right : 0px;" class="form-control" type="number" value="${result.USANCE}" ></input>
											</div>
										</td>
										<td nowrap="nowrap">
											<label class="col-sm-12 control-label">保固期(年)：</label>
										</td>
										<td nowrap="nowrap">
											<div class="col-sm-6">
												<input id="guarantee" name="GUARANTEE" style="padding-right : 0px;"  class="form-control" type="number" value="${result.GUARANTEE}" ></input>
											</div>
										</td>
									</tr>
									<tr>
										<td>
											<div class="clearfix">&nbsp;</div>
										</td>
									</tr>
									<tr>
										<td>
											<label class="col-sm-12 control-label">
												<span class="s">* </span>幣別：
											</label>
										</td>
										<td>
											<div class="col-sm-6">
												<select id="currency" name="CURRENCY" class="form-control" required="required">
													<option value="">---請選擇---</option>
													<c:forEach items="${currencyList}" var="currency">
														<option value="${currency.value}">${currency.label}</option>
													</c:forEach>					
												</select>
											</div>
										</td>
										<td>
											<label class="col-sm-12 control-label">
												<span class="s">* </span>匯率：
											</label>
										</td>
										<td>
											<div class="col-sm-6">
												<input id="excRate" name="EXC_RATE" type="text" class="form-control" placeholder="例:123.00" required="required" value="${result.EXC_RATE}" ></input>
											</div>
										</td>
										
									</tr>
									<tr>
										<td>
											<div class="clearfix">&nbsp;</div>
										</td>
									</tr>
									<tr>
										<td>
											<label class="col-sm-12 control-label">
												<span class="s">* </span>總額：
											</label>
										</td>
										<td>
											<div class="col-sm-6">
												<input id="amount" name="AMOUNT" style="padding-right : 0px;"  class="form-control" type="number" required="required" value="${result.AMOUNT}" ></input>
											</div>
										</td>
										<td nowrap="nowrap">
											<label class="col-sm-12 control-label">
												<span class="s">* </span>告警百分比(總額低於)：
											</label>
										</td>
										<td>
											<div class="col-sm-6">
												<input id="alertRate" name="ALERT_RATE"
												style="padding-right : 0px;" 
												class="form-control" type="number" 
												required="required" value="${result.ALERT_RATE}" ></input> 
											</div>
											<p style="margin-top : 5px">%</p>
										</td>
									</tr>
									<tr>
										<td>
											<div class="clearfix">&nbsp;</div>
										</td>
									</tr>
									<tr>
										<td><label class="col-sm-12 control-label">派工金額(未驗)：</label></td>
										<td>
											<div class="col-sm-10">
												<p style="padding-top: 15px; padding-left: 15px;" id="osAmount" >
												<fmt:formatNumber type="NUMBER">${bookingAmount}<%-- ${result.OS_AMOUNT}--%></fmt:formatNumber></p>
											</div>
										</td>
										<td><label class="col-sm-12 control-label">驗收金額：</label></td>
										<td>
											<div class="col-sm-10">
												<p style="padding-top: 15px; padding-left: 15px;" id="apAmount">
												<fmt:formatNumber type="NUMBER">${result.AP_AMOUNT}</fmt:formatNumber></p>
											</div>
										</td>
									</tr>
									<tr>
										<td>
											<div class="clearfix">&nbsp;</div>
										</td>
									</tr>
									<tr>
										<td><label class="col-sm-12 control-label">可用總餘額：</label></td>
										<td>
											<div class="col-sm-10">
												<p style="padding-top: 15px; padding-left: 15px;" id="unUsedAmount" >
												<fmt:formatNumber type="NUMBER">${unUsedAmount}</fmt:formatNumber></p>
											</div>
										</td>
									</tr>
									<tr>
										<td>
											<div class="clearfix">&nbsp;</div>
										</td>
									</tr>
									<tr>
										<td>
											<label class="col-sm-12 control-label">各區配額：</label>
										</td>
									</tr>
									<tr>
										<td colspan="4" nowrap="nowrap">
											<div style="margin-left: 80px; margin-top: 5px;width :90%">
												<table id="domainAmountTable"
													class="table table-bordered  table-hover table-heading table-datatable">
													<thead>
														<tr>
															<th width=10%>區域</th>
															<th style="display:none;">區域ID</th>
															<th width=20%>配額</th>
															<th width=15%>派工金額(未驗)</th>
<!-- 															<th width=15%>派工金額</th> -->
															<th width=15%>驗收金額</th>
															<th width=15% id="amountTips" title="配額 - 驗收金額 - Booking金額">可用餘額 <span><i class="fa fa-info-circle"></i></span></th>
														</tr>
													</thead>
													<tbody id="poQuotaTbody" >													
													<c:forEach items="${poQuotaDTOList}" var="poQuota">
														<tr>
															<td>${poQuota.poDomainName}</td>
															<td style="display:none;">${poQuota.poDomainId}</td>
															<td>
																<c:choose>
																	<c:when test="${poQuota.QUOTA==null}">
																	<input type="number" style="width:100%" min="0" value="0" />
																	</c:when>
																	<c:otherwise>
																	<input id="${poQuota.poDomainId}" name="${poQuota.poDomainId}" type="number" min="0" style="width:100%" value="${poQuota.QUOTA}" />
	                                                                </c:otherwise>
																</c:choose>
															</td>
															<td>
																<c:choose>
																	<c:when test="${poQuota.tempBookingQuota==null}">
																	0
																	</c:when>
																	<c:otherwise>
																	<fmt:formatNumber type="NUMBER">${poQuota.tempBookingQuota}</fmt:formatNumber>
	                                                                </c:otherwise>
																</c:choose>
															</td>
<!-- 															<td> -->
<%-- 																<c:choose> --%>
<%-- 																	<c:when test="${poQuota.OS_AMOUNT==null}"> --%>
<!-- 																	0 -->
<%-- 																	</c:when> --%>
<%-- 																	<c:otherwise> --%>
<%-- 																	<fmt:formatNumber type="NUMBER">${poQuota.OS_AMOUNT}</fmt:formatNumber> --%>
<%-- 	                                                                </c:otherwise> --%>
<%-- 																</c:choose> --%>
<!-- 															</td> -->
															<td>
																<c:choose>
																	<c:when test="${poQuota.AP_AMOUNT==null}">
																	0
																	</c:when>
																	<c:otherwise>
																	<fmt:formatNumber type="NUMBER">${poQuota.AP_AMOUNT}</fmt:formatNumber>
	                                                                </c:otherwise>
																</c:choose>
															</td>
															<td>
																<c:choose>
																	<c:when test="${poQuota.unUsedQuota==null}">
																	0
																	</c:when>
																	<c:otherwise>
																	<fmt:formatNumber type="NUMBER">${poQuota.unUsedQuota}</fmt:formatNumber>
	                                                                </c:otherwise>
																</c:choose>
															</td>
														</tr>
													</c:forEach>														
													</tbody>
												</table>
											</div>
										</td>
									</tr>
									<%--PO_LINE 只有正式PO 才可以變更--%>
									<c:if test="${poEvent ne 'insert'}">
										<tr>
										<td>
											<label class="col-sm-12 control-label">LINE ID 設定</label>
										</td>
									</tr>
									<tr>
										<td colspan="4" nowrap="nowrap">
											<div style="margin-left: 80px; margin-top: 5px;width :90%">
												<table id="domainAmountTable"
													class="table table-bordered  table-hover table-heading table-datatable">
													<thead>
														<tr>
															<th>大項</th>
															<th>LINE ID</th>
														</tr>
													</thead>
													<tbody>
														<c:forEach items="${poLineIdMapList}" var="lineIdMap" varStatus="status">
														<tr>
															<td width="50%">
																<c:out value="${lineIdMap.catName}"/>
																<input type="hidden" id="" class="lineIdMapCatId" value="${lineIdMap.CAT_ID}"/>
															</td>
															<td width="50%">
																<div class="col-sm-6">
																	<select id="poLineId_${status.count}" class="form-control" >
																		<option value="">---請選擇---</option>
																		<c:forEach items="${poLineIdList}" var="poLineId">
																			<option value="${poLineId.PO_LINE_ID}" <c:if test="${poLineId.PO_LINE_ID eq lineIdMap.PO_LINE_ID}">selected</c:if>>${poLineId.PO_NO} &nbsp;&nbsp;${poLineId.ITEM}</option>
																		</c:forEach>					
																	</select>
																</div>
															</td>
														</tr>
														</c:forEach>
													</tbody>
												</table>
											</div>
										</td>
									</tr>
									</c:if>
								</table>
								<input id="poEvent" value="${poEvent}" type="hidden" />								
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div id="showPOQueryFrame"></div>
		<input id="callBackFun" name="callBackFun" value="toMerger" type="hidden" />
	</body>
</html>