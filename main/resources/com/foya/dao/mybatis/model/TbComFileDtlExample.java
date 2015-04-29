package com.foya.dao.mybatis.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class TbComFileDtlExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table TB_COM_FILE_DTL
	 * @mbggenerated  Thu Sep 04 17:43:10 CST 2014
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table TB_COM_FILE_DTL
	 * @mbggenerated  Thu Sep 04 17:43:10 CST 2014
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table TB_COM_FILE_DTL
	 * @mbggenerated  Thu Sep 04 17:43:10 CST 2014
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_FILE_DTL
	 * @mbggenerated  Thu Sep 04 17:43:10 CST 2014
	 */
	public TbComFileDtlExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_FILE_DTL
	 * @mbggenerated  Thu Sep 04 17:43:10 CST 2014
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_FILE_DTL
	 * @mbggenerated  Thu Sep 04 17:43:10 CST 2014
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_FILE_DTL
	 * @mbggenerated  Thu Sep 04 17:43:10 CST 2014
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_FILE_DTL
	 * @mbggenerated  Thu Sep 04 17:43:10 CST 2014
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_FILE_DTL
	 * @mbggenerated  Thu Sep 04 17:43:10 CST 2014
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_FILE_DTL
	 * @mbggenerated  Thu Sep 04 17:43:10 CST 2014
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_FILE_DTL
	 * @mbggenerated  Thu Sep 04 17:43:10 CST 2014
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_FILE_DTL
	 * @mbggenerated  Thu Sep 04 17:43:10 CST 2014
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_FILE_DTL
	 * @mbggenerated  Thu Sep 04 17:43:10 CST 2014
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_FILE_DTL
	 * @mbggenerated  Thu Sep 04 17:43:10 CST 2014
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table TB_COM_FILE_DTL
	 * @mbggenerated  Thu Sep 04 17:43:10 CST 2014
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

		public Criteria andFILE_DTL_SEQ_IDIsNull() {
			addCriterion("FILE_DTL_SEQ_ID is null");
			return (Criteria) this;
		}

		public Criteria andFILE_DTL_SEQ_IDIsNotNull() {
			addCriterion("FILE_DTL_SEQ_ID is not null");
			return (Criteria) this;
		}

		public Criteria andFILE_DTL_SEQ_IDEqualTo(BigDecimal value) {
			addCriterion("FILE_DTL_SEQ_ID =", value, "FILE_DTL_SEQ_ID");
			return (Criteria) this;
		}

		public Criteria andFILE_DTL_SEQ_IDNotEqualTo(BigDecimal value) {
			addCriterion("FILE_DTL_SEQ_ID <>", value, "FILE_DTL_SEQ_ID");
			return (Criteria) this;
		}

		public Criteria andFILE_DTL_SEQ_IDGreaterThan(BigDecimal value) {
			addCriterion("FILE_DTL_SEQ_ID >", value, "FILE_DTL_SEQ_ID");
			return (Criteria) this;
		}

		public Criteria andFILE_DTL_SEQ_IDGreaterThanOrEqualTo(BigDecimal value) {
			addCriterion("FILE_DTL_SEQ_ID >=", value, "FILE_DTL_SEQ_ID");
			return (Criteria) this;
		}

		public Criteria andFILE_DTL_SEQ_IDLessThan(BigDecimal value) {
			addCriterion("FILE_DTL_SEQ_ID <", value, "FILE_DTL_SEQ_ID");
			return (Criteria) this;
		}

		public Criteria andFILE_DTL_SEQ_IDLessThanOrEqualTo(BigDecimal value) {
			addCriterion("FILE_DTL_SEQ_ID <=", value, "FILE_DTL_SEQ_ID");
			return (Criteria) this;
		}

		public Criteria andFILE_DTL_SEQ_IDIn(List<BigDecimal> values) {
			addCriterion("FILE_DTL_SEQ_ID in", values, "FILE_DTL_SEQ_ID");
			return (Criteria) this;
		}

		public Criteria andFILE_DTL_SEQ_IDNotIn(List<BigDecimal> values) {
			addCriterion("FILE_DTL_SEQ_ID not in", values, "FILE_DTL_SEQ_ID");
			return (Criteria) this;
		}

		public Criteria andFILE_DTL_SEQ_IDBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("FILE_DTL_SEQ_ID between", value1, value2, "FILE_DTL_SEQ_ID");
			return (Criteria) this;
		}

		public Criteria andFILE_DTL_SEQ_IDNotBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("FILE_DTL_SEQ_ID not between", value1, value2, "FILE_DTL_SEQ_ID");
			return (Criteria) this;
		}

		public Criteria andTYPE_PATHIsNull() {
			addCriterion("TYPE_PATH is null");
			return (Criteria) this;
		}

		public Criteria andTYPE_PATHIsNotNull() {
			addCriterion("TYPE_PATH is not null");
			return (Criteria) this;
		}

		public Criteria andTYPE_PATHEqualTo(String value) {
			addCriterion("TYPE_PATH =", value, "TYPE_PATH");
			return (Criteria) this;
		}

		public Criteria andTYPE_PATHNotEqualTo(String value) {
			addCriterion("TYPE_PATH <>", value, "TYPE_PATH");
			return (Criteria) this;
		}

		public Criteria andTYPE_PATHGreaterThan(String value) {
			addCriterion("TYPE_PATH >", value, "TYPE_PATH");
			return (Criteria) this;
		}

		public Criteria andTYPE_PATHGreaterThanOrEqualTo(String value) {
			addCriterion("TYPE_PATH >=", value, "TYPE_PATH");
			return (Criteria) this;
		}

		public Criteria andTYPE_PATHLessThan(String value) {
			addCriterion("TYPE_PATH <", value, "TYPE_PATH");
			return (Criteria) this;
		}

		public Criteria andTYPE_PATHLessThanOrEqualTo(String value) {
			addCriterion("TYPE_PATH <=", value, "TYPE_PATH");
			return (Criteria) this;
		}

		public Criteria andTYPE_PATHLike(String value) {
			addCriterion("TYPE_PATH like", value, "TYPE_PATH");
			return (Criteria) this;
		}

		public Criteria andTYPE_PATHNotLike(String value) {
			addCriterion("TYPE_PATH not like", value, "TYPE_PATH");
			return (Criteria) this;
		}

		public Criteria andTYPE_PATHIn(List<String> values) {
			addCriterion("TYPE_PATH in", values, "TYPE_PATH");
			return (Criteria) this;
		}

		public Criteria andTYPE_PATHNotIn(List<String> values) {
			addCriterion("TYPE_PATH not in", values, "TYPE_PATH");
			return (Criteria) this;
		}

		public Criteria andTYPE_PATHBetween(String value1, String value2) {
			addCriterion("TYPE_PATH between", value1, value2, "TYPE_PATH");
			return (Criteria) this;
		}

		public Criteria andTYPE_PATHNotBetween(String value1, String value2) {
			addCriterion("TYPE_PATH not between", value1, value2, "TYPE_PATH");
			return (Criteria) this;
		}

		public Criteria andDOC_NOIsNull() {
			addCriterion("DOC_NO is null");
			return (Criteria) this;
		}

		public Criteria andDOC_NOIsNotNull() {
			addCriterion("DOC_NO is not null");
			return (Criteria) this;
		}

		public Criteria andDOC_NOEqualTo(String value) {
			addCriterion("DOC_NO =", value, "DOC_NO");
			return (Criteria) this;
		}

		public Criteria andDOC_NONotEqualTo(String value) {
			addCriterion("DOC_NO <>", value, "DOC_NO");
			return (Criteria) this;
		}

		public Criteria andDOC_NOGreaterThan(String value) {
			addCriterion("DOC_NO >", value, "DOC_NO");
			return (Criteria) this;
		}

		public Criteria andDOC_NOGreaterThanOrEqualTo(String value) {
			addCriterion("DOC_NO >=", value, "DOC_NO");
			return (Criteria) this;
		}

		public Criteria andDOC_NOLessThan(String value) {
			addCriterion("DOC_NO <", value, "DOC_NO");
			return (Criteria) this;
		}

		public Criteria andDOC_NOLessThanOrEqualTo(String value) {
			addCriterion("DOC_NO <=", value, "DOC_NO");
			return (Criteria) this;
		}

		public Criteria andDOC_NOLike(String value) {
			addCriterion("DOC_NO like", value, "DOC_NO");
			return (Criteria) this;
		}

		public Criteria andDOC_NONotLike(String value) {
			addCriterion("DOC_NO not like", value, "DOC_NO");
			return (Criteria) this;
		}

		public Criteria andDOC_NOIn(List<String> values) {
			addCriterion("DOC_NO in", values, "DOC_NO");
			return (Criteria) this;
		}

		public Criteria andDOC_NONotIn(List<String> values) {
			addCriterion("DOC_NO not in", values, "DOC_NO");
			return (Criteria) this;
		}

		public Criteria andDOC_NOBetween(String value1, String value2) {
			addCriterion("DOC_NO between", value1, value2, "DOC_NO");
			return (Criteria) this;
		}

		public Criteria andDOC_NONotBetween(String value1, String value2) {
			addCriterion("DOC_NO not between", value1, value2, "DOC_NO");
			return (Criteria) this;
		}

		public Criteria andFILE_NAMEIsNull() {
			addCriterion("FILE_NAME is null");
			return (Criteria) this;
		}

		public Criteria andFILE_NAMEIsNotNull() {
			addCriterion("FILE_NAME is not null");
			return (Criteria) this;
		}

		public Criteria andFILE_NAMEEqualTo(String value) {
			addCriterion("FILE_NAME =", value, "FILE_NAME");
			return (Criteria) this;
		}

		public Criteria andFILE_NAMENotEqualTo(String value) {
			addCriterion("FILE_NAME <>", value, "FILE_NAME");
			return (Criteria) this;
		}

		public Criteria andFILE_NAMEGreaterThan(String value) {
			addCriterion("FILE_NAME >", value, "FILE_NAME");
			return (Criteria) this;
		}

		public Criteria andFILE_NAMEGreaterThanOrEqualTo(String value) {
			addCriterion("FILE_NAME >=", value, "FILE_NAME");
			return (Criteria) this;
		}

		public Criteria andFILE_NAMELessThan(String value) {
			addCriterion("FILE_NAME <", value, "FILE_NAME");
			return (Criteria) this;
		}

		public Criteria andFILE_NAMELessThanOrEqualTo(String value) {
			addCriterion("FILE_NAME <=", value, "FILE_NAME");
			return (Criteria) this;
		}

		public Criteria andFILE_NAMELike(String value) {
			addCriterion("FILE_NAME like", value, "FILE_NAME");
			return (Criteria) this;
		}

		public Criteria andFILE_NAMENotLike(String value) {
			addCriterion("FILE_NAME not like", value, "FILE_NAME");
			return (Criteria) this;
		}

		public Criteria andFILE_NAMEIn(List<String> values) {
			addCriterion("FILE_NAME in", values, "FILE_NAME");
			return (Criteria) this;
		}

		public Criteria andFILE_NAMENotIn(List<String> values) {
			addCriterion("FILE_NAME not in", values, "FILE_NAME");
			return (Criteria) this;
		}

		public Criteria andFILE_NAMEBetween(String value1, String value2) {
			addCriterion("FILE_NAME between", value1, value2, "FILE_NAME");
			return (Criteria) this;
		}

		public Criteria andFILE_NAMENotBetween(String value1, String value2) {
			addCriterion("FILE_NAME not between", value1, value2, "FILE_NAME");
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

		public Criteria andFILE_TYPEIsNull() {
			addCriterion("FILE_TYPE is null");
			return (Criteria) this;
		}

		public Criteria andFILE_TYPEIsNotNull() {
			addCriterion("FILE_TYPE is not null");
			return (Criteria) this;
		}

		public Criteria andFILE_TYPEEqualTo(String value) {
			addCriterion("FILE_TYPE =", value, "FILE_TYPE");
			return (Criteria) this;
		}

		public Criteria andFILE_TYPENotEqualTo(String value) {
			addCriterion("FILE_TYPE <>", value, "FILE_TYPE");
			return (Criteria) this;
		}

		public Criteria andFILE_TYPEGreaterThan(String value) {
			addCriterion("FILE_TYPE >", value, "FILE_TYPE");
			return (Criteria) this;
		}

		public Criteria andFILE_TYPEGreaterThanOrEqualTo(String value) {
			addCriterion("FILE_TYPE >=", value, "FILE_TYPE");
			return (Criteria) this;
		}

		public Criteria andFILE_TYPELessThan(String value) {
			addCriterion("FILE_TYPE <", value, "FILE_TYPE");
			return (Criteria) this;
		}

		public Criteria andFILE_TYPELessThanOrEqualTo(String value) {
			addCriterion("FILE_TYPE <=", value, "FILE_TYPE");
			return (Criteria) this;
		}

		public Criteria andFILE_TYPELike(String value) {
			addCriterion("FILE_TYPE like", value, "FILE_TYPE");
			return (Criteria) this;
		}

		public Criteria andFILE_TYPENotLike(String value) {
			addCriterion("FILE_TYPE not like", value, "FILE_TYPE");
			return (Criteria) this;
		}

		public Criteria andFILE_TYPEIn(List<String> values) {
			addCriterion("FILE_TYPE in", values, "FILE_TYPE");
			return (Criteria) this;
		}

		public Criteria andFILE_TYPENotIn(List<String> values) {
			addCriterion("FILE_TYPE not in", values, "FILE_TYPE");
			return (Criteria) this;
		}

		public Criteria andFILE_TYPEBetween(String value1, String value2) {
			addCriterion("FILE_TYPE between", value1, value2, "FILE_TYPE");
			return (Criteria) this;
		}

		public Criteria andFILE_TYPENotBetween(String value1, String value2) {
			addCriterion("FILE_TYPE not between", value1, value2, "FILE_TYPE");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table TB_COM_FILE_DTL
	 * @mbggenerated  Thu Sep 04 17:43:10 CST 2014
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

	/**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table TB_COM_FILE_DTL
     *
     * @mbggenerated do_not_delete_during_merge Thu Sep 04 14:59:29 CST 2014
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}