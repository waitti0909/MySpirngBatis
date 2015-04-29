<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<style type="text/css">
	th.todoListDisplayNone, td.todoListDisplayNone {display: none;}
</style>

<script type="text/javascript">
$(document).ready(function() {
	var table = $('#workDatatable').dataTable( {
		"aaSorting" : [[ 0, "desc" ]],
		"sDom" : "rtS",
		"iDisplayLength" : -1,
// 		"sScrollY" : 300,
        "sScrollX" : "100%",
        "bProcessing" : false,
        "sAjaxDataProp" : "rows",
        "sAjaxSource" : CONTEXT_URL+'/profile/todoOrders/data',
        "aoColumnDefs" : [ 
            { "aTargets": [0], "mData" : "orderType", "sClass": "todoListDisplayNone"},
            { "aTargets": [1], "mData" : "orderTypeDesc"},
            { "aTargets": [2], "mData" : "orderId", "sClass": "todoListDisplayNone"},
            { "aTargets": [3], "mData" : "orderId", "aDataSort" : [ 1, 5 ], 
            	"mRender": function ( data, type, full ) {
            		var orderInfo = full.orderInfo;
            		if(orderInfo == null || orderInfo == 'null'){
            			orderInfo = '';
            		} else {
            			orderInfo = '&nbsp;-&nbsp;('+orderInfo+')';
            		}
            		return '<a href="javascript:void(0)" style="color:blue;">' + data + '</a>' + orderInfo;
        		}
            },
            { "aTargets": [4], "mData": "appUser", "sClass": "todoListDisplayNone"},
            { "aTargets": [5], "mData": "appUserName", "aDataSort" : [ 1, 5 ]},
            { "aTargets": [6], "mData": "appTime", "aDataSort" : [ 1, 5 ],
            	"mRender": function ( data, type, full ) {
            		return moment(new Date(data)).format('YYYY/MM/DD HH:mm');
            	}
            },
            { "aTargets": [7], "mData": "orderStatus", "sClass": "todoListDisplayNone"},
            { "aTargets": [8], "mData": "orderStatusDesc", "aDataSort" : [ 1, 5 ]},
            { "aTargets": [9], "mData": "linkURL", "sClass": "todoListDisplayNone"}
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
	} );
	
	$('#workDatatable tbody').on( 'click','a', function () {
		var orderTypeDesc = $(this).closest("tr").find('td:eq(1)').text();
		var orderId = $(this).closest("tr").find('td:eq(2)').text();
		var url = $(this).closest("tr").find('td:eq(9)').text();
		openWorkPage(url, orderTypeDesc, orderId);
		
    });
});


</script>
<div class="row">
	<div id="breadcrumb" class="col-md-12">
		<ol class="breadcrumb">
			<li><a href="<s:url value="/index/" />">首頁</a></li>
			<li><a href="javascript:void(0);">待辦事項</a></li>
		</ol>
	</div>
</div>

<div class="row">
	<div class="col-xs-12 col-sm-12">
		<div class="box ui-draggable ui-droppable">
			<div class="box-header">
				<div class="box-name">
					<i class="fa fa-search"></i> <span>待辦工單</span>
				</div>
				<div class="box-icons">
					<a class="collapse-link"> <i class="fa fa-chevron-up"></i></a>
					<a class="expand-link"> <i class="fa fa-expand"></i></a>
				</div>
				<div class="no-move"></div>
			</div>
			<div class="box-content no-padding">
				<table id="workDatatable" class="table table-bordered table-striped table-hover table-heading table-datatable dataTables_wrapper" width="100%">
					<thead>
						<tr>
							<td style="display:none;">工作ID</td>
							<td>類型</td>
							<td style="display:none;">單號ID</td>
							<td>單號</td>
							<td style="display:none;">建立人NO</td>
							<td>建立人</td>
							<td>建立時間</td>
							<td style="display:none;">狀態代號</td>
							<td>狀態</td>
							<td style="display:none;">工單頁面URL</td>
						</tr>
					</thead>
					<tbody>
						<!-- Start: list_row -->
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>