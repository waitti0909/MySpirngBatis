<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<title>調撥申請作業</title>
		<script	src="<s:url value="/resources/js/validate.js" />"></script>
		<script type="text/javascript">			
			// Load script of Select2 and run this	
			$(document).ready(function() {
			LoadSelect2Script("<s:url value="/resources/plugins/select2/select2.min.js" />", selectDefault);
			mountGrid();
				});
					//設定需求日預設日期
					prepareDay();
					
				$('#btn_search').click(function() {
						var trNo = $.trim($('#trNo').val());
						var statusSelect = $("#statusSelect").val();
						var wareHouseOutSelect = $("#wareHouseOutSelect").val();
						var wareHouseInSelect = $("#wareHouseInSelect").val();
						var needStartDate = $("#needStartDate").val();
						var needEndDate = plusOneDay($("#needEndDate").val());
						var deptNeedSelect = $("#deptNeedSelect").val();
						var applyStartDate = $("#applyStartDate").val();
						var applyEndDate = plusOneDay($("#applyEndDate").val());
						var deptApplySelect = $("#deptApplySelect").val();
						var troutStartDate = $("#troutStartDate").val();
						var troutEndDate = plusOneDay($("#troutEndDate").val());
						var personnelSelect = $("#personnelSelect").val();
						var trinStartDate = $("#trinStartDate").val();
						var trinEndDate = plusOneDay($("#trinEndDate").val());
						var domain =$("#domainSelect").find("option:selected").val();
						/* var checkOutWareHouse ="";
						if($("#checkOutWareHouse").prop("checked"))
						 checkOutWareHouse = "1";
						else
						 checkOutWareHouse = "0"; */
						 
					/* if((needStartDate == null || needStartDate == "")){
						alert('需求日期開始日不得為空值');
						return false;
					}
					if((needEndDate == null || needEndDate == "")){
						alert('需求日期結束日不得為空值');
						return false;
					} */
					if((needStartDate != null || needStartDate != "") && (needEndDate != null || needEndDate != "")){
						dateCheck(needStartDate,needEndDate,"需求日");
					}
					if((applyStartDate != null || applyStartDate != "") && (applyEndDate != null || applyEndDate != "")){
						dateCheck(applyStartDate,applyEndDate,"申請日");
					}
					if((troutStartDate != null || troutStartDate != "") && (troutEndDate != null || troutEndDate != "")){
						dateCheck(troutStartDate,troutEndDate,"調出日");
					}
					if((trinStartDate != null || trinStartDate != "") && (trinEndDate != null || trinEndDate != "")){
						dateCheck(trinStartDate,trinEndDate,"調入日");
					}
					/* if((wareHouseOutSelect == null || wareHouseOutSelect == "")){
						alert('請選擇調出倉庫');
						return false;
					}
					if((wareHouseInSelect == null || wareHouseInSelect == "")){
						alert('請選擇調入倉庫');
						return false;
					} */
					if (domain == "") {
						alert('區域不得為空值');
						return false;
					}
					
					var data = {
						trNo : trNo,
						statusSelect : statusSelect,
						wareHouseOutSelect : wareHouseOutSelect,
						wareHouseInSelect : wareHouseInSelect,
						needStartDate : needStartDate,
						needEndDate : needEndDate,
						deptNeedSelect : deptNeedSelect,
						applyStartDate : applyStartDate,
						applyEndDate : applyEndDate,
						deptApplySelect : deptApplySelect,
						troutStartDate : troutStartDate,
						troutEndDate : troutEndDate,
						personnelSelect : personnelSelect,
  					    trinStartDate : trinStartDate,
						trinEndDate : trinEndDate,
						domain : domain
						/* checkOutWareHouse : checkOutWareHouse	 */				
					};
					mountGrid();
					$("#grid").setGridParam({datatype:"json", postData:data,page:1}).trigger("reloadGrid");
				});	
				function mountGrid(){
				$("#grid").jqGrid({
											datatype : "local",
											pager : '#pager',
											autowidth:'true',
											url : CONTEXT_URL + "/inv/inv007/Search/",
											colNames : [ '進度', '單號', '調出倉', '調入倉','需求單位','需求日期','','','','' ],
											colModel : [ 
											    {name : 'statusDscr',index : 'statusDscr',width : 40 , align : 'center',sortable : false},
											    {name : 'tr_no',index : 'tr_no',width : 40 , align : 'center',sortable : false}, 
											    {name : 'trOutWhIdDscr',index : 'trOutWhIdDscr',width : 40, align : 'center',sortable : false},
											    {name : 'trInWhIdDscr',index : 'trInWhIdDscr',width : 40 , align : 'center',sortable : false}, 
											    {name : 'needDeptIdDscr',index : 'needDeptIdDscr',width : 40 , align : 'center',sortable : false},
											    {name : 'need_date',index : 'need_date',sortable : false,formatter: function(cellvalue, options, rowObject){
								   			if (cellvalue != null && cellvalue != "") {
									   			var stDate = new Date(cellvalue);
												return stDate.getFullYear() + "/" + (stDate.getMonth() + 1) + "/" + stDate.getDate() 
												+ " " + addZero(stDate.getHours()) +":"+addZero(stDate.getMinutes());
								   			}else {
								   				return "";
								   			}}},
								   				{name : 'status',index : 'status',width : 40 , align : 'center',sortable : false,hidden : true},
								   				{name : 'tr_out_wh_id',index : 'tr_out_wh_id',width : 40, align : 'center',sortable : false,hidden:true},
											    {name : 'tr_in_wh_id',index : 'tr_in_wh_id',width : 40 , align : 'center',sortable : false,hidden:true},
											    {name : 'need_dept_id',index : 'need_dept_id',width : 40 , align : 'center',sortable : false,hidden:true}
											    ],
											multiselect : true,
											caption : "調撥資料",
											rownumbers : true,
											onSelectRow : function() {
												// do something when select a row
											},
											gridComplete : function() {
												
											},
											ondblClickRow: function(rowId) { //double clicking a jqgrid row
									            var rowData = jQuery(this).getRowData(rowId);
									           	gridDtl(rowData.tr_no);									         
									        }
				});//end grid	
				}
				$(window).resize(function() {
							$(window).unbind("onresize");
							$("#grid,.grid").setGridWidth($("#jQgrid").width() - 10);
							$(window).bind("onresize", this);
					}).trigger('resize');				
				//修改
				$('#btn_edit').click(function() {
					var selr = jQuery('#grid').jqGrid('getGridParam','selarrrow');
							if(selr.length>1){
								alert("一次僅能修改一筆資料");
								return false;
							}
							if(selr.length==0){
								alert("修改資料前，請先勾選一筆資料");
								return false;
							}else{
								for(var i=0;i<selr.length;i++)
								{
									var myrow = jQuery('#grid').jqGrid('getRowData',selr[i]);
									var tr_no=myrow.tr_no;	
									var tr_out_wh_id=myrow.tr_out_wh_id;		
									if(myrow.status != "1" && myrow.status != "10" ){
										alert("狀態為『草稿』或『駁回』時，才提供修改");
										return false;
									}
								}
								gridFixData(tr_no,tr_out_wh_id);
							}
				});
				//刪除
				$('#btn_delete').click(function() {
					var selr = jQuery('#grid').jqGrid('getGridParam','selarrrow');
							if(selr.length==0){
								alert("刪除資料前，請先勾選一筆資料");
								return false;
							}else{
								for(var i=0;i<selr.length;i++)
								{
									var myrow = jQuery('#grid').jqGrid('getRowData',selr[i]);
									var tr_no=myrow.tr_no;
									if(myrow.status != "1" && myrow.status != "10" ){
										alert("單號:"+myrow.tr_no+"狀態為『草稿』或『駁回』時，才提供刪除");
										return false;
									}else{									
										$('#dtlgrid').jqGrid('delRowData',selr[i]);	
										var data = {
											trNo : tr_no
										};
										$.ajax({			
											url : CONTEXT_URL+"/inv/inv007/delMasData/",		
											data:data,
											type : "POST",
											dataType : 'json',
											success : function(data) {
												if(data.success){
													selectData();
												}
											},
											error : function(data){
													alert(data.msg);
											}
										});	
									}	
								}
							}
				});
				//批次新增
				$('#btn_batch_add').click(function() {
					$.fancybox.open({
										href : CONTEXT_URL + "/inv/inv007/batchAdd",
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
										title : "批次新增資料",
										openEffect : 'elastic',
										closeEffect : 'fade',
										afterClose : function() {
											//selectData();
										}
									});
				});
				//新增
				$('#btn_add').click(function() {
				//取代原頁面
					//LoadAjaxContent(CONTEXT_URL + '/inv007/add');
					$.fancybox.open({
										href : CONTEXT_URL + "/inv/inv007/add",
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
										title : "新增資料",
										openEffect : 'elastic',
										closeEffect : 'fade',										
										afterClose : function() {
										}
									});
				});

				//重置按鈕
				$('#btn_reset').click(function() {
					$("#searchFrom").trigger('reset');
					$("#needStartDate").prop("value","");
					$("#needEndDate").prop("value","");
					$("#applyStartDate").prop("value","");
					$("#applyEndDate").prop("value","");
					$("#troutStartDate").prop("value","");
					$("#troutEndDate").prop("value","");
					$("#trinStartDate").prop("value","");
					$("#trinEndDate").prop("value","");
					$("#wareHouseOutSelect").select2("val", "");
					$("#wareHouseInSelect").select2("val", "");
					$("#deptNeedSelect").select2("val", "");
					$("#deptApplySelect").select2("val", "");
					$("#statusSelect").select2("val", "");
				    $("#personnelSelect").html("");
					$("<option value=''>--請選擇--</option>").appendTo(
							"#personnelSelect");
					$("#personnelSelect").select2();
				    /* $("#checkOutWareHouse").prop("checked",false).prop("disabled","disabled"); */
				     prepareDay();
					$("#grid").jqGrid().clearGridData();
				});	
				
				//調撥進度控制返回倉勾選
				/* $("#statusSelect").change(function(){
				  if($(this).val()=="7") {
				  	$("#checkOutWareHouse").prop("disabled",false);
				  } else{
				  	$("#checkOutWareHouse").prop("disabled",true).prop("checked",false);
				  } 
				}); */
				//申請部門連動申請人
				$("#deptApplySelect").change(onDeptSelectChange);
				//區域聯動調出入倉，需求/申請單位
				$("#domainSelect").change(onDomainSelectChange);
