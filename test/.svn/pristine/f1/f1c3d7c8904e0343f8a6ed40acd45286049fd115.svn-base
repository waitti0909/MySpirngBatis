package com.foya.noms.service.org;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.dao.mybatis.model.TbOrgPosition;
import com.foya.noms.GenericTest;

public class ORG001ServiceTest extends GenericTest {

	@Autowired
	private ORG001Service service;
	
	@Test
	public void testSearchPositionByCodeName(){
		List<TbOrgPosition> psnList = service.searchPositionByCodeName("junitId", "junitName");
		Assert.assertNotNull(psnList);
		Assert.assertTrue(psnList.size()>0);
		Assert.assertEquals(psnList.get(0).getPOS_NAME(), "junitName");
		Assert.assertEquals(psnList.get(0).getPOS_ID(), "junitId");
	}
	
	@Test
	public void testGetPositionById(){
		TbOrgPosition psn = service.getPositionById("junitId");
		Assert.assertNotNull(psn);
		Assert.assertEquals(psn.getPOS_ID(), "junitId");
	}
	
	
	@Test
	public void testSaveNewPosition(){
		TbOrgPosition position = new TbOrgPosition();
		position.setPOS_ID("junit_ID");
		position.setPOS_NAME("junit_NAME");
		boolean result = service.saveNewPosition(position,"junit");
		Assert.assertTrue(result);
	}
	
	@Test
	public void testSaveUpdatePosition(){
		TbOrgPosition psn = service.getPositionById("junitId");
		String name = psn.getPOS_NAME();
		String mdUser = psn.getMD_USER();
		psn.setMD_USER("user");
		psn.setPOS_NAME("juint_Name");
		boolean result = service.saveUpdatePosition(psn,"user",3);
		psn = service.getPositionById("junitId");
		Assert.assertNotNull(psn);
		Assert.assertTrue(result);
		Assert.assertEquals(psn.getPOS_NAME(), "juint_Name");
		Assert.assertEquals(psn.getMD_USER(), "user");
		psn.setMD_USER(mdUser);
		psn.setPOS_NAME(name);
		result = service.saveUpdatePosition(psn,"junit",3);
		psn = service.getPositionById("junitId");
		Assert.assertNotNull(psn);
		Assert.assertTrue(result);
		Assert.assertEquals(psn.getPOS_NAME(), name);
		Assert.assertEquals(psn.getMD_USER(), mdUser);
	}
}
