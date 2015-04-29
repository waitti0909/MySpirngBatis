package com.foya.noms.service.st;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foya.dao.mybatis.model.TbComAntenna;
import com.foya.dao.mybatis.model.TbComAntennaExample;
import com.foya.dao.mybatis.model.TbComEqpModel;
import com.foya.dao.mybatis.model.TbComEqpModelExample;
import com.foya.dao.mybatis.model.TbSiteLocation;
import com.foya.dao.mybatis.model.TbSiteLocationExample;
import com.foya.dao.mybatis.model.TbSiteMain;
import com.foya.dao.mybatis.model.TbSiteMainExample;
import com.foya.dao.mybatis.model.TbSiteSearch;
import com.foya.dao.mybatis.model.TbSiteSearchExample;
import com.foya.dao.mybatis.model.TbSiteShareSearchExample;
import com.foya.dao.mybatis.model.TbSiteShareSearchKey;
import com.foya.dao.mybatis.model.TbSiteSrAntCfgTemp;
import com.foya.dao.mybatis.model.TbSiteSrAntCfgTempExample;
import com.foya.dao.mybatis.model.TbSiteSrSiteTemp;
import com.foya.dao.mybatis.model.TbSiteSrSiteTempExample;
import com.foya.dao.mybatis.model.TbSiteSrSiteTempKey;
import com.foya.dao.mybatis.model.TbSiteWork;
import com.foya.dao.mybatis.model.TbSiteWorkExample;
import com.foya.dao.mybatis.model.TbSysLookup;
import com.foya.dao.mybatis.model.TbSysLookupExample;
import com.foya.exception.NomsException;
import com.foya.noms.dao.st.SearchRecordDao;
import com.foya.noms.dao.st.SiteShareSearchDao;
import com.foya.noms.dao.st.SrAntCfgDao;
import com.foya.noms.dao.st.SrSiteTempDao;
import com.foya.noms.dao.system.LookupDao;
import com.foya.noms.dto.st.SiteSearchDTO;
import com.foya.noms.service.BaseService;

@Service
public class SearchRecordService extends BaseService{
	
	@Autowired
	private LookupDao sysLookupDao;
	@Autowired
	private SearchRecordDao searchRecordDao;
	@Autowired
	private SrSiteTempDao srSiteTempDao;
	@Autowired
	private SrAntCfgDao srAntCfgDao;
	@Autowired
	private SiteShareSearchDao siteShareSearchDao;
	
	/**
	 * 共站業者
	 * @param lookupList
	 * @return
	 */
	public List<TbSysLookup> getLookupAll(TbSysLookupExample example) {
		example.createCriteria().andTYPEEqualTo("SHARECOM");
	return sysLookupDao.selectByExample(example);
	}
	
	/**
	 * 探勘記錄查詢
	 * @param example
	 * @return
	 */
	public List<SiteSearchDTO> getSiteSearchByExample(TbSiteSearchExample example){
		return searchRecordDao.findSiteSearchByExample(example);
	}
	
	//===============================================================================
	//selectByExampleWork
	public List<TbSiteMain> getselectByExampleMain(TbSiteMainExample example){
		return searchRecordDao.selectByExampleTbMain(example);
	}
	
	//selectByExampleTbWork
	public List<TbSiteWork> getselectByExampleWork(TbSiteWorkExample example){
		return searchRecordDao.selectByExampleTbWork(example);
	}	
	
	//selectByExampleTbLoc
	public List<TbSiteLocation> getselectByExampleLoc(TbSiteLocationExample example){
		return searchRecordDao.selectByExampleTbLoc(example);
	}	
	//===============================================================================
	
	/**
	 * 探勘記錄查詢
	 * @param example
	 * @return
	 */
	public List<TbComEqpModel> getEqpModelByExample(TbComEqpModelExample example){
		return searchRecordDao.findEqpModelByExample(example);
	}
	
	/**
	 * 基站狀態查詢
	 * @param example
	 * @return
	 */
	public TbSiteMain getSiteStatusIdByExample(String workId){
		return searchRecordDao.findSiteStatusIdByExample(workId);
	}
	
	/**
	 * 天線型號 查詢
	 * @param example
	 * @return
	 */
	public List<TbComAntenna> getAntennaByExample(TbComAntennaExample example){
		return searchRecordDao.findAntennaByExample(example);
	}
	
	/**
	 * 利用探勘序號 srSeq 查詢 出 該筆資料 
	 * @param Example
	 * @return
	 */
	public List<SiteSearchDTO> getSearchTableByExample(Map<String, String> map){
		return searchRecordDao.findSearchTableByExample(map);
	}
	
