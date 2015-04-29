package com.foya.dao.mybatis.model;

import java.util.ArrayList;
import java.util.List;

public class UTbInvCategoryExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table TB_INV_CATEGORY
     *
     * @mbggenerated Mon Nov 03 13:48:37 CST 2014
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table TB_INV_CATEGORY
     *
     * @mbggenerated Mon Nov 03 13:48:37 CST 2014
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table TB_INV_CATEGORY
     *
     * @mbggenerated Mon Nov 03 13:48:37 CST 2014
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_INV_CATEGORY
     *
     * @mbggenerated Mon Nov 03 13:48:37 CST 2014
     */
    public UTbInvCategoryExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_INV_CATEGORY
     *
     * @mbggenerated Mon Nov 03 13:48:37 CST 2014
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_INV_CATEGORY
     *
     * @mbggenerated Mon Nov 03 13:48:37 CST 2014
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_INV_CATEGORY
     *
     * @mbggenerated Mon Nov 03 13:48:37 CST 2014
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_INV_CATEGORY
     *
     * @mbggenerated Mon Nov 03 13:48:37 CST 2014
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_INV_CATEGORY
     *
     * @mbggenerated Mon Nov 03 13:48:37 CST 2014
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_INV_CATEGORY
     *
     * @mbggenerated Mon Nov 03 13:48:37 CST 2014
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_INV_CATEGORY
     *
     * @mbggenerated Mon Nov 03 13:48:37 CST 2014
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_INV_CATEGORY
     *
     * @mbggenerated Mon Nov 03 13:48:37 CST 2014
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
     * This method corresponds to the database table TB_INV_CATEGORY
     *
     * @mbggenerated Mon Nov 03 13:48:37 CST 2014
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_INV_CATEGORY
     *
     * @mbggenerated Mon Nov 03 13:48:37 CST 2014
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table TB_INV_CATEGORY
     *
     * @mbggenerated Mon Nov 03 13:48:37 CST 2014
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

        public Criteria andCatg1_codeIsNull() {
            addCriterion("catg1_code is null");
            return (Criteria) this;
        }

        public Criteria andCatg1_codeIsNotNull() {
            addCriterion("catg1_code is not null");
            return (Criteria) this;
        }

        public Criteria andCatg1_codeEqualTo(String value) {
            addCriterion("catg1_code =", value, "catg1_code");
            return (Criteria) this;
        }

        public Criteria andCatg1_codeNotEqualTo(String value) {
            addCriterion("catg1_code <>", value, "catg1_code");
            return (Criteria) this;
        }

        public Criteria andCatg1_codeGreaterThan(String value) {
            addCriterion("catg1_code >", value, "catg1_code");
            return (Criteria) this;
        }

        public Criteria andCatg1_codeGreaterThanOrEqualTo(String value) {
            addCriterion("catg1_code >=", value, "catg1_code");
            return (Criteria) this;
        }

        public Criteria andCatg1_codeLessThan(String value) {
            addCriterion("catg1_code <", value, "catg1_code");
            return (Criteria) this;
        }

        public Criteria andCatg1_codeLessThanOrEqualTo(String value) {
            addCriterion("catg1_code <=", value, "catg1_code");
            return (Criteria) this;
        }

        public Criteria andCatg1_codeLike(String value) {
            addCriterion("catg1_code like", value, "catg1_code");
            return (Criteria) this;
        }

        public Criteria andCatg1_codeNotLike(String value) {
            addCriterion("catg1_code not like", value, "catg1_code");
            return (Criteria) this;
        }

        public Criteria andCatg1_codeIn(List<String> values) {
            addCriterion("catg1_code in", values, "catg1_code");
            return (Criteria) this;
        }

        public Criteria andCatg1_codeNotIn(List<String> values) {
            addCriterion("catg1_code not in", values, "catg1_code");
            return (Criteria) this;
        }

        public Criteria andCatg1_codeBetween(String value1, String value2) {
            addCriterion("catg1_code between", value1, value2, "catg1_code");
            return (Criteria) this;
        }

        public Criteria andCatg1_codeNotBetween(String value1, String value2) {
            addCriterion("catg1_code not between", value1, value2, "catg1_code");
            return (Criteria) this;
        }

        public Criteria andCatg2_codeIsNull() {
            addCriterion("catg2_code is null");
            return (Criteria) this;
        }

        public Criteria andCatg2_codeIsNotNull() {
            addCriterion("catg2_code is not null");
            return (Criteria) this;
        }

        public Criteria andCatg2_codeEqualTo(String value) {
            addCriterion("catg2_code =", value, "catg2_code");
            return (Criteria) this;
        }

        public Criteria andCatg2_codeNotEqualTo(String value) {
            addCriterion("catg2_code <>", value, "catg2_code");
            return (Criteria) this;
        }

        public Criteria andCatg2_codeGreaterThan(String value) {
            addCriterion("catg2_code >", value, "catg2_code");
            return (Criteria) this;
        }

        public Criteria andCatg2_codeGreaterThanOrEqualTo(String value) {
            addCriterion("catg2_code >=", value, "catg2_code");
            return (Criteria) this;
        }

        public Criteria andCatg2_codeLessThan(String value) {
            addCriterion("catg2_code <", value, "catg2_code");
            return (Criteria) this;
        }

        public Criteria andCatg2_codeLessThanOrEqualTo(String value) {
            addCriterion("catg2_code <=", value, "catg2_code");
            return (Criteria) this;
        }

        public Criteria andCatg2_codeLike(String value) {
            addCriterion("catg2_code like", value, "catg2_code");
            return (Criteria) this;
        }

        public Criteria andCatg2_codeNotLike(String value) {
            addCriterion("catg2_code not like", value, "catg2_code");
            return (Criteria) this;
        }

        public Criteria andCatg2_codeIn(List<String> values) {
            addCriterion("catg2_code in", values, "catg2_code");
            return (Criteria) this;
        }

        public Criteria andCatg2_codeNotIn(List<String> values) {
            addCriterion("catg2_code not in", values, "catg2_code");
            return (Criteria) this;
        }

        public Criteria andCatg2_codeBetween(String value1, String value2) {
            addCriterion("catg2_code between", value1, value2, "catg2_code");
            return (Criteria) this;
        }

        public Criteria andCatg2_codeNotBetween(String value1, String value2) {
            addCriterion("catg2_code not between", value1, value2, "catg2_code");
            return (Criteria) this;
        }

        public Criteria andCatg3_codeIsNull() {
            addCriterion("catg3_code is null");
            return (Criteria) this;
        }

        public Criteria andCatg3_codeIsNotNull() {
            addCriterion("catg3_code is not null");
            return (Criteria) this;
        }

        public Criteria andCatg3_codeEqualTo(String value) {
            addCriterion("catg3_code =", value, "catg3_code");
            return (Criteria) this;
        }

        public Criteria andCatg3_codeNotEqualTo(String value) {
            addCriterion("catg3_code <>", value, "catg3_code");
            return (Criteria) this;
        }

        public Criteria andCatg3_codeGreaterThan(String value) {
            addCriterion("catg3_code >", value, "catg3_code");
            return (Criteria) this;
        }

        public Criteria andCatg3_codeGreaterThanOrEqualTo(String value) {
            addCriterion("catg3_code >=", value, "catg3_code");
            return (Criteria) this;
        }

        public Criteria andCatg3_codeLessThan(String value) {
            addCriterion("catg3_code <", value, "catg3_code");
            return (Criteria) this;
        }

        public Criteria andCatg3_codeLessThanOrEqualTo(String value) {
            addCriterion("catg3_code <=", value, "catg3_code");
            return (Criteria) this;
        }

        public Criteria andCatg3_codeLike(String value) {
            addCriterion("catg3_code like", value, "catg3_code");
            return (Criteria) this;
        }

        public Criteria andCatg3_codeNotLike(String value) {
            addCriterion("catg3_code not like", value, "catg3_code");
            return (Criteria) this;
        }

        public Criteria andCatg3_codeIn(List<String> values) {
            addCriterion("catg3_code in", values, "catg3_code");
            return (Criteria) this;
        }

        public Criteria andCatg3_codeNotIn(List<String> values) {
            addCriterion("catg3_code not in", values, "catg3_code");
            return (Criteria) this;
        }

        public Criteria andCatg3_codeBetween(String value1, String value2) {
            addCriterion("catg3_code between", value1, value2, "catg3_code");
            return (Criteria) this;
        }

        public Criteria andCatg3_codeNotBetween(String value1, String value2) {
            addCriterion("catg3_code not between", value1, value2, "catg3_code");
            return (Criteria) this;
        }

        public Criteria andCatg1_nameIsNull() {
            addCriterion("catg1_name is null");
            return (Criteria) this;
        }

        public Criteria andCatg1_nameIsNotNull() {
            addCriterion("catg1_name is not null");
            return (Criteria) this;
        }

        public Criteria andCatg1_nameEqualTo(String value) {
            addCriterion("catg1_name =", value, "catg1_name");
            return (Criteria) this;
        }

        public Criteria andCatg1_nameNotEqualTo(String value) {
            addCriterion("catg1_name <>", value, "catg1_name");
            return (Criteria) this;
        }

        public Criteria andCatg1_nameGreaterThan(String value) {
            addCriterion("catg1_name >", value, "catg1_name");
            return (Criteria) this;
        }

        public Criteria andCatg1_nameGreaterThanOrEqualTo(String value) {
            addCriterion("catg1_name >=", value, "catg1_name");
            return (Criteria) this;
        }

        public Criteria andCatg1_nameLessThan(String value) {
            addCriterion("catg1_name <", value, "catg1_name");
            return (Criteria) this;
        }

        public Criteria andCatg1_nameLessThanOrEqualTo(String value) {
            addCriterion("catg1_name <=", value, "catg1_name");
            return (Criteria) this;
        }

        public Criteria andCatg1_nameLike(String value) {
            addCriterion("catg1_name like", value, "catg1_name");
            return (Criteria) this;
        }

        public Criteria andCatg1_nameNotLike(String value) {
            addCriterion("catg1_name not like", value, "catg1_name");
            return (Criteria) this;
        }

        public Criteria andCatg1_nameIn(List<String> values) {
            addCriterion("catg1_name in", values, "catg1_name");
            return (Criteria) this;
        }

        public Criteria andCatg1_nameNotIn(List<String> values) {
            addCriterion("catg1_name not in", values, "catg1_name");
            return (Criteria) this;
        }

        public Criteria andCatg1_nameBetween(String value1, String value2) {
            addCriterion("catg1_name between", value1, value2, "catg1_name");
            return (Criteria) this;
        }

        public Criteria andCatg1_nameNotBetween(String value1, String value2) {
            addCriterion("catg1_name not between", value1, value2, "catg1_name");
            return (Criteria) this;
        }

        public Criteria andCatg2_nameIsNull() {
            addCriterion("catg2_name is null");
            return (Criteria) this;
        }

        public Criteria andCatg2_nameIsNotNull() {
            addCriterion("catg2_name is not null");
            return (Criteria) this;
        }

        public Criteria andCatg2_nameEqualTo(String value) {
            addCriterion("catg2_name =", value, "catg2_name");
            return (Criteria) this;
        }

        public Criteria andCatg2_nameNotEqualTo(String value) {
            addCriterion("catg2_name <>", value, "catg2_name");
            return (Criteria) this;
        }

        public Criteria andCatg2_nameGreaterThan(String value) {
            addCriterion("catg2_name >", value, "catg2_name");
            return (Criteria) this;
        }

        public Criteria andCatg2_nameGreaterThanOrEqualTo(String value) {
            addCriterion("catg2_name >=", value, "catg2_name");
            return (Criteria) this;
        }

        public Criteria andCatg2_nameLessThan(String value) {
            addCriterion("catg2_name <", value, "catg2_name");
            return (Criteria) this;
        }

        public Criteria andCatg2_nameLessThanOrEqualTo(String value) {
            addCriterion("catg2_name <=", value, "catg2_name");
            return (Criteria) this;
        }

        public Criteria andCatg2_nameLike(String value) {
            addCriterion("catg2_name like", value, "catg2_name");
            return (Criteria) this;
        }

        public Criteria andCatg2_nameNotLike(String value) {
            addCriterion("catg2_name not like", value, "catg2_name");
            return (Criteria) this;
        }

        public Criteria andCatg2_nameIn(List<String> values) {
            addCriterion("catg2_name in", values, "catg2_name");
            return (Criteria) this;
        }

        public Criteria andCatg2_nameNotIn(List<String> values) {
            addCriterion("catg2_name not in", values, "catg2_name");
            return (Criteria) this;
        }

        public Criteria andCatg2_nameBetween(String value1, String value2) {
            addCriterion("catg2_name between", value1, value2, "catg2_name");
            return (Criteria) this;
        }

        public Criteria andCatg2_nameNotBetween(String value1, String value2) {
            addCriterion("catg2_name not between", value1, value2, "catg2_name");
            return (Criteria) this;
        }

        public Criteria andCatg3_nameIsNull() {
            addCriterion("catg3_name is null");
            return (Criteria) this;
        }

        public Criteria andCatg3_nameIsNotNull() {
            addCriterion("catg3_name is not null");
            return (Criteria) this;
        }

        public Criteria andCatg3_nameEqualTo(String value) {
            addCriterion("catg3_name =", value, "catg3_name");
            return (Criteria) this;
        }

        public Criteria andCatg3_nameNotEqualTo(String value) {
            addCriterion("catg3_name <>", value, "catg3_name");
            return (Criteria) this;
        }

        public Criteria andCatg3_nameGreaterThan(String value) {
            addCriterion("catg3_name >", value, "catg3_name");
            return (Criteria) this;
        }

        public Criteria andCatg3_nameGreaterThanOrEqualTo(String value) {
            addCriterion("catg3_name >=", value, "catg3_name");
            return (Criteria) this;
        }

        public Criteria andCatg3_nameLessThan(String value) {
            addCriterion("catg3_name <", value, "catg3_name");
            return (Criteria) this;
        }

        public Criteria andCatg3_nameLessThanOrEqualTo(String value) {
            addCriterion("catg3_name <=", value, "catg3_name");
            return (Criteria) this;
        }

        public Criteria andCatg3_nameLike(String value) {
            addCriterion("catg3_name like", value, "catg3_name");
            return (Criteria) this;
        }

        public Criteria andCatg3_nameNotLike(String value) {
            addCriterion("catg3_name not like", value, "catg3_name");
            return (Criteria) this;
        }

        public Criteria andCatg3_nameIn(List<String> values) {
            addCriterion("catg3_name in", values, "catg3_name");
            return (Criteria) this;
        }

        public Criteria andCatg3_nameNotIn(List<String> values) {
            addCriterion("catg3_name not in", values, "catg3_name");
            return (Criteria) this;
        }

        public Criteria andCatg3_nameBetween(String value1, String value2) {
            addCriterion("catg3_name between", value1, value2, "catg3_name");
            return (Criteria) this;
        }

        public Criteria andCatg3_nameNotBetween(String value1, String value2) {
            addCriterion("catg3_name not between", value1, value2, "catg3_name");
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

        public Criteria andCr_timeEqualTo(String value) {
            addCriterion("cr_time =", value, "cr_time");
            return (Criteria) this;
        }

        public Criteria andCr_timeNotEqualTo(String value) {
            addCriterion("cr_time <>", value, "cr_time");
            return (Criteria) this;
        }

        public Criteria andCr_timeGreaterThan(String value) {
            addCriterion("cr_time >", value, "cr_time");
            return (Criteria) this;
        }

        public Criteria andCr_timeGreaterThanOrEqualTo(String value) {
            addCriterion("cr_time >=", value, "cr_time");
            return (Criteria) this;
        }

        public Criteria andCr_timeLessThan(String value) {
            addCriterion("cr_time <", value, "cr_time");
            return (Criteria) this;
        }

        public Criteria andCr_timeLessThanOrEqualTo(String value) {
            addCriterion("cr_time <=", value, "cr_time");
            return (Criteria) this;
        }

        public Criteria andCr_timeLike(String value) {
            addCriterion("cr_time like", value, "cr_time");
            return (Criteria) this;
        }

        public Criteria andCr_timeNotLike(String value) {
            addCriterion("cr_time not like", value, "cr_time");
            return (Criteria) this;
        }

        public Criteria andCr_timeIn(List<String> values) {
            addCriterion("cr_time in", values, "cr_time");
            return (Criteria) this;
        }

        public Criteria andCr_timeNotIn(List<String> values) {
            addCriterion("cr_time not in", values, "cr_time");
            return (Criteria) this;
        }

        public Criteria andCr_timeBetween(String value1, String value2) {
            addCriterion("cr_time between", value1, value2, "cr_time");
            return (Criteria) this;
        }

        public Criteria andCr_timeNotBetween(String value1, String value2) {
            addCriterion("cr_time not between", value1, value2, "cr_time");
            return (Criteria) this;
        }

        public Criteria andMd_userIsNull() {
            addCriterion("md_user is null");
            return (Criteria) this;
        }

        public Criteria andMd_userIsNotNull() {
            addCriterion("md_user is not null");
            return (Criteria) this;
        }

        public Criteria andMd_userEqualTo(String value) {
            addCriterion("md_user =", value, "md_user");
            return (Criteria) this;
        }

        public Criteria andMd_userNotEqualTo(String value) {
            addCriterion("md_user <>", value, "md_user");
            return (Criteria) this;
        }

        public Criteria andMd_userGreaterThan(String value) {
            addCriterion("md_user >", value, "md_user");
            return (Criteria) this;
        }

        public Criteria andMd_userGreaterThanOrEqualTo(String value) {
            addCriterion("md_user >=", value, "md_user");
            return (Criteria) this;
        }

        public Criteria andMd_userLessThan(String value) {
            addCriterion("md_user <", value, "md_user");
            return (Criteria) this;
        }

        public Criteria andMd_userLessThanOrEqualTo(String value) {
            addCriterion("md_user <=", value, "md_user");
            return (Criteria) this;
        }

        public Criteria andMd_userLike(String value) {
            addCriterion("md_user like", value, "md_user");
            return (Criteria) this;
        }

        public Criteria andMd_userNotLike(String value) {
            addCriterion("md_user not like", value, "md_user");
            return (Criteria) this;
        }

        public Criteria andMd_userIn(List<String> values) {
            addCriterion("md_user in", values, "md_user");
            return (Criteria) this;
        }

        public Criteria andMd_userNotIn(List<String> values) {
            addCriterion("md_user not in", values, "md_user");
            return (Criteria) this;
        }

        public Criteria andMd_userBetween(String value1, String value2) {
            addCriterion("md_user between", value1, value2, "md_user");
            return (Criteria) this;
        }

        public Criteria andMd_userNotBetween(String value1, String value2) {
            addCriterion("md_user not between", value1, value2, "md_user");
            return (Criteria) this;
        }

        public Criteria andMd_timeIsNull() {
            addCriterion("md_time is null");
            return (Criteria) this;
        }

        public Criteria andMd_timeIsNotNull() {
            addCriterion("md_time is not null");
            return (Criteria) this;
        }

        public Criteria andMd_timeEqualTo(String value) {
            addCriterion("md_time =", value, "md_time");
            return (Criteria) this;
        }

        public Criteria andMd_timeNotEqualTo(String value) {
            addCriterion("md_time <>", value, "md_time");
            return (Criteria) this;
        }

        public Criteria andMd_timeGreaterThan(String value) {
            addCriterion("md_time >", value, "md_time");
            return (Criteria) this;
        }

        public Criteria andMd_timeGreaterThanOrEqualTo(String value) {
            addCriterion("md_time >=", value, "md_time");
            return (Criteria) this;
        }

        public Criteria andMd_timeLessThan(String value) {
            addCriterion("md_time <", value, "md_time");
            return (Criteria) this;
        }

        public Criteria andMd_timeLessThanOrEqualTo(String value) {
            addCriterion("md_time <=", value, "md_time");
            return (Criteria) this;
        }

        public Criteria andMd_timeLike(String value) {
            addCriterion("md_time like", value, "md_time");
            return (Criteria) this;
        }

        public Criteria andMd_timeNotLike(String value) {
            addCriterion("md_time not like", value, "md_time");
            return (Criteria) this;
        }

        public Criteria andMd_timeIn(List<String> values) {
            addCriterion("md_time in", values, "md_time");
            return (Criteria) this;
        }

        public Criteria andMd_timeNotIn(List<String> values) {
            addCriterion("md_time not in", values, "md_time");
            return (Criteria) this;
        }

        public Criteria andMd_timeBetween(String value1, String value2) {
            addCriterion("md_time between", value1, value2, "md_time");
            return (Criteria) this;
        }

        public Criteria andMd_timeNotBetween(String value1, String value2) {
            addCriterion("md_time not between", value1, value2, "md_time");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table TB_INV_CATEGORY
     *
     * @mbggenerated do_not_delete_during_merge Mon Nov 03 13:48:37 CST 2014
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table TB_INV_CATEGORY
     *
     * @mbggenerated Mon Nov 03 13:48:37 CST 2014
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