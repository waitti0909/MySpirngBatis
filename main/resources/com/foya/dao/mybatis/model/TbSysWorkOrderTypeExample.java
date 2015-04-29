package com.foya.dao.mybatis.model;

import java.util.ArrayList;
import java.util.List;

public class TbSysWorkOrderTypeExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table TB_SYS_WORK_ORDER_TYPE
	 * @mbggenerated  Mon Jan 05 19:58:38 CST 2015
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table TB_SYS_WORK_ORDER_TYPE
	 * @mbggenerated  Mon Jan 05 19:58:38 CST 2015
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table TB_SYS_WORK_ORDER_TYPE
	 * @mbggenerated  Mon Jan 05 19:58:38 CST 2015
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_SYS_WORK_ORDER_TYPE
	 * @mbggenerated  Mon Jan 05 19:58:38 CST 2015
	 */
	public TbSysWorkOrderTypeExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_SYS_WORK_ORDER_TYPE
	 * @mbggenerated  Mon Jan 05 19:58:38 CST 2015
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_SYS_WORK_ORDER_TYPE
	 * @mbggenerated  Mon Jan 05 19:58:38 CST 2015
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_SYS_WORK_ORDER_TYPE
	 * @mbggenerated  Mon Jan 05 19:58:38 CST 2015
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_SYS_WORK_ORDER_TYPE
	 * @mbggenerated  Mon Jan 05 19:58:38 CST 2015
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_SYS_WORK_ORDER_TYPE
	 * @mbggenerated  Mon Jan 05 19:58:38 CST 2015
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_SYS_WORK_ORDER_TYPE
	 * @mbggenerated  Mon Jan 05 19:58:38 CST 2015
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_SYS_WORK_ORDER_TYPE
	 * @mbggenerated  Mon Jan 05 19:58:38 CST 2015
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_SYS_WORK_ORDER_TYPE
	 * @mbggenerated  Mon Jan 05 19:58:38 CST 2015
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_SYS_WORK_ORDER_TYPE
	 * @mbggenerated  Mon Jan 05 19:58:38 CST 2015
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_SYS_WORK_ORDER_TYPE
	 * @mbggenerated  Mon Jan 05 19:58:38 CST 2015
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table TB_SYS_WORK_ORDER_TYPE
	 * @mbggenerated  Mon Jan 05 19:58:38 CST 2015
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

		public Criteria andWK_TYPE_IDIsNull() {
			addCriterion("WK_TYPE_ID is null");
			return (Criteria) this;
		}

		public Criteria andWK_TYPE_IDIsNotNull() {
			addCriterion("WK_TYPE_ID is not null");
			return (Criteria) this;
		}

		public Criteria andWK_TYPE_IDEqualTo(String value) {
			addCriterion("WK_TYPE_ID =", value, "WK_TYPE_ID");
			return (Criteria) this;
		}

		public Criteria andWK_TYPE_IDNotEqualTo(String value) {
			addCriterion("WK_TYPE_ID <>", value, "WK_TYPE_ID");
			return (Criteria) this;
		}

		public Criteria andWK_TYPE_IDGreaterThan(String value) {
			addCriterion("WK_TYPE_ID >", value, "WK_TYPE_ID");
			return (Criteria) this;
		}

		public Criteria andWK_TYPE_IDGreaterThanOrEqualTo(String value) {
			addCriterion("WK_TYPE_ID >=", value, "WK_TYPE_ID");
			return (Criteria) this;
		}

		public Criteria andWK_TYPE_IDLessThan(String value) {
			addCriterion("WK_TYPE_ID <", value, "WK_TYPE_ID");
			return (Criteria) this;
		}

		public Criteria andWK_TYPE_IDLessThanOrEqualTo(String value) {
			addCriterion("WK_TYPE_ID <=", value, "WK_TYPE_ID");
			return (Criteria) this;
		}

		public Criteria andWK_TYPE_IDLike(String value) {
			addCriterion("WK_TYPE_ID like", value, "WK_TYPE_ID");
			return (Criteria) this;
		}

		public Criteria andWK_TYPE_IDNotLike(String value) {
			addCriterion("WK_TYPE_ID not like", value, "WK_TYPE_ID");
			return (Criteria) this;
		}

		public Criteria andWK_TYPE_IDIn(List<String> values) {
			addCriterion("WK_TYPE_ID in", values, "WK_TYPE_ID");
			return (Criteria) this;
		}

		public Criteria andWK_TYPE_IDNotIn(List<String> values) {
			addCriterion("WK_TYPE_ID not in", values, "WK_TYPE_ID");
			return (Criteria) this;
		}

		public Criteria andWK_TYPE_IDBetween(String value1, String value2) {
			addCriterion("WK_TYPE_ID between", value1, value2, "WK_TYPE_ID");
			return (Criteria) this;
		}

		public Criteria andWK_TYPE_IDNotBetween(String value1, String value2) {
			addCriterion("WK_TYPE_ID not between", value1, value2, "WK_TYPE_ID");
			return (Criteria) this;
		}

		public Criteria andOD_TYPE_IDIsNull() {
			addCriterion("OD_TYPE_ID is null");
			return (Criteria) this;
		}

		public Criteria andOD_TYPE_IDIsNotNull() {
			addCriterion("OD_TYPE_ID is not null");
			return (Criteria) this;
		}

		public Criteria andOD_TYPE_IDEqualTo(String value) {
			addCriterion("OD_TYPE_ID =", value, "OD_TYPE_ID");
			return (Criteria) this;
		}

		public Criteria andOD_TYPE_IDNotEqualTo(String value) {
			addCriterion("OD_TYPE_ID <>", value, "OD_TYPE_ID");
			return (Criteria) this;
		}

		public Criteria andOD_TYPE_IDGreaterThan(String value) {
			addCriterion("OD_TYPE_ID >", value, "OD_TYPE_ID");
			return (Criteria) this;
		}

		public Criteria andOD_TYPE_IDGreaterThanOrEqualTo(String value) {
			addCriterion("OD_TYPE_ID >=", value, "OD_TYPE_ID");
			return (Criteria) this;
		}

		public Criteria andOD_TYPE_IDLessThan(String value) {
			addCriterion("OD_TYPE_ID <", value, "OD_TYPE_ID");
			return (Criteria) this;
		}

		public Criteria andOD_TYPE_IDLessThanOrEqualTo(String value) {
			addCriterion("OD_TYPE_ID <=", value, "OD_TYPE_ID");
			return (Criteria) this;
		}

		public Criteria andOD_TYPE_IDLike(String value) {
			addCriterion("OD_TYPE_ID like", value, "OD_TYPE_ID");
			return (Criteria) this;
		}

		public Criteria andOD_TYPE_IDNotLike(String value) {
			addCriterion("OD_TYPE_ID not like", value, "OD_TYPE_ID");
			return (Criteria) this;
		}

		public Criteria andOD_TYPE_IDIn(List<String> values) {
			addCriterion("OD_TYPE_ID in", values, "OD_TYPE_ID");
			return (Criteria) this;
		}

		public Criteria andOD_TYPE_IDNotIn(List<String> values) {
			addCriterion("OD_TYPE_ID not in", values, "OD_TYPE_ID");
			return (Criteria) this;
		}

		public Criteria andOD_TYPE_IDBetween(String value1, String value2) {
			addCriterion("OD_TYPE_ID between", value1, value2, "OD_TYPE_ID");
			return (Criteria) this;
		}

		public Criteria andOD_TYPE_IDNotBetween(String value1, String value2) {
			addCriterion("OD_TYPE_ID not between", value1, value2, "OD_TYPE_ID");
			return (Criteria) this;
		}

		public Criteria andOD_SEQIsNull() {
			addCriterion("OD_SEQ is null");
			return (Criteria) this;
		}

		public Criteria andOD_SEQIsNotNull() {
			addCriterion("OD_SEQ is not null");
			return (Criteria) this;
		}

		public Criteria andOD_SEQEqualTo(Integer value) {
			addCriterion("OD_SEQ =", value, "OD_SEQ");
			return (Criteria) this;
		}

		public Criteria andOD_SEQNotEqualTo(Integer value) {
			addCriterion("OD_SEQ <>", value, "OD_SEQ");
			return (Criteria) this;
		}

		public Criteria andOD_SEQGreaterThan(Integer value) {
			addCriterion("OD_SEQ >", value, "OD_SEQ");
			return (Criteria) this;
		}

		public Criteria andOD_SEQGreaterThanOrEqualTo(Integer value) {
			addCriterion("OD_SEQ >=", value, "OD_SEQ");
			return (Criteria) this;
		}

		public Criteria andOD_SEQLessThan(Integer value) {
			addCriterion("OD_SEQ <", value, "OD_SEQ");
			return (Criteria) this;
		}

		public Criteria andOD_SEQLessThanOrEqualTo(Integer value) {
			addCriterion("OD_SEQ <=", value, "OD_SEQ");
			return (Criteria) this;
		}

		public Criteria andOD_SEQIn(List<Integer> values) {
			addCriterion("OD_SEQ in", values, "OD_SEQ");
			return (Criteria) this;
		}

		public Criteria andOD_SEQNotIn(List<Integer> values) {
			addCriterion("OD_SEQ not in", values, "OD_SEQ");
			return (Criteria) this;
		}

		public Criteria andOD_SEQBetween(Integer value1, Integer value2) {
			addCriterion("OD_SEQ between", value1, value2, "OD_SEQ");
			return (Criteria) this;
		}

		public Criteria andOD_SEQNotBetween(Integer value1, Integer value2) {
			addCriterion("OD_SEQ not between", value1, value2, "OD_SEQ");
			return (Criteria) this;
		}

		public Criteria andOD_SORTIsNull() {
			addCriterion("OD_SORT is null");
			return (Criteria) this;
		}

		public Criteria andOD_SORTIsNotNull() {
			addCriterion("OD_SORT is not null");
			return (Criteria) this;
		}

		public Criteria andOD_SORTEqualTo(Integer value) {
			addCriterion("OD_SORT =", value, "OD_SORT");
			return (Criteria) this;
		}

		public Criteria andOD_SORTNotEqualTo(Integer value) {
			addCriterion("OD_SORT <>", value, "OD_SORT");
			return (Criteria) this;
		}

		public Criteria andOD_SORTGreaterThan(Integer value) {
			addCriterion("OD_SORT >", value, "OD_SORT");
			return (Criteria) this;
		}

		public Criteria andOD_SORTGreaterThanOrEqualTo(Integer value) {
			addCriterion("OD_SORT >=", value, "OD_SORT");
			return (Criteria) this;
		}

		public Criteria andOD_SORTLessThan(Integer value) {
			addCriterion("OD_SORT <", value, "OD_SORT");
			return (Criteria) this;
		}

		public Criteria andOD_SORTLessThanOrEqualTo(Integer value) {
			addCriterion("OD_SORT <=", value, "OD_SORT");
			return (Criteria) this;
		}

		public Criteria andOD_SORTIn(List<Integer> values) {
			addCriterion("OD_SORT in", values, "OD_SORT");
			return (Criteria) this;
		}

		public Criteria andOD_SORTNotIn(List<Integer> values) {
			addCriterion("OD_SORT not in", values, "OD_SORT");
			return (Criteria) this;
		}

		public Criteria andOD_SORTBetween(Integer value1, Integer value2) {
			addCriterion("OD_SORT between", value1, value2, "OD_SORT");
			return (Criteria) this;
		}

		public Criteria andOD_SORTNotBetween(Integer value1, Integer value2) {
			addCriterion("OD_SORT not between", value1, value2, "OD_SORT");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table TB_SYS_WORK_ORDER_TYPE
	 * @mbggenerated  Mon Jan 05 19:58:38 CST 2015
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
     * This class corresponds to the database table TB_SYS_WORK_ORDER_TYPE
     *
     * @mbggenerated do_not_delete_during_merge Tue Nov 04 16:24:33 CST 2014
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}