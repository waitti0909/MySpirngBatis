<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>

<!-- 類別、項目 START -->
<div class="form-group" style="margin-top: 10px">
	<label class="col-sm-1 control-label">類別 :</label>
	<div class="col-sm-3">
			<select id="selType" class="populate placeholder" name=""
				onchange="getItem(this.value);">
				<c:forEach items="${lsAddTypeEnum}" var="item">
				<option value="${item.value}">${item.label}</option>
				</c:forEach>
			</select>
	</div>
	<label class="col-sm-1 control-label">項目 :</label>
	<div class="col-sm-3">
		<select id="itemSEL" class="populate placeholder" name=""
			onchange="getTbStyle(this.value);">
			<c:forEach items="${itemSEL}" var="item">
				<option value="${item.key}">${item.value}</option>
			</c:forEach>
		</select>
	</div>
</div>
<!-- 類別、項目 END -->

<!-- 租金起算日異動   START -->
<div id="rentDateChDiv" style="margin-top: 10px; display: none">
<form class="form-horizontal" id="rentDateChForm">
	<fieldset class="ftSolidLine">
		<legend class="legend">租金起算日異動</legend>
		<table id="rentDateChTB" class="scrollTable"
			style="table-layout: fixed; word-break: break-all; word-wrap: break-word; width: 100%">
			<thead style="background-color: #f0f0f0;">
				<tr style="height: 25px">
					<th class="theadth" style="width: 30%;">站點編號</th>
					<th></th>
					<th></th>
					<th></th>
					<th></th>
					<th></th>
					<th class="theadth" style="width: 35%;">租金起算日</th>
					<th></th>
					<th></th>
					<th></th>
					<th class="theadth" style="width: 35%;">終止日</th>
				</tr>
			</thead>
			<tbody id="rentDate">
			</tbody>
		</table>
	</fieldset>
	<input id="lsNoRent" name="lsNoRent" type="hidden"> 
	<input id="lsVerRent" name="lsVerRent" type="hidden"> 
	<input id="appSeqRent" name="appSeqRent" type="hidden"> 
	<input id="appModeRent" name="appModeRent" type="hidden">
	<input id="selTypeRent" name="selTypeRent" type="hidden">
	<input id="itemSELRent" name="itemSELRent" type="hidden">
</form>
</div>
<!-- 租金起算日異動   END -->

<!-- 手機回饋異動   STRAT -->
<div id="phoneRewardDiv" style="margin-top: 10px; display: none">
<form class="form-horizontal" id="phoneRewardForm">
	<fieldset class="ftSolidLine">
		<legend class="legend">手機回饋異動</legend>

		<label class=" control-label">原申請手機 :</label>
		<table
			class="table table-striped table-bordered table-hover table-heading no-border-bottom"
			style="width: 100%;">
			<thead>
				<tr style="height: 25px">
					<th style="width: 20%;">手機型號</th>
					<th style="width: 20%;">數量</th>
					<th style="width: 20%;">預估金額</th>
					<th style="width: 20%;">回饋原因</th>
					<th style="width: 20%;">回饋說明</th>
				</tr>
			</thead>
			<tbody id="phoneObody">
			</tbody>
		</table>

		<div style="margin-top: 5px">
			<div
				style="width: 10%; height: auto; float: left; display: inline; margin-top: 15px">
				<p style="font-weight: bold;">實領手機 :</p>
			</div>
			<div
				style="width: 90%; height: auto; float: left; display: inline; margin-top: 15px">
				<button class="btn btn-primary btn-label" type="button" id=""
					onclick="addPhone('phoneTbody');">新增手機</button>
				<button class="btn btn-primary btn-label" type="button" id=""
					onclick="delRow('phoneTB');">刪除手機</button>
			</div>

			<div id="phoneDIV">
				<table id="phoneTB"
					class="table table-bordered table-hover table-heading no-border-bottom"
					style="width: 100%;">
					<thead>
						<tr>
							<th style="width: 20%;">手機型號</th>
							<th style="width: 20%;">數量</th>
							<th style="width: 20%;">預估金額</th>
							<th style="width: 20%;">實際成本</th>
							<th style="width: 20%;">實領日期</th>
						</tr>
					</thead>
					<tbody id="phoneTbody">
					</tbody>
				</table>
			</div>
		</div>
	</fieldset>
	<input id="lsNoReward" name="lsNoReward" type="hidden"> 
	<input id="lsVerReward" name="lsVerReward" type="hidden"> 
	<input id="appSeqReward" name="appSeqReward" type="hidden"> 
	<input id="appModeReward" name="appModeReward" type="hidden">
	<input id="selTypeReward" name="selTypeReward" type="hidden">
	<input id="itemSELReward" name="itemSELReward" type="hidden">
