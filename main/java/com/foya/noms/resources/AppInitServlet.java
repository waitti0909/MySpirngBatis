package com.foya.noms.resources;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import javax.servlet.http.HttpServlet;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 透過properties初始化系統常數
 * 
 * @author Charlie Woo
 * 
 */
@SuppressWarnings("serial")
public class AppInitServlet extends HttpServlet {

	protected transient Logger log = LoggerFactory.getLogger(getClass());

	public void init() {

		log.info("read system.properties...");
		Properties systemConfig = new Properties();
		try {
			systemConfig.load(Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("system.properties"));

			initEnvironment(systemConfig);
			initPwdConfig(systemConfig);
			initMailConfig(systemConfig);
			initWorkflowConfig(systemConfig);
		} catch (IOException e) {
			log.error("Initial IO Error :: " + ExceptionUtils.getFullStackTrace(e));
		} catch (Exception ex) {
			log.error("Initial Error :: " + ExceptionUtils.getFullStackTrace(ex));
		}
	}

	private void initEnvironment(Properties systemConfig) {
		AppConstants.ENVIRONMENT = String.valueOf(systemConfig.getProperty("environment").toUpperCase());
		AppConstants.NOMS_HOST = String.valueOf(systemConfig.getProperty("nomsHost"));
		AppConstants.WebRealPathRoot = getWebRealPath();
		log.info("======= initRepottsPsth =======");
		log.info("AppConstants.Environment........" + AppConstants.ENVIRONMENT);
		log.info("AppConstants.NOMS_HOST........" + AppConstants.NOMS_HOST);
		log.info("AppConstants.WebRealPathRoot........" + AppConstants.WebRealPathRoot);
		log.info("======= initRepottsPsth =======");
	}

	private void initMailConfig(Properties systemConfig) {
		AppConstants.MAIL_SMTP_HOST = String.valueOf(systemConfig.getProperty("host"));
		AppConstants.MAIL_SMTP_PROTOCAL = String.valueOf(systemConfig.getProperty("protocol"));
		AppConstants.MAIL_SMTP_AUTHENTICATION = Boolean.valueOf(systemConfig.getProperty("isAuth"));
		AppConstants.MAIL_SMTP_AUTH_USER = String.valueOf(systemConfig.getProperty("user"));
		AppConstants.MAIL_SMTP_AUTH_PASSWORD = String.valueOf(systemConfig
				.getProperty("password"));
		AppConstants.MAIL_FROM = String.valueOf(systemConfig.getProperty("mailFrom"));

		String mailToIt = String.valueOf(systemConfig.getProperty("mailToIT"));
		AppConstants.MAIL_TO_IT = new ArrayList<String>();
		if (StringUtils.contains(mailToIt, ";")) {
			for (String mailTo : StringUtils.split(mailToIt, ";")) {
				AppConstants.MAIL_TO_IT.add(mailTo);
			}
		} else {
			AppConstants.MAIL_TO_IT.add(mailToIt);
		}

		String ccToIt = String.valueOf(systemConfig.getProperty("ccToIT"));
		AppConstants.CC_TO_IT = new ArrayList<String>();
		if (StringUtils.contains(ccToIt, ";")) {
			for (String mailTo : StringUtils.split(ccToIt, ";")) {
				AppConstants.CC_TO_IT.add(mailTo);
			}
		} else {
			AppConstants.CC_TO_IT.add(ccToIt);
		}
		
		log.info("======= Initialing system.properties for Mail =======");
		log.info("AppConstants.MAIL_SMTP_HOST........" + AppConstants.MAIL_SMTP_HOST);
		log.info("AppConstants.MAIL_SMTP_PROTOCAL........" + AppConstants.MAIL_SMTP_PROTOCAL);
		log.info("AppConstants.MAIL_SMTP_AUTHENTICATION............" + AppConstants.MAIL_SMTP_AUTHENTICATION);
		log.info("AppConstants.MAIL_SMTP_AUTH_USER........" + AppConstants.MAIL_SMTP_AUTH_USER);
		log.info("AppConstants.MAIL_FROM........." + AppConstants.MAIL_FROM);
		log.info("AppConstants.MAIL_TO_IT........." + AppConstants.MAIL_TO_IT);
		log.info("======= Initialing system.properties =======");
	}

	private void initWorkflowConfig(Properties systemConfig) {
		AppConstants.WORKFLOW_REST_HOST = String.valueOf(systemConfig
				.getProperty("workflow.restHost"));
		AppConstants.WORKFLOW_FAIL_HANDLER = String.valueOf(systemConfig
				.getProperty("workflow.failHandler"));
		AppConstants.WORKFLOW_FAIL_HANDLER_TASK_NAME = String.valueOf(systemConfig
				.getProperty("workflow.failHandler.taskName"));
		log.info("======= init workflow config =======");
		log.info("AppConstants.WORKFLOW_REST_HOST........" + AppConstants.WORKFLOW_REST_HOST);
		log.info("AppConstants.WORKFLOW_FAIL_HANDLER........" + AppConstants.WORKFLOW_FAIL_HANDLER);
		log.info("AppConstants.WORKFLOW_FAIL_HANDLER_TASK_NAME........"
				+ AppConstants.WORKFLOW_FAIL_HANDLER_TASK_NAME);

	}

	private void initPwdConfig(Properties systemConfig) {
		AppConstants.PWD_MIN_LENGTH = Integer.valueOf(systemConfig.getProperty("pwdMinLength"));
		AppConstants.PWD_MAX_LENGTH = Integer.valueOf(systemConfig.getProperty("pwdMaxLength"));
		AppConstants.PWD_PERIOD = Integer.valueOf(systemConfig.getProperty("pwdPeriod"));
		AppConstants.PWD_KEEP_TIMES = Integer.valueOf(systemConfig.getProperty("pwdKeepTimes"));
		AppConstants.PWD_ERR_TIMES = Integer.valueOf(systemConfig.getProperty("pwdErrTimes"));
		AppConstants.PWD_MIX_ENG_NUM = Boolean.valueOf(systemConfig.getProperty("pwdMixEngNum"));
	}
	
	private String getWebRealPath() {
		String webRealPath = getServletContext().getRealPath("/");
		if ( StringUtils.endsWith(webRealPath, File.separator) ) {
			webRealPath = webRealPath.substring(0, webRealPath.length() - 1);
		}
		return webRealPath;
	}
}
