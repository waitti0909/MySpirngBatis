package com.foya.noms.dto.ls;

import java.util.Date;

import com.foya.dao.mybatis.model.TbLsAlocDtl;

public class SiteAlocDetailDTO extends TbLsAlocDtl {
	
	private String locationId;
	private String locationName;
	private int locationType;
	private String siteId;
	private String btsSiteId;
	private String siteName;
	private String alocItem;
	
	private String LOC_TYPE;
	private String CARRIERS_ID;
	
	private Date PAY_BEGIN_DATE;
	private Date PAY_END_DATE;
	
	
	public String getLocationId() {
		return locationId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public int getLocationType() {
		return locationType;
	}
	public void setLocationType(int locationType) {
		this.locationType = locationType;
	}
	
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getAlocItem() {
		return alocItem;
	}
	public void setAlocItem(String alocItem) {
		this.alocItem = alocItem;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getBtsSiteId() {
		return btsSiteId;
	}
	public void setBtsSiteId(String btsSiteId) {
		this.btsSiteId = btsSiteId;
	}
	public String getLOC_TYPE() {
		return LOC_TYPE;
	}
	public void setLOC_TYPE(String lOC_TYPE) {
		LOC_TYPE = lOC_TYPE;
	}
	public String getCARRIERS_ID() {
		return CARRIERS_ID;
	}
	public void setCARRIERS_ID(String cARRIERS_ID) {
		CARRIERS_ID = cARRIERS_ID;
	}
	public Date getPAY_BEGIN_DATE() {
		return PAY_BEGIN_DATE;
	}
	public void setPAY_BEGIN_DATE(Date pAY_BEGIN_DATE) {
		PAY_BEGIN_DATE = pAY_BEGIN_DATE;
	}
	public Date getPAY_END_DATE() {
		return PAY_END_DATE;
	}
	public void setPAY_END_DATE(Date pAY_END_DATE) {
		PAY_END_DATE = pAY_END_DATE;
	}
	
	
	
	
}
