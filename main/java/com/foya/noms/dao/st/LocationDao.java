package com.foya.noms.dao.st;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbSiteLocationMapper;
import com.foya.dao.mybatis.model.TbSiteLocation;
import com.foya.dao.mybatis.model.TbSiteLocationExample;
import com.foya.noms.dao.BaseDao;

@Repository
public class LocationDao extends BaseDao {

	@Autowired
	private TbSiteLocationMapper mapper;
	
	public int insert(TbSiteLocation record) {
		return mapper.insertSelective(record);
	}
	
	public int update(TbSiteLocation record) {
		return mapper.updateByPrimaryKeySelective(record);
	}
	
	public int updateByPrimaryKey(TbSiteLocation record) {
		return mapper.updateByPrimaryKey(record);
	}
	
	public TbSiteLocation findByPk(String LOCATION_ID) {
		return mapper.selectByPrimaryKey(LOCATION_ID);
	}
	
	public List<TbSiteLocation> findByConditions(TbSiteLocationExample example) {
		return mapper.selectByExample(example);
	}
}
