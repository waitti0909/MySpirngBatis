<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<div>
	<button class="btn btn-primary btn-label" type="button" id="saveLocationRent" onclick="saveLsLocationRent();"> 
	存檔</button>
	<button class="btn btn-primary btn-label" type="button" id="printLocationRent">
		列印租金起付確認書</button>
</div>

<form id="siteRent_form">
<input id="location_Ls_No" name="LS_NO" type="hidden" value="${lsNo}">
<input id="location_Ls_Ver" name="LS_VER" type="hidden" value="${lsVer}"> 
<input  id="location_id_Rent" name="LOCATION_ID" type="hidden" value=""> 
<input  id="leaseEndDate_Rent" name="LS_E_DATE" type="hidden" value=""> 


<table style="width: 100%">
	<tr>
		<td width="6%"></td>
		<td width="36%"></td>

		<td width="12%"></td>
		<td width="22%"></td>

		<td width="6%"></td>
		<td width="18%"></td>
	</tr>

	<tr>
		<td nowrap="nowrap"><label class="col-sm-12 control-label">承租日
				:</label></td>
		<td>
			<div class="col-sm-12" style="padding-right: 0px">
				<div
					style="width: 47%; height: auto; float: left; display: inline; margin-top: 2px">
					<input id="siteRentDateS" name="EFF_DATE" type="text"
						class="form-control" readonly="readonly" placeholder="點選輸入框">
				</div>
				<div
					style="width: 6%; height: auto; float: left; display: inline;; text-align: center; margin-top: 8px;padding-right: 1px;padding-left: 1px">
					至</div>
				<div
					style="width: 47%; height: auto; float: left; display: inline; margin-top: 2px">
					<input id="siteRentDateE" name="END_DATE" type="text"
						class="form-control" readonly="readonly" placeholder="點選輸入框">
				</div>
			</div>
		</td>

		<td nowrap="nowrap"><label class="col-sm-12 control-label">租金起算日
				:</label></td>
		<td COLSPAN=3>
			<div class="col-sm-5">
				<input id="editPayBeginDate" name="PAY_BEGIN_DATE" type="text" class="form-control"
					readonly="readonly" placeholder="點選輸入框">
			</div>
		</td>
	</tr>

	<tr>
		<td nowrap="nowrap"><label class="col-sm-12 control-label">租金
				:</label></td>
		<td COLSPAN=5>
			<div id="rentType" class="col-sm-12" style="padding-right: 0px;padding-top: 6px">
				<div style="width: 15%; height: auto; float: left; display: inline">
					<input onblur="javascript:calculateExpCost();" max="10" id="editRentAmt" name="RENT_AMT" style="padding: 2px 0px 0px 10px" class="form-control" type="number" min="0" value="0"/>
				</div>
				<div
					style="width: 5%; height: auto; float: left; display: inline; padding-left: 2px; padding-top: 5px">
					元
				</div>
				<div
					style="width: 70%; height: auto; float: left; display: inline; padding-left: 2px; padding-top: 5px">
					<input id="editElecPlus" value="Y" name="ELEC_PLUS" type="checkbox" onclick="removeElecTab(this);">&nbsp;&nbsp;含電費
				</div>
			</div>
		</td>
	</tr>
	
	<tr>
		<td nowrap="nowrap"><label class="col-sm-12 control-label">押金
				:</label></td>
		<td >
			<div id="rentType" class="col-sm-12" style="padding-right: 0px;padding-top: 6px">
				<div style="width: 43%; height: auto; float: left; display: inline">
					<input id="editPldgAmt" max="10" name="PLDG_AMT"  onblur="checkIntOnBlur(this);" style="padding: 2px 0px 0px 10px" class="form-control" type="number" min="0" value="0" />
				</div>
				<div
					style="width: 5%; height: auto; float: left; display: inline; padding-left: 2px; padding-top: 5px">
					元
				</div>
					
			</div>
		</td>
		<td nowrap="nowrap"><label class="col-sm-12 control-label">移入押金金額:</label></td>
		<td >
			<div id="rentType" class="col-sm-10" style="padding-right: 0px;padding-top: 6px">
					<input id="editPldg_OUT" max="10" name="PLDG_IN" onblur="checkIntOnBlurEmpty(this);" style="padding: 2px 0px 0px 10px" class="form-control" type="text" min="0" value="0" />
			</div>
		</td>
		<!-- 	<td nowrap="nowrap"><label class="col-sm-12 control-label">移入日期:</label></td>
			<td >
					<input id="site_pldg_PAY_DATE" name="pldg_PAY_DATE" type="text"
						class="form-control" readonly="readonly" placeholder="點選輸入框">
			</td> -->
	</tr>

	<tr>
		<td nowrap="nowrap"><label class="col-sm-12 control-label">調幅
				:</label></td>
		<td>
			<div id="revise" class="col-sm-12" style="padding-right: 0px">
				<div
					style="width: 20%; height: auto; float: left; display: inline; margin-top: 6px">
					<input id="editIncrMonth" name="INCR_MONTH" class="form-control"  onblur="checkIntOnBlurEmpty(this);"
						style="padding: 2px 0px 0px 10px" type="text" min="0" max="10" 
						value="" />
				</div>
				<div
					style="width: 5%; height: auto; float: left; display: inline; margin: 10px 5px 0px 5px">
					月</div>
				<div
					style="width: 30%; height: auto; float: left; display: inline; margin-top: 6px">
					<input id="editIncrRange" max="10"  name="INCR_RANGE" class="form-control" type="text" onblur="checkIntOnBlurEmpty(this);" style="padding: 2px 0px 0px 10px" />
				</div>
				<div id="incrModelDiv"
					style="width: 35%; height: auto; float: left; display: inline; margin: 6px 0px 0px 8px">
					<input type="radio" id="LOCATION_INCR_MODEL_dallar" name="INCR_MODEL" value="元" checked>元 &nbsp;<input
						type="radio" name="INCR_MODEL" value="%" id="LOCATION_INCR_MODEL_percent">%
				</div>
			</div>
		</td>
		<!-- <td nowrap="nowrap"><label class="col-sm-12 control-label">起始
				:</label></td>
		<td>
			<div class="col-sm-12">
				<div
					style="width: 30%; height: auto; float: left; display: inline; margin-top: 8px">
					<input id="editIncrBeginM" name="INCR_BEGIN_M" class="form-control"
						style="padding: 2px 0px 0px 10px" type="number" min="0" max="12" />
				</div>
				<div
					style="width: 5%; height: auto; float: left; display: inline; margin: 10px 10px 0px 5px">
					月</div>
				<div
					style="width: 30%; height: auto; float: left; display: inline; margin-top: 8px">
					<input id="editIncrBeginD" name="INCR_BEGIN_D" class="form-control"
						style="padding: 2px 0px 0px 10px" type="number" min="0" max="31" />
				</div>
				<div
					style="width: 5%; height: auto; float: left; display: inline; margin: 10px 5px 0px 5px">
					日</div>
			</div>
		</td> -->
		<td nowrap="nowrap"><label class="col-sm-12 control-label">下次調整日期
				:</label></td>
		<td>
			<div class="col-sm-11">
				<input type="text" class="form-control" name="NEXT_INCR" id="LOCATION_NEXT_INCR" >
				
			</div>
		</td>
	</tr>
	
	<tr>
		<td nowrap="nowrap"><label class="col-sm-12 control-label">承租建物
				:</label></td>
		<td COLSPAN=5>
			<div id="building" class="col-sm-12" style="padding-top: 5px" >
				<div style="height: auto; float: left; display: inline" id="bldgTypeDiv">
					<!-- <input id="" name="editBldgType" type="checkbox" value="" checked>&nbsp;&nbsp;房屋  -->
					<!-- <input id="editBldgSm" type="text" size="3" /> 坪 -->
				</div>
			</div>
		</td>
	</tr>
