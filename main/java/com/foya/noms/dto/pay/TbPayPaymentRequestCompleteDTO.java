/**
 * @author Arvin.Tsai
 */
package com.foya.noms.dto.pay;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.foya.dao.mybatis.model.TbPayPaymentRequest;
import com.foya.noms.enums.PayEnumCommon;

public class TbPayPaymentRequestCompleteDTO extends TbPayPaymentRequest {

	// -------------------------------------------------------------------
	// ※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※
	// private Type [table][column][action]
	/********************************************************************
	 * TB_PAY_CASH_REQUIREMENT [請款單] : payBills<BR>
	 * TB_LS_LOCATION [合約站點檔] : location<BR>
	 * TB_LS_MAIN [合約主檔] : lsMain<BR>
	 *********************************************************************/
	// ※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※
	// -------------------------------------------------------------------

	/**
	 * 
	 */
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	// -------------------------------------------------------------------
	/**
	 * payBills.PROCESS_TYPE
	 */
	private String payBillsProcessType;
	
	/**
	 * payBills.APP_DATE
	 * 申請日
	 */
	private Date payBillsAppDate;
	
	/**
	 * 租約名稱
	 */
	private String lsMainName;

	/**
	 * SUM(PLDG_AMT)<BR>
	 * 押金
	 */
	private Integer locationPldgAmtSum;

	/**
	 * SUM(RENT_AMT)<BR>
	 * 租金
	 */
	private Integer locationRentAmtSum;

	/**
	 * 起帳日
	 */
	private Date lsMainDate;

	/**
	 * sum(TAX_EXCLUSIVE_AMT+BUSINESS_TAX)<BR>
	 * 請款金額
	 */
	private Integer payBillsTotal;
	
	/*****************************************************************************************/
	/**
	 * 取得處裡類別名稱
	 */
	public String getProcessTypeName() {

		return PayEnumCommon.ProcessType.toSource(getPayBillsProcessType()).getValue();
	}

	/**
	 * 取得起帳日
	 */
	public String getLsMainDateFormat() {

		return lsMainDate == null ? "" : sdf.format(lsMainDate);
	}

	/**
	 * 取得起始日
	 */
	public String getBegindDateFormat() {

		return super.getPAYMENT_REQ_BEGIN_DATE() == null ? "" : sdf.format(super.getPAYMENT_REQ_BEGIN_DATE());
	}

	/**
	 * 取得起訖日
	 */
	public String getEndDateFormat() {

		return super.getPAYMENT_REQ_END_DATE() == null ? "" : sdf.format(super.getPAYMENT_REQ_END_DATE());
	}

	/*****************************************************************************************/

	public String getLsMainName() {
		return lsMainName;
	}

	public void setLsMainName(String lsMainName) {
		this.lsMainName = lsMainName;
	}

	public Integer getLocationPldgAmtSum() {
		return locationPldgAmtSum;
	}

	public void setLocationPldgAmtSum(Integer locationPldgAmtSum) {
		this.locationPldgAmtSum = locationPldgAmtSum;
	}

	public Integer getLocationRentAmtSum() {
		return locationRentAmtSum;
	}

	public void setLocationRentAmtSum(Integer locationRentAmtSum) {
		this.locationRentAmtSum = locationRentAmtSum;
	}

	public Date getLsMainDate() {
		return lsMainDate;
	}

	public void setLsMainDate(Date lsMainDate) {
		this.lsMainDate = lsMainDate;
	}

	public Integer getPayBillsTotal() {
		return payBillsTotal;
	}

	public void setPayBillsTotal(Integer payBillsTotal) {
		this.payBillsTotal = payBillsTotal;
	}

	public String getPayBillsProcessType() {
		return payBillsProcessType;
	}

	public void setPayBillsProcessType(String payBillsProcessType) {
		this.payBillsProcessType = payBillsProcessType;
	}

	public Date getPayBillsAppDate() {
		return payBillsAppDate;
	}

	public void setPayBillsAppDate(Date payBillsAppDate) {
		this.payBillsAppDate = payBillsAppDate;
	}
	
}
