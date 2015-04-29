<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>建站作業申請</title>

<script type="text/javascript">
	$(document)
			.ready(
					function() {
						buildCityOptions("siteWorkCity", "siteWorkArea");
						LoadSelect2Script(
								'<s:url value="/resources/plugins/select2/select2.min.js" />',
								Select2Test);
						WinMove();

						//重置
						$("#btn_reset").click(function() {
							$("#searchSiteCreateForm").trigger('reset');
							$("#grid").jqGrid().clearGridData();
							$('#workType').select2({ allowClear: true });
							$('#apzplyDept').select2({ allowClear: true });
							$('#siteWorkCity').select2({ allowClear: true }).change();
							$('#siteWorkArea').select2({ allowClear: true });
							$('#siteFeq').select2({ allowClear: true });
							$('#workStatus').select2({ allowClear: true });
							$('#eqpTypeId').select2({ allowClear: true });
						}); //重置 end

						//新增
						$('#btn_add').click(function() {
							$.fancybox.open({
								href : CONTEXT_URL + "/st/st004/insertPage",
								type : 'ajax',
								width : $(window).width(),
								height : $(window).width(),
								autoSize : false,
								padding : [ 0, 0, 0, 0 ],
								scrolling : 'yes',
								helpers : {
									overlay : {
										closeClick : false
									},
									title : {
										type : 'float'
									}
								},
								title : "新增",
								openEffect : 'elastic',
								closeEffect : 'fade',
								ajax : {
									type : 'POST'
								},
								afterClose : function() {
								}
							});
						});//addButton end

						//編輯
						$('#btn_edit').click(
								function() {
									var id = $("#grid").jqGrid('getGridParam',
											'selrow');
									if (id != "" && id != null) {
										var row = $("#grid").jqGrid(
												'getRowData', id);
										if (row.work_STATUS != "W01"
												&& row.work_STATUS != "W02" 
												&& row.work_STATUS != "W04") {
											alert("此處理狀態無法修改");
											return false;
										}
										updateSiteWork(row.work_ID, "edit");
									} else {
										alert("先選取一列資料列");
									}
								});//編輯 end

						//查詢
						$('#btn_search')
								.click(
										function() {
											var data = {
												workType : $
														.trim($("#workType")
																.val()), //工務類別
												applyDept : $.trim($(
														"#applyDept").val()), //申請單位
												siteWorkCity : $.trim($(
														"#siteWorkCity").val()), //行政區 
												siteWorkArea : $.trim($(
														"#siteWorkArea").val()), //行政區 
												eqpTypeId : $.trim($(
														"#eqpTypeId").val()), //設備型態 
												btsSiteId : $.trim($(
														"#siteWorkBtsSiteId")
														.val()), //基站編號 
												siteName : $.trim($(
														"#siteWorkSiteName")
														.val()), //基站名稱
												workStatus : $.trim($(
														"#workStatus").val()), //處理狀態
												siteFeq : $.trim($("#siteFeq")
														.val()),
											    rangeBa:$.trim($("#rangeBa").val()),//申請日起
											    rangeBb:$.trim($("#rangeBb").val())//申請日迄
											//基站頻段
											};
											$("#grid").setGridParam({
												datatype : "json",
												postData : data,
												mtype : 'POST',
												page : 1
											}).jqGrid().trigger("reloadGrid");
										});//查詢 end

						//查詢結果
						$("#grid")
								.jqGrid(
										{
											datatype : "local",
											pager : '#pager',
											url : CONTEXT_URL
													+ "/st/st004/search",
											colNames : [ '作業編號',
													'work_STATUS', '工務類別',
													'基站編號', '基站名稱', '基站頻段',
													'基站狀態', '處理狀態', '站點編號',
													'地址' ],
											colModel : [ {
												name : 'work_ID',
												index : 'work_ID',
												width : 100,
												sortable : false
											}, {
												name : 'work_STATUS',
												index : 'work_STATUS',
												hidden : true,
												sortable : false
											}, {
												name : 'work_TYPE',
												index : 'work_TYPE',
												width : 100,
												sortable : false
											}, {
												name : 'bts_SITE_ID',
												index : 'bts_SITE_ID',
												width : 100,
												sortable : false
											}, {
												name : 'site_NAME',
												index : 'site_NAME',
												width : 100,
												sortable : false
											}, {
												name : 'feqName',
												index : 'feqName',
												width : 80,
												sortable : false
											}, {
												name : 'siteStatus',
												index : 'siteStatus',
												width : 110,
												sortable : false
											}, {
												name : 'workStatusName',
												index : 'workStatusName',
												width : 80,
												sortable : false
											}, {
												name : 'location_ID',
												index : 'location_ID',
												width : 120,
												sortable : false
											}, {
												name : 'addr',
												index : 'addr',
												width : 200,
												sortable : false
											}, ],
											viewrecords : true,
											caption : "建站作業申請清單",
											rownumbers : true,
											multiselect : false,
											onSelectRow : function(id) {

											},
											gridComplete : function() {
												var ids = jQuery("#grid")
														.jqGrid('getDataIDs');
												for (var i = 0; i < ids.length; i++) {
													var cl = ids[i]; // cl:jqgrid的行數，非資料真正的HEADER_ID
													$("#grid").jqGrid(
															'getRowData', cl);
												}
											},
											ondblClickRow : function(rowId) { //double clicking a jqgrid row
												var rowData = jQuery(this)
														.getRowData(rowId);
												$.each(rowData, function(i,
														item) {
													if (i == "work_ID") {
														updateSiteWork(item,"view");
													}
												});

											}
										});//end grid

						$(window).resize(
								function() {
									$(window).unbind("onresize");
									$("#grid,.grid").setGridWidth(
											$("#jQgrid").width() - 10);
									$(window).bind("onresize", this);
								}).trigger('resize');
						//申請日 起
						$('#rangeBa').datepicker({
							dateFormat : "yy/mm/dd",
							changeYear : true,
							changeMonth : true,
							showButtonPanel : true
						});
						
						//申請日-迄
						$('#rangeBb').datepicker({
							dateFormat : "yy/mm/dd",
							changeYear : true,
							changeMonth : true,
							showButtonPanel : true
						});
					});//document.ready end

	function Select2Test() {
		$("select").select2();
	}

	function updateSiteWork(workId, status) {
		$.fancybox.open({
			href : CONTEXT_URL + "/st/st004/updatePage",
			type : 'ajax',
			width : $(window).width(),
			height : $(window).width(),
			autoSize : false,
			padding : [ 0, 0, 0, 0 ],
			scrolling : 'yes',
			helpers : {
				overlay : {
					closeClick : false
				},
				title : {
					type : 'float'
				}
			},
			title : "修改",
			openEffect : 'elastic',
			closeEffect : 'fade',
			ajax : {
				type : 'POST',
				data : {
					"workId" : workId,
					"status" : status
				}
			},
			afterClose : function() {
			}
		});
	}
