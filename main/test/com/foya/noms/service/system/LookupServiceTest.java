package com.foya.noms.service.system;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.dao.mybatis.model.TbSysLookup;
import com.foya.noms.GenericTest;

public class LookupServiceTest extends GenericTest {

	@Autowired
	private LookupService service;
	
	@Test
	public void testGetFileTypeKeyValues() {
		Map<String ,String> map = service.getFileTypeKeyValues("FILETYPE", "BULLETIN");
		Assert.assertNotNull(map);
		Assert.assertTrue(map.size()>0);
	}
	@Test
	public void testGetByTypeAndCode() {
		TbSysLookup sysLookup = service.getByTypeAndCode("FILETYPE", "TESTTYPE");
		Assert.assertNotNull(sysLookup);
		Assert.assertEquals(sysLookup.getCODE(), "TESTTYPE");
		Assert.assertEquals(sysLookup.getTYPE(), "FILETYPE");
	}
}
