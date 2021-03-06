package com.foya.dao.mybatis.model;

import java.math.BigDecimal;
import java.util.Date;

public class TbLsSignCond {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_LS_SIGN_COND.COND_TYPE
     *
     * @mbggenerated Thu Dec 25 17:35:48 CST 2014
     */
    private String COND_TYPE;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_LS_SIGN_COND.VGM_SIGN
     *
     * @mbggenerated Thu Dec 25 17:35:48 CST 2014
     */
    private String VGM_SIGN;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_LS_SIGN_COND.GM_SIGN
     *
     * @mbggenerated Thu Dec 25 17:35:48 CST 2014
     */
    private String GM_SIGN;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_LS_SIGN_COND.COND_START
     *
     * @mbggenerated Thu Dec 25 17:35:48 CST 2014
     */
    private BigDecimal COND_START;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_LS_SIGN_COND.COND_END
     *
     * @mbggenerated Thu Dec 25 17:35:48 CST 2014
     */
    private BigDecimal COND_END;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_LS_SIGN_COND.DOMAIN_LVL
     *
     * @mbggenerated Thu Dec 25 17:35:48 CST 2014
     */
    private String DOMAIN_LVL;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_LS_SIGN_COND.CR_USER
     *
     * @mbggenerated Thu Dec 25 17:35:48 CST 2014
     */
    private String CR_USER;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_LS_SIGN_COND.CR_TIME
     *
     * @mbggenerated Thu Dec 25 17:35:48 CST 2014
     */
    private Date CR_TIME;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_LS_SIGN_COND.MD_USER
     *
     * @mbggenerated Thu Dec 25 17:35:48 CST 2014
     */
    private String MD_USER;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_LS_SIGN_COND.MD_TIME
     *
     * @mbggenerated Thu Dec 25 17:35:48 CST 2014
     */
    private Date MD_TIME;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_LS_SIGN_COND.COND_TYPE
     *
     * @return the value of TB_LS_SIGN_COND.COND_TYPE
     *
     * @mbggenerated Thu Dec 25 17:35:48 CST 2014
     */
    public String getCOND_TYPE() {
        return COND_TYPE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_LS_SIGN_COND.COND_TYPE
     *
     * @param COND_TYPE the value for TB_LS_SIGN_COND.COND_TYPE
     *
     * @mbggenerated Thu Dec 25 17:35:48 CST 2014
     */
    public void setCOND_TYPE(String COND_TYPE) {
        this.COND_TYPE = COND_TYPE == null ? null : COND_TYPE.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_LS_SIGN_COND.VGM_SIGN
     *
     * @return the value of TB_LS_SIGN_COND.VGM_SIGN
     *
     * @mbggenerated Thu Dec 25 17:35:48 CST 2014
     */
    public String getVGM_SIGN() {
        return VGM_SIGN;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_LS_SIGN_COND.VGM_SIGN
     *
     * @param VGM_SIGN the value for TB_LS_SIGN_COND.VGM_SIGN
     *
     * @mbggenerated Thu Dec 25 17:35:48 CST 2014
     */
    public void setVGM_SIGN(String VGM_SIGN) {
        this.VGM_SIGN = VGM_SIGN == null ? null : VGM_SIGN.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_LS_SIGN_COND.GM_SIGN
     *
     * @return the value of TB_LS_SIGN_COND.GM_SIGN
     *
     * @mbggenerated Thu Dec 25 17:35:48 CST 2014
     */
    public String getGM_SIGN() {
        return GM_SIGN;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_LS_SIGN_COND.GM_SIGN
     *
     * @param GM_SIGN the value for TB_LS_SIGN_COND.GM_SIGN
     *
     * @mbggenerated Thu Dec 25 17:35:48 CST 2014
     */
    public void setGM_SIGN(String GM_SIGN) {
        this.GM_SIGN = GM_SIGN == null ? null : GM_SIGN.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_LS_SIGN_COND.COND_START
     *
     * @return the value of TB_LS_SIGN_COND.COND_START
     *
     * @mbggenerated Thu Dec 25 17:35:48 CST 2014
     */
    public BigDecimal getCOND_START() {
        return COND_START;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_LS_SIGN_COND.COND_START
     *
     * @param COND_START the value for TB_LS_SIGN_COND.COND_START
     *
     * @mbggenerated Thu Dec 25 17:35:48 CST 2014
     */
    public void setCOND_START(BigDecimal COND_START) {
        this.COND_START = COND_START;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_LS_SIGN_COND.COND_END
     *
     * @return the value of TB_LS_SIGN_COND.COND_END
     *
     * @mbggenerated Thu Dec 25 17:35:48 CST 2014
     */
    public BigDecimal getCOND_END() {
        return COND_END;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_LS_SIGN_COND.COND_END
     *
     * @param COND_END the value for TB_LS_SIGN_COND.COND_END
     *
     * @mbggenerated Thu Dec 25 17:35:48 CST 2014
     */
    public void setCOND_END(BigDecimal COND_END) {
        this.COND_END = COND_END;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_LS_SIGN_COND.DOMAIN_LVL
     *
     * @return the value of TB_LS_SIGN_COND.DOMAIN_LVL
     *
     * @mbggenerated Thu Dec 25 17:35:48 CST 2014
     */
    public String getDOMAIN_LVL() {
        return DOMAIN_LVL;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_LS_SIGN_COND.DOMAIN_LVL
     *
     * @param DOMAIN_LVL the value for TB_LS_SIGN_COND.DOMAIN_LVL
     *
     * @mbggenerated Thu Dec 25 17:35:48 CST 2014
     */
    public void setDOMAIN_LVL(String DOMAIN_LVL) {
        this.DOMAIN_LVL = DOMAIN_LVL == null ? null : DOMAIN_LVL.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_LS_SIGN_COND.CR_USER
     *
     * @return the value of TB_LS_SIGN_COND.CR_USER
     *
     * @mbggenerated Thu Dec 25 17:35:48 CST 2014
     */
    public String getCR_USER() {
        return CR_USER;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_LS_SIGN_COND.CR_USER
     *
     * @param CR_USER the value for TB_LS_SIGN_COND.CR_USER
     *
     * @mbggenerated Thu Dec 25 17:35:48 CST 2014
     */
    public void setCR_USER(String CR_USER) {
        this.CR_USER = CR_USER == null ? null : CR_USER.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_LS_SIGN_COND.CR_TIME
     *
     * @return the value of TB_LS_SIGN_COND.CR_TIME
     *
     * @mbggenerated Thu Dec 25 17:35:48 CST 2014
     */
    public Date getCR_TIME() {
        return CR_TIME;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_LS_SIGN_COND.CR_TIME
     *
     * @param CR_TIME the value for TB_LS_SIGN_COND.CR_TIME
     *
     * @mbggenerated Thu Dec 25 17:35:48 CST 2014
     */
    public void setCR_TIME(Date CR_TIME) {
        this.CR_TIME = CR_TIME;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_LS_SIGN_COND.MD_USER
     *
     * @return the value of TB_LS_SIGN_COND.MD_USER
     *
     * @mbggenerated Thu Dec 25 17:35:48 CST 2014
     */
    public String getMD_USER() {
        return MD_USER;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_LS_SIGN_COND.MD_USER
     *
     * @param MD_USER the value for TB_LS_SIGN_COND.MD_USER
     *
     * @mbggenerated Thu Dec 25 17:35:48 CST 2014
     */
    public void setMD_USER(String MD_USER) {
        this.MD_USER = MD_USER == null ? null : MD_USER.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_LS_SIGN_COND.MD_TIME
     *
     * @return the value of TB_LS_SIGN_COND.MD_TIME
     *
     * @mbggenerated Thu Dec 25 17:35:48 CST 2014
     */
    public Date getMD_TIME() {
        return MD_TIME;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_LS_SIGN_COND.MD_TIME
     *
     * @param MD_TIME the value for TB_LS_SIGN_COND.MD_TIME
     *
     * @mbggenerated Thu Dec 25 17:35:48 CST 2014
     */
    public void setMD_TIME(Date MD_TIME) {
        this.MD_TIME = MD_TIME;
    }
}