package com.foya.noms.service.org;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.dao.mybatis.model.TbOrgAgent;
import com.foya.noms.GenericTest;
import com.foya.noms.dao.org.AgentDao;
import com.foya.noms.util.DateUtils;

public class AgentServiceTest extends GenericTest {
	@Autowired
	AgentService agentService;

	@Autowired
	AgentDao dao;

	@Test
	public void testFindAgentsByUser() {
		List<TbOrgAgent> agents = agentService.findAgentsByUserNo("user");
		Assert.assertNotNull(agents);
		Assert.assertTrue(agents.size() > 0);

	}

	@Test
	public void testFindUsersByAgent() {
		List<TbOrgAgent> users = agentService.findUsersByAgentUserNo("user");
		Assert.assertNotNull(users);
		Assert.assertTrue(users.size() > 0);
	}

	@Test
	public void testRangeFindAvailableAgentsByUser() {
		Date startTime = DateUtils.time(2014, 2, 1, 0, 0, 0);
		Date endTime = DateUtils.time(2014, 12, 1, 23, 59, 59);
		List<TbOrgAgent> agents = agentService.findActiveAgentsByUserNo("user", startTime,
				endTime);
		Assert.assertNotNull(agents);
		Assert.assertTrue(agents.size() > 0);
	}

	@Test
	public void testTodayFindAvailableAgentsByUser() {
		List<TbOrgAgent> agents = agentService.findActiveAgentsByUserNo("user");
		Assert.assertNotNull(agents);
		Assert.assertTrue(agents.size() > 0);
	}

	@Test
	public void testRangeFindAvailableUsersByAgent() {
		Date startTime = DateUtils.time(2014, 2, 1, 0, 0, 0);
		Date endTime = DateUtils.time(2014, 12, 1, 23, 59, 59);
		List<TbOrgAgent> users = agentService.findActiveUsersByAgentUserNo("user", startTime,
				endTime);
		Assert.assertNotNull(users);
		Assert.assertTrue(users.size() > 0);
	}

	@Test
	public void testTodayFindAvailableUsersByAgent() {
		List<TbOrgAgent> users = agentService.findActiveUsersByAgentUserNo("user");
		Assert.assertNotNull(users);
		Assert.assertTrue(users.size() > 0);
	}
}
