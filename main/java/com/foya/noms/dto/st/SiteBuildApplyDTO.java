package com.foya.noms.dto.st;

import java.math.BigDecimal;
import java.util.Date;

public class SiteBuildApplyDTO {
	//作業編號
	private String workId;
	//處理狀態
	private String workStatus;
	//工務類別
	private String workType;
	//重要等級
	private String priority;
	//申請人員
	private String applyUser;
	//站點編號
	private String locationId;
	//站點名稱
	private String locName;
	//建站目的
	private String purpose;
	//工程類別
	private String osType;
	//委外廠商
	private String osVen;
	//地址相館欄位
	private String ADDR;
	private String CITY;
	private String AREA;
	private String ZIP;
	private String VILLAGE;
	private String ADJACENT;
	private String ROAD;
	private String LANE;
	private String ALLEY;
	private String SUB_ALLEY;
	private String DOOR_NO;
	private String DOOR_NO_EX;
	private String FLOOR;
	private String FLOOR_EX;
	private String ROOM;
	private String ADD_OTHER;
	//經度
	private BigDecimal lon;
	//緯度
	private BigDecimal lat;
	//基站編號
	private String btsSiteId;
	//基站頻段
	private String feqId;
	//基站名稱
	private String siteName;
	//設備型態
	private String eqpTypeId;
	//設備機型
	private String eqpModelId;
	//涵蓋類別
	private String coverageType;
	//Configuration
	private String btsConfig;
	//Donor BTS
	private String donorSite;
	//Feederless
	private String feederless;
	//Master	Site
	private String masterSite;
	//預計完工日
	private Date prediceEndDate;
	//申請日期
	private Date applyTime;
	//完工日期
	private Date endTime;
	//作業說明
	private String workDesc;
	
	private String siteId;
	
	private String cplNo;
	private String licenseNo;
	private String permitNo;
	//工單類別
	private String siteOrderType;
	//負責單位
	private String siteResponsibleUnit;
	//
	private String allSiteStatus;
	private String startSiteStatus;
	private String siteStatusValue;
	
	public String getWorkId() {
		return workId;
	}



	public void setWorkId(String workId) {
		this.workId = workId;
	}



