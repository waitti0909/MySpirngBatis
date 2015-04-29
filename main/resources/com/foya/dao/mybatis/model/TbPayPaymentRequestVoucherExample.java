package com.foya.dao.mybatis.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TbPayPaymentRequestVoucherExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table TB_PAY_PAYMENT_REQUEST_VOUCHER
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table TB_PAY_PAYMENT_REQUEST_VOUCHER
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table TB_PAY_PAYMENT_REQUEST_VOUCHER
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_PAY_PAYMENT_REQUEST_VOUCHER
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
	 */
	public TbPayPaymentRequestVoucherExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_PAY_PAYMENT_REQUEST_VOUCHER
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_PAY_PAYMENT_REQUEST_VOUCHER
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_PAY_PAYMENT_REQUEST_VOUCHER
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_PAY_PAYMENT_REQUEST_VOUCHER
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_PAY_PAYMENT_REQUEST_VOUCHER
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_PAY_PAYMENT_REQUEST_VOUCHER
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_PAY_PAYMENT_REQUEST_VOUCHER
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_PAY_PAYMENT_REQUEST_VOUCHER
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_PAY_PAYMENT_REQUEST_VOUCHER
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_PAY_PAYMENT_REQUEST_VOUCHER
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table TB_PAY_PAYMENT_REQUEST_VOUCHER
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
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

		public Criteria andSEQ_NBRIsNull() {
			addCriterion("SEQ_NBR is null");
			return (Criteria) this;
		}

		public Criteria andSEQ_NBRIsNotNull() {
			addCriterion("SEQ_NBR is not null");
			return (Criteria) this;
		}

		public Criteria andSEQ_NBREqualTo(Long value) {
			addCriterion("SEQ_NBR =", value, "SEQ_NBR");
			return (Criteria) this;
		}

		public Criteria andSEQ_NBRNotEqualTo(Long value) {
			addCriterion("SEQ_NBR <>", value, "SEQ_NBR");
			return (Criteria) this;
		}

		public Criteria andSEQ_NBRGreaterThan(Long value) {
			addCriterion("SEQ_NBR >", value, "SEQ_NBR");
			return (Criteria) this;
		}

		public Criteria andSEQ_NBRGreaterThanOrEqualTo(Long value) {
			addCriterion("SEQ_NBR >=", value, "SEQ_NBR");
			return (Criteria) this;
		}

		public Criteria andSEQ_NBRLessThan(Long value) {
			addCriterion("SEQ_NBR <", value, "SEQ_NBR");
			return (Criteria) this;
		}

		public Criteria andSEQ_NBRLessThanOrEqualTo(Long value) {
			addCriterion("SEQ_NBR <=", value, "SEQ_NBR");
			return (Criteria) this;
		}

		public Criteria andSEQ_NBRIn(List<Long> values) {
			addCriterion("SEQ_NBR in", values, "SEQ_NBR");
			return (Criteria) this;
		}

		public Criteria andSEQ_NBRNotIn(List<Long> values) {
			addCriterion("SEQ_NBR not in", values, "SEQ_NBR");
			return (Criteria) this;
		}

		public Criteria andSEQ_NBRBetween(Long value1, Long value2) {
			addCriterion("SEQ_NBR between", value1, value2, "SEQ_NBR");
			return (Criteria) this;
		}

		public Criteria andSEQ_NBRNotBetween(Long value1, Long value2) {
			addCriterion("SEQ_NBR not between", value1, value2, "SEQ_NBR");
			return (Criteria) this;
		}

		public Criteria andPROCESS_TYPEIsNull() {
			addCriterion("PROCESS_TYPE is null");
			return (Criteria) this;
		}

		public Criteria andPROCESS_TYPEIsNotNull() {
			addCriterion("PROCESS_TYPE is not null");
			return (Criteria) this;
		}

		public Criteria andPROCESS_TYPEEqualTo(String value) {
			addCriterion("PROCESS_TYPE =", value, "PROCESS_TYPE");
			return (Criteria) this;
		}

		public Criteria andPROCESS_TYPENotEqualTo(String value) {
			addCriterion("PROCESS_TYPE <>", value, "PROCESS_TYPE");
			return (Criteria) this;
		}

		public Criteria andPROCESS_TYPEGreaterThan(String value) {
			addCriterion("PROCESS_TYPE >", value, "PROCESS_TYPE");
			return (Criteria) this;
		}

		public Criteria andPROCESS_TYPEGreaterThanOrEqualTo(String value) {
			addCriterion("PROCESS_TYPE >=", value, "PROCESS_TYPE");
			return (Criteria) this;
		}

		public Criteria andPROCESS_TYPELessThan(String value) {
			addCriterion("PROCESS_TYPE <", value, "PROCESS_TYPE");
			return (Criteria) this;
		}

		public Criteria andPROCESS_TYPELessThanOrEqualTo(String value) {
			addCriterion("PROCESS_TYPE <=", value, "PROCESS_TYPE");
			return (Criteria) this;
		}

		public Criteria andPROCESS_TYPELike(String value) {
			addCriterion("PROCESS_TYPE like", value, "PROCESS_TYPE");
			return (Criteria) this;
		}

		public Criteria andPROCESS_TYPENotLike(String value) {
			addCriterion("PROCESS_TYPE not like", value, "PROCESS_TYPE");
			return (Criteria) this;
		}

		public Criteria andPROCESS_TYPEIn(List<String> values) {
			addCriterion("PROCESS_TYPE in", values, "PROCESS_TYPE");
			return (Criteria) this;
		}

		public Criteria andPROCESS_TYPENotIn(List<String> values) {
			addCriterion("PROCESS_TYPE not in", values, "PROCESS_TYPE");
			return (Criteria) this;
		}

		public Criteria andPROCESS_TYPEBetween(String value1, String value2) {
			addCriterion("PROCESS_TYPE between", value1, value2, "PROCESS_TYPE");
			return (Criteria) this;
		}

		public Criteria andPROCESS_TYPENotBetween(String value1, String value2) {
			addCriterion("PROCESS_TYPE not between", value1, value2,
					"PROCESS_TYPE");
			return (Criteria) this;
		}

		public Criteria andVOUCHER_NOIsNull() {
			addCriterion("VOUCHER_NO is null");
			return (Criteria) this;
		}

		public Criteria andVOUCHER_NOIsNotNull() {
			addCriterion("VOUCHER_NO is not null");
			return (Criteria) this;
		}

		public Criteria andVOUCHER_NOEqualTo(String value) {
			addCriterion("VOUCHER_NO =", value, "VOUCHER_NO");
			return (Criteria) this;
		}

		public Criteria andVOUCHER_NONotEqualTo(String value) {
			addCriterion("VOUCHER_NO <>", value, "VOUCHER_NO");
			return (Criteria) this;
		}

		public Criteria andVOUCHER_NOGreaterThan(String value) {
			addCriterion("VOUCHER_NO >", value, "VOUCHER_NO");
			return (Criteria) this;
		}

		public Criteria andVOUCHER_NOGreaterThanOrEqualTo(String value) {
			addCriterion("VOUCHER_NO >=", value, "VOUCHER_NO");
			return (Criteria) this;
		}

		public Criteria andVOUCHER_NOLessThan(String value) {
			addCriterion("VOUCHER_NO <", value, "VOUCHER_NO");
			return (Criteria) this;
		}

		public Criteria andVOUCHER_NOLessThanOrEqualTo(String value) {
			addCriterion("VOUCHER_NO <=", value, "VOUCHER_NO");
			return (Criteria) this;
		}

		public Criteria andVOUCHER_NOLike(String value) {
			addCriterion("VOUCHER_NO like", value, "VOUCHER_NO");
			return (Criteria) this;
		}

		public Criteria andVOUCHER_NONotLike(String value) {
			addCriterion("VOUCHER_NO not like", value, "VOUCHER_NO");
			return (Criteria) this;
		}

		public Criteria andVOUCHER_NOIn(List<String> values) {
			addCriterion("VOUCHER_NO in", values, "VOUCHER_NO");
			return (Criteria) this;
		}

		public Criteria andVOUCHER_NONotIn(List<String> values) {
			addCriterion("VOUCHER_NO not in", values, "VOUCHER_NO");
			return (Criteria) this;
		}

		public Criteria andVOUCHER_NOBetween(String value1, String value2) {
			addCriterion("VOUCHER_NO between", value1, value2, "VOUCHER_NO");
			return (Criteria) this;
		}

		public Criteria andVOUCHER_NONotBetween(String value1, String value2) {
			addCriterion("VOUCHER_NO not between", value1, value2, "VOUCHER_NO");
			return (Criteria) this;
		}

		public Criteria andVOUCHER_TYPEIsNull() {
			addCriterion("VOUCHER_TYPE is null");
			return (Criteria) this;
		}

		public Criteria andVOUCHER_TYPEIsNotNull() {
			addCriterion("VOUCHER_TYPE is not null");
			return (Criteria) this;
		}

		public Criteria andVOUCHER_TYPEEqualTo(String value) {
			addCriterion("VOUCHER_TYPE =", value, "VOUCHER_TYPE");
			return (Criteria) this;
		}

		public Criteria andVOUCHER_TYPENotEqualTo(String value) {
			addCriterion("VOUCHER_TYPE <>", value, "VOUCHER_TYPE");
			return (Criteria) this;
		}

		public Criteria andVOUCHER_TYPEGreaterThan(String value) {
			addCriterion("VOUCHER_TYPE >", value, "VOUCHER_TYPE");
			return (Criteria) this;
		}

		public Criteria andVOUCHER_TYPEGreaterThanOrEqualTo(String value) {
			addCriterion("VOUCHER_TYPE >=", value, "VOUCHER_TYPE");
			return (Criteria) this;
		}

		public Criteria andVOUCHER_TYPELessThan(String value) {
			addCriterion("VOUCHER_TYPE <", value, "VOUCHER_TYPE");
			return (Criteria) this;
		}

		public Criteria andVOUCHER_TYPELessThanOrEqualTo(String value) {
			addCriterion("VOUCHER_TYPE <=", value, "VOUCHER_TYPE");
			return (Criteria) this;
		}

		public Criteria andVOUCHER_TYPELike(String value) {
			addCriterion("VOUCHER_TYPE like", value, "VOUCHER_TYPE");
			return (Criteria) this;
		}

		public Criteria andVOUCHER_TYPENotLike(String value) {
			addCriterion("VOUCHER_TYPE not like", value, "VOUCHER_TYPE");
			return (Criteria) this;
		}

		public Criteria andVOUCHER_TYPEIn(List<String> values) {
			addCriterion("VOUCHER_TYPE in", values, "VOUCHER_TYPE");
			return (Criteria) this;
		}

		public Criteria andVOUCHER_TYPENotIn(List<String> values) {
			addCriterion("VOUCHER_TYPE not in", values, "VOUCHER_TYPE");
			return (Criteria) this;
		}

		public Criteria andVOUCHER_TYPEBetween(String value1, String value2) {
			addCriterion("VOUCHER_TYPE between", value1, value2, "VOUCHER_TYPE");
			return (Criteria) this;
		}

		public Criteria andVOUCHER_TYPENotBetween(String value1, String value2) {
			addCriterion("VOUCHER_TYPE not between", value1, value2,
					"VOUCHER_TYPE");
			return (Criteria) this;
		}

		public Criteria andVOUCHER_NBRIsNull() {
			addCriterion("VOUCHER_NBR is null");
			return (Criteria) this;
		}

		public Criteria andVOUCHER_NBRIsNotNull() {
			addCriterion("VOUCHER_NBR is not null");
			return (Criteria) this;
		}

		public Criteria andVOUCHER_NBREqualTo(String value) {
			addCriterion("VOUCHER_NBR =", value, "VOUCHER_NBR");
			return (Criteria) this;
		}

		public Criteria andVOUCHER_NBRNotEqualTo(String value) {
			addCriterion("VOUCHER_NBR <>", value, "VOUCHER_NBR");
			return (Criteria) this;
		}

		public Criteria andVOUCHER_NBRGreaterThan(String value) {
			addCriterion("VOUCHER_NBR >", value, "VOUCHER_NBR");
			return (Criteria) this;
		}

		public Criteria andVOUCHER_NBRGreaterThanOrEqualTo(String value) {
			addCriterion("VOUCHER_NBR >=", value, "VOUCHER_NBR");
			return (Criteria) this;
		}

		public Criteria andVOUCHER_NBRLessThan(String value) {
			addCriterion("VOUCHER_NBR <", value, "VOUCHER_NBR");
			return (Criteria) this;
		}

		public Criteria andVOUCHER_NBRLessThanOrEqualTo(String value) {
			addCriterion("VOUCHER_NBR <=", value, "VOUCHER_NBR");
			return (Criteria) this;
		}

		public Criteria andVOUCHER_NBRLike(String value) {
			addCriterion("VOUCHER_NBR like", value, "VOUCHER_NBR");
			return (Criteria) this;
		}

		public Criteria andVOUCHER_NBRNotLike(String value) {
			addCriterion("VOUCHER_NBR not like", value, "VOUCHER_NBR");
			return (Criteria) this;
		}

		public Criteria andVOUCHER_NBRIn(List<String> values) {
			addCriterion("VOUCHER_NBR in", values, "VOUCHER_NBR");
			return (Criteria) this;
		}

		public Criteria andVOUCHER_NBRNotIn(List<String> values) {
			addCriterion("VOUCHER_NBR not in", values, "VOUCHER_NBR");
			return (Criteria) this;
		}

		public Criteria andVOUCHER_NBRBetween(String value1, String value2) {
			addCriterion("VOUCHER_NBR between", value1, value2, "VOUCHER_NBR");
			return (Criteria) this;
		}

		public Criteria andVOUCHER_NBRNotBetween(String value1, String value2) {
			addCriterion("VOUCHER_NBR not between", value1, value2,
					"VOUCHER_NBR");
			return (Criteria) this;
		}

		public Criteria andVOUCHER_DATEIsNull() {
			addCriterion("VOUCHER_DATE is null");
			return (Criteria) this;
		}

		public Criteria andVOUCHER_DATEIsNotNull() {
			addCriterion("VOUCHER_DATE is not null");
			return (Criteria) this;
		}

		public Criteria andVOUCHER_DATEEqualTo(Date value) {
			addCriterion("VOUCHER_DATE =", value, "VOUCHER_DATE");
			return (Criteria) this;
		}

		public Criteria andVOUCHER_DATENotEqualTo(Date value) {
			addCriterion("VOUCHER_DATE <>", value, "VOUCHER_DATE");
			return (Criteria) this;
		}

		public Criteria andVOUCHER_DATEGreaterThan(Date value) {
			addCriterion("VOUCHER_DATE >", value, "VOUCHER_DATE");
			return (Criteria) this;
		}

		public Criteria andVOUCHER_DATEGreaterThanOrEqualTo(Date value) {
			addCriterion("VOUCHER_DATE >=", value, "VOUCHER_DATE");
			return (Criteria) this;
		}

		public Criteria andVOUCHER_DATELessThan(Date value) {
			addCriterion("VOUCHER_DATE <", value, "VOUCHER_DATE");
			return (Criteria) this;
		}

		public Criteria andVOUCHER_DATELessThanOrEqualTo(Date value) {
			addCriterion("VOUCHER_DATE <=", value, "VOUCHER_DATE");
			return (Criteria) this;
		}

		public Criteria andVOUCHER_DATEIn(List<Date> values) {
			addCriterion("VOUCHER_DATE in", values, "VOUCHER_DATE");
			return (Criteria) this;
		}

		public Criteria andVOUCHER_DATENotIn(List<Date> values) {
			addCriterion("VOUCHER_DATE not in", values, "VOUCHER_DATE");
			return (Criteria) this;
		}

		public Criteria andVOUCHER_DATEBetween(Date value1, Date value2) {
			addCriterion("VOUCHER_DATE between", value1, value2, "VOUCHER_DATE");
			return (Criteria) this;
		}

		public Criteria andVOUCHER_DATENotBetween(Date value1, Date value2) {
			addCriterion("VOUCHER_DATE not between", value1, value2,
					"VOUCHER_DATE");
			return (Criteria) this;
		}

		public Criteria andVAT_NUMBERIsNull() {
			addCriterion("VAT_NUMBER is null");
			return (Criteria) this;
		}

		public Criteria andVAT_NUMBERIsNotNull() {
			addCriterion("VAT_NUMBER is not null");
			return (Criteria) this;
		}

		public Criteria andVAT_NUMBEREqualTo(String value) {
			addCriterion("VAT_NUMBER =", value, "VAT_NUMBER");
			return (Criteria) this;
		}

		public Criteria andVAT_NUMBERNotEqualTo(String value) {
			addCriterion("VAT_NUMBER <>", value, "VAT_NUMBER");
			return (Criteria) this;
		}

		public Criteria andVAT_NUMBERGreaterThan(String value) {
			addCriterion("VAT_NUMBER >", value, "VAT_NUMBER");
			return (Criteria) this;
		}

		public Criteria andVAT_NUMBERGreaterThanOrEqualTo(String value) {
			addCriterion("VAT_NUMBER >=", value, "VAT_NUMBER");
			return (Criteria) this;
		}

		public Criteria andVAT_NUMBERLessThan(String value) {
			addCriterion("VAT_NUMBER <", value, "VAT_NUMBER");
			return (Criteria) this;
		}

		public Criteria andVAT_NUMBERLessThanOrEqualTo(String value) {
			addCriterion("VAT_NUMBER <=", value, "VAT_NUMBER");
			return (Criteria) this;
		}

		public Criteria andVAT_NUMBERLike(String value) {
			addCriterion("VAT_NUMBER like", value, "VAT_NUMBER");
			return (Criteria) this;
		}

		public Criteria andVAT_NUMBERNotLike(String value) {
			addCriterion("VAT_NUMBER not like", value, "VAT_NUMBER");
			return (Criteria) this;
		}

		public Criteria andVAT_NUMBERIn(List<String> values) {
			addCriterion("VAT_NUMBER in", values, "VAT_NUMBER");
			return (Criteria) this;
		}

		public Criteria andVAT_NUMBERNotIn(List<String> values) {
			addCriterion("VAT_NUMBER not in", values, "VAT_NUMBER");
			return (Criteria) this;
		}

		public Criteria andVAT_NUMBERBetween(String value1, String value2) {
			addCriterion("VAT_NUMBER between", value1, value2, "VAT_NUMBER");
			return (Criteria) this;
		}

		public Criteria andVAT_NUMBERNotBetween(String value1, String value2) {
			addCriterion("VAT_NUMBER not between", value1, value2, "VAT_NUMBER");
			return (Criteria) this;
		}

		public Criteria andTAX_EXCLUSIVE_AMTIsNull() {
			addCriterion("TAX_EXCLUSIVE_AMT is null");
			return (Criteria) this;
		}

		public Criteria andTAX_EXCLUSIVE_AMTIsNotNull() {
			addCriterion("TAX_EXCLUSIVE_AMT is not null");
			return (Criteria) this;
		}

		public Criteria andTAX_EXCLUSIVE_AMTEqualTo(BigDecimal value) {
			addCriterion("TAX_EXCLUSIVE_AMT =", value, "TAX_EXCLUSIVE_AMT");
			return (Criteria) this;
		}

		public Criteria andTAX_EXCLUSIVE_AMTNotEqualTo(BigDecimal value) {
			addCriterion("TAX_EXCLUSIVE_AMT <>", value, "TAX_EXCLUSIVE_AMT");
			return (Criteria) this;
		}

		public Criteria andTAX_EXCLUSIVE_AMTGreaterThan(BigDecimal value) {
			addCriterion("TAX_EXCLUSIVE_AMT >", value, "TAX_EXCLUSIVE_AMT");
			return (Criteria) this;
		}

		public Criteria andTAX_EXCLUSIVE_AMTGreaterThanOrEqualTo(
				BigDecimal value) {
			addCriterion("TAX_EXCLUSIVE_AMT >=", value, "TAX_EXCLUSIVE_AMT");
			return (Criteria) this;
		}

		public Criteria andTAX_EXCLUSIVE_AMTLessThan(BigDecimal value) {
			addCriterion("TAX_EXCLUSIVE_AMT <", value, "TAX_EXCLUSIVE_AMT");
			return (Criteria) this;
		}

		public Criteria andTAX_EXCLUSIVE_AMTLessThanOrEqualTo(BigDecimal value) {
			addCriterion("TAX_EXCLUSIVE_AMT <=", value, "TAX_EXCLUSIVE_AMT");
			return (Criteria) this;
		}

		public Criteria andTAX_EXCLUSIVE_AMTIn(List<BigDecimal> values) {
			addCriterion("TAX_EXCLUSIVE_AMT in", values, "TAX_EXCLUSIVE_AMT");
			return (Criteria) this;
		}

		public Criteria andTAX_EXCLUSIVE_AMTNotIn(List<BigDecimal> values) {
			addCriterion("TAX_EXCLUSIVE_AMT not in", values,
					"TAX_EXCLUSIVE_AMT");
			return (Criteria) this;
		}

		public Criteria andTAX_EXCLUSIVE_AMTBetween(BigDecimal value1,
				BigDecimal value2) {
			addCriterion("TAX_EXCLUSIVE_AMT between", value1, value2,
					"TAX_EXCLUSIVE_AMT");
			return (Criteria) this;
		}

		public Criteria andTAX_EXCLUSIVE_AMTNotBetween(BigDecimal value1,
				BigDecimal value2) {
			addCriterion("TAX_EXCLUSIVE_AMT not between", value1, value2,
					"TAX_EXCLUSIVE_AMT");
			return (Criteria) this;
		}

		public Criteria andBUSINESS_TAXIsNull() {
			addCriterion("BUSINESS_TAX is null");
			return (Criteria) this;
		}

		public Criteria andBUSINESS_TAXIsNotNull() {
			addCriterion("BUSINESS_TAX is not null");
			return (Criteria) this;
		}

		public Criteria andBUSINESS_TAXEqualTo(BigDecimal value) {
			addCriterion("BUSINESS_TAX =", value, "BUSINESS_TAX");
			return (Criteria) this;
		}

		public Criteria andBUSINESS_TAXNotEqualTo(BigDecimal value) {
			addCriterion("BUSINESS_TAX <>", value, "BUSINESS_TAX");
			return (Criteria) this;
		}

		public Criteria andBUSINESS_TAXGreaterThan(BigDecimal value) {
			addCriterion("BUSINESS_TAX >", value, "BUSINESS_TAX");
			return (Criteria) this;
		}

		public Criteria andBUSINESS_TAXGreaterThanOrEqualTo(BigDecimal value) {
			addCriterion("BUSINESS_TAX >=", value, "BUSINESS_TAX");
			return (Criteria) this;
		}

		public Criteria andBUSINESS_TAXLessThan(BigDecimal value) {
			addCriterion("BUSINESS_TAX <", value, "BUSINESS_TAX");
			return (Criteria) this;
		}

		public Criteria andBUSINESS_TAXLessThanOrEqualTo(BigDecimal value) {
			addCriterion("BUSINESS_TAX <=", value, "BUSINESS_TAX");
			return (Criteria) this;
		}

		public Criteria andBUSINESS_TAXIn(List<BigDecimal> values) {
			addCriterion("BUSINESS_TAX in", values, "BUSINESS_TAX");
			return (Criteria) this;
		}

		public Criteria andBUSINESS_TAXNotIn(List<BigDecimal> values) {
			addCriterion("BUSINESS_TAX not in", values, "BUSINESS_TAX");
			return (Criteria) this;
		}

		public Criteria andBUSINESS_TAXBetween(BigDecimal value1,
				BigDecimal value2) {
			addCriterion("BUSINESS_TAX between", value1, value2, "BUSINESS_TAX");
			return (Criteria) this;
		}

		public Criteria andBUSINESS_TAXNotBetween(BigDecimal value1,
				BigDecimal value2) {
			addCriterion("BUSINESS_TAX not between", value1, value2,
					"BUSINESS_TAX");
			return (Criteria) this;
		}

		public Criteria andCREDIT_MADE_AMTIsNull() {
			addCriterion("CREDIT_MADE_AMT is null");
			return (Criteria) this;
		}

		public Criteria andCREDIT_MADE_AMTIsNotNull() {
			addCriterion("CREDIT_MADE_AMT is not null");
			return (Criteria) this;
		}

		public Criteria andCREDIT_MADE_AMTEqualTo(BigDecimal value) {
			addCriterion("CREDIT_MADE_AMT =", value, "CREDIT_MADE_AMT");
			return (Criteria) this;
		}

		public Criteria andCREDIT_MADE_AMTNotEqualTo(BigDecimal value) {
			addCriterion("CREDIT_MADE_AMT <>", value, "CREDIT_MADE_AMT");
			return (Criteria) this;
		}

		public Criteria andCREDIT_MADE_AMTGreaterThan(BigDecimal value) {
			addCriterion("CREDIT_MADE_AMT >", value, "CREDIT_MADE_AMT");
			return (Criteria) this;
		}

		public Criteria andCREDIT_MADE_AMTGreaterThanOrEqualTo(BigDecimal value) {
			addCriterion("CREDIT_MADE_AMT >=", value, "CREDIT_MADE_AMT");
			return (Criteria) this;
		}

		public Criteria andCREDIT_MADE_AMTLessThan(BigDecimal value) {
			addCriterion("CREDIT_MADE_AMT <", value, "CREDIT_MADE_AMT");
			return (Criteria) this;
		}

		public Criteria andCREDIT_MADE_AMTLessThanOrEqualTo(BigDecimal value) {
			addCriterion("CREDIT_MADE_AMT <=", value, "CREDIT_MADE_AMT");
			return (Criteria) this;
		}

		public Criteria andCREDIT_MADE_AMTIn(List<BigDecimal> values) {
			addCriterion("CREDIT_MADE_AMT in", values, "CREDIT_MADE_AMT");
			return (Criteria) this;
		}

		public Criteria andCREDIT_MADE_AMTNotIn(List<BigDecimal> values) {
			addCriterion("CREDIT_MADE_AMT not in", values, "CREDIT_MADE_AMT");
			return (Criteria) this;
		}

		public Criteria andCREDIT_MADE_AMTBetween(BigDecimal value1,
				BigDecimal value2) {
			addCriterion("CREDIT_MADE_AMT between", value1, value2,
					"CREDIT_MADE_AMT");
			return (Criteria) this;
		}

		public Criteria andCREDIT_MADE_AMTNotBetween(BigDecimal value1,
				BigDecimal value2) {
			addCriterion("CREDIT_MADE_AMT not between", value1, value2,
					"CREDIT_MADE_AMT");
			return (Criteria) this;
		}

		public Criteria andCREDIT_MADE_TAXIsNull() {
			addCriterion("CREDIT_MADE_TAX is null");
			return (Criteria) this;
		}

		public Criteria andCREDIT_MADE_TAXIsNotNull() {
			addCriterion("CREDIT_MADE_TAX is not null");
			return (Criteria) this;
		}

		public Criteria andCREDIT_MADE_TAXEqualTo(BigDecimal value) {
			addCriterion("CREDIT_MADE_TAX =", value, "CREDIT_MADE_TAX");
			return (Criteria) this;
		}

		public Criteria andCREDIT_MADE_TAXNotEqualTo(BigDecimal value) {
			addCriterion("CREDIT_MADE_TAX <>", value, "CREDIT_MADE_TAX");
			return (Criteria) this;
		}

		public Criteria andCREDIT_MADE_TAXGreaterThan(BigDecimal value) {
			addCriterion("CREDIT_MADE_TAX >", value, "CREDIT_MADE_TAX");
			return (Criteria) this;
		}

		public Criteria andCREDIT_MADE_TAXGreaterThanOrEqualTo(BigDecimal value) {
			addCriterion("CREDIT_MADE_TAX >=", value, "CREDIT_MADE_TAX");
			return (Criteria) this;
		}

		public Criteria andCREDIT_MADE_TAXLessThan(BigDecimal value) {
			addCriterion("CREDIT_MADE_TAX <", value, "CREDIT_MADE_TAX");
			return (Criteria) this;
		}

		public Criteria andCREDIT_MADE_TAXLessThanOrEqualTo(BigDecimal value) {
			addCriterion("CREDIT_MADE_TAX <=", value, "CREDIT_MADE_TAX");
			return (Criteria) this;
		}

		public Criteria andCREDIT_MADE_TAXIn(List<BigDecimal> values) {
			addCriterion("CREDIT_MADE_TAX in", values, "CREDIT_MADE_TAX");
			return (Criteria) this;
		}

		public Criteria andCREDIT_MADE_TAXNotIn(List<BigDecimal> values) {
			addCriterion("CREDIT_MADE_TAX not in", values, "CREDIT_MADE_TAX");
			return (Criteria) this;
		}

		public Criteria andCREDIT_MADE_TAXBetween(BigDecimal value1,
				BigDecimal value2) {
			addCriterion("CREDIT_MADE_TAX between", value1, value2,
					"CREDIT_MADE_TAX");
			return (Criteria) this;
		}

		public Criteria andCREDIT_MADE_TAXNotBetween(BigDecimal value1,
				BigDecimal value2) {
			addCriterion("CREDIT_MADE_TAX not between", value1, value2,
					"CREDIT_MADE_TAX");
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
	 * This class was generated by MyBatis Generator. This class corresponds to the database table TB_PAY_PAYMENT_REQUEST_VOUCHER
	 * @mbggenerated  Wed Jan 28 11:01:44 CST 2015
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
     * This class corresponds to the database table TB_PAY_PAYMENT_REQUEST_VOUCHER
     *
     * @mbggenerated do_not_delete_during_merge Wed Jan 21 17:16:33 CST 2015
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}