package com.foya.dao.mybatis.model;

public class TbSiteWorkShareTempKey {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_SITE_WORK_SHARE_TEMP.WORK_ID
	 * @mbggenerated  Mon Feb 09 15:59:55 CST 2015
	 */
	private String WORK_ID;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_SITE_WORK_SHARE_TEMP.LOCATION_ID
	 * @mbggenerated  Mon Feb 09 15:59:55 CST 2015
	 */
	private String LOCATION_ID;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_SITE_WORK_SHARE_TEMP.SHARE_COM
	 * @mbggenerated  Mon Feb 09 15:59:55 CST 2015
	 */
	private String SHARE_COM;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_SITE_WORK_SHARE_TEMP.WORK_ID
	 * @return  the value of TB_SITE_WORK_SHARE_TEMP.WORK_ID
	 * @mbggenerated  Mon Feb 09 15:59:55 CST 2015
	 */
	public String getWORK_ID() {
		return WORK_ID;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_SITE_WORK_SHARE_TEMP.WORK_ID
	 * @param WORK_ID  the value for TB_SITE_WORK_SHARE_TEMP.WORK_ID
	 * @mbggenerated  Mon Feb 09 15:59:55 CST 2015
	 */
	public void setWORK_ID(String WORK_ID) {
		this.WORK_ID = WORK_ID == null ? null : WORK_ID.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_SITE_WORK_SHARE_TEMP.LOCATION_ID
	 * @return  the value of TB_SITE_WORK_SHARE_TEMP.LOCATION_ID
	 * @mbggenerated  Mon Feb 09 15:59:55 CST 2015
	 */
	public String getLOCATION_ID() {
		return LOCATION_ID;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_SITE_WORK_SHARE_TEMP.LOCATION_ID
	 * @param LOCATION_ID  the value for TB_SITE_WORK_SHARE_TEMP.LOCATION_ID
	 * @mbggenerated  Mon Feb 09 15:59:55 CST 2015
	 */
	public void setLOCATION_ID(String LOCATION_ID) {
		this.LOCATION_ID = LOCATION_ID == null ? null : LOCATION_ID.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_SITE_WORK_SHARE_TEMP.SHARE_COM
	 * @return  the value of TB_SITE_WORK_SHARE_TEMP.SHARE_COM
	 * @mbggenerated  Mon Feb 09 15:59:55 CST 2015
	 */
	public String getSHARE_COM() {
		return SHARE_COM;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_SITE_WORK_SHARE_TEMP.SHARE_COM
	 * @param SHARE_COM  the value for TB_SITE_WORK_SHARE_TEMP.SHARE_COM
	 * @mbggenerated  Mon Feb 09 15:59:55 CST 2015
	 */
	public void setSHARE_COM(String SHARE_COM) {
		this.SHARE_COM = SHARE_COM == null ? null : SHARE_COM.trim();
	}
}