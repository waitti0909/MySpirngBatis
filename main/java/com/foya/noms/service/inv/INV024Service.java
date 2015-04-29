package com.foya.noms.service.inv;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foya.dao.mybatis.model.UTbInvBookingExample;
import com.foya.dao.mybatis.model.UTbInvInvExample;
import com.foya.dao.mybatis.model.UTbInvWarehouseExample;
import com.foya.noms.dao.inv.INV024Dao;
import com.foya.noms.dto.inv.TbInvBookingDTO;
import com.foya.noms.dto.inv.TbInvInvDTO;
import com.foya.noms.dto.inv.TbInvWarehouseDTO;
import com.foya.noms.service.BaseService;

@Service
public class INV024Service extends BaseService {

	@Autowired
	private INV024Dao inv024Dao;

	/**
	 * 取得倉庫清單
	 * @param example
	 * @return
	 */
	public List<TbInvWarehouseDTO> selectInvWarehouseByExample(UTbInvWarehouseExample example) {
		return inv024Dao.selectInvWarehouseByExample(example);
	}

	/**
	 * 取得Booking主檔資料
	 * 
	 * @param example
	 * @return
	 */
	public List<TbInvInvDTO> selectInvInvByExampleAndGroupBy(UTbInvInvExample example) {
		return inv024Dao.selectInvInvByExampleAndGroupBy(example);
	}

	/**
	 * 取得Booking明細資料
	 * 
	 * @param example
	 * @return
	 */
	public List<TbInvBookingDTO> selectInvBookingByExample(UTbInvBookingExample example) {
		return inv024Dao.selectInvBookingByExample(example);
	}
}
