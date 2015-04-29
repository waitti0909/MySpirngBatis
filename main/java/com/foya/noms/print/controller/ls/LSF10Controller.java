package com.foya.noms.print.controller.ls;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.foya.dao.mybatis.model.TbLsAppForm;
import com.foya.noms.web.controller.BaseController;

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
public class LSF10Controller extends BaseController {
	
	@RequestMapping(value="/lsf10/printPdf")
	public void print(HttpServletRequest req, HttpServletResponse resp) {
		log.debug("LSF10Controller");
		
		// FIXME 以下是假塞物件，請實際由後端以參數讀取TbLsAppForm資料
		TbLsAppForm appForm = new TbLsAppForm();
		appForm.setFORM_ID(req.getParameter("printPdfDoc"));
		appForm.setPATH("/NOMSFile/pdfTemp/lsf/LSF10.pdf");
		Map<String, String> map = new HashMap<String, String>();	// 由前端準備要送至Maker處理的參數
		// FIXME 以上是假塞物件，請實際由後端以參數讀取TbLsAppForm資料		
		
		// 產生PDF文件
		print(resp, appForm, map);
	}
}
