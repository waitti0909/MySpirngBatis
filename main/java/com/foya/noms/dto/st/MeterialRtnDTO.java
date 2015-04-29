package com.foya.noms.dto.st;

public class MeterialRtnDTO{
	private String dtlSeq;
	private String assetNo;
	private String matNo;
	private String matName;
	private String venSn;
	private String tagNo;
	private Integer assetQty = 0;
	private Integer qty = 0;
	private String matStatus;
	private String trnReason;
	private String srlNo;
	private String faNo;
	private String ctrlType;
	
	private Integer  qtyMrq = 0;
	private Integer  qtyRem = 0;
	private Integer  qtyIns = 0;
	private Integer  qtyRtn = 0;
	
	private String osId;
	
	public String getAssetNo() {
		return assetNo;
	}
	public void setAssetNo(String assetNo) {
		this.assetNo = assetNo;
	}
	public String getMatNo() {
		return matNo;
	}
	public void setMatNo(String matNo) {
		this.matNo = matNo;
	}
	public String getMatName() {
		return matName;
	}
	public void setMatName(String matName) {
		this.matName = matName;
	}
	public String getVenSn() {
		return venSn;
	}
	public void setVenSn(String venSn) {
		this.venSn = venSn;
	}
	public String getTagNo() {
		return tagNo;
	}
	public void setTagNo(String tagNo) {
		this.tagNo = tagNo;
	}
	public Integer getAssetQty() {
		return assetQty;
	}
	public void setAssetQty(Integer assetQty) {
		this.assetQty = assetQty;
	}
	public Integer getQty() {
		return qty;
	}
	public void setQty(Integer qty) {
		this.qty = qty;
	}
	public String getMatStatus() {
		return matStatus;
	}
	public void setMatStatus(String matStatus) {
		this.matStatus = matStatus;
	}
	public String getTrnReason() {
		return trnReason;
	}
	public void setTrnReason(String trnReason) {
		this.trnReason = trnReason;
	}
	public String getSrlNo() {
		return srlNo;
	}
	public void setSrlNo(String srlNo) {
		this.srlNo = srlNo;
	}
	public String getFaNo() {
		return faNo;
	}
	public void setFaNo(String faNo) {
		this.faNo = faNo;
	}
	public String getCtrlType() {
		return ctrlType;
	}
	public void setCtrlType(String ctrlType) {
		this.ctrlType = ctrlType;
	}
	public String getDtlSeq() {
		return dtlSeq;
	}
	public void setDtlSeq(String dtlSeq) {
		this.dtlSeq = dtlSeq;
	}
	public Integer getQtyMrq() {
		return qtyMrq;
	}
	public void setQtyMrq(Integer qtyMrq) {
		this.qtyMrq = qtyMrq;
	}
	public Integer getQtyRem() {
		return qtyRem;
	}
	public void setQtyRem(Integer qtyRem) {
		this.qtyRem = qtyRem;
	}
	public Integer getQtyIns() {
		return qtyIns;
	}
	public void setQtyIns(Integer qtyIns) {
		this.qtyIns = qtyIns;
	}
	public Integer getQtyRtn() {
		return qtyRtn;
	}
	public void setQtyRtn(Integer qtyRtn) {
		this.qtyRtn = qtyRtn;
	}
	public String getOsId() {
		return osId;
	}
	public void setOsId(String osId) {
		this.osId = osId;
	}
	
}
