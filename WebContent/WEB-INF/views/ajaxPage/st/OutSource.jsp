<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>委外申請</title>

<script type="text/javascript">
var mainTableBind = false;
$(document).ready(function() {
	
		//當委外廠商改變 連動PO單
		$("#coVatNoTab2").change(function() {
			//alert($("#coVatNoTab2").val() + ":" +$("#osType").val());
			$("#poNoTab2  option").remove();
			$("#poNoTab2").append("<option value=''>---請選擇---</option>").change();
			$.ajax({
				url : CONTEXT_URL + "/st/outSourcing/out/poNoSearchTable",
				type : "POST",
				dataType : 'json',
				data:{
					"coVatNo" :  $("#coVatNoTab2").val(),
					"osType" : $("#osType").val(),
					"workId" : $("#showWorkId").text()
				},
				async : false,
				success : function(data) {
					//alert(JSON.stringify(data));
					if(data.success){
						if(data.rows.length > 0){
// 							$("#subItemTab2  option").remove();
							for(var i=0; i<data.rows.length; i++){	
								var textValue = data.rows[i].po_NO + " "+data.rows[i].po_NAME+" "+ data.rows[i].fee_TYPE;
								$("#poNoTab2").append("<option value="+data.rows[i].po_ID+">"+textValue+"</option>");
							}
						}
					}
				}
			});
			
		});
		//當po當改變 清除 工項明細
		$("#poNoTab2").change(function() {
			$("#checkBoxItemIdTab2").val("");			
// 			var table = $('#toItemMainTable').dataTable();
			$('#toItemMainTable').dataTable().fnClearTable();
		});
		
		if("${disabled}"){
			initData();
		}
		
	});//document ready end
	
	function initData(){
		$("#showOrderIdTab2").text("${tbSiteOutsourcing.ORDER_ID}");
		  $("#orderIdTab2").val("${tbSiteOutsourcing.ORDER_ID}");
		  
		  $("#showOrderStatusTab2").text(outSourcingStatus("${tbSiteOutsourcing.STATUS}"));
		  $("#orderStatusTab2").val("${tbSiteOutsourcing.STATUS}");
		  
		  $("#showOsIdTab2").text("${tbSiteOutsourcing.OS_ID}");
		  $("#osIdTab2").val("${tbSiteOutsourcing.OS_ID}");
		  
		  $("#showAplUserTab2").text("${tbSiteOutsourcing.APL_USER_NAME}");
		  $("#aplUserTab2").val("${tbSiteOutsourcing.APL_USER}");
		  
		  $("#showEqpTypeTab2").text("${tbSiteOutsourcing.EQP_TYPE_NAME}");
		  $("#eqpTypeTab2").val("${tbSiteOutsourcing.EQP_TYPE}");
		  
		  $("#showAplTimeTab2").text("${tbSiteOutsourcing.APL_TIME}");
		  $("#aplTimeTab2").val("${tbSiteOutsourcing.APL_TIME}");
		  
		  $("#showOsTimeTab2").text("${tbSiteOutsourcing.OS_TIME}");
		  $("#osTimeTab2").val("${tbSiteOutsourcing.OS_TIME}");
		  
		  $("#showApDateTab2").text("${tbSiteOutsourcing.AP_DATE}");
		  $("#apDateTab2").val("${tbSiteOutsourcing.AP_DATE}");
		  
		  $("#showRepTeamTab2").text("${tbSiteOutsourcing.REP_TEAM_NAME}");
		  $("#repTeamTab2").val("${tbSiteOutsourcing.REP_TEAM}");
		   
		  $("#showSiteHuntWorkTypeTab2").text("${workType}");
		  $("#showWorkTypeTab2").text("${siteWork.BTS_SITE_ID}");
		  
		  $("#showOsDescTab2").val("${tbSiteOutsourcing.OS_DESC}");
		  toItemMainTableFun("${tbSiteOutsourcing.OS_ID}");
		  
		  //$("#coVatNoTab2").val("${tbSiteOutsourcing.CO_VAT_NO}").change();
		  //$("#poNoTab2").val("${tbSiteOutsourcing.PO_ID}").change();
		  
		  $("#poYearTab2").val("${tbSiteOutsourcing.PO_YEAR}");
		  
		  
		$("#coVatNoTab2  option").remove();
	    $("#poNoTab2  option").remove();
	    $("#coVatNoTab2").append("<option selected disable value='${tbComCompany.CO_VAT_NO}'>${tbComCompany.CO_NAME}</option>");
		$("#poNoTab2").append("<option selected  value='${tbSiteOutsourcing.PO_ID}'>${tbComPoMain.PO_NO}</option>");
		$("#poNoTab2").trigger('change');
		
		$("#showOsDescTab2").prop("disabled",true);
		$("#poNoTab2").prop("disabled",true);
		$("#coVatNoTab2").prop("disabled",true);
		
		$.ajax({
			url : CONTEXT_URL + "/commom/signHistory",
			type : 'POST',
			datatype:'html',
			data : {
				processType : '${os_process_apply}', //'SearchSiteOutsourcingApply',
				applyNo :   $("#osIdTab2").val()           
			},
			async : false,
			success : function(data) {
				$("#applySignLog").empty();
				$('#applySignLog').append(data);
			}
	  	});
		
		$.ajax({
			url : CONTEXT_URL + "/commom/signHistory",
			type : 'POST',
			datatype:'html',
			data : {
				processType : '${os_process_accept}', //'SearchSiteOutsourcingApply',
				applyNo :   $("#osIdTab2").val()           
			},
			async : false,
			success : function(data) {
				$("#acceptSignLog").empty();
				$('#acceptSignLog').append(data);
			}
	  	});
	}
	
	function bindOutsourceTableTrEvt() {
		mainTableBind = true;
		$('#outSourceTable tbody').off('click', 'tr').on( 'click','tr', function () {
			var data = $('#outSourceTable').dataTable().fnGetData($(this).closest("tr").index());
			// set tr highlight by Charlie
			$("#outSourceTable tbody tr").removeClass('row_selected_highlight');
			$(this).addClass('row_selected_highlight');
			//alert(JSON.stringify(data));
			if (data == null){
				toItemMainTableFun('');
				return;
			}
			//alert(JSON.stringify(data));
			$("#poId").val(data['po_ID']);
			$.ajax({
				url : CONTEXT_URL + "/st/outSourcing/out/outSourceSearchTable",
				type : 'POST',
				data : {
					osId :data['os_ID']                         
				},
				async : true,
				success : function(data) {
				  //alert( JSON.stringify(data));
				  $("#showOrderIdTab2").text(data.rows[0].order_ID);
				  $("#orderIdTab2").val(data.rows[0].order_ID);
				  
				  $("#showOrderStatusTab2").text(outSourcingStatus(data.rows[0].status));
				  $("#orderStatusTab2").val(data.rows[0].status);
				  
				  $("#showOsIdTab2").text(data.rows[0].os_ID);
				  $("#osIdTab2").val(data.rows[0].os_ID);
				  
				  $("#showAplUserTab2").text(data.rows[0].apl_USER_NAME);
				  $("#aplUserTab2").val(data.rows[0].apl_USER);
				  
				 
				  
				  $("#showAplTimeTab2").text(data.rows[0].apl_TIME);
				  $("#aplTimeTab2").val(data.rows[0].apl_TIME);
				  
				  $("#showOsTimeTab2").text(data.rows[0].os_TIME);
				  $("#osTimeTab2").val(data.rows[0].os_TIME);
				  
				  $("#showApDateTab2").text(data.rows[0].ap_DATE);
				  $("#apDateTab2").val(data.rows[0].ap_DATE);
				  
				  $("#showRepTeamTab2").text(data.rows[0].rep_TEAM_NAME);
				  $("#repTeamTab2").val(data.rows[0].rep_TEAM);
				  
				  $("#status").val(data.rows[0].status);
				  
				  if ($("#checkDabClick").val() != "view"){
				    disabledBtn(data.rows[0].status , $("#loginUsrVendor").val());
				  }
				 
				  //尋點-工務類別
				  if($("#siteBuildWorkType").val() == undefined){
					  $("#showSiteHuntWorkTypeTab2").text($("#siteHuntWorkTypeInput").val());
					  $("#siteHuntWorkTypeTab2").val($("#siteHuntWorkTypeInput").val());
					  
				  }
				  //建站-工務類別
				  if ($("#siteHuntWorkTypeInput").val() == undefined){
					  $("#showSiteHuntWorkTypeTab2").text($('#siteBuildWorkType :selected').text());
					  $("#siteHuntWorkTypeTab2").val($("#siteBuildWorkType").val());
					  
				  }
				  
				  //尋點-站點編號
				  if ($("#siteBuildBtsSiteId").val()  == undefined){
					  $("#showWorkTypeTab2").text($("#siteHuntBtsSiteId").val());
					  $("#workTypeTab2").val($("#siteHuntBtsSiteId").val());
				  }
				  //建站-站點編號
				  if ($("#siteHuntBtsSiteId").val()  == undefined){
					  $("#showWorkTypeTab2").text($("#siteBuildBtsSiteId").val());
					  $("#workTypeTab2").val($("#siteBuildBtsSiteId").val());
				  }
				  
				  //尋點-設備型態
				  if ($("#siteBuildEqpType").val()  == undefined){
					  $("#showEqpTypeTab2").text($('#siteHuntEqpType :selected').text());
					  $("#eqpTypeTab2").val($("#siteHuntEqpType").val());
				  }else {  //$("#siteHuntEqpType").val()  == undefined
					//建站-設備型態
					  $("#showEqpTypeTab2").text($('#siteBuildEqpType :selected').text());
					  $("#eqpTypeTab2").val($("#siteBuildEqpType").val());
				  }
				  
				  
				  $("#showOsDescTab2").val(data.rows[0].os_DESC);
				  toItemMainTableFun(data.rows[0].os_ID);
				  
				  $("#coVatNoTab2").val(data.rows[0].co_VAT_NO).change();
				  $("#poNoTab2").val(data.rows[0].po_ID).change();
				  
				  $("#poYearTab2").val(data.rows[0].po_YEAR);
				  if($("#disableAllBtn").val() == 'false'){  
					
					  if ($("#checkDabClick").val() != "view"){
						  disabledBtn(data.rows[0].status, $("#loginUsrVendor").val());
					  }
					 
				  }
				  // 動態申請簽核歷程
				  $.ajax({
						url : CONTEXT_URL + "/commom/signHistory",
						type : 'POST',
						datatype:'html',
						data : {
							processType : $("#os_process_apply").val(), //'SearchSiteOutsourcingApply',
							applyNo :   $("#osIdTab2").val()           
						},
						async : true,
						success : function(data) {
							$("#applySignLog").empty();
							$('#applySignLog').append(data);
						}
				  });
				  
				  // 動態驗收歷程
				  $.ajax({
						url : CONTEXT_URL + "/commom/signHistory",
						type : 'POST',
						datatype:'html',
						data : {
							processType : $("#os_process_accept").val(), //'SearchSiteOutsourcingApply',
							applyNo :   $("#osIdTab2").val()           
						},
						async : true,
						success : function(data) {
							$("#acceptSignLog").empty();
							$('#acceptSignLog').append(data);
						}
				  });
				}	
			});            
		});
	}
	
	function toItemMainTableFun(osId){
			var readOnlyScript = "${view}" == 'view' || '${loginUsrVendor}' == 'true' ? "disabled" : "";
		  //第二個table Str
		  table = $('#toItemMainTable').dataTable({
			"bDestroy" : true,
			"iDisplayLength" : -1,
			"aaSorting" : [ [ 0, "desc" ] ],
			"sDom" : "rS",
			"bAutoWidth":false,
			"sScrollY" : '70',
			"bSort" : false,
			"bProcessing" : false,
			"sAjaxDataProp" : "rows",
			"sAjaxSource" : CONTEXT_URL                                

			 + '/st/outSourcing/out/itemMainQuery?osId='+ osId,
					"aoColumnDefs" : [ 
 
						{"aTargets" : [ 0 ],"mData" : 
						function(data){ 
						//alert(JSON.stringify(data))
						return data.mainItemName;
						}, "sWidth":"5%", "bSortable":false },
						
						{"aTargets" : [ 1 ],"mData" : 
							function(data){ 
							return data.subItemName;
    					}, "sWidth":"5%", "bSortable":false },
    					
    					{"aTargets" : [2 ],"mData" : 
							function(data){ 
							return "<label>"+data.itemIdName+"</label><input name='itemIdTab2' type='hidden' value='"+data.item_ID+"'>";
    					}, "sWidth":"16%", "bSortable":false },
    					
    					{"aTargets" : [3 ],"mData" : 
							function(data){ 
							return data.price;
    					}, "sWidth":"3%", "bSortable":false },
    					
    					{"aTargets" : [4 ],"mData" : 
							function(data){ 
    							var pk = osId + $("#poId").val() + data.item_ID;
    							if ($('#orderStatusTab2').val() === 'OS01' || $('#orderStatusTab2').val() === 'OS03') {
    								var osNumber = data.number;
	      							if(data.number != null){
	      								osNumber = data.number;
	      							}
	      							return "<input type='text' id='osNumber_"+pk+"' "+readOnlyScript+" class='checkNumAndPoint' onblur='checkApplyNum(this, \"數量\", "+data.price+", "+data.mgr_FEE+", "+osNumber+");' name='osNumber' maxlength='13' value='"+osNumber+"'>";
    							} else if (data.number != null) {
    								return data.number;
    							} else {
    								return 1;
    							}
								return data.number;
    						}, 
    						"sWidth":"4%", "bSortable":false 
    					},
    					
    					{"aTargets" : [5 ],"mData" : 
							function(data){ 
    						return data.mgr_FEE + " %";
    					}, "sWidth":"2%", "bSortable":false },
    					{"aTargets" : [6 ],"mData" : 
							function(data){
	    						var pk = osId + $("#poId").val() + data.item_ID;
	    						//alert(JSON.stringify(data));
	    						var amount = Math.round((data.price * data.number * (1 + data.mgr_FEE / 100)) * 1000000) / 1000000;					
								return "<label name='amountLabel' id='amountLabel"+pk+"'>"+amount+"</label>";
    					}, "sWidth":"4%", "bSortable":false },
    					
    					{"aTargets" : [7 ],"mData" : 
									function(data){
    						           	var pk =osId + $("#poId").val() +data.item_ID;
			      						if($('#orderStatusTab2').val() === 'OS05'){
			      							var apNumber = data.number;
			      							if(data.ap_NUMBER != null){
			      								apNumber = data.ap_NUMBER;
			      							}
			      							return "<input type ='hidden' name='itemId' value='"+data.item_ID+"'>" +
			      							       "<input type='text' id='apNumber"+pk+"' "+readOnlyScript+" class='checkNumAndPoint' maxlength='13' onblur='checkNum(this,"+data.price+","+data.mgr_FEE+",\"驗收數量\","+data.number+")' name='apNumber'  value='"+apNumber+"'>";
			      						} else if (data.ap_NUMBER != null) {
											return "<label id='apNumberLable"+pk+"'>"+ data.ap_NUMBER +"</label>";
				      					} else {
					      					return "<label id='apNumberLable"+pk+"'>"+ data.number +"</label>";
				      					}
		      					}, "sWidth":"4%", "bSortable":false 
		      			},
		      			{"aTargets" : [8 ],"mData" : 
									function(data){ 
		      				            var pk = osId + $("#poId").val() + data.item_ID;
		      							var amount = Math.round((data.price * data.ap_NUMBER * (1 + data.mgr_FEE / 100)) * 1000000) / 1000000;
			      						return "<input id='apAmount"+pk+"' type='hidden' name='apAmount' value='"+ amount +"' >"+
			      					       "<label id='apAmountLable"+pk+"'>"+ amount +"</label>";
		      					}, "sWidth":"4%", "bSortable":false 
		      			}
					],
					"oLanguage" : {
						"sProcessing" : "處理中...",
						"sZeroRecords" : "沒有匹配結果",
						"sLoadingRecords" : "讀取中..."
					},
					"fnInitComplete" : function() {
						this.fnAdjustColumnSizing();
						itemMainQueryAfterFun();
						chkOutSourceItemId();
					},
				});
		  
	}//第二個table End

	//檢查 outsource的 itemId
	function chkOutSourceItemId() {
		var itemId = "";
		$("#toItemMainTable").find("tbody>tr").each(function() {
			if ($(this).find("input[name='itemIdTab2']").val() != undefined) {
				itemId += $(this).find("input[name='itemIdTab2']").val() + ",";
			}
		});
		$('#checkBoxItemIdTab2').val(itemId);
	}

	function outSourceTableFun(osId) {
		//委外申請(1 table)
		table = $('#outSourceTable').dataTable({
				"bDestroy" : true,
				"iDisplayLength" : -1,
				"aaSorting" : [ [ 0, "desc" ] ],
				"sDom" : "rS",
				"sScrollY" : '70',
				"bSort" : false,
				"bProcessing" : false,
				"sAjaxDataProp" : "rows",
				"sAjaxSource" : CONTEXT_URL
						+ '/st/outSourcing/out/outSourceTable?orderId='
						+ $("#orderId").val(),
				"aoColumnDefs" : [
						{
							"aTargets" : [ 0 ],
							"mData" : "os_ID"
						},
						{
							"aTargets" : [ 1 ],
							"mData" : "co_VAT_NO"
						},

						{
							"aTargets" : [ 2 ],
							"mData" : function(data) {
								//alert($("#poNoTab2").val());
								return data.po_DOMAIN;
							},
							"bSortable" : false
						},
						{
							"aTargets" : [ 3 ],
							"mData" : function(data) {
								var sss = "";
								if (data.apl_TIME != null) {
									var aplTime = data.apl_TIME;
									var date = new Date(parseInt(aplTime));
									var month = (date.getMonth() + 1);
									sss = date.getFullYear()
											+ "-"
											+ (month < 10 ? ("0" + month)
													: month) + '-'
											+ (date.getDate() < 10 ? ("0" + date.getDate()) : date.getDate());
								}

								return sss;
							},
							"bSortable" : false
						}, {
							"aTargets" : [ 4 ],
							"mData" : function(data) {
								//alert(JSON.stringify(data))
								return outSourcingStatus(data.status);
							},
							"bSortable" : false
						}, {
							"aTargets" : [ 5 ],
							"mData" : function(data) {
								//alert(JSON.stringify(data))
								return data.pay_FLG == 'Y' ? '是' : '否';
							},
							"bSortable" : false
						}, ],
				"oLanguage" : {
					"sProcessing" : "處理中...",
					"sZeroRecords" : "沒有匹配結果",
					"sLoadingRecords" : "讀取中..."
				},
				"fnInitComplete" : function() {
					this.fnAdjustColumnSizing();
					if (!mainTableBind) bindOutsourceTableTrEvt();
					if (osId == '0') {
						//add by miles 2014.12.22
						//自動選擇第一筆資料
						$('#outSourceTable tbody').find("tr:first").trigger('click');
					}
					$('#outSourceTable tr:gt(0)').each(function(){
				    	//$.log($('td:first', $(this)).html());
						if ($('td:first', $(this)).html() == osId) {
							$(this).trigger('click');	// 將原本資料預設進行點選動作
						}
					});
				}
			});
	}

	//狀態
	function outSourcingStatus(status) {
		if (status == "OS01") {
			return "草稿";
		} else if (status == "OS02") {
			return "審核中";
		} else if (status == "OS03") {
			return "駁回";
		} else if (status == "OS04") {
			return "待派工";
		} else if (status == "OS05") {
			return "已派工";
		} else if (status == "OS06") {
			return "驗收審核";
		} else if (status == "OS07") {
			return "已驗收";
		} else if (status == "OS08") {
			return "已取消";
		}
	}
	
	function checkApplyNum(obj, msg, price, mgrFee, oriNumber) {
		if (!qtyCheck(obj.value, msg)) {
			obj.value = oriNumber;
			return false;
		} else if (!validateNumber(obj.value, 6, 6)) {
			alert(msg + "不符合資料格式！(整數六位，小數六位)");
			obj.value = oriNumber;
			return false;
		} else if (new Number(obj.value) <= 0) {
			alert(msg + "不可為0！");
			obj.value = oriNumber;
			return false;
		}
		var osAmount = Math.round((obj.value * price * (1 + mgrFee / 100)) * 1000000) / 1000000; //數量*單價
// 		$("#osNumber" + pk).html(osNumber);
		var pk = obj.id.split("_")[1];
		$("#amountLabel" + pk).text(addCommas(osAmount));
		$("#apNumberLable" + pk).text(obj.value);
		$("#apAmount" + pk).val(osAmount);
		$("#apAmountLable" + pk).text(addCommas(osAmount));

		//重新計算
		itemMainQueryAfterFun();
	}

	//(為html物件,單價,管理費,訊息)
	function checkNum(obj, price, mgrFee, msg, oriNumber) {
		var amount = 0;
		if (!qtyCheck(obj.value, msg)) {
			obj.focus();
			return false;
		} else if (!validateNumber(obj.value, 6, 6)) {
			alert(msg + "不符合資料格式！(整數六位，小數六位)");
			return false;
		} else if (new Number(obj.value) > new Number(oriNumber)) {
			// 驗證驗收數量需小於申請數量
			alert(msg + "需小於申請數量！");
			
			obj.value = oriNumber;
			return false;
		}
		var apNumber = Math.round((obj.value * price * (1 + mgrFee / 100)) * 1000000) / 1000000; //數量*(單價*(1+管理費/100))
		var id = obj.id.replace("apNumber", "apAmountLable");
		$("#" + id).text(apNumber);
		id = id.replace("apAmountLable", "apAmount");
		$("#" + id).val(apNumber);

		//重新計算
		itemMainQueryAfterFun();
	}
	
	//重新計算 總金額
	function itemMainQueryAfterFun() {
		
		var totalAmount = 0;
		$("#toItemMainTable").find("tbody>tr").each(function(){
			totalAmount += (($(this).find("td:eq(8)").text()).replace(/\,/g,"")) * 1;
		});
		totalAmount = Math.round(totalAmount);	// 總和取到整數，四捨五入
		
		$('#priceTab2').val(totalAmount);
		$('#showPriceTab2').text(addCommas(totalAmount));
		
		//正整數+小數
		$(".checkNumAndPoint").keypress(function(event) {  
            var keyCode = event.which;  
            if (keyCode == 46 || (keyCode >= 48 && keyCode <=57))  {
                return true;  
            }else {
                return false; 
            }
        }).focus(function() {  
            this.style.imeMode='disabled';  
        });
	}
