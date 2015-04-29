package com.foya.noms.service.auth;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.dao.mybatis.mapper.TbAuthRoleMenuMapper;
import com.foya.dao.mybatis.model.TbAuthRoleMenu;
import com.foya.dao.mybatis.model.TbAuthRoleMenuKey;
import com.foya.noms.GenericTest;
import com.foya.noms.dto.auth.RoleMenuFunDepDTO;

public class RoleMenuServiceTest extends GenericTest {

	@Autowired
	private RoleMenuService service;
	@Autowired
	private TbAuthRoleMenuMapper tbAuthRoleMenuMapper;
	@Test
	public void testSelectRoleMenuFunDepDTOByRoleId(){
		List<RoleMenuFunDepDTO> roleMenus =service.selectRoleMenuFunDepDTOByRoleId(116);
		Assert.assertNotNull(roleMenus);
		Assert.assertTrue(roleMenus.size()>0);
		Assert.assertEquals(roleMenus.get(0).getRoleId(),new Integer(116));
	}
	
	@Test
	public void testSelectRoleMenuFunDepDTOByMenuId(){
		List<RoleMenuFunDepDTO> roleMenus =service.selectRoleMenuFunDepDTOByMenuId(441);
		Assert.assertNotNull(roleMenus);
		Assert.assertEquals(roleMenus.size(),1);
		Assert.assertEquals(roleMenus.get(0).getMenuId(),new Integer(441));
	}
	
	@Test
	public void testSearchNonOwnedMenuByRoleId(){
		List<RoleMenuFunDepDTO> roleMenus =service.searchNonOwnedMenuByRoleId(116);
		Assert.assertNotNull(roleMenus);
		Assert.assertTrue(roleMenus.size()>0);
	}
	
	@Test
	public void testSearchNonOwnedRoleByMenuId(){
		List<RoleMenuFunDepDTO> roleMenus =service.searchNonOwnedRoleByMenuId(116);
		Assert.assertNotNull(roleMenus);
		Assert.assertTrue(roleMenus.size()>0);
	}
	
	@Test
	public void testGetSelectedMenuByRoleId(){
		List<RoleMenuFunDepDTO> roleMenus =service.getSelectedMenuByRoleId(116);
		Assert.assertNotNull(roleMenus);
		Assert.assertTrue(roleMenus.size()>0);
	}
	
	@Test
	public void testSaveRoleMenu() {
		List<TbAuthRoleMenu> list = new ArrayList<TbAuthRoleMenu>();
		TbAuthRoleMenu rolemenu = new TbAuthRoleMenu();
		rolemenu.setMENU_ID(BigDecimal.valueOf(440));
		rolemenu.setROLE_ID(BigDecimal.valueOf(116));
		list.add(rolemenu);
		rolemenu = new TbAuthRoleMenu();
		rolemenu.setMENU_ID(BigDecimal.valueOf(441));
		rolemenu.setROLE_ID(BigDecimal.valueOf(116));
		list.add(rolemenu);
		service.saveRoleMenu(list);
		TbAuthRoleMenuKey key =new TbAuthRoleMenuKey();
		key.setMENU_ID(BigDecimal.valueOf(440));
		key.setROLE_ID(BigDecimal.valueOf(116));
		TbAuthRoleMenu roleMenu =tbAuthRoleMenuMapper.selectByPrimaryKey(key);
		Assert.assertEquals(roleMenu.getMENU_ID(),BigDecimal.valueOf(440));
		Assert.assertEquals(roleMenu.getROLE_ID(),BigDecimal.valueOf(116));
		key.setMENU_ID(BigDecimal.valueOf(441));
		key.setROLE_ID(BigDecimal.valueOf(116));
		roleMenu =tbAuthRoleMenuMapper.selectByPrimaryKey(key);
		Assert.assertEquals(roleMenu.getMENU_ID(),BigDecimal.valueOf(441));
		Assert.assertEquals(roleMenu.getROLE_ID(),BigDecimal.valueOf(116));
	}
	
	@Test
	public void testDelete() {
		List<TbAuthRoleMenu> list = new ArrayList<TbAuthRoleMenu>();
		TbAuthRoleMenu rolemenu = new TbAuthRoleMenu();
		rolemenu.setMENU_ID(BigDecimal.valueOf(440));
		rolemenu.setROLE_ID(BigDecimal.valueOf(116));
		list.add(rolemenu);
		service.saveRoleMenu(list);
		TbAuthRoleMenuKey key =new TbAuthRoleMenuKey();
		key.setMENU_ID(BigDecimal.valueOf(440));
		key.setROLE_ID(BigDecimal.valueOf(116));
		List<TbAuthRoleMenuKey> keys = new ArrayList<TbAuthRoleMenuKey>();
		keys.add(key);
		service.delete(keys);
	}

}
