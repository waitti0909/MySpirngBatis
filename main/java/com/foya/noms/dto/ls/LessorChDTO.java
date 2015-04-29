package com.foya.noms.dto.ls;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.foya.dao.mybatis.model.TbLsLessor;
import com.foya.dao.mybatis.model.TbPayLookupCode;
import com.foya.dao.mybatis.model.TbSysLookup;

public class LessorChDTO extends TbLsLessor {
	
	private List<TbLsLessor> lessors = new ArrayList<>();
	
	private List<TbSysLookup> paymentModeList= new ArrayList<>();

	private List<TbSysLookup> lessorTypeList= new ArrayList<>();
	
	private List<TbPayLookupCode> voucherTypeList= new ArrayList<>();
	
	private List<TbSysLookup> ownerRelationList=new ArrayList<>();
	
	
	private String selType;
	
	private String itemSEL;
	
	private String tbStyleSEL;
	
	private String rent_CHG_DATE;
	
	private String lessorToJson;
	
	private String deductYear;
	
	private String deductMon;
	
	private String lessorName;
	
	private String lessorPaymentMode;
	
	private String lsLeaseRelation;
	
	private String modifyLessorId;
	
	private String ORG_LESSOR_ID;
	
	public List<TbLsLessor> getLessors() {
		return lessors;
	}
	
	public void setLessors(List<TbLsLessor> lessors) {
		this.lessors = lessors;
	}

	public List<TbSysLookup> getPaymentModeList() {
		return paymentModeList;
	}

	public void setPaymentModeList(List<TbSysLookup> paymentModeList) {
		this.paymentModeList = paymentModeList;
	}

	public List<TbSysLookup> getLessorTypeList() {
		return lessorTypeList;
	}

	public void setLessorTypeList(List<TbSysLookup> lessorTypeList) {
		this.lessorTypeList = lessorTypeList;
	}

	public List<TbPayLookupCode> getVoucherTypeList() {
		return voucherTypeList;
	}

	public void setVoucherTypeList(List<TbPayLookupCode> voucherTypeList) {
		this.voucherTypeList = voucherTypeList;
	}

	public List<TbSysLookup> getOwnerRelationList() {
		return ownerRelationList;
	}

	public void setOwnerRelationList(List<TbSysLookup> ownerRelationList) {
		this.ownerRelationList = ownerRelationList;
	}

	public String getSelType() {
		return selType;
	}

	public void setSelType(String selType) {
		this.selType = selType;
	}

	public String getItemSEL() {
		return itemSEL;
	}

	public void setItemSEL(String itemSEL) {
		this.itemSEL = itemSEL;
	}

	public String getTbStyleSEL() {
		return tbStyleSEL;
	}

	public void setTbStyleSEL(String tbStyleSEL) {
		this.tbStyleSEL = tbStyleSEL;
	}

	public String getRent_CHG_DATE() {
		return rent_CHG_DATE;
	}

	public void setRent_CHG_DATE(String rent_CHG_DATE) {
		this.rent_CHG_DATE = rent_CHG_DATE;
	}

	public String getLessorToJson() {
		return lessorToJson;
	}

	public void setLessorToJson(String lessorToJson) {
		this.lessorToJson = lessorToJson;
	}

	public String getDeductYear() {
		return deductYear;
	}

	public void setDeductYear(String deductYear) {
		this.deductYear = deductYear;
	}

	public String getDeductMon() {
		return deductMon;
	}

	public void setDeductMon(String deductMon) {
		this.deductMon = deductMon;
	}

	public String getLessorName() {
		return lessorName;
	}

	public void setLessorName(String lessorName) {
		this.lessorName = lessorName;
	}

	public String getLessorPaymentMode() {
		return lessorPaymentMode;
	}

	public void setLessorPaymentMode(String lessorPaymentMode) {
		this.lessorPaymentMode = lessorPaymentMode;
	}

	public String getLsLeaseRelation() {
		return lsLeaseRelation;
	}

	public void setLsLeaseRelation(String lsLeaseRelation) {
		this.lsLeaseRelation = lsLeaseRelation;
	}

	public String getModifyLessorId() {
		return modifyLessorId;
	}

	public void setModifyLessorId(String modifyLessorId) {
		this.modifyLessorId = modifyLessorId;
	}

	public String getORG_LESSOR_ID() {
		return ORG_LESSOR_ID;
	}

	public void setORG_LESSOR_ID(String oRG_LESSOR_ID) {
		ORG_LESSOR_ID = oRG_LESSOR_ID;
	}
	
}
