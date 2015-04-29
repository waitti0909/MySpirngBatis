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
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foya.dao.mybatis.model.TbAuthPersonnel;
import com.foya.dao.mybatis.model.TbAuthPersonnelExample;
import com.foya.dao.mybatis.model.TbLsLocPayment;
import com.foya.dao.mybatis.model.TbLsLocation;
import com.foya.dao.mybatis.model.TbPayCashRequirement;
import com.foya.dao.mybatis.model.TbPayCashRequirementExample;
import com.foya.dao.mybatis.model.TbPayElectricity;
import com.foya.dao.mybatis.model.TbPayElectricityExample;
import com.foya.dao.mybatis.model.TbPayLookupCode;
import com.foya.dao.mybatis.model.TbPayLookupCodeExample;
import com.foya.dao.mybatis.model.TbPayPaymentExample;
import com.foya.dao.mybatis.model.TbPayPaymentRequest;
import com.foya.dao.mybatis.model.TbPayPaymentRequestDtlExample;
import com.foya.dao.mybatis.model.TbPayPaymentRequestExample;
import com.foya.dao.mybatis.model.TbPayVoucherCredit;
import com.foya.dao.mybatis.model.TbPayVoucherCreditExample;
import com.foya.dao.mybatis.model.UTbPayPaymentRequestVoucherExample;
import com.foya.exception.NomsException;
import com.foya.noms.dao.pay.Pay003Dao;
import com.foya.noms.dto.pay.TbLsLocElecDTO;
import com.foya.noms.dto.pay.TbLsSiteDTO;
import com.foya.noms.dto.pay.TbPayCashRequirementDTO;
import com.foya.noms.dto.pay.TbPayElectricityDTO;
import com.foya.noms.dto.pay.TbPayPaymentDTO;
import com.foya.noms.dto.pay.TbPayPaymentRequestDTO;
import com.foya.noms.dto.pay.TbPayPaymentRequestDtlDTO;
import com.foya.noms.dto.pay.TbPayPaymentRequestVoucherDTO;
import com.foya.noms.resources.AppConstants;
import com.foya.noms.service.BaseService;
import com.foya.noms.service.workflow.WorkflowActionService;
import com.foya.workflow.exception.WorkflowException;

/**
 * 
 * 
 * <table>
 * <tr>
 * <th>版本</th>
 * <th>日期</th>
 * <th>詳細說明</th>
 * <th>modifier</th>
 * </tr>
 * <tr>
 * <td>1.0</td>
 * <td>2016/02/01</td>
 * <td>新建檔案</td>
 * <td>Smile</td>
 * </tr>
 * </table>
 * 
 * @author Smile
 * 
 */
@Service
public class PAY003Service extends BaseService {
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Inject
	private Pay003Dao pay003Dao;
	@Autowired
	private WorkflowActionService workflowActionService;
	
	@Inject
	private PayPublicUtilService payPublicUtilService;
	
	// 取得請款單號清單資料(By處理類別區分)
		public List<TbPayCashRequirementDTO> searchForPay003(String domain,String processType, String paymentPeriod,
				Date appStartDate, Date appEndDate, String cashReqNo, Set<String> status)throws ParseException {
			HashMap<String, Object> dataObj = new HashMap<String, Object>();
			dataObj.put("domain", domain);
			dataObj.put("processType", processType);
			dataObj.put("paymentPeriod", paymentPeriod);
			dataObj.put("appStartDate", appStartDate);
			dataObj.put("appEndDate", appEndDate);
			dataObj.put("cashReqNo",cashReqNo);
			dataObj.put("status",status);
			return  pay003Dao.searchForPay003(dataObj);
		}
		
		// 
		public List<TbPayPaymentRequestDTO> searchForPay003DtlMaster(String cashReqNo,String processType)throws ParseException {
			HashMap<String, Object> dataObj = new HashMap<String, Object>();
			dataObj.put("processType", processType);
			dataObj.put("cashReqNo",cashReqNo);
			List<TbPayPaymentRequestDTO> list = pay003Dao.searchForPay003DtlMaster(dataObj);
			return  list;
		}
		
		// 
		public List<TbPayElectricityDTO> searchForPay003DtlTable2(String contractNo,String yearMonth,String paymentReqNo)throws ParseException {
			HashMap<String, Object> dataObj = new HashMap<String, Object>();
			dataObj.put("contractNo", contractNo);
			dataObj.put("yearMonth",yearMonth);
			dataObj.put("paymentReqNo",paymentReqNo);
			List<TbPayElectricityDTO> list = pay003Dao.searchForPay003DtlTable2(dataObj);
			for(int i = 0 ; i < list.size() ; i++){
			log.debug("list get " + list.get(i).getELECTRICITY_METER_NBR());
			Map<String, Object> seqNoMap = new HashMap<String, Object>();
			seqNoMap.put("PI_contractNo", contractNo);
			seqNoMap.put("PI_electricity_Meter_Nbr", list.get(i).getELECTRICITY_METER_NBR());
			seqNoMap.put("PO_DEGREE", null);
			seqNoMap.put("PO_m_day", null);
			seqNoMap.put("PO_LAST_DEGREE", null);
			pay003Dao.callPayUsedDegreeMDayMap(seqNoMap);
			seqNoMap.get("PO_DEGREE");
			log.debug("callPayUsedDegreeMDayMap : PO_DEGREE [" + seqNoMap.get("PO_DEGREE") + "] PO_m_day [" + seqNoMap.get("PO_m_day") + "] PO_LAST_DEGREE [" + seqNoMap.get("PO_LAST_DEGREE") + "]");
			BigDecimal usedDegree = (BigDecimal)seqNoMap.get("PO_DEGREE");
			BigDecimal mDay = (BigDecimal)seqNoMap.get("PO_m_day");
			BigDecimal lastDegree = (BigDecimal)seqNoMap.get("PO_LAST_DEGREE");
			list.get(i).setUsed_degree_day(usedDegree);
			list.get(i).setM_day(mDay);
			list.get(i).setLast_used_degree(lastDegree);
			}
			return  list;
		}
		
