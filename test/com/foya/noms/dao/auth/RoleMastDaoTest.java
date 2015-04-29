package com.foya.noms.dao.auth;

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

public class RoleMastDaoTest extends GenericTest {

	@Autowired
	private RoleMastDao dao;
	
	@Test
	public void testSelectByUserId() {
		List<RoleDTO> roles = dao.selectByUserId(3);
		Assert.assertNotNull(roles);
		Assert.assertTrue(roles.size()>0);
	}
	
	@Test
	public void testSelectAll(){
		List<RoleDTO> roles = dao.selectAll();
		Assert.assertNotNull(roles);
		Assert.assertTrue(roles.size()>0);
	}
	
	@Test
	public void testSelectByRoleId(){
		TbAuthRoleMast role = dao.selectByRoleId(BigDecimal.valueOf(116));
		Assert.assertNotNull(role);
		Assert.assertEquals(role.getROLE_ID(),BigDecimal.valueOf(116));
	}
		
	@Test 
	public void testSelectById(){	
		List<RoleMastDTO> roles = dao.selectById(116);
		Assert.assertNotNull(roles);
		Assert.assertEquals(roles.get(0).getRoleId(),new Integer(116));
	}

		
	@Test
	public void testInsert() {
		TbAuthRoleMast role =new TbAuthRoleMast();
		role.setROLE_NAME("test");
		role.setROLE_DESC("test");
		dao.insert(role);
		Assert.assertNotNull(role);
		Assert.assertTrue(role.getROLE_ID().intValue()>0);
	}
	
	@Test
	public void testUpdate(){
		TbAuthRoleMast role =dao.selectByRoleId(new BigDecimal(116));
		String roleName = role.getROLE_NAME();
		String roleDesc = role.getROLE_DESC();
		String mdUser = role.getMD_USER();
		Date mdTime =role.getMD_TIME();
		Date date =DateUtils.time(2014, 9, 20, 12, 51, 20);
		role.setROLE_NAME("test_update");
		role.setROLE_DESC("test_update");
		role.setMD_USER("junit");
		role.setMD_TIME(date);
		dao.update(role);
		role =dao.selectByRoleId(new BigDecimal(116));
		Assert.assertNotNull(role);
		Assert.assertEquals(role.getROLE_NAME(), "test_update");
		Assert.assertEquals(role.getROLE_DESC(), "test_update");
		Assert.assertEquals(role.getMD_USER(), "junit");
		Assert.assertEquals(role.getMD_TIME(), date);
		role.setROLE_NAME(roleName);
		role.setROLE_DESC(roleDesc);
		role.setMD_USER(mdUser);
		role.setMD_TIME(mdTime);
		dao.update(role);
		role =dao.selectByRoleId(new BigDecimal(116));
		Assert.assertNotNull(role);
		Assert.assertEquals(role.getROLE_NAME(), roleName);
		Assert.assertEquals(role.getROLE_DESC(), roleDesc);
		Assert.assertEquals(role.getMD_USER(), mdUser);
		Assert.assertEquals(role.getMD_TIME(), mdTime);
	}
	
	@Test
	public void testdelete() {
		TbAuthRoleMast role =new TbAuthRoleMast();
		role.setROLE_NAME("test");
		role.setROLE_DESC("test");
		dao.insert(role);
		dao.delete(role.getROLE_ID());
	}

}
