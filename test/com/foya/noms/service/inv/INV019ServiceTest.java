package com.foya.noms.service.inv;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.dao.mybatis.model.UTbInvSiteTxnExample;
import com.foya.dao.mybatis.model.UTbInvWarehouseExample;
import com.foya.noms.GenericTest;
import com.foya.noms.dto.inv.TbInvMaterialDTO;
import com.foya.noms.dto.inv.TbInvSiteTxnDTO;
import com.foya.noms.dto.inv.TbInvWarehouseDTO;
/**
 * 庫存查詢之Service Junit
 */
public class INV019ServiceTest extends GenericTest {

	@Autowired
	private INV019Service inv019Service;

	@Test
	public void testSelectInvWarehouseByExample() {
		UTbInvWarehouseExample example = new UTbInvWarehouseExample();
		example.createCriteria().andWh_typeEqualTo("C");
		example.createCriteria().andWh_attributeEqualTo("M");
		List<TbInvWarehouseDTO> recordList = inv019Service.selectInvWarehouseByExample(example);
	    Assert.assertNotNull(recordList);
		Assert.assertTrue(recordList.size() > 0);
	}
	/*
	@Test
	public void testSearch() {
		HttpServletRequest request = new HttpServletRequest();		
		List<Object> recordList = inv019Service.search(request);
		Assert.assertNotNull(recordList);
		Assert.assertTrue(recordList.size() > 0);
	}*/

}
