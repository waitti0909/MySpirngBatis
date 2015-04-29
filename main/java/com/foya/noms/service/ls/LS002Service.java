package com.foya.noms.service.ls;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hornetq.utils.json.JSONArray;
import org.hornetq.utils.json.JSONException;
import org.hornetq.utils.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foya.dao.mybatis.model.TbLsLessor;
import com.foya.dao.mybatis.model.TbLsLessorExample;
import com.foya.dao.mybatis.model.TbLsLocation;
import com.foya.dao.mybatis.model.TbLsLocationAdded;
import com.foya.dao.mybatis.model.TbLsLocationAddedExample;
import com.foya.dao.mybatis.model.TbLsLocationExample;
import com.foya.dao.mybatis.model.TbLsMain;
import com.foya.dao.mybatis.model.TbLsMainKey;
import com.foya.dao.mybatis.model.TbOrgDept;
import com.foya.dao.mybatis.model.TbSiteLocation;
import com.foya.noms.dao.ls.LS002Dao;
import com.foya.noms.dto.ls.LeaseDTO;
import com.foya.noms.enums.SiteType;
import com.foya.noms.service.BaseService;
import com.foya.noms.service.org.ORG002Service;
import com.foya.noms.util.DateUtils;

@Service
public class LS002Service extends BaseService {

	@Autowired
	private LS002Dao lS002Dao;
	@Autowired
	private ORG002Service oRG002Service;

