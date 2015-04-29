package com.foya.noms.dao.st;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbSiteStatusLogMapper;
import com.foya.dao.mybatis.model.TbSiteStatusLog;
import com.foya.dao.mybatis.model.TbSiteStatusLogExample;
import com.foya.noms.dao.BaseDao;

@Repository
public class SiteStatusLogDao extends BaseDao {

	@Autowired
	private TbSiteStatusLogMapper mapper;
	
	public int insert(TbSiteStatusLog record) {
		return mapper.insertSelective(record);
	}
	
	public int update(TbSiteStatusLog record ,TbSiteStatusLogExample example) {
		return mapper.updateByExampleSelective(record, example);
	}
	
}
