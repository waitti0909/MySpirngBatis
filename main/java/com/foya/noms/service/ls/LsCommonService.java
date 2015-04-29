package com.foya.noms.service.ls;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foya.dao.mybatis.model.TbLsApp;
import com.foya.dao.mybatis.model.TbLsAppExample;
import com.foya.dao.mybatis.model.TbLsCollectUnit;
import com.foya.dao.mybatis.model.TbLsCollectUnitSub;
import com.foya.dao.mybatis.model.TbLsLessor;
import com.foya.dao.mybatis.model.TbLsLocElec;
import com.foya.dao.mybatis.model.TbLsLocElecExample;
import com.foya.dao.mybatis.model.TbLsLocPayment;
import com.foya.dao.mybatis.model.TbLsLocPaymentExample;
import com.foya.dao.mybatis.model.TbLsLocation;
import com.foya.dao.mybatis.model.TbLsLocationExample;
import com.foya.dao.mybatis.model.TbLsMain;
import com.foya.dao.mybatis.model.TbLsMainExample;
import com.foya.dao.mybatis.model.TbLsMainKey;
import com.foya.dao.mybatis.model.TbLsSite;
import com.foya.dao.mybatis.model.TbOrgDomain;
import com.foya.exception.NomsException;
import com.foya.noms.dao.ls.LS001Dao;
import com.foya.noms.dao.ls.LS001M3Dao;
import com.foya.noms.dao.ls.LsCommonDao;
import com.foya.noms.dto.ls.LeaseDTO;
import com.foya.noms.dto.ls.LeaseLocationDTO;
import com.foya.noms.dto.ls.LeaseMainDTO;
import com.foya.noms.dto.ls.TbLsLocPaymentDTO;
import com.foya.noms.dto.ls.TbLsRewardDTO;
import com.foya.noms.dto.pay.TbLsLocElecDTO;
import com.foya.noms.dto.pay.TbLsSiteDTO;
import com.foya.noms.enums.LsPaymentItem;
import com.foya.noms.service.BaseService;
import com.foya.noms.service.org.DomainService;

@Service
public class LsCommonService extends BaseService {

	@Autowired
	private LS001M3Dao lS001M3ao;
	@Autowired
	private LS001Dao ls001Dao;
	
	@Autowired
	private LsCommonDao lsCommonDao;
	
	@Autowired
	private DomainService domainService;
	/**
	 * 撈取區域資料
	 * @param lsNo
	 * 			合約編號
	 * @param lsVer
	 * 			版次
	 * @return
	 */
	public List<TbOrgDomain> getDomainValue(String lsNo ,String lsVer) {
		List<TbLsMain> tbLsMainList = lS001M3ao.selectLsMainBylsNoVerMax(lsNo);
		List<TbOrgDomain> tbOrgDomainList = domainService.getAccessDomainByUser(getLoginUser().getDomain());
		Map<String, String> domainMap = new HashMap<String, String>();
		if (tbLsMainList.size() > 0) {
			for (TbLsMain list : tbLsMainList) {
				if (StringUtils.isNotEmpty(list.getOP_DOMAIN())) {
					for (TbOrgDomain userDomain : tbOrgDomainList) {
						String[] tempDomain = list.getOP_DOMAIN().split(",");
						for (String domain : tempDomain) {
							if (StringUtils.equals(domain, userDomain.getID())) {
								domainMap.put(userDomain.getID(), userDomain.getNAME());
								break;
							}
						}
					}
				}
			}
			Set<String> keySet = domainMap.keySet();
			if (keySet != null) {
				List<TbOrgDomain> list = new ArrayList<TbOrgDomain>();
				for (String key : keySet) {
					TbOrgDomain tbOrgDomain = new TbOrgDomain();
					tbOrgDomain.setID(key);
					tbOrgDomain.setNAME(domainMap.get(key));
					list.add(tbOrgDomain);
				}
				return list;
			} else {
				return null;
			}
		} else {

			return null;
		}
	}

	/**
	 * 查詢合約主檔 By 合約最大版次
	 * 
	 * @param lsNo 合約編號
	 * @return
	 */
	public List<TbLsMain> selectLsMainBylsNoVerMax(String lsNo) {
		List<TbLsMain> list = lS001M3ao.selectLsMainBylsNoVerMax(lsNo);
		return list;
	}
	
	/**
	 * 查詢合約主檔 By 合約最大版次
	 * 
	 * @param lsNo 合約編號
	 * @return
	 */
	public List<TbLsMain> selectEffectivesMainBylsNoVerMax(String lsNo) {
		List<TbLsMain> list = lS001M3ao.selectEffectivesMainBylsNoVerMax(lsNo);
		return list;
	}
	
	/**
	 * 取得搜尋條件的所有站點
	 * 
	 * @param lsNo
	 *            合約編號
	 * @param lsVer
	 *            版次 非必需 null 則自動抓取最大板次
	 * @return 版次 非必需 null 則自動抓取最大板次
	 */
	public List<LeaseLocationDTO> getLeaseLocationByLsNo(String lsNo, String lsVer,String domain){
		List<LeaseLocationDTO> leaseLocationDTOList = ls001Dao.getLeaseLocation(lsNo, lsVer,domain);
		return leaseLocationDTOList;
	}
	
