<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>

<!-- 租約 START -->
<div>
	<fieldset class="ftSolidLine">
		<legend class="legend">租約</legend>
		<table style="width: 100%">
			<tr>
				<td width="11%">
				<input type="hidden" name="CR_USER" value="${lease.CR_USER}">
					<input type="hidden" name="CR_TIME" value="<fmt:formatDate value='${lease.CR_TIME }' pattern='yyyy/MM/dd'/>">
				</td>
				<td width="44%"></td>
				<td width="5%"></td>
				<td width="40%"></td>
			</tr>

            <tr>
				<td nowrap="nowrap"><label class="col-sm-12 control-label">簽約日期
						:</label></td>
				<td>
				<div class="col-sm-8">
				<input id="contractSignDate" name="SIGN_DATE" type="text" size="14"
					class="form-control" readonly="readonly" placeholder="點選輸入框"
					value="<fmt:formatDate value='${lease.SIGN_DATE }' pattern='yyyy/MM/dd'/>">
				</div>
				</td>
				<td nowrap="nowrap"><label class="col-sm-12 control-label">合約狀態
						:</label></td>
				<td>
				<div id="contractStatus" class="col-sm-12"></div>
					<c:choose>
						<c:when test="${lease.LS_STATUS eq '1'}">生效</c:when>
						<c:when test="${lease.LS_STATUS eq '9'}">失效</c:when>
						<c:otherwise>未生效</c:otherwise>
					</c:choose>
					<input type="hidden" name="LS_STATUS" value="${lease.LS_STATUS}">
				</td>
			<tr>
				<td nowrap="nowrap"><label class="col-sm-12 control-label">洽談人員
						:</label></td>
				<td>
					<div class="col-sm-12" style="padding-right:0px;margin-top:5px">
						<div
							style="width: 60%; height: auto; float: left; display: inline">
							<select id="contractDDept" class="populate placeholder" name="DEAL_DEPT_ID">
								<c:forEach items="${domainDept}" var="domainDData">
									<c:if test="${appUserDeptId eq domainDData.DEPT_ID}">
										<option value="${domainDData.DEPT_ID}" selected="selected">${domainDData.DEPT_NAME}</option>
									</c:if>
									<c:if test="${appUserDeptId ne domainDData.DEPT_ID}">
										<option value="${domainDData.DEPT_ID}">${domainDData.DEPT_NAME}</option>
									</c:if>
								</c:forEach>
							</select>
						</div>
						<div
							style="width: 40%; height: auto; float: left; display: inline; padding-left: 5px">
							<select id="contractPSN" class="populate placeholder" name="DEAL_USER">
								<option value="">--請選擇--</option>
							</select>
						</div>
					</div>
				</td>
				<td nowrap="nowrap"><label class="col-sm-12 control-label">建立人員
						:</label></td>
				<td>
					<input type="hidden" id="ls_DOMAIN" name="LS_DOMAIN" value="${lease.LS_DOMAIN}">
					<input type="hidden" id="app_USER_DEPT" name="APP_USER_DEPT" readonly="readonly" value="${appUserDeptId}">
					<input type="hidden" id="app_USER" name="APP_USER" class="form-control" value="${appUserNo}">
					<div id="appUserDiv">&nbsp;&nbsp;<c:out value="${appUserDeptName}"/>&nbsp;<c:out value="${appUserName}"/></div>
				</td>
			</tr>

			<tr>
				<td><label class="col-sm-12 control-label">合約編號 :</label></td>
				<td style="padding-top: 5px;">
					<div class="col-sm-8">
						<input id="lsNo" name="LS_NO" type="text" class="form-control"
							readonly="readonly" value="${lease.LS_NO}" placeholder="系統產生">
						<input type="hidden" id="lsVer" name="LS_VER" value="${lease.LS_VER}">
					</div>
				</td>
				<td><label class="col-sm-12 control-label">合約名稱 :</label></td>
				<td style="padding-top: 5px;">
					<div class="col-sm-10">
						<input id="ls_NAME" name="LS_NAME" maxlength="50" type="text" class="form-control" value="${lease.LS_NAME}">
					</div>
				</td>
			</tr>

			<tr>
				<td><label
					class="col-sm-12 control-label" style="padding-left: 0px">紙本保管單位
						:</label></td>
				<td COLSPAN=3 style="padding-top: 5px;">
					<div class="col-sm-10">
						<div style="width: 50%; height: auto; float: left; display: inline">
							<select id="keepDeptSelect" class="populate placeholder" name="KEEP_DEPT">
								<c:forEach items="${domainDept}" var="domainDData">
									<c:if test="${keepDeptId eq domainDData.DEPT_ID}">
										<option value="${domainDData.DEPT_ID}" selected="selected">${domainDData.DEPT_NAME}</option>
									</c:if>
									<c:if test="${keepDeptId ne domainDData.DEPT_ID}">
										<option value="${domainDData.DEPT_ID}">${domainDData.DEPT_NAME}</option>
									</c:if>
								</c:forEach>
							</select>
						</div>
						<div style="width: 50%; height: auto; float: left; display: inline">
							<p style="padding-top: 5px; padding-left: 10px;" id="keep_PLACE">
							</p>
							<input type="hidden" name="KEEP_PLACE">
						</div>
					</div>
				</td>
			</tr>

			<tr>
				<td><label class="col-sm-12 control-label">合約型態 :</label></td>
				<td style="padding-top: 5px;">
					<div class="col-sm-10">
						<c:choose>
							<c:when test="${lsType eq '0'}">新約</c:when>
							<c:when test="${lsType eq '1'}">續約</c:when>
							<c:when test="${lsType eq '2'}">換約</c:when>
							<c:when test="${lsType eq '3'}">一解一簽</c:when>
							<c:when test="${lsType eq '4'}">解約</c:when>
							<c:when test="${lsType eq '5'}">增補協議</c:when>
						</c:choose>
					</div>
					<input type="hidden" name="LS_TYPE" value="${lsType}">
				</td>
				<td><label class="col-sm-12 control-label">原合約編號 :</label></td>
				<td style="padding-top: 5px;">
					<div class="col-sm-12">
						<div
							style="width: 60%; height: auto; float: left; display: inline">
							<input id="dealNUM" name="ORI_LS_NO" type="text" class="form-control" value="${lease.ORI_LS_NO}">
						</div>
						<div
							style="width: 35%; height: auto; float: left; display: inline; margin-left: 5px">
							<button class="btn btn-primary btn-label" type="button" onclick='openSourceLease("${lease.ORI_LS_NO}","${lease.LS_VER}");'
								id="viewDealBtn">檢視原合約</button>
						</div>
					</div>
				</td>
			</tr>

			<tr>
				<td><label class="col-sm-12 control-label">合約類型 :</label></td>
				<td COLSPAN=3 style="padding-top: 5px;">
					<div class="col-sm-12">
						<c:forEach items="${lsTypeMap}" var="lsType" varStatus="loop">
							<c:if test="${lsType.key eq lease.LS_KIND}">
								<input type="radio" name="LS_KIND" class="lsTypeRadios" value="${lsType.key}" checked="checked">&nbsp;&nbsp;${lsType.value}
							</c:if>
							<c:if test="${lsType.key ne lease.LS_KIND}">
								<input type="radio" name="LS_KIND" class="lsTypeRadios" value="${lsType.key}">&nbsp;&nbsp;${lsType.value}
							</c:if>
							
						</c:forEach>
					</div>
				</td>
			</tr>
			<tr class="resExchSection">
				<td><label class="col-sm-12 control-label">互換業者 :</label></td>
				<td COLSPAN=3 style="padding-top: 5px;">
					<div class="col-sm-12">
						<div style="width: 40%; height: auto; float: left; display: inline; padding-left: 5px">
							<select id="exchCarriersSelect" class="populate placeholder" name="EXCH_CARRIERS">
								<c:forEach items="${carriers}" var="data">
									<option value="${data.key}">${data.value}</option>
								</c:forEach>						
							</select>
						</div>
					</div>
				</td>
			</tr>
			<tr class="resExchSection">
				<td><label class="col-sm-12 control-label">互換項目 :</label></td>
				<td COLSPAN=3 style="padding-top: 5px;">
					<div class="col-sm-12">
						<div style="height: auto; float: left; display: inline">
							<input id="" type="checkbox" name="EXCH_ITEM" value="EX1">&nbsp;&nbsp;機房空間
							<input id="" type="checkbox" name="EXCH_ITEM" style="margin-left: 20px"  value="EX2">&nbsp;&nbsp;租金
							<input id="" type="checkbox" name="EXCH_ITEM" style="margin-left: 20px" value="EX3">&nbsp;&nbsp;鐵塔
							<input id="" type="checkbox" name="EXCH_ITEM" style="margin-left: 20px" value="EX4">&nbsp;&nbsp;電費
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td><label class="col-sm-12 control-label">套表格式 :</label></td>
				<td COLSPAN=2 style="padding-top: 8px;">
					<div class="col-sm-12">
						<select id="formIdSelect" class="populate placeholder" name="FORM_ID">
							<option value="">--請選擇--</option>
							<c:forEach items="${appFormList}" var="appForm">
								<c:choose>
									<c:when test="${not empty lease.FORM_ID && appForm.FORM_ID eq lease.FORM_ID}">
										<option value="${appForm.FORM_ID}" selected="selected" >${appForm.FORM_NAME}</option>
									</c:when>
									<c:otherwise>
										<option value="${appForm.FORM_ID}">${appForm.FORM_NAME}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</div>
				</td>
				<td style="padding-top: 8px">
					<input id="unfrmt" type="checkbox" name="UNFRMT">&nbsp;&nbsp;非制式合約
				</td>
			</tr>

			<tr>
				<td><label class="col-sm-12 control-label">維運區域 :</label></td>
				<td COLSPAN=3>
					<div class="col-sm-12" style="padding-top: 6px">
						<div style="height: auto; float: left; display: inline">
							<c:forEach items="${domain}" var="domainData">
								<input id="" type="checkbox" class="opDomains" name="OP_DOMAIN"
									value="${domainData.ID}">&nbsp;&nbsp;${domainData.NAME}
							</c:forEach>
						</div>
					</div>
				</td>
			</tr>

			<tr>
				<td><label class="col-sm-12 control-label">合約地址 :</label></td>
				<td COLSPAN=3>
					<div class="col-sm-12" style="padding-top: 6px">
						<div style="width: 60%; height: auto; float: left; display: inline">
							<input class="form-control" id="ls_ADD" name="LS_ADD" readonly="readonly" value="${lease.LS_ADD}" />
							<input type="hidden" class="form-control" id="ls_ADD_STD" name="LS_ADD_STD" value='${lease.LS_ADD_STD}' />
							<input type="hidden" class="form-control" id="ls_ZIP" name="LS_ZIP" value="${lease.LS_ZIP}" />
						</div>
						<div style="width: 35%; height: auto; float: left; display: inline; margin-left: 5px">
							<button class="btn btn-primary btn-label" type="button" id="lsAddrBtn">輸入地址</button>
						</div>
						<div id="showLsAddressFrame"></div>
					</div>
				</td>
			</tr>

			<tr>
				<td><label class="col-sm-12 control-label">地點狀態 :</label></td>
				<td>
					<div class="col-sm-12" style="padding-top: 6px">
						<input type="radio" name="PLC_TYPE" value="S">&nbsp;&nbsp;自有住宅
						&nbsp;&nbsp;<input type="radio" name="PLC_TYPE" value="L">&nbsp;&nbsp;租賃
					</div>
				</td>
					<td nowrap="nowrap" style="width: 6%">
						<label class="col-sm-12 control-label">房屋租賃起訖 :</label>
					</td>
					<td>
						<div class="col-sm-12">
							<div
								style="width: 45%; height: auto; float: left; display: inline; margin-top: 6px">
								<input id="rentDateS" name="PLC_S_DATE" type="text" size="12"
									class="form-control" readonly="readonly" placeholder="點選輸入框"
									value="<fmt:formatDate value='${lease.PLC_S_DATE }' pattern='yyyy/MM/dd'/>">
							</div>
							<div
								style="width: 10%; height: auto; float: left; display: inline;; text-align: center; margin-top: 8px">
								至</div>
	
							<div
								style="width: 45%; height: auto; float: left; display: inline; margin-top: 6px">
								<input id="rentDateE" name="PLC_E_DATE" type="text" size="12"
									class="form-control" readonly="readonly" placeholder="點選輸入框"
									value="<fmt:formatDate value='${lease.PLC_E_DATE }' pattern='yyyy/MM/dd'/>">
							</div>
						</div>
					</td>
			</tr>
		</table>
	</fieldset>
