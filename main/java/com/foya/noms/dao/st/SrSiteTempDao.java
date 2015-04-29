package com.foya.noms.dao.st;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbSiteSrSiteTempMapper;
import com.foya.dao.mybatis.model.TbSiteSrSiteTemp;
import com.foya.dao.mybatis.model.TbSiteSrSiteTempExample;
import com.foya.dao.mybatis.model.TbSiteSrSiteTempKey;
import com.foya.noms.dao.BaseDao;

@Repository
public class SrSiteTempDao extends BaseDao {
	@Autowired
	private TbSiteSrSiteTempMapper  tbSiteSrSiteTempMapper;
	
	public TbSiteSrSiteTemp findByPk (TbSiteSrSiteTempKey key){
		return tbSiteSrSiteTempMapper.selectByPrimaryKey(key);
	}
	
	/**
	 * 探勘 更新
	 * @return
	 */
	public int updateSiteSrSiteTemp(TbSiteSrSiteTemp record , TbSiteSrSiteTempExample example){
		return tbSiteSrSiteTempMapper.updateByExample(record, example);
	}
	
	/**
	 * 探勘 新增
	 * @return
	 */
	public int insertSiteSrSiteTemp(TbSiteSrSiteTemp record){
		return tbSiteSrSiteTempMapper.insertSelective(record);
	}
}
