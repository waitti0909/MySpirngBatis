package com.foya.dao.mybatis.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TbWorkflowVersionExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table TB_WORKFLOW_VERSION
     *
     * @mbggenerated Wed Oct 01 09:47:16 CST 2014
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table TB_WORKFLOW_VERSION
     *
     * @mbggenerated Wed Oct 01 09:47:16 CST 2014
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table TB_WORKFLOW_VERSION
     *
     * @mbggenerated Wed Oct 01 09:47:16 CST 2014
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_WORKFLOW_VERSION
     *
     * @mbggenerated Wed Oct 01 09:47:16 CST 2014
     */
    public TbWorkflowVersionExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_WORKFLOW_VERSION
     *
     * @mbggenerated Wed Oct 01 09:47:16 CST 2014
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_WORKFLOW_VERSION
     *
     * @mbggenerated Wed Oct 01 09:47:16 CST 2014
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_WORKFLOW_VERSION
     *
     * @mbggenerated Wed Oct 01 09:47:16 CST 2014
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_WORKFLOW_VERSION
     *
     * @mbggenerated Wed Oct 01 09:47:16 CST 2014
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_WORKFLOW_VERSION
     *
     * @mbggenerated Wed Oct 01 09:47:16 CST 2014
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_WORKFLOW_VERSION
     *
     * @mbggenerated Wed Oct 01 09:47:16 CST 2014
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_WORKFLOW_VERSION
     *
     * @mbggenerated Wed Oct 01 09:47:16 CST 2014
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_WORKFLOW_VERSION
     *
     * @mbggenerated Wed Oct 01 09:47:16 CST 2014
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
     * This method corresponds to the database table TB_WORKFLOW_VERSION
     *
     * @mbggenerated Wed Oct 01 09:47:16 CST 2014
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_WORKFLOW_VERSION
     *
     * @mbggenerated Wed Oct 01 09:47:16 CST 2014
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table TB_WORKFLOW_VERSION
     *
     * @mbggenerated Wed Oct 01 09:47:16 CST 2014
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

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(BigDecimal value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(BigDecimal value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(BigDecimal value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(BigDecimal value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(BigDecimal value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<BigDecimal> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<BigDecimal> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andGROUP_IDIsNull() {
            addCriterion("GROUP_ID is null");
            return (Criteria) this;
        }

        public Criteria andGROUP_IDIsNotNull() {
            addCriterion("GROUP_ID is not null");
            return (Criteria) this;
        }

        public Criteria andGROUP_IDEqualTo(String value) {
            addCriterion("GROUP_ID =", value, "GROUP_ID");
            return (Criteria) this;
        }

        public Criteria andGROUP_IDNotEqualTo(String value) {
            addCriterion("GROUP_ID <>", value, "GROUP_ID");
            return (Criteria) this;
        }

        public Criteria andGROUP_IDGreaterThan(String value) {
            addCriterion("GROUP_ID >", value, "GROUP_ID");
            return (Criteria) this;
        }

        public Criteria andGROUP_IDGreaterThanOrEqualTo(String value) {
            addCriterion("GROUP_ID >=", value, "GROUP_ID");
            return (Criteria) this;
        }

        public Criteria andGROUP_IDLessThan(String value) {
            addCriterion("GROUP_ID <", value, "GROUP_ID");
            return (Criteria) this;
        }

        public Criteria andGROUP_IDLessThanOrEqualTo(String value) {
            addCriterion("GROUP_ID <=", value, "GROUP_ID");
            return (Criteria) this;
        }

        public Criteria andGROUP_IDLike(String value) {
            addCriterion("GROUP_ID like", value, "GROUP_ID");
            return (Criteria) this;
        }

        public Criteria andGROUP_IDNotLike(String value) {
            addCriterion("GROUP_ID not like", value, "GROUP_ID");
            return (Criteria) this;
        }

        public Criteria andGROUP_IDIn(List<String> values) {
            addCriterion("GROUP_ID in", values, "GROUP_ID");
            return (Criteria) this;
        }

        public Criteria andGROUP_IDNotIn(List<String> values) {
            addCriterion("GROUP_ID not in", values, "GROUP_ID");
            return (Criteria) this;
        }

        public Criteria andGROUP_IDBetween(String value1, String value2) {
            addCriterion("GROUP_ID between", value1, value2, "GROUP_ID");
            return (Criteria) this;
        }

        public Criteria andGROUP_IDNotBetween(String value1, String value2) {
            addCriterion("GROUP_ID not between", value1, value2, "GROUP_ID");
            return (Criteria) this;
        }

        public Criteria andARTIFACT_IDIsNull() {
            addCriterion("ARTIFACT_ID is null");
            return (Criteria) this;
        }

        public Criteria andARTIFACT_IDIsNotNull() {
            addCriterion("ARTIFACT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andARTIFACT_IDEqualTo(String value) {
            addCriterion("ARTIFACT_ID =", value, "ARTIFACT_ID");
            return (Criteria) this;
        }

        public Criteria andARTIFACT_IDNotEqualTo(String value) {
            addCriterion("ARTIFACT_ID <>", value, "ARTIFACT_ID");
            return (Criteria) this;
        }

        public Criteria andARTIFACT_IDGreaterThan(String value) {
            addCriterion("ARTIFACT_ID >", value, "ARTIFACT_ID");
            return (Criteria) this;
        }

        public Criteria andARTIFACT_IDGreaterThanOrEqualTo(String value) {
            addCriterion("ARTIFACT_ID >=", value, "ARTIFACT_ID");
            return (Criteria) this;
        }

        public Criteria andARTIFACT_IDLessThan(String value) {
            addCriterion("ARTIFACT_ID <", value, "ARTIFACT_ID");
            return (Criteria) this;
        }

        public Criteria andARTIFACT_IDLessThanOrEqualTo(String value) {
            addCriterion("ARTIFACT_ID <=", value, "ARTIFACT_ID");
            return (Criteria) this;
        }

        public Criteria andARTIFACT_IDLike(String value) {
            addCriterion("ARTIFACT_ID like", value, "ARTIFACT_ID");
            return (Criteria) this;
        }

        public Criteria andARTIFACT_IDNotLike(String value) {
            addCriterion("ARTIFACT_ID not like", value, "ARTIFACT_ID");
            return (Criteria) this;
        }

        public Criteria andARTIFACT_IDIn(List<String> values) {
            addCriterion("ARTIFACT_ID in", values, "ARTIFACT_ID");
            return (Criteria) this;
        }

        public Criteria andARTIFACT_IDNotIn(List<String> values) {
            addCriterion("ARTIFACT_ID not in", values, "ARTIFACT_ID");
            return (Criteria) this;
        }

        public Criteria andARTIFACT_IDBetween(String value1, String value2) {
            addCriterion("ARTIFACT_ID between", value1, value2, "ARTIFACT_ID");
            return (Criteria) this;
        }

        public Criteria andARTIFACT_IDNotBetween(String value1, String value2) {
            addCriterion("ARTIFACT_ID not between", value1, value2, "ARTIFACT_ID");
            return (Criteria) this;
        }

        public Criteria andVERSIONIsNull() {
            addCriterion("VERSION is null");
            return (Criteria) this;
        }

        public Criteria andVERSIONIsNotNull() {
            addCriterion("VERSION is not null");
            return (Criteria) this;
        }

        public Criteria andVERSIONEqualTo(String value) {
            addCriterion("VERSION =", value, "VERSION");
            return (Criteria) this;
        }

        public Criteria andVERSIONNotEqualTo(String value) {
            addCriterion("VERSION <>", value, "VERSION");
            return (Criteria) this;
        }

        public Criteria andVERSIONGreaterThan(String value) {
            addCriterion("VERSION >", value, "VERSION");
            return (Criteria) this;
        }

        public Criteria andVERSIONGreaterThanOrEqualTo(String value) {
            addCriterion("VERSION >=", value, "VERSION");
            return (Criteria) this;
        }

        public Criteria andVERSIONLessThan(String value) {
            addCriterion("VERSION <", value, "VERSION");
            return (Criteria) this;
        }

        public Criteria andVERSIONLessThanOrEqualTo(String value) {
            addCriterion("VERSION <=", value, "VERSION");
            return (Criteria) this;
        }

        public Criteria andVERSIONLike(String value) {
            addCriterion("VERSION like", value, "VERSION");
            return (Criteria) this;
        }

        public Criteria andVERSIONNotLike(String value) {
            addCriterion("VERSION not like", value, "VERSION");
            return (Criteria) this;
        }

        public Criteria andVERSIONIn(List<String> values) {
            addCriterion("VERSION in", values, "VERSION");
            return (Criteria) this;
        }

        public Criteria andVERSIONNotIn(List<String> values) {
            addCriterion("VERSION not in", values, "VERSION");
            return (Criteria) this;
        }

        public Criteria andVERSIONBetween(String value1, String value2) {
            addCriterion("VERSION between", value1, value2, "VERSION");
            return (Criteria) this;
        }

        public Criteria andVERSIONNotBetween(String value1, String value2) {
            addCriterion("VERSION not between", value1, value2, "VERSION");
            return (Criteria) this;
        }

        public Criteria andPROCESS_IDIsNull() {
            addCriterion("PROCESS_ID is null");
            return (Criteria) this;
        }

        public Criteria andPROCESS_IDIsNotNull() {
            addCriterion("PROCESS_ID is not null");
            return (Criteria) this;
        }

        public Criteria andPROCESS_IDEqualTo(String value) {
            addCriterion("PROCESS_ID =", value, "PROCESS_ID");
            return (Criteria) this;
        }

        public Criteria andPROCESS_IDNotEqualTo(String value) {
            addCriterion("PROCESS_ID <>", value, "PROCESS_ID");
            return (Criteria) this;
        }

        public Criteria andPROCESS_IDGreaterThan(String value) {
            addCriterion("PROCESS_ID >", value, "PROCESS_ID");
            return (Criteria) this;
        }

        public Criteria andPROCESS_IDGreaterThanOrEqualTo(String value) {
            addCriterion("PROCESS_ID >=", value, "PROCESS_ID");
            return (Criteria) this;
        }

        public Criteria andPROCESS_IDLessThan(String value) {
            addCriterion("PROCESS_ID <", value, "PROCESS_ID");
            return (Criteria) this;
        }

        public Criteria andPROCESS_IDLessThanOrEqualTo(String value) {
            addCriterion("PROCESS_ID <=", value, "PROCESS_ID");
            return (Criteria) this;
        }

        public Criteria andPROCESS_IDLike(String value) {
            addCriterion("PROCESS_ID like", value, "PROCESS_ID");
            return (Criteria) this;
        }

        public Criteria andPROCESS_IDNotLike(String value) {
            addCriterion("PROCESS_ID not like", value, "PROCESS_ID");
            return (Criteria) this;
        }

        public Criteria andPROCESS_IDIn(List<String> values) {
            addCriterion("PROCESS_ID in", values, "PROCESS_ID");
            return (Criteria) this;
        }

        public Criteria andPROCESS_IDNotIn(List<String> values) {
            addCriterion("PROCESS_ID not in", values, "PROCESS_ID");
            return (Criteria) this;
        }

        public Criteria andPROCESS_IDBetween(String value1, String value2) {
            addCriterion("PROCESS_ID between", value1, value2, "PROCESS_ID");
            return (Criteria) this;
        }

        public Criteria andPROCESS_IDNotBetween(String value1, String value2) {
            addCriterion("PROCESS_ID not between", value1, value2, "PROCESS_ID");
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
        public Criteria andSTART_TIMEIsNull() {
            addCriterion("START_TIME is null");
            return (Criteria) this;
        }

        public Criteria andSTART_TIMEIsNotNull() {
            addCriterion("START_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andSTART_TIMEEqualTo(Date value) {
            addCriterion("START_TIME =", value, "START_TIME");
            return (Criteria) this;
        }

        public Criteria andSTART_TIMENotEqualTo(Date value) {
            addCriterion("START_TIME <>", value, "START_TIME");
            return (Criteria) this;
        }

        public Criteria andSTART_TIMEGreaterThan(Date value) {
            addCriterion("START_TIME >", value, "START_TIME");
            return (Criteria) this;
        }

        public Criteria andSTART_TIMEGreaterThanOrEqualTo(Date value) {
            addCriterion("START_TIME >=", value, "START_TIME");
            return (Criteria) this;
        }

        public Criteria andSTART_TIMELessThan(Date value) {
            addCriterion("START_TIME <", value, "START_TIME");
            return (Criteria) this;
        }

        public Criteria andSTART_TIMELessThanOrEqualTo(Date value) {
            addCriterion("START_TIME <=", value, "START_TIME");
            return (Criteria) this;
        }

        public Criteria andSTART_TIMEIn(List<Date> values) {
            addCriterion("START_TIME in", values, "START_TIME");
            return (Criteria) this;
        }

        public Criteria andSTART_TIMENotIn(List<Date> values) {
            addCriterion("START_TIME not in", values, "START_TIME");
            return (Criteria) this;
        }

        public Criteria andSTART_TIMEBetween(Date value1, Date value2) {
            addCriterion("START_TIME between", value1, value2, "START_TIME");
            return (Criteria) this;
        }

        public Criteria andSTART_TIMENotBetween(Date value1, Date value2) {
            addCriterion("START_TIME not between", value1, value2, "START_TIME");
            return (Criteria) this;
        }

        public Criteria andEND_TIMEIsNull() {
            addCriterion("END_TIME is null");
            return (Criteria) this;
        }

        public Criteria andEND_TIMEIsNotNull() {
            addCriterion("END_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andEND_TIMEEqualTo(Date value) {
            addCriterion("END_TIME =", value, "END_TIME");
            return (Criteria) this;
        }

        public Criteria andEND_TIMENotEqualTo(Date value) {
            addCriterion("END_TIME <>", value, "END_TIME");
            return (Criteria) this;
        }

        public Criteria andEND_TIMEGreaterThan(Date value) {
            addCriterion("END_TIME >", value, "END_TIME");
            return (Criteria) this;
        }

        public Criteria andEND_TIMEGreaterThanOrEqualTo(Date value) {
            addCriterion("END_TIME >=", value, "END_TIME");
            return (Criteria) this;
        }

        public Criteria andEND_TIMELessThan(Date value) {
            addCriterion("END_TIME <", value, "END_TIME");
            return (Criteria) this;
        }

        public Criteria andEND_TIMELessThanOrEqualTo(Date value) {
            addCriterion("END_TIME <=", value, "END_TIME");
            return (Criteria) this;
        }

        public Criteria andEND_TIMEIn(List<Date> values) {
            addCriterion("END_TIME in", values, "END_TIME");
            return (Criteria) this;
        }

        public Criteria andEND_TIMENotIn(List<Date> values) {
            addCriterion("END_TIME not in", values, "END_TIME");
            return (Criteria) this;
        }

        public Criteria andEND_TIMEBetween(Date value1, Date value2) {
            addCriterion("END_TIME between", value1, value2, "END_TIME");
            return (Criteria) this;
        }

        public Criteria andEND_TIMENotBetween(Date value1, Date value2) {
            addCriterion("END_TIME not between", value1, value2, "END_TIME");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table TB_WORKFLOW_VERSION
     *
     * @mbggenerated do_not_delete_during_merge Wed Oct 01 09:47:16 CST 2014
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table TB_WORKFLOW_VERSION
     *
     * @mbggenerated Wed Oct 01 09:47:16 CST 2014
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