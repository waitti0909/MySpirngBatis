package com.foya.noms.dao.org;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbOrgPsnPosMapper;
import com.foya.dao.mybatis.model.TbOrgPsnPos;
import com.foya.dao.mybatis.model.TbOrgPsnPosExample;
import com.foya.dao.mybatis.model.TbOrgPsnPosKey;
import com.foya.noms.dao.BaseDao;

@Repository
public class PsnPosDao extends BaseDao {
	@Autowired
	private TbOrgPsnPosMapper tbOrgPsnPosMapper;

	/**
	 * 用PsnPosId 找到 PsnNo
	 */
	public List<TbOrgPsnPos> selectPsnNoList(String deptPosId) {
		TbOrgPsnPosExample example = new TbOrgPsnPosExample();
		example.createCriteria().andDEPT_POS_IDEqualTo(deptPosId);
		return tbOrgPsnPosMapper.selectByExample(example);
	}

	/**
	 * 刪除org_psn_pos
	 * 
	 * @param example
	 */
	public void deletePsnPosByDeptId(TbOrgPsnPosExample example) {
		tbOrgPsnPosMapper.deleteByExample(example);
	}
	
	/**
	 * 新增org_psn_pos
	 * @param record
	 */
	public void insertOrgPsnPos(TbOrgPsnPos record){
		tbOrgPsnPosMapper.insert(record);
	}
	
	/**
	 * 根據example查詢org_psn_pos
	 * @param key
	 * @return
	 */
	public List<TbOrgPsnPos> selectByExample(TbOrgPsnPosExample example){
		return tbOrgPsnPosMapper.selectByExample(example);
	}
	
	/**
	 * 更新org_psn_pos
	 * @param record
	 * @param example
	 */
	public void updateByExample(TbOrgPsnPos record, TbOrgPsnPosExample example){
		tbOrgPsnPosMapper.updateByExample(record,example);
	}
	
	public void deleteByPrimaryKey(TbOrgPsnPosKey key){
		tbOrgPsnPosMapper.deleteByPrimaryKey(key);
	}
	

}
