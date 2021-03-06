package com.foya.dao.mybatis.model;

import java.math.BigDecimal;
import java.util.Date;

public class TbPayPaymentRequestVoucher {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_PAYMENT_REQUEST_VOUCHER.SEQ_NBR
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
	 */
	private Long SEQ_NBR;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_PAYMENT_REQUEST_VOUCHER.PROCESS_TYPE
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
	 */
	private String PROCESS_TYPE;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_PAYMENT_REQUEST_VOUCHER.VOUCHER_NO
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
	 */
	private String VOUCHER_NO;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_PAYMENT_REQUEST_VOUCHER.VOUCHER_TYPE
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
	 */
	private String VOUCHER_TYPE;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_PAYMENT_REQUEST_VOUCHER.VOUCHER_NBR
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
	 */
	private String VOUCHER_NBR;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_PAYMENT_REQUEST_VOUCHER.VOUCHER_DATE
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
	 */
	private Date VOUCHER_DATE;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_PAYMENT_REQUEST_VOUCHER.VAT_NUMBER
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
	 */
	private String VAT_NUMBER;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_PAYMENT_REQUEST_VOUCHER.TAX_EXCLUSIVE_AMT
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
	 */
	private BigDecimal TAX_EXCLUSIVE_AMT;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_PAYMENT_REQUEST_VOUCHER.BUSINESS_TAX
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
	 */
	private BigDecimal BUSINESS_TAX;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_PAYMENT_REQUEST_VOUCHER.CREDIT_MADE_AMT
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
	 */
	private BigDecimal CREDIT_MADE_AMT;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_PAYMENT_REQUEST_VOUCHER.CREDIT_MADE_TAX
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
	 */
	private BigDecimal CREDIT_MADE_TAX;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_PAYMENT_REQUEST_VOUCHER.CR_USER
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
	 */
	private String CR_USER;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_PAYMENT_REQUEST_VOUCHER.CR_TIME
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
	 */
	private Date CR_TIME;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_PAYMENT_REQUEST_VOUCHER.MD_USER
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
	 */
	private String MD_USER;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_PAYMENT_REQUEST_VOUCHER.MD_TIME
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
	 */
	private Date MD_TIME;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_PAYMENT_REQUEST_VOUCHER.SEQ_NBR
	 * @return  the value of TB_PAY_PAYMENT_REQUEST_VOUCHER.SEQ_NBR
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
	 */
	public Long getSEQ_NBR() {
		return SEQ_NBR;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_PAYMENT_REQUEST_VOUCHER.SEQ_NBR
	 * @param SEQ_NBR  the value for TB_PAY_PAYMENT_REQUEST_VOUCHER.SEQ_NBR
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
	 */
	public void setSEQ_NBR(Long SEQ_NBR) {
		this.SEQ_NBR = SEQ_NBR;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_PAYMENT_REQUEST_VOUCHER.PROCESS_TYPE
	 * @return  the value of TB_PAY_PAYMENT_REQUEST_VOUCHER.PROCESS_TYPE
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
	 */
	public String getPROCESS_TYPE() {
		return PROCESS_TYPE;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_PAYMENT_REQUEST_VOUCHER.PROCESS_TYPE
	 * @param PROCESS_TYPE  the value for TB_PAY_PAYMENT_REQUEST_VOUCHER.PROCESS_TYPE
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
	 */
	public void setPROCESS_TYPE(String PROCESS_TYPE) {
		this.PROCESS_TYPE = PROCESS_TYPE == null ? null : PROCESS_TYPE.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_PAYMENT_REQUEST_VOUCHER.VOUCHER_NO
	 * @return  the value of TB_PAY_PAYMENT_REQUEST_VOUCHER.VOUCHER_NO
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
	 */
	public String getVOUCHER_NO() {
		return VOUCHER_NO;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_PAYMENT_REQUEST_VOUCHER.VOUCHER_NO
	 * @param VOUCHER_NO  the value for TB_PAY_PAYMENT_REQUEST_VOUCHER.VOUCHER_NO
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
	 */
	public void setVOUCHER_NO(String VOUCHER_NO) {
		this.VOUCHER_NO = VOUCHER_NO == null ? null : VOUCHER_NO.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_PAYMENT_REQUEST_VOUCHER.VOUCHER_TYPE
	 * @return  the value of TB_PAY_PAYMENT_REQUEST_VOUCHER.VOUCHER_TYPE
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
	 */
	public String getVOUCHER_TYPE() {
		return VOUCHER_TYPE;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_PAYMENT_REQUEST_VOUCHER.VOUCHER_TYPE
	 * @param VOUCHER_TYPE  the value for TB_PAY_PAYMENT_REQUEST_VOUCHER.VOUCHER_TYPE
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
	 */
	public void setVOUCHER_TYPE(String VOUCHER_TYPE) {
		this.VOUCHER_TYPE = VOUCHER_TYPE == null ? null : VOUCHER_TYPE.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_PAYMENT_REQUEST_VOUCHER.VOUCHER_NBR
	 * @return  the value of TB_PAY_PAYMENT_REQUEST_VOUCHER.VOUCHER_NBR
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
	 */
	public String getVOUCHER_NBR() {
		return VOUCHER_NBR;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_PAYMENT_REQUEST_VOUCHER.VOUCHER_NBR
	 * @param VOUCHER_NBR  the value for TB_PAY_PAYMENT_REQUEST_VOUCHER.VOUCHER_NBR
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
	 */
	public void setVOUCHER_NBR(String VOUCHER_NBR) {
		this.VOUCHER_NBR = VOUCHER_NBR == null ? null : VOUCHER_NBR.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_PAYMENT_REQUEST_VOUCHER.VOUCHER_DATE
	 * @return  the value of TB_PAY_PAYMENT_REQUEST_VOUCHER.VOUCHER_DATE
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
	 */
	public Date getVOUCHER_DATE() {
		return VOUCHER_DATE;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_PAYMENT_REQUEST_VOUCHER.VOUCHER_DATE
	 * @param VOUCHER_DATE  the value for TB_PAY_PAYMENT_REQUEST_VOUCHER.VOUCHER_DATE
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
	 */
	public void setVOUCHER_DATE(Date VOUCHER_DATE) {
		this.VOUCHER_DATE = VOUCHER_DATE;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_PAYMENT_REQUEST_VOUCHER.VAT_NUMBER
	 * @return  the value of TB_PAY_PAYMENT_REQUEST_VOUCHER.VAT_NUMBER
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
	 */
	public String getVAT_NUMBER() {
		return VAT_NUMBER;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_PAYMENT_REQUEST_VOUCHER.VAT_NUMBER
	 * @param VAT_NUMBER  the value for TB_PAY_PAYMENT_REQUEST_VOUCHER.VAT_NUMBER
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
	 */
	public void setVAT_NUMBER(String VAT_NUMBER) {
		this.VAT_NUMBER = VAT_NUMBER == null ? null : VAT_NUMBER.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_PAYMENT_REQUEST_VOUCHER.TAX_EXCLUSIVE_AMT
	 * @return  the value of TB_PAY_PAYMENT_REQUEST_VOUCHER.TAX_EXCLUSIVE_AMT
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
	 */
	public BigDecimal getTAX_EXCLUSIVE_AMT() {
		return TAX_EXCLUSIVE_AMT;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_PAYMENT_REQUEST_VOUCHER.TAX_EXCLUSIVE_AMT
	 * @param TAX_EXCLUSIVE_AMT  the value for TB_PAY_PAYMENT_REQUEST_VOUCHER.TAX_EXCLUSIVE_AMT
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
	 */
	public void setTAX_EXCLUSIVE_AMT(BigDecimal TAX_EXCLUSIVE_AMT) {
		this.TAX_EXCLUSIVE_AMT = TAX_EXCLUSIVE_AMT;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_PAYMENT_REQUEST_VOUCHER.BUSINESS_TAX
	 * @return  the value of TB_PAY_PAYMENT_REQUEST_VOUCHER.BUSINESS_TAX
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
	 */
	public BigDecimal getBUSINESS_TAX() {
		return BUSINESS_TAX;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_PAYMENT_REQUEST_VOUCHER.BUSINESS_TAX
	 * @param BUSINESS_TAX  the value for TB_PAY_PAYMENT_REQUEST_VOUCHER.BUSINESS_TAX
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
	 */
	public void setBUSINESS_TAX(BigDecimal BUSINESS_TAX) {
		this.BUSINESS_TAX = BUSINESS_TAX;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_PAYMENT_REQUEST_VOUCHER.CREDIT_MADE_AMT
	 * @return  the value of TB_PAY_PAYMENT_REQUEST_VOUCHER.CREDIT_MADE_AMT
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
	 */
	public BigDecimal getCREDIT_MADE_AMT() {
		return CREDIT_MADE_AMT;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_PAYMENT_REQUEST_VOUCHER.CREDIT_MADE_AMT
	 * @param CREDIT_MADE_AMT  the value for TB_PAY_PAYMENT_REQUEST_VOUCHER.CREDIT_MADE_AMT
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
	 */
	public void setCREDIT_MADE_AMT(BigDecimal CREDIT_MADE_AMT) {
		this.CREDIT_MADE_AMT = CREDIT_MADE_AMT;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_PAYMENT_REQUEST_VOUCHER.CREDIT_MADE_TAX
	 * @return  the value of TB_PAY_PAYMENT_REQUEST_VOUCHER.CREDIT_MADE_TAX
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
	 */
	public BigDecimal getCREDIT_MADE_TAX() {
		return CREDIT_MADE_TAX;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_PAYMENT_REQUEST_VOUCHER.CREDIT_MADE_TAX
	 * @param CREDIT_MADE_TAX  the value for TB_PAY_PAYMENT_REQUEST_VOUCHER.CREDIT_MADE_TAX
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
	 */
	public void setCREDIT_MADE_TAX(BigDecimal CREDIT_MADE_TAX) {
		this.CREDIT_MADE_TAX = CREDIT_MADE_TAX;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_PAYMENT_REQUEST_VOUCHER.CR_USER
	 * @return  the value of TB_PAY_PAYMENT_REQUEST_VOUCHER.CR_USER
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
	 */
	public String getCR_USER() {
		return CR_USER;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_PAYMENT_REQUEST_VOUCHER.CR_USER
	 * @param CR_USER  the value for TB_PAY_PAYMENT_REQUEST_VOUCHER.CR_USER
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
	 */
	public void setCR_USER(String CR_USER) {
		this.CR_USER = CR_USER == null ? null : CR_USER.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_PAYMENT_REQUEST_VOUCHER.CR_TIME
	 * @return  the value of TB_PAY_PAYMENT_REQUEST_VOUCHER.CR_TIME
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
	 */
	public Date getCR_TIME() {
		return CR_TIME;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_PAYMENT_REQUEST_VOUCHER.CR_TIME
	 * @param CR_TIME  the value for TB_PAY_PAYMENT_REQUEST_VOUCHER.CR_TIME
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
	 */
	public void setCR_TIME(Date CR_TIME) {
		this.CR_TIME = CR_TIME;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_PAYMENT_REQUEST_VOUCHER.MD_USER
	 * @return  the value of TB_PAY_PAYMENT_REQUEST_VOUCHER.MD_USER
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
	 */
	public String getMD_USER() {
		return MD_USER;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_PAYMENT_REQUEST_VOUCHER.MD_USER
	 * @param MD_USER  the value for TB_PAY_PAYMENT_REQUEST_VOUCHER.MD_USER
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
	 */
	public void setMD_USER(String MD_USER) {
		this.MD_USER = MD_USER == null ? null : MD_USER.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_PAYMENT_REQUEST_VOUCHER.MD_TIME
	 * @return  the value of TB_PAY_PAYMENT_REQUEST_VOUCHER.MD_TIME
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
	 */
	public Date getMD_TIME() {
		return MD_TIME;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_PAYMENT_REQUEST_VOUCHER.MD_TIME
	 * @param MD_TIME  the value for TB_PAY_PAYMENT_REQUEST_VOUCHER.MD_TIME
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
	 */
	public void setMD_TIME(Date MD_TIME) {
		this.MD_TIME = MD_TIME;
	}
}