<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>


<!DOCTYPE html>
<html lang="en">
	<head>
		<title>缺料查詢 查詢作業</title>
		<script	src="<s:url value="/resources/js/validate.js" />"></script>
		<script type="text/javascript">
		
		LoadSelect2Script('<s:url value="/resources/plugins/select2/select2.min.js" />',select2);
			var executed = false;
			var Today=new Date();
			
			var datebefore=new Date();
			datebefore.setDate(Today.getDate() - 7);
			
			$(document).ready(function() {	
				//setPersonByDept();
				
				$('#appStartDate').val(datebefore.getFullYear()+ "/" + (datebefore.getMonth()+1) +"/" + datebefore.getDate() );
				$('#appEndDate').val(Today.getFullYear()+ "/" + (Today.getMonth()+1) + "/" + Today.getDate() );
				$('#reqStartDate').val(datebefore.getFullYear()+ "/" + (datebefore.getMonth()+1) +"/" + datebefore.getDate() );
				$('#reqEndDate').val(Today.getFullYear()+ "/" + (Today.getMonth()+1) + "/" + Today.getDate() );
				$.datepicker.setDefaults({
					dateFormat : "yy/mm/dd",
					changeYear : true,
					changeMonth : true,
					showButtonPanel : true
				});
				//領料需求日期起始日曆						
				$('#reqStartDate').datepicker();
				//異動日期結束日曆
				$('#reqEndDate').datepicker();
				//申請日期起始日曆						
				$('#appStartDate').datepicker();
				//申請日期結束日曆
				$('#appEndDate').datepicker();
				$(".col-sm-1 i:first").click(function() {selectMaterial() ;}); 
				
				
			});//end grid	
				
			
			
			//query
			$('#btn_search').click(function() {
				
			    
			    var wh_id = $("#wareHouseSelect").val();
				var psn_no = $("#applicant").val();
				var matNoMaterial = $("#matNoMaterial").val();
				var reqStartDate = $("#reqStartDate").val();
				var reqEndDate = $("#reqEndDate").val();
				var appStartDate = $("#appStartDate").val();
				var appEndDate = $("#appEndDate").val();
				var dept_id = $("#applyDept").val();
				
				if(!(appStartDate!=null&&appStartDate!=''&&appEndDate!=null&&appEndDate!='')) {
					alert('申請日期為必輸入欄位');
					return;
				}
				if(!(reqStartDate!=null&&reqStartDate!=''&&reqEndDate!=null&&reqEndDate!='')) {
					alert('領料需求日期為必輸入欄位');
					return;
				}
				

				if(!checkDateStrRange(appStartDate, appEndDate)) {
					alert('申請日期起始不可大於結束');
					return;
				}
				if(!checkDateStrRange(reqStartDate, reqEndDate)) {
					alert('領料需求日期起始不可大於結束');
					return;
				}
				
				
				var data = {
						wh_id : wh_id,
						psn_no : psn_no,
						matNoMaterial : matNoMaterial,
						reqStartDate : reqStartDate	,
						reqEndDate :reqEndDate,
						appStartDate : appStartDate	,
						appEndDate :	appEndDate,
						dept_id	:	dept_id
					};
				
					$("#grid").setGridParam({datatype:"json", postData:data, mtype: 'POST',page:1}).jqGrid().trigger("reloadGrid");
					
					
			});
			
			//result
			$("#grid").jqGrid({
			datatype : "local",
			pager : "#pager",
			url : CONTEXT_URL + "/inv/inv023/query",
		   	colNames:[ "領料單號", "料號", "品名", "倉庫", "申請數","缺料數","庫存數","申請人員","工單號碼","站台"],
		   	colModel:[
				{name:"opt_ID", index:"opt_ID", sortable:false},   //,hidden:true
		   		{name:"mat_no", index:"mat_no", sortable:false},
		   		{name:"mat_name", index:"mat_name", sortable:false},
		   		{name:"whIdDscr", index:"whIdDscr", sortable:false},
		   		{name:"qty", index:"qty", sortable:false},
		   		{name:"lack_qty", index:"lack_qty", sortable:false},
		   		{name:"storage_qty", index:"storage_qty", sortable:false},
		   		{name:"app_userName", index:"app_userName", sortable:false},
		   		{name:"order_ID", index:"order_ID", sortable:false},
		   		{name:"siteIdDscr", index:"siteIdDscr", sortable:false}
			],
		   	viewrecords : true,
		   	caption : "",
			rownumbers : true,
			multiselect : false,
		   	onSelectRow : function(id) {
		   		
		   		
		    },
		    gridComplete : function() {
		    	if(jQuery("#grid").jqGrid('getGridParam', 'records')==-1){
				alert('查詢資料超過3000,請改按匯出EXCEL');
				}
			},
			ondblClickRow : function(rowId) {
	        }
		});
			//end grid	
			
			$(window).resize(function() {
				$(window).unbind("onresize");
				$("#grid,.grid").setGridWidth($("#jQgrid").width() - 10);
				$(window).bind("onresize", this);
			}).trigger('resize');
			
			//excel
			$('#btn_to_excel').click(function() {
			    var wh_id = $("#wareHouseSelect").val();
				var psn_no = $("#applicant").val();
				var matNoMaterial = $("#matNoMaterial").val();
				var reqStartDate = $("#reqStartDate").val();
				var reqEndDate = $("#reqEndDate").val();
				var appStartDate = $("#appStartDate").val();
				var appEndDate = $("#appEndDate").val();
				var dept_id = $("#applyDept").val();
				
					window.location.href = CONTEXT_URL + "/inv/inv023/genExcel?wh_id="+wh_id+"&psn_no="+psn_no+"&matNoMaterial="+matNoMaterial+"&reqStartDate="+reqStartDate+"&reqEndDate="+reqEndDate+"&appStartDate="+appStartDate+"&appEndDate="+appEndDate+"&dept_id="+dept_id;
				
			});
			
			// Load script of Select2 and run this
			/*
			$(document).ready(function() {
				LoadSelect2Script('<s:url value="/resources/plugins/select2/select2.min.js" />', selectDefault);
			});
			*/
			
			//重置按鈕
			$('#btn_reset').click(function() {
				
				
				
				 $("#matNoMaterial").val("");
				 $("#reqStartDate").val("");
				 $("#reqEndDate").val("");
				$("#appStartDate").val("");
				 $("#appEndDate").val("");
				 
				 
				 $("#wareHouseSelect").get(0).selectedIndex = 0; 
				 $("#applyDept").get(0).selectedIndex = 0; 
				 $("#applicant").get(0).selectedIndex = 0; 
				
				
				 $("#grid").jqGrid().clearGridData();
				
				
			});
			
			//料號選擇
			function selectMaterial() {
				
				openMaterialFrame("selectMaterialPage");
				
			
			}
			//基站查詢
			function selectSite() {
				
				openSiteDialogFrame("selectSitePage", $("#siteCallBackFun").val(), $(
						"#filter_DomainId").val(), "multi");
				//  多筆請傳入 multi	
				//	openSiteDialogFrame("selectSitePage", $("#siteCallBackFun").val(),$("#filter_DomainId").val());

			}
			//基站查詢帶回主頁資訊
			function receviceSiteData2(siteObjects) {
				
				var obj = JSON.parse(siteObjects);
				
				for (var i = 0; i < obj.siteObjs.length; i++) {
					
					$("#site_id").val(obj.siteObjs[i].site_ID);
					
				}
			}
			function select2(){
				$('#applicant').select2();
				$('#wareHouseSelect').select2();
				$('#applyDept').select2().on('change',function(){
					$("#applicant").select2().val('') ; 
					var data ={dept :$('#applyDept').val()};
					
					if($('#applyDept').select2().val()==''){
						
						$("#applicant  option").remove();
						$("#applicant").append("<option  selected= true  value=''  >--請選擇--</option>");
						$("#applicant").select2().val('') ; 
							return false;
						
					}
					$.ajax({			
						url : CONTEXT_URL+"/ajax/inv/public/personnelDept/",		
						data:data,
						type : "POST",
						dataType : 'json',
						success : function(data) {
							if(data.success){
								if(data.rows.length > 0){
									$("#applicant  option").remove();
									$("#applicant").append("<option  selected= true  value=''  >--請選擇--</option>");
									$("#applicant").select2().val('') ; 
										for(var i=0; i<data.rows.length; i++){		
											$("#applicant").append("<option value="+data.rows[i].psn_NO+">"+data.rows[i].chn_NM+"</option>");
										}
									
								}
							}
						}
					});
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
							<span>缺料查詢 查詢條件</span>
						</div>
						<div class="box-icons">
							<a class="collapse-link"> <i class="fa fa-chevron-up"></i> </a>
							<a class="expand-link"> <i class="fa fa-expand"></i> </a>
						</div>
						<div class="no-move"></div>
					</div>
				
				<div class="box-content">
					<form id="searchFrom" class="form-horizontal bootstrap-validator-form" role="form" novalidate="novalidate">
						<div class="form-group">
							<label class="col-sm-2 control-label">料號:</label>
							<div class="col-sm-3">
									<input id="matNoMaterial" type="text" name="matNoMaterial" class="form-control fa fa-search" />
							</div>
							<div class="col-sm-1"><i class="fa fa-search" style="cursor: pointer;"></i></div>
							<label class="col-sm-2 control-label">申請領料倉庫 :</label>
							<div class="col-sm-3">
								<select id="wareHouseSelect"  name="wareHouseSelect">
												<option value="" selected="true">--請選擇--</option>
												<c:forEach items="${wareHouseSelect}" var="wareHouse">
													<option value="${wareHouse.wh_id}">${wareHouse.wh_name}</option>
												</c:forEach>
								</select>
							</div>
							
							
							
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">申請單位 :</label>
							<div class="col-sm-3">
												<select id="applyDept">
													<option value="">-- 請選擇 --</option>
													<c:forEach items="${demandDept}" var="authPersonnel">
														<option value="${authPersonnel.DEPT_ID}" <c:if test='${deptId == authPersonnel.DEPT_ID}'>selected</c:if>>
															${authPersonnel.DEPT_NAME}
														</option>
													</c:forEach>
												</select>
							</div>	
							<div class="col-sm-2">
							</div>
						
							<label class="col-sm-1 control-label">申請人:</label>
								<div class="col-sm-3">
												<select id="applicant">
													<option value="">-- 請選擇 --</option>
													
												</select>
								</div>
						</div>
						<div class="form-group">
						<label class="col-sm-2 control-label"><span class="s">*
							</span>領料需求日期 :</label>
							<div class="col-sm-4">
									 <input id="reqStartDate" type="text" value=""name="reqStartDate" readonly="readonly">至<input id="reqEndDate"   type="text" value="" name="reqEndDate" readonly="readonly">
							</div>
						<label class="col-sm-2 control-label"><span class="s">*
							</span>申請日期 :</label>
							<div class="col-sm-4">
									 <input id="appStartDate" type="text" value=""name="appStartDate" readonly="readonly">至<input id="appEndDate"   type="text" value="" name="appEndDate" readonly="readonly">
							</div>	
					    </div>
					    <div id="selectMaterialPage"></div>
					    <form id="siteTestForm">
				             <!-- input name="matNoMaterial" id="matNoMaterial" value="" /--><!-- 料號 -->
				             <input type="hidden" name="matNameMaterial" id="matNameMaterial" value="" /><!-- 品名 -->
						</form>
					    <div id="selectSitePage"></div>
					    <form id="siteTestForm">
							<input type="hidden" name="siteCallBackFun" id="siteCallBackFun" value="receviceSiteData2" />
							<input type="hidden" name="filter_DomainId" id="filter_DomainId" value="" />
							<!-- 單選不用傳 -->
							<input type="hidden" name="selectMode" id="selectMode" value="multi" />
						</form>
					</form>
					</div>
				</div>
			</div>
			<div id="ajaxSearchResult" class="col-xs-12">
				<!-- 固定id for window.resize start-->
				<div id="jQgrid">
					<table id="grid"></table>
					<div id="pager"></div>
					
				</div>
				<!-- 固定id for window.resize end-->
			</div>
		</div>
	</body>
</html>