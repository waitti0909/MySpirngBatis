package com.foya.noms.dto.inv;

import java.util.ArrayList;
import java.util.Map;

import com.foya.dao.mybatis.model.TbInvSrl;
import com.foya.dao.mybatis.model.TbInvTrDtl;
import com.foya.noms.enums.InvEnumCommon;

public class TbInvTrDtlCompleteDTO extends TbInvTrDtl {

	/**
	 * 
	 */
	private Integer trQty;

	/**
	 * 
	 */
	private Integer bookingQty;

	/**
	 * 
	 */
	private String matName;

	/**
	 * 
	 */
	private String ctrlType;

//	/**
//	 * 廠商序號
//	 */
//	private ArrayList<TbInvSrl> venSnList = new ArrayList<TbInvSrl>();

	/**
	 * 廠商序號
	 */
	private ArrayList<String> venSnByStrList = new ArrayList<String>();

	/**
	 * 已選廠商序號
	 */
	private ArrayList<String> selectVenSnStrList = new ArrayList<String>();

	/**
	 * 調出數
	 */
	private Integer exportNumber = 0;

	/********************************************************************/
	/**
	 * 物料狀態
	 */
	public String getMatStatus() {

		return super.getMat_status() != null ? InvEnumCommon.ProductStatus.toSource(super.getMat_status()).getValue() : "";
	}

	/********************************************************************/

	public Integer getTrQty() {
		return trQty == null ? 0 : trQty;
	}

	public void setTrQty(Integer trQty) {
		this.trQty = trQty;
	}

	public Integer getBookingQty() {
		return bookingQty;
	}

	public void setBookingQty(Integer bookingQty) {
		this.bookingQty = bookingQty;
	}

	public String getMatName() {
		return matName;
	}

	public void setMatName(String matName) {
		this.matName = matName;
	}

//	public ArrayList<TbInvSrl> getVenSnList() {
//		return venSnList;
//	}
//
//	public void setVenSnList(ArrayList<TbInvSrl> venSnList) {
//		this.venSnList = venSnList;
//	}

	public ArrayList<String> getSelectVenSnStrList() {
		return selectVenSnStrList;
	}

	public void setSelectVenSnStrList(ArrayList<String> selectVenSnStrList) {
		this.selectVenSnStrList = selectVenSnStrList;
	}

	public Integer getExportNumber() {
		return exportNumber;
	}

	public void setExportNumber(Integer exportNumber) {
		this.exportNumber = exportNumber;
	}

	public void setMatStatus(String status) {
	}

	public String getCtrlType() {
		return ctrlType;
	}

	public void setCtrlType(String ctrlType) {
		this.ctrlType = ctrlType;
	}

	public ArrayList<String> getVenSnByStrList() {
		return venSnByStrList;
	}

	public void setVenSnByStrList(ArrayList<String> venSnByStrList) {
		this.venSnByStrList = venSnByStrList;
	}

}
