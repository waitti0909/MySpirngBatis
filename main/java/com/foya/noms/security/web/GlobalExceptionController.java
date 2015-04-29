package com.foya.noms.security.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.foya.exception.AjaxTimeoutException;
import com.foya.exception.DataNotFoundException;
import com.foya.exception.DbException;
import com.foya.noms.util.BaseJasonObject;
/**
 * 
 * @author tommy
 *
 */
@ControllerAdvice
public class GlobalExceptionController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MessageSource messageSource;
	
	/**
	 * 處理ajax
	 * @param req
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(DbException.class)
	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public BaseJasonObject<Object> handleAjaxException(HttpServletRequest req, DbException ex) {
		log.error("[LOG] " + ExceptionUtils.getFullStackTrace(ex));
		BaseJasonObject<Object> obj = new BaseJasonObject<Object>();
		Map<String, Object> errMap = new HashMap<String, Object>();
		errMap.put("stack", ExceptionUtils.getFullStackTrace(ex));
		errMap.put("requestUrl", req.getRequestURL());
		errMap.put("statusCode", "500");
		obj.setMaps(errMap);
		obj.setMsg(ex.getMessage());
		obj.setSuccess(false);
		return obj;
	}
	/**
	 * 處理ajax
	 * @param req
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(AjaxTimeoutException.class)
	@ResponseStatus(value=HttpStatus.NETWORK_AUTHENTICATION_REQUIRED)
	@ResponseBody
	public String handleAjaxTimeoutException(HttpServletRequest req, AjaxTimeoutException ex) {
		log.info("[LOG] " + ex.getMessage());
		return ex.getMessage();
	}
	
	/**
	 * 權限不足
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(AuthenticationServiceException.class)
	@ResponseStatus(value=HttpStatus.FORBIDDEN)
	public ModelAndView handleCustomException(AuthenticationServiceException ex) {
		log.info(ExceptionUtils.getFullStackTrace(ex));
		ModelAndView model = new ModelAndView("401");
		model.addObject("stack", ex.toString());
		
		return model;
	}
	
	/**
	 * 查無資料
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(DataNotFoundException.class)
	@ResponseBody
	@ResponseStatus(value=HttpStatus.NOT_ACCEPTABLE)
	public ModelAndView handleCustomException(DataNotFoundException ex) {
		log.error(ExceptionUtils.getFullStackTrace(ex));
		ModelAndView model = new ModelAndView("error");
		model.addObject("stack", ExceptionUtils.getFullStackTrace(ex));
		return model;
	}
	
	/**
	 * 404
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(value=HttpStatus.NOT_FOUND)
	@ResponseBody
	public BaseJasonObject<Object> handleNotFoundException(NotFoundException ex) {
		return new BaseJasonObject<Object>(false, ex.getMessage());
	}

	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public BaseJasonObject<Object> handleAllException(HttpServletRequest req, Exception ex) {
		log.error("[LOG] " + ExceptionUtils.getFullStackTrace(ex));
		BaseJasonObject<Object> obj = new BaseJasonObject<Object>();
		Map<String, Object> errMap = new HashMap<String, Object>();
		errMap.put("stack", ExceptionUtils.getFullStackTrace(ex));
		errMap.put("requestUrl", req.getRequestURL());
		errMap.put("statusCode", "500");
		obj.setMaps(errMap);
		obj.setMsg(ex.getMessage());
		obj.setSuccess(false);
		return obj;
	}

}
