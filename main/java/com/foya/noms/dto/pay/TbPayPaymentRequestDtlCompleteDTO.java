package com.foya.noms.dto.pay;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.foya.dao.mybatis.model.TbPayPaymentRequestDtl;

public class TbPayPaymentRequestDtlCompleteDTO extends TbPayPaymentRequestDtl {

	// -------------------------------------------------------------------
	// ※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※
	// private Type [table][column][action]
	/********************************************************************
	 * TB_LS_LOCATION [合約站點檔] : location<BR>
	 *********************************************************************/
	// ※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※
	// -------------------------------------------------------------------
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	// -------------------------------------------------------------------

	/**
	 * location.PAY_BEGIN_DATE <BR>
	 * 起帳日
	 */
	private Date payBeginDate;

	/**
	 * location.LS_E_DATE <BR>
	 * 終止日
	 */
	private Date lsEndDate;

	/**
	 * location.RENT_AMT <BR>
	 * 租金
	 */
	private Integer rentAmt;

	/**
	 * 預付餘額
	 */
	private Integer prepaidBalance;

	/**
	 * 類別名稱
	 */
	private String itemName;

	/*************************************************************************************/

	public String getPayBeginDate() {
		return dateFormat.format(payBeginDate);
	}

	public void setPayBeginDate(Date payBeginDate) {
		this.payBeginDate = payBeginDate;
	}

	public String getLsEndDate() {
		return dateFormat.format(lsEndDate);
	}

	public void setLsEndDate(Date lsEndDate) {
		this.lsEndDate = lsEndDate;
	}

	public Integer getRentAmt() {
		return rentAmt;
	}

	public void setRentAmt(Integer rentAmt) {
		this.rentAmt = rentAmt;
	}

	public Integer getPrepaidBalance() {
		return prepaidBalance;
	}

	public void setPrepaidBalance(Integer prepaidBalance) {
		this.prepaidBalance = prepaidBalance;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getPaymentReqBeginDate() {
		return dateFormat.format(super.getPAYMENT_REQ_BEGIN_DATE());
	}

	public String getPaymentReqEndDate() {
		return dateFormat.format(super.getPAYMENT_REQ_END_DATE());
	}
}
