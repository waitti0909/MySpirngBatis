package com.foya.noms.dao.pay;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;

import com.foya.dao.mybatis.model.TbPayCheckDisregard;
import com.foya.dao.mybatis.model.TbPayCheckDisregardKey;
import com.foya.dao.mybatis.model.TbPayPayment;
import com.foya.dao.mybatis.model.TbPayPaymentRequestDtl;
import com.foya.noms.GenericTest;
import com.foya.noms.dto.pay.TbPayCheckDisregardDTO;

public class Pay010DaoTest  extends GenericTest{
//	@Inject
//	private Pay010Dao pay010Dao;
//	@Test
//	public void testSelectPyamentByExample() {
//		List<TbPayPayment>  list=pay010Dao.selectPyamentByExample();
//		Assert.assertEquals(0, list.size());
//	}
//
//	@Test
//	public void testSelectPyamentByExampleCheckNbr() {
//		List<TbPayPayment>  list=pay010Dao.selectPyamentByExampleCheckNbr("333");
//		Assert.assertEquals(0, list.size());
//	}
//
//	@Test
//	public void testSelectPyamentByExampleSeq() {
//		TbPayPayment pay=pay010Dao.selectPyamentByExampleSeq(new Long(2));
//		Assert.assertNull(pay);
//	}
//
//	@Test
//	public void testGetPayCheckDisregardNo() {
//		List<TbPayCheckDisregard> list= pay010Dao.getPayCheckDisregardNo("2");
//		Assert.assertEquals(0, list.size());
//	}
//
//	@Test
//	public void testGetPayCheckDisregardNoEqual() {
//		 List<TbPayCheckDisregard> list=pay010Dao.getPayCheckDisregardNoEqual("22");
//		 Assert.assertEquals(0, list.size());
//	}
//
//	@Test
//	public void testInsertPayCheckDisregard() {
//		TbPayCheckDisregard exportObject = new TbPayCheckDisregard();
//		exportObject.setDISREGARD_NO("22");
//		exportObject.setCHECK_NBR("33");
//		exportObject.setPAYMENT_REQ_ITEM_NO(new Short("2"));
//		exportObject.setPAYMENT_REQ_NO(new Long("2"));
//		exportObject.setPAYMENT_SEQ_NBR(new Long("3"));
//		exportObject.setDOMAIN("3");
//		exportObject.setAPP_DATE(new Date());
//		exportObject.setAPP_USER("3");
//		exportObject.setSTATUS("3");
//		exportObject.setCHECK_AMT(BigDecimal.valueOf(0.00));
//		exportObject.setCR_USER("3");
//		exportObject.setCR_TIME(new Date());
//		exportObject.setMD_USER("3");
//		exportObject.setMD_TIME(new Date());
//		int i=pay010Dao.insertPayCheckDisregard(exportObject);
//		Assert.assertEquals(0,i);
//	}
//
//	@Test
//	public void testSelectTbPayCheckDisregard() {
//		Set<String> statusList = new HashSet<String>();
//		statusList.add("G");
//		HashMap<String,Object> dataObj = new HashMap<String,Object>();
//		dataObj.put("domain", "3");
//		dataObj.put("disregardNo", "4");
//		dataObj.put("appStartDate", new Date());
//		dataObj.put("appEndDate",new Date());
//		dataObj.put("crStartDate", new Date());
//		dataObj.put("crEndDate", new Date());
//		dataObj.put("status", statusList);
//		List<TbPayCheckDisregardDTO> list=pay010Dao.selectTbPayCheckDisregard(dataObj);
//	Assert.assertEquals(0, list.size());
//	}
//
//	@Test
//	public void testSelectPyamentRequsetBypk() {
//		TbPayPaymentRequestDtl j=pay010Dao.selectPyamentRequsetDtlBypk(new Long(2), new Short("3"));
//		Assert.assertNull(j);
//	}
//
//	@Test
//	public void testSelectPyamentByExampleReqNo() {
//		List<TbPayPayment>  list =pay010Dao.selectPyamentByExampleReqNo(new Long(2), new Short("3"));
//		Assert.assertEquals(0, list.size());
//	}
//
//	@Test
//	public void testSelectPayCheckDisregardBypk() {
//		TbPayCheckDisregardDTO d = pay010Dao.selectPayCheckDisregardBypk("3");
//		Assert.assertNull(d);
//	}
//
//	@Test
//	public void testSelectPyamentRequsetDtlBypk() {
//		TbPayPaymentRequestDtl d =pay010Dao.selectPyamentRequsetDtlBypk(new Long(2), new Short("3"));
//		Assert.assertNull(d);
//	}
//
//	@Test
//	public void testUpdatePayCheckDisregard() {
//		TbPayCheckDisregard exportObject = new TbPayCheckDisregard();
//		exportObject.setDISREGARD_NO("3");
//		exportObject.setCHECK_NBR("3");
//		int i=pay010Dao.updatePayCheckDisregard(exportObject);
//		Assert.assertEquals(0,i);
//	}
//
//	@Test
//	public void testDeletePayCheckDisregard() {
//		TbPayCheckDisregardKey key = new TbPayCheckDisregardKey();
//		key.setCHECK_NBR("33333");
//		key.setDISREGARD_NO("3333");
//		int i=pay010Dao.deletePayCheckDisregard(key);
//		Assert.assertEquals(0,i);
//	}

}
