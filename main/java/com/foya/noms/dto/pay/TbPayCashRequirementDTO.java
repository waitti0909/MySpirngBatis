package com.foya.noms.dto.pay;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.foya.dao.mybatis.model.TbPayCashRequirement;

public class TbPayCashRequirementDTO extends TbPayCashRequirement {
	
	private BigDecimal PR_CNT;
	private BigDecimal VOUCHER_CNT;
	private BigDecimal S_TAX_EXCLUSIVE_TOTAL_AMT;
	private BigDecimal S_TOTAL_BUSINESS_TAX;
	private BigDecimal S_TOTAL_INCOME_TAX;
	private BigDecimal S_TOTAL_NHI_AMT;
	private BigDecimal S_FORFEIT_AMT;
	private BigDecimal AMOUNT_DUE;
	private String APP_USER_DSCR;
	private String ACCOUNT_APPROVAL_USER_DSCR;
	private int reqCnt;
	private BigDecimal totalTaxEx;
	private BigDecimal totalBusinessEx;
	private BigDecimal totalIncomeEx;
	private BigDecimal totalNhiEx;
	private BigDecimal totalAmt;
	private int voucherCnt;
	private String statusDscr;
	private String appUserCnm;
	private String accountUserCnm;
	private String accountApprovalUserCnm;
	private BigDecimal sumTaxExclusiveTotalAmt;
	private BigDecimal sumTotalBusinessTax;
	private BigDecimal sumTotalIncomeTax;
	private BigDecimal sumTotalNhiAmt;

	private BigDecimal S_EXCLUSIVE_BUSINESS_TAX;

	private BigDecimal sumPenalty;

	
	// for PAY013
	private String CONTRACT_NO;
	private String LOCATION_ID;
	private BigDecimal TAX_EXCLUSIVE_AMT;
	private BigDecimal BUSINESS_TAX;
	private BigDecimal PAYMENT_REQ_NO;
	private BigDecimal ITEM_NO;
	private BigDecimal SEQ_NBR;
	private String siteId;
	private String locTypeDscr;
	private String alocItemDscr;
	private String exchSiteId;
	private BigDecimal taxExclusiveAmt;
	private BigDecimal alocRatio;
	private BigDecimal exchAmt;
	private BigDecimal paymentReqNo;
	private BigDecimal itemNo;
	private BigDecimal seqNbr;
	private String locationId;
	private String alocItem;
	
	//For Pay001_Add 請款
	@JsonDeserialize(as=ArrayList.class, contentAs=TbPayPaymentRequestDTO.class)
	private List<TbPayPaymentRequestDTO> tbPayPaymentRequestDTOList;
	
	//For Pay003_Add 請款
	@JsonDeserialize(as=ArrayList.class, contentAs=TbPayPaymentRequestDtlDTO.class)
	private List<TbPayPaymentRequestDtlDTO> tbPayPaymentRequestDtlDTOList;

	@JsonDeserialize(as=ArrayList.class, contentAs=TbPayPaymentRequestVoucherDTO.class)
	private List<TbPayPaymentRequestVoucherDTO> tbPayPaymentRequestVoucherDTOList;

	

