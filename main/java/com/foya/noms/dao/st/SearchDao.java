package com.foya.noms.dao.st;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbSiteSearchMapper;
import com.foya.dao.mybatis.model.TbSiteSearch;
import com.foya.dao.mybatis.model.TbSiteSearchKey;
import com.foya.noms.dao.BaseDao;

@Repository
public class SearchDao extends BaseDao {
	
	@Autowired
	private TbSiteSearchMapper tbSiteSearchMapper;
	
	public TbSiteSearch findByPk(TbSiteSearchKey key){
		return tbSiteSearchMapper.selectByPrimaryKey(key);
	}
	
}
