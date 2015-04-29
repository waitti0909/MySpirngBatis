package com.foya.noms.web.controller;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.foya.noms.resources.AppConstants;
import com.foya.noms.service.ExceptionMailService;
import com.foya.noms.util.Mail;

@Controller
public class ExceptionController extends BaseController {

	@Autowired
	private ExceptionMailService service;

	@ExceptionHandler(org.springframework.web.HttpSessionRequiredException.class)
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "The session has expired")
	public String handleSessionExpired() {
		log.debug("SessionExpired :The session has expired");
		return "sessionExpired";
	}

	@RequestMapping(value = "/exception/sorryPage")
	public @ResponseBody ModelAndView handleAllException(@RequestParam String statusCode, @RequestParam String errMsg,
			@RequestParam String requestUrl, @RequestParam String stack) {

		ModelAndView model = new ModelAndView(statusCode);
		model.addObject("errCode", statusCode);
		model.addObject("action",  getSession().getAttribute("userAction"));
		model.addObject("requestUrl", requestUrl);
		model.addObject("errMsg", errMsg);
		model.addObject("stack", stack);
		return model;
	}
	
	@RequestMapping(value = "/exception/sendMail")
	public @ResponseBody String sendErrorMailToIT(@RequestParam String action, @RequestParam String requestUrl, @RequestParam String stack) {
		try {			
			String content = service.combineMailContents(getLoginUser().getUsername(), action, requestUrl, stack);
			service.setEncoding("UTF-8");
			service.sendMail(Mail.by(AppConstants.MAIL_TO_IT, "NOMS Exception Report", content));
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		
		return "success";
	}

}
