package com.foya.noms.dao.org;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbOrgPositionMapper;
import com.foya.dao.mybatis.model.TbOrgPosition;
import com.foya.dao.mybatis.model.TbOrgPositionExample;
import com.foya.noms.dao.BaseDao;
@Repository
public class PositionDao extends BaseDao {
	
	@Autowired
	private TbOrgPositionMapper tbOrgPositionMapper;
	
	/**
	 * 取得所有職稱
	 * @return
	 */
	public List<TbOrgPosition> selectOrgPosition(TbOrgPositionExample example){
		return tbOrgPositionMapper.selectByExample(example);
	}
	
	/**
	 * 用pk查詢position
	 * */
	public TbOrgPosition findPositionByPrimaryKey(String posId){
		return tbOrgPositionMapper.selectByPrimaryKey(posId);
	}
}
