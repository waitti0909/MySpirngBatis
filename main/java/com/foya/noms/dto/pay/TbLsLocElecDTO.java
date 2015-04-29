package com.foya.noms.dto.pay;

import java.util.Date;

import com.foya.dao.mybatis.model.TbLsLocElec;

public class TbLsLocElecDTO extends TbLsLocElec {

	private String siteId;
	private String siteName;
	private String NORMAL_PRICE;
	private String SPECIAL_PRICE;
	private String LOCATION_ID;
	private Date CHG_DATE;
	
	/** PAY003A 使用 */
	private String ELECTRICITY_METER_NBR;
	private String electricityType;
	
	
	public String getELECTRICITY_METER_NBR() {
		return ELECTRICITY_METER_NBR;
	}
	public void setELECTRICITY_METER_NBR(String eLECTRICITY_METER_NBR) {
		ELECTRICITY_METER_NBR = eLECTRICITY_METER_NBR;
	}
	public String getElectricityType() {
		return electricityType;
	}
	public void setElectricityType(String electricityType) {
		this.electricityType = electricityType;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getNORMAL_PRICE() {
		return NORMAL_PRICE;
	}
	public void setNORMAL_PRICE(String nORMAL_PRICE) {
		NORMAL_PRICE = nORMAL_PRICE;
	}
	public String getSPECIAL_PRICE() {
		return SPECIAL_PRICE;
	}
	public void setSPECIAL_PRICE(String sPECIAL_PRICE) {
		SPECIAL_PRICE = sPECIAL_PRICE;
	}
	public String getLOCATION_ID() {
		return LOCATION_ID;
	}
	public void setLOCATION_ID(String lOCATION_ID) {
		LOCATION_ID = lOCATION_ID;
	}
	public Date getCHG_DATE() {
		return CHG_DATE;
	}
	public void setCHG_DATE(Date cHG_DATE) {
		CHG_DATE = cHG_DATE;
	}
	
}
