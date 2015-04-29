package com.foya.noms.service.pay;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.hornetq.utils.json.JSONArray;
import org.hornetq.utils.json.JSONException;
import org.hornetq.utils.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foya.dao.mybatis.model.TbAuthPersonnel;
import com.foya.dao.mybatis.model.TbLsCollectUnit;
import com.foya.dao.mybatis.model.TbLsCollectUnitExample;
import com.foya.dao.mybatis.model.TbLsCollectUnitSub;
import com.foya.dao.mybatis.model.TbLsCollectUnitSubExample;
import com.foya.dao.mybatis.model.TbPayCashRequirement;
import com.foya.dao.mybatis.model.TbPayCashRequirementExample;
import com.foya.dao.mybatis.model.TbPayLookupCode;
import com.foya.dao.mybatis.model.TbPayPayment;
import com.foya.dao.mybatis.model.TbPayPaymentExample;
import com.foya.dao.mybatis.model.TbPayPaymentItem;
import com.foya.dao.mybatis.model.TbPayPaymentItemExample;
import com.foya.dao.mybatis.model.TbPayPaymentRequest;
import com.foya.dao.mybatis.model.TbPayPaymentRequestDtl;
import com.foya.dao.mybatis.model.TbPayPaymentRequestDtlExample;
import com.foya.dao.mybatis.model.TbPayPaymentRequestExample;
import com.foya.dao.mybatis.model.TbPayPaymentRequestVoucher;
import com.foya.dao.mybatis.model.TbPayPaymentRequestVoucherExample;
import com.foya.dao.mybatis.model.TbPayRepartition;
import com.foya.dao.mybatis.model.TbPayRepartitionExample;
import com.foya.dao.mybatis.model.TbPayVoucherCredit;
import com.foya.dao.mybatis.model.TbPayVoucherCreditExample;
import com.foya.dao.mybatis.model.TbSiteMain;
import com.foya.exception.NomsException;
import com.foya.noms.dao.auth.PersonnelDao;
import com.foya.noms.dao.pay.Pay005Dao;
import com.foya.noms.dao.pay.PayPublicUtilDao;
import com.foya.noms.dto.pay.TbPayCashRequirementDTO;
import com.foya.noms.dto.pay.TbPayPaymentDTO;
import com.foya.noms.dto.pay.TbPayPaymentRequestVoucherDTO;
import com.foya.noms.dto.pay.TbPayRepartitionDTO;
import com.foya.noms.service.BaseService;
import com.foya.noms.service.workflow.WorkflowActionService;
import com.foya.noms.util.DateUtils;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
@Service
public class Pay005Service extends BaseService {
	@Inject 
	private Pay005Dao pay005Dao;
	@Inject
	private PayPublicUtilService payPublicUtilService;
	@Inject
	private PersonnelDao personnelDao;
	@Autowired
	private WorkflowActionService workflowActionService;
	@Inject
	private PayPublicUtilDao payPublicUtilDao;

	public List<TbPayCashRequirement> selectPayCashRequirementByExample(String cashReqNo,String domain,Date appSDate,Date appEDate,List<String> status) throws Exception{
		
		TbPayCashRequirementExample example = new TbPayCashRequirementExample();
		com.foya.dao.mybatis.model.TbPayCashRequirementExample.Criteria criteria = example.createCriteria();
		try{
			if("" != cashReqNo && !cashReqNo.equals(null) && !"null".equals(cashReqNo)){
				criteria.andCASH_REQ_NOLike("%"+cashReqNo+"%");
			}else{
				criteria.andCASH_REQ_NOLike("M%");
			}
		}catch(NullPointerException e){
			criteria.andCASH_REQ_NOLike("M%");
		}
				catch(RuntimeException e){}
		try{	
			if("" != domain && !domain.equals(null) && !"null".equals(domain)){
				criteria.andDOMAINEqualTo(domain);
				}
		}catch(NullPointerException e){}
			catch(RuntimeException e){}
		if(!appSDate.equals(null) && !appEDate.equals(null)){
			criteria.andAPP_DATEGreaterThanOrEqualTo(appSDate);
			criteria.andAPP_DATELessThanOrEqualTo(appEDate);
		}
		if(status.size()>0){
			criteria.andSTATUSIn(status);
		}	
			criteria.andPROCESS_TYPEEqualTo("MIS");
			
		return pay005Dao.selectPayCashRequirementByExample(example);
	}
	
	public List<TbPayCashRequirementDTO> selectCashAllData(String cashReqNo,String domain,Date appSDate,Date appEDate,List<String> status)  throws Exception{
		List<TbPayCashRequirement> cashList=this.selectPayCashRequirementByExample(cashReqNo, domain, appSDate, appEDate, status);
		PageList<TbPayCashRequirementDTO> DTOlist = new PageList<TbPayCashRequirementDTO>();
		TbPayCashRequirementDTO dto=null;
		try{
			for(int i=0;i<cashList.size();i++){
				TbPayCashRequirement cash=(TbPayCashRequirement)cashList.get(i);
				dto=this.makeCashDTO(cash);
				DTOlist.add(dto);
			}
		}catch(ArrayIndexOutOfBoundsException e){
			log.error("[/pay/PAY005/selectCashAllData] ERROR :"
					+ e.getMessage());
			e.printStackTrace();
			return DTOlist;
		}catch(NullPointerException e){
			log.error("[/pay/PAY005/selectCashAllData] ERROR :"
					+ e.getMessage());
			e.printStackTrace();
			return DTOlist;
		}
		return DTOlist;
	}
	
	public TbPayCashRequirementDTO makeCashDTO(TbPayCashRequirement cash){
		TbPayCashRequirementDTO dto=new TbPayCashRequirementDTO();
		String cashReqNoTemp=cash.getCASH_REQ_NO();
		dto.setReqCnt(countPaymentRequestByExample(cashReqNoTemp));
		List<TbPayPaymentRequest> reqList=selectPaymentRequestByExample(cashReqNoTemp);
		Map<String,BigDecimal> map=selectPaymentAmt(reqList);
		dto.setTotalTaxEx(map.get("taxEx"));
		dto.setTotalBusinessEx(map.get("businessEx"));
		dto.setTotalIncomeEx(map.get("incomeEx"));
		dto.setTotalNhiEx(map.get("nhiEx"));
		dto.setTotalAmt(map.get("totalAmt"));
		dto.setVoucherCnt(selectVoucherCnt(reqList));
		dto.setCASH_REQ_NO(cash.getCASH_REQ_NO());
		dto.setAPP_USER(cash.getAPP_USER());
		dto.setAPP_DATE(cash.getAPP_DATE());
		dto.setACCOUNT_APPROVAL_USER(cash.getACCOUNT_APPROVAL_USER());
		dto.setACCOUNT_APPROVAL_DATE(cash.getACCOUNT_APPROVAL_DATE());
		dto.setREJECT_REASON(cash.getREJECT_REASON());
		dto.setDOMAIN(cash.getDOMAIN());
		dto.setYEAR_MONTH(cash.getYEAR_MONTH());
		dto.setSTATUS(cash.getSTATUS());
		List<TbPayLookupCode> lookup=payPublicUtilService.getPayLookupByTypeAndName("CASH_REQ_STATUS", cash.getSTATUS());
		try{
			TbPayLookupCode Lookup = (TbPayLookupCode)lookup.get(0);
			dto.setStatusDscr(Lookup.getLOOKUP_CODE_DESC());
		}
		catch(ArrayIndexOutOfBoundsException e){}
		catch(NullPointerException e){}
		catch(RuntimeException e){}
		try{
			TbAuthPersonnel appPerson=personnelDao.searchByPsnNo(cash.getAPP_USER());
			dto.setAppUserCnm(appPerson.getCHN_NM());
		}catch(RuntimeException e){}
		try{
			TbAuthPersonnel person=personnelDao.searchByPsnNo(cash.getACCOUNT_APPROVAL_USER());
			dto.setAccountUserCnm(person.getCHN_NM());
		}catch(RuntimeException e){}	
		return dto;
	}
	public int countPaymentRequestByExample(String cashReqNo){
		TbPayPaymentRequestExample example = new TbPayPaymentRequestExample();
		example.createCriteria().andCASH_REQ_NOEqualTo(cashReqNo);
		return pay005Dao.countPaymentRequestByExample(example);
	}
	@Transactional
	public void updateToTable(String cashReqNo,Date today,String status){
		TbPayCashRequirement record = new TbPayCashRequirement();
		record.setCASH_REQ_NO(cashReqNo);
		record.setSTATUS(status);
		record.setMD_TIME(today);
		record.setMD_USER(getLoginUser().getUsername());
		pay005Dao.updatePayCashRequirementByPK(record);
		
		//簽核
		workflowActionService.apply("PayOthers", cashReqNo, "雜項請款");
	}
	
