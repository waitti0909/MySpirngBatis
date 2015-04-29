package com.foya.noms.dao.inv;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;

import com.foya.dao.mybatis.model.TbInvMrD;
import com.foya.dao.mybatis.model.TbInvTxn;
import com.foya.noms.GenericTest;
import com.foya.noms.dto.inv.TbInvMaterialOptDTO;
import com.foya.noms.dto.inv.TbInvRtDDTO;
import com.foya.noms.dto.inv.TbInvTxnDTO;

public class INV015DaoTest  extends GenericTest {
	@Inject
	private INV015Dao inv015Dao;

	@Test
	public void testSearch() {
		HashMap<String, Object> dataObj = new HashMap<String, Object>();
		dataObj.put("wareHouse", "333");
		dataObj.put("status", "333");
		dataObj.put("optId", "333");
		dataObj.put("siteId", "333");
		dataObj.put("deptApply", "333");
		dataObj.put("personnel", "333");
		List<TbInvMaterialOptDTO> list = inv015Dao.search(dataObj);
		Assert.assertEquals(0, list.size());
	}

	@Test
	public void testSearchUTbInvRtD() {
		HashMap<String, Object> dataObj = new HashMap<String, Object>();
		dataObj.put("optId", "33333");
		dataObj.put("whId", "3333");
		List<TbInvRtDDTO> list = inv015Dao.searchUTbInvRtD(dataObj);
		Assert.assertEquals(0, list.size());
	}

	@Test
	public void testSearchTxnRTSumQty() {
		int test=inv015Dao.searchTxnRTSumQty("00004", "M030", "1");
		Assert.assertNotNull(test);
	}

	@Test
	public void testSearchFs() {
		HashMap<String, Object> dataObj = new HashMap<String, Object>();
		List<TbInvRtDDTO> list = inv015Dao.searchFs(dataObj);
		Assert.assertNotNull(list);
	}

	@Test
	public void testSearchTbInvTxnRTDetail() {
		HashMap<String,Object> dataObj = new HashMap<String,Object>();
		dataObj.put("optId", "2");
		dataObj.put("matNo", "2");
		dataObj.put("dtlSeq", "2");
		List<TbInvTxnDTO> list = inv015Dao.searchTbInvTxnRTDetail(dataObj);
		Assert.assertEquals(0, list.size());
	}

	@Test
	public void testSearchTbInvTxnRTDetailNonPage() {
		List<TbInvTxnDTO> list = inv015Dao.searchInvTxnPrintPageDetail("3");
		Assert.assertEquals(0, list.size());
	}

	@Test
	public void testSearchTbInvMrDMatNo() {
		List<TbInvMrD> list = inv015Dao.searchTbInvMrDMatNo("4352345");
		Assert.assertEquals(0, list.size());
	}

	@Test
	public void testUpdateTbInvBooking() {		
		int test = inv015Dao.updateTbInvBooking("rweqrw", "q",
				"32414");
		Assert.assertEquals(0, test);
	}

	@Test
	public void testUpdateTbInvInv() {
		int test = inv015Dao.updateTbInvInv(1,"2", "32414");
		Assert.assertEquals(0,test);
	}

	@Test
	public void testSearchTbInvTxn() {
		List<TbInvTxn> list = inv015Dao.searchTbInvTxn("", "", "", new Date(), new Date(), "1", "");
		Assert.assertEquals(0, list.size());
	}

	@Test
	public void testSearchInvTxnPrintPageDetail() {
		List<TbInvTxnDTO> list = inv015Dao
				.searchInvTxnPrintPageDetail("32f4f34");
		Assert.assertEquals(0, list.size());
	}

	@Test
	public void testSelectMaxSeq() {
		TbInvTxnDTO dto=inv015Dao.selectMaxSeq("");
		Assert.assertNotNull(dto);
	}

	@Test
	public void testUpdateInvInv() {
		HashMap<String, Object> dataObj = new HashMap<String, Object>();
		dataObj.put("matNo", "3241234");
		dataObj.put("whId", "42134");
		dataObj.put("wrdQty", 1);
		int test = inv015Dao.updateInvInv(dataObj);
		Assert.assertEquals(0, test);
	}

	@Test
	public void testInsertInvTxn() {
		TbInvTxn record = new TbInvTxn();
		record.setTxn_no("542532534");
		record.setTxn_type(new Byte("2"));
		record.setRef_act_no("542534");
		record.setWh_id("5345235");
		record.setMat_no("345235");
		record.setMat_status(new Byte("2"));
		record.setRn_resn("423134");
		record.setMro_rt_user("324234");
		record.setSend_rcv_user("43545");
		record.setDtl_seq(Long.valueOf("2"));
		record.setQty(0);
		record.setSite_id("32414");
		record.setOrder_no("4234");
		record.setCr_user("35325");// 由於要靈活一點 所以寫入頁面上的收料人員
									// 原始為getLoginUser().getUsername()
		record.setCr_time(new Date());
		int test = inv015Dao.insertInvTxn(record);
		Assert.assertEquals(1, test);
	}
	

	@Test
	public void testUpdateOnhandRTNQty() {
		HashMap<String,Object> dataObj = new HashMap<String,Object>();
		dataObj.put("optId", "3242134");
		dataObj.put("matNo", "42534");
		dataObj.put("qty", 2);
		dataObj.put("orderNo", "4325");
		dataObj.put("user", "4325");
		dataObj.put("mdTime", new Date());
		int test = inv015Dao.updateOnhandRTNQty(dataObj);
		Assert.assertEquals(0, test);
	}

	@Test
	public void testSelectInvTxn() {
		List<TbInvTxn> list = inv015Dao.searchTbInvTxn("423143",
				"32412432", "34255", new Date(), new Date(), "3", "341");
		Assert.assertEquals(0, list.size());
	}


	@Test
	public void testGetGroupInvTxnDataByGrid() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("txnNo", "32232");
		map.put("whId", "21341");
		map.put("createUser", "432435");
		map.put("strDate", new Date());
		map.put("endDate", new Date());
		List<TbInvTxnDTO> list = inv015Dao.getInvTxnDataByGrid(map);
		Assert.assertEquals(0, list.size());
	}

	@Test
	public void testGetInvTxnData() {
		List<TbInvTxnDTO> list = inv015Dao.getInvTxnData("523455", "43253",
				"234534", new Date(), new Date());
		Assert.assertEquals(0, list.size());
	}

	@Test
	public void testGetInvTxnDataByGrid() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("txnNo", "213412");
		map.put("whId", "3214");
		map.put("createUser", "214");
		map.put("strDate", new Date());
		map.put("endDate", new Date());
		List<TbInvTxnDTO> list = inv015Dao.getInvTxnDataByGrid(map);
		Assert.assertEquals(0, list.size());
	}

	@Test
	public void testSearchRtDOptId() {
		Set<String> optId = new HashSet<String>();
		optId.add("43253245");
		List<TbInvRtDDTO> list = inv015Dao.searchRtDOptId(optId);
		Assert.assertEquals(0, list.size());
	}

	@Test
	public void testSearchLessQty() {
		HashMap<String, Object> dataObj = new HashMap<String, Object>();
		dataObj.put("txnType", "2");
		dataObj.put("optId", "324324");
		dataObj.put("matNo", "4323");
		dataObj.put("qty", 3);
		List<TbInvTxnDTO> list = inv015Dao.searchLessQty(dataObj);
		Assert.assertEquals(0, list.size());
	}

}
