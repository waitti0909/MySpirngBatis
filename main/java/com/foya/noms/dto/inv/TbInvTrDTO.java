package com.foya.noms.dto.inv;


import com.foya.dao.mybatis.model.TbInvTr;

public class TbInvTrDTO extends TbInvTr{
	
	private String statusDscr;
	
	private String trOutWhIdDscr;
	
	private String trInWhIdDscr;
	
	private String needDeptIdDscr;
	
	private String appDeptIdDscr;
	
	private String appUserDscr;
	
	public String getStatusDscr() {
		return statusDscr;
	}
	
	public void setStatusDscr(String statusDscr) {
		this.statusDscr = statusDscr;
	}
	
	public String getTrOutWhIdDscr() {
		return trOutWhIdDscr;
	}
	
	public void setTrOutWhIdDscr(String trOutWhIdDscr) {
		this.trOutWhIdDscr = trOutWhIdDscr;
	}
	
	public String getTrInWhIdDscr() {
		return trInWhIdDscr;
	}
	
	public void setTrInWhIdDscr(String trInWhIdDscr) {
		this.trInWhIdDscr = trInWhIdDscr;
	}
	
	public String getNeedDeptIdDscr() {
		return needDeptIdDscr;
	}
	
	public void setNeedDeptIdDscr(String needDeptIdDscr) {
		this.needDeptIdDscr = needDeptIdDscr;
	}
	
	public String getAppDeptIdDscr() {
		return appDeptIdDscr;
	}
	
	public void setAppDeptIdDscr(String appDeptIdDscr) {
		this.appDeptIdDscr = appDeptIdDscr;
	}
	
	public String getAppUserDscr() {
		return appUserDscr;
	}
	
	public void setAppUserDscr(String appUserDscr) {
		this.appUserDscr = appUserDscr;
	}
	
}
