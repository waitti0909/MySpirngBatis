package com.foya.dao.mybatis.model;

import java.math.BigDecimal;

public class TbAuthRoleMenuKey {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_AUTH_ROLE_MENU.ROLE_ID
	 * @mbggenerated  Mon Jul 28 17:15:50 CST 2014
	 */
	private BigDecimal ROLE_ID;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_AUTH_ROLE_MENU.MENU_ID
	 * @mbggenerated  Mon Jul 28 17:15:50 CST 2014
	 */
	private BigDecimal MENU_ID;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_AUTH_ROLE_MENU.ROLE_ID
	 * @return  the value of TB_AUTH_ROLE_MENU.ROLE_ID
	 * @mbggenerated  Mon Jul 28 17:15:50 CST 2014
	 */
	public BigDecimal getROLE_ID() {
		return ROLE_ID;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_AUTH_ROLE_MENU.ROLE_ID
	 * @param ROLE_ID  the value for TB_AUTH_ROLE_MENU.ROLE_ID
	 * @mbggenerated  Mon Jul 28 17:15:50 CST 2014
	 */
	public void setROLE_ID(BigDecimal ROLE_ID) {
		this.ROLE_ID = ROLE_ID;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_AUTH_ROLE_MENU.MENU_ID
	 * @return  the value of TB_AUTH_ROLE_MENU.MENU_ID
	 * @mbggenerated  Mon Jul 28 17:15:50 CST 2014
	 */
	public BigDecimal getMENU_ID() {
		return MENU_ID;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_AUTH_ROLE_MENU.MENU_ID
	 * @param MENU_ID  the value for TB_AUTH_ROLE_MENU.MENU_ID
	 * @mbggenerated  Mon Jul 28 17:15:50 CST 2014
	 */
	public void setMENU_ID(BigDecimal MENU_ID) {
		this.MENU_ID = MENU_ID;
	}
}