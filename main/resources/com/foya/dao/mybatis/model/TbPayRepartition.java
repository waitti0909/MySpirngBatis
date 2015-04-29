package com.foya.dao.mybatis.model;

import java.math.BigDecimal;
import java.util.Date;

public class TbPayRepartition {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_PAY_REPARTITION.SEQ_NBR
     *
     * @mbggenerated Mon Feb 09 10:39:20 CST 2015
     */
    private Long SEQ_NBR;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_PAY_REPARTITION.REPARTITION_NO
     *
     * @mbggenerated Mon Feb 09 10:39:20 CST 2015
     */
    private String REPARTITION_NO;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_PAY_REPARTITION.PAYMENT_REQ_NO
     *
     * @mbggenerated Mon Feb 09 10:39:20 CST 2015
     */
    private Long PAYMENT_REQ_NO;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_PAY_REPARTITION.PAYMENT_REQ_ITEM_NO
     *
     * @mbggenerated Mon Feb 09 10:39:20 CST 2015
     */
    private Short PAYMENT_REQ_ITEM_NO;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_PAY_REPARTITION.YEAR_MONTH
     *
     * @mbggenerated Mon Feb 09 10:39:20 CST 2015
     */
    private String YEAR_MONTH;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_PAY_REPARTITION.REPARTITION_AMT
     *
     * @mbggenerated Mon Feb 09 10:39:20 CST 2015
     */
    private BigDecimal REPARTITION_AMT;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_PAY_REPARTITION.SITE_ID
     *
     * @mbggenerated Mon Feb 09 10:39:20 CST 2015
     */
    private String SITE_ID;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_PAY_REPARTITION.EXP_TYPE
     *
     * @mbggenerated Mon Feb 09 10:39:20 CST 2015
     */
    private String EXP_TYPE;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_PAY_REPARTITION.CR_USER
     *
     * @mbggenerated Mon Feb 09 10:39:20 CST 2015
     */
    private String CR_USER;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_PAY_REPARTITION.CR_TIME
     *
     * @mbggenerated Mon Feb 09 10:39:20 CST 2015
     */
    private Date CR_TIME;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_PAY_REPARTITION.MD_USER
     *
     * @mbggenerated Mon Feb 09 10:39:20 CST 2015
     */
    private String MD_USER;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_PAY_REPARTITION.MD_TIME
     *
     * @mbggenerated Mon Feb 09 10:39:20 CST 2015
     */
    private Date MD_TIME;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_PAY_REPARTITION.LOCATION_ID
     *
     * @mbggenerated Mon Feb 09 10:39:20 CST 2015
     */
    private String LOCATION_ID;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_PAY_REPARTITION.PAYMENT_SEQ_NBR
     *
     * @mbggenerated Mon Feb 09 10:39:20 CST 2015
     */
    private Long PAYMENT_SEQ_NBR;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_PAY_REPARTITION.SEQ_NBR
     *
     * @return the value of TB_PAY_REPARTITION.SEQ_NBR
     *
     * @mbggenerated Mon Feb 09 10:39:20 CST 2015
     */
    public Long getSEQ_NBR() {
        return SEQ_NBR;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_PAY_REPARTITION.SEQ_NBR
     *
     * @param SEQ_NBR the value for TB_PAY_REPARTITION.SEQ_NBR
     *
     * @mbggenerated Mon Feb 09 10:39:20 CST 2015
     */
    public void setSEQ_NBR(Long SEQ_NBR) {
        this.SEQ_NBR = SEQ_NBR;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_PAY_REPARTITION.REPARTITION_NO
     *
     * @return the value of TB_PAY_REPARTITION.REPARTITION_NO
     *
     * @mbggenerated Mon Feb 09 10:39:20 CST 2015
     */
    public String getREPARTITION_NO() {
        return REPARTITION_NO;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_PAY_REPARTITION.REPARTITION_NO
     *
     * @param REPARTITION_NO the value for TB_PAY_REPARTITION.REPARTITION_NO
     *
     * @mbggenerated Mon Feb 09 10:39:20 CST 2015
     */
    public void setREPARTITION_NO(String REPARTITION_NO) {
        this.REPARTITION_NO = REPARTITION_NO == null ? null : REPARTITION_NO.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_PAY_REPARTITION.PAYMENT_REQ_NO
     *
     * @return the value of TB_PAY_REPARTITION.PAYMENT_REQ_NO
     *
     * @mbggenerated Mon Feb 09 10:39:20 CST 2015
     */
    public Long getPAYMENT_REQ_NO() {
        return PAYMENT_REQ_NO;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_PAY_REPARTITION.PAYMENT_REQ_NO
     *
     * @param PAYMENT_REQ_NO the value for TB_PAY_REPARTITION.PAYMENT_REQ_NO
     *
     * @mbggenerated Mon Feb 09 10:39:20 CST 2015
     */
    public void setPAYMENT_REQ_NO(Long PAYMENT_REQ_NO) {
        this.PAYMENT_REQ_NO = PAYMENT_REQ_NO;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_PAY_REPARTITION.PAYMENT_REQ_ITEM_NO
     *
     * @return the value of TB_PAY_REPARTITION.PAYMENT_REQ_ITEM_NO
     *
     * @mbggenerated Mon Feb 09 10:39:20 CST 2015
     */
    public Short getPAYMENT_REQ_ITEM_NO() {
        return PAYMENT_REQ_ITEM_NO;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_PAY_REPARTITION.PAYMENT_REQ_ITEM_NO
     *
     * @param PAYMENT_REQ_ITEM_NO the value for TB_PAY_REPARTITION.PAYMENT_REQ_ITEM_NO
     *
     * @mbggenerated Mon Feb 09 10:39:20 CST 2015
     */
    public void setPAYMENT_REQ_ITEM_NO(Short PAYMENT_REQ_ITEM_NO) {
        this.PAYMENT_REQ_ITEM_NO = PAYMENT_REQ_ITEM_NO;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_PAY_REPARTITION.YEAR_MONTH
     *
     * @return the value of TB_PAY_REPARTITION.YEAR_MONTH
     *
     * @mbggenerated Mon Feb 09 10:39:20 CST 2015
     */
    public String getYEAR_MONTH() {
        return YEAR_MONTH;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_PAY_REPARTITION.YEAR_MONTH
     *
     * @param YEAR_MONTH the value for TB_PAY_REPARTITION.YEAR_MONTH
     *
     * @mbggenerated Mon Feb 09 10:39:20 CST 2015
     */
    public void setYEAR_MONTH(String YEAR_MONTH) {
        this.YEAR_MONTH = YEAR_MONTH == null ? null : YEAR_MONTH.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_PAY_REPARTITION.REPARTITION_AMT
     *
     * @return the value of TB_PAY_REPARTITION.REPARTITION_AMT
     *
     * @mbggenerated Mon Feb 09 10:39:20 CST 2015
     */
    public BigDecimal getREPARTITION_AMT() {
        return REPARTITION_AMT;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_PAY_REPARTITION.REPARTITION_AMT
     *
     * @param REPARTITION_AMT the value for TB_PAY_REPARTITION.REPARTITION_AMT
     *
     * @mbggenerated Mon Feb 09 10:39:20 CST 2015
     */
    public void setREPARTITION_AMT(BigDecimal REPARTITION_AMT) {
        this.REPARTITION_AMT = REPARTITION_AMT;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_PAY_REPARTITION.SITE_ID
     *
     * @return the value of TB_PAY_REPARTITION.SITE_ID
     *
     * @mbggenerated Mon Feb 09 10:39:20 CST 2015
     */
    public String getSITE_ID() {
        return SITE_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_PAY_REPARTITION.SITE_ID
     *
     * @param SITE_ID the value for TB_PAY_REPARTITION.SITE_ID
     *
     * @mbggenerated Mon Feb 09 10:39:20 CST 2015
     */
    public void setSITE_ID(String SITE_ID) {
        this.SITE_ID = SITE_ID == null ? null : SITE_ID.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_PAY_REPARTITION.EXP_TYPE
     *
     * @return the value of TB_PAY_REPARTITION.EXP_TYPE
     *
     * @mbggenerated Mon Feb 09 10:39:20 CST 2015
     */
    public String getEXP_TYPE() {
        return EXP_TYPE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_PAY_REPARTITION.EXP_TYPE
     *
     * @param EXP_TYPE the value for TB_PAY_REPARTITION.EXP_TYPE
     *
     * @mbggenerated Mon Feb 09 10:39:20 CST 2015
     */
    public void setEXP_TYPE(String EXP_TYPE) {
        this.EXP_TYPE = EXP_TYPE == null ? null : EXP_TYPE.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_PAY_REPARTITION.CR_USER
     *
     * @return the value of TB_PAY_REPARTITION.CR_USER
     *
     * @mbggenerated Mon Feb 09 10:39:20 CST 2015
     */
    public String getCR_USER() {
        return CR_USER;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_PAY_REPARTITION.CR_USER
     *
     * @param CR_USER the value for TB_PAY_REPARTITION.CR_USER
     *
     * @mbggenerated Mon Feb 09 10:39:20 CST 2015
     */
    public void setCR_USER(String CR_USER) {
        this.CR_USER = CR_USER == null ? null : CR_USER.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_PAY_REPARTITION.CR_TIME
     *
     * @return the value of TB_PAY_REPARTITION.CR_TIME
     *
     * @mbggenerated Mon Feb 09 10:39:20 CST 2015
     */
    public Date getCR_TIME() {
        return CR_TIME;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_PAY_REPARTITION.CR_TIME
     *
     * @param CR_TIME the value for TB_PAY_REPARTITION.CR_TIME
     *
     * @mbggenerated Mon Feb 09 10:39:20 CST 2015
     */
    public void setCR_TIME(Date CR_TIME) {
        this.CR_TIME = CR_TIME;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_PAY_REPARTITION.MD_USER
     *
     * @return the value of TB_PAY_REPARTITION.MD_USER
     *
     * @mbggenerated Mon Feb 09 10:39:20 CST 2015
     */
    public String getMD_USER() {
        return MD_USER;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_PAY_REPARTITION.MD_USER
     *
     * @param MD_USER the value for TB_PAY_REPARTITION.MD_USER
     *
     * @mbggenerated Mon Feb 09 10:39:20 CST 2015
     */
    public void setMD_USER(String MD_USER) {
        this.MD_USER = MD_USER == null ? null : MD_USER.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_PAY_REPARTITION.MD_TIME
     *
     * @return the value of TB_PAY_REPARTITION.MD_TIME
     *
     * @mbggenerated Mon Feb 09 10:39:20 CST 2015
     */
    public Date getMD_TIME() {
        return MD_TIME;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_PAY_REPARTITION.MD_TIME
     *
     * @param MD_TIME the value for TB_PAY_REPARTITION.MD_TIME
     *
     * @mbggenerated Mon Feb 09 10:39:20 CST 2015
     */
    public void setMD_TIME(Date MD_TIME) {
        this.MD_TIME = MD_TIME;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_PAY_REPARTITION.LOCATION_ID
     *
     * @return the value of TB_PAY_REPARTITION.LOCATION_ID
     *
     * @mbggenerated Mon Feb 09 10:39:20 CST 2015
     */
    public String getLOCATION_ID() {
        return LOCATION_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_PAY_REPARTITION.LOCATION_ID
     *
     * @param LOCATION_ID the value for TB_PAY_REPARTITION.LOCATION_ID
     *
     * @mbggenerated Mon Feb 09 10:39:20 CST 2015
     */
    public void setLOCATION_ID(String LOCATION_ID) {
        this.LOCATION_ID = LOCATION_ID == null ? null : LOCATION_ID.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_PAY_REPARTITION.PAYMENT_SEQ_NBR
     *
     * @return the value of TB_PAY_REPARTITION.PAYMENT_SEQ_NBR
     *
     * @mbggenerated Mon Feb 09 10:39:20 CST 2015
     */
    public Long getPAYMENT_SEQ_NBR() {
        return PAYMENT_SEQ_NBR;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_PAY_REPARTITION.PAYMENT_SEQ_NBR
     *
     * @param PAYMENT_SEQ_NBR the value for TB_PAY_REPARTITION.PAYMENT_SEQ_NBR
     *
     * @mbggenerated Mon Feb 09 10:39:20 CST 2015
     */
    public void setPAYMENT_SEQ_NBR(Long PAYMENT_SEQ_NBR) {
        this.PAYMENT_SEQ_NBR = PAYMENT_SEQ_NBR;
    }
}