package com.foya.dao.mybatis.model;

public class TbSiteWorkAntCfgTempKey {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_SITE_WORK_ANT_CFG_TEMP.WORK_ID
	 * @mbggenerated  Mon Mar 30 15:10:16 CST 2015
	 */
	private String WORK_ID;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_SITE_WORK_ANT_CFG_TEMP.SITE_ID
	 * @mbggenerated  Mon Mar 30 15:10:16 CST 2015
	 */
	private String SITE_ID;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_SITE_WORK_ANT_CFG_TEMP.ANT_NO
	 * @mbggenerated  Mon Mar 30 15:10:16 CST 2015
	 */
	private String ANT_NO;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_SITE_WORK_ANT_CFG_TEMP.WORK_ID
	 * @return  the value of TB_SITE_WORK_ANT_CFG_TEMP.WORK_ID
	 * @mbggenerated  Mon Mar 30 15:10:16 CST 2015
	 */
	public String getWORK_ID() {
		return WORK_ID;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_SITE_WORK_ANT_CFG_TEMP.WORK_ID
	 * @param WORK_ID  the value for TB_SITE_WORK_ANT_CFG_TEMP.WORK_ID
	 * @mbggenerated  Mon Mar 30 15:10:16 CST 2015
	 */
	public void setWORK_ID(String WORK_ID) {
		this.WORK_ID = WORK_ID == null ? null : WORK_ID.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_SITE_WORK_ANT_CFG_TEMP.SITE_ID
	 * @return  the value of TB_SITE_WORK_ANT_CFG_TEMP.SITE_ID
	 * @mbggenerated  Mon Mar 30 15:10:16 CST 2015
	 */
	public String getSITE_ID() {
		return SITE_ID;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_SITE_WORK_ANT_CFG_TEMP.SITE_ID
	 * @param SITE_ID  the value for TB_SITE_WORK_ANT_CFG_TEMP.SITE_ID
	 * @mbggenerated  Mon Mar 30 15:10:16 CST 2015
	 */
	public void setSITE_ID(String SITE_ID) {
		this.SITE_ID = SITE_ID == null ? null : SITE_ID.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_SITE_WORK_ANT_CFG_TEMP.ANT_NO
	 * @return  the value of TB_SITE_WORK_ANT_CFG_TEMP.ANT_NO
	 * @mbggenerated  Mon Mar 30 15:10:16 CST 2015
	 */
	public String getANT_NO() {
		return ANT_NO;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_SITE_WORK_ANT_CFG_TEMP.ANT_NO
	 * @param ANT_NO  the value for TB_SITE_WORK_ANT_CFG_TEMP.ANT_NO
	 * @mbggenerated  Mon Mar 30 15:10:16 CST 2015
	 */
	public void setANT_NO(String ANT_NO) {
		this.ANT_NO = ANT_NO == null ? null : ANT_NO.trim();
	}
}