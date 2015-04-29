package com.foya.noms.dto.pay;

import java.math.BigDecimal;
import java.util.Date;

import com.foya.dao.mybatis.model.TbPayCheckDisregard;

public class TbPayCheckDisregardDTO extends TbPayCheckDisregard {
	private int disregardCount;
	private BigDecimal sumCheckAmt;
	private String approveUser;
	private String approveUserName;
	private Date approveDate;
	private String appUserName;
	private String paymentReqUserId;
	private String paymentReqUserName;
	private String paymentUserId;
	private String paymentUserName;
	private Date checkCashDate;
	private String checkReasonDscr;
	private String reasonCode;
	private String statusDscr;
	public int getDisregardCount() {
		return disregardCount;
	}
	public void setDisregardCount(int disregardCount) {
		this.disregardCount = disregardCount;
	}
	public BigDecimal getSumCheckAmt() {
		return sumCheckAmt;
	}
	public void setSumCheckAmt(BigDecimal sumCheckAmt) {
		this.sumCheckAmt = sumCheckAmt;
	}
	public String getApproveUser() {
		return approveUser;
	}
	public void setApproveUser(String approveUser) {
		this.approveUser = approveUser;
	}
	public Date getApproveDate() {
		return approveDate;
	}
	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}
	public String getApproveUserName() {
		return approveUserName;
	}
	public void setApproveUserName(String approveUserName) {
		this.approveUserName = approveUserName;
	}
	public String getAppUserName() {
		return appUserName;
	}
	public void setAppUserName(String appUserName) {
		this.appUserName = appUserName;
	}
	public String getPaymentReqUserId() {
		return paymentReqUserId;
	}
	public void setPaymentReqUserId(String paymentReqUserId) {
		this.paymentReqUserId = paymentReqUserId;
	}
	public String getPaymentReqUserName() {
		return paymentReqUserName;
	}
	public void setPaymentReqUserName(String paymentReqUserName) {
		this.paymentReqUserName = paymentReqUserName;
	}
	public Date getCheckCashDate() {
		return checkCashDate;
	}
	public void setCheckCashDate(Date checkCashDate) {
		this.checkCashDate = checkCashDate;
	}
	public String getCheckReasonDscr() {
		return checkReasonDscr;
	}
	public void setCheckReasonDscr(String checkReasonDscr) {
		this.checkReasonDscr = checkReasonDscr;
	}
	public String getReasonCode() {
		return reasonCode;
	}
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}
	public String getPaymentUserId() {
		return paymentUserId;
	}
	public void setPaymentUserId(String paymentUserId) {
		this.paymentUserId = paymentUserId;
	}
	public String getPaymentUserName() {
		return paymentUserName;
	}
	public void setPaymentUserName(String paymentUserName) {
		this.paymentUserName = paymentUserName;
	}
	public String getStatusDscr() {
		return statusDscr;
	}
	public void setStatusDscr(String statusDscr) {
		this.statusDscr = statusDscr;
	}
	

}
