package com.foya.noms.service.pay;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foya.dao.mybatis.model.TbPayPaymentRequestDtl;
import com.foya.dao.mybatis.model.TbPayVoucherCredit;
import com.foya.exception.NomsException;
import com.foya.noms.dao.pay.PAY009Dao;
import com.foya.noms.dto.pay.TbPayPaymentRequestDtlDTO;
import com.foya.noms.dto.pay.TbPayPaymentRequestVoucherDTO;
import com.foya.noms.service.BaseService;

@Service
public class PAY009Service extends BaseService {

	@Autowired
	private PAY009Dao pay009Dao;
	/**
	 * 查詢請款資料 - 金額加總
	 * 
	 * @param domain 維運區 
	 * @param voucherNbr 	憑證號碼
	 * @param paymentUserId 付款對象
	 * @param yearMonth 	請款年月
	 * @param locationId 	站點編號
	 * 
	 * @return List<TbPayPaymentRequestVoucherDTO>
	 */
	public List<TbPayPaymentRequestVoucherDTO> selectPaymentAmount(String domain, String voucherNbr,
			String lessorId, String yearMonth, String locationId){
		HashMap<String,Object> dataObj = new HashMap<String,Object>();
		dataObj.put("domain", domain);
		dataObj.put("voucherNbr", StringUtils.isNotBlank(voucherNbr)?"%"+voucherNbr+"%":null);
		dataObj.put("lessorId", lessorId);
		dataObj.put("yearMonth", yearMonth);
		dataObj.put("locationId", StringUtils.isNotBlank(locationId)?"%"+locationId+"%":null);
		return pay009Dao.selectPaymentAmount(dataObj);
	}
	/**
	 * 查詢請款資料 
	 * 
	 * @param voucherNo 	憑證處理單號
	 * 
	 * @return List<TbPayPaymentRequestVoucherDTO>
	 */
	public List<TbPayPaymentRequestVoucherDTO> selectPayPaymentRequestVoucherByVoucherNo(String voucherNo){
		return pay009Dao.selectPayPaymentRequestVoucherByVoucherNo(voucherNo);
	}