</table>

<!-- 均價  START -->
<div style="margin-top: 5px">
	<fieldset class="ftSolidLine">
		<legend class="legend">均價</legend>
		<input type="hidden" id="priceZipCode" >
		<input type="hidden" id="AREA_AVG" name="AREA_AVG" >
		<table style="width: 100%">
			<tr>
				<td nowrap="nowrap" width="6%"><label class="col-sm-12 control-label">設備類型
						:</label></td>
				<td width="94%">
					<div class="col-sm-12">
						<div
							style="width: 30%; height: auto; float: left; display: inline">
							<select id="editTbComEqpType" class="populate placeholder" name="EQP_TYPE_ID">
							</select>
						</div>
						<div
							style="width: 20%; height: auto; float: left; display: inline">
							<label class="col-sm-12 control-label">Repeater(W) :</label>
						</div>
						<div
							style="width: 20%; height: auto; float: left; display: inline">
							<select id="editTbComEqpModel" class="populate placeholder" name="EQP_MODEL_ID">
							</select>
						</div>
						<div
							style="width: 30%; height: auto; float: left; display: inline;text-align:right;">
							<button class="btn btn-primary btn-label" type="button" id="calculatePriceButton">
								計算均價</button>
						</div>
					</div>
				</td>
			</tr>

		</table>

		<table
			class="table table-striped table-bordered table-hover table-heading no-border-bottom" style="width: 100%;margin-top: 5px">
			<thead>
				<tr>
					<th>&nbsp;</th>
					<th>舊站</th>
					<th>舊站</th>
					<th>舊站</th>
					<th>新站</th>
					<th>新站</th>
					<th>新站</th>
					<th>續約</th>
					<th>續約</th>
					<th>期待</th>
				</tr>
			</thead>
			<tbody id="priceTbody">
			</tbody>
		</table>

	</fieldset>
