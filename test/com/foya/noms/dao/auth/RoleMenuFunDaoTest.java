package com.foya.noms.dao.auth;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.dao.mybatis.mapper.TbAuthRoleMenuFunMapper;
import com.foya.dao.mybatis.model.TbAuthRoleMenuFun;
import com.foya.dao.mybatis.model.TbAuthRoleMenuFunExample;
import com.foya.noms.GenericTest;
import com.foya.noms.dto.auth.RoleMenuFunDTO;
import com.foya.noms.dto.auth.RoleMenuFunDepDTO;
import com.foya.noms.util.DateUtils;

public class RoleMenuFunDaoTest extends GenericTest {

	@Autowired
	private RoleMenuFunDao dao;
	@Autowired
	private TbAuthRoleMenuFunMapper mapper;
	@Test
	public void testFindFunctionByPsnAndMenuId() {
		Map<String, Integer> condition = new HashMap<String, Integer>();
		condition.put("psnId", 3);
		condition.put("menuId", 441);
		List<RoleMenuFunDTO> roleFuns = dao
				.findFunctionByPsnAndMenuId(condition);
		Assert.assertNotNull(roleFuns);
		Assert.assertTrue(roleFuns.size() > 0);
	}

	@Test
	public void testFindRoleMenuFunByMenuId() {
		List<RoleMenuFunDepDTO> roleFuns = dao.findRoleMenuFunByMenuId(441);
		Assert.assertNotNull(roleFuns);
		Assert.assertEquals(roleFuns.size(), 1);
		Assert.assertEquals(roleFuns.get(0).getMenuId(), new Integer(441));
	}

	@Test
	public void testFindRoleMenuFunByRoleId() {
		List<RoleMenuFunDepDTO> roleFuns = dao.findRoleMenuFunByRoleId(116);
		Assert.assertNotNull(roleFuns);
		Assert.assertTrue(roleFuns.size() > 0);
		Assert.assertEquals(roleFuns.get(0).getRoleId(), new Integer(116));

	}

	@Test
	public void testFindFuncDeptByRoleAndMenuForGrid() {
		HashMap<String, Integer> condition = new HashMap<String, Integer>();
		condition.put("roleId", 116);
		condition.put("menuId", 441);
		List<RoleMenuFunDepDTO> roleFuns = dao
				.findFuncDeptByRoleAndMenuForGrid(condition);
		Assert.assertNotNull(roleFuns);
		Assert.assertTrue(roleFuns.size() > 0);
		Assert.assertEquals(roleFuns.get(0).getMenuId(), new Integer(441));
	}
	
	@Test
	public void testFindFuncDeptByRoleAndMenu() {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("roleId", 116);
		map.put("menuId", 441);
		List<RoleMenuFunDepDTO> roleFuns  = dao.findFuncDeptByRoleAndMenu(map);
		Assert.assertNotNull(roleFuns);
		Assert.assertTrue(roleFuns.size() > 0);
		Assert.assertEquals(roleFuns.get(0).getMenuId(), new Integer(441));
	}
	
	@Test
	public void testFindRoleMenuByMenuId(){
		List<RoleMenuFunDepDTO> roleFun =dao.findRoleMenuByMenuId(441);
		Assert.assertNotNull(roleFun);
		Assert.assertTrue(roleFun.size() > 0);
		Assert.assertEquals(roleFun.get(0).getMenuId(), new Integer(441));
	}
	
	@Test
	public void testSelectRoleMenuFunByMenuId(){
		List<RoleMenuFunDTO> roleFuns = dao.selectRoleMenuFunByMenuId(441);
		Assert.assertNotNull(roleFuns);
		Assert.assertTrue(roleFuns.size() > 0);
	}
	
	@Test
	public void testInsert() {
		TbAuthRoleMenuFun roleMenuFun = new TbAuthRoleMenuFun();
		roleMenuFun.setDEP_ID(null);
		roleMenuFun.setMENU_FUN_ID(BigDecimal.valueOf(699));
		roleMenuFun.setROLE_ID(BigDecimal.valueOf(116));
		roleMenuFun.setMENU_ID(BigDecimal.valueOf(441));
		int item = dao.insert(roleMenuFun);
		Assert.assertEquals(item, 1);
		Assert.assertTrue(roleMenuFun.getROLE_MENU_FUN_ID().intValue() > 0);
	}

	@Test
	public void testDeleteByRoleMenuFunId() {
		TbAuthRoleMenuFun roleMenuFun = new TbAuthRoleMenuFun();
		roleMenuFun.setDEP_ID(null);
		roleMenuFun.setMENU_FUN_ID(BigDecimal.valueOf(699));
		roleMenuFun.setROLE_ID(BigDecimal.valueOf(116));
		roleMenuFun.setMENU_ID(BigDecimal.valueOf(441));
		dao.insert(roleMenuFun);
		int item = dao.delete(roleMenuFun.getROLE_MENU_FUN_ID());
		Assert.assertEquals(item, 1);
	}

