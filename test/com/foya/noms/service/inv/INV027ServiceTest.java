package com.foya.noms.service.inv;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.dao.mybatis.model.TbComEqpModel;
import com.foya.dao.mybatis.model.TbComEqpModelExample;
import com.foya.dao.mybatis.model.TbComEqpType;
import com.foya.dao.mybatis.model.TbComEqpTypeExample;
import com.foya.dao.mybatis.model.TbInvCategory;
import com.foya.dao.mybatis.model.UTbInvBomExample;
import com.foya.dao.mybatis.model.UTbInvCategoryExample;
import com.foya.dao.mybatis.model.UTbInvMaterialExample;
import com.foya.noms.GenericTest;
import com.foya.noms.dto.inv.TbInvBomDTO;
import com.foya.noms.dto.inv.TbInvMaterialDTO;

public class INV027ServiceTest extends GenericTest {

	@Autowired
	private INV027Service inv027Service;

	@Test
	public void testSelectEqpModelByExample() {
		List<TbComEqpModel> list = inv027Service.selectEqpModelByExample(new TbComEqpModelExample());
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() > 0);
	}

	@Test
	public void testSelectEqpTypeByExample() {
		TbComEqpTypeExample example2 = new TbComEqpTypeExample();
		example2.createCriteria().andEQP_TYPE_IDEqualTo("1");
		List<TbComEqpType> list = inv027Service.selectEqpTypeByExample(example2);
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() > 0);
	}

	@Test
	public void testSelectDistinctInvBomByExample() {
		UTbInvBomExample example = new UTbInvBomExample();
		UTbInvBomExample.Criteria cr = example.createCriteria();
		cr.andEqp_model_idEqualTo("1A");
		cr.andEqp_type_idEqualTo("1");
		List<TbInvBomDTO> list = inv027Service.selectDistinctInvBomByExample(example);
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() > 0);
	}

	@Test
	public void testSelectInvBomByExample() {
		UTbInvBomExample example = new UTbInvBomExample();
		UTbInvBomExample.Criteria cr = example.createCriteria();
		cr.andEqp_model_idEqualTo("1A");
		cr.andEqp_type_idEqualTo("1");
		List<TbInvBomDTO> list = inv027Service.selectInvBomByExample(example);
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() > 0);
	}

	@Test
	public void testCountInvBomByExample() {
		UTbInvBomExample example = new UTbInvBomExample();
		UTbInvBomExample.Criteria cr = example.createCriteria();
		cr.andEqp_model_idEqualTo("1A");
		cr.andEqp_type_idEqualTo("1");
		cr.andMat_noEqualTo("M001");
		int count = inv027Service.countInvBomByExample(example);
		Assert.assertTrue(count > 0);
	}

	@Test
	public void testInsertInvBomSelective() {
		TbInvBomDTO tb = new TbInvBomDTO();
		tb.setEqp_model_id("1X");
		tb.setEqp_type_id("1");
		tb.setMat_no("M001");
		tb.setQty(1);
		tb.setEffective_date(new Date());
		tb.setCr_user("JUnitTestCase");
		tb.setCr_time(new Date());
		tb.setMd_user("JUnitTestCase");
		tb.setMd_time(new Date());
		int status = inv027Service.insertInvBomSelective(tb);
		Assert.assertTrue(status == 1);
	}

	@Test
	public void testUpdateInvBomByPrimaryKeySelective() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		TbInvBomDTO tb = new TbInvBomDTO();
		tb.setEqp_model_id("1A");
		tb.setEqp_type_id("1");
		tb.setMat_no("M001");
		tb.setQty(1);
		try {
			tb.setFailure_date(sdf.parse("2014/12/31"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		tb.setMd_user("JUnitTestCase");
		tb.setMd_time(new Date());
		int status = inv027Service.updateInvBomByPrimaryKeySelective(tb);
		Assert.assertTrue(status == 1);
	}

	@Test
	public void testSelectInvMaterialByExample() {
		UTbInvMaterialExample example = new UTbInvMaterialExample();
		UTbInvMaterialExample.Criteria cr = example.createCriteria();
		cr.andCatg1_codeEqualTo("31");
		cr.andCatg2_codeEqualTo("001");
		cr.andCatg3_codeEqualTo("001");
		List<TbInvMaterialDTO> list = inv027Service.selectInvMaterialByExample(example);
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() > 0);
	}

	@Test
	public void testSelectInvCategoryByExample() {
		UTbInvCategoryExample example = new UTbInvCategoryExample();
		UTbInvCategoryExample.Criteria cr = example.createCriteria();
		cr.andCatg1_codeEqualTo("31");
		cr.andCatg2_codeEqualTo("001");
		List<TbInvCategory> list = inv027Service.selectInvCategoryByExample(example);
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() > 0);
	}

	@Test
	public void testSetEntityStringArray() {
		String[] oneRowCellsArray = {"M001", "1"};
		TbInvBomDTO tb = null;
		try {
			tb = inv027Service.setEntity(oneRowCellsArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Assert.assertNotNull(tb);
		Assert.assertEquals(tb.getMat_no(), "M001");
	}

}
