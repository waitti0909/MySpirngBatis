<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<title>基站分攤</title>
		<script type="text/javascript">
			$(document).ready(function() {
				mountEvent();
				mountSPGrid();
				 if($("#editFlag").val()=="A"){//新增將之前基站資料再寫回到此畫面
					addTempData();
					$("#spSavEditBtn").hide();
				}else if($("#editFlag").val()=="D"){//新增將之前基站資料再寫回到此畫面
					var data = {
						paymentReqNo : $("#paymentReqNo").val()
					};
					$("#spGrid").setGridParam({datatype : "json",postData : data,page:1}).trigger("reloadGrid");
					$("#spSavEditBtn").hide();
					$("#spRstBtn").hide();
					$("#spPlusBtn").hide();
					$("#spDelBtn").hide();
					$("#spSavBtn").hide();
					$("#spTaxExclusiveAmt").prop("disabled","disabled");
				}else{//修改則查詢
					var data = {
						paymentReqNo : $("#paymentReqNo").val()
					};
					$("#spGrid").setGridParam({datatype : "json",postData : data,page:1}).trigger("reloadGrid");
					$("#spSavBtn").hide();
				} 
			});
			//將暫存內容展開
			function addTempData(){
				var ids = jQuery("#tempGrid").jqGrid('getDataIDs');//用暫存序號取值
				if(ids.length != ""){
					for (var i = 0; i < ids.length; i++) {
						var rowId = ids[i];
						var rowData = jQuery('#tempGrid').jqGrid('getRowData', rowId);
						if (rowData.tempKey == $("#gridId").val()) {//如果偽key是回傳的key 先清除								
								var gridId = $("#spGrid").jqGrid('getDataIDs');
								$("#spGrid").jqGrid("addRowData", gridId + 1, {
								'site_ID' : rowData.site_ID,
								'repartition_AMT' : rowData.repartition_AMT,
								'repartition_AMTP' : rowData.repartition_AMTP,
								'spTotalAmt' : rowData.spTotalAmt,
								'location_ID' : rowData.location_ID,
								'bts_SITE_ID' : rowData.bts_SITE_ID,
							}, "first");
						}
					}
				}
			}
			//確認與前一畫面金額加總是否符合
			function spCheck(){
				var totalAmt="";
				var totalCheckAmt=$("#totalCheckAmt").val();
				var ids =$("#spGrid").jqGrid('getGridParam','selarrrow');
					if (!ids.length==0) {					
						for(var i=0;i<ids.length;i++)
							{
								var row = $("#spGrid").jqGrid('getRowData', ids[i]);
								totalAmt=Number(totalCheckAmt)+Number(row.spTotalAmt);
							}
							if(totalAmt != totalCheckAmt){
								alert("分攤金額加總與請款金額不符,請重新操作!");
								return false;
							}
					}else {
			        	alert("先選取一列資料列");
			        	return false;
			        }
				return true;
			}
			
			//寫入暫存grid
			function addSpGrid(){
				var btsSiteId = $("#qrySiteId").val();
				var siteId = $("#qrySiteId").prop("val");
				var spTaxExclusiveAmt = $("#spTaxExclusiveAmt").val();
				var spBusinessTax = $("#spBusinessTax").val();
				var spShareTotalAmt = $("#spShareTotalAmt").val();
				var gridId = $("#spGrid").jqGrid('getDataIDs');
				var locationId = $("#qryLocationId").val();
				if(btsSiteId == ""){
					alert("請輸入基站編號");
					return false;
				}
				if(spTaxExclusiveAmt == ""){
					alert("請輸入未稅金額");
					return false;
				}
				$("#spGrid").jqGrid("addRowData", gridId + 1, {
					'site_ID' : siteId,
					'repartition_AMT' : spTaxExclusiveAmt,
					'repartition_AMTP' : spBusinessTax,
					'spTotalAmt' : spShareTotalAmt,
					'location_ID' : locationId,
					'bts_SITE_ID' : btsSiteId,
				}, "first");
			}
			//result
			function mountSPGrid(){
				$("#spGrid").jqGrid({
					datatype : "local",
					autowidth:'true',
					rowNum : 1000,
					url : CONTEXT_URL + "/pay/pay005/searchForSP",
					colNames : [ '基站編號','未稅金額','營業稅','分攤總額','','' ],
					colModel : [ 
					    {name : 'bts_SITE_ID',index : 'bts_SITE_ID',width : 80 , align : 'center',sortable : false},
					    {name : 'repartition_AMT',index : 'repartition_AMT',width : 80 , align : 'center',sortable : false},
					    {name : 'repartition_AMTP',index : 'repartition_AMTP',width : 80 , align : 'center',sortable : false},
					    {name : 'spTotalAmt',index : 'spTotalAmt',width : 80 , align : 'center',sortable : false},
					    {name : 'location_ID',index : 'location_ID',width : 80 , align : 'center',sortable : false,hidden:true},
					    {name : 'site_ID',index : 'site_ID',width : 80 , align : 'center',sortable : false,hidden:true}
					    ],
					caption : "基站分攤",
					rownumbers : true,
					multiselect : true,
					onSelectRow : function(){},
					gridComplete: function(){}
				});//end grid	
				$(window).resize(function() {
					$(window).unbind("onresize");
					$("#spGrid").setGridWidth($("#jQgrid").width() - 10);
					$(window).bind("onresize", this);
				}).trigger('resize');
			}
			//將值寫回母頁面
			function transBackPage(){
				var selr = jQuery('#spGrid').jqGrid('getGridParam','selarrrow');
				if(!selr.length==0) {
				var spObjectList = {};
				var ids = jQuery("#tempGrid").jqGrid('getDataIDs');
				for (var i = 0; i < ids.length; i++) {
					var rowId = ids[i];
					var rowData = jQuery('#tempGrid').jqGrid('getRowData', rowId);
					if (rowData.tempKey == $("#gridId").val()) {//如果偽key是回傳的key 先清除								
						$('#tempGrid').jqGrid("delRowData", rowId);
					}
				}
					for(var spI=0;spI<selr.length;spI++){
						var myrow = jQuery('#spGrid').jqGrid('getRowData',selr[spI]);		
						spObjectList.spObject = [];
							  var spData={
								  'site_ID' : myrow.site_ID,
								  'repartition_AMT' : myrow.repartition_AMT,
								  'repartition_AMTP' : myrow.repartition_AMTP,
								  'spTotalAmt' : myrow.spTotalAmt,
								  'location_ID' : myrow.location_ID,
								  'bts_SITE_ID' : myrow.bts_SITE_ID,
							  };
							  spObjectList.spObject.push(spData);
							  var callBackFun = "${backFun}";
							if (typeof window[callBackFun] == "function") {
								window[callBackFun](JSON.stringify(spObjectList),$("#gridId").val());
							}
						}
						$("#showSharePag").dialog('close');
				}else{
					alert("按儲存鈕前，請先勾選一筆分攤資料");
					return false;
				}
			}
			function spSave(){
				var spPaymentReqDtlArray = [];
				var spPaymentReqVouCreditArray = [];
				var spRepartitionArray = [];
				var spPaymentArray = [];
				var rowId = "";
				var addData="";//修改畫面的Grid資料
				var selr = jQuery('#spGrid').jqGrid('getGridParam','selarrrow');
				if(!selr.length==0) {
					for(var spI=0;spI<selr.length;spI++){
						var myrow = jQuery('#spGrid').jqGrid('getRowData',selr[spI]);
						var ids = jQuery("#addGrid").jqGrid('getDataIDs');
							for (var i = 0; i < ids.length; i++) {
								rowId = ids[i];
								var rowData = jQuery('#addGrid').jqGrid('getRowData', rowId);
								if (rowData.payment_REQ_NO == $("#paymentReqNo").val()) {
									addData=rowData;
								}
							}
						//TB_PAY_PAYMENT_REQUEST_DTL
						var tbPayPaymentRequestDtl = {
							"payment_REQ_NO" : addData.payment_REQ_NO,
							"payment_REQUEST_ITEM" : addData.payment_METHOD,
							"location_ID" : myrow.location_ID,
							"tax_EXCLUSIVE_AMT" : myrow.repartition_AMT,
							"business_TAX" : myrow.repartition_AMTP
						};
						spPaymentReqDtlArray.push(tbPayPaymentRequestDtl);
						//TB_PAY_PAYMENT
						var TbPayPayment = {
							"payment_REQ_NO" : addData.payment_REQ_NO,
							"location_ID" : myrow.location_ID,
							"payment_REQ_USER_ID" : addData.payment_USER_ID,
							"payment_USER_ID" :addData.payment_USER_ID,
							"payment_USER_NAME" :addData.payment_USER_NAME,
							"payment_METHOD" : addData.payment_METHOD,
							"bank_CODE" : addData.bank_CODE,
							"bank_BRANCH_CODE":addData.bank_BRANCH_CODE,
							"account_NBR":addData.account_NBR,
							"tax_EXCLUSIVE_AMT" : myrow.repartition_AMT,
							"business_TAX" : myrow.repartition_AMTP,
							"total_INCOME_TAX" : myrow.repartition_AMTP,//計算所得稅 未做
							"total_NHI_amt" :myrow.repartition_AMTP,//計算健保費 未做
							"resident_ADDRESS" : addData.resident_ADDRESS,
							"check_CASH_DATE" : addData.check_CASH_DATE,
							"status" :"N",
							"memo" : addData.memo
						};
						spPaymentArray.push(TbPayPayment);
						//TB_PAY_VOUCHER_CREDIT
						var TbPayVoucherCredit = {
							"mst_SEQ_NBR" :addData.voucherSeqNbr,
							"payment_REQ_NO" : addData.payment_REQ_NO,
							"cash_REQ_AP_NO" : $("#editCashReqNo").val(),
							"credit_AMT" : myrow.repartition_AMT,
							"credit_TAX" : myrow.repartition_AMTP
						};
						spPaymentReqVouCreditArray.push(TbPayVoucherCredit);
						//TB_PAY_REPARTITION
						var TbPayRepartition = {
							"payment_REQ_NO" : addData.payment_REQ_NO,
							"location_ID" : myrow.location_ID,
							"year_MONTH" :$("#addApplyYM").val(),
							"repartition_AMT" : myrow.repartition_AMT,
							"site_ID" : myrow.site_ID,
							"exp_TYPE":"MIR"
						};
						spRepartitionArray.push(TbPayRepartition);
					}
					var data ={
						spPaymentReqDtlArray : JSON.stringify(spPaymentReqDtlArray),
						spPaymentArray : JSON.stringify(spPaymentArray),
						spPaymentReqVouCreditArray : JSON.stringify(spPaymentReqVouCreditArray),
						spRepartitionArray : JSON.stringify(spRepartitionArray),
						paymentReqNo : $("#paymentReqNo").val()
					}
				 $.ajax({
					url : CONTEXT_URL + "/pay/pay005/saveSP/",
					data : data,
					type : "POST",
					dataType : 'json',
					async:false,
					success : function(data) {
						if(data.success){
							alert("修改成功");
							$("#addGrid").jqGrid("setRowData", rowId, {'checkShare' : "Y"});
							$("#showSharePag").dialog('close');
						}else{
							$("#addGrid").jqGrid("setRowData", rowId, {'checkShare' : "N"});
							alert("修改錯誤 訊息: " + data.msg);
						}
					}
				});	 
				}else{
					alert("按儲存鈕前，請先勾選一筆分攤資料");
					return false;
				}
			}
			function mountEvent() {
				//新增確定
				$("#spSavBtn").click(function() {
					//暫存成功就關閉畫面 
					if(spCheck()){
					transBackPage();
					}						
				});

				//重置按鈕
				$('#spRstBtn').click(function() {
					/* $("#qrySiteId").prop("value", "");
					$("#qrySiteId").prop("val", "");
					$("#qryLocationId").prop("value", "");
					$("#spTaxExclusiveAmt").prop("value", "");
					$("#spBusinessTax").prop("value", "");
					$("#spShareTotalAmt").prop("value", "");
					$("#spGrid").jqGrid().clearGridData(); */
					$("#searchMaterialForm").trigger('reset');
					if($("#editFlag").val()=="A"){//新增將之前基站資料再寫回到此畫面
						addTempData();
						$("#spSavEditBtn").hide();
					}else if($("#editFlag").val()=="D"){//新增將之前基站資料再寫回到此畫面
						var data = {
							paymentReqNo : $("#paymentReqNo").val()
						};
						$("#spGrid").setGridParam({datatype : "json",postData : data,page:1}).trigger("reloadGrid");
						$("#spSavEditBtn").hide();
						$("#spRstBtn").hide();
						$("#spPlusBtn").hide();
						$("#spDelBtn").hide();
						$("#spSavBtn").hide();
						$("#spTaxExclusiveAmt").prop("disabled","disabled");
					}else{//修改則查詢
						var data = {
							paymentReqNo : $("#paymentReqNo").val()
						};
						$("#spGrid").setGridParam({datatype : "json",postData : data,page:1}).trigger("reloadGrid");
						$("#spSavBtn").hide();
					} 
				});
				//新增基站金額
				$('#spPlusBtn').click(function() {
					addSpGrid();
				});
				//刪除基站金額
				$('#spDelBtn').click(function() {
					delSpGrid();
				});
				//修改儲存
				$("#spSavEditBtn").click(function() {
					if(spCheck()){
					spSave();
					}						
				});
			}
			//站台查詢使用	
			$("#qrySiteIdSearch").click(function() {
				openSiteDialogFrame("qrySelectSitePage", "receviceSiteData"); // call common.js
			});
			function receviceSiteData(data) {
				var json = JSON.parse(data);
				$("#qrySiteId").val(json.siteObjs[0].bts_SITE_ID);
				$("#qrySiteId").prop("val", json.siteObjs[0].site_ID);
				$("#qryLocationId").val(json.siteObjs[0].location_ID);
			}
			function delSpGrid() {
				var selr = jQuery('#spGrid')
						.jqGrid('getGridParam', 'selarrrow');
				if (!selr.length == 0) {
					if (confirm("是否確定要刪除")) {
						for (var i = selr.length - 1; i >= 0; i--) {
							$('#spGrid').jqGrid("delRowData", selr[0]);
						}
					}
				} else {
					alert("未勾選要刪除的資料列,請重新操作!");
				}
			}
			//計算金額
			$("#spTaxExclusiveAmt").on('blur',function() {
				if($("#editFlag").val()!="D"){
					var spTax = $("#spTaxExclusiveAmt").val();
					if (!isAllNumber(spTax)) {
						alert("未稅金額只能輸入數字");
						$("#spBusinessTax").val("");
						$("#spShareTotalAmt").val("");
						return false;
					}
					var data={oriAmt : spTax};
				 $.ajax({
					url : CONTEXT_URL + "/pay/pay005/getAmt/",
					data : data,
					type : "POST",
					dataType : 'json',
					async:false,
					success : function(data) {
						if(data.success){
							$("#spBusinessTax").val(data.maps.businessTax);
							$("#spShareTotalAmt").val((spTax * 1)+ data.maps.businessTax);
						}else{
							alert("修改錯誤 訊息: " + data.msg);
						}
					}
				});	 
				}
			});
		</script>
	</head>
	<body>
			<button class="btn btn-primary btn-label" type="button" id="spSavEditBtn">
				儲存
			</button>
			<button class="btn btn-primary btn-label" type="button" id="spSavBtn">
				確定
			</button>
		<button class="btn btn-primary btn-label" type="button" id="spRstBtn">
			<s:message code="button.reset" text="重置" />
		</button>
		
		<div class="row">
			<div class="col-xs-12 col-sm-12">
				<div class="box ui-draggable ui-droppable">
					<div class="box-header">
						<div class="box-name">
							<i class="fa fa-search"></i>
							<span>基站分攤</span>
						</div>
						<div class="box-icons">
							<a class="collapse-link"> <i class="fa fa-chevron-up"></i> </a>
							<a class="expand-link"> <i class="fa fa-expand"></i> </a>
						</div>
						<div class="no-move"></div>
					</div>
				</div>
				<div class="box-content">
				<form role="form" class="form-horizontal bootstrap-validator-form" novalidate="novalidate" id="searchMaterialForm">
				<input type="hidden" name="gridId" id="gridId" value="${gridId}" />
				<input type="hidden" name="totalCheckAmt" id="totalCheckAmt" value="${totalAmt}" />
				<input type="hidden" name="tempData" id="tempData" value="${tempData}" />
				<input type="hidden" name="editFlag" id="editFlag" value="${edit}" />
				<input type="hidden" name="paymentReqNo" id="paymentReqNo" value="${backFun}" /><!--前一頁選擇那一行的流水號  -->
					<div class="form-group">
						<table style="width:80%">
							<tr>
								<td><label class="col-sm-12 control-label"><span
											class="s">* </span>基站編號：</label></td>
									<td><div class="col-sm-6">
											<input id="qrySiteId" type="text" value="" val="" class="form-control"
												name="qrySiteId" disabled>
											<input type="hidden" name="qryLocationId" id="qryLocationId" value="" />	
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
								<td><label class="col-sm-10 control-label">未稅金額 :</label></td>
								<td>
									<div class="col-sm-6">
										<input id="spTaxExclusiveAmt" type="text" class="form-control"
													name="spTaxExclusiveAmt" value="">
									</div>
								</td>
							</tr>
							<tr>
								<td><div class="clearfix">&nbsp;</div></td>
							</tr>
							<tr>
								<td><label class="col-sm-10 control-label">營業稅 :</label></td>
								<td>
									<div class="col-sm-6">
										<input id="spBusinessTax" type="text" class="form-control" disabled
													name="spBusinessTax" value="">
									</div>
								</td>
								<td><label class="col-sm-10 control-label">分攤總額 :</label></td>
								<td>
									<div class="col-sm-6">
										<input id="spShareTotalAmt" type="text" class="form-control" disabled
													name="spShareTotalAmt" value="">
									</div>
								</td>
							</tr>
										
						</table>
						<tr>
						<div class="col-sm-12" id="btnDiv">
							<button class="btn btn-primary btn-label-left" type="button"
									id="spPlusBtn">新增基站分攤金額</button>
							<span class="s"> </span>	
							<button class="btn btn-primary btn-label-right" type="button"
									id="spDelBtn">刪除基站分攤金額</button>
							</tr>	
						</div>	
					</div>
				</form>
			</div>
			</div>
			<div id="ajaxSearchResult" class="col-sm-12 " style="width: 1200px;">
				<!-- 固定id for window.resize start-->
				<div id="jQgrid" align="left">
					<table id="spGrid"></table>
				</div>
				<!-- 固定id for window.resize end-->
			</div>
		</div>
	</body>
</html>