		// 
		public Map<String, Object> selectDtl4PAY003ATb2(String contractNo, 
				String domain, Date paymentReqBeginDate, Date paymentReqEndDate,
				Date appDate, String paymentMonth, String processType){
			log.debug("selectDtl4PAY003ATb2 : contractNo [" + contractNo + "] domain [" + domain+ "] paymentReqBeginDate [" + paymentReqBeginDate+ "] paymentReqEndDate [" + paymentReqEndDate+ "] appDate [" + appDate + "] paymentMonth [" + paymentMonth + "]processType["+processType+"]");
			Map<String, Object> returnMap = new HashMap<String, Object>();
			List<TbPayElectricityDTO> list = new ArrayList<TbPayElectricityDTO>();
			TbPayElectricityDTO elelistVo;
			HashMap<String, Object> dataObj = new HashMap<String, Object>();
			dataObj.put("contractNo", contractNo);
			
			try{
			if(processType.equals("ELE01") || processType.equals("ELE05")){
				dataObj.put("processType", processType);
				List<TbLsLocElecDTO> list2 = pay003Dao.selectDtl4PAY003ATb2loc(dataObj);
				for(TbLsLocElecDTO vo : list2){
					elelistVo = new TbPayElectricityDTO();
					elelistVo.setCONTRACT_NO(contractNo);
					elelistVo.setELECTRICITY_TYPE(processType);
					elelistVo.setELECTRICITY_METER_NBR(vo.getELECTRICITY_METER_NBR());
					elelistVo.setElectricityDscr(vo.getElectricityType());
					elelistVo.setChrg_mode(vo.getCHRG_MODE());
					elelistVo.setElec_begin_date(vo.getELEC_BEGIN_DATE());
					elelistVo.setElec_end_date(vo.getELEC_END_DATE());
					elelistVo.setPAYMENT_REQ_BEGIN_DATE(null);
					elelistVo.setPAYMENT_REQ_END_DATE(null);
					elelistVo.setUsed_degree_day(BigDecimal.ZERO);
					elelistVo.setM_day(BigDecimal.ZERO);
					elelistVo.setLast_used_degree(BigDecimal.ZERO);
//					elelistVo.setTOTAL_USED_DEGREE(null);
//					elelistVo.setUSED_DEGREE(null);
//					elelistVo.setRatio(null);
//					elelistVo.setMEMO(null);
					elelistVo.setPAYMENT_REQ_AMT(BigDecimal.ZERO);
//					elelistVo.setIF_NO_SITE_MAPPING(null);
					list.add(elelistVo);
				}
			}else{
				list = pay003Dao.selectDtl4PAY003ATb2(dataObj);	
			}
			for(int i = 0 ; i < list.size() ; i++){
			log.debug("list get " + list.get(i).getELECTRICITY_METER_NBR());
			Map<String, Object> seqNoMap = new HashMap<String, Object>();
			seqNoMap.put("PI_contractNo", contractNo);
			seqNoMap.put("PI_electricity_Meter_Nbr", list.get(i).getELECTRICITY_METER_NBR());
			seqNoMap.put("PO_DEGREE", null);
			seqNoMap.put("PO_m_day", null);
			seqNoMap.put("PO_LAST_DEGREE", null);
			pay003Dao.callPayUsedDegreeMDayMap(seqNoMap);
			seqNoMap.get("PO_DEGREE");
			log.debug("callPayUsedDegreeMDayMap : PO_DEGREE [" + seqNoMap.get("PO_DEGREE") + "] PO_m_day [" + seqNoMap.get("PO_m_day") + "] PO_LAST_DEGREE [" + seqNoMap.get("PO_LAST_DEGREE") + "]");
			BigDecimal usedDegree = (BigDecimal)seqNoMap.get("PO_DEGREE");
			BigDecimal mDay = (BigDecimal)seqNoMap.get("PO_m_day");
			BigDecimal lastDegree = (BigDecimal)seqNoMap.get("PO_LAST_DEGREE");
			list.get(i).setUsed_degree_day(usedDegree);
			list.get(i).setM_day(mDay);
			list.get(i).setLast_used_degree(lastDegree);
			}
			returnMap.put("grid", list);
			} catch (Exception e) {
				log.error(ExceptionUtils.getFullStackTrace(e));
				throw new NomsException("99", e.getMessage());
			}
			return returnMap;

		}
		
