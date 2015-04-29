<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">
	<head>
		<title></title>	
		<script type="text/javascript">
		
		$(document).ready(function(){
			
			$("#endDate").datepicker();//退租日期
			$("#cnsDate").datepicker();//預計施工日
			$("#startDate").datepicker();//竣工日期 
			$("#rentEndDate").datepicker();//租期迄日
			
			//正整數
			checkNumber(); 
			//正整數+小數
			checkNumAndPoint(); 
			
		});
		
		//舊專線查詢及舊專線檢視
		function openLineQuery(){
			
			if('${fromLineMaintain}' == 'true' && '${sign}' !== 'true'){
				 $.ajax({
					mimeType : 'text/html; charset=utf-8',
					type : 'POST',
					url : CONTEXT_URL + "/st/line/lineApply/viewOldLineApply",
					data : {
						"targetFrameId" : 'viewOldLinePage',
						"fromLineMaintain": 'true',
						"oLineId" : $("#oLineId").val()
					},
					dataType : "html",
					async : false,
					success : function(data) {
						initialDialogFrame('viewOldLinePage', "檢視舊專線", data);
						
						//初始select change event
						initSelectEvent('viewOldLinePage');
						//初始專線編輯頁的下拉選單
				        initApplySelect('viewOldLinePage');
				        viewApplyDataDetail();  
						$("#viewOldLinePage .view-disabled").prop("disabled",true);
					}
				}); 
			} else{
				openLineQueryFrame('lineQueryPage','lineQuery','${currentMenuId}');
			}
		}
		
		function viewApplyDataDetail(){
			//舊專線退租日期
			<fmt:formatDate value="${oldLineEndDate}" pattern="yyyy/MM/dd" var="oldLineEndDate"/>
			$("#viewOldLinePage #oldEndDate").val('${oldLineEndDate}');
			
			$("#viewOldLinePage #aEqp").val('${viewSiteLineApplyDTO.a_EQP}'); //甲端設備
	        $("#viewOldLinePage #a_Loc").val('${viewSiteLineApplyDTO.a_LOC}');//甲端站點
	        $("#viewOldLinePage #aPortPos").val('${viewSiteLineApplyDTO.a_PORT_POS}');//甲端port
	        $("#viewOldLinePage #bAddr").text('${viewSiteLineApplyDTO.b_ADDR}');//乙端地址 
	        $("#viewOldLinePage #bAddrValue").val('${viewSiteLineApplyDTO.b_ADDR}');//乙端地址
	        $("#viewOldLinePage #bEqp").val('${viewSiteLineApplyDTO.b_EQP}');//乙端設備
	        $("#viewOldLinePage #b_Loc").val('${viewSiteLineApplyDTO.b_LOC}');//乙端站點
	        $("#viewOldLinePage #bChName").text('${viewSiteLineApplyDTO.b_CNT_NAME}'); //乙端聯絡人
	        $("#viewOldLinePage #bName").val('${viewSiteLineApplyDTO.CNT_PSN}'); //乙端聯絡人
	        
	        $("#viewOldLinePage #bPortPos").val('${viewSiteLineApplyDTO.b_PORT_POS}');
	        $("#viewOldLinePage #bTel").text('${viewSiteLineApplyDTO.CNT_TEL}');
	        $("#viewOldLinePage #bTelValue").val('${viewSiteLineApplyDTO.CNT_TEL}');
	        $("#viewOldLinePage #bTelValue").val('${viewSiteLineApplyDTO.CNT_TEL}');
	        $("#viewOldLinePage #chtGe").val('${viewSiteLineApplyDTO.CHT_GE}');
	       
	        <fmt:formatDate value="${viewSiteLineApplyDTO.CNS_DATE}" pattern="yyyy/MM/dd" var="cnsDate"/>
	        <fmt:formatDate value="${viewSiteLineApplyDTO.END_DATE}" pattern="yyyy/MM/dd" var="endDate"/>
	        $("#viewOldLinePage #cnsDate").val('${cnsDate}');//預計施工日
	        $("#viewOldLinePage #endDate").val('${endDate}'); //退租日期
	        $("#viewOldLinePage #linePurpose").val('${viewSiteLineApplyDTO.LINE_PURPOSE}').change();
	        
	        $("#viewOldLinePage #lineStatus").text('${viewSiteLineApplyDTO.LINE_STATUS_NAME}');//專線狀態
	        $("#viewOldLinePage #lineStatusValue").val('${viewSiteLineApplyDTO.LINE_STATUS}');//專線狀態
	        $("#viewOldLinePage #lineId").val('${viewSiteLineApplyDTO.LINE_ID}');
	        $("#viewOldLinePage #oLineId").val('${viewSiteLineApplyDTO.o_LINE_ID}');
	        $("#viewOldLinePage #applyOrderId").text('${viewSiteLineApplyDTO.ORDER_ID}');
	        $("#viewOldLinePage #applyOrderIdHidden").val('${viewSiteLineApplyDTO.ORDER_ID}');
	        $("#viewOldLinePage #rcpNum").val('${viewSiteLineApplyDTO.RCP_NUM}');
	        <fmt:formatDate value="${viewSiteLineApplyDTO.RENT_END_DATE}" pattern="yyyy/MM/dd" var="rentEndDate"/>
	        <fmt:formatDate value="${viewSiteLineApplyDTO.END_DATE}" pattern="yyyy/MM/dd" var="startDate"/>
	        $("#viewOldLinePage #rentEndDate").val('${rentEndDate}'); //租期迄日
	        $("#viewOldLinePage #startDate").val('${startDate}'); //竣工日期
	        $("#viewOldLinePage #lineUseType").val('${viewSiteLineApplyDTO.USE_TYPE}').change();
	        $("#viewOldLinePage #shareCom").val('${viewSiteLineApplyDTO.VENDOR}').change();
	        $("#viewOldLinePage #applyType").change();//申請類別
	        $("#viewOldLinePage #lineTypeDetail").val('${viewSiteLineApplyDTO.LINE_TYPE}').change(); //電路類別 
	        $("#viewOldLinePage #lineSpeed").val('${viewSiteLineApplyDTO.LINE_SPEED}').change();
	        
	        //甲乙端相同線數
	        $("#viewOldLinePage #abSiteTheSameNum").text("${sameSiteCount}");
	        $("#viewOldLinePage #aSiteId").text("${aSiteIds}");
	        $("#viewOldLinePage #bSiteId").text("${bSiteIds}");
	        $("#viewOldLinePage #aAddr").text("${aViewTbSiteLocation.ADDR}");
	        $("#viewOldLinePage #aChName").text('${aViewTbSiteLocation.maintChnNm}'); //甲端聯絡人
	        $("#viewOldLinePage #aTel").text('${aViewTbSiteLocation.maintCellular}');
	        
	        $("#viewOldLinePage #aLineAmount").text("${fn:length(fn:split(aSiteIds,','))}");
	        $("#viewOldLinePage #bLineAmount").text("${fn:length(fn:split(bSiteIds,','))}");
	        if("${oldLineView}" == 'true'){
		        $("#viewOldLinePage .new-hidden").remove();
		        $("#viewOldLinePage .viewOldLine").remove();
	        }
	        
		}
		
		//舊專線查詢 call back function
		function lineQuery(data){
			$("#oLineId").val(data.line_ID);
			$("#shareCom").val(data.vendor);
			$("#linePurpose").val(data.line_PURPOSE);
			$("#lineTypeDetail").val(data.line_TYPE);
			$("#lineTypeDetail").change();
			$("#lineSpeed").val(data.line_SPEED);
			$("#lineUseType").val(data.use_TYPE);
			$("#a_Loc").val(data.a_LOC);
			applyDetail("a",data.a_LOC,null);
			
		}
		//type= a:甲端,b:乙端
		 function searchSiteBtn(type){
			                                      //--call Back--
			                                      //alineDetailData 甲端資料 
			                                      //blineDetailData 乙端資料
			openSiteDialogFrame('lineDetailPage', (type+'lineDetailData'), null,null);
		 }
		 
		 function alineDetailData(siteObjs){
			 var jData = JSON.parse(siteObjs);
			 applyDetail("a",jData.siteObjs[0].location_ID,null);
		 }
		 
		 function blineDetailData(siteObjs){
			 var jData = JSON.parse(siteObjs);
			 applyDetail("b",jData.siteObjs[0].location_ID,null);
		 }
		 

		</script>
	</head>
	<body>
	<fieldset class="ftSolidLine">
	<legend class="legend">申請資料:</legend>
	<table id="lineDetailTable" >
	<tr class="new-hidden" >
		<td ><label class="col-sm-12 control-label ">舊專線號碼:</label></td>
		<td nowrap="nowrap" >
			<div class="col-sm-10">
				<input id="oLineId" name="o_LINE_ID" type="text" class="form-control view-disabled" readonly="readonly" >
			</div>
			
			<div  style="width: 10%; height: auto; float: left; display: inline">
				&nbsp;&nbsp;
				<button class="btn btn-primary btn-label view-disabled" id='oLineBtn' 
					style="margin-right: 10px" type="button"
					onclick="openLineQuery()">${fromLineMaintain eq true and sign ne true ? '檢視':'選擇'}</button>
			</div>
		</td>

		<c:choose>
			<c:when test="${fromLineMaintain eq true and sign ne true}">
				<td colspan="2">
				   <label class="col-sm-5 control-label">舊專線退租日期 :</label>
					<div class="col-sm-6 ">
							<input id="oldEndDate" name="OLD_END_DATE" type="text"
								class="form-control view-disabled" readonly="readonly"
								disabled="disabled">
								<select id="applyType" name="APP_TYPE" style="display:none"  class="form-control maintain-disabled">
								</select>
						</div>
				</td>
			</c:when>
			<c:otherwise>
				<td class="can-show" style="display:none">
				   <label class="col-sm-12 control-label">退租日期 :</label>
			    </td>
				<td class="can-show" style="display:none">
					<div class="col-sm-10 ">
						<input id="endDate" name="END_DATE" type="text" class="form-control view-disabled" readonly="readonly"
							placeholder="點選輸入框">
					</div>
				</td>
			</c:otherwise>
		</c:choose>
	</tr>

	<tr class="viewOldLine">
		<td>
			<div >&nbsp;</div>
		</td>
	</tr>

	<tr>
		<td width="10%" nowrap="nowrap"><label
			class="col-sm-12 control-label"><%-- <span class="s">*</span> --%>
			<c:out value="${oldLineView?'':'新'}"></c:out>專線號碼 :</label></td>
		<td>
			<div class="col-sm-10">
				<input id="lineId" name="LINE_ID" type="text" class="form-control maintain-disabled view-disabled " maxlength="20"  >
			</div>
		</td>

		<td><label class="col-sm-12 control-label">同甲乙端線數:</label></td>
		<td>
			<div class="col-sm-10">
				<p style="padding-top: 15px; padding-left: 5px;" id="abSiteTheSameNum"></p>
			</div>
		</td>
		<td><label class="col-sm-12 control-label">專線狀態:</label></td>
		<td>
			<div class="col-sm-10">
				<p style="padding-top: 15px; padding-left: 5px;" id="lineStatus"></p>
				<input type="hidden" id="lineStatusValue" name="LINE_STATUS"  >
			</div>
		</td>
	</tr>

	<tr>
		<td>
			<div >&nbsp;</div>
		</td>
	</tr>

	<tr>
		<td width="10%" nowrap="nowrap"><label
			class="col-sm-12 control-label"><span class="s">*
			</span>業者別 :</label></td>
		<td width="23%">
			<div class="col-sm-8">
				<select id="shareCom" name="VENDOR" class="form-control maintain-disabled view-disabled"></select>
			</div>
		</td>
		<td><label class="col-sm-12 control-label"><span
				class="s">* </span>甲端(站點) :</label></td>
		<td nowrap="nowrap">
			<div class="col-sm-7">
				<input id="a_Loc" name="a_LOC" type="text" class="form-control view-disabled" readonly="readonly" >
			</div>
			<div
				style="width: 10%; height: auto; float: left; display: inline">
				&nbsp;&nbsp;
				<button id='aSiteBtn' class="btn btn-primary btn-label maintain-disabled view-disabled"
					style="margin-right: 10px" type="button"
					onclick="searchSiteBtn('a')">選擇</button>
			</div>
		</td>
		<td><label class="col-sm-12 control-label"><span
				class="s">* </span>乙端(站點) :</label></td>
		<td nowrap="nowrap">
			<div class="col-sm-7">
				<input id="b_Loc" name="b_LOC" type="text" class="form-control view-disabled" readonly="readonly">
			</div>
			<div
				style="width: 10%; height: auto; float: left; display: inline">
				&nbsp;&nbsp;
				<button id='bSiteBtn' class="btn btn-primary btn-label maintain-disabled view-disabled"
					style="margin-right: 10px" type="button"
					onclick="searchSiteBtn('b')">選擇</button>
			</div>
		</td>
	</tr>

	<tr>
		<td>
			<div>&nbsp;</div>
		</td>
	</tr>

	<tr>
		<td width="10%" nowrap="nowrap"><label
			class="col-sm-12 control-label"><span class="s">*
			</span>電路用途 :</label></td>
		<td width="23%">
			<div class="col-sm-6">
				<select id="linePurpose" name="LINE_PURPOSE" class="form-control maintain-disabled view-disabled"></select>
			</div>
		</td>
		<td width="10%" nowrap="nowrap"><label
			class="col-sm-12 control-label">甲端地址 :</label>
			<input id="aAddrValue" name="a_ADDR" type="hidden" >
		</td>
		<td>
			<p style="padding-top: 15px; padding-left: 5px;" id="aAddr"></p>
		</td>
		<td width="10%" nowrap="nowrap"><label
			class="col-sm-12 control-label">乙端地址 :</label></td>
		<td>
			<p style="padding-top: 15px; padding-left: 5px;" id="bAddr"></p>
			<input id="bAddrValue" name="b_ADDR" type="hidden" >
		</td>
	</tr>

	<tr>
		<td>
			<div>&nbsp;</div>
		</td>
	</tr>

	<tr>
		<td width="10%" nowrap="nowrap"><label
			class="col-sm-12 control-label"><span class="s">*
			</span>電路類別 :</label></td>
		<td width="23%">
			<div class="col-sm-6">
				<select id="lineTypeDetail" name="LINE_TYPE" class="form-control maintain-disabled view-disabled">
				</select>
			</div>
		</td>
		<td width="10%" nowrap="nowrap"><label
			class="col-sm-12 control-label">甲端站台 :</label></td>
		<td>
			<p style="padding-top: 15px; padding-left: 5px;" id="aSiteId"></p>
		</td>
		<td width="10%" nowrap="nowrap"><label
			class="col-sm-12 control-label">乙端站台 :</label></td>
		<td>
			<p style="padding-top: 15px; padding-left: 5px;" id="bSiteId"></p>
		</td>
	</tr>

	<tr>
		<td>
			<div>&nbsp;</div>
		</td>
	</tr>

	<tr>
		<td width="10%" nowrap="nowrap"><label
			class="col-sm-12 control-label"><span class="s">*
			</span>速率 :</label></td>
		<td width="23%">
			<div class="col-sm-6">
				<select id="lineSpeed" name="LINE_SPEED" class="form-control maintain-disabled view-disabled">
					<option value="" >---請選擇--- </option>
				</select>
			</div>
		</td>
		<td width="10%" nowrap="nowrap"><label
			class="col-sm-12 control-label">甲端聯絡人 :</label></td>
		<td>
			<p style="padding-top: 15px; padding-left: 5px;" id="aChName"></p>
		</td>
		<td width="10%" nowrap="nowrap"><label
			class="col-sm-12 control-label">乙端聯絡人 :</label></td>
		<td>
			<p style="padding-top: 15px; padding-left: 5px;" id="bChName"></p>
			<input type="hidden" id="bName" name="CNT_PSN"  >
		</td>
	</tr>

	<tr>
		<td>
			<div>&nbsp;</div>
		</td>
	</tr>

	<tr>
		<td width="10%" nowrap="nowrap"><label
			class="col-sm-12 control-label"><span class="s">*
			</span>使用類別 :</label></td>
		<td width="23%">
			<div class="col-sm-8">
				<select id="lineUseType" name="USE_TYPE" class="form-control maintain-disabled view-disabled">
				</select>
			</div>
		</td>
		<td width="10%" nowrap="nowrap"><label
			class="col-sm-12 control-label">甲端連絡電話 :</label></td>
		<td>
			<p style="padding-top: 15px; padding-left: 5px;" id="aTel"></p>
		</td>
		<td width="10%" nowrap="nowrap"><label
			class="col-sm-12 control-label">乙端連絡電話 :</label></td>
		<td>
			<p style="padding-top: 15px; padding-left: 5px;" id="bTel"></p>
			<input type="hidden" id="bTelValue" name="CNT_TEL" >
		</td>
	</tr>

	<tr>
		<td>
			<div>&nbsp;</div>
		</td>
	</tr>

	<tr>
		<td width="10%" nowrap="nowrap"><label
			class="col-sm-12 control-label">聯單號碼 :</label></td>
		<td>
			<div class="col-sm-10">
				<input id="rcpNum" name="RCP_NUM" type="text" class="form-control view-disabled">
			</div>
		</td>

		<td><label class="col-sm-12 control-label">甲端線數:</label></td>
		<td>
			<div class="col-sm-10">
				<p style="padding-top: 15px; padding-left: 5px;" id="aLineAmount"></p>
			</div>
		</td>
		<td><label class="col-sm-12 control-label">乙端線數:</label></td>
		<td>
			<div class="col-sm-10">
				<p style="padding-top: 15px; padding-left: 5px;" id="bLineAmount"></p>
			</div>
		</td>
	</tr>

	<tr>
		<td>
			<div>&nbsp;</div>
		</td>
	</tr>

	<tr>
		<td><label class="col-sm-12 control-label">預計施工日:</label></td>
		<td>
			<div class="col-sm-10">
				<input id="cnsDate" name="CNS_DATE" type="text"
					class="form-control view-disabled" readonly="readonly"
					placeholder="點選輸入框">
			</div>
		</td>

		<td><label class="col-sm-12 control-label">甲端設備:</label></td>
		<td width="23%">
			<div class="col-sm-8">
				<select id="aEqp" name="a_EQP" class="form-control view-disabled"><option value="">---請選擇---</option></select>
			</div>
		</td>
		<td><label class="col-sm-12 control-label view-disabled">乙端設備:</label></td>
		<td width="23%">
			<div class="col-sm-8">
				<select id="bEqp" name="b_EQP" class="form-control view-disabled"><option value="">---請選擇---</option></select>
			</div>
		</td>
	</tr>

	<tr>
		<td>
			<div>&nbsp;</div>
		</td>
	</tr>

	<tr>
		<td><label class="col-sm-12 control-label">竣工日期 :</label></td>
		<td>
			<div class="col-sm-10">
				<input id="startDate" name="START_DATE" type="text"
					class="form-control view-disabled" readonly="readonly"
					placeholder="點選輸入框">
			</div>
		</td>

		<td><label class="col-sm-12 control-label">甲端PORT位:</label></td>
		<td>
			<div class="col-sm-10">
				<input id="aPortPos" name="a_PORT_POS" type="text" class="form-control checkNum view-disabled" maxlength="20" >
			</div>
		</td>
		<td><label class="col-sm-12 control-label">乙端PORT位:</label></td>
		<td>
			<div class="col-sm-10">
				<input id="bPortPos" name="b_PORT_POS" type="text" class="form-control checkNum view-disabled"  maxlength="20" >
			</div>
		</td>
	</tr>

	<tr>
		<td>
			<div>&nbsp;</div>
		</td>
	</tr>

	<tr>
		<td><label class="col-sm-12 control-label">租期迄日 :</label></td>
		<td>
			<div class="col-sm-10">
				<input id="rentEndDate" name="RENT_END_DATE" type="text"
					class="form-control view-disabled" readonly="readonly"
					placeholder="點選輸入框">
			</div>
		</td>

		<td><label class="col-sm-12 control-label">CHT GE:</label></td>
		<td>
			<div class="col-sm-10">
				<input id="chtGe" name="CHT_GE" type="text" class="form-control view-disabled" maxlength="50">
			</div>
		</td>
		<td></td>
		<td></td>
	</tr>
	<c:if test="${fromLineMaintain == true and sign ne true}">
	<tr class="can-show">
		<td>
			<div>&nbsp;</div>
		</td>
	</tr>
		<tr class="can-show" style="display:none">
			<td><label class="col-sm-12 control-label">退租日期 :</label></td>
			<td>
				<div class="col-sm-10 ">
					<input id="endDate" name="END_DATE" type="text" class="form-control view-disabled" readonly="readonly"
						placeholder="點選輸入框">
				</div>
			</td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
	<tr>
		<td>
			<div>&nbsp;</div>
		</td>
	</tr>
		<tr>
			<td><label class="col-sm-12 control-label">核網設定日期 :</label></td>
			<td>
				<div class="col-sm-10">
					<label id="netCfgDate" ></label>
				</div>
			</td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
	</c:if>
	</table>
	</fieldset>
	<div id="lineDetailPage"></div>
	<div id="lineQueryPage"></div>
	<div id="viewOldLinePage">
	</div>
	
	</body>
</html>