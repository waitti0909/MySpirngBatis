package com.foya.noms.dto.st.line;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.foya.dao.mybatis.model.TbSiteLineApply;
import com.foya.noms.enums.LineApplyStatus;
import com.foya.noms.enums.LineStatus;
import com.foya.noms.enums.WorkType;
import com.foya.noms.util.DateJsonSerializer;

public class SiteLineApplyDTO extends TbSiteLineApply {

	private String A_BTS_SITE_ID;
	private String B_BTS_SITE_ID;
	private String APP_USER_NAME;
	private String A_SITE_NAME;
    private String B_SITE_NAME;
    private String A_SITE_ID;
    private String B_SITE_ID;
	private String MAINT_AREA;
    private String A_LOC_NAME;
    private String A_ADDR;
    private String A_CNT_PSN;
    private String A_CNT_NAME;
    private String A_CNT_TEL;
	private String B_LOC_NAME;
	private String B_CNT_PSN;
	private String B_CNT_NAME;
	private String B_CNT_TEL;
	private String LINE_SPEED_NAME;
	private String WORK_TYPE;
	private String APP_TYPE_NAME;
	private String EQP_NAME;
	
	public String deptName;
	public String siteId;
	public String area;
	public String locNameA;
	public String locAddrA;
	public String cntPsnA;
	public String cntTelA;
	public String locAddrB;
	public String cntPsnB;
	public String cntTelB;
	public String LINE_PURPOSE_NAME;
	
	public String lineCnsDate;
	public String lineStartDate;
	public String lineRentEndDate;
	public String lineEndDate;

	public String LINE_NUMBER;
	
	public String getA_BTS_SITE_ID() {
		return A_BTS_SITE_ID;
	}

	public void setA_BTS_SITE_ID(String a_BTS_SITE_ID) {
		A_BTS_SITE_ID = a_BTS_SITE_ID;
	}

	public String getB_BTS_SITE_ID() {
		return B_BTS_SITE_ID;
	}

