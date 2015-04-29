package com.foya.noms.dto.pay;

import com.foya.dao.mybatis.model.TbPayElectricity;

import java.math.BigDecimal;
import java.util.Date;

public class TbPayElectricityDTO extends TbPayElectricity {
	
	private String cashReqNo;
	private String contractName;
	private String electricityDscr;
	private String siteName;
	private String useTypeDscr;
	private Date lsSDate;
	private String ifAutoDeductDscr;
	private String  ifNoSiteMappingDscr;
	private String siteMemoDSCR;
	private String appDate;
	
	private String chrg_mode;
	private Date elec_begin_date;
	private Date elec_end_date;
	private BigDecimal used_degree_day;
	private BigDecimal m_day;
	private BigDecimal last_used_degree;
	private String ratio;
	
	private String IF_NO_SITE_MAPPING_DSCR;
	
	public String getCashReqNo() {
		return cashReqNo;
	}
	public void setCashReqNo(String cashReqNo) {
		this.cashReqNo = cashReqNo;
	}
	public String getContractName() {
		return contractName;
	}
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	public String getElectricityDscr() {
		return electricityDscr;
	}
	public void setElectricityDscr(String electricityDscr) {
		this.electricityDscr = electricityDscr;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getUseTypeDscr() {
		return useTypeDscr;
	}
	public void setUseTypeDscr(String useTypeDscr) {
		this.useTypeDscr = useTypeDscr;
	}
	public Date getLsSDate() {
		return lsSDate;
	}
	public void setLsSDate(Date lsSDate) {
		this.lsSDate = lsSDate;
	}
	public String getIfAutoDeductDscr() {
		return ifAutoDeductDscr;
	}
	public void setIfAutoDeductDscr(String ifAutoDeductDscr) {
		this.ifAutoDeductDscr = ifAutoDeductDscr;
	}
	public String getIfNoSiteMappingDscr() {
		return ifNoSiteMappingDscr;
	}
	public void setIfNoSiteMappingDscr(String ifNoSiteMappingDscr) {
		this.ifNoSiteMappingDscr = ifNoSiteMappingDscr;
	}
	public String getSiteMemoDSCR() {
		return siteMemoDSCR;
	}
	public void setSiteMemoDSCR(String siteMemoDSCR) {
		this.siteMemoDSCR = siteMemoDSCR;
	}

	public String getChrg_mode() {
		return chrg_mode;
	}
	public void setChrg_mode(String chrg_mode) {
		this.chrg_mode = chrg_mode;
	}
	public Date getElec_begin_date() {
		return elec_begin_date;
	}
	public void setElec_begin_date(Date elec_begin_date) {
		this.elec_begin_date = elec_begin_date;
	}
	public Date getElec_end_date() {
		return elec_end_date;
	}
	public void setElec_end_date(Date elec_end_date) {
		this.elec_end_date = elec_end_date;
	}
	public BigDecimal getUsed_degree_day() {
		return used_degree_day;
	}
	public void setUsed_degree_day(BigDecimal used_degree_day) {
		this.used_degree_day = used_degree_day;
	}
	
	/**
	 * @return the m_day
	 */
	public BigDecimal getM_day() {
		return m_day;
	}
	/**
	 * @param m_day the m_day to set
	 */
	public void setM_day(BigDecimal m_day) {
		this.m_day = m_day;
	}
	public BigDecimal getLast_used_degree() {
		return last_used_degree;
	}
	public void setLast_used_degree(BigDecimal last_used_degree) {
		this.last_used_degree = last_used_degree;
	}
	public String getRatio() {
		return ratio;
	}
	public void setRatio(String ratio) {
		this.ratio = ratio;
	}
	

	public String getAppDate() {
		return appDate;
	}
	public void setAppDate(String appDate) {
		this.appDate = appDate;
	}
	public String getIF_NO_SITE_MAPPING_DSCR() {
		return IF_NO_SITE_MAPPING_DSCR;
	}
	public void setIF_NO_SITE_MAPPING_DSCR(String iF_NO_SITE_MAPPING_DSCR) {
		IF_NO_SITE_MAPPING_DSCR = iF_NO_SITE_MAPPING_DSCR;
	}

	
}
