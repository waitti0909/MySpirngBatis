package com.foya.dao.mybatis.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.foya.dao.mybatis.model.TbLsSite;
import com.foya.noms.dto.pay.TbLsSiteDTO;
import com.foya.noms.dto.st.SiteDTO;

public interface UTbLsSiteMapper {

	/**
	 * Pay013 查詢請款資料
	 * 
	 * @param dataObj
	 *            查詢條件
	 * 
	 * @return List<TbLsSite>
	 */
	List<TbLsSiteDTO> selectTbLsSitePay013(HashMap<String, Object> dataObj);
	
	

	/**
	 * 撈取 站台資訊 資料
	 * 
	 * @param lsNo
	 *            合約編號
	 * @param lsVer
	 *            版次
	 * @param locId
	 *            站點編號
	 * @return
	 */
	List<TbLsSiteDTO> getLsSiteByLsNoLocId(@Param("lsNo")String lsNo, @Param("lsVer")String lsVer, @Param("locId")String locId,@Param("itemType")String itemType);


	/**
	 * 撈取 站台資訊 資料
	 * 
	 * @param lsNo
	 *            合約編號
	 * @param lsVer
	 *            版次
	 * @param locId
	 *            站點編號
	 * @return
	 */
	List<TbLsSiteDTO> getLsSiteAddedByLsNoLocId(@Param("appSeq")String appSeq, @Param("locId")String locId,@Param("itemType")String itemType);

	
	/**
	 * 使用合約編號找尋最大版次的站台資料
	 * @param lsNo 合約編號
	 * @return
	 */
	List<TbLsSiteDTO> getLsSiteByLocId(HashMap<String, String> dataObj);	
	
	
	List<TbLsSite> searchLsSiteByLsNoLocIdVer(@Param("lsNo")String lsNo, @Param("locId")String locId);
	
	TbLsSite getMaxLsVer(@Param("lsNo")String lsNo);
}