</div>
<!-- 租約  END -->

<!-- 租金 / 押金 / 電費   START -->
<div style="margin-top: 5px">
	<fieldset class="ftSolidLine">
		<legend class="legend">租金 / 押金 / 電費</legend>
		<table style="width: 100%">
			<tr>
				<td nowrap="nowrap" style="width: 6%"><label
					class="col-sm-12 control-label">費用項目 :</label></td>
				<td style="width: 25%">
					<div class="col-sm-12" style="padding-top: 4px">
						<div style="height: auto; float: left; display: inline">
							<input id="cRent" name="" type="checkbox" value="R"
							onclick="cRentCheck();">&nbsp;&nbsp;租金&nbsp;&nbsp;
							<input id="cElec" name="" type="checkbox" value="E"
							onclick="cElecCheck();">&nbsp;&nbsp;電費&nbsp;&nbsp;
							<input type="hidden" name="EXPENSE_CAT" id="expenseCat" value="${lease.EXPENSE_CAT}">
						</div>
					</div>
				</td>

				<td nowrap="nowrap" style="width: 6%"><label
					class="col-sm-12 control-label">租金類別 :</label></td>
				<td style="width: 57%">
					<div class="col-sm-4" style="padding-top: 4px">
						<select id="rent_TYPE" class="populate placeholder" name="RENT_TYPE" disabled="disabled" >
							<option value="" selected="selected">--請選擇--</option>
							<option value="0">租金</option>
							<option value="1">管理費</option>
						</select>
					</div>
					<div class="col-sm-8" style="padding-top: 4px">
						<input id="pldgCl" type="checkbox" name="PLDG_CL">&nbsp;&nbsp; 解約清算以押金抵扣租金
					</div>
				</td>
			</tr>
		</table>
		<table style="width: 100%">
			<tr>
				<td nowrap="nowrap" style="width: 6%" ><label class="col-sm-12 control-label">租金總額 :</label></td>
				<td style="width: 14%"><div id="rentTotalDiv1" class="col-sm-12" style="padding-top: 4px"></div>
				</td>
				<td style="width: 80%">
					<div id="rentTotalDiv2" class="col-sm-12" style="padding-top: 4px">						
							</div>
				</td>
			</tr>

			<tr>
				<td nowrap="nowrap"><label class="col-sm-12 control-label">房屋押金總額
						:</label></td>
				<td><div id="houseTotalDiv1" class="col-sm-12" style="padding-top: 4px"></div>
				</td>
				<td>
					<div id="houseTotalDiv2" class="col-sm-12" style="padding-top: 4px">						
							</div>
				</td>
			</tr>

			<tr>
				<td nowrap="nowrap"><label class="col-sm-12 control-label">電費押金總額
						:</label></td>
				<td><div id="elecTotalDiv1" class="col-sm-12" style="padding-top: 4px"></div>
				</td>
				<td>
					<div id="elecTotalDiv2" class="col-sm-12" style="padding-top: 4px">						
							</div>
				</td>
			</tr>
		</table>
	</fieldset>
