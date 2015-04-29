package com.foya.noms.dao.inv;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;

import com.foya.dao.mybatis.model.TbInvBooking;
import com.foya.dao.mybatis.model.TbInvInv;
import com.foya.dao.mybatis.model.TbInvMaterial;
import com.foya.dao.mybatis.model.TbInvMaterialExample;
import com.foya.dao.mybatis.model.TbInvTr;
import com.foya.dao.mybatis.model.TbInvTrDtl;
import com.foya.noms.GenericTest;
import com.foya.noms.dto.inv.TbInvInvDTO;
import com.foya.noms.dto.inv.TbInvTrDTO;
import com.foya.noms.dto.inv.TbInvTrDtlDTO;

public class INV007DaoTest  extends GenericTest  {
	@Inject
	private INV007Dao inv007Dao;

	@Test
	public void testSearch() throws ParseException {
		HashMap<String,Object> dataObj=new HashMap<String,Object>();
		List<TbInvTr> list =inv007Dao.search(dataObj);
		Assert.assertNotNull(list);
	}

	@Test
	public void testSearchTrMasterDscr() {
		HashMap<String, String> dataObj=new HashMap<String,String>();
		dataObj.put("trNo", "TRA141212003");
		TbInvTrDTO list = inv007Dao.searchTrMasterDscr(dataObj);
		Assert.assertNotNull(list);
	}

	@Test
	public void testSearchDtlPage() {		
		HashMap<String,Object> dataObj=new HashMap<String,Object>();
		dataObj.put("trNo", "TRA141212003");
		List<TbInvTrDtlDTO> list = inv007Dao.searchDtlPage(dataObj);
		Assert.assertNotNull(list);
	}

	@Test
	public void testSearchDtlSeq() {
		HashMap<String,Object> dataObj = new HashMap<String,Object>();
		dataObj.put("trNo", "TRA141121001");
		dataObj.put("matNo", "M031");
		dataObj.put("matStatus", new Byte("3"));
		List<TbInvTrDtlDTO> list=inv007Dao.searchDtlSeq(dataObj);
		Assert.assertNotNull(list);
	}

	@Test
	public void testSearchDtlAmt() {
		int test=inv007Dao.searchTrActAmt("TRA1411244", "M003", 2,new Long(1111), "1111");
		Assert.assertNotNull(test);
	}

	@Test
	public void testSearchTrActAmt() {
		HashMap<String,Object> dataObj = new HashMap<String,Object>();
		dataObj.put("trNo", "TRA141121001");
		dataObj.put("matNo", "M031");
		dataObj.put("matStatus", new Byte("3"));
		Integer test=inv007Dao.searchDtlAmt(dataObj);
		Assert.assertSame(1,test);
	}

	@Test
	public void testAddDtlRowCheck() {
		HashMap<String,String> dataObj=new HashMap<String,String>();
		dataObj.put("matNo", "M022");
		dataObj.put("wareHouseOut", "12345678");
		List<TbInvInvDTO> list = inv007Dao.addDtlRowCheck(dataObj);
		Assert.assertNotNull(list);
	}

