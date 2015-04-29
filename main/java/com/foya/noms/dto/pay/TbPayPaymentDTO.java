package com.foya.noms.dto.pay;

import java.math.BigDecimal;
import java.util.Date;

import com.foya.dao.mybatis.model.TbPayPayment;


public class TbPayPaymentDTO extends TbPayPayment {
	private String payment_request_item;
	private String dtlItemDesc;
	private String voucher_type;
	private String voucherDesc;
	private String VOUCHER_NBR;
	private Date VOUCHER_DATE;
	private String VAT_NUMBER;
	private BigDecimal totalAmt;
	private String paymentDesc;
	private String bankCodeDesc;
	private String branchCodeDesc;
	private String cashReqNo;
	private String contractNo;
	private String siteId;
	private String siteName;
	private String paymethodName;
	private BigDecimal sumExclusive;
	private BigDecimal sumAllAmt;
	private BigDecimal rentAmt;
	private String lessorName;
	private String tempKey;
	private String lessorId;
	private BigDecimal payAmt;
	private String lsName;
	private String checkShare;
	private Long voucherSeqNbr;
	private Long electricitySeqNbr;
	private String yearMonth;
	private BigDecimal sumTaxExclusiveTotalAmt;
	private BigDecimal sumTotalBusinessTax;
	private BigDecimal sumTotalIncomeTax;
	private BigDecimal sumTotalNHIAmt;
	private BigDecimal sumAmt;
	
	//ERP用
	private Date PAYMENT_REQ_BEGIN_DATE;	
	private Date PAYMENT_REQ_END_DATE;	
	private String ELECTRICITY_METER_NBR;
	private String CONTRACT_NO;
	private BigDecimal TAX_EXCLUSIVE_AMT;
	private BigDecimal BUSINESS_TAX;
	
