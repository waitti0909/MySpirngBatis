package com.foya.noms.service.org;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foya.dao.mybatis.model.TbOrgAgent;
import com.foya.dao.mybatis.model.TbOrgAgentExample;
import com.foya.noms.dao.org.AgentDao;
import com.foya.noms.service.BaseService;

/**
 * The Class AgentService.
 */
@Service
public class AgentService extends BaseService {
	public static final String ACTIVE = "Active";
	public static final String CREATED = "Created";
	public static final String CANCELLED = "Cancelled";

	/** The agent dao. */
	@Autowired
	private AgentDao agentDao;

	/**
	 * Find agents by user no.
	 * 
	 * @param userNo the user no
	 * @return the list
	 */
	public List<TbOrgAgent> findAgentsByUserNo(String userNo) {
		TbOrgAgentExample example = new TbOrgAgentExample();
		example.createCriteria().andPSN_NOEqualTo(userNo);
		return agentDao.findByCondition(example);
	}

	/**
	 * Find active agents by user no.
	 * 
	 * @param userNo the user no
	 * @return the list
	 */
	public List<TbOrgAgent> findActiveAgentsByUserNo(String userNo) {
		Date today = new Date();
		return findActiveAgentsByUserNo(userNo, today, today);
	}

	/**
	 * Find active agents by user no.
	 * 
	 * @param userNo the user no
	 * @param startTime the start time
	 * @param endTime the end time
	 * @return the list
	 */
	public List<TbOrgAgent> findActiveAgentsByUserNo(String userNo, Date startTime, Date endTime) {
		TbOrgAgentExample example = new TbOrgAgentExample();

		TbOrgAgentExample.Criteria criteria1 = example.createCriteria();
		criteria1.andPSN_NOEqualTo(userNo).andSTATUSEqualTo(ACTIVE)
				.andSTART_TIMELessThanOrEqualTo(startTime).andSTART_TIMEIsNotNull()
				.andEND_TIMEGreaterThanOrEqualTo(endTime);

		TbOrgAgentExample.Criteria criteria2 = example.createCriteria();
		criteria2.andEND_TIMEIsNull();
		example.or(criteria2);

		return agentDao.findByCondition(example);
	}

	/**
	 * Find users by agent user no.
	 * 
	 * @param agentUserNo the agent user no
	 * @return the list
	 */
	public List<TbOrgAgent> findUsersByAgentUserNo(String agentUserNo) {
		TbOrgAgentExample example = new TbOrgAgentExample();
		example.createCriteria().andAGENT_PSN_NOEqualTo(agentUserNo);
		return agentDao.findByCondition(example);
	}

	/**
	 * Find active users by agent user no.
	 * 
	 * @param agentUserNo the agent user no
	 * @return the list
	 */
	public List<TbOrgAgent> findActiveUsersByAgentUserNo(String agentUserNo) {
		Date today = new Date();
		return findActiveUsersByAgentUserNo(agentUserNo, today, today);
	}

	/**
	 * Find active users by agent user no.
	 * 
	 * @param agentUserNo the agent user no
	 * @param startTime the start time
	 * @param endTime the end time
	 * @return the list
	 */
	public List<TbOrgAgent> findActiveUsersByAgentUserNo(String agentUserNo, Date startTime,
			Date endTime) {
		TbOrgAgentExample example = new TbOrgAgentExample();

		TbOrgAgentExample.Criteria criteria1 = example.createCriteria();
		criteria1.andAGENT_PSN_NOEqualTo(agentUserNo).andSTATUSEqualTo(ACTIVE)
				.andSTART_TIMELessThanOrEqualTo(startTime).andSTART_TIMEIsNotNull()
				.andEND_TIMEGreaterThanOrEqualTo(endTime);

		TbOrgAgentExample.Criteria criteria2 = example.createCriteria();
		criteria2.andEND_TIMEIsNull();
		example.or(criteria2);

		return agentDao.findByCondition(example);
	}

}
