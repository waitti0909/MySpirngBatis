package com.foya.noms.dto.inv;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.foya.dao.mybatis.model.TbInvStatTran;
import com.foya.noms.util.DateTimeJsonSerializer;

public class TbInvStatTranDTO extends TbInvStatTran {

	private String tag_no;
    private String ven_sn;
    private String fa_no;
    private String mat_name;
    private String wh_name;
    private String tran_user_name;
    private String tran_reason_dscr;
    private String inv_tran_audit_status_dscr;
    private String new_mat_status_dscr;
    private String old_mat_status_dscr;
    private String btn_type;

	@JsonSerialize(using = DateTimeJsonSerializer.class)
	public Date getTran_date() {
		return super.getTran_date();
	}

	@JsonSerialize(using = DateTimeJsonSerializer.class)
	public Date getCr_time() {
		return super.getCr_time();
	}

	@JsonSerialize(using = DateTimeJsonSerializer.class)
	public Date getMd_time() {
		return super.getMd_time();
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

	public String getFa_no() {
		return fa_no;
	}

	public void setFa_no(String fa_no) {
		this.fa_no = fa_no == null ? null : fa_no.trim();
	}

	public String getMat_name() {
		return mat_name;
	}

	public void setMat_name(String mat_name) {
		this.mat_name = mat_name == null ? null : mat_name.trim();
	}

	public String getWh_name() {
		return wh_name;
	}

	public void setWh_name(String wh_name) {
		this.wh_name = wh_name == null ? null : wh_name.trim();
	}

	public String getTran_user_name() {
		return tran_user_name;
	}

	public void setTran_user_name(String tran_user_name) {
		this.tran_user_name = tran_user_name == null ? null : tran_user_name.trim();
	}

	public String getTran_reason_dscr() {
		return tran_reason_dscr;
	}

	public void setTran_reason_dscr(String tran_reason_dscr) {
		this.tran_reason_dscr = tran_reason_dscr == null ? null : tran_reason_dscr.trim();
	}

	public String getInv_tran_audit_status_dscr() {
		return inv_tran_audit_status_dscr;
	}

	public void setInv_tran_audit_status_dscr(String inv_tran_audit_status_dscr) {
		this.inv_tran_audit_status_dscr = inv_tran_audit_status_dscr == null ? null : inv_tran_audit_status_dscr.trim();
	}

	public String getNew_mat_status_dscr() {
		return new_mat_status_dscr;
	}

	public void setNew_mat_status_dscr(String new_mat_status_dscr) {
		this.new_mat_status_dscr = new_mat_status_dscr == null ? null : new_mat_status_dscr.trim();
	}

	public String getOld_mat_status_dscr() {
		return old_mat_status_dscr;
	}

	public void setOld_mat_status_dscr(String old_mat_status_dscr) {
		this.old_mat_status_dscr = old_mat_status_dscr == null ? null : old_mat_status_dscr.trim();
	}

	public String getBtn_type() {
		return btn_type;
	}

	public void setBtn_type(String btn_type) {
		this.btn_type = btn_type == null ? null : btn_type.trim();
	}
}
