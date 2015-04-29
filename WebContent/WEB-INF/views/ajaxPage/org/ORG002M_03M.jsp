<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<div id="psnPosButtons" hidden="true" >
<button class="btn btn-primary btn-label-left" type="button" id="psnPosAddBtn">
	<span><i class="fa fa-plus"></i></span>
	<s:message code="button.add" text="新增" />
</button>
<button class="btn btn-primary btn-label-left" type="button"
	id="psnPosDeleteBtn">
	<span><i class="fa fa-times txt-danger"></i></span>
	<s:message code="button.delete" text="刪除" />
</button>
</div>


<%-- 部門人員設定頁面 --%>
<div id="" class="selectItemDiv">
<div id="ORG002M_03MPage" class="" hidden="true">
	<div class="col-xs-13 col-sm-13">
		<form role="form" class="form-horizontal bootstrap-validator-form"
			novalidate="novalidate">
			<div class="form-group">
				<label class="col-sm-3 control-label">職務 :</label>
				<label class="labelStyle" id="posName2"></label>
			</div>
			<input type="hidden" id="callBackFun" name="callBackFun"
				value="showPsnPosData" /> <input type="hidden" id="deptPosId" />
				<input type="hidden" id="selecteddeptPosType" />
		</form>
	</div>
	<div class="col-xs-15 ">
		<div class="box">
			<div class="box-content no-padding">
				<table id="psnTable" style="table-layout:fixed;word-wrap:break-word;"
					class="table table-striped table-bordered table-hover table-heading no-border-bottom">
					<thead>
						<tr>
							<th width="25"></th>
							<th>員工編號</th>
							<th>員工名稱</th>
							<th>EMail</th>
							<th width="40" nowrap>主要<br>工作</th>
						</tr>
					</thead>
					<tbody id="psnTableBody">

					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
<div id="showpsnPosPag" hidden="true"></div>
</div>

