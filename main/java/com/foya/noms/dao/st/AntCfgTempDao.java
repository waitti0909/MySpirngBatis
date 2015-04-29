package com.foya.noms.dao.st;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbSiteWorkAntCfgTempMapper;
import com.foya.dao.mybatis.model.TbSiteWorkAntCfgTemp;
import com.foya.dao.mybatis.model.TbSiteWorkAntCfgTempExample;
import com.foya.dao.mybatis.model.TbSiteWorkAntCfgTempKey;
import com.foya.noms.dao.BaseDao;

@Repository
public class AntCfgTempDao extends BaseDao {

	@Autowired
	private TbSiteWorkAntCfgTempMapper mapper;
	
	public int insert(TbSiteWorkAntCfgTemp record) {
		return mapper.insertSelective(record);
	}
	
	public List<TbSiteWorkAntCfgTemp> findByCondition(TbSiteWorkAntCfgTempExample example) {
		return mapper.selectByExample(example);
	}
	
	public TbSiteWorkAntCfgTemp findByPk(TbSiteWorkAntCfgTempKey key) {
		return mapper.selectByPrimaryKey(key);
	}
}
