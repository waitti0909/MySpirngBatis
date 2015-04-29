package com.foya.noms.dao.org;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.dao.mybatis.model.TbAuthRoleMenuFunDept;
import com.foya.dao.mybatis.model.TbAuthRoleMenuFunDeptExample;
import com.foya.noms.GenericTest;
import com.foya.noms.util.DateUtils;

public class RoleMenuFunDeptDaoTest extends GenericTest {

	@Autowired
	private RoleMenuFunDeptDao dao;

	@Test
	public void testFindByExample() {
		TbAuthRoleMenuFunDeptExample example = new TbAuthRoleMenuFunDeptExample();
		List<TbAuthRoleMenuFunDept> rolemenuFuns = dao.findByExample(example);
		Assert.assertNotNull(rolemenuFuns);
		Assert.assertTrue(rolemenuFuns.size() > 0);
	}

	@Test
	public void tesInsert() {
		TbAuthRoleMenuFunDept roleMenuFun = new TbAuthRoleMenuFunDept();
		roleMenuFun.setDEPT_ID("123456");
		roleMenuFun.setMD_TIME(new Date());
		roleMenuFun.setMD_USER("junit");
		roleMenuFun.setROLE_MENU_FUN_ID(BigDecimal.valueOf(248));
		int item = dao.insert(roleMenuFun);
		Assert.assertEquals(item, 1);
	}

	@Test
	public void tesDelete() {
		Date date = DateUtils.time(2012, 10, 1, 9, 30, 50);
		TbAuthRoleMenuFunDept roleMenuFun = new TbAuthRoleMenuFunDept();
		roleMenuFun.setDEPT_ID("123456");
		roleMenuFun.setMD_TIME(date);
		roleMenuFun.setMD_USER("junit");
		roleMenuFun.setROLE_MENU_FUN_ID(BigDecimal.valueOf(248));
		int item = dao.insert(roleMenuFun);
		Assert.assertEquals(item, 1);
		TbAuthRoleMenuFunDeptExample example = new TbAuthRoleMenuFunDeptExample();
		example.createCriteria().andDEPT_IDEqualTo("123456")
				.andROLE_MENU_FUN_IDEqualTo(BigDecimal.valueOf(248))
				.andMD_USEREqualTo("junit").andMD_TIMEEqualTo(date);
		item = dao.delete(example);
		Assert.assertEquals(item, 1);
	}

	@Test
	public void tesFindUsedDeptByRoleMenu() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("roleId", "116");
		map.put("menuId", "441");
		List<TbAuthRoleMenuFunDept> roleMenuFunDep = dao.findUsedDeptByRoleMenu(map);
		Assert.assertNotNull(roleMenuFunDep);
		Assert.assertTrue(roleMenuFunDep.size()>0);
	}
}
