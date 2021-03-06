package com.foya.dao.mybatis.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TbInvMaterialExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table TB_INV_MATERIAL
	 * @mbggenerated  Tue Dec 16 16:59:16 CST 2014
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table TB_INV_MATERIAL
	 * @mbggenerated  Tue Dec 16 16:59:16 CST 2014
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table TB_INV_MATERIAL
	 * @mbggenerated  Tue Dec 16 16:59:16 CST 2014
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_MATERIAL
	 * @mbggenerated  Tue Dec 16 16:59:16 CST 2014
	 */
	public TbInvMaterialExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_MATERIAL
	 * @mbggenerated  Tue Dec 16 16:59:16 CST 2014
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_MATERIAL
	 * @mbggenerated  Tue Dec 16 16:59:16 CST 2014
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_MATERIAL
	 * @mbggenerated  Tue Dec 16 16:59:16 CST 2014
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_MATERIAL
	 * @mbggenerated  Tue Dec 16 16:59:16 CST 2014
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_MATERIAL
	 * @mbggenerated  Tue Dec 16 16:59:16 CST 2014
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_MATERIAL
	 * @mbggenerated  Tue Dec 16 16:59:16 CST 2014
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_MATERIAL
	 * @mbggenerated  Tue Dec 16 16:59:16 CST 2014
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_MATERIAL
	 * @mbggenerated  Tue Dec 16 16:59:16 CST 2014
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_MATERIAL
	 * @mbggenerated  Tue Dec 16 16:59:16 CST 2014
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_MATERIAL
	 * @mbggenerated  Tue Dec 16 16:59:16 CST 2014
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table TB_INV_MATERIAL
	 * @mbggenerated  Tue Dec 16 16:59:16 CST 2014
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

		public Criteria andMat_nameIsNull() {
			addCriterion("mat_name is null");
			return (Criteria) this;
		}

		public Criteria andMat_nameIsNotNull() {
			addCriterion("mat_name is not null");
			return (Criteria) this;
		}

		public Criteria andMat_nameEqualTo(String value) {
			addCriterion("mat_name =", value, "mat_name");
			return (Criteria) this;
		}

		public Criteria andMat_nameNotEqualTo(String value) {
			addCriterion("mat_name <>", value, "mat_name");
			return (Criteria) this;
		}

		public Criteria andMat_nameGreaterThan(String value) {
			addCriterion("mat_name >", value, "mat_name");
			return (Criteria) this;
		}

		public Criteria andMat_nameGreaterThanOrEqualTo(String value) {
			addCriterion("mat_name >=", value, "mat_name");
			return (Criteria) this;
		}

		public Criteria andMat_nameLessThan(String value) {
			addCriterion("mat_name <", value, "mat_name");
			return (Criteria) this;
		}

		public Criteria andMat_nameLessThanOrEqualTo(String value) {
			addCriterion("mat_name <=", value, "mat_name");
			return (Criteria) this;
		}

		public Criteria andMat_nameLike(String value) {
			addCriterion("mat_name like", value, "mat_name");
			return (Criteria) this;
		}

		public Criteria andMat_nameNotLike(String value) {
			addCriterion("mat_name not like", value, "mat_name");
			return (Criteria) this;
		}

		public Criteria andMat_nameIn(List<String> values) {
			addCriterion("mat_name in", values, "mat_name");
			return (Criteria) this;
		}

		public Criteria andMat_nameNotIn(List<String> values) {
			addCriterion("mat_name not in", values, "mat_name");
			return (Criteria) this;
		}

		public Criteria andMat_nameBetween(String value1, String value2) {
			addCriterion("mat_name between", value1, value2, "mat_name");
			return (Criteria) this;
		}

		public Criteria andMat_nameNotBetween(String value1, String value2) {
			addCriterion("mat_name not between", value1, value2, "mat_name");
			return (Criteria) this;
		}

		public Criteria andCatg1_codeIsNull() {
			addCriterion("catg1_code is null");
			return (Criteria) this;
		}

		public Criteria andCatg1_codeIsNotNull() {
			addCriterion("catg1_code is not null");
			return (Criteria) this;
		}

		public Criteria andCatg1_codeEqualTo(String value) {
			addCriterion("catg1_code =", value, "catg1_code");
			return (Criteria) this;
		}

		public Criteria andCatg1_codeNotEqualTo(String value) {
			addCriterion("catg1_code <>", value, "catg1_code");
			return (Criteria) this;
		}

		public Criteria andCatg1_codeGreaterThan(String value) {
			addCriterion("catg1_code >", value, "catg1_code");
			return (Criteria) this;
		}

		public Criteria andCatg1_codeGreaterThanOrEqualTo(String value) {
			addCriterion("catg1_code >=", value, "catg1_code");
			return (Criteria) this;
		}

		public Criteria andCatg1_codeLessThan(String value) {
			addCriterion("catg1_code <", value, "catg1_code");
			return (Criteria) this;
		}

		public Criteria andCatg1_codeLessThanOrEqualTo(String value) {
			addCriterion("catg1_code <=", value, "catg1_code");
			return (Criteria) this;
		}

		public Criteria andCatg1_codeLike(String value) {
			addCriterion("catg1_code like", value, "catg1_code");
			return (Criteria) this;
		}

		public Criteria andCatg1_codeNotLike(String value) {
			addCriterion("catg1_code not like", value, "catg1_code");
			return (Criteria) this;
		}

		public Criteria andCatg1_codeIn(List<String> values) {
			addCriterion("catg1_code in", values, "catg1_code");
			return (Criteria) this;
		}

		public Criteria andCatg1_codeNotIn(List<String> values) {
			addCriterion("catg1_code not in", values, "catg1_code");
			return (Criteria) this;
		}

		public Criteria andCatg1_codeBetween(String value1, String value2) {
			addCriterion("catg1_code between", value1, value2, "catg1_code");
			return (Criteria) this;
		}

		public Criteria andCatg1_codeNotBetween(String value1, String value2) {
			addCriterion("catg1_code not between", value1, value2, "catg1_code");
			return (Criteria) this;
		}

		public Criteria andCatg2_codeIsNull() {
			addCriterion("catg2_code is null");
			return (Criteria) this;
		}

		public Criteria andCatg2_codeIsNotNull() {
			addCriterion("catg2_code is not null");
			return (Criteria) this;
		}

		public Criteria andCatg2_codeEqualTo(String value) {
			addCriterion("catg2_code =", value, "catg2_code");
			return (Criteria) this;
		}

		public Criteria andCatg2_codeNotEqualTo(String value) {
			addCriterion("catg2_code <>", value, "catg2_code");
			return (Criteria) this;
		}

		public Criteria andCatg2_codeGreaterThan(String value) {
			addCriterion("catg2_code >", value, "catg2_code");
			return (Criteria) this;
		}

		public Criteria andCatg2_codeGreaterThanOrEqualTo(String value) {
			addCriterion("catg2_code >=", value, "catg2_code");
			return (Criteria) this;
		}

		public Criteria andCatg2_codeLessThan(String value) {
			addCriterion("catg2_code <", value, "catg2_code");
			return (Criteria) this;
		}

		public Criteria andCatg2_codeLessThanOrEqualTo(String value) {
			addCriterion("catg2_code <=", value, "catg2_code");
			return (Criteria) this;
		}

		public Criteria andCatg2_codeLike(String value) {
			addCriterion("catg2_code like", value, "catg2_code");
			return (Criteria) this;
		}

		public Criteria andCatg2_codeNotLike(String value) {
			addCriterion("catg2_code not like", value, "catg2_code");
			return (Criteria) this;
		}

		public Criteria andCatg2_codeIn(List<String> values) {
			addCriterion("catg2_code in", values, "catg2_code");
			return (Criteria) this;
		}

		public Criteria andCatg2_codeNotIn(List<String> values) {
			addCriterion("catg2_code not in", values, "catg2_code");
			return (Criteria) this;
		}

		public Criteria andCatg2_codeBetween(String value1, String value2) {
			addCriterion("catg2_code between", value1, value2, "catg2_code");
			return (Criteria) this;
		}

		public Criteria andCatg2_codeNotBetween(String value1, String value2) {
			addCriterion("catg2_code not between", value1, value2, "catg2_code");
			return (Criteria) this;
		}

		public Criteria andCatg3_codeIsNull() {
			addCriterion("catg3_code is null");
			return (Criteria) this;
		}

		public Criteria andCatg3_codeIsNotNull() {
			addCriterion("catg3_code is not null");
			return (Criteria) this;
		}

		public Criteria andCatg3_codeEqualTo(String value) {
			addCriterion("catg3_code =", value, "catg3_code");
			return (Criteria) this;
		}

		public Criteria andCatg3_codeNotEqualTo(String value) {
			addCriterion("catg3_code <>", value, "catg3_code");
			return (Criteria) this;
		}

		public Criteria andCatg3_codeGreaterThan(String value) {
			addCriterion("catg3_code >", value, "catg3_code");
			return (Criteria) this;
		}

		public Criteria andCatg3_codeGreaterThanOrEqualTo(String value) {
			addCriterion("catg3_code >=", value, "catg3_code");
			return (Criteria) this;
		}

		public Criteria andCatg3_codeLessThan(String value) {
			addCriterion("catg3_code <", value, "catg3_code");
			return (Criteria) this;
		}

		public Criteria andCatg3_codeLessThanOrEqualTo(String value) {
			addCriterion("catg3_code <=", value, "catg3_code");
			return (Criteria) this;
		}

		public Criteria andCatg3_codeLike(String value) {
			addCriterion("catg3_code like", value, "catg3_code");
			return (Criteria) this;
		}

		public Criteria andCatg3_codeNotLike(String value) {
			addCriterion("catg3_code not like", value, "catg3_code");
			return (Criteria) this;
		}

		public Criteria andCatg3_codeIn(List<String> values) {
			addCriterion("catg3_code in", values, "catg3_code");
			return (Criteria) this;
		}

		public Criteria andCatg3_codeNotIn(List<String> values) {
			addCriterion("catg3_code not in", values, "catg3_code");
			return (Criteria) this;
		}

		public Criteria andCatg3_codeBetween(String value1, String value2) {
			addCriterion("catg3_code between", value1, value2, "catg3_code");
			return (Criteria) this;
		}

		public Criteria andCatg3_codeNotBetween(String value1, String value2) {
			addCriterion("catg3_code not between", value1, value2, "catg3_code");
			return (Criteria) this;
		}

		public Criteria andUnitIsNull() {
			addCriterion("unit is null");
			return (Criteria) this;
		}

		public Criteria andUnitIsNotNull() {
			addCriterion("unit is not null");
			return (Criteria) this;
		}

		public Criteria andUnitEqualTo(String value) {
			addCriterion("unit =", value, "unit");
			return (Criteria) this;
		}

		public Criteria andUnitNotEqualTo(String value) {
			addCriterion("unit <>", value, "unit");
			return (Criteria) this;
		}

		public Criteria andUnitGreaterThan(String value) {
			addCriterion("unit >", value, "unit");
			return (Criteria) this;
		}

		public Criteria andUnitGreaterThanOrEqualTo(String value) {
			addCriterion("unit >=", value, "unit");
			return (Criteria) this;
		}

		public Criteria andUnitLessThan(String value) {
			addCriterion("unit <", value, "unit");
			return (Criteria) this;
		}

		public Criteria andUnitLessThanOrEqualTo(String value) {
			addCriterion("unit <=", value, "unit");
			return (Criteria) this;
		}

		public Criteria andUnitLike(String value) {
			addCriterion("unit like", value, "unit");
			return (Criteria) this;
		}

		public Criteria andUnitNotLike(String value) {
			addCriterion("unit not like", value, "unit");
			return (Criteria) this;
		}

		public Criteria andUnitIn(List<String> values) {
			addCriterion("unit in", values, "unit");
			return (Criteria) this;
		}

		public Criteria andUnitNotIn(List<String> values) {
			addCriterion("unit not in", values, "unit");
			return (Criteria) this;
		}

		public Criteria andUnitBetween(String value1, String value2) {
			addCriterion("unit between", value1, value2, "unit");
			return (Criteria) this;
		}

		public Criteria andUnitNotBetween(String value1, String value2) {
			addCriterion("unit not between", value1, value2, "unit");
			return (Criteria) this;
		}

		public Criteria andCtrl_typeIsNull() {
			addCriterion("ctrl_type is null");
			return (Criteria) this;
		}

		public Criteria andCtrl_typeIsNotNull() {
			addCriterion("ctrl_type is not null");
			return (Criteria) this;
		}

		public Criteria andCtrl_typeEqualTo(String value) {
			addCriterion("ctrl_type =", value, "ctrl_type");
			return (Criteria) this;
		}

		public Criteria andCtrl_typeNotEqualTo(String value) {
			addCriterion("ctrl_type <>", value, "ctrl_type");
			return (Criteria) this;
		}

		public Criteria andCtrl_typeGreaterThan(String value) {
			addCriterion("ctrl_type >", value, "ctrl_type");
			return (Criteria) this;
		}

		public Criteria andCtrl_typeGreaterThanOrEqualTo(String value) {
			addCriterion("ctrl_type >=", value, "ctrl_type");
			return (Criteria) this;
		}

		public Criteria andCtrl_typeLessThan(String value) {
			addCriterion("ctrl_type <", value, "ctrl_type");
			return (Criteria) this;
		}

		public Criteria andCtrl_typeLessThanOrEqualTo(String value) {
			addCriterion("ctrl_type <=", value, "ctrl_type");
			return (Criteria) this;
		}

		public Criteria andCtrl_typeLike(String value) {
			addCriterion("ctrl_type like", value, "ctrl_type");
			return (Criteria) this;
		}

		public Criteria andCtrl_typeNotLike(String value) {
			addCriterion("ctrl_type not like", value, "ctrl_type");
			return (Criteria) this;
		}

		public Criteria andCtrl_typeIn(List<String> values) {
			addCriterion("ctrl_type in", values, "ctrl_type");
			return (Criteria) this;
		}

		public Criteria andCtrl_typeNotIn(List<String> values) {
			addCriterion("ctrl_type not in", values, "ctrl_type");
			return (Criteria) this;
		}

		public Criteria andCtrl_typeBetween(String value1, String value2) {
			addCriterion("ctrl_type between", value1, value2, "ctrl_type");
			return (Criteria) this;
		}

		public Criteria andCtrl_typeNotBetween(String value1, String value2) {
			addCriterion("ctrl_type not between", value1, value2, "ctrl_type");
			return (Criteria) this;
		}

		public Criteria andIs_assetIsNull() {
			addCriterion("is_asset is null");
			return (Criteria) this;
		}

		public Criteria andIs_assetIsNotNull() {
			addCriterion("is_asset is not null");
			return (Criteria) this;
		}

		public Criteria andIs_assetEqualTo(Boolean value) {
			addCriterion("is_asset =", value, "is_asset");
			return (Criteria) this;
		}

		public Criteria andIs_assetNotEqualTo(Boolean value) {
			addCriterion("is_asset <>", value, "is_asset");
			return (Criteria) this;
		}

		public Criteria andIs_assetGreaterThan(Boolean value) {
			addCriterion("is_asset >", value, "is_asset");
			return (Criteria) this;
		}

		public Criteria andIs_assetGreaterThanOrEqualTo(Boolean value) {
			addCriterion("is_asset >=", value, "is_asset");
			return (Criteria) this;
		}

		public Criteria andIs_assetLessThan(Boolean value) {
			addCriterion("is_asset <", value, "is_asset");
			return (Criteria) this;
		}

		public Criteria andIs_assetLessThanOrEqualTo(Boolean value) {
			addCriterion("is_asset <=", value, "is_asset");
			return (Criteria) this;
		}

		public Criteria andIs_assetIn(List<Boolean> values) {
			addCriterion("is_asset in", values, "is_asset");
			return (Criteria) this;
		}

		public Criteria andIs_assetNotIn(List<Boolean> values) {
			addCriterion("is_asset not in", values, "is_asset");
			return (Criteria) this;
		}

		public Criteria andIs_assetBetween(Boolean value1, Boolean value2) {
			addCriterion("is_asset between", value1, value2, "is_asset");
			return (Criteria) this;
		}

		public Criteria andIs_assetNotBetween(Boolean value1, Boolean value2) {
			addCriterion("is_asset not between", value1, value2, "is_asset");
			return (Criteria) this;
		}

		public Criteria andInventory_item_idIsNull() {
			addCriterion("inventory_item_id is null");
			return (Criteria) this;
		}

		public Criteria andInventory_item_idIsNotNull() {
			addCriterion("inventory_item_id is not null");
			return (Criteria) this;
		}

		public Criteria andInventory_item_idEqualTo(Short value) {
			addCriterion("inventory_item_id =", value, "inventory_item_id");
			return (Criteria) this;
		}

		public Criteria andInventory_item_idNotEqualTo(Short value) {
			addCriterion("inventory_item_id <>", value, "inventory_item_id");
			return (Criteria) this;
		}

		public Criteria andInventory_item_idGreaterThan(Short value) {
			addCriterion("inventory_item_id >", value, "inventory_item_id");
			return (Criteria) this;
		}

		public Criteria andInventory_item_idGreaterThanOrEqualTo(Short value) {
			addCriterion("inventory_item_id >=", value, "inventory_item_id");
			return (Criteria) this;
		}

		public Criteria andInventory_item_idLessThan(Short value) {
			addCriterion("inventory_item_id <", value, "inventory_item_id");
			return (Criteria) this;
		}

		public Criteria andInventory_item_idLessThanOrEqualTo(Short value) {
			addCriterion("inventory_item_id <=", value, "inventory_item_id");
			return (Criteria) this;
		}

		public Criteria andInventory_item_idIn(List<Short> values) {
			addCriterion("inventory_item_id in", values, "inventory_item_id");
			return (Criteria) this;
		}

		public Criteria andInventory_item_idNotIn(List<Short> values) {
			addCriterion("inventory_item_id not in", values,
					"inventory_item_id");
			return (Criteria) this;
		}

		public Criteria andInventory_item_idBetween(Short value1, Short value2) {
			addCriterion("inventory_item_id between", value1, value2,
					"inventory_item_id");
			return (Criteria) this;
		}

		public Criteria andInventory_item_idNotBetween(Short value1,
				Short value2) {
			addCriterion("inventory_item_id not between", value1, value2,
					"inventory_item_id");
			return (Criteria) this;
		}

		public Criteria andItem_typeIsNull() {
			addCriterion("item_type is null");
			return (Criteria) this;
		}

		public Criteria andItem_typeIsNotNull() {
			addCriterion("item_type is not null");
			return (Criteria) this;
		}

		public Criteria andItem_typeEqualTo(String value) {
			addCriterion("item_type =", value, "item_type");
			return (Criteria) this;
		}

		public Criteria andItem_typeNotEqualTo(String value) {
			addCriterion("item_type <>", value, "item_type");
			return (Criteria) this;
		}

		public Criteria andItem_typeGreaterThan(String value) {
			addCriterion("item_type >", value, "item_type");
			return (Criteria) this;
		}

		public Criteria andItem_typeGreaterThanOrEqualTo(String value) {
			addCriterion("item_type >=", value, "item_type");
			return (Criteria) this;
		}

		public Criteria andItem_typeLessThan(String value) {
			addCriterion("item_type <", value, "item_type");
			return (Criteria) this;
		}

		public Criteria andItem_typeLessThanOrEqualTo(String value) {
			addCriterion("item_type <=", value, "item_type");
			return (Criteria) this;
		}

		public Criteria andItem_typeLike(String value) {
			addCriterion("item_type like", value, "item_type");
			return (Criteria) this;
		}

		public Criteria andItem_typeNotLike(String value) {
			addCriterion("item_type not like", value, "item_type");
			return (Criteria) this;
		}

		public Criteria andItem_typeIn(List<String> values) {
			addCriterion("item_type in", values, "item_type");
			return (Criteria) this;
		}

		public Criteria andItem_typeNotIn(List<String> values) {
			addCriterion("item_type not in", values, "item_type");
			return (Criteria) this;
		}

		public Criteria andItem_typeBetween(String value1, String value2) {
			addCriterion("item_type between", value1, value2, "item_type");
			return (Criteria) this;
		}

		public Criteria andItem_typeNotBetween(String value1, String value2) {
			addCriterion("item_type not between", value1, value2, "item_type");
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
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table TB_INV_MATERIAL
	 * @mbggenerated  Tue Dec 16 16:59:16 CST 2014
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
     * This class corresponds to the database table TB_INV_MATERIAL
     *
     * @mbggenerated do_not_delete_during_merge Wed Nov 19 15:00:47 CST 2014
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}