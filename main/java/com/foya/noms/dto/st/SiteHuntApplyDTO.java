package com.foya.noms.dto.st;

import java.math.BigDecimal;
import java.util.Date;

public class SiteHuntApplyDTO {
	private String workId;
	private String workType;
	private String workStatus;
	private String priority;
	private String applyUser;
	private String locationId;
	private String locName;
	private String srId;
	private String feqId;
	private String purpose;
	private String city;
	private String area;
	private BigDecimal lon;
	private BigDecimal lat;
	private String coverRange;
	private String btsSiteId;
	private String siteId;
	private String siteName;
	private String eqpTypeId;
	private String eqpModelId;
	private String coverageType;
	private String btsConfig;
	private String donorSite;
	private String masterSite;
	private String feederless;
	private Date prediceEndDate;
	private Date endTime;
	private Date applyTime;
	private String workDesc;
	public String getWorkId() {
		return workId;
	}
	public void setWorkId(String workId) {
		this.workId = workId;
	}
	public String getWorkType() {
		return workType;
	}
	public void setWorkType(String workType) {
		this.workType = workType;
	}
	public String getWorkStatus() {
		return workStatus;
	}
	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getApplyUser() {
		return applyUser;
	}
	public void setApplyUser(String applyUser) {
		this.applyUser = applyUser;
	}
	public String getLocationId() {
		return locationId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	public String getLocName() {
		return locName;
	}
	public void setLocName(String locName) {
		this.locName = locName;
	}
	public String getSrId() {
		return srId;
	}
	public void setSrId(String srId) {
		this.srId = srId;
	}
	public String getFeqId() {
		return feqId;
	}
	public void setFeqId(String feqId) {
		this.feqId = feqId;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}

	public BigDecimal getLon() {
		return lon;
	}
	public void setLon(BigDecimal lon) {
		this.lon = lon;
	}
	public BigDecimal getLat() {
		return lat;
	}
	public void setLat(BigDecimal lat) {
		this.lat = lat;
	}
	public String getCoverRange() {
		return coverRange;
	}
	public void setCoverRange(String coverRange) {
		this.coverRange = coverRange;
	}
	
	public String getBtsSiteId() {
		return btsSiteId;
	}
	public void setBtsSiteId(String btsSiteId) {
		this.btsSiteId = btsSiteId;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getEqpTypeId() {
		return eqpTypeId;
	}
	public void setEqpTypeId(String eqpTypeId) {
		this.eqpTypeId = eqpTypeId;
	}
	public String getEqpModelId() {
		return eqpModelId;
	}
	public void setEqpModelId(String eqpModelId) {
		this.eqpModelId = eqpModelId;
	}
	public String getCoverageType() {
		return coverageType;
	}
	public void setCoverageType(String coverageType) {
		this.coverageType = coverageType;
	}
	public String getBtsConfig() {
		return btsConfig;
	}
	public void setBtsConfig(String btsConfig) {
		this.btsConfig = btsConfig;
	}
	public String getDonorSite() {
		return donorSite;
	}
	public void setDonorSite(String donorSite) {
		this.donorSite = donorSite;
	}
	public String getMasterSite() {
		return masterSite;
	}
	public void setMasterSite(String masterSite) {
		this.masterSite = masterSite;
	}
	public String getFeederless() {
		return feederless;
	}
	public void setFeederless(String feederless) {
		this.feederless = feederless;
	}
	
	public Date getPrediceEndDate() {
		return prediceEndDate;
	}
	public void setPrediceEndDate(Date prediceEndDate) {
		this.prediceEndDate = prediceEndDate;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Date getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	public String getWorkDesc() {
		return workDesc;
	}
	public void setWorkDesc(String workDesc) {
		this.workDesc = workDesc;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	@Override
	public String toString() {
		return "SiteHuntApplyDTO [workId=" + workId + ", workType=" + workType
				+ ", workStatus=" + workStatus + ", priority=" + priority
				+ ", applyUser=" + applyUser + ", locationId=" + locationId
				+ ", locName=" + locName + ", srId=" + srId + ", feqId="
				+ feqId + ", purpose=" + purpose + ", city=" + city + ", area="
				+ area + ", lon=" + lon + ", lat=" + lat + ", coverRange="
				+ coverRange + ", btsSiteId=" + btsSiteId + ", siteId="
				+ siteId + ", siteName=" + siteName + ", eqpTypeId="
				+ eqpTypeId + ", eqpModelId=" + eqpModelId + ", coverageType="
				+ coverageType + ", btsConfig=" + btsConfig + ", donorSite="
				+ donorSite + ", masterSite=" + masterSite + ", feederless="
				+ feederless + ", prediceEndDate=" + prediceEndDate
				+ ", endTime=" + endTime + ", applyTime=" + applyTime
				+ ", workDesc=" + workDesc + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
}
