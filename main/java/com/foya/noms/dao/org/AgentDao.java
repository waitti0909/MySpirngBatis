package com.foya.noms.dao.org;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbOrgAgentMapper;
import com.foya.dao.mybatis.model.TbOrgAgent;
import com.foya.dao.mybatis.model.TbOrgAgentExample;
import com.foya.noms.dao.BaseDao;

@Repository
public class AgentDao extends BaseDao {

	@Autowired
	private TbOrgAgentMapper mapper;

	public List<TbOrgAgent> findByCondition(TbOrgAgentExample example) {
		return mapper.selectByExample(example);
	}

	public int insert(TbOrgAgent record) {
		return mapper.insert(record);
	}

	public int update(TbOrgAgent record, TbOrgAgentExample example) {
		return mapper.updateByExampleSelective(record, example);
	}

	public int delete(TbOrgAgentExample example) {
		return mapper.deleteByExample(example);
	}
}
