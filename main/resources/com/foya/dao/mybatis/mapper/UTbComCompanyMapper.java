package com.foya.dao.mybatis.mapper;

import java.util.List;
import java.util.Map;

import com.foya.noms.dto.basic.CompanyDTO;


public interface UTbComCompanyMapper {
	
	/**
	 * 查詢 委外廠商
	 * @return
	 */
	List<CompanyDTO> selectCompanyAllList();
	
	/**
	 * 查詢 委外廠商
	 * @return
	 */
	List<CompanyDTO> selectCompanyList(String coVatNo);
	
	List<CompanyDTO> selectTbComCompany();
	
	List<CompanyDTO> selectTbComCompanybyN();	
	
	List<CompanyDTO> selectTbComCompanybyGeneral();
	
	List<CompanyDTO> selectTbComCompanybyOutsourcing(Map<String, String> map);
	
	/**
	 * 共用廠商查詢
	 * @return
	 */
	List<CompanyDTO> selectCompanyByCondition(Map<String ,String> map);
}