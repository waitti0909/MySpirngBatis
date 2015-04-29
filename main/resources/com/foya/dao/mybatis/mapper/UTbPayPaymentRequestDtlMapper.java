package com.foya.dao.mybatis.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.foya.dao.mybatis.model.TbPayPaymentRequestDtl;
import com.foya.noms.dto.pay.TbLsSiteDTO;
import com.foya.noms.dto.pay.TbPayPaymentRequestCompleteDTO;
import com.foya.noms.dto.pay.TbPayPaymentRequestDtlCompleteDTO;
import com.foya.noms.dto.pay.TbPayPaymentRequestDtlDTO;

public interface UTbPayPaymentRequestDtlMapper {

	int updatePlusTax(HashMap<String, Object> dataObj);

	/**
	 * 查詢 - 站點編號 (PayPaymentRequestDtl.location_id)
	 * 
	 * @return List<TbPayPaymentRequestDtl>
	 */
	List<TbPayPaymentRequestDtl> selectDistinctLocationId();

	/**
	 * 查詢 相關金額 (PayPaymentRequestDtl.Tax Amount)
	 * 
	 * @param dataObj
	 *            所需條件
	 * 
	 * @return List<TbPayPaymentRequestDtlDTO>
	 */
	List<TbPayPaymentRequestDtlDTO> selectTaxSumAmt(HashMap<String, Object> dataObj);

	/**
	 * 查詢 請款單 PAY009[憑證維護]_Detail2
	 * 
	 * @param dataObj
	 *            所需條件
	 * 
	 * @return List<TbPayPaymentRequestDtlDTO>
	 */
	List<TbPayPaymentRequestDtlDTO> select4Pay009Detail2(HashMap<String, Object> dataObj);

	/**
	 * 查詢 請款單 PAY009[憑證維護]_Detail3
	 * 
	 * @param dataObj
	 *            所需條件
	 * 
	 * @return List<TbPayPaymentRequestDtlDTO>
	 */
	List<TbPayPaymentRequestDtlDTO> select4Pay009Detail3(HashMap<String, Object> dataObj);

	/**
	 * 查詢 請款單 PAY001[租/押金請款]_Detail2
	 * 
	 * @param dataObj
	 *            所需條件
	 * 
	 * @return List<TbPayPaymentRequestDtlDTO>
	 */
	List<TbPayPaymentRequestDtlDTO> select4Pay001Detail2(HashMap<String, Object> dataObj);

	/**
	 * Pay011 查詢固定金額資料
	 * 
	 * @param dataObj
	 *            所需條件
	 * 
	 * @return List<TbPayPaymentRequestDtlDTO>
	 */
	List<TbPayPaymentRequestDtlDTO> selectTbPayPaymentRequestDtlByPAY011(HashMap<String, Object> dataObj);

	List<TbLsSiteDTO> selectDtlForPAY003Table3(HashMap<String, Object> dataObj);

	/**
	 * 查詢合約（租約）資料
	 * 
	 * @param contractNo
	 *            租約編號
	 * @param paymentReqNo
	 * @param appDate
	 *            申請日
	 * 
	 * @return List<TbPayPaymentRequestCompleteDTO>
	 */
	List<TbPayPaymentRequestDtlCompleteDTO> getContractDetailData(@Param("contractNo") String contractNo, @Param("paymentReqNo") String paymentReqNo, @Param("appDate") String appDate);
}