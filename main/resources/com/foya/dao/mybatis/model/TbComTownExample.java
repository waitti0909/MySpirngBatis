package com.foya.dao.mybatis.model;

import java.util.ArrayList;
import java.util.List;

public class TbComTownExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table TB_COM_TOWN
     *
     * @mbggenerated Thu Oct 16 11:57:45 CST 2014
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table TB_COM_TOWN
     *
     * @mbggenerated Thu Oct 16 11:57:45 CST 2014
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table TB_COM_TOWN
     *
     * @mbggenerated Thu Oct 16 11:57:45 CST 2014
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_COM_TOWN
     *
     * @mbggenerated Thu Oct 16 11:57:45 CST 2014
     */
    public TbComTownExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_COM_TOWN
     *
     * @mbggenerated Thu Oct 16 11:57:45 CST 2014
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_COM_TOWN
     *
     * @mbggenerated Thu Oct 16 11:57:45 CST 2014
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_COM_TOWN
     *
     * @mbggenerated Thu Oct 16 11:57:45 CST 2014
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_COM_TOWN
     *
     * @mbggenerated Thu Oct 16 11:57:45 CST 2014
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_COM_TOWN
     *
     * @mbggenerated Thu Oct 16 11:57:45 CST 2014
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_COM_TOWN
     *
     * @mbggenerated Thu Oct 16 11:57:45 CST 2014
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_COM_TOWN
     *
     * @mbggenerated Thu Oct 16 11:57:45 CST 2014
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_COM_TOWN
     *
     * @mbggenerated Thu Oct 16 11:57:45 CST 2014
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
     * This method corresponds to the database table TB_COM_TOWN
     *
     * @mbggenerated Thu Oct 16 11:57:45 CST 2014
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_COM_TOWN
     *
     * @mbggenerated Thu Oct 16 11:57:45 CST 2014
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table TB_COM_TOWN
     *
     * @mbggenerated Thu Oct 16 11:57:45 CST 2014
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

        public Criteria andTOWN_IDIsNull() {
            addCriterion("TOWN_ID is null");
            return (Criteria) this;
        }

        public Criteria andTOWN_IDIsNotNull() {
            addCriterion("TOWN_ID is not null");
            return (Criteria) this;
        }

        public Criteria andTOWN_IDEqualTo(String value) {
            addCriterion("TOWN_ID =", value, "TOWN_ID");
            return (Criteria) this;
        }

        public Criteria andTOWN_IDNotEqualTo(String value) {
            addCriterion("TOWN_ID <>", value, "TOWN_ID");
            return (Criteria) this;
        }

        public Criteria andTOWN_IDGreaterThan(String value) {
            addCriterion("TOWN_ID >", value, "TOWN_ID");
            return (Criteria) this;
        }

        public Criteria andTOWN_IDGreaterThanOrEqualTo(String value) {
            addCriterion("TOWN_ID >=", value, "TOWN_ID");
            return (Criteria) this;
        }

        public Criteria andTOWN_IDLessThan(String value) {
            addCriterion("TOWN_ID <", value, "TOWN_ID");
            return (Criteria) this;
        }

        public Criteria andTOWN_IDLessThanOrEqualTo(String value) {
            addCriterion("TOWN_ID <=", value, "TOWN_ID");
            return (Criteria) this;
        }

        public Criteria andTOWN_IDLike(String value) {
            addCriterion("TOWN_ID like", value, "TOWN_ID");
            return (Criteria) this;
        }

        public Criteria andTOWN_IDNotLike(String value) {
            addCriterion("TOWN_ID not like", value, "TOWN_ID");
            return (Criteria) this;
        }

        public Criteria andTOWN_IDIn(List<String> values) {
            addCriterion("TOWN_ID in", values, "TOWN_ID");
            return (Criteria) this;
        }

        public Criteria andTOWN_IDNotIn(List<String> values) {
            addCriterion("TOWN_ID not in", values, "TOWN_ID");
            return (Criteria) this;
        }

        public Criteria andTOWN_IDBetween(String value1, String value2) {
            addCriterion("TOWN_ID between", value1, value2, "TOWN_ID");
            return (Criteria) this;
        }

        public Criteria andTOWN_IDNotBetween(String value1, String value2) {
            addCriterion("TOWN_ID not between", value1, value2, "TOWN_ID");
            return (Criteria) this;
        }

        public Criteria andCITY_IDIsNull() {
            addCriterion("CITY_ID is null");
            return (Criteria) this;
        }

        public Criteria andCITY_IDIsNotNull() {
            addCriterion("CITY_ID is not null");
            return (Criteria) this;
        }

        public Criteria andCITY_IDEqualTo(String value) {
            addCriterion("CITY_ID =", value, "CITY_ID");
            return (Criteria) this;
        }

        public Criteria andCITY_IDNotEqualTo(String value) {
            addCriterion("CITY_ID <>", value, "CITY_ID");
            return (Criteria) this;
        }

        public Criteria andCITY_IDGreaterThan(String value) {
            addCriterion("CITY_ID >", value, "CITY_ID");
            return (Criteria) this;
        }

        public Criteria andCITY_IDGreaterThanOrEqualTo(String value) {
            addCriterion("CITY_ID >=", value, "CITY_ID");
            return (Criteria) this;
        }

        public Criteria andCITY_IDLessThan(String value) {
            addCriterion("CITY_ID <", value, "CITY_ID");
            return (Criteria) this;
        }

        public Criteria andCITY_IDLessThanOrEqualTo(String value) {
            addCriterion("CITY_ID <=", value, "CITY_ID");
            return (Criteria) this;
        }

        public Criteria andCITY_IDLike(String value) {
            addCriterion("CITY_ID like", value, "CITY_ID");
            return (Criteria) this;
        }

        public Criteria andCITY_IDNotLike(String value) {
            addCriterion("CITY_ID not like", value, "CITY_ID");
            return (Criteria) this;
        }

        public Criteria andCITY_IDIn(List<String> values) {
            addCriterion("CITY_ID in", values, "CITY_ID");
            return (Criteria) this;
        }

        public Criteria andCITY_IDNotIn(List<String> values) {
            addCriterion("CITY_ID not in", values, "CITY_ID");
            return (Criteria) this;
        }

        public Criteria andCITY_IDBetween(String value1, String value2) {
            addCriterion("CITY_ID between", value1, value2, "CITY_ID");
            return (Criteria) this;
        }

        public Criteria andCITY_IDNotBetween(String value1, String value2) {
            addCriterion("CITY_ID not between", value1, value2, "CITY_ID");
            return (Criteria) this;
        }

        public Criteria andTOWN_NAMEIsNull() {
            addCriterion("TOWN_NAME is null");
            return (Criteria) this;
        }

        public Criteria andTOWN_NAMEIsNotNull() {
            addCriterion("TOWN_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andTOWN_NAMEEqualTo(String value) {
            addCriterion("TOWN_NAME =", value, "TOWN_NAME");
            return (Criteria) this;
        }

        public Criteria andTOWN_NAMENotEqualTo(String value) {
            addCriterion("TOWN_NAME <>", value, "TOWN_NAME");
            return (Criteria) this;
        }

        public Criteria andTOWN_NAMEGreaterThan(String value) {
            addCriterion("TOWN_NAME >", value, "TOWN_NAME");
            return (Criteria) this;
        }

        public Criteria andTOWN_NAMEGreaterThanOrEqualTo(String value) {
            addCriterion("TOWN_NAME >=", value, "TOWN_NAME");
            return (Criteria) this;
        }

        public Criteria andTOWN_NAMELessThan(String value) {
            addCriterion("TOWN_NAME <", value, "TOWN_NAME");
            return (Criteria) this;
        }

        public Criteria andTOWN_NAMELessThanOrEqualTo(String value) {
            addCriterion("TOWN_NAME <=", value, "TOWN_NAME");
            return (Criteria) this;
        }

        public Criteria andTOWN_NAMELike(String value) {
            addCriterion("TOWN_NAME like", value, "TOWN_NAME");
            return (Criteria) this;
        }

        public Criteria andTOWN_NAMENotLike(String value) {
            addCriterion("TOWN_NAME not like", value, "TOWN_NAME");
            return (Criteria) this;
        }

        public Criteria andTOWN_NAMEIn(List<String> values) {
            addCriterion("TOWN_NAME in", values, "TOWN_NAME");
            return (Criteria) this;
        }

        public Criteria andTOWN_NAMENotIn(List<String> values) {
            addCriterion("TOWN_NAME not in", values, "TOWN_NAME");
            return (Criteria) this;
        }

        public Criteria andTOWN_NAMEBetween(String value1, String value2) {
            addCriterion("TOWN_NAME between", value1, value2, "TOWN_NAME");
            return (Criteria) this;
        }

        public Criteria andTOWN_NAMENotBetween(String value1, String value2) {
            addCriterion("TOWN_NAME not between", value1, value2, "TOWN_NAME");
            return (Criteria) this;
        }

        public Criteria andZIP_CODEIsNull() {
            addCriterion("ZIP_CODE is null");
            return (Criteria) this;
        }

        public Criteria andZIP_CODEIsNotNull() {
            addCriterion("ZIP_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andZIP_CODEEqualTo(String value) {
            addCriterion("ZIP_CODE =", value, "ZIP_CODE");
            return (Criteria) this;
        }

        public Criteria andZIP_CODENotEqualTo(String value) {
            addCriterion("ZIP_CODE <>", value, "ZIP_CODE");
            return (Criteria) this;
        }

        public Criteria andZIP_CODEGreaterThan(String value) {
            addCriterion("ZIP_CODE >", value, "ZIP_CODE");
            return (Criteria) this;
        }

        public Criteria andZIP_CODEGreaterThanOrEqualTo(String value) {
            addCriterion("ZIP_CODE >=", value, "ZIP_CODE");
            return (Criteria) this;
        }

        public Criteria andZIP_CODELessThan(String value) {
            addCriterion("ZIP_CODE <", value, "ZIP_CODE");
            return (Criteria) this;
        }

        public Criteria andZIP_CODELessThanOrEqualTo(String value) {
            addCriterion("ZIP_CODE <=", value, "ZIP_CODE");
            return (Criteria) this;
        }

        public Criteria andZIP_CODELike(String value) {
            addCriterion("ZIP_CODE like", value, "ZIP_CODE");
            return (Criteria) this;
        }

        public Criteria andZIP_CODENotLike(String value) {
            addCriterion("ZIP_CODE not like", value, "ZIP_CODE");
            return (Criteria) this;
        }

        public Criteria andZIP_CODEIn(List<String> values) {
            addCriterion("ZIP_CODE in", values, "ZIP_CODE");
            return (Criteria) this;
        }

        public Criteria andZIP_CODENotIn(List<String> values) {
            addCriterion("ZIP_CODE not in", values, "ZIP_CODE");
            return (Criteria) this;
        }

        public Criteria andZIP_CODEBetween(String value1, String value2) {
            addCriterion("ZIP_CODE between", value1, value2, "ZIP_CODE");
            return (Criteria) this;
        }

        public Criteria andZIP_CODENotBetween(String value1, String value2) {
            addCriterion("ZIP_CODE not between", value1, value2, "ZIP_CODE");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table TB_COM_TOWN
     *
     * @mbggenerated do_not_delete_during_merge Thu Oct 16 11:57:45 CST 2014
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table TB_COM_TOWN
     *
     * @mbggenerated Thu Oct 16 11:57:45 CST 2014
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