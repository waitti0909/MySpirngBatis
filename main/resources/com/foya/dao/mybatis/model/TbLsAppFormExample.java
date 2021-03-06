package com.foya.dao.mybatis.model;

import java.util.ArrayList;
import java.util.List;

public class TbLsAppFormExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table TB_LS_APP_FORM
     *
     * @mbggenerated Thu Feb 05 11:01:54 CST 2015
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table TB_LS_APP_FORM
     *
     * @mbggenerated Thu Feb 05 11:01:54 CST 2015
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table TB_LS_APP_FORM
     *
     * @mbggenerated Thu Feb 05 11:01:54 CST 2015
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_LS_APP_FORM
     *
     * @mbggenerated Thu Feb 05 11:01:54 CST 2015
     */
    public TbLsAppFormExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_LS_APP_FORM
     *
     * @mbggenerated Thu Feb 05 11:01:54 CST 2015
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_LS_APP_FORM
     *
     * @mbggenerated Thu Feb 05 11:01:54 CST 2015
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_LS_APP_FORM
     *
     * @mbggenerated Thu Feb 05 11:01:54 CST 2015
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_LS_APP_FORM
     *
     * @mbggenerated Thu Feb 05 11:01:54 CST 2015
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_LS_APP_FORM
     *
     * @mbggenerated Thu Feb 05 11:01:54 CST 2015
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_LS_APP_FORM
     *
     * @mbggenerated Thu Feb 05 11:01:54 CST 2015
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_LS_APP_FORM
     *
     * @mbggenerated Thu Feb 05 11:01:54 CST 2015
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_LS_APP_FORM
     *
     * @mbggenerated Thu Feb 05 11:01:54 CST 2015
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
     * This method corresponds to the database table TB_LS_APP_FORM
     *
     * @mbggenerated Thu Feb 05 11:01:54 CST 2015
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_LS_APP_FORM
     *
     * @mbggenerated Thu Feb 05 11:01:54 CST 2015
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table TB_LS_APP_FORM
     *
     * @mbggenerated Thu Feb 05 11:01:54 CST 2015
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

        public Criteria andAPP_TYPEIsNull() {
            addCriterion("APP_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andAPP_TYPEIsNotNull() {
            addCriterion("APP_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andAPP_TYPEEqualTo(String value) {
            addCriterion("APP_TYPE =", value, "APP_TYPE");
            return (Criteria) this;
        }

        public Criteria andAPP_TYPENotEqualTo(String value) {
            addCriterion("APP_TYPE <>", value, "APP_TYPE");
            return (Criteria) this;
        }

        public Criteria andAPP_TYPEGreaterThan(String value) {
            addCriterion("APP_TYPE >", value, "APP_TYPE");
            return (Criteria) this;
        }

        public Criteria andAPP_TYPEGreaterThanOrEqualTo(String value) {
            addCriterion("APP_TYPE >=", value, "APP_TYPE");
            return (Criteria) this;
        }

        public Criteria andAPP_TYPELessThan(String value) {
            addCriterion("APP_TYPE <", value, "APP_TYPE");
            return (Criteria) this;
        }

        public Criteria andAPP_TYPELessThanOrEqualTo(String value) {
            addCriterion("APP_TYPE <=", value, "APP_TYPE");
            return (Criteria) this;
        }

        public Criteria andAPP_TYPELike(String value) {
            addCriterion("APP_TYPE like", value, "APP_TYPE");
            return (Criteria) this;
        }

        public Criteria andAPP_TYPENotLike(String value) {
            addCriterion("APP_TYPE not like", value, "APP_TYPE");
            return (Criteria) this;
        }

        public Criteria andAPP_TYPEIn(List<String> values) {
            addCriterion("APP_TYPE in", values, "APP_TYPE");
            return (Criteria) this;
        }

        public Criteria andAPP_TYPENotIn(List<String> values) {
            addCriterion("APP_TYPE not in", values, "APP_TYPE");
            return (Criteria) this;
        }

        public Criteria andAPP_TYPEBetween(String value1, String value2) {
            addCriterion("APP_TYPE between", value1, value2, "APP_TYPE");
            return (Criteria) this;
        }

        public Criteria andAPP_TYPENotBetween(String value1, String value2) {
            addCriterion("APP_TYPE not between", value1, value2, "APP_TYPE");
            return (Criteria) this;
        }

        public Criteria andAPP_NAMEIsNull() {
            addCriterion("APP_NAME is null");
            return (Criteria) this;
        }

        public Criteria andAPP_NAMEIsNotNull() {
            addCriterion("APP_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andAPP_NAMEEqualTo(String value) {
            addCriterion("APP_NAME =", value, "APP_NAME");
            return (Criteria) this;
        }

        public Criteria andAPP_NAMENotEqualTo(String value) {
            addCriterion("APP_NAME <>", value, "APP_NAME");
            return (Criteria) this;
        }

        public Criteria andAPP_NAMEGreaterThan(String value) {
            addCriterion("APP_NAME >", value, "APP_NAME");
            return (Criteria) this;
        }

        public Criteria andAPP_NAMEGreaterThanOrEqualTo(String value) {
            addCriterion("APP_NAME >=", value, "APP_NAME");
            return (Criteria) this;
        }

        public Criteria andAPP_NAMELessThan(String value) {
            addCriterion("APP_NAME <", value, "APP_NAME");
            return (Criteria) this;
        }

        public Criteria andAPP_NAMELessThanOrEqualTo(String value) {
            addCriterion("APP_NAME <=", value, "APP_NAME");
            return (Criteria) this;
        }

        public Criteria andAPP_NAMELike(String value) {
            addCriterion("APP_NAME like", value, "APP_NAME");
            return (Criteria) this;
        }

        public Criteria andAPP_NAMENotLike(String value) {
            addCriterion("APP_NAME not like", value, "APP_NAME");
            return (Criteria) this;
        }

        public Criteria andAPP_NAMEIn(List<String> values) {
            addCriterion("APP_NAME in", values, "APP_NAME");
            return (Criteria) this;
        }

        public Criteria andAPP_NAMENotIn(List<String> values) {
            addCriterion("APP_NAME not in", values, "APP_NAME");
            return (Criteria) this;
        }

        public Criteria andAPP_NAMEBetween(String value1, String value2) {
            addCriterion("APP_NAME between", value1, value2, "APP_NAME");
            return (Criteria) this;
        }

        public Criteria andAPP_NAMENotBetween(String value1, String value2) {
            addCriterion("APP_NAME not between", value1, value2, "APP_NAME");
            return (Criteria) this;
        }

        public Criteria andSUB_TYPEIsNull() {
            addCriterion("SUB_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andSUB_TYPEIsNotNull() {
            addCriterion("SUB_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andSUB_TYPEEqualTo(String value) {
            addCriterion("SUB_TYPE =", value, "SUB_TYPE");
            return (Criteria) this;
        }

        public Criteria andSUB_TYPENotEqualTo(String value) {
            addCriterion("SUB_TYPE <>", value, "SUB_TYPE");
            return (Criteria) this;
        }

        public Criteria andSUB_TYPEGreaterThan(String value) {
            addCriterion("SUB_TYPE >", value, "SUB_TYPE");
            return (Criteria) this;
        }

        public Criteria andSUB_TYPEGreaterThanOrEqualTo(String value) {
            addCriterion("SUB_TYPE >=", value, "SUB_TYPE");
            return (Criteria) this;
        }

        public Criteria andSUB_TYPELessThan(String value) {
            addCriterion("SUB_TYPE <", value, "SUB_TYPE");
            return (Criteria) this;
        }

        public Criteria andSUB_TYPELessThanOrEqualTo(String value) {
            addCriterion("SUB_TYPE <=", value, "SUB_TYPE");
            return (Criteria) this;
        }

        public Criteria andSUB_TYPELike(String value) {
            addCriterion("SUB_TYPE like", value, "SUB_TYPE");
            return (Criteria) this;
        }

        public Criteria andSUB_TYPENotLike(String value) {
            addCriterion("SUB_TYPE not like", value, "SUB_TYPE");
            return (Criteria) this;
        }

        public Criteria andSUB_TYPEIn(List<String> values) {
            addCriterion("SUB_TYPE in", values, "SUB_TYPE");
            return (Criteria) this;
        }

        public Criteria andSUB_TYPENotIn(List<String> values) {
            addCriterion("SUB_TYPE not in", values, "SUB_TYPE");
            return (Criteria) this;
        }

        public Criteria andSUB_TYPEBetween(String value1, String value2) {
            addCriterion("SUB_TYPE between", value1, value2, "SUB_TYPE");
            return (Criteria) this;
        }

        public Criteria andSUB_TYPENotBetween(String value1, String value2) {
            addCriterion("SUB_TYPE not between", value1, value2, "SUB_TYPE");
            return (Criteria) this;
        }

        public Criteria andSUB_NAMEIsNull() {
            addCriterion("SUB_NAME is null");
            return (Criteria) this;
        }

        public Criteria andSUB_NAMEIsNotNull() {
            addCriterion("SUB_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andSUB_NAMEEqualTo(String value) {
            addCriterion("SUB_NAME =", value, "SUB_NAME");
            return (Criteria) this;
        }

        public Criteria andSUB_NAMENotEqualTo(String value) {
            addCriterion("SUB_NAME <>", value, "SUB_NAME");
            return (Criteria) this;
        }

        public Criteria andSUB_NAMEGreaterThan(String value) {
            addCriterion("SUB_NAME >", value, "SUB_NAME");
            return (Criteria) this;
        }

        public Criteria andSUB_NAMEGreaterThanOrEqualTo(String value) {
            addCriterion("SUB_NAME >=", value, "SUB_NAME");
            return (Criteria) this;
        }

        public Criteria andSUB_NAMELessThan(String value) {
            addCriterion("SUB_NAME <", value, "SUB_NAME");
            return (Criteria) this;
        }

        public Criteria andSUB_NAMELessThanOrEqualTo(String value) {
            addCriterion("SUB_NAME <=", value, "SUB_NAME");
            return (Criteria) this;
        }

        public Criteria andSUB_NAMELike(String value) {
            addCriterion("SUB_NAME like", value, "SUB_NAME");
            return (Criteria) this;
        }

        public Criteria andSUB_NAMENotLike(String value) {
            addCriterion("SUB_NAME not like", value, "SUB_NAME");
            return (Criteria) this;
        }

        public Criteria andSUB_NAMEIn(List<String> values) {
            addCriterion("SUB_NAME in", values, "SUB_NAME");
            return (Criteria) this;
        }

        public Criteria andSUB_NAMENotIn(List<String> values) {
            addCriterion("SUB_NAME not in", values, "SUB_NAME");
            return (Criteria) this;
        }

        public Criteria andSUB_NAMEBetween(String value1, String value2) {
            addCriterion("SUB_NAME between", value1, value2, "SUB_NAME");
            return (Criteria) this;
        }

        public Criteria andSUB_NAMENotBetween(String value1, String value2) {
            addCriterion("SUB_NAME not between", value1, value2, "SUB_NAME");
            return (Criteria) this;
        }

        public Criteria andFORM_IDIsNull() {
            addCriterion("FORM_ID is null");
            return (Criteria) this;
        }

        public Criteria andFORM_IDIsNotNull() {
            addCriterion("FORM_ID is not null");
            return (Criteria) this;
        }

        public Criteria andFORM_IDEqualTo(String value) {
            addCriterion("FORM_ID =", value, "FORM_ID");
            return (Criteria) this;
        }

        public Criteria andFORM_IDNotEqualTo(String value) {
            addCriterion("FORM_ID <>", value, "FORM_ID");
            return (Criteria) this;
        }

        public Criteria andFORM_IDGreaterThan(String value) {
            addCriterion("FORM_ID >", value, "FORM_ID");
            return (Criteria) this;
        }

        public Criteria andFORM_IDGreaterThanOrEqualTo(String value) {
            addCriterion("FORM_ID >=", value, "FORM_ID");
            return (Criteria) this;
        }

        public Criteria andFORM_IDLessThan(String value) {
            addCriterion("FORM_ID <", value, "FORM_ID");
            return (Criteria) this;
        }

        public Criteria andFORM_IDLessThanOrEqualTo(String value) {
            addCriterion("FORM_ID <=", value, "FORM_ID");
            return (Criteria) this;
        }

        public Criteria andFORM_IDLike(String value) {
            addCriterion("FORM_ID like", value, "FORM_ID");
            return (Criteria) this;
        }

        public Criteria andFORM_IDNotLike(String value) {
            addCriterion("FORM_ID not like", value, "FORM_ID");
            return (Criteria) this;
        }

        public Criteria andFORM_IDIn(List<String> values) {
            addCriterion("FORM_ID in", values, "FORM_ID");
            return (Criteria) this;
        }

        public Criteria andFORM_IDNotIn(List<String> values) {
            addCriterion("FORM_ID not in", values, "FORM_ID");
            return (Criteria) this;
        }

        public Criteria andFORM_IDBetween(String value1, String value2) {
            addCriterion("FORM_ID between", value1, value2, "FORM_ID");
            return (Criteria) this;
        }

        public Criteria andFORM_IDNotBetween(String value1, String value2) {
            addCriterion("FORM_ID not between", value1, value2, "FORM_ID");
            return (Criteria) this;
        }

        public Criteria andFORM_NAMEIsNull() {
            addCriterion("FORM_NAME is null");
            return (Criteria) this;
        }

        public Criteria andFORM_NAMEIsNotNull() {
            addCriterion("FORM_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andFORM_NAMEEqualTo(String value) {
            addCriterion("FORM_NAME =", value, "FORM_NAME");
            return (Criteria) this;
        }

        public Criteria andFORM_NAMENotEqualTo(String value) {
            addCriterion("FORM_NAME <>", value, "FORM_NAME");
            return (Criteria) this;
        }

        public Criteria andFORM_NAMEGreaterThan(String value) {
            addCriterion("FORM_NAME >", value, "FORM_NAME");
            return (Criteria) this;
        }

        public Criteria andFORM_NAMEGreaterThanOrEqualTo(String value) {
            addCriterion("FORM_NAME >=", value, "FORM_NAME");
            return (Criteria) this;
        }

        public Criteria andFORM_NAMELessThan(String value) {
            addCriterion("FORM_NAME <", value, "FORM_NAME");
            return (Criteria) this;
        }

        public Criteria andFORM_NAMELessThanOrEqualTo(String value) {
            addCriterion("FORM_NAME <=", value, "FORM_NAME");
            return (Criteria) this;
        }

        public Criteria andFORM_NAMELike(String value) {
            addCriterion("FORM_NAME like", value, "FORM_NAME");
            return (Criteria) this;
        }

        public Criteria andFORM_NAMENotLike(String value) {
            addCriterion("FORM_NAME not like", value, "FORM_NAME");
            return (Criteria) this;
        }

        public Criteria andFORM_NAMEIn(List<String> values) {
            addCriterion("FORM_NAME in", values, "FORM_NAME");
            return (Criteria) this;
        }

        public Criteria andFORM_NAMENotIn(List<String> values) {
            addCriterion("FORM_NAME not in", values, "FORM_NAME");
            return (Criteria) this;
        }

        public Criteria andFORM_NAMEBetween(String value1, String value2) {
            addCriterion("FORM_NAME between", value1, value2, "FORM_NAME");
            return (Criteria) this;
        }

        public Criteria andFORM_NAMENotBetween(String value1, String value2) {
            addCriterion("FORM_NAME not between", value1, value2, "FORM_NAME");
            return (Criteria) this;
        }

        public Criteria andPATHIsNull() {
            addCriterion("PATH is null");
            return (Criteria) this;
        }

        public Criteria andPATHIsNotNull() {
            addCriterion("PATH is not null");
            return (Criteria) this;
        }

        public Criteria andPATHEqualTo(String value) {
            addCriterion("PATH =", value, "PATH");
            return (Criteria) this;
        }

        public Criteria andPATHNotEqualTo(String value) {
            addCriterion("PATH <>", value, "PATH");
            return (Criteria) this;
        }

        public Criteria andPATHGreaterThan(String value) {
            addCriterion("PATH >", value, "PATH");
            return (Criteria) this;
        }

        public Criteria andPATHGreaterThanOrEqualTo(String value) {
            addCriterion("PATH >=", value, "PATH");
            return (Criteria) this;
        }

        public Criteria andPATHLessThan(String value) {
            addCriterion("PATH <", value, "PATH");
            return (Criteria) this;
        }

        public Criteria andPATHLessThanOrEqualTo(String value) {
            addCriterion("PATH <=", value, "PATH");
            return (Criteria) this;
        }

        public Criteria andPATHLike(String value) {
            addCriterion("PATH like", value, "PATH");
            return (Criteria) this;
        }

        public Criteria andPATHNotLike(String value) {
            addCriterion("PATH not like", value, "PATH");
            return (Criteria) this;
        }

        public Criteria andPATHIn(List<String> values) {
            addCriterion("PATH in", values, "PATH");
            return (Criteria) this;
        }

        public Criteria andPATHNotIn(List<String> values) {
            addCriterion("PATH not in", values, "PATH");
            return (Criteria) this;
        }

        public Criteria andPATHBetween(String value1, String value2) {
            addCriterion("PATH between", value1, value2, "PATH");
            return (Criteria) this;
        }

        public Criteria andPATHNotBetween(String value1, String value2) {
            addCriterion("PATH not between", value1, value2, "PATH");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table TB_LS_APP_FORM
     *
     * @mbggenerated do_not_delete_during_merge Thu Feb 05 11:01:54 CST 2015
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table TB_LS_APP_FORM
     *
     * @mbggenerated Thu Feb 05 11:01:54 CST 2015
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