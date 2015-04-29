package com.foya.noms.dto.pay;

import java.math.BigDecimal;
import java.util.Date;

import com.foya.dao.mybatis.model.TbPayRepartition;

public class TbPayRepartitionDTO extends TbPayRepartition {

	private Date paymentReqBeginDate;
	private Date paymentReqEndDate;
	private BigDecimal businessTaxAmt;
	private String siteName;
	private String bts_SITE_ID;
	private BigDecimal repartition_AMTP;
	private BigDecimal spTotalAmt;
	public Date getPaymentReqBeginDate() {
		return paymentReqBeginDate;
	}
	public void setPaymentReqBeginDate(Date paymentReqBeginDate) {
		this.paymentReqBeginDate = paymentReqBeginDate;
	}
	public Date getPaymentReqEndDate() {
		return paymentReqEndDate;
	}
	public void setPaymentReqEndDate(Date paymentReqEndDate) {
		this.paymentReqEndDate = paymentReqEndDate;
	}
	public BigDecimal getBusinessTaxAmt() {
		return businessTaxAmt;
	}
	public void setBusinessTaxAmt(BigDecimal businessTaxAmt) {
		this.businessTaxAmt = businessTaxAmt;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getBts_SITE_ID() {
		return bts_SITE_ID;
	}
	public void setBts_SITE_ID(String bts_SITE_ID) {
		this.bts_SITE_ID = bts_SITE_ID;
	}
	public BigDecimal getRepartition_AMTP() {
		return repartition_AMTP;
	}
	public void setRepartition_AMTP(BigDecimal repartition_AMTP) {
		this.repartition_AMTP = repartition_AMTP;
	}
	public BigDecimal getSpTotalAmt() {
		return spTotalAmt;
	}
	public void setSpTotalAmt(BigDecimal spTotalAmt) {
		this.spTotalAmt = spTotalAmt;
	}

}
