package com.foya.noms.dto.pay;

import java.math.BigDecimal;
import java.util.Date;

import com.foya.dao.mybatis.model.TbPayLocationInfo;

public class TbPayLocationInfoDTO extends TbPayLocationInfo {
	private Date leaseValidEndDate;
	private String statusDscr;
	private String lsName;
	private String siteId;
	private String locType;
	private BigDecimal appAmt;
	private BigDecimal bsTaxAmt;
	private BigDecimal inTaxAmt;
	private BigDecimal hsAmt;
	private BigDecimal totBsTaxAmt;
	private BigDecimal totInTaxAmt;
	private BigDecimal totHsAmt;
	private BigDecimal s_tax_exclusive_amt;
	private BigDecimal s_business_tax;
	private String thirdGSiteId;
	private String fourthGSiteId;
	private String thirdGstatusDscr;
	private String fourthGstatusDscr;
	private String yearMonth;
	private String payment_period;
	private Date  lease_valid_begin_date;
	private Date lease_valid_end_date;
	public Date getLeaseValidEndDate() {
		return leaseValidEndDate;
	}
	public void setLeaseValidEndDate(Date leaseValidEndDate) {
		this.leaseValidEndDate = leaseValidEndDate;
	}
	public String getStatusDscr() {
		return statusDscr;
	}
	public void setStatusDscr(String statusDscr) {
		this.statusDscr = statusDscr;
	}
	public BigDecimal getAppAmt() {
		return appAmt;
	}
	public void setAppAmt(BigDecimal appAmt) {
		this.appAmt = appAmt;
	}
	public BigDecimal getBsTaxAmt() {
		return bsTaxAmt;
	}
	public void setBsTaxAmt(BigDecimal bsTaxAmt) {
		this.bsTaxAmt = bsTaxAmt;
	}
	public BigDecimal getInTaxAmt() {
		return inTaxAmt;
	}
	public void setInTaxAmt(BigDecimal inTaxAmt) {
		this.inTaxAmt = inTaxAmt;
	}
	public BigDecimal getHsAmt() {
		return hsAmt;
	}
	public void setHsAmt(BigDecimal hsAmt) {
		this.hsAmt = hsAmt;
	}
	public String getLsName() {
		return lsName;
	}
	public void setLsName(String lsName) {
		this.lsName = lsName;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getLocType() {
		return locType;
	}
	public void setLocType(String locType) {
		this.locType = locType;
	}
	public BigDecimal getTotBsTaxAmt() {
		return totBsTaxAmt;
	}
	public void setTotBsTaxAmt(BigDecimal totBsTaxAmt) {
		this.totBsTaxAmt = totBsTaxAmt;
	}
	public BigDecimal getTotInTaxAmt() {
		return totInTaxAmt;
	}
	public void setTotInTaxAmt(BigDecimal totInTaxAmt) {
		this.totInTaxAmt = totInTaxAmt;
	}
	public BigDecimal getTotHsAmt() {
		return totHsAmt;
	}
	public void setTotHsAmt(BigDecimal totHsAmt) {
		this.totHsAmt = totHsAmt;
	}
	public BigDecimal getS_tax_exclusive_amt() {
		return s_tax_exclusive_amt;
	}
	public void setS_tax_exclusive_amt(BigDecimal s_tax_exclusive_amt) {
		this.s_tax_exclusive_amt = s_tax_exclusive_amt;
	}
	public BigDecimal getS_business_tax() {
		return s_business_tax;
	}
	public void setS_business_tax(BigDecimal s_business_tax) {
		this.s_business_tax = s_business_tax;
	}
	public String getThirdGSiteId() {
		return thirdGSiteId;
	}
	public void setThirdGSiteId(String thirdGSiteId) {
		this.thirdGSiteId = thirdGSiteId;
	}
	public String getFourthGSiteId() {
		return fourthGSiteId;
	}
	public void setFourthGSiteId(String fourthGSiteId) {
		this.fourthGSiteId = fourthGSiteId;
	}
	public String getThirdGstatusDscr() {
		return thirdGstatusDscr;
	}
	public void setThirdGstatusDscr(String thirdGstatusDscr) {
		this.thirdGstatusDscr = thirdGstatusDscr;
	}
	public String getFourthGstatusDscr() {
		return fourthGstatusDscr;
	}
	public void setFourthGstatusDscr(String fourthGstatusDscr) {
		this.fourthGstatusDscr = fourthGstatusDscr;
	}
	public String getYearMonth() {
		return yearMonth;
	}
	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}
	public String getPayment_period() {
		return payment_period;
	}
	public void setPayment_period(String payment_period) {
		this.payment_period = payment_period;
	}
	public Date getLease_valid_begin_date() {
		return lease_valid_begin_date;
	}
	public void setLease_valid_begin_date(Date lease_valid_begin_date) {
		this.lease_valid_begin_date = lease_valid_begin_date;
	}
	public Date getLease_valid_end_date() {
		return lease_valid_end_date;
	}
	public void setLease_valid_end_date(Date lease_valid_end_date) {
		this.lease_valid_end_date = lease_valid_end_date;
	}
	
}
