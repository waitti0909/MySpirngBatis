package com.foya.noms.dto.ls;

import com.foya.dao.mybatis.model.TbLsResExch;

public class TbLsResExchDTO extends TbLsResExch {

	private String exchSiteAloc;
	private String LocName;
	private String Addr;
	private String appSeq;
	
	public String getExchSiteAloc() {
		return exchSiteAloc;
	}

	public void setExchSiteAloc(String exchSiteAloc) {
		this.exchSiteAloc = exchSiteAloc;
	}

	public String getLocName() {
		return LocName;
	}

	public void setLocName(String locName) {
		LocName = locName;
	}

	public String getAddr() {
		return Addr;
	}

	public void setAddr(String addr) {
		Addr = addr;
	}

	public String getAppSeq() {
		return appSeq;
	}

	public void setAppSeq(String appSeq) {
		this.appSeq = appSeq;
	}
	
	
}