	@Test
	public void testDelete() {
		TbAuthRoleMenuFun roleMenuFun = new TbAuthRoleMenuFun();
		roleMenuFun.setDEP_ID(null);
		roleMenuFun.setMENU_FUN_ID(BigDecimal.valueOf(699));
		roleMenuFun.setROLE_ID(BigDecimal.valueOf(116));
		roleMenuFun.setMENU_ID(BigDecimal.valueOf(442));
		dao.insert(roleMenuFun);
		TbAuthRoleMenuFunExample roleMenuFunExample = new TbAuthRoleMenuFunExample();
		roleMenuFunExample.createCriteria().andROLE_MENU_FUN_IDEqualTo(roleMenuFun.getROLE_MENU_FUN_ID());
		int item = dao.delete(roleMenuFunExample);
		Assert.assertEquals(item, 1);
	}

	@Test
	public void testUpdate() {
		TbAuthRoleMenuFun roleMenuFun = mapper.selectByPrimaryKey(BigDecimal.valueOf(246));
		BigDecimal depId = roleMenuFun.getDEP_ID();
		Date mdTime = roleMenuFun.getMD_TIME();
		String mdUser = roleMenuFun.getMD_USER();
		BigDecimal menuFunId = roleMenuFun.getMENU_FUN_ID();
		BigDecimal menuId = roleMenuFun.getMENU_ID();
		BigDecimal roleId = roleMenuFun.getROLE_ID();
		Date date = DateUtils.time(2014, 9, 12, 5, 12, 50);
		roleMenuFun.setDEP_ID(null);
		roleMenuFun.setMD_TIME(date);
		roleMenuFun.setMD_USER("junit");
		roleMenuFun.setMENU_FUN_ID(BigDecimal.valueOf(699));
		roleMenuFun.setROLE_ID(BigDecimal.valueOf(116));
		roleMenuFun.setMENU_ID(BigDecimal.valueOf(442));
		int item = dao.update(roleMenuFun);
		roleMenuFun = mapper.selectByPrimaryKey(BigDecimal.valueOf(246));
		Assert.assertNotNull(roleMenuFun);
		Assert.assertEquals(item, 1);
		Assert.assertEquals(roleMenuFun.getDEP_ID(), null);
		Assert.assertEquals(roleMenuFun.getMD_TIME(), date);
		Assert.assertEquals(roleMenuFun.getMD_USER(), "junit");
		Assert.assertEquals(roleMenuFun.getMENU_FUN_ID(), BigDecimal.valueOf(699));
		Assert.assertEquals(roleMenuFun.getROLE_ID(), BigDecimal.valueOf(116));
		Assert.assertEquals(roleMenuFun.getMENU_ID(), BigDecimal.valueOf(442));
		roleMenuFun.setDEP_ID(depId);
		roleMenuFun.setMD_TIME(mdTime);
		roleMenuFun.setMD_USER(mdUser);
		roleMenuFun.setMENU_FUN_ID(menuFunId);
		roleMenuFun.setROLE_ID(roleId);
		roleMenuFun.setMENU_ID(menuId);
		item = dao.update(roleMenuFun);
		roleMenuFun = mapper.selectByPrimaryKey(BigDecimal.valueOf(246));
		Assert.assertNotNull(roleMenuFun);
		Assert.assertEquals(item, 1);
		Assert.assertEquals(roleMenuFun.getDEP_ID(), depId);
		Assert.assertEquals(roleMenuFun.getMD_TIME(), mdTime);
		Assert.assertEquals(roleMenuFun.getMD_USER(), mdUser);
		Assert.assertEquals(roleMenuFun.getMENU_FUN_ID(), menuFunId);
		Assert.assertEquals(roleMenuFun.getROLE_ID(), roleId);
		Assert.assertEquals(roleMenuFun.getMENU_ID(), menuId);
	}
	
	@Test
	public void testSelectByExample() {
		TbAuthRoleMenuFunExample example = new TbAuthRoleMenuFunExample();
		example.createCriteria().andMENU_IDEqualTo(BigDecimal.valueOf(441));
		List<TbAuthRoleMenuFun> roleMenuFun = dao.selectByExample(example);
		Assert.assertNotNull(roleMenuFun);
		Assert.assertTrue(roleMenuFun.size()>0);
		Assert.assertEquals(roleMenuFun.get(0).getMENU_ID(),BigDecimal.valueOf(441));
	}
}