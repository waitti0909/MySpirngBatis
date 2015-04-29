<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title>Insert title here</title>
<style type="text/css">
hr {
	display: block;
	margin-top: 0.5em;
	margin-bottom: 0.5em;
	margin-left: auto;
	margin-right: auto;
	border-style: inset;
	border-width: 1px;
}

.ftSolidLine {
	display: block;
	border: 1px solid #000000;
	margin-left: 2px;
	margin-right: 2px;
	padding-top: 0.35em;
	padding-bottom: 0.625em;
	padding-left: 0.75em;
	padding-right: 0.75em;
}

.legend {
	width: inherit;
	font-size: 14px;
	padding: 3px 5px;
	border-bottom: 0px dashed #B6B6B6;
	margin-bottom: 2px;
}

.selected {
  background-color: #b0bed9;
}
</style>


<script src="../resources/plugins/scrolltable/jquery.scrolltable.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	
	var qType = '${qType}';
	var tempSelect='0';
	//判斷修改型態
	if(qType =='租金起算日異動'){
		$('#rentCHDIV').show();
		$('#housesCHDIV').hide();
		var jsons='${json}';
		if(jsons != '' && jsons != null){
			json = JSON.parse(jsons);
			for(var i = 0;i<json.length;i++){
				var tempString = '<tr>';
				tempString += '<td class="" style="width: 17%;">'+json[i]['LOCATION_ID']+'</td>';
				tempString += '<td class="" style="width: 17%;">'+json[i]['LOC_NAME']+'</td>';
				tempString += '<td class="" style="width: 17%;">'+json[i]['LOC_TYPE']+'</td>';
				tempString += '<td class="" style="width: 16%;">'+json[i]['PAY_BEGIN_DATE']+'</td>';
				tempString += '<td class="" style="width: 17%; padding: 3px 5px 3px 5px"><input class="datePicker form-control" name="rentTime" readonly="readonly" placeholder="點選輸入框" type="text" style="width: 100%;" value="'+json[i]['PAY_BEGIN_DATE']+'">';
				tempString += '<input type="hidden" name="rentLsNo" value="'+json[i]['LS_NO']+'">';
				tempString += '<input type="hidden" name="rentLsVer" value="'+json[i]['LS_VER']+'">';
				tempString += '<input type="hidden" name="rentLocationId" value="'+json[i]['LOCATION_ID']+'">';
				tempString += '</td></tr>';
				$('#rentTbody').append(tempString);
			}	
		}
	}else{
		$('#rentCHDIV').hide();
		$('#housesCHDIV').show();
		
		//出租人下拉選單設置
		
		
		var temp=[];
		var jsons='${json}';
		if(jsons != '' && jsons != null){
			var json = JSON.parse(jsons);
			for(var i = 0;i<json.length;i++){
				if(i == 0){
					$('#selectLessorName').append("<option value='"+i+"' selected='selected' >"+json[i]['LESSOR_NAME']+"</option>"); 
					$("#oldHouseTaxNo").text(json[i]['HOUSE_TAX_NO']);
				}else{
				 $('#selectLessorName').append("<option value='"+i+"'>"+json[i]['LESSOR_NAME']+"</option>"); 
				}
				var ls = [json[i]['LS_NO'],json[i]['LS_VER'],json[i]['LESSOR_ID'],json[i]['HOUSE_TAX_NO']];
				temp[i]=ls;
			}
		}
		
		//暫存出租人選取前的值
		$("#selectLessorName").focusin(function() {
			tempSelect = $(this).val();
		});
		
		//出租人下拉值改變時的檢核動作
	 	$("#selectLessorName").change(function() {
	 		if($.trim($("#newHouseTaxNo").val()) != ''){
	 			if(confirm("已有輸入房屋稅籍是否離開?")){
	 				$("#newHouseTaxNo").val('');
	 				$("#oldHouseTaxNo").text(temp[$(this).val()][3]);
	 			}else{
	 				$("#selectLessorName").val(tempSelect);		
	 			}
	 		}else{
	 			$("#oldHouseTaxNo").text(temp[$(this).val()][3]);
	 		}
		}); 
	}

	
	$("select").select2();
	//儲存按鈕
	$("#btn_save").click(function() {
		if(qType =='租金起算日異動'){
			var count = 0 ;
			$("#rentTbody input").each(function() {
				if($(this).val() == ''){
					count++;
				}
			});
			if(count == 0){
				var data = $("#editPayForm").serializeArray();
				$.ajax({			
					url : CONTEXT_URL + "/ls/LS002/updayeLocationSitePayBeginDate",		
					data: data,
					type : "POST",
					dataType : 'json',
					success : function(data) {
						alert(data.msg);
						if(data.success){					
							parent.$.fancybox.close();
						}
					}
				});
			}else{
				alert("會計確認日期請都填入資料");
			}
		}else{
			if($.trim($("#newHouseTaxNo").val()) != ''){
				var data = {
						lsNo : temp[$("#selectLessorName").val()][0],
						lsVer : temp[$("#selectLessorName").val()][1],
						lessorId : temp[$("#selectLessorName").val()][2],
						newTaxNo : $.trim($("#newHouseTaxNo").val())
					};
				$.ajax({			
					url : CONTEXT_URL + "/ls/LS002/saveUpdateHouseTaxNo",		
					data: data,
					type : "POST",
					dataType : 'json',
					success : function(data) {
						alert(data.msg);
						if(data.success){					
							parent.$.fancybox.close();
						}
					}
				});
				}else{
					alert("請填寫房屋稅籍編號!");
				}
		}
	});
	

	



	$('.datePicker').datepicker({
		dateFormat : "yy/mm/dd",
		changeYear : true,
		changeMonth : true,
		showButtonPanel : true
	});
	
	$('#siteTB').scrolltable({
		stripe : true,
		oddClass : 'odd',
		maxHeight : 160
	});
	
	$('#siteTB table:last').addClass('table-striped table-hover');
	
	$("#editHouseForm :input").prop("disabled", ${flag});
	$("#editPayForm :input").prop("disabled", ${flag});
	$("#btn_save").prop("disabled", ${flag});
});
</script>
</head>
<body>
	<div class="row">
		<div class="col-xs-12 col-sm-12">
			<div class="box ui-draggable ui-droppable">
				<div class="box-header">
					<div class="box-name">
						<i class="fa fa-search"></i> <span>合約編輯</span>
					</div>
				</div>
				<div class="box-content">
						<button class="btn btn-primary btn-label" type="button" 
							id="btn_save">儲存</button>
						<table style="width: 100%;margin-top: 10px">
							<tr>
								<td nowrap="nowrap" width="6%"><label
									class="col-sm-12 control-label">合約編號 :</label></td>
								<td width="20%">
									<p style="padding-top: 5px; padding-left: 5px;" id="">${lsNo}</p>
								</td>
								<td nowrap="nowrap" width="10%"><label
									class="col-sm-12 control-label">合約名稱 :</label></td>
								<td width="15%">
									<p style="padding-top: 5px; padding-left: 5px;" id="">${lsName}</p>
								</td>
								<td nowrap="nowrap" width="18%" align="right"><label
									class="col-sm-12 control-label">合約起迄 :</label></td>
								<td width="31%">
									<p style="padding-top: 5px; padding-left: 5px;" id="">${S_Date}~${E_Date}</p>
								</td>
							</tr>

							<tr>
								<td align="right"><label class="col-sm-12 control-label">出租人
										:</label></td>
								<td COLSPAN=3>
									<p style="padding-top: 5px; padding-left: 5px;" id="">${lessorName}</p>
								</td>
								<td><label class="col-sm-12 control-label">合約紙本保管單位/地點
										:</label></td>
								<td>
								    <p style="padding-top: 5px; padding-left: 5px;" id="">${deptPlace}</p>
								</td>
							</tr>
						</table>
						<hr>

						<div>
							<table style="width: 100%;margin-top:10px">								
								<tr>
									<td nowrap="nowrap" width="9%" align="right"><label class="col-sm-12 control-label">類別
											:</label></td>
									<td width="42%">
										<div class="col-sm-8">
										<p style="padding-top: 5px;" id="">${qType}</p>
											<%-- <select id="ls002Sel" class="populate placeholder" name=ls002Sel" >
												<option value="a" >租金起算日異動</option>
												<option value="b" >房屋稅籍編號異動</option>
											</select> --%>
										</div>
									</td>
									<td nowrap="nowrap" width="18%" align="right"><label class="col-sm-12 control-label">建立人員
											:</label></td>
									<td width="31%">
										<div class="col-sm-12">
											<p style="padding-top: 5px; padding-left: 5px;" id=""><c:out value="${loginUser.deptName}"/>&nbsp;/&nbsp;<c:out value="${loginUser.chName}"/></p>
										</div>
									</td>
								</tr>
							</table>
							</div>

							<div id="rentCHDIV" style="margin: 10px 0px 10px 0px;display:none;">
								<form class="form-horizontal" action="" id="editPayForm">
									<input type="hidden" name="addedSeq" id="addedSeq" value="${appSeq}">
									<fieldset class="ftSolidLine">
										<legend class="legend">租金起算日異動</legend>
										<table id="siteTB" class="scrollTable"
											style="table-layout: fixed; word-break: break-all; word-wrap: break-word; width: 100%;margin-top: 10px">
											<thead style="background-color: #f0f0f0;">
												<tr>
													<th style="width: 17%;height: 30px">站點編號</th>
													<th style="width: 17%;height: 30px">站點名稱</th>
													<th style="width: 17%;height: 30px">站點類別</th>
													<th style="width: 17%;height: 30px">租金起算日</th>
													<th style="width: 17%;height: 30px">會計確認日期</th>
													
												</tr>
											</thead>
											<tbody id="rentTbody">
											</tbody>
										</table>
									</fieldset>
								</form>
							</div>

						<div id="housesCHDIV" style="margin: 10px 0px 10px 0px;display:none;">
							<form class="form-horizontal" action="" id="editHouseForm">
								<fieldset class="ftSolidLine">
									<legend class="legend">房屋稅籍編號異動</legend>
	
									<div class="form-group">
										<label class="col-sm-2 control-label">出租人 :</label>
										<div class="col-sm-3">
											<select id="selectLessorName" class="populate placeholder" name="selectLessorName" >
											</select>
										</div>
									</div>
	
									<div class="form-group" style="margin-top: 2%">
										<label class="col-sm-2 control-label">原房屋稅籍編號 :</label>
										<div class="col-sm-3">
											<p style="padding-top: 5px;" id="oldHouseTaxNo"></p>
										</div>
										<label class="col-sm-2 control-label">房屋稅籍編號 :</label>
										<div class="col-sm-3">
											<input id="newHouseTaxNo" class="form-control" type="text" name="newHouseTaxNo" value="">
										</div>
									</div>
								</fieldset>
							</form>
						</div>
					</div>
			</div>
		</div>
	</div>
</body>
</html>