<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<title>專線資訊</title>
		<style type="text/css">
			td.lineInfoDisplayNone {display: none;}
		</style>
		<script type="text/javascript">
		
			$(document).ready(function() {
				lineInfoTableInit($("#lineInfoSiteId").val());
			});//document.ready end
			function showLineNetWorkPageFun(b_siteId){
				$.ajax({
					mimeType : 'text/html; charset=utf-8',
					type : 'POST',
					url : CONTEXT_URL + "/st/line/lineApply/lineNetworkPage",
					dataType : "html",
					async : false,
					success : function(data) {
						var frameId = "showLineNetWorkPage";
						var frameTitle ="VLAN設定"; 
						var frameData = data;
						initialDialogFrame(frameId, frameTitle, frameData, 900,350)
					}
				});
				
				$('#LineNeworkDetailTable').dataTable({
					"bDestroy" : true,
					"iDisplayLength" : -1,
					"aaSorting" : [ [ 0, "desc" ] ],
					"sDom" : "rS",
					"sScrollY" : '70',
					"bSort" : false,
					"bProcessing" : false,
					"sAjaxDataProp" : "rows",
					"sAjaxSource" : CONTEXT_URL
							+ '/st/line/lineApply/search/lineNetworkTable?b_siteId='+b_siteId,
					"aoColumnDefs" : [ 
					{"aTargets" : [ 0 ],"mData" : "btsSiteId"}, 
					{"aTargets" : [ 1 ],"mData" : "vlan"},
					{"aTargets" : [ 2 ],"mData" : "ip"},
					{"aTargets" : [ 3 ],"mData" : "gateway"},
					{"aTargets" : [ 4 ],"mData" : "submask"}
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
			}
			function lineInfoTableInit(siteId){
				var locId = $('#engineRoomLocationId').val();
				if($("#lineInfoSiteId").val() != ""){
					$('#lineInfoTable').dataTable({
						"bDestroy" : true,
						"iDisplayLength" : -1,
						"aaSorting" : [ [ 0, "desc" ] ],
						"sDom" : "rS",
						"sScrollY" : '70',
						"bSort" : false,
						"bProcessing" : false,
						"sAjaxDataProp" : "rows",
						"sAjaxSource" : CONTEXT_URL
								+ '/st/line/lineApply/search/lineInfoTable?siteId='+siteId,
						"aoColumnDefs" : [ 
						{"aTargets" : [ 0 ],"mData" : "line_ID"}, 
						{"aTargets" : [ 1 ],"mData" : "a_BTS_SITE_ID"},
						{"aTargets" : [ 2 ],"mData" : "b_BTS_SITE_ID"},
						{"aTargets" : [ 3 ],"mData" : "vendor"},
						{"aTargets" : [ 4 ],"mData" : "line_TYPE"},
						{"aTargets" : [ 5 ],"mData" : "line_SPEED"},
						{"aTargets" : [ 6 ],"mData" : "b_EQP"},
						{"aTargets" : [ 7 ],"mData" : "b_PORT_POS"},
						{"aTargets" : [ 8 ],"mData" : "cht_GE"},
						{ "aTargets": [ 9 ], "mData": "b_SITE_ID", "sClass": "lineInfoDisplayNone"},
						{"aTargets" : [ 10 ], bSortable : false,"mData" : "b_LOC",
			           		"mRender": function ( data, type, full ) {
			           			return "<input type='button' value='檢視' onclick='showLineNetWorkPageFun(\""+full.b_SITE_ID+"\");'>";
			            	}
			        	},
			        	
						],
						"oLanguage" : {
							"sProcessing" : "處理中...",
							"sZeroRecords" : "沒有匹配結果",
							"sLoadingRecords" : "讀取中..."
						},
						"fnInitComplete" : function() {
							this.fnAdjustColumnSizing();
						},
					});
				}
			}
		</script>
	</head>
	<body>
		<div class="row">
			<div class="col-xs-12 col-sm-12">
				<div class=" ui-draggable ui-droppable">
					<div class="box-content" style="margin-left: 30px; margin-top: 5px;width :95%">
						<table id="lineInfoTable"
							class="table table-bordered  table-hover table-heading table-datatable"
							width="95%">
							<thead>
								<tr>
									<th>專線號碼</th>
									<th>甲端站台</th>
									<th>乙端站台</th>
									<th>業者別</th>
									<th>電路類別</th>
									<th>速率</th>
									<th>設備</th>
									<th>PORT位</th>
									<th>CHT GE</th>
									<th>VLAN&IP</th>
									<th hidden="hidden">SITE_ID</th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
						<input type="hidden" id="lineInfoSiteId"></input>
					</div>
				</div>
			</div>
		</div>
		<div id="showLineNetWorkPage"></div>
	</body>
</html>