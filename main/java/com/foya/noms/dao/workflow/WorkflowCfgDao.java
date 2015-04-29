package com.foya.noms.dao.workflow;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbWorkflowCfgMapper;
import com.foya.dao.mybatis.mapper.UTbWorkflowCfgMapper;
import com.foya.dao.mybatis.model.TbWorkflowCfg;
import com.foya.dao.mybatis.model.TbWorkflowCfgExample;
import com.foya.noms.dao.BaseDao;

@Repository
public class WorkflowCfgDao extends BaseDao {
	@Autowired
	private TbWorkflowCfgMapper mapper;

	@Autowired
	private UTbWorkflowCfgMapper uMapper;

	public List<TbWorkflowCfg> findByCondition(TbWorkflowCfgExample example) {
		return mapper.selectByExample(example);
	}

	public List<TbWorkflowCfg> findSameGroupByProcessType(@Param("processTypes") String processType) {
		return uMapper.findSameGroupByProcessType(processType);
	}
}
