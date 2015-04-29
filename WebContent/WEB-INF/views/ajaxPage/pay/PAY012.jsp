<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>


<!DOCTYPE html>
<html lang="en">
<head>
<title>費用預估作業</title>
	<script type="text/javascript">			
		$(document).ready(function() {
			mountButEvent();
			LoadSelect2Script("<s:url value="/resources/plugins/select2/select2.min.js" />", selectDefault1);
			prepareDay();//扣款期間 預設日期
		});		
		
		//掛載表格
		function mountReeGrid(){//租金表格
			$("#grid").jqGrid({
				datatype : "local",
				pager : '#pager',
				autowidth:'true',
				url : CONTEXT_URL + "/pay/pay012/selectReeData/",
				colNames : [ '租約編號','名稱', '站點編號','類別','3G站台編號', '3G站台狀態', '4G站台編號', '4G站台狀態','預估金額年月','未稅金額','應稅金額','' ],
				colModel : [ 
					{name : 'contract_NO',index : 'contract_NO', align : 'center',sortable : false},
				    {name : 'lsName',index : 'lsName' , align : 'center',sortable : false},
				    {name : 'location_ID',index : 'location_ID' , align : 'center',sortable : false}, 
				    {name : 'locType',index : 'locType' , align : 'center',sortable : false},
				    {name : 'thirdGSiteId',index : 'thirdGSiteId', align : 'center',sortable : false},
				    {name : 'thirdGstatusDscr',index : 'thirdGstatusDscr' , align : 'center',sortable : false}, 
				    {name : 'fourthGSiteId',index : 'fourthGSiteId', align : 'center',sortable : false},
				    {name : 'fourthGstatusDscr',index : 'fourthGstatusDscr' , align : 'center',sortable : false},
				    {name : 'yearMonth',index : 'yearMonth' , align : 'center',sortable : false},
				    {name : 's_tax_exclusive_amt',index : 's_tax_exclusive_amt', align : 'center',sortable : false,formatter: nullForZero},
				    {name : 's_business_tax',index : 's_business_tax', align : 'center',sortable : false,formatter: nullForZero},
				    {name : 'domain',index : 'domain', align : 'center',sortable : false,hidden:true}
				    ],
				caption : "預估清單",
				rownumbers : true,
				multiselect : true,
				onSelectRow : function(rowId) {},
				gridComplete : function() {},
				ondblClickRow: function(rowId) {}
			});
			var data = {
				startVal : $("#estimateStartYM").val(),
				endVal : $("#estimateEndYM").val(),
				appDate : $("#appDate").val()
			};
			$("#grid").setGridParam({datatype:"json", postData:data}).trigger("reloadGrid");
			
			$(window).resize(function() {
				$(window).unbind("onresize");
				$("#grid,.grid").setGridWidth($("#jQgrid").width() - 10);
				$(window).bind("onresize", this);
			}).trigger('resize');	
		}
		function mountEleGrid(){//電費表格
			$("#grid").jqGrid({
				datatype : "local",
				pager : '#pager',
				autowidth:'true',
				url : CONTEXT_URL + "/pay/pay012/selectEleData/",
				colNames : [ '租約編號','名稱', '站點編號','類別','3G站台編號', '3G站台狀態', '4G站台編號', '4G站台狀態','電錶號碼','用電方式','未稅金額','應稅金額','' ],
				colModel : [ 
					{name : 'contract_NO',index : 'contract_NO', align : 'center',sortable : false},
				    {name : 'lsName',index : 'lsName' , align : 'center',sortable : false},
				    {name : 'locationId',index : 'locationId' , align : 'center',sortable : false}, 
				    {name : 'locType',index : 'locType' , align : 'center',sortable : false},
				    {name : 'thirdGSiteId',index : 'thirdGSiteId', align : 'center',sortable : false},
				    {name : 'thirdGstatusDscr',index : 'thirdGstatusDscr' , align : 'center',sortable : false}, 
				    {name : 'fourthGSiteId',index : 'fourthGSiteId', align : 'center',sortable : false},
				    {name : 'fourthGstatusDscr',index : 'fourthGstatusDscr' , align : 'center',sortable : false},
				    {name : 'electricityMeterNbr',index : 'electricityMeterNbr', align : 'center',sortable : false},
				    {name : 'electricityTypeDesc',index : 'electricityTypeDesc', align : 'center',sortable : false},
				    {name : 's_tax_exclusive_amt',index : 's_tax_exclusive_amt', align : 'center',sortable : false,formatter: nullForZero},
				    {name : 's_business_tax',index : 's_business_tax', align : 'center',sortable : false,formatter: nullForZero},
				    {name : 'domain',index : 'domain', align : 'center',sortable : false,hidden:true}],
				caption : "預估清單",
				rownumbers : true,
				multiselect : true,
				onSelectRow : function(rowId) {},
				gridComplete : function() {},
				ondblClickRow: function(rowId) {}
			});
			var data = {
				startVal : $("#estimateStartYM").val(),
				endVal : $("#estimateEndYM").val()
			};
			$("#grid").setGridParam({datatype:"json", postData:data}).trigger("reloadGrid");
			$(window).resize(function() {
				$(window).unbind("onresize");
				$("#grid,.grid").setGridWidth($("#jQgrid").width() - 10);
				$(window).bind("onresize", this);
			}).trigger('resize');	
		}
		function mountLieGrid(){//專線表格
			$("#grid").jqGrid({
				datatype : "local",
				pager : '#pager',
				autowidth:'true',
				url : CONTEXT_URL + "/pay/pay012/selectLieData/",
				colNames : [ '專線編號','名稱', '站點編號','類別','3G站台編號', '3G站台狀態', '4G站台編號', '4G站台狀態','未稅金額','應稅金額','' ],
				colModel : [ 
					{name : 'contract_NO',index : 'contract_NO', align : 'center',sortable : false},
				    {name : 'lsName',index : 'lsName' , align : 'center',sortable : false},
				    {name : 'locationId',index : 'locationId' , align : 'center',sortable : false}, 
				    {name : 'locType',index : 'locType' , align : 'center',sortable : false},
				    {name : 'thirdGSiteId',index : 'thirdGSiteId', align : 'center',sortable : false},
				    {name : 'thirdGstatusDscr',index : 'thirdGstatusDscr' , align : 'center',sortable : false}, 
				    {name : 'fourthGSiteId',index : 'fourthGSiteId', align : 'center',sortable : false},
				    {name : 'fourthGstatusDscr',index : 'fourthGstatusDscr' , align : 'center',sortable : false},
				    {name : 's_tax_exclusive_amt',index : 's_tax_exclusive_amt', align : 'center',sortable : false,formatter: nullForZero},
				    {name : 's_business_tax',index : 's_business_tax', align : 'center',sortable : false,formatter: nullForZero},
				    {name : 'domain',index : 'domain', align : 'center',sortable : false,hidden:true}],
				caption : "預估清單",
				rownumbers : true,
				multiselect : true,
				onSelectRow : function(rowId) {},
				gridComplete : function() {},
				ondblClickRow: function(rowId) {}
			});
			var data = {
				startVal : $("#estimateStartYM").val(),
				endVal : $("#estimateEndYM").val()
			};
		$("#grid").setGridParam({datatype:"json", postData:data}).trigger("reloadGrid");
			$(window).resize(function() {
				$(window).unbind("onresize");
				$("#grid,.grid").setGridWidth($("#jQgrid").width() - 10);
				$(window).bind("onresize", this);
			}).trigger('resize');	
		}

		//格式化grid內數字
		function nullForZero(cellvalue, options, rowObject) {
			var returnValue = cellvalue;
			if (cellvalue == "" || cellvalue == "undefined"
					|| cellvalue == null) {
				returnValue = "0";
			}
			return Math.abs(returnValue);
		}
		function check() {
			var estimateType = $("#estimateType").find("option:selected").val();
			var estimateStartYM = $("#estimateStartYM").val();
			var estimateEndYM = $("#estimateEndYM").val();
			if ((estimateType == null || estimateType == "")) {
				alert('預估類型不得為空值');
				return false;
			}
			if ((estimateStartYM == null || estimateStartYM == "")) {
				alert('預估年月起不得為空值');
				return false;
			}
			if ((estimateEndYM == null || estimateEndYM == "") && estimateType=='REE') {
				alert('預估年月迄不得為空值');
				return false;
			}
			dateCheck(estimateStartYM + "/01", estimateEndYM + "/01", "預估年月");

			return true;
		}

		//掛載表格Event
		function mountButEvent() {
			//產生預估
			$('#btn_estimate').click(
					function() {
						if (check()) {
							gridClear();
							var estimateType = $("#estimateType").find(
									"option:selected").val();
							if (estimateType == 'REE') {//租金
								mountReeGrid();
							} else if (estimateType == 'ELE') {//電費
								mountEleGrid();
							} else if (estimateType == 'LIE') {//專線
								mountLieGrid();
							}
						}
					});

			//儲存
			$('#btn_save').click(
					function() {
					var ids = jQuery("#grid").jqGrid('getDataIDs');
					if(ids.length==0){
						alert("請先執行預估再點擊儲存");
						return false;
					}
						var data = {
							type : $("#estimateType").find("option:selected").val(),
							startVal : $("#estimateStartYM").val(),
							endVal : $("#estimateEndYM").val()
						}
						var estNoArray = [];
						$.ajax({
							url : CONTEXT_URL + "/pay/pay012/selectEstimate/",
							data : data,
							type : "POST",
							dataType : 'json',
							success : function(data) {
								var estimateType = $("#estimateType").find("option:selected").val();
						        var estimateNo=getEstimateNo(estimateType);
								if (data.success) {
									if(confirm("預估類型:"+$("#estimateType").find("option:selected").text()+",預估年月起:"+$("#estimateStartYM").val()
									+",預估年月迄:"+$("#estimateEndYM").val()+"已有資料,是否要重新產生?")) {//先刪除後寫入
										if (data.rows.length > 0) {
											for (var i = 0; i < data.rows.length; i++) {
												var estData = {
													"estimateNo" : data.rows[i].estimate_NO
												};
												estNoArray.push(estData);
											}
										}
										processEstimate(estNoArray,estimateNo);
									}
								}else{
									var estData = {
										"estimateNo" : estimateNo
									};
									estNoArray.push(estData);
									processEstimate(estNoArray,estimateNo);
								}
							}
						});
					});

			//產生EXCEL
			$('#btn_to_excel').click(
			function() {
				var ids = jQuery("#grid").jqGrid('getDataIDs');
					if(ids.length==0){
						alert("請先執行預估再執行匯出檔案");
						return false;
					}
					var type = $("#estimateType").find("option:selected").val();
					var rowArray =[];
					for (var i = 0; i < ids.length; i++) {
						row = jQuery('#addGrid').jqGrid('getRowData', ids[i]);
						var estimateData = {
							contractNo : row.contract_NO,//租約
							locationId : row.location_ID,//站點
							siteId : row.siteId,//基站
							domain : row.domain,//區域
							locType : row.locType,//類型
							taxAmt : row.s_tax_exclusive_amt,//未稅
							businessTax : s_business_tax,//應稅
							thirdGSiteId : row.thirdGSiteId, //3G基站編號
							thirdGstatusDscr : row.thirdGstatusDscr,//3G狀態
							fourthGSiteId : row.fourthGSiteId,//4G基站編號
							fourthGstatusDscr : row.fourthGstatusDscr//4G狀態
						}
						if(type == 'REE'){//租金
							estimateData["yearMonth"] = row.yearMonth;
						}
						if(type == 'ELE'){//電費
							estimateData["electricityMeterNbr"] = row.electricityMeterNbr;
							estimateData["electricityTypeDesc"] = row.electricityTypeDesc;
						}
						rowArray.push(estimateData);
					}
					
				window.location.href = CONTEXT_URL
						+ "/pay/pay012/genExcel?type="+$("#estimateType").find("option:selected").val()+"rowArray="+rowArray;
			});

			//重置按鈕
			$('#btn_reset').click(function() {
				$("#searchFrom").trigger('reset');
				$("#grid").jqGrid().clearGridData();
				$("#estimateType").select2("val", "");
				prepareDay();
			});
		}

		//=========Function=============
		function processEstimate(estNoArray,estimateNo) {
			var selr = jQuery('#grid').jqGrid('getGridParam', 'selarrrow');
			var addArray = [];
			var masArray = [];
			$.log("estNoArray: "+estNoArray);
			if (!selr.length == 0) {//未確認是否全部寫入
				for (var i = 0; i < selr.length; i++) {
					var myrow = jQuery('#grid').jqGrid('getRowData', selr[i]);
					var TbPayExpenseEstimateDtl = {
						"estimate_no" : estimateNo,
						"contract_no" : myrow.contract_NO,
						"site_id" : myrow.locationId,
						"estimate_amt" : myrow.s_tax_exclusive_amt,
						"cr_user" : $('#appUser').val(),
						"md_user" : $('#appUser').val()
					};
					addArray.push(TbPayExpenseEstimateDtl);
				}

				var TbPayExpenseEstimate = {
					"estimate_no" : estimateNo,
					"estimate_begin_year_month" : $("#estimateStartYM").val(),
					"estimate_end_year_month" : $("#estimateEndYM").val(),
					"estimate_type" : $("#estimateType")
							.find("option:selected").val(),
					"estimate_user" : $('#appUser').val(),
					"cr_user" : $('#appUser').val(),
					"md_user" : $('#appUser').val()
				};
				masArray.push(TbPayExpenseEstimate);
				var data = {
					detail : JSON.stringify(addArray),
					master : JSON.stringify(masArray),
					estNoArray : JSON.stringify(estNoArray)
				}
				$.ajax({
						url : CONTEXT_URL + "/pay/pay012/processEstimate/",
						data : data,
						type : "POST",
						dataType : 'json',
						success : function(data) {
							if (data.success) {
								alert($("#estimateStartYM").val()+" 的費用預估作業(預估單號:" + estimateNo
										+ ")處理成功!");
							} else {
								alert("處理失敗：" + data.msg);
							}
						}
					});
			} else {
				alert("未勾選要儲存的資料列,請重新操作!");
			}
			return true;
		}
		
		//產生預估單號
		function getEstimateNo(type) {
			var estimateNo = "";
			var data = {
				type : type
			};
			$.ajax({
				url : CONTEXT_URL + "/pay/pay012/getEstimateNo",
				data : data,
				type : "POST",
				dataType : 'json',
				async : false,
				success : function(data) {
					if (data.success) {
						estimateNo = data.msg
						$.log("estimateNo:" + estimateNo);
					}
				}
			});
			return estimateNo;
		}
		//清除Grid html
		function gridClear() {
			$("#grid").jqGrid().clearGridData();
			$("#jQgrid").html("");
			$("#jQgrid").html(
					"<table id='grid'></table> <div id='pager'></div>");
		}
		// 開啟下拉式選單搜尋文字功能
		function selectDefault1() {
			$("#estimateType").select2();
		}
		//時間左補零滿兩位
		function addZero(i) {
			if (i < 10) {
				i = "0" + i;
			}
			return i;
		}
		//日期預設處理
		function prepareDay() {
			var Today = new Date();
			$('#estimateStartYM')
					.prop(
							"value",
							Today.getFullYear() + "/"
									+ addZero((Today.getMonth() + 1)));
			$('#estimateEndYM')
					.prop(
							"value",
							Today.getFullYear() + "/"
									+ addZero((Today.getMonth() + 2)));
			$('#appDate').prop(
					"value",
					Today.getFullYear() + "/" + (Today.getMonth() + 1) + "/"
							+ (Today.getDate()));
			//預估年月起始日曆						
			$('#estimateStartYM')
					.datepicker(
							{
								changeMonth : true,
								changeYear : true,
								showButtonPanel : true,
								dateFormat : 'yy/mm',
								onClose : function(dateText, inst) {
									var month = $(
											"#ui-datepicker-div .ui-datepicker-month :selected")
											.val();
									var year = $(
											"#ui-datepicker-div .ui-datepicker-year :selected")
											.val();
									$(this).datepicker('setDate',
											new Date(year, month, 1));
								}
							});
			//預估年月結束日曆
			$('#estimateEndYM')
					.datepicker(
							{
								changeMonth : true,
								changeYear : true,
								showButtonPanel : true,
								dateFormat : 'yy/mm',
								onClose : function(dateText, inst) {
									var month = $(
											"#ui-datepicker-div .ui-datepicker-month :selected")
											.val();
									var year = $(
											"#ui-datepicker-div .ui-datepicker-year :selected")
											.val();
									$(this).datepicker('setDate',
											new Date(year, month, 1));
								}
							});
		}
		//異動預估類型變動預估年月
		$("#estimateType").change(estimateTypeChange);
		
		function estimateTypeChange(){		
			var Today = new Date();
			var estimateType=$("#estimateType").find("option:selected").val();
			if (estimateType != 'REE') {//租金
				$('#estimateEndYM').hide();
				$('#rightHere').hide();
				$('#estimateStartYM').prop("value",Today.getFullYear() + "/"+ addZero((Today.getMonth() + 2)));
			} else {
				$('#estimateEndYM').show();
				$('#rightHere').show();
				$('#estimateStartYM').prop("value",Today.getFullYear() + "/"+ addZero((Today.getMonth() + 1)));
			}
		}
	</script>
