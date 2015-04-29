package com.foya.noms.dao.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbSysLookupMapper;
import com.foya.dao.mybatis.mapper.UTbSysLookupMapper;
import com.foya.dao.mybatis.model.TbSysLookup;
import com.foya.dao.mybatis.model.TbSysLookupExample;
import com.foya.noms.dao.BaseDao;

@Repository
public class LookupDao extends BaseDao {

	@Autowired
	private UTbSysLookupMapper uTbSysLookupMapper;
	@Autowired
	private TbSysLookupMapper tbSysLookupMapper;
	/**
	 * 取得全部公告類型
	 * @param
	 * @return
	 */
	public List<TbSysLookup> selectBulletinType(){		
		return uTbSysLookupMapper.selectBulletinType();
	}
	
	/**
	 * 取得所有LookupButton
	 */
	public List<TbSysLookup> selectByExample(TbSysLookupExample example){
		return tbSysLookupMapper.selectByExample(example);
	}
}
