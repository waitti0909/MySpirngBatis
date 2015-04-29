package com.foya.noms.service.basic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foya.dao.mybatis.model.TbAuthPersonnel;
import com.foya.dao.mybatis.model.TbAuthPersonnelExample;
import com.foya.dao.mybatis.model.TbComCompany;
import com.foya.dao.mybatis.model.TbComCompanyExample;
import com.foya.exception.DataExistsException;
import com.foya.exception.NomsException;
import com.foya.noms.dao.auth.PersonnelDao;
import com.foya.noms.dao.basic.CM001Dao;
import com.foya.noms.dto.auth.PersonnelDTO;
import com.foya.noms.service.auth.PersonnelService;

@Service
public class CM001Service {
	
	@Autowired
	private CM001Dao cM001Dao;
	
	@Autowired
	private PersonnelDao personnelDao;
	
	@Autowired
	private PersonnelService personnelService;
	
	/**
	 * 根據條件查詢廠商
	 * @param record
	 * @return
	 */
	public List<TbComCompany> searchCompanyByVatName(String ubnNo, String name, String[] type) {
		return cM001Dao.searchCompanyByVatName(ubnNo,name,type);
	}
	
	/**
	 * 查詢廠商
	 * @param example
	 * @return
	 */
	public List<TbComCompany> searchCompanyByExample(TbComCompanyExample example){
		return cM001Dao.selectByExample(example);
	}
	
	/**
	 * 儲存_廠商新增
	 * @param company
	 * @return
	 */
	public void saveNewCompany(TbComCompany company) throws DataExistsException {	
		String vatNo = company.getCO_UBN_NO();
		TbComCompanyExample example = new TbComCompanyExample();
		example.createCriteria().andCO_UBN_NOEqualTo(vatNo);
		List<TbComCompany> isDup = cM001Dao.selectByExample(example);
		//統編是否重複
		if(isDup.size()!=0){
			throw new NomsException("無法新增，統編已重複!");
		}else{
			cM001Dao.saveNewCompany(company);
		}
	}
	
	/**
	 * 根據廠商編號取CompanyDetail
	 * @param vatNo
	 * @return
	 */
	public TbComCompany getCompanyDetailByID(String vatNo){
		return cM001Dao.getCompanyDetailByID(vatNo);
	}
	
	/**
	 * 儲存_廠商修改
	 * @param company
	 */
	public void saveUpdateCompany(TbComCompany company){
		cM001Dao.saveUpdateCompany(company);
	}

	/**
	 * 廠商帳號新增
	 * @param companyAccount
	 * @throws DataExistsException
	 */
	public void addCompanyAccount(PersonnelDTO companyAccount) throws DataExistsException{		
		String engNm = companyAccount.getENG_NM();
		TbAuthPersonnelExample example = new TbAuthPersonnelExample();
		example.createCriteria().andENG_NMEqualTo(engNm);
		List<TbAuthPersonnel> isDup = personnelDao.getPersonnelsByExample(example);
		//統編是否重複
		if(isDup.size()>0){
			throw new NomsException("無法新增，帳號已重複!");
		}else{
			
			companyAccount.setPSN_TYPE("V");
			companyAccount.setDISMISSION("N");
			cM001Dao.saveNewCompanyAccount(companyAccount);
		}
	}
	
	/**
	 * 廠商帳號編輯
	 * @param companyAccount
	 */
	public void editCompanyAccount(PersonnelDTO companyAccount){
		cM001Dao.saveUpdateCompanyAccount(companyAccount);
	}
}
