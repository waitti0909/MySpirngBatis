package com.foya.noms.dto.auth;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.foya.noms.util.DateTimeJsonSerializer;

public class RoleMastDTO {
	
	private Integer roleId;
	private String roleName;
	private String roleDesc;
	private String mdyuser;
	private String mdUser;
	private Date mdTime;
	private String psnMember;
	
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleDesc() {
		return roleDesc;
	}
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
	public String getMdyuser() {
		return mdyuser;
	}
	public void setMdyuser(String mdyuser) {
		this.mdyuser = mdyuser;
	}
	public String getMdUser() {
		return mdUser;
	}
	public void setMdUser(String mdUser) {
		this.mdUser = mdUser;
	}
	@JsonSerialize(using=DateTimeJsonSerializer.class)
	public Date getMdTime() {
		return mdTime;
	}
	public void setMdTime(Date mdTime) {
		this.mdTime = mdTime;
	}
	public String getPsnMember() {
		return psnMember;
	}
	public void setPsnMember(String psnMember) {	
		this.psnMember = psnMember.substring(0,psnMember.length()-1);
	}

}
