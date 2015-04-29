<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>公告欄維護</title>
<script type="text/javascript">
// //Run Select2 on element
function Select2Type() {
	$("#typeSelect").select2();
}

	$(document).ready(function() {
						// Load script of Select2 and run this	
						LoadSelect2Script('<s:url value="/resources/plugins/select2/select2.min.js" />',Select2Type);
						//起始日日曆						
						$('#search_STARTDATE').datepicker({
							dateFormat : "yy/mm/dd",
							changeYear : true,
							changeMonth : true,
							showButtonPanel : true
						});
						//結束日日曆
						$('#search_ENDDATE').datepicker({
							dateFormat : "yy/mm/dd",
							changeYear : true,
							changeMonth : true,
							showButtonPanel : true,
							showTime : false
						});
						
						
						//查詢按鈕
						$("#btn_search").click(function() {		
								var data = {
									startDate : $.trim($("#search_STARTDATE").val()),  //起始日
									endDate : $.trim($("#search_ENDDATE").val()),  //結束日
									bBoardType : $.trim($("#typeSelect").val()),  //類型
									subject : $.trim($("#search_SUBJECT").val())  //主旨
								};
								$("#grid").setGridParam({datatype : "json",	postData : data, page:1	}).trigger("reloadGrid");
 						});
						
						//==================================================================

						$("#grid")
								.jqGrid(
										{
											datatype : "local",
											pager : '#pager',
											//width:$('#ajaxSearchResult').attr('width'),
											url : CONTEXT_URL + "/sysBulletinboardSearch/",
											colNames : [ '公告類別', '主旨', '公佈日期', '結束日期', '公佈單位', '公佈人員',
													'bBoardId' ],
											colModel : [ 
											    {name : 'typesName',index : 'typesName',width : 30 , align : 'center',sortable : false}, 
											    {name : 'subject',index : 'subject',sortable : false}, 
											    {name : 'startDate',index : 'startDate',width : 40 , align : 'center',sortable : true, formatter : dayFormat}, 
											    {name : 'endDate',index : 'endDate',width : 40, align : 'center',sortable : true, formatter : dayFormat},
											    {name : 'deptName',index : 'deptName',width : 60 , align : 'center',sortable : false}, 
											    {name : 'chnNM',index : 'chnNM',width : 50 , align : 'center',sortable : false}, 
											    {name : 'bulletinID',index : 'bulletinID',sortable : false,hidden : true}
											    ],
											multiselect : true,
											caption : "公告欄清單",
											rownumbers : true,
											sortname : "startDate",
											sortorder : "desc",
											onSelectRow : function() {
												// do something when select a row
											},
											gridComplete : function() {
												// do something after grid loaded
											},
											ondblClickRow: function(rowId) { //double clicking a jqgrid row
									            var rowData = jQuery(this).getRowData(rowId);
									            $.each(rowData, function(i,item) {
									            	if(i=="bulletinID"){
									            		editAddBulletin(item,"view");
									            	}
									            });
									        }
										});//end grid

						$(window).resize(function() {
							$(window).unbind("onresize");
							$("#grid,.grid").setGridWidth($("#jQgrid").width() - 10);
							$(window).bind("onresize", this);
						}).trigger('resize');
						//==================================================================
						
						 //公告欄新增
					    $('#btn_add').click(function() {					    	
					    	editAddBulletin();					    	
					    });
					    
					    //公告欄修改
						$("#btn_edit").click(function() {
							//取得被選取列
							var ids = $("#grid").jqGrid('getGridParam','selarrrow');
							//是否選取
							if (ids.length != 1) {
								alert("請選擇一筆資料編輯");
								return false;
							}else{
								var bulletin = $("#grid").jqGrid('getRowData',ids);
								editAddBulletin(bulletin.bulletinID);
							}			
						});
					    
						//刪除按鈕
						$("#btn_delete").click(function() {		
				             //多選狀態下，被勾選的紀錄
					         var check_array = $("#grid").jqGrid('getGridParam', 'selarrrow');
					         var sendInfo='';
							 //是否有勾選	
				             if (check_array.length > 0) {
									for (var i = 0; i < check_array.length; i++) {
											var id = check_array[i]; // cl:jqgrid的行數，非資料真正的HEADER_ID
											var bulletin = $("#grid").jqGrid('getRowData', id); // 該筆資料model
											 if(i==0){
												 sendInfo=bulletin.bulletinID;
											 }else{
												 sendInfo=sendInfo+","+bulletin.bulletinID;
											 }
										}
									deleteConfirmChoice(sendInfo);
							} else {
								alert("請勾選欲刪除項目。");
							}
						});
						
						//重置按鈕
						$('#btn_reset').click(function() {
							$("#searchFrom").trigger('reset');
							$("#search_STARTDATE").val()=='';
							$("#search_ENDDATE").val()=='';
							$("#grid").jqGrid().clearGridData();
						});
					    		
					});
	
	
