package com.foya.noms.dao.org;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbAuthPersonnelMapper;
import com.foya.dao.mybatis.mapper.TbOrgPsnPosMapper;
import com.foya.dao.mybatis.mapper.UTbAuthPersonnelMapper;
import com.foya.dao.mybatis.mapper.UTbOrgPsnPosMapper;
import com.foya.dao.mybatis.model.TbOrgPsnPos;
import com.foya.dao.mybatis.model.TbOrgPsnPosExample;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.org.PsnPosDTO;

@Repository
public class ORG002Dao extends BaseDao {

	@Autowired
	private TbOrgPsnPosMapper tbOrgPsnPosMapper;

	@Autowired
	private UTbAuthPersonnelMapper uTbAuthPersonnelMapper;

	@Autowired
	private TbAuthPersonnelMapper tbAuthPersonnelMapper;

	@Autowired
	private UTbOrgPsnPosMapper uTbOrgPsnPosMapper;

	/**
	 * 刪除部門職稱
	 * 
	 * @param deptPosId
	 * @return
	 */
	public int deletePsnPosByDeptPosId(String deptPosId) {
		TbOrgPsnPosExample example = new TbOrgPsnPosExample();
		example.createCriteria().andDEPT_POS_IDEqualTo(deptPosId);
		return tbOrgPsnPosMapper.deleteByExample(example);
	}

	/**
	 * 依照DeptPosId修改PsnPos
	 * 
	 * @param newDeptPosId
	 * @param oldDeptPosId
	 * @return
	 */
	public int updatePsnPosByDeptPosId(String oldDeptPosId, String newDeptPosId) {
		TbOrgPsnPosExample example = new TbOrgPsnPosExample();
		example.createCriteria().andDEPT_POS_IDEqualTo(oldDeptPosId);
		TbOrgPsnPos psnPos = new TbOrgPsnPos();
		psnPos.setDEPT_POS_ID(newDeptPosId);
		return tbOrgPsnPosMapper.updateByExampleSelective(psnPos, example);
	}

	/**
	 * 依照DeptPosId修改PsnPrimaryJob
	 * 
	 * @param oldDeptPosId
	 * @param newDeptPosId
	 * @return
	 */
	public int updatePsnPrimaryJobByDeptPosId(String oldDeptPosId, String newDeptPosId) {
		return uTbAuthPersonnelMapper.updatePsnPrimaryJobByDeptPosId(oldDeptPosId, newDeptPosId);
	}

	/**
	 * 依照deptPosId查詢psnPos其餘欄位
	 * 
	 * @param deptPosId
	 * @return
	 */
	public List<TbOrgPsnPos> getPosPersonByDeptPosId(String deptPosId) {
		TbOrgPsnPosExample example = new TbOrgPsnPosExample();
		example.createCriteria().andDEPT_POS_IDEqualTo(deptPosId);
		return tbOrgPsnPosMapper.selectByExample(example);
	}

	/**
	 * 用deptPosId查詢Personnel
	 * 
	 * @param deptPosId
	 * @return
	 */
	public List<PsnPosDTO> getPsnPosDTOByDeptPosId(String deptPosId) {
		return uTbOrgPsnPosMapper.getPsnPosDTOByDeptPosId(deptPosId);
	}

	/**
	 * 用deptPosId查詢Personnel
	 * 
	 * @param deptPosId
	 * @return
	 */
	public List<TbOrgPsnPos> getPsnPosByDeptPosId(String deptPosId) {
		TbOrgPsnPosExample example = new TbOrgPsnPosExample();
		example.createCriteria().andDEPT_POS_IDEqualTo(deptPosId);
		return tbOrgPsnPosMapper.selectByExample(example);
	}

	/**
	 * 新增psnPos
	 * 
	 * @param psnPos
	 * @return
	 */
	public int saveNewPsnPos(TbOrgPsnPos psnPos) {
		return tbOrgPsnPosMapper.insertSelective(psnPos);
	}

	/**
	 * 刪除PosPsn
	 * 
	 * @param deptPosId
	 * @param psnNo
	 * @return
	 */
	public int deletePosPsnByPrimary(String deptPosId, String[] psnNo) {
		List<String> psnNoList = new ArrayList<String>();
		CollectionUtils.addAll(psnNoList, psnNo);
		TbOrgPsnPosExample example = new TbOrgPsnPosExample();
		example.createCriteria().andPSN_NOIn(psnNoList).andDEPT_POS_IDEqualTo(deptPosId);
		return tbOrgPsnPosMapper.deleteByExample(example);
	}

	/**
	 * 更新主要職務
	 * 
	 * @param deptPosId
	 * @param posNo
	 * @return
	 */
	public int updatePrimaryJobByDeptPosIdPSNNo(String deptPosId, String psnNo,
			String primaryDeptPosId) {
		String dept_id = null;
		String jobTitle = null;
		if(deptPosId!=null && deptPosId.indexOf("__")>-1){
			String[] dept_pos = deptPosId.split("__");
			dept_id= dept_pos[0];
			jobTitle= dept_pos[1];
		}
		return uTbAuthPersonnelMapper.updatePrimaryJobByDeptPosIdPSNNo(dept_id,deptPosId, psnNo,jobTitle,
				primaryDeptPosId);
	}

}