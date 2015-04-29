<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>

<form id="siteExch_form" action="" method="post">
<input id="siteExch_Ls_No" name="LS_NO" type="hidden" value="${lsNo}">
<input id="siteExch_Ls_Ver" name="LS_VER" type="hidden" value="${lsVer}"> 
	<div>
		<button class="btn btn-primary btn-label" type="button" id="siteExchSaveBtn">
			存檔</button>
	</div>

	<table style="width: 100%">
		<tr>
			<td nowrap="nowrap" width="8%"><label
				class="col-sm-12 control-label">互換業者 :</label></td>
			<td width="92%">
				<div class="col-sm-3" id="carrier"></div>
			</td>
		</tr>
		<tr>
			<td nowrap="nowrap"><label class="col-sm-12 control-label">互換項目
					:</label></td>
			<td>
				<div class="col-sm-12" style="margin-top: 6px">
					<div style="height: auto; float: left; display: inline">
						<input id="" type="checkbox" name="exchItem" value="EX1" >&nbsp;&nbsp;機房空間
						 <input id="" type="checkbox" name="exchItem" style="margin-left: 20px"  value="EX2" >&nbsp;&nbsp;租金
						<input id="" type="checkbox" name="exchItem" style="margin-left: 20px" value="EX3" >&nbsp;&nbsp;鐵塔
						<input id="" type="checkbox" name="exchItem" style="margin-left: 20px" value="EX4" >&nbsp;&nbsp;電費
					</div>
				</div>
			</td>
		</tr>
		<tr>
			<td valign='top' nowrap="nowrap"><label
				class="col-sm-12 control-label">備註說明 :</label></td>
			<td>
				<div class="col-sm-10" style="margin-top: 6px">
					<textarea class="form-control" id="exch_DESC" name="EXCH_DESC" rows="5" maxlength="500"></textarea>
				</div>
			</td>
		</tr>
	</table>


	<div style="margin: 5px 5px 5px 5px">
		<div style="width: 5%; height: auto; float: left; display: inline; margin-top: 15px">
			<p style="font-weight: bold;">互換站點區域:</p>
		</div>
		<div style="width: 90%; height: auto; float: left; display: inline; margin-top: 15px">
			<div style="width: 45%; height: auto; float: left; display: inline; margin-left: 10px">
			<button class="btn btn-primary btn-label" type="button" id="addExchSiteBtn"
				onclick="">新增互換站點</button>
			<button class="btn btn-primary btn-label" type="button" id=""
				onclick="delRow('siteCHTB');clearExchSiteTbody();">刪除站點</button>
			</div>
		</div>

			<table id="siteCHTB"
				class="table table-bordered table-hover table-heading no-border-bottom"
				style="width: 100%; margin-top: 5px">
				<thead>
					<tr>
						<th style="width: 15%;">互換站點編號</th>
						<th style="width: 15%;">站點名稱</th>
						<th style="width: 35%;">地址</th>
						<th style="width: 35%;">業者設備</th>
					</tr>
				</thead>
				<tbody id="siteCHTbody">

				</tbody>
			</table>
	</div>


	<div style="margin: 5px 5px 5px 5px">
		<div
			style="width: 10%; height: auto; float: left; display: inline; margin-top: 15px">
			<p style="font-weight: bold;">互換站台 :</p>
		</div>

			<table
				class="table table-striped table-bordered table-hover table-heading no-border-bottom"
				style="width: 100%; margin-top: 5px">
				<thead>
					<tr>
						<th style="width: 15%;">站點編號</th>
						<th style="width: 15%;">站台編號</th>
						<th style="width: 15%;">分攤項目</th>
						<th style="width: 15%;">分攤比率</th>
					</tr>
				</thead>
				<tbody id="exchSiteTbody">
				</tbody>
			</table>
	</div>

</form>

