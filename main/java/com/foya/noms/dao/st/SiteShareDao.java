package com.foya.noms.dao.st;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbSiteShareMapper;
import com.foya.dao.mybatis.model.TbSiteShareExample;
import com.foya.dao.mybatis.model.TbSiteShareKey;

@Repository
public class SiteShareDao {
	
	@Autowired
	private TbSiteShareMapper tbSiteShareMapper;
	
	public List<TbSiteShareKey> findByConditions(TbSiteShareExample example){
		return tbSiteShareMapper.selectByExample(example);
	}
	
	public int insert(TbSiteShareKey record){
		return tbSiteShareMapper.insert(record);
	}
	
	public int updateByConditions(TbSiteShareKey record,TbSiteShareExample example){
		return tbSiteShareMapper.updateByExample(record, example);
	}
	
	public int deleteByConditions(TbSiteShareExample example){
		return tbSiteShareMapper.deleteByExample(example);
	}
}
