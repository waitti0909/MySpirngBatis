<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<title>PO單查詢</title>
		
		<script type="text/javascript">
			$(document).ready(function() {
				buildCityOptions("locCity","locTown");
				LoadSelect2Script(
					'<s:url value="/resources/plugins/select2/select2.min.js" />',Select2Test);
				WinMove();
				$("#btn_add_temp_po").click(function(){
					$.fancybox.open({
			 			href : CONTEXT_URL+"/cm/po/insertPage",
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
			 			title : "新增",
			 			openEffect : 'elastic',
			 			closeEffect : 'fade',
			 			ajax : {
			 	            type: 'POST'	 	            
			 	        },
			 	        afterClose : function() {
			 			}
			 		});
				});//reset button end
				
				$("#btn_reset").click(function(){
					$("#searchPoForm").trigger('reset');
					$('#coName').select2("val","");
					$("#grid").jqGrid().clearGridData();
				});//reset button end
				
				$("#btn_search").click(function(){
					
					if($('#isTemp').prop("checked")){
						$('#isTemp').val("Y");
					}else{
						$('#isTemp').val("N");
					}
					var data= {
							poNo: $.trim($('#poNo').val()),
							year: $.trim($('#poYear').val()),
							poName: $.trim($('#poName').val()),
							co_vat_No: $.trim($('#coName').val()),
							isTemp: $.trim($('#isTemp').val()),
					};
					$("#grid").setGridParam({datatype:"json", postData:data, mtype: 'POST',page:1}).jqGrid().trigger("reloadGrid");
				});//search button end
				
				$("#grid").jqGrid({
					datatype: "local",
					pager: '#pager',
					url : CONTEXT_URL+"/cm/po/common/search",
				   	colNames:['poId','poDomain','年度','PO單號','PO單名稱','廠商名稱','總額','是否合併'],
				   	colModel:[
						{name:'po_ID',index:'po_ID',hidden:true, sortable: false},
						{name:'po_DOMAIN',index:'po_DOMAIN',hidden:true, sortable: false},
						{name:'po_YEAR',index:'po_YEAR',width:180, sortable: false},
						{name:'po_NO',index:'po_NO',width:180, sortable: false},
						{name:'po_NAME',index:'po_NAME', width:180,sortable: false},
						{name:'coName',index:'coName', width:180,sortable: false},
						{name:'amount',index:'amount', width:180,sortable: false},
						{name:'is_MERGE',index:'is_MERGE', hidden:true, sortable: false}
					],	
				   	viewrecords: true,
				   	caption:"PO單清單",
					rownumbers:true,
					multiselect: false,
				   	onSelectRow: function(id){ 
				   		
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
			            $.each(rowData, function(i,item) {
			            	if(i=="po_ID"){
			            		editAddPO(item,"view");
			            	}
			            });
			            
			        }
				});//end grid
				
				$(window).resize(function(){
				    $(window).unbind("onresize");
				    $("#grid,.grid").setGridWidth($("#jQgrid").width() -10);
				    $(window).bind("onresize", this);
				}).trigger('resize');
				
				$('#btn_edit').click(function(){
					//取得被選取列
					var ids = $("#grid").jqGrid('getGridParam','selrow');
					//是否選取
					if (ids == null) {
						alert("請選擇一筆資料編輯");
						return false;
					}else{
						var poRow = $("#grid").jqGrid('getRowData',ids);
						if(poRow.is_MERGE=="Y"){
							alert("此臨時PO單已合併過，不可修改!!");
						}else{
							editAddPO(poRow.po_ID,"edit");
						}						
					}	
				});
				
				$('#btn_edit_po_item').click(function(){
					//取得被選取列
					var ids = $("#grid").jqGrid('getGridParam','selrow');
					//是否選取
					if (ids == undefined) {
						alert("請選擇一筆資料編輯");
					}else{
						//工項維護進入
						var poRow = $("#grid").jqGrid('getRowData',ids);
						initPoItem(poRow.po_ID);
					}		
				});
				
			});//document.ready end
			
			function Select2Test() {
				$("#coName").select2();
			}
			
			function editAddPO(po_ID,btnType){
				$.fancybox.open({
		 			href : CONTEXT_URL+"/cm/po/editPage",
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
		 			title : "新增",
		 			openEffect : 'elastic',
		 			closeEffect : 'fade',
		 			ajax : {
		 				data : {
		 					po_ID : po_ID,
		 					btnType : btnType
						},
		 	            type: 'POST'	 	            
		 	        },
		 	        afterClose : function() {
		 			}
		 		});				
		    }
			
			function initPoItem(poId){
				$.fancybox.open({
					href : CONTEXT_URL + "/cm/po/initPoItem",
					type : "ajax",
			        width : $(window).width(),
			        height : $(window).height(),
					autoSize : false,
					padding : [0, 0, 0, 0],
					scrolling : "yes",
					helpers : {
						overlay : {
							closeClick : false
						},
						title : {
							type : "float"
						}
					},
					title : "表單",
					openEffect : "elastic",
					closeEffect : "fade",
					ajax : {
							data : {poId : poId
							},
							type : "POST",
							error : function(jqXHR, testStatus, errorThrown) {
								alert(jqXHR);
							},
							async : false
					},
					
					beforeClose : function() {
						if (!confirm("提醒：請確認資料是否存檔，關閉視窗？")) {
							return false;
						}
					},
					afterShow : function() {
					},
					afterClose : function() {
						// fancybox關閉後要做的事(callback function)
						$("#btn_search").trigger("click");
						$("#grid").trigger("reloadGrid");
					}
				});
			}
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
							<i class="fa fa-search"></i> <span>PO單查詢</span>
						</div>
						<div class="box-icons">
							<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
							</a> <a class="expand-link"> <i class="fa fa-expand"></i>
							</a>
						</div>
						<div class="no-move"></div>
					</div>
					<div class="box-content">
						<form role="form" class="form-horizontal bootstrap-validator-form" novalidate="novalidate" id="searchPoForm">
							<div class="form-group">
							<!--  系統功能  -->
								<table style="width:100%">
									<tr>
										<td width="10%" nowrap="nowrap"><label  class="col-sm-10 control-label" style="padding-left : 0px;">PO單號 : </label></td>
										<td width="20%">
											<div class="col-sm-6">
												<input id="poNo" name="poNo" type="text" class="form-control" />
											</div>
										</td>
										<td width="10%"><label  class="col-sm-10 control-label" style="padding-left : 0px;">年度 : </label></td>
										<td width="20%">
											<div class="col-sm-6">
												<input id="poYear" type="text" name="year" class="form-control" />
											</div>
										</td>
									</tr>
									<tr>
										<td>
											<div class="clearfix">&nbsp;</div>
										</td>
									</tr>
									<tr >
										<td><label class="col-sm-10 control-label">PO單名稱 :</label></td>
										<td>	
											<div class="col-sm-12">
												<input id="poName"  type="text" name="poName" class="form-control" placeholder="關鍵字查詢"/>
											</div>
										</td>
										<td><label class="col-sm-10 control-label">廠商 :</label></td>
										<td>
											<div class="col-sm-6">
												<select id="coName" name="co_vat_No" class="populate placeholder">
													<option value="">---請選擇---</option>
													<c:forEach items="${companyList }" var="company">
														<option value="${company.CO_VAT_NO}">${company.CO_NAME}</option>
													</c:forEach>
												</select>
											</div>
										</td>
									</tr>
									<tr>
										<td>
											<div class="clearfix">&nbsp;</div>
										</td>
									</tr>
									<tr>
										<td>
											<div class="row form-group">
												<div class="checkbox-inline" style="margin-left: 70px">
													<label>
														<input type="checkbox" id="isTemp" name="isTemp" value="Y" /> 臨時PO單 <i class="fa fa-square-o"></i>
													</label>
												</div>
											</div>
										</td>
									</tr>
								</table>
								<!--  系統功能 end -->
							</div>
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