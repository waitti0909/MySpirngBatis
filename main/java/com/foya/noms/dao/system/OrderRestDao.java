package com.foya.noms.dao.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbSysOrderRespMapper;
import com.foya.dao.mybatis.model.TbSysOrderResp;
import com.foya.dao.mybatis.model.TbSysOrderRespKey;
import com.foya.noms.dao.BaseDao;

@Repository
public class OrderRestDao extends BaseDao {

	@Autowired
	private TbSysOrderRespMapper mapper;
	
	public TbSysOrderResp findByPk(TbSysOrderRespKey key) {
		return mapper.selectByPrimaryKey(key);
	}
}
