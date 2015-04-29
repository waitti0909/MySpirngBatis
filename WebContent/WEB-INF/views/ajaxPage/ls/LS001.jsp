<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title></title>
<script	src="<s:url value="/resources/js/lsCommon.js" />"></script>
<script type="text/javascript">

	$(document).ready(function() {
		LoadSelect2Script('<s:url value="/resources/plugins/select2/select2.min.js" />', selectDefault);
		loadCollectUnit();
		
		$("#btn_newDeal").click(function(){
			toLs001M('new','','1');
		});
				
	});
	
	//查詢
	$('#btn_search').click(function() {
		if($("#opDomainSelect").val()==''){
			alert("請選擇區域");
			return false;
		}

		
		$("#grid").jqGrid('clearGridData');
		var data = {
				lsNo : $.trim($("#searchLsNo").val()),	//合約編號
				lsType : $("#lsTypeSelect").val() == null ? "" : $("#lsTypeSelect").val().join(","), //合約型態
				//btsSiteId : $.trim($("#search_bts_site_id").val()),	//站台編號 
				locId : $.trim($("#search_locId").val()),	//站點編號 
				appStatus : $.trim($("input:checkbox:checked[name='appStatus']").map(function() { return $(this).val(); }).get()),	//申請狀態
				lsStatus : $.trim($("input:checkbox:checked[name='lsStatus']").map(function() { return $(this).val(); }).get()),	//合約狀態
				opDomain : $("#opDomainSelect").val(),	//區域代碼
				expiredMonths : $("#expiredMonthsSelect").val(),	//距到期日
				dealUser : $.trim($("#dealUserSelect").val()),	//洽談人員
				appUser : $.trim($("#appUserSelect").val())		//建立人員
		}
		$("#grid").setGridParam({datatype:"json", postData:data, mtype: 'POST',page:1}).jqGrid().trigger("reloadGrid");
	});//Button end
	
	$("#grid").jqGrid({
		datatype: "local",
		pager: '#pager',
		url : CONTEXT_URL + "/ls/LS001/searchByCond",
		colNames:['版本號','合約序號','合約編號','合約型態代碼','合約型態','申請狀態代碼','申請狀態','合約狀態代碼','合約狀態','增補協議代碼','增補協議','項目','洽談人員','建立人員','建立日期'],
		colModel:[
			{name:'ls_VER',index:'ls_VER',width:90, sortable: false, hidden : true},
			{name:'app_SEQ',index:'app_SEQ',width:90, sortable: false, hidden : true},      
			{name:'ls_NO',index:'ls_NO',width:90, sortable: false},
			{name:'ls_TYPE',index:'ls_TYPE',width:60, sortable: false, hidden : true},
			{name:'ls_TYPE_NAME',index:'ls_TYPE_NAME',width:60, sortable: false},
			{name:'app_STATUS',index:'app_STATUS',width:60, sortable: false, hidden : true},
			{name:'app_STATUS_NAME',index:'app_STATUS_NAME',width:60, sortable: false},
			{name:'ls_STATUS',index:'ls_STATUS', width:60,sortable: false, hidden : true},
			{name:'ls_STATUS_NAME',index:'ls_STATUS_NAME', width:60,sortable: false},
			{name:'add_TYPE',index:'add_TYPE', width:80,sortable: false, hidden : true},
			{name:'add_TYPE_NAME',index:'add_TYPE_NAME', width:80,sortable: false},
			{name:'add_ITEM',index:'add_ITEM', width:100,sortable: false},
			{name:'deal_USER_NAME',index:'deal_USER_NAME', width:60,sortable: false},
			{name:'app_USER_NAME',index:'app_USER_NAME', width:60,sortable: false},
			{name:'cr_TIME',index:'cr_TIME', width:60,sortable: false, formatter: "date", formatoptions:{newformat: "Y/m/d"}}
		],	
		viewrecords: true,
		caption:"合約清單",
		rownumbers:true,
		multiselect: false,
		onSelectRow: function(id,status){ 							
				//showLeaseDiv();																	
		},
		gridComplete: function(){
			var ids = jQuery("#grid").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				var cl = ids[i];  // cl:jqgrid的行數，非資料真正的HEADER_ID
				$("#grid").jqGrid('getRowData', cl);
			}	
		},
		ondblClickRow: function(rowId) { //double clicking a jqgrid row
			var rowData = jQuery(this).getRowData(rowId);
			var whId = null;
            $.each(rowData, function (i, item) {
            	if (i == "ls_NO") {
            		lsNo = item;
            	}
            	if(i == "ls_VER"){
            		lsVer = item;
            	}
            	if(i == "app_SEQ"){
            		appSeq = item;
            	}
            	if(i == "ls_TYPE"){
            		lsType = item;
                }
            });
            toLs001M("view",lsNo,lsVer,appSeq,lsType,'');
		}
	});//end grid
	
	
	// 修改Button
	$("#btn_edit").click(update);
	
	// 執行修改
	function update() {
		// 取得被選取列
		var ids = $("#grid").jqGrid("getGridParam", "selrow");
		// 是否選取
		if (ids == undefined) {
			alert("請選擇一筆資料編輯");
			return false;
		} else {
			var data = $("#grid").jqGrid("getRowData", ids);
			if(data.ls_TYPE=="5")
			{
				if (data.app_STATUS == "0" || data.app_STATUS == "9"){
					lsAdd(data.app_SEQ,data.ls_NO,data.ls_VER,data.ls_TYPE,data.app_STATUS);
				}
				else {
					alert('此類型增補協議不可修改');
				}
			}
			else
			{
				//狀態為草稿 or 駁回才能修改 0:草稿，1:送審中，2:核可，9:駁回
				if (data.app_STATUS == "0" || data.app_STATUS == "9") {
					if(data.ls_TYPE == "4"){
						toLs001T(data,'edit');					
					}else{
						toLs001M("edit",data.ls_NO,data.ls_VER,data.app_SEQ,'');
					}
				} else {
					alert('此狀態合約不可修改');
				}
			}
		}
	}
	

	//一解一簽
	$("#btn_endStartDeal").click(endStartDeal);
	
	function endStartDeal() {
		// 取得被選取列
		var ids = $("#grid").jqGrid("getGridParam", "selrow");
		// 是否選取
		if (ids == undefined) {
			alert("請選擇一筆資料做一解一簽");
			return false;
		} else {
			var data = $("#grid").jqGrid("getRowData", ids);
			//LS_TYPE:{4:解約}, APP_STATUS:{2:核可}, LS_STATUS:{1:生效}
			if(data.ls_TYPE == "4" && data.app_STATUS == "2" && data.ls_STATUS == "9") {
				toLs001M("ReSign", data.ls_NO, data.ls_VER, data.app_SEQ,'');
			} else {
				alert('此合約不可一解一簽');
			}
		}
	}

	//續約
	$("#btn_continueDeal").click(continueDeal);
	function continueDeal() {
		// 取得被選取列
		var ids = $("#grid").jqGrid("getGridParam", "selrow");
		// 是否選取
		if (ids == undefined) {
			alert("請選擇一筆資料做續約");
			return false;
		} else {
			var data = $("#grid").jqGrid("getRowData", ids);
			//LS_TYPE:{4:解約}, APP_STATUS:{2:核可}, LS_STATUS:{1:生效}
			console.log("LS_TYPE: " + data.ls_TYPE + ", APP_STATUS: " + data.app_STATUS + ", LS_STATUS: " + data.ls_STATUS);
			if(data.ls_TYPE != "4" && data.app_STATUS == "2" && data.ls_STATUS == "1") {
				toLs001M("continue", data.ls_NO, data.ls_VER, data.app_SEQ,'');
			} else {
				alert('此合約不可續約');
			}
		}
	}


	

	
	//換約
	$("#btn_changeDeal").click(changeDeal);
	
	function changeDeal() {
		// 取得被選取列
		var ids = $("#grid").jqGrid("getGridParam", "selrow");
		// 是否選取
		if (ids == undefined) {
			alert("請選擇一筆資料換約");
			return false;
		} else {
			var data = $("#grid").jqGrid("getRowData", ids);
			//LS_TYPE:{4:解約}, APP_STATUS:{2:核可}, LS_STATUS:{1:生效}
			console.log("LS_TYPE: " + data.ls_TYPE + ", APP_STATUS: " + data.app_STATUS + ", LS_STATUS: " + data.ls_STATUS);
			if(data.ls_TYPE != "4" && data.app_STATUS == "2" && data.ls_STATUS == "1") {
				toLs001M("change", data.ls_NO, data.ls_VER, data.app_SEQ,'');
			} else {
				alert('此合約不可換約');
			}
		}
	}
	
	// 作廢Button
	$("#btn_disregard").click(disregard);
	
	// 執行作廢
	function disregard() {
		// 取得被選取列
		var ids = $("#grid").jqGrid("getGridParam", "selrow");
		// 是否選取
		if (ids == undefined) {
			alert("請選擇一筆資料作廢");
			return false;
		} else {
			var data = $("#grid").jqGrid("getRowData", ids);
			//狀態為草稿才能作廢0:草稿，1:送審中，2:核可，9:駁回
			if (data.app_STATUS == "0") {
				cancelLeaseApp(data.app_SEQ, data.ls_NO);
			} else {
				alert('此狀態合約不可作廢');
			}
		}
	}
	
	//合約作廢
	function cancelLeaseApp(appSeq, lsNo) {
		$.ajax({			
			url : CONTEXT_URL + "/ls/LS001/cancelLeaseApp",		
			data: {appSeq : appSeq,
				   lsNo : lsNo},
			type : "POST",
			dataType : 'json',
			success : function(data) {
				alert(data.msg);
				$("#btn_search").trigger("click");
				$("#grid").trigger("reloadGrid");
			}
		});
	}
	
	//增補協議
	$("#btn_addProtocol").click(function(){
		var ids = $("#grid").jqGrid("getGridParam", "selrow");
		// 是否選取
		if (ids == undefined) {
			alert("請選擇一筆資料作增補協議");
			return false;
		} else {
			
			var data = $("#grid").jqGrid("getRowData", ids);
			if(data.ls_TYPE=="5")
			{
				if (data.app_STATUS == "0" || data.app_STATUS == "1") {
					alert('此狀態不可執行增補協議');
				} else {
					lsAdd(data.app_SEQ,data.ls_NO,data.ls_VER,data.ls_TYPE,data.app_STATUS);
				}
			}else
			{
				if (data.ls_STATUS == "1") {
					lsAdd(data.app_SEQ,data.ls_NO,data.ls_VER,data.ls_TYPE,data.app_STATUS);
				}else
				{
					alert('只有合約狀態為生效，才可執行增補協議');
				}
			}			
		}
	});
	
	//開啟增補協議頁
	function lsAdd(appSeq,lsNo,lsVer,lsType,appStatus){
		$.fancybox.open({
		href : CONTEXT_URL+"/ls/LS001M3",
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
		title : "增補協議",
		openEffect : 'elastic',
		closeEffect : 'fade',
		ajax : {
			data : {
				appSeq : appSeq,
				lsNo:lsNo,
				lsVer:lsVer,
				lsType:lsType,
				appStatus:appStatus
			},
			type : "POST",
			async : false
	   	 },
    	 afterClose : function() {
			}
		});	
	}
	
	// 基站編號LOV
	$("#btn_siteSearch").click(function () {
		openSiteDialogFrame("selectSitePage", "receviceSiteDataSearch"); // call common.js
	});
				
	//站台查詢使用	
	function receviceSiteDataSearch(data) {
		var json = JSON.parse(data);
		var bts_SITE_ID = json.siteObjs[0].bts_SITE_ID;
		$("#search_locId").val(json.siteObjs[0].location_ID);
		$("#search_bts_site_id").val(bts_SITE_ID);
	}
	
	// 開啟下拉式選單搜尋文字功能
	function selectDefault() {
		$("#searchForm").find("select").select2();
	}
	
	//開啟ls001M頁
	function toLs001M(btnType,lsNo,lsVer,appSeq,lsType){
		var action = "";
		var url = CONTEXT_URL + "/ls/LS001M";
		if (btnType == "new") {
			appSeq = '';
			action = "新增";
		} else if(btnType == "edit") {
			action = "修改";
		} else if (btnType == "view") {
			action = "檢視";
		} else if (btnType == "change") {
			action = "換約";
		} else if (btnType == "continue") {
			action = "續約";
		}else if (btnType == "ReSign") {
			action = "一解一簽";
		}
		
		if(typeof(appSeq)=='undefined'){
			appSeq = '';
		}
		
		$.fancybox.open({
 			href : url,
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
 			title : action,
 			openEffect : 'elastic',
 			closeEffect : 'fade',
 			ajax : {
				data : {
					btnType : btnType,
					lsNo : lsNo,
					lsVer : lsVer,
					appSeq: appSeq,
					lsType: lsType
				},
				type : "POST",
				async : false
		    },
 	        afterClose : function() {
 			}
 		});	
	}

	//區域
	$("#opDomainSelect").change(function(){
		//重置洽談人員部門
		restSelect('dealUserDeptSelect');
		//重置洽談人員
		restSelect('dealUserSelect');
		//重置建立人員部門
		restSelect('appUserDeptSelect');
		//重置建立人員
		restSelect('appUserSelect');
		
		var opDomain = $("#opDomainSelect").find("option:selected").prop("value");
		
		var data = {
			opDomain : opDomain
		};
		if (opDomain != "" || opDomain != ''){
			getDeptByDomain(data)
		}				
	});
	
	//重置下拉式選單
	function restSelect(selectType) {
		$("#"+ selectType).html("");
		$("<option value=''>-- 請選擇 --</option>").appendTo("#"+ selectType);
		$("#"+ selectType).select2();
	}
	
	function getDeptByDomain(data) {
		
		$.ajax({			
			url : CONTEXT_URL + "/ls/LS001/getDeptByDomain",		
			data: data,
			type : "POST",
			dataType : 'json',
			success : function(data) {
				if(data.success){
					if(data.rows.length > 0){
						reloadDeptSelect (data, 'deal');
						reloadDeptSelect (data, 'app');
					}
				}
			}
		});
	}
	
	//取得部門下拉式選單
	function reloadDeptSelect (data, userType) {
		
		$("#"+ userType + "UserDeptSelect  option").remove();
		$("#"+ userType + "UserDeptSelect").append($("<option></option>").attr("value", "").text("--請選擇--"));
		$("#"+ userType + "UserSelect  option").remove();
		$("#"+ userType + "UserSelect").append($("<option></option>").attr("value", "").text("--請選擇--"));
		for(var i = 0; i < data.rows.length; i++){
			$("#"+ userType + "UserDeptSelect").append("<option value=" + data.rows[i].dept_ID + ">" + data.rows[i].dept_NAME + "</option>");
			$("#"+ userType + "UserDeptSelect").prop("disabled",false);
		}
	}
	
	//洽談人員部門下拉式選單變動
	$("#dealUserDeptSelect").change(function(){
		reloadUserSelect('dealUserDeptSelect' , 'dealUserSelect');
	});
	
	//建立人員部門下拉式選單變動
	$("#appUserDeptSelect").change(function(){
		reloadUserSelect('appUserDeptSelect' , 'appUserSelect');
	});
	
	//取得人員下拉式選單
	function reloadUserSelect (deptSelectType, userSelectType) {
		$("#" + userSelectType).html("");
		$("<option value=''>-- 請選擇 --</option>").appendTo("#" + userSelectType);
		$("#" + userSelectType).select2();
		
		var deptId = $("#" + deptSelectType).find("option:selected").prop("value");
		
		var data = {
			deptId : deptId
		};
		if (deptId != "" || deptId != ''){
			getUsertByDeptId(data, userSelectType);
		}		
	}
	
	//由部門取得人員
	function getUsertByDeptId(data,userSelectType) {
		$.ajax({			
			url : CONTEXT_URL + "/ls/LS001/getUsertByDeptId",		
			data: data,
			type : "POST",
			dataType : 'json',
			success : function(data) {
				if(data.success){
					if(data.rows.length > 0){
						$("#" + userSelectType  + " option").remove();
						$("#" + userSelectType).append($("<option></option>").attr("value", "").text("--請選擇--"));
						for(i=0; i < data.rows.length; i++){
							$("#" + userSelectType).append("<option value=" + data.rows[i].psn_NO + ">" + data.rows[i].chn_NM + "</option>");
							$("#" + userSelectType).prop("disabled",false);
						}							 
					}
				}
			}
		});
	}
	//解約
	$("#btn_termination").click(function() {
		// 取得被選取列
		var ids = $("#grid").jqGrid("getGridParam", "selrow");
		// 是否選取
		if (ids == undefined) {
			alert("請選擇一筆資料編輯");
			return false;
		} else {
			var dataGrid = $("#grid").jqGrid("getRowData", ids);
			//合約狀態為生效才能解約 0:未生效；1:生效；9:失效
			if (dataGrid.ls_STATUS == "1") {
			 	var vetting = {
						lsNo : dataGrid.ls_NO
					};
				 $.ajax({			
						url : CONTEXT_URL + "/ls/LS001T/vettingTermination",		
						data: vetting,
						type : "POST",
						dataType : 'json',
						success : function(data) {
							if(data.success){					
								alert(data.msg);
							}else{
								toLs001T(dataGrid,'add');
							}
						}
					}); 
			} else {
				alert('只有合約狀態為生效才可解約');
			} 
		}
	});
	
	//開啟ls001M頁
	function toLs001T(data,type){
		$.fancybox.open({
 			href : CONTEXT_URL+"/ls/LS001T",
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
 			title : "修改",
 			openEffect : 'elastic',
 			closeEffect : 'fade',
 			ajax : {
				data : {
					lsNo : data.ls_NO,
					lsVer : data.ls_VER,
					appSeq : data.app_SEQ,
					type :type
				},
				type : "POST",
				async : false
		    },
 	        afterClose : function() {
 			}
 		});	
	}
	
	//重置按鈕
	$("#btn_reset").click(function() {
		/*
		$("#mainItemSelect").select2("val", "");
		
		$("#subItemSelect").html("");
		$("<option value=''>-- 請選擇 --</option>").appendTo("#subItemSelect");
		$("#subItemSelect").select2();
		*/
		$("#searchForm").trigger('reset');
		$("#search_locId").val('');
		$("#grid").jqGrid().clearGridData();
	});	
	
	$(window).resize(function() {
		$(window).unbind("onresize");
		$("#grid,.grid").setGridWidth($("#jQgrid").width() - 10);
		$(window).bind("onresize", this);
	}).trigger('resize');
