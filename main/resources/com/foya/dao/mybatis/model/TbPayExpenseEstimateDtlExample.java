package com.foya.dao.mybatis.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TbPayExpenseEstimateDtlExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table TB_PAY_EXPENSE_ESTIMATE_DTL
	 * @mbggenerated  Fri Jan 23 10:24:06 CST 2015
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table TB_PAY_EXPENSE_ESTIMATE_DTL
	 * @mbggenerated  Fri Jan 23 10:24:06 CST 2015
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table TB_PAY_EXPENSE_ESTIMATE_DTL
	 * @mbggenerated  Fri Jan 23 10:24:06 CST 2015
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_PAY_EXPENSE_ESTIMATE_DTL
	 * @mbggenerated  Fri Jan 23 10:24:06 CST 2015
	 */
	public TbPayExpenseEstimateDtlExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_PAY_EXPENSE_ESTIMATE_DTL
	 * @mbggenerated  Fri Jan 23 10:24:06 CST 2015
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_PAY_EXPENSE_ESTIMATE_DTL
	 * @mbggenerated  Fri Jan 23 10:24:06 CST 2015
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_PAY_EXPENSE_ESTIMATE_DTL
	 * @mbggenerated  Fri Jan 23 10:24:06 CST 2015
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_PAY_EXPENSE_ESTIMATE_DTL
	 * @mbggenerated  Fri Jan 23 10:24:06 CST 2015
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_PAY_EXPENSE_ESTIMATE_DTL
	 * @mbggenerated  Fri Jan 23 10:24:06 CST 2015
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_PAY_EXPENSE_ESTIMATE_DTL
	 * @mbggenerated  Fri Jan 23 10:24:06 CST 2015
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_PAY_EXPENSE_ESTIMATE_DTL
	 * @mbggenerated  Fri Jan 23 10:24:06 CST 2015
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_PAY_EXPENSE_ESTIMATE_DTL
	 * @mbggenerated  Fri Jan 23 10:24:06 CST 2015
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_PAY_EXPENSE_ESTIMATE_DTL
	 * @mbggenerated  Fri Jan 23 10:24:06 CST 2015
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_PAY_EXPENSE_ESTIMATE_DTL
	 * @mbggenerated  Fri Jan 23 10:24:06 CST 2015
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table TB_PAY_EXPENSE_ESTIMATE_DTL
	 * @mbggenerated  Fri Jan 23 10:24:06 CST 2015
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

		public Criteria andESTIMATE_NOIsNull() {
			addCriterion("ESTIMATE_NO is null");
			return (Criteria) this;
		}

		public Criteria andESTIMATE_NOIsNotNull() {
			addCriterion("ESTIMATE_NO is not null");
			return (Criteria) this;
		}

		public Criteria andESTIMATE_NOEqualTo(String value) {
			addCriterion("ESTIMATE_NO =", value, "ESTIMATE_NO");
			return (Criteria) this;
		}

		public Criteria andESTIMATE_NONotEqualTo(String value) {
			addCriterion("ESTIMATE_NO <>", value, "ESTIMATE_NO");
			return (Criteria) this;
		}

		public Criteria andESTIMATE_NOGreaterThan(String value) {
			addCriterion("ESTIMATE_NO >", value, "ESTIMATE_NO");
			return (Criteria) this;
		}

		public Criteria andESTIMATE_NOGreaterThanOrEqualTo(String value) {
			addCriterion("ESTIMATE_NO >=", value, "ESTIMATE_NO");
			return (Criteria) this;
		}

		public Criteria andESTIMATE_NOLessThan(String value) {
			addCriterion("ESTIMATE_NO <", value, "ESTIMATE_NO");
			return (Criteria) this;
		}

		public Criteria andESTIMATE_NOLessThanOrEqualTo(String value) {
			addCriterion("ESTIMATE_NO <=", value, "ESTIMATE_NO");
			return (Criteria) this;
		}

		public Criteria andESTIMATE_NOLike(String value) {
			addCriterion("ESTIMATE_NO like", value, "ESTIMATE_NO");
			return (Criteria) this;
		}

		public Criteria andESTIMATE_NONotLike(String value) {
			addCriterion("ESTIMATE_NO not like", value, "ESTIMATE_NO");
			return (Criteria) this;
		}

		public Criteria andESTIMATE_NOIn(List<String> values) {
			addCriterion("ESTIMATE_NO in", values, "ESTIMATE_NO");
			return (Criteria) this;
		}

		public Criteria andESTIMATE_NONotIn(List<String> values) {
			addCriterion("ESTIMATE_NO not in", values, "ESTIMATE_NO");
			return (Criteria) this;
		}

		public Criteria andESTIMATE_NOBetween(String value1, String value2) {
			addCriterion("ESTIMATE_NO between", value1, value2, "ESTIMATE_NO");
			return (Criteria) this;
		}

		public Criteria andESTIMATE_NONotBetween(String value1, String value2) {
			addCriterion("ESTIMATE_NO not between", value1, value2,
					"ESTIMATE_NO");
			return (Criteria) this;
		}

		public Criteria andCONTRACT_NOIsNull() {
			addCriterion("CONTRACT_NO is null");
			return (Criteria) this;
		}

		public Criteria andCONTRACT_NOIsNotNull() {
			addCriterion("CONTRACT_NO is not null");
			return (Criteria) this;
		}

		public Criteria andCONTRACT_NOEqualTo(String value) {
			addCriterion("CONTRACT_NO =", value, "CONTRACT_NO");
			return (Criteria) this;
		}

		public Criteria andCONTRACT_NONotEqualTo(String value) {
			addCriterion("CONTRACT_NO <>", value, "CONTRACT_NO");
			return (Criteria) this;
		}

		public Criteria andCONTRACT_NOGreaterThan(String value) {
			addCriterion("CONTRACT_NO >", value, "CONTRACT_NO");
			return (Criteria) this;
		}

		public Criteria andCONTRACT_NOGreaterThanOrEqualTo(String value) {
			addCriterion("CONTRACT_NO >=", value, "CONTRACT_NO");
			return (Criteria) this;
		}

		public Criteria andCONTRACT_NOLessThan(String value) {
			addCriterion("CONTRACT_NO <", value, "CONTRACT_NO");
			return (Criteria) this;
		}

		public Criteria andCONTRACT_NOLessThanOrEqualTo(String value) {
			addCriterion("CONTRACT_NO <=", value, "CONTRACT_NO");
			return (Criteria) this;
		}

		public Criteria andCONTRACT_NOLike(String value) {
			addCriterion("CONTRACT_NO like", value, "CONTRACT_NO");
			return (Criteria) this;
		}

		public Criteria andCONTRACT_NONotLike(String value) {
			addCriterion("CONTRACT_NO not like", value, "CONTRACT_NO");
			return (Criteria) this;
		}

		public Criteria andCONTRACT_NOIn(List<String> values) {
			addCriterion("CONTRACT_NO in", values, "CONTRACT_NO");
			return (Criteria) this;
		}

		public Criteria andCONTRACT_NONotIn(List<String> values) {
			addCriterion("CONTRACT_NO not in", values, "CONTRACT_NO");
			return (Criteria) this;
		}

		public Criteria andCONTRACT_NOBetween(String value1, String value2) {
			addCriterion("CONTRACT_NO between", value1, value2, "CONTRACT_NO");
			return (Criteria) this;
		}

		public Criteria andCONTRACT_NONotBetween(String value1, String value2) {
			addCriterion("CONTRACT_NO not between", value1, value2,
					"CONTRACT_NO");
			return (Criteria) this;
		}

		public Criteria andSITE_IDIsNull() {
			addCriterion("SITE_ID is null");
			return (Criteria) this;
		}

		public Criteria andSITE_IDIsNotNull() {
			addCriterion("SITE_ID is not null");
			return (Criteria) this;
		}

		public Criteria andSITE_IDEqualTo(String value) {
			addCriterion("SITE_ID =", value, "SITE_ID");
			return (Criteria) this;
		}

		public Criteria andSITE_IDNotEqualTo(String value) {
			addCriterion("SITE_ID <>", value, "SITE_ID");
			return (Criteria) this;
		}

		public Criteria andSITE_IDGreaterThan(String value) {
			addCriterion("SITE_ID >", value, "SITE_ID");
			return (Criteria) this;
		}

		public Criteria andSITE_IDGreaterThanOrEqualTo(String value) {
			addCriterion("SITE_ID >=", value, "SITE_ID");
			return (Criteria) this;
		}

		public Criteria andSITE_IDLessThan(String value) {
			addCriterion("SITE_ID <", value, "SITE_ID");
			return (Criteria) this;
		}

		public Criteria andSITE_IDLessThanOrEqualTo(String value) {
			addCriterion("SITE_ID <=", value, "SITE_ID");
			return (Criteria) this;
		}

		public Criteria andSITE_IDLike(String value) {
			addCriterion("SITE_ID like", value, "SITE_ID");
			return (Criteria) this;
		}

		public Criteria andSITE_IDNotLike(String value) {
			addCriterion("SITE_ID not like", value, "SITE_ID");
			return (Criteria) this;
		}

		public Criteria andSITE_IDIn(List<String> values) {
			addCriterion("SITE_ID in", values, "SITE_ID");
			return (Criteria) this;
		}

		public Criteria andSITE_IDNotIn(List<String> values) {
			addCriterion("SITE_ID not in", values, "SITE_ID");
			return (Criteria) this;
		}

		public Criteria andSITE_IDBetween(String value1, String value2) {
			addCriterion("SITE_ID between", value1, value2, "SITE_ID");
			return (Criteria) this;
		}

		public Criteria andSITE_IDNotBetween(String value1, String value2) {
			addCriterion("SITE_ID not between", value1, value2, "SITE_ID");
			return (Criteria) this;
		}

		public Criteria andESTIMATE_AMTIsNull() {
			addCriterion("ESTIMATE_AMT is null");
			return (Criteria) this;
		}

		public Criteria andESTIMATE_AMTIsNotNull() {
			addCriterion("ESTIMATE_AMT is not null");
			return (Criteria) this;
		}

		public Criteria andESTIMATE_AMTEqualTo(BigDecimal value) {
			addCriterion("ESTIMATE_AMT =", value, "ESTIMATE_AMT");
			return (Criteria) this;
		}

		public Criteria andESTIMATE_AMTNotEqualTo(BigDecimal value) {
			addCriterion("ESTIMATE_AMT <>", value, "ESTIMATE_AMT");
			return (Criteria) this;
		}

		public Criteria andESTIMATE_AMTGreaterThan(BigDecimal value) {
			addCriterion("ESTIMATE_AMT >", value, "ESTIMATE_AMT");
			return (Criteria) this;
		}

		public Criteria andESTIMATE_AMTGreaterThanOrEqualTo(BigDecimal value) {
			addCriterion("ESTIMATE_AMT >=", value, "ESTIMATE_AMT");
			return (Criteria) this;
		}

		public Criteria andESTIMATE_AMTLessThan(BigDecimal value) {
			addCriterion("ESTIMATE_AMT <", value, "ESTIMATE_AMT");
			return (Criteria) this;
		}

		public Criteria andESTIMATE_AMTLessThanOrEqualTo(BigDecimal value) {
			addCriterion("ESTIMATE_AMT <=", value, "ESTIMATE_AMT");
			return (Criteria) this;
		}

		public Criteria andESTIMATE_AMTIn(List<BigDecimal> values) {
			addCriterion("ESTIMATE_AMT in", values, "ESTIMATE_AMT");
			return (Criteria) this;
		}

		public Criteria andESTIMATE_AMTNotIn(List<BigDecimal> values) {
			addCriterion("ESTIMATE_AMT not in", values, "ESTIMATE_AMT");
			return (Criteria) this;
		}

		public Criteria andESTIMATE_AMTBetween(BigDecimal value1,
				BigDecimal value2) {
			addCriterion("ESTIMATE_AMT between", value1, value2, "ESTIMATE_AMT");
			return (Criteria) this;
		}

		public Criteria andESTIMATE_AMTNotBetween(BigDecimal value1,
				BigDecimal value2) {
			addCriterion("ESTIMATE_AMT not between", value1, value2,
					"ESTIMATE_AMT");
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
	 * This class was generated by MyBatis Generator. This class corresponds to the database table TB_PAY_EXPENSE_ESTIMATE_DTL
	 * @mbggenerated  Fri Jan 23 10:24:06 CST 2015
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
     * This class corresponds to the database table TB_PAY_EXPENSE_ESTIMATE_DTL
     *
     * @mbggenerated do_not_delete_during_merge Mon Jan 12 10:39:42 CST 2015
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}