package com.foya.noms.service.pay;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foya.dao.mybatis.model.TbLsLocation;
import com.foya.dao.mybatis.model.TbPayCashRequirement;
import com.foya.dao.mybatis.model.TbPayCashRequirementExample;
import com.foya.dao.mybatis.model.TbPayLookupCode;
import com.foya.dao.mybatis.model.TbPayPayment;
import com.foya.exception.NomsException;
import com.foya.noms.dao.pay.PAY001Dao;
import com.foya.noms.dto.pay.TbPayCashRequirementDTO;
import com.foya.noms.dto.pay.TbPayPaymentDTO;
import com.foya.noms.dto.pay.TbPayPaymentRequestDTO;
import com.foya.noms.dto.pay.TbPayPaymentRequestDtlDTO;
import com.foya.noms.resources.AppConstants;
import com.foya.noms.service.BaseService;
import com.foya.noms.service.workflow.WorkflowActionService;
@Service
public class PAY001Service extends BaseService {
	@Inject 
	private PAY001Dao pay001Dao;
	
	@Autowired
	private WorkflowActionService workflowActionService;
	
	@Inject
	private PayPublicUtilService payPublicUtilService;

	/**
	 * 查詢請款資料 
	 * 
	 * @param domain 維運區 
	 * @param processType 處理類別
	 * @param paymentPeriod 付款週期
	 * @param cashReqNo 請款單號
	 * @param appDateStart 申請起日
	 * @param appDateEnd 申請迄日
	 * @param status[] 申請狀態 
	 * 
	 * @return List<TbPayCashRequirementDTO>
	 */
	public List<TbPayCashRequirementDTO> selectByExample(String domain, String processType,
			String paymentPeriod, String cashReqNo, Date appDateStart,Date appDateEnd,
			String[] status)throws ParseException{
		HashMap<String,Object> dataObj = new HashMap<String,Object>();
		dataObj.put("domain", domain);
		dataObj.put("processType", processType);
		dataObj.put("paymentPeriod", paymentPeriod);
		dataObj.put("cashReqNo", cashReqNo);
		dataObj.put("appDateStart", appDateStart);
		dataObj.put("appDateEnd", appDateEnd);
		dataObj.put("status", status);
		return pay001Dao.selectByExample(dataObj);
	}
	/**
	 * 查詢請款資料-Detail Page Master資料
	 * 
	 * @param cashReqNo
	 *            請款單號
	 * 
	 * @return TbPayCashRequirement
	 */
	public TbPayCashRequirement selectByPrimaryKey(String cashReqNo){
		TbPayCashRequirement pay=pay001Dao.selectByPrimaryKey(cashReqNo);
		return pay;
	}
	/**
	 * 查詢請款清單資料-Detail Page DetailTable1  
	 * 
	 * @param cashReqNo 請款單號
	 * @param appDate 申請日期
	 * @param processTypeDesc 處理類別-中文說明(使用Master)
	 * 
	 * @return List<TbPayPaymentRequestDTO>
	 */	
	public List<TbPayPaymentRequestDTO> selectDetail1(String cashReqNo, String appDate, String processTypeDesc){
		HashMap<String,Object> dataObj = new HashMap<String,Object>();
		dataObj.put("cashReqNo", cashReqNo);
		dataObj.put("appDate", appDate);
		dataObj.put("processTypeDesc", processTypeDesc);
		return pay001Dao.selectDetail1(dataObj);
	}
	/**
	 * 查詢租約基站清單資料-Detail Page DetailTable2
	 * 
	 * @param contractNo  租約編號
	 * @param paymentReqNo 請款編號(ID)
	 * @param appDate 申請日期
	 * 
	 * @return List<TbPayPaymentRequestDtlDTO>
	 */	
	public List<TbPayPaymentRequestDtlDTO> selectDetail2(String contractNo, String paymentReqNo, String appDate){
		HashMap<String,Object> dataObj = new HashMap<String,Object>();
		dataObj.put("contractNo", contractNo);
		dataObj.put("paymentReqNo", paymentReqNo);
		dataObj.put("appDate", appDate);
		return pay001Dao.selectDetail2(dataObj);
	}
	/**
	 * 查詢 基站請款紀錄 資料-Detail Page DetailTable3
	 * 
	 * @param locationId  站點代碼
	 * @param contractNo 租約編號
	 * @param paymentReqNo 請款編號(ID)
	 * @param appDate 申請日期
	 * @param paymentReqItemNo 請款細項編號 itemNo
	 * @param processType 處理類別 -處理類別=首期/例行/補請,則pay_item=’R’.處理類別=押金,則pay_item=’RD’
	 * 
	 * @return List<TbPayPaymentDTO>
	 */	
	public List<TbPayPaymentDTO> selectDetail3(String locationId, String contractNo, 
			Long paymentReqNo, String appDate, int paymentReqItemNo, String processType){
		HashMap<String,Object> dataObj = new HashMap<String,Object>();
		dataObj.put("locationId", locationId);
		dataObj.put("contractNo", contractNo);
		dataObj.put("paymentReqNo", paymentReqNo);
		dataObj.put("appDate", appDate);
		dataObj.put("paymentReqItemNo", paymentReqItemNo);		
		dataObj.put("processType", processType);
		return pay001Dao.selectDetail3(dataObj);
	}	
	/**
	 * 修改 - 刪除查詢資料(1.請款資料明細, 2.請款資料, 3.付款資料, 4.請款單)
	 * 
	 * @param morePaymentReqNo[]  多筆請款編號
	 * @param cashReqNo 請款單號
	 * 
	 * @return List<TbPayPaymentDTO>
	 */	
	@Transactional
	public int update(String[] morePaymentReqNo, String cashReqNo){
		List<Long> ls = new ArrayList<Long>();
		try{
			for(String s : morePaymentReqNo){
				ls.add(Long.parseLong(s));
			}
			if(ls.size()>0){
				pay001Dao.deletePayPaymentRequestDtl(ls);
				pay001Dao.deletePayPaymentRequest(ls);
				pay001Dao.deletePayPayment(ls);
			}
			if(pay001Dao.getPayPaymentRequestCnt(cashReqNo) == 0){
				pay001Dao.deletePayCashRequirement(cashReqNo);
			}
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			throw new NomsException("99", e.getMessage());
		}
		return morePaymentReqNo.length;
	}
	/**
	 * 修改 - 送審及刪除查詢資料(1.請款資料明細, 2.請款資料, 3.付款資料, 4.請款單)
	 * 
	 * @param morePaymentReqNo[]  多筆請款編號
	 * @param cashReqNo 請款單號
	 * 
	 * @return List<TbPayPaymentDTO>
	 */	
	@Transactional
	public int approved(String[] morePaymentReqNo, String cashReqNo){
		boolean isDelete = false;
		List<Long> ls = null;
		try{
			//先確認是否需要刪除
			if(morePaymentReqNo.length>0){
				ls = new ArrayList<Long>();
				for(String s : morePaymentReqNo){
					ls.add(Long.parseLong(s));
				}
				if(ls.size()>0){
					pay001Dao.deletePayPaymentRequestDtl(ls);
					pay001Dao.deletePayPaymentRequest(ls);
					pay001Dao.deletePayPayment(ls);
				}
				if(pay001Dao.getPayPaymentRequestCnt(cashReqNo) == 0){
					pay001Dao.deletePayCashRequirement(cashReqNo);
					isDelete = true;
				}	
			}
			//送審
			if(!isDelete){
				TbPayCashRequirement record = new TbPayCashRequirement();
				record.setSTATUS("B");
				record.setMD_USER(getLoginUser().getUsername());
				record.setMD_TIME(new Date());  
				pay001Dao.approved(cashReqNo, record);	
				//簽核
				workflowActionService.apply("PayRent", cashReqNo, "租金請款");
			}
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			throw new NomsException("99", e.getMessage());
		}
		return 1;
	}
	/**
	 * 新增- 檢查是否有重覆請款清單資料
	 * 
	 * @param domain 維運區
	 * @param processType 處理類別
	 * @param yearMonth 請款年月
	 * 
	 * @return true有資料/false無資料
	 */	
	public boolean checkDataExists(String domain, String processType, String yearMonth){		
		TbPayCashRequirementExample example = new TbPayCashRequirementExample();
		example.createCriteria().andDOMAINEqualTo(domain)
		.andPROCESS_TYPEEqualTo(processType)
		.andYEAR_MONTHEqualTo(yearMonth);
		int cnt = pay001Dao.countPayCashRequirement(example);
		return cnt>0;
	}
	/**
	 * 新增- 查詢  請款清單資料-Detail Page DetailTable1,依處理類別  不同而執行不同的SQL
	 * 
	 * @param processType 處理類別
	 * @param domain 維運區
	 * @param paymentPeriod 付款週期
	 * @param processTypeDesc 處理類別-中文說明
	 * 
	 * @return List<TbPayPaymentRequestDTO>
	 */	
	public List<TbPayPaymentRequestDTO> selectProcess(String processType, 
			String domain, String paymentPeriod, String processTypeDesc, String yearMonth){
		List<TbPayPaymentRequestDTO> list = null;
		HashMap<String,Object> dataObj = new HashMap<String,Object>();
		dataObj.put("domain", domain);
		dataObj.put("paymentPeriod", paymentPeriod);
		dataObj.put("processTypeDesc", processTypeDesc);
		dataObj.put("yearMonth", yearMonth);
		/*REN01	押金
		REN02	首期
		REN03	例行
		REN04	補請*/
		if(processType.equals("REN01")){
			list = pay001Dao.selectDetail1ByREN01(dataObj);
		}else if(processType.equals("REN02")){
			list = pay001Dao.selectDetail1ByREN02(dataObj);
		}else if(processType.equals("REN03")){
			list = pay001Dao.selectDetail1ByREN03(dataObj);
		}else if(processType.equals("REN04")){
			list = pay001Dao.selectDetail1ByREN04(dataObj);
		}
		return list;
	}
	/**
	 * 新增- 查詢  請款清單資料-Detail Page DetailTable2 &3  
	 * 
	 * @param timestampKey call API傳入的key
	 * @param domain 維運區
	 * @param contractNo 租約編號
	 * @param appDate 請款申請日期
	 * @param paymentPeriod 付款週期
	 * @param paymentMonth 付款月數(計算 本次請款: 租金*月數)
	 * @param processType 處理類別
	 * 
	 * @return List<TbLsLocPayment>
	 */	
	@SuppressWarnings("unchecked")
	@Transactional
	public Map<String, Object> selectDetail2ByProcess(String timestampKey, String domain, 
			String contractNo, Date appDate, String paymentPeriod, 
			String paymentMonth, String processType){
		Map<String, Object> returnMap = new HashMap<String, Object>();
		List<TbPayPaymentRequestDtlDTO> grid2List = new ArrayList<TbPayPaymentRequestDtlDTO>();		
		List<TbPayPaymentDTO> grid3List = new ArrayList<TbPayPaymentDTO>();
		TbPayPaymentRequestDtlDTO grid2Vo;
		TbPayPaymentDTO grid3Vo;
		int id =0;
		String payItem = null;
		String paymentMethodName = null;
		List<TbPayLookupCode> lookupCodeList;
		BigDecimal taxExclusiveAmt ;
		String appType = "";
		String paymentItem = "R";
		try{
			//Step 1 : 先取 TB_LS_LOCATION
			List<TbLsLocation> lsLocationList = pay001Dao.getDistinctLocationId(contractNo);
			
			//Step 2  : 取得 其他資料			
			/*判斷appType, paymentItem
			REN01	押金
			REN02	首期
			REN03	例行
			REN04	補請*/
			if(processType.equals("REN01")){
				appType = "";
				paymentItem = "RD";
			}else if(processType.equals("REN02")){
				appType = "F";
			}else if(processType.equals("REN03")){
				appType = "R";
			}else{
				appType = "B";
			}
			Map<String,Object> dataObj = null;
			List<Map<String,Object>> list = null;
			Map<String, BigDecimal> payItemAmount = null;
			BigDecimal sumPayAmt = BigDecimal.ZERO;
			int grid2Cnt = 0;
			for(TbLsLocation vo : lsLocationList){
				id++;
				taxExclusiveAmt = BigDecimal.ZERO;
				dataObj = payPublicUtilService.payPcGetAppAmt(timestampKey,
						domain, contractNo, vo.getLOCATION_ID(), null, paymentItem, 
						appDate, paymentPeriod, paymentMonth, appType);
				
				list = (List<Map<String, Object>>) dataObj.get("PO_CURSOR");
				//如無key, 表第一次,需取出
				if(StringUtils.isBlank(timestampKey))timestampKey = dataObj.get("PO_TIMESTAMP").toString();
				payItemAmount = new HashMap<String, BigDecimal>(); 
				for(Map<String,Object> m : list){
					grid3Vo = new TbPayPaymentDTO();
					payItem = (String)m.get("PAY_ITEM");
					grid3Vo.setContractNo(contractNo);//parent.parentId=>Grid1
					grid3Vo.set_id(id); //parentId =>grid2
					grid3Vo.setLessorName((String)m.get("LESSOR_NAME"));
					grid3Vo.setPAYMENT_USER_ID((String)m.get("RECIPIENT_ID"));
					grid3Vo.setPAYMENT_USER_NAME((String)m.get("RECIPIENT_NAME"));
					paymentMethodName = (String)m.get("PAYMENT_MODE");
					lookupCodeList = payPublicUtilService.getPayLookupByTypeAndName("PAYMENT_METHOD", paymentMethodName);
					if(lookupCodeList.size()>0){
						paymentMethodName = lookupCodeList.get(0).getLOOKUP_CODE_DESC();
					}
					grid3Vo.setPaymethodName(paymentMethodName);
					grid3Vo.setRentAmt(pay001Dao.getPayAmt4Detail3(
							contractNo, vo.getLOCATION_ID(), (String)m.get("RECIPIENT_ID"), 
							(String)m.get("LESSOR_ID"), paymentItem));
					grid3Vo.setTotalAmt(m.get("PAY_AMT")!=null?new BigDecimal(m.get("PAY_AMT").toString()):BigDecimal.ZERO);					
					grid3Vo.setTOTAL_BUSINESS_TAX(m.get("BUSINESS_TAX_AMT")!=null?new BigDecimal(m.get("BUSINESS_TAX_AMT").toString()):BigDecimal.ZERO);
					grid3Vo.setTOTAL_INCOME_TAX(m.get("INCOM_TAX_AMT")!=null?new BigDecimal(m.get("INCOM_TAX_AMT").toString()):BigDecimal.ZERO);
					grid3Vo.setTOTAL_NHI_AMT(m.get("HS_REFILL_AMT")!=null?new BigDecimal(m.get("HS_REFILL_AMT").toString()):BigDecimal.ZERO);
					grid3Vo.setSumAllAmt(m.get("PAY_AMT")!=null?new BigDecimal(m.get("PAY_AMT").toString()):BigDecimal.ZERO);
					grid3Vo.setTAX_EXCLUSIVE_TOTAL_AMT(
							grid3Vo.getTotalAmt().add(grid3Vo.getTOTAL_NHI_AMT()).add(grid3Vo.getTOTAL_INCOME_TAX())
							.subtract(grid3Vo.getTOTAL_BUSINESS_TAX()));
					//以下為新增所需,不在頁面呈現
					grid3Vo.setPAYMENT_METHOD((String)m.get("PAYMENT_MODE"));
					grid3Vo.setBANK_CODE((String)m.get("UNIT_CODE"));
					grid3Vo.setBANK_BRANCH_CODE((String)m.get("SUB_UNIT_CODE"));
					grid3Vo.setACCOUNT_NBR((String)m.get("ACCOUNT_NO"));
					grid3Vo.setPAYMENT_REQ_USER_ID((String)m.get("LESSOR_ID"));
					grid3List.add(grid3Vo);
					//作為grid2筆數判斷
					sumPayAmt = new BigDecimal(grid3Vo.getTotalAmt().toString());
					if(payItemAmount.get(payItem)!=null){
						sumPayAmt = sumPayAmt.add(payItemAmount.get(payItem));
					}
					payItemAmount.put(payItem, sumPayAmt);
				}
				//組合 grid2
				grid2Cnt = 0;
				for (String key : payItemAmount.keySet()) {
					grid2Cnt ++;
					grid2Vo = new TbPayPaymentRequestDtlDTO();					
					grid2Vo.set_id(id);
					//相同location_id,下述不用再show
					if(grid2Cnt == 1){
						grid2Vo.setLOCATION_ID(vo.getLOCATION_ID());
						grid2Vo.setPayBeginDate(vo.getPAY_BEGIN_DATE());
						grid2Vo.setLsEDate(vo.getLS_E_DATE());
					}
					grid2Vo.setRentAmt(vo.getRENT_AMT());
					grid2Vo.setSumPrepaymentBalance(dataObj.get("PO_PRE_LEFT_AMT")!=null?new BigDecimal(dataObj.get("PO_PRE_LEFT_AMT").toString()):BigDecimal.ZERO);
					grid2Vo.setPAYMENT_REQUEST_ITEM(key);
					grid2Vo.setItemDesc(payPublicUtilService.getPayPaymentItemDescByExpItem("REP", key));
					grid2Vo.setPAYMENT_REQ_BEGIN_DATE((Date) dataObj.get("PO_APP_START_DATE"));
					grid2Vo.setPAYMENT_REQ_END_DATE((Date) dataObj.get("PO_APP_END_DATE"));
					grid2Vo.setSumAmt(payItemAmount.get(key));
					//以下為新增所需,不在頁面呈現
					//請款金額未稅 : PO_TOT_APP_AMT+PO_TOT_HS_AMT+PO_TOT_IN_TAX_AMT-PO_TOT_BS_TAX_AMT
					taxExclusiveAmt = taxExclusiveAmt.add(dataObj.get("PO_TOT_APP_AMT")!=null?new BigDecimal(dataObj.get("PO_TOT_APP_AMT").toString()):BigDecimal.ZERO)
					.add(dataObj.get("PO_TOT_HS_AMT")!=null?new BigDecimal(dataObj.get("PO_TOT_HS_AMT").toString()):BigDecimal.ZERO)
					.add(dataObj.get("PO_TOT_IN_TAX_AMT")!=null?new BigDecimal(dataObj.get("PO_TOT_IN_TAX_AMT").toString()):BigDecimal.ZERO)
					.subtract(dataObj.get("PO_TOT_BS_TAX_AMT")!=null?new BigDecimal(dataObj.get("PO_TOT_BS_TAX_AMT").toString()):BigDecimal.ZERO);
					grid2Vo.setTAX_EXCLUSIVE_AMT(taxExclusiveAmt);
					grid2Vo.setBUSINESS_TAX(dataObj.get("PO_TOT_BS_TAX_AMT")!=null?new BigDecimal(dataObj.get("PO_TOT_BS_TAX_AMT").toString()):BigDecimal.ZERO);
					grid2List.add(grid2Vo);
				}
			}
			returnMap.put("grid2", grid2List);
			returnMap.put("grid3", grid3List);
			returnMap.put("key", timestampKey);
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			throw new NomsException("99", e.getMessage());
		}
		return returnMap;
	}
	/**
	 * 請款, 暫停使用
	 * 
	 * @param key 暫存資料KEY,以此作為第二次執行的標準
		@param processType 處理類別 
		@param paymentPeriod 付款週期 
		@param apYYYYMM 請款年月 YYYYMM
		@param appUser 申請人員代碼
		@param appDate 執行請款日期
	 * 
	 * @return cashReqNo 請款單號
	 */	
	@Transactional
	public String money(String key, String processType, String paymentPeriod, 
			String apYYYYMM, Date appDate){		
		Map<String, Object> map = null;
		String cashReqNo = null;
		try{
			map = payPublicUtilService.payPcProcAppAmt(key, "REP",processType, paymentPeriod, apYYYYMM, getLoginUser().getUsername(), appDate);
			cashReqNo = map.get("PO_CASH_REQ_NO").toString();
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			throw new NomsException("99", e.getMessage());
		}
		return cashReqNo;
	}	