	/**
	 * 利用探勘序號 srSeq 查詢 出 共站業者 
	 * @param Example
	 * @return
	 */
	public List<TbSiteShareSearchKey> getShareSearchByExample(TbSiteShareSearchExample example){
		return searchRecordDao.findShareSearchByExample(example);
	}
	
	/**
	 * 利用探勘序號 srSeq 查詢 天線組態
	 * @param Example
	 * @return
	 */
	public List<TbSiteSrAntCfgTemp> getSiteSrAntCfgTempByExample(TbSiteSrAntCfgTempExample example){
		return searchRecordDao.findSiteSrAntCfgTempByExample(example);
	}
	
	/**
	 * 探勘更新
	 */
	@Transactional
	public boolean saveSearchRecord(Map<String, Object> map, TbSiteSrSiteTemp record, TbSiteSrSiteTempExample example, 
									List<TbSiteSrAntCfgTemp> recordAnt, TbSiteSrAntCfgTempExample exampleAnt, 
									List<TbSiteShareSearchKey> shareSearchList, TbSiteShareSearchExample exampleShare) {
		try {
			//TbSearch
			map.put("mdUser", getLoginUser().getUsername());
			searchRecordDao.updateSearchRecord(map);
			//TbSiteSrSiteTemp
			record.setMD_USER(getLoginUser().getUsername());
			record.setMD_TIME(new Date());
			TbSiteSrSiteTempKey key = new TbSiteSrSiteTemp();
			key.setSR_ID(record.getSR_ID());
			key.setSR_SEQ(record.getSR_SEQ());
			key.setSITE_ID(record.getSITE_ID());
			TbSiteSrSiteTemp targeSrSiteTemp = srSiteTempDao.findByPk(key);
			srSiteTempDao.updateSiteSrSiteTemp(record, example);
			String[] ignoreProperties = {"SR_ID" , "SR_SEQ" , "SITE_ID" , "FEQ_ID" , "CR_USER", "CR_TIME" , "BTS_SITE_ID","PURPOSE"};
			BeanUtils.copyProperties(record, targeSrSiteTemp, ignoreProperties);
			srSiteTempDao.updateSiteSrSiteTemp(targeSrSiteTemp, example);
			
			//TbSiteShareSearch
			siteShareSearchDao.deleteByConditions(exampleShare);
			for (TbSiteShareSearchKey shareTemp : shareSearchList) {
				siteShareSearchDao.insert(shareTemp);
			}
			//TbSiteSrAntCfgTemp
			srAntCfgDao.deleteSiteSrAntCfgTemp(exampleAnt);
			for (TbSiteSrAntCfgTemp antCfgTemp : recordAnt) {
				antCfgTemp.setMD_USER(getLoginUser().getUsername());
				antCfgTemp.setMD_TIME(new Date());	
				srAntCfgDao.updateSaveSiteSrAntCfgTemp(antCfgTemp);
			}
		} catch (NomsException nomsException) {
			log.error(nomsException.getMessage());
			throw new NomsException("探勘資料 更新錯誤 :" + nomsException.getMessage());
		}

		return true;
	}
	
	/**
	 * 新增探勘
	 */
	@Transactional
	public boolean saveNewSearchRecord(TbSiteSearch sitrRecord, TbSiteSrSiteTemp record, List<TbSiteSrAntCfgTemp> recordAnt,
			List<TbSiteShareSearchKey> shareSearchList) {
		try {
			//TbSearch
			sitrRecord.setCR_USER(getLoginUser().getUsername());
			sitrRecord.setCR_TIME(new Date());
			searchRecordDao.insertSearchRecord(sitrRecord);
			//TbSiteSrSiteTemp
			record.setCR_USER(getLoginUser().getUsername());
			record.setCR_TIME(new Date());
			srSiteTempDao.insertSiteSrSiteTemp(record);
			//TbSiteSrAntCfgTemp
			for (TbSiteSrAntCfgTemp antCfgTemp : recordAnt) {
				antCfgTemp.setMD_USER(getLoginUser().getUsername());
				antCfgTemp.setMD_TIME(new Date());	
				srAntCfgDao.updateSaveSiteSrAntCfgTemp(antCfgTemp);
			}
			//TbSiteShareSearch
			for (TbSiteShareSearchKey shareTemp : shareSearchList) {
				siteShareSearchDao.insert(shareTemp);
			}
		} catch (NomsException nomsException) {
			log.error(nomsException.getMessage());
			throw new NomsException("探勘資料 新增錯誤 :" + nomsException.getMessage());
		}

		return true;
	}
}
