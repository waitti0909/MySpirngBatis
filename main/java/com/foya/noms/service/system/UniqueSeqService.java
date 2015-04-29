package com.foya.noms.service.system;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foya.noms.dao.system.UniqueSeqDao;
import com.foya.noms.enums.SiteType;
import com.foya.noms.enums.UniqueSeqType;
import com.foya.noms.service.BaseService;

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
 * <td>2015/4/7</td>
 * <td>Common Generating Sequence Service</td>
 * <td>Charlie Woo</td>
 * </tr>
 * </table> 
 *
 * @author Charlie Woo
 *
 */
@Service
public class UniqueSeqService extends BaseService {
	//合約編號前置馬
	private static final String LEASE_PREFIX = "R";
	
	@Autowired
	private UniqueSeqDao dao;

	/**
	 * 站點編號格式：站點分類碼(1)+郵遞區號(3)+西元年(4)+流水號(4) B：基站 R: Repeater/Booster A：行動車
	 * M：傳輸中繼或是同業機房 H：公司Hub機房 C：公司核心網路機房
	 * 
	 * @return
	 */
	public String getNextLocationId(String locType, String zipCode) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		zipCode = zipCode.substring(0,3);
		Integer serialNumber = dao.getNextSequence(UniqueSeqType.LOCATION_ID, locType + zipCode + year);
		return locType + zipCode + year + String.format("%04d", serialNumber);
	}

	/**
	 * 編號格式：L + ZIP(3) + 流水號(3)
	 * 
	 * @return
	 */
	public String getNextNodeB4G(String zipCode) {
		Integer serialNumber = dao.getNextSequence(UniqueSeqType.NODEB_4G, "L" + zipCode);
		return "L" + zipCode + String.format("%03d", serialNumber);
	}

	/**
	 * 基站編號格式：西元年(4) +月(2) +日(2) +流水號(4)
	 * 
	 * @return
	 */
	public String getNextSiteId() {
		Calendar calendar = Calendar.getInstance();
		String year = String.valueOf(calendar.get(Calendar.YEAR));
		String month = String.format("%02d", calendar.get(Calendar.MONTH) + 1);
		String day = String.format("%02d", calendar.get(Calendar.DATE));
		Integer serialNumber = dao.getNextSequence(UniqueSeqType.SITE_ID, year + month + day);
		return year + month + day + String.format("%04d", serialNumber);
	}

	/**
	 * 作業編號格式：西元年(4) +月(2) +日(2) +流水號(4)
	 * 
	 * @return
	 */
	public String getNextWorkId() {
		Calendar calendar = Calendar.getInstance();
		String year = String.valueOf(calendar.get(Calendar.YEAR));
		String month = String.format("%02d", calendar.get(Calendar.MONTH) + 1);
		String day = String.format("%02d", calendar.get(Calendar.DATE));
		Integer serialNumber = dao.getNextSequence(UniqueSeqType.WORK_ID, year + month + day);
		return year + month + day + String.format("%04d", serialNumber);
	}

	/**
	 * 工單編號格式：作業編號(12)+流水號(1)
	 * 
	 * @return
	 */
	public String getNextOrderId(String workId) {
		Integer serialNumber = dao.getNextSequence(UniqueSeqType.ORDER_ID, workId);
		return workId + serialNumber;
	}

	/**
	 * 委外工單編號格式：工單編號(13)+流水號(2)
	 * 
	 * @return
	 */
	public String getNextOsId(String orderId) {
		Integer serialNumber = dao.getNextSequence(UniqueSeqType.OS_ID, orderId);
		return orderId + String.format("%02d", serialNumber);
	}

	/**
	 * 探勘編號格式：流水號(2)
	 * 
	 * @param srId
	 * @return
	 */
	public String getNextSrId(String srId) {
		Integer serialNumber = dao.getNextSequence(UniqueSeqType.SR_ID_SEQ, srId);
		return String.format("%02d", serialNumber);
	}

	/**
	 * Search Ring 編號格式：郵遞區號的第一碼(1)+縣市英文的兩字母縮寫(2)+流水號(5)
	 * 
	 * @param firstZip
	 * @param county
	 * @return
	 */
	public String getNextSearchRingId(char firstZip, String county) {
		Integer serialNumber = dao.getNextSequence(UniqueSeqType.SEARCH_RING_ID, firstZip + county);
		return firstZip + county + String.format("%05d", serialNumber);
	}

	/**
	 * 領料/退料/安裝/拆下操作單號：工單編號(13)+流水號(2)
	 * 
	 * @param orderId
	 * @return
	 */
	public String getNextMaterialOptId(String orderId) {
		Integer serialNumber = dao.getNextSequence(UniqueSeqType.MT_OPT_ID, orderId);
		return orderId + String.format("%02d", serialNumber);
	}

	/**
	 * 專線申請單號：工單編號(13) + 流水號(1)
	 * @param orderId 
	 * 20140802000112
	 * @return
	 */
	public String getNextLineAppId(String orderId) {
		Integer serialNumber = dao.getNextSequence(UniqueSeqType.LINE_APP_ID, orderId);
		return orderId + String.format("%01d", serialNumber);
	}
	
	/**
	 * 非工務專線申請單號：西元年(4) +月(2) +日(2) + NST + 流水號(3)
	 * 20140802NST001
	 * @return
	 */
	public String getNextNonStLineAppId() {
		Calendar calendar = Calendar.getInstance();
		String year = String.valueOf(calendar.get(Calendar.YEAR));
		String month = String.format("%02d", calendar.get(Calendar.MONTH) + 1);
		String day = String.format("%02d", calendar.get(Calendar.DATE));
		Integer serialNumber = dao.getNextSequence(UniqueSeqType.NONST_LINE_APP_ID, year + month + day);
		return year + month + day + "NST" + String.format("%03d", serialNumber);
	}
	
	/**
	 * 機房站台編碼：M/H/C + 流水號(4)
	 * @param siteType
	 * @return
	 */
	public String getNextMHCSiteId(SiteType siteType) {
		Integer serialNumber = dao.getNextSequence(UniqueSeqType.MHC_SITE_ID, siteType.name());
		return siteType.name() + String.format("%04d", serialNumber);
	}
	
	
	
	/**
	 * 廠商編號
	 * 廠商編號格式：西元年(4) +流水號(5)
	 * @return
	 */
	public String getNextCompanyId() {
		Calendar calendar = Calendar.getInstance();
		String year = String.valueOf(calendar.get(Calendar.YEAR));
		Integer serialNumber = dao.getNextSequence(UniqueSeqType.COMPANY_ID, year);
		return year+String.format("%05d",serialNumber);
	}
	
	/**
	 * ITEMMAIN 工項ID
	 * @return
	 */
	public String getNextItemMainId() {
		Integer serialNumber = dao.getNextSequence(UniqueSeqType.ITEMMAIN_ID, "ITEMMAIN_ID");
		return String.valueOf(serialNumber);
	}
	
	/**
	 * ITEMCAT ITEM_CATEGORY_ID
	 * @return
	 */
	public String getNextItemCatId() {
		Integer serialNumber = dao.getNextSequence(UniqueSeqType.ITEMCAT_ID, "ITEMCAT_ID");
		return String.valueOf(serialNumber);
	}
	
	
	
	
	/**
	 * 合約編號：年月”YYMM”＋三碼流水號”NNN”
	 * @return
	 */
	public String getNextLeaseNo(String domain) {
		Calendar calendar = Calendar.getInstance();
		String year = String.valueOf(calendar.get(Calendar.YEAR)).substring(2);
		String month = String.format("%02d", calendar.get(Calendar.MONTH) + 1);
		Integer serialNumber = dao.getNextSequence(UniqueSeqType.LEASENO, year + month);
		return LEASE_PREFIX+domain+year + month  + String.format("%03d", serialNumber);
	}
	
	/**
	 * 合約申請號碼：年月”YYMM”＋三碼流水號”NNN”
	 * @return
	 */
	public String getNextLeaseAppNo(String leaseNo) {
		
		Integer serialNumber = dao.getNextSequence(UniqueSeqType.LEASEAAPPLYNO, leaseNo);
		return leaseNo+ String.format("%03d", serialNumber);
	}
	
	
	
	
}
