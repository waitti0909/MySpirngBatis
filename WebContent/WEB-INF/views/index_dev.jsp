<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>NOMS System</title>	
<style type="text/css">
.titleTD {
	text-align: center;
}

.textTD {
	padding: 0.2em;
}
</style>
</head>
<body>
<div class="row">
	<div id="breadcrumb" class="col-md-12">
		<ol class="breadcrumb">
			<li class="HoverClass"><a href="<s:url value="/index/" />">首頁</a></li>
			<li class="HoverClass"><a href="#">電子公佈欄</a></li>
		</ol>
	</div>
</div>

			<div class="col-xs-16">
		<%-- Button import --%>
		<c:import url="/bulletboard/" />
		</div>
		<%-- Tab flyer --%>
		<div class="customize_tab">
			<div id="tab_wrapper">
				<div id="tab_navigation" >
					<ul>
						<li class="selected"><a href="#tabDiv1">產生座標PDF</a></li>
						<li><a href="#tabDiv2">Data Input Mask</a></li>
					</ul>
				</div>
				
				<div id="tab_container">
					<div id="tabDiv1" class="tab_content">
						<div class="col-lg-2">
							<div class="input-group">
							  <input type="text" id="templateName" name="templateName" class="form-control" placeholder="例：LSF10"/>
							  <span class="input-group-btn">
							    <button class="btn btn-primary" type="button" onclick="printComboPdf();"><i class="fa fa-file"></i></button>
							  </span>
							</div>
						</div>
						<a href="#" id="" class="btn" onclick="printFakePdf()">LSF10產生範例</a>
					</div>					
					<div id="tabDiv2" class="tab_content">
						<%-- Data mask plugin add by Charlie 20150203 --%>
						<input type="text" id="date" name="date">
						<input type="text" id="datetime" name="datetime">
						<input type="text" id="money" name="money" placeholder="$,$$$,$$$" value="999999">
						<input type="text" id="cellphone" name="cellphone" placeholder="09XX-XXXXXX" value="0987654321">
						<input type="text" id="ipAddress" name="ipAddress" placeholder="IP Address">
					</div>
				</div>
			</div>
		</div>

<%-- Toggle effect --%>
<button class="btn btn-primary btn-app-xs" type="button" id="toggleBtn" onclick="runEffect()">
	<span><i class="fa fa-chevron-up"></i></span>
</button>
<!-- <button class="btn btn-primary btn-app-xs" type="button" onclick="runEffect()" style="display:none"> -->
<%-- 	<span><i class="fa fa-chevron-down"></i></span> --%>
<!-- </button> -->
<div id="effect">
		<div class="col-xs-16">
			<%-- Button import --%>
			<c:import url="/excelUpDown/" />
		</div>		

<button id="" class="btn" onclick="openFileProcess()">File Process</button>
<button id="" class="btn" onclick="openSiteFileProcess()">Site File Process</button>
<input type="button" class="btn" onclick="startObjMessage('objDiv')" value="PopupTips" ID="Button1" NAME="Button1"/>

<div class="col-lg-5">
  <div class="input-group">
    <input type="text" id="address" class="form-control" placeholder="Click button to manage address" readonly="readonly" >
    <span class="input-group-btn">
      <button class="btn btn-primary" type="button" onclick="openAddressProcess(receviceAddressData)"><i class="fa fa-home"></i></button>
    </span>
  </div>
</div>

<div id="showFileUploadFrame"></div>
<div id="showSiteFileDownloadFrame"></div>
<div id="showAddressFrame"></div>

