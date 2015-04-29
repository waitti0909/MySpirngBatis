<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>非工務專線申請處理</title>
<script type="text/javascript">
	$(document).ready(function() {
			$("#applyEvent").val("${applyEvent}");
			var showBtn = "${showBtn}";
			if (showBtn != "") {
				$("tr").attr("hidden", false);
			}
			//申請說明
		    <c:set var="lineApply_Desc" value="${siteLineApple.APP_DESC}" scope="request"></c:set>


			//電路類別帶出速率
			$("#LINE_TYPE").change(function() {
				getLineSpeed($("#LINE_TYPE").val(),"LINE_SPEED");
			 });
			
			//維護類別 連動事件
			$("#APP_TYPE").change(function() {
				$("#O_LINE_ID").prop("readonly", false);
				$("#LINE_ID").prop("readonly", false);
				var appType=$("#APP_TYPE").val();

				if(appType=='NEW')
				{
					$("#O_LINE_ID").prop("readonly", true);
				}else if(appType=='CAN')
				{
					$("#LINE_ID").prop("readonly", true);
				}
			 });
			
			//存檔按鈕事件
			$("#lineApplySaveBtn").click(function(){
				var msg = save();
				if(msg == "success"){
					if($("#applyEvent").val() == "new"){
						alert('<s:message code="msg.add.success"/>');
					}else{
						alert('<s:message code="msg.update.success"/>');
					}
					
					var data= {
	 					workId : "",//工單編號
	 					applicationId : $("#APP_ID").val(),//申請單號
	 					baseStationId : "",//基站編號
	 					btsSiteName : "",//基站名稱
	 					lineId : $.trim($("#LINE_ID").val()),//專線號碼
	 					applyDept : $("#APP_DEPT").val(),//申請單位
	 					appleType : $("#APP_TYPE").val(),//申請類別
	 					appleState : "",//申請狀態
	 					worksType : $("#VENDOR").val(),//業者別
	 					lineType : $("#LINE_STATUS").val(),//專線狀態
					};	
					$('#applyDept').val($("#APP_DEPT").val()).change();
					$('#appleType').val($("#APP_TYPE").val()).change();
					$('#worksType').val($("#VENDOR").val()).change();
					$('#lineType').val($("#LINE_STATUS").val()).change();
					$("#applicationId").val($("#APP_ID").val());
					$("#lineId").val($("#LINE_ID").val());
					$("#grid").setGridParam({page:1 ,datatype:"json", postData:data, mtype: 'POST'}).jqGrid().trigger("reloadGrid");
					$.fancybox.close();
				}else if(msg !=""){
					alert(msg);
					if ($('#addSiteWorkForm') != null && $('#addSiteWorkForm')[0] != null && $('#addSiteWorkForm')[0].checkValidity()) {
						event.preventDefault();
					}
				}

			});//存檔按鈕事件  end
			
			//匯出Excel
			$('#btnExcel').click(function() {			
                var $ifrm = $("<iframe style='display:none' />");
                $ifrm.attr("src", CONTEXT_URL + "/st/line/lineApply/getExcel?appId="+$("#APP_ID").val());
                $ifrm.appendTo("body");
                $ifrm.load(function () {
                   $("body").append(
                        "<div>Failed to download <i>'" + CONTEXT_URL + "/st/line/getExcel?appId="+$("#APP_ID").val() + "'</i>!");
                });
			});


			//重置按鈕
			$('#btnReset').click(function(){	
				$("#addLineApplyForm").trigger('reset');
			});	
		    //唯讀
			if("${status}" == "view"){
				$("#addLineApplyForm :input").prop("disabled", true);
				$('#btnExcel').prop("disabled", false);
			}
		    
			$('#lineCnsDate').datepicker({
				dateFormat : "yy/mm/dd",
				changeYear : true,
				changeMonth : true,
				showButtonPanel : true,
				showTime : false,
				minDate : 0
			});
			$('#lineStartDate').datepicker({
				dateFormat : "yy/mm/dd",
				changeYear : true,
				changeMonth : true,
				showButtonPanel : true,
				showTime : false,
				minDate : 0
			});
			$('#lineRentEndDate').datepicker({
				dateFormat : "yy/mm/dd",
				changeYear : true,
				changeMonth : true,
				showButtonPanel : true,
				showTime : false,
				minDate : 0
			});
			$('#lineEndDate').datepicker({
				dateFormat : "yy/mm/dd",
				changeYear : true,
				changeMonth : true,
				showButtonPanel : true,
				showTime : false,
				minDate : 0
			});
			
			//欄位初始狀態
			initState();
	});//document ready end 


		//欄位初始狀態
		function initState() {
			//申請人-名稱
			$("#showAppUser").text("${applyUserName}");
			//申請單位-名稱
			$("#showApplyDept").text("${applyUserDeptName}");

			//編輯時
			if ($("#applyEvent").val() == "edit") {
				//申請單號
				$("#APP_ID").val("${siteLineApple.APP_ID}");
				$("#showAppId").text("${siteLineApple.APP_ID}");
				//申請單位-ID
				$("#APP_DEPT").val("${siteLineApple.APP_DEPT}");
				//專線狀態
				$("#LINE_STATUS").val("${siteLineApple.LINE_STATUS}").change();
				//維護類別 
				$("#APP_TYPE").val("${siteLineApple.APP_TYPE}").change();
				//申請人-ID
				$("#APP_USER").val("${siteLineApple.APP_USER}");
				//舊專線號碼
				$("#O_LINE_ID").val("${siteLineApple.o_LINE_ID}");
				//乙端名稱
				$("#B_NAME").val("${siteLineApple.b_NAME}");
				//新專線號碼 
				$("#LINE_ID").val("${siteLineApple.LINE_ID}");
				//市話
				$("#B_TEL").val("${siteLineApple.b_TEL}");
				//聯單號碼
				$("#RCP_NUM").val("${siteLineApple.RCP_NUM}");
				//業者別
				$("#VENDOR").val("${siteLineApple.VENDOR}").change();
				//乙端地址 
				$("#B_ADDR").val("${siteLineApple.b_ADDR}");
				//預計施工日  
				$("#lineCnsDate").val("${siteLineApple.lineCnsDate}");
				//電路用途
				$("#LINE_PURPOSE").val("${siteLineApple.LINE_PURPOSE}").change();
				//乙端聯絡人
				$("#CNT_PSN").val("${siteLineApple.CNT_PSN}");
				//竣工日期 
				$("#lineStartDate").val("${siteLineApple.lineStartDate}");
				//電路類別 
				$("#LINE_TYPE").val("${siteLineApple.LINE_TYPE}").change();
				//乙端聯絡電話
				$("#CNT_TEL").val("${siteLineApple.CNT_TEL}");
				//租期迄日 
				$("#lineRentEndDate").val("${siteLineApple.lineRentEndDate}");
				//網路種類 
				$("#LINE_SPEED").val("${siteLineApple.LINE_SPEED}").change();
				//費用
				$("#MON_FEE").val("${siteLineApple.MON_FEE}");
				//退租日期  
				$("#lineEndDate").val("${siteLineApple.lineEndDate}");
			} else {
				//EXCEL按鈕
				$("#btnExcel").prop("disabled", true);
			}
		}

		//取得設備機型
		function getLineSpeed(lineType,tagName){
			$.ajax({
				url : CONTEXT_URL + "/st/line/lineApply/search/lineSpeed",
				type : 'POST',
				data : {
					"circuitType" : lineType,
				},
				async : false,
				success : function(datas){
					$('#'+tagName).html("");
					for(var data in datas){
						$('<option value="'+datas[data].value+'">'+datas[data].label+'</option>').appendTo('#'+tagName);
					}
				}					
	       	});
		}

		//存檔
		function save(){
			var resultMessage = "";
			if ($('#addLineApplyForm') !=null && $('#addLineApplyForm')[0] !=null && $('#addLineApplyForm')[0].checkValidity()) {
				event.preventDefault();
				
				var monFee= $("#MON_FEE").val();
				if(monFee!="")
				{
					if(!/^\d+$/.test(monFee)){
						return"費用，只可輸入數字。";
					}
				}                   
				var status=$("#LINE_STATUS").val();
			
				if(status=='L02'){
					if($("#lineStartDate").val()==""){
						return "竣工日期必須選擇";
					}
				}
				
				if(status=='L04'){
					if($("#lineEndDate").val()==""){
						return "退租日期必須選擇";
					}
				}
				//檢核作業說明字數
				if($("#APP_DESC").val().length >100){
					return "作業說明超過100個字";
				}

				if($("#applyEvent").val() == "new"){
					if(!confirm("確定要儲存?")){
						return false;
					}
				}
				$.ajax({
					url : CONTEXT_URL + "/st/line/lineApply/saveByNonLine",
					type : 'POST',
					async : false,
					data : $("#addLineApplyForm").serialize()+"&applyEvent="+$("#applyEvent").val(),
					success : function(data){
						var result = data['result'];
						if(result == true){
							if($("#applyEvent").val() == "new"){
								var lineApply = data['lineApply'];
								$("#APP_ID").val(lineApply.app_ID);
								$("#showAppId").text(lineApply.app_ID);
							}
							resultMessage="success";
						}else {
							resultMessage = result;
						}
					}
		       	});
			}
			return resultMessage;
		}//save end
