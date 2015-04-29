package com.foya.dao.mybatis.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TbLsAppExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table TB_LS_APP
	 * @mbggenerated  Fri Jan 30 14:30:17 GMT+08:00 2015
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table TB_LS_APP
	 * @mbggenerated  Fri Jan 30 14:30:17 GMT+08:00 2015
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table TB_LS_APP
	 * @mbggenerated  Fri Jan 30 14:30:17 GMT+08:00 2015
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_APP
	 * @mbggenerated  Fri Jan 30 14:30:17 GMT+08:00 2015
	 */
	public TbLsAppExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_APP
	 * @mbggenerated  Fri Jan 30 14:30:17 GMT+08:00 2015
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_APP
	 * @mbggenerated  Fri Jan 30 14:30:17 GMT+08:00 2015
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_APP
	 * @mbggenerated  Fri Jan 30 14:30:17 GMT+08:00 2015
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_APP
	 * @mbggenerated  Fri Jan 30 14:30:17 GMT+08:00 2015
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_APP
	 * @mbggenerated  Fri Jan 30 14:30:17 GMT+08:00 2015
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_APP
	 * @mbggenerated  Fri Jan 30 14:30:17 GMT+08:00 2015
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_APP
	 * @mbggenerated  Fri Jan 30 14:30:17 GMT+08:00 2015
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_APP
	 * @mbggenerated  Fri Jan 30 14:30:17 GMT+08:00 2015
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_APP
	 * @mbggenerated  Fri Jan 30 14:30:17 GMT+08:00 2015
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_APP
	 * @mbggenerated  Fri Jan 30 14:30:17 GMT+08:00 2015
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table TB_LS_APP
	 * @mbggenerated  Fri Jan 30 14:30:17 GMT+08:00 2015
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

		public Criteria andAPP_SEQIsNull() {
			addCriterion("APP_SEQ is null");
			return (Criteria) this;
		}

		public Criteria andAPP_SEQIsNotNull() {
			addCriterion("APP_SEQ is not null");
			return (Criteria) this;
		}

		public Criteria andAPP_SEQEqualTo(String value) {
			addCriterion("APP_SEQ =", value, "APP_SEQ");
			return (Criteria) this;
		}

		public Criteria andAPP_SEQNotEqualTo(String value) {
			addCriterion("APP_SEQ <>", value, "APP_SEQ");
			return (Criteria) this;
		}

		public Criteria andAPP_SEQGreaterThan(String value) {
			addCriterion("APP_SEQ >", value, "APP_SEQ");
			return (Criteria) this;
		}

		public Criteria andAPP_SEQGreaterThanOrEqualTo(String value) {
			addCriterion("APP_SEQ >=", value, "APP_SEQ");
			return (Criteria) this;
		}

		public Criteria andAPP_SEQLessThan(String value) {
			addCriterion("APP_SEQ <", value, "APP_SEQ");
			return (Criteria) this;
		}

		public Criteria andAPP_SEQLessThanOrEqualTo(String value) {
			addCriterion("APP_SEQ <=", value, "APP_SEQ");
			return (Criteria) this;
		}

		public Criteria andAPP_SEQLike(String value) {
			addCriterion("APP_SEQ like", value, "APP_SEQ");
			return (Criteria) this;
		}

		public Criteria andAPP_SEQNotLike(String value) {
			addCriterion("APP_SEQ not like", value, "APP_SEQ");
			return (Criteria) this;
		}

		public Criteria andAPP_SEQIn(List<String> values) {
			addCriterion("APP_SEQ in", values, "APP_SEQ");
			return (Criteria) this;
		}

		public Criteria andAPP_SEQNotIn(List<String> values) {
			addCriterion("APP_SEQ not in", values, "APP_SEQ");
			return (Criteria) this;
		}

		public Criteria andAPP_SEQBetween(String value1, String value2) {
			addCriterion("APP_SEQ between", value1, value2, "APP_SEQ");
			return (Criteria) this;
		}

		public Criteria andAPP_SEQNotBetween(String value1, String value2) {
			addCriterion("APP_SEQ not between", value1, value2, "APP_SEQ");
			return (Criteria) this;
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

		public Criteria andLS_TYPEIsNull() {
			addCriterion("LS_TYPE is null");
			return (Criteria) this;
		}

		public Criteria andLS_TYPEIsNotNull() {
			addCriterion("LS_TYPE is not null");
			return (Criteria) this;
		}

		public Criteria andLS_TYPEEqualTo(String value) {
			addCriterion("LS_TYPE =", value, "LS_TYPE");
			return (Criteria) this;
		}

		public Criteria andLS_TYPENotEqualTo(String value) {
			addCriterion("LS_TYPE <>", value, "LS_TYPE");
			return (Criteria) this;
		}

		public Criteria andLS_TYPEGreaterThan(String value) {
			addCriterion("LS_TYPE >", value, "LS_TYPE");
			return (Criteria) this;
		}

		public Criteria andLS_TYPEGreaterThanOrEqualTo(String value) {
			addCriterion("LS_TYPE >=", value, "LS_TYPE");
			return (Criteria) this;
		}

		public Criteria andLS_TYPELessThan(String value) {
			addCriterion("LS_TYPE <", value, "LS_TYPE");
			return (Criteria) this;
		}

		public Criteria andLS_TYPELessThanOrEqualTo(String value) {
			addCriterion("LS_TYPE <=", value, "LS_TYPE");
			return (Criteria) this;
		}

		public Criteria andLS_TYPELike(String value) {
			addCriterion("LS_TYPE like", value, "LS_TYPE");
			return (Criteria) this;
		}

		public Criteria andLS_TYPENotLike(String value) {
			addCriterion("LS_TYPE not like", value, "LS_TYPE");
			return (Criteria) this;
		}

		public Criteria andLS_TYPEIn(List<String> values) {
			addCriterion("LS_TYPE in", values, "LS_TYPE");
			return (Criteria) this;
		}

		public Criteria andLS_TYPENotIn(List<String> values) {
			addCriterion("LS_TYPE not in", values, "LS_TYPE");
			return (Criteria) this;
		}

		public Criteria andLS_TYPEBetween(String value1, String value2) {
			addCriterion("LS_TYPE between", value1, value2, "LS_TYPE");
			return (Criteria) this;
		}

		public Criteria andLS_TYPENotBetween(String value1, String value2) {
			addCriterion("LS_TYPE not between", value1, value2, "LS_TYPE");
			return (Criteria) this;
		}

		public Criteria andAPP_STATUSIsNull() {
			addCriterion("APP_STATUS is null");
			return (Criteria) this;
		}

		public Criteria andAPP_STATUSIsNotNull() {
			addCriterion("APP_STATUS is not null");
			return (Criteria) this;
		}

		public Criteria andAPP_STATUSEqualTo(String value) {
			addCriterion("APP_STATUS =", value, "APP_STATUS");
			return (Criteria) this;
		}

		public Criteria andAPP_STATUSNotEqualTo(String value) {
			addCriterion("APP_STATUS <>", value, "APP_STATUS");
			return (Criteria) this;
		}

		public Criteria andAPP_STATUSGreaterThan(String value) {
			addCriterion("APP_STATUS >", value, "APP_STATUS");
			return (Criteria) this;
		}

		public Criteria andAPP_STATUSGreaterThanOrEqualTo(String value) {
			addCriterion("APP_STATUS >=", value, "APP_STATUS");
			return (Criteria) this;
		}

		public Criteria andAPP_STATUSLessThan(String value) {
			addCriterion("APP_STATUS <", value, "APP_STATUS");
			return (Criteria) this;
		}

		public Criteria andAPP_STATUSLessThanOrEqualTo(String value) {
			addCriterion("APP_STATUS <=", value, "APP_STATUS");
			return (Criteria) this;
		}

		public Criteria andAPP_STATUSLike(String value) {
			addCriterion("APP_STATUS like", value, "APP_STATUS");
			return (Criteria) this;
		}

		public Criteria andAPP_STATUSNotLike(String value) {
			addCriterion("APP_STATUS not like", value, "APP_STATUS");
			return (Criteria) this;
		}

		public Criteria andAPP_STATUSIn(List<String> values) {
			addCriterion("APP_STATUS in", values, "APP_STATUS");
			return (Criteria) this;
		}

		public Criteria andAPP_STATUSNotIn(List<String> values) {
			addCriterion("APP_STATUS not in", values, "APP_STATUS");
			return (Criteria) this;
		}

		public Criteria andAPP_STATUSBetween(String value1, String value2) {
			addCriterion("APP_STATUS between", value1, value2, "APP_STATUS");
			return (Criteria) this;
		}

		public Criteria andAPP_STATUSNotBetween(String value1, String value2) {
			addCriterion("APP_STATUS not between", value1, value2, "APP_STATUS");
			return (Criteria) this;
		}

		public Criteria andADD_TYPEIsNull() {
			addCriterion("ADD_TYPE is null");
			return (Criteria) this;
		}

		public Criteria andADD_TYPEIsNotNull() {
			addCriterion("ADD_TYPE is not null");
			return (Criteria) this;
		}

		public Criteria andADD_TYPEEqualTo(String value) {
			addCriterion("ADD_TYPE =", value, "ADD_TYPE");
			return (Criteria) this;
		}

		public Criteria andADD_TYPENotEqualTo(String value) {
			addCriterion("ADD_TYPE <>", value, "ADD_TYPE");
			return (Criteria) this;
		}

		public Criteria andADD_TYPEGreaterThan(String value) {
			addCriterion("ADD_TYPE >", value, "ADD_TYPE");
			return (Criteria) this;
		}

		public Criteria andADD_TYPEGreaterThanOrEqualTo(String value) {
			addCriterion("ADD_TYPE >=", value, "ADD_TYPE");
			return (Criteria) this;
		}

		public Criteria andADD_TYPELessThan(String value) {
			addCriterion("ADD_TYPE <", value, "ADD_TYPE");
			return (Criteria) this;
		}

		public Criteria andADD_TYPELessThanOrEqualTo(String value) {
			addCriterion("ADD_TYPE <=", value, "ADD_TYPE");
			return (Criteria) this;
		}

		public Criteria andADD_TYPELike(String value) {
			addCriterion("ADD_TYPE like", value, "ADD_TYPE");
			return (Criteria) this;
		}

		public Criteria andADD_TYPENotLike(String value) {
			addCriterion("ADD_TYPE not like", value, "ADD_TYPE");
			return (Criteria) this;
		}

		public Criteria andADD_TYPEIn(List<String> values) {
			addCriterion("ADD_TYPE in", values, "ADD_TYPE");
			return (Criteria) this;
		}

		public Criteria andADD_TYPENotIn(List<String> values) {
			addCriterion("ADD_TYPE not in", values, "ADD_TYPE");
			return (Criteria) this;
		}

		public Criteria andADD_TYPEBetween(String value1, String value2) {
			addCriterion("ADD_TYPE between", value1, value2, "ADD_TYPE");
			return (Criteria) this;
		}

		public Criteria andADD_TYPENotBetween(String value1, String value2) {
			addCriterion("ADD_TYPE not between", value1, value2, "ADD_TYPE");
			return (Criteria) this;
		}

		public Criteria andADD_ITEMIsNull() {
			addCriterion("ADD_ITEM is null");
			return (Criteria) this;
		}

		public Criteria andADD_ITEMIsNotNull() {
			addCriterion("ADD_ITEM is not null");
			return (Criteria) this;
		}

		public Criteria andADD_ITEMEqualTo(String value) {
			addCriterion("ADD_ITEM =", value, "ADD_ITEM");
			return (Criteria) this;
		}

		public Criteria andADD_ITEMNotEqualTo(String value) {
			addCriterion("ADD_ITEM <>", value, "ADD_ITEM");
			return (Criteria) this;
		}

		public Criteria andADD_ITEMGreaterThan(String value) {
			addCriterion("ADD_ITEM >", value, "ADD_ITEM");
			return (Criteria) this;
		}

		public Criteria andADD_ITEMGreaterThanOrEqualTo(String value) {
			addCriterion("ADD_ITEM >=", value, "ADD_ITEM");
			return (Criteria) this;
		}

		public Criteria andADD_ITEMLessThan(String value) {
			addCriterion("ADD_ITEM <", value, "ADD_ITEM");
			return (Criteria) this;
		}

		public Criteria andADD_ITEMLessThanOrEqualTo(String value) {
			addCriterion("ADD_ITEM <=", value, "ADD_ITEM");
			return (Criteria) this;
		}

		public Criteria andADD_ITEMLike(String value) {
			addCriterion("ADD_ITEM like", value, "ADD_ITEM");
			return (Criteria) this;
		}

		public Criteria andADD_ITEMNotLike(String value) {
			addCriterion("ADD_ITEM not like", value, "ADD_ITEM");
			return (Criteria) this;
		}

		public Criteria andADD_ITEMIn(List<String> values) {
			addCriterion("ADD_ITEM in", values, "ADD_ITEM");
			return (Criteria) this;
		}

		public Criteria andADD_ITEMNotIn(List<String> values) {
			addCriterion("ADD_ITEM not in", values, "ADD_ITEM");
			return (Criteria) this;
		}

		public Criteria andADD_ITEMBetween(String value1, String value2) {
			addCriterion("ADD_ITEM between", value1, value2, "ADD_ITEM");
			return (Criteria) this;
		}

		public Criteria andADD_ITEMNotBetween(String value1, String value2) {
			addCriterion("ADD_ITEM not between", value1, value2, "ADD_ITEM");
			return (Criteria) this;
		}

		public Criteria andFORM_IDIsNull() {
			addCriterion("FORM_ID is null");
			return (Criteria) this;
		}

		public Criteria andFORM_IDIsNotNull() {
			addCriterion("FORM_ID is not null");
			return (Criteria) this;
		}

		public Criteria andFORM_IDEqualTo(String value) {
			addCriterion("FORM_ID =", value, "FORM_ID");
			return (Criteria) this;
		}

		public Criteria andFORM_IDNotEqualTo(String value) {
			addCriterion("FORM_ID <>", value, "FORM_ID");
			return (Criteria) this;
		}

		public Criteria andFORM_IDGreaterThan(String value) {
			addCriterion("FORM_ID >", value, "FORM_ID");
			return (Criteria) this;
		}

		public Criteria andFORM_IDGreaterThanOrEqualTo(String value) {
			addCriterion("FORM_ID >=", value, "FORM_ID");
			return (Criteria) this;
		}

		public Criteria andFORM_IDLessThan(String value) {
			addCriterion("FORM_ID <", value, "FORM_ID");
			return (Criteria) this;
		}

		public Criteria andFORM_IDLessThanOrEqualTo(String value) {
			addCriterion("FORM_ID <=", value, "FORM_ID");
			return (Criteria) this;
		}

		public Criteria andFORM_IDLike(String value) {
			addCriterion("FORM_ID like", value, "FORM_ID");
			return (Criteria) this;
		}

		public Criteria andFORM_IDNotLike(String value) {
			addCriterion("FORM_ID not like", value, "FORM_ID");
			return (Criteria) this;
		}

		public Criteria andFORM_IDIn(List<String> values) {
			addCriterion("FORM_ID in", values, "FORM_ID");
			return (Criteria) this;
		}

		public Criteria andFORM_IDNotIn(List<String> values) {
			addCriterion("FORM_ID not in", values, "FORM_ID");
			return (Criteria) this;
		}

		public Criteria andFORM_IDBetween(String value1, String value2) {
			addCriterion("FORM_ID between", value1, value2, "FORM_ID");
			return (Criteria) this;
		}

		public Criteria andFORM_IDNotBetween(String value1, String value2) {
			addCriterion("FORM_ID not between", value1, value2, "FORM_ID");
			return (Criteria) this;
		}

		public Criteria andAPP_DEPT_IDIsNull() {
			addCriterion("APP_DEPT_ID is null");
			return (Criteria) this;
		}

		public Criteria andAPP_DEPT_IDIsNotNull() {
			addCriterion("APP_DEPT_ID is not null");
			return (Criteria) this;
		}

		public Criteria andAPP_DEPT_IDEqualTo(String value) {
			addCriterion("APP_DEPT_ID =", value, "APP_DEPT_ID");
			return (Criteria) this;
		}

		public Criteria andAPP_DEPT_IDNotEqualTo(String value) {
			addCriterion("APP_DEPT_ID <>", value, "APP_DEPT_ID");
			return (Criteria) this;
		}

		public Criteria andAPP_DEPT_IDGreaterThan(String value) {
			addCriterion("APP_DEPT_ID >", value, "APP_DEPT_ID");
			return (Criteria) this;
		}

		public Criteria andAPP_DEPT_IDGreaterThanOrEqualTo(String value) {
			addCriterion("APP_DEPT_ID >=", value, "APP_DEPT_ID");
			return (Criteria) this;
		}

		public Criteria andAPP_DEPT_IDLessThan(String value) {
			addCriterion("APP_DEPT_ID <", value, "APP_DEPT_ID");
			return (Criteria) this;
		}

		public Criteria andAPP_DEPT_IDLessThanOrEqualTo(String value) {
			addCriterion("APP_DEPT_ID <=", value, "APP_DEPT_ID");
			return (Criteria) this;
		}

		public Criteria andAPP_DEPT_IDLike(String value) {
			addCriterion("APP_DEPT_ID like", value, "APP_DEPT_ID");
			return (Criteria) this;
		}

		public Criteria andAPP_DEPT_IDNotLike(String value) {
			addCriterion("APP_DEPT_ID not like", value, "APP_DEPT_ID");
			return (Criteria) this;
		}

		public Criteria andAPP_DEPT_IDIn(List<String> values) {
			addCriterion("APP_DEPT_ID in", values, "APP_DEPT_ID");
			return (Criteria) this;
		}

		public Criteria andAPP_DEPT_IDNotIn(List<String> values) {
			addCriterion("APP_DEPT_ID not in", values, "APP_DEPT_ID");
			return (Criteria) this;
		}

		public Criteria andAPP_DEPT_IDBetween(String value1, String value2) {
			addCriterion("APP_DEPT_ID between", value1, value2, "APP_DEPT_ID");
			return (Criteria) this;
		}

		public Criteria andAPP_DEPT_IDNotBetween(String value1, String value2) {
			addCriterion("APP_DEPT_ID not between", value1, value2,
					"APP_DEPT_ID");
			return (Criteria) this;
		}

		public Criteria andAPP_USERIsNull() {
			addCriterion("APP_USER is null");
			return (Criteria) this;
		}

		public Criteria andAPP_USERIsNotNull() {
			addCriterion("APP_USER is not null");
			return (Criteria) this;
		}

		public Criteria andAPP_USEREqualTo(String value) {
			addCriterion("APP_USER =", value, "APP_USER");
			return (Criteria) this;
		}

		public Criteria andAPP_USERNotEqualTo(String value) {
			addCriterion("APP_USER <>", value, "APP_USER");
			return (Criteria) this;
		}

		public Criteria andAPP_USERGreaterThan(String value) {
			addCriterion("APP_USER >", value, "APP_USER");
			return (Criteria) this;
		}

		public Criteria andAPP_USERGreaterThanOrEqualTo(String value) {
			addCriterion("APP_USER >=", value, "APP_USER");
			return (Criteria) this;
		}

		public Criteria andAPP_USERLessThan(String value) {
			addCriterion("APP_USER <", value, "APP_USER");
			return (Criteria) this;
		}

		public Criteria andAPP_USERLessThanOrEqualTo(String value) {
			addCriterion("APP_USER <=", value, "APP_USER");
			return (Criteria) this;
		}

		public Criteria andAPP_USERLike(String value) {
			addCriterion("APP_USER like", value, "APP_USER");
			return (Criteria) this;
		}

		public Criteria andAPP_USERNotLike(String value) {
			addCriterion("APP_USER not like", value, "APP_USER");
			return (Criteria) this;
		}

		public Criteria andAPP_USERIn(List<String> values) {
			addCriterion("APP_USER in", values, "APP_USER");
			return (Criteria) this;
		}

		public Criteria andAPP_USERNotIn(List<String> values) {
			addCriterion("APP_USER not in", values, "APP_USER");
			return (Criteria) this;
		}

		public Criteria andAPP_USERBetween(String value1, String value2) {
			addCriterion("APP_USER between", value1, value2, "APP_USER");
			return (Criteria) this;
		}

		public Criteria andAPP_USERNotBetween(String value1, String value2) {
			addCriterion("APP_USER not between", value1, value2, "APP_USER");
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
	 * This class was generated by MyBatis Generator. This class corresponds to the database table TB_LS_APP
	 * @mbggenerated  Fri Jan 30 14:30:17 GMT+08:00 2015
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
     * This class corresponds to the database table TB_LS_APP
     *
     * @mbggenerated do_not_delete_during_merge Wed Jan 21 14:39:20 CST 2015
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}