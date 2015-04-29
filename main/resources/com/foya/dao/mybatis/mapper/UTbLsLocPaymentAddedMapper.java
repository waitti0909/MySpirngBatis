package com.foya.dao.mybatis.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.foya.noms.dto.ls.TbLsLocPaymentDTO;

public interface UTbLsLocPaymentAddedMapper {
	/**
	 * 撈取  TbLsLocPaymentAdded 資料
	 * @param lsNo
	 * 			合約編號
	 * @param locId
	 * 			站點編號
	 * @param appSql
	 * 			申請流水號
	 * @param addedState
	 * 			暫存狀態
	 * @return
	 */
	public List<TbLsLocPaymentDTO> searchTbLsLocPaymentAdded(@Param("lsNo") String lsNo, @Param("locId") String locId, @Param("appSql") String appSql, @Param("addedState") String addedState);

	/**
	 * 撈取 付款資訊 資料
	 * @param object 條件值
	 * @return
	 */
	List<TbLsLocPaymentDTO> getLsLocPaymentAddedByLsNoVerLocIdLessorId(HashMap<String,Object> object);
}
