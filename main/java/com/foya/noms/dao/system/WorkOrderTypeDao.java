package com.foya.noms.dao.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbSysWorkOrderTypeMapper;
import com.foya.dao.mybatis.model.TbSysWorkOrderType;
import com.foya.dao.mybatis.model.TbSysWorkOrderTypeExample;
import com.foya.noms.dao.BaseDao;

@Repository
public class WorkOrderTypeDao extends BaseDao {

	@Autowired
	private TbSysWorkOrderTypeMapper mapper;
	
	public TbSysWorkOrderType findByPk(TbSysWorkOrderType key) {
		return mapper.selectByPrimaryKey(key);
	}
	
	public List<TbSysWorkOrderType> findByConditions(TbSysWorkOrderTypeExample example) {
		return mapper.selectByExample(example);
	}
	
}
