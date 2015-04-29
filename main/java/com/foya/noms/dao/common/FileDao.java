package com.foya.noms.dao.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbComFileMapper;
import com.foya.dao.mybatis.model.TbComFile;
import com.foya.dao.mybatis.model.TbComFileExample;
import com.foya.noms.dao.BaseDao;

@Repository
public class FileDao extends BaseDao {

	@Autowired
	private TbComFileMapper mapper;
	
	public List<TbComFile> findByCondition(TbComFileExample example) {
		return mapper.selectByExample(example);
	}
	
	public int insert(TbComFile record) {
		return mapper.insert(record);
	}
	
	public int update(TbComFile record, TbComFileExample example) {
		return mapper.updateByExampleSelective(record, example);
	}
	
	public int delete(TbComFileExample example) {
		return mapper.deleteByExample(example);
	}
	
}
