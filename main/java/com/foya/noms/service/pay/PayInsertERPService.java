package com.foya.noms.service.pay;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foya.dao.mybatis.mapper.TbAuthPersonnelMapper;
import com.foya.dao.mybatis.mapper.TbLsLessorMapper;
import com.foya.dao.mybatis.mapper.TbNomsApBankInterfaceMapper;
import com.foya.dao.mybatis.mapper.TbNomsApInvoiceLinesTempMapper;
import com.foya.dao.mybatis.mapper.TbNomsApInvoiceTempMapper;
import com.foya.dao.mybatis.mapper.TbNomsGvInterfaceTempMapper;
import com.foya.dao.mybatis.mapper.TbNomsVoidedCheckTempMapper;
import com.foya.dao.mybatis.mapper.TbPayCashRequirementMapper;
import com.foya.dao.mybatis.mapper.TbPayCheckDisregardMapper;
import com.foya.dao.mybatis.mapper.TbPayElectricityMapper;
import com.foya.dao.mybatis.mapper.TbPayPaymentRequestVoucherMapper;
import com.foya.dao.mybatis.mapper.TbPayVoucherCreditMapper;
import com.foya.dao.mybatis.mapper.UTbPayPaymentMapper;
import com.foya.dao.mybatis.model.TbAuthPersonnel;
import com.foya.dao.mybatis.model.TbAuthPersonnelExample;
import com.foya.dao.mybatis.model.TbLsLessor;
import com.foya.dao.mybatis.model.TbLsLessorExample;
import com.foya.dao.mybatis.model.TbNomsApBankInterface;
import com.foya.dao.mybatis.model.TbNomsApInvoiceLinesTemp;
import com.foya.dao.mybatis.model.TbNomsApInvoiceTemp;
import com.foya.dao.mybatis.model.TbNomsGvInterfaceTemp;
import com.foya.dao.mybatis.model.TbNomsVoidedCheckTemp;
import com.foya.dao.mybatis.model.TbPayCashRequirement;
import com.foya.dao.mybatis.model.TbPayCheckDisregard;
import com.foya.dao.mybatis.model.TbPayCheckDisregardExample;
import com.foya.dao.mybatis.model.TbPayElectricity;
import com.foya.dao.mybatis.model.TbPayElectricityExample;
import com.foya.dao.mybatis.model.TbPayPaymentRequestVoucher;
import com.foya.dao.mybatis.model.TbPayPaymentRequestVoucherExample;
import com.foya.dao.mybatis.model.TbPayVoucherCredit;
import com.foya.dao.mybatis.model.TbPayVoucherCreditExample;
import com.foya.exception.NomsException;
import com.foya.noms.dto.pay.TbPayPaymentDTO;


/**
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
 * <td>2015/1/12</td>
 * <td>insertERP中介檔</td>
 * <td>Charlie Woo</td>
 * </tr>
 * </table> 
 *
 * @author Charlie Woo
 *
 */
@Service
public class PayInsertERPService {
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private PayPublicUtilService payPublicUtilService;
	@Autowired
	private TbPayCashRequirementMapper tbPayCashRequirementMapper;
	@Autowired
	private UTbPayPaymentMapper utbPayPaymentMapper;
	@Autowired
	private TbPayElectricityMapper tbPayElectricityMapper;
	@Autowired
	private TbLsLessorMapper tbLsLessorMapper;
	@Autowired
	private TbPayVoucherCreditMapper tbPayVoucherCreditMapper;
	@Autowired
	private TbPayPaymentRequestVoucherMapper tbPayPaymentRequestVoucherMapper;
	@Autowired
	private TbNomsApInvoiceTempMapper tbNomsApInvoiceTempMapper;
	@Autowired
	private TbNomsApInvoiceLinesTempMapper tbNomsApInvoiceLinesTempMapper;
	@Autowired
	private TbNomsApBankInterfaceMapper tbNomsApBankInterfaceMapper;
	@Autowired
	private TbNomsGvInterfaceTempMapper tbNomsGvInterfaceTempMapper;
	@Autowired
	private TbPayCheckDisregardMapper tbPayCheckDisregardMapper;
	@Autowired
	private TbAuthPersonnelMapper tbAuthPersonnelMapper;
	@Autowired
	private TbNomsVoidedCheckTempMapper tbNomsVoidedCheckTempMapper;
	