</form>
</div>
<!-- 手機回饋異動   END -->


<!-- 站台編號異動   STRAT -->
<div id="siteIdChDiv" style="margin-top: 10px; display: none">
<form class="form-horizontal" id="siteIdChForm">
	<fieldset class="ftSolidLine">
		<legend class="legend">站台編號異動</legend>

		<table id="dataCh_siteInfoTB" class="scrollTable"
			style="table-layout: fixed; word-break: break-all; word-wrap: break-word; width: 100%">
			<thead style="background-color: #f0f0f0;">
				<tr>
					<th class="theadth" style="width: 25%;">站點編號</th>
					<th class="theadth" style="width: 25%;">站台名稱</th>
					<th class="theadth" style="width: 25%;">租金起算日</th>
					<th class="theadth" style="width: 25%;">地址</th>
				</tr>
			</thead>
			<tbody id="dataCh_siteInfo">

			</tbody>
		</table>

		<div style="margin-top: 5%">

			<div style="width: 90%; height: auto; float: left; display: inline">
			</div>
			<table id="dataCh_siteEditTB" class="scrollTable"
				style="table-layout: fixed; word-break: break-all; word-wrap: break-word; width: 100%;">
				<thead style="background-color: #f0f0f0;">
					<tr>
						<th class="theadth" style="width: 25%;">站台編號</th>
						<th class="theadth" style="width: 25%;">更換站台</th>
						<th class="theadth" style="width: 25%;">站台電費分攤比</th>
						<th class="theadth" style="width: 25%;">站台租金分攤比</th>
						<th></th>
						<th></th>
						<th></th>
						<th></th>
					</tr>
				</thead>
				<tbody id="dataCh_siteEditTbody">
				</tbody>
			</table>
		</div>
	</fieldset>
	<input id="lsNositeIdCh" name="lsNositeIdCh" type="hidden"> 
	<input id="lsVersiteIdCh" name="lsVersiteIdCh" type="hidden"> 
	<input id="selTypesiteIdCh" name="selTypesiteIdCh" type="hidden">
	<input id="itemSELsiteIdCh" name="itemSELsiteIdCh" type="hidden">	
</form>
</div>
<!-- 站台編號異動   END -->

