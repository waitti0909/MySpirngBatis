<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>調撥申請作業-明細</title>
<script
	src="<s:url value="/resources/plugins/tinymce/tinymce.min.js" />"></script>
<script
	src="<s:url value="/resources/plugins/tinymce/jquery.tinymce.min.js" />"></script>
<script type="text/javascript">

	////Run Select2 on element
	function Select2Priority() {
		$("#addEdit_PRIORITY").select2();
	}
	function Select2Type() {
		$("#addEdit_TYPES").select2();
	}

	$(document).ready(function() {
						$("#dtlgrid").jqGrid({
											datatype : "local",
											pager : '#dtlpager',
											shrinkToFit: true,
											url : CONTEXT_URL + "/inv/inv007/searchDtl/",
											colNames : [  '料號', '品名', '物料狀態', '申請調撥數量','已調出量','已調入量' ],
											colModel : [ 
											 	{name : 'mat_no',index : 'mat_no',align : 'center',sortable : false},
											    {name : 'matName',index : 'material.mat_name',align : 'center',sortable : false},
											    {name : 'matStatusName',index : 'matStatusName', align : 'center',sortable : false}, 
											    {name : 'app_qty',defval:0,index : 'app_qty', align : 'center',sortable : false,formatter: nullForZero},
											    {name : 'trOutCnt',index : 'trOutCnt', align : 'center',sortable : false,formatter: nullForZero}, 
											    {name : 'trInCnt',index : 'trInCnt', align : 'center',sortable : false,formatter: nullForZero} 
											   ],
											multiselect :false,
											caption : "調撥明細資料",
											rownumbers : true,
											
											onSelectRow : function() {
												// do something when select a row
											},
											gridComplete : function() {
												// do something after grid loaded
											}
									});//end grid	
									
				$(window).resize(function() {
							$(window).unbind("onresize");
							$("#dtlgrid,.dtlgrid").setGridWidth($("#jQgrid").width());
							$(window).bind("onresize", this);
					}).trigger('resize');
					
					var data = {
						trNo : $("#tr_no").val()
					};
				$("#dtlgrid").setGridParam({datatype : "json",postData : data,page:1}).trigger("reloadGrid");
			});
			function nullForZero (cellvalue, options, rowObject)
			{
			   var returnValue=cellvalue;
			   if(cellvalue == "" || cellvalue == "undefined" || cellvalue == null){
			   			returnValue="0";
			   }
			   return returnValue;
			}
			
</script>
</head>
<body>
	<div class="box-header">
		<div class="box-name">
			<i class="fa fa-edit"></i> <span>調撥申請作業-明細</span>
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
					<form class="form-horizontal" id="bBoardAddEdit" />
					<input id="trNo" type='hidden' value="${trNo}" /> 
						<div class="form-group">
							<div class="row">
								<label class="col-sm-1 control-label">調撥單號：</label>
								<div class="col-sm-2">
									<input id="tr_no" maxlength="20" type="text"
										class="form-control" name="tr_no" disabled="disabled"
										value="${invTrDscr.tr_no}">
								</div>
								<label class="col-sm-1 control-label">調撥進度：</label>
								<div class="col-sm-2">
									<input id="statusDscr" maxlength="20" type="text"
										class="form-control" name="statusDscr" disabled="disabled"
										value="${invTrDscr.statusDscr}">
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="row">
									<label class="col-sm-1 control-label">調出倉庫：</label>
									<div class="col-sm-2">
										<input id="trOutWhIdDscr" maxlength="20" type="text"
									class="form-control" name="trOutWhIdDscr" disabled="disabled"
									value="${invTrDscr.trOutWhIdDscr}">
									</div>
									<label class="col-sm-1 control-label">調入倉庫：</label>
									<div class="col-sm-2">
										<input id="trInWhIdDscr" maxlength="20" type="text"
									class="form-control" name="trInWhIdDscr" disabled="disabled"
									value="${invTrDscr.trInWhIdDscr}">
									</div>
							</div>
						</div>
						<div class="form-group">
							<div class="row">
									<label class="col-sm-1 control-label">需求單位：</label>
									<div class="col-sm-2">
										<input id="needDeptIdDscr" maxlength="20" type="text"
									class="form-control" name="needDeptIdDscr" disabled="disabled"
									value="${invTrDscr.needDeptIdDscr}">
									</div>
									<label class="col-sm-1 control-label">需求日期：</label>
									<div class="col-sm-2">
										<input id="need_date" type="text" disabled="disabled"
											name="need-date" class="form-control"
											value="<fmt:formatDate  value="${invTrDscr.need_date}"  pattern="yyyy/MM/dd HH:mm"  />">
									</div>
							</div>
						</div>
						<div class="form-group">
							<div class="row">
									<label class="col-sm-1 control-label">申請單位：</label>
									<div class="col-sm-2">
										<input id="appDeptIdDscr" maxlength="20" type="text"
									class="form-control" name="appDeptIdDscr" disabled="disabled"
									value="${invTrDscr.appDeptIdDscr}">
									</div>
									<label class="col-sm-1 control-label">申請人：</label>
									<div class="col-sm-2">
										<input id="appUserDscr" maxlength="20" type="text"
									class="form-control" name="appUserDscr" disabled="disabled"
									value="${invTrDscr.appUserDscr}">
									</div>
							</div>
						</div>
						<div class="form-group">
							<div class="row">
									<label class="col-sm-1 control-label">申請日期：</label>
									<div class="col-sm-2">
										<input id="app_date" type="text" disabled="disabled"
											name="app_date" class="form-control"
											value="<fmt:formatDate  value="${invTrDscr.app_date}"  pattern="yyyy/MM/dd HH:mm"  />">
									</div>
							</div>
						</div>
						<div class="form-group">
							<div class="row">
								<div class="col-md">
									<label class="col-sm-1 control-label">備註：</label>
									<div class="col-sm-9">
									<textarea class="form-control" rows="4" id=memo name="memo"
												value="">${invTrDscr.memo}</textarea>
									</div>
								</div>
							</div>
						<div class="form-group">
							<div class="row">
								<div class="col-md">
									<label class="col-sm-1 control-label">申請明細：</label>
									<div id="ajaxSearchResult" class="col-sm-9 "
										style="width: 1000px;">
										<!-- 固定id for window.resize start-->
										<div id="jQgrid" align="center">
											<table id="dtlgrid"></table>
											<div id="dtlpager"></div>
										</div>
									</div>
								</div>
							</div>
							<hr>
							<div class="row">
								<div class="col-md">
										<label class="col-sm-1 control-label">簽核意見 : </label>
										<div class="col-sm-9"style="width: 1000px;">
											<c:import url="/commom/signHistory">
												<c:param name="processType" value="${invTrDscr.processType}"></c:param>
												<c:param name="applyNo" value="${trNo}"></c:param>
											</c:import>
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
</body>
</html>