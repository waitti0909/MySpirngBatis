package com.foya.dao.mybatis.model;

public class TbSysUniqueSeqKey {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_SYS_UNIQUE_SEQ.SEQ_TYPE
     *
     * @mbggenerated Tue Oct 21 18:59:54 CST 2014
     */
    private String SEQ_TYPE;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column TB_SYS_UNIQUE_SEQ.SEQ_PREV
     *
     * @mbggenerated Tue Oct 21 18:59:54 CST 2014
     */
    private String SEQ_PREV;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_SYS_UNIQUE_SEQ.SEQ_TYPE
     *
     * @return the value of TB_SYS_UNIQUE_SEQ.SEQ_TYPE
     *
     * @mbggenerated Tue Oct 21 18:59:54 CST 2014
     */
    public String getSEQ_TYPE() {
        return SEQ_TYPE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_SYS_UNIQUE_SEQ.SEQ_TYPE
     *
     * @param SEQ_TYPE the value for TB_SYS_UNIQUE_SEQ.SEQ_TYPE
     *
     * @mbggenerated Tue Oct 21 18:59:54 CST 2014
     */
    public void setSEQ_TYPE(String SEQ_TYPE) {
        this.SEQ_TYPE = SEQ_TYPE == null ? null : SEQ_TYPE.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column TB_SYS_UNIQUE_SEQ.SEQ_PREV
     *
     * @return the value of TB_SYS_UNIQUE_SEQ.SEQ_PREV
     *
     * @mbggenerated Tue Oct 21 18:59:54 CST 2014
     */
    public String getSEQ_PREV() {
        return SEQ_PREV;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column TB_SYS_UNIQUE_SEQ.SEQ_PREV
     *
     * @param SEQ_PREV the value for TB_SYS_UNIQUE_SEQ.SEQ_PREV
     *
     * @mbggenerated Tue Oct 21 18:59:54 CST 2014
     */
    public void setSEQ_PREV(String SEQ_PREV) {
        this.SEQ_PREV = SEQ_PREV == null ? null : SEQ_PREV.trim();
    }
}