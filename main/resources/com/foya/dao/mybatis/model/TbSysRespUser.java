package com.foya.dao.mybatis.model;

public class TbSysRespUser extends TbSysRespUserKey {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_SYS_RESP_USER.DEPT_TEAM
	 * @mbggenerated  Wed Feb 04 15:55:18 CST 2015
	 */
	private String DEPT_TEAM;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_SYS_RESP_USER.RESP_USER
	 * @mbggenerated  Wed Feb 04 15:55:18 CST 2015
	 */
	private String RESP_USER;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_SYS_RESP_USER.DEPT_TEAM
	 * @return  the value of TB_SYS_RESP_USER.DEPT_TEAM
	 * @mbggenerated  Wed Feb 04 15:55:18 CST 2015
	 */
	public String getDEPT_TEAM() {
		return DEPT_TEAM;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_SYS_RESP_USER.DEPT_TEAM
	 * @param DEPT_TEAM  the value for TB_SYS_RESP_USER.DEPT_TEAM
	 * @mbggenerated  Wed Feb 04 15:55:18 CST 2015
	 */
	public void setDEPT_TEAM(String DEPT_TEAM) {
		this.DEPT_TEAM = DEPT_TEAM == null ? null : DEPT_TEAM.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_SYS_RESP_USER.RESP_USER
	 * @return  the value of TB_SYS_RESP_USER.RESP_USER
	 * @mbggenerated  Wed Feb 04 15:55:18 CST 2015
	 */
	public String getRESP_USER() {
		return RESP_USER;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_SYS_RESP_USER.RESP_USER
	 * @param RESP_USER  the value for TB_SYS_RESP_USER.RESP_USER
	 * @mbggenerated  Wed Feb 04 15:55:18 CST 2015
	 */
	public void setRESP_USER(String RESP_USER) {
		this.RESP_USER = RESP_USER == null ? null : RESP_USER.trim();
	}
}