</div>
<!-- 租金 / 押金 / 電費  END -->

<!-- 合約期間  START -->
<div style="margin-top: 5px">
	<fieldset class="ftSolidLine">
		<legend class="legend">合約期間</legend>
		<table style="width: 100%">
			<c:if test="${lease.LS_TYPE eq '1'}">
				<tr class="TR_CONTINUE">
					<td valign='top' nowrap="nowrap" style="padding-top: 5px"><label
						class="col-sm-12 control-label">續約展延 :</label></td>
					<td COLSPAN=3>
						<div class="col-sm-12">
							<input type="radio" name="CONTI_EXTEND_TYPE" value="0" class="contiOnly" checked>&nbsp;&nbsp;租期不變
							&nbsp;&nbsp;<input type="radio" name="CONTI_EXTEND_TYPE" class="contiOnly" value="1">&nbsp;&nbsp;展延租期變更
						</div>
					</td>
				</tr>
			</c:if>
			<tr>
				<td nowrap="nowrap" style="width: 6%"><label
					class="col-sm-12 control-label"><span class="s">*</span>合約起訖 :</label></td>
				<td style="width: 35%">
					<div class="col-sm-12">
						<div
							style="width: 45%; height: auto; float: left; display: inline; margin-top: 6px">
							<input id="contractDateS" name="LS_S_DATE" type="text" title="合約起日"
								class="form-control" readonly="readonly" placeholder="點選輸入框" 
								value="<fmt:formatDate value='${lease.LS_S_DATE }' pattern='yyyy/MM/dd'/>">
							<input id="oriLsDateS" name="ORI_LS_S_DATE" type="hidden" title="原合約起日"
								class="form-control" readonly="readonly" placeholder="點選輸入框" 
								value="<fmt:formatDate value='${ORI_LS_S_DATE}' pattern='yyyy/MM/dd'/>">
						</div>
						<div
							style="width: 10%; height: auto; float: left; display: inline;; text-align: center; margin-top: 8px">
							至</div>

						<div
							style="width: 45%; height: auto; float: left; display: inline; margin-top: 6px">
							<input id="contractDateE" name="LS_E_DATE" type="text" title="合約訖日"
								class="form-control" readonly="readonly" placeholder="點選輸入框"
								value="<fmt:formatDate value='${lease.LS_E_DATE }' pattern='yyyy/MM/dd'/>">
							<input id="oriLsDateE" name="ORI_LS_E_DATE" type="hidden" title="原合約訖日"
								class="form-control" readonly="readonly" placeholder="點選輸入框" 
								value="<fmt:formatDate value='${ORI_LS_E_DATE}' pattern='yyyy/MM/dd'/>">
							<input id="contiDateE" name="CONTI_E_DATE" type="hidden" title="續約合約訖日"
								class="form-control" readonly="readonly" placeholder="點選輸入框" 
								value="<fmt:formatDate value='${CONTI_E_DATE}' pattern='yyyy/MM/dd'/>">
						</div>
					</div>
				</td>
				<td nowrap="nowrap" style="width: 6%"></td>
				<td style="width: 53%">
					<div class="col-sm-12">
						<div
							style="width: 30%; height: auto; float: left; display: inline; margin-top: 8px">
							<input id="conUndated" type="checkbox" class="contiOnly" onclick="isCheck()" >&nbsp;&nbsp;合約無期限
						</div>
						<div
							style="width: 35%; height: auto; float: left; display: inline; margin-top: 8px">
							<label class="col-sm-12 control-label">共計 :</label>
						</div>
						<div id="conDateDiffDiv" style="width: 30%; height: auto; float: left; display: inline; margin-top: 13px;margin-left: 10px">
							
						</div>
					</div>
				</td>
			</tr>

			<tr>
				<td nowrap="nowrap" style="padding-top: 0px"><label
					class="col-sm-12 control-label">付款月數 :</label></td>
				<td COLSPAN=3>
					<div class="col-sm-12">
						<div
							style="width: 6%; height: auto; float: left; display: inline; margin-top: 12px; margin-right: 6px">
							<input id="payMonths" name="PAY_MONTH" class="form-control"  onblur="checkIntOnBlurEmpty(this);"
								style="padding: 2px 0px 0px 10px" type="text" value="${lease.PAY_MONTH }" >
						</div>
						<div
							style="width: 5%; height: auto; float: left; display: inline; margin-top: 18px">
							月</div>
						<div
							style="width: 13%; height: auto; float: left; display: inline; margin-top: 13px">
							<label class="col-sm-12 control-label">付款週期 :</label>
						</div>
						<div
							style="width: 4%; height: auto; float: left; display: inline; margin: 18px 0px 0px 5px">
							每月</div>
						<div
							style="width: 6%; height: auto; float: left; display: inline; margin-top: 12px; margin-right: 6px">
							<input id="payPeriod" name="PAY_DAY" class="form-control"
								style="padding: 2px 0px 0px 10px" type="text" value="${lease.PAY_DAY }" onblur="checkIntOnBlurEmpty(this);">
						</div>
						<div
							style="width: 3%; height: auto; float: left; display: inline; margin-top: 18px">
							日</div>
						<!--  
						<div
							style="width: 18%; height: auto; float: left; display: inline; margin-top: 13px">
							<label class="col-sm-12 control-label">合約第一站起算日 :</label>
						</div>
						<div
							style="width: 12%; height: auto; float: left; display: inline; margin-top: 13px">
							<p style="padding-top: 6px; padding-left: 10px;" id=""></p>
						</div>
						-->
					</div>
				</td>
			</tr>

			<tr>
				<td nowrap="nowrap" style="padding-top: 10px"><label
					class="col-sm-12 control-label">到期展延 :</label></td>
				<td style="padding-top: 10px">
					<div class="col-sm-12">
						<input type="radio" name="EXT_TYPE" value="0" checked>&nbsp;&nbsp;展延一期
						&nbsp;&nbsp;<input type="radio" name="EXT_TYPE" value="1">&nbsp;&nbsp;自動展延
						&nbsp;&nbsp;<input type="radio" name="EXT_TYPE" value="2">&nbsp;&nbsp;不展延
					</div>
				</td>
				<td nowrap="nowrap" style="padding-top: 10px"><label
					class="col-sm-12 control-label">到期提醒 :</label></td>
				<td style="padding-top: 10px">
					<div class="col-sm-12">
						<input type="radio" name="EXT_RMIND" value="3" checked>&nbsp;&nbsp;到期日前三個月
						&nbsp;&nbsp;<input type="radio" name="EXT_RMIND" value="6">&nbsp;&nbsp;到期日前六個月
					</div>
				</td>
			</tr>

			<tr>
				<td valign='top' nowrap="nowrap" style="padding-top: 5px"><label
					class="col-sm-12 control-label">滯納金條件 :</label></td>
				<td COLSPAN=3>
					<div class="col-sm-12">
						<div
							style="width: 4%; height: auto; float: left; display: inline; margin-top: 10px">
							<p style="padding-top: 0px; padding-left: 4px;" id="">每月</p>
						</div>
						<div
							style="width: 6%; height: auto; float: left; display: inline; margin-top: 8px">
							<input id="" name="PENALTY_DAY" class="form-control"
								style="padding: 2px 0px 0px 10px" type="text" value="${lease.PENALTY_DAY}"  onblur="checkIntOnBlurEmpty(this);"/>
						</div>
						<div
							style="width: 15%; height: auto; float: left; display: inline; margin-top: 10px; padding-left: 5px;">
							號開始產生滯納金</div>
					</div>
				</td>
			</tr>

			<tr>
				<td nowrap="nowrap" style="padding-top: 5px"><label
					class="col-sm-12 control-label">滯納金 :</label></td>
				<td COLSPAN=3>
					<div class="col-sm-12">
						<div
							style="width: 6%; height: auto; float: left; display: inline; margin-top: 10px">
							<input id="penalty_DESC_TEXT" class="form-control"
								style="padding: 2px 0px 0px 10px" type="text" title="滯納金 " onblur="checkIntOnBlurEmpty(this);" />
							<input type="hidden" name="PENALTY_DESC" id="penalty_DESC">
						</div>
						<div style="width: 94%; height: auto; float: left; display: inline; margin-top: 10px">
							&nbsp;&nbsp;<input id="" name="penalty_DESC_RADIO" class="penalty_DESC_RADIO resetCheckBox" type="checkbox" value="%">&nbsp;&nbsp;%
							&nbsp;&nbsp;<input id="" name="penalty_DESC_RADIO" class="penalty_DESC_RADIO resetCheckBox" type="checkbox" value="‰">&nbsp;&nbsp;‰
							&nbsp;&nbsp;<input id="" name="penalty_DESC_RADIO" class="penalty_DESC_RADIO resetCheckBox" type="checkbox" value="$">&nbsp;&nbsp;固定金額
						</div>
					</div>
				</td>
			</tr>
		</table>
	</fieldset>
