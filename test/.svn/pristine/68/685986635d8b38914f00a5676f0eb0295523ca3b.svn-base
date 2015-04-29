package com.foya.noms.dao.pay;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.foya.dao.mybatis.model.TbLsLocation;
import com.foya.dao.mybatis.model.TbPayCashRequirement;
import com.foya.dao.mybatis.model.TbPayCashRequirementExample;
import com.foya.dao.mybatis.model.TbPayPayment;
import com.foya.dao.mybatis.model.TbPayPaymentRequest;
import com.foya.dao.mybatis.model.TbPayPaymentRequestDtl;
import com.foya.noms.GenericTest;
import com.foya.noms.dto.pay.TbPayCashRequirementDTO;
import com.foya.noms.dto.pay.TbPayPaymentDTO;
import com.foya.noms.dto.pay.TbPayPaymentRequestDTO;
import com.foya.noms.dto.pay.TbPayPaymentRequestDtlDTO;

public class Pay001DaoTest  extends GenericTest{
	@Inject
	private PAY001Dao pay001Dao;
	
	private String[] status ={"C","D","E","F","G"};
	private static String domain = "WWW";
	private static String processType = "_REN01_";
	private String processTypeDesc = "測試";
	private String paymentPeriod = "01";
	private String cashReqNo = "010";
	private String locationId = "999999999";
	private String contractNo = "222";
	private Long paymentReqNo = (long) 333;
	private String lsNo = "222";
	private String userName = "unitTest";
	private static Date today = new Date();
	private static String yearMonth = new SimpleDateFormat("yyyyMM").format(today);

	private String recipientId="666";
	private String lessorId="777";
	private String payItem="888";
	
	private static List<Long> paymentReqNoList = new ArrayList<Long>();
	private static TbPayCashRequirementExample tbPayCashRequirementExample = new TbPayCashRequirementExample();

	@BeforeClass
	public static void beforeClass() throws Exception {
		paymentReqNoList.add(111L);
		paymentReqNoList.add(222L);
		
		tbPayCashRequirementExample.createCriteria().andDOMAINEqualTo(domain)
		.andPROCESS_TYPEEqualTo(processType)
		.andYEAR_MONTHEqualTo(yearMonth);
	}	
	
	@Test
	public void testSelectByExample() {
		HashMap<String,Object> dataObj = new HashMap<String,Object>();
		dataObj.put("domain", domain);
		dataObj.put("processType", processType);
		dataObj.put("paymentPeriod", paymentPeriod);
		dataObj.put("cashReqNo", cashReqNo);
		dataObj.put("appDateStart", today);
		dataObj.put("appDateEnd", today);
		dataObj.put("status", status);			
		List<TbPayCashRequirementDTO> list=pay001Dao.selectByExample(dataObj);
		Assert.assertEquals(0, list.size());
	}
	@Test
	public void testSelectByPrimaryKey() {	
		TbPayCashRequirement list=pay001Dao.selectByPrimaryKey(cashReqNo);
		Assert.assertNull(list);
	}	
	@Test
	public void testSelectDetail1() {
		HashMap<String,Object> dataObj = new HashMap<String,Object>();
		dataObj.put("cashReqNo", cashReqNo);
		dataObj.put("processTypeDesc", processTypeDesc);	
		List<TbPayPaymentRequestDTO> list=pay001Dao.selectDetail1(dataObj);
		Assert.assertEquals(0, list.size());
	}
	@Test
	public void testSelectDetail2() {
		HashMap<String,Object> dataObj = new HashMap<String,Object>();
		dataObj.put("contractNo", contractNo);
		dataObj.put("paymentReqNo", paymentReqNo);
		List<TbPayPaymentRequestDtlDTO> list=pay001Dao.selectDetail2(dataObj);
		Assert.assertEquals(0, list.size());
	}
	@Test
	public void testSelectDetail3() {
		HashMap<String,Object> dataObj = new HashMap<String,Object>();
		dataObj.put("locationId", locationId);
		dataObj.put("contractNo", contractNo);
		List<TbPayPaymentDTO> list=pay001Dao.selectDetail3(dataObj);
		Assert.assertEquals(0, list.size());
	}
	
