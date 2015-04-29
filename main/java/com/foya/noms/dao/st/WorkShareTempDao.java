package com.foya.noms.dao.st;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbSiteWorkShareTempMapper;
import com.foya.dao.mybatis.model.TbSiteWorkShareTempExample;
import com.foya.dao.mybatis.model.TbSiteWorkShareTempKey;
import com.foya.noms.dao.BaseDao;

@Repository
public class WorkShareTempDao extends BaseDao {
	@Autowired
	private TbSiteWorkShareTempMapper tbSiteWorkShareTempMapper;
	
	
	public List<TbSiteWorkShareTempKey> getSelectByExample(
			TbSiteWorkShareTempExample shareTemp) {
		return tbSiteWorkShareTempMapper.selectByExample(shareTemp);
		              
	}

	public int deleteByExample(TbSiteWorkShareTempExample shareTemp) {
		return tbSiteWorkShareTempMapper.deleteByExample(shareTemp);
	}

	public int insert(TbSiteWorkShareTempKey key) {
		return tbSiteWorkShareTempMapper.insert(key);
	}
}
