package com.foya.noms.dao.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbComPoLineIdMapper;
import com.foya.dao.mybatis.model.TbComPoLineId;
import com.foya.dao.mybatis.model.TbComPoLineIdExample;
import com.foya.dao.mybatis.model.TbComPoLineIdKey;
import com.foya.noms.dao.BaseDao;

@Repository
public class PoLineIdDao extends BaseDao {
	
	@Autowired
	private TbComPoLineIdMapper tbComPoLineIdMapper;
	
	public List<TbComPoLineId> findByCondition(TbComPoLineIdExample example){
		return tbComPoLineIdMapper.selectByExample(example);
	}
	
	public TbComPoLineId findByPrimaryKey(TbComPoLineIdKey key){
		return tbComPoLineIdMapper.selectByPrimaryKey(key);
	}
}
