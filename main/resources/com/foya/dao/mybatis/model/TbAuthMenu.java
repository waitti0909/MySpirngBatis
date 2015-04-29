package com.foya.dao.mybatis.model;

import java.math.BigDecimal;
import java.util.Date;

public class TbAuthMenu {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_AUTH_MENU.MENU_ID
     *
     * @mbggenerated Mon Jul 28 17:13:27 CST 2014
     */
    private BigDecimal MENU_ID;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_AUTH_MENU.MENU_NAME
     *
     * @mbggenerated Mon Jul 28 17:13:27 CST 2014
     */
    private String MENU_NAME;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_AUTH_MENU.MENU_DESC
     *
     * @mbggenerated Mon Jul 28 17:13:27 CST 2014
     */
    private String MENU_DESC;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_AUTH_MENU.MENU_SORT
     *
     * @mbggenerated Mon Jul 28 17:13:27 CST 2014
     */
    private Integer MENU_SORT;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_AUTH_MENU.IS_FODR
     *
     * @mbggenerated Mon Jul 28 17:13:27 CST 2014
     */
    private String IS_FODR;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_AUTH_MENU.IS_USE
     *
     * @mbggenerated Mon Jul 28 17:13:27 CST 2014
     */
    private String IS_USE;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_AUTH_MENU.PARENT_ID
     *
     * @mbggenerated Mon Jul 28 17:13:27 CST 2014
     */
    private BigDecimal PARENT_ID;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_AUTH_MENU.PGM_PATH
     *
     * @mbggenerated Mon Jul 28 17:13:27 CST 2014
     */
    private String PGM_PATH;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_AUTH_MENU.CR_USER
     *
     * @mbggenerated Mon Jul 28 17:13:27 CST 2014
     */
    private String CR_USER;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_AUTH_MENU.CR_TIME
     *
     * @mbggenerated Mon Jul 28 17:13:27 CST 2014
     */
    private Date CR_TIME;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_AUTH_MENU.MD_USER
     *
     * @mbggenerated Mon Jul 28 17:13:27 CST 2014
     */
    private String MD_USER;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_AUTH_MENU.MD_TIME
     *
     * @mbggenerated Mon Jul 28 17:13:27 CST 2014
     */
    private Date MD_TIME;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_AUTH_MENU.MENU_ID
     *
     * @return the value of TB_AUTH_MENU.MENU_ID
     *
     * @mbggenerated Mon Jul 28 17:13:27 CST 2014
     */
    public BigDecimal getMENU_ID() {
        return MENU_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_AUTH_MENU.MENU_ID
     *
     * @param MENU_ID the value for TB_AUTH_MENU.MENU_ID
     *
     * @mbggenerated Mon Jul 28 17:13:27 CST 2014
     */
    public void setMENU_ID(BigDecimal MENU_ID) {
        this.MENU_ID = MENU_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_AUTH_MENU.MENU_NAME
     *
     * @return the value of TB_AUTH_MENU.MENU_NAME
     *
     * @mbggenerated Mon Jul 28 17:13:27 CST 2014
     */
    public String getMENU_NAME() {
        return MENU_NAME;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_AUTH_MENU.MENU_NAME
     *
     * @param MENU_NAME the value for TB_AUTH_MENU.MENU_NAME
     *
     * @mbggenerated Mon Jul 28 17:13:27 CST 2014
     */
    public void setMENU_NAME(String MENU_NAME) {
        this.MENU_NAME = MENU_NAME == null ? null : MENU_NAME.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_AUTH_MENU.MENU_DESC
     *
     * @return the value of TB_AUTH_MENU.MENU_DESC
     *
     * @mbggenerated Mon Jul 28 17:13:27 CST 2014
     */
    public String getMENU_DESC() {
        return MENU_DESC;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_AUTH_MENU.MENU_DESC
     *
     * @param MENU_DESC the value for TB_AUTH_MENU.MENU_DESC
     *
     * @mbggenerated Mon Jul 28 17:13:27 CST 2014
     */
    public void setMENU_DESC(String MENU_DESC) {
        this.MENU_DESC = MENU_DESC == null ? null : MENU_DESC.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_AUTH_MENU.MENU_SORT
     *
     * @return the value of TB_AUTH_MENU.MENU_SORT
     *
     * @mbggenerated Mon Jul 28 17:13:27 CST 2014
     */
    public Integer getMENU_SORT() {
        return MENU_SORT;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_AUTH_MENU.MENU_SORT
     *
     * @param MENU_SORT the value for TB_AUTH_MENU.MENU_SORT
     *
     * @mbggenerated Mon Jul 28 17:13:27 CST 2014
     */
    public void setMENU_SORT(Integer MENU_SORT) {
        this.MENU_SORT = MENU_SORT;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_AUTH_MENU.IS_FODR
     *
     * @return the value of TB_AUTH_MENU.IS_FODR
     *
     * @mbggenerated Mon Jul 28 17:13:27 CST 2014
     */
    public String getIS_FODR() {
        return IS_FODR;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_AUTH_MENU.IS_FODR
     *
     * @param IS_FODR the value for TB_AUTH_MENU.IS_FODR
     *
     * @mbggenerated Mon Jul 28 17:13:27 CST 2014
     */
    public void setIS_FODR(String IS_FODR) {
        this.IS_FODR = IS_FODR == null ? null : IS_FODR.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_AUTH_MENU.IS_USE
     *
     * @return the value of TB_AUTH_MENU.IS_USE
     *
     * @mbggenerated Mon Jul 28 17:13:27 CST 2014
     */
    public String getIS_USE() {
        return IS_USE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_AUTH_MENU.IS_USE
     *
     * @param IS_USE the value for TB_AUTH_MENU.IS_USE
     *
     * @mbggenerated Mon Jul 28 17:13:27 CST 2014
     */
    public void setIS_USE(String IS_USE) {
        this.IS_USE = IS_USE == null ? null : IS_USE.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_AUTH_MENU.PARENT_ID
     *
     * @return the value of TB_AUTH_MENU.PARENT_ID
     *
     * @mbggenerated Mon Jul 28 17:13:27 CST 2014
     */
    public BigDecimal getPARENT_ID() {
        return PARENT_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_AUTH_MENU.PARENT_ID
     *
     * @param PARENT_ID the value for TB_AUTH_MENU.PARENT_ID
     *
     * @mbggenerated Mon Jul 28 17:13:27 CST 2014
     */
    public void setPARENT_ID(BigDecimal PARENT_ID) {
        this.PARENT_ID = PARENT_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_AUTH_MENU.PGM_PATH
     *
     * @return the value of TB_AUTH_MENU.PGM_PATH
     *
     * @mbggenerated Mon Jul 28 17:13:27 CST 2014
     */
    public String getPGM_PATH() {
        return PGM_PATH;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_AUTH_MENU.PGM_PATH
     *
     * @param PGM_PATH the value for TB_AUTH_MENU.PGM_PATH
     *
     * @mbggenerated Mon Jul 28 17:13:27 CST 2014
     */
    public void setPGM_PATH(String PGM_PATH) {
        this.PGM_PATH = PGM_PATH == null ? null : PGM_PATH.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_AUTH_MENU.CR_USER
     *
     * @return the value of TB_AUTH_MENU.CR_USER
     *
     * @mbggenerated Mon Jul 28 17:13:27 CST 2014
     */
    public String getCR_USER() {
        return CR_USER;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_AUTH_MENU.CR_USER
     *
     * @param CR_USER the value for TB_AUTH_MENU.CR_USER
     *
     * @mbggenerated Mon Jul 28 17:13:27 CST 2014
     */
    public void setCR_USER(String CR_USER) {
        this.CR_USER = CR_USER == null ? null : CR_USER.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_AUTH_MENU.CR_TIME
     *
     * @return the value of TB_AUTH_MENU.CR_TIME
     *
     * @mbggenerated Mon Jul 28 17:13:27 CST 2014
     */
    public Date getCR_TIME() {
        return CR_TIME;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_AUTH_MENU.CR_TIME
     *
     * @param CR_TIME the value for TB_AUTH_MENU.CR_TIME
     *
     * @mbggenerated Mon Jul 28 17:13:27 CST 2014
     */
    public void setCR_TIME(Date CR_TIME) {
        this.CR_TIME = CR_TIME;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_AUTH_MENU.MD_USER
     *
     * @return the value of TB_AUTH_MENU.MD_USER
     *
     * @mbggenerated Mon Jul 28 17:13:27 CST 2014
     */
    public String getMD_USER() {
        return MD_USER;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_AUTH_MENU.MD_USER
     *
     * @param MD_USER the value for TB_AUTH_MENU.MD_USER
     *
     * @mbggenerated Mon Jul 28 17:13:27 CST 2014
     */
    public void setMD_USER(String MD_USER) {
        this.MD_USER = MD_USER;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_AUTH_MENU.MD_TIME
     *
     * @return the value of TB_AUTH_MENU.MD_TIME
     *
     * @mbggenerated Mon Jul 28 17:13:27 CST 2014
     */
    public Date getMD_TIME() {
        return MD_TIME;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_AUTH_MENU.MD_TIME
     *
     * @param MD_TIME the value for TB_AUTH_MENU.MD_TIME
     *
     * @mbggenerated Mon Jul 28 17:13:27 CST 2014
     */
    public void setMD_TIME(Date MD_TIME) {
        this.MD_TIME = MD_TIME;
    }

	@Override
	public String toString() {
		return "TbAuthMenu [MENU_ID=" + MENU_ID + ", MENU_NAME=" + MENU_NAME
				+ ", MENU_DESC=" + MENU_DESC + ", MENU_SORT=" + MENU_SORT
				+ ", IS_FODR=" + IS_FODR + ", IS_USE=" + IS_USE
				+ ", PARENT_ID=" + PARENT_ID + ", PGM_PATH=" + PGM_PATH
				+ ", CR_USER=" + CR_USER + ", CR_TIME=" + CR_TIME + ", MD_USER="
				+ MD_USER + ", MD_TIME=" + MD_TIME + "]";
	}
    
}