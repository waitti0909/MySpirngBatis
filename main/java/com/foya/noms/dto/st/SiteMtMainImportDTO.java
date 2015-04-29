package com.foya.noms.dto.st;

import java.util.List;

/**
 * 
 * 
 * <table>
 * <tr>
 * <th>版本</th>
 * <th>日期</th>
 * <th>詳細說明</th>
 * <th>modifier</th>
 * </tr>
 * <tr>
 * <td>1.0</td>
 * <td>2014/12/25</td>
 * <td>領料單匯入主檔</td>
 * <td>Charlie Woo</td>
 * </tr>
 * </table> 
 *
 * @author Charlie Woo
 *
 */
public class SiteMtMainImportDTO {
	
	private String optId;	// 領料單號

	private String osId;	// (委外)派工單號
	
	private String osVenderName;	// 委外廠商
	
	private String applyDept;	// 負責單位
	
	private String workType;	// 工務類型
	
	private String btsSiteId;	// 站台ID
	
	private String inventory;	// 領料倉庫
	
	private String eqpType;		// 設備類型
	
	private String eqpModel;	// 設備機型
	
	private List<SiteMtDetailImportDTO> details;	// 領料明細檔

	public String getOptId() {
		return optId;
	}

	public void setOptId(String optId) {
		this.optId = optId;
	}

	public String getOsId() {
		return osId;
	}

	public void setOsId(String osId) {
		this.osId = osId;
	}

	public String getOsVenderName() {
		return osVenderName;
	}

	public void setOsVenderName(String osVenderName) {
		this.osVenderName = osVenderName;
	}

	public String getApplyDept() {
		return applyDept;
	}

	public void setApplyDept(String applyDept) {
		this.applyDept = applyDept;
	}

	public String getWorkType() {
		return workType;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}

	public String getBtsSiteId() {
		return btsSiteId;
	}

	public void setBtsSiteId(String btsSiteId) {
		this.btsSiteId = btsSiteId;
	}

	public String getInventory() {
		return inventory;
	}

	public void setInventory(String inventory) {
		this.inventory = inventory;
	}

	public String getEqpType() {
		return eqpType;
	}

	public void setEqpType(String eqpType) {
		this.eqpType = eqpType;
	}

	public String getEqpModel() {
		return eqpModel;
	}

	public void setEqpModel(String eqpModel) {
		this.eqpModel = eqpModel;
	}

	public List<SiteMtDetailImportDTO> getDetails() {
		return details;
	}

	public void setDetails(List<SiteMtDetailImportDTO> details) {
		this.details = details;
	}
}
