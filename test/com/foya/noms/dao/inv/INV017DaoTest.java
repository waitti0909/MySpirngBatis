package com.foya.noms.dao.inv;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.dao.mybatis.model.TbInvSnCor;
import com.foya.dao.mybatis.model.UTbInvSrlExample;
import com.foya.dao.mybatis.model.UTbInvWarehouseExample;
import com.foya.noms.GenericTest;
import com.foya.noms.dto.inv.TbInvSrlDTO;
import com.foya.noms.dto.inv.TbInvWarehouseDTO;

public class INV017DaoTest extends GenericTest {

	@Autowired
	private INV017Dao inv017Dao;

	@Test
	public void testSelectInvWarehouseByExample() {
		UTbInvWarehouseExample example = new UTbInvWarehouseExample();
		example.createCriteria().andIs_effectiveEqualTo(true);
		List<TbInvWarehouseDTO> list = inv017Dao.selectInvWarehouseByExample(example);
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() > 0);
	}

	@Test
	public void testSelectInvSrlByExample() {
		UTbInvSrlExample example = new UTbInvSrlExample();
		UTbInvSrlExample.Criteria cr = example.createCriteria();
		cr.andMat_noLike("%M001%");
		cr.andVen_snLike("%SN%");
		cr.andSite_idLike("%T2014%");
		cr.andWh_idEqualTo("0001");
		List<TbInvSrlDTO> list = inv017Dao.selectInvSrlByExample(example);
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() > 0);
	}

	@Test
	public void testSelectInvSrlByPrimaryKey() {
		TbInvSrlDTO tb = inv017Dao.selectInvSrlByPrimaryKey(Long.valueOf(2));
		Assert.assertNotNull(tb);
		Assert.assertEquals(tb.getSrl_no(), Long.valueOf(2));
	}

	@Test
	public void testUpdateInvSrlByPrimaryKeySelective() {
		TbInvSrlDTO record = new TbInvSrlDTO();
		record.setSrl_no(Long.valueOf(2));
		record.setVen_sn("SN0000009");
		record.setMd_user("JUnitTestCase");
		record.setMd_time(new Date());
		int status = inv017Dao.updateInvSrlByPrimaryKeySelective(record);
		Assert.assertEquals(status, 1);
	}

	@Test
	public void testInsertInvSnCorSelective() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		TbInvSnCor record2 = new TbInvSnCor();
		record2.setSrl_no(Long.valueOf(sdf.format(new Date())));
		record2.setVen_sn("SN0000009");
		record2.setOld_ven_sn("SN003");
		record2.setTran_dept_id("530100");
		record2.setCr_user("JUnitTestCase");
		record2.setCr_time(new Date());
		int status = inv017Dao.insertInvSnCorSelective(record2);
		Assert.assertEquals(status, 1);
	}

}
