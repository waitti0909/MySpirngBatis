package com.foya.noms.web.controller.inv;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foya.noms.dto.inv.TbInvSrlDTO;
import com.foya.noms.dto.inv.TbInvTxnDTO;
import com.foya.noms.service.inv.INV020Service;
import com.foya.noms.util.JqGridData;
import com.foya.noms.web.controller.BaseController;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

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
 * <td>2014/10/31</td>
 * <td>新建檔案</td>
 * <td>Markee</td>
 * </tr>
 * </table>
 * 
 * @author Markee
 * 
 */
@Controller
@RequestMapping(value = "/inv/inv020")
public class INV020Controller extends BaseController {

	@Autowired
	private INV020Service inv020Service;

	/**
	 * 初始頁面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/init")
	public String init(HttpServletRequest request, Map<String, Object> model) {
		//model.put("mat_no", request.getParameter("paramMatNo"));
		//model.put("wh_id", request.getParameter("paramWhId"));
		return "ajaxPage/inv/INV020";
	}

	/**
	 * 查詢主檔資料
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/query")
	@ResponseBody
	public JqGridData<TbInvSrlDTO> query(HttpServletRequest request) {
		String ven_sn = request.getParameter("ven_sn");
		String mat_name = request.getParameter("mat_name");
		String mat_no= request.getParameter("matNoMaterial");
		String crStartDateStr = request.getParameter("crStartDate");//cr_time
		String crEndDateStr = request.getParameter("crEndDate");//cr_time
		String site_id = request.getParameter("site_id");
		String bts_site_id = request.getParameter("bts_site_id");
		
		
		HashMap<String, String> dataObj=new HashMap<String, String>();
		dataObj.put("ven_sn", (ven_sn!=null&&!ven_sn.equals(""))?"%"+ven_sn+"%":null);
		dataObj.put("site_id", (site_id!=null&&!site_id.equals(""))?"%"+site_id+"%":null);
		dataObj.put("bts_site_id", (site_id!=null&&!site_id.equals(""))?null:bts_site_id);
		dataObj.put("mat_no",(mat_no!=null&&!mat_no.equals(""))?"%"+mat_no+"%":null);
		dataObj.put("mat_name", (mat_name!=null&&!mat_name.equals(""))?"%"+mat_name+"%":null);
		dataObj.put("crStartDateStr", crStartDateStr);
		dataObj.put("crEndDateStr", crEndDateStr);
		List<TbInvSrlDTO> list =(List<TbInvSrlDTO>) inv020Service.search(dataObj);
		PageList<TbInvSrlDTO> page = (PageList<TbInvSrlDTO>) list;
		return new JqGridData<TbInvSrlDTO>(page.getPaginator().getTotalCount(), list);
	}

	/**
	 * 查詢Booking明細資料
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryDetail")
	@ResponseBody
	public JqGridData<TbInvTxnDTO> queryDetail(HttpServletRequest request) {
		String srl_no = request.getParameter("srl_no");
		String crStartDate = request.getParameter("crStartDate");
		String crEndDate = request.getParameter("crEndDate");
		
		log.debug("srl_no = " + srl_no);
		log.debug("crStartDate = " + crStartDate);
		log.debug("crEndDate = " + crEndDate);
		
		
		HashMap<String, String> dataObj=new HashMap<String, String>();
		dataObj.put("srl_no", srl_no);
		dataObj.put("crStartDate", crStartDate+" 00:00:00.0");
		dataObj.put("crEndDate", crEndDate+" 23:59:59.9");
		
		List<TbInvTxnDTO> list =inv020Service.searchDetail(dataObj);
		PageList<TbInvTxnDTO> page = (PageList<TbInvTxnDTO>) list;
		return new JqGridData<TbInvTxnDTO>(page.getPaginator().getTotalCount(), list);
	}
	
	/**
	 * 匯出excel
	 * @return
	 * @throws FileNotFoundException 
	 * @throws Exception 
	 */
	@RequestMapping(value = "/genExcel")
	public void genExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String ven_sn = request.getParameter("ven_sn");
		String mat_name = request.getParameter("mat_name");
		String mat_no= request.getParameter("matNoMaterial");
		String crStartDateStr = request.getParameter("crStartDate");//cr_time
		String crEndDateStr = request.getParameter("crEndDate");//cr_time
		String site_id = request.getParameter("site_id");
		
		
		HashMap<String, String> dataObj=new HashMap<String, String>();
		dataObj.put("ven_sn", (ven_sn!=null&&!ven_sn.equals(""))?"%"+ven_sn+"%":null);
		dataObj.put("site_id", (site_id!=null&&!site_id.equals(""))?"%"+site_id+"%":null);
		dataObj.put("mat_no",(mat_no!=null&&!mat_no.equals(""))?"%"+mat_no+"%":null);
		dataObj.put("mat_name", (mat_name!=null&&!mat_name.equals(""))?"%"+mat_name+"%":null);
		dataObj.put("crStartDateStr", crStartDateStr);
		dataObj.put("crEndDateStr", crEndDateStr);
		List<TbInvSrlDTO> list =(List<TbInvSrlDTO>) inv020Service.search(dataObj);
		
		
		//file name
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String fileName = "INV020-" + sdf.format(new Date());
		
		
		inv020Service.exportExcel(response,  fileName, list,null);
    } 
}