	@Test
	public void testInsertTbInvTrSelective() throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		TbInvTr data = new TbInvTr();
		data.setTr_no("12345");
		data.setStatus(Byte.valueOf("2"));
		data.setTr_in_wh_id("234");
		data.setTr_out_wh_id("4234");
		data.setNeed_dept_id("24424");
		data.setNeed_date(formatter.parse("2014/12/01"));
		data.setApp_dept_id("23");
		data.setApp_user("424");
		data.setApp_date(new Date());
		data.setMemo("1111");
		data.setCr_user("testUser");
		data.setCr_time(new  Date());
		data.setMd_user("user");
		data.setMd_time(new Date());
		String test = inv007Dao.insertTbInvTrSelective(data);
		Assert.assertSame("success",test);
	}

	@Test
	public void testInsertTbInvTrDtlSelective() {
		TbInvTrDtl invDtl = new TbInvTrDtl();
		invDtl.setTr_no("23424");
		invDtl.setMat_no("424342");
		invDtl.setMat_status(Byte.valueOf("4"));
		invDtl.setApp_qty(2);
		invDtl.setCr_user("testuser");
		invDtl.setCr_time(new Date());
		invDtl.setMd_user("testuser");
		invDtl.setMd_time(new Date());
		String test = inv007Dao.insertTbInvTrDtlSelective(invDtl);
		Assert.assertSame("success",test);
	}

	@Test
	public void testUpdateTbInvTrSelective() throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		TbInvTr inv = new TbInvTr();
		inv.setTr_no("12345");
		inv.setStatus(Byte.valueOf("2"));
		inv.setTr_in_wh_id("234");
		inv.setTr_out_wh_id("4234");
		inv.setNeed_dept_id("24424");
		inv.setNeed_date(formatter.parse("2014/12/01"));
		inv.setApp_dept_id("23");
		inv.setApp_user("424");
		inv.setApp_date(new Date());
		inv.setMemo("1111");
		inv.setCr_user("testUser");
		inv.setCr_time(new  Date());
		inv.setMd_user("user");
		inv.setMd_time(new Date());
		String test = inv007Dao.updateTbInvTrSelective(inv);
		Assert.assertSame("success",test);
	}

	@Test
	public void testUpdateTbInvTrDtlSelective() {
		TbInvTrDtl invDtl = new TbInvTrDtl();
		invDtl.setTr_no("23424");
		invDtl.setMat_no("424342");
		invDtl.setMat_status(Byte.valueOf("4"));
		invDtl.setApp_qty(2);
		invDtl.setCr_user("testuser");
		invDtl.setCr_time(new Date());
		invDtl.setMd_user("testuser");
		invDtl.setMd_time(new Date());
		String test = inv007Dao.insertTbInvTrDtlSelective(invDtl);
		Assert.assertSame("success",test );
	}

	@Test
	public void testDeleteTbInvTrDtlByPrimarykey() {
		String test = inv007Dao.deleteByPrimaryKey("213131");
		Assert.assertSame("success",test );
	}

	@Test
	public void testSelectMaxSeq() {
		TbInvTr text = inv007Dao.selectMaxSeq(null);
		Assert.assertNotNull(text);
	}

	@Test
	public void testDeleteByPrimaryKey() {
		String test = inv007Dao.deleteByPrimaryKey("213131");
		Assert.assertSame("success",test);
	}

	@Test
	public void testInsertTbInvBookingSelective() {
		TbInvBooking invBooking = new TbInvBooking();
		invBooking.setMat_no("12332441");
		invBooking.setAct_no("324143125235");
		invBooking.setAct_type(Byte.valueOf("2"));
		invBooking.setBooking_qty(2);
		invBooking.setCr_user("3424355245");
		invBooking.setCr_time(new Date());
		invBooking.setMd_user("3242423");
		invBooking.setMd_time(new Date());
		String test = inv007Dao.insertTbInvBookingSelective(invBooking);
		Assert.assertSame("fail",test);
	}

	@Test
	public void testUpdateTbInvInvSelective() {
		TbInvInv invInv = new TbInvInv();
		invInv.setBooking_qty(234);
		invInv.setMd_user("4325245");
		invInv.setMd_time(new Date());
		String test = inv007Dao.updateTbInvInvSelective(invInv, "31244", "214234");
		Assert.assertSame("success",test);
	}

	@Test
	public void testSelectForBatchExcel() {
		HashMap<String,Object> dataObj=new HashMap<String,Object>();
		dataObj.put("wareHouseOut", "12345678");
		List<TbInvInvDTO> list = inv007Dao.selectForBatchExcel(dataObj);
		Assert.assertNotNull(list);
	}

	@Test
	public void testSelectMaterial() {
		TbInvMaterialExample materialExample = new TbInvMaterialExample();
		materialExample.createCriteria().andIs_assetEqualTo(false).andMat_noEqualTo("4111");//物料狀態 is asset 0=true 1=false
		List<TbInvMaterial> matList=inv007Dao.selectMaterial(materialExample);
		Assert.assertSame(matList.size(),0);
	}

}
