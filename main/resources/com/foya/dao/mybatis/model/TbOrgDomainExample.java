package com.foya.dao.mybatis.model;

import java.util.ArrayList;
import java.util.List;

public class TbOrgDomainExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table TB_ORG_DOMAIN
     *
     * @mbggenerated Wed Oct 01 15:46:28 CST 2014
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table TB_ORG_DOMAIN
     *
     * @mbggenerated Wed Oct 01 15:46:28 CST 2014
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table TB_ORG_DOMAIN
     *
     * @mbggenerated Wed Oct 01 15:46:28 CST 2014
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_ORG_DOMAIN
     *
     * @mbggenerated Wed Oct 01 15:46:28 CST 2014
     */
    public TbOrgDomainExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_ORG_DOMAIN
     *
     * @mbggenerated Wed Oct 01 15:46:28 CST 2014
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_ORG_DOMAIN
     *
     * @mbggenerated Wed Oct 01 15:46:28 CST 2014
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_ORG_DOMAIN
     *
     * @mbggenerated Wed Oct 01 15:46:28 CST 2014
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_ORG_DOMAIN
     *
     * @mbggenerated Wed Oct 01 15:46:28 CST 2014
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_ORG_DOMAIN
     *
     * @mbggenerated Wed Oct 01 15:46:28 CST 2014
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_ORG_DOMAIN
     *
     * @mbggenerated Wed Oct 01 15:46:28 CST 2014
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_ORG_DOMAIN
     *
     * @mbggenerated Wed Oct 01 15:46:28 CST 2014
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_ORG_DOMAIN
     *
     * @mbggenerated Wed Oct 01 15:46:28 CST 2014
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
     * This method corresponds to the database table TB_ORG_DOMAIN
     *
     * @mbggenerated Wed Oct 01 15:46:28 CST 2014
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_ORG_DOMAIN
     *
     * @mbggenerated Wed Oct 01 15:46:28 CST 2014
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table TB_ORG_DOMAIN
     *
     * @mbggenerated Wed Oct 01 15:46:28 CST 2014
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

        public Criteria andIDIsNull() {
            addCriterion("ID is null");
            return (Criteria) this;
        }

        public Criteria andIDIsNotNull() {
            addCriterion("ID is not null");
            return (Criteria) this;
        }

        public Criteria andIDEqualTo(String value) {
            addCriterion("ID =", value, "ID");
            return (Criteria) this;
        }

        public Criteria andIDNotEqualTo(String value) {
            addCriterion("ID <>", value, "ID");
            return (Criteria) this;
        }

        public Criteria andIDGreaterThan(String value) {
            addCriterion("ID >", value, "ID");
            return (Criteria) this;
        }

        public Criteria andIDGreaterThanOrEqualTo(String value) {
            addCriterion("ID >=", value, "ID");
            return (Criteria) this;
        }

        public Criteria andIDLessThan(String value) {
            addCriterion("ID <", value, "ID");
            return (Criteria) this;
        }

        public Criteria andIDLessThanOrEqualTo(String value) {
            addCriterion("ID <=", value, "ID");
            return (Criteria) this;
        }

        public Criteria andIDLike(String value) {
            addCriterion("ID like", value, "ID");
            return (Criteria) this;
        }

        public Criteria andIDNotLike(String value) {
            addCriterion("ID not like", value, "ID");
            return (Criteria) this;
        }

        public Criteria andIDIn(List<String> values) {
            addCriterion("ID in", values, "ID");
            return (Criteria) this;
        }

        public Criteria andIDNotIn(List<String> values) {
            addCriterion("ID not in", values, "ID");
            return (Criteria) this;
        }

        public Criteria andIDBetween(String value1, String value2) {
            addCriterion("ID between", value1, value2, "ID");
            return (Criteria) this;
        }

        public Criteria andIDNotBetween(String value1, String value2) {
            addCriterion("ID not between", value1, value2, "ID");
            return (Criteria) this;
        }

        public Criteria andNAMEIsNull() {
            addCriterion("NAME is null");
            return (Criteria) this;
        }

        public Criteria andNAMEIsNotNull() {
            addCriterion("NAME is not null");
            return (Criteria) this;
        }

        public Criteria andNAMEEqualTo(String value) {
            addCriterion("NAME =", value, "NAME");
            return (Criteria) this;
        }

        public Criteria andNAMENotEqualTo(String value) {
            addCriterion("NAME <>", value, "NAME");
            return (Criteria) this;
        }

        public Criteria andNAMEGreaterThan(String value) {
            addCriterion("NAME >", value, "NAME");
            return (Criteria) this;
        }

        public Criteria andNAMEGreaterThanOrEqualTo(String value) {
            addCriterion("NAME >=", value, "NAME");
            return (Criteria) this;
        }

        public Criteria andNAMELessThan(String value) {
            addCriterion("NAME <", value, "NAME");
            return (Criteria) this;
        }

        public Criteria andNAMELessThanOrEqualTo(String value) {
            addCriterion("NAME <=", value, "NAME");
            return (Criteria) this;
        }

        public Criteria andNAMELike(String value) {
            addCriterion("NAME like", value, "NAME");
            return (Criteria) this;
        }

        public Criteria andNAMENotLike(String value) {
            addCriterion("NAME not like", value, "NAME");
            return (Criteria) this;
        }

        public Criteria andNAMEIn(List<String> values) {
            addCriterion("NAME in", values, "NAME");
            return (Criteria) this;
        }

        public Criteria andNAMENotIn(List<String> values) {
            addCriterion("NAME not in", values, "NAME");
            return (Criteria) this;
        }

        public Criteria andNAMEBetween(String value1, String value2) {
            addCriterion("NAME between", value1, value2, "NAME");
            return (Criteria) this;
        }

        public Criteria andNAMENotBetween(String value1, String value2) {
            addCriterion("NAME not between", value1, value2, "NAME");
            return (Criteria) this;
        }

        public Criteria andPARENTIsNull() {
            addCriterion("PARENT is null");
            return (Criteria) this;
        }

        public Criteria andPARENTIsNotNull() {
            addCriterion("PARENT is not null");
            return (Criteria) this;
        }

        public Criteria andPARENTEqualTo(String value) {
            addCriterion("PARENT =", value, "PARENT");
            return (Criteria) this;
        }

        public Criteria andPARENTNotEqualTo(String value) {
            addCriterion("PARENT <>", value, "PARENT");
            return (Criteria) this;
        }

        public Criteria andPARENTGreaterThan(String value) {
            addCriterion("PARENT >", value, "PARENT");
            return (Criteria) this;
        }

        public Criteria andPARENTGreaterThanOrEqualTo(String value) {
            addCriterion("PARENT >=", value, "PARENT");
            return (Criteria) this;
        }

        public Criteria andPARENTLessThan(String value) {
            addCriterion("PARENT <", value, "PARENT");
            return (Criteria) this;
        }

        public Criteria andPARENTLessThanOrEqualTo(String value) {
            addCriterion("PARENT <=", value, "PARENT");
            return (Criteria) this;
        }

        public Criteria andPARENTLike(String value) {
            addCriterion("PARENT like", value, "PARENT");
            return (Criteria) this;
        }

        public Criteria andPARENTNotLike(String value) {
            addCriterion("PARENT not like", value, "PARENT");
            return (Criteria) this;
        }

        public Criteria andPARENTIn(List<String> values) {
            addCriterion("PARENT in", values, "PARENT");
            return (Criteria) this;
        }

        public Criteria andPARENTNotIn(List<String> values) {
            addCriterion("PARENT not in", values, "PARENT");
            return (Criteria) this;
        }

        public Criteria andPARENTBetween(String value1, String value2) {
            addCriterion("PARENT between", value1, value2, "PARENT");
            return (Criteria) this;
        }

        public Criteria andPARENTNotBetween(String value1, String value2) {
            addCriterion("PARENT not between", value1, value2, "PARENT");
            return (Criteria) this;
        }

        public Criteria andMANAGERIsNull() {
            addCriterion("MANAGER is null");
            return (Criteria) this;
        }

        public Criteria andMANAGERIsNotNull() {
            addCriterion("MANAGER is not null");
            return (Criteria) this;
        }

        public Criteria andMANAGEREqualTo(String value) {
            addCriterion("MANAGER =", value, "MANAGER");
            return (Criteria) this;
        }

        public Criteria andMANAGERNotEqualTo(String value) {
            addCriterion("MANAGER <>", value, "MANAGER");
            return (Criteria) this;
        }

        public Criteria andMANAGERGreaterThan(String value) {
            addCriterion("MANAGER >", value, "MANAGER");
            return (Criteria) this;
        }

        public Criteria andMANAGERGreaterThanOrEqualTo(String value) {
            addCriterion("MANAGER >=", value, "MANAGER");
            return (Criteria) this;
        }

        public Criteria andMANAGERLessThan(String value) {
            addCriterion("MANAGER <", value, "MANAGER");
            return (Criteria) this;
        }

        public Criteria andMANAGERLessThanOrEqualTo(String value) {
            addCriterion("MANAGER <=", value, "MANAGER");
            return (Criteria) this;
        }

        public Criteria andMANAGERLike(String value) {
            addCriterion("MANAGER like", value, "MANAGER");
            return (Criteria) this;
        }

        public Criteria andMANAGERNotLike(String value) {
            addCriterion("MANAGER not like", value, "MANAGER");
            return (Criteria) this;
        }

        public Criteria andMANAGERIn(List<String> values) {
            addCriterion("MANAGER in", values, "MANAGER");
            return (Criteria) this;
        }

        public Criteria andMANAGERNotIn(List<String> values) {
            addCriterion("MANAGER not in", values, "MANAGER");
            return (Criteria) this;
        }

        public Criteria andMANAGERBetween(String value1, String value2) {
            addCriterion("MANAGER between", value1, value2, "MANAGER");
            return (Criteria) this;
        }

        public Criteria andMANAGERNotBetween(String value1, String value2) {
            addCriterion("MANAGER not between", value1, value2, "MANAGER");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table TB_ORG_DOMAIN
     *
     * @mbggenerated do_not_delete_during_merge Wed Oct 01 15:46:28 CST 2014
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table TB_ORG_DOMAIN
     *
     * @mbggenerated Wed Oct 01 15:46:28 CST 2014
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