package com.foya.noms.dao.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbComCityMapper;
import com.foya.dao.mybatis.model.TbComCity;
import com.foya.dao.mybatis.model.TbComCityExample;
import com.foya.noms.dao.BaseDao;

@Repository
public class CityDao extends BaseDao {
	
	@Autowired
	private TbComCityMapper tbComCityMapper;
	
	public List<TbComCity> findCityByCondition(TbComCityExample example) {
		return tbComCityMapper.selectByExample(example);
	}
	
	public TbComCity findCityByPrimaryKey(String CITY_ID) {
		return tbComCityMapper.selectByPrimaryKey(CITY_ID);
	}
}