</div>
<!-- 合約期間   END -->

<!-- 用印申請  START -->
<div style="margin-top: 5px">
	<fieldset class="ftSolidLine">
		<legend class="legend">用印申請</legend>
		<table style="width: 100%">
			<tr>
				<td nowrap="nowrap" style="width: 6%"><label
					class="col-sm-12 control-label">主旨 :</label></td>
				<td style="width: 94%">
					<div class="col-sm-10">
						<input class="form-control" id="seal_PURP" name="SEAL_PURP" value="${lease.SEAL_PURP }" maxlength="200"/>
					</div>
				</td>
			</tr>
			<tr>
				<td nowrap="nowrap"><label class="col-sm-12 control-label">事由
						:</label></td>
				<td style="padding-top: 5px">
					<div class="col-sm-10">
						<input class="form-control" id="seal_RESN" name="SEAL_RESN" value="${lease.SEAL_RESN }" maxlength="200"/>
					</div>
				</td>
			</tr>

			<tr>
				<td nowrap="nowrap"><label class="col-sm-12 control-label">印鑑總類
						:</label></td>
				<td style="padding-top: 5px">
					<div class="col-sm-12" style="padding-top: 4px">	
						<c:forEach items="${lsSealMap}" var="seal" varStatus="loop">
							<c:choose>
								<c:when test="${!loop.last}">
									<input class="seals" id="" name="SEALS" type="checkbox" value="${seal.key}">${seal.value}
									<c:if test="${not (loop.count eq 5)}">&nbsp;&nbsp;</c:if>
									<c:if test="${loop.count eq 5}"><br></c:if>
								</c:when>
								<c:otherwise>
									<input id="" class="seals" name="SEALS" type="checkbox" value="${seal.key}">${seal.value}
									<input class="form-control" id="seal_OTHERS" name="SEAL_OTHERS" maxlength="200" value="${lease.SEAL_OTHERS}" />
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</div>
				</td>
			</tr>

			<tr>
				<td valign='top' nowrap="nowrap"><label
					class="col-sm-12 control-label">其他說明 :</label></td>
				<td style="padding-top: 5px">
					<div class="col-sm-12" style="padding-top: 4px">
						<textarea class="form-control" id="seal_DESC" name="SEAL_DESC" maxlength="200"  rows="3" >${lease.SEAL_DESC }</textarea>
					</div>
				</td>
			</tr>

			<tr>
				<td COLSPAN=2 style="padding-top: 5px"><label
					class="control-label"> 奉&nbsp;核可後由&nbsp;
					<input class="" id="stamper" name="STAMPER" value="${lease.STAMPER }" maxlength="40"/>&nbsp;用印
				</label></td>
			</tr>
		</table>
	</fieldset>
