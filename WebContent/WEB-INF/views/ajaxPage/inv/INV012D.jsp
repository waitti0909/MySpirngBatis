<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>領料單明細</title>
<script src="<s:url value="/resources/plugins/tinymce/tinymce.min.js" />"></script>
<script src="<s:url value="/resources/plugins/tinymce/jquery.tinymce.min.js" />"></script>
<script type="text/javascript">
	$(document).ready(function() {
		
		LoadSelect2Script("<s:url value="/resources/plugins/select2/select2.min.js" />", selectDefault);
	
		$("#dtlgrid").jqGrid({
			datatype : "local",
			pager : '#dtlpager',
			autowidth:'true',
			url : CONTEXT_URL + "/inv/inv012/searchDtl/",
			colNames : ['料號','品名','申請數','已發料數',''],
			colModel : [ 
			 	{name : 'mat_NO',index : 'mat_NO', align : 'center',sortable : false},
			    {name : 'matName',index : 'matName', align : 'center',sortable : false},
			    {name : 'qty',index : 'qty', align : 'center',sortable : false,formatter: nullForZero}, 
			    {name : 'mrQty',index : 'mrQty', align : 'center',sortable : false,formatter: nullForZero},
			    {name : 'dtl_SEQ',index : 'dtl_SEQ', align : 'center',sortable : false,hidden:true}
			   ],
			caption : "明細資料",
			rownumbers : true,
			onSelectRow : function(id) {
				var rowData = jQuery(this).getRowData(id);
				var data = {
						optId:$("#hiddenOptId").val(),
						matNo:rowData.mat_NO,
						dtlSeq:rowData.dtl_SEQ
					};
				$("#historygrid").setGridParam({datatype:"json", postData:data, mtype: 'POST',page:1}).trigger("reloadGrid");
			}
		});
		
		$("#historygrid").jqGrid({
			datatype : "local",
			pager : '#historypager',
			autowidth:'true',
			url : CONTEXT_URL + "/inv/inv012/searchDtlHistory/",
			colNames : ['發料單號','發料倉庫','實發料號','廠商序號','貼標號碼','發料日期','發料人員','發料數',''],
			colModel : [ 
			 	{name : 'txn_no',index : 'inv_txn_no', align : 'center',sortable : false},
			    {name : 'wh_name',index : 'wh_name', align : 'center',sortable : false},
			    {name : 'mat_no',index : 'mat_no', align : 'center',sortable : false},
			    {name : 'venSn',index : 'venSn', align : 'center',sortable : false}, 
			    {name : 'tagNo',index : 'tagNo', align : 'center',sortable : false},
			    {name : 'cr_time',index : 'cr_time', align : 'center',sortable : false,
			    	formatter: function(cellvalue, options, rowObject){
			  			if (cellvalue != null && cellvalue != "") {
				   			var stDate = new Date(cellvalue);
							return stDate.getFullYear() + "/" + (stDate.getMonth() + 1) + "/" + stDate.getDate()+ " " + addZero(stDate.getHours()) +":"+addZero(stDate.getMinutes());
			  			}else {
			  				return "";
			  			}
			  		}
			   	},
	   			{name : 'appUserName',index : 'appUserName', align : 'center',sortable : false},
			    {name : 'qty',index : 'qty', align : 'center',sortable : false,formatter: nullForZero},
			    {name : 'cr_user',index : 'cr_user', align : 'center',sortable : false,hidden:true}],
			caption : "歷程資料",
			rownumbers : true,
			onSelectRow : function() {}
		});
		
		$(window).resize(function() {
			$(window).unbind("onresize");
			$("#dtlgrid,.grid").setGridWidth($("#jQgrid").width() - 10);
			$("#historygrid,.grid").setGridWidth($("#jQgrid").width() - 10);
			$(window).bind("onresize", this);
		}).trigger('resize');
				
	});
	
	// 開啟下拉式選單搜尋文字功能
	function selectDefault() {
		$("#bBoardAddEdit").find("select").select2();
	}
	
	//格式化grid內數字
	function nullForZero(cellvalue, options, rowObject) {
		var returnValue = cellvalue;
		if (cellvalue == "" || cellvalue == "undefined" || cellvalue == null) {
			returnValue = "0";
		}
		return Math.abs(returnValue);
	}	
	
	function addZero(i) {
	    if (i < 10) {
	        i = "0" + i;
	    }
	    return i;
	}			
			
</script>
</head>
<body>
	<div class="box-header">
		<div class="box-name">
			<i class="fa fa-edit"></i> <span>領料單明細</span>
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
					<form class="form-horizontal" id="bBoardAddEdit">
						<input id="hiddenOptId" type="hidden" value="${masterDscr.optId}">
						<div class="form-group">
							<label class="col-sm-2 control-label">領料單號：</label>
							<div class="col-sm-3">
								<label class="control-label">${masterDscr.optId}</label>
							</div>
							<label class="col-sm-2 control-label">發料狀態：</label>
							<div class="col-sm-3">
								<label class="control-label">${masterDscr.statusDscr}</label>
							</div>
						</div>
						<div class="form-group" style="padding-top: 5px;">
							<label class="col-sm-2 control-label">申請單位：</label>
							<div class="col-sm-3">
								<label class="control-label">${masterDscr.deptIdDscr}</label>
							</div>
							<label class="col-sm-2 control-label">申請人：</label>
							<div class="col-sm-3">
								<label class="control-label">${masterDscr.appUser}</label>
							</div>
						</div>
						<div class="form-group" style="padding-top: 5px;">
							<label class="col-sm-2 control-label">站台：</label>
							<div class="col-sm-2">
								<label class="control-label">${masterDscr.siteIdDscr}</label>
							</div>
						</div>
						
						<div class="form-group" style="padding-top: 5px;">
							<div class="row">
								<div class="col-md">
									<div id="ajaxSearchResult" class="col-xs-12">
										<div id="jQgrid" align="center">
											<table id="dtlgrid"></table>
											<div id="dtlpager"></div>
										</div>
									</div>
								</div>
							</div>
						</div>

						<div class="form-group" style="padding-top: 5px;">
							<div class="row">
								<div class="col-md">
									<div id="ajaxSearchResult" class="col-xs-12">
										<div id="jQgrid" align="center">
											<table id="historygrid"></table>
											<div id="historypager"></div>
										</div>
									</div>
								</div>
							</div>
						</div>
						
					</form>
				</div>
			</div>
		</div>
	</div>
	</div>
	</div>
</body>
</html>