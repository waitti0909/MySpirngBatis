package com.foya.noms.service.common;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foya.dao.mybatis.model.TbComCompany;
import com.foya.noms.dao.common.CompanyQueryDao;
import com.foya.noms.dto.basic.CompanyDTO;
import com.foya.noms.service.BaseService;

@Service
public class CompanyQueryService extends BaseService {
	
	@Autowired
	private CompanyQueryDao companyQueryDao;
	/**
	 * 查詢
	 * @param map
	 * @return
	 */
	public List<CompanyDTO> getCompanyList(Map<String,String> map) {
		return companyQueryDao.findCompanyList(map);
	}
	
	public TbComCompany queryCompanyNameByCoVatNo(String coVatNo) {
		
		return companyQueryDao.findByCoVatNo(coVatNo);
	}
}