</div>
<!-- 均價  END -->

<table style="width: 100%;margin-top: 5px; margin-bottom: 15px">
	<tr>
		<td width="6%"></td>
		<td width="36%"></td>
		<td width="4%"></td>
		<td width="23%"></td>
		<td width="8%"></td>
		<td width="23%"></td>
	</tr>
	<tr>
		<td valign='top' nowrap="nowrap"><label
			class="col-sm-12 control-label">預估成本/月 :</label></td>
		<td COLSPAN=5>
			<div class="col-sm-12">
				<p style="padding-top: 6px; padding-left: 5px;" id=""><span id="editEstCost">0</span>元</p>
				<input type="hidden" name="EST_COST" id="EST_COST_HIDDEN" class="contiueOnly" value="0">
				<div style="margin-top: 0px;">
					<div
						style="width: 10%; height: auto; float: left; display: inline; margin-top: 6px">
						<p style="padding-top: 0px; padding-left: 5px;" id="">租金 <span id="editEstRentAmt">0</span></p>
					</div>
					<div
						style="width: 6%; height: auto; float: left; display: inline; margin-top: 5px">
						<p style="padding-top: 0px; padding-left: 5px;" id="">+ 電費</p>
					</div>
					<div
						style="width: 10%; height: auto; float: left; display: inline; margin-top: 0px">
						<input id="editEstElec" name="EST_ELEC" class="form-control contiueOnly"  onblur="calculateExpCost();"
							style="padding: 2px 0px 0px 10px" type="number" min="0" value="0" />
					</div>
					<div
						style="width: 8%; height: auto; float: left; display: inline; margin-top: 5px">
						<p style="padding-top: 0px; padding-left: 5px;" id="">元 + 傳輸</p>
					</div>
					<div
						style="width: 10%; height: auto; float: left; display: inline; margin-top: 0px">
						<input id="editEstDt" name="EST_DT" class="form-control contiueOnly"  onblur="calculateExpCost();"
							style="padding: 2px 0px 0px 10px" type="number" min="0" value="0" />
					</div>
					<div
						style="width: 13%; height: auto; float: left; display: inline; margin-top: 5px">
						<p style="padding-top: 0px; padding-left: 5px;" id="">元 +
							大樓管理費</p>
					</div>
					<div
						style="width: 10%; height: auto; float: left; display: inline; margin-top: 0px">
						<input id="editEstHoa" name="EST_HOA" class="form-control contiueOnly"  onblur="calculateExpCost();"
							style="padding: 2px 0px 0px 10px" type="number" min="0" value="0" />
					</div>
					<div
						style="width: 8%; height: auto; float: left; display: inline; margin-top: 5px">
						<p style="padding-top: 0px; padding-left: 5px;" id="">元 + 其他</p>
					</div>
					<div
						style="width: 10%; height: auto; float: left; display: inline; margin-top: 0px">
						<input id="editEstOthers" name="EST_OTHERS" class="form-control contiueOnly"  onblur="calculateExpCost();"
							style="padding: 2px 0px 0px 10px" type="number" min="0" value="0" />
					</div>
					<div
						style="width: 4%; height: auto; float: left; display: inline; margin-top: 5px">
						<p style="padding-top: 0px; padding-left: 5px;" id="">元</p>
					</div>
				</div>
			</div>
		</td>
	</tr>

	<tr>
		<td nowrap="nowrap"><label class="col-sm-12 control-label">預估話務量
				:</label></td>
		<td COLSPAN=5>
			<div class="col-sm-12">
				<div
					style="width: 15%; height: auto; float: left; display: inline; margin-top: 4px">
					<input id="editEstErl" max="10" min="0" name="EST_ERL" class="form-control contiueOnly" type="number" style="padding: 2px 0px 0px 10px" />
				</div>
				<div
					style="width: 6%; height: auto; float: left; display: inline; margin-top: 8px">
					<p style="padding-top: 0px; padding-left: 5px;" id="">Erl / 月</p>
				</div>

				<div
					style="width: 15%; height: auto; float: left; display: inline; margin-top: 4px">
					<input id="editEstIncome" max="10" min="0" name="EST_INCOME" class="form-control contiueOnly" type="number" style="padding: 2px 0px 0px 10px" />
				</div>
				<div
					style="width: 64%; height: auto; float: left; display: inline; margin-top: 8px">
					<p style="padding-top: 0px; padding-left: 5px;" id="">
						元 / 月<font color='#CC0000'>&nbsp;&nbsp;&nbsp;&nbsp;(舊站請填前半年平均，新站預估)</font>
					</p>
				</div>
			</div>
		</td>
	</tr>

	<tr>
		<td nowrap="nowrap"><label class="col-sm-12 control-label">續/換約原租金
				:</label></td>
		<td>
			<div class="col-sm-12">
				<p style="padding-top: 12px; padding-left: 4px;" id="editOrgRent">0 元
			</div>
		</td>
		<td nowrap="nowrap"><label class="col-sm-12 control-label">與舊約漲幅(-/+)
				:</label></td>
		<td COLSPAN=3>
			<div class="col-sm-12">
				<input type="text" name="CP_RISE" id="editCpRise" readonly="readonly">
			</div>
		</td>
	</tr>

	<tr>
		<td nowrap="nowrap"><label class="col-sm-12 control-label">歷史減租次數
				:</label></td>
		<td>
			<div class="col-sm-6">
				<input id="editReduceCnt" name="REDUCE_CNT" class="form-control contiueOnly" type="text" />
			</div>
		</td>
		<td nowrap="nowrap"><label class="col-sm-12 control-label">歷史總減租金額/調幅
				:</label></td>
		<td COLSPAN=3>
			<div class="col-sm-3">
				<input id="editReduceAmt" name="REDUCE_AMT" class="form-control contiueOnly" type="text" />
			</div>
		</td>
	</tr>

	<tr>
		<td nowrap="nowrap" style="padding-top: 10px"><label
			class="col-sm-12 control-label">涵蓋重點區域 :</label></td>
		<td COLSPAN=5 style="padding-top: 10px">
			<div class="col-sm-12">
				<input id="editMainDomain" name="MAIN_DOMAIN" class="form-control contiueOnly" type="text" />
			</div>
		</td>
	</tr>

	<tr>
		<td valign='top' nowrap="nowrap" style="" COLSPAN=6>
			<div class="col-sm-12" style="padding-right: 0px; margin-top: 10px">
				<div
					style="width: 28%; height: auto; float: left; display: inline;">
					<label class="col-sm-12 control-label" style="padding-left: 0px">對超出均租差額之後續減租配套改善方案
						:</label>
				</div>
				<div
					style="width: 72%; height: auto; float: left; display: inline;">
					<div class="col-sm-12">
						<textarea class="form-control contiueOnly" id="editImprove" name="IMPROVE" rows="3"></textarea>
					</div>
				</div>
			</div>
		</td>
	</tr>
