package com.foya.noms.dto.inv;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.foya.dao.mybatis.model.TbInvRtD;
import com.foya.dao.mybatis.model.TbInvSrl;
import com.foya.dao.mybatis.model.TbInvTxn;
import com.foya.noms.enums.InvEnumCommon;
import com.foya.noms.util.DateTimeJsonSerializer;

public class TbInvTxnDTO extends TbInvTxn {

	private String txn_type_name;// 異動原因中文
	private String wh_name;// 異動倉庫名
	private String dept_name;// 異動部門中文
	private String site_name;// 異動站台名
	private String cr_name;// 異動人員名稱
	private String tagNo;// 貼標號碼
	private String venSn;// 廠商序號
	private String matName;// 品名
	private String appUserName;// 申請人
	private Date appTime;// 退料申請日
	private String callBackFun;// 回傳ＦＯＲＭ使用
	private String matStatusDscr;// 物料狀態中文
	private String rtnReasonDscr;
	private BigDecimal rtnQty;
	private String crUser;
	private int mrQty;
	private String crTime;
	private String btsSiteId;
	
	private List<TbInvTxnDTO> invTxnDtoList;// for 詳細歷程

	private TbInvSrl invSrl; // 標籤資料

	private TbInvRtD invRtD;

	/******************************************************************/
	/**
	 * 取得建立時間 ( yyyy/MM/dd HH:mm )
	 * 
	 * @return String
	 */
	public String getFromatCrTime() {
		
		if(super.getCr_time()==null){
			return "";
		}
		
		return new SimpleDateFormat("yyyy/MM/dd").format(super
				.getCr_time());
	}

	/**
	 * 取的退料狀態
	 * 
	 * @return String
	 */
	public String getReturnMatStatus() {

		if (invRtD == null || invRtD.getMAT_STATUS() == null ||invRtD.getMAT_STATUS().equals("null")) {
			return "";
		}
		return InvEnumCommon.ProductStatus.toSource(
				Integer.parseInt(invRtD.getMAT_STATUS())).getValue();
	}
	
	/**
	 * 取的收料狀態
	 * 
	 * @return String
	 */
	public String getCallInMatStatus() {

		if (super.getMat_status() == null) {
			return "";
		}
		return InvEnumCommon.ProductStatus.toSource(super.getMat_status()).getValue();
	}

	/**
	 * 取的退料狀態原因
	 * 
	 * @return String
	 */
	public String getReturnMatReason() {

		if (invRtD == null || invRtD.getRTN_REASON() == null) {
			return "";
		}
		return InvEnumCommon.faultReason.toSource(invRtD.getRTN_REASON()).getValue();
	}
	
	/**
	 * 取的收料狀態原因
	 * 
	 * @return String
	 */
	public String getCallInMatReason() {

		if (super.getMat_status() == null) {
			return "";
		}
		return InvEnumCommon.faultReason.toSource(String.valueOf(super.getRn_resn())).getValue();
	}

	public String getWh_name() {
		return wh_name;
	}

	public void setWh_name(String wh_name) {
		this.wh_name = wh_name;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getSite_name() {
		return site_name;
	}

	public void setSite_name(String site_name) {
		this.site_name = site_name;
	}

	public String getCr_name() {
		return cr_name;
	}

	public void setCr_name(String cr_name) {
		this.cr_name = cr_name;
	}

	public String getTxn_type_name() {
		return txn_type_name;
	}

	public void setTxn_type_name(String txn_type_name) {
		this.txn_type_name = txn_type_name;
	}

	@JsonSerialize(using = DateTimeJsonSerializer.class)
	public Date getCr_time() {
		return super.getCr_time();
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

	public String getMatName() {
		return matName;
	}

	public void setMatName(String matName) {
		this.matName = matName;
	}

	public String getAppUserName() {
		return appUserName;
	}

	public void setAppUserName(String appUserName) {
		this.appUserName = appUserName;
	}

	public Date getAppTime() {
		return appTime;
	}

	public void setAppTime(Date appTime) {
		this.appTime = appTime;
	}

	public String getCallBackFun() {
		return callBackFun;
	}

	public void setCallBackFun(String callBackFun) {
		this.callBackFun = callBackFun;
	}

	public String getMatStatusDscr() {
		return matStatusDscr;
	}

	public void setMatStatusDscr(String matStatusDscr) {
		this.matStatusDscr = matStatusDscr;
	}

	public String getRtnReasonDscr() {
		return rtnReasonDscr;
	}

	public void setRtnReasonDscr(String rtnReasonDscr) {
		this.rtnReasonDscr = rtnReasonDscr;
	}

	public BigDecimal getRtnQty() {
		return rtnQty;
	}

	public void setRtnQty(BigDecimal rtnQty) {
		this.rtnQty = rtnQty;
	}

	public List<TbInvTxnDTO> getInvTxnDtoList() {
		return invTxnDtoList;
	}

	public void setInvTxnDtoList(List<TbInvTxnDTO> invTxnDtoList) {
		this.invTxnDtoList = invTxnDtoList;
	}

	public TbInvSrl getInvSrl() {
		return invSrl;
	}

	public void setInvSrl(TbInvSrl invSrl) {
		this.invSrl = invSrl;
	}

	public TbInvRtD getInvRtD() {
		return invRtD;
	}

	public void setInvRtD(TbInvRtD invRtD) {
		this.invRtD = invRtD;
	}

	public String getCrUser() {
		return crUser;
	}

	public void setCrUser(String crUser) {
		this.crUser = crUser;
	}

	public int getMrQty() {
		return mrQty;
	}

	public void setMrQty(int mrQty) {
		this.mrQty = mrQty;
	}

	public String getCrTime() {
		return crTime;
	}

	public void setCrTime(String crTime) {
		this.crTime = crTime;
	}

	public String getBtsSiteId() {
		return btsSiteId;
	}

	public void setBtsSiteId(String btsSiteId) {
		this.btsSiteId = btsSiteId;
	}
	
}
