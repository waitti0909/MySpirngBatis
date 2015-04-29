package com.foya.noms.dao.inv;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.UTbInvInvMapper;
import com.foya.dao.mybatis.mapper.UTbInvSrlMapper;
import com.foya.dao.mybatis.mapper.UTbInvWarehouseMapper;
import com.foya.dao.mybatis.model.UTbInvInvExample;
import com.foya.dao.mybatis.model.UTbInvSrlExample;
import com.foya.dao.mybatis.model.UTbInvWarehouseExample;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.inv.TbInvInvDTO;
import com.foya.noms.dto.inv.TbInvSrlDTO;
import com.foya.noms.dto.inv.TbInvWarehouseDTO;


@Repository
public class INV019Dao extends BaseDao {
	
	@Autowired
	private UTbInvWarehouseMapper uTbInvWarehouseMapper;
	@Autowired
	private UTbInvInvMapper uTbInvInvMapper;	
	@Autowired
	private UTbInvSrlMapper uTbInvSrlMapper;
	
	/**
	 * 取得倉庫清單
	 * @param example
	 * @return
	 */
	public List<TbInvWarehouseDTO> selectInvWarehouseByExample(UTbInvWarehouseExample example) {
		return uTbInvWarehouseMapper.selectByExample(example);
	}
	
	/**
	 * 查詢庫存
	 * @param example
	 * @return
	 */
	public List<TbInvInvDTO> selectInvInvBy019(UTbInvInvExample example) {
		return uTbInvInvMapper.selectForINV019(example);		
	}
	
	/**
	 * 查詢序號資料
	 * @param example
	 * @return
	 */
	public List<TbInvSrlDTO> selectInvSrlByExample(UTbInvSrlExample example) {
		return uTbInvSrlMapper.selectByExample(example);
	}

}
