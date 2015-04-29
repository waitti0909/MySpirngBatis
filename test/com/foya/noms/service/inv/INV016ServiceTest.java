package com.foya.noms.service.inv;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.dao.mybatis.model.TbAuthPersonnel;
import com.foya.dao.mybatis.model.TbComCompany;
import com.foya.dao.mybatis.model.TbComCompanyExample;
import com.foya.dao.mybatis.model.TbOrgDept;
import com.foya.dao.mybatis.model.TbOrgDeptExample;
import com.foya.dao.mybatis.model.TbOrgDomain;
import com.foya.dao.mybatis.model.UTbInvWarehouseExample;
import com.foya.noms.GenericTest;
import com.foya.noms.dto.inv.TbInvWarehouseDTO;
import com.foya.noms.service.system.LookupService;
import com.foya.noms.util.LabelValueModel;

public class INV016ServiceTest extends GenericTest {

	@Autowired
	private INV016Service inv016Service;

	@Autowired
	private LookupService lookupService;

	@Test
	public void testSelectInvWarehouseByExample() {
		UTbInvWarehouseExample example = new UTbInvWarehouseExample();
		example.createCriteria().andIs_effectiveEqualTo(true);
		List<TbInvWarehouseDTO> list = inv016Service.selectInvWarehouseByExample(example);
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() > 0);
	}

	@Test
	public void testSelectAllOrgDomain() {
		List<TbOrgDomain> list = inv016Service.selectAllOrgDomain();
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() > 0);
	}

	@Test
	public void testSelectAllOrgDept() {
		TbOrgDeptExample example = new TbOrgDeptExample();
		example.createCriteria().andDOMAINEqualTo("HQ");
		List<TbOrgDept> list = inv016Service.selectAllOrgDept(example);
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() > 0);
	}

	@Test
	public void testSelectWarehouse() {
		UTbInvWarehouseExample example = new UTbInvWarehouseExample();
		UTbInvWarehouseExample.Criteria cr = example.createCriteria();
		cr.andWh_idEqualTo("0001");
		cr.andWh_attributeEqualTo("V");
		cr.andWh_typeEqualTo("C");
		cr.andIs_effectiveEqualTo("1".equals("0") ? false : true);
		cr.andDomainEqualTo("C");
		cr.andDept_idEqualTo("450000");
		List<TbInvWarehouseDTO> list = inv016Service.selectWarehouse(example);
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() == 1);
	}

	@Test
	public void testSelectWarehouseByPrimaryKey() {
		TbInvWarehouseDTO tb = inv016Service.selectWarehouseByPrimaryKey("0001");
		Assert.assertNotNull(tb);
		Assert.assertEquals(tb.getWh_id(), "0001");
	}

	@Test
	public void testInsertInvWarehouseSelectiveSelective() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		TbInvWarehouseDTO record = new TbInvWarehouseDTO();
		record.setWh_id("JUnit" + sdf.format(new Date()));
		record.setWh_name("JUnit測試倉庫");
		record.setWh_type("V");
		record.setWh_attribute("V");
		record.setDomain("C");
		record.setDept_id("450000");
		record.setAssigned_name("341");
		record.setIs_effective("1".equals("0") ? false : true);
		record.setContact1("JUnit使用者");
		record.setFax_nbr("0200000000");
		record.setContact1_nbr1("0900000001");
		record.setContact1_nbr2("0900000002");
		record.setContact2("JUnit測試人員");
		record.setContact2_nbr("0900000003");
		record.setContact_address("１０００２ 台北市中正區八德路１段");
		record.setMd_user("JUnitTestCase");
		record.setMd_time(new Date());
		record.setCr_user("JUnitTestCase");
		record.setCr_time(new Date());
		int flag = inv016Service.insertInvWarehouseSelective(record);
		Assert.assertEquals(flag, 1);
	}

	@Test
	public void testUpdateByPrimaryKeySelective() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		TbInvWarehouseDTO record = new TbInvWarehouseDTO();
		record.setWh_id("0001");
		record.setWh_name("JUnit測試倉庫");
		record.setIs_effective("0".equals("0") ? false : true);
		record.setContact1("JUnit使用者");
		record.setFax_nbr("0200000000");
		record.setContact1_nbr1("0900000001");
		record.setContact1_nbr2("0900000002");
		record.setContact2("JUnit測試人員");
		record.setContact2_nbr("0900000003");
		record.setContact_address("１０００２ 台北市中正區八德路１段");
		try {
			record.setFailure_date(sdf.parse("2014/10/10"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		record.setFailure_resn("OTHERS");
		record.setMemo("test");
		record.setMd_user("JUnitTestCase");
		record.setMd_time(new Date());
		int flag = inv016Service.updateByPrimaryKeySelective(record);
		Assert.assertEquals(flag, 1);
	}

	@Test
	public void testSelectComCompanyByExample() {
		List<TbComCompany> list = inv016Service.selectComCompanyByExample(new TbComCompanyExample());
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() > 0);
	}

	@Test
	public void testSelectPersonnelByDeptId() {
		List<TbAuthPersonnel> list = inv016Service.selectPersonnelByDeptId("440500");
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() > 0);
	}

	@Test
	public void testCountInvWarehouseByExample() {
		UTbInvWarehouseExample example = new UTbInvWarehouseExample();
		UTbInvWarehouseExample.Criteria cr = example.createCriteria();
		cr.andWh_idEqualTo("0001");
		int count = inv016Service.countInvWarehouseByExample(example);
		Assert.assertTrue(count > 0);
	}

	@Test
	public void testGetLabelValueList() {
		List<LabelValueModel> list = lookupService.getLabelValueList("WH_ATTR");
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() > 0);
		list = lookupService.getLabelValueList("WH_TYPE");
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() > 0);
		list = lookupService.getLabelValueList("WH_EFFECTIVE");
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() > 0);
		list = lookupService.getLabelValueList("WH_FAIL_RESN");
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() > 0);
	}
}