	public List<TbLsCollectUnit> getAllCollectionUnit(){
		
		return lsCommonDao.getAllCollectionUnit();
	}
	
	public List<TbLsCollectUnitSub> getAllCollectionUnitSub(){
		
		return lsCommonDao.getAllCollectionUnitSub();
	}
	
	
	/**
	 * 撈取 TB_LS_LOC_ELEC 資料
	 * 
	 * @param lsNo
	 *            合約編號
	 * @param locId
	 *            站點編號
	 * @return
	 */
	public List<TbLsLocElecDTO> searchTbLsLocElecDTO(String lsNo, String locId) {
		return lS001M3ao.searchTbLsLocElecDTO(lsNo, locId);
	}
	
	public List<LeaseLocationDTO> getLsLocationByLsNoVer(String lsNo, String locId) {
		return ls001Dao.getLsSiteLocationByLsNoVer(lsNo, null, locId);
	}
	
	
	public List<TbLsLocPaymentDTO> getLsLocPaymentByLsNoVerLocId(String lsNo, String locId,LsPaymentItem[] pItem) {
		return ls001Dao.getLsLocPaymentByLsNoVerLocId(lsNo, null, locId,pItem);
		
	}
		
	public List<TbLsSiteDTO> getLsSiteByLsNoLocId(String lsNo, String lsVer, String locId,LsPaymentItem pItem){
		return ls001Dao.getLsSiteByLsNoLocId(lsNo, lsVer, locId, pItem);
	}
	
	public List<TbLsLessor> getLsLessorByNoVer(String lsNo){
		return lS001M3ao.getLsLessorByNoVer(lsNo);
	}
	
	public List<TbLsRewardDTO> getLsRewardByLsNoMaxVer(String lsNo,String rewardType){
		HashMap<String, String> dataObj = new HashMap<String, String>();
		dataObj.put("lsNo", lsNo);
		dataObj.put("rewardType", rewardType);
		return lS001M3ao.getLsRewardByLsNoMaxVer(dataObj);
	}
	
	/**
	 * 計算合約編號以及該狀態底下的資料筆數
	 * @param lsNo 合約編號
	 * @param status 狀態代碼
	 * @return int 
	 */
	public int getCountAppStatus(String lsNo,String status){
		return lS001M3ao.getCountAppStatus(lsNo, status);
	}
	
	/**
	 * 
	 * @param appStatus 欲更新的狀態號碼   0:草稿，1:送審中，2:核可，9:駁回
	 * @param lsStatus  欲更新的狀態號碼   0:未生效；1:生效；9:失效
	 * @param appSeq 申請流水號 PK
	 */
	@Transactional
	public void updateLsAppStatus(String appStatus,String lsStatus,String appSeq)throws NomsException{
		 TbLsApp appObj  = new TbLsApp();//tb_ls_app
		 TbLsAppExample exampleApp = new TbLsAppExample();
	     exampleApp.createCriteria().andAPP_SEQEqualTo(appSeq);
		 appObj.setAPP_STATUS(appStatus);
			 
		if(StringUtils.isNotBlank(lsStatus)){
			 TbLsApp tbLsAppTemp  = lS001M3ao.searchTbLsApp(appSeq);//query ls_no、ls_var
			 LeaseMainDTO mainObj = new LeaseMainDTO();//tb_ls_main
			 TbLsMainExample exampleLsMain = new TbLsMainExample();
			 exampleLsMain.createCriteria().andLS_NOEqualTo(tbLsAppTemp.getLS_NO()).andLS_VEREqualTo(tbLsAppTemp.getLS_VER());
			 mainObj.setLS_STATUS(lsStatus);//main Status
			 ls001Dao.updatetblsMainNotAll(mainObj,exampleLsMain);
		}
		  
		 lS001M3ao.updateLsApp(appObj,exampleApp);
	}

	
	/**
	 * 取合約statDate
	 * @param appSeq
	 * @param number
	 * @return
	 */
	public Date getSndDate(String appSeq , int number){ 
		LeaseDTO leaseDTO = selectMaxVerForTbLsApp(appSeq);
		TbLsMainKey key = new TbLsMainKey();
		key.setLS_NO(leaseDTO.getLS_NO());
		key.setLS_VER(leaseDTO.getLS_VER());
		Date sDate = ls001Dao.searchLsMainKey(key).getLS_S_DATE();
		return getYesterDay(sDate,number);
	}
	
