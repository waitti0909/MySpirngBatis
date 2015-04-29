package com.foya.noms.service.inv;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foya.dao.mybatis.model.TbInvSnCor;
import com.foya.dao.mybatis.model.UTbInvSrlExample;
import com.foya.dao.mybatis.model.UTbInvWarehouseExample;
import com.foya.noms.dao.inv.INV017Dao;
import com.foya.noms.dto.inv.TbInvSrlDTO;
import com.foya.noms.dto.inv.TbInvWarehouseDTO;
import com.foya.noms.service.BaseService;

@Service
public class INV017Service extends BaseService {

	@Autowired
	private INV017Dao inv017Dao;

	/**
	 * 取得所有倉庫清單
	 * @param example
	 * @return
	 */
	public List<TbInvWarehouseDTO> selectInvWarehouseByExample(UTbInvWarehouseExample example) {
		return inv017Dao.selectInvWarehouseByExample(example);
	}

	/**
	 * 查詢序號資料
	 * @param example
	 * @return
	 */
	public List<TbInvSrlDTO> selectInvSrlByExample(UTbInvSrlExample example) {
		return inv017Dao.selectInvSrlByExample(example);
	}

	/**
	 * 查詢序號資料筆數
	 * @param example
	 * @return
	 */
	public int countInvSrlByExample(UTbInvSrlExample example) {
		return inv017Dao.countInvSrlByExample(example);
	}

	/**
	 * 查詢序號資料by key
	 * @param srl_no
	 * @return
	 */
	public TbInvSrlDTO selectInvSrlByPrimaryKey(Long srl_no) {
		return inv017Dao.selectInvSrlByPrimaryKey(srl_no);
	}

	/**
	 * 更新序號資料by key
	 * @param record
	 * @return
	 */
	public int updateInvSrlByPrimaryKeySelective(TbInvSrlDTO record) {
		return inv017Dao.updateInvSrlByPrimaryKeySelective(record);
	}

	/**
	 * 新增序號更正紀錄
	 * @param record
	 * @return
	 */
	public int insertInvSnCorSelective(TbInvSnCor record) {
		return inv017Dao.insertInvSnCorSelective(record);
	}
}
