<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>收料</title>
<script src="<s:url value="/resources/plugins/tinymce/tinymce.min.js" />"></script>
<script src="<s:url value="/resources/plugins/tinymce/jquery.tinymce.min.js" />"></script>
<script	src="<s:url value="/resources/js/validate.js" />"></script>
	<script type="text/javascript">
	
		$(document).ready(function() {			
			//import Select2
			LoadSelect2Script('<s:url value="/resources/plugins/select2/select2.min.js" />',mountSelect2);
			//掛載表格
			mountGrid();
			//掛載按鈕事件
			mountBtnEvent();	
					
		});
		
		
		//掛載表格
		function mountGrid(){
			$("#detailGridBy015M").jqGrid({
				datatype : "local",
				pager : '#detailGridBy015Mpager',
				autowidth:'true',
				rowNum : 1000,
				url : CONTEXT_URL + "/inv/inv015/searchCallInDetail",
				colNames : [ '退料單號', '料號','品名','廠商序號','貼標號碼','退料狀態','原因','預計收料數', '已收數', '實際收料狀態', '原因', '收料數','','','','','','',''],
				colModel : [ {name : 'opt_ID',index : 'opt_ID',align : 'center',sortable : false}, 
				             {name : 'mat_NO',index : 'mat_NO',align : 'center',sortable : false}, 
				             {name : 'matName',index : 'matName',align : 'center',sortable : false}, 
				             {name : 'venSn',index : 'venSn',align : 'center',sortable : false}, 
				             {name : 'tagNo',index : 'tagNo',align : 'center',sortable : false}, 
				             {name : 'matStatusDscr',index : 'matStatusDscr',align : 'center',sortable : false} ,
				             {name : 'rnResnDscr',index : 'rnResnDscr',align : 'center',sortable : false},
				             {name : 'qty',index : 'qty',align : 'center',sortable : false}, 
				             {name : 'txnQty',index : 'txnQty',align : 'center',sortable : false}, 
				             {name : 'matStatusSelect',index : 'matStatusSelect',editable: true,edittype:"select"}, 
				             {name : 'INV_RN_RESN',index : 'INV_RN_RESN',align : 'center',sortable : false, editable: true,edittype:"select"} ,
				             {name : 'minusQty',index : 'minusQty',align : 'center',sortable : false, editable: true,edittype:"text",editoptions:{size:"10",maxlength:"10"}},
				             {name : 'mat_STATUS',index : 'mat_STATUS',align : 'center',sortable : false,hidden:true},
				             {name : 'whId',index : 'whId',align : 'center',sortable : false,hidden:true},
				             {name : 'faNo',index : 'faNo',align : 'center',sortable : false,hidden:true},
				             {name : 'dtl_SEQ',index : 'dtl_SEQ',align : 'center',sortable : false,hidden:true},
				             {name : 'tbInvMaterialOpt.site_ID',index : 'tbInvMaterialOpt.site_ID',align : 'center',sortable : false,hidden:true},
				             {name : 'srl_NO',index : 'srl_NO',align : 'center',sortable : false,hidden:true},
				             {name : 'tbInvMaterialOpt.order_ID',index : 'tbInvMaterialOpt.order_ID',align : 'center',sortable : false,hidden:true}   
				             ],
				caption : "收料清單",
				rownumbers : true,
				multiselect : true,
				onSelectRow : function(id) {
				var rowData = jQuery(this).getRowData(id);
				var data = {
						optId:rowData.opt_ID,
						matNo:rowData.mat_NO,
						dtlSeq:rowData.dtl_SEQ
					};
				 $("#historyGridBy015M").setGridParam({datatype:"json", postData:data, mtype: 'POST',page:1}).jqGrid().trigger("reloadGrid");
				},
				gridComplete : function() {
					var ids = jQuery("#detailGridBy015M").jqGrid('getDataIDs');
						for (var i = 0; i < ids.length; i++) 
						{
						    var rowId = ids[i];
						    var rowData = jQuery('#detailGridBy015M').jqGrid ('getRowData', rowId);
						    if(rowData.qty==rowData.txnQty){
						    		var qtyCnt=i;
								var qtyCheckWord = "#jqg_detailGridBy015M_"+(qtyCnt+1);
								$(qtyCheckWord).hide();//設定隱藏  
							}
							var qty=parseInt(rowData.qty);	
							var txnQty=parseInt(rowData.txnQty);	
							if(qty>txnQty){
								$('#detailGridBy015M').jqGrid('setColProp', 'matStatusSelect', { editoptions: { value: getMatStatus('MAT_STATUS')}});
								$('#detailGridBy015M').jqGrid('setColProp', 'INV_RN_RESN', { editoptions: { value: getMatStatus('INV_RN_RESN')}});
							 	jQuery('#detailGridBy015M').jqGrid('editRow',rowId,true);//如果數量不同才可以修改
							 }
						}
					$("#cb_detailGridBy015M").hide();//設定隱藏	
				}
			});
			$("#historyGridBy015M").jqGrid({
				datatype : "local",
				pager : '#historyGridBy015Mpager',
				autowidth:'true',
				url : CONTEXT_URL + "/inv/inv015/searchDtlHistory",
				colNames : [ '收料單號', '收料日期','收料人員','收料數'],
				colModel : [ {name : 'txn_no',index : 'txn_no',align : 'center',sortable : false}, 
				             {name : 'cr_time',index : 'cr_time',align : 'center',sortable : false,formatter: function(cellvalue, options, rowObject){
					   			if (cellvalue != null && cellvalue != "") {
						   			var stDate = new Date(cellvalue);
									return stDate.getFullYear() + "/" + (stDate.getMonth() + 1) + "/" + stDate.getDate()+" " + addZero(stDate.getHours()) +":"+addZero(stDate.getMinutes());
					   			}else {
					   				return "";
					   			}}}, 
				             {name : 'crUser',index : 'crUser',align : 'center',sortable : false}, 
				             {name : 'qty',index : 'qty',align : 'center',sortable : false,formatter: nullForZero}
				             ],
				caption : "收料歷程",
				rownumbers : true,
				onSelectRow : function() {
				},
				gridComplete : function() {
				},
				ondblClickRow: function(rowId) { 
		        }
			});
			$(window).resize(function() {
				$(window).unbind("onresize");
				$("#detailGridBy015M,.grid").setGridWidth($("#jQgrid").width() - 10);
				$("#historyGridBy015M,.grid").setGridWidth($("#jQgrid").width() - 10);
				$(window).bind("onresize", this);
			}).trigger('resize');
			var gridData = {
						optId:$("#optIdList").val(),
						whId:$("#whId").val(),
						workType:$("#workType").val()
					};
			$("#detailGridBy015M").setGridParam({datatype : "json",postData : gridData,mtype : 'POST',page : 1}).jqGrid().trigger("reloadGrid");
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
		//掛載按鈕事件
		function mountBtnEvent(){
			//查詢按鈕
			$('#callInButton').click(function(){
				var returnApplicant = $("#returnApplicant").find("option:selected").val();//退料
				var callInApplicant = $("#callInApplicant").find("option:selected").val();//收料
				if(returnApplicant == ""){
					alert("請選擇退料人員");
					return false;
				}
				if(callInApplicant == ""){
					alert("請選擇收料人員");
					return false;
				}
				var selr = jQuery('#detailGridBy015M').jqGrid('getGridParam','selarrrow');
				if(selr.length==0){
							alert("執行收料前，請先勾選一筆資料");
							return false;
				}else{
					if(msCheck(selr))//檢核
						inDept(selr);//收料function
				}
			});
		}
		//產生TxnNo
		function getTxnNo() {
			var txnNo = "";
			var data = {};
			$.ajax({
				url : CONTEXT_URL + "/inv/inv015/genTxnNo/",
				data : data,
				type : "POST",
				dataType : 'json',
				async : false,
				success : function(data) {
					if (data.success) {
						txnNo = data.msg
					}
				}
			});
			return txnNo;
		}
		//收料檢核
	function msCheck(selr) {
		for (var i = 0; i < selr.length; i++) {
			var rsnCnt=i;//為了取得下拉選單文字
			var matStatus = "#"+selr[i]+"_matStatusSelect";
			var rsn = "#"+selr[i]+"_INV_RN_RESN";
			var makeMinusQty = "#"+selr[i]+"_minusQty";//收料數
			matStatus=$(matStatus).find("option:selected").val();
			rsn=$(rsn).find("option:selected").val();
			var minusQty=$(makeMinusQty).val();
			var rowData = jQuery('#detailGridBy015M').jqGrid('getRowData',selr[i]);
			if (minusQty > (rowData.qty - rowData.txnQty)) {
				alert("退料單號:" + rowData.opt_ID+",料號:"+rowData.mat_NO+" 收料數大於(預計收料數-已收數),無法收料請重新選擇!!");
				return false;
			}	
			/* if (rowData.qty == rowData.txnQty) {
				alert("退料單號:" + rowData.opt_ID+",料號:"+rowData.mat_NO + " 預計收料數=收料數，請勿選擇此筆資料");
				return false;
			} */
			if(!isAllNumber(minusQty)){			
				alert("收料數需為正數，不得為負數或其他文字");
				return false;
			}		
			if(parseInt(minusQty)<=0){
				alert("收料數不得輸入小於0或總和為0，請重新輸入");
				return false;
			}				
			//收料數檢核
			if(minusQty == "0" || minusQty == "" ){
				alert("退料單號:" + rowData.opt_ID+",料號:"+rowData.mat_NO +" 請輸入收料數且不得為0");
				return false;
			}
			if(!isAllNumber(minusQty)){			
				alert("退料單號:" + rowData.opt_ID+",料號:"+rowData.mat_NO +" 收料數只能輸入數字");
				return false;
			}
			if(matStatus != rowData.mat_STATUS){
				if(rsn==""){
					alert("退料單號:" + rowData.opt_ID+",料號:"+rowData.mat_NO +"實際收料狀態與退料狀態不同，請輸入原因");
					return false;
				}	
			}
		}
		return true;
	}
		//收料動作
		function inDept(selr) {
			var txnNo="";//getTxnNo(); 
			var crTime=getCrTime();
			var checkDept="";
			var optIdList="";
			var returnApplicant = $("#returnApplicant").find("option:selected").val();//退料
			var callInApplicant = $("#callInApplicant").find("option:selected").val();//收料
			var msArray =[];
				for (var i = 0; i < selr.length; i++) {
					var rsnCnt=i;//為了取得下拉選單文字
					var matStatus = "#"+selr[i]+"_matStatusSelect";
					var rsn = "#"+selr[i]+"_INV_RN_RESN";
					var makeMinusQty = "#"+selr[i]+"_minusQty";//收料數
					matStatus=$(matStatus).find("option:selected").val();
					rsn=$(rsn).find("option:selected").val();
					var minusQty=$(makeMinusQty).val();
					var rowData = jQuery('#detailGridBy015M').jqGrid('getRowData',
							selr[i]);
					var qty=parseInt(rowData.qty);	
					var txnQty=parseInt(rowData.txnQty);
					$.log("minusQty: "+minusQty);	
					if(qty>txnQty){//如果收料數小於實際收料數 才執行更新
						//txnQty 已收數 qty預收數
						/* updateInvInv(rowData.mat_NO,rowData.qty,rowData.whId,matStatus);//更新Booking檔＆INV檔
						insertTxn(txnNo,rowData.opt_ID,rowData.mat_NO,matStatus,minusQty, 
						rowData.whId,rowData.tagNo,rowData.faNo,rowData.srl_NO,rsn,
						rowData.dtl_SEQ,rowData["tbInvMaterialOpt.site_ID"],rowData["tbInvMaterialOpt.order_ID"],crTime,
						returnApplicant,callInApplicant);//寫入TXN檔 
						updateOnHand(rowData.opt_ID,rowData.mat_NO, minusQty, rowData["tbInvMaterialOpt.order_ID"],crTime);//更新ONHAND檔 
						checkDept="Y";  */
						var msObj ={
							"optId" : rowData.opt_ID,//領料單號
							"matNo" : rowData.mat_NO,//料號
							"qty"  : rowData.qty,//申請數
							"whId" : rowData.whId,//倉庫
							"txnNo" : txnNo,//
							"matStatus" : matStatus,//料號狀態
							"minusQty" : minusQty,//發料數
							"tagNo" : rowData.tagNo,
							"faNo" : rowData.faNo,//
							"srlNo" :rowData.srl_NO,//
							"rsn" : rsn,//原因
							"dtlSeq" : rowData.dtl_SEQ,//
							"siteId" : rowData["tbInvMaterialOpt.site_ID"],//貼標號碼
							"orderId" : rowData["tbInvMaterialOpt.order_ID"],
							"rePerson" : returnApplicant,//退料人員
							"callPerson" : callInApplicant//收料人員
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
				var data ={
					msArray : JSON.stringify(msArray)
				}
				 $.ajax({
					url : CONTEXT_URL + "/inv/inv015/saveToTable/",
					data : data,
					type : "POST",
					dataType : 'json',
					async:false,
					success : function(data) {
						if(data.success){
							//updateOpt(optIdList);//檢查申請數
							openSp1(data.msg);//收料單下載 data.msg=txnNo
						}else{
							alert("錯誤 訊息: " + data.msg);
							parent.$.fancybox.close();//關閉視窗
						}
					}
				});	
				/* if(checkDept=="Y"){
					updateOpt(optIdList);//檢查申請數
					openSp1(txnNo);//收料單下載
				} */
		}
		
		//更新OPT狀態
	function updateOpt(optId) {
		var data = {
			optId : optId
		}
		$.ajax({
			url : CONTEXT_URL + "/inv/inv015/updateOpt/",
			data : data,
			type : "POST",
			dataType : 'json',
			async : false,
			success : function(data) {
				if (data.success) {

				}
			}
		});
		return true;
	}
	
	//更新INV檔
	function updateInvInv(matNo, qty, whId, matStatus) {
		var invInvData = {
			matNo : matNo,
			qty : qty,
			whId : whId,
			matStatus : matStatus
		}
		$.ajax({
			url : CONTEXT_URL + "/inv/inv015/updateInvInvMinusQty/",
			data : invInvData,
			type : "POST",
			dataType : 'json',
			async : false,
			success : function(data) {
				if (data.success) {
					txnNo = data.msg
				}
			}
		});
	}
	//寫入TXN檔
	function insertTxn(txnNo, optId, matNo, matStatus, qty, whId, tagNo, faNo, srlNo,rnResn,
	dtlSeq,siteId,orderNo,crTime,returnApplicant,callInApplicant) {
		var insertTxndata = {
			txnNo : txnNo,
			optId : optId,
			matNo : matNo,
			matStatus : matStatus,
			qty : qty,
			whId : whId,
			tagNo : tagNo,
			faNo : faNo,
			srlNo : srlNo,
			rnResn : rnResn,
			dtlSeq : dtlSeq,
			siteId : siteId,
			orderNo : orderNo,
			crTime : crTime,
			rtUser : returnApplicant,
			rcvUser : callInApplicant
		};
		$.ajax({
			url : CONTEXT_URL + "/inv/inv015/insertTxn/",
			data : insertTxndata,
			type : "POST",
			dataType : 'json',
			async : false,
			success : function(data) {
				if (data.success) {
					txnNo = data.msg
				}
			}
		});
	}
	//寫入ONHAND檔
	function updateOnHand(optId, matNo, qty, orderNo,crTime) {
		var updateOnHanddata = {
			optId : optId,
			matNo : matNo,
			qty : qty,
			orderNo : orderNo,
			crTime : crTime
		};
		$.ajax({
			url : CONTEXT_URL + "/inv/inv015/updateOnHand/",
			data : updateOnHanddata,
			type : "POST",
			dataType : 'json',
			async : false,
			success : function(data) {
				if (data.success) {
					txnNo = data.msg
				}
			}
		});
	}
		//取得系統時間
		function getCrTime(){
		var crTime = "";
		$.ajax({			
			url : CONTEXT_URL+"/ajax/inv/public/getToday/",		
			data:crTime,
			type : "POST",
			dataType : 'json',
			async: false,
			success : function(data) {
				if(data.success){
					crTime=data.msg
				}
			}
		});
			return crTime;			
		}
		//掛載申請人
		function mountApplicant(e, id) {
			var data = {
				dept : e
			};
			$.ajax({
				url : CONTEXT_URL + "/ajax/inv/public/personnelDept/",
				data : data,
				type : "POST",
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						if (data.rows.length > 0) {
							$("#" + id + " option").remove();
							$("#" + id).append(
									"<option value=''>--請選擇--</option>");
							for (var i = 0; i < data.rows.length; i++) {
								$("#" + id).append(
										"<option value="+data.rows[i].psn_NO+">"
												+ data.rows[i].chn_NM
												+ "</option>");
							}
							$("#" + id).select2("val", "");
						}
					}
				}
			});
		}
		function nullForZero(cellvalue, options, rowObject) {
			var returnValue = cellvalue;
			if (cellvalue == "" || cellvalue == "undefined"
					|| cellvalue == null) {
				returnValue = "0";
			}
			return returnValue;
		}
		//取得grid內mat_status 選單內容
		function getMatStatus(stauts1) {
			var returnValue="";
			var data = {
				status : stauts1
			};
			$.ajax({
				url : CONTEXT_URL + "/inv/inv015/lookupByType",
				data : data,
				type : "POST",
				dataType : 'json',
				async : false,
				success : function(data) {
					if (data.success) {
						for (i = 0; i < data.rows.length; i++) {
							if (i == 0 && stauts1 != 'MAT_STATUS') {
								returnValue += "" + ":" + "--請選擇--" + ";";
							}
							if (i < data.rows.length - 1) {
								returnValue += data.rows[i].code + ":"
										+ data.rows[i].name + ";";
							} else {
								returnValue += data.rows[i].code + ":"
										+ data.rows[i].name;
							}
						}
					}
				}
			});
			return returnValue;
		}
		//開啟發料單畫面
		function openSp1(txnNo) {
			window.location.href = CONTEXT_URL+"/inv/inv015L/printReceipt?txnNo="+txnNo
			parent.$.fancybox.close();
		}
		function gridDoubleClick(dataObj) {
			var data = {
				optId : dataObj.optId,
				whId : dataObj.wh_ID
			};
			//查詢dtl資料
			$("#historyGridBy015M").setGridParam({
				url : CONTEXT_URL + "/inv/inv015/searchDtlHistory",
				datatype : "json",
				postData : data,page:1
			}).trigger("reloadGrid");

		}
		//掛載 Select2
		function mountSelect2() {
			$('#returnDepartment').select2();
			$('#callInDepartment').select2();
			$('#returnApplicant').select2();
			$('#callInApplicant').select2();
		}
		$('#returnDepartment').on('change', function(e) {
				mountApplicant(e.val, "returnApplicant");
			});
			$('#callInDepartment').on('change', function(e) {
				mountApplicant(e.val, "callInApplicant");
			});
		$('#returnDepartment').on('change', function(e) {
			mountApplicant(e.val, "returnApplicant");
		});
		$('#callInDepartment').on('change', function(e) {
			mountApplicant(e.val, "callInApplicant");
		});
		function addZero(i) {
			if (i < 10) {
				i = "0" + i;
			}
			return i;
		}
	</script>
</head>

<body>
	
	<!-- Table Header -->
	<div class="box-header">
		<div class="box-name">
			<i class="fa fa-search"></i>退料收料作業功能 收料作業
		</div>
		<div class="no-move"></div>
	</div>
	
	<div class="row">
		<div id="breadcrumb" class="col-md-12">
			<div class="col-sm-12" id="btnDiv">	
				<ol class="breadcrumb">
					<button class="btn btn-primary btn-label-left" type="button"
						id="callInButton" >
						<span>
							<i class="fa fa-save"></i>
						</span> 
						執行收料
					</button>
				</ol>
			</div>
		</div>
	</div>
	
	<div class="row">
		<div class="box-content">
			<form role="form" class="form-horizontal bootstrap-validator-form"
				novalidate="novalidate" id="searchForm">
				<input hidden="true" value="${optId}" id="optIdList">
				<input hidden="true" value="${whId}" id="whId">
				<input hidden="true" value="${workType}" id="workType">
				<div class="form-group">
					<table style="width: 95%">
						<tr>
							<td class="col-sm-2 control-label"><label><span class="s">* </span>退料單位 :</label></td>
							<td class="col-sm-3">
								<select id="returnDepartment">
									<option value="">--請選擇--</option>
									<c:forEach items="${departmentAll}" var="dept">
										<option value="${dept.DEPT_ID}">${dept.DEPT_NAME}</option>
									</c:forEach>
								</select>
							</td>
							<td class="col-sm-2 control-label"><label><span class="s">* </span>收料單位 :</label></td>
							<td class="col-sm-2">
								<select id="callInDepartment" disabled>
									<option value="">--請選擇--</option>
									<c:forEach items="${department}" var="dept">
										<%-- <option value="${dept.DEPT_ID}">${dept.DEPT_NAME}</option> --%>
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
							</td>
							<td class="col-sm-2 control-label"><label>收料倉庫：</label></td>
							<td class="col-sm-2"><label>${whName}</label></td>
						</tr>
						<tr>
							<td><div class="clearfix">&nbsp;</div></td>
						</tr>
						<tr>
							<td class="col-sm-2 control-label"><label><span class="s">* </span>退料人員 :</label></td>
							<td class="col-sm-2">
								<select id="returnApplicant">
									<option value="">--請選擇--</option>
								</select>
							</td>
							<td class="col-sm-2 control-label"><label><span class="s">* </span>收料人員 :</label></td>
							<td class="col-sm-2">
								<select id="callInApplicant" disabled>
									<!-- <option value="">--請選擇--</option> -->
									<option value="${loginUserId}" selected >${loginUserName}</option>
								</select>
							</td>
							<td class="col-sm-2 control-label"/>
							<td class="col-sm-2"/>
						</tr>
					</table>
				</div>
			</form>
		</div>

		<div id="ajaxSearchResult" class="col-xs-12" style="padding-left:150px;width: 1000px;">
			<div id="jQgrid"  align="center">
				<table id="detailGridBy015M"></table>
				<div id="detailGridBy015Mpager"></div>
			</div>
		</div>

		<div class="clearfix">&nbsp;</div>

		<div id="ajaxSearchResult" class="col-xs-12" style="padding-left:150px;width: 1000px;">
			<div id="jQgrid"  align="center">
				<table id="historyGridBy015M"></table>
				<div id="historyGridBy015Mpager"></div>
			</div>
		</div>
	</div>

</body>
</html>