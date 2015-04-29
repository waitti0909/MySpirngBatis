<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<!DOCTYPE html>
<html lang="en">
<head>

<style>
#sortable {
	list-style-type: none;
	margin: 0;
	padding: 0;
	width: 100%;	
}

#sortable li {
	margin: 0 3px 3px 3px;
	padding: 0.4em;
	padding-left: 1.5em;
	font-size: 1.4em;
	width: 100%;
}

#sortable li span {
	position: absolute;
	margin-left: -1.3em;
}

#sortable li+li {
  margin-top: 1%
}
</style>
<title>增修條文</title>
</head>
<body>
	<div class="row">
		<div class="col-xs-12 col-sm-12">
			<div class=" ui-draggable ui-droppable">
				<div class="box-content">					
					<form class="form-horizontal" id="">

						<div>
							<div style="width: 60px; position: absolute;">
								<button class="btn btn-primary btn-label-left" type="button"
									id="savePsnPosBtn">
									<s:message code="button.select" text="選取" />
								</button>
							</div>
							<div style="margin-left: 80px;font-weight: bold;padding-top: 0.5%">
								<p>請拖拉，以改變條文順序!</p>
							</div>
						</div>
						
						<div id="clauseDiv" style="margin-top: 2%">
							<ul id="sortable">
								<c:forEach items="${clauseList}" var="cldata">
									<li id="li_${cldata.key}" class="ui-state-default" style="padding-left: 5px">
										<input type="checkbox" id="${cldata.key}" name="${cldata.key}" style="width: 20px; height: 20px" value="${cldata.key}" />
											<div style="max-height: 100px; overflow: auto;">
												<label for="${cldata.key}">${cldata.value}</label>
											</div>
									</li>
								</c:forEach>
							</ul>
						</div>

					</form>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	$(document).ready(function(){
		var btnType = '${btnType}';
		//conlog.log("btnType: " + btnType + ", claId: " + claId);
		$( "#sortable" ).sortable();
	    $( "#sortable" ).disableSelection();
	    toGenOrder();
	    
		$("#savePsnPosBtn").click(function(){					
			var clauseObjectList = {};
			var clauseArry = [];
			clauseObjectList.clauseArry = clauseArry;
			
			//由上往下搜尋ckeckbox是否勾選
		    $('#sortable').find("input:checkbox:checked").each(function () {
		        var clause={
						  "id": $(this).val(),
						  "content": $('#li_'+$(this).val()).text().trim()
					  };
				clauseObjectList.clauseArry.push(clause);		        
		    });
			
			
           var callbackFun = "toAddClause";
			if (typeof window[callbackFun] === "function") {
				window[callbackFun](JSON.stringify(clauseObjectList));
			}
			var frameId = $("#frameId").val();
			$("#" + frameId).dialog('close');
		});
	});
	
	//排序條文，及勾選
	function toGenOrder(){
		var claId = '${claId}';
		var clArry = claId.split(",");
		if ($.trim(claId) != "") {
			if(clArry.length != 0){
				for(var i=clArry.length-1;i>-1;i--){
					var dds = $("#li_" + clArry[i]).html();
					$("#li_" + clArry[i]).remove();
					$("#sortable").prepend('<li id="li_'+clArry[i]+'" class="ui-state-default" style="padding-left: 5px">'+dds+'</li>');				
					$("input[name='" + clArry[i] +"']").prop("checked", true); 
				}
			}	
		}
			
	}
	
</script>
</html>