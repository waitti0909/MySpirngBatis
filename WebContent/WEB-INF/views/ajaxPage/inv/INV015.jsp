<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">

<head>
	<script	src="<s:url value="/resources/js/validate.js" />"></script>
	<script type="text/javascript">			
		$(document).ready(function() {
			
			//import Select2
			LoadSelect2Script('<s:url value="/resources/plugins/select2/select2.min.js" />',mountSelect2);
			
			//掛載按鈕事件
			mountBtnEvent();
			
			//掛載表格
			mountGrid();
		});
		
		//掛載表格
		function mountGrid(){
			$("#grid").jqGrid({
				datatype : "local",
				pager : '#pager',
				autowidth:'true',
				url : CONTEXT_URL + "/inv/inv015/search",
				colNames : [ '進度','退料單號', '收料倉庫', '申請單位', '申請人', '申請日期', '工單號碼', '站台','','' ],
				colModel : [ 
							{name : 'statusDscr',index : 'statusDscr',align : 'center',sortable : false},	
							{name : 'opt_ID',index : 'opt_ID',align : 'center',sortable : false}, 
				             {name : 'whIdDscr',index : 'whIdDscr',align : 'center',sortable : false}, 
				             {name : 'deptIdDscr',index : 'deptIdDscr',align : 'center',sortable : false}, 
				             {name : 'app_userName',index : 'app_userName',align : 'center',sortable : false}, 
				             {name : 'app_TIME',index : 'app_TIME',align : 'center',sortable : false,formatter: function(cellvalue, options, rowObject){
					   			if (cellvalue != null && cellvalue != "") {
						   			var stDate = new Date(cellvalue);
									return stDate.getFullYear() + "/" + (stDate.getMonth() + 1) + "/" + stDate.getDate()+ " " + addZero(stDate.getHours()) +":"+addZero(stDate.getMinutes());
					   			}else {
					   				return "";
					   			}}}, 
				             {name : 'order_ID',index : 'order_ID',align : 'center',sortable : false} ,
				             {name : 'bts_SITE_ID',index : 'bts_SITE_ID',align : 'center',sortable : false},  
				             {name : 'status',index : 'status',sortable : false,hidden:true},
				             {name : 'wh_ID',index : 'wh_ID',sortable : false,hidden:true}      
				             ],
				caption : "收料清單",
				rownumbers : true,
				multiselect : true,
				onSelectRow : function() {
				},
				gridComplete : function() {
				},
				ondblClickRow: function(rowId) {					
					var rowData = jQuery(this).getRowData(rowId);
					gridDoubleClick1(rowData);
		        }
			});
			
			$(window).resize(function() {
				$(window).unbind("onresize");
				$("#grid,.grid").setGridWidth($("#jQgrid").width() - 10);
				$(window).bind("onresize", this);
			}).trigger('resize');
		}
		
		//掛載按鈕事件
		function mountBtnEvent(){
			
			//查詢按鈕
			$('#btn_rc_app_search').click(function(){
				if($('#inWhId').val() == ""){
					alert("請選擇收料倉庫");
					return;
				}
				var data = {
						'inWhId' : $("#inWhId").val(),
						'inStatus' : $("#inStatus").val(),
						'optId' : $("#applyNumber").val(),
						/* 'siteId' : $("#siteId").attr("realValue"), */
						'siteId' : $("#siteId").val(),
						'applyDept' : $("#applyDept").val(),
						'applyUser' : $("#applyUser").val()
					};
				$("#grid").setGridParam({datatype : "json",postData : data,page:1}).trigger("reloadGrid");
			});
			
			//收料作業
			$('#btn_rc_act').click(function(){
				var optIdList="";
				var selRow = $('#grid').jqGrid('getGridParam', 'selrow');
				var selr = jQuery('#grid').jqGrid('getGridParam','selarrrow');
				if(selRow == null){
					alert("請選擇一筆收料資料");
					return false;
				}
				for(var i=0;i<selr.length;i++){
					var myrow = jQuery('#grid').jqGrid('getRowData',selr[i]);
					if(myrow.status != "RT01"){
						alert("僅退料中料可做收料動作")
						return false;
					}
					if (optIdList == "") {
						optIdList = "optId="+ myrow.opt_ID;
					}
					else {
						optIdList = optIdList + "&" + "optId=" + myrow.opt_ID;
					}
				}
				callInButEvent(optIdList);
			});
			
			//收料單查詢
			$('#btn_rc_act_search').click(function(){
				$.fancybox.open({
					href : CONTEXT_URL + "/inv/inv015/searchCallInInvoice",
					type : 'ajax',
			        width : $(window).width(),
			        height : $(window).height(),
					autoSize : false,
					padding : [ 0, 0, 0, 0 ],
					scrolling : 'yes',
					helpers : {
						overlay : {closeClick : false},
						title : {type : 'float'}
					},
					title : "收料單查詢",
					openEffect : 'elastic',
					closeEffect : 'fade',
					ajax : {
							data : {},
							type : "POST",
							error : function(jqXHR, testStatus, errorThrown) {
								alert(jqXHR);
							},
							async : false
					},
					afterShow : function() {},
					afterClose : function() {}
				});
			});
		}
		//重置按鈕
				$('#btn_reset').click(function() {
					$("#searchFrom").trigger('reset');
					$("#grid").jqGrid().clearGridData();
					$("#applyNumber").prop("value","");
					$("#siteId").prop("value","");
					$("#siteId").attr("realValue","");
					$("#inWhId").select2("val", "");
					$("#inStatus").select2("val", "");
					$("#applyDept").select2("val", "");
					$("#applyUser").html("");
					$("<option value=''>--請選擇--</option>").appendTo(
							"#applyUser");
					$("#applyUser").select2();
				});
		//申請部門連動申請人
				$("#applyDept").change(onDeptSelectChange)
		//掛載 Select2
		function mountSelect2(){
			$('#inWhId').select2();
			$('#inStatus').select2();
			$('#applicant').select2();
			$('#applyDept').select2().on('change',function(e){
				mountApplicant(e.val);
			});
			$('#applyUser').select2();
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
		//站台查詢使用		
		// 基站編號LOV
		$("#siteIdSearch").click(function () {
			openSiteDialogFrame("platformPage", $("#siteCallBackFun").val(), $(
					"#filter_DomainId").val(), "multi");
		})				
		function addZero(i) {
			if (i < 10) {
				i = "0" + i;
			}
			return i;
		}
		$("#siteIdSearch").click(function() {
			openSiteDialogFrame("platformPage", "receviceSiteData"); // call common.js
		});
		//站台查詢使用	
		function receviceSiteData(data) {
			var json = JSON.parse(data);
			var siteId = json.siteObjs[0].site_ID;
			var btsSiteId = json.siteObjs[0].bts_SITE_ID;
			$("#siteId").val(btsSiteId);
			$("#siteId").attr("realValue",siteId);
		}

		//Grade double click event
		function gridDoubleClick1(dataObj) {
			$.fancybox.open({
				href : CONTEXT_URL + "/inv/inv015/searchMasterDscr",
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
						optId : dataObj.opt_ID,
						statusDscr : dataObj.statusDscr,
						deptIdDscr : dataObj.deptIdDscr,
						appUser : dataObj.app_userName,
						siteIdDscr : dataObj.bts_SITE_ID
					},
					type : "POST",
					error : function(jqXHR, testStatus, errorThrown) {
						alert(jqXHR);
					},
					async : false
				},
				beforeShow : function() {
					var dataDetail = {
						optId : dataObj.opt_ID,
						whId : dataObj.wh_ID
					};
					$("#gridByView").setGridParam({
						datatype : "json",
						postData : dataDetail
						,page:1
					}).trigger("reloadGrid");
				}
			});
		}

		//執行收料作業 Event
		function callInButEvent(optIdList) {
			var whId = $('#inWhId').find("option:selected").val();
			var whName = $('#inWhId').find("option:selected").text();
			$.fancybox.open({
				href : CONTEXT_URL + "/inv/inv015/callInView",
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
				title : "收料作業",
				openEffect : 'elastic',
				closeEffect : 'fade',
				ajax : {
					data : {
						whId : whId,
						whName : whName,
						optId : optIdList
					},
					type : "POST",
					error : function(jqXHR, testStatus, errorThrown) {
						alert(jqXHR);
					},
					async : false
				},
				afterShow : function() {
				},
				afterClose : function() {
					$("#btn_rc_app_search").trigger("click");					
				},
				afterShow : function() {
				}
			});
		}
		// 申請部門連動申請人
		function onDeptSelectChange() {
			$("#applyUser").html("");
			$("<option value=''>--請選擇--</option>").appendTo("#applyUser");
			$("#applyUser").select2();
			if ($("#applyDept").val() == "") {
				return false;
			}
			var data = {
				dept : $("#applyDept").find("option:selected").prop("value")
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
								$("#applyUser").append(
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
						<span>倉管收料作業功能 查詢條件</span>
					</div>
					<div class="no-move"></div>
				</div>
				
				<div class="box-content">
					<form id="searchFrom" class="form-horizontal bootstrap-validator-form" role="form" novalidate="novalidate">
						<div class="form-group">
							<table style="width:100%">
								<tr>
									<td>
										<label class="col-sm-12 control-label"><span class="s">* </span>收料倉庫 :</label>
									</td>
									<td>
									<div class="col-sm-6">
										<select id="inWhId">
											<option value="">--請選擇--</option>
											<c:forEach items="${callInDepot}" var="depot">
												<option value="${depot.wh_id}">${depot.wh_name}</option>
											</c:forEach>
										</select>
									</div>
									</td>
									<td>
										<label class="col-sm-12 control-label">收料狀態：</label>
									</td>
									<td>
									<div class="col-sm-6">
										<select id="inStatus">
											<option value="">--請選擇--</option>
											<c:forEach items="${callInStatus}" var="status">
												<option value="${status.CODE}">${status.NAME}</option>
											</c:forEach>
										</select>
									</div>
									</td>
								</tr>
								<tr>
									<td><label class="col-sm-12 control-label">退料申請單號 :</label></td>
									<td>
									<div class="col-sm-6">
										<input id="applyNumber" type="text" class="form-control">
									</div>
									</td>
									<td>
										<label class="col-sm-12 control-label">站台編號：</label>
									</td>
									<td>
									<div class="col-sm-6">
										<input id="siteId" type="text" realValue="" value=""  class="form-control" placeholder="請輸入完整的基站編號">
										<div id="platformPage"></div>
										<form id="platformForm">
											<input type="hidden" name="siteCallBackFun" id="siteCallBackFun" value="receviceSiteData" />
											<input type="hidden" name="filter_DomainId" id="filter_DomainId" value="" />
											<!-- 單選不用傳 -->
											<input type="hidden" name="selectMode" id="selectMode" value="multi" />
										</form>
									</div>
									<div>
									<i id="siteIdSearch" class="fa fa-search" ></i>
									</div>	
									</td>
								</tr>
								<tr>
									<td><label class="col-sm-12 control-label">申請單位:</label></td>
									<td>
									<div class="col-sm-12">
										<select id="applyDept">
											<option value="">--請選擇--</option>
											<c:forEach items="${applyDept}" var="apply">
												<option value="${apply.DEPT_ID}">${apply.DEPT_NAME}</option>
											</c:forEach>
										</select>
									</div>	
									</td>
									<td><label class="col-sm-12 control-label">申請人：</label></td>
									<td>
									<div class="col-sm-6">
										<select id="applyUser">
											<option value="">--請選擇--</option>
										</select>
									</div>	
									</td>
								</tr>
							</table>
						</div>
					</form>
				</div>
			</div>
		</div>
		
		<div id="ajaxSearchResult" class="col-xs-12">
			<div id="jQgrid">
				<table id="grid"></table>
				<div id="pager"></div>
			</div>
		</div>
		
	</div>
	
</html>