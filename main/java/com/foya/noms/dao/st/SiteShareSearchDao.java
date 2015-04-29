package com.foya.noms.dao.st;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbSiteShareSearchMapper;
import com.foya.dao.mybatis.model.TbSiteShareSearchExample;
import com.foya.dao.mybatis.model.TbSiteShareSearchKey;

@Repository
public class SiteShareSearchDao {
	
	@Autowired
	private TbSiteShareSearchMapper tbSiteShareSearchMapper;
	
	public List<TbSiteShareSearchKey> findByConditions(TbSiteShareSearchExample example){
		return tbSiteShareSearchMapper.selectByExample(example);
	}
	
	public int insert(TbSiteShareSearchKey record){
		return tbSiteShareSearchMapper.insert(record);
	}
	
	public int updateByConditions(TbSiteShareSearchKey record,TbSiteShareSearchExample example){
		return tbSiteShareSearchMapper.updateByExample(record, example);
	}
	
	public int deleteByConditions(TbSiteShareSearchExample example){
		return tbSiteShareSearchMapper.deleteByExample(example);
	}
}
