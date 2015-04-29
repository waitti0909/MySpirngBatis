package com.foya.noms.service.auth;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.dao.mybatis.model.TbAuthPsnRole;
import com.foya.dao.mybatis.model.TbAuthRoleMast;
import com.foya.noms.GenericTest;

public class PsnRoleServiceTest extends GenericTest {

	@Autowired
	private PsnRoleService service;
	
	@Test
	public void testGetAllPsnRole() {
		 List<TbAuthPsnRole> psnRole = service.getAllPsnRole();
		 Assert.assertNotNull(psnRole);
		 Assert.assertTrue(psnRole.size()>0);
	}
	
	@Test
	public void testAddPsnRole() {
		TbAuthRoleMast roleMast = new TbAuthRoleMast();
		roleMast.setROLE_ID(BigDecimal.valueOf(116));
		roleMast.setROLE_NAME("testRoleName");
		roleMast.setROLE_DESC("testRoleDesc");
		String[] userIdArray = {"1","2"};
		String currentUserName = "junitTest";
		service.addPsnRole(roleMast, userIdArray, currentUserName);
		List<TbAuthPsnRole> psnRole =service.getUserByRole(BigDecimal.valueOf(116));
		Assert.assertTrue(psnRole.size()==2);
		
	}
	
	@Test
	public  void testGetUserByRole() {
		List<TbAuthPsnRole> psnRole =service.getUserByRole(BigDecimal.valueOf(116));
		Assert.assertNotNull(psnRole);
		Assert.assertTrue(psnRole.size()>0);
		Assert.assertEquals(psnRole.get(0).getPSN_ID(), BigDecimal.valueOf(3));
	}
	
}
