package com.foya.dao.mybatis.mapper;

import com.foya.noms.dto.inv.TbInvTxnDTO;
import com.foya.dao.mybatis.model.TbInvTxn;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface UTbInvTxnMapper {
	// InvDao begin(待修改)
	/**
	 * select TB_INV_TXN where By site_id,order_no,txt_type
	 * 
	 */
	List<TbInvTxn> selectInvTxnByTempWh(TbInvTxn record);

	/**
	 * select wh_id TB_INV_TXN where By site_id,order_no,txt_type,mat_no,fa_no
	 * 
	 */
	List<TbInvTxn> getRtnWhIdBySiteIdAndOrderNo(TbInvTxn record);

	// InvDao end(待修改)

	// 序號歷程查詢結果detail
	List<TbInvTxnDTO> searchInvTxnJoinSiteTxn(HashMap<String, Object> dataObj);

	// 序號歷程增加貼標欄位
	List<TbInvTxnDTO> searchWithInvSrl(HashMap<String, Object> dataObj);

	// 序號歷程以txnNo查詢
	List<TbInvTxnDTO> searchWithInvSrlByTxnNo(HashMap<String, Object> dataObj);

	// 發料查詢頁面
	List<TbInvTxnDTO> searchInvTxnPrintPageDetail(
			HashMap<String, Object> dataObj);

	List<TbInvTxnDTO> searchInvTxn(HashMap<String, Object> dataObj);

	/* 取得當日最大流水號 */
	TbInvTxnDTO selerctMaxSeq(HashMap<String, Object> txnNo);

	// 收料簽收單
	List<TbInvTxnDTO> searchInvTxnRef(HashMap<String, Object> dataObj);

	/**
	 * 搜尋 Txn Data
	 * 
	 * @param txnNo
	 *            單號
	 * @param whId
	 *            收料倉
	 * @param createUser
	 *            收料人員
	 * @param strDate
	 *            起始日期
	 * @param endDate
	 *            起訖日期
	 * @return List<TbInvTxnDTO>
	 */
	List<TbInvTxnDTO> getInvTxnData(@Param("txnNo") String txnNo,
			@Param("whId") String whId, @Param("createUser") String createUser,
			@Param("strDate") Date strDate, @Param("endDate") Date endDate);
	
	/**
	 * 搜尋 Txn Data 依照
	 * 
	 * @param txnNo
	 *            單號
	 * @param whId
	 *            收料倉
	 * @param createUser
	 *            收料人員
	 * @param strDate
	 *            起始日期
	 * @param endDate
	 *            起訖日期
	 * @return List<TbInvTxnDTO>
	 */
	List<TbInvTxnDTO> getInvTxnDataByGroup(@Param("txnNo") String txnNo,
			@Param("whId") String whId, @Param("createUser") String createUser,
			@Param("strDate") Date strDate, @Param("endDate") Date endDate);
	//收料明細畫面
	List<TbInvTxnDTO> searchRTDetail(HashMap<String, Object> dataObj);
	//發料明細畫面 已發料數
	int searchTxnMRSumQty(HashMap<String, Object> dataObj);
	//12 SP1 Detail
	List<TbInvTxnDTO> searchWithTxnNoForSp1(HashMap<String, Object> dataObj);
	//12更新opt使用
	List<TbInvTxnDTO> searchLessQty(HashMap<String, Object> dataObj);
	//給外部呼叫的部分程式
	List<TbInvTxnDTO> searchTxnQty(HashMap<String, Object> dataObj);
	//給外部呼叫的部分程式
	List<TbInvTxnDTO> searchTxnRTQty(HashMap<String, Object> dataObj);
	//取得結案使用的數量
	TbInvTxnDTO searchCloseTxnQty(HashMap<String, Object> dataObj);
}