	public void insertERP(TbPayCashRequirement exportObject) throws NomsException{
		
		//查詢主檔資料 只會有單筆
		TbPayCashRequirement tbPayCashRequirement = tbPayCashRequirementMapper.selectByPrimaryKey(exportObject.getCASH_REQ_NO());
		//處理類別抓畫面上的
		tbPayCashRequirement.setPROCESS_TYPE(exportObject.getPROCESS_TYPE());
		//table 1,2用的資料
		List<TbPayPaymentDTO> list1 = utbPayPaymentMapper.selectByCashReqNo1(exportObject.getCASH_REQ_NO());
		//剩下table用的資料
		List<TbPayPaymentDTO> list2 = utbPayPaymentMapper.selectByCashReqNo2(exportObject.getCASH_REQ_NO());
		
		for (int i = 0; i < list1.size(); i++) {
			
			insertNomsApInvoiceTemp(tbPayCashRequirement,list1,i);
			
			insertNomsApInvoiceLinesTemp(tbPayCashRequirement,list1,i);
		}
		
		for (int i = 0; i < list2.size(); i++) {	
			insertNomsApBankInterface(tbPayCashRequirement,list2,i);
						
			//憑證 先取得多筆MST_SEQ_NBR
			TbPayVoucherCreditExample tbPayVoucherCreditExample = new TbPayVoucherCreditExample();
			com.foya.dao.mybatis.model.TbPayVoucherCreditExample.Criteria criteria= tbPayVoucherCreditExample.createCriteria();
			criteria.andCASH_REQ_AP_NOEqualTo(exportObject.getCASH_REQ_NO());
			criteria.andPAYMENT_REQ_NOEqualTo(list2.get(i).getPAYMENT_REQ_NO());
			criteria.andPAYMENT_REQ_ITEM_NOEqualTo(list2.get(i).getPAYMENT_REQ_ITEM_NO());
			List<TbPayVoucherCredit> listtPVC= tbPayVoucherCreditMapper.selectByExample(tbPayVoucherCreditExample);
			
			//用MST_SEQ_NBR去查TB_PAY_PAYMENT_REQUEST_VOUCHER(每筆seqNbr會查出一筆資料,每筆都要insert)
			for (int j = 0; j <= listtPVC.size(); j++) {
				TbPayPaymentRequestVoucherExample tbPayPaymentRequestVoucherExample = new TbPayPaymentRequestVoucherExample();
				com.foya.dao.mybatis.model.TbPayPaymentRequestVoucherExample.Criteria criteria2= tbPayPaymentRequestVoucherExample.createCriteria();
				criteria2.andSEQ_NBREqualTo(listtPVC.get(j).getMST_SEQ_NBR());
				List<TbPayPaymentRequestVoucher> listtPPRVE= tbPayPaymentRequestVoucherMapper.selectByExample(tbPayPaymentRequestVoucherExample);
				
				insertNomsGvInterfaceTemp(tbPayCashRequirement,list2,listtPPRVE.get(0),i);
			}
			
			
		}
		//處理類別=支票作廢 時 要做
		if("CKD".equals(exportObject.getPROCESS_TYPE())){
			
			TbPayCheckDisregardExample tbPayCheckDisregardExample = new TbPayCheckDisregardExample();
			com.foya.dao.mybatis.model.TbPayCheckDisregardExample.Criteria criteria= tbPayCheckDisregardExample.createCriteria();
			criteria.andCASH_REQ_NOEqualTo(exportObject.getCASH_REQ_NO());
			List<TbPayCheckDisregard> list3 = tbPayCheckDisregardMapper.selectByExample(tbPayCheckDisregardExample);
			for(int i=0;i<list2.size();i++){
				insertNomsVoidedCheckTemp(tbPayCashRequirement,list3,i);
			}
			
	
		}
		
	}
	