<script type="text/javascript">
	$(document).ready(function(){
		$('#psnPosAddBtn').click(function() {
			openUserDialogFrame("showpsnPosPag", $("#callBackFun").val());
		});//addButton end
		
		$('#psnPosDeleteBtn').click(function() {
			
			var deptPosId = $("#deptPosId").val();
			var psnNo =listerenPsnPosCheckBox("psnPos");
			if(psnNo.length == 0){
				alert("請選擇一筆資料");
				return false;
			}
			alert(deptPosId+" , "+$("#selecteddeptPosType").val()+" , "+psnNo.toString());
			return false;
			if(confirm("確認刪除?")){
				$.ajax({
					type : 'POST',
					url : CONTEXT_URL + "/org/dept/psnPos/delete" ,
					data : { 
						"psnNo" : psnNo.toString(),
						"deptPosId" : deptPosId,
						"posType" : $("#selecteddeptPosType").val()
					},
					async : false,
					success : function(data) {
						alert(data['result']);
						createPersnoolTable(deptPosId);
					}
				});
			}
		});//deleteButton end
		
	});
	

	function createPersnoolTable(deptPosId,posType){
		$.ajax({
			url : "<s:url value='/org/dept/psnPos'/>",
			async: false,
			type : 'POST',
			data :{ "deptPosId" :  deptPosId },
			success : function(data){
				var records = data.rows;
				$("#psnTableBody").empty();
				var tableStr = "";
				
				for(var r in records) {
				
					var disable="";
					if('${showType}'=="view"){
					  disable="disabled";
					}
					
					var checked ="";
					var psnEmail = records[r].psnEmail;
					var psnName = records[r].psnName;
					if(records[r].primaryJobPos == records[r].dept_POS_ID){
						checked ='checked';
					}else{
						checked ='';
					}
					if(psnEmail == null){
						psnEmail = "";
					}
					if(psnName == null){
						psnName = "";
					}
					var primaryJobCheckboxId = "primaryJob"+r;
					tableStr +='<tr>'+'<td align="center"><input type="checkbox" name="psnPos" id="'+records[r].psn_NO+'" value="'
					+records[r].psn_NO+'"'+disable+' onclick="doValidatePrimaryJob(\u0027'+records[r].psn_NO+'\u0027);" ></td>'+'<td nowrap>'
					+records[r].psn_NO+'</td>'+'<td>'+psnName+'</td>'
					+'<td>'+psnEmail+'</td>'
					+'<td align="center"><input type="checkbox" name="primaryJobPos" id="'+primaryJobCheckboxId
					+'" onclick="changePrimaryJobPos(\u0027'+primaryJobCheckboxId+'\u0027);" value="'
					+records[r].psn_NO+'" '+checked+disable+'></td>'
					+'</tr>';
				}
				$('#psnTableBody').html(tableStr);
					 
			}
		});
	} 
	function open03M(deptPosId,posId,posType){
		$.ajax({
			type : 'POST',
			url : "<s:url value='/org/dept/psnPos'/>",
			data :{
				"deptPosId" : deptPosId
			},
			async : false,
			success : function(data) {
				$("#posName2").text(posId);
				$("#deptPosId").val(deptPosId);
				$("#selecteddeptPosType").val(posType);
				createPersnoolTable(deptPosId);
				$("#ORG002M_03MPage").show();
				$("#psnPosButtons").show();
			}
		});
	}
	
	function listerenPsnPosCheckBox(checkBoxName){
		var psnNo = [];
		$('input:checkbox:checked[name="'+checkBoxName+'"]').each(function(i) {psnNo.push(this.value);});
		return psnNo;
	}
	
	function changePrimaryJobPos(checkValue){
		var checked =$( "#"+checkValue ).prop('checked');
		if(confirm("確定設定?")){
			var psnNo = $( "#"+checkValue ).val();
			var deptPosId = null;
			if(checked){
				 deptPosId = $("#deptPosId").val();
			}else{
				alert("無法將主要工作取消");
				$( "#"+checkValue ).prop('checked',true);
				return false;
			}
			$.ajax({
				type : 'POST',
				url : CONTEXT_URL + "/org/dept/psnPos/updatePrimaryJobPos ",
				data : {
					"psnNo" : psnNo,
					"deptPosId" :deptPosId
				},
				async : false,
				success : function(data) {
					if(data){
						alert('<s:message code="msg.update.success"/>');
					}else{
						alert('<s:message code="msg.update.fail"/>');
					}
					deptPosId = $("#deptPosId").val();
					createPersnoolTable(deptPosId);
				}
			});
		}else{
			$( "#"+checkValue ).prop('checked' ,!checked);
		}
	}
	
	function showPsnPosData(psnData){
		//新增所屬員工
		var obj= JSON.parse(psnData);
		var deptPosId = $("#deptPosId").val();
		var psnArray =[];
		for(var i=0;i<obj.employees.length;i++){
			var psn ={
					"psn_NO" : obj.employees[i].no,
					"chn_NM": obj.employees[i].name,
					"e_MAIL" : obj.employees[i].email,
					"primary_DEPT_POS_ID" : obj.employees[i].deptPosId
			};
			psnArray.push(psn);
		}
		$.ajax({
			type : 'POST',
			url : CONTEXT_URL + "/org/dept/psnPos/addSave ?deptPosId="+deptPosId,
			data : JSON.stringify(psnArray),
			contentType : "application/json",
			async : false,
			success : function(data) {
				if(data){
					alert('<s:message code="msg.add.success"/>');
				}else{
					alert('<s:message code="msg.add.dataExists"/>');
				}
				createPersnoolTable(deptPosId);
			}
		});
	}
	
	function doValidatePrimaryJob(psnNo){
		var deptPosId = $("#deptPosId").val();
		$.ajax({
			type : 'POST',
			url : CONTEXT_URL + "/org/dept/psnPos/doValidatePrimaryJob" ,
			data : { 
				"psnNo" : psnNo,
				"deptPosId" : deptPosId,
			},
			async : false,
			success : function(data) {
				if(data.msg != ""){
					$("#"+psnNo).prop("checked",false);
					alert(data.msg);
				}
				
			}
		});
	}
</script>