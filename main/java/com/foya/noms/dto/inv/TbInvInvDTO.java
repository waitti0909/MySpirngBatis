package com.foya.noms.dto.inv;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.foya.dao.mybatis.model.TbInvInv;
import com.foya.noms.util.DateTimeJsonSerializer;

public class TbInvInvDTO extends TbInvInv implements Cloneable {

	private String wh_name;
	private String mat_name;
	private String ctrlType;
	private String mat_status;
	private String mat_status_dscr;
	private String dept_id;
	private String dept_name;
	private String domain;
	private String domain_name;
	private String ctrl_type;
	private String catg1_code;
	private String catg1_name;
	private String catg2_code;
	private String catg2_name;
	private String catg3_code;
	private String catg3_name;
	private Integer qty;
	private String tag_no;
	private String ven_sn;
	private String matName;
	private String matStatus;

	@Override
	public TbInvInvDTO clone() throws CloneNotSupportedException {
		TbInvInvDTO clone = (TbInvInvDTO) super.clone();
		return clone;
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

	public String getCtrlType() {
		return ctrlType;
	}

	public void setCtrlType(String ctrlType) {
		this.ctrlType = ctrlType == null ? null : ctrlType.trim();
	}

	public String getMat_status() {
		return mat_status;
	}

	public void setMat_status(String mat_status) {
		this.mat_status = mat_status == null ? null : mat_status.trim();
	}

	public String getMat_status_dscr() {
		return mat_status_dscr;
	}

	public void setMat_status_dscr(String mat_status_dscr) {
		this.mat_status_dscr = mat_status_dscr == null ? null : mat_status_dscr.trim();
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

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain == null ? null : domain.trim();
	}

	public String getDomain_name() {
		return domain_name;
	}

	public void setDomain_name(String domain_name) {
		this.domain_name = domain_name == null ? null : domain_name.trim();
	}

	public String getCtrl_type() {
		return ctrl_type;
	}

	public void setCtrl_type(String ctrl_type) {
		this.ctrl_type = ctrl_type == null ? null : ctrl_type.trim();
	}

	public String getCatg1_code() {
		return catg1_code;
	}

	public void setCatg1_code(String catg1_code) {
		this.catg1_code = catg1_code == null ? null : catg1_code.trim();
	}

	public String getCatg1_name() {
		return catg1_name;
	}

	public void setCatg1_name(String catg1_name) {
		this.catg1_name = catg1_name == null ? null : catg1_name.trim();
	}

	public String getCatg2_code() {
		return catg2_code;
	}

	public void setCatg2_code(String catg2_code) {
		this.catg2_code = catg2_code == null ? null : catg2_code.trim();
	}

	public String getCatg2_name() {
		return catg2_name;
	}

	public void setCatg2_name(String catg2_name) {
		this.catg2_name = catg2_name == null ? null : catg2_name.trim();
	}

	public String getCatg3_code() {
		return catg3_code;
	}

	public void setCatg3_code(String catg3_code) {
		this.catg3_code = catg3_code == null ? null : catg3_code.trim();
	}

	public String getCatg3_name() {
		return catg3_name;
	}

	public void setCatg3_name(String catg3_name) {
		this.catg3_name = catg3_name == null ? null : catg3_name.trim();
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
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

	public String getMatName() {
		return matName;
	}

	public void setMatName(String matName) {
		this.matName = matName;
	}

	public String getMatStatus() {
		return matStatus;
	}

	public void setMatStatus(String matStatus) {
		this.matStatus = matStatus;
	}
	
	
}
