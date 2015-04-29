package com.foya.noms.dto.st;

import com.foya.dao.mybatis.model.TbSiteLineSiteData;

public class SiteLineSiteDataDTO extends TbSiteLineSiteData{
	private String btsSiteId;

	public String getBtsSiteId() {
		return btsSiteId;
	}

	public void setBtsSiteId(String btsSiteId) {
		this.btsSiteId = btsSiteId;
	}
	
}
