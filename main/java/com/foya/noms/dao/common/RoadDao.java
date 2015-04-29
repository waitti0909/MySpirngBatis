package com.foya.noms.dao.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbComRoadMapper;
import com.foya.dao.mybatis.model.TbComRoad;
import com.foya.dao.mybatis.model.TbComRoadExample;
import com.foya.noms.dao.BaseDao;

@Repository
public class RoadDao extends BaseDao {

	@Autowired
	private TbComRoadMapper mapper;
	
	public List<TbComRoad> findByCondition(TbComRoadExample example) {
		return mapper.selectByExample(example);
	}
	
	public int insert(TbComRoad record) {
		return mapper.insert(record);
	}
	
	public int update(TbComRoad record, TbComRoadExample example) {
		return mapper.updateByExampleSelective(record, example);
	}
	
	public int delete(TbComRoadExample example) {
		return mapper.deleteByExample(example);
	}
}
