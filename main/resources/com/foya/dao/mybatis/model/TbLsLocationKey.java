package com.foya.dao.mybatis.model;

import java.util.Date;

public class TbLsLocationKey {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_LS_LOCATION.LS_NO
	 * @mbggenerated  Thu Mar 19 15:18:13 CST 2015
	 */
	private String LS_NO;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_LS_LOCATION.LS_VER
	 * @mbggenerated  Thu Mar 19 15:18:13 CST 2015
	 */
	private String LS_VER;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_LS_LOCATION.LOCATION_ID
	 * @mbggenerated  Thu Mar 19 15:18:13 CST 2015
	 */
	private String LOCATION_ID;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_LS_LOCATION.EFF_DATE
	 * @mbggenerated  Thu Mar 19 15:18:13 CST 2015
	 */
	private Date EFF_DATE;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_LS_LOCATION.LS_NO
	 * @return  the value of TB_LS_LOCATION.LS_NO
	 * @mbggenerated  Thu Mar 19 15:18:13 CST 2015
	 */
	public String getLS_NO() {
		return LS_NO;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_LS_LOCATION.LS_NO
	 * @param LS_NO  the value for TB_LS_LOCATION.LS_NO
	 * @mbggenerated  Thu Mar 19 15:18:13 CST 2015
	 */
	public void setLS_NO(String LS_NO) {
		this.LS_NO = LS_NO == null ? null : LS_NO.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_LS_LOCATION.LS_VER
	 * @return  the value of TB_LS_LOCATION.LS_VER
	 * @mbggenerated  Thu Mar 19 15:18:13 CST 2015
	 */
	public String getLS_VER() {
		return LS_VER;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_LS_LOCATION.LS_VER
	 * @param LS_VER  the value for TB_LS_LOCATION.LS_VER
	 * @mbggenerated  Thu Mar 19 15:18:13 CST 2015
	 */
	public void setLS_VER(String LS_VER) {
		this.LS_VER = LS_VER == null ? null : LS_VER.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_LS_LOCATION.LOCATION_ID
	 * @return  the value of TB_LS_LOCATION.LOCATION_ID
	 * @mbggenerated  Thu Mar 19 15:18:13 CST 2015
	 */
	public String getLOCATION_ID() {
		return LOCATION_ID;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_LS_LOCATION.LOCATION_ID
	 * @param LOCATION_ID  the value for TB_LS_LOCATION.LOCATION_ID
	 * @mbggenerated  Thu Mar 19 15:18:13 CST 2015
	 */
	public void setLOCATION_ID(String LOCATION_ID) {
		this.LOCATION_ID = LOCATION_ID == null ? null : LOCATION_ID.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_LS_LOCATION.EFF_DATE
	 * @return  the value of TB_LS_LOCATION.EFF_DATE
	 * @mbggenerated  Thu Mar 19 15:18:13 CST 2015
	 */
	public Date getEFF_DATE() {
		return EFF_DATE;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_LS_LOCATION.EFF_DATE
	 * @param EFF_DATE  the value for TB_LS_LOCATION.EFF_DATE
	 * @mbggenerated  Thu Mar 19 15:18:13 CST 2015
	 */
	public void setEFF_DATE(Date EFF_DATE) {
		this.EFF_DATE = EFF_DATE;
	}
}