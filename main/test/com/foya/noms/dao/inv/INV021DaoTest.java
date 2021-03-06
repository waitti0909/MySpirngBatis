package com.foya.noms.dao.inv;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.dao.mybatis.model.UTbInvAssetExample;
import com.foya.dao.mybatis.model.UTbInvInvExample;
import com.foya.dao.mybatis.model.UTbInvOnhandExample;
import com.foya.dao.mybatis.model.UTbInvSrlExample;
import com.foya.dao.mybatis.model.UTbInvWarehouseExample;
import com.foya.noms.GenericTest;
import com.foya.noms.dto.inv.TbInvAssetDTO;
import com.foya.noms.dto.inv.TbInvInvDTO;
import com.foya.noms.dto.inv.TbInvOnhandDTO;
import com.foya.noms.dto.inv.TbInvSrlDTO;
import com.foya.noms.dto.inv.TbInvWarehouseDTO;

public class INV021DaoTest extends GenericTest {

	@Autowired
	private INV021Dao inv021Dao;

	@Test
	public void testSelectInvWarehouseByExample() {
		UTbInvWarehouseExample example = new UTbInvWarehouseExample();
		example.createCriteria().andIs_effectiveEqualTo(true);
		List<TbInvWarehouseDTO> list = inv021Dao.selectInvWarehouseByExample(example);
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() > 0);
	}

	@Test
	public void testSelectInvAssetByExample() {
		UTbInvAssetExample example = new UTbInvAssetExample();
		UTbInvAssetExample.Criteria cr = example.createCriteria();
		cr.andAsset_typeEqualTo("M");
		cr.andItem_noEqualTo("M001");
		cr.andSite_idEqualTo("201411270014");
		cr.andTag_noEqualTo("M001F001");
		cr.andMaint_deptEqualTo("440300");
		cr.andDomainEqualTo("N1");
		List<TbInvAssetDTO> list = inv021Dao.selectInvAssetByExample(example);
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() > 0);
	}

	@Test
	public void testSelectInvInvByExampleForLimit() {
		UTbInvInvExample example = new UTbInvInvExample();
		UTbInvInvExample.Criteria cr = example.createCriteria();
		cr.andMat_noEqualTo("M002");
		cr.andWh_idEqualTo("12345678");
		cr.andDomainEqualTo("HQ");
		cr.andDept_idEqualTo("450000");
		String[] ms = "1,2".split(",");
		List<Byte> matStatusList = new ArrayList<Byte>();
		for (int i = 0; i < ms.length; i++) {
			matStatusList.add(Byte.valueOf(ms[i]));
		}
		cr.andMat_statusIn(matStatusList);
		List<TbInvInvDTO> list = inv021Dao.selectInvInvByExampleForLimit(example, 10);
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() > 0);
	}

	@Test
	public void testSelectInvOnhandByExample() {
		UTbInvOnhandExample example = new UTbInvOnhandExample();
		UTbInvOnhandExample.Criteria cr = example.createCriteria();
		cr.andMat_noEqualTo("M001");
		cr.andWh_idEqualTo("12345678");
		cr.andSite_idEqualTo("F4301");
		String[] ms = "1".split(",");
		List<Byte> matStatusList = new ArrayList<Byte>();
		for (int i = 0; i < ms.length; i++) {
			matStatusList.add(Byte.valueOf(ms[i]));
		}
		cr.andMat_statusIn(matStatusList);
		cr.andTag_noEqualTo("A00345661");
		cr.andOver_dayGreaterThanOrEqualTo(Long.valueOf("1"));
		List<TbInvOnhandDTO> list = inv021Dao.selectInvOnhandByExample(example);
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() > 0);
	}

	@Test
	public void testSelectInvSrlByExample() {
		UTbInvSrlExample uTbInvSrlExample = new UTbInvSrlExample();
		UTbInvSrlExample.Criteria cr = uTbInvSrlExample.createCriteria();
		cr.andMat_noEqualTo("M001");
		cr.andWh_idEqualTo("0001");
		List<TbInvSrlDTO> list = inv021Dao.selectInvSrlByExample(uTbInvSrlExample);
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() > 0);
	}

}
