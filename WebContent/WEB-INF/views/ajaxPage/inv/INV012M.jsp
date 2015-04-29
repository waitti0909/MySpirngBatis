<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>發料</title>
<script src="<s:url value="/resources/plugins/tinymce/tinymce.min.js" />"></script>
<script src="<s:url value="/resources/plugins/tinymce/jquery.tinymce.min.js" />"></script>
<script	src="<s:url value="/resources/js/validate.js" />"></script>
<script type="text/javascript">
	$(document).ready(function() {
		
		LoadSelect2Script("<s:url value="/resources/plugins/select2/select2.min.js" />", selectDefault);
		mountGrid();
		//查詢退料站台
		$('#qryMrBtn').click(function() {						
			var matNoList="";
			var ids = jQuery("#mastergrid").jqGrid('getDataIDs');
			
			for (var i = 0; i < ids.length; i++) {
				var rowId = ids[i];
				var myrow = jQuery('#mastergrid').jqGrid ('getRowData', rowId);
				
				if (matNoList == "") {
					matNoList = "matNo="+ myrow.mat_NO;
				}
				else {
					matNoList = matNoList + "&" + "matNo=" + myrow.mat_NO;
				}
			}
			openMrPageDialogFrame("showMsPag", $("#callBackFun").val(),matNoList);
		});
		
		//發料按鈕
		$('#msBtn').click(function() {
			//檢核
			if(msCheck()){
				var optIdList="";
				var prPersonnel = $("#prPersonnelSelect").find("option:selected").val();//領料人員
				var msPersonnel = $("#msPersonnelSelect").find("option:selected").val();//發料人員
				var crTime = getCrTime();
				var whId = $("#whId").val();
				var txnNo="";//getTxnNo();//產生TXN NO
				var selr = jQuery('#mastergrid').jqGrid('getGridParam','selarrrow');
				var msArray =[];
				for(var i=0;i<selr.length;i++){
					var rowData = jQuery('#mastergrid').jqGrid('getRowData', selr[i]);
					
					var newTagNo = null;
					
					if(rowData.ctrlType == "S" /* && rowData.isAsset=="1" */){
						var makeTagNo = "#" + selr[i] + "_tagNo";
						newTagNo = $(makeTagNo).find("option:selected").val();
					}
					
					var makediffQty = "#" + selr[i] + "_diffQty";
					var makeTxnMatNo = "#" + selr[i] + "_txnMatNo";
					var diffQty = $(makediffQty).val();
					var txnMatNo = $(makeTxnMatNo).find("option:selected").text();
					var qty=parseInt(rowData.qty);	
					var txnQty=parseInt(rowData.txnQty);	
					
					//diffQty :發料數 qty：申請數 tnxQty:已領數
					//申請數小於已領數
					if(qty>txnQty){
						
						//更新Booking檔＆INV檔
						/* updateBookingInvInv(rowData.opt_ID,rowData.mat_NO,rowData.qty,whId);
						//寫入TXN檔
						insertTxn(txnNo,rowData.opt_ID,rowData.mat_NO,diffQty,whId,newTagNo,rowData.faNo,
						rowData.srlNo,crTime,rowData.dtl_SEQ,prPersonnel,msPersonnel); 
						//寫入ONHAND檔
						insertOnHand(txnNo,rowData.opt_ID,rowData.mat_NO,rowData.qty,whId,newTagNo,
						rowData.faNo,rowData.srlNo,crTime);
						//寫入ACT檔 
						insertAct(txnNo,rowData.opt_ID,rowData.mat_NO,rowData.qty,newTagNo,rowData.srlNo,crTime,prPersonnel,msPersonnel);
						 */
						 var msObj ={
							"optId" : rowData.opt_ID,//領料單號
							"matNo" : rowData.mat_NO,//料號
							"txnMatNo" : txnMatNo,//實際發料號
							"qty"  : rowData.qty,//申請數
							"whId" : whId,//倉庫
							"txnNo" : txnNo,//
							"diffQty" : diffQty,//發料數
							"newTagNo" : newTagNo,//貼標號碼
							"faNo" : rowData.faNo,//
							"srlNo" :rowData.srlNo,//
							"dtlSeq" : rowData.dtl_SEQ,//
							"prPerson" : prPersonnel,//領料人員
							"msPerson" : msPersonnel//發料人員
						}
						msArray.push(msObj);
						/* if (optIdList == "") {
							optIdList = "optId="+ rowData.opt_ID;
						}
						else {
							optIdList = optIdList + "&" + "optId=" + rowData.opt_ID;
						} */
					}  
				}
				//寫入及更新
				var data ={
					msArray : JSON.stringify(msArray)
				}
				 $.ajax({
					url : CONTEXT_URL + "/inv/inv012/saveToTable/",
					data : data,
					type : "POST",
					dataType : 'json',
					async:false,
					success : function(data) {
						if(data.success){
							//updateOpt(optIdList);//檢查申請數
							openSp2(data.msg);//開啟發料單畫面 data.msg=txnNo
						}else{
							alert("錯誤 訊息: " + data.msg);
							parent.$.fancybox.close();//關閉視窗
						}
					}
				});	
			}	
		});
	});
	
	
	
	$("#dtlgrid").jqGrid({
		datatype : "local",
		pager : '#dtlpager',
		autowidth:true,
		url : CONTEXT_URL + "/inv/inv012/searchWithInvSrlByTxnNo/",
		colNames : ['發料單號','發料日期','實發料號','廠商序號','貼標號碼','發料人員','發料數'],
		colModel : [ 
		 	{name : 'txn_no',index : 'txn_no', width:'220px',align : 'center',sortable : false},
		    {name : 'cr_time',index : 'cr_time', width:'200px', align : 'center',sortable : false,formatter:
		    	function(cellvalue, options, rowObject){
					if (cellvalue != null && cellvalue != "") {
				   		var stDate = new Date(cellvalue);
				   		
						return stDate.getFullYear() + "/" + (stDate.getMonth() + 1) + "/" + stDate.getDate()+ " " + addZero(stDate.getHours()) +":"+addZero(stDate.getMinutes());
			   		}else {
			   			
			   			return "";
			   		}
				}},
		    {name : 'mat_no',index : 'mat_no', width:'150px', align : 'center',sortable : false},
		    {name : 'venSn',index : 'venSn', width:'400px', align : 'center',sortable : false},
		    {name : 'tagNo',index : 'tagNo', width:'400px', align : 'center',sortable : false},
		    {name : 'crUser',index : 'crUser', width:'150px', align : 'center',sortable : false},
		    {name : 'qty',index : 'qty', width:'100px', align : 'center',sortable : false,formatter: nullForZero}],
			caption : "發料歷程",
			rownumbers : true,
			onSelectRow : function() {
			}
	});//end grid	
				
	$(window).resize(function() {
		$(window).unbind("onresize");
		$("#dtlgrid,.grid").setGridWidth($("#jQgrid").width() - 10);
		$("#mastergrid,.grid").setGridWidth($("#jQgrid").width() - 10);
		$(window).bind("onresize", this);
	}).trigger('resize');
	
	//查詢
	$('#qryBtn').click(
		function() {
			var txnNo = $("#txnNo").val();
			var wareHouse = $("#txnWareHouseSelect").find("option:selected").val();
			var dept = $("#txnDeptSelect").find("option:selected").val();
			var crStartDate = $("#crStartDate").val();
			var crEndDate = $("#crEndDate").val();
			var personnel = $("#txnPersonnelSelect").find("option:selected").val();
			
			if (personnel == "" || personnel == null) {
				
				alert("請選擇發料人員");
				return false;
			}
			
			if (crStartDate == "" || crStartDate == null || crEndDate == "" || crEndDate == null) {
				
				alert("請選擇發料日期");
				return false;
			}
			
			var data = {
				txnNo : txnNo,
				wareHouse : wareHouse,
				dept : dept,
				crStartDate : crStartDate,
				crEndDate : crEndDate,
				personnel : personnel,
				txnType : "1"
			};
			
			$("#mastergrid").setGridParam({
				datatype : "json",
				postData : data,
				mtype : 'POST',
				page : 1
			}).jqGrid().trigger("reloadGrid");
	});

	//領料部門連動領料人
	$("#prDeptSelect").change(onDeptSelectChange);

	//發料部門連動發料人
	//$("#msDeptSelect").change(onMsDeptSelectChange);
	
	//========================================
	function mountGrid(){
	$("#mastergrid").jqGrid({
		datatype : "local",
		pager : '#masterpager',
		autowidth:'true',
		/* loadonce: true,
		height:'220px', */
		width: '2500px',
		rowNum : 1000,
		viewrecords : true,
		url : CONTEXT_URL + "/inv/inv012/searchTxnMrD/",
		colNames : ['領料單號','料號','品名','申請','已領','實發料號','實發品名','貼標號碼','廠商序號','庫存','發料','預約','','','','','',''],
		colModel : [ 
		 	{name : 'opt_ID',index : 'opt_ID', width:'280px', align : 'center',sortable : false},
		    {name : 'mat_NO',index : 'mat_NO', widht:'150px', align : 'center',sortable : false},
		    {name : 'matName',index : 'matName' , align : 'center',sortable : false}, 
		    {name : 'qty',index : 'qty', width:'75px', align : 'center',sortable : false,formatter: nullForZero},
	  		{name : 'txnQty',index : 'txnQty', width:'75px', align : 'center',sortable : false,formatter: nullForZero},
	  		{name : 'txnMatNo',index : 'txnMatNo' , width:'260px', align : 'center',sortable : false,editable: true,edittype:"select",
	  		editoptions: { value: getMatNo("") ,
		                 	 class : "require" ,
		                 	dataEvents: [
						        {type: 'change',
						         fn: function(e) {
						        	 var tr=$(e.target).closest("tr");
						        	 var id = tr.attr("id");
						        	 var rowData = $("#mastergrid").getRowData(id);
						        	 var selObj = $('select[name=venSn]',tr);
						        	 $(selObj).html("");
					        		 $("<option value=''>--請選擇--</option>").appendTo(selObj);
					        		 $(selObj).select2();
						        	 getMatName(id,$(this).find("option:selected").val());
						        	 var makeTagNo="#"+id+"_venSn";
						        	 var matNo="#"+id+"_txnMatNo";
						        	 if (((parseInt(rowData.qty, 10) - parseInt(rowData.txnQty, 10)) != 0)) { //序號件
						        		 getTagNoByChange(rowData,$(matNo).find("option:selected").val(),makeTagNo,id,selObj);
						        	 } 
						        	 setGridTxnMatNoStatus(id,$(matNo).find("option:selected").val());
						        	 getGridTxnMatNoCnt(id,$(matNo).find("option:selected").val());
						        }}
							],dataInit: function (elem) {value: gridSelect(elem);}
            }},
	  		{name : 'txnMatName',index : 'txnMatName', align : 'center',sortable : false},
	  		{name : 'tagNo',index:'tagNo', width:'250px', align : 'center',sortable : false},
	  		{name : 'venSn',index : 'venSn' ,width:'410px', editable: true,edittype:"select",
	  		editoptions:{
		     value : "'':--請選擇--",
		     class : "require" ,
		     dataInit:function(colId){$(colId).select2();},
		     dataEvents: [
	                         {  type: 'change',
	                            fn: function(e) {
	                            	var tr=$(e.target).closest("tr");
						          	var id = tr.attr("id");
						          	setFaNo(id,$("#"+id+"_venSn").val());
	                            }
	                         }
                      ]
		    }},
	  		{name : 'stdQty',index:'stdQty', width:'75px', align : 'center',sortable : false,formatter: nullForZero},
	  		{name : 'diffQty',index : 'diffQty', width:'75px', align : 'center',sortable : false,formatter: nullForZero, editable: true,edittype:"text",editoptions:{size:"10",maxlength:"10"}},
			{name : 'bookingQty',index : 'bookingQty' , width:'75px',  align : 'center',sortable : false,formatter:
			function(cellvalue, options, rowObject) {
				var returnValue = cellvalue;
				if (cellvalue == "" || cellvalue == "undefined" || cellvalue == null) {
						returnValue = "0";
				}
				be = "<a onclick=bookingLink('"+rowObject.mat_NO+"');return false;><label style='color:black;text-decoration:underline;'>"+Math.abs(returnValue)+"</label></a>";
				return be;
			}},
			{name : 'ctrlType',index : 'ctrlType' , align : 'center',sortable : false,hidden:true},
			{name : 'srlNo',index : 'srlNo',width : 40 , align : 'center',sortable : false,hidden:true},
			{name : 'faNo',index : 'faNo',width : 40 , align : 'center',sortable : false,hidden:true},
			{name : 'isAsset',index : 'isAsset',width : 40 , align : 'center',sortable : false,hidden:true},
			{name : 'dtl_SEQ',index : 'dtl_SEQ',width : 40 , align : 'center',sortable : false,hidden:true},
			{name : 'txnMatNoStatus',index : 'txnMatNoStatus',width : 40 , align : 'center',sortable : false,hidden:true}],
			multiselect : true,
			caption : "發料清單",
			rownumbers : true,
			onSelectRow : function(id) {
				var rowData = jQuery(this).getRowData(id);
				var data = {
					txnNo:rowData.opt_ID,
					matNo:rowData.mat_NO,
					dtlSeq:rowData.dtl_SEQ
				};
				if((rowData.qty-rowData.txnQty)>0){					
					jQuery('#mastergrid').jqGrid('editRow',id,true);
					changeTxnMatNo(id,rowData.mat_NO);
				}
				$("#dtlgrid").setGridParam({datatype:"json", postData:data, mtype: 'POST',page:1}).jqGrid().trigger("reloadGrid");
			},
			gridComplete : function() {
				var ids = jQuery("#mastergrid").jqGrid('getDataIDs');
				for (var i = 0; i < ids.length; i++) {
				    var rowId = ids[i];
				    var rowData = jQuery('#mastergrid').jqGrid ('getRowData', rowId);
				    if((rowData.qty-rowData.txnQty)<=0){//如果發料數與實際發料數相同								
						var qtyCheckWord = "#jqg_mastergrid_"+rowId;
						$(qtyCheckWord).hide();//設定隱藏
					}
					$("#mastergrid").jqGrid("setRowData", rowId, {'txnMatNoStatus' : rowData.ctrlType});//設定隱藏的物料狀態
					$("#cb_mastergrid").hide();//設定隱藏
				}
			}
			/*,
			//單筆點選
             beforeSelectRow: function(rowid) {
                 var rowData = jQuery(this).getRowData(rowid);
                 //判斷條件 範例
                 if((rowData.qty-rowData.txnQty)<=0){	
                     return false;
                 }else{				
					jQuery('#mastergrid').jqGrid('editRow',rowid,true);
					changeTxnMatNo(rowid,rowData.mat_NO);
					return true;
				}
             },
             //全選
             onSelectAll: function(aRowids,status) { 
             	if(status){
                   $.each(aRowids, function (i) {
                       var rowData =jQuery('#mastergrid').getRowData((i+1));
                       if((rowData.qty-rowData.txnQty)<=0){
                        jQuery('#mastergrid').setSelection((i+1), false);
                       }else{
                       	jQuery('#mastergrid').jqGrid('editRow',(i+1),true);
                       	changeTxnMatNo((i+1),rowData.mat_NO);
                       }
                   });
                }
             }*/
             
		});	
			var gridData = {
				optId:$("#optIdList").val(),
				whId:$("#whId").val(),
				workType:$("#workType").val()
			};
			$("#mastergrid").setGridParam({datatype : "json",postData : gridData,mtype : 'POST',page : 1}).jqGrid().trigger("reloadGrid");	
	}	
	function changeTxnMatNo(id,preMatNo){
		$.log("preMatNo: "+preMatNo)
		var matNo="#"+id+"_txnMatNo";
		$(matNo).select2("val",preMatNo).change();
	}
	function bookingLink(matNo) {
		$.ajax({
			mimeType : 'text/html; charset=utf-8',
			type : 'POST',
			url : CONTEXT_URL + "/inv/inv024/init",
			data : {
				"paramMatNo" : matNo,
				"paramWhId" : $("#whId").val(),
				"cancelSystemBut" : "true"
			},
			dataType : "html",
			async : false,
			success : function(data) {
				initialDialogFrame("showDeptPosPag", "Booking查詢", data);
			}
		});
	}
	function gridSelect(elem) {
		$(elem).select2();
	}
	function addZero(i) {
		if (i < 10) {
			i = "0" + i;
		}
		return i;
	}
	
	//取得系統時間
	function getCrTime() {
		var crTime = "";
		$.ajax({
			url : CONTEXT_URL + "/ajax/inv/public/getToday/",
			data : crTime,
			type : "POST",
			dataType : 'json',
			async : false,
			success : function(data) {
				if (data.success) {
					crTime = data.msg;
				}
			}
		});
		return crTime;
	}
	
	//發料檢核
	function msCheck() {
		var prDeptSelect = $("#prDeptSelect").find("option:selected").val();
		var msDeptSelect = $("#msDeptSelect").find("option:selected").val();
		var prPersonnelSelect = $("#prPersonnelSelect").find("option:selected").val();
		var msPersonnelSelect = $("#msPersonnelSelect").find("option:selected").val();

		if ((prDeptSelect == null || prDeptSelect == "")) {
			alert('請選擇領料單位');
			return false;
		}
		
		if ((msDeptSelect == null || msDeptSelect == "")) {
			alert('請選擇發料單位');
			return false;
		}
		
		if ((prPersonnelSelect == null || prPersonnelSelect == "")) {
			alert('請選擇領料人員');
			return false;
		}
		
		if ((msPersonnelSelect == null || msPersonnelSelect == "")) {
			alert('請選擇發料人員');
			return false;
		}
		
		var selr = jQuery('#mastergrid').jqGrid('getGridParam', 'selarrrow');
		
		if (selr.length == 0) {
			alert("執行發料功能前，請先勾選一筆資料");
			return false;
		}
		
		//檢查貼標號碼及發料數是否都選值
		for (var i = 0; i < selr.length; i++) {
			var rowData = jQuery('#mastergrid').jqGrid('getRowData', selr[i]);
			var newTagNo = null;
			var newTagNoText = "";
			if (rowData.ctrlType == "S" /* && rowData.isAsset == "1" */) {
				var makeTagNo = "#" + selr[i] + "_venSn";
				newTagNo = $(makeTagNo).find("option:selected").val();
				newTagNoText = $(makeTagNo).find("option:selected").text();
			}
			var makeMatNo = "#" + selr[i] + "_txnMatNo";
			var newMatNo = $(makeMatNo).find("option:selected").text();
			var makediffQty = "#" + selr[i] + "_diffQty";
			var diffQty = $(makediffQty).val();
			
			//diffQty :發料數 qty：申請數 tnxQty:已領數
			if (parseInt(diffQty, 10) > (parseInt(rowData.qty, 10) - parseInt(rowData.txnQty, 10))) {
				alert("領料單號:" + rowData.opt_ID+",料號:"+rowData.mat_NO + " 發料數不可大於申請數-已領數，請重新輸入");
				return false;
			}
			if(!isAllNumber(diffQty)){			
				alert("發料數需為正數，不得為負數或其他文字");
				return false;
			}
			if(parseInt(diffQty)<=0){
				alert("發料數不得輸入小於0或總和為0，請重新輸入");
				return false;
			}
			/* if (rowData.qty == rowData.txnQty) {
				alert("領料單號:" + rowData.opt_ID+",料號:"+rowData.mat_NO + " 申請數=已領數，請勿選擇此筆資料");
				return false;
			} */
			
			if (newTagNo == "" && rowData.ctrlType == "S" && (parseInt(rowData.qty) > parseInt(rowData.txnQty)) /* && rowData.isAsset == "1" */ ) {
				alert("領料單號:" + rowData.opt_ID+",料號:"+rowData.mat_NO + " 請使用查詢退料站台功能或使用下拉選單，選擇廠商序號，或無廠商序號可選請通知ＩＴ人員");
				return false;
			}
			
			if (newMatNo == "--請選擇--" || newMatNo == "" && (parseInt(rowData.qty) > parseInt(rowData.txnQty)) ) {
				alert("領料單號:" + rowData.opt_ID+",料號:"+rowData.mat_NO + " 請使用查詢退料站台功能或使用下拉選單，選擇實發料號");
				return false;
			}
			
			if (diffQty == "" || diffQty == "0") {
				alert("領料單號:" + rowData.opt_ID+",料號:"+rowData.mat_NO + " 請輸入發料數且不得為0");
				return false;
			}
			
			if(!isAllNumber(diffQty)){			
				alert("領料單號:" + rowData.opt_ID+",料號:"+rowData.mat_NO +" 發料數只能輸入數字");
				return false;
			}
			if (parseInt(diffQty, 10) > parseInt(rowData.stdQty, 10)) {
				alert("領料單號:" + rowData.opt_ID+",料號:"+rowData.mat_NO + " 發料數大於庫存數，請重新輸入");
				return false;
			}
		}
		return true;
	}
	
	// 申請部門連動申請人
	function onDeptSelectChange() {
		$("#prPersonnelSelect").html("");
		$("<option value=''>--請選擇--</option>").appendTo("#prPersonnelSelect");
		$("#prPersonnelSelect").select2();
		
		if ($("#prDeptSelect").val() == "") {
			return false;
		}
		
		var data = {
			dept : $("#prDeptSelect").find("option:selected").prop("value")
		};
		
		 $.ajax({
			url : CONTEXT_URL + "/ajax/inv/public/personnelDept/",
			data : data,
			type : "POST",
			dataType : 'json',
			success : function(data) {
				if (data.success) {
					if (data.rows.length > 0) {
						for (var i = 0; i < data.rows.length; i++) {
							$("#prPersonnelSelect").append("<option value="+data.rows[i].psn_NO+">"+ data.rows[i].chn_NM+ "</option>");
						}
					}
				}
			}
		}); 
	}
	
	// 申請部門連動申請人
	function onMsDeptSelectChange() {
		$("#msPersonnelSelect").html("");
		$("<option value=''>--請選擇--</option>").appendTo("#msPersonnelSelect");
		$("#msPersonnelSelect").select2();
		
		if ($("#msDeptSelect").val() == "") {
			return false;
		}
		var data = {
			dept : $("#msDeptSelect").find("option:selected").prop("value")
		};
		$.ajax({
			url : CONTEXT_URL + "/ajax/inv/public/personnelDept/",
			data : data,
			type : "POST",
			dataType : 'json',
			success : function(data) {
				if (data.success) {
					if (data.rows.length > 0) {
						for (var i = 0; i < data.rows.length; i++) {
							$("#msPersonnelSelect").append("<option value="+data.rows[i].psn_NO+">"+ data.rows[i].chn_NM + "</option>");
						}
					}
				}
			}
		});
	}
	//取品名
	function getMatName(id,matNo) {
		var data = {'matNo' :matNo};
		$.ajax({
			url : CONTEXT_URL + "/inv/inv012/getMatName/",
			data : data,
			type : "POST",
			dataType : 'json',
			async : false,
			success : function(data) {
				if (data.success) {
					$("#mastergrid").jqGrid("setRowData", id, {'txnMatName' : data.msg }); //設定選擇的廠商序號
				}
			}
		});
	}
	
	//產生TxnNo
	function getTxnNo() {
		var txnNo = "";
		var data = {};
		$.ajax({
			url : CONTEXT_URL + "/inv/inv012/genTxnNo/",
			data : data,
			type : "POST",
			dataType : 'json',
			async : false,
			success : function(data) {
				if (data.success) {
					txnNo = data.msg;
				}
			}
		});
		return txnNo;
	}
	
	//設定faNo srlNo
	function setFaNo(id,venSn) {
			var selr = jQuery('#mastergrid').jqGrid('getGridParam','selarrrow');
			var rowData = jQuery('#mastergrid').jqGrid('getRowData', id);
			var makeTagNo="#"+id+"_venSn";
			var makeVenSn=rowData.venSn;
			var makeMatNo="#"+id+"_txnMatNo";
			var data = {
				matNo : $(makeMatNo).find("option:selected").val(),
				whId : $("#whId").val()
			};
			 for (var i = 0; i < selr.length; i++) {
				if(venSn==$("#"+selr[i]+"_venSn").find("option:selected").text() && id!=selr[i]){
					alert("不得選擇相同廠商序號");
					$("#"+id+"_venSn").select2("val", "");
					$("#mastergrid").jqGrid("setRowData", id, {'faNo' : "",'srlNo' : "",'tagNo' :""});
					return;
				}
			} 
			$.ajax({
				url : CONTEXT_URL + "/inv/inv012/getFaNo/",
				data : data,
				type : "POST",
				dataType : 'json',
				async : false,
				success : function(data) {
					if (data.success) {
						//設定此筆的資產編號及序號控管編號
						var tagNoWord=$(makeTagNo).find("option:selected").text();
						if(tagNoWord=="--請選擇--"){
							$("#mastergrid").jqGrid("setRowData", id, {'faNo' : "",'srlNo' : "",'tagNo' :""});
						}else{
							for(var i = 0; i < data.rows.length; i++) {
								if(data.rows[i].ven_sn==venSn){
									$("#mastergrid").jqGrid("setRowData", id, {'faNo' : data.rows[i].fa_no,'srlNo' : data.rows[i].srl_no,'tagNo' :data.rows[i].tag_no});
									}
								}
						}	
					}
				}
			});		
	}
	//取得實發料號的庫存數量
	function getGridTxnMatNoCnt(id,matNo){
		var data ={ matNo:matNo,whId:$("#whId").val()};
		$.ajax({
			url : CONTEXT_URL + "/inv/inv012/getStdQtyByMatNo/",
			data : data,
			type : "POST",
			dataType : 'json',
			async : false,
			success : function(data) {
				if(data.success){
					$("#mastergrid").jqGrid("setRowData", id,{'stdQty' :data.row.std_qty});
				}	
			}
		});		
	}
	function setGridTxnMatNoStatus(id,matNo){
		var data ={ matNo:matNo};
		$.ajax({
			url : CONTEXT_URL + "/inv/inv012/getTxnMatNoStatus/",
			data : data,
			type : "POST",
			dataType : 'json',
			async : false,
			success : function(data) {	
			var makediffQty = "#" + id + "_diffQty";	
				if(data.row.ctrl_type=="S")	{	
					$(makediffQty).val("1");
					$(makediffQty).attr("disabled",true);
				}else if(data.row.ctrl_type=="V"){
					var rowData = jQuery('#mastergrid').jqGrid('getRowData', id);
					$(makediffQty).val((rowData.qty - rowData.txnQty));
					$(makediffQty).attr("disabled",false);
				}
				$("#mastergrid").jqGrid("setRowData", id,{'ctrlType' :data.row.ctrl_type});
			}
		});
		
		$("#mastergrid").jqGrid("setRowData", id,{'txnMatNoStatus' :""});
	}
	//產生TagNo 實發料號變動
	function getTagNoByChange(object,matNo,makeTagNo,id,selObj) {		
		var returnValue = "";
		var data = {
			matNo : matNo,
			whId : $("#whId").val()
		};		
		if(matNo!=""){	
			$.ajax({
				url : CONTEXT_URL + "/inv/inv012/getTagNo/",
				data : data,
				type : "POST",
				dataType : 'json',
				async : false,
				success : function(data) {
				$.log("data.success :"+data.success);
					if (data.success) {
						for (var i = 0; i < data.rows.length; i++) {						
							$(selObj).append("<option value="+data.rows[i].ven_sn+">"+ data.rows[i].ven_sn+ "</option>");
						}
					}else{
						$("#mastergrid").jqGrid("setRowData", id, {'faNo' : "",'srlNo' : "",'tagNo' :""});
						$(selObj).html("");
					    $("<option value=''>--請選擇--</option>").appendTo(selObj);
						$(selObj).select2();
					}
					}
			});
		}
		return returnValue;
	}
	
	//產生TagNo 預設
	function getTagNo(object) {
		
		var returnValue = "";
		var data = {
			matNo : object.mat_NO,
			whId : $("#whId").val()
		};
		
		if(object.ctrlType=='S'){
			$.ajax({
				url : CONTEXT_URL + "/inv/inv012/getTagNo/",
				data : data,
				type : "POST",
				dataType : 'json',
				async : false,
				success : function(data) {
					if (data.success) {
						for (var i = 0; i < data.rows.length; i++) {
							if (i == 0) {
								returnValue += "" + ":" + "--請選擇--" + ";";
							}
							if (i < data.rows.length - 1) {
								returnValue += data.rows[i].ven_sn + ":"+ data.rows[i].tag_no + ";";
							} else {
								returnValue += data.rows[i].ven_sn + ":"+ data.rows[i].tag_no;
							}
						}
					}
				}
			});
		}
		return returnValue;
	}
	
	//產生TxnMatNo
	function getMatNo(object) {
		var returnValue = "";
		var data={whId : $("#whId").val()};
		$.ajax({
			url : CONTEXT_URL + "/inv/inv012/getTxnMatNo/",
			data : data,
			type : "POST",
			dataType : 'json',
			async : false,
			success : function(data) {
				if (data.success) {
					for (var i = 0; i < data.rows.length; i++) {
						if (i == 0) {
							returnValue += "" + ":" + "--請選擇--" + ";";
						}
						if (i < data.rows.length - 1) {
							//returnValue += data.rows[i].mat_name + ":"+ data.rows[i].mat_no + ";";
							returnValue += data.rows[i].mat_no + ":"+ data.rows[i].mat_no + ";";
						} else {
							returnValue += data.rows[i].mat_no + ":" + data.rows[i].mat_no;
						}
					}
				}
			}
		});
		return returnValue;
	}
	
	//格式化grid內數字
	function nullForZero(cellvalue, options, rowObject) {		
		var returnValue = cellvalue;		
		if (cellvalue == "" || cellvalue == "undefined" || cellvalue == null) {
			returnValue = "0";
		}		
		return Math.abs(returnValue);
	}
	
	//更新OPT狀態
	function updateOpt(optId) {		
		var data = {
			optId : optId,
			whId : $("#whId").val()
		};		
		$.ajax({
			url : CONTEXT_URL + "/inv/inv012/updateOpt/",
			data : data,
			type : "POST",
			dataType : 'json',
			async : false,
			success : function(data) {}
		});
	}
	
	//更新Booking檔＆INV檔
	function updateBookingInvInv(optId1, matNo1, qty1, whId1) {		
		var updateBookingdata = {
			matNo : matNo1,
			qty : qty1,
			whId : whId1,
			optId : optId1
		};		
		var invInvData = {
			matNo : matNo1,
			qty : qty1,
			whId : whId1,
			optId : optId1
		};		
		$.ajax({
			url : CONTEXT_URL + "/inv/inv012/updateBookingMinusQty/",
			data : updateBookingdata,
			type : "POST",
			dataType : 'json',
			async : false,
			success : function(data) {}
		});
		$.ajax({
			url : CONTEXT_URL + "/inv/inv012/updateInvInvMinusQty/",
			data : invInvData,
			type : "POST",
			dataType : 'json',
			async : false,
			success : function(data) {
				if (data.success) {
					txnNo = data.msg;
				}
			}
		});
	}
	
	//寫入TXN檔
	function insertTxn(txnNo, optId, matNo, qty, whId, tagNo, faNo, srlNo, crTime, dtlSeq, prPersonnel, msPersonnel) {		
		var insertTxndata = {
			txnNo : txnNo,
			optId : optId,
			matNo : matNo,
			qty : qty,
			whId : whId,
			tagNo : tagNo,
			faNo : faNo,
			srlNo : srlNo,
			crTime : crTime,
			dtlSeq : dtlSeq,
			rtUser : prPersonnel,
			rcvUser : msPersonnel
		};
		
		$.ajax({
			url : CONTEXT_URL + "/inv/inv012/insertTxn/",
			data : insertTxndata,
			type : "POST",
			dataType : 'json',
			async : false,
			success : function(data) {
				if (data.success) {
					txnNo = data.msg;
				}
			}
		});
	}
	
	//寫入ONHAND檔
	function insertOnHand(txnNo, optId, matNo, qty, whId, tagNo, faNo, srlNo, crTime) {
		var insertOnHanddata = {
			txnNo : txnNo,
			optId : optId,
			matNo : matNo,
			qty : qty,
			whId : whId,
			tagNo : tagNo,
			faNo : faNo,
			srlNo : srlNo,
			crTime : crTime
		};
		
		$.ajax({
			url : CONTEXT_URL + "/inv/inv012/insertOnHand/",
			data : insertOnHanddata,
			type : "POST",
			dataType : 'json',
			async : false,
			success : function(data) {
				if (data.success) {
					txnNo = data.msg;
				}
			}
		});
	}
	
	//寫入ACT檔
	function insertAct(txnNo, optId, matNo, qty, tagNo, srlNo, crTime, prPersonnel, msPersonnel) {
		
		var insertActdata = {
			txnNo : txnNo,
			optId : optId,
			matNo : matNo,
			qty : qty,
			tagNo : tagNo,
			srlNo : srlNo,
			crTime : crTime,
			prPersonnel : prPersonnel,
			msPersonnel : msPersonnel
		};
		
		$.ajax({
			url : CONTEXT_URL + "/inv/inv012/insertMrAct/",
			data : insertActdata,
			type : "POST",
			dataType : 'json',
			async : false,
			success : function(data) {},
			error : function(data) {
				alert(data.msg);
			}
		});
	}
	
	//開啟發料單畫面
	function openSp2(txnNo) {
		var crUser = $("#prPersonnelSelect").find("option:selected").text();
		window.location.href = CONTEXT_URL+"/inv/inv012/printSignPage?txnNo="+txnNo+"&whName="+$("#whName").val()+"&sendDate=&applicant="+crUser;
		parent.$.fancybox.close();
	}

	//接收SP1回傳值並寫入GRID
	function showMsData(psnData) {
		var obj = JSON.parse(psnData);
		for (var i = 0; i < obj.matObject.length; i++) {
			var ids = jQuery("#mastergrid").jqGrid('getDataIDs');
			for (var idCnt = 0; idCnt < ids.length; idCnt++) {
				var rowId = ids[idCnt];
				var rowData = jQuery('#mastergrid').jqGrid('getRowData', rowId);
				if (rowData.mat_NO == obj.matObject[i].mat_no && (rowData.diffQty!=0)) {
					maketxnMatNo="#"+rowId+"_txnMatNo";
					maketagNo="#"+rowId+"_venSn";
					$(maketxnMatNo+' option').each(function() {
					    if($(this).val() == obj.matObject[i].mat_no) {
					        $(this).prop("selected", true);
					    }
					});
					$(maketagNo+' option').each(function() {
					    if($(this).val() == obj.matObject[i].venSn) {
					        $(this).prop("selected", true);
					    }
					});
					
					$("#mastergrid").jqGrid("setRowData", rowId, {
						'tagNo' : obj.matObject[i].tagNo,
						'venSn' : obj.matObject[i].venSn,
						'faNo' : obj.matObject[i].faNo,
						'srlNo' : obj.matObject[i].srlNo,
						'txnMatNo' :  obj.matObject[i].mat_no,
						'txnMatName' :  obj.matObject[i].matName
					});
				}
			}
		}
	}

	// 開啟下拉式選單搜尋文字功能
	function selectDefault() {
		$("#bBoardAddEdit").find("select").select2();
		$("#prDeptSelect").select2();
		$("#msDeptSelect").select2();
		$("#prPersonnelSelect").select2();
		$("#msPersonnelSelect").select2();
	}
