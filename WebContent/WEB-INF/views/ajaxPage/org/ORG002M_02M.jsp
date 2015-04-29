<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<div id="deptPosButtons" hidden="true">
	<button class="btn btn-primary btn-label-left" type="button" id="deptPosAddBtn">
		<span><i class="fa fa-plus"></i></span><s:message code="button.add" text="新增" />
	</button>
	<button class="btn btn-primary btn-label-left" type="button" id="deptPosUpdateBtn">
		<span><i class="fa fa-edit"></i></span><s:message code="button.edit" text="修改" />
	</button>
	<button class="btn btn-primary btn-label-left" type="button" id="deptPosDeleteBtn">
		<span><i class="fa fa-times txt-danger"></i></span><s:message code="button.delete" text="刪除" />
	</button>
</div>   
<%-- 部門職稱設定頁面 --%>
<div id="" class="selectItemDiv">
	<div id="ORG002M_02MPage" class="" hidden="true">
		<div class="col-xs-13 col-sm-13">
			<form role="form" class="form-horizontal bootstrap-validator-form" novalidate="novalidate" id="searchForm">
				<div class="form-group">
					<!--  系統功能  -->
					<label class="col-sm-3 control-label">部門 :</label>
					<label class="labelStyle" id="dept_name">部門 :</label>
	                <br />
					<label class="col-sm-3 control-label">主管 :</label>
					<div class="col-sm-4 ">
						<select class="form-control" style="width:130px;" id="manager" name="manager">
						</select>
					</div>
					<!--  系統功能 end -->
				</div>
			</form>
		</div>
		<div class="col-xs-15">
			<div class="box-content no-padding">
				<table id="deptTable" class="table table-bordered  table-hover table-heading table-datatable" width="100%">
					<thead>
						<tr>
							<td></td>
							<td>職稱代號</td>
							<td>職務名稱</td>
 							<td>類型</td> 
						</tr>
					</thead>
					<tbody id="deptTableBody">
						
					</tbody>
				</table>
			</div>
		</div>
	</div>
	
	<div id="showDeptPosPag" hidden="true">
		<div class="col-xs-6 col-sm-6">
			<form role="form" class="form-horizontal bootstrap-validator-form" id="deptPosForm">
				<div class="form-group">
					<div class="col-sm-6">
						<button class="btn btn-primary btn-label" type="submit" id="deptPosSaveBtn">
		                <span><i class="fa fa-save"></i></span> <s:message code="button.save" text="儲存" />
	                    </button>
	                </div>
	                <div class="clearfix">&nbsp;</div>
					<label class="col-sm-3 control-label-left">類型 :</label> 
					
					<select class="form-control" style="width:130px;" id="pos_type_filter" name="POS_TYPE" >
				        <option  value="all">全部</option>
				        <option  value="N">一般</option>
				        <option  value="W">簽核</option>
					</select> 
					
					
					<!--  系統功能  -->
					<label class="col-sm-3 control-label-left">職稱名稱 :</label>
					
					<select class="form-control" style="width:130px;" id="pos_ID" name="POS_ID" required="required">
						<option id="IdIsEmpty" value="">請選擇</option>
					</select>
					
					<div class="clearfix">&nbsp;</div>
					<div class="clearfix">&nbsp;</div>
	<!-- 				<label class="col-sm-3 control-label-left">上層主管 :</label> -->
					
					<input type="hidden" id="deptID" name="DEPT_ID"></input>
					<input type="hidden" id="oldDeptPosId"></input>
					<input type="hidden" id="decide"></input>
					<input type="hidden" id="oldManager"></input>
					<!--  系統功能 end -->
				</div>
			</form>
		</div>
	</div>
