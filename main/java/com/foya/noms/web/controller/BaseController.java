package com.foya.noms.web.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.foya.dao.mybatis.model.TbLsAppForm;
import com.foya.exception.NomsException;
import com.foya.noms.dto.auth.UserDTO;
import com.foya.noms.print.service.PrintPdfService;
import com.foya.noms.resources.AppConstants;
import com.foya.noms.util.PdfUtil;
import com.lowagie.text.DocumentException;

// test branch1 by mike
public abstract class BaseController {

	protected Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private PrintPdfService service;
	
	public static final String AJAX_SUCCESS = "success";
	public static final String AJAX_FAILED = "failed";
	public static final String AJAX_EMPTY = "empty";
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
		// 日期
        CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy/MM/dd"), true);
        binder.registerCustomEditor(Date.class, editor);
        // 字串
        StringTrimmerEditor strEditor = new StringTrimmerEditor(true);
        binder.registerCustomEditor(String.class, strEditor);
    }
	
	protected HttpServletRequest getRequest() {
		ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return attrs.getRequest();
	}

	protected HttpSession getSession() {
		return getRequest().getSession();
	}

	protected UserDTO getLoginUser() {
		return (UserDTO) getSession().getAttribute("loginUser");
	}
	
	protected String getUserIp() {
		return getRequest().getRemoteAddr();
	}
	
	protected String getMessageDetail(String key,String... replacrStrings ){
		return messageSource.getMessage(key, replacrStrings, getRequest().getLocale());
	}
	
	protected String getMessageDetail(String key){
		return messageSource.getMessage(key, null, getRequest().getLocale());
	}
	
	protected Integer getCurrentMenuId() {
		return Integer.parseInt((String) getSession().getAttribute("currentMenuId"));
	}
	
	protected void print(HttpServletResponse resp, TbLsAppForm appForm, Map<String, String> map) {
		// 標楷體字型
		AppConstants.BASEFONT_TYPE_KAIU = getSession().getServletContext().getRealPath("/NOMSFile/pdfTemp/kaiu.ttf");
		log.debug("Print pdf file....");
		try {
			ByteArrayOutputStream baos = service.makePdfs(appForm, map);
			resp.setContentType(AppConstants.APPLICATION_PDF);
			
	        resp.setHeader("Content-Disposition", "attachment; filename=\"" + PdfUtil.getExportFileName(appForm.getPATH()) + "\"" );
	        OutputStream os = resp.getOutputStream();
	        byte[] ba = baos.toByteArray();
	        
	        InputStream in = new ByteArrayInputStream(ba);	        
	        byte[] buffer = new byte[1024];
			int length = -1;
			while((length = in.read(buffer)) != -1) {
				os.write(buffer, 0, length);
			} // end while
			
			os.write(baos.toByteArray());
			os.flush();
			os.close();
			
		} catch (IOException e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
		} catch (NomsException e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
		} catch (DocumentException e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
	}
	
}
