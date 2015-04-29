package com.foya.noms.dto.inv;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.foya.dao.mybatis.model.TbInvBooking;
import com.foya.noms.util.DateTimeJsonSerializer;

public class TbInvBookingDTO extends TbInvBooking {

	private String dept_name;
	private String status;
	private String status_dscr;
	private String cr_user_name;

	@JsonSerialize(using=DateTimeJsonSerializer.class)
    public Date getCr_time() {
        return super.getCr_time();
    }

	@JsonSerialize(using=DateTimeJsonSerializer.class)
	public Date getMd_time() {
        return super.getMd_time();
    }

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name == null ? null : dept_name.trim();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	public String getStatus_dscr() {
		return status_dscr;
	}

	public void setStatus_dscr(String status_dscr) {
		this.status_dscr = status_dscr == null ? null : status_dscr.trim();
	}

	public String getCr_user_name() {
		return cr_user_name;
	}

	public void setCr_user_name(String cr_user_name) {
		this.cr_user_name = cr_user_name == null ? null : cr_user_name.trim();
	}
}
