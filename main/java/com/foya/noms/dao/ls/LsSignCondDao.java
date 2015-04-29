package com.foya.noms.dao.ls;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbLsSignCondMapper;
import com.foya.dao.mybatis.model.TbLsSignCond;
import com.foya.noms.dao.BaseDao;

@Repository
public class LsSignCondDao extends BaseDao {

	@Autowired
	private TbLsSignCondMapper mapper;

	public List<TbLsSignCond> findLsSignCond() {
		return mapper.selectByExample(null);
	}

}
