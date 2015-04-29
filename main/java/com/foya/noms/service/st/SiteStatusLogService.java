package com.foya.noms.service.st;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foya.dao.mybatis.model.TbSiteStatusLog;
import com.foya.dao.mybatis.model.TbSiteStatusLogExample;
import com.foya.noms.dao.st.SiteStatusLogDao;
import com.foya.noms.enums.SiteStatus;
import com.foya.noms.service.BaseService;

@Service
public class SiteStatusLogService extends BaseService{
	
	@Autowired
	private SiteStatusLogDao siteStatusLogDao;
	
	public void updateSiteStatusLog(String siteId,String btsSiteId,SiteStatus previousSiteStatus,SiteStatus nextSiteStatus,Date date){
		TbSiteStatusLogExample example = new TbSiteStatusLogExample();
		example.createCriteria().andSITE_IDEqualTo(siteId).andSITE_STATUSEqualTo(previousSiteStatus.name());
		TbSiteStatusLog siteStatusLog = new TbSiteStatusLog();
		siteStatusLog.setEND_TIME(date);
		siteStatusLogDao.update(siteStatusLog, example);
		siteStatusLog = new TbSiteStatusLog();
		siteStatusLog.setSITE_ID(siteId);
		siteStatusLog.setBTS_SITE_ID(btsSiteId);
		siteStatusLog.setSITE_STATUS(nextSiteStatus.name());
		siteStatusLog.setSTART_TIME(date);
		siteStatusLogDao.insert(siteStatusLog);
	}
}