//=========Function=============
				function plusOneDay(oriDate){
					if(oriDate != ""){
						var newdate = new Date(oriDate);
						newdate.setDate(newdate.getDate() +1);
						return newdate.getFullYear()+ "/" + (newdate.getMonth()+1) + "/" + (newdate.getDate());
					}else{
						return "";
					}
				}
				function addZero(i) {
				    if (i < 10) {
				        i = "0" + i;
				    }
				    return i;
				}		
				function prepareDay(){
					var Today=new Date();
					var TodayLess=(new Date());
					TodayLess.setDate(TodayLess.getDate()-30);
					$('#applyStartDate').prop("value",TodayLess.getFullYear()+ "/" + (TodayLess.getMonth()+1) + "/" + (TodayLess.getDate()));
					$('#applyEndDate').prop("value",Today.getFullYear()+ "/" + (Today.getMonth()+1) + "/" + (Today.getDate()));
					//需求日起始日曆						
						$('#needStartDate').datepicker({
							dateFormat : "yy/mm/dd",
							changeYear : true,
							changeMonth : true,
							showButtonPanel : true,
							showTime : false
						});
						//需求日結束日曆
						$('#needEndDate').datepicker({
							dateFormat : "yy/mm/dd",
							changeYear : true,
							changeMonth : true,
							showButtonPanel : true,
							showTime : false
						});
						//申請日起始日曆						
						$('#applyStartDate').datepicker({
							dateFormat : "yy/mm/dd",
							changeYear : true,
							changeMonth : true,
							showButtonPanel : true,
							showTime : false
						});
						//申請日結束日曆
						$('#applyEndDate').datepicker({
							dateFormat : "yy/mm/dd",
							changeYear : true,
							changeMonth : true,
							showButtonPanel : true,
							showTime : false,
							showTime : false
						});
						//調出日起始日曆	
						$('#troutStartDate').datepicker({
							dateFormat : "yy/mm/dd",
							changeYear : true,
							changeMonth : true,
							showButtonPanel : true,
							showTime : false
						});
						//調出日結束日曆
						$('#troutEndDate').datepicker({
							dateFormat : "yy/mm/dd",
							changeYear : true,
							changeMonth : true,
							showButtonPanel : true,
							showTime : false
						});	
						//調入日起始日曆						
						$('#trinStartDate').datepicker({
							dateFormat : "yy/mm/dd",
							changeYear : true,
							changeMonth : true,
							showButtonPanel : true,
							showTime : false
						});
						//調入日結束日曆
						$('#trinEndDate').datepicker({
							dateFormat : "yy/mm/dd",
							changeYear : true,
							changeMonth : true,
							showButtonPanel : true,
							showTime : false
						});
				}	
				//日期格式化，只取年月日
				function dayFormat(cellvalue){
					if(cellvalue==""||cellvalue==null){
			            return "";
			        }else{
			            return cellvalue.substr(0,cellvalue.indexOf(" ")); ;
			        } 
			    }
			    function selectData(){
			    		var trNo = $.trim($('#trNo').val());
						var statusSelect = $("#statusSelect").val();
						var wareHouseOutSelect = $("#wareHouseOutSelect").val();
						var wareHouseInSelect = $("#wareHouseInSelect").val();
						var needStartDate = $("#needStartDate").val();
						var needEndDate = plusOneDay($("#needEndDate").val());
						var deptNeedSelect = $("#deptNeedSelect").val();
						var applyStartDate = $("#applyStartDate").val();
						var applyEndDate = plusOneDay($("#applyEndDate").val());
						var deptApplySelect = $("#deptApplySelect").val();
						var troutStartDate = $("#troutStartDate").val();
						var troutEndDate = plusOneDay($("#troutEndDate").val());
						var personnelSelect = $("#personnelSelect").val();
						var trinStartDate = $("#trinStartDate").val();
						var trinEndDate = plusOneDay($("#trinEndDate").val());
						/* var checkOutWareHouse ="";
						if($("#checkOutWareHouse").prop("checked"))
						 checkOutWareHouse = "1";
						else
						 checkOutWareHouse = "0"; */
						 /* 
					if((needStartDate == null || needStartDate == "")){
						alert('需求日期開始日不得為空值');
						return false;
					}
					if((needEndDate == null || needEndDate == "")){
						alert('需求日期結束日不得為空值');
						return false;
					}
					if ($("#domainSelect").find("option:selected").val() == "") {
						alert('區域不得為空值');
						return false;
					} */
					var date = new Date(applyEndDate);
					$.log('the original date is '+date);
					var newdate = new Date(date);
					
					newdate.setDate(newdate.getDate() +1);
					$.log('the new date is '+newdate);
					var data = {
						trNo : trNo,
						statusSelect : statusSelect,
						wareHouseOutSelect : wareHouseOutSelect,
						wareHouseInSelect : wareHouseInSelect,
						needStartDate : needStartDate,
						needEndDate : needEndDate,
						deptNeedSelect : deptNeedSelect,
						applyStartDate : applyStartDate,
						applyEndDate : applyEndDate,
						deptApplySelect : deptApplySelect,
						troutStartDate : troutStartDate,
						troutEndDate : troutEndDate,
						personnelSelect : personnelSelect,
  					    trinStartDate : trinStartDate,
						trinEndDate : trinEndDate,
						/* checkOutWareHouse : checkOutWareHouse	 */				
					};
					$("#grid").setGridParam({datatype:"json", postData:data,page:1}).trigger("reloadGrid");
			    
			    }
				function gridFixData(trNo,trOutWhId){
				$.fancybox.open({
									href : CONTEXT_URL + "/inv/inv007/searchDtlMasterDscr",
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
									title : "修改資料",
									openEffect : 'elastic',
									closeEffect : 'fade',
									ajax : {
											data : {
												trNo : trNo,
												wareHouseOut : trOutWhId
											},
											type : "POST",
											error : function(jqXHR, testStatus, errorThrown) {
												alert(jqXHR);
											},
											async : false
									},
									beforeShow : function() {
									var data = {
												trNo : trNo,
												wareHouseOut : trOutWhId//先使用調出倉庫
											};
										//查詢dtl資料
										$("#dtlgrid").setGridParam({url: CONTEXT_URL+"/inv/inv007/editQry",datatype:"json",postData:data,page:1}).trigger("reloadGrid");
									},
									afterClose : function() {
										selectData();
									}
						});
				}
				function gridDtl(trNo) {
						$.fancybox.open({
							href : CONTEXT_URL + "/inv/inv007/searchMasterDscr",
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
							title : "明細資料",
							openEffect : 'elastic',
							closeEffect : 'fade',
							ajax : {
									data : {
										trNo : trNo
									},
									type : "POST",
									error : function(jqXHR, testStatus, errorThrown) {
										alert(jqXHR);
									}
							}
						});
					}
					
				// 開啟下拉式選單搜尋文字功能
				function selectDefault() {
					$("#searchFrom").find("select").select2();
				}	
				
			//區域聯動調出入倉，需求/申請單位
			function onDomainSelectChange(){
				resetByDomain();
				if ($("#domainSelect").find("option:selected").val() == "") {
					return false;
				}
				var data = {
					id : $("#domainSelect").find("option:selected").prop(
							"value")
				};
				$.log("data:"+data.id);
				$.ajax({
					url : CONTEXT_URL + "/ajax/inv/public/getWareHouseByDomain/",
					data : data,
					type : "POST",
					dataType : 'json',
					success : function(data) {
						if (data.success) {
							if (data.rows.length > 0) {
								for (i = 0; i < data.rows.length; i++) {
								
									$("#wareHouseOutSelect").append(
											"<option value="+data.rows[i].wh_id+">"
													+ data.rows[i].wh_name
													+ "</option>");
									if("總倉" != data.rows[i].wh_name){				
										$("#wareHouseInSelect").append(
												"<option value="+data.rows[i].wh_id+">"
														+ data.rows[i].wh_name
														+ "</option>");	
									}																
								}
							}
						}
					}
				});
				var data2 = {
					domain : $("#domainSelect").find("option:selected").prop(
							"value")
				};
				$.ajax({
					url : CONTEXT_URL + "/ajax/inv/public/deptByDomain/",
					data : data2,
					type : "POST",
					dataType : 'json',
					success : function(data) {
						if (data.success) {
							if (data.rows.length > 0) {
								for (i = 0; i < data.rows.length; i++) {
									$("#deptNeedSelect").append(
											"<option value="+data.rows[i].dept_ID+">"
													+ data.rows[i].dept_NAME
													+ "</option>");
									$("#deptApplySelect").append(
											"<option value="+data.rows[i].dept_ID+">"
													+ data.rows[i].dept_NAME
													+ "</option>");													
								}
							}
						}
					}
				});
			}
			
			function resetByDomain(){
				$("#wareHouseOutSelect").html("");
				$("<option value=''>--請選擇--</option>").appendTo(
						"#wareHouseOutSelect");
				$("#wareHouseOutSelect").select2();
				$("#wareHouseInSelect").html("");
				$("<option value=''>--請選擇--</option>").appendTo(
						"#wareHouseInSelect");
				$("#wareHouseInSelect").select2();
				
				$("#deptNeedSelect").html("");
				$("<option value=''>--請選擇--</option>").appendTo(
						"#deptNeedSelect");
				$("#deptNeedSelect").select2();
				$("#deptApplySelect").html("");
				$("<option value=''>--請選擇--</option>").appendTo(
						"#deptApplySelect");
				$("#deptApplySelect").select2();
			}
			// 申請部門連動申請人
			function onDeptSelectChange() {
				$("#personnelSelect").html("");
				$("<option value=''>--請選擇--</option>").appendTo(
						"#personnelSelect");
				$("#personnelSelect").select2();
				if ($("#deptApplySelect").find("option:selected").val() == "") {
					return false;
				}
				var data = {
					dept : $("#deptApplySelect").find("option:selected").prop(
							"value")
				};
				$.ajax({
					url : CONTEXT_URL + "/ajax/inv/public/personnelDept/",
					data : data,
					type : "POST",
					dataType : 'json',
					success : function(data) {
						if (data.success) {
							if (data.rows.length > 0) {

								for (i = 0; i < data.rows.length; i++) {
									$("#personnelSelect").append(
											"<option value="+data.rows[i].psn_NO+">"
													+ data.rows[i].chn_NM
													+ "</option>");
								}
							}
						}
					}
				});
			}
		</script>
	</head>