</table>

<hr>

<!-- 租金付款資訊   START -->
<div style="margin-top: 5px">
	<fieldset class="ftSolidLine">
		<legend class="legend">付款資訊</legend>
		<div>
			<button class="btn btn-primary btn-label" type="button" id=""
				onclick="addPaymentRow('rentTbody',false);">新增</button>
			<button class="btn btn-primary btn-label" type="button" id=""
				onclick="delRow('rentTB');">刪除</button>
		
		</div>

		<table id="rentTB"
			class="table table-bordered table-hover table-heading no-border-bottom"
			style="width: 100%; margin-top: 5px">
			<thead>
				<tr>
					<th style="width: 10%;">費用項目</th>
					<th style="width: 10%;">出租人</th>
					<th style="width: 7%;">付款對象證號</th>
					<th style="width: 8%;">名稱</th>
					<th style="width: 10%;">與出租人關係</th>
					<th style="width: 6%;">付款方式</th>
					<th style="width: 8%;">銀行</th>
					<th style="width: 8%;">分行</th>
					<th style="width: 11%;">帳號</th>
					<th style="width: 6%;">分攤比</th>
					<th style="width: 8%;">金額</th>
				<!-- 	<th style="width: 8%;">營業稅</th> -->
				</tr>
			</thead>
			<tbody id="rentTbody">
			</tbody>
		</table>

	</fieldset>
