<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>支票作廢申請作業-修改</title>
<script
	src="<s:url value="/resources/plugins/tinymce/tinymce.min.js" />"></script>
<script
	src="<s:url value="/resources/plugins/tinymce/jquery.tinymce.min.js" />"></script>
	<script	src="<s:url value="/resources/js/validate.js" />"></script>
<script type="text/javascript">
	$(document).ready(function() {	
		LoadSelect2Script("<s:url value="/resources/plugins/select2/select2.min.js" />", selectDisDefault);		
				mountGrid();//掛載表格
				var data = {
						disregardNo : $("#disregardNoE").val()
					};
				$("#editGrid").setGridParam({datatype : "json",postData : data}).trigger("reloadGrid");
				
				mountButEvent();
			});
			function selectDisDefault() {
			$("#addDomain").select2();
		}
	//掛載表格
		function mountGrid(){
			$("#editGrid").jqGrid({
				datatype : "local",
				autowidth:'true',
				pager : '#editPager',
				url : CONTEXT_URL + "/pay/pay010/searchPayment/",
				colNames : [ '票據號碼','付款對象', '名稱', '金額', '支票兌現日','作廢原因','說明','','','',''],
				colModel : [ 
					{name : 'check_NBR',index : 'check_NBR', align : 'center',sortable : false},
				    {name : 'paymentUserId',index : 'paymentUserId' , align : 'center',sortable : false,formatter: nullForZero},
				    {name : 'paymentUserName',index : 'paymentUserName' , align : 'center',sortable : false}, 
				    {name : 'check_amt',index : 'totalAmt', align : 'center',sortable : false,formatter: nullForZero},
				    {name : 'checkCashDate',index : 'checkCashDate' , align : 'center',sortable : false,formatter: function(cellvalue, options, rowObject){
			   			if (cellvalue != null && cellvalue != "") {
				   			var stDate = new Date(cellvalue);
							return stDate.getFullYear() + "/" + (stDate.getMonth() + 1) + "/" + stDate.getDate();
			   			}else {
			   				return "";
			   			}}}, 
				    {name : 'check_DISREGARD_REASON',index : 'check_DISREGARD_REASON' , align : 'center',sortable : false,editable: true,edittype:"select"
				    ,
				  		editoptions: { value: getCheckReason('CHECK_DISREGARD_REASON') ,
					                 	 class : "require" ,
					                 	dataInit: function (elem) {value: gridSelect(elem);}
			            }
				    
				    },
				    {name : 'check_DISREGARD_REASON_DESC',index : 'check_DISREGARD_REASON_DESC', align : 'center',sortable : false, editable: true,edittype:"text",editoptions:{size:"10",maxlength:"10"}},
				    {name : 'reasonCode',index : 'reasonCode',sortable : false,hidden:true},
				    {name : 'payment_SEQ_NBR',index : 'payment_SEQ_NBR',sortable : false,hidden:true},
				    {name : 'payment_REQ_NO',index : 'payment_REQ_NO',sortable : false,hidden:true},
				    {name : 'payment_REQ_ITEM_NO',index : 'payment_REQ_ITEM_NO',sortable : false,hidden:true}],
				caption : "支票清單",
				rownumbers : true,
				multiselect : true,
				onSelectRow : function(rowId) {//點一下帶出清單
					var rowData = jQuery(this).getRowData(rowId);
					getPayment(rowData);
					jQuery('#editGrid').jqGrid('editRow',rowId,true);
				},
				gridComplete : function() {
					 /* var ids = jQuery("#editGrid").jqGrid('getDataIDs');
						for (var i = 0; i < ids.length; i++) 
						{
						    var rowId = ids[i];
						    var rowData = jQuery('#editGrid').jqGrid ('getRowData', rowId);
							$('#editGrid').jqGrid('setColProp', 'check_DISREGARD_REASON', { editoptions: { value: getCheckReason('check_disregard_reason')}});
							jQuery('#editGrid').jqGrid('editRow',rowId,true);
						}*/
					}	 
			});
			$("#editDtlGrid").jqGrid({
				datatype : "local",
				autowidth:'true',
				url : CONTEXT_URL + "/pay/pay010/searchPaymentDtl/",
				colNames : [ '請款單號','租約編號', '名稱', '站點編號', '出租人','出租名稱','付款對象','名稱','付款方式', '合約金額', '本次請款', '未稅金額'
				,'營業稅','所得稅','健保補充費','實付金額'],
				colModel : [ 
					{name : 'cashReqNo',index : 'cashReqNo', align : 'center',sortable : false},
				    {name : 'contractNo',index : 'contractNo' , align : 'center',sortable : false},
				    {name : 'lsName',index : 'lsName' , align : 'center',sortable : false}, 
				    {name : 'location_ID',index : 'location_ID', align : 'center',sortable : false},
				    {name : 'lessorId',index : 'lessorId' , align : 'center',sortable : false}, 
				    {name : 'lessorName',index : 'lessorName' , align : 'center',sortable : false},
				    {name : 'payment_USER_ID',index : 'payment_USER_ID', align : 'center',sortable : false},
				    {name : 'payment_USER_NAME',index : 'payment_USER_NAME', align : 'center',sortable : false},
				    {name : 'paymethodName',index : 'paymethodName', align : 'center',sortable : false},
				    {name : 'payAmt',index : 'payAmt' , align : 'center',sortable : false},
				    {name : 'sumExclusive',index : 'sumExclusive' , align : 'center',sortable : false}, 
				    {name : 'tax_EXCLUSIVE_TOTAL_AMT',index : 'tax_EXCLUSIVE_TOTAL_AMT', align : 'center',sortable : false},
				    {name : 'total_BUSINESS_TAX',index : 'total_BUSINESS_TAX' , align : 'center',sortable : false}, 
				    {name : 'total_INCOME_TAX',index : 'total_INCOME_TAX' , align : 'center',sortable : false},
				    {name : 'total_NHI_AMT',index : 'total_NHI_AMT', align : 'center',sortable : false},
				    {name : 'sumAllAmt',index : 'sumAllAmt', align : 'center',sortable : false}],
				caption : "支票明細",
				rownumbers : true
			});
			$(window).resize(function() {
				$(window).unbind("onresize");
				$("#editGrid,.editGrid").setGridWidth($("#jQgrid").width() - 10);
				$("#editDtlGrid,.editDtlGrid").setGridWidth($("#jQgrid").width() - 10);
				$(window).bind("onresize", this);
			}).trigger('resize');
		}	
		function getPayment(rowData) {
			var data = {
				reqNo : rowData.payment_REQ_NO,
				reqItemNo : rowData.payment_REQ_ITEM_NO,
				seqNo : rowData.payment_SEQ_NBR
			};
			$("#editDtlGrid").setGridParam({datatype:"json", postData:data,page:1}).trigger("reloadGrid");
		}	
	function nullForZero(cellvalue, options, rowObject) {
		var returnValue = cellvalue;
		if (cellvalue == "" || cellvalue == "undefined" || cellvalue == null) {
			returnValue = "0";
		}
		return returnValue;
	}
	function gridSelect(elem) {
		$(elem).select2();
	}
	function delEdit() {
		var selr = jQuery('#editGrid').jqGrid('getGridParam', 'selarrrow');
		if (selr.length == 0) {
			alert("未勾選要刪除的資料列,請重新操作!");
				return false;
		}
		
		var delArray =[];
		if (confirm("提醒：請確認資料是否刪除？(將直接刪除資料，不必儲存!!)")) {
			for (var i = 0; i < selr.length; i++) {
				var rowData = jQuery('#editGrid').jqGrid('getRowData',selr[i]);
				 var TbPayCheckDisregardKey = {
						"disregard_NO" : $("#disregardNoE").val(),
						"check_NBR" : rowData.check_NBR
					};
					delArray.push(TbPayCheckDisregardKey);
					$('#editGrid').jqGrid("delRowData", selr[i]);
			}
			$.ajax({
					url : CONTEXT_URL + "/pay/pay010/deleteData/",
					data : JSON.stringify(delArray),
					contentType : "application/json",
					type : "POST",
					dataType : 'json',
					success : function(data) {
						alert("刪除完成");
					},
					error : function(data) {
						alert(data.msg);
					}
				});	 
			$("#editDtlGrid").jqGrid().clearGridData();
		}	
	}
	function editCheck(str){
		var selr = jQuery('#editGrid').jqGrid('getGridParam', 'selarrrow');
		if (!selr.length == 0) {
			for (var i = 0; i < selr.length; i++) {
					var rowData = jQuery('#editGrid').jqGrid('getRowData',selr[i]);
					var checkReason = "#"+selr[i]+"_check_DISREGARD_REASON";
					checkReason=$(checkReason).find("option:selected").val();
					if(checkReason==""){
						alert("票據號碼:" + rowData.check_NBR+" 請選擇作廢原因");
						return false;
					}
				}
			} else {
				alert("未勾選要"+str+"的資料列,請重新操作!");
				return false;
			}
		return true;	
	}
	function updateData(status,statusDesc){
		var selr = jQuery('#editGrid').jqGrid('getGridParam', 'selarrrow');
		var editArray =[];
		for (var i = 0; i < selr.length; i++) {
			var rowData = jQuery('#editGrid').jqGrid('getRowData',selr[i]);
			var checkReason = "#"+selr[i]+"_check_DISREGARD_REASON";
			var checkReasonDesc = "#"+selr[i]+"_check_DISREGARD_REASON_DESC";
			checkReason=$(checkReason).find("option:selected").val();
		    checkReasonDesc=$(checkReasonDesc).val();
			var TbPayCheckDisregard = {
					"disregard_NO" : $("#disregardNoE").val(),
					"check_NBR" : rowData.check_NBR,
					"status" : status,
					"check_DISREGARD_REASON" : checkReason,
					"check_DISREGARD_REASON_DESC" : checkReasonDesc,
					"md_USER" : $("#appUser").val()
				};
				editArray.push(TbPayCheckDisregard);
		}
		$.ajax({
				url : CONTEXT_URL + "/pay/pay010/updateData/",
				data : JSON.stringify(editArray),
				contentType : "application/json",
				type : "POST",
				dataType : 'json',
				success : function(data) {
					alert("作廢單號 : " + $("#disregardNoE").val()+" "+statusDesc+"!");
					isDetail=true;
					parent.$.fancybox.close();//關閉視窗
				}
			});	
	}
	//掛載表格Event
	function mountButEvent() {
		//儲存
		$('#saveEditBtn').click(function() {
			if (editCheck("儲存")) {
				updateData('A','儲存成功');
			}
		});

		//刪除
		$('#delEditBtn').click(function() {
			delEdit();
		});

		//送審			
		$('#approveEditBtn').click(function() {
			if (editCheck("送審")) {
				updateData('B','送審中');
			}			
		});
	}

	function getCheckReason(lookupType) {
		var returnValue = "";
		var data = {
			type : lookupType
		};
		$.ajax({
			url : CONTEXT_URL + "/pay/pay010/lookupByType",
			data : data,
			type : "POST",
			async : false,
			dataType : 'json',
			success : function(data) {
				if (data.success) {
					for (i = 0; i < data.rows.length; i++) {
						if (i == 0) {
							returnValue += "" + ":" + "--請選擇--" + ";";
						}
						if (i < data.rows.length - 1) {
							returnValue += data.rows[i].lookup_CODE + ":"
									+ data.rows[i].lookup_CODE_DESC + ";";
						} else {
							returnValue += data.rows[i].lookup_CODE + ":"
									+ data.rows[i].lookup_CODE_DESC;
						}
					}
				}
			}
		});
		return returnValue;
	}
