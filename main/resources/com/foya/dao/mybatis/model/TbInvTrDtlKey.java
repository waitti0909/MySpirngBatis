package com.foya.dao.mybatis.model;

public class TbInvTrDtlKey {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_INV_TR_DTL.tr_no
     *
     * @mbggenerated Fri Nov 07 10:41:58 CST 2014
     */
    private String tr_no;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_INV_TR_DTL.mat_no
     *
     * @mbggenerated Fri Nov 07 10:41:58 CST 2014
     */
    private String mat_no;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_INV_TR_DTL.tr_no
     *
     * @return the value of TB_INV_TR_DTL.tr_no
     *
     * @mbggenerated Fri Nov 07 10:41:58 CST 2014
     */
    public String getTr_no() {
        return tr_no;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_INV_TR_DTL.tr_no
     *
     * @param tr_no the value for TB_INV_TR_DTL.tr_no
     *
     * @mbggenerated Fri Nov 07 10:41:58 CST 2014
     */
    public void setTr_no(String tr_no) {
        this.tr_no = tr_no == null ? null : tr_no.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_INV_TR_DTL.mat_no
     *
     * @return the value of TB_INV_TR_DTL.mat_no
     *
     * @mbggenerated Fri Nov 07 10:41:58 CST 2014
     */
    public String getMat_no() {
        return mat_no;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_INV_TR_DTL.mat_no
     *
     * @param mat_no the value for TB_INV_TR_DTL.mat_no
     *
     * @mbggenerated Fri Nov 07 10:41:58 CST 2014
     */
    public void setMat_no(String mat_no) {
        this.mat_no = mat_no == null ? null : mat_no.trim();
    }
}