<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>增補協議</title>
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

table.scrollTable {
	width: 100%;
	border: 2px solid #ddd;
	border-color: #000000;
}

.theadth {
	text-align: left;
	padding: 0.3em 0.2em;
}

.tbodytd {
	border-top: 2px solid #eee;
	border-bottom: 2px solid #eee;
	border-right: 2px solid #eee;
	padding: 0.1em 0.3em;
	height: 25px
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
<script	src="<s:url value="/resources/js/lsCommon.js" />"></script>
<script type="text/javascript">
    var firstSeltype='';
    var selText = '';
	$(document).ready(function() {
			LoadSelect2Script('<s:url value="/resources/plugins/select2/select2.min.js" />',
								ls001M3Select2);
			initPage();
			
			var appMode=$("#appMode").text();
			
			//租補協議頁面在各個申請狀態下預設處理
			if(appMode=='NEW')
			{
				//新件申請單時，附件上傳和申請按鈕、套表列印唯讀
				$("#btn_1M3Upload").prop("disabled", true);
				$("#btn_1M3Apply").prop("disabled", true);
				$("#btn_1M3Print").prop("disabled", true);
				
				
				//若為新增，預設選為改至借電變更
				chTypePage("0");				
			}else if(appMode=='EDIT'||appMode=='VIEW')
			{
				//切換至申請單申請類別頁面
				var addType=$("#addType").val();
				chTypePage(addType);
				$("#selType").val(addType);
			}
			
			//附件上傳事件
			$("#btn_1M3Upload").click(function() {
				openFileDialogFrame("showFileUploadPage","LEASE",$("#appSeq").text(),"LEASE");
			});

			// 申請Button
			$("#btn_1M3Apply").click(function(){
				$.ajax({			
					url : CONTEXT_URL + "/ls/LS001M3/ls001M3ApplyNotValidator",		
					data:{
						"appSeq" : $("#appSeq").text(),
						"lsNo"   : $("#lsNo").text(),
						"type"   : "M3"
					},
					type : "POST",
					dataType : 'json',
					success : function(data) {
						alert(data.msg);
					}
				});
			});
	});

	function ls001M3Select2() {
		$("select").select2();
	}

	function ls001M3Select2() {
		$("select").select2();
	}
	
	//公用-選項欄位初始狀態
	function initState(appMode){
		if(appMode=='EDIT'||appMode=='VIEW')
		{
			var addItem=$("#addItem").val();
			$("#itemSEL").val(addItem).change();
			//畫面類別和項目欄位唯讀
			$("#selType").prop("disabled", 'disabled');
			$("#itemSEL").prop("disabled", 'disabled');

			$("#btn_1M3Apply").prop("disabled", true);//申請
			$("#btn_1M3Upload").prop("disabled", true);//附件上傳
			$("#btn_1M3Print").prop("disabled", true);
		}
	}
	
	
	function initPage() {
		$("#lsNo").text("${tbLsMain.LS_NO}");
		$("#laName").text("${tbLsMain.LS_NAME}");
		$("#lessorUser").text("${lessorUser}");
		$("#keepPlace").text("${keepDept}");
		$("#lsDate").text("${lsDate}");
		$("#lsVer").text("${tbLsMain.LS_VER}");
		$("#appSeq").text("${appSeq}");
		$("#appStatus").text("${appStatus}");
		$("#appMode").text("${appMode}");
		$("#addType").val("${tbLsApp.ADD_TYPE}");
		$("#addItem").val("${tbLsApp.ADD_ITEM}");
		$("#rentType").text("${tbLsMain.RENT_TYPE}");
	}
	
	//類別切換
	function getItem(typeID) {
		if (confirm("請儲存! 切換類別後，此頁變更資料將不保留，是否確定切換?")) {
			chTypePage(typeID);
		}else{
			$('#selType').val('0');
			$("#s2id_selType span:first").html("");
			$("#s2id_selType span:first").html(selText);
		}
	}
	

	function chTypePage(typeID) {
		$.ajax({
					url : CONTEXT_URL + "/ls/LS001M3/chPage",
					data : {
						typeID : typeID,
						appMode : $("#appMode").text()
					},
					type : "POST",
					dataType : "html",
					success : function(data) {
						$('#viewDealDiv').html("");
						$("#boDiv").html("");
						$("#boDiv").html(data);
						LoadSelect2Script(
								'<s:url value="/resources/plugins/select2/select2.min.js" />',
								ls001M3Select2);
					}
			});
	}

	//項目切換
	function getTbStyle(itemID) {
		if (itemID == '繼受出租人' || itemID == '變更負責人' || itemID == '變更出租人'
				|| itemID == '出租人更名' || itemID == '變更電匯帳戶') {
			$('#lessorPayerDiv').show();
			$('#accountChDiv').show();
			$('#lessorChDiv').show();
			$('#lessorChNDiv').show();

			if (itemID == '變更負責人' || itemID == '出租人更名' || itemID == '變更電匯帳戶') {
				$('#lessorChDiv').hide();
				if (itemID == '變更電匯帳戶') {
					$('#lessorChNDiv').hide();
				} else {
					$('#lessorChNDiv').show();
				}
			} else {
				$('#lessorChDiv').show();
			}

		} else {
			if (itemID == '變更印鑑') {
				$('#lessorPayerDiv').show();
				$('#accountChDiv').hide();
				$('#lessorChDiv').hide();
				$('#lessorChNDiv').hide();
			} else {
				$('#lessorPayerDiv').hide();
				$('#accountChDiv').hide();
				$('#lessorChDiv').hide();
				$('#lessorChNDiv').hide();
			}
		}

		if (itemID == '租金抵扣所得稅') {
			$('#offsetTaxDiv').show();
			$('#lessorPayerDiv').show();
		} else {
			$('#offsetTaxDiv').hide();
		}
		if (itemID == '租金起算日') {
			$('#rentDateChDiv').show();
			$('#phoneRewardDiv').hide();
			$('#siteIdChDiv').hide();
			$('#btn_1M3PrintRent').show();
			rentDateChTB();
		} else if (itemID == '手機回饋') {
			$('#rentDateChDiv').hide();
			$('#phoneRewardDiv').show();
			$('#siteIdChDiv').hide();
			$('#btn_1M3PrintRent').hide();
			phoneRewar();
		} else if (itemID == '站台編號異動') {
			$('#rentDateChDiv').hide();
			$('#phoneRewardDiv').hide();
			$('#siteIdChDiv').show();
			$('#btn_1M3PrintRent').hide();
			$('#btn_1M3Apply').hide();
			dataChSiteInfo();
		}
	}

	//點選選取列
	function addClassSEL(tbid) {
		$('#' + tbid).on('click', 'tr', function() {
			$('#' + tbid).find('tr').each(function() {
				$(this).removeClass('selected');
			});
			$(this).addClass('selected');
		});
	}

	//刪除列
	function delRow(tableID) {
		var table = document.getElementById(tableID);
		var rows = document.getElementById(tableID).getElementsByTagName(
				'tbody')[0].getElementsByTagName('tr');
		for (var i = 0; i < rows.length; i++) {
			var trSel = table.rows[i + 1].getAttribute("class");
			if (trSel != null) {
				var isSel = trSel.indexOf("selected");
				if (isSel != -1) {
					table.rows[i + 1].remove();
				}
			}
		}
	}

	function siteTBFormat() {
		//站點資訊
		$('#siteTB').scrolltable({
			stripe : true,
			oddClass : 'odd',
			maxHeight : 80
		});
		//$('#siteTB table:last').addClass('table-striped');
	}


</script>
</head>
<body>
	<div class="row">
		<div class="col-xs-12 col-sm-12">
			<div class=" ui-draggable ui-droppable">
				<div class="box-header">
					<div class="box-name">增補協議</div>
					<div class="no-move"></div>
				</div>
				<div class="box-content">
					<form class="form-horizontal" id="" action="">
						<button class="btn btn-primary btn-label" type="button"
							id="btn_1M3Apply">申請</button>
						<button class="btn btn-primary btn-label" type="button"
							id="btn_1M3Upload">附件上傳</button>
						<hr>

						<div id="buttonDiv" style="margin-top: 10px"></div>

						<!-- 合約資訊 START -->
						<table style="width: 100%">
							<tr>
								<td nowrap="nowrap" width="6%"><label
									class="col-sm-12 control-label">合約編號 :</label></td>
								<td width="25%">
									<div
										style="width: 50%; height: auto; float: left; display: inline">
										<p style="padding-top: 12px; padding-left: 10px;" id="lsNo"></p>
									</div>
									<div id="viewDealDiv"
										style="width: 50%; height: auto; float: left; display: inline">

									</div>
								</td>
								<td nowrap="nowrap" width="10%"><label
									class="col-sm-12 control-label">合約名稱 :</label></td>
								<td width="20%">
									<p style="padding-top: 12px; padding-left: 10px;" id="laName"></p>
								</td>
								<td nowrap="nowrap" width="10%"><label
									class="col-sm-12 control-label">合約起迄 :</label></td>
								<td width="29%">
									<p style="padding-top: 12px; padding-left: 10px;" id="lsDate"></p>
								</td>
							</tr>

							<tr>
								<td nowrap="nowrap"><label class="col-sm-12 control-label">出租人
										:</label></td>
								<td COLSPAN="3">
									<p style="padding-top: 15px; padding-left: 10px;" id="lessorUser"></p>
								</td>
								<td nowrap="nowrap"><label class="col-sm-12 control-label">合約紙本保管單位/地點 :</label></td>
								<td>
									<p style="padding-top: 15px; padding-left: 10px;" id="keepPlace"></p>
								</td>
							</tr>
							<input id="lsVer" name="lsVer" type="hidden"> 
							<input id="appSeq" name="appSeq" type="hidden"> 
							<input id="appStatus" name="appStatus" type="hidden"> 
							<input id="appMode" name="appMode" type="hidden">
							<input id="addType" name="addType" type="hidden"> 
							<input id="addItem" name="addItem" type="hidden">
							<input id="rentType" name="rentType" type="hidden">
						</table>
						<!-- 合約資訊 END -->
						<hr>
						<div id="boDiv"></div>
						<div id="showFileUploadPage"></div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>