package com.foya.noms.dto.inv;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.foya.dao.mybatis.model.TbInvMaterialOpt;
import com.foya.noms.enums.MaterialOptType;
import com.foya.noms.enums.WorkType;
import com.foya.noms.util.DateJsonSerializer;

public class TbInvMaterialOptDTO extends TbInvMaterialOpt{
	private String whIdDscr;
	private String deptIdDscr;
	private String siteIdDscr;
	private String app_userName;
	private String mat_no;
	private String mat_name;
	private Integer qty;
	private Integer lack_qty;
	private String statusDscr;
	
	private String SITE_NAME;
	private String REP_DEP;
	private String DEPT_NAME;
	private String STATUS_NAME;
	private String WH_NAME;
	private String REP_DEPT;
	private String EQP_NAME;
	private String OP_TYPE;
	private String CO_NAME;
	private String BTS_SITE_ID;
	private String EQP_MODEL;
	private String ADDR;
	private String MAT_NO;
	private Long SRL_NO;
	private Integer TRO_QTY;
	private String MAT_NAME;
	private String VEN_SN;
	private String TAG_NO;
	private String CHN_NM;
	
	private Integer storage_qty;
	
	public String getWhIdDscr() {
		return whIdDscr;
	}
	
	public void setWhIdDscr(String whIdDscr) {
		this.whIdDscr = whIdDscr;
	}
	
	public String getDeptIdDscr() {
		return deptIdDscr;
	}
	
	public void setDeptIdDscr(String deptIdDscr) {
		this.deptIdDscr = deptIdDscr;
	}
	
	public String getSiteIdDscr() {
		return siteIdDscr;
	}
	
	public void setSiteIdDscr(String siteIdDscr) {
		this.siteIdDscr = siteIdDscr;
	}
	
	public String getApp_userName() {
		return app_userName;
	}
	
	public void setApp_userName(String app_userName) {
		this.app_userName = app_userName;
	}
	
	public String getMat_no() {
		return mat_no;
	}
	
	public void setMat_no(String mat_no) {
		this.mat_no = mat_no;
	}
	
	public String getMat_name() {
		return mat_name;
	}
	
	public void setMat_name(String mat_name) {
		this.mat_name = mat_name;
	}
	
	public Integer getQty() {
		return qty;
	}
	
	public void setQty(Integer qty) {
		this.qty = qty;
	}
	
	public Integer getLack_qty() {
		return lack_qty;
	}
	
	public void setLack_qty(Integer lack_qty) {
		this.lack_qty = lack_qty;
	}
	
	public String getStatusDscr() {
		return statusDscr;
	}
	
	public void setStatusDscr(String statusDscr) {
		this.statusDscr = statusDscr;
	}
	
	public String getSITE_NAME() {
		return SITE_NAME;
	}
	
	public void setSITE_NAME(String sITE_NAME) {
		SITE_NAME = sITE_NAME;
	}
	
	public String getREP_DEP() {
		return REP_DEP;
	}
	
	public void setREP_DEP(String rEP_DEP) {
		REP_DEP = rEP_DEP;
	}
	
	public String getDEPT_NAME() {
		return DEPT_NAME;
	}
	
	public void setDEPT_NAME(String dEPT_NAME) {
		DEPT_NAME = dEPT_NAME;
	}

	public String getSTATUS_NAME() {
		return STATUS_NAME;
	}

	public void setSTATUS_NAME(String sTATUS_NAME) {
		STATUS_NAME = sTATUS_NAME;
	}

	public String getWH_NAME() {
		return WH_NAME;
	}

	public void setWH_NAME(String wH_NAME) {
		WH_NAME = wH_NAME;
	}
	
	public String getREP_DEPT() {
		return REP_DEPT;
	}

	public void setREP_DEPT(String rEP_DEPT) {
		REP_DEPT = rEP_DEPT;
	}

	public String getEQP_NAME() {
		return EQP_NAME;
	}

	public void setEQP_NAME(String eQP_NAME) {
		EQP_NAME = eQP_NAME;
	}


	public String getOP_TYPE() {
		return OP_TYPE;
	}

	public void setOP_TYPE(String oP_TYPE) {
		OP_TYPE = oP_TYPE;
	}
	
	public String getCO_NAME() {
		return CO_NAME;
	}

	public void setCO_NAME(String cO_NAME) {
		CO_NAME = cO_NAME;
	}
	
	public String getBTS_SITE_ID() {
		return BTS_SITE_ID;
	}

	public void setBTS_SITE_ID(String bTS_SITE_ID) {
		BTS_SITE_ID = bTS_SITE_ID;
	}

	public String getEQP_MODEL() {
		return EQP_MODEL;
	}

	public void setEQP_MODEL(String eQP_MODEL) {
		EQP_MODEL = eQP_MODEL;
	}

	public String getWORK_TYPE_NAME(){
		return WorkType.detectLabel(getWORK_TYPE());
	}
	
	public String getOPT_TYPE_NAME(){
		return  MaterialOptType.detectLabel(getOPT_TYPE());
	}

	public String getADDR() {
		return ADDR;
	}

	public void setADDR(String aDDR) {
		ADDR = aDDR;
	}

	public String getMAT_NO() {
		return MAT_NO;
	}

	public void setMAT_NO(String mAT_NO) {
		MAT_NO = mAT_NO;
	}

	public Long getSRL_NO() {
		return SRL_NO;
	}

	public void setSRL_NO(Long sRL_NO) {
		SRL_NO = sRL_NO;
	}

	public Integer getTRO_QTY() {
		return TRO_QTY;
	}

	public void setTRO_QTY(Integer tRO_QTY) {
		TRO_QTY = tRO_QTY;
	}

	public String getMAT_NAME() {
		return MAT_NAME;
	}

	public void setMAT_NAME(String mAT_NAME) {
		MAT_NAME = mAT_NAME;
	}

	public String getVEN_SN() {
		return VEN_SN;
	}

	public void setVEN_SN(String vEN_SN) {
		VEN_SN = vEN_SN;
	}

	public String getTAG_NO() {
		return TAG_NO;
	}

	public void setTAG_NO(String tAG_NO) {
		TAG_NO = tAG_NO;
	}

	public String getCHN_NM() {
		return CHN_NM;
	}

	public void setCHN_NM(String cHN_NM) {
		CHN_NM = cHN_NM;
	}
	public Integer getStorage_qty() {
		return storage_qty;
	}
	
	public void setStorage_qty(Integer storage_qty) {
		this.storage_qty = storage_qty;
	}
	
	@JsonSerialize(using=DateJsonSerializer.class)
	@Override
	public Date getCR_TIME(){
		return super.getCR_TIME();
	}
	
}