	public void insertNomsApInvoiceTemp(TbPayCashRequirement tbPayCashRequirement,List<TbPayPaymentDTO> list,int i) throws NomsException{
		TbNomsApInvoiceTemp tbNomsApInvoiceTemp = new TbNomsApInvoiceTemp();
		//資料型態
		tbNomsApInvoiceTemp.setDATA_TYPE("I");
		//TODO
		//請款狀態必輸 但spec尚未給 先塞
		tbNomsApInvoiceTemp.setPR_STATUS(tbPayCashRequirement.getSTATUS());
		
		if (tbPayCashRequirement.getCASH_REQ_NO().startsWith("R")) {
			tbNomsApInvoiceTemp.setPR_TYPE("REP");
		} else if (tbPayCashRequirement.getCASH_REQ_NO().startsWith("E")) {
			tbNomsApInvoiceTemp.setPR_TYPE("ELP");
		} else if (tbPayCashRequirement.getCASH_REQ_NO().startsWith("L")) {
			tbNomsApInvoiceTemp.setPR_TYPE("LIP");
		} else if (tbPayCashRequirement.getCASH_REQ_NO().startsWith("M")) {
			tbNomsApInvoiceTemp.setPR_TYPE("MIP");
		} //支票作廢未判斷
		
		//請款單號
		tbNomsApInvoiceTemp.setGROUP_ID(tbPayCashRequirement.getCASH_REQ_NO());
		//請款編號
		tbNomsApInvoiceTemp.setPAYMENT_REQ_NO(list.get(i).getPAYMENT_REQ_NO());
		//請款細項編號
		tbNomsApInvoiceTemp.setPAYMENT_REQ_ITEM_NO(list.get(i).getPAYMENT_REQ_ITEM_NO());
		//實際請款起始日
		tbNomsApInvoiceTemp.setPAYMENT_REQ_BEGIN_DATE(list.get(i).getPAYMENT_REQ_BEGIN_DATE());
		//實際請款結束日
		tbNomsApInvoiceTemp.setPAYMENT_REQ_END_DATE(list.get(i).getPAYMENT_REQ_END_DATE());
		//請款年月
		tbNomsApInvoiceTemp.setREQ_YYYYMM(tbPayCashRequirement.getYEAR_MONTH());
		//租約編號
		if("REP".equals(tbNomsApInvoiceTemp.getPR_TYPE()) || "ELP".equals(tbNomsApInvoiceTemp.getPR_TYPE())){
			tbNomsApInvoiceTemp.setLEASE_NO(list.get(i).getCONTRACT_NO());
		}
		//電錶號碼/專線編號
		if("ELP".equals(tbNomsApInvoiceTemp.getPR_TYPE())){
			tbNomsApInvoiceTemp.setELEC_METER_LINE_NBR(list.get(i).getELECTRICITY_METER_NBR());
		} else if("LIP".equals(tbNomsApInvoiceTemp.getPR_TYPE())){
			tbNomsApInvoiceTemp.setELEC_METER_LINE_NBR(list.get(i).getCONTRACT_NO());
		}
		//基站編號
		if("LIP".equals(tbNomsApInvoiceTemp.getPR_TYPE())){
			TbPayElectricityExample tbPayElectricityExample = new TbPayElectricityExample();
			com.foya.dao.mybatis.model.TbPayElectricityExample.Criteria criteria= tbPayElectricityExample.createCriteria();
			criteria.andPAYMENT_REQ_NOEqualTo(tbNomsApInvoiceTemp.getPAYMENT_REQ_NO());
			criteria.andPAYMENT_REQ_ITEM_NOEqualTo(tbNomsApInvoiceTemp.getPAYMENT_REQ_ITEM_NO());
			criteria.andUSE_TYPEEqualTo("L");
			criteria.andCONTRACT_NOEqualTo(list.get(i).getCONTRACT_NO());
			List<TbPayElectricity> listtPE= tbPayElectricityMapper.selectByExample(tbPayElectricityExample);
			tbNomsApInvoiceTemp.setSITE_ID(listtPE.get(0).getSITE_ID());
		}
		//站點編號
		tbNomsApInvoiceTemp.setLOCATION_ID(list.get(i).getLOCATION_ID());
		//出租人類型
		TbLsLessorExample tbLsLessorExample = new TbLsLessorExample();
		com.foya.dao.mybatis.model.TbLsLessorExample.Criteria criteria2= tbLsLessorExample.createCriteria();
		criteria2.andLESSOR_IDEqualTo(list.get(i).getPAYMENT_REQ_USER_ID());
		criteria2.andLS_NOEqualTo(tbNomsApInvoiceTemp.getLEASE_NO());
		List<TbLsLessor> listtLL= tbLsLessorMapper.selectByExample(tbLsLessorExample);
		if(listtLL!=null && !listtLL.isEmpty()){
			tbNomsApInvoiceTemp.setLESSOR_ID(listtLL.get(0).getLESSOR_ID());
		}
		//出租人證號
		tbNomsApInvoiceTemp.setLESSOR_TYPE(list.get(i).getPAYMENT_REQ_USER_ID());
		//TODO
		//付款對象證號
		//tbNomsApInvoiceTemp.setPAYMENT_USER_ID(list.get(i).getPAYMENT_USER_ID());
		//申請人代碼
		tbNomsApInvoiceTemp.setAPP_USER(tbPayCashRequirement.getAPP_USER());
		//申請日期
		tbNomsApInvoiceTemp.setINVOICE_DATE(tbPayCashRequirement.getAPP_DATE());
		//是否止付
		tbNomsApInvoiceTemp.setIF_STOP("N");
		//實付金額
		tbNomsApInvoiceTemp.setINVOICE_AMOUNT(list.get(i).getTAX_EXCLUSIVE_AMT().add(list.get(i).getBUSINESS_TAX()));
		//付款週期
		tbNomsApInvoiceTemp.setPAYMENT_PERIOD(tbPayCashRequirement.getPAYMENT_PERIOD().toString());
		//金額type
		int r= tbNomsApInvoiceTemp.getINVOICE_AMOUNT().compareTo(BigDecimal.ZERO);
		if(r==-1){
			tbNomsApInvoiceTemp.setINVOICE_TYPE("N");
		} else {
			tbNomsApInvoiceTemp.setINVOICE_TYPE("P");
		}
		//TODO
		//付款模式(長度為1 但查出來的長度超過 必輸 先給W)
		//tbNomsApInvoiceTemp.setPAY_MODE("W");
		if(listtLL!=null && !listtLL.isEmpty()){
			tbNomsApInvoiceTemp.setINCOME_TAX(listtLL.get(0).getINCOME_TAX());
		} else {
			//TODO
			//必輸 查無資料先給N
			tbNomsApInvoiceTemp.setINCOME_TAX("N");
		}
		//是否代扣所得稅
		
		
		
		TbPayVoucherCreditExample tbPayVoucherCreditExample = new TbPayVoucherCreditExample();
		com.foya.dao.mybatis.model.TbPayVoucherCreditExample.Criteria criteria3= tbPayVoucherCreditExample.createCriteria();
		criteria3.andCASH_REQ_AP_NOEqualTo(tbNomsApInvoiceTemp.getGROUP_ID());
		criteria3.andPAYMENT_REQ_NOEqualTo(tbNomsApInvoiceTemp.getPAYMENT_REQ_NO());
		criteria3.andPAYMENT_REQ_ITEM_NOEqualTo(tbNomsApInvoiceTemp.getPAYMENT_REQ_ITEM_NO());
		criteria3.andCONTRACT_PO_NOEqualTo(list.get(i).getCONTRACT_NO());
		List<TbPayVoucherCredit> listttPVC= tbPayVoucherCreditMapper.selectByExample(tbPayVoucherCreditExample);
		
		TbPayPaymentRequestVoucherExample tbPayPaymentRequestVoucherExample = new TbPayPaymentRequestVoucherExample();
		com.foya.dao.mybatis.model.TbPayPaymentRequestVoucherExample.Criteria criteria4= tbPayPaymentRequestVoucherExample.createCriteria();
		if(listttPVC!=null && !listttPVC.isEmpty()){
			criteria4.andSEQ_NBREqualTo(listttPVC.get(0).getMST_SEQ_NBR());
		}
		List<TbPayPaymentRequestVoucher> listttPPRV = tbPayPaymentRequestVoucherMapper.selectByExample(tbPayPaymentRequestVoucherExample);
		if(listttPPRV!=null && !listttPPRV.isEmpty()){
			//憑證類別
			tbNomsApInvoiceTemp.setGV_TYPE(listttPPRV.get(0).getVOUCHER_TYPE());
			//憑證號碼
			tbNomsApInvoiceTemp.setGUI_NUMBER(listttPPRV.get(0).getVOUCHER_NBR());
		}
		//insert
		tbNomsApInvoiceTempMapper.insertSelective(tbNomsApInvoiceTemp);
	}
	

