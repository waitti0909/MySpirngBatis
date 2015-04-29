<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="decorator"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>探勘紀錄</title>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title>Insert title here</title>
<style type="text/css">
.input-group-btn {
  position: relative;
  font-size: inherit;
  white-space: nowrap;
}
</style>
<script type="text/javascript">

	$(document).ready(function() {
		//LoadSelect2Script('<s:url value="/resources/plugins/select2/select2.min.js" />',Select2);
		//設備型態 下拉選單
		osSiteHuntEqp();
		$('#siteHuntEqpTemp').val($("#siteHuntEqpType").val()).change();
		$("#siteHuntEqpTemp").prop("disabled", true);
		//設備型態 下拉 連動 設備機型 下拉選單
		//$("#siteHuntEqpTemp").change(function() {
			osSiteHuntEqpType($("#siteHuntEqpTemp").val());
			$("#siteHuntEqpModelTemp").val($("#siteHuntEqpModel").val()).change();
			if($("#siteHuntEqpTemp").val() == '3' || $("#siteHuntEqpTemp").val() == '4' || "${view}" == 'view') {
				$("#configuration").prop("disabled", true);
			} else {
				//$("#configuration").prop("disabled", false);
			}
		//});
		//Feederless 下拉選單
		osFeederlessTeam();
		$('#feederlessTeam').val($("#feederless").val()).change();
		$("#feederlessTeam").prop("disabled", true);
		
		//基站名稱
		$('#siteTempName').val($("#siteHuntSiteName").val());
		//configuration
		$('#configuration').val($("#btsConfig").val());
		//Master Site 
		$('#masterSiteTemp').val($("#masterSiteText").val());
		//Donor BTS 
		$('#donorBts').val($("#donorSiteText").val());	
				
		//涵蓋類別 
		osIncludeRange();
		
		//
	});//document.ready end
	
	function Select2() {
		$("#siteHuntEqpTemp").select2();
		$("#siteHuntEqpModelTemp").select2();
		$("#feederlessTeam").select2();
		$("#includeRange").select2();
		$("#omcuObjectLine").select2();
		$("#repeater").select2();		
	}
	
	var tableNum = 0;
	var unUse = [];
	function addSrTable() {		
		var tableId = '';
		//取得最後table的ID
		$('#testTableDIV table').each(function() {
			tableId = $(this).attr('id');
		});
		if(tableId!=''){
			tableNum = tableId.substr(3); //取ID(tb-X)數字
		}		
		tableNum++;	
		comTable(tableNum);
		
		var preValue = '';
		$("#cell-"+tableNum)
		.on('focus',function(){
			//alert('123');
			preValue = $(this).val(); //取得改變之前的值
		})
		.change(function(){
			var currentValue = $(this).val();
			//console.log("原本的值-------" +preValue);
			if($.inArray(currentValue ,unUse) > -1){
				//console.log("選擇後的值 : "+currentValue +" 已存在 [" + unUse+"]");
				$(this).val(preValue);
				alert("該Cell選項  "+ currentValue + " 已被選擇，請使用其他選項。");
				//console.log("停留在原選項 : " + preValue);
			} else{ 
				var newArr = [];
				if(preValue){
					//console.log("[" + unUse +"] 變動前..");
					for(var i=0 ; i<unUse.length ; i++){
						if(unUse[i]!= preValue){
							newArr.push(unUse[i]);
						}
					}
					unUse = newArr;
					//console.log("["+unUse +"] 已移除 "+preValue);
				} 
				
				if(currentValue){
					//console.log(currentValue +" 即將加入 [" + unUse +"]");
					unUse.push(currentValue);
					//console.log("["+unUse +"] 新增 "+currentValue);
				}
				preValue = currentValue;
			}
			//console.log("end........................");
		});
		
		return tableNum;
	}
	
	function delSrTable() {
		//var s =  $("#testTableDIV :checkbox:checked").length;
		var str = "";
		$("#testTableDIV :checkbox:checked").each(function() {
			str="tb-"+$(this).val();
			//刪除指定ID的TABLE
			var oTab = document.getElementById(str);
			oTab.parentNode.removeChild(oTab);
		});
		unUse = [];
	}
	function buttonIdTemp(id){
		//alert(id);
		$("#buttonId").val(id);
	}
	
