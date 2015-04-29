package com.foya.noms.dto.pay;

import com.foya.dao.mybatis.model.TbPayProvisionalAttachmentUser;

public class TbPayProvisionalAttachmentUserDTO extends
		TbPayProvisionalAttachmentUser {
	
	private String payMethodName;
	private String bankCodeDesc;
	private String bankBranchDesc;
	
	public String getPayMethodName() {
		return payMethodName;
	}
	public void setPayMethodName(String payMethodName) {
		this.payMethodName = payMethodName;
	}
	public String getBankCodeDesc() {
		return bankCodeDesc;
	}
	public void setBankCodeDesc(String bankCodeDesc) {
		this.bankCodeDesc = bankCodeDesc;
	}
	public String getBankBranchDesc() {
		return bankBranchDesc;
	}
	public void setBankBranchDesc(String bankBranchDesc) {
		this.bankBranchDesc = bankBranchDesc;
	}
	

}
