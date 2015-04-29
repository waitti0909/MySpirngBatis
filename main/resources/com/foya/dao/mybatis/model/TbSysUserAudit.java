package com.foya.dao.mybatis.model;

import java.math.BigDecimal;
import java.util.Date;

public class TbSysUserAudit {
    @Override
	public String toString() {
		return "TbSysUserAudit [PSN_ID=" + PSN_ID + ", PSN_NAME=" + PSN_NAME + ", URL_PATH=" + URL_PATH + ", LOG_TIME=" + LOG_TIME
				+ ", LOG_TYPE=" + LOG_TYPE + ", LOG_DESCRIPTION=" + LOG_DESCRIPTION + ", USER_IP=" + USER_IP + ", SESSION_ID=" + SESSION_ID
				+ "]";
	}

	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_SYS_USER_AUDIT.PSN_ID
     *
     * @mbggenerated Wed Aug 06 15:16:49 CST 2014
     */
    private BigDecimal PSN_ID;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_SYS_USER_AUDIT.PSN_NAME
     *
     * @mbggenerated Wed Aug 06 15:16:49 CST 2014
     */
    private String PSN_NAME;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_SYS_USER_AUDIT.URL_PATH
     *
     * @mbggenerated Wed Aug 06 15:16:49 CST 2014
     */
    private String URL_PATH;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_SYS_USER_AUDIT.LOG_TIME
     *
     * @mbggenerated Wed Aug 06 15:16:49 CST 2014
     */
    private Date LOG_TIME;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_SYS_USER_AUDIT.LOG_TYPE
     *
     * @mbggenerated Wed Aug 06 15:16:49 CST 2014
     */
    private String LOG_TYPE;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_SYS_USER_AUDIT.LOG_DESCRIPTION
     *
     * @mbggenerated Wed Aug 06 15:16:49 CST 2014
     */
    private String LOG_DESCRIPTION;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_SYS_USER_AUDIT.USER_IP
     *
     * @mbggenerated Wed Aug 06 15:16:49 CST 2014
     */
    private String USER_IP;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_SYS_USER_AUDIT.SESSION_ID
     *
     * @mbggenerated Wed Aug 06 15:16:49 CST 2014
     */
    private String SESSION_ID;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_SYS_USER_AUDIT.PSN_ID
     *
     * @return the value of TB_SYS_USER_AUDIT.PSN_ID
     *
     * @mbggenerated Wed Aug 06 15:16:49 CST 2014
     */
    public BigDecimal getPSN_ID() {
        return PSN_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_SYS_USER_AUDIT.PSN_ID
     *
     * @param PSN_ID the value for TB_SYS_USER_AUDIT.PSN_ID
     *
     * @mbggenerated Wed Aug 06 15:16:49 CST 2014
     */
    public void setPSN_ID(BigDecimal PSN_ID) {
        this.PSN_ID = PSN_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_SYS_USER_AUDIT.PSN_NAME
     *
     * @return the value of TB_SYS_USER_AUDIT.PSN_NAME
     *
     * @mbggenerated Wed Aug 06 15:16:49 CST 2014
     */
    public String getPSN_NAME() {
        return PSN_NAME;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_SYS_USER_AUDIT.PSN_NAME
     *
     * @param PSN_NAME the value for TB_SYS_USER_AUDIT.PSN_NAME
     *
     * @mbggenerated Wed Aug 06 15:16:49 CST 2014
     */
    public void setPSN_NAME(String PSN_NAME) {
        this.PSN_NAME = PSN_NAME == null ? null : PSN_NAME.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_SYS_USER_AUDIT.URL_PATH
     *
     * @return the value of TB_SYS_USER_AUDIT.URL_PATH
     *
     * @mbggenerated Wed Aug 06 15:16:49 CST 2014
     */
    public String getURL_PATH() {
        return URL_PATH;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_SYS_USER_AUDIT.URL_PATH
     *
     * @param URL_PATH the value for TB_SYS_USER_AUDIT.URL_PATH
     *
     * @mbggenerated Wed Aug 06 15:16:49 CST 2014
     */
    public void setURL_PATH(String URL_PATH) {
        this.URL_PATH = URL_PATH == null ? null : URL_PATH.trim();
    }

    

    public Date getLOG_TIME() {
		return LOG_TIME;
	}

	public void setLOG_TIME(Date lOG_TIME) {
		LOG_TIME = lOG_TIME;
	}

	/**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_SYS_USER_AUDIT.LOG_TYPE
     *
     * @return the value of TB_SYS_USER_AUDIT.LOG_TYPE
     *
     * @mbggenerated Wed Aug 06 15:16:49 CST 2014
     */
    public String getLOG_TYPE() {
        return LOG_TYPE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_SYS_USER_AUDIT.LOG_TYPE
     *
     * @param LOG_TYPE the value for TB_SYS_USER_AUDIT.LOG_TYPE
     *
     * @mbggenerated Wed Aug 06 15:16:49 CST 2014
     */
    public void setLOG_TYPE(String LOG_TYPE) {
        this.LOG_TYPE = LOG_TYPE == null ? null : LOG_TYPE.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_SYS_USER_AUDIT.LOG_DESCRIPTION
     *
     * @return the value of TB_SYS_USER_AUDIT.LOG_DESCRIPTION
     *
     * @mbggenerated Wed Aug 06 15:16:49 CST 2014
     */
    public String getLOG_DESCRIPTION() {
        return LOG_DESCRIPTION;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_SYS_USER_AUDIT.LOG_DESCRIPTION
     *
     * @param LOG_DESCRIPTION the value for TB_SYS_USER_AUDIT.LOG_DESCRIPTION
     *
     * @mbggenerated Wed Aug 06 15:16:49 CST 2014
     */
    public void setLOG_DESCRIPTION(String LOG_DESCRIPTION) {
        this.LOG_DESCRIPTION = LOG_DESCRIPTION == null ? null : LOG_DESCRIPTION.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_SYS_USER_AUDIT.USER_IP
     *
     * @return the value of TB_SYS_USER_AUDIT.USER_IP
     *
     * @mbggenerated Wed Aug 06 15:16:49 CST 2014
     */
    public String getUSER_IP() {
        return USER_IP;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_SYS_USER_AUDIT.USER_IP
     *
     * @param USER_IP the value for TB_SYS_USER_AUDIT.USER_IP
     *
     * @mbggenerated Wed Aug 06 15:16:49 CST 2014
     */
    public void setUSER_IP(String USER_IP) {
        this.USER_IP = USER_IP == null ? null : USER_IP.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_SYS_USER_AUDIT.SESSION_ID
     *
     * @return the value of TB_SYS_USER_AUDIT.SESSION_ID
     *
     * @mbggenerated Wed Aug 06 15:16:49 CST 2014
     */
    public String getSESSION_ID() {
        return SESSION_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_SYS_USER_AUDIT.SESSION_ID
     *
     * @param SESSION_ID the value for TB_SYS_USER_AUDIT.SESSION_ID
     *
     * @mbggenerated Wed Aug 06 15:16:49 CST 2014
     */
    public void setSESSION_ID(String SESSION_ID) {
        this.SESSION_ID = SESSION_ID == null ? null : SESSION_ID.trim();
    }
}