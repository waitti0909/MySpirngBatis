package com.foya.dao.mybatis.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TbAuthRoleMenuFunExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table TB_AUTH_ROLE_MENU_FUN
     *
     * @mbggenerated Wed Aug 13 16:52:10 CST 2014
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table TB_AUTH_ROLE_MENU_FUN
     *
     * @mbggenerated Wed Aug 13 16:52:10 CST 2014
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table TB_AUTH_ROLE_MENU_FUN
     *
     * @mbggenerated Wed Aug 13 16:52:10 CST 2014
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_AUTH_ROLE_MENU_FUN
     *
     * @mbggenerated Wed Aug 13 16:52:10 CST 2014
     */
    public TbAuthRoleMenuFunExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_AUTH_ROLE_MENU_FUN
     *
     * @mbggenerated Wed Aug 13 16:52:10 CST 2014
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_AUTH_ROLE_MENU_FUN
     *
     * @mbggenerated Wed Aug 13 16:52:10 CST 2014
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_AUTH_ROLE_MENU_FUN
     *
     * @mbggenerated Wed Aug 13 16:52:10 CST 2014
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_AUTH_ROLE_MENU_FUN
     *
     * @mbggenerated Wed Aug 13 16:52:10 CST 2014
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_AUTH_ROLE_MENU_FUN
     *
     * @mbggenerated Wed Aug 13 16:52:10 CST 2014
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_AUTH_ROLE_MENU_FUN
     *
     * @mbggenerated Wed Aug 13 16:52:10 CST 2014
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_AUTH_ROLE_MENU_FUN
     *
     * @mbggenerated Wed Aug 13 16:52:10 CST 2014
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_AUTH_ROLE_MENU_FUN
     *
     * @mbggenerated Wed Aug 13 16:52:10 CST 2014
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
     * This method corresponds to the database table TB_AUTH_ROLE_MENU_FUN
     *
     * @mbggenerated Wed Aug 13 16:52:10 CST 2014
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_AUTH_ROLE_MENU_FUN
     *
     * @mbggenerated Wed Aug 13 16:52:10 CST 2014
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table TB_AUTH_ROLE_MENU_FUN
     *
     * @mbggenerated Wed Aug 13 16:52:10 CST 2014
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

        public Criteria andROLE_MENU_FUN_IDIsNull() {
            addCriterion("ROLE_MENU_FUN_ID is null");
            return (Criteria) this;
        }

        public Criteria andROLE_MENU_FUN_IDIsNotNull() {
            addCriterion("ROLE_MENU_FUN_ID is not null");
            return (Criteria) this;
        }

        public Criteria andROLE_MENU_FUN_IDEqualTo(BigDecimal value) {
            addCriterion("ROLE_MENU_FUN_ID =", value, "ROLE_MENU_FUN_ID");
            return (Criteria) this;
        }

        public Criteria andROLE_MENU_FUN_IDNotEqualTo(BigDecimal value) {
            addCriterion("ROLE_MENU_FUN_ID <>", value, "ROLE_MENU_FUN_ID");
            return (Criteria) this;
        }

        public Criteria andROLE_MENU_FUN_IDGreaterThan(BigDecimal value) {
            addCriterion("ROLE_MENU_FUN_ID >", value, "ROLE_MENU_FUN_ID");
            return (Criteria) this;
        }

        public Criteria andROLE_MENU_FUN_IDGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ROLE_MENU_FUN_ID >=", value, "ROLE_MENU_FUN_ID");
            return (Criteria) this;
        }

        public Criteria andROLE_MENU_FUN_IDLessThan(BigDecimal value) {
            addCriterion("ROLE_MENU_FUN_ID <", value, "ROLE_MENU_FUN_ID");
            return (Criteria) this;
        }

        public Criteria andROLE_MENU_FUN_IDLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ROLE_MENU_FUN_ID <=", value, "ROLE_MENU_FUN_ID");
            return (Criteria) this;
        }

        public Criteria andROLE_MENU_FUN_IDIn(List<BigDecimal> values) {
            addCriterion("ROLE_MENU_FUN_ID in", values, "ROLE_MENU_FUN_ID");
            return (Criteria) this;
        }

        public Criteria andROLE_MENU_FUN_IDNotIn(List<BigDecimal> values) {
            addCriterion("ROLE_MENU_FUN_ID not in", values, "ROLE_MENU_FUN_ID");
            return (Criteria) this;
        }

        public Criteria andROLE_MENU_FUN_IDBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ROLE_MENU_FUN_ID between", value1, value2, "ROLE_MENU_FUN_ID");
            return (Criteria) this;
        }

        public Criteria andROLE_MENU_FUN_IDNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ROLE_MENU_FUN_ID not between", value1, value2, "ROLE_MENU_FUN_ID");
            return (Criteria) this;
        }

        public Criteria andROLE_IDIsNull() {
            addCriterion("ROLE_ID is null");
            return (Criteria) this;
        }

        public Criteria andROLE_IDIsNotNull() {
            addCriterion("ROLE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andROLE_IDEqualTo(BigDecimal value) {
            addCriterion("ROLE_ID =", value, "ROLE_ID");
            return (Criteria) this;
        }

        public Criteria andROLE_IDNotEqualTo(BigDecimal value) {
            addCriterion("ROLE_ID <>", value, "ROLE_ID");
            return (Criteria) this;
        }

        public Criteria andROLE_IDGreaterThan(BigDecimal value) {
            addCriterion("ROLE_ID >", value, "ROLE_ID");
            return (Criteria) this;
        }

        public Criteria andROLE_IDGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ROLE_ID >=", value, "ROLE_ID");
            return (Criteria) this;
        }

        public Criteria andROLE_IDLessThan(BigDecimal value) {
            addCriterion("ROLE_ID <", value, "ROLE_ID");
            return (Criteria) this;
        }

        public Criteria andROLE_IDLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ROLE_ID <=", value, "ROLE_ID");
            return (Criteria) this;
        }

        public Criteria andROLE_IDIn(List<BigDecimal> values) {
            addCriterion("ROLE_ID in", values, "ROLE_ID");
            return (Criteria) this;
        }

        public Criteria andROLE_IDNotIn(List<BigDecimal> values) {
            addCriterion("ROLE_ID not in", values, "ROLE_ID");
            return (Criteria) this;
        }

        public Criteria andROLE_IDBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ROLE_ID between", value1, value2, "ROLE_ID");
            return (Criteria) this;
        }

        public Criteria andROLE_IDNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ROLE_ID not between", value1, value2, "ROLE_ID");
            return (Criteria) this;
        }
        
        //
        public Criteria andMENU_IDIsNull() {
            addCriterion("MENU_ID is null");
            return (Criteria) this;
        }

        public Criteria andMENU_IDIsNotNull() {
            addCriterion("MENU_ID is not null");
            return (Criteria) this;
        }

        public Criteria andMENU_IDEqualTo(BigDecimal value) {
            addCriterion("MENU_ID =", value, "MENU_ID");
            return (Criteria) this;
        }

        public Criteria andMENU_IDNotEqualTo(BigDecimal value) {
            addCriterion("MENU_ID <>", value, "MENU_ID");
            return (Criteria) this;
        }

        public Criteria andMENU_IDGreaterThan(BigDecimal value) {
            addCriterion("MENU_ID >", value, "MENU_ID");
            return (Criteria) this;
        }

        public Criteria andMENU_IDGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("MENU_ID >=", value, "MENU_ID");
            return (Criteria) this;
        }

        public Criteria andMENU_IDLessThan(BigDecimal value) {
            addCriterion("MENU_ID <", value, "MENU_ID");
            return (Criteria) this;
        }

        public Criteria andMENU_IDLessThanOrEqualTo(BigDecimal value) {
            addCriterion("MENU_ID <=", value, "MENU_ID");
            return (Criteria) this;
        }

        public Criteria andMENU_IDIn(List<BigDecimal> values) {
            addCriterion("MENU_ID in", values, "MENU_ID");
            return (Criteria) this;
        }

        public Criteria andMENU_IDNotIn(List<BigDecimal> values) {
            addCriterion("MENU_ID not in", values, "MENU_ID");
            return (Criteria) this;
        }

        public Criteria andMENU_IDBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("MENU_ID between", value1, value2, "MENU_ID");
            return (Criteria) this;
        }

        public Criteria andMENU_IDNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("MENU_ID not between", value1, value2, "MENU_ID");
            return (Criteria) this;
        }

        public Criteria andMENU_FUN_IDIsNull() {
            addCriterion("MENU_FUN_ID is null");
            return (Criteria) this;
        }

        public Criteria andMENU_FUN_IDIsNotNull() {
            addCriterion("MENU_FUN_ID is not null");
            return (Criteria) this;
        }

        public Criteria andMENU_FUN_IDEqualTo(BigDecimal value) {
            addCriterion("MENU_FUN_ID =", value, "MENU_FUN_ID");
            return (Criteria) this;
        }

        public Criteria andMENU_FUN_IDNotEqualTo(BigDecimal value) {
            addCriterion("MENU_FUN_ID <>", value, "MENU_FUN_ID");
            return (Criteria) this;
        }

        public Criteria andMENU_FUN_IDGreaterThan(BigDecimal value) {
            addCriterion("MENU_FUN_ID >", value, "MENU_FUN_ID");
            return (Criteria) this;
        }

        public Criteria andMENU_FUN_IDGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("MENU_FUN_ID >=", value, "MENU_FUN_ID");
            return (Criteria) this;
        }

        public Criteria andMENU_FUN_IDLessThan(BigDecimal value) {
            addCriterion("MENU_FUN_ID <", value, "MENU_FUN_ID");
            return (Criteria) this;
        }

        public Criteria andMENU_FUN_IDLessThanOrEqualTo(BigDecimal value) {
            addCriterion("MENU_FUN_ID <=", value, "MENU_FUN_ID");
            return (Criteria) this;
        }

        public Criteria andMENU_FUN_IDIn(List<BigDecimal> values) {
            addCriterion("MENU_FUN_ID in", values, "MENU_FUN_ID");
            return (Criteria) this;
        }

        public Criteria andMENU_FUN_IDNotIn(List<BigDecimal> values) {
            addCriterion("MENU_FUN_ID not in", values, "MENU_FUN_ID");
            return (Criteria) this;
        }

        public Criteria andMENU_FUN_IDBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("MENU_FUN_ID between", value1, value2, "MENU_FUN_ID");
            return (Criteria) this;
        }

        public Criteria andMENU_FUN_IDNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("MENU_FUN_ID not between", value1, value2, "MENU_FUN_ID");
            return (Criteria) this;
        }

        public Criteria andDEP_IDIsNull() {
            addCriterion("DEP_ID is null");
            return (Criteria) this;
        }

        public Criteria andDEP_IDIsNotNull() {
            addCriterion("DEP_ID is not null");
            return (Criteria) this;
        }

        public Criteria andDEP_IDEqualTo(BigDecimal value) {
            addCriterion("DEP_ID =", value, "DEP_ID");
            return (Criteria) this;
        }

        public Criteria andDEP_IDNotEqualTo(BigDecimal value) {
            addCriterion("DEP_ID <>", value, "DEP_ID");
            return (Criteria) this;
        }

        public Criteria andDEP_IDGreaterThan(BigDecimal value) {
            addCriterion("DEP_ID >", value, "DEP_ID");
            return (Criteria) this;
        }

        public Criteria andDEP_IDGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("DEP_ID >=", value, "DEP_ID");
            return (Criteria) this;
        }

        public Criteria andDEP_IDLessThan(BigDecimal value) {
            addCriterion("DEP_ID <", value, "DEP_ID");
            return (Criteria) this;
        }

        public Criteria andDEP_IDLessThanOrEqualTo(BigDecimal value) {
            addCriterion("DEP_ID <=", value, "DEP_ID");
            return (Criteria) this;
        }

        public Criteria andDEP_IDIn(List<BigDecimal> values) {
            addCriterion("DEP_ID in", values, "DEP_ID");
            return (Criteria) this;
        }

        public Criteria andDEP_IDNotIn(List<BigDecimal> values) {
            addCriterion("DEP_ID not in", values, "DEP_ID");
            return (Criteria) this;
        }

        public Criteria andDEP_IDBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DEP_ID between", value1, value2, "DEP_ID");
            return (Criteria) this;
        }

        public Criteria andDEP_IDNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DEP_ID not between", value1, value2, "DEP_ID");
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
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table TB_AUTH_ROLE_MENU_FUN
     *
     * @mbggenerated do_not_delete_during_merge Wed Aug 13 16:52:10 CST 2014
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table TB_AUTH_ROLE_MENU_FUN
     *
     * @mbggenerated Wed Aug 13 16:52:10 CST 2014
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