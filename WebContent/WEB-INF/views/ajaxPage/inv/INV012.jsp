<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>倉管發料作業</title>
	<script type="text/javascript">			
		$(document).ready(function() {
			LoadSelect2Script("<s:url value='/resources/plugins/select2/select2.min.js'/>", selectDefault);
			
			mountGrid();
			
			mountButEvent();
			
		});			
		
		//掛載表格
		function mountGrid(){
			
			$("#grid").jqGrid({
				datatype : "local",
				pager : '#pager',
				autowidth:'true',
				url : CONTEXT_URL + "/inv/inv012/search/",
				colNames : [ '進度','領料單號', '發料倉庫', '申請單位', '申請人','申請日期','站台','','','','' ],
				colModel : [ 
					{name : 'statusDscr',index : 'statusDscr',sortable : false},
				    {name : 'opt_ID',index : 'opt_ID' , align : 'center',sortable : false},
				    {name : 'whIdDscr',index : 'whIdDscr' , align : 'center',sortable : false}, 
				    {name : 'deptIdDscr',index : 'deptIdDscr', align : 'center',sortable : false},
				    {name : 'app_userName',index : 'app_userName' , align : 'center',sortable : false}, 
				    {name : 'app_TIME',index : 'app_TIME' , align : 'center',sortable : false,
				    	formatter: function(cellvalue, options, rowObject){
				   			if (cellvalue != null && cellvalue != "") {
					   			var stDate = new Date(cellvalue);
								return stDate.getFullYear() + "/" + (stDate.getMonth() + 1) + "/" + stDate.getDate() + " " + addZero(stDate.getHours()) +":"+addZero(stDate.getMinutes());
				   			}else {
				   				return "";
				   			}
			   			}},
				    {name : 'bts_SITE_ID',index : 'bts_SITE_ID',sortable : false},
	   			 	{name : 'app_USER',index : 'app_USER' , align : 'center',sortable : false,hidden:true}, 
	   			 	{name : 'wh_ID',index : 'wh_ID' , align : 'center',sortable : false,hidden:true}, 
	   			 	{name : 'site_ID',index : 'site_ID',sortable : false,hidden:true},
	   			 	{name : 'status',index : 'status',sortable : false,hidden:true}],
				multiselect : true,
				caption : "發料清單",
				rownumbers : true,
				onSelectRow : function() {},
				gridComplete : function() {},
				ondblClickRow: function(rowId) { //double clicking a jqgrid row
		            var rowData = jQuery(this).getRowData(rowId);
		            gridDtl(rowData);
		        }
			});
			
			$(window).resize(function() {
				$(window).unbind("onresize");
				$("#grid,.grid").setGridWidth($("#jQgrid").width() - 10);
				$(window).bind("onresize", this);
			}).trigger('resize');	
		}
		
		//掛載表格Event
		function mountButEvent(){
			
			//領料單查詢
			$('#btn_rn_app_search').click(function() {
				var wareHouseSelect = $("#wareHouseSelect").find("option:selected").val();
				var statusSelect = $("#statusSelect").find("option:selected").val(); 
				var optId = $("#optId").val(); 
				/* var siteId = $("#siteId").attr("realValue"); */ 
				var siteId = $("#siteId").val();
				var deptApplySelect = $("#deptApplySelect").find("option:selected").val(); 
				var personnelSelect = $("#personnelSelect").find("option:selected").val(); 
				 
				 if((wareHouseSelect == null || wareHouseSelect == "")){
					alert('發料倉庫不得為空值');
					return false;
				}
				 
				var data = {
					wareHouseSelect : wareHouseSelect,
					statusSelect : statusSelect,
					optId : optId,
					siteId : siteId,
					deptApplySelect : deptApplySelect,
					personnelSelect : personnelSelect					
				};
				
				$("#grid").setGridParam({datatype:"json", postData:data, mtype: 'POST',page:1}).jqGrid().trigger("reloadGrid");
		    
			});
			
			//發料
			$('#btn_rn_act').click(function() {
				
				var selr = jQuery('#grid').jqGrid('getGridParam','selarrrow');
				var optIdList="";
				
				if(!selr.length==0) {
					for(var i=0;i<selr.length;i++){
						
						var myrow = jQuery('#grid').jqGrid('getRowData',selr[i]);
						
						if(myrow.status != "MR02" && myrow.status != "MR04"){
							alert("僅已核可/發料中資料可做發料動作");
							return false;
						}
						
						if (optIdList == "") {
							optIdList = "optId="+ myrow.opt_ID;
						}else {
							optIdList = optIdList + "&" + "optId=" + myrow.opt_ID;
						}
					}
					
					var wareHouseSelect = $("#wareHouseSelect").find("option:selected").text();
					var wareHouseVal = $("#wareHouseSelect").find("option:selected").val();
					
					$.fancybox.open({
						href : CONTEXT_URL + "/inv/inv012/msPage",
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
						ajax : {
							data : {
								wareHouse : wareHouseSelect,
								wareHouseVal : wareHouseVal,
								optId : optIdList
							},
							type : "POST",
							error : function(jqXHR, testStatus, errorThrown) {
								alert(jqXHR);
							},
							async : false
						},
						title : "發料功能",
						openEffect : 'elastic',
						closeEffect : 'fade',
						afterShow : function() {},
						afterClose : function() {
							$("#btn_rn_app_search").trigger("click");
						}
					});
				}else{
					alert("按發料鈕前，請先勾選資料");
					return false;
				}
			});
			
			//結案
			$('#btn_rn_close').click(function() {
				
				var selr = jQuery('#grid').jqGrid('getGridParam','selarrrow');
				
				if(selr.length>1){
					alert("系統僅提供一筆資料做結案動作");
					return false;
				}
				
				if(!selr.length==0) {
					for(var i=0;i<selr.length;i++){
						var myrow = jQuery('#grid').jqGrid('getRowData',selr[i]);
						
						if(myrow.status != "MR02" && myrow.status != "MR04" ){
								alert("僅已核可/發料中資料可做結案動作");
								return false;
								break;
						}else{
							close(myrow.opt_ID,myrow.wh_ID);
						}
					}
				}else{
					alert("按結案鈕前，請先勾選已核可/發料中資料");
					return false;
				}	
						
			});
			
			//發料單查詢
			$('#btn_rn_act_search').click(function() {
				$.fancybox.open({
					href : CONTEXT_URL + "/inv/inv012/txnPage",
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
					closeEffect : 'fade'
				});
			});

			//重置按鈕
			$('#btn_reset').click(function() {
				$("#searchFrom").trigger('reset');
				$("#grid").jqGrid().clearGridData();
				$("#optId").prop("value","");
				$("#siteId").prop("value","");
				$("#siteId").attr("realValue","");
				$("#wareHouseSelect").select2("val", "");
				$("#statusSelect").select2("val", "");
				$("#deptApplySelect").select2("val", "");
				$("#personnelSelect").html("");
				$("<option value=''>--請選擇--</option>").appendTo("#personnelSelect");
				$("#personnelSelect").select2();
			});	
			
		}
		

		// 開啟下拉式選單搜尋文字功能
		function selectDefault() {
			$("#searchFrom").find("select").select2();
		}
		
		//申請部門連動申請人
		$("#deptApplySelect").change(onDeptSelectChange);
				
		//=========Function=============	
		function addZero(i) {
		    if (i < 10) {
		        i = "0" + i;
		    }
		    return i;
		}
		
		//結案
		function close(optId,whId){
			
			var data = {
				optId : optId,
				whId : whId
			};
			
			//更新Booking主檔
			/* $.ajax({			
				url : CONTEXT_URL+"/inv/inv012/updateBooking/",		
				data:data,
				type : "POST",
				dataType : 'json',
				async: false,
				error : function(data) {
					alert(data.msg);
				}
			});
			
			//更新庫存主檔
			$.ajax({			
				url : CONTEXT_URL+"/inv/inv012/updateInvInv/",		
				data:data,
				type : "POST",
				dataType : 'json',
				async: false,
				error : function(data) {
					alert(data.msg);
				}
			});	 */
			
			//更新OPT資料
			$.ajax({			
				url : CONTEXT_URL+"/inv/inv012/close/",		
				data:data,
				type : "POST",
				dataType : 'json',
				async: false,
				success : function(data) {
					$("#grid").trigger("reloadGrid");
					alert(data.msg);
				} ,
				error : function(data) {
					alert(data.msg);
				}
			});	
		}
					
		// 基站編號LOV
		$("#siteIdSearch").click(function () {
			openSiteDialogFrame("selectSitePage", "receviceSiteData"); // call common.js
		});
					
		//站台查詢使用	
		function receviceSiteData(data) {
			var json = JSON.parse(data);
			var siteId = json.siteObjs[0].site_ID;
			var btsSiteId = json.siteObjs[0].bts_SITE_ID;
			$("#siteId").val(btsSiteId);
			$("#siteId").attr("realValue",siteId);
		}
		
		//撈明細資料
		function gridDtl(rowData) {
			$.fancybox.open({
				href : CONTEXT_URL + "/inv/inv012/searchMasterDscr",
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
						optId : rowData.opt_ID,
						whIdDscr : rowData.whIdDscr,
						deptIdDscr : rowData.deptIdDscr,
						appUser : rowData.app_userName,
						appDate : rowData.app_TIME,
						siteIdDscr : rowData.bts_SITE_ID,
						whId : rowData.wh_ID,
						siteId : rowData.site_ID,
						statusDscr : rowData.statusDscr
					},
					type : "POST",
					error : function(jqXHR, testStatus, errorThrown) {
						alert(jqXHR);
					},
					async : false
				},
				afterShow : function() {
					var data = {
						optId : rowData.opt_ID,
						whIdDscr : rowData.whIdDscr,
						deptIdDscr : rowData.deptIdDscr,
						appUser : rowData.app_USER,
						appDate : rowData.app_TIME,
						siteIdDscr : rowData.bts_SITE_ID,
						whId : rowData.wh_ID,
						siteId : rowData.site_ID
					};
					$('#bBoardAddEdit :input:enabled:visible:first').focus();
					$("#dtlgrid").setGridParam({datatype:"json",postData:data, mtype: 'POST',page:1}).jqGrid().trigger("reloadGrid");
				},
				afterClose : function() {}
			});
		}	
		
		// 申請部門連動申請人
		function onDeptSelectChange() {
			$("#personnelSelect").html("");
			$("<option value=''>--請選擇--</option>").appendTo("#personnelSelect");
			$("#personnelSelect").select2();
			if ($("#deptApplySelect").val() == "") {
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

							for (var i = 0; i < data.rows.length; i++) {
								$("#personnelSelect").append("<option value="+data.rows[i].psn_NO+">"+ data.rows[i].chn_NM+ "</option>");
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
						<i class="fa fa-search"></i> <span>發料作業功能 查詢條件</span>
					</div>
					<div class="box-icons">
						<a class="collapse-link"> <i class="fa fa-chevron-up"></i></a> 
						<a class="expand-link"> <i class="fa fa-expand"></i></a>
					</div>
					<div class="no-move"></div>
				</div>
				
				<div class="box-content">
					<form id="searchFrom" class="form-horizontal bootstrap-validator-form" role="form" novalidate="novalidate">
						<div class="form-group">
							<table style="width:100%">
								<tr>
									<td><label class="col-sm-12 control-label"><span class="s">* </span>發料倉庫 :</label></td>
									<td>
										<div class="col-sm-12">
												<select id="wareHouseSelect" name="wareHouseSelect"  class="populate placeholder">
												<option value="" selected>--請選擇--</option>
												<c:forEach items="${wareHouseSelect}" var="wareHouse">
													<option value="${wareHouse.wh_id}">${wareHouse.wh_name}</option>
												</c:forEach>
											</select>
										</div>
									</td>
									<td><label class="col-sm-12 control-label"> 發料狀態 :</label></td>
									<td>
										<div class="col-sm-6">
											<select id="statusSelect" name="statusSelect">
												<option value="" selected>--請選擇--</option>
												<c:forEach items="${statusSelect}" var="status">
													<c:choose>
															<c:when test="${status.CODE == 'MR02' || status.CODE == 'MR04' ||status.CODE == 'MR05'}">
																<option value="${status.CODE}">${status.NAME}</option>
															</c:when>
													</c:choose>
												</c:forEach>
											</select>
										</div>
									</td>
									<td><div class="col-sm-6"></div></td>	
								</tr>
								<tr>
									<td><div class="clearfix">&nbsp;</div></td>
								</tr>
								<tr>
									<td><label class="col-sm-12 control-label"> 領料單號 :</label></td>
									<td>
										<div class="col-sm-6">
											<input id="optId" type="text" value="" class="form-control" name="optId" >
										</div>
									</td>
									<td><label class="col-sm-12 control-label"> 站台編號 :</label></td>
									<td>
										<div class="col-sm-6">
											<input id="siteId" type="text" value="" realValue="" class="form-control" name="siteId" placeholder="請輸入完整的基站編號">
											<div id="selectSitePage"></div>
											<form id="siteTestForm">
												<input type="hidden" name="siteCallBackFun" id="siteCallBackFun" value="receviceSiteData" />
												<input type="hidden" name="filter_DomainId" id="filter_DomainId" value="" />
												<!-- 單選不用傳 -->
												<input type="hidden" name="selectMode" id="selectMode" value="multi" />
											</form>
										</div>
										<i id="siteIdSearch" class="fa fa-search" ></i>
									</td>
								</tr>
								<tr>
									<td><div class="clearfix">&nbsp;</div></td>
								</tr>
								<tr>
									<td><label class="col-sm-12 control-label">申請單位 : </label></td>
									<td>
										<div class="col-sm-12">
											<select id="deptApplySelect" name="deptApplySelect">
												<option value="" selected>--請選擇--</option>
												<c:forEach items="${deptSelect}" var="dept">
													<option value="${dept.DEPT_ID}">${dept.DEPT_NAME}</option>
												</c:forEach>
											</select>
										</div>
									</td>
									<td><label class="col-sm-12 control-label"> 申請人 : </label></td>
									<td>
										<div class="col-sm-6">
											<select id="personnelSelect" name="personnelSelect">
												<option value="" selected>--請選擇--</option>
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
			<div id="jQgrid" align="center">
				<table id="grid"></table>
				<div id="pager"></div>
			</div>
		</div>
</body>
</html>