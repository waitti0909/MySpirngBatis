package com.foya.noms.dto.ls;

import java.util.ArrayList;
import java.util.List;

import com.foya.dao.mybatis.model.TbLsLocation;
import com.foya.noms.dto.pay.TbLsSiteDTO;

public class LeaseLocationDTO extends TbLsLocation{
	
	
	private boolean havingEffectiveLease ;
	
	private List<TbLsSiteDTO> tbLsSiteDTOList;
	
	private List<TbLsLocPaymentDTO> tbLsLocPaymentList;
	
	private String siteName;
	
	private String ADDR;
	private String APP_SEQ;
	
	private String LOC_NAME;
	
	private String lessorNames;

	private String allLessorPaymentMode;
	
	private String lsLeaseRelation;
	
	private String lsPayBeginDate;
	private String lsEndDate;
	
	private String PLDG_CL;
	
	private String lsEffDate;
	
	private List<SiteAlocDetailDTO> locSiteList = new ArrayList<>();
//	private List<TbLsLocPayment> locPaymentList = new ArrayList<>();
	public List<SiteAlocDetailDTO> getLocSiteList() {
		return locSiteList;
	}
	public void setLocSiteList(List<SiteAlocDetailDTO> locSiteList) {
		this.locSiteList = locSiteList;
	}
//	public List<TbLsLocPayment> getLocPaymentList() {
//		return locPaymentList;
//	}
//	public void setLocPaymentList(List<TbLsLocPayment> locPaymentList) {
//		this.locPaymentList = locPaymentList;
//	}
	public boolean isHavingEffectiveLease() {
		return havingEffectiveLease;
	}
	public void setHavingEffectiveLease(boolean havingEffectiveLease) {
		this.havingEffectiveLease = havingEffectiveLease;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getLOC_NAME() {
		return LOC_NAME;
	}
	public void setLOC_NAME(String lOC_NAME) {
		LOC_NAME = lOC_NAME;
	}
	public String getADDR() {
		return ADDR;
	}
	public void setADDR(String aDDR) {
		ADDR = aDDR;
	}
	public List<TbLsSiteDTO> getTbLsSiteDTOList() {
		return tbLsSiteDTOList;
	}
	public void setTbLsSiteDTOList(List<TbLsSiteDTO> tbLsSiteDTOList) {
		this.tbLsSiteDTOList = tbLsSiteDTOList;
	}
	public List<TbLsLocPaymentDTO> getTbLsLocPaymentList() {
		return tbLsLocPaymentList;
	}
	public void setTbLsLocPaymentList(List<TbLsLocPaymentDTO> tbLsLocPaymentList) {
		this.tbLsLocPaymentList = tbLsLocPaymentList;
	}
	public String getLessorNames() {
		return lessorNames;
	}
	public void setLessorNames(String lessorNames) {
		this.lessorNames = lessorNames;
	}
	public String getLsLeaseRelation() {
		return lsLeaseRelation;
	}
	public void setLsLeaseRelation(String lsLeaseRelation) {
		this.lsLeaseRelation = lsLeaseRelation;
	}
	public String getAllLessorPaymentMode() {
		return allLessorPaymentMode;
	}
	public void setAllLessorPaymentMode(String allLessorPaymentMode) {
		this.allLessorPaymentMode = allLessorPaymentMode;
	}
	public String getLsPayBeginDate() {
		return lsPayBeginDate;
	}
	public void setLsPayBeginDate(String lsPayBeginDate) {
		this.lsPayBeginDate = lsPayBeginDate;
	}
	public String getLsEndDate() {
		return lsEndDate;
	}
	public void setLsEndDate(String lsEndDate) {
		this.lsEndDate = lsEndDate;
	}
	public String getPLDG_CL() {
		return PLDG_CL;
	}
	public void setPLDG_CL(String pLDG_CL) {
		PLDG_CL = pLDG_CL;
	}
	public String getLsEffDate() {
		return lsEffDate;
	}
	public void setLsEffDate(String lsEffDate) {
		this.lsEffDate = lsEffDate;
	}
	public String getAPP_SEQ() {
		return APP_SEQ;
	}
	public void setAPP_SEQ(String aPP_SEQ) {
		APP_SEQ = aPP_SEQ;
	}
	
}
