package com.foya.noms.dto.inv;

import java.math.BigDecimal;

import com.foya.dao.mybatis.model.TbInvMaterialOpt;
import com.foya.dao.mybatis.model.TbInvRtD;

public class TbInvRtDDTO extends TbInvRtD{
	private String matName;
	private String tagNo;
	private String venSn;
	private String matStatusDscr;
	private BigDecimal txnQty;
	private String optId;
	private String rnResnDscr;
	private Double minusQty;
	private String whId;
	private String faNo;
	private TbInvMaterialOpt tbInvMaterialOpt;
	private int rtQty;
	public String getMatName() {
		return matName;
	}
	public void setMatName(String matName) {
		this.matName = matName;
	}
	public String getTagNo() {
		return tagNo;
	}
	public void setTagNo(String tagNo) {
		this.tagNo = tagNo;
	}
	public String getVenSn() {
		return venSn;
	}
	public void setVenSn(String venSn) {
		this.venSn = venSn;
	}
	public String getMatStatusDscr() {
		return matStatusDscr;
	}
	public void setMatStatusDscr(String matStatusDscr) {
		this.matStatusDscr = matStatusDscr;
	}
	public BigDecimal getTxnQty() {
		return txnQty;
	}
	public void setTxnQty(BigDecimal txnQty) {
		this.txnQty = txnQty;
	}
	public String getOptId() {
		return optId;
	}
	public void setOptId(String optId) {
		this.optId = optId;
	}
	public String getRnResnDscr() {
		return rnResnDscr;
	}
	public void setRnResnDscr(String rnResnDscr) {
		this.rnResnDscr = rnResnDscr;
	}
	public Double getMinusQty() {
		minusQty = 0.0;
			try{
				minusQty=Double.valueOf((this.getQTY()-this.getTxnQty().intValue()));
			}catch(Exception e){minusQty = 0.0;}
		return minusQty;
	}
	public void setMinusQty(Double minusQty) {
		this.minusQty = minusQty;
	}
	public String getWhId() {
		return whId;
	}
	public void setWhId(String whId) {
		this.whId = whId;
	}
	public String getFaNo() {
		return faNo;
	}
	public void setFaNo(String faNo) {
		this.faNo = faNo;
	}
	public TbInvMaterialOpt getTbInvMaterialOpt() {
		return tbInvMaterialOpt;
	}
	public void setTbInvMaterialOpt(TbInvMaterialOpt tbInvMaterialOpt) {
		this.tbInvMaterialOpt = tbInvMaterialOpt;
	}
	public int getRtQty() {
		return rtQty;
	}
	public void setRtQty(int rtQty) {
		this.rtQty = rtQty;
	}
	
}
