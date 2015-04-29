package com.foya.noms.service.org;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foya.dao.mybatis.mapper.TbOrgDeptMapper;
import com.foya.dao.mybatis.mapper.UTbOrgDeptMapper;
import com.foya.dao.mybatis.model.TbOrgDept;
import com.foya.noms.service.BaseService;

@Service
public class DeptService extends BaseService {
	
	
	@Autowired
	private UTbOrgDeptMapper uTbOrgDeptMapper;
	@Autowired
	private TbOrgDeptMapper tbOrgDeptMapper;
	

	/**
	 *取得可使用的區域清單
	 *@input 使用者部門所屬區域
	 * 
	 * @return List<TbOrgDomain>
	 */
	public List<TbOrgDept> getDeptByDomain(List<String> domainList){
		return uTbOrgDeptMapper.selectDeptByDomainList(domainList);
		
	}
	
	
	/**
	 * 以deptId取得TB_ORG_DEPT
	 * @param deptId
	 * @return
	 */
	public TbOrgDept getDeptByPrimaryKey(String deptId){
		return tbOrgDeptMapper.selectByPrimaryKey(deptId);
		
	}
	
	
}