/* 	function onKeyPressBlockNumbers(e){
		var key = window.event ? e.keyCode : e.which;
		var keychar = String.fromCharCode(key);
		reg = /[0-9]|\./;
		return reg.test(keychar);
	} */
	
	function comTable(tableNum){
		var row1='<div id="view-'+tableNum+'"><table id="tb-'+tableNum+'" name="tb" border=2 style="table-layout: fixed; word-break: break-all; word-wrap: break-word; width: 100%">';
		row1+='<tr><td class="titleTD"><font color="red">*</font> Cell</td><td class="titleTD">訊號源</td>';
		row1+='<td class="titleTD">P-Code</td>';
		row1+='<td COLSPAN=2 class="titleTD">天線型號</td>';
		row1+='<td class="titleTD">安裝高度</td>';
		row1+='<td class="titleTD">方向角</td>';
		row1+='<td class="titleTD">E-TILT</td>';
		row1+='<td class="titleTD">M-TILT</td>';
		row1+='<td class="titleTD">安裝方式</td></tr>';
		var row2='<tr><td ROWSPAN=5 class="titleTD"><input id="chk-'+tableNum+'" type="checkbox" name="siteLine1" value="'+tableNum+'" />&nbsp;<select id="cell-'+tableNum+'" name="siteLine2" class="form-control" required="required" style="width:60%;display:inline;"></select></td>';
		row2+='<td class="textTD"><select id="signalSRC-'+tableNum+'" name="siteLine3" class="form-control"></select></td>';
		row2+='<td class="textTD"><input id="pCode-'+tableNum+'" name="siteLine4" maxlength="20" type="text" style="width: 100%;"></td>';
		row2+='<td COLSPAN=2 class="textTD"><select id="antenna-'+tableNum+'" name="siteLine5" class="form-control"><option value=""></option></select></td>';
		row2+='<td class="textTD"><input id="installH-'+tableNum+'" name="siteLine6" type="text" style="width: 100%;" onkeyup="return ValidateInt(this,value)"></td>';
		row2+='<td class="textTD"><input id="directionAngle-'+tableNum+'" name="siteLine7" type="text" style="width: 100%;" onkeyup="return ValidateInt(this,value)"></td>';
		row2+='<td class="textTD"><input id="eTilt-'+tableNum+'" name="siteLine8" type="text" style="width: 100%;" onkeyup="return ValidateInt(this,value)"></td>';
		row2+='<td class="textTD"><input id="mTilt-'+tableNum+'" name="siteLine9" type="text" style="width: 100%;" onkeyup="return ValidateInt(this,value)"></td>';
		row2+='<td class="textTD"><select id="installStyle-'+tableNum+'" name="siteLine10" class="form-control"><option value=""></option></select></td></tr>';
		var row3='<tr><td class="titleTD">樓高</td>';
		row3+='<td class="titleTD">鐵塔</td>';
		row3+='<td class="titleTD">屋突</td>';
		row3+='<td COLSPAN=4 class="titleTD">地址</td>';
		row3+='<td class="titleTD">經度</td>';
		row3+='<td class="titleTD">緯度</td></tr>';
		var row4='<tr><td class="textTD"><input id="floor-'+tableNum+'" name="siteLine11" type="text" style="width: 100%;" onkeyup="return ValidateInt(this,value)" ></td>';
		row4+='<td class="textTD"><input id="tower-'+tableNum+'" name="siteLine12" type="text" style="width: 100%;" onkeyup="return ValidateInt(this,value)" ></td>';
		row4+='<td class="textTD"><input id="roof-'+tableNum+'" name="siteLine13" type="text" style="width: 100%;" onkeyup="return ValidateInt(this,value)"></td>';
		row4+='<td COLSPAN=4 class="textTD"><div class="input-group" style="width: 95%;"><input id="arrd-'+tableNum+'" name="siteLine14" type="text" class="form-control" placeholder="Click button to manage " readonly="readonly"  value="" ><span class="input-group-btn"><button id="button-'+tableNum+'" name="button-'+tableNum+'" class="btn btn-primary" type="button" onclick="buttonIdTemp('+tableNum+');openAntAddrProcess();"><i class="fa fa-home"></i></button></span></div></td>';
		row4+='<td class="textTD"><input id="lon-'+tableNum+'" name="siteLine15" type="text" style="width: 100%;" onkeyup="return ValidateInt(this,value)" ></td>';
		row4+='<td class="textTD"><input id="lat-'+tableNum+'" name="siteLine16" type="text" style="width: 100%;" onkeyup="return ValidateInt(this,value)" ></td></tr>';
		var row5='<tr><td COLSPAN=2 class="titleTD">Feeder cable type</td>';
		row5+='<td COLSPAN=2 class="titleTD">Feeder cable 長度</td>';
		row5+='<td COLSPAN=2 class="titleTD">Jumper cable 長度</td>';
		row5+='<td COLSPAN=3 class="titleTD">覆蓋區域</td></tr>';
		var row6='<tr><td COLSPAN=2 class="textTD"><select id="feederType-'+tableNum+'" name="siteLine17" class="form-control"><option value=""></option></select></td>';
		row6+='<td COLSPAN=2 class="textTD"><input id="feederL-'+tableNum+'" name="siteLine18" type="text" maxlength="10" onkeyup="return ValidateInt(this,value)" style="width: 100%;"></td>';
		row6+='<td COLSPAN=2 class="textTD"><input id="jumper-'+tableNum+'" name="siteLine19" type="text" maxlength="10" onkeyup="return ValidateInt(this,value)" style="width: 100%;"></td>';
		row6+='<td COLSPAN=3 class="textTD"><input id="coverageArea-'+tableNum+'" name="siteLine20" type="text" maxlength="200" style="width: 100%;"></td>';
		var row7='<input type="hidden" name="siteLine21" id="ant_zip-'+tableNum+'" /><input type="hidden" name="siteLine22" id="ant_city-'+tableNum+'" /><input type="hidden" name="siteLine23" id="ant_area-'+tableNum+'"  /><input type="hidden" name="siteLine24" id="ant_village-'+tableNum+'" /><input type="hidden" name="siteLine25" id="ant_road-'+tableNum+'" /><input type="hidden" name="siteLine26" id="ant_adjacent-'+tableNum+'"  /><input type="hidden" name="siteLine27" id="ant_lane-'+tableNum+'"  /><input type="hidden" name="siteLine28" id="ant_alley-'+tableNum+'"  /><input type="hidden" name="siteLine29" id="ant_subAlley-'+tableNum+'"  /><input type="hidden" name="siteLine30" id="ant_doorNo-'+tableNum+'"  /><input type="hidden" name="siteLine31" id="ant_doorNoEx-'+tableNum+'" /><input type="hidden" name="siteLine32" id="ant_floor-'+tableNum+'" /><input type="hidden" name="siteLine33" id="ant_floorEx-'+tableNum+'" /><input type="hidden" name="siteLine34" id="ant_room-'+tableNum+'" /><input type="hidden" name="siteLine35" id="ant_remark-'+tableNum+'" /><input type="hidden" name="ant_disabledFields" id="ant_disabledFields-'+tableNum+'" value="zip,city,area"/><input type="hidden" name="ant_callBackFun" id="ant_callBackFun-'+tableNum+'" value="receviceAntCfgAddrData" /><input type="hidden" name="antAddr" id="antAddr-'+tableNum+'" value="antAddr" /></tr></table></div>';
		$("#testTableDIV").append(row1 + row2 + row3 + row4 + row5 + row6 + row7);
		
		osAntSignal(tableNum);
		osAntInstallWay(tableNum);
		osAntenna(tableNum);
		osFeederCableType(tableNum);
		osAntCell(tableNum);
		
		if($("#qq_zip").val() != null ) {
			$("#ant_zip-"+tableNum).val($("#qq_zip").val());		
		} else {
			$("#ant_zip-"+tableNum).val($("#zip").val());	
		}
		if($("#city").val() != null) {			
		    $("#ant_city-"+tableNum).val($("#city").val());
			$("#ant_area-"+tableNum).val($("#area").val());
		} else {
		    $("#ant_city-"+tableNum).val($("#siteHuntCity").val());
			$("#ant_area-"+tableNum).val($("#siteHuntArea").val());			
		}
		if($("#address").val() != null) {			
			$("#arrd-"+tableNum).val($("#address").val());
		} else {			
			$("#arrd-"+tableNum).val($("#addr").val());
		}
		if($("#qq_village").val() != null) {			
	    	$("#ant_village-"+tableNum).val($("#qq_village").val());
		} else {
			$("#ant_village-"+tableNum).val($("#village").val());
		}
	    if ($("#qq_road").val() != null) {	    	
	    	$("#ant_road-"+tableNum).val($("#qq_road").val());
	    } else {
	    	$("#ant_road-"+tableNum).val($("#road").val());
	    }
	    if($("#qq_adjacent").val() != null) {
			$("#ant_adjacent-"+tableNum).val($("#qq_adjacent").val());
	    } else {
	    	$("#ant_adjacent-"+tableNum).val($("#adjacent").val());
	    }
	    if($("#qq_lane").val() != null) {	    	
	    	$("#ant_lane-"+tableNum).val($("#qq_lane").val());
	    } else {
	    	$("#ant_lane-"+tableNum).val($("#lane").val());
	    }
	    if($("#qq_alley").val() != null) {
			$("#ant_alley-"+tableNum).val($("#qq_alley").val());
	    } else {
			$("#ant_alley-"+tableNum).val($("#alley").val());
	    }
		if($("#qq_subAlley").val() != null) {
			$("#ant_subAlley-"+tableNum).val($("#qq_subAlley").val());	
		} else {
			$("#ant_subAlley-"+tableNum).val($("#sub_ALLEY").val());
		}
		if($("#qq_doorNo").val() != null) {
			$("#ant_doorNo-"+tableNum).val($("#qq_doorNo").val());			
		} else {
			$("#ant_doorNo-"+tableNum).val($("#door_NO").val());		
		}
		if($("#qq_doorNoEx").val() != null) {
			$("#ant_doorNoEx-"+tableNum).val($("#qq_doorNoEx").val());	
		} else {
			$("#ant_doorNoEx-"+tableNum).val($("#door_NO_EX").val());	
		}
		if($("#qq_floor").val() != null) {
			$("#ant_floor-"+tableNum).val($("#qq_floor").val());
		} else {
			$("#ant_floor-"+tableNum).val($("#floor").val());
		}
		if($("#qq_floorEx").val() != null) {
			$("#ant_floorEx-"+tableNum).val($("#qq_floorEx").val());
		} else {
			$("#ant_floorEx-"+tableNum).val($("#floor_EX").val());
		}
		if($("#qq_room").val() != null) {
			$("#ant_room-"+tableNum).val($("#qq_room").val());			
		} else {
			$("#ant_room-"+tableNum).val($("#room").val());			
		}
		if($("#qq_remark").val() != null) {
			$("#ant_remark-"+tableNum).val($("#qq_remark").val());
		} else {
			$("#ant_remark-"+tableNum).val($("#add_OTHER").val());
		}
	}	

	//探勘紀錄 --設備型態
	function osSiteHuntEqp(){
		$.ajax({
			url : CONTEXT_URL + "/st/searchRecord/siteHuntEqp",			
			type : "POST",
			dataType : 'json',
			data:{
				"workId" :  $("#workId").val()			
			},
			async : false,
			success : function(data) {
				if(data.success){
					if(data.success){
						if(data.rows.length > 0){
							$("#siteHuntEqpTemp  option").remove();
							$("#siteHuntEqpTemp").append("<option value=''>--請選擇--</option>");
							
							for(var i=0; i<data.rows.length; i++){		
								$("#siteHuntEqpTemp").append("<option value="+data.rows[i].value+">"+data.rows[i].label+"</option>");
							}
							
						}
					}
				}
			}
		});
	}	
	
	//探勘紀錄 --設備機型
	function osSiteHuntEqpType(e){
		$.ajax({
			url : CONTEXT_URL + "/st/searchRecord/siteHuntEqpType",			
			type : "POST",
			dataType : 'json',
			data:{
				"eqpType" :  e	
			},
			async : false,
			success : function(data) {
				if(data.success){
					if(data.success){
						if(data.rows.length > 0){
							$("#siteHuntEqpModelTemp  option").remove();
							$("#siteHuntEqpModelTemp").append("<option value=''>--請選擇--</option>");
							for(var i=0; i<data.rows.length; i++){		
								$("#siteHuntEqpModelTemp").append("<option value="+data.rows[i].eqp_MODEL_ID+">"+data.rows[i].eqp_MODEL+"</option>");
							}				
						}
					}
				}
			}
		});
	}
	
	//基站狀態查詢
	function osSiteStatusId(){
		$.ajax({
			url : CONTEXT_URL + "/st/searchRecord/siteStatusId",			
			type : "POST",
			dataType : 'json',
			data:{
				"searchSiteId" :  $("#searchSiteId").val()			
			},
			async : false,
			success : function(data) {
				if(data.success){
					if(data.success){
						//alert(JSON.stringify(data));
						$("#siteStatus").text(data.rows[0]);
						$("#siteStatusId").val(data.rows[0]);
					}
				}
			}
		});
	}	
	
	//探勘紀錄 --feederlessTeam
	function osFeederlessTeam(){
		$.ajax({
			url : CONTEXT_URL + "/st/searchRecord/feederless",			
			type : "POST",
			dataType : 'json',
			data:{
				"workId" :  $("#workId").val()			
			},
			async : false,
			success : function(data) {
				if(data.success){
					if(data.success){
						if(data.rows.length > 0){
							$("#feederlessTeam  option").remove();
							$("#feederlessTeam").append("<option value=''>--請選擇--</option>");
							for(var i=0; i<data.rows.length; i++){		
								$("#feederlessTeam").append("<option value="+data.rows[i].value+">"+data.rows[i].label+"</option>");
							}				
						}
					}
				}
			}
		});
	}
	
	//涵蓋類別  --includeRange
	function osIncludeRange(){
		$.ajax({
			url : CONTEXT_URL + "/st/searchRecord/includeRange",			
			type : "POST",
			dataType : 'json',
			data:{
				"workId" :  $("#workId").val()			
			},
			async : false,
			success : function(data) {
				if(data.success){
					if(data.success){
						if(data.rows.length > 0){
							$("#includeRange  option").remove();
							$("#includeRange").append("<option value=''>--請選擇--</option>");
							for(var i=0; i<data.rows.length; i++){		
								$("#includeRange").append("<option value="+data.rows[i].value+">"+data.rows[i].label+"</option>");
							}				
						}
					}
				}
			}
		});
	}	
	
	//探勘紀錄 --訊號源
	var antSignalSel = "";
	function osAntSignal(id){
		if (antSignalSel.length > 0) {
			$("#signalSRC-"+id+"  option").remove();
			$("#signalSRC-"+id).append("<option value=''>--請選擇--</option>");
			for(var i=0; i<antSignalSel.length; i++){		
				$("#signalSRC-"+id).append("<option value="+antSignalSel[i].value+">"+antSignalSel[i].label+"</option>");
			}
		} else {
			$.ajax({
				url : CONTEXT_URL + "/st/searchRecord/antSignal",	
				type : "POST",
				dataType : 'json',
				data:{
					"workId" :  $("#workId").val()			
				},
				async : false,
				success : function(data) {
					if(data.success){
						if(data.rows.length > 0){
							$("#signalSRC-"+id+"  option").remove();
							$("#signalSRC-"+id).append("<option value=''>--請選擇--</option>");
							for(var i=0; i<data.rows.length; i++){		
								$("#signalSRC-"+id).append("<option value="+data.rows[i].value+">"+data.rows[i].label+"</option>");
							}
							antSignalSel = data.rows;
						}
					}
				}
			});
		}
	}
	
	//探勘紀錄 --cell
	var antCellSel = "";
	function osAntCell(id){
		if (antCellSel.length > 0) {
			$("#cell-"+id+"  option").remove();
			$("#cell-"+id).append("<option value=''>----</option>");
			for(var i=0; i<antCellSel.length; i++){		
				$("#cell-"+id).append("<option value="+antCellSel[i].value+">"+antCellSel[i].label+"</option>");
			}	
		} else {
			$.ajax({
				url : CONTEXT_URL + "/st/searchRecord/antCell",			
				type : "POST",
				dataType : 'json',
				data:{
					"workId" :  $("#workId").val()			
				},
				async : false,
				success : function(data) {

					if(data.success){
						if(data.rows.length > 0){
							$("#cell-"+id+"  option").remove();
							$("#cell-"+id).append("<option value=''>----</option>");
							for(var i=0; i<data.rows.length; i++){		
								$("#cell-"+id).append("<option value="+data.rows[i].value+">"+data.rows[i].label+"</option>");
							}	
							antCellSel = data.rows;
						}
					}
				}
			});
		}
	}
	
	//探勘紀錄 --安裝方式
	var antInstallSel = "";
	function osAntInstallWay(id){
		if (antInstallSel.length > 0) {
			$("#installStyle-"+id+"  option").remove();
			$("#installStyle-"+id).append("<option value=''>--請選擇--</option>");
			for(var i=0; i<antInstallSel.length; i++){		
				$("#installStyle-"+id).append("<option value="+antInstallSel[i].value+">"+antInstallSel[i].label+"</option>");
			}	
		} else {
			$.ajax({
				url : CONTEXT_URL + "/st/searchRecord/antInstallWay",			
				type : "POST",
				dataType : 'json',
				data:{
					"workId" :  $("#workId").val()			
				},
				async : false,
				success : function(data) {

					if(data.success){
						if(data.rows.length > 0){
							$("#installStyle-"+id+"  option").remove();
							$("#installStyle-"+id).append("<option value=''>--請選擇--</option>");
							for(var i=0; i<data.rows.length; i++){		
								$("#installStyle-"+id).append("<option value="+data.rows[i].value+">"+data.rows[i].label+"</option>");
							}	
							antInstallSel = data.rows;
						}
					}
				}
			});
		}
	}
	
	//探勘紀錄 --天線型號
	var antennaSel = "";
	function osAntenna(id){
		if (antennaSel.length > 0) {
			$("#antenna-"+id+"  option").remove();
			$("#antenna-"+id).append("<option value=''>--請選擇--</option>");
			for(var i=0; i<antennaSel.length; i++){		
				$("#antenna-"+id).append("<option value="+antennaSel[i].antenna_ID+">"+antennaSel[i].antenna_MODEL+"</option>");
			}
		} else {
			$.ajax({
				url : CONTEXT_URL + "/st/searchRecord/antenna",			
				type : "POST",
				dataType : 'json',
				data:{
					"workId" :  $("#workId").val()			
				},
				async : false,
				success : function(data) {

					if(data.success){
						if(data.rows.length > 0){
							//alert(JSON.stringify(data));
							$("#antenna-"+id+"  option").remove();
							$("#antenna-"+id).append("<option value=''>--請選擇--</option>");
							for(var i=0; i<data.rows.length; i++){		
								$("#antenna-"+id).append("<option value="+data.rows[i].antenna_ID+">"+data.rows[i].antenna_MODEL+"</option>");
							}
							antennaSel = data.rows;
						}
					}
				}
			});
		}
	}	
	
	//探勘紀錄 --FeederCableType
	var feederCableSel = '';
	function osFeederCableType(id){
		if (feederCableSel.length > 0) {
			$("#feederType-"+id+"  option").remove();
			$("#feederType-"+id).append("<option value=''>--請選擇--</option>");
			for(var i=0; i<feederCableSel.length; i++){		
				$("#feederType-"+id).append("<option value="+feederCableSel[i].value+">"+feederCableSel[i].label+"</option>");
			}
		} else {
			$.ajax({
				url : CONTEXT_URL + "/st/searchRecord/feederCableType",			
				type : "POST",
				dataType : 'json',
				data:{
					"workId" :  $("#workId").val()			
				},
				async : false,
				success : function(data) {

					if(data.success){
						if(data.rows.length > 0){
							$("#feederType-"+id+"  option").remove();
							$("#feederType-"+id).append("<option value=''>--請選擇--</option>");
							for(var i=0; i<data.rows.length; i++){		
								$("#feederType-"+id).append("<option value="+data.rows[i].value+">"+data.rows[i].label+"</option>");
							}	
							feederCableSel = data.rows;
						}
					}
				}
			});
		}
	}
	
	//基站查詢
	function siteLines() {
		var eqpType = $("#siteHuntEqpTemp").val();
		//alert(eqpType);
		if(eqpType==3 || eqpType==4){			
			openSiteDialogFrame("selectSiteLinesPage", $("#siteLinesCallBackFun").val(), 
				$("#filter_DomainId_SiteLines").val(), "multiSiteLines");
		} else{
			alert("此設備型態無法選擇Donor BTS");
		}
	}
	
	//Master Site 下拉判斷
	$("#masterSiteLinesBtn").click(function(){
		if($("#feederlessTeam").val() == "R"){
			openSiteDialogFrame("selectSiteLinesPage", $("#masterSiteLinesTemp").val(), 
				$("#filter_DomainId_SiteLines").val(), "multiSiteLines");
		}else{
			alert("Feederless為Remote才可以選擇Master Site");
		}
	});
	
	function receviceSiteLinesData(siteObjects) {	
		var obj = JSON.parse(siteObjects);
		checkSiteLinesIsOnAir("donorSite","Donor BTS必須選擇ON AIR的基站",obj);				
	}	
	
	function masterSiteLinesTempData(siteObjects){
		var obj = JSON.parse(siteObjects);
		checkSiteLinesIsOnAir("masterSite","Master Site必須選擇ON AIR的基站",obj);
	}
	
	function checkSiteLinesIsOnAir(tagName ,errorMessage ,obj){
		$.ajax({
			url : CONTEXT_URL + "/st/st002/search/site",
			type : 'POST',
			data : {
				siteId :obj.siteObjs[0].site_ID,
			},
			async : false,
			success : function(record){
				var noAir = record.nois_ON_AIR;
				if(noAir == "Y"){
					$("#"+tagName+"Text").val(obj.siteObjs[0].bts_SITE_ID);
					$("#"+tagName).val(obj.siteObjs[0].site_ID);
					if(obj.siteObjs.length > 0 ){		
						$("#btsSiteIdLines").val(obj.siteObjs[0].bts_SITE_ID);
					}						
				}else{
					alert(errorMessage);
				}
			}
		});
	}
	
	
	//顯示地址處理頁
	function openAntAddrProcess() {
		var tableNum = $("#buttonId").val();

/* 		$("#ant_zip-"+tableNum).val($("#qq_zip").val());
	    $("#ant_city-"+tableNum).val($("#qq_city").val());
		$("#ant_area-"+tableNum).val($("#qq_area").val());
	    $("#ant_village-"+tableNum).val($("#qq_village").val());
	    $("#ant_road-"+tableNum).val($("#qq_road").val());
		$("#ant_adjacent-"+tableNum).val($("#qq_adjacent").val());
	    $("#ant_lane-"+tableNum).val($("#qq_lane").val());
		$("#ant_alley-"+tableNum).val($("#qq_alley").val());
		$("#ant_subAlley-"+tableNum).val($("#qq_subAlley").val());
		$("#ant_doorNo-"+tableNum).val($("#qq_doorNo").val());
		$("#ant_doorNoEx-"+tableNum).val($("#qq_doorNoEx").val());
		$("#ant_floor-"+tableNum).val($("#qq_floor").val());
		$("#ant_floorEx-"+tableNum).val($("#qq_floorEx").val());
		$("#ant_room-"+tableNum).val($("#qq_room").val());
		$("#ant_remark-"+tableNum).val($("#qq_remark").val()); */
		
		
		var addr = {
			"zip" : $("#ant_zip-"+tableNum).val(),
			"city" : $("#ant_city-"+tableNum).val(),
			"area" : $("#ant_area-"+tableNum).val(),
			"village" : $("#ant_village-"+tableNum).val(),
			"road" : $("#ant_road-"+tableNum).val(),
			"adjacent" : $("#ant_adjacent-"+tableNum).val(),
			"lane" : $("#ant_lane-"+tableNum).val(),
			"alley" : $("#ant_alley-"+tableNum).val(),
			"subAlley" : $("#ant_subAlley-"+tableNum).val(),
			"doorNo" : $("#ant_doorNo-"+tableNum).val(),
			"doorNoEx" : $("#ant_doorNoEx-"+tableNum).val(),
			"floor" : $("#ant_floor-"+tableNum).val(),
			"floorEx" : $("#ant_floorEx-"+tableNum).val(),
			"room" : $("#ant_room-"+tableNum).val(),
			"remark" : $("#ant_remark-"+tableNum).val(),
			"disabledFields" : $("#ant_disabledFields-"+tableNum).val(),
			"callBackFun" : $("#ant_callBackFun-"+tableNum).val()
		};
		openAddressDialogFrame("showAntAddrFrame", addr); // This is implement in common.js
	}	
	
	function receviceAntCfgAddrData(addressObject) {
		// var obj = JSON.parse(addressObject);
		// do what you want to do
		var tableNum = $("#buttonId").val();
		$.ajax({
			url : CONTEXT_URL + "/common/address/combineAddress",
			dataType : "json",
			data : addressObject,
			contentType : 'application/json',
			type : "POST",
			async : false,
			success : function(data) {
				//alert(JSON.stringify(data));
				//alert("#arrd-"+tableNum);
				$("#arrd-"+tableNum).val(data.row.fullAddress);
 				$("#ant_zip-"+tableNum).val(data.row.zip);
				$("#ant_city-"+tableNum).val(data.row.city);
				$("#ant_area-"+tableNum).val(data.row.area);
				$("#ant_village-"+tableNum).val(data.row.village);
				$("#ant_adjacent-"+tableNum).val(data.row.adjacent);
				$("#ant_road-"+tableNum).val(data.row.road).change();
				$("#ant_lane-"+tableNum).val(data.row.lane);
				$("#ant_alley-"+tableNum).val(data.row.alley);
				$("#ant_subAlley-"+tableNum).val(data.row.subAlley);
				$("#ant_doorNo-"+tableNum).val(data.row.doorNo);
				$("#ant_doorNoEx-"+tableNum).val(data.row.doorNoEx);
				$("#ant_floor-"+tableNum).val(data.row.floor);
				$("#ant_floorEx-"+tableNum).val(data.row.floorEx);
				$("#ant_room-"+tableNum).val(data.row.room);
				$("#ant_remark-"+tableNum).val(data.row.remark);
			}
		});
	}
	
	//限制輸入數字
	function ValidateInt(e, pnumber){
    if (!/^\d+[.]?\d*$/.test(pnumber)){
        $(e).val(/^\d+[.]?\d*/.exec($(e).val()));
		}
    	return false;
	}

	function changeValue(checkboxName){
		if($("#"+checkboxName).prop("checked")){
			$("#"+checkboxName).val("Y");
		}else{
			$("#"+checkboxName).val("N");
		}
	}
