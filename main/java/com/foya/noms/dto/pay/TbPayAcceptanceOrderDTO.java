package com.foya.noms.dto.pay;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.foya.dao.mybatis.model.TbPayAcceptanceOrder;
import com.foya.noms.util.DateTimeJsonSerializer;

public class TbPayAcceptanceOrderDTO extends TbPayAcceptanceOrder {

	private String SITE_NAME;
	private Date AP_DATE;
	private String ORDER_NAME;
	private BigDecimal TAX_EXCLUSIVE_AMT;
	private BigDecimal BUSINESS_TAX;
	private BigDecimal AP_AMT;

	@JsonSerialize(using = DateTimeJsonSerializer.class)
	public Date getCR_TIME() {
		return super.getCR_TIME();
	}

	@JsonSerialize(using = DateTimeJsonSerializer.class)
	public Date getMD_TIME() {
		return super.getMD_TIME();
	}

	public String getSITE_NAME() {
		return SITE_NAME;
	}

	public void setSITE_NAME(String sITE_NAME) {
		this.SITE_NAME = sITE_NAME == null ? null : sITE_NAME.trim();
	}

	@JsonSerialize(using = DateTimeJsonSerializer.class)
	public Date getAP_DATE() {
		return AP_DATE;
	}

	public void setAP_DATE(Date aP_DATE) {
		this.AP_DATE = aP_DATE;
	}

	public String getORDER_NAME() {
		return ORDER_NAME;
	}

	public void setORDER_NAME(String oRDER_NAME) {
		this.ORDER_NAME = oRDER_NAME == null ? null : oRDER_NAME.trim();
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
}