	public String getWorkStatus() {
		return workStatus;
	}



	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}



	public String getWorkType() {
		return workType;
	}



	public void setWorkType(String workType) {
		this.workType = workType;
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



	public String getPurpose() {
		return purpose;
	}



	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}



	public String getOsType() {
		return osType;
	}



	public void setOsType(String osType) {
		this.osType = osType;
	}



	public String getOsVen() {
		return osVen;
	}



	public void setOsVen(String osVen) {
		this.osVen = osVen;
	}



	public String getADDR() {
		return ADDR;
	}



	public void setADDR(String aDDR) {
		ADDR = aDDR;
	}



	public String getCITY() {
		return CITY;
	}



	public void setCITY(String cITY) {
		CITY = cITY;
	}



	public String getAREA() {
		return AREA;
	}



	public void setAREA(String aREA) {
		AREA = aREA;
	}



	public String getZIP() {
		return ZIP;
	}



	public void setZIP(String zIP) {
		ZIP = zIP;
	}



	public String getVILLAGE() {
		return VILLAGE;
	}



	public void setVILLAGE(String vILLAGE) {
		VILLAGE = vILLAGE;
	}



	public String getADJACENT() {
		return ADJACENT;
	}



	public void setADJACENT(String aDJACENT) {
		ADJACENT = aDJACENT;
	}



	public String getROAD() {
		return ROAD;
	}



	public void setROAD(String rOAD) {
		ROAD = rOAD;
	}



	public String getLANE() {
		return LANE;
	}



	public void setLANE(String lANE) {
		LANE = lANE;
	}



	public String getALLEY() {
		return ALLEY;
	}



	public void setALLEY(String aLLEY) {
		ALLEY = aLLEY;
	}



	public String getSUB_ALLEY() {
		return SUB_ALLEY;
	}



	public void setSUB_ALLEY(String sUB_ALLEY) {
		SUB_ALLEY = sUB_ALLEY;
	}



	public String getDOOR_NO() {
		return DOOR_NO;
	}



	public void setDOOR_NO(String dOOR_NO) {
		DOOR_NO = dOOR_NO;
	}



	public String getDOOR_NO_EX() {
		return DOOR_NO_EX;
	}



	public void setDOOR_NO_EX(String dOOR_NO_EX) {
		DOOR_NO_EX = dOOR_NO_EX;
	}



	public String getFLOOR() {
		return FLOOR;
	}



	public void setFLOOR(String fLOOR) {
		FLOOR = fLOOR;
	}



	public String getFLOOR_EX() {
		return FLOOR_EX;
	}



	public void setFLOOR_EX(String fLOOR_EX) {
		FLOOR_EX = fLOOR_EX;
	}



	public String getROOM() {
		return ROOM;
	}



	public void setROOM(String rOOM) {
		ROOM = rOOM;
	}



	public String getADD_OTHER() {
		return ADD_OTHER;
	}



	public void setADD_OTHER(String aDD_OTHER) {
		ADD_OTHER = aDD_OTHER;
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



	public String getBtsSiteId() {
		return btsSiteId;
	}



	public void setBtsSiteId(String btsSiteId) {
		this.btsSiteId = btsSiteId;
	}



	public String getFeqId() {
		return feqId;
	}



	public void setFeqId(String feqId) {
		this.feqId = feqId;
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



	public String getFeederless() {
		return feederless;
	}



	public void setFeederless(String feederless) {
		this.feederless = feederless;
	}



	public String getMasterSite() {
		return masterSite;
	}



	public void setMasterSite(String masterSite) {
		this.masterSite = masterSite;
	}



	public Date getPrediceEndDate() {
		return prediceEndDate;
	}



	public void setPrediceEndDate(Date prediceEndDate) {
		this.prediceEndDate = prediceEndDate;
	}



	public Date getApplyTime() {
		return applyTime;
	}



	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}



	public Date getEndTime() {
		return endTime;
	}



	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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



	public String getCplNo() {
		return cplNo;
	}



	public void setCplNo(String cplNo) {
		this.cplNo = cplNo;
	}



	public String getLicenseNo() {
		return licenseNo;
	}



	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}



	public String getPermitNo() {
		return permitNo;
	}



	public void setPermitNo(String permitNo) {
		this.permitNo = permitNo;
	}



	public String getSiteOrderType() {
		return siteOrderType;
	}



	public void setSiteOrderType(String siteOrderType) {
		this.siteOrderType = siteOrderType;
	}



	public String getSiteResponsibleUnit() {
		return siteResponsibleUnit;
	}



	public void setSiteResponsibleUnit(String siteResponsibleUnit) {
		this.siteResponsibleUnit = siteResponsibleUnit;
	}

	public String getAllSiteStatus() {
		return allSiteStatus;
	}



	public void setAllSiteStatus(String allSiteStatus) {
		this.allSiteStatus = allSiteStatus;
	}



	public String getStartSiteStatus() {
		return startSiteStatus;
	}



	public void setStartSiteStatus(String startSiteStatus) {
		this.startSiteStatus = startSiteStatus;
	}



	public String getSiteStatusValue() {
		return siteStatusValue;
	}



	public void setSiteStatusValue(String siteStatusValue) {
		this.siteStatusValue = siteStatusValue;
	}



	@Override
	public String toString() {
		return "SiteBuildApplyDTO [workId=" + workId + ", workStatus=" + workStatus + ", workType="
				+ workType + ", priority=" + priority + ", applyUser=" + applyUser
				+ ", locationId=" + locationId + ", locName=" + locName + ", purpose=" + purpose
				+ ", osType=" + osType + ", osVen=" + osVen + ", ADDR=" + ADDR + ", CITY=" + CITY
				+ ", AREA=" + AREA + ", ZIP=" + ZIP + ", VILLAGE=" + VILLAGE + ", ADJACENT="
				+ ADJACENT + ", ROAD=" + ROAD + ", LANE=" + LANE + ", ALLEY=" + ALLEY
				+ ", SUB_ALLEY=" + SUB_ALLEY + ", DOOR_NO=" + DOOR_NO + ", DOOR_NO_EX="
				+ DOOR_NO_EX + ", FLOOR=" + FLOOR + ", FLOOR_EX=" + FLOOR_EX + ", ROOM=" + ROOM
				+ ", ADD_OTHER=" + ADD_OTHER + ", lon=" + lon + ", lat=" + lat + ", btsSiteId="
				+ btsSiteId + ", feqId=" + feqId + ", siteName=" + siteName + ", eqpTypeId="
				+ eqpTypeId + ", eqpModelId=" + eqpModelId + ", coverageType=" + coverageType
				+ ", btsConfig=" + btsConfig + ", donorSite=" + donorSite + ", feederless="
				+ feederless + ", masterSite=" + masterSite + ", prediceEndDate=" + prediceEndDate
				+ ", applyTime=" + applyTime + ", endTime=" + endTime + ", workDesc=" + workDesc
				+ ", siteId=" + siteId + ", cplNo=" + cplNo + ", licenseNo=" + licenseNo
				+ ", permitNo=" + permitNo + ", siteOrderType=" + siteOrderType
				+ ", siteResponsibleUnit=" + siteResponsibleUnit + ", allSiteStatus="
				+ allSiteStatus + ", startSiteStatus=" + startSiteStatus + ", siteStatusValue="
				+ siteStatusValue + "]";
	}
	
	
}
