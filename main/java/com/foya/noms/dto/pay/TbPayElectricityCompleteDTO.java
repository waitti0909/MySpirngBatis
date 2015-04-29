package com.foya.noms.dto.pay;

import com.foya.dao.mybatis.model.TbPayElectricity;
import com.foya.noms.enums.PayEnumCommon;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TbPayElectricityCompleteDTO extends TbPayElectricity {

	// -------------------------------------------------------------------
	// ※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※
	// private Type [table][column][action]
	/********************************************************************
	 * TB_SITE_LINE_APPLY [專線申請] : lineApply<BR>
	 *********************************************************************/
	// ※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※
	// -------------------------------------------------------------------
	/**
	 * 
	 */
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 
	 */
	private NumberFormat nf = new DecimalFormat("###,###,###");
	// -------------------------------------------------------------------

	/**
	 * lineApply.LINE_PURPOSE
	 */
	private String lineApplyLinePurpose;

	/**
	 * lineApply.START_DATE
	 */
	private Date lineApplyStartDate;

	/**
	 * lineApply.END_DATE
	 */
	private Date lineApplyEndDate;

	/**
	 * lineApply.MON_FEE
	 */
	private String lineApplyMonFee;

	/**
	 * 接線費 (By Excel)
	 */
	private String wage;

	/***********************************************************************************/
	/**
	 * 取得專線用途
	 */
	public String getLinePurposeName() {

		return PayEnumCommon.CircuiTuses.toSource(lineApplyLinePurpose).getValue();
	}

	/**
	 * 取的生效日
	 * 
	 * @return
	 */
	public String getLineApplyStartDateFormat() {

		if (getLineApplyStartDate() == null) {
			return "";
		}

		return sdf.format(getLineApplyStartDate());
	}

	/**
	 * 取的失效日
	 * 
	 * @return
	 */
	public String getLineApplyEndDateFormat() {

		if (getLineApplyEndDate() == null) {
			return "";
		}

		return sdf.format(getLineApplyEndDate());
	}

	/**
	 * 取得請款期間起始
	 */
	public String getPaymentReqBeginDateFormat() {

		if (super.getPAYMENT_REQ_BEGIN_DATE() == null) {
			return "";
		}

		return sdf.format(super.getPAYMENT_REQ_BEGIN_DATE());
	}

	/**
	 * 取得請款期間起訖
	 */
	public String getPaymentReqEndDateFormat() {

		if (super.getPAYMENT_REQ_END_DATE() == null) {
			return "";
		}

		return sdf.format(super.getPAYMENT_REQ_END_DATE());
	}

	/***********************************************************************************/

	public String getLineApplyMonFee() {
		return lineApplyMonFee;
	}

	public void setLineApplyMonFee(String lineApplyMonFee) {
		this.lineApplyMonFee = lineApplyMonFee;
	}

	public String getLineApplyLinePurpose() {
		return lineApplyLinePurpose;
	}

	public void setLineApplyLinePurpose(String lineApplyLinePurpose) {
		this.lineApplyLinePurpose = lineApplyLinePurpose;
	}

	public Date getLineApplyStartDate() {
		return lineApplyStartDate;
	}

	public void setLineApplyStartDate(Date lineApplyStartDate) {
		this.lineApplyStartDate = lineApplyStartDate;
	}

	public Date getLineApplyEndDate() {
		return lineApplyEndDate;
	}

	public void setLineApplyEndDate(Date lineApplyEndDate) {
		this.lineApplyEndDate = lineApplyEndDate;
	}

	public String getWage() {
		return wage;
	}

	public void setWage(String wage) {
		this.wage = wage;
	}

	public void setPaymentReqEndDateFormat(String str) {
	}

	public void setPaymentReqBeginDateFormat(String str) {
	}

	public void setLineApplyEndDateFormat(String str) {
	}

	public void setLinePurposeName(String str) {
	}

	public void setLineApplyStartDateFormat(String str) {
	}

}
