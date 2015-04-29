package com.foya.noms.dao.pay;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.foya.dao.mybatis.model.TbPayPaymentRequestDtl;
import com.foya.dao.mybatis.model.TbPayVoucherCredit;
import com.foya.noms.GenericTest;
import com.foya.noms.dto.pay.TbPayPaymentRequestDtlDTO;
import com.foya.noms.dto.pay.TbPayPaymentRequestVoucherDTO;

public class Pay009DaoTest  extends GenericTest{
	@Inject
	private PAY009Dao pay009Dao;
	
	private String domain = "M";
	private String voucherNbr = "11112222";
	private String voucherNo = "33335555";
	private String prType = "WWW";
	private String locationId = "9999999999";
	private Long mstSeqNbr = 222L;
	private short itemNo = 9;
	private String cashReqNo = "111";
	private String contractNo = "7777";
	private String voucherType ="Z";
	private static List<Long> mstSeqNbrList = new ArrayList<Long>();
	private static List<Long> seqNbrList = new ArrayList<Long>();
	
	private Long paymentReqNo = (long) 333;
	private String userName = "unitTest";
	private Date today = new Date();
	private String yyyyMMdd = new SimpleDateFormat("yyyyMMdd").format(today);
	private String yearMonth = new SimpleDateFormat("yyyyMM").format(today);

	private String lessorId="777";
	
	@BeforeClass
	public static void beforeClass() throws Exception {
		mstSeqNbrList.add(111L);
		mstSeqNbrList.add(222L);
		seqNbrList.add(333L);
		seqNbrList.add(444L);
	}		

