package com.foya.dao.mybatis.model;

import java.util.Date;

public class TbPayAcceptanceOrder extends TbPayAcceptanceOrderKey {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_ACCEPTANCE_ORDER.SITE_ID
	 * @mbggenerated  Wed Apr 15 14:27:46 CST 2015
	 */
	private String SITE_ID;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_ACCEPTANCE_ORDER.CR_USER
	 * @mbggenerated  Wed Apr 15 14:27:46 CST 2015
	 */
	private String CR_USER;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_ACCEPTANCE_ORDER.CR_TIME
	 * @mbggenerated  Wed Apr 15 14:27:46 CST 2015
	 */
	private Date CR_TIME;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_ACCEPTANCE_ORDER.MD_USER
	 * @mbggenerated  Wed Apr 15 14:27:46 CST 2015
	 */
	private String MD_USER;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_ACCEPTANCE_ORDER.MD_TIME
	 * @mbggenerated  Wed Apr 15 14:27:46 CST 2015
	 */
	private Date MD_TIME;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_ACCEPTANCE_ORDER.LEASE_NO
	 * @mbggenerated  Wed Apr 15 14:27:46 CST 2015
	 */
	private String LEASE_NO;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_ACCEPTANCE_ORDER.SITE_ID
	 * @return  the value of TB_PAY_ACCEPTANCE_ORDER.SITE_ID
	 * @mbggenerated  Wed Apr 15 14:27:46 CST 2015
	 */
	public String getSITE_ID() {
		return SITE_ID;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_ACCEPTANCE_ORDER.SITE_ID
	 * @param SITE_ID  the value for TB_PAY_ACCEPTANCE_ORDER.SITE_ID
	 * @mbggenerated  Wed Apr 15 14:27:46 CST 2015
	 */
	public void setSITE_ID(String SITE_ID) {
		this.SITE_ID = SITE_ID == null ? null : SITE_ID.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_ACCEPTANCE_ORDER.CR_USER
	 * @return  the value of TB_PAY_ACCEPTANCE_ORDER.CR_USER
	 * @mbggenerated  Wed Apr 15 14:27:46 CST 2015
	 */
	public String getCR_USER() {
		return CR_USER;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_ACCEPTANCE_ORDER.CR_USER
	 * @param CR_USER  the value for TB_PAY_ACCEPTANCE_ORDER.CR_USER
	 * @mbggenerated  Wed Apr 15 14:27:46 CST 2015
	 */
	public void setCR_USER(String CR_USER) {
		this.CR_USER = CR_USER == null ? null : CR_USER.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_ACCEPTANCE_ORDER.CR_TIME
	 * @return  the value of TB_PAY_ACCEPTANCE_ORDER.CR_TIME
	 * @mbggenerated  Wed Apr 15 14:27:46 CST 2015
	 */
	public Date getCR_TIME() {
		return CR_TIME;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_ACCEPTANCE_ORDER.CR_TIME
	 * @param CR_TIME  the value for TB_PAY_ACCEPTANCE_ORDER.CR_TIME
	 * @mbggenerated  Wed Apr 15 14:27:46 CST 2015
	 */
	public void setCR_TIME(Date CR_TIME) {
		this.CR_TIME = CR_TIME;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_ACCEPTANCE_ORDER.MD_USER
	 * @return  the value of TB_PAY_ACCEPTANCE_ORDER.MD_USER
	 * @mbggenerated  Wed Apr 15 14:27:46 CST 2015
	 */
	public String getMD_USER() {
		return MD_USER;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_ACCEPTANCE_ORDER.MD_USER
	 * @param MD_USER  the value for TB_PAY_ACCEPTANCE_ORDER.MD_USER
	 * @mbggenerated  Wed Apr 15 14:27:46 CST 2015
	 */
	public void setMD_USER(String MD_USER) {
		this.MD_USER = MD_USER == null ? null : MD_USER.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_ACCEPTANCE_ORDER.MD_TIME
	 * @return  the value of TB_PAY_ACCEPTANCE_ORDER.MD_TIME
	 * @mbggenerated  Wed Apr 15 14:27:46 CST 2015
	 */
	public Date getMD_TIME() {
		return MD_TIME;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_ACCEPTANCE_ORDER.MD_TIME
	 * @param MD_TIME  the value for TB_PAY_ACCEPTANCE_ORDER.MD_TIME
	 * @mbggenerated  Wed Apr 15 14:27:46 CST 2015
	 */
	public void setMD_TIME(Date MD_TIME) {
		this.MD_TIME = MD_TIME;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_ACCEPTANCE_ORDER.LEASE_NO
	 * @return  the value of TB_PAY_ACCEPTANCE_ORDER.LEASE_NO
	 * @mbggenerated  Wed Apr 15 14:27:46 CST 2015
	 */
	public String getLEASE_NO() {
		return LEASE_NO;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_ACCEPTANCE_ORDER.LEASE_NO
	 * @param LEASE_NO  the value for TB_PAY_ACCEPTANCE_ORDER.LEASE_NO
	 * @mbggenerated  Wed Apr 15 14:27:46 CST 2015
	 */
	public void setLEASE_NO(String LEASE_NO) {
		this.LEASE_NO = LEASE_NO == null ? null : LEASE_NO.trim();
	}
}