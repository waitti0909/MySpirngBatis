package com.foya.noms.dao.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbSysEmailTemplateMapper;
import com.foya.dao.mybatis.model.TbSysEmailTemplate;
import com.foya.dao.mybatis.model.TbSysEmailTemplateExample;
import com.foya.noms.dao.BaseDao;

@Repository
public class EmailTemplateDao extends BaseDao {
	
	@Autowired
	private TbSysEmailTemplateMapper mapper;
	
	public List<TbSysEmailTemplate> findByCondition(TbSysEmailTemplateExample example) {
		return mapper.selectByExample(example);
	}
	
	public TbSysEmailTemplate findByPk(String emailType) {
		return mapper.selectByPrimaryKey(emailType);
	}
	
}