<body>
	<div class="row">
		<div id="breadcrumb" class="col-md-12">
			<ol class="breadcrumb">
				<%-- Button import --%>
				<c:import url="/auth/role/menu/func/${currentMenuId}" />
			</ol>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-12 col-sm-12">
			<div class="box ui-draggable ui-droppable">
				<div class="box-header">
					<div class="box-name">
						<i class="fa fa-search"></i> <span>調撥申請作業 查詢功能</span>
					</div>
					<div class="box-icons">
						<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
						</a> <a class="expand-link"> <i class="fa fa-expand"></i>
						</a>
					</div>
					<div class="no-move"></div>
				</div>
				<div class="box-content">
					<form id="searchFrom"
						class="form-horizontal bootstrap-validator-form" role="form"
						novalidate="novalidate">
						<div class="form-group">
							<table style="width: 100%">
								<tr>
									<td><label class="col-sm-12 control-label"> 調撥單號 :
									</label></td>
									<td><div class="col-sm-6">
											<input id="trNo" type="text" value="" class="form-control"
												name="trNo" placeholder="關鍵字查詢">
										</div></td>
									<td><label class="col-sm-12 control-label"> <span class="s">* </span>區域 :
									</label></td>
									<td><div class="col-sm-12">
											<select id="domainSelect" name="domainSelect">
												<option value="" selected>--請選擇--</option>
												<c:forEach items="${domainSelect}" var="domain">
													<option value="${domain.ID}">${domain.NAME}</option>
												</c:forEach>
											</select></td>
									<!-- <td><div class="col-md-push-10">
										<input id="checkOutWareHouse" name="checkOutWareHouse"
										disabled="disabled" type="checkbox">
									結案返回調出倉
										</div>
									</td> -->
									</div>
									</td>
									<td  class="col-sm-1"></td>
								</tr>
								<tr>
									<td><label class="col-sm-12 control-label">調出倉庫 :
									</label></td>
									<td><div class="col-sm-6">
											<select id="wareHouseOutSelect" name="wareHouseOutSelect">
												<option value="" selected>--請選擇--</option>
												<%-- <c:forEach items="${wareHouseSelect}" var="wareHouse">
													<option value="${wareHouse.wh_id}">${wareHouse.wh_name}</option>
												</c:forEach> --%>
											</select>
										</div></td>
									<td><label class="col-sm-12 control-label">調入倉庫 :
									</label></td>
									<td><div class="col-sm-12">
											<select id="wareHouseInSelect" name="wareHouseInSelect">
												<option value="" selected>--請選擇--</option>
												<%-- <c:forEach items="${wareHouseSelect}" var="wareHouse">
													<option value="${wareHouse.wh_id}">${wareHouse.wh_name}</option>
												</c:forEach> --%>
											</select>
										</div></td>
								</tr>
								<tr>
									<td><label class="col-sm-12 control-label">需求日期 : </label></td>
									<td><div class="col-sm-12">
											<input id="needStartDate" type="text" value=""
												name="needStartDate" readonly="readonly"> 至
												<input id="needEndDate"
												type="text" value="" name="needEndDate" readonly="readonly">
												
										</div></td>
									<td><label class="col-sm-12 control-label"> 需求單位 :
									</label></td>
									<td>
										<div class="col-sm-12">
											<select id="deptNeedSelect" name="deptNeedSelect">
												<option value="" selected="true">--請選擇--</option>
												<%-- <c:forEach items="${deptSelect}" var="dept">
													<option value="${dept.DEPT_ID}">${dept.DEPT_NAME}</option>
												</c:forEach> --%>
											</select>
										</div>
									</td>
								</tr>
								<tr>
									<td><label class="col-sm-12 control-label">申請日期 : </label></td>
									<td><div class="col-sm-12">
											<input id="applyStartDate" type="text" value=""
												name="applyStartDate" readonly="readonly"> 至 <input id="applyEndDate"
												type="text" value="" name="applyEndDate" readonly="readonly">
										</div></td>
									<td><label class="col-sm-12 control-label"> 申請單位 :
									</label></td>
									<td><div class="col-sm-12">
											<select id="deptApplySelect" name="deptApplySelect">
												<option value="" selected="true">--請選擇--</option>
												<%-- <c:forEach items="${deptSelect}" var="dept">
													<option value="${dept.DEPT_ID}">${dept.DEPT_NAME}</option>
												</c:forEach> --%>
											</select>
										</div></td>								
								</tr>
								<tr>
									<td><label class="col-sm-12 control-label">調出日期 : </label></td>
									<td><div class="col-sm-12">
											<input id="troutStartDate" type="text" value=""
												name="troutStartDate" readonly="readonly"> 至
											<input id="troutEndDate"
												type="text" value="" name="troutEndDate" readonly="readonly">
										</div>
									</td>
									<td><label class="col-sm-12 control-label"> 申請人 : </label></td>
									<td><div class="col-sm-12">
											<select id="personnelSelect" name="personnelSelect">
												<option value="" selected="true">--請選擇--</option>
											</select>
										</div></td>
								</tr>
								<tr>
									<td><label class="col-sm-12 control-label">調入日期 : </label></td>
									<td><div class="col-sm-12">
											<input id="trinStartDate" type="text" value=""
												name="trinStartDate" readonly="readonly">    至
											<input id="trinEndDate"
												type="text" value="" name="trinEndDate" readonly="readonly">
										</div>
									</td>
									<td><label class="col-sm-12 control-label"> 調撥進度 :
									</label></td>
									<td><div class="col-sm-12">
											<select id="statusSelect" name="statusSelect">
												<option value="" selected="true">--請選擇--</option>
												<c:forEach items="${statusSelect}" var="status">
													<option value="${status.CODE}">${status.NAME}</option>
												</c:forEach>
											</select></td>
								</tr>
							</table>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div id="ajaxSearchResult" class="col-xs-12">
			<!-- 固定id for window.resize start-->
			<div id="jQgrid" align="center">
				<table id="grid"></table>
				<div id="pager"></div>
			</div>
			<!-- 固定id for window.resize end-->
</body>
</html>