package com.foya.noms.service.org;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.dao.mybatis.model.TbAuthRoleMenuFunDept;
import com.foya.noms.GenericTest;

public class RoleMenuFunDeptServiceTest extends GenericTest {

	@Autowired
	private RoleMenuFunDeptService service;
	
	@Test
	public void testGetUsedDeptByRoleMenu() {
		List<TbAuthRoleMenuFunDept> roleMenuFunDep = service.getUsedDeptByRoleMenu("116","441");
		Assert.assertNotNull(roleMenuFunDep);
		Assert.assertTrue(roleMenuFunDep.size()>0);
	}
}
