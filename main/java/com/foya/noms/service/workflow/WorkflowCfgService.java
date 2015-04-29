package com.foya.noms.service.workflow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foya.dao.mybatis.model.TbWorkflowCfg;
import com.foya.dao.mybatis.model.TbWorkflowCfgExample;
import com.foya.noms.dao.workflow.WorkflowCfgDao;
import com.foya.noms.service.BaseService;

@Service
public class WorkflowCfgService extends BaseService {

	@Autowired
	private WorkflowCfgDao workflowCfgDao;

	public TbWorkflowCfg getWorkflowCfgByProcessType(String processType) {
		TbWorkflowCfgExample example = new TbWorkflowCfgExample();
		example.createCriteria().andPROCESS_TYPEEqualTo(processType);
		List<TbWorkflowCfg> list = workflowCfgDao.findByCondition(example);
		return list.isEmpty() ? null : list.get(0);
	}

	public List<TbWorkflowCfg> findWorkflowCfgByProcessTypes(Collection<String> processTypes) {
		if (processTypes.isEmpty()) {
			return new ArrayList<>();
		}
		TbWorkflowCfgExample example = new TbWorkflowCfgExample();
		example.createCriteria().andPROCESS_TYPEIn(new ArrayList<String>(processTypes));
		return workflowCfgDao.findByCondition(example);
	}
	
	public List<TbWorkflowCfg> findByCondition(TbWorkflowCfgExample example) {
		return workflowCfgDao.findByCondition(example);
	}
}
