package com.foya.dao.mybatis.model;

import java.util.Date;

public class TbInvBooking {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_INV_BOOKING.booking_no
     *
     * @mbggenerated Tue Nov 18 17:22:10 CST 2014
     */
    private Long booking_no;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_INV_BOOKING.act_no
     *
     * @mbggenerated Tue Nov 18 17:22:10 CST 2014
     */
    private String act_no;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_INV_BOOKING.act_type
     *
     * @mbggenerated Tue Nov 18 17:22:10 CST 2014
     */
    private Byte act_type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_INV_BOOKING.booking_qty
     *
     * @mbggenerated Tue Nov 18 17:22:10 CST 2014
     */
    private Integer booking_qty;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_INV_BOOKING.site_id
     *
     * @mbggenerated Tue Nov 18 17:22:10 CST 2014
     */
    private String site_id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_INV_BOOKING.order_no
     *
     * @mbggenerated Tue Nov 18 17:22:10 CST 2014
     */
    private String order_no;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_INV_BOOKING.domain
     *
     * @mbggenerated Tue Nov 18 17:22:10 CST 2014
     */
    private String domain;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_INV_BOOKING.dept_id
     *
     * @mbggenerated Tue Nov 18 17:22:10 CST 2014
     */
    private String dept_id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_INV_BOOKING.cr_user
     *
     * @mbggenerated Tue Nov 18 17:22:10 CST 2014
     */
    private String cr_user;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_INV_BOOKING.cr_time
     *
     * @mbggenerated Tue Nov 18 17:22:10 CST 2014
     */
    private Date cr_time;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_INV_BOOKING.md_user
     *
     * @mbggenerated Tue Nov 18 17:22:10 CST 2014
     */
    private String md_user;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_INV_BOOKING.md_time
     *
     * @mbggenerated Tue Nov 18 17:22:10 CST 2014
     */
    private Date md_time;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_INV_BOOKING.wh_id
     *
     * @mbggenerated Tue Nov 18 17:22:10 CST 2014
     */
    private String wh_id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_INV_BOOKING.mat_no
     *
     * @mbggenerated Tue Nov 18 17:22:10 CST 2014
     */
    private String mat_no;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_INV_BOOKING.booking_no
     *
     * @return the value of TB_INV_BOOKING.booking_no
     *
     * @mbggenerated Tue Nov 18 17:22:10 CST 2014
     */
    public Long getBooking_no() {
        return booking_no;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_INV_BOOKING.booking_no
     *
     * @param booking_no the value for TB_INV_BOOKING.booking_no
     *
     * @mbggenerated Tue Nov 18 17:22:10 CST 2014
     */
    public void setBooking_no(Long booking_no) {
        this.booking_no = booking_no;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_INV_BOOKING.act_no
     *
     * @return the value of TB_INV_BOOKING.act_no
     *
     * @mbggenerated Tue Nov 18 17:22:10 CST 2014
     */
    public String getAct_no() {
        return act_no;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_INV_BOOKING.act_no
     *
     * @param act_no the value for TB_INV_BOOKING.act_no
     *
     * @mbggenerated Tue Nov 18 17:22:10 CST 2014
     */
    public void setAct_no(String act_no) {
        this.act_no = act_no == null ? null : act_no.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_INV_BOOKING.act_type
     *
     * @return the value of TB_INV_BOOKING.act_type
     *
     * @mbggenerated Tue Nov 18 17:22:10 CST 2014
     */
    public Byte getAct_type() {
        return act_type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_INV_BOOKING.act_type
     *
     * @param act_type the value for TB_INV_BOOKING.act_type
     *
     * @mbggenerated Tue Nov 18 17:22:10 CST 2014
     */
    public void setAct_type(Byte act_type) {
        this.act_type = act_type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_INV_BOOKING.booking_qty
     *
     * @return the value of TB_INV_BOOKING.booking_qty
     *
     * @mbggenerated Tue Nov 18 17:22:10 CST 2014
     */
    public Integer getBooking_qty() {
        return booking_qty;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_INV_BOOKING.booking_qty
     *
     * @param booking_qty the value for TB_INV_BOOKING.booking_qty
     *
     * @mbggenerated Tue Nov 18 17:22:10 CST 2014
     */
    public void setBooking_qty(Integer booking_qty) {
        this.booking_qty = booking_qty;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_INV_BOOKING.site_id
     *
     * @return the value of TB_INV_BOOKING.site_id
     *
     * @mbggenerated Tue Nov 18 17:22:10 CST 2014
     */
    public String getSite_id() {
        return site_id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_INV_BOOKING.site_id
     *
     * @param site_id the value for TB_INV_BOOKING.site_id
     *
     * @mbggenerated Tue Nov 18 17:22:10 CST 2014
     */
    public void setSite_id(String site_id) {
        this.site_id = site_id == null ? null : site_id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_INV_BOOKING.order_no
     *
     * @return the value of TB_INV_BOOKING.order_no
     *
     * @mbggenerated Tue Nov 18 17:22:10 CST 2014
     */
    public String getOrder_no() {
        return order_no;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_INV_BOOKING.order_no
     *
     * @param order_no the value for TB_INV_BOOKING.order_no
     *
     * @mbggenerated Tue Nov 18 17:22:10 CST 2014
     */
    public void setOrder_no(String order_no) {
        this.order_no = order_no == null ? null : order_no.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_INV_BOOKING.domain
     *
     * @return the value of TB_INV_BOOKING.domain
     *
     * @mbggenerated Tue Nov 18 17:22:10 CST 2014
     */
    public String getDomain() {
        return domain;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_INV_BOOKING.domain
     *
     * @param domain the value for TB_INV_BOOKING.domain
     *
     * @mbggenerated Tue Nov 18 17:22:10 CST 2014
     */
    public void setDomain(String domain) {
        this.domain = domain == null ? null : domain.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_INV_BOOKING.dept_id
     *
     * @return the value of TB_INV_BOOKING.dept_id
     *
     * @mbggenerated Tue Nov 18 17:22:10 CST 2014
     */
    public String getDept_id() {
        return dept_id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_INV_BOOKING.dept_id
     *
     * @param dept_id the value for TB_INV_BOOKING.dept_id
     *
     * @mbggenerated Tue Nov 18 17:22:10 CST 2014
     */
    public void setDept_id(String dept_id) {
        this.dept_id = dept_id == null ? null : dept_id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_INV_BOOKING.cr_user
     *
     * @return the value of TB_INV_BOOKING.cr_user
     *
     * @mbggenerated Tue Nov 18 17:22:10 CST 2014
     */
    public String getCr_user() {
        return cr_user;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_INV_BOOKING.cr_user
     *
     * @param cr_user the value for TB_INV_BOOKING.cr_user
     *
     * @mbggenerated Tue Nov 18 17:22:10 CST 2014
     */
    public void setCr_user(String cr_user) {
        this.cr_user = cr_user == null ? null : cr_user.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_INV_BOOKING.cr_time
     *
     * @return the value of TB_INV_BOOKING.cr_time
     *
     * @mbggenerated Tue Nov 18 17:22:10 CST 2014
     */
    public Date getCr_time() {
        return cr_time;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_INV_BOOKING.cr_time
     *
     * @param cr_time the value for TB_INV_BOOKING.cr_time
     *
     * @mbggenerated Tue Nov 18 17:22:10 CST 2014
     */
    public void setCr_time(Date cr_time) {
        this.cr_time = cr_time;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_INV_BOOKING.md_user
     *
     * @return the value of TB_INV_BOOKING.md_user
     *
     * @mbggenerated Tue Nov 18 17:22:10 CST 2014
     */
    public String getMd_user() {
        return md_user;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_INV_BOOKING.md_user
     *
     * @param md_user the value for TB_INV_BOOKING.md_user
     *
     * @mbggenerated Tue Nov 18 17:22:10 CST 2014
     */
    public void setMd_user(String md_user) {
        this.md_user = md_user == null ? null : md_user.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_INV_BOOKING.md_time
     *
     * @return the value of TB_INV_BOOKING.md_time
     *
     * @mbggenerated Tue Nov 18 17:22:10 CST 2014
     */
    public Date getMd_time() {
        return md_time;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_INV_BOOKING.md_time
     *
     * @param md_time the value for TB_INV_BOOKING.md_time
     *
     * @mbggenerated Tue Nov 18 17:22:10 CST 2014
     */
    public void setMd_time(Date md_time) {
        this.md_time = md_time;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_INV_BOOKING.wh_id
     *
     * @return the value of TB_INV_BOOKING.wh_id
     *
     * @mbggenerated Tue Nov 18 17:22:10 CST 2014
     */
    public String getWh_id() {
        return wh_id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_INV_BOOKING.wh_id
     *
     * @param wh_id the value for TB_INV_BOOKING.wh_id
     *
     * @mbggenerated Tue Nov 18 17:22:10 CST 2014
     */
    public void setWh_id(String wh_id) {
        this.wh_id = wh_id == null ? null : wh_id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_INV_BOOKING.mat_no
     *
     * @return the value of TB_INV_BOOKING.mat_no
     *
     * @mbggenerated Tue Nov 18 17:22:10 CST 2014
     */
    public String getMat_no() {
        return mat_no;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_INV_BOOKING.mat_no
     *
     * @param mat_no the value for TB_INV_BOOKING.mat_no
     *
     * @mbggenerated Tue Nov 18 17:22:10 CST 2014
     */
    public void setMat_no(String mat_no) {
        this.mat_no = mat_no == null ? null : mat_no.trim();
    }
}