<form action="" id="inputAddressForm" name="" method="post">
<span id="abccc">
		<input type="hidden" name="qq_zip" id="qq_zip" value="437" />
		<input type="hidden" name="qq_city" id="qq_city" value="50000009" />
		<input type="hidden" name="qq_area" id="qq_area" value="50000132" />
		<input type="hidden" name="qq_village" id="qq_village" value="" />
		<input type="hidden" name="qq_road" id="qq_road" value="大發路" />
		<input type="hidden" name="qq_adjacent" id="qq_adjacent" value="32" />
		<input type="hidden" name="qq_lane" id="qq_lane" value="1" />
		<input type="hidden" name="qq_alley" id="qq_alley" value="56" />
		<input type="hidden" name="qq_subAlley" id="qq_subAlley" value="27" />
		<input type="hidden" name="qq_doorNo" id="qq_doorNo" value="247" />
		<input type="hidden" name="qq_doorNoEx" id="qq_doorNoEx" value="1" />
		<input type="hidden" name="qq_floor" id="qq_floor" value="2" />
		<input type="hidden" name="qq_floorEx" id="qq_floorEx" value="3" />
		<input type="hidden" name="qq_room" id="qq_room" value="A" />
		<input type="hidden" name="qq_remark" id="qq_remark" value="" />
		<input type="hidden" name="qq_disabledFields" id="qq_disabledFields" value="city,area" />
		<input type="hidden" name="qq_callBackFun" id="qq_callBackFun" value="receviceAddressData" />
	</span>
	<input type="hidden" name="abcdedf" id="adajoi" value="adijr" />
</form>

<!-- 料號查詢 test -->
<button id="material" type="button" onclick="selectMaterial();">查料號</button>
<div id="selectMaterialPage"></div>
<form id="siteTestForm">
             <input name="matNoMaterial" id="matNoMaterial" value="" /><!-- 料號 -->
             <input name="matNameMaterial" id="matNameMaterial" value="" /><!-- 品名 -->
</form>
<!-- 料號查詢 test end -->

<button id="selectUser"  onclick="selectUser();">Personnel</button>
<div id="showPersonnelPage"></div>

<form id="testForm">
	<input type="hidden" id="callBackFun" name="callBackFun" value="showPsnData" />	
<!-- 	<input type="submit" id="selectUser" onclick="selectUser();" value="Personnel"></input> -->
</form>
<button id="selectSite"  onclick="selectSite();">基站查詢</button>
<div id="selectSitePage"></div>
<form id="siteTestForm">
	<input type="hidden" name="siteCallBackFun" id="siteCallBackFun" value="receviceSiteData" />
	<input type="hidden" name="filter_DomainId" id="filter_DomainId" value="" />
	<!-- 單選不用傳 -->
	<input type="hidden" name="selectMode" id="selectMode" value="multi" />
</form>
<br>
<button id="selectSrId"  onclick="selectSrId();">SR ID</button>
<div id="showSrIdPage"></div>
<form id="testSrIdForm">
	<input type="hidden" id="srIdCallBackFun" name="srIdCallBackFun" value="showSrIdData" />
</form>
<br />
<button id="unuseBTSSiteId"  onclick="unuseBTSSiteId();">UnuseBTSSiteId</button>
<div id="showUnuseBTSSiteIdPage"></div>
<form id="testSiteIdPoolForm">
	<input type="hidden" id="unuseBTSSiteIdCallBackFun" name="unuseBTSSiteIdCallBackFun" value="showUnuseBTSSiteIdData" />
</form>
<br />
<button id="commonPurchaseOrder"  onclick="commonPurchaseOrder();">共用PO單</button>
<div id="showCommonPurchaseOrderPage"></div>
<form id="testCommonPurchaseOrderForm">
	<input type="hidden" id="commonPurchaseOrderCallBackFun" name="commonPurchaseOrderCallBackFun" value="showCommonPurchaseOrderData" />
</form>
<br />
<button id="companyQuery"  onclick="companyQuery();">companyQuery</button>
<div id="showCompanyQueryPage"></div>
<form id="testCompanyQueryForm">
	<input type="hidden" id="companyQueryCallBackFun" name="companyQueryCallBackFun" value="showCompanyQueryData" />
</form>
<br />
<p>============ workflow 流程申請  Start====================</p>
<button type="button" class="btn btn-label-left btn-success" onclick="workflowApply('SearchSiteApplyNSR','201411-001','尋點申請事由xxx')">
	尋點申請NSR
