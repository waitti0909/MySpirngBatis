package com.foya.noms.dao.auth;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbAuthPersonnelMapper;
import com.foya.dao.mybatis.mapper.UTbAuthMenuMapper;
import com.foya.dao.mybatis.mapper.UTbAuthPersonnelMapper;
import com.foya.dao.mybatis.mapper.UTbOrgDeptMapper;
import com.foya.dao.mybatis.model.TbAuthMenu;
import com.foya.dao.mybatis.model.TbAuthPersonnel;
import com.foya.dao.mybatis.model.TbAuthPersonnelExample;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.auth.PersonnelDTO;
import com.foya.noms.dto.auth.UserDTO;
import com.foya.noms.dto.org.DeptDTO;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Repository
public class PersonnelDao extends BaseDao {

	@Autowired
	private UTbAuthPersonnelMapper uTbAuthPersonnelMapper;

	@Autowired
	private TbAuthPersonnelMapper tbAuthPersonnelMapper;
	
	@Autowired
	private UTbOrgDeptMapper uTbOrgDeptMapper;
	
	@Autowired
	private UTbAuthMenuMapper uTbAuthMenuMapper;
	
	/**
	 * 查詢所有使用者
	 * 
	 * @return
	 */
	public List<TbAuthPersonnel> selectAllPsn() {
		TbAuthPersonnelExample example = new TbAuthPersonnelExample();
		example.createCriteria().andDISMISSIONEqualTo("N");
		return tbAuthPersonnelMapper.selectByExample(example);
//		return uTbAuthPersonnelMapper.selectAllPsn();
	}

	/**
	 * 查詢Personnel
	 * @param example
	 * @return
	 */
	public List<TbAuthPersonnel> getPersonnelsByExample(TbAuthPersonnelExample example) {
		return tbAuthPersonnelMapper.selectByExample(example);

	}

	/**
	 * Import Excel to insert DB
	 */
	public void insertComputer(TbAuthPersonnel record) {
		tbAuthPersonnelMapper.insertSelective(record);
	}

	/**
	 * 用 psn id 找到部門名稱
	 * 
	 * @param psnId
	 * @return PersonnelDTO
	 */
	public PersonnelDTO getPsnDeptById(Integer psnId) {
		return uTbAuthPersonnelMapper.getPsnDeptById(psnId);
	}

	/**
	 * 用psn mo, chn name, email 查詢 psn
	 * 
	 * @param psnNo
	 * @param chnName
	 * @param email
	 * @return list of TbAuthPersonnel
	 */
	public List<TbAuthPersonnel> searchPsnByNoChnNameEmail(String psnNo, String chnName,
			String email) {
		Map<String, Object> psnObj = new HashMap<String, Object>();
		psnObj.put("psnNo", psnNo);
		psnObj.put("chnName", chnName);
		psnObj.put("email", email);
		PageBounds pageBounds = getPageBounds("PSN_ID");
		List<TbAuthPersonnel> list = this.sqlSession.selectList(
				"com.foya.dao.mybatis.mapper.UTbAuthPersonnelMapper.searchPsnByNoChnNameEmail",
				psnObj, pageBounds);
		PageList<TbAuthPersonnel> pageList = (PageList<TbAuthPersonnel>) list;
		return pageList;
	}

	/**
	 * 用 psn no 查詢 psn
	 * 
	 * @param psnNo
	 * @return list of TbAuthPersonnel
	 */
	public List<TbAuthPersonnel> searchPsnsByPsnNo(String psnNo) {
		Map<String, Object> psnObj = new HashMap<String, Object>();
		psnObj.put("psnNo", psnNo);
		PageBounds pageBounds = getPageBounds();
		List<TbAuthPersonnel> list = this.sqlSession.selectList(
				"com.foya.dao.mybatis.mapper.UTbAuthPersonnelMapper.searchPsnsByPsnNo", psnObj,
				pageBounds);
		PageList<TbAuthPersonnel> pageList = (PageList<TbAuthPersonnel>) list;
		return pageList;
	}

