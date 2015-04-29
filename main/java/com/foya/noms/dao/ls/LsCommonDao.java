package com.foya.noms.dao.ls;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbLsCollectUnitMapper;
import com.foya.dao.mybatis.mapper.TbLsCollectUnitSubMapper;
import com.foya.dao.mybatis.model.TbLsCollectUnit;
import com.foya.dao.mybatis.model.TbLsCollectUnitSub;
import com.foya.noms.dao.BaseDao;

@Repository
public class LsCommonDao extends BaseDao {

	
	@Autowired
	private TbLsCollectUnitMapper tbLsCollectUnitMapper;
	
	@Autowired
	private TbLsCollectUnitSubMapper tbLsCollectUnitSubMapper;
	
	
	
	public List<TbLsCollectUnit> getAllCollectionUnit(){
		
		return tbLsCollectUnitMapper.selectByExample(null);
	}
	
	public List<TbLsCollectUnitSub> getAllCollectionUnitSub(){
		
		return tbLsCollectUnitSubMapper.selectByExample(null);
	}
	
	
	
	
	
}