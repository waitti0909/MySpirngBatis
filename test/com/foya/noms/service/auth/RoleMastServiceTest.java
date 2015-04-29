package com.foya.noms.service.auth;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.dao.mybatis.model.TbAuthRoleMast;
import com.foya.noms.GenericTest;
import com.foya.noms.dto.auth.RoleDTO;
import com.foya.noms.dto.auth.RoleMastDTO;
import com.foya.noms.util.DateUtils;

public class RoleMastServiceTest extends GenericTest {

	@Autowired
	private RoleMastService service;
	
	@Test
	public void testGetAll() {
		List<RoleDTO> roles = service.getAll();
		Assert.assertNotNull(roles);
		Assert.assertTrue(roles.size()>0);
	}
	
	@Test
	public void testGetByRoleId() {
		TbAuthRoleMast roleMast =  service.getByRoleId(116);
		Assert.assertNotNull(roleMast);
		Assert.assertEquals(roleMast.getROLE_ID(), BigDecimal.valueOf(116));
	}
	
	@Test
	public void testGetById() {
		List<RoleMastDTO> roleMasts =service.getById(116);
		Assert.assertNotNull(roleMasts);
		Assert.assertEquals(roleMasts.size(),1);
		Assert.assertEquals(roleMasts.get(0).getRoleId(), new Integer(116));
	}		
	
	@Test
	public void testAddRole() {
		TbAuthRoleMast roleMast = new TbAuthRoleMast();
		roleMast.setROLE_NAME("roleName");
		roleMast.setROLE_DESC("roleDesc");
		service.addRole(roleMast);
		Assert.assertNotNull(roleMast);
		Assert.assertTrue(roleMast.getROLE_ID().intValue()>0);
	}
	
	@Test
	public void testEditRole(){
		TbAuthRoleMast roleMast = service.getByRoleId(116);
		Date date = DateUtils.time(2014, 9, 15, 20, 15, 20);
		String roleName = roleMast.getROLE_NAME();
		String roleDesc = roleMast.getROLE_DESC();
		Date mdTime = roleMast.getMD_TIME();
		String mdUser = roleMast.getMD_USER();
		roleMast.setROLE_NAME("roleName");
		roleMast.setROLE_DESC("roleDesc");
		roleMast.setMD_TIME(date);
		roleMast.setMD_USER("junitTest");
		service.editRole(roleMast);
		roleMast = service.getByRoleId(116);
		Assert.assertNotNull(roleMast);
		Assert.assertEquals(roleMast.getROLE_NAME(), "roleName");
		Assert.assertEquals(roleMast.getROLE_DESC(), "roleDesc");
		Assert.assertEquals(roleMast.getMD_TIME(), date);
		Assert.assertEquals(roleMast.getMD_USER(), "junitTest");
		roleMast.setROLE_NAME(roleName);
		roleMast.setROLE_DESC(roleDesc);
		roleMast.setMD_TIME(mdTime);
		roleMast.setMD_USER(mdUser);
		service.editRole(roleMast);
		roleMast = service.getByRoleId(116);
		Assert.assertNotNull(roleMast);
		Assert.assertEquals(roleMast.getROLE_NAME(), roleName);
		Assert.assertEquals(roleMast.getROLE_DESC(), roleDesc);
		Assert.assertEquals(roleMast.getMD_TIME(), mdTime);
		Assert.assertEquals(roleMast.getMD_USER(), mdUser);
	}
	
	
	@Test
	public void testDelete(){
		TbAuthRoleMast roleMast = new TbAuthRoleMast();
		roleMast.setROLE_NAME("roleName");
		roleMast.setROLE_DESC("roleDesc");
		service.addRole(roleMast);
		String[] roleIdArray = {String.valueOf(roleMast.getROLE_ID())};
		try {
			service.delete(roleIdArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void testCheckDelete() {
		boolean result = service.checkDelete(BigDecimal.valueOf(138));
		Assert.assertNotNull(result);
		Assert.assertTrue(result);
	}
}
