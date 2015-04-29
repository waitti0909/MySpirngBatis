package com.foya.noms.dto.inv;

import java.math.BigDecimal;

import com.foya.dao.mybatis.model.TbInvMrD;

public class TbInvMrDDTO extends TbInvMrD{
	private BigDecimal txnQty;
	private String matName;
	private String txnMatNo;
	private String txnMatName;
	private BigDecimal bookingQty;
	private BigDecimal msQty;
	private String ctrlType;
	private String isAsset;
	private int diffQty;
	private int mrQty;
	private BigDecimal stdQty;
	public BigDecimal getTxnQty() {
		return txnQty;
	}

	public void setTxnQty(BigDecimal txnQty) {
		this.txnQty = txnQty;
	}

	public String getMatName() {
		return matName;
	}

	public void setMatName(String matName) {
		this.matName = matName;
	}

	public String getTxnMatNo() {
		return txnMatNo;
	}

	public void setTxnMatNo(String txnMatNo) {
		this.txnMatNo = txnMatNo;
	}

	public String getTxnMatName() {
		return txnMatName;
	}

	public void setTxnMatName(String txnMatName) {
		this.txnMatName = txnMatName;
	}

	public BigDecimal getBookingQty() {
		return bookingQty;
	}

	public void setBookingQty(BigDecimal bookingQty) {
		this.bookingQty = bookingQty;
	}

	public BigDecimal getMsQty() {
		return msQty;
	}

	public void setMsQty(BigDecimal msQty) {
		this.msQty = msQty;
	}

	public String getCtrlType() {
		return ctrlType;
	}

	public void setCtrlType(String ctrlType) {
		this.ctrlType = ctrlType;
	}

	public String getIsAsset() {
		return isAsset;
	}

	public void setIsAsset(String isAsset) {
		this.isAsset = isAsset;
	}

	public int getMrQty() {
		return mrQty;
	}

	public void setMrQty(int mrQty) {
		this.mrQty = mrQty;
	}

	public int getDiffQty() {
		return diffQty;
	}

	public void setDiffQty(int diffQty) {
		this.diffQty = diffQty;
	}

	public BigDecimal getStdQty() {
		return stdQty;
	}

	public void setStdQty(BigDecimal stdQty) {
		this.stdQty = stdQty;
	}
	
	
}
