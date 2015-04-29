package com.foya.noms.dto.pay;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.foya.dao.mybatis.model.TbPayOutsourceAcceptanceDtl;
import com.foya.noms.util.DateTimeJsonSerializer;

public class TbPayOutsourceAcceptanceDtlDTO extends TbPayOutsourceAcceptanceDtl {

	private BigDecimal AP_AMT;
	private String ORDER_ITEM_DESC;
	private Date SUBPOENA_DATE;
	private Date VOUCHER_DATE;
    private String VOUCHER_NBR;

	@JsonSerialize(using = DateTimeJsonSerializer.class)
	public Date getCR_TIME() {
		return super.getCR_TIME();
	}

	@JsonSerialize(using = DateTimeJsonSerializer.class)
	public Date getMD_TIME() {
		return super.getMD_TIME();
	}

	public BigDecimal getAP_AMT() {
		return AP_AMT;
	}

	public void setAP_AMT(BigDecimal aP_AMT) {
		this.AP_AMT = aP_AMT;
	}

	public String getORDER_ITEM_DESC() {
		return ORDER_ITEM_DESC;
	}

	public void setORDER_ITEM_DESC(String oRDER_ITEM_DESC) {
		this.ORDER_ITEM_DESC = oRDER_ITEM_DESC == null ? null : oRDER_ITEM_DESC.trim();
	}

	@JsonSerialize(using = DateTimeJsonSerializer.class)
	public Date getSUBPOENA_DATE() {
		return SUBPOENA_DATE;
	}

	public void setSUBPOENA_DATE(Date sUBPOENA_DATE) {
		this.SUBPOENA_DATE = sUBPOENA_DATE;
	}

	@JsonSerialize(using = DateTimeJsonSerializer.class)
	public Date getVOUCHER_DATE() {
		return VOUCHER_DATE;
	}

	public void setVOUCHER_DATE(Date vOUCHER_DATE) {
		this.VOUCHER_DATE = vOUCHER_DATE;
	}

	public String getVOUCHER_NBR() {
		return VOUCHER_NBR;
	}

	public void setVOUCHER_NBR(String vOUCHER_NBR) {
		this.VOUCHER_NBR = vOUCHER_NBR == null ? null : vOUCHER_NBR.trim();
	}
}
