package com.foya.dao.mybatis.mapper;

import java.util.List;

import com.foya.dao.mybatis.model.TbOrgDomain;

public interface UTbOrgDomainMapper {
	/**
	 * 取得標準第二層(全區的下一層) Domain清單 (北一區,北二區,中區,南區)
	 * 
	 * @return
	 */
	List<TbOrgDomain> getStandardDomain();

	/**
	 * 取得標準第二層(全區的下一層)跟全區 Domain清單 (全區,北一區,北二區,中區,南區)
	 * 
	 * @return
	 */
	List<TbOrgDomain> getStandardAndTopDomain();

	/**
	 * 取得全區 Domain清單 (全區,北一區,北二區,中區,南區)
	 * 
	 * @return
	 */
	List<TbOrgDomain> getAllDomain();
	
	
	/**
	 * 取得 Domain Id 樹狀清單 (全區,北一區,北二區,中區,南區)
	 * 
	 * @return
	 */
	List<TbOrgDomain> getDomainIdTreeByStandard(String domainId);
	
	
	
}