package com.foya.noms.util;

import java.net.URI;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;

import com.foya.exception.NomsException;
import com.foya.noms.resources.AppConstants;

public class RestClient {

	private String url;
	// example : noms
	private String contextPath;
	private HttpMethod method = HttpMethod.GET;
	private String loginPath = "loginProcess";
	private String logoutPath = "logout";
	private final String usernameInputFieldName = "j_username";
	private final String passwordInputFieldName = "j_password";
	private StatefullRestTemplate template = null;

	public RestClient(String url) {
		if (StringUtils.isBlank(url)) {
			throw new NomsException("Error! url is blank");
		}
		if (url.toLowerCase().indexOf("https") > -1) {
			template = new StatefullRestTemplate(true);
		} else {
			template = new StatefullRestTemplate(false);
		}
		this.url = url;
	}

	/**
	 * This method logs into a service by doing an standard http using the configuration in this
	 * class.
	 * 
	 * @param username the username to log into the application with
	 * @param password the password to log into the application with
	 * 
	 * @return the url that the login redirects to
	 */
	public String login(String username, String password) {
		MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
		form.add(usernameInputFieldName, username);
		form.add(passwordInputFieldName, password);
		// example : http://localhost:8080/noms/loginProcess/
		String loginUrl = getUrl() + "/" + getContextPath() + "/" + getLoginPath() + "/";
		URI location = this.template.postForLocation(loginUrl, form);
		return location.toString();
	}

	/**
	 * Logout by doing an http get on the logout url
	 * 
	 * @return result of the get as ResponseEntity
	 */
	public ResponseEntity<String> logout() {
		// example : http://localhost:8080/noms/logout/
		String logoutUrl = getUrl() + "/" + getContextPath() + "/" + getLogoutPath() + "/";
		return this.template.getForEntity(logoutUrl, String.class);
	}

	public ResponseEntity<String> exchange(String restImpUri, MultiValueMap<String, String> paramMap) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(
				paramMap, headers);
		// example : http://localhost:8080/noms/st/st002/doFinalApply
		String actionUrl = getUrl() + "/" + getContextPath() + restImpUri;
		return template.exchange(actionUrl, getMethod(), entity, String.class, paramMap);
	}

	public StatefullRestTemplate template() {
		return template;
	}

	public String applicationUrl() {
		return getUrl() + "/" + (contextPath);
	}

	public void setLogoutPath(String logoutPath) {
		this.logoutPath = logoutPath;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public String getLoginPath() {
		return loginPath;
	}

	public void setLoginPath(String loginPath) {
		this.loginPath = loginPath;
	}

	public String getLogoutPath() {
		return logoutPath;
	}

	public String getUrl() {
		return url;
	}

	public HttpMethod getMethod() {
		return method;
	}

	public void setMethod(HttpMethod method) {
		this.method = method;
	}

	public static void main(String args[]) {
		try {
			// example :
			// http://localhost:8080/noms/flowActionRest?type=SiteBuildMaterialApply&docNo=201503210008&action=APPROVAL
			String url = AppConstants.NOMS_HOST;
			RestClient client = new RestClient(url);
			client.setLoginPath("loginProcess");
			client.setContextPath("noms");
			client.setMethod(HttpMethod.POST);

			String loginResult = client.login("SYSTEM", "1");
			System.out.println("loginResult = " + loginResult);
			MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<String, String>();
			paramMap.add("orderId", "201503190005");
			paramMap.add("type", "SearchSiteApplySH");
			paramMap.add("action", AppConstants.WORKFLOW_REST_APPROVAL);
			ResponseEntity<String> respEntity = client.exchange("/st/st002/doFinalApply", paramMap);

			System.out.println("respEntity = " + respEntity.getBody());
		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
