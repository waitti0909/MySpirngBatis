package com.foya.noms.dto.org;

import com.foya.dao.mybatis.model.TbOrgPsnPos;

public class PsnPosDTO extends TbOrgPsnPos{
	
	private String primaryJobPos;
	private String psnName;
	private String psnEmail;
	
	public String getPrimaryJobPos() {
		return primaryJobPos;
	}

	public void setPrimaryJobPos(String primaryJobPos) {
		this.primaryJobPos = primaryJobPos;
	}

	public String getPsnName() {
		return psnName;
	}

	public void setPsnName(String psnName) {
		this.psnName = psnName;
	}

	public String getPsnEmail() {
		return psnEmail;
	}

	public void setPsnEmail(String psnEmail) {
		this.psnEmail = psnEmail;
	}
	
}
