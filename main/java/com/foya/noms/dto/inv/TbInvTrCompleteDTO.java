/**
 * 日期：2014/11/14
 * 功能說明：調出作業與查詢DTO
 * 程式人員：Arvin
 */
package com.foya.noms.dto.inv;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.foya.dao.mybatis.model.TbInvTr;
import com.foya.dao.mybatis.model.TbInvWarehouse;
import com.foya.dao.mybatis.model.TbOrgDept;
import com.foya.noms.enums.InvEnumCommon;
import com.foya.noms.util.DateTimeJsonSerializer;

public class TbInvTrCompleteDTO extends TbInvTr {

	// -------------------------------------------------------------------
	// ※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※
	// private Type [table][column][action]
	/********************************************************************
	 *********************************************************************/
	// ※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※
	// -------------------------------------------------------------------

	/**
	 * 調出倉名稱
	 */
	private String outWhName;

	/**
	 * 調入倉名稱
	 */
	private String inWhName;

	/**
	 * 申請單位
	 */
	private String applyDeptName;

	/**
	 * 需求單位
	 */
	private String needDeptName;

	/**
	 * 申請人姓名
	 */
	private String appUserName;

	/****************************************************************/
	/**
	 * 取得調撥進度
	 */
	public String getStatusValue() {

		return super.getStatus() != null ? InvEnumCommon.Allocationstatus.toSource(super.getStatus()).getValue() : "";
	}

	/**
	 * 
	 */
	@JsonSerialize(using = DateTimeJsonSerializer.class)
	public Date getNeedDate() {

		return super.getNeed_date();
	}

	/**
	 * 取得日期
	 * 
	 * @return format Date
	 */
	public String getNeedDateToString() {

		return super.getNeed_date() != null ? new SimpleDateFormat("yyyy/MM/dd HH:mm").format(super.getNeed_date()) : "";
	}

	/****************************************************************/

	public String getOutWhName() {
		return outWhName;
	}

	public void setOutWhName(String outWhName) {
		this.outWhName = outWhName;
	}

	public String getInWhName() {
		return inWhName;
	}

	public void setInWhName(String inWhName) {
		this.inWhName = inWhName;
	}

	public String getApplyDeptName() {
		return applyDeptName;
	}

	public void setApplyDeptName(String applyDeptName) {
		this.applyDeptName = applyDeptName;
	}

	public String getAppUserName() {
		return appUserName;
	}

	public void setAppUserName(String appUserName) {
		this.appUserName = appUserName;
	}

	public String getNeedDeptName() {
		return needDeptName;
	}

	public void setNeedDeptName(String needDeptName) {
		this.needDeptName = needDeptName;
	}

	public void setNeedDate(String date) {
	}

	public void setStatusValue(String status) {
	}
	
	public void setNeedDateToString(String date) {
	}
	
	// /**
	// * 倉庫資料(調出)
	// */
	// private TbInvWarehouse invWarehouseByeExport;
	//
	// /**
	// * 倉庫資料(調入)
	// */
	// private TbInvWarehouse invWarehouseByeIn;
	//
	// /**
	// * 管理單位(需求部門)
	// */
	// private TbOrgDept tbOrgNeedDept;
	//
	// /**
	// * 管理單位(申請部門)
	// */
	// private TbOrgDept tbOrgApplyDept;
	//
	// /**
	// * 申請人
	// */
	// private String applyUserName;
	//
	// /*************************************************************************************/
	//
	// /**
	// * 取得調撥進度
	// */
	// public String getStatusValue() {
	//
	// return this.getStatus() != null ? InvEnumCommon.Allocationstatus
	// .toSource(this.getStatus()).getValue() : "";
	// }
	//
	// /**
	// * 取得日期
	// *
	// * @return format Date
	// */
	// public String getNeedDate() {
	//
	// return this.getNeed_date() != null ? new SimpleDateFormat(
	// "yyyy/MM/dd HH:mm").format(this.getNeed_date()) : "";
	// }
	//
	// /**
	// * 取得日期
	// *
	// * @return format Date
	// */
	// public String getNeedDateToString() {
	//
	// return super.getNeed_date() != null ? new SimpleDateFormat(
	// "yyyy/MM/dd HH:mm").format(super.getNeed_date()) : "";
	// }
	//
	// public TbInvWarehouse getInvWarehouseByeExport() {
	// return invWarehouseByeExport;
	// }
	//
	// public void setInvWarehouseByeExport(TbInvWarehouse
	// invWarehouseByeExport) {
	// this.invWarehouseByeExport = invWarehouseByeExport;
	// }
	//
	// public TbInvWarehouse getInvWarehouseByeIn() {
	// return invWarehouseByeIn;
	// }
	//
	// public void setInvWarehouseByeIn(TbInvWarehouse invWarehouseByeIn) {
	// this.invWarehouseByeIn = invWarehouseByeIn;
	// }
	//
	// public TbOrgDept getTbOrgNeedDept() {
	// return tbOrgNeedDept;
	// }
	//
	// public void setTbOrgNeedDept(TbOrgDept tbOrgNeedDept) {
	// this.tbOrgNeedDept = tbOrgNeedDept;
	// }
	//
	// public TbOrgDept getTbOrgApplyDept() {
	// return tbOrgApplyDept;
	// }
	//
	// public void setTbOrgApplyDept(TbOrgDept tbOrgApplyDept) {
	// this.tbOrgApplyDept = tbOrgApplyDept;
	// }
	//
	// public String getApplyUserName() {
	// return applyUserName;
	// }
	//
	// public void setApplyUserName(String applyUserName) {
	// this.applyUserName = applyUserName;
	// }

}
