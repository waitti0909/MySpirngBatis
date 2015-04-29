<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>

<html lang="en">
<head>
<style type="text/css">

.titleTDVo {
	text-align: center;
}

</style>
<script type="text/javascript">
var itemChoiceTable;
$(document).ready(function() {
	//確定
	$('#poItemBtnChk').click(function(){
		//alert($("#poNoTab2").val() + " : " + $("#checkBoxItemIdPoItem").val());
		$("#checkBoxItemIdTab2").val($("#checkBoxItemIdPoItem").val());
		table = $('#toItemMainTable')
		.dataTable(
				{
					"bDestroy" : true,
					"iDisplayLength" : -1,
					"aaSorting" : [ [ 0, "desc" ] ],
					"sDom" : "rS",
					"sScrollY" : '70',
					"bSort" : false,
					"bProcessing" : false,
					"sAjaxDataProp" : "rows",
					"sAjaxSource" : CONTEXT_URL                                
					 + '/st/outSourcing/out/itemMainToChk?itemId='
							 +$("#checkBoxItemIdPoItem").val() + "&poId=" + $("#poNoTab2").val(),
							"aoColumnDefs" : [ 
								{"aTargets" : [ 0 ],"mData" : 
								function(data){ 
									//alert(JSON.stringify(data))
								return data.mainItemName;
	      						}, "sWidth":"6%", "bSortable":false },
	      						
	      						{"aTargets" : [ 1 ],"mData" : 
									function(data){ 
									return data.subItemName;
		      					}, "sWidth":"6%", "bSortable":false },
		      					
		      					{"aTargets" : [2 ],"mData" : 
									function(data){ 
									//return data.itemIdName;
									return "<label>"+data.itemIdName+"</label><input name='itemIdTab2' type='hidden' value='"+data.item_ID+"'>";
		      					}, "sWidth":"17%", "bSortable":false },
		      					
		      					{"aTargets" : [3 ],"mData" : 
									function(data){ 
									return data.price;
		      					}, "sWidth":"5%", "bSortable":false },
		      					
		      					{"aTargets" : [4 ],"mData" : 
									function(data){ 
// 									return data.quantity;
									var pk = $("#poNoTab2").val() + data.item_ID;
									return "<input type='text' id='osNumber_"+pk+"' maxlength='13' onblur='checkApplyNum(this, \"數量\", "+data.price+", "+data.mgr_FEE+", 1);' name='osNumber' value='1'>";
		      					}, "sWidth":"4%", "bSortable":false },
		      					{"aTargets" : [5 ],"mData" : 
									function(data){ 
									return data.mgr_FEE + " %";
		      					}, "sWidth":"3%", "bSortable":false },
		      					
		      					{"aTargets" : [6 ],"mData" : 
									function(data){ 
		      						var pk = $("#poNoTab2").val() + data.item_ID;
		      						var amount = Math.round((data.price * data.quantity * (1 + data.mgr_FEE / 100)) * 1000000) /1000000;
		    						return "<label name='amountLabel' id='amountLabel"+pk+"'>"+amount+"</label>";
		      					}, "sWidth":"5%", "bSortable":false },
		      					{"aTargets" : [7 ],"mData" : 
									function(data){ 
			      						var pk = $("#poNoTab2").val() + data.item_ID;
			      						return "<label id='apNumberLable"+pk+"'>"+ data.quantity +"</label>";
		      					}, "sWidth":"4%", "bSortable":false },
		      					{"aTargets" : [8 ],"mData" : 
									function(data){ 
		      						var pk = $("#poNoTab2").val() + data.item_ID;
		      						var amount = Math.round((data.price * data.quantity * (1 + data.mgr_FEE / 100)) * 1000000) / 1000000;
									return "<label id='apAmountLable"+pk+"'>"+ amount +"</label>";
		      					}, "sWidth":"6%", "bSortable":false },
							],
							"oLanguage" : {
								"sProcessing" : "處理中...",
								"sZeroRecords" : "沒有匹配結果",
								"sLoadingRecords" : "讀取中..."
							},
							"fnInitComplete" : function() {
								this.fnAdjustColumnSizing();

								setBackOsItemOsNumber();
								//重新計算
								itemMainQueryAfterFun();
							},
						});
		$('#showPoItemPag').dialog('close');
	});
	//取消
	$('#poItemBtnCancel').click(function(){
		$('#showPoItemPag').dialog('close');
	});
	
	//當大項改變 連動中項
	$("#mainItemTab2").change(function() {
		$("#subItemTab2  option").remove();
		$("#subItemTab2").append("<option value=''>---請選擇---</option>").change();
		if (this.value != '') {
			$.ajax({
				url : CONTEXT_URL + "/st/outSourcing/out/itemSubSearch",
				type : "POST",
				dataType : 'json',
				data:{
					"mainItem" :  $("#mainItemTab2").val()
				},
				async : false,
				success : function(data) {
					//alert(JSON.stringify(data));
					if(data.success){
						if(data.rows.length > 0){
							//中項
							for(var i=0; i<data.rows.length; i++){		
								$("#subItemTab2").append("<option value="+data.rows[i].cat_ID+">"+data.rows[i].cat_NAME+"</option>");
							}						
						}
					}
				}
			});
		}		
	});
	
	$("#subItemTab2").change(function(){
		itemMainTableOnChange();
	});
	
}); //END $(document).ready(function()
var allData="";
function itemBoxClick(obj) {
	allData = $('#checkBoxItemIdPoItem').val();
	if ($(obj).is(':checked') ) {
		allData += $(obj).val() + ",";
		
	} else {
		if (allData.indexOf($(obj).val() + ",") != -1) {
			allData= allData.replace($(obj).val() + "," , "");
		}
	}
	$("#checkBoxItemIdPoItem").val(allData);
}

