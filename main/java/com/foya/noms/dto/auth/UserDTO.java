package com.foya.noms.dto.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.foya.dao.mybatis.model.TbOrgDomain;

/**
 * 用戶
 */
public class UserDTO implements UserDetails {

	private static final long serialVersionUID = 8529792514937624455L;

	private Map<Integer,Set<String>> allowDeptList = new HashMap<Integer, Set<String>>();
	
	private Collection<GrantedAuthority> authorities = new ArrayList<>();

	private Integer userId;

	private String username;

	private String password;

	private String deptId;
	
	private String hrDeptId;
	
	private String deptName;

	private String hrDeptName;
	
	private String primaryDeptPosId;
	
	private String psnType; 
	
	private String coVatNo;
	
	private boolean isManager;
	
	private String chName;

	private String enName;

	private TbOrgDomain domain;

	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		return getUsername().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		UserDTO user = (UserDTO) obj;
		return this.getUsername().equals(user.getUsername());
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		// return default
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// return default
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// return default
		return true;
	}

	@Override
	public boolean isEnabled() {
		// return default
		return true;
	}

	public void setAuthorities(Collection<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getPrimaryDeptPosId() {
		return primaryDeptPosId;
	}

	public void setPrimaryDeptPosId(String primaryDeptPosId) {
		this.primaryDeptPosId = primaryDeptPosId;
	}

	@Override
	public String toString() {
		return "UserDTO [userId=" + userId + ", username=" + username + ", password=" + password + ", authorities=" + authorities
				+ ", deptId=" + deptId + ", deptName=" + deptName + ", primaryDeptPosId=" + primaryDeptPosId + "]";
	}

	
	
	public String getHrDeptId() {
		return hrDeptId;
	}

	public void setHrDeptId(String hrDeptId) {
		this.hrDeptId = hrDeptId;
	}

	public Map<Integer, Set<String>> getAllowDeptList() {
		return allowDeptList;
	}

	public void setAllowDeptList(Map<Integer, Set<String>> allowDeptList) {
		this.allowDeptList = allowDeptList;
	}

	public String getHrDeptName() {
		return hrDeptName;
	}

	public void setHrDeptName(String hrDeptName) {
		this.hrDeptName = hrDeptName;
	}
	
	
	
	public Set<String> getAccessDeptListByMenuID(Integer menuId){
		// modify by Charlie 20150130 最少都要只能查自己部門
		Set<String> allowDepts = allowDeptList.get(menuId);
		if (allowDepts == null || allowDepts.isEmpty()) {
			allowDepts = new HashSet<>();
			allowDepts.add(getDeptId());
		}
//		return allowDeptList.get(menuId);	marked by Charlie 20150130
		return allowDepts;
	}

	public String getPsnType() {
		return psnType;
	}

	public void setPsnType(String psnType) {
		this.psnType = psnType;
	}

	
	/*
	 * 判斷是否分份為廠商
	 * true:廠商 false:員工
	 * */
	public boolean isVendor(){
		
		return "V".equalsIgnoreCase(getPsnType());
	}

	/*
	 * 廠商統編
	 * */
	public String getCoVatNo() {
		return coVatNo;
	}

	public void setCoVatNo(String coVatNo) {
		this.coVatNo = coVatNo;
	}

	public boolean isManager() {
		return isManager;
	}

	public void setManager(boolean isManager) {
		this.isManager = isManager;
	}

	public String getChName() {
		return chName;
	}

	public void setChName(String chName) {
		this.chName = chName;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public TbOrgDomain getDomain() {
		return domain;
	}

	public void setDomain(TbOrgDomain domain) {
		this.domain = domain;
	}



}
