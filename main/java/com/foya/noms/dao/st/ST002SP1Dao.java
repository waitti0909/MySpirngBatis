package com.foya.noms.dao.st;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbComEqpTypeMapper;
import com.foya.dao.mybatis.mapper.TbComSiteFeqMapper;
import com.foya.dao.mybatis.mapper.TbSiteMainMapper;
import com.foya.dao.mybatis.mapper.UTbSiteMainMapper;
import com.foya.dao.mybatis.model.TbComEqpType;
import com.foya.dao.mybatis.model.TbComEqpTypeExample;
import com.foya.dao.mybatis.model.TbComSiteFeq;
import com.foya.dao.mybatis.model.TbComSiteFeqExample;
import com.foya.dao.mybatis.model.TbSiteMain;
import com.foya.dao.mybatis.model.TbSiteMainExample;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.st.SiteDTO;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Repository
public class ST002SP1Dao extends BaseDao {
	@Autowired
	private UTbSiteMainMapper uTbSiteMainMapper;
	@Autowired
	private TbSiteMainMapper tbSiteMainMapper;
	@Autowired
	private TbComEqpTypeMapper tbComEqpTypeMapper;
	@Autowired
	private TbComSiteFeqMapper tbComSiteFeqMapper;
	/**
	 * 查詢site
	 * @param map
	 * @return
	 */
	public List<SiteDTO> findWorkSiteList(Map<String ,String > map){
		PageBounds pageBounds = getPageBounds(map.get("sort")+"."+map.get("order"));
		List<SiteDTO> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbSiteMainMapper.selectWorkSiteList", map, pageBounds);
		PageList<SiteDTO> pageList = (PageList<SiteDTO>)list;
		return pageList;
	}
	
	/**
	 * 用TbSiteMainExample查詢site
	 * @param example
	 * @return
	 */
	public List<TbSiteMain> findWorkSiteListByExample(TbSiteMainExample example){
		return tbSiteMainMapper.selectByExample(example);
	}
	
	/**
	 * 用siteId查詢siteMain
	 * @param siteId
	 * @return
	 */
	public TbSiteMain findWorkSiteBySiteId(String siteId){
		return tbSiteMainMapper.selectByPrimaryKey(siteId);
	}
	
	/**
	 * 用locationId查詢Site
	 * @param locationId
	 * @return
	 */
	public List<SiteDTO> getSiteByLocationId(String locationId){
		return uTbSiteMainMapper.selectSiteByLocationId(locationId);
	}
	
	/**
	 * 查詢設備型態
	 * @param example
	 * @return
	 */
	public List<TbComEqpType> findEqpTypes(TbComEqpTypeExample example){
		return tbComEqpTypeMapper.selectByExample(example);
	}
	/**
	 * 查詢基站頻段
	 * @param example
	 * @return
	 */
	public List<TbComSiteFeq> findSiteFeqs(TbComSiteFeqExample example){
		return tbComSiteFeqMapper.selectByExample(example);
	}
}
