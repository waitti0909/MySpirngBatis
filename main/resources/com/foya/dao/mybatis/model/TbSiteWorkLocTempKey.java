package com.foya.dao.mybatis.model;

public class TbSiteWorkLocTempKey {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_SITE_WORK_LOC_TEMP.WORK_ID
	 * @mbggenerated  Tue Feb 24 11:38:17 CST 2015
	 */
	private String WORK_ID;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_SITE_WORK_LOC_TEMP.LOCATION_ID
	 * @mbggenerated  Tue Feb 24 11:38:17 CST 2015
	 */
	private String LOCATION_ID;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_SITE_WORK_LOC_TEMP.WORK_ID
	 * @return  the value of TB_SITE_WORK_LOC_TEMP.WORK_ID
	 * @mbggenerated  Tue Feb 24 11:38:17 CST 2015
	 */
	public String getWORK_ID() {
		return WORK_ID;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_SITE_WORK_LOC_TEMP.WORK_ID
	 * @param WORK_ID  the value for TB_SITE_WORK_LOC_TEMP.WORK_ID
	 * @mbggenerated  Tue Feb 24 11:38:17 CST 2015
	 */
	public void setWORK_ID(String WORK_ID) {
		this.WORK_ID = WORK_ID == null ? null : WORK_ID.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_SITE_WORK_LOC_TEMP.LOCATION_ID
	 * @return  the value of TB_SITE_WORK_LOC_TEMP.LOCATION_ID
	 * @mbggenerated  Tue Feb 24 11:38:17 CST 2015
	 */
	public String getLOCATION_ID() {
		return LOCATION_ID;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_SITE_WORK_LOC_TEMP.LOCATION_ID
	 * @param LOCATION_ID  the value for TB_SITE_WORK_LOC_TEMP.LOCATION_ID
	 * @mbggenerated  Tue Feb 24 11:38:17 CST 2015
	 */
	public void setLOCATION_ID(String LOCATION_ID) {
		this.LOCATION_ID = LOCATION_ID == null ? null : LOCATION_ID.trim();
	}
}