	public void setB_BTS_SITE_ID(String b_BTS_SITE_ID) {
		B_BTS_SITE_ID = b_BTS_SITE_ID;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getLocNameA() {
		return locNameA;
	}

	public void setLocNameA(String locNameA) {
		this.locNameA = locNameA;
	}

	public String getLocAddrA() {
		return locAddrA;
	}

	public void setLocAddrA(String locAddrA) {
		this.locAddrA = locAddrA;
	}

	public String getCntPsnA() {
		return cntPsnA;
	}

	public void setCntPsnA(String cntPsnA) {
		this.cntPsnA = cntPsnA;
	}

	public String getCntTelA() {
		return cntTelA;
	}

	public void setCntTelA(String cntTelA) {
		this.cntTelA = cntTelA;
	}

	public String getLocAddrB() {
		return locAddrB;
	}

	public void setLocAddrB(String locAddrB) {
		this.locAddrB = locAddrB;
	}

	public String getCntPsnB() {
		return cntPsnB;
	}

	public void setCntPsnB(String cntPsnB) {
		this.cntPsnB = cntPsnB;
	}

	public String getCntTelB() {
		return cntTelB;
	}

	public void setCntTelB(String cntTelB) {
		this.cntTelB = cntTelB;
	}

	public String getAPP_USER_NAME() {
		return APP_USER_NAME;
	}

	public void setAPP_USER_NAME(String aPP_USER_NAME) {
		APP_USER_NAME = aPP_USER_NAME;
	}

	public String getEQP_NAME() {
		return EQP_NAME;
	}

	public void setEQP_NAME(String eQP_NAME) {
		EQP_NAME = eQP_NAME;
	}

	public String getAPP_STATUS_NAME(){
		return  LineApplyStatus.detectLabel(getAPP_STATUS());
	}
	
	public String getLINE_STATUS_NAME(){
		return LineStatus.detectLabel(getLINE_STATUS());
	}
	
	@JsonSerialize(using=DateJsonSerializer.class)
	@Override
	public Date getAPP_TIME(){
		return super.getAPP_TIME();
	}
	
	@JsonSerialize(using=DateJsonSerializer.class)
	@Override
	public Date getRENT_END_DATE(){
		return super.getRENT_END_DATE();
	}
	
	@JsonSerialize(using=DateJsonSerializer.class)
	@Override
	public Date getSTART_DATE(){
		return super.getSTART_DATE();
		
	}
	
	@JsonSerialize(using=DateJsonSerializer.class)
	@Override
	public Date getEND_DATE(){
		return super.getEND_DATE();
	}
	
	@JsonSerialize(using=DateJsonSerializer.class)
	@Override
	public Date getCNS_DATE(){
		return super.getCNS_DATE();
	}
	
	public String getMAINT_AREA() {
		return MAINT_AREA;
	}

	public void setMAINT_AREA(String mAINT_AREA) {
		MAINT_AREA = mAINT_AREA;
	}

	public String getA_LOC_NAME() {
		return A_LOC_NAME;
	}

	public void setA_LOC_NAME(String a_LOC_NAME) {
		A_LOC_NAME = a_LOC_NAME;
	}

	public String getA_ADDR() {
		return A_ADDR;
	}

	public void setA_ADDR(String a_ADDR) {
		A_ADDR = a_ADDR;
	}

	public String getA_CNT_PSN() {
		return A_CNT_PSN;
	}

	public void setA_CNT_PSN(String a_CNT_PSN) {
		A_CNT_PSN = a_CNT_PSN;
	}

	public String getA_CNT_NAME() {
		return A_CNT_NAME;
	}

	public void setA_CNT_NAME(String a_CNT_NAME) {
		A_CNT_NAME = a_CNT_NAME;
	}

	public String getA_CNT_TEL() {
		return A_CNT_TEL;
	}

	public void setA_CNT_TEL(String a_CNT_TEL) {
		A_CNT_TEL = a_CNT_TEL;
	}

	public String getB_LOC_NAME() {
		return B_LOC_NAME;
	}

	public void setB_LOC_NAME(String b_LOC_NAME) {
		B_LOC_NAME = b_LOC_NAME;
	}

	public String getB_CNT_PSN() {
		return B_CNT_PSN;
	}

	public void setB_CNT_PSN(String b_CNT_PSN) {
		B_CNT_PSN = b_CNT_PSN;
	}

	public String getB_CNT_NAME() {
		return B_CNT_NAME;
	}

	public void setB_CNT_NAME(String b_CNT_NAME) {
		B_CNT_NAME = b_CNT_NAME;
	}

	public String getB_CNT_TEL() {
		return B_CNT_TEL;
	}

	public void setB_CNT_TEL(String b_CNT_TEL) {
		B_CNT_TEL = b_CNT_TEL;
	}

	public String getLINE_SPEED_NAME() {
		return LINE_SPEED_NAME;
	}

	public void setLINE_SPEED_NAME(String lINE_SPEED_NAME) {
		LINE_SPEED_NAME = lINE_SPEED_NAME;
	}
	
	public String getA_SITE_NAME() {
		return A_SITE_NAME;
	}

	public void setA_SITE_NAME(String a_SITE_NAME) {
		A_SITE_NAME = a_SITE_NAME;
	}

	public String getB_SITE_NAME() {
		return B_SITE_NAME;
	}

	public void setB_SITE_NAME(String b_SITE_NAME) {
		B_SITE_NAME = b_SITE_NAME;
	}

	public String getA_SITE_ID() {
		return A_SITE_ID;
	}

	public void setA_SITE_ID(String a_SITE_ID) {
		A_SITE_ID = a_SITE_ID;
	}

	public String getB_SITE_ID() {
		return B_SITE_ID;
	}

	public void setB_SITE_ID(String b_SITE_ID) {
		B_SITE_ID = b_SITE_ID;
	}
	
	public String getWORK_TYPE_NAME(){
		return WorkType.detectLabel(getWORK_TYPE());
	}

	public String getWORK_TYPE() {
		return WORK_TYPE;
	}

	public void setWORK_TYPE(String wORK_TYPE) {
		WORK_TYPE = wORK_TYPE;
	}

	public String getLINE_PURPOSE_NAME() {
		return LINE_PURPOSE_NAME;
	}

	public void setLINE_PURPOSE_NAME(String lINE_PURPOSE_NAME) {
		LINE_PURPOSE_NAME = lINE_PURPOSE_NAME;
	}
	
	public String getAPP_TYPE_NAME() {
		return APP_TYPE_NAME;
	}

	public void setAPP_TYPE_NAME(String aPP_TYPE_NAME) {
		APP_TYPE_NAME = aPP_TYPE_NAME;
	}

	public String getLineCnsDate() {
		return lineCnsDate;
	}

	public void setLineCnsDate(String lineCnsDate) {
		this.lineCnsDate = lineCnsDate;
	}

	public String getLineStartDate() {
		return lineStartDate;
	}

	public void setLineStartDate(String lineStartDate) {
		this.lineStartDate = lineStartDate;
	}

	public String getLineRentEndDate() {
		return lineRentEndDate;
	}

	public void setLineRentEndDate(String lineRentEndDate) {
		this.lineRentEndDate = lineRentEndDate;
	}

	public String getLineEndDate() {
		return lineEndDate;
	}

	public void setLineEndDate(String lineEndDate) {
		this.lineEndDate = lineEndDate;
	}

	
	public String getLINE_NUMBER() {
		return LINE_NUMBER;
	}

	public void setLINE_NUMBER(String lINE_NUMBER) {
		LINE_NUMBER = lINE_NUMBER;
	}

	@Override
	public String toString() {
		return "SiteLineApplyDTO [A_BTS_SITE_ID=" + A_BTS_SITE_ID
				+ ", B_BTS_SITE_ID=" + B_BTS_SITE_ID + ", APP_USER_NAME="
				+ APP_USER_NAME + ", A_SITE_NAME=" + A_SITE_NAME
				+ ", B_SITE_NAME=" + B_SITE_NAME + ", A_SITE_ID=" + A_SITE_ID
				+ ", B_SITE_ID=" + B_SITE_ID + ", MAINT_AREA=" + MAINT_AREA
				+ ", A_LOC_NAME=" + A_LOC_NAME + ", A_ADDR=" + A_ADDR
				+ ", A_CNT_PSN=" + A_CNT_PSN + ", A_CNT_NAME=" + A_CNT_NAME
				+ ", A_CNT_TEL=" + A_CNT_TEL + ", B_LOC_NAME=" + B_LOC_NAME
				+ ", B_CNT_PSN=" + B_CNT_PSN + ", B_CNT_NAME=" + B_CNT_NAME
				+ ", B_CNT_TEL=" + B_CNT_TEL + ", LINE_SPEED_NAME="
				+ LINE_SPEED_NAME + ", WORK_TYPE=" + WORK_TYPE
				+ ", APP_TYPE_NAME=" + APP_TYPE_NAME + ", EQP_NAME=" + EQP_NAME
				+ ", deptName=" + deptName + ", siteId=" + siteId + ", area="
				+ area + ", locNameA=" + locNameA + ", locAddrA=" + locAddrA
				+ ", cntPsnA=" + cntPsnA + ", cntTelA=" + cntTelA
				+ ", locAddrB=" + locAddrB + ", cntPsnB=" + cntPsnB
				+ ", cntTelB=" + cntTelB + ", LINE_PURPOSE_NAME="
				+ LINE_PURPOSE_NAME + ", lineCnsDate=" + lineCnsDate
				+ ", lineStartDate=" + lineStartDate + ", lineRentEndDate="
				+ lineRentEndDate + ", lineEndDate=" + lineEndDate
				+ ", LINE_NUMBER=" + LINE_NUMBER + ", getAPP_ID()="
				+ getAPP_ID() + ", getAPP_TYPE()=" + getAPP_TYPE()
				+ ", getAPP_DEPT()=" + getAPP_DEPT() + ", getAPP_USER()="
				+ getAPP_USER() + ", getAPP_STATUS()=" + getAPP_STATUS()
				+ ", getLINE_STATUS()=" + getLINE_STATUS() + ", getORDER_ID()="
				+ getORDER_ID() + ", getVENDOR()=" + getVENDOR()
				+ ", getLINE_PURPOSE()=" + getLINE_PURPOSE()
				+ ", getLINE_TYPE()=" + getLINE_TYPE() + ", getLINE_SPEED()="
				+ getLINE_SPEED() + ", getUSE_TYPE()=" + getUSE_TYPE()
				+ ", getO_LINE_ID()=" + getO_LINE_ID() + ", getLINE_ID()="
				+ getLINE_ID() + ", getA_LOC()=" + getA_LOC() + ", getA_EQP()="
				+ getA_EQP() + ", getA_PORT_POS()=" + getA_PORT_POS()
				+ ", getB_LOC()=" + getB_LOC() + ", getB_EQP()=" + getB_EQP()
				+ ", getB_PORT_POS()=" + getB_PORT_POS() + ", getRCP_NUM()="
				+ getRCP_NUM() + ", getMON_FEE()=" + getMON_FEE()
				+ ", getINS_FEE()=" + getINS_FEE() + ", getB_TEL()="
				+ getB_TEL() + ", getB_ADDR()=" + getB_ADDR()
				+ ", getB_NAME()=" + getB_NAME() + ", getCNT_PSN()="
				+ getCNT_PSN() + ", getCNT_TEL()=" + getCNT_TEL()
				+ ", getAPP_DESC()=" + getAPP_DESC() + ", getCHT_GE()="
				+ getCHT_GE() + ", getCR_USER()=" + getCR_USER()
				+ ", getCR_TIME()=" + getCR_TIME() + ", getMD_USER()="
				+ getMD_USER() + ", getMD_TIME()=" + getMD_TIME() + "]";
	}
	
}
