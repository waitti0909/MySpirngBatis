package com.foya.noms.service.pay;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foya.dao.mybatis.model.TbLsLocPayment;
import com.foya.dao.mybatis.model.TbLsLocPaymentExample;
import com.foya.dao.mybatis.model.TbLsMain;
import com.foya.dao.mybatis.model.TbLsMainExample;
import com.foya.dao.mybatis.model.TbPayCashRequirement;
import com.foya.dao.mybatis.model.TbPayCheckDisregard;
import com.foya.dao.mybatis.model.TbPayCheckDisregardExample;
import com.foya.dao.mybatis.model.TbPayCheckDisregardKey;
import com.foya.dao.mybatis.model.TbPayLookupCode;
import com.foya.dao.mybatis.model.TbPayPayment;
import com.foya.dao.mybatis.model.TbPayPaymentRequest;
import com.foya.dao.mybatis.model.TbPayPaymentRequestDtl;
import com.foya.exception.NomsException;
import com.foya.noms.dao.pay.Pay010Dao;
import com.foya.noms.dto.pay.TbPayCheckDisregardDTO;
import com.foya.noms.dto.pay.TbPayPaymentDTO;
import com.foya.noms.resources.AppConstants;
import com.foya.noms.service.BaseService;
import com.foya.noms.service.workflow.WorkflowActionService;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
@Service
public class Pay010Service extends BaseService {
	@Inject 
	private Pay010Dao pay010Dao;
	@Inject 
	private PayPublicUtilService payPublicUtilService;
	@Autowired
	private WorkflowActionService workflowActionService;
	public List<TbPayPayment> selectPyamentByExample(){
		return pay010Dao.selectPyamentByExample();
	}
	
	public List<TbPayPayment> selectPyamentByExampleCheckNbr(String CheckNbr){
		return pay010Dao.selectPyamentByExampleCheckNbr(CheckNbr);
	}
	
	public String getPayCheckDisregardNo(){
		//String tempNo="",returnNo="",genInitSeq="000001",genInitFirstWord="CKD",dateString="";
		String dateString="";
		dateString=payPublicUtilService.getTodayStringWithoutTime();
		/*List<TbPayCheckDisregard> list = pay010Dao.getPayCheckDisregardNo(dateString);
		try{
			TbPayCheckDisregard check=(TbPayCheckDisregard)list.get(list.size()-1);
			tempNo=check.getDISREGARD_NO();
			tempNo=tempNo.substring(tempNo.length()-6,tempNo.length());
			int plusOneSeq = Integer.valueOf(tempNo);
			log.debug("first plusOneSeqString:"+plusOneSeq);
			tempNo = String.format("%06d",plusOneSeq + 1);
			log.debug("second plusOneSeqString:"+tempNo);
			returnNo = genInitFirstWord + dateString + tempNo;
		}catch(ArrayIndexOutOfBoundsException e){
			returnNo=genInitFirstWord+dateString+genInitSeq;
		}
		catch(NullPointerException e){
			returnNo=genInitFirstWord+dateString+genInitSeq;
		}
		return returnNo;
		*/
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("PI_PREFIX", "CKD");
		map.put("PI_APP_DATE", dateString);
		pay010Dao.payPcGetSeqnoByMap(map);
		if (!map.get("PO_ERR_CDE").equals("00")) {
			log.error("自動產生請款單號Call PAY_PC_GET_SEQNO Error：ERR_CDE="
					+ map.get("PO_ERR_CDE") + ", PO_ERR_MSG="
					+ map.get("PO_ERR_MSG"));
			throw new NomsException("99", "自動產生請款單號執行錯誤：" + map.get("PO_ERR_MSG"));
		}
		return (String)map.get("PO_SEQNO");
	}
	@Transactional
	public void insertPayCheckDisregard(List<TbPayCheckDisregard> exportList,Date today)throws NomsException{
		for (int i = 0; i < exportList.size(); i++) {
			TbPayCheckDisregard exportObject = exportList.get(i);
			try{
				exportObject.setCR_TIME(today);
				exportObject.setMD_TIME(today);
				pay010Dao.insertPayCheckDisregard(exportObject);
			}catch (NomsException throwable) {
				log.error(throwable.getMessage());
				throwable.printStackTrace();
			}
		}
	}
	
