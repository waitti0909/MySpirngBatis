package com.foya.noms.dto.st;

import com.foya.dao.mybatis.model.TbSiteWork;
import com.foya.noms.enums.OrderStatus;
import com.foya.noms.enums.WorkType;

public class SiteWorkDTO extends TbSiteWork{
	private String feqName;
	private String siteStatus;
	private String siteStatusName;
	private String addr;
	private String deptName;
	private String REP_DEPT;
	private String feqType;
	private String workStatusName;
	private String srFeqId;
	private String orderId;
	private String repUserName;
	private String eqpName;
	private String ODR_STATUS;
	private String cplNo;
	private String licenseNo;
	private String permitNo;
	private String siteId;
	
	public String getFeqName() {
		return feqName;
	}
	public void setFeqName(String feqName) {
		this.feqName = feqName;
	}
	public String getSiteStatus() {
		return siteStatus;
	}
	public void setSiteStatus(String siteStatus) {
		this.siteStatus = siteStatus;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getFeqType() {
		return feqType;
	}
	public void setFeqType(String feqType) {
		this.feqType = feqType;
	}
	public String getWorkStatusName() {
		return workStatusName;
	}
	public void setWorkStatusName(String workStatusName) {
		this.workStatusName = workStatusName;
	}
	public String getSrFeqId() {
		return srFeqId;
	}
	public void setSrFeqId(String srFeqId) {
		this.srFeqId = srFeqId;
	}
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	
	public String getRepUserName() {
		return repUserName;
	}
	
	public void setRepUserName(String repUserName) {
		this.repUserName = repUserName;
	}
	
	public String getOdStatusName(){
		return OrderStatus.detectLabel(getODR_STATUS());
	}
	
	public String getWorkTypeName(){
		return WorkType.detectLabel(super.getWORK_TYPE());
	}
	
	public String getEqpName() {
		return eqpName;
	}
	
	public void setEqpName(String eqpName) {
		this.eqpName = eqpName;
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

	public String getODR_STATUS() {
		return ODR_STATUS;
	}
	public void setODR_STATUS(String oDR_STATUS) {
		ODR_STATUS = oDR_STATUS;
	}
	public String getSiteStatusName() {
		return siteStatusName;
	}
	public void setSiteStatusName(String siteStatusName) {
		this.siteStatusName = siteStatusName;
	}
	
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	
	public String getREP_DEPT() {
		return REP_DEPT;
	}
	
	public void setREP_DEPT(String rEP_DEPT) {
		REP_DEPT = rEP_DEPT;
	}
	
	@Override
	public String toString() {
		return "SiteWorkDTO [feqName=" + feqName + ", siteStatus=" + siteStatus
				+ ", siteStatusName=" + siteStatusName + ", addr=" + addr
				+ ", deptName=" + deptName + ", REP_DEPT=" + REP_DEPT
				+ ", feqType=" + feqType + ", workStatusName=" + workStatusName
				+ ", srFeqId=" + srFeqId + ", orderId=" + orderId
				+ ", repUserName=" + repUserName + ", eqpName=" + eqpName
				+ ", ODR_STATUS=" + ODR_STATUS + ", cplNo=" + cplNo
				+ ", licenseNo=" + licenseNo + ", permitNo=" + permitNo
				+ ", siteId=" + siteId + ", getWORK_ID()=" + getWORK_ID()
				+ ", getWORK_TYPE()=" + getWORK_TYPE() + ", getWORK_STATUS()="
				+ getWORK_STATUS() + ", getAPP_DEPT()=" + getAPP_DEPT()
				+ ", getAPL_USER()=" + getAPL_USER() + ", getAPL_TIME()="
				+ getAPL_TIME() + ", getEND_TIME()=" + getEND_TIME()
				+ ", getPRIORITY()=" + getPRIORITY() + ", getWORK_DESC()="
				+ getWORK_DESC() + ", getSR_ID()=" + getSR_ID()
				+ ", getSR_LON()=" + getSR_LON() + ", getSR_LAT()="
				+ getSR_LAT() + ", getSR_COVER_RANG()=" + getSR_COVER_RANG()
				+ ", getLOCATION_ID()=" + getLOCATION_ID() + ", getSITE_ID()="
				+ getSITE_ID() + ", getBTS_SITE_ID()=" + getBTS_SITE_ID()
				+ ", getFEQ_ID()=" + getFEQ_ID() + ", getPURPOSE()="
				+ getPURPOSE() + ", getSITE_NAME()=" + getSITE_NAME()
				+ ", getEQP_TYPE_ID()=" + getEQP_TYPE_ID()
				+ ", getEQP_MODEL_ID()=" + getEQP_MODEL_ID()
				+ ", getCOVERAGE_TYPE()=" + getCOVERAGE_TYPE()
				+ ", getBTS_CONFIG()=" + getBTS_CONFIG() + ", getDONOR_SITE()="
				+ getDONOR_SITE() + ", getFEEDERLESS()=" + getFEEDERLESS()
				+ ", getMASTER_SITE()=" + getMASTER_SITE() + ", getOS_VEN()="
				+ getOS_VEN() + ", getOS_TYPE()=" + getOS_TYPE()
				+ ", getPREDICE_END_DATE()=" + getPREDICE_END_DATE()
				+ ", getEND_DATE()=" + getEND_DATE() + ", getADDR()="
				+ getADDR() + ", getZIP()=" + getZIP() + ", getCITY()="
				+ getCITY() + ", getAREA()=" + getAREA() + ", getVILLAGE()="
				+ getVILLAGE() + ", getADJACENT()=" + getADJACENT()
				+ ", getROAD()=" + getROAD() + ", getLANE()=" + getLANE()
				+ ", getALLEY()=" + getALLEY() + ", getSUB_ALLEY()="
				+ getSUB_ALLEY() + ", getDOOR_NO()=" + getDOOR_NO()
				+ ", getDOOR_NO_EX()=" + getDOOR_NO_EX() + ", getFLOOR()="
				+ getFLOOR() + ", getFLOOR_EX()=" + getFLOOR_EX()
				+ ", getROOM()=" + getROOM() + ", getADD_OTHER()="
				+ getADD_OTHER() + ", getCPL_NO()=" + getCPL_NO()
				+ ", getPERMIT_NO()=" + getPERMIT_NO() + ", getLICENSE_NO()="
				+ getLICENSE_NO() + ", getCR_USER()=" + getCR_USER()
				+ ", getCR_TIME()=" + getCR_TIME() + ", getMD_USER()="
				+ getMD_USER() + ", getMD_TIME()=" + getMD_TIME()
				+ ", getEND_SITE_STATUS()=" + getEND_SITE_STATUS()
				+ ", getSTART_SITE_STATUS()=" + getSTART_SITE_STATUS() + "]";
	}
	
	
	
}
