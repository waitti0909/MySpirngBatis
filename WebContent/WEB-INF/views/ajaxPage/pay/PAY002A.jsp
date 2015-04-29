<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>用電/預付輸入作業</title>
<script src="<s:url value="/resources/plugins/tinymce/tinymce.min.js" />"></script>
<script src="<s:url value="/resources/plugins/tinymce/jquery.tinymce.min.js" />"></script>
<script	src="<s:url value="/resources/js/validate.js" />"></script>
<script type="text/javascript">
	// 全域變數控制刪除、儲存功能
	var isInput = false;
	var isFile = false;
	// 初始載入
	$(document).ready(function() {
    	$("#useElectricityRatio").val(1);// 用電比值待確認,暫時設定為1
		// 預設下拉選單樣式
		$("#contractNoSelectAdd").select2();
		$("#electricityMeterNbrSelectAdd").select2();
		$("#electricityTypeSelectAdd").select2();
		addMountButEvent();
		// 預設disabled值
		$("#contractNameAdd").prop("disabled", "disabled");
		$("#electricityTypeSelectAdd").prop("disabled", "disabled");
		$("#lsSDateAdd").prop("disabled", "disabled");
		$("#usedDegreeDayAdd").prop("disabled", "disabled");
		$("#lastUsedDegreeAdd").prop("disabled", "disabled");
		$("#ratioAdd").prop("disabled", "disabled");
		$("#deleteAddBtn").prop("disabled", "disabled");// 刪除鈕
		$("#saveAddBtn").prop("disabled", "disabled");// 儲存鈕
		// 本次請款起迄日曆
		$('#paymentReqBeginDateAdd').datepicker();
		$('#paymentReqEndDateAdd').datepicker();
		// 一開始預設只顯示用電輸入的Grid(addGrid),匯檔的Grid(addFileGrid)先不顯示
		$("#addFileGridDiv").hide();
		
		// 增加用電輸入Grid
		$("#addGrid").jqGrid({
			datatype : "local",
			pager : '#addpager',
			autowidth:'true',
			colNames : ['租約編號', '電錶號碼', '租約名稱', '用電/預付方式','起帳日','本次請款起始日','本次請款結束日','平均用電數/日','上期度數','本次度數','本次用電度數','超出/減少比率','用電補充說明','請款金額','自動扣款','無站台對應','','',''],
			colModel : [
				{name : 'contractNoAdd',index : 'contractNoAdd' , align : 'center',sortable : false},
				{name : 'electricityMeterNbrAdd',index : 'electricityMeterNbrAdd' , align : 'center',sortable : false},
				{name : 'contractNameAdd',index : 'contractNameAdd' , align : 'center',sortable : false},
				{name : 'electricityTypeDscrAdd',index : 'electricityTypeDscrAdd' , align : 'center',sortable : false},// 中文
				{name : 'lsSDateAdd',index : 'lsSDateAdd' , align : 'center',sortable : false,
			    	formatter: function(cellvalue, options, rowObject){
			   			if (cellvalue != null && cellvalue != "") {
				   			var stDate = new Date(cellvalue);
							return stDate.getFullYear() + "/" + addZero((stDate.getMonth() + 1)) + "/" + addZero(stDate.getDate()) ;
			   			}else {
			   				return "";
			   			}
		   		}},
				{name : 'paymentReqBeginDateAdd',index : 'paymentReqBeginDateAdd' , align : 'center',width:210,sortable : false,
			    	formatter: function(cellvalue, options, rowObject){
			   			if (cellvalue != null && cellvalue != "") {
				   			var stDate = new Date(cellvalue);
							return stDate.getFullYear() + "/" + addZero((stDate.getMonth() + 1)) + "/" + addZero(stDate.getDate()) ;
			   			}else {
			   				return "";
			   			}
		   		}},
				{name : 'paymentReqEndDateAdd',index : 'paymentReqEndDateAdd' , align : 'center',width:210,sortable : false,
			    	formatter: function(cellvalue, options, rowObject){
			   			if (cellvalue != null && cellvalue != "") {
				   			var stDate = new Date(cellvalue);
							return stDate.getFullYear() + "/" + addZero((stDate.getMonth() + 1)) + "/" + addZero(stDate.getDate()) ;
			   			}else {
			   				return "";
			   			}
		   		}},
				{name : 'usedDegreeDayAdd',index : 'usedDegreeDayAdd' , align : 'center',sortable : false},
		   		{name : 'lastUsedDegreeAdd',index : 'lastUsedDegreeAdd', align : 'center',sortable : false},
		   		{name : 'totalUsedDegreeAdd',index : 'totalUsedDegreeAdd', align : 'center',sortable : false},
		   		{name : 'usedDegreeAdd',index : 'usedDegreeAdd', align : 'center',width:210,sortable : false},
		   		{name : 'ratioAdd',index : 'ratioAdd', align : 'center',width:210,sortable : false},
		   		{name : 'memoAdd',index : 'memoAdd', align : 'center',width:210,sortable : false},
		   		{name : 'paymentReqAmtAdd',index : 'paymentReqAmtAdd' , align : 'center',sortable : false},
		   		{name : 'ifAutoDeductDscrAdd',index : 'ifAutoDeductDscrAdd' , align : 'center',sortable : false},// 中文
		   		{name : 'ifNoSiteMappingDscrAdd',index : 'ifNoSiteMappingDscrAdd', align : 'center',sortable : false},// 中文
		   		{name : 'electricityTypeAdd',index : 'electricityTypeAdd',sortable : false,hidden:true},// 用電方式代碼
		   		{name : 'ifAutoDeductAdd',index : 'ifAutoDeductAdd',sortable : false,hidden:true},// 自動扣款代碼
		   		{name : 'ifNoSiteMappingAdd',index : 'ifNoSiteMappingAdd',sortable : false,hidden:true}],// 無站台對應代碼
			multiselect : true,
			caption : "用電/預付輸入清單",
			rownumbers : true,
			onSelectRow : function() {},
			gridComplete : function() {},
			ondblClickRow: function() {}
		});	
		$(window).resize(function() {
			$(window).unbind("onresize");
			$("#addGrid,.addGrid").setGridWidth($("#jQgrid").width() + 160);
			$(window).bind("onresize", this);
		}).trigger('resize');
		
		// 檔案匯入Grid
		$("#addFileGrid").jqGrid({
			datatype : "local",
			pager : '#addfpager',
			rowNum:10,
			//rowList : [10,20,30,50], 
			autowidth:'true',
			colNames : ['租約編號', '電錶號碼', '租約名稱', '用電/預付方式','起帳日','本次請款起始日','本次請款結束日','平均用電數/日','上期度數','本次度數','本次用電度數','超出/減少比率','用電補充說明','請款金額','自動扣款','無站台對應','','',''],
			colModel : [
				{name : 'contractNoAdd',index : 'contractNoAdd' , align : 'center',sortable : false},
				{name : 'electricityMeterNbrAdd',index : 'electricityMeterNbrAdd' , align : 'center',sortable : false,width:200},
				{name : 'contractNameAdd',index : 'contractNameAdd' , align : 'center',sortable : false},
				{name : 'electricityTypeDscrAdd',index : 'electricityTypeDscrAdd' , align : 'center',sortable : false,width:200},// 中文
				{name : 'lsSDateAdd',index : 'lsSDateAdd' , align : 'center',sortable : false,
			    	formatter: function(cellvalue, options, rowObject){
			   			if (cellvalue != null && cellvalue != "") {
				   			var stDate = new Date(cellvalue);
							return stDate.getFullYear() + "/" + addZero((stDate.getMonth() + 1)) + "/" + addZero(stDate.getDate()) ;
			   			}else {
			   				return "";
			   			}
		   		}},
				{name : 'paymentReqBeginDateAdd',index : 'paymentReqBeginDateAdd' , align : 'center',width:210,sortable : false,
			    	formatter: function(cellvalue, options, rowObject){
			   			if (cellvalue != null && cellvalue != "") {
				   			var stDate = new Date(cellvalue);
							return stDate.getFullYear() + "/" + addZero((stDate.getMonth() + 1)) + "/" + addZero(stDate.getDate()) ;
			   			}else {
			   				return "";
			   			}
		   		}},
				{name : 'paymentReqEndDateAdd',index : 'paymentReqEndDateAdd' , align : 'center',width:210,sortable : false,
			    	formatter: function(cellvalue, options, rowObject){
			   			if (cellvalue != null && cellvalue != "") {
				   			var stDate = new Date(cellvalue);
							return stDate.getFullYear() + "/" + addZero((stDate.getMonth() + 1)) + "/" + addZero(stDate.getDate()) ;
			   			}else {
			   				return "";
			   			}
		   		}},
				{name : 'usedDegreeDayAdd',index : 'usedDegreeDayAdd' , align : 'center',sortable : false},
		   		{name : 'lastUsedDegreeAdd',index : 'lastUsedDegreeAdd', align : 'center',sortable : false},
		   		{name : 'totalUsedDegreeAdd',index : 'totalUsedDegreeAdd', align : 'center',sortable : false, editable: true, edittype:"text",editoptions:{size:"13",maxlength:"13"}},
		   		{name : 'usedDegreeAdd',index : 'usedDegreeAdd', align : 'center',width:210,sortable : false, editable: true, edittype:"text",editoptions:{size:"13",maxlength:"13"}},
		   		{name : 'ratioAdd',index : 'ratioAdd', align : 'center',width:210,sortable : false},
		   		{name : 'memoAdd',index : 'memoAdd', align : 'center',width:210,sortable : false, editable: true, edittype:"text",editoptions:{size:"13",maxlength:"13"}},
		   		{name : 'paymentReqAmtAdd',index : 'paymentReqAmtAdd' , align : 'center',sortable : false},
		   		{name : 'ifAutoDeductDscrAdd',index : 'ifAutoDeductDscrAdd' , align : 'center',sortable : false, editable: true,edittype:"select"},// 中文
		   		{name : 'ifNoSiteMappingDscrAdd',index : 'ifNoSiteMappingDscrAdd', align : 'center',sortable : false, editable: true,edittype:"select",width:250},// 中文
		   		{name : 'electricityTypeAdd',index : 'electricityTypeAdd',sortable : false,hidden:true},// 用電方式代碼
		   		{name : 'insertYrmnAdd',index : 'insertYrmnAdd',sortable : false,hidden:true},// 匯入檔案年月
		   		{name : 'seqNbrAdd',index : 'seqNbrAdd',sortable : false,hidden:true}],// 匯入檔案序號
		   	
				
			multiselect : true,
			caption : "用電/預付輸入清單",
			rownumbers : true,
			onSelectRow : function() {},
			gridComplete : function() {
				$('#addFileGrid').jqGrid('setColProp', 'ifAutoDeductDscrAdd', { editoptions: { value: "Y:是;N:否"}});
				$('#addFileGrid').jqGrid('setColProp', 'ifNoSiteMappingDscrAdd', { editoptions: { value: "Y:無站台對應;N:有站台對應"}});
				var count = 1;
			    $("#addFileGrid tr").each(function () {
		        	var tr = $(this);
		        	if (count > 1) {
		        		$(tr).attr("id","tr"+count);
		        	}
		        	count++;
		        });
			    
			    $("#addFileGrid tr").each(function () {
			    	var tr = $(this);
			    	$("#addFileGrid").editRow($(tr).attr("id"), true);
			    });
			    //$("#addFileGrid").editRow($(tr).attr("id"), true);
			},
			ondblClickRow: function() {}
		});	
		$(window).resize(function() {
			$(window).unbind("onresize");
			$("#addFileGrid,.addFileGrid").setGridWidth($("#jQgrid").width() + 160);
			$(window).bind("onresize", this);
		}).trigger('resize');
		
		
		/* //換顯示筆數事件 重新打開編輯欄位
		$("#addfpager_center").find("select").each(function(){
			var select = $(this);
			$(select).change(function(){
				$('#addFileGrid').jqGrid('setColProp', 'ifAutoDeductDscrAdd', { editoptions: { value: "Y:是;N:否"}});
				$('#addFileGrid').jqGrid('setColProp', 'ifNoSiteMappingDscrAdd', { editoptions: { value: "Y:無站台對應;N:有站台對應"}});
			    $("#addFileGrid tr").each(function () {
		        	var tr = $(this);
		        	$("#addFileGrid").editRow($(tr).attr("id"), true);
		        });
			});
		});
		
		//上下頁事件 重新打開編輯欄位
		$("#first_addfpager,#prev_addfpager,#next_addfpager,#last_addfpager").click(function() {
			$('#addFileGrid').jqGrid('setColProp', 'ifAutoDeductDscrAdd', { editoptions: { value: "Y:是;N:否"}});
			$('#addFileGrid').jqGrid('setColProp', 'ifNoSiteMappingDscrAdd', { editoptions: { value: "Y:無站台對應;N:有站台對應"}});
		    $("#addFileGrid tr").each(function () {
	        	var tr = $(this);
	        	$("#addFileGrid").editRow($(tr).attr("id"), true);
	        });
		}); */
		
	});
	// 全域變數
	var files, fileNames; // user to check duplicate file
	
	// 掛載表格Event
	function addMountButEvent() {
		// 請電電子檔匯入
		$('#importFileBtn').click(function() {
			// 清空輸入欄位內容
			$("#bBoardAdd").trigger('reset');
			// 清空用電輸入資料
			$("#addGrid").jqGrid().clearGridData();
			// 關閉增加用電輸入鈕、輸入欄位及取消鈕功能
			$("#contractNoSelectAdd").select2("val","");
			$("#electricityMeterNbrSelectAdd").select2("val","");
			$("#electricityTypeSelectAdd").select2("val","");
			$("#inputAddBtn").prop("disabled", "disabled");// 增加用電輸入鈕
			$("#resetBtn").prop("disabled", "disabled");// 取消鈕
			$("#contractNoSelectAdd").prop("disabled", "disabled");
			$("#electricityMeterNbrSelectAdd").prop("disabled", "disabled");
			$("#electricityTypeSelectAdd").prop("disabled", "disabled");
			$("#paymentReqBeginDateAdd").prop("disabled", "disabled");
			$("#paymentReqEndDateAdd").prop("disabled", "disabled");
			$("#totalUsedDegreeAdd").prop("disabled", "disabled");
			$("#usedDegreeAdd").prop("disabled", "disabled");
			$("#memoAdd").prop("disabled", "disabled");
			$("#paymentReqAmtAdd").prop("disabled", "disabled");
			$("#ifAutoDeductSelectAdd").prop("disabled", "disabled");
			$("#ifNoSiteMappingSelectAdd").prop("disabled", "disabled");
			// file_A用來協助讀取檔名用,於頁面上不會顯示
			if ($.trim($("#file_A").val()) == "") {
				$("#file_A").click();
			}
		});
		// 匯入檔案
		$("#file_A").change(function () {
		
			if ($.trim($("#file_A").val()) != "") {
				
				prepareUpload();
				importFileData();
				$("#file_A").val("");
				$("#deleteAddBtn").prop("disabled", false);// 刪除鈕
				$("#saveAddBtn").prop("disabled", false);// 儲存鈕
				isFile = true;
				
			}
		});

		//Grab the files and set them to our variable
		function prepareUpload() {
			files = event.target.files;
		}

		// 增加用電輸入
		$('#inputAddBtn').click(function() {
			if (inputAddCheck()) {
				$("#importFileBtn").prop("disabled", "disabled");
				addGridRow();
				$("#deleteAddBtn").prop("disabled", false);// 刪除鈕
				$("#saveAddBtn").prop("disabled", false);// 儲存鈕
				isInput = true;
			}
		});
		
		// 刪除用電輸入
		$('#deleteAddBtn').click(function() {
			if (isInput){
				delAddGridRow();
			}
			if (isFile){
				delAddFileGridRow();
			}
		});
		
		// 儲存
		$('#saveAddBtn').click(function() {
			isAdd = true;// 控制頁面的關閉視窗提醒訊息
			if (isInput){
				saveAddData();
			}
			if (isFile){
				if (addFileGridCheck()){
					saveFileData();
				}
			}
		});

		// 取消
		$('#resetBtn').click(function() {
			$("#contractNoSelectAdd").select2("val","");
			$("#electricityTypeSelectAdd").select2("val","");
			$("#electricityMeterNbrSelectAdd").html("");
			$("<option value=''>--請選擇--</option>").appendTo("#electricityMeterNbrSelectAdd");
			$("#electricityMeterNbrSelectAdd").select2();
			$("#electricityTypeSelectAdd").prop("disabled", "disabled");
			$("#paymentReqBeginDateAdd").prop("disabled",  false);
			$("#paymentReqEndDateAdd").prop("disabled",  false);
			$("#totalUsedDegreeAdd").prop("disabled",  false);
			$("#usedDegreeAdd").prop("disabled",  false);
			$("#memoAdd").prop("disabled",  false);
			$("#paymentReqAmtAdd").prop("disabled", false);
			$("#bBoardAdd").trigger('reset');
		});
	}
	
	//  租約編號連動取得電錶號碼、租約名稱、起帳日資料
	$("#contractNoSelectAdd").change(onContractNoSelectAddChange);
	// 當選擇電錶號碼時，判斷租約編號及電錶號碼皆有選值才開啟用電輸入下拉選單
	$("#electricityMeterNbrSelectAdd").change(onElectricityMeterNbrSelectAddChange);
	// 當用電方式選擇'借電_抄表'時，取得租約的每度金額，並計算出請款金額
	$("#electricityTypeSelectAdd").change(onElectricityTypeSelectAddChange);
	// 本次用電度數修改控制
	$("#usedDegreeAdd").change(onUsedDegreeAddChange);
	// 當請款日期有改變時清空本次用電度數及請款金額,避免請款金額計算錯誤
	$("#paymentReqBeginDateAdd").change(onPaymentReqBeginDateAddChange);
	$("#paymentReqEndDateAdd").change(onPaymentReqEndDateAddChange);
	//================請電電子檔匯入FUNCTION======================//
	// 匯入檔案
	function importFileData(){
		event.stopPropagation(); // Stop stuff happening
		event.preventDefault(); // Totally stop stuff happening 
		var duplicateFile = false;
		var filename = $("#file_A").val();
		var selname = filename.substr(filename.indexOf(".") + 1,filename.length);
		if ($("#file_A").val() == '') {
			alert('請選擇檔案');
			return false;
		} else if (selname != "txt") {
			alert("請電電子檔匯入格式只有txt,請重新操作!");
		} else {
			duplicateFile = true;
		}
		if (duplicateFile) {
			// Create a formdata object
			var data = new FormData();
			$.each(files, function(key, value) {
				data.append(key, value);
			});
			$.ajax({
				url : CONTEXT_URL + "/pay/pay002/readBatchApply",
				type : 'post',
				data : data,
				cache : false,
				dataType : 'json',
				processData : false, // Don't process the files
				contentType : false, // Set content type to false as jQuery will tell the server its a query string request
				success : function(data) {
				    addGridFileDataRow(data);
					//alert(data.msg);
				}
			});
		}
	}

	// Grid增加匯入FileData
	function addGridFileDataRow(data) {
		$('#addFileGrid').jqGrid('clearGridData');
		
		if(data.rows.length > 0){
		   for (var i=0; i<data.rows.length; i++){
			  
				var electricityMeterNbrAdd = data.rows[i].power_NO;
				var contractNoAdd = getContractNo(electricityMeterNbrAdd);
				var contractNameAdd = getContractValue("contractName",contractNoAdd);
				var lsSDateAdd = getContractValue("lsSDate",contractNoAdd);
				var paymentReqBeginDateAdd = data.rows[i].last_RECORD_YYYYMMDD;
				var paymentReqEndDateAdd = data.rows[i].record_YYYYMMDD;
				var usedDegreeDayAdd = getUsedDegreeDayDataValue("usedDegreeDay",contractNoAdd,electricityMeterNbrAdd);
				var lastUsedDegreeAdd = getUsedDegreeDayDataValue("lastUsedDegree",contractNoAdd,electricityMeterNbrAdd);
				var totalUsedDegreeAdd = data.rows[i].normal_DIFF_VOL;
				var usedDegreeAdd = data.rows[i].normal_CHARGE_VOL;
				// 取得超出/減少比率
				var ratioAdd = 0;
				if (usedDegreeAdd !=""){
					var ratio = 0;
					ratio = (usedDegreeAdd - usedDegreeDayAdd)/usedDegreeDayAdd;
					ratioAdd = formatFloat(ratio,2);
					if(ratioAdd == "Infinity"){
						ratioAdd = 0;
					}
				} 
				var paymentReqAmtAdd = (data.rows[i].charge_OVER_AMT-data.rows[i].charge_DISC_AMT);
				var insertYrmnAdd = data.rows[i].insert_YRMN;
				var seqNbrAdd = data.rows[i].seq_NBR;
				// addFileGrid
				
				var gridId = $("#addFileGrid").jqGrid('getDataIDs');
				$("#addFileGrid").jqGrid("addRowData", gridId + 1, {
					'contractNoAdd' : contractNoAdd,
					'electricityMeterNbrAdd' : electricityMeterNbrAdd,
					'contractNameAdd' : contractNameAdd,
					'electricityTypeDscrAdd' : '請電',
					'lsSDateAdd' : lsSDateAdd,
					'paymentReqBeginDateAdd' : paymentReqBeginDateAdd,
					'paymentReqEndDateAdd' : paymentReqEndDateAdd,
					'usedDegreeDayAdd' : usedDegreeDayAdd,
					'lastUsedDegreeAdd' : lastUsedDegreeAdd,
					'totalUsedDegreeAdd' : totalUsedDegreeAdd,
					'usedDegreeAdd' : usedDegreeAdd,
					'ratioAdd' : ratioAdd,
					'paymentReqAmtAdd' : paymentReqAmtAdd,
					'electricityTypeAdd' : 'ELE04',
					'insertYrmnAdd' : insertYrmnAdd,
					'seqNbrAdd' : seqNbrAdd
				}, "first");
				
				$("#addFileGrid").trigger("reloadGrid");
				
			}
		  
		  
		    $('#addFileGrid').jqGrid('setColProp', 'ifAutoDeductDscrAdd', { editoptions: { value: "Y:是;N:否"}});
			$('#addFileGrid').jqGrid('setColProp', 'ifNoSiteMappingDscrAdd', { editoptions: { value: "Y:無站台對應;N:有站台對應"}});
			
			 $("#addFileGrid tr").each(function () {
                 var tr = $(this);
                 $("#addFileGrid").editRow($(tr).attr("id"), true);
                
             });

		  
		}
		
		// 關閉用電輸入Grid,顯示請電電子檔匯入Grid
		$("#addGridDiv").hide();
		$("#addFileGridDiv").show();
	}
	
	// 依電錶號碼取得租約編號
	function getContractNo(electricityMeterNbrAdd){
		var returnValue="";
		var data = {
			  electricityMeterNbrAdd : electricityMeterNbrAdd
		};
		$.ajax({
			url : CONTEXT_URL + "/pay/pay002/getContractNo",
			data : data,
		    type : "POST",
			dataType : 'json',
			async : false,
			success : function(data) {
				if (data.success) {
					if (data.rows.length > 0) {
						returnValue = data.rows[0].ls_NO;
					}
				}
			}
		});
		return returnValue;
	}
	
	// 依租約編號取得租約名稱、起帳日
	function getContractValue(str,contractNoAdd){
		var returnValue="";
		var data = {
			   contractNoSelectAdd : contractNoAdd
		};
		$.ajax({
			url : CONTEXT_URL + "/pay/pay002/getContractValue",
			data : data,
		    type : "POST",
			dataType : 'json',
			async : false,
			success : function(data) {
				if (data.success) {
					if (data.rows.length > 0) {
						if (str == "contractName"){
							returnValue = data.rows[0].ls_NAME;
						} else {
							returnValue = dateFormatter(data.rows[0].ls_S_DATE);
						}
					}
				}
			}
		});
		return returnValue;
	}
	
	// 依租約編號、電錶號碼取得平均用電數/日、上期度數
	function getUsedDegreeDayDataValue(str,contractNoAdd,electricityMeterNbrAdd){
		var returnValue="";
		var data = {
				contractNoSelectAdd : contractNoAdd,
				electricityMeterNbrSelectAdd : electricityMeterNbrAdd
		};
		$.ajax({
			url : CONTEXT_URL + "/pay/pay002/getUsedDegreeDayData",
			data : data,
		    type : "POST",
			dataType : 'json',
			async : false,
			success : function(data) {
				if (data.success) {
					    var usedDegreeDay = data.maps.usedDegreeDay;
						var mDay = data.maps.mDay;
				        var lastUsedDegree = data.maps.lastUsedDegree ;
						if (usedDegreeDay == "" || usedDegreeDay == "undefined" || usedDegreeDay == null) {
							usedDegreeDay = 0;
						}
						if (mDay == "" || mDay == "undefined" || mDay == null) {
							mDay = 1;
						}
						if (lastUsedDegree == "" || lastUsedDegree == "undefined" || lastUsedDegree == null) {
							lastUsedDegree = 0;
						}
						usedDegreeDay = usedDegreeDay/mDay;
						if (str == "usedDegreeDay"){
							returnValue = formatFloat(usedDegreeDay,2);// 平均用電數/日
						} else {
							returnValue = lastUsedDegree;// 上期度數
						}
				}
			}
		});
		return returnValue;
	}
	
    // Grid檢查請電電子檔匯入資料欄位
    function addFileGridCheck(){
		var isChecked;
		var flag = true;
		// 取得Grid中textbox及select內容值
		$("#addFileGrid tr:not(:first)").each(function(idx, obj) {
			isChecked = ($('input[type=checkbox]:checked', this).length==1);
			if(!isChecked) return;
		    var totalUsedDegreeAdd = $("input[name=totalUsedDegreeAdd]",this);	// 本次度數textbox
			var usedDegreeAdd =  $("input[name=usedDegreeAdd]",this);// 本次用電度數textbox
			var memoAdd =  $("input[name=memoAdd]",this);// 用電補充說明textbox
			var gridRow = $("#addFileGrid").getRowData($(this).attr("id"));
			var usedDegreeDayAdd = gridRow.usedDegreeDayAdd;
			var useElectricityRatio = $("#useElectricityRatio").val();// 用電比值待確認,暫時設定為1
			// 必須輸入欄位檢查
			if(totalUsedDegreeAdd.val() == ""){
				alert("本次度數不可為空值!");
				totalUsedDegreeAdd.focus();
				flag = false;
			}
			if(usedDegreeAdd.val() == ""){
				alert("本次用電度數不可為空值!");
				usedDegreeAdd.focus();
				flag = false;
			}
			// 取得用電補充說明是否需輸入的判斷依據(本次用電度數-平均用電數/日)/平均用電數/日
			var countAvgVal = (usedDegreeAdd.val()-usedDegreeDayAdd)/usedDegreeDayAdd;
			// 若(本次用電度數-平均用電數/日)/平均用電數/日>設定用電比,則用電補充說明為必要欄位
			if(countAvgVal > useElectricityRatio && memoAdd.val() ==""){
				alert("用電補充說明一定要輸入!");
				memoAdd.focus();
				flag = false;
			}
			// 輸入欄位內容檢查
			if(totalUsedDegreeAdd.val() !="" && !isAllNumber(totalUsedDegreeAdd.val())){
				alert("本次度數只能輸入數字");
				totalUsedDegreeAdd.focus();
				flag = false;
			}
			if(usedDegreeAdd.val() !="" && !isAllNumber(usedDegreeAdd.val())){
				alert("本次用電度數只能輸入數字");
				usedDegreeAdd.focus();
				flag = false;
			}
		});
		return flag;
	}
    
	//================增加用電輸入FUNCTION======================//
	// 當日期有改變時清空本次用電度數及請款金額,避免請款金額計算錯誤
	function 	onPaymentReqBeginDateAddChange(){
		var electricityTypeSelect = $("#electricityTypeSelectAdd").find("option:selected").prop("value");
		if (electricityTypeSelect == "ELE02"){
			$("#usedDegreeAdd").val("");
			$("#paymentReqAmtAdd").val("");
		}
	}
	function 	onPaymentReqEndDateAddChange(){
		var electricityTypeSelect = $("#electricityTypeSelectAdd").find("option:selected").prop("value");
		if (electricityTypeSelect == "ELE02"){
			$("#usedDegreeAdd").val("");
			$("#paymentReqAmtAdd").val("");
		}
	}
	
	// 本次用電度數修改控制
	function onUsedDegreeAddChange(){
		var electricityTypeSelect = $("#electricityTypeSelectAdd").find("option:selected").prop("value");
		var usedDegree = $("#usedDegreeAdd").val();
		var paymentReqBeginDate = $("#paymentReqBeginDateAdd").val();
		var paymentReqEndDate = $("#paymentReqEndDateAdd").val();
		var countAmt = 0;
		if(usedDegree !="" && !isAllNumber(usedDegree)){
			alert("本次用電度數只能輸入數字");
			$("#usedDegreeAdd").val("");
			$("#electricityTypeSelectAdd").select2("val","");
			$("#paymentReqAmtAdd").val("");
			$("#paymentReqAmtAdd").prop("disabled", false);
			return false;
		}
		if (electricityTypeSelect == "ELE02"){
			if (paymentReqBeginDate == "" || paymentReqEndDate == "" || usedDegree == ""){
				alert("選擇'借電_抄表'需先輸入本次請款日起迄及本次用電數! ");
				$("#electricityTypeSelectAdd").select2("val","");
			} else {
		        // disable請款金額欄位並計算請款金額
		        countAmt = getPaymentReqAmt();
		        $("#paymentReqAmtAdd").val(countAmt);
		        $("#paymentReqAmtAdd").prop("disabled", "disabled");
			}
		}
		// 取得超出/減少比率
		if (usedDegree !=""){
			var ratio = 0;
			var usedDegreeDay = $("#usedDegreeDayAdd").val();// 平均用電數/日
			ratio = (usedDegree - usedDegreeDay)/usedDegreeDay;
			$("#ratioAdd").val(formatFloat(ratio,2));
		}
	}
	
	// 當用電方式選擇'借電_抄表'時，取得租約的每度金額(計算出請款金額)、欄位控制
	function onElectricityTypeSelectAddChange() {
		var electricityTypeSelect = $("#electricityTypeSelectAdd").find("option:selected").prop("value");
		var countAmt = 0;
		var usedDegree = $("#usedDegreeAdd").val();// 本次用電度數
		var paymentReqBeginDate = $("#paymentReqBeginDateAdd").val();// 本次請款起始日
		var paymentReqEndDate = $("#paymentReqEndDateAdd").val();// 本次請款結束日
		// 當用電方式選擇'借電_抄表'時，取得租約的每度金額，並計算出請款金額且需disable請款金額欄位
		if (electricityTypeSelect == "ELE02"){
			$("#paymentReqAmtAdd").val("");
			$("#paymentReqAmtAdd").prop("disabled", false);
			$("#paymentReqBeginDateAdd").prop("disabled",  false);
			$("#paymentReqEndDateAdd").prop("disabled",  false);
			$("#totalUsedDegreeAdd").prop("disabled",  false);
			$("#usedDegreeAdd").prop("disabled",  false);
			$("#memoAdd").prop("disabled",  false);
			if (paymentReqBeginDate == "" || paymentReqEndDate == ""){
				alert("選擇'借電_抄表'需先輸入本次請款日起迄及本次用電數! ");
				$("#electricityTypeSelectAdd").select2("val","");
			} else {
		        // disable請款金額欄位並計算請款金額
		        countAmt = getPaymentReqAmt();
		        $("#paymentReqAmtAdd").val(countAmt);
		        $("#paymentReqAmtAdd").prop("disabled", "disabled");
			}
		} else if (electricityTypeSelect == "ELE06"){// 預付
			//如果用電方式=預付,則租約編號,錶號碼,租約名稱,用電方式,起帳日,請款金額一定要有値,其餘欄位皆disable不用輸入
			$("#paymentReqBeginDateAdd").val("");
			$("#paymentReqEndDateAdd").val("");
			$("#totalUsedDegreeAdd").val("");
			$("#usedDegreeAdd").val("");
			$("#memoAdd").val("");
			$("#paymentReqBeginDateAdd").prop("disabled", "disabled");
			$("#paymentReqEndDateAdd").prop("disabled", "disabled");
			$("#totalUsedDegreeAdd").prop("disabled", "disabled");
			$("#usedDegreeAdd").prop("disabled", "disabled");
			$("#memoAdd").prop("disabled", "disabled");
			$("#paymentReqAmtAdd").val("");
			$("#paymentReqAmtAdd").prop("disabled", false);
		} else {
			$("#paymentReqAmtAdd").val("");
			$("#paymentReqAmtAdd").prop("disabled", false);
			$("#paymentReqBeginDateAdd").prop("disabled",  false);
			$("#paymentReqEndDateAdd").prop("disabled",  false);
			$("#totalUsedDegreeAdd").prop("disabled",  false);
			$("#usedDegreeAdd").prop("disabled",  false);
			$("#memoAdd").prop("disabled",  false);
		}
	}
	
	// 當選擇電錶號碼時，判斷租約編號及電錶號碼皆有選值才開啟用電輸入下拉選單並取得平均用電數/日、上期度數、超出/減少比率
	function onElectricityMeterNbrSelectAddChange() {
		$("#electricityTypeSelectAdd").select2("val","");
		$("#usedDegreeAdd").val("");
		$("#paymentReqAmtAdd").val("");
		$("#paymentReqAmtAdd").prop("disabled", false);
		$("#paymentReqBeginDateAdd").prop("disabled",  false);
		$("#paymentReqEndDateAdd").prop("disabled",  false);
		$("#totalUsedDegreeAdd").prop("disabled",  false);
		$("#usedDegreeAdd").prop("disabled",  false);
		$("#memoAdd").prop("disabled",  false);
		// 若租約編號或電錶號碼選擇空值則用電/預付方式不可選取
		if ($("#contractNoSelectAdd").val() == "" || $("#electricityMeterNbrSelectAdd").val() == "") {
			$("#electricityTypeSelectAdd").prop("disabled", "disabled");
			return false;
		}
		if ($("#contractNoSelectAdd").val() != "" && $("#electricityMeterNbrSelectAdd").val() != ""){
			// 兩個值皆選擇才開啟用電/預付方式下拉選單
			$("#electricityTypeSelectAdd").prop("disabled", false);
			var data = {
					contractNoSelectAdd : $("#contractNoSelectAdd").find("option:selected").prop("value"),
					electricityMeterNbrSelectAdd : $("#electricityMeterNbrSelectAdd").find("option:selected").prop("value")
			};
			// 租約編號及電錶號碼皆有值才取得平均用電數/日、上期度數
			$.ajax({
				url : CONTEXT_URL + "/pay/pay002/getUsedDegreeDayData",
				data : data,
			    type : "POST",
				dataType : 'json',
				success : function(data) {
					if (data.success) {			
						    var usedDegreeDay = data.maps.usedDegreeDay;
							var mDay = data.maps.mDay;
					        var lastUsedDegree = data.maps.lastUsedDegree ;
							if (usedDegreeDay == "" || usedDegreeDay == "undefined" || usedDegreeDay == null) {
								usedDegreeDay = 0;
							}
							if (mDay == "" || mDay == "undefined" || mDay == null) {
								mDay = 1;
							}
							if (lastUsedDegree == "" || lastUsedDegree == "undefined" || lastUsedDegree == null) {
								lastUsedDegree = 0;
							}
							usedDegreeDay = usedDegreeDay/mDay;
							$("#usedDegreeDayAdd").val(formatFloat(usedDegreeDay,2));// 平均用電數/日
							$("#lastUsedDegreeAdd").val(lastUsedDegree);// 上期度數
					}
				}
			});
		}
	}

	// 租約編號連動取得電錶號碼、租約名稱、起帳日資料
	function onContractNoSelectAddChange() {
		$("#electricityMeterNbrSelectAdd").html("");
		$("<option value=''>--請選擇--</option>").appendTo("#electricityMeterNbrSelectAdd");
		$("#electricityMeterNbrSelectAdd").select2();
		$("#electricityTypeSelectAdd").prop("disabled", "disabled");
		// 預設租約名稱、起帳日為空值
		$("#contractNameAdd").val("");
		$("#lsSDateAdd").val("");
		$("#electricityTypeSelectAdd").select2("val","");
		$("#usedDegreeAdd").val("");
		$("#paymentReqAmtAdd").val("");
		$("#paymentReqAmtAdd").prop("disabled", false);
		$("#paymentReqBeginDateAdd").prop("disabled",  false);
		$("#paymentReqEndDateAdd").prop("disabled",  false);
		$("#totalUsedDegreeAdd").prop("disabled",  false);
		$("#usedDegreeAdd").prop("disabled",  false);
		$("#memoAdd").prop("disabled",  false);
		// 若租約編號選擇空值則不取電錶號碼、租約名稱、起帳日資料、清空並開啟請款金額
		if ($("#contractNoSelectAdd").val() == "") {
			$("#electricityMeterNbrSelectAdd").select2("val","");// 電錶號碼回預設值
			$("#electricityTypeSelectAdd").prop("disabled", "disabled");
			return false;
		}
		var data = {
				contractNoSelectAdd : $("#contractNoSelectAdd").find("option:selected").prop("value")
		};
		$.ajax({
			url : CONTEXT_URL + "/pay/pay002/getEnergyMeter",
			data : data,
		    type : "POST",
			dataType : 'json',
			success : function(data) {
				if (data.success) {
					if (data.rows.length > 0) {
						for (var i = 0; i < data.rows.length; i++) {
							$("#electricityMeterNbrSelectAdd").append(
									"<option value="+data.rows[i].energy_METER+">"
											+ data.rows[i].energy_METER
											+ "</option>");
						}
					}
				}
			}
		});
		// 依選擇租約編號取得租約名稱、起帳日
		$.ajax({
			url : CONTEXT_URL + "/pay/pay002/getContractValue",
			data : data,
		    type : "POST",
			dataType : 'json',
			success : function(data) {
				if (data.success) {
					if (data.rows.length > 0) {
						var lsSDate = dateFormatter(data.rows[0].ls_S_DATE);
						$("#contractNameAdd").val(data.rows[0].ls_NAME);
						$("#lsSDateAdd").val(lsSDate);
					}
				}
			}
		});
	}
	
