package com.foya.dao.mybatis.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TbOrgAgentExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table TB_ORG_AGENT
     *
     * @mbggenerated Fri Apr 10 14:15:36 CST 2015
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table TB_ORG_AGENT
     *
     * @mbggenerated Fri Apr 10 14:15:36 CST 2015
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table TB_ORG_AGENT
     *
     * @mbggenerated Fri Apr 10 14:15:36 CST 2015
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_ORG_AGENT
     *
     * @mbggenerated Fri Apr 10 14:15:36 CST 2015
     */
    public TbOrgAgentExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_ORG_AGENT
     *
     * @mbggenerated Fri Apr 10 14:15:36 CST 2015
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_ORG_AGENT
     *
     * @mbggenerated Fri Apr 10 14:15:36 CST 2015
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_ORG_AGENT
     *
     * @mbggenerated Fri Apr 10 14:15:36 CST 2015
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_ORG_AGENT
     *
     * @mbggenerated Fri Apr 10 14:15:36 CST 2015
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_ORG_AGENT
     *
     * @mbggenerated Fri Apr 10 14:15:36 CST 2015
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_ORG_AGENT
     *
     * @mbggenerated Fri Apr 10 14:15:36 CST 2015
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_ORG_AGENT
     *
     * @mbggenerated Fri Apr 10 14:15:36 CST 2015
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_ORG_AGENT
     *
     * @mbggenerated Fri Apr 10 14:15:36 CST 2015
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
     * This method corresponds to the database table TB_ORG_AGENT
     *
     * @mbggenerated Fri Apr 10 14:15:36 CST 2015
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_ORG_AGENT
     *
     * @mbggenerated Fri Apr 10 14:15:36 CST 2015
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table TB_ORG_AGENT
     *
     * @mbggenerated Fri Apr 10 14:15:36 CST 2015
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

        public Criteria andPSN_NOIsNull() {
            addCriterion("PSN_NO is null");
            return (Criteria) this;
        }

        public Criteria andPSN_NOIsNotNull() {
            addCriterion("PSN_NO is not null");
            return (Criteria) this;
        }

        public Criteria andPSN_NOEqualTo(String value) {
            addCriterion("PSN_NO =", value, "PSN_NO");
            return (Criteria) this;
        }

        public Criteria andPSN_NONotEqualTo(String value) {
            addCriterion("PSN_NO <>", value, "PSN_NO");
            return (Criteria) this;
        }

        public Criteria andPSN_NOGreaterThan(String value) {
            addCriterion("PSN_NO >", value, "PSN_NO");
            return (Criteria) this;
        }

        public Criteria andPSN_NOGreaterThanOrEqualTo(String value) {
            addCriterion("PSN_NO >=", value, "PSN_NO");
            return (Criteria) this;
        }

        public Criteria andPSN_NOLessThan(String value) {
            addCriterion("PSN_NO <", value, "PSN_NO");
            return (Criteria) this;
        }

        public Criteria andPSN_NOLessThanOrEqualTo(String value) {
            addCriterion("PSN_NO <=", value, "PSN_NO");
            return (Criteria) this;
        }

        public Criteria andPSN_NOLike(String value) {
            addCriterion("PSN_NO like", value, "PSN_NO");
            return (Criteria) this;
        }

        public Criteria andPSN_NONotLike(String value) {
            addCriterion("PSN_NO not like", value, "PSN_NO");
            return (Criteria) this;
        }

        public Criteria andPSN_NOIn(List<String> values) {
            addCriterion("PSN_NO in", values, "PSN_NO");
            return (Criteria) this;
        }

        public Criteria andPSN_NONotIn(List<String> values) {
            addCriterion("PSN_NO not in", values, "PSN_NO");
            return (Criteria) this;
        }

        public Criteria andPSN_NOBetween(String value1, String value2) {
            addCriterion("PSN_NO between", value1, value2, "PSN_NO");
            return (Criteria) this;
        }

        public Criteria andPSN_NONotBetween(String value1, String value2) {
            addCriterion("PSN_NO not between", value1, value2, "PSN_NO");
            return (Criteria) this;
        }

        public Criteria andAGENT_PSN_NOIsNull() {
            addCriterion("AGENT_PSN_NO is null");
            return (Criteria) this;
        }

        public Criteria andAGENT_PSN_NOIsNotNull() {
            addCriterion("AGENT_PSN_NO is not null");
            return (Criteria) this;
        }

        public Criteria andAGENT_PSN_NOEqualTo(String value) {
            addCriterion("AGENT_PSN_NO =", value, "AGENT_PSN_NO");
            return (Criteria) this;
        }

        public Criteria andAGENT_PSN_NONotEqualTo(String value) {
            addCriterion("AGENT_PSN_NO <>", value, "AGENT_PSN_NO");
            return (Criteria) this;
        }

        public Criteria andAGENT_PSN_NOGreaterThan(String value) {
            addCriterion("AGENT_PSN_NO >", value, "AGENT_PSN_NO");
            return (Criteria) this;
        }

        public Criteria andAGENT_PSN_NOGreaterThanOrEqualTo(String value) {
            addCriterion("AGENT_PSN_NO >=", value, "AGENT_PSN_NO");
            return (Criteria) this;
        }

        public Criteria andAGENT_PSN_NOLessThan(String value) {
            addCriterion("AGENT_PSN_NO <", value, "AGENT_PSN_NO");
            return (Criteria) this;
        }

        public Criteria andAGENT_PSN_NOLessThanOrEqualTo(String value) {
            addCriterion("AGENT_PSN_NO <=", value, "AGENT_PSN_NO");
            return (Criteria) this;
        }

        public Criteria andAGENT_PSN_NOLike(String value) {
            addCriterion("AGENT_PSN_NO like", value, "AGENT_PSN_NO");
            return (Criteria) this;
        }

        public Criteria andAGENT_PSN_NONotLike(String value) {
            addCriterion("AGENT_PSN_NO not like", value, "AGENT_PSN_NO");
            return (Criteria) this;
        }

        public Criteria andAGENT_PSN_NOIn(List<String> values) {
            addCriterion("AGENT_PSN_NO in", values, "AGENT_PSN_NO");
            return (Criteria) this;
        }

        public Criteria andAGENT_PSN_NONotIn(List<String> values) {
            addCriterion("AGENT_PSN_NO not in", values, "AGENT_PSN_NO");
            return (Criteria) this;
        }

        public Criteria andAGENT_PSN_NOBetween(String value1, String value2) {
            addCriterion("AGENT_PSN_NO between", value1, value2, "AGENT_PSN_NO");
            return (Criteria) this;
        }

        public Criteria andAGENT_PSN_NONotBetween(String value1, String value2) {
            addCriterion("AGENT_PSN_NO not between", value1, value2, "AGENT_PSN_NO");
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

        public Criteria andSTATUSIsNull() {
            addCriterion("STATUS is null");
            return (Criteria) this;
        }

        public Criteria andSTATUSIsNotNull() {
            addCriterion("STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andSTATUSEqualTo(String value) {
            addCriterion("STATUS =", value, "STATUS");
            return (Criteria) this;
        }

        public Criteria andSTATUSNotEqualTo(String value) {
            addCriterion("STATUS <>", value, "STATUS");
            return (Criteria) this;
        }

        public Criteria andSTATUSGreaterThan(String value) {
            addCriterion("STATUS >", value, "STATUS");
            return (Criteria) this;
        }

        public Criteria andSTATUSGreaterThanOrEqualTo(String value) {
            addCriterion("STATUS >=", value, "STATUS");
            return (Criteria) this;
        }

        public Criteria andSTATUSLessThan(String value) {
            addCriterion("STATUS <", value, "STATUS");
            return (Criteria) this;
        }

        public Criteria andSTATUSLessThanOrEqualTo(String value) {
            addCriterion("STATUS <=", value, "STATUS");
            return (Criteria) this;
        }

        public Criteria andSTATUSLike(String value) {
            addCriterion("STATUS like", value, "STATUS");
            return (Criteria) this;
        }

        public Criteria andSTATUSNotLike(String value) {
            addCriterion("STATUS not like", value, "STATUS");
            return (Criteria) this;
        }

        public Criteria andSTATUSIn(List<String> values) {
            addCriterion("STATUS in", values, "STATUS");
            return (Criteria) this;
        }

        public Criteria andSTATUSNotIn(List<String> values) {
            addCriterion("STATUS not in", values, "STATUS");
            return (Criteria) this;
        }

        public Criteria andSTATUSBetween(String value1, String value2) {
            addCriterion("STATUS between", value1, value2, "STATUS");
            return (Criteria) this;
        }

        public Criteria andSTATUSNotBetween(String value1, String value2) {
            addCriterion("STATUS not between", value1, value2, "STATUS");
            return (Criteria) this;
        }

        public Criteria andCREATED_BYIsNull() {
            addCriterion("CREATED_BY is null");
            return (Criteria) this;
        }

        public Criteria andCREATED_BYIsNotNull() {
            addCriterion("CREATED_BY is not null");
            return (Criteria) this;
        }

        public Criteria andCREATED_BYEqualTo(String value) {
            addCriterion("CREATED_BY =", value, "CREATED_BY");
            return (Criteria) this;
        }

        public Criteria andCREATED_BYNotEqualTo(String value) {
            addCriterion("CREATED_BY <>", value, "CREATED_BY");
            return (Criteria) this;
        }

        public Criteria andCREATED_BYGreaterThan(String value) {
            addCriterion("CREATED_BY >", value, "CREATED_BY");
            return (Criteria) this;
        }

        public Criteria andCREATED_BYGreaterThanOrEqualTo(String value) {
            addCriterion("CREATED_BY >=", value, "CREATED_BY");
            return (Criteria) this;
        }

        public Criteria andCREATED_BYLessThan(String value) {
            addCriterion("CREATED_BY <", value, "CREATED_BY");
            return (Criteria) this;
        }

        public Criteria andCREATED_BYLessThanOrEqualTo(String value) {
            addCriterion("CREATED_BY <=", value, "CREATED_BY");
            return (Criteria) this;
        }

        public Criteria andCREATED_BYLike(String value) {
            addCriterion("CREATED_BY like", value, "CREATED_BY");
            return (Criteria) this;
        }

        public Criteria andCREATED_BYNotLike(String value) {
            addCriterion("CREATED_BY not like", value, "CREATED_BY");
            return (Criteria) this;
        }

        public Criteria andCREATED_BYIn(List<String> values) {
            addCriterion("CREATED_BY in", values, "CREATED_BY");
            return (Criteria) this;
        }

        public Criteria andCREATED_BYNotIn(List<String> values) {
            addCriterion("CREATED_BY not in", values, "CREATED_BY");
            return (Criteria) this;
        }

        public Criteria andCREATED_BYBetween(String value1, String value2) {
            addCriterion("CREATED_BY between", value1, value2, "CREATED_BY");
            return (Criteria) this;
        }

        public Criteria andCREATED_BYNotBetween(String value1, String value2) {
            addCriterion("CREATED_BY not between", value1, value2, "CREATED_BY");
            return (Criteria) this;
        }

        public Criteria andCREATED_DATEIsNull() {
            addCriterion("CREATED_DATE is null");
            return (Criteria) this;
        }

        public Criteria andCREATED_DATEIsNotNull() {
            addCriterion("CREATED_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andCREATED_DATEEqualTo(Date value) {
            addCriterion("CREATED_DATE =", value, "CREATED_DATE");
            return (Criteria) this;
        }

        public Criteria andCREATED_DATENotEqualTo(Date value) {
            addCriterion("CREATED_DATE <>", value, "CREATED_DATE");
            return (Criteria) this;
        }

        public Criteria andCREATED_DATEGreaterThan(Date value) {
            addCriterion("CREATED_DATE >", value, "CREATED_DATE");
            return (Criteria) this;
        }

        public Criteria andCREATED_DATEGreaterThanOrEqualTo(Date value) {
            addCriterion("CREATED_DATE >=", value, "CREATED_DATE");
            return (Criteria) this;
        }

        public Criteria andCREATED_DATELessThan(Date value) {
            addCriterion("CREATED_DATE <", value, "CREATED_DATE");
            return (Criteria) this;
        }

        public Criteria andCREATED_DATELessThanOrEqualTo(Date value) {
            addCriterion("CREATED_DATE <=", value, "CREATED_DATE");
            return (Criteria) this;
        }

        public Criteria andCREATED_DATEIn(List<Date> values) {
            addCriterion("CREATED_DATE in", values, "CREATED_DATE");
            return (Criteria) this;
        }

        public Criteria andCREATED_DATENotIn(List<Date> values) {
            addCriterion("CREATED_DATE not in", values, "CREATED_DATE");
            return (Criteria) this;
        }

        public Criteria andCREATED_DATEBetween(Date value1, Date value2) {
            addCriterion("CREATED_DATE between", value1, value2, "CREATED_DATE");
            return (Criteria) this;
        }

        public Criteria andCREATED_DATENotBetween(Date value1, Date value2) {
            addCriterion("CREATED_DATE not between", value1, value2, "CREATED_DATE");
            return (Criteria) this;
        }

        public Criteria andLAST_UPDATED_BYIsNull() {
            addCriterion("LAST_UPDATED_BY is null");
            return (Criteria) this;
        }

        public Criteria andLAST_UPDATED_BYIsNotNull() {
            addCriterion("LAST_UPDATED_BY is not null");
            return (Criteria) this;
        }

        public Criteria andLAST_UPDATED_BYEqualTo(String value) {
            addCriterion("LAST_UPDATED_BY =", value, "LAST_UPDATED_BY");
            return (Criteria) this;
        }

        public Criteria andLAST_UPDATED_BYNotEqualTo(String value) {
            addCriterion("LAST_UPDATED_BY <>", value, "LAST_UPDATED_BY");
            return (Criteria) this;
        }

        public Criteria andLAST_UPDATED_BYGreaterThan(String value) {
            addCriterion("LAST_UPDATED_BY >", value, "LAST_UPDATED_BY");
            return (Criteria) this;
        }

        public Criteria andLAST_UPDATED_BYGreaterThanOrEqualTo(String value) {
            addCriterion("LAST_UPDATED_BY >=", value, "LAST_UPDATED_BY");
            return (Criteria) this;
        }

        public Criteria andLAST_UPDATED_BYLessThan(String value) {
            addCriterion("LAST_UPDATED_BY <", value, "LAST_UPDATED_BY");
            return (Criteria) this;
        }

        public Criteria andLAST_UPDATED_BYLessThanOrEqualTo(String value) {
            addCriterion("LAST_UPDATED_BY <=", value, "LAST_UPDATED_BY");
            return (Criteria) this;
        }

        public Criteria andLAST_UPDATED_BYLike(String value) {
            addCriterion("LAST_UPDATED_BY like", value, "LAST_UPDATED_BY");
            return (Criteria) this;
        }

        public Criteria andLAST_UPDATED_BYNotLike(String value) {
            addCriterion("LAST_UPDATED_BY not like", value, "LAST_UPDATED_BY");
            return (Criteria) this;
        }

        public Criteria andLAST_UPDATED_BYIn(List<String> values) {
            addCriterion("LAST_UPDATED_BY in", values, "LAST_UPDATED_BY");
            return (Criteria) this;
        }

        public Criteria andLAST_UPDATED_BYNotIn(List<String> values) {
            addCriterion("LAST_UPDATED_BY not in", values, "LAST_UPDATED_BY");
            return (Criteria) this;
        }

        public Criteria andLAST_UPDATED_BYBetween(String value1, String value2) {
            addCriterion("LAST_UPDATED_BY between", value1, value2, "LAST_UPDATED_BY");
            return (Criteria) this;
        }

        public Criteria andLAST_UPDATED_BYNotBetween(String value1, String value2) {
            addCriterion("LAST_UPDATED_BY not between", value1, value2, "LAST_UPDATED_BY");
            return (Criteria) this;
        }

        public Criteria andLAST_UPDATED_DATEIsNull() {
            addCriterion("LAST_UPDATED_DATE is null");
            return (Criteria) this;
        }

        public Criteria andLAST_UPDATED_DATEIsNotNull() {
            addCriterion("LAST_UPDATED_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andLAST_UPDATED_DATEEqualTo(Date value) {
            addCriterion("LAST_UPDATED_DATE =", value, "LAST_UPDATED_DATE");
            return (Criteria) this;
        }

        public Criteria andLAST_UPDATED_DATENotEqualTo(Date value) {
            addCriterion("LAST_UPDATED_DATE <>", value, "LAST_UPDATED_DATE");
            return (Criteria) this;
        }

        public Criteria andLAST_UPDATED_DATEGreaterThan(Date value) {
            addCriterion("LAST_UPDATED_DATE >", value, "LAST_UPDATED_DATE");
            return (Criteria) this;
        }

        public Criteria andLAST_UPDATED_DATEGreaterThanOrEqualTo(Date value) {
            addCriterion("LAST_UPDATED_DATE >=", value, "LAST_UPDATED_DATE");
            return (Criteria) this;
        }

        public Criteria andLAST_UPDATED_DATELessThan(Date value) {
            addCriterion("LAST_UPDATED_DATE <", value, "LAST_UPDATED_DATE");
            return (Criteria) this;
        }

        public Criteria andLAST_UPDATED_DATELessThanOrEqualTo(Date value) {
            addCriterion("LAST_UPDATED_DATE <=", value, "LAST_UPDATED_DATE");
            return (Criteria) this;
        }

        public Criteria andLAST_UPDATED_DATEIn(List<Date> values) {
            addCriterion("LAST_UPDATED_DATE in", values, "LAST_UPDATED_DATE");
            return (Criteria) this;
        }

        public Criteria andLAST_UPDATED_DATENotIn(List<Date> values) {
            addCriterion("LAST_UPDATED_DATE not in", values, "LAST_UPDATED_DATE");
            return (Criteria) this;
        }

        public Criteria andLAST_UPDATED_DATEBetween(Date value1, Date value2) {
            addCriterion("LAST_UPDATED_DATE between", value1, value2, "LAST_UPDATED_DATE");
            return (Criteria) this;
        }

        public Criteria andLAST_UPDATED_DATENotBetween(Date value1, Date value2) {
            addCriterion("LAST_UPDATED_DATE not between", value1, value2, "LAST_UPDATED_DATE");
            return (Criteria) this;
        }

        public Criteria andSYNC_IDIsNull() {
            addCriterion("SYNC_ID is null");
            return (Criteria) this;
        }

        public Criteria andSYNC_IDIsNotNull() {
            addCriterion("SYNC_ID is not null");
            return (Criteria) this;
        }

        public Criteria andSYNC_IDEqualTo(String value) {
            addCriterion("SYNC_ID =", value, "SYNC_ID");
            return (Criteria) this;
        }

        public Criteria andSYNC_IDNotEqualTo(String value) {
            addCriterion("SYNC_ID <>", value, "SYNC_ID");
            return (Criteria) this;
        }

        public Criteria andSYNC_IDGreaterThan(String value) {
            addCriterion("SYNC_ID >", value, "SYNC_ID");
            return (Criteria) this;
        }

        public Criteria andSYNC_IDGreaterThanOrEqualTo(String value) {
            addCriterion("SYNC_ID >=", value, "SYNC_ID");
            return (Criteria) this;
        }

        public Criteria andSYNC_IDLessThan(String value) {
            addCriterion("SYNC_ID <", value, "SYNC_ID");
            return (Criteria) this;
        }

        public Criteria andSYNC_IDLessThanOrEqualTo(String value) {
            addCriterion("SYNC_ID <=", value, "SYNC_ID");
            return (Criteria) this;
        }

        public Criteria andSYNC_IDLike(String value) {
            addCriterion("SYNC_ID like", value, "SYNC_ID");
            return (Criteria) this;
        }

        public Criteria andSYNC_IDNotLike(String value) {
            addCriterion("SYNC_ID not like", value, "SYNC_ID");
            return (Criteria) this;
        }

        public Criteria andSYNC_IDIn(List<String> values) {
            addCriterion("SYNC_ID in", values, "SYNC_ID");
            return (Criteria) this;
        }

        public Criteria andSYNC_IDNotIn(List<String> values) {
            addCriterion("SYNC_ID not in", values, "SYNC_ID");
            return (Criteria) this;
        }

        public Criteria andSYNC_IDBetween(String value1, String value2) {
            addCriterion("SYNC_ID between", value1, value2, "SYNC_ID");
            return (Criteria) this;
        }

        public Criteria andSYNC_IDNotBetween(String value1, String value2) {
            addCriterion("SYNC_ID not between", value1, value2, "SYNC_ID");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table TB_ORG_AGENT
     *
     * @mbggenerated do_not_delete_during_merge Fri Apr 10 14:15:36 CST 2015
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table TB_ORG_AGENT
     *
     * @mbggenerated Fri Apr 10 14:15:36 CST 2015
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