	public List<TbPayPaymentRequestVoucherDTO> getTbPayPaymentRequestVoucherDTOList() {
		return tbPayPaymentRequestVoucherDTOList;
	}
	public void setTbPayPaymentRequestVoucherDTOList(
			List<TbPayPaymentRequestVoucherDTO> tbPayPaymentRequestVoucherDTOList) {
		this.tbPayPaymentRequestVoucherDTOList = tbPayPaymentRequestVoucherDTOList;
	}
	public List<TbPayPaymentRequestDTO> getTbPayPaymentRequestDTOList() {
	return tbPayPaymentRequestDTOList;
	}
	public void setTbPayPaymentRequestDTOList(
			List<TbPayPaymentRequestDTO> tbPayPaymentRequestDTOList) {
		this.tbPayPaymentRequestDTOList = tbPayPaymentRequestDTOList;
	}
	public String getAccountApprovalUserCnm() {
		return accountApprovalUserCnm;
	}
	public void setAccountApprovalUserCnm(String accountApprovalUserCnm) {
		this.accountApprovalUserCnm = accountApprovalUserCnm;
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
	public BigDecimal getSumTotalNhiAmt() {
		return sumTotalNhiAmt;
	}
	public void setSumTotalNhiAmt(BigDecimal sumTotalNhiAmt) {
		this.sumTotalNhiAmt = sumTotalNhiAmt;
	}
	

	public List<TbPayPaymentRequestDtlDTO> getTbPayPaymentRequestDtlDTOList() {
		return tbPayPaymentRequestDtlDTOList;
	}
	public void setTbPayPaymentRequestDtlDTOList(
			List<TbPayPaymentRequestDtlDTO> tbPayPaymentRequestDtlDTOList) {
		this.tbPayPaymentRequestDtlDTOList = tbPayPaymentRequestDtlDTOList;
	}
	public BigDecimal getS_EXCLUSIVE_BUSINESS_TAX() {
		return S_EXCLUSIVE_BUSINESS_TAX;
	}
	public void setS_EXCLUSIVE_BUSINESS_TAX(BigDecimal s_EXCLUSIVE_BUSINESS_TAX) {
		S_EXCLUSIVE_BUSINESS_TAX = s_EXCLUSIVE_BUSINESS_TAX;
	}
	public BigDecimal getPR_CNT() {
		return PR_CNT;
	}
	public void setPR_CNT(BigDecimal pR_CNT) {
		PR_CNT = pR_CNT;
	}
	public BigDecimal getVOUCHER_CNT() {
		return VOUCHER_CNT;
	}
	public void setVOUCHER_CNT(BigDecimal vOUCHER_CNT) {
		VOUCHER_CNT = vOUCHER_CNT;
	}
	public BigDecimal getS_TAX_EXCLUSIVE_TOTAL_AMT() {
		return S_TAX_EXCLUSIVE_TOTAL_AMT;
	}
	public void setS_TAX_EXCLUSIVE_TOTAL_AMT(BigDecimal s_TAX_EXCLUSIVE_TOTAL_AMT) {
		S_TAX_EXCLUSIVE_TOTAL_AMT = s_TAX_EXCLUSIVE_TOTAL_AMT;
	}
	public BigDecimal getS_TOTAL_BUSINESS_TAX() {
		return S_TOTAL_BUSINESS_TAX;
	}
	public void setS_TOTAL_BUSINESS_TAX(BigDecimal s_TOTAL_BUSINESS_TAX) {
		S_TOTAL_BUSINESS_TAX = s_TOTAL_BUSINESS_TAX;
	}
	public BigDecimal getS_TOTAL_INCOME_TAX() {
		return S_TOTAL_INCOME_TAX;
	}
	public void setS_TOTAL_INCOME_TAX(BigDecimal s_TOTAL_INCOME_TAX) {
		S_TOTAL_INCOME_TAX = s_TOTAL_INCOME_TAX;
	}
	public BigDecimal getS_TOTAL_NHI_AMT() {
		return S_TOTAL_NHI_AMT;
	}
	public void setS_TOTAL_NHI_AMT(BigDecimal s_TOTAL_NHI_AMT) {
		S_TOTAL_NHI_AMT = s_TOTAL_NHI_AMT;
	}
	public BigDecimal getS_FORFEIT_AMT() {
		return S_FORFEIT_AMT;
	}
	public void setS_FORFEIT_AMT(BigDecimal s_FORFEIT_AMT) {
		S_FORFEIT_AMT = s_FORFEIT_AMT;
	}
	public BigDecimal getAMOUNT_DUE() {
		return AMOUNT_DUE;
	}
	public void setAMOUNT_DUE(BigDecimal aMOUNT_DUE) {
		AMOUNT_DUE = aMOUNT_DUE;
	}
	public String getAPP_USER_DSCR() {
		return APP_USER_DSCR;
	}
	public void setAPP_USER_DSCR(String aPP_USER_DSCR) {
		APP_USER_DSCR = aPP_USER_DSCR;
	}
	public String getACCOUNT_APPROVAL_USER_DSCR() {
		return ACCOUNT_APPROVAL_USER_DSCR;
	}
	public void setACCOUNT_APPROVAL_USER_DSCR(String aCCOUNT_APPROVAL_USER_DSCR) {
		ACCOUNT_APPROVAL_USER_DSCR = aCCOUNT_APPROVAL_USER_DSCR;
	}
	public int getReqCnt() {
		return reqCnt;
	}

	public void setReqCnt(int reqCnt) {
		this.reqCnt = reqCnt;
	}

	public BigDecimal getTotalTaxEx() {
		return totalTaxEx;
	}

	public void setTotalTaxEx(BigDecimal totalTaxEx) {
		this.totalTaxEx = totalTaxEx;
	}

	public BigDecimal getTotalBusinessEx() {
		return totalBusinessEx;
	}

	public void setTotalBusinessEx(BigDecimal totalBusinessEx) {
		this.totalBusinessEx = totalBusinessEx;
	}

	public BigDecimal getTotalIncomeEx() {
		return totalIncomeEx;
	}

	public void setTotalIncomeEx(BigDecimal totalIncomeEx) {
		this.totalIncomeEx = totalIncomeEx;
	}

	public BigDecimal getTotalNhiEx() {
		return totalNhiEx;
	}

	public void setTotalNhiEx(BigDecimal totalNhiEx) {
		this.totalNhiEx = totalNhiEx;
	}

	public BigDecimal getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(BigDecimal totalAmt) {
		this.totalAmt = totalAmt;
	}

	public int getVoucherCnt() {
		return voucherCnt;
	}

	public void setVoucherCnt(int voucherCnt) {
		this.voucherCnt = voucherCnt;
	}

	public String getStatusDscr() {
		return statusDscr;
	}

	public void setStatusDscr(String statusDscr) {
		this.statusDscr = statusDscr;
	}

	public String getAppUserCnm() {
		return appUserCnm;
	}

	public void setAppUserCnm(String appUserCnm) {
		this.appUserCnm = appUserCnm;
	}

	public String getAccountUserCnm() {
		return accountUserCnm;
	}

	public void setAccountUserCnm(String accountUserCnm) {
		this.accountUserCnm = accountUserCnm;
	}
	public String getCONTRACT_NO() {
		return CONTRACT_NO;
	}
	public void setCONTRACT_NO(String cONTRACT_NO) {
		CONTRACT_NO = cONTRACT_NO;
	}
	public String getLOCATION_ID() {
		return LOCATION_ID;
	}
	public void setLOCATION_ID(String lOCATION_ID) {
		LOCATION_ID = lOCATION_ID;
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
	public BigDecimal getPAYMENT_REQ_NO() {
		return PAYMENT_REQ_NO;
	}
	public void setPAYMENT_REQ_NO(BigDecimal pAYMENT_REQ_NO) {
		PAYMENT_REQ_NO = pAYMENT_REQ_NO;
	}
	public BigDecimal getITEM_NO() {
		return ITEM_NO;
	}
	public void setITEM_NO(BigDecimal iTEM_NO) {
		ITEM_NO = iTEM_NO;
	}
	public BigDecimal getSEQ_NBR() {
		return SEQ_NBR;
	}
	public void setSEQ_NBR(BigDecimal sEQ_NBR) {
		SEQ_NBR = sEQ_NBR;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getLocTypeDscr() {
		return locTypeDscr;
	}
	public void setLocTypeDscr(String locTypeDscr) {
		this.locTypeDscr = locTypeDscr;
	}
	public String getAlocItemDscr() {
		return alocItemDscr;
	}
	public void setAlocItemDscr(String alocItemDscr) {
		this.alocItemDscr = alocItemDscr;
	}
	public String getExchSiteId() {
		return exchSiteId;
	}
	public void setExchSiteId(String exchSiteId) {
		this.exchSiteId = exchSiteId;
	}
	public BigDecimal getTaxExclusiveAmt() {
		return taxExclusiveAmt;
	}
	public void setTaxExclusiveAmt(BigDecimal taxExclusiveAmt) {
		this.taxExclusiveAmt = taxExclusiveAmt;
	}
	public BigDecimal getAlocRatio() {
		return alocRatio;
	}
	public void setAlocRatio(BigDecimal alocRatio) {
		this.alocRatio = alocRatio;
	}
	public BigDecimal getExchAmt() {
		return exchAmt;
	}
	public void setExchAmt(BigDecimal exchAmt) {
		this.exchAmt = exchAmt;
	}
	public BigDecimal getPaymentReqNo() {
		return paymentReqNo;
	}
	public void setPaymentReqNo(BigDecimal paymentReqNo) {
		this.paymentReqNo = paymentReqNo;
	}
	public BigDecimal getItemNo() {
		return itemNo;
	}
	public void setItemNo(BigDecimal itemNo) {
		this.itemNo = itemNo;
	}
	public BigDecimal getSeqNbr() {
		return seqNbr;
	}
	public void setSeqNbr(BigDecimal seqNbr) {
		this.seqNbr = seqNbr;
	}
	public String getLocationId() {
		return locationId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	public String getAlocItem() {
		return alocItem;
	}
	public void setAlocItem(String alocItem) {
		this.alocItem = alocItem;
	}
	public BigDecimal getSumPenalty() {
		return sumPenalty;
	}
	public void setSumPenalty(BigDecimal sumPenalty) {
		this.sumPenalty = sumPenalty;
	}

}
