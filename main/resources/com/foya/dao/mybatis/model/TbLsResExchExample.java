package com.foya.dao.mybatis.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TbLsResExchExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table TB_LS_RES_EXCH
	 * @mbggenerated  Tue Apr 07 10:25:19 CST 2015
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table TB_LS_RES_EXCH
	 * @mbggenerated  Tue Apr 07 10:25:19 CST 2015
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table TB_LS_RES_EXCH
	 * @mbggenerated  Tue Apr 07 10:25:19 CST 2015
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_RES_EXCH
	 * @mbggenerated  Tue Apr 07 10:25:19 CST 2015
	 */
	public TbLsResExchExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_RES_EXCH
	 * @mbggenerated  Tue Apr 07 10:25:19 CST 2015
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_RES_EXCH
	 * @mbggenerated  Tue Apr 07 10:25:19 CST 2015
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_RES_EXCH
	 * @mbggenerated  Tue Apr 07 10:25:19 CST 2015
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_RES_EXCH
	 * @mbggenerated  Tue Apr 07 10:25:19 CST 2015
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_RES_EXCH
	 * @mbggenerated  Tue Apr 07 10:25:19 CST 2015
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_RES_EXCH
	 * @mbggenerated  Tue Apr 07 10:25:19 CST 2015
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_RES_EXCH
	 * @mbggenerated  Tue Apr 07 10:25:19 CST 2015
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_RES_EXCH
	 * @mbggenerated  Tue Apr 07 10:25:19 CST 2015
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_RES_EXCH
	 * @mbggenerated  Tue Apr 07 10:25:19 CST 2015
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_RES_EXCH
	 * @mbggenerated  Tue Apr 07 10:25:19 CST 2015
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table TB_LS_RES_EXCH
	 * @mbggenerated  Tue Apr 07 10:25:19 CST 2015
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

		public Criteria andLOCATION_IDIsNull() {
			addCriterion("LOCATION_ID is null");
			return (Criteria) this;
		}

		public Criteria andLOCATION_IDIsNotNull() {
			addCriterion("LOCATION_ID is not null");
			return (Criteria) this;
		}

		public Criteria andLOCATION_IDEqualTo(String value) {
			addCriterion("LOCATION_ID =", value, "LOCATION_ID");
			return (Criteria) this;
		}

		public Criteria andLOCATION_IDNotEqualTo(String value) {
			addCriterion("LOCATION_ID <>", value, "LOCATION_ID");
			return (Criteria) this;
		}

		public Criteria andLOCATION_IDGreaterThan(String value) {
			addCriterion("LOCATION_ID >", value, "LOCATION_ID");
			return (Criteria) this;
		}

		public Criteria andLOCATION_IDGreaterThanOrEqualTo(String value) {
			addCriterion("LOCATION_ID >=", value, "LOCATION_ID");
			return (Criteria) this;
		}

		public Criteria andLOCATION_IDLessThan(String value) {
			addCriterion("LOCATION_ID <", value, "LOCATION_ID");
			return (Criteria) this;
		}

		public Criteria andLOCATION_IDLessThanOrEqualTo(String value) {
			addCriterion("LOCATION_ID <=", value, "LOCATION_ID");
			return (Criteria) this;
		}

		public Criteria andLOCATION_IDLike(String value) {
			addCriterion("LOCATION_ID like", value, "LOCATION_ID");
			return (Criteria) this;
		}

		public Criteria andLOCATION_IDNotLike(String value) {
			addCriterion("LOCATION_ID not like", value, "LOCATION_ID");
			return (Criteria) this;
		}

		public Criteria andLOCATION_IDIn(List<String> values) {
			addCriterion("LOCATION_ID in", values, "LOCATION_ID");
			return (Criteria) this;
		}

		public Criteria andLOCATION_IDNotIn(List<String> values) {
			addCriterion("LOCATION_ID not in", values, "LOCATION_ID");
			return (Criteria) this;
		}

		public Criteria andLOCATION_IDBetween(String value1, String value2) {
			addCriterion("LOCATION_ID between", value1, value2, "LOCATION_ID");
			return (Criteria) this;
		}

		public Criteria andLOCATION_IDNotBetween(String value1, String value2) {
			addCriterion("LOCATION_ID not between", value1, value2,
					"LOCATION_ID");
			return (Criteria) this;
		}

		public Criteria andEXCH_LOC_IDIsNull() {
			addCriterion("EXCH_LOC_ID is null");
			return (Criteria) this;
		}

		public Criteria andEXCH_LOC_IDIsNotNull() {
			addCriterion("EXCH_LOC_ID is not null");
			return (Criteria) this;
		}

		public Criteria andEXCH_LOC_IDEqualTo(String value) {
			addCriterion("EXCH_LOC_ID =", value, "EXCH_LOC_ID");
			return (Criteria) this;
		}

		public Criteria andEXCH_LOC_IDNotEqualTo(String value) {
			addCriterion("EXCH_LOC_ID <>", value, "EXCH_LOC_ID");
			return (Criteria) this;
		}

		public Criteria andEXCH_LOC_IDGreaterThan(String value) {
			addCriterion("EXCH_LOC_ID >", value, "EXCH_LOC_ID");
			return (Criteria) this;
		}

		public Criteria andEXCH_LOC_IDGreaterThanOrEqualTo(String value) {
			addCriterion("EXCH_LOC_ID >=", value, "EXCH_LOC_ID");
			return (Criteria) this;
		}

		public Criteria andEXCH_LOC_IDLessThan(String value) {
			addCriterion("EXCH_LOC_ID <", value, "EXCH_LOC_ID");
			return (Criteria) this;
		}

		public Criteria andEXCH_LOC_IDLessThanOrEqualTo(String value) {
			addCriterion("EXCH_LOC_ID <=", value, "EXCH_LOC_ID");
			return (Criteria) this;
		}

		public Criteria andEXCH_LOC_IDLike(String value) {
			addCriterion("EXCH_LOC_ID like", value, "EXCH_LOC_ID");
			return (Criteria) this;
		}

		public Criteria andEXCH_LOC_IDNotLike(String value) {
			addCriterion("EXCH_LOC_ID not like", value, "EXCH_LOC_ID");
			return (Criteria) this;
		}

		public Criteria andEXCH_LOC_IDIn(List<String> values) {
			addCriterion("EXCH_LOC_ID in", values, "EXCH_LOC_ID");
			return (Criteria) this;
		}

		public Criteria andEXCH_LOC_IDNotIn(List<String> values) {
			addCriterion("EXCH_LOC_ID not in", values, "EXCH_LOC_ID");
			return (Criteria) this;
		}

		public Criteria andEXCH_LOC_IDBetween(String value1, String value2) {
			addCriterion("EXCH_LOC_ID between", value1, value2, "EXCH_LOC_ID");
			return (Criteria) this;
		}

		public Criteria andEXCH_LOC_IDNotBetween(String value1, String value2) {
			addCriterion("EXCH_LOC_ID not between", value1, value2,
					"EXCH_LOC_ID");
			return (Criteria) this;
		}

		public Criteria andEXCH_DESCIsNull() {
			addCriterion("EXCH_DESC is null");
			return (Criteria) this;
		}

		public Criteria andEXCH_DESCIsNotNull() {
			addCriterion("EXCH_DESC is not null");
			return (Criteria) this;
		}

		public Criteria andEXCH_DESCEqualTo(String value) {
			addCriterion("EXCH_DESC =", value, "EXCH_DESC");
			return (Criteria) this;
		}

		public Criteria andEXCH_DESCNotEqualTo(String value) {
			addCriterion("EXCH_DESC <>", value, "EXCH_DESC");
			return (Criteria) this;
		}

		public Criteria andEXCH_DESCGreaterThan(String value) {
			addCriterion("EXCH_DESC >", value, "EXCH_DESC");
			return (Criteria) this;
		}

		public Criteria andEXCH_DESCGreaterThanOrEqualTo(String value) {
			addCriterion("EXCH_DESC >=", value, "EXCH_DESC");
			return (Criteria) this;
		}

		public Criteria andEXCH_DESCLessThan(String value) {
			addCriterion("EXCH_DESC <", value, "EXCH_DESC");
			return (Criteria) this;
		}

		public Criteria andEXCH_DESCLessThanOrEqualTo(String value) {
			addCriterion("EXCH_DESC <=", value, "EXCH_DESC");
			return (Criteria) this;
		}

		public Criteria andEXCH_DESCLike(String value) {
			addCriterion("EXCH_DESC like", value, "EXCH_DESC");
			return (Criteria) this;
		}

		public Criteria andEXCH_DESCNotLike(String value) {
			addCriterion("EXCH_DESC not like", value, "EXCH_DESC");
			return (Criteria) this;
		}

		public Criteria andEXCH_DESCIn(List<String> values) {
			addCriterion("EXCH_DESC in", values, "EXCH_DESC");
			return (Criteria) this;
		}

		public Criteria andEXCH_DESCNotIn(List<String> values) {
			addCriterion("EXCH_DESC not in", values, "EXCH_DESC");
			return (Criteria) this;
		}

		public Criteria andEXCH_DESCBetween(String value1, String value2) {
			addCriterion("EXCH_DESC between", value1, value2, "EXCH_DESC");
			return (Criteria) this;
		}

		public Criteria andEXCH_DESCNotBetween(String value1, String value2) {
			addCriterion("EXCH_DESC not between", value1, value2, "EXCH_DESC");
			return (Criteria) this;
		}

		public Criteria andEQUIP_2GIsNull() {
			addCriterion("EQUIP_2G is null");
			return (Criteria) this;
		}

		public Criteria andEQUIP_2GIsNotNull() {
			addCriterion("EQUIP_2G is not null");
			return (Criteria) this;
		}

		public Criteria andEQUIP_2GEqualTo(BigDecimal value) {
			addCriterion("EQUIP_2G =", value, "EQUIP_2G");
			return (Criteria) this;
		}

		public Criteria andEQUIP_2GNotEqualTo(BigDecimal value) {
			addCriterion("EQUIP_2G <>", value, "EQUIP_2G");
			return (Criteria) this;
		}

		public Criteria andEQUIP_2GGreaterThan(BigDecimal value) {
			addCriterion("EQUIP_2G >", value, "EQUIP_2G");
			return (Criteria) this;
		}

		public Criteria andEQUIP_2GGreaterThanOrEqualTo(BigDecimal value) {
			addCriterion("EQUIP_2G >=", value, "EQUIP_2G");
			return (Criteria) this;
		}

		public Criteria andEQUIP_2GLessThan(BigDecimal value) {
			addCriterion("EQUIP_2G <", value, "EQUIP_2G");
			return (Criteria) this;
		}

		public Criteria andEQUIP_2GLessThanOrEqualTo(BigDecimal value) {
			addCriterion("EQUIP_2G <=", value, "EQUIP_2G");
			return (Criteria) this;
		}

		public Criteria andEQUIP_2GIn(List<BigDecimal> values) {
			addCriterion("EQUIP_2G in", values, "EQUIP_2G");
			return (Criteria) this;
		}

		public Criteria andEQUIP_2GNotIn(List<BigDecimal> values) {
			addCriterion("EQUIP_2G not in", values, "EQUIP_2G");
			return (Criteria) this;
		}

		public Criteria andEQUIP_2GBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("EQUIP_2G between", value1, value2, "EQUIP_2G");
			return (Criteria) this;
		}

		public Criteria andEQUIP_2GNotBetween(BigDecimal value1,
				BigDecimal value2) {
			addCriterion("EQUIP_2G not between", value1, value2, "EQUIP_2G");
			return (Criteria) this;
		}

		public Criteria andEQUIP_3GIsNull() {
			addCriterion("EQUIP_3G is null");
			return (Criteria) this;
		}

		public Criteria andEQUIP_3GIsNotNull() {
			addCriterion("EQUIP_3G is not null");
			return (Criteria) this;
		}

		public Criteria andEQUIP_3GEqualTo(BigDecimal value) {
			addCriterion("EQUIP_3G =", value, "EQUIP_3G");
			return (Criteria) this;
		}

		public Criteria andEQUIP_3GNotEqualTo(BigDecimal value) {
			addCriterion("EQUIP_3G <>", value, "EQUIP_3G");
			return (Criteria) this;
		}

		public Criteria andEQUIP_3GGreaterThan(BigDecimal value) {
			addCriterion("EQUIP_3G >", value, "EQUIP_3G");
			return (Criteria) this;
		}

		public Criteria andEQUIP_3GGreaterThanOrEqualTo(BigDecimal value) {
			addCriterion("EQUIP_3G >=", value, "EQUIP_3G");
			return (Criteria) this;
		}

		public Criteria andEQUIP_3GLessThan(BigDecimal value) {
			addCriterion("EQUIP_3G <", value, "EQUIP_3G");
			return (Criteria) this;
		}

		public Criteria andEQUIP_3GLessThanOrEqualTo(BigDecimal value) {
			addCriterion("EQUIP_3G <=", value, "EQUIP_3G");
			return (Criteria) this;
		}

		public Criteria andEQUIP_3GIn(List<BigDecimal> values) {
			addCriterion("EQUIP_3G in", values, "EQUIP_3G");
			return (Criteria) this;
		}

		public Criteria andEQUIP_3GNotIn(List<BigDecimal> values) {
			addCriterion("EQUIP_3G not in", values, "EQUIP_3G");
			return (Criteria) this;
		}

		public Criteria andEQUIP_3GBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("EQUIP_3G between", value1, value2, "EQUIP_3G");
			return (Criteria) this;
		}

		public Criteria andEQUIP_3GNotBetween(BigDecimal value1,
				BigDecimal value2) {
			addCriterion("EQUIP_3G not between", value1, value2, "EQUIP_3G");
			return (Criteria) this;
		}

		public Criteria andEQUIP_4GIsNull() {
			addCriterion("EQUIP_4G is null");
			return (Criteria) this;
		}

		public Criteria andEQUIP_4GIsNotNull() {
			addCriterion("EQUIP_4G is not null");
			return (Criteria) this;
		}

		public Criteria andEQUIP_4GEqualTo(BigDecimal value) {
			addCriterion("EQUIP_4G =", value, "EQUIP_4G");
			return (Criteria) this;
		}

		public Criteria andEQUIP_4GNotEqualTo(BigDecimal value) {
			addCriterion("EQUIP_4G <>", value, "EQUIP_4G");
			return (Criteria) this;
		}

		public Criteria andEQUIP_4GGreaterThan(BigDecimal value) {
			addCriterion("EQUIP_4G >", value, "EQUIP_4G");
			return (Criteria) this;
		}

		public Criteria andEQUIP_4GGreaterThanOrEqualTo(BigDecimal value) {
			addCriterion("EQUIP_4G >=", value, "EQUIP_4G");
			return (Criteria) this;
		}

		public Criteria andEQUIP_4GLessThan(BigDecimal value) {
			addCriterion("EQUIP_4G <", value, "EQUIP_4G");
			return (Criteria) this;
		}

		public Criteria andEQUIP_4GLessThanOrEqualTo(BigDecimal value) {
			addCriterion("EQUIP_4G <=", value, "EQUIP_4G");
			return (Criteria) this;
		}

		public Criteria andEQUIP_4GIn(List<BigDecimal> values) {
			addCriterion("EQUIP_4G in", values, "EQUIP_4G");
			return (Criteria) this;
		}

		public Criteria andEQUIP_4GNotIn(List<BigDecimal> values) {
			addCriterion("EQUIP_4G not in", values, "EQUIP_4G");
			return (Criteria) this;
		}

		public Criteria andEQUIP_4GBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("EQUIP_4G between", value1, value2, "EQUIP_4G");
			return (Criteria) this;
		}

		public Criteria andEQUIP_4GNotBetween(BigDecimal value1,
				BigDecimal value2) {
			addCriterion("EQUIP_4G not between", value1, value2, "EQUIP_4G");
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
	 * This class was generated by MyBatis Generator. This class corresponds to the database table TB_LS_RES_EXCH
	 * @mbggenerated  Tue Apr 07 10:25:19 CST 2015
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
     * This class corresponds to the database table TB_LS_RES_EXCH
     *
     * @mbggenerated do_not_delete_during_merge Thu Jan 29 20:11:29 CST 2015
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}