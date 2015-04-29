package com.foya.noms.dao.st;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbSiteSrAntCfgTempMapper;
import com.foya.dao.mybatis.model.TbSiteSrAntCfgTemp;
import com.foya.dao.mybatis.model.TbSiteSrAntCfgTempExample;
import com.foya.dao.mybatis.model.TbSiteSrAntCfgTempKey;
import com.foya.noms.dao.BaseDao;

@Repository
public class SrAntCfgDao extends BaseDao {
	
	@Autowired
	private TbSiteSrAntCfgTempMapper tbSiteSrAntCfgTempMapper;
	
	public TbSiteSrAntCfgTemp findByPk (TbSiteSrAntCfgTempKey key){
		return tbSiteSrAntCfgTempMapper.selectByPrimaryKey(key);
	}
	
	public List<TbSiteSrAntCfgTemp> findByExample(TbSiteSrAntCfgTempExample example){
		return tbSiteSrAntCfgTempMapper.selectByExample(example);
	}
	
	/**
	 * 探勘 更新
	 * @return
	 */
	public int deleteSiteSrAntCfgTemp(TbSiteSrAntCfgTempExample example){
		return tbSiteSrAntCfgTempMapper.deleteByExample(example);
	}
	
	/**
	 * 探勘 新增
	 * @return
	 */
	public int updateSaveSiteSrAntCfgTemp(TbSiteSrAntCfgTemp record){
		return tbSiteSrAntCfgTempMapper.insertSelective(record);
	}
}
