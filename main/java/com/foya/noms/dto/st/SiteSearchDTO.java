package com.foya.noms.dto.st;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.foya.dao.mybatis.model.TbSiteSearch;
import com.foya.dao.mybatis.model.TbSiteSrSiteTemp;
import com.foya.noms.util.DateJsonSerializer;

public class SiteSearchDTO extends TbSiteSearch{
	
	
	//探勘紀錄站台資料暫存檔
	private TbSiteSrSiteTemp tbSiteSrSiteTemp;
	
	private String chnName;
	
	private String evalName;

	private String shareCom;
	private String btsSiteId;
	
	public String getBtsSiteId() {
		return btsSiteId;
	}

	public void setBtsSiteId(String btsSiteId) {
		this.btsSiteId = btsSiteId;
	}

	public String getShareCom() {
		return shareCom;
	}

	public void setShareCom(String shareCom) {
		this.shareCom = shareCom;
	}

	public String getChnName() {
		return chnName;
	}

	public void setChnName(String chnName) {
		this.chnName = chnName;
	}

	public String getEvalName() {
		return evalName;
	}

	public void setEvalName(String evalName) {
		this.evalName = evalName;
	}

	public TbSiteSrSiteTemp getTbSiteSrSiteTemp() {
		return tbSiteSrSiteTemp;
	}

	public void setTbSiteSrSiteTemp(TbSiteSrSiteTemp tbSiteSrSiteTemp) {
		this.tbSiteSrSiteTemp = tbSiteSrSiteTemp;
	}
	
	@JsonSerialize(using=DateJsonSerializer.class)
	public Date getSR_DATE(){
		return super.getSR_DATE();
	}
  
}
