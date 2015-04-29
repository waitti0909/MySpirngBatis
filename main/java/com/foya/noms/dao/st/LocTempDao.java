package com.foya.noms.dao.st;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbSiteWorkLocTempMapper;
import com.foya.dao.mybatis.mapper.TbSiteWorkMapper;
import com.foya.dao.mybatis.model.TbSiteWork;
import com.foya.dao.mybatis.model.TbSiteWorkLocTemp;
import com.foya.dao.mybatis.model.TbSiteWorkLocTempExample;
import com.foya.dao.mybatis.model.TbSiteWorkLocTempKey;
import com.foya.dao.mybatis.model.TbSiteWorkSiteTempExample;
import com.foya.noms.dao.BaseDao;

@Repository
public class LocTempDao extends BaseDao {

	@Autowired
	private TbSiteWorkLocTempMapper mapper;
	@Autowired
	private TbSiteWorkMapper tbSiteWorkMapper;
	
	
	/**
	 * 新增
	 * @param record
	 * @return
	 */
	public int insert(TbSiteWorkLocTemp record) {
		return mapper.insertSelective(record);
	}
	
	public TbSiteWork getSiteWork(String workId) {
		// TODO Auto-generated method stub
		return tbSiteWorkMapper.selectByPrimaryKey(workId);
	}

	public int updatelocTemp(TbSiteWorkLocTemp siteWorkLocTemp) {
		// TODO Auto-generated method stub
		return mapper.updateByPrimaryKeySelective(siteWorkLocTemp);
	}

	public List<TbSiteWorkLocTemp> selectLocTemp(TbSiteWorkLocTempExample example) {
		return mapper.selectByExample(example);	
	}

	public TbSiteWorkLocTemp selectByPrimaryKey(TbSiteWorkLocTempKey key) {
		// TODO Auto-generated method stub
		return mapper.selectByPrimaryKey(key);
	}

	
}
