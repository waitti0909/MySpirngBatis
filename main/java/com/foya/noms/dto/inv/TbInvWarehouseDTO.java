package com.foya.noms.dto.inv;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.foya.dao.mybatis.model.TbInvWarehouse;
import com.foya.noms.util.DateTimeJsonSerializer;

public class TbInvWarehouseDTO extends TbInvWarehouse {

	private String btn_type;
	private String is_effective_str;
	private String str_failure_date;
	private String is_effective_name;
	private String wh_type_name;
	private String wh_attribute_name;
	private String domain_name;
	private String dept_name;
	private String assigned_dscr;
	private String co_name;

	@JsonSerialize(using=DateTimeJsonSerializer.class)
	public Date getFailure_date() {
		return super.getFailure_date();
	}

	@JsonSerialize(using=DateTimeJsonSerializer.class)
	public Date getCr_time() {
		return super.getCr_time();
	}

	@JsonSerialize(using=DateTimeJsonSerializer.class)
	public Date getMd_time() {
		return super.getMd_time();
	}

	public String getStr_failure_date() {
		return str_failure_date;
	}

	public void setStr_failure_date(String str_failure_date) {
		this.str_failure_date = str_failure_date == null ? null : str_failure_date.trim();
	}

	public String getBtn_type() {
		return btn_type;
	}

	public void setBtn_type(String btn_type) {
		this.btn_type = btn_type == null ? null : btn_type.trim();
	}

	public String getIs_effective_str() {
		return is_effective_str;
	}

	public void setIs_effective_str(String is_effective_str) {
		this.is_effective_str = is_effective_str == null ? null : is_effective_str.trim();
	}

	public String getIs_effective_name() {
		return is_effective_name;
	}

	public void setIs_effective_name(String is_effective_name) {
		this.is_effective_name = is_effective_name == null ? null : is_effective_name.trim();
	}

	public String getWh_type_name() {
		return wh_type_name;
	}

	public void setWh_type_name(String wh_type_name) {
		this.wh_type_name = wh_type_name == null ? null : wh_type_name.trim();
	}

	public String getWh_attribute_name() {
		return wh_attribute_name;
	}

	public void setWh_attribute_name(String wh_attribute_name) {
		this.wh_attribute_name = wh_attribute_name == null ? null : wh_attribute_name.trim();
	}

	public String getDomain_name() {
		return domain_name;
	}

	public void setDomain_name(String domain_name) {
		this.domain_name = domain_name == null ? null : domain_name.trim();
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name == null ? null : dept_name.trim();
	}

	public String getAssigned_dscr() {
		return assigned_dscr;
	}

	public void setAssigned_dscr(String assigned_dscr) {
		this.assigned_dscr = assigned_dscr == null ? null : assigned_dscr.trim();
	}

	public String getCo_name() {
		return co_name;
	}

	public void setCo_name(String co_name) {
		this.co_name = co_name == null ? null : co_name.trim();
	}
}
