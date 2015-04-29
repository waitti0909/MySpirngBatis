package com.foya.noms.dao.org;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbOrgDeptPosMapper;
import com.foya.dao.mybatis.mapper.UTbOrgDeptPosMapper;
import com.foya.dao.mybatis.model.TbOrgDeptPos;
import com.foya.dao.mybatis.model.TbOrgDeptPosExample;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.org.DeptPosDTO;

@Repository
public class DeptPosDao extends BaseDao {

	@Autowired
	private TbOrgDeptPosMapper tbOrgDeptPosMapper;

	@Autowired
	private UTbOrgDeptPosMapper utbOrgDeptPosMapper;

	/**
	 * 根據部門ID取得部門職稱
	 * 
	 * @param deptId
	 * @return
	 */
	public List<TbOrgDeptPos> getDeptPosByDeptId(String deptId) {
		TbOrgDeptPosExample example = new TbOrgDeptPosExample();
		example.createCriteria().andDEPT_IDEqualTo(deptId);
		return tbOrgDeptPosMapper.selectByExample(example);
	}

	public List<DeptPosDTO> getDeptPosDtoByDeptPosId(String deptPosId) {
		return utbOrgDeptPosMapper.getDeptPosByDeptPosId(deptPosId);
	}

	public List<DeptPosDTO> getDeptPosDtoByDeptId(String deptId) {
		return utbOrgDeptPosMapper.getDeptPosByDeptId(deptId);
	}

	public List<DeptPosDTO> getDeptPosDtoByPosId(String posId) {
		return utbOrgDeptPosMapper.getDeptPosByPosId(posId);
	}

	public List<DeptPosDTO> getDeptPosDtoByDeptIdAndPosId(String deptId, String posId) {
		return utbOrgDeptPosMapper.getDeptPosByDeptIdAndPosId(deptId, posId);
	}

	public List<DeptPosDTO> getDeptAllUnderDeptPosByDeptId(String deptId) {
		return utbOrgDeptPosMapper.getDeptAllUnderDeptPosByDeptId(deptId);
	}

	/**
	 * 用部門ID、職稱ID查詢部門職稱其他欄位
	 * 
	 * @param deptId
	 * @param posId
	 * @return
	 */
	public List<TbOrgDeptPos> getDeptPosByDeptPos(String deptId, String posId) {
		TbOrgDeptPosExample example = new TbOrgDeptPosExample();
		example.createCriteria().andDEPT_IDEqualTo(deptId).andPOS_IDEqualTo(posId);
		return tbOrgDeptPosMapper.selectByExample(example);
	}

	/**
	 * 新增部門職稱並儲存
	 * 
	 * @param dptPos
	 * @return
	 */
	public int saveNewDeptPos(TbOrgDeptPos deptPos) {
		return tbOrgDeptPosMapper.insert(deptPos);
	}

	/**
	 * 刪除部門職稱
	 * 
	 * @param deptPosId
	 * @return
	 */
	public int deleteDeptPosByPrimaryKey(String deptPosId) {
		return tbOrgDeptPosMapper.deleteByPrimaryKey(deptPosId);
	}

	/**
	 * 修改DeptPos
	 * 
	 * @param newDptPos
	 * @param oldDeptPosId
	 * @return
	 */
	public int saveUpdateDeptPos(TbOrgDeptPos newDptPos, String oldDeptPosId) {
		TbOrgDeptPosExample example = new TbOrgDeptPosExample();
		example.createCriteria().andDEPT_POS_IDEqualTo(oldDeptPosId);
		return tbOrgDeptPosMapper.updateByExampleSelective(newDptPos, example);
	}

	/**
	 * 用deptPosId查詢DeptPos其餘欄位
	 * 
	 * @param deptPosId
	 * @return
	 */
	public TbOrgDeptPos serachDeptPosByDeptPosId(String deptPosId) {
		return tbOrgDeptPosMapper.selectByPrimaryKey(deptPosId);
	}
	
	/**
	 * 用deptId查詢該部門下所有員工
	 * @param deptId
	 * @return
	 */
	public List<DeptPosDTO> findMaintainUserByDeptId(String deptId) {
		return utbOrgDeptPosMapper.selectMaintainUserByDeptId(deptId);
	}
}
