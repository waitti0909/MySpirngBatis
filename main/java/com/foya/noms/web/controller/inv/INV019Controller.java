package com.foya.noms.web.controller.inv;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foya.noms.service.common.PoiService;
import com.foya.noms.service.inv.INV016Service;
import com.foya.noms.service.inv.INV019Service;
import com.foya.noms.service.org.DomainService;
import com.foya.noms.util.JqGridData;
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
 * <td>2014/11/03</td>
 * <td>新建檔案</td>
 * <td>Gran</td>
 * </tr>
 * </table>
 * 
 * @author Gran
 * 
 */
@Controller
@RequestMapping(value = "/inv/inv019")
public class INV019Controller extends BaseController {
	
	@Inject
	private InvPublicUtilController invPublicUtilController;
	@Autowired
	private INV019Service inv019Service;
	@Autowired
	private PoiService poiService;
	@Autowired
	private DomainService domainService;
	@Autowired
	private INV016Service inv016Service;
	/**
	 * 初始頁面
	 * 
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/init")
	public String init(HttpServletRequest request, Map<String, Object> model)
			throws Exception {
		//倉庫
		model.put("wareHouseSelect", invPublicUtilController.initWareHouseSelectIsActive(request));
		//區域
		model.put("domainSelect", domainService.getStandardDomainList());
		return "ajaxPage/inv/INV019";
	}
	
	/**
	 * 查詢
	 */
	@RequestMapping(value = "/Search")
	@ResponseBody
	public JqGridData<Object> search(HttpServletRequest request) {
		List<Object> list = inv019Service.search(request);

		if(list.size() >= 1000){
			return new JqGridData<Object>(-1, null);
		}else{
			return this.getJqGridData(request.getParameter("page"), request.getParameter("size"), list);
		}
	}

	/**
	 * 匯出excel
	 * @return
	 * @throws FileNotFoundException 
	 * @throws Exception 
	 */
	@RequestMapping(value = "/getExcel", method = RequestMethod.GET) 
	@ResponseBody
    public void getExcel(HttpServletRequest request, HttpServletResponse response) { 
		List<Object> list = inv019Service.search(request);
		
		//file name
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String fileName = "INV019-" + sdf.format(new Date());
		
		inv019Service.exportExcel(response, "sheet", fileName, list);
    } 
	
	/**
	 * 手動設定jqGrid換頁功能
	 * @param inputPage
	 * @param inputSize
	 * @param list
	 * @return
	 */
	private JqGridData<Object> getJqGridData(String inputPage, String inputSize, List<Object> list) {
		if (list.size() == 0) {
			return new JqGridData<Object>(list.size(), list);
		}

		int page = Integer.valueOf(inputPage == null || inputPage.equals("") ? "1" : inputPage);
		int size = Integer.valueOf(inputSize == null || inputSize.equals("") ? "10" : inputSize);
		int startRow = (page - 1) * size;
		int endRow = (page * size) - 1;

		List<Object> results = new ArrayList<Object>();
		while (startRow <= endRow && startRow < list.size()) {
			results.add(list.get(startRow++));
		}

		return new JqGridData<Object>(list.size(), results);
	}	
	
}
