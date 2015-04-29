package com.foya.noms.dto.st;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.foya.dao.mybatis.model.TbSiteOutsourcing;
import com.foya.noms.util.DateTimeJsonSerializer;

public class TbSiteOutsourcingDTO extends TbSiteOutsourcing {

	private String PO_NO;
	private String ORDER_DESC;
	private String SITE_ID;
	private String SITE_NAME;
	private String DOMAIN;
	private String CO_NAME;
	private BigDecimal TAX_EXCLUSIVE_AMT;
	private BigDecimal BUSINESS_TAX;
	private BigDecimal AP_AMT;
	private String REP_TEAM_NAME;

	@JsonSerialize(using = DateTimeJsonSerializer.class)
	public Date getOS_TIME() {
		return super.getOS_TIME();
	}

	@JsonSerialize(using = DateTimeJsonSerializer.class)
	public Date getAP_DATE() {
		return super.getAP_DATE();
	}

	@JsonSerialize(using = DateTimeJsonSerializer.class)
	public Date getCR_TIME() {
		return super.getCR_TIME();
	}

	@JsonSerialize(using = DateTimeJsonSerializer.class)
	public Date getMD_TIME() {
		return super.getMD_TIME();
	}

	public String getPO_NO() {
		return PO_NO;
	}

	public void setPO_NO(String pO_NO) {
		this.PO_NO = pO_NO == null ? null : pO_NO.trim();
	}

	public String getORDER_DESC() {
		return ORDER_DESC;
	}

	public void setORDER_DESC(String oRDER_DESC) {
		this.ORDER_DESC = oRDER_DESC == null ? null : oRDER_DESC.trim();
	}

	public String getSITE_ID() {
		return SITE_ID;
	}

	public void setSITE_ID(String sITE_ID) {
		this.SITE_ID = sITE_ID == null ? null : sITE_ID.trim();
	}

	public String getSITE_NAME() {
		return SITE_NAME;
	}

	public void setSITE_NAME(String sITE_NAME) {
		this.SITE_NAME = sITE_NAME == null ? null : sITE_NAME.trim();
	}

	public String getDOMAIN() {
		return DOMAIN;
	}

	public void setDOMAIN(String dOMAIN) {
		this.DOMAIN = dOMAIN == null ? null : dOMAIN.trim();
	}

	public String getCO_NAME() {
		return CO_NAME;
	}

	public void setCO_NAME(String cO_NAME) {
		this.CO_NAME = cO_NAME == null ? null : cO_NAME.trim();
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

	public String getREP_TEAM_NAME() {
		return REP_TEAM_NAME;
	}

	public void setREP_TEAM_NAME(String rEP_TEAM_NAME) {
		REP_TEAM_NAME = rEP_TEAM_NAME;
	}
	
}
