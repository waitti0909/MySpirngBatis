package com.foya.dao.mybatis.model;

public class TbSysWorkType {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_SYS_WORK_TYPE.WK_TYPE_ID
	 * @mbggenerated  Mon Jan 05 19:58:26 CST 2015
	 */
	private String WK_TYPE_ID;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_SYS_WORK_TYPE.WK_TYPE_NAME
	 * @mbggenerated  Mon Jan 05 19:58:26 CST 2015
	 */
	private String WK_TYPE_NAME;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_SYS_WORK_TYPE.WK_SORT
	 * @mbggenerated  Mon Jan 05 19:58:26 CST 2015
	 */
	private Integer WK_SORT;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_SYS_WORK_TYPE.WK_TYPE_ID
	 * @return  the value of TB_SYS_WORK_TYPE.WK_TYPE_ID
	 * @mbggenerated  Mon Jan 05 19:58:26 CST 2015
	 */
	public String getWK_TYPE_ID() {
		return WK_TYPE_ID;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_SYS_WORK_TYPE.WK_TYPE_ID
	 * @param WK_TYPE_ID  the value for TB_SYS_WORK_TYPE.WK_TYPE_ID
	 * @mbggenerated  Mon Jan 05 19:58:26 CST 2015
	 */
	public void setWK_TYPE_ID(String WK_TYPE_ID) {
		this.WK_TYPE_ID = WK_TYPE_ID == null ? null : WK_TYPE_ID.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_SYS_WORK_TYPE.WK_TYPE_NAME
	 * @return  the value of TB_SYS_WORK_TYPE.WK_TYPE_NAME
	 * @mbggenerated  Mon Jan 05 19:58:26 CST 2015
	 */
	public String getWK_TYPE_NAME() {
		return WK_TYPE_NAME;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_SYS_WORK_TYPE.WK_TYPE_NAME
	 * @param WK_TYPE_NAME  the value for TB_SYS_WORK_TYPE.WK_TYPE_NAME
	 * @mbggenerated  Mon Jan 05 19:58:26 CST 2015
	 */
	public void setWK_TYPE_NAME(String WK_TYPE_NAME) {
		this.WK_TYPE_NAME = WK_TYPE_NAME == null ? null : WK_TYPE_NAME.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_SYS_WORK_TYPE.WK_SORT
	 * @return  the value of TB_SYS_WORK_TYPE.WK_SORT
	 * @mbggenerated  Mon Jan 05 19:58:26 CST 2015
	 */
	public Integer getWK_SORT() {
		return WK_SORT;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_SYS_WORK_TYPE.WK_SORT
	 * @param WK_SORT  the value for TB_SYS_WORK_TYPE.WK_SORT
	 * @mbggenerated  Mon Jan 05 19:58:26 CST 2015
	 */
	public void setWK_SORT(Integer WK_SORT) {
		this.WK_SORT = WK_SORT;
	}
}