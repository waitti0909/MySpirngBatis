<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>角色維護</title>

<script type="text/javascript">
	// Run Select2 on element
	function Select2Test() {
		$("#s2_RoleSelect").select2();
	}
	$(document)
			.ready(
					function() {
						// Load script of Select2 and run this	
						LoadSelect2Script(
								'<s:url value="/resources/plugins/select2/select2.min.js" />',
								Select2Test);

		//查詢按鈕
		$("#btn_search").click(function() {		
				var data = {
					roleId : $.trim($("#s2_RoleSelect").val())
				};
				$.log('data data=' + data);
				$("#grid").setGridParam({datatype : "json",	postData : data, mtype: 'POST', page:1	}).trigger("reloadGrid");
		});

		//==================================================================

		$("#grid")
				.jqGrid(
						{
							datatype : "local",
							pager : '#pager',
							url : CONTEXT_URL + "/auth/searchRole/",
							colNames : [ '角色名稱', '角色說明', '角色修改人員', '角色修改日期', '成員',
									'roleId' ],
							colModel : [ 
							    {name : 'roleName',index : 'roleName',width : 60 , align : 'center',sortable : false}, 
							    {name : 'roleDesc',index : 'roleDesc',sortable : false}, 
							    {name : 'mdUser',index : 'mdUser',width : 60 , align : 'center',sortable : false}, 
							    {name : 'mdTime',index : 'mdTime',width : 80,align : 'center',sortable : false}, 
							    {name : 'psnMember',index : 'psnMember',sortable : false}, 
							    {name : 'roleId',index : 'roleId',sortable : false,hidden : true}
							    ],
							multiselect : false,
							caption : "角色清單",
							rownumbers : true,
							onSelectRow : function() {
								// do something when select a row
							},
							gridComplete : function() {
								// do something after grid loaded
							},
							ondblClickRow: function(rowId) { //double clicking a jqgrid row
					            var rowData = jQuery(this).getRowData(rowId);
					            $.each(rowData, function(i,item) {
					            	if(i=="roleId"){
					            		editPsnRole(item,"view");
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

		//新增按鈕
		$('#btn_add').click(function() {
			$.fancybox.open({
				href : CONTEXT_URL + "/auth/roleAdd/",
				type : 'ajax',
		        width : $(window).width(),
		        height : $(window).height(),
				autoSize : false,
				padding : [ 0, 0, 0, 0 ],
				scrolling : 'no',
				helpers : {
					overlay : {
						closeClick : false
					},
					title : {
						type : 'float'
					}
				},
				title : "新增表單",
				openEffect : 'elastic',
				closeEffect : 'fade',
				ajax : {
					type : 'POST'
				},

				afterShow : function() {
					$('#addForm :input:enabled:visible:first').focus();
				}
			});
		});//addButton end
		
		//修改按鈕
		$("#btn_edit").click(function() {
			//取得被選取列
			var ids = $("#grid").jqGrid('getGridParam','selrow');
			//是否選取
			if (ids == null) {
				alert("請選擇一筆資料編輯");
				return false;
			}else{
				var row = $("#grid").jqGrid('getRowData',ids);
				editPsnRole(row.roleId,"edit");
			}			
		});
		

		//重置按鈕
		$("#btn_reset").click(function() {
			$("#s2_RoleSelect").select2("val", "");		
			$("#grid").jqGrid().clearGridData();
		});

		//刪除按鈕
		$("#btn_delete").click(function() {		
			//取得被選取列
			var ids = $("#grid").jqGrid('getGridParam','selrow');
			//是否選取
			if (ids == null) {
				alert("請選擇一筆資料刪除。");
				return false;
			}else{
				var row = $("#grid").jqGrid('getRowData',ids);
				deleteConfirmChoice(row.roleId);
			}		
		});
	});

//**************FUNCTION**************

	//確認刪除否
	function deleteConfirmChoice(sendInfo) {
		if (confirm("確定刪除?")) {
			$.ajax({
						url : '<s:url value="/auth/roleDelete/" />',
						data : {roleID: sendInfo},
						type : "POST",
						success : function(data) {
							alert(data.msg);
							if (data.success) {	   
							    var ohtml = '';	
								var tohtml = '';	
								$('#s2_RoleSelect')[0].options.length = 0;  //清空下拉選單
								tohtml += ('<option value="">-- 請選擇 --</option>');
								$('#s2_RoleSelect').append(tohtml);								
								$.each(data.rows, function() {
									ohtml += ('<option value="'+this.id+'">'+this.name+"-"+this.desc+'</option>');
								});			
								$('#s2_RoleSelect').append(ohtml);
								//刪除後重載jqgrid
								$("#grid").trigger("reloadGrid");
							} else {
								alert("無法刪除，某使用者歸屬於此角色!");
							}
						},
						complete : function() {
							$("#s2_RoleSelect").select2("val", "");
						},
						error : function(jqXHR, testStatus, errorThrown) {
							alert(jqXHR);
						},
						//dataType : "text",
						async : false
					});

		} else {
			$("#grid").trigger("reloadGrid");
		}
	}
	
	//角色與使用者之設定與編輯呈現
	function editPsnRole(roleid,btnType) {
		$.fancybox.open({
			href : CONTEXT_URL + "/auth/searchUserRole/",
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
						roleID : roleid
					},
					type : "POST",
					error : function(jqXHR, testStatus, errorThrown) {
						alert(jqXHR);
					},
					async : false
			},
			afterShow : function() {
				document.getElementById("roledesc").focus(); 
			},
			afterClose : function() {
				// fancybox關閉後要做的事(callback function)
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
<!-- 				<li><a href="#">權限管理</a></li> -->
<!-- 				<li><a href="#">角色維護</a></li> -->
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
						<i class="fa fa-search"></i> <span>系統角色查詢</span>
					</div>
					<div class="box-icons">
						<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
						</a> <a class="expand-link"> <i class="fa fa-expand"></i>
						</a>
					</div>
					<div class="no-move"></div>
				</div>

				<div class="box-content">
<!-- 					<h4 class="page-header">查詢條件</h4> -->
					<form class="form-horizontal bootstrap-validator-form" role="form" id="searchFrom" novalidate="novalidate">
						<div class="form-group">
							<label class="col-sm-2 control-label">系統角色</label>
							<div class="col-sm-6">
								<select class="populate placeholder" name="roleID"
									id="s2_RoleSelect">
									<option value="">-- 請選擇 --</option>
									<c:forEach items="${allRoles}" var="role">
										<option value="${role.id}">${role.name}-
										<c:if test="${role.desc!=null}">
										${role.desc}
										</c:if>
										</option>
									</c:forEach>
								</select>
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