package com.foya.noms.dto.st;

import java.util.Date;

import com.foya.dao.mybatis.model.TbSiteLocation;
import com.foya.noms.enums.InBuildingType;
import com.foya.noms.enums.NSLevel;
import com.foya.noms.enums.RoomType;
import com.foya.noms.enums.SiteType;

public class SiteLocationDTO extends TbSiteLocation {
	private String townName;
	private String cityName;
	private String btsSiteId;
	private String siteId;
	private String siteType;
	private String siteStatus;
	private String feqId;
	private String maintAreaName;
	private String maintDeptName;
	private String maintTeamName;
	private String maintChnNm;
	private String maintCellular;
	private Date constrKoDate;
	private Date constrDoneDate;
	private Date smrReadyDate;
	private Date upsReadyDate;
	private Date acReadyDate;
	private Date dynamoReadyDate;
	private Date admReadyDate;
	private Date powerReadyDate;
	private Date transReadyDate;

	private String cityAreaName;
	private String locTypeName;

	private Date onAirDate;

	public SiteLocationDTO() {
		this.setIS_FREEENTER("N");
		this.setIS_KEY("N");
		this.setIS_INDOOR("N");
		this.setNEED_MISC_LIC("N");
		this.setHAS_MISC_LIC("N");
	}

	public String getTownName() {
		return townName;
	}

	public void setTownName(String townName) {
		this.townName = townName;
	}

	public String getBtsSiteId() {
		return btsSiteId;
	}

	public void setBtsSiteId(String btsSiteId) {
		this.btsSiteId = btsSiteId;
	}

	public String getSiteStatus() {
		return siteStatus;
	}

	public void setSiteStatus(String siteStatus) {
		this.siteStatus = siteStatus;
	}

	public String getFeqId() {
		return feqId;
	}

	public void setFeqId(String feqId) {
		this.feqId = feqId;
	}

	public String getSiteType() {
		return siteType;
	}

	public void setSiteType(String siteType) {
		this.siteType = siteType;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getMaintAreaName() {
		return maintAreaName;
	}

	public void setMaintAreaName(String maintAreaName) {
		this.maintAreaName = maintAreaName;
	}

	public String getMaintDeptName() {
		return maintDeptName;
	}

	public void setMaintDeptName(String maintDeptName) {
		this.maintDeptName = maintDeptName;
	}

	public String getMaintTeamName() {
		return maintTeamName;
	}

	public void setMaintTeamName(String maintTeamName) {
		this.maintTeamName = maintTeamName;
	}

	public String getLocTypeName() {
		return SiteType.detectLabel(getLOC_TYPE());
	}

	public String getBaseTypeName() {
		return RoomType.detectLabel(getBASE_TYPE());
	}

	public String getIndoorOptionName() {
		return InBuildingType.detectLabel(getINDOOR_OPTION());
	}

	public String getNSLevelName() {
		return NSLevel.detectLabel(getNS_LEVEL());
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public Date getConstrKoDate() {
		return constrKoDate;
	}

	public void setConstrKoDate(Date constrKoDate) {
		this.constrKoDate = constrKoDate;
	}

	public Date getConstrDoneDate() {
		return constrDoneDate;
	}

	public void setConstrDoneDate(Date constrDoneDate) {
		this.constrDoneDate = constrDoneDate;
	}

	public Date getSmrReadyDate() {
		return smrReadyDate;
	}

	public void setSmrReadyDate(Date smrReadyDate) {
		this.smrReadyDate = smrReadyDate;
	}

	public Date getUpsReadyDate() {
		return upsReadyDate;
	}

	public void setUpsReadyDate(Date upsReadyDate) {
		this.upsReadyDate = upsReadyDate;
	}

	public Date getAcReadyDate() {
		return acReadyDate;
	}

	public void setAcReadyDate(Date acReadyDate) {
		this.acReadyDate = acReadyDate;
	}

	public Date getDynamoReadyDate() {
		return dynamoReadyDate;
	}

	public void setDynamoReadyDate(Date dynamoReadyDate) {
		this.dynamoReadyDate = dynamoReadyDate;
	}

	public Date getAdmReadyDate() {
		return admReadyDate;
	}

	public Date getPowerReadyDate() {
		return powerReadyDate;
	}

	public void setPowerReadyDate(Date powerReadyDate) {
		this.powerReadyDate = powerReadyDate;
	}

	public Date getTransReadyDate() {
		return transReadyDate;
	}

	public void setTransReadyDate(Date transReadyDate) {
		this.transReadyDate = transReadyDate;
	}

	public void setAdmReadyDate(Date admReadyDate) {
		this.admReadyDate = admReadyDate;
	}

	public String getCityAreaName() {
		return cityAreaName;
	}

	public void setCityAreaName(String cityAreaName) {
		this.cityAreaName = cityAreaName;
	}

	public void setLocTypeName(String locTypeName) {
		this.locTypeName = locTypeName;
	}

	public Date getOnAirDate() {
		return onAirDate;
	}

	public void setOnAirDate(Date onAirDate) {
		this.onAirDate = onAirDate;
	}

	public String getMaintChnNm() {
		return maintChnNm;
	}

	public void setMaintChnNm(String maintChnNm) {
		this.maintChnNm = maintChnNm;
	}

	public String getMaintCellular() {
		return maintCellular;
	}

	public void setMaintCellular(String maintCellular) {
		this.maintCellular = maintCellular;
	}

	@Override
	public String toString() {
		return "SiteLocationDTO [townName=" + townName + ", cityName=" + cityName + ", btsSiteId="
				+ btsSiteId + ", siteId=" + siteId + ", siteType=" + siteType + ", siteStatus="
				+ siteStatus + ", feqId=" + feqId + ", maintAreaName=" + maintAreaName
				+ ", maintDeptName=" + maintDeptName + ", maintTeamName=" + maintTeamName
				+ ", maintChnNm=" + maintChnNm + ", maintCellular=" + maintCellular
				+ ", constrKoDate=" + constrKoDate + ", constrDoneDate=" + constrDoneDate
				+ ", smrReadyDate=" + smrReadyDate + ", upsReadyDate=" + upsReadyDate
				+ ", acReadyDate=" + acReadyDate + ", dynamoReadyDate=" + dynamoReadyDate
				+ ", admReadyDate=" + admReadyDate + ", powerReadyDate=" + powerReadyDate
				+ ", transReadyDate=" + transReadyDate + ", cityAreaName=" + cityAreaName
				+ ", locTypeName=" + locTypeName + ", onAirDate=" + onAirDate + "]";
	}

}
