package com.foya.noms.dao.st;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foya.dao.mybatis.mapper.TbComAntennaMapper;
import com.foya.dao.mybatis.mapper.TbSiteAntCfgMapper;
import com.foya.dao.mybatis.mapper.TbSiteLocationMapper;
import com.foya.dao.mybatis.mapper.TbSiteMainMapper;
import com.foya.dao.mybatis.mapper.TbSiteShareMapper;
import com.foya.dao.mybatis.mapper.UTbSiteLocationMapper;
import com.foya.dao.mybatis.mapper.UTbSiteMainMapper;
import com.foya.dao.mybatis.model.TbComAntenna;
import com.foya.dao.mybatis.model.TbComAntennaExample;
import com.foya.dao.mybatis.model.TbSiteAntCfg;
import com.foya.dao.mybatis.model.TbSiteAntCfgExample;
import com.foya.dao.mybatis.model.TbSiteLocation;
import com.foya.dao.mybatis.model.TbSiteMain;
import com.foya.dao.mybatis.model.TbSiteMainExample;
import com.foya.dao.mybatis.model.TbSiteShareExample;
import com.foya.dao.mybatis.model.TbSiteShareKey;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.st.SiteDTO;
import com.foya.noms.dto.st.SiteLocationDTO;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

@Service
public class ST011Dao extends BaseDao {
	
	@Autowired
	private UTbSiteMainMapper uTbSiteMainMapper;
	
	@Autowired
	private TbSiteMainMapper tbSiteMainMapper;
	
	@Autowired
	private TbSiteLocationMapper tbSiteLocationMapper;
	
	@Autowired
	private UTbSiteLocationMapper uTbSiteLocationMapper;
	
	@Autowired
	private TbSiteAntCfgMapper tbSiteAntCfgMapper;
	
	@Autowired
	private TbComAntennaMapper tbComAntennaMapper;
	
	@Autowired
	private TbSiteShareMapper tbSiteShareMapper;
	
	public List<SiteDTO> selectWorkSiteList(Map<String,String> map){
		PageBounds pageBounds = getPageBounds("BTS_SITE_ID");
		List<SiteDTO> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbSiteMainMapper.selectWorkSiteList", map, pageBounds);
		return list;
	}

	/**
	 * 由pk查詢站台資料
	 * @param siteId
	 * @return
	 */
	public TbSiteMain selectBySiteId(String siteId ){
		return tbSiteMainMapper.selectByPrimaryKey(siteId);
	}
	
	/**
	 * 由pk查詢站點資料
	 * @param locationId
	 * @return
	 */
	public TbSiteLocation selectByLocationId(String locationId){
		return tbSiteLocationMapper.selectByPrimaryKey(locationId);
	}
	
	/**
	 * 由站始編號查詢天線態檔
	 * @param example
	 * @return
	 */
	public List<TbSiteAntCfg> findByExample(TbSiteAntCfgExample example){
		return tbSiteAntCfgMapper.selectByExample(example);
	}
	
	/**
	 * 由pk查詢站台資料
	 * @param siteId
	 * @return
	 */
	public SiteDTO selectSiteBySiteId(String siteId){
		return uTbSiteMainMapper.selectSiteBySiteId(siteId);
	}
	
	/**
	 * 由pk查詢站點資料
	 * @param locationId
	 * @return
	 */
	public SiteLocationDTO findSiteByPrimaryKey(String locationId){
		return uTbSiteLocationMapper.selectByPrimaryKey(locationId);
	}
	
	/**
	 * 天線型號查詢
	 * @param example
	 * @return
	 */
	public List<TbComAntenna> findAntennaByExample(TbComAntennaExample example){
		return tbComAntennaMapper.selectByExample(example);
	}
	
	public List<TbSiteMain> findTbSiteMainByCondition(TbSiteMainExample example){
		return tbSiteMainMapper.selectByExample(example);
	}
	
	public List<TbSiteShareKey> findSharComByCodition(TbSiteShareExample example){
		return tbSiteShareMapper.selectByExample(example);
	}
	
}