</script>
</head>
<body>
	<div class="row">
		<div class="col-xs-12 col-sm-12">
			<div class=" ui-draggable ui-droppable">
				<div class="box-header">
					<div class="box-name">
						<i class="fa fa-search"></i> <span>專線申請處理</span>
					</div>
					<div class="no-move"></div>
				</div>
				<div class="box-content">
					<div class="box-content">
						<form class="form-horizontal" id="addLineApplyForm" action="">

							<table style="width: 100%">
								<tr hidden="true">
										<td nowrap="nowrap" align='right'>
											<button class="btn btn-primary btn-label-left" style="margin-right: 10px" type="submit" id="lineApplySaveBtn"><span><i class="fa fa-save"></i></span>存檔</button>
										</td>
										<td nowrap="nowrap">
											<button class="btn btn-default btn-label-left" type="button" id="btnReset" style="margin-right: 10px">
												<span><i class="fa fa-undo txt-danger"></i></span>重置
											</button>
											<button class="btn btn-primary btn-label-left" type="button"  id="btnExcel"><span><i class="fa fa-cloud-download"></i></span>匯出Excel</button>
										</td>
										<td>
										</td>
								</tr>
								<tr>
								    <td width="10%" nowrap="nowrap"><label class="col-sm-12 control-label">申請單號 :</label></td>
									<td width="20%">
										<p style="padding-top:15px; padding-left:15px;" id="showAppId"></p>
										<input id="APP_ID" name="APP_ID" type="hidden"></input>
									</td>
									
									<td width="15%" nowrap="nowrap"><label class="col-sm-12 control-label" >申請單位 :</label></td>
									<td width="20%">
									 	<p style="padding-top:15px; padding-left:15px;" id="showApplyDept"></p>
										<input id="APP_DEPT" name="APP_DEPT" type="hidden"></input>
									</td>
									
									<td width="10%"><label class="col-sm-12 control-label" >專線狀態 :</label></td>
									<td width="20%">
										<div class="col-sm-10">
										   <select  id="LINE_STATUS" class="form-control" name="LINE_STATUS" >
