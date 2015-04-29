package com.foya.noms.service.pay;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hornetq.utils.json.JSONArray;
import org.hornetq.utils.json.JSONException;
import org.hornetq.utils.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foya.dao.mybatis.model.TbPayCashRequirement;
import com.foya.exception.NomsException;
import com.foya.noms.dao.pay.PAY008Dao;
import com.foya.noms.dto.ls.TbLsLocationCompleteDTO;
import com.foya.noms.dto.pay.TbPayPaymentCompleteDTO;
import com.foya.noms.dto.pay.TbPayPaymentRequestCompleteDTO;
import com.foya.noms.dto.pay.TbPayPaymentRequestDtlCompleteDTO;
import com.foya.noms.service.BaseService;

@Service
public class PAY008Service extends BaseService {

	// -----------------------------------------------------------------------------------
	/**
	 * Log
	 */
	protected Logger log = LoggerFactory.getLogger(PAY008Service.class);

	/**
	 * DAO
	 */
	@Autowired
	private PAY008Dao pay008Dao;
	

	// -----------------------------------------------------------------------------------

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
	public List<TbPayCashRequirement> getPayRequirementNo(String payType, String maintainArea) {

		return pay008Dao.getPayRequirementNo(payType, maintainArea);
	}

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
	public List<TbPayPaymentRequestCompleteDTO> getPayRequirementData(String cashReqNo, String domain, String applyType, String payType) {

		return pay008Dao.getPayRequirementData(cashReqNo, domain, applyType, payType);
	}

	/**
	 * 取得租約站點清單
	 * 
	 * @param contractNo
	 *            租約編號
	 * @param paymentReqNo
	 *            請款編號
	 * @param appDate
	 *            申請日期
	 */
	public List<TbLsLocationCompleteDTO> getLsLocationData(String contractNo, String paymentReqNo, String appDate) {

		return pay008Dao.getLsLocationData(contractNo, paymentReqNo, appDate);
	}

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
	public List<TbPayPaymentCompleteDTO> getPayPaymentData(String contractNo, String locationId, String paymentReqNo, String appDate,String itemNo) {

		return pay008Dao.getPayPaymentData(contractNo, locationId, paymentReqNo, appDate, itemNo);
	}

	/**
	 * 更新請款資料
	 * 
	 * @param id
	 *            id
	 * @param stopPayType
	 *            止付原因
	 * @param explain
	 *            說明
	 * 
	 * @return int
	 */
	@Transactional
	public boolean updatePaymentRequest(String jsonArray,Date today) {
		try {
			JSONArray jsonArrayTemp = new JSONArray(jsonArray);
			for (int i = 0; i < jsonArrayTemp.length(); i++) {
				JSONObject objTemp = jsonArrayTemp.getJSONObject(i);
				HashMap<String, Object> dataObj = new HashMap<String,Object>();
				dataObj.put("PI_CASH_REQ_NO", objTemp.getString("cashReqNo"));
				dataObj.put("PI_DOMAIN", objTemp.getString("domain"));
				dataObj.put("PI_CONTRACT_NO", objTemp.getString("contractNo"));			
				dataObj.put("PI_SUSPEND_REASON", objTemp.getString("stopPayType"));
				dataObj.put("PI_SUSPEND_MEMO", objTemp.getString("explain"));
				dataObj.put("PI_SUSPEND_USER", getLoginUser().getUsername());
				//dataObj.put("PI_REQUEST_DATEE", today);
				pay008Dao.updatePaymentRequest(dataObj) ;
			}
		} catch (JSONException e) {
			e.printStackTrace();
			throw new NomsException("止付作業 錯誤："+e.getMessage());
		} catch(Exception e){throw new NomsException("止付作業 錯誤："+e.getMessage());}
		return true;
	}

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
	public List<TbPayPaymentRequestDtlCompleteDTO> getContractDetailData(String contractNo, String paymentReqNo, String appDate) {

		return pay008Dao.getContractDetailData(contractNo, paymentReqNo, appDate);
	}
}
