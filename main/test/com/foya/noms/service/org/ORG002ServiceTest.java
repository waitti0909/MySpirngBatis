package com.foya.noms.service.org;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.dao.mybatis.model.TbOrgDept;
import com.foya.dao.mybatis.model.TbOrgDeptExample;
import com.foya.noms.GenericTest;
import com.foya.noms.dto.org.DeptDTO;

public class ORG002ServiceTest extends GenericTest {

	@Autowired
	private ORG002Service service;
	
	@Test
	public void testGetByPk() {
		TbOrgDept dept = service.getByPk("123456");
		Assert.assertNotNull(dept);
		Assert.assertEquals(dept.getDEPT_ID(), "123456");
	}
	
	@Test
	public void testGetByCondition() {
		List<TbOrgDept> deptList = service.getByCondition(new TbOrgDeptExample());
		Assert.assertNotNull(deptList);
		Assert.assertTrue(deptList.size()>0);
	}
	
	@Test
	public void testGetDeptKeyValues() {
		Map<String, String> keyValues = service.getDeptKeyValues();
		Assert.assertNotNull(keyValues);
		Assert.assertTrue(keyValues.size()>0);
	}

	@Test
	public void testSearchDeptByIdName(){
		List<DeptDTO> dept = service.searchDeptByIdName("123456", "junit");
		Assert.assertNotNull(dept);
		Assert.assertTrue(dept.size()==1);
		Assert.assertEquals(dept.get(0).getDEPT_ID(), "123456");
		Assert.assertEquals(dept.get(0).getDEPT_NAME(), "junit");
	}
}
