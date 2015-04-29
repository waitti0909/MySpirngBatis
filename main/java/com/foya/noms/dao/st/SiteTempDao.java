package com.foya.noms.dao.st;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbSiteWorkAntCfgTempMapper;
import com.foya.dao.mybatis.mapper.TbSiteWorkSiteTempMapper;
import com.foya.dao.mybatis.model.TbSiteWorkAntCfgTemp;
import com.foya.dao.mybatis.model.TbSiteWorkAntCfgTempExample;
import com.foya.dao.mybatis.model.TbSiteWorkSiteTemp;
import com.foya.dao.mybatis.model.TbSiteWorkSiteTempExample;
import com.foya.dao.mybatis.model.TbSiteWorkSiteTempKey;
import com.foya.noms.dao.BaseDao;

@Repository
public class SiteTempDao extends BaseDao {

	@Autowired
	private TbSiteWorkSiteTempMapper mapper;
	@Autowired
	private TbSiteWorkAntCfgTempMapper tempMapper;
	
	public List<TbSiteWorkSiteTemp> findByConditions(TbSiteWorkSiteTempExample example) {
		return mapper.selectByExample(example);
	}
	
	public TbSiteWorkSiteTemp findByPrimaryKey(TbSiteWorkSiteTempKey key) {
		return mapper.selectByPrimaryKey(key);
	}
	
	public int insert(TbSiteWorkSiteTemp record) {
		return mapper.insertSelective(record);
	}
	
	public int update(TbSiteWorkSiteTemp record) {
		return mapper.updateByPrimaryKey(record);
	}
	
	public List<TbSiteWorkSiteTemp> findSiteTempByExample(TbSiteWorkSiteTempExample example){
		return mapper.selectByExample(example);
	}
	
	/**
	 * 基站資訊 更新
	 * @return
	 */
	public int updateSiteWorkSiteTemp(TbSiteWorkSiteTemp record , TbSiteWorkSiteTempExample example){
		return mapper.updateByExample(record, example);
	}
	
	/**
	 * 基站資訊 天線組態 刪除
	 * @return
	 */
	public int deleteSiteWorkAntCfgTemp(TbSiteWorkAntCfgTempExample example){
		return tempMapper.deleteByExample(example);
	}
	
}
