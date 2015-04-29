package com.foya.noms.dao.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbSysRespUserMapper;
import com.foya.dao.mybatis.model.TbSysRespUser;
import com.foya.dao.mybatis.model.TbSysRespUserKey;
import com.foya.noms.dao.BaseDao;

@Repository
public class RespUserDao extends BaseDao {

	@Autowired
	private TbSysRespUserMapper mapper;
	
	public TbSysRespUser findByPk(TbSysRespUserKey key) {
		return mapper.selectByPrimaryKey(key);
	}
}
