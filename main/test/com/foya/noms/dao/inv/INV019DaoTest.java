package com.foya.noms.dao.inv;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.dao.mybatis.model.UTbInvInvExample;
import com.foya.dao.mybatis.model.UTbInvSiteTxnExample;
import com.foya.dao.mybatis.model.UTbInvSrlExample;
import com.foya.dao.mybatis.model.UTbInvWarehouseExample;
import com.foya.noms.GenericTest;
import com.foya.noms.dto.inv.TbInvInvDTO;
import com.foya.noms.dto.inv.TbInvMaterialDTO;
import com.foya.noms.dto.inv.TbInvSiteTxnDTO;
import com.foya.noms.dto.inv.TbInvSrlDTO;
import com.foya.noms.dto.inv.TbInvWarehouseDTO;
/**
 * 庫存查詢Dao Junit
 */
public class INV019DaoTest extends GenericTest {

	@Autowired
	private INV019Dao inv019Dao;

	@Test
	public void testSelectInvWarehouseByExample() {
		UTbInvWarehouseExample example = new UTbInvWarehouseExample();
		example.createCriteria().andWh_typeEqualTo("C");
		example.createCriteria().andWh_attributeEqualTo("M");
		List<TbInvWarehouseDTO> recordList = inv019Dao.selectInvWarehouseByExample(example);		
		Assert.assertNotNull(recordList);
		Assert.assertTrue(recordList.size() > 0);
	}
//	@Test
//	public void testSelectInvInvBy019() {
//		UTbInvInvExample example = new UTbInvInvExample();
//		example.createCriteria().andMat_noLike("%M01%");
//		List<TbInvInvDTO>  recordList = inv019Dao.selectInvInvBy019(example);
//		Assert.assertNotNull(recordList);
//		Assert.assertTrue(recordList.size() > 0);
//	}
//	@Test
//	public void testSelectInvInvBy019() {
//		UTbInvSrlExample example = new UTbInvSrlExample();
//		example.createCriteria().andMat_noLike("%M01%");
//		List<TbInvSrlDTO>   recordList = inv019Dao.selectInvSrlByExample(example);
//		Assert.assertNotNull(recordList);
//		Assert.assertTrue(recordList.size() > 0);
//	}

}
