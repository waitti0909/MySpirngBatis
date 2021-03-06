package com.foya.noms.dao.inv;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.dao.mybatis.model.TbInvOnhand;
import com.foya.dao.mybatis.model.TbInvSrl;
import com.foya.dao.mybatis.model.TbInvTr;
import com.foya.dao.mybatis.model.TbInvTrAct;
import com.foya.dao.mybatis.model.TbInvTxn;
import com.foya.noms.GenericTest;
import com.foya.noms.dto.inv.TbInvTrActCompleteDTO;
import com.foya.noms.dto.inv.TbInvTrCompleteDTO;
import com.foya.noms.dto.inv.TbInvTrDtlCompleteDTO;
import com.foya.noms.enums.InvEnumCommon;

public class INV008DaoTest extends GenericTest{

	// -------------------------------------------------
	@Autowired
	private INV008Dao inv008Dao;

	// -------------------------------------------------

	@Test
	public void testGetInvTrDataDtoByDate() {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("userNo", "JunitTest");
		map.put("strDemandDate", null);
		map.put("endDemandDate", null);
		map.put("applyNumber", null);
		map.put("outDepot", null);
		map.put("inDepot", null);
		map.put("demandDept", null);
		map.put("applyDept", null);
		map.put("applicant", null);
		map.put("approved", true);
		map.put("partExit", true);

		List<TbInvTrCompleteDTO> invTrCompleteDtoList = inv008Dao
				.getInvTrDataDtoByDate(map);

		assertFalse(sizeJudge(invTrCompleteDtoList.size()));
		assertNotNull(invTrCompleteDtoList);

	}

	@Test
	public void testGetInvTrDtlDTo() {

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("trNo", "JunitText");
		map.put("matNo", null);
		map.put("matStatus", null);

//		List<TbInvTrDtlCompleteDTO> TnvTrCompleteDto = inv008Dao
//				.getInvTrDtlDTo("JunitText",null,null);
//
//		assertNotNull(TnvTrCompleteDto);
//		assertFalse(sizeJudge(TnvTrCompleteDto.size()));
	}

	@Test
	public void testGetInvTrActDtoData() {

		List<TbInvTrActCompleteDTO> invTrCompleteDtoList = inv008Dao
				.getInvTrActDtoData("Junit", null, 0, null, 0);

		assertNotNull(invTrCompleteDtoList);
		assertFalse(sizeJudge(invTrCompleteDtoList.size()));

	}

	@Test
	public void testGetBookingNumber() {

		int value = inv008Dao.getBookingNumber("MatNo", "00000");

		assertEquals(0, value);

	}

	@Test
	public void testUpdateTbInvTrByKey() {

		TbInvTr invTr = new TbInvTr();

//		boolean results = inv008Dao.updateTbInvTrByKey(invTr);

//		assertFalse(results);
	}

	@Test
	public void testUpdateBookingQty() {

		boolean results = inv008Dao.updateBookingQty("0", "0", "0", "0", 0);

		assertFalse(results);
	}

	@Test
	public void testUpdateStockByExport() {

//		boolean results = inv008Dao.updateStockByExport(1, "MatNo", "12342", 5, 0,
//				0, 0);
//		
//		assertFalse(results);
	}

	@Test
	public void testGetTbInvSrlList() {

		List<TbInvSrl> invSrlList =inv008Dao.getTbInvSrlList("MatNo", "12345", (byte)1);
		
		assertNotNull(invSrlList);
		assertFalse(sizeJudge(invSrlList.size()));
	}

	@Test
	public void testGetTbInvSrl() {

		TbInvSrl srl= inv008Dao.getTbInvSrl("M00", "M099", (long)999);
		
		assertNull(srl);
		
	}

	@Test
	public void testInsertInvTxn() {

		TbInvTxn tInvTxn = new TbInvTxn();
		tInvTxn.setTxn_type((byte) 2);
		tInvTxn.setTxn_no("1234");
		tInvTxn.setWh_id("1235");
		tInvTxn.setMat_no("M001");
		tInvTxn.setMat_status((byte) 1);
		tInvTxn.setQty(1);
		tInvTxn.setCr_time(new Date());
		tInvTxn.setCr_user("Test");

		int results = inv008Dao.insertInvTxn(tInvTxn);
		assertEquals(1, results);
		
	}

	@Test
	public void testInsertTrAct() {
		
		TbInvTrAct invTrAct = new TbInvTrAct();
//		invTrAct.setTr_no("123");
//		invTrAct.setMat_no("M001");
//		invTrAct.setTr_qty(5);
//		invTrAct.setCr_user("User");
//		invTrAct.setCr_time(new Date());
//		invTrAct.setMd_user("User");
//		invTrAct.setMd_time(new Date());

		int results = inv008Dao.insertTrAct(invTrAct);
		assertEquals(1, results);
	}

	@Test
	public void testGetInvTrAct() {

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("trNo", "JunitTest");
		map.put("matNo", null);
		map.put("trIn", null);
		map.put("srlNo", null);
		map.put("trDtlNo", 0);
		
//		List<TbInvTrActCompleteDTO> trActDtoList = inv008Dao.getInvTrAct(map);
//
//		assertNotNull(trActDtoList);
//		assertFalse(sizeJudge(trActDtoList.size()));
	}

	@Test
	public void testInsertOnHand() {

		TbInvOnhand invOnhand = new TbInvOnhand();
		invOnhand.setTxn_type((byte) InvEnumCommon.OnHandStatus.EXIT.getCode());
		invOnhand.setTxn_no("12333");
		invOnhand.setWh_id("11111");
		invOnhand.setMat_no("MatNo");
		invOnhand.setMat_status((byte)1);
		invOnhand.setQty(2);
		invOnhand.setOnhand_qty(2);
		invOnhand.setTr_dtl_no((long)2);
		invOnhand.setCr_time(new Date());
		invOnhand.setCr_user("user");
		invOnhand.setMd_time(new Date());
		invOnhand.setMd_user("user");

		inv008Dao.insertOnHand(invOnhand);
		
		invOnhand.setFa_no("FaNo");
		invOnhand.setSrl_no((long)99999);
		
		inv008Dao.insertOnHand(invOnhand);
	}

	@Ignore
	public boolean sizeJudge(int size) {

		boolean judge = false;

		if (size > 0) {
			judge = true;
		}

		return judge;
	}
}
