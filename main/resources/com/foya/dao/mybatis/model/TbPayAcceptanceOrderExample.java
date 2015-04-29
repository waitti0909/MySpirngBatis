package com.foya.dao.mybatis.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TbPayAcceptanceOrderExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table TB_PAY_ACCEPTANCE_ORDER
	 * @mbggenerated  Wed Apr 15 14:27:46 CST 2015
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table TB_PAY_ACCEPTANCE_ORDER
	 * @mbggenerated  Wed Apr 15 14:27:46 CST 2015
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table TB_PAY_ACCEPTANCE_ORDER
	 * @mbggenerated  Wed Apr 15 14:27:46 CST 2015
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_PAY_ACCEPTANCE_ORDER
	 * @mbggenerated  Wed Apr 15 14:27:46 CST 2015
	 */
	public TbPayAcceptanceOrderExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_PAY_ACCEPTANCE_ORDER
	 * @mbggenerated  Wed Apr 15 14:27:46 CST 2015
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_PAY_ACCEPTANCE_ORDER
	 * @mbggenerated  Wed Apr 15 14:27:46 CST 2015
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_PAY_ACCEPTANCE_ORDER
	 * @mbggenerated  Wed Apr 15 14:27:46 CST 2015
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_PAY_ACCEPTANCE_ORDER
	 * @mbggenerated  Wed Apr 15 14:27:46 CST 2015
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_PAY_ACCEPTANCE_ORDER
	 * @mbggenerated  Wed Apr 15 14:27:46 CST 2015
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_PAY_ACCEPTANCE_ORDER
	 * @mbggenerated  Wed Apr 15 14:27:46 CST 2015
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_PAY_ACCEPTANCE_ORDER
	 * @mbggenerated  Wed Apr 15 14:27:46 CST 2015
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_PAY_ACCEPTANCE_ORDER
	 * @mbggenerated  Wed Apr 15 14:27:46 CST 2015
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_PAY_ACCEPTANCE_ORDER
	 * @mbggenerated  Wed Apr 15 14:27:46 CST 2015
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_PAY_ACCEPTANCE_ORDER
	 * @mbggenerated  Wed Apr 15 14:27:46 CST 2015
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table TB_PAY_ACCEPTANCE_ORDER
	 * @mbggenerated  Wed Apr 15 14:27:46 CST 2015
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

		public Criteria andMST_AP_NOIsNull() {
			addCriterion("MST_AP_NO is null");
			return (Criteria) this;
		}

		public Criteria andMST_AP_NOIsNotNull() {
			addCriterion("MST_AP_NO is not null");
			return (Criteria) this;
		}

		public Criteria andMST_AP_NOEqualTo(String value) {
			addCriterion("MST_AP_NO =", value, "MST_AP_NO");
			return (Criteria) this;
		}

		public Criteria andMST_AP_NONotEqualTo(String value) {
			addCriterion("MST_AP_NO <>", value, "MST_AP_NO");
			return (Criteria) this;
		}

		public Criteria andMST_AP_NOGreaterThan(String value) {
			addCriterion("MST_AP_NO >", value, "MST_AP_NO");
			return (Criteria) this;
		}

		public Criteria andMST_AP_NOGreaterThanOrEqualTo(String value) {
			addCriterion("MST_AP_NO >=", value, "MST_AP_NO");
			return (Criteria) this;
		}

		public Criteria andMST_AP_NOLessThan(String value) {
			addCriterion("MST_AP_NO <", value, "MST_AP_NO");
			return (Criteria) this;
		}

		public Criteria andMST_AP_NOLessThanOrEqualTo(String value) {
			addCriterion("MST_AP_NO <=", value, "MST_AP_NO");
			return (Criteria) this;
		}

		public Criteria andMST_AP_NOLike(String value) {
			addCriterion("MST_AP_NO like", value, "MST_AP_NO");
			return (Criteria) this;
		}

		public Criteria andMST_AP_NONotLike(String value) {
			addCriterion("MST_AP_NO not like", value, "MST_AP_NO");
			return (Criteria) this;
		}

		public Criteria andMST_AP_NOIn(List<String> values) {
			addCriterion("MST_AP_NO in", values, "MST_AP_NO");
			return (Criteria) this;
		}

		public Criteria andMST_AP_NONotIn(List<String> values) {
			addCriterion("MST_AP_NO not in", values, "MST_AP_NO");
			return (Criteria) this;
		}

		public Criteria andMST_AP_NOBetween(String value1, String value2) {
			addCriterion("MST_AP_NO between", value1, value2, "MST_AP_NO");
			return (Criteria) this;
		}

		public Criteria andMST_AP_NONotBetween(String value1, String value2) {
			addCriterion("MST_AP_NO not between", value1, value2, "MST_AP_NO");
			return (Criteria) this;
		}

		public Criteria andPO_NOIsNull() {
			addCriterion("PO_NO is null");
			return (Criteria) this;
		}

		public Criteria andPO_NOIsNotNull() {
			addCriterion("PO_NO is not null");
			return (Criteria) this;
		}

		public Criteria andPO_NOEqualTo(String value) {
			addCriterion("PO_NO =", value, "PO_NO");
			return (Criteria) this;
		}

		public Criteria andPO_NONotEqualTo(String value) {
			addCriterion("PO_NO <>", value, "PO_NO");
			return (Criteria) this;
		}

		public Criteria andPO_NOGreaterThan(String value) {
			addCriterion("PO_NO >", value, "PO_NO");
			return (Criteria) this;
		}

		public Criteria andPO_NOGreaterThanOrEqualTo(String value) {
			addCriterion("PO_NO >=", value, "PO_NO");
			return (Criteria) this;
		}

		public Criteria andPO_NOLessThan(String value) {
			addCriterion("PO_NO <", value, "PO_NO");
			return (Criteria) this;
		}

		public Criteria andPO_NOLessThanOrEqualTo(String value) {
			addCriterion("PO_NO <=", value, "PO_NO");
			return (Criteria) this;
		}

		public Criteria andPO_NOLike(String value) {
			addCriterion("PO_NO like", value, "PO_NO");
			return (Criteria) this;
		}

		public Criteria andPO_NONotLike(String value) {
			addCriterion("PO_NO not like", value, "PO_NO");
			return (Criteria) this;
		}

		public Criteria andPO_NOIn(List<String> values) {
			addCriterion("PO_NO in", values, "PO_NO");
			return (Criteria) this;
		}

		public Criteria andPO_NONotIn(List<String> values) {
			addCriterion("PO_NO not in", values, "PO_NO");
			return (Criteria) this;
		}

		public Criteria andPO_NOBetween(String value1, String value2) {
			addCriterion("PO_NO between", value1, value2, "PO_NO");
			return (Criteria) this;
		}

		public Criteria andPO_NONotBetween(String value1, String value2) {
			addCriterion("PO_NO not between", value1, value2, "PO_NO");
			return (Criteria) this;
		}

		public Criteria andORDER_NOIsNull() {
			addCriterion("ORDER_NO is null");
			return (Criteria) this;
		}

		public Criteria andORDER_NOIsNotNull() {
			addCriterion("ORDER_NO is not null");
			return (Criteria) this;
		}

		public Criteria andORDER_NOEqualTo(String value) {
			addCriterion("ORDER_NO =", value, "ORDER_NO");
			return (Criteria) this;
		}

		public Criteria andORDER_NONotEqualTo(String value) {
			addCriterion("ORDER_NO <>", value, "ORDER_NO");
			return (Criteria) this;
		}

		public Criteria andORDER_NOGreaterThan(String value) {
			addCriterion("ORDER_NO >", value, "ORDER_NO");
			return (Criteria) this;
		}

		public Criteria andORDER_NOGreaterThanOrEqualTo(String value) {
			addCriterion("ORDER_NO >=", value, "ORDER_NO");
			return (Criteria) this;
		}

		public Criteria andORDER_NOLessThan(String value) {
			addCriterion("ORDER_NO <", value, "ORDER_NO");
			return (Criteria) this;
		}

		public Criteria andORDER_NOLessThanOrEqualTo(String value) {
			addCriterion("ORDER_NO <=", value, "ORDER_NO");
			return (Criteria) this;
		}

		public Criteria andORDER_NOLike(String value) {
			addCriterion("ORDER_NO like", value, "ORDER_NO");
			return (Criteria) this;
		}

		public Criteria andORDER_NONotLike(String value) {
			addCriterion("ORDER_NO not like", value, "ORDER_NO");
			return (Criteria) this;
		}

		public Criteria andORDER_NOIn(List<String> values) {
			addCriterion("ORDER_NO in", values, "ORDER_NO");
			return (Criteria) this;
		}

		public Criteria andORDER_NONotIn(List<String> values) {
			addCriterion("ORDER_NO not in", values, "ORDER_NO");
			return (Criteria) this;
		}

		public Criteria andORDER_NOBetween(String value1, String value2) {
			addCriterion("ORDER_NO between", value1, value2, "ORDER_NO");
			return (Criteria) this;
		}

		public Criteria andORDER_NONotBetween(String value1, String value2) {
			addCriterion("ORDER_NO not between", value1, value2, "ORDER_NO");
			return (Criteria) this;
		}

		public Criteria andOS_IDIsNull() {
			addCriterion("OS_ID is null");
			return (Criteria) this;
		}

		public Criteria andOS_IDIsNotNull() {
			addCriterion("OS_ID is not null");
			return (Criteria) this;
		}

		public Criteria andOS_IDEqualTo(String value) {
			addCriterion("OS_ID =", value, "OS_ID");
			return (Criteria) this;
		}

		public Criteria andOS_IDNotEqualTo(String value) {
			addCriterion("OS_ID <>", value, "OS_ID");
			return (Criteria) this;
		}

		public Criteria andOS_IDGreaterThan(String value) {
			addCriterion("OS_ID >", value, "OS_ID");
			return (Criteria) this;
		}

		public Criteria andOS_IDGreaterThanOrEqualTo(String value) {
			addCriterion("OS_ID >=", value, "OS_ID");
			return (Criteria) this;
		}

		public Criteria andOS_IDLessThan(String value) {
			addCriterion("OS_ID <", value, "OS_ID");
			return (Criteria) this;
		}

		public Criteria andOS_IDLessThanOrEqualTo(String value) {
			addCriterion("OS_ID <=", value, "OS_ID");
			return (Criteria) this;
		}

		public Criteria andOS_IDLike(String value) {
			addCriterion("OS_ID like", value, "OS_ID");
			return (Criteria) this;
		}

		public Criteria andOS_IDNotLike(String value) {
			addCriterion("OS_ID not like", value, "OS_ID");
			return (Criteria) this;
		}

		public Criteria andOS_IDIn(List<String> values) {
			addCriterion("OS_ID in", values, "OS_ID");
			return (Criteria) this;
		}

		public Criteria andOS_IDNotIn(List<String> values) {
			addCriterion("OS_ID not in", values, "OS_ID");
			return (Criteria) this;
		}

		public Criteria andOS_IDBetween(String value1, String value2) {
			addCriterion("OS_ID between", value1, value2, "OS_ID");
			return (Criteria) this;
		}

		public Criteria andOS_IDNotBetween(String value1, String value2) {
			addCriterion("OS_ID not between", value1, value2, "OS_ID");
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

		public Criteria andLEASE_NOIsNull() {
			addCriterion("LEASE_NO is null");
			return (Criteria) this;
		}

		public Criteria andLEASE_NOIsNotNull() {
			addCriterion("LEASE_NO is not null");
			return (Criteria) this;
		}

		public Criteria andLEASE_NOEqualTo(String value) {
			addCriterion("LEASE_NO =", value, "LEASE_NO");
			return (Criteria) this;
		}

		public Criteria andLEASE_NONotEqualTo(String value) {
			addCriterion("LEASE_NO <>", value, "LEASE_NO");
			return (Criteria) this;
		}

		public Criteria andLEASE_NOGreaterThan(String value) {
			addCriterion("LEASE_NO >", value, "LEASE_NO");
			return (Criteria) this;
		}

		public Criteria andLEASE_NOGreaterThanOrEqualTo(String value) {
			addCriterion("LEASE_NO >=", value, "LEASE_NO");
			return (Criteria) this;
		}

		public Criteria andLEASE_NOLessThan(String value) {
			addCriterion("LEASE_NO <", value, "LEASE_NO");
			return (Criteria) this;
		}

		public Criteria andLEASE_NOLessThanOrEqualTo(String value) {
			addCriterion("LEASE_NO <=", value, "LEASE_NO");
			return (Criteria) this;
		}

		public Criteria andLEASE_NOLike(String value) {
			addCriterion("LEASE_NO like", value, "LEASE_NO");
			return (Criteria) this;
		}

		public Criteria andLEASE_NONotLike(String value) {
			addCriterion("LEASE_NO not like", value, "LEASE_NO");
			return (Criteria) this;
		}

		public Criteria andLEASE_NOIn(List<String> values) {
			addCriterion("LEASE_NO in", values, "LEASE_NO");
			return (Criteria) this;
		}

		public Criteria andLEASE_NONotIn(List<String> values) {
			addCriterion("LEASE_NO not in", values, "LEASE_NO");
			return (Criteria) this;
		}

		public Criteria andLEASE_NOBetween(String value1, String value2) {
			addCriterion("LEASE_NO between", value1, value2, "LEASE_NO");
			return (Criteria) this;
		}

		public Criteria andLEASE_NONotBetween(String value1, String value2) {
			addCriterion("LEASE_NO not between", value1, value2, "LEASE_NO");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table TB_PAY_ACCEPTANCE_ORDER
	 * @mbggenerated  Wed Apr 15 14:27:46 CST 2015
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
     * This class corresponds to the database table TB_PAY_ACCEPTANCE_ORDER
     *
     * @mbggenerated do_not_delete_during_merge Thu Jan 15 16:18:02 CST 2015
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}