package com.foya.noms.dto.pay;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.foya.dao.mybatis.model.TbPayPaymentRequest;

public class TbPayPaymentRequestDTO extends TbPayPaymentRequest {

	private BigDecimal sumAmt;
	
	private String paymentReqBeginDate;
	private String paymentReqEndDate;
	private String lsName;
	private BigDecimal pldgAmt;
	private BigDecimal rentAmt;
	private Date lsSdate;
	private String statusDesc;
	private String vatNbr;
	private String voucherNbr;
	private String voucherType;
	/*For Pay001_Add 查詢Grid2用: 維運區*/
	private String domain;
	/*For Pay001_Add 查詢Grid2用: 申請日期 */
	private Date appDate;
	/*For Pay001_Add 查詢Grid2用: 請款年月 */
	private String yearMonth;
	/*For Pay001_Add 查詢Grid2用: 辨別ID */
	private int _id;
	/*For Pay001_Add 查詢Grid2用: call API PI_TIME_STAMP用 */
	private String _key;
	/*For Pay001_Add 查詢Grid2用: 付款週期 call API 用 */
	private String paymentPeriod;
	private Date maxLsEDate;
	private String locationId;
	private String siteId;
	private String locType;
	private String electricityType;
	private String electricityTypeDesc;
	private String lineName;
	private String circuitUses;
	private int lieCnt;
	private String thirdGSiteId;
	private String fourthGSiteId;
	private String thirdGstatusDscr;
	private String fourthGstatusDscr;
	private String electricityMeterNbr;
	//For Pay001_Add 請款
	@JsonDeserialize(as=ArrayList.class, contentAs=TbPayPaymentRequestDtlDTO.class)
	private List<TbPayPaymentRequestDtlDTO> tbPayPaymentRequestDtlDTOList;
	
	//For Pay003_Add 請款
	@JsonDeserialize(as=ArrayList.class, contentAs=TbPayElectricityDTO.class)
	private List<TbPayElectricityDTO> tbPayElectricityDTOList;
	
	@JsonDeserialize(as=ArrayList.class, contentAs=TbPayPaymentDTO.class)
	private List<TbPayPaymentDTO> tbPayPaymentList;
		
	/**
	 * 起帳日 
	 */
	private Date pay_begin_date;
	/**
	 * 憑證
	 */
	private String voucher_credit;

	/**
	 * 請款金額
	 */
	private BigDecimal  s_amt;
	/**
	 * 未稅金額
	 */
	private BigDecimal s_tax_exclusive_amt;
	/**
	 * 營業稅
	 */
	private BigDecimal s_business_tax;
	
	private String processType;
	
	public BigDecimal getSumAmt() {
		return sumAmt;
	}
	public void setSumAmt(BigDecimal sumAmt) {
		this.sumAmt = sumAmt;
	}
	public String getPaymentReqBeginDate() {
		return paymentReqBeginDate;
	}
	public void setPaymentReqBeginDate(String paymentReqBeginDate) {
		this.paymentReqBeginDate = paymentReqBeginDate;
	}
	public String getPaymentReqEndDate() {
		return paymentReqEndDate;
	}
	public void setPaymentReqEndDate(String paymentReqEndDate) {
		this.paymentReqEndDate = paymentReqEndDate;
	}
	public String getLsName() {
		return lsName;
	}
	public void setLsName(String lsName) {
		this.lsName = lsName;
	}
	public BigDecimal getPldgAmt() {
		return pldgAmt;
	}
	public void setPldgAmt(BigDecimal pldgAmt) {
		this.pldgAmt = pldgAmt;
	}
	public BigDecimal getRentAmt() {
		return rentAmt;
	}
	public void setRentAmt(BigDecimal rentAmt) {
		this.rentAmt = rentAmt;
	}

	public String getStatusDesc() {
		return statusDesc;
	}
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
	/**
	 * @return the voucher_credit
	 */
	public String getVoucher_credit() {
		return voucher_credit;
	}
	/**
	 * @param voucher_credit the voucher_credit to set
	 */
	public void setVoucher_credit(String voucher_credit) {
		this.voucher_credit = voucher_credit;
	}
	/**
	 * @return the s_tax_exclusive_amt
	 */
	public BigDecimal getS_tax_exclusive_amt() {
		return s_tax_exclusive_amt;
	}
	/**
	 * @param s_tax_exclusive_amt the s_tax_exclusive_amt to set
	 */
	public void setS_tax_exclusive_amt(BigDecimal s_tax_exclusive_amt) {
		this.s_tax_exclusive_amt = s_tax_exclusive_amt;
	}
	
