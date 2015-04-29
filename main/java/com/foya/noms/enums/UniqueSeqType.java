package com.foya.noms.enums;


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
 * <td>2014/10/22</td>
 * <td>取號類型</td>
 * <td>Charlie Woo</td>
 * </tr>
 * </table> 
 *
 * @author Charlie Woo
 *
 */
public enum UniqueSeqType {

	LOCATION_ID("站點編碼"),
	NODEB_4G("4G基站編碼"),
	SITE_ID("基站編碼"),
	WORK_ID("作業編碼"),
	ORDER_ID("工單編碼"),
	OS_ID("委外工單編碼"),
	SR_ID_SEQ("探勘序號編碼"),
	SEARCH_RING_ID("SearchRingID"),
	MT_OPT_ID("物料操作編碼"),
	LINE_APP_ID("專線申請編碼"),
	NONST_LINE_APP_ID("非工務專線申請編碼"),
	MHC_SITE_ID("機房編碼"),
	COMPANY_ID("COMPANY_ID"),//廠商ID
	ITEMMAIN_ID("ITEMMAIN_ID"),//工項ID
	LEASENO("LEASENO"),//租約號碼
	LEASEAAPPLYNO("LEASEAPPLYNO"),//租約申請號碼	
	ITEMCAT_ID("ITEMCAT_ID");//工項CategoryID
	
	private final String localName;
	
	private UniqueSeqType(String localName) {
		this.localName = localName;
	}
	
	public String getLocalName() {
		return localName;
	}
}
