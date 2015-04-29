package com.foya.noms.dto.inv;

import com.foya.dao.mybatis.model.TbInvTrDtl;

public class TbInvTrDtlDTO extends TbInvTrDtl{
	private String matName;
	private Integer trInCnt;
	private Integer trOutCnt;
	private Integer stockQty;
	private Integer bookingQty;
	private String checkResult;
	private String checkStatus;
	private String matStatusName;
	private String ctrlType;
	private int page;
	private int total;
	private int record;
	
	public String getMatName() {
		return matName;
	}
	public void setMatName(String matName) {
		this.matName = matName;
	}
	public Integer getTrInCnt() {
		return trInCnt;
	}
	public void setTrInCnt(Integer trInCnt) {
		this.trInCnt = trInCnt;
	}
	public Integer getTrOutCnt() {
		return trOutCnt;
	}
	public void setTrOutCnt(Integer trOutCnt) {
		this.trOutCnt = trOutCnt;
	}
	public Integer getStockQty() {
		return stockQty;
	}
	public void setStockQty(Integer stockQty) {
		this.stockQty = stockQty;
	}
	public String getCheckResult() {
		return checkResult;
	}
	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}
	public String getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
	public Integer getBookingQty() {
		return bookingQty;
	}
	public void setBookingQty(Integer bookingQty) {
		this.bookingQty = bookingQty;
	}
	public String getMatStatusName() {
		return matStatusName;
	}
	public void setMatStatusName(String matStatusName) {
		this.matStatusName = matStatusName;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getRecord() {
		return record;
	}
	public void setRecord(int record) {
		this.record = record;
	}
	public String getCtrlType() {
		return ctrlType;
	}
	public void setCtrlType(String ctrlType) {
		this.ctrlType = ctrlType;
	}
	
	
}
