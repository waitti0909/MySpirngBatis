package com.foya.noms.dao.system;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbSysUserAuditMapper;
import com.foya.dao.mybatis.model.TbSysUserAudit;
import com.foya.dao.mybatis.model.TbSysUserAuditExample;
import com.foya.noms.dao.BaseDao;

@Repository
public class UserAuditDao extends BaseDao {

	@Autowired
	private TbSysUserAuditMapper userAuditMapper;
	
	
	//查詢
	public List<TbSysUserAudit> selectById(TbSysUserAuditExample example) {
		// do nothing
		return null;
	}
	//新增	
	public void insert(TbSysUserAudit model) {
		userAuditMapper.insert(model);
	}
	//更新
	public void update(TbSysUserAudit model) {
		// do nothing
	}
	//刪除
	public void delete(BigDecimal roleId) {
		// do nothing
	}

}
