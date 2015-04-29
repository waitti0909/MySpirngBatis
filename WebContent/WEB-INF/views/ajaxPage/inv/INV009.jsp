<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<style type="text/css">
			tr.highlight td {padding-top: 10px;}
		</style>
		<script type="text/javascript">
			
			var invTrDataList;
		
			$(document).ready(function() {
				
				//import Select2
				LoadSelect2Script('<s:url value="/resources/plugins/select2/select2.min.js" />',select2);
	
				dateSetting();
				
				mountGrid();
				
				mountButEvent();
				
				$('#btn_search').click();
				
			});
			
			//掛載表格
			function mountGrid(){
				$("#inv009Grid").jqGrid({
					datatype : "local",
					pager : '#inv009Pager',
					autowidth:'true',
					url : CONTEXT_URL + "/inv/inv008/detailSearch",
					colNames : [ '單號', '調出倉', '調入倉', '調撥進度', '需求單位', '需求日期', '申請人' ],
					colModel : [ {name : 'tr_no',index : 'tr_no',align : 'center',sortable : false}, 
					             {name : 'outWhName',index : 'outWhName',align : 'center',sortable : false}, 
					             {name : 'inWhName',index : 'inWhName',align : 'center',sortable : false}, 
					             {name : 'statusValue',index : 'statusValue',align : 'center',sortable : false}, 
					             {name : 'applyDeptName',index : 'need_date',align : 'center',sortable : false}, 
					             {name : 'needDate',index : 'need_date',align : 'center',sortable : false,formatter: "date", formatoptions:{newformat: "Y-m-d"}} ,
					             {name : 'appUserName',index : 'appUserName',align : 'center',sortable : false},        
					             ],
					caption : "調入清單",
					rownumbers : true,
					onSelectRow : function() {},
					gridComplete : function() {},
					ondblClickRow: function(rowId) { 
						var invTrData = invTrDataList[(rowId-1)];
						doubleClickEvent(invTrData);
			        },
					beforeProcessing: function (data, status, xhr) {
						invTrDataList = data.rows;
		            }
				});
				
				$(window).resize(function() {
					$(window).unbind("onresize");
					$("#inv009Grid,.grid").setGridWidth($("#inv009JQgrid").width() - 10);
					$(window).bind("onresize", this);
				}).trigger('resize');
			}
			
			//掛載按鈕事件
			function mountButEvent(){
				
				//查詢按鈕
				$('#btn_search').click(function(){
					
					if ($("#domainSelect").find("option:selected").val() == "") {
						alert('區域不得為空值');
						return;
					}
					
					var data = {
							'trNo' : $("#applyNumber").val(),
							'domain' : $("#domainSelect").find("option:selected").val(),
							'outWhId' : $("#outDepot").find("option:selected").val(),
							'inWhId' : $("#inDepot").find("option:selected").val(),
							'needDept' : $("#demandDept").val(),
							'applyDept' :  $("#applyDept").val(),
							'applyDateStr' : $("#strDate").val(),
							'applyDateEnd' : $("#endDate").val(),
							'applicant' : $("#applicant").val(),
							'wareHouseType' : 'in',
							'applyState' : $("#applyState").val()
					};
						
					if(checkDateStrRange($("#strDate").val(), $("#endDate").val())) {
						
						$("#inv009Grid").setGridParam({datatype : "json",postData : data,page:1}).trigger("reloadGrid");
					}else{
						
						alert('起始日不可大於結束');
					}
				});
				
				//調出按鈕
				$('#btn_tran_in').click(function(){
					
					var id = $('#inv009Grid').jqGrid('getGridParam', 'selrow');
					
					if(id == null){
						alert("請選擇一筆調入資料");
						return;
					}
					
					var invTrData = invTrDataList[(id-1)];
					
					//調撥進度為(5.調出，4.部份調出，5.調入)的資料才可進入
					if(invTrData.statusValue == '調出' || invTrData.statusValue == '部份調出' || invTrData.statusValue == '調入'){
 						exportDataEvent(invTrData);
					}else{
						alert("調撥進度為 [ "+invTrData.statusValue+" ] 無法調入。");
					}
					
				});
			}
			
			//Select2 UI And Date Setting
			function select2() {
				
				$('#applyState').select2();
				$('#outDepot').select2();
				$('#inDepot').select2();
				$('#applyDept').select2().on('change',function(e){
 					mountApplicant(e.val);
				});
				$('#demandDept').select2();
				$('#applicant').select2();
				$('#domainSelect').select2().on('change',function(){
 					onDomainSelectChange();
				});
			}
			
			//時間預設
			function dateSetting(){
				
				//起始日日曆						
				$('#strDate').datepicker({
					dateFormat : "yy-mm-dd",
					changeYear : true,
					changeMonth : true,
					showButtonPanel : true
				});
				
				//結束日日曆
				$('#endDate').datepicker({
					dateFormat : "yy-mm-dd",
					changeYear : true,
					changeMonth : true,
					showButtonPanel : true,
					showTime : false
				});
				
				$('#endDate').datepicker('setDate',new Date());
				
				var today = new Date();
				today.setDate(today.getDate()-30);
				
				$('#strDate').datepicker('setDate',today);
			}
			
			//掛載申請人
			function mountApplicant(e){
				
				$("#applicant  option").remove();
				$("#applicant").append("<option value=''>--請選擇--</option>");
				$("#applicant").select2("val","");
				
				if(e == ""){
					return;	
				}
				
				var data ={dept :e};
				$.ajax({			
					url : CONTEXT_URL+"/ajax/inv/public/personnelDept/",		
					data:data,
					type : "POST",
					dataType : 'json',
					success : function(data) {
						if(data.success){
							if(data.rows.length > 0){
								for(var i=0; i<data.rows.length; i++){		
									$("#applicant").append("<option value="+data.rows[i].psn_NO+">"+data.rows[i].chn_NM+"</option>");
								}
							}
						}
					}
				});
			}
			
			//區域聯動調出入倉，需求/申請單位
			function onDomainSelectChange(){
				
				var data = {
					id : $("#domainSelect").find("option:selected").prop("value")
				};
				
				$("#outDepot  option").remove();
				$("#outDepot").append("<option value=''>-- 請選擇 --</option>");
				$("#outDepot").select2("val","");
				$("#inDepot  option").remove();
				$("#inDepot").append("<option value=''>-- 請選擇 --</option>");
				$("#inDepot").select2("val","");
				$("#demandDept  option").remove();
				$("#demandDept").append("<option value=''>-- 請選擇 --</option>");
				$("#demandDept").select2("val","");
				$("#applyDept  option").remove();
				$("#applyDept").append("<option value=''>-- 請選擇 --</option>");
				$("#applyDept").select2("val","");
				
				$.ajax({
					url : CONTEXT_URL + "/ajax/inv/public/getWareHouseByDomain/",
					data : data,
					type : "POST",
					dataType : 'json',
					success : function(data) {
						if (data.success) {
							if (data.rows.length > 0) {
								for (var i = 0; i < data.rows.length; i++) {
									$("#outDepot").append("<option value="+data.rows[i].wh_id+">"+ data.rows[i].wh_name+ "</option>");
									$("#inDepot").append("<option value="+data.rows[i].wh_id+">"+ data.rows[i].wh_name+ "</option>");													
								}
							}
						}
					}
				});
				
				var data2 = {
					domain : $("#domainSelect").find("option:selected").prop("value")
				};
				
				$.ajax({
					url : CONTEXT_URL + "/ajax/inv/public/deptByDomain/",
					data : data2,
					type : "POST",
					dataType : 'json',
					success : function(data) {
						if (data.success) {
							if (data.rows.length > 0) {
								for (var i = 0; i < data.rows.length; i++) {
									$("#demandDept").append("<option value="+data.rows[i].dept_ID+">"+ data.rows[i].dept_NAME+ "</option>");
									$("#applyDept").append("<option value="+data.rows[i].dept_ID+">"+ data.rows[i].dept_NAME+ "</option>");													
								}
							}
						}
					}
				});
			}
			
			//執行調入
			function exportDataEvent(invTrData){
				$.fancybox.open({
					href : CONTEXT_URL + "/inv/inv009/exportDataDetail",
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
					title : "執行調入",
					openEffect : 'elastic',
					closeEffect : 'fade',
					ajax : {
							data : JSON.stringify(invTrData),
							type : "POST",
							contentType : "application/json",
							error : function(jqXHR, testStatus, errorThrown) {
								alert(jqXHR);
							},
							async : false
					},
					afterShow : function() {},
					afterClose : function() {
						$('#btn_search').click();
					}
				});
			}
			
			//資料檢視
			function doubleClickEvent(invTrData){
				$.fancybox.open({
					href : CONTEXT_URL + "/inv/inv009/callInDetailView",
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
					title : "執行調入",
					openEffect : 'elastic',
					closeEffect : 'fade',
					ajax : {
							data : JSON.stringify(invTrData),
							type : "POST",
							contentType : "application/json",
							error : function(jqXHR, testStatus, errorThrown) {
								alert(jqXHR);
							},
							async : false
					},
					afterShow : function() {},
					afterClose : function() {}
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
							<i class="fa fa-search"></i>
							<span>調入作業與查詢</span>
						</div>
						<div class="no-move"></div>
					</div>
				
					<div class="box-content">
						<form id="searchFrom" class="form-horizontal bootstrap-validator-form" role="form" novalidate="novalidate">
							<div class="form-group">
								<table style="width:100%">
									<tr>
										<td class="col-sm-2 control-label"><label>調撥單號 :</label></td>
										<td class="col-sm-3">
											<div>
												<input id="applyNumber" type="text" class="form-control">
											</div>
										</td>
										<td  class="col-sm-1"></td>
										<td class="col-sm-2 control-label"><span class="s">* </span><label>區域：</label></td>
										<td class="col-sm-3">
											<div>
												<select id="domainSelect" name="domainSelect">
													<c:forEach items="${domainSelect}" var="domain">
														<option value="${domain.ID}">${domain.NAME}</option>
													</c:forEach>
												</select>
											</div>
										</td>
										<td  class="col-sm-1"></td>
									</tr>
									<tr class="highlight">
										<td class="col-sm-2 control-label"><label>調出倉庫 :</label></td>
										<td class="col-sm-3">
											<div>
												<select id="outDepot">
													<option value="">-- 請選擇 --</option>
													<c:forEach items="${outDepot}" var="depot">
														<option value="${depot.wh_id}">${depot.wh_name}</option>
													</c:forEach>
												</select>
											</div>
										</td>
										<td class="col-sm-1"></td>
										<td class="col-sm-2 control-label"><label>調入倉庫：</label></td>
										<td class="col-sm-3">
											<div>
												<select id="inDepot">
													<option value="">-- 請選擇 --</option>
													<c:forEach items="${outDepot}" var="depot">
														<option value="${depot.wh_id}">${depot.wh_name}</option>
													</c:forEach>
												</select>
											</div>
										</td>
										<td  class="col-sm-1"></td>
									</tr>
									<tr class="highlight">
										<td class="col-sm-2 control-label"><label>需求單位:</label></td>
										<td class="col-sm-3">
											<div>
												<select id="demandDept">
													<option value="">-- 請選擇 --</option>
													<c:forEach items="${demandDept}" var="depot">
														<option value="${depot.DEPT_ID}">${depot.DEPT_NAME}</option>
													</c:forEach>
												</select>
											</div>
										</td>
										<td  class="col-sm-1"></td>
										<td class="col-sm-2 control-label"><span class="s">* </span><label>申請日期：</label></td>
										<td class="col-sm-3" colspan="2">
											<div style=" white-space:nowrap;">
												<input id="strDate" style="width:100px;display:inline;" class="form-control" type="text" placeholder="點選輸入框" readonly="readonly">
												 至 
												<input id="endDate" style="width:100px;display:inline;" class="form-control" type="text" placeholder="點選輸入框" readonly="readonly">
											</div>
										</td>
										<td  class="col-sm-1"></td>
									</tr>
									<tr class="highlight">
										<td class="col-sm-2 control-label"><label>申請單位 :</label></td>
										<td class="col-sm-3">
											<div>
												<select id="applyDept">
													<option value="">-- 請選擇 --</option>
													<c:forEach items="${demandDept}" var="depot">
														<option value="${depot.DEPT_ID}">${depot.DEPT_NAME}</option>
													</c:forEach>
												</select>
											</div>
										</td>
										<td  class="col-sm-1"></td>
										<td class="col-sm-2 control-label"><label>申請人：</label></td>
										<td class="col-sm-3">
											<div>
												<select id="applicant">
													<option value="">-- 請選擇 --</option>
													<c:forEach items="${applicant}" var="authPersonnel">
														<option value="${authPersonnel.PSN_NO}" <c:if test='${psnId == authPersonnel.PSN_ID}'>selected</c:if>>
															${authPersonnel.CHN_NM}
														</option>
													</c:forEach>
												</select>
											</div>
										</td>
										<td  class="col-sm-1"></td>
									</tr>									
									<tr class="highlight">
										<td class="col-sm-2 control-label"><label>調撥進度：</label></td>
										<td class="col-sm-3">
											<div>
												<select id="applyState">
													<option value="">-- 請選擇 --</option>
													<c:forEach items="${applyState}" var="sysLookup">
														<option value="${sysLookup.CODE}">${sysLookup.NAME}</option>
													</c:forEach>
												</select>
											</div>
										</td>
										<td  class="col-sm-1"></td>
									</tr>
								</table>
							</div>
						</form>
					</div>
				</div>
			</div>
			
			<div id="ajaxSearchResult" class="col-xs-12">
				<div id="inv009JQgrid">
					<table id="inv009Grid"></table>
					<div id="inv009Pager"></div>
				</div>
			</div>
			
		</div>
	</body>
</html>