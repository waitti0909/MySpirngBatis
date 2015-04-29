package com.foya.noms.dao.st;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbSiteMainMapper;
import com.foya.dao.mybatis.model.TbSiteMain;
import com.foya.dao.mybatis.model.TbSiteMainExample;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.enums.SiteStatus;

@Repository
public class SiteMainDao extends BaseDao {

	@Autowired
	private TbSiteMainMapper mapper;
	
	public int insert(TbSiteMain record) {
		return mapper.insertSelective(record);
	}
	
	public int update(TbSiteMain record) {
		return mapper.updateByPrimaryKey(record);
	}
	public int updateByPrimaryKeySelective(TbSiteMain record) {
		return mapper.updateByPrimaryKeySelective(record);
	}
	public TbSiteMain findByPk(String siteId) {
		return mapper.selectByPrimaryKey(siteId);
	}
	
	public List<TbSiteMain> findByConditions(TbSiteMainExample example) {
		return mapper.selectByExample(example);
	}
	
	public int deleteByPrimaryKey(String siteId) {
		return mapper.deleteByPrimaryKey(siteId);
	}
	
	public int updateSiteStatusBySiteId(String siteId,SiteStatus siteStatus){
		TbSiteMain siteMain = findByPk(siteId);
		siteMain.setSITE_STATUS(siteStatus.name());
		return this.updateByPrimaryKeySelective(siteMain);
	}
}
