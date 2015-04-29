package com.foya.dao.mybatis.model;

public class TbComArea extends TbComAreaKey {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_COM_AREA.ZIP
     *
     * @mbggenerated Thu Sep 18 11:14:59 CST 2014
     */
    private String ZIP;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_COM_AREA.ZONE
     *
     * @mbggenerated Thu Sep 18 11:14:59 CST 2014
     */
    private String ZONE;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_COM_AREA.LEVEL
     *
     * @mbggenerated Thu Sep 18 11:14:59 CST 2014
     */
    private Integer LEVEL;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_COM_AREA.ZIP
     *
     * @return the value of TB_COM_AREA.ZIP
     *
     * @mbggenerated Thu Sep 18 11:14:59 CST 2014
     */
    public String getZIP() {
        return ZIP;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_COM_AREA.ZIP
     *
     * @param ZIP the value for TB_COM_AREA.ZIP
     *
     * @mbggenerated Thu Sep 18 11:14:59 CST 2014
     */
    public void setZIP(String ZIP) {
        this.ZIP = ZIP == null ? null : ZIP.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_COM_AREA.ZONE
     *
     * @return the value of TB_COM_AREA.ZONE
     *
     * @mbggenerated Thu Sep 18 11:14:59 CST 2014
     */
    public String getZONE() {
        return ZONE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_COM_AREA.ZONE
     *
     * @param ZONE the value for TB_COM_AREA.ZONE
     *
     * @mbggenerated Thu Sep 18 11:14:59 CST 2014
     */
    public void setZONE(String ZONE) {
        this.ZONE = ZONE == null ? null : ZONE.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_COM_AREA.LEVEL
     *
     * @return the value of TB_COM_AREA.LEVEL
     *
     * @mbggenerated Thu Sep 18 11:14:59 CST 2014
     */
    public Integer getLEVEL() {
        return LEVEL;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_COM_AREA.LEVEL
     *
     * @param LEVEL the value for TB_COM_AREA.LEVEL
     *
     * @mbggenerated Thu Sep 18 11:14:59 CST 2014
     */
    public void setLEVEL(Integer LEVEL) {
        this.LEVEL = LEVEL;
    }
}