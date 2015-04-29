package com.foya.noms.service.inv;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.noms.GenericTest;

/**
 * 供站點呼叫之Service Junit
 */
public class InvServiceTest extends GenericTest {

	@Autowired
	private INVService invService;

	@Test
	public void testSetup() {
		boolean ifSuccess = invService
				.setup("P","T002","L001" ,"201411270014", "1", "M001", 2, "F005", 5, "inv1","inv1");
		Assert.assertTrue(ifSuccess);
	}

	@Test
	public void testUnload() {
		boolean ifSuccess = invService.unload("P","T003", "201411270014", "201412130001", "M005", 2,
				"F003", 5, 2, "inv1");
		Assert.assertTrue(ifSuccess);
	}

	@Test
	public void testCompletion() {
		boolean ifSuccess = invService.completion("T", "2014112500071", "LOC002", "T201420141",
				 "inv1", "inv1", new Date(), new BigDecimal("10000"), "RCV005", "OS003");
		Assert.assertTrue(ifSuccess);
	}

}
