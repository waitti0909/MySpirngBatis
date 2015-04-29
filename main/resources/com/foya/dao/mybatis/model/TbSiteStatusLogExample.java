package com.foya.dao.mybatis.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class TbSiteStatusLogExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table TB_SITE_STATUS_LOG
	 * @mbggenerated  Thu Dec 04 17:41:51 CST 2014
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table TB_SITE_STATUS_LOG
	 * @mbggenerated  Thu Dec 04 17:41:51 CST 2014
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table TB_SITE_STATUS_LOG
	 * @mbggenerated  Thu Dec 04 17:41:51 CST 2014
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_SITE_STATUS_LOG
	 * @mbggenerated  Thu Dec 04 17:41:51 CST 2014
	 */
	public TbSiteStatusLogExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_SITE_STATUS_LOG
	 * @mbggenerated  Thu Dec 04 17:41:51 CST 2014
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_SITE_STATUS_LOG
	 * @mbggenerated  Thu Dec 04 17:41:51 CST 2014
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_SITE_STATUS_LOG
	 * @mbggenerated  Thu Dec 04 17:41:51 CST 2014
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_SITE_STATUS_LOG
	 * @mbggenerated  Thu Dec 04 17:41:51 CST 2014
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_SITE_STATUS_LOG
	 * @mbggenerated  Thu Dec 04 17:41:51 CST 2014
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_SITE_STATUS_LOG
	 * @mbggenerated  Thu Dec 04 17:41:51 CST 2014
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_SITE_STATUS_LOG
	 * @mbggenerated  Thu Dec 04 17:41:51 CST 2014
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_SITE_STATUS_LOG
	 * @mbggenerated  Thu Dec 04 17:41:51 CST 2014
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_SITE_STATUS_LOG
	 * @mbggenerated  Thu Dec 04 17:41:51 CST 2014
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_SITE_STATUS_LOG
	 * @mbggenerated  Thu Dec 04 17:41:51 CST 2014
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table TB_SITE_STATUS_LOG
	 * @mbggenerated  Thu Dec 04 17:41:51 CST 2014
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

		public Criteria andBTS_SITE_IDIsNull() {
			addCriterion("BTS_SITE_ID is null");
			return (Criteria) this;
		}

		public Criteria andBTS_SITE_IDIsNotNull() {
			addCriterion("BTS_SITE_ID is not null");
			return (Criteria) this;
		}

		public Criteria andBTS_SITE_IDEqualTo(String value) {
			addCriterion("BTS_SITE_ID =", value, "BTS_SITE_ID");
			return (Criteria) this;
		}

		public Criteria andBTS_SITE_IDNotEqualTo(String value) {
			addCriterion("BTS_SITE_ID <>", value, "BTS_SITE_ID");
			return (Criteria) this;
		}

		public Criteria andBTS_SITE_IDGreaterThan(String value) {
			addCriterion("BTS_SITE_ID >", value, "BTS_SITE_ID");
			return (Criteria) this;
		}

		public Criteria andBTS_SITE_IDGreaterThanOrEqualTo(String value) {
			addCriterion("BTS_SITE_ID >=", value, "BTS_SITE_ID");
			return (Criteria) this;
		}

		public Criteria andBTS_SITE_IDLessThan(String value) {
			addCriterion("BTS_SITE_ID <", value, "BTS_SITE_ID");
			return (Criteria) this;
		}

		public Criteria andBTS_SITE_IDLessThanOrEqualTo(String value) {
			addCriterion("BTS_SITE_ID <=", value, "BTS_SITE_ID");
			return (Criteria) this;
		}

		public Criteria andBTS_SITE_IDLike(String value) {
			addCriterion("BTS_SITE_ID like", value, "BTS_SITE_ID");
			return (Criteria) this;
		}

		public Criteria andBTS_SITE_IDNotLike(String value) {
			addCriterion("BTS_SITE_ID not like", value, "BTS_SITE_ID");
			return (Criteria) this;
		}

		public Criteria andBTS_SITE_IDIn(List<String> values) {
			addCriterion("BTS_SITE_ID in", values, "BTS_SITE_ID");
			return (Criteria) this;
		}

		public Criteria andBTS_SITE_IDNotIn(List<String> values) {
			addCriterion("BTS_SITE_ID not in", values, "BTS_SITE_ID");
			return (Criteria) this;
		}

		public Criteria andBTS_SITE_IDBetween(String value1, String value2) {
			addCriterion("BTS_SITE_ID between", value1, value2, "BTS_SITE_ID");
			return (Criteria) this;
		}

		public Criteria andBTS_SITE_IDNotBetween(String value1, String value2) {
			addCriterion("BTS_SITE_ID not between", value1, value2,
					"BTS_SITE_ID");
			return (Criteria) this;
		}

		public Criteria andSITE_STATUSIsNull() {
			addCriterion("SITE_STATUS is null");
			return (Criteria) this;
		}

		public Criteria andSITE_STATUSIsNotNull() {
			addCriterion("SITE_STATUS is not null");
			return (Criteria) this;
		}

		public Criteria andSITE_STATUSEqualTo(String value) {
			addCriterion("SITE_STATUS =", value, "SITE_STATUS");
			return (Criteria) this;
		}

		public Criteria andSITE_STATUSNotEqualTo(String value) {
			addCriterion("SITE_STATUS <>", value, "SITE_STATUS");
			return (Criteria) this;
		}

		public Criteria andSITE_STATUSGreaterThan(String value) {
			addCriterion("SITE_STATUS >", value, "SITE_STATUS");
			return (Criteria) this;
		}

		public Criteria andSITE_STATUSGreaterThanOrEqualTo(String value) {
			addCriterion("SITE_STATUS >=", value, "SITE_STATUS");
			return (Criteria) this;
		}

		public Criteria andSITE_STATUSLessThan(String value) {
			addCriterion("SITE_STATUS <", value, "SITE_STATUS");
			return (Criteria) this;
		}

		public Criteria andSITE_STATUSLessThanOrEqualTo(String value) {
			addCriterion("SITE_STATUS <=", value, "SITE_STATUS");
			return (Criteria) this;
		}

		public Criteria andSITE_STATUSLike(String value) {
			addCriterion("SITE_STATUS like", value, "SITE_STATUS");
			return (Criteria) this;
		}

		public Criteria andSITE_STATUSNotLike(String value) {
			addCriterion("SITE_STATUS not like", value, "SITE_STATUS");
			return (Criteria) this;
		}

		public Criteria andSITE_STATUSIn(List<String> values) {
			addCriterion("SITE_STATUS in", values, "SITE_STATUS");
			return (Criteria) this;
		}

		public Criteria andSITE_STATUSNotIn(List<String> values) {
			addCriterion("SITE_STATUS not in", values, "SITE_STATUS");
			return (Criteria) this;
		}

		public Criteria andSITE_STATUSBetween(String value1, String value2) {
			addCriterion("SITE_STATUS between", value1, value2, "SITE_STATUS");
			return (Criteria) this;
		}

		public Criteria andSITE_STATUSNotBetween(String value1, String value2) {
			addCriterion("SITE_STATUS not between", value1, value2,
					"SITE_STATUS");
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
	 * This class was generated by MyBatis Generator. This class corresponds to the database table TB_SITE_STATUS_LOG
	 * @mbggenerated  Thu Dec 04 17:41:51 CST 2014
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
     * This class corresponds to the database table TB_SITE_STATUS_LOG
     *
     * @mbggenerated do_not_delete_during_merge Wed Nov 19 17:16:51 CST 2014
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}