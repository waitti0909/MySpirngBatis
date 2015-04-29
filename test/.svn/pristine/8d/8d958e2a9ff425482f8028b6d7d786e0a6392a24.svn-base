package com.foya.noms.service.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.dao.mybatis.model.TbSysEmailTemplate;
import com.foya.dao.mybatis.model.TbSysEmailTemplateExample;
import com.foya.noms.GenericTest;
import com.foya.noms.dao.common.EmailTemplateDao;
import com.foya.noms.enums.EmailType;
import com.foya.noms.enums.WorkflowEmailType;

public class EmailTemplateServiceTest extends GenericTest {

	@Autowired
	private EmailTemplateService service;
	@Autowired
	private EmailTemplateDao emailTemplateDao;

//	@Test
	public void testGetEmailTemplate() {
		String type = WorkflowEmailType.WORKFLOW_TODO_WORK_APPLY.name();
		TbSysEmailTemplateExample example = new TbSysEmailTemplateExample();
		example.createCriteria().andEMAIL_TYPEEqualTo(type);
		List<TbSysEmailTemplate> list = emailTemplateDao.findByCondition(example);
		Assert.assertNotNull(list);
		Assert.assertFalse(list.isEmpty());
	}

//	@Test
	public void testGetEmailTemplates() {
		List<String> types = new ArrayList<>();
		for (WorkflowEmailType value : WorkflowEmailType.values()) {
			types.add(value.name());
		}
		TbSysEmailTemplateExample example = new TbSysEmailTemplateExample();
		example.createCriteria().andEMAIL_TYPEIn(types);
		List<TbSysEmailTemplate> list = emailTemplateDao.findByCondition(example);
		Assert.assertNotNull(list);
		Assert.assertFalse(list.isEmpty());
	}
	
	@Test
	public void testGetMailVarMapForWorkflow() {
		Map<String, Object> map = service.getMailVarMapForWorkflow(EmailType.WORKFLOW_TODO_WORK_OUTSOURCING_ACCEPT, "201503160001203");
		log.debug(map.get("workType"));
		log.debug(map.get("osId"));
		log.debug(map.get("company"));
		log.debug(map.get("appDept"));
		log.debug(map.get("appUser"));
	}
}
