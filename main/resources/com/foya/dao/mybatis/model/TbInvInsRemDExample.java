package com.foya.dao.mybatis.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TbInvInsRemDExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table TB_INV_INS_REM_D
	 * @mbggenerated  Mon Feb 02 17:12:51 CST 2015
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table TB_INV_INS_REM_D
	 * @mbggenerated  Mon Feb 02 17:12:51 CST 2015
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table TB_INV_INS_REM_D
	 * @mbggenerated  Mon Feb 02 17:12:51 CST 2015
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_INS_REM_D
	 * @mbggenerated  Mon Feb 02 17:12:51 CST 2015
	 */
	public TbInvInsRemDExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_INS_REM_D
	 * @mbggenerated  Mon Feb 02 17:12:51 CST 2015
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_INS_REM_D
	 * @mbggenerated  Mon Feb 02 17:12:51 CST 2015
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_INS_REM_D
	 * @mbggenerated  Mon Feb 02 17:12:51 CST 2015
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_INS_REM_D
	 * @mbggenerated  Mon Feb 02 17:12:51 CST 2015
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_INS_REM_D
	 * @mbggenerated  Mon Feb 02 17:12:51 CST 2015
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_INS_REM_D
	 * @mbggenerated  Mon Feb 02 17:12:51 CST 2015
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_INS_REM_D
	 * @mbggenerated  Mon Feb 02 17:12:51 CST 2015
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_INS_REM_D
	 * @mbggenerated  Mon Feb 02 17:12:51 CST 2015
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_INS_REM_D
	 * @mbggenerated  Mon Feb 02 17:12:51 CST 2015
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_INS_REM_D
	 * @mbggenerated  Mon Feb 02 17:12:51 CST 2015
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table TB_INV_INS_REM_D
	 * @mbggenerated  Mon Feb 02 17:12:51 CST 2015
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

		public Criteria andDTL_SEQIsNull() {
			addCriterion("DTL_SEQ is null");
			return (Criteria) this;
		}

		public Criteria andDTL_SEQIsNotNull() {
			addCriterion("DTL_SEQ is not null");
			return (Criteria) this;
		}

		public Criteria andDTL_SEQEqualTo(Long value) {
			addCriterion("DTL_SEQ =", value, "DTL_SEQ");
			return (Criteria) this;
		}

		public Criteria andDTL_SEQNotEqualTo(Long value) {
			addCriterion("DTL_SEQ <>", value, "DTL_SEQ");
			return (Criteria) this;
		}

		public Criteria andDTL_SEQGreaterThan(Long value) {
			addCriterion("DTL_SEQ >", value, "DTL_SEQ");
			return (Criteria) this;
		}

		public Criteria andDTL_SEQGreaterThanOrEqualTo(Long value) {
			addCriterion("DTL_SEQ >=", value, "DTL_SEQ");
			return (Criteria) this;
		}

		public Criteria andDTL_SEQLessThan(Long value) {
			addCriterion("DTL_SEQ <", value, "DTL_SEQ");
			return (Criteria) this;
		}

		public Criteria andDTL_SEQLessThanOrEqualTo(Long value) {
			addCriterion("DTL_SEQ <=", value, "DTL_SEQ");
			return (Criteria) this;
		}

		public Criteria andDTL_SEQIn(List<Long> values) {
			addCriterion("DTL_SEQ in", values, "DTL_SEQ");
			return (Criteria) this;
		}

		public Criteria andDTL_SEQNotIn(List<Long> values) {
			addCriterion("DTL_SEQ not in", values, "DTL_SEQ");
			return (Criteria) this;
		}

		public Criteria andDTL_SEQBetween(Long value1, Long value2) {
			addCriterion("DTL_SEQ between", value1, value2, "DTL_SEQ");
			return (Criteria) this;
		}

		public Criteria andDTL_SEQNotBetween(Long value1, Long value2) {
			addCriterion("DTL_SEQ not between", value1, value2, "DTL_SEQ");
			return (Criteria) this;
		}

		public Criteria andOPT_IDIsNull() {
			addCriterion("OPT_ID is null");
			return (Criteria) this;
		}

		public Criteria andOPT_IDIsNotNull() {
			addCriterion("OPT_ID is not null");
			return (Criteria) this;
		}

		public Criteria andOPT_IDEqualTo(String value) {
			addCriterion("OPT_ID =", value, "OPT_ID");
			return (Criteria) this;
		}

		public Criteria andOPT_IDNotEqualTo(String value) {
			addCriterion("OPT_ID <>", value, "OPT_ID");
			return (Criteria) this;
		}

		public Criteria andOPT_IDGreaterThan(String value) {
			addCriterion("OPT_ID >", value, "OPT_ID");
			return (Criteria) this;
		}

		public Criteria andOPT_IDGreaterThanOrEqualTo(String value) {
			addCriterion("OPT_ID >=", value, "OPT_ID");
			return (Criteria) this;
		}

		public Criteria andOPT_IDLessThan(String value) {
			addCriterion("OPT_ID <", value, "OPT_ID");
			return (Criteria) this;
		}

		public Criteria andOPT_IDLessThanOrEqualTo(String value) {
			addCriterion("OPT_ID <=", value, "OPT_ID");
			return (Criteria) this;
		}

		public Criteria andOPT_IDLike(String value) {
			addCriterion("OPT_ID like", value, "OPT_ID");
			return (Criteria) this;
		}

		public Criteria andOPT_IDNotLike(String value) {
			addCriterion("OPT_ID not like", value, "OPT_ID");
			return (Criteria) this;
		}

		public Criteria andOPT_IDIn(List<String> values) {
			addCriterion("OPT_ID in", values, "OPT_ID");
			return (Criteria) this;
		}

		public Criteria andOPT_IDNotIn(List<String> values) {
			addCriterion("OPT_ID not in", values, "OPT_ID");
			return (Criteria) this;
		}

		public Criteria andOPT_IDBetween(String value1, String value2) {
			addCriterion("OPT_ID between", value1, value2, "OPT_ID");
			return (Criteria) this;
		}

		public Criteria andOPT_IDNotBetween(String value1, String value2) {
			addCriterion("OPT_ID not between", value1, value2, "OPT_ID");
			return (Criteria) this;
		}

		public Criteria andMAT_NOIsNull() {
			addCriterion("MAT_NO is null");
			return (Criteria) this;
		}

		public Criteria andMAT_NOIsNotNull() {
			addCriterion("MAT_NO is not null");
			return (Criteria) this;
		}

		public Criteria andMAT_NOEqualTo(String value) {
			addCriterion("MAT_NO =", value, "MAT_NO");
			return (Criteria) this;
		}

		public Criteria andMAT_NONotEqualTo(String value) {
			addCriterion("MAT_NO <>", value, "MAT_NO");
			return (Criteria) this;
		}

		public Criteria andMAT_NOGreaterThan(String value) {
			addCriterion("MAT_NO >", value, "MAT_NO");
			return (Criteria) this;
		}

		public Criteria andMAT_NOGreaterThanOrEqualTo(String value) {
			addCriterion("MAT_NO >=", value, "MAT_NO");
			return (Criteria) this;
		}

		public Criteria andMAT_NOLessThan(String value) {
			addCriterion("MAT_NO <", value, "MAT_NO");
			return (Criteria) this;
		}

		public Criteria andMAT_NOLessThanOrEqualTo(String value) {
			addCriterion("MAT_NO <=", value, "MAT_NO");
			return (Criteria) this;
		}

		public Criteria andMAT_NOLike(String value) {
			addCriterion("MAT_NO like", value, "MAT_NO");
			return (Criteria) this;
		}

		public Criteria andMAT_NONotLike(String value) {
			addCriterion("MAT_NO not like", value, "MAT_NO");
			return (Criteria) this;
		}

		public Criteria andMAT_NOIn(List<String> values) {
			addCriterion("MAT_NO in", values, "MAT_NO");
			return (Criteria) this;
		}

		public Criteria andMAT_NONotIn(List<String> values) {
			addCriterion("MAT_NO not in", values, "MAT_NO");
			return (Criteria) this;
		}

		public Criteria andMAT_NOBetween(String value1, String value2) {
			addCriterion("MAT_NO between", value1, value2, "MAT_NO");
			return (Criteria) this;
		}

		public Criteria andMAT_NONotBetween(String value1, String value2) {
			addCriterion("MAT_NO not between", value1, value2, "MAT_NO");
			return (Criteria) this;
		}

		public Criteria andSRL_NOIsNull() {
			addCriterion("SRL_NO is null");
			return (Criteria) this;
		}

		public Criteria andSRL_NOIsNotNull() {
			addCriterion("SRL_NO is not null");
			return (Criteria) this;
		}

		public Criteria andSRL_NOEqualTo(Long value) {
			addCriterion("SRL_NO =", value, "SRL_NO");
			return (Criteria) this;
		}

		public Criteria andSRL_NONotEqualTo(Long value) {
			addCriterion("SRL_NO <>", value, "SRL_NO");
			return (Criteria) this;
		}

		public Criteria andSRL_NOGreaterThan(Long value) {
			addCriterion("SRL_NO >", value, "SRL_NO");
			return (Criteria) this;
		}

		public Criteria andSRL_NOGreaterThanOrEqualTo(Long value) {
			addCriterion("SRL_NO >=", value, "SRL_NO");
			return (Criteria) this;
		}

		public Criteria andSRL_NOLessThan(Long value) {
			addCriterion("SRL_NO <", value, "SRL_NO");
			return (Criteria) this;
		}

		public Criteria andSRL_NOLessThanOrEqualTo(Long value) {
			addCriterion("SRL_NO <=", value, "SRL_NO");
			return (Criteria) this;
		}

		public Criteria andSRL_NOIn(List<Long> values) {
			addCriterion("SRL_NO in", values, "SRL_NO");
			return (Criteria) this;
		}

		public Criteria andSRL_NONotIn(List<Long> values) {
			addCriterion("SRL_NO not in", values, "SRL_NO");
			return (Criteria) this;
		}

		public Criteria andSRL_NOBetween(Long value1, Long value2) {
			addCriterion("SRL_NO between", value1, value2, "SRL_NO");
			return (Criteria) this;
		}

		public Criteria andSRL_NONotBetween(Long value1, Long value2) {
			addCriterion("SRL_NO not between", value1, value2, "SRL_NO");
			return (Criteria) this;
		}

		public Criteria andQTYIsNull() {
			addCriterion("QTY is null");
			return (Criteria) this;
		}

		public Criteria andQTYIsNotNull() {
			addCriterion("QTY is not null");
			return (Criteria) this;
		}

		public Criteria andQTYEqualTo(Integer value) {
			addCriterion("QTY =", value, "QTY");
			return (Criteria) this;
		}

		public Criteria andQTYNotEqualTo(Integer value) {
			addCriterion("QTY <>", value, "QTY");
			return (Criteria) this;
		}

		public Criteria andQTYGreaterThan(Integer value) {
			addCriterion("QTY >", value, "QTY");
			return (Criteria) this;
		}

		public Criteria andQTYGreaterThanOrEqualTo(Integer value) {
			addCriterion("QTY >=", value, "QTY");
			return (Criteria) this;
		}

		public Criteria andQTYLessThan(Integer value) {
			addCriterion("QTY <", value, "QTY");
			return (Criteria) this;
		}

		public Criteria andQTYLessThanOrEqualTo(Integer value) {
			addCriterion("QTY <=", value, "QTY");
			return (Criteria) this;
		}

		public Criteria andQTYIn(List<Integer> values) {
			addCriterion("QTY in", values, "QTY");
			return (Criteria) this;
		}

		public Criteria andQTYNotIn(List<Integer> values) {
			addCriterion("QTY not in", values, "QTY");
			return (Criteria) this;
		}

		public Criteria andQTYBetween(Integer value1, Integer value2) {
			addCriterion("QTY between", value1, value2, "QTY");
			return (Criteria) this;
		}

		public Criteria andQTYNotBetween(Integer value1, Integer value2) {
			addCriterion("QTY not between", value1, value2, "QTY");
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
	 * This class was generated by MyBatis Generator. This class corresponds to the database table TB_INV_INS_REM_D
	 * @mbggenerated  Mon Feb 02 17:12:51 CST 2015
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
     * This class corresponds to the database table TB_INV_INS_REM_D
     *
     * @mbggenerated do_not_delete_during_merge Mon Feb 02 13:42:00 CST 2015
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}