	public void insertNomsApInvoiceLinesTemp(TbPayCashRequirement tbPayCashRequirement,List<TbPayPaymentDTO> list,int i) throws NomsException{
		TbNomsApInvoiceLinesTemp tbNomsApInvoiceLinesTemp = new TbNomsApInvoiceLinesTemp();
		//請款單號
		tbNomsApInvoiceLinesTemp.setGROUP_ID(tbPayCashRequirement.getCASH_REQ_NO());
		//請款編號
		tbNomsApInvoiceLinesTemp.setPAYMENT_REQ_NO(list.get(i).getPAYMENT_REQ_NO());
		//請款細項編號
		tbNomsApInvoiceLinesTemp.setPAYMENT_REQ_ITEM_NO(list.get(i).getPAYMENT_REQ_ITEM_NO());
		//請款金額
		tbNomsApInvoiceLinesTemp.setAMOUNT(list.get(i).getTAX_EXCLUSIVE_AMT().add(list.get(i).getBUSINESS_TAX()));
		
		//insert
		tbNomsApInvoiceLinesTempMapper.insertSelective(tbNomsApInvoiceLinesTemp);
	}
	
	public void insertNomsApBankInterface(TbPayCashRequirement tbPayCashRequirement,List<TbPayPaymentDTO> list,int i) throws NomsException{
		TbNomsApBankInterface tbNomsApBankInterface = new TbNomsApBankInterface();
		//請款編號
		tbNomsApBankInterface.setPAYMENT_REQ_NO(list.get(i).getPAYMENT_REQ_NO());
		//請款細項編號
		tbNomsApBankInterface.setPAYMENT_REQ_ITEM_NO(list.get(i).getPAYMENT_REQ_ITEM_NO());
		//受款人代號 統編/身份證號碼
		tbNomsApBankInterface.setBENEFICIARY_NO(list.get(i).getPAYMENT_USER_ID());
		//受款人名稱（受款全名）
		tbNomsApBankInterface.setBENEFICIARY_NAME(list.get(i).getPAYMENT_USER_NAME());
		//付款模式
		tbNomsApBankInterface.setPAY_MODE(list.get(i).getPAYMENT_METHOD());
		//解款銀行金資代碼
		tbNomsApBankInterface.setACCOUNT_SWIFT(list.get(i).getBANK_CODE() + list.get(i).getBANK_BRANCH_CODE());
		//解款銀行帳號
		tbNomsApBankInterface.setACCOUNT_NO(list.get(i).getACCOUNT_NBR());
		//匯款幣別
		tbNomsApBankInterface.setPAY_CURRENCY("TWD");
		//支票/匯款金額
		tbNomsApBankInterface.setPAY_MONEY(
						 list.get(i).getTAX_EXCLUSIVE_TOTAL_AMT().add(
								 list.get(i).getTOTAL_BUSINESS_TAX()).add(
										 list.get(i).getTOTAL_INCOME_TAX()).add(
												 list.get(i).getTOTAL_NHI_AMT()));
		//票面到期日(付款日)
		tbNomsApBankInterface.setPAY_DATE(list.get(i).getCHECK_CASH_DATE());
		//外部系統編號
		tbNomsApBankInterface.setEXTEND_NUMBER(list.get(i).getCONTRACT_NO());
		//系統日期
		tbNomsApBankInterface.setCREATION_DATE(payPublicUtilService.getToday());
		//來源系統
		tbNomsApBankInterface.setSOURCE("NOMS");
		//S:暫停支付/I:假扣押/V:支票作廢
		if(("CKD").equals(tbPayCashRequirement.getPROCESS_TYPE())){
			tbNomsApBankInterface.setCANCEL_REASON("V");
		} else {
			if(list.get(i).getDOCUMENT_NO()==null || list.get(i).getDOCUMENT_NO().isEmpty()){
				tbNomsApBankInterface.setCANCEL_REASON("I");
			}
		}
		//Y/N 預設給N
		tbNomsApBankInterface.setCANCEL_FLAG("N");
		//支票號碼
		tbNomsApBankInterface.setCHECK_NUMBER(list.get(i).getCHECK_NBR());
		//假扣押文案編號
		tbNomsApBankInterface.setP_ATT_NO(list.get(i).getDOCUMENT_NO());
		//原匯款帳號
		tbNomsApBankInterface.setORIGINAL_ACCOUNT_NO(list.get(i).getORIGINAL_ACCOUNT_NBR());
		
		//insert
		tbNomsApBankInterfaceMapper.insertSelective(tbNomsApBankInterface);
	}
	
