package com.foya.noms.dto.st;

import com.foya.dao.mybatis.model.TbSiteSearchRing;

public class SearchRingDTO extends TbSiteSearchRing{
	private String callBackFun;
	private String btsSiteId;
	private String feqName;
	public String getCallBackFun() {
		return callBackFun;
	}

	public void setCallBackFun(String callBackFun) {
		this.callBackFun = callBackFun;
	}

	public String getBtsSiteId() {
		return btsSiteId;
	}

	public void setBtsSiteId(String btsSiteId) {
		this.btsSiteId = btsSiteId;
	}

	public String getFeqName() {
		return feqName;
	}

	public void setFeqName(String feqName) {
		this.feqName = feqName;
	}
	
}
