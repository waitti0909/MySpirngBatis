package com.foya.noms.dao.st.line;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbSiteLineSiteDataMapper;
import com.foya.dao.mybatis.mapper.UTbSiteLineSiteDataMapper;
import com.foya.dao.mybatis.model.TbSiteLineSiteData;
import com.foya.dao.mybatis.model.TbSiteLineSiteDataExample;
import com.foya.dao.mybatis.model.TbSiteLineSiteDataKey;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.st.SiteLineSiteDataDTO;

@Repository
public class LineSiteDataDao extends BaseDao {

	@Autowired
	private TbSiteLineSiteDataMapper mapper;
	@Autowired
	private UTbSiteLineSiteDataMapper uTbSiteLineSiteDataMapper;
	
	public TbSiteLineSiteData findByPk(TbSiteLineSiteDataKey key) {
		return mapper.selectByPrimaryKey(key);
	}
	/**
	 * 依照example查詢SiteLineSiteDate
	 * @param example
	 * @return
	 */
	public List<TbSiteLineSiteData> findByCondition(TbSiteLineSiteDataExample example) {
		return mapper.selectByExample(example);
	}
	/**
	 * 依照siteId查詢SiteLineSiteDate
	 * @param siteIdList
	 * @return
	 */
	public List<SiteLineSiteDataDTO> findSiteLineSiteDateBySiteId(List<String> siteIdList){
		return uTbSiteLineSiteDataMapper.selectSiteLineSiteDateBySiteId(siteIdList);
	}
}
