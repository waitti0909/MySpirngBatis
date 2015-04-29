package com.foya.noms.dto.auth;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.foya.noms.util.DateTimeJsonSerializer;

public class RoleMenuFunDepDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5111531664830364020L;
	
	
	private String roleName;
	private String roleDesc;
	private String funName;
	private String funCode;
	private String depId;
	private String depName;
	private Integer roleId;
	private Integer menuId;
	private String menuName;
	private String menuDesc;
	private String modifyUserName;
	private String md_user;
	private Integer menuFunId;
	
	public String getMd_user() {
		return md_user;
	}
	public void setMd_user(String md_user) {
		this.md_user = md_user;
	}
	private Date md_time;
	
	

	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public Integer getMenuId() {
		return menuId;
	}
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getMenuDesc() {
		return menuDesc;
	}
	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}
	public String getModifyUserName() {
		return modifyUserName;
	}
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}
	@JsonSerialize(using=DateTimeJsonSerializer.class)
	public Date getMd_time() {
		return md_time;
	}
	public void setMd_time(Date md_time) {
		this.md_time = md_time;
	}

	public String getRoleDesc() {
		return roleDesc;
	}
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
	public String getFunName() {
		return funName;
	}
	public void setFunName(String funName) {
		this.funName = funName;
	}
	public String getFunCode() {
		return funCode;
	}
	public void setFunCode(String funCode) {
		this.funCode = funCode;
	}
	public String getDepId() {
		return depId;
	}
	public void setDepId(String depId) {
		this.depId = depId;
	}
	public String getDepName() {
		return depName;
	}
	public void setDepName(String depName) {
		this.depName = depName;
	}
	public Integer getMenuFunId() {
		return menuFunId;
	}
	public void setMenuFunId(Integer menuFunId) {
		this.menuFunId = menuFunId;
	}
}