	/**
	 * 用 psn no 查詢 psn
	 * 
	 * @param psnNo
	 * @return TbAuthPersonnel
	 */
	public TbAuthPersonnel searchByPsnNo(String psnNo) {
		TbAuthPersonnelExample example = new TbAuthPersonnelExample();
		example.createCriteria().andPSN_NOEqualTo(psnNo);
		List<TbAuthPersonnel> list = getPersonnelsByExample(example);
		if (list == null || list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	/**
	 * 根據 psn no 查詢用戶
	 * 
	 * @param psnNo
	 * @return
	 */
	public UserDTO getUserDtoByPsnNo(String psnNo) {
		return uTbAuthPersonnelMapper.getUserDtoByPsnNo(psnNo);
	}

	/**
	 * 根據 list of psn no 查詢用戶
	 * 
	 * @param psnNo
	 * @return list of UserDTO
	 */
	public List<UserDTO> getUserDtoByPsnNos(Collection<String> psnNos) {
		return uTbAuthPersonnelMapper.getUserDtoByPsnNos(new ArrayList<String>(psnNos));
	}

	/**
	 * 根據 dept pos id 查詢用戶
	 * 
	 * @param deptPosId
	 * @return list of UserDTO
	 */
	public List<UserDTO> getUserDtoByDeptPosId(String deptPosId) {
		return uTbAuthPersonnelMapper.getUserDtoByDeptPosId(deptPosId);
	}

	/**
	 * 根據登入且到職生效帳號查詢用戶
	 * 
	 * @param psnNo 帳號
	 * @return UserDTO
	 */
	public UserDTO selectLoginUserByPsnNo(String psnNo, Date date) {
		return uTbAuthPersonnelMapper.selectLoginUserByPsnNo(psnNo, date);
	}

	/**
	 * 根據 dept id 取psn
	 * 
	 * @param example
	 * @return
	 */
	public List<TbAuthPersonnel> selectPersonnelByDeptId(String deptId) {
		TbAuthPersonnelExample example = new TbAuthPersonnelExample();
		example.createCriteria().andDEPT_IDEqualTo(deptId);
		example.createCriteria().andEXPIRE_DATEIsNotNull();
		return tbAuthPersonnelMapper.selectByExample(example);
	}

	/**
	 * 透過 Domain 查詢 psn_no、chn_nm
	 * 
	 * @param Domain
	 * @return list of TbAuthPersonnel
	 */
	public List<TbAuthPersonnel> selectAppUserByDomain(String domain) {
		return uTbAuthPersonnelMapper.selectAppUserByDomain(domain);
	}
	
	
	/**
	 * 透過 Domain 查詢 psn_no、chn_nm
	 * 
	 * @param Domain
	 * @return list of TbAuthPersonnel
	 */
	public List<TbAuthPersonnel> selectOnJobUserByDomain(List<String> domainList) {
		return uTbAuthPersonnelMapper.selectOnJobUserByDomain(domainList);
	}
	
	
	
	
	/**
	 * 根據 dept id 更新 psn 的部門
	 * 
	 * @param deptId
	 */
	public void updatePsnDeptByDeptId(String mdUser, Date mdTime, String deptId,
			String primaryDeptPosId, String editdeptID) {
		uTbAuthPersonnelMapper.updatePsnDeptByDeptId(mdUser, mdTime, deptId, primaryDeptPosId,
				editdeptID);
	}

	/**
	 * 透過 dept id 模糊查詢 psn
	 * 
	 * @param deptId
	 * @return list of TbAuthPersonnel
	 */
	public List<TbAuthPersonnel> selectByDeptIdLike(String deptId) {
		return uTbAuthPersonnelMapper.selectByDeptIdLike(deptId);
	}

	/**
	 * 根據dept id 找到派工負責人 姓名
	 * 
	 * @param deptId
	 * @return list of TbAuthPersonnel
	 */
	public List<TbAuthPersonnel> selectByDeptId(String deptId) {
		return uTbAuthPersonnelMapper.selectByDeptId(deptId);
	}

	/**
	 * 用 psn Id 查詢 Personnel
	 * 
	 * @param psnId
	 * @return TbAuthPersonnel
	 */
	public TbAuthPersonnel selectPersonnelByPsnId(BigDecimal psnId) {
		return tbAuthPersonnelMapper.selectByPrimaryKey(psnId);
	}
	
	/**
	 * 根據vatNO查詢Personnel
	 * @param vatNo
	 * @return
	 */
	public List<PersonnelDTO> searchPsnByVatNo(String vatNo){
		return uTbAuthPersonnelMapper.searchPsnByVatNo(vatNo);
	}
	
	public UserDTO selectLoginUserByEName(String eName, Date date) {
		return uTbAuthPersonnelMapper.selectLoginUserByEName(eName, date);
	}
	
	/**
	 * 根據查詢條件查詢Personnel(使用者維護)
	 * [PSN_TYPE='E'and (CO_VAT_NO is null or CO_VAT_NO='')]
	 * @param psnNo
	 * @param chnName
	 * @param email
	 * @return
	 */
	public List<PersonnelDTO> searchPsnByCondition(String psnNO, String engNM,
			String email) {
		Map<String, Object> psnObj = new HashMap<String, Object>();
		psnObj.put("psnNO", psnNO);
		psnObj.put("engNM", engNM);
		psnObj.put("email", email);
		PageBounds pageBounds = getPageBounds("PSN_ID");
		List<PersonnelDTO> list = this.sqlSession.selectList(
				"com.foya.dao.mybatis.mapper.UTbAuthPersonnelMapper.searchPsnByCondition",
				psnObj, pageBounds);
		PageList<PersonnelDTO> pageList = (PageList<PersonnelDTO>) list;
		return pageList;
	}
	

	/**
	 * 使用者帳號新增
	 * @param record
	 */
	public void saveNewPersonnel(TbAuthPersonnel record){
		tbAuthPersonnelMapper.insertSelective(record);
	}
	
	/**
	 * 使用者帳號修改
	 * @param record
	 */
	public void saveUpdatePersonnelByPK(TbAuthPersonnel record){
		tbAuthPersonnelMapper.updateByPrimaryKey(record);
	}
	
	/**
	 * 用部門ID查詢POS_TYPE='N'相對應的職務
	 * 
	 * @param deptId
	 * @return DeptDTO list
	 */
	public List<DeptDTO> selecPosByDept(String deptId) {
		return uTbOrgDeptMapper.selecPosByDept(deptId);
	}
	
	
	/**
	 * 查詢登入者所擁有的權限
	 * 
	 * @param psnId
	 *            登入者ID
	 * @return List<TbAuthMenu>
	 */
	public List<TbAuthMenu> getLimitsOfAuthority(int psnId){
		
		return uTbAuthMenuMapper.getLimitsOfAuthority(psnId);
	}
	
	/**
	 * 取得部門下所有職稱之人員
	 * @param deptID
	 * @return
	 */
	public List<PersonnelDTO> getDeptPersonnels(String deptID){
		return uTbAuthPersonnelMapper.getDeptPersonnels(deptID);
	}
}