	/**
	 * 依合約編號模糊查詢
	 * 
	 * @param lsNo
	 *       	合約編號
	 * @param type
	 * 			類型	
	 * @return
	 */
	public List<LeaseDTO> searchByLeaseNo(String lsNo,String type) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.equals("1", type)){
			String[] appStatus = {"1"};
			String[] lsType = {"5"};
			map.put("appStatus", appStatus);
			map.put("lsType", lsType); // 合約型態
			map.put("addItem", "租金起算日");
		}
		map.put("lsNo", lsNo); // 合約編號
		return lS002Dao.searchByLeaseNo(map);
	}

	/**
	 * 查詢LS002M UI顯示共用的值
	 * 
	 * @param leaseNo
	 *            合約編號
	 * @param modelMap
	 *            返回UI所承接的map
	 */
	public void searchByCommonLs002M(String leaseNo, String lsVer, Map<String, Object> modelMap) {
		TbLsMainKey key = new TbLsMainKey();
		key.setLS_NO(leaseNo);
		key.setLS_VER(lsVer);
		TbLsMain tbLsMain = lS002Dao.searchTbLsMainByLsNo(key);
		if (tbLsMain != null) {
			TbOrgDept tbOrgDept = oRG002Service.getByPk(tbLsMain.getAPP_USER_DEPT());
			modelMap.put("lsNo", tbLsMain.getLS_NO());
			modelMap.put("lsName", tbLsMain.getLS_NAME());// DateUtils.format(tbLsMain.getLS_S_DATE(), "yyyy/MM/dd")
			modelMap.put("S_Date", tbLsMain.getLS_S_DATE() == null ? "" : DateUtils.formatDateUS(tbLsMain.getLS_S_DATE(), "yyyy/MM/dd"));
			modelMap.put("E_Date", tbLsMain.getLS_E_DATE() == null ? "" : DateUtils.formatDateUS(tbLsMain.getLS_E_DATE(), "yyyy/MM/dd"));
			tbOrgDept = oRG002Service.getByPk(tbLsMain.getKEEP_DEPT());
			modelMap.put("deptPlace", tbOrgDept.getDEPT_NAME() + "/" + (tbOrgDept.getDOMAIN().indexOf("N") >= 0 ? "北一合約室" : tbOrgDept.getDOMAIN().indexOf("M") >= 0 ? "北二合約室" : tbOrgDept.getDOMAIN().indexOf("C") >= 0 ? "中區合約室" : tbOrgDept.getDOMAIN().indexOf("S") >= 0 ? "南區合約室" : ""));
		}
	}

	/**
	 * 查詢LS002M 房屋稅籍編號異動UI需要使用的值
	 * 
	 * @param flag
	 *            房屋稅籍編號異動需要使用的值
	 * @param leaseNo
	 *            合約編號
	 * @param modelMap
	 *            返回UI所承接的map
	 */
	public void searchByHouseLs002M(boolean flag, String leaseNo, Map<String, Object> modelMap) {
		TbLsLessorExample example = new TbLsLessorExample();
		example.createCriteria().andLS_NOEqualTo(leaseNo);
		List<TbLsLessor> tbLsLessorList = lS002Dao.searchTbLsLessorByLsNo(example);
		if (tbLsLessorList.size() > 0) {
			String name = "";
			JSONArray arr = new JSONArray();
			JSONObject tmp;
			for (int i = 0; i < tbLsLessorList.size(); i++) {
				if (name.length() > 0) {
					name += ",";
				}
				name += tbLsLessorList.get(i).getLESSOR_NAME();
				if (flag) {
					tmp = new JSONObject();
					try {
						tmp.put("LS_NO", tbLsLessorList.get(i).getLS_NO());
						tmp.put("LS_VER", tbLsLessorList.get(i).getLS_VER());
						tmp.put("LESSOR_ID", tbLsLessorList.get(i).getLESSOR_ID());
						tmp.put("LESSOR_NAME", tbLsLessorList.get(i).getLESSOR_NAME());
						tmp.put("HOUSE_TAX_NO", tbLsLessorList.get(i).getHOUSE_TAX_NO());
						arr.put(tmp);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}

			}
			if (flag) {
				modelMap.put("json", arr.toString());
			}
			modelMap.put("lessorName", name);
		}
	}

	/**
	 * 查詢LS002M 租金起算日異動UI需要使用的值
	 * 
	 * @param appSeq
	 *            申請流水號
	 * @param lsNo
	 *            合約編號
	 * @param modelMap
	 *            返回UI所承接的map
	 */
	public void searchByRentLs002M(String appSeq,String lsNo, Map<String, Object> modelMap) {
		TbLsLocationAddedExample example = new TbLsLocationAddedExample();
		example.createCriteria().andLS_NOEqualTo(lsNo).andAPP_SEQEqualTo(appSeq).andADDED_STATEEqualTo("AFTER");
		List<TbLsLocationAdded> tbLsLocationAddedList = lS002Dao.searchTbLsLocationAdded(example);
		JSONArray arr = new JSONArray();
		JSONObject tmp;
		if (tbLsLocationAddedList.size() > 0) {
			try {
				for (TbLsLocationAdded list : tbLsLocationAddedList) {
					tmp = new JSONObject();
					tmp.put("LS_NO", list.getLS_NO());
					tmp.put("LS_VER", list.getLS_VER());
					tmp.put("LOCATION_ID", list.getLOCATION_ID());// 站點編號
					tmp.put("PAY_BEGIN_DATE", list.getPAY_BEGIN_DATE() == null ? "" : DateUtils.formatDateUS(list.getPAY_BEGIN_DATE(), "yyyy/MM/dd"));// 租金起算日
					TbSiteLocation tbSiteLocation = lS002Dao.searchTbSiteLocationByLsNo(list.getLOCATION_ID());
					if (tbSiteLocation != null) {
						tmp.put("LOC_NAME", tbSiteLocation.getLOC_NAME() == null ? "" : tbSiteLocation.getLOC_NAME());// 站點名稱
						tmp.put("LOC_TYPE", SiteType.detectLabel(tbSiteLocation.getLOC_TYPE()));// 站點類別
					}
					arr.put(tmp);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			modelMap.put("json", arr.toString());
		}
	}

	/**
	 * 更新 TB_LS_LESSOR 的 房屋稅籍編號
	 * 
	 * @param lsNo
	 *            合約編號
	 * @param lsVer
	 *            版次
	 * @param lessorId
	 *            出租人證號
	 * @param newTaxNo
	 *            房屋稅籍編號
	 */
	@Transactional
	public void saveUpdateHouseTaxNo(String lsNo, String lsVer, String lessorId, String newTaxNo) {
		try {
			lS002Dao.updateHouseTaxNo(lsNo, lsVer, lessorId, newTaxNo, getLoginUser().getUsername());
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	/**
	 * 更新 TB_LS_LOCATION 與 TB_LS_SITE 的 租金起算日、更新人員 與 更新日期
	 * 
	 * @param appSeq
	 *            申請單號流水號
	 * @param rentLsNoArray
	 *            陣列-合約編號
	 * @param rentLsVerArray
	 *            陣列-版次
	 * @param rentLocationIdArray
	 *            陣列-站點編號
	 * @param rentTimeArray
	 *            陣列-租金起算日
	 */
	@Transactional
	public void updayeLocationSitePayBeginDate(String appSeq,String[] rentLsNoArray, String[] rentLsVerArray, String[] rentLocationIdArray, String[] rentTimeArray) {
		Date[] payBeginTimeArray = new Date[rentTimeArray.length];
		try {
			for (int i = 0; i < rentTimeArray.length; i++) {
				payBeginTimeArray[i] = DateUtils.paserDate(rentTimeArray[i], "yyyy/MM/dd");
			}
			for(int i = 0; i < payBeginTimeArray.length; i++){
				TbLsLocationAdded record = new TbLsLocationAdded();
				TbLsLocationAddedExample example = new TbLsLocationAddedExample();
				example.createCriteria().andLS_NOEqualTo(rentLsNoArray[i]).andAPP_SEQEqualTo(appSeq).andADDED_STATEEqualTo("AFTER").andLOCATION_IDEqualTo(rentLocationIdArray[i]);
				record.setACCT_USER(getLoginUser().getUsername());
				record.setACCT_TIME(DateUtils.today());
				record.setPAY_BEGIN_DATE(payBeginTimeArray[i]);
				lS002Dao.updayeLocationAddedDate(record, example);
			}
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
}
