package com.foya.dao.mybatis.model;

import java.util.Date;

public class TbInvTr {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_INV_TR.tr_no
	 * @mbggenerated  Thu Jan 08 11:08:08 CST 2015
	 */
	private String tr_no;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_INV_TR.tr_out_wh_id
	 * @mbggenerated  Thu Jan 08 11:08:08 CST 2015
	 */
	private String tr_out_wh_id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_INV_TR.tr_in_wh_id
	 * @mbggenerated  Thu Jan 08 11:08:08 CST 2015
	 */
	private String tr_in_wh_id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_INV_TR.need_dept_id
	 * @mbggenerated  Thu Jan 08 11:08:08 CST 2015
	 */
	private String need_dept_id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_INV_TR.need_date
	 * @mbggenerated  Thu Jan 08 11:08:08 CST 2015
	 */
	private Date need_date;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_INV_TR.app_dept_id
	 * @mbggenerated  Thu Jan 08 11:08:08 CST 2015
	 */
	private String app_dept_id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_INV_TR.app_user
	 * @mbggenerated  Thu Jan 08 11:08:08 CST 2015
	 */
	private String app_user;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_INV_TR.app_date
	 * @mbggenerated  Thu Jan 08 11:08:08 CST 2015
	 */
	private Date app_date;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_INV_TR.status
	 * @mbggenerated  Thu Jan 08 11:08:08 CST 2015
	 */
	private Byte status;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_INV_TR.if_manual_close
	 * @mbggenerated  Thu Jan 08 11:08:08 CST 2015
	 */
	private Boolean if_manual_close;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_INV_TR.memo
	 * @mbggenerated  Thu Jan 08 11:08:08 CST 2015
	 */
	private String memo;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_INV_TR.cr_user
	 * @mbggenerated  Thu Jan 08 11:08:08 CST 2015
	 */
	private String cr_user;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_INV_TR.cr_time
	 * @mbggenerated  Thu Jan 08 11:08:08 CST 2015
	 */
	private Date cr_time;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_INV_TR.md_user
	 * @mbggenerated  Thu Jan 08 11:08:08 CST 2015
	 */
	private String md_user;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_INV_TR.md_time
	 * @mbggenerated  Thu Jan 08 11:08:08 CST 2015
	 */
	private Date md_time;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_INV_TR.processType
	 * @mbggenerated  Thu Jan 08 11:08:08 CST 2015
	 */
	private String processType;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_INV_TR.tr_no
	 * @return  the value of TB_INV_TR.tr_no
	 * @mbggenerated  Thu Jan 08 11:08:08 CST 2015
	 */
	public String getTr_no() {
		return tr_no;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_INV_TR.tr_no
	 * @param tr_no  the value for TB_INV_TR.tr_no
	 * @mbggenerated  Thu Jan 08 11:08:08 CST 2015
	 */
	public void setTr_no(String tr_no) {
		this.tr_no = tr_no == null ? null : tr_no.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_INV_TR.tr_out_wh_id
	 * @return  the value of TB_INV_TR.tr_out_wh_id
	 * @mbggenerated  Thu Jan 08 11:08:08 CST 2015
	 */
	public String getTr_out_wh_id() {
		return tr_out_wh_id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_INV_TR.tr_out_wh_id
	 * @param tr_out_wh_id  the value for TB_INV_TR.tr_out_wh_id
	 * @mbggenerated  Thu Jan 08 11:08:08 CST 2015
	 */
	public void setTr_out_wh_id(String tr_out_wh_id) {
		this.tr_out_wh_id = tr_out_wh_id == null ? null : tr_out_wh_id.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_INV_TR.tr_in_wh_id
	 * @return  the value of TB_INV_TR.tr_in_wh_id
	 * @mbggenerated  Thu Jan 08 11:08:08 CST 2015
	 */
	public String getTr_in_wh_id() {
		return tr_in_wh_id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_INV_TR.tr_in_wh_id
	 * @param tr_in_wh_id  the value for TB_INV_TR.tr_in_wh_id
	 * @mbggenerated  Thu Jan 08 11:08:08 CST 2015
	 */
	public void setTr_in_wh_id(String tr_in_wh_id) {
		this.tr_in_wh_id = tr_in_wh_id == null ? null : tr_in_wh_id.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_INV_TR.need_dept_id
	 * @return  the value of TB_INV_TR.need_dept_id
	 * @mbggenerated  Thu Jan 08 11:08:08 CST 2015
	 */
	public String getNeed_dept_id() {
		return need_dept_id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_INV_TR.need_dept_id
	 * @param need_dept_id  the value for TB_INV_TR.need_dept_id
	 * @mbggenerated  Thu Jan 08 11:08:08 CST 2015
	 */
	public void setNeed_dept_id(String need_dept_id) {
		this.need_dept_id = need_dept_id == null ? null : need_dept_id.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_INV_TR.need_date
	 * @return  the value of TB_INV_TR.need_date
	 * @mbggenerated  Thu Jan 08 11:08:08 CST 2015
	 */
	public Date getNeed_date() {
		return need_date;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_INV_TR.need_date
	 * @param need_date  the value for TB_INV_TR.need_date
	 * @mbggenerated  Thu Jan 08 11:08:08 CST 2015
	 */
	public void setNeed_date(Date need_date) {
		this.need_date = need_date;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_INV_TR.app_dept_id
	 * @return  the value of TB_INV_TR.app_dept_id
	 * @mbggenerated  Thu Jan 08 11:08:08 CST 2015
	 */
	public String getApp_dept_id() {
		return app_dept_id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_INV_TR.app_dept_id
	 * @param app_dept_id  the value for TB_INV_TR.app_dept_id
	 * @mbggenerated  Thu Jan 08 11:08:08 CST 2015
	 */
	public void setApp_dept_id(String app_dept_id) {
		this.app_dept_id = app_dept_id == null ? null : app_dept_id.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_INV_TR.app_user
	 * @return  the value of TB_INV_TR.app_user
	 * @mbggenerated  Thu Jan 08 11:08:08 CST 2015
	 */
	public String getApp_user() {
		return app_user;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_INV_TR.app_user
	 * @param app_user  the value for TB_INV_TR.app_user
	 * @mbggenerated  Thu Jan 08 11:08:08 CST 2015
	 */
	public void setApp_user(String app_user) {
		this.app_user = app_user == null ? null : app_user.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_INV_TR.app_date
	 * @return  the value of TB_INV_TR.app_date
	 * @mbggenerated  Thu Jan 08 11:08:08 CST 2015
	 */
	public Date getApp_date() {
		return app_date;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_INV_TR.app_date
	 * @param app_date  the value for TB_INV_TR.app_date
	 * @mbggenerated  Thu Jan 08 11:08:08 CST 2015
	 */
	public void setApp_date(Date app_date) {
		this.app_date = app_date;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_INV_TR.status
	 * @return  the value of TB_INV_TR.status
	 * @mbggenerated  Thu Jan 08 11:08:08 CST 2015
	 */
	public Byte getStatus() {
		return status;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_INV_TR.status
	 * @param status  the value for TB_INV_TR.status
	 * @mbggenerated  Thu Jan 08 11:08:08 CST 2015
	 */
	public void setStatus(Byte status) {
		this.status = status;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_INV_TR.if_manual_close
	 * @return  the value of TB_INV_TR.if_manual_close
	 * @mbggenerated  Thu Jan 08 11:08:08 CST 2015
	 */
	public Boolean getIf_manual_close() {
		return if_manual_close;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_INV_TR.if_manual_close
	 * @param if_manual_close  the value for TB_INV_TR.if_manual_close
	 * @mbggenerated  Thu Jan 08 11:08:08 CST 2015
	 */
	public void setIf_manual_close(Boolean if_manual_close) {
		this.if_manual_close = if_manual_close;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_INV_TR.memo
	 * @return  the value of TB_INV_TR.memo
	 * @mbggenerated  Thu Jan 08 11:08:08 CST 2015
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_INV_TR.memo
	 * @param memo  the value for TB_INV_TR.memo
	 * @mbggenerated  Thu Jan 08 11:08:08 CST 2015
	 */
	public void setMemo(String memo) {
		this.memo = memo == null ? null : memo.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_INV_TR.cr_user
	 * @return  the value of TB_INV_TR.cr_user
	 * @mbggenerated  Thu Jan 08 11:08:08 CST 2015
	 */
	public String getCr_user() {
		return cr_user;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_INV_TR.cr_user
	 * @param cr_user  the value for TB_INV_TR.cr_user
	 * @mbggenerated  Thu Jan 08 11:08:08 CST 2015
	 */
	public void setCr_user(String cr_user) {
		this.cr_user = cr_user == null ? null : cr_user.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_INV_TR.cr_time
	 * @return  the value of TB_INV_TR.cr_time
	 * @mbggenerated  Thu Jan 08 11:08:08 CST 2015
	 */
	public Date getCr_time() {
		return cr_time;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_INV_TR.cr_time
	 * @param cr_time  the value for TB_INV_TR.cr_time
	 * @mbggenerated  Thu Jan 08 11:08:08 CST 2015
	 */
	public void setCr_time(Date cr_time) {
		this.cr_time = cr_time;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_INV_TR.md_user
	 * @return  the value of TB_INV_TR.md_user
	 * @mbggenerated  Thu Jan 08 11:08:08 CST 2015
	 */
	public String getMd_user() {
		return md_user;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_INV_TR.md_user
	 * @param md_user  the value for TB_INV_TR.md_user
	 * @mbggenerated  Thu Jan 08 11:08:08 CST 2015
	 */
	public void setMd_user(String md_user) {
		this.md_user = md_user == null ? null : md_user.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_INV_TR.md_time
	 * @return  the value of TB_INV_TR.md_time
	 * @mbggenerated  Thu Jan 08 11:08:08 CST 2015
	 */
	public Date getMd_time() {
		return md_time;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_INV_TR.md_time
	 * @param md_time  the value for TB_INV_TR.md_time
	 * @mbggenerated  Thu Jan 08 11:08:08 CST 2015
	 */
	public void setMd_time(Date md_time) {
		this.md_time = md_time;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_INV_TR.processType
	 * @return  the value of TB_INV_TR.processType
	 * @mbggenerated  Thu Jan 08 11:08:08 CST 2015
	 */
	public String getProcessType() {
		return processType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_INV_TR.processType
	 * @param processType  the value for TB_INV_TR.processType
	 * @mbggenerated  Thu Jan 08 11:08:08 CST 2015
	 */
	public void setProcessType(String processType) {
		this.processType = processType == null ? null : processType.trim();
	}
}