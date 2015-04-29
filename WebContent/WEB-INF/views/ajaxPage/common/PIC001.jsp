<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<title>圖資上傳作業</title>
<script type="text/javascript">
	$(document).ready(function() {
		
		buildCityOptions("siteCityPic001", "siteTownPic001");  //取行政區
		LoadSelect2Script(
				'<s:url value="/resources/plugins/select2/select2.min.js" />',
				picSel2);
		
		//查詢鈕
		$('#btn_search').click(function(){	
			if($('#siteCityPic001').val()==''||$('#siteTownPic001').val()==''){
				alert("請選擇行政區!");
			}else{
				$.ajax({
					url : CONTEXT_URL + "/common/PIC001/search",
					type : 'POST',
					data : {
						zip : getZipCode($('#siteTownPic001').val()),
						typePath : $('#typePath').val()
					},
					async : false,
					dataType : 'html',
					success : function(data) {
						$('#upFileDiv').html(data);
					}
				});
			}		
		});
		
		//重置鈕
		$('#btn_reset').click(function(){
			$("#pic001From").trigger('reset');
			reSetDiv();
		});
				
	});
	
	//清空檔案上傳DIV
	function reSetDiv(){
		$('#upFileDiv').html("");
	}
	
	function picSel2() {
		$("select").select2();
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
						<i class="fa fa-search"></i> <span>圖資上傳作業</span>
					</div>
					<div class="box-icons">
						<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
						</a> <a class="expand-link"> <i class="fa fa-expand"></i>
						</a>
					</div>
					<div class="no-move"></div>
				</div>

				<div class="box-content">
					<form class="form-horizontal bootstrap-validator-form"
						id="pic001From" novalidate="novalidate">
						<table style="width: 100%; margin : 10px 0px 10px 0px">
							<tr>
								<td nowrap="nowrap" width="15%"><label
									class="col-sm-12 control-label"><span class="s">*</span>圖資類型
										:</label></td>
								<td width="85%">
									<div class="col-sm-4">
										<select class="populate placeholder" name="typePath" id="typePath">
											<option value="SIGNALMAP">路測涵蓋圖</option>
										</select>
									</div>
								</td>
							</tr>

							<tr>
								<td nowrap="nowrap" style="padding-top: 10px;"><label class="col-sm-12 control-label"><span
										class="s">*</span>行政區 :</label></td>
								<td width="80%" style="padding-top: 10px;">
									<div class="col-sm-12">
										<div
											style="width: 40%; height: auto; float: left; display: inline">
											<select id="siteCityPic001" class="populate placeholder">
											</select>
										</div>
										<div
											style="width: 40%; height: auto; float: left; display: inline; margin-left: 2%">
											<select id="siteTownPic001" class="populate placeholder" onChange="reSetDiv()">
											</select>
										</div>
									</div>
								</td>
							</tr>
						</table>
					</form>
				</div>

			</div>
		</div>
		<div id="upFileDiv" class="col-xs-12">
		</div>
	</div>
</body>
</html>