function itemMainTableOnChange(){
	//工項設定(2 table grig)
	if ($("#mainItemTab2").val() == '' || $("#subItemTab2").val() == '') {
		$('#itemMainTable tbody').html('');
		return false;
	} else {
		itemChoiceTable = $('#itemMainTable')
		.dataTable(
				{
					"bDestroy" : true,
					"iDisplayLength" : -1,
					"aaSorting" : [ [ 0, "desc" ] ],
					"sDom" : "rS",
					// 	 			"bAutoWidth":true,
					"sScrollY" : '70',
					"bSort" : false,
					// 		        "sScrollX" : "100%",
					"bProcessing" : false,
					"sAjaxDataProp" : "rows",
					"sAjaxSource" : CONTEXT_URL                                
				           + '/st/outSourcing/out/itemMainTable?poId=' 
				        		   + $("#poNoTab2").val() 
				        		   + '&mainItem='+ $("#mainItemTab2").val()
								   + '&subItem='+ $("#subItemTab2").val(),
											        		   
							"aoColumnDefs" : [ 

							{"aTargets" : [ 0 ],"mData" : 
								function(data){ 
								 //alert(JSON.stringify(data)); 
								 //alert($("#checkBoxItemIdTab2").val().indexOf(data.item_ID));
								 if ($("#checkBoxItemIdTab2").val().indexOf(data.item_ID) > -1){
									 
									 return "<input type='checkbox' checked onclick='itemBoxClick(this)' id='itemBox"+data.item_ID+"' name='itemBox' value='"+data.item_ID+"'>"
								 } else{
									 return "<input type='checkbox' onclick='itemBoxClick(this)' id='itemBox"+data.item_ID+"' name='itemBox' value='"+data.item_ID+"'>"
								 }
								      }, 
								   "sWidth":"20px", "bSortable":false },
							{"aTargets" : [ 1 ],"mData" : "item_NO", "sWidth":"50px" }, 
							{"aTargets" : [ 2 ],"mData" : "itemIdName", "sWidth":"100px"}, 
							{"aTargets" : [ 3 ],"mData" : "unit_HOUR", "sWidth":"20px"}, 
							{"aTargets" : [ 4 ],"mData" : "unit", "sWidth":"20px"}, 
							{"aTargets" : [ 5 ],"mData" : "price", "sWidth":"90px"}, 
							{"aTargets" : [ 6 ],"mData" : "quantity", "sWidth":"30px"}, 
							{"aTargets" : [ 7 ],"mData" : "price", "sWidth":"90px"}, 
							{"aTargets" : [ 8 ],"mData" : "memo", "sWidth":"500px"}
							
							],
					"oLanguage" : {
						"sProcessing" : "處理中...",
						"sZeroRecords" : "沒有匹配結果",
						"sLoadingRecords" : "讀取中..."
					},
					"fnInitComplete" : function() {
						this.fnAdjustColumnSizing();
					}
				});
	}
}