</button>
<button type="button" class="btn btn-label-left btn-success" onclick="workflowApply('SearchSiteApplySH','201411-001','尋點申請事由xxx')">
	尋點申請SH
</button>
<button type="button" class="btn btn-label-left btn-success" onclick="workflowApply('SearchSiteOutsourcingApply','201411-002','尋點委外派工申請事由xxx')">
	尋點委外派工申請
</button>
<button type="button" class="btn btn-label-left btn-success" onclick="workflowApply('SearchSiteOutsourcingAccept','201411-003','尋點委外派工驗收事由xxx')">
	尋點委外派工驗收
</button>
<button type="button" class="btn btn-label-left btn-success" onclick="workflowApply('SearchSiteCompletionNSR','201411-004','尋點完工NSR事由xxx')">
	尋點完工NSR
</button>
<button type="button" class="btn btn-label-left btn-success" onclick="workflowApply('SearchSiteCompletionSH','201411-005','尋點完工SH事由xxx')">
	尋點完工SH
</button>
<button type="button" class="btn btn-label-left btn-success" onclick="workflowApply('TransferApplyForLogistics','201411-001','調撥單申請(後勤)事由xxx')">
	調撥單申請(後勤部門)
</button>
<button type="button" class="btn btn-label-left btn-success" onclick="workflowApply('TransferApply','201411-001','調撥單申請事由xxx')">
	調撥單申請
</button>
<button type="button" class="btn btn-label-left btn-success" onclick="workflowApply('InventoryStatusChange','201411-001','庫存狀態異動事由xxx')">
	庫存狀態異動
</button>
<p>============ workflow 流程申請  End====================</p>

<br>
<button id="approvalBtn" onclick="toApproval();">簽核</button>

<!-- <br> -->
<!-- <button onclick="insertHIERARCHY();">塞HIERARCHY資料</button> -->
<br>
<br>
<button onclick="addTable();">動態增加Table</button>
<button onclick="delTable();">動態刪除Table</button>
<div id="testTableDIV"></div>

</div>

