package com.foya.noms.service.st;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foya.dao.mybatis.model.TbComAntenna;
import com.foya.dao.mybatis.model.TbComAntennaExample;
import com.foya.dao.mybatis.model.TbSiteAntCfg;
import com.foya.dao.mybatis.model.TbSiteAntCfgExample;
import com.foya.dao.mybatis.model.TbSiteLocation;
import com.foya.dao.mybatis.model.TbSiteMain;
import com.foya.dao.mybatis.model.TbSiteMainExample;
import com.foya.dao.mybatis.model.TbSiteShareExample;
import com.foya.dao.mybatis.model.TbSiteShareKey;
import com.foya.noms.dao.st.ST011Dao;
import com.foya.noms.dto.st.SiteDTO;
import com.foya.noms.dto.st.SiteLocationDTO;
import com.foya.noms.enums.SiteStatus;
import com.foya.noms.service.BaseService;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Service
public class ST011Service extends BaseService {

	@Autowired
	private ST011Dao st011Dao;
	
	/**
	 * 
	 * @param map
	 * @return
	 */
	public PageList<SiteDTO> findSiteMainList(Map<String,String> map){
		return (PageList<SiteDTO>) st011Dao.selectWorkSiteList(map);
	}
	
	/**
	 * 由pk查詢站台資料
	 * @param siteId
	 * @return
	 */
	public TbSiteMain findBySiteId(String siteId){
		return st011Dao.selectBySiteId(siteId);
	}
	/**
	 * 由pk查詢站台資料
	 * @param siteId
	 * @return
	 */
	public SiteDTO selectSiteBySiteId(String siteId){
		return st011Dao.selectSiteBySiteId(siteId);
	}
	
	/**
	 * 由pk查詢站點資料
	 * @param locationId
	 * @return
	 */
	public TbSiteLocation findByLocationId(String locationId){
		return st011Dao.selectByLocationId(locationId);
	}
	
	/**
	 * 由pk查詢站點資料
	 * @param locationId
	 * @return
	 */
	public SiteLocationDTO findSiteByPrimaryKey(String locationId){
		return st011Dao.findSiteByPrimaryKey(locationId);
	}
	
	/**
	 * 由站始編號查詢天線態檔
	 * @param siteId
	 * @return
	 */
	public List<TbSiteAntCfg> findByCondition(String siteId){
		TbSiteAntCfgExample example = new TbSiteAntCfgExample();
		example.createCriteria().andSITE_IDEqualTo(siteId);//.andANT_NOEqualTo(antNo);
		return st011Dao.findByExample(example);
	}
	
	/**
	 * 天線型號
	 * @return
	 */
	public List<TbComAntenna> findAntenna(){
		return st011Dao.findAntennaByExample(new TbComAntennaExample());
	}
	
	
	/**
	 * 依站點編號查出有效站台資料
	 * @param locationId
	 * @return 
	 * @return
	 */
	public List<TbSiteMain> selectSiteBylocationIdAndSiteStatusNotS09(String locationId){
		TbSiteMainExample example = new TbSiteMainExample();
		example.createCriteria().andLOCATION_IDEqualTo(locationId).andSITE_IDNotEqualTo(SiteStatus.S09.name());
		return st011Dao.findTbSiteMainByCondition(example);
	}
	
	public List<TbSiteShareKey> findSharComByLocationId(String locationId){
		TbSiteShareExample example = new TbSiteShareExample();
		example.createCriteria().andLOCATION_IDEqualTo(locationId);
		return st011Dao.findSharComByCodition(example);
	}
}
