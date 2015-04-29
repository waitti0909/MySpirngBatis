package com.foya.dao.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.foya.noms.dto.inv.TbInvTrActCompleteDTO;

public interface UTbInvTrActMapper {

	/**
	 * 查詢調撥數量
	 * 
	 * @param trNo
	 *            調撥申請單號
	 * @param matNo
	 *            料號代碼
	 * @param trIn
	 *            是否調入 (0=調出 , 1=調入)
	 * @return int
	 */
	Integer getActExportNumber(@Param("trNo") String trNo, @Param("matNo") String matNo, @Param("trIn") int trIn, @Param("trDtlNo") long trDtlNo);

	// /**
	// * 查詢調撥數量
	// *
	// * @param trNo
	// * 調撥申請單號
	// * @param matNo
	// * 料號代碼
	// * @param srlNo
	// * 序號控管ID
	// * @param isTrIn
	// * 是否調入(0:否，1:是)
	// *
	// * @return List<TbInvTrAct>
	// */
	// List<TbInvTrActCompleteDTO> getInvTrActDtoData(@Param("trNo") String
	// trNo, @Param("matNo") String matNo, @Param("srlNo") Integer srlNo,
	// @Param("trIn") String trIn);

	/**
	 * 查詢調撥資料
	 * 
	 * @param trNo
	 *            調撥申請單號
	 * @param matNo
	 *            料號代碼
	 * @param isTrIn
	 *            是否調入(0:否，1:是)
	 * 
	 * @return List<TbInvTrAct>
	 */
	List<TbInvTrActCompleteDTO> getExportHistory(@Param("trNo") String trNo, @Param("matNo") String matNo, @Param("trIn") String trIn,
			@Param("trDtlNo") int trDtlNo,@Param("outActNo") int outActNo);

	/**
	 * 查詢調撥資料
	 * 
	 * @param trNo
	 *            調撥申請單號
	 * 
	 * @return List<TbInvTrAct>
	 */
	List<TbInvTrActCompleteDTO> getInvTrExportDtlData(String trNo);
	
	/**
	 * 查詢調撥資料
	 * 
	 * @param trNo
	 *            調撥申請單號
	 * 
	 * @return List<TbInvTrAct>
	 */
	List<TbInvTrActCompleteDTO> getApplyTotal(String trNo);

	/**
	 * 查詢調撥資料
	 * 
	 * @param trNo
	 *            調撥申請單號
	 * @param matNo
	 *            料號代碼
	 * @param isTrIn
	 *            是否調入(0:否，1:是)
	 * 
	 * @return List<TbInvTrAct>
	 */
	List<TbInvTrActCompleteDTO> getExportHistoryData(@Param("trNo") String trNo, @Param("matNo") String matNo, @Param("trIn") String trIn);

	/**
	 * 查詢調撥數量以group
	 * 
	 * @param trNo
	 *            調撥申請單號
	 * @param matNo
	 *            料號代碼
	 * @param trIn
	 *            是否調入 (0=調出 , 1=調入)
	 * @return int
	 */
	Integer getActExportNumberGroup(@Param("trNo") String trNo, @Param("matNo") String matNo, @Param("trIn") int trIn,
			@Param("trDtlNo") long trDtlNo, @Param("returnTr") String returnTr);

}