</script>
</head>
<body>
	<div class="row">
		<div id="breadcrumb" class="col-md-12">
			<ol class="breadcrumb">
				<!-- button -->
				<c:import url="/auth/role/menu/func/${currentMenuId}" />
				<!-- button end-->
			</ol>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-12 col-sm-12">
			<div class="box ui-draggable ui-droppable">
				<div class="box-header">
					<div class="box-name">
						<i class="fa fa-search"></i> <span>建站作業申請查詢</span>
					</div>
					<div class="box-icons">
						<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
						</a> <a class="expand-link"> <i class="fa fa-expand"></i>
						</a>
					</div>
					<div class="no-move"></div>
				</div>
				<div class="box-content">
					<form role="form" class="form-horizontal bootstrap-validator-form"
						novalidate="novalidate" id="searchSiteCreateForm"
						action='<s:url value="/auth/systemMenu/"/>'>
						<div class="form-group">
							<!--  系統功能  -->
							<table style="width: 90%">
								<tr>
									<td width="15%"><label class="col-sm-12 control-label">工務類別:</label></td>
									<td width="35%">
										<div class="col-sm-8">
											<select id="workType" class="populate placeholder"
												name="country">
												<option value="">---請選擇---</option>
												<c:forEach items="${allWorkType}" var="workType">
													<option value="${workType.value}">${workType.label}</option>
												</c:forEach>
											</select>
										</div>
									</td>
									<td width="15%"><label class="col-sm-12 control-label">申請單位
											:</label></td>
									<td width="35%">
										<div class="col-sm-8">
											<select id="applyDept" class="populate placeholder"
												name="country">
												<option value="">---請選擇---</option>
												<c:forEach items="${siteWorkList}" var="siteWork">
													<option value="${siteWork.APP_DEPT}">${siteWork.deptName}</option>
												</c:forEach>
											</select>
										</div>
									</td>
								</tr>
								<tr>
									<td><div class="clearfix">&nbsp;</div></td>
								</tr>
								<tr>
									<td style="padding-top: 10px"><label
										class="col-sm-12 control-label">行政區 :</label></td>
									<td style="padding-top: 10px">
										<div class="col-sm-6">
											<select id="siteWorkCity" class="populate placeholder"
												name="country">
											</select>
										</div>
										<div class="col-sm-6">
											<select id="siteWorkArea" class="populate placeholder"
												name="country">
											</select>
										</div>
									</td>
									<td><label class="col-sm-12 control-label">設備型態 :</label></td>
									<td>
										<div class="col-sm-8">
											<select id="eqpTypeId" class="populate placeholder">
												<option value="">--- 請選擇 ---</option>
												<c:forEach items="${allEqpTypes}" var="eqpType">
													<option value="${eqpType.value}">${eqpType.label}</option>
												</c:forEach>
											</select>
										</div>
									</td>
								</tr>
								<tr>
									<td><div class="clearfix">&nbsp;</div></td>
								</tr>
								<tr>
									<td style="padding-top: 10px"><label
										class="col-sm-12 control-label">基站編號 :</label></td>
									<td style="padding-top: 10px">
										<div class="col-sm-8">
											<input id="siteWorkBtsSiteId" type="text"
												class="form-control" />
										</div>
									</td>
									<td style="padding-top: 10px"><label
										class="col-sm-12 control-label">基站名稱 :</label></td>
									<td style="padding-top: 10px">
										<div class="col-sm-8">
											<input id="siteWorkSiteName" type="text" class="form-control"
												placeholder="關鍵字查詢" />
										</div>
									</td>
								</tr>
								<tr>
									<td><div class="clearfix">&nbsp;</div></td>
								</tr>
								<tr>
									<td style="padding-top: 5px"><label
										class="col-sm-12 control-label">處理狀態 :</label></td>
									<td style="padding-top: 5px">
										<div class="col-sm-8">
											<select id="workStatus" class="populate placeholder"
												name="country">
												<option value="">---請選擇---</option>
												<c:forEach items="${allWorkStatus }" var="workStatus">
													<option value="${workStatus.value}">${workStatus.label}</option>
												</c:forEach>
											</select>
										</div>
									</td>
									<td style="padding-top: 5px"><label
										class="col-sm-12 control-label">基站頻段 :</label></td>
									<td style="padding-top: 5px">
										<div class="col-sm-8">
											<select id="siteFeq" class="populate placeholder"
												name="country">
												<option value="">---請選擇---</option>
												<c:forEach items="${allSiteFeq}" var="siteFeq">
													<option value="${siteFeq.value}">${siteFeq.label}</option>
												</c:forEach>
											</select>
										</div>
									</td>
								</tr>
								<tr>
									<td><div class="clearfix">&nbsp;</div></td>
								</tr>
								<tr>
									<td><label class="col-sm-12 control-label">申請日期(起) : </label></td>
									<td>
										<div class="col-sm-8">
											<input id="rangeBa" name="rangeBa" type="text"	class="form-control" readonly="readonly" placeholder="點選輸入框">
										</div>
									</td>
									
									<td nowrap="nowrap"><label class="col-sm-12 control-label">申請日期(迄) : </label></td>
										<td>
										<div class="col-sm-8">
											<input id="rangeBb" name="rangeBb" type="text" class="form-control" readonly="readonly" placeholder="點選輸入框">
										</div>
									</td>
								</tr>
							</table>
							<!--  系統功能 end -->
						</div>
					</form>
				</div>
			</div>
		</div>
		<div id="ajaxSearchResult" class="col-xs-12">
			<!-- 		固定id for window.resize start -->
			<div id="jQgrid">
				<table id="grid"></table>
				<div id="pager"></div>
			</div>
			<!-- 	 	固定id for window.resize end -->
		</div>
	</div>
</body>
</html>