</div>
<!-- 用印申請  END -->

<!-- 增修條文  START -->
<div style="margin-top: 5px">
	<fieldset class="ftSolidLine">
		<legend class="legend">增修條文</legend>
		<button class="btn btn-primary btn-label" type="button" id="selClauseBtn">選擇範本</button>
		<div id="contract_clauseDiv"></div>
		<input type="hidden" name="CLA_ID" id="claId">
	</fieldset>
</div>
<!-- 增修條文   END -->

<!-- 備註說明   START -->
<div style="margin-top: 5px">
	<fieldset class="ftSolidLine">
		<legend class="legend">備註說明</legend>
		<div class="col-sm-12" style="padding-top: 4px; padding-bottom: 10px">
			<textarea class="form-control" id="ls_DESC" name="LS_DESC" rows="5" maxlength="500">${lease.LS_DESC}</textarea>
		</div>
	</fieldset>
</div>
<!-- 備註說明   END -->
<div id="showClausePage"></div>

<script type="text/javascript">
	var rentDateS='';
	var rentDateE='';
    var conDateS='';
    var conDateE='';
    var btnType = '';
	var callBackAddrField;		//Full addr
	var callBackAddrStdField;	//Std addr
	var lsKind = "";
	$(document).ready(function() {
		btnType = $("#btnType_M").val();
		lsKind = '${lease.LS_KIND}';
		$("#signingDate").datepicker();
		$("#contractDDept").trigger("change");
		$("#contractCRDDept").trigger("change");
		
		if (btnType == "new") {
			$('#payMonths').val(1); //付款月數預設1個月
			$('#payPeriod').val(10);  //付款週期預設10號
			$(".resExchSection").hide();
		}
		
		//簽約日期
		var signDate= $('#contractSignDate').datepicker();
		
		
		//房屋租賃期間起訖
		$( "#rentDateS" ).datepicker({
		    onClose: function( selectedDate ) {
		        $( "#rentDateE" ).datepicker( "option", "minDate", selectedDate );
		    },
		    onSelect: function(dateText, inst) { 
		    	rentDateS = dateText;
		    }
		});
		$( "#rentDateE" ).datepicker({
		    onClose: function( selectedDate ) {
		        $( "#rentDateS" ).datepicker( "option", "maxDate", selectedDate );
		    },
		    onSelect: function(dateText, inst) { 
		    	rentDateE = dateText;
		    }
		});
		
		
		//合約起訖
		$( "#contractDateS" ).datepicker({
		    onClose: function( selectedDate ) {
		        $( "#contractDateE" ).datepicker( "option", "minDate", selectedDate );
		    },
		    onSelect: function(dateText, inst) { 
		    	conDateS=dateText;
		    	toDiffDate();
		    }
		});
		$( "#contractDateE" ).datepicker({
		    onClose: function( selectedDate ) {
		        $( "#contractDateS" ).datepicker( "option", "maxDate", selectedDate );
		    },
		    onSelect: function(dateText, inst) { 
		    	conDateE=dateText;
		    	toDiffDate();
		    }
		});
		
		if (btnType == "ReSign"){
			
			var EDate = new Date($("#contractDateS").val());
			
			$( "#contractDateS" ).datepicker( "option", "minDate", EDate);
			$( "#contractDateE" ).datepicker( "option", "minDate", EDate);
		}
		
		var lsType = '${lsType}';
		if (lsType == '1') {
			checkContiueType();
		}
		
		//選擇範本
		$('#selClauseBtn').click(function(){
			openClauseFrame("showClausePage", "toAddClause");
		});
		
		if('${lease.ORI_LS_NO}'!=''){
			$("#dealNUM").prop("readOnly" , false);
			$("#viewDealBtn").prop("disabled" , false);

		}else{
			$("#dealNUM").prop("readOnly" , true);
			$("#viewDealBtn").prop("disabled" , true);
		}

		
		//新約頁面設定
		if(btnType == 'new'){
			signDate.datepicker("setDate", new Date());
		
			//建立人員: 登入者 部門  以及 名稱不可修改
			$("#contractCRDDept").prop("disabled" , true);
			$("#contractCRPSN").prop("disabled" , true);
			getDeptPsn(true);
			getKeepPlace();
			getLsDomain()
			
		} else if (btnType == 'edit' || btnType == 'change' || btnType == 'ReSign'|| btnType == 'continue') {
			loadContractInfo();
			var lsType = '${lease.LS_TYPE}';
			if (lsType == "1") {
				$("#mainTabs-1 :input").prop("disabled", true);
				$(".contiOnly").prop("disabled", false);
			}
		} else if (btnType == 'view') {
			loadContractInfo();
			$("#mainTabs-1 :input").prop("disabled", true);
		}
			
		
	});
	
	//續約展延
	$("input[name='CONTI_EXTEND_TYPE']").click(function(){
		checkContiueType();
	});
	
	//載入合約資訊
	function loadContractInfo() {
		//新增合約，則原合約編號反灰不可輸入 Button 也不可以按
		if('${lease.ORI_LS_NO}'!=''){
			$("#dealNUM").prop("readonly" , true);
			$("#viewDealBtn").prop("disabled" , false);
		}
		//印鑑總類checkbox 勾選
		multiCheckBoxCheck("${lease.SEALS}", ".seals");
		//維運區域checkbox 勾選
		multiCheckBoxCheck("${lease.OP_DOMAIN}", ".opDomains");
		//增修條文
		clauseLoad();
		//非制式合約
		unfrmtLoad();
		//解約清算
		pldgClLoad();
		//
		var extendType = "${lease.CONTI_EXTEND_TYPE}";
		if ($.trim(extendType) != "") {
			sigleRadioLoad("CONTI_EXTEND_TYPE", "${lease.CONTI_EXTEND_TYPE}");
			checkContiueType();
		}
		//地點狀態
		sigleRadioLoad("PLC_TYPE", "${lease.PLC_TYPE}");
		//非制式合約
		sigleRadioLoad("EXT_RMIND", "${lease.EXT_RMIND}");
		//到期展延
		sigleRadioLoad("EXT_TYPE", "${lease.EXT_TYPE}");
		//租金 / 押金 / 電費
		domainMoneyLoad();
		//費用項目
		expenseCatCheckBoxLoad();
		//合約起訖總數(日)
		lsDateLoad();
		//滯納金
		penaltyDescLoad();
		//洽談人員
		getDeptPsn(true);
		getKeepPlace();
		//互換業者
		carrierLoad();
		//互換項目
		exchItemLoad();
	}
	
	function checkContiueType() {
		if ($("input[name='CONTI_EXTEND_TYPE']:checked").val() == '0') {
			if(btnType == 'continue') {
				$("#contractDateE").val($("input[name='CONTI_E_DATE']").val());				
			}
			$("#contractDateE").prop("disabled", true);
			$("#contractDateS").prop("disabled", true);
			$("#conUndated").prop("checked", false).prop("disabled", true);
			$("#conDateDiffDiv").html("");
			
		} else {
			$("#contractDateE").prop("disabled", false);
			$("#contractDateS").prop("disabled", false);
			$("#conUndated").prop("disabled", false);
		}
		
	}

	//開啟增修條文頁面
	function openClauseFrame(targetFrameId) {
		var claId = $("#claId").val();
		
		$.ajax({
					mimeType : 'text/html; charset=utf-8',
					type : 'POST',
					url : CONTEXT_URL + "/ls/LS001M/clause",
					data : {
					  "btnType" : btnType,
					  "claId" : claId
					},
					dataType : "html",
					async : false,
					success : function(data) {
						var hiddenFrameId = "<input type='hidden' id='frameId' name='frameId' value="
						+ targetFrameId + " />";
						initialDialogFrame(targetFrameId, "增修條文", hiddenFrameId + data);
					}
			});
	}

	var clArry = [];
	//增修條文callBack後動作
	function toAddClause(psnData) {
		clArry = [];
		var obj = JSON.parse(psnData);
		var str = "";
		$('#contract_clauseDiv').html("");
		for (var i = 0; i < obj.clauseArry.length; i++) {
			clArry.push(obj.clauseArry[i].id);
			str += '<div class="col-sm-12" style="padding-top: 8px; padding-bottom: 8px; border: 1px solid #000000; font-weight: bold;">'
					+ obj.clauseArry[i].content + '</div>';
		}

		$('#contract_clauseDiv').append(str);
		$("#claId").val(clArry);
	}

	//合約紙本保管單位/地點
	$("#keepDeptSelect").change(function(){
		getKeepPlace();
	});
	
	//取得紙本保管地點
	function getKeepPlace() {
		
		$.ajax({
			url : CONTEXT_URL + "/ls001M/contract/getKeepPlace",
			dataType : "json",
			data : {
				"deptId" : $("#keepDeptSelect").val()
			},
			type : "POST",
			async : false,
			success : function(data) {
				$("#keep_PLACE").text("保管地點名稱(" + data.row.name + "合約室)");
				$("input[name='KEEP_PLACE']").val(data.row.name + "合約室");
			}
		});
	}
	
	$("#contractDDept").change(function(){
		getDeptPsn(false);
		getLsDomain();
	});
	
	function getLsDomain() {
		$.ajax({
			url : CONTEXT_URL + "/ls001M/contract/getKeepPlace",
			dataType : "json",
			data : {
				"deptId" : $("#contractDDept").val()
			},
			type : "POST",
			async : false,
			success : function(data) {
				$("#ls_DOMAIN").val(data.row.id);
			}
		});
	}
	
	//取得部門下人員,init: 是否帶入洽談人員
	function getDeptPsn(init) {
		var dealUser;
		
		if (btnType == "new") {
			dealUser = $("#app_USER").val();
		} else {
			dealUser = '${lease.DEAL_USER}';
		}
		$.ajax({
			url : CONTEXT_URL + "/ls001M/contract/getDeptPSN",
			dataType : "json",
			data : {
				"deptId" : $("#contractDDept").val()
			},
			type : "POST",
			async : false,
			success : function(data) {
				var rows = data.rows;
				
				if (init) {
					for ( var r in rows) {
						if (dealUser == rows[r].psn_NO) {
							$('#contractPSN').append("<option selected value='"+rows[r].psn_NO + "'>"+ rows[r].chn_NM + "</option>");
						} else {
							$('#contractPSN').append("<option value='"+rows[r].psn_NO + "'>"+ rows[r].chn_NM + "</option>");
						}
					}
				} else {
					$("#contractPSN  option").remove();
					$("#contractPSN").append($("<option></option>").attr("value", "").text("--請選擇--"));
					for ( var r in rows) {
						$('#contractPSN').append("<option value='"+rows[r].psn_NO + "'>"+ rows[r].chn_NM + "</option>");
					}
				}
			}
		});
		
		$('#contractPSN').trigger('change');
	}
	
	//點選合約期間時觸發
	function toDiffDate(){	
		//如果合約無期限 不計算合約長度
		if ($("#conUndated").prop("checked")) {
			return;
		}
		if(conDateS != "" && conDateE != ""){
			$('#conDateDiffDiv').html("");
			$('#conDateDiffDiv').append(compareDate(conDateS, conDateE));
		}
	}
	//年月總計
	function compareDate(conDateS,conDateE){
		var SDate = new Date(conDateS);
		var EDate = new Date(conDateE);
		
		var SDays = new Date(SDate.getFullYear(),SDate.getMonth() + 1,0).getDate();	//起日當月總天數
		var EDays = new Date(EDate.getFullYear(),EDate.getMonth() + 1,0).getDate();	//迄日當月總天數
		
		//IF　起訖日同月 總月數 = (迄日 - 起日 + 1) / 當月總天數
		if (SDate.getFullYear() == EDate.getFullYear() && SDate.getMonth() == EDate.getMonth()) {
			var diffDays = EDate.getDate() - SDate.getDate() + 1;
			if (diffDays < 0) {
				$("contractDateE").prop("readonly", false);
				$("contractDateE").val("");
				$("contractDateE").prop("readonly", true);
				alert("合約迄日不得小於合約起日!");
				return;
 			}
			var months = diffDays / SDays;
			
			return formatFloat(months, 1) + "月"
		} else {
			//ELSE 總月數 =
			// 	   (起日當月最後一天日期 - 起日  + 1) / 起日當月總天數 +
			var SMonth = (SDays - SDate.getDate() + 1) / SDays;
			//     (迄年月 - 起年月 -1) + 
			var diffMonth = (EDate.getFullYear() - SDate.getFullYear()) * 12 + 
							(EDate.getMonth() - SDate.getMonth()) - 1;
			//	   (迄日 - 迄日當月第一天日期  + 1) / 迄日當月總天數 
			var EMonth = EDate.getDate() / EDays;
			//年月小記 = 總月數 / 12 (四捨五入到小數1位)
			var totalMonths = SMonth + diffMonth + EMonth;
			if (totalMonths < 0) {
				$("contractDateE").prop("readonly", false);
				$("contractDateE").val("");
				$("contractDateE").prop("readonly", true);
				alert("合約迄日不得小於合約起日!");
				return;
			}
			//總月數 大於等於 12 才需要計算年
			var years = Math.floor(totalMonths / 12);
			var months = formatFloat(totalMonths%12, 1);
			if (totalMonths < 12) {
				months = formatFloat(totalMonths, 1);
				return months + "月";
			} else {
				years = totalMonths / 12;
				months = totalMonths%12;
				return Math.floor(years) + " 年  " + formatFloat(months, 1) + " 月";
			}
		}

    }
	
	//四捨五入 pos:進位的小數位
	function formatFloat(num, pos)
	{
	  var size = Math.pow(10, pos);
	  return Math.round(num * size) / size;
	}
	
	//合約起訖總數(日)
	function lsDateLoad() {
		conDateS =  $("#contractDateS").val();
		conDateE =  $("#contractDateE").val();
		//合約訖日為空時，合約無期限打勾
		$("#conUndated").prop("checked", (isConUndated()));
		toDiffDate();
	}
	
	function isCheck(){
		if($("#conUndated").prop("checked")){
			$('#conDateDiffDiv').html("");			
			$("#contractDateE").val("9999/12/31");
			$("#contractDateE").prop("disabled", true);
			$('#conDateDiffDiv').html("無期限");
		}else{
			$("#contractDateE").val("");
			$("#contractDateE").prop("disabled", false);
			$('#conDateDiffDiv').html("");
		}
	}
	
	//是否合約無期限
	function isConUndated() {
		var EDate = new Date(conDateE);
		if (EDate.getFullYear() == 9999) {
			$('#conDateDiffDiv').html("無期限");
			return true;
		}
		return false;
	}
	
	//所有人地址
	$("#lsAddrBtn").click(function() {
		callBackAddrField = "ls_ADD";
		callBackAddrStdField = "ls_ADD_STD";
		openContractAddressProcess("receviceAddressDataLsAdd");
	});
	
	//顯示地址處理頁
	function openContractAddressProcess(callBackFunc) {
		//取得相對應hidden正規化地址
		var addrStd = $("#" + callBackAddrStdField).val();
		var addr = {
				"zip" : "",
				"city" : "",
				"area" : "",
				"village" : "",
				"road" : "",
				"adjacent" : "",
				"lane" : "",
				"alley" : "",
				"subAlley" : "",
				"doorNo" : "",
				"doorNoEx" : "",
				"floor" : "",
				"floorEx" : "",
				"room" : "",
				"remark" : "",
				"disabledFields" : "",
				"callBackFun" : callBackFunc
		};
		if (addrStd != "") {
			addr = JSON.parse(addrStd);
		}
		openAddressDialogFrame("showLsAddressFrame", addr); // This is implement in common.js
	}
	
	function receviceAddressDataLsAdd(addressObject) {
		var lsAddStdJson = JSON.parse(addressObject);
		//將正規化地址暫存至hidden欄位
		$("#" + callBackAddrStdField).val(addressObject);
		$.ajax({
			url : CONTEXT_URL + "/common/address/combineAddress",
			dataType : "json",
			data : addressObject,
			contentType : 'application/json',
			type : "POST",
			async : false,
			success : function(data) {
				$("#" + callBackAddrField).val(data.row.fullAddress);
				$("#ls_ZIP").val(lsAddStdJson.zip);
			}
		});
	}
	
	//非制式合約
	$("#unfrmt").click(function(){
		checkUnfrmt();
	});
	
	//非制式合約點選動作
	function checkUnfrmt() {
		$("#formIdSelect").prop("disabled", $("#unfrmt").prop("checked"));
		
		if ($("#unfrmt").prop("checked")) {
			$("#formIdSelect").val("");
			$("#formIdSelect").trigger("change");
		}
	}
	
	//多重 checkbox 勾選
	function multiCheckBoxCheck(arrayStr, checkClass){
		$(checkClass).each(function () {
			$(this).prop("checked", ($.inArray($(this).val(), arrayStr.split(',')) > -1));
		});
	}
	
	//增修條文
	function clauseLoad() {
		var clauses = '${lease.jsonClause}';
		if ($.trim(clauses) != "") {
			var datas = {
					"clauseArry": JSON.parse(clauses)
				};
				toAddClause(JSON.stringify(datas));
		}
		
	}
	
	//單選radio
	function sigleRadioLoad(name, value) {
		if ($.trim(value) != "") {
			$('input[name="' + name +'"][value="' + value + '"]').prop("checked",true);
		}
	}
	
	//非制式合約
	function unfrmtLoad() {
		var unfrmt = '${lease.UNFRMT}';
		if ($.trim(unfrmt) != "") {
			$("#formIdSelect").prop("disabled", (unfrmt == "Y"));
			$("#unfrmt").prop("checked", (unfrmt == "Y"));
		}
		if (lsKind == "RESCHG") {
			$("#unfrmt").prop("disabled", true);
			exchItemLoad();
		}
	}
	
	//解約清算
	function pldgClLoad() {
		var pldgCl = '${lease.PLDG_CL}';
		if ($.trim(pldgCl) != "") {
			$("#pldgCl").prop("checked", (pldgCl == "Y"));
		}
	}
	
	function domainMoneyLoad(domainMoneylist) {
		//租金總額
		var rentTotal = 0;
		var rentTotalDiv1 = "";
		var rentTotalDiv2 = "";
		//房屋押金總額
		var housePledgeTotal = 0;
		var houseTotalDiv1 = "";
		var houseTotalDiv2 = "";
		//電費押金總額
		var elecPledgeTotal = 0;
		var elecTotalDiv1 = "";
		var elecTotalDiv2 = "";
		
		var datas = "";
		if (typeof(domainMoneylist)!='undefined') {
			datas = JSON.parse(domainMoneylist);
			$('#rentTotalDiv1').empty();
			$('#rentTotalDiv2').empty();
			
			$('#houseTotalDiv1').empty();
			$('#houseTotalDiv2').empty();
			
			$('#elecTotalDiv1').empty();
			$('#elecTotalDiv2').empty();
		} else if ('${domainMoneylist}' != "") {
			datas = JSON.parse('${domainMoneylist}');
		}
		
		if (datas != "" && datas.length > 0) {
			for (var i=0; i< datas.length; i++) {
				if (datas[i].kind == 'Rent') {
					if (datas[i].rent > 0) {
						rentTotal += datas[i].rent;
						rentTotalDiv2 = rentTotalDiv2 + datas[i].domainName + datas[i].rent + "元,";
					}
					if (datas[i].pledge > 0) {
						housePledgeTotal += datas[i].pledge;
						houseTotalDiv2 = houseTotalDiv2 + datas[i].domainName + datas[i].pledge + "元,";
					}
				} else if (datas[i].kind == 'Elec') {
					if (datas[i].pledge > 0) {
						elecPledgeTotal += datas[i].pledge;
						elecTotalDiv2 = elecTotalDiv2 + datas[i].domainName + datas[i].pledge + "元,";
					}
				}
					
			}
		}
		//租金總額
		if ($.trim(rentTotalDiv2) != "") {
			rentTotalDiv2 = "("+ rentTotalDiv2.substring(0, rentTotalDiv2.length-1) +")";
		}
		$('#rentTotalDiv1').append(rentTotal + "元").hide();
		$('#rentTotalDiv2').append(rentTotalDiv2).hide();
		//房屋押金總額
		if ($.trim(houseTotalDiv2) != "") {
			houseTotalDiv2 = "("+ houseTotalDiv2.substring(0, houseTotalDiv2.length-1) +")";
		}
		$('#houseTotalDiv1').append(housePledgeTotal + "元").hide();
		$('#houseTotalDiv2').append(houseTotalDiv2).hide();
		//電費押金總額
		if ($.trim(elecTotalDiv2) != "") {
			elecTotalDiv2 = "("+ elecTotalDiv2.substring(0, elecTotalDiv2.length-1) +")";
		}
		$('#elecTotalDiv1').append(elecPledgeTotal + "元").hide();
		$('#elecTotalDiv2').append(elecTotalDiv2).hide();
	}
	
	//費用項目:租金  
	function cRentCheck(){
		if ($("#cRent").prop("checked")) {
			$('#rentTotalDiv1').show();
			$('#rentTotalDiv2').show();
			$('#houseTotalDiv1').show();
			$('#houseTotalDiv2').show();
			$("#rent_TYPE").prop("disabled", false);
		} else {
			$('#rentTotalDiv1').hide();
			$('#rentTotalDiv2').hide();
			$('#houseTotalDiv1').hide();
			$('#houseTotalDiv2').hide();
			$("#rent_TYPE").val("");
			$("#rent_TYPE").trigger('change');
			$("#rent_TYPE").prop("disabled", true);
		}
		expenseCatLoad();
	}
	
	//費用項目:電費 
	function cElecCheck(){
		if ($("#cElec").prop("checked")) {
			$('#elecTotalDiv1').show();
			$('#elecTotalDiv2').show();
		} else {
			$('#elecTotalDiv1').hide();
			$('#elecTotalDiv2').hide();
		}
		expenseCatLoad();
	}
	
	//費用項目hidden欄位
	function expenseCatLoad(){
		
		$("#expenseCat").val("");
		var expenseCat = [];
		if ($("#cRent").prop("checked")) {
			expenseCat.push($("#cRent").val());
		}
		if ($("#cElec").prop("checked")) {
			expenseCat.push($("#cElec").val());
		}
		if (expenseCat.length == 0) {
			$("#expenseCat").val("");
		} else {
			$("#expenseCat").val(expenseCat);
		}
	}
		
	//費用項目checkbox
	function expenseCatCheckBoxLoad() {
		var expenseCat = '${lease.EXPENSE_CAT}';
		if ($.trim(expenseCat) != "") {
			var expenseCatArry = expenseCat.split(",");
			for (var i=0; i < expenseCatArry.length; i++) {
				var value = expenseCatArry[i];
				if (value == 'R') {
					$("#cRent").prop("checked", true);
					cRentCheck();
					rentTypeLoad();
				} else {
					$("#cElec").prop("checked", true);
					cElecCheck();
				}
			}
		}
	}
	
	//費用項目RENT_TYPE
	function rentTypeLoad() {
		var rentType = '${lease.RENT_TYPE}';
		if ($.trim(rentType) != "") {
			$("#rent_TYPE").val(rentType);
			$("#rent_TYPE").trigger('change');
		}
	}
	
	//憑證寄送地址 : 多組 checkbox 單選
	$("input[name='penalty_DESC_RADIO']:checkbox").click(function() {
		if ($(this).is(":checked")) {
			var group = "input:checkbox[name='" + $(this).prop("name") + "']";
			$(group).prop("checked", false);
			$(this).prop("checked", true);
		} else {
			$(this).prop("checked", false);
		}
	});
	
	//滯納金
	function penaltyDescLoad() {
		var penaltyDesc = '${lease.PENALTY_DESC}';
		if ($.trim(penaltyDesc) != "") {
			var array = penaltyDesc.split(":");
			$("#penalty_DESC_TEXT").val(array[0]);
			sigleRadioLoad("penalty_DESC_RADIO", array[1]);
		}
	}
	
	//合約類型
	$(".lsTypeRadios").click(function(){
		if ($(this).val() == "RESCHG") {
			$("#unfrmt").prop("checked", true);
			$("#unfrmt").prop("disabled", true);
			$(".resExchSection").show();
		} else {
			if (lsKind == "RESCHG") {
				if (confirm("資源互換相關設定會移除,確定變更嗎?")) {
					$("#unfrmt").prop("checked", false);
					$("#unfrmt").prop("disabled", false);
					$(".resExchSection").hide();
				} else {
					sigleRadioLoad("LS_KIND", "RESCHG");
				}
			}
		}
		checkUnfrmt();
		lsKind = $(this).val();
	});
	
	//互換業者
	function carrierLoad() {
		var carrier = '${lease.EXCH_CARRIERS}';
		if ($.trim(carrier) == "") {
			$(".resExchSection").hide();
		} else {
			$("#exchCarriersSelect").val(carrier);
			$("#exchCarriersSelect").trigger("change");
		}
		
	}
	
	//互換項目
	function exchItemLoad() {
		var exchItem = '${lease.EXCH_ITEM}';
		if ($.trim(exchItem) == "") {
			$(".resExchSection").hide();
		} else {
			$("input[name='EXCH_ITEM']").each(function () {
				$(this).prop("checked", ($.inArray($(this).val(), exchItem.split(',')) > -1));
			});
			$(".resExchSection").show();
		}
	}
	
</script>