</div>
<script type="text/javascript">
	$(document).ready(function() {		
		var decide = $("#decide").val();
		$( "#manager" ).change(function() {
			var manager = $("#manager").val();
			var oldManager = $("#oldManager").val();
			if(confirm("確定要變更?")){
				var deptId = $("#deptID").val();
				var deptPosId ="";
				if(manager != ""){
					deptPosId = deptId+"__"+manager;
				}
				$.ajax({
					type : 'POST',
					url : CONTEXT_URL + "/org/dept/deptPos/updateManager",
					data :{
						"deptId" : deptId,
						"deptPosId" : deptPosId,
					},
					async : false,
					success : function(data) {
						var result = data['result'];
						var manager = data['manager'];
						if(result==true){
							alert('<s:message code="msg.update.success"/>');
							$("#oldManager").val(manager);
							
						}else{
							alert('<s:message code="msg.update.fail"/>');
						}
					}
				});
			}else{
				$("#manager").val(oldManager);
				return false ;
			}
		});
		//新增
		$('#deptPosAddBtn').click(function() {
			$.ajax({
				mimeType : 'text/html; charset=utf-8',
				type : 'POST',
				url : CONTEXT_URL + "/org/dept/deptPos/add",
				async : false,
				dataType : "json",
				success : function(data) {
					decide="add";
					$("#showDeptPosPag").show();
					$("#showDeptPosPag").dialog({
						width : $(window).width() - 50,
				        height : $(window).height() - 50,
						modal: true,
						title: "部門職稱資料",
						resizable: false,
						position: { my: "center", at: "center"},
						close: function() {
					  		$("#parent_DEPT_POS_ID").val("");
							$("#showDeptPosPag").dialog('destroy');
							$("#showDeptPosPag").hide();
				    	},
					    open: function (event, ui) {
					    	$("#pos_type_filter").val("all").change();
					    	$('.ui-dialog').css('z-index',10300);
					    	$('.ui-widget-overlay').css('z-index',10200);
					    	
					   	}
					});
				}
			});		
		});//addButton end
		//更新
		$('#deptPosUpdateBtn').click(function() {
			var posId = listenerDeptPosCheckBox();
			if(posId.length==1){
				var deptID = $("#deptID").val();
				$.ajax({
					mimeType : 'text/html; charset=utf-8',
					type : 'POST',
					url : CONTEXT_URL + "/org/dept/deptPos/updatePage",
					async : false,
					dataType : "json",
					data :{
						"posId" : posId.toString(),
						"deptId" : deptID
					},
					success : function(data) {
						var rowData = data.row;
						//for(var key in map){
							$("#parent_DEPT_POS_ID").val(rowData.pos_ID);
					  		$("#pos_type_filter").val(rowData.pos_TYPE).change();
					  		$("#pos_ID").val(rowData.pos_ID).change();
					  		$("#oldDeptPosId").val(deptID+"__"+rowData.pos_ID);
						//}
						decide="update";
						$("#showDeptPosPag").show();
						$("#showDeptPosPag").dialog({
							width : $(window).width() - 50,
					        height : $(window).height() - 50,
							modal: true,
							title: "部門職稱資料",
							resizable: false,
							position: { my: "center", at: "center"},
							close: function() {
						  		$("#parent_DEPT_POS_ID").val("");
						  		$("#pos_ID").empty();
						  		$( "#pos_ID" ).append("<option value=''>請選擇</option>");
						  		$("#showDeptPosPag").dialog('destroy');
								$("#showDeptPosPag").hide();
						    },
						    open: function (event, ui) {
						    	$('.ui-dialog').css('z-index',10300);
						    	$('.ui-widget-overlay').css('z-index',10200);
						   	}
						});
					}
				});		
			}else if(posId.length>1){
				alert("只能修改一筆資料");
			}else{
				alert("請選擇一筆資料");
			}
		});//UpdateButton end
		
		$('#deptPosDeleteBtn').click(function() {
			var deptID = $("#deptID").val();
			var psnNo = listenerDeptPosCheckBox();
			var deptPosId =[];
			for(var i =0;i<psnNo.length;i++){
				deptPosId[i]=deptID+"__"+psnNo[i];
			}
			if(psnNo.length<1){
				alert("請選擇一筆資料");
				return false;
			}
			if(confirm("確定執行刪除?")){
				$.ajax({
					type : 'POST',
					url : "<s:url value='/org/dept/deptPos/delete'/>",
					async : false,
					data :{
						"deptPosId" : deptPosId.toString()
					},
					success : function(data) {
						var result = data['result'];
						var message = data['message'];
						if(result==true){
							alert('<s:message code="msg.delete.success"/>');
							
						}else if(message !='' && message !=null){
							alert(message);
						}else{
							alert('<s:message code="msg.delete.fail"/>');
						}
						createPositonTable(deptID);
					}
				});		
			}
		});//deleteButton end
		
		$('#deptPosSaveBtn').click(function() {
			if ($('#deptPosForm')[0].checkValidity()) {
				event.preventDefault();
				if(decide=="add"){
					$.ajax({
						url : "<s:url value='/org/dept/deptPos/addSave'/>",
						type : 'POST',
						data : $("#deptPosForm").serialize(),
						success : function(data){
							if(data==true){
								alert('<s:message code="msg.add.success"/>');
								var deptId = $("#deptID").val();
								createPositonTable(deptId);
								searchDeptPosList(deptId);
								var manager = $("#oldManager").val();
								$("#manager option[value="+manager+"]").prop("selected", "selected");
								$("#showDeptPosPag").dialog('close');
							}else{
								alert('<s:message code="msg.add.dataExists"/>');
							}
							
						}
					});
				}else{
					var posId = listenerDeptPosCheckBox();
					var oldDeptPosId = $("#oldDeptPosId").val();
					$.ajax({
						url : "<s:url value='/org/dept/deptPos/updateSave'/>",
						async: false,
						type : 'POST',
						data : $("#deptPosForm").serialize()+"&oldDeptPosId="+oldDeptPosId,
						success : function(data){
							var manager = $("#manager").val();
							var result = data['result'];
							var newPosId = data['podId'];
							if(result==true){
								alert('<s:message code="msg.update.success"/>');
								var deptId = $("#deptID").val();
								createPositonTable(deptId);
								searchDeptPosList(deptId);
									if(posId==manager){
										$("#manager option[value="+newPosId+"]").prop("selected", "selected"); //.val(newPosId);
									}else{
										$("#manager option[value="+manager+"]").prop("selected", "selected"); //.val(newPosId);
									}
								$("#showDeptPosPag").dialog('close');
							}else{
								alert('<s:message code="msg.update.dataExists"/>');
							}
							
						}
					});
				}
			}
		});//saveButton end


		$( "#pos_type_filter" ).change(function() {
			$.ajax({
				url : CONTEXT_URL + "/org/dept/deptPos/searchPosition",
				type : 'POST',
				async : false,
				data : {
					posType : $("#pos_type_filter").val()
				},
				success : function(posList){
					$("#pos_ID").empty();
					$("<option value=''>請選擇</option>").appendTo("#pos_ID");
					for(var pos in posList){
						$("<option value='"+posList[pos].pos_ID+"' filterType='"+posList[pos].pos_TYPE+"' name='"+posList[pos].pos_ID+"'>"+posList[pos].pos_NAME+"</option>").appendTo("#pos_ID");
					}
				}					
	       	});
		});
		
		$('#deptTable tbody').off('click', 'tr').on( 'click','tr', function () {
			var data = $('#deptTable').dataTable().fnGetData($(this).closest("tr").index());
			 if ( !$(this).hasClass('deptTableCSS') ) {
				 $('#deptTable').dataTable().$('tr').removeClass('deptTableCSS');
				$(this).addClass('deptTableCSS');
	         }
	         else {
	        	 $('#deptTable').dataTable().$('tr').removeClass('deptTableCSS');
	         }
			
			listenerDeptPos(data.posId,data.posType);
		});

		WinMove();
		$(window).on('resize', function () {
			if($('#deptTable').dataTable().fnSettings()!= null){
				$('#deptTable').dataTable().fnAdjustColumnSizing();
			}
		} );
	});//document.ready end
	//重新產生表格
	function createPositonTable(deptId){
		var disable="";
		if('${showType}'=="view"){
			var disable=" disabled";
		}
		var table = $('#deptTable').dataTable( {
			"bDestroy": true,
			"iDisplayLength" : -1,
			"aaSorting" : [[ 0, "desc" ]],
			"sDom" : "rtS",
// 			"aAutoWidth":true,
			"sScrollY" : 'auto',
	        "sScrollX" : "100%",
	        "bProcessing" : false,
	        "sAjaxDataProp" : "rows",
	        "sAjaxSource" : CONTEXT_URL+'/org/dept/deptPos/deptPosTable?deptId='+deptId,
	        "aoColumnDefs" : [ {
	           	"aTargets" : [0], bSortable : false, "mData" : "posId",
	           	"mRender": function ( data, type, full ) {
	             	return '<input type="checkbox"  onclick="listenerDeptPosCheckBox();" '+disable+' name="deptPos" value="'+data+'">';
	            }},
	            { "aTargets": [1], "mData": "posId"},
	            { "aTargets": [2], "mData": "posName"},
	            { "aTargets": [3], "mData": "posType"},
	           ],
	        "oLanguage":{
	        	"sProcessing": "處理中...",
	        	"sZeroRecords": "沒有匹配結果",
	        	"sLoadingRecords": "讀取中..."
	        },"fnInitComplete": function() {
	        	this.fnAdjustColumnSizing();
	        }
		});
		
	}
	//重新產生主管的下拉選單
	function searchDeptPosList(deptId){
		$.ajax({
			url : "<s:url value='/org/dept/deptPos/deptPosTable'/>",
			async: false,
			type : 'POST',
			data :{ "deptId" : deptId },
			success : function(data){
				var records = data.rows;
				$('#manager').empty();
				$("<option value=''></option>").appendTo("#manager");
				for(var record in records) {
					$("<option value='"+records[record].posId+"'>"+records[record].posId+"</option>").appendTo("#manager");
				}
			}
		});
	}
	
	function listenerDeptPosCheckBox(){
		var posNo = [];
		$('input:checkbox:checked[name="deptPos"]').each(function(i) {posNo.push(this.value);});
		return posNo;
	}
	
	function listenerDeptPos(posId,posType){
		var deptID = $("#deptID").val();
		open03M(deptID+"__"+posId,posId,posType);
	}
	
	function open02M(deptIDNode){
		$.ajax({
			type : 'POST',
			url : CONTEXT_URL + "/org/dept/deptPos",
			data :{
				"deptId" : deptIDNode,
			},
			async : false,
			success : function(data) {
				var deptName = data['deptName'];
				var deptId = data['deptId'];
				var manager = data['manager'];
				var posList = data['posList'];
				$("#dept_name").text(deptName);
				$("#oldManager").val(manager);
				$("#deptID").val(deptId);
				$("#pos_type_filter").val("all").change();
				$("#manager").empty();
				searchDeptPosList(deptId);
				if(manager == "" || manager ==null){
					$("#manager").val("");
				}else{
					$("#manager").val(manager);
				}
		 		createPositonTable(deptId);
				$("#ORG002M_02MPage").show();
				$("#deptPosButtons").show();
				$("#ORG002M_03MPage").hide();
				$("#psnPosButtons").hide();
				org002M02MShowSet('${showType}');
			}
		});
		
	}
	
	//檢視畫面設定
	function org002M02MShowSet(btnType){
		if(btnType=="view"){
			document.getElementById("manager").disabled = true;
		}		
	}
</script>
<style>
.labelStyle {
   margin-top: 5px;
   margin-left: 15px;
}
.deptTableCSS{
	background-color:#beebff;
}
</style>