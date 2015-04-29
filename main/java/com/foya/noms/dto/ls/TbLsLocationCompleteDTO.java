/**
 * @author Arvin.Tsai
 */
package com.foya.noms.dto.ls;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.foya.dao.mybatis.model.TbLsLocation;

public class TbLsLocationCompleteDTO extends TbLsLocation {

	// -------------------------------------------------------------------
	// ※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※
	// private Type [table][column][action]
	/********************************************************************
	 * TB_PAY_PREPAYMENT [預付主檔] : prepaid <BR>
	 * TB_PAY_LOCATION_INFO [LOCATION資料檔] : payLocationInfo <BR>
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
	 * SUM(G.prepayment_balance)<BR>
	 * 預付金額
	 */
	private Integer prepaidBalanceSum;

	/**
	 * payLocationInfo.PRE_PR_END_DATE
	 */
	private Date payLocationInfoEndDate;

	/************************************************************************************/

	/**
	 * 起帳日
	 */
	public String getPayBeginDateFormat() {

		return super.getPAY_BEGIN_DATE() == null ? "" : sdf.format(super.getPAY_BEGIN_DATE());
	}

	/**
	 * 終止日
	 */
	public String getEndDateFormat() {

		return super.getLS_E_DATE() == null ? "" : sdf.format(super.getLS_E_DATE());
	}

	/**
	 * 租金
	 */
	public String getRentAmtFormat() {

		return super.getRENT_AMT() == null ? "" : nf.format(super.getRENT_AMT());
	}

	/**
	 * 預付金額
	 */
	public String getPrepaidBalanceSumFormat() {

		return getPrepaidBalanceSum() == null ? "" : nf.format(getPrepaidBalanceSum());
	}

	/**
	 * 請款期間起始
	 */
	public String getPayLocationInfoEndDateFormat() {

		if (getPayLocationInfoEndDate() != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(getPayLocationInfoEndDate());
			calendar.add(Calendar.DAY_OF_MONTH, +1);

			return sdf.format(calendar.getTime());
		}

		return "";
	}

	/************************************************************************************/

	/************************************************************************************/
	public Integer getPrepaidBalanceSum() {
		return prepaidBalanceSum;
	}

	public void setPrepaidBalanceSum(Integer prepaidBalanceSum) {
		this.prepaidBalanceSum = prepaidBalanceSum;
	}

	public Date getPayLocationInfoEndDate() {
		return payLocationInfoEndDate;
	}

	public void setPayLocationInfoEndDate(Date payLocationInfoEndDate) {
		this.payLocationInfoEndDate = payLocationInfoEndDate;
	}

}
