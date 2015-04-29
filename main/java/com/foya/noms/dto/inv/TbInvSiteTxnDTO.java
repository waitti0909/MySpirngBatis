package com.foya.noms.dto.inv;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.foya.dao.mybatis.model.TbInvSiteTxn;
import com.foya.noms.util.DateTimeJsonSerializer;

public class TbInvSiteTxnDTO extends TbInvSiteTxn {

	private String txn_type_name;
	private String dept_id;
	private String dept_name;
	private String site_name;
	private String bts_site_id;
	private String mat_name;
	private String tag_no;
	private String ven_sn;
	private String cr_user_name;

	@JsonSerialize(using = DateTimeJsonSerializer.class)
	public Date getTo_erp_date() {
		return super.getTo_erp_date();
	}

	@JsonSerialize(using = DateTimeJsonSerializer.class)
	public Date getCr_time() {
		return super.getCr_time();
	}

	@JsonSerialize(using = DateTimeJsonSerializer.class)
	public Date getMd_time() {
		return super.getMd_time();
	}

	public String getTxn_type_name() {
		return txn_type_name;
	}

	public void setTxn_type_name(String txn_type_name) {
		this.txn_type_name = txn_type_name == null ? null : txn_type_name.trim();
	}

	public String getDept_id() {
		return dept_id;
	}

	public void setDept_id(String dept_id) {
		this.dept_id = dept_id == null ? null : dept_id.trim();
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name == null ? null : dept_name.trim();
	}

	public String getSite_name() {
		return site_name;
	}

	public void setSite_name(String site_name) {
		this.site_name = site_name == null ? null : site_name.trim();
	}

	public String getBts_site_id() {
		return bts_site_id;
	}

	public void setBts_site_id(String bts_site_id) {
		this.bts_site_id = bts_site_id == null ? null : bts_site_id.trim();
	}

	public String getMat_name() {
		return mat_name;
	}

	public void setMat_name(String mat_name) {
		this.mat_name = mat_name == null ? null : mat_name.trim();
	}

	public String getTag_no() {
		return tag_no;
	}

	public void setTag_no(String tag_no) {
		this.tag_no = tag_no == null ? null : tag_no.trim();
	}

	public String getVen_sn() {
		return ven_sn;
	}

	public void setVen_sn(String ven_sn) {
		this.ven_sn = ven_sn == null ? null : ven_sn.trim();
	}

	public String getCr_user_name() {
		return cr_user_name;
	}

	public void setCr_user_name(String cr_user_name) {
		this.cr_user_name = cr_user_name == null ? null : cr_user_name.trim();
	}
}