</script>
</head>
<body>
	<div class="row">
		<div id="breadcrumb" class="col-md-12">
			<ol class="breadcrumb">
				<!-- button -->
				<c:import url="/auth/role/menu/func/${currentMenuId}" />
				<!-- button end-->
			</ol>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-12 col-sm-12">
			<div class="box ui-draggable ui-droppable">
				<div class="box-header">
					<div class="box-name">
						<i class="fa fa-search"></i> <span>合約維護 合約查詢</span>
					</div>
					<div class="box-icons">
						<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
						</a> <a class="expand-link"> <i class="fa fa-expand"></i>
						</a>
					</div>
					<div class="no-move"></div>
				</div>
				<div class="box-content">
						<form role="form"
							class="form-horizontal bootstrap-validator-form"
							novalidate="novalidate" id="searchForm">
							<table style="width: 100%">
								<tr>
									<td nowrap="nowrap" width="10%"><label
										class="col-sm-12 control-label"><span class="s">*</span>區域 :</label></td>
									<td width="40%">
										<div class="col-sm-8">
											<select id="opDomainSelect" class="populate placeholder" name="opDomainSelect">
											<option value=''>-- 請選擇 --</option>
												<c:forEach items="${domainList}" var="domain">
													<option value="${domain.ID}">${domain.NAME}</option>
												</c:forEach>
											</select>
										</div>
									</td>

									<td nowrap="nowrap" width="10%"><label
										class="col-sm-12 control-label">站台編號 :</label></td>
									<td width="40%">
										<div class="col-sm-10">
											<div
												style="width: 80%; height: auto; float: left; display: inline">
												<input id="search_bts_site_id" name="search_bts_site_id" type="text" readonly="readonly" class="form-control">
												<input id="search_locId" name="search_locId" type="hidden" class="form-control">
											</div>
											<div style="width: 16%; height: auto; float: left; display: inline;padding-left:5px">
												<button class="btn btn-primary btn-label-left"
													type="button" id="btn_siteSearch">站台查詢</button>
											<div id="selectSitePage"></div>
											<input type="hidden" name="siteCallBackFun" id="siteCallBackFun" value="receviceSiteDataSearch" />
											<input type="hidden" name="filter_DomainId" id="filter_DomainId" value="" />
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td nowrap="nowrap" width="10%"><label
										class="col-sm-12 control-label">合約編號 :</label></td>
									<td width="40%">
										<div class="col-sm-10" style="padding-top: 6px;">
											<div
												style="width: 75%; height: auto; float: left; display: inline">
												<input id="searchLsNo" class="form-control" type="text" name="searchLsNo"
													value="">
											</div>
											<div
												style="width: 25%; height: auto; float: left; display: inline; margin-top: 5px">
												(關鍵字)</div>
										</div>
									</td>
									<td nowrap="nowrap" width="10%"><label
										class="col-sm-12 control-label">合約型態 :</label></td>
									<td width="40%">
										<div class="col-sm-8">
											<select id="lsTypeSelect" multiple="multiple" class="populate placeholder" name="lsType">
												<option value="0">新約</option>
												<option value="1">續約</option>
												<option value="2">換約</option>
												<option value="3">一解一簽</option>
												<option value="4">解約</option>
												<option value="5">增補協議</option>
											</select>
										</div>
									</td>
								</tr>
								<tr>
									<td nowrap="nowrap" width="10%"><label
										class="col-sm-12 control-label">申請狀態 :</label></td>
									<td width="40%" style="padding-left: 12px;">
										<div style="padding-top: 5px;">
											<div style="height: auto; float: left; display: inline">
												<input id="" name="appStatus" type="checkbox" value="0">&nbsp;&nbsp;草稿&nbsp;&nbsp;
												<input id="" name="appStatus" type="checkbox" value="1">&nbsp;&nbsp;送審中&nbsp;&nbsp;
												<input id="" name="appStatus" type="checkbox" value="2">&nbsp;&nbsp;核可&nbsp;&nbsp;
												<input id="" name="appStatus" type="checkbox" value="9">&nbsp;&nbsp;駁回&nbsp;&nbsp;
											</div>
										</div>
									</td>
									<td nowrap="nowrap" width="10%"><label
										class="col-sm-12 control-label">合約狀態 :</label></td>
									<td width="40%" style="padding-left: 12px;">
										<div style="padding-top: 5px;">
											<div style="height: auto; float: left; display: inline">
												<input id="" name="lsStatus" type="checkbox" value="0">&nbsp;&nbsp;未生效&nbsp;&nbsp;
												<input id="" name="lsStatus" type="checkbox" value="1">&nbsp;&nbsp;生效&nbsp;&nbsp;
												<input id="" name="lsStatus" type="checkbox" value="9">&nbsp;&nbsp;失效&nbsp;&nbsp;
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td nowrap="nowrap" width="10%"><label
										class="col-sm-12 control-label">距到期日 :</label></td>
									<td width="40%" style="padding-top: 5px;">
										<div class="col-sm-8">
											<select id="expiredMonthsSelect" class="populate placeholder" name="">
												<option value="" selected>--請選擇--</option>
												<option value="3">3月</option>
												<option value="6">6月</option>
											</select>
										</div>
									</td>
								</tr>
								<tr>
									<td nowrap="nowrap" width="10%"><label
										class="col-sm-12 control-label">洽談人員 :</label></td>
									<td width="40%" style="padding-top: 5px;">
										<div class="col-sm-12">
											<div style="width: 60%; height: auto; float: left; display: inline">
												<select id="dealUserDeptSelect" class="populate placeholder" name="dealUserDeptSelect">
												    <option value="">--請選擇--</option>
												</select>
											</div>
											<div style="width: 40%; height: auto; float: left; display: inline;padding-left:5px">
											    <select id="dealUserSelect" class="populate placeholder" name="">
											        <option value="">--請選擇--</option>
												</select>
											</div>
										</div>
									</td>
									<td nowrap="nowrap" width="10%"><label
										class="col-sm-12 control-label">建立人員 :</label></td>
									<td width="40%" style="padding-top: 5px;">
										<div class="col-sm-12">
											<div style="width: 60%; height: auto; float: left; display: inline">
												<select id="appUserDeptSelect" class="populate placeholder" name="appUserDeptSelect">
												    <option value="">--請選擇--</option>
												</select>
											</div>
											<div style="width: 40%; height: auto; float: left; display: inline;padding-left:5px">
											    <select id="appUserSelect" class="populate placeholder" name="appUserSelect">
											        <option value="">--請選擇--</option>
												</select>
											</div>
										</div>
									</td>
								</tr>
								
								
							</table>
						</form>
					</div>
			</div>
		</div>
		<div id="ajaxSearchResult"  class="col-xs-12">
<!-- 		固定id for window.resize start -->
			<div id="jQgrid">
				<table id="grid"></table>
				<div id="pager"></div>
			</div>
<!-- 	 	固定id for window.resize end -->
		</div>
	</div>
</body>
</html>