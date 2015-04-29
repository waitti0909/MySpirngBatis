package com.foya.noms.service.st;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foya.dao.mybatis.model.TbComCity;
import com.foya.dao.mybatis.model.TbComTown;
import com.foya.dao.mybatis.model.TbSiteLocation;
import com.foya.dao.mybatis.model.TbSiteLocationExample;
import com.foya.dao.mybatis.model.TbSiteMain;
import com.foya.dao.mybatis.model.TbSiteMainExample;
import com.foya.dao.mybatis.model.TbSiteSearch;
import com.foya.dao.mybatis.model.TbSiteSearchExample;
import com.foya.dao.mybatis.model.TbSiteSearchRing;
import com.foya.dao.mybatis.model.TbSiteSearchRingExample;
import com.foya.dao.mybatis.model.TbSiteShareSearchExample;
import com.foya.dao.mybatis.model.TbSiteShareSearchKey;
import com.foya.dao.mybatis.model.TbSiteWork;
import com.foya.dao.mybatis.model.TbSiteWorkExample;
import com.foya.exception.CreateFailException;
import com.foya.exception.NomsException;
import com.foya.exception.UpdateFailException;
import com.foya.noms.dao.common.CityDao;
import com.foya.noms.dao.common.TownDao;
import com.foya.noms.dao.st.LocationDao;
import com.foya.noms.dao.st.SearchRingDao;
import com.foya.noms.dao.st.SiteMainDao;
import com.foya.noms.dao.st.SiteShareSearchDao;
import com.foya.noms.dao.st.WorkDao;
import com.foya.noms.dto.st.SearchRingDTO;
import com.foya.noms.dto.st.SiteSearchDTO;
import com.foya.noms.service.BaseService;
import com.foya.noms.service.system.UniqueSeqService;

@Service
public class SearchRingService extends BaseService {
	@Autowired
	private SearchRingDao searchRingDao;
	@Autowired
	private UniqueSeqService uniqueSeqService;
	@Autowired
	private TownDao townDao;
	@Autowired
	private CityDao cityDao;
	@Autowired
	private SiteShareSearchDao siteShareSearchDao;
	@Autowired
	private SiteMainDao siteMainDao;
	@Autowired
	private WorkDao workDao;
	@Autowired
	private LocationDao locationDao;
	/**
	 * 查詢所有search Ring
	 * 
	 * @param map
	 * @return
	 */
	public List<SearchRingDTO> getSearchRingList(Map<String, String> map) {
		return searchRingDao.findSearchRingList(map);
	}

	/**
	 * 用PrimaryKey查詢SearchRing
	 * 
	 * @param SR_ID
	 * @return
	 */
	public TbSiteSearchRing getSearchRingByPrimaryKey(String SR_ID) {
		return searchRingDao.findSearchRingByPrimaryKey(SR_ID);
	}
	
	/**
	 * 用Example查詢SiteSearch
	 * @param Example
	 * @return
	 */
	public List<TbSiteSearch> getSiteSearchByExample(TbSiteSearchExample example){
		return searchRingDao.findSiteSearchByExample(example);
	}
	
	/**
	 * 用PrimaryKey查詢SiteSearch
	 * @param key
	 * @return
	 */
	public SiteSearchDTO getSiteSearchByPk(String srId, String srSeq){
		return searchRingDao.findSiteSearchByPk(srId, srSeq);
	}
	
	/**
	 * 新增
	 * @param searchRing
	 * @return
	 * @throws NomsException
	 */
	@Transactional
	public TbSiteSearchRing insert(TbSiteSearchRing searchRing,String user)
			throws CreateFailException {
//		String errorMessage = doValidate(searchRing);
//		if (errorMessage.isEmpty()) {
			TbComTown town = townDao.findTownByPrimaryKey(searchRing
					.getSR_AREA());
			TbComCity city = cityDao.findCityByPrimaryKey(searchRing
					.getSR_CITY());
			String srId = uniqueSeqService.getNextSearchRingId(town
					.getZIP_CODE().charAt(0), city.getBRIEFCODE());
			Date date = new Date();
			searchRing.setSR_ID(srId);
			searchRing.setCR_USER(user);
			searchRing.setCR_TIME(date);
			searchRing.setMD_USER(user);
			searchRing.setMD_TIME(date);
			int item = searchRingDao.insert(searchRing);
			if (item == 0) {
				log.error("SearchRing insert fail , srId = "+srId);
				throw new CreateFailException("新增失敗");
			}
//		} else {
//			throw new NomsException(errorMessage);
//		}
		return searchRing;
	}
	/**
	 * 修改
	 * @param searchRing
	 * @return
	 * @throws NomsException
	 */
	@Transactional
	public void update(TbSiteSearchRing searchRing,String mdUser) throws NomsException,UpdateFailException {
//		String errorMessage = doValidate(searchRing);
//		if (errorMessage.isEmpty()) {
			searchRing.setMD_USER(mdUser);
			searchRing.setMD_TIME(new Date());
			int searchRingItem = searchRingDao.update(searchRing);
			if (searchRingItem == 0 ) {
				log.error("SearchRing update count ="+searchRingItem+" , srId = "+searchRing.getSR_ID());
				throw new UpdateFailException("更新失敗");
			}
//		} else {
//			throw new NomsException(errorMessage);
//		}
	}
	
	/**
	 * 驗證
	 * @param searchRing
	 * @return
	 */
	public  String doValidate(TbSiteSearchRing searchRing) {
		String errorMessage = "";
		TbSiteSearchRingExample example = new TbSiteSearchRingExample();
		example.createCriteria()
				.andSR_LONBetween(
						searchRing.getSR_LON().subtract(
								BigDecimal.valueOf(0.001)),
						searchRing.getSR_LON()
								.add(BigDecimal.valueOf(0.001)))
				.andSR_LATBetween(
						searchRing.getSR_LAT().subtract(
								BigDecimal.valueOf(0.001)),
						searchRing.getSR_LAT()
								.add(BigDecimal.valueOf(0.001)));
		List<TbSiteSearchRing> searchRingObj = searchRingDao.findSearchRingByExample(example);
		if (searchRingObj.size() > 0) {
			for(TbSiteSearchRing srObj : searchRingObj){
				log.debug("getSR_ID : "+!srObj.getSR_ID().equals(searchRing.getSR_ID()));
				log.debug("getFEQ_ID : " +srObj.getFEQ_ID().equals(searchRing.getFEQ_ID()));
				if((!srObj.getSR_ID().equals(searchRing.getSR_ID())) && srObj.getFEQ_ID().equals(searchRing.getFEQ_ID())){
					errorMessage = "經度與緯度已有相差+-0.001的Search Ring ID";
					break;
				}
			}
		}
		return errorMessage;
	}
	/**
	 * 查詢共構共站
	 * @param example
	 * @return
	 */
	public List<TbSiteShareSearchKey> getSiteShareSearchByConditions(TbSiteShareSearchExample example){
		return siteShareSearchDao.findByConditions(example);
	}
	/**
	 * 查詢siteWork
	 * @param example
	 * @return
	 */
	public List<TbSiteWork> getSiteWorkByConditions(TbSiteWorkExample example){
		return workDao.findByConditions(example);
	}
	/**
	 * 查詢site
	 * @param map
	 * @return
	 */
	public List<TbSiteMain> getSiteMainByConditions(TbSiteMainExample example){
		return siteMainDao.findByConditions(example);
	}
	/**
	 * 查詢location
	 * @param example
	 * @return
	 */
	public List<TbSiteLocation> getLocationByConditions(TbSiteLocationExample example){
		return locationDao.findByConditions(example);
	}
}
