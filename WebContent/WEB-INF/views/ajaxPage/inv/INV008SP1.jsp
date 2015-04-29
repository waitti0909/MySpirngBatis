<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
<head>

<script type="text/javascript">
	$(document).ready(function() {
		
		$('#inv008SP1Grid').jqGrid({
			datatype : "local",
			colNames : [ '廠商序號'],
			colModel : [ {name : 'venSn',index : 'venSn',align : 'center',sortable : false},
						],
			viewrecords : true,
			caption : "已選廠商序號",
			rownumbers : false,
			multiselect : true,
			loadonce: true,
			rowNum:-1,
			onSelectRow : function(id) {
			},
			gridComplete : function() {
			},
			ondblClickRow : function(rowId) {
			},
			gridComplete:function(){
			}
		});
		
		var list = '${tbInvTrDtlDto.selectVenSnStrList}';
		
		var venSn = list.substring(1, (list.length-1)).split(",");
		
		var array = list.substring(1, (list.length-1)).split(",");
		
		if(venSn[0] != ""){
			for(var i=0 ; i<venSn.length ; i++){
				$("#inv008SP1Grid").jqGrid("addRow",{initdata:{'venSn':venSn[i]}});
			}
		}
		
		$('#deleteBut').click(function(){
			
			array=[];
			
			var selectRowId = jQuery('#inv008SP1Grid').jqGrid('getGridParam','selarrrow');
			
			var allRowsId = $('#inv008SP1Grid').jqGrid('getDataIDs');
			
			for(var i = 0 ;i<allRowsId.length;i++){
				
				var row = jQuery('#inv008SP1Grid').jqGrid('getRowData',allRowsId[i]);
				
				if(selectRowId.indexOf(allRowsId[i]) != -1){
					$('#'+allRowsId[i]).hide();
					continue;
				}
				
				array.push($.trim(row.venSn));
			}
			
		});
		
		$('#saveBut').click(function(){
	
			if(array.length == 1 && array[0]==""){
				array=[];
			}
			
			window['changeRowData'](array);
			
			$("#certificatePage").dialog('close');
		});
		
		
	});
</script>
</head>
<body>
	<div id="inv008SP1Result" class="col-xs-12">
		<div id="inv008SP1JQgrid" align="center">
			<button class="btn btn-primary btn-label-left" type="button" id="saveBut" >
				<span>
					<i class="fa fa-check"></i>
				</span> 
				確定
			</button>
			<button class="btn btn-danger btn-label-left" type="button" id="deleteBut" >
				<span>
					<i class="fa fa-trash-o"></i>
				</span> 
				刪除
			</button>
			<table id="inv008SP1Grid"></table>
			<div id="inv008SP1Page"></div>
		</div>
	</div>
</body>
</html>