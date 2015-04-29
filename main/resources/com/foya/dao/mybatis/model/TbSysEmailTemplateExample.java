package com.foya.dao.mybatis.model;

import java.util.ArrayList;
import java.util.List;

public class TbSysEmailTemplateExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table TB_SYS_EMAIL_TEMPLATE
     *
     * @mbggenerated Sat Dec 27 13:55:43 CST 2014
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table TB_SYS_EMAIL_TEMPLATE
     *
     * @mbggenerated Sat Dec 27 13:55:43 CST 2014
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table TB_SYS_EMAIL_TEMPLATE
     *
     * @mbggenerated Sat Dec 27 13:55:43 CST 2014
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_SYS_EMAIL_TEMPLATE
     *
     * @mbggenerated Sat Dec 27 13:55:43 CST 2014
     */
    public TbSysEmailTemplateExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_SYS_EMAIL_TEMPLATE
     *
     * @mbggenerated Sat Dec 27 13:55:43 CST 2014
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_SYS_EMAIL_TEMPLATE
     *
     * @mbggenerated Sat Dec 27 13:55:43 CST 2014
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_SYS_EMAIL_TEMPLATE
     *
     * @mbggenerated Sat Dec 27 13:55:43 CST 2014
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_SYS_EMAIL_TEMPLATE
     *
     * @mbggenerated Sat Dec 27 13:55:43 CST 2014
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_SYS_EMAIL_TEMPLATE
     *
     * @mbggenerated Sat Dec 27 13:55:43 CST 2014
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_SYS_EMAIL_TEMPLATE
     *
     * @mbggenerated Sat Dec 27 13:55:43 CST 2014
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_SYS_EMAIL_TEMPLATE
     *
     * @mbggenerated Sat Dec 27 13:55:43 CST 2014
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_SYS_EMAIL_TEMPLATE
     *
     * @mbggenerated Sat Dec 27 13:55:43 CST 2014
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
     * This method corresponds to the database table TB_SYS_EMAIL_TEMPLATE
     *
     * @mbggenerated Sat Dec 27 13:55:43 CST 2014
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_SYS_EMAIL_TEMPLATE
     *
     * @mbggenerated Sat Dec 27 13:55:43 CST 2014
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table TB_SYS_EMAIL_TEMPLATE
     *
     * @mbggenerated Sat Dec 27 13:55:43 CST 2014
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

        public Criteria andEMAIL_TYPEIsNull() {
            addCriterion("EMAIL_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andEMAIL_TYPEIsNotNull() {
            addCriterion("EMAIL_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andEMAIL_TYPEEqualTo(String value) {
            addCriterion("EMAIL_TYPE =", value, "EMAIL_TYPE");
            return (Criteria) this;
        }

        public Criteria andEMAIL_TYPENotEqualTo(String value) {
            addCriterion("EMAIL_TYPE <>", value, "EMAIL_TYPE");
            return (Criteria) this;
        }

        public Criteria andEMAIL_TYPEGreaterThan(String value) {
            addCriterion("EMAIL_TYPE >", value, "EMAIL_TYPE");
            return (Criteria) this;
        }

        public Criteria andEMAIL_TYPEGreaterThanOrEqualTo(String value) {
            addCriterion("EMAIL_TYPE >=", value, "EMAIL_TYPE");
            return (Criteria) this;
        }

        public Criteria andEMAIL_TYPELessThan(String value) {
            addCriterion("EMAIL_TYPE <", value, "EMAIL_TYPE");
            return (Criteria) this;
        }

        public Criteria andEMAIL_TYPELessThanOrEqualTo(String value) {
            addCriterion("EMAIL_TYPE <=", value, "EMAIL_TYPE");
            return (Criteria) this;
        }

        public Criteria andEMAIL_TYPELike(String value) {
            addCriterion("EMAIL_TYPE like", value, "EMAIL_TYPE");
            return (Criteria) this;
        }

        public Criteria andEMAIL_TYPENotLike(String value) {
            addCriterion("EMAIL_TYPE not like", value, "EMAIL_TYPE");
            return (Criteria) this;
        }

        public Criteria andEMAIL_TYPEIn(List<String> values) {
            addCriterion("EMAIL_TYPE in", values, "EMAIL_TYPE");
            return (Criteria) this;
        }

        public Criteria andEMAIL_TYPENotIn(List<String> values) {
            addCriterion("EMAIL_TYPE not in", values, "EMAIL_TYPE");
            return (Criteria) this;
        }

        public Criteria andEMAIL_TYPEBetween(String value1, String value2) {
            addCriterion("EMAIL_TYPE between", value1, value2, "EMAIL_TYPE");
            return (Criteria) this;
        }

        public Criteria andEMAIL_TYPENotBetween(String value1, String value2) {
            addCriterion("EMAIL_TYPE not between", value1, value2, "EMAIL_TYPE");
            return (Criteria) this;
        }

        public Criteria andPRIMARY_EMAILIsNull() {
            addCriterion("PRIMARY_EMAIL is null");
            return (Criteria) this;
        }

        public Criteria andPRIMARY_EMAILIsNotNull() {
            addCriterion("PRIMARY_EMAIL is not null");
            return (Criteria) this;
        }

        public Criteria andPRIMARY_EMAILEqualTo(String value) {
            addCriterion("PRIMARY_EMAIL =", value, "PRIMARY_EMAIL");
            return (Criteria) this;
        }

        public Criteria andPRIMARY_EMAILNotEqualTo(String value) {
            addCriterion("PRIMARY_EMAIL <>", value, "PRIMARY_EMAIL");
            return (Criteria) this;
        }

        public Criteria andPRIMARY_EMAILGreaterThan(String value) {
            addCriterion("PRIMARY_EMAIL >", value, "PRIMARY_EMAIL");
            return (Criteria) this;
        }

        public Criteria andPRIMARY_EMAILGreaterThanOrEqualTo(String value) {
            addCriterion("PRIMARY_EMAIL >=", value, "PRIMARY_EMAIL");
            return (Criteria) this;
        }

        public Criteria andPRIMARY_EMAILLessThan(String value) {
            addCriterion("PRIMARY_EMAIL <", value, "PRIMARY_EMAIL");
            return (Criteria) this;
        }

        public Criteria andPRIMARY_EMAILLessThanOrEqualTo(String value) {
            addCriterion("PRIMARY_EMAIL <=", value, "PRIMARY_EMAIL");
            return (Criteria) this;
        }

        public Criteria andPRIMARY_EMAILLike(String value) {
            addCriterion("PRIMARY_EMAIL like", value, "PRIMARY_EMAIL");
            return (Criteria) this;
        }

        public Criteria andPRIMARY_EMAILNotLike(String value) {
            addCriterion("PRIMARY_EMAIL not like", value, "PRIMARY_EMAIL");
            return (Criteria) this;
        }

        public Criteria andPRIMARY_EMAILIn(List<String> values) {
            addCriterion("PRIMARY_EMAIL in", values, "PRIMARY_EMAIL");
            return (Criteria) this;
        }

        public Criteria andPRIMARY_EMAILNotIn(List<String> values) {
            addCriterion("PRIMARY_EMAIL not in", values, "PRIMARY_EMAIL");
            return (Criteria) this;
        }

        public Criteria andPRIMARY_EMAILBetween(String value1, String value2) {
            addCriterion("PRIMARY_EMAIL between", value1, value2, "PRIMARY_EMAIL");
            return (Criteria) this;
        }

        public Criteria andPRIMARY_EMAILNotBetween(String value1, String value2) {
            addCriterion("PRIMARY_EMAIL not between", value1, value2, "PRIMARY_EMAIL");
            return (Criteria) this;
        }

        public Criteria andSUBJECTIsNull() {
            addCriterion("SUBJECT is null");
            return (Criteria) this;
        }

        public Criteria andSUBJECTIsNotNull() {
            addCriterion("SUBJECT is not null");
            return (Criteria) this;
        }

        public Criteria andSUBJECTEqualTo(String value) {
            addCriterion("SUBJECT =", value, "SUBJECT");
            return (Criteria) this;
        }

        public Criteria andSUBJECTNotEqualTo(String value) {
            addCriterion("SUBJECT <>", value, "SUBJECT");
            return (Criteria) this;
        }

        public Criteria andSUBJECTGreaterThan(String value) {
            addCriterion("SUBJECT >", value, "SUBJECT");
            return (Criteria) this;
        }

        public Criteria andSUBJECTGreaterThanOrEqualTo(String value) {
            addCriterion("SUBJECT >=", value, "SUBJECT");
            return (Criteria) this;
        }

        public Criteria andSUBJECTLessThan(String value) {
            addCriterion("SUBJECT <", value, "SUBJECT");
            return (Criteria) this;
        }

        public Criteria andSUBJECTLessThanOrEqualTo(String value) {
            addCriterion("SUBJECT <=", value, "SUBJECT");
            return (Criteria) this;
        }

        public Criteria andSUBJECTLike(String value) {
            addCriterion("SUBJECT like", value, "SUBJECT");
            return (Criteria) this;
        }

        public Criteria andSUBJECTNotLike(String value) {
            addCriterion("SUBJECT not like", value, "SUBJECT");
            return (Criteria) this;
        }

        public Criteria andSUBJECTIn(List<String> values) {
            addCriterion("SUBJECT in", values, "SUBJECT");
            return (Criteria) this;
        }

        public Criteria andSUBJECTNotIn(List<String> values) {
            addCriterion("SUBJECT not in", values, "SUBJECT");
            return (Criteria) this;
        }

        public Criteria andSUBJECTBetween(String value1, String value2) {
            addCriterion("SUBJECT between", value1, value2, "SUBJECT");
            return (Criteria) this;
        }

        public Criteria andSUBJECTNotBetween(String value1, String value2) {
            addCriterion("SUBJECT not between", value1, value2, "SUBJECT");
            return (Criteria) this;
        }

        public Criteria andCONTENTIsNull() {
            addCriterion("CONTENT is null");
            return (Criteria) this;
        }

        public Criteria andCONTENTIsNotNull() {
            addCriterion("CONTENT is not null");
            return (Criteria) this;
        }

        public Criteria andCONTENTEqualTo(String value) {
            addCriterion("CONTENT =", value, "CONTENT");
            return (Criteria) this;
        }

        public Criteria andCONTENTNotEqualTo(String value) {
            addCriterion("CONTENT <>", value, "CONTENT");
            return (Criteria) this;
        }

        public Criteria andCONTENTGreaterThan(String value) {
            addCriterion("CONTENT >", value, "CONTENT");
            return (Criteria) this;
        }

        public Criteria andCONTENTGreaterThanOrEqualTo(String value) {
            addCriterion("CONTENT >=", value, "CONTENT");
            return (Criteria) this;
        }

        public Criteria andCONTENTLessThan(String value) {
            addCriterion("CONTENT <", value, "CONTENT");
            return (Criteria) this;
        }

        public Criteria andCONTENTLessThanOrEqualTo(String value) {
            addCriterion("CONTENT <=", value, "CONTENT");
            return (Criteria) this;
        }

        public Criteria andCONTENTLike(String value) {
            addCriterion("CONTENT like", value, "CONTENT");
            return (Criteria) this;
        }

        public Criteria andCONTENTNotLike(String value) {
            addCriterion("CONTENT not like", value, "CONTENT");
            return (Criteria) this;
        }

        public Criteria andCONTENTIn(List<String> values) {
            addCriterion("CONTENT in", values, "CONTENT");
            return (Criteria) this;
        }

        public Criteria andCONTENTNotIn(List<String> values) {
            addCriterion("CONTENT not in", values, "CONTENT");
            return (Criteria) this;
        }

        public Criteria andCONTENTBetween(String value1, String value2) {
            addCriterion("CONTENT between", value1, value2, "CONTENT");
            return (Criteria) this;
        }

        public Criteria andCONTENTNotBetween(String value1, String value2) {
            addCriterion("CONTENT not between", value1, value2, "CONTENT");
            return (Criteria) this;
        }

        public Criteria andCC_EMAILIsNull() {
            addCriterion("CC_EMAIL is null");
            return (Criteria) this;
        }

        public Criteria andCC_EMAILIsNotNull() {
            addCriterion("CC_EMAIL is not null");
            return (Criteria) this;
        }

        public Criteria andCC_EMAILEqualTo(String value) {
            addCriterion("CC_EMAIL =", value, "CC_EMAIL");
            return (Criteria) this;
        }

        public Criteria andCC_EMAILNotEqualTo(String value) {
            addCriterion("CC_EMAIL <>", value, "CC_EMAIL");
            return (Criteria) this;
        }

        public Criteria andCC_EMAILGreaterThan(String value) {
            addCriterion("CC_EMAIL >", value, "CC_EMAIL");
            return (Criteria) this;
        }

        public Criteria andCC_EMAILGreaterThanOrEqualTo(String value) {
            addCriterion("CC_EMAIL >=", value, "CC_EMAIL");
            return (Criteria) this;
        }

        public Criteria andCC_EMAILLessThan(String value) {
            addCriterion("CC_EMAIL <", value, "CC_EMAIL");
            return (Criteria) this;
        }

        public Criteria andCC_EMAILLessThanOrEqualTo(String value) {
            addCriterion("CC_EMAIL <=", value, "CC_EMAIL");
            return (Criteria) this;
        }

        public Criteria andCC_EMAILLike(String value) {
            addCriterion("CC_EMAIL like", value, "CC_EMAIL");
            return (Criteria) this;
        }

        public Criteria andCC_EMAILNotLike(String value) {
            addCriterion("CC_EMAIL not like", value, "CC_EMAIL");
            return (Criteria) this;
        }

        public Criteria andCC_EMAILIn(List<String> values) {
            addCriterion("CC_EMAIL in", values, "CC_EMAIL");
            return (Criteria) this;
        }

        public Criteria andCC_EMAILNotIn(List<String> values) {
            addCriterion("CC_EMAIL not in", values, "CC_EMAIL");
            return (Criteria) this;
        }

        public Criteria andCC_EMAILBetween(String value1, String value2) {
            addCriterion("CC_EMAIL between", value1, value2, "CC_EMAIL");
            return (Criteria) this;
        }

        public Criteria andCC_EMAILNotBetween(String value1, String value2) {
            addCriterion("CC_EMAIL not between", value1, value2, "CC_EMAIL");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table TB_SYS_EMAIL_TEMPLATE
     *
     * @mbggenerated do_not_delete_during_merge Sat Dec 27 13:55:43 CST 2014
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table TB_SYS_EMAIL_TEMPLATE
     *
     * @mbggenerated Sat Dec 27 13:55:43 CST 2014
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