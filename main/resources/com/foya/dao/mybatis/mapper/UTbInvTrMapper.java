/**
 * 日期：2014/11/14
 * 功能說明：Mapper
 * 程式人員：Arvin
 */

package com.foya.dao.mybatis.mapper;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.foya.dao.mybatis.model.TbInvTr;
import com.foya.noms.dto.inv.TbInvTrCompleteDTO;
import com.foya.noms.dto.inv.TbInvTrDTO;

public interface UTbInvTrMapper {

	/**
	 * 取得調撥主檔
	 * 
	 * @param strDemandDate
	 *            需求日期 (開始)
	 * @param endDemandDate
	 *            需求日期 (結束)
	 * @param applyNumber
	 *            調撥單號
	 * @param applyState
	 *            調撥進度
	 * @param outDepot
	 *            調出倉庫
	 * @param inDepot
	 *            調入倉庫
	 * @param demandDept
	 *            需求單位
	 * @param applyDept
	 *            申請單位
	 * @param applicant
	 *            申請人
	 * @return List<TbInvTr>
	 */
	List<TbInvTrCompleteDTO> getInvTrDataDtoDate(HashMap<String, Object> map);

	/* 查詢調撥申請單＆記錄 */
	List<TbInvTr> searchInvTrByInitSearch(Map condition);

	/* 查詢調撥申請主檔包含中文說明 */
	TbInvTrDTO searchMasterColumsDscr(Map condition);

	/* 計算DTL結案筆數 */
	int searchInvDtlCntByStatusSevenWareHouseClose(String tr_no);

	/* 計算記錄檔結案筆數 */
	int searchInvActCntByStatusSevenWareHouseClose(String tr_no);

	/* 取得當日最大流水號 */
	TbInvTr selerctMaxSeq(Map trNo);
}