<script type="text/javascript">
	$(document).ready(function() {
		runEffect();
		comTable(1);
		$("#date").mask('0000/00/00', {placeholder: "____/__/__"});
		$("#datetime").mask('0000/00/00 00:00:00', {placeholder: "____/__/__ __:__:__"});
		$("#money").mask('#,##9,999',{reverse: true});
		$("#ipAddress").mask('099.099.099.099');
		$("#cellphone").mask('0000-000000');
	});

	//顯示公告欄資訊
	function showContents(b_ID) {
		$.fancybox.open({
			href : CONTEXT_URL + "/sysBulletinboard/contents/",
			type : 'ajax',
			width : $(window).width(),
			height : $(window).height(),
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
			title : "公告欄資訊",
			openEffect : 'elastic',
			closeEffect : 'fade',
			ajax : {
				data : {
					b_ID : b_ID
				},
				type : "POST",
				async : false
			}
		});
	}

	//顯示檔案處理頁
	function openFileProcess() {
		openFileDialogFrame("showFileUploadFrame", "BULLETIN", "test", "TYPE1"); // This is implement in common.js
	}
	
	//顯示基站檔案下載頁
	function openSiteFileProcess() {
		openSiteFileDownFrame("showSiteFileDownloadFrame", "201501120004", "L231015"); // This is implement in common.js
	}

	//顯示地址處理頁
	function openAddressProcess() {
		var addr = {
			"zip" : $("#qq_zip").val(),
			"city" : $("#qq_city").val(),
			"area" : $("#qq_area").val(),
			"village" : $("#qq_village").val(),
			"road" : $("#qq_road").val(),
			"adjacent" : $("#qq_adjacent").val(),
			"lane" : $("#qq_lane").val(),
			"alley" : $("#qq_alley").val(),
			"subAlley" : $("#qq_subAlley").val(),
			"doorNo" : $("#qq_doorNo").val(),
			"doorNoEx" : $("#qq_doorNoEx").val(),
			"floor" : $("#qq_floor").val(),
			"floorEx" : $("#qq_floorEx").val(),
			"room" : $("#qq_room").val(),
			"remark" : $("#qq_remark").val(),
			"disabledFields" : $("#qq_disabledFields").val(),
			"callBackFun" : $("#qq_callBackFun").val()
		};
		openAddressDialogFrame("showAddressFrame", addr); // This is implement in common.js
	}

	function receviceAddressData(addressObject) {
		// var obj = JSON.parse(addressObject);
		// do what you want to do
		$.ajax({
			url : CONTEXT_URL + "/common/address/combineAddress",
			dataType : "json",
			data : addressObject,
			contentType : 'application/json',
			type : "POST",
			async : false,
			success : function(data) {
				$("#address").val(data.row.fullAddress);
			}
		});
	}
	
	//料號選擇
	function selectMaterial() {
		openMaterialFrame("selectMaterialPage");
	}
	//使用者選擇
	function selectUser() {
		openUserDialogFrame("showPersonnelPage", $("#callBackFun").val());
	}

	function showPsnData(psnData) {
		$("input~br").remove();
		$("input[name = 'psn']").remove();
		var obj = JSON.parse(psnData);
		for (var i = 0; i < obj.employees.length; i++) {
			$(
					'<input type="text" name="psn" size="90" value="'+"no = "+obj.employees[i].no+" ,name ="+obj.employees[i].name+
	    	" ,email ="+obj.employees[i].email+" ,deptPosId ="+obj.employees[i].deptPosId+'"></input><br />')
					.appendTo('#testForm');
		}
	}
	//基站查詢
	function selectSite() {
		openSiteDialogFrame("selectSitePage", $("#siteCallBackFun").val(), $(
				"#filter_DomainId").val(), "multi","B20141053439");
		//  多筆請傳入 multi	
		//	openSiteDialogFrame("selectSitePage", $("#siteCallBackFun").val(),$("#filter_DomainId").val());

	}
	function receviceSiteData(siteObjects) {
		$("input[name = 'site']~br").remove();
		$("input[name = 'site']").remove();
		var obj = JSON.parse(siteObjects);
		for (var i = 0; i < obj.siteObjs.length; i++) {
			$(
					'<input type="text" name="site" size="90" value="'+"bts_SITE_ID = "+obj.siteObjs[i].bts_SITE_ID+",site="+obj.siteObjs[i].site_ID
		+" ,feqName ="+obj.siteObjs[i].feqName+" ,eqpName ="+obj.siteObjs[i].eqpName+" ,zip ="+obj.siteObjs[i].locZip
		+" ,location_ID ="+obj.siteObjs[i].location_ID+" ,locName ="+obj.siteObjs[i].locName+" ,maintArea ="+obj.siteObjs[i].maintArea
		+'"></input><br />')
					.appendTo('#siteTestForm');
		}
	}

	function toApproval() {
		var params = [ 'N', 'tasksId1', '201408060001', '尋站作業申請', 'taskName',
				'taskStartTime', 'userTaskType' ];
		$.fancybox.open({
			href : CONTEXT_URL + "/workflow/openTask",
			type : 'ajax',
			width : $(window).width(),
			height : $(window).height(),
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
			title : "簽核",
			openEffect : 'elastic',
			closeEffect : 'fade',
			ajax : {
				data : {
					'isAgent' : params[0],
					'taskId' : params[1],
					'applyNo' : params[2],
					'processType' : params[3],
					'taskName' : params[4],
					'taskStartTime' : params[5],
					'userTaskType' : params[6]
				},
				type : "POST",
				success : function(data) {

				},
				error : function(jqXHR, testStatus, errorThrown) {
					alert(jqXHR);
				},
				async : false
			},
			beforeClose : function() {

			},
			afterShow : function() {
			},
			afterClose : function() {
			}
		});
	}

	function fulltoHalf(v) {
		//全行轉半型
		result = "";
		for (var i = 0; i <= v.length; i++) {
			if (v.charCodeAt(i) == 12288) {
				result += " ";
			} else {
				if (v.charCodeAt(i) > 65280 && v.charCodeAt(i) < 65375) {
					result += String.fromCharCode(v.charCodeAt(i) - 65248);
				} else {
					result += String.fromCharCode(v.charCodeAt(i));
				}
			}
		}
		val.value = result;
		v = result;
		return v;
	}

	var toggleOpen = true;
	function runEffect() {
		var options = {};
		// run the effect
		$("#effect").toggle('blind', options, 200);
		if (toggleOpen) {
			$("#toggleBtn").html(
					'<span><i class="fa fa-chevron-down"></i></span>');
		} else {
			$("#toggleBtn").html(
					'<span><i class="fa fa-chevron-up"></i></span>');
		}
		toggleOpen = !toggleOpen;
	};

	//(塞HIERARCHY用)
	function insertHIERARCHY() {
		$.ajax({
			url : '<s:url value="/org/org200/insertHIERARCHY" />',
			type : "POST",
			beforeSend : function() {
			},
			success : function(data) {
				alert(data);
			},
			error : function() {
			}
		});
	}
	
	
	function comTable(tableNum){
		var row1='<table id="tb-'+tableNum+'" border=2 style="table-layout: fixed; word-break: break-all; word-wrap: break-word; width: 100%">';
		row1+='<tr><td class="titleTD">Cell</td><td class="titleTD">訊號源</td>';
		row1+='<td class="titleTD">P-Code</td>';
		row1+='<td COLSPAN=2 class="titleTD">天線型號</td>';
		row1+='<td class="titleTD">安裝高度</td>';
		row1+='<td class="titleTD">方向角</td>';
		row1+='<td class="titleTD">E-TILT</td>';
		row1+='<td class="titleTD">M-TILT</td>';
		row1+='<td class="titleTD">安裝方式</td></tr>';
		var row2='<tr><td ROWSPAN=5 class="titleTD"><input id="chk-'+tableNum+'" type="checkbox" name="chk-'+tableNum+'" value="'+tableNum+'" />&nbsp;<input id="cell-'+tableNum+'" name="cell-'+tableNum+'" size="2" type="text"></td>';
		row2+='<td class="textTD"><select id="signalSRC-'+tableNum+'" name="signalSRC-'+tableNum+'" class="form-control"><option value="">NodeB</option></select></td>';
		row2+='<td class="textTD"><input id="pCode-'+tableNum+'" name="pCode-'+tableNum+'" type="text" style="width: 100%;"></td>';
		row2+='<td COLSPAN=2 class="textTD"><select id="antenna-'+tableNum+'" name="antenna-'+tableNum+'" class="form-control"><option value="">UMWD-04517-XD</option></select></td>';
		row2+='<td class="textTD"><input id="installH-'+tableNum+'" name="installH-'+tableNum+'" type="text" style="width: 100%;"></td>';
		row2+='<td class="textTD"><input id="directionAngle-'+tableNum+'" name="directionAngle-'+tableNum+'" type="text" style="width: 100%;"></td>';
		row2+='<td class="textTD"><input id="eTilt-'+tableNum+'" name="eTilt-'+tableNum+'" type="text" style="width: 100%;"></td>';
		row2+='<td class="textTD"><input id="mTilt-'+tableNum+'" name="mTilt-'+tableNum+'" type="text" style="width: 100%;"></td>';
		row2+='<td class="textTD"><select id="installStyle-'+tableNum+'" name="installStyle-'+tableNum+'" class="form-control"><option value="">tower</option></select></td></tr>';
		var row3='<tr><td class="titleTD">樓高</td>';
		row3+='<td class="titleTD">鐵塔</td>';
		row3+='<td class="titleTD">屋突</td>';
		row3+='<td COLSPAN=4 class="titleTD">地址</td>';
		row3+='<td class="titleTD">經度</td>';
		row3+='<td class="titleTD">緯度</td></tr>';
		var row4='<tr><td class="textTD"><input id="floor-'+tableNum+'" name="floor-'+tableNum+'" type="text" style="width: 100%;"></td>';
		row4+='<td class="textTD"><input id="tower-'+tableNum+'" name="tower-'+tableNum+'" type="text" style="width: 100%;"></td>';
		row4+='<td class="textTD"><input id="roof-'+tableNum+'" name="roof-'+tableNum+'" type="text" style="width: 100%;"></td>';
		row4+='<td COLSPAN=4 class="textTD"><input id="arrd-'+tableNum+'" name="arrd-'+tableNum+'" type="text" style="width: 100%;"></td>';
		row4+='<td class="textTD"><input id="long-'+tableNum+'" name="long-'+tableNum+'" type="text" style="width: 100%;"></td>';
		row4+='<td class="textTD"><input id="lat-'+tableNum+'" name="lat-'+tableNum+'" type="text" style="width: 100%;"></td></tr>';
		var row5='<tr><td COLSPAN=2 class="titleTD">Feeder cable type</td>';
		row5+='<td COLSPAN=2 class="titleTD">Feeder cable 長度</td>';
		row5+='<td COLSPAN=2 class="titleTD">Jumper cable 長度</td>';
		row5+='<td COLSPAN=3 class="titleTD">覆蓋區域</td></tr>';
		var row6='<tr><td COLSPAN=2 class="textTD"><select id="feederType-'+tableNum+'" name="feederType-'+tableNum+'" class="form-control"><option value="">1 1/4"</option></select></td>';
		row6+='<td COLSPAN=2 class="textTD"><input id="feederL-'+tableNum+'" name="feederL-'+tableNum+'" type="text" style="width: 100%;"></td>';
		row6+='<td COLSPAN=2 class="textTD"><input id="jumper-'+tableNum+'" name="jumper-'+tableNum+'" type="text" style="width: 100%;"></td>';
		row6+='<td COLSPAN=3 class="textTD"><input id="coverageArea-'+tableNum+'" name="coverageArea-'+tableNum+'" type="text" style="width: 100%;"></td></tr></table>';
		$("#testTableDIV").append(row1 + row2 + row3 + row4 + row5 + row6);
	}
	
	var tableNum = 0;
	function addTable() {		
		var tableId = '';
		//取得最後table的ID
		$('#testTableDIV table').each(function() {
			tableId = $(this).attr('id');
		});
		if(tableId!=''){
			tableNum = tableId.substr(3); //取ID(tb-X)數字
		}		
		tableNum++;	
		comTable(tableNum);
	}

	function delTable() {
		//var s =  $("#testTableDIV :checkbox:checked").length;
		var str = "";
		$("#testTableDIV :checkbox:checked").each(function() {
			str="tb-"+$(this).val();
			//刪除指定ID的TABLE
			var oTab = document.getElementById(str);
			oTab.parentNode.removeChild(oTab);
		});
	}
	
	function selectSrId(){
		openSearchRingDialogFrame("showSrIdPage", $("#srIdCallBackFun").val());
	}
	function showSrIdData(srObjects){
		$("input[name = 'site']~br").remove();
		$("input[name = 'site']").remove();
		var obj = JSON.parse(srObjects);
		$('<input type="text" name="site" size="90" value="'+"sr_ID = "+obj.sr_ID
		+" ,sr_LON ="+obj.sr_LON+" ,sr_LAT ="+obj.sr_LAT+'"></input><br />')
					.appendTo('#testSrIdForm');
		
		
	}
	
	function unuseBTSSiteId(){
		var callBackFunName = $("#unuseBTSSiteIdCallBackFun").val();
		var targetFrameId = "showUnuseBTSSiteIdPage";
		$.ajax({
			url : CONTEXT_URL + "/st/st002/search/unuseBTSSiteId",
			type : 'POST',
			dataType : "html",
			data:{
				"callBackFun" : callBackFunName,
				"targetFrameId" : targetFrameId,
			},
			async : false,
			success : function(data) {
				initialDialogFrame(targetFrameId, "可用基站編號",data);
			}
		});
	}
	function showUnuseBTSSiteIdData(object){
		$("input[name = 'site']~br").remove();
		$("input[name = 'site']").remove();
		var obj = JSON.parse(object);
		$('<input type="text" name="site" size="90" value="'+"sr_ID = "+obj.bts_SITE_ID+'"></input><br />').appendTo('#testSiteIdPoolForm');
	}
	
	function commonPurchaseOrder(){
		var callBackFunName = $("#commonPurchaseOrderCallBackFun").val();
		var targetFrameId = "showCommonPurchaseOrderPage";
		openCommonPoFrame(targetFrameId, callBackFunName);
	}
	function showCommonPurchaseOrderData(object){
		$("input[name = 'po']~br").remove();
		$("input[name = 'po']").remove();
		var obj = JSON.parse(object);
	
		$('<input type="text" name="po" size="90" value="'+"po_ID = "+obj.po_ID+'"></input><br />').appendTo('#testCommonPurchaseOrderForm');
		$('<input type="text" name="po" size="90" value="'+"po_NO = "+obj.po_NO+'"></input><br />').appendTo('#testCommonPurchaseOrderForm');
		$('<input type="text" name="po" size="90" value="'+"po_NAME = "+obj.po_NAME+'"></input><br />').appendTo('#testCommonPurchaseOrderForm');
		$('<input type="text" name="po" size="90" value="'+"po_YEAR = "+obj.po_YEAR+'"></input><br />').appendTo('#testCommonPurchaseOrderForm');
		$('<input type="text" name="po" size="90" value="'+"po_DOMAIN = "+obj.po_DOMAIN+'"></input><br />').appendTo('#testCommonPurchaseOrderForm');
		$('<input type="text" name="po" size="90" value="'+"amount = "+obj.amount+'"></input><br />').appendTo('#testCommonPurchaseOrderForm');
	}
	function printItextPdf() {//
		$.ajax({
			url : CONTEXT_URL + "/printPdf",
			type : 'GET',
			data:{
				
			},
			async : false,
			success : function(data) {
				
			}
		});
	}
	function companyQuery(){
		var callBackFunName = $("#companyQueryCallBackFun").val();
		var targetFrameId = "showCompanyQueryPage";
		openCompanyQueryFrame(targetFrameId, callBackFunName);
	}
	function showCompanyQueryData(object){
		$("input[name = 'comQuery']~br").remove();
		$("input[name = 'comQuery']").remove();
		var obj = JSON.parse(object);
	
		$('<input type="text" name="comQuery" size="90" value="'+"co_VAT_NO = "+obj.co_VAT_NO+'"></input><br />').appendTo('#testCompanyQueryForm');
		$('<input type="text" name="comQuery" size="90" value="'+"co_ERP_NO = "+obj.co_ERP_NO+'"></input><br />').appendTo('#testCompanyQueryForm');
		$('<input type="text" name="comQuery" size="90" value="'+"co_UBN_NO = "+obj.co_UBN_NO+'"></input><br />').appendTo('#testCompanyQueryForm');
		$('<input type="text" name="comQuery" size="90" value="'+"co_NAME = "+obj.co_NAME+'"></input><br />').appendTo('#testCompanyQueryForm');
	}
	function printComboPdf() {
		var fileName = $("#templateName").val();
		if (fileName === '') {
			alert('請先輸入套表編號(FORM_ID)');
		} else {
			location.href = CONTEXT_URL + "/printPdf?appFormId=" + fileName; 
		}
	}
	function printFakePdf() {
		location.href = CONTEXT_URL + "/lsf10/printPdf?printPdfDoc=LSF10"; 
	}
</script>
</body>
</html>