<script type="text/javascript">
	var actionType;
	$(document).ready(function() {
		addClassSEL("siteCHTB");
		$("select").select2();
		$("#siteExch_form :input").prop("disabled", (btnType == "view"));
		if ('${lsType}' == "1") {
			$("#siteExch_form :input").prop("disabled", true);
		}
		$('#siteTabs').hide();
	});
	
	$("#siteExch_form").bind("reset",function(){
		$("#siteCHTbody").html('');
		$("#exchSiteTbody").empty();
   });
	
	function clearExchSiteTbody() {
		$("#exchSiteTbody").empty();
	}
	
	$("#siteCHTbody td")
		.mouseenter(function() {
			$(this).parent("tr").addClass("hover");
		})
		.mouseout(function() {
			$(this).parent("tr").removeClass("hover");
	});
	
 	$("#siteCHTB").delegate("td.tbodytd", "click", function(){
		$("#siteCHTbody tr").each(function(i){
			$(this).removeClass("subItemSelect");
		});
		$(this).parent("tr").addClass("subItemSelect");
	  	var locId = $(this).parent("tr").find('td:eq(0)').text();
	  	var exchSiteAloc = $(".exchSiteAloc" + locId).val();
	  	if ($.trim(exchSiteAloc) == "") {
	  		alert("該站台資源互換分攤比尚未設定或找不到");
	  		$("#exchSiteTbody").empty();
	  		return;
	  	}
	  	$("#exchSiteTbody").empty();
		addExchSiteAlocRow(exchSiteAloc);
 	});
	 
   	
   	//取得互換站台
   	function getExchangeSiteAloc(locId) {
   		$("#exchSiteTbody").empty();
   		//最上方站點編號
   		var mainLocId = $("#siteTbody tr").closest(".selected").first().children().first().text();
   		//互換項目(已移至TB_LS_MAIN)
   		//var exchItem = $('input:checkbox[name="EXCH_ITEM"]:checked').map(function(){return $(this).val()}).get().join(",");
   		//互換業者(已移至TB_LS_MAIN)
   		//var carrierId = $("#exchCarriersSelect").val();
   		
   		//console.log("mainLocId: " + mainLocId + ", lsNo: " + $("#siteExch_Ls_No").val() + ", lsVer: " + $("#siteExch_Ls_Ver").val());
		$.ajax({			
			url : CONTEXT_URL + "/ls/LS001M_Site/getExchangeSiteAlocByLocationId",		
			data: {locId : locId,
				   mainLocId : mainLocId,
				   lsNo : $("#siteExch_Ls_No").val(),
				   lsType : $("#site_LsType").val(),
				   appSeq : $("#site_AppSeq").val(),
				   lsVer : $("#siteExch_Ls_Ver").val()
				   },
			type : "POST",
			dataType : 'json',
			success : function(data) {
				if(data.success){	
					$(".exchSiteAloc" + locId).val(data.msg);
					if (actionType != "load") {
						addExchSiteAlocRow(data.msg);
					}
				} else {
					alert(data.msg);
				}
			}
		});
	}
	
	$('#addExchSiteBtn').click(function(){
		var mainLocId = $("#siteTbody tr").closest(".selected").first().children().first().text();
   		if ($.trim(mainLocId) == "") {
   			alert("請先選擇主站點");
   			return;
   		}
   		
		openSiteDialogFrame('selectSitePage', 'receviceExchSiteData', '');	
	});
	
	function receviceExchSiteData(siteObjects) {
		var obj = JSON.parse(siteObjects);
		var flag = false;
		if(obj.siteObjs[0].maintArea != '' && 
		   $('#siteDomainList').val() == $.trim(obj.siteObjs[0].maintArea.charAt(0))){
			flag = true;
		}
		
		if(flag){
			$("#siteCHTbody tr").each(function() {
				 //console.log("互換站點編號: " + $(this).children().first().text() + ", 此次選擇編號: " + obj.siteObjs[0].location_ID);
				 if($.trim($(this).children().first().text()) == obj.siteObjs[0].location_ID){
					flag = false;
				}; 
			});
			if(flag){
				 if(obj.siteObjs[0].location_ID!=null && obj.siteObjs[0].location_ID!=''){
					 var data = {
							lsNo : $("#siteExch_Ls_No").val(),
							lsVer : $("#siteExch_Ls_Ver").val(),
							locId : obj.siteObjs[0].location_ID,
							domain : obj.siteObjs[0].maintArea,
							zipCode : obj.siteObjs[0].locZip,
							locAddr : obj.siteObjs[0].locAddr,
							appSeq :  $("#site_AppSeq").val(),
							lsType :  $("#site_LsType").val(),
							locName : obj.siteObjs[0].locName
						};
					$.ajax({			
						url : CONTEXT_URL + "/ls/LS001M_Site/queryResExchSite",		
						data: data,
						type : "POST",
						dataType : 'json',
						success : function(data) {
							if(data.success){	
								addExchSiteRow(data.msg);
							}
						}
					});   
				}else{
					alert("Location不得為空");
				}
				
			}else{
				alert("該站點已經存在");
			}
		}else{
			alert("該站點不在區域內");
		}

	}
	
	//互換站點
	function loadExchSiteRow(data){
		actionType = "load";
		var row1 = '';
		var locId;
		var checked;
		//var data = JSON.parse(data);
		for(var i = 0;i<data.length;i++){
			locId = data[i]['exch_LOC_ID'];
			row1+='<tr id="tr' + locId + '"><td class="tbodytd" style="width: 15%;">' + locId +'</td>';
			row1+='<td class="tbodytd" style="width: 15%;">' + data[i]['locName']+'</td>';
			row1+='<td class="tbodytd" style="width: 45%;">' + data[i]['addr']+'</td>';
			row1+='<td><div style="height: auto; float: left; display: inline">';
			checked = $.trim(data[i]['equip_2G']) != "" ? "checked" : "" ;
			row1+='<input id="" type="checkbox" class="equip_2G_Check" ' + checked +'>';
			row1+='&nbsp;&nbsp;2G <input id="" name="" class="equip_2G"  type="text" onblur="checkIntOnBlur(this);" size="1" maxlength="10" value="' + $.trim(data[i]['equip_2G']) +'" /> 套';
			checked = $.trim(data[i]['equip_3G']) != "" ? "checked" : "" ;
			row1+='<input id="" type="checkbox" style="margin-left: 10px"  class="equip_3G_Check" ' + checked +'>';
			row1+='&nbsp;&nbsp;3G <input id="" name="" class="equip_3G"  type="text" onblur="checkIntOnBlur(this);" size="1" maxlength="10" value="' + $.trim(data[i]['equip_3G']) +'" /> 套';
			checked = $.trim(data[i]['equip_4G']) != "" ? "checked" : "" ;
			row1+='<input id="" type="checkbox" style="margin-left: 10px"  class="equip_4G_Check" ' + checked +'>';
			row1+='&nbsp;&nbsp;4G <input id="" name="" class="equip_4G"  type="text" onblur="checkIntOnBlur(this);" size="1" maxlength="10" value="' + $.trim(data[i]['equip_4G']) +'" /> 套';
			row1+='<input id="" name="" class="exchSiteAloc' + locId + '" type="hidden"/>';
			row1+='</div>';
			row1+='</td></tr>';
			getExchangeSiteAloc(locId)
		}
		$("#siteCHTbody").prepend(row1);	
	}
	
	//互換站點
	function addExchSiteRow(data){
		var row1 = '';
		var locId;
		var locationJson = JSON.parse(data);
			for(var i = 0;i<locationJson.length;i++){
				locId = locationJson[i]['LOCATION_ID'];
				row1+='<tr id="tr'+locationJson[i]['LOCATION_ID']+'"><td class="tbodytd" style="width: 15%;">'+locationJson[i]['LOCATION_ID']+'</td>';
				row1+='<td class="tbodytd" style="width: 15%;">'+locationJson[i]['LocName']+'</td>';
				row1+='<td class="tbodytd" style="width: 45%;">'+locationJson[i]['Addr']+'</td>';
				row1+='<td><div style="height: auto; float: left; display: inline">';
				row1+='<input id="" type="checkbox" class="equip_2G_Check">&nbsp;&nbsp;2G <input id="" name="" class="equip_2G" onblur="checkIntOnBlur(this);" type="text" size="1" maxlength="10" /> 套';
				row1+='<input id="" type="checkbox" style="margin-left: 10px" class="equip_3G_Check">&nbsp;&nbsp;3G <input id="" name="" class="equip_3G" onblur="checkIntOnBlur(this);" type="text" size="1" maxlength="10" /> 套';
				row1+='<input id="" type="checkbox" style="margin-left: 10px" class="equip_4G_Check">&nbsp;&nbsp;4G <input id="" name="" class="equip_4G" onblur="checkIntOnBlur(this);" type="text" size="1" maxlength="10" /> 套';
				row1+='<input id="" name="" class="exchSiteAloc' + locationJson[i]['LOCATION_ID'] + '" type="hidden"/>';
				row1+='</div>';
				row1+='</td></tr>';
			}
		$("#siteCHTbody").prepend(row1);	
		getExchangeSiteAloc(locId)
		
	}
	
	//互換站台
	function addExchSiteAlocRow(data){
		var row = '';
		var locationJson = JSON.parse(data);
		for(var i = 0;i<locationJson.length;i++){
			row+='<tr><td>' + locationJson[i].locationId + '</td>';
			row+='<td>' + locationJson[i].btsSiteId + '</td>';
			row+='<td>' + (locationJson[i].alocItem == "R" ? "租金" : "電費") + '</td>';
			row+='<td>' + locationJson[i].aloc_RATIO + '%</td>';
			row+='</tr>';
		}
		$("#exchSiteTbody").prepend(row);									
	}
	
	
	$("#siteExchSaveBtn").click(function(){
		var resExchJson = getResExchDatas();
		var jsonObj = JSON.parse(resExchJson);
		if (jsonObj.length == 0) {
			alert("請先新增互換站點");
			return;
		}
		$.ajax({
			url : CONTEXT_URL + "/ls/LS001M_Site/saveUpdateResExch",
			type : 'POST',
			dataType : "json",
			data : {"resExchJson" : resExchJson,
				    "lsNo" : '${lsNo}',
				    "lsVer" : '${lsVer}',
				    "mainLocId" : $("#siteTbody tr").closest(".selected").first().children().first().text()},
			async : false,
			success : function(data) {
				alert(data.msg);
			}
		});
	});
	
	function getResExchDatas() {
		var exchDesc = $("#exch_DESC").val();
		var datas = [];
		var msg = "";
		$("#siteCHTbody tr").each(function() {
			var locId = $(this).find('td:eq(0)').text();
			var data = {};
			data['exch_LOC_ID'] = locId;
			data['exch_DESC'] = exchDesc;
			if ($(this).find('.equip_2G_Check').prop("checked")) {
				data['equip_2G'] = $(this).find('.equip_2G').val();
			}
			if ($(this).find('.equip_3G_Check').prop("checked")) {
				data['equip_3G'] = $(this).find('.equip_3G').val();
			}
			if ($(this).find('.equip_4G_Check').prop("checked")) {
				data['equip_4G'] = $(this).find('.equip_4G').val();
			}
			data['exchSiteAloc'] = $(this).find('.exchSiteAloc' + locId).val();
			
			if ($.trim(data['equip_2G']) == "" &&
				$.trim(data['equip_3G']) == "" &&	
				$.trim(data['equip_4G']) == "") {
				msg = "互換站點編號: " + data['exch_LOC_ID'] + ", 業者設備尚未填寫";
				return false;
			}
			
			if ($.trim(data['exchSiteAloc']) == "") {
					msg = "互換站點編號: " + data['exch_LOC_ID'] + ", 該站台資源互換分攤比尚未設定或找不到";
					return false;
			}
			datas.push(data);
		});
		
		if ($.trim(msg) != "") {
			alert(msg);
			return;
		}
		
		return JSON.stringify(datas);
		
	}
	
	//互換項目
	function exchItemLoad(exchItem) {
		$("input[name='exchItem']").each(function () {
			$(this).prop("checked", ($.inArray($(this).val(), exchItem.split(',')) > -1));
		});
		$("input[name='exchItem']").prop("disabled", true);
	}
</script>

