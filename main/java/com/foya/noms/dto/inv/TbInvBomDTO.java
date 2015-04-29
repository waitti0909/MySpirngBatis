package com.foya.noms.dto.inv;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.foya.dao.mybatis.model.TbInvBom;
import com.foya.noms.util.DateTimeJsonSerializer;

public class TbInvBomDTO extends TbInvBom {

	private String eqp_type_name;
	private String eqp_model_name;
	private String mat_name;
	private String catg1_code;
	private String catg2_code;
	private String catg3_code;
	private String catg1_name;
	private String catg2_name;
	private String catg3_name;
	private String check_status;
	private String check_status_name;

	@JsonSerialize(using = DateTimeJsonSerializer.class)
    public Date getEffective_date() {
        return super.getEffective_date();
    }

	@JsonSerialize(using = DateTimeJsonSerializer.class)
    public Date getFailure_date() {
        return super.getFailure_date();
    }

	@JsonSerialize(using = DateTimeJsonSerializer.class)
    public Date getCr_time() {
        return super.getCr_time();
    }

	@JsonSerialize(using = DateTimeJsonSerializer.class)
    public Date getMd_time() {
        return super.getMd_time();
    }

	public String getEqp_type_name() {
		return eqp_type_name;
	}

	public void setEqp_type_name(String eqp_type_name) {
		this.eqp_type_name = eqp_type_name == null ? null : eqp_type_name
				.trim();
	}

	public String getEqp_model_name() {
		return eqp_model_name;
	}

	public void setEqp_model_name(String eqp_model_name) {
		this.eqp_model_name = eqp_model_name == null ? null : eqp_model_name
				.trim();
	}

	public String getMat_name() {
		return mat_name;
	}

	public void setMat_name(String mat_name) {
		this.mat_name = mat_name == null ? null : mat_name.trim();
	}

	public String getCatg1_code() {
		return catg1_code;
	}

	public void setCatg1_code(String catg1_code) {
		this.catg1_code = catg1_code == null ? null : catg1_code.trim();
	}

	public String getCatg2_code() {
		return catg2_code;
	}

	public void setCatg2_code(String catg2_code) {
		this.catg2_code = catg2_code == null ? null : catg2_code.trim();
	}

	public String getCatg3_code() {
		return catg3_code;
	}

	public void setCatg3_code(String catg3_code) {
		this.catg3_code = catg3_code == null ? null : catg3_code.trim();
	}

	public String getCatg1_name() {
		return catg1_name;
	}

	public void setCatg1_name(String catg1_name) {
		this.catg1_name = catg1_name == null ? null : catg1_name.trim();
	}

	public String getCatg2_name() {
		return catg2_name;
	}

	public void setCatg2_name(String catg2_name) {
		this.catg2_name = catg2_name == null ? null : catg2_name.trim();
	}

	public String getCatg3_name() {
		return catg3_name;
	}

	public void setCatg3_name(String catg3_name) {
		this.catg3_name = catg3_name == null ? null : catg3_name.trim();
	}

	public String getCheck_status() {
		return check_status;
	}

	public void setCheck_status(String check_status) {
		this.check_status = check_status == null ? null : check_status.trim();
	}

	public String getCheck_status_name() {
		return check_status_name;
	}

	public void setCheck_status_name(String check_status_name) {
		this.check_status_name = check_status_name == null ? null
				: check_status_name.trim();
	}
}
