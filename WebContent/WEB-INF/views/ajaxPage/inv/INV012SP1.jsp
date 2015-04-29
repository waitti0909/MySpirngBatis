<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>查詢退料站台</title>
<script
	src="<s:url value="/resources/plugins/tinymce/tinymce.min.js" />"></script>
<script
	src="<s:url value="/resources/plugins/tinymce/jquery.tinymce.min.js" />"></script>
	<script	src="<s:url value="/resources/js/validate.js" />"></script>
<script type="text/javascript">
	$(document).ready(function() {	
			var Today=new Date();
			var TodayLess=(new Date());
			TodayLess.setDate(TodayLess.getDate()-7);
			$('#crStartDate').prop("value",TodayLess.getFullYear()+ "/" + (TodayLess.getMonth()+1) + "/" + (TodayLess.getDate()-7));
			$('#crEndDate').prop("value",Today.getFullYear()+ "/" + (Today.getMonth()+1) + "/" + (Today.getDate()));
						//收料日起始日曆						
						$('#crStartDate').datepicker({
							dateFormat : "yy/mm/dd",
							changeYear : true,
							changeMonth : true,
							showButtonPanel : true,
							showTime : false
						});
						//收料日結束日曆
						$('#crEndDate').datepicker({
							dateFormat : "yy/mm/dd",
							changeYear : true,
							changeMonth : true,
							showButtonPanel : true,
							showTime : false
						});
						$("#matNoOption").hide();				
						
					});
					$("#sp1mastergrid").jqGrid({
											datatype : "local",
											pager : '#sp1masterpager',
											autowidth:'true',
											url : CONTEXT_URL + "/inv/inv012/searchTxnMaster/",
											colNames : ['退料單號','站台','退料申請日','收料日期','收料倉'],
											colModel : [ 
											 	{name : 'txn_no',index : 'txn_no', align : 'center',sortable : false},
											    {name : 'site_name',index : 'site_name', align : 'center',sortable : false},
											    {name : 'appTime',index : 'appTime',align : 'center',sortable : false,formatter: function(cellvalue, options, rowObject){
											    if (cellvalue != null && cellvalue != "") {
									   			var stDate = new Date(cellvalue);
													return stDate.getFullYear() + "/" + (stDate.getMonth() + 1) + "/" + stDate.getDate() 
												+ " " + addZero(stDate.getHours()) +":"+addZero(stDate.getMinutes());
									   			}else {
									   				return "";
									   			}}},
									   			{name : 'cr_time',index : 'cr_time', align : 'center',sortable : false,formatter: function(cellvalue, options, rowObject){
											    if (cellvalue != null && cellvalue != "") {
									   			var stDate = new Date(cellvalue);
													return stDate.getFullYear() + "/" + (stDate.getMonth() + 1) + "/" + stDate.getDate() 
												+ " " + addZero(stDate.getHours()) +":"+addZero(stDate.getMinutes());
									   			}else {
									   				return "";
									   			}}},
											    {name : 'wh_name',index : 'wh_name', align : 'center',sortable : false}
											   ],
											caption : "收料清單",
											rownumbers : true,											
											onSelectRow : function(id) {
											var rowData = jQuery(this).getRowData(id);
											var data = {
													txnNo:rowData.txn_no
												};
												$("#sp1dtlgrid").setGridParam({datatype:"json", postData:data, mtype: 'POST',page:1}).jqGrid().trigger("reloadGrid");
											}
									});//end grid	
									$(window).resize(function() {
												$(window).unbind("onresize");
												$("#sp1mastergrid,.grid").setGridWidth($("#jQgrid").width() - 10);
												$(window).bind("onresize", this);
											}).trigger('resize');
				
						$("#sp1dtlgrid").jqGrid({
										datatype : "local",
										pager : '#sp1dtlpager',
										autowidth:'true',
										url : CONTEXT_URL + "/inv/inv012/searchWithTxnNoForSp1/",
										colNames : ['料號','品名','廠商序號','貼標號碼','收料數','',''],
										colModel : [ 
										 	{name : 'mat_no',index : 'mat_no',align : 'center',sortable : false},
										    {name : 'matName',index : 'matName', align : 'center',sortable : false}, 
										    {name : 'tagNo',index : 'tagNo', align : 'center',sortable : false},
										    {name : 'venSn',index : 'venSn', align : 'center',sortable : false},
										    {name : 'qty',index : 'qty', align : 'center',sortable : false,formatter: nullForZero},
										    {name : 'fa_no',index : 'fa_no', align : 'center',sortable : false,hidden:true},
										    {name : 'srl_no',index : 'srl_no', align : 'center',sortable : false,hidden:true}
										   ],
										multiselect : true,
										caption : "收料歷程",
										rownumbers : true,
										onSelectRow : function() {
										}
								});//end grid	
								$(window).resize(function() {
											$(window).unbind("onresize");
											$("#sp1dtlgrid,.grid").setGridWidth($("#jQgrid").width() - 10);
											$(window).bind("onresize", this);
										}).trigger('resize');
					//查詢
					$('#qryBtn').click(function() {
						/* var siteId = $("#qrySiteId").attr("realValue"); */
						var siteId = $("#qrySiteId").val();
						var crStartDate = $("#crStartDate").val(); 
						var crEndDate = $("#crEndDate").val(); 
						
						if( siteId == "" || siteId == null){
							alert("請選擇基站編號");
							return false;
						}
						if(crStartDate == "" || crStartDate == null || crEndDate == "" || crEndDate == null){
							alert("請選擇收料日期");
							return false;					
						}		
						dateCheck(crStartDate,crEndDate,"收料日期");	
						var data = {
							siteId : siteId,						
							crStartDate : crStartDate,
							crEndDate : crEndDate,
							txnType : "2"
						}
						$("#sp1mastergrid").setGridParam({datatype:"json", postData:data, mtype: 'POST',page:1}).jqGrid().trigger("reloadGrid");
						$("#sp1dtlgrid").jqGrid().clearGridData();
					});
					//選擇
					$('#choiceBtn').click(function() {
						var selr = jQuery('#sp1dtlgrid').jqGrid('getGridParam','selarrrow');
							if(!selr.length==0) {
								if(!matListCheck(selr))
									return false;
								for(var i=0;i<selr.length;i++){
									var myrow = jQuery('#sp1dtlgrid').jqGrid('getRowData',selr[i]);									
									var matObjectList = {};
									var matObject= [];
									
									matObjectList.matObject = matObject;
										  var matData={
											  "mat_no": myrow.mat_no,
											  "matName": myrow.matName,
											  "venSn": myrow.venSn,
											  "tagNo" : myrow.tagNo,
											  "qty" : myrow.qty,
											  "faNo" : myrow.fa_no,
											  "srlNo" : myrow.srl_no
										  };
										  matObjectList.matObject.push(matData);
									}
									var callBackFun = "${master.callBackFun}";
										if (typeof window[callBackFun] === "function") {
											window[callBackFun](JSON.stringify(matObjectList));
										}
										$("#showMsPag").dialog('close');
							}else{
								alert("按選擇鈕前，請先勾選一筆明細資料");
								return false;
							}
					});
					
