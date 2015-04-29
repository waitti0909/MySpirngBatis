/**
 * @author Arvin.Tsai
 */
package com.foya.noms.dto.pay;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import com.foya.dao.mybatis.model.TbPayPayment;
import com.foya.noms.enums.PayEnumCommon;

public class TbPayPaymentCompleteDTO extends TbPayPayment {

	// -------------------------------------------------------------------
	// ※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※
	// private Type [table][column][action]
	/********************************************************************
	 * TB_LS_LOC_PAYMENT [站點付款明細檔] : locPayment<BR>
	 *********************************************************************/
	// ※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※
	// -------------------------------------------------------------------
	/**
	 * 
	 */
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 
	 */
	private NumberFormat nf = new DecimalFormat("###,###,###");
	// -------------------------------------------------------------------

	/**
	 * payData.LESSOR_ID<BR>
	 * 付款人證號
	 */
	private String locPaymentUserId;

	/**
	 * payData.LESSOR_NAME<BR>
	 * 付款人姓名
	 */
	private String locPaymentUserName;

	/**
	 * locPayment.PAY_AMT 付款金額
	 */
	private String locPaymentPayAmt;

	/**
	 * locPayment.BUSINESS_TAX 營業稅
	 */
	private String locPaymentBusinessTax;

	/**************************************************************************/

	/**
	 * 取得租金
	 */
	public String getRentFormat() {

		int payAmt = getLocPaymentPayAmt() == null ? 0 : Integer.parseInt(getLocPaymentPayAmt());

		int businessTax = getLocPaymentBusinessTax() == null ? 0 : Integer.parseInt(getLocPaymentBusinessTax());

		return nf.format(payAmt + businessTax);
	}

	/**
	 * 取得付款方式
	 */
	public String getPayMethod() {

		return PayEnumCommon.PaymentStatus.toSource(super.getSTATUS()).getValue();
	}

	/**
	 * 取得本次請款
	 */
	public int getAmountPayable() {

		int totalAmt = super.getTAX_EXCLUSIVE_TOTAL_AMT() == null ? 0 : super.getTAX_EXCLUSIVE_TOTAL_AMT().intValue();

		int businessTax = super.getTOTAL_BUSINESS_TAX() == null ? 0 : super.getTOTAL_BUSINESS_TAX().intValue();

		return totalAmt + businessTax;
	}

	/**
	 * 取得本次請款
	 */
	public String getAmountPayableFormat() {

		return nf.format(getAmountPayable());
	}

	/**
	 * 取得實付金額
	 */
	public int getAmountPaid() {

		int amontPayable = getAmountPayable();

		int incomeTax = super.getTOTAL_INCOME_TAX() == null ? 0 : super.getTOTAL_INCOME_TAX().intValue();

		int nh1Amt = super.getTOTAL_NHI_AMT() == null ? 0 : super.getTOTAL_NHI_AMT().intValue();

		return amontPayable - incomeTax - nh1Amt;
	}

	/**
	 * 取得實付金額
	 */
	public String getAmountPaidFormat() {

		return nf.format(getAmountPaid());
	}

	/**
	 * 健保補充費
	 */
	public String getNhiAmtFormat() {

		return nf.format(super.getTOTAL_NHI_AMT() == null ? 0 : super.getTOTAL_NHI_AMT().intValue());
	}

	/**
	 * 取得所得稅
	 */
	public String getIncomeTaxFormat() {

		return nf.format(super.getTOTAL_INCOME_TAX() == null ? 0 : super.getTOTAL_INCOME_TAX().intValue());
	}

	/**
	 * 取得營業稅
	 */
	public String getTaxOnUurnoverFormat() {

		return nf.format(super.getTOTAL_BUSINESS_TAX() == null ? 0 : super.getTOTAL_BUSINESS_TAX().intValue());
	}

	/**
	 * 取得 未稅金額
	 */
	public String getNoneIncludedTaxFormat() {

		return nf.format(super.getTAX_EXCLUSIVE_TOTAL_AMT() == null ? 0 : super.getTAX_EXCLUSIVE_TOTAL_AMT().intValue());
	}

	/**************************************************************************/

	public String getLocPaymentUserId() {
		return locPaymentUserId;
	}

	public void setLocPaymentUserId(String locPaymentUserId) {
		this.locPaymentUserId = locPaymentUserId;
	}

	public String getLocPaymentUserName() {
		return locPaymentUserName;
	}

	public void setLocPaymentUserName(String locPaymentUserName) {
		this.locPaymentUserName = locPaymentUserName;
	}

	public String getLocPaymentPayAmt() {
		return locPaymentPayAmt;
	}

	public void setLocPaymentPayAmt(String locPaymentPayAmt) {
		this.locPaymentPayAmt = locPaymentPayAmt;
	}

	public String getLocPaymentBusinessTax() {
		return locPaymentBusinessTax;
	}

	public void setLocPaymentBusinessTax(String locPaymentBusinessTax) {
		this.locPaymentBusinessTax = locPaymentBusinessTax;
	}

}
