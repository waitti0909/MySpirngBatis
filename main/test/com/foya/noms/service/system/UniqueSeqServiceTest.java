package com.foya.noms.service.system;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.noms.GenericTest;

public class UniqueSeqServiceTest extends GenericTest {

	@Autowired
	private UniqueSeqService service;
	
//	@Test
//	public void testGetNextLocationId() {
//		String result = service.getNextLocationId("B", "111");
//		Assert.assertNotNull(result);
//		Assert.assertTrue(result.length()>0);
//	
//	}
//	@Test
//	public void testGetNextNodeB4G() {
//		String result = service.getNextNodeB4G("111");
//		Assert.assertNotNull(result);
//		Assert.assertTrue(result.length()>0);
//	}
//	@Test
//	public void testGetNextSiteId() {
//		String result = service.getNextSiteId();
//		Assert.assertNotNull(result);
//		Assert.assertTrue(result.length()>0);
//	}
//	
//	@Test
//	public void testGetNextWorkId() {
//		String result = service.getNextWorkId();
//		Assert.assertNotNull(result);
//		Assert.assertTrue(result.length()>0);
//	}
//	
//	@Test
//	public void  testGetNextOrderId() {
//		String workId = service.getNextWorkId();
//		String result = service.getNextOrderId(workId);
//		Assert.assertNotNull(result);
//		Assert.assertTrue(result.length()>0);
//	}
//	
//	@Test
//	public void testGetNextOsId() {
//		String workId = service.getNextOrderId(service.getNextWorkId());
//		String result = service.getNextOsId(workId);
//		Assert.assertNotNull(result);
//		Assert.assertTrue(result.length()>0);
//	}
//
//	@Test
//	public void testGetNextSrId() {
//		String result = service.getNextSrId("B11120140001");
//		Assert.assertNotNull(result);
//		Assert.assertTrue(result.length()>0);
//	}
//	
//	
//	@Test
//	public void testGetNextLeaseId() {
//		String result = service.getNextLeaseNo("N");
//		
//		Assert.assertNotNull(result);
//		Assert.assertTrue(result.length()>0);
//	}
//	
	@Test
	public void testGetNextLeaseAppNo() {
		
//		String appNo = service.getNextLeaseNo("N");
		String result = service.getNextLeaseAppNo("RM1501001");
		
		Assert.assertNotNull(result);
		Assert.assertTrue(result.length()>0);
	}
	
	
	
	
}
