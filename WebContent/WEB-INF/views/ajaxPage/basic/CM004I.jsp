<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<title>PO工項維護</title>
		<script src="<s:url value="/resources/plugins/bootstrapvalidator/bootstrapValidator.min.js" />"></script>
		<script src="<s:url value="/resources/plugins/scrolltable/jquery.scrolltable.js" />"></script>
	</head>
	<body>
		<div class="row">
		<div class="col-xs-12 col-sm-12">
			<div class=" ui-draggable ui-droppable">
				<div class="box-header">
					<div class="box-name">
						<i class="fa fa-search"></i> <span>PO工項維護</span>
					</div>
				</div>
				<div class="box-content">
				<form id="addEdit" class="form-horizontal" method="post" enctype="multipart/form-data">
					<input id="file_A" type="file" name="file" class="require" style="display: none;" accept=".xls, .xlsx" />
					<div style="padding-top: 5px; padding-left: 15px">
						<button class="btn btn-primary btn-label"
							style="margin-right: 10px" type="button" id="cm004MPOSaveBtn">
							<span><i class="fa fa-save"></i></span>
							存檔</button>
						<input id="files" type="file" name="file" class="" style="display: none;" accept=".xls, .xlsx" />
						<button class="btn btn-primary btn-label"
							style="margin-right: 10px" type="button" id="cm004MPOImportBtn">
							<span><i class="fa fa-file"></i></span>
							匯入工項</button>
						<button class="btn btn-primary btn-label"
							style="margin-right: 10px" type="button" id="cm004MPOCloseBtn">
							關閉</button>
					</div>
					<div style="border: 1px solid #000000; padding: 10px 5px 5px 5px">
						<table style="width: 100%">
							<tr>
								<td width="15%" align='right'><label
									class="col-sm-12 control-label">年度 :</label></td>
								<td width="20%">
									<p style="padding-top: 6px; padding-left: 2px;" id=""><c:out value="${poMain.PO_YEAR}" /></p>
								</td>
								<td width="10%" align='right'><label
									class="col-sm-12 control-label">PO廠商 :</label></td>
								<td width="20%">
									<p style="padding-top: 6px; padding-left: 2px;" id=""><c:out value="${company.CO_NAME}" /></p>
								</td>
								<td width="10%" align='right'><label class="col-sm-12 control-label">PO單號
										:</label></td>
								<td width="20%">
									<p style="padding-top: 6px; padding-left: 2px;" id=""><c:out value="${poMain.PO_NO}" /></p>
								</td>
							</tr>
							<tr>
								<td colspan="6">
									<table id="mainItemTB" class="scrollTable"
										style="table-layout: fixed; word-break: break-all; word-wrap: break-word;width: 100%" >
										<thead style="background-color: #f0f0f0;">
											<tr>
												<th class="theadth" style="width: 15%">序號</th>
												<th class="theadth" style="width: 40%">主項</th>
												<th class="theadth" style="width: 45%">中項</th>												
											</tr>
										</thead>
										<tbody id="mainItemBody">
											<c:forEach items="${subCatItemList}" var="subCatItem">
												<tr>
													<td class="tbodytd" style="width: 15%"><c:out value="${subCatItem.CAT_NO}"/></td>
													<td class="tbodytd" style="width: 40%"><c:out value="${subCatItem.parentCatName}"/></td>
													<td class="tbodytd" style="width: 45%"><c:out value="${subCatItem.CAT_NAME}"/></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</td>
							</tr>
						</table>
						</div>
						<div style="padding-top: 5px; padding-left: 15px">
							<table style="width:30%">
								<tr>
									<td width="5%" nowrap="nowrap"  align="right">
										ERP FA資產分類 :
									</td>
									<td width="20%"align="left">
										<div class="col-sm-6"  align="left">
											<input id="subMainfaCategory" name="subMainfaCategory" type="text" class="form-control" maxlength="100"/>
										</div>
									</td>
								</tr>
							</table>
						</div>
														
						<!-- 動態生成下方TABLE -->
						<div style="padding-top: 15px;" id="subItemTBDiv"></div>
						</form>
					</div>
			</div>
		</div>
	</div>
	</div>
		<!-- TODO -->
		<div id="showAddItemFrame"></div>

<style type="text/css">
#controls {
	float: left;
	padding: 0.3em 1em;
}

table.scrollTable {
	width: 100%;
	border: 2px solid #ddd;
	border-color: #000000;
}

.theadth {
	text-align: left;
	padding: 0.1em 0.3em;
}

