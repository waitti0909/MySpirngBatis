package com.foya.noms.dao.ls;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbLsClauseMapper;
import com.foya.dao.mybatis.mapper.UTbLsClauseMapper;
import com.foya.dao.mybatis.model.TbLsClause;
import com.foya.noms.dao.BaseDao;

@Repository
public class LsClauseDao extends BaseDao{

	@Autowired
	private TbLsClauseMapper tbLsClauseMapper;
	
	@Autowired
	private UTbLsClauseMapper uTbLsClauseMapper;
	
	public List<TbLsClause> getLsClause(){
		return uTbLsClauseMapper.getAllClause();
	}
	
	
	/**
	 * TB_LS_CLAUSE 查詢
	 * @param claId
	 *          PK鍵查詢
	 * @return
	 */
	public TbLsClause findClauseByPk(String claId) {
		return tbLsClauseMapper.selectByPrimaryKey(Integer.valueOf(claId));
	}
}
