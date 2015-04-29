package com.foya.noms.dao.ls;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbLsAppFormMapper;
import com.foya.dao.mybatis.mapper.UTbLsAppFormMapper;
import com.foya.dao.mybatis.model.TbLsAppForm;
import com.foya.dao.mybatis.model.TbLsAppFormExample;
import com.foya.noms.dao.BaseDao;

@Repository
public class AppFormDao extends BaseDao {

	@Autowired
	private TbLsAppFormMapper mapper;
	@Autowired
	private UTbLsAppFormMapper uTbLsAppFormMapper;
	
	public List<TbLsAppForm> findByExample(TbLsAppFormExample example) {
		return mapper.selectByExample(example);
	}
	
	/**
	 * 取得套表格式 for 租約
	 * @return
	 */
	public List<TbLsAppForm> getTbLsAppFormForLsContract() {
		return uTbLsAppFormMapper.getTbLsAppFormForLsContract();
	}
}