</script>

</head>

<body>
	<button class="btn btn-primary btn-label-left" style="margin-right: 10px"
		type="button" id="poItemBtnChk"><span><i class="fa fa-check"></i></span>確定</button>
	<button class="btn btn-primary btn-label-left" style="margin-right: 10px"
		type="button" id="poItemBtnCancel"><span><i class="fa fa-times"></i></span>取消</button>
	
	<div class="clearfix">&nbsp;</div>
	
	<table style="width: 60%">
		
		<tr>
			<td width="10%" nowrap="nowrap"><label
				class="col-sm-10 control-label">年度：</label></td>
			<td width="23%">
				<p style="padding-top: -35px; padding-left: -15px;" id="showYearTab2">${poMain.PO_YEAR}</p>
			</td>
			
		</tr>
		<tr>
			<td width="10%" nowrap="nowrap"><label
				class="col-sm-10 control-label">PO廠商：</label></td>
			<td width="23%">
				<p style="padding-top: -35px; padding-left: -15px;" id="showVenTab2">${company.CO_NAME}</p>
			</td>
			<td width="10%" nowrap="nowrap"><label
				class="col-sm-12 control-label">PO單號：</label></td>
			<td width="23%">
				<p style="padding-top: -35px; padding-left: -15px;" id="showPoIdTab2">${poMain.PO_NO}</p>
			</td>
		</tr>
		<tr>
			<td width="10%" nowrap="nowrap"><label
				class="col-sm-10 control-label">大項：</label></td>
			<td width="23%"><select id="mainItemTab2" name="mainItemTab2" class="form-control">
					<option value="">---請選擇---</option>
			</select></td>
			<td width="10%"><label class="col-sm-12 control-label">中項：</label></td>
			<td width="24%"><select id="subItemTab2" name="subItemTab2" class="form-control">
					<option value="">---請選擇---</option>
			</select></td>
		</tr>
	</table>
	
    <div class="clearfix">&nbsp;</div>
	
	<div>
		<table id="itemMainTable" class="scrollTable" border="1"
			style="table-layout: fixed; word-break: break-all; word-wrap: break-word;">
			<thead style="background-color: #f0f0f0;">
				<tr>
					<th class="" style="width: 20px;">選擇</th>
					<th class="" style="width: 20px;">項次</th>
					<th class="" style="width: 50px;">小項</th>
					<th class="" style="width: 10px;">工時</th>
					<th class="" style="width: 10px;">單位</th>
					<th class="" style="width: 50px;">單價</th>
					<th class="" style="width: 10px;">數量</th>
					<th class="" style="width: 50px;">小計</th>
					<th class="" style="width: 160px;">備註</th>
				</tr>
			</thead>
		</table>
	</div>

<!-- 勾選工項設定的value -->
<input id="checkBoxItemIdPoItem"  type="hidden"></input>
</body>
</html>