	public void insertNomsGvInterfaceTemp(TbPayCashRequirement tbPayCashRequirement,List<TbPayPaymentDTO> list,TbPayPaymentRequestVoucher tPPRV,int i) throws NomsException{
		TbNomsGvInterfaceTemp tbNomsGvInterfaceTemp = new TbNomsGvInterfaceTemp();
		//憑證號碼
		tbNomsGvInterfaceTemp.setGUI_NUMBER(tPPRV.getVOUCHER_NBR());
		//憑證類別
		tbNomsGvInterfaceTemp.setGV_TYPE(tPPRV.getVOUCHER_TYPE());
		//發票日期
		tbNomsGvInterfaceTemp.setINVOICE_DATE(tPPRV.getVOUCHER_DATE());
		//資料所屬年月
		if(tPPRV.getVOUCHER_DATE()!=null){
		    Calendar cal = Calendar.getInstance();
			cal.setTime(tPPRV.getVOUCHER_DATE());
			int year = cal.get(Calendar.YEAR);
		    int mm = cal.get(Calendar.MONTH);
		    //資料所屬年
			tbNomsGvInterfaceTemp.setOCCURED_YEAR(String.valueOf(year));
			//資料所屬月
			tbNomsGvInterfaceTemp.setOCCURED_MONTH(String.valueOf(mm+1));
		}
		//銷售人統一編號
		tbNomsGvInterfaceTemp.setSALER_NO(tPPRV.getVAT_NUMBER());
		//銷售金額
		tbNomsGvInterfaceTemp.setSALES_AMT(tPPRV.getTAX_EXCLUSIVE_AMT().add(tPPRV.getBUSINESS_TAX()));
		//營業稅額
		tbNomsGvInterfaceTemp.setVAT_IO(tPPRV.getBUSINESS_TAX());
		//未稅金額
		tbNomsGvInterfaceTemp.setTAX_EXCLUSIVE_AMT(tPPRV.getTAX_EXCLUSIVE_AMT());
		//請款年月
		tbNomsGvInterfaceTemp.setREQ_YYYYMM(tbPayCashRequirement.getYEAR_MONTH());
		//請款單號
		tbNomsGvInterfaceTemp.setGROUP_ID(tbPayCashRequirement.getCASH_REQ_NO());
		//租約編號
		if(tbPayCashRequirement.getCASH_REQ_NO().startsWith("R") || tbPayCashRequirement.getCASH_REQ_NO().startsWith("E")){
			tbNomsGvInterfaceTemp.setLEASE_NO(list.get(i).getCONTRACT_NO());
		}
		//請款編號
		tbNomsGvInterfaceTemp.setPAYMENT_REQ_NO(list.get(i).getPAYMENT_REQ_NO());
		//請款細項
		tbNomsGvInterfaceTemp.setPAYMENT_REQ_ITEM_NO(list.get(i).getPAYMENT_REQ_ITEM_NO());
		//電錶號碼/專線編號
		if(tbPayCashRequirement.getCASH_REQ_NO().startsWith("E")){
			tbNomsGvInterfaceTemp.setELEC_METER_LINE_NBR(list.get(i).getELECTRICITY_METER_NBR());
		} else if(tbPayCashRequirement.getCASH_REQ_NO().startsWith("L")){
			tbNomsGvInterfaceTemp.setELEC_METER_LINE_NBR(list.get(i).getCONTRACT_NO());
		}
		//請款對象(證號)
		tbNomsGvInterfaceTemp.setPAYMENT_REQ_USER_ID(list.get(i).getPAYMENT_REQ_USER_ID());
		//申請人(代碼)
		tbNomsGvInterfaceTemp.setAPP_USER(tPPRV.getCR_USER());
		//申請日期
		tbNomsGvInterfaceTemp.setAPP_DATE(tPPRV.getCR_TIME());
		//建立日期
		tbNomsGvInterfaceTemp.setCREATION_DATE(payPublicUtilService.getToday());
		//憑證處理單號
		tbNomsGvInterfaceTemp.setVOUCHER_NO(tPPRV.getVOUCHER_NO());
				
		//insert
		tbNomsGvInterfaceTempMapper.insertSelective(tbNomsGvInterfaceTemp);
	}
	public void insertNomsVoidedCheckTemp(TbPayCashRequirement tbPayCashRequirement,List<TbPayCheckDisregard> list,int i) throws NomsException{
		TbNomsVoidedCheckTemp tbNomsVoidedCheckTemp = new TbNomsVoidedCheckTemp();
		//支票號碼
		tbNomsVoidedCheckTemp.setCHECK_NUMBER(list.get(i).getCHECK_NBR());
		//E:換票/D:作廢
		tbNomsVoidedCheckTemp.setCANCEL_REASON("D");
		//NOMS寫入日期
		tbNomsVoidedCheckTemp.setCANCEL_DATE(payPublicUtilService.getToday());
		//申請人
		tbNomsVoidedCheckTemp.setAPP_USER(list.get(i).getAPP_USER());
		//審核單位
		TbAuthPersonnelExample tbAuthPersonnelExample = new TbAuthPersonnelExample();
		com.foya.dao.mybatis.model.TbAuthPersonnelExample.Criteria criteria= tbAuthPersonnelExample.createCriteria();
		criteria.andPSN_NOEqualTo(tbPayCashRequirement.getACCOUNT_APPROVAL_USER());
		List<TbAuthPersonnel> listtAP = tbAuthPersonnelMapper.selectByExample(tbAuthPersonnelExample);
		tbNomsVoidedCheckTemp.setAPPROVAL_DEPT(listtAP.get(0).getDEPT_ID());
		//作廢作業批號
		tbNomsVoidedCheckTemp.setDISREGARD_NO(list.get(i).getDISREGARD_NO());
		
		//insert
		tbNomsVoidedCheckTempMapper.insertSelective(tbNomsVoidedCheckTemp);
	}
	
}