		/**
		 * 新增- 查詢  請款清單資料-Detail Page DetailTable2 &3  
		 * 
		 * @param contractNo 租約編號
		 * @param domain 維運區
		 * @param paymentReqBeginDate 請款日期(起)
		 * @param paymentReqEndDate 請款日期(迄)
		 * @param appDate 請款申請日期
		 * @param paymentMonth 付款月數(計算 本次請款: 租金*月數)
		 * 
		 * @return List<TbLsLocPayment>
		 */	
		@SuppressWarnings("unchecked")
		public Map<String, Object> selectDetail2ByProcess(String contractNo, 
				String domain, Date paymentReqBeginDate, Date paymentReqEndDate,
				Date appDate, String paymentMonth, String processType){
			log.debug("selectDetail2ByProcess : contractNo [" + contractNo + "] domain [" + domain+ "] paymentReqBeginDate [" + paymentReqBeginDate+ "] paymentReqEndDate [" + paymentReqEndDate+ "] appDate [" + appDate + "] paymentMonth [" + paymentMonth + "]processType["+processType+"]");
			Map<String, Object> returnMap = new HashMap<String, Object>();
			List<TbPayPaymentRequestDtlDTO> grid2List = new ArrayList<TbPayPaymentRequestDtlDTO>();	
			TbPayPaymentRequestDtlDTO grid2Vo;
			int id =0;
			String payItem = null;
			BigDecimal taxExclusiveAmt ;
			try{
				//Step 1 : 先取 TB_LS_LOCATION
				List<TbLsLocation> lsLocationList = pay003Dao.getDistinctLocationId(contractNo);
				
				//Step 2  : 取得 其他資料
				Map<String,Object> dataObj = null;
				List<Map<String,Object>> list = null;
				for(TbLsLocation vo : lsLocationList){
					id++;
					taxExclusiveAmt = BigDecimal.ZERO;
					dataObj = payPublicUtilService.payPcGetAppAmt(
							domain, contractNo, vo.getLOCATION_ID(), processType.equals("ELE05")? "ED" : "E", 
							paymentReqBeginDate, paymentReqEndDate, appDate, paymentMonth, "F", null);
					list = (List<Map<String, Object>>) dataObj.get("PO_CURSOR");
					for(Map<String,Object> m : list){
						payItem = (String)m.get("PAY_ITEM");
					}
					grid2Vo = new TbPayPaymentRequestDtlDTO();
					grid2Vo.set_id(id);
					grid2Vo.setLOCATION_ID(vo.getLOCATION_ID());
					grid2Vo.setPayBeginDate(vo.getPAY_BEGIN_DATE());
					grid2Vo.setLsEDate(vo.getLS_E_DATE());
					grid2Vo.setRentAmt(vo.getRENT_AMT());
					//grid2Vo.setSumPrepaymentBalance(sumPrepaymentBalance); 待確認
					grid2Vo.setPAYMENT_REQUEST_ITEM(payItem);
					grid2Vo.setItemDesc(payPublicUtilService.getPayPaymentItemDescByExpItem("ELP", payItem));
					grid2Vo.setPAYMENT_REQ_BEGIN_DATE((Date) dataObj.get("PO_APP_START_DATE"));
					grid2Vo.setPAYMENT_REQ_END_DATE((Date) dataObj.get("PO_APP_END_DATE"));
					grid2Vo.setSumAmt(dataObj.get("PO_TOT_APP_AMT")!=null?new BigDecimal(dataObj.get("PO_TOT_APP_AMT").toString()):BigDecimal.ZERO);
					//以下為新增所需,不在頁面呈現
					//請款金額未稅 : PO_TOT_APP_AMT+PO_TOT_HS_AMT+PO_TOT_IN_TAX_AMT-PO_TOT_BS_TAX_AMT
					taxExclusiveAmt = taxExclusiveAmt.add(dataObj.get("PO_TOT_APP_AMT")!=null?new BigDecimal(dataObj.get("PO_TOT_APP_AMT").toString()):BigDecimal.ZERO)
					.add(dataObj.get("PO_TOT_HS_AMT")!=null?new BigDecimal(dataObj.get("PO_TOT_HS_AMT").toString()):BigDecimal.ZERO)
					.add(dataObj.get("PO_TOT_IN_TAX_AMT")!=null?new BigDecimal(dataObj.get("PO_TOT_IN_TAX_AMT").toString()):BigDecimal.ZERO)
					.subtract(dataObj.get("PO_TOT_BS_TAX_AMT")!=null?new BigDecimal(dataObj.get("PO_TOT_BS_TAX_AMT").toString()):BigDecimal.ZERO);
					grid2Vo.setTAX_EXCLUSIVE_AMT(taxExclusiveAmt);
					grid2Vo.setBUSINESS_TAX(dataObj.get("PO_TOT_BS_TAX_AMT")!=null?new BigDecimal(dataObj.get("PO_TOT_BS_TAX_AMT").toString()):BigDecimal.ZERO);
					grid2Vo.setSumincomeTax(dataObj.get("PO_TOT_IN_TAX_AMT")!=null?new BigDecimal(dataObj.get("PO_TOT_IN_TAX_AMT").toString()):BigDecimal.ZERO);
					grid2List.add(grid2Vo);
				}
				returnMap.put("grid2", grid2List);

			} catch (Exception e) {
				log.error(ExceptionUtils.getFullStackTrace(e));
				throw new NomsException("99", e.getMessage());
			}
			return returnMap;
		}	
		
		// 
		public List<TbLsSiteDTO> selectDtlForPAY003Table3(String contractNo,String electricityMeterNbr,Date paymentReqBeginDate)throws ParseException {
			HashMap<String, Object> dataObj = new HashMap<String, Object>();
			dataObj.put("contractNo", contractNo);
			dataObj.put("electricityMeterNbr",electricityMeterNbr);
			dataObj.put("paymentReqBeginDate",paymentReqBeginDate);
			return  pay003Dao.selectDtlForPAY003Table3(dataObj);
		}
		
		// 
		public List<TbPayPaymentRequestVoucherDTO> selectPay003EditVoucher(String apNo,String poNo)throws ParseException {
			HashMap<String, Object> dataObj = new HashMap<String, Object>();
			dataObj.put("apNo", apNo);
			dataObj.put("poNo", poNo);
			return  pay003Dao.selectPay003EditVoucher(dataObj);
		}
		
		
		
