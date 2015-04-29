package com.foya.noms.dto.pay;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.foya.dao.mybatis.model.TbPayOutsourceAcceptance;
import com.foya.noms.util.DateTimeJsonSerializer;

public class TbPayOutsourceAcceptanceDTO extends TbPayOutsourceAcceptance {

	private BigDecimal TAX_EXCLUSIVE_AMT;
	private BigDecimal BUSINESS_TAX;
	private BigDecimal AP_AMT;
	private String DOMAIN_NAME;
	private String CO_NAME;
	private String APP_USER_NAME;
	private String STATUS_DSCR;
	private String PO_NAME;
	private String CERTIFICATE_FLAG;

	@JsonSerialize(using = DateTimeJsonSerializer.class)
	public Date getAP_DATE() {
		return super.getAP_DATE();
	}

	@JsonSerialize(using = DateTimeJsonSerializer.class)
	public Date getPRINT_DATE() {
		return super.getPRINT_DATE();
	}

	@JsonSerialize(using = DateTimeJsonSerializer.class)
	public Date getCR_TIME() {
		return super.getCR_TIME();
	}

	@JsonSerialize(using = DateTimeJsonSerializer.class)
	public Date getMD_TIME() {
		return super.getMD_TIME();
	}

	@JsonSerialize(using = DateTimeJsonSerializer.class)
	public Date getSUBPOENA_DATE() {
		return super.getSUBPOENA_DATE();
	}

	@JsonSerialize(using = DateTimeJsonSerializer.class)
	public Date getAPP_DATE() {
		return super.getAPP_DATE();
	}

	public BigDecimal getTAX_EXCLUSIVE_AMT() {
		return TAX_EXCLUSIVE_AMT;
	}

	public void setTAX_EXCLUSIVE_AMT(BigDecimal tAX_EXCLUSIVE_AMT) {
		this.TAX_EXCLUSIVE_AMT = tAX_EXCLUSIVE_AMT;
	}

	public BigDecimal getBUSINESS_TAX() {
		return BUSINESS_TAX;
	}

	public void setBUSINESS_TAX(BigDecimal bUSINESS_TAX) {
		this.BUSINESS_TAX = bUSINESS_TAX;
	}

	public BigDecimal getAP_AMT() {
		return AP_AMT;
	}

	public void setAP_AMT(BigDecimal aP_AMT) {
		this.AP_AMT = aP_AMT;
	}

	public String getDOMAIN_NAME() {
		return DOMAIN_NAME;
	}

	public void setDOMAIN_NAME(String dOMAIN_NAME) {
		this.DOMAIN_NAME = dOMAIN_NAME == null ? null : dOMAIN_NAME.trim();
	}

	public String getCO_NAME() {
		return CO_NAME;
	}

	public void setCO_NAME(String cO_NAME) {
		this.CO_NAME = cO_NAME == null ? null : cO_NAME.trim();
	}

	public String getAPP_USER_NAME() {
		return APP_USER_NAME;
	}

	public void setAPP_USER_NAME(String aPP_USER_NAME) {
		this.APP_USER_NAME = aPP_USER_NAME == null ? null : aPP_USER_NAME
				.trim();
	}

	public String getSTATUS_DSCR() {
		return STATUS_DSCR;
	}

	public void setSTATUS_DSCR(String sTATUS_DSCR) {
		this.STATUS_DSCR = sTATUS_DSCR == null ? null : sTATUS_DSCR.trim();
	}

	public String getPO_NAME() {
		return PO_NAME;
	}

	public void setPO_NAME(String pO_NAME) {
		this.PO_NAME = pO_NAME == null ? null : pO_NAME.trim();
	}

	public String getCERTIFICATE_FLAG() {
		return CERTIFICATE_FLAG;
	}

	public void setCERTIFICATE_FLAG(String cERTIFICATE_FLAG) {
		this.CERTIFICATE_FLAG = cERTIFICATE_FLAG == null ? null
				: cERTIFICATE_FLAG.trim();
	}
}