/* 	// 取得計算後請款金額(舊版)
	function getPaymentReqAmt() {
		var data = {
				contractNoVal : $("#contractNoSelectAdd").find("option:selected").prop("value"),
				electricityMeterNbrVal : $("#electricityMeterNbrSelectAdd").find("option:selected").prop("value"),
				paymentReqBeginDateVal : $("#paymentReqBeginDateAdd").val(),
				paymentReqEndDateVal : $("#paymentReqEndDateAdd").val(),
				usedDegreeVal : $("#usedDegreeAdd").val()
		};
		var countAmt = "";
		$.ajax({
			url : CONTEXT_URL + "/pay/pay002/getPaymentReqAmt/",
			data : data,
			type : "POST",
			dataType : 'json',
			async: false,
			success : function(data) {
				if (data.success) {
					countAmt = data.msg
				}
			}
		});
		return countAmt;
	} */
	
	// 取得計算後請款金額
	function getPaymentReqAmt() {
		var data = {
				contractNoVal : $("#contractNoSelectAdd").find("option:selected").prop("value"),
				electricityMeterNbrVal : $("#electricityMeterNbrSelectAdd").find("option:selected").prop("value"),
				paymentReqBeginDateVal : $("#paymentReqBeginDateAdd").val(),
				paymentReqEndDateVal : $("#paymentReqEndDateAdd").val(),
				totalUsedDegreeVal : $("#totalUsedDegreeAdd").val()
		};
		var countAmt = "";
		$.ajax({
			url : CONTEXT_URL + "/pay/pay002/getPaymentReqAmt/",
			data : data,
			type : "POST",
			dataType : 'json',
			async: false,
			success : function(data) {
				if (data.success) {
					countAmt = data.msg;
				}
			}
		});
		return countAmt;
	}
	
    // 檢查增加用電輸入資料欄位(將資料新增至Grid前先檢查)
    function inputAddCheck(){
    	var usedDegreeDay = $("#usedDegreeDayAdd").val();// 平均用電數/日
    	var useElectricityRatio = $("#useElectricityRatio").val();// 用電比值待確認,暫時設定為1
    	var paymentReqBeginDate= $("#paymentReqBeginDateAdd").val();
    	var paymentReqEndDate = $("#paymentReqEndDateAdd").val();
		var totalUsedDegree = $("#totalUsedDegreeAdd").val();
		var usedDegree = $("#usedDegreeAdd").val();
		var memo = $("#memoAdd").val();
		var paymentReqAmt = $("#paymentReqAmtAdd").val();
		var electricityTypeSelect = $("#electricityTypeSelectAdd").find("option:selected").prop("value");
		var contractNoSelect = $("#contractNoSelectAdd").find("option:selected").prop("value");
		var electricityMeterNbrSelect = $("#electricityMeterNbrSelectAdd").find("option:selected").prop("value");
		var contractName = $("#contractNameAdd").val();
		var lsSDate = $("#lsSDateAdd").val();
		// 必須輸入欄位檢查
		if(contractNoSelect == ""){
			alert("租約編號不可為空值!");
			return false;
		}
		if(electricityMeterNbrSelect == ""){
			alert("電錶號碼不可為空值!");
			return false;
		}
		if(electricityTypeSelect == ""){
			alert("用電方式不可為空值!");
			return false;
		}
		if (electricityTypeSelect == "ELE06"){// 預付
			if(contractName == ""){
				alert("租約名稱不可為空值!");
				return false;
			}
			if(lsSDate == ""){
				alert("起帳日不可為空值!");
				return false;
			}
		} else {// 借電_抄錶、借電_其他
			if(paymentReqBeginDate == ""){
				alert("本次請款起始日不可為空值!");
				return false;
			}
			if(paymentReqEndDate ==""){
				alert("本次請款結束日不可為空值!");
				return false;
			}
			if(totalUsedDegree == ""){
				alert("本次度數不可為空值!");
				return false;
			}
			if(usedDegree == ""){
				alert("本次用電度數不可為空值!");
				return false;
			}
			// 取得用電補充說明是否需輸入的判斷依據(本次用電度數-平均用電數/日)/平均用電數/日
			var countAvgVal = (usedDegree-usedDegreeDay)/usedDegreeDay;
			// 若(本次用電度數-平均用電數/日)/平均用電數/日>設定用電比,則用電補充說明為必要欄位
			if(countAvgVal > useElectricityRatio && memo ==""){
				alert("用電補充說明一定要輸入!");
				return false;
			}
		}
		if(paymentReqAmt == ""){
			alert("請款金額不可為空值!");
			return false;
		}
		// 輸入欄位內容檢查
		// 請款日期起迄檢核
		if(Date.parse(paymentReqEndDate).valueOf() <= Date.parse(paymentReqBeginDate).valueOf()){
			alert("本次請款結束日不能早於等於開始日!");
			return false;
		}
		// 輸入數字檢查
		if(totalUsedDegree !="" && !isAllNumber(totalUsedDegree)){
			alert("本次度數只能輸入數字");
			return false;
		}
		if(usedDegree !="" && !isAllNumber(usedDegree)){
			alert("本次用電度數只能輸入數字");
			return false;
		}
		if(paymentReqAmt !="" && isNaN(paymentReqAmt)){
			alert("請款/預付金額只能輸入數字");
			return false;
		}
		return true;
	}

	// Grid增加用電輸入
	function addGridRow() {
		var contractNoAdd = $("#contractNoSelectAdd").find("option:selected").val();
		var electricityMeterNbrAdd = $("#electricityMeterNbrSelectAdd").find("option:selected").val();
		var contractNameAdd = $("#contractNameAdd").val();
		var electricityTypeAdd = $("#electricityTypeSelectAdd").find("option:selected").val();
		var lsSDateAdd = $("#lsSDateAdd").val();
		var paymentReqBeginDateAdd = $("#paymentReqBeginDateAdd").val();
		var paymentReqEndDateAdd = $("#paymentReqEndDateAdd").val();
		var usedDegreeDayAdd = $("#usedDegreeDayAdd").val();
		var lastUsedDegreeAdd = $("#lastUsedDegreeAdd").val();
		var totalUsedDegreeAdd = $("#totalUsedDegreeAdd").val();
		var usedDegreeAdd = $("#usedDegreeAdd").val();
		var ratioAdd = $("#ratioAdd").val();
		var memoAdd = $("#memoAdd").val();
		var paymentReqAmtAdd = $("#paymentReqAmtAdd").val();
		var ifAutoDeductAdd = $("#ifAutoDeductSelectAdd").find("option:selected").val();
		var ifNoSiteMappingAdd = $("#ifNoSiteMappingSelectAdd").find("option:selected").val();
		var electricityTypeDscrAdd = $("#electricityTypeSelectAdd").find("option:selected").text();
		var ifAutoDeductDscrAdd = $("#ifAutoDeductSelectAdd").find("option:selected").text();
		var ifNoSiteMappingDscrAdd = $("#ifNoSiteMappingSelectAdd").find("option:selected").text();
		var gridId = $("#addGrid").jqGrid('getDataIDs');
		$("#addGrid").jqGrid("addRowData", gridId + 1, {
			'contractNoAdd' : contractNoAdd,
			'electricityMeterNbrAdd' : electricityMeterNbrAdd,
			'contractNameAdd' : contractNameAdd,
			'electricityTypeDscrAdd' : electricityTypeDscrAdd,
			'lsSDateAdd' : lsSDateAdd,
			'paymentReqBeginDateAdd' : paymentReqBeginDateAdd,
			'paymentReqEndDateAdd' : paymentReqEndDateAdd,
			'usedDegreeDayAdd' : usedDegreeDayAdd,
			'lastUsedDegreeAdd' : lastUsedDegreeAdd,
			'totalUsedDegreeAdd' : totalUsedDegreeAdd,
			'usedDegreeAdd' : usedDegreeAdd,
			'ratioAdd' : ratioAdd,
			'memoAdd' : memoAdd,
			'paymentReqAmtAdd' : paymentReqAmtAdd,
			'ifAutoDeductDscrAdd' : ifAutoDeductDscrAdd,
			'ifNoSiteMappingDscrAdd' : ifNoSiteMappingDscrAdd,
			'electricityTypeAdd' : electricityTypeAdd,
			'ifAutoDeductAdd' : ifAutoDeductAdd,
			'ifNoSiteMappingAdd' : ifNoSiteMappingAdd
		}, "first");
	}
	
	//=======================刪除、儲存功能======================================================
    // 刪除用電輸入
	function delAddGridRow() {
		var selr = jQuery('#addGrid').jqGrid('getGridParam', 'selarrrow');
		if (!selr.length == 0) {
			if(confirm("是否確定要刪除?")){
				for (var i = selr.length - 1; i >= 0; i--) {
					$('#addGrid').jqGrid("delRowData", selr[0]);
				}
			}	
		} else {
			alert("未勾選要刪除的資料列,請重新操作!");
		}
	}
	
    // 刪除請電電子檔匯入資料
	function delAddFileGridRow() {
		var selr = jQuery('#addFileGrid').jqGrid('getGridParam', 'selarrrow');
		if (!selr.length == 0) {
			if(confirm("是否確定要刪除?")){
				for (var i = selr.length - 1; i >= 0; i--) {
					$('#addFileGrid').jqGrid("delRowData", selr[0]);
				}
			}
		} else {
			alert("未勾選要刪除的資料列,請重新操作!");
		}
	}
    
	// 儲存用電輸入資料(insert 多筆)
	function saveAddData(){
		var addTbPayElectricityArray =[];
		var selr = jQuery('#addGrid').jqGrid('getGridParam', 'selarrrow');
		if (selr.length == 0) {
			alert("未勾選要儲存的資料列,請重新操作!");
			return false;
		}
		for (var i = 0; i < selr.length; i++) {
			var rowId = selr[i];
			var rowData = jQuery('#addGrid').jqGrid('getRowData', rowId);
			var TbPayElectricity = {
					contractNo :rowData.contractNoAdd,
					electricityMeterNbr :rowData.electricityMeterNbrAdd,
					electricityType :rowData.electricityTypeAdd,
					totalUsedDegree :rowData.totalUsedDegreeAdd,
					usedDegree :rowData.usedDegreeAdd,
					paymentReqAmt :rowData.paymentReqAmtAdd,
					ifAutoDeduct :rowData.ifAutoDeductAdd,
					ifNoSiteMapping :rowData.ifNoSiteMappingAdd,
					paymentReqBeginDate :rowData.paymentReqBeginDateAdd,
					paymentReqEndDate :rowData.paymentReqEndDateAdd,
					memo :rowData.memoAdd
			};
			addTbPayElectricityArray.push(TbPayElectricity);
		}
		var data = {
				payElectricity : JSON.stringify(addTbPayElectricityArray)
		}
		$.ajax({
			url : CONTEXT_URL + "/pay/pay002/saveToTable/",
			data : data,
			type : "POST",
			dataType : 'json',
			success : function(data) {
				if (data.success) {
					alert("用電輸入新增成功!");
					parent.$.fancybox.close();//關閉視窗
				} else {
					alert("錯誤：" + data.msg);
				}
			},
			error : function(data) {
				alert(data.msg);
				return false;
			}
		});
		return true;
	}
	
	// 儲存請電電子檔匯入資料(insert 多筆)
	function saveFileData(){
		var isChecked;
		var addTbPayElectricityArray =[];
		var selr = jQuery('#addFileGrid').jqGrid('getGridParam', 'selarrrow');
		if (selr.length == 0) {
			alert("未勾選要儲存的資料列,請重新操作!");
			return false;
		}
		// 取得Grid中textbox及select內容值
		$("#addFileGrid tr:not(:first)").each(function(idx, obj) {
			isChecked = ($('input[type=checkbox]:checked', this).length==1);
			if(!isChecked) return;
		    var totalUsedDegreeAdd = $("input[name=totalUsedDegreeAdd]",this);	// 本次度數
			var usedDegreeAdd =  $("input[name=usedDegreeAdd]",this);// 本次用電度數textbox
			var memoAdd =  $("input[name=memoAdd]",this);// 用電補充說明textbox
			var ifAutoDeductDscrAdd =  $("select[name=ifAutoDeductDscrAdd]",this);// 自動扣款下拉選單
			var ifNoSiteMappingDscrAdd =  $("select[name=ifNoSiteMappingDscrAdd]",this);	// 無站台對應下拉選單
			var gridRow = $("#addFileGrid").getRowData($(this).attr("id"));
			var TbPayElectricity = {
					yearMonth :gridRow.insertYrmnAdd,
					impFileSeqNbr :gridRow.seqNbrAdd,
					contractNo :gridRow.contractNoAdd,
					electricityMeterNbr :gridRow.electricityMeterNbrAdd,
					electricityType :gridRow.electricityTypeAdd,
					totalUsedDegree :totalUsedDegreeAdd.val(),
					usedDegree :usedDegreeAdd.val(),
					paymentReqAmt :gridRow.paymentReqAmtAdd,
					ifAutoDeduct :ifAutoDeductDscrAdd.val(),
					ifNoSiteMapping :ifNoSiteMappingDscrAdd.val(),
					paymentReqBeginDate :gridRow.paymentReqBeginDateAdd,
					paymentReqEndDate :gridRow.paymentReqEndDateAdd,
					memo :memoAdd.val()
			};
			addTbPayElectricityArray.push(TbPayElectricity);
		});
		var data = {
			   payElectricity : JSON.stringify(addTbPayElectricityArray)
		}
		$.ajax({
			url : CONTEXT_URL + "/pay/pay002/saveToTable/",
			data : data,
			type : "POST",
			dataType : 'json',
			success : function(data) {
				if (data.success) {
					alert("用電輸入新增成功!");
					parent.$.fancybox.close();//關閉視窗
				} else {
					alert("錯誤：" + data.msg);
				}
			},
			error : function(data) {
				alert(data.msg);
				return false;
			}
		});
		return true;
	}
	
	// 時間左補零滿兩位
	function addZero(i) {
		if (i < 10) {
			i = "0" + i;
		}
		return i;
	}
	
    // 日期格式化(YYYY/MM/DD)
    function dateFormatter(cellvalue){
		if (cellvalue != null && cellvalue != "") {
	   		var stDate = new Date(cellvalue);
			return stDate.getFullYear() + "/" + addZero((stDate.getMonth() + 1)) + "/" + addZero(stDate.getDate());
   		}else {
   			return "";
   		}
    }
    
	// 取到小數第二位
	function formatFloat(num, pos){
	    var size = Math.pow(10, pos);
	    return Math.round(num * size) / size;
	}
