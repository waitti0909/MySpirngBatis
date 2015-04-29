package com.foya.noms.dto.st;

import com.foya.dao.mybatis.model.TbSiteIdPoolKey;

public class SiteIdPoolDTO extends TbSiteIdPoolKey{
	private String siteId;
	private String callBackFun;
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getCallBackFun() {
		return callBackFun;
	}
	public void setCallBackFun(String callBackFun) {
		this.callBackFun = callBackFun;
	}
	
}
