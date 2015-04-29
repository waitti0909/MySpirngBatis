package com.foya.noms.service.auth;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.dao.mybatis.model.TbAuthPersonnel;
import com.foya.noms.GenericTest;

public class PersonnelServiceTest extends GenericTest {

	@Autowired
	private PersonnelService service;
	
	@Test
	public void testGetAllPsn() {
		List<TbAuthPersonnel> psns = service.getAllPsn();
		Assert.assertNotNull(psns);
		Assert.assertTrue(psns.size()>0);
	}
	
}
