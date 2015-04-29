package com.foya.noms.dto.pay;

import java.math.BigDecimal;
import com.foya.dao.mybatis.model.TbLsSite;

public class TbLsSiteDTO extends TbLsSite {

	private String LOC_TYPE;
	private String LOC_TYPE_DSCR;
	private String SITE_NAME;
	private String EXCH_SITE_ID;
	private String ALOC_ITEM;
	private String ALOC_ITEM_DSCR;
	private BigDecimal ALOC_RATIO;
	private String DUMP_DATE;
	private BigDecimal REPARTITION_AMT;
	private String paymentReqBeginDate;
	private String paymentReqEndDate;
	private String ALOC_ID;
	
	private String pr_item_dscr;
	private BigDecimal s_TAX_EXCLUSIVE_AMT;
	
	private BigDecimal alcRatioE;
	private BigDecimal alcRatioR;
	
	public String getLOC_TYPE() {
		return LOC_TYPE;
	}
	public void setLOC_TYPE(String lOC_TYPE) {
		LOC_TYPE = lOC_TYPE;
	}
	public String getLOC_TYPE_DSCR() {
		return LOC_TYPE_DSCR;
	}
	public void setLOC_TYPE_DSCR(String lOC_TYPE_DSCR) {
		LOC_TYPE_DSCR = lOC_TYPE_DSCR;
	}
	public String getSITE_NAME() {
		return SITE_NAME;
	}
	public void setSITE_NAME(String sITE_NAME) {
		SITE_NAME = sITE_NAME;
	}
	public String getEXCH_SITE_ID() {
		return EXCH_SITE_ID;
	}
	public void setEXCH_SITE_ID(String eXCH_SITE_ID) {
		EXCH_SITE_ID = eXCH_SITE_ID;
	}
	public String getALOC_ITEM() {
		return ALOC_ITEM;
	}
	public void setALOC_ITEM(String aLOC_ITEM) {
		ALOC_ITEM = aLOC_ITEM;
	}
	public String getALOC_ITEM_DSCR() {
		return ALOC_ITEM_DSCR;
	}
	public void setALOC_ITEM_DSCR(String aLOC_ITEM_DSCR) {
		ALOC_ITEM_DSCR = aLOC_ITEM_DSCR;
	}
	public BigDecimal getALOC_RATIO() {
		return ALOC_RATIO;
	}
	public void setALOC_RATIO(BigDecimal aLOC_RATIO) {
		ALOC_RATIO = aLOC_RATIO;
	}
	public String getDUMP_DATE() {
		return DUMP_DATE;
	}
	public void setDUMP_DATE(String dUMP_DATE) {
		DUMP_DATE = dUMP_DATE;
	}
	public BigDecimal getREPARTITION_AMT() {
		return REPARTITION_AMT;
	}
	public void setREPARTITION_AMT(BigDecimal rEPARTITION_AMT) {
		REPARTITION_AMT = rEPARTITION_AMT;
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
	
	/**
	 * @return the pr_item_dscr
	 */
	public String getPr_item_dscr() {
		return pr_item_dscr;
	}
	/**
	 * @param pr_item_dscr the pr_item_dscr to set
	 */
	public void setPr_item_dscr(String pr_item_dscr) {
		this.pr_item_dscr = pr_item_dscr;
	}
	/**
	 * @return the s_TAX_EXCLUSIVE_AMT
	 */
	public BigDecimal getS_TAX_EXCLUSIVE_AMT() {
		return s_TAX_EXCLUSIVE_AMT;
	}
	/**
	 * @param s_TAX_EXCLUSIVE_AMT the s_TAX_EXCLUSIVE_AMT to set
	 */
	public void setS_TAX_EXCLUSIVE_AMT(BigDecimal s_TAX_EXCLUSIVE_AMT) {
		this.s_TAX_EXCLUSIVE_AMT = s_TAX_EXCLUSIVE_AMT;
	}
	public String getALOC_ID() {
		return ALOC_ID;
	}
	public void setALOC_ID(String aLOC_ID) {
		ALOC_ID = aLOC_ID;
	}
	public BigDecimal getAlcRatioE() {
		return alcRatioE;
	}
	public void setAlcRatioE(BigDecimal alcRatioE) {
		this.alcRatioE = alcRatioE;
	}
	public BigDecimal getAlcRatioR() {
		return alcRatioR;
	}
	public void setAlcRatioR(BigDecimal alcRatioR) {
		this.alcRatioR = alcRatioR;
	}
			
}
