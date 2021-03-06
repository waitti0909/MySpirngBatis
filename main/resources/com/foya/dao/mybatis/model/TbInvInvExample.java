package com.foya.dao.mybatis.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TbInvInvExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table TB_INV_INV
	 * @mbggenerated  Mon Nov 24 10:11:47 CST 2014
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table TB_INV_INV
	 * @mbggenerated  Mon Nov 24 10:11:47 CST 2014
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table TB_INV_INV
	 * @mbggenerated  Mon Nov 24 10:11:47 CST 2014
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_INV
	 * @mbggenerated  Mon Nov 24 10:11:47 CST 2014
	 */
	public TbInvInvExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_INV
	 * @mbggenerated  Mon Nov 24 10:11:47 CST 2014
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_INV
	 * @mbggenerated  Mon Nov 24 10:11:47 CST 2014
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_INV
	 * @mbggenerated  Mon Nov 24 10:11:47 CST 2014
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_INV
	 * @mbggenerated  Mon Nov 24 10:11:47 CST 2014
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_INV
	 * @mbggenerated  Mon Nov 24 10:11:47 CST 2014
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_INV
	 * @mbggenerated  Mon Nov 24 10:11:47 CST 2014
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_INV
	 * @mbggenerated  Mon Nov 24 10:11:47 CST 2014
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_INV
	 * @mbggenerated  Mon Nov 24 10:11:47 CST 2014
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_INV
	 * @mbggenerated  Mon Nov 24 10:11:47 CST 2014
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_INV
	 * @mbggenerated  Mon Nov 24 10:11:47 CST 2014
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table TB_INV_INV
	 * @mbggenerated  Mon Nov 24 10:11:47 CST 2014
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

		public Criteria andInv_noIsNull() {
			addCriterion("inv_no is null");
			return (Criteria) this;
		}

		public Criteria andInv_noIsNotNull() {
			addCriterion("inv_no is not null");
			return (Criteria) this;
		}

		public Criteria andInv_noEqualTo(Long value) {
			addCriterion("inv_no =", value, "inv_no");
			return (Criteria) this;
		}

		public Criteria andInv_noNotEqualTo(Long value) {
			addCriterion("inv_no <>", value, "inv_no");
			return (Criteria) this;
		}

		public Criteria andInv_noGreaterThan(Long value) {
			addCriterion("inv_no >", value, "inv_no");
			return (Criteria) this;
		}

		public Criteria andInv_noGreaterThanOrEqualTo(Long value) {
			addCriterion("inv_no >=", value, "inv_no");
			return (Criteria) this;
		}

		public Criteria andInv_noLessThan(Long value) {
			addCriterion("inv_no <", value, "inv_no");
			return (Criteria) this;
		}

		public Criteria andInv_noLessThanOrEqualTo(Long value) {
			addCriterion("inv_no <=", value, "inv_no");
			return (Criteria) this;
		}

		public Criteria andInv_noIn(List<Long> values) {
			addCriterion("inv_no in", values, "inv_no");
			return (Criteria) this;
		}

		public Criteria andInv_noNotIn(List<Long> values) {
			addCriterion("inv_no not in", values, "inv_no");
			return (Criteria) this;
		}

		public Criteria andInv_noBetween(Long value1, Long value2) {
			addCriterion("inv_no between", value1, value2, "inv_no");
			return (Criteria) this;
		}

		public Criteria andInv_noNotBetween(Long value1, Long value2) {
			addCriterion("inv_no not between", value1, value2, "inv_no");
			return (Criteria) this;
		}

		public Criteria andWh_idIsNull() {
			addCriterion("wh_id is null");
			return (Criteria) this;
		}

		public Criteria andWh_idIsNotNull() {
			addCriterion("wh_id is not null");
			return (Criteria) this;
		}

		public Criteria andWh_idEqualTo(String value) {
			addCriterion("wh_id =", value, "wh_id");
			return (Criteria) this;
		}

		public Criteria andWh_idNotEqualTo(String value) {
			addCriterion("wh_id <>", value, "wh_id");
			return (Criteria) this;
		}

		public Criteria andWh_idGreaterThan(String value) {
			addCriterion("wh_id >", value, "wh_id");
			return (Criteria) this;
		}

		public Criteria andWh_idGreaterThanOrEqualTo(String value) {
			addCriterion("wh_id >=", value, "wh_id");
			return (Criteria) this;
		}

		public Criteria andWh_idLessThan(String value) {
			addCriterion("wh_id <", value, "wh_id");
			return (Criteria) this;
		}

		public Criteria andWh_idLessThanOrEqualTo(String value) {
			addCriterion("wh_id <=", value, "wh_id");
			return (Criteria) this;
		}

		public Criteria andWh_idLike(String value) {
			addCriterion("wh_id like", value, "wh_id");
			return (Criteria) this;
		}

		public Criteria andWh_idNotLike(String value) {
			addCriterion("wh_id not like", value, "wh_id");
			return (Criteria) this;
		}

		public Criteria andWh_idIn(List<String> values) {
			addCriterion("wh_id in", values, "wh_id");
			return (Criteria) this;
		}

		public Criteria andWh_idNotIn(List<String> values) {
			addCriterion("wh_id not in", values, "wh_id");
			return (Criteria) this;
		}

		public Criteria andWh_idBetween(String value1, String value2) {
			addCriterion("wh_id between", value1, value2, "wh_id");
			return (Criteria) this;
		}

		public Criteria andWh_idNotBetween(String value1, String value2) {
			addCriterion("wh_id not between", value1, value2, "wh_id");
			return (Criteria) this;
		}

		public Criteria andMat_noIsNull() {
			addCriterion("mat_no is null");
			return (Criteria) this;
		}

		public Criteria andMat_noIsNotNull() {
			addCriterion("mat_no is not null");
			return (Criteria) this;
		}

		public Criteria andMat_noEqualTo(String value) {
			addCriterion("mat_no =", value, "mat_no");
			return (Criteria) this;
		}

		public Criteria andMat_noNotEqualTo(String value) {
			addCriterion("mat_no <>", value, "mat_no");
			return (Criteria) this;
		}

		public Criteria andMat_noGreaterThan(String value) {
			addCriterion("mat_no >", value, "mat_no");
			return (Criteria) this;
		}

		public Criteria andMat_noGreaterThanOrEqualTo(String value) {
			addCriterion("mat_no >=", value, "mat_no");
			return (Criteria) this;
		}

		public Criteria andMat_noLessThan(String value) {
			addCriterion("mat_no <", value, "mat_no");
			return (Criteria) this;
		}

		public Criteria andMat_noLessThanOrEqualTo(String value) {
			addCriterion("mat_no <=", value, "mat_no");
			return (Criteria) this;
		}

		public Criteria andMat_noLike(String value) {
			addCriterion("mat_no like", value, "mat_no");
			return (Criteria) this;
		}

		public Criteria andMat_noNotLike(String value) {
			addCriterion("mat_no not like", value, "mat_no");
			return (Criteria) this;
		}

		public Criteria andMat_noIn(List<String> values) {
			addCriterion("mat_no in", values, "mat_no");
			return (Criteria) this;
		}

		public Criteria andMat_noNotIn(List<String> values) {
			addCriterion("mat_no not in", values, "mat_no");
			return (Criteria) this;
		}

		public Criteria andMat_noBetween(String value1, String value2) {
			addCriterion("mat_no between", value1, value2, "mat_no");
			return (Criteria) this;
		}

		public Criteria andMat_noNotBetween(String value1, String value2) {
			addCriterion("mat_no not between", value1, value2, "mat_no");
			return (Criteria) this;
		}

		public Criteria andInv_qtyIsNull() {
			addCriterion("inv_qty is null");
			return (Criteria) this;
		}

		public Criteria andInv_qtyIsNotNull() {
			addCriterion("inv_qty is not null");
			return (Criteria) this;
		}

		public Criteria andInv_qtyEqualTo(Integer value) {
			addCriterion("inv_qty =", value, "inv_qty");
			return (Criteria) this;
		}

		public Criteria andInv_qtyNotEqualTo(Integer value) {
			addCriterion("inv_qty <>", value, "inv_qty");
			return (Criteria) this;
		}

		public Criteria andInv_qtyGreaterThan(Integer value) {
			addCriterion("inv_qty >", value, "inv_qty");
			return (Criteria) this;
		}

		public Criteria andInv_qtyGreaterThanOrEqualTo(Integer value) {
			addCriterion("inv_qty >=", value, "inv_qty");
			return (Criteria) this;
		}

		public Criteria andInv_qtyLessThan(Integer value) {
			addCriterion("inv_qty <", value, "inv_qty");
			return (Criteria) this;
		}

		public Criteria andInv_qtyLessThanOrEqualTo(Integer value) {
			addCriterion("inv_qty <=", value, "inv_qty");
			return (Criteria) this;
		}

		public Criteria andInv_qtyIn(List<Integer> values) {
			addCriterion("inv_qty in", values, "inv_qty");
			return (Criteria) this;
		}

		public Criteria andInv_qtyNotIn(List<Integer> values) {
			addCriterion("inv_qty not in", values, "inv_qty");
			return (Criteria) this;
		}

		public Criteria andInv_qtyBetween(Integer value1, Integer value2) {
			addCriterion("inv_qty between", value1, value2, "inv_qty");
			return (Criteria) this;
		}

		public Criteria andInv_qtyNotBetween(Integer value1, Integer value2) {
			addCriterion("inv_qty not between", value1, value2, "inv_qty");
			return (Criteria) this;
		}

		public Criteria andStd_qtyIsNull() {
			addCriterion("std_qty is null");
			return (Criteria) this;
		}

		public Criteria andStd_qtyIsNotNull() {
			addCriterion("std_qty is not null");
			return (Criteria) this;
		}

		public Criteria andStd_qtyEqualTo(Integer value) {
			addCriterion("std_qty =", value, "std_qty");
			return (Criteria) this;
		}

		public Criteria andStd_qtyNotEqualTo(Integer value) {
			addCriterion("std_qty <>", value, "std_qty");
			return (Criteria) this;
		}

		public Criteria andStd_qtyGreaterThan(Integer value) {
			addCriterion("std_qty >", value, "std_qty");
			return (Criteria) this;
		}

		public Criteria andStd_qtyGreaterThanOrEqualTo(Integer value) {
			addCriterion("std_qty >=", value, "std_qty");
			return (Criteria) this;
		}

		public Criteria andStd_qtyLessThan(Integer value) {
			addCriterion("std_qty <", value, "std_qty");
			return (Criteria) this;
		}

		public Criteria andStd_qtyLessThanOrEqualTo(Integer value) {
			addCriterion("std_qty <=", value, "std_qty");
			return (Criteria) this;
		}

		public Criteria andStd_qtyIn(List<Integer> values) {
			addCriterion("std_qty in", values, "std_qty");
			return (Criteria) this;
		}

		public Criteria andStd_qtyNotIn(List<Integer> values) {
			addCriterion("std_qty not in", values, "std_qty");
			return (Criteria) this;
		}

		public Criteria andStd_qtyBetween(Integer value1, Integer value2) {
			addCriterion("std_qty between", value1, value2, "std_qty");
			return (Criteria) this;
		}

		public Criteria andStd_qtyNotBetween(Integer value1, Integer value2) {
			addCriterion("std_qty not between", value1, value2, "std_qty");
			return (Criteria) this;
		}

		public Criteria andWrd_qtyIsNull() {
			addCriterion("wrd_qty is null");
			return (Criteria) this;
		}

		public Criteria andWrd_qtyIsNotNull() {
			addCriterion("wrd_qty is not null");
			return (Criteria) this;
		}

		public Criteria andWrd_qtyEqualTo(Integer value) {
			addCriterion("wrd_qty =", value, "wrd_qty");
			return (Criteria) this;
		}

		public Criteria andWrd_qtyNotEqualTo(Integer value) {
			addCriterion("wrd_qty <>", value, "wrd_qty");
			return (Criteria) this;
		}

		public Criteria andWrd_qtyGreaterThan(Integer value) {
			addCriterion("wrd_qty >", value, "wrd_qty");
			return (Criteria) this;
		}

		public Criteria andWrd_qtyGreaterThanOrEqualTo(Integer value) {
			addCriterion("wrd_qty >=", value, "wrd_qty");
			return (Criteria) this;
		}

		public Criteria andWrd_qtyLessThan(Integer value) {
			addCriterion("wrd_qty <", value, "wrd_qty");
			return (Criteria) this;
		}

		public Criteria andWrd_qtyLessThanOrEqualTo(Integer value) {
			addCriterion("wrd_qty <=", value, "wrd_qty");
			return (Criteria) this;
		}

		public Criteria andWrd_qtyIn(List<Integer> values) {
			addCriterion("wrd_qty in", values, "wrd_qty");
			return (Criteria) this;
		}

		public Criteria andWrd_qtyNotIn(List<Integer> values) {
			addCriterion("wrd_qty not in", values, "wrd_qty");
			return (Criteria) this;
		}

		public Criteria andWrd_qtyBetween(Integer value1, Integer value2) {
			addCriterion("wrd_qty between", value1, value2, "wrd_qty");
			return (Criteria) this;
		}

		public Criteria andWrd_qtyNotBetween(Integer value1, Integer value2) {
			addCriterion("wrd_qty not between", value1, value2, "wrd_qty");
			return (Criteria) this;
		}

		public Criteria andWsp_qtyIsNull() {
			addCriterion("wsp_qty is null");
			return (Criteria) this;
		}

		public Criteria andWsp_qtyIsNotNull() {
			addCriterion("wsp_qty is not null");
			return (Criteria) this;
		}

		public Criteria andWsp_qtyEqualTo(Integer value) {
			addCriterion("wsp_qty =", value, "wsp_qty");
			return (Criteria) this;
		}

		public Criteria andWsp_qtyNotEqualTo(Integer value) {
			addCriterion("wsp_qty <>", value, "wsp_qty");
			return (Criteria) this;
		}

		public Criteria andWsp_qtyGreaterThan(Integer value) {
			addCriterion("wsp_qty >", value, "wsp_qty");
			return (Criteria) this;
		}

		public Criteria andWsp_qtyGreaterThanOrEqualTo(Integer value) {
			addCriterion("wsp_qty >=", value, "wsp_qty");
			return (Criteria) this;
		}

		public Criteria andWsp_qtyLessThan(Integer value) {
			addCriterion("wsp_qty <", value, "wsp_qty");
			return (Criteria) this;
		}

		public Criteria andWsp_qtyLessThanOrEqualTo(Integer value) {
			addCriterion("wsp_qty <=", value, "wsp_qty");
			return (Criteria) this;
		}

		public Criteria andWsp_qtyIn(List<Integer> values) {
			addCriterion("wsp_qty in", values, "wsp_qty");
			return (Criteria) this;
		}

		public Criteria andWsp_qtyNotIn(List<Integer> values) {
			addCriterion("wsp_qty not in", values, "wsp_qty");
			return (Criteria) this;
		}

		public Criteria andWsp_qtyBetween(Integer value1, Integer value2) {
			addCriterion("wsp_qty between", value1, value2, "wsp_qty");
			return (Criteria) this;
		}

		public Criteria andWsp_qtyNotBetween(Integer value1, Integer value2) {
			addCriterion("wsp_qty not between", value1, value2, "wsp_qty");
			return (Criteria) this;
		}

		public Criteria andRd_qtyIsNull() {
			addCriterion("rd_qty is null");
			return (Criteria) this;
		}

		public Criteria andRd_qtyIsNotNull() {
			addCriterion("rd_qty is not null");
			return (Criteria) this;
		}

		public Criteria andRd_qtyEqualTo(Integer value) {
			addCriterion("rd_qty =", value, "rd_qty");
			return (Criteria) this;
		}

		public Criteria andRd_qtyNotEqualTo(Integer value) {
			addCriterion("rd_qty <>", value, "rd_qty");
			return (Criteria) this;
		}

		public Criteria andRd_qtyGreaterThan(Integer value) {
			addCriterion("rd_qty >", value, "rd_qty");
			return (Criteria) this;
		}

		public Criteria andRd_qtyGreaterThanOrEqualTo(Integer value) {
			addCriterion("rd_qty >=", value, "rd_qty");
			return (Criteria) this;
		}

		public Criteria andRd_qtyLessThan(Integer value) {
			addCriterion("rd_qty <", value, "rd_qty");
			return (Criteria) this;
		}

		public Criteria andRd_qtyLessThanOrEqualTo(Integer value) {
			addCriterion("rd_qty <=", value, "rd_qty");
			return (Criteria) this;
		}

		public Criteria andRd_qtyIn(List<Integer> values) {
			addCriterion("rd_qty in", values, "rd_qty");
			return (Criteria) this;
		}

		public Criteria andRd_qtyNotIn(List<Integer> values) {
			addCriterion("rd_qty not in", values, "rd_qty");
			return (Criteria) this;
		}

		public Criteria andRd_qtyBetween(Integer value1, Integer value2) {
			addCriterion("rd_qty between", value1, value2, "rd_qty");
			return (Criteria) this;
		}

		public Criteria andRd_qtyNotBetween(Integer value1, Integer value2) {
			addCriterion("rd_qty not between", value1, value2, "rd_qty");
			return (Criteria) this;
		}

		public Criteria andRj_qtyIsNull() {
			addCriterion("rj_qty is null");
			return (Criteria) this;
		}

		public Criteria andRj_qtyIsNotNull() {
			addCriterion("rj_qty is not null");
			return (Criteria) this;
		}

		public Criteria andRj_qtyEqualTo(Integer value) {
			addCriterion("rj_qty =", value, "rj_qty");
			return (Criteria) this;
		}

		public Criteria andRj_qtyNotEqualTo(Integer value) {
			addCriterion("rj_qty <>", value, "rj_qty");
			return (Criteria) this;
		}

		public Criteria andRj_qtyGreaterThan(Integer value) {
			addCriterion("rj_qty >", value, "rj_qty");
			return (Criteria) this;
		}

		public Criteria andRj_qtyGreaterThanOrEqualTo(Integer value) {
			addCriterion("rj_qty >=", value, "rj_qty");
			return (Criteria) this;
		}

		public Criteria andRj_qtyLessThan(Integer value) {
			addCriterion("rj_qty <", value, "rj_qty");
			return (Criteria) this;
		}

		public Criteria andRj_qtyLessThanOrEqualTo(Integer value) {
			addCriterion("rj_qty <=", value, "rj_qty");
			return (Criteria) this;
		}

		public Criteria andRj_qtyIn(List<Integer> values) {
			addCriterion("rj_qty in", values, "rj_qty");
			return (Criteria) this;
		}

		public Criteria andRj_qtyNotIn(List<Integer> values) {
			addCriterion("rj_qty not in", values, "rj_qty");
			return (Criteria) this;
		}

		public Criteria andRj_qtyBetween(Integer value1, Integer value2) {
			addCriterion("rj_qty between", value1, value2, "rj_qty");
			return (Criteria) this;
		}

		public Criteria andRj_qtyNotBetween(Integer value1, Integer value2) {
			addCriterion("rj_qty not between", value1, value2, "rj_qty");
			return (Criteria) this;
		}

		public Criteria andBooking_qtyIsNull() {
			addCriterion("booking_qty is null");
			return (Criteria) this;
		}

		public Criteria andBooking_qtyIsNotNull() {
			addCriterion("booking_qty is not null");
			return (Criteria) this;
		}

		public Criteria andBooking_qtyEqualTo(Integer value) {
			addCriterion("booking_qty =", value, "booking_qty");
			return (Criteria) this;
		}

		public Criteria andBooking_qtyNotEqualTo(Integer value) {
			addCriterion("booking_qty <>", value, "booking_qty");
			return (Criteria) this;
		}

		public Criteria andBooking_qtyGreaterThan(Integer value) {
			addCriterion("booking_qty >", value, "booking_qty");
			return (Criteria) this;
		}

		public Criteria andBooking_qtyGreaterThanOrEqualTo(Integer value) {
			addCriterion("booking_qty >=", value, "booking_qty");
			return (Criteria) this;
		}

		public Criteria andBooking_qtyLessThan(Integer value) {
			addCriterion("booking_qty <", value, "booking_qty");
			return (Criteria) this;
		}

		public Criteria andBooking_qtyLessThanOrEqualTo(Integer value) {
			addCriterion("booking_qty <=", value, "booking_qty");
			return (Criteria) this;
		}

		public Criteria andBooking_qtyIn(List<Integer> values) {
			addCriterion("booking_qty in", values, "booking_qty");
			return (Criteria) this;
		}

		public Criteria andBooking_qtyNotIn(List<Integer> values) {
			addCriterion("booking_qty not in", values, "booking_qty");
			return (Criteria) this;
		}

		public Criteria andBooking_qtyBetween(Integer value1, Integer value2) {
			addCriterion("booking_qty between", value1, value2, "booking_qty");
			return (Criteria) this;
		}

		public Criteria andBooking_qtyNotBetween(Integer value1, Integer value2) {
			addCriterion("booking_qty not between", value1, value2,
					"booking_qty");
			return (Criteria) this;
		}

		public Criteria andCr_userIsNull() {
			addCriterion("cr_user is null");
			return (Criteria) this;
		}

		public Criteria andCr_userIsNotNull() {
			addCriterion("cr_user is not null");
			return (Criteria) this;
		}

		public Criteria andCr_userEqualTo(String value) {
			addCriterion("cr_user =", value, "cr_user");
			return (Criteria) this;
		}

		public Criteria andCr_userNotEqualTo(String value) {
			addCriterion("cr_user <>", value, "cr_user");
			return (Criteria) this;
		}

		public Criteria andCr_userGreaterThan(String value) {
			addCriterion("cr_user >", value, "cr_user");
			return (Criteria) this;
		}

		public Criteria andCr_userGreaterThanOrEqualTo(String value) {
			addCriterion("cr_user >=", value, "cr_user");
			return (Criteria) this;
		}

		public Criteria andCr_userLessThan(String value) {
			addCriterion("cr_user <", value, "cr_user");
			return (Criteria) this;
		}

		public Criteria andCr_userLessThanOrEqualTo(String value) {
			addCriterion("cr_user <=", value, "cr_user");
			return (Criteria) this;
		}

		public Criteria andCr_userLike(String value) {
			addCriterion("cr_user like", value, "cr_user");
			return (Criteria) this;
		}

		public Criteria andCr_userNotLike(String value) {
			addCriterion("cr_user not like", value, "cr_user");
			return (Criteria) this;
		}

		public Criteria andCr_userIn(List<String> values) {
			addCriterion("cr_user in", values, "cr_user");
			return (Criteria) this;
		}

		public Criteria andCr_userNotIn(List<String> values) {
			addCriterion("cr_user not in", values, "cr_user");
			return (Criteria) this;
		}

		public Criteria andCr_userBetween(String value1, String value2) {
			addCriterion("cr_user between", value1, value2, "cr_user");
			return (Criteria) this;
		}

		public Criteria andCr_userNotBetween(String value1, String value2) {
			addCriterion("cr_user not between", value1, value2, "cr_user");
			return (Criteria) this;
		}

		public Criteria andCr_timeIsNull() {
			addCriterion("cr_time is null");
			return (Criteria) this;
		}

		public Criteria andCr_timeIsNotNull() {
			addCriterion("cr_time is not null");
			return (Criteria) this;
		}

		public Criteria andCr_timeEqualTo(Date value) {
			addCriterion("cr_time =", value, "cr_time");
			return (Criteria) this;
		}

		public Criteria andCr_timeNotEqualTo(Date value) {
			addCriterion("cr_time <>", value, "cr_time");
			return (Criteria) this;
		}

		public Criteria andCr_timeGreaterThan(Date value) {
			addCriterion("cr_time >", value, "cr_time");
			return (Criteria) this;
		}

		public Criteria andCr_timeGreaterThanOrEqualTo(Date value) {
			addCriterion("cr_time >=", value, "cr_time");
			return (Criteria) this;
		}

		public Criteria andCr_timeLessThan(Date value) {
			addCriterion("cr_time <", value, "cr_time");
			return (Criteria) this;
		}

		public Criteria andCr_timeLessThanOrEqualTo(Date value) {
			addCriterion("cr_time <=", value, "cr_time");
			return (Criteria) this;
		}

		public Criteria andCr_timeIn(List<Date> values) {
			addCriterion("cr_time in", values, "cr_time");
			return (Criteria) this;
		}

		public Criteria andCr_timeNotIn(List<Date> values) {
			addCriterion("cr_time not in", values, "cr_time");
			return (Criteria) this;
		}

		public Criteria andCr_timeBetween(Date value1, Date value2) {
			addCriterion("cr_time between", value1, value2, "cr_time");
			return (Criteria) this;
		}

		public Criteria andCr_timeNotBetween(Date value1, Date value2) {
			addCriterion("cr_time not between", value1, value2, "cr_time");
			return (Criteria) this;
		}

		public Criteria andMd_userIsNull() {
			addCriterion("md_user is null");
			return (Criteria) this;
		}

		public Criteria andMd_userIsNotNull() {
			addCriterion("md_user is not null");
			return (Criteria) this;
		}

		public Criteria andMd_userEqualTo(String value) {
			addCriterion("md_user =", value, "md_user");
			return (Criteria) this;
		}

		public Criteria andMd_userNotEqualTo(String value) {
			addCriterion("md_user <>", value, "md_user");
			return (Criteria) this;
		}

		public Criteria andMd_userGreaterThan(String value) {
			addCriterion("md_user >", value, "md_user");
			return (Criteria) this;
		}

		public Criteria andMd_userGreaterThanOrEqualTo(String value) {
			addCriterion("md_user >=", value, "md_user");
			return (Criteria) this;
		}

		public Criteria andMd_userLessThan(String value) {
			addCriterion("md_user <", value, "md_user");
			return (Criteria) this;
		}

		public Criteria andMd_userLessThanOrEqualTo(String value) {
			addCriterion("md_user <=", value, "md_user");
			return (Criteria) this;
		}

		public Criteria andMd_userLike(String value) {
			addCriterion("md_user like", value, "md_user");
			return (Criteria) this;
		}

		public Criteria andMd_userNotLike(String value) {
			addCriterion("md_user not like", value, "md_user");
			return (Criteria) this;
		}

		public Criteria andMd_userIn(List<String> values) {
			addCriterion("md_user in", values, "md_user");
			return (Criteria) this;
		}

		public Criteria andMd_userNotIn(List<String> values) {
			addCriterion("md_user not in", values, "md_user");
			return (Criteria) this;
		}

		public Criteria andMd_userBetween(String value1, String value2) {
			addCriterion("md_user between", value1, value2, "md_user");
			return (Criteria) this;
		}

		public Criteria andMd_userNotBetween(String value1, String value2) {
			addCriterion("md_user not between", value1, value2, "md_user");
			return (Criteria) this;
		}

		public Criteria andMd_timeIsNull() {
			addCriterion("md_time is null");
			return (Criteria) this;
		}

		public Criteria andMd_timeIsNotNull() {
			addCriterion("md_time is not null");
			return (Criteria) this;
		}

		public Criteria andMd_timeEqualTo(Date value) {
			addCriterion("md_time =", value, "md_time");
			return (Criteria) this;
		}

		public Criteria andMd_timeNotEqualTo(Date value) {
			addCriterion("md_time <>", value, "md_time");
			return (Criteria) this;
		}

		public Criteria andMd_timeGreaterThan(Date value) {
			addCriterion("md_time >", value, "md_time");
			return (Criteria) this;
		}

		public Criteria andMd_timeGreaterThanOrEqualTo(Date value) {
			addCriterion("md_time >=", value, "md_time");
			return (Criteria) this;
		}

		public Criteria andMd_timeLessThan(Date value) {
			addCriterion("md_time <", value, "md_time");
			return (Criteria) this;
		}

		public Criteria andMd_timeLessThanOrEqualTo(Date value) {
			addCriterion("md_time <=", value, "md_time");
			return (Criteria) this;
		}

		public Criteria andMd_timeIn(List<Date> values) {
			addCriterion("md_time in", values, "md_time");
			return (Criteria) this;
		}

		public Criteria andMd_timeNotIn(List<Date> values) {
			addCriterion("md_time not in", values, "md_time");
			return (Criteria) this;
		}

		public Criteria andMd_timeBetween(Date value1, Date value2) {
			addCriterion("md_time between", value1, value2, "md_time");
			return (Criteria) this;
		}

		public Criteria andMd_timeNotBetween(Date value1, Date value2) {
			addCriterion("md_time not between", value1, value2, "md_time");
			return (Criteria) this;
		}

		public Criteria andPo_noIsNull() {
			addCriterion("po_no is null");
			return (Criteria) this;
		}

		public Criteria andPo_noIsNotNull() {
			addCriterion("po_no is not null");
			return (Criteria) this;
		}

		public Criteria andPo_noEqualTo(String value) {
			addCriterion("po_no =", value, "po_no");
			return (Criteria) this;
		}

		public Criteria andPo_noNotEqualTo(String value) {
			addCriterion("po_no <>", value, "po_no");
			return (Criteria) this;
		}

		public Criteria andPo_noGreaterThan(String value) {
			addCriterion("po_no >", value, "po_no");
			return (Criteria) this;
		}

		public Criteria andPo_noGreaterThanOrEqualTo(String value) {
			addCriterion("po_no >=", value, "po_no");
			return (Criteria) this;
		}

		public Criteria andPo_noLessThan(String value) {
			addCriterion("po_no <", value, "po_no");
			return (Criteria) this;
		}

		public Criteria andPo_noLessThanOrEqualTo(String value) {
			addCriterion("po_no <=", value, "po_no");
			return (Criteria) this;
		}

		public Criteria andPo_noLike(String value) {
			addCriterion("po_no like", value, "po_no");
			return (Criteria) this;
		}

		public Criteria andPo_noNotLike(String value) {
			addCriterion("po_no not like", value, "po_no");
			return (Criteria) this;
		}

		public Criteria andPo_noIn(List<String> values) {
			addCriterion("po_no in", values, "po_no");
			return (Criteria) this;
		}

		public Criteria andPo_noNotIn(List<String> values) {
			addCriterion("po_no not in", values, "po_no");
			return (Criteria) this;
		}

		public Criteria andPo_noBetween(String value1, String value2) {
			addCriterion("po_no between", value1, value2, "po_no");
			return (Criteria) this;
		}

		public Criteria andPo_noNotBetween(String value1, String value2) {
			addCriterion("po_no not between", value1, value2, "po_no");
			return (Criteria) this;
		}

		public Criteria andPo_line_nbrIsNull() {
			addCriterion("po_line_nbr is null");
			return (Criteria) this;
		}

		public Criteria andPo_line_nbrIsNotNull() {
			addCriterion("po_line_nbr is not null");
			return (Criteria) this;
		}

		public Criteria andPo_line_nbrEqualTo(Short value) {
			addCriterion("po_line_nbr =", value, "po_line_nbr");
			return (Criteria) this;
		}

		public Criteria andPo_line_nbrNotEqualTo(Short value) {
			addCriterion("po_line_nbr <>", value, "po_line_nbr");
			return (Criteria) this;
		}

		public Criteria andPo_line_nbrGreaterThan(Short value) {
			addCriterion("po_line_nbr >", value, "po_line_nbr");
			return (Criteria) this;
		}

		public Criteria andPo_line_nbrGreaterThanOrEqualTo(Short value) {
			addCriterion("po_line_nbr >=", value, "po_line_nbr");
			return (Criteria) this;
		}

		public Criteria andPo_line_nbrLessThan(Short value) {
			addCriterion("po_line_nbr <", value, "po_line_nbr");
			return (Criteria) this;
		}

		public Criteria andPo_line_nbrLessThanOrEqualTo(Short value) {
			addCriterion("po_line_nbr <=", value, "po_line_nbr");
			return (Criteria) this;
		}

		public Criteria andPo_line_nbrIn(List<Short> values) {
			addCriterion("po_line_nbr in", values, "po_line_nbr");
			return (Criteria) this;
		}

		public Criteria andPo_line_nbrNotIn(List<Short> values) {
			addCriterion("po_line_nbr not in", values, "po_line_nbr");
			return (Criteria) this;
		}

		public Criteria andPo_line_nbrBetween(Short value1, Short value2) {
			addCriterion("po_line_nbr between", value1, value2, "po_line_nbr");
			return (Criteria) this;
		}

		public Criteria andPo_line_nbrNotBetween(Short value1, Short value2) {
			addCriterion("po_line_nbr not between", value1, value2,
					"po_line_nbr");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table TB_INV_INV
	 * @mbggenerated  Mon Nov 24 10:11:47 CST 2014
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
     * This class corresponds to the database table TB_INV_INV
     *
     * @mbggenerated do_not_delete_during_merge Wed Nov 19 15:11:03 CST 2014
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}