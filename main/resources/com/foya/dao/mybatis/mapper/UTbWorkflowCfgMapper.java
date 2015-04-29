package com.foya.dao.mybatis.mapper;

import java.util.List;

import com.foya.dao.mybatis.model.TbWorkflowCfg;

public interface UTbWorkflowCfgMapper {
	List<TbWorkflowCfg> findSameGroupByProcessType(String processType);
}