	@Test
	public void testSelectPaymentAmount(){
		HashMap<String,Object> dataObj = new HashMap<String,Object>();
		dataObj.put("domain", domain);
		dataObj.put("voucherNbr", "%"+voucherNbr+"%");
		dataObj.put("lessorId", lessorId);
		dataObj.put("yearMonth", yearMonth);
		dataObj.put("locationId", "%"+locationId+"%");
		List<TbPayPaymentRequestVoucherDTO> list = pay009Dao.selectPaymentAmount(dataObj);
		Assert.assertEquals(0, list.size());
	}
	@Test
	public void testSelectPayPaymentRequestVoucherByVoucherNo(){
		List<TbPayPaymentRequestVoucherDTO> list = pay009Dao.selectPayPaymentRequestVoucherByVoucherNo(voucherNo);
		Assert.assertEquals(0, list.size());
	}
	@Test
	public void testSelectPayPaymentRequestDtlLocationId(){
		List<TbPayPaymentRequestDtl> list = pay009Dao.selectPayPaymentRequestDtlLocationId();
		Assert.assertNotNull(list);
	}
	@Test
	public void testSelectPayPaymentRequestDtlTaxSumAmt(){
		HashMap<String,Object> dataObj = new HashMap<String,Object>();
		dataObj.put("prType", "%"+prType+"%");
		dataObj.put("locationId", locationId);
		List<TbPayPaymentRequestDtlDTO> list = pay009Dao.selectPayPaymentRequestDtlTaxSumAmt(dataObj);
		Assert.assertEquals(1, list.size());
	}
	@Test
	public void testSelect4Pay009Detail2(){
		HashMap<String,Object> dataObj = new HashMap<String,Object>();
		dataObj.put("mstSeqNbr", mstSeqNbr);
		List<TbPayPaymentRequestDtlDTO> list = pay009Dao.select4Pay009Detail2(dataObj);
		Assert.assertEquals(0, list.size());
	}
	@Test
	public void testSelect4Pay009Detail3(){
		HashMap<String,Object> dataObj = new HashMap<String,Object>();
		dataObj.put("mstSeqNbr", mstSeqNbr);	
		dataObj.put("eqPrType", prType);
		dataObj.put("locationId", locationId);
		dataObj.put("prType", "%"+prType+"%");	
		List<TbPayPaymentRequestDtlDTO> list = pay009Dao.select4Pay009Detail3(dataObj);
		Assert.assertEquals(0, list.size());
	}
	@Test
	public void testCallPayPcGetSeqnoByMap(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("PI_PREFIX", "VOU");
		map.put("PI_APP_DATE", yyyyMMdd);
		pay009Dao.callPayPcGetSeqnoByMap(map);
		if (!map.get("PO_ERR_CDE").equals("00")) {
			Assert.assertTrue(false);
		}else{
			Assert.assertTrue(true);
		}
	}
	@Test
	public void testInsertPaymentRequestVoucher(){
		TbPayPaymentRequestVoucherDTO record = new TbPayPaymentRequestVoucherDTO();
		record.setCR_TIME(today);
		record.setCR_USER(userName);
		record.setMD_TIME(today);
		record.setMD_USER(userName);
		
		record.setPROCESS_TYPE("VOU");
		record.setVOUCHER_NO(voucherNbr);
		record.setCREDIT_MADE_AMT(BigDecimal.ZERO);
		record.setCREDIT_MADE_TAX(BigDecimal.ZERO);
		record.setVOUCHER_TYPE(voucherType);
		record.setVOUCHER_NBR(voucherNbr);
		record.setVOUCHER_DATE(today);
		record.setVAT_NUMBER("0");
		record.setTAX_EXCLUSIVE_AMT(BigDecimal.ZERO);
		record.setBUSINESS_TAX(BigDecimal.ZERO);
		int i = pay009Dao.insertPaymentRequestVoucher(record);
		
		Assert.assertEquals(1, i);
	}
	@Test
	public void testInsertVoucherCreditTaxFromFn(){
		TbPayVoucherCredit record = new TbPayVoucherCredit();
		record .setCR_TIME(today);
		record.setCR_USER(userName);
		record.setMD_TIME(today);
		record.setMD_USER(userName);
		
		record.setMST_SEQ_NBR(mstSeqNbr);
		record.setPAYMENT_REQ_NO(paymentReqNo);
		record.setPAYMENT_REQ_ITEM_NO(itemNo);
		record.setCASH_REQ_AP_NO(cashReqNo);
		record.setCONTRACT_PO_NO(contractNo);
		record.setCREDIT_AMT(BigDecimal.ZERO);
		record.setCREDIT_DATE(today);
		record.setPR_TYPE(prType);
		record.setLOCATION_ID(locationId);
		record.setPAYMENT_REQ_USER_ID(userName);
		int i = pay009Dao.insertVoucherCreditTaxFromFn(record);
		Assert.assertEquals(1, i);
	}
	@Test
	public void testUpdateVoucherCreditPlusTaxByRecord(){
		TbPayVoucherCredit record = new TbPayVoucherCredit();
		record .setCR_TIME(today);
		record.setCR_USER(userName);
		record.setMD_TIME(today);
		record.setMD_USER(userName);
		
		record.setMST_SEQ_NBR(mstSeqNbr);
		record.setPAYMENT_REQ_NO(paymentReqNo);
		record.setPAYMENT_REQ_ITEM_NO(itemNo);
		record.setCASH_REQ_AP_NO(cashReqNo);
		record.setCONTRACT_PO_NO(contractNo);
		record.setCREDIT_AMT(BigDecimal.ZERO);
		record.setCREDIT_DATE(today);
		record.setPR_TYPE(prType);
		record.setLOCATION_ID(locationId);
		record.setPAYMENT_REQ_USER_ID(userName);
		int i = pay009Dao.updateVoucherCreditPlusTaxByRecord(record );
		Assert.assertEquals(0, i);
	}
	@Test
	public void testCountVoucherCredit(){
		int i = pay009Dao.countVoucherCredit(mstSeqNbr, paymentReqNo, itemNo);
		Assert.assertEquals(0, i);
	}
	@Test
	public void testDeleteVoucherCreditByMstSeqNbr(){
		int i = pay009Dao.deleteVoucherCreditByMstSeqNbr(mstSeqNbrList);
		Assert.assertEquals(0, i);
	}
	@Test
	public void testDeleteVoucherCreditByPrTypeLocationId(){
		int i = pay009Dao.deleteVoucherCreditByPrTypeLocationId(mstSeqNbr, prType, locationId);
		Assert.assertEquals(0, i);
	}
	@Test
	public void testDeletePaymentRequestVoucherBySeqNbr(){	
		int i = pay009Dao.deletePaymentRequestVoucherBySeqNbr(seqNbrList);
		Assert.assertEquals(0, i);
	}
	@Test
	public void testUpdateCreditMadeAmtTaxFromVoucherCredit(){	
		TbPayPaymentRequestVoucherDTO record = new TbPayPaymentRequestVoucherDTO();
		record.setCR_TIME(today);
		record.setCR_USER(userName);
		record.setMD_TIME(today);
		record.setMD_USER(userName);
		
		record.setPROCESS_TYPE("VOU");
		record.setVOUCHER_NO(voucherNbr);
		record.setCREDIT_MADE_AMT(BigDecimal.ZERO);
		record.setCREDIT_MADE_TAX(BigDecimal.ZERO);
		int i = pay009Dao.updateCreditMadeAmtTaxFromVoucherCredit(record);
		Assert.assertEquals(0, i);
	}
}
