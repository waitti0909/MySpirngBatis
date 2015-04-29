package com.foya.noms.dto.inv;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.foya.dao.mybatis.model.TbInvSrl;
import com.foya.noms.util.DateTimeJsonSerializer;

public class TbInvSrlDTO extends TbInvSrl {

	private String wh_name;
	private String mat_name;
	private String site_name;
	private String ctrl_type;
	private Boolean is_asset;
	private String mat_status_dscr;
	private String s_site_id;
	private String s_site_name;

	@JsonSerialize(using=DateTimeJsonSerializer.class)
	public Date getRcv_date() {
		return super.getRcv_date();
	}

	@JsonSerialize(using=DateTimeJsonSerializer.class)
	public Date getDiscard_date() {
		return super.getDiscard_date();
	}

	@JsonSerialize(using=DateTimeJsonSerializer.class)
	public Date getCr_time() {
		return super.getCr_time();
	}

	@JsonSerialize(using=DateTimeJsonSerializer.class)
	public Date getMd_time() {
		return super.getMd_time();
	}

	public String getWh_name() {
		return wh_name;
	}

	public void setWh_name(String wh_name) {
		this.wh_name = wh_name == null ? null : wh_name.trim();
	}

	public String getMat_name() {
		return mat_name;
	}

	public void setMat_name(String mat_name) {
		this.mat_name = mat_name == null ? null : mat_name.trim();
	}

	public String getSite_name() {
		return site_name;
	}

	public void setSite_name(String site_name) {
		this.site_name = site_name == null ? null : site_name.trim();
	}

	public String getCtrl_type() {
		return ctrl_type;
	}

	public void setCtrl_type(String ctrl_type) {
		this.ctrl_type = ctrl_type == null ? null : ctrl_type.trim();
	}

	public Boolean getIs_asset() {
		return is_asset;
	}

	public void setIs_asset(Boolean is_asset) {
		this.is_asset = is_asset;
	}

	public String getMat_status_dscr() {
		return mat_status_dscr;
	}

	public void setMat_status_dscr(String mat_status_dscr) {
		this.mat_status_dscr = mat_status_dscr == null ? null : mat_status_dscr.trim();
	}

	public String getS_site_id() {
		return s_site_id;
	}

	public void setS_site_id(String s_site_id) {
		this.s_site_id = s_site_id == null ? null : s_site_id.trim();
	}

	public String getS_site_name() {
		return s_site_name;
	}

	public void setS_site_name(String s_site_name) {
		this.s_site_name = s_site_name == null ? null : s_site_name.trim();
	}
}