<!-- 												<option value="">---請選擇---</option> -->
												<c:forEach items="${lineStatusList}" var="lineStatus">
													<option value="${lineStatus.value}">${lineStatus.label}</option>
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
									<td><label style="padding-left:5px;" class="col-sm-12 control-label"><span class="s">* </span>維護類別 :</label></td>
									<td>											
										<div class="col-sm-10">
											<select id="APP_TYPE" name="APP_TYPE" class="form-control" >
<!-- 												<option value="">---請選擇---</option> -->
												<c:forEach items="${appleTypeList}" var="appleType">
													<option value="${appleType.value}">${appleType.label}</option>
												</c:forEach>
											</select>
										</div>
									</td>
									
									<td width="15%" nowrap="nowrap"><label class="col-sm-12 control-label" >申請人 :</label></td>
									<td width="20%">
									 	<p style="padding-top:15px; padding-left:15px;" id="showAppUser"></p>
										<input id="APP_USER" name="APP_USER" type="hidden"></input>
									</td>
									
									<td><label style="padding-left:5px;" class="col-sm-12 control-label">申請時間 :</label></td>
									<td>
										<fmt:formatDate value="${siteLineApple.APP_TIME}" pattern="yyyy/MM/dd HH:mm" var="APP_TIME" />
				                    	<p style="padding-top: 15px; padding-left: 5px;" id="showAppTime">
					                		<c:out value="${APP_TIME}" />
				                   		</p>
									</td>
								</tr>
								<tr>
									<td>
										<div class="clearfix">&nbsp;</div>
									</td>
								</tr>
								<tr>
								  <td><label class="col-sm-12 control-label">舊專線號碼 :</label></td>
									<td nowrap="nowrap">
										<div class="col-sm-10">
											<input id="O_LINE_ID" name="O_LINE_ID"  type="text" class="form-control" maxlength="20">
										</div>
									</td>
									
									<td><label class="col-sm-12 control-label">乙端名稱 :</label></td>
									<td nowrap="nowrap">
										<div class="col-sm-10">
											<input id="B_NAME" name="B_NAME"  type="text" class="form-control" maxlength="30">
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="clearfix">&nbsp;</div>
									</td>
								</tr>
								<tr>
								   <td><label class="col-sm-12 control-label">新專線號碼 :</label></td>
									<td nowrap="nowrap">
										<div class="col-sm-10">
											<input id="LINE_ID" name="LINE_ID"  type="text" class="form-control" maxlength="20">
										</div>
									</td>
									
									<td><label class="col-sm-12 control-label">市話 :</label></td>
									<td nowrap="nowrap">
										<div class="col-sm-10">
											<input id="B_TEL" name="B_TEL"  type="text" class="form-control" maxlength="15">
										</div>
									</td>
									
									<td><label class="col-sm-12 control-label">聯單號碼 :</label></td>
									<td nowrap="nowrap">
										<div class="col-sm-10">
											<input id="RCP_NUM" name="RCP_NUM"  type="text" class="form-control" maxlength="20">
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="clearfix">&nbsp;</div>
									</td>
								</tr>
								<tr>
									<td><label class="col-sm-12 control-label">業者別 :</label></td>
									<td>	
										<div class="col-sm-10">
											<select id="VENDOR" name="VENDOR" class="form-control" >
