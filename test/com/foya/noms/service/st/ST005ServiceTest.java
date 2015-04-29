package com.foya.noms.service.st;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.noms.GenericTest;
import com.foya.noms.resources.AppConstants;

public class ST005ServiceTest extends GenericTest {

	@Autowired
	private ST005Service service;
	
	@Test
	public void testFinishSiteBuildOrder() {
		service.finishSiteBuildOrder("2015032500281", AppConstants.WORKFLOW_REST_APPROVAL, "SYSTEM");
	}

}