	private int _id;//識別用 PAY001
	public String getPayment_request_item() {
		return payment_request_item;
	}
	public void setPayment_request_item(String payment_request_item) {
		this.payment_request_item = payment_request_item;
	}
	public String getDtlItemDesc() {
		return dtlItemDesc;
	}
	public void setDtlItemDesc(String dtlItemDesc) {
		this.dtlItemDesc = dtlItemDesc;
	}
	public String getVoucher_type() {
		return voucher_type;
	}
	public void setVoucher_type(String voucher_type) {
		this.voucher_type = voucher_type;
	}
	public String getVoucherDesc() {
		return voucherDesc;
	}
	public void setVoucherDesc(String voucherDesc) {
		this.voucherDesc = voucherDesc;
	}
	public BigDecimal getTotalAmt() {
		return totalAmt;
	}
	public void setTotalAmt(BigDecimal totalAmt) {
		this.totalAmt = totalAmt;
	}
	public String getPaymentDesc() {
		return paymentDesc;
	}
	public void setPaymentDesc(String paymentDesc) {
		this.paymentDesc = paymentDesc;
	}
	public String getBankCodeDesc() {
		return bankCodeDesc;
	}
	public void setBankCodeDesc(String bankCodeDesc) {
		this.bankCodeDesc = bankCodeDesc;
	}
	public String getBranchCodeDesc() {
		return branchCodeDesc;
	}
	public void setBranchCodeDesc(String branchCodeDesc) {
		this.branchCodeDesc = branchCodeDesc;
	}
	public String getCashReqNo() {
		return cashReqNo;
	}
	public void setCashReqNo(String cashReqNo) {
		this.cashReqNo = cashReqNo;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
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
	public String getPaymethodName() {
		return paymethodName;
	}
	public void setPaymethodName(String paymethodName) {
		this.paymethodName = paymethodName;
	}
	public BigDecimal getSumExclusive() {
		return sumExclusive;
	}
	public void setSumExclusive(BigDecimal sumExclusive) {
		this.sumExclusive = sumExclusive;
	}
	public BigDecimal getSumAllAmt() {
		return sumAllAmt;
	}
	public void setSumAllAmt(BigDecimal sumAllAmt) {
		this.sumAllAmt = sumAllAmt;
	}
	public BigDecimal getRentAmt() {
		return rentAmt;
	}
	public void setRentAmt(BigDecimal rentAmt) {
		this.rentAmt = rentAmt;
	}
	public String getLessorName() {
		return lessorName;
	}
	public void setLessorName(String lessorName) {
		this.lessorName = lessorName;
	}
	public String getTempKey() {
		return tempKey;
	}
	public void setTempKey(String tempKey) {
		this.tempKey = tempKey;
	}
	public String getLessorId() {
		return lessorId;
	}
	public void setLessorId(String lessorId) {
		this.lessorId = lessorId;
	}
	public BigDecimal getPayAmt() {
		return payAmt;
	}
	public void setPayAmt(BigDecimal payAmt) {
		this.payAmt = payAmt;
	}
	public String getLsName() {
		return lsName;
	}
	public void setLsName(String lsName) {
		this.lsName = lsName;
	}
	public String getCheckShare() {
		return checkShare;
	}
	public void setCheckShare(String checkShare) {
		this.checkShare = checkShare;
	}
	public Long getVoucherSeqNbr() {
		return voucherSeqNbr;
	}
	public void setVoucherSeqNbr(Long voucherSeqNbr) {
		this.voucherSeqNbr = voucherSeqNbr;
	}
	
	public Long getElectricitySeqNbr() {
		return electricitySeqNbr;
	}
	public void setElectricitySeqNbr(Long electricitySeqNbr) {
		this.electricitySeqNbr = electricitySeqNbr;
	}
	public String getYearMonth() {
		return yearMonth;
	}
	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}
	public String getVOUCHER_NBR() {
		return VOUCHER_NBR;
	}
	public void setVOUCHER_NBR(String vOUCHER_NBR) {
		VOUCHER_NBR = vOUCHER_NBR;
	}
	public Date getVOUCHER_DATE() {
		return VOUCHER_DATE;
	}
	public void setVOUCHER_DATE(Date vOUCHER_DATE) {
		VOUCHER_DATE = vOUCHER_DATE;
	}
	public String getVAT_NUMBER() {
		return VAT_NUMBER;
	}
	public void setVAT_NUMBER(String vAT_NUMBER) {
		VAT_NUMBER = vAT_NUMBER;
	}
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public Date getPAYMENT_REQ_BEGIN_DATE() {
		return PAYMENT_REQ_BEGIN_DATE;
	}
	public void setPAYMENT_REQ_BEGIN_DATE(Date pAYMENT_REQ_BEGIN_DATE) {
		PAYMENT_REQ_BEGIN_DATE = pAYMENT_REQ_BEGIN_DATE;
	}
	public Date getPAYMENT_REQ_END_DATE() {
		return PAYMENT_REQ_END_DATE;
	}
	public void setPAYMENT_REQ_END_DATE(Date pAYMENT_REQ_END_DATE) {
		PAYMENT_REQ_END_DATE = pAYMENT_REQ_END_DATE;
	}
	public String getELECTRICITY_METER_NBR() {
		return ELECTRICITY_METER_NBR;
	}
	public void setELECTRICITY_METER_NBR(String eLECTRICITY_METER_NBR) {
		ELECTRICITY_METER_NBR = eLECTRICITY_METER_NBR;
	}
	public String getCONTRACT_NO() {
		return CONTRACT_NO;
	}
	public void setCONTRACT_NO(String cONTRACT_NO) {
		CONTRACT_NO = cONTRACT_NO;
	}
	public BigDecimal getTAX_EXCLUSIVE_AMT() {
		return TAX_EXCLUSIVE_AMT;
	}
	public void setTAX_EXCLUSIVE_AMT(BigDecimal tAX_EXCLUSIVE_AMT) {
		TAX_EXCLUSIVE_AMT = tAX_EXCLUSIVE_AMT;
	}
	public BigDecimal getBUSINESS_TAX() {
		return BUSINESS_TAX;
	}
	public void setBUSINESS_TAX(BigDecimal bUSINESS_TAX) {
		BUSINESS_TAX = bUSINESS_TAX;
	}
	public BigDecimal getSumTaxExclusiveTotalAmt() {
		return sumTaxExclusiveTotalAmt;
	}
	public void setSumTaxExclusiveTotalAmt(BigDecimal sumTaxExclusiveTotalAmt) {
		this.sumTaxExclusiveTotalAmt = sumTaxExclusiveTotalAmt;
	}
	public BigDecimal getSumTotalBusinessTax() {
		return sumTotalBusinessTax;
	}
	public void setSumTotalBusinessTax(BigDecimal sumTotalBusinessTax) {
		this.sumTotalBusinessTax = sumTotalBusinessTax;
	}
	public BigDecimal getSumTotalIncomeTax() {
		return sumTotalIncomeTax;
	}
	public void setSumTotalIncomeTax(BigDecimal sumTotalIncomeTax) {
		this.sumTotalIncomeTax = sumTotalIncomeTax;
	}
	public BigDecimal getSumTotalNHIAmt() {
		return sumTotalNHIAmt;
	}
	public void setSumTotalNHIAmt(BigDecimal sumTotalNHIAmt) {
		this.sumTotalNHIAmt = sumTotalNHIAmt;
	}
	public BigDecimal getSumAmt() {
		return sumAmt;
	}
	public void setSumAmt(BigDecimal sumAmt) {
		this.sumAmt = sumAmt;
	}
	
}
