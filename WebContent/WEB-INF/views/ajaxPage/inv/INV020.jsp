<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>


<!DOCTYPE html>
<html lang="en">
	<head>
		<title>序號件異動查詢作業</title>
		<script	src="<s:url value="/resources/js/validate.js" />"></script>
		<script type="text/javascript">
			var executed = false;
			var Today=new Date();
			
			var datebefore=new Date();
			datebefore.setDate(Today.getDate() - 7);
			
			$(document).ready(function() {	
				
				
				$('#crStartDate').val(datebefore.getFullYear()+ "/" + (datebefore.getMonth()+1) +"/" + datebefore.getDate() );
				$('#crEndDate').val(Today.getFullYear()+ "/" + (Today.getMonth()+1) + "/" + Today.getDate() );
				$.datepicker.setDefaults({
					dateFormat : "yy/mm/dd",
					changeYear : true,
					changeMonth : true,
					showButtonPanel : true
				});
				//異動日期起始日曆						
				$('#crStartDate').datepicker();
				//異動日期結束日曆
				$('#crEndDate').datepicker();
				
				$(".col-sm-1 i:first").click(function() {selectMaterial() ;}); 
				$(".col-sm-1 i:last").click(function() {selectSite();}); 
				
				
			});//end grid	
				
			setDetailResults();
			
			//query
			$('#btn_search').click(function() {
				
				var check=0;
			    var ven_sn = $("#ven_sn").val();
				var mat_name = $("#mat_name").val();
				var matNoMaterial = $("#matNoMaterial").val();
				var crStartDate = $("#crStartDate").val();
				var crEndDate = $("#crEndDate").val();
				var site_id = $("#site_id").val();
				var bts_site_id = $("#bts_site_id").val();
				if(bts_site_id==null||bts_site_id==''){
					site_id='';
					$("#site_id").val('');
				}
				if(ven_sn!=null&&ven_sn!='')check++;
				if(mat_name!=null&&mat_name!='')check++;
				if(matNoMaterial!=null&&matNoMaterial!='')check++;
				if(crStartDate!=null&&crStartDate!=''&&crEndDate!=null&&crEndDate!='')check++;
				if(site_id!=null&&site_id!='')check++;
				if(bts_site_id!=null&&bts_site_id!='')check++;
				if(!(crStartDate!=null&&crStartDate!=''&&crEndDate!=null&&crEndDate!='')) {
					alert('異動日期為必輸入欄位');
					return;
				}
				if(check<2){
					alert('您必須輸入最少2個條件');
					return;
				}
				
				if(!checkDateStrRange(crStartDate, crEndDate)) {
					alert('異動日期起始不可大於結束');
					return;
				}
				
				var data = {
						ven_sn : ven_sn,
						mat_name : mat_name,
						matNoMaterial : matNoMaterial,
						crStartDate : crStartDate	,
						crEndDate :	crEndDate,
						site_id	:	site_id,
						bts_site_id:bts_site_id
					};
				
					$("#grid").setGridParam({datatype:"json", postData:data, mtype: 'POST',page:1}).jqGrid().trigger("reloadGrid");
					 $("#grid2").jqGrid().clearGridData();
				
					
			});
			
			//result
			$("#grid").jqGrid({
			datatype : "local",
			pager : "#pager",
			url : CONTEXT_URL + "/inv/inv020/query",
		   	colNames:["", "料號", "廠商序號", "貼標號碼","品名","目前保管地"],
		   	colModel:[
				{name:"srl_no", index:"srl_no",hidden:true},   //,hidden:true
		   		{name:"mat_no", index:"mat_no", sortable:false},
		   		{name:"ven_sn", index:"ven_sn", sortable:false},
		   		{name:"tag_no", index:"tag_no", sortable:false},
		   		{name:"mat_name", index:"mat_name", sortable:false},
		   		{name:"s_site_name", index:"s_site_name", sortable:false}
			],
		   	viewrecords : true,
		   	caption : "",
			rownumbers : true,
			multiselect : false,
		   	onSelectRow : function(id) {
		   		
		   		var row = $("#grid").jqGrid("getRowData", id);
		   		var data = {
	   				srl_no : row.srl_no,
	   				crStartDate : $("#crStartDate").val(),
	   				crEndDate : $("#crEndDate").val()
	   			};
		   		
	   			$("#grid2").setGridParam({datatype : "json", postData : data,page:1}).trigger("reloadGrid");
		    },
		    gridComplete : function() {
			},
			ondblClickRow : function(rowId) {
	        }
		});
			//end grid	
			
			$(window).resize(function() {
				$(window).unbind("onresize");
				$("#grid,.grid").setGridWidth($("#jQgrid").width() - 10);
				$("#grid2").setGridWidth($("#jQgrid").width() - 10);
				$(window).bind("onresize", this);
			}).trigger('resize');
			
			//excel
			$('#btn_to_excel').click(function() {
				 	var ven_sn = $("#ven_sn").val();
					var mat_name = $("#mat_name").val();
					var matNoMaterial = $("#matNoMaterial").val();
					var crStartDate = $("#crStartDate").val();
					var crEndDate = $("#crEndDate").val();
					var site_id = $("#site_id").val();
					window.location.href = CONTEXT_URL + "/inv/inv020/genExcel?ven_sn="+ven_sn+"&mat_name="+mat_name+"&matNoMaterial="+matNoMaterial+"&crStartDate="+crStartDate+"&crEndDate="+crEndDate+"&site_id="+site_id;
				/*var catgCode1 = $("#catg1Select").find("option:selected").prop("value");
				var catgCode2 = $("#catg2Select").find("option:selected").prop("value");
				var catgCode3 = $("#catg3Select").find("option:selected").prop("value");
				var matNo = $("#matNoSelect").find("option:selected").prop("value");
				
				if(catgCode1 == '' && catgCode1 == ""){
					alert("請選擇類別。");
				}else{
					window.location.href = CONTEXT_URL + "/inv/inv022/getExcel?catgCode1="+catgCode1+"&catgCode2="+catgCode2+"&catgCode3="+catgCode3+"&matNo="+matNo;
				}*/
			});
			
			// Load script of Select2 and run this
			/*
			$(document).ready(function() {
				LoadSelect2Script('<s:url value="/resources/plugins/select2/select2.min.js" />', selectDefault);
			});
			*/
			
			//重置按鈕
			$('#btn_reset').click(function() {
				 $("#ven_sn").val("");
				 $("#mat_name").val("");
				 $("#matNoMaterial").val("");
				 $('#crStartDate').val(datebefore.getFullYear()+ "-" + (datebefore.getMonth()+1) +"-" + datebefore.getDate() );
				$('#crEndDate').val(Today.getFullYear()+ "-" + (Today.getMonth()+1) + "-" + Today.getDate() );
				$("#site_id").val("");
				$("#bts_site_id").val("");
				 $("#grid").jqGrid().clearGridData();
				
				 $("#grid2").jqGrid().clearGridData();
			});
			
			//料號選擇
			function selectMaterial() {
				openMaterialFrame("selectMaterialPage");
				
			
			}
			//基站查詢
			function selectSite() {
				$("#site_id").val("");
				$("#bts_site_id").val("");
				openSiteDialogFrame("selectSitePage", $("#siteCallBackFun").val(), $(
						"#filter_DomainId").val(), "multi");
				//  多筆請傳入 multi	
				//	openSiteDialogFrame("selectSitePage", $("#siteCallBackFun").val(),$("#filter_DomainId").val());

			}
			//基站查詢帶回主頁資訊
			function receviceSiteData2(siteObjects) {
				$("input[name = 'site']~br").remove();
				$("input[name = 'site']").remove();
				var obj = JSON.parse(siteObjects);
				
				for (var i = 0; i < obj.siteObjs.length; i++) {
					
					$("#site_id").val(obj.siteObjs[i].site_ID);
					$("#bts_site_id").val(obj.siteObjs[i].bts_SITE_ID);
				}
			}
			// 設定主檔明細結果顯示內容
			function setDetailResults() {
				$("#grid2").jqGrid({
					datatype : "local",
					pager : "#pager2",
					url : CONTEXT_URL + "/inv/inv020/queryDetail",
				   	colNames:["異動原因", "異動站台", "異動倉庫", "單號", "異動部門", "異動人員", "異動日期"],
				   	colModel:[
				   		{name:"txn_type_name", index:"txn_type_name", sortable:false},
				   		{name:"site_name", index:"site_id", sortable:false},
				   		{name:"wh_name", index:"wh_id", sortable:false},
				   		{name:"txn_no", index:"txn_no", sortable:false},
				   		{name:"dept_name", index:"dept_name", sortable:false},
				   		{name:"cr_name", index:"cr_name", sortable:false},
				   		{name:"cr_time", index:"cr_time", sortable:false, formatter: 'date',formatoptions: {srcformat:'Y-m-d H:i:s',newformat:'Y-m-d H:i'}}
					],	
				   	viewrecords : true,
				   	caption : "",
					rownumbers : true,
					multiselect : false,
				   	onSelectRow : function(id) {
				    },
				    gridComplete : function() {
					},
					ondblClickRow : function(rowId) {
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
							<span>序號歷程 查詢條件</span>
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
							<label class="col-sm-2 control-label">廠商序號:</label>
							<div class="col-sm-3">
									<input id="ven_sn" type="text" name="ven_sn" class="form-control" placeholder="關鍵字查詢" />
							</div>
							
							<label class="col-sm-2 control-label">品名 :</label>
							<div class="col-sm-4">
								<input id="mat_name" type="text" name="mat_name" class="form-control fa fa-search" placeholder="關鍵字查詢" />	
							</div>
							
							
							
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">料號 :</label>
							<div class="col-sm-3">
								<input id="matNoMaterial" type="text" name="matNoMaterial" class="form-control fa fa-search"  />
							</div>	
							<div class="col-sm-1"><i class="fa fa-search" style="cursor: pointer;"></i></div>
							
							
							
							<label class="col-sm-1 control-label"><span class="s">*
							</span>異動日期 :</label>
							<div class="col-sm-5">
								
									 <input id="crStartDate" type="text" value=""name="crStartDate" readonly="readonly">至<input id="crEndDate"   type="text" value="" name="crEndDate" readonly="readonly">
					
							</div>
						</div>
						<div class="form-group">
						<label class="col-sm-2 control-label">基站編號 :</label>
							<div class="col-sm-3">
								<input type="hidden" id="site_id" name="site_id" />
								<input id="bts_site_id" type="text" class="form-control"  placeholder="請輸入完整的基站編號" />
							</div>
							<div class="col-sm-1"><i class="fa fa-search" style="cursor: pointer;"></i></div>
							
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
					<br/>
					<table id="grid2"></table>
					<div id="pager2"></div>
				</div>
				<!-- 固定id for window.resize end-->
			</div>
		</div>
	</body>
</html>