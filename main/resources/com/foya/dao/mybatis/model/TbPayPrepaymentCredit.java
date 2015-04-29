package com.foya.dao.mybatis.model;

import java.math.BigDecimal;
import java.util.Date;

public class TbPayPrepaymentCredit {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_PAY_PREPAYMENT_CREDIT.MST_SEQ_NBR
     *
     * @mbggenerated Thu Feb 26 17:21:46 CST 2015
     */
    private Long MST_SEQ_NBR;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_PAY_PREPAYMENT_CREDIT.PAYMENT_REQ_NO
     *
     * @mbggenerated Thu Feb 26 17:21:46 CST 2015
     */
    private Long PAYMENT_REQ_NO;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_PAY_PREPAYMENT_CREDIT.PAYMENT_REQ_ITEM_NO
     *
     * @mbggenerated Thu Feb 26 17:21:46 CST 2015
     */
    private Short PAYMENT_REQ_ITEM_NO;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_PAY_PREPAYMENT_CREDIT.CREDIT_AMT
     *
     * @mbggenerated Thu Feb 26 17:21:46 CST 2015
     */
    private BigDecimal CREDIT_AMT;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_PAY_PREPAYMENT_CREDIT.CREDIT_YEAR_MONTH
     *
     * @mbggenerated Thu Feb 26 17:21:46 CST 2015
     */
    private String CREDIT_YEAR_MONTH;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_PAY_PREPAYMENT_CREDIT.CREDIT_STATUS
     *
     * @mbggenerated Thu Feb 26 17:21:46 CST 2015
     */
    private String CREDIT_STATUS;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_PAY_PREPAYMENT_CREDIT.CR_USER
     *
     * @mbggenerated Thu Feb 26 17:21:46 CST 2015
     */
    private String CR_USER;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_PAY_PREPAYMENT_CREDIT.CR_TIME
     *
     * @mbggenerated Thu Feb 26 17:21:46 CST 2015
     */
    private Date CR_TIME;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_PAY_PREPAYMENT_CREDIT.MD_USER
     *
     * @mbggenerated Thu Feb 26 17:21:46 CST 2015
     */
    private String MD_USER;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_PAY_PREPAYMENT_CREDIT.MD_TIME
     *
     * @mbggenerated Thu Feb 26 17:21:46 CST 2015
     */
    private Date MD_TIME;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_PAY_PREPAYMENT_CREDIT.BUSINESS_TAX
     *
     * @mbggenerated Thu Feb 26 17:21:46 CST 2015
     */
    private BigDecimal BUSINESS_TAX;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_PAY_PREPAYMENT_CREDIT.INCOME_TAX
     *
     * @mbggenerated Thu Feb 26 17:21:46 CST 2015
     */
    private BigDecimal INCOME_TAX;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_PAY_PREPAYMENT_CREDIT.NHI_FEE
     *
     * @mbggenerated Thu Feb 26 17:21:46 CST 2015
     */
    private BigDecimal NHI_FEE;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_PAY_PREPAYMENT_CREDIT.MST_SEQ_NBR
     *
     * @return the value of TB_PAY_PREPAYMENT_CREDIT.MST_SEQ_NBR
     *
     * @mbggenerated Thu Feb 26 17:21:46 CST 2015
     */
    public Long getMST_SEQ_NBR() {
        return MST_SEQ_NBR;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_PAY_PREPAYMENT_CREDIT.MST_SEQ_NBR
     *
     * @param MST_SEQ_NBR the value for TB_PAY_PREPAYMENT_CREDIT.MST_SEQ_NBR
     *
     * @mbggenerated Thu Feb 26 17:21:46 CST 2015
     */
    public void setMST_SEQ_NBR(Long MST_SEQ_NBR) {
        this.MST_SEQ_NBR = MST_SEQ_NBR;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_PAY_PREPAYMENT_CREDIT.PAYMENT_REQ_NO
     *
     * @return the value of TB_PAY_PREPAYMENT_CREDIT.PAYMENT_REQ_NO
     *
     * @mbggenerated Thu Feb 26 17:21:46 CST 2015
     */
    public Long getPAYMENT_REQ_NO() {
        return PAYMENT_REQ_NO;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_PAY_PREPAYMENT_CREDIT.PAYMENT_REQ_NO
     *
     * @param PAYMENT_REQ_NO the value for TB_PAY_PREPAYMENT_CREDIT.PAYMENT_REQ_NO
     *
     * @mbggenerated Thu Feb 26 17:21:46 CST 2015
     */
    public void setPAYMENT_REQ_NO(Long PAYMENT_REQ_NO) {
        this.PAYMENT_REQ_NO = PAYMENT_REQ_NO;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_PAY_PREPAYMENT_CREDIT.PAYMENT_REQ_ITEM_NO
     *
     * @return the value of TB_PAY_PREPAYMENT_CREDIT.PAYMENT_REQ_ITEM_NO
     *
     * @mbggenerated Thu Feb 26 17:21:46 CST 2015
     */
    public Short getPAYMENT_REQ_ITEM_NO() {
        return PAYMENT_REQ_ITEM_NO;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_PAY_PREPAYMENT_CREDIT.PAYMENT_REQ_ITEM_NO
     *
     * @param PAYMENT_REQ_ITEM_NO the value for TB_PAY_PREPAYMENT_CREDIT.PAYMENT_REQ_ITEM_NO
     *
     * @mbggenerated Thu Feb 26 17:21:46 CST 2015
     */
    public void setPAYMENT_REQ_ITEM_NO(Short PAYMENT_REQ_ITEM_NO) {
        this.PAYMENT_REQ_ITEM_NO = PAYMENT_REQ_ITEM_NO;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_PAY_PREPAYMENT_CREDIT.CREDIT_AMT
     *
     * @return the value of TB_PAY_PREPAYMENT_CREDIT.CREDIT_AMT
     *
     * @mbggenerated Thu Feb 26 17:21:46 CST 2015
     */
    public BigDecimal getCREDIT_AMT() {
        return CREDIT_AMT;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_PAY_PREPAYMENT_CREDIT.CREDIT_AMT
     *
     * @param CREDIT_AMT the value for TB_PAY_PREPAYMENT_CREDIT.CREDIT_AMT
     *
     * @mbggenerated Thu Feb 26 17:21:46 CST 2015
     */
    public void setCREDIT_AMT(BigDecimal CREDIT_AMT) {
        this.CREDIT_AMT = CREDIT_AMT;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_PAY_PREPAYMENT_CREDIT.CREDIT_YEAR_MONTH
     *
     * @return the value of TB_PAY_PREPAYMENT_CREDIT.CREDIT_YEAR_MONTH
     *
     * @mbggenerated Thu Feb 26 17:21:46 CST 2015
     */
    public String getCREDIT_YEAR_MONTH() {
        return CREDIT_YEAR_MONTH;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_PAY_PREPAYMENT_CREDIT.CREDIT_YEAR_MONTH
     *
     * @param CREDIT_YEAR_MONTH the value for TB_PAY_PREPAYMENT_CREDIT.CREDIT_YEAR_MONTH
     *
     * @mbggenerated Thu Feb 26 17:21:46 CST 2015
     */
    public void setCREDIT_YEAR_MONTH(String CREDIT_YEAR_MONTH) {
        this.CREDIT_YEAR_MONTH = CREDIT_YEAR_MONTH == null ? null : CREDIT_YEAR_MONTH.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_PAY_PREPAYMENT_CREDIT.CREDIT_STATUS
     *
     * @return the value of TB_PAY_PREPAYMENT_CREDIT.CREDIT_STATUS
     *
     * @mbggenerated Thu Feb 26 17:21:46 CST 2015
     */
    public String getCREDIT_STATUS() {
        return CREDIT_STATUS;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_PAY_PREPAYMENT_CREDIT.CREDIT_STATUS
     *
     * @param CREDIT_STATUS the value for TB_PAY_PREPAYMENT_CREDIT.CREDIT_STATUS
     *
     * @mbggenerated Thu Feb 26 17:21:46 CST 2015
     */
    public void setCREDIT_STATUS(String CREDIT_STATUS) {
        this.CREDIT_STATUS = CREDIT_STATUS == null ? null : CREDIT_STATUS.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_PAY_PREPAYMENT_CREDIT.CR_USER
     *
     * @return the value of TB_PAY_PREPAYMENT_CREDIT.CR_USER
     *
     * @mbggenerated Thu Feb 26 17:21:46 CST 2015
     */
    public String getCR_USER() {
        return CR_USER;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_PAY_PREPAYMENT_CREDIT.CR_USER
     *
     * @param CR_USER the value for TB_PAY_PREPAYMENT_CREDIT.CR_USER
     *
     * @mbggenerated Thu Feb 26 17:21:46 CST 2015
     */
    public void setCR_USER(String CR_USER) {
        this.CR_USER = CR_USER == null ? null : CR_USER.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_PAY_PREPAYMENT_CREDIT.CR_TIME
     *
     * @return the value of TB_PAY_PREPAYMENT_CREDIT.CR_TIME
     *
     * @mbggenerated Thu Feb 26 17:21:46 CST 2015
     */
    public Date getCR_TIME() {
        return CR_TIME;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_PAY_PREPAYMENT_CREDIT.CR_TIME
     *
     * @param CR_TIME the value for TB_PAY_PREPAYMENT_CREDIT.CR_TIME
     *
     * @mbggenerated Thu Feb 26 17:21:46 CST 2015
     */
    public void setCR_TIME(Date CR_TIME) {
        this.CR_TIME = CR_TIME;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_PAY_PREPAYMENT_CREDIT.MD_USER
     *
     * @return the value of TB_PAY_PREPAYMENT_CREDIT.MD_USER
     *
     * @mbggenerated Thu Feb 26 17:21:46 CST 2015
     */
    public String getMD_USER() {
        return MD_USER;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_PAY_PREPAYMENT_CREDIT.MD_USER
     *
     * @param MD_USER the value for TB_PAY_PREPAYMENT_CREDIT.MD_USER
     *
     * @mbggenerated Thu Feb 26 17:21:46 CST 2015
     */
    public void setMD_USER(String MD_USER) {
        this.MD_USER = MD_USER == null ? null : MD_USER.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_PAY_PREPAYMENT_CREDIT.MD_TIME
     *
     * @return the value of TB_PAY_PREPAYMENT_CREDIT.MD_TIME
     *
     * @mbggenerated Thu Feb 26 17:21:46 CST 2015
     */
    public Date getMD_TIME() {
        return MD_TIME;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_PAY_PREPAYMENT_CREDIT.MD_TIME
     *
     * @param MD_TIME the value for TB_PAY_PREPAYMENT_CREDIT.MD_TIME
     *
     * @mbggenerated Thu Feb 26 17:21:46 CST 2015
     */
    public void setMD_TIME(Date MD_TIME) {
        this.MD_TIME = MD_TIME;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_PAY_PREPAYMENT_CREDIT.BUSINESS_TAX
     *
     * @return the value of TB_PAY_PREPAYMENT_CREDIT.BUSINESS_TAX
     *
     * @mbggenerated Thu Feb 26 17:21:46 CST 2015
     */
    public BigDecimal getBUSINESS_TAX() {
        return BUSINESS_TAX;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_PAY_PREPAYMENT_CREDIT.BUSINESS_TAX
     *
     * @param BUSINESS_TAX the value for TB_PAY_PREPAYMENT_CREDIT.BUSINESS_TAX
     *
     * @mbggenerated Thu Feb 26 17:21:46 CST 2015
     */
    public void setBUSINESS_TAX(BigDecimal BUSINESS_TAX) {
        this.BUSINESS_TAX = BUSINESS_TAX;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_PAY_PREPAYMENT_CREDIT.INCOME_TAX
     *
     * @return the value of TB_PAY_PREPAYMENT_CREDIT.INCOME_TAX
     *
     * @mbggenerated Thu Feb 26 17:21:46 CST 2015
     */
    public BigDecimal getINCOME_TAX() {
        return INCOME_TAX;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_PAY_PREPAYMENT_CREDIT.INCOME_TAX
     *
     * @param INCOME_TAX the value for TB_PAY_PREPAYMENT_CREDIT.INCOME_TAX
     *
     * @mbggenerated Thu Feb 26 17:21:46 CST 2015
     */
    public void setINCOME_TAX(BigDecimal INCOME_TAX) {
        this.INCOME_TAX = INCOME_TAX;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_PAY_PREPAYMENT_CREDIT.NHI_FEE
     *
     * @return the value of TB_PAY_PREPAYMENT_CREDIT.NHI_FEE
     *
     * @mbggenerated Thu Feb 26 17:21:46 CST 2015
     */
    public BigDecimal getNHI_FEE() {
        return NHI_FEE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_PAY_PREPAYMENT_CREDIT.NHI_FEE
     *
     * @param NHI_FEE the value for TB_PAY_PREPAYMENT_CREDIT.NHI_FEE
     *
     * @mbggenerated Thu Feb 26 17:21:46 CST 2015
     */
    public void setNHI_FEE(BigDecimal NHI_FEE) {
        this.NHI_FEE = NHI_FEE;
    }
}