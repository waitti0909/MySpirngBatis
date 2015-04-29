package com.foya.noms.dao.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbComTownMapper;
import com.foya.dao.mybatis.model.TbComTown;
import com.foya.dao.mybatis.model.TbComTownExample;
import com.foya.noms.dao.BaseDao;

@Repository
public class TownDao extends BaseDao {

	@Autowired
	private TbComTownMapper tbComTownMapper;
	
	public List<TbComTown> findTownByCondition(TbComTownExample example) {
		return tbComTownMapper.selectByExample(example);
	}
	
	public TbComTown findTownByPrimaryKey(String TOWN_ID) {
		return tbComTownMapper.selectByPrimaryKey(TOWN_ID);
	}
}
