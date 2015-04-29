package com.foya.noms.dao.org;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.dao.mybatis.model.TbOrgDept;
import com.foya.dao.mybatis.model.TbOrgDeptExample;
import com.foya.noms.GenericTest;
import com.foya.noms.dto.org.DeptDTO;

public class ORG002DaoTest extends GenericTest {

	@Autowired
	private ORG002Dao dao;
	@Autowired
	private DeptDao deptDao;

	@Test
	public void testFindByPk() {
		TbOrgDept dept = deptDao.findByPk("123456");
		Assert.assertNotNull(dept);
		Assert.assertEquals(dept.getDEPT_ID(), "123456");
	}

	@Test
	public void testFindByCondition() {
		List<TbOrgDept> deptList = deptDao.findByCondition(new TbOrgDeptExample());
		Assert.assertNotNull(deptList);
		Assert.assertTrue(deptList.size() > 0);
	}

	@Test
	public void testSearchDeptByIdName() {
		List<DeptDTO> dept = deptDao.searchDeptByIdName("123456", "junit");
		Assert.assertNotNull(dept);
		Assert.assertTrue(dept.size() == 1);
		Assert.assertEquals(dept.get(0).getDEPT_ID(), "123456");
		Assert.assertEquals(dept.get(0).getDEPT_NAME(), "junit");
	}
}
