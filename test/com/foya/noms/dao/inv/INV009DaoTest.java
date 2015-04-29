package com.foya.noms.dao.inv;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.dao.mybatis.model.TbInvSrl;
import com.foya.dao.mybatis.model.TbInvTr;
import com.foya.dao.mybatis.model.TbInvTrAct;
import com.foya.dao.mybatis.model.TbInvTxn;
import com.foya.noms.GenericTest;
import com.foya.noms.dto.inv.TbInvTrActCompleteDTO;
import com.foya.noms.service.inv.INV009Service;

public class INV009DaoTest extends GenericTest {

	@Autowired
	private INV009Dao inv009Dao;

	@Test
	public void testGetInvTrActDtoData1() {

//		List<TbInvTrActCompleteDTO> invTrActDtoList = inv009Dao
//				.getInvTrActDtoData("A1234", null, null, null);
//
//		assertFalse(sizeJudge(invTrActDtoList.size()));
//		assertNotNull(invTrActDtoList);
	}

	@Test
	public void testGetInvTrActDtoData2() {

		List<TbInvTrActCompleteDTO> invTrActDtoList = inv009Dao
				.getInvTrActDtoData("A1234", null, null, null, 0);

		assertFalse(sizeJudge(invTrActDtoList.size()));
		assertNotNull(invTrActDtoList);
	}

	@Test
	public void testUpdateStockByCallIn() {

//		boolean result = inv009Dao.updateStockByCallIn(1, "Matno", "12345543",
//				1, 0, 0);
//
//		assertFalse(result);
	}

	@Test
	public void testUpdateInvSrlByKey() {

		TbInvSrl tbInvSrl = new TbInvSrl();
		tbInvSrl.setSrl_no((long) 9999);
		tbInvSrl.setMat_no("MAT");
		tbInvSrl.setFa_no("H-1");
		tbInvSrl.setRcv_date(new Date());
		tbInvSrl.setMat_status((byte) 1);
		tbInvSrl.setCr_time(new Date());
		tbInvSrl.setCr_user("user");
		tbInvSrl.setMd_time(new Date());
		tbInvSrl.setMd_user("user");
		tbInvSrl.setTag_no("TAG");

		boolean result = inv009Dao.updateInvSrlByKey(tbInvSrl);

		assertFalse(result);
	}

	@Test
	public void testUpdateOnhandQty() {

		boolean result = inv009Dao.updateOnhandQty(1, new Date(), "userNo",
				"trNo", "matNo", (long) 0);

		assertFalse(result);
	}

	@Test
	public void testInsertInvTxn() {

		TbInvTxn invTxn = new TbInvTxn();
		invTxn.setTxn_type((byte) 1);
		invTxn.setTxn_no("H13");
		invTxn.setWh_id("12345");
		invTxn.setMat_no("M011");
		invTxn.setMat_status((byte) 1);
		invTxn.setQty(1);
		invTxn.setCr_time(new Date());
		invTxn.setCr_user("userNo");

		boolean result = inv009Dao.insertInvTxn(invTxn);

		assertTrue(result);

	}

	@Test
	public void testInsertInvTrAct() {

		TbInvTrAct invTrAct = new TbInvTrAct();
//		invTrAct.setTr_no("TRA12345");
//		invTrAct.setMat_no("MatNo");
//		invTrAct.setTr_qty(1);
//		invTrAct.setCr_time(new Date());
//		invTrAct.setCr_user("User");
//		invTrAct.setMd_time(new Date());
//		invTrAct.setMd_user("User");
		
		boolean result = inv009Dao.insertInvTrAct(invTrAct);
		
		assertTrue(result);
	}

	@Test
	public void testUpdateTbInvTrByKey() {
		
		TbInvTr invTr = new TbInvTr();
		invTr.setTr_no("H456");
		
		boolean result = inv009Dao.updateTbInvTrByKey(invTr);
		
		assertFalse(result);
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
