package com.foya.dao.mybatis.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TbPayLookupCodeExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table TB_PAY_LOOKUP_CODE
	 * @mbggenerated  Fri Jan 23 11:30:58 CST 2015
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table TB_PAY_LOOKUP_CODE
	 * @mbggenerated  Fri Jan 23 11:30:58 CST 2015
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table TB_PAY_LOOKUP_CODE
	 * @mbggenerated  Fri Jan 23 11:30:58 CST 2015
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_PAY_LOOKUP_CODE
	 * @mbggenerated  Fri Jan 23 11:30:58 CST 2015
	 */
	public TbPayLookupCodeExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_PAY_LOOKUP_CODE
	 * @mbggenerated  Fri Jan 23 11:30:58 CST 2015
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_PAY_LOOKUP_CODE
	 * @mbggenerated  Fri Jan 23 11:30:58 CST 2015
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_PAY_LOOKUP_CODE
	 * @mbggenerated  Fri Jan 23 11:30:58 CST 2015
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_PAY_LOOKUP_CODE
	 * @mbggenerated  Fri Jan 23 11:30:58 CST 2015
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_PAY_LOOKUP_CODE
	 * @mbggenerated  Fri Jan 23 11:30:58 CST 2015
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_PAY_LOOKUP_CODE
	 * @mbggenerated  Fri Jan 23 11:30:58 CST 2015
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_PAY_LOOKUP_CODE
	 * @mbggenerated  Fri Jan 23 11:30:58 CST 2015
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_PAY_LOOKUP_CODE
	 * @mbggenerated  Fri Jan 23 11:30:58 CST 2015
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_PAY_LOOKUP_CODE
	 * @mbggenerated  Fri Jan 23 11:30:58 CST 2015
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_PAY_LOOKUP_CODE
	 * @mbggenerated  Fri Jan 23 11:30:58 CST 2015
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table TB_PAY_LOOKUP_CODE
	 * @mbggenerated  Fri Jan 23 11:30:58 CST 2015
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

		protected void addCriterion(String condition, Object value,
				String property) {
			if (value == null) {
				throw new RuntimeException("Value for " + property
						+ " cannot be null");
			}
			criteria.add(new Criterion(condition, value));
		}

		protected void addCriterion(String condition, Object value1,
				Object value2, String property) {
			if (value1 == null || value2 == null) {
				throw new RuntimeException("Between values for " + property
						+ " cannot be null");
			}
			criteria.add(new Criterion(condition, value1, value2));
		}

		public Criteria andLOOKUP_TYPEIsNull() {
			addCriterion("LOOKUP_TYPE is null");
			return (Criteria) this;
		}

		public Criteria andLOOKUP_TYPEIsNotNull() {
			addCriterion("LOOKUP_TYPE is not null");
			return (Criteria) this;
		}

		public Criteria andLOOKUP_TYPEEqualTo(String value) {
			addCriterion("LOOKUP_TYPE =", value, "LOOKUP_TYPE");
			return (Criteria) this;
		}

		public Criteria andLOOKUP_TYPENotEqualTo(String value) {
			addCriterion("LOOKUP_TYPE <>", value, "LOOKUP_TYPE");
			return (Criteria) this;
		}

		public Criteria andLOOKUP_TYPEGreaterThan(String value) {
			addCriterion("LOOKUP_TYPE >", value, "LOOKUP_TYPE");
			return (Criteria) this;
		}

		public Criteria andLOOKUP_TYPEGreaterThanOrEqualTo(String value) {
			addCriterion("LOOKUP_TYPE >=", value, "LOOKUP_TYPE");
			return (Criteria) this;
		}

		public Criteria andLOOKUP_TYPELessThan(String value) {
			addCriterion("LOOKUP_TYPE <", value, "LOOKUP_TYPE");
			return (Criteria) this;
		}

		public Criteria andLOOKUP_TYPELessThanOrEqualTo(String value) {
			addCriterion("LOOKUP_TYPE <=", value, "LOOKUP_TYPE");
			return (Criteria) this;
		}

		public Criteria andLOOKUP_TYPELike(String value) {
			addCriterion("LOOKUP_TYPE like", value, "LOOKUP_TYPE");
			return (Criteria) this;
		}

		public Criteria andLOOKUP_TYPENotLike(String value) {
			addCriterion("LOOKUP_TYPE not like", value, "LOOKUP_TYPE");
			return (Criteria) this;
		}

		public Criteria andLOOKUP_TYPEIn(List<String> values) {
			addCriterion("LOOKUP_TYPE in", values, "LOOKUP_TYPE");
			return (Criteria) this;
		}

		public Criteria andLOOKUP_TYPENotIn(List<String> values) {
			addCriterion("LOOKUP_TYPE not in", values, "LOOKUP_TYPE");
			return (Criteria) this;
		}

		public Criteria andLOOKUP_TYPEBetween(String value1, String value2) {
			addCriterion("LOOKUP_TYPE between", value1, value2, "LOOKUP_TYPE");
			return (Criteria) this;
		}

		public Criteria andLOOKUP_TYPENotBetween(String value1, String value2) {
			addCriterion("LOOKUP_TYPE not between", value1, value2,
					"LOOKUP_TYPE");
			return (Criteria) this;
		}

		public Criteria andLOOKUP_CODEIsNull() {
			addCriterion("LOOKUP_CODE is null");
			return (Criteria) this;
		}

		public Criteria andLOOKUP_CODEIsNotNull() {
			addCriterion("LOOKUP_CODE is not null");
			return (Criteria) this;
		}

		public Criteria andLOOKUP_CODEEqualTo(String value) {
			addCriterion("LOOKUP_CODE =", value, "LOOKUP_CODE");
			return (Criteria) this;
		}

		public Criteria andLOOKUP_CODENotEqualTo(String value) {
			addCriterion("LOOKUP_CODE <>", value, "LOOKUP_CODE");
			return (Criteria) this;
		}

		public Criteria andLOOKUP_CODEGreaterThan(String value) {
			addCriterion("LOOKUP_CODE >", value, "LOOKUP_CODE");
			return (Criteria) this;
		}

		public Criteria andLOOKUP_CODEGreaterThanOrEqualTo(String value) {
			addCriterion("LOOKUP_CODE >=", value, "LOOKUP_CODE");
			return (Criteria) this;
		}

		public Criteria andLOOKUP_CODELessThan(String value) {
			addCriterion("LOOKUP_CODE <", value, "LOOKUP_CODE");
			return (Criteria) this;
		}

		public Criteria andLOOKUP_CODELessThanOrEqualTo(String value) {
			addCriterion("LOOKUP_CODE <=", value, "LOOKUP_CODE");
			return (Criteria) this;
		}

		public Criteria andLOOKUP_CODELike(String value) {
			addCriterion("LOOKUP_CODE like", value, "LOOKUP_CODE");
			return (Criteria) this;
		}

		public Criteria andLOOKUP_CODENotLike(String value) {
			addCriterion("LOOKUP_CODE not like", value, "LOOKUP_CODE");
			return (Criteria) this;
		}

		public Criteria andLOOKUP_CODEIn(List<String> values) {
			addCriterion("LOOKUP_CODE in", values, "LOOKUP_CODE");
			return (Criteria) this;
		}

		public Criteria andLOOKUP_CODENotIn(List<String> values) {
			addCriterion("LOOKUP_CODE not in", values, "LOOKUP_CODE");
			return (Criteria) this;
		}

		public Criteria andLOOKUP_CODEBetween(String value1, String value2) {
			addCriterion("LOOKUP_CODE between", value1, value2, "LOOKUP_CODE");
			return (Criteria) this;
		}

		public Criteria andLOOKUP_CODENotBetween(String value1, String value2) {
			addCriterion("LOOKUP_CODE not between", value1, value2,
					"LOOKUP_CODE");
			return (Criteria) this;
		}

		public Criteria andLOOKUP_CODE_DESCIsNull() {
			addCriterion("LOOKUP_CODE_DESC is null");
			return (Criteria) this;
		}

		public Criteria andLOOKUP_CODE_DESCIsNotNull() {
			addCriterion("LOOKUP_CODE_DESC is not null");
			return (Criteria) this;
		}

		public Criteria andLOOKUP_CODE_DESCEqualTo(String value) {
			addCriterion("LOOKUP_CODE_DESC =", value, "LOOKUP_CODE_DESC");
			return (Criteria) this;
		}

		public Criteria andLOOKUP_CODE_DESCNotEqualTo(String value) {
			addCriterion("LOOKUP_CODE_DESC <>", value, "LOOKUP_CODE_DESC");
			return (Criteria) this;
		}

		public Criteria andLOOKUP_CODE_DESCGreaterThan(String value) {
			addCriterion("LOOKUP_CODE_DESC >", value, "LOOKUP_CODE_DESC");
			return (Criteria) this;
		}

		public Criteria andLOOKUP_CODE_DESCGreaterThanOrEqualTo(String value) {
			addCriterion("LOOKUP_CODE_DESC >=", value, "LOOKUP_CODE_DESC");
			return (Criteria) this;
		}

		public Criteria andLOOKUP_CODE_DESCLessThan(String value) {
			addCriterion("LOOKUP_CODE_DESC <", value, "LOOKUP_CODE_DESC");
			return (Criteria) this;
		}

		public Criteria andLOOKUP_CODE_DESCLessThanOrEqualTo(String value) {
			addCriterion("LOOKUP_CODE_DESC <=", value, "LOOKUP_CODE_DESC");
			return (Criteria) this;
		}

		public Criteria andLOOKUP_CODE_DESCLike(String value) {
			addCriterion("LOOKUP_CODE_DESC like", value, "LOOKUP_CODE_DESC");
			return (Criteria) this;
		}

		public Criteria andLOOKUP_CODE_DESCNotLike(String value) {
			addCriterion("LOOKUP_CODE_DESC not like", value, "LOOKUP_CODE_DESC");
			return (Criteria) this;
		}

		public Criteria andLOOKUP_CODE_DESCIn(List<String> values) {
			addCriterion("LOOKUP_CODE_DESC in", values, "LOOKUP_CODE_DESC");
			return (Criteria) this;
		}

		public Criteria andLOOKUP_CODE_DESCNotIn(List<String> values) {
			addCriterion("LOOKUP_CODE_DESC not in", values, "LOOKUP_CODE_DESC");
			return (Criteria) this;
		}

		public Criteria andLOOKUP_CODE_DESCBetween(String value1, String value2) {
			addCriterion("LOOKUP_CODE_DESC between", value1, value2,
					"LOOKUP_CODE_DESC");
			return (Criteria) this;
		}

		public Criteria andLOOKUP_CODE_DESCNotBetween(String value1,
				String value2) {
			addCriterion("LOOKUP_CODE_DESC not between", value1, value2,
					"LOOKUP_CODE_DESC");
			return (Criteria) this;
		}

		public Criteria andTXT_VAL_1IsNull() {
			addCriterion("TXT_VAL_1 is null");
			return (Criteria) this;
		}

		public Criteria andTXT_VAL_1IsNotNull() {
			addCriterion("TXT_VAL_1 is not null");
			return (Criteria) this;
		}

		public Criteria andTXT_VAL_1EqualTo(String value) {
			addCriterion("TXT_VAL_1 =", value, "TXT_VAL_1");
			return (Criteria) this;
		}

		public Criteria andTXT_VAL_1NotEqualTo(String value) {
			addCriterion("TXT_VAL_1 <>", value, "TXT_VAL_1");
			return (Criteria) this;
		}

		public Criteria andTXT_VAL_1GreaterThan(String value) {
			addCriterion("TXT_VAL_1 >", value, "TXT_VAL_1");
			return (Criteria) this;
		}

		public Criteria andTXT_VAL_1GreaterThanOrEqualTo(String value) {
			addCriterion("TXT_VAL_1 >=", value, "TXT_VAL_1");
			return (Criteria) this;
		}

		public Criteria andTXT_VAL_1LessThan(String value) {
			addCriterion("TXT_VAL_1 <", value, "TXT_VAL_1");
			return (Criteria) this;
		}

		public Criteria andTXT_VAL_1LessThanOrEqualTo(String value) {
			addCriterion("TXT_VAL_1 <=", value, "TXT_VAL_1");
			return (Criteria) this;
		}

		public Criteria andTXT_VAL_1Like(String value) {
			addCriterion("TXT_VAL_1 like", value, "TXT_VAL_1");
			return (Criteria) this;
		}

		public Criteria andTXT_VAL_1NotLike(String value) {
			addCriterion("TXT_VAL_1 not like", value, "TXT_VAL_1");
			return (Criteria) this;
		}

		public Criteria andTXT_VAL_1In(List<String> values) {
			addCriterion("TXT_VAL_1 in", values, "TXT_VAL_1");
			return (Criteria) this;
		}

		public Criteria andTXT_VAL_1NotIn(List<String> values) {
			addCriterion("TXT_VAL_1 not in", values, "TXT_VAL_1");
			return (Criteria) this;
		}

		public Criteria andTXT_VAL_1Between(String value1, String value2) {
			addCriterion("TXT_VAL_1 between", value1, value2, "TXT_VAL_1");
			return (Criteria) this;
		}

		public Criteria andTXT_VAL_1NotBetween(String value1, String value2) {
			addCriterion("TXT_VAL_1 not between", value1, value2, "TXT_VAL_1");
			return (Criteria) this;
		}

		public Criteria andTXT_VAL_2IsNull() {
			addCriterion("TXT_VAL_2 is null");
			return (Criteria) this;
		}

		public Criteria andTXT_VAL_2IsNotNull() {
			addCriterion("TXT_VAL_2 is not null");
			return (Criteria) this;
		}

		public Criteria andTXT_VAL_2EqualTo(String value) {
			addCriterion("TXT_VAL_2 =", value, "TXT_VAL_2");
			return (Criteria) this;
		}

		public Criteria andTXT_VAL_2NotEqualTo(String value) {
			addCriterion("TXT_VAL_2 <>", value, "TXT_VAL_2");
			return (Criteria) this;
		}

		public Criteria andTXT_VAL_2GreaterThan(String value) {
			addCriterion("TXT_VAL_2 >", value, "TXT_VAL_2");
			return (Criteria) this;
		}

		public Criteria andTXT_VAL_2GreaterThanOrEqualTo(String value) {
			addCriterion("TXT_VAL_2 >=", value, "TXT_VAL_2");
			return (Criteria) this;
		}

		public Criteria andTXT_VAL_2LessThan(String value) {
			addCriterion("TXT_VAL_2 <", value, "TXT_VAL_2");
			return (Criteria) this;
		}

		public Criteria andTXT_VAL_2LessThanOrEqualTo(String value) {
			addCriterion("TXT_VAL_2 <=", value, "TXT_VAL_2");
			return (Criteria) this;
		}

		public Criteria andTXT_VAL_2Like(String value) {
			addCriterion("TXT_VAL_2 like", value, "TXT_VAL_2");
			return (Criteria) this;
		}

		public Criteria andTXT_VAL_2NotLike(String value) {
			addCriterion("TXT_VAL_2 not like", value, "TXT_VAL_2");
			return (Criteria) this;
		}

		public Criteria andTXT_VAL_2In(List<String> values) {
			addCriterion("TXT_VAL_2 in", values, "TXT_VAL_2");
			return (Criteria) this;
		}

		public Criteria andTXT_VAL_2NotIn(List<String> values) {
			addCriterion("TXT_VAL_2 not in", values, "TXT_VAL_2");
			return (Criteria) this;
		}

		public Criteria andTXT_VAL_2Between(String value1, String value2) {
			addCriterion("TXT_VAL_2 between", value1, value2, "TXT_VAL_2");
			return (Criteria) this;
		}

		public Criteria andTXT_VAL_2NotBetween(String value1, String value2) {
			addCriterion("TXT_VAL_2 not between", value1, value2, "TXT_VAL_2");
			return (Criteria) this;
		}

		public Criteria andTXT_VAL_3IsNull() {
			addCriterion("TXT_VAL_3 is null");
			return (Criteria) this;
		}

		public Criteria andTXT_VAL_3IsNotNull() {
			addCriterion("TXT_VAL_3 is not null");
			return (Criteria) this;
		}

		public Criteria andTXT_VAL_3EqualTo(String value) {
			addCriterion("TXT_VAL_3 =", value, "TXT_VAL_3");
			return (Criteria) this;
		}

		public Criteria andTXT_VAL_3NotEqualTo(String value) {
			addCriterion("TXT_VAL_3 <>", value, "TXT_VAL_3");
			return (Criteria) this;
		}

		public Criteria andTXT_VAL_3GreaterThan(String value) {
			addCriterion("TXT_VAL_3 >", value, "TXT_VAL_3");
			return (Criteria) this;
		}

		public Criteria andTXT_VAL_3GreaterThanOrEqualTo(String value) {
			addCriterion("TXT_VAL_3 >=", value, "TXT_VAL_3");
			return (Criteria) this;
		}

		public Criteria andTXT_VAL_3LessThan(String value) {
			addCriterion("TXT_VAL_3 <", value, "TXT_VAL_3");
			return (Criteria) this;
		}

		public Criteria andTXT_VAL_3LessThanOrEqualTo(String value) {
			addCriterion("TXT_VAL_3 <=", value, "TXT_VAL_3");
			return (Criteria) this;
		}

		public Criteria andTXT_VAL_3Like(String value) {
			addCriterion("TXT_VAL_3 like", value, "TXT_VAL_3");
			return (Criteria) this;
		}

		public Criteria andTXT_VAL_3NotLike(String value) {
			addCriterion("TXT_VAL_3 not like", value, "TXT_VAL_3");
			return (Criteria) this;
		}

		public Criteria andTXT_VAL_3In(List<String> values) {
			addCriterion("TXT_VAL_3 in", values, "TXT_VAL_3");
			return (Criteria) this;
		}

		public Criteria andTXT_VAL_3NotIn(List<String> values) {
			addCriterion("TXT_VAL_3 not in", values, "TXT_VAL_3");
			return (Criteria) this;
		}

		public Criteria andTXT_VAL_3Between(String value1, String value2) {
			addCriterion("TXT_VAL_3 between", value1, value2, "TXT_VAL_3");
			return (Criteria) this;
		}

		public Criteria andTXT_VAL_3NotBetween(String value1, String value2) {
			addCriterion("TXT_VAL_3 not between", value1, value2, "TXT_VAL_3");
			return (Criteria) this;
		}

		public Criteria andTXT_VAL_4IsNull() {
			addCriterion("TXT_VAL_4 is null");
			return (Criteria) this;
		}

		public Criteria andTXT_VAL_4IsNotNull() {
			addCriterion("TXT_VAL_4 is not null");
			return (Criteria) this;
		}

		public Criteria andTXT_VAL_4EqualTo(String value) {
			addCriterion("TXT_VAL_4 =", value, "TXT_VAL_4");
			return (Criteria) this;
		}

		public Criteria andTXT_VAL_4NotEqualTo(String value) {
			addCriterion("TXT_VAL_4 <>", value, "TXT_VAL_4");
			return (Criteria) this;
		}

		public Criteria andTXT_VAL_4GreaterThan(String value) {
			addCriterion("TXT_VAL_4 >", value, "TXT_VAL_4");
			return (Criteria) this;
		}

		public Criteria andTXT_VAL_4GreaterThanOrEqualTo(String value) {
			addCriterion("TXT_VAL_4 >=", value, "TXT_VAL_4");
			return (Criteria) this;
		}

		public Criteria andTXT_VAL_4LessThan(String value) {
			addCriterion("TXT_VAL_4 <", value, "TXT_VAL_4");
			return (Criteria) this;
		}

		public Criteria andTXT_VAL_4LessThanOrEqualTo(String value) {
			addCriterion("TXT_VAL_4 <=", value, "TXT_VAL_4");
			return (Criteria) this;
		}

		public Criteria andTXT_VAL_4Like(String value) {
			addCriterion("TXT_VAL_4 like", value, "TXT_VAL_4");
			return (Criteria) this;
		}

		public Criteria andTXT_VAL_4NotLike(String value) {
			addCriterion("TXT_VAL_4 not like", value, "TXT_VAL_4");
			return (Criteria) this;
		}

		public Criteria andTXT_VAL_4In(List<String> values) {
			addCriterion("TXT_VAL_4 in", values, "TXT_VAL_4");
			return (Criteria) this;
		}

		public Criteria andTXT_VAL_4NotIn(List<String> values) {
			addCriterion("TXT_VAL_4 not in", values, "TXT_VAL_4");
			return (Criteria) this;
		}

		public Criteria andTXT_VAL_4Between(String value1, String value2) {
			addCriterion("TXT_VAL_4 between", value1, value2, "TXT_VAL_4");
			return (Criteria) this;
		}

		public Criteria andTXT_VAL_4NotBetween(String value1, String value2) {
			addCriterion("TXT_VAL_4 not between", value1, value2, "TXT_VAL_4");
			return (Criteria) this;
		}

		public Criteria andNUM_VAL_1IsNull() {
			addCriterion("NUM_VAL_1 is null");
			return (Criteria) this;
		}

		public Criteria andNUM_VAL_1IsNotNull() {
			addCriterion("NUM_VAL_1 is not null");
			return (Criteria) this;
		}

		public Criteria andNUM_VAL_1EqualTo(Integer value) {
			addCriterion("NUM_VAL_1 =", value, "NUM_VAL_1");
			return (Criteria) this;
		}

		public Criteria andNUM_VAL_1NotEqualTo(Integer value) {
			addCriterion("NUM_VAL_1 <>", value, "NUM_VAL_1");
			return (Criteria) this;
		}

		public Criteria andNUM_VAL_1GreaterThan(Integer value) {
			addCriterion("NUM_VAL_1 >", value, "NUM_VAL_1");
			return (Criteria) this;
		}

		public Criteria andNUM_VAL_1GreaterThanOrEqualTo(Integer value) {
			addCriterion("NUM_VAL_1 >=", value, "NUM_VAL_1");
			return (Criteria) this;
		}

		public Criteria andNUM_VAL_1LessThan(Integer value) {
			addCriterion("NUM_VAL_1 <", value, "NUM_VAL_1");
			return (Criteria) this;
		}

		public Criteria andNUM_VAL_1LessThanOrEqualTo(Integer value) {
			addCriterion("NUM_VAL_1 <=", value, "NUM_VAL_1");
			return (Criteria) this;
		}

		public Criteria andNUM_VAL_1In(List<Integer> values) {
			addCriterion("NUM_VAL_1 in", values, "NUM_VAL_1");
			return (Criteria) this;
		}

		public Criteria andNUM_VAL_1NotIn(List<Integer> values) {
			addCriterion("NUM_VAL_1 not in", values, "NUM_VAL_1");
			return (Criteria) this;
		}

		public Criteria andNUM_VAL_1Between(Integer value1, Integer value2) {
			addCriterion("NUM_VAL_1 between", value1, value2, "NUM_VAL_1");
			return (Criteria) this;
		}

		public Criteria andNUM_VAL_1NotBetween(Integer value1, Integer value2) {
			addCriterion("NUM_VAL_1 not between", value1, value2, "NUM_VAL_1");
			return (Criteria) this;
		}

		public Criteria andNUM_VAL_2IsNull() {
			addCriterion("NUM_VAL_2 is null");
			return (Criteria) this;
		}

		public Criteria andNUM_VAL_2IsNotNull() {
			addCriterion("NUM_VAL_2 is not null");
			return (Criteria) this;
		}

		public Criteria andNUM_VAL_2EqualTo(Integer value) {
			addCriterion("NUM_VAL_2 =", value, "NUM_VAL_2");
			return (Criteria) this;
		}

		public Criteria andNUM_VAL_2NotEqualTo(Integer value) {
			addCriterion("NUM_VAL_2 <>", value, "NUM_VAL_2");
			return (Criteria) this;
		}

		public Criteria andNUM_VAL_2GreaterThan(Integer value) {
			addCriterion("NUM_VAL_2 >", value, "NUM_VAL_2");
			return (Criteria) this;
		}

		public Criteria andNUM_VAL_2GreaterThanOrEqualTo(Integer value) {
			addCriterion("NUM_VAL_2 >=", value, "NUM_VAL_2");
			return (Criteria) this;
		}

		public Criteria andNUM_VAL_2LessThan(Integer value) {
			addCriterion("NUM_VAL_2 <", value, "NUM_VAL_2");
			return (Criteria) this;
		}

		public Criteria andNUM_VAL_2LessThanOrEqualTo(Integer value) {
			addCriterion("NUM_VAL_2 <=", value, "NUM_VAL_2");
			return (Criteria) this;
		}

		public Criteria andNUM_VAL_2In(List<Integer> values) {
			addCriterion("NUM_VAL_2 in", values, "NUM_VAL_2");
			return (Criteria) this;
		}

		public Criteria andNUM_VAL_2NotIn(List<Integer> values) {
			addCriterion("NUM_VAL_2 not in", values, "NUM_VAL_2");
			return (Criteria) this;
		}

		public Criteria andNUM_VAL_2Between(Integer value1, Integer value2) {
			addCriterion("NUM_VAL_2 between", value1, value2, "NUM_VAL_2");
			return (Criteria) this;
		}

		public Criteria andNUM_VAL_2NotBetween(Integer value1, Integer value2) {
			addCriterion("NUM_VAL_2 not between", value1, value2, "NUM_VAL_2");
			return (Criteria) this;
		}

		public Criteria andDATE_VAL_1IsNull() {
			addCriterion("DATE_VAL_1 is null");
			return (Criteria) this;
		}

		public Criteria andDATE_VAL_1IsNotNull() {
			addCriterion("DATE_VAL_1 is not null");
			return (Criteria) this;
		}

		public Criteria andDATE_VAL_1EqualTo(Date value) {
			addCriterion("DATE_VAL_1 =", value, "DATE_VAL_1");
			return (Criteria) this;
		}

		public Criteria andDATE_VAL_1NotEqualTo(Date value) {
			addCriterion("DATE_VAL_1 <>", value, "DATE_VAL_1");
			return (Criteria) this;
		}

		public Criteria andDATE_VAL_1GreaterThan(Date value) {
			addCriterion("DATE_VAL_1 >", value, "DATE_VAL_1");
			return (Criteria) this;
		}

		public Criteria andDATE_VAL_1GreaterThanOrEqualTo(Date value) {
			addCriterion("DATE_VAL_1 >=", value, "DATE_VAL_1");
			return (Criteria) this;
		}

		public Criteria andDATE_VAL_1LessThan(Date value) {
			addCriterion("DATE_VAL_1 <", value, "DATE_VAL_1");
			return (Criteria) this;
		}

		public Criteria andDATE_VAL_1LessThanOrEqualTo(Date value) {
			addCriterion("DATE_VAL_1 <=", value, "DATE_VAL_1");
			return (Criteria) this;
		}

		public Criteria andDATE_VAL_1In(List<Date> values) {
			addCriterion("DATE_VAL_1 in", values, "DATE_VAL_1");
			return (Criteria) this;
		}

		public Criteria andDATE_VAL_1NotIn(List<Date> values) {
			addCriterion("DATE_VAL_1 not in", values, "DATE_VAL_1");
			return (Criteria) this;
		}

		public Criteria andDATE_VAL_1Between(Date value1, Date value2) {
			addCriterion("DATE_VAL_1 between", value1, value2, "DATE_VAL_1");
			return (Criteria) this;
		}

		public Criteria andDATE_VAL_1NotBetween(Date value1, Date value2) {
			addCriterion("DATE_VAL_1 not between", value1, value2, "DATE_VAL_1");
			return (Criteria) this;
		}

		public Criteria andDATE_VAL_2IsNull() {
			addCriterion("DATE_VAL_2 is null");
			return (Criteria) this;
		}

		public Criteria andDATE_VAL_2IsNotNull() {
			addCriterion("DATE_VAL_2 is not null");
			return (Criteria) this;
		}

		public Criteria andDATE_VAL_2EqualTo(Date value) {
			addCriterion("DATE_VAL_2 =", value, "DATE_VAL_2");
			return (Criteria) this;
		}

		public Criteria andDATE_VAL_2NotEqualTo(Date value) {
			addCriterion("DATE_VAL_2 <>", value, "DATE_VAL_2");
			return (Criteria) this;
		}

		public Criteria andDATE_VAL_2GreaterThan(Date value) {
			addCriterion("DATE_VAL_2 >", value, "DATE_VAL_2");
			return (Criteria) this;
		}

		public Criteria andDATE_VAL_2GreaterThanOrEqualTo(Date value) {
			addCriterion("DATE_VAL_2 >=", value, "DATE_VAL_2");
			return (Criteria) this;
		}

		public Criteria andDATE_VAL_2LessThan(Date value) {
			addCriterion("DATE_VAL_2 <", value, "DATE_VAL_2");
			return (Criteria) this;
		}

		public Criteria andDATE_VAL_2LessThanOrEqualTo(Date value) {
			addCriterion("DATE_VAL_2 <=", value, "DATE_VAL_2");
			return (Criteria) this;
		}

		public Criteria andDATE_VAL_2In(List<Date> values) {
			addCriterion("DATE_VAL_2 in", values, "DATE_VAL_2");
			return (Criteria) this;
		}

		public Criteria andDATE_VAL_2NotIn(List<Date> values) {
			addCriterion("DATE_VAL_2 not in", values, "DATE_VAL_2");
			return (Criteria) this;
		}

		public Criteria andDATE_VAL_2Between(Date value1, Date value2) {
			addCriterion("DATE_VAL_2 between", value1, value2, "DATE_VAL_2");
			return (Criteria) this;
		}

		public Criteria andDATE_VAL_2NotBetween(Date value1, Date value2) {
			addCriterion("DATE_VAL_2 not between", value1, value2, "DATE_VAL_2");
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

		public Criteria andCR_USERIsNull() {
			addCriterion("CR_USER is null");
			return (Criteria) this;
		}

		public Criteria andCR_USERIsNotNull() {
			addCriterion("CR_USER is not null");
			return (Criteria) this;
		}

		public Criteria andCR_USEREqualTo(String value) {
			addCriterion("CR_USER =", value, "CR_USER");
			return (Criteria) this;
		}

		public Criteria andCR_USERNotEqualTo(String value) {
			addCriterion("CR_USER <>", value, "CR_USER");
			return (Criteria) this;
		}

		public Criteria andCR_USERGreaterThan(String value) {
			addCriterion("CR_USER >", value, "CR_USER");
			return (Criteria) this;
		}

		public Criteria andCR_USERGreaterThanOrEqualTo(String value) {
			addCriterion("CR_USER >=", value, "CR_USER");
			return (Criteria) this;
		}

		public Criteria andCR_USERLessThan(String value) {
			addCriterion("CR_USER <", value, "CR_USER");
			return (Criteria) this;
		}

		public Criteria andCR_USERLessThanOrEqualTo(String value) {
			addCriterion("CR_USER <=", value, "CR_USER");
			return (Criteria) this;
		}

		public Criteria andCR_USERLike(String value) {
			addCriterion("CR_USER like", value, "CR_USER");
			return (Criteria) this;
		}

		public Criteria andCR_USERNotLike(String value) {
			addCriterion("CR_USER not like", value, "CR_USER");
			return (Criteria) this;
		}

		public Criteria andCR_USERIn(List<String> values) {
			addCriterion("CR_USER in", values, "CR_USER");
			return (Criteria) this;
		}

		public Criteria andCR_USERNotIn(List<String> values) {
			addCriterion("CR_USER not in", values, "CR_USER");
			return (Criteria) this;
		}

		public Criteria andCR_USERBetween(String value1, String value2) {
			addCriterion("CR_USER between", value1, value2, "CR_USER");
			return (Criteria) this;
		}

		public Criteria andCR_USERNotBetween(String value1, String value2) {
			addCriterion("CR_USER not between", value1, value2, "CR_USER");
			return (Criteria) this;
		}

		public Criteria andCR_TIMEIsNull() {
			addCriterion("CR_TIME is null");
			return (Criteria) this;
		}

		public Criteria andCR_TIMEIsNotNull() {
			addCriterion("CR_TIME is not null");
			return (Criteria) this;
		}

		public Criteria andCR_TIMEEqualTo(Date value) {
			addCriterion("CR_TIME =", value, "CR_TIME");
			return (Criteria) this;
		}

		public Criteria andCR_TIMENotEqualTo(Date value) {
			addCriterion("CR_TIME <>", value, "CR_TIME");
			return (Criteria) this;
		}

		public Criteria andCR_TIMEGreaterThan(Date value) {
			addCriterion("CR_TIME >", value, "CR_TIME");
			return (Criteria) this;
		}

		public Criteria andCR_TIMEGreaterThanOrEqualTo(Date value) {
			addCriterion("CR_TIME >=", value, "CR_TIME");
			return (Criteria) this;
		}

		public Criteria andCR_TIMELessThan(Date value) {
			addCriterion("CR_TIME <", value, "CR_TIME");
			return (Criteria) this;
		}

		public Criteria andCR_TIMELessThanOrEqualTo(Date value) {
			addCriterion("CR_TIME <=", value, "CR_TIME");
			return (Criteria) this;
		}

		public Criteria andCR_TIMEIn(List<Date> values) {
			addCriterion("CR_TIME in", values, "CR_TIME");
			return (Criteria) this;
		}

		public Criteria andCR_TIMENotIn(List<Date> values) {
			addCriterion("CR_TIME not in", values, "CR_TIME");
			return (Criteria) this;
		}

		public Criteria andCR_TIMEBetween(Date value1, Date value2) {
			addCriterion("CR_TIME between", value1, value2, "CR_TIME");
			return (Criteria) this;
		}

		public Criteria andCR_TIMENotBetween(Date value1, Date value2) {
			addCriterion("CR_TIME not between", value1, value2, "CR_TIME");
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

		public Criteria andMD_USERLike(String value) {
			addCriterion("MD_USER like", value, "MD_USER");
			return (Criteria) this;
		}

		public Criteria andMD_USERNotLike(String value) {
			addCriterion("MD_USER not like", value, "MD_USER");
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
	 * This class was generated by MyBatis Generator. This class corresponds to the database table TB_PAY_LOOKUP_CODE
	 * @mbggenerated  Fri Jan 23 11:30:58 CST 2015
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

		protected Criterion(String condition, Object value, Object secondValue,
				String typeHandler) {
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

	/**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table TB_PAY_LOOKUP_CODE
     *
     * @mbggenerated do_not_delete_during_merge Tue Jan 06 11:31:42 CST 2015
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}