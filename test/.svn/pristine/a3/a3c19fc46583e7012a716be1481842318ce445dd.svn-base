package com.foya.noms.dao.auth;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.dao.mybatis.model.TbAuthPsnRole;
import com.foya.noms.GenericTest;

public class PsnRoleDaoTest extends GenericTest {

	@Autowired
	private PsnRoleDao dao;
	
	@Test
	public void testSelectAllPsnRole(){
		List<TbAuthPsnRole> psnRole = dao.selectAllPsnRole();
		Assert.assertNotNull(psnRole);
		Assert.assertTrue(psnRole.size()>0);
	}
	
	@Test 
	public void testAddUserRole(){
		TbAuthPsnRole psnRole= new TbAuthPsnRole();
		psnRole.setROLE_ID(BigDecimal.valueOf(138));
		psnRole.setPSN_ID(BigDecimal.valueOf(3));
		psnRole.setMD_USER("juintTest");
		psnRole.setMD_TIME(new Date());
		dao.addUserRole(psnRole);
		List<TbAuthPsnRole> psnRoleList =dao.searchUserByRole(BigDecimal.valueOf(138));
		Assert.assertNotNull(psnRoleList);
		Assert.assertEquals(psnRoleList.get(0).getPSN_ID(), BigDecimal.valueOf(3));
	}
	
	@Test
	public void testSearchUserByRole(){
		List<TbAuthPsnRole> psnRole =dao.searchUserByRole(BigDecimal.valueOf(116));
		Assert.assertNotNull(psnRole);
		Assert.assertTrue(psnRole.size()>0);
	}
	
	@Test
	public void testRemoveUserRole(){
		TbAuthPsnRole psnRole= new TbAuthPsnRole();
		psnRole.setROLE_ID(BigDecimal.valueOf(138));
		psnRole.setPSN_ID(BigDecimal.valueOf(3));
		psnRole.setMD_USER("junit");
		psnRole.setMD_TIME(new Date());
		dao.addUserRole(psnRole);
		TbAuthPsnRole key = new TbAuthPsnRole();
		key.setPSN_ID(BigDecimal.valueOf(3));
		key.setROLE_ID(BigDecimal.valueOf(138));
		dao.removeUserRole(key);
	}
}
