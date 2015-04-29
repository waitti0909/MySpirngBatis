/**
 * @author Arvin.Tsai
 */
package com.foya.noms.dto.pay;

import com.foya.dao.mybatis.model.TbPayCashRequirement;

public class TbPayCashRequirementCompleteDTO extends TbPayCashRequirement {

	// -------------------------------------------------------------------
	// ※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※
	// private Type [table][column][action]
	/********************************************************************
	 * TB_PAY_CASH_REQUIREMENT [請款單] : payBills <BR>
	 * TB_PAY_PAYMENT_REQUEST [請款資料] : payBillsData <BR>
	 * TB_PAY_PAYMENT_REQUEST_VOUCHER [請款憑證] : voucher <BR>
	 * TB_PAY_PAYMENT [付款資料] : payData <BR>
	 *********************************************************************/
	// ※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※
	// -------------------------------------------------------------------

	/**
	 * Count(payBillsData)
	 */
	private Integer payBillsDataSize;

	/**
	 * Count(voucher)
	 */
	private Integer voucherSize;

	/**
	 * Sum(payData.TAX_EXCLUSIVE_TOTAL_AMT)
	 */
	private Integer payDataTotalAmtSum;

	/**
	 * Sum(payData.TOTAL_BUSINESS_TAX)
	 */
	private Integer payDataBusinessTaxSum;

	/**
	 * Sum(payData.TOTAL_INCOME_TAX)
	 */
	private Integer payDataIncomeTaxSum;

	/**
	 * Sum(payData.TOTAL_NHI_AMT)
	 */
	private Integer payDataNhiAmtSUm;

	/**
	 * Sum((payData.TAX_EXCLUSIVE_TOTAL_AMT+payData.TOTAL_BUSINESS_TAX) <BR>
	 * -(payData.TOTAL_INCOME_TAX+payData.TOTAL_NHI_AMT)) <BR>
	 * 應付帳款總額
	 */
	private Integer payable;

	/**
	 * payBills.APP_USER <BR>
	 * 申請人員姓名
	 */
	private String payBillsUserName;

	/**
	 * payBills.ACCOUNT_APPROVAL_USER <BR>
	 * 審核人員姓名
	 */
	private String payBillsCheckUserName;

	/******************************************************************************/

	public Integer getPayBillsDataSize() {
		return payBillsDataSize;
	}

	public void setPayBillsDataSize(Integer payBillsDataSize) {
		this.payBillsDataSize = payBillsDataSize;
	}

	public Integer getVoucherSize() {
		return voucherSize;
	}

	public void setVoucherSize(Integer voucherSize) {
		this.voucherSize = voucherSize;
	}

	public Integer getPayDataTotalAmtSum() {
		return payDataTotalAmtSum;
	}

	public void setPayDataTotalAmtSum(Integer payDataTotalAmtSum) {
		this.payDataTotalAmtSum = payDataTotalAmtSum;
	}

	public Integer getPayDataBusinessTaxSum() {
		return payDataBusinessTaxSum;
	}

	public void setPayDataBusinessTaxSum(Integer payDataBusinessTaxSum) {
		this.payDataBusinessTaxSum = payDataBusinessTaxSum;
	}

	public Integer getPayDataIncomeTaxSum() {
		return payDataIncomeTaxSum;
	}

	public void setPayDataIncomeTaxSum(Integer payDataIncomeTaxSum) {
		this.payDataIncomeTaxSum = payDataIncomeTaxSum;
	}

	public Integer getPayDataNhiAmtSUm() {
		return payDataNhiAmtSUm;
	}

	public void setPayDataNhiAmtSUm(Integer payDataNhiAmtSUm) {
		this.payDataNhiAmtSUm = payDataNhiAmtSUm;
	}

	public Integer getPayable() {
		return payable;
	}

	public void setPayable(Integer payable) {
		this.payable = payable;
	}

	public String getPayBillsUserName() {
		return payBillsUserName;
	}

	public void setPayBillsUserName(String payBillsUserName) {
		this.payBillsUserName = payBillsUserName;
	}

	public String getPayBillsCheckUserName() {
		return payBillsCheckUserName;
	}

	public void setPayBillsCheckUserName(String payBillsCheckUserName) {
		this.payBillsCheckUserName = payBillsCheckUserName;
	}

}
