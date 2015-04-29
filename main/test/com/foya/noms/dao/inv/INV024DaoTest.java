package com.foya.noms.dao.inv;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.dao.mybatis.model.UTbInvBookingExample;
import com.foya.dao.mybatis.model.UTbInvInvExample;
import com.foya.noms.GenericTest;
import com.foya.noms.dto.inv.TbInvBookingDTO;
import com.foya.noms.dto.inv.TbInvInvDTO;

public class INV024DaoTest extends GenericTest {

	@Autowired
	private INV024Dao inv024Dao;

	@Test
	public void testSelectInvInvByExampleAndGroupBy() {
		UTbInvInvExample example = new UTbInvInvExample();
		UTbInvInvExample.Criteria cr = example.createCriteria();
		cr.andMat_noEqualTo("M002");
		cr.andWh_idEqualTo("0001");
		List<TbInvInvDTO> list = inv024Dao.selectInvInvByExampleAndGroupBy(example);
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() > 0);
	}

	@Test
	public void testSelectInvBookingByExample() {
		UTbInvBookingExample example = new UTbInvBookingExample();
		UTbInvBookingExample.Criteria cr = example.createCriteria();
		cr.andMat_noEqualTo("M002");
		cr.andWh_idEqualTo("0001");
		List<TbInvBookingDTO> list = inv024Dao.selectInvBookingByExample(example);
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() > 0);
	}

}