</script>
</head>
<body>
	<div id="breadcrumb" class="col-md-12">
		<ol class="breadcrumb">
			<div class="col-sm-12" id="btnDiv">
				<button class="btn btn-primary btn-label-left" type="button" id="msBtn">
					<span>
						<i class="fa fa-plus"></i>
					</span>
					執行發料
				</button>
				<button class="btn btn-primary btn-label-left" type="button" id="qryMrBtn">
					<span>
						<i class="fa fa-search"></i>
					</span>
					查詢退料站台
				</button>
			</div>
		</ol>
	</div>
	
	<div class="row">
		<div class="col-xs-12 col-sm-12">
			<div class=" ui-draggable ui-droppable">
			
				<div class="box-header">
					<div class="box-name">
						<i class="fa fa-edit"></i> <span>發料作業功能</span>
					</div>
					<div class="box-icons">
						<p class="expand-link"></p>
					</div>
					<div class="no-move"></div>
				</div>
				
				<div class="box-content">
					<form class="form-horizontal bootstrap-validator-form" id="bBoardAddEdit" >
						<input hidden="true" value="${wareHouseVal}" id="whId">
						<input hidden="true" value="${wareHouse}" id="whName">
						<input hidden="true" value="${optId}" id="optIdList">
						<input hidden="true" value="${workType}" id="workType">
						<table style="width: 95%">
							<tr>
								<td><label class="col-sm-12 control-label"><span class="s">* </span>領料單位：</label></td>
								<td>
									<div class="col-sm-12">
										<select id="prDeptSelect" name="prDeptSelect">
											<option value="" selected>--請選擇--</option>
											<c:forEach items="${deptSelectAll}" var="dept">
												<option value="${dept.DEPT_ID}">${dept.DEPT_NAME}</option>
											</c:forEach>
										</select>
									</div>
								</td>
								<td><label class="col-sm-12 control-label"><span class="s">* </span>發料單位：</label></td>
								<td>
									<div class="col-sm-12">
										<select id="msDeptSelect" name="msDeptSelect" disabled>
											<option value="" selected>--請選擇--</option>
											<c:forEach items="${deptSelect}" var="dept">
												<c:choose>
														<c:when test="${deptId eq dept.DEPT_ID}">
															<option value="${dept.DEPT_ID}" selected>${dept.DEPT_NAME}</option>
														</c:when>
														<c:otherwise>
															<option value="${dept.DEPT_ID}">${dept.DEPT_NAME}</option>
														</c:otherwise>
													</c:choose>
											</c:forEach>
										</select>
									</div>
								</td>
								<td><label class="col-sm-12 control-label">發料倉庫：   ${wareHouse}</label></td>
								<td><div class="col-sm-6"></div></td>	
							</tr>
							<tr>
								<td><div class="clearfix">&nbsp;</div></td>
							</tr>
							<tr>
								<td><label class="col-sm-12 control-label"><span class="s">* </span>領料人員：</label></td>
								<td>
									<div class="col-sm-12">
										<select id="prPersonnelSelect" name="prPersonnelSelect">
											<option value="" selected>--請選擇--</option>
											
										</select>
									</div>
								</td>
								<td><label class="col-sm-12 control-label"><span class="s">* </span>發料人員：</label></td>
								<td>
									<div class="col-sm-12">
										<select id="msPersonnelSelect" name="msPersonnelSelect" disabled>
											<!-- <option value="" selected>--請選擇--</option> -->
											<option value="${loginUserId}" selected >${loginUserName}</option>
										</select>
									</div>
								</td>
								<td></td>
								<td></td>
							</tr>
						</table>
						
						<div class="clearfix">&nbsp;</div>
						
						
							<div id="ajaxSearchResult" class="col-xs-12" style="padding-left:50px;width: 1000px;">
								<!-- 固定id for window.resize start-->
								<div id="jQgrid" align="center">
									<table id="mastergrid"></table>
									<div id="masterpager"></div>
								</div>
								<!-- 固定id for window.resize end-->
							</div>
						
						
						<div class="clearfix">&nbsp;</div>
						
						
							<div id="ajaxSearchResult" class="col-xs-12" style="padding-left:50px;width: 1000px;">
								<!-- 固定id for window.resize start-->
								<div id="jQgrid" align="center">
									<table id="dtlgrid"></table>
									<div id="dtlpager"></div>
								</div>
								<!-- 固定id for window.resize end-->
							</div>
						
					</form>
					
					<input type="hidden" id="callBackFun" name="callBackFun" value="showMsData" />
				</div>
			</div>
		</div>
		
		<div id="showMsPag" hidden="true"></div>		
		<div id="showDeptPosPag" >
			<div id="selectMaterialPage"></div>
		</div>
	</div>
</body>
</html>