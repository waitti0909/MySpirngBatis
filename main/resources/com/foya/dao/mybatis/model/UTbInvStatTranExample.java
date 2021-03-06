package com.foya.dao.mybatis.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UTbInvStatTranExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table TB_INV_STAT_TRAN
	 * @mbggenerated  Tue Nov 25 16:47:57 CST 2014
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table TB_INV_STAT_TRAN
	 * @mbggenerated  Tue Nov 25 16:47:57 CST 2014
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table TB_INV_STAT_TRAN
	 * @mbggenerated  Tue Nov 25 16:47:57 CST 2014
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_STAT_TRAN
	 * @mbggenerated  Tue Nov 25 16:47:57 CST 2014
	 */
	public UTbInvStatTranExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_STAT_TRAN
	 * @mbggenerated  Tue Nov 25 16:47:57 CST 2014
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_STAT_TRAN
	 * @mbggenerated  Tue Nov 25 16:47:57 CST 2014
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_STAT_TRAN
	 * @mbggenerated  Tue Nov 25 16:47:57 CST 2014
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_STAT_TRAN
	 * @mbggenerated  Tue Nov 25 16:47:57 CST 2014
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_STAT_TRAN
	 * @mbggenerated  Tue Nov 25 16:47:57 CST 2014
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_STAT_TRAN
	 * @mbggenerated  Tue Nov 25 16:47:57 CST 2014
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_STAT_TRAN
	 * @mbggenerated  Tue Nov 25 16:47:57 CST 2014
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_STAT_TRAN
	 * @mbggenerated  Tue Nov 25 16:47:57 CST 2014
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_STAT_TRAN
	 * @mbggenerated  Tue Nov 25 16:47:57 CST 2014
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_STAT_TRAN
	 * @mbggenerated  Tue Nov 25 16:47:57 CST 2014
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table TB_INV_STAT_TRAN
	 * @mbggenerated  Tue Nov 25 16:47:57 CST 2014
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

		public Criteria andInv_tran_noIsNull() {
			addCriterion("a.inv_tran_no is null");
			return (Criteria) this;
		}

		public Criteria andInv_tran_noIsNotNull() {
			addCriterion("a.inv_tran_no is not null");
			return (Criteria) this;
		}

		public Criteria andInv_tran_noEqualTo(String value) {
			addCriterion("a.inv_tran_no =", value, "inv_tran_no");
			return (Criteria) this;
		}

		public Criteria andInv_tran_noNotEqualTo(String value) {
			addCriterion("a.inv_tran_no <>", value, "inv_tran_no");
			return (Criteria) this;
		}

		public Criteria andInv_tran_noGreaterThan(String value) {
			addCriterion("a.inv_tran_no >", value, "inv_tran_no");
			return (Criteria) this;
		}

		public Criteria andInv_tran_noGreaterThanOrEqualTo(String value) {
			addCriterion("a.inv_tran_no >=", value, "inv_tran_no");
			return (Criteria) this;
		}

		public Criteria andInv_tran_noLessThan(String value) {
			addCriterion("a.inv_tran_no <", value, "inv_tran_no");
			return (Criteria) this;
		}

		public Criteria andInv_tran_noLessThanOrEqualTo(String value) {
			addCriterion("a.inv_tran_no <=", value, "inv_tran_no");
			return (Criteria) this;
		}

		public Criteria andInv_tran_noLike(String value) {
			addCriterion("a.inv_tran_no like", value, "inv_tran_no");
			return (Criteria) this;
		}

		public Criteria andInv_tran_noNotLike(String value) {
			addCriterion("a.inv_tran_no not like", value, "inv_tran_no");
			return (Criteria) this;
		}

		public Criteria andInv_tran_noIn(List<String> values) {
			addCriterion("a.inv_tran_no in", values, "inv_tran_no");
			return (Criteria) this;
		}

		public Criteria andInv_tran_noNotIn(List<String> values) {
			addCriterion("a.inv_tran_no not in", values, "inv_tran_no");
			return (Criteria) this;
		}

		public Criteria andInv_tran_noBetween(String value1, String value2) {
			addCriterion("a.inv_tran_no between", value1, value2, "inv_tran_no");
			return (Criteria) this;
		}

		public Criteria andInv_tran_noNotBetween(String value1, String value2) {
			addCriterion("a.inv_tran_no not between", value1, value2,
					"inv_tran_no");
			return (Criteria) this;
		}

		public Criteria andMat_noIsNull() {
			addCriterion("a.mat_no is null");
			return (Criteria) this;
		}

		public Criteria andMat_noIsNotNull() {
			addCriterion("a.mat_no is not null");
			return (Criteria) this;
		}

		public Criteria andMat_noEqualTo(String value) {
			addCriterion("a.mat_no =", value, "mat_no");
			return (Criteria) this;
		}

		public Criteria andMat_noNotEqualTo(String value) {
			addCriterion("a.mat_no <>", value, "mat_no");
			return (Criteria) this;
		}

		public Criteria andMat_noGreaterThan(String value) {
			addCriterion("a.mat_no >", value, "mat_no");
			return (Criteria) this;
		}

		public Criteria andMat_noGreaterThanOrEqualTo(String value) {
			addCriterion("a.mat_no >=", value, "mat_no");
			return (Criteria) this;
		}

		public Criteria andMat_noLessThan(String value) {
			addCriterion("a.mat_no <", value, "mat_no");
			return (Criteria) this;
		}

		public Criteria andMat_noLessThanOrEqualTo(String value) {
			addCriterion("a.mat_no <=", value, "mat_no");
			return (Criteria) this;
		}

		public Criteria andMat_noLike(String value) {
			addCriterion("a.mat_no like", value, "mat_no");
			return (Criteria) this;
		}

		public Criteria andMat_noNotLike(String value) {
			addCriterion("a.mat_no not like", value, "mat_no");
			return (Criteria) this;
		}

		public Criteria andMat_noIn(List<String> values) {
			addCriterion("a.mat_no in", values, "mat_no");
			return (Criteria) this;
		}

		public Criteria andMat_noNotIn(List<String> values) {
			addCriterion("a.mat_no not in", values, "mat_no");
			return (Criteria) this;
		}

		public Criteria andMat_noBetween(String value1, String value2) {
			addCriterion("a.mat_no between", value1, value2, "mat_no");
			return (Criteria) this;
		}

		public Criteria andMat_noNotBetween(String value1, String value2) {
			addCriterion("a.mat_no not between", value1, value2, "mat_no");
			return (Criteria) this;
		}

		public Criteria andWh_idIsNull() {
			addCriterion("a.wh_id is null");
			return (Criteria) this;
		}

		public Criteria andWh_idIsNotNull() {
			addCriterion("a.wh_id is not null");
			return (Criteria) this;
		}

		public Criteria andWh_idEqualTo(String value) {
			addCriterion("a.wh_id =", value, "wh_id");
			return (Criteria) this;
		}

		public Criteria andWh_idNotEqualTo(String value) {
			addCriterion("a.wh_id <>", value, "wh_id");
			return (Criteria) this;
		}

		public Criteria andWh_idGreaterThan(String value) {
			addCriterion("a.wh_id >", value, "wh_id");
			return (Criteria) this;
		}

		public Criteria andWh_idGreaterThanOrEqualTo(String value) {
			addCriterion("a.wh_id >=", value, "wh_id");
			return (Criteria) this;
		}

		public Criteria andWh_idLessThan(String value) {
			addCriterion("a.wh_id <", value, "wh_id");
			return (Criteria) this;
		}

		public Criteria andWh_idLessThanOrEqualTo(String value) {
			addCriterion("a.wh_id <=", value, "wh_id");
			return (Criteria) this;
		}

		public Criteria andWh_idLike(String value) {
			addCriterion("a.wh_id like", value, "wh_id");
			return (Criteria) this;
		}

		public Criteria andWh_idNotLike(String value) {
			addCriterion("a.wh_id not like", value, "wh_id");
			return (Criteria) this;
		}

		public Criteria andWh_idIn(List<String> values) {
			addCriterion("a.wh_id in", values, "wh_id");
			return (Criteria) this;
		}

		public Criteria andWh_idNotIn(List<String> values) {
			addCriterion("a.wh_id not in", values, "wh_id");
			return (Criteria) this;
		}

		public Criteria andWh_idBetween(String value1, String value2) {
			addCriterion("a.wh_id between", value1, value2, "wh_id");
			return (Criteria) this;
		}

		public Criteria andWh_idNotBetween(String value1, String value2) {
			addCriterion("a.wh_id not between", value1, value2, "wh_id");
			return (Criteria) this;
		}

		public Criteria andTran_reasonIsNull() {
			addCriterion("a.tran_reason is null");
			return (Criteria) this;
		}

		public Criteria andTran_reasonIsNotNull() {
			addCriterion("a.tran_reason is not null");
			return (Criteria) this;
		}

		public Criteria andTran_reasonEqualTo(String value) {
			addCriterion("a.tran_reason =", value, "tran_reason");
			return (Criteria) this;
		}

		public Criteria andTran_reasonNotEqualTo(String value) {
			addCriterion("a.tran_reason <>", value, "tran_reason");
			return (Criteria) this;
		}

		public Criteria andTran_reasonGreaterThan(String value) {
			addCriterion("a.tran_reason >", value, "tran_reason");
			return (Criteria) this;
		}

		public Criteria andTran_reasonGreaterThanOrEqualTo(String value) {
			addCriterion("a.tran_reason >=", value, "tran_reason");
			return (Criteria) this;
		}

		public Criteria andTran_reasonLessThan(String value) {
			addCriterion("a.tran_reason <", value, "tran_reason");
			return (Criteria) this;
		}

		public Criteria andTran_reasonLessThanOrEqualTo(String value) {
			addCriterion("a.tran_reason <=", value, "tran_reason");
			return (Criteria) this;
		}

		public Criteria andTran_reasonLike(String value) {
			addCriterion("a.tran_reason like", value, "tran_reason");
			return (Criteria) this;
		}

		public Criteria andTran_reasonNotLike(String value) {
			addCriterion("a.tran_reason not like", value, "tran_reason");
			return (Criteria) this;
		}

		public Criteria andTran_reasonIn(List<String> values) {
			addCriterion("a.tran_reason in", values, "tran_reason");
			return (Criteria) this;
		}

		public Criteria andTran_reasonNotIn(List<String> values) {
			addCriterion("a.tran_reason not in", values, "tran_reason");
			return (Criteria) this;
		}

		public Criteria andTran_reasonBetween(String value1, String value2) {
			addCriterion("a.tran_reason between", value1, value2, "tran_reason");
			return (Criteria) this;
		}

		public Criteria andTran_reasonNotBetween(String value1, String value2) {
			addCriterion("a.tran_reason not between", value1, value2,
					"tran_reason");
			return (Criteria) this;
		}

		public Criteria andTran_qtyIsNull() {
			addCriterion("a.tran_qty is null");
			return (Criteria) this;
		}

		public Criteria andTran_qtyIsNotNull() {
			addCriterion("a.tran_qty is not null");
			return (Criteria) this;
		}

		public Criteria andTran_qtyEqualTo(Integer value) {
			addCriterion("a.tran_qty =", value, "tran_qty");
			return (Criteria) this;
		}

		public Criteria andTran_qtyNotEqualTo(Integer value) {
			addCriterion("a.tran_qty <>", value, "tran_qty");
			return (Criteria) this;
		}

		public Criteria andTran_qtyGreaterThan(Integer value) {
			addCriterion("a.tran_qty >", value, "tran_qty");
			return (Criteria) this;
		}

		public Criteria andTran_qtyGreaterThanOrEqualTo(Integer value) {
			addCriterion("a.tran_qty >=", value, "tran_qty");
			return (Criteria) this;
		}

		public Criteria andTran_qtyLessThan(Integer value) {
			addCriterion("a.tran_qty <", value, "tran_qty");
			return (Criteria) this;
		}

		public Criteria andTran_qtyLessThanOrEqualTo(Integer value) {
			addCriterion("a.tran_qty <=", value, "tran_qty");
			return (Criteria) this;
		}

		public Criteria andTran_qtyIn(List<Integer> values) {
			addCriterion("a.tran_qty in", values, "tran_qty");
			return (Criteria) this;
		}

		public Criteria andTran_qtyNotIn(List<Integer> values) {
			addCriterion("a.tran_qty not in", values, "tran_qty");
			return (Criteria) this;
		}

		public Criteria andTran_qtyBetween(Integer value1, Integer value2) {
			addCriterion("a.tran_qty between", value1, value2, "tran_qty");
			return (Criteria) this;
		}

		public Criteria andTran_qtyNotBetween(Integer value1, Integer value2) {
			addCriterion("a.tran_qty not between", value1, value2, "tran_qty");
			return (Criteria) this;
		}

		public Criteria andInv_tran_audit_statusIsNull() {
			addCriterion("a.inv_tran_audit_status is null");
			return (Criteria) this;
		}

		public Criteria andInv_tran_audit_statusIsNotNull() {
			addCriterion("a.inv_tran_audit_status is not null");
			return (Criteria) this;
		}

		public Criteria andInv_tran_audit_statusEqualTo(Byte value) {
			addCriterion("a.inv_tran_audit_status =", value,
					"inv_tran_audit_status");
			return (Criteria) this;
		}

		public Criteria andInv_tran_audit_statusNotEqualTo(Byte value) {
			addCriterion("a.inv_tran_audit_status <>", value,
					"inv_tran_audit_status");
			return (Criteria) this;
		}

		public Criteria andInv_tran_audit_statusGreaterThan(Byte value) {
			addCriterion("a.inv_tran_audit_status >", value,
					"inv_tran_audit_status");
			return (Criteria) this;
		}

		public Criteria andInv_tran_audit_statusGreaterThanOrEqualTo(Byte value) {
			addCriterion("a.inv_tran_audit_status >=", value,
					"inv_tran_audit_status");
			return (Criteria) this;
		}

		public Criteria andInv_tran_audit_statusLessThan(Byte value) {
			addCriterion("a.inv_tran_audit_status <", value,
					"inv_tran_audit_status");
			return (Criteria) this;
		}

		public Criteria andInv_tran_audit_statusLessThanOrEqualTo(Byte value) {
			addCriterion("a.inv_tran_audit_status <=", value,
					"inv_tran_audit_status");
			return (Criteria) this;
		}

		public Criteria andInv_tran_audit_statusIn(List<Byte> values) {
			addCriterion("a.inv_tran_audit_status in", values,
					"inv_tran_audit_status");
			return (Criteria) this;
		}

		public Criteria andInv_tran_audit_statusNotIn(List<Byte> values) {
			addCriterion("a.inv_tran_audit_status not in", values,
					"inv_tran_audit_status");
			return (Criteria) this;
		}

		public Criteria andInv_tran_audit_statusBetween(Byte value1, Byte value2) {
			addCriterion("a.inv_tran_audit_status between", value1, value2,
					"inv_tran_audit_status");
			return (Criteria) this;
		}

		public Criteria andInv_tran_audit_statusNotBetween(Byte value1,
				Byte value2) {
			addCriterion("a.inv_tran_audit_status not between", value1, value2,
					"inv_tran_audit_status");
			return (Criteria) this;
		}

		public Criteria andNew_mat_statusIsNull() {
			addCriterion("a.new_mat_status is null");
			return (Criteria) this;
		}

		public Criteria andNew_mat_statusIsNotNull() {
			addCriterion("a.new_mat_status is not null");
			return (Criteria) this;
		}

		public Criteria andNew_mat_statusEqualTo(Byte value) {
			addCriterion("a.new_mat_status =", value, "new_mat_status");
			return (Criteria) this;
		}

		public Criteria andNew_mat_statusNotEqualTo(Byte value) {
			addCriterion("a.new_mat_status <>", value, "new_mat_status");
			return (Criteria) this;
		}

		public Criteria andNew_mat_statusGreaterThan(Byte value) {
			addCriterion("a.new_mat_status >", value, "new_mat_status");
			return (Criteria) this;
		}

		public Criteria andNew_mat_statusGreaterThanOrEqualTo(Byte value) {
			addCriterion("a.new_mat_status >=", value, "new_mat_status");
			return (Criteria) this;
		}

		public Criteria andNew_mat_statusLessThan(Byte value) {
			addCriterion("a.new_mat_status <", value, "new_mat_status");
			return (Criteria) this;
		}

		public Criteria andNew_mat_statusLessThanOrEqualTo(Byte value) {
			addCriterion("a.new_mat_status <=", value, "new_mat_status");
			return (Criteria) this;
		}

		public Criteria andNew_mat_statusIn(List<Byte> values) {
			addCriterion("a.new_mat_status in", values, "new_mat_status");
			return (Criteria) this;
		}

		public Criteria andNew_mat_statusNotIn(List<Byte> values) {
			addCriterion("a.new_mat_status not in", values, "new_mat_status");
			return (Criteria) this;
		}

		public Criteria andNew_mat_statusBetween(Byte value1, Byte value2) {
			addCriterion("a.new_mat_status between", value1, value2,
					"new_mat_status");
			return (Criteria) this;
		}

		public Criteria andNew_mat_statusNotBetween(Byte value1, Byte value2) {
			addCriterion("a.new_mat_status not between", value1, value2,
					"new_mat_status");
			return (Criteria) this;
		}

		public Criteria andOld_mat_statusIsNull() {
			addCriterion("a.old_mat_status is null");
			return (Criteria) this;
		}

		public Criteria andOld_mat_statusIsNotNull() {
			addCriterion("a.old_mat_status is not null");
			return (Criteria) this;
		}

		public Criteria andOld_mat_statusEqualTo(Byte value) {
			addCriterion("a.old_mat_status =", value, "old_mat_status");
			return (Criteria) this;
		}

		public Criteria andOld_mat_statusNotEqualTo(Byte value) {
			addCriterion("a.old_mat_status <>", value, "old_mat_status");
			return (Criteria) this;
		}

		public Criteria andOld_mat_statusGreaterThan(Byte value) {
			addCriterion("a.old_mat_status >", value, "old_mat_status");
			return (Criteria) this;
		}

		public Criteria andOld_mat_statusGreaterThanOrEqualTo(Byte value) {
			addCriterion("a.old_mat_status >=", value, "old_mat_status");
			return (Criteria) this;
		}

		public Criteria andOld_mat_statusLessThan(Byte value) {
			addCriterion("a.old_mat_status <", value, "old_mat_status");
			return (Criteria) this;
		}

		public Criteria andOld_mat_statusLessThanOrEqualTo(Byte value) {
			addCriterion("a.old_mat_status <=", value, "old_mat_status");
			return (Criteria) this;
		}

		public Criteria andOld_mat_statusIn(List<Byte> values) {
			addCriterion("a.old_mat_status in", values, "old_mat_status");
			return (Criteria) this;
		}

		public Criteria andOld_mat_statusNotIn(List<Byte> values) {
			addCriterion("a.old_mat_status not in", values, "old_mat_status");
			return (Criteria) this;
		}

		public Criteria andOld_mat_statusBetween(Byte value1, Byte value2) {
			addCriterion("a.old_mat_status between", value1, value2,
					"old_mat_status");
			return (Criteria) this;
		}

		public Criteria andOld_mat_statusNotBetween(Byte value1, Byte value2) {
			addCriterion("a.old_mat_status not between", value1, value2,
					"old_mat_status");
			return (Criteria) this;
		}

		public Criteria andTran_userIsNull() {
			addCriterion("a.tran_user is null");
			return (Criteria) this;
		}

		public Criteria andTran_userIsNotNull() {
			addCriterion("a.tran_user is not null");
			return (Criteria) this;
		}

		public Criteria andTran_userEqualTo(String value) {
			addCriterion("a.tran_user =", value, "tran_user");
			return (Criteria) this;
		}

		public Criteria andTran_userNotEqualTo(String value) {
			addCriterion("a.tran_user <>", value, "tran_user");
			return (Criteria) this;
		}

		public Criteria andTran_userGreaterThan(String value) {
			addCriterion("a.tran_user >", value, "tran_user");
			return (Criteria) this;
		}

		public Criteria andTran_userGreaterThanOrEqualTo(String value) {
			addCriterion("a.tran_user >=", value, "tran_user");
			return (Criteria) this;
		}

		public Criteria andTran_userLessThan(String value) {
			addCriterion("a.tran_user <", value, "tran_user");
			return (Criteria) this;
		}

		public Criteria andTran_userLessThanOrEqualTo(String value) {
			addCriterion("a.tran_user <=", value, "tran_user");
			return (Criteria) this;
		}

		public Criteria andTran_userLike(String value) {
			addCriterion("a.tran_user like", value, "tran_user");
			return (Criteria) this;
		}

		public Criteria andTran_userNotLike(String value) {
			addCriterion("a.tran_user not like", value, "tran_user");
			return (Criteria) this;
		}

		public Criteria andTran_userIn(List<String> values) {
			addCriterion("a.tran_user in", values, "tran_user");
			return (Criteria) this;
		}

		public Criteria andTran_userNotIn(List<String> values) {
			addCriterion("a.tran_user not in", values, "tran_user");
			return (Criteria) this;
		}

		public Criteria andTran_userBetween(String value1, String value2) {
			addCriterion("a.tran_user between", value1, value2, "tran_user");
			return (Criteria) this;
		}

		public Criteria andTran_userNotBetween(String value1, String value2) {
			addCriterion("a.tran_user not between", value1, value2, "tran_user");
			return (Criteria) this;
		}

		public Criteria andTran_dateIsNull() {
			addCriterion("a.tran_date is null");
			return (Criteria) this;
		}

		public Criteria andTran_dateIsNotNull() {
			addCriterion("a.tran_date is not null");
			return (Criteria) this;
		}

		public Criteria andTran_dateEqualTo(Date value) {
			addCriterion("a.tran_date =", value, "tran_date");
			return (Criteria) this;
		}

		public Criteria andTran_dateNotEqualTo(Date value) {
			addCriterion("a.tran_date <>", value, "tran_date");
			return (Criteria) this;
		}

		public Criteria andTran_dateGreaterThan(Date value) {
			addCriterion("a.tran_date >", value, "tran_date");
			return (Criteria) this;
		}

		public Criteria andTran_dateGreaterThanOrEqualTo(Date value) {
			addCriterion("a.tran_date >=", value, "tran_date");
			return (Criteria) this;
		}

		public Criteria andTran_dateLessThan(Date value) {
			addCriterion("a.tran_date <", value, "tran_date");
			return (Criteria) this;
		}

		public Criteria andTran_dateLessThanOrEqualTo(Date value) {
			addCriterion("a.tran_date <=", value, "tran_date");
			return (Criteria) this;
		}

		public Criteria andTran_dateIn(List<Date> values) {
			addCriterion("a.tran_date in", values, "tran_date");
			return (Criteria) this;
		}

		public Criteria andTran_dateNotIn(List<Date> values) {
			addCriterion("a.tran_date not in", values, "tran_date");
			return (Criteria) this;
		}

		public Criteria andTran_dateBetween(Date value1, Date value2) {
			addCriterion("a.tran_date between", value1, value2, "tran_date");
			return (Criteria) this;
		}

		public Criteria andTran_dateNotBetween(Date value1, Date value2) {
			addCriterion("a.tran_date not between", value1, value2, "tran_date");
			return (Criteria) this;
		}

		public Criteria andMemoIsNull() {
			addCriterion("a.memo is null");
			return (Criteria) this;
		}

		public Criteria andMemoIsNotNull() {
			addCriterion("a.memo is not null");
			return (Criteria) this;
		}

		public Criteria andMemoEqualTo(String value) {
			addCriterion("a.memo =", value, "memo");
			return (Criteria) this;
		}

		public Criteria andMemoNotEqualTo(String value) {
			addCriterion("a.memo <>", value, "memo");
			return (Criteria) this;
		}

		public Criteria andMemoGreaterThan(String value) {
			addCriterion("a.memo >", value, "memo");
			return (Criteria) this;
		}

		public Criteria andMemoGreaterThanOrEqualTo(String value) {
			addCriterion("a.memo >=", value, "memo");
			return (Criteria) this;
		}

		public Criteria andMemoLessThan(String value) {
			addCriterion("a.memo <", value, "memo");
			return (Criteria) this;
		}

		public Criteria andMemoLessThanOrEqualTo(String value) {
			addCriterion("a.memo <=", value, "memo");
			return (Criteria) this;
		}

		public Criteria andMemoLike(String value) {
			addCriterion("a.memo like", value, "memo");
			return (Criteria) this;
		}

		public Criteria andMemoNotLike(String value) {
			addCriterion("a.memo not like", value, "memo");
			return (Criteria) this;
		}

		public Criteria andMemoIn(List<String> values) {
			addCriterion("a.memo in", values, "memo");
			return (Criteria) this;
		}

		public Criteria andMemoNotIn(List<String> values) {
			addCriterion("a.memo not in", values, "memo");
			return (Criteria) this;
		}

		public Criteria andMemoBetween(String value1, String value2) {
			addCriterion("a.memo between", value1, value2, "memo");
			return (Criteria) this;
		}

		public Criteria andMemoNotBetween(String value1, String value2) {
			addCriterion("a.memo not between", value1, value2, "memo");
			return (Criteria) this;
		}

		public Criteria andCr_userIsNull() {
			addCriterion("a.cr_user is null");
			return (Criteria) this;
		}

		public Criteria andCr_userIsNotNull() {
			addCriterion("a.cr_user is not null");
			return (Criteria) this;
		}

		public Criteria andCr_userEqualTo(String value) {
			addCriterion("a.cr_user =", value, "cr_user");
			return (Criteria) this;
		}

		public Criteria andCr_userNotEqualTo(String value) {
			addCriterion("a.cr_user <>", value, "cr_user");
			return (Criteria) this;
		}

		public Criteria andCr_userGreaterThan(String value) {
			addCriterion("a.cr_user >", value, "cr_user");
			return (Criteria) this;
		}

		public Criteria andCr_userGreaterThanOrEqualTo(String value) {
			addCriterion("a.cr_user >=", value, "cr_user");
			return (Criteria) this;
		}

		public Criteria andCr_userLessThan(String value) {
			addCriterion("a.cr_user <", value, "cr_user");
			return (Criteria) this;
		}

		public Criteria andCr_userLessThanOrEqualTo(String value) {
			addCriterion("a.cr_user <=", value, "cr_user");
			return (Criteria) this;
		}

		public Criteria andCr_userLike(String value) {
			addCriterion("a.cr_user like", value, "cr_user");
			return (Criteria) this;
		}

		public Criteria andCr_userNotLike(String value) {
			addCriterion("a.cr_user not like", value, "cr_user");
			return (Criteria) this;
		}

		public Criteria andCr_userIn(List<String> values) {
			addCriterion("a.cr_user in", values, "cr_user");
			return (Criteria) this;
		}

		public Criteria andCr_userNotIn(List<String> values) {
			addCriterion("a.cr_user not in", values, "cr_user");
			return (Criteria) this;
		}

		public Criteria andCr_userBetween(String value1, String value2) {
			addCriterion("a.cr_user between", value1, value2, "cr_user");
			return (Criteria) this;
		}

		public Criteria andCr_userNotBetween(String value1, String value2) {
			addCriterion("a.cr_user not between", value1, value2, "cr_user");
			return (Criteria) this;
		}

		public Criteria andCr_timeIsNull() {
			addCriterion("a.cr_time is null");
			return (Criteria) this;
		}

		public Criteria andCr_timeIsNotNull() {
			addCriterion("a.cr_time is not null");
			return (Criteria) this;
		}

		public Criteria andCr_timeEqualTo(Date value) {
			addCriterion("a.cr_time =", value, "cr_time");
			return (Criteria) this;
		}

		public Criteria andCr_timeNotEqualTo(Date value) {
			addCriterion("a.cr_time <>", value, "cr_time");
			return (Criteria) this;
		}

		public Criteria andCr_timeGreaterThan(Date value) {
			addCriterion("a.cr_time >", value, "cr_time");
			return (Criteria) this;
		}

		public Criteria andCr_timeGreaterThanOrEqualTo(Date value) {
			addCriterion("a.cr_time >=", value, "cr_time");
			return (Criteria) this;
		}

		public Criteria andCr_timeLessThan(Date value) {
			addCriterion("a.cr_time <", value, "cr_time");
			return (Criteria) this;
		}

		public Criteria andCr_timeLessThanOrEqualTo(Date value) {
			addCriterion("a.cr_time <=", value, "cr_time");
			return (Criteria) this;
		}

		public Criteria andCr_timeIn(List<Date> values) {
			addCriterion("a.cr_time in", values, "cr_time");
			return (Criteria) this;
		}

		public Criteria andCr_timeNotIn(List<Date> values) {
			addCriterion("a.cr_time not in", values, "cr_time");
			return (Criteria) this;
		}

		public Criteria andCr_timeBetween(Date value1, Date value2) {
			addCriterion("a.cr_time between", value1, value2, "cr_time");
			return (Criteria) this;
		}

		public Criteria andCr_timeNotBetween(Date value1, Date value2) {
			addCriterion("a.cr_time not between", value1, value2, "cr_time");
			return (Criteria) this;
		}

		public Criteria andMd_userIsNull() {
			addCriterion("a.md_user is null");
			return (Criteria) this;
		}

		public Criteria andMd_userIsNotNull() {
			addCriterion("a.md_user is not null");
			return (Criteria) this;
		}

		public Criteria andMd_userEqualTo(String value) {
			addCriterion("a.md_user =", value, "md_user");
			return (Criteria) this;
		}

		public Criteria andMd_userNotEqualTo(String value) {
			addCriterion("a.md_user <>", value, "md_user");
			return (Criteria) this;
		}

		public Criteria andMd_userGreaterThan(String value) {
			addCriterion("a.md_user >", value, "md_user");
			return (Criteria) this;
		}

		public Criteria andMd_userGreaterThanOrEqualTo(String value) {
			addCriterion("a.md_user >=", value, "md_user");
			return (Criteria) this;
		}

		public Criteria andMd_userLessThan(String value) {
			addCriterion("a.md_user <", value, "md_user");
			return (Criteria) this;
		}

		public Criteria andMd_userLessThanOrEqualTo(String value) {
			addCriterion("a.md_user <=", value, "md_user");
			return (Criteria) this;
		}

		public Criteria andMd_userLike(String value) {
			addCriterion("a.md_user like", value, "md_user");
			return (Criteria) this;
		}

		public Criteria andMd_userNotLike(String value) {
			addCriterion("a.md_user not like", value, "md_user");
			return (Criteria) this;
		}

		public Criteria andMd_userIn(List<String> values) {
			addCriterion("a.md_user in", values, "md_user");
			return (Criteria) this;
		}

		public Criteria andMd_userNotIn(List<String> values) {
			addCriterion("a.md_user not in", values, "md_user");
			return (Criteria) this;
		}

		public Criteria andMd_userBetween(String value1, String value2) {
			addCriterion("a.md_user between", value1, value2, "md_user");
			return (Criteria) this;
		}

		public Criteria andMd_userNotBetween(String value1, String value2) {
			addCriterion("a.md_user not between", value1, value2, "md_user");
			return (Criteria) this;
		}

		public Criteria andMd_timeIsNull() {
			addCriterion("a.md_time is null");
			return (Criteria) this;
		}

		public Criteria andMd_timeIsNotNull() {
			addCriterion("a.md_time is not null");
			return (Criteria) this;
		}

		public Criteria andMd_timeEqualTo(Date value) {
			addCriterion("a.md_time =", value, "md_time");
			return (Criteria) this;
		}

		public Criteria andMd_timeNotEqualTo(Date value) {
			addCriterion("a.md_time <>", value, "md_time");
			return (Criteria) this;
		}

		public Criteria andMd_timeGreaterThan(Date value) {
			addCriterion("a.md_time >", value, "md_time");
			return (Criteria) this;
		}

		public Criteria andMd_timeGreaterThanOrEqualTo(Date value) {
			addCriterion("a.md_time >=", value, "md_time");
			return (Criteria) this;
		}

		public Criteria andMd_timeLessThan(Date value) {
			addCriterion("a.md_time <", value, "md_time");
			return (Criteria) this;
		}

		public Criteria andMd_timeLessThanOrEqualTo(Date value) {
			addCriterion("a.md_time <=", value, "md_time");
			return (Criteria) this;
		}

		public Criteria andMd_timeIn(List<Date> values) {
			addCriterion("a.md_time in", values, "md_time");
			return (Criteria) this;
		}

		public Criteria andMd_timeNotIn(List<Date> values) {
			addCriterion("a.md_time not in", values, "md_time");
			return (Criteria) this;
		}

		public Criteria andMd_timeBetween(Date value1, Date value2) {
			addCriterion("a.md_time between", value1, value2, "md_time");
			return (Criteria) this;
		}

		public Criteria andMd_timeNotBetween(Date value1, Date value2) {
			addCriterion("a.md_time not between", value1, value2, "md_time");
			return (Criteria) this;
		}

		public Criteria andSrl_noIsNull() {
			addCriterion("a.srl_no is null");
			return (Criteria) this;
		}

		public Criteria andSrl_noIsNotNull() {
			addCriterion("a.srl_no is not null");
			return (Criteria) this;
		}

		public Criteria andSrl_noEqualTo(Long value) {
			addCriterion("a.srl_no =", value, "srl_no");
			return (Criteria) this;
		}

		public Criteria andSrl_noNotEqualTo(Long value) {
			addCriterion("a.srl_no <>", value, "srl_no");
			return (Criteria) this;
		}

		public Criteria andSrl_noGreaterThan(Long value) {
			addCriterion("a.srl_no >", value, "srl_no");
			return (Criteria) this;
		}

		public Criteria andSrl_noGreaterThanOrEqualTo(Long value) {
			addCriterion("a.srl_no >=", value, "srl_no");
			return (Criteria) this;
		}

		public Criteria andSrl_noLessThan(Long value) {
			addCriterion("a.srl_no <", value, "srl_no");
			return (Criteria) this;
		}

		public Criteria andSrl_noLessThanOrEqualTo(Long value) {
			addCriterion("a.srl_no <=", value, "srl_no");
			return (Criteria) this;
		}

		public Criteria andSrl_noIn(List<Long> values) {
			addCriterion("a.srl_no in", values, "srl_no");
			return (Criteria) this;
		}

		public Criteria andSrl_noNotIn(List<Long> values) {
			addCriterion("a.srl_no not in", values, "srl_no");
			return (Criteria) this;
		}

		public Criteria andSrl_noBetween(Long value1, Long value2) {
			addCriterion("a.srl_no between", value1, value2, "srl_no");
			return (Criteria) this;
		}

		public Criteria andSrl_noNotBetween(Long value1, Long value2) {
			addCriterion("a.srl_no not between", value1, value2, "srl_no");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table TB_INV_STAT_TRAN
	 * @mbggenerated  Tue Nov 25 16:47:57 CST 2014
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
     * This class corresponds to the database table TB_INV_STAT_TRAN
     *
     * @mbggenerated do_not_delete_during_merge Tue Nov 11 16:57:36 CST 2014
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}