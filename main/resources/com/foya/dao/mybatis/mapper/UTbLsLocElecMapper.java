package com.foya.dao.mybatis.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.foya.dao.mybatis.model.TbLsLocElec;
import com.foya.noms.dto.pay.TbLsLocElecDTO;

public interface UTbLsLocElecMapper {
	
	/**
	 * 查租約編號
	 * 
	 * @return
	 */
	List<TbLsLocElec> getLsNoList();
	
	/**
	 * Pay002 依電錶號碼取得租約編號
	 * 
	 * @param String
	 *            查詢條件
	 * 
	 * @return TbLsLocElec
	 */
	List<TbLsLocElec> getContractNo(@Param("electricityMeterNbr") String electricityMeterNbr);
	
	/**
	 * 根據租約編號查電錶號碼
	 * 
	 * @return
	 */
	List<TbLsLocElec> getEnergyMeterList(@Param(value = "contractNo") String contractNo);
	
	/**
	 * Pay002 取得租約每度金額
	 * 
	 * @param dataObj
	 *            查詢條件
	 * 
	 * @return TbLsLocElecDTO
	 */
	TbLsLocElecDTO getChrgMode(HashMap<String, Object> dataObj);
	
	/**
	 * Pay002 location_id
	 * 
	 * @param dataObj
	 *            查詢條件
	 * 
	 * @return TbLsLocElecDTO
	 */
	TbLsLocElecDTO getLocationId(HashMap<String, Object> dataObj);
	
	/**
	 * Pay011 查詢基站編號下拉選單資料
	 * 
	 * @param dataObj
	 *            查詢條件
	 * 
	 * @return List<TbLsLocElecDTO>
	 */
	List<TbLsLocElecDTO> getSiteIdList(HashMap<String, Object> dataObj);

	/**
	 * Pay011 取得用電地址、戶名及計算營業稅額所需站點代碼
	 * 
	 * @param dataObj
	 *            查詢條件
	 * 
	 * @return List<TbLsLocElec>
	 */
	TbLsLocElec getTbLsLocElec(HashMap<String, Object> dataObj);
	
	/**
	 * 撈取 TB_LS_LOC_ELEC 資料
	 * @param lsNo
	 * 				合約編號
	 * @param locId
	 * 				站點編號
	 * @return
	 */
	List<TbLsLocElecDTO> searchTbLsLocElec(@Param("lsNo") String lsNo,@Param("locId")String locId);
	/**
	 * 撈取 TB_LS_LOC_ELEC 資料
	 * @param lsNo
	 * 				合約編號
	 * @param locId
	 * 				站點編號
	 * @return
	 */
	List<TbLsLocElecDTO> getTbLsLocElecDTOByLsNoVerLoc(@Param("lsNo") String lsNo,@Param("lsVer") String lsVer,@Param("locId")String locId);
	
	/**
	 * 撈取 TB_LS_LOC_ELEC 資料
	 * @param lsNo
	 * 				合約編號
	 * @param locId
	 * 				站點編號
	 * @return
	 */
	List<TbLsLocElecDTO> getTbLsLocElecAddedDTOByLsNoVerLoc(@Param("appSeq") String appSeq,@Param("locId")String locId);
	
	
	
	
	
	List<TbLsLocElecDTO> selectDtl4PAY003ATb2(HashMap<String, Object> dataObj);
	
	/**
	 * 依照傳入的條件sum出相對的電費金額 
	 * group LS_NO,LS_VER,LOCATION_ID
	 * @param example
	 * @return list
	 */
	List<TbLsLocElec> sumElecPledgegroupbylocationId(@Param("lsNo")String lsNo,
			@Param("lsVer") String lsVer ,@Param("locationId")String locationId,@Param("elecMode") String elecMode);
	
	TbLsLocElec getMaxVerbyLsNo(@Param("lsNo")String lsNo);
}