	public List<TbPayCheckDisregardDTO> selectTbPayCheckDisregard(String domain,String disregardNo,Date appStartDate,Date appEndDate,
			Date crStartDate,Date crEndDate,Set<String> status) throws ParseException{
		HashMap<String,Object> dataObj = new HashMap<String,Object>();
		dataObj.put("domain", domain);
		dataObj.put("disregardNo", disregardNo);
		dataObj.put("appStartDate", appStartDate);
		dataObj.put("appEndDate",appEndDate);
		dataObj.put("crStartDate", crStartDate);
		dataObj.put("crEndDate", crEndDate);
		dataObj.put("status", status);
		List<TbPayCheckDisregardDTO> list = pay010Dao.selectTbPayCheckDisregard(dataObj);
		for(int i=0;i<list.size();i++){
			TbPayCheckDisregardDTO data= list.get(i);
			TbPayCheckDisregardDTO getSum=pay010Dao.selectPayCheckDisregardBypk(data.getDISREGARD_NO());
			data.setCHECK_AMT(getSum.getCHECK_AMT());
			data.setDisregardCount(getSum.getDisregardCount());
			list.set(i, data);
		}
		return list;
	}
	
	public List<TbPayCheckDisregardDTO> getPayCheckDisregardNoEqual(
			String disregardNo) {
		PageList<TbPayCheckDisregardDTO> DTOlist = new PageList<TbPayCheckDisregardDTO>();
		List<TbPayCheckDisregard> list = pay010Dao.getPayCheckDisregardNoEqual(disregardNo);
		if(list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				TbPayCheckDisregard data = list.get(i);
				DTOlist.add(this.setPayCheckDisregardDTO(data));
			}
		}
		return DTOlist;
	}
	
	public List<TbPayPaymentDTO> selectPyamentByExampleReqNo(Long reqNo,Short reqItemNo,Long seqNo){
		List<TbPayPaymentDTO> DTOList = new PageList<TbPayPaymentDTO>();
		List<TbPayPayment> payment=pay010Dao.selectPyamentByExampleReqNo(reqNo, reqItemNo,seqNo);
		if(payment.size()>0){
			for(int i=0;i<payment.size();i++){
				TbPayPayment data = payment.get(i);
				DTOList.add(this.setPaymentDto(data, reqItemNo));
			}
		}
		return DTOList;
	}
	
	@Transactional
	public void updatePayCheckDisregard(List<TbPayCheckDisregard> editArray,Date today)throws NomsException{	
		for (int i = 0; i < editArray.size(); i++) {
			TbPayCheckDisregard exportObject = editArray.get(i);
			try{
				exportObject.setMD_TIME(today);
				exportObject.setMD_USER(getLoginUser().getUsername());
				pay010Dao.updatePayCheckDisregard(exportObject);
				if("B".equals(exportObject.getSTATUS())){
					//簽核
					workflowActionService.apply("PayVoidedCheck", exportObject.getDISREGARD_NO(), "支票作廢");
				}
			}catch (NomsException throwable) {
				log.error(throwable.getMessage());
				throwable.printStackTrace();
			}
		}
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
	public boolean applyByWF(String disregardNo, String action){		
		TbPayCheckDisregard record=null;
		TbPayCheckDisregardExample example=new TbPayCheckDisregardExample();
		try{
			record = new TbPayCheckDisregard();
			if(action.equals(AppConstants.WORKFLOW_REST_APPROVAL)){ //審核通過,狀態改核可             
				record.setSTATUS("C");
			}else if(action.equals(AppConstants.WORKFLOW_REST_REJECT)){
				record.setSTATUS("D");
			}
			record.setMD_USER(getLoginUser().getUsername());
			record.setMD_TIME(new Date());  
			example.createCriteria().andDISREGARD_NOEqualTo(disregardNo);
			pay010Dao.updatePayCheckDisregardByExample(record, example)	;	
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			throw new NomsException("99", e.getMessage());
		}
		return true;
	}
	@Transactional
	public void deletePayCheckDisregard(List<TbPayCheckDisregardKey> delArray)throws NomsException{
		for (int i = 0; i < delArray.size(); i++) {
			TbPayCheckDisregardKey key = delArray.get(i);
			try{
				pay010Dao.deletePayCheckDisregard(key);
			}catch (NomsException throwable) {
				log.error(throwable.getMessage());
				throwable.printStackTrace();
			}
		}
	}
	/*組合回傳payment資料*/
	public TbPayPaymentDTO setPaymentDto(TbPayPayment data,Short reqItemNo){
		List<TbPayLookupCode> lookup=null;
		TbPayPaymentDTO dto = new TbPayPaymentDTO();			
		TbPayPaymentRequest req=pay010Dao.selectPyamentRequsetBypk(data.getPAYMENT_REQ_NO());				
		TbPayPaymentRequestDtl reqDtl=pay010Dao.selectPyamentRequsetDtlBypk(data.getPAYMENT_REQ_NO(),reqItemNo);
		dto.setCashReqNo(req.getCASH_REQ_NO());
		dto.setContractNo(req.getCONTRACT_NO());
		try{
			TbLsMainExample example = new TbLsMainExample();
			example.createCriteria().andLS_NOEqualTo(req.getCONTRACT_NO()).andLS_STATUSEqualTo("1");
			List<TbLsMain> list = pay010Dao.selectLsMainByExample(example);
			dto.setLsName(list.get(0).getLS_NAME());
		}catch(NullPointerException e){}
		dto.setLOCATION_ID(reqDtl.getLOCATION_ID());
		try{
			TbPayCashRequirement cashReq=pay010Dao.selectCashRequirement(req.getCASH_REQ_NO());
			TbLsLocPaymentExample example=new TbLsLocPaymentExample();
			example.createCriteria().andLS_NOEqualTo(req.getCONTRACT_NO()).andEFF_DATELessThanOrEqualTo(cashReq.getAPP_DATE()).andEND_DATEGreaterThanOrEqualTo(cashReq.getAPP_DATE());
			List<TbLsLocPayment> list=pay010Dao.selectLsLocPaymentByExample(example);
			try{
				dto.setLessorId(list.get(0).getLESSOR_ID());
			}catch(NullPointerException e){}
			catch(RuntimeException e){}
			try{
				dto.setLessorName(list.get(0).getLESSOR_NAME());
			}catch(NullPointerException e){}
			catch(RuntimeException e){}
			try{
				dto.setPayAmt(list.get(0).getPAY_AMT());
			}catch(NullPointerException e){}
			catch(RuntimeException e){}
		}catch(Exception e){}
		dto.setPAYMENT_USER_ID(data.getPAYMENT_USER_ID());
		dto.setPAYMENT_USER_NAME(data.getPAYMENT_USER_NAME());
		lookup=payPublicUtilService.getPayLookupByTypeAndName("PAYMENT_METHOD", data.getPAYMENT_METHOD());
		try{
			TbPayLookupCode Lookup = (TbPayLookupCode)lookup.get(0);
			dto.setPaymethodName(Lookup.getLOOKUP_CODE_DESC());
		}
		catch(ArrayIndexOutOfBoundsException e){}
		catch(NullPointerException e){}
		catch(RuntimeException e){}
		
		dto.setSumExclusive(BigDecimal.valueOf(data.getTAX_EXCLUSIVE_TOTAL_AMT().longValue()+data.getTOTAL_BUSINESS_TAX().longValue()));
		dto.setTAX_EXCLUSIVE_TOTAL_AMT(data.getTAX_EXCLUSIVE_TOTAL_AMT());
		dto.setTOTAL_BUSINESS_TAX(data.getTOTAL_BUSINESS_TAX());
		dto.setTOTAL_INCOME_TAX(data.getTOTAL_INCOME_TAX());
		dto.setTOTAL_NHI_AMT(data.getTOTAL_NHI_AMT());
		dto.setSumAllAmt(BigDecimal.valueOf(data.getTAX_EXCLUSIVE_TOTAL_AMT().longValue()+
				data.getTOTAL_BUSINESS_TAX().longValue()-data.getTOTAL_INCOME_TAX().longValue()-data.getTOTAL_NHI_AMT().longValue()));
		return dto;
	}
	
	public TbPayCheckDisregardDTO setPayCheckDisregardDTO(TbPayCheckDisregard data){
		TbPayCheckDisregardDTO dto = new TbPayCheckDisregardDTO();
		TbPayPayment payment = null;
		List<TbPayLookupCode> lookup=null;
		dto.setCHECK_NBR(data.getCHECK_NBR());
		payment=pay010Dao.selectPyamentByExampleSeq(data.getPAYMENT_SEQ_NBR());
		try{
			dto.setPaymentUserId(payment.getPAYMENT_USER_ID());
			dto.setPaymentUserName(payment.getPAYMENT_USER_NAME());//待改
		}catch(NullPointerException e){
			dto.setPaymentUserId("");
			dto.setPaymentUserName("");//待改
		}catch(RuntimeException e){}
		dto.setCHECK_AMT(data.getCHECK_AMT());
		dto.setCheckCashDate(payment.getCHECK_CASH_DATE());
		lookup=payPublicUtilService.getPayLookupByTypeAndName("CHECK_DISREGARD_REASON", data.getCHECK_DISREGARD_REASON());
		try{
			TbPayLookupCode Lookup = (TbPayLookupCode)lookup.get(0);
			dto.setCHECK_DISREGARD_REASON(Lookup.getLOOKUP_CODE_DESC());
		}
		catch(ArrayIndexOutOfBoundsException e){}
		catch(NullPointerException e){}
		catch(RuntimeException e){}
		dto.setCHECK_DISREGARD_REASON_DESC(data.getCHECK_DISREGARD_REASON_DESC());
		dto.setPAYMENT_REQ_ITEM_NO(data.getPAYMENT_REQ_ITEM_NO());
		dto.setPAYMENT_REQ_NO(payment.getPAYMENT_REQ_NO());
		dto.setPAYMENT_SEQ_NBR(payment.getSEQ_NBR());
		return dto;
	}
}
