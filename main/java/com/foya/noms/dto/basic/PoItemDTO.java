package com.foya.noms.dto.basic;

import java.math.BigDecimal;

import com.foya.dao.mybatis.model.TbComPoItem;

public class PoItemDTO extends TbComPoItem {

	private String siteUsedTimes;
	private BigDecimal MGR_FEE;
	private String MAIN_ITEM;
    private String SUB_ITEM;
    private String ITEM_NO;
    private BigDecimal NUMBER;
	private BigDecimal AP_NUMBER;
	private BigDecimal AP_AMOUNT;
	private BigDecimal AMOUNT;
	private String mainItemName;
	private String subItemName;
    private String itemIdName;
	public String getSiteUsedTimes() {
		return siteUsedTimes;
	}

	public void setSiteUsedTimes(String siteUesdTimes) {
		this.siteUsedTimes = siteUesdTimes;
	}

	public BigDecimal getMGR_FEE() {
		return MGR_FEE;
	}

	public void setMGR_FEE(BigDecimal mGR_FEE) {
		MGR_FEE = mGR_FEE;
	}

	public String getMAIN_ITEM() {
		return MAIN_ITEM;
	}

	public void setMAIN_ITEM(String mAIN_ITEM) {
		MAIN_ITEM = mAIN_ITEM;
	}

	public String getSUB_ITEM() {
		return SUB_ITEM;
	}

	public void setSUB_ITEM(String sUB_ITEM) {
		SUB_ITEM = sUB_ITEM;
	}

	public String getITEM_NO() {
		return ITEM_NO;
	}

	public void setITEM_NO(String iTEM_NO) {
		ITEM_NO = iTEM_NO;
	}

	public BigDecimal getNUMBER() {
		return NUMBER;
	}

	public void setNUMBER(BigDecimal nUMBER) {
		NUMBER = nUMBER;
	}

	public BigDecimal getAP_NUMBER() {
		return AP_NUMBER;
	}

	public void setAP_NUMBER(BigDecimal aP_NUMBER) {
		AP_NUMBER = aP_NUMBER;
	}

	public BigDecimal getAP_AMOUNT() {
		return AP_AMOUNT;
	}

	public void setAP_AMOUNT(BigDecimal aP_AMOUNT) {
		AP_AMOUNT = aP_AMOUNT;
	}

	public BigDecimal getAMOUNT() {
		return AMOUNT;
	}

	public void setAMOUNT(BigDecimal aMOUNT) {
		AMOUNT = aMOUNT;
	}

	public String getMainItemName() {
		return mainItemName;
	}

	public void setMainItemName(String mainItemName) {
		this.mainItemName = mainItemName;
	}

	public String getSubItemName() {
		return subItemName;
	}

	public void setSubItemName(String subItemName) {
		this.subItemName = subItemName;
	}

	public String getItemIdName() {
		return itemIdName;
	}

	public void setItemIdName(String itemIdName) {
		this.itemIdName = itemIdName;
	}
	
	
}