</script>
</head>
<body>
	<table id="outSource" style="width: 100%">
		<tr>
			<td width="10%" nowrap="nowrap"><label
				class="col-sm-12 control-label">工單號碼 :</label></td>
			<td width="23%">
				<p style="padding-top: 15px; padding-left: 5px;" id="showOrderIdTab2"></p>
				<input id="orderIdTab2" name="orderIdTab2" type="hidden"></input>
			</td>
			<td width="10%" nowrap="nowrap"><label
				class="col-sm-12 control-label">派工單位 :</label></td>
			<td width="23%">
				<p style="padding-top: 15px; padding-left: 5px;" id="showRepTeamTab2"></p>
				<input id="repTeamTab2" name="repTeamTab2" type="hidden"></input>
			</td>
			<td width="10%"><label class="col-sm-12 control-label">派工狀態
					:</label></td>
			<td width="24%">
				<p style="padding-top: 15px; padding-left: 5px;" id="showOrderStatusTab2"></p> <input
				id="orderStatusTab2" name="orderStatusTab2" type="hidden"></input>
			</td>
		</tr>
		<tr>
			<td width="10%" nowrap="nowrap"><label
				class="col-sm-12 control-label">派工單號 :</label></td>
			<td width="23%">
				<p style="padding-top: 15px; padding-left: 5px;" id="showOsIdTab2"></p>
				<input id="osIdTab2" name="osIdTab2" type="hidden"></input>
			</td>
			<td width="10%" nowrap="nowrap"><label
				class="col-sm-12 control-label">工務類別 :</label></td>
			<td width="23%">
				<p style="padding-top: 15px; padding-left: 5px;" id="showSiteHuntWorkTypeTab2"></p>
				<input id="siteHuntWorkTypeTab2" name="siteHuntWorkTypeTab2" type="hidden"></input>
			</td>
			<td width="10%"><label class="col-sm-12 control-label">申請人
					:</label></td>
			<td width="24%">
				<p style="padding-top: 15px; padding-left: 5px;" id="showAplUserTab2"></p> <input
				id="aplUserTab2" name="aplUserTab2" type="hidden"></input>
			</td> 
		</tr>
		<tr>
			<td width="10%" nowrap="nowrap"><label
				class="col-sm-12 control-label">基站編號 :</label></td>
			<td width="23%">
				<p style="padding-top: 15px; padding-left: 5px;" id="showWorkTypeTab2"></p> <input
				id="workTypeTab2" name="workTypeTab2" type="hidden"></input>
			</td>
			<td width="10%" nowrap="nowrap"><label
				class="col-sm-12 control-label">設備型態 :</label></td>
			<td width="23%">
				<p style="padding-top: 15px; padding-left: 5px;" id="showEqpTypeTab2"></p>
				<input id="eqpTypeTab2" name="eqpTypeTab2" type="hidden"></input>
			</td>
			<td width="10%"><label class="col-sm-12 control-label">申請時間
					:</label></td>
			<td width="24%">
				<p style="padding-top: 15px; padding-left: 5px;" id="showAplTimeTab2"></p> 
				<input id="aplTimeTab2" name="aplTimeTab2" type="hidden"></input>
			</td>
		</tr>
		<tr>
			<td width="10%" nowrap="nowrap"><label
				class="col-sm-12 control-label">派工時間 :</label></td>
			<td width="23%">
				<p style="padding-top: 15px; padding-left: 5px;" id="showOsTimeTab2"></p>
				<input id="osTimeTab2" name="osTimeTab2" type="hidden"></input>
			</td>
			<td width="10%" nowrap="nowrap"><label
				class="col-sm-12 control-label">驗收時間 :</label></td>
			<td width="23%">
				<p style="padding-top: 15px; padding-left: 5px;" id="showApDateTab2"></p>
				<input id="apDateTab2" name="apDateTab2" type="hidden"></input>
			</td>
		</tr>
		<tr>
			<td width="10%" nowrap="nowrap"><label
				class="col-sm-12 control-label">廠商名稱 :</label></td>
			<td width="23%"><select id="coVatNoTab2" name="coVatNoTab2"  class="form-control" >
					<option value="">---請選擇---</option>
			</select></td>
			<td width="10%"><label class="col-sm-12 control-label">PO單號
					:</label></td>
			<td width="24%"><select id="poNoTab2" name="poNoTab2" class="form-control">
					<option value="">---請選擇---</option>
			</select></td>
			<td width="10%" nowrap="nowrap" style="padding-top: 15px;"><label
				class="col-sm-12 control-label">總金額 :</label></td>
			<td width="23%" style="padding-top: 15px;">
			    <p style="padding-top: 15px; padding-left: 5px;" id="showPriceTab2"></p>
				<input id="priceTab2" name="priceTab2" type="hidden"></input>
			</td> 
		</tr>
		<tr>
			<td valign='top' width="10%" nowrap="nowrap"
				style="padding-top: 15px;"><label
				class="col-sm-12 control-label">工項明細 :</label></td>
			<td colspan="5">
				
					<div>
		<table id="toItemMainTable"  class="table table-bordered table-striped table-hover table-heading table-datatable dataTables_wrapper" border="1"
			style="table-layout: fixed; word-break: break-all; word-wrap: break-word;">
			<thead style="background-color: #f0f0f0;">
				<tr>
				    <th class="theadth" style="width: 50px;">大類</th>
					<th class="theadth" style="width: 50px;">中類</th>
					<th class="theadth" style="width: 170px;">小類</th>
					<th class="theadth" style="width: 30px;">單價</th>
					<th class="theadth" style="width: 30px;">數量</th>
					<th class="theadth" style="width: 30px;">管理費</th>
					<th class="theadth" style="width: 30px;">小計</th>
					<th class="theadth" style="width: 30px;">驗收數量</th>
					<th class="theadth" style="width: 30px;">驗收金額</th>
				</tr>
			</thead>
		</table>
	</div>
				
			</td>
		</tr>
		<tr>
			<td valign='top' width="10%" nowrap="nowrap"
				style="padding-top: 15px;"><label
				class="col-sm-12 control-label">委外說明 :</label></td>
			<td colspan="5" nowrap="nowrap">
				<div style="margin-top: 15px">
					<textarea class="form-control" id="showOsDescTab2" name="showOsDescTab2" rows="3"></textarea>
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<div class="clearfix">&nbsp;</div>
			</td>
		</tr>
		<tr>
			<td><label class="col-sm-12 control-label"
				style="margin-bottom: 80px">申請簽核意見 :</label></td>
			<td colspan="5">
				<div class="form-group" id="applySignLog"
					style="margin-left: 15px; width: 94%; margin-bottom: 65px;">
				</div>
			</td>
		</tr>
		<tr>
			<td><label class="col-sm-12 control-label"
				style="margin-bottom: 80px">驗收簽核意見 :</label></td>
			<td colspan="5">
				<div class="form-group" id="acceptSignLog"
					style="margin-left: 15px; width: 94%; margin-bottom: 65px;">
				</div>
			</td>
		</tr>
	</table>
	
	<input id="checkBoxItemIdTab2" type="hidden"></input>
	<input id="poYearTab2" type="hidden"></input>
	<input id="poId" type="hidden" >

	
</body>
</html>