</script>

<style type="text/css">
	.titleTD {
		text-align: center;
	}
	
	.textTD {
		padding: 0.2em;
	}
</style>
</head>
<body>
	
	<table style="width: 100%">
	</table>
	
	<div id="effect">
		<table style="width: 100%">
			<tr>
				<td width="10%" nowrap="nowrap"><label
					class="col-sm-12 control-label">基站編號 :</label></td>
				<td width="23%">
					<p style="padding-top: 15px; padding-left: 5px;" id="btsSite"></p>
					<input id="btsSiteId" name="btsSiteId" type="hidden"></input>
				</td>
				<td width="10%" nowrap="nowrap">
					<label class="col-sm-12 control-label"><span class="s">*</span>基站名稱:</label></td>
				<td width="23%">
						<input id="siteTempName" name="siteTempName" type="text"
							class="form-control" required="required" maxlength="100"></input>
				</td>	
				<td width="10%"><label class="col-sm-12 control-label">基站狀態:</label></td>
				<td width="24%">
					<p style="padding-top: 15px; padding-left: 5px;" id="siteStatus"></p>
					<input id="siteStatusId" name="siteStatusId" type="hidden"></input>
				</td>
			</tr>
			<tr>
				<td>
					<div class="clearfix">&nbsp;</div>
				</td>
			</tr>			
			<tr>
				<td width="10%" nowrap="nowrap"><label
					class="col-sm-12 control-label">基站頻段 :</label></td>
				<td width="23%">
					<p style="padding-top: 15px; padding-left: 5px;" id="feq"></p>
					<input id="idFeq" name="feqId" type="hidden"></input>
				</td>
				<td width="10%" nowrap="nowrap"><label
					class="col-sm-12 control-label"><span class="s">*</span>設備型態:</label></td>
				<td width="20%">
						<select  id="siteHuntEqpTemp" name="siteHuntEqpTemp" class="form-control" required="required">
							<option value="">---請選擇---</option>								
						</select>
				</td>
				<td width="10%"><label class="col-sm-12 control-label">設備機型:</label></td>
				<td width="20%">
						<select id="siteHuntEqpModelTemp" class="form-control" name="siteHuntEqpModelTemp">
							<option value="">---請選擇---</option>
						</select>
				</td>
			</tr>
			<tr>
				<td>
					<div class="clearfix">&nbsp;</div>
				</td>
			</tr>			
			<tr>
				<td width="10%" nowrap="nowrap"><label
					class="col-sm-12 control-label">Feederless:</label></td>
				<td width="23%" style="padding-top: 15px;">
						<select id="feederlessTeam" name="feederlessTeam" class="form-control">
							<option value="">---請選擇---</option>
						</select>		
				</td>
				<td width="10%" nowrap="nowrap"><label
					class="col-sm-12 control-label">Master Site :</label></td>
				<td width="23%">
					<div style="margin-top: 15px">
						<div style="width: 60%; height: auto; float: left; display: inline">
							<input class="form-control" id="masterSiteTemp" name="masterSiteTemp" value="" readonly="readonly"/>
						</div>
						<div
							style="width: 40%; height: auto; float: left; display: inline">
							&nbsp;
							<input id="masterSiteLinesBtn" name="masterSiteLinesBtn" type="button" value="選擇"
									style="margin-right: 10px" class="btn btn-primary btn-label"/>
						</div>
					</div>
				</td>
				<td width="15%"><label class="col-sm-12 control-label">Configuration:</label></td>
				<td width="24%">
				<input id="configuration" name="configuration" type="text" value="" maxlength="15" class="form-control"></input></td>
			</tr>
			<tr>
				<td>
					<div class="clearfix">&nbsp;</div>
				</td>
			</tr>			
			<tr>
