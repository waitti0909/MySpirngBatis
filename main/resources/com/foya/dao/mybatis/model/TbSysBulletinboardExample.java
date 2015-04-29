package com.foya.dao.mybatis.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TbSysBulletinboardExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table TB_SYS_BULLETINBOARD
	 * @mbggenerated  Thu Aug 21 09:53:31 CST 2014
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table TB_SYS_BULLETINBOARD
	 * @mbggenerated  Thu Aug 21 09:53:31 CST 2014
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table TB_SYS_BULLETINBOARD
	 * @mbggenerated  Thu Aug 21 09:53:31 CST 2014
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_SYS_BULLETINBOARD
	 * @mbggenerated  Thu Aug 21 09:53:31 CST 2014
	 */
	public TbSysBulletinboardExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_SYS_BULLETINBOARD
	 * @mbggenerated  Thu Aug 21 09:53:31 CST 2014
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_SYS_BULLETINBOARD
	 * @mbggenerated  Thu Aug 21 09:53:31 CST 2014
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_SYS_BULLETINBOARD
	 * @mbggenerated  Thu Aug 21 09:53:31 CST 2014
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_SYS_BULLETINBOARD
	 * @mbggenerated  Thu Aug 21 09:53:31 CST 2014
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_SYS_BULLETINBOARD
	 * @mbggenerated  Thu Aug 21 09:53:31 CST 2014
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_SYS_BULLETINBOARD
	 * @mbggenerated  Thu Aug 21 09:53:31 CST 2014
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_SYS_BULLETINBOARD
	 * @mbggenerated  Thu Aug 21 09:53:31 CST 2014
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_SYS_BULLETINBOARD
	 * @mbggenerated  Thu Aug 21 09:53:31 CST 2014
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_SYS_BULLETINBOARD
	 * @mbggenerated  Thu Aug 21 09:53:31 CST 2014
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_SYS_BULLETINBOARD
	 * @mbggenerated  Thu Aug 21 09:53:31 CST 2014
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table TB_SYS_BULLETINBOARD
	 * @mbggenerated  Thu Aug 21 09:53:31 CST 2014
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

		public Criteria andBULLETIN_IDIsNull() {
			addCriterion("BULLETIN_ID is null");
			return (Criteria) this;
		}

		public Criteria andBULLETIN_IDIsNotNull() {
			addCriterion("BULLETIN_ID is not null");
			return (Criteria) this;
		}

		public Criteria andBULLETIN_IDEqualTo(BigDecimal value) {
			addCriterion("BULLETIN_ID =", value, "BULLETIN_ID");
			return (Criteria) this;
		}

		public Criteria andBULLETIN_IDNotEqualTo(BigDecimal value) {
			addCriterion("BULLETIN_ID <>", value, "BULLETIN_ID");
			return (Criteria) this;
		}

		public Criteria andBULLETIN_IDGreaterThan(BigDecimal value) {
			addCriterion("BULLETIN_ID >", value, "BULLETIN_ID");
			return (Criteria) this;
		}

		public Criteria andBULLETIN_IDGreaterThanOrEqualTo(BigDecimal value) {
			addCriterion("BULLETIN_ID >=", value, "BULLETIN_ID");
			return (Criteria) this;
		}

		public Criteria andBULLETIN_IDLessThan(BigDecimal value) {
			addCriterion("BULLETIN_ID <", value, "BULLETIN_ID");
			return (Criteria) this;
		}

		public Criteria andBULLETIN_IDLessThanOrEqualTo(BigDecimal value) {
			addCriterion("BULLETIN_ID <=", value, "BULLETIN_ID");
			return (Criteria) this;
		}

		public Criteria andBULLETIN_IDIn(List<BigDecimal> values) {
			addCriterion("BULLETIN_ID in", values, "BULLETIN_ID");
			return (Criteria) this;
		}

		public Criteria andBULLETIN_IDNotIn(List<BigDecimal> values) {
			addCriterion("BULLETIN_ID not in", values, "BULLETIN_ID");
			return (Criteria) this;
		}

		public Criteria andBULLETIN_IDBetween(BigDecimal value1,
				BigDecimal value2) {
			addCriterion("BULLETIN_ID between", value1, value2, "BULLETIN_ID");
			return (Criteria) this;
		}

		public Criteria andBULLETIN_IDNotBetween(BigDecimal value1,
				BigDecimal value2) {
			addCriterion("BULLETIN_ID not between", value1, value2,
					"BULLETIN_ID");
			return (Criteria) this;
		}

		public Criteria andSTARTDATEIsNull() {
			addCriterion("STARTDATE is null");
			return (Criteria) this;
		}

		public Criteria andSTARTDATEIsNotNull() {
			addCriterion("STARTDATE is not null");
			return (Criteria) this;
		}

		public Criteria andSTARTDATEEqualTo(Date value) {
			addCriterion("STARTDATE =", value, "STARTDATE");
			return (Criteria) this;
		}

		public Criteria andSTARTDATENotEqualTo(Date value) {
			addCriterion("STARTDATE <>", value, "STARTDATE");
			return (Criteria) this;
		}

		public Criteria andSTARTDATEGreaterThan(Date value) {
			addCriterion("STARTDATE >", value, "STARTDATE");
			return (Criteria) this;
		}

		public Criteria andSTARTDATEGreaterThanOrEqualTo(Date value) {
			addCriterion("STARTDATE >=", value, "STARTDATE");
			return (Criteria) this;
		}

		public Criteria andSTARTDATELessThan(Date value) {
			addCriterion("STARTDATE <", value, "STARTDATE");
			return (Criteria) this;
		}

		public Criteria andSTARTDATELessThanOrEqualTo(Date value) {
			addCriterion("STARTDATE <=", value, "STARTDATE");
			return (Criteria) this;
		}

		public Criteria andSTARTDATEIn(List<Date> values) {
			addCriterion("STARTDATE in", values, "STARTDATE");
			return (Criteria) this;
		}

		public Criteria andSTARTDATENotIn(List<Date> values) {
			addCriterion("STARTDATE not in", values, "STARTDATE");
			return (Criteria) this;
		}

		public Criteria andSTARTDATEBetween(Date value1, Date value2) {
			addCriterion("STARTDATE between", value1, value2, "STARTDATE");
			return (Criteria) this;
		}

		public Criteria andSTARTDATENotBetween(Date value1, Date value2) {
			addCriterion("STARTDATE not between", value1, value2, "STARTDATE");
			return (Criteria) this;
		}

		public Criteria andENDDATEIsNull() {
			addCriterion("ENDDATE is null");
			return (Criteria) this;
		}

		public Criteria andENDDATEIsNotNull() {
			addCriterion("ENDDATE is not null");
			return (Criteria) this;
		}

		public Criteria andENDDATEEqualTo(Date value) {
			addCriterion("ENDDATE =", value, "ENDDATE");
			return (Criteria) this;
		}

		public Criteria andENDDATENotEqualTo(Date value) {
			addCriterion("ENDDATE <>", value, "ENDDATE");
			return (Criteria) this;
		}

		public Criteria andENDDATEGreaterThan(Date value) {
			addCriterion("ENDDATE >", value, "ENDDATE");
			return (Criteria) this;
		}

		public Criteria andENDDATEGreaterThanOrEqualTo(Date value) {
			addCriterion("ENDDATE >=", value, "ENDDATE");
			return (Criteria) this;
		}

		public Criteria andENDDATELessThan(Date value) {
			addCriterion("ENDDATE <", value, "ENDDATE");
			return (Criteria) this;
		}

		public Criteria andENDDATELessThanOrEqualTo(Date value) {
			addCriterion("ENDDATE <=", value, "ENDDATE");
			return (Criteria) this;
		}

		public Criteria andENDDATEIn(List<Date> values) {
			addCriterion("ENDDATE in", values, "ENDDATE");
			return (Criteria) this;
		}

		public Criteria andENDDATENotIn(List<Date> values) {
			addCriterion("ENDDATE not in", values, "ENDDATE");
			return (Criteria) this;
		}

		public Criteria andENDDATEBetween(Date value1, Date value2) {
			addCriterion("ENDDATE between", value1, value2, "ENDDATE");
			return (Criteria) this;
		}

		public Criteria andENDDATENotBetween(Date value1, Date value2) {
			addCriterion("ENDDATE not between", value1, value2, "ENDDATE");
			return (Criteria) this;
		}

		public Criteria andSUBJECTIsNull() {
			addCriterion("SUBJECT is null");
			return (Criteria) this;
		}

		public Criteria andSUBJECTIsNotNull() {
			addCriterion("SUBJECT is not null");
			return (Criteria) this;
		}

		public Criteria andSUBJECTEqualTo(String value) {
			addCriterion("SUBJECT =", value, "SUBJECT");
			return (Criteria) this;
		}

		public Criteria andSUBJECTNotEqualTo(String value) {
			addCriterion("SUBJECT <>", value, "SUBJECT");
			return (Criteria) this;
		}

		public Criteria andSUBJECTGreaterThan(String value) {
			addCriterion("SUBJECT >", value, "SUBJECT");
			return (Criteria) this;
		}

		public Criteria andSUBJECTGreaterThanOrEqualTo(String value) {
			addCriterion("SUBJECT >=", value, "SUBJECT");
			return (Criteria) this;
		}

		public Criteria andSUBJECTLessThan(String value) {
			addCriterion("SUBJECT <", value, "SUBJECT");
			return (Criteria) this;
		}

		public Criteria andSUBJECTLessThanOrEqualTo(String value) {
			addCriterion("SUBJECT <=", value, "SUBJECT");
			return (Criteria) this;
		}

		public Criteria andSUBJECTLike(String value) {
			addCriterion("SUBJECT like", value, "SUBJECT");
			return (Criteria) this;
		}

		public Criteria andSUBJECTNotLike(String value) {
			addCriterion("SUBJECT not like", value, "SUBJECT");
			return (Criteria) this;
		}

		public Criteria andSUBJECTIn(List<String> values) {
			addCriterion("SUBJECT in", values, "SUBJECT");
			return (Criteria) this;
		}

		public Criteria andSUBJECTNotIn(List<String> values) {
			addCriterion("SUBJECT not in", values, "SUBJECT");
			return (Criteria) this;
		}

		public Criteria andSUBJECTBetween(String value1, String value2) {
			addCriterion("SUBJECT between", value1, value2, "SUBJECT");
			return (Criteria) this;
		}

		public Criteria andSUBJECTNotBetween(String value1, String value2) {
			addCriterion("SUBJECT not between", value1, value2, "SUBJECT");
			return (Criteria) this;
		}

		public Criteria andCONTENTSIsNull() {
			addCriterion("CONTENTS is null");
			return (Criteria) this;
		}

		public Criteria andCONTENTSIsNotNull() {
			addCriterion("CONTENTS is not null");
			return (Criteria) this;
		}

		public Criteria andCONTENTSEqualTo(String value) {
			addCriterion("CONTENTS =", value, "CONTENTS");
			return (Criteria) this;
		}

		public Criteria andCONTENTSNotEqualTo(String value) {
			addCriterion("CONTENTS <>", value, "CONTENTS");
			return (Criteria) this;
		}

		public Criteria andCONTENTSGreaterThan(String value) {
			addCriterion("CONTENTS >", value, "CONTENTS");
			return (Criteria) this;
		}

		public Criteria andCONTENTSGreaterThanOrEqualTo(String value) {
			addCriterion("CONTENTS >=", value, "CONTENTS");
			return (Criteria) this;
		}

		public Criteria andCONTENTSLessThan(String value) {
			addCriterion("CONTENTS <", value, "CONTENTS");
			return (Criteria) this;
		}

		public Criteria andCONTENTSLessThanOrEqualTo(String value) {
			addCriterion("CONTENTS <=", value, "CONTENTS");
			return (Criteria) this;
		}

		public Criteria andCONTENTSLike(String value) {
			addCriterion("CONTENTS like", value, "CONTENTS");
			return (Criteria) this;
		}

		public Criteria andCONTENTSNotLike(String value) {
			addCriterion("CONTENTS not like", value, "CONTENTS");
			return (Criteria) this;
		}

		public Criteria andCONTENTSIn(List<String> values) {
			addCriterion("CONTENTS in", values, "CONTENTS");
			return (Criteria) this;
		}

		public Criteria andCONTENTSNotIn(List<String> values) {
			addCriterion("CONTENTS not in", values, "CONTENTS");
			return (Criteria) this;
		}

		public Criteria andCONTENTSBetween(String value1, String value2) {
			addCriterion("CONTENTS between", value1, value2, "CONTENTS");
			return (Criteria) this;
		}

		public Criteria andCONTENTSNotBetween(String value1, String value2) {
			addCriterion("CONTENTS not between", value1, value2, "CONTENTS");
			return (Criteria) this;
		}

		public Criteria andPRIORITYIsNull() {
			addCriterion("PRIORITY is null");
			return (Criteria) this;
		}

		public Criteria andPRIORITYIsNotNull() {
			addCriterion("PRIORITY is not null");
			return (Criteria) this;
		}

		public Criteria andPRIORITYEqualTo(Integer value) {
			addCriterion("PRIORITY =", value, "PRIORITY");
			return (Criteria) this;
		}

		public Criteria andPRIORITYNotEqualTo(Integer value) {
			addCriterion("PRIORITY <>", value, "PRIORITY");
			return (Criteria) this;
		}

		public Criteria andPRIORITYGreaterThan(Integer value) {
			addCriterion("PRIORITY >", value, "PRIORITY");
			return (Criteria) this;
		}

		public Criteria andPRIORITYGreaterThanOrEqualTo(Integer value) {
			addCriterion("PRIORITY >=", value, "PRIORITY");
			return (Criteria) this;
		}

		public Criteria andPRIORITYLessThan(Integer value) {
			addCriterion("PRIORITY <", value, "PRIORITY");
			return (Criteria) this;
		}

		public Criteria andPRIORITYLessThanOrEqualTo(Integer value) {
			addCriterion("PRIORITY <=", value, "PRIORITY");
			return (Criteria) this;
		}

		public Criteria andPRIORITYIn(List<Integer> values) {
			addCriterion("PRIORITY in", values, "PRIORITY");
			return (Criteria) this;
		}

		public Criteria andPRIORITYNotIn(List<Integer> values) {
			addCriterion("PRIORITY not in", values, "PRIORITY");
			return (Criteria) this;
		}

		public Criteria andPRIORITYBetween(Integer value1, Integer value2) {
			addCriterion("PRIORITY between", value1, value2, "PRIORITY");
			return (Criteria) this;
		}

		public Criteria andPRIORITYNotBetween(Integer value1, Integer value2) {
			addCriterion("PRIORITY not between", value1, value2, "PRIORITY");
			return (Criteria) this;
		}

		public Criteria andTYPESIsNull() {
			addCriterion("TYPES is null");
			return (Criteria) this;
		}

		public Criteria andTYPESIsNotNull() {
			addCriterion("TYPES is not null");
			return (Criteria) this;
		}

		public Criteria andTYPESEqualTo(Integer value) {
			addCriterion("TYPES =", value, "TYPES");
			return (Criteria) this;
		}

		public Criteria andTYPESNotEqualTo(Integer value) {
			addCriterion("TYPES <>", value, "TYPES");
			return (Criteria) this;
		}

		public Criteria andTYPESGreaterThan(Integer value) {
			addCriterion("TYPES >", value, "TYPES");
			return (Criteria) this;
		}

		public Criteria andTYPESGreaterThanOrEqualTo(Integer value) {
			addCriterion("TYPES >=", value, "TYPES");
			return (Criteria) this;
		}

		public Criteria andTYPESLessThan(Integer value) {
			addCriterion("TYPES <", value, "TYPES");
			return (Criteria) this;
		}

		public Criteria andTYPESLessThanOrEqualTo(Integer value) {
			addCriterion("TYPES <=", value, "TYPES");
			return (Criteria) this;
		}

		public Criteria andTYPESIn(List<Integer> values) {
			addCriterion("TYPES in", values, "TYPES");
			return (Criteria) this;
		}

		public Criteria andTYPESNotIn(List<Integer> values) {
			addCriterion("TYPES not in", values, "TYPES");
			return (Criteria) this;
		}

		public Criteria andTYPESBetween(Integer value1, Integer value2) {
			addCriterion("TYPES between", value1, value2, "TYPES");
			return (Criteria) this;
		}

		public Criteria andTYPESNotBetween(Integer value1, Integer value2) {
			addCriterion("TYPES not between", value1, value2, "TYPES");
			return (Criteria) this;
		}

		public Criteria andATTACHMENTIsNull() {
			addCriterion("ATTACHMENT is null");
			return (Criteria) this;
		}

		public Criteria andATTACHMENTIsNotNull() {
			addCriterion("ATTACHMENT is not null");
			return (Criteria) this;
		}

		public Criteria andATTACHMENTEqualTo(String value) {
			addCriterion("ATTACHMENT =", value, "ATTACHMENT");
			return (Criteria) this;
		}

		public Criteria andATTACHMENTNotEqualTo(String value) {
			addCriterion("ATTACHMENT <>", value, "ATTACHMENT");
			return (Criteria) this;
		}

		public Criteria andATTACHMENTGreaterThan(String value) {
			addCriterion("ATTACHMENT >", value, "ATTACHMENT");
			return (Criteria) this;
		}

		public Criteria andATTACHMENTGreaterThanOrEqualTo(String value) {
			addCriterion("ATTACHMENT >=", value, "ATTACHMENT");
			return (Criteria) this;
		}

		public Criteria andATTACHMENTLessThan(String value) {
			addCriterion("ATTACHMENT <", value, "ATTACHMENT");
			return (Criteria) this;
		}

		public Criteria andATTACHMENTLessThanOrEqualTo(String value) {
			addCriterion("ATTACHMENT <=", value, "ATTACHMENT");
			return (Criteria) this;
		}

		public Criteria andATTACHMENTLike(String value) {
			addCriterion("ATTACHMENT like", value, "ATTACHMENT");
			return (Criteria) this;
		}

		public Criteria andATTACHMENTNotLike(String value) {
			addCriterion("ATTACHMENT not like", value, "ATTACHMENT");
			return (Criteria) this;
		}

		public Criteria andATTACHMENTIn(List<String> values) {
			addCriterion("ATTACHMENT in", values, "ATTACHMENT");
			return (Criteria) this;
		}

		public Criteria andATTACHMENTNotIn(List<String> values) {
			addCriterion("ATTACHMENT not in", values, "ATTACHMENT");
			return (Criteria) this;
		}

		public Criteria andATTACHMENTBetween(String value1, String value2) {
			addCriterion("ATTACHMENT between", value1, value2, "ATTACHMENT");
			return (Criteria) this;
		}

		public Criteria andATTACHMENTNotBetween(String value1, String value2) {
			addCriterion("ATTACHMENT not between", value1, value2, "ATTACHMENT");
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

		public Criteria andCR_USEREqualTo(BigDecimal value) {
			addCriterion("CR_USER =", value, "CR_USER");
			return (Criteria) this;
		}

		public Criteria andCR_USERNotEqualTo(BigDecimal value) {
			addCriterion("CR_USER <>", value, "CR_USER");
			return (Criteria) this;
		}

		public Criteria andCR_USERGreaterThan(BigDecimal value) {
			addCriterion("CR_USER >", value, "CR_USER");
			return (Criteria) this;
		}

		public Criteria andCR_USERGreaterThanOrEqualTo(BigDecimal value) {
			addCriterion("CR_USER >=", value, "CR_USER");
			return (Criteria) this;
		}

		public Criteria andCR_USERLessThan(BigDecimal value) {
			addCriterion("CR_USER <", value, "CR_USER");
			return (Criteria) this;
		}

		public Criteria andCR_USERLessThanOrEqualTo(BigDecimal value) {
			addCriterion("CR_USER <=", value, "CR_USER");
			return (Criteria) this;
		}

		public Criteria andCR_USERIn(List<BigDecimal> values) {
			addCriterion("CR_USER in", values, "CR_USER");
			return (Criteria) this;
		}

		public Criteria andCR_USERNotIn(List<BigDecimal> values) {
			addCriterion("CR_USER not in", values, "CR_USER");
			return (Criteria) this;
		}

		public Criteria andCR_USERBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("CR_USER between", value1, value2, "CR_USER");
			return (Criteria) this;
		}

		public Criteria andCR_USERNotBetween(BigDecimal value1,
				BigDecimal value2) {
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
			addCriterion("CR_TIME not between", value1, value2,
					"CR_TIME");
			return (Criteria) this;
		}

		public Criteria andMD_USERSN_IDIsNull() {
			addCriterion("MD_USER is null");
			return (Criteria) this;
		}

		public Criteria andMD_USERIsNotNull() {
			addCriterion("MD_USER is not null");
			return (Criteria) this;
		}

		public Criteria andMD_USEREqualTo(BigDecimal value) {
			addCriterion("MD_USER =", value, "MD_USER");
			return (Criteria) this;
		}

		public Criteria andMD_USERNotEqualTo(BigDecimal value) {
			addCriterion("MD_USER <>", value, "MD_USER");
			return (Criteria) this;
		}

		public Criteria andMD_USERGreaterThan(BigDecimal value) {
			addCriterion("MD_USER >", value, "MD_USER");
			return (Criteria) this;
		}

		public Criteria andMD_USERGreaterThanOrEqualTo(BigDecimal value) {
			addCriterion("MD_USER >=", value, "MD_USER");
			return (Criteria) this;
		}

		public Criteria andMD_USERLessThan(BigDecimal value) {
			addCriterion("MD_USER <", value, "MD_USER");
			return (Criteria) this;
		}

		public Criteria andMD_USERLessThanOrEqualTo(BigDecimal value) {
			addCriterion("MD_USER <=", value, "MD_USER");
			return (Criteria) this;
		}

		public Criteria andMD_USERIn(List<BigDecimal> values) {
			addCriterion("MD_USER in", values, "MD_USER");
			return (Criteria) this;
		}

		public Criteria andMD_USERNotIn(List<BigDecimal> values) {
			addCriterion("MD_USER not in", values, "MD_USER");
			return (Criteria) this;
		}

		public Criteria andMD_USERBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("MD_USER between", value1, value2, "MD_USER");
			return (Criteria) this;
		}

		public Criteria andMD_USERNotBetween(BigDecimal value1,
				BigDecimal value2) {
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
			addCriterion("MD_TIME not between", value1, value2,
					"MD_TIME");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table TB_SYS_BULLETINBOARD
	 * @mbggenerated  Thu Aug 21 09:53:31 CST 2014
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
     * This class corresponds to the database table TB_SYS_BULLETINBOARD
     *
     * @mbggenerated do_not_delete_during_merge Wed Aug 20 16:29:34 CST 2014
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}