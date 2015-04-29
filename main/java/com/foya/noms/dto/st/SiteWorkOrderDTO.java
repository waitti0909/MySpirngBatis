package com.foya.noms.dto.st;

import java.util.Date;

import com.foya.dao.mybatis.model.TbSiteWorkOrder;
public class SiteWorkOrderDTO extends TbSiteWorkOrder{
	private String orderTypeName;
	private String deptName;
	private String btsSiteId;
	private String siteName;
	private String ChnName;
	private String OS_ID;
	private String APL_USER;
	private String APL_DEPT;
	private String OS_DESC;
	private String OS_TIME;
	private String APL_TIME;
	private String EQP_TYPE;
	private String STATUS;
	private String AP_DATE;
	private String REP_TEAM;
	private String CO_VAT_NO;
	private String ITEM_TYPE;
	private String PO_ID;
	private String PO_YEAR;
	private String WORK_TYPE;
	private String workTypeName;
	private String siteId;
	private String orderStatusName;
	private String REP_TEAM_NAME;
	private String APL_USER_NAME;
	private String EQP_TYPE_NAME;
	private String FEQ_ID;
	private String feqName;
	private String work_STATUS;
	private Date endDate;
	public String getOrderTypeName() {
		return orderTypeName;
	}
	public void setOrderTypeName(String orderTypeName) {
		this.orderTypeName = orderTypeName;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	@Override
	public String toString() {
		return "SiteWorkOrderDTO [orderTypeName=" + orderTypeName
				+ ", deptName=" + deptName + ", getORDER_ID()=" + getORDER_ID()
				+ ", getORDER_TYPE()=" + getORDER_TYPE() + ", getWORK_ID()="
				+ getWORK_ID() + ", getODR_SEQ()=" + getODR_SEQ()
				+ ", getPRIORITY()=" + getPRIORITY() + ", getODR_STATUS()="
				+ getODR_STATUS() + ", getREP_DEPT()=" + getREP_DEPT()
				+ ", getREP_USER()=" + getREP_USER() + ", getASSIGN_TIME()="
				+ getASSIGN_TIME() + ", getPICK_USER()=" + getPICK_USER()
				+ ", getPICK_TIME()=" + getPICK_TIME() + ", getEND_USER()="
				+ getEND_USER() + ", getEND_TIME()=" + getEND_TIME()
				+ ", getEND_DESC()=" + getEND_DESC() + ", getCR_USER()="
				+ getCR_USER() + ", getCR_TIME()=" + getCR_TIME()
				+ ", getMD_USER()=" + getMD_USER() + ", getMD_TIME()="
				+ getMD_TIME() + ", toString()=" + super.toString()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ "]";
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
	public String getChnName() {
		return ChnName;
	}
	public void setChnName(String chnName) {
		ChnName = chnName;
	}
	public String getOS_ID() {
		return OS_ID;
	}
	public void setOS_ID(String oS_ID) {
		OS_ID = oS_ID;
	}
	public String getAPL_USER() {
		return APL_USER;
	}
	public void setAPL_USER(String aPL_USER) {
		APL_USER = aPL_USER;
	}
	public String getAPL_DEPT() {
		return APL_DEPT;
	}
	public void setAPL_DEPT(String aPL_DEPT) {
		APL_DEPT = aPL_DEPT;
	}
	public String getOS_DESC() {
		return OS_DESC;
	}
	public void setOS_DESC(String oS_DESC) {
		OS_DESC = oS_DESC;
	}
	public String getEQP_TYPE() {
		return EQP_TYPE;
	}
	public void setEQP_TYPE(String eQP_TYPE) {
		EQP_TYPE = eQP_TYPE;
	}
	public String getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}
	public String getOS_TIME() {
		return OS_TIME;
	}
	public void setOS_TIME(String oS_TIME) {
		OS_TIME = oS_TIME;
	}
	public String getAPL_TIME() {
		return APL_TIME;
	}
	public void setAPL_TIME(String aPL_TIME) {
		APL_TIME = aPL_TIME;
	}
	public String getAP_DATE() {
		return AP_DATE;
	}
	public void setAP_DATE(String aP_DATE) {
		AP_DATE = aP_DATE;
	}
	public String getREP_TEAM() {
		return REP_TEAM;
	}
	public void setREP_TEAM(String rEP_TEAM) {
		REP_TEAM = rEP_TEAM;
	}
	public String getCO_VAT_NO() {
		return CO_VAT_NO;
	}
	public void setCO_VAT_NO(String cO_VAT_NO) {
		CO_VAT_NO = cO_VAT_NO;
	}
	public String getITEM_TYPE() {
		return ITEM_TYPE;
	}
	public void setITEM_TYPE(String iTEM_TYPE) {
		ITEM_TYPE = iTEM_TYPE;
	}
	public String getPO_ID() {
		return PO_ID;
	}
	public void setPO_ID(String pO_ID) {
		PO_ID = pO_ID;
	}
	public String getPO_YEAR() {
		return PO_YEAR;
	}
	public void setPO_YEAR(String pO_YEAR) {
		PO_YEAR = pO_YEAR;
	}
	public String getWORK_TYPE() {
		return WORK_TYPE;
	}
	public void setWORK_TYPE(String wORK_TYPE) {
		WORK_TYPE = wORK_TYPE;
	}
	public String getWorkTypeName() {
		return workTypeName;
	}
	public void setWorkTypeName(String workTypeName) {
		this.workTypeName = workTypeName;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getOrderStatusName() {
		return orderStatusName;
	}
	public void setOrderStatusName(String orderStatusName) {
		this.orderStatusName = orderStatusName;
	}
	public String getREP_TEAM_NAME() {
		return REP_TEAM_NAME;
	}
	public void setREP_TEAM_NAME(String rEP_TEAM_NAME) {
		REP_TEAM_NAME = rEP_TEAM_NAME;
	}
	public String getAPL_USER_NAME() {
		return APL_USER_NAME;
	}
	public void setAPL_USER_NAME(String aPL_USER_NAME) {
		APL_USER_NAME = aPL_USER_NAME;
	}
	public String getEQP_TYPE_NAME() {
		return EQP_TYPE_NAME;
	}
	public void setEQP_TYPE_NAME(String eQP_TYPE_NAME) {
		EQP_TYPE_NAME = eQP_TYPE_NAME;
	}
	public String getFEQ_ID() {
		return FEQ_ID;
	}
	public void setFEQ_ID(String fEQ_ID) {
		FEQ_ID = fEQ_ID;
	}
	public String getFeqName() {
		return feqName;
	}
	public void setFeqName(String feqName) {
		this.feqName = feqName;
	}
	public String getWork_STATUS() {
		return work_STATUS;
	}
	public void setWork_STATUS(String work_STATUS) {
		this.work_STATUS = work_STATUS;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
    
    
	
	
	
}