<!-- 											    <option value="">---請選擇---</option> -->
												<c:forEach items="${allWorkType}" var="workType">
													<option value="${workType.value}">${workType.label}</option>
												</c:forEach>
											</select>
										</div>
									</td>
									
									<td><label class="col-sm-12 control-label">乙端地址 :</label></td>
									<td nowrap="nowrap">
										<div class="col-sm-12">
											<input id="B_ADDR" name="B_ADDR"  type="text" class="form-control" maxlength="80">
										</div>
									</td>
									
									<td><label class="col-sm-12 control-label">預計施工日 :</label></td>
									<td>
										<div class="col-sm-10" >
										    <input id="lineCnsDate" name="lineCnsDate" type="text" class="form-control" placeholder="點選輸入框" readonly="readonly"></input>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="clearfix">&nbsp;</div>
									</td>
								</tr>
								<tr>
						            <td><label class="col-sm-12 control-label">電路用途 :</label></td>
									<td>	
										<div class="col-sm-10">
											<select id="LINE_PURPOSE" name="LINE_PURPOSE" class="form-control" >
												<c:forEach items="${purposeList}" var="purpose">
													<option value="${purpose.value}">${purpose.label}</option>
												</c:forEach>
											</select>
										</div>
									</td>
									<td><label class="col-sm-12 control-label">乙端聯絡人 :</label></td>
									<td nowrap="nowrap">
										<div class="col-sm-10">
											<input id="CNT_PSN" name="CNT_PSN"  type="text" class="form-control" maxlength="20">
										</div>
									</td>
									
									<td><label class="col-sm-12 control-label">竣工日期 :</label></td>
									<td>
										<div class="col-sm-10" >
										    <input id="lineStartDate" name="lineStartDate" type="text" class="form-control" placeholder="點選輸入框" readonly="readonly"></input>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="clearfix">&nbsp;</div>
									</td>
								</tr>
								<tr>
								    <td><label class="col-sm-12 control-label">電路類別 :</label></td>
									<td>	
										<div class="col-sm-10">
											<select id="LINE_TYPE" name="LINE_TYPE" class="form-control" >
												<c:forEach items="${lineTypeList}" var="lineTypeType">
													<option value="${lineTypeType.value}">${lineTypeType.label}</option>
												</c:forEach>
											</select>
										</div>
									</td>
									
									<td><label class="col-sm-12 control-label">乙端聯絡電話 :</label></td>
									<td nowrap="nowrap">
										<div class="col-sm-10">
											<input id="CNT_TEL" name="CNT_TEL"  type="text" class="form-control" maxlength="20">
										</div>
									</td>

									<td><label class="col-sm-12 control-label">租期迄日 :</label></td>
									<td>
										<div class="col-sm-10" >
											 <input id="lineRentEndDate" name="lineRentEndDate" type="text" class="form-control" placeholder="點選輸入框" readonly="readonly"></input>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="clearfix">&nbsp;</div>
									</td>
								</tr>
								<tr>
									<td><label class="col-sm-12 control-label">網路種類 :</label></td>
									<td>	
										<div class="col-sm-10">
											<select id="LINE_SPEED" name="LINE_SPEED" class="form-control" >
												<c:forEach items="${lineSpeedList}" var="lineSpeed">
													<option value="${lineSpeed.value}">${lineSpeed.label}</option>
												</c:forEach>
											</select>
										</div>
									</td>
									
									<td><label class="col-sm-12 control-label">費用(月):</label></td>
									<td nowrap="nowrap">
										<div class="col-sm-10">
											<input id="MON_FEE" name="MON_FEE"  type="text" class="form-control" maxlength="10">
										</div>
									</td>
									
									<td><label class="col-sm-12 control-label">退租日期 :</label></td>
									<td>
										<div class="col-sm-10" >
											 <input id="lineEndDate" name="lineEndDate" type="text" class="form-control" placeholder="點選輸入框" readonly="readonly"></input>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="clearfix">&nbsp;</div>
									</td>
								</tr>
								<tr>
									<td valign='top' width="10%" nowrap="nowrap"
										style="padding-top: 15px;"><label
										class="col-sm-12 control-label">申請說明 :</label></td>
									<td colspan="5" nowrap="nowrap">
										<div style="margin-top: 15px">
											<textarea id="APP_DESC" name="APP_DESC"
												style="resize: none; width: 94%; border-left-width: 1px; margin-left: 15px; margin-top: 4px;"
												rows="3"><c:if test="${lineApply_Desc != null}">${lineApply_Desc}</c:if></textarea>
										</div>
									</td>
								</tr>
							</table>
							
							<input id="applyEvent"  type="hidden"></input>
							<input id="APP_STATUS" name="APP_STATUS" type="hidden" value="LA06" /><%-- add by Charlie 20150127 default to LA06 --%>
						</form>
						<form id="toExcel"  action="" method="post"> 
						    <input id="appId" name="appId" type="hidden"></input>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>