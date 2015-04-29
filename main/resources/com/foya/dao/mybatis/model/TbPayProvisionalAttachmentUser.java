package com.foya.dao.mybatis.model;

import java.math.BigDecimal;
import java.util.Date;

public class TbPayProvisionalAttachmentUser extends TbPayProvisionalAttachmentUserKey {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_PROVISIONAL_ATTACHMENT_USER.PAYMENT_USER_NAME
	 * @mbggenerated  Fri Mar 13 13:42:19 CST 2015
	 */
	private String PAYMENT_USER_NAME;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_PROVISIONAL_ATTACHMENT_USER.PAYMENT_METHOD
	 * @mbggenerated  Fri Mar 13 13:42:19 CST 2015
	 */
	private String PAYMENT_METHOD;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_PROVISIONAL_ATTACHMENT_USER.BANK_CODE
	 * @mbggenerated  Fri Mar 13 13:42:19 CST 2015
	 */
	private String BANK_CODE;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_PROVISIONAL_ATTACHMENT_USER.BANK_BRANCH_CODE
	 * @mbggenerated  Fri Mar 13 13:42:19 CST 2015
	 */
	private String BANK_BRANCH_CODE;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_PROVISIONAL_ATTACHMENT_USER.ACCOUNT_NBR
	 * @mbggenerated  Fri Mar 13 13:42:19 CST 2015
	 */
	private String ACCOUNT_NBR;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_PROVISIONAL_ATTACHMENT_USER.ATTACH_TAX_INCLUSIVE_AMT
	 * @mbggenerated  Fri Mar 13 13:42:19 CST 2015
	 */
	private BigDecimal ATTACH_TAX_INCLUSIVE_AMT;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_PROVISIONAL_ATTACHMENT_USER.TOTAL_MADE_AMT
	 * @mbggenerated  Fri Mar 13 13:42:19 CST 2015
	 */
	private BigDecimal TOTAL_MADE_AMT;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_PROVISIONAL_ATTACHMENT_USER.PAYMENT_PROPORTION
	 * @mbggenerated  Fri Mar 13 13:42:19 CST 2015
	 */
	private Short PAYMENT_PROPORTION;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_PROVISIONAL_ATTACHMENT_USER.PAYMENT_BEGIN_DATE
	 * @mbggenerated  Fri Mar 13 13:42:19 CST 2015
	 */
	private Date PAYMENT_BEGIN_DATE;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_PROVISIONAL_ATTACHMENT_USER.PAYMENT_END_DATE
	 * @mbggenerated  Fri Mar 13 13:42:19 CST 2015
	 */
	private Date PAYMENT_END_DATE;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_PROVISIONAL_ATTACHMENT_USER.CR_USER
	 * @mbggenerated  Fri Mar 13 13:42:19 CST 2015
	 */
	private String CR_USER;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_PROVISIONAL_ATTACHMENT_USER.CR_TIME
	 * @mbggenerated  Fri Mar 13 13:42:19 CST 2015
	 */
	private Date CR_TIME;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_PROVISIONAL_ATTACHMENT_USER.MD_USER
	 * @mbggenerated  Fri Mar 13 13:42:19 CST 2015
	 */
	private String MD_USER;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_PROVISIONAL_ATTACHMENT_USER.MD_TIME
	 * @mbggenerated  Fri Mar 13 13:42:19 CST 2015
	 */
	private Date MD_TIME;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_PROVISIONAL_ATTACHMENT_USER.CHECK_ADDR
	 * @mbggenerated  Fri Mar 13 13:42:19 CST 2015
	 */
	private String CHECK_ADDR;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_PROVISIONAL_ATTACHMENT_USER.PAYMENT_USER_NAME
	 * @return  the value of TB_PAY_PROVISIONAL_ATTACHMENT_USER.PAYMENT_USER_NAME
	 * @mbggenerated  Fri Mar 13 13:42:19 CST 2015
	 */
	public String getPAYMENT_USER_NAME() {
		return PAYMENT_USER_NAME;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_PROVISIONAL_ATTACHMENT_USER.PAYMENT_USER_NAME
	 * @param PAYMENT_USER_NAME  the value for TB_PAY_PROVISIONAL_ATTACHMENT_USER.PAYMENT_USER_NAME
	 * @mbggenerated  Fri Mar 13 13:42:19 CST 2015
	 */
	public void setPAYMENT_USER_NAME(String PAYMENT_USER_NAME) {
		this.PAYMENT_USER_NAME = PAYMENT_USER_NAME == null ? null
				: PAYMENT_USER_NAME.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_PROVISIONAL_ATTACHMENT_USER.PAYMENT_METHOD
	 * @return  the value of TB_PAY_PROVISIONAL_ATTACHMENT_USER.PAYMENT_METHOD
	 * @mbggenerated  Fri Mar 13 13:42:19 CST 2015
	 */
	public String getPAYMENT_METHOD() {
		return PAYMENT_METHOD;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_PROVISIONAL_ATTACHMENT_USER.PAYMENT_METHOD
	 * @param PAYMENT_METHOD  the value for TB_PAY_PROVISIONAL_ATTACHMENT_USER.PAYMENT_METHOD
	 * @mbggenerated  Fri Mar 13 13:42:19 CST 2015
	 */
	public void setPAYMENT_METHOD(String PAYMENT_METHOD) {
		this.PAYMENT_METHOD = PAYMENT_METHOD == null ? null : PAYMENT_METHOD
				.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_PROVISIONAL_ATTACHMENT_USER.BANK_CODE
	 * @return  the value of TB_PAY_PROVISIONAL_ATTACHMENT_USER.BANK_CODE
	 * @mbggenerated  Fri Mar 13 13:42:19 CST 2015
	 */
	public String getBANK_CODE() {
		return BANK_CODE;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_PROVISIONAL_ATTACHMENT_USER.BANK_CODE
	 * @param BANK_CODE  the value for TB_PAY_PROVISIONAL_ATTACHMENT_USER.BANK_CODE
	 * @mbggenerated  Fri Mar 13 13:42:19 CST 2015
	 */
	public void setBANK_CODE(String BANK_CODE) {
		this.BANK_CODE = BANK_CODE == null ? null : BANK_CODE.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_PROVISIONAL_ATTACHMENT_USER.BANK_BRANCH_CODE
	 * @return  the value of TB_PAY_PROVISIONAL_ATTACHMENT_USER.BANK_BRANCH_CODE
	 * @mbggenerated  Fri Mar 13 13:42:19 CST 2015
	 */
	public String getBANK_BRANCH_CODE() {
		return BANK_BRANCH_CODE;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_PROVISIONAL_ATTACHMENT_USER.BANK_BRANCH_CODE
	 * @param BANK_BRANCH_CODE  the value for TB_PAY_PROVISIONAL_ATTACHMENT_USER.BANK_BRANCH_CODE
	 * @mbggenerated  Fri Mar 13 13:42:19 CST 2015
	 */
	public void setBANK_BRANCH_CODE(String BANK_BRANCH_CODE) {
		this.BANK_BRANCH_CODE = BANK_BRANCH_CODE == null ? null
				: BANK_BRANCH_CODE.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_PROVISIONAL_ATTACHMENT_USER.ACCOUNT_NBR
	 * @return  the value of TB_PAY_PROVISIONAL_ATTACHMENT_USER.ACCOUNT_NBR
	 * @mbggenerated  Fri Mar 13 13:42:19 CST 2015
	 */
	public String getACCOUNT_NBR() {
		return ACCOUNT_NBR;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_PROVISIONAL_ATTACHMENT_USER.ACCOUNT_NBR
	 * @param ACCOUNT_NBR  the value for TB_PAY_PROVISIONAL_ATTACHMENT_USER.ACCOUNT_NBR
	 * @mbggenerated  Fri Mar 13 13:42:19 CST 2015
	 */
	public void setACCOUNT_NBR(String ACCOUNT_NBR) {
		this.ACCOUNT_NBR = ACCOUNT_NBR == null ? null : ACCOUNT_NBR.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_PROVISIONAL_ATTACHMENT_USER.ATTACH_TAX_INCLUSIVE_AMT
	 * @return  the value of TB_PAY_PROVISIONAL_ATTACHMENT_USER.ATTACH_TAX_INCLUSIVE_AMT
	 * @mbggenerated  Fri Mar 13 13:42:19 CST 2015
	 */
	public BigDecimal getATTACH_TAX_INCLUSIVE_AMT() {
		return ATTACH_TAX_INCLUSIVE_AMT;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_PROVISIONAL_ATTACHMENT_USER.ATTACH_TAX_INCLUSIVE_AMT
	 * @param ATTACH_TAX_INCLUSIVE_AMT  the value for TB_PAY_PROVISIONAL_ATTACHMENT_USER.ATTACH_TAX_INCLUSIVE_AMT
	 * @mbggenerated  Fri Mar 13 13:42:19 CST 2015
	 */
	public void setATTACH_TAX_INCLUSIVE_AMT(BigDecimal ATTACH_TAX_INCLUSIVE_AMT) {
		this.ATTACH_TAX_INCLUSIVE_AMT = ATTACH_TAX_INCLUSIVE_AMT;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_PROVISIONAL_ATTACHMENT_USER.TOTAL_MADE_AMT
	 * @return  the value of TB_PAY_PROVISIONAL_ATTACHMENT_USER.TOTAL_MADE_AMT
	 * @mbggenerated  Fri Mar 13 13:42:19 CST 2015
	 */
	public BigDecimal getTOTAL_MADE_AMT() {
		return TOTAL_MADE_AMT;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_PROVISIONAL_ATTACHMENT_USER.TOTAL_MADE_AMT
	 * @param TOTAL_MADE_AMT  the value for TB_PAY_PROVISIONAL_ATTACHMENT_USER.TOTAL_MADE_AMT
	 * @mbggenerated  Fri Mar 13 13:42:19 CST 2015
	 */
	public void setTOTAL_MADE_AMT(BigDecimal TOTAL_MADE_AMT) {
		this.TOTAL_MADE_AMT = TOTAL_MADE_AMT;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_PROVISIONAL_ATTACHMENT_USER.PAYMENT_PROPORTION
	 * @return  the value of TB_PAY_PROVISIONAL_ATTACHMENT_USER.PAYMENT_PROPORTION
	 * @mbggenerated  Fri Mar 13 13:42:19 CST 2015
	 */
	public Short getPAYMENT_PROPORTION() {
		return PAYMENT_PROPORTION;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_PROVISIONAL_ATTACHMENT_USER.PAYMENT_PROPORTION
	 * @param PAYMENT_PROPORTION  the value for TB_PAY_PROVISIONAL_ATTACHMENT_USER.PAYMENT_PROPORTION
	 * @mbggenerated  Fri Mar 13 13:42:19 CST 2015
	 */
	public void setPAYMENT_PROPORTION(Short PAYMENT_PROPORTION) {
		this.PAYMENT_PROPORTION = PAYMENT_PROPORTION;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_PROVISIONAL_ATTACHMENT_USER.PAYMENT_BEGIN_DATE
	 * @return  the value of TB_PAY_PROVISIONAL_ATTACHMENT_USER.PAYMENT_BEGIN_DATE
	 * @mbggenerated  Fri Mar 13 13:42:19 CST 2015
	 */
	public Date getPAYMENT_BEGIN_DATE() {
		return PAYMENT_BEGIN_DATE;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_PROVISIONAL_ATTACHMENT_USER.PAYMENT_BEGIN_DATE
	 * @param PAYMENT_BEGIN_DATE  the value for TB_PAY_PROVISIONAL_ATTACHMENT_USER.PAYMENT_BEGIN_DATE
	 * @mbggenerated  Fri Mar 13 13:42:19 CST 2015
	 */
	public void setPAYMENT_BEGIN_DATE(Date PAYMENT_BEGIN_DATE) {
		this.PAYMENT_BEGIN_DATE = PAYMENT_BEGIN_DATE;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_PROVISIONAL_ATTACHMENT_USER.PAYMENT_END_DATE
	 * @return  the value of TB_PAY_PROVISIONAL_ATTACHMENT_USER.PAYMENT_END_DATE
	 * @mbggenerated  Fri Mar 13 13:42:19 CST 2015
	 */
	public Date getPAYMENT_END_DATE() {
		return PAYMENT_END_DATE;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_PROVISIONAL_ATTACHMENT_USER.PAYMENT_END_DATE
	 * @param PAYMENT_END_DATE  the value for TB_PAY_PROVISIONAL_ATTACHMENT_USER.PAYMENT_END_DATE
	 * @mbggenerated  Fri Mar 13 13:42:19 CST 2015
	 */
	public void setPAYMENT_END_DATE(Date PAYMENT_END_DATE) {
		this.PAYMENT_END_DATE = PAYMENT_END_DATE;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_PROVISIONAL_ATTACHMENT_USER.CR_USER
	 * @return  the value of TB_PAY_PROVISIONAL_ATTACHMENT_USER.CR_USER
	 * @mbggenerated  Fri Mar 13 13:42:19 CST 2015
	 */
	public String getCR_USER() {
		return CR_USER;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_PROVISIONAL_ATTACHMENT_USER.CR_USER
	 * @param CR_USER  the value for TB_PAY_PROVISIONAL_ATTACHMENT_USER.CR_USER
	 * @mbggenerated  Fri Mar 13 13:42:19 CST 2015
	 */
	public void setCR_USER(String CR_USER) {
		this.CR_USER = CR_USER == null ? null : CR_USER.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_PROVISIONAL_ATTACHMENT_USER.CR_TIME
	 * @return  the value of TB_PAY_PROVISIONAL_ATTACHMENT_USER.CR_TIME
	 * @mbggenerated  Fri Mar 13 13:42:19 CST 2015
	 */
	public Date getCR_TIME() {
		return CR_TIME;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_PROVISIONAL_ATTACHMENT_USER.CR_TIME
	 * @param CR_TIME  the value for TB_PAY_PROVISIONAL_ATTACHMENT_USER.CR_TIME
	 * @mbggenerated  Fri Mar 13 13:42:19 CST 2015
	 */
	public void setCR_TIME(Date CR_TIME) {
		this.CR_TIME = CR_TIME;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_PROVISIONAL_ATTACHMENT_USER.MD_USER
	 * @return  the value of TB_PAY_PROVISIONAL_ATTACHMENT_USER.MD_USER
	 * @mbggenerated  Fri Mar 13 13:42:19 CST 2015
	 */
	public String getMD_USER() {
		return MD_USER;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_PROVISIONAL_ATTACHMENT_USER.MD_USER
	 * @param MD_USER  the value for TB_PAY_PROVISIONAL_ATTACHMENT_USER.MD_USER
	 * @mbggenerated  Fri Mar 13 13:42:19 CST 2015
	 */
	public void setMD_USER(String MD_USER) {
		this.MD_USER = MD_USER == null ? null : MD_USER.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_PROVISIONAL_ATTACHMENT_USER.MD_TIME
	 * @return  the value of TB_PAY_PROVISIONAL_ATTACHMENT_USER.MD_TIME
	 * @mbggenerated  Fri Mar 13 13:42:19 CST 2015
	 */
	public Date getMD_TIME() {
		return MD_TIME;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_PROVISIONAL_ATTACHMENT_USER.MD_TIME
	 * @param MD_TIME  the value for TB_PAY_PROVISIONAL_ATTACHMENT_USER.MD_TIME
	 * @mbggenerated  Fri Mar 13 13:42:19 CST 2015
	 */
	public void setMD_TIME(Date MD_TIME) {
		this.MD_TIME = MD_TIME;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_PROVISIONAL_ATTACHMENT_USER.CHECK_ADDR
	 * @return  the value of TB_PAY_PROVISIONAL_ATTACHMENT_USER.CHECK_ADDR
	 * @mbggenerated  Fri Mar 13 13:42:19 CST 2015
	 */
	public String getCHECK_ADDR() {
		return CHECK_ADDR;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_PROVISIONAL_ATTACHMENT_USER.CHECK_ADDR
	 * @param CHECK_ADDR  the value for TB_PAY_PROVISIONAL_ATTACHMENT_USER.CHECK_ADDR
	 * @mbggenerated  Fri Mar 13 13:42:19 CST 2015
	 */
	public void setCHECK_ADDR(String CHECK_ADDR) {
		this.CHECK_ADDR = CHECK_ADDR == null ? null : CHECK_ADDR.trim();
	}
}