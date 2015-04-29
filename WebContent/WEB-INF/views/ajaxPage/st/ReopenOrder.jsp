<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<title>重啟工單</title>
		<style type="text/css">
			.reopenOrderTableCSS{ background-color:#beebff; }
			td.reopenOrderDisplayNone {display: none;}
		</style>
		<script type="text/javascript">
			$(document).ready(function() {
				reopenOrderInit();
				$('#reopenOrderTable').dataTable({
					"bDestroy" : true,
					"iDisplayLength" : -1,
					"aaSorting" : [ [ 0, "desc" ] ],
					"sDom" : "rS",
					"sScrollY" : '70',
					"bSort" : false,
					"bProcessing" : false,
					"sAjaxDataProp" : "rows",
					"sAjaxSource" : CONTEXT_URL
							+ '/st/st005/search/workOrder?workId='+$("#workId").val(),
					"aoColumnDefs" : [
					{
						"aTargets" : [ 0 ],
						"mData" : "order_ID"
					}, {
						"aTargets" : [ 1 ],
						"mData" : "orderTypeName"
					}, {
						"aTargets" : [ 2 ],
						"mData" : "deptName"
					}, {
						"aTargets" : [ 3 ],
						"mData" : "odr_SEQ"
					}, {
						"aTargets" : [ 4 ],
						"mData" : "orderStatusName"
					}, {
						"aTargets" : [ 5 ],
						"mData" : "odr_STATUS",
						"sClass": "reopenOrderDisplayNone"
					}
					],
					"oLanguage" : {
						"sProcessing" : "處理中...",
						"sZeroRecords" : "沒有匹配結果",
						"sLoadingRecords" : "讀取中..."
					},
					"fnInitComplete" : function() {
						this.fnAdjustColumnSizing();
					}
				});
				
				$('#reopenOrderTable tbody').off('click', 'tr').on( 'click','tr', function () {
					var data = $('#reopenOrderTable').dataTable().fnGetData($(this).closest("tr").index());
					 if ( !$(this).hasClass('reopenOrderTableCSS') ) {
						 $('#reopenOrderTable').dataTable().$('tr').removeClass();
						$(this).addClass('reopenOrderTableCSS');
			         }
			         else {
			        	 $('#reopenOrderTable').dataTable().$('tr').removeClass('reopenOrderTableCSS');
			         }
// 					 alert(data.odr_STATUS+" , "+data.order_ID);
					 $("#reopenOrderId").val(data.order_ID);
					 $("#reopenOrderStatus").val(data.odr_STATUS);
					 $("#reopenOrderOrderSeq").val(data.odr_SEQ);
					 if(data != null ){
						$("#reopenOrderworkDescDiv").empty();
						$.ajax({
							url : CONTEXT_URL + "/st/st005/search/workOrderObj",
							type : 'POST',
							data : {
								orderId : data.order_ID
							},
							async : false,
							success : function(data) {
								var desc = "";
								if(data.order_DESC != null){
									desc = data.order_DESC;
								}
								$("#reopenOrderworkDescDiv").append('<textarea id="reopenOrderworkDesc" name="reopenOrderworkDesc" style="resize: none; width:95%;margin-left: 15px; " rows="5">'+desc+'</textarea>');
							}
						});
					}
				});
				
				
				$("#reopenSendBtn").click(function(){
					var orderStatus =  $("#reopenOrderStatus").val();
					var errorMessages = [];
					if(orderStatus == ""){
						errorMessages [errorMessages.length]="請選擇一筆工單";
					}else if(orderStatus != "OR09"){
						errorMessages [errorMessages.length]="非已完工的工單無法重啟";
					}
					if($("#reopenOrderworkDesc").val().length >100){
						errorMessages [errorMessages.length]="重啟工單說明超過100個字";
					}
					//從修改頁面取得該筆工單的odr_seq
					var odrSeq = parseInt($("#siteBuildapplyOrderSeq").val());
					if(parseInt($("#reopenOrderOrderSeq").val()) > odrSeq){
						alert("無法選擇比此工單作業順序還大的工單進行重啟工單");
						return false;
					}
					if(errorMessages.length != 0){
						var msg ="";
						for(var em in errorMessages){
							msg += errorMessages[em]+"\n";
						}
						alert(msg);
						return false;
					}
					if(confirm("是否要重啟工單?")){
						$.ajax({
							url :  CONTEXT_URL +'/st/st005/update/reopenOrder',
							type : 'POST',
							async : false,
							data : {
								orderId : $("#reopenOrderId").val(),
								reopenOrderDesc : $("#reopenOrderworkDesc").val(),
								workType : $("#siteBuildWorkType").val()
							},
							success : function(data){
								var result = data['result'];
								if(result == true){
									alert('<s:message code="msg.update.success"/>');
									$("#${targetFrameId}").dialog('close');
								}else{
									alert(result);	
								}
								
							}
						});
					}
				});
				
				$("#reopenCancelBtn").click(function(){
					$("#${targetFrameId}").dialog('close');
				});
				
// 				$('#reopenOrderTable tbody').off('click', 'tr').on( 'click','tr', function () {
// 					var data = $('#reopenOrderTable').dataTable().fnGetData($(this).closest("tr").index());
					
					
// 				});
			});//document.ready end
			function reopenOrderInit(){
				$("#showReopenOrderWorkId").text($("#workId").val());
				$("#showRepoenOrderBtsSiteId").text($("#siteBuildBtsSiteId").val());
				$("#showReopenOrderWorkType").text($("#siteBuildWorkType").find(":selected").text());
			}
		</script>
	</head>
	<body>
		<div class="row">
			<div class="col-xs-12 col-sm-12">
				<div class=" ui-draggable ui-droppable">
					<div class="box-content" style="width :100%">
						<table width="100%">
							<tr>
								<td width="10%"></td>
								<td width="20%"></td>
								<td width="10%"></td>
								<td width="20%"></td>
								<td width="10%"></td>
								<td width="20%"></td>
							</tr>
							<tr>
								<td nowrap="nowrap" align='left' colspan="2">
									<button class="btn btn-primary btn-label" style="margin-right: 10px"
										type="button" id="reopenSendBtn">&nbsp;確定</button>
									<button class="btn btn-primary btn-label" 
									type="button" id="reopenCancelBtn">&nbsp;取消</button>
								</td>
							</tr>
							<tr>
								<td nowrap="nowrap">
									<label class="col-sm-12 control-label">作業編號 :</label>
								</td>
								<td>
									<p style="padding-top: 6px; margin-left:-10px;" id="showReopenOrderWorkId"></p>
								</td>
								<td nowrap="nowrap">
									<label class="col-sm-12 control-label">基站編號 :</label>
								</td>
								<td>
									<p style="padding-top: 6px; margin-left:-10px;" id="showRepoenOrderBtsSiteId"></p>
								</td>
								<td nowrap="nowrap">
									<label class="col-sm-12 control-label">工務類別 :</label>
								</td>
								<td>
									<p style="padding-top: 6px; margin-left:-10px;" id="showReopenOrderWorkType"></p>
								</td>
							</tr>
							<tr>
								<td colspan="6">
									<table id="reopenOrderTable"
										class="table table-bordered  table-hover table-heading table-datatable"
										style="margin-left: 15px;"
										width="95%">
										<thead>
											<tr>
												<th>工單號碼</th>
												<th>派工目的</th>
												<th>負責單位</th>
												<th>作業順序</th>
												<th>狀態</th>
												<th hidden="hidden">ORDER_STATUS</th>
											</tr>
										</thead>
										<tbody>
										</tbody>
									</table>
								</td>
							</tr>
							<tr>
								<td nowrap="nowrap" colspan="2">
									<label class="col-sm-12 control-label">重啟工單說明 :</label>
								</td>
							</tr>
							<td colspan="6" nowrap="nowrap">
								<div style="margin-top: 5px" id="reopenOrderworkDescDiv">
									<textarea id="reopenOrderworkDesc" name="reopenOrderworkDesc" style="resize: none; width:95%;margin-left: 15px; " rows="5"></textarea>
								</div>
							</td>
						</table>
						<input id="reopenOrderId" type="hidden"/>
						<input id="reopenOrderStatus" type="hidden"/> 
						<input id="reopenOrderOrderSeq" type="hidden"/> 
					</div>
				</div>
			</div>
		</div>
	</body>
</html>