package com.foya.dao.mybatis.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TbLsRewardExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table TB_LS_REWARD
	 * @mbggenerated  Wed Jan 28 16:10:34 CST 2015
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table TB_LS_REWARD
	 * @mbggenerated  Wed Jan 28 16:10:34 CST 2015
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table TB_LS_REWARD
	 * @mbggenerated  Wed Jan 28 16:10:34 CST 2015
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_REWARD
	 * @mbggenerated  Wed Jan 28 16:10:34 CST 2015
	 */
	public TbLsRewardExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_REWARD
	 * @mbggenerated  Wed Jan 28 16:10:34 CST 2015
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_REWARD
	 * @mbggenerated  Wed Jan 28 16:10:34 CST 2015
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_REWARD
	 * @mbggenerated  Wed Jan 28 16:10:34 CST 2015
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_REWARD
	 * @mbggenerated  Wed Jan 28 16:10:34 CST 2015
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_REWARD
	 * @mbggenerated  Wed Jan 28 16:10:34 CST 2015
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_REWARD
	 * @mbggenerated  Wed Jan 28 16:10:34 CST 2015
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_REWARD
	 * @mbggenerated  Wed Jan 28 16:10:34 CST 2015
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_REWARD
	 * @mbggenerated  Wed Jan 28 16:10:34 CST 2015
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_REWARD
	 * @mbggenerated  Wed Jan 28 16:10:34 CST 2015
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_REWARD
	 * @mbggenerated  Wed Jan 28 16:10:34 CST 2015
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table TB_LS_REWARD
	 * @mbggenerated  Wed Jan 28 16:10:34 CST 2015
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

		public Criteria andLS_NOIsNull() {
			addCriterion("LS_NO is null");
			return (Criteria) this;
		}

		public Criteria andLS_NOIsNotNull() {
			addCriterion("LS_NO is not null");
			return (Criteria) this;
		}

		public Criteria andLS_NOEqualTo(String value) {
			addCriterion("LS_NO =", value, "LS_NO");
			return (Criteria) this;
		}

		public Criteria andLS_NONotEqualTo(String value) {
			addCriterion("LS_NO <>", value, "LS_NO");
			return (Criteria) this;
		}

		public Criteria andLS_NOGreaterThan(String value) {
			addCriterion("LS_NO >", value, "LS_NO");
			return (Criteria) this;
		}

		public Criteria andLS_NOGreaterThanOrEqualTo(String value) {
			addCriterion("LS_NO >=", value, "LS_NO");
			return (Criteria) this;
		}

		public Criteria andLS_NOLessThan(String value) {
			addCriterion("LS_NO <", value, "LS_NO");
			return (Criteria) this;
		}

		public Criteria andLS_NOLessThanOrEqualTo(String value) {
			addCriterion("LS_NO <=", value, "LS_NO");
			return (Criteria) this;
		}

		public Criteria andLS_NOLike(String value) {
			addCriterion("LS_NO like", value, "LS_NO");
			return (Criteria) this;
		}

		public Criteria andLS_NONotLike(String value) {
			addCriterion("LS_NO not like", value, "LS_NO");
			return (Criteria) this;
		}

		public Criteria andLS_NOIn(List<String> values) {
			addCriterion("LS_NO in", values, "LS_NO");
			return (Criteria) this;
		}

		public Criteria andLS_NONotIn(List<String> values) {
			addCriterion("LS_NO not in", values, "LS_NO");
			return (Criteria) this;
		}

		public Criteria andLS_NOBetween(String value1, String value2) {
			addCriterion("LS_NO between", value1, value2, "LS_NO");
			return (Criteria) this;
		}

		public Criteria andLS_NONotBetween(String value1, String value2) {
			addCriterion("LS_NO not between", value1, value2, "LS_NO");
			return (Criteria) this;
		}

		public Criteria andLS_VERIsNull() {
			addCriterion("LS_VER is null");
			return (Criteria) this;
		}

		public Criteria andLS_VERIsNotNull() {
			addCriterion("LS_VER is not null");
			return (Criteria) this;
		}

		public Criteria andLS_VEREqualTo(String value) {
			addCriterion("LS_VER =", value, "LS_VER");
			return (Criteria) this;
		}

		public Criteria andLS_VERNotEqualTo(String value) {
			addCriterion("LS_VER <>", value, "LS_VER");
			return (Criteria) this;
		}

		public Criteria andLS_VERGreaterThan(String value) {
			addCriterion("LS_VER >", value, "LS_VER");
			return (Criteria) this;
		}

		public Criteria andLS_VERGreaterThanOrEqualTo(String value) {
			addCriterion("LS_VER >=", value, "LS_VER");
			return (Criteria) this;
		}

		public Criteria andLS_VERLessThan(String value) {
			addCriterion("LS_VER <", value, "LS_VER");
			return (Criteria) this;
		}

		public Criteria andLS_VERLessThanOrEqualTo(String value) {
			addCriterion("LS_VER <=", value, "LS_VER");
			return (Criteria) this;
		}

		public Criteria andLS_VERLike(String value) {
			addCriterion("LS_VER like", value, "LS_VER");
			return (Criteria) this;
		}

		public Criteria andLS_VERNotLike(String value) {
			addCriterion("LS_VER not like", value, "LS_VER");
			return (Criteria) this;
		}

		public Criteria andLS_VERIn(List<String> values) {
			addCriterion("LS_VER in", values, "LS_VER");
			return (Criteria) this;
		}

		public Criteria andLS_VERNotIn(List<String> values) {
			addCriterion("LS_VER not in", values, "LS_VER");
			return (Criteria) this;
		}

		public Criteria andLS_VERBetween(String value1, String value2) {
			addCriterion("LS_VER between", value1, value2, "LS_VER");
			return (Criteria) this;
		}

		public Criteria andLS_VERNotBetween(String value1, String value2) {
			addCriterion("LS_VER not between", value1, value2, "LS_VER");
			return (Criteria) this;
		}

		public Criteria andREWARD_TYPEIsNull() {
			addCriterion("REWARD_TYPE is null");
			return (Criteria) this;
		}

		public Criteria andREWARD_TYPEIsNotNull() {
			addCriterion("REWARD_TYPE is not null");
			return (Criteria) this;
		}

		public Criteria andREWARD_TYPEEqualTo(String value) {
			addCriterion("REWARD_TYPE =", value, "REWARD_TYPE");
			return (Criteria) this;
		}

		public Criteria andREWARD_TYPENotEqualTo(String value) {
			addCriterion("REWARD_TYPE <>", value, "REWARD_TYPE");
			return (Criteria) this;
		}

		public Criteria andREWARD_TYPEGreaterThan(String value) {
			addCriterion("REWARD_TYPE >", value, "REWARD_TYPE");
			return (Criteria) this;
		}

		public Criteria andREWARD_TYPEGreaterThanOrEqualTo(String value) {
			addCriterion("REWARD_TYPE >=", value, "REWARD_TYPE");
			return (Criteria) this;
		}

		public Criteria andREWARD_TYPELessThan(String value) {
			addCriterion("REWARD_TYPE <", value, "REWARD_TYPE");
			return (Criteria) this;
		}

		public Criteria andREWARD_TYPELessThanOrEqualTo(String value) {
			addCriterion("REWARD_TYPE <=", value, "REWARD_TYPE");
			return (Criteria) this;
		}

		public Criteria andREWARD_TYPELike(String value) {
			addCriterion("REWARD_TYPE like", value, "REWARD_TYPE");
			return (Criteria) this;
		}

		public Criteria andREWARD_TYPENotLike(String value) {
			addCriterion("REWARD_TYPE not like", value, "REWARD_TYPE");
			return (Criteria) this;
		}

		public Criteria andREWARD_TYPEIn(List<String> values) {
			addCriterion("REWARD_TYPE in", values, "REWARD_TYPE");
			return (Criteria) this;
		}

		public Criteria andREWARD_TYPENotIn(List<String> values) {
			addCriterion("REWARD_TYPE not in", values, "REWARD_TYPE");
			return (Criteria) this;
		}

		public Criteria andREWARD_TYPEBetween(String value1, String value2) {
			addCriterion("REWARD_TYPE between", value1, value2, "REWARD_TYPE");
			return (Criteria) this;
		}

		public Criteria andREWARD_TYPENotBetween(String value1, String value2) {
			addCriterion("REWARD_TYPE not between", value1, value2,
					"REWARD_TYPE");
			return (Criteria) this;
		}

		public Criteria andREWARD_IDIsNull() {
			addCriterion("REWARD_ID is null");
			return (Criteria) this;
		}

		public Criteria andREWARD_IDIsNotNull() {
			addCriterion("REWARD_ID is not null");
			return (Criteria) this;
		}

		public Criteria andREWARD_IDEqualTo(String value) {
			addCriterion("REWARD_ID =", value, "REWARD_ID");
			return (Criteria) this;
		}

		public Criteria andREWARD_IDNotEqualTo(String value) {
			addCriterion("REWARD_ID <>", value, "REWARD_ID");
			return (Criteria) this;
		}

		public Criteria andREWARD_IDGreaterThan(String value) {
			addCriterion("REWARD_ID >", value, "REWARD_ID");
			return (Criteria) this;
		}

		public Criteria andREWARD_IDGreaterThanOrEqualTo(String value) {
			addCriterion("REWARD_ID >=", value, "REWARD_ID");
			return (Criteria) this;
		}

		public Criteria andREWARD_IDLessThan(String value) {
			addCriterion("REWARD_ID <", value, "REWARD_ID");
			return (Criteria) this;
		}

		public Criteria andREWARD_IDLessThanOrEqualTo(String value) {
			addCriterion("REWARD_ID <=", value, "REWARD_ID");
			return (Criteria) this;
		}

		public Criteria andREWARD_IDLike(String value) {
			addCriterion("REWARD_ID like", value, "REWARD_ID");
			return (Criteria) this;
		}

		public Criteria andREWARD_IDNotLike(String value) {
			addCriterion("REWARD_ID not like", value, "REWARD_ID");
			return (Criteria) this;
		}

		public Criteria andREWARD_IDIn(List<String> values) {
			addCriterion("REWARD_ID in", values, "REWARD_ID");
			return (Criteria) this;
		}

		public Criteria andREWARD_IDNotIn(List<String> values) {
			addCriterion("REWARD_ID not in", values, "REWARD_ID");
			return (Criteria) this;
		}

		public Criteria andREWARD_IDBetween(String value1, String value2) {
			addCriterion("REWARD_ID between", value1, value2, "REWARD_ID");
			return (Criteria) this;
		}

		public Criteria andREWARD_IDNotBetween(String value1, String value2) {
			addCriterion("REWARD_ID not between", value1, value2, "REWARD_ID");
			return (Criteria) this;
		}

		public Criteria andCUST_NAMEIsNull() {
			addCriterion("CUST_NAME is null");
			return (Criteria) this;
		}

		public Criteria andCUST_NAMEIsNotNull() {
			addCriterion("CUST_NAME is not null");
			return (Criteria) this;
		}

		public Criteria andCUST_NAMEEqualTo(String value) {
			addCriterion("CUST_NAME =", value, "CUST_NAME");
			return (Criteria) this;
		}

		public Criteria andCUST_NAMENotEqualTo(String value) {
			addCriterion("CUST_NAME <>", value, "CUST_NAME");
			return (Criteria) this;
		}

		public Criteria andCUST_NAMEGreaterThan(String value) {
			addCriterion("CUST_NAME >", value, "CUST_NAME");
			return (Criteria) this;
		}

		public Criteria andCUST_NAMEGreaterThanOrEqualTo(String value) {
			addCriterion("CUST_NAME >=", value, "CUST_NAME");
			return (Criteria) this;
		}

		public Criteria andCUST_NAMELessThan(String value) {
			addCriterion("CUST_NAME <", value, "CUST_NAME");
			return (Criteria) this;
		}

		public Criteria andCUST_NAMELessThanOrEqualTo(String value) {
			addCriterion("CUST_NAME <=", value, "CUST_NAME");
			return (Criteria) this;
		}

		public Criteria andCUST_NAMELike(String value) {
			addCriterion("CUST_NAME like", value, "CUST_NAME");
			return (Criteria) this;
		}

		public Criteria andCUST_NAMENotLike(String value) {
			addCriterion("CUST_NAME not like", value, "CUST_NAME");
			return (Criteria) this;
		}

		public Criteria andCUST_NAMEIn(List<String> values) {
			addCriterion("CUST_NAME in", values, "CUST_NAME");
			return (Criteria) this;
		}

		public Criteria andCUST_NAMENotIn(List<String> values) {
			addCriterion("CUST_NAME not in", values, "CUST_NAME");
			return (Criteria) this;
		}

		public Criteria andCUST_NAMEBetween(String value1, String value2) {
			addCriterion("CUST_NAME between", value1, value2, "CUST_NAME");
			return (Criteria) this;
		}

		public Criteria andCUST_NAMENotBetween(String value1, String value2) {
			addCriterion("CUST_NAME not between", value1, value2, "CUST_NAME");
			return (Criteria) this;
		}

		public Criteria andPHONE_NBRIsNull() {
			addCriterion("PHONE_NBR is null");
			return (Criteria) this;
		}

		public Criteria andPHONE_NBRIsNotNull() {
			addCriterion("PHONE_NBR is not null");
			return (Criteria) this;
		}

		public Criteria andPHONE_NBREqualTo(String value) {
			addCriterion("PHONE_NBR =", value, "PHONE_NBR");
			return (Criteria) this;
		}

		public Criteria andPHONE_NBRNotEqualTo(String value) {
			addCriterion("PHONE_NBR <>", value, "PHONE_NBR");
			return (Criteria) this;
		}

		public Criteria andPHONE_NBRGreaterThan(String value) {
			addCriterion("PHONE_NBR >", value, "PHONE_NBR");
			return (Criteria) this;
		}

		public Criteria andPHONE_NBRGreaterThanOrEqualTo(String value) {
			addCriterion("PHONE_NBR >=", value, "PHONE_NBR");
			return (Criteria) this;
		}

		public Criteria andPHONE_NBRLessThan(String value) {
			addCriterion("PHONE_NBR <", value, "PHONE_NBR");
			return (Criteria) this;
		}

		public Criteria andPHONE_NBRLessThanOrEqualTo(String value) {
			addCriterion("PHONE_NBR <=", value, "PHONE_NBR");
			return (Criteria) this;
		}

		public Criteria andPHONE_NBRLike(String value) {
			addCriterion("PHONE_NBR like", value, "PHONE_NBR");
			return (Criteria) this;
		}

		public Criteria andPHONE_NBRNotLike(String value) {
			addCriterion("PHONE_NBR not like", value, "PHONE_NBR");
			return (Criteria) this;
		}

		public Criteria andPHONE_NBRIn(List<String> values) {
			addCriterion("PHONE_NBR in", values, "PHONE_NBR");
			return (Criteria) this;
		}

		public Criteria andPHONE_NBRNotIn(List<String> values) {
			addCriterion("PHONE_NBR not in", values, "PHONE_NBR");
			return (Criteria) this;
		}

		public Criteria andPHONE_NBRBetween(String value1, String value2) {
			addCriterion("PHONE_NBR between", value1, value2, "PHONE_NBR");
			return (Criteria) this;
		}

		public Criteria andPHONE_NBRNotBetween(String value1, String value2) {
			addCriterion("PHONE_NBR not between", value1, value2, "PHONE_NBR");
			return (Criteria) this;
		}

		public Criteria andPRCINGIsNull() {
			addCriterion("PRCING is null");
			return (Criteria) this;
		}

		public Criteria andPRCINGIsNotNull() {
			addCriterion("PRCING is not null");
			return (Criteria) this;
		}

		public Criteria andPRCINGEqualTo(String value) {
			addCriterion("PRCING =", value, "PRCING");
			return (Criteria) this;
		}

		public Criteria andPRCINGNotEqualTo(String value) {
			addCriterion("PRCING <>", value, "PRCING");
			return (Criteria) this;
		}

		public Criteria andPRCINGGreaterThan(String value) {
			addCriterion("PRCING >", value, "PRCING");
			return (Criteria) this;
		}

		public Criteria andPRCINGGreaterThanOrEqualTo(String value) {
			addCriterion("PRCING >=", value, "PRCING");
			return (Criteria) this;
		}

		public Criteria andPRCINGLessThan(String value) {
			addCriterion("PRCING <", value, "PRCING");
			return (Criteria) this;
		}

		public Criteria andPRCINGLessThanOrEqualTo(String value) {
			addCriterion("PRCING <=", value, "PRCING");
			return (Criteria) this;
		}

		public Criteria andPRCINGLike(String value) {
			addCriterion("PRCING like", value, "PRCING");
			return (Criteria) this;
		}

		public Criteria andPRCINGNotLike(String value) {
			addCriterion("PRCING not like", value, "PRCING");
			return (Criteria) this;
		}

		public Criteria andPRCINGIn(List<String> values) {
			addCriterion("PRCING in", values, "PRCING");
			return (Criteria) this;
		}

		public Criteria andPRCINGNotIn(List<String> values) {
			addCriterion("PRCING not in", values, "PRCING");
			return (Criteria) this;
		}

		public Criteria andPRCINGBetween(String value1, String value2) {
			addCriterion("PRCING between", value1, value2, "PRCING");
			return (Criteria) this;
		}

		public Criteria andPRCINGNotBetween(String value1, String value2) {
			addCriterion("PRCING not between", value1, value2, "PRCING");
			return (Criteria) this;
		}

		public Criteria andREWARD_QTYIsNull() {
			addCriterion("REWARD_QTY is null");
			return (Criteria) this;
		}

		public Criteria andREWARD_QTYIsNotNull() {
			addCriterion("REWARD_QTY is not null");
			return (Criteria) this;
		}

		public Criteria andREWARD_QTYEqualTo(BigDecimal value) {
			addCriterion("REWARD_QTY =", value, "REWARD_QTY");
			return (Criteria) this;
		}

		public Criteria andREWARD_QTYNotEqualTo(BigDecimal value) {
			addCriterion("REWARD_QTY <>", value, "REWARD_QTY");
			return (Criteria) this;
		}

		public Criteria andREWARD_QTYGreaterThan(BigDecimal value) {
			addCriterion("REWARD_QTY >", value, "REWARD_QTY");
			return (Criteria) this;
		}

		public Criteria andREWARD_QTYGreaterThanOrEqualTo(BigDecimal value) {
			addCriterion("REWARD_QTY >=", value, "REWARD_QTY");
			return (Criteria) this;
		}

		public Criteria andREWARD_QTYLessThan(BigDecimal value) {
			addCriterion("REWARD_QTY <", value, "REWARD_QTY");
			return (Criteria) this;
		}

		public Criteria andREWARD_QTYLessThanOrEqualTo(BigDecimal value) {
			addCriterion("REWARD_QTY <=", value, "REWARD_QTY");
			return (Criteria) this;
		}

		public Criteria andREWARD_QTYIn(List<BigDecimal> values) {
			addCriterion("REWARD_QTY in", values, "REWARD_QTY");
			return (Criteria) this;
		}

		public Criteria andREWARD_QTYNotIn(List<BigDecimal> values) {
			addCriterion("REWARD_QTY not in", values, "REWARD_QTY");
			return (Criteria) this;
		}

		public Criteria andREWARD_QTYBetween(BigDecimal value1,
				BigDecimal value2) {
			addCriterion("REWARD_QTY between", value1, value2, "REWARD_QTY");
			return (Criteria) this;
		}

		public Criteria andREWARD_QTYNotBetween(BigDecimal value1,
				BigDecimal value2) {
			addCriterion("REWARD_QTY not between", value1, value2, "REWARD_QTY");
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

		public Criteria andGET_DATEIsNull() {
			addCriterion("GET_DATE is null");
			return (Criteria) this;
		}

		public Criteria andGET_DATEIsNotNull() {
			addCriterion("GET_DATE is not null");
			return (Criteria) this;
		}

		public Criteria andGET_DATEEqualTo(Date value) {
			addCriterion("GET_DATE =", value, "GET_DATE");
			return (Criteria) this;
		}

		public Criteria andGET_DATENotEqualTo(Date value) {
			addCriterion("GET_DATE <>", value, "GET_DATE");
			return (Criteria) this;
		}

		public Criteria andGET_DATEGreaterThan(Date value) {
			addCriterion("GET_DATE >", value, "GET_DATE");
			return (Criteria) this;
		}

		public Criteria andGET_DATEGreaterThanOrEqualTo(Date value) {
			addCriterion("GET_DATE >=", value, "GET_DATE");
			return (Criteria) this;
		}

		public Criteria andGET_DATELessThan(Date value) {
			addCriterion("GET_DATE <", value, "GET_DATE");
			return (Criteria) this;
		}

		public Criteria andGET_DATELessThanOrEqualTo(Date value) {
			addCriterion("GET_DATE <=", value, "GET_DATE");
			return (Criteria) this;
		}

		public Criteria andGET_DATEIn(List<Date> values) {
			addCriterion("GET_DATE in", values, "GET_DATE");
			return (Criteria) this;
		}

		public Criteria andGET_DATENotIn(List<Date> values) {
			addCriterion("GET_DATE not in", values, "GET_DATE");
			return (Criteria) this;
		}

		public Criteria andGET_DATEBetween(Date value1, Date value2) {
			addCriterion("GET_DATE between", value1, value2, "GET_DATE");
			return (Criteria) this;
		}

		public Criteria andGET_DATENotBetween(Date value1, Date value2) {
			addCriterion("GET_DATE not between", value1, value2, "GET_DATE");
			return (Criteria) this;
		}

		public Criteria andREWARD_RESNIsNull() {
			addCriterion("REWARD_RESN is null");
			return (Criteria) this;
		}

		public Criteria andREWARD_RESNIsNotNull() {
			addCriterion("REWARD_RESN is not null");
			return (Criteria) this;
		}

		public Criteria andREWARD_RESNEqualTo(String value) {
			addCriterion("REWARD_RESN =", value, "REWARD_RESN");
			return (Criteria) this;
		}

		public Criteria andREWARD_RESNNotEqualTo(String value) {
			addCriterion("REWARD_RESN <>", value, "REWARD_RESN");
			return (Criteria) this;
		}

		public Criteria andREWARD_RESNGreaterThan(String value) {
			addCriterion("REWARD_RESN >", value, "REWARD_RESN");
			return (Criteria) this;
		}

		public Criteria andREWARD_RESNGreaterThanOrEqualTo(String value) {
			addCriterion("REWARD_RESN >=", value, "REWARD_RESN");
			return (Criteria) this;
		}

		public Criteria andREWARD_RESNLessThan(String value) {
			addCriterion("REWARD_RESN <", value, "REWARD_RESN");
			return (Criteria) this;
		}

		public Criteria andREWARD_RESNLessThanOrEqualTo(String value) {
			addCriterion("REWARD_RESN <=", value, "REWARD_RESN");
			return (Criteria) this;
		}

		public Criteria andREWARD_RESNLike(String value) {
			addCriterion("REWARD_RESN like", value, "REWARD_RESN");
			return (Criteria) this;
		}

		public Criteria andREWARD_RESNNotLike(String value) {
			addCriterion("REWARD_RESN not like", value, "REWARD_RESN");
			return (Criteria) this;
		}

		public Criteria andREWARD_RESNIn(List<String> values) {
			addCriterion("REWARD_RESN in", values, "REWARD_RESN");
			return (Criteria) this;
		}

		public Criteria andREWARD_RESNNotIn(List<String> values) {
			addCriterion("REWARD_RESN not in", values, "REWARD_RESN");
			return (Criteria) this;
		}

		public Criteria andREWARD_RESNBetween(String value1, String value2) {
			addCriterion("REWARD_RESN between", value1, value2, "REWARD_RESN");
			return (Criteria) this;
		}

		public Criteria andREWARD_RESNNotBetween(String value1, String value2) {
			addCriterion("REWARD_RESN not between", value1, value2,
					"REWARD_RESN");
			return (Criteria) this;
		}

		public Criteria andREWARD_DESCIsNull() {
			addCriterion("REWARD_DESC is null");
			return (Criteria) this;
		}

		public Criteria andREWARD_DESCIsNotNull() {
			addCriterion("REWARD_DESC is not null");
			return (Criteria) this;
		}

		public Criteria andREWARD_DESCEqualTo(String value) {
			addCriterion("REWARD_DESC =", value, "REWARD_DESC");
			return (Criteria) this;
		}

		public Criteria andREWARD_DESCNotEqualTo(String value) {
			addCriterion("REWARD_DESC <>", value, "REWARD_DESC");
			return (Criteria) this;
		}

		public Criteria andREWARD_DESCGreaterThan(String value) {
			addCriterion("REWARD_DESC >", value, "REWARD_DESC");
			return (Criteria) this;
		}

		public Criteria andREWARD_DESCGreaterThanOrEqualTo(String value) {
			addCriterion("REWARD_DESC >=", value, "REWARD_DESC");
			return (Criteria) this;
		}

		public Criteria andREWARD_DESCLessThan(String value) {
			addCriterion("REWARD_DESC <", value, "REWARD_DESC");
			return (Criteria) this;
		}

		public Criteria andREWARD_DESCLessThanOrEqualTo(String value) {
			addCriterion("REWARD_DESC <=", value, "REWARD_DESC");
			return (Criteria) this;
		}

		public Criteria andREWARD_DESCLike(String value) {
			addCriterion("REWARD_DESC like", value, "REWARD_DESC");
			return (Criteria) this;
		}

		public Criteria andREWARD_DESCNotLike(String value) {
			addCriterion("REWARD_DESC not like", value, "REWARD_DESC");
			return (Criteria) this;
		}

		public Criteria andREWARD_DESCIn(List<String> values) {
			addCriterion("REWARD_DESC in", values, "REWARD_DESC");
			return (Criteria) this;
		}

		public Criteria andREWARD_DESCNotIn(List<String> values) {
			addCriterion("REWARD_DESC not in", values, "REWARD_DESC");
			return (Criteria) this;
		}

		public Criteria andREWARD_DESCBetween(String value1, String value2) {
			addCriterion("REWARD_DESC between", value1, value2, "REWARD_DESC");
			return (Criteria) this;
		}

		public Criteria andREWARD_DESCNotBetween(String value1, String value2) {
			addCriterion("REWARD_DESC not between", value1, value2,
					"REWARD_DESC");
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
	 * This class was generated by MyBatis Generator. This class corresponds to the database table TB_LS_REWARD
	 * @mbggenerated  Wed Jan 28 16:10:34 CST 2015
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
     * This class corresponds to the database table TB_LS_REWARD
     *
     * @mbggenerated do_not_delete_during_merge Tue Jan 27 18:58:33 CST 2015
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}