	/**
	 * 新增頁面 - 查詢 - Detail2 所需 站點編號 (PayPaymentRequestDtl.location_id)
	 * 
	 * @return List<TbPayPaymentRequestDtl>
	 */
	public List<TbPayPaymentRequestDtl> selectPayPaymentRequestDtlLocationId(){		
		return pay009Dao.selectPayPaymentRequestDtlLocationId();
	}	
	/**
	 * 新增頁面 - 查詢 - Detail2 所需相關金額 (PayPaymentRequestDtl.Tax Amount)
	 * 
	 * @param prType 請款類別代碼
	 * @param locationId 基站代碼
	 * 
	 * @return TbPayPaymentRequestDtlDTO
	 */
	public TbPayPaymentRequestDtlDTO selectPayPaymentRequestDtlTaxSumAmt(String prType, String locationId){
		TbPayPaymentRequestDtlDTO vo = new TbPayPaymentRequestDtlDTO();
		HashMap<String,Object> dataObj = new HashMap<String,Object>();
		dataObj.put("prType", "%"+prType+"%");
		dataObj.put("locationId", locationId);
		try{
			List<TbPayPaymentRequestDtlDTO> ls = pay009Dao.selectPayPaymentRequestDtlTaxSumAmt(dataObj);			
			if(ls.size()>0){
				vo = ls.get(0);
			}
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			throw new NomsException("99", e.getMessage());
		}
		return vo;
	}	
	/**
	 * 查詢 請款單 - Detail2 所需資料
	 * 
	 * @param mstSeqNbr 請款憑證table的資料列序號
	 * 
	 * @return List<TbPayPaymentRequestDtlDTO>
	 */
	public List<TbPayPaymentRequestDtlDTO> select4Pay009Detail2(Long mstSeqNbr){
		HashMap<String,Object> dataObj = new HashMap<String,Object>();
		dataObj.put("mstSeqNbr", mstSeqNbr);
		return pay009Dao.select4Pay009Detail2(dataObj);
	}
	/**
	 * 查詢 請款單 - Detail3 所需資料
	 * 
	 * @param paymentReqNo 請款編號
	 * @param mstSeqNbr 預付主檔 -預付編號
	 * @param locationId 站點編號
	 * @param prType 	請款類別
	 * 
	 * @return List<TbPayPaymentRequestDtlDTO>
	 */
	public List<TbPayPaymentRequestDtlDTO> select4Pay009Detail3(
			Long mstSeqNbr, String locationId, String prType){
		HashMap<String,Object> dataObj = new HashMap<String,Object>();
		dataObj.put("mstSeqNbr", mstSeqNbr);	
		dataObj.put("eqPrType", prType);//與mstSeqNbr一起加入條件		
		dataObj.put("locationId", locationId);
		dataObj.put("prType", "%"+prType+"%");	
		return pay009Dao.select4Pay009Detail3(dataObj);
	}
	/**
	 * 更新
	 * 
	 * @param voucherRecord  多筆  請款憑證  資料(內含多筆 憑證沖抵記錄[TB_PAY_VOUCHER_CREDIT]及刪除者)
	 * @param deleteSeqNbr	多筆刪除資料的SeqNbr
	 * 
	 */	
	@Transactional
	public boolean edit(List<TbPayPaymentRequestVoucherDTO> voucherList){
		Date today = new Date();
		List<Long> seqNbrList = new ArrayList<Long>();
		boolean ok = false;
		try{
			//先做刪除 - Detail 資料
			for(TbPayPaymentRequestVoucherDTO vo : voucherList){
				//刪除者				
				if(vo.getSEQ_NBR().equals(-99L) 
						&& StringUtils.isNotBlank(vo.getEditType()) && vo.getEditType().equals("DEL")){
					//刪除資料 #2 Detail有做刪修者
					for(TbPayVoucherCredit creditRecord : vo.getTbPayVoucherCreditList()){
						pay009Dao.deleteVoucherCreditByPrTypeLocationId(
								creditRecord.getMST_SEQ_NBR(), creditRecord.getPR_TYPE(), creditRecord.getLOCATION_ID());
					}
				}else if(StringUtils.isNotBlank(vo.getEditType()) && vo.getEditType().equals("DEL")){
					//刪除多筆資料 #1 Master
					seqNbrList.add(vo.getSEQ_NBR());	
				}				
			}
			//刪除 - Master 資料
			if(seqNbrList.size() > 0){
				pay009Dao.deletePaymentRequestVoucherBySeqNbr(seqNbrList);
				pay009Dao.deleteVoucherCreditByMstSeqNbr(seqNbrList);
			}
			//再做 - 新增Detail 資料
			for(TbPayPaymentRequestVoucherDTO vo : voucherList){	
				if(StringUtils.isNotBlank(vo.getEditType()) && vo.getEditType().equals("DEL")){
					continue;
				}
				
				//Table 2
				for(TbPayVoucherCredit creditRecord : vo.getTbPayVoucherCreditList()){
					creditRecord.setMST_SEQ_NBR(vo.getSEQ_NBR());
					creditRecord.setCR_USER(getLoginUser().getUsername());
					creditRecord.setMD_USER(getLoginUser().getUsername());
					creditRecord.setCR_TIME(today);
					creditRecord.setMD_TIME(today);
					//確認是否新增,或修改
					if(pay009Dao.countVoucherCredit(
							creditRecord.getMST_SEQ_NBR(), creditRecord.getPAYMENT_REQ_NO(), creditRecord.getPAYMENT_REQ_ITEM_NO()) >0){
						pay009Dao.updateVoucherCreditPlusTaxByRecord(creditRecord);
					}else{
						pay009Dao.insertVoucherCreditTaxFromFn(creditRecord);	
					}
				}
				//Table 1 更新金額
				vo.setMD_USER(getLoginUser().getUsername());
				vo.setMD_TIME(today);
				pay009Dao.updateCreditMadeAmtTaxFromVoucherCredit(vo);
			}
			ok = true;
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			throw new NomsException("99", e.getMessage());
		}
		return ok;
	}
	/**
	 * 新增
	 * 
	 * @param voucherRecord  多筆  請款憑證  資料(內含多筆 憑證沖抵記錄[TB_PAY_VOUCHER_CREDIT])
	 * 
	 * @return vucherNbr 憑證處理單號
	 */	
	@Transactional
	public String insert(List<TbPayPaymentRequestVoucherDTO> voucherList){		
		String vucherNbr = null;
		Map<String, Object> map = null;
		Date today = new Date();
		String yyyyMMdd = new SimpleDateFormat("yyyyMMdd").format(today);
		try{
			//取憑證處理單號
			map = new HashMap<String, Object>();
			map.put("PI_PREFIX", "VOU");
			map.put("PI_APP_DATE", yyyyMMdd);
			pay009Dao.callPayPcGetSeqnoByMap(map);
			if (!map.get("PO_ERR_CDE").equals("00")) {
				log.error("自動產生憑證處理單號Call PAY_PC_GET_SEQNO Error：ERR_CDE="
						+ map.get("PO_ERR_CDE") + ", PO_ERR_MSG="
						+ map.get("PO_ERR_MSG"));
				throw new NomsException("99", "自動產生憑證處理單號執行錯誤：" + map.get("PO_ERR_MSG"));
			}
			vucherNbr = (String) map.get("PO_SEQNO");
			for(TbPayPaymentRequestVoucherDTO vo : voucherList){				
				//Table 1 				
				vo.setPROCESS_TYPE("VOU");
				vo.setVOUCHER_NO(vucherNbr);
				vo.setCREDIT_MADE_AMT(BigDecimal.ZERO);//金額等扣抵記錄新增完畢,再行更新
				vo.setCREDIT_MADE_TAX(BigDecimal.ZERO);
				vo.setCR_USER(getLoginUser().getUsername());
				vo.setMD_USER(getLoginUser().getUsername());
				vo.setCR_TIME(today);
				vo.setMD_TIME(today);
				pay009Dao.insertPaymentRequestVoucher(vo);
				if (vo.getSEQ_NBR() == null) {
					throw new NomsException("99", "無法取得新增後的憑證主檔SEQ_NO");
				}				
				
				//Table 2
				for(TbPayVoucherCredit creditRecord : vo.getTbPayVoucherCreditList()){
					creditRecord.setMST_SEQ_NBR(vo.getSEQ_NBR());
					creditRecord.setCR_USER(getLoginUser().getUsername());
					creditRecord.setMD_USER(getLoginUser().getUsername());
					creditRecord.setCR_TIME(today);
					creditRecord.setMD_TIME(today);
					pay009Dao.insertVoucherCreditTaxFromFn(creditRecord);
				}
				//再更新金額
				pay009Dao.updateCreditMadeAmtTaxFromVoucherCredit(vo);
			}
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			throw new NomsException("99", e.getMessage());
		}
		return vucherNbr;
	}	
}
