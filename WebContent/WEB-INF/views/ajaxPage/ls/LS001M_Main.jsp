<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>


<div>
	<button class="btn btn-primary btn-label" type="button" id="save_draft">
		儲存草稿</button>
	<button class="btn btn-primary btn-label" type="button" id="">
		列印合約</button>
	<button class="btn btn-primary btn-label" type="button" id="">
		列印用印簽辦單</button>
	<button class="btn btn-primary btn-label" type="button" id="">
		列印手機簽收確認書</button>
	<button class="btn btn-primary btn-label" type="button" id="">
		列印委託管理使用收益暨授權簽約書</button>
</div>

<div id="mainTabs" style="margin-top: 20px">
	<ul>
		<li><a href="#mainTabs-1">租約</a></li>
		<li><a href="#mainTabs-2">出租人</a></li>
		<li><a href="#mainTabs-3">回饋</a></li>
	</ul>

	<div id="mainTabs-1">
		<jsp:include page="/WEB-INF/views/ajaxPage/ls/LS001M_Contract.jsp"></jsp:include>
	</div>
	<div id="mainTabs-2">
		<jsp:include page="/WEB-INF/views/ajaxPage/ls/LS001M_Lessor.jsp"></jsp:include>
	</div>
	<div id="mainTabs-3">
		<jsp:include page="/WEB-INF/views/ajaxPage/ls/LS001M_Reward.jsp"></jsp:include>
	</div>
</div>