<!-- 				<td width="10%" nowrap="nowrap"></td> -->
<!-- 				<td width="23%"></td> -->
<!-- 				<td width="10%" nowrap="nowrap"></td> -->
<!-- 				<td width="23%"></td> -->
				<td width="10%" nowrap="nowrap"><label
					class="col-sm-12 control-label">Cluster :</label></td>
				<td width="23%"><input id="cluster" name="cluster" type="text"  class="form-control" maxlength="50"></input>
				</td>
				<td width="10%" nowrap="nowrap"><label
					class="col-sm-12 control-label">RNC-BTS :</label></td>
				<td width="23%"><input id="rncBts" name="rncBts" type="text"  maxlength="50" class="form-control"></input>
				</td>
				<td width="10%" nowrap="nowrap"><label
					class="col-sm-12 control-label">Donor BTS :</label></td>
				<td width="23%">
					<div style="margin-top: 15px">
						<div style="width: 60%; height: auto; float: left; display: inline">
							<input class="form-control" id="donorBts" name="donorBts" value="" readonly="readonly"/>
						</div>
						<div
							style="width: 40%; height: auto; float: left; display: inline">
							&nbsp;
							<button class="btn btn-primary btn-label" style="margin-right: 10px" type="button"
									id="selectSiteLines"  onclick="siteLines();">選擇</button>
							<div id="selectSiteLinesPage"></div>		
							<form id=siteLinesForm>
								<input id="donorSiteLinesCallBackFun" value="donorSiteLinesCallBackFun" type="hidden"></input>
								<input id="masterSiteLinesCallBackFun" value="masterSiteLinesCallBackFun" type="hidden"></input>							
								<input type="hidden" name="siteLinesCallBackFun" id="siteLinesCallBackFun" value="receviceSiteLinesData" />
								<input type="hidden" name="masterSiteLinesTemp" id="masterSiteLinesTemp" value="masterSiteLinesTempData" />
								<!-- 單選不用傳 -->
								<input type="hidden" name="selectModeSiteLines" id="selectModeSiteLines" value="multiSiteLines" />
							</form>							
						</div>
					</div>
				</td>
			</tr>										
			<tr>
				<td width="10%" nowrap="nowrap"><label
					class="col-sm-12 control-label">涵蓋類別 :</label></td>
				<td width="23%" style="padding-top: 15px;">
						<select id="includeRange" name="includeRange" class="form-control">
							<option value="">---請選擇---</option>
						</select>		
				</td>
				<td width="10%" nowrap="nowrap"><label
					class="col-sm-12 control-label">室內天線涵蓋樓層 :</label></td>
				<td width="23%"><input id="coverageInFloor" name="coverageInFloor" type="text"  maxlength="6" class="form-control"></input>
				</td>
				<td width="15%"><label class="col-sm-12 control-label">功率:</label></td>
				<td width="24%"><input id="power" name="power" type="text"
					value=""  class="form-control" maxlength="10" style="width:90%;display:inline;"></input>mW</td>
			</tr>
			<tr>
				<td>
					<div class="clearfix">&nbsp;</div>
				</td>
			</tr>
