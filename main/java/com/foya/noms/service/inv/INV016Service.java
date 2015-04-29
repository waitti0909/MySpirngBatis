package com.foya.noms.service.inv;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foya.dao.mybatis.model.TbAuthPersonnel;
import com.foya.dao.mybatis.model.TbComCompany;
import com.foya.dao.mybatis.model.TbComCompanyExample;
import com.foya.dao.mybatis.model.UTbInvWarehouseExample;
import com.foya.dao.mybatis.model.TbOrgDept;
import com.foya.dao.mybatis.model.TbOrgDeptExample;
import com.foya.dao.mybatis.model.TbOrgDomain;
import com.foya.dao.mybatis.model.TbOrgDomainExample;
import com.foya.noms.dao.auth.PersonnelDao;
import com.foya.noms.dao.inv.INV016Dao;
import com.foya.noms.dao.org.DeptDao;
import com.foya.noms.dao.org.DomainDao;
import com.foya.noms.dto.inv.TbInvWarehouseDTO;
import com.foya.noms.service.BaseService;

@Service
public class INV016Service extends BaseService {

	@Autowired
	private INV016Dao inv016Dao;
	@Autowired
	private DomainDao domainDao;
	@Autowired
	private DeptDao deptDao;
	@Autowired
	private PersonnelDao personnelDao;

	/**
	 * 取得倉庫清單
	 * @param example
	 * @return
	 */
	public List<TbInvWarehouseDTO> selectInvWarehouseByExample(UTbInvWarehouseExample example) {
		return inv016Dao.selectInvWarehouseByExample(example);
	}

	/**
	 * 取得所有區域
	 * 
	 * @return
	 */
	public List<TbOrgDomain> selectAllOrgDomain() {
		return domainDao.selectOrgDomain(new TbOrgDomainExample());
	}

	/**
	 * 取得所有管理單位
	 * 
	 * @param example
	 * @return
	 */
	public List<TbOrgDept> selectAllOrgDept(TbOrgDeptExample example) {
		return deptDao.selectAllOrgDept(example);
	}

	/**
	 * 查詢倉庫資料
	 * 
	 * @param example
	 * @return
	 */
	public List<TbInvWarehouseDTO> selectWarehouse(UTbInvWarehouseExample example) {
		return inv016Dao.selectWarehouse(example);
	}

	/**
	 * 取得倉庫資料by key
	 * @param wh_id
	 * @return
	 */
	public TbInvWarehouseDTO selectWarehouseByPrimaryKey(String wh_id) {
		return inv016Dao.selectWarehouseByPrimaryKey(wh_id);
	}

	/**
	 * 新增倉庫資料
	 * @param record
	 * @return
	 */
	public int insertInvWarehouseSelective(TbInvWarehouseDTO record) {
		return inv016Dao.insertInvWarehouseSelective(record);
	}

	/**
	 * 修改倉庫資料
	 * @param record
	 * @return
	 */
	public int updateByPrimaryKeySelective(TbInvWarehouseDTO record) {
		return inv016Dao.updateByPrimaryKeySelective(record);
	}

	/**
	 * 取得廠商清單
	 * @param example
	 * @return
	 */
	public List<TbComCompany> selectComCompanyByExample(TbComCompanyExample example) {
		return inv016Dao.selectComCompanyByExample(example);
	}

	/**
	 * 取得保管人
	 * @param deptId
	 * @return
	 */
	public List<TbAuthPersonnel> selectPersonnelByDeptId(String deptId) {
		return personnelDao.selectPersonnelByDeptId(deptId);
	}

	/**
	 * 取得資料筆數
	 * @param example
	 * @return
	 */
	public int countInvWarehouseByExample(UTbInvWarehouseExample example) {
		return inv016Dao.countInvWarehouseByExample(example);
	}
}
