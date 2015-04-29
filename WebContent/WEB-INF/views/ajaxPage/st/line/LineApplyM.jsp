<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<title>專線申請</title>
		<script type="text/javascript">
		var aSiteVlan = []; // 甲端 vlan
		var bSiteVlan = []; // 乙端 vlan
		$(document).ready(function() {
			
			//初始select change event
			initSelectEvent('');
			//初始專線編輯頁的下拉選單
		    initApplySelect('');
			if('${fromLineMaintain}'){  //來源是專線維護編輯(不是從建立作業處理的編輯開啟)
				
				initApplyDataNotJsonObj();
				var siteLineAppStatus = '${siteLineApplyDTO.APP_STATUS}';
				if($.inArray(siteLineAppStatus , ['LA04','LA05','LA06']) == -1){
					$("#lineMaintainSaveBtn").prop("disabled",true);
					$("#lineMaintainUploadBtn").prop("disabled",true);
					$("#lineMaintainExportBtn").prop("disabled",true);
				}
				
				if($.inArray(siteLineAppStatus , ['LA04']) == -1){
					$("#setupNetworkBtn").prop("disabled",true);
				}
				
				if($.inArray(siteLineAppStatus , ['LA04','LA05']) == -1){
					$("#cancelApplyBtn").prop("disabled",true);
				}
				
				if(siteLineAppStatus == 'LA06'){
					$("#cnsDate").prop("disabled",true);
					$("#startDate").prop("disabled",true);
					$("#rentEndDate").prop("disabled",true);
				}
				
				if('${siteLineApplyDTO.NET_CFG_DATE}' != '' || '${siteLineApplyDTO.NET_CFG_DATE}' != null){
					 <fmt:formatDate value="${siteLineApplyDTO.NET_CFG_DATE}" pattern="yyyy/MM/dd HH:mm" var="netCfgDate"/>
					 $("#netCfgDate").text('${netCfgDate}');
				}
				
				$("#oldEndDate").val("${oldEndDate}");
				$(".maintain-disabled").prop("disabled",true); //由專線維護功能開啟時設定不可編輯
				
				$("#lineMaintainSaveBtn").click(function(){ //存檔
					var result = updateOrInsert("save");
					if(result){
						$("#workId").val('${siteLineApplyDTO.ORDER_ID}');
						$("#applicationId").val('${siteLineApplyDTO.APP_ID}');
						$("#lineId").val('${siteLineApplyDTO.LINE_ID}');
						$("#appleType").val('${siteLineApplyDTO.APP_TYPE}').change();
						var data= {
			 					workId : $("#workId").val(),//工單編號
			 					applicationId : $("#applicationId").val(),//申請單號
			 					lineId : $.trim($("#lineId").val()),//專線號碼
			 					appleType : $("#appleType").val()//申請類別
			 			};
						$("#grid").setGridParam({datatype:"json", postData:data, mtype: 'POST',page:1}).jqGrid().trigger("reloadGrid");
					}
				}); //end $("#lineMaintainSaveBtn")
				
				$("#lineMaintainUploadBtn").click(function(){ //檔案上傳
					openFileDialogFrame('fileUploadDiv',  $('#applyWorkType').val(), $("#appId").text());
				}); // end $("#lineMaintainUploadBtn")
				
				$("#lineMaintainExportBtn").click(function(){ //export Excel
					 var $ifrm = $("<iframe style='display:none' />");
	                $ifrm.attr("src", CONTEXT_URL + "/st/line/lineApply/exportLineApplyExcel?appId="+$("#appId").text());
	                $ifrm.appendTo("body");
	                $ifrm.load(function () {
	                   $("body").append(
	                        "<div>Failed to download <i>'" + CONTEXT_URL + "/st/line/lineApply/exportLineApplyExcel?appId="+$("#appId").text() + "'</i>!");
	                }); 
				});// end $("#lineMaintainExportBtn")
				$("#setupNetworkBtn").click(function(){ //核網設定
					var settingCount = 0;
	                $("#LineNeworkTable > tbody > tr").each(function(){
	                	var vlan = $(this).find("input[name='VLAN']").val();
	                	var ip =$(this).find("input[name='IP']").val();
	                	if(vlan !='' && ip != '' && !$(this).hasClass("a_Site")){
	                		settingCount = 1;
	                		return false;
	                	}
	                }); 
	                
	                if(settingCount == 0){
	                	alert("需至少1組VLAN與IP設定！");
	                	return false;
	                }
	                
	                var result = updateOrInsert("additionalSave");
	                if (result) updateLineApply("setupNetwork");
				});//end $("#setupNetworkBtn")
				
				$("#cancelApplyBtn").click(function(){ //取消申請
					 updateLineApply("cancelApply");
				});//end $("#cancelApplyBtn")
				
				if("${view}" == 'view' || "${view}" == 'true' || "${sign}" == 'sign' || "${sign}" == 'true'){
					if("${view}" == 'view' || "${view}" == 'true'){ //檢視專用 
						$("#lineMaintainDownloadBtn").click(function(){
							openFileDownFrame('fileDownloadDiv', $("#applyWorkType").val(), $("#appIdValue").val(), '');
						});
						
						$("#btns button").prop("disabled",true);
						$("#setupNetTable input,textArea").prop("disabled",true);
						$("#lineMaintainExportBtn").prop("disabled",false);
						$("#lineMaintainDownloadBtn").prop("disabled",false).show();
					}
					
					if("${sign}" == 'sign' || "${sign}" == 'true'){ //簽核專用
						$("#btns").remove();
						$("#LineNeworkDIV").remove();
						$("#setupNetTable input").prop("disabled",true);
						$("#setupNetTable #appDesc").prop("disabled",true);
						$("#oLineBtn").prop("disabled",true);
					} 
					
					$("#lineDetailTable input,#lineDetailTable select").prop("disabled",true);
					$("#LineNeworkTable input").prop("disabled",true);
					$("#bCostShare").prop("disabled",true);
				}
			}
			
			//費用分攤費用(乙端)
			$("#bCostShare").click(function(){
				var rowNum = $("#costShareTable tr.b_side").length+ $("#costShareTable tr.ab_side").length;
				var currentNum = 1;
				var percent = 100;
				var remainPercent = percent;
				var averagePercent = parseInt(percent/rowNum);
				$("#costShareTable > tbody :input[name='SHARE_RATE']").each(function(){
					var trClass = $(this).parent().parent().attr('class');
					if(trClass =='b_side' || trClass =='ab_side'){
						if(currentNum == rowNum){//最後一筆，將剩餘金額填入
							$(this).val(remainPercent);
						} else {
							remainPercent = remainPercent - averagePercent;
							$(this).val(averagePercent);
						}
						currentNum++;
					} else {
						$(this).val('0');
					}
					
				});
			});
			
			$("#monFee").mask('#,##9,999',{reverse: true});
			$("#insFee").mask('#,##9,999',{reverse: true});
		});

		//專線申請資料(Not json object)
		function initApplyDataNotJsonObj(){
			$("input#lineId").val('${siteLineApplyDTO.LINE_ID}');
	        $("#applyDeptName").text('${siteLineApplyDTO.deptName}');//申請單位
	        $("#applyDeptId").val('${siteLineApplyDTO.APP_DEPT}');//申請單位 ID
	        $("#appDesc").val('${siteLineApplyDTO.APP_DESC}');
	        $("#appId").text('${siteLineApplyDTO.APP_ID}');
	        $("#appIdValue").val('${siteLineApplyDTO.APP_ID}');
	        $("#appStatusName").text('${siteLineApplyDTO.APP_STATUS_NAME}'); //申請狀態
	        $("#appStatus").val('${siteLineApplyDTO.APP_STATUS}');//申請狀態
	        <fmt:formatDate value="${siteLineApplyDTO.APP_TIME}" pattern="yyyy/MM/dd HH:mm" var="applyTime"/>
	        $("#applyTime").text('${applyTime}');//申請時間
	        $("#applyType").val('${siteLineApplyDTO.APP_TYPE}').change(); //申請類別
	        $("#applyChName").text('${siteLineApplyDTO.APP_USER_NAME}');//申請人
	        $("#applyUserName").val('${siteLineApplyDTO.APP_USER}');//申請人 ID
	        applyDataDetail();
	        $('#applyEqpTypeName').text('${eqpTypeLabel}');
	        $('#applyWorkTypeName').text('${workTypeLabel}');//工務類別名稱
	        $('#applyWorkType').val('${workTypeValue}');//工務類別代碼
	        $('#applyOrderIdHidden').val('${siteLineApplyDTO.ORDER_ID}');
	        
			applyDetail('a','${siteLineApplyDTO.a_LOC}','${siteLineApplyDTO.a_EQP}');
			applyDetail('b','${siteLineApplyDTO.b_LOC}','${siteLineApplyDTO.b_EQP}');
			
			if(bSiteVlan !==undefined ){
				for(var i=0 ; i < bSiteVlan.length ; i++){
					var tr = '<tr><td><div>&nbsp;</div></td></tr><tr><td></td><td></td>';
						tr = tr + "<td></td><td></td>"+bSiteVlan[i] + "</tr>";
					$("#lineDetailTable tbody").append(tr); //VLAN設定
				}
			}
				
			//專線申請簽核
			applySign('${siteLineApplyDTO.APP_ID}');
		}
		
		//專線申請資料
		function applyDataDetail(){
			$("#monFee").val('${siteLineApplyDTO.MON_FEE}');
			$("#insFee").text('${siteLineApplyDTO.INS_FEE}');
			$("#aEqp").val('${siteLineApplyDTO.a_EQP}');
	        $("#a_Loc").val('${siteLineApplyDTO.a_LOC}');
	        $("#aPortPos").val('${siteLineApplyDTO.a_PORT_POS}');
	        $("#bAddr").text('${siteLineApplyDTO.b_ADDR}');
	        $("#bAddrValue").val('${siteLineApplyDTO.b_ADDR}');
	        $("#bEqp").val('${siteLineApplyDTO.b_EQP}');
	        $("#b_Loc").val('${siteLineApplyDTO.b_LOC}');
	        $("#bChName").text('${siteLineApplyDTO.b_CNT_NAME}'); //乙端聯絡人
	        $("#bName").val('${siteLineApplyDTO.CNT_PSN}'); //乙端聯絡人
	        $("#bPortPos").val('${siteLineApplyDTO.b_PORT_POS}');
	        $("#bTel").text('${siteLineApplyDTO.CNT_TEL}');
	        $("#bTelValue").val('${siteLineApplyDTO.CNT_TEL}');
	        $("#chtGe").val('${siteLineApplyDTO.CHT_GE}');
	       
	        <fmt:formatDate value="${siteLineApplyDTO.CNS_DATE}" pattern="yyyy/MM/dd" var="cnsDate"/>
	        <fmt:formatDate value="${siteLineApplyDTO.END_DATE}" pattern="yyyy/MM/dd" var="endDate"/>
	        $("#cnsDate").val('${cnsDate}');//預計施工日
	        $("#endDate").val('${endDate}'); //退租日期
	        
	        $("#linePurpose").val('${siteLineApplyDTO.LINE_PURPOSE}');
	        $("#lineTypeDetail").val('${siteLineApplyDTO.LINE_TYPE}').change();
	        $("#lineSpeed").val('${siteLineApplyDTO.LINE_SPEED}');
	        $("#lineStatus").text('${siteLineApplyDTO.LINE_STATUS_NAME}');//專線狀態
	        $("#lineStatusValue").val('${siteLineApplyDTO.LINE_STATUS}');//專線狀態
	        $("#oLineId").val('${siteLineApplyDTO.o_LINE_ID}');
	        $("#applyOrderId").text('${siteLineApplyDTO.ORDER_ID}');
	        $("#applyOrderIdHidden").val('${siteLineApplyDTO.ORDER_ID}');
	        $("#rcpNum").val('${siteLineApplyDTO.RCP_NUM}');
	        <fmt:formatDate value="${siteLineApplyDTO.RENT_END_DATE}" pattern="yyyy/MM/dd" var="rentEndDate"/>
	        <fmt:formatDate value="${siteLineApplyDTO.START_DATE}" pattern="yyyy/MM/dd" var="startDate"/>
	        $("#rentEndDate").val('${rentEndDate}'); //租期迄日
	        $("#startDate").val('${startDate}'); //竣工日期
	        $("#lineUseType").val('${siteLineApplyDTO.USE_TYPE}');
	        $("#shareCom").val('${siteLineApplyDTO.VENDOR}'); 
		}
		
		//專線申請資料(json object)
		function initApplyData(data){
			$("input#lineId").val(data.line_ID);
			$("#appId").text(data.app_ID);
			$("#appIdValue").text(data.app_ID);
			$("#aEqp").val(data.a_EQP);
	        $("#a_Loc").val(data.a_LOC);
	        $("#aPortPos").val(data.a_PORT_POS);
	        $("#applyDeptName").text(data.deptName);//申請單位
	        $("#applyDeptId").val(data.app_DEPT);//申請單位 ID
	        $("#appDesc").val(data.app_DESC);
	        $("#appId").text(data.app_ID);
	        $("#appIdValue").val(data.app_ID);
	        $("#appStatusName").text(data.app_STATUS_NAME); //申請狀態
	        $("#appStatus").val(data.app_STATUS);//申請狀態
	        
	        $("#applyWorkTypeName").text(data.work_TYPE_NAME); //工務類別
	        $('#applyEqpTypeName').text(data.eqp_NAME);
	        $("p#applyTime").text(data.app_TIME);
	        
	        $("#applyType").val(data.app_TYPE).change(); //申請類別
	        $("#applyChName").text(data.app_USER_NAME);//申請人
	        $("#applyUserName").val(data.app_USER);//申請人 ID
	        $("#bAddr").text(data.b_ADDR);
	        $("#bAddrValue").val(data.b_ADDR);
	        $("#bEqp").val(data.b_EQP);
	        $("#b_Loc").val(data.b_LOC);
	        $("#bChName").text(data.b_CNT_NAME); //乙端聯絡人
	        $("#bName").val(data.cnt_PSN); //乙端聯絡人
	        $("#bPortPos").val(data.b_PORT_POS);
	        $("#bTel").text(data.cnt_TEL);
	        $("#bTelValue").val(data.cnt_TEL);
	        $("#chtGe").val(data.cht_GE);
	       
	        $("#cnsDate").val(data.cns_DATE);
	        $("#endDate").val(data.end_DATE);
	        $("#linePurpose").val(data.line_PURPOSE);
	        
	        $("#lineStatus").text(data.line_STATUS_NAME);//專線狀態
	        $("#lineStatusValue").val(data.line_STATUS);//專線狀態
	        $("#lineTypeDetail").val(data.line_TYPE).change();
	        $("#lineSpeed").val(data.line_SPEED);
	        $("#oLineId").val(data.o_LINE_ID);
	        $("#applyOrderId").text(data.order_ID);
	        $("#applyOrderIdHidden").val(data.order_ID);
	        $("#rcpNum").val(data.rcp_NUM);
	        $("#rentEndDate").val(data.rent_END_DATE);
	        $("#startDate").val(data.start_DATE);
	        $("#lineUseType").val(data.use_TYPE);
	        $("#shareCom").val(data.vendor);
		}
		
		//初始select change event
		function initSelectEvent(divId){
			var origDivId = divId;
			if(divId!=''){
				divId = "#"+divId+" ";
			}
			//申請類別
			$(divId+"select#applyType").change(function(){
				$(divId + ".new-hidden").show();
				$(divId + ".can-show").hide();
				if($(this).val() === 'NEW'){
					$(divId + ".new-hidden").hide();
				}
				
				if($(this).val() === 'CAN' && divId== ''){
					$(divId + ".can-show").show();
				}
				
			});
			
			//電路類別
			$(divId + "select#lineTypeDetail").change(function(){
				
				$(divId + '#lineUseType option').prop("disabled", true);//使用類別
				if($(this).val() === 'SDH' ||  $(this).val() === 'ELL'){
					$(divId + '#lineUseType option').prop("disabled", false);
					//$(divId + '#lineUseType option:first').prop("selected", true);
				} else {
					$(divId + '#lineUseType option:last').prop("disabled", false);
					//$(divId + '#lineUseType option:last').prop("selected", true);
				}
				if($(this).val()){
					$.ajax({
						url : CONTEXT_URL + "/st/line/lineApply/getLineSpeedList",
						type : 'POST',
						dataType : "json",
						data:{
							"lineType" :  $(this).val()
						},
						async : false,
						success : function(data) {
							selectAppend('lineSpeed',data.row, origDivId);
						}
					});
				}
			});
		}
		
		//初始專線編輯頁的下拉選單
		function initApplySelect(divId){
			$.ajax({
				url : CONTEXT_URL + "/st/line/lineApply/init",
				type : 'POST',
				dataType : "json",
				data:{
					"orderId" :  $("#orderId").val()
				},
				async : false,
				success : function(data) {
					selectAppend('applyType' ,data.row['lineApplyTypeList'],divId); //申請類別
					selectAppend('shareCom' ,data.row['sharecomList'],divId); //業者別
					selectAppend('linePurpose' ,data.row['linePurposeList'],divId); //電路用途
					selectAppend('lineUseType' ,data.row['lineUseTypeList'],divId); //使用類別
					selectAppend('lineTypeDetail' ,data.row['lineTypeList'],divId); //電路類別
				}
			});
		}
		
		//專線申請中的 select
		function selectAppend (id,dataList,divId){
			if(divId != ''){
				divId = "#"+divId + " ";
			}
			$(divId + "select#"+id +" option").remove();
			$(divId + "select#"+id).append("<option value ='' >---請選擇---</option>");
			for(var i=0 ; i < dataList.length ; i++){
				$(divId + "select#"+id).append("<option value ='" +dataList[i].code+"'>"+dataList[i].name+"</option>");
			}
		}
		
		//專線申請簽核
		function applySign(appId){
			$.ajax({
				url:CONTEXT_URL + "/commom/signHistory",
				type : 'POST',
				dataType : "html",
				data:{
					"processType" :  "SiteBuildLeaseLineApply",
					"applyNo" : appId
				},
				async : false,
				success : function(data) {
					$("#lineApplySign").find("table").remove();
					$("#lineApplySign").append(data);
				}
			});
		}
		
		 //target: a=甲端資料,b=乙端資料 , eqp
		function applyDetail(target,locationId, eqp){
			$('#'+target+'_Loc').val(locationId);
				$.ajax({
					url : CONTEXT_URL + "/st/line/lineApply/getSiteLocationData",
					type : 'POST',
					dataType : "json",
					data:{
						"appId" :$("#appId").text(),
						"locationId" :  locationId,
						"ab" : target
					},
					async : false,
					success : function(data) {
						
						 var siteLocationDto = data.row['siteLocationDto'];
						 var tbSiteLineSiteDatas = data.row['tbSiteLineSiteDatas'];
						 var btsSiteIds = data.row['btsSiteIds'];
						 var tbSiteMainList = data.row['tbSiteMainList'];
						 var meterialList = data.row['meterialList'];
						 
						 if(siteLocationDto == null){
							$("#"+target+"Addr").text('');
							$("#"+target+"AddrValue").val('');
							$("#"+target+"ChName").text(''); //連絡人
							$("#"+target+"Name").val(''); //連絡人 ID 
							
							$("#"+target+"Tel").text('');
							$("#"+target+"TelValue").val('');
						} else {
							if(siteLocationDto.addr){
								$("#"+target+"Addr").text(siteLocationDto.addr);
								$("#"+target+"AddrValue").val(siteLocationDto.addr);
							} 
							//M,H,C 為機房類型, 則帶出cnt人員資料 TODO
							if($.inArray(siteLocationDto.loc_TYPE , ['M','H','C']) > -1){
								$("#"+target+"ChName").text(siteLocationDto.cnt_PSN); //連絡人
								$("#"+target+"Name").val(siteLocationDto.cnt_PSN); //連絡人 ID 
								$("#"+target+"Tel").text(siteLocationDto.cnt_TEL);
								$("#"+target+"TelValue").val(siteLocationDto.cnt_TEL);
							} else {
								$("#"+target+"ChName").text(siteLocationDto.maintChnNm); //連絡人
								$("#"+target+"Name").val(siteLocationDto.maint_USER); //連絡人 ID 
								$("#"+target+"Tel").text(siteLocationDto.maintCellular);
								$("#"+target+"TelValue").val(siteLocationDto.maintCellular);
							}
						}
						 
						$("#"+target+"SiteId").text(btsSiteIds);
						$("#"+target+"LineAmount").text(data.row['siteNum']); //甲/乙端線數
						$('#abSiteTheSameNum').text(data.row['sameLineNum']); //同甲乙端線數
						
						$("#"+target+"Eqp option").remove();
						$("#"+target+"Eqp").append("<option value=''>---請選擇---</option>");
						if(meterialList){
							for(var i =0; i<meterialList.length ; i++){
								var valeLabel = meterialList[i];
								$("#"+target+"Eqp").append("<option value ='" +valeLabel.value+"'>"+valeLabel.label+"</option>");
							}
						}
						
						if(eqp){
							$("#"+target+"Eqp").val(eqp).change();
						}
						
						
						$("."+target+"_side").remove(); //VLAN設定,分攤費用
						var otherSideClass = (target =='a'?'b':'a');
						$("#LineNeworkTable > tbody > tr.ab_side").each(function(index){
							//移除共同站台，保留單一端的class
							$(this).removeClass("ab_side").addClass(otherSideClass+"_side"); //VLAN設定
							//移除共同站台，保留單一端的class
							var tr = $("#costShareTable > tbody > tr.ab_side:eq("+index+")"); //分攤費用
							tr.removeClass("ab_side").addClass(otherSideClass+"_side");
						});
						
						if(tbSiteMainList != null){
							for(var i=0 ; i< tbSiteMainList.length ; i++){
								var tbSiteMain = tbSiteMainList[i];
								var isBreak = false;
								$("#LineNeworkTable > tbody > tr."+otherSideClass+"_side").each(function(index){
									if($(this).find("input[name='SITE_ID']").val() === tbSiteMain.site_ID){ //甲/乙端有共同站台時
										//將另一端的class替換成共同class
										$(this).removeClass(otherSideClass+"_side").addClass("ab_side");
										
									   //分攤費用 
									   var tr = $("#costShareTable > tbody > tr."+otherSideClass+"_side:eq("+index+")");
									    $(tr).removeClass(otherSideClass+"_side").addClass("ab_side");

										return (isBreak=true);
									}
								});
								
								if(isBreak){ //如果有相同站同時，下一筆
									continue;
								}
								
								var shareRate = tbSiteLineSiteDatas[i].share_RATE==null?'':tbSiteLineSiteDatas[i].share_RATE;
								var vlan = tbSiteLineSiteDatas[i].vlan==null?'':tbSiteLineSiteDatas[i].vlan;
								var ip  =  tbSiteLineSiteDatas[i].ip==null?'':tbSiteLineSiteDatas[i].ip;
								var gateway = tbSiteLineSiteDatas[i].gateway==null?'':tbSiteLineSiteDatas[i].gateway;
								var submask = tbSiteLineSiteDatas[i].submask==null?'':tbSiteLineSiteDatas[i].submask;
								if("${sign}" == 'true' || "${sign}" == 'sign'){ //由簽檢開啟
									if(target == 'a'){
										aSiteVlan.push("<td>"+
												            // "<label class='col-sm-12 control-label'>甲端VLAN("+tbSiteMain.bts_SITE_ID+"):</label>"+
												       "</td>"+
												       "<td><div class='col-sm-10'>" +
												             //"<input type='text' class='form-control' disabled value='" + vlan + "' >"+
												       "</div></td>"); // 甲端 vlan
										aSiteVlan.push("<td>" + 
												           // "<label class='col-sm-12 control-label'>甲端IP("+tbSiteMain.bts_SITE_ID+"):</label>"+
												        "</td>"+
												        "<td><div class='col-sm-10'>"+
												             //"<input type='text' class='form-control' disabled value='" + ip + "' >"+
												        "</div></td>"); // 甲端 ip
									} else {
										bSiteVlan.push("<td>"+
												            "<label class='col-sm-12 control-label'>乙端VLAN("+tbSiteMain.bts_SITE_ID+"):</label>"+
												       "</td>"+
												       "<td><div class='col-sm-10'>"+
												            "<input type='text' class='form-control' disabled value='" + vlan + "' >"+
												      "</div></td>"); // 乙端 vlan
										bSiteVlan.push("<td>"+
												          "<label class='col-sm-12 control-label'>乙端IP("+tbSiteMain.bts_SITE_ID+"):</label>"+
												       "</td>"+
												       "<td><div class='col-sm-10'>"+
												          "<input type='text'class='form-control'  disabled value='" + ip + "' >"+
												       "</div></td>"); // 乙端 ip
									}
																						  //處理畫面且為已竣工(LA06)狀態不得修改
								} else if ("${view}" == "true" || "${view}" == 'view' || (!'${fromLineMaintain}' && $('#appStatus')!=null && $('#appStatus').val() == 'LA06')){
									var tr = "<tr class='"+target+"_side' ><td>" + tbSiteMain.bts_SITE_ID + "<input type='hidden' name='SITE_ID' value='"+tbSiteMain.site_ID+"' ><input type='hidden' name='LOC_ID' value='"+tbSiteMain.location_ID+"' ></td>"+
									 "<td style='padding: 5px 10px 5px 5px'><input id='vlan' class='checkNumAndEng'  name='VLAN' maxlength='20' type='text' style='width:100%' disabled value='"+vlan+"' ></td>"+ //VLAN ID
							         "<td style='padding: 5px 10px 5px 5px'><input class='ipaddress' id='serviceIP' name='IP' maxlength='50' type='text' style='width:100%' disabled value='"+ip+"' ></td>"+ //service IP
							         "<td style='padding: 5px 10px 5px 5px'><input class='ipaddress' id='gatewayIP' name='GATEWAY' maxlength='50' type='text' style='width:100%' disabled value='"+gateway+"' ></td>"+ //Gateway IP
							         "<td style='padding: 5px 10px 5px 5px'><input class='ipaddress' id='subnetMark' name='SUBMASK' maxlength='50' type='text' style='width:100%' disabled value='"+submask+"' ></td></tr>"; //Subnet Mask
									$("#LineNeworkTable tbody").append(tr);
								} else {
									var tr = "<tr class='"+target+"_side' ><td>" + tbSiteMain.bts_SITE_ID + "<input type='hidden' name='SITE_ID' value='"+tbSiteMain.site_ID+"' ><input type='hidden' name='LOC_ID' value='"+tbSiteMain.location_ID+"' ></td>"+
									 "<td style='padding: 5px 10px 5px 5px'><input id='vlan' class='checkNumAndEng'  name='VLAN' maxlength='20' type='text' style='width:100%' value='"+vlan+"' ></td>"+ //VLAN ID
							         "<td style='padding: 5px 10px 5px 5px'><input class='ipaddress' id='serviceIP' name='IP' maxlength='50' type='text' style='width:100%' value='"+ip+"' ></td>"+ //service IP
							         "<td style='padding: 5px 10px 5px 5px'><input class='ipaddress' id='gatewayIP' name='GATEWAY' maxlength='50' type='text' style='width:100%' value='"+gateway+"' ></td>"+ //Gateway IP
							         "<td style='padding: 5px 10px 5px 5px'><input class='ipaddress' id='subnetMark' name='SUBMASK' maxlength='50' type='text' style='width:100%' value='"+submask+"' ></td></tr>"; //Subnet Mask
									$("#LineNeworkTable tbody").append(tr); //VLAN設定
								}
								
								var costSharTr = "<tr class='"+target+"_side' ><td class='tbodytd' style='width: 110px;'>" + tbSiteMain.bts_SITE_ID + "</td>"+
					          	 "<td class='tbodytd' style='width: 110px;'>";
					          	 																							   ////處理畫面且為已竣工(LA06)狀態不得修改
					          	if("${sign}" == 'true' || "${sign}" == 'sign' || "${view}" == "true" || "${view}" == 'view' || (!'${fromLineMaintain}' && $('#appStatus')!=null && $('#appStatus').val() == 'LA06')){
					          		costSharTr += "<input type='text' name='SHARE_RATE' id='sharRate' class='checkNumAndPoint' maxlength='3' disabled value='"+shareRate+"' />%</td></tr>";
					          	} else {
					          		costSharTr += "<input type='text' name='SHARE_RATE' id='sharRate' class='checkNumAndPoint' maxlength='3' value='"+shareRate+"' />%</td></tr>";
					          	}
								$("#costShareTable > tbody").append(costSharTr); //分攤費用
							}
						}
						$(".ipaddress").mask('099.099.099.099');
						
						$("#costShareTable > tbody > tr.a_side").hide(); // 甲端不顯示
						$("#costShareTable > tbody > tr.b_side").show();//共同站及乙端要顯示
						$("#costShareTable > tbody > tr.ab_side").show();//共同站及乙端要顯示
						
						$("#LineNeworkTable tbody tr.a_side").hide(); //將甲VLAN端設定欄位隱藏
						$("#LineNeworkTable tbody tr.ab_side").show();
						$("#LineNeworkTable tbody tr.b_side").show();
						//英數
						checkNumberAndEng();
						//正整數
						checkNumber(); 
						//正整數+小數
						checkNumAndPoint(); 
					}
				});	
		 }
		
		//英數
		function checkNumberAndEng(){
			$(".checkNumAndEng").keypress(function(event) {
				var key = event.which;
			    if(48 <= key && key <= 57)
			        return true;
			    if(65 <= key && key <= 90)
			        return true;
			    if(97 <= key && key <= 122)
			        return true;
			    return false;
	        }).focus(function() { 
	            this.style.imeMode='disabled'; 
	        });
		}
		
		//正整數
		function checkNumber(){
			$(".checkNum").keypress(function(event) { 
	            var keyCode = event.which; 
	            if ((keyCode >= 48 && keyCode <=57)) 
	                return true; 
	            else 
	                return false; 
	        }).focus(function() { 
	            this.style.imeMode='disabled'; 
	        }); 
		}
		//正整數+小數
		function checkNumAndPoint(){
			//正整數+小數
			$(".checkNumAndPoint").keypress(function(event) {  
	            var keyCode = event.which;  
	            if (keyCode == 46 || (keyCode >= 48 && keyCode <=57))  
	                return true;  
	            else  
	                return false;  
	        }).focus(function() {  
	            this.style.imeMode='disabled';
	        });
		}
		
		//分攤比例  輸入0-100
		function checkShareNumber(number){
			if(number <= 100){
				return true;
			} else {
					alert("輸入整數值範圍(0~100).");
					return false;
			}
		}
			
		//專線申請 新增或更新(申請、儲檔)
		function updateOrInsert(method){
			var additionalSave = false;
			if (method == 'additionalSave') {
				method = "save";
				additionalSave = true;
			}
			if($("#applyType option:selected").val() == ""){
				alert("申請類別為必填！");
				return false;
			}
			
			if($("#applyType option:selected").val()=="CAN" && $.trim($("#endDate").val())==''){
				alert("申請類別為[退租]時，退租日期欄位為必填！");
				return false;
			}
			
			if($("#monFee").val() != null && $("#monFee").val().length > 7){
				alert("月租費不得超過六位數");
				return false;
			}
			
			/* if($.trim($("#lineId").val())==''){
				alert("新專線號碼欄位為必填！");
				return false;
			} */
			
			if($.trim($("#shareCom option:selected").val())==''){
				alert("業者別欄位為必填！");
				return false;
			}
			
			if($.trim($("#a_Loc").val())==''){
				alert("甲端(站點)欄位為必填！");
				return false;
			}

			if($.trim($("#b_Loc").val())==''){
				alert("乙端(站點)欄位為必填！");
				return false;
			}
			
			if($.trim($("#linePurpose option:selected").val())==''){
				alert("電路用途欄位為必填！");
				return false;
			}
			
			if($.trim($("#lineTypeDetail option:selected").val())==''){
				alert("電路類別欄位為必填！");
				return false;
			}

			if($.trim($("#lineSpeed option:selected").val())==''){
				alert("速率欄位為必填！");
				return false;
			}
			
			if($.trim($("#lineUseType option:selected").val())==''){
				alert("使用類別欄位為必填！");
				return false;
			}
			
			if($("#shareCom option:selected").val()=="CHT" && $.trim($("#chtGe").val())==''){
				alert("業者別欄位為[中華電信]時，CHT GE欄位為必填！");
				return false;
			}
			
			var lineNetwork = [];
			var shareRateSum = 0;
			var regex =/^((?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))*$/;
			var vlanIdRegex = /^[\d|a-zA-Z]+$/;
			var msg = '';
			var isOk = true;
			var ip='';
			var gateway='';
			var submask='';
			var siteId='';
			
			$("#LineNeworkTable > tbody>tr").each(function(index){// tr .a_site .b_site
				var vlanId = $(this).find("input[name='VLAN']").val();
				var siteId  = $(this).find("[name='SITE_ID']").val();
				var ip=       $(this).find("[name='IP']").val();
				var gateway = $(this).find("[name='GATEWAY']").val();
				var submask = $(this).find("[name='SUBMASK']").val();
				var rate = 0;
				
				if($(this).hasClass("b_side") || $(this).hasClass("ab_side")){ // 甲端vlan無須檢核
				
					if(vlanId != '' && !vlanIdRegex.test(vlanId)){
						msg = 'VLAN ID格式錯誤！';
						isOk = false;
					}
					if(!regex.test(ip)){
		        		msg ='IP格式錯誤！';
		        		isOk = false;
		        	}
					         
	        	 	if(!regex.test(gateway)){
	        			msg ='GATEWAY格式錯誤！';
	        			isOk = false;
	        	 	}
			         
	        		if(!regex.test(submask)){
	        			msg ='SUBMASK格式錯誤！';
	        			isOk = false;
	        	 	}
				         
			     	if(!isOk){
			        	alert('Site ID : '+siteId+"\r\n"+msg);
						return false; //break:數值驗證失敗
					}
					 //分攤費用 
					var tr = $("#costShareTable > tbody > tr:eq("+index+")");
					rate = tr.find("[name='SHARE_RATE']").val();
					isOk = checkShareNumber(rate);
					if(!isOk){
						return false; //break:數值驗證失敗
					}
					
					if(rate){
						shareRateSum = shareRateSum + parseFloat(rate);
					}
				}
				
				lineNetwork.push({
					SITE_ID :siteId,
					LOC_ID  :$(this).find("[name='LOC_ID']").val(),
					SHARE_RATE: rate,
					VLAN    :$(this).find("[name='VLAN']").val(),
					IP      :ip,
					GATEWAY :gateway,
					SUBMASK :submask 
				});
				
			});

			if(!isOk){
				return false; //break:數值驗證失敗 
			}
			
			if(Math.round(shareRateSum) != '100'){
				alert("分攤比例必需加總等於100%!");
				return false;
			}
			var param = "&APP_DESC=" + $("#appDesc").val() + //申請說明
		            "&ORDER_ID=" + $("#applyOrderIdHidden").val() + 
		            "&APP_ID="   + $('#appIdValue').val() + 
		            "&APP_DEPT=" + $('#applyDeptId').val() + //申請人單位
				    "&APP_STATUS=" + $('#appStatus').val() + //申請狀態
					"&APP_TYPE=" + $('#applyType option:selected').val() + //申請類別
					"&APP_USER=" + $('#applyUserName').val();//申請人ID
			if("{fromLineMaintain}"){
				param = param + "&MON_FEE="+($("#monFee").val().replace(/[,]+/g, ''));
			}
		
			$.ajax({
				url : CONTEXT_URL + "/st/line/lineApply/" + method,
				type : 'POST',
				dataType : "json",
				data : $("#lineDetailTable :input").serialize() + param
						+ "&jsonArrStr=" + JSON.stringify(lineNetwork),
				async : false,
				success : function(data) {
					if (!additionalSave) alert(data.msg);
					if(data.success){
						if('${fromLineMaintain}' == 'true' && !additionalSave){
							parent.$.fancybox.close();
						} else {
							tab7Open = false;
							$("#tabs7").click();
							return true;
						}
					}
				}
			});
			return true;
		}
		//專線申請(取消申請、竣工、核網設定)
		function updateLineApply(method) {
			$.ajax({
				url : CONTEXT_URL + "/st/line/lineApply/" + method,
				type : 'POST',
				dataType : "json",
				data : {
					"appId" : $("#appId").text()
				},
				async : false,
				success : function(data) {
					alert(data.msg);
					if(data.success){
						if('${fromLineMaintain}' == 'true'){
							$('#btn_search').click();
							parent.$.fancybox.close();
						}else{
							tab7Open = false;
							$("#tabs7").click();
						}
					}
				}
			});
		}