</head>
<style>
.ui-datepicker-calendar {
    display: none;
    }
</style>
<body>

	<div class="row">
		<div id="breadcrumb" class="col-md-12">
			<ol class="breadcrumb">
				<%-- Button import --%>
				<c:import url="/auth/role/menu/func/${currentMenuId}" />					
			</ol>
		</div>
	</div>
	
	<div class="row">
		<div class="col-xs-12 col-sm-12">
			<div class="box ui-draggable ui-droppable">
			
				<div class="box-header">
					<div class="box-name">
						<i class="fa fa-search"></i> <span>費用預估作業功能 查詢條件</span>
					</div>
					<div class="box-icons">
						<a class="collapse-link"> <i class="fa fa-chevron-up"></i></a> 
						<a class="expand-link"> <i class="fa fa-expand"></i></a>
					</div>
					<div class="no-move"></div>
				</div>
				
				<div class="box-content">
					<form id="searchFrom" class="form-horizontal bootstrap-validator-form" role="form" novalidate="novalidate">
						<input type="hidden" id="saveType" value="${saveType}"/>
						<div class="form-group">
							<table style="width:100%">
								<tr>
									<td><label class="col-sm-12 control-label"><span class="s">* </span>預估類型 :</label></td>
									<td>
										<div class="col-sm-6">
												<select id="estimateType" name="estimateType"  class="populate placeholder">
												<option value="" selected>--請選擇--</option>
												<c:forEach items="${estimateType}" var="estimateType">
													<option value="${estimateType.LOOKUP_CODE}">${estimateType.LOOKUP_CODE_DESC}</option>
												</c:forEach>
											</select>
										</div>
									</td>
									<td><label class="col-sm-12 control-label"> <span class="s">* </span>預估年月 :</label></td>
									<td>
										<div class="col-sm-12">
											<input id="estimateStartYM" type="text" value=""
												name="estimateStartYM" readonly="readonly"> <label id="rightHere">至</label>
												<input id="estimateEndYM"
												type="text" value="" name="estimateEndYM" readonly="readonly">
										</div>
									</td>
								</tr>
								<tr>
									<td><div class="clearfix">&nbsp;</div></td>
								</tr>
								<tr>
									<td><label class="col-sm-12 control-label"> <span class="s">* </span>申請人 :</label></td>
									<td>
										<div class="col-sm-6">
											<input id="appUserName" type="text" class="form-control"
												name="appUserName" disabled value="${appUserName}"> 
											<input id="appUser" type="hidden" class="form-control"
												name="appUser" value="${appUser}">
										</div>
									</td>
								<td><label class="col-sm-12 control-label"><span class="s">* </span>申請日期 : </label></td>
									<td>
										<div class="col-sm-6">
										<input id="appDate" maxlength="20" type="text"
											class="form-control" name="appDate" disabled
											value="">
										</div>
									</td>
								</tr>
								<tr>
									<td><div class="clearfix">&nbsp;</div></td>
								</tr>
								<tr>

								</tr>
							</table>
						</div>
					</form>
				</div>
			</div>
		</div>
		
		<div id="ajaxSearchResult" class="col-xs-12">
			<div id="jQgrid" align="center">
			</div>
		</div>
</body>
</html>