package com.foya.dao.mybatis.model;

import java.util.Date;
import java.math.BigDecimal;

public class TbPayCheckDisregard extends TbPayCheckDisregardKey {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_CHECK_DISREGARD.PAYMENT_SEQ_NBR
	 * @mbggenerated  Thu Jan 22 17:14:38 CST 2015
	 */
	private Long PAYMENT_SEQ_NBR;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_CHECK_DISREGARD.PAYMENT_REQ_NO
	 * @mbggenerated  Thu Jan 22 17:14:38 CST 2015
	 */
	private Long PAYMENT_REQ_NO;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_CHECK_DISREGARD.PAYMENT_REQ_ITEM_NO
	 * @mbggenerated  Thu Jan 22 17:14:38 CST 2015
	 */
	private Short PAYMENT_REQ_ITEM_NO;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_CHECK_DISREGARD.APP_DATE
	 * @mbggenerated  Thu Jan 22 17:14:38 CST 2015
	 */
	private Date APP_DATE;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_CHECK_DISREGARD.APP_USER
	 * @mbggenerated  Thu Jan 22 17:14:38 CST 2015
	 */
	private String APP_USER;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_CHECK_DISREGARD.STATUS
	 * @mbggenerated  Thu Jan 22 17:14:38 CST 2015
	 */
	private String STATUS;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_CHECK_DISREGARD.CHECK_DISREGARD_REASON
	 * @mbggenerated  Thu Jan 22 17:14:38 CST 2015
	 */
	private String CHECK_DISREGARD_REASON;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_CHECK_DISREGARD.CHECK_DISREGARD_REASON_DESC
	 * @mbggenerated  Thu Jan 22 17:14:38 CST 2015
	 */
	private String CHECK_DISREGARD_REASON_DESC;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_CHECK_DISREGARD.TO_ERP_DATE
	 * @mbggenerated  Thu Jan 22 17:14:38 CST 2015
	 */
	private Date TO_ERP_DATE;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_CHECK_DISREGARD.CR_USER
	 * @mbggenerated  Thu Jan 22 17:14:38 CST 2015
	 */
	private String CR_USER;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_CHECK_DISREGARD.CR_TIME
	 * @mbggenerated  Thu Jan 22 17:14:38 CST 2015
	 */
	private Date CR_TIME;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_CHECK_DISREGARD.MD_USER
	 * @mbggenerated  Thu Jan 22 17:14:38 CST 2015
	 */
	private String MD_USER;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_CHECK_DISREGARD.MD_TIME
	 * @mbggenerated  Thu Jan 22 17:14:38 CST 2015
	 */
	private Date MD_TIME;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_CHECK_DISREGARD.CHECK_AMT
	 * @mbggenerated  Thu Jan 22 17:14:38 CST 2015
	 */
	private BigDecimal CHECK_AMT;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_CHECK_DISREGARD.CASH_REQ_NO
	 * @mbggenerated  Thu Jan 22 17:14:38 CST 2015
	 */
	private String CASH_REQ_NO;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_CHECK_DISREGARD.DOMAIN
	 * @mbggenerated  Thu Jan 22 17:14:38 CST 2015
	 */
	private String DOMAIN;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_CHECK_DISREGARD.PAYMENT_SEQ_NBR
	 * @return  the value of TB_PAY_CHECK_DISREGARD.PAYMENT_SEQ_NBR
	 * @mbggenerated  Thu Jan 22 17:14:38 CST 2015
	 */
	public Long getPAYMENT_SEQ_NBR() {
		return PAYMENT_SEQ_NBR;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_CHECK_DISREGARD.PAYMENT_SEQ_NBR
	 * @param PAYMENT_SEQ_NBR  the value for TB_PAY_CHECK_DISREGARD.PAYMENT_SEQ_NBR
	 * @mbggenerated  Thu Jan 22 17:14:38 CST 2015
	 */
	public void setPAYMENT_SEQ_NBR(Long PAYMENT_SEQ_NBR) {
		this.PAYMENT_SEQ_NBR = PAYMENT_SEQ_NBR;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_CHECK_DISREGARD.PAYMENT_REQ_NO
	 * @return  the value of TB_PAY_CHECK_DISREGARD.PAYMENT_REQ_NO
	 * @mbggenerated  Thu Jan 22 17:14:38 CST 2015
	 */
	public Long getPAYMENT_REQ_NO() {
		return PAYMENT_REQ_NO;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_CHECK_DISREGARD.PAYMENT_REQ_NO
	 * @param PAYMENT_REQ_NO  the value for TB_PAY_CHECK_DISREGARD.PAYMENT_REQ_NO
	 * @mbggenerated  Thu Jan 22 17:14:38 CST 2015
	 */
	public void setPAYMENT_REQ_NO(Long PAYMENT_REQ_NO) {
		this.PAYMENT_REQ_NO = PAYMENT_REQ_NO;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_CHECK_DISREGARD.PAYMENT_REQ_ITEM_NO
	 * @return  the value of TB_PAY_CHECK_DISREGARD.PAYMENT_REQ_ITEM_NO
	 * @mbggenerated  Thu Jan 22 17:14:38 CST 2015
	 */
	public Short getPAYMENT_REQ_ITEM_NO() {
		return PAYMENT_REQ_ITEM_NO;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_CHECK_DISREGARD.PAYMENT_REQ_ITEM_NO
	 * @param PAYMENT_REQ_ITEM_NO  the value for TB_PAY_CHECK_DISREGARD.PAYMENT_REQ_ITEM_NO
	 * @mbggenerated  Thu Jan 22 17:14:38 CST 2015
	 */
	public void setPAYMENT_REQ_ITEM_NO(Short PAYMENT_REQ_ITEM_NO) {
		this.PAYMENT_REQ_ITEM_NO = PAYMENT_REQ_ITEM_NO;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_CHECK_DISREGARD.APP_DATE
	 * @return  the value of TB_PAY_CHECK_DISREGARD.APP_DATE
	 * @mbggenerated  Thu Jan 22 17:14:38 CST 2015
	 */
	public Date getAPP_DATE() {
		return APP_DATE;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_CHECK_DISREGARD.APP_DATE
	 * @param APP_DATE  the value for TB_PAY_CHECK_DISREGARD.APP_DATE
	 * @mbggenerated  Thu Jan 22 17:14:38 CST 2015
	 */
	public void setAPP_DATE(Date APP_DATE) {
		this.APP_DATE = APP_DATE;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_CHECK_DISREGARD.APP_USER
	 * @return  the value of TB_PAY_CHECK_DISREGARD.APP_USER
	 * @mbggenerated  Thu Jan 22 17:14:38 CST 2015
	 */
	public String getAPP_USER() {
		return APP_USER;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_CHECK_DISREGARD.APP_USER
	 * @param APP_USER  the value for TB_PAY_CHECK_DISREGARD.APP_USER
	 * @mbggenerated  Thu Jan 22 17:14:38 CST 2015
	 */
	public void setAPP_USER(String APP_USER) {
		this.APP_USER = APP_USER == null ? null : APP_USER.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_CHECK_DISREGARD.STATUS
	 * @return  the value of TB_PAY_CHECK_DISREGARD.STATUS
	 * @mbggenerated  Thu Jan 22 17:14:38 CST 2015
	 */
	public String getSTATUS() {
		return STATUS;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_CHECK_DISREGARD.STATUS
	 * @param STATUS  the value for TB_PAY_CHECK_DISREGARD.STATUS
	 * @mbggenerated  Thu Jan 22 17:14:38 CST 2015
	 */
	public void setSTATUS(String STATUS) {
		this.STATUS = STATUS == null ? null : STATUS.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_CHECK_DISREGARD.CHECK_DISREGARD_REASON
	 * @return  the value of TB_PAY_CHECK_DISREGARD.CHECK_DISREGARD_REASON
	 * @mbggenerated  Thu Jan 22 17:14:38 CST 2015
	 */
	public String getCHECK_DISREGARD_REASON() {
		return CHECK_DISREGARD_REASON;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_CHECK_DISREGARD.CHECK_DISREGARD_REASON
	 * @param CHECK_DISREGARD_REASON  the value for TB_PAY_CHECK_DISREGARD.CHECK_DISREGARD_REASON
	 * @mbggenerated  Thu Jan 22 17:14:38 CST 2015
	 */
	public void setCHECK_DISREGARD_REASON(String CHECK_DISREGARD_REASON) {
		this.CHECK_DISREGARD_REASON = CHECK_DISREGARD_REASON == null ? null
				: CHECK_DISREGARD_REASON.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_CHECK_DISREGARD.CHECK_DISREGARD_REASON_DESC
	 * @return  the value of TB_PAY_CHECK_DISREGARD.CHECK_DISREGARD_REASON_DESC
	 * @mbggenerated  Thu Jan 22 17:14:38 CST 2015
	 */
	public String getCHECK_DISREGARD_REASON_DESC() {
		return CHECK_DISREGARD_REASON_DESC;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_CHECK_DISREGARD.CHECK_DISREGARD_REASON_DESC
	 * @param CHECK_DISREGARD_REASON_DESC  the value for TB_PAY_CHECK_DISREGARD.CHECK_DISREGARD_REASON_DESC
	 * @mbggenerated  Thu Jan 22 17:14:38 CST 2015
	 */
	public void setCHECK_DISREGARD_REASON_DESC(
			String CHECK_DISREGARD_REASON_DESC) {
		this.CHECK_DISREGARD_REASON_DESC = CHECK_DISREGARD_REASON_DESC == null ? null
				: CHECK_DISREGARD_REASON_DESC.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_CHECK_DISREGARD.TO_ERP_DATE
	 * @return  the value of TB_PAY_CHECK_DISREGARD.TO_ERP_DATE
	 * @mbggenerated  Thu Jan 22 17:14:38 CST 2015
	 */
	public Date getTO_ERP_DATE() {
		return TO_ERP_DATE;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_CHECK_DISREGARD.TO_ERP_DATE
	 * @param TO_ERP_DATE  the value for TB_PAY_CHECK_DISREGARD.TO_ERP_DATE
	 * @mbggenerated  Thu Jan 22 17:14:38 CST 2015
	 */
	public void setTO_ERP_DATE(Date TO_ERP_DATE) {
		this.TO_ERP_DATE = TO_ERP_DATE;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_CHECK_DISREGARD.CR_USER
	 * @return  the value of TB_PAY_CHECK_DISREGARD.CR_USER
	 * @mbggenerated  Thu Jan 22 17:14:38 CST 2015
	 */
	public String getCR_USER() {
		return CR_USER;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_CHECK_DISREGARD.CR_USER
	 * @param CR_USER  the value for TB_PAY_CHECK_DISREGARD.CR_USER
	 * @mbggenerated  Thu Jan 22 17:14:38 CST 2015
	 */
	public void setCR_USER(String CR_USER) {
		this.CR_USER = CR_USER == null ? null : CR_USER.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_CHECK_DISREGARD.CR_TIME
	 * @return  the value of TB_PAY_CHECK_DISREGARD.CR_TIME
	 * @mbggenerated  Thu Jan 22 17:14:38 CST 2015
	 */
	public Date getCR_TIME() {
		return CR_TIME;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_CHECK_DISREGARD.CR_TIME
	 * @param CR_TIME  the value for TB_PAY_CHECK_DISREGARD.CR_TIME
	 * @mbggenerated  Thu Jan 22 17:14:38 CST 2015
	 */
	public void setCR_TIME(Date CR_TIME) {
		this.CR_TIME = CR_TIME;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_CHECK_DISREGARD.MD_USER
	 * @return  the value of TB_PAY_CHECK_DISREGARD.MD_USER
	 * @mbggenerated  Thu Jan 22 17:14:38 CST 2015
	 */
	public String getMD_USER() {
		return MD_USER;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_CHECK_DISREGARD.MD_USER
	 * @param MD_USER  the value for TB_PAY_CHECK_DISREGARD.MD_USER
	 * @mbggenerated  Thu Jan 22 17:14:38 CST 2015
	 */
	public void setMD_USER(String MD_USER) {
		this.MD_USER = MD_USER == null ? null : MD_USER.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_CHECK_DISREGARD.MD_TIME
	 * @return  the value of TB_PAY_CHECK_DISREGARD.MD_TIME
	 * @mbggenerated  Thu Jan 22 17:14:38 CST 2015
	 */
	public Date getMD_TIME() {
		return MD_TIME;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_CHECK_DISREGARD.MD_TIME
	 * @param MD_TIME  the value for TB_PAY_CHECK_DISREGARD.MD_TIME
	 * @mbggenerated  Thu Jan 22 17:14:38 CST 2015
	 */
	public void setMD_TIME(Date MD_TIME) {
		this.MD_TIME = MD_TIME;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_CHECK_DISREGARD.CHECK_AMT
	 * @return  the value of TB_PAY_CHECK_DISREGARD.CHECK_AMT
	 * @mbggenerated  Thu Jan 22 17:14:38 CST 2015
	 */
	public BigDecimal getCHECK_AMT() {
		return CHECK_AMT;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_CHECK_DISREGARD.CHECK_AMT
	 * @param CHECK_AMT  the value for TB_PAY_CHECK_DISREGARD.CHECK_AMT
	 * @mbggenerated  Thu Jan 22 17:14:38 CST 2015
	 */
	public void setCHECK_AMT(BigDecimal CHECK_AMT) {
		this.CHECK_AMT = CHECK_AMT;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_CHECK_DISREGARD.CASH_REQ_NO
	 * @return  the value of TB_PAY_CHECK_DISREGARD.CASH_REQ_NO
	 * @mbggenerated  Thu Jan 22 17:14:38 CST 2015
	 */
	public String getCASH_REQ_NO() {
		return CASH_REQ_NO;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_CHECK_DISREGARD.CASH_REQ_NO
	 * @param CASH_REQ_NO  the value for TB_PAY_CHECK_DISREGARD.CASH_REQ_NO
	 * @mbggenerated  Thu Jan 22 17:14:38 CST 2015
	 */
	public void setCASH_REQ_NO(String CASH_REQ_NO) {
		this.CASH_REQ_NO = CASH_REQ_NO == null ? null : CASH_REQ_NO.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_CHECK_DISREGARD.DOMAIN
	 * @return  the value of TB_PAY_CHECK_DISREGARD.DOMAIN
	 * @mbggenerated  Thu Jan 22 17:14:38 CST 2015
	 */
	public String getDOMAIN() {
		return DOMAIN;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_CHECK_DISREGARD.DOMAIN
	 * @param DOMAIN  the value for TB_PAY_CHECK_DISREGARD.DOMAIN
	 * @mbggenerated  Thu Jan 22 17:14:38 CST 2015
	 */
	public void setDOMAIN(String DOMAIN) {
		this.DOMAIN = DOMAIN == null ? null : DOMAIN.trim();
	}
}