	@Test
	public void testDeletePayPaymentRequestDtl() {
		int i=pay001Dao.deletePayPaymentRequestDtl(paymentReqNoList);
		Assert.assertEquals(0,i);
	}
	@Test
	public void testDeletePayPaymentRequest() {
		int i=pay001Dao.deletePayPaymentRequest(paymentReqNoList);
		Assert.assertEquals(0,i);
	}	
	@Test
	public void testDeletePayPayment() {
		int i=pay001Dao.deletePayPayment(paymentReqNoList);
		Assert.assertEquals(0,i);
	}	
	
	@Test
	public void testDeletePayCashRequirement() {
		int i=pay001Dao.deletePayCashRequirement(cashReqNo);
		Assert.assertEquals(0,i);
	}
	
	@Test
	public void testGetPayPaymentRequestCnt() {
		int i=pay001Dao.getPayPaymentRequestCnt(cashReqNo);
		Assert.assertEquals(0,i);
	}	
	
	@Test
	public void testApproved() {
		TbPayCashRequirement record = new TbPayCashRequirement();
		record.setSTATUS("B");
		record.setMD_USER(userName);
		record.setMD_TIME(today);  
		int i=pay001Dao.approved(cashReqNo, record);
		Assert.assertEquals(0,i);
	}	
	@Test
	public void testCountPayCashRequirement() {
		int i=pay001Dao.countPayCashRequirement(tbPayCashRequirementExample);
		Assert.assertEquals(0,i);
	}
	@Test
	public void testSelectDetail1ByREN01() {
		HashMap<String,Object> dataObj = new HashMap<String,Object>();
		dataObj.put("domain", domain);
		dataObj.put("paymentPeriod", paymentPeriod);
		dataObj.put("processTypeDesc", processTypeDesc);
		List<TbPayPaymentRequestDTO> list=pay001Dao.selectDetail1ByREN01(dataObj);
		Assert.assertEquals(0, list.size());
	}
	@Test
	public void testSelectDetail1ByREN02() {
		HashMap<String,Object> dataObj = new HashMap<String,Object>();
		dataObj.put("domain", domain);
		dataObj.put("paymentPeriod", paymentPeriod);
		dataObj.put("processTypeDesc", processTypeDesc);
		List<TbPayPaymentRequestDTO> list=pay001Dao.selectDetail1ByREN02(dataObj);
		Assert.assertEquals(0, list.size());
	}
	@Test
	public void testSelectDetail1ByREN03() {
		HashMap<String,Object> dataObj = new HashMap<String,Object>();
		dataObj.put("domain", domain);
		dataObj.put("paymentPeriod", paymentPeriod);
		dataObj.put("processTypeDesc", processTypeDesc);
		List<TbPayPaymentRequestDTO> list=pay001Dao.selectDetail1ByREN03(dataObj);
		Assert.assertEquals(0, list.size());
	}
	@Test
	public void testSelectDetail1ByREN04() {
		HashMap<String,Object> dataObj = new HashMap<String,Object>();
		dataObj.put("domain", domain);
		dataObj.put("paymentPeriod", paymentPeriod);
		dataObj.put("processTypeDesc", processTypeDesc);
		List<TbPayPaymentRequestDTO> list=pay001Dao.selectDetail1ByREN04(dataObj);
		Assert.assertEquals(0, list.size());
	}
	@Test
	public void testGetPayAmt4Detail3(){
		BigDecimal value = pay001Dao.getPayAmt4Detail3(lsNo, locationId, recipientId, lessorId, payItem);
		Assert.assertEquals(BigDecimal.ZERO, value);
	}
	@Test
	public void testGetDistinctLocationId(){
		List<TbLsLocation> list = pay001Dao.getDistinctLocationId(lsNo);
		Assert.assertEquals(0, list.size());
	}
	@Test
	public void testPayPcGetSeqnoByMap(){
		HashMap<String,Object> map = new HashMap<String, Object>();
		map.put("PI_PREFIX", "REP");
		map.put("PI_APP_DATE", new SimpleDateFormat("yyyyMMdd").format(today));
		pay001Dao.payPcGetSeqnoByMap(map);
		if (!map.get("PO_ERR_CDE").equals("00")) {
			Assert.assertTrue(false);
		}else{
			Assert.assertTrue(true);
		}
	}
	@Test
	public void testInsertPayCashRequirement(){
		TbPayCashRequirementDTO record = new TbPayCashRequirementDTO();
		record.setCR_TIME(today);
		record.setCR_USER(userName);
		record.setMD_TIME(today);
		record.setMD_USER(userName);
		
		record.setDOMAIN(domain);
		record.setPROCESS_TYPE(processType);
		record.setAPP_USER(userName);
		record.setAPP_DATE(today);
		record.setYEAR_MONTH(yearMonth);
		record.setPAYMENT_PERIOD((byte) 1);
		record.setSTATUS(status[0]);
		record.setCASH_REQ_NO(cashReqNo);
		int i = pay001Dao.insertPayCashRequirement(record);
		Assert.assertEquals(1,i);
	}
	@Test
	public void testInsertPayPaymentRequest(){
		TbPayPaymentRequest record = new TbPayPaymentRequest();
		record.setCR_TIME(today);
		record.setCR_USER(userName);
		record.setMD_TIME(today);
		record.setMD_USER(userName);
		
		record.setPAYMENT_REQ_BEGIN_DATE(today);
		record.setPAYMENT_REQ_END_DATE(today);
		record.setCONTRACT_NO(contractNo);
		record.setPAYMENT_MONTHS((byte) 1);
		record.setCASH_REQ_NO(cashReqNo);
		int i = pay001Dao.insertPayPaymentRequest(record);
		Assert.assertEquals(1,i);		
	}
	@Test
	public void testUpdatePayContractInfo(){
		int i = pay001Dao.updatePayContractInfo(yearMonth, domain, contractNo);
		Assert.assertEquals(0,i);
	}
	@Test
	public void testInsertPayPaymentRequestDtl(){
		TbPayPaymentRequestDtl record = new TbPayPaymentRequestDtl();
		record.setCR_TIME(today);
		record.setCR_USER(userName);
		record.setMD_TIME(today);
		record.setMD_USER(userName);
		
		record.setITEM_NO((short) 1);
		record.setPAYMENT_REQUEST_ITEM("1");
		record.setLOCATION_ID(locationId);
		record.setTAX_EXCLUSIVE_AMT(BigDecimal.ZERO);
		record.setBUSINESS_TAX(BigDecimal.ZERO);
		record.setPAYMENT_REQ_BEGIN_DATE(today);
		record.setPAYMENT_REQ_END_DATE(today);
		record.setPAYMENT_REQ_NO(paymentReqNo);
		int i = pay001Dao.insertPayPaymentRequestDtl(record);
		Assert.assertEquals(1,i);
	}
	@Test
	public void testUpdatePayLocationInfo(){
		int i = pay001Dao.updatePayLocationInfo(today, today, cashReqNo, domain, contractNo, locationId);
		Assert.assertEquals(0,i);
	}
	@Test
	public void testInsertPayPayment(){
		TbPayPayment record = new TbPayPayment();
		record.setCR_TIME(today);
		record.setCR_USER(userName);
		record.setMD_TIME(today);
		record.setMD_USER(userName);
		record.setPAYMENT_REQ_ITEM_NO((short) 1);
		record.setLOCATION_ID(locationId);
		record.setPAYMENT_REQ_USER_ID(userName);
		record.setPAYMENT_USER_ID(userName);
		record.setPAYMENT_USER_NAME(userName);
		record.setPAYMENT_METHOD("M");
		record.setBANK_CODE("111");
		record.setBANK_BRANCH_CODE("222");
		record.setACCOUNT_NBR("111");
		record.setTAX_EXCLUSIVE_TOTAL_AMT(BigDecimal.ZERO);
		record.setTOTAL_BUSINESS_TAX(BigDecimal.ZERO);
		record.setTOTAL_INCOME_TAX(BigDecimal.ZERO);
		record.setTOTAL_NHI_AMT(BigDecimal.ZERO);
		record.setSTATUS(status[0]);
		record.setPAYMENT_REQ_NO(paymentReqNo);
		int i = pay001Dao.insertPayPayment(record);
		Assert.assertEquals(1,i);	
	}
}
