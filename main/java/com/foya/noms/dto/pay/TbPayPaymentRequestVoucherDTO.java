package com.foya.noms.dto.pay;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.foya.dao.mybatis.model.TbPayPaymentRequestVoucher;
import com.foya.dao.mybatis.model.TbPayVoucherCredit;
import com.foya.noms.util.DateTimeJsonSerializer;

public class TbPayPaymentRequestVoucherDTO extends TbPayPaymentRequestVoucher {

	private BigDecimal AP_AMT;
	private String VOUCHER_TYPE_DESC;
	private BigDecimal voucherCnt;
	private BigDecimal sumTaxExclusiveAmt;
	private BigDecimal sumBusinessTax;
	private BigDecimal totalAmt;
	private BigDecimal sumCreditMadeAmt;
	private String editType;
	
	private String spstatus;
	private String mst_po_no;
	private String _id;
	private String voucherdate;
	private String taxexclusiveamt;
	
	@JsonDeserialize(as=ArrayList.class, contentAs=TbPayVoucherCredit.class)
	private List<TbPayVoucherCredit> tbPayVoucherCreditList;

	
	
	@JsonSerialize(using = DateTimeJsonSerializer.class)
	public Date getVOUCHER_DATE() {
		return super.getVOUCHER_DATE();
	}

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
		AP_AMT = aP_AMT;
	}

	public String getVOUCHER_TYPE_DESC() {
		return VOUCHER_TYPE_DESC;
	}

	public void setVOUCHER_TYPE_DESC(String vOUCHER_TYPE_DESC) {
		VOUCHER_TYPE_DESC = vOUCHER_TYPE_DESC == null ? null : vOUCHER_TYPE_DESC.trim();
	}

	public BigDecimal getSumTaxExclusiveAmt() {
		return sumTaxExclusiveAmt;
	}

	public void setSumTaxExclusiveAmt(BigDecimal sumTaxExclusiveAmt) {
		this.sumTaxExclusiveAmt = sumTaxExclusiveAmt;
	}

	public BigDecimal getSumBusinessTax() {
		return sumBusinessTax;
	}

	public void setSumBusinessTax(BigDecimal sumBusinessTax) {
		this.sumBusinessTax = sumBusinessTax;
	}

	public BigDecimal getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(BigDecimal totalAmt) {
		this.totalAmt = totalAmt;
	}

	public BigDecimal getSumCreditMadeAmt() {
		return sumCreditMadeAmt;
	}

	public void setSumCreditMadeAmt(BigDecimal sumCreditMadeAmt) {
		this.sumCreditMadeAmt = sumCreditMadeAmt;
	}

	public BigDecimal getVoucherCnt() {
		return voucherCnt;
	}

	public void setVoucherCnt(BigDecimal voucherCnt) {
		this.voucherCnt = voucherCnt;
	}

	public List<TbPayVoucherCredit> getTbPayVoucherCreditList() {
		return tbPayVoucherCreditList;
	}

	public void setTbPayVoucherCreditList(
			List<TbPayVoucherCredit> tbPayVoucherCreditList) {
		this.tbPayVoucherCreditList = tbPayVoucherCreditList;
	}

	public String getEditType() {
		return editType;
	}

	public void setEditType(String editType) {
		this.editType = editType;
	}

	
	
	public String getSpstatus() {
		return spstatus;
	}

	public void setSpstatus(String spstatus) {
		this.spstatus = spstatus;
	}

	public String getMst_po_no() {
		return mst_po_no;
	}

	public void setMst_po_no(String mst_po_no) {
		this.mst_po_no = mst_po_no;
	}



	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getVoucherdate() {
		return voucherdate;
	}

	public void setVoucherdate(String voucherdate) {
		this.voucherdate = voucherdate;
	}

	public String getTaxexclusiveamt() {
		return taxexclusiveamt;
	}

	public void setTaxexclusiveamt(String taxexclusiveamt) {
		this.taxexclusiveamt = taxexclusiveamt;
	}
	
	
}