		/**
		 * 取得憑證資料 by page
		 * @param example
		 * @return
		 */
		public List<TbPayPaymentRequestVoucherDTO> selectPayPaymentRequestVoucherByExamplePage(UTbPayPaymentRequestVoucherExample example) {
			return pay003Dao.selectPayPaymentRequestVoucherByExamplePage(example);
		}
		/**
		 * 儲存憑證資料異動結果
		 * @param map
		 * @throws NomsException
		 */
		@Transactional
		public void creditSave(Map<String, String> map,String type) throws NomsException {
			try {
				String mst_ap_no = map.get("mst_ap_no");
				String mst_po_no = map.get("mst_po_no");
				String[] id = map.get("id").split(",");
				String[] voucher_type = map.get("voucher_type").split(",");
				String[] voucher_nbr = map.get("voucher_nbr").split(",");
				String[] voucher_date = map.get("voucher_date").split(",");
				String[] vat_number = map.get("vat_number").split(",");
				String[] tax_exclusive_amt = map.get("tax_exclusive_amt").split(",");
				String voucherNo = null; // 憑證處理單號

				// 刪除憑證資料，並取得已存在的憑證處理單號
				UTbPayPaymentRequestVoucherExample example = new UTbPayPaymentRequestVoucherExample();
				UTbPayPaymentRequestVoucherExample.Criteria cr = example.createCriteria();
				if(type.equals("A")){
					cr.andPROCESS_TYPEEqualTo("CRQ").andEqualTo("B.CASH_REQ_AP_NO", mst_ap_no).andEqualTo("B.CONTRACT_PO_NO", mst_po_no);
				}else{
					cr.andPROCESS_TYPEEqualTo("CRQ").andEqualTo("B.CASH_REQ_AP_NO", mst_ap_no);
				}
				List<TbPayPaymentRequestVoucherDTO> list = pay003Dao.selectPayPaymentRequestVoucherByExample(example);
				for (int i = 0; list != null && i < list.size(); i++) {
					if (i == 0) {
						voucherNo = list.get(i).getVOUCHER_NO();
					}
					creditDelete(list.get(i).getSEQ_NBR());
				}

				// 若不存在憑證處理單號，則自動產生
				if (voucherNo == null) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					Map<String, Object> seqNoMap = new HashMap<String, Object>();
					seqNoMap.put("PI_PREFIX", "CRQ");
					seqNoMap.put("PI_APP_DATE", sdf.format(new Date()));
					seqNoMap.put("PO_SEQNO", null);
					seqNoMap.put("PO_ERR_CDE", null);
					seqNoMap.put("PO_ERR_MSG", null);
					pay003Dao.callPayPcGetSeqnoByMap(seqNoMap);
					if (!seqNoMap.get("PO_ERR_CDE").equals("00")) {
						log.error("自動產生憑證處理單號Call PAY_PC_GET_SEQNO Error：ERR_CDE=" + seqNoMap.get("PO_ERR_CDE")
								+ ", PO_ERR_MSG=" + seqNoMap.get("PO_ERR_MSG"));
						throw new NomsException("99", "自動產生憑證處理單號執行錯誤：" + seqNoMap.get("PO_ERR_MSG"));
					}
					voucherNo = (String) seqNoMap.get("PO_SEQNO");
				}
				if(type.equals("A")){
					String[] spstatus = map.get("spstatus").split(",");
					for (int i = 0; i < id.length; i++) {
						if(spstatus[i].equals("0")){
						Map<String, String> insMap = new HashMap<String, String>();
						insMap.put("ap_no", mst_ap_no);
						insMap.put("po_no", mst_po_no);
						insMap.put("voucher_no", voucherNo);
						insMap.put("voucher_type", voucher_type[i]);
						insMap.put("voucher_nbr", voucher_nbr[i]);
						insMap.put("voucher_date", voucher_date[i]);
						insMap.put("vat_number", vat_number[i]);
						insMap.put("tax_exclusive_amt", tax_exclusive_amt[i]);
						this.creditAdd(insMap);
						}
					}
				}else{
					for (int i = 0; i < id.length; i++) {
						Map<String, String> insMap = new HashMap<String, String>();
						insMap.put("ap_no", mst_ap_no);
						insMap.put("po_no", mst_po_no);
						insMap.put("voucher_no", voucherNo);
						insMap.put("voucher_type", voucher_type[i]);
						insMap.put("voucher_nbr", voucher_nbr[i]);
						insMap.put("voucher_date", voucher_date[i]);
						insMap.put("vat_number", vat_number[i]);
						insMap.put("tax_exclusive_amt", tax_exclusive_amt[i]);
						this.creditAdd(insMap);
					}
				}
			} catch (Exception e) {
				log.error(ExceptionUtils.getFullStackTrace(e));
				throw new NomsException("99", e.getMessage());
			}
		}
		/**
		 * 新增憑證資料
		 * @param map
		 */
		@Transactional
		public void creditAdd(Map<String, String> map) throws NomsException {
			try {
				// 產生憑證處理單號
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				Map<String, Object> seqNoMap = new HashMap<String, Object>();
				seqNoMap.put("PI_PREFIX", "CRQ");
				seqNoMap.put("PI_APP_DATE", sdf.format(new Date()));
				seqNoMap.put("PO_SEQNO", null);
				seqNoMap.put("PO_ERR_CDE", null);
				seqNoMap.put("PO_ERR_MSG", null);
				pay003Dao.callPayPcGetSeqnoByMap(seqNoMap);
				if (!seqNoMap.get("PO_ERR_CDE").equals("00")) {
					log.error("自動產生憑證處理單號Call PAY_PC_GET_SEQNO Error：ERR_CDE=" + seqNoMap.get("PO_ERR_CDE")
							+ ", PO_ERR_MSG=" + seqNoMap.get("PO_ERR_MSG"));
					throw new NomsException("99", "自動產生憑證處理單號執行錯誤：" + seqNoMap.get("PO_ERR_MSG"));
				}
				String voucherNo = (String) seqNoMap.get("PO_SEQNO");

				// 新增憑證主檔資料
				sdf = new SimpleDateFormat("yyyy/MM/dd");
				TbPayPaymentRequestVoucherDTO voucherRecord = new TbPayPaymentRequestVoucherDTO();
				voucherRecord.setPROCESS_TYPE("CRQ");
				voucherRecord.setVOUCHER_NO(voucherNo);
				voucherRecord.setVOUCHER_TYPE(map.get("voucher_type"));
				voucherRecord.setVOUCHER_NBR(map.get("voucher_nbr"));
				voucherRecord.setVOUCHER_DATE(sdf.parse(map.get("voucher_date")));
				voucherRecord.setVAT_NUMBER(map.get("vat_number"));
				voucherRecord.setTAX_EXCLUSIVE_AMT(new BigDecimal(map.get("tax_exclusive_amt")));

				// 取得營業稅business_tax
				Map<String, Object> taxRateMap = new HashMap<String, Object>();
				taxRateMap.put("PI_TYPE", "3"); // 計算營業稅
				taxRateMap.put("PI_AMT", voucherRecord.getTAX_EXCLUSIVE_AMT()); // 傳入未稅金額tax_exclusive_amt
				pay003Dao.callPayFnGetTax(taxRateMap);
				voucherRecord.setBUSINESS_TAX(new BigDecimal(((Integer) taxRateMap.get("RETURN_VALUE")).toString()));

				voucherRecord.setCREDIT_MADE_AMT(voucherRecord.getTAX_EXCLUSIVE_AMT());
				voucherRecord.setCREDIT_MADE_TAX(voucherRecord.getBUSINESS_TAX());
				voucherRecord.setCR_USER(getLoginUser().getUsername());
				voucherRecord.setCR_TIME(new Date());
				voucherRecord.setMD_USER(getLoginUser().getUsername());
				voucherRecord.setMD_TIME(new Date());
				pay003Dao.insertPayPaymentRequestVoucherSelective(voucherRecord);
				if (voucherRecord.getSEQ_NBR() == null) {
					throw new NomsException("99", "無法取得新增後的憑證主檔SEQ_NO");
				}

				// 新增憑證明細資料
				TbPayVoucherCredit creditRecord = new TbPayVoucherCredit();
				creditRecord.setMST_SEQ_NBR(voucherRecord.getSEQ_NBR());
				creditRecord.setCASH_REQ_AP_NO(map.get("ap_no"));
				creditRecord.setCONTRACT_PO_NO(map.get("po_no"));
				creditRecord.setCREDIT_AMT(voucherRecord.getTAX_EXCLUSIVE_AMT());
				creditRecord.setCREDIT_TAX(voucherRecord.getBUSINESS_TAX());
				creditRecord.setCREDIT_DATE(new Date());
				creditRecord.setCR_USER(getLoginUser().getUsername());
				creditRecord.setCR_TIME(new Date());
				creditRecord.setMD_USER(getLoginUser().getUsername());
				creditRecord.setMD_TIME(new Date());
				pay003Dao.insertPayVoucherCreditSelective(creditRecord);
			} catch (ParseException e) {
				log.error(ExceptionUtils.getFullStackTrace(e));
				throw new NomsException("99", e.getMessage());
			} catch (Exception e) {
				log.error(ExceptionUtils.getFullStackTrace(e));
				throw new NomsException("99", e.getMessage());
			}
		}

		/**
		 * 修改憑證資料
		 * @param map
		 */
		@Transactional
		public void creditEdit(Map<String, String> map) throws NomsException {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

				// 修改憑證主檔資料
				TbPayPaymentRequestVoucherDTO voucherRecord = new TbPayPaymentRequestVoucherDTO();
				voucherRecord.setVOUCHER_TYPE(map.get("voucher_type"));
				voucherRecord.setVOUCHER_NBR(map.get("voucher_nbr"));
				voucherRecord.setVOUCHER_DATE(sdf.parse(map.get("voucher_date")));
				voucherRecord.setVAT_NUMBER(map.get("vat_number"));
				voucherRecord.setTAX_EXCLUSIVE_AMT(new BigDecimal(map.get("tax_exclusive_amt")));

				// 取得營業稅business_tax
				Map<String, Object> taxRateMap = new HashMap<String, Object>();
				taxRateMap.put("PI_TYPE", "3"); // 計算營業稅
				taxRateMap.put("PI_AMT", voucherRecord.getTAX_EXCLUSIVE_AMT()); // 傳入未稅金額tax_exclusive_amt
				pay003Dao.callPayFnGetTax(taxRateMap);
				voucherRecord.setBUSINESS_TAX(new BigDecimal(((Integer) taxRateMap.get("RETURN_VALUE")).toString()));

				voucherRecord.setCREDIT_MADE_AMT(voucherRecord.getTAX_EXCLUSIVE_AMT());
				voucherRecord.setCREDIT_MADE_TAX(voucherRecord.getBUSINESS_TAX());
				voucherRecord.setMD_USER(getLoginUser().getUsername());
				voucherRecord.setMD_TIME(new Date());

				UTbPayPaymentRequestVoucherExample voucherExample = new UTbPayPaymentRequestVoucherExample();
				voucherExample.createCriteria().andEqualTo("SEQ_NBR", Integer.valueOf(map.get("seq_nbr")).longValue());
				pay003Dao.updatePayPaymentRequestVoucherByExampleSelective(voucherRecord, voucherExample);

				// 修改憑證明細資料
				TbPayVoucherCredit creditRecord = new TbPayVoucherCredit();
				creditRecord.setCREDIT_AMT(voucherRecord.getTAX_EXCLUSIVE_AMT());
				creditRecord.setCREDIT_TAX(voucherRecord.getBUSINESS_TAX());
				creditRecord.setCREDIT_DATE(new Date());
				creditRecord.setMD_USER(getLoginUser().getUsername());
				creditRecord.setMD_TIME(new Date());

				TbPayVoucherCreditExample creditExample = new TbPayVoucherCreditExample();
				creditExample.createCriteria().andMST_SEQ_NBREqualTo(Integer.valueOf(map.get("seq_nbr")).longValue());
				pay003Dao.updatePayVoucherCreditByExampleSelective(creditRecord, creditExample);
			} catch (Exception e) {
				log.error(ExceptionUtils.getFullStackTrace(e));
				throw new NomsException("99", e.getMessage());
			}
		}

		/**
		 * 刪除憑證資料
		 * @param seq_nbr 憑證流水號
		 * @throws NomsException
		 */
		private void creditDelete(long seq_nbr) throws NomsException {
			try {
				// 刪除憑證主檔資料
				UTbPayPaymentRequestVoucherExample voucherExample = new UTbPayPaymentRequestVoucherExample();
				voucherExample.createCriteria().andEqualTo("SEQ_NBR", seq_nbr);
				pay003Dao.deletePayPaymentRequestVoucherByExample(voucherExample);

				// 刪除憑證明細資料
				TbPayVoucherCreditExample creditExample = new TbPayVoucherCreditExample();
				creditExample.createCriteria().andMST_SEQ_NBREqualTo(seq_nbr);
				pay003Dao.deletePayVoucherCreditByExample(creditExample);
			} catch (Exception e) {
				log.error(ExceptionUtils.getFullStackTrace(e));
				throw new NomsException("99", e.getMessage());
			}
		}

		/**
		 * 取得參數設定資料
		 * @param example
		 * @return
		 */
		public List<TbPayLookupCode> selectPayLookupCodeByExample(TbPayLookupCodeExample example) {
			return pay003Dao.selectPayLookupCodeByExample(example);
		}
		
		/**
		 * 取得人員清單
		 * @param example
		 * @return
		 */
		public List<TbAuthPersonnel> selectAuthPersonnelByExample(TbAuthPersonnelExample example) {
			return pay003Dao.selectAuthPersonnelByExample(example);
		}
		
		@Transactional
		public int updateByPAY003(String cashReqNo,String appUser)throws NomsException{
			try{
			pay003Dao.updateByPAY003(cashReqNo,appUser);
			//簽核
			workflowActionService.apply("PayElectricBill", cashReqNo, "電費請款");
			
			return 1;
			} catch (WorkflowException e) {
				throw new NomsException(e.getMessage());
			}catch (Exception e) {
				log.error(ExceptionUtils.getFullStackTrace(e));
				throw new NomsException(e.getMessage());
			}
		}
		
		/**
		 * 刪除明細
		 * @param map
		 */
		@Transactional
		public int deletePayPaymentRequestDtl(List<TbPayPaymentRequest> delArray)throws NomsException{
			try {
				for (int i = 0; i < delArray.size(); i++) {
					TbPayPaymentRequest key = delArray.get(i);
					log.debug("PAYMENT_REQ_NO : "+key.getPAYMENT_REQ_NO());
					TbPayPaymentRequestDtlExample example = new TbPayPaymentRequestDtlExample();
					TbPayPaymentRequestDtlExample.Criteria cr = example.createCriteria();
					cr.andPAYMENT_REQ_NOEqualTo(key.getPAYMENT_REQ_NO());
					pay003Dao.deletePayPaymentRequestDtlByExample(example);
					
					TbPayPaymentRequestExample example2 = new TbPayPaymentRequestExample();
					TbPayPaymentRequestExample.Criteria cr2 = example2.createCriteria();
					cr2.andPAYMENT_REQ_NOEqualTo(key.getPAYMENT_REQ_NO());
					pay003Dao.deletePayPaymentRequestByExample(example2);
					
					pay003Dao.deletePaymentRequestVoucherPay003(key.getPAYMENT_REQ_NO());
					
					TbPayVoucherCreditExample example3 = new TbPayVoucherCreditExample();
					TbPayVoucherCreditExample.Criteria cr3 = example3.createCriteria();
					cr3.andPAYMENT_REQ_NOEqualTo(key.getPAYMENT_REQ_NO());
					pay003Dao.deletePayVoucherCreditByExample(example3);
					
					TbPayPaymentExample example4 = new TbPayPaymentExample();
					TbPayPaymentExample.Criteria cr4 = example4.createCriteria();
					cr4.andPAYMENT_REQ_NOEqualTo(key.getPAYMENT_REQ_NO());
					pay003Dao.deletePayPaymentByExample(example4);
					
					List<TbPayPaymentRequestDTO> payList = pay003Dao.selectCountPay003(key.getCASH_REQ_NO());
					log.debug("payList.size() : "+payList.size());
					
					if(payList.size() == 0){
					TbPayCashRequirementExample example5 = new TbPayCashRequirementExample();
					TbPayCashRequirementExample.Criteria cr5 = example5.createCriteria();
					cr5.andCASH_REQ_NOEqualTo(key.getCASH_REQ_NO());
					pay003Dao.deletePayCashRequirementExample(example5);
					}
					
					
				}
				return 1;
			} catch (Exception e) {
				log.error(ExceptionUtils.getFullStackTrace(e));
				throw new NomsException(e.getMessage());
			}
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
		public List<TbPayPaymentRequestDTO> selectProcess(String domain,
				String processType,
				String appUser,
				Date appDate,
				String yearMonth,
				String paymentPeriod){
			List<TbPayPaymentRequestDTO> list = null;
			HashMap<String,Object> dataObj = new HashMap<String,Object>();
			dataObj.put("domain", domain);
			dataObj.put("processType", processType);
			dataObj.put("appUser", appUser);
			dataObj.put("appDate", appDate);
			dataObj.put("yearMonth", yearMonth);
			dataObj.put("paymentPeriod", paymentPeriod);
			log.debug("domain["+domain+"]processType["+processType+"]appUser["+appUser+"]appDate["+appDate+"]yearMonth["+yearMonth+"]paymentPeriod["+paymentPeriod+"]");
			/*ELE01	[借電_固定金額]
			ELE05	[押金]
			Other : ELE02 ELE03 ELE04 ELE06	 [請電] [借電_抄表] [借電_其他] [預付]
			*/
			if(processType.equals("ELE01")){
				log.debug("selectPayContractInfo4ELE01");
				list = pay003Dao.selectPayContractInfo4ELE01(dataObj);
			}else if(processType.equals("ELE05")){
				log.debug("selectPayContractInfo4ELE05");
				list = pay003Dao.selectPayContractInfo4ELE05(dataObj);
			}else if(processType.equals("ELE02") || processType.equals("ELE03")||processType.equals("ELE04")||processType.equals("ELE06")) {
				log.debug("selectPayContractInfo4ELEOther");
				list = pay003Dao.selectPayContractInfo4ELEOther(dataObj);
			}
			return list;
		}
		
		/**
		 * 請款
		 * 
		 * @param list  請款單新增資料,內包含多筆payment請款資料,DTL請款明細,Payment付款資料
		 * 
		 * @return cashReqNo 請款單號
		 */	
		@Transactional
		public String money(TbPayCashRequirementDTO payCashRequirementDTO){		
			Map<String, Object> map = null;
			Date today = new Date();
			Calendar cal = Calendar.getInstance();
			String nextYearMonth = null;
			Integer s_tax = 0 ;
			try{
				//Step 1 : 取得請款單號
				map = new HashMap<String, Object>();
				map.put("PI_PREFIX", "ELP");
				map.put("PI_APP_DATE", new SimpleDateFormat("yyyyMMdd").format(payCashRequirementDTO.getAPP_DATE()));
				pay003Dao.payPcGetSeqnoByMap(map);
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
				pay003Dao.insertPayCashRequirement(payCashRequirementDTO);	
				log.debug("["+payCashRequirementDTO.getCASH_REQ_NO()+"]["+payCashRequirementDTO.getDOMAIN()+"]["+payCashRequirementDTO.getPROCESS_TYPE()+"]["+payCashRequirementDTO.getPAYMENT_PERIOD()+"]["+payCashRequirementDTO.getAPP_USER()+"]["+payCashRequirementDTO.getAPP_DATE()+"]["+payCashRequirementDTO.getYEAR_MONTH()+"]");
				cal.setTime(new SimpleDateFormat("yyyyMMdd").parse(payCashRequirementDTO.getYEAR_MONTH()+"01"));
				cal.add(Calendar.MONTH, 1);
				nextYearMonth = new SimpleDateFormat("yyyyMM").format(cal.getTime());
				
				//Step 3 : 新增請款資料  PayPaymentRequest
				for(TbPayPaymentRequestDTO payPaymentRequestDTO : payCashRequirementDTO.getTbPayPaymentRequestDTOList()){
					for(TbPayPaymentRequestVoucherDTO payPaymentRequestVoucherDTO : payCashRequirementDTO.getTbPayPaymentRequestVoucherDTOList()){
						log.debug("LIST SIZE : [" + payCashRequirementDTO.getTbPayPaymentRequestVoucherDTOList().size()
								+"]mst_ap_no : ["+payCashRequirementDTO.getCASH_REQ_NO()
								+"] mst_po_no : [" + payPaymentRequestVoucherDTO.getMst_po_no()
								+"] spstatus : [" + payPaymentRequestVoucherDTO.getSpstatus()
								+"] id : [" + payPaymentRequestVoucherDTO.get_id()
								+"] voucher_type : ["+ payPaymentRequestVoucherDTO.getVOUCHER_TYPE()
								+"] voucher_nbr : ["+payPaymentRequestVoucherDTO.getVOUCHER_NBR()
								+"] voucher_date : ["+payPaymentRequestVoucherDTO.getVoucherdate()
								+"] vat_number : ["+payPaymentRequestVoucherDTO.getVAT_NUMBER()
								+"] tax_exclusive_amt : ["+ payPaymentRequestVoucherDTO.getTaxexclusiveamt()+"]"
								);
//						if(payPaymentRequestDTO.getCONTRACT_NO().equals(payPaymentRequestVoucherDTO.getMst_po_no())){
						Map<String, String> insMap = new HashMap<String, String>();
						insMap.put("mst_ap_no", payCashRequirementDTO.getCASH_REQ_NO());
						insMap.put("mst_po_no", payPaymentRequestVoucherDTO.getMst_po_no());
						insMap.put("spstatus", payPaymentRequestVoucherDTO.getSpstatus());
						insMap.put("id", payPaymentRequestVoucherDTO.get_id());
						insMap.put("voucher_type", payPaymentRequestVoucherDTO.getVOUCHER_TYPE());
						insMap.put("voucher_nbr", payPaymentRequestVoucherDTO.getVOUCHER_NBR());
						insMap.put("voucher_date", payPaymentRequestVoucherDTO.getVoucherdate());
						insMap.put("vat_number", payPaymentRequestVoucherDTO.getVAT_NUMBER());
						insMap.put("tax_exclusive_amt", payPaymentRequestVoucherDTO.getTaxexclusiveamt());
						creditSave(insMap,"A");
//						}
					}
					payPaymentRequestDTO.setCASH_REQ_NO(payCashRequirementDTO.getCASH_REQ_NO());
					payPaymentRequestDTO.setCR_TIME(today);
					payPaymentRequestDTO.setCR_USER(getLoginUser().getUsername());
					payPaymentRequestDTO.setMD_TIME(today);
					payPaymentRequestDTO.setMD_USER(getLoginUser().getUsername());
					pay003Dao.insertPayPaymentRequest(payPaymentRequestDTO);
					//Step 3.0 : 更新 TB_PAY_CONTRACT_INFO
					pay003Dao.updatePayContractInfo(nextYearMonth, payCashRequirementDTO.getDOMAIN(), payPaymentRequestDTO.getCONTRACT_NO());
					
					//Step 3.1 : 新增請款明細資料 PayPaymentRequestDtl
					for(TbPayPaymentRequestDtlDTO payPaymentRequestDtl : payPaymentRequestDTO.getTbPayPaymentRequestDtlDTOList()){
						List<TbLsLocPayment> tbLsLocPayment2 = pay003Dao.selectLsLocPayByExample(payPaymentRequestDtl.getContractNo(), payPaymentRequestDtl.getLOCATION_ID(),payPaymentRequestDtl.getElectricityType().equals("ELE05")?"ED":"E");
						payPaymentRequestDtl.setPAYMENT_REQ_USER_ID(tbLsLocPayment2.get(0).getLESSOR_ID());
						payPaymentRequestDtl.setPAYMENT_REQ_NO(payPaymentRequestDTO.getPAYMENT_REQ_NO());
						payPaymentRequestDtl.setCR_TIME(today);
						payPaymentRequestDtl.setCR_USER(getLoginUser().getUsername());
						payPaymentRequestDtl.setMD_TIME(today);
						payPaymentRequestDtl.setMD_USER(getLoginUser().getUsername());
						pay003Dao.insertPayPaymentRequestDtl(payPaymentRequestDtl);
						//Step 3.1.0 : 更新 TB_PAY_LOCATION_INFO
						pay003Dao.updatePayLocationInfo(
								payPaymentRequestDtl.getPAYMENT_REQ_BEGIN_DATE(), payPaymentRequestDtl.getPAYMENT_REQ_END_DATE(), payPaymentRequestDTO.getCASH_REQ_NO(), 
								payCashRequirementDTO.getDOMAIN(), payPaymentRequestDTO.getCONTRACT_NO(), payPaymentRequestDtl.getLOCATION_ID());
						//Step 3.2 : 新增請款明細資料 PayPaymentRequestDtl
						for(TbPayElectricityDTO tbPayElectricity : payPaymentRequestDTO.getTbPayElectricityDTOList()){
							tbPayElectricity.setUSE_TYPE("E");
							tbPayElectricity.setIF_AUTO_DEDUCT("N");
							tbPayElectricity.setPAYMENT_REQ_NO(payPaymentRequestDTO.getPAYMENT_REQ_NO());
							tbPayElectricity.setPAYMENT_REQ_ITEM_NO(payPaymentRequestDtl.getITEM_NO());
							tbPayElectricity.setCR_TIME(today);
							tbPayElectricity.setCR_USER(getLoginUser().getUsername());
							tbPayElectricity.setMD_TIME(today);
							tbPayElectricity.setMD_USER(getLoginUser().getUsername());
							log.debug("tbPayElectricity.getELECTRICITY_TYPE() : "+tbPayElectricity.getELECTRICITY_TYPE());
							if(tbPayElectricity.getELECTRICITY_TYPE().equals("ELE01")||tbPayElectricity.getELECTRICITY_TYPE().equals("ELE05")){
								pay003Dao.insertPayElectricity(tbPayElectricity);
							}else{
							//Step 3.2.0 : 更新 TB_PAY_LOCATION_INFO
								pay003Dao.updatePayElectricity(tbPayElectricity.getSEQ_NBR(),tbPayElectricity.getPAYMENT_REQ_NO(),tbPayElectricity.getPAYMENT_REQ_ITEM_NO(),getLoginUser().getUsername(),today);
								if(tbPayElectricity.getELECTRICITY_TYPE().equals("ELE04")){
									pay003Dao.updatePayImpFile(tbPayElectricity.getIMP_FILE_SEQ_NBR(),tbPayElectricity.getCashReqNo(),getLoginUser().getUsername(),today);
								}
							}
						}
						//Step 3.1.1 : 新增付款資料 PayPayment
						for(TbPayPaymentDTO payPayment : payPaymentRequestDTO.getTbPayPaymentList()){
							List<TbLsLocPayment> tbLsLocPayment = pay003Dao.selectLsLocPayByExample(payPayment.getContractNo(), payPayment.getLOCATION_ID(),payPayment.getVoucher_type().equals("ELE05")?"ED":"E");
							payPayment.setPAYMENT_REQ_NO(payPaymentRequestDTO.getPAYMENT_REQ_NO());
							payPayment.setPAYMENT_REQ_ITEM_NO(payPaymentRequestDtl.getITEM_NO());
							payPayment.setPAYMENT_REQ_USER_ID(tbLsLocPayment.get(0).getLESSOR_ID());
							payPayment.setPAYMENT_USER_ID(tbLsLocPayment.get(0).getRECIPIENT_ID());
							payPayment.setPAYMENT_USER_NAME(tbLsLocPayment.get(0).getRECIPIENT_NAME());
							payPayment.setPAYMENT_METHOD(tbLsLocPayment.get(0).getPAYMENT_MODE());
							payPayment.setBANK_CODE(tbLsLocPayment.get(0).getUNIT_CODE());
							payPayment.setBANK_BRANCH_CODE(tbLsLocPayment.get(0).getSUB_UNIT_CODE());
							payPayment.setACCOUNT_NBR(tbLsLocPayment.get(0).getACCOUNT_NO());
							payPayment.setRESIDENT_ADDRESS(tbLsLocPayment.get(0).getRECIPIENT_ADDR());
							if(payPayment.getVoucher_type().equals("ELE02") || payPayment.getVoucher_type().equals("ELE03")||payPayment.getVoucher_type().equals("ELE04")||payPayment.getVoucher_type().equals("ELE06")) {
								TbPayElectricityExample example = new TbPayElectricityExample();
								example.createCriteria().andSEQ_NBREqualTo(payPayment.getElectricitySeqNbr());
								List<TbPayElectricity> payElectricity =  pay003Dao.selectPayElectricityExample(example);
								s_tax = payPublicUtilService.payFnGetTax("3",payElectricity.get(0).getPAYMENT_REQ_AMT(),"Y");
								payPayment.setTAX_EXCLUSIVE_TOTAL_AMT(BigDecimal.valueOf(payElectricity.get(0).getPAYMENT_REQ_AMT().intValue()-s_tax));
								payPayment.setTOTAL_BUSINESS_TAX(BigDecimal.valueOf((long)s_tax));
								payPayment.setTOTAL_INCOME_TAX(BigDecimal.ZERO);
								payPayment.setTOTAL_NHI_AMT(BigDecimal.ZERO);
							}
							payPayment.setCR_TIME(today);
							payPayment.setCR_USER(getLoginUser().getUsername());
							payPayment.setMD_TIME(today);
							payPayment.setMD_USER(getLoginUser().getUsername());
							pay003Dao.insertPayPayment(payPayment);
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
				pay003Dao.approved(cashReqNo, record);		
			} catch (Exception e) {
				log.error(ExceptionUtils.getFullStackTrace(e));
				throw new NomsException("99", e.getMessage());
			}
			return true;
		}
		public List<TbPayCashRequirement> checkDataCount(TbPayCashRequirementExample example){		
			return pay003Dao.checkDataCount(example);
		}
		
		public List<TbAuthPersonnel> selectTbAuthPersonnel(TbAuthPersonnelExample example){		
			return pay003Dao.selectTbAuthPersonnel(example);
		}
}
