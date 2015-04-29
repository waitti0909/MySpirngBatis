package com.foya.noms.dto.auth;

import java.util.Date;

import org.springframework.security.core.GrantedAuthority;

@SuppressWarnings("serial")
public class RoleDTO implements GrantedAuthority {
	
	private Integer id;
	/**角色名稱*/
	private String name;

	private String desc;
	
	private Integer mdyuser;
	
	private Date mdytstmp;

	public Date getMdytstmp() {
		return mdytstmp;
	}
	 
	public void setMdytstmp(Date mdytstmp) {
	        this.mdytstmp = mdytstmp;
	}
	
	
	public Integer getMdyuser() {
		return mdyuser;
	}

	public void setMdyuser(Integer mdyuser) {
		this.mdyuser = mdyuser;
	}
	
	
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getAuthority() {
		return null;
	}
}
