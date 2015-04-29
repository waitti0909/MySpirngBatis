package com.foya.noms.dao.auth;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbAuthPsnRoleMapper;
import com.foya.dao.mybatis.mapper.UTbAuthPsnRoleMapper;
import com.foya.dao.mybatis.model.TbAuthPsnRole;
import com.foya.noms.dao.BaseDao;

@Repository
public class PsnRoleDao extends BaseDao {

	@Autowired
	private UTbAuthPsnRoleMapper uTbAuthPsnRoleMapper;
	
	@Autowired
	private TbAuthPsnRoleMapper tbAuthPsnRoleMapper;
	
	/**
	 * 查詢所有使用者的角色(distinct)
	 * @return
	 */
	public List<TbAuthPsnRole> selectAllPsnRole(){
		return uTbAuthPsnRoleMapper.selectAllPsnRole();
	}
	
	/**
	 * 新增
	 * @return
	 */
	public void addUserRole(TbAuthPsnRole record){
		tbAuthPsnRoleMapper.insert(record);
	}
	
	/**
	 * 根據角色ID查詢使用者
	 * @return
	 */
	public List<TbAuthPsnRole> searchUserByRole(BigDecimal roleId){
		return uTbAuthPsnRoleMapper.selectPsnByRole(roleId);
	}
	
	/**
	 * 刪除
	 * @return
	 */
	public void removeUserRole(TbAuthPsnRole key){
		tbAuthPsnRoleMapper.deleteByPrimaryKey(key);
	}
	

}