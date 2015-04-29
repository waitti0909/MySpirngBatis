package com.foya.noms.dao.st;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbSiteAntCfgMapper;
import com.foya.dao.mybatis.model.TbSiteAntCfg;
import com.foya.dao.mybatis.model.TbSiteAntCfgExample;
import com.foya.dao.mybatis.model.TbSiteAntCfgKey;
import com.foya.noms.dao.BaseDao;

@Repository
public class AntCfgDao extends BaseDao {
	
	@Autowired
	private TbSiteAntCfgMapper mapper;
	
	public int insert(TbSiteAntCfg record){
		return mapper.insertSelective(record);
	}
	
	public TbSiteAntCfg findByPk(TbSiteAntCfgKey key) {
		return mapper.selectByPrimaryKey(key);
	}
	
	public List<TbSiteAntCfg> findByCondition(TbSiteAntCfgExample example) {
		return mapper.selectByExample(example);
	}
	
	/**
	 * 基站資訊 天線組態 刪除
	 * @return
	 */
	public int deleteSiteAntCfg(TbSiteAntCfgExample example){
		return mapper.deleteByExample(example);
	}
}
