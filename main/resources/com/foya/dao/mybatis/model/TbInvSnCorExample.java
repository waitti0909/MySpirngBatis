package com.foya.dao.mybatis.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TbInvSnCorExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table TB_INV_SN_COR
     *
     * @mbggenerated Fri Nov 28 14:21:44 CST 2014
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table TB_INV_SN_COR
     *
     * @mbggenerated Fri Nov 28 14:21:44 CST 2014
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table TB_INV_SN_COR
     *
     * @mbggenerated Fri Nov 28 14:21:44 CST 2014
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_INV_SN_COR
     *
     * @mbggenerated Fri Nov 28 14:21:44 CST 2014
     */
    public TbInvSnCorExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_INV_SN_COR
     *
     * @mbggenerated Fri Nov 28 14:21:44 CST 2014
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_INV_SN_COR
     *
     * @mbggenerated Fri Nov 28 14:21:44 CST 2014
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_INV_SN_COR
     *
     * @mbggenerated Fri Nov 28 14:21:44 CST 2014
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_INV_SN_COR
     *
     * @mbggenerated Fri Nov 28 14:21:44 CST 2014
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_INV_SN_COR
     *
     * @mbggenerated Fri Nov 28 14:21:44 CST 2014
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_INV_SN_COR
     *
     * @mbggenerated Fri Nov 28 14:21:44 CST 2014
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_INV_SN_COR
     *
     * @mbggenerated Fri Nov 28 14:21:44 CST 2014
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_INV_SN_COR
     *
     * @mbggenerated Fri Nov 28 14:21:44 CST 2014
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
     * This method corresponds to the database table TB_INV_SN_COR
     *
     * @mbggenerated Fri Nov 28 14:21:44 CST 2014
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_INV_SN_COR
     *
     * @mbggenerated Fri Nov 28 14:21:44 CST 2014
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table TB_INV_SN_COR
     *
     * @mbggenerated Fri Nov 28 14:21:44 CST 2014
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

        public Criteria andCor_noIsNull() {
            addCriterion("cor_no is null");
            return (Criteria) this;
        }

        public Criteria andCor_noIsNotNull() {
            addCriterion("cor_no is not null");
            return (Criteria) this;
        }

        public Criteria andCor_noEqualTo(Long value) {
            addCriterion("cor_no =", value, "cor_no");
            return (Criteria) this;
        }

        public Criteria andCor_noNotEqualTo(Long value) {
            addCriterion("cor_no <>", value, "cor_no");
            return (Criteria) this;
        }

        public Criteria andCor_noGreaterThan(Long value) {
            addCriterion("cor_no >", value, "cor_no");
            return (Criteria) this;
        }

        public Criteria andCor_noGreaterThanOrEqualTo(Long value) {
            addCriterion("cor_no >=", value, "cor_no");
            return (Criteria) this;
        }

        public Criteria andCor_noLessThan(Long value) {
            addCriterion("cor_no <", value, "cor_no");
            return (Criteria) this;
        }

        public Criteria andCor_noLessThanOrEqualTo(Long value) {
            addCriterion("cor_no <=", value, "cor_no");
            return (Criteria) this;
        }

        public Criteria andCor_noIn(List<Long> values) {
            addCriterion("cor_no in", values, "cor_no");
            return (Criteria) this;
        }

        public Criteria andCor_noNotIn(List<Long> values) {
            addCriterion("cor_no not in", values, "cor_no");
            return (Criteria) this;
        }

        public Criteria andCor_noBetween(Long value1, Long value2) {
            addCriterion("cor_no between", value1, value2, "cor_no");
            return (Criteria) this;
        }

        public Criteria andCor_noNotBetween(Long value1, Long value2) {
            addCriterion("cor_no not between", value1, value2, "cor_no");
            return (Criteria) this;
        }

        public Criteria andSrl_noIsNull() {
            addCriterion("srl_no is null");
            return (Criteria) this;
        }

        public Criteria andSrl_noIsNotNull() {
            addCriterion("srl_no is not null");
            return (Criteria) this;
        }

        public Criteria andSrl_noEqualTo(Long value) {
            addCriterion("srl_no =", value, "srl_no");
            return (Criteria) this;
        }

        public Criteria andSrl_noNotEqualTo(Long value) {
            addCriterion("srl_no <>", value, "srl_no");
            return (Criteria) this;
        }

        public Criteria andSrl_noGreaterThan(Long value) {
            addCriterion("srl_no >", value, "srl_no");
            return (Criteria) this;
        }

        public Criteria andSrl_noGreaterThanOrEqualTo(Long value) {
            addCriterion("srl_no >=", value, "srl_no");
            return (Criteria) this;
        }

        public Criteria andSrl_noLessThan(Long value) {
            addCriterion("srl_no <", value, "srl_no");
            return (Criteria) this;
        }

        public Criteria andSrl_noLessThanOrEqualTo(Long value) {
            addCriterion("srl_no <=", value, "srl_no");
            return (Criteria) this;
        }

        public Criteria andSrl_noIn(List<Long> values) {
            addCriterion("srl_no in", values, "srl_no");
            return (Criteria) this;
        }

        public Criteria andSrl_noNotIn(List<Long> values) {
            addCriterion("srl_no not in", values, "srl_no");
            return (Criteria) this;
        }

        public Criteria andSrl_noBetween(Long value1, Long value2) {
            addCriterion("srl_no between", value1, value2, "srl_no");
            return (Criteria) this;
        }

        public Criteria andSrl_noNotBetween(Long value1, Long value2) {
            addCriterion("srl_no not between", value1, value2, "srl_no");
            return (Criteria) this;
        }

        public Criteria andVen_snIsNull() {
            addCriterion("ven_sn is null");
            return (Criteria) this;
        }

        public Criteria andVen_snIsNotNull() {
            addCriterion("ven_sn is not null");
            return (Criteria) this;
        }

        public Criteria andVen_snEqualTo(String value) {
            addCriterion("ven_sn =", value, "ven_sn");
            return (Criteria) this;
        }

        public Criteria andVen_snNotEqualTo(String value) {
            addCriterion("ven_sn <>", value, "ven_sn");
            return (Criteria) this;
        }

        public Criteria andVen_snGreaterThan(String value) {
            addCriterion("ven_sn >", value, "ven_sn");
            return (Criteria) this;
        }

        public Criteria andVen_snGreaterThanOrEqualTo(String value) {
            addCriterion("ven_sn >=", value, "ven_sn");
            return (Criteria) this;
        }

        public Criteria andVen_snLessThan(String value) {
            addCriterion("ven_sn <", value, "ven_sn");
            return (Criteria) this;
        }

        public Criteria andVen_snLessThanOrEqualTo(String value) {
            addCriterion("ven_sn <=", value, "ven_sn");
            return (Criteria) this;
        }

        public Criteria andVen_snLike(String value) {
            addCriterion("ven_sn like", value, "ven_sn");
            return (Criteria) this;
        }

        public Criteria andVen_snNotLike(String value) {
            addCriterion("ven_sn not like", value, "ven_sn");
            return (Criteria) this;
        }

        public Criteria andVen_snIn(List<String> values) {
            addCriterion("ven_sn in", values, "ven_sn");
            return (Criteria) this;
        }

        public Criteria andVen_snNotIn(List<String> values) {
            addCriterion("ven_sn not in", values, "ven_sn");
            return (Criteria) this;
        }

        public Criteria andVen_snBetween(String value1, String value2) {
            addCriterion("ven_sn between", value1, value2, "ven_sn");
            return (Criteria) this;
        }

        public Criteria andVen_snNotBetween(String value1, String value2) {
            addCriterion("ven_sn not between", value1, value2, "ven_sn");
            return (Criteria) this;
        }

        public Criteria andOld_ven_snIsNull() {
            addCriterion("old_ven_sn is null");
            return (Criteria) this;
        }

        public Criteria andOld_ven_snIsNotNull() {
            addCriterion("old_ven_sn is not null");
            return (Criteria) this;
        }

        public Criteria andOld_ven_snEqualTo(String value) {
            addCriterion("old_ven_sn =", value, "old_ven_sn");
            return (Criteria) this;
        }

        public Criteria andOld_ven_snNotEqualTo(String value) {
            addCriterion("old_ven_sn <>", value, "old_ven_sn");
            return (Criteria) this;
        }

        public Criteria andOld_ven_snGreaterThan(String value) {
            addCriterion("old_ven_sn >", value, "old_ven_sn");
            return (Criteria) this;
        }

        public Criteria andOld_ven_snGreaterThanOrEqualTo(String value) {
            addCriterion("old_ven_sn >=", value, "old_ven_sn");
            return (Criteria) this;
        }

        public Criteria andOld_ven_snLessThan(String value) {
            addCriterion("old_ven_sn <", value, "old_ven_sn");
            return (Criteria) this;
        }

        public Criteria andOld_ven_snLessThanOrEqualTo(String value) {
            addCriterion("old_ven_sn <=", value, "old_ven_sn");
            return (Criteria) this;
        }

        public Criteria andOld_ven_snLike(String value) {
            addCriterion("old_ven_sn like", value, "old_ven_sn");
            return (Criteria) this;
        }

        public Criteria andOld_ven_snNotLike(String value) {
            addCriterion("old_ven_sn not like", value, "old_ven_sn");
            return (Criteria) this;
        }

        public Criteria andOld_ven_snIn(List<String> values) {
            addCriterion("old_ven_sn in", values, "old_ven_sn");
            return (Criteria) this;
        }

        public Criteria andOld_ven_snNotIn(List<String> values) {
            addCriterion("old_ven_sn not in", values, "old_ven_sn");
            return (Criteria) this;
        }

        public Criteria andOld_ven_snBetween(String value1, String value2) {
            addCriterion("old_ven_sn between", value1, value2, "old_ven_sn");
            return (Criteria) this;
        }

        public Criteria andOld_ven_snNotBetween(String value1, String value2) {
            addCriterion("old_ven_sn not between", value1, value2, "old_ven_sn");
            return (Criteria) this;
        }

        public Criteria andTran_dept_idIsNull() {
            addCriterion("tran_dept_id is null");
            return (Criteria) this;
        }

        public Criteria andTran_dept_idIsNotNull() {
            addCriterion("tran_dept_id is not null");
            return (Criteria) this;
        }

        public Criteria andTran_dept_idEqualTo(String value) {
            addCriterion("tran_dept_id =", value, "tran_dept_id");
            return (Criteria) this;
        }

        public Criteria andTran_dept_idNotEqualTo(String value) {
            addCriterion("tran_dept_id <>", value, "tran_dept_id");
            return (Criteria) this;
        }

        public Criteria andTran_dept_idGreaterThan(String value) {
            addCriterion("tran_dept_id >", value, "tran_dept_id");
            return (Criteria) this;
        }

        public Criteria andTran_dept_idGreaterThanOrEqualTo(String value) {
            addCriterion("tran_dept_id >=", value, "tran_dept_id");
            return (Criteria) this;
        }

        public Criteria andTran_dept_idLessThan(String value) {
            addCriterion("tran_dept_id <", value, "tran_dept_id");
            return (Criteria) this;
        }

        public Criteria andTran_dept_idLessThanOrEqualTo(String value) {
            addCriterion("tran_dept_id <=", value, "tran_dept_id");
            return (Criteria) this;
        }

        public Criteria andTran_dept_idLike(String value) {
            addCriterion("tran_dept_id like", value, "tran_dept_id");
            return (Criteria) this;
        }

        public Criteria andTran_dept_idNotLike(String value) {
            addCriterion("tran_dept_id not like", value, "tran_dept_id");
            return (Criteria) this;
        }

        public Criteria andTran_dept_idIn(List<String> values) {
            addCriterion("tran_dept_id in", values, "tran_dept_id");
            return (Criteria) this;
        }

        public Criteria andTran_dept_idNotIn(List<String> values) {
            addCriterion("tran_dept_id not in", values, "tran_dept_id");
            return (Criteria) this;
        }

        public Criteria andTran_dept_idBetween(String value1, String value2) {
            addCriterion("tran_dept_id between", value1, value2, "tran_dept_id");
            return (Criteria) this;
        }

        public Criteria andTran_dept_idNotBetween(String value1, String value2) {
            addCriterion("tran_dept_id not between", value1, value2, "tran_dept_id");
            return (Criteria) this;
        }

        public Criteria andMemoIsNull() {
            addCriterion("memo is null");
            return (Criteria) this;
        }

        public Criteria andMemoIsNotNull() {
            addCriterion("memo is not null");
            return (Criteria) this;
        }

        public Criteria andMemoEqualTo(String value) {
            addCriterion("memo =", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoNotEqualTo(String value) {
            addCriterion("memo <>", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoGreaterThan(String value) {
            addCriterion("memo >", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoGreaterThanOrEqualTo(String value) {
            addCriterion("memo >=", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoLessThan(String value) {
            addCriterion("memo <", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoLessThanOrEqualTo(String value) {
            addCriterion("memo <=", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoLike(String value) {
            addCriterion("memo like", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoNotLike(String value) {
            addCriterion("memo not like", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoIn(List<String> values) {
            addCriterion("memo in", values, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoNotIn(List<String> values) {
            addCriterion("memo not in", values, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoBetween(String value1, String value2) {
            addCriterion("memo between", value1, value2, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoNotBetween(String value1, String value2) {
            addCriterion("memo not between", value1, value2, "memo");
            return (Criteria) this;
        }

        public Criteria andCr_userIsNull() {
            addCriterion("cr_user is null");
            return (Criteria) this;
        }

        public Criteria andCr_userIsNotNull() {
            addCriterion("cr_user is not null");
            return (Criteria) this;
        }

        public Criteria andCr_userEqualTo(String value) {
            addCriterion("cr_user =", value, "cr_user");
            return (Criteria) this;
        }

        public Criteria andCr_userNotEqualTo(String value) {
            addCriterion("cr_user <>", value, "cr_user");
            return (Criteria) this;
        }

        public Criteria andCr_userGreaterThan(String value) {
            addCriterion("cr_user >", value, "cr_user");
            return (Criteria) this;
        }

        public Criteria andCr_userGreaterThanOrEqualTo(String value) {
            addCriterion("cr_user >=", value, "cr_user");
            return (Criteria) this;
        }

        public Criteria andCr_userLessThan(String value) {
            addCriterion("cr_user <", value, "cr_user");
            return (Criteria) this;
        }

        public Criteria andCr_userLessThanOrEqualTo(String value) {
            addCriterion("cr_user <=", value, "cr_user");
            return (Criteria) this;
        }

        public Criteria andCr_userLike(String value) {
            addCriterion("cr_user like", value, "cr_user");
            return (Criteria) this;
        }

        public Criteria andCr_userNotLike(String value) {
            addCriterion("cr_user not like", value, "cr_user");
            return (Criteria) this;
        }

        public Criteria andCr_userIn(List<String> values) {
            addCriterion("cr_user in", values, "cr_user");
            return (Criteria) this;
        }

        public Criteria andCr_userNotIn(List<String> values) {
            addCriterion("cr_user not in", values, "cr_user");
            return (Criteria) this;
        }

        public Criteria andCr_userBetween(String value1, String value2) {
            addCriterion("cr_user between", value1, value2, "cr_user");
            return (Criteria) this;
        }

        public Criteria andCr_userNotBetween(String value1, String value2) {
            addCriterion("cr_user not between", value1, value2, "cr_user");
            return (Criteria) this;
        }

        public Criteria andCr_timeIsNull() {
            addCriterion("cr_time is null");
            return (Criteria) this;
        }

        public Criteria andCr_timeIsNotNull() {
            addCriterion("cr_time is not null");
            return (Criteria) this;
        }

        public Criteria andCr_timeEqualTo(Date value) {
            addCriterion("cr_time =", value, "cr_time");
            return (Criteria) this;
        }

        public Criteria andCr_timeNotEqualTo(Date value) {
            addCriterion("cr_time <>", value, "cr_time");
            return (Criteria) this;
        }

        public Criteria andCr_timeGreaterThan(Date value) {
            addCriterion("cr_time >", value, "cr_time");
            return (Criteria) this;
        }

        public Criteria andCr_timeGreaterThanOrEqualTo(Date value) {
            addCriterion("cr_time >=", value, "cr_time");
            return (Criteria) this;
        }

        public Criteria andCr_timeLessThan(Date value) {
            addCriterion("cr_time <", value, "cr_time");
            return (Criteria) this;
        }

        public Criteria andCr_timeLessThanOrEqualTo(Date value) {
            addCriterion("cr_time <=", value, "cr_time");
            return (Criteria) this;
        }

        public Criteria andCr_timeIn(List<Date> values) {
            addCriterion("cr_time in", values, "cr_time");
            return (Criteria) this;
        }

        public Criteria andCr_timeNotIn(List<Date> values) {
            addCriterion("cr_time not in", values, "cr_time");
            return (Criteria) this;
        }

        public Criteria andCr_timeBetween(Date value1, Date value2) {
            addCriterion("cr_time between", value1, value2, "cr_time");
            return (Criteria) this;
        }

        public Criteria andCr_timeNotBetween(Date value1, Date value2) {
            addCriterion("cr_time not between", value1, value2, "cr_time");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table TB_INV_SN_COR
     *
     * @mbggenerated do_not_delete_during_merge Fri Nov 28 14:21:44 CST 2014
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table TB_INV_SN_COR
     *
     * @mbggenerated Fri Nov 28 14:21:44 CST 2014
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