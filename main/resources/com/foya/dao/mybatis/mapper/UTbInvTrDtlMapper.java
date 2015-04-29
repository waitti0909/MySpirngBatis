package com.foya.dao.mybatis.mapper;

import com.foya.noms.dto.inv.TbInvTrDtlCompleteDTO;
import com.foya.noms.dto.inv.TbInvTrDtlCompleteDTO;
import com.foya.noms.dto.inv.TbInvTrDtlDTO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface UTbInvTrDtlMapper {

	/**
	 * 查詢調撥明細檔
	 * 
	 * @param trNo
	 *            調撥申請單號
	 * @param matNo
	 *            料號代碼
	 * @param mat_status
	 *            物料狀態
	 * @return List<TbInvTrDtlCompleteDTO>
	 */
	List<TbInvTrDtlCompleteDTO> getInvTrDetailDto(@Param("trNo") String trNo, @Param("matNo") String matNo, @Param("matStatus") String matStatus);

	/**
	 * 查詢調撥明細檔
	 * 
	 * @param trNo
	 *            調撥申請單號
	 * @param whId
	 *            調出倉
	 * @return List<TbInvTrDtlCompleteDTO>
	 */
	List<TbInvTrDtlCompleteDTO> getInvTrExportDetailDto(@Param("trNo") String trNo, @Param("whId") String whId);

//	/**
//	 * 查詢調撥明細檔
//	 * 
//	 * @param trNo
//	 *            調撥申請單號
//	 * @param whId
//	 *            倉庫ID
//	 * @return List<TbInvTrDtlCompleteDTO>
//	 */
//	List<TbInvTrDtlComplete2DTO> getInvTrApplyDetailDto(@Param("trNo") String trNo, @Param("whId") String whId);

	/* 查詢明細檔 */
	List<TbInvTrDtlDTO> searchDtl(Map<String, Object> condition);

	/* 查詢明細檔金額 以trNo */
	int searchDtlAmt(Map<String, Object> condition);

	/* 取得流水號 以trNo matNo matstatus */
	List<TbInvTrDtlDTO> searchDtlSeq(Map<String, Object> condition);
}