</script>
</head>
<body>
<div class="row">
		<div id="breadcrumb" class="col-md-12">
			<ol class="breadcrumb">
				<div class="col-sm-12" id="btnDiv">
					<button class="btn btn-primary btn-label-left" type="button"
						id="saveEditBtn">
						<span><i class="fa fa-save"></i></span> 儲存
					</button>
					<button class="btn btn-primary btn-label-left" type="button"
						id="delEditBtn">刪除明細</button>
					<button class="btn btn-primary btn-label-left" type="button"
						id="approveEditBtn">送審</button>
				</div>
			</ol>
		</div>
	</div>
	<div class="box-header">
		<div class="box-name">
			<i class="fa fa-edit"></i> <span>支票作廢申請作業-修改</span>
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
					<div class="form-group">
						<div class="row">
							<div class="col-md-12">
									<table style="width: 100%">
									<tr>
										<td><label class="col-sm-12 control-label">作廢單號：</label></td>
										<td><div class="col-sm-6">
												<input id="disregardNoE" type="text" disabled
													class="form-control" name="disregardNoE" value="${disregardNo}">
											</div></td>
										<td><label class="col-sm-12 control-label">維運區：</label></td>
										<td>
											<div class="col-sm-6">
												<select id="addDomain" name="addDomain" disabled>
													<option value="" selected disabled>--請選擇--</option>
													<c:forEach items="${domainSelect}" var="addDomain">
														<c:choose>
															<c:when test="${domain eq addDomain.ID}">
																<option value="${addDomain.ID}" selected>${addDomain.NAME}</option>
															</c:when>
															<c:otherwise>
																<option value="${addDomain.ID}">${addDomain.NAME}</option>
															</c:otherwise>
														</c:choose>
													</c:forEach>
												</select>
											</div>
										</td>
									</tr>
									<tr>
										<td><div class="clearfix">&nbsp;</div></td>
									</tr>
									<tr>
										<td><label class="col-sm-12 control-label">申請人：</label></td>
										<td><div class="col-sm-6">
												<input id="appUserName" type="text" disabled
													class="form-control" name="appUserName" value="${appUserName}">
												<input id="appUser" type="hidden" class="form-control"
													name="appUser" value="${appUser}">	
											</div></td>
										<td><label class="col-sm-12 control-label">申請日期：</label></td>
										<td>
											<div class="col-sm-6">
												<input id="appDate" type="text" disabled class="form-control"
													class="form-control" name="appDate" 
													value="<fmt:formatDate  value="${appDate}"  pattern="yyyy/MM/dd"  />">
											</div>
										</td>
									</tr>
								</table>	
								<tr>
									<td><div class="clearfix">&nbsp;</div></td>
								</tr>
								<div id="ajaxSearchResult" class="col-xs-12">
									<div id="jQgrid" align="center">
										<table id="editGrid"></table>
										<div id="editPager"></div>
									</div>
								</div>
								<tr>
									<td><div class="clearfix">&nbsp;</div></td>
								</tr>
								<div id="ajaxSearchResult" class="col-xs-12">
									<div id="jQgrid" align="center">
										<table id="editDtlGrid"></table>
									</div>
								</div>
							</div>
						</div>
						<hr>
					</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>