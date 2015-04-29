package com.foya.noms.dao.org;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbAuthRoleMenuFunDeptMapper;
import com.foya.dao.mybatis.mapper.UTbAuthRoleMenuFunDeptMapper;
import com.foya.dao.mybatis.model.TbAuthRoleMenuFunDept;
import com.foya.dao.mybatis.model.TbAuthRoleMenuFunDeptExample;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.auth.RoleMenuFunDepDTO;

@Repository
public class RoleMenuFunDeptDao extends BaseDao {

	@Autowired
	private TbAuthRoleMenuFunDeptMapper mapper;
	@Autowired
	private UTbAuthRoleMenuFunDeptMapper uMapper;
	
	public List<TbAuthRoleMenuFunDept> findByExample(TbAuthRoleMenuFunDeptExample example) {
		return mapper.selectByExample(example);
	}
	
	public int insert(TbAuthRoleMenuFunDept record) {
		return mapper.insert(record);
	}
	
	public int delete(TbAuthRoleMenuFunDeptExample example) {
		return mapper.deleteByExample(example);
	}
	
	public List<TbAuthRoleMenuFunDept> findUsedDeptByRoleMenu(Map<String, String> map) {
		return uMapper.selectUsedDeptByRoleMenu(map);
	}
	
	/**
	 * 刪除RoleMenuFunDept
	 * @param deptId
	 */
	public void deleteByExample(TbAuthRoleMenuFunDeptExample example){
		mapper.deleteByExample(example);
	}
	
	/**
	 * 取得使用者所擁有的資料部門權限
	 * 
	 * @param psnId
	 */
	public List<RoleMenuFunDepDTO> getAccessDeptByUser(String psnNo) {
		return uMapper.getAccessDeptByUser(psnNo);
	}
	
	
}
