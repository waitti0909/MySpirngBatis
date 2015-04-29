<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html lang="en">
<head>
<title>基站基本資料</title>
	<script type="text/javascript">
	$(document).ready(function() {
		if("${PageType}" == "view"){
			$("#materialOutTable tbody tr").remove();
			$("#optId").text('${materialOptTRO.OPT_ID}'); //操作單號
			$("#editeOrderIdOut").val('${materialOptTRO.ORDER_ID}'); //轉出工單
		
			if('${materialOptTRO.ORDER_ID}' != '${materialOptTRO.OS_ID}'){
				$("#editeOsWorkOut option").remove();
				$("#editeOsWorkOut").append('<option>${materialOptTRO.OS_ID}</option>'); //轉出派工單號
			}
			
			$("#editeBtsSiteIdOut").text('${siteOut.BTS_SITE_ID}');//基站編號
			$("#editeEqpTypeOut").text('${siteOut.eqpName}'); //設備型態
			
			$("#editeDepotOut option").remove();
			$("#editeDepotOut").append('<option>${whNameOut}</option>'); //退料倉庫
			$("#editeDepotIn option").remove();
			$("#editeDepotIn").append('<option>${whNameOut}</option>'); //領料倉庫
			
			$("#editeOrderIdIn").val('${materialOptTRO.ORDER_ID_IN}'); //目的工單
			
			$("#editeBtsSiteIdIn").text('${siteIn.BTS_SITE_ID}');//目的基站編號
			$("#editeEqpTypeIn").text('${siteIn.eqpName}'); //目的設備型態
			
			if('${materialOptTRO.ORDER_ID_IN}' != '${materialOptTRO.OS_ID_IN}'){
				$("#editeOsWorkIn option").remove();
				$("#editeOsWorkIn").append('<option>${materialOptTRO.OS_ID_IN}</option>'); //目的派工單號
			}
			
			$("#deptName").text('${deptName}');//執行單位
			$("#chName").text('${chName}'); //執行人員
			<fmt:formatDate value="${materialOptTRO.CR_TIME }" pattern="yyyy/MM/dd HH:MM"  var="crTime"/>
			$("#trnsfertTime").text('${crTime}'); //轉出時間
			//personnel
			var tr = '';
				<c:forEach items="${materialTroDetail }" var="detail">
					tr='<tr>';
					tr =tr+'<td><input  disabled type="checkbox" name="SRL_NO" checked></td>';
					tr =tr+	'<td>${fn:trim(detail.MAT_NO)}</td>';
					tr =tr+	'<td>${fn:trim(detail.MAT_NAME)}</td>';
					tr =tr+	'<td>${fn:trim(detail.VEN_SN)}</td>';
					tr =tr+	'<td>${fn:trim(detail.TAG_NO)}</td>';
					tr =tr+	'<td>${fn:trim(detail.TRO_QTY)}</td>';
					tr =tr+'</tr>';
					$("#materialOutTable").append(tr);
				</c:forEach>
				
		
				$("#editeOptDesc").val('${materialOptTRO.OPT_DESC}');
			$(".view-disabled").prop("disabled",true);	
			
		} else {
			editePage();
		}
		
	});//document ready end
	
	function editePage(){
			$("#transOutSaveBtn").unbind("click").bind("click",function(){
				if(!confirm('確認要將物料轉出？')){
					return;
				}
			
				if($("#editeOrderIdOut").val() == ''){
					alert('轉出工單為必填！請選擇轉出工單！ ');
					return;
				}
				
				
				if($("#editeOrderIdIn").val() == ''){
					alert('目的工單為必填！請選擇目的工單！ ');
					return;
				}
				
				if($("#editeOrderIdIn").val() == $("#editeOrderIdOut").val()){
					alert('轉出工單與目的工單需不同！請重新選擇！');
					return;
				}
				
				if($("#editeWorkIdOut").val() == ''){
					alert("轉出工單為必填!!");
					return;
				}
				
				if($("#editeDepotOut").val() == ''){
					alert("退料倉庫為必填！");
					return;
				}
				
				if($("#editeWorkIdIn").val() == ''){
					alert("目的工單為必填!!");
					return;
				}
				
				if($("#editeDepotIn").val() == ''){
					alert("領料倉庫為必填！");
					return;
				}
				
				
				var orStatus = ['OR05','OR06']; 
				
				if($.inArray($('#editeOrdStatusIn').val(),orStatus) == -1){
					alert("目的工單狀態必須為 已接工 或 已委外");
					return;
				}
				
				
				if($.inArray($('#editeOrdStatusOut').val(),orStatus) == -1){
					alert("轉出工單狀態必需為 已接工 或 已委外  !!");
					return;
				}
				
				if($("#editeRepDeptIn").val() != $("#editeRepDeptOut").val()){
					alert("轉出工單與目的工單接工單位需為同部門！！");
					return;
				} 
				
				var jsonArray = [];
				var checkQty = false;
				var msg = '';
				//轉出明細
				if($("#materialOutTable tbody tr").length == 0){
					alert("無轉出明細資料！");
					return;
				}
				
				if($("#materialOutTable tbody input[type='checkbox']:checked").length == 0){
					alert("轉出明細至少勾選1筆!");
					return;
				}
				
				$("#materialOutTable tbody tr").each(function () {
					
					if($(this).find("input[name='SRL_NO']").prop("checked")){
						var qty = $(this).find("input[name='QTY']").val();
						if(qty == '' || qty =='0'){
							checkQty = true;
							msg ='轉出數量不可空或為0 !';
							return;
						}
						
						if(qty > $(this).find("td:eq(5)").text()){
							checkQty = true;
							msg ='轉出數量不可大於總數量 !';
							return;
						}
						
						jsonArray.push({
										"ctrlType"  : 'S',
										"matStatus" :'1', //TODO matStatus暫時填入1 by miles Chang 2015/03/23
										"trnReason" :'F001',//TODO rnResn退料原因填入 F001，目前未確定退料原因的定義  by miles Chang 2015/03/23
										"srlNo" : $(this).find("input[name='SRL_NO']").val() , //序號控管物料檔編號
										"matNo" : $(this).find("input[name='MAT_NO']").val(), //料號
										"faNo" : $(this).find("input[name='FA_NO']").val(), //資產編號
										"qty" : qty //轉出數量
									});	
					}
					
				});
				
				if(checkQty){
					alert(msg);
				}
				
				$.ajax({
						type : 'POST',
						url : CONTEXT_URL + "/otr/otr001/MTTransOut",
						data : {
							"orderIdIn" : $("#editeOrderIdIn").val(), //目的工單
							"siteIdIn" : $("#editeSiteIdIn").val(), //目的站台
							"osIdIn":$("#editeOsWorkIn").val(), //目的派工單
							"whIdIn": $("#editeDepotIn").val(), //領料倉庫別
							"workTypeIn" : $("#editeWorkTypeIn").val(), //目的工務類別
							"jsonArrStrOut" : JSON.stringify(jsonArray),
							"orderIdOut" : $("#editeOrderIdOut").val(), //轉出工單
							"siteIdOut" : $("#editeSiteIdOut").val(), //轉出站台
							"osIdOut":$("#editeOsWorkOut").val(), //轉出派工單
							"whIdOut": $("#editeDepotIn").val(), //退領料倉庫別
							"workTypeOut" : $("#editeWorkTypeOut").val(), //轉出工務類別
							"optDesc" : $("#editeOptDesc").val() //轉出說明
						},
						dataType : "json",
						async : false,
						success : function(data) {
							alert(data.msg);
							if(data.success){
								$.fancybox.close();
							}
						}
					});
		});
			//重置
			$("#resetOtr").unbind('click').bind('click',function(){
				$("#editeOrderIdOut").val('');
				$("#editeOrderIdIn").val('');
				$("#otrTable p").text('');
				$("#editeOsWorkOut option").remove();
				$("#editeOsWorkOut").append('<option>----請選擇----</option>');
				$("#editeOsWorkOut").change();
				//----------------
				$("#editeOsWorkIn option").remove();
				$("#editeOsWorkIn").append('<option>----請選擇----</option>');
				$("#editeOsWorkIn").change();
				
				$("#materialOutTable tbody tr").remove();
				$("#optDesc").val('');
				
			});
			
			$('#editeDepotOut').unbind('change').bind('change',function(){
				$('#editeDepotIn').val($(this).val());
			});
			
			
			$(".numberMask").mask('#0',{reverse: true});
	}
	
	function openWorkQueryPage(callBackFunName) {
		openWorkQueryFrame('openWorkQueryPage',callBackFunName,'${currentMenuId}');
	}
	
	//轉出
	function workOut(data){
		setOutInData('Out',data);
	}
	
	//目的
	function workIn(data){
		setOutInData('In',data)
	}
	
	function setOutInData(InOrOut,callBackData){
		$('#editeOrderId'+InOrOut).val(callBackData.orderId);//轉出/目的  工單
		$('#editeBtsSiteId'+InOrOut).text(callBackData.bts_SITE_ID);//轉出/目的 基站編號
		$('#editeSiteId'+InOrOut).val(callBackData.siteId);//轉出/目的 站台編號
		$('#editeEqpType'+InOrOut).text(callBackData.eqpName);//轉出/目的 設備型態
		$('#editeWorkType'+InOrOut).val(callBackData.work_TYPE); //轉出/目的 工務類型
		$('#editeOrdStatus'+InOrOut).val(callBackData.odr_STATUS); //轉出/目的工單狀態
		$('#editeRepDept'+InOrOut).val(callBackData.rep_DEPT); //轉出/目的 接工單位

		var orderId  = callBackData.orderId;
		if(InOrOut == 'Out'){
			$('#editeOsWork'+InOrOut).unbind("change").bind("change",function(){
				$.ajax({
					type : 'POST',
					url : CONTEXT_URL + "/otr/otr001/searchWareHourse",
					data : {
						"orderId" : $('#editeOrderId'+InOrOut).val(),
						"osId" : $(this).val() 
					},
					dataType : "json",
					async : false,
					success : function(data) {
						//轉出/入退料倉庫
						$('#editeDepotOut option').remove(); //退料倉庫
						$('#editeDepotIn option').remove();  //領料倉庫
						$('#editeDepotOut').append("<option value=''>----請選擇----</option>");
						$('#editeDepotIn').append("<option value=''>----請選擇----</option>");
						for(var i=0 ; i < data.rows.length ; i++ ){
							var wareHourse = data.rows[i];
							$('#editeDepotOut').append("<option value='"+wareHourse.wh_id+"'>"+wareHourse.wh_name+"</option>");
							$('#editeDepotIn').append("<option value='"+wareHourse.wh_id+"'>"+wareHourse.wh_name+"</option>");
						}					
					}
				}); 
			});
		}
		
		//目的派工單號
		$.ajax({
				type : 'POST',
				url : CONTEXT_URL + "/otr/otr001/searchOutSourcing",
				data : {
					"orderId" : orderId
				},
				dataType : "json",
				async : false,
				success : function(data) {
					$('#editeOsWork'+InOrOut + " option").remove();
					$('#editeOsWork'+InOrOut).append("<option value='"+$('#editeOrderId'+InOrOut).val()+"'>----請選擇----</option>");
					var outSourcingList = data.row['outSourcingList'];
					if( outSourcingList != null){
						for(var i=0 ; i <outSourcingList.length ; i++ ){
							var outSourcing = outSourcingList[i];
							$('#editeOsWork'+InOrOut).append("<option value='"+outSourcing.os_ID+"'>"+outSourcing.os_ID+"</option>");
						}
					}
					$('#editeOsWork'+InOrOut).change();
					
					if(InOrOut == 'Out'){
						$("#editeOsWorkOut").unbind("change").bind("change",function(){
							//轉出明細
							$.ajax({
								type: 'POST',
								url : CONTEXT_URL + "/otr/otr001/getTransOutDetail",
								data : {
									"osId" : $('#editeOsWorkOut').val()
								},
								dataType : "json",
								async : false,
								success : function(data) {
									$("#materialOutTable tbody tr").remove();
									var tr = '';
									for(var i = 0 ; i< data.row.length ; i++){
										var material = data.row[i];
										tr += '<tr><td><input type="checkbox" name="SRL_NO" value="'+material.srlNo+'" ></td>'; //序號控管物料檔編號
										tr += '<td><input type="hidden" name="MAT_NO"  value="'+material.matNo+'" >'+	material.matNo+'</td>';//料號
										tr += '<td>'+	material.matName+'<input name="FA_NO" type="hidden" valu="'+material.fano+'" ></td>'; //品名
										tr += '<td>'+	$.trim(material.venSn)+'</td>';//商場序號
										tr += '<td>'+	$.trim(material.tagNo)+'</td>';//貼標籤
										tr += '<td>'+	material.assetQty+'</td>';//總數量
										tr += '<td><input type="text" name="QTY" class="numberMask"  style="width:140px" ></td></tr>';//轉出數量
									}
									$("#materialOutTable").append(tr);
									
									$(".numberMask").mask('#0',{reverse: true});
								}
							});
						});
						$("#editeOsWorkOut").change();
					}
					
				}
			}); 
		
	}
	
	</script>