	/**
	 * @return the s_amt
	 */
	public BigDecimal getS_amt() {
		return s_amt;
	}
	/**
	 * @param s_amt the s_amt to set
	 */
	public void setS_amt(BigDecimal s_amt) {
		this.s_amt = s_amt;
	}
	/**
	 * @return the s_business_tax
	 */
	public BigDecimal getS_business_tax() {
		return s_business_tax;
	}
	/**
	 * @param s_business_tax the s_business_tax to set
	 */
	public void setS_business_tax(BigDecimal s_business_tax) {
		this.s_business_tax = s_business_tax;
	}
	/**
	 * @return the pay_begin_date
	 */
	public Date getPay_begin_date() {
		return pay_begin_date;
	}
	/**
	 * @param pay_begin_date the pay_begin_date to set
	 */
	public void setPay_begin_date(Date pay_begin_date) {
		this.pay_begin_date = pay_begin_date;
	}

	/**
	 * @return the processType
	 */
	public String getProcessType() {
		return processType;
	}
	/**
	 * @param processType the processType to set
	 */
	public void setProcessType(String processType) {
		this.processType = processType;
	}

	public String getVatNbr() {
		return vatNbr;
	}
	public void setVatNbr(String vatNbr) {
		this.vatNbr = vatNbr;
	}
	public String getVoucherNbr() {
		return voucherNbr;
	}
	public void setVoucherNbr(String voucherNbr) {
		this.voucherNbr = voucherNbr;
	}
	public String getVoucherType() {
		return voucherType;
	}
	public void setVoucherType(String voucherType) {
		this.voucherType = voucherType;
	}
	public Date getLsSdate() {
		return lsSdate;
	}
	public void setLsSdate(Date lsSdate) {
		this.lsSdate = lsSdate;
	}


	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public Date getAppDate() {
		return appDate;
	}
	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public String getYearMonth() {
		return yearMonth;
	}
	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}
	public List<TbPayPaymentRequestDtlDTO> getTbPayPaymentRequestDtlDTOList() {
		return tbPayPaymentRequestDtlDTOList;
	}
	public void setTbPayPaymentRequestDtlDTOList(
			List<TbPayPaymentRequestDtlDTO> tbPayPaymentRequestDtlDTOList) {
		this.tbPayPaymentRequestDtlDTOList = tbPayPaymentRequestDtlDTOList;
	}
	
	
	public List<TbPayElectricityDTO> getTbPayElectricityDTOList() {
		return tbPayElectricityDTOList;
	}
	public void setTbPayElectricityDTOList(
			List<TbPayElectricityDTO> tbPayElectricityDTOList) {
		this.tbPayElectricityDTOList = tbPayElectricityDTOList;
	}
	
	

	public List<TbPayPaymentDTO> getTbPayPaymentList() {
		return tbPayPaymentList;
	}
	public void setTbPayPaymentList(List<TbPayPaymentDTO> tbPayPaymentList) {
		this.tbPayPaymentList = tbPayPaymentList;
	}
	public Date getMaxLsEDate() {
		return maxLsEDate;
	}
	public void setMaxLsEDate(Date maxLsEDate) {
		this.maxLsEDate = maxLsEDate;
	}
	public String getLocationId() {
		return locationId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
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
	public String getElectricityType() {
		return electricityType;
	}
	public void setElectricityType(String electricityType) {
		this.electricityType = electricityType;
	}
	public String getElectricityTypeDesc() {
		return electricityTypeDesc;
	}
	public void setElectricityTypeDesc(String electricityTypeDesc) {
		this.electricityTypeDesc = electricityTypeDesc;
	}
	public int getLieCnt() {
		return lieCnt;
	}
	public void setLieCnt(int lieCnt) {
		this.lieCnt = lieCnt;
	}
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	public String getCircuitUses() {
		return circuitUses;
	}
	public void setCircuitUses(String circuitUses) {
		this.circuitUses = circuitUses;
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
	public String getElectricityMeterNbr() {
		return electricityMeterNbr;
	}
	public void setElectricityMeterNbr(String electricityMeterNbr) {
		this.electricityMeterNbr = electricityMeterNbr;
	}
	public String get_key() {
		return _key;
	}
	public void set_key(String _key) {
		this._key = _key;
	}
	public String getPaymentPeriod() {
		return paymentPeriod;
	}
	public void setPaymentPeriod(String paymentPeriod) {
		this.paymentPeriod = paymentPeriod;
	}
}
