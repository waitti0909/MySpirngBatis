package com.foya.dao.mybatis.model;

import java.math.BigDecimal;
import java.util.Date;

public class TbLsElecRangeKey {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_LS_ELEC_RANGE.BEGIN_RANGE
     *
     * @mbggenerated Fri Mar 20 15:37:16 CST 2015
     */
    private BigDecimal BEGIN_RANGE;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_LS_ELEC_RANGE.END_RANGE
     *
     * @mbggenerated Fri Mar 20 15:37:16 CST 2015
     */
    private BigDecimal END_RANGE;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_LS_ELEC_RANGE.EFF_DATE
     *
     * @mbggenerated Fri Mar 20 15:37:16 CST 2015
     */
    private Date EFF_DATE;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_LS_ELEC_RANGE.BEGIN_RANGE
     *
     * @return the value of TB_LS_ELEC_RANGE.BEGIN_RANGE
     *
     * @mbggenerated Fri Mar 20 15:37:16 CST 2015
     */
    public BigDecimal getBEGIN_RANGE() {
        return BEGIN_RANGE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_LS_ELEC_RANGE.BEGIN_RANGE
     *
     * @param BEGIN_RANGE the value for TB_LS_ELEC_RANGE.BEGIN_RANGE
     *
     * @mbggenerated Fri Mar 20 15:37:16 CST 2015
     */
    public void setBEGIN_RANGE(BigDecimal BEGIN_RANGE) {
        this.BEGIN_RANGE = BEGIN_RANGE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_LS_ELEC_RANGE.END_RANGE
     *
     * @return the value of TB_LS_ELEC_RANGE.END_RANGE
     *
     * @mbggenerated Fri Mar 20 15:37:16 CST 2015
     */
    public BigDecimal getEND_RANGE() {
        return END_RANGE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_LS_ELEC_RANGE.END_RANGE
     *
     * @param END_RANGE the value for TB_LS_ELEC_RANGE.END_RANGE
     *
     * @mbggenerated Fri Mar 20 15:37:16 CST 2015
     */
    public void setEND_RANGE(BigDecimal END_RANGE) {
        this.END_RANGE = END_RANGE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_LS_ELEC_RANGE.EFF_DATE
     *
     * @return the value of TB_LS_ELEC_RANGE.EFF_DATE
     *
     * @mbggenerated Fri Mar 20 15:37:16 CST 2015
     */
    public Date getEFF_DATE() {
        return EFF_DATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_LS_ELEC_RANGE.EFF_DATE
     *
     * @param EFF_DATE the value for TB_LS_ELEC_RANGE.EFF_DATE
     *
     * @mbggenerated Fri Mar 20 15:37:16 CST 2015
     */
    public void setEFF_DATE(Date EFF_DATE) {
        this.EFF_DATE = EFF_DATE;
    }
}