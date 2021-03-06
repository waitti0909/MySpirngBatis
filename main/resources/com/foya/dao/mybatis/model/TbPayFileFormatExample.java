package com.foya.dao.mybatis.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TbPayFileFormatExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table TB_PAY_FILE_FORMAT
     *
     * @mbggenerated Thu Apr 09 18:32:20 CST 2015
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table TB_PAY_FILE_FORMAT
     *
     * @mbggenerated Thu Apr 09 18:32:20 CST 2015
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table TB_PAY_FILE_FORMAT
     *
     * @mbggenerated Thu Apr 09 18:32:20 CST 2015
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_PAY_FILE_FORMAT
     *
     * @mbggenerated Thu Apr 09 18:32:20 CST 2015
     */
    public TbPayFileFormatExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_PAY_FILE_FORMAT
     *
     * @mbggenerated Thu Apr 09 18:32:20 CST 2015
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_PAY_FILE_FORMAT
     *
     * @mbggenerated Thu Apr 09 18:32:20 CST 2015
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_PAY_FILE_FORMAT
     *
     * @mbggenerated Thu Apr 09 18:32:20 CST 2015
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_PAY_FILE_FORMAT
     *
     * @mbggenerated Thu Apr 09 18:32:20 CST 2015
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_PAY_FILE_FORMAT
     *
     * @mbggenerated Thu Apr 09 18:32:20 CST 2015
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_PAY_FILE_FORMAT
     *
     * @mbggenerated Thu Apr 09 18:32:20 CST 2015
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_PAY_FILE_FORMAT
     *
     * @mbggenerated Thu Apr 09 18:32:20 CST 2015
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_PAY_FILE_FORMAT
     *
     * @mbggenerated Thu Apr 09 18:32:20 CST 2015
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_PAY_FILE_FORMAT
     *
     * @mbggenerated Thu Apr 09 18:32:20 CST 2015
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_PAY_FILE_FORMAT
     *
     * @mbggenerated Thu Apr 09 18:32:20 CST 2015
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table TB_PAY_FILE_FORMAT
     *
     * @mbggenerated Thu Apr 09 18:32:20 CST 2015
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andSEQ_NBRIsNull() {
            addCriterion("SEQ_NBR is null");
            return (Criteria) this;
        }

        public Criteria andSEQ_NBRIsNotNull() {
            addCriterion("SEQ_NBR is not null");
            return (Criteria) this;
        }

        public Criteria andSEQ_NBREqualTo(Long value) {
            addCriterion("SEQ_NBR =", value, "SEQ_NBR");
            return (Criteria) this;
        }

        public Criteria andSEQ_NBRNotEqualTo(Long value) {
            addCriterion("SEQ_NBR <>", value, "SEQ_NBR");
            return (Criteria) this;
        }

        public Criteria andSEQ_NBRGreaterThan(Long value) {
            addCriterion("SEQ_NBR >", value, "SEQ_NBR");
            return (Criteria) this;
        }

        public Criteria andSEQ_NBRGreaterThanOrEqualTo(Long value) {
            addCriterion("SEQ_NBR >=", value, "SEQ_NBR");
            return (Criteria) this;
        }

        public Criteria andSEQ_NBRLessThan(Long value) {
            addCriterion("SEQ_NBR <", value, "SEQ_NBR");
            return (Criteria) this;
        }

        public Criteria andSEQ_NBRLessThanOrEqualTo(Long value) {
            addCriterion("SEQ_NBR <=", value, "SEQ_NBR");
            return (Criteria) this;
        }

        public Criteria andSEQ_NBRIn(List<Long> values) {
            addCriterion("SEQ_NBR in", values, "SEQ_NBR");
            return (Criteria) this;
        }

        public Criteria andSEQ_NBRNotIn(List<Long> values) {
            addCriterion("SEQ_NBR not in", values, "SEQ_NBR");
            return (Criteria) this;
        }

        public Criteria andSEQ_NBRBetween(Long value1, Long value2) {
            addCriterion("SEQ_NBR between", value1, value2, "SEQ_NBR");
            return (Criteria) this;
        }

        public Criteria andSEQ_NBRNotBetween(Long value1, Long value2) {
            addCriterion("SEQ_NBR not between", value1, value2, "SEQ_NBR");
            return (Criteria) this;
        }

        public Criteria andPROCESS_TYPEIsNull() {
            addCriterion("PROCESS_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andPROCESS_TYPEIsNotNull() {
            addCriterion("PROCESS_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andPROCESS_TYPEEqualTo(String value) {
            addCriterion("PROCESS_TYPE =", value, "PROCESS_TYPE");
            return (Criteria) this;
        }

        public Criteria andPROCESS_TYPENotEqualTo(String value) {
            addCriterion("PROCESS_TYPE <>", value, "PROCESS_TYPE");
            return (Criteria) this;
        }

        public Criteria andPROCESS_TYPEGreaterThan(String value) {
            addCriterion("PROCESS_TYPE >", value, "PROCESS_TYPE");
            return (Criteria) this;
        }

        public Criteria andPROCESS_TYPEGreaterThanOrEqualTo(String value) {
            addCriterion("PROCESS_TYPE >=", value, "PROCESS_TYPE");
            return (Criteria) this;
        }

        public Criteria andPROCESS_TYPELessThan(String value) {
            addCriterion("PROCESS_TYPE <", value, "PROCESS_TYPE");
            return (Criteria) this;
        }

        public Criteria andPROCESS_TYPELessThanOrEqualTo(String value) {
            addCriterion("PROCESS_TYPE <=", value, "PROCESS_TYPE");
            return (Criteria) this;
        }

        public Criteria andPROCESS_TYPELike(String value) {
            addCriterion("PROCESS_TYPE like", value, "PROCESS_TYPE");
            return (Criteria) this;
        }

        public Criteria andPROCESS_TYPENotLike(String value) {
            addCriterion("PROCESS_TYPE not like", value, "PROCESS_TYPE");
            return (Criteria) this;
        }

        public Criteria andPROCESS_TYPEIn(List<String> values) {
            addCriterion("PROCESS_TYPE in", values, "PROCESS_TYPE");
            return (Criteria) this;
        }

        public Criteria andPROCESS_TYPENotIn(List<String> values) {
            addCriterion("PROCESS_TYPE not in", values, "PROCESS_TYPE");
            return (Criteria) this;
        }

        public Criteria andPROCESS_TYPEBetween(String value1, String value2) {
            addCriterion("PROCESS_TYPE between", value1, value2, "PROCESS_TYPE");
            return (Criteria) this;
        }

        public Criteria andPROCESS_TYPENotBetween(String value1, String value2) {
            addCriterion("PROCESS_TYPE not between", value1, value2, "PROCESS_TYPE");
            return (Criteria) this;
        }

        public Criteria andVENDOR_TYPEIsNull() {
            addCriterion("VENDOR_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andVENDOR_TYPEIsNotNull() {
            addCriterion("VENDOR_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andVENDOR_TYPEEqualTo(String value) {
            addCriterion("VENDOR_TYPE =", value, "VENDOR_TYPE");
            return (Criteria) this;
        }

        public Criteria andVENDOR_TYPENotEqualTo(String value) {
            addCriterion("VENDOR_TYPE <>", value, "VENDOR_TYPE");
            return (Criteria) this;
        }

        public Criteria andVENDOR_TYPEGreaterThan(String value) {
            addCriterion("VENDOR_TYPE >", value, "VENDOR_TYPE");
            return (Criteria) this;
        }

        public Criteria andVENDOR_TYPEGreaterThanOrEqualTo(String value) {
            addCriterion("VENDOR_TYPE >=", value, "VENDOR_TYPE");
            return (Criteria) this;
        }

        public Criteria andVENDOR_TYPELessThan(String value) {
            addCriterion("VENDOR_TYPE <", value, "VENDOR_TYPE");
            return (Criteria) this;
        }

        public Criteria andVENDOR_TYPELessThanOrEqualTo(String value) {
            addCriterion("VENDOR_TYPE <=", value, "VENDOR_TYPE");
            return (Criteria) this;
        }

        public Criteria andVENDOR_TYPELike(String value) {
            addCriterion("VENDOR_TYPE like", value, "VENDOR_TYPE");
            return (Criteria) this;
        }

        public Criteria andVENDOR_TYPENotLike(String value) {
            addCriterion("VENDOR_TYPE not like", value, "VENDOR_TYPE");
            return (Criteria) this;
        }

        public Criteria andVENDOR_TYPEIn(List<String> values) {
            addCriterion("VENDOR_TYPE in", values, "VENDOR_TYPE");
            return (Criteria) this;
        }

        public Criteria andVENDOR_TYPENotIn(List<String> values) {
            addCriterion("VENDOR_TYPE not in", values, "VENDOR_TYPE");
            return (Criteria) this;
        }

        public Criteria andVENDOR_TYPEBetween(String value1, String value2) {
            addCriterion("VENDOR_TYPE between", value1, value2, "VENDOR_TYPE");
            return (Criteria) this;
        }

        public Criteria andVENDOR_TYPENotBetween(String value1, String value2) {
            addCriterion("VENDOR_TYPE not between", value1, value2, "VENDOR_TYPE");
            return (Criteria) this;
        }

        public Criteria andRECORD_TYPEIsNull() {
            addCriterion("RECORD_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andRECORD_TYPEIsNotNull() {
            addCriterion("RECORD_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andRECORD_TYPEEqualTo(String value) {
            addCriterion("RECORD_TYPE =", value, "RECORD_TYPE");
            return (Criteria) this;
        }

        public Criteria andRECORD_TYPENotEqualTo(String value) {
            addCriterion("RECORD_TYPE <>", value, "RECORD_TYPE");
            return (Criteria) this;
        }

        public Criteria andRECORD_TYPEGreaterThan(String value) {
            addCriterion("RECORD_TYPE >", value, "RECORD_TYPE");
            return (Criteria) this;
        }

        public Criteria andRECORD_TYPEGreaterThanOrEqualTo(String value) {
            addCriterion("RECORD_TYPE >=", value, "RECORD_TYPE");
            return (Criteria) this;
        }

        public Criteria andRECORD_TYPELessThan(String value) {
            addCriterion("RECORD_TYPE <", value, "RECORD_TYPE");
            return (Criteria) this;
        }

        public Criteria andRECORD_TYPELessThanOrEqualTo(String value) {
            addCriterion("RECORD_TYPE <=", value, "RECORD_TYPE");
            return (Criteria) this;
        }

        public Criteria andRECORD_TYPELike(String value) {
            addCriterion("RECORD_TYPE like", value, "RECORD_TYPE");
            return (Criteria) this;
        }

        public Criteria andRECORD_TYPENotLike(String value) {
            addCriterion("RECORD_TYPE not like", value, "RECORD_TYPE");
            return (Criteria) this;
        }

        public Criteria andRECORD_TYPEIn(List<String> values) {
            addCriterion("RECORD_TYPE in", values, "RECORD_TYPE");
            return (Criteria) this;
        }

        public Criteria andRECORD_TYPENotIn(List<String> values) {
            addCriterion("RECORD_TYPE not in", values, "RECORD_TYPE");
            return (Criteria) this;
        }

        public Criteria andRECORD_TYPEBetween(String value1, String value2) {
            addCriterion("RECORD_TYPE between", value1, value2, "RECORD_TYPE");
            return (Criteria) this;
        }

        public Criteria andRECORD_TYPENotBetween(String value1, String value2) {
            addCriterion("RECORD_TYPE not between", value1, value2, "RECORD_TYPE");
            return (Criteria) this;
        }

        public Criteria andDATA_LENGTHIsNull() {
            addCriterion("DATA_LENGTH is null");
            return (Criteria) this;
        }

        public Criteria andDATA_LENGTHIsNotNull() {
            addCriterion("DATA_LENGTH is not null");
            return (Criteria) this;
        }

        public Criteria andDATA_LENGTHEqualTo(Short value) {
            addCriterion("DATA_LENGTH =", value, "DATA_LENGTH");
            return (Criteria) this;
        }

        public Criteria andDATA_LENGTHNotEqualTo(Short value) {
            addCriterion("DATA_LENGTH <>", value, "DATA_LENGTH");
            return (Criteria) this;
        }

        public Criteria andDATA_LENGTHGreaterThan(Short value) {
            addCriterion("DATA_LENGTH >", value, "DATA_LENGTH");
            return (Criteria) this;
        }

        public Criteria andDATA_LENGTHGreaterThanOrEqualTo(Short value) {
            addCriterion("DATA_LENGTH >=", value, "DATA_LENGTH");
            return (Criteria) this;
        }

        public Criteria andDATA_LENGTHLessThan(Short value) {
            addCriterion("DATA_LENGTH <", value, "DATA_LENGTH");
            return (Criteria) this;
        }

        public Criteria andDATA_LENGTHLessThanOrEqualTo(Short value) {
            addCriterion("DATA_LENGTH <=", value, "DATA_LENGTH");
            return (Criteria) this;
        }

        public Criteria andDATA_LENGTHIn(List<Short> values) {
            addCriterion("DATA_LENGTH in", values, "DATA_LENGTH");
            return (Criteria) this;
        }

        public Criteria andDATA_LENGTHNotIn(List<Short> values) {
            addCriterion("DATA_LENGTH not in", values, "DATA_LENGTH");
            return (Criteria) this;
        }

        public Criteria andDATA_LENGTHBetween(Short value1, Short value2) {
            addCriterion("DATA_LENGTH between", value1, value2, "DATA_LENGTH");
            return (Criteria) this;
        }

        public Criteria andDATA_LENGTHNotBetween(Short value1, Short value2) {
            addCriterion("DATA_LENGTH not between", value1, value2, "DATA_LENGTH");
            return (Criteria) this;
        }

        public Criteria andSEPARATION_SIGNIsNull() {
            addCriterion("SEPARATION_SIGN is null");
            return (Criteria) this;
        }

        public Criteria andSEPARATION_SIGNIsNotNull() {
            addCriterion("SEPARATION_SIGN is not null");
            return (Criteria) this;
        }

        public Criteria andSEPARATION_SIGNEqualTo(String value) {
            addCriterion("SEPARATION_SIGN =", value, "SEPARATION_SIGN");
            return (Criteria) this;
        }

        public Criteria andSEPARATION_SIGNNotEqualTo(String value) {
            addCriterion("SEPARATION_SIGN <>", value, "SEPARATION_SIGN");
            return (Criteria) this;
        }

        public Criteria andSEPARATION_SIGNGreaterThan(String value) {
            addCriterion("SEPARATION_SIGN >", value, "SEPARATION_SIGN");
            return (Criteria) this;
        }

        public Criteria andSEPARATION_SIGNGreaterThanOrEqualTo(String value) {
            addCriterion("SEPARATION_SIGN >=", value, "SEPARATION_SIGN");
            return (Criteria) this;
        }

        public Criteria andSEPARATION_SIGNLessThan(String value) {
            addCriterion("SEPARATION_SIGN <", value, "SEPARATION_SIGN");
            return (Criteria) this;
        }

        public Criteria andSEPARATION_SIGNLessThanOrEqualTo(String value) {
            addCriterion("SEPARATION_SIGN <=", value, "SEPARATION_SIGN");
            return (Criteria) this;
        }

        public Criteria andSEPARATION_SIGNLike(String value) {
            addCriterion("SEPARATION_SIGN like", value, "SEPARATION_SIGN");
            return (Criteria) this;
        }

        public Criteria andSEPARATION_SIGNNotLike(String value) {
            addCriterion("SEPARATION_SIGN not like", value, "SEPARATION_SIGN");
            return (Criteria) this;
        }

        public Criteria andSEPARATION_SIGNIn(List<String> values) {
            addCriterion("SEPARATION_SIGN in", values, "SEPARATION_SIGN");
            return (Criteria) this;
        }

        public Criteria andSEPARATION_SIGNNotIn(List<String> values) {
            addCriterion("SEPARATION_SIGN not in", values, "SEPARATION_SIGN");
            return (Criteria) this;
        }

        public Criteria andSEPARATION_SIGNBetween(String value1, String value2) {
            addCriterion("SEPARATION_SIGN between", value1, value2, "SEPARATION_SIGN");
            return (Criteria) this;
        }

        public Criteria andSEPARATION_SIGNNotBetween(String value1, String value2) {
            addCriterion("SEPARATION_SIGN not between", value1, value2, "SEPARATION_SIGN");
            return (Criteria) this;
        }

        public Criteria andSTART_LINE_NBRIsNull() {
            addCriterion("START_LINE_NBR is null");
            return (Criteria) this;
        }

        public Criteria andSTART_LINE_NBRIsNotNull() {
            addCriterion("START_LINE_NBR is not null");
            return (Criteria) this;
        }

        public Criteria andSTART_LINE_NBREqualTo(Short value) {
            addCriterion("START_LINE_NBR =", value, "START_LINE_NBR");
            return (Criteria) this;
        }

        public Criteria andSTART_LINE_NBRNotEqualTo(Short value) {
            addCriterion("START_LINE_NBR <>", value, "START_LINE_NBR");
            return (Criteria) this;
        }

        public Criteria andSTART_LINE_NBRGreaterThan(Short value) {
            addCriterion("START_LINE_NBR >", value, "START_LINE_NBR");
            return (Criteria) this;
        }

        public Criteria andSTART_LINE_NBRGreaterThanOrEqualTo(Short value) {
            addCriterion("START_LINE_NBR >=", value, "START_LINE_NBR");
            return (Criteria) this;
        }

        public Criteria andSTART_LINE_NBRLessThan(Short value) {
            addCriterion("START_LINE_NBR <", value, "START_LINE_NBR");
            return (Criteria) this;
        }

        public Criteria andSTART_LINE_NBRLessThanOrEqualTo(Short value) {
            addCriterion("START_LINE_NBR <=", value, "START_LINE_NBR");
            return (Criteria) this;
        }

        public Criteria andSTART_LINE_NBRIn(List<Short> values) {
            addCriterion("START_LINE_NBR in", values, "START_LINE_NBR");
            return (Criteria) this;
        }

        public Criteria andSTART_LINE_NBRNotIn(List<Short> values) {
            addCriterion("START_LINE_NBR not in", values, "START_LINE_NBR");
            return (Criteria) this;
        }

        public Criteria andSTART_LINE_NBRBetween(Short value1, Short value2) {
            addCriterion("START_LINE_NBR between", value1, value2, "START_LINE_NBR");
            return (Criteria) this;
        }

        public Criteria andSTART_LINE_NBRNotBetween(Short value1, Short value2) {
            addCriterion("START_LINE_NBR not between", value1, value2, "START_LINE_NBR");
            return (Criteria) this;
        }

        public Criteria andCR_USERIsNull() {
            addCriterion("CR_USER is null");
            return (Criteria) this;
        }

        public Criteria andCR_USERIsNotNull() {
            addCriterion("CR_USER is not null");
            return (Criteria) this;
        }

        public Criteria andCR_USEREqualTo(String value) {
            addCriterion("CR_USER =", value, "CR_USER");
            return (Criteria) this;
        }

        public Criteria andCR_USERNotEqualTo(String value) {
            addCriterion("CR_USER <>", value, "CR_USER");
            return (Criteria) this;
        }

        public Criteria andCR_USERGreaterThan(String value) {
            addCriterion("CR_USER >", value, "CR_USER");
            return (Criteria) this;
        }

        public Criteria andCR_USERGreaterThanOrEqualTo(String value) {
            addCriterion("CR_USER >=", value, "CR_USER");
            return (Criteria) this;
        }

        public Criteria andCR_USERLessThan(String value) {
            addCriterion("CR_USER <", value, "CR_USER");
            return (Criteria) this;
        }

        public Criteria andCR_USERLessThanOrEqualTo(String value) {
            addCriterion("CR_USER <=", value, "CR_USER");
            return (Criteria) this;
        }

        public Criteria andCR_USERLike(String value) {
            addCriterion("CR_USER like", value, "CR_USER");
            return (Criteria) this;
        }

        public Criteria andCR_USERNotLike(String value) {
            addCriterion("CR_USER not like", value, "CR_USER");
            return (Criteria) this;
        }

        public Criteria andCR_USERIn(List<String> values) {
            addCriterion("CR_USER in", values, "CR_USER");
            return (Criteria) this;
        }

        public Criteria andCR_USERNotIn(List<String> values) {
            addCriterion("CR_USER not in", values, "CR_USER");
            return (Criteria) this;
        }

        public Criteria andCR_USERBetween(String value1, String value2) {
            addCriterion("CR_USER between", value1, value2, "CR_USER");
            return (Criteria) this;
        }

        public Criteria andCR_USERNotBetween(String value1, String value2) {
            addCriterion("CR_USER not between", value1, value2, "CR_USER");
            return (Criteria) this;
        }

        public Criteria andCR_TIMEIsNull() {
            addCriterion("CR_TIME is null");
            return (Criteria) this;
        }

        public Criteria andCR_TIMEIsNotNull() {
            addCriterion("CR_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andCR_TIMEEqualTo(Date value) {
            addCriterion("CR_TIME =", value, "CR_TIME");
            return (Criteria) this;
        }

        public Criteria andCR_TIMENotEqualTo(Date value) {
            addCriterion("CR_TIME <>", value, "CR_TIME");
            return (Criteria) this;
        }

        public Criteria andCR_TIMEGreaterThan(Date value) {
            addCriterion("CR_TIME >", value, "CR_TIME");
            return (Criteria) this;
        }

        public Criteria andCR_TIMEGreaterThanOrEqualTo(Date value) {
            addCriterion("CR_TIME >=", value, "CR_TIME");
            return (Criteria) this;
        }

        public Criteria andCR_TIMELessThan(Date value) {
            addCriterion("CR_TIME <", value, "CR_TIME");
            return (Criteria) this;
        }

        public Criteria andCR_TIMELessThanOrEqualTo(Date value) {
            addCriterion("CR_TIME <=", value, "CR_TIME");
            return (Criteria) this;
        }

        public Criteria andCR_TIMEIn(List<Date> values) {
            addCriterion("CR_TIME in", values, "CR_TIME");
            return (Criteria) this;
        }

        public Criteria andCR_TIMENotIn(List<Date> values) {
            addCriterion("CR_TIME not in", values, "CR_TIME");
            return (Criteria) this;
        }

        public Criteria andCR_TIMEBetween(Date value1, Date value2) {
            addCriterion("CR_TIME between", value1, value2, "CR_TIME");
            return (Criteria) this;
        }

        public Criteria andCR_TIMENotBetween(Date value1, Date value2) {
            addCriterion("CR_TIME not between", value1, value2, "CR_TIME");
            return (Criteria) this;
        }

        public Criteria andMD_USERIsNull() {
            addCriterion("MD_USER is null");
            return (Criteria) this;
        }

        public Criteria andMD_USERIsNotNull() {
            addCriterion("MD_USER is not null");
            return (Criteria) this;
        }

        public Criteria andMD_USEREqualTo(String value) {
            addCriterion("MD_USER =", value, "MD_USER");
            return (Criteria) this;
        }

        public Criteria andMD_USERNotEqualTo(String value) {
            addCriterion("MD_USER <>", value, "MD_USER");
            return (Criteria) this;
        }

        public Criteria andMD_USERGreaterThan(String value) {
            addCriterion("MD_USER >", value, "MD_USER");
            return (Criteria) this;
        }

        public Criteria andMD_USERGreaterThanOrEqualTo(String value) {
            addCriterion("MD_USER >=", value, "MD_USER");
            return (Criteria) this;
        }

        public Criteria andMD_USERLessThan(String value) {
            addCriterion("MD_USER <", value, "MD_USER");
            return (Criteria) this;
        }

        public Criteria andMD_USERLessThanOrEqualTo(String value) {
            addCriterion("MD_USER <=", value, "MD_USER");
            return (Criteria) this;
        }

        public Criteria andMD_USERLike(String value) {
            addCriterion("MD_USER like", value, "MD_USER");
            return (Criteria) this;
        }

        public Criteria andMD_USERNotLike(String value) {
            addCriterion("MD_USER not like", value, "MD_USER");
            return (Criteria) this;
        }

        public Criteria andMD_USERIn(List<String> values) {
            addCriterion("MD_USER in", values, "MD_USER");
            return (Criteria) this;
        }

        public Criteria andMD_USERNotIn(List<String> values) {
            addCriterion("MD_USER not in", values, "MD_USER");
            return (Criteria) this;
        }

        public Criteria andMD_USERBetween(String value1, String value2) {
            addCriterion("MD_USER between", value1, value2, "MD_USER");
            return (Criteria) this;
        }

        public Criteria andMD_USERNotBetween(String value1, String value2) {
            addCriterion("MD_USER not between", value1, value2, "MD_USER");
            return (Criteria) this;
        }

        public Criteria andMD_TIMEIsNull() {
            addCriterion("MD_TIME is null");
            return (Criteria) this;
        }

        public Criteria andMD_TIMEIsNotNull() {
            addCriterion("MD_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andMD_TIMEEqualTo(Date value) {
            addCriterion("MD_TIME =", value, "MD_TIME");
            return (Criteria) this;
        }

        public Criteria andMD_TIMENotEqualTo(Date value) {
            addCriterion("MD_TIME <>", value, "MD_TIME");
            return (Criteria) this;
        }

        public Criteria andMD_TIMEGreaterThan(Date value) {
            addCriterion("MD_TIME >", value, "MD_TIME");
            return (Criteria) this;
        }

        public Criteria andMD_TIMEGreaterThanOrEqualTo(Date value) {
            addCriterion("MD_TIME >=", value, "MD_TIME");
            return (Criteria) this;
        }

        public Criteria andMD_TIMELessThan(Date value) {
            addCriterion("MD_TIME <", value, "MD_TIME");
            return (Criteria) this;
        }

        public Criteria andMD_TIMELessThanOrEqualTo(Date value) {
            addCriterion("MD_TIME <=", value, "MD_TIME");
            return (Criteria) this;
        }

        public Criteria andMD_TIMEIn(List<Date> values) {
            addCriterion("MD_TIME in", values, "MD_TIME");
            return (Criteria) this;
        }

        public Criteria andMD_TIMENotIn(List<Date> values) {
            addCriterion("MD_TIME not in", values, "MD_TIME");
            return (Criteria) this;
        }

        public Criteria andMD_TIMEBetween(Date value1, Date value2) {
            addCriterion("MD_TIME between", value1, value2, "MD_TIME");
            return (Criteria) this;
        }

        public Criteria andMD_TIMENotBetween(Date value1, Date value2) {
            addCriterion("MD_TIME not between", value1, value2, "MD_TIME");
            return (Criteria) this;
        }

        public Criteria andVENDOR_NAMEIsNull() {
            addCriterion("VENDOR_NAME is null");
            return (Criteria) this;
        }

        public Criteria andVENDOR_NAMEIsNotNull() {
            addCriterion("VENDOR_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andVENDOR_NAMEEqualTo(String value) {
            addCriterion("VENDOR_NAME =", value, "VENDOR_NAME");
            return (Criteria) this;
        }

        public Criteria andVENDOR_NAMENotEqualTo(String value) {
            addCriterion("VENDOR_NAME <>", value, "VENDOR_NAME");
            return (Criteria) this;
        }

        public Criteria andVENDOR_NAMEGreaterThan(String value) {
            addCriterion("VENDOR_NAME >", value, "VENDOR_NAME");
            return (Criteria) this;
        }

        public Criteria andVENDOR_NAMEGreaterThanOrEqualTo(String value) {
            addCriterion("VENDOR_NAME >=", value, "VENDOR_NAME");
            return (Criteria) this;
        }

        public Criteria andVENDOR_NAMELessThan(String value) {
            addCriterion("VENDOR_NAME <", value, "VENDOR_NAME");
            return (Criteria) this;
        }

        public Criteria andVENDOR_NAMELessThanOrEqualTo(String value) {
            addCriterion("VENDOR_NAME <=", value, "VENDOR_NAME");
            return (Criteria) this;
        }

        public Criteria andVENDOR_NAMELike(String value) {
            addCriterion("VENDOR_NAME like", value, "VENDOR_NAME");
            return (Criteria) this;
        }

        public Criteria andVENDOR_NAMENotLike(String value) {
            addCriterion("VENDOR_NAME not like", value, "VENDOR_NAME");
            return (Criteria) this;
        }

        public Criteria andVENDOR_NAMEIn(List<String> values) {
            addCriterion("VENDOR_NAME in", values, "VENDOR_NAME");
            return (Criteria) this;
        }

        public Criteria andVENDOR_NAMENotIn(List<String> values) {
            addCriterion("VENDOR_NAME not in", values, "VENDOR_NAME");
            return (Criteria) this;
        }

        public Criteria andVENDOR_NAMEBetween(String value1, String value2) {
            addCriterion("VENDOR_NAME between", value1, value2, "VENDOR_NAME");
            return (Criteria) this;
        }

        public Criteria andVENDOR_NAMENotBetween(String value1, String value2) {
            addCriterion("VENDOR_NAME not between", value1, value2, "VENDOR_NAME");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table TB_PAY_FILE_FORMAT
     *
     * @mbggenerated do_not_delete_during_merge Thu Apr 09 18:32:20 CST 2015
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table TB_PAY_FILE_FORMAT
     *
     * @mbggenerated Thu Apr 09 18:32:20 CST 2015
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}