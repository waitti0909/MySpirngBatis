package com.foya.noms.dao.org;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbOrgDeptMapper;
import com.foya.dao.mybatis.mapper.UTbOrgDeptMapper;
import com.foya.dao.mybatis.model.TbOrgDept;
import com.foya.dao.mybatis.model.TbOrgDeptExample;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.org.DeptDTO;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Repository
public class DeptDao extends BaseDao {

	@Autowired
	private TbOrgDeptMapper tbOrgDeptMapper;

	@Autowired
	private UTbOrgDeptMapper uTbOrgDeptMapper;

	public TbOrgDept findByPk(String deptId) {
		return tbOrgDeptMapper.selectByPrimaryKey(deptId);
	}

	public List<TbOrgDept> findByCondition(TbOrgDeptExample example) {
		return tbOrgDeptMapper.selectByExample(example);
	}

	/**
	 * 用部門ID查詢相對應的職務
	 * 
	 * @param deptId
	 * @return DeptDTO list
	 */
	public List<DeptDTO> searchPositionByDeptId(String deptId) {
		return uTbOrgDeptMapper.searchPositionByDeptId(deptId);
	}

	/**
	 * 取得所有部門
	 * 
	 * @return TbOrgDept list
	 */
	public List<TbOrgDept> selectAllOrgDept(TbOrgDeptExample example) {
		return tbOrgDeptMapper.selectByExample(example);
	}

	/**
	 * 根據ID取得部門Info
	 * 
	 * @return TbOrgDept
	 */
	public TbOrgDept selectOrgDeptById(String deptId) {
		return tbOrgDeptMapper.selectByPrimaryKey(deptId);
	}

	/**
	 * 查詢預新增之資料是否已存於DB中
	 * 
	 * @param vatNo
	 * @return TbOrgDept
	 */
	public TbOrgDept selectByPrimaryKey(String deptId) {
		return tbOrgDeptMapper.selectByPrimaryKey(deptId);
	}

	/**
	 * 取得子部門
	 * 
	 * @param deptId
	 * @return TbOrgDept list
	 */
	public List<TbOrgDept> getChildDeptsById(String deptId) {
		TbOrgDeptExample example = new TbOrgDeptExample();
		example.createCriteria().andPARENT_DEPT_IDEqualTo(deptId);
		return tbOrgDeptMapper.selectByExample(example);
	}

	/**
	 * 依照deptId查詢部門
	 * 
	 * @param deptId
	 * @return TbOrgDept
	 */
	public TbOrgDept getDeptById(String deptId) {
		return tbOrgDeptMapper.selectByPrimaryKey(deptId);
	}

	/**
	 * 取得所有部門Tree
	 * 
	 * @return DeptDTO list
	 */
	public List<DeptDTO> getDeptTree() {
		return uTbOrgDeptMapper.getDeptTree();
	}

	/**
	 * 取得所有上層部門
	 * 
	 * @param deptId
	 * @return DeptDTO list
	 */
	public List<DeptDTO> searchParentByChild(String deptId) {
		return uTbOrgDeptMapper.searchParentByChild(deptId);
	}

	/**
	 * 用id和name查詢Dept其餘欄位
	 * 
	 * @param deptId
	 * @param deptName
	 * @return
	 */
	public List<DeptDTO> searchDeptByIdName(String deptId, String deptName) {
		Map<String, String> deptObj = new HashMap<String, String>();
		deptObj.put("deptId", deptId);
		deptObj.put("deptName", deptName);
		PageBounds pageBounds = getPageBounds("DEPT_ID");
		List<DeptDTO> list = this.sqlSession.selectList(
				"com.foya.dao.mybatis.mapper.UTbOrgDeptMapper.searchDeptByIdName", deptObj,
				pageBounds);
		PageList<DeptDTO> pageList = (PageList<DeptDTO>) list;
		return pageList;
	}

	/**
	 * 依照DeptPosId修改DeptManager
	 * 
	 * @param oldDeptPosId
	 * @param newDeptPosId
	 * @return update success count
	 */
	public int updateDeptManagerByDeptPosId(String oldDeptPosId, String newDeptPosId) {
		TbOrgDept dept = new TbOrgDept();
		dept.setMANAGER(newDeptPosId);
		TbOrgDeptExample example = new TbOrgDeptExample();
		example.createCriteria().andMANAGEREqualTo(oldDeptPosId);
		return tbOrgDeptMapper.updateByExampleSelective(dept, example);
	}

	public int updateDept(TbOrgDept record, TbOrgDeptExample example) {
		return tbOrgDeptMapper.updateByExampleSelective(record, example);
	}

	/**
	 * 新增
	 * 
	 * @param record
	 */
	public void saveNewDept(TbOrgDept orgDept) {
		tbOrgDeptMapper.insertSelective(orgDept);
	}

	/**
	 * 修改
	 * 
	 * @param orgDept
	 */
	public void saveUpdateDept(TbOrgDept orgDept) {
		// tbOrgDeptMapper.updateByPrimaryKeySelective(orgDept);
		tbOrgDeptMapper.updateByPrimaryKey(orgDept);
	}

	/**
	 * 修改部門主管
	 * 
	 * @param deptId
	 * @param dpetPosId
	 * @return save or update success count
	 */
	public int saveUpdateDeptManager(String deptId, String dpetPosId) {
		return uTbOrgDeptMapper.updateByPrimaryKeySelective(deptId, dpetPosId);
	}

	/**
	 * 刪除Org_Dept
	 * 
	 * @param deptId
	 */
	public void deleteDeptById(String deptId) {
		tbOrgDeptMapper.deleteByPrimaryKey(deptId);
	}
	
	/**
	 *取得可使用的區域清單
	 *@input 使用者部門所屬區域
	 * 
	 * @return List<TbOrgDomain>
	 */
	public List<TbOrgDept> selectDeptByDomainList(List<String> domainList){
		return uTbOrgDeptMapper.selectDeptByDomainList(domainList);
		
	}

}
