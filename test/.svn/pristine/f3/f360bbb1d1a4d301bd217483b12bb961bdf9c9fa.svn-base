package com.foya.noms.service.inv;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;

import com.foya.dao.mybatis.model.TbInvBooking;
import com.foya.dao.mybatis.model.TbInvInv;
import com.foya.dao.mybatis.model.TbInvTr;
import com.foya.dao.mybatis.model.TbInvTrDtl;
import com.foya.dao.mybatis.model.TbInvTrDtlExample;
import com.foya.noms.GenericTest;
import com.foya.noms.dto.ExcelImportDTO;
import com.foya.noms.dto.inv.TbInvInvDTO;
import com.foya.noms.dto.inv.TbInvTrDTO;
import com.foya.noms.dto.inv.TbInvTrDtlDTO;
import com.foya.noms.dto.inv.TbInvTrReadExcelDTO;

public class INV007ServiceTest extends GenericTest {

	@Inject
	private INV007Service inv007Service;

	@Test
	public void testSearch() throws ParseException {
		HashMap<String, Object> dataObj = new HashMap<String, Object>();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");

		dataObj.put("trNo", "12345677");
		dataObj.put("statusSelect", "1");
		dataObj.put("wareHouseOut", "12345678");
		dataObj.put("wareHouseIn", "12345678");
		dataObj.put("needStartDate", formatter.parse("2014/12/01"));
		dataObj.put("needEndDate", formatter.parse("2014/12/01"));
		dataObj.put("deptNeed", "1");
		dataObj.put("appDept", "1");
		dataObj.put("applyStartDate", formatter.parse("2014/12/01"));
		dataObj.put("applyEndDate", formatter.parse("2014/12/01"));
		dataObj.put("deptApplySelect", "1");
		dataObj.put("troutStartDate", formatter.parse("2014/12/01"));
		dataObj.put("troutEndDate", formatter.parse("2014/12/01"));
		dataObj.put("appUser", "1");
		dataObj.put("trinStartDate", formatter.parse("2014/12/01"));
		dataObj.put("trinEndDate", formatter.parse("2014/12/01"));
		List<TbInvTr> list = inv007Service.search(dataObj);
		Assert.assertEquals(0, list.size());
	}

	@Test
	public void testSearchTrMasterDscr() {
		HashMap<String, String> dataObj = new HashMap<String, String>();
		dataObj.put("trNo", "12345678");
		TbInvTrDTO list = inv007Service.searchTrMasterDscr(dataObj);
		Assert.assertNull(list);
	}

	@Test
	public void testSearchDtlAmt() {
		Assert.assertSame(1, inv007Service.searchDtlAmt("TRA141121001", "M031",
				new Byte("3")));
	}

	@Test
	public void testSearchTrActAmt() {
		int test = inv007Service.searchTrActAmt("12345677", "2345", 1,
				new Long(2), "2");
		Assert.assertEquals(0, test);
	}

	@Test
	public void testSearchDtlSeq() {
		List<TbInvTrDtlDTO> list = inv007Service.searchDtlSeq("12345677",
				"2345", new Byte("1"));
		Assert.assertEquals(0, list.size());
	}

	@Test
	public void testSearchDtlPage() {
		HashMap<String, Object> dataObj = new HashMap<String, Object>();
		dataObj.put("trNo", "12345677");
		List<TbInvTrDtlDTO> list = inv007Service.searchDtlPage(dataObj);
		Assert.assertEquals(0, list.size());
	}

	@Test
	public void testAddDtlRowCheck() {
		HashMap<String, String> dataObj = new HashMap<String, String>();
		dataObj.put("matNo", "2");
		dataObj.put("matStatus", "2");
		dataObj.put("wareHouseOut", "234");
		List<TbInvInvDTO> list = inv007Service.addDtlRowCheck(dataObj);
		Assert.assertEquals(0, list.size());
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
		data.setCr_time(new Date());
		data.setMd_user("user");
		data.setMd_time(new Date());
		String test = inv007Service.insertTbInvTrSelective(data);
		Assert.assertNotNull(test, "success");
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
		String test = inv007Service.insertTbInvTrDtlSelective(invDtl);
		Assert.assertNotNull(test, "success");
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
		inv.setCr_time(new Date());
		inv.setMd_user("user");
		inv.setMd_time(new Date());
		String test = inv007Service.updateTbInvTrSelective(inv);
		Assert.assertNotNull(test, "fail");
	}

