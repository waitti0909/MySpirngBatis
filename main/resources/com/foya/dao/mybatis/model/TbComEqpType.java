package com.foya.dao.mybatis.model;

import java.util.Date;

public class TbComEqpType {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_COM_EQP_TYPE.EQP_TYPE_ID
	 * @mbggenerated  Wed Oct 29 14:21:04 CST 2014
	 */
	private String EQP_TYPE_ID;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_COM_EQP_TYPE.EQP_NAME
	 * @mbggenerated  Wed Oct 29 14:21:04 CST 2014
	 */
	private String EQP_NAME;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_COM_EQP_TYPE.CR_USER
	 * @mbggenerated  Wed Oct 29 14:21:04 CST 2014
	 */
	private String CR_USER;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_COM_EQP_TYPE.CR_TIME
	 * @mbggenerated  Wed Oct 29 14:21:04 CST 2014
	 */
	private Date CR_TIME;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_COM_EQP_TYPE.MD_USER
	 * @mbggenerated  Wed Oct 29 14:21:04 CST 2014
	 */
	private String MD_USER;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_COM_EQP_TYPE.MD_TIME
	 * @mbggenerated  Wed Oct 29 14:21:04 CST 2014
	 */
	private Date MD_TIME;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_COM_EQP_TYPE.EQP_TYPE_ID
	 * @return  the value of TB_COM_EQP_TYPE.EQP_TYPE_ID
	 * @mbggenerated  Wed Oct 29 14:21:04 CST 2014
	 */
	public String getEQP_TYPE_ID() {
		return EQP_TYPE_ID;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_COM_EQP_TYPE.EQP_TYPE_ID
	 * @param EQP_TYPE_ID  the value for TB_COM_EQP_TYPE.EQP_TYPE_ID
	 * @mbggenerated  Wed Oct 29 14:21:04 CST 2014
	 */
	public void setEQP_TYPE_ID(String EQP_TYPE_ID) {
		this.EQP_TYPE_ID = EQP_TYPE_ID == null ? null : EQP_TYPE_ID.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_COM_EQP_TYPE.EQP_NAME
	 * @return  the value of TB_COM_EQP_TYPE.EQP_NAME
	 * @mbggenerated  Wed Oct 29 14:21:04 CST 2014
	 */
	public String getEQP_NAME() {
		return EQP_NAME;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_COM_EQP_TYPE.EQP_NAME
	 * @param EQP_NAME  the value for TB_COM_EQP_TYPE.EQP_NAME
	 * @mbggenerated  Wed Oct 29 14:21:04 CST 2014
	 */
	public void setEQP_NAME(String EQP_NAME) {
		this.EQP_NAME = EQP_NAME == null ? null : EQP_NAME.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_COM_EQP_TYPE.CR_USER
	 * @return  the value of TB_COM_EQP_TYPE.CR_USER
	 * @mbggenerated  Wed Oct 29 14:21:04 CST 2014
	 */
	public String getCR_USER() {
		return CR_USER;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_COM_EQP_TYPE.CR_USER
	 * @param CR_USER  the value for TB_COM_EQP_TYPE.CR_USER
	 * @mbggenerated  Wed Oct 29 14:21:04 CST 2014
	 */
	public void setCR_USER(String CR_USER) {
		this.CR_USER = CR_USER == null ? null : CR_USER.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_COM_EQP_TYPE.CR_TIME
	 * @return  the value of TB_COM_EQP_TYPE.CR_TIME
	 * @mbggenerated  Wed Oct 29 14:21:04 CST 2014
	 */
	public Date getCR_TIME() {
		return CR_TIME;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_COM_EQP_TYPE.CR_TIME
	 * @param CR_TIME  the value for TB_COM_EQP_TYPE.CR_TIME
	 * @mbggenerated  Wed Oct 29 14:21:04 CST 2014
	 */
	public void setCR_TIME(Date CR_TIME) {
		this.CR_TIME = CR_TIME;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_COM_EQP_TYPE.MD_USER
	 * @return  the value of TB_COM_EQP_TYPE.MD_USER
	 * @mbggenerated  Wed Oct 29 14:21:04 CST 2014
	 */
	public String getMD_USER() {
		return MD_USER;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_COM_EQP_TYPE.MD_USER
	 * @param MD_USER  the value for TB_COM_EQP_TYPE.MD_USER
	 * @mbggenerated  Wed Oct 29 14:21:04 CST 2014
	 */
	public void setMD_USER(String MD_USER) {
		this.MD_USER = MD_USER == null ? null : MD_USER.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_COM_EQP_TYPE.MD_TIME
	 * @return  the value of TB_COM_EQP_TYPE.MD_TIME
	 * @mbggenerated  Wed Oct 29 14:21:04 CST 2014
	 */
	public Date getMD_TIME() {
		return MD_TIME;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_COM_EQP_TYPE.MD_TIME
	 * @param MD_TIME  the value for TB_COM_EQP_TYPE.MD_TIME
	 * @mbggenerated  Wed Oct 29 14:21:04 CST 2014
	 */
	public void setMD_TIME(Date MD_TIME) {
		this.MD_TIME = MD_TIME;
	}
}