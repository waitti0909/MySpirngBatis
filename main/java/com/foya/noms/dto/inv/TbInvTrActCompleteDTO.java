/**
 * 日期：2014/11/25
 * 功能說明：調撥紀錄檔DTO
 * 程式人員：Arvin
 */
package com.foya.noms.dto.inv;

import com.foya.dao.mybatis.model.TbInvTrAct;
import com.foya.noms.enums.InvEnumCommon;

public class TbInvTrActCompleteDTO extends TbInvTrAct {

	// -------------------------------------------------------------------
	// ※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※
	// private Type [table][column][action]
	/********************************************************************
	 * TB_INV_SRL [序號檔] : invSrl <BR>
	 * TB_INV_TR_DTL [調撥申請單] : trDtl <BR>
	 *********************************************************************/
	// ※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※
	// -------------------------------------------------------------------

	/**
	 * invSrl.VEN_SN
	 */
	private String venSn;

	/**
	 * invSrl.TAG_NO
	 */
	private String tagNo;

	/**
	 * invSrl.FA_NO
	 */
	private String faNo;

	/**
	 * trDtl.APP_QTY
	 */
	private Integer appQty;

	/**
	 * 
	 */
	private Integer dtlTrQty;

	/**
	 * trDtl.MAT_STATUS
	 */
	private Integer matStatus;

	/**
	 * 
	 */
	private String matName;

	/**
	 * 
	 */
	private Integer exportNumber = 0;

	/********************************************************************/
	/**
	 * 物料狀態
	 */
	public String getMatStatusStr() {

		return getMatStatus() != null ? InvEnumCommon.ProductStatus.toSource(getMatStatus()).getValue() : "";
	}

	/********************************************************************/
	public String getVenSn() {
		return venSn;
	}

	public Integer getAppQty() {
		return appQty;
	}

	public void setAppQty(Integer appQty) {
		this.appQty = appQty;
	}

	public Integer getDtlTrQty() {
		return dtlTrQty;
	}

	public void setDtlTrQty(Integer dtlTrQty) {
		this.dtlTrQty = dtlTrQty;
	}

	public Integer getMatStatus() {
		return matStatus;
	}

	public void setMatStatus(Integer matStatus) {
		this.matStatus = matStatus;
	}

	public String getMatName() {
		return matName;
	}

	public void setMatName(String matName) {
		this.matName = matName;
	}

	public void setVenSn(String venSn) {
		this.venSn = venSn;
	}

	public String getTagNo() {
		return tagNo;
	}

	public void setTagNo(String tagNo) {
		this.tagNo = tagNo;
	}

	public Integer getExportNumber() {
		return exportNumber;
	}

	public void setExportNumber(Integer exportNumber) {
		this.exportNumber = exportNumber;
	}

	public String getFaNo() {
		return faNo;
	}

	public void setFaNo(String faNo) {
		this.faNo = faNo;
	}

	public void setMatStatusStr(String status) {
	}

}
