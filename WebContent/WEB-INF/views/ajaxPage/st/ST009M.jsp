<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<title>單張工單作業申請</title>
		<script type="text/javascript">
		var selectBtsSiteIdEvent = false;
		$(document).ready(function() {	
			
			buildCityOptions("siteHuntCity","siteHuntArea");
			$("#singleOrderApplyEvent").val("${singleOrderApplyEvent}");
			$("#fileDownloadsBtn").hide();
			initForm();
			
			//是否為簽核頁面
			if("${isApplyPage}" == "Y"){
				//alert("${isApplyPage}");
				$("#fileProcessBtn").prop("disabled", true);
				$("#siteBuildApplyBtn").prop("disabled", true);
				$("#cancelApplyBtn").prop("disabled", true);
				$("#siteWorkSaveBtn").prop("disabled", true);		   
				//$("#fileDownloadsBtn").show().prop("disabled",false);
			}
			
			//檔案上傳事件
			$("#fileProcessBtn").click(function() {
				openFileDialogFrame("showFileUploadPage",$("#siteWorkType").val(),$("#workId").val(),"NOTYPE");
			});
			
			$("#fileDownloadsBtn").click(function() {
				openFileDownFrame("showFileUploadPage",$("#siteWorkType").val(),$("#workId").val(),"NOTYPE");
			});
			
			//工務類別
 			$("#showWorkType").val("${showWorkType}");
			if($("#showWorkType").val() == "SGL"){
				$("#siteWorkType").val($("#showWorkType").val());
				$("#showWorkType").text("單張工單");
			}	
			
			//處理狀態
			var workStatus = $("#showWorkStatus").text();
			if(workStatus == ""){
				$("#siteHuntWorkStatus").val("W01");
				$("#showWorkStatus").text("草稿");
			}
			
			if ($("#singleOrderApplyEvent").val() == "insert") {
				$("#cancelApplyBtn").prop("disabled", true);
			}
			
			//欄位初始狀態
			initState();
			

			
			//儲存
			$("#siteWorkSaveBtn").click(function(){
/* 				console.log($("#addSiteWorkForm").serialize());
				return false; */
				console.log($("#addSiteWorkForm").serialize());
				if ($('#addSiteWorkForm')[0].checkValidity()) {
					event.preventDefault();
					if(!confirm("確定要儲存?")){
						return false;
					}
					var orderId="";
					if($("#singleOrderApplyEvent").val() == "edit"){
						orderId=$("#checkBoxItemId").val();
					}					
					var errorMessages = doValidate(orderId);
					var msg ="";
					if(errorMessages.length!=0){
						for(var em in errorMessages){
							msg += errorMessages[em]+"\n";
						}
					}
					if(msg != ""){
						alert(msg);
						return false;
					}
					//saveEnableTag();
					
					var attr="";
					if($("#siteBuildEqpType").val() == null){
						attr = "&eqpTypeId="+$("#siteCallbackFunEqpType").val();
					}
					$.ajax({
						url : CONTEXT_URL + "/st/st009/save",
						type : 'POST',
						async : false,
						data : $("#addSiteWorkForm").serialize()+"&singleOrderApplyEvent="+$("#singleOrderApplyEvent").val()+"&orderId="+orderId+attr,
						success : function(data){
							var result = data['result'];
							if(result == true){
								if($("#singleOrderApplyEvent").val() == "insert"){
									var siteWork = data['siteWork'];
									$("#workId").val(siteWork.work_ID);
									$("#siteHuntSiteId").val(siteWork.bts_SITE_ID);
									alert('<s:message code="msg.add.success"/>' + '，請將作業工單後送出申請');
								}else{
									alert('<s:message code="msg.update.success"/>');
									var data= {
										    workType : $.trim($("#siteWorkType").val()), //工務類別
										    appDept : "", //申請單位
											//siteWorkCity : $.trim($("#city").val()), //行政區 
											//siteWorkArea : $.trim($("#area").val()), //行政區 
											btsSiteId : $.trim($("#siteHuntSiteId").val()), //基站編號 
											siteName : $.trim($("#siteNameData").val()), //基站名稱
											workStatus :$("#siteHuntWorkStatus").val(), //處理狀態
											siteFeq : $.trim($("#siteFeqId").val()), //基站頻段
											addr : $('#siteAddr').val(), //地址
											priority : $('#priority').val(), // 重要等級
											lon : $.trim($('#singleLon').val()), //經度
											lat : $.trim($('#singleLat').val()), //緯度
											siteOrderType : $('#siteOrderType').val(), //工單類別
											siteResponsibleUnit : $('#siteResponsibleUnit').val(), //負責單位
											allSiteStatus : $('#allSiteStatus').val(), //負責單位
											//prediceEndDate: $('#prediceEndDate').val(), //預計完工日
											workDesc : $('#workDesc').val(), //作業說明
											locationId : $("#locationId").val(),
											siteBuildSiteId :$("#siteBuildSiteId").val(),
											startSiteStatus : $("#startSiteStatus").val(),//基站狀態
											rangeBa:"",
											rangeBb:""
									};	
/* 									$('#siteWorkType').val($("#siteWorkType").val());
									$('#priority').val($("#priority").val()).change();
									$('#siteHuntSiteId').val($("#siteHuntSiteId").val());
									$('#siteNameData').val($("#siteNameData").val());
									$('#siteFeqId').val($("#siteFeqId").val());
									$("#siteAddr").val($("#siteAddr").val());
									$("#lon").val($("#lon").val());
									$("#lat").val($("#lat").val());
									$('#siteOrderType').val($("#siteOrderType").val()).change();
									$("#siteResponsibleUnit").val($("#siteResponsibleUnit").val());
									$("#prediceEndDate").val($("#prediceEndDate").val()); */
									

									$("#grid").setGridParam({page:1 ,datatype:"json", postData:data, mtype: 'POST'}).jqGrid().trigger("reloadGrid");
									//$.fancybox.close();
									updateSiteWork($("#workId").val(), "edit");
								}
							}else {
								alert(result);
							}
						}					
			       	});
				}
			});//save butoon end
			
			//申請按鈕事件
			$("#siteBuildApplyBtn").click(function(){
				if ($('#addSiteWorkForm')[0].checkValidity()) {
					event.preventDefault();
					if(!confirm("送出申請後將無法再修改資料，是否繼續？")){
						return false;
					}
					var orderId="";
					if($("#singleOrderApplyEvent").val() == "edit"){
						orderId=$("#checkBoxItemId").val();
					}
					var errorMessages = doValidate(orderId);
					var msg ="";
					if(errorMessages.length!=0){
						for(var em in errorMessages){
							msg += errorMessages[em]+"\n";
						}
					}
					if(msg != ""){
						alert(msg);
						return false;
					}
					
					var attr="";
					if($("#siteBuildEqpType").val() == null){
						//alert($("#siteCallbackFunEqpType").val());
						//return false;
						attr = "&eqpTypeId="+$("#siteCallbackFunEqpType").val();
					}
					
					var orderIdArray = [];
					if($("#singleOrderApplyEvent").val() == "update"){
						$('#workOrderTable tbody').each(function () {
							var data = $('#workOrderTable').dataTable().fnGetData($(this).index()-1);
							orderIdArray [orderIdArray.length]= data['order_ID'];
						});
					}
					
					 $.ajax({
						 url : CONTEXT_URL + "/st/st009/updateApply",
						 type : 'POST',
						 async : false,
						 data: $("#addSiteWorkForm").serialize()+"&singleOrderApplyEvent="+$("#singleOrderApplyEvent").val()+"&orderId="+orderId+attr+"&orderIdArray="+orderIdArray,
						 success : function(datas){
						 //saveDisableTag();
						 var result = datas['result'];
						 if(result == true){
						   var siteWork = datas['siteWork'];
						   alert('<s:message code="msg.apply.success"/>');
						   $("#workStatus").val(siteWork.work_STATUS).change();
						   $("#applyDept").val(siteWork.app_DEPT).change();
						   var data= {
								    workType : $.trim($("#siteBuildWorkType").val()), //工務類別
								    appDept : "", //申請單位
									siteWorkCity : $.trim($("#city").val()), //行政區 
									siteWorkArea : "", //行政區 
									siteHuntEqpTemp : $.trim($("#siteBuildEqpType").val()), //設備型態 
									btsSiteId : $.trim($("#siteBuildBtsSiteId").val()), //基站編號 
									siteName : $.trim($("#siteBuildSiteName").val()), //基站名稱
									workStatus :siteWork.work_STATUS, //處理狀態
									comSiteFeq : $.trim($("#feqId").val()), //基站頻段 
									rangeBa:"",
									rangeBb:""
								     };
							$('#workType').val($("#siteBuildWorkType").val()).change();
							$('#siteWorkCity').val($("#city").val()).change();
					// 		$('#siteWorkArea').val($("#area").val()).change();;
							$('#siteFeq').val($("#feqId").val()).change();
							$('#workStatus').val(siteWork.work_STATUS).change();
							$("#siteWorkBtsSiteId").val($("#siteBuildBtsSiteId").val());
							$("#siteWorkSiteName").val($("#siteBuildSiteName").val());

							if ($("#allSiteStatus").val() == "S11") {								
								$("#siteStatusValue").val("S11");
							}
							
							$.fancybox.close();
							$("#grid").setGridParam({page:1 ,datatype:"json", postData:data, mtype: 'POST'}).jqGrid().trigger("reloadGrid");
						}else{
							alert(result);
							}
					   }					
					});					
				}
			});//申請按鈕事件  end
			
			  //取消申請事件
			$("#cancelApplyBtn").click(function (){
				if(confirm("取消申請後將無法再修改資料，是否繼續？")){
					var workStatus = $("#siteHuntWorkStatus").val();
					//alert(workStatus);
					if(workStatus != "W01" && workStatus != "W04"){
						alert("此處理狀態無法取消申請");
						return false;
					}
					$.ajax({
							url : CONTEXT_URL + "/st/st009/cancelApply",
							type : 'POST',
							data:{
									workId :  $("#workId").val()
							},
							async : false,
							success : function(record) {
							var result = record['result'];
							if(result == true){
							var siteWork = record['siteWork'];
							alert('<s:message code="msg.cancelApply.success"/>');
							var data= {
								    workType : $.trim($("#siteBuildWorkType").val()), //工務類別
									applyDept :siteWork.APP_DEPT, //申請單位
									//siteWorkCity : $.trim($("#city").val()), //行政區 
									//siteWorkArea : "", //行政區 
									eqpTypeId : $.trim($("#siteBuildEqpType").val()), //設備型態 
									btsSiteId : $.trim($("#siteBuildBtsSiteId").val()), //基站編號 
									siteName : $.trim($("#siteBuildSiteName").val()), //基站名稱
									workStatus :siteWork.work_STATUS, //處理狀態
									siteFeq : $.trim($("#siteFeq").val()),
									rangeBa:"",
									rangeBb:""
								
									};
							$('#workType').val($("#siteBuildWorkType").val()).change();

							$('#siteFeq').val($("#feqId").val()).change();
							$('#workStatus').val(siteWork.work_STATUS).change();
							$("#siteWorkBtsSiteId").val($("#siteBuildBtsSiteId").val());
							$("#siteWorkSiteName").val($("#siteBuildSiteName").val());
							$("#grid").setGridParam({page:1,datatype:"json", postData:data, mtype: 'POST'}).jqGrid().trigger("reloadGrid");
							$.fancybox.close();
						}else {
								alert(result);
						       }
	
						}						
					});
				}
			});
			
/* 			function saveEnableTag(){
				$("#feqId").prop("disabled", false);
				$("#siteBuildEqpType").prop("disabled", false);	
				$("#siteBuildWorkType").prop("disabled", false);
				$("#feederless").prop("disabled", false);
				$("#coverageType").prop("disabled", false);
			} */
			

			
			if("${status}" == "view"){
				//alert("${status}");
				$("#addSiteWorkForm :input").prop("disabled", true);
				$("#fileDownloadsBtn").show().prop("disabled",false);
			}
		});//document ready end 
		
		function disableBtsSiteIdText(){
			var siteFeq = $("#feqId").val();
			var siteFeqArray = siteFeq.split(",");
			if(siteFeqArray[1] == "L" && $("#siteHuntEqpType").val() == 2){
				$("#siteHuntBtsSiteId").prop("readonly",true).val("");
				$("#unuseBtsSiteIdBtn").prop("disabled",true);
			}else{
				$("#siteHuntBtsSiteId").prop("readonly",false);
				$("#unuseBtsSiteIdBtn").prop("disabled",false);
			}

		}

		function unuseBtsSiteIdCallBackFun(object){
			$("#feqId").val("").change();
			$("#siteHuntEqpType").val("").change();
			var obj = JSON.parse(object);
			$("#siteHuntBtsSiteId").val(obj.bts_SITE_ID);
			$("#feqId").val($("#unuseBtsSiteIdSiteFeq").val()).change();
			$("#siteHuntEqpType").val($("#unuseBtsSiteIdEqpType").val()).change();
			$("#coverageType").val($("#unuseBtsSiteIdCoverageType").val()).change();
			if($("#siteHuntEqpType").val() == "5"){
				$("#feederless").val("R").change();	
			}
// 			$("#siteHuntBtsSiteId").prop("readonly",true);
		}
		function initForm(){
// 			$("#addSiteWorkForm :input ").prop("disabled", false);
			$("#siteHuntCity").prop("disabled",true);
			$("#siteHuntArea").prop("disabled",true);
			$("#multibandLocation").prop("disabled",true);
			$("#donorSiteText").prop("readonly",true);
			$("#masterSiteText").prop("readonly",true);
			$("#siteHuntLon").prop("readonly",true);
			$("#siteHuntLat").prop("readonly",true);
			if("${siteWork.START_SITE_STATUS}" == "S02" || "${siteWork.START_SITE_STATUS}" == "S04") {
				$("#siteStatusValue").val("S11");
				$("#allSiteStatus").change().prop("disabled", true);
			}
		}
		function getSiteFeq(feqType,tagName){
			$.ajax({
				url : CONTEXT_URL + "/st/st002/search/siteFeq",
				type : 'POST',
				data : {
					"feqType" :feqType,
				},
				async : false,
				success : function(datas){
					$('#'+tagName).html("");
					$('<option value="">---請選擇---</option>').appendTo('#'+tagName);
					for(var data in datas){
						$('<option value="'+data+'">'+datas[data]+'</option>').appendTo('#'+tagName);
					}
				}					
	       	});
		}

		
		function doValidate(orderId){
			//預計完工日 
			var prediceEndDate = $("#prediceEndDate").val();
			var errorMessages = [];
			//檢核預計完工日 
			if(prediceEndDate == "" ){
				errorMessages[errorMessages.length]="預定完成日必須選擇";
			}
			//檢核作業說明字數
			if($("#workDesc").val().length >100){
				errorMessages [errorMessages.length]="作業說明超過100個字";
			}
			if($("#siteHuntSiteId").val() == "") {
				errorMessages[errorMessages.length]="基站編號必須選擇";
			}
			//工單類別
			if($("#siteOrderType").val()==""){
				errorMessages[errorMessages.length]="工單類別必須選擇";
			}

			var lon = $("#singleLon").val();
			if(!validateNumber(lon,3,6)){
				errorMessages [errorMessages.length]="經度格式錯誤，整數最多三位、小數最多為六位";
			}
			var lat = $("#singleLat").val();
			if(!validateNumber(lat,2,6)){
				errorMessages [errorMessages.length]="緯度格式錯誤，整數最多二位、小數最多為六位";
			}

			var msg =  validateLatAndLon(lon,lat,"");
			if(msg != "" && msg != null){
				errorMessages [errorMessages.length]=msg;
			}
			return  errorMessages;
		}
		
		function initOrderType() {			
			//變更 工單類別 塞值 到 物件裡
			$("#siteOrderType").change(function() {
				$("#odTypeId").val($("#siteOrderType").val());
				//if ($("#siteOrderType").val() != "") {	
					if($("#siteHuntSiteId").val() != "") {				
						//alert("123");
						osResponsibleUnit();
					} 

/* 				} else {
					$('#siteOrderType').val("").change();
					$("#showResponsibleUnit").text("");
					$("#siteResponsibleUnit").val("");
				} */
			});
		}
		
		//完成基站狀態
		//alert($("#startSiteStatus").val());
		function osSiteStatus(){
			//alert($("#startSiteStatus").val());
			if($("#startSiteStatus").val() == "S02" || $("#startSiteStatus").val() == "S04") {
				//當 基站起始狀態為 待尋點 、待建設時，完工基站 預設成 拆站 不可修改
				$("#allSiteStatus").val("S11").change();
				$("#siteStatusValue").val("S11");
				$("#allSiteStatus").change().prop("disabled", true);
				//alert($("#startSiteStatus").val());
			} else {
				$("#allSiteStatus").change().prop("disabled", false);
			}			
		}
		
		//負責單位
		function osResponsibleUnit(){		
			$.ajax({
				url : CONTEXT_URL + "/st/st009/responsibleUnit",
				type : 'POST',
				data : {
					odTypeId :$("#odTypeId").val(),
					eqpType :$("#eqpType").val(),
					city : $("#locCity").val(),
					area : $("#locArea").val()
				},
				async : false,
				success : function(data){
					//console.log(data);
					//alert(JSON.stringify(data));
					if(data.success){
						//alert(data.msg);
						if (data.msg == 'failed') {
							if ($('#siteOrderType').val() != "") {								
								alert("查無 負責單位!");
							}
							$("#showResponsibleUnit").text("");
							$("#siteResponsibleUnit").val("");
							return false;
						} else {
							$("#showResponsibleUnit").text(data.row.dept_NAME);
							$("#siteResponsibleUnit").val(data.row.dept_ID);				
						}
					}
				}
			});			
		}
		
		//工單類別
		function osSiteOrderType(){
			//$("#siteHuntSiteId").change(function() {	
				$.ajax({
					url : CONTEXT_URL + "/st/st009/siteOrderType",			
					type : "POST",
					dataType : 'json',
					data : {
						eqpTypeId :$('#eqpType').val(),
						siteStatus : $('#startSiteStatus').val(),
					},
					async : false,
					success : function(data) {
						if(data.success){
							if(data.rows.length > 0){
								$("#siteOrderType  option").remove();
								$("#siteOrderType").append("<option value=''>--請選擇--</option>");
								//alert(JSON.stringify(data.rows));
								for(var i=0; i<data.rows.length; i++){		
									$("#siteOrderType").append("<option value="+data.rows[i].od_TYPE_ID+">"+data.rows[i].od_TYPE_NAME+"</option>");
								}			
							}
						}
					}
				});
			//});
		}

		
		//欄位初始狀態
		function initState() {			
			//編輯時
			if ($("#singleOrderApplyEvent").val() == "edit") {
				osSiteOrderType();
				//工單
				$("#checkBoxItemId").val("${orderIdStr}");
				//工單編號欄位
				$("#showWorkId").text("${siteWork.WORK_ID}");
				$("#workId").val("${siteWork.WORK_ID}");
				//工務類別
				$("#showWorkType").text("單張工單");
				$("#siteWorkType").val("${siteWork.WORK_TYPE}");
				//重要等級
				$("#priority").val("${siteWork.PRIORITY}").change();
				//申請人原
				$("#showApplyUser").text("${applyUserName}");
				$("#applyUser").val("${siteWork.APL_USER}");

				//多頻段站點 
				if ("${isMultibandLocation}" > 1) {
					$("#multibandLocation").prop("checked", true);
				}
				//處理狀態
				$("#showWorkStatus").text("${workStatusName}");
				$("#siteHuntWorkStatus").val("${siteHuntWorkStatus}");
				
				//地址相關欄位
				$("#showAddr").text("${siteWork.ADDR}");
				$("#siteAddr").val("${siteWork.ADDR}");
				
				$("#zip").val("${siteWork.ZIP}");
				$("#city").val("${siteWork.CITY}");
				$("#area").val("${siteWork.AREA}");
				$("#locCity").val("${siteWork.CITY}");
				$("#locArea").val("${siteWork.AREA}");
				$("#village").val("${siteWork.VILLAGE}");
				$("#road").val("${siteWork.ROAD}");
				$("#adjacent").val("${siteWork.ADJACENT}");
				$("#lane").val("${siteWork.LANE}");
				$("#alley").val("${siteWork.ALLEY}");
				$("#sub_ALLEY").val("${siteWork.SUB_ALLEY}");
				$("#door_NO").val("${siteWork.DOOR_NO}");
				$("#door_NO_EX").val("${siteWork.DOOR_NO_EX}");
				$("#floor").val("${siteWork.FLOOR}");
				$("#floor_EX").val("${siteWork.FLOOR_EX}");
				$("#room").val("${siteWork.ROOM}");
				$("#add_OTHER").val("${siteWork.ADD_OTHER}");
				//經度
				$("#showLon").text("${siteWork.SR_LON}");
				$("#singleLon").val("${siteWork.SR_LON}");
				//緯度
				$("#showLat").text("${siteWork.SR_LAT}");
				$("#singleLat").val("${siteWork.SR_LAT}");
				//基站編號
				$("#siteHuntSiteId").val("${siteWork.BTS_SITE_ID}");
				//基站頻段
				$("#showFeqId").text("${siteFeqName}");
				$("#siteFeqId").val("${siteWork.FEQ_ID}");
				//基站名稱
				$("#showSiteName").text("${siteWork.SITE_NAME}");
				$("#siteNameData").val("${siteWork.SITE_NAME}");
				//預計完工日
				$("#prediceEndDate").val("${prediceEndDate}");
				//完工日期
				$("#showEndTime").text("${siteWorkEndTime}");
				$("#endTime").val("${siteWorkEndTime}");		
				//工單狀態
				$("#siteOrderType").val("${siteOrderType}");
				initOrderType();
				$("#siteOrderType").trigger("change");
				//負責單位
				//$("#showResponsibleUnit").text("${siteResponsibleUnit}");
				//$("#siteResponsibleUnit").val("${siteResponsibleUnit}");
				
				$("#locationId").val("${locationId}");
				$("#siteBuildSiteId").val("${siteBuildSiteId}");
				$("#eqpType").val("${siteWork.EQP_TYPE_ID}");
				
				$("#allSiteStatus").val("${siteWork.END_SITE_STATUS}");
				$("#startSiteStatus").val("${siteWork.START_SITE_STATUS}");
			} else {
				//作業附件按鈕
				$("#fileProcessBtn").prop("disabled", true);
				//申請按鈕
				//$("#siteBuildApplyBtn").prop("disabled", true);
				initOrderType();
			}
		}
		

		</script>
	</head>
	<body>
		<div class="row">
			<div class="col-xs-12 col-sm-12">
				<div class=" ui-draggable ui-droppable">
					<div class="box-header">
						<div class="box-name">
							<i class="fa fa-search"></i> <span>單張工單作業申請</span>
						</div>
						<div class="no-move"></div>
					</div>
					<div class="box-content">
						<div class="box-content">
							<form class="form-horizontal" id="addSiteWorkForm" action="" >
								<button class="btn btn-primary btn-label-left" style="margin-left: 30px"
									type="submit" id="siteWorkSaveBtn"><span><i class="fa fa-save"></i></span>存檔</button>
								<button class="btn btn-primary btn-label"
									style="margin-right: 10px" type="button" id="cancelApplyBtn">
									取消申請</button>
								<button class="btn btn-primary btn-label"
									style="margin-right: 10px" type="button" id="fileProcessBtn">
									作業附件</button>
								<button class="btn btn-primary btn-label"
									style="margin-right: 10px" type="button" id="siteBuildApplyBtn">
									申請</button>		
								<button class="btn btn-primary btn-label"
									style="margin-right: 10px" type="button" id="fileDownloadsBtn">
									附件下載</button>															
								<jsp:include page="/WEB-INF/views/ajaxPage/st/SingleOrderApply.jsp"></jsp:include>
								<input id="siteCallBackFun" value="siteCallBackFun" type="hidden"></input>
								<input id="donorSiteCallBackFun" value="donorSiteCallBackFun" type="hidden"></input>
								<!-- <input id="masterSiteCallBackFun" value="masterSiteCallBackFun" type="hidden"></input> -->
								<input id="singleOrderApplyEvent"  type="hidden"></input>
								<input id="srIdCallBackFun" value="srIdCallBackFun" type="hidden"></input>
								<input id="unuseBtsSiteIdCallBackFun" value="unuseBtsSiteIdCallBackFun" type="hidden"></input>
								<input id="siteCallbackFunEqpType"  type="hidden"></input>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="showSitePage"></div>
		<div id="showFileUploadPage"></div>
		<div id="showSrIdPage"></div>
		<div id="showUnuseBtsSiteIdPage"></div>
		<input id="siteBuildEqpType" type="hidden"></input>

	</body>
</html>