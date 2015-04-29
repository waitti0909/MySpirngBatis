package com.foya.noms.dao.system;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbSysSignLogMapper;
import com.foya.dao.mybatis.mapper.UTbSysSignLogMapper;
import com.foya.dao.mybatis.model.TbSysSignLog;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.system.SignLogDTO;

@Repository
public class SignLogDao extends BaseDao {

	@Autowired
	private TbSysSignLogMapper tbSysSignLogMapper;
	@Autowired
	private UTbSysSignLogMapper uTbSysSignLogMapper;
	
	/**
	 * 新增
	 * @param record
	 * @return
	 */
	public int insert(TbSysSignLog record){
		return tbSysSignLogMapper.insert(record);
	}
	
	/**
	 * 根據簽核類型與單號查詢簽核紀錄
	 * @return
	 */
	public List<SignLogDTO> getSignHistory(Collection<String> processTypes, String applyNo){
		return uTbSysSignLogMapper.selectSignHistory(new ArrayList<String>(processTypes), applyNo);
	}
}
