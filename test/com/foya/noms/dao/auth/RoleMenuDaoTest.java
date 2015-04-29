package com.foya.noms.dao.auth;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.dao.mybatis.model.TbAuthRoleMenu;
import com.foya.dao.mybatis.model.TbAuthRoleMenuKey;
import com.foya.noms.GenericTest;
import com.foya.noms.dto.auth.RoleMenuFunDepDTO;

public class RoleMenuDaoTest extends GenericTest {

	@Autowired
	private RoleMenuDao dao;
	
	@Test  
	public void testSelectRoleMenuByRoleId(){
		List<RoleMenuFunDepDTO> roles = dao.selectRoleMenuByRoleId(116);
		Assert.assertNotNull(roles);
		Assert.assertTrue(roles.size()>0);
		Assert.assertEquals(roles.get(0).getRoleId(), new Integer(116));
	}
	
	@Test
	public void testSelectRoleMenuByMenuId(){
		List<RoleMenuFunDepDTO> role =dao.selectRoleMenuByMenuId(440);
		Assert.assertNotNull(role);
		Assert.assertTrue(role.size()>0);
		Assert.assertEquals(role.get(0).getMenuId(),new Integer(440));
	}
	
	
	@Test
	public void testSearchNonOwnedMenuByRoleId(){
		List<RoleMenuFunDepDTO> role =dao.searchNonOwnedMenuByRoleId(116);
		Assert.assertNotNull(role);
		Assert.assertTrue(role.size()>0);
	}
	
	
	@Test 
	public void testSearchNonOwnedRoleByMenuId(){
		List<RoleMenuFunDepDTO> role =dao.searchNonOwnedRoleByMenuId(386);
		Assert.assertNotNull(role);
		Assert.assertTrue(role.size()>0);
	}
	
	@Test
	public void testFindSelectedMenuByRoleId(){
		List<RoleMenuFunDepDTO> role =dao.findSelectedMenuByRoleId(116);
		Assert.assertNotNull(role);
		Assert.assertTrue(role.size()>0);
	}
	
	@Test
	public void testInsertAddRoleMenu(){
		TbAuthRoleMenu roleMenu = new TbAuthRoleMenu();
		roleMenu.setROLE_ID(BigDecimal.valueOf(116));
		roleMenu.setMENU_ID(BigDecimal.valueOf(501));
		int item = dao.insertAddRoleMenu(roleMenu);
		Assert.assertNotNull(roleMenu);
		Assert.assertEquals(item, 0);
	}
	@Test
	public void testDelete() {
		TbAuthRoleMenu roleMenu = new TbAuthRoleMenu();
		roleMenu.setROLE_ID(BigDecimal.valueOf(116));
		roleMenu.setMENU_ID(BigDecimal.valueOf(501));
		dao.insertAddRoleMenu(roleMenu);
		TbAuthRoleMenuKey key = new TbAuthRoleMenuKey();
		key.setMENU_ID(BigDecimal.valueOf(501));
		key.setROLE_ID(BigDecimal.valueOf(116));
		dao.delete(key);
	}
}