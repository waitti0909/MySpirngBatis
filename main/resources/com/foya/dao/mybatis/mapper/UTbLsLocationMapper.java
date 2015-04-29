package com.foya.dao.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.foya.dao.mybatis.model.TbLsLocation;
import com.foya.dao.mybatis.model.TbLsLocationExample;
import com.foya.noms.dto.ls.LeaseLocationDTO;
import com.foya.noms.dto.ls.TbLsLocationCompleteDTO;

public interface UTbLsLocationMapper extends TbLsLocationMapper{

	/**
	 * 取得租約站點清單
	 * 
	 * @param contractNo
	 *            租約編號
	 * @param reqNo
	 *            請款編號
	 * @param appDate
	 *            申請日期
	 * 
	 * @return List<TbLsLocationCompleteDTO>
	 */
	List<TbLsLocationCompleteDTO> getLsLocationData(@Param("contractNo") String contractNo, @Param("paymentReqNo") String reqNo,
			@Param("appDate") String appDate);
	/**
	 * 取得搜尋條件的所有站點
	 * @param lsNo
	 * 			合約編號
	 * @param lsVer
	 * 			版次
	 * @return
	 */
	List<LeaseLocationDTO> getLeaseLocation(@Param("lsNo") String lsNo,@Param("lsVer") String lsVer,@Param("domain") String domain);
	
	
	/**
	 * 取得搜尋條件的所有站點
	 * @param lsNo
	 * 			合約編號
	 * @param lsVer
	 * 			版次
	 * @return
	 */
	List<LeaseLocationDTO> getLeaseLocationAdded(@Param("appSeq") String appSeq,@Param("domain") String domain);
	
	
	
	/**
	 * 搜尋 tb_ls_location 與 tb_ls_main 的資料
	 * @param locationId
	 * 			站點編號
	 * @param lsStatus
	 * 			合約狀態 ：生效
	 * @return
	 */
	List<TbLsLocationCompleteDTO> getLeaseByLocation(@Param("locationId")String locationId,@Param("lsStatus")String lsStatus);
	/**
	 * 撈取站點資料
	 * @param lsNo
	 *            合約編號
	 * @param lsVer
	 *            版次
	 * @param locId
	 *            站點編號
	 * @return
	 */
	List<LeaseLocationDTO> getLsSiteLocationByLsNoVer(@Param("lsNo")String lsNo, @Param("lsVer")String lsVer, @Param("locId")String locId);
	
	
	/**
	 * 撈取站點Added資料
	 * @param lsNo
	 *            合約編號
	 * @param lsVer
	 *            版次
	 * @param locId
	 *            站點編號
	 * @return
	 */
	List<LeaseLocationDTO> getLsSiteLocationAddedByAppSeqLoc(@Param("appSeq")String appSeq, @Param("locId")String locId);
	
	
	
	/**
	 * 撈取站點資料
	 * @param lsNo
	 *            合約編號
	 * @param lsVer
	 *            版次
	 * @param locId
	 *            站點編號
	 * @return
	 */
	List<TbLsLocation> getLsLocationByLsNoVer(@Param("lsNo")String lsNo, @Param("locId")String locId);
	/**
	 * 取得 location_id 的資料 For Pay001
	 * 
	 * @param lsNo 合約編號
	 * 
	 * @return list of TbLsLocation
	 */
	List<TbLsLocation> select4Pay001Detail2(@Param("lsNo") String lsNo);
	/**
	 * 撈取 tb_ls_location 資料
	 * @param lsNo
	 * 			合約編號
	 * @param domain
	 * 			區域
	 * @return
	 */
	List<LeaseLocationDTO> getLocationVerMax(@Param("lsNo")String lsNo,@Param("domain")String domain);


	int updateByExampleSelective(@Param("record") TbLsLocation record,
			@Param("example") TbLsLocationExample example);
	
	List<TbLsLocation> getEditElecCh(@Param("lsNo")String lsNo,@Param("appSeq")String appSeq);
	
	/**
	 * 
	 * @param lsNo
	 * @param locateionId
	 * @return
	 */
	TbLsLocation selectMaLsvar(@Param("lsNo")String lsNo,@Param("locateionId")String locateionId);

}