	@Test
	public void testDeleteTbInvTrDtlByPrimarykey() {
		TbInvTrDtlExample dtlExample = new TbInvTrDtlExample();
		dtlExample.createCriteria().andTr_noEqualTo("231313")
				.andMat_noEqualTo("21312414")
				.andMat_statusEqualTo(new Byte("2"));
		String test = inv007Service.deleteTbInvTrDtlByPrimarykey(dtlExample);
		Assert.assertNotNull(test, "fail");
	}

	@Test
	public void testSearchInvDtl() {
		List<TbInvTrDtlDTO> invTrDtlList2 = new ArrayList<TbInvTrDtlDTO>();
		TbInvTrDtlDTO dto = new TbInvTrDtlDTO();
		dto.setMat_no("M022");
		dto.setMat_status(new Byte("2"));
		dto.setApp_qty(1);
		// dto.setCr_user("4ferg");
		// dto.setTr_no("4234");
		// dto.setMatName("44444");
		// dto.setTr_dtl_no(new Long("3"));
		invTrDtlList2.add(dto);
		// List<TbInvTrDtlDTO> list = inv007Service.searchInvDtl(invTrDtlList2,
		// "213124");
		Assert.assertNotNull(inv007Service.searchInvDtl(invTrDtlList2, "12345"));
	}

	@Test
	public void testSelectTrNoToday() {
		Assert.assertNotNull(inv007Service.selectTrNoToday("20141212",
				"P09083552"));
	}

	@Test
	public void testDeleteByPrimaryKey() {
		String test = inv007Service.deleteByPrimaryKey("213131");
		Assert.assertNotNull(test, "fail");
	}

	@Test
	public void testInserttbInvBookingSelective() {
		TbInvBooking invBooking = new TbInvBooking();
		invBooking.setMat_no("12332441");
		invBooking.setAct_no("324143125235");
		invBooking.setAct_type(Byte.valueOf("2"));
		invBooking.setBooking_qty(2);
		invBooking.setCr_user("3424355245");
		invBooking.setCr_time(new Date());
		invBooking.setMd_user("3242423");
		invBooking.setMd_time(new Date());
		String test = inv007Service.inserttbInvBookingSelective(invBooking);
		Assert.assertNotNull(test, "success");
	}

	@Test
	public void testUpdateTbInvInvSelective() {
		TbInvInv invInv = new TbInvInv();
		invInv.setBooking_qty(234);
		invInv.setMd_user("4325245");
		invInv.setMd_time(new Date());
		String test = inv007Service.updateTbInvInvSelective(invInv, "31244",
				"214234");
		Assert.assertNotNull(test, "fail");
	}

	@Test
	public void testSearchBatchApply() {
		List<ExcelImportDTO<TbInvTrReadExcelDTO>> invTrDtlList = new ArrayList<ExcelImportDTO<TbInvTrReadExcelDTO>>();
		TbInvTrReadExcelDTO dto = new TbInvTrReadExcelDTO();
		ExcelImportDTO<TbInvTrReadExcelDTO> dtoM = new ExcelImportDTO<TbInvTrReadExcelDTO>();
		dto.setAppQty("2");
		dto.setBookingQty("3");
		dto.setCtrlType("A");
		dto.setMatName("test");
		dto.setMatNo("ssf");
		dto.setMatStatus("4");
		dtoM.setRecord(dto);
		invTrDtlList.add(dtoM);
		Assert.assertNotNull(inv007Service.searchBatchApply(invTrDtlList,
				"2342314"));
	}

	@Test
	public void testSelectForBatchExcel() {
		HashMap<String, Object> dataObj = new HashMap<String, Object>();
		dataObj.put("wareHouseOut", "12345677");
		List<TbInvInvDTO> list = inv007Service.selectForBatchExcel(dataObj);
		Assert.assertEquals(0, list.size());
	}

}