	public List<TbPayPaymentRequest> selectPaymentRequestByExample(String cashReqNo){
		TbPayPaymentRequestExample example= new TbPayPaymentRequestExample();
		example.createCriteria().andCASH_REQ_NOEqualTo(cashReqNo);
		return pay005Dao.selectPaymentRequestByExample(example);
	}

	public double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();

	}
	
	public double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}
	
	/*取得請款金額*/
	public Map<String,BigDecimal> selectPaymentAmt(List<TbPayPaymentRequest> reqList){
		Map<String,BigDecimal> returnMap=new HashMap<String,BigDecimal>();
		BigDecimal taxEx=new BigDecimal(0);
		BigDecimal businessEx=new BigDecimal(0);
		BigDecimal incomeEx=new BigDecimal(0);
		BigDecimal nhiEx=new BigDecimal(0);
		BigDecimal totalAmt=new BigDecimal(0);
		for(int i=0;i<reqList.size();i++){
			TbPayPaymentRequest req=(TbPayPaymentRequest)reqList.get(i);
			TbPayPaymentRequestDtlExample example = new TbPayPaymentRequestDtlExample();
			example.createCriteria().andPAYMENT_REQ_NOEqualTo(req.getPAYMENT_REQ_NO());
			List<TbPayPaymentRequestDtl> list=pay005Dao.selectPaymentRequestDtlByExample(example);
			for(int dtlI=0;dtlI<list.size();dtlI++){
				TbPayPaymentRequestDtl dtl= (TbPayPaymentRequestDtl)list.get(dtlI);
				TbPayPaymentExample paymentExample = new TbPayPaymentExample();
				paymentExample.createCriteria().andPAYMENT_REQ_NOEqualTo(dtl.getPAYMENT_REQ_NO());
				List<TbPayPayment> payList=pay005Dao.selectPaymentByExample(paymentExample);
				for(int payI=0;payI<payList.size();payI++){
					TbPayPayment pay=(TbPayPayment)payList.get(payI);
					taxEx=new BigDecimal(this.add(taxEx.doubleValue(), pay.getTAX_EXCLUSIVE_TOTAL_AMT().doubleValue()));
					businessEx=new BigDecimal(this.add(businessEx.doubleValue(), pay.getTOTAL_BUSINESS_TAX().doubleValue()));
					incomeEx=new BigDecimal(this.add(incomeEx.doubleValue(), pay.getTOTAL_INCOME_TAX().doubleValue()));
					nhiEx=new BigDecimal(this.add(nhiEx.doubleValue(), pay.getTOTAL_NHI_AMT().doubleValue()));
				}
			}
		}
		totalAmt=new BigDecimal(this.add(totalAmt.doubleValue(), taxEx.doubleValue()));
		totalAmt=new BigDecimal(this.add(totalAmt.doubleValue(), businessEx.doubleValue()));
		totalAmt=new BigDecimal(this.sub(totalAmt.doubleValue(), incomeEx.doubleValue()));
		totalAmt=new BigDecimal(this.sub(totalAmt.doubleValue(), nhiEx.doubleValue()));
		returnMap.put("taxEx", taxEx);
		returnMap.put("businessEx", businessEx);
		returnMap.put("incomeEx", incomeEx);
		returnMap.put("nhiEx", nhiEx);
		returnMap.put("totalAmt", totalAmt);
		return returnMap;
	}
	
	/*取得憑證筆數*/
	public int selectVoucherCnt(List<TbPayPaymentRequest> reqList){
		int returnNumber=0;
		for(int i=0;i<reqList.size();i++){
			TbPayPaymentRequest req=(TbPayPaymentRequest)reqList.get(i);
			TbPayPaymentRequestDtlExample example = new TbPayPaymentRequestDtlExample();
			example.createCriteria().andPAYMENT_REQ_NOEqualTo(req.getPAYMENT_REQ_NO());
			List<TbPayPaymentRequestDtl> list=pay005Dao.selectPaymentRequestDtlByExample(example);
			for(int dtlI=0;dtlI<list.size();dtlI++){
				TbPayPaymentRequestDtl dtl= (TbPayPaymentRequestDtl)list.get(dtlI);
				TbPayVoucherCreditExample voucherExample = new TbPayVoucherCreditExample();
				voucherExample.createCriteria().andPAYMENT_REQ_ITEM_NOEqualTo(dtl.getITEM_NO()).andPAYMENT_REQ_NOEqualTo(dtl.getPAYMENT_REQ_NO());
				List<TbPayVoucherCredit> voucherList=pay005Dao.selectPayVoucherCreditByExample(voucherExample);
				for(int vInt=0;vInt<voucherList.size();vInt++){
					TbPayVoucherCredit voucher=(TbPayVoucherCredit)voucherList.get(vInt);
					TbPayPaymentRequestVoucherExample reqExample = new TbPayPaymentRequestVoucherExample();
					reqExample.createCriteria().andSEQ_NBREqualTo(voucher.getMST_SEQ_NBR());
					returnNumber=pay005Dao.countPayPaymentRequestVoucherByExample(reqExample);
				}
			}
		}
		return returnNumber;
	}
	public String getPayCashReqNo(String initWord){
		//String tempNo="",returnNo="",genInitSeq="0001",genInitFirstWord=initWord,dateString="";
		String dateString="";
		dateString=payPublicUtilService.getTodayStringWithoutTime();
		
		/*
		List<TbPayCashRequirement> list = pay005Dao.getPayCashReqNo(dateString,initWord);
		try{
			TbPayCashRequirement check=(TbPayCashRequirement)list.get(list.size()-1);
			tempNo=check.getCASH_REQ_NO();
			tempNo=tempNo.substring(tempNo.length()-4,tempNo.length());
			int plusOneSeq = Integer.valueOf(tempNo);
			log.debug("first plusOneSeqString:"+plusOneSeq);
			tempNo = String.format("%04d",plusOneSeq + 1);
			log.debug("second plusOneSeqString:"+tempNo);
			returnNo = genInitFirstWord + dateString + tempNo;
		}catch(ArrayIndexOutOfBoundsException e){
			returnNo=genInitFirstWord+dateString+genInitSeq;
		}
		catch(NullPointerException e){
			returnNo=genInitFirstWord+dateString+genInitSeq;
		}*/
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("PI_PREFIX", initWord);
		map.put("PI_APP_DATE", dateString);
		pay005Dao.payPcGetSeqnoByMap(map);
		if (!map.get("PO_ERR_CDE").equals("00")) {
			log.error("自動產生請款單號Call PAY_PC_GET_SEQNO Error：ERR_CDE="
					+ map.get("PO_ERR_CDE") + ", PO_ERR_MSG="
					+ map.get("PO_ERR_MSG"));
			throw new NomsException("99", "自動產生請款單號執行錯誤：" + map.get("PO_ERR_MSG"));
		}
		return (String)map.get("PO_SEQNO");
	}
	
	public String getRepartionNo(String initWord){
		String tempNo="",returnNo="",genInitSeq="0001",genInitFirstWord=initWord,dateString="";
		dateString=payPublicUtilService.getTodayStringWithoutTime();
		List<TbPayRepartition> list = pay005Dao.getRepartionNo(dateString,initWord);
		try{
			TbPayRepartition check=(TbPayRepartition)list.get(list.size()-1);
			tempNo=check.getREPARTITION_NO();
			tempNo=tempNo.substring(tempNo.length()-4,tempNo.length());
			int plusOneSeq = Integer.valueOf(tempNo);
			log.debug("first plusOneSeqString:"+plusOneSeq);
			tempNo = String.format("%04d",plusOneSeq + 1);
			log.debug("second plusOneSeqString:"+tempNo);
			returnNo = genInitFirstWord + dateString + tempNo;
		}catch(ArrayIndexOutOfBoundsException e){
			returnNo=genInitFirstWord+dateString+genInitSeq;
		}
		catch(NullPointerException e){
			returnNo=genInitFirstWord+dateString+genInitSeq;
		}
		return returnNo;
	}
	
	public String getPayPaymentRequestVoucherNo(String initWord){
		String tempNo="",returnNo="",genInitSeq="0001",genInitFirstWord=initWord,dateString="";
		dateString=payPublicUtilService.getTodayStringWithoutTime();
		List<TbPayPaymentRequestVoucher> list = pay005Dao.getPayPaymentRequestVoucherNo(dateString,initWord);
		try{
			TbPayPaymentRequestVoucher check=(TbPayPaymentRequestVoucher)list.get(list.size()-1);
			tempNo=check.getVOUCHER_NO();
			tempNo=tempNo.substring(tempNo.length()-4,tempNo.length());
			int plusOneSeq = Integer.valueOf(tempNo);
			log.debug("first plusOneSeqString:"+plusOneSeq);
			tempNo = String.format("%04d",plusOneSeq + 1);
			log.debug("second plusOneSeqString:"+tempNo);
			returnNo = genInitFirstWord + dateString + tempNo;
		}catch(ArrayIndexOutOfBoundsException e){
			returnNo=genInitFirstWord+dateString+genInitSeq;
		}
		catch(NullPointerException e){
			returnNo=genInitFirstWord+dateString+genInitSeq;
		}
		return returnNo;
	}
	@Transactional
	public void spSaveToTable(String spPaymentReqDtlArray,String spPaymentArray,
			String spPaymentReqVouCreditArray,String spRepartitionArray,Long paymentReqNo,Date today) throws NomsException ,Exception{
		//先刪除舊有資料
		pay005Dao.deleteSpData(paymentReqNo);
		
		JSONArray jsonReqDtlArray = new JSONArray(spPaymentReqDtlArray);//
		JSONArray jsonPaymentArray = new JSONArray(spPaymentArray);//
		JSONArray jsonReqVouArray = new JSONArray(spPaymentReqVouCreditArray);//
		JSONArray jsonRepartitionArray = new JSONArray(spRepartitionArray);//
		String repartionNo=this.getRepartionNo("MAN");
		try{
			//判斷更新或寫入 透過location id
			int locationCnt=1;
			Long paymentSeqNo;
			for (int i = 0; i < jsonReqDtlArray.length(); i++) {
				JSONObject obj = jsonReqDtlArray.getJSONObject(i);
				JSONObject objPayment = jsonPaymentArray.getJSONObject(i);
				JSONObject objReqVou = jsonReqVouArray.getJSONObject(i);
				JSONObject objRepartition = jsonRepartitionArray.getJSONObject(i);
				//寫入payment request Dtl
				TbPayPaymentRequestDtl dtl=this.makeTbPayPaymentRequestDtl(obj, today, locationCnt);
				TbPayPaymentRequestDtlExample example = new TbPayPaymentRequestDtlExample();
				example.createCriteria().andLOCATION_IDEqualTo(dtl.getLOCATION_ID()).andPAYMENT_REQ_NOEqualTo(paymentReqNo);
				int dtlCnt=pay005Dao.countPaymentRequestDtlByExample(example);
				if(dtlCnt>0){//如果已經存在 就更新 不存在就寫入
					HashMap<String , Object> dataObj = this.makeTbPayPaymentRequestDtlMap(dtl);
					try{
						pay005Dao.updateTbPayPaymentRequestDtl(dataObj);
					}catch(Exception e){throw new NomsException("更新 TB_PAY_PAYMENT_REQUEST_DTL 錯誤："+e.getLocalizedMessage());}	
				}else{
					try{
						pay005Dao.insertTbPayPaymentRequestDtl(dtl);
					}catch(Exception e){throw new NomsException("寫入 TB_PAY_PAYMENT_REQUEST_DTL 錯誤："+e.getLocalizedMessage());}
				}
				//寫入payment
				TbPayPayment payment = this.makePayPayment(dtl, objPayment, paymentReqNo, today,"SP");
				TbPayPaymentExample paymentExample = new TbPayPaymentExample();
				paymentExample.createCriteria().andLOCATION_IDEqualTo(dtl.getLOCATION_ID()).andPAYMENT_REQ_NOEqualTo(paymentReqNo);
				int paymentCnt=pay005Dao.countPaymentByExample(paymentExample);
				//寫入 TbPayVoucherCredit
				TbPayVoucherCredit vouCredit=this.makeTbPaVoucherCredit(objReqVou, paymentReqNo, locationCnt, today);
				if(paymentCnt>0){//如果已經存在 就更新 不存在就寫入
					HashMap<String , Object> dataObj = this.makeTbPayPaymentMap(payment, dtl);
					try{
						pay005Dao.updateByExampleSelectiveWithPlus(dataObj);
						List<TbPayPayment> paymentList=pay005Dao.selectPaymentByExample(paymentExample);
						paymentSeqNo=paymentList.get(0).getSEQ_NBR();
					}catch(Exception e){throw new NomsException("更新 TB_PAY_PAYMENT 錯誤："+e.getLocalizedMessage());}
					//更新 TbPayVoucherCredit
					HashMap<String , Object> creditDataObj = this.makeTbPayVoucherCreditMap(payment, locationCnt, paymentReqNo, vouCredit.getMST_SEQ_NBR());
					try{
						pay005Dao.updateTbPayVoucherCreditSelectiveWithPlus(creditDataObj);
					}catch(Exception e){throw new NomsException("更新 TB_PAY_VOUCHER_CREDIT 錯誤："+e.getLocalizedMessage());}
				}else{
					try{
						pay005Dao.insertTbPayPayment(payment);
						if (payment.getSEQ_NBR() == null) {
							throw new NomsException("99", "無法取得新增TB_PAY_PAYMENT後的流水號");
						}else{
							paymentSeqNo=payment.getSEQ_NBR();
						}
					}catch(Exception e){throw new NomsException("寫入 TB_PAY_PAYMENT 錯誤："+e.getLocalizedMessage());}
					//寫入TbPayVoucherCredit
					try{
						pay005Dao.insertTbPayVoucherCredit(vouCredit);
					}catch(Exception e){throw new NomsException("寫入 TB_PAY_VOUCHER_CREDIT 錯誤："+e.getLocalizedMessage());}
				}
				//更新TB_PAY_PAYMENT_REQUEST_VOUCHER 
				this.updatePaymentVoucherBySeqNo(new Long(objReqVou.getString("mst_SEQ_NBR")));
				//寫入 pay repartition
				TbPayRepartition payRepartition = this.makeTbPayRepartition(today, repartionNo, paymentReqNo, dtl.getITEM_NO(), paymentSeqNo, objRepartition);
				try{
					pay005Dao.insertTbPayRepartition(payRepartition);
				}catch(Exception e){throw new NomsException("寫入 TB_PAY_REPARTITION 錯誤："+e.getLocalizedMessage());}
				locationCnt+=1;
			}
		}catch(Exception e){throw new NomsException(e.getLocalizedMessage());}
		
	}
	
	@Transactional
	public void insertToTable(String paymentVou,String paymentReq,
			String cashReq,String paymentDTO,Date today,String cashReqNo,String yearMonth) throws NomsException ,Exception{
			JSONArray jsonArray = new JSONArray(paymentReq);//取得pyament request明細清單
			JSONArray jsonVouArray = new JSONArray(paymentVou);//取得paymentVou明細清單
			JSONArray jsonCashArray = new JSONArray(cashReq);//取得cashReq明細清單
			JSONArray jsonPaymentArray = new JSONArray(paymentDTO);//取得基站分攤明細清單
			String repartionNo=this.getRepartionNo("MAN");
			try{
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject obj = jsonArray.getJSONObject(i);
					//寫入pay payment
					TbPayPaymentRequest paymentReqData = this.makeTbPayPaymentRequest(obj, today);
					try{
						pay005Dao.insertTbPayPaymentRequest(paymentReqData);
					}catch(Exception e){throw new NomsException("寫入 TB_PAY_PAYMENT_REQUEST 錯誤："+e.getLocalizedMessage());}
					if (paymentReqData.getPAYMENT_REQ_NO() == null) {
						throw new NomsException("99", "無法取得新增TB_PAY_PAYMENT_REQUEST後的請款明細流水號");
					}
					//寫入 pay request voucher
					JSONObject vouObj = jsonVouArray.getJSONObject(i);
					TbPayPaymentRequestVoucherDTO paymentVouData = this.makeTbPayPaymentRequestVoucher(vouObj, today);
					try{
						pay005Dao.insertTbPayPaymentRequestVoucher(paymentVouData);
					}catch(Exception e){throw new NomsException("寫入 TB_PAY_PAYMENT_REQUEST_VOUCHER 錯誤："+e.getLocalizedMessage());}
					if (paymentVouData.getSEQ_NBR() == null) {
						throw new NomsException("99", "無法取得新增TB_PAY_PAYMENT_REQUEST_VOUCHER後的憑證處理流水號");
					}
					String tempKey=obj.getString("voucherType")+obj.getString("voucherNbr")+obj.getString("vatNbr");
					//判斷更新或寫入 透過location id
					int locationCnt=1;
					Long paymentSeqNo;
					for (int iPayment = 0; iPayment < jsonPaymentArray.length(); iPayment++) {
						JSONObject objPayment = jsonPaymentArray.getJSONObject(iPayment);
						//如果tempKey跟明細相同則寫入
						if(tempKey.equals(objPayment.getString("tempKey"))){
							//寫入payment request Dtl
							TbPayPaymentRequestDtl dtl=this.makeTbPayPaymentRequestDtl(objPayment, today, paymentReqData.getPAYMENT_REQ_NO(), obj.getString("voucherType"),locationCnt);
							TbPayPaymentRequestDtlExample example = new TbPayPaymentRequestDtlExample();
							example.createCriteria().andLOCATION_IDEqualTo(dtl.getLOCATION_ID()).andPAYMENT_REQ_NOEqualTo(dtl.getPAYMENT_REQ_NO());
							int dtlCnt=pay005Dao.countPaymentRequestDtlByExample(example);
							if(dtlCnt>0){//如果已經存在 就更新 不存在就寫入
								HashMap<String , Object> dataObj = this.makeTbPayPaymentRequestDtlMap(dtl);
								try{
									pay005Dao.updateTbPayPaymentRequestDtl(dataObj);
								}catch(Exception e){throw new NomsException("更新 TB_PAY_PAYMENT_REQUEST_DTL 錯誤："+e.getLocalizedMessage());}	
							}else{
								try{
									pay005Dao.insertTbPayPaymentRequestDtl(dtl);
								}catch(Exception e){throw new NomsException("寫入 TB_PAY_PAYMENT_REQUEST_DTL 錯誤："+e.getLocalizedMessage());}
							}
							//寫入payment
							TbPayPayment payment = this.makePayPayment(dtl, objPayment, paymentReqData.getPAYMENT_REQ_NO(), today,"A");
							TbPayPaymentExample paymentExample = new TbPayPaymentExample();
							paymentExample.createCriteria().andLOCATION_IDEqualTo(dtl.getLOCATION_ID()).andPAYMENT_REQ_NOEqualTo(dtl.getPAYMENT_REQ_NO());
							int paymentCnt=pay005Dao.countPaymentByExample(paymentExample);
							//寫入 TbPayVoucherCredit
							TbPayVoucherCredit vouCredit=this.makeTbPaVoucherCredit(paymentVouData.getSEQ_NBR(), cashReqNo, paymentReqData.getPAYMENT_REQ_NO(), objPayment, dtl.getITEM_NO(), today);
							if(paymentCnt>0){//如果已經存在 就更新 不存在就寫入
								HashMap<String , Object> dataObj = this.makeTbPayPaymentMap(payment, dtl);
								try{
									pay005Dao.updateByExampleSelectiveWithPlus(dataObj);
									List<TbPayPayment> paymentList=pay005Dao.selectPaymentByExample(paymentExample);
									paymentSeqNo=paymentList.get(0).getSEQ_NBR();
								}catch(Exception e){throw new NomsException("更新 TB_PAY_PAYMENT 錯誤："+e.getLocalizedMessage());}
								//更新 TbPayVoucherCredit
								HashMap<String , Object> creditDataObj = this.makeTbPayVoucherCreditMap(payment, locationCnt, dtl, paymentVouData);
								try{
									pay005Dao.updateTbPayVoucherCreditSelectiveWithPlus(creditDataObj);
								}catch(Exception e){throw new NomsException("更新 TB_PAY_VOUCHER_CREDIT 錯誤："+e.getLocalizedMessage());}
							}else{
								try{
									pay005Dao.insertTbPayPayment(payment);
									if (payment.getSEQ_NBR() == null) {
										throw new NomsException("99", "無法取得新增TB_PAY_PAYMENT後的流水號");
									}else{
										paymentSeqNo=payment.getSEQ_NBR();
									}
								}catch(Exception e){throw new NomsException("寫入 TB_PAY_PAYMENT 錯誤："+e.getLocalizedMessage());}
								//寫入TbPayVoucherCredit
								try{
									pay005Dao.insertTbPayVoucherCredit(vouCredit);
								}catch(Exception e){throw new NomsException("寫入 TB_PAY_VOUCHER_CREDIT 錯誤："+e.getLocalizedMessage());}
							}
							//更新TB_PAY_PAYMENT_REQUEST_VOUCHER
							this.updatePaymentVoucherBySeqNo(paymentVouData.getSEQ_NBR());
							//寫入 pay repartition
							TbPayRepartition payRepartition = this.makeTbPayRepartition(today, repartionNo, paymentReqData.getPAYMENT_REQ_NO(), 
									dtl.getITEM_NO(), paymentSeqNo, payment.getLOCATION_ID(), objPayment, yearMonth);
							try{
								pay005Dao.insertTbPayRepartition(payRepartition);
							}catch(Exception e){throw new NomsException("寫入 TB_PAY_REPARTITION 錯誤："+e.getLocalizedMessage());}
							locationCnt+=1;
						}
					}
				}
				for (int i = 0; i < jsonCashArray.length(); i++) {
					JSONObject obj = jsonCashArray.getJSONObject(i);
					TbPayCashRequirement cashData = this.makeTbPayCashRequirement(obj, today);
					try{
						pay005Dao.insertTbPayCashRequirement(cashData);
					}catch(Exception e){throw new NomsException("寫入 TB_PAY_CASH_REQUIREMENT 錯誤："+e.getLocalizedMessage());}
				}
			}catch(Exception e){throw new NomsException(e.getLocalizedMessage());}
	}
	public void updatePaymentVoucherBySeqNo(Long seqNo){
		BigDecimal totalCreaidAmt = new BigDecimal(0.0);
		BigDecimal totalCreaidTax = new BigDecimal(0.0);
		try{
			TbPayVoucherCreditExample example = new TbPayVoucherCreditExample();
			example.createCriteria().andMST_SEQ_NBREqualTo(seqNo);
			List<TbPayVoucherCredit> list=pay005Dao.selectPayVoucherCreditByExample(example);
			for(int i=0;i<list.size();i++){
				totalCreaidAmt=new BigDecimal(this.add(list.get(i).getCREDIT_AMT().doubleValue(), totalCreaidAmt.doubleValue()));
				totalCreaidTax=new BigDecimal(this.add(list.get(i).getCREDIT_TAX().doubleValue(), totalCreaidTax.doubleValue()));
			}	
			TbPayPaymentRequestVoucher record = new TbPayPaymentRequestVoucher();
			record.setCREDIT_MADE_AMT(totalCreaidAmt);
			record.setCREDIT_MADE_TAX(totalCreaidTax);
			TbPayPaymentRequestVoucherExample exampleVou=new TbPayPaymentRequestVoucherExample();
			exampleVou.createCriteria().andSEQ_NBREqualTo(seqNo);
			pay005Dao.updateTbPayPaymentRequestVoucher(record, exampleVou);
		}catch(Exception e){throw new NomsException("更新 TB_PAY_PAYMENT_REQUEST_VOUCHER 錯誤："+e.getLocalizedMessage());}
	}
	public TbPayCashRequirement selectPayCashRequirementByPK(String cashReqNo){
		return pay005Dao.selectPayCashRequirementByPK(cashReqNo);
	}
	public List<TbPayPaymentItem> getPaymentItem(){
		TbPayPaymentItemExample example = new TbPayPaymentItemExample();
		example.createCriteria().andPAYMENT_REQ_ITEMEqualTo("MIP");
		return pay005Dao.getPaymentItem(example);
	}
	//取得明細資料
	public List<TbPayPaymentDTO> selectDtlAllData(String cashReqNo){
		PageList<TbPayPaymentDTO> DTOlist = new PageList<TbPayPaymentDTO>();
		/*
		HashMap<String,Object> dataObj = new HashMap<String,Object>();
		
		dataObj.put("cashReqNo", cashReqNo);
		List<TbPayPaymentDTO> list = pay005Dao.searchDtl(dataObj);
				for(int i=0;i<list.size();i++){
					TbPayPaymentDTO data=list.get(i);
					data.setCheckShare("Y");
					list.set(i, data);
				}
		*/
		TbPayPaymentRequestExample example = new TbPayPaymentRequestExample();
		example.createCriteria().andCASH_REQ_NOEqualTo(cashReqNo);
		List<TbPayPaymentRequest> list=pay005Dao.selectPaymentRequestByExample(example);
		for(int i=0;i<list.size();i++){
			List<TbPayPaymentRequestVoucherDTO> voucherList = new ArrayList<TbPayPaymentRequestVoucherDTO>();
			List<TbPayPaymentRequestDtl> dtlList = new ArrayList<TbPayPaymentRequestDtl>();
			List<TbPayPaymentDTO> paymentAmtList = new ArrayList<TbPayPaymentDTO>();
			List<TbPayPayment> paymentList = new ArrayList<TbPayPayment>();
			try{
				HashMap<String,Object> dataObj = new HashMap<String,Object>();
				dataObj.put("cashReqNo", list.get(i).getCASH_REQ_NO());
				dataObj.put("paymentReqNo", list.get(i).getPAYMENT_REQ_NO());
				voucherList=pay005Dao.getWithVoucherCredit(dataObj);
			}catch(NullPointerException e){log.error(e.getMessage());}
			try{
				TbPayPaymentRequestDtlExample dtlExample = new TbPayPaymentRequestDtlExample();
				dtlExample.createCriteria().andPAYMENT_REQ_NOEqualTo(list.get(i).getPAYMENT_REQ_NO());
				dtlList=pay005Dao.selectPaymentRequestDtlByExample(dtlExample);
			}catch(NullPointerException e){log.error(e.getMessage());}
			try{
				HashMap<String,Object> dataObj = new HashMap<String,Object>();
				dataObj.put("paymentReqNo", list.get(i).getPAYMENT_REQ_NO());
				paymentAmtList=pay005Dao.getPaymentAmt(dataObj);
			}catch(NullPointerException e){log.error(e.getMessage());}
			try{
				TbPayPaymentExample paymentExample = new TbPayPaymentExample();
				paymentExample.createCriteria().andPAYMENT_REQ_NOEqualTo(list.get(i).getPAYMENT_REQ_NO());
				paymentList=pay005Dao.selectPaymentByExample(paymentExample);
			}catch(NullPointerException e){log.error(e.getMessage());}
			DTOlist.add(this.makeTbPayPaymentDTO(voucherList, dtlList, paymentAmtList, paymentList));
		}
		log.debug("DTOlist : "+DTOlist.size());
		return DTOlist;
	}
	public TbPayPaymentDTO makeTbPayPaymentDTO(List<TbPayPaymentRequestVoucherDTO> voucherList,List<TbPayPaymentRequestDtl> dtlList,
			List<TbPayPaymentDTO> paymentAmtList,List<TbPayPayment> paymentList){
		TbPayPaymentDTO dto = new TbPayPaymentDTO();
		try{
			TbPayPaymentItemExample example = new TbPayPaymentItemExample();
			example.createCriteria().andEXP_ITEMEqualTo(dtlList.get(0).getPAYMENT_REQUEST_ITEM()).andPAYMENT_REQ_ITEMEqualTo("MIP");
			List<TbPayPaymentItem> paymentItemList=pay005Dao.getPaymentItem(example);
			dto.setDtlItemDesc(paymentItemList.get(0).getEXP_ITEM_DESC());
		}catch(IndexOutOfBoundsException e){log.error(e.getMessage());}
		catch(NullPointerException e){log.error(e.getMessage());}
		try{
			TbPayLookupCode lookupData=payPublicUtilService.getPayLookupByTypeAndCode("VOUCHER_TYPE", voucherList.get(0).getVOUCHER_TYPE());
			dto.setVoucherDesc(lookupData.getLOOKUP_CODE_DESC());
		}catch(IndexOutOfBoundsException e){log.error(e.getMessage());}
		catch(NullPointerException e){log.error(e.getMessage());}
		try{
			dto.setVOUCHER_NBR(voucherList.get(0).getVOUCHER_NBR());
		}catch(IndexOutOfBoundsException e){log.error(e.getMessage());}
		catch(NullPointerException e){log.error(e.getMessage());}
		try{
			dto.setVOUCHER_DATE(voucherList.get(0).getVOUCHER_DATE());
		}catch(IndexOutOfBoundsException e){log.error(e.getMessage());}
		catch(NullPointerException e){log.error(e.getMessage());}
		try{
			dto.setVAT_NUMBER(voucherList.get(0).getVAT_NUMBER());
		}catch(NullPointerException e){log.error(e.getMessage());}
		try{
			dto.setSumTaxExclusiveTotalAmt(paymentAmtList.get(0).getSumTaxExclusiveTotalAmt());
		}catch(IndexOutOfBoundsException e){log.error(e.getMessage());}
		catch(NullPointerException e){log.error(e.getMessage());}
		try{
			dto.setSumTotalBusinessTax(paymentAmtList.get(0).getSumTotalBusinessTax());
		}catch(IndexOutOfBoundsException e){log.error(e.getMessage());}
		catch(NullPointerException e){log.error(e.getMessage());}
		try{
			dto.setSumTotalIncomeTax(paymentAmtList.get(0).getSumTotalIncomeTax());
		}catch(IndexOutOfBoundsException e){log.error(e.getMessage());}
		catch(NullPointerException e){log.error(e.getMessage());}
		try{
			dto.setSumTotalNHIAmt(paymentAmtList.get(0).getSumTotalNHIAmt());
		}catch(IndexOutOfBoundsException e){log.error(e.getMessage());}
		catch(NullPointerException e){log.error(e.getMessage());}
		try{
			dto.setSumAmt(paymentAmtList.get(0).getSumAmt());
		}catch(IndexOutOfBoundsException e){log.error(e.getMessage());}
		catch(NullPointerException e){log.error(e.getMessage());}
		try{
			TbPayLookupCode lookupData=payPublicUtilService.getPayLookupByTypeAndCode("PAYMENT_METHOD", paymentList.get(0).getPAYMENT_METHOD());
			dto.setPaymentDesc(lookupData.getLOOKUP_CODE_DESC());
		}catch(IndexOutOfBoundsException e){log.error(e.getMessage());}
		catch(NullPointerException e){log.error(e.getMessage());}
		try{
			dto.setPAYMENT_USER_ID(paymentList.get(0).getPAYMENT_USER_ID());
		}catch(IndexOutOfBoundsException e){log.error(e.getMessage());}
		catch(NullPointerException e){log.error(e.getMessage());}
		try{
			dto.setPAYMENT_USER_NAME(paymentList.get(0).getPAYMENT_USER_NAME());
		}catch(IndexOutOfBoundsException e){log.error(e.getMessage());}
		catch(NullPointerException e){log.error(e.getMessage());}
		try{
			TbLsCollectUnitExample example=new TbLsCollectUnitExample();
			example.createCriteria().andUNIT_CODEEqualTo(paymentList.get(0).getBANK_CODE());
			List<TbLsCollectUnit> bankList=payPublicUtilDao.getTbLsCollectUnit(example);
			dto.setBankCodeDesc(bankList.get(0).getUNIT_NAME());
		}catch(IndexOutOfBoundsException e){log.error(e.getMessage());}
		catch(NullPointerException e){log.error(e.getMessage());}
		try{
			TbLsCollectUnitSubExample example=new TbLsCollectUnitSubExample();
			example.createCriteria().andUNIT_CODEEqualTo(paymentList.get(0).getBANK_BRANCH_CODE());
			List<TbLsCollectUnitSub> branchList=payPublicUtilDao.getTbLsCollectUnitSub(example);
			dto.setBranchCodeDesc(branchList.get(0).getSUB_NAME());
		}catch(IndexOutOfBoundsException e){log.error(e.getMessage());}
		catch(NullPointerException e){log.error(e.getMessage());}
		try{
			dto.setACCOUNT_NBR(paymentList.get(0).getACCOUNT_NBR());
		}catch(IndexOutOfBoundsException e){log.error(e.getMessage());}
		catch(NullPointerException e){log.error(e.getMessage());}
		try{
			dto.setCHECK_CASH_DATE(paymentList.get(0).getCHECK_CASH_DATE());
		}catch(IndexOutOfBoundsException e){log.error(e.getMessage());}
		catch(NullPointerException e){log.error(e.getMessage());}
		try{
			dto.setRESIDENT_ADDRESS(paymentList.get(0).getRESIDENT_ADDRESS());
		}catch(IndexOutOfBoundsException e){log.error(e.getMessage());}
		catch(NullPointerException e){log.error(e.getMessage());}
		try{
			dto.setMEMO(paymentList.get(0).getMEMO());
		}catch(IndexOutOfBoundsException e){log.error(e.getMessage());}
		catch(NullPointerException e){log.error(e.getMessage());}
		dto.setCheckShare("Y");
		return dto;
		
	}
	public HashMap<String,Object> makeTbPayPaymentRequestDtlMap(TbPayPaymentRequestDtl dtl){
		HashMap<String , Object> dataObj = new HashMap<String , Object>();
		dataObj.put("taxExclusiveAmt", dtl.getTAX_EXCLUSIVE_AMT());
		dataObj.put("businessTax", dtl.getBUSINESS_TAX());
		dataObj.put("paymentReqNo", dtl.getPAYMENT_REQ_NO());
		dataObj.put("locationId", dtl.getLOCATION_ID());
		return dataObj;
	}
	public HashMap<String,Object> makeTbPayVoucherCreditMap(TbPayPayment payment,int locationCnt,TbPayPaymentRequestDtl dtl,TbPayPaymentRequestVoucherDTO paymentVouData){
		HashMap<String , Object> creditDataObj = new HashMap<String , Object>();
		creditDataObj.put("taxExclusiveAmt", payment.getTAX_EXCLUSIVE_TOTAL_AMT());
		creditDataObj.put("businessTax", payment.getTOTAL_BUSINESS_TAX());
		creditDataObj.put("mstSeq", paymentVouData.getSEQ_NBR());
		creditDataObj.put("paymentReq", dtl.getPAYMENT_REQ_NO());
		creditDataObj.put("paymentReqItemNo", locationCnt);
		return creditDataObj;
	}
	public HashMap<String,Object> makeTbPayVoucherCreditMap(TbPayPayment payment,int locationCnt,Long paymentReqNo,Long seqNo){
		HashMap<String , Object> creditDataObj = new HashMap<String , Object>();
		creditDataObj.put("taxExclusiveAmt", payment.getTAX_EXCLUSIVE_TOTAL_AMT());
		creditDataObj.put("businessTax", payment.getTOTAL_BUSINESS_TAX());
		creditDataObj.put("mstSeq", seqNo);
		creditDataObj.put("paymentReq", paymentReqNo);
		creditDataObj.put("paymentReqItemNo", locationCnt);
		return creditDataObj;
	}
	public HashMap<String,Object> makeTbPayPaymentMap(TbPayPayment payment,TbPayPaymentRequestDtl dtl){
		HashMap<String , Object> dataObj = new HashMap<String , Object>();
		dataObj.put("TAX_EXCLUSIVE_TOTAL_AMT", payment.getTAX_EXCLUSIVE_TOTAL_AMT());
		dataObj.put("TOTAL_BUSINESS_TAX", payment.getTOTAL_BUSINESS_TAX());
		dataObj.put("TOTAL_INCOME_TAX", payment.getTOTAL_INCOME_TAX());
		dataObj.put("TOTAL_NHI_AMT", payment.getTOTAL_NHI_AMT());
		dataObj.put("MD_USER", payment.getMD_USER());
		dataObj.put("MD_TIME", payment.getMD_TIME());
		dataObj.put("LOCATION_ID", dtl.getLOCATION_ID());
		dataObj.put("PAYMENT_REQ_NO", dtl.getPAYMENT_REQ_NO());
		return dataObj;
	}
	public List<TbPayRepartitionDTO> getRepartion(Long paymentReqNo){
		TbPayRepartitionExample example = new TbPayRepartitionExample();
		example.createCriteria().andPAYMENT_REQ_NOEqualTo(paymentReqNo);
		List<TbPayRepartitionDTO> list=pay005Dao.getRepartion(example);
		for(int i=0;i<list.size();i++){
			TbPayRepartitionDTO data=list.get(i);
			list.set(i, this.makeTbPayRepartitionDTO(data));
		}
		return list;
	}
	public TbPayRepartitionDTO makeTbPayRepartitionDTO(TbPayRepartition data){
		TbPayRepartitionDTO dto = new TbPayRepartitionDTO();
		TbSiteMain site=pay005Dao.getTbSiteMain(data.getSITE_ID());
		try{
			dto.setSiteName(site.getSITE_NAME());
		}catch(NullPointerException e){}
		try{
			dto.setBts_SITE_ID(site.getBTS_SITE_ID());
		}catch(NullPointerException e){}
		dto.setSITE_ID(data.getSITE_ID());
		dto.setLOCATION_ID(data.getLOCATION_ID());
		dto.setREPARTITION_AMT(data.getREPARTITION_AMT());
		dto.setRepartition_AMTP(new BigDecimal(data.getREPARTITION_AMT().multiply(new BigDecimal(0.05)).setScale(2, BigDecimal.ROUND_HALF_UP)
                .doubleValue()));
		dto.setSpTotalAmt(data.getREPARTITION_AMT().add(dto.getRepartition_AMTP()));
		return dto;
	}
	public TbPayPaymentRequest makeTbPayPaymentRequest(JSONObject obj,Date today) throws NomsException{
		TbPayPaymentRequest paymentReqData = new TbPayPaymentRequest();
		try{
			paymentReqData.setCASH_REQ_NO(obj.getString("cash_REQ_NO"));
			paymentReqData.setPAYMENT_REQ_BEGIN_DATE(DateUtils.parse(obj.getString("payment_REQ_BEGIN_DATE"), "yyyy/MM/dd"));
			paymentReqData.setPAYMENT_REQ_END_DATE(DateUtils.parse(obj.getString("payment_REQ_END_DATE"), "yyyy/MM/dd"));
			paymentReqData.setCR_TIME(today);
			paymentReqData.setMD_TIME(today);
			paymentReqData.setCR_USER(getLoginUser().getUsername());
			paymentReqData.setMD_USER(getLoginUser().getUsername());
		}catch(JSONException e){throw new NomsException("makeTbPayPaymentRequest: "+e.getMessage());}
		return paymentReqData;
	}
	public TbPayPaymentRequestDtl makeTbPayPaymentRequestDtl(JSONObject obj,Date today,int locationCnt) throws NomsException{
		TbPayPaymentRequestDtl paymentReqDtlData = new TbPayPaymentRequestDtl();
		try{
			paymentReqDtlData.setITEM_NO((short)locationCnt);//
			paymentReqDtlData.setPAYMENT_REQ_NO(new Long(obj.getString("payment_REQ_NO")));
			paymentReqDtlData.setLOCATION_ID(obj.getString("location_ID"));//
			paymentReqDtlData.setPAYMENT_REQUEST_ITEM(obj.getString("payment_REQUEST_ITEM"));
			paymentReqDtlData.setTAX_EXCLUSIVE_AMT(new BigDecimal(obj.getString("tax_EXCLUSIVE_AMT")));
			paymentReqDtlData.setBUSINESS_TAX(new BigDecimal(obj.getString("business_TAX")));
			paymentReqDtlData.setPAYMENT_REQ_BEGIN_DATE(today);
			paymentReqDtlData.setPAYMENT_REQ_END_DATE(today);
			paymentReqDtlData.setCR_TIME(today);
			paymentReqDtlData.setMD_TIME(today);
			paymentReqDtlData.setCR_USER(getLoginUser().getUsername());
			paymentReqDtlData.setMD_USER(getLoginUser().getUsername());
		}catch(JSONException e){throw new NomsException("makeTbPayPaymentRequestDtl: "+e.getMessage());}
		return paymentReqDtlData;
	}
	
	public TbPayPaymentRequestDtl makeTbPayPaymentRequestDtl(JSONObject obj,Date today,Long paymentReqNo,String paymentRequestItem,int locationCnt) throws NomsException{
		TbPayPaymentRequestDtl paymentReqDtlData = new TbPayPaymentRequestDtl();
		try{
			paymentReqDtlData.setITEM_NO((short)locationCnt);//
			paymentReqDtlData.setPAYMENT_REQ_NO(paymentReqNo);
			paymentReqDtlData.setLOCATION_ID(obj.getString("locationId"));//
			paymentReqDtlData.setPAYMENT_REQUEST_ITEM(paymentRequestItem);
			paymentReqDtlData.setTAX_EXCLUSIVE_AMT(new BigDecimal(obj.getString("repartition_AMT")));
			paymentReqDtlData.setBUSINESS_TAX(new BigDecimal(obj.getString("repartition_AMTP")));
			paymentReqDtlData.setPAYMENT_REQ_BEGIN_DATE(today);
			paymentReqDtlData.setPAYMENT_REQ_END_DATE(today);
			paymentReqDtlData.setPAYMENT_REQ_USER_ID(obj.getString("payment_USER_ID"));
			paymentReqDtlData.setCR_TIME(today);
			paymentReqDtlData.setMD_TIME(today);
			paymentReqDtlData.setCR_USER(getLoginUser().getUsername());
			paymentReqDtlData.setMD_USER(getLoginUser().getUsername());
		}catch(JSONException e){throw new NomsException("makeTbPayPaymentRequestDtl: "+e.getMessage());}
		return paymentReqDtlData;
	}
	
	public TbPayPayment makePayPayment(TbPayPaymentRequestDtl dtl,JSONObject payment,Long paymentReqNo,Date today,String type) throws NomsException{
		TbPayPayment paymentData = new TbPayPayment();
		try{
			paymentData.setPAYMENT_REQ_NO(paymentReqNo);
			paymentData.setPAYMENT_REQ_ITEM_NO(dtl.getITEM_NO());
			paymentData.setLOCATION_ID(dtl.getLOCATION_ID());
			paymentData.setPAYMENT_REQ_USER_ID(payment.getString("payment_USER_ID"));
			paymentData.setPAYMENT_USER_ID(payment.getString("payment_USER_ID"));
			paymentData.setPAYMENT_USER_NAME(payment.getString("payment_USER_NAME"));
			paymentData.setACCOUNT_NBR(payment.getString("account_NBR"));
			paymentData.setPAYMENT_METHOD(payment.getString("payment_METHOD"));
			paymentData.setBANK_CODE(payment.getString("bank_CODE"));
			paymentData.setBANK_BRANCH_CODE(payment.getString("bank_BRANCH_CODE"));
			paymentData.setMEMO(payment.getString("memo"));
			paymentData.setRESIDENT_ADDRESS(payment.getString("resident_ADDRESS"));
			paymentData.setCHECK_CASH_DATE(DateUtils.parse(payment.getString("check_CASH_DATE"), "yyyy/MM/dd"));
			if("SP".equals(type)){
				paymentData.setTAX_EXCLUSIVE_TOTAL_AMT(new BigDecimal(payment.getString("tax_EXCLUSIVE_AMT")));
				paymentData.setTOTAL_BUSINESS_TAX(new BigDecimal(payment.getString("business_TAX")));
				paymentData.setTOTAL_INCOME_TAX(new BigDecimal(payment.getString("total_INCOME_TAX")));//尚未乘上稅率
				paymentData.setTOTAL_NHI_AMT(new BigDecimal(payment.getString("total_NHI_amt")));//尚未乘上稅率
			}else{
				paymentData.setTAX_EXCLUSIVE_TOTAL_AMT(new BigDecimal(payment.getString("repartition_AMT")));
				paymentData.setTOTAL_BUSINESS_TAX(new BigDecimal(payment.getString("repartition_AMTP")));
				paymentData.setTOTAL_INCOME_TAX(new BigDecimal(payment.getString("repartition_AMT")));//尚未乘上稅率
				paymentData.setTOTAL_NHI_AMT(new BigDecimal(payment.getString("repartition_AMT")));//尚未乘上稅率
			}
			paymentData.setSTATUS("N");
			paymentData.setCR_TIME(today);
			paymentData.setMD_TIME(today);
			paymentData.setCR_USER(getLoginUser().getUsername());
			paymentData.setMD_USER(getLoginUser().getUsername());
		}catch(JSONException e){throw new NomsException("makePayPayment: "+e.getMessage());}
		return paymentData;
	}
	
	public TbPayPaymentRequestVoucherDTO makeTbPayPaymentRequestVoucher(JSONObject obj,Date today) throws NomsException{
		TbPayPaymentRequestVoucherDTO paymentVouData = new TbPayPaymentRequestVoucherDTO();
		try{
			paymentVouData.setPROCESS_TYPE(obj.getString("process_TYPE"));
			paymentVouData.setVOUCHER_NO(obj.getString("voucher_NO"));
			paymentVouData.setVOUCHER_TYPE(obj.getString("voucher_TYPE"));
			paymentVouData.setVOUCHER_NBR(obj.getString("voucher_NBR"));
			paymentVouData.setVOUCHER_DATE(DateUtils.parse(obj.getString("voucher_DATE"), "yyyy/MM/dd"));
			paymentVouData.setVAT_NUMBER(obj.getString("vat_NUMBER"));
			paymentVouData.setTAX_EXCLUSIVE_AMT(new BigDecimal(obj.getString("tax_EXCLUSIVE_AMT")));
			paymentVouData.setBUSINESS_TAX(new BigDecimal(obj.getString("business_TAX")));
			paymentVouData.setCR_TIME(today);
			paymentVouData.setMD_TIME(today);
			paymentVouData.setCR_USER(getLoginUser().getUsername());
			paymentVouData.setMD_USER(getLoginUser().getUsername());
		}catch(JSONException e){throw new NomsException("makeTbPayPaymentRequestVoucher: "+e.getMessage());}
		return paymentVouData;
	}
	public TbPayVoucherCredit makeTbPaVoucherCredit(JSONObject voucherCredit,Long paymentReqNo,int locationCnt,Date today) throws NomsException{
		TbPayVoucherCredit payVoucherCreditData = new TbPayVoucherCredit();
		try{
			payVoucherCreditData.setMST_SEQ_NBR(new Long(voucherCredit.getString("mst_SEQ_NBR")));
			payVoucherCreditData.setPAYMENT_REQ_NO(paymentReqNo);
			payVoucherCreditData.setPAYMENT_REQ_ITEM_NO((short)locationCnt);
			payVoucherCreditData.setCASH_REQ_AP_NO(voucherCredit.getString("cash_REQ_AP_NO"));
			payVoucherCreditData.setCREDIT_AMT(new BigDecimal(voucherCredit.getString("credit_AMT")));
			payVoucherCreditData.setCREDIT_TAX(new BigDecimal(voucherCredit.getString("credit_TAX")));
			payVoucherCreditData.setCREDIT_DATE(today);
			payVoucherCreditData.setCR_TIME(today);
			payVoucherCreditData.setMD_TIME(today);
			payVoucherCreditData.setCR_USER(getLoginUser().getUsername());
			payVoucherCreditData.setMD_USER(getLoginUser().getUsername());
		}catch(JSONException e){throw new NomsException("makeTbPaVoucherCredit: "+e.getMessage());}
		return payVoucherCreditData;
	}
	public TbPayVoucherCredit makeTbPaVoucherCredit(Long mstSeqNo,String cashReqNo,Long paymentReqNo,JSONObject payment,int locationCnt,Date today) throws NomsException{
		TbPayVoucherCredit payVoucherCreditData = new TbPayVoucherCredit();
		try{
			payVoucherCreditData.setMST_SEQ_NBR(mstSeqNo);
			payVoucherCreditData.setPAYMENT_REQ_NO(paymentReqNo);
			payVoucherCreditData.setPAYMENT_REQ_ITEM_NO((short)locationCnt);
			payVoucherCreditData.setCASH_REQ_AP_NO(cashReqNo);
			payVoucherCreditData.setCREDIT_AMT(new BigDecimal(payment.getString("repartition_AMT")));
			payVoucherCreditData.setCREDIT_TAX(new BigDecimal(payment.getString("repartition_AMTP")));
			payVoucherCreditData.setCREDIT_DATE(today);
			payVoucherCreditData.setCR_TIME(today);
			payVoucherCreditData.setMD_TIME(today);
			payVoucherCreditData.setCR_USER(getLoginUser().getUsername());
			payVoucherCreditData.setMD_USER(getLoginUser().getUsername());
		}catch(JSONException e){throw new NomsException("makeTbPaVoucherCredit: "+e.getMessage());}
		return payVoucherCreditData;
	}
	public TbPayCashRequirement makeTbPayCashRequirement(JSONObject obj,Date today) throws NomsException{
		TbPayCashRequirement cashData = new TbPayCashRequirement();
		try{
		cashData.setCASH_REQ_NO(obj.getString("cash_REQ_NO"));
		cashData.setPROCESS_TYPE(obj.getString("process_TYPE"));
		cashData.setAPP_USER(obj.getString("app_USER"));
		cashData.setAPP_DATE(DateUtils.parse(obj.getString("app_DATE"), "yyyy/MM/dd"));
		cashData.setYEAR_MONTH(obj.getString("year_MONTH"));
		cashData.setSTATUS(obj.getString("status"));
		cashData.setDOMAIN(obj.getString("domain"));
		cashData.setCR_TIME(today);
		cashData.setMD_TIME(today);
		cashData.setCR_USER(getLoginUser().getUsername());
		cashData.setMD_USER(getLoginUser().getUsername());
		}catch(JSONException e){throw new NomsException("makeTbPayCashRequirement: "+e.getMessage());}
		return cashData;
	}
	
	public TbPayRepartition makeTbPayRepartition(Date today,String repartionNo,Long paymentReqNo,short itemNo,
			Long paymentSeqNo,String locationId,JSONObject obj,String yearMonth) throws NomsException{
		TbPayRepartition repartitionData = new TbPayRepartition();
		try{
			repartitionData.setREPARTITION_NO(repartionNo);
			repartitionData.setPAYMENT_REQ_NO(paymentReqNo);
			repartitionData.setPAYMENT_REQ_ITEM_NO(itemNo);
			repartitionData.setPAYMENT_SEQ_NBR(paymentSeqNo);
			repartitionData.setLOCATION_ID(locationId);
			repartitionData.setSITE_ID(obj.getString("siteId"));
			repartitionData.setYEAR_MONTH(yearMonth);
			repartitionData.setREPARTITION_AMT(new BigDecimal(obj.getString("repartition_AMT")));
			repartitionData.setEXP_TYPE("MIR");
			repartitionData.setCR_TIME(today);
			repartitionData.setMD_TIME(today);
			repartitionData.setCR_USER(getLoginUser().getUsername());
			repartitionData.setMD_USER(getLoginUser().getUsername());
		}catch(JSONException e){throw new NomsException("makeTbPayRepartition: "+e.getMessage());}
		return repartitionData;
	}
	
	public TbPayRepartition makeTbPayRepartition(Date today,String repartionNo,Long paymentReqNo,short itemNo,
			Long paymentSeqNo,JSONObject obj) throws NomsException{
		TbPayRepartition repartitionData = new TbPayRepartition();
		try{
			repartitionData.setREPARTITION_NO(repartionNo);
			repartitionData.setPAYMENT_REQ_NO(paymentReqNo);
			repartitionData.setPAYMENT_REQ_ITEM_NO(itemNo);
			repartitionData.setPAYMENT_SEQ_NBR(paymentSeqNo);
			repartitionData.setLOCATION_ID(obj.getString("location_ID"));
			repartitionData.setSITE_ID(obj.getString("site_ID"));
			repartitionData.setYEAR_MONTH(obj.getString("year_MONTH"));
			repartitionData.setREPARTITION_AMT(new BigDecimal(obj.getString("repartition_AMT")));
			repartitionData.setEXP_TYPE(obj.getString("exp_TYPE"));
			repartitionData.setCR_TIME(today);
			repartitionData.setMD_TIME(today);
			repartitionData.setCR_USER(getLoginUser().getUsername());
			repartitionData.setMD_USER(getLoginUser().getUsername());
		}catch(JSONException e){throw new NomsException("makeTbPayRepartition: "+e.getMessage());}
		return repartitionData;
	}
}
