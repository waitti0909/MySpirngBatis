package com.foya.noms.dto.pay;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.foya.dao.mybatis.model.TbPayPayment;
import com.foya.dao.mybatis.model.TbPayPaymentRequestDtl;
import com.foya.noms.util.DateTimeJsonSerializer;


public class TbPayPaymentRequestDtlDTO extends TbPayPaymentRequestDtl {

	private String siteName;
	private String repAmt;
	private String startDate;
	private String endDate;
	private String expDate;
	private String paymentRequestDscr;
	private String paymentTaxAmt;
	
	private BigDecimal sumPrAmt;
	private BigDecimal sumTaxExclusiveAmt;
	private BigDecimal sumBusinessTax ;
	private BigDecimal sumincomeTax ;
	private BigDecimal sumNhiAmt ;
	private String cashReqNo;	
	private String contractNo;
	private String paymentReqUserId;
	private String paymentUserName;
	private String paymentReqDate;
	private BigDecimal sumCreditAmt;
	//TB_PAY_VOUCHER_CREDIT
	private String PR_TYPE;
	private String prTypeDesc;
	private String LOCATION_ID;
	private Date CREDIT_DATE;
	// for PAY001
	private Date payBeginDate;
	private Date lsEDate;
	private BigDecimal rentAmt;
	private BigDecimal sumPrepaymentBalance;
	private String itemDesc;
	private BigDecimal sumAmt;
	private int _id;//識別用 PAY001 PAY003

	//For Pay001_Add 請款
	@JsonDeserialize(as=ArrayList.class, contentAs=TbPayPayment.class)
	private List<TbPayPayment> tbPayPaymentList;

	// for PAY011
	private Date PAY_BEGIN_DATE;
	private Date LS_E_DATE;
	private BigDecimal S_PREPAYMENT_BALANCE;
	private String PAYMENT_REQUEST_ITEM_DESC;
	private Date PAYMENT_REQ_BEGIN_DATE;
	private Date PAYMENT_REQ_END_DATE;
	private BigDecimal PAYMENT_REQUEST_AMT;
	
	// for PAY003
	private String exp_item_desc;
	private BigDecimal s_exclusive_business_amt;
	private String electricityType;
	
	public String getPR_TYPE() {
		return PR_TYPE;
	}
	public void setPR_TYPE(String pR_TYPE) {
		PR_TYPE = pR_TYPE;
	}
	public String getLOCATION_ID() {
		return LOCATION_ID;
	}
	public void setLOCATION_ID(String lOCATION_ID) {
		LOCATION_ID = lOCATION_ID;
	}
	@JsonSerialize(using = DateTimeJsonSerializer.class)
	public Date getCREDIT_DATE() {
		return CREDIT_DATE;
	}
	public void setCREDIT_DATE(Date cREDIT_DATE) {
		CREDIT_DATE = cREDIT_DATE;
	}
	
	public String getPrTypeDesc() {
		return prTypeDesc;
	}
	public void setPrTypeDesc(String prTypeDesc) {
		this.prTypeDesc = prTypeDesc;
	}
	public String getCashReqNo() {
		return cashReqNo;
	}
	public void setCashReqNo(String cashReqNo) {
		this.cashReqNo = cashReqNo;
	}
	public String getPaymentReqUserId() {
		return paymentReqUserId;
	}
	public void setPaymentReqUserId(String paymentReqUserId) {
		this.paymentReqUserId = paymentReqUserId;
	}
	public String getPaymentUserName() {
		return paymentUserName;
	}
	public void setPaymentUserName(String paymentUserName) {
		this.paymentUserName = paymentUserName;
	}
	public String getPaymentReqDate() {
		return paymentReqDate;
	}
	public void setPaymentReqDate(String paymentReqDate) {
		this.paymentReqDate = paymentReqDate;
	}
	public BigDecimal getSumCreditAmt() {
		return sumCreditAmt;
	}
	public void setSumCreditAmt(BigDecimal sumCreditAmt) {
		this.sumCreditAmt = sumCreditAmt;
	}
	public BigDecimal getSumPrAmt() {
		return sumPrAmt;
	}
	public void setSumPrAmt(BigDecimal sumPrAmt) {
		this.sumPrAmt = sumPrAmt;
	}
	public BigDecimal getSumTaxExclusiveAmt() {
		return sumTaxExclusiveAmt;
	}
	public void setSumTaxExclusiveAmt(BigDecimal sumTaxExclusiveAmt) {
		this.sumTaxExclusiveAmt = sumTaxExclusiveAmt;
	}
	public BigDecimal getSumBusinessTax() {
		return sumBusinessTax;
	}
	public void setSumBusinessTax(BigDecimal sumBusinessTax) {
		this.sumBusinessTax = sumBusinessTax;
	}
	
