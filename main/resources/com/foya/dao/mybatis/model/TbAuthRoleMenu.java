package com.foya.dao.mybatis.model;

import java.util.Date;

public class TbAuthRoleMenu extends TbAuthRoleMenuKey {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_AUTH_ROLE_MENU.MD_USER
	 * @mbggenerated  Mon Jul 28 17:15:50 CST 2014
	 */
	private String MD_USER;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_AUTH_ROLE_MENU.MD_TIME
	 * @mbggenerated  Mon Jul 28 17:15:50 CST 2014
	 */
	private Date MD_TIME;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_AUTH_ROLE_MENU.MD_USER
	 * @return  the value of TB_AUTH_ROLE_MENU.MD_USER
	 * @mbggenerated  Mon Jul 28 17:15:50 CST 2014
	 */
	public String getMD_USER() {
		return MD_USER;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_AUTH_ROLE_MENU.MD_USER
	 * @param MD_USER  the value for TB_AUTH_ROLE_MENU.MD_USER
	 * @mbggenerated  Mon Jul 28 17:15:50 CST 2014
	 */
	public void setMD_USER(String MD_USER) {
		this.MD_USER = MD_USER;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_AUTH_ROLE_MENU.MD_TIME
	 * @return  the value of TB_AUTH_ROLE_MENU.MD_TIME
	 * @mbggenerated  Mon Jul 28 17:15:50 CST 2014
	 */
	public Date getMD_TIME() {
		return MD_TIME;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_AUTH_ROLE_MENU.MD_TIME
	 * @param MD_TIME  the value for TB_AUTH_ROLE_MENU.MD_TIME
	 * @mbggenerated  Mon Jul 28 17:15:50 CST 2014
	 */
	public void setMD_TIME(Date MD_TIME) {
		this.MD_TIME = MD_TIME;
	}
}