//==========FUNCTION==================================================================
	
	//新增修改執行
	function editAddBulletin(bulletinid,btnType) {
		$.fancybox.open({
			href : CONTEXT_URL + "/sysBulletinboardAddEdit/",
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
			title : "表單",
			openEffect : 'elastic',
			closeEffect : 'fade',
			ajax : {
					data : {
						btnType : btnType,
						bulletinID : bulletinid
					},
					type : "POST",
					error : function(jqXHR, testStatus, errorThrown) {
						alert(jqXHR);
					},
					async : false
			},
			afterShow : function() {
				$('#bBoardAddEdit :input:enabled:visible:first').focus();
			},
			afterClose : function() {
				// fancybox關閉後要做的事(callback function)
				$("#grid").trigger("reloadGrid");
			}
		});
	}
	
	
	//確認刪除否
	function deleteConfirmChoice(sendInfo) {
		if (confirm("確定刪除?")) {
			$.ajax({
						url : '<s:url value="/sysBulletinboard/Delete/" />',
						data : {bulletinID: sendInfo},
						type : "POST",
						success : function(data) {
							    alert("成功刪除");
								$("#grid").trigger("reloadGrid");
						},
						//dataType : "text",
						async : false
					});
		} else {
			$("#grid").trigger("reloadGrid");
		}
	}
	
	//日期格式化，只取年月日
	function dayFormat(cellvalue){
		if(cellvalue==""||cellvalue==null){
            return "";
        }else{
            return cellvalue.substr(0,cellvalue.indexOf(" ")); ;
        } 
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
						<i class="fa fa-search"></i> <span>公告欄查詢</span>
					</div>
					<div class="box-icons">
						<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
						</a> <a class="expand-link"> <i class="fa fa-expand"></i>
						</a>
					</div>
					<div class="no-move"></div>
				</div>
				<div class="box-content">
					<form class="form-horizontal bootstrap-validator-form" role="form" id="searchFrom" novalidate="novalidate">
						<div class="form-group">
							<label class="col-sm-2 control-label">起始日 : </label>
							<div class="col-sm-2">
								<input id="search_STARTDATE" type="text" value="" class="form-control"
									name="search_STARTDATE" readonly="readonly" placeholder="公告開始時間">
							</div>
							<label class="col-sm-2 control-label">結束日 : </label>
							<div class="col-sm-2">
								<input id="search_ENDDATE" type="text" value="" name="search_ENDDATE" class="form-control"
									readonly="readonly" placeholder="公告結束時間">
							</div>
						</div>					
						<div class="form-group">
							<label class="col-sm-2 control-label">類型 : </label>
							<div class="col-sm-2">
								<select class="populate placeholder" name="typeID"
									id="typeSelect">
									<option value="">-- 請選擇 --</option>
									<c:forEach items="${bulletinType}" var="bType">
										<option value="${bType.CODE}">${bType.VALUE1}</option>
									</c:forEach>
								</select>
							</div>

							<label class="col-sm-2 control-label">主旨 : </label>
							<div class="col-sm-4">
								<input id="search_SUBJECT" type="text" value="" class="form-control" name="search_SUBJECT"
									placeholder="關鍵字查詢">
							</div>
						</div>

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