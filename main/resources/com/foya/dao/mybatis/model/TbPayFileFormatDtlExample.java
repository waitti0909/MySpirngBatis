package com.foya.dao.mybatis.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TbPayFileFormatDtlExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table TB_PAY_FILE_FORMAT_DTL
     *
     * @mbggenerated Thu Apr 09 18:36:00 CST 2015
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table TB_PAY_FILE_FORMAT_DTL
     *
     * @mbggenerated Thu Apr 09 18:36:00 CST 2015
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table TB_PAY_FILE_FORMAT_DTL
     *
     * @mbggenerated Thu Apr 09 18:36:00 CST 2015
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_PAY_FILE_FORMAT_DTL
     *
     * @mbggenerated Thu Apr 09 18:36:00 CST 2015
     */
    public TbPayFileFormatDtlExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_PAY_FILE_FORMAT_DTL
     *
     * @mbggenerated Thu Apr 09 18:36:00 CST 2015
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_PAY_FILE_FORMAT_DTL
     *
     * @mbggenerated Thu Apr 09 18:36:00 CST 2015
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_PAY_FILE_FORMAT_DTL
     *
     * @mbggenerated Thu Apr 09 18:36:00 CST 2015
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_PAY_FILE_FORMAT_DTL
     *
     * @mbggenerated Thu Apr 09 18:36:00 CST 2015
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_PAY_FILE_FORMAT_DTL
     *
     * @mbggenerated Thu Apr 09 18:36:00 CST 2015
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_PAY_FILE_FORMAT_DTL
     *
     * @mbggenerated Thu Apr 09 18:36:00 CST 2015
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_PAY_FILE_FORMAT_DTL
     *
     * @mbggenerated Thu Apr 09 18:36:00 CST 2015
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_PAY_FILE_FORMAT_DTL
     *
     * @mbggenerated Thu Apr 09 18:36:00 CST 2015
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
     * This method corresponds to the database table TB_PAY_FILE_FORMAT_DTL
     *
     * @mbggenerated Thu Apr 09 18:36:00 CST 2015
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_PAY_FILE_FORMAT_DTL
     *
     * @mbggenerated Thu Apr 09 18:36:00 CST 2015
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table TB_PAY_FILE_FORMAT_DTL
     *
     * @mbggenerated Thu Apr 09 18:36:00 CST 2015
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

        public Criteria andMST_SEQ_NBRIsNull() {
            addCriterion("MST_SEQ_NBR is null");
            return (Criteria) this;
        }

        public Criteria andMST_SEQ_NBRIsNotNull() {
            addCriterion("MST_SEQ_NBR is not null");
            return (Criteria) this;
        }

        public Criteria andMST_SEQ_NBREqualTo(Long value) {
            addCriterion("MST_SEQ_NBR =", value, "MST_SEQ_NBR");
            return (Criteria) this;
        }

        public Criteria andMST_SEQ_NBRNotEqualTo(Long value) {
            addCriterion("MST_SEQ_NBR <>", value, "MST_SEQ_NBR");
            return (Criteria) this;
        }

        public Criteria andMST_SEQ_NBRGreaterThan(Long value) {
            addCriterion("MST_SEQ_NBR >", value, "MST_SEQ_NBR");
            return (Criteria) this;
        }

        public Criteria andMST_SEQ_NBRGreaterThanOrEqualTo(Long value) {
            addCriterion("MST_SEQ_NBR >=", value, "MST_SEQ_NBR");
            return (Criteria) this;
        }

        public Criteria andMST_SEQ_NBRLessThan(Long value) {
            addCriterion("MST_SEQ_NBR <", value, "MST_SEQ_NBR");
            return (Criteria) this;
        }

        public Criteria andMST_SEQ_NBRLessThanOrEqualTo(Long value) {
            addCriterion("MST_SEQ_NBR <=", value, "MST_SEQ_NBR");
            return (Criteria) this;
        }

        public Criteria andMST_SEQ_NBRIn(List<Long> values) {
            addCriterion("MST_SEQ_NBR in", values, "MST_SEQ_NBR");
            return (Criteria) this;
        }

        public Criteria andMST_SEQ_NBRNotIn(List<Long> values) {
            addCriterion("MST_SEQ_NBR not in", values, "MST_SEQ_NBR");
            return (Criteria) this;
        }

        public Criteria andMST_SEQ_NBRBetween(Long value1, Long value2) {
            addCriterion("MST_SEQ_NBR between", value1, value2, "MST_SEQ_NBR");
            return (Criteria) this;
        }

        public Criteria andMST_SEQ_NBRNotBetween(Long value1, Long value2) {
            addCriterion("MST_SEQ_NBR not between", value1, value2, "MST_SEQ_NBR");
            return (Criteria) this;
        }

        public Criteria andCOL_NAMEIsNull() {
            addCriterion("COL_NAME is null");
            return (Criteria) this;
        }

        public Criteria andCOL_NAMEIsNotNull() {
            addCriterion("COL_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andCOL_NAMEEqualTo(String value) {
            addCriterion("COL_NAME =", value, "COL_NAME");
            return (Criteria) this;
        }

        public Criteria andCOL_NAMENotEqualTo(String value) {
            addCriterion("COL_NAME <>", value, "COL_NAME");
            return (Criteria) this;
        }

        public Criteria andCOL_NAMEGreaterThan(String value) {
            addCriterion("COL_NAME >", value, "COL_NAME");
            return (Criteria) this;
        }

        public Criteria andCOL_NAMEGreaterThanOrEqualTo(String value) {
            addCriterion("COL_NAME >=", value, "COL_NAME");
            return (Criteria) this;
        }

        public Criteria andCOL_NAMELessThan(String value) {
            addCriterion("COL_NAME <", value, "COL_NAME");
            return (Criteria) this;
        }

        public Criteria andCOL_NAMELessThanOrEqualTo(String value) {
            addCriterion("COL_NAME <=", value, "COL_NAME");
            return (Criteria) this;
        }

        public Criteria andCOL_NAMELike(String value) {
            addCriterion("COL_NAME like", value, "COL_NAME");
            return (Criteria) this;
        }

        public Criteria andCOL_NAMENotLike(String value) {
            addCriterion("COL_NAME not like", value, "COL_NAME");
            return (Criteria) this;
        }

        public Criteria andCOL_NAMEIn(List<String> values) {
            addCriterion("COL_NAME in", values, "COL_NAME");
            return (Criteria) this;
        }

        public Criteria andCOL_NAMENotIn(List<String> values) {
            addCriterion("COL_NAME not in", values, "COL_NAME");
            return (Criteria) this;
        }

        public Criteria andCOL_NAMEBetween(String value1, String value2) {
            addCriterion("COL_NAME between", value1, value2, "COL_NAME");
            return (Criteria) this;
        }

        public Criteria andCOL_NAMENotBetween(String value1, String value2) {
            addCriterion("COL_NAME not between", value1, value2, "COL_NAME");
            return (Criteria) this;
        }

        public Criteria andCOL_DESCIsNull() {
            addCriterion("COL_DESC is null");
            return (Criteria) this;
        }

        public Criteria andCOL_DESCIsNotNull() {
            addCriterion("COL_DESC is not null");
            return (Criteria) this;
        }

        public Criteria andCOL_DESCEqualTo(String value) {
            addCriterion("COL_DESC =", value, "COL_DESC");
            return (Criteria) this;
        }

        public Criteria andCOL_DESCNotEqualTo(String value) {
            addCriterion("COL_DESC <>", value, "COL_DESC");
            return (Criteria) this;
        }

        public Criteria andCOL_DESCGreaterThan(String value) {
            addCriterion("COL_DESC >", value, "COL_DESC");
            return (Criteria) this;
        }

        public Criteria andCOL_DESCGreaterThanOrEqualTo(String value) {
            addCriterion("COL_DESC >=", value, "COL_DESC");
            return (Criteria) this;
        }

        public Criteria andCOL_DESCLessThan(String value) {
            addCriterion("COL_DESC <", value, "COL_DESC");
            return (Criteria) this;
        }

        public Criteria andCOL_DESCLessThanOrEqualTo(String value) {
            addCriterion("COL_DESC <=", value, "COL_DESC");
            return (Criteria) this;
        }

        public Criteria andCOL_DESCLike(String value) {
            addCriterion("COL_DESC like", value, "COL_DESC");
            return (Criteria) this;
        }

        public Criteria andCOL_DESCNotLike(String value) {
            addCriterion("COL_DESC not like", value, "COL_DESC");
            return (Criteria) this;
        }

        public Criteria andCOL_DESCIn(List<String> values) {
            addCriterion("COL_DESC in", values, "COL_DESC");
            return (Criteria) this;
        }

        public Criteria andCOL_DESCNotIn(List<String> values) {
            addCriterion("COL_DESC not in", values, "COL_DESC");
            return (Criteria) this;
        }

        public Criteria andCOL_DESCBetween(String value1, String value2) {
            addCriterion("COL_DESC between", value1, value2, "COL_DESC");
            return (Criteria) this;
        }

        public Criteria andCOL_DESCNotBetween(String value1, String value2) {
            addCriterion("COL_DESC not between", value1, value2, "COL_DESC");
            return (Criteria) this;
        }

        public Criteria andCOL_ORDERIsNull() {
            addCriterion("COL_ORDER is null");
            return (Criteria) this;
        }

        public Criteria andCOL_ORDERIsNotNull() {
            addCriterion("COL_ORDER is not null");
            return (Criteria) this;
        }

        public Criteria andCOL_ORDEREqualTo(Short value) {
            addCriterion("COL_ORDER =", value, "COL_ORDER");
            return (Criteria) this;
        }

        public Criteria andCOL_ORDERNotEqualTo(Short value) {
            addCriterion("COL_ORDER <>", value, "COL_ORDER");
            return (Criteria) this;
        }

        public Criteria andCOL_ORDERGreaterThan(Short value) {
            addCriterion("COL_ORDER >", value, "COL_ORDER");
            return (Criteria) this;
        }

        public Criteria andCOL_ORDERGreaterThanOrEqualTo(Short value) {
            addCriterion("COL_ORDER >=", value, "COL_ORDER");
            return (Criteria) this;
        }

        public Criteria andCOL_ORDERLessThan(Short value) {
            addCriterion("COL_ORDER <", value, "COL_ORDER");
            return (Criteria) this;
        }

        public Criteria andCOL_ORDERLessThanOrEqualTo(Short value) {
            addCriterion("COL_ORDER <=", value, "COL_ORDER");
            return (Criteria) this;
        }

        public Criteria andCOL_ORDERIn(List<Short> values) {
            addCriterion("COL_ORDER in", values, "COL_ORDER");
            return (Criteria) this;
        }

        public Criteria andCOL_ORDERNotIn(List<Short> values) {
            addCriterion("COL_ORDER not in", values, "COL_ORDER");
            return (Criteria) this;
        }

        public Criteria andCOL_ORDERBetween(Short value1, Short value2) {
            addCriterion("COL_ORDER between", value1, value2, "COL_ORDER");
            return (Criteria) this;
        }

        public Criteria andCOL_ORDERNotBetween(Short value1, Short value2) {
            addCriterion("COL_ORDER not between", value1, value2, "COL_ORDER");
            return (Criteria) this;
        }

        public Criteria andCOL_LENGTHIsNull() {
            addCriterion("COL_LENGTH is null");
            return (Criteria) this;
        }

        public Criteria andCOL_LENGTHIsNotNull() {
            addCriterion("COL_LENGTH is not null");
            return (Criteria) this;
        }

        public Criteria andCOL_LENGTHEqualTo(Short value) {
            addCriterion("COL_LENGTH =", value, "COL_LENGTH");
            return (Criteria) this;
        }

        public Criteria andCOL_LENGTHNotEqualTo(Short value) {
            addCriterion("COL_LENGTH <>", value, "COL_LENGTH");
            return (Criteria) this;
        }

        public Criteria andCOL_LENGTHGreaterThan(Short value) {
            addCriterion("COL_LENGTH >", value, "COL_LENGTH");
            return (Criteria) this;
        }

        public Criteria andCOL_LENGTHGreaterThanOrEqualTo(Short value) {
            addCriterion("COL_LENGTH >=", value, "COL_LENGTH");
            return (Criteria) this;
        }

        public Criteria andCOL_LENGTHLessThan(Short value) {
            addCriterion("COL_LENGTH <", value, "COL_LENGTH");
            return (Criteria) this;
        }

        public Criteria andCOL_LENGTHLessThanOrEqualTo(Short value) {
            addCriterion("COL_LENGTH <=", value, "COL_LENGTH");
            return (Criteria) this;
        }

        public Criteria andCOL_LENGTHIn(List<Short> values) {
            addCriterion("COL_LENGTH in", values, "COL_LENGTH");
            return (Criteria) this;
        }

        public Criteria andCOL_LENGTHNotIn(List<Short> values) {
            addCriterion("COL_LENGTH not in", values, "COL_LENGTH");
            return (Criteria) this;
        }

        public Criteria andCOL_LENGTHBetween(Short value1, Short value2) {
            addCriterion("COL_LENGTH between", value1, value2, "COL_LENGTH");
            return (Criteria) this;
        }

        public Criteria andCOL_LENGTHNotBetween(Short value1, Short value2) {
            addCriterion("COL_LENGTH not between", value1, value2, "COL_LENGTH");
            return (Criteria) this;
        }

        public Criteria andFILL_UP_CHARIsNull() {
            addCriterion("FILL_UP_CHAR is null");
            return (Criteria) this;
        }

        public Criteria andFILL_UP_CHARIsNotNull() {
            addCriterion("FILL_UP_CHAR is not null");
            return (Criteria) this;
        }

        public Criteria andFILL_UP_CHAREqualTo(String value) {
            addCriterion("FILL_UP_CHAR =", value, "FILL_UP_CHAR");
            return (Criteria) this;
        }

        public Criteria andFILL_UP_CHARNotEqualTo(String value) {
            addCriterion("FILL_UP_CHAR <>", value, "FILL_UP_CHAR");
            return (Criteria) this;
        }

        public Criteria andFILL_UP_CHARGreaterThan(String value) {
            addCriterion("FILL_UP_CHAR >", value, "FILL_UP_CHAR");
            return (Criteria) this;
        }

        public Criteria andFILL_UP_CHARGreaterThanOrEqualTo(String value) {
            addCriterion("FILL_UP_CHAR >=", value, "FILL_UP_CHAR");
            return (Criteria) this;
        }

        public Criteria andFILL_UP_CHARLessThan(String value) {
            addCriterion("FILL_UP_CHAR <", value, "FILL_UP_CHAR");
            return (Criteria) this;
        }

        public Criteria andFILL_UP_CHARLessThanOrEqualTo(String value) {
            addCriterion("FILL_UP_CHAR <=", value, "FILL_UP_CHAR");
            return (Criteria) this;
        }

        public Criteria andFILL_UP_CHARLike(String value) {
            addCriterion("FILL_UP_CHAR like", value, "FILL_UP_CHAR");
            return (Criteria) this;
        }

        public Criteria andFILL_UP_CHARNotLike(String value) {
            addCriterion("FILL_UP_CHAR not like", value, "FILL_UP_CHAR");
            return (Criteria) this;
        }

        public Criteria andFILL_UP_CHARIn(List<String> values) {
            addCriterion("FILL_UP_CHAR in", values, "FILL_UP_CHAR");
            return (Criteria) this;
        }

        public Criteria andFILL_UP_CHARNotIn(List<String> values) {
            addCriterion("FILL_UP_CHAR not in", values, "FILL_UP_CHAR");
            return (Criteria) this;
        }

        public Criteria andFILL_UP_CHARBetween(String value1, String value2) {
            addCriterion("FILL_UP_CHAR between", value1, value2, "FILL_UP_CHAR");
            return (Criteria) this;
        }

        public Criteria andFILL_UP_CHARNotBetween(String value1, String value2) {
            addCriterion("FILL_UP_CHAR not between", value1, value2, "FILL_UP_CHAR");
            return (Criteria) this;
        }

        public Criteria andFIXED_VALUEIsNull() {
            addCriterion("FIXED_VALUE is null");
            return (Criteria) this;
        }

        public Criteria andFIXED_VALUEIsNotNull() {
            addCriterion("FIXED_VALUE is not null");
            return (Criteria) this;
        }

        public Criteria andFIXED_VALUEEqualTo(String value) {
            addCriterion("FIXED_VALUE =", value, "FIXED_VALUE");
            return (Criteria) this;
        }

        public Criteria andFIXED_VALUENotEqualTo(String value) {
            addCriterion("FIXED_VALUE <>", value, "FIXED_VALUE");
            return (Criteria) this;
        }

        public Criteria andFIXED_VALUEGreaterThan(String value) {
            addCriterion("FIXED_VALUE >", value, "FIXED_VALUE");
            return (Criteria) this;
        }

        public Criteria andFIXED_VALUEGreaterThanOrEqualTo(String value) {
            addCriterion("FIXED_VALUE >=", value, "FIXED_VALUE");
            return (Criteria) this;
        }

        public Criteria andFIXED_VALUELessThan(String value) {
            addCriterion("FIXED_VALUE <", value, "FIXED_VALUE");
            return (Criteria) this;
        }

        public Criteria andFIXED_VALUELessThanOrEqualTo(String value) {
            addCriterion("FIXED_VALUE <=", value, "FIXED_VALUE");
            return (Criteria) this;
        }

        public Criteria andFIXED_VALUELike(String value) {
            addCriterion("FIXED_VALUE like", value, "FIXED_VALUE");
            return (Criteria) this;
        }

        public Criteria andFIXED_VALUENotLike(String value) {
            addCriterion("FIXED_VALUE not like", value, "FIXED_VALUE");
            return (Criteria) this;
        }

        public Criteria andFIXED_VALUEIn(List<String> values) {
            addCriterion("FIXED_VALUE in", values, "FIXED_VALUE");
            return (Criteria) this;
        }

        public Criteria andFIXED_VALUENotIn(List<String> values) {
            addCriterion("FIXED_VALUE not in", values, "FIXED_VALUE");
            return (Criteria) this;
        }

        public Criteria andFIXED_VALUEBetween(String value1, String value2) {
            addCriterion("FIXED_VALUE between", value1, value2, "FIXED_VALUE");
            return (Criteria) this;
        }

        public Criteria andFIXED_VALUENotBetween(String value1, String value2) {
            addCriterion("FIXED_VALUE not between", value1, value2, "FIXED_VALUE");
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

        public Criteria andIF_LEFT_PADDINGIsNull() {
            addCriterion("IF_LEFT_PADDING is null");
            return (Criteria) this;
        }

        public Criteria andIF_LEFT_PADDINGIsNotNull() {
            addCriterion("IF_LEFT_PADDING is not null");
            return (Criteria) this;
        }

        public Criteria andIF_LEFT_PADDINGEqualTo(String value) {
            addCriterion("IF_LEFT_PADDING =", value, "IF_LEFT_PADDING");
            return (Criteria) this;
        }

        public Criteria andIF_LEFT_PADDINGNotEqualTo(String value) {
            addCriterion("IF_LEFT_PADDING <>", value, "IF_LEFT_PADDING");
            return (Criteria) this;
        }

        public Criteria andIF_LEFT_PADDINGGreaterThan(String value) {
            addCriterion("IF_LEFT_PADDING >", value, "IF_LEFT_PADDING");
            return (Criteria) this;
        }

        public Criteria andIF_LEFT_PADDINGGreaterThanOrEqualTo(String value) {
            addCriterion("IF_LEFT_PADDING >=", value, "IF_LEFT_PADDING");
            return (Criteria) this;
        }

        public Criteria andIF_LEFT_PADDINGLessThan(String value) {
            addCriterion("IF_LEFT_PADDING <", value, "IF_LEFT_PADDING");
            return (Criteria) this;
        }

        public Criteria andIF_LEFT_PADDINGLessThanOrEqualTo(String value) {
            addCriterion("IF_LEFT_PADDING <=", value, "IF_LEFT_PADDING");
            return (Criteria) this;
        }

        public Criteria andIF_LEFT_PADDINGLike(String value) {
            addCriterion("IF_LEFT_PADDING like", value, "IF_LEFT_PADDING");
            return (Criteria) this;
        }

        public Criteria andIF_LEFT_PADDINGNotLike(String value) {
            addCriterion("IF_LEFT_PADDING not like", value, "IF_LEFT_PADDING");
            return (Criteria) this;
        }

        public Criteria andIF_LEFT_PADDINGIn(List<String> values) {
            addCriterion("IF_LEFT_PADDING in", values, "IF_LEFT_PADDING");
            return (Criteria) this;
        }

        public Criteria andIF_LEFT_PADDINGNotIn(List<String> values) {
            addCriterion("IF_LEFT_PADDING not in", values, "IF_LEFT_PADDING");
            return (Criteria) this;
        }

        public Criteria andIF_LEFT_PADDINGBetween(String value1, String value2) {
            addCriterion("IF_LEFT_PADDING between", value1, value2, "IF_LEFT_PADDING");
            return (Criteria) this;
        }

        public Criteria andIF_LEFT_PADDINGNotBetween(String value1, String value2) {
            addCriterion("IF_LEFT_PADDING not between", value1, value2, "IF_LEFT_PADDING");
            return (Criteria) this;
        }

        public Criteria andALLOW_NULLIsNull() {
            addCriterion("ALLOW_NULL is null");
            return (Criteria) this;
        }

        public Criteria andALLOW_NULLIsNotNull() {
            addCriterion("ALLOW_NULL is not null");
            return (Criteria) this;
        }

        public Criteria andALLOW_NULLEqualTo(String value) {
            addCriterion("ALLOW_NULL =", value, "ALLOW_NULL");
            return (Criteria) this;
        }

        public Criteria andALLOW_NULLNotEqualTo(String value) {
            addCriterion("ALLOW_NULL <>", value, "ALLOW_NULL");
            return (Criteria) this;
        }

        public Criteria andALLOW_NULLGreaterThan(String value) {
            addCriterion("ALLOW_NULL >", value, "ALLOW_NULL");
            return (Criteria) this;
        }

        public Criteria andALLOW_NULLGreaterThanOrEqualTo(String value) {
            addCriterion("ALLOW_NULL >=", value, "ALLOW_NULL");
            return (Criteria) this;
        }

        public Criteria andALLOW_NULLLessThan(String value) {
            addCriterion("ALLOW_NULL <", value, "ALLOW_NULL");
            return (Criteria) this;
        }

        public Criteria andALLOW_NULLLessThanOrEqualTo(String value) {
            addCriterion("ALLOW_NULL <=", value, "ALLOW_NULL");
            return (Criteria) this;
        }

        public Criteria andALLOW_NULLLike(String value) {
            addCriterion("ALLOW_NULL like", value, "ALLOW_NULL");
            return (Criteria) this;
        }

        public Criteria andALLOW_NULLNotLike(String value) {
            addCriterion("ALLOW_NULL not like", value, "ALLOW_NULL");
            return (Criteria) this;
        }

        public Criteria andALLOW_NULLIn(List<String> values) {
            addCriterion("ALLOW_NULL in", values, "ALLOW_NULL");
            return (Criteria) this;
        }

        public Criteria andALLOW_NULLNotIn(List<String> values) {
            addCriterion("ALLOW_NULL not in", values, "ALLOW_NULL");
            return (Criteria) this;
        }

        public Criteria andALLOW_NULLBetween(String value1, String value2) {
            addCriterion("ALLOW_NULL between", value1, value2, "ALLOW_NULL");
            return (Criteria) this;
        }

        public Criteria andALLOW_NULLNotBetween(String value1, String value2) {
            addCriterion("ALLOW_NULL not between", value1, value2, "ALLOW_NULL");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table TB_PAY_FILE_FORMAT_DTL
     *
     * @mbggenerated do_not_delete_during_merge Thu Apr 09 18:36:00 CST 2015
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table TB_PAY_FILE_FORMAT_DTL
     *
     * @mbggenerated Thu Apr 09 18:36:00 CST 2015
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