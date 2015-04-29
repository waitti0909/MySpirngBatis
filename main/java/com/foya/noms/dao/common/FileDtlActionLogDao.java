package com.foya.noms.dao.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbComFileDtlActionLogMapper;
import com.foya.dao.mybatis.model.TbComFileDtlActionLog;
import com.foya.dao.mybatis.model.TbComFileDtlActionLogExample;
import com.foya.noms.dao.BaseDao;

@Repository
public class FileDtlActionLogDao extends BaseDao {

	@Autowired
	private TbComFileDtlActionLogMapper mapper;
	
	public List<TbComFileDtlActionLog> findByCondition(TbComFileDtlActionLogExample example) {
		return mapper.selectByExample(example);
	}
	
	public int insert(TbComFileDtlActionLog record) {
		return mapper.insert(record);
	}
	
	public int update(TbComFileDtlActionLog record, TbComFileDtlActionLogExample example) {
		return mapper.updateByExampleSelective(record, example);
	}
	
	public int delete(TbComFileDtlActionLogExample example) {
		return mapper.deleteByExample(example);
	}
}
