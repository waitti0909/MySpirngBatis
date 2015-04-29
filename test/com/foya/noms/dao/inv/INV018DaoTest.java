package com.foya.noms.dao.inv;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.dao.mybatis.model.TbInvTxn;
import com.foya.dao.mybatis.model.TbOrgDept;
import com.foya.dao.mybatis.model.TbSysLookup;
import com.foya.dao.mybatis.model.TbSysLookupExample;
import com.foya.dao.mybatis.model.UTbInvBookingExample;
import com.foya.dao.mybatis.model.UTbInvInvExample;
import com.foya.dao.mybatis.model.UTbInvSrlExample;
import com.foya.dao.mybatis.model.UTbInvStatTranExample;
import com.foya.dao.mybatis.model.UTbInvWarehouseExample;
import com.foya.noms.GenericTest;
import com.foya.noms.dto.inv.TbInvBookingDTO;
import com.foya.noms.dto.inv.TbInvInvDTO;
import com.foya.noms.dto.inv.TbInvSrlDTO;
import com.foya.noms.dto.inv.TbInvStatTranDTO;
import com.foya.noms.dto.inv.TbInvWarehouseDTO;

public class INV018DaoTest extends GenericTest {

	@Autowired
	private INV018Dao inv018Dao;

	@Test
	public void testSelectInvWarehouseByExample() {
		UTbInvWarehouseExample warehouseExample = new UTbInvWarehouseExample();
		warehouseExample.createCriteria().andIs_effectiveEqualTo(true);
		List<TbInvWarehouseDTO> warehouseList = inv018Dao.selectInvWarehouseByExample(warehouseExample);
		Assert.assertNotNull(warehouseList);
		Assert.assertTrue(warehouseList.size() > 0);
	}

	@Test
	public void testSelectSysLookupByExample() {
		TbSysLookupExample lookupExample = new TbSysLookupExample();
		lookupExample.createCriteria().andTYPEEqualTo("INV_TRAN_AUDIT_STAT");
		List<TbSysLookup> list = inv018Dao.selectSysLookupByExample(lookupExample);
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() > 0);

