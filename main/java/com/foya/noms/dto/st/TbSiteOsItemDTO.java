package com.foya.noms.dto.st;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.foya.dao.mybatis.model.TbSiteOsItem;
import com.foya.noms.util.DateTimeJsonSerializer;

public class TbSiteOsItemDTO extends TbSiteOsItem {

	private String MST_ORDER_ID;
	private BigDecimal REAL_AP_NUMBER;
	private BigDecimal PRICE;
	private String ITEM_NAME;
	private Integer MGR_FEE;
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

	public String getMST_ORDER_ID() {
		return MST_ORDER_ID;
	}

	public void setMST_ORDER_ID(String mST_ORDER_ID) {
		this.MST_ORDER_ID = mST_ORDER_ID == null ? null : mST_ORDER_ID.trim();
	}

	public BigDecimal getREAL_AP_NUMBER() {
		return REAL_AP_NUMBER;
	}

	public void setREAL_AP_NUMBER(BigDecimal rEAL_AP_NUMBER) {
		this.REAL_AP_NUMBER = rEAL_AP_NUMBER;
	}

	public BigDecimal getPRICE() {
		return PRICE;
	}

	public void setPRICE(BigDecimal pRICE) {
		this.PRICE = pRICE;
	}

	public String getITEM_NAME() {
		return ITEM_NAME;
	}

	public void setITEM_NAME(String iTEM_NAME) {
		this.ITEM_NAME = iTEM_NAME == null ? null : iTEM_NAME.trim();
	}

	public Integer getMGR_FEE() {
		return MGR_FEE;
	}

	public void setMGR_FEE(Integer mGR_FEE) {
		this.MGR_FEE = mGR_FEE;
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
