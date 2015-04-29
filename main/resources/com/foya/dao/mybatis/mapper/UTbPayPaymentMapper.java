package com.foya.dao.mybatis.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.foya.dao.mybatis.model.TbPayPayment;
import com.foya.noms.dto.pay.TbPayPaymentCompleteDTO;
import com.foya.noms.dto.pay.TbPayPaymentDTO;

public interface UTbPayPaymentMapper {
	/**
	 * 查詢 基站請款紀錄 資料-Detail Page DetailTable3
	 * 
	 * @param dataObj
	 *            查詢條件
	 * 
	 * @return List<TbPayPaymentDTO>
	 */	
	List<TbPayPaymentDTO> selectTbPayPaymentByLocationId(HashMap<String, Object> dataObj);

	
	/**
	 * 查詢ERP中介檔資料12
	 * 
	 * @param dataObj 查詢條件
	 * 
	 * @return List<TbPayPaymentDTO>
	 */	
	List<TbPayPaymentDTO> selectByCashReqNo1(String cashReqNo);
	
	/**
	 * 查詢ERP中介檔資料34
	 * 
	 * @param dataObj 查詢條件
	 * 
	 * @return List<TbPayPaymentDTO>
	 */	
	List<TbPayPaymentDTO> selectByCashReqNo2(String cashReqNo);
	
	/**
	 * 取得付款資料
	 * 
	 * @param contractNo
	 *            租約編號
	 * @param locationId
	 *            站點代碼
	 * @param paymentReqNo
	 *            請款編號
	 * @param appDate
	 *            申請日期
	 * @return List<TbPayPaymentCompleteDTO>
	 */
	List<TbPayPaymentCompleteDTO> getPayPaymentData(@Param("contractNo") String contractNo, @Param("locationId") String locationId,
			@Param("paymentReqNo") String paymentReqNo, @Param("appDate") String appDate, @Param("itemNo") String itemNo);
	
	int updateByExampleSelectiveWithPlus(HashMap<String , Object> dataObj);
	
	int insertSelective(TbPayPayment record);
	
	List<TbPayPaymentDTO> getPaymentAmt(HashMap<String, Object> dataObj);

}