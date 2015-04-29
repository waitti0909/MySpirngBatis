package com.foya.noms.dto.auth;

import com.foya.dao.mybatis.model.TbAuthPersonnel;

public class PersonnelDTO extends TbAuthPersonnel{
	private String deptName;
	private String callBackFun;
	private String dismission;
	
	public String getDismission() {
		return dismission;
	}

	public void setDismission(String dismission) {
		this.dismission = dismission;
	}

	public String getCallBackFun() {
		return callBackFun;
	}

	public void setCallBackFun(String callBackFun) {
		this.callBackFun = callBackFun;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
}