<!-- 			<tr> -->
<!-- 				<td width="10%" nowrap="nowrap"><label -->
<!-- 					class="col-sm-12 control-label">Cluster :</label></td> -->
<!-- 				<td width="23%"><input id="cluster" name="cluster" type="text"  class="form-control" maxlength="50"></input> -->
<!-- 				</td> -->
<!-- 				<td width="10%" nowrap="nowrap"><label -->
<!-- 					class="col-sm-12 control-label">RNC-BTS :</label></td> -->
<!-- 				<td width="23%"><input id="rncBts" name="rncBts" type="text"  maxlength="50" class="form-control"></input> -->
<!-- 				</td> -->
<!-- 			</tr> -->
			<tr id="hideTr">
				<td width="10%" nowrap="nowrap"><label
					class="col-sm-12 control-label">Cell Status :</label></td>
				<td width="23%">
					<p style="padding-top: 15px; padding-left: 5px;" id="feq"></p>
					<input id="cellStatus" name="cellStatus" type="hidden"></input>
				</td>
				<td width="10%" nowrap="nowrap"><label
					class="col-sm-12 control-label"><span class="s">*</span>OMCu Object:</label></td>
				<td width="20%">
					<div style="padding-top: 15px;" id="selectOmcuObject" >	
						<select  id="omcuObjectLine" name="omcuObject" class="form-control" required="required">
							<option value="">---請選擇---</option>
							<option value="1">未建立</option>
							<option value="2">已建立</option>								
						</select>
					</div>	
				</td>
				<td width="10%"><label class="col-sm-12 control-label">NOIS repeater狀態:</label></td>
				<td width="20%">
						<select id="repeater" name="repeater" class="form-control">
							<option value="">---請選擇---</option>
							<option value="1">納入網管系統</option>
							<option value="2">未納入網管系統</option>
						</select>
				</td>
			</tr>	
			<tr>
				<td>
					<div class="clearfix">&nbsp;</div>
				</td>
			</tr>
			<tr id="hideTrOne">
				<td><label class="col-sm-12 control-label"><span class="s">*</span>經度 :</label></td>
				<td>
					<div class="col-sm-10">
						<input id="siteLon" name="LON" type="text" class="form-control">
					</div>
				</td>
				<td><label class="col-sm-12 control-label"><span class="s">*</span>緯度 :</label></td>
				<td>
					<div class="col-sm-10">
						<input id="siteLat" name="LAT" type="text" class="form-control">
					</div>
				</td>
			</tr>	
			<tr>
				<td width="10%" nowrap="nowrap" style="padding-top: 15px;">
					<label class="col-sm-12 control-label">其他 :</label></td>
				<td colspan="5" nowrap="nowrap">
					<div id="isTeam" style="padding-top: 15px;">
						<div style="height: auto; float: left; display: inline" class="checkbox-inline"><label>
							<input id="coverageIndoor" name="isCoverageIndoor" type="checkbox" onclick="changeValue('coverageIndoor');"></input> 有室內涵蓋<i class="fa fa-square-o"></i></label>
						</div>
						<div style="height: auto; float: left; display: inline" class="checkbox-inline"><label>
							<input id="hidden" name="isHidden" type="checkbox"  onclick="changeValue('hidden');"></input> 有美化施工<i class="fa fa-square-o"></i></label>
						</div>
						<div style="height: auto; float: left; display: inline" class="checkbox-inline"><label>
							<input id="noisOnAir" name="isNoisOnAir" type="checkbox" disabled="disabled" ></input> 網管On Air<i class="fa fa-square-o"></i></label>
						</div>
						<div style="height: auto; float: left; display: inline" class="checkbox-inline"><label>
							<input id="l2Switch" name="isL2Switch" type="checkbox"  onclick="changeValue('l2Switch');"></input> 有接L2_SWITCH<i class="fa fa-square-o"></i></label>
						</div>
					</div>
				</td>
			</tr>	
			<tr>
				<td>
					<div class="clearfix">&nbsp;</div>
				</td>
			</tr>			
			<tr id="hideTrTwo">
				<td width="10%" nowrap="nowrap"><label
					class="col-sm-12 control-label">On Air日期 :</label></td>
				<td width="23%">
					<p style="padding-top: 15px; padding-left: 5px;" id=""></p> <input
					id="onAirDate" name="onAirDate" type="hidden"></input>
				</td>
				<td width="10%" nowrap="nowrap"><label
					class="col-sm-12 control-label">停用日期 :</label></td>
				<td width="23%">
					<p style="padding-top: 15px; padding-left: 5px;" id=""></p> <input
					id="dumpDate" name="dumpDate" type="hidden"></input>
				</td>
			</tr>					
			<tr>
				<td width="10%" nowrap="nowrap"><label
					class="col-sm-12 control-label">天線組態 :</label></td>
				<td colspan="5" nowrap="nowrap">
					<div style="padding-top: 15px;">
						<button class="btn btn-primary btn-label-left" id="addAnt"
							style="margin-right: 10px" type="button" onclick="addSrTable();"><span><i class="fa fa-plus-circle"></i></span>新增組態</button>
						&nbsp;
						<button class="btn btn-primary btn-label-left"
							style="margin-right: 10px" type="button" onclick="delSrTable();"><span><i class="fa fa-minus-circle"></i></span>刪除組態</button>
					</div>
				</td>
			</tr>

		</table>
		<div id="tableAnt">
			<div id="testTableDIV">		
			</div>	
		</div>
	</div>  
				<input type="hidden" name="siteAntList" id="siteAntList" ></input>	
