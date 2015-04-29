package com.foya.noms.dao.org;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbOrgDomainMapper;
import com.foya.dao.mybatis.mapper.UTbOrgDomainMapper;
import com.foya.dao.mybatis.model.TbOrgDomain;
import com.foya.dao.mybatis.model.TbOrgDomainExample;
import com.foya.noms.dao.BaseDao;
/**
 * @author arda
 *
 */
@Repository
public class DomainDao extends BaseDao  {
	@Autowired
	private TbOrgDomainMapper tbOrgDomainMapper;
	@Autowired
	private UTbOrgDomainMapper uTbOrgDomainMapper;
	/**
	 * 取得所有區域
	 * @return
	 */
	public List<TbOrgDomain> selectOrgDomain(TbOrgDomainExample example){
		return tbOrgDomainMapper.selectByExample(example);
	}
	/**
	 *取得標準第二層(全區的下一層) Domain清單  (北一區,北二區,中區,南區)
	 * 
	 * @return List<TbOrgDomain>
	 */
	public List<TbOrgDomain> getStandardDomain(){
		
		return uTbOrgDomainMapper.getStandardDomain();
	}

	/**
	 *取得標準第二層(全區的下一層)跟全區 Domain清單  (全區,北一區,北二區,中區,南區)
	 * 
	 * @return List<TbOrgDomain>
	 */
	public List<TbOrgDomain> getStandardAndTopDomain(){
	
		return uTbOrgDomainMapper.getStandardAndTopDomain();
	}
	
	/**
	 *取得Domain By id
	 * 
	 * @return TbOrgDomain
	 */
	public TbOrgDomain getDomainById(String domainId){
		
		return tbOrgDomainMapper.selectByPrimaryKey(domainId);
	}
	
	
	
	/**
	 *取得Domain tree By standard id
	 * 
	 * @return TbOrgDomain
	 */
	public List<TbOrgDomain> getDomainIdTreeByStandard(String domainId){
		
		return uTbOrgDomainMapper.getDomainIdTreeByStandard(domainId);
	}
	
	
	
	
}
