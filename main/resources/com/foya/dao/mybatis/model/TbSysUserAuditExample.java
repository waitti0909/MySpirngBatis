package com.foya.dao.mybatis.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TbSysUserAuditExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table TB_SYS_USER_AUDIT
     *
     * @mbggenerated Wed Aug 06 15:16:49 CST 2014
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table TB_SYS_USER_AUDIT
     *
     * @mbggenerated Wed Aug 06 15:16:49 CST 2014
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table TB_SYS_USER_AUDIT
     *
     * @mbggenerated Wed Aug 06 15:16:49 CST 2014
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_SYS_USER_AUDIT
     *
     * @mbggenerated Wed Aug 06 15:16:49 CST 2014
     */
    public TbSysUserAuditExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_SYS_USER_AUDIT
     *
     * @mbggenerated Wed Aug 06 15:16:49 CST 2014
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_SYS_USER_AUDIT
     *
     * @mbggenerated Wed Aug 06 15:16:49 CST 2014
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_SYS_USER_AUDIT
     *
     * @mbggenerated Wed Aug 06 15:16:49 CST 2014
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_SYS_USER_AUDIT
     *
     * @mbggenerated Wed Aug 06 15:16:49 CST 2014
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_SYS_USER_AUDIT
     *
     * @mbggenerated Wed Aug 06 15:16:49 CST 2014
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_SYS_USER_AUDIT
     *
     * @mbggenerated Wed Aug 06 15:16:49 CST 2014
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_SYS_USER_AUDIT
     *
     * @mbggenerated Wed Aug 06 15:16:49 CST 2014
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_SYS_USER_AUDIT
     *
     * @mbggenerated Wed Aug 06 15:16:49 CST 2014
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
     * This method corresponds to the database table TB_SYS_USER_AUDIT
     *
     * @mbggenerated Wed Aug 06 15:16:49 CST 2014
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_SYS_USER_AUDIT
     *
     * @mbggenerated Wed Aug 06 15:16:49 CST 2014
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table TB_SYS_USER_AUDIT
     *
     * @mbggenerated Wed Aug 06 15:16:49 CST 2014
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

        public Criteria andPSN_IDIsNull() {
            addCriterion("PSN_ID is null");
            return (Criteria) this;
        }

        public Criteria andPSN_IDIsNotNull() {
            addCriterion("PSN_ID is not null");
            return (Criteria) this;
        }

        public Criteria andPSN_IDEqualTo(BigDecimal value) {
            addCriterion("PSN_ID =", value, "PSN_ID");
            return (Criteria) this;
        }

        public Criteria andPSN_IDNotEqualTo(BigDecimal value) {
            addCriterion("PSN_ID <>", value, "PSN_ID");
            return (Criteria) this;
        }

        public Criteria andPSN_IDGreaterThan(BigDecimal value) {
            addCriterion("PSN_ID >", value, "PSN_ID");
            return (Criteria) this;
        }

        public Criteria andPSN_IDGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PSN_ID >=", value, "PSN_ID");
            return (Criteria) this;
        }

        public Criteria andPSN_IDLessThan(BigDecimal value) {
            addCriterion("PSN_ID <", value, "PSN_ID");
            return (Criteria) this;
        }

        public Criteria andPSN_IDLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PSN_ID <=", value, "PSN_ID");
            return (Criteria) this;
        }

        public Criteria andPSN_IDIn(List<BigDecimal> values) {
            addCriterion("PSN_ID in", values, "PSN_ID");
            return (Criteria) this;
        }

        public Criteria andPSN_IDNotIn(List<BigDecimal> values) {
            addCriterion("PSN_ID not in", values, "PSN_ID");
            return (Criteria) this;
        }

        public Criteria andPSN_IDBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PSN_ID between", value1, value2, "PSN_ID");
            return (Criteria) this;
        }

        public Criteria andPSN_IDNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PSN_ID not between", value1, value2, "PSN_ID");
            return (Criteria) this;
        }

        public Criteria andPSN_NAMEIsNull() {
            addCriterion("PSN_NAME is null");
            return (Criteria) this;
        }

        public Criteria andPSN_NAMEIsNotNull() {
            addCriterion("PSN_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andPSN_NAMEEqualTo(String value) {
            addCriterion("PSN_NAME =", value, "PSN_NAME");
            return (Criteria) this;
        }

        public Criteria andPSN_NAMENotEqualTo(String value) {
            addCriterion("PSN_NAME <>", value, "PSN_NAME");
            return (Criteria) this;
        }

        public Criteria andPSN_NAMEGreaterThan(String value) {
            addCriterion("PSN_NAME >", value, "PSN_NAME");
            return (Criteria) this;
        }

        public Criteria andPSN_NAMEGreaterThanOrEqualTo(String value) {
            addCriterion("PSN_NAME >=", value, "PSN_NAME");
            return (Criteria) this;
        }

        public Criteria andPSN_NAMELessThan(String value) {
            addCriterion("PSN_NAME <", value, "PSN_NAME");
            return (Criteria) this;
        }

        public Criteria andPSN_NAMELessThanOrEqualTo(String value) {
            addCriterion("PSN_NAME <=", value, "PSN_NAME");
            return (Criteria) this;
        }

        public Criteria andPSN_NAMELike(String value) {
            addCriterion("PSN_NAME like", value, "PSN_NAME");
            return (Criteria) this;
        }

        public Criteria andPSN_NAMENotLike(String value) {
            addCriterion("PSN_NAME not like", value, "PSN_NAME");
            return (Criteria) this;
        }

        public Criteria andPSN_NAMEIn(List<String> values) {
            addCriterion("PSN_NAME in", values, "PSN_NAME");
            return (Criteria) this;
        }

        public Criteria andPSN_NAMENotIn(List<String> values) {
            addCriterion("PSN_NAME not in", values, "PSN_NAME");
            return (Criteria) this;
        }

        public Criteria andPSN_NAMEBetween(String value1, String value2) {
            addCriterion("PSN_NAME between", value1, value2, "PSN_NAME");
            return (Criteria) this;
        }

        public Criteria andPSN_NAMENotBetween(String value1, String value2) {
            addCriterion("PSN_NAME not between", value1, value2, "PSN_NAME");
            return (Criteria) this;
        }

        public Criteria andURL_PATHIsNull() {
            addCriterion("URL_PATH is null");
            return (Criteria) this;
        }

        public Criteria andURL_PATHIsNotNull() {
            addCriterion("URL_PATH is not null");
            return (Criteria) this;
        }

        public Criteria andURL_PATHEqualTo(String value) {
            addCriterion("URL_PATH =", value, "URL_PATH");
            return (Criteria) this;
        }

        public Criteria andURL_PATHNotEqualTo(String value) {
            addCriterion("URL_PATH <>", value, "URL_PATH");
            return (Criteria) this;
        }

        public Criteria andURL_PATHGreaterThan(String value) {
            addCriterion("URL_PATH >", value, "URL_PATH");
            return (Criteria) this;
        }

        public Criteria andURL_PATHGreaterThanOrEqualTo(String value) {
            addCriterion("URL_PATH >=", value, "URL_PATH");
            return (Criteria) this;
        }

        public Criteria andURL_PATHLessThan(String value) {
            addCriterion("URL_PATH <", value, "URL_PATH");
            return (Criteria) this;
        }

        public Criteria andURL_PATHLessThanOrEqualTo(String value) {
            addCriterion("URL_PATH <=", value, "URL_PATH");
            return (Criteria) this;
        }

        public Criteria andURL_PATHLike(String value) {
            addCriterion("URL_PATH like", value, "URL_PATH");
            return (Criteria) this;
        }

        public Criteria andURL_PATHNotLike(String value) {
            addCriterion("URL_PATH not like", value, "URL_PATH");
            return (Criteria) this;
        }

        public Criteria andURL_PATHIn(List<String> values) {
            addCriterion("URL_PATH in", values, "URL_PATH");
            return (Criteria) this;
        }

        public Criteria andURL_PATHNotIn(List<String> values) {
            addCriterion("URL_PATH not in", values, "URL_PATH");
            return (Criteria) this;
        }

        public Criteria andURL_PATHBetween(String value1, String value2) {
            addCriterion("URL_PATH between", value1, value2, "URL_PATH");
            return (Criteria) this;
        }

        public Criteria andURL_PATHNotBetween(String value1, String value2) {
            addCriterion("URL_PATH not between", value1, value2, "URL_PATH");
            return (Criteria) this;
        }

        public Criteria andLOG_TIMEIsNull() {
            addCriterion("LOG_TIME is null");
            return (Criteria) this;
        }

        public Criteria andLOG_TIMEIsNotNull() {
            addCriterion("LOG_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andLOG_TIMEEqualTo(String value) {
            addCriterion("LOG_TIME =", value, "LOG_TIME");
            return (Criteria) this;
        }

        public Criteria andLOG_TIMENotEqualTo(String value) {
            addCriterion("LOG_TIME <>", value, "LOG_TIME");
            return (Criteria) this;
        }

        public Criteria andLOG_TIMEGreaterThan(String value) {
            addCriterion("LOG_TIME >", value, "LOG_TIME");
            return (Criteria) this;
        }

        public Criteria andLOG_TIMEGreaterThanOrEqualTo(String value) {
            addCriterion("LOG_TIME >=", value, "LOG_TIME");
            return (Criteria) this;
        }

        public Criteria andLOG_TIMELessThan(String value) {
            addCriterion("LOG_TIME <", value, "LOG_TIME");
            return (Criteria) this;
        }

        public Criteria andLOG_TIMELessThanOrEqualTo(String value) {
            addCriterion("LOG_TIME <=", value, "LOG_TIME");
            return (Criteria) this;
        }

        public Criteria andLOG_TIMELike(String value) {
            addCriterion("LOG_TIME like", value, "LOG_TIME");
            return (Criteria) this;
        }

        public Criteria andLOG_TIMENotLike(String value) {
            addCriterion("LOG_TIME not like", value, "LOG_TIME");
            return (Criteria) this;
        }

        public Criteria andLOG_TIMEIn(List<String> values) {
            addCriterion("LOG_TIME in", values, "LOG_TIME");
            return (Criteria) this;
        }

        public Criteria andLOG_TIMENotIn(List<String> values) {
            addCriterion("LOG_TIME not in", values, "LOG_TIME");
            return (Criteria) this;
        }

        public Criteria andLOG_TIMEBetween(String value1, String value2) {
            addCriterion("LOG_TIME between", value1, value2, "LOG_TIME");
            return (Criteria) this;
        }

        public Criteria andLOG_TIMENotBetween(String value1, String value2) {
            addCriterion("LOG_TIME not between", value1, value2, "LOG_TIME");
            return (Criteria) this;
        }

        public Criteria andLOG_TYPEIsNull() {
            addCriterion("LOG_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andLOG_TYPEIsNotNull() {
            addCriterion("LOG_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andLOG_TYPEEqualTo(String value) {
            addCriterion("LOG_TYPE =", value, "LOG_TYPE");
            return (Criteria) this;
        }

        public Criteria andLOG_TYPENotEqualTo(String value) {
            addCriterion("LOG_TYPE <>", value, "LOG_TYPE");
            return (Criteria) this;
        }

        public Criteria andLOG_TYPEGreaterThan(String value) {
            addCriterion("LOG_TYPE >", value, "LOG_TYPE");
            return (Criteria) this;
        }

        public Criteria andLOG_TYPEGreaterThanOrEqualTo(String value) {
            addCriterion("LOG_TYPE >=", value, "LOG_TYPE");
            return (Criteria) this;
        }

        public Criteria andLOG_TYPELessThan(String value) {
            addCriterion("LOG_TYPE <", value, "LOG_TYPE");
            return (Criteria) this;
        }

        public Criteria andLOG_TYPELessThanOrEqualTo(String value) {
            addCriterion("LOG_TYPE <=", value, "LOG_TYPE");
            return (Criteria) this;
        }

        public Criteria andLOG_TYPELike(String value) {
            addCriterion("LOG_TYPE like", value, "LOG_TYPE");
            return (Criteria) this;
        }

        public Criteria andLOG_TYPENotLike(String value) {
            addCriterion("LOG_TYPE not like", value, "LOG_TYPE");
            return (Criteria) this;
        }

        public Criteria andLOG_TYPEIn(List<String> values) {
            addCriterion("LOG_TYPE in", values, "LOG_TYPE");
            return (Criteria) this;
        }

        public Criteria andLOG_TYPENotIn(List<String> values) {
            addCriterion("LOG_TYPE not in", values, "LOG_TYPE");
            return (Criteria) this;
        }

        public Criteria andLOG_TYPEBetween(String value1, String value2) {
            addCriterion("LOG_TYPE between", value1, value2, "LOG_TYPE");
            return (Criteria) this;
        }

        public Criteria andLOG_TYPENotBetween(String value1, String value2) {
            addCriterion("LOG_TYPE not between", value1, value2, "LOG_TYPE");
            return (Criteria) this;
        }

        public Criteria andLOG_DESCRIPTIONIsNull() {
            addCriterion("LOG_DESCRIPTION is null");
            return (Criteria) this;
        }

        public Criteria andLOG_DESCRIPTIONIsNotNull() {
            addCriterion("LOG_DESCRIPTION is not null");
            return (Criteria) this;
        }

        public Criteria andLOG_DESCRIPTIONEqualTo(String value) {
            addCriterion("LOG_DESCRIPTION =", value, "LOG_DESCRIPTION");
            return (Criteria) this;
        }

        public Criteria andLOG_DESCRIPTIONNotEqualTo(String value) {
            addCriterion("LOG_DESCRIPTION <>", value, "LOG_DESCRIPTION");
            return (Criteria) this;
        }

        public Criteria andLOG_DESCRIPTIONGreaterThan(String value) {
            addCriterion("LOG_DESCRIPTION >", value, "LOG_DESCRIPTION");
            return (Criteria) this;
        }

        public Criteria andLOG_DESCRIPTIONGreaterThanOrEqualTo(String value) {
            addCriterion("LOG_DESCRIPTION >=", value, "LOG_DESCRIPTION");
            return (Criteria) this;
        }

        public Criteria andLOG_DESCRIPTIONLessThan(String value) {
            addCriterion("LOG_DESCRIPTION <", value, "LOG_DESCRIPTION");
            return (Criteria) this;
        }

        public Criteria andLOG_DESCRIPTIONLessThanOrEqualTo(String value) {
            addCriterion("LOG_DESCRIPTION <=", value, "LOG_DESCRIPTION");
            return (Criteria) this;
        }

        public Criteria andLOG_DESCRIPTIONLike(String value) {
            addCriterion("LOG_DESCRIPTION like", value, "LOG_DESCRIPTION");
            return (Criteria) this;
        }

        public Criteria andLOG_DESCRIPTIONNotLike(String value) {
            addCriterion("LOG_DESCRIPTION not like", value, "LOG_DESCRIPTION");
            return (Criteria) this;
        }

        public Criteria andLOG_DESCRIPTIONIn(List<String> values) {
            addCriterion("LOG_DESCRIPTION in", values, "LOG_DESCRIPTION");
            return (Criteria) this;
        }

        public Criteria andLOG_DESCRIPTIONNotIn(List<String> values) {
            addCriterion("LOG_DESCRIPTION not in", values, "LOG_DESCRIPTION");
            return (Criteria) this;
        }

        public Criteria andLOG_DESCRIPTIONBetween(String value1, String value2) {
            addCriterion("LOG_DESCRIPTION between", value1, value2, "LOG_DESCRIPTION");
            return (Criteria) this;
        }

        public Criteria andLOG_DESCRIPTIONNotBetween(String value1, String value2) {
            addCriterion("LOG_DESCRIPTION not between", value1, value2, "LOG_DESCRIPTION");
            return (Criteria) this;
        }

        public Criteria andUSER_IPIsNull() {
            addCriterion("USER_IP is null");
            return (Criteria) this;
        }

        public Criteria andUSER_IPIsNotNull() {
            addCriterion("USER_IP is not null");
            return (Criteria) this;
        }

        public Criteria andUSER_IPEqualTo(String value) {
            addCriterion("USER_IP =", value, "USER_IP");
            return (Criteria) this;
        }

        public Criteria andUSER_IPNotEqualTo(String value) {
            addCriterion("USER_IP <>", value, "USER_IP");
            return (Criteria) this;
        }

        public Criteria andUSER_IPGreaterThan(String value) {
            addCriterion("USER_IP >", value, "USER_IP");
            return (Criteria) this;
        }

        public Criteria andUSER_IPGreaterThanOrEqualTo(String value) {
            addCriterion("USER_IP >=", value, "USER_IP");
            return (Criteria) this;
        }

        public Criteria andUSER_IPLessThan(String value) {
            addCriterion("USER_IP <", value, "USER_IP");
            return (Criteria) this;
        }

        public Criteria andUSER_IPLessThanOrEqualTo(String value) {
            addCriterion("USER_IP <=", value, "USER_IP");
            return (Criteria) this;
        }

        public Criteria andUSER_IPLike(String value) {
            addCriterion("USER_IP like", value, "USER_IP");
            return (Criteria) this;
        }

        public Criteria andUSER_IPNotLike(String value) {
            addCriterion("USER_IP not like", value, "USER_IP");
            return (Criteria) this;
        }

        public Criteria andUSER_IPIn(List<String> values) {
            addCriterion("USER_IP in", values, "USER_IP");
            return (Criteria) this;
        }

        public Criteria andUSER_IPNotIn(List<String> values) {
            addCriterion("USER_IP not in", values, "USER_IP");
            return (Criteria) this;
        }

        public Criteria andUSER_IPBetween(String value1, String value2) {
            addCriterion("USER_IP between", value1, value2, "USER_IP");
            return (Criteria) this;
        }

        public Criteria andUSER_IPNotBetween(String value1, String value2) {
            addCriterion("USER_IP not between", value1, value2, "USER_IP");
            return (Criteria) this;
        }

        public Criteria andSESSION_IDIsNull() {
            addCriterion("SESSION_ID is null");
            return (Criteria) this;
        }

        public Criteria andSESSION_IDIsNotNull() {
            addCriterion("SESSION_ID is not null");
            return (Criteria) this;
        }

        public Criteria andSESSION_IDEqualTo(String value) {
            addCriterion("SESSION_ID =", value, "SESSION_ID");
            return (Criteria) this;
        }

        public Criteria andSESSION_IDNotEqualTo(String value) {
            addCriterion("SESSION_ID <>", value, "SESSION_ID");
            return (Criteria) this;
        }

        public Criteria andSESSION_IDGreaterThan(String value) {
            addCriterion("SESSION_ID >", value, "SESSION_ID");
            return (Criteria) this;
        }

        public Criteria andSESSION_IDGreaterThanOrEqualTo(String value) {
            addCriterion("SESSION_ID >=", value, "SESSION_ID");
            return (Criteria) this;
        }

        public Criteria andSESSION_IDLessThan(String value) {
            addCriterion("SESSION_ID <", value, "SESSION_ID");
            return (Criteria) this;
        }

        public Criteria andSESSION_IDLessThanOrEqualTo(String value) {
            addCriterion("SESSION_ID <=", value, "SESSION_ID");
            return (Criteria) this;
        }

        public Criteria andSESSION_IDLike(String value) {
            addCriterion("SESSION_ID like", value, "SESSION_ID");
            return (Criteria) this;
        }

        public Criteria andSESSION_IDNotLike(String value) {
            addCriterion("SESSION_ID not like", value, "SESSION_ID");
            return (Criteria) this;
        }

        public Criteria andSESSION_IDIn(List<String> values) {
            addCriterion("SESSION_ID in", values, "SESSION_ID");
            return (Criteria) this;
        }

        public Criteria andSESSION_IDNotIn(List<String> values) {
            addCriterion("SESSION_ID not in", values, "SESSION_ID");
            return (Criteria) this;
        }

        public Criteria andSESSION_IDBetween(String value1, String value2) {
            addCriterion("SESSION_ID between", value1, value2, "SESSION_ID");
            return (Criteria) this;
        }

        public Criteria andSESSION_IDNotBetween(String value1, String value2) {
            addCriterion("SESSION_ID not between", value1, value2, "SESSION_ID");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table TB_SYS_USER_AUDIT
     *
     * @mbggenerated do_not_delete_during_merge Wed Aug 06 15:16:49 CST 2014
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table TB_SYS_USER_AUDIT
     *
     * @mbggenerated Wed Aug 06 15:16:49 CST 2014
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