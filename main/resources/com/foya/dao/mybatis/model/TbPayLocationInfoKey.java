package com.foya.dao.mybatis.model;

public class TbPayLocationInfoKey {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_LOCATION_INFO.CONTRACT_NO
	 * @mbggenerated  Fri Apr 10 14:48:18 CST 2015
	 */
	private String CONTRACT_NO;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_LOCATION_INFO.DOMAIN
	 * @mbggenerated  Fri Apr 10 14:48:18 CST 2015
	 */
	private String DOMAIN;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_PAY_LOCATION_INFO.LOCATION_ID
	 * @mbggenerated  Fri Apr 10 14:48:18 CST 2015
	 */
	private String LOCATION_ID;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_LOCATION_INFO.CONTRACT_NO
	 * @return  the value of TB_PAY_LOCATION_INFO.CONTRACT_NO
	 * @mbggenerated  Fri Apr 10 14:48:18 CST 2015
	 */
	public String getCONTRACT_NO() {
		return CONTRACT_NO;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_LOCATION_INFO.CONTRACT_NO
	 * @param CONTRACT_NO  the value for TB_PAY_LOCATION_INFO.CONTRACT_NO
	 * @mbggenerated  Fri Apr 10 14:48:18 CST 2015
	 */
	public void setCONTRACT_NO(String CONTRACT_NO) {
		this.CONTRACT_NO = CONTRACT_NO == null ? null : CONTRACT_NO.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_LOCATION_INFO.DOMAIN
	 * @return  the value of TB_PAY_LOCATION_INFO.DOMAIN
	 * @mbggenerated  Fri Apr 10 14:48:18 CST 2015
	 */
	public String getDOMAIN() {
		return DOMAIN;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_LOCATION_INFO.DOMAIN
	 * @param DOMAIN  the value for TB_PAY_LOCATION_INFO.DOMAIN
	 * @mbggenerated  Fri Apr 10 14:48:18 CST 2015
	 */
	public void setDOMAIN(String DOMAIN) {
		this.DOMAIN = DOMAIN == null ? null : DOMAIN.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_PAY_LOCATION_INFO.LOCATION_ID
	 * @return  the value of TB_PAY_LOCATION_INFO.LOCATION_ID
	 * @mbggenerated  Fri Apr 10 14:48:18 CST 2015
	 */
	public String getLOCATION_ID() {
		return LOCATION_ID;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_PAY_LOCATION_INFO.LOCATION_ID
	 * @param LOCATION_ID  the value for TB_PAY_LOCATION_INFO.LOCATION_ID
	 * @mbggenerated  Fri Apr 10 14:48:18 CST 2015
	 */
	public void setLOCATION_ID(String LOCATION_ID) {
		this.LOCATION_ID = LOCATION_ID == null ? null : LOCATION_ID.trim();
	}
}