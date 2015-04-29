<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<jsp:include page="/WEB-INF/layout/jsLayout.jsp" />
<script type="text/javascript">
var table = null;
$(document).ready(function() {
	$("#query").on("click", function(){
		if(table != null){
			table.fnDestroy();
		}
		table = $('#flowTasksDatatable').dataTable(getOSetting());
// 		var oSettings = table.fnSettings();
// 		oSettings.sAjaxSource = CONTEXT_URL+'/workflow/query/data?processType='+$('#processType').val()+"&applyNo="+$('#applyNo').val();
// 		oSettings.aoServerParams.push({"sName":"user","fn":function(aoData){
// 			aoData.push({"processType":$('#processType').val(),"applyNo":$('#applyNo').val()});
// 		}});
// 		table._fnAjaxUpdate();
// 		table.fnDraw();
	});
	
	$(window).on('resize', function () {
		if(table.fnSettings()!= null){
			table.fnAdjustColumnSizing();
		}
	} );
});
function getOSetting(){
	return oSetting = {
			"aaSorting" : [[ 0, "desc" ]],
			"sDom" : "rtS",
			"iDisplayLength" : -1,
	        "sScrollX" : "100%",
	        "bDestroy": true,
	        "bProcessing" : false,
	        "sAjaxDataProp" : "rows",
	        "sAjaxSource" : CONTEXT_URL+'/workflow/query/data?processType='+$('#processType').val()+"&applyNo="+$('#applyNo').val(),
	        "aoColumnDefs" : [ 
	            { "aTargets": [0], "mData" : "taskId"},
	            { "aTargets": [1], "mData": "applyNo", "sWidth": "100px",
	        		"mRender": function ( data, type, full ) {
	            		return '<a href="javascript:void(0);" style="color:blue;">'+data+'</a>';
	            	}
	            },
	            { "aTargets": [2], "mData": "processType"},
	            { "aTargets": [3], "mData": "processName"},
	            { "aTargets": [4], "mData": "taskName"},
	            { "aTargets": [5], "mData": "taskOwnerId",
	            	"mRender": function ( data, type, full ) {
	            		if(data == 'null'){
	            			return "";
	            		}
	            		return data;
	            	}
	            },
	            { "aTargets": [6], "mData": "taskOwnerGroupId",
	            	"mRender": function ( data, type, full ) {
	            		if(data == 'null'){
	            			return "";
	            		}
	            		return data;
	            	}
	            },
	            { "aTargets": [7], "mData": "applyDescription"},
	            { "aTargets": [8], "mData": "applicantId"},
	            { "aTargets": [9], "mData": "applyDate",
	            	"mRender": function ( data, type, full ) {
	            		if(data == null || data.length<=0){
	            			return "";
	            		}
	            		return moment(new Date(data)).format('YYYY/MM/DD HH:mm:ss');
	            	}
	            },
	            { "aTargets": [10],"mData": "taskStartTime", "sWidth": "110px",
	            	"mRender": function ( data, type, full ) {
	            		if(data == null || data.length<=0){
	            			return "";
	            		}
	            		return moment(new Date(data)).format('YYYY/MM/DD HH:mm:ss');
	            	}
	            },
	            { "aTargets": [11], "mData": "userTaskType"},
	            { "aTargets": [12], "mData": "jsonData"}
	        ],
	        "oLanguage":{
	        	"sProcessing": "處理中...",
	        	"sZeroRecords": "沒有匹配結果",
	        	"sLoadingRecords": "讀取中..."
	        },"fnInitComplete": function() {
	            this.fnAdjustColumnSizing();
	        }
		};
}

</script>
<head>
<title>Workflow todo list</title>
<body>
	<div class="row">
		<div class="col-xs-12">
			<div class="box-content no-padding">
				<select id="processType">
					<c:forEach items="${processMap}" var="map">
						<option value="${map['key']}"><c:out value="${map['value']}(${map['key']})"/></option>
					</c:forEach>
					<option value="">Any type</option>
				</select>
				<input type="text" id="applyNo" placeholder="單號">
				<input type="button" id="query" value="查詢">
				<table
					class="table table-bordered table-striped table-hover table-heading table-datatable dataTables_wrapper"
					id="flowTasksDatatable" width="100%">
					<thead>
						<tr>
							<th>taskId</th>
							<th>單號</th>
							<th>processType</th>
							<th>流程類型</th>
							<th>工作名稱</th>
							<th>工作執行人</th>
							<th>工作執行人職稱</th>
							<th>說明</th>
							<th>申請人代號</th>
							<th>申請時間</th>
							<th>指派時間</th>
							<th>工作類型</th>
							<th>jsonData</th>
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