	/**
	 * 請款, 暫停使用
	 * 
	 * @param list  請款單新增資料,內包含多筆payment請款資料,DTL請款明細,Payment付款資料
	 * 
	 * @return cashReqNo 請款單號
	 */	
	@Transactional
	public String money_(TbPayCashRequirementDTO payCashRequirementDTO){		
		Map<String, Object> map = null;
		Date today = new Date();
		Calendar cal = Calendar.getInstance();
		String nextYearMonth = null;
		try{
			//Step 1 : 取得請款單號
			map = new HashMap<String, Object>();
			map.put("PI_PREFIX", "REP");
			map.put("PI_APP_DATE", new SimpleDateFormat("yyyyMMdd").format(payCashRequirementDTO.getAPP_DATE()));
			pay001Dao.payPcGetSeqnoByMap(map);
			if (!map.get("PO_ERR_CDE").equals("00")) {
				log.error("自動產生請款單號Call PAY_PC_GET_SEQNO Error：ERR_CDE="
						+ map.get("PO_ERR_CDE") + ", PO_ERR_MSG="
						+ map.get("PO_ERR_MSG"));
				throw new NomsException("99", "自動產生請款單號執行錯誤：" + map.get("PO_ERR_MSG"));
			}
			payCashRequirementDTO.setCASH_REQ_NO((String) map.get("PO_SEQNO"));
			
			//Step 2 : 新增 請款單 TB_PAY_CASH_REQUIREMENT
			payCashRequirementDTO.setCR_TIME(today);
			payCashRequirementDTO.setCR_USER(getLoginUser().getUsername());
			payCashRequirementDTO.setMD_TIME(today);
			payCashRequirementDTO.setMD_USER(getLoginUser().getUsername());
			pay001Dao.insertPayCashRequirement(payCashRequirementDTO);	
			
			cal.setTime(new SimpleDateFormat("yyyyMMdd").parse(payCashRequirementDTO.getYEAR_MONTH()+"01"));
			cal.add(Calendar.MONTH, 1);
			nextYearMonth = new SimpleDateFormat("yyyyMM").format(cal.getTime());
			
			//Step 3 : 新增請款資料  PayPaymentRequest
			for(TbPayPaymentRequestDTO payPaymentRequestDTO : payCashRequirementDTO.getTbPayPaymentRequestDTOList()){
				payPaymentRequestDTO.setCASH_REQ_NO(payCashRequirementDTO.getCASH_REQ_NO());
				payPaymentRequestDTO.setCR_TIME(today);
				payPaymentRequestDTO.setCR_USER(getLoginUser().getUsername());
				payPaymentRequestDTO.setMD_TIME(today);
				payPaymentRequestDTO.setMD_USER(getLoginUser().getUsername());
				pay001Dao.insertPayPaymentRequest(payPaymentRequestDTO);
				//Step 3.0 : 更新 TB_PAY_CONTRACT_INFO
				pay001Dao.updatePayContractInfo(nextYearMonth, payCashRequirementDTO.getDOMAIN(), payPaymentRequestDTO.getCONTRACT_NO());
				
				//Step 3.1 : 新增請款明細資料 PayPaymentRequestDtl
				for(TbPayPaymentRequestDtlDTO payPaymentRequestDtl : payPaymentRequestDTO.getTbPayPaymentRequestDtlDTOList()){
					payPaymentRequestDtl.setPAYMENT_REQ_NO(payPaymentRequestDTO.getPAYMENT_REQ_NO());
					payPaymentRequestDtl.setCR_TIME(today);
					payPaymentRequestDtl.setCR_USER(getLoginUser().getUsername());
					payPaymentRequestDtl.setMD_TIME(today);
					payPaymentRequestDtl.setMD_USER(getLoginUser().getUsername());
					pay001Dao.insertPayPaymentRequestDtl(payPaymentRequestDtl);
					//Step 3.1.0 : 更新 TB_PAY_LOCATION_INFO
					pay001Dao.updatePayLocationInfo(
							payPaymentRequestDtl.getPAYMENT_REQ_BEGIN_DATE(), payPaymentRequestDtl.getPAYMENT_REQ_END_DATE(), payPaymentRequestDTO.getCASH_REQ_NO(), 
							payCashRequirementDTO.getDOMAIN(), payPaymentRequestDTO.getCONTRACT_NO(), payPaymentRequestDtl.getLOCATION_ID());
					
					//Step 3.1.1 : 新增付款資料 PayPayment
					for(TbPayPayment payPayment : payPaymentRequestDtl.getTbPayPaymentList()){
						payPayment.setPAYMENT_REQ_NO(payPaymentRequestDTO.getPAYMENT_REQ_NO());
						payPayment.setCR_TIME(today);
						payPayment.setCR_USER(getLoginUser().getUsername());
						payPayment.setMD_TIME(today);
						payPayment.setMD_USER(getLoginUser().getUsername());
						pay001Dao.insertPayPayment(payPayment);
					}
				}				
			}
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			throw new NomsException("99", e.getMessage());
		}
		return payCashRequirementDTO.getCASH_REQ_NO();
	}
	/**
	 * 供WORKFLOW審核呼叫 - 核可/駁回
	 * 
	 * @param cashReqNo 請款單號
	 * @param action 簽核狀態
	 * 
	 * @return true/false
	 */	
	@Transactional
	public boolean applyByWF(String cashReqNo, String action){		
		TbPayCashRequirement record = null;
		try{
			record = new TbPayCashRequirement();
			if(action.equals(AppConstants.WORKFLOW_REST_APPROVAL)){ //審核通過,狀態改核可             
				record.setSTATUS("C");
			}else if(action.equals(AppConstants.WORKFLOW_REST_REJECT)){
				record.setSTATUS("D");
			}
			record.setMD_USER(getLoginUser().getUsername());
			record.setMD_TIME(new Date());  
			pay001Dao.approved(cashReqNo, record);		
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			throw new NomsException("99", e.getMessage());
		}
		return true;
	}
}
