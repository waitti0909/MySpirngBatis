package com.foya.dao.mybatis.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.foya.dao.mybatis.model.TbPayPaymentRequest;
import com.foya.noms.dto.pay.TbPayPaymentRequestCompleteDTO;
import com.foya.noms.dto.pay.TbPayPaymentRequestDTO;

public interface UTbPayPaymentRequestMapper {
	
	List<TbPayPaymentRequestDTO> selectTbPayPaymentRequest(HashMap<String , Object> dataObj);
	
	List<TbPayPaymentRequestDTO> searchForPay003DtlMaster(HashMap<String , Object> dataObj);
	
	
	int insertSelective(TbPayPaymentRequest record);
	
	/**
	 * 查詢請款資料
	 * 
	 * @param cashReqNo
	 *            請款單號
	 * @param domain
	 *            維運區
	 * @param applyStatus
	 *            請款單 - 申請狀態
	 * @param payType
	 *            付款狀態
	 * 
	 * @return List<TbPayCashRequirementDTO>
	 */
	List<TbPayPaymentRequestCompleteDTO> getPayRequirementData(@Param("cashReqNo") String cashReqNo, @Param("domain") String domain,
			@Param("applyType") String applyStatus, @Param("payType") String payType);
	
	List<TbPayPaymentRequestDTO> selectLieData();
	
	List<TbPayPaymentRequestDTO> getLieAmt(HashMap<String , Object> dataObj);
	
	List<TbPayPaymentRequestDTO> selectEleData();
	
	List<TbPayPaymentRequestDTO> getEleAmt(HashMap<String , Object> dataObj);
	
	String getLinePurpose(String lineId);
	
}