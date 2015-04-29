package com.foya.dao.mybatis.mapper;

import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.foya.dao.mybatis.model.TbPayCashRequirement;
import com.foya.noms.dto.pay.TbPayCashRequirementCompleteDTO;
import com.foya.noms.dto.pay.TbPayCashRequirementDTO;

public interface UTbPayCashRequirementMapper {

	List<TbPayCashRequirementDTO> selectTbPayCashRequirementPay007ByType(HashMap<String, Object> dataObj);

	List<TbPayCashRequirementDTO> selectTbPayCashRequirementPay007(HashMap<String, Object> dataObj);

	List<TbPayCashRequirementDTO> searchWithPayment(HashMap<String, Object> dataObj);
	

	/**
	 * 查詢請款單號
	 * 
	 * @param payType
	 *            請款類型 (止付類型)
	 * 
	 * @param maintainArea
	 *            維運區
	 * 
	 * @return List<TbPayCashRequirementDTO>
	 */
	List<TbPayCashRequirement> getPayRequirementNo(@Param("payType") String payType, @Param("maintainArea") String maintainArea);

	List<TbPayCashRequirementDTO> searchForPay003(HashMap<String, Object> dataObj);
	
	
	
	int updateByPAY003(@Param("cashReqNo") String cashReqNo,
			@Param("appUser") String appUser);

	/**
	 * Pay001 查詢請款資料
	 * 
	 * @param dataObj
	 *            查詢條件
	 * 
	 * @return List<TbPayCashRequirementDTO>
	 */
	List<TbPayCashRequirementDTO> selectTbPayCashRequirementPay001(HashMap<String, Object> dataObj);

	/**
	 * 取得專線請款資料
	 * 
	 * @param companyId
	 *            公司統編
	 * @param strDate
	 *            起使日期
	 * @param endDate
	 *            起訖日期
	 * @param reqNo
	 *            請款單號
	 * @param statusList
	 *            狀態
	 * 
	 * @return List<TbPayCashRequirementCompleteDTO>
	 */
	List<TbPayCashRequirementCompleteDTO> getPayCashData(@Param("companyId") String companyId, @Param("strDate") String strDate,
			@Param("endDate") String endDate, @Param("reqNo") String reqNo, @Param("status") List<String> statusList);

	/**
	 * Pay013 查詢請款資料
	 * 
	 * @param dataObj
	 *            查詢條件
	 * 
	 * @return List<TbPayCashRequirementDTO>
	 */
	List<TbPayCashRequirementDTO> selectTbPayCashRequirementPay013(HashMap<String, Object> dataObj);
	
}