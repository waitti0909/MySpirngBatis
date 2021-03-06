package com.foya.dao.mybatis.model;

import java.util.Date;

public class TbInvLot {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_INV_LOT.lot_no
     *
     * @mbggenerated Mon Nov 17 10:07:10 CST 2014
     */
    private Long lot_no;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_INV_LOT.mat_no
     *
     * @mbggenerated Mon Nov 17 10:07:10 CST 2014
     */
    private String mat_no;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_INV_LOT.fa_no
     *
     * @mbggenerated Mon Nov 17 10:07:10 CST 2014
     */
    private String fa_no;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_INV_LOT.po_no
     *
     * @mbggenerated Mon Nov 17 10:07:10 CST 2014
     */
    private String po_no;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_INV_LOT.rcv_date
     *
     * @mbggenerated Mon Nov 17 10:07:10 CST 2014
     */
    private Date rcv_date;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_INV_LOT.buying_qty
     *
     * @mbggenerated Mon Nov 17 10:07:10 CST 2014
     */
    private Integer buying_qty;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_INV_LOT.setup_qty
     *
     * @mbggenerated Mon Nov 17 10:07:10 CST 2014
     */
    private Integer setup_qty;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_INV_LOT.discard_qty
     *
     * @mbggenerated Mon Nov 17 10:07:10 CST 2014
     */
    private Integer discard_qty;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_INV_LOT.std_qty
     *
     * @mbggenerated Mon Nov 17 10:07:10 CST 2014
     */
    private Integer std_qty;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_INV_LOT.cr_user
     *
     * @mbggenerated Mon Nov 17 10:07:10 CST 2014
     */
    private String cr_user;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_INV_LOT.cr_time
     *
     * @mbggenerated Mon Nov 17 10:07:10 CST 2014
     */
    private Date cr_time;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_INV_LOT.md_user
     *
     * @mbggenerated Mon Nov 17 10:07:10 CST 2014
     */
    private String md_user;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_INV_LOT.md_time
     *
     * @mbggenerated Mon Nov 17 10:07:10 CST 2014
     */
    private Date md_time;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_INV_LOT.lot_no
     *
     * @return the value of TB_INV_LOT.lot_no
     *
     * @mbggenerated Mon Nov 17 10:07:10 CST 2014
     */
    public Long getLot_no() {
        return lot_no;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_INV_LOT.lot_no
     *
     * @param lot_no the value for TB_INV_LOT.lot_no
     *
     * @mbggenerated Mon Nov 17 10:07:10 CST 2014
     */
    public void setLot_no(Long lot_no) {
        this.lot_no = lot_no;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_INV_LOT.mat_no
     *
     * @return the value of TB_INV_LOT.mat_no
     *
     * @mbggenerated Mon Nov 17 10:07:10 CST 2014
     */
    public String getMat_no() {
        return mat_no;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_INV_LOT.mat_no
     *
     * @param mat_no the value for TB_INV_LOT.mat_no
     *
     * @mbggenerated Mon Nov 17 10:07:10 CST 2014
     */
    public void setMat_no(String mat_no) {
        this.mat_no = mat_no == null ? null : mat_no.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_INV_LOT.fa_no
     *
     * @return the value of TB_INV_LOT.fa_no
     *
     * @mbggenerated Mon Nov 17 10:07:10 CST 2014
     */
    public String getFa_no() {
        return fa_no;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_INV_LOT.fa_no
     *
     * @param fa_no the value for TB_INV_LOT.fa_no
     *
     * @mbggenerated Mon Nov 17 10:07:10 CST 2014
     */
    public void setFa_no(String fa_no) {
        this.fa_no = fa_no == null ? null : fa_no.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_INV_LOT.po_no
     *
     * @return the value of TB_INV_LOT.po_no
     *
     * @mbggenerated Mon Nov 17 10:07:10 CST 2014
     */
    public String getPo_no() {
        return po_no;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_INV_LOT.po_no
     *
     * @param po_no the value for TB_INV_LOT.po_no
     *
     * @mbggenerated Mon Nov 17 10:07:10 CST 2014
     */
    public void setPo_no(String po_no) {
        this.po_no = po_no == null ? null : po_no.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_INV_LOT.rcv_date
     *
     * @return the value of TB_INV_LOT.rcv_date
     *
     * @mbggenerated Mon Nov 17 10:07:10 CST 2014
     */
    public Date getRcv_date() {
        return rcv_date;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_INV_LOT.rcv_date
     *
     * @param rcv_date the value for TB_INV_LOT.rcv_date
     *
     * @mbggenerated Mon Nov 17 10:07:10 CST 2014
     */
    public void setRcv_date(Date rcv_date) {
        this.rcv_date = rcv_date;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_INV_LOT.buying_qty
     *
     * @return the value of TB_INV_LOT.buying_qty
     *
     * @mbggenerated Mon Nov 17 10:07:10 CST 2014
     */
    public Integer getBuying_qty() {
        return buying_qty;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_INV_LOT.buying_qty
     *
     * @param buying_qty the value for TB_INV_LOT.buying_qty
     *
     * @mbggenerated Mon Nov 17 10:07:10 CST 2014
     */
    public void setBuying_qty(Integer buying_qty) {
        this.buying_qty = buying_qty;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_INV_LOT.setup_qty
     *
     * @return the value of TB_INV_LOT.setup_qty
     *
     * @mbggenerated Mon Nov 17 10:07:10 CST 2014
     */
    public Integer getSetup_qty() {
        return setup_qty;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_INV_LOT.setup_qty
     *
     * @param setup_qty the value for TB_INV_LOT.setup_qty
     *
     * @mbggenerated Mon Nov 17 10:07:10 CST 2014
     */
    public void setSetup_qty(Integer setup_qty) {
        this.setup_qty = setup_qty;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_INV_LOT.discard_qty
     *
     * @return the value of TB_INV_LOT.discard_qty
     *
     * @mbggenerated Mon Nov 17 10:07:10 CST 2014
     */
    public Integer getDiscard_qty() {
        return discard_qty;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_INV_LOT.discard_qty
     *
     * @param discard_qty the value for TB_INV_LOT.discard_qty
     *
     * @mbggenerated Mon Nov 17 10:07:10 CST 2014
     */
    public void setDiscard_qty(Integer discard_qty) {
        this.discard_qty = discard_qty;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_INV_LOT.std_qty
     *
     * @return the value of TB_INV_LOT.std_qty
     *
     * @mbggenerated Mon Nov 17 10:07:10 CST 2014
     */
    public Integer getStd_qty() {
        return std_qty;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_INV_LOT.std_qty
     *
     * @param std_qty the value for TB_INV_LOT.std_qty
     *
     * @mbggenerated Mon Nov 17 10:07:10 CST 2014
     */
    public void setStd_qty(Integer std_qty) {
        this.std_qty = std_qty;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_INV_LOT.cr_user
     *
     * @return the value of TB_INV_LOT.cr_user
     *
     * @mbggenerated Mon Nov 17 10:07:10 CST 2014
     */
    public String getCr_user() {
        return cr_user;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_INV_LOT.cr_user
     *
     * @param cr_user the value for TB_INV_LOT.cr_user
     *
     * @mbggenerated Mon Nov 17 10:07:10 CST 2014
     */
    public void setCr_user(String cr_user) {
        this.cr_user = cr_user == null ? null : cr_user.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_INV_LOT.cr_time
     *
     * @return the value of TB_INV_LOT.cr_time
     *
     * @mbggenerated Mon Nov 17 10:07:10 CST 2014
     */
    public Date getCr_time() {
        return cr_time;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_INV_LOT.cr_time
     *
     * @param cr_time the value for TB_INV_LOT.cr_time
     *
     * @mbggenerated Mon Nov 17 10:07:10 CST 2014
     */
    public void setCr_time(Date cr_time) {
        this.cr_time = cr_time;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_INV_LOT.md_user
     *
     * @return the value of TB_INV_LOT.md_user
     *
     * @mbggenerated Mon Nov 17 10:07:10 CST 2014
     */
    public String getMd_user() {
        return md_user;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_INV_LOT.md_user
     *
     * @param md_user the value for TB_INV_LOT.md_user
     *
     * @mbggenerated Mon Nov 17 10:07:10 CST 2014
     */
    public void setMd_user(String md_user) {
        this.md_user = md_user == null ? null : md_user.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_INV_LOT.md_time
     *
     * @return the value of TB_INV_LOT.md_time
     *
     * @mbggenerated Mon Nov 17 10:07:10 CST 2014
     */
    public Date getMd_time() {
        return md_time;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_INV_LOT.md_time
     *
     * @param md_time the value for TB_INV_LOT.md_time
     *
     * @mbggenerated Mon Nov 17 10:07:10 CST 2014
     */
    public void setMd_time(Date md_time) {
        this.md_time = md_time;
    }
}