	/**
	 * update tb_ls_main
	 * update tb_ls_loction
	 * update tb_ls_loc_elec
	 * update TB_LS_LOC_PAYMENT
	 * update TB_ls_sit
	 * @param oAppSeq 舊合約流水號
	 * @param nAppSeq 新合約流水號
	 */
	@Transactional
	public void updateEndDate(String oAppSeq,String userName,Date oEDate)throws NomsException{
		//查詢 lsNo lsVer 舊約的enddate
		TbLsApp tbLsAppTemp  = lS001M3ao.searchTbLsApp(oAppSeq);
		String lsNo = tbLsAppTemp.getLS_NO();
		String lsVer = tbLsAppTemp.getLS_VER();
		//update tb_ls_main
		LeaseMainDTO leaseMainDTO = new LeaseMainDTO();
		leaseMainDTO.setLS_E_DATE(oEDate);
		leaseMainDTO.setMD_TIME(new Date());
		leaseMainDTO.setMD_USER(userName);
		TbLsMainExample tbLsMainExample = new TbLsMainExample();
		tbLsMainExample.createCriteria().andLS_NOEqualTo(lsNo).andLS_VEREqualTo(lsVer);
		ls001Dao.updatetblsMainNotAll(leaseMainDTO,tbLsMainExample);
		
		//update tb_ls_loction
		TbLsLocation tbLsLocationObj = new TbLsLocation();
		tbLsLocationObj.setLS_E_DATE(oEDate);
		tbLsLocationObj.setMD_TIME(new Date());
		tbLsLocationObj.setMD_USER(userName);
		TbLsLocationExample tbLsLocationExample = new TbLsLocationExample();
		tbLsLocationExample.createCriteria().andLS_NOEqualTo(lsNo).andLS_VEREqualTo(lsVer);
		lS001M3ao.updateTbLsLocationByExample(tbLsLocationObj,tbLsLocationExample);
		
		//update tb_ls_loc_elec
		TbLsLocElec tbLsLocElecObj = new TbLsLocElec();
		tbLsLocElecObj.setELEC_END_DATE(oEDate);
		tbLsLocElecObj.setMD_TIME(new Date());
		tbLsLocElecObj.setMD_USER(userName);
		TbLsLocElecExample tbLsLocElecExample = new TbLsLocElecExample();
		tbLsLocElecExample.createCriteria().andLS_NOEqualTo(lsNo).andLS_VEREqualTo(lsVer);
		lS001M3ao.updateElecCh(tbLsLocElecObj,tbLsLocElecExample);
		
		//update TB_LS_LOC_PAYMENT
		TbLsLocPayment tbLsLocPaymentObj = new TbLsLocPayment();
		tbLsLocPaymentObj.setEND_DATE(oEDate);
		tbLsLocPaymentObj.setMD_TIME(new Date());
		tbLsLocPaymentObj.setMD_USER(userName);
		TbLsLocPaymentExample tbLsLocPaymentExample = new TbLsLocPaymentExample();
		tbLsLocPaymentExample.createCriteria().andLS_NOEqualTo(lsNo).andLS_VEREqualTo(lsVer);
		ls001Dao.updateLsLocPayment(tbLsLocPaymentObj,tbLsLocPaymentExample);
		
		//update TB_ls_sit
		TbLsSite tbLsSiteObj = new TbLsSite();
		tbLsSiteObj.setEND_DATE(oEDate);
		tbLsSiteObj.setMD_TIME(new Date());
		tbLsSiteObj.setMD_USER(userName);
		lS001M3ao.updateTbLsSiteByChangeSiteId(lsNo,lsVer,"", "",tbLsSiteObj);
	}
	
	/**
	 * 抓出原合約號碼 (換約)
	 * @param appSeq
	 * @return
	 */
	public String queryOldAppSeq(String appSeq){
		LeaseDTO leaseDTO = selectMaxVerForTbLsApp(appSeq);
		TbLsMainKey key = new TbLsMainKey();
		key.setLS_NO(leaseDTO.getLS_NO());
		key.setLS_VER(leaseDTO.getLS_VER());
		String oldAppSeq = "";
		//原合約
		String oriLsNo = ls001Dao.searchLsMainKey(key).getORI_LS_NO();
		if(StringUtils.isNotBlank(oriLsNo)){
			List<TbLsMain> list = selectLsMainBylsNoVerMax(oriLsNo);
			TbLsAppExample tbLsAppExample  = new TbLsAppExample();
			tbLsAppExample.createCriteria().andLS_NOEqualTo(list.get(0).getLS_NO()).andLS_VEREqualTo(list.get(0).getLS_VER());
			oldAppSeq = lS001M3ao.selectTbLsAppByExampleWithBLOBs(tbLsAppExample).get(0).getAPP_SEQ();
		}
		return oldAppSeq;	
	}
	/**
	 * 用app_Seq 取最大版號以及
	 * @param appSeq
	 * @return
	 */
	public LeaseDTO selectMaxVerForTbLsApp(String appSeq){
		return lS001M3ao.selectMaxVerForTbLsApp(appSeq);
	}
	
	/**
	 * 
	 * @param day 
	 * @param number 運算數字
	 * @return
	 */
	public Date getYesterDay(Date day,int number){
		Calendar cal = Calendar.getInstance();
		cal.setTime(day);
		cal.add(Calendar.DATE, number);
		return cal.getTime();
	}

}
