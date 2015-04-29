<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<script type="text/javascript">
$(document).ready(function() {
	$("#selectAll2").on("click", function(){
		if($("#selectAll2").prop("checked")){
			$('input[type="checkbox"]').each(function(){
				$(this).prop("checked", true);
			});
		} else {
			$('input[type="checkbox"]').each(function(){
				$(this).prop("checked", false);
			});
		}
	});
	var table = $('#agentDatatable').dataTable( {
		"aaSorting" : [[ 1, "desc" ]],
		"sDom" : "rtS",
		"iDisplayLength" : -1,
		"sScrollY" : 300,
		"sScrollX" : "100%",
		"bProcessing" : false,
		"sAjaxDataProp" : "rows",
		"sAjaxSource" : CONTEXT_URL+'/profile/todoList/agent/data',
		"fnServerData": function (sSource, aoData, fnCallback) {
            $.ajax({
            	dataType: "json",
                contentType: "application/json; charset=utf-8",
                type: "POST",
                url: sSource,
                success : function (result) {
                   if(!result.success){
                     alert('請確認人員職稱是否有設定正確或workflow service是否有啟動');
                   }
                   fnCallback(result);
                },
                error : function () {
                	alert('請確認人員職稱是否有設定正確或workflow service是否有啟動');
                }
            });
        },
		"aoColumnDefs" : [ 
   			{ "aTargets" : [0], bSortable : false, "mData" : "allowBatch", "sWidth": "65px",
				"mRender": function ( data, type, full ) {
					if(data == "Y"){
						return '<input type="checkbox" value="'+data+'">';
					}
					return "";
				}
			},
			{ "aTargets": [1], "mData" : "taskId", "sClass": "todoListDisplayNone"},
			{ "aTargets": [2], "mData": "applyNo", "sWidth": "100px",
				"mRender": function ( data, type, full ) {
					return '<a href="javascript:void(0);" style="color:blue;">'+data+'</a>';
				}
			},
			{ "aTargets": [3], "mData": "processType", "sClass": "todoListDisplayNone"},
			{ "aTargets": [4], "mData": "processName", "sWidth": "80px"},
			{ "aTargets": [5], "mData": "taskName", "sWidth": "110px"},
			{ "aTargets": [6], "mData": "taskOwnerId", "sClass": "todoListDisplayNone"},
			{ "aTargets": [7], "mData": "taskOwnerGroupId", "sClass": "todoListDisplayNone"},
			{ "aTargets": [8], "mData": "applyDescription", "sWidth": "150px"},
			{ "aTargets": [9], "mData": "applicantId", "sClass": "todoListDisplayNone"},
			{ "aTargets": [10],"mData": "applicantName", "sWidth": "80px"},
			{ "aTargets": [11],"mData": "applyDate", "sWidth": "110px",
				"mRender": function ( data, type, full ) {
					if(data == null || data.length<=0){
						return "";
					}
					return moment(new Date(data)).format('YYYY/MM/DD HH:mm:ss');
				}
			},
			{ "aTargets": [12],"mData": "taskStartTime", "sWidth": "110px",
				"mRender": function ( data, type, full ) {
					if(data == null || data.length<=0){
						return "";
					}
					return moment(new Date(data)).format('YYYY/MM/DD HH:mm:ss');
				}
			},
			{ "aTargets": [13], "mData": "userTaskType", "sClass": "todoListDisplayNone"},
			{ "aTargets": [14], "mData": "jsonData", "sClass": "todoListDisplayNone"}
   		],
		"oLanguage":{
			"sProcessing": "處理中...",
			"sZeroRecords": "沒有匹配結果",
			"sLoadingRecords": "讀取中..."
		},"fnInitComplete": function() {
			this.fnAdjustColumnSizing();
		}
	});
	$(window).on('resize', function () {
		if(table.fnSettings()!= null){
			table.fnAdjustColumnSizing();
		}
	});
	$('#agentDatatable tbody').on( 'click','a', function () {
// 		var allowBatch = $(this).closest("tr").find('td:eq(0)').find(':checkbox').val();
		var taskId = $(this).closest("tr").find('td:eq(1)').text();
		var applyNo = $(this).closest("tr").find('td:eq(2)').text();
		var processType = $(this).closest("tr").find('td:eq(3)').text();
		var processName = $(this).closest("tr").find('td:eq(4)').text();
		var taskName = $(this).closest("tr").find('td:eq(5)').text();
		var taskOwnerId = $(this).closest("tr").find('td:eq(6)').text();
		var taskOwnerGroupId = $(this).closest("tr").find('td:eq(7)').text();
// 		var applyDescription = $(this).closest("tr").find('td:eq(8)').text();
// 		var applicantId = $(this).closest("tr").find('td:eq(9)').text();
// 		var applicantName = $(this).closest("tr").find('td:eq(10)').text();
// 		var applyDate = $(this).closest("tr").find('td:eq(11)').text();
		var taskStartTime = $(this).closest("tr").find('td:eq(12)').text();
		var userTaskType = $(this).closest("tr").find('td:eq(13)').text();
		var jsonData = $(this).closest("tr").find('td:eq(14)').text();
		var params = ["N", taskId, applyNo, processType, processName, taskName, taskOwnerId, taskOwnerGroupId, taskStartTime, userTaskType, jsonData];
		openApproval(params);
	});
});

</script>
<head>
<title>Agent todo list</title>
<body>
	<div class="row">
		<div class="col-xs-12">
			<div class="box-content no-padding">
				<table
					class="table table-bordered table-striped table-hover table-heading table-datatable dataTables_wrapper"
					id="agentDatatable" width="100%">
					<thead>
						<tr>
							<td><input type="checkbox" id="selectAll2"/>簽核</td>
							<td style="display:none;">taskId</td>
							<td>單號</td>
							<td style="display:none;">流程類型</td>
							<td>類型</td>
							<td>工作名稱</td>
							<td style="display:none;">工作執行人</td>
							<td style="display:none;">工作執行人職稱</td>
							<td>說明</td>
							<td style="display:none;">申請人代號</td>
							<td>申請人</td>
							<td style="width: 10px;">申請時間</td>
							<td style="width: 10px;">指派時間</td>
							<td style="display:none;">工作類型</td>
							<td style="display:none;">jsonData</td>
						</tr>
					</thead>
					<tbody>
						<!-- Start: list_row -->
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>