<script type="text/javascript">
	$(document).ready(function() {
		$("#mainTabs").tabs();				
	});	
	
	$("#save_draft").on('click', function(){
		$("#mainTabs-1 :input").prop("disabled", false);
		if (validContract()) {
			return;
		} else {
			var lessorJson = getLessorJson();
			
			if (!checkPhoneNumTbody()) {
				return;
			}
			if (!checkMobileTbody()) {
				return;
			}
			$("#unfrmt").prop("disabled", false);
			var rewardJsonArr = getRewardJson();
			$.ajax({
				url : CONTEXT_URL + "/ls001M/saveDraft",
				type : 'POST',
				dataType : "json",
				data : $("#mainTabs-1 :input").serialize()
						+ "&lessorJson=" + lessorJson
						+ "&rewardJsonArr=" + rewardJsonArr
						+ "&btnType=" + btnType,
				async : false,
				success : function(data) {
					alert(data.msg);
					if(data.success){					
						parent.$.fancybox.close();
						parent.toLs001M("edit",data.row.ls_NO, data.row.ls_VER, data.row.app_SEQ);
					}		
				}
			});
		}
	});
	
	//合約tab檢核
	function validContract() {
		
		if ($("#contractDDept").val() == "") {
			alert("洽談人員部門為必填欄位");
			$("#mainTabs a[href='#mainTabs-1']").trigger('click');
			return true;
		}
		
		if ($("#contractPSN").val() == "") {
			alert("洽談人員為必填欄位");
			$("#mainTabs a[href='#mainTabs-1']").trigger('click');
			return true;
		}
		
		if ($("#formIdSelect").val() == "" && $("#unfrmt").prop("checked") != true) {
			alert("套表格式為必填欄位");
			$("#mainTabs a[href='#mainTabs-1']").trigger('click');
			return true;
		}
		
		//維運區域
		var opDomain = "";
		$("input[name='OP_DOMAIN']:checkbox").each(function() {
			if ($(this).is(":checked")) {
				opDomain = $(this).val();
			}
		});
		
		if (opDomain == "") {
			alert("維運區域為必選");
			$("#mainTabs a[href='#mainTabs-1']").trigger('click');
			return true;
		}
		//地點狀態：Booster及Repeater租約時必填欄位，定義租賃地點的狀態，為自有住宅或租賃。如為租賃，需說明租賃期間。
		var lsKind = "";
		var plcType = "";
		$("input[name='PLC_TYPE']:radio").each(function() {
			if ($(this).is(":checked")) {
				plcType = $(this).val();
			}
		});
		
		var lsKind = "";
		$("input[name='LS_KIND']:radio").each(function() {
			if ($(this).is(":checked")) {
				lsKind = $(this).val();
			}
		});
		
		if (plcType == "L"){
			if ($("#rentDateS").val() == "") {
				$("#mainTabs a[href='#mainTabs-1']").trigger('click');
				alert("地點狀態為『租賃』時，房屋租賃起日為必填");
				return true;
			}
			if ($("#rentDateE").val() == "") {
				$("#mainTabs a[href='#mainTabs-1']").trigger('click');
				alert("地點狀態為『租賃』時，房屋租賃訖日為必填");
				return true;
			}
		}
		
		if (plcType == "" && (lsKind == "BOOSTER" || lsKind == "REPEATER")) {
			alert("合約類型為租賃為﻿﻿『Repeater租約』或『Booster使用同意書』時，地點狀態為必填");
			$("#mainTabs a[href='#mainTabs-1']").trigger('click');
			return true;
		}
		
		//合約類型為租賃為﻿﻿『資源互換協議』，互換項目為必填
		if (lsKind == "RESCHG") {
			var exchItemUnChecked = true;
			$("input[name='EXCH_ITEM']").each(function () {
				if ($(this).prop("checked")) {
					exchItemUnChecked = false;
					return false;
				}
			});
			if (exchItemUnChecked) {
				alert("合約類型為租賃為﻿﻿『資源互換協議』，互換項目為必填");
				$("#mainTabs a[href='#mainTabs-1']").trigger('click');
				return true;
			}
			
		}
		
		if($("#conUndated").prop("checked")){
			if ($("#contractDateS").val() == "") {
				$("#mainTabs a[href='#mainTabs-1']").trigger('click');
				alert("合約起日為必填");
				return true;
			}
		} else {
			if ($("#contractDateS").val() == "") {
				$("#mainTabs a[href='#mainTabs-1']").trigger('click');
				alert("合約起日為必填");
				return true;
			}
			if ($("#contractDateE").val() == "") {
				$("#mainTabs a[href='#mainTabs-1']").trigger('click');
				alert("合約訖日為必填");
				return true;
			}
		}
		
		if (btnType == "continue" || btnType == "ReSign" || btnType == "change") {
			var lsSDate = new Date($("#contractDateS").val());
			var lsEDate = new Date($("#contractDateE").val());
			var oriLsSDate = new Date($("#oriLsDateS").val());
			var oriLsEDate = new Date($("#oriLsDateE").val());
			if (btnType == "change") {
				if (oriLsSDate >= lsSDate) {
					alert("合約起日必須大於原合約起日" + $("#oriLsDateS").val());
					return true;
				}
				if (lsSDate >= oriLsEDate) {
					alert("合約起日必須小於原合約訖日" + $("#oriLsDateE").val());
					return true;
				}
			} else if (btnType == "ReSign") {
				//nothing todo
			} else {
				if (oriLsEDate >= lsSDate) {
					alert("合約起日必須大於原合約訖日" + $("#oriLsDateE").val());
					return true;
				}
			}
		}
		
		$("#contractDateE").prop("disabled", false);
		
		var notValid = false;
		var penaltyDescChecked = false;
		var penaltyDescRadio = "";
		$(".penalty_DESC_RADIO").each(function(){
			if ($(this).prop("checked")) {
				penaltyDescChecked = true;
				penaltyDescRadio = $(this).val();
				return false;
			}
		});
		
		if(penaltyDescChecked) {
			//var reg = /^[0-9]+\.[0-9]*$/;
			if($.trim($("#penalty_DESC_TEXT").val()) == "") {
				$("#mainTabs a[href='#mainTabs-1']").trigger('click');
				alert($("#penalty_DESC_TEXT").prop("title") + '不得為空白');
				$("#penalty_DESC_TEXT").focus();
				notValid = true;
				return true;
			} else {
				$("#penalty_DESC").val($.trim($("#penalty_DESC_TEXT").val()) + ":" + penaltyDescRadio);
			}
			
		}
		
		//印鑑總類 
		var sealOtherChecked = false;
		$("input[name='SEALS']:checkbox").each(function() {
			if ($(this).is(":checked")) {
				if ($(this).val() == "S10") {
					sealOtherChecked = true;
				}
			}
		});
		//印鑑總類 :選取其他
		if (sealOtherChecked) {
			if ($.trim($("#seal_OTHERS").val()) == "") {
				alert("印鑑總類勾選其他時，印鑑其他說明必填");
				$("#mainTabs a[href='#mainTabs-1']").trigger('click');
				$("#seal_OTHERS").focus();
				return true;
			}
		} else {
			$("#seal_OTHERS").val("");
		}
		
		return false;
	}
	
	//取得出租人TAB資訊
	function getLessorJson() {
		var formDataArray = $(".formData").toArray();
		var array = [];
		var lessorJson = "";
		for (var i = 0; i < formDataArray.length; i++) {
			var formData = formDataArray[i];
			array.push(formData.value);
		}
		
		if(array.length > 0){   
			lessorJson = array.join(";");
		}
		
		return lessorJson;
	}
	
	//取得回饋TAB資訊
	function getRewardJson(){
		var rewardJsonArr = [];
		//門號
		var rowCount = $('#phoneNumFBTbody tr').length;
		if (rowCount > 0) {
			$("#phoneNumFBTbody tr").each(function(){
				var REWARD_ID = $(this).find("input[name='REWARD_ID']").val();
				var CUST_NAME = $(this).find("input[name='CUST_NAME']").val();
				var PHONE_NBR = $(this).find("input[name='PHONE_NBR']").val();
				var PRCING = $(this).find("input[name='PRCING']").val();
				var REWARD_RESN = $(this).find("select").find("option:selected").prop("value");
				var REWARD_DESC = $(this).find("input[name='REWARD_DESC']").val();
				rewardJsonArr.push({
					REWARD_TYPE : '1',
					REWARD_ID : REWARD_ID,
					PHONE_NBR : PHONE_NBR,
					CUST_NAME : CUST_NAME,
					PRCING : PRCING,
					REWARD_RESN : REWARD_RESN,
					REWARD_DESC : REWARD_DESC
				});
			});
		}
		
		//手機
		rowCount = $('#mobPhoneFBTbody tr').length;
		if (rowCount > 0) {
			$("#mobPhoneFBTbody tr").each(function(){
				var REWARD_ID = $(this).find("input[name='REWARD_ID']").val();
				var REWARD_QTY = $(this).find("input[name='REWARD_QTY']").val();
				var ESTIMATE_AMT = $(this).find("input[name='ESTIMATE_AMT']").val();
				var REWARD_RESN = $(this).find("select").find("option:selected").prop("value");
				var REWARD_DESC = $(this).find("input[name='REWARD_DESC']").val();
				rewardJsonArr.push({
					REWARD_TYPE : '2',
					REWARD_ID : REWARD_ID,
					REWARD_QTY : REWARD_QTY,
					ESTIMATE_AMT : ESTIMATE_AMT,
					REWARD_RESN : REWARD_RESN,
					REWARD_DESC : REWARD_DESC
				});
			});
		}
		
		return JSON.stringify(rewardJsonArr);
	}
</script>