	public BigDecimal getSumincomeTax() {
		return sumincomeTax;
	}
	public void setSumincomeTax(BigDecimal sumincomeTax) {
		this.sumincomeTax = sumincomeTax;
	}
	
	public BigDecimal getSumNhiAmt() {
		return sumNhiAmt;
	}
	public void setSumNhiAmt(BigDecimal sumNhiAmt) {
		this.sumNhiAmt = sumNhiAmt;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getRepAmt() {
		return repAmt;
	}
	public void setRepAmt(String repAmt) {
		this.repAmt = repAmt;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getExpDate() {
		return expDate;
	}
	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}
	public String getPaymentRequestDscr() {
		return paymentRequestDscr;
	}
	public void setPaymentRequestDscr(String paymentRequestDscr) {
		this.paymentRequestDscr = paymentRequestDscr;
	}
	public String getPaymentTaxAmt() {
		return paymentTaxAmt;
	}
	public void setPaymentTaxAmt(String paymentTaxAmt) {
		this.paymentTaxAmt = paymentTaxAmt;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public Date getPAY_BEGIN_DATE() {
		return PAY_BEGIN_DATE;
	}
	public void setPAY_BEGIN_DATE(Date pAY_BEGIN_DATE) {
		PAY_BEGIN_DATE = pAY_BEGIN_DATE;
	}
	public Date getLS_E_DATE() {
		return LS_E_DATE;
	}
	public void setLS_E_DATE(Date lS_E_DATE) {
		LS_E_DATE = lS_E_DATE;
	}
	public BigDecimal getS_PREPAYMENT_BALANCE() {
		return S_PREPAYMENT_BALANCE;
	}
	public void setS_PREPAYMENT_BALANCE(BigDecimal s_PREPAYMENT_BALANCE) {
		S_PREPAYMENT_BALANCE = s_PREPAYMENT_BALANCE;
	}
	public String getPAYMENT_REQUEST_ITEM_DESC() {
		return PAYMENT_REQUEST_ITEM_DESC;
	}
	public void setPAYMENT_REQUEST_ITEM_DESC(String pAYMENT_REQUEST_ITEM_DESC) {
		PAYMENT_REQUEST_ITEM_DESC = pAYMENT_REQUEST_ITEM_DESC;
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
	public BigDecimal getPAYMENT_REQUEST_AMT() {
		return PAYMENT_REQUEST_AMT;
	}
	public void setPAYMENT_REQUEST_AMT(BigDecimal pAYMENT_REQUEST_AMT) {
		PAYMENT_REQUEST_AMT = pAYMENT_REQUEST_AMT;
	}
	public String getExp_item_desc() {
		return exp_item_desc;
	}
	public void setExp_item_desc(String exp_item_desc) {
		this.exp_item_desc = exp_item_desc;
	}
	public BigDecimal getS_exclusive_business_amt() {
		return s_exclusive_business_amt;
	}
	public void setS_exclusive_business_amt(BigDecimal s_exclusive_business_amt) {
		this.s_exclusive_business_amt = s_exclusive_business_amt;
	}
	
	public Date getPayBeginDate() {
		return payBeginDate;
	}
	public void setPayBeginDate(Date payBeginDate) {
		this.payBeginDate = payBeginDate;
	}
	public Date getLsEDate() {
		return lsEDate;
	}
	public void setLsEDate(Date lsEDate) {
		this.lsEDate = lsEDate;
	}
	public BigDecimal getRentAmt() {
		return rentAmt;
	}
	public void setRentAmt(BigDecimal rentAmt) {
		this.rentAmt = rentAmt;
	}
	public BigDecimal getSumPrepaymentBalance() {
		return sumPrepaymentBalance;
	}
	public void setSumPrepaymentBalance(BigDecimal sumPrepaymentBalance) {
		this.sumPrepaymentBalance = sumPrepaymentBalance;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public BigDecimal getSumAmt() {
		return sumAmt;
	}
	public void setSumAmt(BigDecimal sumAmt) {
		this.sumAmt = sumAmt;
	}
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}

	public List<TbPayPayment> getTbPayPaymentList() {
		return tbPayPaymentList;
	}
	public void setTbPayPaymentList(List<TbPayPayment> tbPayPaymentList) {
		this.tbPayPaymentList = tbPayPaymentList;
	}
	public String getElectricityType() {
		return electricityType;
	}
	public void setElectricityType(String electricityType) {
		this.electricityType = electricityType;
	}
	
}