</head>
<body>
<div class="box-header">
	<div class="box-name">
		<span>工單物料轉出</span>
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
				<div>
					<button class="btn btn-primary btn-label view-disabled" type="button" id="transOutSaveBtn"  >
						<span><i class="fa fa-save"></i></span>&nbsp;存檔
					</button>
					<button class="btn btn-primary btn-label view-disabled" type="button" id="resetOtr" >
						<span><i class="fa fa-reset"></i></span>&nbsp;重置
					</button>
				</div>
				<form class="form-horizontal"  action="" >
					<table id="otrTable" style="width:100%">	
						<tr>
							<td nowrap="nowrap"><label class="col-sm-12 control-label">操作單號 :</label></td>
							<td  nowrap="nowrap">
								<p id="optId" style="padding-top:17px; padding-left:15px;"  ></p>
							</td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td>
								<div >&nbsp;</div>
							</td>
						</tr>
						<tr >
							<td  nowrap="nowrap">
								<label style="padding-left:5px;" class="col-sm-12 control-label">
									<span class="s">* </span>轉出工單:</label></td>
							<td  nowrap="nowrap">	
								<div class="col-sm-6">
									<input id="editeOrderIdOut"  type="text" class="form-control" readonly="readonly">
								</div>
								<div style="width: 5%; height: auto; float: left; display: inline">
									<button  class="btn btn-primary btn-label view-disabled" style="margin-right: 10px" type="button"  onclick="openWorkQueryPage('workOut')" 
									>選擇</button>
								</div>
							</td>
							<td     nowrap="nowrap"><label class="col-sm-12 control-label">基地編號 :</label></td>
							<td    nowrap="nowrap">
								<p id="editeBtsSiteIdOut" style="padding-top:17px; padding-left:15px;" ></p>
								<input type="hidden" id="editeSiteIdOut" >
							</td>
							<td   nowrap="nowrap"><label style="padding-left:5px;" class="col-sm-12 control-label">設備型態 :</label></td>
							<td nowrap="nowrap">
								<p id="editeEqpTypeOut" style="padding-top:17px; padding-left:15px;" ></p>
							</td>
						</tr>
						<tr>
							<td>
								<div >&nbsp;</div>
							</td>
						</tr>
						<tr>
							<td  nowrap="nowrap"><label class="col-sm-12 control-label">轉出派工單號:</label></td>
							<td nowrap="nowrap">
								<div class="col-sm-6">
									<select id="editeOsWorkOut"  class="form-control view-disabled">
										<option>--請選擇--</option>
									</select>
								</div>
							</td>
							<td  nowrap="nowrap"><label class="col-sm-12 control-label"><span class="s">* </span>退料倉庫:</label></td>
							<td   nowrap="nowrap">
								<div class="col-sm-12">
									<select id="editeDepotOut"  class="form-control view-disabled"  >
										<option>--請選擇--</option>
									</select>
								</div>
							</td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td>
								<div >&nbsp;</div>
							</td>
						</tr>
						<tr >
							<td  nowrap="nowrap"><label style="padding-left:5px;" class="col-sm-12 control-label">
									<span class="s">* </span>目的工單:</label></td>
							<td  nowrap="nowrap">	
								<div class="col-sm-6">
									<input id="editeOrderIdIn"  type="text" class="form-control " readonly="readonly">
								</div>
								<div style="width: 10%; height: auto; float: left; display: inline">
									<button onclick="openWorkQueryPage('workIn')"   class="btn btn-primary btn-label view-disabled" style="margin-right: 10px" type="button">選擇</button>
								</div>
							</td>
							<td  nowrap="nowrap"><label class="col-sm-12 control-label">基地編號 :</label></td>
							<td  nowrap="nowrap">
								<p id='editeBtsSiteIdIn' style="padding-top:17px; padding-left:15px;"></p>
								<input type="hidden" id="editeSiteIdIn" >
							</td>
							<td><label style="padding-left:5px;" class="col-sm-12 control-label">設備型態 :</label></td>
							<td  nowrap="nowrap">
								<p id="editeEqpTypeIn" style="padding-top:17px; padding-left:15px;" ></p>
							</td>
						</tr>
						<tr>
							<td>
								<div >&nbsp;</div>
							</td>
						</tr>
						<tr>
							<td><label class="col-sm-12 control-label">目的派工單號:</label></td>
							<td nowrap="nowrap">
								<div class="col-sm-6">
									<select id="editeOsWorkIn"  class="form-control view-disabled">
										<option>--請選擇--</option>
									</select>
								</div>
							</td>
							<td  nowrap="nowrap"><label style="display:none" class="col-sm-12 control-label"><%-- <span class="s">* </span> --%>
								領料倉庫:</label></td>
							<td  nowrap="nowrap">
								<div class="col-sm-12">
									<select id="editeDepotIn"  class="form-control" disabled  style="display:none">
										<option>--請選擇--</option>
									</select>
								</div>
							</td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td>
								<div >&nbsp;</div>
							</td>
						</tr>
						<tr>
							<td  nowrap="nowrap"><label class="col-sm-12 control-label">執行單位 :</label></td>
							<td  nowrap="nowrap">
								<p id='deptName' style="padding-top:17px; padding-left:15px;" ></p>
							</td>
							<td  nowrap="nowrap"><label style="padding-left:5px;" class="col-sm-12 control-label">執行人員 :</label></td>
							<td  nowrap="nowrap">
								<p id='chName' style="padding-top:17px; padding-left:15px;" ></p>
							</td>
							<td  nowrap="nowrap"><label class="col-sm-12 control-label">轉出時間 :</label></td>
							<td nowrap="nowrap"  class="col-sm-1">
								<p id="trnsfertTime" style="padding-top:17px; padding-left:15px;" ></p>
							</td>
						</tr>
						<tr>
							<td>
								<div >&nbsp;</div>
							</td>
						</tr>
						<tr>
							<td  nowrap="nowrap"><label class="col-sm-12 control-label">轉出明細 :</label></td>
							<td colspan="5">
									<!-- table table-bordered  table-hover table-heading table-datatable -->
								<table id="materialOutTable" class="table table-bordered  table-hover table-heading table-datatable"
									style="table-layout: fixed; word-break: break-all; word-wrap: break-word;">
									<thead style="background-color: #f0f0f0;">
										<tr>
											<th class="theadth" >選擇</th>
											<th class="theadth" >料號</th>
											<th class="theadth" >品名</th>
											<th class="theadth" >廠商序號 </th>
											<th class="theadth" >貼標號碼</th>
											<c:if test="${PageType ne 'view' }">
												<th class="theadth" >總數量</th>
											</c:if>
											<th class="theadth" >轉出數量</th>
										</tr>
									</thead>
								</table>
							</td>
						</tr>
						<tr>
							<td>
								<div >&nbsp;</div>
							</td>
						</tr>
						<tr>
							<td><label class="col-sm-12 control-label">轉出說明:</label></td>
							<td colspan="5">
								<textarea id="editeOptDesc" class="view-disabled" maxlength="400" style="resize: none;width:98%;border-left-width:1px;margin-left:15px;margin-top:4px;" rows="3"></textarea>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
</div>
<div id="openWorkQueryPage" ></div>
	<input id="editeWorkTypeIn"  type="hidden" >
	<input id="editeWorkTypeOut" type="hidden" >
	<input id="editeOrdStatusIn"  type="hidden" >
	<input id="editeOrdStatusOut" type="hidden" >
	<input id="editeRepDeptIn"  type="hidden" >
	<input id="editeRepDeptOut" type="hidden" >
</body>
</html>