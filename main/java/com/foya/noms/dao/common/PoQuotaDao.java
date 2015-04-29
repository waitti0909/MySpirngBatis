package com.foya.noms.dao.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbComPoQuotaMapper;
import com.foya.dao.mybatis.model.TbComPoQuota;
import com.foya.dao.mybatis.model.TbComPoQuotaKey;
import com.foya.noms.dao.BaseDao;

@Repository
public class PoQuotaDao extends BaseDao {

	@Autowired
	private TbComPoQuotaMapper mapper;
	
	public int update(TbComPoQuota record) {
		return mapper.updateByPrimaryKeySelective(record);
	}
	
	public int insert(TbComPoQuota record) {
		return mapper.insertSelective(record);
	}
	
	public TbComPoQuota findByPk(TbComPoQuotaKey key) {
		return mapper.selectByPrimaryKey(key);
	}
}
