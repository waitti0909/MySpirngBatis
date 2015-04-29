<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>發料單查詢</title>

<script src="<s:url value="/resources/plugins/tinymce/tinymce.min.js" />"></script>
<script src="<s:url value="/resources/plugins/tinymce/jquery.tinymce.min.js" />"></script>
<script	src="<s:url value="/resources/js/validate.js" />"></script>

<script type="text/javascript">
	$(document).ready(function() {
		LoadSelect2Script("<s:url value="/resources/plugins/select2/select2.min.js" />", selectDefault);
		
		var Today=new Date();
		
		$('#crStartDate').prop("value",Today.getFullYear()+ "/" + (Today.getMonth()+1) + "/" + (Today.getDate()-7));
		$('#crEndDate').prop("value",Today.getFullYear()+ "/" + (Today.getMonth()+1) + "/" + Today.getDate());
		
		//申請部門連動申請人
		$("#txnDeptSelect").change(onDeptSelectChange);
		
		//發料日起始日曆						
		$('#crStartDate').datepicker({
			dateFormat : "yy/mm/dd",
			changeYear : true,
			changeMonth : true,
			showButtonPanel : true,
			showTime : false,
			defaultDate: -7
		});
		
		//發料日結束日曆
		$('#crEndDate').datepicker({
			dateFormat : "yy/mm/dd",
			changeYear : true,
			changeMonth : true,
			showButtonPanel : true,
			showTime : false
		});
		
		$("#mastergrid").jqGrid({
			datatype : "local",
			pager : '#masterpager',
			autowidth:'true',
			url : CONTEXT_URL + "/inv/inv012/searchTxnMaster",
			colNames : ['發料單號','發料倉庫','領料人員','發料日期','',''],
			colModel : [ 
			 	{name : 'txn_no',index : 'txn_no', align : 'center',sortable : false},
			    {name : 'wh_name',index : 'wh_name',align : 'center',sortable : false},
			    {name : 'cr_name',index : 'cr_name',align : 'center',sortable : false}, 
			    {name : 'crTime',index : 'crTime',align : 'center',sortable : false,
			    	formatter: function(cellvalue, options, rowObject){
			   			if (cellvalue != null && cellvalue != "") {
				   			var stDate = new Date(cellvalue);
				   			
							return stDate.getFullYear() + "/" + (stDate.getMonth() + 1) + "/" + stDate.getDate() /* + " " + addZero(stDate.getHours()) +":"+addZero(stDate.getMinutes() )*/;
			   			}else {
			   				return "";
			   			}
		   			}},
   				{name : 'wh_id',index : 'wh_id', align : 'center',sortable : false,hidden:true},
   				{name : 'cr_user',index : 'cr_user',align : 'center',sortable : false,hidden:true}
			   ],
			//multiselect : true,
			caption : "發料資料",
			rownumbers : true,
			onSelectRow : function(id) {
				var rowData = jQuery(this).getRowData(id);
				var data = {
						txnNo:rowData.txn_no
					};
				$("#dtlgrid").setGridParam({datatype:"json", postData:data, mtype: 'POST',page:1}).jqGrid().trigger("reloadGrid");
			}
		});	
		
		$("#dtlgrid").jqGrid({
			datatype : "local",
			pager : '#dtlpager',
			autowidth:'true',
			url : CONTEXT_URL + "/inv/inv012L/searchTxnDetail",
			colNames : ['領料單號','料號','品名','廠商序號','貼標號碼','發料數'],
			colModel : [ 
			 	{name : 'ref_act_no',index : 'ref_act_no', align : 'center',sortable : false},
			    {name : 'mat_no',index : 'mat_no', align : 'center',sortable : false},
			    {name : 'matName',index : 'matName', align : 'center',sortable : false}, 
			    {name : 'venSn',index : 'venSn', align : 'center',sortable : false},
			    {name : 'tagNo',index : 'tagNo', align : 'center',sortable : false},
			    {name : 'qty',index : 'qty', align : 'center',sortable : false,formatter: nullForZero}
			   ],
			caption : "發料明細",
			rownumbers : true,
			onSelectRow : function() {}
		});
		
		$(window).resize(function() {
			$(window).unbind("onresize");
			$("#mastergrid,.grid").setGridWidth($("#jQgrid").width() - 10);
			$("#dtlgrid,.grid").setGridWidth($("#jQgrid").width() - 10);
			$(window).bind("onresize", this);
		}).trigger('resize');
		
		//查詢
		$('#qryBtn').click(function() {
			var txnNo = $("#txnNo").val();
			var wareHouse = $("#txnWareHouseSelect").find("option:selected").val(); 
			//var dept = $("#txnDeptSelect").find("option:selected").val(); 
			
			var crStartDate = $("#crStartDate").val(); 
			var crEndDate = $("#crEndDate").val(); 
			
			/*
			var personnel = $("#txnPersonnelSelect").find("option:selected").val(); 
			
			 if( personnel == "" || personnel == null){
				alert("請選擇發料人員");
				return false;
			}
			
			if(crStartDate == "" || crStartDate == null || crEndDate == "" || crEndDate == null){
				alert("請選擇發料日期");
				return false;					
			} */
			$("#mastergrid").jqGrid().clearGridData();
			if(txnNo == ""){
				if( wareHouse == "" || wareHouse == null){
				alert("請選擇發料倉庫");
				return false;
				}
				dateCheck(crStartDate,crEndDate,"發料日期");
				var data = {
					wareHouse : $("#txnWareHouseSelect").find("option:selected").val(),
					crStartDate : $("#crStartDate").val(),
					crEndDate : $("#crEndDate").val(),
					txnType : "1",
					txnNo : ""
				};
				$("#mastergrid").setGridParam({datatype:"json", postData:data, mtype: 'POST',page:1}).trigger("reloadGrid");
			}else{
				 var data2 = {
				 	wareHouse : "",
					crStartDate : "",
					crEndDate : "",
					txnNo : $("#txnNo").val(),
					txnType : "1"
				};
				$("#mastergrid").setGridParam({datatype:"json", postData:data2, mtype: 'POST',page:1}).trigger("reloadGrid");
			}
			/* var data = {
				txnNo : txnNo,
				wareHouse : wareHouse,
				dept : dept,
				crStartDate : crStartDate,
				crEndDate : crEndDate,
				personnel : personnel,
				txnType : "1"
			}; */
			$("#dtlgrid").jqGrid().clearGridData();
		});
					
		//列印簽收單
		$('#printBtn').click(function() {				
			//var selr = jQuery('#mastergrid').jqGrid('getGridParam','selarrrow');
			var selr = jQuery('#mastergrid').jqGrid('getGridParam','selrow');
			if(selr.length==0){
				alert("列印簽收單資料前，請先勾選一筆資料");
				return ;
			}
				
			if(selr.length > 1){
				alert("只能勾選一筆資料");
				return ;
			}
			
			var myrow = jQuery('#mastergrid').jqGrid('getRowData',selr[0]);							
			window.location.href = CONTEXT_URL+"/inv/inv012/printSignPage?txnNo="+myrow.txn_no+"&whName="+myrow.wh_name+"&applicant="+myrow.cr_name+"&sendDate="+myrow.crTime;
		});
	});
	
	// 申請部門連動申請人
	function onDeptSelectChange() {
		$("#txnPersonnelSelect").html("");
		$("<option value=''>--請選擇--</option>").appendTo("#txnPersonnelSelect");
		$("#txnPersonnelSelect").select2();
		
		if ($("#txnDeptSelect").val() == "") {
			return false;
		}
		
		var data = {
			dept : $("#txnDeptSelect").find("option:selected").prop("value")
		};
		
		$.ajax({
			url : CONTEXT_URL + "/ajax/inv/public/personnelDept/",
			data : data,
			type : "POST",
			dataType : 'json',
			success : function(data) {
				if (data.success) {
					if (data.rows.length > 0) {
						for (var i = 0; i < data.rows.length; i++) {
							$("#txnPersonnelSelect").append("<option value="+data.rows[i].psn_NO+">"+ data.rows[i].chn_NM+ "</option>");
						}
					}
				}
			}
		});
	}
	
	// 開啟下拉式選單搜尋文字功能
	function selectDefault() {
		$("#bBoardAddEdit").find("select").select2();
		$("#txnWareHouseSelect").select2();
		$("#txnDeptSelect").select2();
		$("#txnPersonnelSelect").select2();
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
	<div id="breadcrumb" class="col-md-12">
		<ol class="breadcrumb">
			<div class="col-sm-12" id="btnDiv">
				<button class="btn btn-primary btn-label-left" type="button" id="qryBtn">
					<span>
						<i class="fa fa-save"></i>
					</span>
					查詢 
				</button>
				<button class="btn btn-primary btn-label-left" type="button" id="printBtn">
					<span>
						<i class="fa fa-save"></i>
					</span>
					列印簽收單
				</button>
			</div>
		</ol>
	</div>
	<div class="row">
		<div class="col-xs-12 col-sm-12">
			<div class=" ui-draggable ui-droppable">
			
				<div class="box-header">
					<div class="box-name">
						<i class="fa fa-edit"></i> <span>發料單作業功能 查詢條件</span>
					</div>
					<div class="box-icons">
						<p class="expand-link"></p>
					</div>
					<div class="no-move"></div>
				</div>
				
				<div class="box-content">
					<form class="form-horizontal bootstrap-validator-form" id="bBoardAddEdit" >
						<table style="width: 100%">
							<tr>
								<td><label class="col-sm-12 control-label">發料單號：</label></td>
								<td>
									<div class="col-sm-6">
										<input id="txnNo" maxlength="20" type="text" class="form-control" name="txnNo" value="">
									</div>
								</td>
								<td><label class="col-sm-12 control-label">發料倉庫：</label></td>
								<td>
									<div class="col-sm-12">
										<select id="txnWareHouseSelect" name="txnWareHouseSelect">
											<option value="" selected>--請選擇--</option>
											<c:forEach items="${wareHouseSelect}" var="wareHouse">
												<option value="${wareHouse.wh_id}">${wareHouse.wh_name}</option>
											</c:forEach>
										</select>
									</div>
								</td>
								<td  class="col-sm-1"></td>
								<td  class="col-sm-1"></td>
							</tr>
							<tr>
								<td><div class="clearfix">&nbsp;</div></td>
							</tr>
							<%-- <tr>
								<td><label class="col-sm-12 control-label">發料單位：</label></td>
								<td>
									<div class="col-sm-6">
										<select id="txnDeptSelect" name="txnDeptSelect">
											<option value="" selected>--請選擇--</option>
											<c:forEach items="${deptSelect}" var="dept">
												<option value="${dept.DEPT_ID}">${dept.DEPT_NAME}</option>
											</c:forEach>
										</select>
									</div>
									</td>
								<td><label class="col-sm-12 control-label"><span class="s">* </span>發料人員：</label></td>
								<td>
									<div class="col-sm-12">
										<select id="txnPersonnelSelect" name="txnPersonnelSelect">
											<option value="" selected>--請選擇--</option>
										</select>
									</div>
								</td>
							</tr> --%>
							<tr>
								<td><div class="clearfix">&nbsp;</div></td>
							</tr>
							<tr>
								<td><label class="col-sm-12 control-label"><span class="s">* </span>發料日期：</label></td>
								<td>
									<div class="col-sm-12">
										<input id="crStartDate" type="text" value="" name="crStartDate" readonly="readonly"> 
										至
										<input id="crEndDate" type="text" value="" name="crEndDate" readonly="readonly">
									</div>
								</td>
							</tr>
						</table>

						<div class="clearfix">&nbsp;</div>
							
						<div class="col-md">
							<div id="ajaxSearchResult" class="col-xs-12">
								<div id="jQgrid" align="center">
									<table id="mastergrid"></table>
									<div id="masterpager"></div>
								</div>
							</div>
						</div>
					
						<div class="clearfix">&nbsp;</div>
							
						<div class="col-md">
							<div id="ajaxSearchResult" class="col-xs-12">
								<div id="jQgrid" align="center">
									<table id="dtlgrid"></table>
									<div id="dtlpager"></div>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>