package com.foya.dao.mybatis.model;

import java.util.Date;

public class TbPayCashRequirement {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_CASH_REQUIREMENT.CASH_REQ_NO
	 * @mbggenerated  Wed Jan 28 15:39:01 CST 2015
	 */
	private String CASH_REQ_NO;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_CASH_REQUIREMENT.SUBPOENA_DATE
	 * @mbggenerated  Wed Jan 28 15:39:01 CST 2015
	 */
	private Date SUBPOENA_DATE;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_CASH_REQUIREMENT.SUBPOENA_NBR
	 * @mbggenerated  Wed Jan 28 15:39:01 CST 2015
	 */
	private String SUBPOENA_NBR;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_CASH_REQUIREMENT.ACCOUNT_APPROVAL_DATE
	 * @mbggenerated  Wed Jan 28 15:39:01 CST 2015
	 */
	private Date ACCOUNT_APPROVAL_DATE;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_CASH_REQUIREMENT.ACCOUNT_APPROVAL_USER
	 * @mbggenerated  Wed Jan 28 15:39:01 CST 2015
	 */
	private String ACCOUNT_APPROVAL_USER;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_CASH_REQUIREMENT.REJECT_REASON
	 * @mbggenerated  Wed Jan 28 15:39:01 CST 2015
	 */
	private String REJECT_REASON;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_CASH_REQUIREMENT.REJECT_MEMO
	 * @mbggenerated  Wed Jan 28 15:39:01 CST 2015
	 */
	private String REJECT_MEMO;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_CASH_REQUIREMENT.DOMAIN
	 * @mbggenerated  Wed Jan 28 15:39:01 CST 2015
	 */
	private String DOMAIN;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_CASH_REQUIREMENT.PROCESS_TYPE
	 * @mbggenerated  Wed Jan 28 15:39:01 CST 2015
	 */
	private String PROCESS_TYPE;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_CASH_REQUIREMENT.PAYMENT_PERIOD
	 * @mbggenerated  Wed Jan 28 15:39:01 CST 2015
	 */
	private Byte PAYMENT_PERIOD;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_CASH_REQUIREMENT.APP_USER
	 * @mbggenerated  Wed Jan 28 15:39:01 CST 2015
	 */
	private String APP_USER;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_CASH_REQUIREMENT.APP_DATE
	 * @mbggenerated  Wed Jan 28 15:39:01 CST 2015
	 */
	private Date APP_DATE;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_CASH_REQUIREMENT.YEAR_MONTH
	 * @mbggenerated  Wed Jan 28 15:39:01 CST 2015
	 */
	private String YEAR_MONTH;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_CASH_REQUIREMENT.STATUS
	 * @mbggenerated  Wed Jan 28 15:39:01 CST 2015
	 */
	private String STATUS;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_CASH_REQUIREMENT.TO_ERP_DATE
	 * @mbggenerated  Wed Jan 28 15:39:01 CST 2015
	 */
	private Date TO_ERP_DATE;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_CASH_REQUIREMENT.CR_USER
	 * @mbggenerated  Wed Jan 28 15:39:01 CST 2015
	 */
	private String CR_USER;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_CASH_REQUIREMENT.CR_TIME
	 * @mbggenerated  Wed Jan 28 15:39:01 CST 2015
	 */
	private Date CR_TIME;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_CASH_REQUIREMENT.MD_USER
	 * @mbggenerated  Wed Jan 28 15:39:01 CST 2015
	 */
	private String MD_USER;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_CASH_REQUIREMENT.MD_TIME
	 * @mbggenerated  Wed Jan 28 15:39:01 CST 2015
	 */
	private Date MD_TIME;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_CASH_REQUIREMENT.CASH_REQ_NO
	 * @return  the value of TB_PAY_CASH_REQUIREMENT.CASH_REQ_NO
	 * @mbggenerated  Wed Jan 28 15:39:01 CST 2015
	 */
	public String getCASH_REQ_NO() {
		return CASH_REQ_NO;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_CASH_REQUIREMENT.CASH_REQ_NO
	 * @param CASH_REQ_NO  the value for TB_PAY_CASH_REQUIREMENT.CASH_REQ_NO
	 * @mbggenerated  Wed Jan 28 15:39:01 CST 2015
	 */
	public void setCASH_REQ_NO(String CASH_REQ_NO) {
		this.CASH_REQ_NO = CASH_REQ_NO == null ? null : CASH_REQ_NO.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_CASH_REQUIREMENT.SUBPOENA_DATE
	 * @return  the value of TB_PAY_CASH_REQUIREMENT.SUBPOENA_DATE
	 * @mbggenerated  Wed Jan 28 15:39:01 CST 2015
	 */
	public Date getSUBPOENA_DATE() {
		return SUBPOENA_DATE;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_CASH_REQUIREMENT.SUBPOENA_DATE
	 * @param SUBPOENA_DATE  the value for TB_PAY_CASH_REQUIREMENT.SUBPOENA_DATE
	 * @mbggenerated  Wed Jan 28 15:39:01 CST 2015
	 */
	public void setSUBPOENA_DATE(Date SUBPOENA_DATE) {
		this.SUBPOENA_DATE = SUBPOENA_DATE;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_CASH_REQUIREMENT.SUBPOENA_NBR
	 * @return  the value of TB_PAY_CASH_REQUIREMENT.SUBPOENA_NBR
	 * @mbggenerated  Wed Jan 28 15:39:01 CST 2015
	 */
	public String getSUBPOENA_NBR() {
		return SUBPOENA_NBR;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_CASH_REQUIREMENT.SUBPOENA_NBR
	 * @param SUBPOENA_NBR  the value for TB_PAY_CASH_REQUIREMENT.SUBPOENA_NBR
	 * @mbggenerated  Wed Jan 28 15:39:01 CST 2015
	 */
	public void setSUBPOENA_NBR(String SUBPOENA_NBR) {
		this.SUBPOENA_NBR = SUBPOENA_NBR == null ? null : SUBPOENA_NBR.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_CASH_REQUIREMENT.ACCOUNT_APPROVAL_DATE
	 * @return  the value of TB_PAY_CASH_REQUIREMENT.ACCOUNT_APPROVAL_DATE
	 * @mbggenerated  Wed Jan 28 15:39:01 CST 2015
	 */
	public Date getACCOUNT_APPROVAL_DATE() {
		return ACCOUNT_APPROVAL_DATE;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_CASH_REQUIREMENT.ACCOUNT_APPROVAL_DATE
	 * @param ACCOUNT_APPROVAL_DATE  the value for TB_PAY_CASH_REQUIREMENT.ACCOUNT_APPROVAL_DATE
	 * @mbggenerated  Wed Jan 28 15:39:01 CST 2015
	 */
	public void setACCOUNT_APPROVAL_DATE(Date ACCOUNT_APPROVAL_DATE) {
		this.ACCOUNT_APPROVAL_DATE = ACCOUNT_APPROVAL_DATE;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_CASH_REQUIREMENT.ACCOUNT_APPROVAL_USER
	 * @return  the value of TB_PAY_CASH_REQUIREMENT.ACCOUNT_APPROVAL_USER
	 * @mbggenerated  Wed Jan 28 15:39:01 CST 2015
	 */
	public String getACCOUNT_APPROVAL_USER() {
		return ACCOUNT_APPROVAL_USER;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_CASH_REQUIREMENT.ACCOUNT_APPROVAL_USER
	 * @param ACCOUNT_APPROVAL_USER  the value for TB_PAY_CASH_REQUIREMENT.ACCOUNT_APPROVAL_USER
	 * @mbggenerated  Wed Jan 28 15:39:01 CST 2015
	 */
	public void setACCOUNT_APPROVAL_USER(String ACCOUNT_APPROVAL_USER) {
		this.ACCOUNT_APPROVAL_USER = ACCOUNT_APPROVAL_USER == null ? null
				: ACCOUNT_APPROVAL_USER.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_CASH_REQUIREMENT.REJECT_REASON
	 * @return  the value of TB_PAY_CASH_REQUIREMENT.REJECT_REASON
	 * @mbggenerated  Wed Jan 28 15:39:01 CST 2015
	 */
	public String getREJECT_REASON() {
		return REJECT_REASON;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_CASH_REQUIREMENT.REJECT_REASON
	 * @param REJECT_REASON  the value for TB_PAY_CASH_REQUIREMENT.REJECT_REASON
	 * @mbggenerated  Wed Jan 28 15:39:01 CST 2015
	 */
	public void setREJECT_REASON(String REJECT_REASON) {
		this.REJECT_REASON = REJECT_REASON == null ? null : REJECT_REASON
				.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_CASH_REQUIREMENT.REJECT_MEMO
	 * @return  the value of TB_PAY_CASH_REQUIREMENT.REJECT_MEMO
	 * @mbggenerated  Wed Jan 28 15:39:01 CST 2015
	 */
	public String getREJECT_MEMO() {
		return REJECT_MEMO;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_CASH_REQUIREMENT.REJECT_MEMO
	 * @param REJECT_MEMO  the value for TB_PAY_CASH_REQUIREMENT.REJECT_MEMO
	 * @mbggenerated  Wed Jan 28 15:39:01 CST 2015
	 */
	public void setREJECT_MEMO(String REJECT_MEMO) {
		this.REJECT_MEMO = REJECT_MEMO == null ? null : REJECT_MEMO.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_CASH_REQUIREMENT.DOMAIN
	 * @return  the value of TB_PAY_CASH_REQUIREMENT.DOMAIN
	 * @mbggenerated  Wed Jan 28 15:39:01 CST 2015
	 */
	public String getDOMAIN() {
		return DOMAIN;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_CASH_REQUIREMENT.DOMAIN
	 * @param DOMAIN  the value for TB_PAY_CASH_REQUIREMENT.DOMAIN
	 * @mbggenerated  Wed Jan 28 15:39:01 CST 2015
	 */
	public void setDOMAIN(String DOMAIN) {
		this.DOMAIN = DOMAIN == null ? null : DOMAIN.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_CASH_REQUIREMENT.PROCESS_TYPE
	 * @return  the value of TB_PAY_CASH_REQUIREMENT.PROCESS_TYPE
	 * @mbggenerated  Wed Jan 28 15:39:01 CST 2015
	 */
	public String getPROCESS_TYPE() {
		return PROCESS_TYPE;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_CASH_REQUIREMENT.PROCESS_TYPE
	 * @param PROCESS_TYPE  the value for TB_PAY_CASH_REQUIREMENT.PROCESS_TYPE
	 * @mbggenerated  Wed Jan 28 15:39:01 CST 2015
	 */
	public void setPROCESS_TYPE(String PROCESS_TYPE) {
		this.PROCESS_TYPE = PROCESS_TYPE == null ? null : PROCESS_TYPE.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_CASH_REQUIREMENT.PAYMENT_PERIOD
	 * @return  the value of TB_PAY_CASH_REQUIREMENT.PAYMENT_PERIOD
	 * @mbggenerated  Wed Jan 28 15:39:01 CST 2015
	 */
	public Byte getPAYMENT_PERIOD() {
		return PAYMENT_PERIOD;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_CASH_REQUIREMENT.PAYMENT_PERIOD
	 * @param PAYMENT_PERIOD  the value for TB_PAY_CASH_REQUIREMENT.PAYMENT_PERIOD
	 * @mbggenerated  Wed Jan 28 15:39:01 CST 2015
	 */
	public void setPAYMENT_PERIOD(Byte PAYMENT_PERIOD) {
		this.PAYMENT_PERIOD = PAYMENT_PERIOD;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_CASH_REQUIREMENT.APP_USER
	 * @return  the value of TB_PAY_CASH_REQUIREMENT.APP_USER
	 * @mbggenerated  Wed Jan 28 15:39:01 CST 2015
	 */
	public String getAPP_USER() {
		return APP_USER;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_CASH_REQUIREMENT.APP_USER
	 * @param APP_USER  the value for TB_PAY_CASH_REQUIREMENT.APP_USER
	 * @mbggenerated  Wed Jan 28 15:39:01 CST 2015
	 */
	public void setAPP_USER(String APP_USER) {
		this.APP_USER = APP_USER == null ? null : APP_USER.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_CASH_REQUIREMENT.APP_DATE
	 * @return  the value of TB_PAY_CASH_REQUIREMENT.APP_DATE
	 * @mbggenerated  Wed Jan 28 15:39:01 CST 2015
	 */
	public Date getAPP_DATE() {
		return APP_DATE;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_CASH_REQUIREMENT.APP_DATE
	 * @param APP_DATE  the value for TB_PAY_CASH_REQUIREMENT.APP_DATE
	 * @mbggenerated  Wed Jan 28 15:39:01 CST 2015
	 */
	public void setAPP_DATE(Date APP_DATE) {
		this.APP_DATE = APP_DATE;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_CASH_REQUIREMENT.YEAR_MONTH
	 * @return  the value of TB_PAY_CASH_REQUIREMENT.YEAR_MONTH
	 * @mbggenerated  Wed Jan 28 15:39:01 CST 2015
	 */
	public String getYEAR_MONTH() {
		return YEAR_MONTH;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_CASH_REQUIREMENT.YEAR_MONTH
	 * @param YEAR_MONTH  the value for TB_PAY_CASH_REQUIREMENT.YEAR_MONTH
	 * @mbggenerated  Wed Jan 28 15:39:01 CST 2015
	 */
	public void setYEAR_MONTH(String YEAR_MONTH) {
		this.YEAR_MONTH = YEAR_MONTH == null ? null : YEAR_MONTH.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_CASH_REQUIREMENT.STATUS
	 * @return  the value of TB_PAY_CASH_REQUIREMENT.STATUS
	 * @mbggenerated  Wed Jan 28 15:39:01 CST 2015
	 */
	public String getSTATUS() {
		return STATUS;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_CASH_REQUIREMENT.STATUS
	 * @param STATUS  the value for TB_PAY_CASH_REQUIREMENT.STATUS
	 * @mbggenerated  Wed Jan 28 15:39:01 CST 2015
	 */
	public void setSTATUS(String STATUS) {
		this.STATUS = STATUS == null ? null : STATUS.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_CASH_REQUIREMENT.TO_ERP_DATE
	 * @return  the value of TB_PAY_CASH_REQUIREMENT.TO_ERP_DATE
	 * @mbggenerated  Wed Jan 28 15:39:01 CST 2015
	 */
	public Date getTO_ERP_DATE() {
		return TO_ERP_DATE;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_CASH_REQUIREMENT.TO_ERP_DATE
	 * @param TO_ERP_DATE  the value for TB_PAY_CASH_REQUIREMENT.TO_ERP_DATE
	 * @mbggenerated  Wed Jan 28 15:39:01 CST 2015
	 */
	public void setTO_ERP_DATE(Date TO_ERP_DATE) {
		this.TO_ERP_DATE = TO_ERP_DATE;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_CASH_REQUIREMENT.CR_USER
	 * @return  the value of TB_PAY_CASH_REQUIREMENT.CR_USER
	 * @mbggenerated  Wed Jan 28 15:39:01 CST 2015
	 */
	public String getCR_USER() {
		return CR_USER;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_CASH_REQUIREMENT.CR_USER
	 * @param CR_USER  the value for TB_PAY_CASH_REQUIREMENT.CR_USER
	 * @mbggenerated  Wed Jan 28 15:39:01 CST 2015
	 */
	public void setCR_USER(String CR_USER) {
		this.CR_USER = CR_USER == null ? null : CR_USER.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_CASH_REQUIREMENT.CR_TIME
	 * @return  the value of TB_PAY_CASH_REQUIREMENT.CR_TIME
	 * @mbggenerated  Wed Jan 28 15:39:01 CST 2015
	 */
	public Date getCR_TIME() {
		return CR_TIME;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_CASH_REQUIREMENT.CR_TIME
	 * @param CR_TIME  the value for TB_PAY_CASH_REQUIREMENT.CR_TIME
	 * @mbggenerated  Wed Jan 28 15:39:01 CST 2015
	 */
	public void setCR_TIME(Date CR_TIME) {
		this.CR_TIME = CR_TIME;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_CASH_REQUIREMENT.MD_USER
	 * @return  the value of TB_PAY_CASH_REQUIREMENT.MD_USER
	 * @mbggenerated  Wed Jan 28 15:39:01 CST 2015
	 */
	public String getMD_USER() {
		return MD_USER;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_CASH_REQUIREMENT.MD_USER
	 * @param MD_USER  the value for TB_PAY_CASH_REQUIREMENT.MD_USER
	 * @mbggenerated  Wed Jan 28 15:39:01 CST 2015
	 */
	public void setMD_USER(String MD_USER) {
		this.MD_USER = MD_USER == null ? null : MD_USER.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_CASH_REQUIREMENT.MD_TIME
	 * @return  the value of TB_PAY_CASH_REQUIREMENT.MD_TIME
	 * @mbggenerated  Wed Jan 28 15:39:01 CST 2015
	 */
	public Date getMD_TIME() {
		return MD_TIME;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_CASH_REQUIREMENT.MD_TIME
	 * @param MD_TIME  the value for TB_PAY_CASH_REQUIREMENT.MD_TIME
	 * @mbggenerated  Wed Jan 28 15:39:01 CST 2015
	 */
	public void setMD_TIME(Date MD_TIME) {
		this.MD_TIME = MD_TIME;
	}
}