package com.foya.dao.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.foya.dao.mybatis.model.TbLsLocElec;
import com.foya.noms.dto.pay.TbLsLocElecDTO;

public interface UTbLsLocElecAddedMapper {
	
	/**
	 * 撈取 TB_LS_LOC_ELEC 資料
	 * @param lsNo
	 * 				合約編號
	 * @param locId
	 * 				站點編號
	 *  @param appSql
	 * 				申請流水號
	 * @param addedState
	 * 				暫存狀態
	 * @return
	 */
	List<TbLsLocElecDTO> searchTbLsLocElec(@Param("lsNo") String lsNo,@Param("locId")String locId,@Param("appSql")String appSql,@Param("addedState")String addedState);
	
	/**
	 * query MAx Ver By Ls_No
	 * 
	 * @param lsNo lsNo
	 * @return
	 */
	TbLsLocElec selectTblslocelecMAxVerByLsNo(@Param("lsNo") String lsNo,@Param("locationId")String locationId);

}
