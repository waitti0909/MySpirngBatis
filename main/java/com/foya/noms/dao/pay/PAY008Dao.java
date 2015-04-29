package com.foya.noms.dao.pay;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.ProcedureNomsMapper;
import com.foya.dao.mybatis.mapper.TbPayPaymentRequestMapper;
import com.foya.dao.mybatis.mapper.UTbLsLocationMapper;
import com.foya.dao.mybatis.mapper.UTbPayCashRequirementMapper;
import com.foya.dao.mybatis.mapper.UTbPayPaymentMapper;
import com.foya.dao.mybatis.mapper.UTbPayPaymentRequestDtlMapper;
import com.foya.dao.mybatis.mapper.UTbPayPaymentRequestMapper;
import com.foya.dao.mybatis.model.TbPayCashRequirement;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.ls.TbLsLocationCompleteDTO;
import com.foya.noms.dto.pay.TbPayPaymentCompleteDTO;
import com.foya.noms.dto.pay.TbPayPaymentRequestCompleteDTO;
import com.foya.noms.dto.pay.TbPayPaymentRequestDtlCompleteDTO;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

@Repository
public class PAY008Dao extends BaseDao {

	// ----------------------------------------------------------------------------------
	/**
	 * UTbPayCashRequirementMapper
	 */
	@Autowired
	private UTbPayCashRequirementMapper payCashRequirementMapper;

	/**
	 * 
	 */
	@Autowired
	private TbPayPaymentRequestMapper paymentRequestMapper;

	/**
	 * 
	 */
	@Autowired
	private UTbPayPaymentMapper uTbPayPaymentMapper;

	/**
	 * 
	 */
	@Autowired
	private UTbLsLocationMapper uLsLocationMapper;

	/**
	 * 
	 */
	@Autowired
	private UTbPayPaymentRequestMapper uTbPayPaymentRequestMapper;

	@Autowired
	private UTbPayPaymentRequestDtlMapper uTbPayPaymentRequestDtlMapper;
	
	@Autowired
	private ProcedureNomsMapper procedureNomsMapper;

	// ----------------------------------------------------------------------------------

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

		return payCashRequirementMapper.getPayRequirementNo(payType, maintainArea);
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

		// HashMap<String, Object> map = new HashMap<String, Object>();
		// map.put("cashReqNo", cashReqNo);
		// map.put("domain", domain);
		// map.put("applyType", applyType);
		// map.put("payType", payType);
		//
		// PageBounds pageBounds = getPageBounds("LS_S_DATE");
		// List<TbPayPaymentRequestCompleteDTO> list =
		// this.sqlSession.selectList(
		// "com.foya.dao.mybatis.mapper.UTbPayPaymentRequestMapper.getPayRequirementData",
		// map, pageBounds);

		return uTbPayPaymentRequestMapper.getPayRequirementData(cashReqNo, domain, applyType, payType);
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

		return uLsLocationMapper.getLsLocationData(contractNo, paymentReqNo, appDate);
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

		return uTbPayPaymentMapper.getPayPaymentData(contractNo, locationId, paymentReqNo, appDate, itemNo);
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
	 * @throws Exception
	 */
	public void updatePaymentRequest(HashMap<String, Object> dataObj) throws Exception {
		procedureNomsMapper.PAY_PC_SUSPEND_APP(dataObj);
		if (!dataObj.get("PO_ERR_CDE").equals("00")) {
			log.error("止付作業 Call PAY_PC_SUSPEND_APP Error：ERR_CDE=" + dataObj.get("PO_ERR_CDE") + ", PO_ERR_MSG=" + dataObj.get("PO_ERR_MSG"));
			throw new Exception("更新錯誤：" + dataObj.get("PO_ERR_MSG"));
		}
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

		return uTbPayPaymentRequestDtlMapper.getContractDetailData(contractNo, paymentReqNo, appDate);
	}

}
