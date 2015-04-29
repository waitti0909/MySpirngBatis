package com.foya.noms.service;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import com.foya.noms.resources.AppConstants;
import com.foya.noms.service.system.MailService;
import com.foya.noms.util.DateUtils;

@Service
public class ExceptionMailService extends MailService {

	/**
	 * 組合MAIL內容資料
	 * @param user
	 * @param action
	 * @param requestUrl
	 * @param e
	 * @return
	 */
	public String combineMailContents(String user, String action, String requestUrl, Exception e) {
		return combineMailContents(user, action, requestUrl, ExceptionUtils.getFullStackTrace(e));
	}
	
	public String combineMailContents(String user, String action, String requestUrl, String stack) {
		StringBuffer contents = new StringBuffer();
		contents.append("<table border='1' style='width:80%'>"
				+ "<tr>"
				+ "<td align='center'>回報者</td><td>"+user+"</td>"
				+ "<td align='center'>回報時間</td><td>"+DateUtils.formatDate(AppConstants.DATE_TIME_PATTERN)+"</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td align='center'>Action</td><td>"+action+"</td>"
				+ "<td align='center'>RequestURL</td><td>"+requestUrl+"</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td align='center'>Exception</td>"
				+ "<td colspan='3'>" + stack + "</td>"
				+ "</tr>"
				+ "</table>");
		return contents.toString();
	}
}
