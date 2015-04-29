package com.foya.noms.dto.st;

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
 * <td>領料單匯入明細</td>
 * <td>Charlie Woo</td>
 * </tr>
 * </table> 
 *
 * @author Charlie Woo
 *
 */
public class SiteMtDetailImportDTO {

	private String itemNo;	// 料號
	
	private String itemCatMain;		// 大項
	
	private String itemCatSub;		// 中項
	
	private String itemDetail;		// 小項
	
	private String matName;    //品名
	
	private String unit;		// 單位
	
	private String ctrlType;	// 序號件/非序號件類別
	
	private Integer qty = 0;	// 數量
	
	private Integer qtySum = 0;	// 總數量
	
	private Integer actQty = 0;	// 發料數量
	
	private String whId; //倉庫
	
    private String vsn;		// 廠商序號
	
	private String tag;		// 貼標號碼
	
    private String actNo;		// 實發料號
	
	private String actName;		// 實發品名

	private Integer totalQty = 0; //倉庫庫存數
	
	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public String getItemCatMain() {
		return itemCatMain;
	}

	public void setItemCatMain(String itemCatMain) {
		this.itemCatMain = itemCatMain;
	}

	public String getItemCatSub() {
		return itemCatSub;
	}

	public void setItemCatSub(String itemCatSub) {
		this.itemCatSub = itemCatSub;
	}

	public String getItemDetail() {
		return itemDetail;
	}

	public void setItemDetail(String itemDetail) {
		this.itemDetail = itemDetail;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getCtrlType() {
		return ctrlType;
	}

	public void setCtrlType(String ctrlType) {
		this.ctrlType = ctrlType;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public Integer getActQty() {
		return actQty;
	}

	public void setActQty(Integer actQty) {
		this.actQty = actQty;
	}

	public String getWhId() {
		return whId;
	}

	public void setWhId(String whId) {
		this.whId = whId;
	}

	public String getVsn() {
		return vsn;
	}

	public void setVsn(String vsn) {
		this.vsn = vsn;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getActNo() {
		return actNo;
	}

	public void setActNo(String actNo) {
		this.actNo = actNo;
	}

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}

	public Integer getTotalQty() {
		return totalQty;
	}

	public void setTotalQty(Integer totalQty) {
		this.totalQty = totalQty;
	}

	public String getMatName() {
		return matName;
	}

	public void setMatName(String matName) {
		this.matName = matName;
	}

	public Integer getQtySum() {
		return qtySum;
	}

	public void setQtySum(Integer qtySum) {
		this.qtySum = qtySum;
	}
	
}
