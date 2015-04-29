package com.foya.noms.web.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.foya.dao.mybatis.model.TbLsAppForm;
import com.foya.exception.DataNotFoundException;
import com.foya.noms.resources.AppConstants;
import com.foya.noms.service.DemoPDFService;
import com.foya.noms.service.PdfCreateFactory;
import com.foya.noms.service.PdfExportCreator;

/**
 * 
 * 
 * <table>
 * <tr>
 * <th>版本</th>
 * <th>日期</th>
 * <th>詳細說明</th>
 * <th>modifier</th>
 * </tr>
 * <tr>
 * <td>1.0</td>
 * <td>2014/12/30</td>
 * <td>實際產生PDF Controller</td>
 * <td>Charlie Woo</td>
 * </tr>
 * </table> 
 *
 * @author Charlie Woo
 *
 */
@Controller
public class DemoPdfController extends BaseController {

	public static final String COMBO_PDF = "combo";
	
	@Autowired
	private PdfCreateFactory factory;
	
	@Autowired
	private DemoPDFService demoPDFService; 
	
	@RequestMapping(value="/printPdf")
	public void printDemoPDF(HttpServletRequest req, HttpServletResponse resp) {
		
		String appFormId = req.getParameter("appFormId");
		
		PdfExportCreator creator = factory.getPdfCreator(COMBO_PDF);		
        
		try {
			log.debug("print pdf....");
			TbLsAppForm form = demoPDFService.getAppFormByFormId(appFormId);
			
			if (form != null) {				
//				String realPath = FilenameUtils.normalize(AppConstants.WebRealPathRoot + form.getPATH());
				String realPath = FilenameUtils.normalize(form.getPATH());
				ByteArrayOutputStream baos = creator.create(realPath);
				resp.setContentType(AppConstants.APPLICATION_PDF);
				
				resp.setHeader("Content-Disposition", "attachment; filename=\"" + "Combo_" + StringUtils.substringAfterLast(form.getPATH(), "/") + "\"" );
				
				OutputStream os = resp.getOutputStream();
				
				os.write(baos.toByteArray());
				os.flush();
				os.close();
			} else {
				throw new DataNotFoundException("appFormId=" + appFormId + ", 查無設定資料");
			}
			
		} catch (IOException e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
	}
}
