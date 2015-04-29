package com.foya.noms.dto.ls;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.foya.dao.mybatis.model.TbLsElecRange;
import com.foya.dao.mybatis.model.TbLsLocElec;
import com.foya.dao.mybatis.model.TbLsSite;
import com.foya.noms.dto.pay.TbLsLocElecDTO;
import com.foya.noms.dto.pay.TbLsSiteDTO;

public class LeaseLocElecDTO {

	private List<TbLsLocElecDTO> lsLocElecList = new ArrayList<>();
	private List<TbLsSiteDTO> tbLsSiteDTOList = new ArrayList<>();
	private List<TbLsLocPaymentDTO> tbLsLocPaymentList = new ArrayList<>();
	private List<TbLsElecRange> tbLsElecRanges = new ArrayList<>();
	
	private String APP_SEQ;
	
	private String lessorNames;

	private String allLessorPaymentMode;
	
	private String lsLeaseRelation;
	
	private String LS_NO;

	private String LS_VER;

	private String LOCATION_ID;
	
	private Date LS_S_DATE;
	private Date LS_E_DATE;
	
	private String LS_ADD;
	private String LS_ADD_STD;
	
	
	
	public List<TbLsLocElecDTO> getLsLocElecList() {
		return lsLocElecList;
	}
	public void setLsLocElecList(List<TbLsLocElecDTO> lsLocElecList) {
		this.lsLocElecList = lsLocElecList;
	}

	
	public String getLessorNames() {
		return lessorNames;
	}
	public void setLessorNames(String lessorNames) {
		this.lessorNames = lessorNames;
	}
	public String getAllLessorPaymentMode() {
		return allLessorPaymentMode;
	}
	public void setAllLessorPaymentMode(String allLessorPaymentMode) {
		this.allLessorPaymentMode = allLessorPaymentMode;
	}
	public String getLsLeaseRelation() {
		return lsLeaseRelation;
	}
	public void setLsLeaseRelation(String lsLeaseRelation) {
		this.lsLeaseRelation = lsLeaseRelation;
	}
	public List<TbLsSiteDTO> getTbLsSiteDTOList() {
		return tbLsSiteDTOList;
	}
	public void setTbLsSiteDTOList(List<TbLsSiteDTO> tbLsSiteDTOList) {
		this.tbLsSiteDTOList = tbLsSiteDTOList;
	}
	public List<TbLsLocPaymentDTO> getTbLsLocPaymentList() {
		return tbLsLocPaymentList;
	}
	public void setTbLsLocPaymentList(List<TbLsLocPaymentDTO> tbLsLocPaymentList) {
		this.tbLsLocPaymentList = tbLsLocPaymentList;
	}
	public String getLS_NO() {
		return LS_NO;
	}
	public void setLS_NO(String lS_NO) {
		LS_NO = lS_NO;
	}
	public String getLS_VER() {
		return LS_VER;
	}
	public void setLS_VER(String lS_VER) {
		LS_VER = lS_VER;
	}
	public String getLOCATION_ID() {
		return LOCATION_ID;
	}
	public void setLOCATION_ID(String lOCATION_ID) {
		LOCATION_ID = lOCATION_ID;
	}
	public List<TbLsElecRange> getTbLsElecRanges() {
		return tbLsElecRanges;
	}
	public void setTbLsElecRanges(List<TbLsElecRange> tbLsElecRanges) {
		this.tbLsElecRanges = tbLsElecRanges;
	}
	public Date getLS_S_DATE() {
		return LS_S_DATE;
	}
	public void setLS_S_DATE(Date lS_S_DATE) {
		LS_S_DATE = lS_S_DATE;
	}
	public Date getLS_E_DATE() {
		return LS_E_DATE;
	}
	public void setLS_E_DATE(Date lS_E_DATE) {
		LS_E_DATE = lS_E_DATE;
	}
	public String getLS_ADD() {
		return LS_ADD;
	}
	public void setLS_ADD(String lS_ADD) {
		LS_ADD = lS_ADD;
	}
	public String getLS_ADD_STD() {
		return LS_ADD_STD;
	}
	public void setLS_ADD_STD(String lS_ADD_STD) {
		LS_ADD_STD = lS_ADD_STD;
	}
	public String getAPP_SEQ() {
		return APP_SEQ;
	}
	public void setAPP_SEQ(String aPP_SEQ) {
		APP_SEQ = aPP_SEQ;
	}
	
	
	
	
	

}