</script>
</head>

<body>
	<div class="row">
		<div id="breadcrumb" class="col-md-12">
			<ol class="breadcrumb">
				<div class="col-sm-12" id="btnDiv">
					<input id="file_A" type="file" name="file" class="require" style="display: none;" />
					<button class="btn btn-primary btn-label-left" type="button"
						id="importFileBtn"> 請電電子檔匯入
					</button>
					<button class="btn btn-primary btn-label-left" type="button"
						id="inputAddBtn"> 增加用電輸入
					</button>
					<button class="btn btn-primary btn-label-left" type="button"
						id="deleteAddBtn"> 刪除用電輸入
					</button>
					<button class="btn btn-primary btn-label-left" type="button"
						id="saveAddBtn">
						<span><i class="fa fa-save"></i></span> 儲存
					</button>
					<button class="btn btn-primary btn-label-left" type="button"
						id="resetBtn">取消</button>
				</div>
			</ol>
		</div>
	</div>
	<div class="box-header">
		<div class="box-name">
			<i class="fa fa-edit"></i> <span>用電/預付輸入作業</span>
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
					<form class="form-horizontal" id="bBoardAdd">
						<div class="form-group">
							<div class="row">
								<div class="col-md-12">
									<label class="col-sm-12 control-label">
										<table border=2 style="table-layout: fixed; word-break: break-all; word-wrap: break-word; width: 99%;">
											<tr>
											    <td class="titleTD" style="width: 15%">租約編號</td>
												<td class="titleTD" style="width: 15%">電錶號碼</td>
												<td class="titleTD" style="width: 15%">租約名稱</td>
												<td class="titleTD" style="width: 13%">用電/預付方式</td>
												<td class="titleTD" style="width: 12%">起帳日</td>
												<td class="titleTD" style="width: 12%">本次請款起始日</td>
												<td class="titleTD" style="width: 12%">本次請款結束日</td>
											</tr>
											<tr>
												<td class="textTD">
													<!-- 隱藏欄位 start-->
													<input type="hidden" id="useElectricityRatio" name="useElectricityRatio"  value="${useElectricityRatio}"/>
													<!-- 隱藏欄位 end-->
													<select id="contractNoSelectAdd" name="contractNoSelectAdd" >
														<option value="" selected>--請選擇--</option>
														<c:forEach items="${contractNoSelectAdd}" var="contractNo">
															<option value="${contractNo.LS_NO}">${contractNo.LS_NO}</option>
														</c:forEach>
													</select>
												</td>
												<td class="textTD">
													<select id="electricityMeterNbrSelectAdd" name="electricityMeterNbrSelectAdd" >
														<option value="" selected>--請選擇--</option>
													</select>
												</td>
												<td class="textTD">
													<input id="contractNameAdd" type="text" class="form-control"
														name="contractNameAdd" value="">
												</td>
												<td class="textTD">
												    <select id="electricityTypeSelectAdd" name="electricityTypeSelectAdd" >
														<option value="" selected>--請選擇--</option>
														<c:forEach items="${electricityTypeSelectAdd}" var="electricity">
															<option value="${electricity.LOOKUP_CODE}">${electricity.LOOKUP_CODE_DESC}</option>
														</c:forEach>
													</select>
												</td>
												<td class="textTD">
													<input id="lsSDateAdd" type="text" class="form-control" 
														name="lsSDateAdd" value="" readonly="readonly">
												</td>
												<td class="textTD">
													<input id="paymentReqBeginDateAdd" type="text" value="" 
														class="form-control" name="paymentReqBeginDateAdd"  readonly="readonly">
												</td>
												<td class="textTD">
													<input id="paymentReqEndDateAdd" type="text" value="" 
														 class="form-control" name="paymentReqEndDateAdd"  readonly="readonly">
												</td>
											</tr>
											<tr><td class="titleTD">平均用電數/日</td>
												<td class="titleTD">上期度數</td>
												<td class="titleTD">本次度數</td>
												<td class="titleTD">本次用電度數</td>
												<td class="titleTD">超出/減少比率</td>
												<td COLSPAN=2 class="titleTD">用電補充說明</td>
											</tr>
											<tr>
												<td class="textTD">
													<input id="usedDegreeDayAdd" type="text" class="form-control" 
														name="usedDegreeDayAdd" value="">
												</td>
												<td class="textTD">
													<input id="lastUsedDegreeAdd" type="text" class="form-control" 
														name="lastUsedDegreeAdd" value="">
												</td>
												<td class="textTD">
													<input id="totalUsedDegreeAdd" type="text" class="form-control"
														name="totalUsedDegreeAdd" value="">
												</td>
												<td class="textTD">
													<input id="usedDegreeAdd" type="text" class="form-control" 
														name="usedDegreeAdd" value="">
												</td>
												<td class="textTD">
													<input id="ratioAdd" type="text" class="form-control" 
														name="ratioAdd" value="">
												</td>
												<td COLSPAN=2 class="textTD">
													<input id="memoAdd" type="text" class="form-control" 
														name="memoAdd" value="">
												</td>
											</tr>
											<tr>
											    <td class="titleTD">請款/預付金額</td>
												<td class="titleTD">自動扣款</td>
												<td COLSPAN=5 class="titleTD">無站台對應</td>
											</tr>
											<tr>
												<td class="textTD">
													<input id="paymentReqAmtAdd" type="text" class="form-control" 
														name="paymentReqAmtAdd" value="">
												</td>
												<td class="textTD">
													<select id="ifAutoDeductSelectAdd" name="ifAutoDeductSelectAdd" class="form-control">
													   <option value="Y">是</option>
													   <option value="N">否</option>
												    </select>
												</td>
												<td COLSPAN=5 class="textTD">
													<select id="ifNoSiteMappingSelectAdd" name="ifNoSiteMappingSelectAdd" class="form-control">
													   <option value="Y">無站台對應</option>
													   <option value="N">有站台對應</option>
												    </select>	
												</td>
											</tr>
										</table>
									</label>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div id="addGridDiv" class="col-xs-12" >
			<!-- 固定id for window.resize start-->
			<div id="jQgrid" align="center">
				<table id="addGrid"></table>
				<div id="addpager"></div>
			</div>
			<!-- 固定id for window.resize end-->
		</div>
		<div id="addFileGridDiv" class="col-xs-12" >
			<!-- 固定id for window.resize start-->
			<div id="jQgrid" align="center">
				<table id="addFileGrid"></table>
				<div id="addfpager"></div>
			</div>
			<!-- 固定id for window.resize end-->
		</div>											
	</div>
</body>
</html>