<script type="text/javascript">
	$(document).ready(function() {
	
		var appMode =  $("#appMode").text();
		//類別
		$('#selType').val('${selType}');
		selText=$("#selType").find("option:selected").text();
		
		//項目
		$('#itemSEL').trigger("change");
		
		//租金起算日異動
		$('#rentDateChTB').scrolltable({
			stripe : true,
			oddClass : 'odd',
			maxHeight : 100
		});				
		$('#rentDateChTB table:last').addClass('table-striped');
		
		$('.rentDateChPicker').datepicker();
		
		//站台編號異動
		$('#dataCh_siteInfoTB').scrolltable({
			stripe : true,
			oddClass : 'odd',
			maxHeight : 80
		});			
		
		$('#dataCh_siteEditTB').scrolltable({
			stripe : true,
			oddClass : 'odd',
			maxHeight : 120
		});	
		
		
		//按鈕呈現
		$('#buttonDiv').html("");
		var str = '<c:forEach items="${button}" var="data"><button class="btn btn-primary btn-label" type="button" id="${data.key}" style="margin-right: 10px" >${data.value}</button></c:forEach>';
		$('#buttonDiv').append(str);
		
		$('#viewDealDiv').append('<button class="btn btn-primary btn-label" type="button" id="btn_1M3View" style="margin-left: 5px;margin-top: 6px">檢視原合約</button>');
		
		//檢視合約按鈕
		$('#btn_1M3View').click(function() {		
			$.fancybox.open({
	 			href : CONTEXT_URL + "/ls/LS001M",
	 			type : 'ajax',
	 			width : $(window).width(),
	 			height : $(window).width(),
	 			autoSize : false,
	 			padding : [0,0,0,0],
	 			scrolling : 'yes',
	 			helpers:{
	 				overlay:{closeClick:false},
	 				title : {type : 'float'}
	 			},
	 			title : "檢視",
	 			openEffect : 'elastic',
	 			closeEffect : 'fade',
	 			ajax : {
					data : {
						btnType : "view",
						lsNo : $("#lsNo").text(),
						lsVer : $("#lsVer").text(),
						appSeq: $("#appSeq").text()
					},
					type : "POST",
					async : false
			    },
	 	        afterClose : function() {
	 			}
	 		});	
		});
		
		//存檔
		$('#btn_1M3Save').click(function() {
			var itemSEL=$("#itemSEL").find(":selected").text();
			if(itemSEL=='租金起算日'){
				$("#lsNoRent").val($("#lsNo").text())
				$("#lsVerRent").val($("#lsVer").text())
				$("#appSeqRent").val($("#appSeq").text())
				$("#appModeRent").val($("#appMode").text())
				$("#selTypeRent").val($("#selType").find(":selected").val())
				$("#itemSELRent").val(itemSEL)
				saverentDateCh();
			} else if (itemSEL == '手機回饋'){
				$("#lsNoReward").val($("#lsNo").text())
				$("#lsVerReward").val($("#lsVer").text())
				$("#appSeqReward").val($("#appSeq").text())
				$("#appModeReward").val($("#appMode").text())
				$("#selTypeReward").val($("#selType").find(":selected").val())
				$("#itemSELReward").val(itemSEL);
				savePhoneReward();
			} else if (itemSEL == '站台編號異動'){
				$("#lsNositeIdCh").val($("#lsNo").text())
				$("#lsVersiteIdCh").val($("#lsVer").text())
				$("#appSeqsiteIdCh").val($("#appSeq").text())
				$("#appModesiteIdCh").val($("#appMode").text())
				$("#selTypesiteIdCh").val($("#selType").find(":selected").val())
				$("#itemSELsiteIdCh").val(itemSEL);
				saveSiteIdCh();
			}
		});
		//資異  實領手機
		addPhone("phoneTbody");
		addClassSEL("phoneTB");
		
		//addClassSEL("dataCh_siteInfoTB");
		
		//公用-選項欄位初始狀態
		initState(appMode);
		
		//控制畫面是否唯讀
		initStates(appMode);
	});
	
	//儲存租金起算日異動
	function saverentDateCh(){
		if(checkRentDateCh())
		{
			var data = $("#rentDateChForm").serialize();
			//取得站台資訊
			$.ajax({			
				url : CONTEXT_URL + "/ls/LS001M3/saveRentDate",		
				data: data,
				type : "POST",
				dataType : 'json',
				success : function(data) {
					if(data.success){
						alert(data.msg);
						parent.$.fancybox.close();
					}
				}
			});
		} 
	}
	
	//取得 資料異動-租金起算日異動資料
	function rentDateChTB(){
		var appMode=$("#appMode").text();
		var lsNo=$("#lsNo").text();
		var appSeq=$("#appSeq").text();
		if(appMode=='NEW')
		{
			searchTbLsLocation(lsNo);
		}
		else
		{
			searchTbLsLocationAdded(lsNo,appSeq);
		}
	}
	
	//租金起算日異動檢核
	function checkRentDateCh(){
		var flag=true;
		$('#rentDate tr').each(function(){
			var payBeginDate=$(this).find("input[name='lsPayBeginDate']").val();
			if(payBeginDate!='')
			{
				flag=false;
			}
		});
		if(flag)
		{
			alert("無站點填寫需異動之租金起算日，請填寫需異動站點之租金起算日");
			return false;
		}
		return true;
	}
	
	//取得站點資訊  By合約
	function searchTbLsLocation(lsNo){
		
		$.ajax({			
			url : CONTEXT_URL + "/ls/LS001M3/searchTbLsLocation",		
			data: {
				lsNo : lsNo,
				domain : ""
			},
			type : "POST",
			dataType : 'json',
			success : function(data) {
				if(data.success){
					rentDateEdit(data);
				}
			}
		});
	}
	
	//取得站點資訊暫存檔資料   By合約 申請單seq
	function searchTbLsLocationAdded(lsNo,appSeq){
		$.ajax({			
			url : CONTEXT_URL + "/ls/LS001M3/searchTbLsLocationAdded",		
			data: {
				lsNo : lsNo,
				appSeq : appSeq,
				addState:"AFTER"
			},
			type : "POST",
			dataType : 'json',
			success : function(data) {
				if(data.success){
					rentDateEdit(data);
				}
			}
		});
	}
	
	//組成資料異動-租金起算日異動資料
	function rentDateEdit(data){
		//清空Table內容
		$("#rentDate tr").remove(); 
		
		var rows = data.rows;
		var row1 = '';
		for ( var r in rows) {						
			row1+='<tr id="tr'+r+'"><td class="tbodytd" style="width: 30%;">'+rows[r].location_ID+'</td>';
			row1+='<td  class="tbodytd" style="display: none"><input name="location_ID" type="hiddlen" value="'+rows[r].location_ID+'"></td>';
			row1+='<td  class="tbodytd" style="display: none"><input name="ls_VER" type="hiddlen" value="'+rows[r].ls_VER+'"></td>';
			row1+='<td  class="tbodytd" style="display: none"><input name="zip_CODE" type="hiddlen" value="'+rows[r].zip_CODE+'"></td>';
			row1+='<td  class="tbodytd" style="display: none"><input name="domain" type="hiddlen" value="'+rows[r].domain+'"></td>';
			row1+='<td  class="tbodytd" style="display: none"><input name="lsEffDate" type="hiddlen" value="'+rows[r].lsEffDate+'"></td>';
			row1+='<td class="tbodytd" style="width: 35%;"><input class="form-control rentDateChPicker" readonly="readonly" name="lsPayBeginDate" value="'+rows[r].lsPayBeginDate+'" placeholder="點選輸入框"></td>';
			row1+='<td  class="tbodytd" style="display: none"><input name="olsPayBeginDate" type="hiddlen" value="'+rows[r].lsPayBeginDate+'"></td>';
			row1+='<td  class="tbodytd" style="display: none"><input name="lsEndDate" type="hiddlen" value="'+rows[r].lsEndDate+'"></td>';
			row1+='<td class="tbodytd" style="width: 35%;">'+rows[r].lsEndDate+'</td></tr>';
		}
		$("#rentDate").append(row1);
		$('.rentDateChPicker').datepicker();
	}
	
	//儲存資料異動-合約手機回饋
	function savePhoneReward(){	
		if(checkPhoneReward())
		{
			var data = $("#phoneRewardForm").serialize();
			//取得站台資訊
			$.ajax({			
				url : CONTEXT_URL + "/ls/LS001M3/savePhoneReward",		
				data: data,
				type : "POST",
				dataType : 'json',
				success : function(data) {
					if(data.success){
						alert(data.msg);
						parent.$.fancybox.close();
					}
				}
			}); 
		}
	}
	
	//取得資料異動-合約手機回饋資訊
	function phoneRewar(){
		var appMode=$("#appMode").text();
		var lsNo=$("#lsNo").text();
		var appSeq=$("#appSeq").text();
		
		if(appMode=='NEW')
		{
			searchLsReward();
		}
		else
		{
			searchLsRewardAdded(lsNo,appSeq);
		}
	}
	
	//手機回饋資訊檢核
	function checkPhoneReward(){
		var rowCount=$('#phoneTbody tr').length;
		if (rowCount == 0)
		{
			alert("無合約手機回饋資料");
			return false;
		}
		
		
		var flag=true;
		$('#phoneTbody tr').each(function(){
			var rewardId=$(this).find("input[name='REWARD_ID']").val();
			if(rewardId !='' && typeof rewardId != 'undefined')
			{
				flag=false;
			}
			else
			{
				flag=true;
			}
		});
		
		if(flag)
		{
			alert("請填寫回饋手機資訊");
			return false;
		}
		return true;
	}
	
	//取得資料異動-合約手機回饋資訊:申請單新撐時
	function searchLsReward(){
		//清空Table內容
		$("#phoneObody tr").remove();
		$("#phoneTbody tr").remove();
		
		//取得原合約手機回饋資訊
		$.ajax({			
			url : CONTEXT_URL + "/ls/LS001M3/searchLsReward",		
			data: {
				lsNo : $("#lsNo").text()
			},
			type : "POST",
			dataType : 'json',
			success : function(data) {
				if(data.success){
					var rows = data.rows;
					var row1 = '';
					var row2 = '';
					for ( var r in rows) {						
						row1+='<tr id="tr'+r+'"><td class="tbodytd" style="width: 20%;">'+rows[r].reward_ID+'</td>';
						row1+='<td class="tbodytd" style="display: none"><input name="oreward_ID" type="hiddlen" value="'+rows[r].reward_ID+'"></td>';
						row1+='<td class="tbodytd" style="display: none"><input name="ols_VER" type="hiddlen" value="'+rows[r].ls_VER+'"></td>';
						row1+='<td class="tbodytd" style="display: none"><input name="oreward_TYPE" type="hiddlen" value="'+rows[r].reward_TYPE+'"></td>';
						row1+='<td class="tbodytd" style="display: none"><input name="oreward_QTY" type="hiddlen" value="'+rows[r].reward_QTY+'"></td>';
						row1+='<td class="tbodytd" style="display: none"><input name="oestimate_AMT" type="hiddlen" value="'+rows[r].estimate_AMT+'"></td>';
						row1+='<td class="tbodytd" style="display: none"><input name="oreward_RESN" type="hiddlen" value="'+rows[r].reward_RESN+'"></td>';
						row1+='<td class="tbodytd" style="display: none"><input name="oreward_DESC" type="hiddlen" value="'+rows[r].reward_DESC+'"></td>';
						row1+='<td class="tbodytd" style="width: 20%;">'+rows[r].reward_QTY+'</td>';
						row1+='<td class="tbodytd" style="width: 20%;">'+rows[r].estimate_AMT+'</td>';
						row1+='<td class="tbodytd" style="width: 20%;">'+rows[r].resnDesc+'</td>';
						row1+='<td class="tbodytd" style="width: 20%;">'+rows[r].reward_DESC+'</td></tr>';
						
						row2+='<tr id="tr'+r+'"><td class="tbodytd" style="width: 20%;"><input class="form-control" name="REWARD_ID" maxlength="100" type="text" style="width: 100%;" value="'+rows[r].reward_ID+'"></td>';
						row2+='<td class="tbodytd" style="width: 20%;"><input class="form-control" name="REWARD_QTY" class="REWARD_QTY" onblur="checkIntOnBlur(this);" type="number"  maxlength="100" type="text" style="width: 100%;padding: 2px 0px 0px 10px" value="'+rows[r].reward_QTY+'"></td>';
						row2+='<td class="tbodytd" style="width: 20%;"><input class="form-control" name="ESTIMATE_AMT" class="ESTIMATE_AMT" onblur="checkIntOnBlur(this);" type="number" maxlength="100" type="text" style="width: 100%;padding: 2px 0px 0px 10px" value="'+rows[r].estimate_AMT+'"></td>';
						row2+='<td class="tbodytd" style="width: 20%;"></td>';
						row2+='<td class="tbodytd" style="width: 20%;"><input class="phoneDate form-control" readonly="readonly" name="GET_DATE" placeholder="點選輸入框" value="'+rows[r].getDate+'"></td></tr>';
					}
					$("#phoneObody").append(row1);
					$("#phoneTbody").append(row2);
					$('.phoneDate').datepicker({
						dateFormat : "yy/mm/dd",
						changeYear : true,
						changeMonth : true,
						showButtonPanel : true
					});
				}
			}
		});
	}
	
	//
	function searchLsRewardAdded(lsNo,appSeq){
		//清空原合約手機回饋暫存資訊Table內容
		$("#phoneObody tr").remove();
		
		//取得原合約手機回饋暫存資訊
		$.ajax({			
			url : CONTEXT_URL + "/ls/LS001M3/searchLsRewardAddedBySeq",		
			data: {
				lsNo : lsNo,
				appSeq : appSeq,
				addState : "BEFORE"
			},
			type : "POST",
			dataType : 'json',
			success : function(data) {
				if(data.success){
					var rows = data.rows;
					var row1 = '';
					for ( var r in rows) {						
						row1+='<tr id="tr'+r+'"><td class="tbodytd" style="width: 20%;">'+rows[r].reward_ID+'</td>';
						row1+='<td class="tbodytd" style="display: none"><input name="oreward_ID" type="hiddlen" value="'+rows[r].reward_ID+'"></td>';
						row1+='<td class="tbodytd" style="display: none"><input name="ols_VER" type="hiddlen" value="'+rows[r].ls_VER+'"></td>';
						row1+='<td class="tbodytd" style="display: none"><input name="oreward_TYPE" type="hiddlen" value="'+rows[r].reward_TYPE+'"></td>';
						row1+='<td class="tbodytd" style="display: none"><input name="oreward_QTY" type="hiddlen" value="'+rows[r].reward_QTY+'"></td>';
						row1+='<td class="tbodytd" style="display: none"><input name="oestimate_AMT" type="hiddlen" value="'+rows[r].estimate_AMT+'"></td>';
						row1+='<td class="tbodytd" style="display: none"><input name="oreward_RESN" type="hiddlen" value="'+rows[r].reward_RESN+'"></td>';
						row1+='<td class="tbodytd" style="display: none"><input name="oreward_DESC" type="hiddlen" value="'+rows[r].reward_DESC+'"></td>';
						row1+='<td class="tbodytd" style="width: 20%;">'+rows[r].reward_QTY+'</td>';
						row1+='<td class="tbodytd" style="width: 20%;">'+rows[r].estimate_AMT+'</td>';
						row1+='<td class="tbodytd" style="width: 20%;">'+rows[r].resnDesc+'</td>';
						row1+='<td class="tbodytd" style="width: 20%;">'+rows[r].reward_DESC+'</td></tr>';
					}
					$("#phoneObody").append(row1);
					$('.phoneDate').datepicker({
						dateFormat : "yy/mm/dd",
						changeYear : true,
						changeMonth : true,
						showButtonPanel : true
					});
				}
			}
		});
		
		
		//清空新合約手機回饋暫存資訊Table內容
		$("#phoneTbody tr").remove();
		
		//取得原合約手機回饋暫存資訊
		$.ajax({			
			url : CONTEXT_URL + "/ls/LS001M3/searchLsRewardAddedBySeq",		
			data: {
				lsNo : lsNo,
				appSeq : appSeq,
				addState : "AFTER"
			},
			type : "POST",
			dataType : 'json',
			success : function(data) {
				if(data.success){
					var rows = data.rows;
					var row1 = '';
					for ( var r in rows) {						
						row1+='<tr id="tr'+r+'"><td class="tbodytd" style="width: 20%;"><input class="form-control" name="REWARD_ID" maxlength="100" type="text" style="width: 100%;" value="'+rows[r].reward_ID+'"></td>';
						row1+='<td class="tbodytd" style="width: 20%;"><input class="form-control" name="REWARD_QTY" onblur="checkIntOnBlur(this);" type="number" class="REWARD_QTY" maxlength="100" type="text" style="width: 100%; padding: 2px 0px 0px 10px" value="'+rows[r].reward_QTY+'"></td>';
						row1+='<td class="tbodytd" style="width: 20%;"><input class="form-control" name="ESTIMATE_AMT" onblur="checkIntOnBlur(this);" type="number" class="ESTIMATE_AMT" maxlength="100" type="text" style="width: 100%;padding: 2px 0px 0px 10px" value="'+rows[r].estimate_AMT+'"></td>';
						row1+='<td class="tbodytd" style="width: 20%;"></td>';
						row1+='<td class="tbodytd" style="width: 20%;"><input class="phoneDate form-control" readonly="readonly" name="GET_DATE" placeholder="點選輸入框" value="'+rows[r].getDate+'"></td></tr>';
					}
					$("#phoneTbody").append(row1);
					$('.phoneDate').datepicker({
						dateFormat : "yy/mm/dd",
						changeYear : true,
						changeMonth : true,
						showButtonPanel : true
					});
				}
			}
		});
		
		
	}
	
	//儲存站台編號異動
	function saveSiteIdCh(){
		if(checkSiteId())
		{
			var data = $("#siteIdChForm").serialize();
			//取得站台資訊
			$.ajax({			
				url : CONTEXT_URL + "/ls/LS001M3/saveSiteIdCh",		
				data: data,
				type : "POST",
				dataType : 'json',
				success : function(data) {
					if(data.success){
						alert(data.msg);
						parent.$.fancybox.close();
					}
				}
			}); 
		}
	}
	
	//取得資料異動-站台編號異動_站點資料
	function dataChSiteInfo(){
		//清空Table內容
		$("#dataCh_siteInfo tr").remove();
		
		initData = {
				lsNo : $("#lsNo").text(),
				domain : ""
			};
		
		$.ajax({			
			url : CONTEXT_URL + "/ls/LS001M3/searchTbLsLocation",		
			data: initData,
			type : "POST",
			dataType : 'json',
			success : function(data) {
				if(data.success){
					var siteValue = '';
					for(var i = 0 ;i<data.rows.length ; i++){
						siteValue += '<tr>';
						siteValue += '<td class="tbodytd" style="width: 25%;">'+data.rows[i].location_ID+'</td>';
						siteValue += '<td class="tbodytd" style="width: 25%;">'+data.rows[i].loc_NAME+'</td>';
						siteValue += '<td class="tbodytd" style="width: 25%;">'+data.rows[i].lsPayBeginDate+'</td>';
						siteValue += '<td class="tbodytd" style="width: 25%;">'+data.rows[i].addr+'</td>';
						siteValue += '</tr>';
					}
					$('#dataCh_siteInfo').append(siteValue);
				}
			}
		});
		
	}
	
	//當取得資料異動-站台編號異動，點選站點資料時
	$('#dataCh_siteInfo').on( 'click', 'tr', function () {
		$('#dataCh_siteInfo').find('tr').each(function(){
			$(this).removeClass('selected');
		}); 
		$(this).addClass('selected');
		querySiteEdit($.trim($(this).children().first().text()));
	});
	
	//查詢戰點站台資料
	function querySiteEdit(locId){
		var data = {
				lsNo : $("#lsNo").text(),
				locId : locId
			};
		
		$.ajax({			
			url : CONTEXT_URL + "/ls/LS001M3/searchTbLsSiteByLocId",		
			data: data,
			type : "POST",
			dataType : 'json',
			success : function(data) {
					addSiteEdit(data.row['dtoList'],data.row['siteMainList']);
			}
		});
	}
	
	//組成站台grid資料
	function addSiteEdit(data,siteMain){
		//清空Grid內容
		$("#dataCh_siteEditTbody tr").remove();
		var siteValue = '';
		for(var i =0; i < data.length; i++){
			siteValue += '<tr>';
			siteValue += '<td class="tbodytd" style="width: 25%;">'+data[i].site_ID+'</td>';
			siteValue +='<td class="tbodytd" style="width: 25%;"><select class="" id="changeSite" name="changeSite"><option value="Original">---請選擇---</option>';
			for(var j=0 ; j < siteMain.length ; j++){
				siteValue +='<option value="'+siteMain[j].site_ID+'">'+siteMain[j].site_ID+'</option>';
			}
			siteValue+='</select></td>';
			siteValue += '<td class="tbodytd" style="width: 25%;">'+data[i].alcRatioE+'</td>';
			siteValue += '<td class="tbodytd" style="width: 25%;">'+data[i].alcRatioR+'</td>';
			siteValue+='<td class="tbodytd" style="display: none"><input name="ls_NO" type="hiddlen" value="'+data[i].ls_NO+'"></td>';
			siteValue+='<td class="tbodytd" style="display: none"><input name="ls_VER" type="hiddlen" value="'+data[i].ls_VER+'"></td>';
			siteValue+='<td class="tbodytd" style="display: none"><input name="location_ID" type="hiddlen" value="'+data[i].location_ID+'"></td>';
			siteValue+='<td class="tbodytd" style="display: none"><input name="site_ID" type="hiddlen" value="'+data[i].site_ID+'"></td>';
			siteValue += '</tr>';
			
		}
		$('#dataCh_siteEditTbody').append(siteValue);
		LoadSelect2Script(
				'<s:url value="/resources/plugins/select2/select2.min.js" />',ls001M3DataChSelect2);
	}
	
	function ls001M3DataChSelect2() {
		$("select").select2();
	}
	
	function checkSiteId(){
		var flag=false;
		var modifyFlag=true;
		var chgsiteId="";
		$('#dataCh_siteEditTbody tr').each(function(){
			chgsiteId=$(this).find("select[name='changeSite']").val();
			var siteId=$(this).find("input[name='site_ID']").val();
			if(chgsiteId!='Original')
			{
				modifyFlag=false;
				$('#dataCh_siteEditTbody tr').each(function(){
					var siteIdN=$(this).find("input[name='site_ID']").val();
					if(siteIdN!=siteId)
					{
						var chgsiteIdN=$(this).find("select[name='changeSite']").val();
						if(chgsiteId==chgsiteIdN)
						{
							flag=true;
						}
					}
				});
			}
		});
		if(flag)
		{
			alert("更換站台編號:"+chgsiteId+"已被選擇，請選擇其他站台編號");
			return false;
		}
		if(modifyFlag)
		{
			alert("無站台編號被更換;請選擇需的更換站台");
			return false;
		}
		return true;
	}
	
	// 新增手機
	function addPhone(tbodyID) {
		var row1 = '<tr id="" ><td class="tbodytd" style="width: 20%;"><input class="form-control" type="text" name="REWARD_ID" style="width: 100%;" value=""></td>';
		row1 += '<td class="tbodytd" style="width: 20%;"><input class="form-control" onblur="checkIntOnBlur(this);" type="number" name="REWARD_QTY" style="width: 100%;padding: 2px 0px 0px 10px" type="text" value="0"></td>';
		row1 += '<td class="tbodytd" style="width: 20%;"><input class="form-control" onblur="checkIntOnBlur(this);" type="number" name="ESTIMATE_AMT" style="width: 100%;padding: 2px 0px 0px 10px" type="text" value="0"></td>';
		row1 += '<td class="tbodytd" style="width: 20%;">&nbsp;</td>';
		row1 += '<td class="tbodytd" style="width: 20%;"><input class="phoneDate form-control" id="" name="GET_DATE" readonly="readonly" placeholder="點選輸入框" type="text" style="width: 100%;"></td></tr>';

		$("#" + tbodyID).append(row1);

		$('.phoneDate').datepicker({
			dateFormat : "yy/mm/dd",
			changeYear : true,
			changeMonth : true,
			showButtonPanel : true
		});
	}
	 //控制畫面是否開啟
	 function initStates(appMode){
			if(appMode=='VIEW'){
				$("#rentDateChDiv select").prop("disabled",true);
				$("#rentDateChDiv input").prop("disabled",true);

				$("#phoneRewardForm select").prop("disabled",true);
				$("#phoneRewardForm input").prop("disabled",true);

				$("#siteIdChDiv select").prop("disabled",true);
				$("#siteIdChDiv input").prop("disabled",true);
				
				$("#selType").prop("disabled",true);
				$("#itemSEL").prop("disabled",true);
				
				$('#btn_1M3Save_Draft').prop("disabled",true);
				$('#btn_1M3Print').prop("disabled",true);
				$('#btn_1M3PrintRent').prop("disabled",true);
				$("#btn_1M3Save_Draft").prop("disabled", true);
			}
		}
</script>