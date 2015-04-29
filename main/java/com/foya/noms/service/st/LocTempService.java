package com.foya.noms.service.st;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foya.dao.mybatis.model.TbSiteWork;
import com.foya.dao.mybatis.model.TbSiteWorkLocTemp;
import com.foya.dao.mybatis.model.TbSiteWorkLocTempKey;
import com.foya.dao.mybatis.model.TbSiteWorkShareTempExample;
import com.foya.dao.mybatis.model.TbSiteWorkShareTempKey;
import com.foya.noms.dao.st.LocTempDao;
import com.foya.noms.dao.st.WorkShareTempDao;
import com.foya.noms.service.BaseService;

@Service
public class LocTempService extends BaseService {
	
	@Autowired
	private LocTempDao locTempDao;
	@Autowired
	private WorkShareTempDao tbSiteWorkShareTempDao;

	public TbSiteWork getSiteWork(String workId) {
		return locTempDao.getSiteWork(workId);
	}
	
	@Transactional
	public int updatelocTemp(TbSiteWorkLocTemp siteWorkLocTemp) {
		return locTempDao.updatelocTemp(siteWorkLocTemp);
	}

	public List<TbSiteWorkShareTempKey> getSelectByExample(
			TbSiteWorkShareTempExample shareTemp) {
		return tbSiteWorkShareTempDao.getSelectByExample(shareTemp);
	}
	@Transactional
	public int deleteByExample(TbSiteWorkShareTempExample shareTemp) {
		return tbSiteWorkShareTempDao.deleteByExample(shareTemp);
	}
	@Transactional
	public int insert(TbSiteWorkShareTempKey key) {
		return tbSiteWorkShareTempDao.insert(key);
	}

	public TbSiteWorkLocTemp selectByPrimaryKey(TbSiteWorkLocTempKey key) {
		// TODO Auto-generated method stub
		return locTempDao.selectByPrimaryKey(key);
	}

	
	
}
