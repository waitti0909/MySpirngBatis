package com.foya.noms.dao.org;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.dao.mybatis.model.TbOrgPosition;
import com.foya.noms.GenericTest;
import com.foya.noms.util.DateUtils;

public class ORG001DaoTest extends GenericTest {

	@Autowired
	private ORG001Dao dao;

	@Test
	public void testGetPositionById(){
		TbOrgPosition psn = dao.getPositionById("junitId");
		Assert.assertNotNull(psn);
		Assert.assertEquals(psn.getPOS_ID(), "junitId");
	}
	
	@Test
	public void testSearchPositionByCodeName(){
		List<TbOrgPosition> psnList = dao.searchPositionByCodeName("junitId", "junitName");
		Assert.assertNotNull(psnList);
		Assert.assertTrue(psnList.size()>0);
		Assert.assertEquals(psnList.get(0).getPOS_NAME(), "junitName");
		Assert.assertEquals(psnList.get(0).getPOS_ID(), "junitId");
	}

	@Test
	public void testSaveNewPosition(){
		TbOrgPosition position = new TbOrgPosition();
		position.setPOS_ID("junit_ID");
		position.setPOS_NAME("junit_NAME");
		int item = dao.saveNewPosition(position);
		Assert.assertEquals(item, 1);
	}
	
	@Test
	public void testSaveUpdatePosition(){
		TbOrgPosition psn = dao.getPositionById("junitId");
		Date date = DateUtils.time(2014, 9, 30, 5, 17, 50);
		String name = psn.getPOS_NAME();
		Date mdTime = psn.getMD_TIME();
		String mdUser = psn.getMD_USER();
		psn.setMD_TIME(date);
		psn.setMD_USER("user");
		psn.setPOS_NAME("juint_Name");
		int item = dao.saveUpdatePosition(psn);
		psn = dao.getPositionById("junitId");
		Assert.assertNotNull(psn);
		Assert.assertEquals(item, 1);
		Assert.assertEquals(psn.getPOS_NAME(), "juint_Name");
		Assert.assertEquals(psn.getMD_USER(), "user");
		Assert.assertEquals(psn.getMD_TIME(), date);
		psn.setMD_TIME(mdTime);
		psn.setMD_USER(mdUser);
		psn.setPOS_NAME(name);
		item = dao.saveUpdatePosition(psn);
		psn = dao.getPositionById("junitId");
		Assert.assertNotNull(psn);
		Assert.assertEquals(item, 1);
		Assert.assertEquals(psn.getPOS_NAME(), name);
		Assert.assertEquals(psn.getMD_USER(), mdUser);
		Assert.assertEquals(psn.getMD_TIME(), mdTime);
	}
}