</script>
			
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
		.ftSolidLine { 
		    display: block;
		    border:1px solid #000000;
		    margin-left: 2px;
		    margin-right: 2px;
		    padding-top: 0.35em;
		    padding-bottom: 0.625em;
		    padding-left: 0.75em;
		    padding-right: 0.75em;
		
		}
		.legend {
			width:inherit;
			font-size:14px;
		    padding:3px 5px;    
		    border-bottom:0px dashed #B6B6B6;
		    margin-bottom:2px;
		}
	</style>		
	</head>
	<body>
	 <div class="box-content">
	 	<form class="form-horizontal" action="">
		<div id="btns" class="row" style="${fromLineMaintain== true ? '':'display:none'}" >
			<div class="col-xs-12 col-sm-12">
				<div class=" ui-draggable ui-droppable">
					<div class="box-header">
						<div class="box-name">
							<i class="fa fa-search"></i> <span>專線申請處理</span>
						</div>
<!-- 						<div class="box-icons"> -->
<!-- 							<a class="collapse-link"> <i class="fa fa-chevron-up"></i> -->
<!-- 							</a> <a class="expand-link"> <i class="fa fa-expand"></i> -->
<!-- 							</a> -->
<!-- 						</div> -->
						<div class="no-move"></div>
					</div>
					<div class="box-content">
						<button class="btn btn-primary btn-label-left" style="margin-right: 10px"
							type="button" id="lineMaintainSaveBtn"><span><i class="fa fa-save"></i></span>存檔</button>
						<button class="btn btn-primary btn-label-left" style="margin-right: 10px"
							type="button" id="lineMaintainUploadBtn"><span><i class="fa fa-upload"></i></span>檔案上傳</button>
						<button class="btn btn-primary btn-label-left" style="margin-right: 10px"
							type="button" id="lineMaintainExportBtn"><span><i class="fa fa-cloud-download"></i></span>匯出Excel</button>
						<button class="btn btn-primary btn-label-left" style="display:none;margin-right: 10px"
							type="button" id="lineMaintainDownloadBtn"  ><span><i class="fa fa-download"></i></span>附件下載</button>	
						<button class="btn btn-primary btn-label-left" style="margin-right: 10px"
							type="button" id="setupNetworkBtn"><span><i class="fa fa-cogs"></i></span>核網設定</button>
						<button class="btn btn-primary btn-label-left" style="margin-right: 10px"
							type="button" id="cancelApplyBtn"><span><i class="fa fa-times"></i></span>取消申請</button>	
					</div>
			</div>
		</div>
	</div>
	
	<table id='lineApplyMTable' class="" style="width: 100%">
				<tr>
					<td width="10%" nowrap="nowrap">
						<label class="col-sm-12 control-label" >工單號碼 :</label>
				    </td>
					<td width="23%">
						<p style="padding-top: 15px; padding-left: 5px;" id="applyOrderId" ></p>
						<input id="applyOrderIdHidden" name="ORDER_ID" type="hidden"></input>
					</td>
					<td width="10%" nowrap="nowrap">
					   <label class="col-sm-12 control-label">工務類別 :</label></td>
					<td width="23%">
						<p style="padding-top: 15px; padding-left: 5px;"  id="applyWorkTypeName">
						</p> <input id="applyWorkType" name="" type="hidden"></input>
					</td>
					<td width="10%"><label class="col-sm-12 control-label" >申請狀態:</label></td>
					<td width="24%">
						<p style="padding-top: 15px; padding-left: 5px;" id="appStatusName"></p>
						<input id="appStatus" name="APP_STATUS" type="hidden"></input>
					</td>
				</tr>
				<tr>
					<td width="10%" nowrap="nowrap"><label
						class="col-sm-12 control-label">申請單號 :</label></td>
					<td width="23%">
						<p style="padding-top: 15px; padding-left: 5px;" id="appId"></p>
						<input id="appIdValue" name="APP_ID" type="hidden"></input>
					</td>
					<td width="10%" nowrap="nowrap">
					   <label class="col-sm-12 control-label">申請單位 :</label></td>
					<td width="23%">
						<p style="padding-top: 15px; padding-left: 5px;" id="applyDeptName"></p>
						<input id="applyDeptId" name="APP_DEPT" type="hidden"></input>
					</td>
					<td width="10%"><label class="col-sm-12 control-label">申請人
							:</label></td>
					<td width="24%">
						<p style="padding-top: 15px; padding-left: 5px;" id="applyChName"></p>
						<input id="applyUserName" name="APP_USER" type="hidden"></input>
					</td>
				</tr>
				<tr>
					<td width="10%" nowrap="nowrap"  >
					 <label	class="col-sm-12 control-label">
						<span class="s">*</span>
						${fromLineMaintain== true ? '異動':'申請'}類別:</label>
					</td>
						
					<td width="23%">
						<div class="col-sm-6">
							<select id="applyType" name="APP_TYPE" class="form-control maintain-disabled">
							</select>
						</div>
					</td>
					<td width="10%" nowrap="nowrap"><label
						class="col-sm-12 control-label">設備型態 :</label></td>
					<td width="23%">
						<p style="padding-top: 15px; padding-left: 5px;" id="applyEqpTypeName"></p> 
						<input id="applyEqpType" name="" type="hidden"></input>
					</td>
					<td width="10%"><label class="col-sm-12 control-label">申請時間
							:</label></td>
					<td width="24%">
						<p style="padding-top: 15px; padding-left: 5px;" id="applyTime"></p> 
					</td>
				</tr>
			</table>
			
			 	<jsp:include page="./LineDetail.jsp"></jsp:include>
				<br>
				<jsp:include page="./LineNetwork.jsp"></jsp:include>
				
				<br>
				<fieldset class="ftSolidLine">
				<legend class="legend">費用維護:</legend>
				<table id="setupNetTable">
				<!-- 專線維護編輯且狀態為限核網設定顯示 -->
				<tr style="${fromLineMaintain eq true and siteLineApplyDTO.APP_STATUS eq 'LA04' ? '':'display:none'}" >
					<td  width="10%" nowrap="nowrap">
					  <label class="col-sm-12 control-label">月租費 :</label>
					</td>  
					<td  width="10%" nowrap="nowrap">
					<!-- 整數六位數(不含,符號) -->
					  <input id="monFee"  name="MON_FEE" type="text" class="checkNum form-control" style="width: 150px;" maxlength="7" />
					</td>
					<td  width="10%" nowrap="nowrap">
					  <label class="col-sm-12 control-label"  >接線移機費 :</label>
					</td>  
					<td  width="10%" nowrap="nowrap">
					  <label class="col-sm-12 control-label" id="insFee" >&nbsp;</label>
					</td>
					<td width="80%" nowrap="nowrap">&nbsp;</td>
				</tr>
				<tr>
					<td width="10%" nowrap="nowrap"><label
						class="col-sm-12 control-label">費用分攤(乙端) :</label>
					</td>
					<td>
					<button id="bCostShare" class="btn btn-primary btn-label view-disabled" style="margin-right: 10px" type="button">平均分攤</button>
					</td>
				</tr>
				<tr>
					<td></td>
					<td colspan="5"><!-- scrollTable -->
							<table id="costShareTable" class="table table-bordered  table-hover table-heading table-datatable "
								style="table-layout: fixed; word-break: break-all; word-wrap: break-word;">
								<thead style="background-color: #f0f0f0;">
									<tr>
										<th class="theadth" style="width: 110px;">站台</th>
										<th class="theadth" style="width: 110px;">分攤比率</th>
									</tr>
								</thead>
								<tbody >
								</tbody>
								
							</table>
					</td>
				</tr>
				
				<tr>
					<td valign='top' width="10%" nowrap="nowrap"
						style="padding-top: 15px;"><label
						class="col-sm-12 control-label">申請說明 :</label></td>
					<td colspan="5" nowrap="nowrap">
						<div style="margin-top: 15px">
							<textarea class="form-control" id="appDesc" name="APP_DESC" rows="3" maxlength="100" ></textarea>
						</div>
					</td>
				</tr>
				<tr>
					<td valign='top' width="10%" nowrap="nowrap"
						style="padding-top: 15px;"><label
						class="col-sm-12 control-label">簽核意見 :</label></td>
					<td colspan="5" >
						<div id="lineApplySign"  style="margin-top: 15px">
						</div>
					</td>
				</tr>				
			</table>
			</fieldset>
			<div id="fileUploadDiv" ></div>
			<div id="fileDownloadDiv" ></div>
			</form>
	 </div>
	</body>
</html>