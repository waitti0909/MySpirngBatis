package com.foya.dao.mybatis.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.foya.dao.mybatis.model.TbLsLocPayment;
import com.foya.noms.dto.ls.TbLsLocPaymentDTO;

public interface UTbLsLocPaymentMapper {

	/**
	 * 撈取 付款資訊 資料
	 * 
	 * @param lsNo
	 *            合約編號
	 * @param lsVer
	 *            版次
	 * @param locId
	 *            站點編號
	 * @return
	 */
	List<TbLsLocPaymentDTO> getLsLocPaymentByLsNoVerLocId(@Param("lsNo")String lsNo, @Param("lsVer")String lsVer, @Param("locId")String locId,@Param("itemType")String[] itemType);
	
	/**
	 * 撈取 付款資訊 資料
	 * 
	 * @param lsNo
	 *            合約編號
	 * @param lsVer
	 *            版次
	 * @param locId
	 *            站點編號
	 * @return
	 */
	List<TbLsLocPaymentDTO> getLsLocPaymentAddedByAppSeqLocId(@Param("appSeq")String appSeq,  @Param("locId")String locId,@Param("itemType")String[] itemType);
	
	
	/**
	 * 查詢 TbLsLocPaymen 資料
	 * @param lsNo
	 * 			合約編號
	 * @param locId
	 * 			站點編號
	 * @param paymentItem
	 * 			支付項目
	 * @return
	 */
	List<TbLsLocPaymentDTO> searchTbLsLocPaymen(@Param("lsNo")String lsNo, @Param("locId")String locId,@Param("paymentItems")String[] paymentItems);
	
	/**
	 * 撈取 付款資訊 資料
	 * @param object 條件值
	 * @return
	 */
	List<TbLsLocPaymentDTO> getLsLocPaymentByLsNoVerLocIdLessorId(HashMap<String,Object> object);
	
	/**
	 * 依照傳入條件，query出 
	 * PAYMENT_ITEM、PAYMENT_ITEM、LOCATION_ID、sum(PAY_AMT)<租金、租金押金、電費、電費押金> 
	 * group by PAYMENT_ITEM、PAYMENT_ITEM、LOCATION_ID、sum(PAY_AMT) 
	 * @param example
	 * @return list
	 */
	List<TbLsLocPayment> sumPayamtgroupbyPaymentItem (@Param("lsNo")String lsNo,
			@Param("lsVer") String lsVer ,@Param("locationId")String locationId);
	
	/**
	 * 依照傳入條件，query出 LOCATION_ID,SUM(PAY_ALOC)<分攤比>
	 * group by LOCATION_ID
	 * @param example
	 * @return list
	 */
	List<TbLsLocPayment> sumPayalocgroupbylocationId (@Param("lsNo")String lsNo,
			@Param("lsVer") String lsVer);
	
	/**
	 * selectTblslocpaymentMaxVer by ls_no
	 * @param lsNo 
	 * @return
	 */
	TbLsLocPayment selectTblslocpaymentMaxVer (@Param("lsNo")String lsNo,@Param("locationId")String locationId);
	
	int queryPaymentAmtByLocItem(HashMap<String,Object> object);

}