//=========Function=============	
					$("#qrySiteIdSearch").click(function() {
						openSiteDialogFrame("qrySelectSitePage", "receviceSiteData"); // call common.js
					});
					 function matListCheck(selr){
					 var foo = [];
					 var matNotIn = [];
					 var lastCheck="";
					  $('#matNoOption option').each(function(i, selected){
							  foo.push($(selected).val()); 
							});
					 	for(var i=0;i<selr.length;i++){
							var rowData = jQuery('#sp1dtlgrid').jqGrid('getRowData', selr[i]);
							if($.inArray(rowData.mat_no,foo) == -1) {lastCheck="N";matNotIn.push(rowData.mat_no);};
						}
						 if(lastCheck == "N"){
							alert("料號 [ "+matNotIn+" ]不存在於發料清單中請重新選擇!!");
							return false;
						} 
						return true;
					} 
					
					function addZero(i) {
					    if (i < 10) {
					        i = "0" + i;
					    }
					    return i;
					}
					//站台查詢使用	
					function receviceSiteData(data) {
						var json = JSON.parse(data);
						var siteId = json.siteObjs[0].site_ID;
						var btsSiteId = json.siteObjs[0].bts_SITE_ID;
						$("#qrySiteId").val(btsSiteId);
						$("#qrySiteId").attr("realValue",siteId);
					}
					//格式化grid內數字
					function nullForZero(cellvalue, options, rowObject) {
						var returnValue = cellvalue;
						if (cellvalue == "" || cellvalue == "undefined" || cellvalue == null) {
							returnValue = "0";
						}
						return Math.abs(returnValue);
					}				