		lookupExample = new TbSysLookupExample();
		lookupExample.createCriteria().andTYPEEqualTo("MAT_STATUS");
		list = inv018Dao.selectSysLookupByExample(lookupExample);
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() > 0);

		lookupExample = new TbSysLookupExample();
		lookupExample.createCriteria().andTYPEEqualTo("INV_STAT_TRAN_RESN");
		list = inv018Dao.selectSysLookupByExample(lookupExample);
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() > 0);
	}

	@Test
	public void testSelectInvStatTranByExample() {
		UTbInvStatTranExample example = new UTbInvStatTranExample();
		UTbInvStatTranExample.Criteria cr = example.createCriteria();
		cr.andInv_tran_noLike("%IAH2014%");
		cr.andInv_tran_audit_statusEqualTo(Byte.valueOf("1"));
		cr.andWh_idEqualTo("0001");
		List<TbInvStatTranDTO> list = inv018Dao.selectInvStatTranByExample(example);
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() > 0);
	}

	@Test
	public void testCountInvStatTranByExample() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		UTbInvStatTranExample example = new UTbInvStatTranExample();
		UTbInvStatTranExample.Criteria cr = example.createCriteria();
		cr.andInv_tran_noLike("IAH2014%");
		try {
			cr.andCr_timeBetween(sdf.parse("2014/12/05 00:00:00.000"), sdf.parse("2014/12/08 23:59:59.999"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int count = inv018Dao.countInvStatTranByExample(example);
		Assert.assertTrue(count > 0);
	}

	@Test
	public void testUpdateInvStatTranByPrimaryKeySelective() {
		TbInvStatTranDTO record = new TbInvStatTranDTO();
		record.setInv_tran_no("IAH20141205001");
		record.setInv_tran_audit_status(Byte.valueOf("1"));
		record.setWh_id("0001");
		record.setMat_no("M001");
		record.setOld_mat_status(Byte.valueOf("1"));
		record.setNew_mat_status(Byte.valueOf("2"));
		record.setSrl_no(Long.valueOf(2));
		record.setTran_qty(1);
		record.setTran_reason("TRAN1");
		record.setMemo("test");
		record.setTran_user("JUnitTestCase");
		record.setTran_date(new Date());
		record.setMd_user("JUnitTestCase");
		record.setMd_time(new Date());
		int status = inv018Dao.updateInvStatTranByPrimaryKeySelective(record);
		Assert.assertTrue(status == 1);
	}

	@Test
	public void testInsertInvStatTranSelective() {
		TbInvStatTranDTO record = new TbInvStatTranDTO();
		record.setInv_tran_no("IAH20141205003");
		record.setInv_tran_audit_status(Byte.valueOf("1"));
		record.setWh_id("0001");
		record.setMat_no("M001");
		record.setOld_mat_status(Byte.valueOf("1"));
		record.setNew_mat_status(Byte.valueOf("2"));
		record.setSrl_no(Long.valueOf(2));
		record.setTran_qty(1);
		record.setTran_reason("TRAN1");
		record.setMemo("test");
		record.setTran_user("JUnitTestCase");
		record.setTran_date(new Date());
		record.setMd_user("JUnitTestCase");
		record.setMd_time(new Date());
		record.setCr_user("JUnitTestCase");
		record.setCr_time(new Date());
		int status = inv018Dao.insertInvStatTranSelective(record);
		Assert.assertTrue(status == 1);
	}

	@Test
	public void testSelectInvBookingByExample() {
		UTbInvBookingExample invBookingExample = new UTbInvBookingExample();
		UTbInvBookingExample.Criteria cr = invBookingExample.createCriteria();
		cr.andAct_noEqualTo("A0001");
		cr.andMat_noEqualTo("M002");
		cr.andWh_idEqualTo("0001");
		cr.andAct_typeEqualTo(Byte.valueOf("3"));
		List<TbInvBookingDTO> list = inv018Dao.selectInvBookingByExample(invBookingExample);
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() > 0);
	}

	@Test
	public void testUpdateInvBookingByExampleSelective() {
		UTbInvBookingExample invBookingExample = new UTbInvBookingExample();
		UTbInvBookingExample.Criteria cr = invBookingExample.createCriteria();
		cr.andEqualTo("act_no", "A0001");
		cr.andEqualTo("mat_no", "M002");
		cr.andEqualTo("wh_id", "0001");
		cr.andEqualTo("act_type", Byte.valueOf("3"));
		
		TbInvBookingDTO tbInvBookingDTO = new TbInvBookingDTO();
		tbInvBookingDTO.setBooking_qty(1);
		tbInvBookingDTO.setMd_user("JUnitTestCase");
		tbInvBookingDTO.setMd_time(new Date());
		int status = inv018Dao.updateInvBookingByExampleSelective(tbInvBookingDTO, invBookingExample);
		Assert.assertEquals(status, 1);
	}

	@Test
	public void testInsertInvBookingSelective() {
		// 新增Booking資料
		TbInvBookingDTO bookingRecord = new TbInvBookingDTO();
		bookingRecord.setWh_id("0001");
		bookingRecord.setMat_no("M001");
		bookingRecord.setAct_no("A0001");
		bookingRecord.setAct_type(Byte.valueOf("3"));
		bookingRecord.setBooking_qty(1);
		bookingRecord.setCr_user("JUnitTestCase");
		bookingRecord.setCr_time(new Date());
		bookingRecord.setMd_user("JUnitTestCase");
		bookingRecord.setMd_time(new Date());
		int status = inv018Dao.insertInvBookingSelective(bookingRecord);
		Assert.assertEquals(status, 1);
	}

	@Test
	public void testDeleteInvBookingByExample() {
		UTbInvBookingExample invBookingExample = new UTbInvBookingExample();
		UTbInvBookingExample.Criteria cr = invBookingExample.createCriteria();
		cr.andEqualTo("act_no", "A0001");
		cr.andEqualTo("mat_no", "M002");
		cr.andEqualTo("wh_id", "0001");
		cr.andEqualTo("act_type", Byte.valueOf("3"));
		int status = inv018Dao.deleteInvBookingByExample(invBookingExample);
		Assert.assertEquals(status, 1);
	}

	@Test
	public void testSelectInvInvByExample() {
		UTbInvInvExample invInvExample = new UTbInvInvExample();
		UTbInvInvExample.Criteria invInvCr = invInvExample.createCriteria();
		invInvCr.andWh_idEqualTo("0001");
		invInvCr.andMat_noEqualTo("M002");
		List<TbInvInvDTO> list = inv018Dao.selectInvInvByExample(invInvExample);
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() > 0);
	}

	@Test
	public void testUpdateInvInvByExampleSelective() {
		UTbInvInvExample invInvExample = new UTbInvInvExample();
		UTbInvInvExample.Criteria invInvCr = invInvExample.createCriteria();
		invInvCr.andEqualTo("wh_id", "0001");
		invInvCr.andEqualTo("mat_no", "M002");

		TbInvInvDTO tbInvInvDTO = new TbInvInvDTO();
		tbInvInvDTO.setStd_qty(1);
		tbInvInvDTO.setWrd_qty(2);
		tbInvInvDTO.setWsp_qty(3);
		tbInvInvDTO.setBooking_qty(6);
		int status = inv018Dao.updateInvInvByExampleSelective(tbInvInvDTO, invInvExample);
		Assert.assertEquals(status, 1);
	}

	@Test
	public void testInsertInvTxnSelective() {
		TbInvTxn tbInvTxn = new TbInvTxn();
		tbInvTxn.setTxn_type(Byte.valueOf("5"));
		tbInvTxn.setTxn_no("X999");
		tbInvTxn.setWh_id("0001");
		tbInvTxn.setMat_no("M002");
		tbInvTxn.setMat_status(Byte.valueOf("1"));
		tbInvTxn.setQty(1);
		tbInvTxn.setFa_no("F001");
		tbInvTxn.setSrl_no(Long.valueOf(4));
		tbInvTxn.setCr_user("JUnitTestCase");
		tbInvTxn.setCr_time(new Date());
		int status = inv018Dao.insertInvTxnSelective(tbInvTxn);
		Assert.assertEquals(status, 1);
	}

	@Test
	public void testSelectInvSrlByExample() {
		UTbInvSrlExample example = new UTbInvSrlExample();
		UTbInvSrlExample.Criteria cr = example.createCriteria();
		cr.andWh_idEqualTo("0001");
		cr.andMat_noEqualTo("M001");
		List<TbInvSrlDTO> list = inv018Dao.selectInvSrlByExample(example);
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() > 0);
	}

	@Test
	public void testSelectOrgDeptByPrimaryKey() {
		TbOrgDept orgDept = inv018Dao.selectOrgDeptByPrimaryKey("110000");
		Assert.assertNotNull(orgDept);
		Assert.assertEquals(orgDept.getDEPT_ID(), "110000");
	}

}