</div>
<!-- 租金付款資訊   END -->

<!-- 租金站台資訊   START -->
<div style="margin-top: 5px">
	<fieldset class="ftSolidLine">
		<legend class="legend">站台資訊</legend>

		<table
			class="table table-striped table-bordered table-hover table-heading no-border-bottom"
			style="width: 100%; margin-top: 5px">
			<thead>
				<tr>
					<th style="width: 20%;">站台編號</th>
					<!-- <th style="width: 20%;">租金起算日</th> -->
					<th style="width: 20%;">終止日</th>
					<th style="width: 20%;">分攤項目</th>
					<th style="width: 20%;">分攤比率</th>
				</tr>
			</thead>
			<tbody id="tbodySite_rent">
			</tbody>
		</table>

	</fieldset>
</div>
<!-- 租金站台資訊   END -->
</form>

<script type="text/javascript">
	$(document).ready(function() {
	   $("#siteRent_form").bind("reset",function(){
			$("#tbodySite_rent").html('');
			$("#rentTbody").html('');
			$('#priceTbody tr').remove();
			
	   });


		var initData = {};
		$.ajax({			
			url : CONTEXT_URL + "/ls/LS001M_Site/getLsLeaseBldgtype",		
			data: initData,
			type : "POST",
			dataType : 'json',
			success : function(data) {
				if(data.success){
					for(var i = 0 ; i<data.rows.length; i++){
						switch(data.rows[i].display_ORDER){
							case '1':
								$("#bldgTypeDiv").append("<input id='editBldgType"+data.rows[i].code+"' name='BLDG_TYPE' type='checkbox' value='"+data.rows[i].code+"' >&nbsp;&nbsp;"+data.rows[i].name);
							  break;
							case '2':
								$("#bldgTypeDiv").append("<input id='editBldgType"+data.rows[i].code+"' name='BLDG_TYPE' type='checkbox' style='margin-left: 20px' value='"+data.rows[i].code+"' >&nbsp;&nbsp;"+data.rows[i].name);
							  break;
							case '3':
								$("#bldgTypeDiv").append("&nbsp;&nbsp;(&nbsp;<input id='editBldgType"+data.rows[i].code+"' name='BLDG_TYPE' type='checkbox'  value='"+data.rows[i].code+"' >&nbsp;"+data.rows[i].name);
								  break;
							case '5':
								$("#bldgTypeDiv").append("<input id='editBldgType"+data.rows[i].code+"' name='BLDG_TYPE' type='checkbox' style='margin-left: 5px' value='"+data.rows[i].code+"' >&nbsp;"+data.rows[i].name+"&nbsp;)");
								  break;
							case '6':
								$("#bldgTypeDiv").append("<input id='editBldgType"+data.rows[i].code+"' name='BLDG_TYPE' type='checkbox' style='margin-left: 20px' value='"+data.rows[i].code+"' >&nbsp;&nbsp;"+data.rows[i].name);
								  break;
							default:
								$("#bldgTypeDiv").append("<input id='editBldgType"+data.rows[i].code+"'  name='BLDG_TYPE' type='checkbox' value='"+data.rows[i].code+"' >&nbsp;&nbsp;"+data.rows[i].name);
						}
					}
						$("#bldgTypeDiv").append("&nbsp;&nbsp;<input id='editBldgSm' name='BLDG_SM' type='text' size='3' onblur='checkIntOnBlurEmpty(this);'  />&nbsp;坪");
				}
			}
		}); 
		
		$.ajax({			
			url : CONTEXT_URL + "/ls/LS001M_Site/getTbComEqpType",		
			data: initData,
			type : "POST",
			dataType : 'json',
			success : function(data) {
				for(var i = 0 ;i<data.rows.length;i++){
					$('#editTbComEqpType').append('<option value="'+data.rows[i].eqp_TYPE_ID+'">'+data.rows[i].eqp_NAME+'</option>');
				}
				rentSelect2Test();
				$('#editTbComEqpType').trigger('change');
			}
		});
		
		
		$('#editTbComEqpType').change(function() {

		   var eqpTypeId = $("#editTbComEqpType").find("option:selected").text().toLowerCase() ;
		   $('#editTbComEqpModel option').remove();
		   $('#editTbComEqpModel').select2("data",null);
		   if(eqpTypeId.indexOf("node")>-1){
			   $('#editTbComEqpModel').append('<option value="All">All</option>');
		   }else{
			   var data = {
						eqpTypeId : $("#editTbComEqpType").val()
				};
				$.ajax({			
					url : CONTEXT_URL + "/ls/LS001M_Site/getTbComEqpModel",		
					data: data,
					type : "POST",
					dataType : 'json',
					success : function(data) {

						for(var i = 0 ;i<data.rows.length;i++){
							$('#editTbComEqpModel').append('<option value="'+data.rows[i].eqp_MODEL_ID+'">'+data.rows[i].eqp_MODEL+'</option>');
						}
						rentSelect2Test();
					}
				});
		   }	
		});
		
		$('#calculatePriceButton').click(function(){
			
			var data = {
					eqpTypeId : $("#editTbComEqpType").val(),
					eqpModelId : $("#editTbComEqpModel").val(),
					zipCode : ($("#editTbComEqpType").val().indexOf("repeater")>-1)?"All":$("#priceZipCode").val()
			};
			$.ajax({			
				url : CONTEXT_URL + "/ls/LS001M_Site/getTbLsAvgRent",		
				data: data,
				type : "POST",
				dataType : 'json',
				success : function(dataReturn) {
					if(!dataReturn.success){
						alert("找無均價資料");
						$('#AREA_AVG').val("");
						$('#priceTbody tr').remove();
						return;	
					}
				
					$('#AREA_AVG').val(JSON.stringify(dataReturn));
					setepAREA_AVG();	
				}
			});
		});
		
		
		//租金起算日
		$("#editPayBeginDate").datepicker();
		$("#site_pldg_PAY_DATE").datepicker();
		$("#LOCATION_NEXT_INCR").datepicker();
		
		addClassSEL("rentTB");
		
		$('#siteTabs').hide();
	});
     //均價Table 展開
	function setepAREA_AVG(){
		if($('#AREA_AVG').val()==''){
			return false;
		}
		console.log("$('#AREA_AVG').val()="+data);
		var data=JSON.parse($('#AREA_AVG').val());
	
		$('#priceTbody tr').remove();
		if(data!='' && data.rows!=null){
			
			//$('#priceTbody').append('<tr><td>&nbsp;</td><td>標準合法站</td><td>標準策略站</td><td>標準總計</td><td>承租合法站</td><td>承租策略站</td><td>承租總計</td><td>標準合法站</td><td>標準策略站</td><td>期待均價</td></tr>');
			var row = '';
			
			for(var i = 0 ;i<data.rows.length;i++){
				row += '<tr>';
				row += '<td>'+data.rows[i].zip_CODE+'</td>';
				row += '<td>'+(data.rows[i].old_IP==null?0:data.rows[i].old_IP)+'</td>';
				row += '<td>'+(data.rows[i].old_NIP==null?0:data.rows[i].old_NIP)+'</td>';
				row += '<td>'+(data.rows[i].old_TOTAL==null?0:data.rows[i].old_TOTAL)+'</td>';
				row += '<td>'+(data.rows[i].new_IP==null?0:data.rows[i].new_IP)+'</td>';
				row += '<td>'+(data.rows[i].new_NIP==null?0:data.rows[i].new_NIP)+'</td>';
				row += '<td>'+(data.rows[i].new_TOTAL==null?0:data.rows[i].new_TOTAL)+'</td>';
				row += '<td>'+(data.rows[i].ext_IP==null?0:data.rows[i].ext_IP)+'</td>';
				row += '<td>'+(data.rows[i].ext_NIP==null?0:data.rows[i].ext_NIP)+'</td>';
				row += '<td>'+(data.rows[i].com_TOTAL==null?0:data.rows[i].com_TOTAL)+'</td>';
				$('#priceTbody').append(row);
			}
		}
	}

	
	
	

	function saveLsLocationRent(){
		$("#siteRent_form :input").prop("disabled", false);
	    if (!validateRentBeforeSubmit()) {
	    	if ('${lsType}' == '1') {
	    		$("#siteRent_form :input").prop("disabled", true);
	    		$(".contiueOnly").prop("disabled", false);
	    	}
			return false;
		} else { 
			 var url =  CONTEXT_URL + "/ls001M/saveLeaseLocationRent";

			 if($("#site_LsType").val()==1){
				 url =  CONTEXT_URL + "/ls001M/saveLeaseLocationRentAdded";
			 }
			 $.ajax({
				url : url,
				type : 'POST',
				dataType : "json",
				data : $("#siteRent_form").serialize(),
				async : false,
				success : function(data) {
					alert(data.msg);
					if ('${lsType}' == '1') {
			    		$("#siteRent_form :input").prop("disabled", true);
			    		$(".contiueOnly").prop("disabled", false);
			    	}
			    	
					getLsDomainMoneyList();
				}
			}); 



				
		}

	}

	function calculateExpCost(){
		$('#editEstRentAmt').text($('#editRentAmt').val());
		var total =  Number($('#editRentAmt').val())+Number($('#editEstElec').val())+Number($('#editEstOthers').val())
		+Number($('#editEstHoa').val())+Number($('#editEstDt').val());
		$('#editEstCost').text(total);
		$('#EST_COST_HIDDEN').val(total);
		
	}	

	function validateRentBeforeSubmit(){
		/* if($("#editBldgSm").val()!=''){
			$("#editBldgTypeL").prop("checked",true);
		} */
		if(!checkDateStrRange($("#siteRentDateS").val(),$("#siteRentDateE").val())){
			alert("租金起訖時間錯誤");
			return false;
		}
		/* if($("#editPayBeginDate").val()!=''){
			if(!checkDateStrRange($("#siteRentDateS").val(),$("#editPayBeginDate").val())){
				alert("租金起算日必須在租金起間內");
				return false;
			}
			if(!checkDateStrRange($("#editPayBeginDate").val(),$("#siteRentDateE").val())){
				alert("租金起算日必須在租金起間內");
				return false;
			}
		} */
		if($("#editIncrMonth").val()!='' || $("#editIncrRange").val()!=''){
			if(!$("#LOCATION_INCR_MODEL_dallar").prop("checked")
					&& !$("#LOCATION_INCR_MODEL_percent").prop("checked")){
				alert("請選擇下次調幅單位");
				return false;
			}else{
				if($("#editIncrMonth").val()==''|| $("#editIncrRange").val()==''){
		    		alert("請輸入調幅金額以及方式");
					return false;
			    }
			}
			if($("#LOCATION_NEXT_INCR").val()==''){
				alert("請選擇下次調幅日期");
				return false;
			}
	    }else if($("#editIncrMonth").val()=='' && $("#editIncrRange").val()==''){
			$("#LOCATION_INCR_MODEL_dallar").prop("checked",false);
			$("#LOCATION_INCR_MODEL_percent").prop("checked",false);

		}

	/* 	if($("#rentTbody tr").length==0){
			alert("請輸入付款資訊");
			return false;
		
		}
 */
        var checkFlag = true;				
		if($("#editIncrMonth").val()!=''){
			checkFlag=qtyCheck($("#editIncrMonth").val(),"調幅");
		}
		if(!checkFlag){
			return checkFlag;
		}
		
		if($("#editIncrRange").val()!=''){
			checkFlag=qtyCheck($("#editIncrRange").val(),"調幅金額");
		}
		if(!checkFlag){
			return checkFlag;
		}
		checkFlag = checkBankNecessary("rentTbody", "payment_Mode", "UNIT_CODE", "SUB_UNIT_CODE","ACCOUNT_NO");
		if(!checkFlag){
			return checkFlag;
		}
		
		var rrr =  checkRepeatPayment("rentTbody");

		console.log("rrr="+rrr);
		
		if(rrr){		
			if(qtyCheck($("#editRentAmt").val(),"租金") && qtyCheck($("#editPldgAmt").val(),"押金")
					&& qtyCheck($("#editEstElec").val(),"預估電費") && qtyCheck($("#editEstDt").val(),"預估傳輸")
					&& qtyCheck($("#editEstHoa").val(),"預估大樓管理費") && qtyCheck($("#editEstOthers").val(),"預估其他") 
					&& qtyCheck($("#editEstErl").val(),"預估話務量Erl/月") && qtyCheck($("#editEstIncome").val(),"預估話務量元/月")
				//&& qtyCheck($("#editReduceCnt").val(),"歷史減租次數") && qtyCheck($("#editReduceAmt").val(),"歷史總減租金額/")
			){
				return true;
			}
		}
		return false;
		
	}
	
	
	function removeElecTab(theField){
		if(theField.checked){
			alert("租金包含電費不必輸入電費資訊");
			$("#siteTabs-2_li").hide();	
		}else{
			$("#siteTabs-2_li").show();

		}

	}
	function rentSelect2Test() {
		$("select").select2();
	}
</script>