</script>
</head>
<body>
	<div id="breadcrumb" class="col-md-12">
		<ol class="breadcrumb">
			<div class="col-sm-12" id="btnDiv">
				<button class="btn btn-primary btn-label-left" type="button"
					id="qryBtn">
					查詢 
				</button>
				<button class="btn btn-primary btn-label-left" type="button"
					id="choiceBtn">選擇</button>
			</div>
		</ol>
	</div>
	<div class="row">
		<div class="col-xs-12 col-sm-12">
			<div class=" ui-draggable ui-droppable">
				<div class="box-header">
					<div class="box-name">
						<i class="fa fa-edit"></i> <span>查詢退料站台</span>
					</div>
					<div class="box-icons">
						<p class="expand-link"></p>
					</div>
					<div class="no-move"></div>
				</div>
				<div class="box-content">
					<form class="form-horizontal bootstrap-validator-form"
						id="bBoardAddEdit" />
						<input type="hidden" id="matNoList" value="${matList}">
					<table style="width: 100%">
						<div class="form-group">
							<div class="row">								
								<tr>									
									<td><label class="col-sm-12 control-label"><span
											class="s">* </span>基站編號：</label></td>
									<td><div class="col-sm-6">
											<input id="qrySiteId" type="text" realValue=""  value="" placeholder="請輸入完整的基站編號" class="form-control"
												name="qrySiteId">
											<div id="qrySelectSitePage"></div>
											<form id="qrySiteTestForm">
											<input type="hidden" name="qrySiteCallBackFun" id="qrySiteCallBackFun" value="qryReceviceSiteData" />
											<input type="hidden" name="qryFilter_DomainId" id="qryFilter_DomainId" value="" />
											<!-- 單選不用傳 -->
											<input type="hidden" name="qrySelectMode" id="qrySelectMode" value="multi" />
											</form>
										</div>
										<i id="qrySiteIdSearch" class="fa fa-search" ></i>
										</td>
									<td><label class="col-sm-12 control-label"><span
											class="s">* </span>收料日期：</label></td>
									<td><div class="col-sm-12">
											<input id="crStartDate" type="text" value=""
												name="crStartDate" readonly="readonly"> 至 <input
												id="crEndDate" type="text" value="" name="crEndDate"
												readonly="readonly">
										</div></td>
								</tr>
							</div>
						</div>
					</table>
					<div class="clearfix">&nbsp;</div>
					<tr>
						<div class="col-md">
							<div id="ajaxSearchResult" class="col-xs-12">
								<!-- 固定id for window.resize start-->
								<div id="jQgrid" align="left">
									<table id="sp1mastergrid"></table>
									<div id="sp1masterpager"></div>
								</div>
								<!-- 固定id for window.resize end-->
							</div>
						</div>
					</tr>
					<hr>
					<div class="clearfix">&nbsp;</div>
					<tr>
						<div class="col-md">
							<div id="ajaxSearchResult" class="col-xs-12">
								<!-- 固定id for window.resize start-->
								<div id="jQgrid" align="left">
									<table id="sp1dtlgrid"></table>
									<div id="sp1dtlpager"></div>
								</div>
								<!-- 固定id for window.resize end-->
							</div>
						</div>
					</tr>
					<td style="text-align:center;display:none;"><div>
							<select id="matNoOption" name="matNoOption">
								<c:forEach items="${matList}" var="mat">
									<option value="${mat.matNo}">${mat.matNo}</option>
								</c:forEach>
							</select>
						</div></td>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>