.tbodytd {
	border-top: 1px solid #eee;
	border-right: 1px solid #eee;
	padding: 0.1em 0.3em;
}

.hover {
    background-color:LightSkyBlue;
}

.subItemSelect {
    background-color:yellow;
}
</style>
<script type="text/javascript">
	var poItemList;
	var catNo;
	var poId;
	var saveBtnCheck = true;
	
	$(document).ready(function() {
		poId = '${poMain.PO_ID}';
		$('#mainItemTB').scrolltable({
			stripe : true,
			oddClass : 'odd',
			maxHeight : 80
		});
		
		$('#subItemTB').scrolltable({
			stripe : true,
			oddClass : 'odd',
			maxHeight : 250
		});
		
		loadPoItemList();
		
	});
	
	$("#mainItemBody td")
		.mouseenter(function() {
			$(this).parent("tr").addClass("hover");
		})
		.mouseout(function() {
			$(this).parent("tr").removeClass("hover");
		});
	
	  $("#mainItemTB").delegate("td.tbodytd", "click", function(){
		  $("#mainItemBody tr").each(function(i){
			  $(this).removeClass("subItemSelect");
		  });
		  $(this).parent("tr").addClass("subItemSelect");
		  catNo = $(this).parent("tr").find('td:eq(0)').text();
		  buildItemMainTable();
	   });
	  
	function buildItemMainTable(){
		$("#subItemTBDiv").empty();
        getAllItemMain();
	}
	  
	function getAllItemMain() {
		$.ajax({			
			url : CONTEXT_URL + "/cm/po/getAllItemMain",		
			data: {catNo : catNo,
				   poId : poId},
			type : "POST",
			dataType : 'json',
			success : function(data) {
				if(data['itemList'].length > 0){
					saveBtnCheck = false;
					$("#subMainfaCategory").val(data['subMainfaCategory']);
					makeItemMainTable(data['itemList']);
				}
			}
		});
	}
	
	function loadPoItemList() {
		poItemList = "";
		$.ajax({			
			url : CONTEXT_URL + "/cm/po/getPoItemList",		
			data: {poId : poId},
			type : "POST",
			dataType : 'json',
			success : function(data) {
				if(data.rows.length > 0){
					poItemList = data;
				}
			}
		});
	}
	
	function makeItemMainTable(data) {
		var tableStr = '<table id="subItemTB" class="scrollTable" border=2 style="table-layout: fixed; word-break: break-all; word-wrap: break-word; width: 100%">';
		tableStr +=    	'<thead style="background-color: #f0f0f0;">';
		tableStr +=   		'<tr>';
		tableStr +=   			'<th class="theadth" style="width: 5%">核選</th>';
		tableStr +=   			'<th class="theadth" style="width: 10%">項次</th>';
		tableStr +=   			'<th class="theadth" style="width: 50%">小項</th>';
		tableStr +=   			'<th class="theadth" style="width: 10%">工時</th>';
		tableStr +=   			'<th class="theadth" style="width: 10%">單位</th>';
		tableStr +=   			'<th class="theadth" style="width: 15%">單價</th>';
		tableStr +=   		'</tr>';
		tableStr +=    	'</thead>';
		tableStr +=    	'<tbody id="signHistoryTbody">';
		for(i=0; i < data.length; i++){
			tableStr +=   		'<tr>';
			var ischecked = "";
			//帶入預設單價(若已經存檔則帶出實際單價)
			var price = data[i].price;
			var disabled;
			var readonly;
			var usedTimes = "0";
			
			if (poItemList != undefined && poItemList != "") {
				for(j=0; j < poItemList.rows.length; j++){
					if (poItemList.rows[j].item_ID == data[i].item_ID) {
						ischecked = "checked";
						disabled = poItemList.rows[j].siteUsedTimes != "0" ? "disabled" : "";
						readonly = poItemList.rows[j].siteUsedTimes != "0" ? "readonly" : "";
						price = poItemList.rows[j].price;
						break;
					} 
				}
			}
			tableStr +=   			'<td class="tbodytd" style="width: 5%"><div class="row form-group"><div class="checkbox-inline" style="margin-left: 15px"><label><input type="checkbox" id="" name="" class="itemCheckbox" ' + disabled + ' value="' + data[i].item_ID +'" '+ ischecked +'/><i class="fa fa-square-o"></i></label></div></div></td>';
			tableStr +=   			'<td class="tbodytd" style="width: 10%">' + data[i].item_NO +'</td>';
			tableStr +=   			'<td class="tbodytd" style="width: 50%">' + data[i].item_NAME +'</td>';
			tableStr +=   			'<td class="tbodytd" style="width: 10%">' + data[i].unit_HOUR +'</td>';
			tableStr +=   			'<td class="tbodytd" style="width: 10%">' + data[i].unit +'</td>';
			tableStr +=   			'<td class="tbodytd" style="width: 15%">';
			tableStr +=   				'<input id="" name="price" ' + readonly + ' style="margin : 5px 0px 5px 0px;" class="form-control"  maxlength="18" value="' + price +'"/>';
			tableStr +=   			'</td>';
			tableStr +=   		'</tr>';
		}
		tableStr +=    	'</tbody>';	
		tableStr +=   '</table>';
		
		$("#subItemTBDiv").append(tableStr);
	}
		
	$("#cm004MPOSaveBtn").click(function(){
		if (saveBtnCheck) {
			alert("請查詢資料再存檔!");
			return;
		}
		if ($("#subMainfaCategory").val() == "") {
			alert("ERP FA資產分類 不得空白!");
			$("#subMainfaCategory").focus();
			return;
		}
		var faCategory = $("#subMainfaCategory").val();
		var datas=[];
		var dataNotValid = false;
		
		$('.itemCheckbox').each(function(i) {
			if ($(this).prop("checked")) {
				priceStr = $(this).parents("tr").find('td:eq(5) input').val();
				console.log("priceStr: " + priceStr + ",this : " + $(this).prop("checked"));
				var reg = /^[0-9]+(.[0-9]{1,2})?$/;
				if(!reg.test(priceStr)) {
					alert('單價金額格式錯誤');
					$(this).parents("tr").find('td:eq(5) input').focus();
					dataNotValid = true;
					return;
				}
				datas.push({
					  itemId : this.value,
					  price : $(this).parents("tr").find('td:eq(5) input[name="price"]').val(),
				});
			}
		});
		
		if (dataNotValid) {
			return;
		}
		
		$.ajax({
			url : CONTEXT_URL + "/cm/po/savePoItem",
			type : "POST",
			data : "jArray=" + JSON.stringify(datas) + "&catNo=" + catNo + "&poId=" + poId + "&faCategory=" + faCategory,
			dataType : "json",
			async : false,
			success : function(data) {	
				alert(data.msg.replace("error", ""));
				if (data.msg.indexOf("error") >= 0) {
					return false;
				}
				loadPoItemList();
			}
		});
	});
	
	$("#cm004MPOCloseBtn").click(function(){
		parent.$.fancybox.close();
	});
	
	// 準備上傳檔案設定
	var files;
	function prepareUpload() {
		files = event.target.files;
	}
	
	$("#cm004MPOImportBtn").click(function(){
		if ($.trim($("#file_A").val()) == "") {
			$("#file_A").click();
		}
	});
	
	// 匯入報表Button
	$("#file_A").change(function () {
		if ($.trim($("#file_A").val()) != "") {
			prepareUpload();
			uploadFiles();
			$("#file_A").val("");
		}
	});
	
	// 檔案上傳
	function uploadFiles() {
		event.stopPropagation(); // Stop stuff happening
		event.preventDefault(); // Totally stop stuff happening 
		var duplicateFile = false;
		var filename = $.trim($("#file_A").val());
		var selname = filename.substr(filename.indexOf(".") + 1,filename.length);
		if (filename == "") {
			alert("請選擇檔案");
			return false;
		} else if (selname != "xls" && selname != "xlsx") {
			alert("檔案類型須為 xls,xlsx");
			$("#file_A").val("");
			return false;
		} else {
			duplicateFile = true;
		}

		if (duplicateFile) {
			var data = new FormData();
			$.each(files, function(key, value) {
				data.append(key, value);
			});
			data.append("poId", poId);
			
			$.ajax({
				url : CONTEXT_URL + "/cm/po/importExcel",
				type : "post",
				data : data,
				cache : false,
				dataType : "json",
				processData : false, // Don't process the files
				contentType : false, // Set content type to false as jQuery will tell the server its a query string request
				success : function(data, textStatus, jqXHR) {
					alert(data.msg.replace("error", ""));
					if (data.msg.indexOf("error") >= 0) {
						return false;
					}
					//將新匯入的小項checkbox顯示為選取，並更更新單價
					loadPoItemList();
					//有點選過中項的情況
					if (catNo != undefined && catNo != "") {
						buildItemMainTable();
					}
				}
			});
		}
	}

	
</script>
</body>
</html>