<!-- Toggle effect End -->	
 	<form action="" id="inputAntAddrForm" name="" method="post">
		<span id="abccc">
			<input type="hidden" name="ant_zip" id="ant_zip" />
			<input type="hidden" name="ant_city" id="ant_city" />
			<input type="hidden" name="ant_area" id="ant_area"  />
			<input type="hidden" name="ant_village" id="ant_village" />
			<input type="hidden" name="ant_road" id="ant_road" />
			<input type="hidden" name="ant_adjacent" id="ant_adjacent"  />
			<input type="hidden" name="ant_lane" id="ant_lane"  />
			<input type="hidden" name="ant_alley" id="ant_alley"  />
			<input type="hidden" name="ant_subAlley" id="ant_subAlley"  />
			<input type="hidden" name="ant_doorNo" id="ant_doorNo"  />
			<input type="hidden" name="ant_doorNoEx" id="ant_doorNoEx" />
			<input type="hidden" name="ant_floor" id="ant_floor" />
			<input type="hidden" name="ant_floorEx" id="ant_floorEx" />
			<input type="hidden" name="ant_room" id="ant_room" />
			<input type="hidden" name="ant_remark" id="ant_remark" />
			<input type="hidden" name="ant_disabledFields" id="ant_disabledFields" />
			<input type="hidden" name="ant_callBackFun" id="ant_callBackFun" value="receviceAntCfgAddrData" />
		</span>
		<input type="hidden" name="antAddr" id="antAddr" value="antAddr" />
	</form>
	<div id="showAntAddrFrame"></div>
	<input type="hidden" id="buttonId" />
	<input type="hidden" id="searchSiteId" />

</body>
</html>