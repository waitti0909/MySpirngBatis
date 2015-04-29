package com.foya.noms.service.st;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.foya.noms.GenericTest;
import com.foya.noms.resources.AppConstants;
import com.foya.noms.util.RestClient;

public class ST004ServiceTest extends GenericTest {

	@Autowired
	private ST004Service service;

	@Test
	public void testFeedbackFromPayment() {
		log.debug("start");
		RestClient client = new RestClient("http://127.0.0.1:8080");
		client.setLoginPath("loginProcess");
		client.setContextPath("noms");
		client.setMethod(HttpMethod.POST);
		client.login("P14075225", "1");

		MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<String, String>();
		paramMap.add("orderId", "201501160006");
		paramMap.add("type", "SearchSiteApplySH");
		paramMap.add("action", AppConstants.WORKFLOW_REST_APPROVAL);
		client.exchange("/st/st004/doFinalApply", paramMap);
		log.debug("end");

	}

}
